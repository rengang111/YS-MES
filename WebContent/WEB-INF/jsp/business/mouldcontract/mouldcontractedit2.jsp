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
var detailCounter = 0;
var regulationCounter = 0;
var sumNum = 0;
var sumPrice = 0;
//var sumPrice = 0.0;

function getSum(index) {

	var price = $("#detailLines-" + index + "\\.price").html();
	var num = $("#detailLines-" + index + "\\.num").val();

	if (price == '' || num == '') {
		var num = $("#detailLines-" + index + "\\.totalPrice").html('');
	} else {
		$("#detailLines-" + index + "\\.totalPrice").html(parseFloat(price) * parseFloat(num));
	}
	
	
}

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
					"sAjaxSource" : "${ctx}/business/mouldcontract?methodtype=getMouldContractRegulationList",
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
		    		dom : 'T<"clear">rt',

		    		"tableTools" : {

		    			"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

		    			"aButtons" : [ {
		    				"sExtends" : "add_regulationrows",
		    				"sButtonText" : "追加新行"
		    			}],
		    		},
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "name", "className" : 'td-center'}, 
						{"data" : "money", "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'},
					],
					"columnDefs":[
						{"targets":0,"render":function(data, type, row){
							return row["rownum"]// + "<input type=checkbox name='regulationnumCheck' id='regulationnumCheck' value='" + row["id"] + "' />"
						}},
						{"targets":3,"render":function(data, type, row){
							return "<a href=\"#\" onClick=\"doUpdateRegulation('" + row["id"] + "')\">编辑</a>&nbsp;" + "<a href=\"#\" onClick=\"doDeleteRegulation('" + row["id"] + "')\">删除</a>"
						}},

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
								var rowNode = $('#MouldContractDetailList')
								.DataTable()
								.row
								.add(
								  [
									'<td></td>',
									'<td></td>',
									'<td></td>',
									'<td></td>',
									'<td></td>',
									'<td></td>',
									'<td></td>',
									'<td>合计</td>',
									'<td><label  name="sumNum" id="sumNum"></label></td>',
									'<td><label  name="sumPrice" id="sumPrice"></label></td>',
									'<td</td>'
									]).draw();
							},
							 error:function(XMLHttpRequest, textStatus, errorThrown){
				             }
						})
					},
					dom : 'T<"clear">rt',

					"tableTools" : {

						"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

						"aButtons" : [ {
							"sExtends" : "add_rows",
							"sButtonText" : "追加新行"
						}],
					},
					"language": {
		        		"url":"${ctx}/plugins/datatables/chinese.json"
		        	},
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "mouldId", "className" : 'td-center'}, 
						{"data" : "name", "className" : 'td-center'},
						{"data" : "size", "className" : 'td-center'},
						{"data" : "weight", "className" : 'td-center'},
						{"data" : "materialQuality", "className" : 'td-center'},
						{"data" : "unloadingNum", "className" : 'td-center'},
						{"data" : "price", "className" : 'td-center'},
						{"data" : "number", "className" : 'td-center'},
						{"data" : "totalPrice", "className" : 'td-center'},
						{"data" : null, "className" : 'td-center'},
					],
					"columnDefs":[
						{"targets":0,"render":function(data, type, row){
							//sumNum = sumNum + row["number"]
							//sumPrice = sumPrice + row["totalPrice"]
							return row["rownum"]// + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
						}},
						{"targets":10,"render":function(data, type, row){
							return "<a href=\"#\" onClick=\"doUpdateDetail('" + row["id"] + "')\">编辑</a>&nbsp;" + "<a href=\"#\" onClick=\"doDeleteDetail('" + row["id"] + "')\">删除</a>"
						}},
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

function ajaxMouldContractDetailList2() {

	var t = $('#MouldContractDetailList').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
		//"scrollY"    : "160px",
        "scrollCollapse": false,
        "paging"    : false,
        "pageLength": 50,
        "ordering"  : false,

		dom : 'T<"clear">rt',

		"tableTools" : {

			"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

			"aButtons" : [ {
				"sExtends" : "add_rows",
				"sButtonText" : "追加新行"
			},
			{
				"sExtends" : "reset",
				"sButtonText" : "清空一行"
			}  ],
		},
		
		"columns" : [ 
		        	{"className":"dt-body-center"
				}, {"className":"dt-body-center"
				}, {"className":"dt-body-center"
				}, {"className":"dt-body-center"
				}, {"className":"dt-body-center"
				}, {"className":"dt-body-center"
				}, {"className":"dt-body-center"
				}, {"className":"dt-body-center"
				}, {"className":"dt-body-right"				
				}, {"className":"dt-body-left"				
				}			
			]
		
	}).draw();

	
	t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {

	});
	
					
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

