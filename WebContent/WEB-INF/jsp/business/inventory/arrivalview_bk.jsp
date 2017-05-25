<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-到货查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

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
					}, {
					}, {
					}, {
					}, {"className":"dt-body-center"
					}, {"className":"dt-body-right"	
					}, {"className":"dt-body-right"	
					}, {"className":"dt-body-right"
					}, {"className":"dt-body-right"
					}
				]
			
		}).draw();
						
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

		ajax();
		
		$("#return").click(
				function() {
					var url = "${ctx}/business/arrival";
					location.href = url;		
				});
		
		$("#update").click(
				function() {

			$('#formModel').attr("action", "${ctx}/business/arrival?methodtype=updateInit");
			$('#formModel').submit();
		});		
		
	});
	
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
	
	<fieldset>
		<legend> 到货登记</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px"><label>到货编号：</label></td>					
				<td>${arrival.arrivalId }</td>
														
				<td width="100px" class="label">到货日期：</td>
				<td>${arrival.arriveDate }</td>
				
				<td width="100px" class="label">仓管员：</td>
				<td>${arrival.userId }</td>
			</tr>
			<tr> 				
				<td class="label"><label>供应商：</label></td>					
				<td colspan="5">${arrival.supplierId }</td>			
			</tr>
			<tr> 				
				<td class="label"><label>供应商名称：</label></td>					
				<td colspan="5">${arrival.supplierName }</td>		
			</tr>
										
		</table>
</fieldset>

<fieldset>
	<legend>到货详情</legend>
	<div class="list">
	
	<table id="example" class="display" >
		<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="100px">合同编号</th>
				<th class="dt-center" width="175px">物料编号</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="30px">单位</th>
				<th class="dt-center" width="60px">本次<br/>到货数量</th>
				<th class="dt-center" width="60px">累计<br/>到货数量</th>
				<th class="dt-center" width="60px">合同<br/>总数量</th>
				<th class="dt-center" width="60px">剩余数量</th>
			</tr>
		</thead>		
		<tbody>
			<c:forEach var='list' items='${arrivalList}' varStatus='status'>	
				<tr>
					<td></td>
					<td>${list.contractId}</td>
					<td>${list.materialId}</td>
					<td>${list.materialName}</td>
					<td>${list.unit}</td>
					<td>${list.quantity}</td>
					<td>${list.recodeSum}</td>
					<td><span id=total${status.index}></span></td>
					<td><span id=surplus${status.index}></span></td>
				</tr>
				<script type="text/javascript">
					var index = '${status.index}';
					var quantity = currencyToFloat( '${list.quantity}' );
					var recode = currencyToFloat( '${list.recodeSum}' );
					var total = currencyToFloat( '${list.total}' );
					
					var surplus = total - recode;					

					$('#surplus'+index).html(surplus);
					$('#total'+index).html(floatToNumber(total));
				
				</script>
					
			</c:forEach>
		</tbody>	
	</table>
</div>
</fieldset>
<div style="clear: both"></div>

<fieldset class="action" style="text-align: right;">
	<button type="button" id="return" class="DTTT_button">返回</button>
	<!-- <button type="button" id="update" class="DTTT_button">修改</button> -->
</fieldset>		
	
</form:form>

</div>
</div>
</body>
</html>
