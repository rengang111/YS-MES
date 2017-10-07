<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>进料检验--检验报告</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
		
	$(document).ready(function() {
		$("#quantity").hide();
		//设置耀升编号
		var ysid = '${arrived.YSId }';
		$("#inspect\\.ysid").val(ysid);
		$("#process\\.checkdate").val(shortToday());
		
		$("#inspect\\.report").val(replaceTextarea('${arrived.report}'));
		$("#process\\.managerfeedback").val(replaceTextarea('${arrived.managerFeedback}'));
		$("#process\\.gmfeedback").val(replaceTextarea('${arrived.gmFeedback}'));
		$("#inspect\\.lossandcisposal").val(replaceTextarea('${arrived.LossAndCisposal}'));
		$("#inspect\\.memo").val(replaceTextarea('${arrived.memo}'));
		
		$("#goBack").click(
				function() {
					var materialId = '${arrived.materialId }';
					var url = "${ctx}/business/receiveinspection?keyBackup="+materialId;
					location.href = url;		
				});
		
		$("#process\\.checkresult").change(function() {
			var val = $(this).val();
			if(val == '030'){
				$("#quantity").show();
			}else{
				$("#quantity").hide();
				
			}
		});
		
		$('select').css('width','100px');
	});
	
	function doInsert() {

		$('#formModel').attr("action", "${ctx}/business/receiveinspection?methodtype=insert");
		$('#formModel').submit();
	}
	
	function doInsertPM() {


		$('#formModel').attr("action", "${ctx}/business/receiveinspection?methodtype=insertPM");
		$('#formModel').submit();
	}
	
	function doInsertGM() {


		$('#formModel').attr("action", "${ctx}/business/receiveinspection?methodtype=insertGM");
		$('#formModel').submit();
	}
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<form:hidden path="inspect.ysid" value=""/>
	<form:hidden path="inspect.parentid" value=""/>
	<form:hidden path="inspect.subid" value=""/>
	<form:hidden path="inspect.arrivedate" value="${arrived.arriveDate }"/>
	<input type="hidden" id=report value="${arrived.report }" />
	
	<fieldset>
		<legend> 到货信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">进料检报告编号：</td>	
				<td width="200px">
					<form:input path="inspect.inspectionid" class="read-only" /></td>
				<td class="label">质检员：</td>					
				<td width="200px">
					<form:input path="process.checkerid" value="${userName }" class="read-only" /></td>										
				<td class="label" width="100px">报检日期：</td>
				<td>
					<form:input path="process.checkdate" value="" class="read-only"/></td>
			</tr>
			<tr> 				
				<td class="label" width="100px">到货登记：</td>	
				<td width="200px">${arrived.arrivalId }
					<form:hidden path="inspect.arrivalid" value="${arrived.arrivalId }"/></td>
				<td class="label">合同编号：</td>					
				<td width="200px">${arrived.contractId }
					<form:hidden path="inspect.contractid" value="${arrived.contractId }"/></td>										
				<td class="label" width="100px">供应商：</td>
				<td>${arrived.supplierName }
					<form:hidden path="inspect.supplierid" value="${arrived.supplierId }"/></td>
			</tr>
			<tr> 
				<td class="label" width="100px">到货数量：</td>
				<td>${arrived.quantity }</td>				
				<td class="label" width="100px">物料编号：</td>			
				<td>${arrived.materialId }
					<form:hidden path="inspect.materialid" value="${arrived.materialId }"/></td>
				<td class="label" width="100px">物料名称：</td>
				<td colspan="3">${arrived.materialName }</td>
			</tr>
												
		</table>		
	</fieldset>
	<div style="clear: both"></div>

	<fieldset class="action" style="text-align: right;">
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>		
	
	<fieldset>
		<legend> 检验报告</legend>
		<table class="form" id="table_form">
			
			<tr>
				<td rowspan="3" width="700">
					<form:textarea path="inspect.report" rows="7" cols="80" /></td>

			</tr>
			<tr>
				<td>质检员检验结果：
					<form:select path="process.checkresult">
							<form:options items="${resultList}" 
								itemValue="key" itemLabel="value"/></form:select>
					<br><br><br><div id="quantity">合格数量：<form:input path="inspect.quantity" value="${arrived.quantity }" class="num short" /></div>
								</td>

			</tr>
			<tr>
				<td style="vertical-align: bottom;" >
					<button type="button" id="submit11"  onclick="doInsert();"
						class="DTTT_button" style="margin-bottom: 5px;">确认提交</button></td>

			</tr>
												
		</table>
		
	</fieldset>
	<fieldset>
		<legend>品质部经理</legend>
		<table class="form" id="table_form2">
			<tr>
				<td rowspan="3" width="700">
					<form:textarea path="process.managerfeedback" rows="7" cols="80" /></td>
			</tr>
			<tr>
				<td>品质部经理批示： 
					<form:select path="process.managerresult" style="width: 120px;">							
					<form:options items="${resultList}" 
						itemValue="key" itemLabel="value" /></form:select> </td>
			</tr>
			<tr>
				<td style="vertical-align: bottom;" >
					<button type="button" id="submit12"  onclick="doInsertPM();"
						class="DTTT_button" style="margin-bottom: 5px;">确认提交</button></td>
			</tr>
												
		</table>
	</fieldset>
	<fieldset>
		<legend>总经理</legend>
		<table class="form" id="table_form2">
			<tr>
				<td rowspan="3" width="700">
				<form:textarea path="process.gmfeedback" rows="7" cols="80" /></td>
			</tr>
			<tr>
				<td>总经理确认：
					<button type="button" id="submit13"  onclick="doInsertGM();"
						class="DTTT_button" style="margin-bottom: 5px;">确认提交</button></td>			
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
				<td>
				<form:textarea path="inspect.lossandcisposal" rows="7" cols="80" /></td>								
			</tr>												
		</table>
</fieldset>
<fieldset>
		<legend>备注</legend>
		<table class="form" id="table_form2">
			<tr>
				<td>
				<form:textarea path="inspect.memo" rows="7" cols="80" /></td>								
			</tr>												
		</table>
</fieldset>
</form:form>

</div>
</div>
</body>

</html>
