<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>

<title>模具出借详情</title>
<script type="text/javascript">

var validator;

$(document).ready(function() {
    jQuery.validator.addMethod("noCheck",function(value, element){
    	var rtnValue = true;
    	if ($('#name').html() == '') {
    		rtnValue = false;
    	}
        return rtnValue;   
    }, "模具编号错误"); 
    
	validator = $("#baseInfo").validate({
		rules: {	
			no: {
				noCheck: true,
				required: true,
				maxlength: 123,
			},
			name: {
				required: true,
				maxlength:50,
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
	
	autoComplete();
})


function doSave() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		actionUrl = "${ctx}/business/mouldlendregister?methodtype=updatemouldlendregisterdetail";

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
					parent.reloadMouldDetailList();

					if (d.message != "") {
						alert(d.message);	
					} else {
						
						//不管成功还是失败都刷新父窗口，关闭子窗口
						var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
						//parent.$('#events').DataTable().destroy();
						parent.layer.close(index); //执行关闭
					}
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
		
		actionUrl = "${ctx}/business/mouldlendregister?methodtype=deletemouldlendregisterdetail";

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
					parent.reloadMouldDetailList();

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

function autoComplete() { 
	$("#no").autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "POST",
				url : "${ctx}/business/mouldlendregister?methodtype=modelNoSearch",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					response($.map(
						data.data,
						function(item) {
							//alert(item.viewList)
							return {
								label : item.viewList,
								value : item.no,
								id : item.id,
								name : item.name
							}
						}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {

				}
			});
		},

		select : function(event, ui) {
			$('#mouldDetailId').val(ui.item.id);
			$("#no").val(ui.item.no);
			$("#name").html(ui.item.name);
			//$("#factoryProductCode").focus();

		},
		minLength : 1,
		autoFocus : false,
		width: 200,
	});	
}
</script>

</head>

<body>
<div id="container">

		<div id="main">
			<div id="mouldDetailBasic">				
				<div  style="height:20px"></div>
				
				<legend>出借详情</legend>
				<button type="button" id="save" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >删除</button>				
				<button type="button" id="save" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
					
				<form:form modelAttribute="dataModels" id="baseInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<input type=hidden id="mouldLendNo" name="mouldLendNo" value="${DisplayData.mouldLendNo}"/>
					<input type=hidden id="mouldDetailId" name="mouldDetailId" value="${DisplayData.mouldDetailId}"/>
					<table class="form" width="300px">
						<tr>
							<td width="60px">模具编号：</td>
							<td width="250px">
								<input type="text" id="no" name="no" class="short" value="${DisplayData.no}"/>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="60px">模具名称：</td>
							<td width="250px">
								<label  id="name" name="name" class="short" >${DisplayData.name}</label>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>

				</form:form>
			</div>
		</div>
	</div>
</html>
