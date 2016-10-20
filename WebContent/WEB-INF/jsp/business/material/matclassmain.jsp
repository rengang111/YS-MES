<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../../common/common.jsp"%>
<html>
<head>
	<title>	物料分类管理</title>
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden id="operType" name="operType" value='${DisplayData.operType}'>
		<input type=hidden name="userUnitId" id="userUnitId" value="${DisplayData.userUnitId}"/>
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
					上级分类名称：
				</td>
				<td>
					<input type=text name="unitIdName" id="unitIdName" value="${DisplayData.unitIdName}"/>
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
				<td colspan=6 align=right>
					<input type=button name="add" id="add" value="新建子分类" onClick="addUnit()"/>
					<!-- input type=button name="merge" id="merge" value="合并" onClick="mergeUnit()"/ -->>
					<input type=button name="delete" id="delete" value="撤销" onClick="deleteUnit()"/>
				</td>
			</tr>
			<tr>
				<td>序号</td>
				<td>分类编码</td>
				<td>分类名称</td>
				<td>上级单位</td>
				<td>操作</td>
			</tr>
			<c:set var="userUnitId" value="${DisplayData.userUnitId}" />
			<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
				<tr>
					<td>
						<c:choose>
							<c:when test="${value[1] == userUnitId}">
								${value[0]}
							</c:when>
							<c:otherwise>
								${value[0]}<input type=checkbox name="numCheck" id="numCheck" value='${value[1]}' />
							</c:otherwise>
						</c:choose>
						
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
						<a href="javascript:void(0);" title="增加子单位" onClick="callAddSubUnit('${value[1]}');">子单位</a>
						<a href="javascript:void(0);" title="详细信息" onClick="dispUnitDetail('${value[1]}');">详细信息</a>
						<a href="javascript:void(0);" title="修改" onClick="callUpdateUnit('${value[1]}');">修改</a>
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
		$('#form').attr("action", "${ctx}/business/material?methodtype=search");

		var updateRecordCount = parseInt('${DisplayData.updatedRecordCount}');

		if (updateRecordCount > 0) {
			if ($('#operType').val() == 'add') {
				reloadTree('${DisplayData.unitData.parentid}');
			}
			if ($('#operType').val() == 'addsub') {
				reloadTree('${DisplayData.unitData.parentid}');
			}
			if ($('#operType').val() == 'update') {
				reloadTree('${DisplayData.unitData.parentid}');
			}
			if ($('#operType').val() == 'delete') {
				var dataList = '${DisplayData.numCheck}'.split(",");
				for(i = 0; i < dataList.length; i++) {
					removeNode(dataList[i]);
				}
			}
		}

		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 
	function noticeNaviChanged(id, name, isLeaf) {

		$('#unitIdName').val(name);
		$('#search').click();

	}

	function inputCheck() {
		var str = $('#unitIdName').val();
		if (!inputStrCheck(str, "单位名称", 100, 7, true, true)) {
			return false;
		}

		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			$('#form').attr("action", "${ctx}/business/material?methodtype=search");
			$('#form').submit();
		}
	}

	function addUnit() {
		$('#operType').val("add");
		popupWindow("UnitDetail", "${pageContext.request.contextPath}/business/material?methodtype=updateinit&operType=add", 800, 600);	
	}
	
	function deleteUnit() {
		
		$('#operType').val("delete");
		
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				$('#form').attr("action", "${ctx}/business/material?methodtype=delete");
				$('#form').submit();
			}
		} else {
			alert("请至少选择一个菜单项");
		}
	}

	function callAddSubUnit(unitId) {
		$('#operType').val("addsub");
		popupWindow("UnitDetail", "${pageContext.request.contextPath}/business/business/material?methodtype=updateinit&operType=addsub&unitId=" + unitId, 800, 600);	
	}
	
	function dispUnitDetail(unitId) {
		popupWindow("UnitDetail", "${pageContext.request.contextPath}/business/material?methodtype=detail&unitId=" + unitId, 800, 600);
	}

	function callUpdateUnit(unitId) {
		$('#operType').val("update");
		popupWindow("UnitDetail", "${pageContext.request.contextPath}/business/material?methodtype=updateinit&operType=update&unitId=" + unitId, 800, 600);	
	}

	function mergeUnit(unitId) {
		alert("等待实装...");
	}

	function removeNode(nodeId) {
		window.parent.removeNode(nodeId);
	}	
	function updateNode(parentNodeId, nodeId, text, icon) {
		//window.parent.updateNode(nodeId, text, icon);
		window.parent.removeNode(nodeId);
		window.parent.addNode(parentNodeId, nodeId, text, icon);
	}
	function addNode(parentNodeId, id, text, icon) {
		window.parent.addNode(parentNodeId, id, text, icon);
	}
</script>
</html>