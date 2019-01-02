<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>
<title>物料别的流水账(带期初值)</title>


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
		var materialId = $("#materialId").val();

		var actionUrl = "${ctx}/business/financereport?methodtype=reportForDaybookByMaterialIdSsearch";
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		actionUrl = actionUrl + "&materialId=" + materialId;
		actionUrl = actionUrl + "&monthday=" + monthday;

		var t = $('#TMaterial').DataTable({
				"paging": false,
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

							var record = data['beginingRecord'];
							
							if(record > 0){
								var start = data['begining'][0]['startValue'];
								var end   = data['begining'][0]['endValue'];
							}else{
								var start = '***';
								var end = '***';
							}	
							var startDate = data['startDate'];
							var endDate = data['endDate'];					

							$('#start').text(start);
							$('#end').text(end);
							$('#startDate').text(startDate);
							$('#endDate').text(endDate);
							
							fnCallback(data);
							sumFn(start);
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
					{"data": "receiptId", "defaultContent" : '', "className" : 'td-left'},//出/入库<br />编号
					{"data": "YSId", "defaultContent" : ''},//耀升<br />/合同编号
					{"data": "quantity", "defaultContent" : '', "className" : 'td-right'},// 入出库<br />数量
					{"data": "dataFlag", "defaultContent" : '0', "className" : 'td-right'},//当前余额
					
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
		    		{"targets":4,"render":function(data, type, row){
		    			var dataFlag = row["dataFlag"];
		    			var quantity = currencyToFloat(row["quantity"]);
		    			if(dataFlag == 'O'){
		    				quantity = quantity * (-1);//出库为负数
		    				return '<span class="text-red">'+floatToCurrency(quantity)+'</span>';
		    			}else{
		    				return '<span class="">'+floatToCurrency(quantity)+'</span>';
		    			}
		    			return floatToCurrency(quantity) ;	 
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
			//month = '0'+ month;
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
	
	function sumFn(start){
		
		var storage = currencyToFloat( start );
		$('#TMaterial tbody tr').each (function (){
			
			var quantity = $(this).find("td").eq(4).text();//入出库数量	
			var dataFlag = $(this).find("td").eq(5).text();//入出库标识
			quantity = currencyToFloat(quantity);
			
			//if(dataFlag == 'O'){
			//	quantity = quantity * (-1);//出库为负数
			//}
			
			storage = storage + quantity;
			
			var fstorage = floatToCurrency(storage);


			//alert("计划用量+已领量+库存:"+fjihua+"---"+fyiling+"---"+fkucun)

			
			$(this).find("td").eq(5).text(fstorage)
						
		});	
				
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
			<input type="hidden" id="monthday" name="monthday"  />
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
			
			<table style="width: 100%;">
				<tr>
					<td class="label" width="80px">物料编号：</td>
					<td width="100px">${storage.materialId }</td>
					
					<td class="label" width="80px">物料名称：</td>
					<td colspan="5">${storage.materialName }</td>
					
				</tr>
				<tr>
					<td class="label" width="80px">期初日期：</td>
					<td width="100px"><span id="startDate"></span></td>
					<td class="label" width="80px">期初库存：</td>					
					<td width="100px"><span id="start"></span></td>
					
					<td class="label" width="80px">期末日期：</td>
					<td width="100px"><span id="endDate"></span></td>
					<td class="label" width="80px">期末库存：</td>					
					<td><span id="end"></span></td>
				 
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
					<th style="width: 70px;">发生日期</th>
					<th style="width: 50px;">出/入库编号</th>
					<th style="width: 80px;">耀升编号/合同编号</th>
					<th style="width: 60px;">入出库数量</th>
					<th style="width: 60px;">当前余额</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
