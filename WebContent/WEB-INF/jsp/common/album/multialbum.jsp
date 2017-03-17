<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugins/jquery-auto-play-image/css/datouwang.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/jquery-auto-play-image/js/koala.min.1.5.js"></script>

<!-- 代码 开始 -->
<div id="fsD${param.index}" class="focus" style="margin:0px 0px 6px 0px;">  
	
    <div id="D1pic${param.index}" class="fPic">  
    
    	<c:if test="${empty DisplayData.filenames[param.index - 1]}">	
    				
			<div class="fcon" style="display: none;">
	            <a  href="#"><img src="${pageContext.request.contextPath}/images/blankDemo.png" /></a>
	            <a  href="#"></a>
	        </div>
		</c:if>	
		
		<c:forEach var="filename" items="${DisplayData.filenames[param.index - 1]}">
		
			<div class="fcon" style="display: none;">
	            <a  href="#"><img src="${pageContext.request.contextPath}/${DisplayData.path}${DisplayData.imageKey}/${param.index}/small/${filename}" 
	            			onclick = "showRealPhoto('${DisplayData.imageKey}', '${param.index}', '${filename}');" />
	            			<span style="position: absolute; top: 3; left: 3;">
	            				<c:if test="${filename eq DisplayData.nowUseImage}">
									<img src="${pageContext.request.contextPath}/images/now_use.jpg"/></span> 
								</c:if>
	            				</a>
	        
	        </div>
	        
		</c:forEach>
		
    </div>
    <div class="fbg">  
    <div class="D1fBt" id="D1fBt${param.index}">  
    
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

 <div id="l${param.index}" style="position:relative;">					
	<div style="float:right;"> 
		<a href="#" class="a-btn-green" style="height:25px;" onclick="return showLayer('${param.index}')">
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
	
	function showRealPhoto(key, index, fileName){
		
		//alert(src);
		
		//fileName = fileName.replace(/[\/]/g, "-").replace('.',','); 
		
		//alert(src.indexOf('blankDemo'));
		
		if (fileName.indexOf('blankDemo') > 0)
			return;
		
		layer.open({
			type : 2,
			title : false,
			area : [ '690px', '560px' ],
			scrollbar : false,
			content : '${pageContext.request.contextPath}/album/album-photo-show?className=' + '${DisplayData.className}' + '&key=' + key + '&index=' + index + '&fileName=' + fileName + "&albumCount=" + '${param.albumCount}'
		});
		
	}
	
	function showLayer(index){
		
		//alert('${product.product_id}');
		layer.open({
			type : 2,
			title : false,
			area : [ '1000px', '600px' ],
			scrollbar : false,
			title : false,
			content : '${pageContext.request.contextPath}/album/album-upload-init?key=' + '${DisplayData.keyBackup}' + '&index=' + index
		});
		
	}	

	Qfast.add('widgets', { path: "${pageContext.request.contextPath}/plugins/jquery-auto-play-image/js/terminator2.2.min.js", type: "js", requires: ['fx'] });  
	Qfast(false, 'widgets', function () {
		K.tabs({
			id: 'fsD${param.index}',   //焦点图包裹id  
			conId: "D1pic${param.index}",  //** 大图域包裹id  
			tabId:"D1fBt${param.index}",  
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