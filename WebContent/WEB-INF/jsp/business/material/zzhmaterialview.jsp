<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>H类自制品基本数据-查看</title>

<script type="text/javascript">

$(document).ready(function() {
	
	$("#goBack").click(function() {
		var url = "${ctx}/business/zzmaterial";
		location.href = url;		
	});
	
	$("#doEdit").click(function() {	
		var materialId=$('#price\\.materialid').val();
		$('#ZZMaterial').attr("action", "${ctx}/business/zzmaterial?methodtype=editH&materialId="+materialId);
		location.href = url;
	});
});

</script>
</head>

<body>
<div id="container">
<div id="main">
	
<form:form modelAttribute="ZZMaterial" method="POST" 
	id="ZZMaterial" name="ZZMaterial"   autocomplete="off">
		
	<form:hidden path="price.recordid" value=""/>
	
<fieldset>
	<legend>人工成本</legend>

	<table class="form">
		<tr>
			<td class="label" style="width: 120px;">产品编号：</td>
			<td style="width: 150px;">${material.materialId }
				<form:hidden path="price.materialid" value="${material.materialId }"/></td>
								
			<td class="label" style="width: 120px;"><label>产品名称：</label></td>
			<td style="width: 250px;"><span id="materialname">${material.materialName }</span></td>
			<td class="label" style="width: 120px;"><label>计量单位：</label></td>
			<td>&nbsp;<span id="unit">${material.dicName }</span></td>
	</table>
	</fieldset>
	
	<fieldset>
		<table class="form" style="text-align: center;">
			<thead>
				<tr>
					<td style="width:200px">作业人数</td>
					<td style="width:200px">每小时产量</td>
					<td style="width:200px">每小时工价</td>
					<td>单位产品工价</td>
				</tr>
			</thead>		
			<tbody>
				<tr>
					<td>${price.peoplenumber }</td>
					<td>${price.houryield }</td>				
					<td>${price.hourprice }</td>
					<td>${price.laborprice }</td>				
				</tr>			
			</tbody>
		</table>
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">	
		<button type="button" id="doEdit" class="DTTT_button">编辑</button>				
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	
</form:form>
</div>
</div>
</body>
</html>
