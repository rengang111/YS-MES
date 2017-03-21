<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>进程控制</title>
<script type="text/javascript">

var validator;
var layerHeight = "250";

function ajaxprocessDetailExpect() {
	var table = $('#processDetailExpect').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#processDetailExpect').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/processcontrol?methodtype=getProcessExpectCollect",
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
						{"data" : "title", "className" : 'td-center'}, 
						{"data" : "expectDate", "className" : 'td-center'}, 
						{"data" : "exceedTime", "className" : 'td-center'},
						{"data" : "lastestExpectDate", "className" : 'td-center'}, 
						{"data" : "finishTime", "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":1,"render":function(data, type, row){
			    			if (row["expectDate"] == '') {
			    				return "-";
			    			} else {
			    				return row["expectDate"];
			    			}
			    			
	                    }},
			    		{"targets":2,"render":function(data, type, row){
			    			if (row["expectDate"] == '') {
			    				return '';
			    			} else {
			    				return row["exceedTime"];
			    			}
			    			
	                    }},

			    		{"targets":5,"render":function(data, type, row){
			    			if (row["finishTime"] != "") {
			    				$('#' + "addNew" + row["type"]).attr('disabled',"true");
			    				$('#' + "addCheckPoint" + row["type"]).attr('disabled',"true");
			    			}
			    			if (row["expectDate"] != '') {
			    				setExpectDate(row["type"], row["expectDate"], row["exceedTime"], row["finishTime"])
			    				$('#addNew' + row["type"]).attr("disabled", false);
			    				$('#addCheckPoint' + row["type"]).attr("disabled", false);
			    				return "<a href=\"#\" onClick=\"doUpdateProcessDetail('" + row["id"] + "', '" + row["type"] + "')\">输入完成日期</a>"
			    			} else {
			    				$('#addNew' + row["type"]).attr("disabled", true);
			    				$('#addCheckPoint' + row["type"]).attr("disabled", true);
			    				return ""
			    			}
	                    }},
				    ]
				});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

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

function ajaxProcessDetailCheckPoint() {
	
	var table = $('#processDetailCheckPoint').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#processDetailCheckPoint').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/processcontrol?methodtype=getProcessCheckPointCollect",
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
						{"data" : "title", "className" : 'td-center'}, 
						{"data" : "createDate", "className" : 'td-center'}, 
						{"data" : "description", "className" : 'td-center'},
						{"data" : "reason", "className" : 'td-center'},
						{"data" : "expectDate", "className" : 'td-center'}, 
						{"data" : "confirm", "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
						{"targets": 2, "createdCell": function (td, cellData, rowData, row, col) {
					        $(td).attr('title', cellData);
						}},
						{"targets": 3, "createdCell": function (td, cellData, rowData, row, col) {
					        $(td).attr('title', cellData);
						}},
			    		{"targets":5,"render":function(data, type, row){
			    			if (row["confirm"] == '0') {
			    				return "未解除";
			    			} else {
			    				if (row["id"] != "") {
			    					return "已解除";
			    				} else {
			    					return "";
			    				}
			    			}
	                    }},
			    		{"targets":6,"render":function(data, type, row){
			    			if (row["id"] != "") {
			    				return "<a href=\"#\" onClick=\"doViewProcessCheckPointDetail('" + row["id"] + "', '" + row["type"] + "')\">查看</a>"
			    			} else {
			    				return "";
			    			}
	                    }},
				    ]
				});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

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

