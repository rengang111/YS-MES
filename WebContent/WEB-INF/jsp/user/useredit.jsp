<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>
<html>
<head>
	 用户信息维护
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
		<input type=hidden name="userData.userid" id="userid" value="${DisplayData.userData.userid}"/>
		<input type=hidden name="photo" id="photo" value="${DisplayData.photo}"/>
		<input type=hidden name="photoChangeFlg" id="photoChangeFlg" value="${DisplayData.photoChangeFlg}"/>
		<table>
			<tr>
				<td>
					单位名称：
				</td>
				<td>
					<input type=text name="unitName" id="unitName" value="${DisplayData.unitName}" readonly/>
					<input type=hidden name="unitId" id="unitId" value="${DisplayData.unitId}"/>
					<input type=button name="userselect" id="userselect" value="选择单位" onClick="selectUnit();"/>					
				</td>
			</tr>
			<tr>
				<td>
					账号：
				</td>
				<td>
					<input type=text name="userData.loginid" id="loginid" value="${DisplayData.userData.loginid}"/>
				</td>
			</tr>			
			<tr>
				<td>
					用户名称：
				</td>
				<td>
					<input type=text name="userData.loginname" id="loginname" value="${DisplayData.userData.loginname}"/>
				</td>
			</tr>
			<tr>
				<td>
					用户简拼：
				</td>
				<td>
					<input type=text name="userData.jianpin" id="jianpin" value="${DisplayData.userData.jianpin}"/>
				</td>
			</tr>		
			<tr>
				<td>
					密码：
				</td>
				<td>
					<input type=password name="userData.loginpwd" id="loginpwd" value="${DisplayData.userData.loginpwd}"/>
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
				<td>
					性别：
				</td>
				<td>
					<select name="userData.sex" id="usersex">
						<c:forEach items="${DisplayData.sexList}" var="value" varStatus="status">
							<option value ="${value[0]}">${value[1]}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					职务：		
				</td>
				<td>
					<select name="userData.duty" id="userduty">
						<c:forEach items="${DisplayData.dutyList}" var="value" varStatus="status">
							<option value ="${value[0]}">${value[1]}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					地址（省市县）：
				</td>
				<td>
					<input type=text name="address" id="address" value="${DisplayData.address}" readonly/>
					<input type=hidden name="userData.addresscode" id="addresscode" value="${DisplayData.userData.addresscode}" readonly/>
					<input type=button name="addressSelect" id="addressSelect" value="选择地址" onClick="selectAddress();"/>
				</td>
			</tr>
			<tr>
				<td>
					地址（街道门牌号码）：
				</td>
				<td>
					<input type=text name="userData.addressuser" id="addressuser" value="${DisplayData.userData.addressuser}"/>
				</td>
			</tr>			
			<tr>
				<td>
					邮编：
				</td>
				<td>
					<input type=text name="userData.postcode" id="postcode" value="${DisplayData.userData.postcode}"/>
				</td>
			</tr>
			<tr>
				<td>
					电话：
				</td>
				<td>
					<input type=text name="userData.telphone" id="telphone" value="${DisplayData.userData.telphone}"/>
				</td>
			</tr>
			<tr>
				<td>
					手机：
				</td>
				<td>
					<input type=text name="userData.handphone" id="handphone" value="${DisplayData.userData.handphone}"/>
				</td>
			</tr>			
			<tr>
				<td>
					EMAIL：
				</td>
				<td>
					<input type=text name="userData.email" id="email" value="${DisplayData.userData.email}"/>
				</td>
			</tr>
			<tr>
				<td>
					找回密码1：
				</td>
				<td>
					<input type=text name="userData.pwdquestion1" id="pwdquestion1" value="${DisplayData.userData.pwdquestion1}"/>
				</td>
			</tr>
			<tr>
				<td>
					找回密码2：
				</td>
				<td>
					<input type=text name="userData.pwdquestion2" id="pwdquestion2" value="${DisplayData.userData.pwdquestion2}"/>
				</td>
			</tr>
			<tr>
				<td>
					有效时间：
				</td>
				<td>
					<input class="easyui-datebox" name="userData.enablestarttime" id="enablestarttime" value="${DisplayData.userData.enablestarttime}"/>至
					<input class="easyui-datebox" name="userData.enableendtime" id="enableendtime" value="${DisplayData.userData.enableendtime}"/>
				</td>
			</tr>			
			<tr>
				<td>
					工号：
				</td>
				<td>
					<input type=text name="userData.workid" id="workid" value="${DisplayData.userData.workid}"/>
				</td>
			</tr>			
			<tr>
				<td>
					序号：
				</td>
				<td>
					<input type=text name="userData.sortno" id="sortno" value="${DisplayData.userData.sortno}"/>
				</td>
			</tr>
			<tr>
				<td>
					照片：
				</td>
				<td>
					<input type="file" id="headPhotoFile" name="headPhotoFile" onchange="uploadHeadPhoto();" accept="image/jpeg"/>
					<img id="headPhoto" width=60px height=60px src="${ctx}${DisplayData.photo}"/>
				</td>
			</tr>		
			<tr>
				<td>
				</td>
				<td>
					<input type=checkbox name="userData.lockflag" id="lockflag" value="1"/>选中锁定
					<input type=checkbox name="userData.enableflag" id="enableflag" value="1"/>选中失效
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<input type=button name="save" id="save" value="保存" onClick="saveUpdate()"/>
					<input type=button name="close" id="close"" value="关闭" onClick="closeWindow('')"/>
				</td>
			</tr>

		</table>
		<br>
	</form>

