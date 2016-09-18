<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../common/common.jsp"%>
<head>
	角色选择
</head>
<body>

<script>
var srcValue = "";
$(document).ready(function(){

	$("form")[0].action = "${ctx}" +"/common/" + "selectRolePopActionSearch";
	
	srcValue = window.opener.document.getElementById($('#roleControl').val()).value;
	var selectedroleList = window.opener.document.getElementById($('#roleControl').val()).value.split(",");
	for (var i = 0; i < selectedroleList.length; i++) {
		$("input[name='selectrole']").each(function(){
			var data = $(this).val().split(",");
			if (data[0] == selectedroleList[i]) {
				$(this).prop('checked', true);
			}
		});
	}
	
});

function closeWindow() {
	var selroleNameList = "";
	var selroleIdList = "";
	var isFirst = true;
	if(window.opener) { 
		$("input[name='selectrole']").each(function(){
			if ($(this).prop('checked') == true) {
				var data = $(this).val().split(",");
				if (isFirst) {
					isFirst = false;
					selroleIdList = data[0];
					selroleNameList = data[1];
				} else {
					selroleIdList = selroleIdList + "," + data[0];
					selroleNameList = selroleNameList + "," + data[1];
				}
			}
		});	
		
		window.opener.document.getElementById($('#roleControl').val()).value = selroleIdList;
		window.opener.document.getElementById($('#roleControlView').val()).value = selroleNameList;

		if (typeof(window.opener.noticeChanged) != undefined) {
			if (srcValue != selroleIdList) {
				window.opener.noticeChanged();
			}
		}
		
		self.opener = null;
		self.close();
	}
}
function doSearch() {
	$("form")[0].action = "${ctx}" +"/common/" + "selectRolePopActionSearch";
	$("form")[0].submit();
}
function doSelect(type) {
	$("input[name='selectrole']").each(function(){
		if (type == '0') {
			$(this).prop('checked', true);
		} else {
			$(this).prop('checked', false);
		}
	});	
}
</script>

<form id="form" modelAttribute="dataModel" action="" method="post">
	<input type="hidden" name="roleControl" id="roleControl" value="${DisplayData.roleControl}" />
	<input type="hidden" name="roleControlView" id="roleControlView" value="${DisplayData.roleControlView}" />
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
			用户列表
		</td>
		<td>
			<input type=button id="return" value="选择并返回" onClick="closeWindow();"/>
		</td>
	</tr>
	<tr>
		<td>
			角色名称：
		</td>
		<td>
			<input type=text maxlength=60 name = "roleIdName" id="roleIdName" value="${DisplayData.roleIdName}"/>
		</td>
		<td>
			<input type=button id="searchId" value="查询" onClick="doSearch();"/>
		</td>
	</tr>
	<tr>
		<td>
			<input type=button id="selectAll" value="全选选中" onClick="doSelect(0);"/>
			<input type=button id="selectAll" value="全部取消" onClick="doSelect(1);"/>
		</td>
	</tr>
</table>
<div style="OVERFLOW-Y: auto; OVERFLOW-X:hidden;">
<table>
	<tr>
		<td>
			选择
		</td>	
		<td>
			角色名称
		</td>
		<td>
			角色类型
		</td>
	</tr>
	<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
		<tr>
			<c:forEach items="${value}" var="subValue" varStatus="colStatus">
				<c:if test="${colStatus.index == 0}">
					<td>
						<input type=checkbox name="selectrole" value='${value[1]},${value[2]}' />
					</td>
				</c:if>
				<c:if test="${colStatus.index > 1}">
					<td>
						${subValue}
					</td>
				</c:if>	
			</c:forEach>
		</tr>
	</c:forEach>
</table>
	<br>
	${DisplayData.turnPageHtml}
</div>
</body>
</html>