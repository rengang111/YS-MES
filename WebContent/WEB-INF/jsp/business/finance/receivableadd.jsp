<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应收款-收款</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	function ajax(scrollHeight) {
		
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	        "bWidth"	: false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
		    "autoWidth":false,
			"dom"		: '<"clear">rt',
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},	
			"columns": [
				{"className" : 'td-center'},//
				{"className" : 'td-left'},//
				{"className" : ''},//
				{"className" : 'td-left'},//
				{"className" : 'td-center'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-left'},//
			],	
			
		});		
			
		t.on('change', 'tr td:nth-child(8)',function() {

			var $td = $(this).parent().find("td");

			var $oChargeType = $td.eq(7).find("option:checked");//扣款方式
			var $oContract = $td.eq(5).find("span");//合同款
			var $oCharge   = $td.eq(6).find("span");//增减项
			var $oPayment  = $td.eq(8).find("span");//应付款
			var $oPaymentI = $td.eq(8).find("input");//应付款
			var $oTaxRate = $td.eq(9).find("option:checked");//退税率
			var $oTaxesS  = $td.eq(10).find("span");//税
			var $oTaxesI  = $td.eq(10).find("input");//税
			var $oTaxExcludedS = $td.eq(11).find("span");//价
			var $oTaxExcludedI = $td.eq(11).find("input");//价
			

			var chargeType = $oChargeType.val();		
			var fContract = currencyToFloat($oContract.text());	
			var fCharge = currencyToFloat($oCharge.text());	
			var fPayment = currencyToFloat($oPayment.text());
			var fTaxRate = currencyToFloat($oTaxRate.val());
//alert(chargeType+"---"+fPayment)
			if(chargeType == '020'){
				//外扣
				fPayment = fContract;//直接采用合同金额
			}else{
				fPayment = fContract + fCharge;//直接采用合同金额
			}
			
			var fTaxExcluded = fPayment / (fTaxRate / 100 + 1 );//价
			var fTaxes = fPayment - fTaxExcluded;//税

			var vTaxes = floatToCurrency(fTaxes);
			var vTaxExcluded = floatToCurrency(fTaxExcluded);
			var vPayment = floatToCurrency(fPayment);

			$oPayment.text(vPayment);
			$oPaymentI.val(vPayment);
			$oTaxesS.text(vTaxes);
			$oTaxesI.val(vTaxes);
			$oTaxExcludedS.text(vTaxExcluded);
			$oTaxExcludedI.val(vTaxExcluded);
			//
			doComputeTax();//价税计算

		});
		
		t.on('change', 'tr td:nth-child(10)',function() {

			var $td = $(this).parent().find("td");

			var $oPayment = $td.eq(8).find("span");//应付款
			var $oTaxRate = $td.eq(9).find("option:checked");//退税率
			var $oTaxesS = $td.eq(10).find("span");//税
			var $oTaxesI = $td.eq(10).find("input");//税
			var $oTaxExcludedS = $td.eq(11).find("span");//价
			var $oTaxExcludedI = $td.eq(11).find("input");//价
			
			var fPayment = currencyToFloat($oPayment.text());
			var fTaxRate = currencyToFloat($oTaxRate.val());

			var fTaxExcluded = fPayment / (fTaxRate / 100 + 1 );//价
			var fTaxes = fPayment - fTaxExcluded;//税

			var vTaxes = floatToCurrency(fTaxes);
			var vTaxExcluded = floatToCurrency(fTaxExcluded);
			
			$oTaxesS.text(vTaxes);
			$oTaxesI.val(vTaxes);
			$oTaxExcludedS.text(vTaxExcluded);
			$oTaxExcludedI.val(vTaxExcluded);
			//
			doComputeTax();//价税计算

		});
		
		t.on('click', 'tr', function() {
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }			
		});
		
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	};
	
	
	$(document).ready(function() {
		
		//日期
		$("#receivableDetail\\.collectiondate").val(shortToday());
		$("#receivableDetail\\.collectiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
		}); 
		
	
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
			alert($('#receivableDetail\\.bankdeduction').val())
			$('#formModel').attr("action", "${ctx}/business/receivable?methodtype=receivableInsert"+"&YSId="+YSId);
			$('#formModel').submit();
		});
		

		//ajax();
		//productPhotoView();//付款单
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
			var surplus = orderPrice - actualCnt;

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
			var surplus = orderPrice - actualCnt;
			
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
		var actualCnt =  currencyToFloat('${order.actualCnt }');
		var orderPrice = currencyToFloat('${order.orderPrice }');
		var currency = '${order.currency}';//币种
		var surplus = orderPrice - actualCnt;
		
		$('#surplus').text(floatToSymbol(surplus,currency));
		$('#receivableDetail\\.bankdeduction').val('0');
		$('#receivableDetail\\.actualamount').val(floatToSymbol(surplus,currency));
		$('#orderPrice').text(floatToSymbol(orderPrice,currency))
		$('#receivable\\.amountreceivable').val(floatToCurrency(orderPrice));
		
		
	});
	
	function doComputeBank(){
		
		//计算本次最大收款金额
		var actualCnt =  currencyToFloat('${order.actualCnt }');
		var orderPrice = currencyToFloat('${order.orderPrice }');
		var currency = '${order.currency}';//币种
		var bank  = currencyToFloat($('#receivableDetail\\.bankdeduction').val());
		var surplus = orderPrice - actualCnt;
		
		var lastSurplus = surplus - bank - shiji
		var shiji = currencyToFloat($('#receivableDetail\\.actualamount').val());
		
		$('#surplus').text(floatToSymbol(surplus,currency));

		$('#receivableDetail\\.actualamount').val(floatToSymbol(shiji,currency));
		$('#receivableDetail\\.bankdeduction').val(floatToSymbol(bank,currency));
		$('#lastSurplus').text(floatToSymbol(lastSurplus,currency));
	}
	
	function doComputeTax(){
		
		var contract = contractSum(5);//合同总金额
		var minis = contractSum(6);//增加项
		var payment = contractSum(8);//应付款
		var taxes = contractSum(10);//税
		var taxExcluded = contractSum(11);//价
		$('#contractTotal').html(floatToCurrency(contract));
		$('#minisTotal').html(floatToCurrency(minis));
		$('#paymentTotal').html(floatToCurrency(payment));
		$('#taxesTotal').html(floatToCurrency(taxes));
		$('#taxExcludedTotal').html(floatToCurrency(taxExcluded));
		$('#receivable\\.totalpayable').val(floatToCurrency(payment));
	}
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/requisition?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}

	//列合计
	function contractSum(col){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(col).text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})
		return sum;
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
	
	function doShowStockin(contractId) {

		var url = '${ctx}/business/storage?methodtype=showStockInByContractId&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("入库单",url);
	}
	
