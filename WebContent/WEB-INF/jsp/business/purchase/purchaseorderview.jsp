<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>采购合同-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

var orderExpanseQty='0';

function deductAjax() {
	
	var table = $('#example2').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}

	var contractId = $('#contract\\.contractid').val();
	var actionUrl = "${ctx}/business/contract?methodtype=getContractDeduct";
	actionUrl = actionUrl + "&contractId=" + contractId;
	
	
	var t = $('#example2').DataTable({
			"paging": false,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:false,
			"autoWidth"	:false,
			"searching" : false,
			"retrieve"  : true,
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
						orderExpanseQty = currencyToFloat( data["orderExpanseQty"] );
						sumFn(orderExpanseQty);
						
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns": [
						{"data": null, "defaultContent" : '',"className" : 'td-center'},
						{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},
						{"data": "requisitionId", "defaultContent" : '', "className" : 'td-left'},
						{"data": "remarks", "defaultContent" : ''},//3
						{"data": "deductQty", "defaultContent" : '', "className" : 'td-right'},
						{"data": "price", "defaultContent" : '0', "className" : 'td-right'},
						{"data": null, "defaultContent" : '0', "className" : 'td-right'},
						
						],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
	    			return row["rownum"] ;				    			 
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			
	    			var rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["requisitionId"] + "')\">"+data+"</a>";
    			
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = jQuery.fixedWidth(data,46);//true:两边截取,左边从汉字开始
	    			return name;
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			var deduct = currencyToFloat( row["deductQty"] );
	    			var price = currencyToFloat( row["price"] );
	    			var rtn="";

	    			rtn = floatToCurrency(deduct * price);
	    			return rtn;
	    		}},
	    		{
					"visible" : false,
					"targets" : []
				}
         	]
		}
	);
	

	t.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});

}
	function ajaxRawGroup() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,

			dom : '<"clear">rt',
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {"className":"td-center"					
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				],
			"columnDefs":[
    			{
					"visible" : false,
					"targets" : []
				}
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
		
		t.on('contract.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	};//ajaxRawGroup()
	
	$(document).ready(function() {

		$("#costDeduct").attr('readonly',true);
		$("#costDeduct").addClass('read-only');
		$("#doSaveDeduct").hide();
		$("#doCancelDeduct").hide();
		
		$("#doEditDeduct").click(function() {
			$("#costDeduct").attr('readonly',false);
			$("#costDeduct").removeClass('read-only');
			$("#doSaveDeduct").show();
			$("#doCancelDeduct").show();
			$("#doEditDeduct").hide();
		});
		

		$("#doCancelDeduct").click(function() {
			$("#costDeduct").attr('readonly',true);
			$("#costDeduct").addClass('read-only');
			$("#doSaveDeduct").hide();
			$("#doCancelDeduct").hide();
			$("#doEditDeduct").show();
			$("#costDeduct").val($("#oldDeduct").val());
		});
		
		$("#doSaveDeduct").click(function() {
			var deduct  = $('#costDeduct').val();
			var contractId  = '${ contract.contractId }';
			var url = "${ctx}/business/contract?methodtype=updateContractDeduct";
			url = url + "&deduct="+deduct+"&contractId="+contractId;

			$.ajax({
				type : "post",
				url : url,
				//async : false,
				//data : null,
				dataType : "text",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {			

					$().toastmessage('showNoticeToast', "保存成功。");
					$("#costDeduct").val(floatToCurrency(deduct));
					$("#costDeduct").attr('readonly',true);
					$("#costDeduct").addClass('read-only');
					$("#doSaveDeduct").hide();
					$("#doEditDeduct").show();
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					//alert(textStatus)
				}
			});		

		});
		
		var YSId = '${ contract.YSId }';
		//var productid = '${ contract.productId }';
		if(YSId == null || YSId == ""){
			$('#ysid00').attr("style","display:none");
						
		}else{
			//if(productid == null || productid == ""){
				$('#ysidLink').contents().unwrap();			
			//}
		}
		
		
		//返回按钮
		var goBactkBt = '${openFlag}';
		if(goBactkBt == "newWindow"){
			$('#goBack').attr("style","display:none");			
		}
		
		ajaxRawGroup();			
				
		stockinList();//收货记录	
		
		contractPayment();//付款记录
		
		$("#goBack").click(
				function() {	
					var url = '${ctx}/business/contract?methodtype=goBackContractMainInit';
					location.href = url;		
				});
		
		$("#doEdit").click(
				function() {
					
			$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=edit");
			$('#attrForm').submit();
		});	
		
		$("#doDelete").click(function() {
			
			var contrDelFlag = 'NG';	
			var contractId  = '${ contract.contractId }';	
			
			var url = "${ctx}/business/contract?methodtype=checkContractDelete";
			url = url + "&contractId="+contractId;

			$.ajax({
				type : "post",
				url : url,
				async : false,
				data : 'key=',
				dataType : "json",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {			

					var flag = data["deleteFlag"];
					
					if(flag == "OK"){
						contrDelFlag = "OK";//未收货，可以删除
					}else{
						var checkResult  = data['data'][0]['checkResult'];	
						
						if(checkResult == '040'){
							contrDelFlag = "OK";//退货后，可以删除
						}	
					}
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("系统错误，请联系管理员。")
				}
			});	
			
			if(contrDelFlag == "NG"){
				$().toastmessage('showWarningToast', "该合同已有收货,不能删除。");
				return false;
			}
			
			if(confirm("采购合同删除后不能恢复,\n\n        确定要删除吗？")) {
				$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=delete");
				$('#attrForm').submit();
			}else{
				return false;
			}
			
		});
		
		
		contractSum();//合同计算
		
		//expenseAjax3();//订单过程扣款明细
		
		stockinAjax();//入库退货
		

		deductAjax();
		
	});	
	
	function doShowYS(YSId) {

		var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;
		
		location.href = url;
	}
	

	function doEditMaterial(recordid,parentid) {
		//var height = setScrollTop();
		//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
		var url = '${ctx}/business/material?methodtype=detailView';
		url = url + '&parentId=' + parentid+'&recordId='+recordid+'&keyBackup=1';
		
		layer.open({
			offset :[10,''],
			type : 2,
			title : false,
			area : [ '1100px', '520px' ], 
			scrollbar : false,
			title : false,
			content : url,
			//只有当点击confirm框的确定时，该层才会关闭
			cancel: function(index){ 
			 // if(confirm('确定要关闭么')){
			    layer.close(index)
			 // }
			  //$('#baseBomTable').DataTable().ajax.reload(null,false);
			  return false; 
			}    
		});		

	};
	
	function contractSum(){
		
		var contract = 0;
		$('#example tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(5).text();////合同
			
			contractValue= currencyToFloat(contractValue);
			//alert("计划用量+已领量+库存:"+fjihua+"---"+fyiling+"---"+fkucun)
			
			contract = contract + contractValue;
						
		});	
		
		$('#contractCount').html(floatToCurrency(contract));
	}
	
	function sumFn(orderExpanseQty){
	
		var deduct = 0;
		$('#example2 tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(6).text();//扣款金额
			
			contractValue= currencyToFloat(contractValue);
			//alert("计划用量+已领量+库存:"+fjihua+"---"+fyiling+"---"+fkucun)
			
			deduct = deduct + contractValue;
						
		});	
		
		var contract = currencyToFloat($('#contractCount').text());
		var stockin = currencyToFloat(stockinSum());

		var taxExcluded,taxes,payment;
		var taxRate = '${ contract.taxRate }';
		taxRate = currencyToFloat(taxRate)/100;
		orderExpanseQty = currencyToFloat(orderExpanseQty);
		var deductCnt = Math.abs(deduct) + Math.abs(orderExpanseQty) + Math.abs(stockin);
		payment = contract - deductCnt;//应付款
		taxExcluded = payment * (1 - taxRate);
		taxes = payment - taxExcluded;

		$('#deductCount').html(floatToCurrency(deduct));
		$('#deductCount1').html(floatToCurrency(deduct));
		$('#deductCount2').html(floatToCurrency(orderExpanseQty));
		$('#deductCount3').html(floatToCurrency(stockin));
		
		$('#payment').html(floatToCurrency(payment));
		$('#taxExcluded').html(floatToCurrency(taxExcluded));
		$('#taxes').html(floatToCurrency(taxes));
		$('#costDeduct').val(floatToCurrency(deductCnt));
		$('#oldDeduct').val(floatToCurrency(deductCnt));
	
	}
	

	function showContract() {
		var contractId = '${ contract.contractId }';
		var url = '${ctx}/business/requirement?methodtype=contractPrint';
		url = url +'&contractId='+contractId;
		//alert(url)
		
		layer.open({
			offset :[10,''],
			type : 2,
			title : false,
			area : [ '1100px', '520px' ], 
			scrollbar : false,
			title : false,
			content : url,
			//只有当点击confirm框的确定时，该层才会关闭
			cancel: function(index){ 
			 // if(confirm('确定要关闭么')){
			  //  layer.close(index)
			 // }
			  //baseBomView();
			 // return false; 
			}    
		});		

	};
