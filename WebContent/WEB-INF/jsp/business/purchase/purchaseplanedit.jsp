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
	
	
	function ajax() {
		var height = $(window).height();
		height = height - 370;

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
			//"scrollY"    : height,
	       // "scrollCollapse": true,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,

			//dom : 'T<"clear">rt',
			
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
			/*
            var $tds = $(this).parent().find("td");
			
            var $oMaterial  = $tds.eq(1).find("input:text");
            var $oQuantity  = $tds.eq(4).find("input");
			var $oThisPrice = $tds.eq(5).find("input");
			var $oAmount1   = $tds.eq(6).find("input:hidden");
			var $oAmount2   = $tds.eq(6).find("span");
			
			var fPrice = currencyToFloat($oThisPrice.val());		
			var fQuantity = currencyToFloat($oQuantity.val());			
			var fTotalOld = currencyToFloat($oAmount1.val());
			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vPrice = floatToCurrency(fPrice);	
			var vQuantity = floatToCurrency(fQuantity);
			var vTotalNew = floatToCurrency(fTotalNew);
					
			//详情列表显示新的价格
			$oThisPrice.val(vPrice);					
			$oQuantity.val(vQuantity);	
			$oAmount1.val(vTotalNew);	
			$oAmount2.html(vTotalNew);	

			//临时计算该客户的销售总价
			//首先减去旧的价格		
			alert('fTotalNew:'+fTotalNew+"fTotalOld:"+fTotalOld)
			costAcount($oMaterial.val(),fTotalNew,fTotalOld);
			*/
			
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
				cell.innerHTML = i + 1;
			});
		}).draw();

	};//ajax()

	$(document).ready(function() {
	
		ajax();
		
		
		//$('#example').DataTable().columns.adjust().draw();
		
		
		$("#goBack").click(
				function() {
					var url = '${ctx}/business/purchase?methodtype=init';
					location.href = url;		
				});
		
		$("#insert").click(
				function() {			
			$('#purchaseForm').attr("action", "${ctx}/business/bom?methodtype=update");
			$('#purchaseForm').submit();
		});
		

		//重设显示窗口(iframe)高度
		//iFramAutoSroll();
		
		//input获取焦点初始化处理
		foucsInit();

	});	
	
	function foucsInit(){
		
		$("input:text").not(".read-only").addClass('bgnone');
		$(".cash").css('border','0px');
		
		$("input:text") .not(".read-only") .focus(function(){
			$(this).removeClass('bgnone').removeClass('error').addClass('bgwhite');
		    $(this).select();
		});
		
		$(".read-only").removeClass('bgwhite');
		
		$(".cash") .focus(function(){
			$(this).val(currencyToFloat($(this).val()));
		    $(this).select();
		});
		
		$(".cash") .blur(function(){
			$(this).val(floatToCurrency($(this).val()));
		});
		
	}
	
	function costAcount(materialId,totalNew,totalOld){
		//alert('productCost: '+productCost+'--new:'+totalNew+'--old:'+totalOld);
		//计算该客户的销售总价,首先减去旧的		
		//产品成本=各项累计
		//人工成本=H带头的ERP编号下的累加
		//材料成本=产品成本-人工成本
		//经管费=经管费率x产品成本
		//核算成本=产品成本+经管费
			
		//判断是否是人工成本
		/*
		var costType;
		if(materialId != '')
			costType = materialId.substring(0,1);
		
		if(costType == 'H')
			laborCost = laborCost - totalOld +  totalNew;		
		
		productCost = productCost - totalOld +  totalNew;
		
		var rate = $('#bomPlan\\.managementcostrate').val();
		var fmaterialCost,fmanageCost,facoutCost;
		
		fmaterialCost = productCost - laborCost;
		fmanageCost   = productCost * rate/100;
		facoutCost    = productCost + fmanageCost;
		
		$('#bomPlan\\.productcost').val(floatToCurrency(productCost));
		$('#bomPlan\\.laborcost').val(floatToCurrency(laborCost));
		$('#bomPlan\\.materialcost').val(floatToCurrency(fmaterialCost));
		$('#bomPlan\\.managementcost').val(floatToCurrency(fmanageCost));
		$('#bomPlan\\.totalcost').val(floatToCurrency(facoutCost));
		*/
	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="purchaseForm" method="POST"
		id="purchaseForm" name="purchaseForm"  autocomplete="off">
		
		<input type="hidden" id="tmpMaterialId" />	
		<form:hidden path="bomPlan.recordid" value="${bomPlan.productRecord}" />		
		
		<fieldset>
			<legend> 订单执行方案</legend>
			<table class="form" id="table_form" width="100%" style="margin-top: -4px;">
				<tr> 		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="250px">${bomPlan.YSId }
						<form:hidden path="ysid"  value="${bomPlan.YSId }"/></td>
									
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td width="150px">${bomPlan.productId}					
						<form:hidden path="bomid" value="${bomPlan.productId}" /></td>
						
					<td class="label"><label>产品名称：</label></td>
					<td>${bomPlan.productName }</td>
					
					<td class="label"><label>数量：</label></td>
					<td>&nbsp;${bomPlan.quantity }</td>
				</tr>								
			</table>
			
		<div class="list" style="margin-top: -4px;">
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
				<th class="dt-center" style="width:80px;font-size:10px">当前需求<br/>数量</th>
				<th class="dt-center" width="50px">其他</th>
			</tr>
		</thead>
		<tbody>							
		<c:forEach var="detail" items="${bomDetail}" varStatus='status' >	
			<tr>
				<td></td>
				<td>${detail.materialId}<form:hidden path="detail[${status.index}].materialId" /> </td>								
				<td>${detail.materialName}</td>
				<td>${detail.supplierId}</td>
				<td>${detail.quantity}</td>			
				<td><form:input path="detail[${status.index}].orderQuantity"  value="${bomPlan.orderQuantity }" class="cash"  style="width:50px"/></td>			
				<td><span></span>
					<form:hidden path="detail[${status.index}].materialId" /></td>
				<td>${detail.currentStock}</td>
				<td><span></span></td>
				<td><form:input path="detail[${status.index}].memo" /></td>				
			</tr>
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
		<button type="button" id="update" class="DTTT_button">保存</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>		
		
</form:form>

</div>
</div>
</body>

	
</html>
