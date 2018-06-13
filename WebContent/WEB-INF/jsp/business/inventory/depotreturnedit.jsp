<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>仓库退货-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	$(document).ready(function() {
		
		var quantity = '${depot.quantity }';
		quantity = currencyToFloat(quantity) * (-1);//转换为正数
		quantity = floatToCurrency(quantity);
		$('#oldQuantity').val(quantity);
		$('#detail\\.quantity').val(quantity);
		
		$("#stockin\\.remarks").val(replaceTextarea('${depot.remarks }'));

		$("#doUpdate").click(function() {
		
			var oldQuantity = $('#oldQuantity').val();
			
			var quantity = $('#detail\\.quantity').val();			
			quantity = currencyToFloat(quantity);
			if(quantity<=0){
				$().toastmessage('showWarningToast', "取消数量必须大于零。");
				return;
			}
			var url =  "${ctx}/business/depotReturn?methodtype=depotRentunUpdate"+"&oldQuantity="+oldQuantity;
			

			$('#formModel').attr("action", url);
			$('#formModel').submit();
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
		<form:hidden path="detail.materialid" value="${depot.materialId }"/>
		<input type="hidden" id="oldQuantity" value="${depot.quantity }" >
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
					<td><form:input path="detail.quantity" class="num" value="${depot.quantity }" /> </td>
						
					<td class="label" width="100px"><label>物料编号：</label></td>					
					<td width="150px">${depot.materialId }</td>
									
					<td class="label" width="100px"><label>物料名称：</label></td>					
					<td>${ depot.materialName }</td>						
				</tr>
				<tr>
					<td class="label">取消日期：</td>
					<td>${ depot.returnDate }</td>
					<td class="label">操作员：</td>
					<td colspan="3">${ depot.LoginName }</td>
				</tr>
				<tr style="height:100px">
					<td class="label" width="100px">取消事由：</td>
					<td colspan="5">
						<form:textarea path="stockin.remarks" rows="5" cols="80" /></td>
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
