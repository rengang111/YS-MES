<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	用户管理
</head>

<body>
	<form name="form" id="form" modelAttribute="dataModels" action="${ctx}/login/checklogin" method="post">

		<table>
			<tr>
				<td>
					用户名：
				</td>
				<td>
					<input type=text name="loginId" id="loginId" value="${DisplayData.loginId}"/>
				</td>
			</tr>
			<tr>
				<td>
					密码：
				</td>
				<td>
					<input type=password name="pwd" id="pwd" value=""/>
				</td>
			</tr>
			<tr>
				<td>
					验证码：
				</td>
				<td>
	 				<input type="text" name="verifyCode" id="verifyCode"  />       
	        		<img id="imgObj"  alt="" src="${ctx}/login/verifyCode"/>       
	        		<a href="#" onclick="changeImg();">换一张</a>
	        	</td>	
			<tr>
				<td>
					<input type=button name="search" id="search" value="登录" onClick="doLogin()"/>
				</td>
			</tr>
		</table>
	</form>

</body>

<script>

	$(function(){
		if ('${DisplayData.message}' != '') {

			alert('${DisplayData.message}');
		}
		
	});

	function doLogin() {
		str = $('#loginId').val();
		if (!inputStrCheck(str, "用户名", 60, 8, false, true)) {
			return false;
		}
		str = $('#pwd').val();
		if (!inputStrCheck(str, "密码", 20, 8, false, true)) {
			return false;
		}
		str = $('#verifyCode').val();
		if (!inputStrCheck(str, "验证码", 5, 8, false, true)) {
			return false;
		}
		
		$('#form').submit();
	}

	function changeImg(){     
	    var imgSrc = $("#imgObj");     
	    var src = imgSrc.attr("src");     
	    imgSrc.attr("src", chgUrl(src));     
	}     
	//时间戳     
	//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳     
	function chgUrl(url){     
	    var timestamp = (new Date()).valueOf();     
	    newUrl = url.substring(0,17);     
	    if((url.indexOf("&")>=0)){     
	    	newUrl = url + "×tamp=" + timestamp;
	    }else{     
	    	newUrl = url + "?timestamp=" + timestamp;     
	    }

	    return newUrl;     
	}     
	function isRightCode(){     
	    var code = "verifyCode=" + $("#verifyCode").val();
	
	    $.ajax({     
	        type:"POST",     
	        url:"${ctx}/login/verify/validateCode",     
	        data:code,
	        success:callback     
	    });     
	}     
	function callback(data){     
	    $("#info").html(data);     
	} 
</script>
</html>