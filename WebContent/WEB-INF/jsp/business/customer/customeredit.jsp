<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>客户基本数据</title>
<script type="text/javascript">
var validator;
var layerHeight = "250";

function ajaxCustomerAddr() {
	var table = $('#TCustomerAddrList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TCustomerAddrList').DataTable({
					"paging": true,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"pagingType" : "full_numbers",
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/customeraddr?methodtype=search",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#customerInfo").serializeArray();
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
		        	/*
		        	dom : 'T<"clear">rt',

					"tableTools" : {

						"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

						"aButtons" : [										
								{
									"sExtends" : "create",
									"sButtonText" : "新建"
								},								
								{
									"sExtends" : "Delete",
									"sButtonText" : "删除"
								},
						]
					},
					*/
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "title", "className" : 'td-center'}, 
						{"data" : "address", "className" : 'td-center'},
						{"data" : "postcode", "className" : 'td-center'}, 
						{"data" : "memo", "className" : 'td-center'}, 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
	                    }},
			    		{"targets":5,"render":function(data, type, row){
			    			return "<a href=\"#\" onClick=\"doUpdateCustomerAddr('" + row["id"] + "')\">编辑</a>"
	                    }}
				    ] 						
				});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	/*
	t.on('dblclick', 'tr', function() {

		var d = t.row(this).data();

		
		layer.open({
			type : 2,
			title : false,
			area : [ '900px', '370px' ],
			scrollbar : false,
			title : false,
			closeBtn: 0, //不显示关闭按钮
			content : '${ctx}/business/customer/contactedit?name=' + d["name"] + '&id=' + $('#customerID').val()
		});
	});
	*/
	
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

function ajaxContact() {
	var table = $('#TContactList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TContactList').DataTable({
					"paging": true,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"pagingType" : "full_numbers",
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/contact?methodtype=contactsearch",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#customerInfo").serializeArray();
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
		        	/*
		        	dom : 'T<"clear">rt',

					"tableTools" : {

						"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

						"aButtons" : [										
								{
									"sExtends" : "create",
									"sButtonText" : "新建"
								},								
								{
									"sExtends" : "Delete",
									"sButtonText" : "删除"
								},
						]
					},
					*/
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "userName", "className" : 'td-center'}, 
						{"data" : "sex", "className" : 'td-center'},
						{"data" : "position", "className" : 'td-center'}, 
						{"data" : "mobile", "className" : 'td-center'}, 
						{"data" : "phone", "className" : 'td-center'}, 
						{"data" : "fax", "className" : 'td-center'}, 
						{"data" : "mail", "className" : 'td-center'}, 
						{"data" : "qq", "className" : 'td-center'},
						{"data" : "skype", "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
	                    }},
			    		{"targets":10,"render":function(data, type, row){
			    			return "<a href=\"#\" onClick=\"doUpdateContact('" + row["id"] + "')\">编辑</a>"
	                    }}
				    ] 						
				});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	/*
	t.on('dblclick', 'tr', function() {

		var d = t.row(this).data();

		
		layer.open({
			type : 2,
			title : false,
			area : [ '900px', '370px' ],
			scrollbar : false,
			title : false,
			closeBtn: 0, //不显示关闭按钮
			content : '${ctx}/business/customer/contactedit?name=' + d["name"] + '&id=' + $('#customerID').val()
		});
	});
	*/
	
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

$.fn.dataTable.TableTools.buttons.create = $
.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {

			}
		});
		
$.fn.dataTable.TableTools.buttons.Delete = $
.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {

			}
		});
		
function initEvent(){

	ajaxCustomerAddr();
	ajaxContact();

	controlButtons($('#keyBackup').val());
	
	$('#TContactList').DataTable().on('click', 'tr', function() {
		
		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
        	$('#TContactList').DataTable().$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});
	
	/*
	$('#TContactList').DataTable().on('dblclick', 'tr', function() {

		var d = $('#TContactList').DataTable().row(this).data();

		location.href = '${pageContext.request.contextPath}/factory/show/' + d["factory_id"] + '.html';		
		
	});
	*/
}

