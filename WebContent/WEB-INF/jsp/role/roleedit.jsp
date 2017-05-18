<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common2.jsp"%>
<html>

<body class="easyui-layout">
<div id="container">
	<div id="main">
		<div style="height:10px"></div>
		<fieldset>
		<legend>角色信息维护</legend>
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
	
			<table width=100%>
				<tr>
					<td width=80px>
						角色名称：
					</td>
					<td>
						<input type=hidden name="roleid" id="roleid" value="${DisplayData.roleData.roleid}"/>
						<input type=text name="rolename" id="rolename" value="${DisplayData.roleData.rolename}"/>
					</td>
					<td width=80px>
						角色类型：
					</td>
					<td>
						<select name="roletype" id="roletype">
							<c:forEach items="${DisplayData.roleTypeList}" var="value" varStatus="status">
								<option value ="${value[0]}">${value[1]}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						创建单位：
					</td>
					<td>
						<input type=text name="unitName" id="unitName" value="${DisplayData.unitName}" readonly/>
					</td>
					<td>
						创建人：
					</td>
					<td>
						<input type=text name="userName" id="userName" value="${DisplayData.userName}" readonly/>
					</td>
				</tr>
				<tr rowspan=3><td>&nbsp;</td></tr>			
				<tr>
					<td colspan=4>
						<button type="button" id="close" class="DTTT_button" onClick="doReturn();"
								style="height:25px;margin:0px 5px 0px 0px;float:right;" >返回</button>
						<button type="button" id="save" class="DTTT_button" onClick="saveUpdate();"
								style="height:25px;margin:0px 5px 0px 0px;float:right;" >保存</button>

					</td>
				</tr>
			</table>
			<br>
		</form>
	</fieldset>
	</div>
</div>
</body>

<script>
	var operType = '';
	var isUpdateSuccessed = false;
	var updatedRecordCount = parseInt('${DisplayData.updatedRecordCount}');
	var validatorBaseInfo;
	
	$(function(){
		
		validatorBaseInfo = $("#form").validate({
			rules: {
				rolename: {
					required: true,
					maxlength: 60,
					rolename: true,
				},
				roletype: {
					required: true,								
					maxlength: 3,
				}
			},
			errorPlacement: function(error, element) {
			    if (element.is(":radio"))
			        error.appendTo(element.parent().next().next());
			    else if (element.is(":checkbox"))											    	
			    	error.insertAfter(element.parent().parent());
			    else
			    	error.insertAfter(element);
			}
		});
		
	    jQuery.validator.addMethod("rolename",function(value, element){
	    	var rtnValue = true;

	    	if (value != "") {
				rtnValue = checkRoleName();
	    	}

	        return rtnValue;   
	    }, "子编码重复");
		
		operType = $('#operType').val();
			
		if (operType == 'add' || operType == 'addsub') {
			$('#save').val("保存增加");
		}
		if (operType == 'update') {
			if (updatedRecordCount > 0 ) {
				if ('${DisplayData.message}' != '') {
					alert('${DisplayData.message}');
				}
				refreshOpenerData();
				closeWindow("1");
			} else {
				$('#save').val("保存修正");
				$('#roletype').attr('disabled', 'disabled');
			}
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('role');
		}
		
		if (updatedRecordCount > 0) {
			refreshOpenerData();
		}

		$('#roletype').val('${DisplayData.roleData.roletype}');
		
		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 

	function inputCheck() {
		var str = '';
		
		str = $('#rolename').val();
		if (!inputStrCheck(str, "角色名称", 60, 7, false, true)) {
			return false;
		}
		
		str = $('#roletype').val();
		if (!inputStrCheck(str, "角色类型", 3, 3, false, true)) {
			return false;
		}

		if (!checkRoleName()) {
			return false;
		}
		
		return true;
	}

	function saveUpdate() {
		if (inputCheck()) {
			if (confirm("确定要保存吗？")) {
				var actionUrl;
				if (operType == 'add' || operType == 'addsub') {
					actionUrl = "${ctx}/role?methodtype=add";
				} else {
					actionUrl = "${ctx}/role?methodtype=update";
				}
				$.ajax({
					type : "POST",
					contentType : 'application/json',
					dataType : 'json',
					url : actionUrl,
					data : JSON.stringify($('#form').serializeArray()),// 要提交的表单
					success : function(d) {
						if (d.rtnCd != "000") {
							alert(d.message);	
						} else {
							reloadTabWindowWithNodeChangeNotice(d.info, true);
							doReturn();
						}
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						//alert(XMLHttpRequest.status);					
						//alert(XMLHttpRequest.readyState);					
						//alert(textStatus);					
						//alert(errorThrown);
					}
				});
			}
		}	
	}

	function doReturn() {

		var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		parent.layer.close(index); //执行关闭
		
	}

	function refreshOpenerData() {
		if (operType == 'add' || operType == 'update') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}

	function checkRoleName() {
		var result = false;
		jQuery.ajax({
			type : 'POST',
			async: false,
			contentType : 'application/json',
			dataType : 'json',
			data : $('#rolename').val() + '&' + $('#roleid').val(),
			url : "${ctx}/role?methodtype=checkRoleName",
			success : function(data) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				}

			},
			 error:function(XMLHttpRequest, textStatus, errorThrown){
             }
		}); 

		return result;
	}
	
</script>
</html>