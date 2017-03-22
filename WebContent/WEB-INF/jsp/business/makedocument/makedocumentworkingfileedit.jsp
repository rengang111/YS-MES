<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>加工文件</title>
<script type="text/javascript">

var validator;

$(document).ready(function() {
	
	validator = $("#baseInfo").validate({
		rules: {
			fileNo: {
				required: true,
				maxlength: 50,
			},
			partsName: {
				required: true,
				maxlength:50,
			},		
			erpNo: {
				required: true,
				maxlength:50,
			},	
			material: {
				required: true,
				maxlength: 50,
			},
			working: {
				maxlength: 50,
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
		
		actionUrl = "${ctx}/business/makedocument?methodtype=updateworkingfile";

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
					parent.reloadWorkingFileList();

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
		
		actionUrl = "${ctx}/business/makedocument?methodtype=deleteworkingfile";

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
					parent.reloadQuestionList();

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
				
				<legend>加工文件</legend>
				<button type="button" id="save" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >删除</button>				
				<button type="button" id="save" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
					
				<form:form modelAttribute="dataModels" id="baseInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<input type=hidden id="projectId" name="projectId" value="${DisplayData.projectId}"/>
					<table class="form" width="850px">
						<tr>
							<td width="60px">文件编号：</td>
							<td width="80px">
								<input type="text" id="fileNo" name="fileNo" class="short" value="${DisplayData.workingFilesData.fileno}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">配件品名：</td>
							<td width="80px">
								<input type="text" id="partsName" name="partsName" rows=5 cols=50 class="short" value="${DisplayData.workingFilesData.partsname}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">ERP号码：</td>
							<td width="80px">
								<input type="text" id="erpNo" name="erpNo" rows=5 cols=120 class="short" value="${DisplayData.workingFilesData.erpno}"/>
							</td>
						</tr>									
						<tr>
							<td width="60px">材质要求</td>
							<td width="80px">
								<textarea id="material" name="material" cols=50 rows=2>${DisplayData.workingFilesData.material}</textarea>
							</td>
						</tr>
						<tr>
							<td width="60px">加工要求：</td>
							<td width="80px">
								<textarea id="working" name="working" cols=50 rows=2>${DisplayData.workingFilesData.working}</textarea>
							</td>
						</tr>
	
					</table>

				</form:form>
			</div>
		</div>
	</div>
</html>
