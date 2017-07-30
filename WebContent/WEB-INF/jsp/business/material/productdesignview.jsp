<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>产品设计明细-查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
</head>
<body>
<div id="container">
<div id="main">
	
<form:form modelAttribute="formModel" method="POST" 
	id="form" name="form"   autocomplete="off">
	
	<input type="hidden" id="PIId" value="${PIId}" />
	<input type="hidden" id="goBackFlag" value="${goBackFlag }" />
	<input type="hidden" id="productDetailId" value="${product.productDetailId}" />
	<form:hidden path="productDesign.ysid"  value="${product.YSId}" />
	<form:hidden path="productDesign.productid"  value="${product.productId}" />
	<form:hidden path="productDesign.productdetailid"  value="${product.productDetailId}" />
	<form:hidden path="productDesign.recordid"  value="${product.recordId}" />
	
<fieldset>
	<legend>做单资料</legend>

	<table class="form" >		
		<tr>
			<td class="label" style="width: 100px;">耀升编号：</td>
			<td style="width: 150px;">${product.YSId}</td>
								
			<td class="label" style="width: 100px;">产品编号：</td>
			<td style="width: 150px;">
				&nbsp;<a href="###" onClick="doShowProduct()">${product.productId}</a></td>
			
			<td class="label" style="width: 100px;">产品名称：</td>
			<td>${product.materialName}</td>
		</tr>
		<tr>				
			<td class="label">交货时间：</td>
			<td>${product.deliveryDate}</td>				
								
			<td class="label">交货数量：</td>
			<td>${product.quantity}</td>
			
			<td class="label">封样数量：</td>
			<td>${product.sealedSample}</td>		
		</tr>
		<tr>
			<td class="label">版本类别：</td>
			<td>${product.productClassify}</td>
			<td class="label" >电池包数量：</td>
			<td>${product.batteryPack}</td>
								
			<td class="label">充电器：</td>
			<td>${product.chargerType}</td>
				
		</tr>
		<tr>		
			<td class="label">包装描述：</td>
			<td colspan="5">${product.packageDescription }</td>
		</tr>
	</table>
	
	<div class="action" style="text-align: right;">
		<button type="button" id="doEdit" class="DTTT_button" >编辑</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</div>
</fieldset>	

<div id="tabs" style="padding: 0px;white-space: nowrap;">
	<ul>
		<li><a href="#tabs-1" class="tabs1">产品信息</a></li>
		<li><a href="#tabs-2" class="tabs2">标贴及文字印刷</a></li>
		<li><a href="#tabs-3" class="tabs3">包装</a></li>
	</ul>

	<div id="tabs-1" style="padding: 5px;">
		<fieldset>
			<legend>机器配置</legend>
			<div class="list">
			<table id="machineConfiguration" class="display" style="width:100%">
				<thead>				
					<tr style="text-align: left;">
						<th width="1px">No</th>
						<th style="width:60px">名称</th>
						<th style="width:120px">ERP编码</th>
						<th >产品名称</th>
						<th style="width:105px">采购方</th>
						<th style="width:255px">备注</th>
					</tr>
				</thead>
			</table>
			</div>
		</fieldset>
		<fieldset>
			<span class="tablename">产品图片</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
			<div class="list">
				<div class="" id="subidDiv" style="overflow: auto;height: 360px;">
					<table id="productPhoto" style="width:100%">
						<tbody><tr class="photo"><td></td><td></td></tr></tbody>
					</table>
				</div>
			</div>	
		</fieldset>
		<fieldset>
			<legend>塑料制品</legend>
			<div class="list">
			<table id="plastic" class="display" style="width:100%">
				<thead>				
					<tr style="text-align: left;">
						<th width="1px">No</th>
						<th style="width:60px">名称</th>
						<th style="width:120px">ERP编码</th>
						<th >产品名称</th>
						<th style="width:40px">材质</th>
						<th style="width:60px">颜色</th>
						<th style="width:60px">备注</th>
					</tr>
				</thead>			
			</table>
			</div>
		</fieldset>
		<fieldset>
			<legend>配件清单</legend>
			<div class="list">
			<table id="accessory" class="display"  style="width:100%">
				<thead>				
					<tr style="text-align: left;">
						<th width="1px">No</th>
						<th style="width:120px">配件名称及规格描述</th>
						<th style="width:40px">材质</th>
						<th style="width:60px">加工方式</th>
						<th style="width:60px">表面处理</th>
						<th style="width:60px">备注</th>
					</tr>
				</thead>			
			</table>
			</div>
		</fieldset>
		<fieldset>
			<span class="tablename">产品收纳</span>&nbsp;<button type="button" id="addStoragePhoto" class="DTTT_button">添加图片</button>
			<div class="list">
				<div class="" id="subidDiv" style="overflow: auto;height: 360px;">
					<table id="storagePhoto" style="width:100%">
						<tbody><tr class="photo"><td></td><td></td></tr></tbody>
					</table>
				</div>
			</div>	
		</fieldset>
	</div>
	
	<div id="tabs-2" style="padding: 5px;">
	<fieldset>
	<legend>标贴-描述</legend>
	<div class="list">
	<table id="labelT" class="display"  style="width:100%">
		<thead>				
			<tr style="text-align: left;">
				<th width="1px">No</th>
				<th style="width:60px">配件名称</th>
				<th style="width:40px">材质及要求</th>
				<th style="width:60px">尺寸</th>
				<th style="width:60px">备注</th>
			</tr>
		</thead>			
	</table>
	</div><br/>
	<span class="tablename">标贴-图片</span>&nbsp;<button type="button" id="addLabelPhoto" class="DTTT_button">添加图片</button>
	<div class="list">
		<div class="" id="subidDiv" style="overflow: auto;">
			<table id="labelPhoto" style="width:100%">
				<tbody><tr class="photo"><td></td></tr></tbody>
			</table>
		</div>
	</div>	
