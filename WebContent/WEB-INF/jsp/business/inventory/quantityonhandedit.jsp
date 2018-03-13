<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>期初库存--实际库存(编辑)</title>
<script type="text/javascript">

	$(document).ready(function() {

		
		
		foucsInit();
		$(".num") .blur(function(){
			$(this).val(floatToCurrency($(this).val()));
		});
		//设置光标项目
		$("#attribute1").focus();



	});
	

	function doSaveFn() {
		
		var recordId = $('#material\\.recordid').val();
		var qantity = $('#material\\.quantityonhand').val();
		var origin = '${formModel.material.quantityonhand}';
		$('#invetoryHistory\\.quantity').val(qantity);
		$('#invetoryHistory\\.originquantity').val(origin);
		
		$("#doSave").attr("disabled", true);
		
		$('#material').attr("action", "${ctx}/business/storage?methodtype=quantityOnHandAdd");
		$('#material').submit();
	}
	
</script>

</head>
<body class="noscroll">

<div id="layer_main">

	<form:form modelAttribute="formModel" method="POST" 
	 id="material" name="material"  autocomplete="off">

		<form:hidden path="material.recordid" />
		<form:hidden path="material.materialid"  value="${formModel.material.materialid}"/>
		<form:hidden path="material.quantityeditflag"  value="0" /><!-- 0:修正未确认 -->
		<form:hidden path="invetoryHistory.materialid" value="${formModel.material.materialid}"/>
		<form:hidden path="invetoryHistory.quantity" />
		<form:hidden path="invetoryHistory.originquantity" />
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
				
					<td class="label" width="100px" >当前实际库存：</td>
					<td width="100px" style="text-align: right;">${formModel.material.quantityonhand}</td>
					
						<td class="label" width="100px" >修正后库存：</td>
						<td><form:input path="material.quantityonhand" class="required num attribute1" />
						</td>
					
				</tr>
			</table>
		</fieldset>

		<fieldset style="text-align: right;">
			<button type="button" id="doSave"  class="DTTT_button" 
				onclick="doSaveFn();">保存</button>		
		</fieldset>

	</form:form>
</div>
</body>
</html>
