<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	操作日志查询
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
					单位：
				</td>
				<td>
					<input type=text name="unitIdName" id="unitIdName" value="${DisplayData.unitIdName}"/>
					<input type=hidden name="unitId" id="unitId" value="${DisplayData.unitId}"/>
					<input type=button name="unitselect" id="unitselect" value="选择单位" onClick="selectUnit();"/>
				</td>
			</tr>
			<tr>
				<td>
					用户：
				</td>
				<td>
					<input type=text name="userIdName" id="userIdName" value="${DisplayData.userIdName}"/>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
					ip：
				</td>
				<td>
					<input type=text name="ip" id="ip" value="${DisplayData.ip}"/>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
					菜单：
				</td>
				<td>
					<input type=text name="menuIdName" id="menuIdName" value="${DisplayData.menuIdName}"/>
					<input type=hidden name="menuId" id="menuId" value="${DisplayData.menuId}"/>
					<input type=button name="menuselect" id="menuselect" value="选择菜单" onClick="selectMenu();"/>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
					开始时间：
				</td>
				<td>
					<input class="easyui-datetimebox" name="startTime" id="startTime" value="${DisplayData.startTime}"/>
				</td>
				<td>
				</td>
				<td>
					截止时间：
				</td>
				<td>
					<input class="easyui-datetimebox" name="endTime" id="endTime" value="${DisplayData.endTime}"/>
				</td>
				<td>
				</td>
			</tr>			
			<tr>
				<td>
					<input type=button name="search" id="search" value="查询" onClick="doSearch()"/>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td>序号</td>
				<td>单位名称</td>
				<td>用户名称</td>
				<td>ip</td>
				<td>操作菜单类</td>
				<td>操作菜单名称</td>
				<td>URL</td>
				<td>操作时间</td>
				<td>浏览器信息</td>
			</tr>
			<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
				<tr>
					<td>
						${value[0]}						
					</td>
					<td>
						${value[1]}
					</td>
					<td>
						${value[2]}
					</td>
					<td>
						${value[3]}
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

				</tr>
			</c:forEach>
		</table>
		<br>
		${DisplayData.turnPageHtml}
	</form>

</body>

<script>
	$(function(){
		$('#form').attr("action", "${ctx}/operlog?methodtype=search");

		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 

	function inputCheck() {
		//var str = $('#unitIdName').val();
		//if (!inputStrCheck(str, "单位名称", 100, 7, true, true)) {
			//return false;
		//}

		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			$('#form').attr("action", "${ctx}/operlog?methodtype=search");
			$('#form').submit();
		}
	}

	function selectUnit() {
		callUnitSelect("unitId", "unitIdName", "0");
	}

	function selectMenu() {
		callMenuSelect("menuId", "menuIdName", "0");
	}

	function noticeNaviChanged(id, name, isLeaf) {
		$('#menuIdName').val(name);
		//doSearch();
	}
</script>
</html>