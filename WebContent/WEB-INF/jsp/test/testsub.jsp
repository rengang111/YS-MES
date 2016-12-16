<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>

</head>
<body class="panel-body">
	jyyjyyjyy
	<input type=text name="selCheck" id="selCheck" />
	<input type=button onclick="dotest();"/><br>
	通知次数：<input type=text name="noticelabel" id="noticelabel" readonly=true />
</body>

<script>
	$(function(){ 
		node = $('#naviTree', parent.document).tree('find', '001');
		alert(node);
		alert(node.text);
	});

	var noticeTime = 0;
	function noticeNaviChanged() {
		//alert("this message is from mainFrame! " + $('#selCheck').val());
		$('#noticelabel').val(++noticeTime);
	}
</script>
</html>