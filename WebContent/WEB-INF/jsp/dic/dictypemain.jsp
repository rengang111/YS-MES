<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	代码类管理
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
					代码类：
				</td>
				<td>
					<input type=text name="dicTypeIdName" id="dicTypeIdName" value="${DisplayData.dicTypeIdName}"/>
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
					<input type=button name="add" id="add" value="增加" onClick="addDictype()"/>
					<input type=button name="delete" id="delete" value="删除" onClick="deleteDictype()"/>
				</td>
			</tr>
			<tr>
				<td>序号</td>
				<td>代码类ID</td>
				<td>代码类名称</td>
				<td>代码类属性</td>
				<td>代码录入层次</td>
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
						${value[3]}
					</td>
					<td>
						${value[4]}
					</td>
					<td>
						<a href="javascript:void(0);" title="增加代码" onClick="callAddSubDictype('${value[1]}', '${value[2]}');">增加代码</a>
						<a href="javascript:void(0);" title="详细信息" onClick="dispDictypeDetail('${value[1]}');">详细信息</a>
						<a href="javascript:void(0);" title="修改" onClick="callUpdateDictype('${value[1]}');">修改</a>
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
		$('#form').attr("action", "${ctx}/dictype?methodtype=search");

		var updateRecordCount = parseInt('${DisplayData.updatedRecordCount}');

		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	});

	function inputCheck() {
		var str = $('#dicTypeIdName').val();
		if (!inputStrCheck(str, "代码类", 60, 7, true, true)) {
			return false;
		}

		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			$('#form').attr("action", "${ctx}/dictype?methodtype=search");
			$('#form').submit();
		}
	}

	function addDictype() {
		$('#operType').val("add");
		popupWindow("DictypeDetail", "${pageContext.request.contextPath}/dictype?methodtype=updateinit&operType=add", 800, 600);	
	}
	
	function deleteDictype() {
		
		$('#operType').val("delete");
		
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				$('#form').attr("action", "${ctx}/dictype?methodtype=delete");
				$('#form').submit();
			}
		} else {
			alert("请至少选择一个项目");
		}
	}

	function callAddSubDictype(dicTypeId, dicTypeName) {
		$('#operType').val("addsubfromtype");
		popupWindow("DictypeDetail", "${pageContext.request.contextPath}/diccode?methodtype=updateinit&operType=addsubfromtype&dicTypeId=" + dicTypeId + "&dicTypeName=" + dicTypeName, 800, 600);	
	}
	
	function dispDictypeDetail(dictypeId) {
		popupWindow("DictypeDetail", "${pageContext.request.contextPath}/dictype?methodtype=detail&dictypeId=" + dictypeId, 800, 600);
	}

	function callUpdateDictype(dictypeId) {
		$('#operType').val("update");
		popupWindow("DictypeDetail", "${pageContext.request.contextPath}/dictype?methodtype=updateinit&operType=update&dictypeId=" + dictypeId, 800, 600);	
	}

</script>
</html>