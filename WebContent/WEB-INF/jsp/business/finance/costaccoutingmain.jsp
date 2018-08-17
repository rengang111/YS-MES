<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<style>
body{
   font-size:10px;
}
</style>
<title>财务核算一览页面</title>
<script type="text/javascript">


	function ajax(orderNature,monthday,sessionFlag,status) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/financereport?methodtype=costAccountingSsearch&sessionFlag="+sessionFlag;
		url = url + "&monthday=" +monthday;
		url = url + "&statusFlag=" +status;
		
		var t = $('#TMaterial').DataTable({
				"paging": false,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//每页显示条数设置
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
	         	"bAutoWidth":false,
				"ordering"	:true,
				"searching" : false,
				"pagingType" : "full_numbers",      
				"sAjaxSource" : url,
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
							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);
							
							if(data["keyword1"] != '' || data["keyword2"] != ''){
								var collection = $(".box");
							    $.each(collection, function () {
							    	$(this).removeClass("end");
							    });
							    var collection = $(".box2");
							    $.each(collection, function () {
							    	$(this).removeClass("end");
							    });
							}
							
							//核算成本合计，利润合计
							costCountByCurrency();
							
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
					{"data": "YSId", "defaultContent" : ''},
					{"data": "productId", "defaultContent" : '', "className" : 'td-left'},
					{"data": "productName", "defaultContent" : ''},//3
					{"data": "customerShort", "defaultContent" : '', "className" : 'td-center'},
					{"data": "quantity", "defaultContent" : '', "className" : 'td-right'},//5
					{"data": "stockinQty", "defaultContent" : '', "className" : 'td-right'},
					{"data": "orderTotalPrice", "className" : 'td-right'},//7
					{"data": "checkInDate", "defaultContent" : '0', "className" : 'td-right'},
					{"data": "cost", "className" : 'td-right'},//9
					{"data": "currency", "className" : 'td-right'},//10
					{"data": "profit", "className" : 'td-right'},//11
					{"data": null, "className" : 'td-right',"defaultContent" : ''},//12
					{"data": "team", "defaultContent" : ''},
					{"data": null, "className" : 'td-center'},//14
				
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"];
                    }},
		    		{"targets":1,"render":function(data, type, row){

		    			var accountingDate = currencyToFloat(row["accountingDate"]);
		    			var rtn="";
	    				if(accountingDate != '' ){
			    			rtn= "<a href=\"###\" onClick=\"doShow('"+ row["YSId"] + "')\">"+row["YSId"]+"</a>";
    					
	    				}else{
			    			rtn= "<a href=\"###\" onClick=\"doCreate('"+ row["YSId"] + "')\">"+row["YSId"]+"</a>";
	    				}
	    				return rtn;
		    			
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["productName"];
		    			name = jQuery.fixedWidth(name,24);//true:两边截取,左边从汉字开始
		    			
		    			return name;
		    		}},
		    		{"targets":9,"render":function(data, type, row){
		    			var index = row["rownum"];
		    			var cost = row["cost"];
		    			var costA = row["costAcounting"]; 
		    			var currency = row["currency"];
		    			var rate = row["exchange_rate"];
		    			var lastPrice = currencyToFloat(row["lastPrice"]);
		    			var quantity = currencyToFloat(row["quantity"]);
		    			if(cost == 0)
		    				cost = floatToCurrency(costA);
		    			if(costA == 0){
		    				cost = floatToCurrency(lastPrice * quantity);//装配品
		    			}
		    				
		    			var text = '<input type="hidden" name="cost" id="cost'+index + '" value="'+cost+'" >';
		    			text = text + '<input type="hidden" name="currency" id="currency'+index + '" value="'+currency+'" >';
		    			text = text + '<input type="hidden" name="rate" id="rate'+index + '" value="'+rate+'" >';
		    			return text + cost;
		    		}},
		    		{"targets":11,"render":function(data, type, row){
		    			var index = row["rownum"];
		    			var orderType = row["orderType"];
		    			var cost = currencyToFloat(row["cost"]);
		    			var costA = currencyToFloat(row["costAcounting"]); 
		    			var currency = row["currency"];
		    			var profit = currencyToFloat(row["profit"]);
		    			var orderCnt = currencyToFloat(row["orderTotalPrice"]);
		    			var rate = currencyToFloat(row["exchange_rate"]);
		    			var lastPrice = currencyToFloat(row["lastPrice"]);
		    			var quantity = currencyToFloat(row["quantity"]);
		    			if(cost == 0){
		    				if(orderType == '020'){
			    				cost = lastPrice * quantity;//装配品的成本
		    				}else{
		    					cost = costA;//未领料的从BOM计算
		    				}
		    			}
    					profit = floatToCurrency(rate * orderCnt - cost);//利润
    					
		    			return profit;
		    			//return rate +" -- "+ orderCnt  +" -- "+  cost;
		    		}},
		    		{"targets":12,"render":function(data, type, row){
		    			var index = row["rownum"];
		    			var orderType = row["orderType"];
		    			var cost = currencyToFloat(row["cost"]);
		    			var costA = currencyToFloat(row["costAcounting"]); 
		    			var rate = row["exchange_rate"];
		    			var profit = currencyToFloat(row["profit"]);
		    			var orderCnt = currencyToFloat(row["orderTotalPrice"]);
		    			var profitRate = row["profitRate"];
		    			var lastPrice = currencyToFloat(row["lastPrice"]);
		    			var quantity = currencyToFloat(row["quantity"]);
		    			/*
		    			if(profitRate <=0){
		    				if(costA > 0){
			    				profitRate = floatToCurrency((rate * orderCnt - costA) / costA * 100);
	    					
		    				}else{
		    					profitRate = 0;
		    				}
		    			}    			
		    			*/
		    			if(cost == 0){
		    				if(orderType == '020'){
			    				cost = lastPrice * quantity;//装配品的成本
		    				}else{
		    					cost = costA;//未领料的从BOM计算
		    				}

		    				if(cost == 0){
		    					profitRate = 0;
		    				}else{
				    			profitRate = floatToCurrency((rate * orderCnt - cost) / cost * 100);//利润
		    				}
		    			}
    					
		    			return profitRate+"%";
		    			//return rate +" -- "+ orderCnt +" -- "+ cost;
		    		}},
		    		{"targets":13,"render":function(data, type, row){
		    			return jQuery.fixedWidth(row["team"],8);
		    		}},
		    		{"targets":14,"render":function(data, type, row){
		    			var stockinQty = currencyToFloat(row["stockinQty"]);
		    			var orderQty = currencyToFloat(row["quantity"]);
		    			var accountingDate = currencyToFloat(row["accountingDate"]);
		    			var rtn="";
		    			if(accountingDate != '' ){
		    				rtn="已核算";
		    			}else{
		    				if(stockinQty >= orderQty){
			    				rtn="待核算";
			    					
			    			}else{
			    				rtn = "待入库";
			    			}
		    			}
		    			
		    			return rtn;
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : [10]
					}
	         	],
	         	
	         	
			});


	}

	
	function initEvent(){

		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		ajax("",monthday,"true",statusFlag);
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}

	$(document).ready(function() {

		initEvent();
	
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样2
		
		var month = "";
		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		if(monthday == '' || monthday == null){
			month = getMonth();
		}else{
			month = monthday.split("-")[1];
		}
	
		$('#defutBtn'+month).removeClass("start").addClass("end");
		$('#defutBtn'+statusFlag).removeClass("start").addClass("end");
	})	
	
	function doSearch() {	

		ajax('','','false','');
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	}
	
	function doCreate(YSId) {
		

		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();

		var actionUrl = "${ctx}/business/financereport?methodtype=costAccountingAdd";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&monthday="+monthday;
		actionUrl = actionUrl +"&statusFlag="+statusFlag;

		location.href = actionUrl;
	}
	
	//订单月份
	function doSearchCustomer(monthday){
		$('#keyword1').val('');
		$('#keyword2').val('');
		var year = getYear();
		if(monthday == '12'){
			year = year - 1;//去年的12月
		}
		var todaytmp = year +"-"+monthday+"-"+"01";
		$('#monthday').val(todaytmp);
		
		var statusFlag = $('#statusFlag').val();
		ajax('',todaytmp,'false',statusFlag);
	}
	
	//部分入库
	function doSearchCustomer3(status){
		$('#keyword1').val('');
		$('#keyword2').val('');
			
		ajax('','','false',status);
	}

	//订单状态
	function doSearchCustomer2(status){
		var monthday = $('#monthday').val();

		$('#statusFlag').val(status);
		ajax('',monthday,'false',status);
	}

	//月度统计
	function doShowContract(contractId) {

		var url = '${ctx}/business/financereport?methodtype=monthlyStatistics&contractId=' + contractId;
		
		callProductDesignView("月度统计",url);
	}	
	
	
	function doShow(YSId) {

		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		var url = '${ctx}/business/financereport?methodtype=costBomDetailView'
				+"&monthday="+monthday
				+"&statusFlag="+statusFlag
				+"&YSId="+YSId;

		location.href = url;
	}


	function reload() {
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		
		return true;
	}
	
	function costCountByCurrency(){

		var rmb = 0;
		var rmbCount = 0;
		var us = 0;
		var eur = 0;
		var gbp = 0;
		var jpy = 0;
		var costSum = 0;
		$('#TMaterial tbody tr').each (function (){

			var sales        = $(this).find("td").eq(7).text();//销售额
			var currencyType = $(this).find("td").eq(9).find("input[name=currency]").val();//币种
			var cost         = $(this).find("td").eq(9).find("input[name=cost]").val();//核算成本
			var rate         = $(this).find("td").eq(9).find("input[name=rate]").val();//汇率
			
			sales = currencyToFloat(sales);
			cost = currencyToFloat(cost);
			rmbCount = rmbCount + rate * sales;//人民币总计
			costSum = costSum + cost;//核算成本合计
			
			if(currencyType == 'RMB'){
				rmb = rmb + sales;//
			}else if(currencyType == 'USD'){
				us = us + sales;//
			}else if(currencyType == 'EURO'){
				eur = eur + sales;//
			}else if(currencyType == 'GBP'){
				gbp = gbp + sales;//
			}else if(currencyType == 'JPY'){
				jpy = jpy + sales;//
			}
						
		});	
		//alert("rmb:"+rmb)

		var textView="";
		if(rmb > 0){
			textView = "销售额：¥"+floatToCurrency(rmb);
		}
		if(us > 0){			
			textView = textView + "; $"+floatToCurrency(us);			
		}
		if(eur > 0){
			textView = textView + "; €"+floatToCurrency(eur);			
		}
		if(gbp > 0){
			textView = textView + "; ￡"+floatToCurrency(gbp);			
		}
		if(jpy > 0){
			textView = textView + "; ¥"+floatToCurrency(jpy);			
		}
		
		textView = textView +"&nbsp;&nbsp;RMB销售总计：¥ " + floatToCurrency(rmbCount);
		
		textView = textView + "&nbsp;&nbsp;成本总计：¥ "+ floatToCurrency(costSum);
		
		var profit = rmbCount - costSum;//利润
		var profitm = profit / costSum * 100;//利润率
		
		textView = textView + "&nbsp;&nbsp;利润总计：¥ "+ floatToCurrency(profit)+"（"+floatToCurrency(profitm)+"%）";
		
		$('#costCount').html(textView);
		
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<input type="hidden" id="monthday"  value="${monthday }"/>
			<input type="hidden" id="statusFlag"  value="${statusFlag }"/>
			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label" style="width:100px">关键字1：</td>
					<td class="condition">
						<input type="text" id="keyword1" name="keyword1" class="middle"/></td>
					<td class="label" style="width:100px">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle"/></td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();"/>查询</td>
					<td style="vertical-align: bottom;width: 150px;">
					</td> 
					<td width="10%"></td> 
				</tr>
				<tr style="height: 40px;">
					<td width="10%"></td> 
					<td class="label" style="width:100px">快捷查询：</td>
					
					<td colspan="6">
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
				 		<a id="defutBtn14" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer4('050');">
						<span>月度统计</span></a> 
				 		&nbsp;&nbsp;<a id="defutBtn13" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer3('040');">
						<span>部分入库</span></a> 	
					</td>
				</tr>
				
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>
	<div class="list">

		<div id="TSupplier_wrapper" class="dataTables_wrapper">
		
			<div id="DTTT_container2" style="height:40px;float: left">
			  	<a  class="DTTT_button box2" onclick="doSearchCustomer2('010');" id="defutBtn010"><span>ALL</span></a>
				<a  class="DTTT_button box2" onclick="doSearchCustomer2('020');" id="defutBtn020"><span>待审核</span></a>
				<a  class="DTTT_button box2" onclick="doSearchCustomer2('030');" id="defutBtn030"><span>已审核</span></a>
			</div>
			
			<div id="DTTT_container2" style="height:40px;float: right">
				<span id="costCount" style="font-size: 12px;font-weight: bold;"></span>
			</div>
			 
			<div id="clear"></div>
			<table id="TMaterial" class="display" >
				<thead>						
					<tr>
						<th style="width: 10px;">No</th>
						<th style="width: 60px;">耀升编号</th>
						<th style="width: 80px;">产品编号</th>
						<th>产品名称</th>
						<th style="width: 30px;">客户<br/>简称</th>
						<th style="width: 40px;">订单<br/>数量</th>
						<th style="width: 40px;">入库<br/>累计</th>
						<th style="width: 50px;">销售金额</th>
						<th style="width: 50px;">入库日期</th>
						<th style="width: 40px;">核算成本</th>
						<th style="width: 40px;">退税</th>
						<th style="width: 40px;">利润</th>
						<th style="width: 30px;">利润率</th>
						<th style="width: 30px;">业务组</th>
						<th style="width: 20px;">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>
