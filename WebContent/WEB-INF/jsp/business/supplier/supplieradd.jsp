<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>供应商基本数据</title>
<script type="text/javascript">
var validator;


$(document).ready(function() {

	//initEvent();
	
	$("#province").change(function() {

		var val = $(this).val();

		$.ajax({
			type : "post",
			url : "${ctx}/business/supplier?methodtype=optionChange&province="+val,
			async : false,
			data : 'key=' + val,
			dataType : "json",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				var jsonObj = data;
				
				$("#city").val("");
				$("#city").html("");	
				$("#county").val("");
				$("#county").html("");	
				
				for (var i = 0; i < jsonObj.length; i++) {
					$("#city").append(
						"<option value="+jsonObj[i].key+">"
						+ jsonObj[i].value
						+ "</option>");
				};

			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {;
				$("#city").val("");
				$("#city").html("");
				$("#county").val("");
				$("#county").html("");
			}
		});
	});
		
	$("#city").change(function() {

		var val = $(this).val();
		
											
		if (val != "0"){ //
			$.ajax({
				type : "post",
				url : "${ctx}/business/supplier?methodtype=optionChange2&province="+val,
				async : false,
				data : 'key=' + val,
				dataType : "json",
				success : function(data) {
					$("#county").val("");	
					$("#county").html("");	
					var jsonObj = data;
					
					for (var i = 0; i < jsonObj.length; i++) {
						$("#county").append(
							"<option value="+jsonObj[i].key+">"
									+ jsonObj[i].value
									+ "</option>");
					};

				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					
					$("#county").html("");
				}
			});
		}else{
			//关联项目清空
		}
	});	
	
	$("#county").change(function() {

		var county = $(this).val();
		var province = $("#province").val();
		var city = $("#city").val();
		var parentId = city+county;
		//alert(parentId)
		var url = "${ctx}/business/supplier?methodtype=optionChange3&parentId="+parentId
											
		if (parentId != ""){ //
			$.ajax({
				type : "post",
				url : url,
				async : false,
				data : 'key=' + county,
				dataType : "json",
				success : function(data) {
					
					

					var subId = data["subId"];
					var supplierId = parentId + subId;

					$('#supplier\\.parentid').val(parentId);
					$('#supplier\\.subid').val(subId);
					$('#supplier\\.supplierid').val(supplierId);
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					
					//alert("supplierId2222:"+textStatus);
				}
			});
		}else{
			//关联项目清空
		}
	});	//市县选择
	
	validator = $("#supplierBasicInfo").validate({
		rules: {
			supplierId: {
				required: true,
				minlength: 5 ,
				maxlength: 20,
			},
			shortName: {
				maxlength: 10,
			},
			supplierName: {
				maxlength: 50,
			},
			categoryId: {
				maxlength: 12,
			},
			categoryDes: {
				maxlength: 50,
			},
			paymentTerm: {
				maxlength: 5,
			},
			address: {
				maxlength: 50,
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

	//$("#province").val("${formModel.province}");
	//$("#city").val("${formModel.city}");
	//$("#county").val("${formModel.county}");
})

function doSave() {

	if (validator.form()) {
					
		$('#supplierBasicInfo').attr("action", "${ctx}/business/supplier?methodtype=insert");
		$('#supplierBasicInfo').submit();
		
	}
}

function doDelete() {

	var url = "${ctx}/business/supplier";
	location.href = url;
}

function clearSupplierBasicInfo() {
	$('#supplierId').val('');
	$('#shortName').val('');
	$('#supplierName').val('');
	$('#categoryId').val('');
	$('#categoryDes').val('');
	$('#paymentTerm').val('');
	$('#address').val('');
	$('#country').val('');
	$('#province').val('');
	$('#city').val('');
	$("#province").find("option").remove();
	$("#city").find("option").remove();
}

</script>

</head>

<body>
<div id="container">
	<form:form modelAttribute="formModel" id="supplierBasicInfo">		
		
		<form:hidden path="supplier.recordid" />
		
	<fieldset>		
		<legend>供应商-综合信息</legend>
			
		<table class="form" style="height:150px">
			<tr>
				<td class="label" width="100px">供应商编码：</td>
				<td width="150px">
					<form:input path="supplier.supplierid" class="read-only" />
					<form:hidden path="supplier.parentid" />
					<form:hidden path="supplier.subid" /></td>
					
				<td class="label" width="100px">简称：</td> 
				<td width="100px">
					<form:input path="supplier.shortname" class="short" /></td>

				<td class="label" width="100px">名称：</td> 
				<td>
					<form:input path="supplier.suppliername" class="middle" /></td>
			</tr>
		<c:if test="${empty formModel.supplier.recordid}" >
			<tr>
				<td class="label">省份：</td>
				<td width="150px">
					<form:select path="province" style="width:100px">
						<form:options items="${formModel.countryList}" itemValue="key"
							itemLabel="value" />
					</form:select></td>
				<td class="label">地级市：</td>
				<td width="150px"> 
					<form:select path="city" style="width:100px">
						<form:options items="${formModel.provinceList}" itemValue="key"
							itemLabel="value" />
					</form:select></td>
				<td class="label" width="60px">	市县：</td>
				<td width="150px"> 
					<form:select path="county" style="width:100px">
						<form:options items="${formModel.cityList}" itemValue="key"
							itemLabel="value" />
					</form:select></td>
		</tr>
			</c:if>
			<tr>	
				<td class="label" width="100px">二级编码：</td> 
				<td>
					<form:input path="supplier.categoryid" class="mini" /></td>
				<td class="label" width="100px">编码解释：</td> 
				<td>
					<form:input path="supplier.categorydes" class="middle" /></td>

				<td class="label" width="100px">付款条件：</td>
				<td>&nbsp;入库后
					<form:input path="supplier.paymentterm" class="mini num" />天</td>
			</tr>
			<tr>
				<td class="label">详细地址： </td>
				<td colspan=5>
					<form:input path="supplier.address" class="long" />
				</td>
			</tr>
		</table>

	</fieldset>	
		
		<fieldset class="action" >
		<button type="button" id="return" class="DTTT_button" onclick="doDelete();">返回</button>
		<button type="submit" id="submit" class="DTTT_button" onclick="doSave();">保存</button>
		</fieldset>
		
	</form:form>
	</div>
</html>

