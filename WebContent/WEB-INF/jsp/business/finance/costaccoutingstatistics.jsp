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
					{"data": "groupFlag", "defaultContent" : ''},
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
                    {"targets":7,"render":function(data, type, row){
		    			return data+'%';
                    }}
		    		
	         	],
	         	
	         	
			});


	}
	
	function initEvent(){

		var year = $('#defulYear').val();
		
		if(year == '' || year == null)
			year = getYear();
			
		$('#year').val(year);
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
				
		$("#team").change(function() {

			var monthday = $('#year').val();
			var team = $(this).val();
			
			ajaxSearch(monthday,team);	
		});
		

		$("#year").change(function() {

			var monthday = $('#year').val();
			var team = $('#team').val();
			
			ajaxSearch(monthday,team);	
		});
	
	})	
	
	
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
		var materCnt = 0;//订单个数
		var profitCnt = 0;
		$('#TMaterial tbody tr').each (function (){

			var sales        = $(this).find("td").eq(2).text();//销售额
			var cost         = $(this).find("td").eq(3).text();//核算成本
			var materCost    = $(this).find("td").eq(4).text();//材料成本
			var labolCost    = $(this).find("td").eq(5).text();//人工成本
			var profit       = $(this).find("td").eq(6).text();//利润
			
			sales     = currencyToFloat(sales);
			cost      = currencyToFloat(cost);
			materCost = currencyToFloat(materCost);
			labolCost = currencyToFloat(labolCost);
			profit   = currencyToFloat(profit);
			
			rmbCount = rmbCount + sales;//人民币总计
			costSum  = costSum + cost;//核算成本合计
			materCnt = materCnt + materCost;//材料成本合计
			labolCnt = labolCnt + labolCost;//人工成本合计
			profitCnt = profitCnt + profit;//利润合计
			//alert("labolCnt+labolCost"+labolCnt+"::::"+labolCost)
			
						
		});	
		//alert("rmb:"+rmb)

			var rate = profitCnt / rmbCount * 100;

			//alert("rmbCount+labolCost"+rmbCount+"::::"+costSum+"::::"+materCnt+"::::"+labolCnt+"::::"+profitCnt+"::::"+rate)
			$('#rmb').text(floatToCurrency(rmbCount));
			$('#cost').text(floatToCurrency(costSum));
			$('#mater').text(floatToCurrency(materCnt));
			$('#labol').text(floatToCurrency(labolCnt));
			$('#profit').text(floatToCurrency(profitCnt));
			$('#rate').text(floatToCurrency(rate)+''+'%');
		
	}
	
	function downloadExcel() {
		 
		var key1 = $("#keyword1").val();
		var key2 = $("#keyword2").val();
		var monthday = $("#monthday").val()
		var statusFlag = $("#statusFlag").val()
		
		var html = HtmlTableToJson(TMaterial);
		$("#jsonData").val(JSON.stringify(html));
		
		//return;
		var url = '${ctx}/business/financereport?methodtype=downloadExcelForCostStatistics'
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
			      heads.push("year");
			      heads.push("rmb");
			      heads.push("cost");
			      heads.push("matercost");
			      heads.push("labolcost");
			      heads.push("profit");
			      heads.push("profitrate");
		    	
		    	
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
				rowdata[heads[5]] = $(item).find("td").eq(5).text();	
				rowdata[heads[6]] = $(item).find("td").eq(6).text();	
				rowdata[heads[7]] = $(item).find("td").eq(7).text();
						      
		      jsondata.push(rowdata);
		    });

		    // tfoot
		    $('#TMaterial tfoot tr').each(function(index, item){
		    	
		      	var rowdata = {};
		      	var y=0;
				rowdata[heads[0]] = $(item).find("td").eq(0).text();
				rowdata[heads[1]] = $(item).find("td").eq(1).text();
				rowdata[heads[2]] = $(item).find("td").eq(2).text();
				rowdata[heads[3]] = $(item).find("td").eq(3).text();
				rowdata[heads[4]] = $(item).find("td").eq(4).text();
				rowdata[heads[5]] = $(item).find("td").eq(5).text();	
				rowdata[heads[6]] = $(item).find("td").eq(6).text();
				rowdata[heads[7]] = $(item).find("td").eq(7).text();
						      
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
			<input type="hidden" id="defulYear"  value="${defulYear }" />
			
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
						<th style="width: 60px;">年月</th>
						<th style="width: 150px;">RMB销售总计</th>
						<th style="width: 150px;">核算成本</th>
						<th style="width: 150px;">材料成本</th>
						<th style="width: 150px;">人工成本</th>
						<th style="width: 100px;">利润</th>
						<th style="width: 70px;">利润率</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td></td>
						<td>合计：</td>
						<td id="rmb"></td>
						<td id="cost"></td>
						<td id="mater"></td>
						<td id="labol"></td>
						<td id="profit"></td>
						<td id="rate"></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	</form:form>
</div>
</div>
</body>
</html>
