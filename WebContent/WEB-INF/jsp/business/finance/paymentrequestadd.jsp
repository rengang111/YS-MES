<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应付款申请-新建</title>
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
			
		$("#invoice\\.invoicedate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 
		
		$('.read-only').attr('readonly', "true");
		$("#payment\\.requestdate").val(shortToday());
		$("#payment\\.requestdate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
		}); 
		
		var paymentTypeId='${paymentTypeId}';
		var name="正常付款";
		if(paymentTypeId == '020'){
			name="预付款";
		}
		$("#paymentType").text(name);
		
		//申请单编号
		var paymentId = $('#paymentId').val();

		if(paymentId == '' || paymentId == null){
			$('#payment\\.paymentid').val('（保存后自动生成）');
		}else{	
			$('#payment\\.paymentid').val(paymentId);	
		}
		
		$(".goBack").click(
				function() {
					var paymentTypeId=$("#paymentTypeId").val();
					var url = "${ctx}/business/payment";
					if( paymentTypeId == '020')
						url = "${ctx}/business/payment"+"?methodtype=beforehandMainInit";
						
					location.href = url;		
		});

		
		$("#insert").click(
				function() {

					var num    = $('#invoice\\.invoicenumber').val();
					var date   = $('#invoice\\.invoicedate').val();
					var type   = $('#invoice\\.invoicetype').val();
					var amount = $('#invoice\\.invoiceamount').val();
					
					if($.trim(amount) ==''){
						$().toastmessage('showWarningToast', "请输入发票金额。");
						return;				
					}
					if( type !='030' ){//选择有发票
						if($.trim(num) =='' ){
							$().toastmessage('showWarningToast', "请输入发票编号。");
							return;
						}
						if($.trim(date) ==''){
							$().toastmessage('showWarningToast', "请输入发票日期。");
							return;				
						}
					}else{
						if($.trim(num) !='' ){
							$().toastmessage('showWarningToast', "有发票编号，请选择发票类型。");
							return;
						}
						if($.trim(num) !='' && $.trim(date) ==''){
							$().toastmessage('showWarningToast', "有发票编号，请输入发票日期。");
							return;				
						}
					}
					
					$("#insert").attr("disabled", "disabled");
					
					var paymentId = $('#payment\\.paymentid').val();
					var paymentTypeId =$("#paymentTypeId").val();
					if(paymentId == '（保存后自动生成）')
						$('#payment\\.paymentid').val('');//清除非正常ID

					$('#formModel').attr("action", "${ctx}/business/payment?methodtype=applyInsert"+"&paymentId="+paymentId);
					$('#formModel').submit();
		});
		

		ajax();
		productPhotoView();//付款单
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
		
		doComputeTax();//价税计算
		
		
	});
	
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
		$('#payment\\.totalpayable').val(floatToCurrency(payment));
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

	var paymentId = $("#payment\\.paymentid").val();
	var supplierId = '${supplier.supplierId }';

	$.ajax({
		"url" :"${ctx}/business/payment?methodtype=getProductPhoto&paymentId="+paymentId+"&supplierId="+supplierId,	
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
	
	var url = '${ctx}/business/payment?methodtype='+tableId+'Delete';
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
	
	var paymentId = $('#payment\\.paymentid').val();
	if(paymentId == '（保存后自动生成）')
		$('#payment\\.paymentid').val('');//清除非正常ID
		//alert('url:'+url)	
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
					$('#payment\\.paymentid').val(data["paymentId"]);//设置新的ID
					$('#payment\\.recordid').val(data["recordId"]);//设置新的ID
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

	<form:hidden path="payment.parentid" />
	<form:hidden path="payment.subid"  />
	<form:hidden path="payment.recordid"  />
	<form:hidden path="payment.finishstatus"  />
	<form:hidden path="payment.contractids"  value="${contractIds }"/>
	<form:hidden path="payment.supplierid" value="${supplier.supplierId }" />
	<form:hidden path="payment.paymenttype"  value="${paymentTypeId }"/><!-- 1:正常；2：预付 -->
	<input type="hidden" id="paymentTypeId"  value="${paymentTypeId }"/><!-- 1:正常；2：预付 -->
	<input type="hidden" id="paymentId" value="${formModel.payment.paymentid }">
	<fieldset>
		<legend> 付款申请单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">申请单编号：</td>					
				<td width="150px">
					<form:input path="payment.paymentid" class="read-only"  value=""/></td>	
								
				<td class="label" width="100px">申请人：</td>					
				<td width="150px">
					<form:input path="payment.applicant" class="short required read-only"  value="${userName }"/></td>
														
				<td width="100px" class="label">申请日期：</td>
				<td>
					<form:input path="payment.requestdate" class="read-only"  value=""/></td>				
			</tr>
			<tr> 				
				<td class="label" width="100px">供应商编号：</td>					
				<td width="150px">&nbsp;<a href="###" onClick="doShowSupplier('${supplier.supplierId }')">${supplier.supplierId }</a></td>
														
				<td width="100px" class="label">供应商简称：</td>
				<td width="150px">&nbsp;${supplier.shortName }</td>
														
				<td width="100px" class="label">供应商名称：</td>
				<td>&nbsp;${supplier.supplierName }</td>
			</tr>
			<tr>			
				<td class="label" width="100px">付款总额：</td>					
				<td width="150px">
					<form:input path="payment.totalpayable" class="read-only num"  style="width: 130px;"/></td>
								
				<td class="label" width="100px">供应商付款条件：</td>					
				<td width="150px">&nbsp;发票后&nbsp;${supplier.paymentTerm }&nbsp;天</td>
														
				<td width="100px" class="label">付款类别：</td>
				<td>&nbsp;<span id="paymentType"></span></td>
			</tr>										
		</table>
	</fieldset>
	<div style="clear: both"></div>	
	<div id="DTTT_container" align="right" style="margin-right: 30px;">
		<button type="button" class="DTTT_button" id="insert" >提交申请</button>
		<a class="DTTT_button DTTT_button_text" onclick="doPrintReceiptList();return false;">批量打印入库单</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 发票信息</legend>
		<table class="form" id="table_form2">
			<tr>
				<td class="label" width="100px">发票金额：</td> 
				<td width="200px"><form:input path="invoice.invoiceamount"  class="num"  value=""/></td>
	
				<td class="label" width="100px">发票类型： </td>
				<td width="150px">
					<form:select path="invoice.invoicetype" style="width: 120px;" value="">							
					<form:options items="${invoiceTypeOption}" 
						itemValue="key" itemLabel="value" /></form:select> </td>
			
				<td class="label" width="100px">发票编号：</td> 
				<td width="200px"><form:input path="invoice.invoicenumber"  class="middle"  value=""/></td>
				
				<td class="label" width="100px">发票日期：</td> 
				<td width="200px"><form:input path="invoice.invoicedate"  class="short"  value=""/></td>
			</tr>												
		</table>
	</fieldset>
	<fieldset>
		<legend> 合同明细</legend>
		<div class="list">
		<table id="example" class="display" >
			<thead>				
				<tr>
					<th width="10px">No</th>
					<th width="100px">合同编号</th>
					<th width="80px">入库单号</th>
					<th width="80px">耀升编号</th>
					<th width="70px">约定付款日</th>
					<th width="60px">合同金额</th>
					<th width="50px">增减项</th>
					<th width="50px">扣款方式</th>
					<th width="60px">应付款</th>
					<th width="50px">退税率</th>
					<th width="40px">税</th>
					<th width="50px">价</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var='list' items='${contract}' varStatus='status'>	
					<tr>
						<td class="td-center">${status.index+1 }</td>
						<td><a href="###" onClick="doShowContract('${list.contractId }')">${list.contractId }</a>
							<form:hidden path="contractList[${status.index}].contractid"  value="${list.contractId }" /></td>
						<td><a href="###" onClick="receiptView('${list.contractId }')"><span id="receipt${status.index }">${list.receiptId }</span></a></td>
						<td>${list.YSId }</td>
						<td class="td-center">${list.agreementDate }</td>
						<td class="td-right"><span id="contract${status.index }">${list.totalPrice }</span></td>
						<td class="td-right"><span id="chargeback${status.index }"></span></td>
						
						<!-- 扣款方式 -->
						<td class="td-right">
							<form:select path="paymentList[${status.index}].chargetype">
								<form:options items="${chargetypeList}"  itemValue="key" itemLabel="value" />
							</form:select></td>
						<!-- 应付款 -->
						<td class="td-right">
							<span id="payment${status.index }"></span>
							<form:hidden path="paymentList[${status.index }].payable" /></td>
							
						<td class="td-right">
							<form:select path="contractList[${status.index}].taxrate">
								<form:options items="${taxRateList}"  itemValue="key" itemLabel="value" />
							</form:select></td>
						<td class="td-right"><span id="taxes${status.index}"></span>
							<form:hidden path="contractList[${status.index}].taxes" /></td>
						<td class="td-right"><span id="taxexcluded${status.index}"></span>
							<form:hidden path="contractList[${status.index}].taxexcluded" /></td>
						<td class="td-center">
							<a href="###" onClick="doPrintContract('${list.contractId }')">打印合同</a><br>
							<a href="###" onClick="doShowStockin('${list.contractId }')">打印入库单</a>
						</td>
							<form:hidden path="paymentList[${status.index }].recordid"    value="${list.detailRecordId }" />
							<form:hidden path="paymentList[${status.index }].contractid"  value="${list.contractId }"  class="contractid" />
						
					</tr>
					<script type="text/javascript">
						var contract = currencyToFloat('${list.totalPrice }');
						var chargeback = currencyToFloat('${list.chargeback }');
						var payment = contract + chargeback;
						var index = ${status.index };
						
						var taxrate =  $("#contractList" + index + "\\.taxrate").val() / 100;//退税率
						var taxexcluded =  payment / (taxrate + 1 ) ; //应付款 / 税率 = 价
						var taxes = payment - taxexcluded;//应付款 - 价 = 税
						
						var vtaxes = floatToCurrency(taxes);
						var vtaxexcluded = floatToCurrency(taxexcluded);
						
						//alert("payment-taxrate-taxes-taxexcluded："+payment+"--"+taxrate+"--"+taxes+"--"+taxexcluded)
						$("#contractList" + index + "\\.taxes").val(vtaxes);
						$('#taxes'+index).html(vtaxes);
						$("#contractList" + index + "\\.taxexcluded").val(vtaxexcluded);
						$('#taxexcluded'+index).html(vtaxexcluded);
						$('#payment'+index).html(floatToCurrency(payment));
						$("#paymentList" + index + "\\.payable").val(payment);
						$('#chargeback'+index).html(floatToCurrency(chargeback));
						var receipt = '${list.receiptId }';
						$('#receipt'+index).html(jQuery.fixedWidth(receipt,12)); 
						
					</script>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>合计：</td>
					<td><span id="contractTotal" style="font-weight: bold;"></span></td>
					<td><span id="minisTotal" style="font-weight: bold;"></span></td>
					<td></td>
					<td><span id="paymentTotal" style="font-weight: bold;"></span></td>
					<td></td>
					<td><span id="taxesTotal" style="font-weight: bold;"></span></td>
					<td><span id="taxExcludedTotal" style="font-weight: bold;"></span></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
		</div>
	</fieldset>
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
