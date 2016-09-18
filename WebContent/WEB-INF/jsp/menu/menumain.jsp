<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	功能菜单管理
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
					上级功能：
				</td>
				<td colspan=3>
					<input type=text name="parentMenuIdName" id="parentMenuIdName" value="${DisplayData.parentMenuIdName}"/>
				</td>
			</tr>
			<tr>
				<td>
					菜单ID：
				</td>
				<td>
					<input type=text name="menuId" id="menuId" value="${DisplayData.menuId}"/>
				</td>
				<td>
					菜单名称：
				</td>
				<td>
					<input type=text name="menuName" id="menuName" value="${DisplayData.menuName}"/>
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
				<td colspan=6 align=right>
					<input type=button name="add" id="add" value="增加" onClick="addMenu()"/>
					<input type=button name="delete" id="delete" value="删除" onClick="deleteMenu()"/>
				</td>
			</tr>
			<tr>
				<td>序号</td>
				<td>菜单ID</td>
				<td>菜单名称</td>
				<td>上级单位</td>
				<td>URL</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
				<tr>
					<td>
						${value[0]}<input type=checkbox name="numCheck" id="numCheck" value='${value[1]}' />
					</td>
					<td>
						${value[1]}
					</td>
					<td>
						${value[2]}
					</td>
					<td>
						${value[4]}
					</td>							
					<td>
						${value[5]}
					</td>
					<td>
						<a href="javascript:void(0);" title="子菜单" onClick="callAddSubMenu('${value[1]}');">子菜单</a>
						<a href="javascript:void(0);" title="详细信息" onClick="dispMenuDetail('${value[1]}');">详细信息</a>
						<a href="javascript:void(0);" title="修改" onClick="callUpdateMenu('${value[1]}');">修改</a>
						<a href="javascript:void(0);" title="关联角色" onClick="callRelationRole('${value[1]}');">关联角色</a>
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
		$('#form').attr("action", "${ctx}/menu?methodtype=search");

		var updateRecordCount = parseInt('${DisplayData.updatedRecordCount}');

		if (updateRecordCount > 0) {
			if ($('#operType').val() == 'add') {
				reloadTree('${DisplayData.menuData.menuparentid}');
			}
			if ($('#operType').val() == 'addsub') {
				reloadTree('${DisplayData.menuData.menuparentid}');
			}
			if ($('#operType').val() == 'update') {
				reloadTree('${DisplayData.menuData.menuparentid}');
			}
			if ($('#operType').val() == 'delete') {
				var dataList = '${DisplayData.numCheck}'.split(",");
				for(i = 0; i < dataList.length; i++) {
					removeNode(dataList[i]);
				}

				$('#menuId').val("");
				$('#menuName').val("");
			}
		}

		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 
	function noticeNaviChanged(id, name, isLeaf) {

		if (isLeaf) {
			$('#parentMenuIdName').val("");	
			$('#menuId').val(id);
			$('#menuName').val(name);
		} else {
			$('#menuId').val("");
			$('#menuName').val("");
			$('#parentMenuIdName').val(name);	
		}

		doSearch();
	}

	function inputCheck() {
		var str = $('#parentMenuIdName').val();
		if (!inputStrCheck(str, "上级功能", 7, 30, true, true)) {
			return false;
		}
		
		str = $('#menuId').val();
		if (!inputStrCheck(str, "菜单ID", 3, 8, true, true)) {
			return false;
		}
		
		str = $('#menuName').val();
		if (!inputStrCheck(str, "菜单名称", 30, 7, true, true)) {
			return false;
		}
		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			$('#form').attr("action", "${ctx}/menu?methodtype=search");
			$('#form').submit();
		}
	}

	function addMenu() {
		$('#operType').val("add");
		popupWindow("MenuDetail", "${pageContext.request.contextPath}/menu?methodtype=updateinit&operType=add", 800, 600);	
	}
	
	function deleteMenu() {
		
		$('#operType').val("delete");
		
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				$('#form').attr("action", "${ctx}/menu?methodtype=delete");
				$('#form').submit();
			}
		} else {
			alert("请至少选择一个菜单项");
		}
	}

	function callAddSubMenu(menuId) {
		$('#operType').val("addsub");
		popupWindow("MenuDetail", "${pageContext.request.contextPath}/menu?methodtype=updateinit&operType=addsub&menuId=" + menuId, 800, 600);	
		}
	
	function dispMenuDetail(menuId) {
		popupWindow("MenuDetail", "${pageContext.request.contextPath}/menu?methodtype=detail&menuId=" + menuId, 800, 600);
	}

	function callUpdateMenu(menuId) {
		$('#operType').val("update");
		popupWindow("MenuDetail", "${pageContext.request.contextPath}/menu?methodtype=updateinit&operType=update&menuId=" + menuId, 800, 600);	
	}

	function callRelationRole(menuId) {
		popupWindow("roleDetail", "${pageContext.request.contextPath}/rolemenu?methodtype=updateinit&roleId=" + "&roleName=" + "&menuId=", 800, 600);	
	}

	function removeNode(nodeId) {
		window.parent.removeNode(nodeId);
	}	
	function updateNode(nodeId, text, icon) {
		window.parent.updateNode(nodeId, text, icon);
	}
	function addNode(parentNodeId, id, text, icon) {
		window.parent.addNode(parentNodeId, id, text, icon);
	}
</script>
</html>