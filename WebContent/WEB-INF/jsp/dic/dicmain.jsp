<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	代码管理
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
					<input type=hidden name="dicTypeId" id="dicTypeId"/>
					<input type=button name="dictypeSelector" id="dictypeSelector" value="选择" onClick="selectDicType();" />
				</td>
			</tr>
			<tr>
				<td>
					代码名称：
				</td>
				<td>
					<input type=text name="dicIdName" id="dicIdName" value="${DisplayData.dicIdName}"/>
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
					<input type=button name="add" id="add" value="增加" onClick="addDicCode()"/>
					<input type=button name="delete" id="delete" value="删除" onClick="deleteDicCode()"/>
				</td>
			</tr>
			<tr>
				<td>序号</td>
				<td>代码类ID</td>
				<td>代码类名称</td>
				<td>代码类属性</td>
				<td>代码录入层次</td>
				<td>代码ID</td>
				<td>代码名称</td>
				<td>上级代码</td>
				<td>操作</td>
			</tr>

			<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
				<tr>
					<td>
						${value[0]}<input type=checkbox name="numCheck" id="numCheck" value='${value[5]}' />
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
						<a href="javascript:void(0);" title="增加子代码" onClick="callAddSubDicCode('${value[5]}', '${value[1]}');">增加子代码</a>
						<a href="javascript:void(0);" title="详细信息" onClick="dispDicCodeDetail('${value[5]}', '${value[1]}');">详细信息</a>
						<a href="javascript:void(0);" title="修改" onClick="callUpdateDicCode('${value[5]}', '${value[1]}');">修改</a>
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
		$('#form').attr("action", "${ctx}/diccode?methodtype=search");

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

		str = $('#dicIdName').val();
		if (!inputStrCheck(str, "代码ID", 60, 7, true, true)) {
			return false;
		}
		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			$('#form').attr("action", "${ctx}/diccode?methodtype=search");
			$('#form').submit();
		}
	}

	function deleteDicCode() {
		
		$('#operType').val("delete");
		
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				$('#form').attr("action", "${ctx}/diccode?methodtype=delete");
				$('#form').submit();
			}
		} else {
			alert("请至少选择一个项目");
		}
	}
	
	function dispDicCodeDetail(dicCodeId, dicTypeId) {
		popupWindow("DicCodeDetail", "${pageContext.request.contextPath}/diccode?methodtype=detail&dicCodeId=" + dicCodeId + "&dicTypeId=" + dicTypeId, 800, 600);
	}

	function addDicCode() {
		$('#operType').val("add");
		popupWindow("DicCodeDetail", "${pageContext.request.contextPath}/diccode?methodtype=updateinit&operType=add", 800, 600);	
	}
		
	function callAddSubDicCode(dicCodeId, dicTypeId) {
		$('#operType').val("addsub");
		popupWindow("DicCodeDetail", "${pageContext.request.contextPath}/diccode?methodtype=updateinit&operType=addsub&dicCodeId=" + dicCodeId + "&dicTypeId=" + dicTypeId, 800, 600);	
	}

	function callUpdateDicCode(dicCodeId, dicTypeId) {
		$('#operType').val("update");
		popupWindow("DictypeDetail", "${pageContext.request.contextPath}/diccode?methodtype=updateinit&operType=update&dicCodeId=" + dicCodeId + "&dicTypeId=" + dicTypeId, 800, 600);	
	}

	function selectDicType() {
		callDicTypeSelect("dicTypeId", "dicTypeIdName", "0", "", "");
	}
</script>
</html>