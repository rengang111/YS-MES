<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>库存管理-成品入库登记</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var shortYear = ""; 
	var validator;
	
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
			
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = data;				    			
    			name = jQuery.fixedWidth(name,35);				    			
    			return name;
    		}}
           
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
		

	};

		
	$(document).ready(function() {

		//设置光标项目
		//$("#attribute1").focus();
		//$("#order\\.piid").attr('readonly', "true");

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#stock\\.checkindate").val(shortToday());
		
		//ajax();//产品
		ajaxHistory();//入库记录
		
		$("#stock\\.checkindate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$("#goBack").click(
				function() {
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/storage?methodtype=orderSearchInit&keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					
					var surplus = currencyToFloat( $("#surplus").val() );
					var orderQty = currencyToFloat( '${order.totalQuantity}');
					var stockinQty = currencyToFloat( '${order.stockinQty}');
					var curr = currencyToFloat($(".quantity").val());
					var tmp = surplus / orderQty;
					var sts = $("#stock\\.storagefinish").val();
				
					if(tmp <= 0.1 && sts == '010'){
						if(confirm("剩余入库数量小于订单的10%，是否要选择入库完结处理")){
							return;
						}
					}
					if(surplus == 0 && sts == '010'){
						$("#stock\\.storagefinish").val('020');
					}
					
					if( (curr + stockinQty) > orderQty ){

						$().toastmessage('showWarningToast', "入库数量不能超过订单数。");
						return;
					}
				
					var flag = checkMaterialStockOut();
										
					if(flag == false){

						if( (curr + stockinQty) >= orderQty ){

							//$().toastmessage('showWarningToast', "物料未领完，不能全部入库。");
							//return;
						}
					}

					$('#insert').attr("disabled","true").removeClass("DTTT_button");
					
					if (validator.form()) {
						$('#formModel').attr("action", "${ctx}/business/storage?methodtype=insertProduct");
						$('#formModel').submit();
						
					}
					
		});
				
		//计算剩余数量
		$(".quantity").change(function() {
			
			quantitySum();
		});
		
		foucsInit();
		
		quantitySum();
		
		validator = $("#formModel").validate({
			rules: {
				"stockList[0].packagnumber": {
					required: true,	
					number: true,
				},
			},
			errorPlacement: function(error, element) {
			    if (element.is(":radio"))
			        error.appendTo(element.parent().next().next());
			    else if (element.is(":checkbox"))											    	
			    	error.insertAfter(element.parent().parent());
			    else
			    	error.insertAfter(element);
			}
		});	
	});
	
	function checkMaterialStockOut(){
		//alert("plan -- requisitionQty:"+'${stockout.manufactureQty}'+"--"+'${stockout.requisitionQty}')
		var plan = currencyToFloat( '${stockout.manufactureQty}');
		var requisitionQty = currencyToFloat( '${stockout.requisitionQty}');
		var tmp = requisitionQty  - plan;
		if(tmp >= 0){
			return true;
		}else{
			return false;
		}
	}
	
	function quantitySum(){
		var surplus = currencyToFloat( '${order.surplus}');
		var curr = currencyToFloat($(".quantity").val());
		var orderQty = currencyToFloat( '${order.totalQuantity}');
		var stockinQty = currencyToFloat( '${order.stockinQty}');
		
		var shengyu = surplus - curr;
		if(shengyu < 0){
			$().toastmessage('showWarningToast', "入库数量不能超过订单数。");
			shengyu = 0;
			curr = surplus;
		}
		
		$('#surplus').val(floatToCurrency(shengyu));
		$('.quantity').val(floatToCurrency(curr));
		
		if(shengyu == 0){
			$("#stock\\.storagefinish").val('020');
		}else{
			$("#stock\\.storagefinish").val('010');
		}
	}
	
	function doEdit(YSId,receiptId) {
		var url = "${ctx}/business/storage?methodtype=editProduct"
				+"&YSId="+YSId
				+"&receiptId="+receiptId;
		location.href = url;
	}
	
		
	function ajaxHistory() {

		var YSId = '${order.YSId }';
		var materialId = '${order.materialId }';
		var actionUrl = "${ctx}/business/storage?methodtype=getProductStockInDetail";
		actionUrl = actionUrl + "&YSId="+YSId+"&materialId="+materialId;
		
		var t = $('#history').DataTable({
			
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"autoWidth"	:false,
			"searching" : false,
			"retrieve"  : true,
			"dom"		: '<"clear">rt',
			"sAjaxSource" : actionUrl,
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
						 alert(errorThrown)
		             }
				})
			},	
 			"columns" : [
		        	{"data": null,"className":"dt-body-center"
				}, {"data": "checkInDate","className":"td-center"
				}, {"data": "receiptId","className":"td-left"
				}, {"data": "quantity","className":"td-right"
				}, {"data": "packagNumber","className":"td-right"
				}, {"data": "packaging","className":"td-center"
				}, {"data": "areaNumber",
				}, {"data": null,"className":"dt-body-center"
				}
			],
			"columnDefs":[
	    		{"targets":7,"render":function(data, type, row){
					return "<a href=\"###\" onClick=\"doEdit('"  + row["YSId"] + "','"  + row["receiptId"] + "')\">"+"编辑"+"</a>";
                   }}
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

</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<form:hidden path="stock.subid" />
	<form:hidden path="stock.ysid"  value="${order.YSId }"/>
	
	<fieldset>
		<legend> 成品信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="200px">&nbsp;${order.YSId }</td>
							
				<td width="100px" class="label">成品编码：</td>
				<td width="200px">&nbsp;${order.materialId }</td>							
				<td width="100px" class="label">成品名称：</td>
				<td>${order.materialName }</td>
			</tr>
			<tr> 				
				<td class="label" width="100px">入库时间：</td>					
				<td width="200px">
					<form:input path="stock.checkindate" class="read-only" /></td>
							
				<td width="100px" class="label">仓管员：</td>
				<td width="200px">
					<form:input path="stock.keepuser" class="short read-only" value="${userName }" /></td>							
				<td class="label"></td>
				<td></td>
			</tr>
			<tr style="height: 40px;vertical-align: bottom;"> 				
				<td class="label" >本次入库数量：</td>					
				<td>
					<form:input path="stockList[0].quantity"  value="${order.surplus }" class="num quantity font16" style="width: 130px;"/>
					<form:hidden path="stockList[0].materialid" value="${order.materialId }"/></td>
							
				<td class="label">累计入库数量：</td>
				<td class="font16">&nbsp;${order.stockinQty }</td>							
				<td class="label">订单数量：</td>
				<td class="font16">&nbsp;${order.totalQuantity }</td>
			</tr>
			<tr> 				
				<td class="label" >包装方式：</td>					
				<td>
					<form:select path="stockList[0].packaging" style="width: 140px;">
								<form:options items="${packagingList}" 
									itemValue="key" itemLabel="value"/></form:select></td>
							
				<td class="label">入库件数：</td>
				<td>
					<form:input path="stockList[0].packagnumber" class="" /></td>							
				<td class="label">库位编号：</td>
				<td><form:input path="stockList[0].areanumber" class="" /></td>
			</tr>
			<tr> 				
				<td class="label" >剩余入库数量：</td>					
				<td>
					<input type="text" id="surplus" value="0" class="read-only num font16" style="width: 130px;"/></td>
							
				<td class="label">入库完结处理：</td>
				<td><form:select path="stock.storagefinish" style="width: 140px;">
								<form:options items="${storageFinishList}" 
									itemValue="key" itemLabel="value"/></form:select></td>							
				<td class="label"></td>
				<td></td>
			</tr>	
										
		</table>
	</fieldset>
		
	<fieldset class="action" style="text-align: right;margin-top:-20px">
		<button type="button" id="insert" class="DTTT_button">确认入库</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>	
	<fieldset>
	<legend> 入库记录</legend>
	<div class="list">
		<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;margin: 5px 0px -10px 10px;">
				<a class="DTTT_button" id="all" onclick="doPrint();return false;">打印入库单</a>
		</div>
		<table class="display" id="history" >	
			<thead>		
				<tr>
					<th style="width:15px">No</th>
					<th style="width:80px">入库时间</th>
					<th style="width:120px">入库单号</th>
					<th style="width:80px">入库数量</th>
					<th style="width:60px">入库件数</th>
					<th style="width:55px">包装方式</th>
					<th style="width:80px">库位编号</th>	
					<th style="width:30px"></th>	
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

function doPrint() {
	var YSId = '${order.YSId }';
	var materialId = '${order.materialId }';
	var actionUrl = "${ctx}/business/storage?methodtype=printProductReceipt";
	actionUrl = actionUrl + "&YSId="+YSId+"&materialId="+materialId;
		
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '520px' ], 
		scrollbar : false,
		title : false,
		content : actionUrl,
		cancel: function(index){			
		}    
	});		

};
</script>

</html>
