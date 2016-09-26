<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt'%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
  var ctx = '${ctx}'; 
</script>
<script type="text/javascript" src="${ctx}/js/jquery-2.1.3.js"></script>
<!-- 
<script type="text/javascript" src="${ctx}/js/jquery-1.11.1.min.js"></script>
 -->
<script type="text/javascript" src="${ctx}/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.fancybox.js"></script>
<script type="text/javascript" src="${ctx}/js/kxbdSuperMarquee.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.similar.msgbox.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/js/inputcheck.js"></script>
<script type="text/javascript" src="${ctx}/js/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.dataTables.js"	charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/js/dataTables.tableTools.js"	charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/js/dataTables.select.js"></script>
<script type="text/javascript" src="${ctx}/plugins/validate/jquery.validate.js"	charset="utf8"></script>
<script type="text/javascript" src="${ctx}/plugins/validate/localization/messages_zh.js" charset="utf8"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/dataTables.tableTools.css" />

<Script>
	function callMenuSelect(menuControl, menuControlView) {
		popupWindow("selectmenu", "${ctx}" + "/common/selectMenuPopActionInit?menuControl=" + menuControl + "&menuControlView=" + menuControlView, 800, 600);
	}
	function callUserSelect(userControl, userControlView) {
		popupWindow("selectuser", "${ctx}" + "/common/selectUserPopActionInit?userControl=" + userControl + "&userControlView=" + userControlView, 800, 600);
	}
	function callRoleSelect(roleControl, roleControlView) {
		popupWindow("selectrole", "${ctx}" + "/common/selectRolePopActionInit?roleControl=" + roleControl + "&roleControlView=" + roleControlView, 800, 600);
	}    
	function callDicSelect(dicControl, dicControlView, dicType, treeType) {
		popupWindow("selectdic", "${ctx}" + "/common/selectDicPopActionInit?dicControl=" + dicControl + "&dicControlView=" + dicControlView + "&dicTypeID=" + dicType + "&treeType=" + treeType, 800, 600);
	}
	function callUnitSelect(unitControl, unitControlView, treeType) {
		popupWindow("selectunit", "${ctx}" + "/common/selectUnitPopActionSearch?unitControl=" + unitControl + "&unitControlView=" + unitControlView + "&treeType=" + treeType, 800, 600);
	}
	//type->0:代码类     1:代码
	function callDicTypeSelect(dicTypeControl, dicTypeControlView, type, dicTypeId, treeType) {
		popupWindow("selectdictype", "${ctx}" + "/common/selectDicTypePopActionInit?dicControl=" + dicTypeControl + "&dicControlView=" + dicTypeControlView + "&type=" + type + "&dicTypeId=" + dicTypeId + "&treeType=0", 800, 600);
		
	}
	function openLayer(url, width, height) {
		if (width == "") {
			width = '900px';
		} else {
			width = width + 'px';
		}
		if (height == "") {
			height = '450px'
		} else {
			height = height + 'px';
		}
		parent.layer.open({
			type : 2,
			title : false,
			area : [ width, height ], 
			scrollbar : false,
			title : false,
			content : url
		});
	}
</Script>

