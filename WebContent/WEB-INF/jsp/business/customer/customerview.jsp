<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>客户基本数据</title>
<script type="text/javascript">
var validator;
var layerHeight = "280";

function ajaxCustomerAddr() {
	var table = $('#TCustomerAddrList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TCustomerAddrList').DataTable({
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
        "ordering"  : false,

		dom : '<"clear">lt',
		
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
       	
       	dom : '<"clear">rt',
		"columns" : [ 
			{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
			{"data" : "title"}, 
			{"data" : "address" },
			{"data" : "postcode"}, 
			{"data" : "memo"}, 
			{"data": null, "defaultContent" : '', "className" : 'td-center'}
		],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
				return row["rownum"] + "<input type=checkbox name='numCheckAddr' id='numCheckAddr' value='" + row["id"] + "' />"
                  }},
    		{"targets":5,"render":function(data, type, row){
    			return "<a href=\"#\" onClick=\"doUpdateCustomerAddr('" + row["id"] + "')\">编辑</a>"
                  }}
	    ] 						
	});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

};

function ajaxContact() {
	var table = $('#TContactList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TContactList').DataTable({
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
        "ordering"  : false,
		"sAjaxSource" : "${ctx}/business/contact?methodtype=contactsearch",
		dom : '<"clear">lt',
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
       	
       	dom : '<"clear">rt',

		"columns" : [ 
			{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
			{"data" : "userName"}, 
			{"data" : "sex"},
			{"data" : "position"}, 
			{"data" : "mobile"}, 
			{"data" : "phone"}, 
			{"data" : "fax"}, 
			{"data" : "mail"}, 
			{"data" : "qq"},
			{"data" : "skype"},
			{"data": null, "defaultContent" : '', "className" : 'td-center'}
		],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
				return row["rownum"] + "<input type=checkbox name='numCheckContact' id='numCheckContact' value='" + row["id"] + "' />"
                  }},
    		{"targets":10,"render":function(data, type, row){
    			return "<a href=\"#\" onClick=\"doUpdateContact('" + row["id"] + "')\">编辑</a>"
                  }}
	    ] 						
	});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});
	
};
		
function initEvent(){

	ajaxCustomerAddr();
	ajaxContact();

	
	$('#TContactList').DataTable().on('click', 'tr', function() {
		
		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
        	$('#TContactList').DataTable().$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});
}

function reloadContact() {

	$('#TContactList').DataTable().ajax.reload(null,false);
	//reloadTabWindow();

	return true;
}

function reloadCustomerAddr() {

	$('#TCustomerAddrList').DataTable().ajax.reload(null,false);
	// reloadTabWindow();

	return true;
}

