<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<!-- 直接出库申请-查看 -->
<title></title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	
	$(document).ready(function() {
		
		//$('#addProductPhoto').css('display','none');
		
		//日期
		$("#requisition\\.requisitiondate").val(shortToday());
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/requisition?methodtype=materialRequisitionMain";
					location.href = url;
				});

		
		$("#doEdit").click(
				function() {

			var recordId = '${requisition.recordId }';
			$('#formModel').attr("action", "${ctx}/business/requisition?methodtype=materialRequisitionEdit"+"&recordId="+recordId);
			$('#formModel').submit();
		});
				
		foucsInit();

		productPhotoView();
		
		//产品图片添加位置                                                                                                                                                                                    
		var productIndex = 1;
		$("#addProductPhoto").click(function() {
			
			var path='${ctx}';
			var cols = $("#productPhoto tbody tr.photo").length - 1;
			//从 1 开始			
			var trHtml = addPhotoRow('productPhoto','uploadProductPhoto',productIndex,path);		

			$('#productPhoto tr.photo:eq('+0+')').after(trHtml);	
			productIndex++;		
			//alert("row:"+row+"-----"+"::productIndex:"+productIndex)
		});
	});

	
</script>
<script type="text/javascript">


function productPhotoView() {

	var requisitionId = '${requisition.requisitionId }';

	$.ajax({
		"url" :"${ctx}/business/requisition?methodtype=getProductPhoto"+"&requisitionId="+requisitionId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["productFileCount"];
			
			photoView1('productPhoto','uploadProductPhoto',countData,data['productFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
         	alert(errorThrown)
		 }
	});
	
}//产品图片


function photoView1(id, tdTable, count, data) {

	var row = 0;
	for (var index = 0; index < count; index++) {

		var path = '${ctx}' + data[index];
		var pathDel = data[index];
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td>';
		trHtml += '<table style="width:400px;height:300px;margin: auto;" class="form" id="tb'+index+'">';
		
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td></td>';
		trHtml += '<td width="50px"><a id="uploadFile' + index + '" href="###" '+
				'onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\''+ ',' + '\'' + pathDel + '\'' + ');">删除</a></td>';
		trHtml += "</tr>";

		trHtml += '<tr><td colspan="2"  style="height:300px;">';
		
		trHtml += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
		trHtml += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
		trHtml += '</a>';
		trHtml += '</td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';

		index++;
		if (index == count) {
			//因为是偶数循环,所以奇数张图片的最后一张为空
			var trHtmlOdd = '<table style="width:400px;margin: auto;" class="">';
			trHtmlOdd += '<tr><td></td></tr>';	
			trHtmlOdd += '</table>';
		} else {
			path = '${ctx}' + data[index];
			pathDel = data[index];

			var trHtmlOdd = '<table style="width:400px;height:300px;margin: auto;" class="form">';
			
			trHtmlOdd += '<tr style="background: #d4d0d0;height: 35px;">';
			trHtmlOdd += '<td></td>';
			trHtmlOdd += '<td width="50px"><a id="uploadFile' + index + '" href="###" '+
					'onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\''+ ',' + '\'' + pathDel + '\'' + ');">删除</a></td>';
			trHtmlOdd += "</tr>";

			trHtmlOdd += '<tr><td colspan="2"  style="height:300px;">';
			
			trHtmlOdd += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
			trHtmlOdd += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
			trHtmlOdd += '</a>'
			trHtmlOdd += '</td></tr>';
			trHtmlOdd += '</table>';
		}
		trHtml += '<td>';
		trHtml += trHtmlOdd;
		trHtml += '</td>';
		trHtml += "</tr>";

		$('#' + id + ' tr.photo:eq(' + row + ')').after(trHtml);
		
		row++;

	}
}


function deletePhoto(tableId,tdTable,path) {
	
	var requisitionId = '${requisition.requisitionId }';
	var url = '${ctx}/business/requisition?methodtype='+tableId+'Delete';
	url+='&tabelId='+tableId+"&path="+path+"&requisitionId="+requisitionId;
	    
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
				photoView1(tableId, tdTable, countData, photo);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片删除失败,请重试。")
		}
	});
}

function uploadPhoto(tableId,tdTable, id) {

	var requisitionId = '${requisition.requisitionId }';
	var url = '${ctx}/business/materialRequisitionUpload'
			+ '?methodtype=uploadPhoto' + '&id=' + id + '&requisitionId=' + requisitionId;


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
				photoView1(tableId, tdTable, countData, photo);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片上传失败,请重试。")
		}
	});
}


</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
	
	<input type="hidden" id="makeType" value="${makeType }" />
	
	<fieldset>
		<legend> 直接领料申请单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">申请单编号：</td>					
				<td width="200px">${requisition.requisitionId }</td>
														
				<td width="100px" class="label">申请日期：</td>
				<td width="150px">${requisition.requisitionDate }</td>
				
				<td width="100px" class="label">申请人：</td>
				<td>${requisition.requisitionUser }</td>
			</tr>

			<tr> 				
				<td class="label" width="100px">物料编号：</td>					
				<td width="200px">${requisition.materialId }</td>
														
				<td width="100px" class="label">物料名称：</td>
				<td colspan="3">${requisition.materialName }</td>
				
			</tr>
			<tr> 				
				<td class="label">申请数量：</td>
				<td>${requisition.quantity }</td>
				
				<td width="100px" class="label">领料用途：</td>
				<td colspan="3">${requisition.usedType }</td>
				
			</tr>
			<tr> 				
				<td class="label">申请事由：</td>					
				<td colspan="5">
					<pre>${requisition.remarks }</pre></td>
				
			</tr>										
		</table>
	</fieldset>
	<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="doEdit" >编辑</a>
		<a class="DTTT_button DTTT_button_text" id="doDelete" >删除</a>
		<a class="DTTT_button DTTT_button_text" id="doPrint" onclick="doPrint();return false;" >打印</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<fieldset>
		<span id="photoAdd"><span class="tablename">附件清单</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button></span>
			<div class="" id="subidDiv" style="min-height: 300px;">
				<table id="productPhoto" class="phototable">
					<tbody><tr class="photo"><td></td><td></td></tr></tbody>
				</table>
			</div>
	</fieldset>
</form:form>

</div>
</div>
</body>

<script  type="text/javascript">

function doPrint(){
	
	var headstr = "<html><head><title></title></head><body>";  
	var footstr = "</body>";
	$("#DTTT_container").hide();
	$("#photoAdd").hide();
	window.print();
	$("#DTTT_container").show();
	$("#photoAdd").show();
	
}
</script>
</html>
