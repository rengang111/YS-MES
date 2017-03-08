<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<%@ include file="./common.jsp"%>

<script type="text/javascript" src="${ctx}/jsp/viewer/js/viewer.min.js"></script>
<link rel="stylesheet" href="${ctx}/jsp/viewer/css/viewer.min.css" />


<button type="button" id="return" class="DTTT_button" style="height:25px;margin:0px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
<ul id="dowebok">
	<li>
		<img src="${ctx}${imagePath}" alt=""/>
	</li>
</ul>


<script>
	var viewer = new Viewer(document.getElementById('dowebok'));
</script>

<script>
function doReturn() {
	//var url = "${ctx}/business/externalsample";
	//location.href = url;	
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	//parent.$('#events').DataTable().destroy();/
	//parent.reload_contactor();
	parent.layer.close(index); //执行关闭	
}

</script>

</html>