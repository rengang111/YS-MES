<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	代码类维护
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
		<input type=hidden name="dicTypeId" id="dicTypeId" value="${DisplayData.dicTypeData.dictypeid}"/>
		<table>
			<tr>
				<td>
					代码类ID：
				</td>
				<td>
					<input type=text name="dicTypeData.dictypeid" id="dictypeid" value="${DisplayData.dicTypeData.dictypeid}"/>
				</td>
			</tr>
			<tr>
				<td>
					代码类名称：
				</td>
				<td>
					<input type=text name="dicTypeData.dictypename" id="dictypename" value="${DisplayData.dicTypeData.dictypename}"/>
				</td>
			</tr>			
			<tr>
				<td>
					代码类属性：
				</td>
				<td>
					<select name="dicTypeData.dictypelevel" id="dictypelevel">
						<c:forEach items="${DisplayData.dicTypeLevelList}" var="value" varStatus="status">
							<option value ="${value[0]}">${value[1]}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		
			<tr>
				<td>
					代码采集层次：
				</td>
				<td>
					<select name="dicTypeData.dicselectedflag" id="dicselectedflag">
						<c:forEach items="${DisplayData.dicSelectedFlagList}" var="value" varStatus="status">
							<option value ="${value[0]}">${value[1]}</option>
						</c:forEach>
					</select>
				</td>
			</tr>		
			<tr>
				<td>
					序号：
				</td>
				<td>
					<input type=text name="dicTypeData.sortno" id="sortno" value="${DisplayData.dicTypeData.sortno}"/>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<input type=checkbox name="dicTypeData.enableflag" id="enableflag" value="0"/>选中有效
				</td>
			</tr>					
			<tr>
				<td colspan=2>
					<input type=button name="save" id="save" value="保存" onClick="saveUpdate()"/>
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
		
		if (operType == 'add') {
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
				$('#dictypeid').attr('readonly', true);
			}
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('dic');
		}

		$('#dictypelevel').val('${DisplayData.dicTypeData.dictypelevel}');
		$('#dicselectedflag').val('${DisplayData.dicTypeData.dicselectedflag}');
		if ('${DisplayData.dicTypeData.enableflag}' != '1') {
			$('#enableflag').attr("checked", "true");
		}
		
		if (updatedRecordCount > 0) {
			refreshOpenerData();
		}
		
		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	});

	function inputCheck() {
		var str = '';

		//str = $('#dictypeid').val();
		//if (!inputStrCheck(str, "代码类ID", 10, 8, false, true)) {
		//	return false;
		//}
		str = $('#dictypename').val();
		if (!inputStrCheck(str, "代码类名称", 60, 7, false, true)) {
			return false;
		}
		str = $('#dictypelevel').val();
		if (!inputStrCheck(str, "代码类属性", 3, 3, false, true)) {
			return false;
		}
		str = $('#dicselectedflag').val();
		if (!inputStrCheck(str, "代码采集层次", 3, 3, true, true)) {
			return false;
		}		

		str = $('#sortno').val();
		if (!inputStrCheck(str, "序号", 2, 3, true, true)) {
			return false;
		}

		return true;	
	}

	function saveUpdate() {
		if (inputCheck()) {
			if (confirm("确定要保存吗？")) {
				if (operType == 'add') {
					$('#form').attr("action", "${ctx}/dictype?methodtype=add");
				} else {
					$('#form').attr("action", "${ctx}/dictype?methodtype=update");
				}
				$('#form').submit();
			}
		}	
	}
	
	function closeWindow(isNeedConfirm) {
		if (isNeedConfirm == '') {
			if (operType == 'add' || operType == 'update') {
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
		if (operType == 'add' || operType == 'update') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}

	
</script>
</html>