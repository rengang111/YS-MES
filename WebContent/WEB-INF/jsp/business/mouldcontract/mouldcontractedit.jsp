<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具合同</title>
<script type="text/javascript">

var validatorBaseInfo;
var validatorAcceptance;
var validatorPay;
var layerHeight = "250";
var sumPrice = 0.0;
var paid = 0.0;

function ajaxMouldDetailList() {
	var table = $('#MouldDetailList').dataTable();
	
	if(table) {
		table.fnDestroy();
	}

	var t = $('#MouldDetailList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/mouldcontract?methodtype=getMouldDetailList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#mouldContractBaseInfo").serializeArray();
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
								/*
								if (data.message != undefined) {
									alert(data.message);
								}
								*/
								sumPrice = data.sumPrice;
								$('#sumPrice').html(data.sumPrice);
								if (sumPrice != '' && sumPrice != 0) {
									$('#payable').html(sumPrice - parseFloat('${DisplayData.mouldPayInfoData.withhold}'));
								}
								fnCallback(data);

							},
							 error:function(XMLHttpRequest, textStatus, errorThrown){
				                 //alert(XMLHttpRequest.status);
				                 //alert(XMLHttpRequest.readyState);
				                 //alert(textStatus);
				             }
						})
					},
						
					"language": {
		        		"url":"${ctx}/plugins/datatables/chinese.json"
		        	},
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "type", "className" : 'td-center'},
						{"data" : "no", "className" : 'td-center'}, 
						{"data" : "name", "className" : 'td-center'},
						{"data" : "size", "className" : 'td-center'}, 
						{"data" : "materialQuality", "className" : 'td-center'},
						{"data" : "mouldUnloadingNum", "className" : 'td-center'},
						{"data" : "heavy", "className" : 'td-center'},
						{"data" : "price", "className" : 'td-center'},
						{"data" : "mouldFactory", "className" : 'td-center'},
						{"data" : "place", "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheckMD' id='numCheckMD' value='" + row["id"] + "' />"
	                    }},
			    		{"targets":11,"render":function(data, type, row){
			    			return "<a href=\"#\" onClick=\"doUpdateMD('" + row["id"] + "')\">编辑</a>"
	                    }}
				    ] 						
				});
	
	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

		var tr = $(this).closest('tr');
		
		var row = t.row(tr);

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(format(row.data())).show();
			tr.addClass('shown');
		}
	});
};

function ajaxPayList() {

	var table = $('#payList').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t = $('#payList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/mouldcontract?methodtype=getPayList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#mouldContractBaseInfo").serializeArray();
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
								/*
								if (data.message != undefined) {
									alert(data.message);
								}
								*/
								$('#paid').html(data.paid);
								paid = data.paid;
								if (sumPrice != '' && sumPrice != 0) {
									$('#payable').html(sumPrice - parseFloat('${DisplayData.mouldPayInfoData.withhold}'));
								}
								fnCallback(data);
							},
							 error:function(XMLHttpRequest, textStatus, errorThrown){
				                 //alert(XMLHttpRequest.status);
				                 //alert(XMLHttpRequest.readyState);
				                 //alert(textStatus);
				             }
						})
					},
						
					"language": {
		        		"url":"${ctx}/plugins/datatables/chinese.json"
		        	},
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'},
						{"data" : "payTime", "className" : 'td-center'}, 
						{"data" : "pay", "className" : 'td-center'},
						{"data" : "confirm", "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheckPay' id='numCheckPay' value='" + row["id"] + "' />"
	                    }},
			    		{"targets":3,"render":function(data, type, row){
			    			if (row["confirm"] == '1') {
			    				return "已确认";
			    			} else {
			    				return "";
			    			}
	                    }},
			    		{"targets":4,"render":function(data, type, row){
			    			if (row["confirm"] == '1') {
			    				return "";
			    			} else {
			    				return "<button type='button' id='confirmPay' class='DTTT_button' onClick=\"doConfirmPay('" + row["id"] + "');\">总经理确认</button>"
			    			}
	                    }},
			    		{"targets":5,"render":function(data, type, row){
			    			if (row["confirm"] == '1') {
			    				return "";
			    			} else {
			    				return "<a href=\"#\" onClick=\"doUpdatePay('" + row["id"] + "')\">编辑</a>"
			    			}
	                    }}
				    ] 						
				});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});
	
	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

		//alert(999);

		var tr = $(this).closest('tr');
		t
		var row = t.row(tr);
		t

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(format(row.data())).show();
			tr.addClass('shown');
		}
	});

};

