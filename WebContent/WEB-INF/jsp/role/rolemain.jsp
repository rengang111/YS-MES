<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	角色管理
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden id="operType" name="operType" value='${DisplayData.operType}'>
		<!-- 翻页start -->
		<input type=hidden name="startIndex" id="startIndex" value=""/>
		<input type=hidden name="flg" id="flg" value="11111"/>
		<input type=hidden name="turnPageFlg" id="turnPageFlg" value=""/>
		<input type=hidden name="sortFieldList" id="sortFieldList" value="${DisplayData.sortFieldList}"/>
		<input type=hidden name="totalPages" value="${DisplayData.totalPages}"/>
		<!-- 翻页end -->

		<table>
			<tr>
				<td>
					菜单：
				</td>
				<td colspan=3>
					<input type=text name="menuIdName" id="menuIdName" value="${DisplayData.menuIdName}"/>
				</td>
			</tr>
			<tr>
				<td>
					角色名称：
				</td>
				<td>
					<input type=text name="roleIdName" id="roleIdName" value="${DisplayData.roleIdName}"/>
				</td>
			</tr>
			<tr>
				<td>
					关联用户：
				</td>
				<td>
					<input type=text name="userIdName" id="userIdName" value="${DisplayData.userIdName}"/>
				</td>
			</tr>
			<tr>
				<td colspan=4>
					<input type=button name="search" id="search" value="查询" onClick="doSearch()"/>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td colspan=9 align=right>
					<input type=button name="author" id="author" value="授权" onClick="setPower('')"/>
					<input type=button name="add" id="add" value="增加" onClick="addrole()"/>
					<input type=button name="delete" id="delete" value="删除" onClick="deleterole()"/>
				</td>
			</tr>
			<tr>
				<td>序号</td>
				<td>角色名称</td>
				<td>关联用户</td>
				<td>是否管理员</td>
				<td>创建单位</td>
				<td>创建人</td>
				<td>创建时间</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
				<tr>
					<td>
						${value[0]}<input type=checkbox name="numCheck" id="numCheck" value='${value[1]}' />
					</td>
					<td>
						${value[2]}
					</td>
					<td>
						${value[4]}
					</td>							
					<td>
						${value[3]}
					</td>
					<td>
						${value[5]}
					</td>
					<td>
						${value[6]}
					</td>
					<td>
						${value[7]}
					</td>									
					<td>
						<a href="javascript:void(0);" title="详细信息" onClick="dispRoleDetail('${value[1]}');">详细信息</a>
						<a href="javascript:void(0);" title="修改" onClick="callUpdateRole('${value[1]}');">修改</a>
						<a href="javascript:void(0);" title="菜单" onClick="callMenu('${value[1]}', '${value[2]}');">菜单</a>
						<a href="javascript:void(0);" title="关联用户" onClick="dispRelationUser('${value[1]}');">关联用户</a>
						<a href="javascript:void(0);" title="授权" onClick="setPower('${value[1]}');">授权</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		${DisplayData.turnPageHtml}
	</form>

</body>

<script>
	$(function(){
		$('#form').attr("action", "${ctx}/role?methodtype=search");

		var updateRecordCount = parseInt('${DisplayData.updatedRecordCount}');

		if ('${DisplayData.message}' != '' && '${DisplayData.operType}' != 'add') {
			alert('${DisplayData.message}');
		}
		
	}); 

	function noticeNaviChanged(id, name, isLeaf) {
		$('#menuIdName').val(name);
		doSearch();
	}
	
	function inputCheck() {
		var str = $('#menuIdName').val();
		if (!inputStrCheck(str, "菜单", 30, 7, true, true)) {
			return false;
		}
		
		str = $('#roleIdName').val();
		if (!inputStrCheck(str, "角色名称", 60, 7, true, true)) {
			return false;
		}
		
		str = $('#userIdName').val();
		if (!inputStrCheck(str, "关联用户", 32, 7, true, true)) {
			return false;
		}
		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			$('#form').attr("action", "${ctx}/role?methodtype=search");
			$('#form').submit();
		}
	}

	function addrole() {
		$('#operType').val("add");
		popupWindow("roleDetail", "${ctx}/role?methodtype=updateinit&operType=add", 800, 600);	
	}
	
	function deleterole() {
		
		$('#operType').val("delete");
		
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				$('#form').attr("action", "${ctx}/role?methodtype=delete");
				$('#form').submit();
			}
		} else {
			alert("请至少选择一个角色项");
		}
	}
	
	function dispRoleDetail(roleId) {
		popupWindow("roleDetail", "${ctx}/role?methodtype=detail&roleId=" + roleId, 800, 600);
	}

	function callUpdateRole(roleId) {
		$('#operType').val("update");
		popupWindow("roleDetail", "${ctx}/role?methodtype=updateinit&operType=update&roleId=" + roleId, 800, 600);	
	}

	function dispRelationUser(roleId) {
		popupWindow("roleRelationUser", "${ctx}/role?methodtype=rolerelationuser&roleId=" + roleId, 800, 600);
	}
	
	function callMenu(roleId, roleName) {
		popupWindow("roleDetail", "${pageContext.request.contextPath}/rolemenu?methodtype=updateinit&roleId=" + roleId + "&roleName=" + roleName + "&menuId=", 800, 600);	
	}

	function setPower(roleId) {
		if (roleId != '') {
			popupWindow("PowerDetail", "${ctx}/power?methodtype=updateinit&operType=addviarole&roleId=" + roleId, 800, 600);
		} else {
			var roleIdList = "";
			var isFirst = true;
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					if (isFirst) {
						isFirst = false;
						roleIdList = $(this).val();
					} else {
						roleIdList += "," + $(this).val();
					}
				}
			});
			if (roleIdList != "") {
				popupWindow("PowerDetail", "${ctx}/power?methodtype=updateinit&operType=addviarole&roleId=" + roleIdList, 800, 600);
			} else {
				alert("请至少选择一个角色项");
			}
		}
	}

</script>
</html>