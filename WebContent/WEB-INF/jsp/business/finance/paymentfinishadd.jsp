<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应付款完成-录入</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	function contractAjax() {

		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
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
				{"className" : 'td-left'},//
				{"className" : 'td-center'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//			
				
			],	
			
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
		$("#history\\.finishdate").val(shortToday());
		$("#history\\.finishdate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		$("#goBack").click(
				function() {
					var url = "${ctx}/business/payment?methodtype=finishMain";
					location.href = url;		
		});
		$("#history").click(
				function() {
					var paymentId = '${payment.paymentId }';
					var url = "${ctx}/business/payment?methodtype=finishHistoryView&paymentId="+paymentId;
					location.href = url;		
		});

		
		$("#insert").click(
				function() {
			var suplus = $('#suplus').val();
			var paymentamount = $('#history\\.paymentamount').val();
			paymentamount = currencyToFloat(paymentamount);
			
			if(paymentamount<=0){				
				$().toastmessage('showWarningToast', "付款金额必须大于零。");
				return;
			}
			if(paymentamount > suplus){
				$().toastmessage('showWarningToast', "付款金额不能大于剩余应付款。");
				$('#history\\.paymentamount').val(suplus);
				return;				
			}
					
			$('#formModel').attr("action", "${ctx}/business/payment?methodtype=finishInsert");
			$('#formModel').submit();
		});

		contractAjax();//合同明细
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
		
		var contract = contractSum(4);
		var minis = contractSum(5);
		var payment = contractSum(6);
		$('#contractTotal').html(floatToCurrency(contract));
		$('#minisTotal').html(floatToCurrency(minis));
		$('#paymentTotal').html(floatToCurrency(payment));		
		$('#payment\\.totalpayable').val(floatToCurrency(payment));
		
		//剩余应付款
		var paymentAmount = '${paymentAmount}';				
		var curr = floatToCurrency( payment - paymentAmount );
		$('#suplus').val(curr);
		$('#history\\.paymentamount').val(curr);
		$('#paymentAmount').html(floatToCurrency(paymentAmount));
	});
	
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
</script>
<script type="text/javascript">

