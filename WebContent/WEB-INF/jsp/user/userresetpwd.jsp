<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
<title>	 密码修改</title>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">
	<form:form name="form" id="form" modelAttribute="dataModels" action="" method="post" autocomplete="off">
		<fieldset>
		<legend> 密码修改</legend>
		<table  class="form" style="height: 200px;">
			<tr>
				<td class="label" width="100px">现在的密码：</td>
				<td><input type="password" name="nowPwd" id="nowPwd" value="" autocomplete="off" /></td>
			</tr>		
			<tr>
				<td class="label" width="100px">新密码：</td>
				<td><input type="password" name="wantPwd" id="wantPwd" value="" class="" /></td>
			</tr>
			<tr>
				<td class="label" width="100px">确认新密码：</td>
				<td><input type="password" name="loginpwdConfirm" id="loginpwdConfirm" value=""/></td>
			</tr>				
			<tr>
				<td colspan=2 style="text-align: end;">
						<button type="button" id="save" class="DTTT_button" 
							style="width:50px" onclick="saveUpdate();">保存</button>
						<button type="button" id="close" class="DTTT_button" 
							style="width:50px" onclick="closeWindow('');">关闭</button>
					
				<!-- 	<input type=button name="save" id="save" value="找回密码" onClick=""/>	 -->				
					
				</td>
			</tr>

		</table>
		</fieldset>
	</form:form>
</div>
</div>
</body>

<script>

	$(function(){
		$('#form').attr("action", "");
		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	});
	
	function inputCheck() {
		var str = '';
		str = $('#nowPwd').val();
		if (!inputStrCheck(str, "现在的密码", 100, 7, false, true)) {
			return false;
		}
		str = $('#wantPwd').val();
		if (!inputStrCheck(str, "密码", 100, 7, false, true)) {
			return false;
		}	
		str = $('#loginpwdConfirm').val();
		if (!inputStrCheck(str, "确认密码", 100, 7,  false, true)) {
			return false;
		}
		if (!confirmPwd()) {
			return false;
		}
		return true;	
	}

	function saveUpdate() {
		if (inputCheck()) {
			if (confirm("确定要保存吗？")) {
				$('#form').attr("action", "${ctx}/user?methodtype=resetpwd");
				$('#form').submit();
			}
		}	
	}

	
	function confirmPwd() {
		if ($('#wantPwd').val() != $('#loginpwdConfirm').val()) {
			alert("密码及确认密码不一致");
			return false;
		}
		return true;
	}

	function closeWindow() {
		self.opener = null;
		self.close();
	}

</script>
</html>