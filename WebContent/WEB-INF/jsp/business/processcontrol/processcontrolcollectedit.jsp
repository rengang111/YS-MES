<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>进程详情</title>
<script type="text/javascript">

var validator;

$(document).ready(function() {
	
	addValidator();
	
	validator = $("#baseInfo").validate({
		rules: {
			expectDate: {
				required: true,
				date: true,
				maxlength: 10,
			},
			finishDate: {
				date: true,
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

					parent.setExpectDate(info[0], info[1], info[2], info[3]);

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

function onExpectDateInput() {
	$('#exceedDate').html(getDateDiff($('#expectDate').val(), ""));
	$('#exceedDate2View').val($('#exceedDate').html());
}

function onFinishDateInput() {
	$('#exceedDate').html(getDateDiff($('#expectDate').val(), $('#finishDate').val()));
	$('#exceedDate2View').val($('#exceedDate').html());
}


</script>

</head>

<body>
<div id="container">

		<div id="main">
			<div id="supplierBasic">				
				<div  style="height:20px"></div>
				
				<legend>文件信息</legend>
				<button type="button" id="save" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
					
				<form:form modelAttribute="dataModels" id="baseInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<input type=hidden id="type" name="type" value="${DisplayData.type}"/>
					<input type=hidden id="projectId" name="projectId" value="${DisplayData.projectId}"/>
					<input type=hidden id="exceedDate2View" name="exceedDate2View" value=""/>
					<table class="form" width="850px">					
						<tr>
							<td width="60px">预期完成：</td>
							<td width="80px" align="left">
								<input type="text" id="expectDate" name="expectDate" class="short" value="${DisplayData.processControlData.expectdate}" onInput="onExpectDateInput();"/>
							</td>
						</tr>
						<tr>
							<td width="60px">当前超期天数：</td>
							<td width="80px" align="left">
								<label id="exceedDate" name="exceedDate" class="short" style="margin: 0px 0px 0px 10px;">${DisplayData.exceedTime}</label>
							</td>
						</tr>
						<tr>
							<td width="60px">最新预期：</td>
							<td width="80px" align="left">
								<label id="lastestExpectDate" name="lastestExpectDate" class="short" style="margin: 0px 0px 0px 10px;">${DisplayData.lastestExpectDate}</label>
							</td>
						</tr>						
						<tr>
							<td width="60px">完成日期：</td>
							<td width="80px" align="left">
								<input type="text" id="finishTime" name="finishTime" class="short" value="${DisplayData.processControlData.finishtime}" onInput="onFinishDateInput();"/>
							</td>
						</tr>					
					</table>

				</form:form>
			</div>
		</div>
	</div>
</html>
