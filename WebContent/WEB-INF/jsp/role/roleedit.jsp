<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	角色信息维护
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>

		<table>
			<tr>
				<td>
					角色名称：
				</td>
				<td>
					<input type=hidden name="roleData.roleid" id="roleid" value="${DisplayData.roleData.roleid}"/>
					<input type=text name="roleData.rolename" id="rolename" value="${DisplayData.roleData.rolename}"/>
				</td>
			</tr>
			<tr>
				<td>
					角色类型：
				</td>
				<td>
					<select name="roleData.roletype" id="roletype">
						<c:forEach items="${DisplayData.roleTypeList}" var="value" varStatus="status">
							<option value ="${value[0]}">${value[1]}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					创建单位：
				</td>
				<td>
					<input type=text name="unitName" id="unitName" value="${DisplayData.unitName}" readonly/>
				</td>
			</tr>
			<tr>
				<td>
					创建人：
				</td>
				<td>
					<input type=text name="userName" id="userName" value="${DisplayData.userName}" readonly/>
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
			
		if (operType == 'add' || operType == 'addsub') {
			$('#save').val("保存增加");
		}
		if (operType == 'update') {
			if (updatedRecordCount > 0 ) {
				if ('${DisplayData.message}' != '') {
					alert('${DisplayData.message}');
				}
				refreshOpenerData();
				closeWindow("1");
			} else {
				$('#save').val("保存修正");
				$('#roletype').attr('disabled', 'disabled');
			}
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('role');
		}
		
		if (updatedRecordCount > 0) {
			refreshOpenerData();
		}

		$('#roletype').val('${DisplayData.roleData.roletype}');
		
		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 

	function inputCheck() {
		var str = '';
		
		str = $('#rolename').val();
		if (!inputStrCheck(str, "角色名称", 60, 7, false, true)) {
			return false;
		}
		
		str = $('#roletype').val();
		if (!inputStrCheck(str, "角色类型", 3, 3, false, true)) {
			return false;
		}

		if (!checkRoleName()) {
			return false;
		}
		
		return true;
	}

	function saveUpdate() {
		if (inputCheck()) {
			if (confirm("确定要保存吗？")) {
				if (operType == 'add') {
					$('#form').attr("action", "${ctx}/role?methodtype=add");
				} else {
					$('#form').attr("action", "${ctx}/role?methodtype=update");
				}
				$('#form').submit();
			}
		}	
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

	function checkRoleName() {
		var result = false;
		jQuery.ajax({
			type : 'POST',
			async: false,
			contentType : 'application/json',
			dataType : 'json',
			data : $('#rolename').val() + '&' + $('#roleid').val(),
			url : "${ctx}/role?methodtype=checkRoleName",
			success : function(data) {
				if (!data.success) {
					alert(data.message);
				} else {
					result = true;
				}
			},
			 error:function(XMLHttpRequest, textStatus, errorThrown){
             }
		}); 

		return result;
	}
	
</script>
</html>