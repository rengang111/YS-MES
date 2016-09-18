<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../common/common.jsp"%>
<head>
	用户选择
</head>
<body>

<script>
var srcValue = "";
$(document).ready(function(){

	$("form")[0].action = "${pageContext.request.contextPath}/common/" + "selectUserPopActionSearch";
	
	srcValue = window.opener.document.getElementById($('#userControl').val()).value;
	var selectedUserList = window.opener.document.getElementById($('#userControl').val()).value.split(",");
	for (var i = 0; i < selectedUserList.length; i++) {
		$("input[name='selectUser']").each(function(){
			var data = $(this).val().split(",");
			if (data[0] == selectedUserList[i]) {
				$(this).prop('checked', true);
			}
		});
	}
	
});

function closeWindow() {
	var selUserNameList = "";
	var selUserIdList = "";
	var isFirst = true;
	if(window.opener) { 
		$("input[name='selectUser']").each(function(){
			if ($(this).prop('checked') == true) {
				var data = $(this).val().split(",");
				if (isFirst) {
					isFirst = false;
					selUserIdList = data[0];
					selUserNameList = data[1];
				} else {
					selUserIdList = selUserIdList + "," + data[0];
					selUserNameList = selUserNameList + "," + data[1];
				}
			}
		});	
		
		window.opener.document.getElementById($('#userControl').val()).value = selUserIdList;
		window.opener.document.getElementById($('#userControlView').val()).value = selUserNameList;

		if (typeof(window.opener.noticeChanged) != undefined) {
			if (srcValue != selUserIdList) {
				window.opener.noticeChanged();
			}
		}
		
		self.opener = null;
		self.close();
	}
}
function doSearch() {

	$("form")[0].action = "${pageContext.request.contextPath}/common/" + "selectUserPopActionSearch";

	$("form")[0].submit();
}
function doSelect(type) {
	$("input[name='selectUser']").each(function(){
		if (type == '0') {
			$(this).prop('checked', true);
		} else {
			$(this).prop('checked', false);
		}
	});	
}
</script>

<form id="form" modelAttribute="dataModel" action="" method="post">
	<input type="hidden" name="userControl" id="userControl" value="${DisplayData.userControl}" />
	<input type="hidden" name="userControlView" id="userControlView" value="${DisplayData.userControlView}" />
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
			用户登录id或名称：
		</td>
		<td>
			<input type=text maxlength=32 name = "userIdName" id="userIdName" value="${DisplayData.userIdName}"/>
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
			用户id
		</td>		
		<td>
			用户名称
		</td>
		<td>
			性别
		</td>
		<td>
			职务
		</td>		
		<td>
			单位名称
		</td>
		<td>
			手机号码
		</td>
		<td>
			简拼
		</td>
	</tr>
	<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
		<tr>
			<c:forEach items="${value}" var="subValue" varStatus="colStatus">
				<c:if test="${colStatus.index == 0}">
					<td>
						<input type=checkbox name="selectUser" value='${value[1]},${value[2]}' />
					</td>
				</c:if>
				<c:if test="${colStatus.index >= 2 && colStatus.index <= 8}">
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