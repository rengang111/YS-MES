<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>问题和改善</title>
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
	
	validator = $("#baseInfo").validate({
		rules: {
			createDate: {
				date: true,
				required: true,
				maxlength: 10,
			},
			description: {
				required: true,
				maxlength:200,
			},		
			improve: {
				maxlength:200,
			},	
			expectDate: {
				date: true,
				required: true,
				maxlength: 10,
			},
			finishTime: {
				maxlength: 10,
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
		
		actionUrl = "${ctx}/business/lateperfection?methodtype=updatequestion";

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

function doDelete() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		actionUrl = "${ctx}/business/lateperfection?methodtype=deletequestion";

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
							<td width="80px">新建日期：</td>
							<td >
								<input type="text" id="createDate" name="createDate" class="short" value="${DisplayData.questionData.createdate}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">问题描述：</td>
							<td >
								<textarea id="description" name="description" rows=5 cols=100 class="long">${DisplayData.questionData.description}</textarea>
							</td>
						</tr>
						<tr>
							<td width="60px">改善方案：</td>
							<td >
								<textarea id="improve" name="improve" rows=5 cols=100 class="long">${DisplayData.questionData.improve}</textarea>
							</td>
						</tr>									
						<tr>
							<td width="60px">预期完成<br>时间：</td>
							<td >
								<input type="text" id="expectDate" name="expectDate" class="short" value="${DisplayData.questionData.expectfinishdate}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">完成时间：</td>
							<td>
								<input type="text" id="finishTime" name="finishTime" class="short"  style="margin: 0px 0px 0px 10px;" value="${DisplayData.questionData.finishtime}"/>
							</td>
						</tr>
	
					</table>

				</form:form>
			</div>
		</div>
	</div>
</html>
