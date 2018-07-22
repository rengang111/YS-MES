<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />


<%@ include file="../../common/common.jsp"%>

<title>订单基本数据一览页面(订单过程)</title>
<script type="text/javascript">

function ajax(monthday,sessionFlag,status) {
	
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var url = "${ctx}/business/order?methodtype=orderExpenseSearch&sessionFlag="+sessionFlag;
		url = url + "&monthday=" +monthday;
		url = url + "&statusFlag=" +status;
		
		var t = $('#TMaterial').DataTable({
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//每页显示条数设置
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			//"bSort":true,
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
				{"data": "quantity", "className" : 'td-right'},
				{"data": "checkInDate", "defaultContent" : '',"className" : 'td-center'},
				{"data": "chejianCost", "defaultContent" : '0',"className" : 'td-right'},//7
				{"data": "gongyingshangCost", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "kehuCost", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "jianyanCost", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "gendanCost", "defaultContent" : '0',"className" : 'td-right'},//11
				
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
	    		{"targets":3,"render":function(data, type, row){
	    			return jQuery.fixedWidth(data,32);
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":7,"render":function(data, type, row){
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
					"targets" : []
				}				           
         	] 
		});

	}
	
	function YSKcheck(v,id){
		var zzFlag = "";
		if(id != ''){
			zzFlag = id.substr(2,3);
		}
		if(zzFlag == 'YSK') v = 0;//库存订单不显示明细内容
		return v;
		
	}
	
	function initEvent(){

		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		ajax(monthday,"true",statusFlag);
	
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


	function doCreate() {

		var url = '${ctx}/business/order?methodtype=orderExpenseYsid';

		location.href = url;
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
		ajax(todaytmp,'false',statusFlag);
	}
	

	//订单状态
	function doSearchCustomer2(status){
		
		var monthday = $('#monthday').val();

		$('#statusFlag').val(status);
		ajax(monthday,'false',status);
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
							<td class="label">关键字1：</td>
							<td class="condition">
								<input type="text" id="keyword1" name="keyword1" class="middle"/>
							</td>
							<td class="label">关键字2：</td> 
							<td class="condition">
								<input type="text" id="keyword2" name="keyword2" class="middle"/>
							</td>
							<td>
								<button type="button" id="retrieve" class="DTTT_button" 
									style="width:50px" value="查询" onclick="doSearch();"/>查询
							</td>
							<td width="10%"></td> 
						</tr>
				<tr style="height: 40px;">
					<td width="10%"></td> 
					<td class="label" style="width:100px">快捷查询：</td>
					
					<td colspan="6">
						<a id="defutBtn12" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('12');">
						<span>去年12月份</span></a>
						<a id="defutBtn01"  style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('01');">
						<span>1月份</span></a>
						<a id="defutBtn02" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('02');">
						<span>2月份</span></a>
						<a id="defutBtn03" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('03');">
						<span>3月份</span></a>
						<a id="defutBtn04" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('04');">
						<span>4月份</span></a>
						<a id="defutBtn05" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('05');">
						<span>5月份</span></a>
						<a id="defutBtn06" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('06');">
						<span>6月份</span></a>
						<a id="defutBtn07" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('07');">
						<span>7月份</span></a>
						<a id="defutBtn08" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('08');">
						<span>8月份</span></a>
						<a id="defutBtn09" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('09');">
						<span>9月份</span></a>
						<a id="defutBtn10" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('10');">
						<span>10月份</span></a>
						<a id="defutBtn11" style="height: 15px;" class="DTTT_button box" onclick="doSearchCustomer('11');">
						<span>11月份</span></a>
					</td> 
				</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">

				<div id="DTTT_container2" style="height:40px;float: left">
					<a  class="DTTT_button box2" onclick="doSearchCustomer2('');" id="defutBtn010"><span>ALL</span></a>
					<a  class="DTTT_button box2" onclick="doSearchCustomer2('010');" id="defutBtn010"><span>待入库</span></a>
					<a  class="DTTT_button box2" onclick="doSearchCustomer2('020');" id="defutBtn020"><span>已入库</span></a>
				</div>
				<!-- <div id="DTTT_container2" style="height:40px;float: right">
					<a  class="DTTT_button box" onclick="doCreate();" ><span>订单过程录入</span></a>
				</div> -->
				<table id="TMaterial" class="display dataTable" cellspacing="0">
					<thead>						
						<tr>
							<th style="width: 10px;" class="dt-middle ">No</th>
							<th style="width: 60px;" class="dt-middle ">耀升编号</th>
							<th style="width: 100px;" class="dt-middle ">产品编号</th>
							<th class="dt-middle ">产品名称</th>
							<th style="width: 60px;" class="dt-middle ">订单数量</th>
							<th style="width: 70px;" class="dt-middle ">入库时间</th>
							<th style="width: 60px;" class="dt-middle ">车间费用</th>
							<th style="width: 60px;" class="dt-middle ">供应商<br/>费用</th>
							<th style="width: 60px;" class="dt-middle ">客户费用</th>
							<th style="width: 60px;" class="dt-middle ">检验费用</th>
							<th style="width: 60px;" class="dt-middle ">跟单费用</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
