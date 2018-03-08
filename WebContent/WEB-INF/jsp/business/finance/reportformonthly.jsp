<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>
<title>收发存总表</title>
<style type="text/css">
.ui-datepicker-calendar {display: none;}
</style>
<script type="text/javascript">

	function ajax(status,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		

		var actionUrl = "${ctx}/business/financereport?methodtype=inventoryReportSearch";
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;

		var scrollHeight = $(document).height() - 170;
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"autoWidth"	:false,
				"pagingType" : "full_numbers",
				"scrollY":scrollHeight,
		        "sScrollX": true,
				"scrollCollapse":false,
		       	"fixedColumns":   { leftColumns: 2 },
				"retrieve" : true,
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
							$('#recordsTotal').val(data["recordsTotal"]);
							fnCallback(data);
						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null, "defaultContent" : '',"className" : 'td-center'},
					{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},//
					{"data": "materialName", "defaultContent" : '',"className" : 'td-left'},//
					{"data": "unit", "defaultContent" : '', "className" : 'td-center'},
					{"data": "beginningQty", "defaultContent" : '0', "className" : 'td-right td-bg-f2'},//4期初值
					{"data": "beginningPrice", "defaultContent" : '0', "className" : 'td-right td-bg-f2'},// 5
					{"data": null, "defaultContent" : '0', "className" : 'td-right td-bg-f2'},//6
					{"data": "stockinQty", "defaultContent" : '0', "className" : 'td-right'},//7
					{"data": "stockinPrice", "defaultContent" : '0', "className" : 'td-right'},//8单价
					{"data": "stockinAmount", "defaultContent" : '0', "className" : 'td-right'},//9
					{"data": "stockoutQty", "defaultContent" : '0', "className" : 'td-right td-bg-f2'},//10
					{"data": "stockoutPrice", "defaultContent" : '0', "className" : 'td-right td-bg-f2'},//11单价
					{"data": "stockoutAmount", "defaultContent" : '0', "className" : 'td-right td-bg-f2'},//12
					{"data": null, "defaultContent" : '0', "className" : 'td-right'},//13
					{"data": "MAPrice", "defaultContent" : '0', "className" : 'td-right'},//14单价
					{"data": null, "defaultContent" : '', "className" : 'td-right'},//	15				
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"] ;				    			 
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var  rtn= "<a href=\"###\" onClick=\"showHistory('"+ row["YSId"] + "','"+ row["stockOutId"] + "')\">"+row["stockOutId"]+"</a>";
		    			if(data == ""){
		    				rtn= "<a href=\"###\" onClick=\"doCreate('"+ row["YSId"] + "','"+ row["requisitionId"] + "')\">"+"（待出库）"+"</a>";
		    			
		    			}
		    			return data;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			return jQuery.fixedWidth(data,40);	    			 
                    }},
		    		{"targets":6,"render":function(data, type, row){//期初总价
		    			var quantity = currencyToFloat(row["beginningQty"]);//期初
		    			var price = currencyToFloat(row["MAPrice"]);
		    			var total = quantity * price;
		    			
		    			return floatToCurrency(total);
		    		}},
		    		{"targets":8,"render":function(data, type, row){//入库单价
		    			if(data == 0 )
		    				data = '0';
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":11,"render":function(data, type, row){//发货单价
		    			if(data == 0 )
		    				data = currencyToFloat( row["MAPrice"] );
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":12,"render":function(data, type, row){//发货总价
		    			var outprice = currencyToFloat( row["stockoutPrice"] );
		    			var maprice = currencyToFloat( row["MAPrice"] )
		    			var stockout = currencyToFloat(row["stockoutQty"]);
		    			if(outprice == 0 )
		    				outprice = maprice;
		    			return floatToCurrency( outprice * stockout );
		    		}},
		    		{"targets":13,"render":function(data, type, row){//结存数量
		    			var begin = currencyToFloat(row["beginningQty"]);//期初
		    			var stockin = currencyToFloat(row["stockinQty"]);
		    			var stockout = currencyToFloat(row["stockoutQty"]);
		    			var quantity =  begin + stockin - stockout;
		    			
		    			return floatToCurrency(quantity);
		    		}},
		    		{"targets":15,"render":function(data, type, row){//结存总价
		    			var begin = currencyToFloat(row["beginningQty"]);//期初
		    			var stockin = currencyToFloat(row["stockinQty"]);
		    			var stockout = currencyToFloat(row["stockoutQty"]);
		    			var price = currencyToFloat(row["MAPrice"]);
		    			var quantity =  begin + stockin - stockout;
		    			var total = quantity * price;
		    			
		    			return floatToCurrency(total);;
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : []
					}
	         	]
			}
		);
		
