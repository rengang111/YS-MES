<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>	
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>

<head>
<title></title>
<meta charset="UTF-8"> 
<%@ include file="../../common/common.jsp"%>

<style type="text/css">

html { overflow: hidden; }

.aligncenter {
margin: auto;
position: absolute;
top: -30px; left: 0; bottom: 0; right: 0;
}

</style>

<script type="text/javascript">

	//alert("${src}");

	//Form序列化后转为AJAX可提交的JSON格式。
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	
	function setNowUse(){
		
		//alert(999);
		
		$
		.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/album/setNowUseImage",
			data : {key:"${key}", fileName:"${fileName}", className:"${className}", index:"${index}", albumCount:"${albumCount}"},// 要提交的表单
			success : function(d) {

				var retValue = d['retValue'];

				//alert(retValue);

				if (retValue == "failure") {

					var retCode = d['retCode'];
					//alert(retCode);

					switch (retCode) {
					case '-100':
						var errMsg = d['errMsg'];
						alert(errMsg);
						break;
					default:
						alert("${err001}"); //操作中发生错误，页面将关闭，请重试或者联系管理员。
					}
				} else {
					// 从体验度来说，不要成功提示似乎操作更流畅。
					alert("已设置为当前图片");
					//parent.refresh();
				}
								
			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {

				//alert(XMLHttpRequest.status);					
				//alert(XMLHttpRequest.readyState);					
				//alert(textStatus);					
				//alert(errorThrown);					

				if (XMLHttpRequest.status == "800") {
					alert("${err006}"); //请不要重复提交！
				} else {
					alert("${err002}"); //发生系统异常，请再试或者联系系统管理员。
				}
				//关闭窗口
				var index = parent.layer
						.getFrameIndex(window.name); //获取当前窗体索引				
				//parent.refresh();
				parent.layer
						.close(index); //执行关闭			

			}
		});
	}
		
	function closeWindow(){
		if (confirm("确认关闭窗口吗？")) {
			
			var index = parent.layer
			.getFrameIndex(window.name); //获取当前窗体索引
			//parent.$('#events').DataTable().destroy();/
			parent.refresh();
			//alert(777);
			parent.layer.close(index); //执行关闭
			
		}
	}
					
	function deletePhoto() {
				
		if (confirm("${msg}")) {
		
			//var str_temp = "${factoryContact.contactId}";
			//alert(888);
			
			var url = "${pageContext.request.contextPath}/album/image-remove";
			
			//alert(url);
						
			$.ajax({
				type : "GET",
				url : url,
				data : {key:"${key}", fileName:"${fileName}", className:"${className}", index:"${index}", albumCount:"${albumCount}"},// 要提交的表单
				success : function(d) {													

					var retValue = d['retValue'];
					
					//alert(999);
					
					//alert(retValue);
					
					if (retValue =="failure"){
						//alert(d['exName']);
						$().toastmessage('showWarningToast', "请联系系统管理员。</br>请联系系统管理员。");	
						
						//$("submit").attr("disabled",true); 
					}	
					
					var index = parent.layer
					.getFrameIndex(window.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();/
					parent.refresh();
					//alert(777);
					parent.layer.close(index); //执行关闭
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
					alert("操作失败，请再试或和系统管理员联系。");
				}
			});
		}
	};
	
	
</script>

</head>
<body>	

	<div id="layer_main" style="padding:20px;height:480px;width:640px;">
		<img alt="" src="${pageContext.request.contextPath}/${path}${key}/${index}/${fileName}" style="max-height:480px;max-width:640px;" class="aligncenter" >
	</div>
	
	<div style="float:right;">
		<a href="#" class="a-btn-green" style="padding: 0px 5px 0px 25px;margin:0px 25px 0px 3px;" onclick="return closeWindow()">
			<img src="../../images/action_delete.png" alt="Photos" height="16px" style="top:5px;"/>
			<span class="a-btn-text" >关闭窗口</span> 
			</span></span>
		</a>	
	</div>
	
	<div style="float:right;">
		<a href="#" class="a-btn-green" style="padding: 0px 5px 0px 25px;margin:0px 25px 0px 3px;" onclick="return deletePhoto()">
			<img src="../../images/action_delete.png" alt="Photos" height="16px" style="top:5px;" />
			<span class="a-btn-text" style="font-size:12px;">删除图片</span> 
			</span></span>
		</a>		
	</div>
	
	<div style="float:right;">
		<a href="#" class="a-btn-green" style="padding: 0px 5px 0px 25px;margin:0px 25px 0px 3px;" onclick="return setNowUse()">
			<img src="../../images/action_add.png" alt="Photos" height="16px" style="top:5px;"/>
			<span class="a-btn-text" >设为当前</span> 
			</span></span>
		</a>	
	</div>
	
</body>
</html>
