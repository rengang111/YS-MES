<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具登记</title>
<script type="text/javascript">
	var validatorBaseInfo;
	var layerHeight = "250";
	var sumPrice = 0.0;
	var paid = 0.0;
	
	function PrefixInteger(num, length) {
		 return (Array(length).join('0') + num).slice(-length);
	} 
	
	function doRotate(direct) {
		
		$('#rotateDirect').val(direct);
		var actionUrl = "${ctx}/business/mouldregister?methodtype=rotate";
		
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : actionUrl,
			data : JSON.stringify($('#mouldBaseInfo').serializeArray()),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					if (d.info == $('#keyBackup').val()) {
						if (direct == 0) {
							alert("当前已经是第一条数据了");
						} else {
							alert("当前已经是最后一条数据了");
						}
					} else {
						var url = "${ctx}/business/mouldregister?methodtype=updateinit&key=" + d.info;
						$(window.location).attr('href', url);
					}
				}
				
				//不管成功还是失败都刷新父窗口，关闭子窗口
				//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();
				//parent.layer.close(index); //执行关闭
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//alert(XMLHttpRequest.status);					
				//alert(XMLHttpRequest.readyState);					
				//alert(textStatus);					
				//alert(errorThrown);
			}
		});
	}
	
	function getMouldId() {
		var actionUrl = "${ctx}/business/mouldregister?methodtype=getMouldId";
		
		if ($('#productModelId').val() != "") {
		
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#mouldBaseInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						$('#mouldId').html('<font color="red">' + d.info + '</font>');
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();
					//parent.layer.close(index); //执行关闭
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);					
					//alert(XMLHttpRequest.readyState);					
					//alert(textStatus);					
					//alert(errorThrown);
				}
			});
		} else {
			$('#mouldId').html("");
		}
	}
	
	function addSubCodeTr(){
		 
		var i = $("#subidTab tr").length - 1;	
		var subid = PrefixInteger(i, 3);
		
		var trHtml = "";
		trHtml+="<tr>";	
		trHtml+="<td class='td-center' width='200px'>";		  
		trHtml+="<input name='mouldSubs[" + i + "].subcode'    id='mouldSubs[" + i + "].subcode' type='text' value='" + subid + "' class='small center' />";
		trHtml+="</td>";
		trHtml+="<td class='td-center' colspan=2>";
		trHtml+="<input name='mouldSubs[" + i + "].name' id='mouldSubs[" + i + "].name' type='text' class='middle'/>";
		trHtml+="</td>";
		trHtml+="</tr>";
		
		$('#subidTab tr:last').after(trHtml);
		
		$('#mouldSubs\\[' + i + '\\]\\.subcode').rules('add', { noDuplicateSubcode: true});
		$('#mouldSubs\\[' + i + '\\]\\.subcode').rules('add', { maxlength: 3 });
		$('#mouldSubs\\[' + i + '\\]\\.name').rules('add', { maxlength: 50 });
		
		$('#subCodeCount').val($("#subidTab tr").length - 1);
	}

	
	function addFactoryTr(){
		 
		var i = $("#factoryTable tr").length - 1;
		
		var trHtml = "";
		trHtml+="<tr>";	
		trHtml+="<td class='td-center'>";	
		trHtml+="<input type=hidden name='detailLines[" + i + "].mouldfactoryid' id='detailLines[" + i + "].mouldfactoryid' class='small center' />";
		trHtml+="<input name='detailLines1[" + i + "].code' id='detailLines1[" + i + "].code' type='text' value='' class='attributeList1 center'/>";
		trHtml+="</td>";
		trHtml+="<td>";
		trHtml+="<input name='detailLines1[" + i + "].name' id='detailLines1[" + i + "].name' type='text' class='attributeList1' disabled/>";
		trHtml+="</td>";
		trHtml+="<td colspan=2>";
		trHtml+="<input name='detailLines[" + i + "].price' id='detailLines[" + i + "].price' type='text' class='attributeList1'/>";
		trHtml+="</td>";
		trHtml+="</tr>";
		
		$('#factoryTable tr:last').after(trHtml);
		
		$('#detailLines\\[' + i + '\\]\\.price').rules('add', { number: true, maxlength: 10 });
		$('#detailLines1\\[' + i + '\\]\\.code').rules('add', { noDuplicateFactory: true});
		
		autoCompleteFactory(i);
		
		$('#factoryCount').val($("#factoryTable tr").length - 1);
		
	}
	
	
	function initEvent(){
		
		$('#factoryTable').width(750);
		
		autoComplete();
		
		validatorBaseInfo = $("#mouldBaseInfo").validate({
			rules: {
				type: {
					required: true,
					minlength: 1,
					maxlength: 4,
				},
				productModelIdView: {
					required: true,				
					maxlength: 120,
				},
				mouldFactoryId: {
					required: true,								
					maxlength: 72,
				},
				name: {
					required: true,
					maxlength: 50,
				},
				materialQuality: {				
					required: true,
					maxlength: 72,
				},
				size: {				
					required: true,
					maxlength: 50,
				},
				weight: {
					required: true,
					maxlength: 5,
				},
				unloadingNum: {
					required: true,
					maxlength: 5,
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
			
	    jQuery.validator.addMethod("noDuplicateSubcode",function(value, element){
	    	var rtnValue = true;

	    	if (value != "") {
				for(var i = 0; i < $('#subCodeCount').val(); i++) {
					if (element.id != ("mouldSubs[" + i + "].subcode")) {
						if (value == $('#mouldSubs\\[' + i + '\\]\\.subcode').val()) {
							rtnValue = false;
							break;
						}
					}	
				}
	    	}

	        return rtnValue;   
	    }, "子编码重复");
	    
	    jQuery.validator.addMethod("noDuplicateFactory",function(value, element){
	    	var rtnValue = true;

	    	if (value != "") {
				for(var i = 0; i < $('#factoryCount').val(); i++) {
					if (element.id != ("detailLines1[" + i + "].code")) {
						if (value == $('#detailLines1\\[' + i + '\\]\\.code').val()) {
							rtnValue = false;
							break;
						}
					}	
				}
	    	}

	        return rtnValue;   
	    }, "模具工厂重复"); 

	    
		controlButtons($('#keyBackup').val());
		$("#productModelId").val('${DisplayData.mouldBaseInfoData.productmodelid}');
		$("#productModelIdView").val('${DisplayData.productModelIdView}');
		$("#productModelName").val('${DisplayData.productModelName}');
		if ('${DisplayData.mouldBaseInfoData.type}' != '') {
			$('#type').val('${DisplayData.mouldBaseInfoData.type}');
		} else {
			$("#type option:first").prop("selected", 'selected');
		}
		
		if ($('#keyBackup').val() == '') {
			addFactoryTr();
		} else {
			<c:forEach items="${DisplayData.mouldSubDatas}" var="item">
				addSubCodeTr();
				var index = $('#subCodeCount').val();
				index--;
				$('#mouldSubs\\[' + index + '\\]\\.subcode').val('${item.subCode}');
				$('#mouldSubs\\[' + index + '\\]\\.name').val('${item.name}');
			</c:forEach>
			<c:forEach items="${DisplayData.mouldFactoryDatas}" var="item">
				addFactoryTr();
				var index = $('#factoryCount').val();
				index--;
				$('#detailLines\\[' + index + '\\]\\.mouldfactoryid').val('${item.mouldFactoryId}');
				$('#detailLines1\\[' + index + '\\]\\.code').val('${item.no}');
				$('#detailLines1\\[' + index + '\\]\\.name').val('${item.factoryName}');
				$('#detailLines\\[' + index + '\\]\\.price').val('${item.price}');
			</c:forEach>
			
			//$('#tabs').show();
			$('#tabs').css('display','inline-block');
			$('#rotateArea').css('display','inline-block');
		}
	}

	function setViewOnly() {
		$('#acceptanceDate').attr("disabled", true);
		$('#result').attr("disabled", true);
		$('#memo').attr("disabled", true);
		$('#updateacceptance').attr("disabled", true);
		$('#confirmacceptance').attr("disabled", true);
	}
/*	
	$(window).load(function(){
		initEvent();
	});
*/
	$(document).ready(function() {
		
		initEvent();
		
	})
	
	function doSave(isContinue) {

		if (validatorBaseInfo.form()) {
			
			var message = "${DisplayData.endInfoMap.message}";
			
			if ($('#keyBackup').val() == "") {				
				//新建
				actionUrl = "${ctx}/business/mouldregister?methodtype=add";
			} else {
				//修正
				actionUrl = "${ctx}/business/mouldregister?methodtype=update";
			}		
			
			if (confirm(message)) {
				var actionUrl;			
	
				//将提交按钮置为【不可用】状态
				//$("#submit").attr("disabled", true); 
				$.ajax({
					type : "POST",
					contentType : 'application/json',
					dataType : 'json',
					url : actionUrl,
					data : JSON.stringify($('#mouldBaseInfo').serializeArray()),// 要提交的表单
					success : function(d) {
						if (d.rtnCd != "000") {
							alert(d.message);	
						} else {
							parent.reload();
							if (isContinue == 0) {
								//不管成功还是失败都刷新父窗口，关闭子窗口
								//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
								//parent.$('#events').DataTable().destroy();
								//parent.layer.close(index); //执行关闭
								$('#tabs').css('display','inline-block');
								$('#rotateArea').css('display','inline-block');
								var x = new Array();
								x = d.info.split("|");
								controlButtons(x[0]);
								$('#mouldId').html(x[1]);
							} else {
								$('#keyBackup').val("");
								getMouldId();
								rowCount = $("#subidTab tr").length;
								for(var i = 1; i < rowCount; i++) {
									$("#subidTab").find("tr").eq(i).remove();
								}
								$('#subCodeCount').val(0);
								
								rowCount = $("#factoryTable tr").length;
								for(var i = 1; i < rowCount; i++) {
									$("#factoryTable").find("tr").eq(i).remove();
								}
								addFactoryTr();
								
							}							
							
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
	
	function doDelete() {
		
		if (confirm("${DisplayData.endInfoMap.message}")) {
			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : "${ctx}/business/mouldregister?methodtype=deleteDetail",
				data : $('#keyBackup').val(),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						controlButtons("");
						clearAll();
						parent.reload();
					}
						
					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();/
					//parent.reload_contactor();
					parent.layer.close(index); //执行关闭
					
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
	
	function controlButtons(data) {
		$('#keyBackup').val(data);
		if (data == '') {
			$('#delete').attr("disabled", true);
			$('#printmd').attr("disabled", true);
			$('#deletemd').attr("disabled", true);
			$('#addmd').attr("disabled", true);
			$('#acceptanceDate').attr("disabled", true);
			$('#result').attr("disabled", true);
			$('#memo').attr("disabled", true);
			$('#updateacceptance').attr("disabled", true);
			$('#confirmacceptance').attr("disabled", true);
			$('#withhold').attr("disabled", true);
			$('#deletepay').attr("disabled", true);
			$('#addpay').attr("disabled", true);
			
		} else {
			$('#delete').attr("disabled", false);
			$('#printmd').attr("disabled", false);
			$('#deletemd').attr("disabled", false);
			$('#addmd').attr("disabled", false);
			$('#acceptanceDate').attr("disabled", false);
			$('#result').attr("disabled", false);
			$('#memo').attr("disabled", false);
			$('#updateacceptance').attr("disabled", false);
			$('#confirmacceptance').attr("disabled", false);
			$('#withhold').attr("disabled", false);
			$('#deletepay').attr("disabled", false);
			$('#addpay').attr("disabled", false);
		}
	}
	
	function clearAll() {
		sumPrice = 0;
		paid = 0;
		$('#contractId').val("");
		$('#productModelId').val("");
		$('#productModelIdView').val("");
		$("#productModelName").val("");
		$('#mouldFactoryId').val("");
	
	}
	function autoComplete() { 
		$("#productModelIdView").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/business/mouldregister?methodtype=productModelIdSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								//alert(item.viewList)
								return {
									label : item.viewList,
									value : item.name,
									id : item.id,
									name: item.name,
									des : item.des
								}
							}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},

			select : function(event, ui) {
				$("#productModelId").val(ui.item.id);
				$("#productModelIdView").val(ui.item.name);
				$("#productModelName").val(ui.item.des);
				//$("#factoryProductCode").focus();
			},
			minLength : 1,
			autoFocus : false,
			width: 200,
		});	
	}
	
	
	function autoCompleteFactory(index) { 
		$("#detailLines1\\[" + index + "\\]\\.code").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/business/mouldregister?methodtype=factoryIdSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								//alert(item.viewList)
								return {
									label : item.viewList,
									value : item.no,
									id : item.id,
									name: item.no,
									des : item.fullName
								}
							}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
						alert("系统异常，请再试或和系统管理员联系。");
					}
				});
			},

			select : function(event, ui) {
				$("#detailLines\\[" + index + "\\]\\.mouldfactoryid").val(ui.item.id);
				$("#detailLines1\\[" + index + "\\]\\.code").val(ui.item.name);
				$("#detailLines1\\[" + index + "\\]\\.name").val(ui.item.des);
			},
			minLength : 1,
			autoFocus : false,
			width: 200,
		});	
	}
	
	function doReturn() {
		//var url = "${ctx}/business/externalsample";
		//location.href = url;	
		var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		//parent.$('#events').DataTable().destroy();/
		//parent.reload_contactor();
		parent.layer.close(index); //执行关闭
		
	}
</script>
</head>

<body>
<div id="container">

		<div id="main">
			<form:form modelAttribute="dataModels" id="mouldBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<input type=hidden id='productModelId' name='productModelId'/>
				<input type=hidden id="subCodeCount" name="subCodeCount" value=""/>
				<input type=hidden id="factoryCount" name="factoryCount" value=""/>
				<input type=hidden id="rotateDirect" name="rotateDirect" value=""/>
				<legend>模具-基本信息</legend>
				<div style="height:10px"></div>
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave(1);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存(连续登记)</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave(0);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<div style="height:10px"></div>
				<table class="form" width="1100px" cellspacing="0" style="table-layout:fixed">
					<tr>
						<td width="60px">编号：</td>
						<td width="130px">
							<label id="mouldId" name="mouldId" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.mouldid}</label>
						</td>
						<td width="60px">产品型号：</td>
						<td width="130px">
							<form:input path="productModelIdView" class="required mini" onblur="getMouldId();"/>
						</td>
						<td width="60px">产品名称：</td>
						<td width="130px">
							<form:input path="productModelName"	class="read-only short" />
						</td>
						<td width="60px">模具类型：</td>
						<td width="130px">
							<form:select path="type" onChange="getMouldId();">
								<form:options items="${DisplayData.typeList}" itemValue="key"
									itemLabel="value" />
							</form:select>
						</td>
						<td width="50px">
							出模数：
						</td>
						<td width="100px">
							<input type="text" id="unloadingNum" name="unloadingNum" class="mini" value="${DisplayData.mouldBaseInfoData.unloadingnum}"></input>
						</td>
					</tr>
					<tr>
						<td>
							模具名称：
						</td>
						<td>
							<input type="text" id="name" name="name" class="short" value="${DisplayData.mouldBaseInfoData.name}"></input>
						</td>
						<td>
							材质：
						</td>
						<td>
							<input type="text" id="materialQuality" name="materialQuality" class="short" value="${DisplayData.mouldBaseInfoData.materialquality}"></input>
						</td>
						<td>
							尺寸：
						</td>
						<td>
							<input type="text" id="size" name="size" class="mini" value="${DisplayData.mouldBaseInfoData.size}"></input>
						</td>
						<td>
							重量：
						</td>
						<td colspan=2>
							<input type="text" id="weight" name="weight" class="mini" value="${DisplayData.mouldBaseInfoData.weight}"></input>
						</td>
					</tr>
					<tr>
						<td align=center>
							子编码
						</td>
						<td colspan=3>
							<table class="form">
								<tr>
									<td>
										<table width='350px'>
											<tr>
												<td class="td-center" width='150px'>子编码</td>
												<td class="td-center" width='150px'>子编码解释</td>
												<td class="td-center">
													<button type="button"  style = "height:20px;" 
													id="createSubid" class="DTTT_button" onClick="addSubCodeTr();">新建</button>
												</td>
											</tr>
										</table>
									</td>
								</tr>		
								<tr>
									<td>			
										<div class="" id="subidDiv" style="overflow: auto;height: 150px;">
											<table id="subidTab" class="dataTable">
												<tr style='display:none'>
													<td width='150px'></td>
													<td width='150px'></td>
													<td></td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
						<td colspan=3>	
							<table>
								<tr>
									<td>
										<div id="tabs" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true" style="margin:10px 0px 0px 15px;padding:0px;display:none;">
											<div id="tabs-1" title="图片" style="padding:5px;height:200px;">
												<jsp:include page="../../common/album/album.jsp"></jsp:include>
											</div>
										</div>
									</td>
								</tr>
							</table>						
						</td>
						<td colspan=3 align=center>
							<div id="rotateArea" style="display:none;margin:0px 0px 0px 50px">
								<div style="height:40px">
									<button type="button" id="delete" class="DTTT_button" onClick="doRotate(1);"
										style="height:25px;margin:-20px 30px 0px 0px;float:right;">&gt;&gt;</button>
									<button type="button" id="delete" class="DTTT_button" onClick="doRotate(0);"
										style="height:25px;margin:-20px 30px 0px 0px;float:left;">&lt;&lt;</button>
								</div>
								<div style="height:40px">
									<label 
										style="height:25px;margin:-20px 10px 0px 0px;float:right;">下一个模具</label>
									<label
										style="height:25px;margin:-20px 30px 0px -10px;float:left;">前一个模具</label>
								</div>
							</div>						
						</td>
					</tr>
				</table>			
				
				<div  style="height:20px"></div>
				<legend>模具工厂</legend>
			
				<div id="autoscroll">
					<table class="form" id="factoryTable" name="factoryTable">
						<tr>
							<td align="center" width='200px'>供应商编码</td>
							<td align="center" width='200px'>供应商名称</td>
							<td align="center" width='200px'>价格
								&nbsp;&nbsp;&nbsp;
								<button type="button"  style = "height:25px;" class="DTTT_button"
								 id="createShare" onClick="addFactoryTr();">新建</button>
							</td>
							<td></td>
						</tr>
					</table>
				</div>


			</form:form>
		</div>
</html>
