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
<title>订单评审-录入</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var GsalesTax = '0';//销售税
	var Grebate =currencyToFloat('${bomPlan.rebateRate}');//退税率
	var GexRate = currencyToFloat('${bomPlan.exchangeRate}');//汇率
	var GproductCost = currencyToFloat('${bomPlan.productCost}');//产品成本
	var Gprice = currencyToFloat('${bomPlan.price}');//销售单价
	var GmanageCost = currencyToFloat('${bomPlan.managementCost}');//经管费
	var GmaterialCost = currencyToFloat('${bomPlan.materialCost}');//材料成本
	var Gquantity = currencyToFloat('${bomPlan.quantity}');//销售数量

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

		
		//销售税=（销售单价-材料成本）*17%		
		//GsalesTax = (Gprice - GmaterialCost) * 0.17;		
		//var fsalesTax = floatToCurrency(GsalesTax);
		//$('#review\\.salestax').val(fsalesTax);
		
		//退税=材料成本*退税率/1.17 退税率默认为15%
		var vrate = $('#review\\.rebaterate').val();
		var frate = currencyToFloat(vrate)/100;
		Grebate = GmaterialCost * frate / 1.17;		
		var rebate = floatToCurrency(Grebate);
		//alert('Grebate:'+rebate+"---GmaterialCost:"+GmaterialCost)
		
		countCost();//计算成本
		
		$('#review\\.rebate').val(rebate);
		//$('#review\\.rebaterate').val('15');//初始值
		
		//汇率变化
		$("#review\\.exchangerate").change(function() {
			
			//销售单价（原币）= 销售单价 * 汇率
			//单位销售毛利=销售单价（原币）-产品成本-销售税+退税
			//单位核算毛利=销售单价（原币）-产品成本-销售税+退税-经管费=单位销售毛利-经管费

			GexRate = currencyToFloat($(this).val());			

			countCost();
			
			$(this).val(float4ToCurrency(GexRate));
			
		});

		//退税率变化
		$("#review\\.rebaterate").change(function() {
			//alert(2222)
			//退税=材料成本*退税率/1.17
			var frate = currencyToFloat($(this).val());
			Grebate = GmaterialCost * (frate/100) / 1.17;
			
			countCost();
			
			//var vrate = Math.round(100*(3.4441))/100
			
			var vrebate = floatToCurrency(Grebate);
			$('#review\\.rebate').val(vrebate);//退税

		});

		$("#return").click(
				function() {
					var url = "${ctx}/business/bom";
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					
			$('#reviewForm').attr("action", "${ctx}/business/orderreview?methodtype=insert");
			$('#reviewForm').submit();
		});
				
		
		$("input:text").focus (function(){
		    $(this).select();
		});
		

	});
	
	function countCost(){
		
		//
		var frmb  = GexRate * Gprice;
		
		//销售税=（销售单价-材料成本）*17%		
		GsalesTax = (frmb - GmaterialCost) * 0.17;	
		
		var fsales = frmb - GproductCost - GsalesTax + Grebate;//单位销售毛利
		var fadjust = fsales - GmanageCost;
		
		var ftotalSal = Gquantity * fsales;
		var ftotalAdj = Gquantity * fadjust;

		var vrmb = floatToCurrency(frmb);
		var vsales = floatToCurrency(fsales);
		var vadjust = floatToCurrency(fadjust);
		var vtotalSal = floatToCurrency(ftotalSal);
		var vtotalAdj = floatToCurrency(ftotalAdj);	
		var vsalesTax = floatToCurrency(GsalesTax);
		

		$('#review\\.rmbprice').val(vrmb);//原币
		$('#review\\.salestax').val(vsalesTax);//销售税
		$('#review\\.salesprofit').val(vsales);//单位销售毛利
		$('#review\\.adjustprofit').val(vadjust);//单位核算毛利
		$('#review\\.totalsalesprofit').val(vtotalSal);//销售毛利
		$('#review\\.totaladjustprofit').val(vtotalAdj);//核算毛利
		
	}
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="reviewForm" method="POST"
		id="reviewForm" name="reviewForm"  autocomplete="off">
		
		
		<fieldset>
			<legend> 订单基本信息</legend>
			<table class="form" id="table_form" width="100%" style="margin-top: -4px;">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:150px;">${bomPlan.YSId}
						<form:hidden path="review.ysid" value="${bomPlan.YSId}"/></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;">${bomPlan.materialId}
						<form:hidden path="review.materialid" value="${bomPlan.materialId}"/></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td>&nbsp;${bomPlan.materialName}</td>
				</tr>
				<tr>
					<td class="label"><label>PI号：</label></td>
					<td>${bomPlan.PIId}</td>

					<td class="label"><label>订单交期：</label></td>
					<td>${bomPlan.deliveryDate}</td>
						
					<td class="label"><label>汇率：</label></td>
					<td>
						<form:input path="review.exchangerate" class="short cash"  value="${bomPlan.exchangeRate}"/></td>
				</tr>							
			</table>
			</fieldset>
			<fieldset>
			<table class="form" id="table_form2" width="100%" style="margin-top: -3px;height:70px">
				
				<tr style="vertical-align: bottom;">
					<td class="td-center" width="150px"><label>BOM做成日</label></td>	
					<td class="td-center" width="150px"><label>BOM编号</label></td>
					<td class="td-center" width="150px"><label>材料成本</label></td>
					<td class="td-center" width="150px"><label>人工</label></td>
					<td class="td-center" width="150px"><label>经管费率</label></td>
					<td class="td-center"  width="150px"><label>经管费</label></td>	
					<td class="td-center" width="150px"><label>核算成本</label></td>
				</tr>	
				<tr>			
					<td class="td-center">${bomPlan.planDate}</td>
					<td class="td-center">${bomPlan.bomId}
						<form:hidden path="review.bomid" value="${bomPlan.bomId}"/></td>
					<td class="td-center">${bomPlan.materialCost}</td>
					<td class="td-center">${bomPlan.laborCost}</td>
					<td class="td-center">${bomPlan.managementCostRate}</td>
					<td class="td-center">${bomPlan.managementCost}</td>
					<td class="td-center">${bomPlan.totalCost}</td>
				</tr>								
			</table>
			
			<table class="form" id="table_form3" width="100%" style="margin-top: -3px;height:70px">
				
				<tr style="vertical-align: bottom;">
					<td class="td-center" width="150px"><label>产品成本</label></td>	
					<td class="td-center" width="150px"><label>销售单价</label></td>
					<td class="td-center" width="150px"><label>币种</label></td>
					<td class="td-center" width="150px"><label>原币单价</label></td>	
					<td class="td-center" width="150px"><label>销售税</label></td>
					<td class="td-center" width="150px"><label>退税率</label></td>
					<td class="td-center" width="150px"><label>退税</label></td>
				</tr>	
				<tr>
					<td class="td-center"><label id="totalCost">${bomPlan.productCost}</label></td>
					<td class="td-center"><label id="price">${bomPlan.price}</label></td>
					<td class="td-center">${bomPlan.currency}</td>
					<td class="td-center">
						<form:input path="review.rmbprice" class="read-only short cash" /></td>
					<td class="td-center">
						<form:input path="review.salestax" class="read-only short cash" /></td>
					<td class="td-center">
						<form:input path="review.rebaterate" class="mini cash"  value="${bomPlan.rebateRate}"/>％</td>
					<td class="td-center">
						<form:input path="review.rebate" class="read-only short cash" /></td>
				</tr>								
			</table>
			
			<table class="form" id="table_form2" width="100%" style="margin-top: -3px;height:70px">
				
				<tr style="vertical-align: bottom;">
					<td class="td-center" width="150px"><label>单位销售毛利</label></td>	
					<td class="td-center" width="150px"><label>单位核算毛利</label></td>
					<td class="td-center" width="150px"><label>订单数量</label></td>
					<td class="td-center"  colspan="2"><label>销售毛利</label></td>	
					<td class="td-center"  colspan="2"><label>核算毛利</label></td>
				</tr>	
				<tr>			
					<td class="td-center">
						<form:input path="review.salesprofit" class="read-only cash short" value=""/></td>
					<td class="td-center">
						<form:input path="review.adjustprofit" class="read-only cash short" value="" /></td>
					<td class="td-center">${bomPlan.quantity}</td>
					<td class="td-center" colspan="2">
						<form:input path="review.totalsalesprofit" class="read-only cash middle" value=""/></td>
					<td class="td-center" colspan="2">
						<form:input path="review.totaladjustprofit" class="read-only cash middle" value=""/></td>
				</tr>								
			</table>

	</fieldset>
	
	<fieldset>
		<legend> 相关库存</legend>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display" >
			<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-left" width="100px">产品编号</th>
				<th class="dt-left" width="200px">产品描述</th>
				<th class="dt-left" >数量</th>
			</tr>
			</thead>
			<tfoot>
				<tr>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</tfoot>
		<tbody>	
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					
				</tr>
			
		</tbody>
	</table>
	</div>
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
