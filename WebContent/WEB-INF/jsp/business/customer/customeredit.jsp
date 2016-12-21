<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>客户基本数据</title>
<script type="text/javascript">

$(document).ready(function() {

	$('select').css('width','140px');
	
	validator = $("#customer").validate({
		rules: {
			customerId: {
				required: true,
				minlength: 5 ,
				maxlength: 20,
			},
			customerSimpleDes: {
				required: true,				
				maxlength: 20,
			},
			customerName: {
				required: true,								
				maxlength: 100,
			},
			paymentTerm: {
				required: true,					
				maxlength: 15,
			}
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
	
	$("#customer\\.country").change(function() {

		var parentId = $(this).val();
		//alert(parentId)
		var url = "${ctx}/business/customer?methodtype=optionChange&parentId="+parentId
											
		if (parentId != ""){ 
			$.ajax({
				type : "post",
				url : url,
				async : false,
				data : 'key=' + parentId,
				dataType : "json",
				success : function(data) {
	
					var subId = data["subId"];
					var customerId = parentId + subId;

					$('#customer\\.parentid').val(parentId);
					$('#customer\\.subid').val(subId);
					$('#customer\\.customerId').val(customerId);
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
	});	//国家选择

})

function doSave() {

	if (validator.form()) {					
		$('#customer').attr("action", "${ctx}/business/customer?methodtype=insert");
		$('#customer').submit();	
	}
}

function goBack() {

	var url = "${ctx}/business/customer";
	location.href = url;
}

</script>

</head>

<body>
<div id="container">
<div id="main">				
	<form:form modelAttribute="formModel" id="customer"  >
	
		<form:hidden path="customer.recordid" />
			
		<fieldset>		
			<legend>客户-综合信息</legend>
				
				<input type="hidden" id="keyBackup" name="keyBackup" value="${formModel.keyBackup}"/>
				<table class="form" style="height:120px">
					<tr>
						<td class="label" style="widht:100px">客户编号：</td>
						<td style="widht:120px">
							<form:input path="customer.customerid" class="short read-only"/>
							<form:hidden path="customer.parentid"/>
							<form:hidden path="customer.subid" />
						</td>
						<td class="label" style="widht:100px">客户简称：</td> 
						<td>
							<form:input path="customer.shortname" class="short"/>
						</td>
					
						<td class="label" style="widht:100px">客户名称：</td> 
						<td>
							<form:input path="customer.customername" class="middle"/>
						</td>
					</tr>
					<tr><td class="label" >国家：</td>
						<td>
							<form:select path="customer.country">
								<form:options items="${formModel.countryList}" itemValue="key"
									itemLabel="value" />
							</form:select></td>
						<td class="label" >
							计价货币：</td>
						<td> 
							<form:select path="customer.currency">
								<form:options items="${formModel.currencyList}" itemValue="key"
									itemLabel="value" />
							</form:select></td>
						<td class="label" >付款条件：</td>
						<td>&nbsp;出运后<form:input path="customer.paymentterm" class="small num" />天</td>						
					</tr>
					<tr>
						<td class="label" >出运条件：</td>
						<td> 
							<form:select path="customer.shippingcondition">
								<form:options items="${formModel.shippingConditionList}" itemValue="key"
									itemLabel="value" />
							</form:select></td>
						<td  class="label" >
							出运港：</td>
						<td width="100px">
							<form:select path="customer.shippiingport">
								<form:options items="${formModel.shippiingPortList}" itemValue="key"
									itemLabel="value" />
							</form:select></td>
						<td  class="label" >目的港： </td>
						<td>
							<form:select path="customer.destinationport">
								<form:options items="${formModel.destinationPortList}" itemValue="key"
									itemLabel="value" />
							</form:select></td>								
					</tr>
				</table>

		</fieldset>
		<div style="clear: both"></div>

		<fieldset class="action" style="text-align: right;">
			<button type="button" class="DTTT_button" onClick="doSave()">保存</button>
			<button type="button" class="DTTT_button" onClick="goBack()">返回</button>
		</fieldset>	

	</form:form>
</div>
</div>
</body>
</html>	
