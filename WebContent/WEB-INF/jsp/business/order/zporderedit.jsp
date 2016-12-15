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

<title>装配品及非完全成品库存订单-编辑</title>

<script type="text/javascript">

$(document).ready(function() {

	autocomplete();

	//设置光标项目
	$("#order\\.orderid").attr('readonly', "true");
	
	$("#order\\.orderdate").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
		buttonImage: '${ctx}/css/images/details_open.png',
	}); 

	$("#order\\.deliverydate").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
	});

	
	$("#goBack").click(
		function() {
			history.go(-1);		
	});
	
	$("#update").click(
		function() {
			if(inputCheck()){
				doSubmitReturn();
			}else{
				$().toastmessage('showWarningToast', "请填写完整后,再保存。");
			}
	});
	
	
	foucsInit();
});

function doSubmitReturn(){
	
	$('#ZPOrder').attr("action", "${ctx}/business/zporder?methodtype=update");
	$('#ZPOrder').submit();
}

</script>
</head>

<body class="easyui-layout">
<div id="container">
<div id="main">
	
<form:form modelAttribute="ZPOrder" method="POST"  
	id="ZPOrder" name="ZPOrder"   autocomplete="off">
	
	<form:hidden path="order.recordid" value="${order.orderRecordId }"/>
	<form:hidden path="orderDetail.recordid" value="${order.recordId }"/>
	
<fieldset>
	<legend>装配品及非完全成品库存订单</legend>

	<table id="baseinfo" class="form">		
		<tr>
			<td class="label" style="width: 100px;">耀升编码：</td>
			<td colspan="5">${order.YSId }
				<form:hidden path="order.orderid" value="${ysCode }"/>
				<form:hidden path="orderDetail.ysid"  value="${ysCode }"/></td>
		</tr>
		<tr>						
			<td class="label" style="width: 100px;"><label>产品名称：</label></td>
			<td colspan="5">${order.materialName }</td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">产品编码：</td>
			<td>${order.materialId }
				<form:hidden path="orderDetail.materialid"  /></td>
			<td class="label" style="width: 100px;"><label>分类编码：</label></td>
			<td colspan="3">${order.categoryName }</td>
		</tr>
		<tr>						
			<td class="label" style="width: 100px;"><label>下单日期：</label></td>
			<td ><form:input path="order.orderdate" class="required" value="${order.orderDate }"/></td>
			<td class="label" style="width: 100px;"><label>完成日期：</label></td>
			<td><form:input path="order.deliverydate" class="required" value="${order.deliveryDate }"/></td>
			<td class="label" style="width: 100px;"><label>订单数量：</label></td>
			<td><form:input path="orderDetail.quantity" class="required num" value="${order.quantity }"/></td>
		</tr>		
	</table>
	</fieldset>
	<fieldset>
	<legend> 库存制作原因</legend>
	<table class="form" >
		<tr>
			<td class="td-left"><textarea name="order.yskordertarget" rows="7" cols="70" >${order.YSKorderTarget}</textarea></td>
		</tr>
	</table>
	
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">	
		<button type="button" id="update" class="DTTT_button">保存</button>		
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	
</form:form>
</div>
</div>

<script type="text/javascript">

function autocomplete(){
		
	//原材料选择
	$("#orderDetail\\.materialId").autocomplete({
		minLength : 2,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/order?methodtype=getMaterialList",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.viewList,
									value : item.materialId,
									id : item.materialId,
									name : item.materialName,
									category : item.categoryName
								}
							}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},

		select : function(event, ui) {
			
			$('#materialName').text((ui.item.name));
			$('#categoryId').text((ui.item.category));			
		},

		
	});
}

</script>
</body>
</html>
