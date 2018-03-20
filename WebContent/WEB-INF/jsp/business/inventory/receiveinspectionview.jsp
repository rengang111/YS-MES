<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-进料报检-查看</title>
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
					}, {"className":"dt-body-center"
					}, {"className":"td-right"
					}
				],
			
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
		
		$("#goBack").click(
				function() {
					var contractId='${arrived.contractId }';
					var url = "${ctx}/business/receiveinspection?keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#doEdit").click(
				function() {
					var keyBackup = $('#keyBackup').val();				
					$('#formModel').attr("action", "${ctx}/business/receiveinspection?methodtype=updateInit"+"&keyBackup="+keyBackup);
					$('#formModel').submit();
		});		
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/receiveinspection?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
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

	<form:hidden path="inspect.ysid" value=""/>
	<form:hidden path="inspect.parentid" value=""/>
	<form:hidden path="inspect.subid" value=""/>
	<form:hidden path="inspect.arrivedate" value="${arrived.arriveDate }"/>
	<input type="hidden" id=report value="${arrived.report }" />
	<input type="hidden" id="keyBackup" value="${keyBackup }" />
	<form:hidden path="inspect.contractid" value="${arrived.contractId }"/>
	<fieldset>
		<legend> 报检信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">到货登记：</td>	
				<td width="200px">${arrived.arrivalId }
					<form:hidden path="inspect.arrivalid" value="${arrived.arrivalId }"/></td>
												
				<td class="label" width="100px">供应商：</td>
				<td colspan="3">${arrived.supplierName }
					<form:hidden path="inspect.supplierid" value="${arrived.supplierId }"/></td>
			</tr>
			<tr> 				
				<td class="label" width="100px">进料检报告编号：</td>	
				<td width="200px">${arrived.inspectionId }</td>
				<td class="label">质检员：</td>					
				<td width="200px">${arrived.checkerId }</td>										
				<td class="label" width="100px">报检日期：</td>
				<td>${arrived.checkDate }</td>
			</tr>
												
		</table>		
	</fieldset>
	<div style="clear: both"></div>
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doEdit" class="DTTT_button">编辑</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	<fieldset style="margin-top: -25px;">
		<div class="list">	
		<table id="example" class="display" >
			<thead>				
				<tr>
					<th style="width:1px">No</th>
					<th class="dt-center" width="120px">物料编号</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" width="30px">单位</th>
					<th class="dt-center" width="60px">合同数量</th>
					<th class="dt-center" width="80px">本次到货</th>
					<th class="dt-center" width="60px">检验结果</th>
					<th class="dt-center" width="60px">合格数量</th>
				</tr>
			</thead>
			
		<tbody>
			<c:forEach var="list" items="${material}" varStatus='status' >
				
					<tr>
						<td></td>
						<td>${list.materialId }
							<form:hidden path="inspectList[${status.index}].materialid" value="${list.materialId }"/></td>
						<td><span>${list.materialName }</span></td>
						<td><span>${list.unit }</span></td>
						<td><span>${list.contractQuantity }</span></td>
						<td><span>${list.quantity }</span></td>					
						<td>${list.checkResult }</td>
						<td><span>${list.quantityQualified }</span></td>
					</tr>
					<script type="text/javascript">
							var index = '${status.index}';
							//var quantity = currencyToFloat('${list.quantity}');
							//var contractStorage = currencyToFloat('${list.contractStorage}');
							//var surplus = quantity - contractStorage;
							
							//$('#surplus'+index).html(floatToCurrency( surplus ))
					</script>
				
			</c:forEach>
			
		</tbody>
	</table>
	</div>
	</fieldset>		
	<fieldset>
		<legend> 检验报告</legend>
		<table class="form" id="table_form">		
			<tr>
				<td style="width:700px;height:200px;vertical-align: text-top;"> 
					<pre>${arrived.report }</pre></td>
			</tr>												
		</table>
		
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
