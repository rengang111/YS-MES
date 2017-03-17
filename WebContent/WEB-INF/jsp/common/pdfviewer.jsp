<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>

<%@ include file="./common.jsp"%>
<script type="text/javascript" src="${ctx}/js/jquery.media.js"></script>
<title>Online View PDF</title>  

<script>
$(function() { 
	//$('a.media').media({width:$(window).width() - 100, height:$(window).height() - 100});  
	var width = $(window).width() - 100;
	var height = $(window).height() - 50;
	$('.media').media( { width:width, height:height, autoplay: true } );
});
</script>  
</head>  
   
<body>  
<a class="media" href="${ctx}${pdfPath}">PDF File</a>  
</body>  

<button type="button" id="return" class="DTTT_button" style="height:25px;margin:0px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>

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