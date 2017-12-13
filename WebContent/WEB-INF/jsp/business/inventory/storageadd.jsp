<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-入库登记</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var shortYear = ""; 
	
	function ajax() {

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
			//"scrollY"    : "160px",
	        //"scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',		
			"columns" : [
			        	{"className":"dt-body-center"
					}, {"className":"td-left"
					}, {
					}, {"className":"td-right"
					}, {"className":"td-right"
					}, {"className":"td-right"	
					}, {"className":"td-left"
					}, {
					}
				],
			"columnDefs":[
    			{"targets":2,"render":function(data, type, row){
	    			var name = data;				    			
	    			name = jQuery.fixedWidth(name,35);				    			
	    			return name;
    			}}           
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

	};

		
	$(document).ready(function() {

		//设置光标项目
		//$("#attribute1").focus();
		//$("#order\\.piid").attr('readonly', "true");

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#stock\\.checkindate").val(shortToday());
		
		ajax();
		productPhotoView();//供应商的入库单
		
		$("#stock\\.checkindate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$("#goBack").click(
				function() {
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/storage?keyBackup="+contractId;
					location.href = url;		
				});
		$("#showStockin").click(
				function() {
					var contractId='${contract.contractId }';
					var arrivelId= $('#stock\\.arrivelid').val();
					var url = "${ctx}/business/storage?methodtype=showHistory&contractId="+contractId
							+"&arrivalId="+arrivelId;
					location.href = url;		
				});
		$("#insert").click(
				function() {					
			$('#form').attr("action", "${ctx}/business/storage?methodtype=insert");
			$('#form').submit();
		});
		
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
				
		foucsInit();

		$(".quantity").attr('readonly', "true");
		$(".quantity").addClass('read-only');
		$(".quantity").removeClass('bgnone');
		

		//横向滚动条宽度计算
		var maxW = $("#container").width();
		var DivWidth = maxW -80 + "px";
		$(".showPhotoDiv").css("width",DivWidth);
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/arrival?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}
		
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="form" name="form"  autocomplete="off">

	<form:hidden path="stock.subid" />
	<form:hidden path="stock.ysid"  value="${contract.YSId }"/>
	<form:hidden path="stock.arrivelid"  value="${head.arrivalId }"/>
	<form:hidden path="stock.supplierid"  value="${contract.supplierId }"/>
	<form:hidden path="stock.contractid"  value="${contract.contractId }"/>
	
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="200px">&nbsp;${contract.YSId }</td>
							
				<td width="100px" class="label">成品编码：</td>
				<td width="200px">&nbsp;${contract.productId }</td>							
				<td width="100px" class="label">成品名称：</td>
				<td>${contract.productName }</td>
			</tr>
			<tr>							
				<td class="label">合同编号：</td>					
				<td>&nbsp;<a href="#" onClick="showContract('${contract.contractId }')">${contract.contractId }</a></td>								 	
				<td class="label">供应商：</td>					
				<td colspan="3">&nbsp;${contract.supplierId }（${contract.shortName }）${contract.fullName }</td>	
			</tr>
			<tr> 				
				<td class="label" width="100px">入库时间：</td>					
				<td width="200px">
					<form:input path="stock.checkindate" class="read-only" /></td>
							
				<td width="100px" class="label">仓管员：</td>
				<td width="200px">
					<form:input path="stock.keepuser" class="short read-only" value="${userName }" /></td>							
				<td class="label">入库件数：</td>
				<td><form:input path="stock.packagnumber" class="short num" value="" /></td>
			</tr>
										
		</table>
	</fieldset>
		
	<fieldset class="action" style="text-align: right;margin-top:-20px">
		<button type="button" id="insert" class="DTTT_button">确认入库</button>
		<button type="button" id="showStockin" class="DTTT_button">查看入库记录</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>	
	<fieldset style="margin-top: -40px;">
		<legend> 物料信息</legend>
		<div class="list">
		<table class="display" id="example">	
			<thead>		
				<tr>
					<th style="width:1px">No</th>
					<th style="width:100px">物料编号</th>
					<th>物料名称</th>
					<th style="width:65px">合同数量</th>
					<th style="width:65px">已入库数量</th>
					<th style="width:65px">待入库数</th>
					<th style="width:55px">包装方式</th>
					<th style="width:60px">库位编号</th>		
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${material}" varStatus='status' >			
					<tr>
						<td></td>
						<td>${list.materialId }
							<form:hidden path="stockList[${status.index}].materialid" value="${list.materialId }"/></td>
						<td>${list.materialName }</td>
						<td>${list.contractQuantity }</td>
						<td>${list.contractStorage }</td>
						<td><form:input path="stockList[${status.index}].quantity"  value="${list.quantityQualified }" class="num short quantity" /></td>
						<td><form:select path="stockList[${status.index}].packaging" style="width:70px">
								<form:options items="${packagingList}" 
									itemValue="key" itemLabel="value"/></form:select></td>
						<td><form:input path="stockList[${status.index}].areanumber" class="short" /></td>
					</tr>
					<script type="text/javascript">
							var index = '${status.index}';
					</script>
				
				</c:forEach>
			
		</tbody>
										
		</table>

		</div>
	</fieldset>
	<fieldset>
		<span class="tablename">供应商的入库单</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
		<div class="list">
			<div class="showPhotoDiv" style="overflow: auto;">
				<table id="productPhoto" style="width:100%;height:335px">
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

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	openLayer(url);

};

function showYS(YSId) {
	var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;

	//var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
	openLayer(url);

};

</script>

<script type="text/javascript">

function productPhotoView() {

	var contractId = $("#stock\\.contractid").val();
	var YSId = $("#stock\\.ysid").val();
	var supplierId = $("#stock\\.supplierid").val();

	$.ajax({
		"url" :"${ctx}/business/storage?methodtype=getProductPhoto&YSId="+YSId+"&supplierId="+supplierId+"&contractId="+contractId,	
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
         	alert(errorThrown)
		 }
	});
	
}//产品图片

function photoView(id, tdTable, count, data) {
	
	var row = 0;
	for (var index = 0; index < count; index++) {

		var path = '${ctx}' + data[index];
		var pathDel = data[index];
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
		trHtml += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
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

function addPhotoRow(id,tdTable, index) {
	
	for (var i = 0; i < 1; i++) {

		var path = '${ctx}' + "/images/blankDemo.png";
		var pathDel = '';
		var trHtml = '';

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
	var materialId = '${product.materialId}';
	callProductView(materialId);
}

function deletePhoto(tableId,tdTable,path) {
	
	var url = '${ctx}/business/storage?methodtype='+tableId+'Delete';
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
			}
			
			//删除后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error:"+errorThrown)
		}
	});
}

function uploadPhoto(tableId,tdTable, id) {

	var url = '${ctx}/business/godownEntryUpload'
			+ '?methodtype=uploadPhoto' + '&id=' + id;
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
			}
			
			//添加后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error:"+errorThrown)
		}
	});
}

</script>
</html>