</fieldset>
<fieldset>
	<legend>文字印刷</legend>
	<div class="list">
	<table id="textPrint" class="display" style="width:100%">
		<thead>				
			<tr style="text-align: left;">
				<th width="1px">No</th>
				<th style="width:60px">配件名称</th>
				<th style="width:40px">材质</th>
				<th style="width:60px">尺寸</th>
				<th style="width:60px">颜色</th>
				<th style="width:120px">文件名</th>
				<th style="width:60px">操作</th>
			</tr>
		</thead>			
	</table>
	</div>
</fieldset>
	</div>
	<div id="tabs-3" style="padding: 5px;">
	<fieldset>
	<legend>包装描述</legend>
	<div class="list">
	<table id="package" class="display" style="width:100%">
		<thead>				
			<tr style="text-align: left;">
				<th style="width:1px">No</th>
				<th style="width:100px">名称</th>
				<th style="width:300px">材质</th>
				<th style="width:100px">装箱数量</th>
				<th style="width:200px">尺寸</th>
				<th style="width:60px">备注</th>
			</tr>
		</thead>		
	</table>
	</div>
	<br/>
	<span class="tablename">包装描述-图片</span>&nbsp;<button type="button" id="addPackagePhoto" class="DTTT_button">添加图片</button>
	<div class="list">
	<table id="packagePhoto" style="width:100%">
		<tbody><tr class="photo"><td></td><td></td></tr></tbody>
	</table>
	</div>	
</fieldset>
	</div>
</div>
		
<div style="clear: both"></div>		
	
</form:form>
</div>
</div>
<script type="text/javascript">