</script>
<script type="text/javascript">

function expenseAjax3() {//工厂（供应商）增减费用

	var table = $('#supplier').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${contract.YSId}';
		var contractId = '${contract.contractId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=S&YSId="+YSId+"&contractId="+contractId;

		var t = $('#supplier').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
			"async"		: false,
	        "ordering"  : false,
			"sAjaxSource" : actionUrl,
			dom : '<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {
						
						fnCallback(data);

						//supplierSum();
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-center'},
			    {"data": "supplierId", "defaultContent" : '',"className" : 'td-center'},//供应商
			    {"data": "contractId", "defaultContent" : '',"className" : 'td-center'},//合同编号
			    {"data": "costName", "defaultContent" : '',"className" : 'td-left'},//增减内容3
			    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},//金额4
			    {"data": "remarks", "defaultContent" : ''},//备注5
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
	    			return rownum;
	    		}}
		     ] ,
		})
		
		t.on('change', 'tr td:nth-child(5)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(4).find("input");//金额			
			$cost.val(floatToCurrency($cost.val()));
		});
	
};//供应商

//供应商
function supplierSum(){	
	var contract = 0;	
	$('#supplier tbody tr').each (function (){
		var contractValue = $(this).find("td").eq(4).text();//
		contractValue= currencyToFloat(contractValue);
		contract = contract + contractValue;
	});	
	$('#supplierSum').html(floatToCurrency(contract));
}

