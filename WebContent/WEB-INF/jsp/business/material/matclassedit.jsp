<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="../../common/common.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>物料分类维护</title>
</head>
<body>
	<div id="layer_main">
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
		<fieldset>
		<table class="form" cellspacing="0" cellpadding="0" style="width: 100%;">
			<tr>
				<td width="100px">
					上级分类编码：
				</td>
				<td width="150px"> <label>${DisplayData.parentCategoryId}</label>
					<input type=hidden name="parentCategoryId" id="parentCategoryId" value="${DisplayData.parentCategoryId}" />
				</td>
				<td width="100px">
					上级分类名称：
				</td>
				<td><label>${DisplayData.parentCategoryName}</label>
					<input type=hidden name="parentCategoryName" id="parentCategoryName" value="${DisplayData.parentCategoryName}" />
				</td>
			</tr>
			
			<tr>
				<td>子分类编码<font color="#F00">(*)</font>：</td>
				<td colSpan="3">
					<input type=text   name="unitData.categoryid" id="categoryid" value="${DisplayData.unitData.categoryid}"/>
					<input type=hidden name="unitData.recordid" id="recordid" value="${DisplayData.unitData.recordid}"/>
					<input type=hidden name="unitData.createtime" id="createtime" value="${DisplayData.unitData.createtime}"/>
					<input type=hidden name="unitData.createperson" id="createperson" value="${DisplayData.unitData.createperson}"/>
					<input type=hidden name="unitData.createunitid" id="createunitid" value="${DisplayData.unitData.createunitid}"/>
					<input type=hidden name="unitData.parentid" id="parentid" value="${DisplayData.parentCategoryId}"/>
				</td>
			</tr>		
	
			<tr>
				<td>
					子分类名称：
				</td>
				<td colSpan="3">
					<input type=text name="unitData.categoryname" id="categoryname" class="middle" value="${DisplayData.unitData.categoryname}"/>
				</td>
			</tr>
			<tr>
				<td>
					规格描述：
				</td>
				<td colSpan="3">
					<input type=text name="unitData.formatdes" id="formatdes"  class="long" value="${DisplayData.unitData.formatdes}"/>
				</td>
			</tr>
			<tr>
				<td>备注：</td>
				<td colSpan="3">
					<textarea rows="10" cols="60" name="unitData.memo" id="memo" class="long" >${DisplayData.unitData.memo}</textarea>
				</td>
			</tr>
		</table>
		</fieldset>					
		<fieldset class="action">
				<button type="button" id="save"
						onclick="saveUpdate()" class="DTTT_button" />保存
				<button type="button" id="save"
						onclick="closeWindow()" class="DTTT_button" />关闭
		</fieldset>
	</form>
	</div>

</body>

<script>
	var operType = '';
	var isUpdateSuccessed = false;
	var updatedRecordCount = parseInt('${DisplayData.updatedRecordCount}');
	$(function(){
		operType = $('#operType').val();

		if (updatedRecordCount > 0 ) {

			var strName = '${DisplayData.unitData.categoryname}';
			var strchild =' ${DisplayData.unitData.childid}';
			var treeview = strchild+'_'+strName;
			
			if (operType == 'add') {			
				window.opener.addNode('${DisplayData.unitData.parentid}', '${DisplayData.unitData.categoryid}', treeview, '');
			}
			if (operType == 'update') {
				window.opener.updateNode('${DisplayData.unitData.parentid}', '${DisplayData.unitData.categoryid}', treeview, '');
			}
		}
			
		if (operType == 'add') {
			$('#save').val("保存增加");
		}	
		if (operType == 'addsub') {
			$('#categoryid').val('${DisplayData.parentCategoryId}');
		}
		if (operType == 'update') {
			if (updatedRecordCount > 0 ) {
				refreshOpenerData();				
				if ('${DisplayData.message}' != '') {
					alert('${DisplayData.message}');
				}
				closeWindow("1");
			} else {
				$('#save').val("保存修正");
				//$('#categoryid').attr('readonly', true);
			}
		}
	
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('material');
		}

		if (operType == 'update') {
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
			$('#parentCategoryName').val("");	
			$('#categoryid').val(id);
			$('#categoryname').val(name);
		} else {
			$('#categoryid').val("");
			$('#categoryname').val("");
			$('#parentCategoryName').val(id);	
		}
	}

	function inputCheck() {
		return true;	
	}

	function saveUpdate() {
		if (inputCheck()) {
			if (confirm("确定要保存吗？")) {

				if (operType == 'add' || operType == 'addsub') {
					$('#form').attr("action", "${ctx}/business/material?methodtype=add");
				} else {
					$('#form').attr("action", "${ctx}/business/material?methodtype=update");
				}
				$('#form').submit();
			}
		}	
	}

	function selectAddress() {
		callDicSelect("addresscode", "address", 'A2', '1' );
	}
	

	function closeWindow(isNeedConfirm) {

		self.opener = null;
		self.close();
	}

	function refreshOpenerData() {
		if (operType == 'add' || operType == 'addsub'|| operType == 'update') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}

	
</script>
</html>