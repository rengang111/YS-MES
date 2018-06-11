<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>订单取消-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	$(document).ready(function() {
		
		//日期
		$("#orderCancel\\.canceldate").val(shortToday());		
		
		$("#orderCancel\\.canceldate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
		}); 
		var remark = "${order.remarks}";
		$("#orderCancel\\.remarks").val(replaceTextarea( remark ));
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/order?methodtype=orderCancelSearchInit";
					location.href = url;
		});

		
		$("#update").click(
				function() {

			var quantity = $('#orderCancel\\.cancelquantity').val();
			
			quantity = currencyToFloat(quantity);
			if(quantity<=0){
				$().toastmessage('showWarningToast', "退货数量必须大于零。");
				return;
			}
			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=orderCancelUpdate");
			$('#orderForm').submit();
		});	
		
	});


	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">
	
			
<form:form modelAttribute="orderForm" method="POST"
	id="orderForm" name="orderForm"  autocomplete="off">
	
	<form:hidden path="orderCancel.recordid"  value="${order.cancelRecordId }"/>
	<form:hidden path="orderCancel.ysid"  value="${order.YSId }"/>
	
	<fieldset>
		<legend> 订单退货</legend>
		<table class="form" id="table_form">
		
			<tr> 	
				<td class="label" width="100px">耀升编号：</td>
				<td width="100px">${order.YSId }</td>
				<td class="label" width="100px">产品编号：</td>
				<td width="100px">${order.materialId }</td>
														
				<td width="100px" class="label">产品名称：</td>
				<td>${order.materialName }</td>
			</tr>
			<tr> 				
				<td class="label">退货数量：</td>
				<td><form:input path="orderCancel.cancelquantity" class="num short"  value="${order.cancelQuantity }"/></td>
														
				<td width="100px" class="label">退货日期：</td>
				<td><form:input path="orderCancel.canceldate" class="short read-only" /></td>
				 
				<td width="100px" class="label">退货人：</td>
				<td>
					<form:input path="orderCancel.modifyperson" class="short read-only" value="${userName }" /></td>
				 
			</tr>
			<tr> 				
				<td class="label">退货事由：</td>					
				<td colspan="5">
					<form:textarea path="orderCancel.remarks" rows="2" cols="80"  value="${order.remarks}"/><pre></pre></td>
				
			</tr>										
		</table>
</fieldset>
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;width: 50%;float: right;">
		<a class="DTTT_button" id="update" >保存</a>
		<a class="DTTT_button goBack" id="goBack" >返回</a>
	</div>
</form:form>

</div>
</div>
</body>

</html>
