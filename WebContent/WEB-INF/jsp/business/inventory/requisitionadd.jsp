<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>领料申请-领料单</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var shortYear = ""; 
	
	function ajax() {

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
			//"scrollY"    : "160px",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : '<"clear">rt',		
			"columns" : [
			        	{"className":"dt-body-center"
					}, {"className":"td-left"
					}, {
					}, {"className":"dt-body-center"
					}, {"className":"td-right"
					}, {"className":"td-right"
					}, {"className":"td-right"
					}, {"className":"td-right"
					}, {"className":"td-right"
					}, {"className":"td-right"
					}, {"className":"td-right"
					}
				]
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(5)',function() {

			var $td = $(this).parent().find("td");

			var $oArrival = $td.eq(4).find("input");
			var $oRecorde = $td.eq(6).find("span");
			var $oQuantity= $td.eq(5).find("span");
			var $oSurplus = $td.eq(7).find("span");

			var fArrival  = currencyToFloat($oArrival.val());
			var fRecorde  = currencyToFloat($oRecorde.html());
			var fquantity = currencyToFloat($oQuantity.html());	
			
			if(fArrival > (fquantity-fRecorde)){

				$().toastmessage('showWarningToast', "登记数不能大于剩余数");
		       // $(this).find("input:text").removeClass('bgwhite').removeClass('bgnone');
           	 	$(this).find("input:text").addClass('error');
				return;
			}else{
				$(this).find("input:text").removeClass('error')
			}
			
			//剩余数量
			var fsurplus = floatToCurrency(fquantity - fRecorde - fArrival);	
			$oSurplus.html(fsurplus);
			$oArrival.val(floatToCurrency(fArrival))

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

	function historyAjax() {
		var contractId = '${contract.contractId }';
		var t = $('#example2').DataTable({
			
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/requisition?methodtype=getArrivalHistory&contractId="+contractId,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				var param = {};
				var formData = $("#condition").serializeArray();
				formData.forEach(function(e) {
					aoData.push({"name":e.name, "value":e.value});
				});

				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					"data" : JSON.stringify(aoData),
					success: function(data){							
						fnCallback(data);
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			        	{"data": null,"className":"dt-body-center"
					}, {"data": "arrivalId","className":"dt-body-center"
					}, {"data": "arriveDate","className":"dt-body-center"
					}, {"data": "materialId"
					}, {"data": "materialName"
					}, {"data": "unit","className":"dt-body-center"
					}, {"data": "quantity","className":"td-right"	
					}, {"data": "status","className":"td-center"
					}, {"data": null,"className":"td-center"
					}
				] ,
				"columnDefs":[
		    		{"targets":8,"render":function(data, type, row){
		    			var contractId = row["contractId"];		    			
		    			var rtn= "<a href=\"###\" onClick=\"doEdit('" + row["contractId"] + "','" + row["arrivalId"] + "')\">编辑</a>";
		    			return rtn;
		    		}},
		    	]        
			
		}).draw();
						
		t.on('click', 'tr', function() {
			
			var rowIndex = $(this).context._DT_RowIndex; //行号			
			//alert(rowIndex);

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

		//设置光标项目
		//$("#attribute1").focus();
		//$("#order\\.piid").attr('readonly', "true");

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#requisition\\.requisitiondate").val(shortToday());
		
		ajax();

		//historyAjax();//领料记录

		//autocomplete();
		
		//$('#example').DataTable().columns.adjust().draw();
		
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$("#goBack").click(
				function() {
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/requisition?keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
				
			var i=0;
			$(".error").each(function () {  
		       i++;  
		    });
			
			if(i>0){

				$().toastmessage('showWarningToast', "请先修正页面中的错误输入，再保存。");
				return
			}
					
			$('#formModel').attr("action", "${ctx}/business/requisition?methodtype=insert");
			$('#formModel').submit();
		});
		
		$("#reverse").click(function () { 
			$("input[name='numCheck']").each(function () {  
		        $(this).prop("checked", !$(this).prop("checked"));  
		    });
		});
		
		$("#selectall").click(function () { 
			
			$('#example tbody tr').each (function (){
			
				var vcontract = $(this).find("td").eq(5).find("span").text();////计划用量
				var vreceive  = $(this).find("td").eq(7).find("span").text();//已领量
				var vstocks   = $(this).find("td").eq(8).find("span").text();//库存
				var vsurplus  = $(this).find("td").eq(10).find("span").text();//剩余

				var fcontract= currencyToFloat(vcontract);
				var freceive = currencyToFloat(vreceive);
				var fstocks  = currencyToFloat(vstocks);
				var fsurplus = floatToCurrency(fcontract - freceive);
				

				if(fsurplus > "0"){
					if(fstocks > fsurplus){

						$(this).find("td").eq(9).find("input").val(fsurplus);//本次领料
						$(this).find("td").eq(10).find("span").text("0")//剩余数清零
					}else{

						$(this).find("td").eq(9).find("input").val(fstocks);//本次领料						
						$(this).find("td").eq(10).find("span").text(fsurplus - fstocks)//剩余数清零
					}
				}
							
			})

		});
		

		$("#reverse").click(function () { 
			
			$('#example tbody tr').each (function (){

				var vcontract = $(this).find("td").eq(5).find("span").text();////计划用量
				var vreceive  = $(this).find("td").eq(7).find("span").text();//已领量
				var vstocks   = $(this).find("td").eq(8).find("span").text();//库存
				var vquantity = $(this).find("td").eq(9).find("span").text();//本次领料
				var vsurplus  = $(this).find("td").eq(10).find("span").text();//剩余

				var fcontract= currencyToFloat(vcontract);
				var freceive = currencyToFloat(vreceive);
				var fstocks  = currencyToFloat(vstocks);
				var fquantity= currencyToFloat(vquantity);
				var fsurplus = floatToCurrency(fcontract - freceive);

				
					$(this).find("td").eq(10).find("span").text(fsurplus);//剩余数
					$(this).find("td").eq(9).find("input").val("0");//本次到货清零
				
							
			})

		});
		
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

	<input type="hidden" id="goBackFlag" />
	
	<fieldset>
		<legend> 领料单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">领料申请编号：</td>					
				<td width="200px">
					<form:input path="requisition.requisitionid" class="short required read-only" value="保存后自动生成" /></td>
														
				<td width="100px" class="label">领料日期：</td>
				<td width="200px">
					<form:input path="requisition.requisitiondate" class="short read-only" /></td>
				
				<td width="100px" class="label">领料人：</td>
				<td>
					<form:input path="requisition.requisitionuserid" class="short read-only" value="${userName }" /></td>
			</tr>
			<tr> 				
				<td class="label">耀升编号：</td>					
				<td>&nbsp;<a href="#" onClick="showYS('${order.YSId}')">${order.YSId }</a></td>
									
				<td class="label">生产数量：</td>					
				<td colspan="3">&nbsp;${order.orderQuantity }&nbsp;(订单数量+额外采购)</td>
			</tr>
			<tr>
							
				<td class="label">产品编号：</td>					
				<td>&nbsp;<a href="#" onClick="showContract('${order.materialId }')">${order.materialId }</a></td>
							
				<td class="label">产品名称：</td>					
				<td colspan="3">&nbsp;${order.materialName }</td>
			</tr>
										
		</table>
</fieldset>

<fieldset>
	<legend>物料需求表</legend>
	<div class="list">	
	<table id="example" class="display" >
		<thead>				
			<tr>
				<th style="width:1px">No</th>
				<th class="dt-center" width="120px">物料编号</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="30px">单位</th>
				<th class="dt-center" width="60px">基本用量</th>
				<th class="dt-center" width="60px">计划用量</th>
				<th class="dt-center" width="60px">已入库</th>
				<th class="dt-center" width="60px">已领数量</th>
				<th class="dt-center" width="60px">总库存</th>
				<th class="dt-center" width="80px">
					<input type="checkbox" name="selectall" id="selectall" /><label for="selectall">全部领完</label>
					<input type="checkbox" name="reverse" id="reverse" /><label for="reverse">全部清空</label></th>
				<th class="dt-center" width="60px">剩余数量</th>
			</tr>
		</thead>
		
	<tbody>
		<c:forEach var="list" items="${material}" varStatus='status' >
			<tr>
				<td></td>
				<td>${list.materialId }
					<form:hidden path="requisitionList[${status.index}].materialid" value="${list.materialId }"/></td>
				<td><span id="name${status.index}">${list.materialName }</span></td>
				<td><span>${list.unit }</span></td>
				<td><span>${list.unitQuantity }</span></td>
				<td><span>${list.quantity }</span></td>
				<td><span>${list.contractStorage }</span></td>
				<td><span>${list.totalRequisition }</span></td>
				<td><span>${list.quantityOnHand }</span></td>
				<td><form:input path="requisitionList[${status.index}].quantity" class="quantity num mini"  value="0"/></td>
				<td><span id="surplus${ status.index}"></span></td>
			</tr>
			<script type="text/javascript">
					var index = '${status.index}';
					var quantity = currencyToFloat('${list.quantity}');
					var accumulated = currencyToFloat('${list.totalRequisition}');
					var name ='${list.materialName}';
					var surplus = quantity - accumulated;
					
					$('#surplus'+index).html(floatToCurrency( surplus ));
					$('#name'+index).html(jQuery.fixedWidth(name,20));
			</script>
		</c:forEach>
		
	</tbody>
</table>
</div>
</fieldset>
<div style="clear: both"></div>
<fieldset class="action" style="text-align: right;">
	<button type="button" id="insert" class="DTTT_button">保存</button>
	<button type="button" id="goBack" class="DTTT_button">返回</button>
</fieldset>		

<div style="clear: both"></div>

<fieldset>
	<legend>领料记录</legend>
	<div class="list">	
	<table id="example2" class="display" >
		<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" style="width:60px">收货编号</th>
				<th class="dt-center" width="100px">到货日期</th>
				<th class="dt-center" width="150px">物料编号</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="40px">单位</th>
				<th class="dt-center" width="80px">到货数量</th>
				<th class="dt-center" width="60px">状态</th>
				<th class="dt-center" width="30px"></th>
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
	var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;

	//var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
	openLayer(url);

};

</script>

</html>
