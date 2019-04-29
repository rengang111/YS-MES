<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应付款完成-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
function ContractListAjax() {
	
	var table = $('#example').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var paymentId = $('#payment\\.paymentid').val();

	var t = $('#example').DataTable({			
		"paging": false,
		"lengthChange":false,
		"lengthMenu":[50,100,200],//设置一页展示20条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"ordering "	:true,
		"searching" : false,
		"retrieve" : true,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/payment?methodtype=paymentContractList&paymentId="+paymentId,
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

					doComputeTax();//合同集计
					var supplierId   = data['data'][0]['supplierId'];
					var supplierName = data['data'][0]['supplierName'];
				//	var shortName    = data['data'][0]['shortName'];

					$('#supplierId').text(supplierId);
					$('#payment\\.supplierid').val(supplierId);
					$('#supplierName').text(supplierName);
				//	$('#shortName').text(shortName);

					productPhotoView();//图片需要供应商编号
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},		
		"columns" : [
		        	{"data": null,"className":"dt-body-center"//
				}, {"data": "contractId","className":"td-center"//合同编号
				}, {"data": "receiptId","className":"td-right"//入库单号
				}, {"data": "YSId","className":"td-center"//耀升编号
				}, {"data": null, "className":"td-center",// 4//入库状态
				}, {"data": "totalPrice","className":"td-right"//合同金额
				}, {"data": "chargeback","className":"td-right","defaultContent" : '0'//增减项
				}, {"data": "chargeType","className":"td-center"//扣款方式
				}, {"data": "payable","className":"td-center"//应付款 8
				}, {"data": "taxRate","className":"td-center"//退税率
				}, {"data": "taxes","className":"td-center"//税
				}, {"data": "taxExcluded","className":"td-center"//价
				}, {"data": null,"className":"td-center" // 12  
				}
				
			],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
    			return row['rownum'];
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var txt = "<a href=\"###\" onClick=\"doShowContract('"+row['contractId']+"')\">"+row['contractId']+"</a>";
    			var hidden = '<input type="hidden" id=contract" name="contract"  value="'+row['contractId']+'"   class="contractid" />';
    			return txt +hidden;
    		}},
    		{"targets":2,"render":function(data, type, row){
    			var receipt = jQuery.fixedWidth(data,12)
    			var txt = "<a href=\"###\" onClick=\"receiptView('"+row['contractId']+"')\">"+receipt+"</a>";
				return txt;
    		}},
    		{"targets":4,"render":function(data, type, row){
    			var contractQty  = currencyToFloat(row['contractQty']);
    			var stockinQty  = currencyToFloat(row['stockinQty']);
    			var rtn = '待入库';
    			if(stockinQty > 0){ 
    				if(stockinQty >= contractQty){
        				rtn = '已入库';
    				}else{
        				rtn = '部分入库';  
    				}
    			}
    			return rtn;
    		}},
    		{"targets":5,"render":function(data, type, row){
    			return floatToCurrency(data);
    		}},
    		{"targets":8,"render":function(data, type, row){
    			    			
    			return floatToCurrency(data);
    		}},
    		{"targets":12,"render":function(data, type, row){
    			var delet = "<a href=\"###\" onClick=\"doPrintContract(\"'"+row["contractId"]+"')\">打印合同</a>";
    			
    			return delet;
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
	
	t.on('order.dt search.dt draw.dt', function() {
		t.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			cell.innerHTML = i + 1;
		});
	}).draw();

};//合同明细


	
	function paymentHistoryAjax() {
		
		var table = $('#history').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var paymentId = $('#payment\\.paymentid').val();

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
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/payment?methodtype=paymentHistoryList&paymentId="+paymentId,
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
						
						paymentSum();//已付款合计
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
	    	"language": {
	    		"url":"${ctx}/plugins/datatables/chinese.json"
	    	},		
			"columns" : [
			        	{"data": null,"className":"dt-body-center"
					}, {"data": "paymentId","className":"td-left"
					}, {"data": "finishDate","className":"td-center"
					}, {"data": "finishUser","className":"td-center"
					}, {"data": "currency", "className":"td-center",// 4
					}, {"data": "paymentMethod","className":"td-center"
					}, {"data": "paymentAmount","className":"td-right"
					}, {"data": null,
					}, {"data": null,
					}
				
				],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
	    			return row['rownum'];
	    		}},
	    		{"targets":7,"render":function(data, type, row){
	    			//var edit    = "<a href=\"#\" onClick=\"doUpdateInvoice('" + row["recordId"] + "')\">编辑</a>";
	    			var delet   = "<a href=\"#\" onClick=\"deletePayment('" + row["paymentId"] + "','" + row["recordId"] + "')\">删除</a>";
	    			
	    			return delet;
	            }},
	            {"targets":8,"render":function(data, type, row){
	    			return "";
	    		}},
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
		
		$("#goBack").click(
				function() {
					var url = "${ctx}/business/payment?methodtype=finishMain";
					location.href = url;		
		});
		
		$("#addInit").click(
				function() {
			var paymentId = '${payment.paymentId }';
			var url =  "${ctx}/business/payment?methodtype=finishAddInit&paymentId="+paymentId;
			location.href = url;		
		});

		invoiceAjax();//发票信息
		paymentHistoryAjax();//付款明细

		ContractListAjax();//合同明细
		
		var payment = contractSum(6);	
		$('#payment\\.totalpayable').val(floatToCurrency(payment));
		
	});
	


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
	
	//列合计
	function paymentSum(){

		var sum = 0;
		$('#history tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(6).text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})

		$('#paymentCount').html(floatToCurrency(sum));	
		$('#paymentCount2').html(floatToCurrency(sum));		
	}
	
	function doShowContract(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("采购合同",url);
	}
	
	function deletePayment(paymentId,recordId){
		
		if (confirm("删除后不能恢复，确定要删除吗？")){ //
			$.ajax({
				type : "post",
				url : "${ctx}/business/payment?methodtype=deletePyamentRecord&recordId="+recordId+"&paymentId="+paymentId,
				async : false,
				data : 'key=' + recordId,
				dataType : "json",
				success : function(data) {
					paymentHistoryAjax();
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					
					
				}
			});
		}else{
			//
		}
	}
	
	function invoiceCountFn(){
		
		var cost = 0;
		$('#invoice tbody tr').each (function (){
			
			var temp = $(this).find("td").eq(2).text();//发票金额	
			
			temp = currencyToFloat(temp);
			cost = cost + temp;
						
		});	
		
		$('#invoiceCnt').text(floatToCurrency(cost));
		
	}
	
	function invoiceAjax() {
		
		var table = $('#invoice').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var paymentId = $('#payment\\.paymentid').val();

		var t = $('#invoice').DataTable({			
			"paging": false,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/payment?methodtype=paymentInvoiceList&paymentId="+paymentId,
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
						invoiceCountFn();
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
	    	"language": {
	    		"url":"${ctx}/plugins/datatables/chinese.json"
	    	},		
			"columns" : [
			        	{"data": null,"className":"dt-body-center"
					}, {"data": "invoiceDate","className":"td-center"
					}, {"data": "invoiceAmount","className":"td-right"
					}, {"data": "invoiceType","className":"td-center"
					}, {"data": "invoiceNumber", "defaultContent" : '',// 4
					}, {"data": null,"className":"td-center"
					}, {"data": null,
					}
				],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
	    			return row['rownum'];
	    		}},
	    		{"targets":2,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			return "";
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			var edit    = "<a href=\"#\" onClick=\"doUpdateInvoice('" + row["recordId"] + "')\">编辑</a>";
	    			var delet   = "<a href=\"#\" onClick=\"doDeleteInvoice('" + row["recordId"] + "')\">删除</a>";
	    			
	    			return "";
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
		
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	};
</script>
<script type="text/javascript">

function productPhotoView() {

	var paymentId = $("#payment\\.paymentid").val();
	var supplierId = $('#supplierId').text();//'${payment.supplierId }';
alert('supplierId:'+supplierId+":::"+paymentId)
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
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td>';
		trHtml += '<table style="width:400px;height:300px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr><td>';
		trHtml += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
		trHtml += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
		trHtml += '</a>';
		trHtml += '</td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';

		index++;
		if (index == count) {
			//因为是偶数循环,所以奇数张图片的最后一张为空

			var trHtmlOdd = '<table style="width:400px;margin: auto;" class="">';
			trHtmlOdd += '<tr><td></td></tr>';	
			trHtmlOdd += '</table>';
		} else {
			path = '${ctx}' + data[index];
			pathDel = data[index];

			var trHtmlOdd = '<table style="width:400px;height:300px;margin: auto;" class="form">';
			trHtmlOdd += '<tr><td>';
			trHtmlOdd += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
			trHtmlOdd += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
			trHtmlOdd += '</a>'
			trHtmlOdd += '</td></tr>';
			trHtmlOdd += '</table>';
		}

		trHtml += '<td>';
		trHtml += trHtmlOdd;
		trHtml += '</td>';
		trHtml += "</tr>";

		$('#' + id + ' tr.photo:eq(' + row + ')').after(trHtml);
		row++;

	}
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
	$('#payment\\.totalpayable').val(floatToCurrency(payment));
}
</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<form:hidden path="payment.recordid"  value="${payment.recordId }" />
	<form:hidden path="payment.paymentid"  value="${payment.paymentId }" />
	<form:hidden path="payment.totalpayable"  value="${payment.totalPayable }" />
	
	<fieldset>
		<legend> 付款申请单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">申请单编号：</td>					
				<td width="150px">${payment.paymentId }</td>	
								
				<td class="label" width="100px">申请人：</td>					
				<td width="150px">${payment.applicantName }</td>
														
				<td width="100px" class="label">申请日期：</td>
				<td width="150px">${payment.requestDate }</td>	
				
				<td width="100px" class="label">付款类别：</td>
				<td>${payment.paymentType }</td>			
			</tr>
			<tr> 				
				<td class="label" width="100px">供应商编号：</td>					
				<td width="150px"><span id="supplierId"></span></td>
														
				<td width="100px" class="label">供应商名称：</td>
				<td colspan="5"><span id="supplierName"></span></td>
			</tr>
			<tr>	
				<td width="100px" class="label">已付款合计：</td>
				<td class="font16" ><div id="paymentCount"></div></td>
						
				<td class="label" width="100px">申请付款总额：</td>					
				<td class="font16" width="150px">${payment.totalPayable }</td>
								
				<td class="label" width="100px">付款条件：</td>					
				<td width="150px">发票后&nbsp;${payment.paymentTerm }&nbsp;天</td>
														
				
				<td width="100px" class="label">付款状态：</td>
				<td class="bold">${payment.finishStatus }</td>
			</tr>										
		</table>
	</fieldset>	
	<div style="clear: both"></div>	
	<div id="DTTT_container" align="right" style="margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="addInit" >继续付款</a>
		<a class="DTTT_button DTTT_button_text" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 付款明细</legend>
		<table class="display" id="history">
			<thead>
				<tr> 
					<th width="30px">No</th>				
					<th width="100px">付款单编号</th>				
					<th width="100px">付款日期</th>
					<th width="100px">付款人</th>					
					<th width="100px">币种</th>					
					<th width="100px" >付款方式</th>
					<th width="100px">付款金额</th>	
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>合计：</td>
					<td><span id="paymentCount2" style="font-weight: bold;"></span></td>
					<td></td>
					<td></td>
				</tr>
			</tfoot>										
		</table>
	</fieldset>
	<fieldset>
		<span class="tablename" style="">  发票信息</span>		
		<table class="display" id="invoice">
			<thead>
				<tr> 
					<th width="30px">No</th>				
					<th width="100px">发票日期</th>				
					<th width="150px">发票金额</th>
					<th width="100px">发票类型</th>					
					<th width="180px">发票编号</th>		
					<th width="80px">操作</th>
					<th></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td><span id="invoiceCnt"></span></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tfoot>
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
					<th width="50px">入库状态</th>
					<th width="60px">合同金额</th>
					<th width="50px">增减项</th>
					<th width="50px">扣款方式</th>
					<th width="60px">应付款</th>
					<th width="50px">退税率</th>
					<th width="50px">税</th>
					<th width="60px">价</th>
					<th>操作</th>
				</tr>
			</thead>
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
						<td class="td-right"><span id="payment${status.index }">${list.payable }</span></td>
						<td class="td-center">
							<form:hidden path="paymentList[${status.index }].contractid"  value="${list.contractId }" />
							<form:hidden path="paymentList[${status.index }].payable"  value="${list.total }" />
					</tr>
					<script type="text/javascript">
						//var contract = currencyToFloat('${list.totalPrice }');
						var chargeback = currencyToFloat('${list.chargeback }');
						//var payment = floatToCurrency( contract + chargeback );
						var index = ${status.index }
						//alert('payment--chargeback:'+chargeback+'---'+payment)
						//$('#payment'+index).html(payment);
						$('#chargeback'+index).html(floatToCurrency(chargeback));
					</script>
				</c:forEach>
			</tbody>
			
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
	 -->
	<fieldset>
		<legend>收据清单</legend>
		<div class="list">
			<div class="" id="subidDiv" style="min-height: 300px;">
				<table id="productPhoto" class="phototable">
					<tbody><tr class="photo"><td></td><td></td></tr></tbody>
				</table>
			</div>
		</div>	
	</fieldset>
	
</form:form>

</div>
</div>
</body>

</html>
