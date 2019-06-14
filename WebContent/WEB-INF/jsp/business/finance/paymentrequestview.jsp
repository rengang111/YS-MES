<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应付款-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

function ContractListAjax() {
	
	var table = $('#example').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var paymentId = $('#payment\\.paymentid').val();

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
		"sAjaxSource" : "${ctx}/business/payment?methodtype=paymentContractList&paymentId="+paymentId,
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

					doComputeTax();//合同集计
					var supplierId   = data['data'][0]['supplierId'];
					var supplierName = data['data'][0]['supplierName'];
				//	var shortName    = data['data'][0]['shortName'];

					$('#supplierId').text(supplierId);
					$('#payment\\.supplierid').val(supplierId);
					$('#supplierName').text(supplierName);
				//	$('#shortName').text(shortName);

					productPhotoView();//图片需要供应商编号
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},		
		"columns" : [
		        	{"data": null,"className":"dt-body-center"//
				}, {"data": "contractId","className":"td-center"//合同编号
				}, {"data": "receiptId","className":"td-right"//入库单号
				}, {"data": "YSId","className":"td-center"//耀升编号
				}, {"data": null, "className":"td-center",// 4//入库状态
				}, {"data": "totalPrice","className":"td-right"//合同金额
				}, {"data": "chargeback","className":"td-right","defaultContent" : '0'//增减项
				}, {"data": "chargeType","className":"td-center"//扣款方式
				}, {"data": "payable","className":"td-center"//应付款 8
				}, {"data": "taxRate","className":"td-center"//退税率
				}, {"data": "taxes","className":"td-center"//税
				}, {"data": "taxExcluded","className":"td-center"//价
				}, {"data": null,"className":"td-center" // 12  
				}
				
			],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
    			return row['rownum'];
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var txt = "<a href=\"###\" onClick=\"doShowContract('"+row['contractId']+"')\">"+row['contractId']+"</a>";
    			var hidden = '<input type="hidden" id=contract" name="contract"  value="'+row['contractId']+'"   class="contractid" />';
    			return txt +hidden;
    		}},
    		{"targets":2,"render":function(data, type, row){
    			var receipt = jQuery.fixedWidth(data,12)
    			var txt = "<a href=\"###\" onClick=\"receiptView('"+row['contractId']+"')\">"+receipt+"</a>";
				return txt;
    		}},
    		{"targets":4,"render":function(data, type, row){
    			var contractQty  = currencyToFloat(row['contractQty']);
    			var stockinQty  = currencyToFloat(row['stockinQty']);
    			var rtn = '待入库';
    			if(stockinQty > 0){ 
    				if(stockinQty >= contractQty){
        				rtn = '已入库';
    				}else{
        				rtn = '部分入库';  
    				}
    			}
    			return rtn;
    		}},
    		{"targets":5,"render":function(data, type, row){
    			return floatToCurrency(data);
    		}},
    		{"targets":8,"render":function(data, type, row){
    			    			
    			return floatToCurrency(data);
    		}},
    		{"targets":12,"render":function(data, type, row){
    			var delet = "<a href=\"###\" onClick=\"doPrintContract(\"'"+row["contractId"]+"')\">打印合同</a>";
    			
    			return delet;
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

function doUpdateInvoice(recordId) {

	layerWidth  = '900px';
	layerHeight = '360px';
	var url = "${ctx}/business/payment?methodtype=editPyamentInvoice&recordId=" + recordId;		

	layer.open({
		offset :[50,''],
		type : 2,
		title : false,
		area : [ layerWidth, layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
		cancel: function(index){ 
			  layer.close(index)
			  invoiceAjax();
			  return false; 
		}    
	});
}

function doDeleteInvoice(recordId){
	
	if (confirm("删除后不能恢复，确定要删除吗？")){ //
		$.ajax({
			type : "post",
			url : "${ctx}/business/payment?methodtype=deletePyamentInvoice&recordId="+recordId,
			async : false,
			data : 'key=' + recordId,
			dataType : "json",
			success : function(data) {
				  invoiceAjax();
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

//新增供应商
function doCreatePrice() {
	var paymentId = $("#payment\\.paymentid").val();
	var invoiceCnt = $("#invoiceCnt").text();
	var contractPrice = '${payment.totalPayable }';
	var url = "${ctx}/business/payment?methodtype=addPyamentInvoice&paymentId="+paymentId;
	url += "&invoiceCnt=" + invoiceCnt;
	url += "&contractPrice=" + contractPrice;
	//alert('url：'+url)
	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '900px', '360px' ], 
		scrollbar : false,
		title : false,
		content : url,
		cancel: function(index){ 
		  layer.close(index)
		  invoiceAjax();
		  return false; 
		}    
	});
}
	
	
	function materialzzAjax() {

		var taskId = $('#task\\.taskid').val();
		var YSId= $('#task\\.collectysid').val();
		var actionUrl = "${ctx}/business/requisitionzz?methodtype=getMaterialZZList";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&taskId="+taskId;
				
		var t = $('#payment').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	       "autoWidth":false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"aaSorting": [[ 1, "DESC" ]],
			"sAjaxSource" : actionUrl,
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
					//"data" : JSON.stringify(aoData),
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
		        	{"data": null,"className":"dt-body-center"//0
				}, {"data": "YSId","className":"td-left", "defaultContent" : ''//5
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2
				}, {"data": "unit","className":"td-center"		//3单位
				}, {"data": "manufactureQuantity","className":"td-right"//4
				}, {"data": null, "defaultContent" : ''	//6 
				}
			],
			"columnDefs":[
	    		
	    		{"targets":3,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					//var inputTxt =       '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value=""/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			
	    			return name;
                }},
				{"targets":5,"render":function(data, type, row){	    			
	    			
	    			var qty = floatToCurrency(row["manufactureQuantity"]);			
	    			return qty;				 
                }}
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
		
		invoiceAjax();//发票信息
		
		ContractListAjax();//合同明细
		
		paymentHistoryAjax();//付款明细
		
		$(".goBack").click(
				function() {
					var paymentTypeId=$("#paymentTypeId").val();
					var url = "${ctx}/business/payment";
					if( paymentTypeId == '020')
						url = "${ctx}/business/payment"+"?methodtype=beforehandMainInit";
						
					location.href = url;	
		});

		
		$("#update").click(
				function() {

					var paymentTypeId=$("#paymentTypeId").val();
			$('#formModel').attr("action", "${ctx}/business/payment?methodtype=applyUpdateInit"+"&paymentTypeId="+paymentTypeId);
			$('#formModel').submit();
		});
		

		$("#doDelete").click(function() {
					
			var status = '${payment.finishStatusId}';//付款状态
			status = parseInt(status)

			if(status > 20 ){
				alert("该申请已审核，如要删除，请先弃审。");
				return;
			}
					
			if(!(confirm("删除后，需要重新申请，确定要删除吗？"))){
				return;
			}
					
			$('#formModel').attr("action", "${ctx}/business/payment?methodtype=applyDelete"+"&paymentTypeId="+paymentTypeId);
			$('#formModel').submit();
		});
		
		materialzzAjax();
				
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
	
	function doComputeTax(){
		
		var contract = contractSum(5);//合同总金额
		var minis = contractSum(6);//增加项
		var payment = contractSum(8);//应付款
		var taxes = contractSum(10);//税
		var taxExcluded = contractSum(11);//价
		$('#contractTotal').html(floatToCurrency(contract));
		$('#minisTotal').html(floatToCurrency(minis));
		$('#paymentTotal').html(floatToCurrency(payment));
		$('#taxesTotal').html(floatToCurrency(taxes));
		$('#taxExcludedTotal').html(floatToCurrency(taxExcluded));
		$('#payment\\.totalpayable').val(floatToCurrency(payment));
	}
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/requisition?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}

	//列合计
	function contractSum(col){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(col).text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})
		return sum;
	}
	
	function doShowContract(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callWindowFullView("采购合同",url);
	}

	function doShowSupplier(supplierId) {

		var url = '${ctx}/business/supplier?methodtype=showById&openFlag=newWindow&key=' + supplierId;
		
		callWindowFullView("供应商",url);
	}
	
	
	function doPrintContract(contractId) {
	
		var url = '${ctx}/business/requirement?methodtype=contractPrint';
		url = url +'&contractId='+contractId;
		//alert(url)		
		callProductDesignView("打印合同",url);	

	}
	//显示入库单信息
	function receiptView(contractId) {

		var url = '${ctx}/business/storage?methodtype=showHistory&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("显示入库单",url);
	}
	
	//批量打印入库单
	function doPrintReceiptList() {
		var contractId = "";
		$(".contractid").each(function(){			
			contractId += $(this).val() + ",";			
		});
		var url = '${ctx}/business/storage?methodtype=receiptListPrint&contractIds=' + contractId;
		
		callProductDesignView("打印入库单",url);
	}
</script>
<script type="text/javascript">

function productPhotoView() {

	var paymentId = $("#payment\\.paymentid").val();
	var supplierId =$("#supplierId").text();;

	$.ajax({
		"url" :"${ctx}/business/payment?methodtype=getProductPhoto&paymentId="+paymentId+"&supplierId="+supplierId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["productFileCount"];
			//alert('countData:'+ countData);
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
		var trHtml = showPhotoRow(id,tdTable,path,pathDel,index);		
		$('#' + id + ' td.photo:eq(' + row + ')').after(trHtml);
		row++;
	}
}

</script>

<script type="text/javascript">


function deletePhoto(tableId,tdTable,path) {
	
	var url = '${ctx}/business/payment?methodtype='+tableId+'Delete';
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

	var url = '${ctx}/business/paymentBillUpload'
			+ '?methodtype=uploadPhoto' + '&id=' + id;
	
	var paymentId = $('#payment\\.paymentid').val();
	if(paymentId == '（保存后自动生成）')
		$('#payment\\.paymentid').val('');//清除非正常ID
		//alert('url:'+url)	
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
					//$('#payment\\.paymentid').val(data["paymentId"]);//设置新的ID
					//$('#payment\\.recordid').val(data["recordId"]);//设置新的ID
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


function invoiceAjax() {
	
	var table = $('#invoice').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var paymentId = $('#payment\\.paymentid').val();

	var t = $('#invoice').DataTable({			
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
		"sAjaxSource" : "${ctx}/business/payment?methodtype=paymentInvoiceList&paymentId="+paymentId,
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
					invoiceCountFn();
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
				}, {"data": "invoiceDate","className":"td-center"
				}, {"data": "invoiceAmount","className":"td-right"
				}, {"data": "invoiceType","className":"td-center"
				}, {"data": "invoiceNumber", "defaultContent" : '',// 4
				}, {"data": "remarks","className":""
				}, {"data": null,"className":"td-center",// 6
				}, {"data": null, "defaultContent" : '',
				}
			],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
    			return row['rownum'];
    		}},
    		{"targets":2,"render":function(data, type, row){
    			return floatToCurrency(data);
    		}},
    		{"targets":5,"render":function(data, type, row){
    			return data;
    		}},
    		{"targets":6,"render":function(data, type, row){
    			var edit    = "<a href=\"#\" onClick=\"doUpdateInvoice('" + row["recordId"] + "')\">编辑</a>";
    			var delet   = "<a href=\"#\" onClick=\"doDeleteInvoice('" + row["recordId"] + "')\">删除</a>";
    			
    			return edit+"&nbsp;"+"&nbsp;"+delet;
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

function invoiceCountFn(){
	
	var cost = 0;
	$('#invoice tbody tr').each (function (){
		
		var temp = $(this).find("td").eq(2).text();//发票金额	
		
		temp = currencyToFloat(temp);
		cost = cost + temp;
					
	});	
	
	$('#invoiceCnt').text(floatToCurrency(cost));
	
}

function paymentHistoryAjax() {
	
	var table = $('#history').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var paymentId = $('#payment\\.paymentid').val();

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
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/payment?methodtype=paymentHistoryList&paymentId="+paymentId,
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
					
					paymentSum();//已付款合计
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
				}, {"data": "paymentHistoryId","className":"td-left"
				}, {"data": "finishDate","className":"td-center"
				}, {"data": "finishUser","className":"td-center"
				}, {"data": "currency", "className":"td-center",// 4
				}, {"data": "paymentMethod","className":"td-center"
				}, {"data": "paymentAmount","className":"td-right"
				}, {"data": null,
				}, {"data": null,
				}
			
			],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
    			return row['rownum'];
    		}},
    		{"targets":7,"render":function(data, type, row){
    			
    			//var delet   = "<a href=\"#\" onClick=\"deletePayment('" + row["paymentId"] + "','" + row["recordId"] + "')\">删除</a>";
    			
    			return "";
            }},
            {"targets":8,"render":function(data, type, row){
    			return "";
    		}},
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

//列合计
function paymentSum(){

	var sum = 0;
	$('#history tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(6).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})

	$('#paymentCount').html(floatToCurrency(sum));	
	$('#paymentCount2').html(floatToCurrency(sum));		
}
</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<form:hidden path="payment.parentid"  />
	<form:hidden path="payment.subid"  />
	<form:hidden path="payment.finishstatus" value="${payment.finishStatusId }"/>
	<form:hidden path="payment.recordid"     value="${payment.recordId }"/>
	<form:hidden path="payment.contractids"  value="${payment.contractIds }"/>
	<form:hidden path="payment.paymentid"    value="${payment.paymentId }"/>
	<form:hidden path="payment.supplierid"    value=""/>
	<input type="hidden" id="paymentTypeId"  value="${paymentTypeId }"/><!-- 1:正常；2：预付 -->
	
	<fieldset>
		<legend> 付款申请单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">申请单编号：</td>					
				<td width="150px">${payment.paymentId }</td>	
								
				<td class="label" width="100px">申请人：</td>					
				<td width="150px">${payment.applicantName }</td>
														
				<td width="100px" class="label">申请日期：</td>
				<td colspan="3">${payment.requestDate }</td>				
			</tr>
			<tr> 				
				<td class="label" width="100px">供应商编号：</td>					
				<td width="150px"><span id="supplierId"></span></td>
														
				<td width="100px" class="label">供应商名称：</td>
				<td colspan="5"><span id="supplierName"></span></td>
			</tr>
			<tr>			
				<td class="label" width="100px">申请付款总额：</td>					
				<td class="font16" width="150px">${payment.totalPayable }</td>
								
				<td class="label" width="100px">付款条件：</td>					
				<td width="150px">发票后&nbsp;${supplier.paymentTerm }&nbsp;天</td>
														
				<td width="100px" class="label">付款类别：</td>
				<td width="150px">${payment.paymentType }</td>
				<td width="100px" class="label">付款状态：</td>
				<td class="bold">${payment.finishStatus }</td>
			</tr>										
		</table>
	</fieldset>
	<div style="clear: both"></div>	
	<div id="DTTT_container" align="right" style="margin-right: 30px;">
	 	<a class="DTTT_button DTTT_button_text" id="update" >编辑付款申请</a>
	 	<a class="DTTT_button DTTT_button_text" id="doDelete" >删除申请</a>&nbsp;&nbsp;
		<a class="DTTT_button DTTT_button_text" id="printbtn" onclick="doPrintReceiptList();return false;">打印入库单</a>
		
		<!-- a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a-->
	</div>
		
	
	<fieldset>
		<span class="tablename" style="">  发票信息</span>	
		<a class="DTTT_button" onclick="doCreatePrice();" style="">新增发票</a>	
		
		<table class="display" id="invoice">
			<thead>
				<tr> 
					<th width="30px">No</th>				
					<th width="100px">发票日期</th>				
					<th width="150px">发票金额</th>
					<th width="100px">发票类型</th>					
					<th width="180px">发票编号</th>					
					<th width="">备注信息</th>		
					<th width="80px">操作</th>
					<th width="20px"></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td><span id="invoiceCnt"></span></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
	</fieldset>
	
	<fieldset>
		<legend> 付款明细</legend>
		<table class="display" id="history">
			<thead>
				<tr> 
					<th width="30px">No</th>				
					<th width="100px">付款单编号</th>				
					<th width="100px">付款日期</th>
					<th width="100px">付款人</th>					
					<th width="100px">币种</th>					
					<th width="100px" >付款方式</th>
					<th width="100px">付款金额</th>	
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span id="paymentCount2" style="font-weight: bold;"></span></td>
					<td></td>
					<td></td>
				</tr>
			</tfoot>										
		</table>
	</fieldset>
	
	<fieldset>
		<span class="tablename">付款票据</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
		<div class="list">
			<div class="showPhotoDiv" style="overflow: auto;width: 1024px;">
				<table id="productPhoto" style="width:100%;height:335px">
					<tbody><tr><td class="photo"></td></tr></tbody>
				</table>
			</div>
		</div>	
	</fieldset>
	<fieldset>
		<legend> 合同明细</legend>
		<div class="list">
		<table id="example" class="display" >
			<thead>				
				<tr>
					<th width="10px">No</th>
					<th width="100px">合同编号</th>
					<th width="80px">入库单号</th>
					<th width="80px">耀升编号</th>
					<th width="50px">入库状态</th>
					<th width="60px">合同金额</th>
					<th width="50px">增减项</th>
					<th width="50px">扣款方式</th>
					<th width="60px">应付款</th>
					<th width="50px">退税率</th>
					<th width="50px">税</th>
					<th width="60px">价</th>
					<th>操作</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>合计：</td>
					<td><span id="contractTotal" style="font-weight: bold;"></span></td>
					<td><span id="minisTotal" style="font-weight: bold;"></span></td>
					<td></td>
					<td><span id="paymentTotal" style="font-weight: bold;"></span></td>
					<td></td>
					<td><span id="taxesTotal" style="font-weight: bold;"></span></td>
					<td><span id="taxExcludedTotal" style="font-weight: bold;"></span></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
		</div>
	</fieldset>
	
</form:form>

</div>
</div>
</body>

</html>
