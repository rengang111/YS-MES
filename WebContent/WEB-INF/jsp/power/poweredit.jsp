<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>
<html>
<body class="easyui-layout">
<div id="container">
	<div id="main">
		<div style="height:10px"></div>
		<fieldset>
		<legend>权限维护</legend>
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
			<input type=hidden name="id" id="id" value='${DisplayData.id}'/>
			<input type=hidden name="unitId" id="unitId" value='${DisplayData.unitId}'/>
			<div class="">
				<table width=100% style=" border-collapse: separate;border-spacing:6px;"  >
					<tr>
						<td>
							用户：
						</td>
						<td>
							<input type=text name="userName" id="userName" value="${DisplayData.userName}"/>
							<input type=hidden name="userId" id="userId" value="${DisplayData.userId}"/>
						</td>
					</tr>
					<tr>
						<td>
							角色：
						</td>
						<td>
							<input type=text name="roleName" id="roleName" value="${DisplayData.roleName}"/>
							<input type=hidden name="roleId" id="roleId" value="${DisplayData.roleId}"/>
						</td>
					</tr>
					<tr>
						<td>
							授权方式：
						</td>
						<td>
							<select name="powerType" id="powerType">
								<option value ="1">用户授权</option>
								<option value ="2">用户组授权</option>
							</select>
						</td>
						<td>
							<button type="button" id="close" class="DTTT_button" onClick="doReturn();"
									style="height:25px;margin:0px 5px 0px 0px;float:right;" >返回</button>
							<button type="button" id="save" class="DTTT_button" onClick="saveUpdate();"
									style="height:25px;margin:0px 5px 0px 0px;float:right;" >保存</button>
						</td>
					</tr>
				</table>
			</div>
			<table>		
				<tr>
					<td>
						单位：
					</td>
					<td>
						<%@ include file="../common/deptselector.jsp"%>
					</td>
				</tr>

			</table>
			
		</form>
	</fieldset>
</body>

<script>
	var operType = '';
	var isUpdateSuccessed = false;
	var updatedRecordCount = parseInt('${DisplayData.updatedRecordCount}');
	var validatorBaseInfo;
	$(function(){
		
		autoCompleteUser();
		
		autoCompleteRole();
		
		validatorBaseInfo = $("#form").validate({
			rules: {
				userName: {
					required: true,	
				},
				roleName: {
					required: true,
				},
				powerType: {
					required: true,								
					maxlength: 1,
				},
				unitId: {
					required: true,	
				},
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
				
		operType = $('#operType').val();

		setCheckBoxTrue(true);
		setMenuId("${menuId}");
		noticeChanged();
		
		if (operType == 'add') {
			$('#save').val("保存授权");
		}
		if (operType == 'update') {
			$('#save').val("保存授权");
			$('#userName').attr('readonly', 'readonly');
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('power');
		}

		$('#powerType').val('${DisplayData.powerType}');
		
		if (updatedRecordCount > 0) {
			refreshOpenerData();
		}
				
		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 

	function inputCheck() {
		var str = '';

		str = $('#userName').val();
		if (!inputStrCheck(str, "用户", -1, 7, false, true)) {
			return false;
		}
		str = $('#roleName').val();
		if (!inputStrCheck(str, "角色", -1, 7, false, true)) {
			return false;
		}
		str = $('#powerType').val();
		if (!inputStrCheck(str, "授权方式", 1, 3, false, true)) {
			return false;
		}
		$('#unitId').val(getCheckedId());
		str = $('#unitId').val();
		if (!inputStrCheck(str, "单位", -1, 7, false, true)) {
			return false;
		}

		return true;	
	}

	function saveUpdate() {
		
		$('#unitId').val(getCheckedId());
		
		if (validatorBaseInfo.form()) {
			if (confirm("确定要保存吗？")) {
				var actionUrl;
				actionUrl = "${ctx}/power?methodtype=add";

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

	function noticeChanged() {

		if ($('#roleName').val() != '' && $('#userName').val() != '') {
			setInitDeptUrl("${ctx}/power?methodtype=getUnitList&userId=" + $('#userId').val() + "&roleId=" + $('#roleId').val());
			//setLaunchNaviUrl("${ctx}/power/getUnitList?");
			loadData();
		} else {
			if (isTreeEmpty()) {
				setInitDeptUrl("${ctx}/power?methodtype=getUnitList&userId=" + $('#userId').val() + "&roleId=" + $('#roleId').val());
				loadData();
			}
		}
	}
	
	function selectUser() {
		callUserSelect("userId", "userName");
	}

	function selectRole() {
		callRoleSelect("roleId", "roleName");
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
	
	function autoCompleteUser() { 
		$("#userName").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/power?methodtype=userSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								return {
									label : item.loginid + " | " + item.LoginName,
									value : item.LoginName,
									id : item.UserID,
								}
							}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},
			focus: function() {
                // 按上下键时不做选择操作
                return false;
            },
			select : function(event, ui) {

				//$("#userName").val(ui.item.value);
				//$("#userId").val(ui.item.id);
				
	        	var terms = split(this.value);
                // 移除当前所有的内容
                terms.pop();
                // 将选中的添加到input中
                terms.push( ui.item.value );
                // 在最后添加分隔符
                terms.push( "" );
                this.value = terms.join( "," );
                
                terms = split($("#userId").val());
                // 移除当前所有的内容
                terms.pop();
                // 将选中的添加到input中
                terms.push( ui.item.id );
                // 在最后添加分隔符
                terms.push( "" );
                $("#userId").val(terms.join( "," ));
                
                return false;
                
				
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#userName").val('');
                    $('#userId').val('');
                }
            },
			
			minLength : 1,
			autoFocus : false,
			width: 200,
			mustMatch:true,
		});
	}
	
	function autoCompleteRole() { 
		$("#roleName").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/power?methodtype=roleSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								return {
									label : item.RoleName + ' | ' + item.RoleType,
									value : item.RoleName,
									id : item.roleid,
								}
							}));
						datas = data.data;
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},
			focus: function() {
                // 按上下键时不做选择操作
                return false;
            },
			select : function(event, ui) {
				//$("#roleName").val(ui.item.value);
				//$("#roleId").val(ui.item.id);
				
	        	var terms = split(this.value);
                // 移除当前所有的内容
                terms.pop();
                // 将选中的添加到input中
                terms.push( ui.item.value );
                // 在最后添加分隔符
                terms.push( "" );
                this.value = terms.join( "," );
                
                terms = split($("#roleId").val());
                // 移除当前所有的内容
                terms.pop();
                // 将选中的添加到input中
                terms.push( ui.item.id );
                // 在最后添加分隔符
                terms.push( "" );
                $("#roleId").val(terms.join( "," ));
                
                return false;
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#roleName").val('');
                    $('#roleId').val('');
                }
            },
			
			minLength : 1,
			autoFocus : false,
			width: 200,
			mustMatch:true,
			multiple: true,
			
		});
	}
	
    function split( val ) {
        return val.split( /,\s*/ );
      }
</script>
</html>