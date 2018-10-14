<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>入库单查看(付款用)</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function receiptView(index,contractId) {

		var t = $('#example'+index).DataTable({
			
			"paging": false,
			"lengthChange":false,
			//"lengthMenu":[50,100,200],//设置一页展示20条记录
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
				//var param = {};
				//var formData = $("#condition").serializeArray();
				//formData.forEach(function(e) {
				//	aoData.push({"name":e.name, "value":e.value});
				//});
				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					"data" : JSON.stringify(aoData),
					success: function(data){
						fnCallback(data);

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
				}, {"data": "total","className":"td-right"
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
	    		{
					"visible" : false,
					"targets" : [8,9]
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

	};

	
	$(document).ready(function() {

		//循环显示入库单
		var index = 0; 
		$(".receipt").each(function(){
			var supplierId = $('#supplierId'+index).val();
			var contractId = $('#contractId'+index).val();
			//alert("sup--con:"+supplierId+"----"+contractId)
			
			receiptView(index,contractId)

			productPhotoView(index,supplierId,contractId);
			
			index++;
		});
		
		
	});
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">
<div id="printBtn">
	<button type="button" id="print" onclick="doPrint();"
	class="DTTT_button " style="float: right;margin: 15px 50px -50px 0px;width: 70px;">打印</button>
</div>
<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
	
	<c:forEach var="detail" items="${contractList}" varStatus='status'>
		
		<input type="hidden" id="YSId${status.index}" value="${detail.YSId }"/>
		<input type="hidden" id="supplierId${status.index}" value="${detail.supplierId }"/>
		<input type="hidden" id="contractId${status.index}" value="${detail.contractId }"/>
	

			<table class="form" >
				<tr>
					<td class="label" width="80px">耀升编号：</td>					
					<td width="100px">${detail.YSId }</td>
					<td class="label" width="80px">合同编号：</td>					
					<td width="150px">${detail.contractId }</td>								 	
					<td class="label" width="80px">供应商：</td>					
					<td>${detail.supplierId }（${detail.shortName }）${detail.fullName }</td>	
				</tr>
											
			</table>
			<div class="list">
				<table class="display receipt" id="example${status.index}">	
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
						</tr>
					</thead>										
				</table>
			</div>
		
		
		<div class="" id="subidDiv" style="min-height: 300px;">
			<table id="productPhoto${status.index }" class="phototable">
				<tbody><tr class="photo"><td></td><td></td></tr></tbody>
			</table>
		</div>
		
		 <div style="page-break-before:always;"></div>
		
	</c:forEach>
</form:form>

</div>
</div>
</body>
<script type="text/javascript">

function productPhotoView(index,supplierId,contractId) {

	$.ajax({
		"url" :"${ctx}/business/storage?methodtype=getProductPhoto"+"&supplierId="+supplierId+"&contractId="+contractId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["productFileCount"];
			//alert(countData)
			photoView('productPhoto'+index,'uploadProductPhoto',countData,data['productFileList'])		
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

		trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td>';
		trHtml += '<table style="width:400px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr><td>';
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

			var trHtmlOdd = '<table style="width:400px;margin: auto;" class="form">';
			trHtmlOdd += '<tr><td>';
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

</script>

<script  type="text/javascript">

function doPrint(){
	
	var headstr = "<html><head><title></title></head><body>";  
	var footstr = "</body>";
	$("#printBtn").hide();
	window.print();
	$("#printBtn").show();
}
</script>
</html>
