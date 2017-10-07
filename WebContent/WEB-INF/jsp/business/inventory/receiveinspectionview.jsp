<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt'%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>

<head>
<title>进料检验--查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

		
	$(document).ready(function() {

		//设置耀升编号
		var ysid = '${arrived.YSId }';
		$("#inspect\\.ysid").val(ysid);
		$("#process\\.checkresult").val('${arrived.result}');		
		
		$("#goBack").click(
				function() {
					var materialId = '${arrived.materialId }';
					var url = "${ctx}/business/receiveinspection?keyBackup="+materialId;
					location.href = url;		
				});
		
		
		$("#doEdit").click(
				function() {
					$('#formModel').attr("action", "${ctx}/business/receiveinspection?methodtype=updateInit");
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
	<form:hidden path="inspect.ysid" value=""/>
	
	<fieldset>
		<legend> 到货信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">进料检报告编号：</td>	
				<td width="200px">${arrived.inspectionId }
					<form:hidden path="inspect.inspectionid" value="${arrived.inspectionId }"/>
					<form:hidden path="inspect.arrivalid" value="${arrived.arrivalId }"/></td>
				<td class="label">合同编号：</td>					
				<td width="200px">${arrived.contractId }
					<form:hidden path="inspect.contractid" value="${arrived.contractId }"/></td>										
				<td class="label" width="100px">供应商：</td>
				<td>${arrived.supplierName }
					<form:hidden path="inspect.supplierid" value="${arrived.supplierId }"/></td>
			</tr>
			<tr> 
				<td class="label" width="100px">检验数量：</td>
				<td>${arrived.quantity }
					<form:hidden path="inspect.quantity" value="${arrived.quantity }"/></td>				
				<td class="label" width="100px">物料编号：</td>			
				<td>${arrived.materialId }
					<form:hidden path="inspect.materialid" value="${arrived.materialId }"/></td>
				<td class="label" width="100px">物料名称：</td>
				<td>${arrived.materialName }</td>
			</tr>
			<tr> 
				<td class="label" width="100px">检验结果：</td>
				<td>${arrived.resultName }</td>
				<td class="label" width="100px">检验日期：</td>
				<td colspan="3">${arrived.checkDate }</td>
			</tr>
												
		</table>
		
	</fieldset>
	
<div style="clear: both"></div>

<fieldset class="action" style="text-align: right;">
	<button type="button" id="doEdit" class="DTTT_button">修改</button>
	<button type="button" id="goBack" class="DTTT_button">返回</button>
</fieldset>		
	
	<fieldset>
		<legend> 检验报告</legend>
		<table class="form" id="table_form">
			
			<tr>
				<td style="width:700px;height:200px;vertical-align: text-top;"> 
					<pre>${ arrived.report}</pre></td>
			</tr>
												
		</table>
		
	</fieldset>
	<fieldset>
		<legend>品质部经理</legend>
		<table class="form" id="table_form2">
			<tr>
				<td style="width:700px;height:200px;vertical-align: text-top;"> 
					<pre>${ arrived.managerFeedback}</pre>
				</td>
			</tr>
												
		</table>
	</fieldset>
	<fieldset>
		<legend>总经理</legend>
		<table class="form" id="table_form2">
			<tr>
				<td style="width:700px;height:200px;vertical-align: text-top;"> 
					<pre>${ arrived.gmFeedback}</pre></td>
			</tr>
			<tr>
				<td></td>			
			</tr>
			<tr>
				<td></td>
			</tr>
												
		</table>
</fieldset>
<fieldset>
		<legend>损失评估及处理</legend>
		<table class="form" id="table_form2">
			<tr>
				<td style="width:700px;height:200px;vertical-align: text-top;"> 
					<pre>${ arrived.LossAndCisposal}</pre></td>							
			</tr>												
		</table>
</fieldset>
<fieldset>
		<legend>备注</legend>
		<table class="form" id="table_form2">
			<tr>
				<td style="width:700px;height:200px;vertical-align: text-top;"> 
					<pre>${ arrived.memo}</pre></td>													
			</tr>												
		</table>
</fieldset>
</form:form>

</div>
</div>
</body>

</html>