function initEvent(){

    jQuery.validator.addMethod("contractId",function(value, element){ 
    	var rtnValue = false;
    	if (value != '') {
    		var productModelId = $('#productModelId').val();
    		var mouldFactoryId = $('#mouldFactoryId').find("option:selected").text();
    		if (value.length == (7 + productModelId.length + mouldFactoryId.length)) {
  				var year = value.substring(0, 4);
  				var productModelIdVar = value.substring(4, 4 + productModelId.length);
  	    		var mouldFactoryIdVar = value.substring(4 + productModelId.length, mouldFactoryId.length + 4 + productModelId.length);
  	    		var serialNo = value.substring(value.length - 4, value.length);
    			if (year >= '1950' && year <= '2050') {
    				if (productModelId == productModelIdVar ) {
    					if (mouldFactoryId == mouldFactoryIdVar) {
    						if (checkNum(serialNo)) {
    							rtnValue = true;
    						}
    					}
    				}
    			}
    		}    		 
    	}
        return rtnValue;  
    }, "合同编号不正确(年份+型号+工厂简称+3位流水号)"); 
	
	validatorBaseInfo = $("#mouldContractBaseInfo").validate({
		rules: {
			contractId: {
				required: true,
				contractId: true,
				minlength: 8,
				maxlength: 27,
			},
			productModelId: {
				required: true,				
				maxlength: 120,
			},
			mouldFactoryId: {
				required: true,								
				maxlength: 72,
			},
			payCase: {
				required: true,
				digits: true,
				maxlength: 1,
			},
			finishTime: {				
				date: true,
			},
		},
		errorPlacement: function(error, element) {
		    if (element.is(":radio"))
		        error.appendTo(element.parent().next().next());
		    else if (element.is(":checkbox"))											    	
		    	error.insertAfter(element.parent().parent());
		    else
		    	error.insertAfter(element);
		}
	});
	
	validatorAcceptance = $("#acceptanceInfo").validate({
		rules: {
			acceptanceDate: {
				required: true,
				date: true,
				minlength: 10,
				maxlength: 10,
			},
			memo: {			
				maxlength: 200,
			},
		},
		errorPlacement: function(error, element) {
		    if (element.is(":radio"))
		        error.appendTo(element.parent().next().next());
		    else if (element.is(":checkbox"))											    	
		    	error.insertAfter(element.parent().parent());
		    else
		    	error.insertAfter(element);
		}
	});
	
	validatorPay = $("#payInfo").validate({
		rules: {
			withhold: {
				digits: true,
				minlength: 0,
				maxlength: 10,
			},
		},
		errorPlacement: function(error, element) {
		    if (element.is(":radio"))
		        error.appendTo(element.parent().next().next());
		    else if (element.is(":checkbox"))											    	
		    	error.insertAfter(element.parent().parent());
		    else
		    	error.insertAfter(element);
		}
	});
	
	controlButtons($('#keyBackup').val());
	$("#productModelId").val('${DisplayData.mouldBaseInfoData.productmodelid}');
	$("#productModelName").val('${DisplayData.productModelName}');
	
	$("#mouldFactoryId").val('${DisplayData.mouldBaseInfoData.mouldfactoryid}');
	$("#result").val('${DisplayData.mouldAcceptanceData.result}');
	
	if ('${DisplayData.mouldAcceptanceData.confirm}' == '1') {
		doConfirm();
	}
	
	autoComplete();
}

function doConfirm() {
	$('#acceptanceDate').attr("disabled", true);
	$('#result').attr("disabled", true);
	$('#memo').attr("disabled", true);
	$('#updateacceptance').attr("disabled", true);
	$('#confirmacceptance').attr("disabled", true);
}

$(window).load(function(){
	initEvent();
});

$(document).ready(function() {

	ajaxMouldDetailList();
	ajaxPayList();
	
	
})

function reloadMouldDetailList() {
	$('#MouldDetailList').DataTable().ajax.reload(null,false);
	
	//reloadTabWindow();
}

function reloadPayList() {

	$('#payList').DataTable().ajax.reload(null,false);
	
	//reloadTabWindow();
}

