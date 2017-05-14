<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
<title>客户报价--查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	

function quotationDetail() {

	var bomId='${product.materialId}';
	var table = $('#quotaionTable').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#quotaionTable').DataTable({
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
        "paging"    : false,
        "pageLength": 50,
        "ordering"  : false,
		dom : '<"clear">rt',			
		"columns" : [ 
		        	{"className":"dt-body-center"
				}, {
				}, {								
				}, {				
				}, {"className":"td-right"				
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
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="bomForm" method="POST"
		id="bomForm" name="bomForm"  autocomplete="off">
		
		<form:hidden path="bomPlan.recordid" value="${material.productRecord}"/>
		
		<fieldset>
			<legend>客户报价</legend>
			<table class="form" id="table_form" style="margin-top: -4px;">
				<tr>
					<td class="label">产品名称：</td>				
					<td>（${product.materialId}）${product.materialName }
						<form:hidden path="bomPlan.materialid"  value="${product.materialId}"/></td>					

					<td class="label" width="120px">客户名称：</td>
					<td>（${product.shortName }）${product.customerName }</td>
				</tr>
				<tr>
					<td class="label" width="120px">BOM编号：</td>					
					<td colspan="3">${material.bomId}</td>
				</tr>
												
			</table>
			
			<table class="form" id="table_form2" style="margin-top: 6px;">
				
				<tr>
					<td class="td-center"><label>材料成本</label></td>	
					<td class="td-center"><label>人工成本</label></td>
					<td class="td-center"><label> BOM成本</label></td>
					<td class="td-center"><label>基础成本</label></td>
					<td class="td-center"><label>经管费率</label></td>
					<td class="td-center"><label>核算成本</label></td>
				</tr>	
				<tr>			
					<td class="td-center">${material.materialCost}</td>
					<td class="td-center">${material.laborCost}</td>
					<td class="td-center">${material.bomCost}</td>
					<td class="td-center">${material.productCost}</td>
					<td class="td-center">${material.managementCostRate}%</td>
					<td class="td-center">${material.totalCost}</td>
				</tr>								
			</table>
				
			<table class="form" id="table_form3" style="margin-top: 6px;">
				
				<tr>
					<td class="td-center"><label>报价时间</label></td>	
					<td class="td-center"><label>报价币种</label></td>
					<td class="td-center"><label>换汇</label></td>
					<td class="td-center"><label>报价</label></td>
					<td class="td-center"><label>原币价格</label></td>
					<td class="td-center"><label>退税率</label></td>
					<td class="td-center"><label>退税</label></td>
					<td class="td-center"><label>折扣</label></td>
					<td class="td-center"><label>因子</label></td>
					<td class="td-center"><label>利润率</label></td>
				</tr>	
				<tr>			
					<td class="td-center">${material.planDate}</td>
					<td class="td-center">${material.currency}</td>
					<td class="td-center">${material.exchangeRate}</td>
					<td class="td-center">${material.exchangePrice}</td>
					<td class="td-center">${material.RMBPrice}</td>
					<td class="td-center">${material.rebateRate}%</td>
					<td class="td-center">${material.rebate}</td>
					<td class="td-center">${material.discount}%</td>
					<td class="td-center">${material.commission}%</td>
					<td class="td-center">${material.profitRate}%</td>
				</tr>								
			</table>
	</fieldset>
	
	<div style="margin: -3px 10px 0px 5px;float:right; padding:0px;">
			<button type="button" id="goBack" class="DTTT_button" style="width: 60px;">返回</button>
	</div>
	<fieldset>
		<div class="list" style="margin-top: -4px;">
		
			<table id="quotaionTable" class="display">
				<thead>				
				<tr>
					<th width="1px">No</th>
					<th class="dt-center" width="200px">物料编码</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" width="100px">供应商编号</th>
					<th class="dt-center" width="60px">用量</th>
					<th class="dt-center" width="80px">本次单价</th>
					<th class="dt-center" width="80px">总价</th>
				</tr>
				</thead>
				<tbody>

					<c:forEach var="detail" items="${materialDetail}" varStatus='status' >
					
						<tr>
							<td></td>
							<td>${detail.materialId}</td>
							<td>${detail.materialName}</td>
							<td>${detail.supplierId}</td>
							<td>${detail.quantity}</td>
							<td>${detail.price}</td>
							<td>${detail.totalPrice}</td>
						</tr>
		
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
