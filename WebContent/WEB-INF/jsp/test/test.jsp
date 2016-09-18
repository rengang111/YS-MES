<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%@ include file="../common/common.jsp"%>
<Script>
    function execSubmit(urlaction) {
        if (urlaction != '') {
        	$('#form').attr("action", "${ctx}" + urlaction);
        } else {
        	$('#form').attr("action", "${ctx}/test123");
        }

    	$('#form').submit();
    }

    
    function changeImg(){     
        var imgSrc = $("#imgObj");     
        var src = imgSrc.attr("src");     
        imgSrc.attr("src", chgUrl(src));     
    }     
    //时间戳     
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳     
    function chgUrl(url){     
        var timestamp = (new Date()).valueOf();     
        newUrl = url.substring(0,17);     
        if((url.indexOf("&")>=0)){     
        	newUrl = url + "×tamp=" + timestamp;
        }else{     
        	newUrl = url + "?timestamp=" + timestamp;     
        }
        alert(newUrl);
        return newUrl;     
    }     
    function isRightCode(){     
        var code = "verifyCode=" + $("#verifyCode").val();

        $.ajax({     
            type:"POST",     
            url:"${ctx}/Login/verify/validateCode",     
            data:code,
            success:callback     
        });     
    }     
    function callback(data){     
        $("#info").html(data);     
    } 

    function doCheckStr() {
        /*
    	if (inputStrCheck($('#checkStr').val(), "项目", 20, $('#checkStrType').val(), false, true)) {
			alert("ok");
        }
        */
    	var reg = /^(?![_.,:])[a-zA-Z0-9._\&:/\u4e00-\u9fa5-]+$/;
		alert(reg.test($('#checkStrType').val()));
    }
    function doAjax() {
		jQuery.ajax({
			type : 'POST',
			async: false,
			contentType : 'application/json',
			dataType : 'json',
			data : {a:123,b:456},
			url : "${ctx}/test123?type=ajax",
			success : function(data) {
				alert(data);
			},
			 error:function(XMLHttpRequest, textStatus, errorThrown){
                 alert(XMLHttpRequest.status);
                 alert(XMLHttpRequest.readyState);
                 alert(textStatus);
             }
		}); 

    }
</Script>
<body>
	<form id="form" modelAttribute="dataModels" action="${pageContext.request.contextPath}/test123" method="get">
		<input type="text" name="tSub.formId" value=""/>
		<input type="text" name="tSub.formName" value=""/>
		<input type="text" name="formId" value=""/>
		<input type="text" name="formName" value=""/>		
		<input type="text" name="formDisp" id="formdisp" value="12222"/>
		<input type="button" name="doSubmit" id="doSubmit" value="MainFrame" onClick="execSubmit('');"/>
		<br>
		<input type="button" name="callUserSelectPopup" id="callUserSelectPopup" value=" 选择用户" onClick="callUserSelect('userId', 'formdisp');" />
		<input type="text" name="userId" id="userId" />
		<br>
		<input type="button" name="callRoleSelectPopup" id="callRoleSelectPopup" value=" 选择角色" onClick="callRoleSelect('roleId', 'formdisp');" />
		<input type="text" name="roleId" id="roleId" />
		<br>
		<input type="button" name="callDicSelectPopup" id="callDicSelectPopup" value=" 选择字典" onClick="callDicSelect('dicId', 'formdisp', '01');" />
		<input type="text" name="dicId" id="dicId" />
		<br>
		<input type="button" name="callMenu" id="callMenu" value="菜单管理" onClick="execSubmit('/menu/initmenu');" />	
		<br>
	 		<input id="verifyCode" name="verifyCode" type="text"/>       
	        <img id="imgObj"  alt="" src="${ctx}/Login/verifyCode"/>       
	        <a href="#" onclick="changeImg();">换一张</a>       
	        <input type="button" value="验证" onclick="isRightCode()"/> 
		<br>
		<input type=text id="checkStr" name="checkStr"/>
		<input type=text id="checkStrType" name="checkStrType"/>
		<input type=button value="输入检查" onClick="doCheckStr();"/>
		<br>
		<input type=button value="ajax" onClick="doAjax();" />
		<br>
		
		<!-- 翻页start -->
		<input type=hidden name="startIndex" id="startIndex" value=""/>
		<input type=hidden name="flg" id="flg" value="11111"/>
		<input type=hidden name="turnPageFlg" id="turnPageFlg" value=""/>
		<input type=hidden name="sortFieldList" id="sortFieldList" value="${DisplayData.sortFieldList}"/>
		<input type=hidden name="totalPages" value="${DisplayData.totalPages}"/>
		<!-- 翻页end -->
		
		<br>检索结果:<br>
		<Table>
			<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
				<tr>
				<c:forEach items="${value}" var="subValue" varStatus="status">
					<td>
						${subValue}
					</td>
				</c:forEach>
				</tr>
			</c:forEach>
		</Table>
		<br>
		${DisplayData.turnPageHtml}
	</form>
	

</body>
</html>