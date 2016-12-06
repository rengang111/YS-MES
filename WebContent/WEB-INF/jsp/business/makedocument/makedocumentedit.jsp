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

function ajaxBaseTechDoc(index) {
	var table = $('#BaseTechDoc-' + index).dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#BaseTechDoc-' + index).DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/makedocument?methodtype=getBaseTechDoc",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#makeDocumentInfo").serializeArray();
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
						{"data": null, "defaultContent" : '', "className" : 'td-center'},
						{"data" : "title", "className" : 'td-center'},
						{"data" : "fileName", "className" : 'td-center'}, 
						{"data" : "path", "className" : 'td-center'},
						{"data" : "memo", "className" : 'td-center'}, 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheckBaseTechDoc-" + row["type"] + "' id='numCheckBaseTechDoc-" + row["type"] + "' value='" + row["id"] + "' />"
	                    }},
			    		{"targets":5,"render":function(data, type, row){
			    			return "<a href=\"#\" onClick=\"doUpdateBaseTechDoc('" + row["id"] + "')\">编辑</a>"
	                    }}
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
			    		{"targets":6,"render":function(data, type, row){
			    			return "<a href=\"#\" onClick=\"doUpdateWorkingFile('" + row["id"] + "')\">编辑</a>"
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

	var folderNames = '${DisplayData.folderNames}';
	if (folderNames.length > 0) {
		var folderNameList = folderNames.split(",");
		for(var i = 0; i < folderNameList.length; i++) {
			addTab(folderNameList[i]);
		}
	}
	
	$("#tabs").tabs();
    $('#tabs').height(tabHeight);
	$('#tabs').show();
	
}

$(window).load(function(){
	initEvent();
});

$(document).ready(function() {

	ajaxBaseTechDoc(1);
	ajaxBaseTechDoc(2);
	ajaxBaseTechDoc(3);
	ajaxWorkingFileList();
})

function reloadBaseTechDoc(index) {
	$('#BaseTechDoc-' + index).DataTable().ajax.reload(null,false);
	
	//reloadTabWindow();
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
					reloadBaseTechDoc(1);
					reloadBaseTechDoc(2);
					reloadBaseTechDoc(3);
					reloadWorkingFileList();
					closeAllTabs();
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
			for(var i = 1; i <=3; i++) {
				$('#addBaseTechDoc-' + i).attr("disabled", true);
				$('#deleteBaseTechDoc-' + i).attr("disabled", true);
			}
			$('#addWorkingFile').attr("disabled", true);
			$('#deleteWorkingFile').attr("disabled", true);
			
		} else {
			$('#delete').attr("disabled", false);
			for(var i = 1; i <=3; i++) {
				$('#addBaseTechDoc-' + i).attr("disabled", false);
				$('#deleteBaseTechDoc-' + i).attr("disabled", false);
			}
			$('#addWorkingFile').attr("disabled", false);
			$('#deleteWorkingFile').attr("disabled", false);
		}
	}
}

