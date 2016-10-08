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
var layerHeight = "250";

function ajax() {
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
				maxlength: 50,
			},
			name_full: {
				maxlength: 5,
			},
			address: {
				maxlength: 50,
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
	$("#category").val("${DisplayData.organData.category}")
})

function doSaveOrgan() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/organ?methodtype=insert";				
		} else {
			//修正
			actionUrl = "${ctx}/business/organ?methodtype=update";
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
				data : JSON.stringify($('#supplierBasicInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.message != "") {
						alert(d.message);	
					}
					controlButtons(d.info);
					
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
					clearSupplierBasicInfo();
					
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
	openLayer(url, $(document).width() - 25, layerHeight, false);	
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

function clearSupplierBasicInfo() {
	$('#supplierId').val('');
	$('#supplierSimpleDes').val('');
	$('#supplierDes').val('');
	$('#twoLevelId').val('');
	$('#twoLevelIdDes').val('');
	$('#paymentTerm').val('');
	$('#address').val('');
	$('#country').val('');
	$('#province').val('');
	$('#city').val('');
	$("#province").find("option").remove();
	$("#city").find("option").remove();
}

function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		$('#delete').attr("disabled", true);
		$('#addcontact').attr("disabled", true);
		$('#deletecontact').attr("disabled", true);
		
	} else {
		$('#delete').attr("disabled", false);
		$('#addcontact').attr("disabled", false);
	}
}
</script>

</head>

<body>
<div id="container">

		<div id="main">
			<div id="supplierBasic">				
				<div  style="height:20px"></div>
				
				<legend>供应商-综合信息</legend>
					
				<button type="button" id="delete" class="DTTT_button" onClick="doDeleteOrgan();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSaveOrgan();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
					
				<form:form modelAttribute="organ" id="supplierBasicInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<table>
						<tr>
							<td width="80px">
								<label>机构类别：</label></td>
							<td colspan="3">	
								<form:select path="category">							
									<form:options items="${DisplayData.categoryList}" itemValue="key" itemLabel="value" /></form:select></td></tr>
						<tr>
							<td>
								<label>机构简称：</label></td>
							<td>
								<form:input path="name_short" class="short" value="${DisplayData.organData.name_short}"/></td>
							<td width="80px">
								<label>机构全称：</label></td>
							<td>								
								<form:input path="name_full" class="long" value="${DisplayData.organData.name_full}"/></td></tr>
						<tr>
							<td>								
								<label>详细地址：</label></td>
							<td colspan="3">								
								<form:input path="address" class="long" value="${DisplayData.organData.address}"/></td>
						</tr>	
					</table>

				</form:form>
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
						</tr>
					</tfoot>
				</table>

		</div>
	</div>
	</div>
	</body>
</html>
