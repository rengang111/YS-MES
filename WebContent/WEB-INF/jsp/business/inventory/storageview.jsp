<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function ajax() {

		var contractId = $("#stock\\.contractid").val();
		var t = $('#example').DataTable({
			
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"autoWidth"	:false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/storage?methodtype=getStockInDetail&contractId="+contractId,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				var param = {};
				var formData = $("#condition").serializeArray();
				formData.forEach(function(e) {
					aoData.push({"name":e.name, "value":e.value});
				});

				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					"data" : JSON.stringify(aoData),
					success: function(data){
						fnCallback(data);

						$('#checkInDate').text(data['data'][0]["checkInDate"]);
						$('#materialNumber').text(data['data'][0]["materialNumber"]);
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
						 alert(errorThrown)
		             }
				})
			},	
			"columns" : [
		        	{"data": null,"className":"dt-body-center"
				}, {"data": "receiptId","className":"td-left"
				}, {"data": "materialId","className":"td-left"
				}, {"data": "materialName",
				}, {"data": "contractQuantity","className":"td-right"
				}, {"data": "contractTotalPrice","className":"td-right"
				}, {"data": "quantity","className":"td-right"	
				}, {"data": "checkInDate","className":"td-right"
				}, {"data": "packaging","className":"td-center"
				}, {"data": "areaNumber",
				}
			],
			"columnDefs":[
	    		{"targets":3,"render":function(data, type, row){
	    			
	    			var name = data;				    			
	    			name = jQuery.fixedWidth(name,40);				    			
	    			return name;
	    		}},
	    		{"targets":10,"render":function(data, type, row){
	    			var text="";
	    			text += "<a href=\"###\" onClick=\"doEdit('"  + row["contractId"] + "','"  + row["receiptId"] + "')\">编辑</a>";
	    			text += "&nbsp;"
	    			text += "<a href=\"###\" onClick=\"doPrint('" + row["contractId"] + "','" + row["receiptId"] + "')\">打印</a>";

	    			return text;
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

		ajax();
		
		$("#goBack").click(
				function() {
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/storage?keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#doAddInit").click(
					function() {
						var addFlag = $('#addFlag').val();
						if(addFlag == "false"){
							$().toastmessage('showWarningToast', "该合同暂时没有要入库的数据。");	
							return;
						}
						var arrivalId = $('#arrivalId').val();
						var contractId='${contract.contractId }';
						var url = "${ctx}/business/storage?methodtype=addinit";
						url += "&contractId="+contractId;
						url += "&arrivalId="+arrivalId;
						location.href = url;		
					});
		
		productPhotoView();
		
	});
	
	function doEdit(contractId,receiptId) {

		var url = "${ctx}/business/storage?methodtype=edit&receiptId="+receiptId
				+ "&contractId="+contractId;
		location.href = url;
	}

	function doPrint(contractId,receiptId) {
		var url = '${ctx}/business/storage?methodtype=printReceipt';
		url = url +'&contractId='+contractId;
		url = url +'&receiptId='+receiptId;
			
		layer.open({
			offset :[10,''],
			type : 2,
			title : false,
			area : [ '1100px', '520px' ], 
			scrollbar : false,
			title : false,
			content : url,
			cancel: function(index){			
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
	
	<input type="hidden" id="addFlag"  value="${addFlag}"/>
	<input type="hidden" id="arrivalId"  value="${arrivalId}"/>
	<form:hidden path="stock.receiptid"  value="${receiptId}"/>
	<form:hidden path="stock.ysid"  value="${contract.YSId }"/>
	<form:hidden path="stock.supplierid"  value="${contract.supplierId }"/>
	<form:hidden path="stock.contractid"  value="${contract.contractId }"/>
	
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="150px">${contract.YSId }</td>
							
				<td width="100px" class="label">成品编码：</td>
				<td width="150px">${contract.productId }</td>							
				<td width="100px" class="label">成品名称：</td>
				<td>${contract.productName }</td>
			</tr>
			<tr>							
				<td class="label">合同编号：</td>					
				<td>${contract.contractId }</td>								 	
				<td class="label">供应商：</td>					
				<td colspan="3">${contract.supplierId }（${contract.shortName }）${contract.fullName }</td>	
			</tr>
										
		</table>
	</fieldset>
	<div style="clear: both"></div>
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doAddInit" class="DTTT_button">继续入库</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>	
<fieldset>
	<legend> 入库记录</legend>
	<div class="list">
	<table class="display" id="example">	
		<thead>		
			<tr>
					<th style="width:1px">No</th>
					<th style="width:65px">入库单编号</th>
					<th style="width:100px">物料编号</th>
					<th>物料名称</th>
					<th style="width:65px">合同数量</th>
					<th style="width:65px">合同金额</th>
					<th style="width:65px">入库数量</th>
					<th style="width:60px">入库时间</th>
					<th style="width:35px">包装</th>
					<th style="width:40px">库位</th>	
					<th style="width:35px">操作</th>	
			</tr>
		</thead>		
									
	</table>
	</div>
</fieldset>

<fieldset>
	<legend>收据清单</legend>
	<div class="list">
		<div class="" id="subidDiv" style="min-height: 300px;">
			<table id="productPhoto" class="phototable">
				<tbody><tr class="photo"><td></td><td></td></tr></tbody>
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

	//alert("id:"+id+"--count:"+count+"--countView:"+countView)	
	var row = 0;
	for (var index = 0; index < count; index++) {

		var path = '${ctx}' + data[index];
		var pathDel = data[index];
		//alert(index+"::::::::::::"+path)
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td>';
		trHtml += '<table style="width:400px;height:300px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr><td>';
		trHtml += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
		//trHtml += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage2(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
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
			trHtmlOdd += '<tr><td>';
			trHtmlOdd += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
			//trHtmlOdd += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage2(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
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


function doShowProduct() {
	var materialId = '${product.materialId}';
	callProductView(materialId);
}


</script>

</html>
