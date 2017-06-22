<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<div id="container">
	<div id="main">
		<div style="height:10px"></div>
		<fieldset>
		<legend>字典类型编辑</legend>
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
			<input type=hidden name="dicTypeId" id="dicTypeId" value="${DisplayData.dicTypeData.dictypeid}"/>
			<div class="">
				<table width=100% style=" border-collapse: separate;border-spacing:6px;"  >
				<tr>
					<td>
						代码类ID：
					</td>
					<td>
						<input type=text name="dictypeid" id="dictypeid" value="${DisplayData.dicTypeData.dictypeid}"/>
					</td>
					<td>
						代码类名称：
					</td>
					<td>
						<input type=text name="dictypename" id="dictypename" value="${DisplayData.dicTypeData.dictypename}"/>
					</td>
				</tr>			
				<tr>
					<td>
						代码类属性：
					</td>
					<td>
						<select name="dictypelevel" id="dictypelevel">
							<c:forEach items="${DisplayData.dicTypeLevelList}" var="value" varStatus="status">
								<option value ="${value[0]}">${value[1]}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						代码采集层次：
					</td>
					<td>
						<select name="dicselectedflag" id="dicselectedflag">
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
						<input type=text name="sortno" id="sortno" value="${DisplayData.dicTypeData.sortno}"/>
					</td>
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
				dictypeid: {
					required: true,	
					maxlength: 10,
				},
				dictypename: {
					required: true,
					maxlength: 60,
				},
				dictypelevel: {
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
			//setPageReadonly('dic');
			$('#save').attr("disabled", true);
		}

		$('#dictypelevel').val('${DisplayData.dicTypeData.dictypelevel}');
		$('#dicselectedflag').val('${DisplayData.dicTypeData.dicselectedflag}');
		if ('${DisplayData.dicTypeData.enableflag}' == '0') {
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

		str = $('#dictypeid').val();
		if (!inputStrCheck(str, "代码类ID", 10, 8, false, true)) {
			return false;
		}
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
		if (validatorBaseInfo.form()) {
			if (confirm("确定要保存吗？")) {
				var actionUrl;
				if (operType == 'add') {
					actionUrl = "${ctx}/dictype?methodtype=add";
				} else {
					actionUrl = "${ctx}/dictype?methodtype=update";
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
		if (operType == 'add' || operType == 'update') {
			var goPageIndex = parseInt($(window.opener.document.getElementById('pageIndex')).val()) - 1;
			window.opener.goToPage('form', goPageIndex, '');
			
		}
		return true;
	}

	
</script>
</html>