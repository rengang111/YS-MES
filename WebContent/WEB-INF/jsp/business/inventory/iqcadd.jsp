<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>进料检验</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

		
	$(document).ready(function() {

		//设置光标项目
		//$("#attribute1").focus();
		//$("#order\\.piid").attr('readonly', "true");
		
		$("#return").click(
				function() {
					var url = "${ctx}/business/arrival";
					location.href = url;		
				});
		
		$("#insert").click(
				function() {

			$('#formModel').attr("action", "${ctx}/business/arrival?methodtype=insert");
			$('#formModel').submit();
		});
		
		
		$('select').css('width','100px');
	});
	
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="tmpMaterialId" />
	
	<fieldset>
		<legend> 检验报告</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">报告编号：</td>	
				<td width="200px"></td>
				<td class="label">合同编号：</td>					
				<td width="200px"></td>										
				<td class="label" width="100px">供应商：</td>
				<td></td>
			</tr>
			<tr> 
				<td class="label" width="100px">检验数量：</td>
				<td></td>				
				<td class="label" width="100px">物料编号：</td>			
				<td></td>
				<td class="label" width="100px">物料名称：</td>
				<td colspan="3"></td>
			</tr>
			<tr>
				<td colspan="5" rowspan="3">
					<form:textarea path="arrival.arrivalid" rows="7" cols="80" /></td>

			</tr>
			<tr>
				<td colspan="6">检验结果：</td>

			</tr>
			<tr>
				<td colspan="6" style="vertical-align: bottom;" ><button type="button" id="submit1" class="DTTT_button" style="margin-bottom: 5px;">确认提交</button></td>

			</tr>
												
		</table>
		
	</fieldset>
	<fieldset>
		<legend>品质部经理</legend>
		<table class="form" id="table_form2">
			<tr>
				<td rowspan="3" width="700">
					<form:textarea path="arrival.arrivalid" rows="7" cols="80" /></td>
			</tr>
			<tr>
				<td>品质部经理判断：<%-- 
					<form:select path="material.purchasetype" style="width: 120px;">							
					<form:options items="${DisplayData.purchaseTypeList}" 
						itemValue="key" itemLabel="value" /></form:select> --%></td>
			</tr>
			<tr>
				<td><button type="button" id="submit1" class="DTTT_button">确认提交</button></td>
			</tr>
												
		</table>
	</fieldset>
	<fieldset>
		<legend>总经理</legend>
		<table class="form" id="table_form2">
			<tr>
				<td class="label" width="100px">品质部经理批示：</td>
				<td><button type="button" id="submit1" class="DTTT_button">确认提交</button></td>
			</tr>
			<tr>
				<td colspan="2">
				
				<form:textarea path="arrival.arrivalid" rows="7" cols="70" /></td>
								
			</tr>
												
		</table>
</fieldset>
<fieldset>
		<legend>损失评估及处理</legend>
		<table class="form" id="table_form2">
			<tr>
				<td>
				<form:textarea path="arrival.arrivalid" rows="7" cols="70" /></td>								
			</tr>												
		</table>
</fieldset>
<fieldset>
		<legend>备注</legend>
		<table class="form" id="table_form2">
			<tr>
				<td>
				<form:textarea path="arrival.arrivalid" rows="7" cols="70" /></td>								
			</tr>												
		</table>
</fieldset>
<div style="clear: both"></div>

<fieldset class="action" style="text-align: right;">
	<button type="button" id="return" class="DTTT_button">返回</button>
	<button type="button" id="insert" class="DTTT_button">保存</button>
</fieldset>		
	
</form:form>

</div>
</div>
</body>

</html>
