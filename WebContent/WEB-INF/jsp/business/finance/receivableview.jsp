<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应收款-收款查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	

function ajaxProductStockout() {

	var receivableId = '${receivableId }';
	var actionUrl = "${ctx}/business/receivable?methodtype=getProductStockoutDetail";
	actionUrl = actionUrl + "&receivableId="+receivableId;
	
	var t = $('#stockout').DataTable({
		
		"paging": true,
		"lengthChange":false,
		"lengthMenu":[50,100,200],//设置一页展示20条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"ordering "	:true,
		"autoWidth"	:false,
		"searching" : false,
		"retrieve"  : true,
		"dom"		: '<"clear">rt',
		"sAjaxSource" : actionUrl,
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
					 alert(errorThrown)
	             }
			})
		},	
			"columns" : [
	        	{"data": null,"className":"dt-body-center"
			}, {"data": "YSId","className":"td-left"
			}, {"data": "orderQty","className":"td-right"
			}, {"data": "stockOutId","className":"td-left"
			}, {"data": "checkOutDate","className":"td-center"
			}, {"data": "quantity","className":"td-right"
			}, {"data": null,"className":"dt-body-center"
			}
		],
		"columnDefs":[
    		{"targets":6,"render":function(data, type, row){
				return "";
            }}
		]

	}).draw();
						
	t.on('order.dt search.dt draw.dt', function() {
		t.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			cell.innerHTML = i + 1;
		});
	}).draw();

};

