<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>订单取消-查看</title>
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
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/order?methodtype=orderCancelSearchInit";
					location.href = url;
		});

		
		$("#doEdit").click(
				function() {

			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=orderCancelEdit");
			$('#orderForm').submit();
		});	
		

		$("#doDelete").click(
				function() {
			
			if(confirm("删除后不能恢复，确定要删除吗")){

				$('#orderForm').attr("action", "${ctx}/business/order?methodtype=orderCancelDelete");
				$('#orderForm').submit();
			
			}
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
				<td colspan="3">${order.materialName }</td>
			</tr>
			<tr> 				
				<td class="label">退货数量：</td>
				<td>${order.cancelQuantity }</td>
				
				<td class="label">退货后订单数量：</td>
				<td>${order.quantity }</td>
														
				<td width="100px" class="label">退货日期：</td>
				<td>${order.cancelDate}</td>
				<!-- 
				<td width="100px" class="label">退货人：</td>
				<td>${order.modifyPerson}</td>
				 -->
			</tr>
			<tr> 				
				<td class="label">退货事由：</td>					
				<td colspan="7"><pre>${order.remarks}</pre></td>
				
			</tr>										
		</table>
</fieldset>
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;width: 50%;float: right;">
		<a class="DTTT_button" id="doEdit" >编辑</a>
		<a class="DTTT_button" id="doDelete" >删除</a>
		<a class="DTTT_button goBack" id="goBack" >返回</a>
	</div>
</form:form>

</div>
</div>
</body>

</html>