function doShowDetail(requisitionId) {
	var virtualClass = $('#virtualClass').val();
	var methodtype = "excessDetail";
	
	var url =  "${ctx}/business/requisition?methodtype="+methodtype+"&requisitionId="+requisitionId+"&virtualClass="+virtualClass;
	
	callProductDesignView("超领详情",url);
}

</script>
<script type="text/javascript">

function stockinAjax() {//入库退货

	var table = $('#stockin').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${contract.YSId}';
		var contractId = '${contract.contractId}';
		var actionUrl = "${ctx}/business/depotReturn?methodtype=getDepotReturnByContract&&YSId="+YSId+"&contractId="+contractId;

		var t = $('#stockin').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
			"async"		: false,
	        "ordering"  : false,
			"sAjaxSource" : actionUrl,
			dom : '<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : null,//JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {
						
						fnCallback(data);

						stockinSum();
						
						sumFn();
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-center'},
			    {"data": null,"className" : 'td-center'},
			    {"data": "receiptId", "defaultContent" : '',"className" : 'td-left'},//入库单号
			    {"data": "contractId", "defaultContent" : '',"className" : 'td-left'},//合同编号
			    {"data": "quantity", "defaultContent" : '',"className" : 'td-right'},//数量
			    {"data": "price", "defaultContent" : '',"className" : 'td-right'},//单价
			    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},//预计扣款
			    {"data": null, "defaultContent" : '',"className" : 'td-right'},//实际扣款
			    {"data": "remarks", "defaultContent" : ''},//备注6
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
	    			return rownum;
	    		}},
	    		{"targets":1,"render":function(data, type, row){	
	    			var reverseValid = currencyToFloat(row["reverseValid"]);//扣款是否有效标识
	    			var rtn="";
	    			if(reverseValid == '1'){
	    				rtn = '已取消';
	    			}else{
	    				rtn= "<a href=\"###\" onClick=\"doDeleteStockin('"+ row["receiptId"] + "')\">"+"取消"+"</a>";	        			
	    			}
	    			
	    			return rtn;
	    		}},
	    		{"targets":6,"render":function(data, type, row){	
	    			var quantity = currencyToFloat(row["quantity"]);
	    			var price = currencyToFloat(row["price"]);
	    			var cost = quantity * price;
	    			
	    			return floatToCurrency(cost);
	    		}},
	    		{"targets":7,"render":function(data, type, row){	
	    			var quantity = currencyToFloat(row["quantity"]);
	    			var price = currencyToFloat(row["price"]);
	    			var reverseValid = currencyToFloat(row["reverseValid"]);
	    			
	    			var cost = quantity * price;
	    			
	    			if(reverseValid == '1')
	    				cost = 0;//取消该扣款
	    			
	    			return floatToCurrency(cost);
	    		}}
		     ] ,
		})
		
	
};//供应商

