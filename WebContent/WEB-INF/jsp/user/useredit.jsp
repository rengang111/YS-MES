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
		<legend>用户信息维护</legend>
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
			<input type=hidden name="userid" id="userid" value="${DisplayData.userData.userid}"/>
			<input type=hidden name="photo" id="photo" value="${DisplayData.photo}"/>
			<input type=hidden name="photoChangeFlg" id="photoChangeFlg" value="${DisplayData.photoChangeFlg}"/>
			<input type=hidden name="doSaveFlag" id="doSaveFlag"/>
			<input type=hidden name="loginidbackup" id="loginidbackup" value="${DisplayData.userData.loginid}"/>
			
			<table width=100% style=" border-collapse: separate;border-spacing:6px;"  >
				<tr>
					<td height="25px">
						单位名称：
					</td>
					<td>
						<input type=text name="unitName" id="unitName" value="${DisplayData.unitName}"/>
						<input type=hidden name="unitId" id="unitId" value="${DisplayData.unitId}"/>			
					</td>
					<td>
						账号：
					</td>
					<td>
						<input type=text name="loginid" id="loginid" value="${DisplayData.userData.loginid}"/>
					</td>
				</tr>			
				<tr>
					<td height="25px">
						用户名称：
					</td>
					<td>
						<input type=text name="loginname" id="loginname" value="${DisplayData.userData.loginname}"/>
					</td>
					<td>
						用户简拼：
					</td>
					<td>
						<input type=text name="jianpin" id="jianpin" value="${DisplayData.userData.jianpin}"/>
					</td>
				</tr>		
				<tr>
					<td height="25px">
						密码：
					</td>
					<td>
						<input type=password name="loginpwd" id="loginpwd" value="${DisplayData.userData.loginpwd}" style="height:22px;"/>
					</td>
					<td>
						确认密码：
					</td>
					<td>
						<input type=password name="loginpwdConfirm" id="loginpwdConfirm" value="" style="height:22px;"/>
					</td>
				</tr>				
				<tr>
					<td height="25px">
						性别：
					</td>
					<td>
						<select name="sex" id="usersex">
							<c:forEach items="${DisplayData.sexList}" var="value" varStatus="status">
								<option value ="${value[0]}">${value[1]}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						职务：		
					</td>
					<td>
						<select name="duty" id="userduty">
							<c:forEach items="${DisplayData.dutyList}" var="value" varStatus="status">
								<option value ="${value[0]}">${value[1]}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td height="25px">
						地址（省市县）：
					</td>
					<td>
						<input type=text name="address" id="address" value="${DisplayData.address}"/>
						<input type=hidden name="addresscode" id="addresscode" value="${DisplayData.userData.addresscode}"/>
					</td>
					<td>
						地址（街道门牌号码）：
					</td>
					<td>
						<input type=text name="addressuser" id="addressuser" value="${DisplayData.userData.addressuser}"/>
					</td>
				</tr>			
				<tr>
					<td height="25px">
						邮编：
					</td>
					<td>
						<input type=text name="postcode" id="postcode" value="${DisplayData.userData.postcode}"/>
					</td>
					<td>
						电话：
					</td>
					<td>
						<input type=text name="telphone" id="telphone" value="${DisplayData.userData.telphone}"/>
					</td>
				</tr>
				<tr>
					<td height="25px">
						手机：
					</td>
					<td>
						<input type=text name="handphone" id="handphone" value="${DisplayData.userData.handphone}"/>
					</td>
					<td>
						EMAIL：
					</td>
					<td>
						<input type=text name="email" id="email" value="${DisplayData.userData.email}"/>
					</td>
				</tr>
				<tr>
					<td height="25px">
						找回密码1：
					</td>
					<td>
						<input type=text name="pwdquestion1" id="pwdquestion1" value="${DisplayData.userData.pwdquestion1}"/>
					</td>
					<td>
						找回密码2：
					</td>
					<td>
						<input type=text name="pwdquestion2" id="pwdquestion2" value="${DisplayData.userData.pwdquestion2}"/>
					</td>
				</tr>
				<tr>
					<td height="25px">
						有效时间：
					</td>
					<td>
						<input name="enablestarttime" id="enablestarttime" value="${DisplayData.userData.enablestarttime}" style="height:22px"/>至<br>
						<input name="enableendtime" id="enableendtime" value="${DisplayData.userData.enableendtime}" style="height:22px"/>
					</td>
					<td>
						工号：
					</td>
					<td>
						<input type=text name="workid" id="workid" value="${DisplayData.userData.workid}"/>
					</td>
				</tr>			
				<tr>
					<td height="25px">
						序号：
					</td>
					<td>
						<input type=text name="sortno" id="sortno" value="${DisplayData.userData.sortno}"/>
					</td>
					<td>
						<input type=checkbox name="lockflag" id="lockflag" value="1"/>选中锁定
						<input type=checkbox name="enableflag" id="enableflag" value="1"/>选中失效
					</td>
				</tr>		
				<tr>
					<td>
						照片：
					</td>
					<td colspan=3>
						<img id="headPhoto" width=60px height=60px src="${ctx}${DisplayData.photo}" style="margin:0px 0px 0px 10px;"/><br>
						<input type="file" id="headPhotoFile" name="headPhotoFile" onchange="uploadHeadPhoto();" accept="image/jpeg"/>
						
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
</body>

