<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>外样记录关联文件</title>
<script type="text/javascript">

var validator;

$(document).ready(function() {
	
	controlButtons($('#keyBackup').val());
	
	addValidator();
	
	validator = $("#esRelationFileInfo").validate({
		rules: {
			title: {
				maxlength: 20,
			},			
			fileName: {
				required: true,
				maxlength: 50,
			},
			path: {
				required: true,
				maxlength: 50,
			},
			memo: {
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
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/esrelationfile?methodtype=add";
			$('#relationFileId').val(parent.$('#keyBackup').val());
		} else {
			//修正
			actionUrl = "${ctx}/business/esrelationfile?methodtype=update";
		}

		if (confirm(message)) {
			var actionUrl;			
			
			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#esRelationFileInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					parent.reloadTestFileList();
					parent.reloadMachinePicList();
					if (d.message != "") {
						alert(d.message);	
					}
					//TODO
					//var infoList = d.info.split("|");
					//$('#keyBackup').val(infoList[0]);
					//$('#relationFileId').val(infoList[1]);
					
					controlButtons(d.info);
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
	
	if (confirm("${DisplayData.endInfoMap.message}")) {
		//将提交按钮置为【不可用】状态
		//$("#submit").attr("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/business/esrelationfile?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.message != "") {
					alert(d.message);	
				} else {
					parent.reloadTestFileList();
					parent.reloadMachinePicList();
					//$('#keyBackup').val("");
					//$('#relationFileId').val("");
					controlButtons("");
					clearContactInfo();

				}
					
				//不管成功还是失败都刷新父窗口，关闭子窗口
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();/
				//parent.reload_esrelationfileor();
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

function clearContactInfo() {
	$('#fileName').val('');
	$('#path').val('');
	$('#memo').val('');
}

function controlButtons(data) {
	var valueArray = data.split("|");
	if (valueArray.length > 1) {
		$('#keyBackup').val(valueArray[0]);
		$('#').val(valueArray[1]);
	} else {
		$('#keyBackup').val(data);
	}
	if (data == '') {
		$('#delete').attr("disabled",true);
	} else {
		$('#delete').attr("disabled",false);
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
					
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="save" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
					
				<form:form modelAttribute="dataModels" id="esRelationFileInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<input type=hidden id="esId" name="esId" value="${DisplayData.esId}"/>
					<input type=hidden id="type" name="type" value="${DisplayData.type}"/>
					<table class="form" width="850px">
						<tr>
							<td width="60px">文件标题：</td>
							<td width="200px">
								<input type="text" id="title" name="title" class="short" value="${DisplayData.esRelationFileData.title}"/>
							</td>
						</tr>					
						<tr>
							<td width="60px">文件名：</td>
							<td width="200px">
								<input type="text" id="fileName" name="fileName" class="short" value="${DisplayData.esRelationFileData.filename}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">路径：</td> 
							<td width="200px">
								<input type="text" id="path" name="path" class="middle" value="${DisplayData.esRelationFileData.path}"/>
							</td>
						</tr>
						<tr>
							<td>说明：</td> 
							<td width="200px">
								<input type="text" id="memo" name="memo" class="middle" value="${DisplayData.esRelationFileData.memo}"/>
							</td>
						</tr>
					</table>

				</form:form>
			</div>
		</div>
	</div>
</html>
