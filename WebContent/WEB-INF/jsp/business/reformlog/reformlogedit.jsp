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
var counter = 0;

function ajaxReformLog() {
	var table = $('#reformLog').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#reformLog').DataTable({
					"processing" : false,
					"retrieve"   : true,
					"stateSave"  : true,
					"pagingType" : "full_numbers",
					//"scrollY"    : "160px",
			        "scrollCollapse": false,
			        "paging"    : false,
			        "pageLength": 50,
			        "ordering"  : false,
					/*
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
								fnCallback(data);
							},
							 error:function(XMLHttpRequest, textStatus, errorThrown){
				                 //alert(XMLHttpRequest.status);
				                 //alert(XMLHttpRequest.readyState);
				                 //alert(textStatus);
				             }
						})
					},
					*/	
					"language": {
		        		"url":"${ctx}/plugins/datatables/chinese.json"
		        	},
		        	dom : 'T<"clear">rt',

					"tableTools" : {

						"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

						"aButtons" : [										
								{
									"sExtends" : "add_rows",
									"sButtonText" : "追加新行"
								},								
								{
									"sExtends" : "reset",
									"sButtonText" : "清空一行"
								},
						]
					},
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'},
						{}, 
						{},
						{},
						{},
						{}
					],
					"columnDefs":[

				    ] 						
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

	
	//Form序列化后转为AJAX可提交的JSON格式。
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	
	$.fn.dataTable.TableTools.buttons.add_rows = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {

					var rowIndex = counter++;

					var rowNode = $('#reformLog').DataTable().row.add([
           					'<td class="dt-center"></td>',
        					'<td><input type="text" id="detailLines[' + rowIndex + '].createdate" name="detailLines[' + rowIndex + '].createdate" class="short" /></td>',
        					'<td><input type="text" id="detailLines[' + rowIndex + '].oldfileno" name="detailLines[' + rowIndex + '].oldfileno" class="short"/></td>',
        					'<td><input type="text" id="detailLines[' + rowIndex + '].newfileno" name="detailLines[' + rowIndex + '].newfileno" class="short"/></td>',
        					'<td class="dt-center"><textarea id="detailLines[' + rowIndex + '].content" name="detailLines[' + rowIndex + '].content" class="short" row=5 cols=30 ></textarea></td>',
        					'<td class="dt-center"><textarea id="detailLines[' + rowIndex + '].reason" name="detailLines[' + rowIndex + '].reason" class="short" row=5 cols=30></textarea></td>',
					]).draw();
					
					addRules(rowIndex);					
					//foucsInit();//设置新增行的基本属性
					
					//autocomplete();//调用自动填充功能
					
					//iFramAutoSroll();//重设显示窗口(iframe)高度
				}
			});

	$.fn.dataTable.TableTools.buttons.reset = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {

			var t=$('#reformLog').DataTable();
			
			rowIndex = t.row('.selected').index();

			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			}else{
				
				var amount = $('#reformLog tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				//alert('['+amount+']:amount '+'---- totalPrice:'+totalPrice)
				
				//$().toastmessage('showWarningToast', "删除后,[ PI编号 ]可能会发生变化。");	
				t.row('.selected').remove().draw();

			}
						
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

	validator = $("#processControlInfo").validate({
		rules: {			
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
	
	ajaxReformLog();

	counter = parseInt("${detailCount}");
	for(var i = 0; i < counter; i++) {
		addRules(i);
	}
})

function addRules(index) {
	$('#detailLines\\[' + index + '\\]\\.createdate').rules('add', { date: true, required: true, maxlength: 10 });
	$('#detailLines\\[' + index + '\\]\\.oldfileno').rules('add', { required: true, maxlength:50 });
	$('#detailLines\\[' + index + '\\]\\.newfileno').rules('add', { required: true, maxlength:50 });
	$('#detailLines\\[' + index + '\\]\\.content').rules('add', {  required: true, maxlength:20 });
	$('#detailLines\\[' + index + '\\]\\.reason').rules('add', { maxlength: 20 });
}

function reloadTable() {

	$('#reformLog').DataTable().ajax.reload(null,false);
	
	//reloadTabWindow();
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
	var url = "${ctx}/business/reformlog?methodtype=addreformloginit&projectId=" + projectId;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function doUpdateReformLog() {

	if (validator.form()) {
		if (confirm("您确认执行该操作吗？") == false) {
			return;
		}
		$('#processControlInfo').attr("action", "${ctx}/business/reformlog?methodtype=updatereformlog");
		$('#processControlInfo').submit();
	}
	/*
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
	*/

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
				
			<legend>变更日志-基本信息</legend>
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

			<form:form modelAttribute="dataModels" id="processControlInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				

				<div  style="height:20px"></div>
				<legend>变更日志</legend>

				<button type="button" id="addFormLog" class="DTTT_button" onClick="doUpdateReformLog();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<div style="height:10px"></div>
				<div class="list">
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
						<tbody>
							<c:forEach var='reformlogData' items='${detail.data}' varStatus='status'>
								<tr>				
									<td></td>
									<td><input type="text" id="detailLines[${status.index}].createdate" name="detailLines[${status.index}].createdate" class="short" value='${reformlogData.createDate}'/></td>
									<td><input type="text" id="detailLines[${status.index}].oldfileno" name="detailLines[${status.index}].oldfileno" class="short" value='${reformlogData.oldFileNo}'/></td>
									<td><input type="text" id="detailLines[${status.index}].newfileno" name="detailLines[${status.index}].newfileno" class="short" value='${reformlogData.newFileNo}'/></td>
									<td><textarea id="detailLines[${status.index}].content" name="detailLines[${status.index}].content" class="short" row=5 cols=30 >${reformlogData.content}</textarea></td>
									<td><textarea id="detailLines[${status.index}].reason" name="detailLines[${status.index}].reason" class="short" row=5 cols=30>${reformlogData.reason}</textarea></td>									
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</form:form>
		</div>
</html>
