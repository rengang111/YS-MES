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
<title>订单采购合同-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function ajax() {
		
		var t = $('#rawDetail').DataTable({
			
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
					}, {				
					}, {"className":"td-center"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				]
			
		}).draw();
		
			
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

	function ajaxRawGroup() {
		
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
					}, {"className":"td-center"				
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
		
		
		t.on('blur', 'tr td:nth-child(8)',function() {
			
           $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
				

		t.on('change', 'tr td:nth-child(8)',function() {
			
			/*产品成本 = 各项累计
			人工成本 = H带头的ERP编号下的累加
			材料成本 = 产品成本 - 人工成本
			经管费 = 经管费率 x 产品成本
			核算成本 = 产品成本 + 经管费*/
			
            var $tds = $(this).parent().find("td");

			var $oPurchase = $tds.eq(7).find("input");
            var $oPrice    = $tds.eq(8).find("span");
			var $oTotals   = $tds.eq(9).find("span");
			var $oTotali   = $tds.eq(9).find("input");
			
			var fPurchase = currencyToFloat($oPurchase.val());		
			var fPrice    = currencyToFloat($oPrice.html());	

			var fTotal   = fPurchase * fPrice;

			var vTotal   = floatToCurrency(fTotal);
					
			//详情列表显示新的价格
			$oTotals.html(vTotal);
			$oTotali.val(vTotal);			
			
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

	};//ajaxRawGroup()
	
	$(document).ready(function() {
	
		ajax();	
		
		ajaxRawGroup();	
		
		$('#example').DataTable().columns.adjust().draw();		
		
		$("#goBack").click(
				function() {
					history.go(-1);
					//var url = '${ctx}/business/purchase?methodtype=init';
					//location.href = url;		
				});
		
		$("#insert").click(
				function() {			
			$('#attrForm').attr("action", "${ctx}/business/requirement?methodtype=insert");
			$('#attrForm').submit();
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

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
			
		<fieldset>
			<legend> 订单采购方案</legend>
			<table class="form" id="table_form">
				<tr> 		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="250px">${order.YSId }
						<form:hidden path="requirment.ysid" value="${order.YSId }"/></td>
									
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td width="150px"></td>
						
					<td class="label" width="100px"><label>产品名称：</label></td>
					<td></td>
					
					<td class="label" width="100px"><label>数量：</label></td>
					<td></td>
				</tr>								
			</table>
			
		<div class="list">
			<table class="form" id="table_form2">
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
		
		<table id="rawDetail" class="display" width="100%">
			<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="80px">ERP编号</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" style="width:80px;">原材料ERP</th>
				<th class="dt-center" style="width:100px;">原材料名称</th>
				<th class="dt-center" width="30px">计量单位</th>
				<th class="dt-center" width="50px">用料重量</th>
				<th class="dt-center" width="60px">当前需求数量</th>
				<th class="dt-center" style="width:60px">原材料<br>需求数量</th>
				<th class="dt-center" width="1px"></th>
			</tr>
		</thead>
		<tbody>				
					
		<c:forEach var="detail" items="${rawDetail}" varStatus='status' >
			
			<tr>
				<td></td>
				<td>${detail.ERP}</td>		
				<td><span id="name${status.index}"></span></td>
				<td><span>${detail.rawERP}</span></td>			
				<td><span id="rawname${status.index}"></span></td>			
				<td>${detail.unit}</td>		
				<td>${detail.weight}</td>					
				<td>${detail.quantity}</td>
				<td><span id="total${status.index}"></span></td>
				<td><span></span></td>
						
			</tr>
			<script type="text/javascript">
			
			 	var index = '${status.index}';
				var name = '${detail.ERPname}';
				var rawname = '${detail.rawERPname}';
				var quantity = '${detail.quantity}';
				var weight = '${detail.weight }';				
				var fmt = currencyToFloat(quantity) * currencyToFloat(weight); 

				$("#total"   + index ).html(floatToNumber(fmt));
				$("#name" + index).html(jQuery.fixedWidth(name,20));
				$("#rawname" + index).html(jQuery.fixedWidth(rawname,20));	
				//$("#detail" + index + "\\.quantity").val(floatToNumber(purchase));							
			
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
	<fieldset>
		<legend>原材料需求表</legend>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display" width="100%">
			<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="100px">原材料ERP编号</th>
				<th class="dt-center" >原材料名称</th>
				<th class="dt-center" width="30px">计量单位</th>
				<th class="dt-center" style="width:50px;">供应商</th>
				<th class="dt-center" style="width:50px;">原材料<br>需求总量</th>
				<th class="dt-center" width="50px">当前虚拟库存</th>
				<th class="dt-center" width="50px">采购量</th>
				<th class="dt-center" width="50px">单价</th>
				<th class="dt-center" style="width:80px">总价</th>
				<th class="dt-center" width="1px"></th>
			</tr>
		</thead>
		<tbody>							
		<c:forEach var="detail" items="${rawGroup}" varStatus='status' >	
			
			<tr>
				<td></td>
				<td>${detail.rawERP}<form:hidden path="requirmentList[${status.index}].materialid"  value="${detail.rawERP}"/></td>								
				<td id="name${status.index}">${detail.rawERPname}</td>			
				<td>${detail.unit}</td>	
				<td>${detail.supplierId}<form:hidden path="requirmentList[${status.index}].supplierid"  value="${detail.supplierId}"/></td>
				<td>${detail.targetQuty}</td>		
				<td><span>0</span></td>	
				<td><form:input path="requirmentList[${status.index}].quantity" class="num short" /></td>	
				<td><span>${detail.price}</span><form:hidden path="requirmentList[${status.index}].price" value="${detail.price}" /></td>
				<td><span id="totalPrice${status.index}"></span><form:hidden path="requirmentList[${status.index}].totalprice" /></td>
				<td><span></span>
						<form:hidden path="requirmentList[${status.index}].ysid"  value="${order.YSId }" /></td>	
				<form:hidden path="requirmentList[${status.index}].recordid"  value="${detail.rawRecordId }" />			
			</tr>
			<script type="text/javascript">
			
			 	var index = '${status.index}';
				var name = '${detail.rawERPname}';
				var target = '${detail.targetQuty}';
				var price = '${detail.price}';
				var actualQuty = '${detail.quantity}';
				var currStok = 0;//'${detail.currentStock }';
				
				var purchase = 0;
				if(actualQuty =="" || actualQuty =="0"){
					purchase = currencyToFloat(target) - Number(currStok);
					
				}else{
					purchase = currencyToFloat(actualQuty);					
				}
				
				var totalPrice = floatToCurrency( purchase * currencyToFloat(price) );
				
				$("#name" + index).html(jQuery.fixedWidth(name,15));	
				$("#totalPrice" + index).html(totalPrice);	
				$("#requirmentList" + index + "\\.totalprice").val(floatToNumber(totalPrice));	
				$("#requirmentList" + index + "\\.quantity").val(floatToNumber(purchase));	

				//alert('purchase::'+floatToNumber($("#detail" + index + "\\.purchaseQuty").val()))
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
				<th></th>
			</tr>
		</tfoot>
	</table>
	</div>
	</fieldset>
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="insert" class="DTTT_button">保存</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>		
		
</form:form>

</div>
</div>
</body>

	
</html>
