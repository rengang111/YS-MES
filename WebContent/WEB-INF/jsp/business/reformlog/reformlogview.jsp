<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>变更日志</title>
<script type="text/javascript">

var validator;
var layerHeight = "250";


function ajaxReformLog() {
	var table = $('#reformLog').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#reformLog').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/reformlog?methodtype=getReformLogList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#processControlInfo").serializeArray();
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
						{"data" : "createDate", "className" : 'td-center'}, 
						{"data" : "oldFileNo", "className" : 'td-center'},
						{"data" : "newFileNo", "className" : 'td-center'},
						{"data" : "content", "className" : 'td-center'},
						{"data" : "reason", "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"]
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

}

$(window).load(function(){
	initEvent();
});

$(document).ready(function() {

	ajaxReformLog();
	
	var message = "${DisplayData.endInfoMap.message}";
	var rtnCd = "${DisplayData.endInfoMap.rtnCd}";
	if (rtnCd == "-1") {
		alert(message);
	}
})

function reloadTable() {

	$('#reformLog').DataTable().ajax.reload(null,false);
	
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
			url : "${ctx}/business/reformlog?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					controlButtons("");
					reloadTable();
					//clearProjectTaskInfo();
					//reloadTabWindow();
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
	function controlButtons(data) {
		$('#keyBackup').val(data);
		if (data == '') {
			$('#delete').attr("disabled", true);
			$('#addFormLog').attr("disabled", true);
			$('#deleteReformLog').attr("disabled", true);
			
		} else {
			$('#delete').attr("disabled", false);
			$('#addFormLog').attr("disabled", false);
		}
	}
}

function doAddReformLog() {
	var projectId = $('#keyBackup').val();
	var url = "${ctx}/business/reformlog?methodtype=addreformloginit&key=" + projectId + "&keyBackup=" + projectId;
	//openLayer(url, $(document).width() - 25, layerHeight, false);	
	$('#processControlInfo').attr("action", url);
	$('#processControlInfo').submit();
}

function doUpdateReformLog(key) {
	var projectId = $('#keyBackup').val();
	var url = "${ctx}/business/reformlog?methodtype=updatereformloginit&projectId=" + projectId + "&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);
}

function doDeleteReformLog() {
	var str = '';
	$("input[name='numCheckReformLog']").each(function(){
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
			url : "${ctx}/business/reformlog?methodtype=deletereformlog",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadTable();
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
</script>

</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
				
			<legend>后期完善-基本信息</legend>
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

			<form:form modelAttribute="dataModels" id="processControlInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				

				<div  style="height:20px"></div>
				<legend>变革日志</legend>
				<button type="button" id="addFormLog" class="DTTT_button" onClick="doAddReformLog();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >编辑</button>
				<div>
					<table id="reformLog" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 40px;" class="dt-middle">No</th>
								<th style="width: 40px;" class="dt-middle">日期</th>
								<th style="width: 80px;" class="dt-middle">原文件编号</th>
								<th style="width: 80px;" class="dt-middle">新文件编号</th>
								<th style="width: 180px;" class="dt-middle">文件内容</th>
								<th style="width: 180px;" class="dt-middle">变更原因</th>
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
