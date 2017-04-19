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

function getSum(index) {

	var price = $("#price").html();
	var num = $("#num").val();

	if (price == '' || num == '') {
		$("#totalPriceView").html('');
		$('#totalPrice').val('');
	} else {
		$("#totalPriceView").html(parseFloat(price) * parseFloat(num));
		$('#totalPrice').val(parseFloat(price) * parseFloat(num));
	}
}

function initEvent(){
	
	validatorBaseInfo = $("#detailInfo").validate({
		rules: {
			mouldId: {
				required: true,
				maxlength: 20,
			},
			num: {
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
	autoComplete(0);
	
	$('#mouldIdView').focus();
})


function doSave() {
	if (validatorBaseInfo.form()) {
		$.ajax({
			async:false,
			type : "POST",
			url : "${ctx}/business/mouldcontract?methodtype=updateDetails",
			contentType : 'application/json',
			dataType : 'json',
			data : JSON.stringify($('#detailInfo').serializeArray()),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					parent.reloadMouldContractDetailList();
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

function autoComplete(index) { 
	
	$("#mouldIdView").autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "POST",
				url : "${ctx}/business/mouldcontract?methodtype=factorySearch",
				dataType : "json",
				data : {
					key : request.term,
					type : $('#type').val(),
					supplierId : $('#supplierId').val()
				},
				success : function(data) {
					response($.map(
						data.data,
						function(item) {
							//alert(item.viewList)
							return {
								label : item.viewList,
								value : item.mouldId,
								id : item.id,
								mouldFactoryId : item.mouldFactoryId,
								mouldId : item.mouldId,
								name : item.name,
								materialQuality : item.materialQuality,
								size : item.size,
								weight : item.weight,
								unloadingNum : item.unloadingNum,
								price : item.price
							}
						}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
				}
			});
		},

		select : function(event, ui) {
			$('#mouldId').val(ui.item.id);
			$("#mouldFactoryId").val(ui.item.mouldFactoryId);
			$("#name").html(ui.item.name);
			$("#id").val(ui.item.id);
			$("#mouldFactoryId").val(ui.item.mouldFactoryId);
			$("#size").html(ui.item.size);
			$("#weight").html(ui.item.weight);
			$("#materialQuality").html(ui.item.materialQuality);
			$("#unloadingNum").html(ui.item.unloadingNum);
			$("#price").html(ui.item.price);
			getSum(index);
		},
		
		change : function(event, ui) {
			if (ui.item == null) {
				$('#mouldId').val('');
				$("#mouldId").val('');
				$("#name").html('');
				$("#id").val('');
				$("#mouldFactoryId").val('');
				$("#size").html('');
				$("#weight").html('');
				$("#materialQuality").html('');
				$("#unloadingNum").html('');
				$("#price").html('');
				getSum(index);
			}
		},
		
		minLength : 0,
		autoFocus : false,
		width: 200,
		mustMatch:true,
		autoFill:true,
		selectFirst:true,
	});	
}
</script>
</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
			<form:form modelAttribute="dataModels" id="detailInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.mouldContractDetailData.id}"/>
				<input type=hidden id="mouldContractId" name="mouldContractId" value="${DisplayData.mouldContractBaseInfoData.id}"/>
				<input type=hidden id="type" name="type" value="${DisplayData.mouldContractBaseInfoData.type}"/>
				<input type=hidden id="supplierId" name="supplierId" value="${DisplayData.mouldContractBaseInfoData.supplierid}"/>
				<input type=hidden id="mouldId" name="mouldId" value="${DisplayData.mouldContractDetailData.mouldid}"/>
				<input type=hidden id="mouldFactoryId" name="mouldFactoryId" value="${DisplayData.mouldContractDetailData.mouldfactoryid}"/>
				<input type=hidden id="totalPrice" name="totalPrice" value="${DisplayData.mouldContractDetailData.totalprice}"/>
				
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
				
				<legend>模具详情；</legend>
				<div style="height:10px"></div>
				<table class="form" width="1100px" cellspacing="0">
					<thead>
						<tr>
							<th style="width: 80px;" class="dt-middle">编号</th>
							<th style="width: 80px;" class="dt-middle">名称</th>
							<th style="width: 80px;" class="dt-middle">尺寸</th>
							<th style="width: 80px;" class="dt-middle">重量</th>
							<th style="width: 80px;" class="dt-middle">材质</th>
							<th style="width: 80px;" class="dt-middle">出模数</th>
							<th style="width: 80px;" class="dt-middle">单价</th>
							<th style="width: 80px;" class="dt-middle">数量</th>
							<th style="width: 80px;" class="dt-middle">总价</th>
						</tr>
					</thead>
					<tr>
						<td><input type="text"   name="mouldIdView" id="mouldIdView" style="width:80px;" value='${DisplayData.mouldBaseInfoData.mouldid}'/></td>
						<td align="center"><label   name="name" id="name"  style="width:80px;" class="readonly" readonly="readonly">${DisplayData.mouldBaseInfoData.name}</label></td>
						<td align="center"><label   name="size"   id="size"   style="width:80px;" class="short readonly" readonly="readonly" />${DisplayData.mouldBaseInfoData.size}</label></td>
						<td align="center"><label   name="weight"   id="weight"  style="width:80px;"  class="short readonly" readonly="readonly"/>${DisplayData.mouldBaseInfoData.weight}</label></td>
						<td align="center"><label   name="materialQuality"   id="materialQuality"  style="width:80px;" class="short readonly" readonly="readonly"/>${DisplayData.mouldBaseInfoData.materialquality}</label></td>
						<td align="center"><label   name="unloadingNum"   id="unloadingNum"  style="width:80px;"  class="short readonly" readonly="readonly"/>${DisplayData.mouldBaseInfoData.unloadingnum}</label></td>
						<td align="center"><label   name="price"   id="price"  style="width:80px;"  class="short readonly" readonly="readonly"/>${DisplayData.mouldFactoryData.price}</label></td>
						<td><input type="text"   name="num"   id="num"  style="width:80px;"  class="short readonly" onInput="getSum(0);" value='${DisplayData.mouldContractDetailData.number}'/></td>
						<td align="center"><label   name="totalPriceView"   id="totalPriceView"   style="width:80px;margin:0px 0px 0px 10px" class="short" readonly="readonly">${DisplayData.mouldContractDetailData.totalprice}</label></td>
					</tr>
				</table>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:10px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:10px 5px 0px 0px;float:right;" >保存</button>
				
			</form:form>
		</div>
</html>
