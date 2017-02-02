<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../../common/common.jsp"%>
<html>
<head>
	<title>物料分类管理</title>
</head>

<script>
	$(function(){ 
		setCheckBoxTrue(false);
		//setMenuId("001");
		setMainFrameSrc("${ctx}/business/matcategory?methodtype=initframe");
		//setNaviObj("selCheck");
		setInitNaviUrl("mainframe/initMaterial");
		setLaunchNaviUrl("mainframe/launchMaterial");
		//alert(111)
		//节点上的每个点击动作都通知给父窗口
		setClickNoCheckFlg(true);
		loadData();
	}); 
</script>

<body>
	<%@ include file="../../common/navitree.jsp"%>
</body>


</html>