$(document).ready(function() {
			
	$( "#tabs" ).tabs();	
	//$("#tabs").tabs({ active: 2 });  //选择第二个tab为默认显示。

	productPhotoView();//产品图片
	productStoragePhotoView();//产品收纳
	machineConfigurationView();//机器配置
	plasticView();//塑料制品
	accessoryView();//配件清单
	labelView();//标贴
	textPrintView();//文字印刷
	packageView();//包装描述	

	$("#doEdit").click(function() {
		var PIId=$('#PIId').val();
		var YSId=$('#productDesign\\.ysid').val();
		var goBackFlag = $('#goBackFlag').val();
		var productDetailId=$('#productDesign\\.productdetailid').val();
		var url = '${ctx}/business/productDesign?methodtype=edit&YSId=' + YSId+
				"&productDetailId="+productDetailId+
				"&PIId="+PIId+
				"&goBackFlag="+goBackFlag;
		location.href = url;	 
	});
	
	$("#goBack").click(function() {
		var PIId=$('#PIId').val();
		var goBackFlag = $('#goBackFlag').val();
		if(goBackFlag == "1"){
			//该查看页面来自于一览
			var url = '${ctx}/business/productDesign?keyBackup='+ PIId;
			
		}else{
			var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
			
		}
		location.href = url;	
	});

	//产品图片添加位置,                                                                                                                                                                                        
	var productIndex = 1;
	$("#addProductPhoto").click(function() {
		
		var row = $("#productPhoto tbody tr.photo").length - 1;

		//从 1 开始
		var trHtml = addPhotoRow('productPhoto','uploadProductPhoto',productIndex);		

		$('#productPhoto tr.photo:eq('+row+')').after(trHtml);	
		productIndex++;	
		productIndex++;	
		//alert("row:"+row+"-----"+"::productIndex:"+productIndex)	
						
		
	});
	
	//产品收纳图片添加位置,
	var storageIndex = 1;
	$("#addStoragePhoto").click(function() {
		
		var row = $("#storagePhoto tbody tr.photo").length - 1;

		var trHtml = addPhotoRow('storagePhoto','uploadStoragePhoto',storageIndex);		

		$('#storagePhoto tr.photo:eq('+row+')').after(trHtml);	
		storageIndex++;
		storageIndex++;
						
		
	});
	
	//包装图片添加位置,
	var packageIndex = 1;
	$("#addPackagePhoto").click(function() {
		
		var row = $("#packagePhoto tbody tr.photo").length - 1;

		var trHtml = addPhotoRow('packagePhoto','uploadPackagePhoto',packageIndex);	
		
		$('#packagePhoto tr.photo:eq('+row+')').after(trHtml);	
		packageIndex++;	
		packageIndex++;	
		
	});
	
	//标贴图片添加位置,
	var labelIndex = 0;
	$("#addLabelPhoto").click(function() {
		
		var row = $("#labelPhoto tbody tr.photo").length - 1;

		var trHtml = addPhotoRowLabel('labelPhoto','uploadLabelPhoto',labelIndex);		

		$('#labelPhoto tr.photo:eq('+row+')').after(trHtml);	
		labelIndex++;
		labelIndex++;
						
		
	});
	
	/*
	$(".tabs1").click(function(){
		
	});
	$(".tabs2").click(function(){
		
	});
	$(".tabs3").click(function(){

		//packageView();//包装描述	
		//$('#package').DataTable().ajax.reload(null,false);
	});
	*/
    	
});


function productPhotoView() {

	var productDetailId = $("#productDetailId").val();
	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();

	$.ajax({
		"url" :"${ctx}/business/productDesign?methodtype=getProductPhoto&productDetailId="+productDetailId+"&YSId="+YSId+"&productId="+productId,	
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
            }
	});
	
}//产品图片


function productStoragePhotoView() {

	var productDetailId = $("#productDetailId").val();
	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();

	$.ajax({
		"url" :"${ctx}/business/productDesign?methodtype=getProductStoragePhoto&productDetailId="+productDetailId+"&YSId="+YSId+"&productId="+productId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["storageFileCount"];
			//alert(countData)
			photoView('storagePhoto','uploadStoragePhoto',countData,data['storageFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
            }
	});
	
}//产品收纳

function machineConfigurationView() {

	var productDetailId = $("#productDetailId").val();
	var table = $('#machineConfiguration').dataTable();
	if(table) {
		table.fnDestroy();
	}
	
	var t = $('#machineConfiguration').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : true,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : false,
		"bSort":false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/productDesign?methodtype=getMachineConfiguration&productDetailId="+productDetailId,	
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "componentName"},
			{"data": "materialId"},
			{"data": "materialName"},
			{"data": "purchaser"},
			{"data": "remark"},
		 ],
		
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
	
}//机器配置


