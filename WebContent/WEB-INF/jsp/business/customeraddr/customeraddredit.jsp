<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>地址</title>
<script type="text/javascript">

var validator;

$(document).ready(function() {
	
	controlButtons($('#keyBackup').val());
	
	addValidator();
	
	validator = $("#customerAddrInfo").validate({
		rules: {
			title: {
				required: true,
				maxlength: 20,
			},
			address: {
				required: true,
				maxlength: 50,
			},
			postcode: {
				required: true,
				maxlength: 10,
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
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/customeraddr?methodtype=add";
			$('#customerId').val(parent.$('#keyBackup').val());
		} else {
			//修正
			actionUrl = "${ctx}/business/customeraddr?methodtype=update";
		}

		var actionUrl;			
		
		//将提交按钮置为【不可用】状态
		//$("#submit").attr("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : actionUrl,
			data : JSON.stringify($('#customerAddrInfo').serializeArray()),// 要提交的表单
			success : function(d) {
				parent.reloadCustomerAddr();
				if (d.message != "") {
					alert(d.message);	
				}
				//TODO
				//var infoList = d.info.split("|");
				//$('#keyBackup').val(infoList[0]);
				//$('#customerId').val(infoList[1]);
				
				controlButtons(d.info);
				parent.reloadCustomerAddr();
				//不管成功还是失败都刷新父窗口，关闭子窗口
				//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();
				//parent.layer.close(index); //执行关闭
				
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

function doDelete() {
	
	if (confirm("${DisplayData.endInfoMap.message}")) {
		//将提交按钮置为【不可用】状态
		//$("#submit").attr("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/business/customeraddr?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.message != "") {
					alert(d.message);	
				} else {
					parent.reloadCustomerAddr();
					//$('#keyBackup').val("");
					//$('#customerId').val("");
					controlButtons("");
					clearContactInfo();

				}
					
				//不管成功还是失败都刷新父窗口，关闭子窗口
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();/
				//parent.reload_customeraddror();
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
	$('#title').val('');
	$('#address').val('');
	$('#postcode').val('');
	$('#memo').val('');
}

function controlButtons(data) {
	var valueArray = data.split("|");
	if (valueArray.length > 1) {
		$('#keyBackup').val(valueArray[0]);
		$('#customerId').val(valueArray[1]);
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

<body class="noscroll">

<div id="layer_main">			
				
	<form:form modelAttribute="dataModels" id="customerAddrInfo" style='padding: 0px; margin: 10px;' >
	
		<input type="hidden" id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
		<input type="hidden" id="customerId" name="customerId" value="${DisplayData.customerId}"/>
		
		<fieldset>
			<legend>客户地址信息</legend>
			
			<table class="form" width="850px">
				<tr>
					<td width="100px" class="label">地址抬头：</td>
					<td>
						<input type="text" id="title" name="title" class="short" value="${DisplayData.customerAddrData.title}"/>
					</td>
				</tr>
				<tr>
					<td width="100px" class="label">详细地址：</td> 
					<td>
						<input type="text" id="address" name="address" class="middle" value="${DisplayData.customerAddrData.address}"/>
					</td>
				</tr>
				<tr>
					<td width="100px" class="label">邮编：</td> 
					<td>
						<input type="text" id="postcode" name="postcode" class="short" value="${DisplayData.customerAddrData.postcode}"/>
					</td>	
				</tr>
				<tr>
					<td width="100px" class="label">备注：</td> 
					<td>
						<input type="text" id="memo" name="memo" class="long" value="${DisplayData.customerAddrData.memo}"/>
					</td>
				</tr>
			</table>
		</fieldset>	
		<fieldset style="text-align:right">
			<button type="button" class="DTTT_button"  onclick="doSave()">保存</button>
		</fieldset>
		</form:form>
	</div>

	</body>
</html>