function historyAjax() {
	
	var table = $('#history').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var receivableId = '${receivableId }';

	var t = $('#history').DataTable({			
		"paging": false,
		"lengthChange":false,
		"lengthMenu":[50,100,200],//设置一页展示20条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"ordering "	:true,
		"searching" : false,
		"retrieve" : true,
		//"scrollY":scrollHeight,
		//"scrollCollapse":true,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/receivable?methodtype=getReceivableDetail&receivableId="+receivableId,
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
					doComputeTax();
					
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
				}, {"data": "receivableSerialId"
				}, {"data": "collectionDate","className":"td-center"
				}, {"data": "LoginName","className":"td-center"
				}, {"data": "currency","className":"td-center", "defaultContent" : '',// 4
				}, {"data": "bankDeduction","className":"td-right"
				}, {"data": "actualAmount","className":"td-right"
				}, {"data": "remarks","className":""
				}, {"data": null,"className":"td-center" //8 操作
				}
				

			],
			"columnDefs":[
	    		{"targets":2,"render":function(data, type, row){
	    			return jQuery.fixedWidth(data,64);
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":8,"render":function(data, type, row){
	    			var text= "<a href=\"###\" onClick=\"doEdit('"+ row["YSId"] + "','"+ row["receivableSerialId"] + "')\">" + "编辑" + "</a>";
	    			var text= "<a href=\"###\" onClick=\"doDelete('"+ row["receivableId"] + "','"+ row["receivableSerialId"] + "')\">" + "删除" + "</a>";
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
	

function orderDetailAjax() {
	
	var receivableId = '${receivableId}';
	
	var table = $('#detail').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var t = $('#detail').DataTable({			
		"paging": false,
		"lengthChange":false,
		"lengthMenu":[50,100,200],//设置一页展示20条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"ordering "	:true,
		"searching" : false,
		"retrieve" : true,
		//"scrollY":scrollHeight,
		//"scrollCollapse":true,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/receivable?methodtype=getOrderDetail&receivableId="+receivableId,
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
					var supplier = data['data']['0']['customerFullName'];		
					var currency = data['data']['0']['currencyName'];
					//alert('supplier'+supplier)
					$('#supplier').text(supplier);
					
					var cnt = orderSum();
					var cnt2 = floatToSymbol(cnt,currency)
					$('#orderPrice').text(cnt2);
					$('#orderCnt').text(floatToCurrency(cnt));
					$('#receivable\\.amountreceivable').val(cnt);
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
		
		"columns" : [
		           {"data": null,"className":"td-center"
					}, {"data": "YSId","className":"td-left"
					}, {"data": "productId","className":"td-left"
					}, {"data": "productName","className":""
					}, {"data": "totalPrice","className":"td-right"
					}, {"data":null,"className":""
					}
			],
			"columnDefs":[
				{"targets":0,"render":function(data, type, row){
					return row["rownum"];
				}},
	    		{"targets":5,"render":function(data, type, row){
	    			return "";
	    		}}
	    	] 
		
	}).draw();

};	
	$(document).ready(function() {
		
		historyAjax();
		orderDetailAjax();
		ajaxProductStockout();
		
		//var currency = '${order.currency}';//币种
		//var orderPrice = currencyToFloat('${order.orderPrice }');
		//$('#orderPrice').text(floatToSymbol(orderPrice,currency))
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/receivable";						
					location.href = url;		
		});

		
		$("#insert").click(
				function() {

			var detailid = $("#receivable\\.receivableid").val();
			$('#formModel').attr("action", "${ctx}/business/receivable?methodtype=addContinueInit&detailId="+detailid);
			$('#formModel').submit();
		});
		
		productPhotoView();//收款单
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
	
	//列合计
	function orderSum(){

		var sum = 0;
		$('#detail tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(4).text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})
		return sum;
	}
	
	function doComputeTax(){
		
		var bank = contractSum(5);//银行扣款
		var shiji = contractSum(6);//实际收款
		var heji = bank + shiji;
		
		$('#bank').html(floatToCurrency(bank));
		$('#tbank').html(floatToCurrency(bank));
		$('#shiji').html(floatToCurrency(shiji));
		$('#tshiji').html(floatToCurrency(shiji));
		$('#heji').html(floatToCurrency(heji));
	}
	
	function doEdit(YSId,receivableSerialId) {
		
		var url = '${ctx}/business/receivable?methodtype=editInit&receivableSerialId='+receivableSerialId+"&YSId="+YSId;
		location.href = url;
	}
	
	function doDelete(receivableId,recordId){
		
		if (confirm("删除后不能恢复，确定要删除吗？")){ //
			$.ajax({
				type : "post",
				url : "${ctx}/business/receivable?methodtype=receivableDelete&receivableSerialId="+recordId+"&receivableId="+receivableId,
				async : false,
				data : 'key=',
				dataType : "json",
				success : function(data) {
					$().toastmessage('showWarningToast', "收款记录删除成功。");	
					
					historyAjax();//
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					
					
				}
			});
		}else{
			//
		}
	}

	//列合计
	function contractSum(col){

		var sum = 0;
		$('#history tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(col).text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})
		return sum;
	}
		
</script>
<script type="text/javascript">

function productPhotoView() {

	var detailid = $("#receivable\\.receivableid").val();

	$.ajax({
		"url" :"${ctx}/business/receivable?methodtype=getProductPhoto&detailId="+detailid,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["productFileCount"];

			photoView('productPhoto','uploadProductPhoto',countData,data['productFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
         	alert("图片显示失败.")
		 }
	});
	
}//产品图片

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
	
	var url = '${ctx}/business/receivable?methodtype='+tableId+'Delete';
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

	var url = '${ctx}/business/receivabelUpload'
			+ '?methodtype=uploadPhoto' + '&id=' + id;
	
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
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="currency" value="${order.currency }">
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">收款编号：</td>					
				<td width="150px">${receivableId }
					<form:hidden path="receivable.receivableid" class="read-only"  value="${receivableId }"/></td>	
																										
				<td width="100px" class="label">客户名称：</td>
				<td colspan="5"><span id="supplier"></span></td>			
			</tr>
			<tr>
				<td class="label" width="100px">应收款总额：</td>					
				<td width="150px"><span id="orderPrice"></span></td>
													
				<td width="100px" class="label">已收款合计：</td>
				<td width="150px"><span id="heji"></span></td>
							
				<td width="100px" class="label">实收金额合计：</td>
				<td width="150px"><span id="shiji"></span></td>
				
				<td width="100px" class="label">银行扣款合计：</td>
				<td><span id="bank"></span></td>
													
			</tr>										
		</table>
	</fieldset>
	<div style="clear: both"></div>	
	<div id="DTTT_container" align="right" style="margin-right: 30px;">
		<a class="DTTT_button " id="insert" >继续收款</a> 
		<a class="DTTT_button  goBack" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 收款记录</legend>
		<table class="display" id="history">
			<thead>
				<tr> 
					<th width="30px">No</th>				
					<th width="100px">收款单编号</th>				
					<th width="100px">收款日期</th>
					<th width="100px">收款人</th>					
					<th width="100px">币种</th>					
					<th width="100px">银行扣款</th>
					<th width="100px">收款金额</th>	
					<th>备注</th>
					<th width="40px">操作</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td id="tbank"></td>
					<td id="tshiji"></td>
					<td></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
	</fieldset>		

	<fieldset>
		<legend>汇票所含订单</legend>
		<table class="display" id="detail">
			<thead>
				<tr> 		
					<th width="50px">No</th>				
					<th width="100px">耀升编号</th>
					<th width="150px">产品编号</th>				
					<th width="">产品名称</th>
					<th width="100px">收款金额</th>	
					<th width="100px"></th>
				</tr>
			</thead>
			<tfoot>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><span id="orderCnt"></span></td>
				<td></td>
			</tr>
			</tfoot>			
		</table>
	</fieldset>	
	
	<fieldset>
		<legend> 出库记录</legend>
		<div class="list">
			<table class="display" id="stockout" >	
				<thead>		
					<tr>
						<th style="width:15px">No</th>
						<th style="width:120px">耀升编号</th>
						<th style="width:80px">订单数量</th>
						<th style="width:120px">出库单</th>
						<th style="width:80px">出库时间</th>
						<th style="width:80px">出库数量</th>
						<th></th>	
					</tr>
				</thead>		
										
			</table>
		</div>
	</fieldset>
	<fieldset>
		<span class="tablename">收款票据</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
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

</html>