function doSave() {

	if (validatorBaseInfo.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/mouldcontract?methodtype=add";
		} else {
			//修正
			actionUrl = "${ctx}/business/mouldcontract?methodtype=update";
		}		
		
		if (confirm(message)) {
			var actionUrl;			

			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#mouldContractBaseInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						$('#tabs').show();
						reloadTabWindow();
						controlButtons(d.info);
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();
					//parent.layer.close(index); //执行关闭
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);					
					//alert(XMLHttpRequest.readyState);					
					//alert(textStatus);					
					//alert(errorThrown);
				}
			});
		}
	}
}

function doDelete() {
	
	if (confirm("${DisplayData.endInfoMap.message}")) {
		//将提交按钮置为【不可用】状态
		//$("#submit").attr("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/business/mouldcontract?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					controlButtons("");
					clearAll();
					reloadMouldDetailList();
					reloadPayList();
					reloadTabWindow();
				}
				/*	
				//不管成功还是失败都刷新父窗口，关闭子窗口
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();/
				parent.reload_contactor();
				parent.layer.close(index); //执行关闭
				*/
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//alert(XMLHttpRequest.status);					
				//alert(XMLHttpRequest.readyState);					
				//alert(textStatus);					
				//alert(errorThrown);
			}
		});
	}
}

function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		$('#delete').attr("disabled", true);
		$('#printmd').attr("disabled", true);
		$('#deletemd').attr("disabled", true);
		$('#addmd').attr("disabled", true);
		$('#acceptanceDate').attr("disabled", true);
		$('#result').attr("disabled", true);
		$('#memo').attr("disabled", true);
		$('#updateacceptance').attr("disabled", true);
		$('#confirmacceptance').attr("disabled", true);
		$('#withhold').attr("disabled", true);
		$('#deletepay').attr("disabled", true);
		$('#addpay').attr("disabled", true);
	} else {
		$('#delete').attr("disabled", false);
		$('#printmd').attr("disabled", false);
		$('#deletemd').attr("disabled", false);
		$('#addmd').attr("disabled", false);
		$('#acceptanceDate').attr("disabled", false);
		$('#result').attr("disabled", false);
		$('#memo').attr("disabled", false);
		$('#updateacceptance').attr("disabled", false);
		$('#confirmacceptance').attr("disabled", false);
		$('#withhold').attr("disabled", false);
		$('#deletepay').attr("disabled", false);
		$('#addpay').attr("disabled", false);
	}
}

function clearAll() {
	sumPrice = 0;
	paid = 0;
	$('#contractId').val("");
	$('#productModelId').val("");
	$("#productModelName").val("");
	$('#mouldFactoryId').val("");
	$('#payCase').val("");
	$('#finishTime').val("");
	$('#acceptanceDate').val("");
	$('#memo').val("");
	$('#withhold').val("");
	$('#sumPrice').html("");
	$('#payable').html("");
	$('#paid').html("");

}

function doAddMD() {
	var mouldBaseId = $('#keyBackup').val();
	var url = "${ctx}/business/mouldcontract?methodtype=addmdinit&mouldBaseId=" + mouldBaseId;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function doUpdateMD(key) {
	var mouldBaseId = $('#keyBackup').val();
	var url = "${ctx}/business/mouldcontract?methodtype=updatemdinit&mouldBaseId=" + mouldBaseId + "&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);
}

function doDeleteMD() {
	var str = '';
	$("input[name='numCheckMD']").each(function(){
		if ($(this).prop('checked')) {
			str += $(this).val() + ",";
		}
	});

	if (str != "") {
		if (confirm("您确认执行该操作吗？") == false) {
			return;
		}
		$.ajax({
			contentType : 'application/json',
			dataType : 'json',						
			type : "POST",
			data : str,// 要提交的表单						
			url : "${ctx}/business/mouldcontract?methodtype=deletemd",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadMouldDetailList();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});
		
	} else {
		alert("请先选中要删除的记录。");
	}
}

function doUpdateAcceptance() {
	if (validatorAcceptance.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";	
		
		if (confirm(message)) {
			var actionUrl;
			if ($('#keyBackup').val() == "") {				
				//新建
				actionUrl = "${ctx}/business/mouldcontract?methodtype=addacceptance";				
			} else {
				//修正
				actionUrl = "${ctx}/business/mouldcontract?methodtype=updateacceptance";
			}

			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#acceptanceInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();
					//parent.layer.close(index); //执行关闭
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);					
					//alert(XMLHttpRequest.readyState);					
					//alert(textStatus);					
					//alert(errorThrown);
				}
			});
		}
	}
	
}

