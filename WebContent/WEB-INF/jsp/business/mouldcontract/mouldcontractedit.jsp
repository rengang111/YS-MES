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
var layerHeight = "250";
//var sumPrice = 0.0;

function ajaxMouldContractRegulationList() {
	var table = $('#MouldContractRegulationList').dataTable();
	
	if(table) {
		table.fnDestroy();
	}

	var t = $('#MouldContractRegulationList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/mouldcontract?methodtype=getMouldRegulationList",
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
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "name", "className" : 'td-center'}, 
						{"data" : "money", "className" : 'td-center'},
					],
					"columnDefs":[
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

function ajaxMouldContractDetailList() {
	var table = $('#MouldContractDetailList').dataTable();
	
	if(table) {
		table.fnDestroy();
	}

	var t = $('#MouldContractDetailList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/mouldcontract?methodtype=getMouldContractDetailList",
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
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "name", "className" : 'td-center'}, 
						{"data" : "money", "className" : 'td-center'},
						{"data" : null, "className" : 'td-center'},
					],
					"columnDefs":[
			    		{"targets":7,"render":function(data, type, row){
			    			if (row["id"] == "") {
			    				return "合计"
			    			}
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


function initEvent(){
	
	validatorBaseInfo = $("#mouldContractBaseInfo").validate({
		rules: {
			productModelId: {
				required: true,
				minlength: 1,
				maxlength: 120,
			},
			type: {
				required: true,
			},
			supplierId: {
				required: true,
			},
			contractDate: {
				required: true,
				date: true,
			},
			deliverDate: {
				required: true,
				date: true,
			},
			belong: {
				required: true,
			},
			oursidePay: {
				digits: true,
				maxLength: 20,
			},
			providerPay: {
				digits: true,
				maxLength: 20,
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
	
	$("#contractDate").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
	});
	if ($("#contractDate").val() == "") {
		$("#contractDate").datepicker( 'setDate' , new Date() );
	}
	$("#deliverDate").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
	});
	if ($("#deliverDate").val() == "") {
		$("#deliverDate").datepicker( 'setDate' , new Date() );
	}
	
	autoComplete();
}

$(window).load(function(){
	
});

$(document).ready(function() {

	initEvent();
	
	ajaxMouldContractDetailList();
	ajaxMouldContractRegulationList();
	
})

function getSumPrice() {
	var length = $("#MouldContractDetailList tr").length;
	var sumPrice = 0.0;
	
	for (var i = 2; i < (length - 1); i++) {
		var tr = $("#MouldContractDetailList tr").eq(i);
		if ($(tr).find("td").eq(0).find("input").prop('checked')) {
			sumPrice += parseFloat($(tr).find("td").eq(8).html());//收入类别
		}
   	}
	
	var tr = $("#MouldContractDetailList tr").eq(length - 1);
	$(tr).find("td").eq(8).html(sumPrice);
}

function getMouldContractId() {
	var actionUrl = "${ctx}/business/mouldcontract?methodtype=getContractId";
	
	if ($('#supplierIdView').val() != "") {
	
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
					$('#contractId').html('<font color="red">' + d.info + '</font>');
				}
				
				//不管成功还是失败都刷新父窗口，关闭子窗口
				//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();
				//parent.layer.close(index); //执行关闭
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {

			}
		});
	} else {
		$('#contractId').html("");
	}
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
						//reloadMouldContractDetailList();
						reloadTabWindow();
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();
					//parent.layer.close(index); //执行关闭
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
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
					reloadMouldContractDetailList();
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
			}
		});
	}
}

function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		$('#delete').attr("disabled", true);
		$('#printmd').attr("disabled", true);
		
	} else {
		$('#delete').attr("disabled", false);
		$('#printmd').attr("disabled", false);
	}
}

function clearAll() {
	sumPrice = 0;
	$('#contractId').val("");
	$('#productModelId').val("");
	$('#productModelIdView').val("");
	$("#productModelName").val("");
	$('#payCase').val("");
	$('#finishTime').val("");
}

function autoComplete() { 

	$("#supplierIdView").autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "POST",
				url : "${ctx}/business/mouldcontract?methodtype=productModelIdSearch",
				dataType : "json",
				data : {
					key : $('#mouldFactoryId').val()
				},
				success : function(data) {
					response($.map(
						data.data,
						function(item) {
							//alert(item.viewList)
							return {
								label : item.viewList,
								value : item.name,
								id : item.id,
								name: item.name,
								des : item.des
							}
						}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
				}
			});
		},

		select : function(event, ui) {
			var oldProductModelId = $("#productModelId").val();
			$("#productModelId").val(ui.item.id);
			$("#productModelIdView").val(ui.item.name);
			$("#productModelName").val(ui.item.des);
			if (oldProductModelId != $("#productModelId").val()) {
				reloadMouldContractDetailList();
			}
		},
		minLength : 1,
		autoFocus : false,
		width: 200,
		mustMatch:true,
		autoFill:true,
		selectFirst:true,
	});	
}

