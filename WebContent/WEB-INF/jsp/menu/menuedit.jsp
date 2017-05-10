<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>

<body class="easyui-layout">
<div id="container">
	<div id="main">
		<div style="height:10px"></div>
		<fieldset>
		<legend>菜单信息维护</legend>
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
			<table width=100%>
				<tr>
					<td width="80px">
						上级菜单：
					</td>
					<td>
						<input type=text name="menuparentid" id="menuparentid" value="${DisplayData.menuData.menuparentid}"/>
					</td>
					<td width="80px">
						菜单ID：
					</td>
					<td>
						<input type=text name="menuid" id="menuid" value="${DisplayData.menuData.menuid}"/>
					</td>
				</tr>
				<tr>
					<td>
						菜单名称：
					</td>
					<td>
						<input type=text name="menuname" id="menuname" value="${DisplayData.menuData.menuname}"/>
					</td>
					<td>
						菜单描述：
					</td>
					<td>
						<input type=text name="menudes" id="menudes" value="${DisplayData.menuData.menudes}"/>
					</td>
				</tr>
				<tr>
					<td>
						菜单URL：
					</td>
					<td>
						<input type=text name="menuurl" id="menuurl" value="${DisplayData.menuData.menuurl}"/>
					</td>
					<td>
						菜单类型：
					</td>
					<td>
						<select name="menutype" id="menutype">
							<c:forEach items="${DisplayData.menuTypeList}" var="value" varStatus="status">
								<option value ="${value[0]}">${value[1]}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						授权显示项：		
					</td>
					<td>
						<select name="menuviewflag" id="menuviewflag">
									<option value ="T">是</option>
									<option value ="F">否</option>
						</select>
					</td>
					<td>
						是否有效：
					</td>
					<td>
						<select name="menunnableflag" id="menunnableflag">
								<option value ="0">有效</option>
								<option value ="1">无效</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						关联菜单：
					</td>
					<td>
						<input type=text name="relationalmenuid" id="relationalmenuid" value="${DisplayData.menuData.relationalmenuid}"/>
					</td>
					<td>
						关联工作流ID：
					</td>
					<td>
						<input type=text name="menuwfnode" id="menuwfnode" value="${DisplayData.menuData.menuwfnode}"/>
					</td>
				</tr>
				<tr>
					<td>
						图标1：
					</td>
					<td>
						<input type=text name="menuicon1" id="menuicon1" value="${DisplayData.menuData.menuicon1}"/>
					</td>
					<td>
						图标2：
					</td>
					<td>
						<input type=text name="menuicon2" id="menuicon2" value="${DisplayData.menuData.menuicon2}"/>
					</td>
				</tr>
				<tr>
					<td>
						排序序号：
					</td>
					<td>
						<input type=text name="sortno" id="sortno" value="${DisplayData.menuData.sortno}"/>
					</td>
					<td>
					</td>
					<td>
						<input type=checkbox name="menuopenflag" id="menuopenflag" value="T"/>在新窗口中打开
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
</body>

