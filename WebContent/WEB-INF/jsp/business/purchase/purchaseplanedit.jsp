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
<title>订单采购方案-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function ajax() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
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
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				]
			
		}).draw();
		
		
		t.on('blur', 'tr td:nth-child(6)',function() {
			
           $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
				

		t.on('change', 'tr td:nth-child(6)',function() {
			
			/*产品成本 = 各项累计
			人工成本 = H带头的ERP编号下的累加
			材料成本 = 产品成本 - 人工成本
			经管费 = 经管费率 x 产品成本
			核算成本 = 产品成本 + 经管费*/
			
            var $tds = $(this).parent().find("td");
			
            var $oQuantity = $tds.eq(4).find("span");
			var $oOrderQty = $tds.eq(5).find("input");
			var $oAmount   = $tds.eq(6).find("span");
			var $oCurStock = $tds.eq(7).find("span");
			var $oPurchases= $tds.eq(8).find("span");
			var $oPurchasei= $tds.eq(8).find("input");
			
			var fQuantity = currencyToFloat($oQuantity.html());		
			var fOrderQty = currencyToFloat($oOrderQty.val());	
			var fCurStock = currencyToFloat($oCurStock.html());

			var fAmount   = fQuantity * fOrderQty;
			var fPurchase = fAmount - fCurStock;

			var vQuantity = floatToNumber(fQuantity);	
			var vOrderQty = floatToNumber(fOrderQty);
			var vAmount   = floatToNumber(fAmount);
			var vPurchase = floatToNumber(fPurchase);
					
			//详情列表显示新的价格
			$oQuantity.val(vQuantity);					
			$oOrderQty.val(vOrderQty);						
			$oAmount.html(vAmount);
			$oPurchases.html(vPurchase);	
			$oPurchasei.val(vPurchase);		

			//alert('fTotalNew:'+fTotalNew+"fTotalOld:"+fTotalOld)
			
			
		});
			
		t.on('click', 'tr', function() {
			
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
				cell.innerHTML = i + 1;
			});
		}).draw();

	};//ajax()

	$(document).ready(function() {
	
		ajax();		
		
		$('#example').DataTable().columns.adjust().draw();		
		
		$("#goBack").click(
				function() {
					var url = '${ctx}/business/purchase?methodtype=init';
					location.href = url;		
				});
		
		$("#reate").click(
				function() {			
			$('#purchaseForm').attr("action", "${ctx}/business/purchase?methodtype=create");
			$('#purchaseForm').submit();
		});		
		
		//iFramAutoSroll();//重设显示窗口(iframe)高度
		
		foucsInit();//input获取焦点初始化处理

	});	
	
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="purchaseForm" method="POST"
		id="purchaseForm" name="purchaseForm"  autocomplete="off">
			
		<fieldset>
			<legend> 订单执行方案</legend>
			<table class="form" id="table_form">
				<tr> 		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="250px">${bomPlan.YSId }
						<form:hidden path="YSId"  value="${bomPlan.YSId }"/></td>
									
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td width="150px">${bomPlan.productId}					
						<form:hidden path="bomId" value="${bomPlan.productId}" /></td>
						
					<td class="label"><label>产品名称：</label></td>
					<td>${bomPlan.productName }</td>
					
					<td class="label"><label>数量：</label></td>
					<td>&nbsp;${bomPlan.productQty }</td>
				</tr>								
			</table>
			
		<div class="list">
			<table class="form" id="table_form2" width="100%" style="margin-top: 6px;">
				<thead>
				<tr>
					<th class="td-center" width="150px">产品型号</th>	
					<th class="td-center" width="100px">库存数量</th>
					<th class="td-center" width="80px">耀升编号</th>
					<th class="td-center" width="150px">其他备注</th>
				</tr>
				</thead>
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
	
	<fieldset>
		<legend> 物料详情</legend>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display" width="100%">
			<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="80px">ERP编号</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" style="width:50px;">供应商</th>
				<th class="dt-center" width="50px">用量</th>
				<th class="dt-center" width="50px">订单数量</th>
				<th class="dt-center" width="80px">总量</th>
				<th class="dt-center" width="50px">库存数量</th>
				<th class="dt-center" style="width:80px">采购数量</th>
				<th class="dt-center" width="150px">其他</th>
			</tr>
		</thead>
		<tbody>							
		<c:forEach var="detail" items="${bomDetail}" varStatus='status' >	
	
			
			<tr>
				<td></td>
				<td>${detail.materialId}<form:hidden path="detail[${status.index}].materialid"  value="${detail.materialId}"/> </td>								
				<td id="materialName${status.index}"></td>
				<td>${detail.supplierId}</td>
				<td><span>${detail.quantity}</span></td>			
				<td><form:input path="detail[${status.index}].orderquantity"  value="${bomPlan.productQty }" class="num mini" /></td>			
				<td><span id="amount${status.index}"></span></td>
				<td><span id="currStock${status.index}">0${detail.currentStock}</span></td>
				<td><span id="purchase${status.index}"></span><form:hidden path="detail[${status.index}].quantity" /></td>
				<td><form:input path="detail[${status.index}].remark" /></td>				
			</tr>
			<script type="text/javascript">
			 	var index = '${status.index}';
				var name = '${detail.materialName}';
				var quantity = '${detail.quantity}';
				var orderQty = '${bomPlan.productQty }';
				var currStok = '${detail.currentStock }';
				var fmt = currencyToFloat(quantity) * currencyToFloat(orderQty); 
				//alert('fmt'+fmt)
				var purchase = fmt - Number(currStok);
				$("#amount"   + index ).html(floatToNumber(fmt));
				$("#purchase" + index ).html(floatToNumber(purchase));
				$("#materialName" + index).html(jQuery.fixedWidth(name,15));	
				$("#detail" + index + "\\.quantity").val(floatToNumber(purchase));							
			</script>
		</c:forEach>
		
		</tbody>
		<tfoot>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
			</tr>
		</tfoot>
	</table>
	</div>
	</fieldset>
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="create" class="DTTT_button">生成物料需求表</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>		
		
</form:form>

</div>
</div>
</body>

	
</html>
