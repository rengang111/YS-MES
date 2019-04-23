<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>库存管理-成品入库编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var validator;
		
	$(document).ready(function() {

		
		ajaxHistory();//入库记录
			
		
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
					var tmp = surplus / orderQty;
					var sts = $("#stock\\.storagefinish").val();
				
					if(tmp <= 0.1 && sts == '010'){
						if(confirm("剩余入库数量小于订单的10%，是否要选择入库完结处理")){
							return;
						}
					}

					$('#insert').attr("disabled","true").removeClass("DTTT_button");
					
					if (validator.form()) {

						$('#formModel').attr("action", "${ctx}/business/storage?methodtype=updateProduct");
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
	
	function quantitySum(){

		var order = currencyToFloat( '${order.totalQuantity}');
		var curr = currencyToFloat($(".quantity").val());
		var stockin = currencyToFloat('${order.stockinQty}');
		
		stockin = stockin - currencyToFloat ('${head.quantity }');//累计入库：扣除当前入库数量

		var shengyu = order - stockin;//剩余总量
		
		var currShenyu = shengyu - curr;//当前剩余数量
		
		$('#surplus').val(floatToCurrency(currShenyu));
		$('.quantity').val(floatToCurrency(curr));
		$('#stockinQty').text(floatToCurrency(stockin));
	}
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/arrival?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
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
						}
					],
			
	
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
					<form:input path="stock.checkindate" class="read-only" value="${head.checkInDate }" /></td>
							
				<td width="100px" class="label">仓管员：</td>
				<td width="200px">
					<form:input path="stock.keepuser" class="short read-only" value="${userName }" /></td>							
				<td class="label"></td>
				<td></td>
			</tr>
			<tr style="height: 40px;vertical-align: bottom;"> 				
				<td class="label" >本次入库数量：</td>					
				<td>
					<form:input path="stockList[0].quantity"  value="${head.quantity }" class="num quantity font16" style="width: 130px;"/>
					<form:hidden path="stockList[0].materialid" value="${head.materialId }"/>
					<form:hidden path="stockList[0].receiptid" value="${head.receiptId }"/>
					<form:hidden path="stockList[0].recordid" value="${head.recordId }"/>
					<form:hidden path="stock.receiptid" value="${head.receiptId }"/>
					<form:hidden path="stock.recordid" value="${head.stockRcordId }"/>
					<form:hidden path="oldQuantity" value="${head.quantity }"/>
					<form:hidden path="oldPackagNumber" value="${head.packagNumber }"/></td>
					
				<td class="label">累计入库数量：</td>
				<td class="font16">&nbsp;<span id="stockinQty">${order.stockinQty }</span></td>							
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
					<form:input path="stockList[0].packagnumber" class="" value="${head.packagNumber }"/></td>							
				<td class="label">库位编号：</td>
				<td><form:input path="stockList[0].areanumber" class="" value="${head.areaNumber }"/></td>
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
	<table class="display" id="history">	
		<thead>		
			<tr>
						<th style="width:1px">No</th>
						<th style="width:80px">入库时间</th>
						<th style="width:120px">入库单号</th>
						<th style="width:80px">入库数量</th>
						<th style="width:80px">入库件数</th>
						<th style="width:55px">包装方式</th>
						<th style="width:60px">库位编号</th>	
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
