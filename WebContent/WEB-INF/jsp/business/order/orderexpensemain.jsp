<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>

<title>订单基本数据一览页面(订单过程)</title>
<script type="text/javascript">

function ajax(year,sessionFlag,costType) {
	
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var url = "${ctx}/business/order?methodtype=orderExpenseSearch&sessionFlag="+sessionFlag;
		url = url + "&year=" +year;
		url = url + "&costType=" +costType;
		
		var t = $('#TMaterial').DataTable({
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//每页显示条数设置
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"bWidth"	:false,
			// "bFilter": false, //列筛序功能
			"ordering"	:true,
			"searching" : false,
			// "Info": true,//页脚信息
			// "bPaginate": true, //翻页功能
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
				{"data": "productId", "defaultContent" : ''},
				{"data": "productName", "defaultContent" : ''},
				{"data": "quantity", "className" : 'td-right'},//4
				{"data": "deliveryDate", "defaultContent" : '',"className" : 'td-center'},
				{"data": "stockinQty", "className" : 'td-right'},//6
				{"data": "checkInDate", "defaultContent" : '',"className" : 'td-center'},
				{"data": "chejianCost", "defaultContent" : '0',"className" : 'td-right'},//8
				{"data": "gongyingshangCost", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "kehuCost", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "jianyanCost", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "gendanCost", "defaultContent" : '0',"className" : 'td-right'},//12
				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
	    			return row["rownum"];			    			 
                }},
	    		{"targets":1,"render":function(data, type, row){
	    			var rtn = "";
	    			rtn= "<a href=\"###\" onClick=\"doShow('" + row["YSId"] +"','"+ row["materialId"] + "')\">"+ row["YSId"] +"</a>";
	    			return rtn;
	    		}},
	    		{"targets":2,"render":function(data, type, row){
	    			var rtn = "";
	    			rtn= "<a href=\"###\" onClick=\"doShowPlan('" + row["YSId"] +"')\">"+ row["productId"] +"</a>";
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			return jQuery.fixedWidth(data,32);
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":11,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":12,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":8,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":9,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":10,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{
					"visible" : false,
					"targets" : [6,7]
				}				           
         	] 
		});

	}

	
	function initEvent(){

		var year = $('#year').val();
		var costType = $('#costType').val();
		
		if(year == '' || year == null){
			year = getYear();
		}
		if(costType == '' || costType == null){
			costType = 'C';
		}
		
		
		ajax(year,"true",costType);
	

		$('#defutBtn'+year).removeClass("start").addClass("end");
		$('#defutBtn'+costType).removeClass("start").addClass("end");
		
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
		
	})	
	
	function doSearch() {	

		var key1 = $('#keyword1').val();
		var key2 = $('#keyword2').val();
		if($.trim(key1) =='' && $.trim(key2) ==''){
			$().toastmessage('showWarningToast', "请输入关键字。");		
			return;
		}
		
		ajax('','false','');
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });

	}
	
	function doShow(YSId,materialId) {
		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		var url = '${ctx}/business/bom?methodtype=orderexpenseview&YSId=' + YSId
				+'&materialId='+materialId
				+'&monthday='+monthday
				+'&statusFlag='+statusFlag;

		location.href = url;
	}
	
	function doShowPlan(YSId) {

		var url = '${ctx}/business/purchasePlan?methodtype=showPurchasePlan&YSId=' + YSId;
		callProductDesignView('采购方案',url);
	}
	
	//年份选择
	function doSearchCustomer(year){
		
		$('#keyword1').val('');
		$('#keyword2').val('');
		$('#year').val(year);
				
		var costType = $('#costType').val();

		if(costType == '' || costType == null){
			costType = 'C';
		}

		$('#defutBtn'+year).removeClass("start").addClass("end");
		$('#defutBtn'+costType).removeClass("start").addClass("end");
		
		ajax(year,'false',costType);
	}
	

	//费用类别
	function doSearchCustomer2(type,hidden_col){

		$('#keyword1').val('');
		$('#keyword2').val('');
		$('#costType').val(type);
		
		var year = $('#year').val();
		if(year == '' || year == null){
			year = getYear();
		}

		$('#defutBtn'+year).removeClass("start").addClass("end");
		$('#defutBtn'+type).removeClass("start").addClass("end");
		
		ajax(year,'false',type);
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >

			<input type="hidden" id="monthday"    value="${monthday }"/>
			<input type="hidden" id="statusFlag"  value="${statusFlag }"/>
			<input type="hidden" id="hiddenCol"  value="${hiddenCol }"/>
			<input type="hidden" id="year"       value="${year }"/>
			<input type="hidden" id="costType"   value="${costType }"/>
			
			<table>
				<tr>
					<td width="50px"></td> 
					<td class="label">关键字1：</td>
					<td class="condition"><input type="text" id="keyword1" name="keyword1" class="middle"/></td>
					<td class="label">关键字2：</td> 
					<td class="condition"><input type="text" id="keyword2" name="keyword2" class="middle"/></td>
					<td><button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();"/>查询</td>
					<td width=""></td> 
				</tr>
				<tr>
					<td width="50px"></td> 
					<td class="label">年份选择：</td>			
					<td class="condition"> 
						<a class="DTTT_button box" onclick="doSearchCustomer('2018',7);" id="defutBtn2018">2018</a>
						<a class="DTTT_button box" onclick="doSearchCustomer('2019',7);" id="defutBtn2019">2019</a></td> 
			
					<td class="label" >费用类别：</td>					
					<td colspan="3"> 
						<a class="DTTT_button box2" onclick="doSearchCustomer2('C',7);" id="defutBtnC">车间费用</a>
						<a class="DTTT_button box2" onclick="doSearchCustomer2('S',7);" id="defutBtnS">供应商费用</a>
						<a class="DTTT_button box2" onclick="doSearchCustomer2('K',5);" id="defutBtnK">客户费用</a>
						<a class="DTTT_button box2" onclick="doSearchCustomer2('J',7);" id="defutBtnJ">检验费用</a>
						<a class="DTTT_button box2" onclick="doSearchCustomer2('G',7);" id="defutBtnG">跟单费用</a>
						<a class="DTTT_button box2" onclick="doSearchCustomer2('A',7);" id="defutBtnN">ALL</a></td> 
				
					</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>
	<div class="list">
		<table id="TMaterial" class="display dataTable" cellspacing="0" style="width:100%">
			<thead>						
				<tr>
					<th style="width: 1px;" class="dt-middle ">No</th>
					<th style="width: 60px;" class="dt-middle ">耀升编号</th>
					<th style="width: 100px;" class="dt-middle ">产品编号</th>
					<th class="dt-middle ">产品名称</th>
					<th style="width: 60px;" class="dt-middle ">订单数量</th>
					<th style="width: 60px;" class="dt-middle ">订单交期</th>
					<th style="width: 60px;" class="dt-middle ">入库数量</th>
					<th style="width: 50px;" class="dt-middle ">入库时间</th>
					<th style="width: 50px;" class="dt-middle ">车间费用</th>
					<th style="width: 50px;" class="dt-middle ">供应商<br/>费用</th>
					<th style="width: 50px;" class="dt-middle ">客户费用</th>
					<th style="width: 50px;" class="dt-middle ">检验费用</th>
					<th style="width: 50px;" class="dt-middle ">跟单费用</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