function doConfirmAcceptance() {
	if (validatorAcceptance.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";	
		
		if (confirm(message)) {
			var actionUrl;
			actionUrl = "${ctx}/business/mouldcontract?methodtype=confirmacceptance";

			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#acceptanceInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					}
					doConfirm();
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();
					//parent.layer.close(index); //执行关闭
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);					
					//alert(XMLHttpRequest.readyState);					
					//alert(textStatus);					
					//alert(errorThrown);
				}
			});
		}
	}
	
}

function doAddPay() {
	if (validatorPay.form()) {
		var mouldBaseId = $('#keyBackup').val();
		var url = "${ctx}/business/mouldcontract?methodtype=addpayinit&mouldBaseId=" + mouldBaseId;
		openLayer(url, $(document).width() - 25, layerHeight, false);
	}
}

function doUpdatePay(key) {
	if (validatorPay.form()) {
		var mouldBaseId = $('#keyBackup').val();
		var withhold = $('#withhold').val();
		var url = "${ctx}/business/mouldcontract?methodtype=updatepayinit&mouldBaseId=" + mouldBaseId + "&key=" + key + "&withhold=" + withhold;
		openLayer(url, $(document).width() - 25, layerHeight, false);
	}
}

function doDeletePay() {
	var str = '';
	$("input[name='numCheckPay']").each(function(){
		if ($(this).prop('checked')) {
			str += $(this).val() + ",";
		}
	});

	if (str != "") {
		if (confirm("您确认执行该操作吗？") == false) {
			return;
		}
		$.ajax({
			contentType : 'application/json',
			dataType : 'json',						
			type : "POST",
			data : str,// 要提交的表单						
			url : "${ctx}/business/mouldcontract?methodtype=deletepay",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadPayList();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});
		
	} else {
		alert("请先选中要删除的记录。");
	}
}

function doConfirmPay(key) {

	if (validatorPay.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";	
		
		if (confirm(message)) {
			var actionUrl;
			actionUrl = "${ctx}/business/mouldcontract?methodtype=confirmpay";

			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : key,// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						reloadPayList();
					}
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);					
					//alert(XMLHttpRequest.readyState);					
					//alert(textStatus);					
					//alert(errorThrown);
				}
			});
		}
	}
}

function resetPayable() {
	$('#payable').html(sumPrice - $('#withhold').val());
}
</script>
	<script>
		function autoComplete() { 
			$("#productModelId").autocomplete({
				source : function(request, response) {
					$.ajax({
						type : "POST",
						url : "${ctx}/business/mouldcontract?methodtype=productModelIdSearch",
						dataType : "json",
						data : {
							key : request.term
						},
						success : function(data) {
							response($.map(
								data.data,
								function(item) {
									//alert(item.viewList)
									return {
										label : item.viewList,
										value : item.id,
										id : item.id,
										des : item.des
									}
								}));
						},
						error : function(XMLHttpRequest,
								textStatus, errorThrown) {
							alert(XMLHttpRequest.status);
							alert(XMLHttpRequest.readyState);
							alert(textStatus);
							alert(errorThrown);
							alert("系统异常，请再试或和系统管理员联系。");
						}
					});
				},
	
				select : function(event, ui) {
					$("#productModelId").val(ui.item.id);
					$("#productModelName").val(ui.item.des);
					//$("#factoryProductCode").focus();
	
				},
				minLength : 1,
				autoFocus : false,
				width: 200,
			});	
		}
	</script>
