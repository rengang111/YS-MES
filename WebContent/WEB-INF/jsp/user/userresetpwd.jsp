<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>
<html>
<head>
	 密码修改
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<table>
			<tr>
				<td>
					现在的密码：
				</td>
				<td>
					<input type=password name="nowPwd" id="nowPwd" value=""/>
				</td>
			</tr>		
			<tr>
				<td>
					密码：
				</td>
				<td>
					<input type=password name="wantPwd" id="wantPwd" value=""/>
				</td>
			</tr>
			<tr>
				<td>
					确认密码：
				</td>
				<td>
					<input type=password name="loginpwdConfirm" id="loginpwdConfirm" value=""/>
				</td>
			</tr>				
			<tr>
				<td colspan=2>
					<input type=button name="save" id="save" value="保存" onClick="saveUpdate()"/>
					<input type=button name="save" id="save" value="找回密码" onClick=""/>					
					<input type=button name="close" id="close"" value="关闭" onClick="closeWindow('')"/>
				</td>
			</tr>

		</table>
		<br>
	</form>

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