function ajaxTable0(index) {
	var table = $('#table-' + index).dataTable();
	if(table) {
		table.fnDestroy();
	}

	//$("#type").val(index);
	
	var t = $('#table-' + index).DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/processcontrol?methodtype=getProcessDetail",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#processControlInfo").serializeArray();
						formData.forEach(function(e) {
							aoData.push({"name":e.name, "value":e.value});
						});
						aoData.push({"name":"type", "value":index});
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
						{"data" : "createDate", "className" : 'td-center'}, 
						{"data" : "expectDate", "className" : 'td-center'},
						{"data" : "exceedTime", "className" : 'td-center'},
						{"data" : "reason", "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":4,"render":function(data, type, row){
			    			if (row["lastOne"] == '1') {
			    				return "<a href=\"#\" onClick=\"addNewExpect('" + row["id"] + "', '" + index + "')\">查看</a>"
			    			}
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

function ajaxTable1(index) {
	var table = $('#table-' + index).dataTable();
	if(table) {
		table.fnDestroy();
	}

	//$("#type").val(index);
	
	var t = $('#table-' + index).DataTable({
					"paging": false,
					"lengthMenu":'',//设置一页展示10条记录
					"processing" : false,
					"stateSave" : false,
					"searching" : false,
					"pagingType" : "",
					"serverSide" : true,
					"retrieve" : false,
					"sAjaxSource" : "${ctx}/business/processcontrol?methodtype=getProcessDetail",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#processControlInfo").serializeArray();
						formData.forEach(function(e) {
							aoData.push({"name":e.name, "value":e.value});
						});
						aoData.push({"name":"type", "value":index});
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
						{"data" : "confirm", "className" : 'td-center'},
						{"data" : "createDate", "className" : 'td-center'},
						{"data" : "description", "className" : 'td-center'},
						{"data" : "reason", "className" : 'td-center'},
						{"data" : "expectDate", "className" : 'td-center'},
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
			    			if (row["confirm"] == "1") {
			    				return "已解除"
			    			} else {
			    				return "未解除"
			    			}
	                    }},
						{"targets": 2, "createdCell": function (td, cellData, rowData, row, col) {
					        $(td).attr('title', cellData);
						}},
						{"targets": 3, "createdCell": function (td, cellData, rowData, row, col) {
					        $(td).attr('title', cellData);
						}},
			    		{"targets":5,"render":function(data, type, row){
			    			if (row["confirm"] == "1") {
			    				return ""
			    			} else {
			    				return "<a href=\"#\" onClick=\"addNewCheckPoint('" + row["id"] + "', '" + index + "', '" + index + "')\">查看</a>" + "&nbsp;&nbsp;" + "<a href=\"#\" onClick=\"clearCheckPoint('" + row["id"] + "', '" + index + "', '" + index + "')\">解除</a>"
			    			}
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

	ajaxprocessDetailExpect();
	
	ajaxProcessDetailCheckPoint();
	
	for(var i = 0; i < 9; i++) {
		ajaxTable0(i + '1');
		ajaxTable1(i + '2');
	}
})

function reloadTable(type) {
	$('#processDetailExpect').DataTable().ajax.reload(null,false);
	$('#processDetailCheckPoint').DataTable().ajax.reload(null,false);
	//ajaxprocessDetailExpect();
	
	if (type.length > 1) {
		$('#table-' + type).DataTable().ajax.reload(null,false);
	}
	
	reloadTabWindow();
}

function doViewProcessCheckPointDetail(id, type) {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/processcontrol?methodtype=addnewexpectinit&key=" + key + "&type=" + type + "&id=" + id + "&viewOnly=1";
	openLayer(url, $(document).width() - 25, layerHeight, false);
}

function doDelete() {
	
	if (confirm("${DisplayData.endInfoMap.message}")) {
		//将提交按钮置为【不可用】状态
		//$("#submit").attr("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/business/processcontrol?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					controlButtons("");
					//clearProjectTaskInfo();
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
				//alert(XMLHttpRequest.status);					
				//alert(XMLHttpRequest.readyState);					
				//alert(textStatus);					
				//alert(errorThrown);
			}
		});
	}
}



function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		$('#delete').attr("disabled", true);
	} else {
		$('#delete').attr("disabled", false);
	}
}

function addNewExpect(id, type) {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/processcontrol?methodtype=addnewexpectinit&key=" + key + "&type=" + type + "&id=" + id;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function addNewCheckPoint(id, type) {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/processcontrol?methodtype=addnewcheckpointinit&key=" + key + "&type=" + type + "&id=" + id;
	openLayer(url, $(document).width() - 25, 350, false);	
}

function doViewProcessCheckPointDetail(id, type) {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/processcontrol?methodtype=addnewcheckpointinit&key=" + key + "&type=" + type + "&id=" + id + "&view=1";
	openLayer(url, $(document).width() - 25, 350, false);	
}

function clearCheckPoint(id, type) {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/processcontrol?methodtype=clearcheckpoint&key=" + key + "&type=" + type + "&id=" + id;
	
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		dataType : 'json',
		url : url,
		data : $('#keyBackup').val(),// 要提交的表单
		success : function(d) {
			if (d.rtnCd != "000") {
				alert(d.message);	
			} else {
				reloadTable(type);
				
				//clearProjectTaskInfo();
				//reloadTabWindow();
			}
		}
	});
	
}


