<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>期初库存--实际库存修正(确认)</title>
<script type="text/javascript">

	$(document).ready(function() {

	});
	

	function doEditFn() {
		var recordId = $('#material\\.recordid').val();
		var url = '${ctx}/business/storage?methodtype=quantityOnHandEdit'+'&recordId='+recordId;

		location.href = url;
	}
	

	function doConfirmFn() {

		var recordId = $('#material\\.recordid').val();
		var url = '${ctx}/business/storage?methodtype=quantityOnHandConfirm&recordId=' + recordId+'&recordId='+recordId;

		$.ajax({
			
			async:false,
			type : "POST",
			url : url,
			data : $('#material').serialize(),// 要提交的表单
			success : function(d) {
				
				$('#doConfirm').hide();
				$('#confirmSts').text('已确认');
				
			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {				

			}
		});
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
							
					<td class="label" width="100px" >修正后库存：</td>
					<td width="150px">${formModel.material.quantityonhand}
						<span id="confirmSts" style="color: red;font-style: italic;/*! font-weight: bold; */">（未确认）</span></td>
					<td></td>
				</tr>
			</table>
		</fieldset>

		<fieldset class="action" style="text-align: right;">
			<button type="button" id="doEdit" class="DTTT_button"
				onclick="doEditFn();">重新修改</button>
			<button type="button" id="doConfirm" class="DTTT_button" 
				onclick="doConfirmFn();return false;">确认</button>
		</fieldset>

	</form:form>
</div>
</body>
</html>
