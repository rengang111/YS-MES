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
		
		var rate = '${bomPlan.managementCostRate}';
		$('#manageRate').html(Number(rate));

		$("#return").click(
				function() {
					var url = "${ctx}/business/bom";
					location.href = url;		
				});
		
		$("#edit").click(
				function() {
					
			var bomId = $('#review\\.bomid').val();
			$('#reviewForm').attr("action", "${ctx}/business/orderreview?methodtype=edit&bomId="+bomId);
			$('#reviewForm').submit();
		});
				
		$("#approve").click(
				function() {
					
			var bomId = $('#review\\.bomid').val();
			$('#reviewForm').attr("action", "${ctx}/business/orderreview?methodtype=approve&bomId="+bomId);
			$('#reviewForm').submit();
		});

	});

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
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td>${bomPlan.YSId}
						<form:hidden path="review.bomid" value="${bomPlan.bomId}"/></td>
								
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td>${bomPlan.materialId}</td>
				
					<td class="label"><label>产品名称：</label></td>				
					<td>${bomPlan.materialName}</td>
				</tr>
				<tr>
					<td class="label"><label>PI号：</label></td>
					<td>${bomPlan.PIId}</td>

					<td class="label"><label>订单交期：</label></td>
					<td>${bomPlan.deliveryDate}</td>
						
					<td class="label"><label>汇率：</label></td>
					<td>${bomPlan.exchangeRate}</td>
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
					<td class="td-center"><span id="manageRate" ></span>％</td>
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
					<td class="td-center">${bomPlan.RMBPrice}</td>
					<td class="td-center">${bomPlan.salesTax}</td>
					<td class="td-center">${bomPlan.rebateRate}％</td>
					<td class="td-center">${bomPlan.rebate}</td>
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
					<td class="td-center">${bomPlan.salesProfit}</td>
					<td class="td-center">${bomPlan.adjustProfit}</td>
					<td class="td-center">${bomPlan.quantity}</td>
					<td class="td-center" colspan="2">${bomPlan.totalSalesProfit}</td>
					<td class="td-center" colspan="2">${bomPlan.totalAdjustProfit}</td>
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
		<c:if test="${ bomPlan.status == ''}" >
			<button type="button" id="edit" class="DTTT_button">编辑</button>
		</c:if>
		<c:if test="${ bomPlan.status == ''}" >
			<button type="button" id="approve" class="DTTT_button">审批</button>
		</c:if>
	</fieldset>		
		
</form:form>

</div>
</div>
</body>
	
</html>