<script>
	var operType = '';
	var isUpdateSuccessed = false;
	var updatedRecordCount = parseInt('${DisplayData.updatedRecordCount}');
	var validatorBaseInfo;
	
	$(function(){
		
		autoCompleteUnit();
		
		autoCompleteAddress();
		
		validatorBaseInfo = $("#form").validate({
			rules: {
				unitName: {
					required: true,	
					maxlength: 100,
				},
				loginid: {
					required: true,								
					maxlength: 20,
				},
				loginname: {
					required: true,								
					maxlength: 20,
				},
				loginpwd: {
					pwd: true,
					maxlength: 100,
				},
				jianpin: {				
					maxlength: 20,
				},
				usersex	: {				
					required: true,
				},
				userduty: {
					required: true,
				},
				addresscode: {
					maxlength: 100,
				},
				addressuser: {
					maxlength: 100,
				},
				postcode: {
					maxlength: 6,
				},
				telphone: {
					maxlength: 20,
				},
				handphone: {
					maxlength: 20,
				},
				email: {
					maxlength: 50,
				},
				pwdquestion1: {
					required: true,
					maxlength: 20,
				},
				pwdquestion2: {
					maxlength: 20,
				},
				enablestarttime: {
					required: true,
				},
				enableendtime: {
					maxlength: 120,
				},
				workid: {
					maxlength: 120,
				},
				sortno: {
					number: true,
					maxlength: 120,
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
		
	    jQuery.validator.addMethod("pwd",function(value, element){
	    	var rtnValue = true;

    		if (operType != 'update') {
    			var rtn = confirmPwd();
    			if (rtn != 0) {
    				rtnValue = false;
    			}
    		}

	        return rtnValue;   
	    }, "密码错误");
	    
		$("#enablestarttime").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});
		if ($("#enablestarttime").val() == "") {
			$("#enablestarttime").datepicker( 'setDate' , new Date() );
		}
		
		$("#enableendtime").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});

		operType = $('#operType').val();

		$('#usersex').val("${DisplayData.userData.sex}");
		$('#userduty').val("${DisplayData.userData.duty}");
		if ('${DisplayData.userData.lockflag}' == '1') {
			$('#lockflag').attr("checked", "true");
		}
		if ('${DisplayData.userData.enableflag}' == '1') {
			$('#enableflag').attr("checked", "true");
		}
		
		if (operType == 'add') {
			$('#save').val("保存增加");
		}
		if (operType == 'update') {
			if (updatedRecordCount > 0 ) {

			} else {
				$('#save').val("保存修正");
				$('#userid').attr('readonly', true);
				$('#loginpwd').hide();
				$('#loginpwdConfirm').hide();
			}
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('user');
			$('#headPhotoFile').attr('disabled', true);
		}
		
		if (updatedRecordCount > 0) {
			$('#headPhoto').attr('src', '');
		}

		if ($('#photo').val() != '') {
			$('#headPhoto').attr('src', '${ctx}' + $('#photo').val());
		}
		
	}); 

	function inputCheck() {
		var str = '';

		str = $('#unitName').val();
		if (!inputStrCheck(str, "单位名称", 100, 7, false, true)) {
			return false;
		}
		str = $('#loginid').val();
		if (!inputStrCheck(str, "账号", 20, 8, false, true)) {
			return false;
		}
		str = $('#loginname').val();
		if (!inputStrCheck(str, "用户名称", 20, 7, false, true)) {
			return false;
		}
		str = $('#jianpin').val();
		if (!inputStrCheck(str, "用户简拼", 20, 7, true, true)) {
			return false;
		}
		
		if (operType != 'update') {
			str = $('#loginpwd').val();
			if (!inputStrCheck(str, "密码", 100, 7, false, true)) {
				return false;
			}
			
			str = $('#loginpwdConfirm').val();
			if (!inputStrCheck(str, "确认密码", 100, 7,  false, true)) {
				return false;
			}
			if (!confirmPwd()) {
				return false;
			}
		}	

		str = $('#usersex').val();
		if (!inputStrCheck(str, "性别", 3, 8,  false, true)) {
			return false;
		}
		str = $('#userduty').val();
		if (!inputStrCheck(str, "职务", 30, 8,  false, true)) {
			return false;
		}	
		str = $('#addresscode').val();
		if (!inputStrCheck(str, "地址（省市县）", 100, 7,  false, true)) {
			return false;
		}		
		str = $('#addressuser').val();
		if (!inputStrCheck(str, "地址（街道门牌号码）", 100, 7,  false, true)) {
			return false;
		}
		
		str = $('#postcode').val();
		if (!inputStrCheck(str, "邮编", 6, 3, false, true)) {
			return false;
		}
		str = $('#telphone').val();
		if (!inputStrCheck(str, "电话", 20, 5, false, true)) {
			return false;
		}
		str = $('#handphone').val();
		if (!inputStrCheck(str, "手机", 20, 5, false, true)) {
			return false;
		}
		str = $('#email').val();
		if (!inputStrCheck(str, "EMAIL", 50, 6, false, true)) {
			return false;
		}
		str = $('#pwdquestion1').val();
		if (!inputStrCheck(str, "找回密码1", 20, 7, false, true)) {
			return false;
		}
		str = $('#pwdquestion2').val();
		if (!inputStrCheck(str, "找回密码2", 20, 7, true, true)) {
			return false;
		}
		str = $('#enablestarttime').datebox('getValue');
		if (!inputStrCheck(str, "有效时间开始", 120, 1, false, true)) {
			return false;
		}
		str = $('#enableendtime').datebox('getValue');
		if (!inputStrCheck(str, "有效时间终止", 120, 1, true, true)) {
			return false;
		}
		if (str == "") {
			$('#enableendtime').datebox('setValue', '2050-12-31 24:00:00');
		}
		str = $('#workid').val();
		if (!inputStrCheck(str, "工号", 120, 8, true, true)) {
			return false;
		}
		str = $('#sortno').val();
		if (!inputStrCheck(str, "序号", 120, 3, true, true)) {
			return false;
		}
		/*
		str = $('#photo').val();
		if (!inputStrCheck(str, "照片", 120, 8, true, true)) {
			return false;
		}
		*/
		return true;	
	}

	function saveUpdate() {
		$('#doSaveFlag').val("1");
		if (validatorBaseInfo.form()) {
			if (confirm("确定要保存吗？")) {
				var actionUrl;
				if (operType == 'add') {
					actionUrl = "${ctx}/user?methodtype=add";
				} else {
					actionUrl = "${ctx}/user?methodtype=update";
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
		$('#doSaveFlag').val("");
	}

	function selectUnit() {
		callUnitSelect("unitId", "unitName", "0");
	}

	function selectAddress() {
		callDicSelect("addresscode", "address", 'A2', '1' );
	}
	
	function doReturn() {

		var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		parent.layer.close(index); //执行关闭
		
	}

	function confirmPwd() {
		
		if ($('#doSaveFlag').val() != "") {
			if ($('#loginpwd').val() == "") {
				return 1;
			}
			if ($('#loginpwd').val() != $('#loginpwdConfirm').val()) {
				return 2;
			}
		}
		return 0;
	}

	function uploadHeadPhoto() {

		var fileName = $("#headPhotoFile").val();
		if (fileName != '') {
			var extname = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);  
		    extname = extname.toLowerCase();
		    
		    if (extname != "jpeg" && extname != "jpg") {
				alert("只可以上传jpeg格式的图片文件");
				return false;
			}
	
		    $("#form").ajaxSubmit({
				type: "POST",
				url:'${ctx}/userphoto?methodtype=uploadHeadPhoto',
				dataType: 'json',
			    success: function(data){
			     	if(data.result == '0'){
						$('#userid').val(data.userId);
						$('#headPhoto').attr('src', '${ctx}' + data.path);						
						$('#photo').val(data.path);
						$('#photoChangeFlg').val("1");
			   		 }
			    	else{
			    		alert(data.message);
			    	}
				},
		        error : function(XMLHttpRequest, textStatus, errorThrown) {  
					
		        } 		
			});	
		}
	}
	
	function autoCompleteUnit() { 
		$("#unitName").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/user?methodtype=unitSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								return {
									label : item.unitName,
									value : item.unitName,
									id : item.unitId,
								}
							}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},

			select : function(event, ui) {

				$("#unitName").val(ui.item.value);
				$("#unitId").val(ui.item.id);
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#unitName").val('');
                    $('#unitId').val('');
                }
            },
			
			minLength : 1,
			autoFocus : false,
			width: 200,
			mustMatch:true,
		});
	}
	
	function autoCompleteAddress() { 
		$("#address").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/user?methodtype=addressSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								return {
									label : item.dicName,
									value : item.dicName,
									id : item.dicId,
								}
							}));
						datas = data.data;
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},

			select : function(event, ui) {
				$("#address").val(ui.item.value);
				$("#addresscode").val(ui.item.id);
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#address").val('');
                    $('#addresscode').val('');
                }
            },
			
			minLength : 1,
			autoFocus : false,
			width: 200,
			mustMatch:true,
		});
	}
</script>
</html>