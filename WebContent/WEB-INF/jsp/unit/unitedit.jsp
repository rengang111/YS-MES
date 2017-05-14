<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common2.jsp"%>
<html>

<body class="easyui-layout">
<div id="container">
	<div id="main">
		<div style="height:10px"></div>
		<fieldset>
		<legend>机构信息维护</legend>
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
			<input type=hidden name="unitid" id="unitid" value="${DisplayData.unitData.unitid}"/>
			<table width=100%>
				<tr>
					<td width="180px">
						上级单位：
					</td>
					<td>
						<input type=text name="parentUnitName" id="parentUnitName" value="${DisplayData.parentUnitName}"/>
						<input type=hidden name="parentUnitId" id="parentUnitId" value="${DisplayData.parentUnitId}"/>
					</td>
					<td width="120px">
						单位名称<font color="#F00">(*)</font>：
					</td>
					<td>
						<input type=text name="unitname" id="unitname" value="${DisplayData.unitData.unitname}"/>
					</td>
				</tr>
				<tr>
					<td>
						单位简称：
					</td>
					<td>
						<input type=text name="unitsimpledes" id="unitsimpledes" value="${DisplayData.unitData.unitsimpledes}"/>
					</td>
					<td>
						单位简拼：
					</td>
					<td>
						<input type=text name="jianpin" id="jianpin" value="${DisplayData.unitData.jianpin}"/>
					</td>
				</tr>
			
				<tr>
					<td>
						单位编码：
					</td>
					<td>
						<input type=text name="orgid" id="orgid" value="${DisplayData.unitData.orgid}"/>
					</td>
					<td>
						单位属性：
					</td>
					<td>
						<select name="unitproperty" id="unitproperty">
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
						<select name="unittype" id="unittype">
							<c:forEach items="${DisplayData.unitTypeList}" var="value" varStatus="status">
								<option value ="${value[0]}">${value[1]}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						地址（省市县）：
					</td>
					<td>
						<input type=text name="address" id="address" value="${DisplayData.address}"/>
						<input type=hidden name="addresscode" id="addresscode" value="${DisplayData.unitData.addresscode}"/>
					</td>
				</tr>
				<tr>
					<td>
						地址（街道门牌号码）：
					</td>
					<td>
						<input type=text name="addressuser" id="addressuser" value="${DisplayData.unitData.addressuser}"/>
					</td>
					<td>
						邮编：
					</td>
					<td>
						<input type=text name="postcode" id="postcode" value="${DisplayData.unitData.postcode}"/>
					</td>
				</tr>
				<tr>
					<td>
						电话：
					</td>
					<td>
						<input type=text name="telphone" id="telphone" value="${DisplayData.unitData.telphone}"/>
					</td>
					<td>
						EMAIL：
					</td>
					<td>
						<input type=text name="email" id="email" value="${DisplayData.unitData.email}"/>
					</td>
				</tr>
				<tr>
					<td>
						负责人：
					</td>
					<td>
						<input type=text name="mgrperson" id="mgrperson" value="${DisplayData.unitData.mgrperson}"/>
					</td>
					<td>
						科室：
					</td>
					<td>
						<input type=text name="officeid" id="officeid" value="${DisplayData.unitData.officeid}"/>
					</td>
				</tr>
				<tr>
					<td>
						区域码：
					</td>
					<td>
						<input type=text name="areaid" id="areaid" value="${DisplayData.unitData.areaid}"/>
					</td>
					<td>
						序号：
					</td>
					<td>
						<input type=text name="sortno" id="sortno" value="${DisplayData.unitData.sortno}"/>
					</td>
				</tr>			
				<tr rowspan=3><td>&nbsp;</td></tr>			
				<tr>
					<td colspan=4>
						<button type="button" id="close" class="DTTT_button" onClick="doReturn();"
								style="height:25px;margin:0px 5px 0px 0px;float:right;" >返回</button>
						<button type="button" id="addsub" class="DTTT_button" onClick="addSub();"
								style="height:25px;margin:0px 5px 0px 0px;float:right;" >增加子单位</button>
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
	$(function(){
		
		autoCompleteParentUnit();
		
		autoCompleteAddress();
		
		validatorBaseInfo = $("#form").validate({
			rules: {
				parentUnitIdName: {
					maxlength: 3,
				},
				unitname: {
					required: true,								
					maxlength: 100,
				},
				unitsimpledes: {
					maxlength: 100,
				},
				jianpin: {				
					maxlength: 100,
				},
				orgid	: {				
					maxlength: 100,
				},
				unitproperty: {
					maxlength: 3,
				},
				unittype: {
					maxlength: 3,
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
				email: {
					maxlength: 50,
				},
				mgrperson: {
					maxlength: 20,
				},
				officeid: {
					maxlength: 3,
				},
				areaid: {
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
			setPageReadonly('unit');
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

		return true;	
	}

	function saveUpdate() {
		
		if (validatorBaseInfo.form()) {
			if (confirm("确定要保存吗？")) {
				var actionUrl;
				if (operType == 'add' || operType == 'addsub') {
					actionUrl = "${ctx}/unit?methodtype=add";
				} else {
					actionUrl = "${ctx}/unit?methodtype=update";
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
		$('#addressuser').val("");
		$('#postcode').val("");
		$('#telphone').val("");
		$('#email').val("");
		$('#mgrperson').val("");
		$('#officeid').val("");
		$('#areaid').val("");
		$('#sortno').val("");		
	}
	
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
	
	function autoCompleteParentUnit() { 
		$("#parentUnitName").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/unit?methodtype=unitSearch",
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

				$("#parentUnitName").val(ui.item.value);
				$("#parentUnitId").val(ui.item.id);
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#parentUnitName").val('');
                    $('#parentUnitId').val('');
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
					url : "${ctx}/unit?methodtype=addressSearch",
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