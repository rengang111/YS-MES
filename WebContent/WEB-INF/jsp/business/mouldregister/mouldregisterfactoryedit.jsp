<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>

<head>
<title>模具管理--新增模具单价(供应商)</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>

<script type="text/javascript">
	var validator;
	var datas;
	
	$(document).ready(function() {
		
		validator = $("#mould").validate({
			rules: {
				attribute1: {
					required: true,
				},
				price: {
					required: true,
					minlength: 1,
					maxlength: 10,
				},
				currency: {
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
		
		if ("${DisplayData.subCode}" != "") {
			$('#mouldId').html("${DisplayData.mouldBaseInfoData.mouldid}.${DisplayData.subCode}");
		} else {
			$('#mouldId').html("${DisplayData.mouldBaseInfoData.mouldid}");
		}
		
		//设置光标项目
		$("#attribute1").focus();
		
		//设置选择项目的选中项	
		$("#priceTime").val(shortToday());
		$("#priceTime").attr('readonly', "true");

		$("#attribute3").attr('readonly', "true");
		$("#attribute2").attr('readonly', "true");

		$('#currency').val("${DisplayData.mouldFactoryData.currency}");
	
		if ($('#currency').val() == null) {
		    $("#currency option").each(function(){
		        if($(this).text() == "人民币"){  
		            $(this).attr("selected","selected");  
		        }  
		    });
		}
		
		$("#price").blur(function(){
			
			//$(this).val(float4ToCurrency($(this).val()));
		});
		
		$("#return").click(function() {

			var index = parent.layer
					.getFrameIndex(window.name); //获取当前窗体索引

			parent.layer.close(index); //执行关闭

		});
		
		
		$("#save").click(function() {

			if (validator.form()) {
				$.ajax({
					async:false,
					type : "POST",
					url : "${ctx}/business/mouldregister?methodtype=updateFactory",
					contentType : 'application/json',
					dataType : 'json',
					data : JSON.stringify($('#mould').serializeArray()),// 要提交的表单
					success : function(d) {
						if (d.rtnCd != "000") {
							alert(d.message);	
						} else {
							parent.parent.reload();
							//不管成功还是失败都刷新父窗口，关闭子窗口
							var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引	
							parent.reloadFatory();//刷新供应商单价信息
							parent.layer.close(index); //执行关闭	
						}
					},
					error : function(
							XMLHttpRequest,
							textStatus,
							errorThrown) {
	
					}
				});
			}			
		});

	});
	
</script>

</head>
<body class="noscroll">

<div id="layer_main">

	<form:form modelAttribute="dataModels" method="POST" 
	 id="mould" name="mould"  autocomplete="off">

		<input type=hidden name="keyBackup" id="keyBackup" value="${DisplayData.keyBackup}" />
		<input type=hidden name="mouldId" id="activeSubCode" value="${DisplayData.mouldId}" />
		<input type=hidden name="unit" id="unit" value="${DisplayData.mouldBaseInfoData.unit}" />
		<input type=hidden name="supplierid" id="supplierid" value="${DisplayData.mouldFactoryData.mouldfactoryid}" />
		

		<fieldset>
			<legend> 模具供应商报价-新建</legend>

			<table class="form" width="600px" style="table-layout:fixed;">

				<tr>
					<td width="100px" class="label"><label>模具单元编号：</label></td>
					<td width="150px">
						<label id="mouldId"></label>
					</td>
					
					<td width="100px" class="label"><label>模具单元名称：</label></td>
					<td width="200px">
						${DisplayData.mouldBaseInfoData.name}
					</td>
				</tr>

				<tr>
					<td class="label"><label>模具类型：</label></td>
					<td>${DisplayData.mouldType}&nbsp;${DisplayData.typeDesc}</td>
					<td class="label" width="80px"><label>报价单位：</label></td>
					<td colspan=3>${DisplayData.unit}</td>
				</tr>

			</table>
		</fieldset>
		<fieldset>
			<legend Style="margin: 10px 0px 0px 0px"> 供应商信息</legend>
			<table class="form">
				<tr>
					<td class="label" width="100px" ><label>选择供应商：</label></td>
					<td colspan="3">
						<div class="ui-widget">
							<input type="text" id="attribute1" class="required middle" value="${DisplayData.supplierData.supplierid}"/>
							&nbsp;<span style="color: blue">
								&nbsp;（查询范围：供应商编号、供应商简称、供应商全称）</span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label" width="100px" ><label>供应商简称：</label></td>

					<td width="150px"><input type="text" id="attribute2"
							class="read-only" value="${DisplayData.supplierData.shortname}"/></td>
					<td class="label" width="100px" >供应商全称：</td>

					<td><input type="text" id="attribute3" value="${DisplayData.supplierData.suppliername}" class="long read-only" />
					</td>
				</tr>
			</table>	
		</fieldset>
		<fieldset>
			<legend Style="margin: 10px 0px 0px 0px"> 报价信息</legend>
			<table class="form">
				<tr>
					<td class="label" width="100px" >供应商单价：</td>

					<td width="150px" >
						<input type="text" id="price" name="price" class="short required cash" value="${DisplayData.mouldFactoryData.price}"/>
					</td>
					
					<td class="label" width="100px" ><label>币种：</label></td>
					<td><form:select path="currency">
							<form:options items="${DisplayData.currencyList}" 
								itemValue="key" itemLabel="value"/></form:select></td>
		
					<td class="label" width="100px" ><label>报价日：</label></td>

					<td><form:input path="priceTime" class="read-only required " value="${DisplayData.priceTime}"/></td>

				</tr>
			</table>
		</fieldset>

		<fieldset class="action">
			<button type="button" id="return" class="DTTT_button" 
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >关闭</button>
			<button type="button" id="save" class="DTTT_button" 
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
		</fieldset>

	</form:form>

</div>
</body>
<script type="text/javascript">

	$("#attribute1").autocomplete({
		
		source : function(request, response) {
			//alert(888);
			$.ajax({
				type : "POST",
				url : "${ctx}/business/mouldregister?methodtype=supplierSearch",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(777);
					response($.map(
						data.data,
						function(item) {
							//alert(item.viewList)
							return {
								label : item.viewList,
								value : item.supplierId,
								id : item.supplierId,
								shortName : item.shortName,
								fullName : item.fullName,
								recordId: item.recordId,
							}
						}));
					datas = data.data;
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},

		select : function(event, ui) {
			$("#supplierid").val(ui.item.recordId);
			$("#attribute1").val(ui.item.id);
			$("#attribute2").val(ui.item.shortName);
			$("#attribute3").val(ui.item.fullName);
			

		},
        change: function(event, ui) {
            // provide must match checking if what is in the input
            // is in the list of results. HACK!
            var inputSource = $(this).val();
            var found = $('.ui-autocomplete li').text().search(inputSource);
            if(found < 0) {
                $(this).val('');
            } else {
            	var matcher = new RegExp("^" + $(this).val());
            	for(var i = 0; i < datas.length; i++){//用javascript的for/in循环遍历对象的属性
            		if (matcher.test(datas[i].name)) {
            			$("#attribute1").val(datas[i].id);
        				$("#supplierid").val(datas[i].recordId);
        				$("#attribute2").val(datas[i].shortName);
        				$("#attribute3").val(datas[i].fullName);                			
        				break;
            		}
            	}
            }
        },

		minLength : 2,
		autoFocus : false,
		width: 500,
	});
</script>
</html>
