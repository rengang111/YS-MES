<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<style>
body{
  /* font-size:10px;*/
}
</style>
<title>财务核算一览页面</title>
<script type="text/javascript">


	function ajax(orderType,monthday,sessionFlag,status) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/financereport?methodtype=costAccountingSsearch&sessionFlag="+sessionFlag;
		url = url + "&monthday=" +monthday;
		url = url + "&statusFlag=" +status;
		url = url + "&orderType=" +orderType;
		
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
					var formData = $("#formModel").serializeArray();
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
							    var collection = $(".box3");
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
			    			rtn= "<a href=\"###\" onClick=\"doCreate('"+ row["YSId"] + "','"+ row["orderType"] + "')\">"+row["YSId"]+"</a>";
	    				}
	    				return rtn;
		    			
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["productName"];
		    			name = jQuery.fixedWidth(name,36);//true:两边截取,左边从汉字开始
		    			
		    			return name;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			var index = row["rownum"];
		    			var order = floatToCurrency(row["quantity"]);
		    			var manuf = floatToCurrency(row["totalQuantity"]);
		    			var text='';
		    			text +=	'<input type="hidden" name="order" 	id="order'+index + '" 	value="'+order+'" >';
		    			text +=	'<input type="hidden" name="manuf" 	id="manuf'+index + '" 	value="'+manuf+'" >';

		    			return order +"<br>"+manuf + text;
		    		}},
		    		{"targets":9,"render":function(data, type, row){
		    			var index = row["rownum"];
		    			var orderType = row["orderType"];
		    			var cost = currencyToFloat(row["cost"]);
		    			var mateCost = currencyToFloat(row["costAcounting"]); 
		    			var currency = row["currency"];
		    			var rate = row["exchange_rate"];
		    			var lastPrice = currencyToFloat(row["lastPrice"]);
		    			var quantity = currencyToFloat(row["totalQuantity"]);
		    			var labolCost = currencyToFloat(row["labolCost"]);//人工成本
		    			
		    			if(cost == 0){
		    				if(orderType == '020'){
			    				cost = lastPrice * quantity;//装配品的成本
			    				mateCost = cost;
		    				}else{
		    					cost = mateCost + labolCost;//未领料的从BOM计算
		    				}
		    			}
		    			cost = floatToCurrency(cost)
		    			labolCost = floatToCurrency(labolCost)
		    			var text = '';
		    			text = text + '<input type="hidden" name="cost" 		id="cost'+index + '" 		value="'+cost+'" >';
		    			text = text + '<input type="hidden" name="labolCost" 	id="labolCost'+index + '" 	value="'+labolCost+'" >';
		    			return cost + "<br />" + labolCost + text;
		    		}},
		    		{"targets":11,"render":function(data, type, row){
		    			var rtnValue="";
		    			var index = row["rownum"];
		    			var orderType = row["orderType"];
		    			var cost = currencyToFloat(row["cost"]);
		    			var mateCost = currencyToFloat(row["costAcounting"]); 
		    			var currency = row["currency"];
		    			var profit = currencyToFloat(row["profit"]);//利润
		    			var profitrate = currencyToFloat(row["profitRate"]);//利润率
		    			var actualSales = currencyToFloat(row["orderTotalPrice"]);
		    			var exchange = currencyToFloat(row["exchange_rate"]);//汇率
		    			var lastPrice = currencyToFloat(row["lastPrice"]);
		    			var quantity = currencyToFloat(row["totalQuantity"]);
		    			var labolCost = currencyToFloat(row["labolCost"]);//人工成本
		    			var rmbprice = currencyToFloat(row["RMBPrice"]);//原币金额
						var rebaterate = 0.16;//退税率
						var rebate = currencyToFloat(row["rebate"]);//退税
						var gendan = currencyToFloat(row["orderDeduct"]);//跟单费用（订单过程中以负数录入）

						var gross = 0;//毛利
						
		    			if(cost == 0){
		    				if(orderType == '020'){
			    				cost = lastPrice * quantity;//装配品的成本
			    				mateCost = cost;
		    				}else{
		    					cost = mateCost + labolCost;//未领料的从BOM计算
		    				}
		    			}else{

		    				profit =  floatToCurrency(profit);
		    				profitrate =  floatToCurrency(profitrate);

			    			var text =	'<input type="hidden" name="rmbprice" 	id="rmbprice'+index + '" 	value="'+rmbprice+'" >';
			    			text +=	  	'<input type="hidden" name="cost" 		id="cost'+index + '" 		value="'+cost+'" >';
			    		  	text +=   	'<input type="hidden" name="rebate" 	id="rebate'+index + '" 		value="'+rebate+'" >';
			    			text +=   	'<input type="hidden" name="labolCost" 	id="labolCost'+index + '" 	value="'+labolCost+'" >';
			    			text +=		'<input type="hidden" name="currency" 	id="currency'+index + '" 	value="'+currency+'" >';
			    			text +=		'<input type="hidden" name="exchange" 	id="exchange'+index + '" 	value="'+exchange+'" >';
			    			text +=		'<input type="hidden" name="profit" 	id="profit'+index + '" 		value="'+profit+'" >';
			    			text +=		'<input type="hidden" name="profitrate"	id="profitrate'+index + '" 	value="'+profitrate+'" >';

			    			return profit + "<br />" + profitrate +"%"+ text;
		    			}
		    			
		    			//*****************************************************///
		    			if(currency == 'RMB'){
		    				
		    			//	if(rebaterate == 0){
		    					//纯内销
		    					//增值税
		    					zeng = (actualSales - mateCost) / 1.16 * 0.16 ;
		    					gross = actualSales - cost - zeng;
		    					rmbprice = actualSales;		    					
		    					
		    			//	}else{
		    			//		//人民币外销
		    			//		exchange = 1;//外销人民币的汇率默认：1
		    			//		rebate = mateCost * rebaterate / (1 + rebaterate);//退税
		    			//		rmbprice = actualSales * exchange;
		    			//		profit = rmbprice - cost + rebate;		    									
		    			//	}
		    						
		    			}else{
		    				//纯外销			
		    				//退税
		    				rebate = mateCost * rebaterate / (1 + rebaterate);
		    				rmbprice = actualSales * exchange;
		    				gross = rmbprice - cost + rebate;
		    			}
		    			profit = gross + gendan;
		    			if(cost <= 0){

			    			profitrate = 0;
		    			}else{
			    			profitrate = floatToCurrency(profit / cost * 100);
		    			}
		    			profit = floatToCurrency(profit);

		    			var text =	'<input type="hidden" name="rmbprice" 	id="rmbprice'+index + '" 	value="'+rmbprice+'" >';
		    			text +=	  	'<input type="hidden" name="cost" 		id="cost'+index + '" 		value="'+cost+'" >';
		    			text +=   	'<input type="hidden" name="rebate" 	id="rebate'+index + '" 		value="'+rebate+'" >';
		    			text +=   	'<input type="hidden" name="labolCost" 	id="labolCost'+index + '" 	value="'+labolCost+'" >';
		    			text +=		'<input type="hidden" name="currency" 	id="currency'+index + '" 	value="'+currency+'" >';
		    			text +=		'<input type="hidden" name="exchange" 	id="exchange'+index + '" 	value="'+exchange+'" >';
		    			text +=		'<input type="hidden" name="profit" 	id="profit'+index + '" 		value="'+profit+'" >';
		    			text +=		'<input type="hidden" name="profitrate"	id="profitrate'+index + '" 	value="'+profitrate+'" >';

		    			return profit + "<br />" + profitrate +"%"+ text;
		    			
		    			//return floatToCurrency(profit)+"<br />+"+"（"+floatToCurrency(profitrate)+"%）";//+":::"+profitrate+":::"+exchange+":::"+mateCost+":::"+labolCost;
		    			//return cost+":::"+profitrate+":::"+exchange+":::"+mateCost+":::"+labolCost;
		    		}},
		    		{"targets":13,"render":function(data, type, row){
		    			return jQuery.fixedWidth(row["team"],8);
		    		}},
		    		{"targets":14,"render":function(data, type, row){
		    			var stockinQty = currencyToFloat(row["stockinQty"]);
		    			var orderQty = currencyToFloat(row["quantity"]);
		    			var storageFinish = currencyToFloat(row["storageFinish"]);
		    			var accountingDate = currencyToFloat(row["accountingDate"]);
		    			var rtn="";
		    			if(accountingDate != '' ){
		    				rtn="已核算";
		    			}else{
		    				if(stockinQty >= orderQty){
			    				rtn="待核算";
			    					
			    			}else{
			    				if(storageFinish == '020'){

				    				rtn="待核算";
			    				}else{

				    				rtn = "待入库";
			    				}
			    			}
		    			}
		    			
		    			return rtn;
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : [10,12]
					}
	         	],
	         	
	         	
			});


	}

	
	function initEvent(){

		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		var orderType = $('#orderType').val();
		
		ajax(orderType,monthday,"true",statusFlag);
	
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
		buttonSelectedEvent3();//按钮选择式样3
		
		var month = "";
		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		var orderType = $('#orderType').val();
		
		if(monthday == '' || monthday == null){
			month = getMonth();
		}else{
			month = monthday.split("-")[1];
		}
	
		$('#defutBtn'+month).removeClass("start").addClass("end");
		$('#defutBtn'+statusFlag).removeClass("start").addClass("end");
		$('#defutBtn'+orderType).removeClass("start").addClass("end");
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
	
	function doCreate(YSId,orderType) {
		

		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();

		var actionUrl = "${ctx}/business/financereport?methodtype=costAccountingAdd";
		if(orderType == '020'){
			//配件订单
			actionUrl = "${ctx}/business/financereport?methodtype=costAccountingPeiAdd";
		}
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
		var orderType = $('#orderType').val();
		ajax(orderType,todaytmp,'false',statusFlag);
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
		var orderType = $('#orderType').val();

		$('#statusFlag').val(status);
		
		ajax(orderType,monthday,'false',status);
	}
	
	//订单分类
	function doSearchCustomer5(orderType){
		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val()

		$('#orderType').val(orderType);
		
		ajax(orderType,monthday,'false',statusFlag);
	}

	//月度统计
	function doShowContract(contractId) {

		var url = '${ctx}/business/financereport?methodtype=monthlyStatistics&contractId=' + contractId;
		
		callProductDesignView("月度统计",url);
	}	
	
	
	function doShow(YSId) {

		var monthday   = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		var orderType  = $('#orderType').val();
		
		var url = '${ctx}/business/financereport?methodtype=costBomDetailView'
				+"&monthday="+monthday
				+"&statusFlag="+statusFlag
				+"&orderType="+orderType
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
		var labolCnt = 0;
		var ysCnt = 0;//订单个数
		var profitCnt = 0;
		$('#TMaterial tbody tr').each (function (){

			var sales        = $(this).find("td").eq(7).text();//销售额
			var currencyType = $(this).find("td").eq(10).find("input[name=currency]").val();//币种
			var cost         = $(this).find("td").eq(10).find("input[name=cost]").val();//核算成本
			var labolCost    = $(this).find("td").eq(10).find("input[name=labolCost]").val();//人工成本
			var rate         = $(this).find("td").eq(10).find("input[name=exchange]").val();//汇率
			var profit       = $(this).find("td").eq(10).find("input[name=profit]").val();//利润
			
			sales = currencyToFloat(sales);
			cost = currencyToFloat(cost);
			labolCost = currencyToFloat(labolCost);
			profit = currencyToFloat(profit);
			rmbCount = rmbCount + rate * sales;//人民币总计
			costSum = costSum + cost;//核算成本合计
			labolCnt = labolCnt + labolCost;//人工成本合计
			profitCnt = profitCnt + profit;//利润合计
			//alert("labolCnt+labolCost"+labolCnt+"::::"+labolCost)
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
			
			ysCnt++;
						
		});	
		//alert("rmb:"+rmb)

		var textView="销售额：";
		if(rmb > 0){
			textView = textView + "¥"+floatToCurrency(rmb);
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
		
		textView = textView + "&nbsp;&nbsp;总成本：¥ "+ floatToCurrency(costSum);
		textView = textView + "&nbsp;&nbsp;人工：¥ "+ floatToCurrency(labolCnt);
		
		//var profit = rmbCount - costSum;//利润
		var profitm = profitCnt / costSum * 100;//利润率
		
		textView = textView + "&nbsp;&nbsp;利润：¥ "+ floatToCurrency(profitCnt)+"（"+floatToCurrency(profitm)+"%）";
		
		$('#costCount').html(textView);
		
	}
	
	function downloadExcel() {
		 
		var key1 = $("#keyword1").val();
		var key2 = $("#keyword2").val();
		var monthday = $("#monthday").val()
		var statusFlag = $("#statusFlag").val()
		
		var html = HtmlTableToJson(TMaterial);
		$("#jsonData").val(JSON.stringify(html));
		
		//return;
		var url = '${ctx}/business/financereport?methodtype=downloadExcelForCostAccounting'
				 + "&key1=" + key1
				 + "&key2=" + key2
				// + "&html=" + JSON.stringify(html)
				 + "&monthday=" + monthday
				 + "&statusFlag=" + statusFlag;
				
		//url =encodeURI(encodeURI(url));//中文两次转码
		
		//location.href = url;
		
		$('#formModel').attr("action", url);
		$('#formModel').submit();
		
/*
		$.ajax({
			type : "post",
			url : url,
			//async : false,
			data : JSON.stringify(html),//$("#TMaterial").serializeArray(),//
			"datatype": "json", 
			"contentType": "application/json; charset=utf-8",
			success : function(data) {			
				window.location.href 
				$().toastmessage('showNoticeToast', "保存成功。");
			},
			 error:function(XMLHttpRequest, textStatus, errorThrown){
				//alert(textStatus)
			}
		});		
		*/
	}
	
	  function HtmlTableToJson(tableid){
		    // table need set thead and tbody
		    // talbe head need set name attr
		    // value control need set josnval attr or will get null string
		    var jsondata = [];
		    // table head
		    var heads = [];
		   
		    $('#TMaterial thead tr').each(function(index,item){
		    		
			      heads.push("No");
			      heads.push("ysid");
			      heads.push("productId");
			      heads.push("name");
			      heads.push("customer");
			      heads.push("orderQty");
			      heads.push("totalQty");
			      heads.push("stockinQty");//7
			      heads.push("salse");
			      heads.push("stockinDate");
			      heads.push("cost");//10
			      heads.push("labolCost");//11
			      heads.push("profit");
			      heads.push("profitrate");	
			      heads.push("team");		
		    	
				//heads.push($(item).text());
		    	
		    });

		    // tbody
		    $('#TMaterial tbody tr').each(function(index, item){
		    	
		      	var rowdata = {};
		      	var y=0;
				rowdata[heads[0]] = $(item).find("td").eq(0).text();
				rowdata[heads[1]] = $(item).find("td").eq(1).text();
				rowdata[heads[2]] = $(item).find("td").eq(2).text();
				rowdata[heads[3]] = $(item).find("td").eq(3).text();
				rowdata[heads[4]] = $(item).find("td").eq(4).text();
	
	    	 	var order = $(item).find("td").eq(5).find('input[name=order]').val();
	    	 	var manuf = $(item).find("td").eq(5).find('input[name=manuf]').val();
	    	 
		    	rowdata[heads[5]] = order;
		    	rowdata[heads[6]] = manuf;

				rowdata[heads[7]] = $(item).find("td").eq(6).text();
				rowdata[heads[8]] = $(item).find("td").eq(7).text();
				rowdata[heads[9]] = $(item).find("td").eq(8).text();
							    	  
				var cost 		= $(item).find("td").eq(9).find('input[name=cost]').val();
		    	var labolCost	= $(item).find("td").eq(9).find('input[name=labolCost]').val();
		    	 
		    	rowdata[heads[10]] = cost;
		    	rowdata[heads[11]] = labolCost;
	    	  
		    	var profit 		= $(item).find("td").eq(10).find('input[name=profit]').val();
		    	var profitrate 	= $(item).find("td").eq(10).find('input[name=profitrate]').val();
		    	
		    	rowdata[heads[12]] = profit;
		    	rowdata[heads[13]] = profitrate;
		    	
				rowdata[heads[14]] = $(item).find("td").eq(11).text();
			    
		       // }else{
		      //    console.log("no jsonval");
		       //   rowdata[heads[index]] = "";
		      //  }
		     // });

		      jsondata.push(rowdata);
		    });
		    return jsondata;
	};
		  
		  
	function errorCheckAndCostCount(){
			
			var cost = 0;
			$('#example2 tbody tr').each (function (){
				
				var stockOutQty = $(this).find("td").eq(5).text();//领料数量
				var contractValue = $(this).find("td").eq(8).text();//领料金额
				
				stockOutQty = currencyToFloat(stockOutQty);
				contractValue= currencyToFloat(contractValue);
				
				cost = cost + contractValue;
				
				if( stockOutQty == 0){
					
					$(this).addClass('error');
				}
							
			});	
			
	}
	
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
		<div id="search">
		
			<input type="hidden" id="monthday"  value="${monthday }"/>
			<input type="hidden" id="statusFlag"  value="${statusFlag }"/>
			<input type="hidden" id="orderType"  value="${orderType }"/>
			<input type="hidden" id="jsonData"  name = "jsonData"  />
			
			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label" style="width:100px">关键字1：</td>
					<td class="">
						<input type="text" id="keyword1" name="keyword1" class="middle"/></td>
					<td class="label" style="width:100px">关键字2：</td> 
					<td class="">
						<input type="text" id="keyword2" name="keyword2" class="middle"/></td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();"/>查询</td>
					<td style="vertical-align: bottom;width: 150px;">
					</td> 
					<td width="10%"></td> 
				</tr>
				<tr>
					<td width="10%"></td> 
					<td class="label" style="width:100px">订单分类：</td>
					<td class="">
						<a id="defutBtn010"  class="DTTT_button box3" onclick="doSearchCustomer5('010');">
						<span>常规订单</span></a> 
				 		<a id="defutBtn020"  class="DTTT_button box3" onclick="doSearchCustomer5('020');">
						<span>装配品</span></a> 					</td>
					<td class="label" style="width:100px">审核状态：</td> 
					<td class="">
						<a  class="DTTT_button box2" onclick="doSearchCustomer2('A');" id="defutBtnA"><span>ALL</span></a>
						<a  class="DTTT_button box2" onclick="doSearchCustomer2('D');" id="defutBtnD"><span>待审</span></a>
						<a  class="DTTT_button box2" onclick="doSearchCustomer2('Y');" id="defutBtnY"><span>已审</span></a>
					</td>
					<td></td>
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
				 		&nbsp;&nbsp;<a id="defutBtnB" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer3('B');">
						<span>部分入库</span></a> 	
					</td>
				</tr>
				
			</table>

		
	</div>
	<div  style="height:10px"></div>
	<div class="list">

		<div id="TSupplier_wrapper" class="dataTables_wrapper">
					
			<div id="DTTT_container2" style="height:40px;float: right">
			  	<a  class="DTTT_button" onclick="downloadExcel();return false;" ><span>EXCEL</span></a>
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
						<th style="width: 40px;">订单量/<br/>生产量</th>
						<th style="width: 40px;">入库<br/>累计</th>
						<th style="width: 50px;">销售金额</th>
						<th style="width: 50px;">入库日期</th>
						<th style="width: 40px;">核算成本<br/>人工成本</th>
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
	</form:form>
</div>
</div>
</body>
</html>
