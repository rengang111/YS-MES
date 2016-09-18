<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>

</head>

<script>
	$(function(){ 
		setMenuId("001");
		setMainFrameSrc("${ctx}/jsp/test/testsub.jsp");
		//setNaviObj("selCheck");
		setInitNaviUrl("mainframe/initDept");
		setLaunchNaviUrl("mainframe/launchDept");
		loadData();

	}); 
</script>

<body>

	<table>
		<tr>
			<td>
				代码名称：<input type=text/>
			</td>
		</tr>
		<tr>
			<td>
				上级代码：<input type=text/>
			</td>
		</tr>
	</table>
	<%@ include file="../common/navitree.jsp"%>
</body>


</html>