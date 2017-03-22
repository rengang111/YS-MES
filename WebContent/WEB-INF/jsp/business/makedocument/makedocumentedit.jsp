<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>档案制作</title>
<script type="text/javascript">

var validator;
var layerHeight = "250";
var tabHeight = "320px";

function ajaxWorkingFileList() {
	var table = $('#workingFileList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#workingFileList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/makedocument?methodtype=getWorkingFileList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#makeDocumentInfo").serializeArray();
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
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'},
						{"data" : "fileNo", "className" : 'td-center'}, 
						{"data" : "partsName", "className" : 'td-center'},
						{"data" : "erpNo", "className" : 'td-center'},
						{"data" : "material", "className" : 'td-center'},
						{"data" : "working", "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheckWorkingFile' id='numCheckWorkingFile' value='" + row["id"] + "' />"
	                    }},
						{"targets": 4, "createdCell": function (td, cellData, rowData, row, col) {
					        $(td).attr('title', cellData);
						}},
						{"targets": 5, "createdCell": function (td, cellData, rowData, row, col) {
					        $(td).attr('title', cellData);
						}},
			    		{"targets":6,"render":function(data, type, row){
			    			return "<a href=\"#\" onClick=\"doUpdateWorkingFile('" + row["id"] + "')\">查看</a>"
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

	controlButtons($('#keyBackup').val());
	
	$("#tabs").tabs();
    $('#tabs').height(tabHeight);
	$('#tabs').show();
	
}

$(window).load(function(){
	initEvent();
});

$(document).ready(function() {

	resetFinder(0, 2);
	if ($('#keyBackup').val() != "") {
		refreshFileBrowser(0);
		refreshFileBrowser(1);
	}
	
	ajaxWorkingFileList();

})

function refreshFileBrowser(id) {

	if (id == 0) {
		$("#BaseDocArea").html("");
		$("#BaseDocArea").show();
		doRefreshFileBrowser("BaseDocArea", id, "BaseDoc");
	}
	if (id == 1) {
		$("#WorkingFileArea").html("");
		$("#WorkingFileArea").show();
		doRefreshFileBrowser("WorkingFileArea", id, "WorkingFile");
	}
	
}

function reloadWorkingFileList() {

	$('#workingFileList').DataTable().ajax.reload(null,false);
	
	//reloadTabWindow();
}

function doDelete() {
	
	if (confirm("${DisplayData.endInfoMap.message}")) {
		//将提交按钮置为【不可用】状态
		//$("#submit").attr("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/business/makedocument?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					controlButtons("");
					reloadWorkingFileList();
					closeAllTabs();
					//clearProjectTaskInfo();
					//reloadTabWindow();
				}
				
				//不管成功还是失败都刷新父窗口，关闭子窗口
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();/
				//parent.reload_contactor();
				parent.layer.close(index); //执行关闭
				
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
	function controlButtons(data) {
		$('#keyBackup').val(data);
		if (data == '') {
			$('#delete').attr("disabled", true);
			$('#addWorkingFile').attr("disabled", true);
			$('#deleteWorkingFile').attr("disabled", true);
			
		} else {
			$('#delete').attr("disabled", false);
			$('#addWorkingFile').attr("disabled", false);
			$('#deleteWorkingFile').attr("disabled", false);
		}
	}
}

function doAddWorkingFile() {
	var projectId = $('#keyBackup').val();
	var url = "${ctx}/business/makedocument?methodtype=addworkingfileinit&projectId=" + projectId;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function doUpdateWorkingFile(key) {
	var projectId = $('#keyBackup').val();
	var url = "${ctx}/business/makedocument?methodtype=updateworkingfileinit&projectId=" + projectId + "&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);
}

function doDeleteWorkingFile() {
	var str = '';
	$("input[name='numCheckWorkingFile']").each(function(){
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
			url : "${ctx}/business/makedocument?methodtype=deleteworkingfile",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadWorkingFileList();
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




function doReturn() {
	//var url = "${ctx}/business/externalsample";
	//location.href = url;	
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	//parent.$('#events').DataTable().destroy();/
	//parent.reload_contactor();
	parent.layer.close(index); //执行关闭
	
}
</script>

</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
				
			<legend>档案制作-基本信息</legend>

			<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
					style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
			<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
			<table class="form" width="850px">
				<tr>
					<td width="60px">项目编号：</td>
					<td width="240px">
						${DisplayData.projectTaskData.projectid}
					</td>
					<td width="100px">项目名称：</td> 
					<td>
						${DisplayData.projectTaskData.projectname}
					</td>
					<td width="60px">暂定型号：</td> 
					<td>
						${DisplayData.projectTaskData.tempversion}
					</td>
				</tr>
				<tr>
					<td>
						项目经理：
					</td>
					<td>
						${DisplayData.projectTaskData.manager}
					</td>
					<td>
						参考原型：
					</td>
					<td colspan=4>
						${DisplayData.projectTaskData.referprototype}
					</td>
				</tr>
				<tr>
					<td>	
						起始时间：
					</td>
					<td> 
						${DisplayData.projectTaskData.begintime}
					</td>
					<td>	
						预计完成时间：
					</td>
					<td> 
						<label id="projectEndTime">${DisplayData.projectTaskData.endtime}</label>
					</td>							
					<td>
						超期：
					</td>
					<td> 
						${DisplayData.exceedTime}
					</td>
				</tr>

			</table>

			<form:form modelAttribute="dataModels" id="makeDocumentInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<input type=hidden id="tabTitle" name="tabTitle" value=""/>
				
				<div  style="height:20px"></div>
				<legend>基础技术档案</legend>
				<div  style="height:10px"></div>
				<div class="list">
					<div id="BaseDocArea" style="display:none;">
						 <%@ include file="../../common/filebrowser.jsp"%>
					</div>
				</div>
				<div  style="height:20px"></div>
				<legend>加工文件</legend>
				<button type="button" id="deleteWorkingFile" class="DTTT_button" onClick="doDeleteWorkingFile();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addWorkingFile" class="DTTT_button" onClick="doAddWorkingFile();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				<div style="height:10px"></div>
				<div class="list">
					<table id="workingFileList" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 40px;" class="dt-middle">No</th>
								<th style="width: 60px;" class="dt-middle">文件编号</th>
								<th style="width: 60px;" class="dt-middle">配件品名</th>
								<th style="width: 60px;" class="dt-middle">ERP号码</th>
								<th style="width: 180px;" class="dt-middle">材质要求</th>
								<th style="width: 180px;" class="dt-middle">加工要求</th>
								<th style="width: 40px;" class="dt-middle">操作</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				
				<div  style="height:20px"></div>
				<legend>文件夹</legend>
				<div style="height:10px"></div>
				<div class="list">
					<div id="WorkingFileArea" style="display:none;">
						 <%@ include file="../../common/filebrowser.jsp"%>
					</div>
				</div>
				</div>
			</form:form>
		</div>
</html>