//供应商
function stockinSum(){	
	var contract = 0;	
	var actualCnt = 0;	
	$('#stockin tbody tr').each (function (){
		var contractValue = $(this).find("td").eq(6).text();//
		var actual = $(this).find("td").eq(7).text();//
		
		contractValue= currencyToFloat(contractValue);
		actual= currencyToFloat(actual);
		
		contract = contract + contractValue;
		actualCnt = actualCnt + actual;
	});	
	$('#stockinSum').html(floatToCurrency(contract));
	$('#stockinActual').html(floatToCurrency(actualCnt));
	
	return actualCnt;
}

function doDeleteStockin(id){

	if(confirm('取消后不能恢复,确定要取消吗?')){
		
		var url = "${ctx}/business/depotReturn?methodtype=CansolDepotReturnByStockinId&stockinId="+id;					
		
		$.ajax({
			type : "post",
			url : url,
			async : false,
			data : 'key=',
			dataType : "json",
			success : function(data) {

				var returnCode = data["returnCode"];
				if(returnCode == 'SUCCESS'){
					//$("#supplier\\.shortname").addClass('error');
					//$().toastmessage('showWarningToast', "该简称已存在，请重新输入。");
				
					$().toastmessage('showNoticeToast', "操作成功。");						
					
					stockinAjax();
				}
			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {
				
				alert('取消失败。');
			}
		});
		
	};
}

</script>

<script type="text/javascript">

function stockinList() {//收货记录

	var table = $('#contractStockin').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var contractId = '${contract.contractId}';
		var actionUrl = "${ctx}/business/storage?methodtype=getStockinListById&contractId="+contractId;

		var t = $('#contractStockin').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
			"async"		: false,
	        "ordering"  : false,
			"sAjaxSource" : actionUrl,
			dom : '<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {
						
						fnCallback(data);
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-center'},
			    {"data": "receiptId", "defaultContent" : '',"className" : 'td-center'},//入库单号
			    {"data": "checkInDate", "defaultContent" : '',"className" : 'td-center'},//入库时间
			    {"data": "materialId", "defaultContent" : '',"className" : ''},//物料编号
			    {"data": "materialName", "defaultContent" : '',"className" : ''},//物料名称
			   // {"data": "remarks", "defaultContent" : ''},//合同数量
			    {"data": "quantity", "defaultContent" : '0',"className" : 'td-right'},//入库数量
				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
	    			return rownum;
	    		}}
		     ] ,
		})
		
	
};//收货

