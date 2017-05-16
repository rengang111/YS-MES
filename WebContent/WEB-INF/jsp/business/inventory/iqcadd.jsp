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
		<legend> 到货信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">进料检报告编号：</td>					
				<td colspan="7"></td>

			</tr>
			<tr> 				
				<td class="label">采购合同编号：</td>					
				<td></td>
														
				<td class="label">供应商：</td>
				<td colspan="5"></td>
			</tr>
			<tr> 				
				<td class="label" width="100px">ERP编号：</td>
				<td width="150px"></td>
				<td class="label" width="100px">产品品名：</td>
				<td></td>
				<td class="label" width="100px">数量：</td>
				<td width="100px"></td>
				<td class="label" width="100px">检验结果：</td>
				<td width="100px"></td>
			</tr>										
		</table>
</fieldset>
	

		<table class="include" style="width: 98%;">
			<tr>
				<td><span class="tablename">检验报告</span>
					<button type="button" id="submit1" class="DTTT_button">确认提交</button></td>
			</tr>
			<tr>
				<td class="td-left" >
				<form:textarea path="arrival.arrivalid" rows="7" cols="70" /></td>

			</tr>
												
		</table>
<fieldset>
		<legend>品质部经理</legend>
		<table class="form" id="table_form2">
			<tr>
				<td class="label" width="100px">品质部经理判断：</td>
				<td width="150px"></td>
				<td class="label" width="100px">品质部经理批示：</td>
				<td><button type="button" id="submit1" class="DTTT_button">确认提交</button></td>
			</tr>
			<tr>
				<td colspan="4">
				<form:textarea path="arrival.arrivalid" rows="7" cols="70" /></td>
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
