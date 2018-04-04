<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>领料申请-领料单</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
		
	var shortYear = ""; 
	
	function ajax() {
		
		var requisitionId= '${detail.requisitionId}';
		var actionUrl = "${ctx}/business/requisition?methodtype=getRequisitionDetail";
		actionUrl = actionUrl +"&requisitionId="+requisitionId;
		
		var table = $('#example').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : "200px",
	      // "scrollCollapse": false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"sAjaxSource" : actionUrl,
			"fnServerData" : function(sSource, aoData, fnCallback) {
// 				var param = {};
// 				var formData = $("#condition").serializeArray();
// 				formData.forEach(function(e) {
// 					aoData.push({"name":e.name, "value":e.value});
// 				});

				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					//"data" : JSON.stringify(aoData),
					success: function(data){					
						fnCallback(data);
						
						reloadFn();
						foucsInit();
						
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns" : [
		        	{"data": null,"className":"dt-body-center",
				}, {"data": "materialId","className":"td-left"
				}, {"data": "materialName","className":"td-left"
				}, {"data": "unitQuantity","className":"td-right"	//3
				}, {"data": "manufactureQuantity","className":"td-right"
				}, {"data": null,"className":"td-right"	//5
				}, {"data": "totalRequisition","className":"td-right"
				}, {"data": "quantityOnHand","className":"td-right"	//7
				}, {"data": null,"className":"td-right"		//8
				}, {"data": null,"className":"td-right"	,	//9
				}, {"data": "purchaseType","className":"td-right"		//10
				}, {"data": "supplierId","className":"td-right"		//11
				}, {"data": null,"className":"td-right"		//12
				}
			],
			"columnDefs":[
	    		
	    		{"targets":2,"render":function(data, type, row){ 					
					var index=row["rownum"]	
					var inputTxt =  '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value="'+row["overQuantity"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.subbomno" name="requisitionList['+index+'].subbomno" value="'+row["subBomNo"]+'"/>';

	    			var name =  jQuery.fixedWidth( row["materialName"],30); 	    			
	    			return name + inputTxt;
                }},
	    		{"render":function(data, type, row){	    			
	    			var val = row["contractMaterialId"];
	    			var storage = row["contractStorage"];
	    			var index=row["rownum"]
	    			if(val == null || val =='')
	    				storage='-';
	    			var inputTxt = '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
				
	    			return storage + inputTxt;				 
                }},
	    		{"targets":6,"render":function(data, type, row){	    			
					var index=row["rownum"];
					var totalRequisition = row["manufactureQuantity"];//需求量
					var quantity = row["quantity"];//本次领料
					var tmp = currencyToFloat(totalRequisition) - currencyToFloat(quantity);
					
					return floatToCurrency(tmp);
                }},
	    		{"targets":7,"render":function(data, type, row){	    			
					var index=row["rownum"];
					var quantityOnHand = row["quantityOnHand"];//可用库存
					var quantity = row["quantity"];//本次领料
					var tmp = currencyToFloat(quantityOnHand) - currencyToFloat(quantity);
					
					return floatToCurrency(tmp);
                }},
	    		{"targets":8,"render":function(data, type, row){	    			
					var index=row["rownum"];
					var val = row["quantity"];//本次领料
					var inputTxt = '<input type="text" id="requisitionList'+index+'.quantity" name="requisitionList['+index+'].quantity" class="quantity num mini"  value="'+val+'"/>';
				
					return inputTxt;
                }},
	    		{"targets":9,"render":function(data, type, row){	    			

					var quantity = currencyToFloat(row["manufactureQuantity"]);
					var accumulated = currencyToFloat(row["totalRequisition"]);
					
					var surplus = (quantity - accumulated);	
					if(surplus < 0)
						surplus = 0;
					return floatToCurrency(surplus);
                }},
                {
					"visible" : false,
					"targets" : [5,10,11,12]
				}
			]
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(8)',function() {

			var $td = $(this).parent().find("td");

			var $oArrival = $td.eq(4);//计划
			var $oOverQty = $td.eq(2).find("input");//超领
			var $oyiling  = $td.eq(5);//已领料
			var $oTotalQt = $td.eq(6);//总库存
			var $oCurrQty = $td.eq(7).find("input");//本次领料
			var $oSurplus = $td.eq(8);//剩余

			var fArrival  = currencyToFloat($oArrival.text());
			var fYiling   = currencyToFloat($oyiling.text());
			var fCurrQty  = currencyToFloat($oCurrQty.val());	
			var fTotalQt  = currencyToFloat($oTotalQt.text());	
			var fOverQty  = currencyToFloat($oOverQty.val());	
			
			//最多允许领料数量 = 计划 - 已领料
		 	var fMaxQuanty = fArrival - fYiling;
			if(fMaxQuanty < 0)
				fMaxQuanty = 0;
			if ( fTotalQt >= fCurrQty ){//总库存充足
				if(fCurrQty > fMaxQuanty){//超领
					fOverQty = fCurrQty - fMaxQuanty
					$().toastmessage('showWarningToast', "领料数量超出需求量！");
				}				
			}else{//领料数大于可用库存
				fCurrQty = fTotalQt;
				$().toastmessage('showWarningToast', "实际库存不足！");
			}
			
			//剩余数量=计划 - 本次 - 已领
			var fSurplus = fArrival - fYiling - fCurrQty;
			if (fSurplus < 0)
				fSurplus = 0;
			$oSurplus.html(floatToCurrency(fSurplus));
			$oCurrQty.val(fCurrQty);
			$oOverQty.val(floatToCurrency(fOverQty));

		});
		
						
		t.on('click', 'tr', function() {
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }			
		});
		
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	};
	
	$(document).ready(function() {
		
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#requisition\\.requisitiondate").val(shortToday());
		
		ajax();

		//$('#example').DataTable().columns.adjust().draw();
		
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var virtualClass = $('#virtualClass').val();
					var url = "${ctx}/business/requisition"+"?virtualClass="+virtualClass;
					location.href = url;		
				});

		$("#showHistory").click(
				function() {
					var YSId='${order.YSId }';
					var virtualClass = $('#virtualClass').val();
					var url = "${ctx}/business/requisition?methodtype=getRequisitionHistoryInit&YSId="+YSId+"&virtualClass="+virtualClass;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {

			var virtualClass = $('#virtualClass').val();
			$('#formModel').attr("action", "${ctx}/business/requisition?methodtype=update"+"&virtualClass="+virtualClass);
			$('#formModel').submit();
		});
		
		$("#reverse").click(function () { 
			$("input[name='numCheck']").each(function () {  
		        $(this).prop("checked", !$(this).prop("checked"));  
		    });
		});
		
		reloadFn();
		
		foucsInit();		
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/requisition?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<!-- 虚拟领料区分 -->
	<input type="hidden" id="virtualClass" value="${virtualClass }" />
	<input type="hidden" id="goBackFlag" />
	<form:hidden path="requisition.recordid"  value="${detail.recordId }" />
	<form:hidden path="requisition.ysid"  value="${detail.YSId }" />
	<form:hidden path="requisition.requisitionid"  value="${detail.requisitionId }" />
	<fieldset>
		<legend> 领料单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">领料申请编号：</td>					
				<td width="200px">${detail.requisitionId }</td>
														
				<td width="100px" class="label">领料日期：</td>
				<td width="200px">
					<form:input path="requisition.requisitiondate" class="short read-only" /></td>
				
				<td width="100px" class="label">领料人：</td>
				<td>
					<form:input path="requisition.requisitionuserid" class="short read-only" value="${userName }" /></td>
			</tr>
			<tr> 				
				<td class="label">耀升编号：</td>					
				<td>${order.YSId }</td>
							
				<td class="label">产品编号：</td>					
				<td>${order.materialId }</td>
							
				<td class="label">产品名称：</td>					
				<td>${order.materialName }</td>
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >编辑保存</a>
		<a class="DTTT_button DTTT_button_text" id="showHistory" >取消编辑</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>

	<fieldset style="margin-top: -30px;">
		<legend> 物料需求表</legend>
		<div class="list">
			<table id="example" class="display" style="width:100%">
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th class="dt-center" width="120px">物料编号</th>
						<th class="dt-center" >物料名称</th>				
						<th class="dt-center" width="60px">基本用量</th>
						<th class="dt-center" width="60px">计划用量</th>
						<th class="dt-center" width="60px">已入库</th>
						<th class="dt-center" width="60px">已领数量</th>
						<th class="dt-center" width="60px">虚拟库存</th>
						<th class="dt-center" width="80px">本次领料
						<!-- 	<input type="checkbox" name="selectall" id="selectall" /><label for="selectall"></label> --></th>
						<th class="dt-center" width="60px">剩余数量</th>
						<th class="dt-center" width="60px">物料特性</th>
						<th class="dt-center" width="60px">供应商</th>
						<th class="dt-center" width="60px"></th>
					</tr>
				</thead>					
			</table>
		</div>
	</fieldset>
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	openLayer(url);

};