function reloadContact() {

	$('#TContactList').DataTable().ajax.reload(null,false);
	reloadTabWindow();

	return true;
}

function reloadCustomerAddr() {

	$('#TCustomerAddrList').DataTable().ajax.reload(null,false);
	reloadTabWindow();

	return true;
}

$(document).ready(function() {

	initEvent();
	
	validator = $("#customerInfo").validate({
		rules: {
			customerId: {
				required: true,
				minlength: 5 ,
				maxlength: 8,
			},
			customerSimpleDes: {
				required: true,				
				maxlength: 10,
			},
			customerName: {
				required: true,								
				maxlength: 50,
			},
			paymentTerm: {
				required: true,					
				maxlength: 5,
			}
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

	$("#country").val("${DisplayData.customerData.country}");
	$('#denominationCurrency').val("${DisplayData.customerData.denominationcurrency}");
	$('#shippingCase').val("${DisplayData.customerData.shippingcase}");
	$("#loadingPort").val("${DisplayData.customerData.loadingport}");
	$("#deliveryPort").val("${DisplayData.customerData.deliveryport}");
})

function doSave() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/customer?methodtype=add";				
		} else {
			//修正
			actionUrl = "${ctx}/business/customer?methodtype=update";
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
				data : JSON.stringify($('#customerInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						parent.window.frames["mainFrame"].contentWindow.reload();
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
			url : "${ctx}/business/customer?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					controlButtons("");
					clearCustomerInfo();
					reloadContact();
					reloadCustomerAddr();
					parent.window.frames["mainFrame"].contentWindow.reload();
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

function doAddContact() {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/contact?methodtype=addinit&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function doUpdateContact(key) {		
	var url = "${ctx}/business/contact?methodtype=updateinit&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);
}

function doDeleteContact() {
	var str = '';
	$("input[name='numCheck']").each(function(){
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
			url : "${ctx}/business/contact?methodtype=delete",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadContact();
					reloadCustomerAddr();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//alert(XMLHttpRequest.status);
				//alert(XMLHttpRequest.readyState);
				//alert(textStatus);
				//alert(errorThrown);
				
				//发生系统异常，请再试或者联系管理员。
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});
		
	} else {
		alert("请先选中要删除的记录。");
	}

}

function doAddCustomerAddr() {
	var url = "${ctx}/business/customeraddr?methodtype=addinit";
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function doUpdateCustomerAddr(key) {		
	var url = "${ctx}/business/customeraddr?methodtype=updateinit&key=" + key;
	openLayer(url, '', layerHeight, false);
}

function doDeleteCustomerAddr() {
	var str = '';
	$("input[name='numCheck']").each(function(){
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
			url : "${ctx}/business/customeraddr?methodtype=delete",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadContact();
					reloadCustomerAddr();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//alert(XMLHttpRequest.status);
				//alert(XMLHttpRequest.readyState);
				//alert(textStatus);
				//alert(errorThrown);
				
				//发生系统异常，请再试或者联系管理员。
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});
		
	} else {
		alert("请先选中要删除的记录。");
	}

}

function clearCustomerInfo() {
	$('#customerId').val('');
	$('#customerSimpleDes').val('');
	$('#customerName').val('');
	$('#paymentTerm').val('');
	$('#country').val('');
	$('#denominationCurrency').val('');
	$('#shippingCase').val('');
	$("#loadingPort").val('');
	$("#deliveryPort").val('');
}

function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		$('#delete').attr("disabled", true);
		$('#addcontact').attr("disabled", true);
		$('#deletecontact').attr("disabled", true);
		$('#addcustomeraddr').attr("disabled", true);
		$('#deletecustomeraddr').attr("disabled", true);
		
	} else {
		$('#delete').attr("disabled", false);
		$('#addcontact').attr("disabled", false);
		$('#addcustomeraddr').attr("disabled", false);
	}
}
</script>

</head>

<body>
<div id="container">

		<div id="main">
			<div id="customerBasic">				
				<div  style="height:20px"></div>
				
				<legend>客户-综合信息</legend>
					
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
					
				<form:form modelAttribute="dataModels" id="customerInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<table class="form" width="850px">
						<tr>
							<td>客户编号：</td>
							<td colspan=4>
								<input type="text" id="customerId" name="customerId" class="short" value="${DisplayData.customerData.customerid}"/>
							</td>
							<td width="60px">客户简称：</td> 
							<td colspan=2>
								<input type="text" id="customerSimpleDes" name="customerSimpleDes" class="short" value="${DisplayData.customerData.customersimpledes}"/>
							</td>
						</tr>
						<tr>
							<td>客户名称：</td> 
							<td colspan=4>
								<input type="text" id="customerName" name="customerName" class="middle" value="${DisplayData.customerData.customername}"/>
							</td>
							<td>
								付款条件：
							</td>
							<td>
								出运后：
								<input type="text" id="paymentTerm" name="paymentTerm" class="mini" value="${DisplayData.customerData.paymentterm}"/>&nbsp;&nbsp;天
							</td>
						</tr>
						<tr>
							<td>
								国家：
							</td>
							<td>
								<form:select path="country">
									<form:options items="${DisplayData.countryList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>
							<td>
								计价货币：
							</td>
							<td colspan=2> 
								<form:select path="denominationCurrency">
									<form:options items="${DisplayData.denominationCurrencyList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>
							<td colspan=2></td>
						</tr>
						<tr>
							<td width="60px">	
								出运条件：
							</td>
							<td width="100px"> 
								<form:select path="shippingCase">
									<form:options items="${DisplayData.shippingCaseList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>
							<td width="60px">
								出运港： 
							</td>
							<td width="100px">
								<form:select path="loadingPort">
									<form:options items="${DisplayData.portList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>
							<td width="60px">
								目的港： 
							</td>
							<td colspan=2>
								<form:select path="deliveryPort">
									<form:options items="${DisplayData.portList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>
									
						</tr>
					</table>

				</form:form>
			</div>
			
			<div  style="height:20px"></div>
				
			<div>
				<legend> 地址</legend>
				<button type="button" id="deletecustomeraddr" class="DTTT_button" onClick="doDeleteCustomerAddr();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addcustomeraddr" class="DTTT_button" onClick="doAddCustomerAddr();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				<table id="TCustomerAddrList" class="display" cellspacing="0">
					<thead>
						<tr class="selected">
							<th style="width: 80px;" class="dt-middle">No</th>
							<th style="width: 80px;" class="dt-middle">抬头</th>
							<th style="width: 30px;" class="dt-middle">地址</th>
							<th style="width: 80px;" class="dt-middle">邮编</th>
							<th style="width: 80px;" class="dt-middle">备注</th>
							<th style="width: 80px;" class="dt-middle">操作</th>
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
			
		<div  style="height:20px"></div>
			
		<div>
			<legend> 联系人</legend>
			<button type="button" id="deletecontact" class="DTTT_button" onClick="doDeleteContact();"
					style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
			<button type="button" id="addcontact" class="DTTT_button" onClick="doAddContact();"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
			<table id="TContactList" class="display" cellspacing="0">
				<thead>
					<tr class="selected">
						<th style="width: 80px;" class="dt-middle">No</th>
						<th style="width: 80px;" class="dt-middle">姓名</th>
						<th style="width: 30px;" class="dt-middle">性别</th>
						<th style="width: 80px;" class="dt-middle">职务</th>
						<th style="width: 80px;" class="dt-middle">手机</th>
						<th style="width: 80px;" class="dt-middle">电话</th>
						<th style="width: 80px;" class="dt-middle">传真</th>
						<th style="width: 80px;" class="dt-middle">邮箱</th>
						<th style="width: 80px;" class="dt-middle">QQ</th>
						<th style="width: 80px;" class="dt-middle">skype</th>
						<th style="width: 80px;" class="dt-middle">操作</th>
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
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
</html>
