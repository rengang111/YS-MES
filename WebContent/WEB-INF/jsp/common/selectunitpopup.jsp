<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../common/common.jsp"%>

<body>

<script>
$(document).ready(function(){
	setMenuId("${menuId}");
	setUnitObj("${DisplayData.unitControl}");
	setActionControlName("${DisplayData.unitControlView}");
	if ('${DisplayData.treeType}' == '0' || '${DisplayData.treeType}' == '') {
		setCheckBoxTrue(false);
	} else {
		setCheckBoxTrue(true);
	}
	
	loadData();
});

function closeWindow() {
	var selunitNameList = "";
	var selunitIdList = "";
	var isFirst = true;
	if(window.opener) {
		var node;
		var code = "";
		var name = "";
		if ('${DisplayData.treeType}' == '0' || '${DisplayData.treeType}' == '') {
			node = $(naviObj).tree("getSelected");
			if (node) {
				code = node.id;
				name = node.text;
			}
		} else {
			code = getChecked();
			name = getCheckedName();
		}
		
		window.opener.document.getElementById('${DisplayData.unitControl}').value = code;
		window.opener.document.getElementById('${DisplayData.unitControlView}').value = name;

		self.opener = null;
		self.close();
	}
}

</script>
	
	<input type=button id="return" value="选择并返回" onClick="closeWindow();"/>
	<%@ include file="../common/deptselector.jsp"%>
</body>
</html>