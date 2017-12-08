<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>

<body>

	<div style="margin:0 auto;width:400px;height:200px; position: fixed;top: 50%;botoom: 50%; left:50%; right:50%;">

		<div style="margin-left:-200px;margin-top:-200px;width:400px;height:200px;">
			<form name="form" id="form" modelAttribute="dataModels" action="${ctx}/login/checklogin" method="post"  autocomplete="off">
			
				<table  style='margin:0px auto;border-collapse:separate; border-spacing:10px;width:500px'>
					<tr height="30px">
						<td width="60px">
							用户名：
						</td>
						<td width="150px" colspan=3>
							<input type=text name="loginId" id="loginId" value="${DisplayData.loginId}" style="height:30px" />
						</td>
					</tr>
					<tr height="30px">
						<td width="60px">
							密码：
						</td>
						<td width="150px" colspan=3>
							<input type="text"  id="webPwd" value="" style="height:30px" 
								onfocus="this.type='password'" 
								onBlur="pwdChange();" />
							<input type="hidden" name="pwd" id="pwd" />
						</td>
					</tr>
					<!-- 
					<tr height="30px">
						<td width="60px">
							验证码：
						</td>
						<td width="150px">
			 				<input type="text" name="verifyCode" id="verifyCode" style="height:30px;text-transform:uppercase;font-size: 24px;width: 135px;"/>
			 			</td>
			 			<td width="100px">
			        			<img id="imgObj" src="${ctx}/login/verifyCode" ></img>
			        	</td>
			        	<td width="60px">
			        		<a href="#" onclick="changeImg();">换一张</a>
			        	</td>
			        </tr>	
			         -->
					<tr height="30px">
						<td></td>
						<td align="center">
							<button type="button" id="login" class="DTTT_button" style="width:50px" value="登陆" onClick="doLogin();">登陆</button>
						</td>
						<td colspan=2></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>

<script>

	$(function(){
		if ('${DisplayData.message}' != '') {

			alert('${DisplayData.message}');
		}
		
	});

	function pwdChange() {
		
		var pwd=$('#webPwd').val();
		$('#pwd').val(pwd);
		$('#webPwd').val('         ');//清空
	}
	
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
		//if (!inputStrCheck(str, "验证码", 5, 8, false, true)) {
		//	return false;
		//}
		
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