//
function supplierSum2(){	
	var contract = 0;	
	$('#supplier tbody tr').each (function (){
		var contractValue = $(this).find("td").eq(4).text();//
		contractValue= currencyToFloat(contractValue);
		contract = contract + contractValue;
	});	
	$('#supplierSum').html(floatToCurrency(contract));
}

function doShowDetail2(requisitionId) {
	var virtualClass = $('#virtualClass').val();
	var methodtype = "excessDetail";
	
	var url =  "${ctx}/business/requisition?methodtype="+methodtype+"&requisitionId="+requisitionId+"&virtualClass="+virtualClass;
	
	callProductDesignView("超领详情",url);
}

</script>

<script type="text/javascript">

function contractPayment() {//付款记录

	var table = $('#contractPayment').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var contractId = '${contract.contractId}';
		var actionUrl = "${ctx}/business/payment?methodtype=contractPayment&contractId="+contractId;

		var t = $('#contractPayment').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
			"async"		: false,
	        "ordering"  : false,
			"sAjaxSource" : actionUrl,
			dom : '<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {
						
						fnCallback(data);
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-center'},
			    {"data": "paymentHistoryId", "defaultContent" : '',"className" : 'td-center'},//付款单号
			    {"data": "finishDate", "defaultContent" : '',"className" : 'td-center'},//付款时间
			    {"data": "paymentAmount", "defaultContent" : '0',"className" : 'td-right'},//金额
			    {"data": "contractId", "defaultContent" : '',"className" : ''},//关联合同
				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
	    			return rownum;
	    		}},
	    		{"targets":3,"render":function(data, type, row){	
	    			
	    			return floatToCurrency(data);
	    		}}
		     ] ,
		})
		
	
};//


