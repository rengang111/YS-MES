<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugins/jflip/style.css" />
	
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/jflip/jquery-1.2.6.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/jflip/jquery.jflip-0.4.js"></script> 	

	<div>
			
			
		<ul id="album">
		
			<c:if test="${empty filenames}">				
				<li>
					<img src="${pageContext.request.contextPath}/images/blankDemo.png" />
			  	</li>
			</c:if>			
								
			<c:forEach var="filename" items="${filenames}">
				<li>
					<img src="${pageContext.request.contextPath}/images/product/${product.product_id}/${filename}" />
			  	</li>
			</c:forEach>
			
		</ul>
		
		<div id="l1" style="margin:20px 0px;">
									
			<div style="float:right;">
				<a href="#" class="a-btn-green" style="height:25px;padding: 0px 5px 0px 25px;margin:0px 25px 0px 3px;" onclick="return showLayer()">
					<img src="${pageContext.request.contextPath}/images/action_add.png" height="16px" style="top:5px;"/>
					<span class="a-btn-text" style="font-size:12px;">图片上传</span> 
					</span></span>
				</a>
			
			</div>
						
		</div>
		
		<script type="text/javascript">
		
		var $180 = $.noConflict();
	 	
	 	$180("#album").jFlip(360,360,{background:"lightgray",cornersTop:false,scale:"fill"});
				
		function refresh(){
								
			//$("#reset").click();
			
			location.reload();
			
		}
		
		function showRealPhoto(src){
			
			alert(src);
			
			src = src.replace(/[\/]/g, "-").replace('.',','); 
			
			//alert(src.indexOf('blankDemo'));
			
			if (src.indexOf('blankDemo')>0)
				return;
			
			layer.open({
				type : 2,
				title : false,
				area : [ '840px', '840px' ],
				scrollbar : false,
				content : '${pageContext.request.contextPath}/album/product-album-photo-show/' + src + '.html'
			});
			
		}
		
		function showLayer(){
			
			//alert("${product.productId}");
			
			layer.open({
				type : 2,
				title : false,
				area : [ '1000px', '600px' ],
				scrollbar : false,
				title : false,
				content : '${pageContext.request.contextPath}/album/product-album-upload/' + '${product.product_id}' + '.html'
			});
			
		}
		
		
		</script>
	
	</div>