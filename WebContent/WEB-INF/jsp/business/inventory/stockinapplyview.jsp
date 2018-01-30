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
					var url = "${ctx}/business/stockinapply";					
					location.href = url;
				});
		
		$("#doEdit").click(
				function() {
					var applyId = $('#stockinApply\\.stockinapplyid').val();
					var url = "${ctx}/business/stockinapply?methodtype=stockInApplyEdit"+"&applyId="+applyId;
			location.href = url;
		});
		
	});	
	
	
	function doShowMaterial(recordid,parentid) {
		//var height = setScrollTop();
		//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
		var url = '${ctx}/business/material?methodtype=detailView';
		url = url + '&parentId=' + parentid+'&recordId='+recordid+'&keyBackup=1';

		layer.open({
			offset :[10,''],
			type : 2,
			title : false,
			area : [ '1100px', '520px' ], 
			scrollbar : false,
			title : false,
			content : url,
			//只有当点击confirm框的确定时，该层才会关闭
			cancel: function(index){ 
			 // if(confirm('确定要关闭么')){
			    layer.close(index)
			 // }
			  $('#baseBomTable').DataTable().ajax.reload(null,false);
			  return false; 
			}    
		});		

	};
	
</script>


</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">
			
		<form:hidden path="stockinApply.stockinapplyid" class="required read-only " value="${apply.stockInApplyId }" />
		<fieldset>
			<legend> 直接入库申请</legend>
			<table class="form" id="table_form">				
				<tr> 				
					<td class="label" width="100px">申请单编号：</td>					
					<td width="150px">${apply.stockInApplyId }</td>
															
					<td width="100px" class="label">申请日期：</td>
					<td width="150px">${apply.requestDate }</td>
					
					<td width="100px" class="label">申请人：</td>
					<td>${apply.storageName }</td>
				</tr>
			</table>			
		</fieldset>	
		<div style="clear: both"></div>	
		<fieldset class="action" style="text-align: right;">
			<button type="button" id="doEdit" class="DTTT_button">编辑</button>
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
						<c:forEach var='detail' items='${applyDetail}' varStatus='status'>
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
		<fieldset>
			<legend> 备注信息</legend>
			<table class="form">
				<tr>				
					<td><pre>${apply.remarks }</pre></td>					
				</tr>		
			</table>
		</fieldset>		
</form:form>
</div>
</div>
</body>
</html>
