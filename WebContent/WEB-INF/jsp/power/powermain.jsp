<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	用户权限管理
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
					单位名称：
				</td>
				<td>
					<input type=text name="unitIdName" id="unitIdName" value="${DisplayData.unitIdName}"/>
				</td>
			</tr>
			<tr>
				<td>
					用户名称：
				</td>
				<td>
					<input type=text name="userIdName" id="userIdName" value="${DisplayData.userIdName}"/>
				</td>
			</tr>
			<tr>
				<td>
					角色：
				</td>
				<td>
					<input type=text name="roleIdName" id="roleIdName" value="${DisplayData.roleIdName}"/>
					<input type=hidden name="roleId" id="roleId"/>
					<input type=button name="roleSelect" id="roleSelect" value="选择" onClick="selectRole()"/>
				</td>
			</tr>				
			<tr>
				<td colspan=2 align=right>
					<input type=button name="search" id="search" value="查询" onClick="doSearch()"/>
				</td>
			</tr>
				<tr>
					<td align=right>
						<input type=button name="authorize" id="authorize" value="授权" onClick="addPower()"/>
						<input type=button name="delete" id="delete" value="删除" onClick="deletePower('')"/>
					</td>
				</tr>			
		</table>
		
		<div id="tableMode">
			<table>
				<tr>
					<td>序号</td>
					<td>单位名称</td>
					<td>用户名称</td>
					<td>角色</td>
					<td>是否管理员</td>
					<td>操作</td>
				</tr>
				<c:set var="userId" value="${DisplayData.userId}" />
				<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
					<tr>
						<td>
							${value[0]}<input type=checkbox name="numCheck" id="numCheck" value='${value[1]}' />
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
							<a href="javascript:void(0);" title="修改" onClick="updatePower('${value[1]}');">授权</a>
							<a href="javascript:void(0);" title="删除" onClick="deletePower('${value[1]}');">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<br>
		${DisplayData.turnPageHtml}
	</form>

</body>

<script>
	$(function(){
		$('#form').attr("action", "${ctx}/power?methodtype=search");
		
		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	});

	function noticeNaviChanged(id, name, isLeaf) {

		$('#unitIdName').val(name);
		doSearch();
	}
	
	function inputCheck() {
		var str = $('#unitIdName').val();
		if (!inputStrCheck(str, "单位名称", 100, 7, true, true)) {
			return false;
		}
		str = $('#userIdName').val();
		if (!inputStrCheck(str, "用户名称", 20, 7, true, true)) {
			return false;
		}
		str = $('#roleIdName').val();
		if (!inputStrCheck(str, "角色", 60, 7, true, true)) {
			return false;
		}		
		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			$('#form').attr("action", "${ctx}/power?methodtype=search");
			$('#form').submit();
		}
	}

	function addPower() {
		$('#operType').val("add");
		popupWindow("PowerDetail", "${ctx}/power?methodtype=updateinit&operType=add", 800, 600);	
	}
	
	function deletePower(id) {
		
		$('#operType').val("delete");
		
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if (id != '') {
				if ($(this).val() == id) {
					$(this).prop('checked', true);
					isAnyOneChecked = true;
				} else {
					$(this).prop('checked', false);
				}
			} else {
				if ($(this).prop('checked')) {
					isAnyOneChecked = true;
				}
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				$('#form').attr("action", "${ctx}/power?methodtype=delete");
				$('#form').submit();
			}
		} else {
			alert("请至少选择一个菜单项");
		}
	}

	function updatePower(id) {
		$('#operType').val("update");
		popupWindow("PowerDetail", "${pageContext.request.contextPath}/power?methodtype=updateinit&operType=update&id=" + id, 800, 600);	
	}

	function selectRole() {
		callRoleSelect("roleId", "roleIdName");
	}

	function noticeChanged() {

	}
</script>
</html>