function plasticView() {

	var productDetailId = $("#productDetailId").val();
	var table = $('#plastic').dataTable();
	if(table) {
		//table.fnClearTable();
		table.fnDestroy();
	}
	var t = $('#plastic').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : true,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : false,
		"bSort":false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/productDesign?methodtype=getPlastic&productDetailId="+productDetailId,	
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "componentName"},
			{"data": "materialId"},
			{"data": "materialName"},
			{"data": "materialQuality"},
			{"data": "color"},
			{"data": "remark"},
		 ],
		
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
	
}//塑料制品

function accessoryView() {

	var productDetailId = $("#productDetailId").val();

	var table = $('#accessory').dataTable();
	if(table) {
		//table.fnClearTable();
		table.fnDestroy();
	}
	var t = $('#accessory').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : true,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : false,
		"bSort":false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/productDesign?methodtype=getAccessory&productDetailId="+productDetailId,	
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "componentName"},
			{"data": "materialQuality"},
			{"data": "process"},
			{"data": "specification"},
			{"data": "remark"},
		 ],
		
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
	
}//配件清单


function labelView() {

	var productDetailId = $("#productDetailId").val();
	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();

	var table = $('#labelT').dataTable();
	if(table) {
		//table.fnClearTable();
		table.fnDestroy();
	}
	var t = $('#labelT').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : false,
		"bSort":false,
		"async" : false,
		"bAutoWidth":false,
		"sAjaxSource" : "${ctx}/business/productDesign?methodtype=getLabel&productDetailId="+productDetailId+"&YSId="+YSId+"&productId="+productId,	
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);			
					var countData = data["labelFileCount"];
					//alert(countData)
					photoViewLabel('labelPhoto','uploadLabelPhoto',countData,data['labelFileList'])		
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "componentName"},
			{"data": "materialQuality"},
			{"data": "size"},
			{"data": "remark"},
		 ],
		
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
	
}//标贴

function textPrintView() {

	var productDetailId = $("#productDetailId").val();
	var table = $('#textPrint').dataTable();
	if(table) {
		//table.fnClearTable();
		table.fnDestroy();
	}
	var text = $('#textPrint').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : true,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : false,
		"bSort":false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/productDesign?methodtype=getTextPrint&productDetailId="+productDetailId,	
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "componentName"},
			{"data": "materialQuality"},
			{"data": "size"},
			{"data": "color"},
			{"data": "fileName"},
			{"data": null,"className" : 'td-center'},
		],
		"columnDefs":[
    		{"targets":6,"render":function(data, type, row){
    			var recordId=row["recordId"];
    			var id = row["rownum"];
    			var componentName = row["componentName"];
    			var fileName = row["fileName"];
    			var html="";
    			if(fileName == null || fileName ==""){
    				html+='<input name="pdfFile" onchange="uploadFileFn('+'\''+recordId+'\''+');" type="file" size="30" />'; 
    				//html+='<div id="textPrintDelete'+id+'" style="display:none"><a href="###" onclick="downloadFile('+'\''+fileName+'\''+','+id+')">下载</a>&nbsp;<a href="###" onclick="deleteTextPrintFile('+'\''+recordId+'\''+')">删除</a></div>'
    			}else{
       				//html+='<div id="textPrintUpload'+id+'" style="display:none"><input name="pdfFile" onchange="uploadFileFn('+'\''+recordId+'\''+','+id+');" type="file" size="30" /></div>'; 
    				html+='<a href="###" onclick="downloadFile('+'\''+fileName+'\''+')">下载</a>&nbsp;<a href="###" onclick="deleteTextPrintFile('+'\''+recordId+'\''+','+'\''+componentName+'\''+')">删除</a>'
    				//html+='<div id="textPrintDelete'+id+'"><a href="'+"${ctx}" + fileName+'" onclick="downloadFile('+'\''+fileName+'\''+','+id+')">下载</a>&nbsp;<a href="###" onclick="deleteTextPrintFile('+'\''+recordId+'\''+','+id+')">删除</a></div>'
   				
    			}
				return html;
				
    		}}
    	]
		
	});

			
	text.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            text.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
		
	});
	
	text.on('order.dt search.dt draw.dt', function() {
		text.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			cell.innerHTML = i + 1;
		});
	}).draw();
	
}//文字印刷


