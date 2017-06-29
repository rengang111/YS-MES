<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<div id="container">
	<div id="main">
		<div style="height:10px"></div>
		<fieldset>
		<legend>字典编辑</legend>
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
			<input type=hidden name="dicId" id="dicId" value="${DisplayData.dicData.dicid}"/>
				<div class="">
					<table width=100% style=" border-collapse: separate;border-spacing:6px;"  >
						<tr>
							<td>
								代码类：
							</td>
							<td>
								<input type=text name="dicTypeName" id="dicTypeName" value="${DisplayData.dicTypeName}" onfocusin="setReadonly(this, true);" onBlur="setReadonly(this, false);"/>
								<input type=hidden name="dictypeid" id="dictypeid" value="${DisplayData.dicData.dictypeid}"/>
								<button type="button" id="selectdictype" class="DTTT_button" onClick="selectDicType();"
									style="height:25px;margin:0px 5px 0px 0px;" >选择</button>
							</td>
						<td>
							代码ID：
						</td>
						<td>
							<input type=text name="dicid" id="dicid" value="${DisplayData.dicData.dicid}"/>
						</td>
					</tr>			
					<tr>
						<td>
							代码名称：
						</td>
						<td>
							<input type=text name="dicname" id="dicname" value="${DisplayData.dicData.dicname}"/>
						</td>
						<td>
							简拼：
						</td>
						<td>
							<input type=text name="jianpin" id="jianpin" value="${DisplayData.dicData.jianpin}"/>
						</td>
					</tr>				
					<tr>
						<td>
							代码描述：
						</td>
						<td>
							<input type=text name="dicdes" id="dicdes" value="${DisplayData.dicData.dicdes}"/>
						</td>
						<td>
							上级代码：
						</td>
						<td>
							<input type=text name="dicParentName" id="dicParentName" value="${DisplayData.dicParentName}" readonly/>
							<input type=hidden name="dicprarentid" id="dicprarentid" value="${DisplayData.dicData.dicprarentid}"/>
							<button type="button" id="selectdicparentid" class="DTTT_button" onClick="selectDicParentId();"
								style="height:25px;margin:0px 0px 0px 0px;" >选择</button>
							<button type="button" id="clear" class="DTTT_button" onClick="clearDicParentId();"
								style="height:25px;margin:0px 0px 0px 0px;" >清空</button>

						
						</td>
					</tr>
					<tr>
						<td>
							是否叶子节点：
						</td>
						<td>
							<select name="isleaf" id="dicisleaf">
								<option value ="0">否</option>
								<option value ="1">是</option>
							</select>
						</td>
						<td>
							序号：
						</td>
						<td>
							<input type=text name="sortno" id="sortno" value="${DisplayData.dicData.sortno}"/>
						</td>
					</tr>
					<tr>
						<td colspan=2>
							<input type=checkbox name="enableflag" id="enableflag" value="0"/>选中有效
						</td>
					</tr>					
					<tr>
						<td colspan=4>
							<button type="button" id="close" class="DTTT_button" onClick="doReturn();"
									style="height:25px;margin:0px 5px 0px 0px;float:right;" >返回</button>
							<button type="button" id="save" class="DTTT_button" onClick="saveUpdate();"
									style="height:25px;margin:0px 5px 0px 0px;float:right;" >保存</button>
						</td>
					</tr>
		
				</table>
			</div>
		<br>
	</form>

</body>

<script>
	var operType = '';
	var isUpdateSuccessed = false;
	var updatedRecordCount = parseInt('${DisplayData.updatedRecordCount}');
	var validatorBaseInfo;
	$(function(){
		
		validatorBaseInfo = $("#form").validate({
			rules: {
				dicTypeName: {
					required: true,	
				},
				dicname: {
					required: true,
					maxlength: 60,
				},
				dicid: {
					required: true,								
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
				$('#selectdictype').attr("disabled", true);
			}
		}
		if ('${DisplayData.isOnlyView}' != '') {
			//setPageReadonly('dic');
			$('#save').attr("disabled", true);
			$('#selectdicparentid').attr("disabled", true);
			$('#selectdictype').attr("disabled", true);
			$('#clear').attr("disabled", true);
		}

		if ('${DisplayData.dicData.enableflag}' == '0') {
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

	function setReadonly(ctl, flag) {
		$(ctl).attr('readonly', flag);		
	}
	
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
		if (validatorBaseInfo.form()) {
			if (confirm("确定要保存吗？")) {
				
				var actionUrl;
				
				if (operType == 'add' || operType == 'addsub' || operType == 'addsubfromtype') {
					actionUrl = "${ctx}/diccode?methodtype=add";
				} else {
					actionUrl = "${ctx}/diccode?methodtype=update";
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
							reloadTabWindow();
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
		if (operType == 'add' || operType == 'addsub' || operType == 'update' || operType == 'addsubfromtype') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}

	function selectDicType() {
		var parentindex = parent.layer.getFrameIndex(window.name);
		callDicTypeSelect("dictypeid", "dicTypeName", "0", "", "", parentindex);
	}
	
	function selectDicParentId() {
		if ($('#dicTypeName').val() == '') {
			alert("请先选择代码类");
			return false;
		}
		var parentindex = parent.layer.getFrameIndex(window.name);
		callDicTypeSelect("dicprarentid", "dicParentName", "1", $('#dictypeid').val(), "", parentindex);
	}

	function clearDicParentId() {
		$('#dicprarentid').val('');
		$('#dicParentName').val('');
	}

	function setDicType(id, name) {
		$('#dictypeid').val(id);
		$('#dicTypeName').val(name);
	}
</script>
</html>