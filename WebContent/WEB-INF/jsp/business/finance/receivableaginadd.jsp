<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应收款-继续收款</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	

function historyAjax() {
	
	var table = $('#history').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var receivableId = '${receivableId }';

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
		"sAjaxSource" : "${ctx}/business/receivable?methodtype=getReceivableDetail&receivableId="+receivableId,
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
					
					doComputeTax();
					
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


function doComputeTax(){
	
	var bank = contractSum(3);//银行扣款
	var shiji = contractSum(4);//实际收款
	var heji = bank + shiji;//实收合计
	
	$('#bankCnt').html(floatToCurrency(bank));
	$('#actualAmountCnt').html(floatToCurrency(shiji));
	$('#oreadyRecivable').html(floatToCurrency(heji));
	
	var cnt = currencyToFloat($('#receivable\\.amountreceivable').val());
	var sup = cnt - heji;
	var currency = $('#currency').val();//币种

	$('#receivableDetail\\.actualamount').val(floatToSymbol(sup,currency));
	$('#thisCount').val(floatToSymbol(sup,currency));
	$('#currMax').val(sup);
	
}

//列合计
function contractSum(col){

	var sum = 0;
	$('#history tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(col).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

function orderDetailAjax() {

	var receivableId = '${receivableId}';

	var t = $('#detail').DataTable({			
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
		"sAjaxSource" : "${ctx}/business/receivable?methodtype=getOrderDetail&receivableId="+receivableId,
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
					
					//计算收款金额					
					//$('#receivableDetail\\.actualamount').val(cnt2);
					//$('#receivableDetail\\.bankdeduction').val('0');
					
					//$('#thisCount').val(cnt2)
					
					
					var supplier = data['data']['0']['customerFullName'];		
					var currency = data['data']['0']['currencyName'];
					//alert('currency:'+currency)
					$('#supplier').text(supplier);
					
					var cnt = orderSum();
					var cnt2 = floatToSymbol(cnt,currency)
					$('#orderPrice').text(cnt2);
					$('#orderCnt').text(floatToCurrency(cnt));
					$('#receivable\\.amountreceivable').val(cnt);
					$('#currency').val(currency);
					
					historyAjax();//历史收款单
					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
		
		"columns" : [
		           {"data": null,"className":"td-center"
					}, {"data": "YSId","className":"td-left"
					}, {"data": "productId","className":"td-left"
					}, {"data": "productName","className":""
					}, {"data": "totalPrice","className":"td-right"
					}, {"data":null,"className":""
					}
			],
			"columnDefs":[
				{"targets":0,"render":function(data, type, row){
					return row["rownum"];
				}},
	    		{"targets":5,"render":function(data, type, row){
	    			return "";
	    		}}
	    	] 
		
	}).draw();

};
	
//列合计
function orderSum(){

	var sum = 0;
	$('#detail tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(4).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

	$(document).ready(function() {

		orderDetailAjax();//订单明细
		//historyAjax();//历史收款单
		
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

			$("#insert").attr("disabled", "disabled");
			
			$('#formModel').attr("action", "${ctx}/business/receivable?methodtype=receivableAginInsert"+"&YSId="+YSId);
			$('#formModel').submit();
		});
		
				
		//银行扣款		
		$("#receivableDetail\\.bankdeduction") .blur(function(){
			//计算本次最大收款金额			
			var actualCnt =  $('#currMax').val();//currencyToFloat($('#receivable\\.amountreceivable').val());//预计收款合计（包含银行扣款）
			var currency = $('#currency').val();//币种
			var bank  = currencyToFloat($('#receivableDetail\\.bankdeduction').val());//本次银行扣款
			var shiji = currencyToFloat($('#receivableDetail\\.actualamount').val());//本次实际收款	
			var thisCount = bank + shiji
			
			if(bank < 0 ){
				$().toastmessage('showWarningToast', "银行扣款不能为负数。");	
				bank = 0;
			}

			if(bank + shiji > actualCnt){
				$().toastmessage('showWarningToast', "总收款不能超过应收款。");	
				bank = actualCnt - shiji;
			}
			
			if(bank > shiji){
				$().toastmessage('showWarningToast', "银行扣款不能大于应收款");	
				bank = actualCnt - shiji;
			}
			
			thisCount = bank + shiji;//重新计算
			$('#receivableDetail\\.bankdeduction').val(floatToSymbol(bank,currency));
			$('#thisCount').val(floatToSymbol(thisCount,currency));
		});
		
		//收款
		$("#receivableDetail\\.actualamount") .blur(function(){
			//计算本次最大收款金额
			var actualCnt =  $('#currMax').val();//currencyToFloat($('#receivable\\.amountreceivable').val());//预计收款合计（包含银行扣款）
			var currency = $('#currency').val();//币种
			var bank  = currencyToFloat($('#receivableDetail\\.bankdeduction').val());//本次银行扣款
			var shiji = currencyToFloat($('#receivableDetail\\.actualamount').val());//本次实际收款			
			var thisCount = bank + shiji
			
			if(shiji < 0 ){
				$().toastmessage('showWarningToast', "收款金额不能为负数。");	
				$('#receivableDetail\\.actualamount').val('0');
				return;
			}

			if(bank + shiji > actualCnt){
				$().toastmessage('showWarningToast', "总收款不能超过应收款。");	
				var tmp = actualCnt - bank;
				$('#receivableDetail\\.actualamount').val(floatToSymbol(tmp,currency));
				return;
			}
			
			thisCount = bank + shiji;//重新计算
			$('#receivableDetail\\.actualamount').val(floatToSymbol(shiji,currency));
			$('#thisCount').val(floatToSymbol(thisCount,currency));
		});
		
	});
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="paymentId" value="">
	<input type="hidden" id="currency"  value="${formModel.receivable.currency }"/>
	<input type="hidden" id="currMax" value=""><!-- 本次最大收款金额 -->
	<form:hidden path="receivable.receivableid"  value="${formModel.receivable.receivableid }"/>
	<form:hidden path="receivable.parentid"      value="${formModel.receivable.parentid }"/>
	<form:hidden path="receivable.subid"         value="${formModel.receivable.subid }"/>
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr>
				<td class="label" width="100px">应收款总金额：</td>
				<td class="font16" width="150px">
					<span id="orderPrice"></span>
					<form:hidden path="receivable.amountreceivable" class="" value=""/></td>
				
				<td class="label" width="100px">已收款金额：</td>
				<td class="font16" width="150px">
					<span id="oreadyRecivable"></span>
					
				<td class="label" width="100px">客户名称：</td>
				<td colspan="5"><span id="supplier"></span></td>
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
				<td class="" width="150px">本次收款金额</td>
				<td class="" width="150px">银行扣款</td>	
				<td class="" width="100px">本次收款总计</td>
				<td>备注</td>
			</tr>
			<tr style="text-align: center;">
				
				<td><form:input path="receivableDetail.receivableserialid" class="short required read-only" 
					 	value="${formModel.receivableDetail.receivableserialid }"/>
					 <form:hidden path="receivableDetail.subid" class="short required read-only" 
					 	value="${formModel.receivableDetail.subid }"/></td>
				<td><form:input path="receivableDetail.collectiondate" class="short required read-only"  /></td>
				<td><form:input path="receivableDetail.payee" class="short required read-only"  value="${userName }"/></td>
				<td>
					<form:input path="receivableDetail.actualamount" class="num font16 bank" value=""/></td>
					
				<td>
					<form:input path="receivableDetail.bankdeduction" class="short num font16 bank" value="0"/></td>
				
				<td>
					<input type="text" id="thisCount" class="read-only font16" /></td>
					
				<td><form:input path="receivableDetail.remarks" class="" value=""/></td>
														
			</tr>
		</table>
	</fieldset>
	<div style="clear: both"></div>	
	<div id="DTTT_container" align="right" style="margin-right: 30px;">
		<button class="DTTT_button " id="insert" >确认收款</button>
		<a class="DTTT_button  goBack" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 本次收汇所包含订单</legend>
		<table class="display" id="detail">
			<thead>
				<tr> 		
					<th width="50px">No</th>				
					<th width="100px">耀升编号</th>
					<th width="200px">产品编号</th>				
					<th width="">产品名称</th>
					<th width="100px">收款金额</th>	
					<th width="100px"></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td><span id="orderCnt"></span></td>
					<td></td>
				</tr>
			</tfoot>			
		</table>
	</fieldset>	
	<fieldset>
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
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td><span id="bankCnt"></span></td>
					<td><span id="actualAmountCnt"></span></td>
					<td></td>
				</tr>
			</tfoot>			
			
		</table>
	</fieldset>		

</form:form>

</div>
</div>
</body>

</html>