$.fn.dataTable.TableTools.buttons.add_rows = $
.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				var key = $("#keyBackup").val();
				var url = "${ctx}/business/mouldcontract?methodtype=adddetailsinit&mouldId=" + key;
				openLayer(url, '', 300, false);

				
				/*
				var hidden = '';
				
				var lineNo =  detailCounter + 1;
				var hidden = "";
				
				hidden = '';
				
				var rowNode = $('#MouldContractDetailList')
					.DataTable()
					.row
					.add(
					  [
						'<td align="center">' + lineNo + '</td>',
						'<td><input type="text"   name="detailLines-' + detailCounter + '.mouldId" id="detailLines-' + detailCounter + '.mouldId" style="width:80px;"/></td>',
						'<td align="center"><label   name="detailLines-' + detailCounter + '.name" id="detailLines-' + detailCounter + '.name"  style="width:80px;" class="readonly" readonly="readonly"></label>' + 
						                   '<input type="hidden" name="detailLines-' + detailCounter + '.id" id="detailLines-' + detailCounter + '.id"/>' +
						                   '<input type="hidden" name="detailLines-' + detailCounter + '.mouldFactoryId" id="detailLines-' + detailCounter + '.mouldFactoryId"/>' +
						'</td>',
						'<td align="center"><label   name="detailLines-' + detailCounter + '.size"   id="detailLines-' + detailCounter + '.size"   style="width:80px;" class="short readonly" readonly="readonly" /></label></td>',
						'<td align="center"><label   name="detailLines-' + detailCounter + '.weight"   id="detailLines-' + detailCounter + '.weight"  style="width:80px;"  class="short readonly" readonly="readonly"/></label></td>',
						'<td align="center"><label   name="detailLines-' + detailCounter + '.materialQuality"   id="detailLines-' + detailCounter + '.materialQuality"  style="width:80px;" class="short readonly" readonly="readonly"/></label></td>',
						'<td align="center"><label   name="detailLines-' + detailCounter + '.unloadingNum"   id="detailLines-' + detailCounter + '.unloadingNum"  style="width:80px;"  class="short readonly" readonly="readonly"/></label></td>',
						'<td align="center"><label   name="detailLines-' + detailCounter + '.price"   id="detailLines-' + detailCounter + '.price"  style="width:80px;"  class="short readonly" readonly="readonly"/></label></td>',
						'<td><input type="text"   name="detailLines-' + detailCounter +'.num"   id="detailLines-' + detailCounter + '.num"  style="width:80px;"  class="short readonly" onInput="getSum(' + detailCounter + ');"/></td>',
						'<td align="center"><label   name="detailLines-' + detailCounter +'.totalPrice"   id="detailLines-' + detailCounter + '.totalPrice"   style="width:80px;margin:0px 0px 0px 10px" class="short" readonly="readonly"></label></td>',
						
						]).draw();

				$('#detailLines-' + detailCounter + '\\.mouldId').focus();//设置新增行的基本属性
				
				autoComplete(detailCounter);//调用自动填充功能

				$('#mouldContractDetailCount').val(detailCounter);
				
				detailCounter++;
				
				iFramAutoSroll();//重设显示窗口(iframe)高度
				*/
			}
		});

$.fn.dataTable.TableTools.buttons.reset = $.extend(true, {},
	$.fn.dataTable.TableTools.buttonBase, {
	"fnClick" : function(button) {
		doDeleteDetail();
		/*
		var t = $('#MouldContractDetailList').DataTable();
		
		rowIndex = t.row('.selected').index();

		if(typeof rowIndex == "undefined"){				
			$().toastmessage('showWarningToast', "请选择要删除的数据。");	
		}else{
			
			var amount = $('#MouldContractDetailList tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
			//alert('['+amount+']:amount '+'---- totalPrice:'+totalPrice)
			
			//$().toastmessage('showWarningToast', "删除后,[ PI编号 ]可能会发生变化。");	
			t.row('.selected').remove().draw();

			//随时计算该客户的销售总价
			//totalPrice = currencyToFloat(totalPrice) - currencyToFloat(amount);			
			//$('#order\\.totalprice').val(floatToCurrency(totalPrice));
		}
		*/	
	}
});

