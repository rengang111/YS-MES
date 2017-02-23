<%@ page language="java" pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="${ctx}/jsp/ckfinder/ckfinder.js"></script>  
<title>文档管理</title>  
</head>  
<body>  
<script type="text/javascript">  
    var finder = new CKFinder();  
    finder.basePath = 'ckfinder/';  
    finder.create();  
</script>  
</body>  
</html> 