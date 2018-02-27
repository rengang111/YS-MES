<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>期初库存--实际库存修正</title>
<script type="text/javascript">

	$(document).ready(function() {

		foucsInit();
		$(".num") .blur(function(){
			$(this).val(floatToCurrency($(this).val()));
		});
		//设置光标项目
		$("#attribute1").focus();
		
		$("#submit").click(function() {

			//if ($("#price\\.supplierid").val() == "") {

				//alert("请输入期初值。");	

				//$("#attribute1").focus();

				//return;

			//}

			$("#submit").attr("disabled", true);

			$.ajax({
				async:false,
				type : "POST",
				url : "${ctx}/business/storage?methodtype=quantityOnHandAdd",
				data : $('#material').serialize(),// 要提交的表单
				success : function(d) {
					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
					parent.reload();
					parent.layer.close(index); //执行关闭	
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					alert(XMLHttpRequest.status);							

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

	<form:form modelAttribute="formModel" method="POST" 
	 id="material" name="material"  autocomplete="off">

		<form:hidden path="material.recordid" />
		<form:hidden path="material.materialid"  value="${formModel.material.materialid}"/>
		<form:hidden path="material.quantityeditflag"  value="0" /><!-- 0:修正未确认 -->

		<fieldset>
			<legend> 实际库存修正</legend>
			<table class="form">
				<tr>
					<td width="100px" class="label"><label>物料编号：</label></td>
					<td width="150px">${formModel.material.materialid}</td>

					<td width="100px" class="label"><label>物料名称：</label></td>
					<td>${formModel.material.materialname}</td>
				</tr>
			</table>	

			<legend Style="margin: 10px 0px 0px 0px"> 库存</legend>
			<table class="form">
				<tr>
					<td class="label" width="100px" >计量单位：</td>
					<td width="50px" >${formModel.material.unit}</td>
				
					<td class="label" width="100px" >当前库存：</td>
					<td width="100px" style="text-align: right;">${formModel.material.quantityonhand}</td>
					
					<td class="label" width="100px" >修正后库存：</td>
					<td><form:input path="material.quantityonhand" class="required num attribute1" /></td>
					
				</tr>
			</table>
		</fieldset>

		<fieldset class="action">
			<button type="submit" id="submit" class="DTTT_button">保存</button>
		</fieldset>

	</form:form>
</div>
</body>
</html>
