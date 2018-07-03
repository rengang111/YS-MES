<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>直接入库-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	function ajaxRawGroup() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 500,
	        "ordering"  : false,
			"dom" 		: '<"clear">rt',
			"columns" : [ 
			           {"className":"dt-body-center"
					}, {"className":"td-left"
					}, {							
					}, {"className":"td-center"		
					}, {"className":"td-center"				
					}			
				],				
			
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
		
				
		ajaxRawGroup();	
		
		$("#goBack").click(
				function() {
					var url = "${ctx}/business/stockinapply?methodtype=stockinDirect";					
					location.href = url;
				});
		
		$("#doEdit").click(
				function() {
					var applyId = $('#stockinApply\\.arrivalid').val();
					var url = "${ctx}/business/stockinapply?methodtype=stockInApplyEdit"+"&applyId="+applyId;
			location.href = url;
		});
		
	});	
	
	
</script>


</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">
			
		<form:hidden path="stockinApply.arrivalid" class="required read-only " value="${apply.arrivalId }" />
		<fieldset>
			<legend> 直接入库申请</legend>
			<table class="form" id="table_form">				
				<tr> 				
					<td class="label" width="100px">入库单编号：</td>					
					<td width="150px">${stockin.receiptId }</td>
															
					<td width="100px" class="label">入库日期：</td>
					<td width="150px">${stockin.checkInDate }</td>
					
					<td width="100px" class="label">申请人：</td>
					<td>${stockin.stockInName }</td>
				</tr>
			</table>			
		</fieldset>	
		<div style="clear: both"></div>	
		<fieldset class="action" style="text-align: right;">
		<!-- 	<button type="button" id="doEdit" class="DTTT_button">编辑</button> -->
			<button type="button" id="goBack" class="DTTT_button">返回</button>
		</fieldset>
		<fieldset>
			<legend> 物料详情</legend>		
			<div class="list">
				<table id="example" class="display">	
					<thead>
						<tr>
							<th style="width:30px">No</th>
							<th style="width:200px">物料编码</th>
							<th>物料名称</th>
							<th style="width:80px">单位</th>
							<th style="width:150px">入库数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var='detail' items='${DirectDetail}' varStatus='status'>
							<tr>
								<td><c:out value="${i+1 }"/></td>
								<td>${detail.materialId }</td>								
								<td>${detail.materialName }</td>					
								<td>${detail.unit }</td>
								<td>${detail.quantity }</td>
							</tr>								
						</c:forEach>						
					</tbody>
				</table>
			</div>
		</fieldset>
</form:form>
</div>
</div>
</body>
</html>