function productPhotoView() {

	var paymentId = $("#history\\.paymentid").val();
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


function doShowProduct() {
	var materialId = '${product.materialId}';
	callProductView(materialId);
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
	//alert(url)
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

	<form:hidden path="history.paymentid" value="${payment.paymentId }"/>
	<form:hidden path="history.parentid"  />
	<form:hidden path="history.subid"  />
	<form:hidden path="payment.recordid"  value="${payment.recordId }" />
	<form:hidden path="payment.paymentid"  value="${payment.paymentId }" />
	<form:hidden path="payment.totalpayable"  value="${payment.totalPayable }" />
	<form:hidden path="payment.supplierid"  value="${supplier.supplierId }" />
	<input type="hidden" id="suplus" />
	
	<fieldset>
		<legend> 付款申请单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">申请单编号：</td>					
				<td width="150px">${payment.paymentId }</td>	
								
				<td class="label" width="100px">申请人：</td>					
				<td width="150px">${payment.applicantName }</td>
														
				<td width="100px" class="label">申请日期：</td>
				<td>${payment.requestDate }</td>				
			</tr>
			<tr> 				
				<td class="label" width="100px">供应商编号：</td>					
				<td width="150px">${supplier.supplierId }</td>
														
				<td width="100px" class="label">供应商简称：</td>
				<td width="150px">${supplier.shortName }</td>
														
				<td width="100px" class="label">供应商名称：</td>
				<td>${supplier.supplierName }</td>
			</tr>
			<tr>			
				<td class="label" width="100px">申请付款总额：</td>					
				<td class="font16" width="150px">${payment.totalPayable }</td>
								
				<td class="label" width="100px">付款条件：</td>					
				<td width="150px">入库后&nbsp;${supplier.paymentTerm }&nbsp;天</td>
														
				<td width="100px" class="label">申请付款状态：</td>
				<td class="bold">${payment.finishStatus }</td>
			</tr>										
		</table>
	</fieldset>	
	<div style="clear: both"></div>	
	<div id="DTTT_container" align="right" style="margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认付款</a>
		<a class="DTTT_button DTTT_button_text" id="history" >查看付款记录</a>
		<a class="DTTT_button DTTT_button_text" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 付款信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">付款单编号：</td>					
				<td width="150px">
					<form:input path="history.paymenthistoryid" class="short read-only"  /></td>	
								
				<td class="label" width="100px">付款人：</td>					
				<td width="150px">
					<form:input path="history.finishuser" class="short required read-only"  value="${userName }"/></td>
														
				<td width="100px" class="label">付款日期：</td>
				<td>
					<form:input path="history.finishdate" class="read-only short"  value=""/></td>				
			</tr>
			<tr>			
				<td class="label" width="100px">本次付款金额：</td>					
				<td class="font16" width="150px">
					<form:input path="history.paymentamount" class="num short"  /></td>

				<td class="label" width="100px">已付款总额：</td>					
				<td class="font16" width="150px">&nbsp;<span id="paymentAmount"></span></td>
															
				<td class="label" width="100px">币种：</td>					
				<td width="150px">
					<form:select path="history.currency" style="width: 120px;">							
					<form:options items="${formModel.currencyList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
														
				<td width="100px" class="label">付款方式：</td>
				<td class="bold">
					<form:select path="history.paymentmethod" style="width: 120px;">							
					<form:options items="${formModel.paymentMethodList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
			</tr>										
		</table>
	</fieldset>
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
	
	<fieldset>
		<legend> 审核结果</legend>
		<table class="form" id="table_form2">
			<tr>
				<td width="100px" class="label">审核人：</td>
				<td width="100px" >${payment.approvalUser }</td>
				<td width="100px" class="label">审核结果：</td>
				<td width="100px" >${payment.approvalStatus }</td>
				<td width="100px" class="label">发票编号：</td>
				<td width="150px" >${payment.invoiceNumber }</td>
				<td width="100px" class="label">审核日期：</td>
				<td>${payment.approvalDate }</td>
			</tr>	
			<tr>	
				<td class="label" width="100" style="vertical-align: baseline;">审核意见：</td>			
				<td colspan="7" >
					<pre>${payment.approvalFeedback }</pre></td>
			</tr>						
		</table>
	</fieldset>
	<fieldset>
		<legend> 合同明细</legend>
		<div class="list">
		<table id="example" class="display" >
			<thead>				
				<tr>
					<th width="30px">No</th>
					<th width="120px">合同编号</th>
					<th width="80px">耀升编号</th>
					<th width="80px">约定付款日</th>
					<th width="100px">合同金额</th>
					<th width="100px">增减项总额</th>
					<th width="100px">应付款金额</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var='list' items='${contract}' varStatus='status'>	
					<tr>
						<td>${status.index+1 }</td>
						<td><a href="###" onClick="doShowContract('${list.contractId }')">${list.contractId }</a></td>
						<td>${list.YSId }</td>
						<td>${list.agreementDate }</td>
						<td>${list.totalPrice }</td>
						<td class="td-right"><span id="chargeback${status.index }"></span></td>
						<td class="td-right"><span id="payment${status.index }"></span></td>
						<td class="td-center">
							<form:hidden path="paymentList[${status.index }].contractid"  value="${list.contractId }" />
							<form:hidden path="paymentList[${status.index }].payable"  value="${list.total }" />
					</tr>
					<script type="text/javascript">
						var contract = currencyToFloat('${list.totalPrice }');
						var chargeback = currencyToFloat('${list.chargeback }');
						var payment = floatToCurrency( contract + chargeback );
						var index = ${status.index }
						//alert('payment--chargeback:'+chargeback+'---'+payment)
						$('#payment'+index).html(payment);
						$('#chargeback'+index).html(floatToCurrency(chargeback));
					</script>
				</c:forEach>
			</tbody>
			<!--  -->
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td>合计：</td>
					<td><span id="contractTotal" style="font-weight: bold;"></span></td>
					<td><span id="minisTotal" style="font-weight: bold;"></span></td>
					<td><span id="paymentTotal" style="font-weight: bold;"></span></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
		</div>
	</fieldset>
</form:form>

</div>
</div>
</body>

</html>
