<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	角色菜单管理
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
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
					<input type=button name="add" id="add" value="增加" onClick="callUpdateRoleMenu('', '', '')"/>
					<input type=button name="delete" id="delete" value="删除" onClick="deleterolemenu()"/>
				</td>
			</tr>
			<tr>
				<td>序号</td>
				<td>角色名称</td>
				<td>菜单</td>
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
						${value[4]}
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
						${value[8]}
					</td>									
					<td>
						<a href="javascript:void(0);" title="修改" onClick="callUpdateRoleMenu('${value[2]}', '${value[4]}', '${value[3]}');">修改</a>
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
		$('#form').attr("action", "${ctx}/rolemenu?methodtype=search");

		if ('${DisplayData.message}' != '') {
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
			$('#form').attr("action", "${ctx}/rolemenu?methodtype=search");
			$('#form').submit();
		}
	}

	function deleterolemenu() {

		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				$('#form').attr("action", "${ctx}/rolemenu?methodtype=delete");
				$('#form').submit();
			}
		} else {
			alert("请至少选择一个角色菜单项");
		}
	}

	function callUpdateRoleMenu(roleId, roleName, menuId) {
		popupWindow("roleDetail", "${pageContext.request.contextPath}/rolemenu?methodtype=updateinit&roleId=" + roleId + "&roleName=" + roleName + "&menuId=" + menuId, 800, 600);	
	}

</script>
</html>