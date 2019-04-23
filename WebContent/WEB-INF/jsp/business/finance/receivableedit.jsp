<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应收款-收款编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	

function historyAjax() {
	
	var YSId = '${order.YSId }';

	var t = $('#history').DataTable({			
		"paging": false,
		"lengthChange":false,
		"lengthMenu":[50,100,200],//设置一页展示20条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"ordering "	:true,
		"searching" : false,
		"retrieve" : true,
		//"scrollY":scrollHeight,
		//"scrollCollapse":true,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/receivable?methodtype=getReceivableDetail&YSId="+YSId,
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var param = {};
			var formData = $("#condition").serializeArray();
			formData.forEach(function(e) {
				aoData.push({"name":e.name, "value":e.value});
			});

			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : JSON.stringify(aoData),
				success: function(data){							
					fnCallback(data);
					
					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
		
		"columns" : [
		           {"data": "receivableSerialId","className":"td-center"
				}, {"data": "collectionDate","className":"td-center"
				}, {"data": "LoginName","className":"td-center"
				}, {"data": "bankDeduction","className":"td-right"
				}, {"data": "actualAmount","className":"td-right"
				}, {"data": "remarks","className":""
				}
			],
			"columnDefs":[
	    		
	    		{"targets":3,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}}
	    	] 
		
	}).draw();
					
	t.on('click', 'tr', function() {
		
		var rowIndex = $(this).context._DT_RowIndex; //行号			
		//alert(rowIndex);

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
		
	});
	

};
	
	$(document).ready(function() {
		
		//日期
		/*
		$("#receivableDetail\\.collectiondate").val(shortToday());
		$("#receivableDetail\\.collectiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
		}); 
		*/
	
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/receivable";						
					location.href = url;		
		});

		
		$("#insert").click(
				function() {
 			var YSId = '${order.YSId}';
			var bankdeduction =	$('#receivableDetail\\.bankdeduction').val();
			var actualamount  =	$('#receivableDetail\\.actualamount').val();
			
			bankdeduction = currencyToFloat(bankdeduction);
			actualamount = currencyToFloat(actualamount);
			if(actualamount <= 0){

				$().toastmessage('showWarningToast', "收款金额不能小于零。");	
				return;
			}
			$('#receivableDetail\\.bankdeduction').val(bankdeduction);
			$('#receivableDetail\\.actualamount').val(actualamount);

			$('#insert').attr("disabled","true").removeClass("DTTT_button");
			$('#formModel').attr("action", "${ctx}/business/receivable?methodtype=receivableUpdate"+"&YSId="+YSId);
			$('#formModel').submit();
		});
		

		historyAjax();//历史收款单
		productPhotoView();//收款单
		//产品图片添加位置,                                                                                                                                                                                        
		var productIndex = 1;
		$("#addProductPhoto").click(function() {
			
			var path='${ctx}';
			var cols = $("#productPhoto tbody td.photo").length - 1;
			//从 1 开始
			var trHtml = addPhotoRow('productPhoto','uploadProductPhoto',productIndex,path);		

			$('#productPhoto td.photo:eq('+0+')').after(trHtml);	
			productIndex++;		
			//alert("row:"+row+"-----"+"::productIndex:"+productIndex)
		});
		
		//银行扣款
		$("#receivableDetail\\.bankdeduction") .blur(function(){
			//计算本次最大收款金额
			var actualCnt =  currencyToFloat('${order.actualCnt }');
			var orderPrice = currencyToFloat('${order.orderPrice }');
			var currency = '${order.currency}';//币种
			var bank  = currencyToFloat($('#receivableDetail\\.bankdeduction').val());
			var surplus = currencyToFloat($('#dangqianMax').val()) ;//当前可以收款的最大数;

			if(bank >= surplus){
				$().toastmessage('showWarningToast', "扣款金额不能超过应收款。");	
				$('#receivableDetail\\.bankdeduction').val('0');
				return;
			}
			
			if(surplus <= 0){

				var shiji = '0';
				var lastSurplus = '0';
				$('#surplus').text('0');
			}else{

				var shiji = surplus - bank;
				var lastSurplus = surplus - bank - shiji;
				$('#surplus').text(floatToSymbol(surplus,currency));
			}
			

			$('#receivableDetail\\.actualamount').val(floatToSymbol(shiji,currency));
			$('#receivableDetail\\.bankdeduction').val(floatToSymbol(bank,currency));
			$('#lastSurplus').text(floatToSymbol(lastSurplus,currency));
		});
		
		//收款
		$("#receivableDetail\\.actualamount") .blur(function(){
			//计算本次最大收款金额
			var actualCnt =  currencyToFloat('${order.actualCnt }');
			var orderPrice = currencyToFloat('${order.orderPrice }');
			var currency = '${order.currency}';//币种
			var bank  = currencyToFloat($('#receivableDetail\\.bankdeduction').val());
			var shiji = currencyToFloat($('#receivableDetail\\.actualamount').val());
			var surplus = currencyToFloat($('#dangqianMax').val()) ;//当前可以收款的最大数;
			
			if(shiji > surplus - bank){
				$().toastmessage('showWarningToast', "收款金额不能超过应收款。");				
				shiji = surplus - bank;
			}
			
			var lastSurplus = surplus - bank - shiji
			
			$('#surplus').text(floatToSymbol(surplus,currency));

			$('#receivableDetail\\.actualamount').val(floatToSymbol(shiji,currency));
			$('#receivableDetail\\.bankdeduction').val(floatToSymbol(bank,currency));
			$('#lastSurplus').text(floatToSymbol(lastSurplus,currency));
		});
	

		
		var orderPrice ='${order.orderPrice}';
		var bankDeduction =  currencyToFloat('${detail.bankDeduction }');
		var actualAmount  =  currencyToFloat('${detail.actualAmount }');
		var actualCnt  =  currencyToFloat('${order.actualCnt }');
		var currency = '${order.currency}';//币种
		var dangqian = orderPrice - actualCnt + bankDeduction + actualAmount ;//当前可以收款的最大数
		
		$('#dangqianMax').val(dangqian);
		$('#actualCnt').text(floatToSymbol(actualCnt,currency));
		$('#receivableDetail\\.bankdeduction').val(floatToSymbol(bankDeduction,currency));
		$('#receivableDetail\\.actualamount').val(floatToSymbol(actualAmount,currency));
		$('#orderPrice').text(floatToSymbol(orderPrice,currency))
		$('#receivable\\.amountreceivable').val(floatToCurrency(orderPrice));
		
		
	});
	

	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/requisition?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}

		
	function doShowContract(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("采购合同",url);
	}

	function doShowSupplier(supplierId) {

		var url = '${ctx}/business/supplier?methodtype=showById&openFlag=newWindow&key=' + supplierId;
		
		callProductDesignView("供应商",url);
	}
	
	//批量打印入库单
	function doPrintReceiptList() {
		var contractId = "";
		$(".contractid").each(function(){			
			contractId += $(this).val() + ",";			
		});
		var url = '${ctx}/business/storage?methodtype=receiptListPrint&contractIds=' + contractId;
		
		callProductDesignView("打印入库单",url);
	}

	//显示入库单信息
	function receiptView(contractId) {

		var url = '${ctx}/business/storage?methodtype=showHistory&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("显示入库单",url);
	}
	
	function doPrintContract(contractId) {
	
		var url = '${ctx}/business/requirement?methodtype=contractPrint';
		url = url +'&contractId='+contractId;
		//alert(url)		
		callProductDesignView("打印合同",url);	

	}
	
	
