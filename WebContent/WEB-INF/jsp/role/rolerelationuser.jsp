<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	角色：${DisplayData.roleIdName}
</head>
<body>

	<table>
		<tr>
			<td>
				用户名称
			</td>
			<td>
				职务
			</td>
			<td>
				所属单位
			</td>
			<td>
				电话
			</td>
			<td>
				手机
			</td>
			<td>
				Email
			</td>
		</tr>

		<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
			<tr>
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
					${value[3]}
				</td>
				<td>
					${value[5]}
				</td>
				<td>
					${value[6]}
				</td>
			</tr>
		
		</c:forEach>
		<tr>
			<td colspan=6>
				<input type=button name="close" id="close"" value="关闭" onClick="closeWindow('')"/>
			</td>
		</tr>
	</table>

</body>

<script>
	function closeWindow() {
		self.opener = null;
		self.close();
	}
</script>
</html>