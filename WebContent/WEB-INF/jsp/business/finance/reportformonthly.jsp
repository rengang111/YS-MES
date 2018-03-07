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

		var key1 = $("#keyword1").val();
		var key2 = $("#keyword2").val();
		

		var actionUrl = "${ctx}/business/financereport?methodtype=inventoryReportSearch";
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		//actionUrl = actionUrl + "&key1=" + key1;
		//actionUrl = actionUrl + "&key2=" + key2;
		
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
	         	"aaSorting": [[ 1, "ASC" ]],
				//"scrollY":scrollHeight,
				//"scrollCollapse":true,
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
					{"data": "sortDate", "defaultContent" : '', "className" : 'td-center'},//时间
					{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},//
					{"data": "materialName", "defaultContent" : ''},//
					{"data": "receiptId", "defaultContent" : '', "className" : 'td-left'},
					{"data": "YSId", "defaultContent" : ''},//
					{"data": "quantity", "defaultContent" : '', "className" : 'td-right'},// 
					{"data": "price", "defaultContent" : '0', "className" : 'td-right'},//
					{"data": "price", "defaultContent" : '0', "className" : 'td-right'},//
					{"data": "price", "defaultContent" : '0', "className" : 'td-right'},//
					{"data": "price", "defaultContent" : '0', "className" : 'td-right'},//
					{"data": "price", "defaultContent" : '0', "className" : 'td-right'},//
					{"data": "price", "defaultContent" : '0', "className" : 'td-right'},//
					{"data": "price", "defaultContent" : '0', "className" : 'td-right'},//
					{"data": "price", "defaultContent" : '0', "className" : 'td-right'},//
					{"data": null, "defaultContent" : '0', "className" : 'td-right'},//
					
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
		    		{"targets":3,"render":function(data, type, row){	    			
		    			return jQuery.fixedWidth(data,45);
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			var price = currencyToFloat(row["price"]);
		    			var newPrice = float6ToCurrency( price / 1.17 );
		    			return newPrice;
		    		}},
		    		{"targets":9,"render":function(data, type, row){
		    			var price = currencyToFloat(row["price"]);
		    			var quantity = currencyToFloat(row["quantity"]);
		    			var newPrice = ( price / 1.17 );
		    			var total = floatToCurrency( newPrice * quantity )
		    			return total;
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
		

		t.on('click', 'tr', function() {

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});

	}


	$(document).ready(function() {

		//日期
		//$("#payment\\.requestdate").val(shortToday());
		$("#keyword1").datepicker({
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
		            $('#keyword1').val(year + '-' + month);
		        }
		}); 
		
		//ajax("020","true");

	    //buttonSelectedEvent();//按钮点击效果
	})	
	
	function doSearch() {	

		var key = myTrim($('#keyword1').val());
		if(key == "" ){
			$().toastmessage('showWarningToast', "请选择要查询的月份。");	
			return;
		}
		$('#monthly').val(key + '-' + '01');
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
						<input type="text" id="keyword1" name="keyword1" class=""/>
						<input type="hidden" id="monthly" name="monthly"  />
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
					<th style="width: 100px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 40px;">单位</th>
					<th style="width: 50px;">期初数量</th>
					<th style="width: 50px;">期初价格</th>
					<th style="width: 60px;">总价</th>
					<th style="width: 50px;">入库数量</th>
					<th style="width: 50px;">入库价格</th>
					<th style="width: 60px;">总价</th>
					<th style="width: 50px;">发货数量</th>
					<th style="width: 50px;">发货价格</th>
					<th style="width: 60px;">总价</th>
					<th style="width: 50px;">结存数量</th>
					<th style="width: 50px;">结存价格</th>
					<th style="width: 60px;">总价</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
