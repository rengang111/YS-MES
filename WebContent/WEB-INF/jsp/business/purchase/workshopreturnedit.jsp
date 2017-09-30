<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>车间退货-编辑</title>
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
	        "bAutoWidth":false,
	        "ordering"  : false,
			"dom" : '<"clear">rt',			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {"className":"td-left"
					}, {								
					}, {"className":"td-center"
					}, {"className":"td-right"		
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				],
				"columnDefs":[
				    {
						"visible" : false,
						"targets" : [ ]
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

		var productid = '${ contract.productId }';
		if(productid == null || productid == ""){
			$('#ysid00').attr("style","display:none");			
		}
		
		ajaxRawGroup();			
		
		$("#workshopReturn\\.returndate").val(shortToday());
		
		
		$("#workshopReturn\\.returndate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 
		
		
		$('#example').DataTable().columns.adjust().draw();		
		
		$("#goBack").click(
				function() {
					var contractId = '${ contract.contractId }';
					var url = '${ctx}/business/contract?methodtype=workshopRentunInit&keyBackup=' + contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {			
			$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=workshopRentunUpdate");
			$('#attrForm').submit();
		});		
	
		//input格式化
		foucsInit();
		
		

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

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
			
		<form:hidden path="workshopReturn.ysid" value="${contract.YSId }"/>
		<form:hidden path="workshopReturn.contractid" value="${contract.contractId }"/>
		<form:hidden path="workshopReturn.workshopreturnid" value="${workshop.workshopReturnId }"/>
		<form:hidden path="workshopReturn.recordid" value="${workshop.workshopRecordId }"/>
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
				<tr id="ysid00">		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="150px">${contract.YSId }</td>
									
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td width="150px">&nbsp;${ contract.productId }</td>
						
					<td class="label" width="100px"><label>产品名称：</label></td>
					<td>&nbsp;${ contract.productName } </td>
				</tr>	
				<tr> 		
					<td class="label"><label>供应商编号：</label></td>					
					<td>${ contract.supplierId }</td>
									
					<td class="label"><label>供应商简称：</label></td>					
					<td>&nbsp;${ contract.shortName }</td>
						
					<td class="label"><label>供应商名称：</label></td>
					<td>&nbsp;${ contract.fullName }</td>
				</tr>	
				<tr> 		
					<td class="label"><label>采购合同编号：</label></td>					
					<td>${ contract.contractId }</td>
					<td class="label"><label>退货日期：</label></td>
					<td><form:input path="workshopReturn.returndate"  /></td>
					<td class="label"><label>任务编号：</label></td>
					<td><form:input  path="workshopReturn.taskid" calss="short" /></td>
				</tr>									
			</table>
			
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="insert" class="DTTT_button">保存</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>			
	<fieldset style="margin-top: -30px;">
	<legend> 合同详情</legend>	
		<div class="list">
		<table id="example" class="display" style="width:100%">	
			<thead>
				<tr>
					<th style="width:1px">No</th>
					<th style="width:120px">ERP编码</th>
					<th>ERP名称</th>
					<th style="width:40px">单位</th>
					<th style="width:100px">今日退货(修改后)</th>
					<th style="width:100px">今日退货(修改前)</th>
					<th></th>
				</tr>
			</thead>		
			<tbody>
				<c:forEach var="detail" items="${workshopDetail}" varStatus='status' >	
					<tr>
						<td></td>
						<td>
							<a href="###" onClick="doShowMaterial('${detail.materialRecordId}','${detail.materialParentId}')">${detail.materialId}</a>
							<form:hidden path="workshopRetunList[${status.index}].materialid" value="${detail.materialId}" /></td>								
						
						<td><span id="name${status.index}"></span></td>
						<td>${ detail.unit }</td>		
						<td><form:input  path="workshopRetunList[${status.index}].quantity"  class="num short" /></td>
						<td><span>${detail.quantity}</span></td>				
						<td><form:hidden path="workshopRetunList[${status.index}].recordid" value="${detail.recordId}" /></td>				
						
					</tr>									
					<script type="text/javascript">
						var materialName = '${detail.materialName}';
						var index = '${status.index}';						
						$('#name'+index).html(jQuery.fixedWidth(materialName,60));
					</script>						
				</c:forEach>				
			</tbody>			
		</table>
		</div>
		</fieldset>
		<fieldset>
		<legend> 退货详情</legend>
		<table class="form" >
			<tr>
				<td class="td-left"><textarea name="workshopReturn.remarks" rows="6" cols="100" ></textarea></td>
			</tr>
		</table>		
		</fieldset>
			
	</form:form>

</div>
</div>
</body>	
</html>