function showYS(YSId) {
	//var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;

	var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
	openLayer(url);

};

function reloadFn(){
	$("#selectall").click(function () { 
		
		var sltFlag = $(this).prop("checked");
			
		$('#example tbody tr').each (function (){
		
			var vcontract = $(this).find("td").eq(4).text();////计划用量
			var vreceive  = $(this).find("td").eq(5).text();//已领量
			var vstocks   = $(this).find("td").eq(6).text();//库存

			var fcontract= currencyToFloat(vcontract);
			var freceive = currencyToFloat(vreceive);
			var fstocks  = currencyToFloat(vstocks);
			var fsurplus = fcontract - freceive;
			if(fsurplus < 0)
				fsurplus = 0;
			var vsurplus = floatToCurrency(fsurplus);

			if(sltFlag){//一次性全部领料
				
				if(fsurplus > "0"){//未领完的场合下
					if(fstocks >= fsurplus){//库存大于需求量
						$(this).find("td").eq(7).find("input").val(vsurplus);//本次领料
						$(this).find("td").eq(8).html("0")//剩余数清零
					}else{
						$(this).find("td").eq(7).find("input").val(fstocks);//本次领料
						$(this).find("td").eq(8).html(floatToCurrency( fsurplus - fstocks ));//剩余数清零							
					}
				}else{//超领
					
				}
			
			}else{//取消一次性全部领料
				$(this).find("td").eq(7).find("input").val("0");//本次到货清零
				$(this).find("td").eq(8).html(vsurplus);//剩余数
			}		
		})			
	

	});
	
}

</script>

</html>
