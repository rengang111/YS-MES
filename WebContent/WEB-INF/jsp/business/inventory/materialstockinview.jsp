<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>直接出库申请-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	
	$(document).ready(function() {
		
		//日期
		$("#storage\\.requisitiondate").val(shortToday());
		
		//ajax(scrollHeight);
		
		$("#storage\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/storage?methodtype=materialStockinMainInit";
					location.href = url;
				});

		
		$("#insert").click(
				function() {

			//$('#formModel').attr("action", "${ctx}/business/storage?methodtype=materialRequisitionInsert");
			//$('#formModel').submit();
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
				<td class="label" width="100px">入库单编号：</td>					
				<td width="300px">${storage.receiptId }</td>
														
				<td width="100px" class="label">入库日期：</td>
				<td width="150px">${storage.checkInDate }</td>
				
				<td width="100px" class="label">仓管员：</td>
				<td>${storage.stockInName }</td>
			</tr>

			<tr> 				
				<td class="label" width="100px">物料编号：</td>					
				<td width="200px">${storage.materialId }</td>
														
				<td width="100px" class="label">物料名称：</td>
				<td colspan="3">${storage.materialName }</td>
				
			</tr>
			<tr> 				
				<td class="label">入库数量：</td>
				<td colspan="5">${storage.quantity }</td>
				
			</tr>
			<tr> 				
				<td class="label">备注信息：</td>					
				<td colspan="5">
					<pre>${storage.remarks }</pre></td>
				
			</tr>										
		</table>
	</fieldset>
	<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >编辑</a>
		<a class="DTTT_button DTTT_button_text" id="doDelete" >删除</a>
		<a class="DTTT_button DTTT_button_text" id="doPrint" >打印</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
</form:form>

</div>
</div>
</body>

</html>