</script>
<script type="text/javascript">

function productPhotoView() {

	var ysid = $("#receivable\\.ysid").val();
	var detailid = $("#receivable\\.receivableid").val();

	$.ajax({
		"url" :"${ctx}/business/receivable?methodtype=getProductPhoto&detailId="+detailid+"&YSId="+ysid,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["productFileCount"];

			photoView('productPhoto','uploadProductPhoto',countData,data['productFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
         	alert("图片显示失败.")
		 }
	});
	
}//产品图片

function photoView(id, tdTable, count, data) {
	
	var row = 0;
	for (var index = 0; index < count; index++) {
		var path = '${ctx}' + data[index];
		var pathDel = data[index];		
		var trHtml = showPhotoRow(id,tdTable,path,pathDel,index);		
		$('#' + id + ' td.photo:eq(' + row + ')').after(trHtml);
		row++;
	}
}

function deletePhoto(tableId,tdTable,path) {
	
	var url = '${ctx}/business/receivable?methodtype='+tableId+'Delete';
	url+='&tabelId='+tableId+"&path="+path;
	    
	if(!(confirm("确定要删除该图片吗？"))){
		return;
	}
    $("#formModel").ajaxSubmit({
		type: "POST",
		url:url,	
		data:$('#formModel').serialize(),// 你的formid
		dataType: 'json',
	    success: function(data){
	    	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
			}
			
			//删除后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片删除失败,请重试。")
		}
	});
}

