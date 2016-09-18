<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	代码维护
</head>
<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
		<input type=hidden name="dicId" id="dicId" value="${DisplayData.dicData.dicid}"/>
		<table>
			<tr>
				<td>
					代码类：
				</td>
				<td>
					<input type=text name="dicTypeName" id="dicTypeName" value="${DisplayData.dicTypeName}" readonly/>
					<input type=hidden name="dicData.dictypeid" id="dictypeid" value="${DisplayData.dicData.dictypeid}"/>
					<input type=button name="dicTypeSelect" id="dicTypeSelect" value="选择" onClick="selectDicType()" />
				</td>
			</tr>
			<tr>
				<td>
					代码ID：
				</td>
				<td>
					<input type=text name="dicData.dicid" id="dicid" value="${DisplayData.dicData.dicid}"/>
				</td>
			</tr>			
			<tr>
				<td>
					代码名称：
				</td>
				<td>
					<input type=text name="dicData.dicname" id="dicname" value="${DisplayData.dicData.dicname}"/>
				</td>
			</tr>
			<tr>
				<td>
					简拼：
				</td>
				<td>
					<input type=text name="dicData.jianpin" id="jianpin" value="${DisplayData.dicData.jianpin}"/>
				</td>
			</tr>				
			<tr>
				<td>
					代码描述：
				</td>
				<td>
					<input type=text name="dicData.dicdes" id="dicdes" value="${DisplayData.dicData.dicdes}"/>
				</td>
			</tr>
			<tr>
				<td>
					上级代码：
				</td>
				<td>
					<input type=text name="dicParentName" id="dicParentName" value="${DisplayData.dicParentName}" readonly/>
					<input type=hidden name="dicData.dicprarentid" id="dicprarentid" value="${DisplayData.dicData.dicprarentid}"/>
					<input type=button name="dicParentIdSelect" id="dicParentIdSelect" value="选择" onClick="selectDicParentId()" />
					<input type=button name="dicParentIdClear" id="dicParentIdClear" value="清空" onClick="clearDicParentId()" />
				</td>
			</tr>
			<tr>
				<td>
					是否叶子节点：
				</td>
				<td>
					<select name="dicData.isleaf" id="dicisleaf">
						<option value ="0">否</option>
						<option value ="1">是</option>
					</select>
				</td>
			</tr>
						
			<tr>
				<td>
					序号：
				</td>
				<td>
					<input type=text name="dicData.sortno" id="sortno" value="${DisplayData.dicData.sortno}"/>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<input type=checkbox name="dicData.enableflag" id="enableflag" value="0"/>选中有效
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
			
		if (operType == 'add' || operType == 'addsub' || operType == 'addsubfromtype') {
			$('#save').val("保存增加");
			if (operType == 'addsub') {
				$('#dicParentIdSelect').attr('disabled', 'disabled');
			}			
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
				$('#dicid').attr('readonly', true);
			}
		}
		if ('${DisplayData.isOnlyView}' != '') {
			setPageReadonly('dic');
		}

		if ('${DisplayData.dicData.enableflag}' != '1') {
			$('#enableflag').attr("checked", "true");
		}

		if ('${DisplayData.dicData.isleaf}' != '') {
			$('#dicisleaf').val("${DisplayData.dicData.isleaf}");
		} else {
			$('#dicisleaf').val("1");
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

		str = $('#dicTypeName').val();
		if (!inputStrCheck(str, "代码类", 10, 7, false, true)) {
			return false;
		}
		str = $('#dicid').val();
		if (!inputStrCheck(str, "代码ID", 120, 3, false, true)) {
			return false;
		}
		if (str.length < 3) {
			$('#dicid').val(pad(str, 3));
		} else {
			if (str.length % 3 != 0) {
				alert("编码规则：每一段的编码应为3位，如001");
				return;
			}
		}
		str = $('#dicname').val();
		if (!inputStrCheck(str, "代码名称", 60, 7, false, true)) {
			return false;
		}
		str = $('#jianpin').val();
		if (!inputStrCheck(str, "简拼", 30, 7, true, true)) {
			return false;
		}		
		str = $('#dicParentName').val();
		if (!inputStrCheck(str, "上级代码", 120, 7, true, true)) {
			return false;
		}		
		str = $('#dicisleaf').val();
		if (!inputStrCheck(str, "是否叶子节点", 1, 3, false, true)) {
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
				if (operType == 'add' || operType == 'addsub' || operType == 'addsubfromtype') {
					$('#form').attr("action", "${ctx}/diccode?methodtype=add");
				} else {
					$('#form').attr("action", "${ctx}/diccode?methodtype=update");
				}
				$('#form').submit();
			}
		}	
	}
	
	function closeWindow(isNeedConfirm) {
		if (isNeedConfirm == '') {
			if (operType == 'add' || operType == 'addsub' ||  operType == 'update' || operType == 'addsubfromtype') {
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
		if (operType == 'add' || operType == 'addsub' || operType == 'update' || operType == 'addsubfromtype') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}

	function selectDicType() {
		callDicTypeSelect("dictypeid", "dicTypeName", "0", "", "");
	}
	
	function selectDicParentId() {
		if ($('#dicTypeName').val() == '') {
			alert("请先选择代码类");
			return false;
		}

		callDicTypeSelect("dicprarentid", "dicParentName", "1", $('#dictypeid').val(), "");
	}

	function clearDicParentId() {
		$('#dicprarentid').val('');
		$('#dicParentName').val('');
	}
	
</script>
</html>