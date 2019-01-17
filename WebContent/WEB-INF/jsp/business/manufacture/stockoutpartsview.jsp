<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>料件出库-查看（配件单）</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function detailAjax() {
	
		var stockOutId = '${order.stockOutId }';
		
		var table = $('#example').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var t = $('#example').DataTable({
			
			"paging": false,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/stockout?methodtype=getStockoutDetailParts&stockOutId="+stockOutId,
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
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
		        	{"data": null,"className":"dt-body-center"
				}, {"data": "YSId","className":"td-left"
				}, {"data": "materialId","className":"td-left"
				}, {"data": "materialName"
				}, {"data": "totalQuantity","className":"td-right","defaultContent" : '0'
				}, {"data": "quantity","className":"td-right"
				}
				
			] ,
			"columnDefs":[
		    		
		    		{"targets":4,"render":function(data, type, row){
		    			return parseInt(data);
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			return parseInt(data);
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
				
		var width = $(window).width();
		$(".showPhotoDiv").width(width - 100);
		
		//historyAjax();//出库记录
				
		$(".goBack").click(
				function() {
					var makeType=$('#makeType').val();
					var usedType=$('#usedType').val();
					var url = "${ctx}/business/stockout?methodtype=partsStockoutSearchInit&makeType="
							+makeType+"&usedType="+usedType;
					location.href = url;		
		});
		
		detailAjax();

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
	
	function doEdit() {

		var requisitionType=$('#requisitionType').val();
		
		var YSId= '${order.YSId }';
		var requisitionId= '${order.requisitionId }';

		var usedType=$('#usedType').val();
		var url =  "${ctx}/business/stockout?methodtype=addinit&YSId="+YSId
			+"&requisitionId="+requisitionId+"&requisitionType="+requisitionType;
		
		location.href = url;
	}
	
	function doDelete() {

		var requisitionType=$('#requisitionType').val();

		var YSId= '${order.YSId }';
		var stockoutid= '${order.stockOutId }';
		
		if(confirm("删除后不能恢复，确定要删除吗？")){

			var url = '${ctx}/business/stockout?methodtype=stockoutDelete&YSId='
					+YSId+'&stockOutId='+stockoutid
					+'&requisitionType='+requisitionType;
			location.href = url;	
		}
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
	<input type="hidden" id="usedType" value="${usedType }" />
	<input type="hidden" id="requisitionType" value="${requisitionType }" />
	
	<form:hidden path="stockout.ysid"  />
	<form:hidden path="stockout.stockoutid"  />
	<form:hidden path="stockout.requisitionid"  value="${order.requisitionId }"/>

	<fieldset>
		<legend> 出库单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">出库单编号：</td>					
				<td width="150px">${order.stockOutId }</td>
				
				<td class="label">出库日期：</td>					
				<td>${order.checkOutDate }</td>
									
				<td class="label" width="100px">仓管员：</td>					
				<td colspan="5">${order.loginName }</td>
			</tr>
										
		</table>
		
	</fieldset>

	<div id="DTTT_container" align="right" style="margin-right: 30px;">
	 	<a class="DTTT_button " id="doEdit"  onclick="doEdit();">继续领料</a>
	 	<a class="DTTT_button " id="doPrint"  onclick="doPrint();">打印出库单</a>
	 	<a class="DTTT_button " id="doDelete"  onclick="doDelete();">删除</a>
		<a class="DTTT_button  goBack" id="goBack" > 返回 </a>
	</div>
	
	<fieldset style="margin-top: -20px;">
		<legend> 物料详情</legend>
		<div class="list">		
			<table id="example" class="display" style="width:100%">
				<thead>
					<tr>
						<th style="width:30px">No</th>
						<th class="dt-center" width="150px">耀升编号</th>
						<th class="dt-center" width="150px">物料编号</th>
						<th class="dt-center" >物料名称</th>	
						<th class="dt-center" width="60px">订单数量</th>
						<th class="dt-center" width="60px">当前出库</th>
					</tr>
				</thead>
			</table>
		</div>
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

function doPrint(stockOutId) {
	var YSId 		= '${order.YSId }';
	var stockOutId  = '${order.stockOutId }';
	
	var url = '${ctx}/business/stockout?methodtype=partsPrint';
	url = url +'&YSId='+YSId;
	url = url +'&stockOutId='+stockOutId;
		
	callWindowFullView("print",url);

};

function productPhotoView() {

	var YSId = '${order.YSId }';
	var requisitionId = '${order.requisitionId }';
	
	$.ajax({
		"url" :"${ctx}/business/stockout?methodtype=getProductPhoto&YSId="+YSId+"&requisitionId="+requisitionId,	
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
	
	var url = '${ctx}/business/stockout?methodtype='+tableId+'Delete';
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

	var url = '${ctx}/business/ODOUpload?methodtype=uploadPhoto';
	
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