function ajaxMouldContractRegulationList2() {

	var t = $('#MouldContractRegulationList').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
		//"scrollY"    : "160px",
        "scrollCollapse": false,
        "paging"    : false,
        "pageLength": 50,
        "ordering"  : false,

		dom : 'T<"clear">rt',

		"tableTools" : {

			"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

			"aButtons" : [ {
				"sExtends" : "add_regulationrows",
				"sButtonText" : "追加新行"
			},
			{
				"sExtends" : "reset_regulation",
				"sButtonText" : "清空一行"
			}  ],
		},
		
		"columns" : [ 
		        	{"className":"dt-body-center"
				}, {"className":"dt-body-center"
				}, {"className":"dt-body-center"
				}			
			]
		
	}).draw();

	
	t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {

	});
	
					
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

$.fn.dataTable.TableTools.buttons.add_regulationrows = $
.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
					var key = $("#keyBackup").val();
					var url = "${ctx}/business/mouldcontract?methodtype=addregulationinit&mouldId=" + key;
					openLayer(url, '', 300, false);

				/*
				var hidden = '';
				
				var lineNo =  detailCounter + 1;
				var hidden = "";
				
				hidden = '';
				
				var rowNode = $('#MouldContractRegulationList')
					.DataTable()
					.row
					.add(
					  [
						'<td align="center">' + lineNo + '</td>',
						'<td><input type="text"   name="regulationLines-' + regulationCounter + '.name" id="regulationLines-' + regulationCounter + '.name" style="width:200px;"/></td>',
						'<td><input type="text"   name="regulationLines-' + regulationCounter + '.money" id="regulationLines-' + regulationCounter + '.money" style="width:200px;"/></td>',
						
						]).draw();

				$('#regulationLines-' + regulationCounter + '\\.name').focus();//设置新增行的基本属性

				$('#regulationCount').val(regulationCounter);
				
				regulationCounter++;
				
				iFramAutoSroll();//重设显示窗口(iframe)高度
				*/
			}
		});

$.fn.dataTable.TableTools.buttons.reset_regulation = $.extend(true, {},
	$.fn.dataTable.TableTools.buttonBase, {
	"fnClick" : function(button) {
		
		doDeleteRegulation();
		/*
		var t = $('#MouldContractRegulationList').DataTable();
		
		rowIndex = t.row('.selected').index();

		if(typeof rowIndex == "undefined"){
			$().toastmessage('showWarningToast', "请选择要删除的数据。");	
		}else{
			
			//var amount = $('#reset tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
			//alert('['+amount+']:amount '+'---- totalPrice:'+totalPrice)
			
			//$().toastmessage('showWarningToast', "删除后,[ PI编号 ]可能会发生变化。");	
			t.row('.selected').remove().draw();

			//随时计算该客户的销售总价
			//totalPrice = currencyToFloat(totalPrice) - currencyToFloat(amount);			
			//$('#order\\.totalprice').val(floatToCurrency(totalPrice));
		}
		*/				
	}
});

