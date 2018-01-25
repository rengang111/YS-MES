<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<!-- 直接出库申请-查看 -->
<title></title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	
	$(document).ready(function() {
		
		//日期
		$("#requisition\\.requisitiondate").val(shortToday());
		
		//ajax(scrollHeight);
		
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/requisition?methodtype=materialRequisitionMain";
					location.href = url;
				});

		
		$("#doEdit").click(
				function() {

			var recordId = '${requisition.recordId }';
			$('#formModel').attr("action", "${ctx}/business/requisition?methodtype=materialRequisitionEdit"+"&recordId="+recordId);
			$('#formModel').submit();
		});
				
		foucsInit();		
		
	});

	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
	
	<input type="hidden" id="makeType" value="${makeType }" />
	
	<fieldset>
		<legend> 直接领料申请单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">申请单编号：</td>					
				<td width="200px">${requisition.requisitionId }</td>
														
				<td width="100px" class="label">申请日期：</td>
				<td width="150px">${requisition.requisitionDate }</td>
				
				<td width="100px" class="label">申请人：</td>
				<td>${requisition.requisitionUser }</td>
			</tr>

			<tr> 				
				<td class="label" width="100px">物料编号：</td>					
				<td width="200px">${requisition.materialId }</td>
														
				<td width="100px" class="label">物料名称：</td>
				<td colspan="3">${requisition.materialName }</td>
				
			</tr>
			<tr> 				
				<td class="label">申请数量：</td>
				<td>${requisition.quantity }</td>
				
				<td width="100px" class="label">领料用途：</td>
				<td colspan="3">${requisition.usedType }</td>
				
			</tr>
			<tr> 				
				<td class="label">申请事由：</td>					
				<td colspan="5">
					<pre>${requisition.remarks }</pre></td>
				
			</tr>										
		</table>
	</fieldset>
	<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="doEdit" >编辑</a>
		<a class="DTTT_button DTTT_button_text" id="doDelete" >删除</a>
		<a class="DTTT_button DTTT_button_text" id="doPrint" onclick="doPrint();return false;" >打印</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
</form:form>

</div>
</div>
</body>

<script  type="text/javascript">

function doPrint(){
	
	var headstr = "<html><head><title></title></head><body>";  
	var footstr = "</body>";
	$("#DTTT_container").hide();
	window.print();
	$("#DTTT_container").show();
	
}
</script>
</html>