function doUpdateProcessDetail(id, type) {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/processcontrol?methodtype=addexpectcollectinit&key=" + key + "&type=" + type + "&id=" + id;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function setExpectDate(type, value1, value2, value3) {
	$('#expectFinish-' + type).html(value1);
	$('#exceedDates-' + type).html(value2);
	$('#finishDate-' + type).html(value3);

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
				
			<legend>进程控制-基本信息</legend>
			<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
			<!-- 
			<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
					style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
 			-->
			<table class="form" width="850px">
				<tr>
					<td width="60px">项目编号：</td>
					<td width="240px">
						${DisplayData.projectTaskData.projectid}
					</td>
					<td width="60px">项目名称：</td> 
					<td width="240px">
						${DisplayData.projectTaskData.projectname}
					</td>
					<td width="60px">暂定型号：</td> 
					<td width="240px">
						${DisplayData.projectTaskData.tempversion}
					</td>
					<td width="60px">
						项目经理：
					</td>
					<td>
						${DisplayData.projectTaskData.manager}
					</td>
				</tr>
				<tr>
					<td>
						参考原型：
					</td>
					<td >
						${DisplayData.projectTaskData.referprototype}
					</td>
					<td>	
						起始时间：
					</td>
					<td> 
						${DisplayData.projectTaskData.begintime}
					</td>
					<td>	
						预计完成<p>时间：
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
				<input type=hidden id="id" name="id" value=""/>
				
				<div  style="height:20px"></div>
				<legend>进程详情</legend>
				<div style="height:10px"></div>
				<div class="list">
					<table id="processDetailExpect" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">预期</th>
								<th style="width: 80px;" class="dt-middle">预期完成</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 80px;" class="dt-middle">最新预期</th>
								<th style="width: 80px;" class="dt-middle">完成日期</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="processDetailCheckPoint" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">卡点</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 30px;" class="dt-middle">问题描述</th>
								<th style="width: 80px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决时间</th>
								<th style="width: 80px;" class="dt-middle">状态</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				<div  style="height:20px"></div>
				<div class="list">
				<legend style="display:inline">3D完成</legend>
				<div style="display:inline-block;margin:0px 0px -5px;">
					<div>
						<table id='table-0' class='editableTable'>
							<tr>
								<td align="center" width="80px">预期完成</td>
								<td align="center" width="80px"><label id='expectFinish-0'></label></td>
								<td align="center" width="80px">超期天数</td>
								<td align="center" width="80px"><label id='exceedDates-0' name='exceedDates-0'></label></td>
								<td align="center" width="80px">完成日期</td>
								<td align="center" width="80px"><label id='finishDate-0'></label></td>
								<td align="center" width="80px"></td>
								
							</tr>
						</table>
					</div>
				</div>
				<div style="height:5px"></div>
				<div  style="float:right;display:inline">
					<button type="button" id="addNew0" class="DTTT_button" onClick="addNewExpect('', '01');"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建预期</button>
				</div>
				<div  style="height:10px"></div>
				<div class="list">
					<table id="table-01" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">新建日期</th>
								<th style="width: 80px;" class="dt-middle">新建预期</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 180px;" class="dt-middle">原因</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				<div style="height:10px">
				</div>				
				<button type="button" id="addCheckPoint0" class="DTTT_button" onClick="addNewCheckPoint('', '02');"
				style="height:25px;margin:0px 5px 0px 0px;float:right;" >新建卡点</button>
				
				<div style="height:30px"></div>
				<div class="list">
					<table id="table-02" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">状态</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 200px;" class="dt-middle">描述</th>
								<th style="width: 200px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决<p>时间</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				</div>
				<div  style="height:20px"></div>
				<div class="list">
				<legend style="display:inline">3D手模</legend>
				<div style="display:inline-block;margin:0px 0px -5px;">
					<div>
						<table id='table-1' class='editableTable'>
							<tr>
								<td align="center" width="80px">预期完成</td>
								<td align="center" width="80px"><label id='expectFinish-1'></label></td>
								<td align="center" width="80px">超期天数</td>
								<td align="center" width="80px"><label id='exceedDates-1' name='exceedDates-1'></label></td>
								<td align="center" width="80px">完成日期</td>
								<td align="center" width="80px"><label id='finishDate-1'></label></td>
								<td align="center" width="240px"></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="height:5px"></div>
				<div  style="float:right;display:inline">
					<button type="button" id="addNew1" class="DTTT_button" onClick="addNewExpect('', '11');"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建预期</button>
				</div>
				<div  style="height:10px"></div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="table-11" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">新建日期</th>
								<th style="width: 80px;" class="dt-middle">新建预期</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 180px;" class="dt-middle">原因</th>
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
							</tr>
						</tfoot>
					</table>	
				</div>
				<div style="height:10px">
				</div>				
				<button type="button" id="addCheckPoint1" class="DTTT_button" onClick="addNewCheckPoint('', '12');"
				style="height:25px;margin:0px 5px 0px 0px;float:right;" >新建卡点</button>
				<div style="height:30px"></div>
				<div class="list">
					<table id="table-12" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">状态</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 200px;" class="dt-middle">描述</th>
								<th style="width: 200px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决<p>时间</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				</div>		
				<div  style="height:20px"></div>
				<div class="list">
				<legend style="display:inline">3D工作样机</legend>
				<div style="display:inline-block;margin:0px 0px -5px;">
					<div>
						<table id='table-2' class='editableTable'>
							<tr>
								<td align="center" width="80px">预期完成</td>
								<td align="center" width="80px"><label id='expectFinish-2'></label></td>
								<td align="center" width="80px">超期天数</td>
								<td align="center" width="80px"><label id='exceedDates-2' name='exceedDates-2'></label></td>
								<td align="center" width="80px">完成日期</td>
								<td align="center" width="80px"><label id='finishDate-2'></label></td>
								<td align="center" width="240px"></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="height:5px"></div>
				<div  style="float:right;display:inline">
					<button type="button" id="addNew2" class="DTTT_button" onClick="addNewExpect('', '21');"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建预期</button>
				</div>
				<div  style="height:10px"></div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="table-21" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">新建日期</th>
								<th style="width: 80px;" class="dt-middle">新建预期</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 180px;" class="dt-middle">原因</th>
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
							</tr>
						</tfoot>
					</table>	
				</div>
				<div style="height:10px">
				</div>				
				<button type="button" id="addCheckPoint2" class="DTTT_button" onClick="addNewCheckPoint('', '22');"
				style="height:25px;margin:0px 5px 0px 0px;float:right;" >新建卡点</button>
				<div style="height:30px"></div>
				<div class="list">
					<table id="table-22" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">状态</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 200px;" class="dt-middle">描述</th>
								<th style="width: 200px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决<p>时间</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				</div>
				<div  style="height:20px"></div>
				<div class="list">
				<legend style="display:inline">模具确认</legend>
				<div style="display:inline-block;margin:0px 0px -5px;">
					<div>
						<table id='table-3' class='editableTable'>
							<tr>
								<td align="center" width="80px">预期完成</td>
								<td align="center" width="80px"><label id='expectFinish-3'></label></td>
								<td align="center" width="80px">超期天数</td>
								<td align="center" width="80px"><label id='exceedDates-3' name='exceedDates-3'></label></td>
								<td align="center" width="80px">完成日期</td>
								<td align="center" width="80px"><label id='finishDate-3'></label></td>
								<td align="center" width="240px"></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="height:5px"></div>
				<div  style="float:right;display:inline">
					<button type="button" id="addNew3" class="DTTT_button" onClick="addNewExpect('', '31');"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建预期</button>
				</div>
				<div  style="height:10px"></div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="table-31" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">新建日期</th>
								<th style="width: 80px;" class="dt-middle">新建预期</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 180px;" class="dt-middle">原因</th>
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
							</tr>
						</tfoot>
					</table>	
				</div>
				<div style="height:10px">
				</div>				
				<button type="button" id="addCheckPoint3" class="DTTT_button" onClick="addNewCheckPoint('', '32');"
				style="height:25px;margin:0px 5px 0px 0px;float:right;" >新建卡点</button>
				<div style="height:30px"></div>
				<div class="list">
					<table id="table-32" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">状态</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 200px;" class="dt-middle">描述</th>
								<th style="width: 200px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决<p>时间</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				</div>
				<div  style="height:20px"></div>
				<div class="list">
				<legend style="display:inline">模具完成</legend>
				<div style="display:inline-block;margin:0px 0px -5px;">
					<div>
						<table id='table-4' class='editableTable'>
							<tr>
								<td align="center" width="80px">预期完成</td>
								<td align="center" width="80px"><label id='expectFinish-4'></label></td>
								<td align="center" width="80px">超期天数</td>
								<td align="center" width="80px"><label id='exceedDates-4' name='exceedDates-4'></label></td>
								<td align="center" width="80px">完成日期</td>
								<td align="center" width="80px"><label id='finishDate-4'></label></td>
								<td align="center" width="240px"></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="height:5px"></div>
				<div  style="float:right;display:inline">				
					<button type="button" id="addNew4" class="DTTT_button" onClick="addNewExpect('', '41');"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建预期</button>
				</div>
				<div  style="height:10px"></div>
				<div style="height:10px"></div>
				<div class="list">				
					<table id="table-41" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">新建日期</th>
								<th style="width: 80px;" class="dt-middle">新建预期</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 180px;" class="dt-middle">原因</th>
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
							</tr>
						</tfoot>
					</table>	
				</div>
				<div style="height:10px">
				</div>				
				<button type="button" id="addCheckPoint4" class="DTTT_button" onClick="addNewCheckPoint('', '42');"
				style="height:25px;margin:0px 5px 0px 0px;float:right;" >新建卡点</button>
				<div style="height:30px"></div>
				<div class="list">
					<table id="table-42" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">状态</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 200px;" class="dt-middle">描述</th>
								<th style="width: 200px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决<p>时间</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				</div>
				<div  style="height:20px"></div>
				<div class="list">
				<legend style="display:inline">模具调整</legend>
				<div style="display:inline-block;margin:0px 0px -5px;">
					<div>
						<table id='table-5' class='editableTable'>
							<tr>
								<td align="center" width="80px">预期完成</td>
								<td align="center" width="80px"><label id='expectFinish-5'></label></td>
								<td align="center" width="80px">超期天数</td>
								<td align="center" width="80px"><label id='exceedDates-5' name='exceedDates-5'></label></td>
								<td align="center" width="80px">完成日期</td>
								<td align="center" width="80px"><label id='finishDate-5'></label></td>
								<td align="center" width="240px"></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="height:5px"></div>
				<div  style="float:right;display:inline">
					<button type="button" id="addNew5" class="DTTT_button" onClick="addNewExpect('', '51');"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建预期</button>
				</div>
				<div  style="height:10px"></div>
				<div style="height:10px"></div>
				<div class="list">			
					<table id="table-51" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">新建日期</th>
								<th style="width: 80px;" class="dt-middle">新建预期</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 180px;" class="dt-middle">原因</th>
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
							</tr>
						</tfoot>
					</table>	
				</div>
				<div style="height:10px">
				</div>				
				<button type="button" id="addCheckPoint5" class="DTTT_button" onClick="addNewCheckPoint('', '52');"
				style="height:25px;margin:0px 5px 0px 0px;float:right;" >新建卡点</button>
				<div style="height:30px"></div>
				<div class="list">
					<table id="table-52" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">状态</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 200px;" class="dt-middle">描述</th>
								<th style="width: 200px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决<p>时间</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				</div>
				<div  style="height:20px"></div>
				<div class="list">
				<legend style="display:inline">委外加工</legend>
				<div style="display:inline-block;margin:0px 0px -5px;">
					<div>
						<table id='table-6' class='editableTable'>
							<tr>
								<td align="center" width="80px">预期完成</td>
								<td align="center" width="80px"><label id='expectFinish-6'></label></td>
								<td align="center" width="80px">超期天数</td>
								<td align="center" width="80px"><label id='exceedDates-6' name='exceedDates-6'></label></td>
								<td align="center" width="80px">完成日期</td>
								<td align="center" width="80px"><label id='finishDate-6'></label></td>
								<td align="center" width="240px"></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="height:5px"></div>
				<div  style="float:right;display:inline">
					<button type="button" id="addNew6" class="DTTT_button" onClick="addNewExpect('', '61');"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建预期</button>
				</div>
				<div  style="height:10px"></div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="table-61" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">新建日期</th>
								<th style="width: 80px;" class="dt-middle">新建预期</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 180px;" class="dt-middle">原因</th>
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
							</tr>
						</tfoot>
					</table>	
				</div>
				<div style="height:10px">
				</div>				
				<button type="button" id="addCheckPoint6" class="DTTT_button" onClick="addNewCheckPoint('', '62');"
				style="height:25px;margin:0px 5px 0px 0px;float:right;" >新建卡点</button>
				<div style="height:30px"></div>
				<div class="list">
					<table id="table-62" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">状态</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 200px;" class="dt-middle">描述</th>
								<th style="width: 200px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决<p>时间</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				</div>
				<div  style="height:20px"></div>
				<div class="list">
				<legend style="display:inline">试产</legend>
				<div style="display:inline-block;margin:0px 0px -5px;">
					<div>
						<table id='table-7' class='editableTable'>
							<tr>
								<td align="center" width="80px">预期完成</td>
								<td align="center" width="80px"><label id='expectFinish-7'></label></td>
								<td align="center" width="80px">超期天数</td>
								<td align="center" width="80px"><label id='exceedDates-7' name='exceedDates-7'></label></td>
								<td align="center" width="80px">完成日期</td>
								<td align="center" width="80px"><label id='finishDate-7'></label></td>
								<td align="center" width="240px"></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="height:5px"></div>
				<div  style="float:right;display:inline">
					<button type="button" id="addNew7" class="DTTT_button" onClick="addNewExpect('', '71');"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建预期</button>
				</div>
				<div  style="height:10px"></div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="table-71" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">新建日期</th>
								<th style="width: 80px;" class="dt-middle">新建预期</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 180px;" class="dt-middle">原因</th>
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
							</tr>
						</tfoot>
					</table>	
				</div>
				<div style="height:10px">
				</div>				
				<button type="button" id="addCheckPoint7" class="DTTT_button" onClick="addNewCheckPoint('', '72');"
				style="height:25px;margin:0px 5px 0px 0px;float:right;" >新建卡点</button>
				<div style="height:30px"></div>
				<div class="list">				
					<table id="table-72" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">状态</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 200px;" class="dt-middle">描述</th>
								<th style="width: 200px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决<p>时间</th>
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
							</tr>
						</tfoot>
					</table>
				</div>
				</div>
				<div  style="height:20px"></div>
				<div class="list">
				<legend style="display:inline">文档整理</legend>
				<div style="display:inline-block;margin:0px 0px -5px;">
					<div>
						<table id='table-8' class='editableTable'>
							<tr>
								<td align="center" width="80px">预期完成</td>
								<td align="center" width="80px"><label id='expectFinish-8'></label></td>
								<td align="center" width="80px">超期天数</td>
								<td align="center" width="80px"><label id='exceedDates-8' name='exceedDates-8'></label></td>
								<td align="center" width="80px">完成日期</td>
								<td align="center" width="80px"><label id='finishDate-8'></label></td>
								<td align="center" width="240px"></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="height:5px"></div>
				<div  style="float:right;display:inline">
					<button type="button" id="addNew8" class="DTTT_button" onClick="addNewExpect('', '81');"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建预期</button>
				</div>
				<div  style="height:10px"></div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="table-81" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">新建日期</th>
								<th style="width: 80px;" class="dt-middle">新建预期</th>
								<th style="width: 30px;" class="dt-middle">当前超期<br>天数</th>
								<th style="width: 180px;" class="dt-middle">原因</th>
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
							</tr>
						</tfoot>
					</table>	
				</div>
				<div style="height:10px">
				</div>				
				<button type="button" id="addCheckPoint8" class="DTTT_button" onClick="addNewCheckPoint('', '82');"
				style="height:25px;margin:0px 5px 0px 0px;float:right;" >新建卡点</button>
				<div style="height:30px"></div>
				<div class="list">
					<table id="table-82" class="display" cellspacing="0" style="table-layout:fixed;">
						<thead>
							<tr class="selected">
								<th style="width: 40px;" class="dt-middle">状态</th>
								<th style="width: 80px;" class="dt-middle">发生时间</th>
								<th style="width: 200px;" class="dt-middle">描述</th>
								<th style="width: 200px;" class="dt-middle">解决方案</th>
								<th style="width: 80px;" class="dt-middle">预期解决<p>时间</th>
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
							</tr>
						</tfoot>
						</tfoot>
					</table>
				</div>
				</div>
			</form:form>
		</div>
</html>