function initEvent(){
	
	validatorBaseInfo = $("#mouldContractBaseInfo").validate({
		rules: {
			contractYear: {
				required: true,
				digits: true,
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
	
}

$(window).load(function(){
	
});

$(document).ready(function() {

	initEvent();

	ajaxMouldContractDetailList();
	ajaxMouldContractRegulationList();

	//ajaxMouldContractDetailList2();
	//ajaxMouldContractRegulationList2();
	
	if ($('#contractYear').val() == '') {
		var nowDate = new Date();
		$('#contractYear').val(nowDate.getFullYear());
	}
	
	$('#belong').val("${DisplayData.mouldContractBaseInfoData.belong}");
	
	if ($('#keyBackup').val() != '') {
		//$('#type').attr('disabled', true);
		//$('#supplierIdView').attr('disabled', true);		
	} else {
		//addRegulationTr('', '');
		//$('#type').focus();

	}

	$('#belongView').html($("#belong").find("option:selected").text());
})

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
		$('#typeDesc').html("");
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

function doEdit() {
	var url = "${ctx}/business/mouldcontract?methodtype=mouldcontractedit&key=" + $('#keyBackup').val();
	location.href = url;	
}

function doUpdateRegulation(id) {
	var url = "${ctx}/business/mouldcontract?methodtype=updateregulaltioninit&key=" + id + "&mouldId=" + $("#keyBackup").val();
	openLayer(url, '', 300, false);
}

function doDeleteRegulation(id) {
	var str = '';
	str = id;
	/*
	$("input[name='regulationnumCheck']").each(function(){
		if ($(this).prop('checked')) {
			str += $(this).val() + ",";
		}
	});
	*/
	if (str != '') {
		if(confirm("确定要删除数据吗？")) {
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : str,
				url : "${ctx}/business/mouldcontract?methodtype=deleteregulation",
				success : function(data) {
					reloadMouldContractRegulationList();
					//alert(data.message);
					
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
	                // alert(XMLHttpRequest.status);
	                //alert(XMLHttpRequest.readyState);
	                //alert(textStatus);
	             }
			});
		}
	} else {
		alert("请至少选择一条数据");
	}	
}
function doUpdateDetail(id) {
	var url = "${ctx}/business/mouldcontract?methodtype=updatedetailsinit&key=" + id + "&mouldId=" + $("#keyBackup").val();;
	openLayer(url, '', 300, false);
}
function doDeleteDetail(id) {
	var str = '';
	/*
	$("input[name='numCheck']").each(function(){
		if ($(this).prop('checked')) {
			str += $(this).val() + ",";
		}
	});
	*/
	str = id;
	if (str != '') {
		if(confirm("确定要删除数据吗？")) {
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : str,
				url : "${ctx}/business/mouldcontract?methodtype=deletedetails",
				success : function(data) {
					reloadMouldContractDetailList();
					//alert(data.message);
					
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
	                // alert(XMLHttpRequest.status);
	                //alert(XMLHttpRequest.readyState);
	                //alert(textStatus);
	             }
			});
		}
	} else {
		alert("请至少选择一条数据");
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
						$('oldContractYear').val($('contractYear').val());
						$('oldType').val($('type').val());
						$('oldSupplierIdView').val($('oldSupplierIdView').val());
						parent.reload();
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

function autoComplete(index) { 
	
	$("#detailLines-" + index + "\\.mouldId").autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "POST",
				url : "${ctx}/business/mouldcontract?methodtype=factorySearch",
				dataType : "json",
				data : {
					key : request.term,
					type : $('#type').val(),
					supplierId : $('#supplierId').val()
				},
				success : function(data) {
					response($.map(
						data.data,
						function(item) {
							//alert(item.viewList)
							return {
								label : item.viewList,
								value : item.mouldId,
								id : item.id,
								mouldFactoryId : item.mouldFactoryId,
								mouldId : item.mouldId,
								name : item.name,
								materialQuality : item.materialQuality,
								size : item.size,
								weight : item.weight,
								unloadingNum : item.unloadingNum,
								price : item.price
							}
						}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
				}
			});
		},

		select : function(event, ui) {
			$("#detailLines-" + index + "\\.name").html(ui.item.name);
			$("#detailLines-" + index + "\\.id").val(ui.item.id);
			$("#detailLines-" + index + "\\.mouldFactoryId").val(ui.item.mouldFactoryId);
			$("#detailLines-" + index + "\\.size").html(ui.item.size);
			$("#detailLines-" + index + "\\.weight").html(ui.item.weight);
			$("#detailLines-" + index + "\\.materialQuality").html(ui.item.materialQuality);
			$("#detailLines-" + index + "\\.unloadingNum").html(ui.item.unloadingNum);
			$("#detailLines-" + index + "\\.price").html(ui.item.price);
			getSum(index);
		},
		
		change : function(event, ui) {
			if (ui.item == null) {
				$("#detailLines-" + index + "\\.mouldId").val('');
				$("#detailLines-" + index + "\\.name").html('');
				$("#detailLines-" + index + "\\.id").val('');
				$("#detailLines-" + index + "\\.mouldFactoryId").val('');
				$("#detailLines-" + index + "\\.size").html('');
				$("#detailLines-" + index + "\\.weight").html('');
				$("#detailLines-" + index + "\\.materialQuality").html('');
				$("#detailLines-" + index + "\\.unloadingNum").html('');
				$("#detailLines-" + index + "\\.price").html('');
				getSum(index);
			}
		},
		
		minLength : 0,
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
				<input type=hidden id="type" name="type" value="${DisplayData.mouldContractBaseInfoData.type}"/>
				<input type=hidden id="oldType" name="oldType" value="${DisplayData.mouldContractBaseInfoData.type}"/>
				<input type=hidden id="oldContractYear" name="oldContractYear" value="${DisplayData.mouldContractBaseInfoData.contractyear}"/>
				<input type=hidden id="oldSupplierId" name="oldSupplierId" value="${DisplayData.supplierIdView}"/>
				<input type=hidden id="regulationCount" name="regulationCount" value="0"/>
				<input type=hidden id="mouldContractDetailCount" name="mouldContractDetailCount" value="0"/>
				
				<legend>模具合同-基本信息</legend>
				<div style="height:10px"></div>
				<table class="form" width="1100px" cellspacing="0">
					<tr>
						<td class="label" width="60px">编号：</td>
						<td width="130px">
							<label id="contractId" name="contractId" style="margin:0px 10px">${DisplayData.mouldContractBaseInfoData.contractid}</label>
						</td>
						<td class="label" width="80px">分类编码：	</td>
						<td width="200px">
							<label name="typeView" id="typeView">${DisplayData.mouldContractBaseInfoData.type}</label>
						</td>
						<td class="label" width="60px">模具类型：</td>
						<td width="150px">
							${DisplayData.mouldType}&nbsp;${DisplayData.typeDesc}
						</td>
						<td class="label" width="60px">年份：</td>
						<td width="130px">
							<label name="contractYear" id="contractYear">${DisplayData.mouldContractBaseInfoData.contractyear}</label>
						</td>

					</tr>
					<tr>
						<td class="label" width="50px">
							供应商ID：
						</td>
						<td width="100px">
							<label type="text" id="supplierIdView" name="supplierIdView">${DisplayData.supplierIdView}</label>
						</td>
						<td class="label" width="50px">
							名称：
						</td>
						<td width="150px"3>
							<label id="supplierName" name="supplierName" >${DisplayData.supplierName}</label>
						</td>
						<td class="label" >
							合同日期：
						</td>
						<td>
							<label id="contractDate" name="contractDate" >${DisplayData.contractDate}</label>
						</td>
						<td class="label" >
							合同交期：
						</td>
						<td>
							<label id="deliverDate" name="deliverDate" >${DisplayData.deliverDate}</label>
						</td>
					</tr>
					<tr>
						<td class="label" >
							我方费用：
						</td>
						<td>
							<label id="oursidePay" name="oursidePay" >${DisplayData.mouldContractBaseInfoData.oursidepay}</label>
						</td>
						<td class="label" width="60px">
							供方费用：
						</td>
						<td>
							<label id="providerPay" name="providerPay">${DisplayData.mouldContractBaseInfoData.providerpay}</label>
						</td>
						<td class="label" >
							模具归属：
						</td>
						<td colspan=3>
							<label id="belongView" name="belongView" style="margin:0px 10px"></label>
							<form:select path="belong" style="display:none">
								<form:options items="${DisplayData.belongList}" itemValue="key"
									itemLabel="value" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td class="label" >
							返还条件：
						</td>
						<td colspan=7>
							<label id="returnCase" name="returnCase" cols=50 rows=2>${DisplayData.mouldContractBaseInfoData.returncase}</label>
						</td>
					</tr>
				</table>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:10px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doEdit();"
						style="height:25px;margin:10px 5px 0px 0px;float:right;" >编辑</button>
				
				<div style='clear:both;'>
				<div  style="height:10px"></div>
				<legend>模具合同-增减项</legend>
				<div  style="height:10px"></div>
				<div style="margin: 0px 0px 0px 0px;width:45%" class="list">
					<table id="MouldContractRegulationList"  class="display" style="width: 100%;" style="table-layout:fixed;" >
						<thead>
							<tr>
								<th style="width: 40px;" class="dt-middle">No</th>
								<th style="width: 80px;" class="dt-middle">名称</th>
								<th style="width: 80px;" class="dt-middle">金额</th>
								<th style="width: 80px;" class="dt-middle">操作</th>
							</tr>
						</thead>
					</table>
				</div>
												
				<div style='clear:both;'>
				<div  style="height:10px"></div>
				<legend>模具详情</legend>
				<!-- 
				<button type="button" id="printmd" class="DTTT_button" onClick="doPrintContract();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >打印模具合同</button>
				 -->			
				<div style="height:10px"></div>
				<div class="list">
					<table id="MouldContractDetailList"  class="display" style="width: 100%;" style="table-layout:fixed;" >
						<thead>
							<tr>
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
					</table>
				</div>
			</form:form>
		</div>
</html>
