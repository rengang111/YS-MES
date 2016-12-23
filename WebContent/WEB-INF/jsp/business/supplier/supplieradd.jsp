<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>

<title>供应商基本数据</title>
<script type="text/javascript">
var validator;


$(document).ready(function() {

	$("#supplier\\.shortname").attr('readonly', "true");
	$("#supplier\\.shortname").addClass('read-only');
	
	$("#province").change(function() {
		
		$('#supplier\\.supplierid').val('');
		
		var val =encodeURI( $(this).val());//中文两次转码

		$.ajax({
			type : "post",
			url : "${ctx}/business/supplier?methodtype=optionChange&province="+encodeURI(val),
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

		var cityCode = $(this).val();
		var subId = $('#supplier\\.subid').val();
		var shortName = $('#supplier\\.shortname').val().toUpperCase();
		var supplierId = $('#supplier\\.supplierid').val();
		var parentId;		
		
		if(shortName != ''){
			parentId = cityCode + shortName;
			supplierId = parentId + subId;
		}
		$('#supplier\\.parentid').val(parentId);
		$('#supplier\\.subid').val(subId);
		$('#supplier\\.supplierid').val(supplierId);
		
		$("#supplier\\.shortname").removeAttr('readonly');
		$("#supplier\\.shortname").removeClass('read-only');
													
		
	});	
	
	$("#supplier\\.shortname").change(function() {

		var city =  $("#city").val();
		var shortName = $(this).val().toUpperCase();
		var parentId = city+shortName;
		//alert(parentId)
		var url = "${ctx}/business/supplier?methodtype=setSupplierId&parentId="+parentId
											
		if (parentId != ""){ //
			$.ajax({
				type : "post",
				url : url,
				async : false,
				data : 'key=' + parentId,
				dataType : "json",
				success : function(data) {

					var subId = data["subId"];
					var supplierId = parentId + subId;

					$('#supplier\\.parentid').val(parentId);
					$('#supplier\\.subid').val(subId);
					$('#supplier\\.supplierid').val(supplierId);
					$('#supplier\\.shortname').val(shortName);
					
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
				maxlength: 10,
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

				<td class="label" width="100px">供应商名称：</td> 
				<td colspan="3">
					<form:input path="supplier.suppliername" class="middle" /></td>
			</tr>
		
			<tr>
				<td class="label">省份：</td>
				<td width="150px">
					<form:select path="province" style="width:100px">
						<form:options items="${formModel.countryList}" itemValue="key"
							itemLabel="value" />
					</form:select></td>
				<td class="label">城市：</td>
				<td width="150px"> 
					<form:select path="city" style="width:100px">
						<form:options items="${formModel.provinceList}" itemValue="key"
							itemLabel="value" />
					</form:select></td>
				<td class="label" width="100px">供应商简称：</td> 
				<td width="150px">
					<form:input path="supplier.shortname" class="short" style="text-transform:uppercase;"/></td>
			</tr>
			
			<tr>	
				<td class="label" width="100px">物料分类：</td> 
				<td>
					<form:input path="supplier.categoryid"  style="text-transform:uppercase;" /></td>
				<td class="label" width="100px">分类解释：</td> 
				<td>
					<form:input path="supplier.categorydes" class="long read-only" /></td>

				<td class="label" width="100px">付款条件：</td>
				<td>&nbsp;入库后
					<form:input path="supplier.paymentterm" class="small num" />天</td>
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
	
<script type="text/javascript">

	$("#supplier\\.categoryid").autocomplete({
		
		search: function( event, ui ) {
			
			var categoryid = $(this).val();
			if(categoryid.indexOf(",") > 0 ){

				$.ajax({
					type : "POST",
					url : "${ctx}/business/material?methodtype=categorySearchMul",
					dataType : "json",
					data : {
						key : categoryid
					},
					success : function(data) {						
						var arrayObj = new Array(); //创建一个数组.toUpperCase()
						arrayObj = data;
						var name = '';
						var firstFlg = true;						
						$.each(arrayObj, function(i, n){
							
							if(firstFlg){
								name = n["categoryname"];
								firstFlg = false;
							}else{
								name = name + ', ' + n["categoryname"];
							}									                   
			            })
						$('#supplier\\.categorydes').val(name);
						$("#supplier\\.categoryid").val(categoryid.toUpperCase());						
					},
					error : function(XMLHttpRequest,textStatus, errorThrown) {
						alert(textStatus);
						alert("系统异常，请再试或和系统管理员联系22。");
					}
				});//多项查询
				
				return false;
				
			}else{
				//继续单项查询
			}		
			
		},
		source : function(request, response) {
			//alert(888);
			$.ajax({
				type : "POST",
				url : "${ctx}/business/material?methodtype=categorySearch",
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
								value : item.categoryId,
								id : item.categoryId,
								categoryName : item.categoryViewName,
								
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
			$("#supplier\\.categorydes").val(ui.item.categoryName);
			$("#supplier\\.categoryid").val(ui.item.id.toUpperCase());

		},

		minLength : 0,
		autoFocus : false,
	});
</script>
	</body>
</html>