function packageView() {
	var productDetailId = $("#productDetailId").val();
	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();
	
	var table = $('#package').dataTable();
	if(table) {
		//table.fnClearTable(false);
		table.fnDestroy();
	}
	var t = $('#package').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : false,
		"bSort":false,
		"async" : false,
		"bAutoWidth":false,
		//"sAjaxSource" : "${ctx}/business/productDesign?methodtype=getPackage",	
		"sAjaxSource" : "${ctx}/business/productDesign?methodtype=getPackage&productDetailId="+productDetailId+"&YSId="+YSId+"&productId="+productId,
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);	

					var countData = data["packageFileCount"];
					photoView('packagePhoto','uploadPackagePhoto',countData,data['packageFileList'])
					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "componentName"},
			{"data": "materialQuality"},
			{"data": "packingQty"},
			{"data": "size"},
			{"data": "remark"},
		 ],
		
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
	
}//包装描述


</script>
<script type="text/javascript">

function deletePhoto(tableId,tdTable,path) {
	
	var url = '${ctx}/business/productDesign?methodtype='+tableId+'Delete';
	url+='&tabelId='+tableId+"&path="+path;
	    
	if(!(confirm("确定要删除该图片吗？"))){
		return;
	}
    $("#form").ajaxSubmit({
		type: "POST",
		url:url,	
		data:$('#form').serialize(),// 你的formid
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
				case "storagePhoto":
					countData = data["storageFileCount"];
					photo = data['storageFileList'];
					break;
				case "labelPhoto":
					countData = data["labelFileCount"];
					photo = data['labelFileList']
					flg = "false";
					break;
				case "packagePhoto":
					countData = data["packageFileCount"];
					photo = data['packageFileList']
					break;
				default:
					break;
			}
			
			//删除后,刷新现有图片
			$("#" + tableId + " tr:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}else{
				photoViewLabel(tableId, tdTable, countData, photo);
				
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error:"+errorThrown)
		}
	});
}

function uploadPhoto(tableId,tdTable, id) {

	var url = '${ctx}/business/productDesignPhotoUpload'
			+ '?methodtype=' + tdTable + '&id=' + id;
	//alert(url)
	$("#form").ajaxSubmit({
		type : "POST",
		url : url,
		data : $('#form').serialize(),// 你的formid
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
				case "storagePhoto":
					countData = data["storageFileCount"];
					photo = data['storageFileList'];
					break;
				case "labelPhoto":
					countData = data["labelFileCount"];
					photo = data['labelFileList']
					flg = "false";
					break;
				case "packagePhoto":
					countData = data["packageFileCount"];
					photo = data['packageFileList']
					break;
				default:
					break;
			}
			
			//添加后,刷新现有图片
			$("#" + tableId + " tr:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}else{
				photoViewLabel(tableId, tdTable, countData, photo);
				
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error:"+errorThrown)
		}
	});
}

function photoView(id, tdTable, count, data) {
	
	//alert("id:"+id+"--count:"+count+"--countView:"+countView)	
	var row = 0;
	for (var index = 0; index < count; index++) {

		var path = '${ctx}' + data[index];
		var pathDel = data[index];
		//alert(index+"::::::::::::"+path)
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td>';
		trHtml += '<table style="width:400px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td></td>';
		trHtml += '<td width="50px"><a id="uploadFile' + index + '" href="###" '+
				'onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\''+ ',' + '\'' + pathDel + '\'' + ');">删除</a></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;">';
				//'<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">'+
		trHtml += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
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
			trHtmlOdd += '<tr>';
			trHtmlOdd += '<td></td>';			
			trHtmlOdd += '<td width="50px"></td>';			
			trHtmlOdd += "</tr>";
			trHtmlOdd += '<tr><td colspan="2"  style="height:300px;"></td>';
		} else {
			path = '${ctx}' + data[index];
			pathDel = data[index];

			var trHtmlOdd = '<table style="width:400px;margin: auto;" class="form">';
			trHtmlOdd += '<tr style="background: #d4d0d0;height: 35px;">';
			trHtmlOdd += '<td></td>';			
			trHtmlOdd += '<td width="50px">';
			trHtmlOdd += '<a id="uploadFile1' + index + '" href="###" onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\''+ tdTable + '\'' + ','+ '\'' + pathDel + '\'' + ');">删除';
			trHtmlOdd += '</a>';
			trHtmlOdd += '</td>';
			trHtmlOdd += '</tr>';
			trHtmlOdd += '<tr>';
			trHtmlOdd += '<td colspan="2"  style="height:300px;">';
			trHtmlOdd += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
			trHtmlOdd += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
			trHtmlOdd += '</a>'
			trHtmlOdd += '</td>';
		}

		trHtml += '<td>';
		//trHtml += '<table style="width:400px;margin: auto;" class="form">';
		//trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += trHtmlOdd;
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		trHtml += "</tr>";

		$('#' + id + ' tr.photo:eq(' + row + ')').after(trHtml);
		row++;

	}

}


