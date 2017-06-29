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
<link rel="stylesheet" type="text/css" href="${ctx}/css/main2.css">
<!-- 
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
 -->
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/js/inputcheck.js"></script>
<script type="text/javascript" src="${ctx}/js/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.MetaData.js"></script>
<script type="text/javascript" src="${ctx}/js/dataTables.tableTools.js"></script>
<script type="text/javascript" src="${ctx}/js/dataTables.select.js"></script>
<script type="text/javascript" src="${ctx}/plugins/validate/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/plugins/validate/localization/messages_zh.js" charset="utf8"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/dataTables.tableTools.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/all.css" />
<script type="text/javascript" src="${ctx}/js/business.js"></script>
<script type="text/javascript" src="${ctx}/js/dataTables.fixedColumns.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/fixedColumns.dataTables.min.css" />
<script type="text/javascript" src="${ctx}/js/jquery.jqprint.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/plugins/toastMessage/resources/css/jquery.toastmessage.css" />
<script type="text/javascript" src="${ctx}/plugins/toastMessage/javascript/jquery.toastmessage.js"></script>

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
	function callDicTypeSelect(dicTypeControl, dicTypeControlView, type, dicTypeId, treeType, parentIndex) {
		//popupWindow("selectdictype", "${ctx}" + "/common/selectDicTypePopActionInit?dicControl=" + dicTypeControl + "&dicControlView=" + dicTypeControlView + "&type=" + type + "&dicTypeId=" + dicTypeId + "&treeType=0", 800, 600);
		var url = "${ctx}" + "/common/selectDicTypePopActionInit?dicControl=" + dicTypeControl + "&dicControlView=" + dicTypeControlView + "&type=" + type + "&dicTypeId=" + dicTypeId + "&treeType=0&index=" + parentIndex;
		openLayer(url, $(document).width() - 500, 500, true, dicTypeControl, dicTypeControlView);
	}
	
	function openLayer(url, width, height, isParentOpen) {
		if (width == "") {
			//width = '900px';
			width = $(document).width() + 'px';
		} else {
			width = width + 'px';
		}
		if (height == "") {
			height = '450px'
		} else {
			height = height + 'px';
		}
		if (isParentOpen) {
			parent.layer.open({
				type : 2,
				title : false,
				area : [ width, height ], 
				scrollbar : false,
				title : false,
				content : url
			});
		} else {
			layer.open({
				type : 1,
				title : false,
				area : [ width, height ], 
				scrollbar : false,
				title : false,
				content : url
			});
		}
	}
	
	function addValidator() {
	    jQuery.validator.addMethod("mobile",function(value, element){ 
	    	var rtnValue = true;
	    	if (value != '') {
	    		rtnValue = checkTelphone(value);
	    	}
	        return rtnValue;  
	    }, "手机号码不正确"); 
	    
	    jQuery.validator.addMethod("phone",function(value, element){
	    	var rtnValue = true;
	    	if (value != '') {
	    		rtnValue = checkPhoneNumber(value);
	    	}
	        return rtnValue;   
	    }, "电话号码不正确"); 
	    
	    jQuery.validator.addMethod("identifycard",function(value, element){  
	    	var rtnValue = true;
	    	if (value != '') {
	    		rtnValue = checkIdcard(value);
	    	}
	        return rtnValue;
	    }, "身份证号码不正确"); 
	    	    
	}
	
	function reloadTabWindow() {
		var curTabWin = null;
		/*
		var curTab = parent.$('#_main_center_tabs').tabs('getSelected');
        if (curTab && curTab.find('iframe').length > 0) {
            curTabWin = curTab.find('iframe')[0].contentWindow;
        }
        if ($.isFunction(curTabWin.reload)) {
        	curTabWin.reload();
        }
		*/
		
		var curTab = parent.parent.$('#_main_center_tabs').tabs('getSelected');
        if (curTab && curTab.find('iframe').length > 0) {
            curTabWin = curTab.find('iframe')[0].contentWindow;
        }
        
        if ($.isFunction(curTabWin.reload)) {
        	curTabWin.reload();
        }
	}
	
	function reloadTabWindowWithNodeChangeNotice(nodeInfo, isParent) {
		var curTabWin = null;
		var curTabWinTree = null;
		var curTab;
		/*
		var curTab = parent.$('#_main_center_tabs').tabs('getSelected');
        if (curTab && curTab.find('iframe').length > 0) {
            curTabWin = curTab.find('iframe')[0].contentWindow;
        }
        if ($.isFunction(curTabWin.reload)) {
        	curTabWin.reload();
        }
		*/
		if (isParent) {
			var curTab = parent.parent.$('#_main_center_tabs').tabs('getSelected');
	        if (curTab && curTab.find('iframe').length > 0) {
	            curTabWinTree = curTab.find('iframe')[0].contentWindow;
	            curTabWin = curTabWinTree.document.getElementById("mainFrame").contentWindow;
	        }
	        
	        if ($.isFunction(curTabWin.reload)) {
	        	curTabWin.reload();
	        }
	        
	        if ($.isFunction(curTabWinTree.loadData)) {
	        	curTabWinTree.loadData(nodeInfo);
	        }
		} else {
			curTab = parent.$('#_main_center_tabs').tabs('getSelected');
	        if (curTab && curTab.find('iframe').length > 0) {
	            curTabWinTree = curTab.find('iframe')[0].contentWindow;
	            curTabWin = curTabWinTree.document.getElementById("mainFrame").contentWindow;
	        }
	        if ($.isFunction(curTabWin.reload)) {
	        	curTabWin.reload();
	        }
	        if ($.isFunction(curTabWinTree.loadData)) {
	        	curTabWinTree.loadData(nodeInfo);
	        }
		}
	}
	
	function getTabTitle() {
		return top.$('#_main_center_tabs').tabs('getSelected').panel('options').title;
	}
	
	function getDateDiff(date1, date2) {
		var newDate1;
		var newDate2;
		
		if (date1 == "") {
			newDate1 = new Date();
		} else {
			newDate1 = new Date(date1);
		}
		if (date2 == "") {
			newDate2 = new Date();
		} else {
			newDate2 = new Date(date2);
		}		
		
		var dateDiff = Math.ceil((newDate2 - newDate1) / (24 * 60 * 60 * 1000));
		
		if (dateDiff < 0) {
			dateDiff = 0;
		}
		
		return dateDiff;
	}
</Script>

