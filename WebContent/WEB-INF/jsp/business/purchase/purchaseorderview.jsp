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
<title>采购合同-查看</title>
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
		
		t.on('contract.dt search.dt draw.dt', function() {
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
		
		$( "#tabs" ).tabs();
		
		$('#example').DataTable().columns.adjust().draw();		
		
		$("#goBack").click(
				function() {	
					var keyBackup = '${contract.YSId}';
					var url = '${ctx}/business/contract?keyBackup='+keyBackup;
					location.href = url;		
				});
		
		$("#doEdit").click(
				function() {
			$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=edit");
			$('#attrForm').submit();
		});		
		
	


	});	
	
	function doShowYS(YSId) {

		var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;
		
		location.href = url;
	}
	

	function doEditMaterial(recordid,parentid) {
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
			  //$('#baseBomTable').DataTable().ajax.reload(null,false);
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
			
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
			
				<tr id="ysid00"> 		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="150px">
						<a href="#" onClick="doShowYS('${contract.YSId}')">${contract.YSId }</a>
						<form:hidden path="contract.ysid" value="${contract.YSId }"/></td>
									
					<td class="label" width="100px">产品编号：</td>					
					<td width="150px">
						<a href="#" onClick="doEditMaterial('${contract.productRecordId}','${contract.productParentId}')">${ contract.productId }</a></td>
						
					<td class="label" width="100px">产品名称：</td>
					<td colspan="3">${ contract.productName } </td>
				</tr>
				<tr> 		
					<td class="label" width="100px"><label>供应商编号：</label></td>					
					<td width="200px">${ contract.supplierId }
						<form:hidden path="contract.supplierid" value="${contract.supplierId }"/></td>
									
					<td class="label" width="100px"><label>供应商简称：</label></td>					
					<td width="200px">${ contract.shortName }</td>
						
					<td class="label" width="100px"><label>供应商名称：</label></td>
					<td colspan="3">${ contract.fullName }</td>
				</tr>	
				<tr> 		
					<td class="label"><label>合同编号：</label></td>					
					<td>${ contract.contractId }
						<form:hidden path="contract.contractid" value="${contract.contractId }"/></td>
					<td class="label"><label>下单日期：</label></td>
					<td>${ contract.purchaseDate }</td>
					<td class="label"><label>合同交期：</label></td>
					<td width="150px">${ contract.deliveryDate }</td>
					<td class="label" width="100px"><label>合同金额：</label></td>
					<td>${ contract.total }</td>
				</tr>									
			</table>
			
	</fieldset>
	
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="submit" id="doEdit" class="DTTT_button">编辑</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
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
	<table id="example" class="display" >	
		<thead>
		<tr>
			<th style="width:10px">No</th>
			<th style="width:130px">物料编码</th>
			<th>物料名称</th>
			<th style="width:30px">单位</th>
			<th style="width:50px">数量</th>
			<th style="width:50px">单价</th>
			<th style="width:50px">总价</th>
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
					<td>${ detail.totalPrice }<form:hidden path="detailList[${status.index}].totalprice" value="${detail.totalPrice}" /></td>				
					<td>					  <form:hidden path="detailList[${status.index}].recordid" value="${detail.recordId}" /></td>				
				</tr>	
								
				<script type="text/javascript">
					var materialName = '${detail.materialName}';
					var index = '${status.index}';
					
					$('#name'+index).html(jQuery.fixedWidth(materialName,20));
										
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
	<fieldset>
	<legend> 合同注意事项</legend>
	<table class="form" >
		<tr>
			<td class="td-left"><pre>${contract.memo}</pre></td>
		</tr>
	</table>
	
	</fieldset>
		
</form:form>

</div>
</div>
</body>

	
</html>
