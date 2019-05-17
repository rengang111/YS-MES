<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>仓库退货-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	$(document).ready(function() {

		$("#doEdit").click(function() {
		
			var receiptId = $('#stockin\\.receiptid').val();
			var url = "${ctx}/business/depotReturn?methodtype=depotRentunEdit&inspectionReturnId="+receiptId;

			location.href = url;	
		});

		$("#doDelete").click(function() {
			if(confirm("删除后不能恢复，确定要删除吗")){
				var receiptId = $('#stockin\\.receiptid').val();
				var url = '${ctx}/business/depotReturn?methodtype=depotRentunDelete&receiptId='+receiptId;
				location.href = url;
				
			}	
		});
		

		$("#goBack").click(function() {
			var url = '${ctx}/business/depotReturn';
			location.href = url;	
		});
		
		productPhotoView();//出库单附件
		
		//产品图片添加位置,                                                                                                                                                                                        
		var productIndex = 1;
		$("#addProductPhoto").click(function() {
			
			var path='${ctx}';
			var cols = $("#productPhoto tbody td.photo").length - 1;
			//从 1 开始
			var trHtml = addPhotoRow('productPhoto','uploadProductPhoto',productIndex,path);		

			$('#productPhoto td.photo:eq('+0+')').after(trHtml);	
			productIndex++;		
			//alert("row:"+row+"-----"+"::productIndex:"+productIndex)
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
			
		<form:hidden path="stockin.receiptid" value="${depot.receiptId }"/>
		<form:hidden path="stockin.ysid"      value="${depot.YSId }"/>
		
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
				<tr id="">		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="150px">${depot.YSId }</td>
									
					<td class="label" width="100px"><label>合同编号：</label></td>					
					<td>${ depot.contractId }</td>
					
					<td class="label" width="100px"><label>供应商：</label></td>					
					<td>（${ depot.supplierId }）${ depot.supplierName }</td>						
				</tr>
				<tr id="">	
					<td class="label" width="100px"><label>取消数量：</label></td>
					<td>${ depot.quantity } </td>
						
					<td class="label" width="100px"><label>物料编号：</label></td>					
					<td width="150px">${depot.materialId }</td>
									
					<td class="label" width="100px"><label>物料名称：</label></td>					
					<td>${ depot.materialName }</td>						
				</tr>
				<tr>
					<td class="label">取消日期：</td>
					<td>${ depot.checkInDate }</td>
					<td class="label">操作员：</td>
					<td colspan="3">${ depot.LoginName }</td>
				</tr>
				<tr style="height:100px">
					<td class="label" width="100px">取消事由：</td>
					<td colspan="5">${ depot.remarks }</td>
				</tr>									
			</table>
			
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doEdit"   class="DTTT_button">编辑</button>
		<button type="button" id="doDelete" class="DTTT_button">删除</button>
		<button type="button" id="goBack"   class="DTTT_button">返回</button>
	</fieldset>	
	<fieldset>
		<span class="tablename">附件清单</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
		<div class="list">
			<div class="showPhotoDiv" style="overflow: auto;width: 1024px;">
				<table id="productPhoto" style="width:100%;">
					<tbody><tr><td class="photo"></td></tr></tbody>
				</table>
			</div>
		</div>	
	</fieldset>		
			
	</form:form>

</div>
</div>
</body>	
<script type="text/javascript">

function productPhotoView() {

	var YSId = '${depot.YSId }';
	var receiptId = $('#stockin\\.receiptid').val();
	
	$.ajax({
		"url" :"${ctx}/business/depotReturn?methodtype=getProductPhoto&YSId="+YSId+"&receiptId="+receiptId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["productFileCount"];
			//alert(countData)
			photoView('productPhoto','uploadProductPhoto',countData,data['productFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
         	//alert(errorThrown)
		 }
	});
	
}//产品图片


</script>

<script type="text/javascript">

function photoView(id, tdTable, count, data) {
	
	var row = 0;
	for (var index = 0; index < count; index++) {
		var path = '${ctx}' + data[index];
		var pathDel = data[index];		
		var trHtml = showPhotoRow(id,tdTable,path,pathDel,index);		
		$('#' + id + ' td.photo:eq(' + row + ')').after(trHtml);
		row++;
	}
}


function deletePhoto(tableId,tdTable,path) {
	
	var url = '${ctx}/business/depotReturn?methodtype='+tableId+'Delete';
	url+='&tabelId='+tableId+"&path="+path;
	    
	if(!(confirm("确定要删除该图片吗？"))){
		return;
	}
    $("#formModel").ajaxSubmit({
		type: "POST",
		url:url,	
		data:$('#formModel').serialize(),// 你的formid
		dataType: 'json',
	    success: function(data){
	    	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
			}
			
			//删除后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片删除失败,请重试。")
		}
	});
}

function uploadPhoto(tableId,tdTable, id) {

	var url = '${ctx}/business/depotReturnFileUpload?methodtype=uploadPhoto';
	
	$("#formModel").ajaxSubmit({
		type : "POST",
		url : url,
		data : $('#formModel').serialize(),// 你的formid
		dataType : 'json',
		success : function(data) {
	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
			}
			
			//添加后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片上传失败,请重试。")
		}
	});
}


</script>
</html>
