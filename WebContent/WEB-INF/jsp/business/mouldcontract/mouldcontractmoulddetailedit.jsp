<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>模具详情</title>
<script type="text/javascript">

var validator;

$(document).ready(function() {
	
	validator = $("#baseInfo").validate({
		rules: {
			type: {
				required: true,
			},			
			no: {
				required: true,
				maxlength: 123,
			},
			name: {
				required: true,
				maxlength:50,
			},		
			size: {
				required: true,
				maxlength:50,
			},	
			materialQuality: {
				required: true,
				maxlength: 50,
			},
			mouldUnloadingNum: {
				required: true,
				digits: true,
				maxlength: 5,
			},
			heavy: {
				required: true,
				digits: true,
				maxlength: 5,
			},
			price: {
				required: true,
				digits: true,
				maxlength: 5,
			},
			place: {
				required: true,
				maxlength: 120,
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
	
	$('#type').val('${DisplayData.mouldDetailData.type}');
	$('#place').val('${DisplayData.mouldDetailData.place}');
})


function doSave() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		actionUrl = "${ctx}/business/mouldcontract?methodtype=updatemoulddetail";

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
		
		actionUrl = "${ctx}/business/mouldcontract?methodtype=deletemoulddetail";

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
</script>

</head>

<body>
<div id="container">

		<div id="main">
			<div id="mouldDetailBasic">				
				<div  style="height:20px"></div>
				
				<legend>预期信息</legend>
				<button type="button" id="save" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >删除</button>				
				<button type="button" id="save" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
					
				<form:form modelAttribute="dataModels" id="baseInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<input type=hidden id="mouldBaseId" name="mouldBaseId" value="${DisplayData.mouldBaseId}"/>
					<table class="form" width="800px" border="1px;">
						<tr>
							<td width="60px">类型：</td>
							<td width="80px">
								<form:select path="type">
									<form:options items="${DisplayData.typeList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>
							<td width="60px">模具编号：</td>
							<td width="80px">
								<input type="text" id="no" name="no" class="short" value="${DisplayData.mouldDetailData.no}"/>
							</td>
							<td width="60px">模具名称：</td>
							<td width="80px">
								<input type="text"  id="name" name="name" class="short" value="${DisplayData.mouldDetailData.name}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">模架尺寸：</td>
							<td width="80px">
								<input type="text"  id="size" name="size" class="short" value="${DisplayData.mouldDetailData.size}"/>
							</td>
							<td width="60px">材质：</td>
							<td width="80px">
								<input type="text" id="materialQuality" name="materialQuality" class="short" value="${DisplayData.mouldDetailData.materialquality}"/>
							</td>
							<td width="60px">出模数：</td>
							<td width="80px">
								<input type="text" id="mouldUnloadingNum" name="mouldUnloadingNum" class="short"  style="margin: 0px 0px 0px 10px;" value="${DisplayData.mouldDetailData.mouldunloadingnum}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">重量：</td>
							<td width="80px">
								<input type="text" id="heavy" name="heavy" class="short"  style="margin: 0px 0px 0px 10px;" value="${DisplayData.mouldDetailData.heavy}"/>
							</td>
						</tr>
						<tr>
							<td width="60px">价格：</td>
							<td width="80px">
								<input type="text" id="price" name="price" class="short"  style="margin: 0px 0px 0px 10px;" value="${DisplayData.mouldDetailData.price}"/>
							</td>
							<td width="60px">模具工厂：</td>
							<td width="80px">
								<label id="mouldFactory" name="mouldFactory" class="short"  style="margin: 0px 0px 0px 10px;">${DisplayData.mouldFactory}</label>
							</td>
						</tr>
						<tr>
							<td width="60px">存放位置：</td>
							<td width="80px" colspan=3>
								<form:select path="place">
									<form:options items="${DisplayData.placeList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>							
						</tr>
					</table>

				</form:form>
			</div>
		</div>
	</div>
</html>
