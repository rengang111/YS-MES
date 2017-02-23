<%@ page language="java" pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="${ctx}/jsp/ckfinder/ckfinder.js"></script>  
<title>文档管理</title>  
</head>  
<body> 
 
<div>
	<button type="button" id="delete" class="DTTT_button" onClick="doReturn();">返回</button>
</div>

<script type="text/javascript">  
    var finder = new CKFinder();  
    finder.basePath = 'ckfinder/';  
    finder.create();
    
    function doReturn() {
    	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    	parent.layer.close(index); //执行关闭
    }
</script>

</body>  
</html> 