function uploadPhoto(tableId,tdTable, id) {

	var url = '${ctx}/business/receivabelUpload'
			+ '?methodtype=uploadPhoto' + '&id=' + id;
		
	$("#formModel").ajaxSubmit({
		type : "POST",
		url : url,
		data : $('#formModel').serialize(),// 你的formid
		dataType : 'json',
		success : function(data) {
	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
			}
			
			//添加后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片上传失败,请重试。")
		}
	});
}

</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="dangqianMax" value="">
	<form:hidden path="receivable.receivableid"  value="${detail.receivableId }"/>
	<form:hidden path="receivable.ysid"          value="${order.YSId }"/>
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="150px">${order.YSId }</td>	

				<td class="label" width="100px">产品编号：</td>					
				<td width="150px">${order.productId }</td>
														
				<td width="100px" class="label">产品名称：</td>
				<td>${order.productName }</td>
			</tr>
			<tr>
														
				<td class="label">收款条件：</td>
				<td>出运后&nbsp;${order.paymentTerm }&nbsp;天</td>
																			
				<td class="label">预定收款日：</td>
				<td>${order.reserveDate } </td>		
													
				<td class="label">客户名称：</td>
				<td>${order.customerFullName }</td>
			</tr>
			
			<tr>
				<td class="label">应收款总金额：</td>
				<td class="font16">
					<span id="orderPrice">${order.orderPrice }</span>
					<form:hidden path="receivable.amountreceivable" class="short num read-only" value="${order.orderPrice }"/></td>
					
				<td class="label">已收款合计：</td>
				<td class="font16" colspan="3">
					<span id="actualCnt">${order.actualCnt }</span>（包含本次收款）</td>		
			</tr>	
			
		</table>
	</fieldset>
	<fieldset>
		<legend> 本次收款</legend>
		<table class="form" id="table_form2">
			<tr style="text-align: center;">			
				<td class="" width="100px">收款单号</td>
				<td class="" width="100px">收款日期</td>
				<td class="" width="150px">收款人</td>
				<td class="" width="150px">本次银行扣款</td>	
				<td class="" width="150px">本次实际收款</td>
				<!-- <td class="" width="100px">剩余金额</td> -->
				<td>备注</td>
			</tr>
			<tr style="text-align: center;">
				
				<td><form:input path="receivableDetail.receivableserialid" class="short required read-only" 
					 	 value="${detail.receivableSerialId }" /></td>
				<td><form:input path="receivableDetail.collectiondate" class="short required read-only"  value="${detail.collectionDate }" /></td>
				<td><form:input path="receivableDetail.payee" class="short required read-only"  value="${userName }"/></td>
				<td>
					<form:input path="receivableDetail.bankdeduction" class="short num font16 bank" value="${detail.bankDeduction }"/></td>
					
				<td>
					<form:input path="receivableDetail.actualamount" class="num font16 bank" value="${detail.actualAmount }"/></td>
				
				<!-- 
				<td class="font16">
					<span id="lastSurplus">0</span></td> -->
					
				<td><form:input path="receivableDetail.remarks" class="middle" value="${detail.remarks }"/></td>
														
			</tr>
		</table>
	</fieldset>
	<div style="clear: both"></div>	
	<div id="DTTT_container" align="right" style="margin-right: 30px;">
		<button class="DTTT_button DTTT_button_text" id="insert" >确认收款</button>
	<!-- 	<a class="DTTT_button DTTT_button_text" onclick="doPrintReceiptList();return false;">批量打印入库单</a> -->
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	
	<fieldset style="margin-top: -20px;">
		<legend> 历史收款记录</legend>
		<table class="display" id="history">
			<thead>
				<tr> 		
					<th width="100px">收款单编号</th>				
					<th width="100px">收款日期</th>
					<th width="100px">收款人</th>				
					<th width="100px">银行扣款</th>
					<th width="100px">收款金额</th>	
					<th>备注</th>
				</tr>
			</thead>
			
			
		</table>
	</fieldset>		
	 
	<fieldset>
		<span class="tablename">收款票据</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
		<div class="list">
			<div class="showPhotoDiv" style="overflow: auto;">
				<table id="productPhoto" style="width:100%;height:335px">
					<tbody><tr><td class="photo"></td></tr></tbody>
				</table>
			</div>
		</div>	
	</fieldset>
	 
</form:form>

</div>
</div>
</body>

</html>
