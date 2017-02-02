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
<title>订单采购方案-查看</title>
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
					}, {"className":"td-center"				
					}, {				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-center"				
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
					}, {"className":"td-center"				
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

	};//ajaxRawGroup()
	
	$(document).ready(function() {
	
		ajax();	
		
		ajaxRawGroup();	
		
		$('#example').DataTable().columns.adjust().draw();		
		
		$("#goBack").click(
				function() {
					history.go(-2);
					//var url = '${ctx}/business/purchase?methodtype=init';
					//location.href = url;		
				});
		
		$("#doContract").click(
				function() {	
					var YSId = '${order.YSId }';
					var materialId = '${order.productId }';
					var url = '${ctx}/business/contract?methodtype=createZZ&YSId='+YSId+'&materialId='+materialId;
					location.href = url;
		});		
		

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
					<td width="100px">${order.YSId }
						<form:hidden path="requirment.ysid" value="${order.YSId }"/></td>
									
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td width="150px">${order.productId }</td>
						
					<td class="label" width="100px"><label>产品名称：</label></td>
					<td>${order.productName }</td>
					
					<td class="label" width="100px"><label>数量：</label></td>
					<td width="150px">${order.quantity }</td>
					
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
	<!-- 
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
	-->
	<fieldset>

		<legend>原材料需求表</legend>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display" width="100%">
			<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="100px">ERP编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" width="30px">计量单位</th>
				<th class="dt-center" style="width:50px;">供应商</th>
				<th class="dt-center" style="width:50px;">当前<br>需求数量</th>
				<th class="dt-center" width="50px">当前虚拟库存</th>
				<th class="dt-center" width="50px">采购量</th>
				<th class="dt-center" width="50px">单价</th>
				<th class="dt-center" width="50px">货币</th>
				<th class="dt-center" style="width:80px">总价</th>
				<th class="dt-center" width="1px"></th>
			</tr>
		</thead>
		<tbody>							
		<c:forEach var="detail" items="${rawGroup}" varStatus='status' >	
			
			<tr>
				<td></td>
				<td>${detail.materialId}</td>					
				<td>${detail.materialName}</td>
				<td>${detail.unit}</td>	
				<td>${detail.supplierId}</td>
				<td>${detail.total}</td>
				<td><span>0</span></td>	
				<td>${detail.quantity}</td>	
				<td>${detail.price}</td>
				<td>${detail.currency}</td>
				<td>${detail.totalPrice}</td>
				<td><span></span></td>
			</tr>
		</c:forEach>
		
		</tbody>
	</table>
	</div>
	</fieldset>
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doContract" class="DTTT_button">生成采购合同</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>		
		
</form:form>

</div>
</div>
</body>

	
</html>
