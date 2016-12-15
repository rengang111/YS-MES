<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>付款申请</title>
<script type="text/javascript">

var validator;

$(document).ready(function() {
	
	controlButtons($('#keyBackup').val());
	
	addValidator();
	
	validator = $("#payInfo").validate({
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
		
		//修正
		actionUrl = "${ctx}/business/mouldcontract?methodtype=updatepay";

		if (confirm(message)) {
			var actionUrl;			
			
			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#payInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					parent.reloadPayList();

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
			url : "${ctx}/business/mouldcontract?methodtype=deletepay",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.message != "") {
					alert(d.message);	
				} else {
					parent.reloadPayList();
					//$('#keyBackup').val("");
					//$('#relationFileId').val("");
					controlButtons("");
					clearContactInfo();

				}
					
				//不管成功还是失败都刷新父窗口，关闭子窗口
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();/
				//parent.reload_latePerfectionor();
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
	$('#payTime').val('');
	$('#pay').val('');
	
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
					
				<form:form modelAttribute="dataModels" id="payInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<input type=hidden id="mouldBaseId" name="mouldBaseId" value="${DisplayData.mouldBaseId}"/>
					<input type=hidden id="withhold" name="withhold" value="${DisplayData.withhold}"/>
					<table class="form" width="850px">
						<tr>
							<td width="60px">付款日期：</td>
							<td width="200px">
								<input type="text" id="payTime" name="payTime" class="short" value="${DisplayData.mouldPayListData.paytime}"/>
							</td>
						</tr>					
						<tr>
							<td width="60px">付款金额：</td>
							<td width="200px">
								<input type="text" id="pay" name="pay" class="short" value="${DisplayData.mouldPayListData.pay}"/>
							</td>
						</tr>
					</table>

				</form:form>
			</div>
		</div>
	</div>
</html>