$(document).ready(function() {

	$('select').css('width','100px');
	
	initEvent();
	
	validator = $("#customerInfo").validate({
		rules: {
			customerId: {
				required: true,
				minlength: 5 ,
				maxlength: 20,
			},
			customerSimpleDes: {
				required: true,				
				maxlength: 20,
			},
			customerName: {
				required: true,								
				maxlength: 100,
			},
			paymentTerm: {
				required: true,					
				maxlength: 15,
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

})


function doAddContact() {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/contact?methodtype=addinit&key=" + key;
	//openLayer(url, $(document).width() - 50, layerHeight, false);	
	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '900px', layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
	});
}

function doUpdateContact(key) {		
	var url = "${ctx}/business/contact?methodtype=updateinit&key=" + key;
	openLayer(url, $(document).width() - 50, layerHeight, false);
}

function doDeleteContact() {
	var str = '';
	$("input[name='numCheckContact']").each(function(){
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
	//openLayer(url,'', layerHeight, false);	
	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '900px', layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
	});
}

function doUpdateCustomerAddr(key) {		
	var url = "${ctx}/business/customeraddr?methodtype=updateinit&key=" + key;
	openLayer(url, '', layerHeight, false);
}

function doDeleteCustomerAddr() {
	var str = '';
	$("input[name='numCheckAddr']").each(function(){
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

function doEdit() {
	var recodId = '${customer.recordId }';
	var url = "${ctx}/business/customer?methodtype=edit&key="+recodId;
	location.href = url;
}

function goBack() {

	var url = "${ctx}/business/customer";
	location.href = url;
}

</script>

</head>

<body>
<div id="container">
<div id="main">				
	<form:form modelAttribute="formModel" id="customerInfo"  >
	
		<input type="hidden" id="keyBackup" name="keyBackup" value="${formModel.keyBackup}"/>
		<fieldset>		
			<legend>客户-综合信息</legend>
				
				<table class="form">
					<tr>
						<td class="label" style="widht:150px">客户编号：</td>
						<td style="widht:120px">${customer.customerId }
							<form:hidden path="customer.recordid" value="${customer.recordId }"/></td>
						<td class="label" style="widht:150px">客户简称：</td> 
						<td>${customer.shortName }</td>
					
						<td class="label" style="widht:150px">客户名称：</td> 
						<td>${customer.customerName }</td>
					</tr>
					<tr><td class="label" >所在国家：</td>
						<td>${customer.country }</td>
						<td class="label" >计价货币：</td>
						<td> ${customer.currency }</td>
						<td class="label" >付款条件：</td>
						<td>出运后&nbsp;${customer.paymentTerm }&nbsp;天</td>
						
						
					</tr>
					<tr>
						<td class="label" >出运条件：</td>
						<td> ${customer.shippingCondition }</td>
						<td  class="label" >出运港：</td>
						<td>${customer.shippiingPort }</td>
						<td  class="label" >目的港： </td>
						<td>${customer.destinationPort }</td>
								
					</tr>
				</table>

		</fieldset>				
		<fieldset class="action" style="text-align: right;">
			<button type="button" class="DTTT_button" onclick="doEdit();">编辑</button>
			<button type="button" class="DTTT_button" onclick="goBack();">返回</button>
		</fieldset>
						
		<fieldset>	
			<legend> 地址</legend>
			<div class="list">
			<button type="button" style="margin-top: -50px;margin-left: 80px;" class="DTTT_button" onClick="doDeleteCustomerAddr();"
					>删除</button>
			<button type="button" style="margin-top: -50px;" class="DTTT_button" onClick="doAddCustomerAddr();"
				 >新建</button>
			<table id="TCustomerAddrList" class="display" >
				<thead>
					<tr class="selected">
						<th style="width: 10px;" class="dt-middle">No</th>
						<th style="width: 150px;" class="dt-middle">抬头</th>
						<th class="dt-middle">地址</th>
						<th style="width: 80px;" class="dt-middle">邮编</th>
						<th style="width: 200px;" class="dt-middle">备注</th>
						<th style="width: 30px;" class="dt-middle">操作</th>
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
	</fieldset>
			
		<fieldset>
			<legend> 联系人</legend>
			<div class="list">
			<button type="button" style="margin-top: -50px;margin-left: 80px;" class="DTTT_button" onClick="doDeleteContact();">删除</button>
			<button type="button" style="margin-top: -50px;" class="DTTT_button" onClick="doAddContact();">新建</button>
			<table id="TContactList" class="display">
				<thead>
					<tr class="selected">
						<th style="width: 10px;" class="dt-middle">No</th>
						<th style="width: 80px;" class="dt-middle">姓名</th>
						<th style="width: 10px;" class="dt-middle">性别</th>
						<th style="width: 50px;" class="dt-middle">职务</th>
						<th style="width: 80px;" class="dt-middle">手机</th>
						<th style="width: 80px;" class="dt-middle">电话</th>
						<th style="width: 80px;" class="dt-middle">传真</th>
						<th style="width: 80px;" class="dt-middle">邮箱</th>
						<th style="width: 50px;" class="dt-middle">QQ</th>
						<th style="width: 80px;" class="dt-middle">skype</th>
						<th style="width: 10px;" class="dt-middle">操作</th>
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
		</fieldset>
	</form:form>
</div>
</div>
	
</body>
</html>
