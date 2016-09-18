<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	用户管理
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden id="operType" name="operType" value='${DisplayData.operType}'>
		<input type=hidden name="userId" id="userId" value="${DisplayData.userId}"/>
		<input type=hidden name="duttiesId" id="duttiesId" value=""/>
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
					职务：
				</td>
				<td>
					<input type=text name="dutiesIdName" id="dutiesIdName" value="${DisplayData.dutiesIdName}"/>
					<input type=button name="dutiesSelect" id="dutiesSelect" value="选择" onClick="selectDuties()"/>
				</td>
			</tr>				
			<tr>
				<td colspan=2 align=right>
					<input type=button name="search" id="search" value="查询" onClick="doSearch()"/>
				</td>
			</tr>
				<tr>
					<td>
						<input type="radio" value="0" name="dispMode" id="dispMode" chcked="true" onClick="changeDispMode();">列表模式<br>
						<input type="radio" value="1" name="dispMode" id="dispMode" onClick="changeDispMode();">头像模式
					</td>
					<td align=right>
						<input type=button name="authorize" id="authorize" value="授权" onClick="setPower('')"/>
						<input type=button name="add" id="add" value="增加用户" onClick="addUser()"/>
						<input type=button name="delete" id="delete" value="删除用户" onClick="deleteUser()"/>
						<input type=button name="lock" id="lock" value="锁定" onClick="lockUser()"/>
						<input type=button name="unlock" id="unlock" value="解锁" onClick="unlockUser()"/>
					</td>
				</tr>			
		</table>
		
		<div id="tableMode" style="display:none">
			<table>
				<tr>
					<td>序号</td>
					<td>单位名称</td>
					<td>用户ID</td>
					<td>用户名称</td>
					<td>职务</td>
					<td>操作</td>
				</tr>
				<c:set var="userId" value="${DisplayData.userId}" />
				<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
					<tr>
						<td>
							<c:choose>
								<c:when test="${value[1] == userId}">
									${value[0]}
								</c:when>
								<c:otherwise>
									${value[0]}<input type=checkbox name="numCheck" id="numCheck" value='${value[1]}' />
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							${value[4]}
						</td>
						<td>
							${value[2]}
						</td>
						<td>
							${value[3]}
						</td>
						<td>
							${value[5]}
						</td>					
						<td>
							<a href="javascript:void(0);" title="授权" onClick="setPower('${value[1]}');">授权</a>
							<a href="javascript:void(0);" title="详细信息" onClick="dispUserDetail('${value[1]}');">详细信息</a>
							<a href="javascript:void(0);" title="修改" onClick="callUpdateUser('${value[1]}');">修改</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div id="photoMode" style="display:none">
			<table>
				<c:set var="userId" value="${DisplayData.userId}" />
				<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
					<c:if test="${status.index == 0}">
						<tr>
					</c:if>
					<c:if test="${ status.index > 0 && status.index % 5 == 0}">
						</tr>
						<tr>
					</c:if>
					
					<td>
						<table>
							<tr>
								<td>
									<img src="${ctx}${value[7]}" width=100px height=100px />
								</td>
							</tr>
							
							<c:choose>
								<c:when test="${value[1] == userId}">
									<tr>
										<td>
											<input type=checkbox disabled />
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td>
											<input type=checkbox name="numCheck" id="numCheck" value='${value[1]}' />
										</td>
									</tr>
								</c:otherwise>
							</c:choose>
							<tr>
								<td>
									${value[3]}&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									${value[4]}&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									${value[2]}&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									${value[5]}&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									<a href="javascript:void(0);" title="授权" onClick="setPower('${value[1]}');">授权</a>
									<a href="javascript:void(0);" title="详细信息" onClick="dispUserDetail('${value[1]}');">详细信息</a>
									<a href="javascript:void(0);" title="修改" onClick="callUpdateUser('${value[1]}');">修改</a>
								</td>
							</tr>
						</table>
					</td>
				</c:forEach>
				</tr>
			</table>		
		</div>
		<br>
		${DisplayData.turnPageHtml}
	</form>

</body>

<script>
	$(function(){
		$('#form').attr("action", "${ctx}/user?methodtype=search");

		if ('${DisplayData.dispMode}' == '0' || '${DisplayData.dispMode}' == '') {
			$("input:radio[name='dispMode']").eq(0).attr("checked", true);
			$('#photoMode').hide();
			$('#tableMode').show();			
		} else {
			$("input:radio[name='dispMode']").eq(1).attr("checked", true);
			$('#tableMode').hide();
			$('#photoMode').show();
		}
		
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
		str = $('#dutiesIdName').val();
		if (!inputStrCheck(str, "职务", 60, 7, true, true)) {
			return false;
		}		
		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			$('#form').attr("action", "${ctx}/user?methodtype=search");
			$('#form').submit();
		}
	}

	function addUser() {
		$('#operType').val("add");
		popupWindow("UserDetail", "${pageContext.request.contextPath}/user?methodtype=updateinit&operType=add", 800, 600);	
	}
	
	function deleteUser() {
		
		$('#operType').val("delete");
		
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				$('#form').attr("action", "${ctx}/user?methodtype=delete");
				$('#form').submit();
			}
		} else {
			alert("请至少选择一个菜单项");
		}
	}
	
	function dispUserDetail(userId) {
		popupWindow("UserDetail", "${pageContext.request.contextPath}/user?methodtype=detail&userId=" + userId, 800, 600);
	}

	function callUpdateUser(userId) {
		$('#operType').val("update");
		popupWindow("UserDetail", "${pageContext.request.contextPath}/user?methodtype=updateinit&operType=update&userId=" + userId, 800, 600);	
	}

	function setPower(userId) {
		if (userId != '') {
			popupWindow("PowerDetail", "${pageContext.request.contextPath}/power?methodtype=updateinit&operType=addviauser&userId=" + userId, 800, 600);
		} else {
			var userIdList = "";
			var isFirst = true;
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					if (isFirst) {
						isFirst = false;
						userIdList = $(this).val();
					} else {
						userIdList += "," + $(this).val();
					}
				}
			});
			if (userIdList != "") {
				popupWindow("PowerDetail", "${pageContext.request.contextPath}/power?methodtype=updateinit&operType=addviauser&userId=" + userIdList, 800, 600);
			} else {
				alert("请至少选择一个菜单项");
			}
		}
	}
	function lockUser() {
		$('#operType').val("lock");
		$('#form').attr("action", "${ctx}/user?methodtype=lock");
		$('#form').submit();
	}
	function unlockUser() {
		$('#operType').val("unlock");
		$('#form').attr("action", "${ctx}/user?methodtype=unlock");
		$('#form').submit();		
	}
	function selectDuties() {
		callDicSelect("duttiesId", "dutiesIdName", 'A3', '1' );
	}
	function changeDispMode() {

		if ($('input[name="dispMode"]:checked').val() == '0') {
			$('#photoMode').hide();
			$('#tableMode').show();		
		} else {
			$('#tableMode').hide();
			$('#photoMode').show();	
		}
	}

</script>
</html>