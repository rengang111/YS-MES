<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>仓库退货-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	$(document).ready(function() {

		$("#doEdit").click(function() {
		
			var receiptId = $('#stockin\\.receiptid').val();
			var url = "${ctx}/business/depotReturn?methodtype=depotRentunEdit&inspectionReturnId="+receiptId;

			location.href = url;	
		});

		$("#doDelete").click(function() {
			if(confirm("删除后不能恢复，确定要删除吗")){
				var receiptId = $('#stockin\\.receiptid').val();
				var url = '${ctx}/business/depotReturn?methodtype=depotRentunDelete&receiptId='+receiptId;
				location.href = url;
				
			}	
		});
		

		$("#goBack").click(function() {
			var url = '${ctx}/business/depotReturn';
			location.href = url;	
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
			
		<form:hidden path="stockin.receiptid" value="${depot.receiptId }"/>
		
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
				<tr id="">		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="150px">${depot.YSId }</td>
									
					<td class="label" width="100px"><label>合同编号：</label></td>					
					<td>${ depot.contractId }</td>
					
					<td class="label" width="100px"><label>供应商：</label></td>					
					<td>（${ depot.supplierId }）${ depot.supplierName }</td>						
				</tr>
				<tr id="">	
					<td class="label" width="100px"><label>取消数量：</label></td>
					<td>${ depot.quantity } </td>
						
					<td class="label" width="100px"><label>物料编号：</label></td>					
					<td width="150px">${depot.materialId }</td>
									
					<td class="label" width="100px"><label>物料名称：</label></td>					
					<td>${ depot.materialName }</td>						
				</tr>
				<tr>
					<td class="label">取消日期：</td>
					<td>${ depot.checkInDate }</td>
					<td class="label">操作员：</td>
					<td colspan="3">${ depot.LoginName }</td>
				</tr>
				<tr style="height:100px">
					<td class="label" width="100px">取消事由：</td>
					<td colspan="5">${ depot.remarks }</td>
				</tr>									
			</table>
			
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doEdit"   class="DTTT_button">编辑</button>
		<button type="button" id="doDelete" class="DTTT_button">删除</button>
		<button type="button" id="goBack"   class="DTTT_button">返回</button>
	</fieldset>			
			
	</form:form>

</div>
</div>
</body>	
</html>
