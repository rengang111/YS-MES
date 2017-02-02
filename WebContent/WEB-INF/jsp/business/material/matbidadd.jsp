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
<title>物料管理--新增物料单价(供应商)</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
  var ctx = '${ctx}'; 
</script>
<script type="text/javascript" src="${ctx}/js/jquery-2.1.3.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/dataTables.tableTools.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/all.css" />

<script type="text/javascript">

	$(document).ready(function() {
		
		//设置光标项目
		$("#attribute1").focus();
		
		//设置选择项目的选中项	
		$("#price\\.pricedate").val(shortToday());
		$("#price\\.pricedate").attr('readonly', "true");

		$("#attribute3").attr('readonly', "true");
		$("#attribute2").attr('readonly', "true");

		
		$("#price\\.price").blur(function(){
			
			$(this).val(float4ToCurrency($(this).val()));
		});
		
		$("#return").click(function() {

					//alert(999);

					var index = parent.layer
							.getFrameIndex(window.name); //获取当前窗体索引

					parent.layer.close(index); //执行关闭

		});
		
		
		$("#submit").click(function() {

			if ($("#price\\.supplierid").val() == "0") {

				alert("请填入正确的供应商。");

				$("#attribute1").focus();

				return;

			}

			$("#submit").attr("disabled", true);

			$.ajax({
				async:false,
				type : "POST",
				url : "${ctx}/business/material?methodtype=insertPrice",
				data : $('#material').serialize(),// 要提交的表单
				success : function(d) {

					var retValue = d['message'];
					if (retValue != "failure") {
						//alert(retValue);
						parent.$('#handle_status').val('1');
					} else {
						// 从体验度来说，不要成功提示似乎操作更流畅。
						 parent.$('#handle_status').val('2');
					}
					

					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引	
					parent.supplierPriceView();//刷新供应商单价信息
					parent.layer.close(index); //执行关闭	
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					alert(XMLHttpRequest.status);							
					//alert(XMLHttpRequest.readyState);							
					//alert(textStatus);							
					//alert(errorThrown);							

					if (XMLHttpRequest.status == "800") {
						alert("800"); //请不要重复提交！
					} else {
						alert("发生系统异常，请再试或者联系系统管理员。"); 
					}
					//关闭窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引						
					//parent.reloadSupplier();
					parent.layer.close(index); //执行关闭					

				}
			});
			
		});

	});
	
</script>

</head>
<body class="noscroll">

<div id="layer_main">

	<form:form modelAttribute="material" method="POST" 
	 id="material" name="material"  autocomplete="off">

		<form:hidden path="material.recordid" />
		<form:hidden path="material.parentid" />
		<form:hidden path="material.serialnumber" />
		<form:hidden path="material.categoryid" />
		<form:hidden path="categoryname" />
		<form:hidden path="price.priceunit"  value="${product.unit}"  />
		<form:hidden path="price.materialid" value="${product.materialId}" />
		<form:hidden path="price.supplierid" />

		<fieldset>
			<legend> 物料供应商报价-新建</legend>

			<table class="form">

				<tr>
					<td width="100px" class="label"><label>产品编号：</label></td>
					<td width="250px">
							${product.materialId}
						</td>

					<td width="100px" class="label"><label>中文品名：</label></td>
					<td>${product.materialName}</td>
				</tr>

				<tr>
					<td class="label"><label>分类编码：</label></td>
					<td>${product.categoryIdAndName}</td>

					<td class="label"><label>报价单位：</label></td>
					<td>${product.dicName}</td>
				</tr>

			</table>

			<legend Style="margin: 10px 0px 0px 0px"> 供应商信息</legend>
			<table class="form">
				<tr>
					<td class="label" width="100px" ><label>选择供应商：</label></td>
					<td colspan="3">
						<div class="ui-widget">
							<form:input path="attribute1" class="required middle" />
							&nbsp;<span style="color: blue">
								&nbsp;（查询范围：供应商编号、供应商简称、供应商全称）</span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label" width="100px" ><label>供应商简称：</label></td>

					<td width="150px"><form:input path="attribute2"
							class="read-only" /></td>
					<td class="label" width="100px" >供应商全称：</td>

					<td><form:input path="attribute3" class="long read-only" />
					</td>
				</tr>
			</table>	

			<legend Style="margin: 10px 0px 0px 0px"> 报价信息</legend>
			<table class="form">
				<tr>
					<td class="label" width="100px" >供应商单价：</td>

					<td width="150px" ><form:input path="price.price" class="short required cash" /></td>
					
					<td class="label" width="100px" ><label>币种：</label></td>
					<td><form:select path="price.currency">
							<form:options items="${material.currencyList}" 
								itemValue="key" itemLabel="value"/></form:select></td>
		
					<td class="label" width="100px" ><label>报价日：</label></td>

					<td><form:input path="price.pricedate" class="read-only required " /></td>

				</tr>
			</table>
		</fieldset>

		<fieldset class="action">
			<button type="button" id="return" class="DTTT_button">关闭</button>
			<button type="submit" id="submit" class="DTTT_button">保存</button>
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
				url : "${ctx}/business/material?methodtype=supplierSearch",
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
								
							}
						}));
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
			$("#price\\.supplierid ").val(ui.item.id);
			$("#attribute2").val(ui.item.shortName);
			$("#attribute3").val(ui.item.fullName);

		},

		minLength : 2,
		autoFocus : false,
		width: 500,
	});
</script>
</html>
