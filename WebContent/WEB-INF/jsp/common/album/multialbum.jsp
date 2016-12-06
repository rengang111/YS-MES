<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugins/jquery-auto-play-image/css/datouwang.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/jquery-auto-play-image/js/koala.min.1.5.js"></script>

<%@ include file="../../common/common.jsp"%>

<!-- 代码 开始 -->
<div id="fsD-${DisplayData.folderName}" class="focus" style="margin:0px 0px 6px 0px;">  
	
    <div id="D1pic-${DisplayData.folderName}" class="fPic">  
    
    	<c:if test="${empty DisplayData.filenames}">
			<div class="fcon" style="display: none;">
	            <a  href="#"><img src="${pageContext.request.contextPath}/images/blankDemo.png" /></a>
	            <a  href="#"></a>
	        </div>
	        
		</c:if>	
		
		<c:forEach var="filename" items="${DisplayData.filenames}">
		
			<div class="fcon" style="display: none;">
	            <a  href="#"><img src="${pageContext.request.contextPath}/${DisplayData.path}${DisplayData.imageKey}/small/${filename}" 
	            			onclick = "showRealPhoto('${DisplayData.imageKey}', '${filename}');" />
	            			<span style="position: absolute; top: 3; left: 3;">
	            				<c:if test="${filename eq DisplayData.nowUseImage}">
									<img src="${pageContext.request.contextPath}/images/now_use.jpg"/></span> 
								</c:if>
	            				</a>
	        
	        </div>
	        
		</c:forEach>
		
    </div>
    <div class="fbg">  
    <div class="D1fBt" id="D1fBt-${DisplayData.folderName}">  
    
    	<c:if test="${empty DisplayData.filenames}">	
    				
			<a href="javascript:void(1)" hidefocus="true" target="_self" class=""><i>1</i></a>  
	        
		</c:if>	
    
    	<c:set var="i" value="0"></c:set>
    	<c:forEach var="filename" items="${DisplayData.filenames}">
    	
    		<c:set var="i" value="${i+1}"></c:set>
        	<a href="javascript:void(${i})" hidefocus="true" target="_self" class=""><i>${i}</i></a>  
        
        </c:forEach>
        
    </div>  
    </div>  
    <span class="prev"></span>   
    <span class="next"></span>  
    
</div>  

 <div id="l1-${DisplayData.folderName}" style="height:210px;">					
	<div style="position:absolute;top:215px;left:175px;"> 
		<a href="javascript:void(0);" class="a-btn-green" style="height:25px;" onclick="return showLayer();">
			<img src="${pageContext.request.contextPath}/images/action_add.png" height="16px" style="top:5px;"/>
			<span class="a-btn-text" >图片上传</span> 
		</a>
	</div>
				
</div>  

<!-- 代码 结束 -->

<div style="text-align:center;clear:both;">

<script type="text/javascript">
	
	function refresh(){		
		location.reload();
	}
	
	function showRealPhoto(key, fileName){
		
		//alert(src);
		
		//fileName = fileName.replace(/[\/]/g, "-").replace('.',','); 
		
		//alert(src.indexOf('blankDemo'));
		
		if (fileName.indexOf('blankDemo')>0)
			return;
		
		var content = '${pageContext.request.contextPath}/album/album-photo-show?className=' + '${DisplayData.className}' + '&key=' + key + '&fileName=' + fileName;
		
		layer.open({
			type : 2,
			title : false,
			area : [ '690px', '560px' ],
			scrollbar : false,
			content : content
		});
		
	}
	
	function showLayer(){
		
		layer.open({
			type : 2,
			title : false,
			area : [ '770px', '210px' ],
			scrollbar : false,
			title : false,
			content : '${pageContext.request.contextPath}/album/album-upload-init?key=' + '${DisplayData.keyBackup}' + '&info=' + '${DisplayData.projectId},${DisplayData.folderName},${DisplayData.className}'
		});
		
	}	

	Qfast.add('widgets', { path: "${pageContext.request.contextPath}/plugins/jquery-auto-play-image/js/terminator2.2.min.js", type: "js", requires: ['fx'] });  
	Qfast(false, 'widgets', function () {
		K.tabs({
			id: 'fsD-${DisplayData.folderName}',   //焦点图包裹id  
			conId: "D1pic-${DisplayData.folderName}",  //** 大图域包裹id  
			tabId:"D1fBt-${DisplayData.folderName}",  
			tabTn:"a",
			conCn: '.fcon', //** 大图域配置class       
			auto: 0,   //自动播放 1或0
			//effect: 'fade',   //效果配置
			eType: 'click', //** 鼠标事件
			pageBt:true,//是否有按钮切换页码
			bns: ['.prev', '.next'],//** 前后按钮配置class                          
			interval: 3000  //** 停顿时间  
		}) 
	})  
</script>

</div>