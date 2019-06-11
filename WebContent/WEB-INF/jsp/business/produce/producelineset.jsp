<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>生产线设置</title>
<script type="text/javascript">

var thisCount = 0;

	$(document).ready(function() {
						
		$("#return").click(function() {

					//alert(999);

					var index = parent.layer
							.getFrameIndex(window.name); //获取当前窗体索引

					parent.layer.close(index); //执行关闭

		});
		
		
		$("#submit").click(function() {

			

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
			<legend> 生产线设置</legend>

			<table class="form">

				<tr>
					<td width="80px" class="label"><label>耀升编号：</label></td>
					<td width="150px">
						<form:hidden path="plan.ysid"  value="${YSId}" />
						${YSId}</td>
						
					<td width="80px" class="label"><label>生产线：</label></td>
					<td width="120px">${order.materialId}</td>
					<td width="80px" class="label"><label>订单数量：</label></td>
					<td width=""><span class="font16">${order.quantity}</span></td>
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