</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
			
		<input type="hidden" id="methodtype" value="${methodtype }" />
		<form:hidden path="contract.recordid" value="${contract.contractRecordId }"/>
		<input type="hidden" id="deleteFlag" value="${deleteFlag }" />
		
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
			
				<tr id="ysid00"> 		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td colspan="9">
						<a href="#" id="ysidLink" onClick="doShowYS('${contract.YSId}')">${contract.YSId }</a>
						<form:hidden path="contract.ysid" value="${contract.YSId }"/></td>
				</tr>
				<tr> 		
					<td class="label"><label>供应商编号：</label></td>					
					<td width="150px">${ contract.supplierId }
						<form:hidden path="contract.supplierid" value="${contract.supplierId }"/></td>
									
					<td class="label" width="100px"><label>供应商简称：</label></td>					
					<td width="100px">${ contract.shortName }</td>
						
					<td class="label" width="100px"><label>供应商名称：</label></td>
					<td colspan="5">${ contract.fullName }</td>
				</tr>	
				<tr> 		
					<td class="label"><label>合同编号：</label></td>					
					<td>${ contract.contractId }
						<form:hidden path="contract.contractid" value="${contract.contractId }"/></td>
					<td class="label" width="100px">付款条件：</td>
					<td>发票后&nbsp;${ contract.paymentTerm }&nbsp;天</td>
					<td class="label"><label>下单日期：</label></td>
					<td width="100px">${ contract.purchaseDate }</td>
					<td class="label" width="100px"><label>合同交期：</label></td>
					<td width="100px">${ contract.deliveryDate }</td>
					<td class="label" width="100px">采购员：${ contract.purchaser }</td>
					<td></td>
				</tr>									
			</table>
			
	</fieldset>
	
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="submit" id="doEdit" class="DTTT_button">编辑</button>
		<button type="submit" id="doDelete" class="DTTT_button">删除</button>
		<button type="button" id="doPrint" class="DTTT_button" onclick="showContract();">打印</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	<fieldset style="margin-top: -30px;">
	<legend> 合同付款</legend>
		<table class="form" style="font-weight: bold;font-size: 13px;">	
			<tr> 		
				<td width="100px" class="label"><label>合同总金额：</label></td>					
				<td width="100px">${ contract.total }</td>
				<td width="100px" class="label"><label style="color: red;">报废扣款：</label></td>					
				<td width="60px"><span id="deductCount1" style="color: red;"></span></td>
				<td width="100px" class="label"><label style="color: red;">订单过程扣款：</label></td>					
				<td width="60px"><span id="deductCount2" style="color: red;"></span></td>
				<td width="100px" class="label"><label style="color: red;">入库退货：</label></td>					
				<td width="60px"><span id="deductCount3" style="color: red;"></span></td>
				<td width="100px" class="label"><label>最终协商扣款：</label></td>					
				<td width="">
					<input type="text" id="costDeduct" class="num mini" value="${ contract.deduct }" />
					<input type="hidden" id="oldDeduct"   />
					<button type="button" id="doEditDeduct" class="DTTT_button" >修改扣款</button>
					<button type="button" id="doSaveDeduct" class="DTTT_button" >保存</button>
					<button type="button" id="doCancelDeduct" class="DTTT_button" >取消修改</button>
				</td>
			</tr>
			<tr>
				<td width="" class="label"><label>应付款：</label></td>					
				<td width=""><span id="payment"></span></td>
				<td width="" class="label"><label>退税率：</label></td>
				<td width="">${ contract.taxRate }</td>
				<td width="" class="label">退税额：</td>
				<td width=""><span id="taxes"></span></td>
				<td width="" class="label"><label>税前价：</label></td>
				<td><span id="taxExcluded"></span></td>
				<td></td>
				<td></td>
			</tr>	
		</table>
	</fieldset>
	<fieldset style="margin-top: -10px;">
		<legend> 合同详情</legend>
		<div class="list">
			<table id="example" class="display" >	
				<thead>
				<tr>
					<th style="width:10px">No</th>
					<th style="width:130px">物料编码</th>
					<th>物料名称</th>
					<th style="width:60px">合同数</th>
					<th style="width:60px">单价</th>
					<th style="width:60px">合同金额</th>
				</tr>
				</thead>		
				<tbody>
					<c:forEach var="detail" items="${detail}" varStatus='status' >	
						<tr id="detailTr${status.index }">
							<td></td>
							<td><a href="###" id="meteLink${status.index }" onClick="doEditMaterial('${detail.materialRecordId}','${detail.materialParentId}')">${detail.materialId}</a>
								<form:hidden path="detailList[${status.index}].materialid" value="${detail.materialId}" /></td>								
							<td><span id="name${status.index}"></span>${ detail.description }</td>					
						
							<td>${ detail.quantity}   </td>								
							<td><span id="price${status.index }">${ detail.price }</span></td>
							<td><span id="total${status.index }">${ detail.totalPrice }</span></td>
								
								<form:hidden path="detailList[${status.index}].recordid" value="${detail.recordId}" />	
								<form:hidden path="detailList[${status.index}].quantity" value="${detail.quantity}" />	
						</tr>	
										
						<script type="text/javascript">
							//var materialName = '${detail.materialName}';
							var index = '${status.index}';
							var contractQty = currencyToFloat('${detail.quantity}');
							var chargeback = currencyToFloat('${detail.chargeback}');
							var price = currencyToFloat('${detail.price}');
							//alert("合同数量+退货数量+单价"+contractQty+"---"+chargeback+"---"+price)
							var contractValue = contractQty * price;
							
							var pay = floatToCurrency( contractValue + chargeback );
							
							//$('#name'+index).html(jQuery.fixedWidth(materialName,64));
							
							//$('#returnValue'+index).html(floatToCurrency( chargeback ));
							//$('#pay'+index).html(pay);
							$('#price'+index).html(formatNumber(price));
							$('#total'+index).html(floatToCurrency(contractValue));
		
							var deleteFlag = '${detail.deleteFlag}';
							if( deleteFlag == 1 ) {
								$('#detailTr'+index).addClass('delete');
								$('#meteLink'+index).contents().unwrap();	
								$('#total'+index).html('0');
								//$('#pay'+index).html('0');
			 				}				
						</script>	
							
					</c:forEach>
					
				</tbody>
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td >合计：</td>
						<td ><div id="contractCount"></div></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</fieldset>
	
	<fieldset>
		<span class="tablename"> 合同收货记录</span>
		<div class="list">		
			<table id="contractStockin" class="display" >
				<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="100px">入库单号</th>
						<th class="dt-center" width="100px">入库时间</th>
						<th class="dt-center" width="150px">物料编号</th>
						<th class="dt-center" width="">物料名称</th>
						<th class="dt-center" width="100px">入库数量</th>
					</tr>
				</thead>					
			</table>
		</div>
	</fieldset>
	<fieldset>
		<span class="tablename"> 付款记录</span>
		<div class="list">		
			<table id="contractPayment" class="display" >
				<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="80px">付款单号</th>
						<th class="dt-center" width="70px">付款时间</th>
						<th class="dt-center" width="100px">付款金额</th>
						<th class="dt-center" width="">关联合同</th>
					</tr>
				</thead>					
			</table>
		</div>
	</fieldset>
	<fieldset style="margin-top: -10px;">
		<legend> 超领扣款明细</legend>
		<div class="list">
			<table id="example2" class="display" >	
				<thead>
				<tr>
					<th style="width:10px">No</th>
					<th style="width:80px">退还耀升编号</th>
					<th style="width:80px">超领单号</th>
					<th>超领原由</th>
					<th style="width:50px">退还数量</th>
					<th style="width:50px">单价</th>
					<th style="width:60px">扣款金额</th>
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
						<td><div id="deductCount"></div></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</fieldset>
	<!-- 
	<fieldset>
		<span class="tablename"> 订单过程扣款明细</span>
		<div class="list">		
		<table id="supplier" class="display" >
			<thead>				
				<tr>
					<th width="20px">No</th>
					<th class="dt-center" width="100px">供应商</th>
					<th class="dt-center" width="150px">合同编号</th>
					<th class="dt-center" width="200px">增减内容</th>
					<th class="dt-center" width="100px">金额</th>
					<th class="dt-center">备注</th>
				</tr>
				</thead>	
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td style="text-align: right;">扣款合计：</td>
						<td ><div id="supplierSum"></div></td>
						<td ></td>
					</tr>
				</tfoot>					
			</table>
		</div>
	</fieldset>
	 -->
	<fieldset>
		<span class="tablename"> 合同入库扣款明细</span>（点击【取消】，该条记录可以修改为“不扣款”）
		<div class="list">		
		<table id="stockin" class="display" >
			<thead>				
				<tr>
					<th width="20px">No</th>
					<th class="dt-center" width="60px">是否扣款</th>
					<th class="dt-center" width="60px">入库单号</th>
					<th class="dt-center" width="150px">合同编号</th>
					<th class="dt-center" width="100px">退货数量</th>
					<th class="dt-center" width="100px">单价</th>
					<th class="dt-center" width="100px">预计扣款</th>
					<th class="dt-center" width="100px">实际扣款</th>
					<th class="dt-center">备注</th>
				</tr>
				</thead>	
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td style="text-align: right;">扣款合计：</td>
						<td ><div id="stockinSum"></div></td>
						<td ><div id="stockinActual"></div></td>
						<td ></td>
					</tr>
				</tfoot>					
			</table>
		</div>
	</fieldset>
	<fieldset>
	<legend> 合同注意事项</legend>
		<table class="form" >
			<tr>
				<td class="td-left"><pre>${contract.memo}</pre></td>
			</tr>
		</table>	
	</fieldset>
		
</form:form>

</div>
</div>
</body>	
</html>
