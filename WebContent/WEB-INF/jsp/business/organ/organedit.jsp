<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>机构管理基本数据</title>
<script type="text/javascript">
var validator;
var layerHeight = "280";

function ajax() {
	var table = $('#TContactList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TContactList').DataTable({
		"paging": false,
		//"lengthMenu":[5],//设置一页展示10条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : true,
		"sAjaxSource" : "${ctx}/business/contact?methodtype=contactsearch",
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var param = {};
			var formData = $("#supplierBasicInfo").serializeArray();
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
			{"data" : "userName", "className" : 'td-center'}, 
			{"data" : "sex", "className" : 'td-center'},
			{"data" : "position", "className" : 'td-center'}, 
			{"data" : "mobile", "className" : 'td-center'}, 
			{"data" : "phone", "className" : 'td-center'}, 
			{"data" : "fax", "className" : 'td-center'}, 
			{"data" : "mail", "className" : 'td-center'}, 
			{"data" : "qq", "className" : 'td-center'},
			{"data": null, "defaultContent" : '', "className" : 'td-center'}
		],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
				return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
                  }},
    		{"targets":9,"render":function(data, type, row){
    			return "<a href=\"#\" onClick=\"doUpdateContact('" + row["id"] + "')\">编辑</a>"
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

	ajax();

	controlButtons($('#keyBackup').val());
	
	//单行选中
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
	
	reloadTabWindow();

	return true;
}

$(document).ready(function() {

	initEvent();
	
	validator = $("#supplierBasicInfo").validate({
		rules: {
			name_short: {
				maxlength: 20,
			},
			name_full: {
				maxlength: 50,
			},
			address: {
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
	$("#type").val("${DisplayData.organData.type}")
	
	$("#return").click(
			function() {
				var url = "${ctx}/business/organ";
				location.href = url;		
			});
})

function doSaveOrgan() {

		
	var message = "${DisplayData.endInfoMap.message}";
	
	if ($('#keyBackup').val() == "") {				
		//新建
		actionUrl = "${ctx}/business/organ?methodtype=insert";				
	} else {
		//修正
		actionUrl = "${ctx}/business/organ?methodtype=update";
	}		

	var actionUrl;			

	//将提交按钮置为【不可用】状态
	//$("#submit").attr("disabled", true); 
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		dataType : 'json',
		url : actionUrl,
		data : JSON.stringify($('#supplierBasicInfo').serializeArray()),// 要提交的表单
		success : function(d) {
			if (d.message != "") {
				
			}
			alert(d.message);	
			//controlButtons(d.info);
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert(XMLHttpRequest.status);					
			//alert(XMLHttpRequest.readyState);					
			//alert(textStatus);					
			alert(errorThrown);
		}
	});
	
}

function doDeleteOrgan() {
	
	if (confirm("${DisplayData.endInfoMap.message}")) {
		//将提交按钮置为【不可用】状态
		//$("#submit").attr("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/business/organ?methodtype=delete",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.message != "") {
					alert(d.message);	
				} else {
					controlButtons("");
					
				}
				reloadContact();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
}

function doAddContact() {
	var url = "${ctx}/business/contact?methodtype=addinit";
	
	openLayer(url, $(document).width() - 250, layerHeight, false,60);	
}

function doUpdateContact(key) {		
	var url = "${ctx}/business/contact?methodtype=updateinit&key=" + key;
	openLayer(url, '', layerHeight, false);
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
			url : "${ctx}/business/contact?methodtype=deleteContact",
			success : function(d) {													
				reloadContact();
				//alert(data.message);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
				//发生系统异常，请再试或者联系管理员。
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});
		
	} else {
		alert("请先选中要删除的记录。");
	}

}


function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		//$('#delete').attr("readonly", 'true');
		$('#addcontact').attr("disabled", true);
		$('#deletecontact').attr("disabled", true);
		
	} else {
		//$('#delete').attr("disabled", false);
		$('#addcontact').attr("disabled", false);
	}
}


</script>

</head>

<body class="easyui-layout">
<div id="container">
<div id="main">
		
	<form:form modelAttribute="organ" id="supplierBasicInfo" style='padding: 0px; margin: 10px;' >
				
		<fieldset>	
			<legend>机构管理-编辑</legend>
				
				<input type="hidden" id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<table>
					<tr>
						<td width="80px">
							<label>机构类别：</label></td>
						<td colspan="3">	
							<form:select path="type">							
								<form:options items="${DisplayData.typeList}" itemValue="key" itemLabel="value" /></form:select></td></tr>
					<tr>
						<td>
							<label>机构简称：</label></td>
						<td>
							<form:input path="shortName" class="short" value="${DisplayData.organData.shortname}"/></td>
						<td width="80px">
							<label>机构全称：</label></td>
						<td>								
							<form:input path="fullName" class="long" value="${DisplayData.organData.fullname}"/></td></tr>
					<tr>
						<td>								
							<label>详细地址：</label></td>
						<td colspan="3">								
							<form:input path="address" class="long" value="${DisplayData.organData.address}"/></td>
					</tr>	
				</table>
	
		</fieldset>
		
		<div style="clear: both"></div>			
		<fieldset class="action" style="text-align: right;">					
			<button type="button" id="return" class="DTTT_button">返回</button>
			<!-- button type="button" id="delete" class="DTTT_button" onclick="doDeleteOrgan();"
					style="height:25px;">删除</button -->
			<button type="button" id="edit" class="DTTT_button" onclick="doSaveOrgan();"
					style="height:25px;" >保存</button>
		
		</fieldset>
		<div style="clear: both"></div>		
				
		<fieldset style="padding-right: 3px;">
			<legend> 机构联系人</legend>
			<div class="list">
			<div class="dataTables_wrapper">
			<div id="DTTT_container" style="height:40px;align:right">
			<button type="button" id="addcontact" class="DTTT_button" onclick="doAddContact();"
					style="height:25px;" >新建</button>
			<button type="button" id="deletecontact" class="DTTT_button" onclick="doDeleteContact();"
					style="height:25px;" >删除</button>
			</div>
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
			</div>
		</fieldset>
	</form:form>
</div>
</div>
</body>
</html>
