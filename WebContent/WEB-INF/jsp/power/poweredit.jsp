<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>
<html>
<head>
	 用户授权管理
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
		<input type=hidden name="id" id="id" value='${DisplayData.id}'/>
		<input type=hidden name="unitId" id="unitId" value='${DisplayData.unitId}'/>
		<table>
			<tr>
				<td>
					用户：
				</td>
				<td>
					<input type=text name="userName" id="userName" value="${DisplayData.userName}" readonly/>
					<input type=hidden name="userId" id="userId" value="${DisplayData.userId}"/>
					<input type=button name="userselect" id="userselect" value="选择" onClick="selectUser();"/>					
				</td>
			</tr>
			<tr>
				<td>
					角色：
				</td>
				<td>
					<input type=text name="roleName" id="roleName" value="${DisplayData.roleName}" readonly/>
					<input type=hidden name="roleId" id="roleId" value="${DisplayData.roleId}"/>
					<input type=button name="roleselect" id="roleselect" value="选择" onClick="selectRole();"/>					
				</td>
			</tr>
			<tr>
				<td>
					授权方式：
				</td>
				<td>
					<select name="powerType" id="powerType">
						<option value ="1">用户授权</option>
						<option value ="2">用户组授权</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					单位：
				</td>
				<td>
					<%@ include file="../common/deptselector.jsp"%>
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

		setCheckBoxTrue(true);
		setMenuId("${menuId}");
		noticeChanged();
		
		if (operType == 'add') {
			$('#save').val("保存授权");
		}
		if (operType == 'update') {
			$('#save').val("保存授权");
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('power');
		}

		$('#powerType').val('${DisplayData.powerType}');
		
		if (updatedRecordCount > 0) {
			refreshOpenerData();
		}
				
		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 

	function inputCheck() {
		var str = '';

		str = $('#userName').val();
		if (!inputStrCheck(str, "用户", -1, 7, false, true)) {
			return false;
		}
		str = $('#roleName').val();
		if (!inputStrCheck(str, "角色", -1, 7, false, true)) {
			return false;
		}
		str = $('#powerType').val();
		if (!inputStrCheck(str, "授权方式", 1, 3, false, true)) {
			return false;
		}
		$('#unitId').val(getCheckedId());
		str = $('#unitId').val();
		if (!inputStrCheck(str, "单位", -1, 7, false, true)) {
			return false;
		}

		return true;	
	}

	function saveUpdate() {
		if (inputCheck()) {
			if (confirm("确定要保存吗？")) {
				$('#form').attr("action", "${ctx}/power?methodtype=add");
				$('#form').submit();
			}
		}	
	}

	function noticeChanged() {

		if ($('#roleName').val() != '' && $('#userName').val() != '') {
			setInitDeptUrl("${ctx}/power?methodtype=getUnitList&userId=" + $('#userId').val() + "&roleId=" + $('#roleId').val());
			//setLaunchNaviUrl("${ctx}/power/getUnitList?");
			loadData();
		} else {
			if (!isTreeEmpty()) {
				setInitDeptUrl("${ctx}/power?methodtype=getUnitList&userId=" + $('#userId').val() + "&roleId=" + $('#roleId').val());
				loadData();
			}
		}
	}
	
	function selectUser() {
		callUserSelect("userId", "userName");
	}

	function selectRole() {
		callRoleSelect("roleId", "roleName");
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
	
	function refreshOpenerData() {
		if (operType == 'add' || operType == 'update') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}
</script>
</html>