</script>
<script type="text/javascript">

function productPhotoView() {

	var paymentId = $("#receivable\\.paymentid").val();
	var supplierId = '${supplier.supplierId }';

	$.ajax({
		"url" :"${ctx}/business/receivable?methodtype=getProductPhoto&paymentId="+paymentId+"&supplierId="+supplierId,	
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

	var url = '${ctx}/business/paymentBillUpload'
			+ '?methodtype=uploadPhoto' + '&id=' + id;
	
	var paymentId = $('#receivable\\.paymentid').val();
	if(paymentId == '（保存后自动生成）')
		$('#receivable\\.paymentid').val('');//清除非正常ID

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
					$('#receivable\\.paymentid').val(data["paymentId"]);//设置新的ID
					$('#receivable\\.recordid').val(data["recordId"]);//设置新的ID
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

	<input type="hidden" id="paymentId" value="">
	<form:hidden path="receivable.currency"  value="${order.currencyId }"/>
	<form:hidden path="receivable.productid"  value="${order.productId }"/>
	<form:hidden path="receivable.customerid"  value="${order.customerId }"/>
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="150px">
					<form:input path="receivable.ysid" class="read-only"  value="${order.YSId }"/></td>	
								
				<td class="label" width="100px">收款人：</td>					
				<td width="150px">
					<form:input path="receivableDetail.payee" class="short required read-only"  value="${userName }"/></td>
								
				<td class="label" width="100px">收款日期：</td>					
				<td width="150px">
					<form:input path="receivableDetail.collectiondate" class="short required read-only"  /></td>
															
				<td width="100px" class="label">预定收款日：</td>
				<td>${order.reserveDate } </td>				
			</tr>
			<tr>
				<td class="label" width="100px">产品编号：</td>					
				<td width="150px">&nbsp;${order.productId }</td>
														
				<td width="100px" class="label">产品名称：</td>
				<td  colspan="5">&nbsp;${order.productName }</td>
			</tr>
			<tr>
														
				<td width="100px" class="label">付款条件：</td>
				<td width="150px">&nbsp;出运后&nbsp;${order.paymentTerm }&nbsp;天</td>
														
				<td width="100px" class="label">客户名称：</td>
				<td  colspan="5">&nbsp;${order.customerFullName }</td>
			</tr>	
		</table>
	</fieldset>
	<fieldset>
		<legend> 收款记录</legend>
		<table class="form" id="table_form2">
			<tr style="text-align: center;">
				<td class="" width="200px">应收款总额</td>	
				<td class="" width="200px">已收款合计</td>
				<td class="" width="200px">本次预计收款</td>
				<td class="" width="200px">本次银行扣款</td>	
				<td class="" width="200px">本次实际收款</td>
				<td class="" width="200px">剩余金额</td>
				<td ></td>
			</tr>
			<tr style="text-align: center;">
				<td class="font16"><span id="orderPrice">${order.orderPrice }</span>
					<form:hidden path="receivable.amountreceivable" class="short num read-only" value="${order.orderPrice }"/></td>	
				<td class="font16">
					${order.actualCnt }</td>	
										
				<td class="font16">
					<span id="surplus"></span></td>
				
				<td>
					<form:input path="receivableDetail.bankdeduction" class="short num font16 bank" value="0"/></td>
					
				<td>
					<form:input path="receivableDetail.actualamount" class="num font16 bank" value=""/></td>
				
				<td class="font16">
					<span id="lastSurplus">0</span></td>
					
				<td ></td>
														
			</tr>
		</table>
	</fieldset>		
	<div style="clear: both"></div>	
	<div id="DTTT_container" align="right" style="margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >提交付款</a>
	<!-- 	<a class="DTTT_button DTTT_button_text" onclick="doPrintReceiptList();return false;">批量打印入库单</a> -->
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>

	<!-- 
	<fieldset>
		<span class="tablename">付款票据</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
		<div class="list">
			<div class="showPhotoDiv" style="overflow: auto;">
				<table id="productPhoto" style="width:100%;height:335px">
					<tbody><tr><td class="photo"></td></tr></tbody>
				</table>
			</div>
		</div>	
	</fieldset>
	 -->
</form:form>

</div>
</div>
</body>

</html>