</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
			<form:form modelAttribute="dataModels" id="mouldContractBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<legend>模具合同-基本信息</legend>
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<table class="form" width="850px">
					<tr>
						<td width="90px">模具合同编号：</td>
						<td colspan=3>
							<input type=text id="contractId" name="contractId" value="${DisplayData.mouldBaseInfoData.contractid}"></input>
						</td>
					</tr>
					<tr>
						<td width="60px">产品型号：</td> 
						<td>
							<form:input path="productModelId" class="required middle" />
						</td>
						<td width="60px">产品名称：</td> 
						<td>
							<form:input path="productModelName"	class="read-only" />
						</td>
					</tr>
					<tr>
						<td>
							模具工厂：
						</td>
						<td>
							<form:select path="mouldFactoryId">
								<form:options items="${DisplayData.mouldFactoryList}" itemValue="key"
									itemLabel="value" />
							</form:select>
						</td>
						<td>
							付款条件：
						</td>
						<td>
							交付后
							<input type="text" id="payCase" name="payCase" class="short" value="${DisplayData.mouldBaseInfoData.paycase}"></input>
							天
						</td>
					</tr>
					<tr>
						<td>	
							完成时间：
						</td>
						<td colspan=3> 
							<input type="text" id="finishTime" name="finishTime" class="short" value="${DisplayData.mouldBaseInfoData.finishtime}"></input>
						</td>
					</tr>
				</table>			
				
				<div  style="height:20px"></div>
				<legend>模具详情</legend>
				<div>
				<button type="button" id="printmd" class="DTTT_button" onClick="doPrintContract();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >打印模具合同</button>				
				<button type="button" id="deletemd" class="DTTT_button" onClick="doDeleteMD();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addmd" class="DTTT_button" onClick="doAddMD();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				</div>
				<table id="MouldDetailList" class="display" cellspacing="0">
					<thead>
						<tr class="selected">
							<th style="width: 40px;" class="dt-middle">No</th>
							<th style="width: 40px;" class="dt-middle">类型</th>
							<th style="width: 80px;" class="dt-middle">模具<br>编号</th>
							<th style="width: 80px;" class="dt-middle">模具<br>名称</th>
							<th style="width: 80px;" class="dt-middle">模架<br>尺寸</th>
							<th style="width: 80px;" class="dt-middle">材质</th>
							<th style="width: 80px;" class="dt-middle">出模数</th>
							<th style="width: 80px;" class="dt-middle">重量</th>
							<th style="width: 80px;" class="dt-middle">价格</th>
							<th style="width: 80px;" class="dt-middle">模具<br>工厂</th>
							<th style="width: 40px;" class="dt-middle">存放<br>位置</th>
							<th style="width: 80px;" class="dt-middle">操作</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</tfoot>
				</table>
				<table class="display" cellspacing="0" style='margin: -10px;'>
					<tr>
							<td style="width: 520px;" class="dt-middle">&nbsp;</td>
							<td style="width: 80px;" class="dt-middle">总价</td>
							<td style="width: 80px;" class="dt-middle"><label id="sumPrice"></label></td>
					</tr>
				</table>
			</form:form>
			<form:form modelAttribute="dataModels" id="acceptanceInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<div  style="height:20px"></div>
				<legend>模具验收</legend>
				<table>
					<tr>
						<td>
							验收日期
						</td>
						<td>
							<input type=text id="acceptanceDate" name="acceptanceDate" value="${DisplayData.mouldAcceptanceData.acceptancedate}"/>
						</td>
						<td>
							验收结果
						</td>
						<td>
							<form:select path="result">
								<form:options items="${DisplayData.resultList}" itemValue="key"
									itemLabel="value" />
							</form:select>
							<button type="button" id="updateacceptance" class="DTTT_button" onClick="doUpdateAcceptance();"
							style="height:25px;margin:-20px 0px 0px 0px;" >确认入库</button>
						</td>
					</tr>
					<tr>
						<td colspan=4>
							<textarea id="memo" name="memo" cols=100 rows=10>${DisplayData.mouldAcceptanceData.memo}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan=4>
							<button type="button" id="confirmacceptance" class="DTTT_button" onClick="doConfirmAcceptance();"
							style="height:25px;" >总经理确认</button>
						</td>
					</tr>
				</table>
			</form:form>
			<div  style="height:20px"></div>
			
			<legend>付款申请</legend>
			<form:form modelAttribute="dataModels" id="payInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<table>
					<tr>
						<td>
							扣款金额
						</td>
						<td>
							<input type=text id="withhold" name="withhold" class="short" value="${DisplayData.mouldPayInfoData.withhold}" oninput="resetPayable();"/>
						</td>
						<td>
							应付款金额
						</td>
						<td width="80px">
							<label id="payable"></label>
						</td>
						<td>
							已付款金额
						</td>
						<td width="80px">
							<label id="paid"></label>
						</td>
					</tr>				
				
				<button type="button" id="deletepay" class="DTTT_button" onClick="doDeletePay();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addpay" class="DTTT_button" onClick="doAddPay();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				<div>
					<table id="payList" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 40px;" class="dt-middle">no</th>
								<th style="width: 40px;" class="dt-middle">付款日期</th>
								<th style="width: 100px;" class="dt-middle">付款金额</th>
								<th style="width: 100px;" class="dt-middle">确认状态</th>
								<th style="width: 50px;" class="dt-middle">总经理确认</th>
								<th style="width: 50px;" class="dt-middle">操作</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>

			</form:form>
		</div>
</html>
