<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>H类自制品基本数据-新建</title>

<script type="text/javascript">

$(document).ready(function() {

	autocomplete();
	
	//设置光标项目		

	
	$("#goBack").click(function() {
			var url = "${ctx}/business/zzmaterial";
			location.href = url;		
		});
	
	$("#submitReturn").click(
			function() {
				if(inputCheck()){
					doSubmitReturn();
				}

		});
	
	//计算人工成本
	$(".labor").change(function() {
		
		var vpeople = $("#price\\.peoplenumber").val();//作业人数
		var vhyield = $("#price\\.houryield").val();//每小时产量
		var vhprice = $("#price\\.hourprice").val();//每小时工价
		
		var fpeople = currencyToFloat(vpeople);
		var fhyield = currencyToFloat(vhyield);
		var fhprice = currencyToFloat(vhprice);
		//alert('fpeople'+fpeople+'fhyield'+fhyield+'fhprice'+fhprice)
		//单位产品工价 = 作业人数*每小时工价/每小时产量
		var labor = '0';
		if(fhyield != '0'){
			labor = fpeople * fhprice / vhyield;			
		}
		
		var vlabor = float4ToCurrency(labor);		

		$('#laborprice').html(vlabor);	
		$("#price\\.laborprice").val(vlabor);
		$("#price\\.totalprice").val(vlabor);
				
	});
	

	foucsInit();
	
});


function doSubmitReturn(){
		
	$('#ZZMaterial').attr("action", "${ctx}/business/zzmaterial?methodtype=insertH");
	$('#ZZMaterial').submit();
	
}
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
			<td style="width: 150px;">
				<form:input path="price.materialid" class="required" value="${price.materialId }"/></td>
								
			<td class="label" style="width: 120px;"><label>产品名称：</label></td>
			<td style="width: 250px;"><span id="materialname">${price.materialName }</span></td>
			<td class="label" style="width: 120px;"><label>计量单位：</label></td>
			<td>&nbsp;<span id="unit">${price.dicName }</span></td>
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
				<td><form:input path="price.peoplenumber" class="cash short labor"  value="${price.peopleNumber }" /></td>
				<td><form:input path="price.houryield"    class="cash short labor"  value="${price.hourYield }" /></td>				
				<td><form:input path="price.hourprice"    class="cash short labor"    value="${price.hourPrice }"/></td>
				<td><span id="laborprice">${price.laborPrice }</span>
					<form:hidden path="price.laborprice"   value="${price.laborPrice }"/>
					<form:hidden path="price.totalprice"   value="${price.totalPrice }"/></td>				
			</tr>			
		</tbody>
	</table>

	</fieldset>
	
	<fieldset class="action" style="text-align: right;">	
		<button type="button" id="submitReturn" class="DTTT_button">保存</button>				
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	
</form:form>
</div>
</div>

<script type="text/javascript">

function autocomplete(){
		
	//自制品选择
	$("#price\\.materialid").autocomplete({
		minLength : 2,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/order?methodtype=getMaterialList",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.viewList,
									value : item.materialId,
									id : item.materialId,
									name : item.materialName,
									unit : item.dicName
								}
							}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
					//alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},

		select : function(event, ui) {
			
			//产品名称
			$('#materialname').text(ui.item.name);
			
			//计量单位
			$('#unit').text(ui.item.unit);			
		},

		
	});//自制品选择

}

</script>
<script type="text/javascript">
	window.onunload = function(){ 
	parent.supplierPriceView();//刷新供应商单价信息 
	} 
</script>
</body>
</html>
