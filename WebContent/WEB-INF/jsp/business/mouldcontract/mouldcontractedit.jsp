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
			contractYear: {
				required: true,
				digits: true,
				isYear: true,
			},
			type: {
				required: true,
			},
			supplierIdView: {
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
				maxlength: 20,
			},
			providerPay: {
				maxlength: 20,
			},
			returnCase: {
				maxlength: 100,
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
	
    jQuery.validator.addMethod("isYear",function(value, element){
    	var rtnValue = true;

    	if (value != "") {
			if (parseInt(value) < 1949 && parseInt(value) > 2100) {
				rtnValue = false;
			}
    	}
        return rtnValue;   
    }, "年份");
	
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
	autoCompleteType();
}

$(window).load(function(){
	
});

$(document).ready(function() {

	initEvent();
	
	//ajaxMouldContractDetailList();
	//ajaxMouldContractRegulationList();
	
	if ($('#contractYear').val() == '') {
		var nowDate = new Date();
		$('#contractYear').val(nowDate.getFullYear());
	}
	
	$('#belong').val("${DisplayData.mouldContractBaseInfoData.belong}");
	
	if ($('#keyBackup').val() != '') {
		$('#type').attr('disabled', true);
		$('#supplierIdView').attr('disabled', true);
		
		<c:forEach items="${DisplayData.regulations}" var="item">
			addRegulationTr('${item.name}', '${item.money}');
		</c:forEach>

		<c:forEach items="${DisplayData.mouldDetails}" var="item">
			addMouldContractDetailTr('${item.id}', '${item.mouldId}', '${item.name}', '${item.size}', '${item.weight}', '${item.materialQuality}', '${item.unloadingNum}', '${item.unloadingNum}', '${item.price}', '${item.number}');
		</c:forEach>

	} else {
		//addRegulationTr('', '');
		$('#type').focus();
	}
	
	
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
	var actionUrl = "${ctx}/business/mouldcontract?methodtype=getMouldContractId";
	
	if ($('#type').val() != "" && $('#type').val().substr(0, 1) == 'M') {
	
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
					$('#supplierIdView').attr("disabled", false);
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
		$('#mouldType').html("");
		$('#supplierIdView').attr("disabled", true);
	}
}


function changeWorkingMode(operType, index) {
	var isChangeConfirmed = false;
	if ($('#keyBackup').val() != '') {
		if ($("#MouldContractDetailList tr").length > 0) {
			if (index == 0) {
				if ($('#operType').val() != $('#oldoperType').val()) {
					msg = "修改分类编码有可能使模具详情发生变化。如果确认此变更，需先保存后才可以操作模具详情.确认本次变更吗？";
					if (operType == 0) {
						if (confirm(msg)) {
							isChangeConfirmed = true;
						} else {
							$('#operType').val($('#oldoperType').val());
						}
					} else {
						isChangeConfirmed = true;
					}
				}	
			} else {
				if ($('#supplierId').val() != $('#oldSupplierId').val()) {
					msg = "修改供应商有可能使模具详情发生变化。如果确认此变更，需先保存后才可以操作模具详情。确认本次变更吗？";
					if (operType == 0) {
						if (confirm(msg)) {
							isChangeConfirmed = true;
						} else {
							$('#supplierIdView').val($('#oldSupplierId').val());
						}
					} else {
						isChangeConfirmed = true;
					}
				}
			}
		}
	}
	
	return isChangeConfirmed;
}

function addRegulationTr(name, money){
	 
	var i = $("#MouldContractRegulationList tr").length - 1;	
	var trHtml = "";
	
	trHtml += "<tr>";
	trHtml += "<td class='td-left'>";
	trHtml += "<input name='regulations[" + i + "].name' id='regulations[" + i + "].name' type='text' style='width:150px' value='" + name + "'/>";
	trHtml += "</td>";
	trHtml += "<td class='td-left'>";
	trHtml += "<input name='regulations[" + i + "].money' id='regulations[" + i + "].money' type='text'  style='width:150px' value='" + money + "'/>";
	trHtml += "</td>";
	trHtml += "</tr>";			
	$('#MouldContractRegulationList tr:last').after(trHtml);

	$('#regulations\\[' + i + '\\]\\.name').rules('add', { maxlength: 20 });
	$('#regulations\\[' + i + '\\]\\.money').rules('add', { maxlength: 20, digits: true });
	
	$('#regulationCount').val(i + 1);
}

function addMouldContractDetailTr(id, mouldId, name, size, weight, materialQuality, unloadingNum, price, number, totalPrice) {
	 
	var i = $("#MouldContractDetailList tr").length - 1;	
	var trHtml = "";
	
	trHtml += "<tr>";
	trHtml += "<td class='td-center'>";
	trHtml += "<input name='mouldContractDetail[" + i + "].mouldIdView' id='mouldContractDetail[" + i + "].mouldIdView' type='text' class='short' value='" + id + "'/>";
	trHtml += "<input name='mouldContractDetail[" + i + "].mouldId' id='mouldContractDetail[" + i + "].mouldId' type='hidden' value='" + mouldId + "'/>";
	trHtml += "</td>";
	trHtml += "<td class='td-center'>";
	trHtml += "<label>" + name + "</label>"
	trHtml += "</td>";
	trHtml += "<td class='td-center'>";
	trHtml += "<label>" + size + "</label>"
	trHtml += "</td>";
	trHtml += "<td class='td-center'>";
	trHtml += "<label>" + weight + "</label>"
	trHtml += "</td>";
	trHtml += "<td class='td-center'>";
	trHtml += "<label>" + materialQuality + "</label>"
	trHtml += "</td>";
	trHtml += "<td class='td-center'>";
	trHtml += "<label>" + unloadingNum + "</label>"
	trHtml += "</td>";
	trHtml += "<td class='td-center'>";
	trHtml += "<label>" + price + "</label>"
	trHtml += "</td>";
	trHtml += "<td class='td-center'>";
	trHtml += "<label>" + number + "</label>"
	trHtml += "</td>";
	trHtml += "<td class='td-center'>";
	trHtml += "<label>" + totalPrice + "</label>"
	trHtml += "</td>";
	
	trHtml += "</tr>";			
	$('#MouldContractDetailList tr:last').after(trHtml);

	$('#mouldContractDetail\\[' + i + '\\]\\.name').rules('add', { maxlength: 20 });
	$('#mouldContractDetail\\[' + i + '\\]\\.money').rules('add', { maxlength: 20, digits: true });
	
	$('#mouldContractDetailCount').val(i + 1);
}

function doSave() {
	
	if (validatorBaseInfo.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/mouldcontract?methodtype=add";
			message = "模具类型以及供应商选定后将无法修改。" + message;
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
						var x = new Array();
						x = d.info.split("|");
						controlButtons(x[0]);
						$('#contractId').html(x[1]);
						
						//reloadMouldContractDetailList();
						$('oldContractYear').val($('contractYear').val());
						$('oldType').val($('type').val());
						$('oldSupplierIdView').val($('oldSupplierIdView').val());
						parent.reload();
						
						var url = "${ctx}/business/mouldcontract?methodtype=updateinit&key=" + x[0];
						location.href = url;
						//reloadTabWindow();
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

function autoComplete() { 

	$("#supplierIdView").autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "POST",
				url : "${ctx}/business/mouldcontract?methodtype=supplierSearch",
				dataType : "json",
				data : {
					key : request.term,
					type : $('#type').val()
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
								name: item.name,
								key: item.keyId
							}
						}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
				}
			});
		},

		select : function(event, ui) {
			$("#supplierId").val(ui.item.key);
			$("#supplierIdView").val(ui.item.id);
			$("#supplierName").html(ui.item.name);
		},
		
		change : function(event, ui) {
			if (ui.item == null) {
				//$("#supplierId").val("");
				$("#supplierIdView").val("");
				$("#supplierName").html("");
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

function autoCompleteType() { 
	$("#type").autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "POST",
				url : "${ctx}/business/mouldcontract?methodtype=typeSearch",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					response($.map(
						data.data,
						function(item) {
							return {
								label : item.viewList,
								value : item.id,
								id : item.id,
								name: item.categoryViewName,
								parentId: item.parentcategoryId,
								parentName: item.parentName,
							}
						}));
					datas = data.data;
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
				}
			});
		},

		select : function(event, ui) {

			$("#type").val(ui.item.id);
			$("#mouldType").html(ui.item.parentId + "&nbsp;" + ui.item.parentName);
			getMouldContractId();
			//$("#factoryProductCode").focus();
		},

        change: function(event, ui) {
            // provide must match checking if what is in the input
            // is in the list of results. HACK!
            if(ui.item == null) {
                $(this).val('');
				$("#mouldType").html('');
                $('#contractId').html('');
            }
        },
		
		minLength : 1,
		autoFocus : false,
		width: 200,
		mustMatch:true,
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
				<input type=hidden id="oldType" name="oldType" value="${DisplayData.mouldContractBaseInfoData.type}"/>
				<input type=hidden id="oldContractYear" name="oldContractYear" value="${DisplayData.mouldContractBaseInfoData.contractyear}"/>
				<input type=hidden id="oldSupplierId" name="oldSupplierId" value="${DisplayData.supplierIdView}"/>
				<input type=hidden id="regulationCount" name="regulationCount" value="0"/>
				<input type=hidden id="mouldContractDetailCount" name="regulationCount" value="0"/>
				
				<legend>模具合同-基本信息</legend>
				<div style="height:10px"></div>
				<table class="form" width="1100px" cellspacing="0">
					<tr>
						<td width="60px">编号：</td>
						<td width="130px">
							<label id="contractId" name="contractId" style="margin:0px 10px">${DisplayData.mouldContractBaseInfoData.contractid}</label>
						</td>
						<td width="60px">模具类型：</td>
						<td width="140px" colspan = 3>
							<label id="mouldType" name="mouldType">${DisplayData.mouldType}&nbsp;${DisplayData.typeDesc}</label>
						</td>
					</tr>
					<tr>
						<td width="60px">年份：</td>
						<td width="130px">
							<input type="text" name="contractYear" id="contractYear" class="mini" value="${DisplayData.mouldContractBaseInfoData.contractyear}"></input>
						</td>
						<td width="80px">分类编码：	</td>
						<td width="150px">
							<form:input path="type"	class="short" onBlur="changeWorkingMode();" value="${DisplayData.mouldContractBaseInfoData.type}"/>
						</td>
						<td width="50px">
							供应商ID：
						</td>
						<td width="100px" title="输入**查询同一模具类型的供应商">
							<input type="text" id="supplierIdView" name="supplierIdView" class="short" value="${DisplayData.supplierIdView}" disabled></input>
						</td>
						<td width="50px">
							名称：
						</td>
						<td width="150px">
							<label id="supplierName" name="supplierName" >${DisplayData.supplierName}</label>
						</td>
					</tr>
					<tr>
						<td>
							合同日期：
						</td>
						<td>
							<input type="text" id="contractDate" name="contractDate" class="short" value="${DisplayData.contractDate}"></input>
						</td>
						<td>
							合同交期：
						</td>
						<td>
							<input type="text" id="deliverDate" name="deliverDate" class="short" value="${DisplayData.deliverDate}"></input>
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
					</tr>
					<tr>
						<td>
							模具归属：
						</td>
						<td colspan=7>
							<form:select path="belong">
								<form:options items="${DisplayData.belongList}" itemValue="key"
									itemLabel="value" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td>
							返还条件：
						</td>
						<td colspan=7>
							<textarea id="returnCase" name="returnCase" cols=50 rows=2>${DisplayData.mouldContractBaseInfoData.returncase}</textarea>
						</td>
					</tr>
				</table>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:10px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:10px 5px 0px 0px;float:right;" >保存</button>
			</form:form>
		</div>
</html>
