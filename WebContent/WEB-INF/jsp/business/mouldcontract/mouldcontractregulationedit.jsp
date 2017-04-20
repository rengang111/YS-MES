<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具合同</title>
<script type="text/javascript">

var validatorBaseInfo;
var layerHeight = "250";

function initEvent(){
	
	validatorBaseInfo = $("#regulationInfo").validate({
		rules: {
			name: {
				required: true,
				maxlength: 20,
			},
			money: {
				digits: true,
				maxlength: 20,
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
	
	
}

$(window).load(function(){
	
});

$(document).ready(function() {

	initEvent();
	$('#name').focus();
})


function doSave() {
	if (validatorBaseInfo.form()) {
		$.ajax({
			async:false,
			type : "POST",
			url : "${ctx}/business/mouldcontract?methodtype=updateRegulation",
			contentType : 'application/json',
			dataType : 'json',
			data : JSON.stringify($('#regulationInfo').serializeArray()),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					parent.reloadMouldContractRegulationList();
					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引	
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

}

function doReturn() {

	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	parent.layer.close(index); //执行关闭
	
}
</script>
</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
			<form:form modelAttribute="dataModels" id="regulationInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.mouldContractRegulationData.id}"/>
				<input type=hidden id="mouldId" name="mouldId" value="${DisplayData.mouldContractBaseInfoData.id}"/>
				
				<legend>模具合同基本信息</legend>
				<div style="height:10px"></div>
				<table class="form" width="1100px" cellspacing="0">
					<tr>
						<td class="label" width="60px">编号：</td>
						<td width="130px">
							<label id="contractId" name="contractId" style="margin:0px 10px">${DisplayData.mouldContractBaseInfoData.contractid}</label>
						</td>
						<td class="label" width="80px">分类编码：	</td>
						<td width="200px">
							<label name="typeView" id="typeView">${DisplayData.mouldContractBaseInfoData.type}</label>
						</td>
						<td class="label" width="60px">模具类型：</td>
						<td width="150px">
							${DisplayData.mouldType}&nbsp;${DisplayData.typeDesc}
						</td>
						<td class="label" width="60px">年份：</td>
						<td width="130px">
							<label name="contractYear" id="contractYear">${DisplayData.mouldContractBaseInfoData.contractyear}</label>
						</td>

					</tr>
					<tr>
						<td class="label" width="50px">
							供应商ID：
						</td>
						<td width="100px">
							<label type="text" id="supplierIdView" name="supplierIdView">${DisplayData.supplierIdView}</label>
						</td>
						<td class="label" width="50px">
							名称：
						</td>
						<td width="150px"3>
							<label id="supplierName" name="supplierName" >${DisplayData.supplierName}</label>
						</td>
						<td class="label" >
							合同日期：
						</td>
						<td>
							<label id="contractDate" name="contractDate" >${DisplayData.contractDate}</label>
						</td>
						<td class="label" >
							合同交期：
						</td>
						<td>
							<label id="deliverDate" name="deliverDate" >${DisplayData.deliverDate}</label>
						</td>
					</tr>
				</table>
				
				<legend>增减项</legend>
				<div style="height:10px"></div>
				<table class="form" width="1100px" cellspacing="0">
					<tr>
						<td class="label" width="60px">名称：</td>
						<td width="130px">
							<input type=text id="name" name="name" value='${DisplayData.mouldContractRegulationData.name}'/>
						</td>
						<td class="label" width="80px">金额：	</td>
						<td width="200px">
							<input type=text id="money" name="money" value='${DisplayData.mouldContractRegulationData.money}'/>
						</td>
					</tr>
				</table>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:10px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:10px 5px 0px 0px;float:right;" >保存</button>
				
			</form:form>
		</div>
</html>
