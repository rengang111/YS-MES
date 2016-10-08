<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../../common/common.jsp"%>
<html>
<head>
	<title>物料分类维护</title>
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
		<input type=hidden name="unitData.unitid" id="unitid" value="${DisplayData.unitData.unitid}"/>
		<table>
			<tr>
				<td>
					上级分类：
				</td>
				<td>
					<input type=text name="parentUnitName" id="parentUnitName" value="${DisplayData.parentUnitName}" readonly/>
					<input type=hidden name="parentUnitId" id="parentUnitId" value="${DisplayData.parentUnitId}"/>
					<input type=button name="unitselect" id="unitselect" value="选择" onClick="selectUnit();"/>
					<input type=button name="unitclear" id="unitclear" value="清空" onClick="clearUnit();"/>
				</td>
			</tr>
			<tr>
				<td>
					子分类编码：
				</td>
				<td>
					<input type=text name="unitData.orgid" id="orgid" value="${DisplayData.unitData.orgid}"/>
				</td>
			</tr>		
	
			<tr>
				<td>
					子分类名称<font color="#F00">(*)</font>：
				</td>
				<td>
					<input type=text name="unitData.unitname" id="unitname" value="${DisplayData.unitData.unitname}"/>
				</td>
			</tr>
			<tr>
				<td>
					单位简称：
				</td>
				<td>
					<input type=text name="unitData.unitsimpledes" id="unitsimpledes" value="${DisplayData.unitData.unitsimpledes}"/>
				</td>
			</tr>			
			<tr>
				<td>
					单位简拼：
				</td>
				<td>
					<input type=text name="unitData.jianpin" id="jianpin" value="${DisplayData.unitData.jianpin}"/>
				</td>
			</tr>
		
					<tr>
				<td>
					单位属性：
				</td>
				<td>
					<select name="unitData.unitproperty" id="unitproperty">
						<c:forEach items="${DisplayData.unitPropertyList}" var="value" varStatus="status">
							<option value ="${value[0]}">${value[1]}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					单位性质：		
				</td>
				<td>
					<select name="unitData.unittype" id="unittype">
						<c:forEach items="${DisplayData.unitTypeList}" var="value" varStatus="status">
							<option value ="${value[0]}">${value[1]}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					地址（省市县）：
				</td>
				<td>
					<input type=text name="address" id="address" value="${DisplayData.address}" readonly/>
					<input type=hidden name="unitData.addresscode" id="addresscode" value="${DisplayData.unitData.addresscode}" readonly/>
					<input type=button name="addressSelect" id="addressSelect" value="选择地址" onClick="selectAddress();"/>
				</td>
			</tr>
			<tr>
				<td>
					地址（街道门牌号码）：
				</td>
				<td>
					<input type=text name="unitData.addressuser" id="addressuser" value="${DisplayData.unitData.addressuser}"/>
				</td>
			</tr>			
			<tr>
				<td>
					邮编：
				</td>
				<td>
					<input type=text name="unitData.postcode" id="postcode" value="${DisplayData.unitData.postcode}"/>
				</td>
			</tr>
			<tr>
				<td>
					电话：
				</td>
				<td>
					<input type=text name="unitData.telphone" id="telphone" value="${DisplayData.unitData.telphone}"/>
				</td>
			</tr>
			<tr>
				<td>
					EMAIL：
				</td>
				<td>
					<input type=text name="unitData.email" id="email" value="${DisplayData.unitData.email}"/>
				</td>
			</tr>
			<tr>
				<td>
					负责人：
				</td>
				<td>
					<input type=text name="unitData.mgrperson" id="mgrperson" value="${DisplayData.unitData.mgrperson}"/>
				</td>
			</tr>			
			<tr>
				<td>
					科室：
				</td>
				<td>
					<input type=text name="unitData.officeid" id="officeid" value="${DisplayData.unitData.officeid}"/>
				</td>
			</tr>
			<tr>
				<td>
					区域码：
				</td>
				<td>
					<input type=text name="unitData.areaid" id="areaid" value="${DisplayData.unitData.areaid}"/>
				</td>
			</tr>
			<tr>
				<td>
					序号：
				</td>
				<td>
					<input type=text name="unitData.sortno" id="sortno" value="${DisplayData.unitData.sortno}"/>
				</td>
			</tr>			
			<tr>
				<td colspan=2>
					<input type=button name="save" id="save" value="保存" onClick="saveUpdate()"/>
					<input type=button name="addsub" id="addsub"" value="增加子单位" onClick="addSub('')"/>
					<input type=button name="close" id="close"" value="关闭" onClick="closeWindow('')"/>
				</td>
			</tr>

		</table>
		<br>
	</form>

</body>

