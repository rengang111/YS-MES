<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>自制品基本数据-查看</title>
</head>

<body class="panel-body">
<div id="container">
<div id="main">
	
<form:form modelAttribute="ZZMaterial" method="POST" style='padding: 0px; margin: 0px 10px;' 
	id="ZZMaterial" name="ZZMaterial"   autocomplete="off">
		
	<form:hidden path="price.materialid" value="${price.materialId}"/>
	
<fieldset style="margin-top: -16px;">
	<legend style='margin: 20px 10px -10px 0px;'>基本信息</legend>

	<table class="form" width="100%">		
		<tr>
			<td class="label" style="width: 100px;">产品编码：</td>
			<td style="width: 120px;">${price.materialId }</td>
								
			<td class="label" style="width: 100px;"><label>产品名称：</label></td>
			<td colspan="3">${price.materialId }</td>
			
		<tr>
			<td class="label"><label>计量单位：</label></td>
			<td style="width: 100px;">${price.unit }</td>											
			<td class="label"><label>管理费率：</label></td>
			<td>${price.managementCostRate }</td>	
			<td class="label" >自制品单价（合计）：</td>
			<td>${price.totalPrice }</td>					
		</tr>
	</table>
	</fieldset>
<fieldset>
<div class="list">
	<table id="example" class="display" width="100%">
	
		<thead>
		<tr>
			<th style="width:30px">No</th>
			<th style="width:80px">原材料编码</th>
			<th>原材料名称</th>
			<th style="width:70px">用料净重量</th>
			<th style="width:60px">损耗2%</th>
			<th style="width:60px">用料重量</th>
			<th style="width:60px">单价/公斤</th>
			<th style="width:80px">材料价/克 </th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="raw" items="${detail}" varStatus="status">		
				<tr>
					<td>${status.index }</td>
					<td>${raw.rawMaterialId }</td>								
					<td>${raw.materialName }</td>
					<td>${raw.netWeight }</td>							
					<td>${raw.wastage }</td>
					<td>${raw.weight }</td>
					<td>${raw.kgPrice }</td>
					<td>${raw.materialPrice }</td>				
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	</div>
	</fieldset>
	<fieldset style="margin-top: -15px;">
	<legend style="margin: 10px 0px 0px 0px"> 人工成本</legend>
	<table class="form" width="100%" style="text-align: center;margin-top: -5px;">
	
		<thead>
		<tr>
			<td style="width:200px">出模数</td>
			<td style="width:200px">出模时间(秒)</td>
			<td style="width:200px">每小时产量</td>
			<td style="width:200px">每小时工价</td>
			<td>单位产品工价</td>
		</tr>
		</thead>		
		<tbody>
			<tr>
				<td>${price.cavitiesNumber }</td>							
				<td>${price.time }</td>	
				<td>${price.hourYield }</td>				
				<td>${price.hourPrice }</td>
				<td>${price.laborPrice }</td>				
			</tr>			
		</tbody>
	</table>
	<legend style="margin: 10px 0px 0px 0px"> 电耗</legend>
	<table class="form" width="100%" style="text-align: center;">
	
		<thead>
		<tr>
			<td style="width:200px">机器功率(KW)</td>
			<td style="width:200px">每小时耗电</td>
			<td style="width:200px">每度电价</td>
			<td>单位产品电价</td>
		</tr>
		</thead>		
		<tbody>
			<tr>
				<td>${price.kilowatt }</td>							
				<td>${price.hourPower }</td>
				<td>${price.kwPrice }</td>
				<td>${price.powerPrice }</td>				
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

<script type="text/javascript">

	//Form序列化后转为AJAX可提交的JSON格式。
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};



$(document).ready(function() {
	
	$('#example').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		//"pagingType" : "full_numbers",
		//"scrollY"    : height,
        //"scrollCollapse": true,
        "paging"    : false,
        "pageLength": 50,
        "ordering"  : false,
		"searching" : false,

		dom : '<"clear">lt',
		
		"columns" : [ 
		        	{"className":"dt-body-center"
				}, {
				}, {								
				}, {				
				}, {"className":"dt-body-right"				
				}, {"className":"dt-body-right"				
				}, {"className":"dt-body-right"				
				}, {"className":"dt-body-right"				
				}			
			]
		
	}).draw();
	
	$("#goBack").click(
		function() {
			var url = "${ctx}/business/zzmaterial";
			location.href = url;	
		});
	
	$("#doEdit").click(
			function() {	
				var materialId=$('#price\\.materialid').val();
				$('#ZZMaterial').attr("action", "${ctx}/business/zzmaterial?methodtype=edit&materialId="+materialId);
				$('#ZZMaterial').submit();

		});

});

</script>
</body>
</html>
