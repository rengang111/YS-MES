<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>新建卡点</title>
<script type="text/javascript">

var validator;

$(document).ready(function() {
	
	$("#createDate").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
		defaultDate : new Date(),
	}); 
	if ($("#createDate").val() == "") {
		$("#createDate").datepicker( 'setDate' , new Date() );
	}
	
	$("#expectDate").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
		defaultDate : new Date(),
	}); 
	if ($("#expectDate").val() == "") {
		$("#expectDate").datepicker( 'setDate' , new Date() );
	}
	
	addValidator();
	
	validator = $("#baseInfo").validate({
		rules: {
			createDate: {
				date: true,
				required: true,
				maxlength: 10,
			},			
			description: {
				required: true,
				maxlength: 200,
			},
			reason: {
				required: true,
				maxlength: 200,
			},
			expectDate: {
				date: true,
				required: true,
				maxlength: 10,
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

	if ('${DisplayData.isOnlyView}' == '1') {
		$('#save').attr('disabled', true);
	}
})

function doReturn() {
	
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	//parent.$('#events').DataTable().destroy();/
	//parent.reload_contactor();
	parent.layer.close(index); //执行关闭
	
}

function doSave() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		actionUrl = "${ctx}/business/processcontrol?methodtype=update";

		if (confirm(message)) {
			var actionUrl;			
			
			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#baseInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					var info = new Array();
					info = d.info.split(",");
					parent.reloadTable(info[0]);
					if (d.message != "") {
						alert(d.message);	
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();
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
}

</script>

</head>

<body>
<div id="container">

		<div id="main">
			<div id="supplierBasic">				
				<div  style="height:20px"></div>
				
				<legend>文件信息</legend>
				<button type="button" id="return" class="DTTT_button" onClick="doReturn();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >返回</button>
				<button type="button" id="save" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>

						
				<form:form modelAttribute="dataModels" id="baseInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<input type=hidden id="type" name="type" value="${DisplayData.type}"/>
					<input type=hidden id="projectId" name="projectId" value="${DisplayData.projectId}"/>
					<table class="form" width="850px">
						<tr>
							<td width="60px">发生时间：</td>
							<td width="80px">
								<input type="text" id="createDate" name="createDate" class="short" value="${DisplayData.processControlData.createdate}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">问题描述：</td>
							<td width="200px">
								<textarea id="description" name="description" cols=50 rows=4>${DisplayData.processControlData.description}</textarea>
							</td>
						</tr>
						<tr>
							<td width="60px">解决方案：</td>
							<td width="200px">
								<textarea id="reason" name="reason" cols=50 rows=4>${DisplayData.processControlData.reason}</textarea>
							</td>
						</tr>
						<tr>
							<td width="60px">预期解决时间：</td>
							<td width="80px">
								<input type="text" id="expectDate" name="expectDate" class="short" value="${DisplayData.processControlData.expectdate}"/>
							</td>
						</tr>
					</table>

				</form:form>
			</div>
		</div>
	</div>
</html>