function reloadMouldContractDetailList() {
	$('#MouldContractDetailList').DataTable().ajax.reload(null,false);
}

function reloadMouldContractRegulationList() {
	$('#MouldContractRegulationList').DataTable().ajax.reload(null,false);
}

function doReturn() {

	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	parent.layer.close(index); //执行关闭
	
}
</script>
</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
			<form:form modelAttribute="dataModels" id="mouldContractBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<input type=hidden id="supplierId" name="supplierId" value="${DisplayData.mouldContractBaseInfoData.supplierid}"/>
				<legend>模具合同-基本信息</legend>
				<div style="height:10px"></div>
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<table class="form" width="1100px" cellspacing="0" style="table-layout:fixed">
					<tr>
						<td width="60px">编号：</td>
						<td width="130px">
							<label id="mouldId" name="mouldId" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.mouldid}</label>
						</td>
						<td width="60px">机器型号：</td>
						<td width="130px">
							<input type=text name="productModelId" id="productModelId" class="required mini" />
						</td>
						<td width="60px">模具类型：</td>
						<td width="130px">
							<form:select path="type" onChange="getContractId();"  onblur="getContractId();">
								<form:options items="${DisplayData.typeList}" itemValue="key"
									itemLabel="value" />
							</form:select>
						</td>
						<td width="50px">
							供应商ID：
						</td>
						<td width="100px">
							<input type="text" id="supplierIdView" name="supplierIdView" class="short readonly" readonly="readonly" value="${DisplayData.supplierIdView}"></input>
						</td>
						<td width="50px">
							供应商名称：
						</td>
						<td width="100px">
							<input type="text" id="supplierName" name="supplierName" class="short" value="${DisplayData.supplierName}"></input>
						</td>
					</tr>
					<tr>
						<td>
							合同日期：
						</td>
						<td>
							<input type="text" id="contractDate" name="contractDate" class="short" value="${DisplayData.mouldContractBaseInfoData.contractDate}"></input>
						</td>
						<td>
							合同交期：
						</td>
						<td>
							<input type="text" id="deliverDate" name="deliverDate" class="short" value="${DisplayData.mouldContractBaseInfoData.deliverDate}"></input>
						</td>
						<td>
							模具归属：
						</td>
						<td>
							<form:select path="belong"">
								<form:options items="${DisplayData.belongList}" itemValue="key"
									itemLabel="value" />
							</form:select>
						</td>
						<td>
							我方费用：
						</td>
						<td>
							<input type="text" id="oursidePay" name="oursidePay" class="mini" value="${DisplayData.mouldContractBaseInfoData.oursidepay}"></input>
						</td>
						<td>
							供方费用：
						</td>
						<td>
							<input type="text" id="providerPay" name="providerPay" class="mini" value="${DisplayData.mouldContractBaseInfoData.providerpay}"></input>
						</td>
						<td>
							供方返还条件：
						</td>
						<td>
							<textarea id="returnCase" name="returnCase" cols=10 rows=3>${DisplayData.mouldContractBaseInfoData.returnCase}</textarea>
						</td>
					</tr>
				</table>
				
				<div  style="height:20px"></div>
				<legend>合同增减项</legend>
				<div>
				<button type="button" id="printmd" class="DTTT_button" onClick="doCreateContract();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>				
				</div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="MouldContractRegulationList" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 40px;" class="dt-middle">No</th>
								<th style="width: 60px;" class="dt-middle">名称</th>
								<th style="width: 60px;" class="dt-middle">急呢</th>
								<th style="width: 80px;" class="dt-middle">操作</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
				
				<div  style="height:20px"></div>
				<legend>模具详情</legend>
				<div>
				<button type="button" id="printmd" class="DTTT_button" onClick="doPrintContract();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >打印模具合同</button>				
				</div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="MouldContractDetailList" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 40px;" class="dt-middle">No</th>
								<th style="width: 80px;" class="dt-middle">编号</th>
								<th style="width: 80px;" class="dt-middle">名称</th>
								<th style="width: 80px;" class="dt-middle">尺寸</th>
								<th style="width: 80px;" class="dt-middle">重量</th>
								<th style="width: 80px;" class="dt-middle">材质</th>
								<th style="width: 80px;" class="dt-middle">出模数</th>
								<th style="width: 80px;" class="dt-middle">单价</th>
								<th style="width: 80px;" class="dt-middle">数量</th>
								<th style="width: 80px;" class="dt-middle">总价</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
			</form:form>
		</div>
</html>
