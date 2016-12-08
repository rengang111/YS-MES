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
<title>采购合同-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

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
					}, {				
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

	};//ajaxRawGroup()
	
	$(document).ready(function() {

		$( "#tabs" ).tabs();
		
		$("#contract\\.purchasedate").val(shortToday());
		var date = new Date(shortToday());
		date.setDate(date.getDate()+20);
		$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
		
		$("#contract\\.purchasedate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 

		$("#contract\\.deliverydate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});
		
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

		$("#contract\\.purchasedate").change(function(){
			
			var val = $(this).val();
			var date = new Date(val);
			date.setDate(date.getDate()+20);
			$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
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
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
				<tr> 		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="120px">${order.YSId }
						<form:hidden path="contract.ysid" value="${order.YSId }"/></td>
									
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td width="150px">${ order.materialId }</td>
						
					<td class="label" width="100px"><label>产品名称：</label></td>
					<td colspan="3">${ order.materialName } </td>
				</tr>	
				<tr> 		
					<td class="label"><label>供应商编号：</label></td>					
					<td>${ order.supplierId }
						<form:hidden path="contract.supplierid" value="${ order.supplierId }"/></td>
									
					<td class="label"><label>供应商简称：</label></td>					
					<td>${ order.supplierShortName }</td>
						
					<td class="label"><label>供应商名称：</label></td>
					<td colspan="3">${ order.supplierFullName }</td>
				</tr>	
				<tr> 		
					<td class="label"><label>采购合同编号：</label></td>					
					<td>${ contractId }</td>
					<td class="label"><label>下单日期：</label></td>
					<td>
						<form:input path="contract.purchasedate" /></td>
					<td class="label"><label>合同交期：</label></td>
					<td colspan="3">
						<form:input path="contract.deliverydate" /></td>
				</tr>									
			</table>
			
	</fieldset>
	
	<div style="clear: both"></div>		
	<fieldset>
	<legend> 合同详情</legend>
	<div id="tabs" style="margin: -6px 0px 0px 5px; float: right; padding: 0px;">
		<ul>
			<li><a href="#tabs-1" style="font-size: 11px;">描述</a></li>
			<li><a href="#tabs-2" style="font-size: 11px;">图片</a></li>
		</ul>

		<div id="tabs-2" style="padding: 5px;">
			<img id="productImage"
				src="${ctx}/css/images/blankDemo.png"
				style="width: 280px; height: 210px;" />
		</div>

		<div id="tabs-1" style="padding: 5px;">
			<div id="productDetail" style="width: 280px; height: 210px;"></div>
		</div>

	</div>
	<div id="floatTable" style="width: 70%; float: left; margin: 5px 0px 0px 0px;">
	
	<div class="list">
	<table id="example" class="display" width="100%">	
		<thead>
		<tr>
			<th style="width:20px">No</th>
			<th style="width:80px">物料ERP编码</th>
			<th>物料名称</th>
			<th style="width:50px">计量单位</th>
			<th style="width:80px">数量</th>
			<th style="width:50px">单价</th>
			<th style="width:70px">总价</th>
			<th style="width:1px"></th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="detail" items="${detail}" varStatus='status' >	
				<tr>
					<td></td>
					<td>${ detail.materialId }<form:hidden path="detailList[${status.index}].materialid" value="${detail.materialId}" /></td>								
					<td><span id="name${status.index}"></span></td>					
					<td>${ detail.unit }</td>
					<td>${ detail.quantity}   <form:hidden path="detailList[${status.index}].quantity" value="${detail.quantity}"/></td>							
					<td>${ detail.price }     <form:hidden path="detailList[${status.index}].price" value="${detail.price}" /></td>
					<td>${ detail.totalprice }<form:hidden path="detailList[${status.index}].totalprice" value="${detail.totalPrice}" /></td>				
					<td>					  <form:hidden path="orderDetailLines[${status.index}].recordid" value="${detail.recordId}" /></td>				
				</tr>	
								
				<script type="text/javascript">
					var materialName = '${detail.materialName}';
					var index = '${status.index}';
					
					$('#name'+index).html(jQuery.fixedWidth(materialName,20));
					
					counter++;
					
				</script>	
					
			</c:forEach>
			
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	</div>
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