</body>

<script>
	var operType = '';
	var isUpdateSuccessed = false;
	var updatedRecordCount = parseInt('${DisplayData.updatedRecordCount}');
	
	$(function(){
		operType = $('#operType').val();

		$('#usersex').val("${DisplayData.userData.sex}");
		$('#userduty').val("${DisplayData.userData.duty}");
		if ('${DisplayData.userData.lockflag}' == '1') {
			$('#lockflag').attr("checked", "true");
		}
		if ('${DisplayData.userData.enableflag}' == '1') {
			$('#enableflag').attr("checked", "true");
		}
		
		if (operType == 'add') {
			$('#save').val("保存增加");
		}
		if (operType == 'update') {
			if (updatedRecordCount > 0 ) {
				refreshOpenerData();				
				if ('${DisplayData.message}' != '') {
					alert('${DisplayData.message}');
				}
				closeWindow("1");
			} else {
				$('#save').val("保存修正");
				$('#userid').attr('readonly', true);
				$('#loginpwd').hide();
				$('#loginpwdConfirm').hide();
			}
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('user');
			$('#headPhotoFile').attr('disabled', true);
		}
		
		if (updatedRecordCount > 0) {
			refreshOpenerData();
			$('#headPhoto').attr('src', '');
		}

		if ($('#photo').val() != '') {
			$('#headPhoto').attr('src', '${ctx}' + $('#photo').val());
		}
				
		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 

	function inputCheck() {
		var str = '';

		str = $('#unitName').val();
		if (!inputStrCheck(str, "单位名称", 100, 7, false, true)) {
			return false;
		}
		str = $('#loginid').val();
		if (!inputStrCheck(str, "账号", 20, 8, false, true)) {
			return false;
		}
		str = $('#loginname').val();
		if (!inputStrCheck(str, "用户名称", 20, 7, false, true)) {
			return false;
		}
		str = $('#jianpin').val();
		if (!inputStrCheck(str, "用户简拼", 20, 7, true, true)) {
			return false;
		}
		
		if (operType != 'update') {
			str = $('#loginpwd').val();
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
		}	

		str = $('#usersex').val();
		if (!inputStrCheck(str, "性别", 3, 8,  false, true)) {
			return false;
		}
		str = $('#userduty').val();
		if (!inputStrCheck(str, "职务", 30, 8,  false, true)) {
			return false;
		}	
		str = $('#addresscode').val();
		if (!inputStrCheck(str, "地址（省市县）", 100, 7,  false, true)) {
			return false;
		}		
		str = $('#addressuser').val();
		if (!inputStrCheck(str, "地址（街道门牌号码）", 100, 7,  false, true)) {
			return false;
		}
		
		str = $('#postcode').val();
		if (!inputStrCheck(str, "邮编", 6, 3, false, true)) {
			return false;
		}
		str = $('#telphone').val();
		if (!inputStrCheck(str, "电话", 20, 5, false, true)) {
			return false;
		}
		str = $('#handphone').val();
		if (!inputStrCheck(str, "手机", 20, 5, false, true)) {
			return false;
		}
		str = $('#email').val();
		if (!inputStrCheck(str, "EMAIL", 50, 6, false, true)) {
			return false;
		}
		str = $('#pwdquestion1').val();
		if (!inputStrCheck(str, "找回密码1", 20, 7, false, true)) {
			return false;
		}
		str = $('#pwdquestion2').val();
		if (!inputStrCheck(str, "找回密码2", 20, 7, true, true)) {
			return false;
		}
		str = $('#enablestarttime').datebox('getValue');
		if (!inputStrCheck(str, "有效时间开始", 120, 1, false, true)) {
			return false;
		}
		str = $('#enableendtime').datebox('getValue');
		if (!inputStrCheck(str, "有效时间终止", 120, 1, true, true)) {
			return false;
		}
		if (str == "") {
			$('#enableendtime').datebox('setValue', '2050-12-31 24:00:00');
		}
		str = $('#workid').val();
		if (!inputStrCheck(str, "工号", 120, 8, true, true)) {
			return false;
		}
		str = $('#sortno').val();
		if (!inputStrCheck(str, "序号", 120, 3, true, true)) {
			return false;
		}
		/*
		str = $('#photo').val();
		if (!inputStrCheck(str, "照片", 120, 8, true, true)) {
			return false;
		}
		*/
		return true;	
	}

	function saveUpdate() {
		if (inputCheck()) {
			if (confirm("确定要保存吗？")) {
				if (operType == 'add') {
					$('#form').attr("action", "${ctx}/user?methodtype=add");
				} else {
					$('#form').attr("action", "${ctx}/user?methodtype=update");
				}
				$('#form').submit();
			}
		}	
	}

	function selectUnit() {
		callUnitSelect("unitId", "unitName", "0");
	}

	function selectAddress() {
		callDicSelect("addresscode", "address", 'A2', '1' );
	}
	
	function closeWindow(isNeedConfirm) {
		if (isNeedConfirm == '') {
			if (operType == 'add' || operType == 'update') {
				if (confirm("确定要离开吗？")) {
					self.opener = null;
					self.close();
				}
			} else {
				self.opener = null;
				self.close();
			}
		} else {
			self.opener = null;
			self.close();
		}
	}

	function confirmPwd() {
		if ($('#loginpwd').val() != $('#loginpwdConfirm').val()) {
			alert("密码及确认密码不一致");
			return false;
		}
		return true;
	}
	
	function refreshOpenerData() {
		if (operType == 'add' || operType == 'update') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}

	function uploadHeadPhoto() {

		var fileName = $("#headPhotoFile").val();
		if (fileName != '') {
			var extname = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);  
		    extname = extname.toLowerCase();
		    
		    if (extname != "jpeg" && extname != "jpg") {
				alert("只可以上传jpeg格式的图片文件");
				return false;
			}
	
		    $("#form").ajaxSubmit({
				type: "POST",
				url:'${ctx}/user?methodtype=uploadHeadPhoto',
				dataType: 'json',
			    success: function(data){
			     	if(data.result == '0'){
						$('#userid').val(data.userId);
						$('#headPhoto').attr('src', '${ctx}' + data.path);						
						$('#photo').val(data.path);
						$('#photoChangeFlg').val("1");
			   		 }
			    	else{
			    		alert(data.message);
			    	}
				},
		        error : function(XMLHttpRequest, textStatus, errorThrown) {  
					
		        } 		
			});	
		}
	}
</script>
</html>