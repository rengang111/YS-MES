<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>配件产品设计资料-编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>

<script type="text/javascript">
	//标贴
	var labelCont = 0;
	var dbCnt4 = '${labelCount}';
	if(dbCnt4 >0 ){
		labelCont = Number(dbCnt4)-1;
	}

	$.fn.dataTable.TableTools.buttons.add_rows4 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = labelCont;
				var hidden = '';
				
				for (var i=0;i<1;i++){
					
					rowIndex =  rowIndex+1;
					var hidden = "";
					
					hidden = '';
					
					var rowNode = $('#labelT')
						.DataTable()
						.row
						.add(
						  [
							'<td class="dt-center"></td>',
							'<td><input  name="labelList['+rowIndex+'].componentname"   id="labelList'+rowIndex+'.componentname"  class="short" /></td>',
							'<td><input  name="labelList['+rowIndex+'].materialquality" id="labelList'+rowIndex+'.materialquality" class="middle" /></td>',
							'<td><input  name="labelList['+rowIndex+'].size"            id="labelList'+rowIndex+'.size" class="short" /></td>',
							'<td><input  name="labelList['+rowIndex+'].remark"   	    id="labelList'+rowIndex+'.remark"   class="middle" /></td>',
							
							]).draw();
					
					rowIndex ++;						
				}					
				labelCont += 1;
				
				foucsInit();//设置新增行的基本属性
				
				autocomplete();//调用自动填充功能
				
			}
		});

	$.fn.dataTable.TableTools.buttons.reset4 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {

			var t=$('#labelT').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			}else{
				
				//var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				
				$().toastmessage('showWarningToast', "删除后,请点击[ 保存 ]按钮。");	
				t.row('.selected').remove().draw();
	
			}
					
		}
	});
	
	function labelView() {

		var t = $('#labelT').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : 'T<"clear">rt',
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_rows4",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset4",
					"sButtonText" : "清空一行"
				}  ],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {	
					}			
				]
			
		}).draw();

				
		t.on('click', 'tr', function() {
			
			var rowIndex = $(this).context._DT_RowIndex; //行号			
			//alert(rowIndex);

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

</script>

<script type="text/javascript">
	//包装描述
	var packageCont = 0;
	var dbCnt6 = '${labelCount}';
	if(dbCnt6 >0 ){
		packageCont = Number(dbCnt6)-1;
	}

	$.fn.dataTable.TableTools.buttons.add_rows6 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = packageCont;
				var hidden = '';
				
				for (var i=0;i<1;i++){
					
					rowIndex =  rowIndex+1;
					var hidden = "";
					
					hidden = '';
					
					var rowNode = $('#package')
						.DataTable()
						.row
						.add(
						  [
							'<td class="dt-center"></td>',
							'<td><input  name="packageList['+rowIndex+'].componentname"   id="packageList'+rowIndex+'.componentname"  class="short" /></td>',
							'<td><input  name="packageList['+rowIndex+'].materialquality" id="packageList'+rowIndex+'.materialquality" class="middle" /></td>',
							'<td><input  name="packageList['+rowIndex+'].packingqty"      id="packageList'+rowIndex+'.packingqty" class="short" /></td>',
							'<td><input  name="packageList['+rowIndex+'].size"            id="packageList'+rowIndex+'.size" class="short" /></td>',
							'<td><input  name="packageList['+rowIndex+'].remark"   	      id="packageList'+rowIndex+'.remark"   class="middle" /></td>',
							
							]).draw();
					
					rowIndex ++;						
				}					
				packageCont += 1;
				
				foucsInit();//设置新增行的基本属性
				
				autocomplete();//调用自动填充功能
				
			}
		});

	$.fn.dataTable.TableTools.buttons.reset6 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {

			var t=$('#package').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			}else{
				
				//var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				
				$().toastmessage('showWarningToast', "删除后,请点击[ 保存 ]按钮。");	
				t.row('.selected').remove().draw();
	
			}
					
		}
	});
	
	function packageView() {

		var t = $('#package').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : 'T<"clear">rt',
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_rows6",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset6",
					"sButtonText" : "清空一行"
				}  ],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {	
					}, {				
					}			
				]
			
		}).draw();

				
		t.on('click', 'tr', function() {
			
			var rowIndex = $(this).context._DT_RowIndex; //行号			
			//alert(rowIndex);

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

</head>
<body>
<div id="container">
<div id="main">
	
<form:form modelAttribute="formModel" method="POST"
	id="form" name="form"   autocomplete="off">
	
	<input type="hidden" id="PIId" value="${PIId}" />
	<input type="hidden" id="goBackFlag" value="${goBackFlag }" />
	<form:hidden path="productDesign.recordid"  value="${product.recordId}" />
	<form:hidden path="productDesign.ysid"  value="${YSId}" />
	<form:hidden path="productDesign.productid"  value="" />
	<form:hidden path="productDesign.productdetailid"  value="${product.productDetailId}" />
	<form:hidden path="productDesign.subid"  value="${product.subId}" />
	
<fieldset>
	<legend>做单资料</legend>
	<table class="form" >		
		<tr>
			<td class="label" style="width: 100px;">耀升编号：</td>
			<td style="width: 150px;">${YSId}</td>
								
			<td class="label" style="width: 100px;">产品编号：</td>
			<td style="width: 150px;">&nbsp;${product.productId}</td>
			
			<td class="label" style="width: 100px;">产品名称：</td>
			<td colspan="3">&nbsp;${product.materialName}</td>
		</tr>
		<tr>				
			<td class="label">交货时间：</td>
			<td>${product.deliveryDate}</td>				
								
			<td class="label">交货数量：</td>
			<td>&nbsp;${product.quantity}</td>

			<td class="label">版本类别：</td>
			<td colspan="3">&nbsp;${product.productClassify}</td>			
		</tr>
		<tr>
			<td class="label">包装描述：</td>
			<td colspan="3"><form:input path="productDesign.packagedescription"  class="long"/></td>

			<td class="label">封样数量：</td>
			<td><form:input path="productDesign.sealedsample"  class="short"/></td>		
								
			<td class="label">资料完成状况：</td>
			<td style="width: 150px;">
				<form:select path="productDesign.status" style="width: 100px;">							
					<form:options items="${statusList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
		</tr>
	</table>
</fieldset>	
		
<div style="clear: both"></div>		
<div class="action" style="text-align: right;margin-right: 10px;">
	<button type="button" id="doSave" class="DTTT_button" >保存</button>
	<button type="button" id="goBack" class="DTTT_button">返回</button>
</div>

<fieldset>
	<span class="tablename">产品图片</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
	<div class="list">
		<div class="showPhotoDiv" style="overflow: auto;">
			<table id="productPhoto" style="width:100%;height:335px">
				<tbody><tr><td class="photo"></td></tr></tbody>
			</table>
		</div>
	</div><br/>
	<span class="tablename">标贴图片</span>&nbsp;<button type="button" id="addLabelPhoto" class="DTTT_button">添加图片</button>
	<div class="list">
		<div class="" id="subidDiv" style="overflow: auto;">
			<table id="labelPhoto" style="width:100%;height:300px">
				<tbody><tr class="photo"><td></td></tr></tbody>
			</table>
		</div>
	</div>
</fieldset>
<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;margin-top: 20px;">产品描述</legend>
	<div class="list">
	<table id="orderBom1" class="display" >
		<thead>		
			<tr>
				<td>
					<form:textarea path="productDesign.storagedescription" style="width: 800px; height: 150px;margin-top: 30px;"/>
				</td>

			</tr>
		</thead>			
	</table>
	</div>
</fieldset>
<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;">标贴描述</legend>
	<div class="list">
	<table id="labelT" class="display">
		<thead>				
			<tr style="text-align: left;">
				<th width="1px">No</th>
				<th style="width:60px">名称</th>
				<th style="width:40px">材质及要求</th>
				<th style="width:60px">尺寸</th>
				<th style="width:60px">备注</th>
			</tr>
		</thead>	
		<tbody>
			<c:forEach var="list" items="${labelList}" varStatus="status">
				<tr>
					<td></td>
					<td><form:input path="labelList[${status.index}].componentname"  class="short" value="${list.componentName }" /></td>
					<td><form:input path="labelList[${status.index}].materialquality"  class="middle" value="${list.materialQuality }" /></td>
					<td><form:input path="labelList[${status.index}].size"  class="short" value="${list.size }" /></td>
					<td><form:input path="labelList[${status.index}].remark"  class="middle" value="${list.remark }" /></td>
				</tr>
			
			</c:forEach>
		</tbody>				
	</table>
	</div>	
</fieldset>

<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;">包装描述</legend>
	<div class="list">
	<table id="package" class="display">
		<thead>				
			<tr style="text-align: left;">
				<th width="1px">No</th>
				<th style="width:60px">名称</th>
				<th style="width:40px">材质</th>
				<th style="width:60px">装箱数量</th>
				<th style="width:60px">尺寸</th>
				<th style="width:60px">备注</th>
			</tr>
		</thead>	
		<tbody>
			<c:forEach var="i" items="${packageList}" varStatus="status">
				<tr>
					<td></td>
					<td><form:input path="packageList[${status.index}].componentname"  class="short" value="${i.componentName }" /></td>
					<td><form:input path="packageList[${status.index}].materialquality"  class="middle" value="${i.materialQuality }" /></td>
					<td><form:input path="packageList[${status.index}].packingqty"  class="short" value="${i.packingQty }" /></td>
					<td><form:input path="packageList[${status.index}].size"  class="short" value="${i.size }" /></td>
					<td><form:input path="packageList[${status.index}].remark"  class="middle" value="${i.remark }" /></td>
				</tr>
			
			</c:forEach>
		</tbody>				
	</table>
	</div>
</fieldset>
</form:form>
</div>
</div>

<script type="text/javascript">
$(document).ready(function() {

	$('#productDesign\\.status').val('${product.statusId }');
	$("#productDesign\\.storagedescription").val(replaceTextarea('${product.storageDescription}'));

	
	labelView();//标贴
	packageView();//包装描述
	
	productPhotoView();//产品图片
	labelPhotoView();//标贴图片
	
	
	$("#goBack").click(function() {

		var PIId=$('#PIId').val();
		var goBackFlag = $('#goBackFlag').val();
		if(goBackFlag == "productDesignMain"){
			//该查看页面来自于一览
			var url = '${ctx}/business/productDesign?keyBackup='+ PIId;
			
		}else{
			var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
			
		}
		location.href = url;			
	});

	$("#doSave").click(function() {
		var PIId=$('#PIId').val();
		var goBackFlag = $('#goBackFlag').val();
		$('#form').attr("action", 
				"${ctx}/business/productDesign?methodtype=accessoryUpdate&PIId=" + PIId+"&goBackFlag="+goBackFlag);
		$('#form').submit();	

	});

	foucsInit();//设置input的基本属性

	//横向滚动条宽度计算
	var maxW = $("#container").width();
	var DivWidth = maxW -80 + "px";
	$(".showPhotoDiv").css("width",DivWidth);

	//产品图片添加位置,                                                                                                                                                                                        
	var productIndex = 1;
	$("#addProductPhoto").click(function() {
		
		var cols = $("#productPhoto tbody td.photo").length - 1;

		//从 1 开始
		var trHtml = addPhotoRow('productPhoto','uploadProductPhoto',productIndex);		

		$('#productPhoto td.photo:eq('+0+')').after(trHtml);	
		productIndex++;		
		//alert("row:"+row+"-----"+"::productIndex:"+productIndex)	
						
		
	});	
	
	//标贴图片添加位置,
	var labelIndex = 0;
	$("#addLabelPhoto").click(function() {
		
		var row = $("#labelPhoto tbody tr.photo").length - 1;

		var trHtml = addPhotoRowLabel('labelPhoto','uploadLabelPhoto',labelIndex);		

		$('#labelPhoto tr.photo:eq('+row+')').after(trHtml);	
		labelIndex++;
						
		
	});
    	
});
</script>
<script type="text/javascript">

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

function labelPhotoView() {

	var productDetailId = $("#productDetailId").val();
	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();

	$.ajax({
		"url" :"${ctx}/business/productDesign?methodtype=getLabelPhoto&productDetailId="+productDetailId+"&YSId="+YSId+"&productId="+productId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){

			var countData = data["labelFileCount"];
			photoViewLabel('labelPhoto','uploadLabelPhoto',countData,data['labelFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
            }
	});
	
}//标贴图片

</script>
<script type="text/javascript">

function foucsInit(){
	
	$("#labelT input").addClass('bsolid').addClass('bsolid');
	$("#package input").addClass('bsolid').addClass('bsolid');	
}
</script>

<script type="text/javascript">

function autocomplete(){
	
	$(".materialid").autocomplete({
		minLength : 2,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			var materialId = $('#productDesign\\.productid').val();
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/productDesign?methodtype=getSupplierFromBom",
				dataType : "json",
				data : {
					key1 : request.term,
					key2 : materialId,
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.materialId+" | "+item.materialName,
									value : item.materialId,
									id : item.materialId,
									name : item.materialName,
									materialId : item.materialId,
									supplierId : item.supplierId
								}
							}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
					//alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},

		select : function(event, ui) {			
			//产品名称
			$(this).parent().parent().find("td").eq(3).find("span")
				.html(jQuery.fixedWidth(ui.item.name,40));
			//供应商
			$(this).parent().parent().find("td").eq(4).find("span")
				.text(ui.item.supplierId);			
		},
	});
}

</script>


<script type="text/javascript">

function photoView(id, tdTable, count, data) {
	
	//alert("id:"+id+"--count:"+count+"--countView:"+countView)	
	var row = 0;
	for (var index = 0; index < count; index++) {

		var path = '${ctx}' + data[index];
		var pathDel = data[index];
		//alert(index+"::::::::::::"+path)
		var trHtml = '';

		//trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td class="photo" style="text-align:center;padding: 10px;">';
		trHtml += '<table style="width:400px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td></td>';
		trHtml += '<td width="50px"><a id="uploadFile' + index + '" href="###" '+
				'onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\''+ ',' + '\'' + pathDel + '\'' + ');">删除</a></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;">';
		trHtml +='<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
		//trHtml += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage2(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
		trHtml += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
		trHtml += '</a>';
		trHtml += '</td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';

		$('#' + id + ' td.photo:eq(' + row + ')').after(trHtml);
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
		trHtml += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
	  //trHtml += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage2(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
		trHtml += '<img id="imgFile'+tdTable+index+'" src="'+path+'"  style="max-height: 100%;" />';
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
		trHtml += '<td width="50px"><div id="deleteFile'+tdTable+index+'"><a href="###" '+
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

		//trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td class="photo" style="text-align:center;padding: 10px;">';
		trHtml += '<table style="width:400px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td><div id="uploadFile'+tdTable+index+'" ><input type="file"  id="photoFile" name="photoFile" '+
				'onchange="uploadPhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\'' + ',' + index + ');" accept="image/*" style="max-width: 250px;" /></div></td>';
		trHtml += '<td width="50px"><div id="deleteFile'+tdTable+index+'" ><a href="###" '+
				'onclick=\"deletePhoto(' + '\'' + id + '\'' + ','+ '\''+ tdTable + '\'' + ',' + '\'' + pathDel+ '\''+ ')\">删除</a></div></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;text-align: center;""><img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  /></td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
				
	}//for		

	return trHtml;
}

function doShowProduct() {
	var materialId = '${product.productId}';
	callProductView(materialId);
}

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
			$("#" + tableId + " td:gt(0)").remove();
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
			$("#" + tableId + " td:gt(0)").remove();
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

</script>

</body>
</html>
