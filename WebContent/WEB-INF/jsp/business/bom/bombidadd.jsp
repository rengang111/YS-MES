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
<title>客户报价--新建</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	function ajax() {
		var height = $(window).height();
		height = height - 370;

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,

			dom : '<"clear">rt',
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				]
			
		}).draw();

		
		t.on('blur', 'tr td:nth-child(2),tr td:nth-child(4)',function() {
			
			var currValue = $(this).find("input:text").val().trim();

            $(this).find("input:text").removeClass('bgwhite');
            
            if(currValue =="" ){
            	
            	 $(this).find("input:text").addClass('error');
            }else{
            	 $(this).find("input:text").addClass('bgnone');
            }
			
		});

		
		t.on('blur', 'tr td:nth-child(5),tr td:nth-child(6)',function() {
			
           $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
				

		t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {
			
			/*产品成本 = 各项累计
			人工成本 = H带头的ERP编号下的累加
			材料成本 = 产品成本 - 人工成本
			经管费 = 经管费率 x 产品成本
			核算成本 = 产品成本 + 经管费*/
			
            var $tds = $(this).parent().find("td");
			
            var $oMaterial  = $tds.eq(1).find("input:text");
            var $oQuantity  = $tds.eq(4).find("input");
			var $oThisPrice = $tds.eq(5).find("input");
			var $oAmount1   = $tds.eq(6).find("input:hidden");
			var $oAmount2   = $tds.eq(6).find("span");
			var $oAmountd   = $tds.eq(6).find("input:last-child");//人工成本
			
			var materialId = $oMaterial.val();
			var fPrice = currencyToFloat($oThisPrice.val());		
			var fQuantity = currencyToFloat($oQuantity.val());	
			var fTotalNew = currencyToFloat(fPrice * fQuantity);
			var fAmountd  = fnLaborCost(materialId,fTotalNew);//人工成本

			var vPrice = float4ToCurrency(fPrice);	
			var vQuantity = float5ToCurrency(fQuantity);
			var vTotalNew = float4ToCurrency(fTotalNew);
					
			//详情列表显示新的价格
			$oThisPrice.val(vPrice);					
			$oQuantity.val(vQuantity);	
			$oAmount1.val(vTotalNew);	
			$oAmount2.html(vTotalNew);
			$oAmountd.val(fAmountd);

			//合计成本
			costAcount();
			
		});
			
		t.on('click', 'tr', function() {
			
			var rowIndex = $(this).context._DT_RowIndex; //行号			
			//alert(rowIndex);

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
			
		});
		
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				var num   = i + 1;
				//var checkBox = "<input type=checkbox name='numCheck' id='numCheck' value='" + num + "' />";
				cell.innerHTML = num;//+ checkBox;
			});
		}).draw();

	};//ajax()

	$(document).ready(function() {

		$("#bomPlan\\.currency").val('${material.currency}');
		
		$("#bomPlan\\.plandate").val(shortToday());
		$("#bomPlan\\.plandate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});	
		
		ajax();
		
		
		//$('#example').DataTable().columns.adjust().draw();
		
		$("#goBack").click(
				function() {
					var materialId = '${product.materialId}';
					var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;
					location.href = url;		
				});
		
		$("#update").click(function() {			
			$('#bomForm').attr("action", "${ctx}/business/bom?methodtype=insertQuotation");
			$('#bomForm').submit();
		});
	
		
		foucsInit();//input获取焦点初始化处理
		
		costAcount();//成本核算

		//外汇报价换算
		$(".exchange").change(function() {
			
			var exchang  = currencyToFloat($('#bomPlan\\.exchangerate').val());
			var exRebate  = currencyToFloat($('#bomPlan\\.rebaterate').val());
			var exprice  = currencyToFloat($('#bomPlan\\.exchangeprice').val());
			var mateCost = currencyToFloat($('#bomPlan\\.materialcost').val());
			var baseCost = currencyToFloat($('#bomPlan\\.productcost').val());
			//原币价格=汇率*报价，利润率=（原币价格+退税-基础成本）/基础成本
			var rmb = exchang * exprice;
			var rebate = exRebate * mateCost / 1.17 / 100;
			var profit = rmb + rebate - baseCost ;
			var profitRate = profit / baseCost * 100;
			//alert('p='+(rmb - baseCost)+'profit='+profit)
			$('#bomPlan\\.rebate').val(floatToCurrency(rebate));
			$('#bomPlan\\.rmbprice').val(floatToCurrency(rmb));
			$('#bomPlan\\.profit').val(floatToCurrency(profit));
			$('#bomPlan\\.profitrate').val(floatToCurrency(profitRate));
		});
	});	
	
	function fnLaborCost(materialId,cost){
		
		var laborCost = '0';
		
		//判断是否是人工成本
		if(materialId != '' && materialId.substring(0,1) == 'H')
			laborCost = cost;
		
		//alert('materialId:'+materialId+'--laborCost:'+laborCost);
		
		return laborCost;
	}
	
	function costAcount(){
		//计算该客户的销售总价,首先减去旧的		
		//产品成本=各项累计
		//人工成本=H带头的ERP编号下的累加
		//材料成本=产品成本-人工成本
		//经管费=经管费率x产品成本
		//核算成本=产品成本+经管费
		var managementCostRate = $('#bomPlan\\.managementcostrate').val();
		managementCostRate = currencyToFloat(managementCostRate) / 100;//费率百分比转换
		
		var laborCost = laborCostSum();
		var bomCost = productCostSum();
		
		var fmaterialCost = bomCost - laborCost;
		var productCost = bomCost * 1.1;		
		var ftotalCost = productCost * ( 1 + managementCostRate );

		$('#bomPlan\\.bomcost').val(floatToCurrency(bomCost));
		$('#bomPlan\\.productcost').val(floatToCurrency(productCost));
		$('#bomPlan\\.laborcost').val(floatToCurrency(laborCost));
		$('#bomPlan\\.materialcost').val(floatToCurrency(fmaterialCost));
		$('#bomPlan\\.totalcost').val(floatToCurrency(ftotalCost));
		//alert('labor:'+laborCost+'--product:'+productCost)
		
	}
	
	//列合计:总价
	function productCostSum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(6).find("span").text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})
		return sum;
	}
	
	//列合计:人工成本
	function laborCostSum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(6).find("input:last-child").val();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})		
		return sum;
	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="bomForm" method="POST"
		id="bomForm" name="bomForm"  autocomplete="off">
		
		<form:hidden path="bomPlan.recordid" value="${material.productRecord}"/>
		
		<fieldset>
			<legend>客户报价</legend>
			<table class="form" id="table_form" style="margin-top: -4px;">
				<tr>
					<td class="label" width="100px">BOM编号：</td>					
					<td width="150px">${bomForm.bomPlan.bomid}
						<form:hidden path="bomPlan.bomid"    value="${bomForm.bomPlan.bomid}"/>
						<form:hidden path="bomPlan.parentid" value="${bomForm.bomPlan.parentid}"/>
						<form:hidden path="bomPlan.subid"    value="${bomForm.bomPlan.subid}"/></td>
						
					<td class="label" width="100px">产品名称：</td>				
					<td colspan="3">${product.materialId} | ${product.materialName }
						<form:hidden path="bomPlan.materialid"  value="${product.materialId}"/></td>					
					
					<td class="label" width="100px">客户名称：</td>
					<td colspan="3">${product.shortName } | ${product.customerName }</td>
				</tr>
												
			</table>
			
			<table class="form" id="table_form2" style="margin-top: 6px;">
				
				<tr>
					<td class="td-center"><label>材料成本</label></td>	
					<td class="td-center"><label>人工成本</label></td>
					<td class="td-center"><label> BOM成本</label></td>
					<td class="td-center"><label>基础成本</label></td>
					<td class="td-center"><label>经管费率</label></td>
					<td class="td-center"><label>核算成本</label></td>
				</tr>	
				<tr>			
					<td class="td-center">
						<form:input path="bomPlan.materialcost" class="read-only cash" /></td>
					<td class="td-center">
						<form:input path="bomPlan.laborcost" class="read-only cash" value="" /></td>
					<td class="td-center">
						<form:input path="bomPlan.bomcost" class="read-only cash" value="${material.bomCost}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.productcost" class="read-only cash" value="${material.productCost}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.managementcostrate" class="read-only num mini" value="${material.managementCostRate}" style="text-align: center;"/>%</td>
					<td class="td-center">
						<form:input path="bomPlan.totalcost" class="read-only cash" value="${material.totalCost}"/></td>
				</tr>								
			</table>
				
			<table class="form" id="table_form3" style="margin-top: 6px;">
				
				<tr>
					<td class="td-center"><label>报价时间</label></td>	
					<td class="td-center"><label>报价币种</label></td>
					<td class="td-center"><label>换汇</label></td>
					<td class="td-center"><label>报价</label></td>
					<td class="td-center"><label>原币价格</label></td>
					<td class="td-center"><label>退税率</label></td>
					<td class="td-center"><label>退税</label></td>
					<td class="td-center"><label>利润率</label></td>
				</tr>	
				<tr>			
					<td class="td-center">
						<form:input path="bomPlan.plandate"  class="short"/></td>
					<td class="td-center">
							<form:select path="bomPlan.currency" style="width: 100px;">
								<form:options items="${bomForm.currencyList}" itemValue="key"
									itemLabel="value" />
							</form:select></td>
					<td class="td-center">
						<form:input path="bomPlan.exchangerate" class="cash exchange short" value="${material.exchangeRate}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.exchangeprice" class="cash exchange short" value="${material.exchangePrice}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.rmbprice" class="read-only cash short" value="${material.RMBPrice}" /></td>
					<td class="td-center">
						<form:input path="bomPlan.rebaterate" class="cash exchange mini" value="${material.rebateRate}"/>%</td>
					<td class="td-center">
						<form:input path="bomPlan.rebate" class="read-only cash mini" value="${material.rebate}"/></td>
					<td class="td-center">
						<form:hidden path="bomPlan.profit"/>
						<form:input  path="bomPlan.profitrate" class="read-only cash mini" value="${material.profitRate}"/>%</td>
				</tr>								
			</table>
	</fieldset>
	
	<div style="margin: -3px 10px 0px 5px;float:right; padding:0px;">	
			<button type="button" id="update" class="DTTT_button">保存</button>
			<button type="button" id="goBack" class="DTTT_button">返回</button>
	</div>
	<fieldset>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display">
			<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="200px">物料编码</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="100px">供应商编号</th>
				<th class="dt-center" width="60px">用量</th>
				<th class="dt-center" width="80px">本次单价</th>
				<th class="dt-center" width="80px">总价</th>
			</tr>
			</thead>
			<tfoot>
				<tr>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</tfoot>
		<tbody>

		<c:if test="${fn:length(materialDetail) > 0}" >
						
			<c:forEach var="detail" items="${materialDetail}" varStatus='status' >		

				<tr>
					<td></td>
					<td>${detail.materialId}
						<form:hidden path="bomDetailLines[${status.index}].materialid"  value="${detail.materialId}"/></td>								
					<td><span id="name${status.index}">${detail.materialName}</span></td>
					<td>${detail.supplierId}
						<form:hidden path="bomDetailLines[${status.index}].supplierid"  value="${detail.supplierId}" /></td>
					<td>${detail.quantity}
						<form:hidden path="bomDetailLines[${status.index}].quantity" value="${detail.quantity}"  /></td>							
				<c:if test="${empty bomForm.accessFlg}" >
					<td>${detail.price}
						<form:hidden path="bomDetailLines[${status.index}].price"  value="${detail.price}" /></td>						
				</c:if>
				<c:if test="${!empty bomForm.accessFlg}" >
					<td>${detail.oldPrice}
						<form:hidden path="bomDetailLines[${status.index}].price"  value="${detail.oldPrice}" /></td>						
				</c:if>
					<td><span id="total${status.index}">${detail.totalPrice}</span>
						<form:hidden path="bomDetailLines[${status.index}].totalprice"  value="${detail.totalPrice}"/>
						<input type="hidden" id="labor${status.index}"></td>	
					
					<form:hidden path="bomDetailLines[${status.index}].sourceprice"  value="${detail.price}" />	
				</tr>

				<script type="text/javascript">
					var index = '${status.index}';
					var cost = '${detail.productcost}';
					var materialId = '${detail.materialId}';
					var materialName = '${detail.materialName}';
					var quantity = currencyToFloat('${detail.quantity}');
					var accessFlg = '${bomForm.accessFlg}';
					if(accessFlg ==''){
						var price = currencyToFloat( '${detail.price}');
						
					}else{
						var price = currencyToFloat( '${detail.oldPrice}');
						
					}
					var totalPrice = float4ToCurrency(quantity * price);
					var labor = fnLaborCost( materialId,totalPrice);
					$('#labor'+index).val(labor);
					$('#total'+index).html(totalPrice);
					$('#bomDetailLines'+index+'\\.totalprice').val(totalPrice);
					$('#name'+index).html(jQuery.fixedWidth(materialName,40));
					
				</script>

			</c:forEach>
		</c:if>
		</tbody>
	</table>
	</div>
	</fieldset>
		
</form:form>

</div>
</div>
</body>

	
</html>