function photoViewLabel(id, tdTable, count, data) {
	
	//alert("id:"+id+"--count:"+count+"--countView:"+countView)	
	var row = 0;
	for (var index = 0; index < count; index++) {

		var path = '${ctx}' + data[index];
		var pathDel = data[index];
		//alert(index+"::::::::::::"+path)
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';

		trHtml += '<td>';
		trHtml += '<table style="width:100%;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td></td>';
		trHtml += '<td width="50px"><a id="uploadFile' + index + '" href="###" '+
				'onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\''+ ',' + '\'' + pathDel + '\'' + ');">删除</a></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;">'
		trHtml += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
		trHtml += '<img id="imgFile'+tdTable+index+'" src="'+path+'"  style="max-height: 450px;" />';
		trHtml += '</a>';
		trHtml += '</td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		trHtml += "</tr>";

		$('#' + id + ' tr.photo:eq(' + row + ')').after(trHtml);
		row++;

	}

}

function addPhotoRowLabel(id,tdTable, index) {

	for (var i = 0; i < 1; i++) {

		var path = '${ctx}' + "/images/blankDemo.png";
		var pathDel = '';
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';

		trHtml += '<td>';
		trHtml += '<table style="width:100%;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td><div id="uploadFile'+tdTable+index+'" ><input type="file"  id="photoFile" name="photoFile" '+
				'onchange="uploadPhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\'' + ',' + index + ');" accept="image/*" style="max-width: 250px;" /></div></td>';
		//trHtml+='<td><div id="uploadFile'+index+'" ></div></td>';
		trHtml += '<td width="50px"><div id="deleteFile'+tdTable+index+'" style="display:none"><a href="###" '+
				'onclick=\"deletePhoto(' + '\'' + id + '\'' + ','+ '\''+ tdTable + '\'' + ',' + '\'' + pathDel+ '\''+ ')\">删除</a></div></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;"><img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  /></td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		trHtml += "</tr>";
	
	}//for		

	return trHtml;
}

