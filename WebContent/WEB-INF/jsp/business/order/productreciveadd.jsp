<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>成品领料</title>
<script type="text/javascript">

var thisCount = 0;

	$(document).ready(function() {
		
		//设置允许领料最大数
		var dbThisQty = $('#recive\\.receivequantity').val();
		var orderQty = '${order.quantity}';
		dbThisQty = currencyToFloat(dbThisQty);
		orderQty = currencyToFloat(orderQty);
		
		thisCount = dbThisQty + orderQty;
		
		//设置选择项目的选中项	
		$("#recive\\.receivedate").val(shortToday());
		$("#recive\\.receivedate").attr('readonly', "true");
		$("#recive\\.receivedate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
		}); 
		
		
		$("#recive\\.receivequantity").focus(function(){
			$(this).val(floatToNumber($(this).val()));
		   	$(this).select();
		});
		
		$("#recive\\.receivequantity") .blur(function(){
			
			var num = $(this).val();// 		
			//var oldQty = currencyToFloat('${order.quantity}');// 	
			var oldQty = thisCount;
			
			var checkedNum = checkNumber(num);			
			if(checkedNum == false){
				$().toastmessage('showWarningToast', "请输入有效数字。");
				$(this).val('');
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

			if ($("#recive\\.receivetopiid").val() == "") {	
				$().toastmessage('showWarningToast', "请输入耀升编号。");
				$("#recive\\.receivetopiid").focus();
				return;
			}
			
			if ($("#recive\\.receivequantity").val() == "") {	
				$().toastmessage('showWarningToast', "请输入领料数量。");
				$("#recive\\.receivequantity").focus();
				return;
			}


			$("#submit").attr("disabled", true);
						
			$.ajax({
				async:false,
				type : "POST",
				url : "${ctx}/business/order?methodtype=addProductRecive",
				data : $('#orderForm').serialize(),// 要提交的表单
				success : function(d) {

					var retValue = d['message'];
					if (retValue != "failure") {
						//alert(retValue);
						parent.$('#handle_status').val('1');
					} else {
						// 从体验度来说，不要成功提示似乎操作更流畅。
						 parent.$('#handle_status').val('2');
					}
					

					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引	
					parent.productReciveAjax();//刷新供应商单价信息
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

		<form:hidden path="recive.recordid" />
		<input type="hidden" id="oldQuantity" />		

		<fieldset>
			<legend> 成品领料</legend>

			<table class="form">

				<tr>
					<td width="80px" class="label"><label>耀升编号：</label></td>
					<td width="150px">
						<form:hidden path="recive.ysid"  value="${order.YSId}" />
						${order.YSId}</td>
						
					<td width="80px" class="label"><label>产品编号：</label></td>
					<td width="120px">${order.materialId}</td>
					<td width="80px" class="label"><label>订单数量：</label></td>
					<td width=""><span class="font16">${order.quantity}</span></td>
				</tr>
				<tr>
					<td class="label"><label>产品名称：</label></td>
					<td colspan="5">${order.materialName}</td>
				</tr>

			</table>

			<table class="form">
				<tr>
					<td class="label" width="80px" >领料数量：</td>

					<td width="150px" ><form:input path="recive.receivequantity" class="required num" style="width: 130px;"/></td>
					
					<td class="label" width="80px" ><label>领料部门：</label></td>
					<td><form:input path="recive.receiveunit" class="required " /></td>
				</tr>
				<tr>		
					<td class="label" width="80px" ><label>领料人：</label></td>
					<td  width="100px" ><form:input path="recive.requester" class="required " /></td>
					
					<td class="label" width="80px" ><label>领料时间：</label></td>
					<td><form:input path="recive.receivedate" class="required read-only" /></td>
				</tr>
				<tr>		
					<td class="label" width="80px" ><label>领料备注：</label></td>
					<td colspan="3"><form:input path="recive.remarks" class="required long" /></td>
					
				</tr>
			</table>
		</fieldset>

		<fieldset class="action">
			<button type="button" id="return" class="DTTT_button">关闭</button>
			<button type="submit" id="submit" class="DTTT_button">保存</button>
		</fieldset>

	</form:form>

</div>
</body>
</html>
