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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>

<title>装配品及非完全成品库存订单-新建</title>

<script type="text/javascript">



$(document).ready(function() {

	$("#goBack").click(
		function() {
			url = "${ctx}/business/order";
			location.href = url;
	});
	
	$("#submitReturn").click(
		function() {
			if(inputCheck()){
				doSubmitReturn();
			}else{
				$().toastmessage('showWarningToast', "请填写完整后,再保存。");
			}
	});
	
	$("#doEdit").click(
		function() {
			var YSId = '${order.YSId}';
			url = "${ctx}/business/zporder?methodtype=edit&YSId="+YSId;
			location.href = url;
	});	
	
	$("#doApprove").click(
		function() {
			var recordId = '${order.recordId}';
			var orderId = '${order.orderId}';
			url = "${ctx}/business/zporder?methodtype=approve&recordId="+recordId+"&orderId="+orderId;
			location.href = url;
	});
	
	$("#doReview").click(
		function() {
			var YSId = '${order.YSId }';
			var url = '${ctx}/business/orderreview?methodtype=create&YSId=' + YSId;

			location.href = url;

	});
	
	$("#doPurchasePlan").click(
		function() {
			var YSId = '${order.YSId }';
			var url = '${ctx}/business/orderreview?methodtype=create&YSId=' + YSId;

			location.href = url;

	});
});

</script>
</head>

<body class="panel-body">
<div id="container">
<div id="main">
<fieldset>
	<legend>装配品及非完全成品库存订单</legend>

	<table id="baseinfo" class="form" >		
		<tr>
			<td class="label" style="width: 100px;">耀升编码：</td>
			<td colspan="5">${order.YSId }
				<input type="hidden" id="orderid" value="${order.YSId }"/></td>
		</tr>
		<tr>						
			<td class="label" style="width: 100px;"><label>产品名称：</label></td>
			<td colspan="5">${order.materialName }</td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">产品编码：</td>
			<td>${order.materialId }</td>
			<td class="label" style="width: 100px;"><label>分类编码：</label></td>
			<td colspan="3">${order.categoryName }</td>
		</tr>
		<tr>						
			<td class="label" style="width: 100px;"><label>下单日期：</label></td>
			<td >${order.orderDate }</td>
			<td class="label" style="width: 100px;"><label>完成日期：</label></td>
			<td>${order.deliveryDate }</td>
			<td class="label" style="width: 100px;"><label>订单数量：</label></td>
			<td>${order.quantity }</td>
		</tr>		
	</table>
	</fieldset>
	<fieldset>
	<legend> 库存制作原因</legend>
	<table class="form" >
		<tr>
			<td class="td-left"><pre>${order.YSKorderTarget }</pre></td>
		</tr>
	</table>
	
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">	
		<button type="button" id="doEdit" class="DTTT_button">编辑</button>
		<button type="button" id="doApprove" class="DTTT_button">审批</button>	
		<button type="button" id="doReview" class="DTTT_button">订单评审</button>
		<button type="button" id="doPurchasePlan" class="DTTT_button">生成订单采购方案</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
</div>
</div>
</body>
</html>
