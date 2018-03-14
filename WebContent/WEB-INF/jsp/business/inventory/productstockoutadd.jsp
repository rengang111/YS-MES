<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>库存管理-成品出库登记</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var shortYear = ""; 
	
	function ajax() {

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
			//"scrollY"    : "160px",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : '<"clear">rt',		
			"columns" : [
			        	{"className":"dt-body-center"
					}, {"className":"td-center"
					}, {"className":"td-center"
					}, {"className":"td-center"
					}, {"className":"td-center"
					}, {"className":"td-center"	
					}
				],
	
		}).draw();
		
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
		$("#stockout\\.checkoutdate").val(shortToday());
		$("#stockout\\.checkoutdate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		ajax();//产品
		ajaxHistory();//出库记录
		
		
		$("#goBack").click(
				function() {
					var url = "${ctx}/business/stockout?methodtype=productSearchMainInit";
					location.href = url;		
				});
		
		$("#insert").click(
				function() {

			var quantity = 	$('#stockDetail\\.quantity').val();
			quantity = currencyToFloat(quantity);
			if(quantity <= 0 ){
				
				$().toastmessage('showWarningToast', "出库数量必须大于零。");
				return;
			}
			$('#formModel').attr("action", "${ctx}/business/stockout?methodtype=productStockoutAdd");
			$('#formModel').submit();
		});
				
		foucsInit();
		
		//计算剩余出库数量
		var orderQty = currencyToFloat('${stockin.stockinQty }');
		var stockoutCnt = currencyToFloat('${stockin.stockoutQty }');
		var stockout = orderQty - stockoutCnt;
		$('#stockDetail\\.quantity').val(floatToCurrency(stockout));

	});
	
	function doEdit(YSId,receiptId) {
		var url = "${ctx}/business/storage?methodtype=editProduct"
				+"&YSId="+YSId
				+"&receiptId="+receiptId;
		location.href = url;
	}
	
		
	function ajaxHistory() {

		var YSId = '${order.YSId }';
		var materialId = '${order.materialId }';
		var actionUrl = "${ctx}/business/stockout?methodtype=getProductStockoutDetail";
		actionUrl = actionUrl + "&YSId="+YSId+"&materialId="+materialId;
		
		var t = $('#history').DataTable({
			
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"autoWidth"	:false,
			"searching" : false,
			"retrieve"  : true,
			"dom"		: '<"clear">rt',
			"sAjaxSource" : actionUrl,
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
						 alert(errorThrown)
		             }
				})
			},	
 			"columns" : [
		        	{"data": null,"className":"dt-body-center"
				}, {"data": "stockOutId","className":"td-left"
				}, {"data": "checkOutDate","className":"td-center"
				}, {"data": "quantity","className":"td-right"
				}, {"data": null,"className":"dt-body-center"
				}
			],
			"columnDefs":[
	    		{"targets":4,"render":function(data, type, row){
					return "<a href=\"###\" onClick=\"doEdit('"  + row["YSId"] + "','"  + row["receiptId"] + "')\">"+"编辑"+"</a>";
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

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<form:hidden path="stockout.subid" />
	<form:hidden path="stockout.ysid"  value="${order.YSId }"/>
	
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="150px">&nbsp;${order.YSId }</td>
							
				<td width="100px" class="label">成品编码：</td>
				<td width="150px">&nbsp;${order.materialId }</td>							
				<td width="100px" class="label">成品名称：</td>
				<td>&nbsp;${order.materialName }</td>
			</tr>
			<tr> 
				<td class="label">出库单编号：</td>
				<td><form:input path="stockout.stockoutid" class="read-only"  value="（保存后自动生成）"/></td>			
				<td class="label">出库时间：</td>					
				<td>
					<form:input path="stockout.checkoutdate" class="read-only" /></td>
							
				<td class="label">仓管员：</td>
				<td>
					<form:input path="stockout.keepuser" class="short read-only" value="${userName }" /></td>							

			</tr>
										
		</table>
	</fieldset>
		
	<fieldset class="action" style="text-align: right;margin-top:-20px">
		<button type="button" id="insert" class="DTTT_button">确认出库</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>	
	<fieldset style="margin-top: -40px;">
		<legend> 成品信息</legend>
		<div class="list">
			<table class="display" id="example">	
				<thead>		
					<tr>
						<th style="width:1px">No</th>
						<th style="width:80px">订单数量</th>
						<th style="width:60px">入库数量</th>
						<th style="width:60px">库位编号</th>
						<th style="width:80px">已出库数量</th>
						<th style="width:80px">本次出库数</th>
					</tr>
				</thead>
				<tbody>			
					<tr>
						<td>1</td>
						<td>${stockin.orderQty }
							<form:hidden path="stockDetail.materialid" value="${stockin.materialId }"/></td>
						<td>${stockin.stockinQty }</td>
						<td>${stockin.areaNumber }</td>
						<td>${stockin.stockoutQty }</td>
						<td><form:input path="stockDetail.quantity"  value="${stockin.orderQty }" class="num short quantity" /></td>
					</tr>
				
				</tbody>										
			</table>
		</div>
	</fieldset>
	<fieldset>
	<legend> 出库记录</legend>
	<div class="list">
		<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;margin: 5px 0px -10px 10px;">
				<a class="DTTT_button" id="all" onclick="doPrint();return false;">打印出库单</a>
		</div>
		<table class="display" id="history" >	
			<thead>		
				<tr>
					<th style="width:15px">No</th>
					<th style="width:120px">出库单号</th>
					<th style="width:80px">出库时间</th>
					<th style="width:80px">出库数量</th>
					<th style="width:30px"></th>	
				</tr>
			</thead>		
									
		</table>
	</div>
</fieldset>
	

</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	openLayer(url);

};

function showYS(YSId) {
	var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;

	//var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
	openLayer(url);

};

function doPrint() {
	var YSId = '${order.YSId }';
	var materialId = '${order.materialId }';
	var actionUrl = "${ctx}/business/storage?methodtype=printProductReceipt";
	actionUrl = actionUrl + "&YSId="+YSId+"&materialId="+materialId;
		
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '520px' ], 
		scrollbar : false,
		title : false,
		content : actionUrl,
		cancel: function(index){			
		}    
	});		

};
</script>

</html>