function doAddBaseTechDoc(type) {
	var projectId = $('#keyBackup').val();
	var url = "${ctx}/business/makedocument?methodtype=addbasetechdocinit&projectId=" + projectId + "&type=" + type;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function doUpdateBaseTechDoc(key) {
	var projectId = $('#keyBackup').val();
	var url = "${ctx}/business/makedocument?methodtype=updatebasetechdocinit&projectId=" + projectId + "&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);
}

function doDeleteBaseTechDoc(type) {
	var str = '';
	$("input[name='numCheckBaseTechDoc-'" + type + "]").each(function(){
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
			url : "${ctx}/business/makedocument?methodtype=deletebasetechdoc",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadBaseTechDoc(d.info);
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

function openTabEdit(type) {
	var projectId = $('#keyBackup').val();
	var url = "";
	
	if(type == 1) {
		url = "${ctx}/business/makedocument?methodtype=editfoldername&projectId=" + projectId;
	}
	if (type == 2) {
		var $tabs = $('#tabs');
		var curTab = $tabs.tabs('getSelected');
		var folderName = curTab.panel('options').title;
		url = "${ctx}/business/makedocument?methodtype=editfoldername&projectId=" + projectId + "&folderName=" + folderName;
	}
	openLayer(url, $(document).width() - 25, layerHeight, false);
}

function addTab(title) {
	var projectId = $('#keyBackup').val();
    var $tabs = $('#tabs');
	var icon;

    if($tabs.tabs('exists', title)){//存在，则打开
        $tabs.tabs('select', title);
    }else{//不存在，新建,新建时判断tab页个数，超出则关闭第一个
        var content = '<iframe scrolling="auto" id="submainFrame" frameborder="0" src="' + "${ctx}/business/makedocument?methodtype=showtab&projectId=" + projectId + "&title=" + title + '" width="100%" height="270px""></iframe>';

        var tabCon = { title:title,  content:content, closable:true, selected:true, iconCls:icon };
        /*
        if($('.tabs-inner').length > 8){//最多打开8个（不包括首页）
            $.messager.confirm('提示', '菜单页打开过多，是否关闭第一个，并打开“'+title+'”？', function(r){
                if (r){//关闭第一页，并打开当前页
                    $('#tabs').tabs('close',1);
                    $tabs.tabs('add',tabCon);
                    tabsRightClickAction();//绑定双击tab事件和右键菜单事件。
                }
            });
        }else{
            $tabs.tabs('add',tabCon);
        }
        */
        $tabs.tabs('add',tabCon);
    }
    $('#tabs').height(tabHeight);
    $('#tabs').show();
}

function editTab(title) {
	var projectId = $('#keyBackup').val();
    var $tabs = $('#tabs');
    var curTab = $tabs.tabs('getSelected');
    if($tabs.tabs('exists', title)){//存在，则打开
        $tabs.tabs('select', title);
    } else {
    	var content = '<iframe scrolling="auto" id="submainFrame" frameborder="0" src="' + "${ctx}/business/makedocument?methodtype=showtab&projectId=" + projectId + "&title=" + title + '" width="100%" height="270px""></iframe>';
    	$(tabs).tabs('update', {tab:curTab,options:{title:title, content:content}});
    }

}

function removeTab() {
	var $tabs = $('#tabs');
	var curTab = $tabs.tabs('getSelected');
	var index = $(tabs).tabs('getTabIndex', curTab);
	var str = curTab.panel('options').title;
	
	//alert(curTab.panel('options').title);
	//$(tabs).tabs('update', {tab:curTab,options:{title:'pppp'}});
	if (confirm("您确认执行该操作吗？")) {

		$('#tabTitle').val(str);
		$.ajax({
			contentType : 'application/json',
			dataType : 'json',						
			type : "POST",
			data : JSON.stringify($('#makeDocumentInfo').serializeArray()),// 要提交的表单						
			url : "${ctx}/business/makedocument?methodtype=deletefolder",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					$('#tabs').tabs('close', index);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});		
		
	}
	
}

function closeAllTabs() {
	var tiles = new Array();  
	var tabs = $('#tabs').tabs('tabs');      
	var len =  tabs.length;           
	if(len > 0) {  
	    for(var j=0; j<len; j++){  
	        var a = tabs[j].panel('options').title;               
	        tiles.push(a);  
	    }  
	    for(var i=0; i<tiles.length; i++){               
	        $('#tabs').tabs('close', tiles[i]);  
	    }
	    
		$("#tabs").tabs();
	    $('#tabs').height(tabHeight);
		$('#tabs').show();

	}
}
</script>

</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
				
			<legend>后期完善-基本信息</legend>

			<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
					style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
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
				<legend style="margin:0px 0px 0px 35px;">3D结构</legend>
				<button type="button" id="deleteBaseTechDoc-1" class="DTTT_button" onClick="doDeleteBaseTechDoc(1);"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addBaseTechDoc-1" class="DTTT_button" onClick="doAddBaseTechDoc(1);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				<table id="BaseTechDoc-1" class="display" cellspacing="0">
					<thead>
						<tr class="selected">
							<th style="width: 40px;" class="dt-middle">No</th>
							<th style="width: 80px;" class="dt-middle">标题</th>
							<th style="width: 80px;" class="dt-middle">文件名</th>
							<th style="width: 80px;" class="dt-middle">路径</th>
							<th style="width: 180px;" class="dt-middle">说明</th>
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

				<div  style="height:20px"></div>
				<legend style="margin:0px 0px 0px 35px;">爆炸图</legend>
				<button type="button" id="deleteBaseTechDoc-2" class="DTTT_button" onClick="doDeleteBaseTechDoc(2);"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addBaseTechDoc-2" class="DTTT_button" onClick="doAddBaseTechDoc(2);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				<table id="BaseTechDoc-2" class="display" cellspacing="0">
					<thead>
						<tr class="selected">
							<th style="width: 40px;" class="dt-middle">No</th>
							<th style="width: 80px;" class="dt-middle">标题</th>
							<th style="width: 80px;" class="dt-middle">文件名</th>
							<th style="width: 80px;" class="dt-middle">路径</th>
							<th style="width: 180px;" class="dt-middle">说明</th>
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
				
				<div  style="height:20px"></div>
				<legend style="margin:0px 0px 0px 35px;">BOM表</legend>
				<button type="button" id="deleteBaseTechDoc-3" class="DTTT_button" onClick="doDeleteBaseTechDoc(3);"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addBaseTechDoc-3" class="DTTT_button" onClick="doAddBaseTechDoc(3);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				<table id="BaseTechDoc-3" class="display" cellspacing="0">
					<thead>
						<tr class="selected">
							<th style="width: 40px;" class="dt-middle">No</th>
							<th style="width: 80px;" class="dt-middle">标题</th>
							<th style="width: 80px;" class="dt-middle">文件名</th>
							<th style="width: 80px;" class="dt-middle">路径</th>
							<th style="width: 180px;" class="dt-middle">说明</th>
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

				<div  style="height:20px"></div>
				<legend>加工文件</legend>
				<button type="button" id="deleteWorkingFile" class="DTTT_button" onClick="doDeleteWorkingFile();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addWorkingFile" class="DTTT_button" onClick="doAddWorkingFile();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				<div>
					<table id="workingFileList" class="display" cellspacing="0">
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
				<button type="button" id="deleteFolder" class="DTTT_button" onClick="removeTab();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="editFolder" class="DTTT_button" onClick="openTabEdit(2);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >修改</button>
				<button type="button" id="addFolder" class="DTTT_button" onClick="openTabEdit(1);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				<div id="tabs" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true" style="height:320px;margin:10px 0px 0px 15px;padding:0px;display:none;">

				</div>
			</form:form>
		</div>
</html>
