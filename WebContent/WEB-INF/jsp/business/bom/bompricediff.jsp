<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>报价BOM-基础BOM-对比</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
function quotationDetail() {

	var bomId='${product.materialId}';
	var table = $('#quotaionTable').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var scrollHeight = $(document).height() - 200; 
	
	var t2 = $('#quotaionTable').DataTable({
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
        "paging"    : false,
        "pageLength": 50,
		"scrollY":scrollHeight,
		"scrollCollapse":false,
        "ordering"  : false,
		dom : '<"clear">rt',			
		"columns" : [ 
		        	{"className":"dt-body-center"
				}, {
				}, {								
				}, {"className":"td-center"
				}, {"className":"td-right"				
				}, {"className":"td-right"				
				}, {"className":"td-center"				
				}, {"className":"td-right"				
				}, {"className":"td-right"				
				}			
			]		
		
	}).draw();
	
	t2.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t2.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});

	t2.on('order.dt search.dt draw.dt', function() {
		t2.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();
	
}//ajax()供应商信息

	$(document).ready(function() {
				
		quotationDetail();
					
		$("#goBack").click(function() {
			var materialId = '${product.materialId}';
			var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;
			location.href = url;		
		});
	
	});
	
</script>

</head>
<body>
<div id="container" style="width:90%">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main" style="width:90%">

	<form:form modelAttribute="bomForm" method="POST"
		id="bomForm" name="bomForm"  autocomplete="off">
		
		
		<fieldset>
			<table class="form" id="table_form" style="margin-top: -4px;">
				<tr>
					<td class="label" width="100px">产品编号：</td>				
					<td width="120px">${difflistHead.productId}</td>					

					<td class="label" width="100px">BOM编号：</td>					
					<td width="120px">${difflistHead.bomId}</td>
					
					<td class="label" width="100px">产品名称：</td>
					<td>${difflistHead.productName }</td>
				
				
				</tr>
												
			</table>
		
	</fieldset>

	<fieldset>
		<div class="list" style="margin-top: -4px;">
		
			<table id="quotaionTable" class="display" >
				<thead>				
				<tr>
					<th width="30px">No</th>
					<th class="dt-center" >物料编码</th>
					<th class="dt-center" style="width: 80px;background: bisque;">客户报价<br>供应商</th>
					<th class="dt-center" style="width: 60px;background: bisque;">客户报价<br>用量</th>
					<th class="dt-center" style="width: 80px;background: bisque;">客户报价<br>单价</th>
					<th class="dt-center" style="width: 80px;background: burlywood;">基础BOM<br>供应商</th>
					<th class="dt-center" style="width: 60px;background: burlywood;">基础BOM<br>用量</th>
					<th class="dt-center" style="width: 80px;background: burlywood;">基础BOM<br>单价</th>
				<!-- <th class="dt-center" width="80px">报价合计</th>
					<th class="dt-center" width="80px">BOM合计</th> -->	
					<th class="dt-center" width="60px">对比差值<br>报价-BOM</th>
				</tr>
				</thead>
				<tbody>

					<c:forEach var="detail" items="${difflist}" varStatus='status' >
					
						<tr>
							<td></td>
							<td>${detail.materialId}</td>
							<td style="background: bisque;">${detail.supplierId}</td>
							<td style="background: bisque;">${detail.quantity}</td>
							<td style="background: bisque;">${detail.price}</td>
							<td style="background: burlywood;">${detail.baseSupplier}</td>
							<td style="background: burlywood;">${detail.baseUnit}</td>
							<td style="background: burlywood;">${detail.basePrice}</td>
							<td><span id="diff${status.index}"></span></td>
						</tr>
						
						<script type="text/javascript">

							var index = '${status.index}';
							var price = '${detail.price}';
							var basePrice = '${detail.basePrice}';
							var unit      = '${detail.quantity}';
							var baseUnit  = '${detail.baseUnit}';

							var baseOnly  = '${detail.baseOnly}';
							var priceFlag  = '${detail.priceFlag}';
							var unitFlag   = '${detail.unitFlag}';
							
							price     = currencyToFloat(price);
							basePrice = currencyToFloat(basePrice);
							unit      = currencyToFloat(unit);
							baseUnit  = currencyToFloat(baseUnit);
						
							if(baseOnly == '1'){
								price = 0;
								unit = 0;
							}
							
							if(baseOnly == '0'){
								basePrice = 0;
								baseUnit = 0;
							}
							
							if(priceFlag == '0')
								basePrice = price;
							
							if(unitFlag == '0')
								baseUnit = unit;
							
							var diff = price * unit - basePrice * baseUnit;
							
							if(diff > 0){
								$('#diff'+index).addClass('text-red');
							}else{
								$('#diff'+index).addClass('text-green');							
							}
							
							$('#diff'+index).html(floatToCurrency(diff));
						
						</script>
		
					</c:forEach>
									
				</tbody>
			</table>
		</div>
	</fieldset>
		
</form:form>

</div>
</div>
</body>
	
</html>
