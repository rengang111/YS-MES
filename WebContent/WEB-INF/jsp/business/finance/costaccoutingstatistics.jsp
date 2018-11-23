<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<style>
body{
  /* font-size:10px;*/
}
</style>
<title>财务核算----月度统计</title>
<script type="text/javascript">


	function ajaxSearch(yearFlag,team) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/financereport?methodtype=monthlyStatistics";
		url = url + "&yearFlag=" +yearFlag;
		//url = url + "&statusFlag=" +status;
		//url = url + "&orderType=" +orderType;
		url = url + "&team=" +team;
		//url = url + "&unFinished=" +unFinished;
		
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
							
							
							
							
							//核算成本合计，利润合计
							//costCountByCurrency();
							
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
					{"data": "groupFlag", "defaultContent" : ''},
					{"data": "xiaoshou_group", "defaultContent" : '', "className" : 'td-right'},
					{"data": "xiaoshou_group", "defaultContent" : '', "className" : 'td-right'},//3
					{"data": "cost_group", "defaultContent" : '', "className" : 'td-right'},
					{"data": "materail_group", "defaultContent" : '', "className" : 'td-right'},//5
					{"data": "labol_group", "defaultContent" : '', "className" : 'td-right'},
					{"data": "profit_group", "className" : 'td-right'},//7
					{"data": "profit_rate", "defaultContent" : '0', "className" : 'td-right'},
														
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"];
                    }},
                    {"targets":8,"render":function(data, type, row){
		    			return data+'%';
                    }}
		    		
	         	],
	         	
	         	
			});


	}


	
	function initEvent(){

		var monthday   = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		var orderType  = $('#orderType').val();
		
		var year = getYear();
		var mounth = getMonth() ;//前移一个月
		mounth = mounth -1;
		if(mounth<10){
			  return '0'+mounth;
		}
		if(monthday == '12'){
			year = year - 1;//去年的12月
		}
		var todaytmp = year +''+mounth;
		
		//alert('todaytmp:'+todaytmp)
		$('#monthday').val(year);
		ajaxSearch(year,'ALL');
	
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
	
		//buttonSelectedEvent();//按钮选择式样
		//buttonSelectedEvent2();//按钮选择式样2
		//buttonSelectedEvent3();//按钮选择式样3
		
		var month = "";
		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		var orderType = $('#orderType').val();
		
		if(monthday == '' || monthday == null){
			month = getMonth();
		}else{
			month = monthday.substring(4,6);
		}
		
		
		$("#team").change(function() {

			var monthday = $('#monthday').val();
			var orderType = $('#orderType').val();
			var statusFlag = $('#statusFlag').val();
			var team = $(this).val();
			
			ajaxSearch(orderType,monthday,'false',statusFlag,team,'');	
		});
	
		//$('#defutBtn'+month).removeClass("start").addClass("end");
		//$('#defutBtn'+statusFlag).removeClass("start").addClass("end");
		//$('#defutBtn'+orderType).removeClass("start").addClass("end");
	})	
	
	function doSearch() {	

		ajaxSearch('','','false','','ALL','');
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
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
		//var profitm = profitCnt / costSum * 100;//利润率
		
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
		
			<input type="hidden" id="costConcelFlag"  value=""/>
			<input type="hidden" id="monthday"  value="${monthday }"/>
			<input type="hidden" id="statusFlag"  value="${statusFlag }"/>
			<input type="hidden" id="orderType"  value="${orderType }"/>
			<input type="hidden" id="jsonData"  name = "jsonData"  />
			
			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label" style="width:100px">年度：</td>
					<td><form:select path="year" style="width: 100px;">
						<form:options items="${year}" itemValue="key" itemLabel="value" />
						</form:select></td>
					
					<td style="text-align: right;">业务组：</td>
					<td><form:select path="team" style="width: 100px;">
						<form:options items="${team}" itemValue="key" itemLabel="value" />
						</form:select></td> 
					<td width="10%"></td> 
				</tr>
				
				
			</table>

		
	</div>
	<div  style="height:10px"></div>
	<div class="list">

		<div id="TSupplier_wrapper" class="dataTables_wrapper">
					
			<div id="DTTT_container2" style="height:40px;float: right">
			  	<!-- a  class="DTTT_button" onclick="downloadExcel();return false;" ><span>EXCEL</span></a-->
			</div>
			<div id="DTTT_container2" style="height:40px;float: right">
				<span id="costCount" style="font-size: 12px;font-weight: bold;"></span>
			</div>
			 
			<div id="clear"></div>
			<table id="TMaterial" class="display" >
				<thead>						
					<tr>
						<th style="width: 10px;">No</th>
						<th style="width: 60px;">年月</th>
						<th style="width: 120px;">销售额</th>
						<th style="width: 150px;">RMB销售总计</th>
						<th style="width: 150px;">核算成本</th>
						<th style="width: 150px;">材料成本</th>
						<th style="width: 150px;">人工成本</th>
						<th style="width: 100px;">利润</th>
						<th style="width: 70px;">利润率</th>
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
