<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>应收款--一览</title>
<style>
body{
	font-size:11px;
}
</style>
<script type="text/javascript">

	function searchAjax(type,sessionFlag,monthday) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var yewuzuId   = $("#yewuzuId").val();
		var year       = $("#year").val();
		var url = "${ctx}/business/receivable?methodtype=search";
		url = url + "&sessionFlag="+sessionFlag;
		url = url + "&status="+type;
		url = url + "&year=" + year;
		url = url + "&yewuzuId=" + yewuzuId;
		url = url + "&monthday="+monthday;

		var colSort = 5;
		//if(type == '070')
		//	colSort = 8;
			
		var t = $('#TMaterial').DataTable({
				"paging": true,//action,
				"iDisplayLength" : 100,
				"lengthChange":false,
				//"lengthMenu":[10,150,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"ordering "	:true,
				"searching" :false,
				"stateSave" :false,
	         	"bAutoWidth":false,
				"pagingType" : "full_numbers",
				//"retrieve" : true,
				"sAjaxSource" : url,
				//"scrollY":scrollHeight,
				//"scrollCollapse":false,
				// "aaSorting": [[ colSort, "asc" ]],
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

							$("#rmbOrderSum").text('');
							$("#usdOrderSum").text('');
							$("#rmbActuaSum").text('');
							$("#usdActuaSum").text('');
							$("#rmbSurplSum").text('');
							$("#usdSurplSum").text('');
							
							fnCallback(data);
							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);
							
							$("#rmbOrderSum").text(floatToCurrency(data['mapSum']['rmbOrderSum']));
							$("#usdOrderSum").text(floatToCurrency(data['mapSum']['usdOrderSum']));
							$("#rmbActuaSum").text(floatToCurrency(data['mapSum']['rmbActuaSum']));
							$("#usdActuaSum").text(floatToCurrency(data['mapSum']['usdActuaSum']));
							$("#rmbSurplSum").text(floatToCurrency(data['mapSum']['rmbSurplSum']));
							$("#usdSurplSum").text(floatToCurrency(data['mapSum']['usdSurplSum']));
						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null,"className" : 'td-center'},//0
					{"data": null, "defaultContent" : '', "className" : 'td-left'},//1 收款编号
					{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},//2
					{"data": "productId", "defaultContent" : '', "className" : 'td-left'},//3				
					{"data": "productName", "defaultContent" : '', "className" : ''},//4		
					{"data": "customerId", "defaultContent" : '0', "className" : 'td-right'},//5客户编号
					{"data": "orderPrice", "defaultContent" : '0', "className" : 'td-right'},//6 应收金额
					{"data": "actualAmountCnt", "defaultContent" : '0', "className" : 'td-right'},//7实收金额
					{"data": "reserveDate", "className" : 'td-center', "defaultContent" : '***'},//8 约定收款日
					{"data": "teamName", "className" : 'td-center', "defaultContent" : '***'},//9 业务组
					{"data": "status", "className" : 'td-center'},//10 收款状态
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			var paymentId = row["receivableId"];
		    			if(paymentId == ""){
							return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["YSId"] + "' />";		    				
		    			}else{
							return row["rownum"];
		    			}
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = row["receivableId"];//
		    			if(rtn == ""){
				    		rtn= "<a href=\"###\" onClick=\"doCreate2('" + row["YSId"] +"')\">" + "（单项收款）" + "</a>";
		    			}else{
			    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["YSId"] + "','"+ row["receivableId"] + "')\">" + row["receivableId"] + "</a>";
		    			}
		    			
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			var rtn = "";
		    			var YSId = jQuery.fixedWidth(row["YSId"],10);
		    			rtn= "<a href=\"###\" onClick=\"doShowOrderDetail('" + row["PIId"] +"')\">" + YSId + "</a>";
		    			return rtn;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    					    			
		    			return jQuery.fixedWidth(data,26);
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    					    			
		    			return floatToSymbol(data,row["currency"]);
		    		}},
		    		{"targets":7,"render":function(data, type, row){//实收金额
		    			var rtn = "0";
		    			if(row['statusId'] == '030'){
		    				rtn = floatToSymbol(row['totalPrice'],row["currency"]);
		    			}else if(row['statusId'] == '020'){
		    				rtn = floatToSymbol(row['actualCnt'],row["currency"]);
		    			}
		    			return rtn;
		    		}},
		    		{"targets":10,"render":function(data, type, row){
		    			if(data == '已收款')
		    				data = row['collectionDate']
		    			return data;
		    		}},
		    		{ "bSortable": false, "aTargets": [ 0 ] },
		    		{
						"visible" : false,
						"targets" : []
					}
	           
	         ] 
			});
	}

	$(document).ready(function() {
		
		hideAllSearch();

		setYearList();
		
		
		var scrollHeight = $(document).height() - 200; 

		var searchSts = $('#searchSts').val();
		var yewuzuId    = $('#yewuzuId').val();
		var month     = $('#month').val();
		var monthday  = $('#monthday').val();
		
		searchAjax(searchSts,"true",monthday);
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		buttonSelectedEvent3();//按钮选择式样
		buttonSelectedEvent4();//按钮选择式样

		$('#defutBtn'+month).removeClass("start").addClass("end");	
		$('#defutBtnu'+yewuzuId).removeClass("start").addClass("end");	
		$('#defutBtnm'+searchSts).removeClass("start").addClass("end");	
		
		$("#yearList").change(function() {
			
			var yearList  = $('#yearList').val();
			var currYear = getYear();
			
			if(yearList == currYear){//当前年份
				var month = getMonth();//$('#month').val();
			
			}else{//其他年份

				var month = '12';//默认是年末
			}
			
			var monthday = yearList +"-"+month;
			$('#monthday').val(monthday);
			$('#year').val(yearList);
			

			var collection = $(".box");
		    $.each(collection, function () {
		    	$(this).removeClass("end");
		    });
		    
		 	$('#defutBtn'+month).removeClass("start").addClass("end");
		 	
		 	searchAjax('','false',monthday);
	
		});

		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			$(this).toggleClass("selected");
		    if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
		        
		    	$(this).children().first().children().prop("checked", true);
		    }else{//如果没有被选中

		    	$(this).children().first().children().prop("checked", false);
		    }			
		});	

	})	
	
	function hideAllSearch(){

		$('#yearFlag').hide();
		//$('#userFlag').hide();
	}
	
	function setYearList(){
		var i = 0;	
		var options = "";
		<c:forEach var="list" items="${year}">
			i++;
			options += '<option value="${list.key}">${list.value}</option>';
		</c:forEach>
		
		var curYear = getYear();
		$('#yearList').html(options);
		
		$('#yearList').val(curYear);//默认显示当前年
	}
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		var scrollHeight = $(document).height() - 200; 
			
		searchAjax("","false","");

	}
	
	function doSearch2(type) {	

		hideAllSearch();
		
		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    
	 	$('#defutBtm'+type).removeClass("start").addClass("end");
	 	
		
		searchAjax(type,"false","");

	}
	
	function doCreate() {
		var str = '';
		var supplierId = '';
		var supplierId_next = '';
	    var flag = true;
	    var rtnValue = true;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				str += $(this).val() + ",";	
								
				if(flag){
					supplierId      = $(this).parent().parent().find('td').eq(5).text();
					supplierId_next = $(this).parent().parent().find('td').eq(5).text();
					flag = false;
				}else{
					supplierId_next = $(this).parent().parent().find('td').eq(5).text();
					//alert(supplierId+"---"+supplierId_next)
					if(supplierId != supplierId_next){
					
						$().toastmessage('showWarningToast', "请选择同一个客户。");

						rtnValue = false;
						return false;
					}
				}			
			}
		});

		
		if(!rtnValue)
			return;
		
		if (str == '') {
			alert("请至少选择一条数据");
			return;
		}		
		var url = '${ctx}/business/receivable?methodtype=ordersAddInit';
		url = url +"&ysids="+str;
		location.href = url;
		
	}
	
	function doCreate2(YSId) {

		var url = '${ctx}/business/receivable?methodtype=addInit';
		url = url +"&ysids="+YSId;
		location.href = url;
		
	}
	
	function doShowDetail(YSId,receivableId) {

		var url = '${ctx}/business/receivable?methodtype=receivableDetailInit&receivableId=' + receivableId+'&YSId='+YSId;

		callWindowFullView("收汇明细",url);
	}

	function doShowContract(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callWindowFullView("采购合同",url);
	}	
	

	function fnselectall() { 
		if($("#selectall").prop("checked")){
			$("input[name='numCheck']").each(function() {
				$(this).prop("checked", true);
				$(this).parent().parent().addClass("selected");
			});
				
		}else{
			$("input[name='numCheck']").each(function() {
				if($(this).prop("checked")){
					$(this).removeAttr("checked");
					$(this).parent().parent().removeClass('selected');
				}
			});
		}
	};
	
	function fnreverse() { 
		$("input[name='numCheck']").each(function () {  
	        $(this).prop("checked", !$(this).prop("checked"));  
			$(this).parent().parent().toggleClass("selected");
	    });
	};
	
	function downloadExcel(finishStatus) {
		 
		var key1 = $("#keyword1").val();
		var key2 = $("#keyword2").val();
		var searchType = $("#searchSts").val();
		
		var url = '${ctx}/business/payment?methodtype=downloadExcelForPayment'
				
				 + "&key1=" + key1
				 + "&key2=" + key2
				 + "&finishStatus=070";
				 + "&searchType=" + searchType;
		
		url =encodeURI(encodeURI(url));//中文两次转码

		location.href = url;
	}
	
	//已收款
	function doSearchCustomer3(searchSts){

		hideAllSearch();
		$('#yearFlag').show();

		var monthday = '';//getYearMonth();
		var monthonly = 'ALL';//getMonth();
		var userId = $('#yewuzuId').val();
		
		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });		    

	 	$('#defutBtnm'+searchSts).removeClass("start").addClass("end");
	 	$('#defutBtn'+monthonly).removeClass("start").addClass("end");
	 	$('#defutBtnu'+userId).removeClass("start").addClass("end");


		var curYear = getYear();
		$('#year').val(curYear);//默认显示当前年
		$('#yearList').val(curYear);//默认显示当前年
		$("#searchSts").val(searchSts);	
		$("#monthday").val(monthday);	
		$("#month").val(monthonly);	
		
	 	searchAjax(searchSts,'false',monthday);		
	}
	
	//采购员选择
	function doSelectUserId(userId){

		var monthday  = $('#monthday').val();
		var searchSts = $('#searchSts').val();
		
	 	$('#defutBtnu'+userId).removeClass("start").addClass("end");
	 	//$('#defutBtnm'+searchSts).removeClass("start").addClass("end");
	 	
		$('#yewuzuId').val(userId);

		searchAjax(searchSts,'false','',monthday);
	}
	
	//月份选择
	function doSearchCustomer(month){
				
		var year = $('#yearList').val();
		var monthday = '';		

		if(month == '12'){
			var crrMonth = getMonth();
			if(month == crrMonth){
				//当前就是12月				
			}else{
				var CurrYear = getYear();
				if(year == CurrYear){
					
					var tmpYear = CurrYear - 1;
					monthday = tmpYear +"-"+month; 
					$('#year').val(tmpYear);
					$('#yearList').val(getYear());					
				}
				
				var collection = $(".box4");
			    $.each(collection, function () {
			    	$(this).removeClass("end");
			    });
				$('#defutBtny'+CurrYear).removeClass("start").addClass("end");
			}
		}else{
			if(month != 'ALL'){
				monthday = year +"-"+month; 
			}
			$('#year').val(year);
		}

		$('#monthday').val(monthday);
		$('#month').val(month);
		
		searchAjax('030','false','',monthday);
	}
	

	function doShowOrderDetail(PIId) {

		var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;

		callWindowFullView("订单详情",url);
	}
		
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">

		<form id="condition"  style='padding: 0px; margin: 10px;' >
								
			<input type="hidden" id="paymentTypeId" value="010"><!-- 正常付款 -->
			<input type="hidden" id="methodtype" value="${methodtype }" />
			<input type="hidden" id="makeType"   value="${makeType }" />
			<input type="hidden" id="searchSts"  value="${searchSts }" /><!-- 快速查询按钮 -->
			<input type="hidden" id="yewuzuId"     value="${yewuzuId }" />
			<input type="hidden" id="methodkey"  value="${methodkey }" />
			<input type="hidden" id="monthday" name="monthday" value="" />
			<input type="hidden" id="month"    name="month"    value="" />
			<input type="hidden" id="year"    name="year"    value="" />

			<table>
				<tr>
					<td width="50px"></td> 
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
							style="width:50px" value="查询" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
		<tr style="height: 25px;">
			<td width=""></td> 
			<td class="label">收汇情况：</td>
			<td colspan="5">
				<a id="defutBtnm0"   class="DTTT_button box2" onclick="doSearch2('0');">ALL</a>
				<a id="defutBtnm010" class="DTTT_button box2" onclick="doSearch2('010');">待收款</a>
				<a id="defutBtnm020" class="DTTT_button box2" onclick="doSearch2('070');" >逾期未收款</a>
				<a id="defutBtnm030" class="DTTT_button box2" onclick="doSearchCustomer3('030');">已收款</a>
				
				<span id="yearFlag">			
					<select id="yearList" name="yearList"  style="width: 100px;vertical-align: bottom;height: 25px;"></select>
					
					<a id="defutBtn12"  class="DTTT_button box" onclick="doSearchCustomer('12');">
						12</a>
					<a id="defutBtn01"  class="DTTT_button box" onclick="doSearchCustomer('01');">
						1</a>
					<a id="defutBtn02"  class="DTTT_button box" onclick="doSearchCustomer('02');">
						2</a>
					<a id="defutBtn03"  class="DTTT_button box" onclick="doSearchCustomer('03');">
						3</a>
					<a id="defutBtn04"  class="DTTT_button box" onclick="doSearchCustomer('04');">
						4</a>
					<a id="defutBtn05"  class="DTTT_button box" onclick="doSearchCustomer('05');">
						5</a>
					<a id="defutBtn06"  class="DTTT_button box" onclick="doSearchCustomer('06');">
						6</a>
					<a id="defutBtn07"  class="DTTT_button box" onclick="doSearchCustomer('07');">
						7</a>
					<a id="defutBtn08"  class="DTTT_button box" onclick="doSearchCustomer('08');">
						8</a>
					<a id="defutBtn09"  class="DTTT_button box" onclick="doSearchCustomer('09');">
						9</a>
					<a id="defutBtn10"  class="DTTT_button box" onclick="doSearchCustomer('10');">
						10</a>
					<a id="defutBtn11"  class="DTTT_button box" onclick="doSearchCustomer('11');">
						11</a>
					<a id="defutBtnALL"  class="DTTT_button box" onclick="doSearchCustomer('ALL');">
						ALL</a>
				</span>
						 
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td width="50px"></td>
			<td width="" class="label">业务组：</td>
			<td colspan="">
				<span id="userFlag2">
					<c:forEach var='list' items='${yewuzu}' varStatus='status'>
						<a id="defutBtnu${list.dicId }" style="height: 15px;margin-top: 5px;" 
							class="DTTT_button box3" onclick="doSelectUserId('${list.dicId }');">
							<span>${list.dicName }</span></a>
					</c:forEach>
				</span>			
			</td> 
			<td width="100px"></td>
			<td class="label"></td>
			<td colspan="">
										 
			</td>
		</tr>
	</table>

		</form>
	</div>
	<div style="height:10px"></div>

	<div class="list">	
	<!-- 		
		<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:left">
			<a class="DTTT_button DTTT_button_text box" onclick="doSearch22(1,'010');" id="defutBtn010"><span>待收款</span></a>
			<a class="DTTT_button DTTT_button_text box" onclick="doSearch22(8,'020');" id="defutBtn020"><span>部分收款</span></a>
			<a class="DTTT_button DTTT_button_text box" onclick="doSearch22(8,'030');" id="defutBtn030"><span>已收款</span></a>&nbsp;&nbsp;
			<a class="DTTT_button DTTT_button_text box" onclick="doSearch22(8,'070');" id="defutBtn070"><span>逾期未收款</span></a>
			<a class="DTTT_button DTTT_button_text" onclick="downloadExcel('080');" ><span>EXCEL导出</span></a>
		</div>
		  -->
		  <div id="" align="left" style="height:40px;width:70%;float: left;">
			<table>
				<tr style="font-size: 13px;font-weight: bold;">
					<td width="200px">USD 应收合计:<span id="usdOrderSum" ></span></td>
					<td width="200px">USD 已收合计:<span id="usdActuaSum" ></span></td>
					<td width="200px">USD 剩余合计:<span id="usdSurplSum" ></span></td>
				</tr>
				<tr style="font-size: 13px;font-weight: bold;">
					<td width="">RMB 应收合计:<span id="rmbOrderSum"></span></td>
					<td width="">RMB 已收合计:<span id="rmbActuaSum" ></span></td>
					<td width="">RMB 剩余合计:<span id="rmbSurplSum" ></span></td>
				</tr>
			</table>
		</div>	
		<div style="height: 40px;margin-bottom: -15px;float:right">
			<a class="DTTT_button DTTT_button_text" onclick="doCreate();">合并收款</a>
		</div>
		<table style="width: 100%;" id="TMaterial" class="display">
			<thead>						
				<tr>					
					<th width="55px">
						<input type="checkbox" name="selectall" id="selectall" onclick="fnselectall()"/><label for="selectall">全选</label><br>
						<input type="checkbox" name="reverse" id="reverse" onclick="fnreverse()" /><label for="reverse">反选</label></th>
					<th width="60px">收款编号</th>
					<th width="60px">耀升编号</th>							
					<th width="70px">产品编号</th>						
					<th>产品名称</th>
					<th width="50px">客户编号</th>
					<th width="60px">应收金额</th>
					<th width="60px">已收款</th>
					<th width="60px">约定收款日</th>
					<th width="60px">业务组</th>
					<th width="50px">收款状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