<script>
	var validatorBaseInfo;
	var operType = '';
	var isUpdateSuccessed = false;
	var updatedRecordCount = parseInt('${DisplayData.updatedRecordCount}');
	$(function(){
		
		validatorBaseInfo = $("#form").validate({
			rules: {
				menuparentid: {
					maxlength: 3,
				},
				menuid: {
					required: true,								
					maxlength: 3,
				},
				menuname: {
					required: true,
					maxlength: 30,
				},
				menuurl: {				
					maxlength: 100,
				},
				menudes	: {				
					maxlength: 200,
				},
				menutype: {
					required: true,
				},
				menuviewflag: {
					required: true,
				},
				menunnableflag: {
					required: true,
				},
				relationalmenuid: {
					maxlength: 300,
				},
				menuwfnode: {
					maxlength: 60,
				},
				menuicon1: {
					maxlength: 200,
				},
				menuicon2: {
					maxlength: 200,
				},
				sortno: {
					number: true,
					maxlength: 3,
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

		if (updatedRecordCount > 0 ) {
			if (operType == 'add' || operType == 'addsub') {			
				window.opener.addNode('${DisplayData.menuData.menuparentid}', '${DisplayData.menuData.menuid}', '${DisplayData.menuData.menuname}', '${DisplayData.menuData.menuicon1}');
			}
			if (operType == 'update') {
				window.opener.updateNode('${DisplayData.menuData.menuid}', '${DisplayData.menuData.menuname}', '${DisplayData.menuData.menuicon1}');
			}
		}
			
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
				$('#menuid').attr('readonly', true);
			}
		}
		if (operType == 'addsub') {
			$('#menuparentid').val('${DisplayData.menuData.menuparentid}');
			$('#menuparentid').attr("readonly", true);
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('menu');
		}

		$('#menutype').val('${DisplayData.menuData.menutype}');
		$('#menuviewflag').val('${DisplayData.menuData.menuviewflag}');
		$('#menunnableflag').val('${DisplayData.menuData.menunnableflag}');
		if ('${DisplayData.menuData.menuopenflag}' == 'T') {
			$('#menuopenflag').attr("checked", "true");
		}
		
		if (updatedRecordCount > 0) {
			refreshOpenerData();
		}
				
		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 

	function noticeNaviChanged(id, name, isLeaf) {

		if (isLeaf) {
			$('#parentMenuIdName').val("");	
			$('#menuId').val(id);
			$('#menuName').val(name);
		} else {
			$('#menuId').val("");
			$('#menuName').val("");
			$('#parentMenuIdName').val(id);	
		}
	}

	function inputCheck() {
		var str = '';
		
		str = $('#menuparentid').val();
		if (!inputStrCheck(str, "上级菜单", 3, 8, true, true)) {
			return false;
		}
		str = $('#menuid').val();
		if (!inputStrCheck(str, "菜单ID", 3, 8, false, true)) {
			return false;
		}
		str = $('#menuname').val();
		if (!inputStrCheck(str, "菜单名称", 30, 7, false, true)) {
			return false;
		}
		str = $('#menuurl').val();
		if (!inputStrCheck(str, "菜单URL", 30, 9, true, true)) {
			return false;
		}
		str = $('#menudes').val();
		if (!inputStrCheck(str, "菜单描述", 200, 7, true, true)) {
			return false;
		}	
		str = $('#menutype').val();
		if (!inputStrCheck(str, "菜单类型", 3, 3,  false, true)) {
			return false;
		}
		str = $('#menuviewflag').val();
		if (!inputStrCheck(str, "授权显示项", 1, 8,  false, true)) {
			return false;
		}
		str = $('#menunnableflag').val();
		if (!inputStrCheck(str, "是否有效", 1, 8,  false, true)) {
			return false;
		}
		str = $('#relationalmenuid').val();
		if (!inputStrCheck(str, "关联菜单", 300, 8, true, true)) {
			return false;
		}
		str = $('#menuwfnode').val();
		if (!inputStrCheck(str, "关联工作流ID", 60, 8, true, true)) {
			return false;
		}
		str = $('#menuicon1').val();
		if (!inputStrCheck(str, "图标1", 200, 9, true, true)) {
			return false;
		}
		str = $('#menuicon2').val();
		if (!inputStrCheck(str, "图标2", 200, 9, true, true)) {
			return false;
		}
		str = $('#sortno').val();
		if (!inputStrCheck(str, "序号", 3, 3, true, true)) {
			return false;
		}
		
		return checkMenuId();
	}

	function checkMenuId() {
		var result = false;
		jQuery.ajax({
			type : 'POST',
			async: false,
			contentType : 'application/json',
			dataType : 'json',
			data : $('#menuid').val() + "&" + $('#operType').val(),
			url : "${ctx}/menu?methodtype=checkMenuId",
			success : function(data) {
				if (!data.success) {
					alert(data.message);
				} else {
					result = true;
				}
			},
			 error:function(XMLHttpRequest, textStatus, errorThrown){
                 alert(XMLHttpRequest.status);
                 alert(XMLHttpRequest.readyState);
                 alert(textStatus);
             }
		});

		return result;
	}
	
	function saveUpdate() {
		if (validatorBaseInfo.form()) {
			if (confirm("确定要保存吗？")) {
				var actionUrl;
				if (operType == 'add' || operType == 'addsub') {
					actionUrl = "${ctx}/menu?methodtype=add";
				} else {
					actionUrl = "${ctx}/menu?methodtype=update";
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
							reloadTabWindowWithNodeChangeNotice(d.info);
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
/*
	function closeWindow(isNeedConfirm) {
		if (isNeedConfirm == '') {
			if (operType == 'add' || operType == 'addsub' || operType == 'update') {
				if (confirm("确定要离开吗？")) {
					self.opener = null;
					self.close();
				}
			} else {
				self.opener = null;
				self.close();
			}
		} else {
			self.opener = null;
			self.close();
		}
	}
*/
	function doReturn() {

		var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		parent.layer.close(index); //执行关闭
		
	}

	
	function refreshOpenerData() {
		if (operType == 'add' || operType == 'addsub' || operType == 'update') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}

	
</script>
</html>