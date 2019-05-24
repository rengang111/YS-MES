<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>订单挪用----新建</title>
<script type="text/javascript">

var thisCount = 0;

	$(document).ready(function() {
		
		$("#divert\\.divertquantity").focus(function(){
			$(this).val(floatToNumber($(this).val()));
		   	$(this).select();
		});
		
		$("#divert\\.divertquantity") .blur(function(){
			
			var num = $(this).val();// 		
			var oldQty = currencyToFloat('${order.quantity}');
			
			var checkedNum = checkNumber(num);			
			if(checkedNum == false){
				$().toastmessage('showWarningToast', "请输入有效数字。");
				$(this).val('0');
				return;
			}
			
			var thisNum = currencyToFloat(num);
			if(thisNum > oldQty){
				$().toastmessage('showWarningToast', "领料数量不能大于原订单数量。");
				$(this).val(floatToNumber(oldQty));
				return;
			}
			
			$(this).val(floatToNumber(num));
					
		});
		
		$("#return").click(function() {

					//alert(999);

					var index = parent.layer
							.getFrameIndex(window.name); //获取当前窗体索引

					parent.layer.close(index); //执行关闭

		});
		
		
		$("#submit").click(function() {
			
			if ($("#divert\\.divertfromysid").val() == "") {	
				$().toastmessage('showWarningToast', "请输入耀升编号。");
				$("#divert\\.divertfromysid").focus();
				return;
			}
			if ($("#divert\\.thisreductionqty").val() == "") {	
				$().toastmessage('showWarningToast', "请输入减少数量。");
				$("#divert\\.thisreductionqty").focus();
				return;
			}
			
			if ($("#divert\\.divertquantity").val() == "") {	
				$().toastmessage('showWarningToast', "请输入挪用数量。");
				$("#divert\\.divertquantity").focus();
				return;
			}

			$("#submit").attr("disabled", true);

			var PIId = '${order.PIId}';
			var actionUrl = "${ctx}/business/order?methodtype=insertDivertOrder&PIId="+PIId;
			
			$.ajax({
				async:false,
				type : "POST",
				url : actionUrl,
				data : $('#orderForm').serialize(),// 要提交的表单
				success : function(d) {

					var retValue = d['message'];
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
					parent.documentaryAjax(PIId);	//挪用订单				
					window.parent.document.getElementById('dingwei').scrollIntoView();
					parent.layer.close(index); //执行关闭	
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					alert(XMLHttpRequest.status);							
					//alert(XMLHttpRequest.readyState);							
					//alert(textStatus);							
					//alert(errorThrown);							

					if (XMLHttpRequest.status == "800") {
						alert("800"); //请不要重复提交！
					} else {
						alert("发生系统异常，请再试或者联系系统管理员。"); 
					}
					//关闭窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引						
					//parent.reloadSupplier();
					parent.layer.close(index); //执行关闭					

				}
			});
			
		});

	});
	
</script>

</head>
<body class="noscroll">
<div id="layer_main">

	<form:form modelAttribute="orderForm" method="POST" 
	 id="orderForm" name="orderForm"  autocomplete="off">

		<form:hidden path="divert.recordid" />
		<input type="hidden" id="oldQuantity" />		

		<fieldset>
			<legend> 当前利用订单</legend>

			<table class="form">

				<tr>
					<td width="80px" class="label"><label>耀升编号：</label></td>
					<td width="150px">${order.YSId}
						<form:hidden path="divert.divertoysid"  value="${order.YSId}" />
						<form:hidden path="divert.diverttopiid"  value="${order.PIId}" /></td>
						
					<td width="80px" class="label"><label>产品编号：</label></td>
					<td width="120px">${order.materialId}</td>
					<td width="100px" class="label"><label>订单数量：</label></td>
					<td width=""><span class="font16">${order.quantity}</span></td>
				</tr>
				<tr>
					<td class="label"><label>产品名称：</label></td>
					<td colspan="5">${order.materialName}</td>
				</tr>

			</table>
		</fieldset>
		<fieldset>
			<legend> 被挪用订单</legend>
			<table class="form" >
				<tr>
					<td width="80px" class="label"><label>耀升编号：</label></td>
					<td width="150px" >
						<form:input path="divert.divertfromysid" 
							class="required " style="width: 130px;"/>
						<form:hidden path="divert.divertfrompiid" /></td>
						
					<td width="80px" class="label">产品编号：</td>
					<td width="120px"><span id="productId"></span></td>
					
					<td width="100px" class="label">订单数量：</td>
					<td width=""><span class="font16" id="quantity"></span></td>
				</tr>
				<tr>
					<td class="label"><label>产品名称：</label></td>
					<td colspan="5"><span id="productName"></span></td>
				</tr>
				<tr>
					<td class="label" width="80px" >挪用品名：</td>
					<td colspan="5">
						<form:input path="divert.shortname" 
							class="required long" /></td>
					
				</tr>
				<tr>					
					<td class="label" width="80px" ><label>挪用数量：</label></td>
					<td>
						<form:input path="divert.divertquantity" class="required num" /></td>
					<td class="label" width="80px" >本次减少数：</td>
					<td colspan="3">
						<form:input path="divert.thisreductionqty" class="required num" /></td>
				</tr>
			</table>
		</fieldset>

		<fieldset class="action">
			<button type="button" id="return" class="DTTT_button">关闭</button>
			<button type="button" id="submit" class="DTTT_button">保存</button>
		</fieldset>

	</form:form>

</div>
</body>
<script type="text/javascript">

	$("#divert\\.divertfromysid").autocomplete({
		
		source : function(request, response) {
			//alert(888);
			$.ajax({
				type : "POST",
				url : "${ctx}/business/order?methodtype=getOrderDetailForDivert",
				dataType : "json",
				data : {
					YSId : request.term
				},
				success : function(data) {
					//alert(777);
					response($.map(
						data.data,
						function(item) {
							//alert(item.viewList)
							return {
								label : item.YSId + ' | ' + item.materialId +' | ' + item.materialName,
								value : item.YSId,
								id : item.materialId,
								name : item.materialName,
								piid : item.PIId,
								materialId : item.materialId,
								quantity : item.quantity
								
							}
						}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
					//alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},

		select : function(event, ui) {	
			$("#divert\\.divertfrompiid").val(ui.item.piid);
			$("#productId").text(ui.item.materialId);
			$("#productName").text(ui.item.name);
			$("#quantity").text(ui.item.quantity);

		},

		minLength : 8,
		autoFocus : false,
		width: 500,
	});
</script>
</html>