<script>
	var operType = '';
	var isUpdateSuccessed = false;
	var updatedRecordCount = parseInt('${DisplayData.updatedRecordCount}');
	$(function(){
		operType = $('#operType').val();

		if (updatedRecordCount > 0 ) {
			if (operType == 'add' || operType == 'addsub') {			
				window.opener.addNode('${DisplayData.unitData.parentid}', '${DisplayData.unitData.unitid}', '${DisplayData.unitData.unitname}', '');
			}
			if (operType == 'update') {
				window.opener.updateNode('${DisplayData.unitData.parentid}', '${DisplayData.unitData.unitid}', '${DisplayData.unitData.unitname}', '');
			}
		}
			
		if (operType == 'add' || operType == 'addsub') {
			$('#save').val("保存增加");
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
				$('#unitid').attr('readonly', true);
			}
		}
		if (operType == 'addsub') {
			$('#parentid').val('${DisplayData.unitData.parentid}');
			$('#parentUnitName').attr("readonly", true);
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('material');
		}

		if ('${DisplayData.unitData.unittype}' == '') {
			$("#unittype option:first").prop("selected", 'selected');
		} else {
			$('#unittype').val('${DisplayData.unitData.unittype}');
		}
		if ('${DisplayData.unitData.unitproperty}' == '') {
			$("#unitproperty option:first").prop("selected", 'selected');
		} else {
			$('#unitproperty').val('${DisplayData.unitData.unitproperty}');
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
			$('#parentUnitName').val("");	
			$('#unitId').val(id);
			$('#unitName').val(name);
		} else {
			$('#unitId').val("");
			$('#unitName').val("");
			$('#parentUnitName').val(id);	
		}
	}

	function inputCheck() {
		var str = '';
/*
		str = $('#parentUnitIdName').val();
		if (!inputStrCheck(str, "上级单位", 3, 8, true, true)) {
			return false;
		}
		str = $('#unitname').val();
		if (!inputStrCheck(str, "单位名称", 100, 7, false, true)) {
			return false;
		}
		str = $('#unitsimpledes').val();
		if (!inputStrCheck(str, "单位简称", 100, 7, true, true)) {
			return false;
		}
		str = $('#jianpin').val();
		if (!inputStrCheck(str, "单位简拼", 100, 7, true, true)) {
			return false;
		}		
		str = $('#orgid').val();
		if (!inputStrCheck(str, "单位编码", 100, 7, true, true)) {
			return false;
		}	
		str = $('#unitproperty').val();
		if (!inputStrCheck(str, "单位属性", 3, 3,  true, true)) {
			return false;
		}
		str = $('#unittype').val();
		if (!inputStrCheck(str, "单位性质", 3, 3,  true, true)) {
			return false;
		}
		str = $('#addresscode').val();
		if (!inputStrCheck(str, "地址（省市县）", 100, 7,  true, true)) {
			return false;
		}		
		str = $('#addressuser').val();
		if (!inputStrCheck(str, "地址（街道门牌号码）", 100, 7,  true, true)) {
			return false;
		}
		
		str = $('#postcode').val();
		if (!inputStrCheck(str, "邮编", 6, 3, true, true)) {
			return false;
		}
		str = $('#telphone').val();
		if (!inputStrCheck(str, "电话", 20, 5, true, true)) {
			return false;
		}
		str = $('#email').val();
		if (!inputStrCheck(str, "EMAIL", 50, 6, true, true)) {
			return false;
		}
		str = $('#mgrperson').val();
		if (!inputStrCheck(str, "负责人", 20, 7, true, true)) {
			return false;
		}
		str = $('#officeid').val();
		if (!inputStrCheck(str, "科室", 3, 8, true, true)) {
			return false;
		}
		str = $('#areaid').val();
		if (!inputStrCheck(str, "区域码", 120, 8, true, true)) {
			return false;
		}
		str = $('#sortno').val();
		if (!inputStrCheck(str, "序号", 120, 3, true, true)) {
			return false;
		}
*/
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

	function selectUnit() {
		callUnitSelect("parentUnitId", "parentUnitName", "0");
	}

	function selectAddress() {
		callDicSelect("addresscode", "address", 'A2', '1' );
	}
	
	function addSub() {
		if ($('#parentUnitName').val() == '') {
			$('#parentUnitName').val($('#unitname').val());
			$('#parentUnitId').val($('#unitid').val());
		}
		
		$('#unitname').val("");
		$('#unitsimpledes').val("");
		$('#jianpin').val("");
		$('#orgid').val("");
		//$('#unitproperty').val("");
		//$('#unittype').val("");
		$("#unitproperty option:first").prop("selected", 'selected');
		$("#unittype option:first").prop("selected", 'selected');
		$('#address').val("");
		$('#postcode').val("");
		$('#telphone').val("");
		$('#email').val("");
		$('#mgrperson').val("");
		$('#officeid').val("");
		$('#areaid').val("");
		$('#sortno').val("");		
	}
	
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

	function refreshOpenerData() {
		if (operType == 'add' || operType == 'addsub' || operType == 'update') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}

	function clearUnit() {
		$('#parentUnitId').val('');
		$('#parentUnitName').val('');
	}
	
</script>
</html>