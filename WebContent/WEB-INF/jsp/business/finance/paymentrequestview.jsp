<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应付款-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	function ajax(scrollHeight) {

		var taskId = $('#task\\.taskid').val();
		var YSId= $('#task\\.collectysid').val();
		var actionUrl = "${ctx}/business/requisitionzz?methodtype=getRawMaterialList";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&taskId="+taskId;
		
		scrollHeight =$(document).height() - 250; 
		
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
				{"className" : 'td-center'},//
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
	
	function materialzzAjax() {

		var makeType = $('#makeType').val();
		var taskId = $('#task\\.taskid').val();
		var YSId= $('#task\\.collectysid').val();
		var actionUrl = "${ctx}/business/requisitionzz?methodtype=getMaterialZZList";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&taskId="+taskId;
		actionUrl = actionUrl +"&makeType="+makeType;
				
		var t = $('#payment').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	       "autoWidth":false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"aaSorting": [[ 1, "DESC" ]],
			"sAjaxSource" : actionUrl,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				//var param = {};
				//var formData = $("#condition").serializeArray();
				//formData.forEach(function(e) {
				//	aoData.push({"name":e.name, "value":e.value});
				//});

				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					//"data" : JSON.stringify(aoData),
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
		        	{"data": null,"className":"dt-body-center"//0
				}, {"data": "YSId","className":"td-left", "defaultContent" : ''//5
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2
				}, {"data": "unit","className":"td-center"		//3单位
				}, {"data": "manufactureQuantity","className":"td-right"//4
				}, {"data": null, "defaultContent" : ''	//6 
				}
			],
			"columnDefs":[
	    		
	    		{"targets":3,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					//var inputTxt =       '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value=""/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			
	    			return name;
                }},
				{"targets":5,"render":function(data, type, row){	    			
	    			
	    			var qty = floatToCurrency(row["manufactureQuantity"]);			
	    			return qty;				 
                }}
			]
			
		}).draw();

	
		
						
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
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/payment";
					location.href = url;		
		});

		
		$("#insert").click(
				function() {
					
			$('#formModel').attr("action", "${ctx}/business/payment?methodtype=applyInsert");
			$('#formModel').submit();
		});
		

		ajax();
		materialzzAjax();
		
		var contract = contractSum(5);
		var minis = contractSum(6);
		var payment = contractSum(7);
		$('#contractTotal').html(floatToCurrency(contract));
		$('#minisTotal').html(floatToCurrency(minis));
		$('#paymentTotal').html(floatToCurrency(payment));		
		$('#payment\\.totalpayable').val(floatToCurrency(payment));
		
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

	function doShowSupplier(supplierId) {

		var url = '${ctx}/business/supplier?methodtype=showById&openFlag=newWindow&key=' + supplierId;
		
		callProductDesignView("供应商",url);
	}
	
	function doShowStockin(contractId) {

		var url = '${ctx}/business/storage?methodtype=showStockInByContractId&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("入库单",url);
	}
	
	function doPrintContract(contractId) {
	
		var url = '${ctx}/business/requirement?methodtype=contractPrint';
		url = url +'&contractId='+contractId;
		//alert(url)		
		callProductDesignView("打印合同",url);	

	}
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<form:hidden path="payment.parentid"  />
	<form:hidden path="payment.subid"  />
	<form:hidden path="payment.recordid"  value="${payment.recordId }"/>
	<form:hidden path="payment.paymentid" value="${payment.paymentId }"/>
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
		<a class="DTTT_button DTTT_button_text" id="insert" >修改</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<!-- 
	<fieldset>
		<legend> 付款信息</legend>
		<table id="payment" class="form"  style="width:80%">
			<thead>				
				<tr>			
					<th width="30px">No</th>
					<th width="120px">付款日期</th>
					<th width="120px">本次付款金额</th>
					<th width="120px">应付款总额</th>
					<th width="120px">剩余未付款</th>
					<th>备注</th>
				</tr>
			</thead>
		</table>
	</fieldset>
	 -->
	<fieldset>
		<legend> 合同明细</legend>
		<div class="list">
		<table id="example" class="display" >
			<thead>				
				<tr>
					<th width="30px">No</th>
					<th width="120px">合同编号</th>
					<th width="120px">入库单号</th>
					<th width="80px">耀升编号</th>
					<th width="80px">约定付款日</th>
					<th width="100px">合同金额</th>
					<th width="100px">增减项总额</th>
					<th width="100px">应付款金额</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var='list' items='${contract}' varStatus='status'>	
					<tr>
						<td>${status.index+1 }</td>
						<td><a href="###" onClick="doShowContract('${list.contractId }')">${list.contractId }</a></td>
						<td><a href="###" onClick="doShowStockin('${list.contractId }')">${list.receiptId }</a></td>
						<td>${list.YSId }</td>
						<td>${list.agreementDate }</td>
						<td>${list.totalPrice }</td>
						<td>0</td>
						<td>${list.totalPrice }</td>
						<td class="td-center">
							<a href="###" onClick="doPrintContract('${list.contractId }')">打印合同</a>&nbsp;&nbsp;
							<a href="###" onClick="doShowStockin('${list.contractId }')">打印入库单</a>
						</td>
							<form:hidden path="paymentList[${status.index }].contractid"  value="${list.contractId }" />
							<form:hidden path="paymentList[${status.index }].payable"  value="${list.total }" />
					</tr>
				</c:forEach>
			</tbody>
			<!--  -->
			<tfoot>
				<tr>
					<td></td>
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