/*
		t.on('click', 'tr', function() {

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		*/

	}


	$(document).ready(function() {

		//日期
		//$("#payment\\.requestdate").val(shortToday());
		$("#monthly").datepicker({
			language: "zh-CN",
			 changeMonth: true,
		        changeYear: true,
		        dateFormat: 'yy-mm',
		        showButtonPanel: true,
		        onClose: function(dateText, inst) {
		            var month = $("#ui-datepicker-div .ui-datepicker-month option:selected").val();
		            var year = $("#ui-datepicker-div .ui-datepicker-year option:selected").val();
		            month = parseInt(month)+1;//要对月值加1才是实际的月份
		            if(month < 10){
		                month = "0" + month;
		            }
		            $('#monthly').val(year + '-' + month);
		        }
		}); 
		
		//ajax("020","true");

	    //buttonSelectedEvent();//按钮点击效果
	})	
	
	function doSearch() {	

		var key = myTrim($('#monthly').val());
		if(key == "" ){
			$().toastmessage('showWarningToast', "请选择要查询的月份。");	
			return;
		}
		$('#monthday').val(key + '-' + '01');
		ajax("","false");
	}

	
	function showHistory(YSId,stockOutId) {
		
		var url = "${ctx}/business/stockout?methodtype=stockoutHistoryInit&YSId="+YSId;
		callProductDesignView('出库信息',url);
	}
	

	function doCreate(YSId,requisitionId) {
		
		var url =  "${ctx}/business/stockout?methodtype=addinit&YSId="+YSId+"&requisitionId="+requisitionId;
		callProductDesignView('出库信息',url);
	}
	
	function selectContractByDate(type){
		$("#keyword1").val('');
		$("#keyword1").val('');
		ajax(type,"false");
	}
	
	function downloadExcel() {
		
		var recordsTotal = $('#recordsTotal').val();
		if(recordsTotal <=0){
			$().toastmessage('showWarningToast', "没有可以导出的数据。");	
			return;
		}
		var monthly = $('#monthly').val();
		var url = '${ctx}/business/financereport?methodtype=downloadExcel'
				 + "&monthly=" + monthly;
		
		url =encodeURI(encodeURI(url));//中文两次转码

		location.href = url;
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label">月份选择：</td>
					<td>
						<input type="text" id="monthly" name="monthly" class=""/>
						<input type="hidden" id="monthday" name="monthday"  />
						<input type="hidden" id="recordsTotal" name="recordsTotal"  />
					</td>
					<!-- 
					<td class="label">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle"/>
					</td>
					 -->
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" onclick="doSearch();">查询</button>
						
					</td>
					<td>
						（财务月份：上个月26日与这个月25日）
					</td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							onclick="doCreateReport();">生成报表</button>
						
					</td>
					<td >
						<a class="DTTT_button DTTT_button_text" onclick="downloadForMonthlyReport();">EXCEL导出</a>
						
					</td>
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
	
		<table id="TMaterial" class="display" style="width: 100%;">
			<thead>						
				<tr>
					<th style="width: 30px;">No</th>
					<th style="width: 120px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 40px;word-break: keep-all">单位</th>
					<th style="width: 60px;word-break: keep-all">期初数</th>
					<th style="width: 60px;word-break: keep-all">期初价</th>
					<th style="width: 60px;word-break: keep-all">总价</th>
					<th style="width: 60px;word-break: keep-all">入库数</th>
					<th style="width: 60px;word-break: keep-all">入库价</th>
					<th style="width: 60px;word-break: keep-all">总价</th>
					<th style="width: 70px;word-break: keep-all">发货数</th>
					<th style="width: 60px;word-break: keep-all">发货价</th>
					<th style="width: 60px;word-break: keep-all">总价</th>
					<th style="width: 70px;word-break: keep-all">结存数</th>
					<th style="width: 60px;word-break: keep-all">结存价</th>
					<th style="width: 60px;word-break: keep-all">总价</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