function addPhotoRow(id,tdTable, index) {
	
	for (var i = 0; i < 1; i++) {

		var path = '${ctx}' + "/images/blankDemo.png";
		var pathDel = '';
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';

		trHtml += '<td>';
		trHtml += '<table style="width:400px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td><div id="uploadFile'+tdTable+index+'" ><input type="file"  id="photoFile" name="photoFile" '+
				'onchange="uploadPhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\'' + ',' + index + ');" accept="image/*" style="max-width: 250px;" /></div></td>';
		//trHtml+='<td><div id="uploadFile'+index+'" ></div></td>';
		trHtml += '<td width="50px"><div id="deleteFile'+tdTable+index+'" style="display:none"><a href="###" '+
				'onclick=\"deletePhoto(' + '\'' + id + '\'' + ','+ '\''+ tdTable + '\'' + ',' + '\'' + pathDel+ '\''+ ')\">删除</a></div></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;"><img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  /></td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';

		index++;

		trHtml += '<td>';
		trHtml += '<table style="width:400px;margin: auto;" class="">';
		trHtml += '<tr style="height: 35px;">';
		trHtml += '<td></td>';
		trHtml += '<td width="50px"></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;"></td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		trHtml += "</tr>";
	
	}//for		

	return trHtml;
}
function uploadFileFn(recordId, id) {
	//alert("recordeId:"+recordId+"---id:"+id)
	var url = '${ctx}/business/productDesignFileUpload?methodtype=uploadTextPrintFile'
			+ '&id=' + id + '&recordId=' + recordId;
	//alert(url)
	$("#form").ajaxSubmit({
		type : "POST",
		url : url,
		data : $('#form').serialize(),// 你的formid
		dataType : 'json',
		success : function(data) {
			if (data.result == '0') {
				$('#textPrint').DataTable().ajax.reload(null,false);

			} else {
				alert(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error:"+errorThrown)
		}
	});
}

function downloadFile(fileName) {

	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();
	var url = '${ctx}/business/productDesign?methodtype=downloadFile&fileName='
			+ fileName + "&productId=" + productId + "&YSId=" + YSId;
	url =encodeURI(encodeURI(url));//中文两次转码

	location.href = url;
}

function deleteTextPrintFile(recordId,componentName){
	
	if(confirm("确定要删除文件 [ "+componentName+" ] 吗？")){
		var url = '${ctx}/business/productDesign?methodtype=textPrintFileDelete'+ '&recordId=' + recordId;
		//alert(url)
		$("#form").ajaxSubmit({
			type : "POST",
			url : url,
			data : $('#form').serialize(),// 你的formid
			dataType : 'json',
			success : function(data) {
				if (data.message == "操作成功") {
					$().toastmessage('showWarningToast', "文件已被删除。");
					$('#textPrint').DataTable().ajax.reload(null,false);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("error:"+errorThrown)
			}

		});
	}
	
}


function doShowProduct() {
	var materialId = '${product.productId}';
	callProductView(materialId);
	/*
	var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;
	var index = parent.layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '500px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		//cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		//    layer.close(index)
		 // }
		 // return false; 
		//}    
	});
	parent.layer.full(index);
	*/
}

</script>
<script>
function bigImage(tdTable,index,path){

	  var sWidth = $(window).width()+"px";
	  var sHeight= $(window).height()+"px";
	  var tWidth = ($(window).width() - 100 )+"px"
	  var tHeight= ($(window).height() - 50 )+"px"
	 //alert("sWidth:"+sWidth+"----tWidth:"+tWidth)
 	//var imgUrl = $("#linkFile"+tdTable+index).attr('data-src');
 	var imgUrl = path
 	// window.img_width = 1024;
	var html  = "<table style='height:"+tHeight+";width:"+tWidth+"'><tr><td style='text-align: center;'>";
 	//html += "<span style='height:100%;display:inline-block;vertical-align:middle;'></span>"
   //html += "<div style='position:fixed; left:50%; margin-left:-40px; bottom:5px;'>";
   //html += "<div>";
   //html += "  <button style='width:40px; height:40px; border-radius:20px;'";
   //html += "        onclick='window.img_width < 1000 ? window.img_width += 100 : 0;";
   //html += "  $(\".img_url_class\").css({width: window.img_width});'>";
   //html += "  放大";
   //html += "  </button>&nbsp; &nbsp; ";
  // html += "  <button style='width:40px;  height:40px;border-radius:20px;'";
   //html += "  onclick='parent.window.img_width > 100 ? wparent.indow.img_width -= 100 : 0;";
   //html += "  $(\".img_url_class\").css({width: window.img_width})' >"; 
   //html += "  缩小"; 
 //  html += "  </button>";
   //html += "</div>";
   //html += "</center>";
   //html += "<br/>";
   //html += "<center>";
   html += "<img class='img_url_class' style='' src='"+imgUrl+"' />";
   //html += "<img class='img_url_class' style='width:100%;' src='"+imgUrl+"' />";
   html += "</td></tr></table>";
  //页面层
 var idx = parent.layer.open({
    	title:'原始图预览',
    	type: 1,
		scrollbar : true,
   		skin: 'layui-layer-rim', //加上边框
   		area: [sWidth, sHeight], //宽高
   		content: html
  	});
 //parent.layer.full(idx);
}
</script>
	</body>
</html>
