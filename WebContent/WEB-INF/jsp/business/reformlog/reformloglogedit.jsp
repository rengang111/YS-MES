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

$(document).ready(function() {
	
	var d = new Date();
	if ($('#createDate').val() == "") {
		$('#createDate').val(d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate());
	}
	
	validator = $("#baseInfo").validate({
		rules: {
			createDate: {
				date: true,
				required: true,
				maxlength: 10,
			},
			oldFileNo: {
				required: true,
				maxlength:50,
			},		
			newFileNo: {
				required: true,
				maxlength:50,
			},	
			content: {
				required: true,
				maxlength: 20,
			},
			reason: {
				maxlength: 20,
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


function doSave() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		actionUrl = "${ctx}/business/reformlog?methodtype=updatereformlog";

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
					parent.reloadTable();

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

function doDelete() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		actionUrl = "${ctx}/business/reformlog?methodtype=deletereformlog";

		if (confirm(message)) {
			var actionUrl;			
			
			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : $('#keyBackup').val(),// 要提交的表单
				success : function(d) {
					parent.reloadTable();

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
				
				<legend>预期信息</legend>
				<button type="button" id="save" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >删除</button>				
				<button type="button" id="save" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
					
				<form:form modelAttribute="dataModels" id="baseInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<input type=hidden id="projectId" name="projectId" value="${DisplayData.projectId}"/>
					<table class="form" width="850px">
						<tr>
							<td width="60px">日期：</td>
							<td width="80px">
								<input type="text" id="createDate" name="createDate" class="short" value="${DisplayData.reformlogData.createdate}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">原文件编号：</td>
							<td width="80px">
								<input type="text" id="oldFileNo" name="oldFileNo" class="short" class="long" value="${DisplayData.reformlogData.oldfileno}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">新文件编号：</td>
							<td width="80px">
								<input type="text" id="newFileNo" name="newFileNo" class="short" class="long" value="${DisplayData.reformlogData.newfileno}"/>
							</td>
						</tr>									
						<tr>
							<td width="60px">文件内容：</td>
							<td width="80px">
								<textarea id="content" name="content" class="short" row=5 cols=100 >${DisplayData.reformlogData.content}</textarea>
							</td>
						</tr>
						<tr>
							<td width="60px">变更原因：</td>
							<td width="80px">
								<textarea id="reason" name="reason" class="short" row=5 cols=100>${DisplayData.reformlogData.reason}</textarea>
							</td>
						</tr>
	
					</table>

				</form:form>
			</div>
		</div>
	</div>
</html>
