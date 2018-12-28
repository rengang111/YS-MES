<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>
<title>财务流水账</title>


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
		var monthday = $("#monthday").val();

		var actionUrl = "${ctx}/business/financereport?methodtype=reportForDaybookSsearch";
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		actionUrl = actionUrl + "&monthday=" + monthday;
		
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
					{"data": null, "defaultContent" : '0', "className" : 'td-right'},//
					{"data": "depotId", "defaultContent" : '', "className" : 'td-left'},//
					
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
		    		{"targets":7,"render":function(data, type, row){
		    			var price = currencyToFloat(row["price"]);
		    			var newPrice = float6ToCurrency( price / 1.17 );
		    			return price +"<br />"+newPrice;
		    		}},
		    		{"targets":8,"render":function(data, type, row){
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

		var year = getYear();
		var month = getMonth();
		
		if(month<10){
			  return '0'+ month;
		}
		//if(month == '12'){
		//	year = year - 1;//去年的12月
		//}
		var monthday = year + '-' + month + '-01';

		$('#year').val(year);
		$('#monthday').val(monthday);

		$('#defutBtn'+month).removeClass("start").addClass("end");
		buttonSelectedEvent();//按钮选择式样
		
		ajax("","false");

		
		/*
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
		*/
		
	})	
	
	//订单月份
	function doSearchCustomer(monthday){
		var year = $('#year').val();

		var todaytmp = year + '-'+ monthday + '-01';//默认当月初：2018-12-01
		$('#monthday').val(todaytmp);

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
		ajax(type,"false");
	}
	
	function downloadExcel() {
		
		var recordsTotal = $('#recordsTotal').val();
		if(recordsTotal <=0){
			$().toastmessage('showWarningToast', "没有可以导出的数据。");	
			return;
		}
		var monthday = $('#monthday').val();
		var url = '${ctx}/business/financereport?methodtype=downloadExcel'
				 + "&monthly=" + monthday;
		
		url =encodeURI(encodeURI(url));//中文两次转码

		location.href = url;
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form:form modelAttribute="formModel" method="POST"
			id="formModel" name="formModel"  autocomplete="off">
		
			<input type="hidden" id="materialId" name="materialId"  value="${materialId }"/>
			<input type="hidden" id="monthday"  value="${monthday }"/>
			<input type="hidden" id="recordsTotal" name="recordsTotal"  />
					
			<table style="height: 40px;">
				<tr>
					<td width="10%"></td> 
					<td><form:select path="year" style="width: 100px;">
						<form:options items="${year}" itemValue="key" itemLabel="value" />
						</form:select></td>
					
					<td colspan="6">&nbsp;&nbsp;
						<a id="defutBtn12" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('12');">
						<span>12月</span></a>
						<a id="defutBtn01"  style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('01');">
						<span>1月</span></a>
						<a id="defutBtn02" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('02');">
						<span>2月</span></a>
						<a id="defutBtn03" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('03');">
						<span>3月</span></a>
						<a id="defutBtn04" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('04');">
						<span>4月</span></a>
						<a id="defutBtn05" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('05');">
						<span>5月</span></a>
						<a id="defutBtn06" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('06');">
						<span>6月</span></a>
						<a id="defutBtn07" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('07');">
						<span>7月</span></a>
						<a id="defutBtn08" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('08');">
						<span>8月</span></a>
						<a id="defutBtn09" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('09');">
						<span>9月</span></a>
						<a id="defutBtn10" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('10');">
						<span>10月</span></a>
						<a id="defutBtn11" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('11');">
						<span>11月</span></a>

					</td>
				</tr>				
			</table>
		</form:form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
		<div id="DTTT_container" align="right" style="height:40px;width:50%;float: right">
			<a class="DTTT_button DTTT_button_text" onclick="downloadExcel();">EXCEL导出</a>
		</div>
	
		<table id="TMaterial" class="display">
			<thead>						
				<tr>
					<th style="width: 30px;">No</th>
					<th style="width: 70px;">日期</th>
					<th style="width: 100px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 50px;">出/入库<br />单号</th>
					<th style="width: 80px;">耀升<br />/合同编号</th>
					<th style="width: 60px;">数量</th>
					<th style="width: 60px;">单价<br />不含税价</th>
					<th style="width: 60px;">金额</th>
					<th style="width: 60px;">仓库位置</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
