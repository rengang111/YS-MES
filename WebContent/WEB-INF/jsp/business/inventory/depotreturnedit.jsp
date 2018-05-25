<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>仓库退货-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	$(document).ready(function() {
				
		$("#depotReturn\\.remarks").val(replaceTextarea('${depot.remarks }'));

		$("#doUpdate").click(function() {
		
			var url =  "${ctx}/business/depotReturn?methodtype=depotRentunUpdate";

			$('#formModel').attr("action", url);
			$('#formModel').submit();
		});

		$("#doDelete").click(function() {
			if(confirm("删除后不能恢复，确定要删除吗")){
				var recordid = $('#depotReturn\\.recordid').val();
				var url = '${ctx}/business/depotReturn?methodtype=depotRentunDelete&recordid='+recordid;
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
			
		<form:hidden path="depotReturn.recordid" value="${depot.recordId }"/>
		<form:hidden path="depotReturn.materialid" value="${depot.materialId }"/>
		
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
					<td class="label" width="100px"><label>退货数量：</label></td>
					<td><form:input path="depotReturn.returnquantity" class="num" value="${depot.returnQuantity }" /> </td>
						
					<td class="label" width="100px"><label>物料编号：</label></td>					
					<td width="150px">${depot.materialId }</td>
									
					<td class="label" width="100px"><label>物料名称：</label></td>					
					<td>${ depot.materialName }</td>						
				</tr>
				<tr>
					<td class="label">退货日期：</td>
					<td>${ depot.returnDate }</td>
					<td class="label">申请人：</td>
					<td colspan="3">${ depot.LoginName }</td>
				</tr>
				<tr style="height:100px">
					<td class="label" width="100px">退货事由：</td>
					<td colspan="5">
						<form:textarea path="depotReturn.remarks" rows="5" cols="80" value="${depot.remarks }" /></td>
				</tr>									
			</table>
			
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doUpdate" class="DTTT_button">保存</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>			
			
	</form:form>

</div>
</div>
</body>	
</html>
