<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>供应商基本数据</title>
<script type="text/javascript">
var validator;
var layerHeight = "300";

function ajax() {
	var table = $('#TContactList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TContactList').DataTable({
					//"paging": true,
					//"lengthMenu":[50],//设置一页展示10条记录
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
		        	
		        	dom : '<"clear">rt',
		        	
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

		
function initEvent(){

	ajax();
	
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

$(document).ready(function() {

	initEvent();
	
	$("#country").change(function() {

		var val = $("#country option:selected").val();

		$.ajax({
			type : "post",
			url : "${ctx}/business/supplier?methodtype=optionChange",
			async : false,
			data : 'key=' + val,
			dataType : "json",
			success : function(data) {
				var jsonObj = data;
				
				$("#province").val("");
				$("#province").html("");
				$("#city").val("");
				$("#city").html("");				
				
				for (var i = 0; i < jsonObj.length; i++) {
					$("#province").append(
						"<option value="+jsonObj[i].key+">"
						+ jsonObj[i].value
						+ "</option>");
				};

			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {
				/*
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
				alert(errorThrown);
				*/
				$("#province").val("");
				$("#province").html("");
				$("#city").val("");
				$("#city").html("");
			}
		});
	});
		
	$("#province").change(function() {

		var val = $("#province option:selected").val();
		
		$("#factoryCode").val("");
											
		if (val != "0"){ //
			$.ajax({
					type : "post",
					url : "${ctx}/business/supplier?methodtype=optionChange",
					async : false,
					data : 'key=' + val,
					dataType : "json",
					success : function(data) {
						$("#city").val("");	
						$("#city").html("");	
						var jsonObj = data;
						
						for (var i = 0; i < jsonObj.length; i++) {
							$("#city").append(
											"<option value="+jsonObj[i].key+">"
													+ jsonObj[i].value
													+ "</option>");
						};

					},
					error : function(
							XMLHttpRequest,
							textStatus,
							errorThrown) {
						//alert(XMLHttpRequest.status);
						//alert(XMLHttpRequest.readyState);
						//alert(textStatus);
						//alert(errorThrown);
						
						$("#city").html("");
					}
				});
		}else{
			//关联项目清空
			$("#city")
			.html("");
			
			$("#county")
			.html("");
			
			$("#countyCode")
			.val("");
		}
	});	
	
	

	$("#country").val("${DisplayData.supplierBasicInfoData.country}");
	$("#province").val("${DisplayData.supplierBasicInfoData.province}");
	$("#city").val("${DisplayData.supplierBasicInfoData.city}");
})

function doEdit() {
	var recodId = '${formModel.supplier.recordid}';
	var url = "${ctx}/business/supplier?methodtype=edit&key="+recodId;
	location.href = url;
}



function doBack() {
	var supplierId = "${formModel.supplier.supplierid}";
	var url = "${ctx}/business/supplier?keyBackup="+supplierId;
	location.href = url;
}

function doAddContact() {
	var supplierId = $("#supplier\\.supplierid").val();
	var url = "${ctx}/business/contact?methodtype=addinit&supplierId="+supplierId;
	openLayer(url, $(document).width() - 300, layerHeight, false);	
	
}

function doUpdateContact(key) {		
	var url = "${ctx}/business/contact?methodtype=updateinit&key=" + key;
	openLayer(url,$(document).width() - 300, layerHeight, false);
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
				reloadContact();
				//alert(data.message);
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


function controlButtons(data) {
	
}
</script>

</head>

<body>
<div id="container">

	<form:form modelAttribute="formModel" id="supplierBasicInfo" >		
		<form:hidden path="keyBackup" value="${formModel.supplier.supplierid}" />		
		<form:hidden path="supplier.recordid" />
	<fieldset>		
			<legend>供应商-综合信息</legend>
				
			<table class="form">
			<tr>
				<td class="label" width="100px">供应商编码：</td>
				<td width="150px">${formModel.supplier.supplierid}</td>
				<td class="label" width="100px">简称：</td> 
				<td width="250px">${formModel.supplier.shortname}</td>

				<td class="label" width="100px">名称：</td> 
				<td>${formModel.supplier.suppliername}</td>
			</tr>
			<tr>	
				<td class="label" width="100px">物料分类：</td> 
				<td>${formModel.supplier.categoryid}</td>
				<td class="label" width="100px">分类解释：</td> 
				<td colspan=3>${formModel.supplier.categorydes}</td>				
			</tr>
			<tr>
				<td class="label">详细地址： </td>
				<td colspan=3>${formModel.supplier.address}</td>

				<td class="label" width="100px">付款条件：</td>
				<td>入库后&nbsp;${formModel.supplier.paymentterm}&nbsp;天</td>
			</tr>
		</table>

		</fieldset>	
		<fieldset class="action" style="text-align: right;">
		<button type="button" class="DTTT_button" onclick="doEdit();">编辑</button>
		<button type="button" class="DTTT_button" onclick="doBack();">返回</button>
		</fieldset>
							
		<fieldset>		
			<legend> 联系人</legend>
			<div class="list">

			<div id="TSupplier_wrapper" class="dataTables_wrapper">
				<div id="DTTT_container" style="height:40px;align:right">
					<button type="button" id="deletecontact" class="DTTT_button" onClick="doDeleteContact();"
						style="height:25px;" >删除</button>
					<button type="button" id="addcontact" class="DTTT_button" onClick="doAddContact();"
						style="height:25px;" >新建</button>
				</div>
				<table id="TContactList" class="display" >
					<thead>
						<tr class="selected">
							<th style="width: 10px;" class="dt-middle">No</th>
							<th style="width: 80px;" class="dt-middle">姓名</th>
							<th style="width: 30px;" class="dt-middle">性别</th>
							<th style="width: 80px;" class="dt-middle">职务</th>
							<th style="width: 80px;" class="dt-middle">手机</th>
							<th style="width: 80px;" class="dt-middle">电话</th>
							<th style="width: 80px;" class="dt-middle">传真</th>
							<th style="width: 80px;" class="dt-middle">邮箱</th>
							<th style="width: 80px;" class="dt-middle">QQ</th>
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
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</tfoot>
				</table>
</div></div>
		</fieldset>
	</form:form>
	</div>
	</body>
</html>
