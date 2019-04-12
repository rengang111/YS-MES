<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>应付款--异常付款查询</title>
<style>
body{
	font-size:11px;
}
</style>
<script type="text/javascript">

	function searchAjax(scrollHeight,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var searchType = $('#searchType').val();
		var userId     = $("#userId").val();
		var year       = $("#year").val();
		var monthday   = $("#monthday").val();
		var groupFlag  = $("#groupFlag").val();
		var url = "${ctx}/business/payment?methodtype=paymentAbnormalMain";
		url = url + "&sessionFlag=" + sessionFlag;
		url = url + "&searchType="+ searchType;
		url = url + "&monthday="    + monthday;
		url = url + "&groupFlag="   + groupFlag;
		url = url + "&userId="+ userId;
		url = url + "&year="  + year;
	
		var t = $('#TMaterial').DataTable({
				"paging": true,
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
							fnCallback(data);
							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);

							$("input[aria-controls='TMaterial']").css("height","25px");
							$("input[aria-controls='TMaterial']").css("width","200px");
							$("#TMaterial_filter").css("float","left");
							
							var  groupFlag = $('#groupFlag').val();
							$('.box3').removeClass("end");
							/*
							var collection = $(".box");
						    $.each(collection, function () {
						    	$(this).removeClass("end");
						    });
						    */
							$('#defutBtnF'+groupFlag).removeClass("start").addClass("end");

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
					{"data": null, "defaultContent" : '', "className" : 'td-'},//1 付款申请编号
					{"data": "contractId", "defaultContent" : '', "className" : 'td-'},//2 合同编号
					{"data": "YSId", "defaultContent" : '', "className" : 'td-'},//3 耀升编号
					{"data": "supplierId", "defaultContent" : '', "className" : 'td-'},//4	供应商
					{"data": "supplierName", "defaultContent" : ''},//5	供应商名称
					{"data": "YSId", "defaultContent" : '***', "className" : 'td-center'},//6 采购员
					{"data": "totalPayable", "defaultContent" : '0', "className" : 'td-right'},//7应付款总金额
					{"data": "invoiceAmount", "defaultContent" : '0', "className" : 'td-right'},//8 发票金额合计
					{"data": "paymentAmount", "defaultContent" : '0', "className" : 'td-right'},//9 已付款
					{"data": "", "className" : 'td-center'},//10 剩余金额
					{"data": "requestDate", "defaultContent" : '***',"className" : 'td-center'},//11 申请日期
					{"data": "finishStatus", "className" : 'td-center'},//12 付款状态
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			var text = row["rownum"];		    			
		    			return text;		    			
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var paymentId = row["paymentId"];//
		    			var rtn = "待申请";
		    			var stockinQty = currencyToFloat(row['stockinQty']);
		    			var contractQty = currencyToFloat(row['contractQty']);
		    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["contractId"] + "','"+ row["paymentId"] + "')\">" + row["paymentId"] + "</a>";
		    					    					    			
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			var rtn = "";
		    			var contractId = jQuery.fixedWidth(row["contractId"],16);
		    			rtn= "<a href=\"###\" onClick=\"doShowContract('" + row["contractId"] +"')\">" + data + "</a>";
		    			
		    			//var groupFlag = $('#groupFlag').val();
		    			//if(groupFlag == 'S'){
		    			//	rtn = '<div style="text-align: center;">***</div>';
		    			//}
		    			return rtn;
		    		}},
		    		{"targets":3,"render":function(data, type, row){

		    			//var groupFlag = $('#groupFlag').val();
		    			//if(groupFlag == 'S'){
		    			//	return  '<div style="text-align: center;">***</div>';
		    			//}else{
			    			return jQuery.fixedWidth(data,12);
		    			//}		    				
		    		}},
		    		{"targets":4,"render":function(data, type, row){

		    			//供应商编号
		    			var groupFlag = $('#groupFlag').val();
		    			//if(groupFlag == 'S'){
		    			//	return  '<div style="text-align: center;">***</div>';
		    			//}else{
			    			return jQuery.fixedWidth(data,12);
		    			//}
		    		}},
		    		{"targets":5,"render":function(data, type, row){
						//供应商名称
		    			var groupFlag = $('#groupFlag').val();
		    			//if(groupFlag == 'S'){
		    			//	return  '<div style="text-align: center;">***</div>';
		    			//}else{
			    			return jQuery.fixedWidth(data,32);
		    			//}
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			
		    			if(data == null || data == '')
		    				return '-';
		    			else
		    				return data;
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			var totalPayable   = currencyToFloat(row['totalPayable']);
		    			var invoiceAmount   = currencyToFloat(row['invoiceAmount']);
		    			var rtn = floatToCurrency(invoiceAmount);
		    			if(invoiceAmount < totalPayable){
		    				rtn = '<span class="error">'+rtn+'</span>';
		    			}
		    			return rtn;
		    		}},
		    		{"targets":10,"render":function(data, type, row){
		    			var stockInStatus   = row['stockInStatus'];

		    			var rtn = '已入库';
						if(stockInStatus == 'N'){
							rtn = '<span class="error">未入库</span>';
						}
		    			
		    			return rtn
		    		}},
		    		{"targets":12,"render":function(data, type, row){
		    			
		    			var staN = row['finishStatus'];
		    			var sts  = row['finishStatusId'];
		    		
		    			if(sts ==  '050'){
		    				staN = row['finishDate'];
		    			}
		    			
		    			return staN;
		    		}},
		    		{ "bSortable": false, "aTargets": [ 0 ] },
		    		{
						"visible" : false,
						"targets" : [6]
					}
	         ] 
		});
	}

	
	$(document).ready(function() {
	
		hideAllSearch();
		setYearList();

		var scrollHeight = $(document).height() - 200; 
		var searchType = $("#searchType").val();
		var userId    = $('#userId').val();
		var month     = $('#month').val();
		var monthday  = $('#monthday').val();
		
		if(searchType == '6'){//已收货
			$('#yearFlag').show();
			monthday = shortToday();
		}else{
			 monthday = '';
		}
		var currYear = getYear();
		$('#year').val(currYear);
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		buttonSelectedEvent3();//按钮选择式样
		buttonSelectedEvent4();//按钮选择式样

		$('#defutBtn'+month).removeClass("start").addClass("end");	
		$('#defutBtnFS').removeClass("start").addClass("end");	
		$('#defutBtnm'+searchType).removeClass("start").addClass("end");
		$('#defutBtny'+currYear).removeClass("start").addClass("end");		

		
		searchAjax(scrollHeight,"true");
	
		/*	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			$(this).toggleClass("selected");
		    if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
		        
		    	$(this).children().first().children().prop("checked", true);
		    }else{//如果没有被选中

		    	$(this).children().first().children().prop("checked", false);
		    }	
		    
		});	
*/
		  $(".checkbox").click(function () {
			 // alert(111)
              //获取checkbox选中项
              if ($(this).prop("checked") == true) {
                  $(this).parent().parent().css("background", "#b2dba1");
              } else {
                  $(this).parent().parent().css("background", "");
              }
          });
          
		  $(document).on('click','.checkbox',function(){
			 // alert(222)
              //获取checkbox选中项
              if ($(this).prop("checked") == true) {
            	  $(this).parent().parent().addClass("selected");
                 // $(this).parent().parent().css("background", "selected");
              } else {
            	  $(this).parent().parent().removeClass("selected")
                 // $(this).parent().parent().css("background", "");
              }
			  
		  });
		  
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
			 	
			 	searchAjax('','false');
		
			});

	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		var scrollHeight = $(document).height() - 200; 
		
		searchAjax(scrollHeight,"false");

	}
	
	function doSearch2(colNum,type) {	

		//$("#keyword1").val("");
		//$("#keyword2").val("");
		
		$("#searchType").val(type);
		var scrollHeight = $(document).height() - 200; 
		
		searchAjax(scrollHeight,"false");

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
					supplierId = $(this).parent().parent().find('td').eq(4).text();
					supplierId_next = $(this).parent().parent().find('td').eq(4).text();
					flag = false;
				}else{
					supplierId_next = $(this).parent().parent().find('td').eq(4).text();
					//alert(supplierId+"---"+supplierId_next)
					if(supplierId != supplierId_next){
					
						$().toastmessage('showWarningToast', "请选择同一个供应商。");

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
		var paymentTypeId = $("#paymentTypeId").val();	
		var url = '${ctx}/business/payment?methodtype=addinit';
		url = url +"&contractIds="+str;
		url = url +"&paymentTypeId="+paymentTypeId;
		//location.href = url;
		callWindowFullView("批量付款",url);
		
	}
	
	function doCreate2(contractId) {

		var paymentTypeId = $("#paymentTypeId").val();
		var searchType = $("#searchType").val();
		
		var url = '${ctx}/business/payment?methodtype=addinit';
		url = url +"&contractIds="+contractId;
		url = url +"&paymentTypeId="+paymentTypeId;
		url = url +"&searchType="+searchType;
		//location.href = url;
		
		callWindowFullView("付款管理申请",url);
		
	}
	
	function doShowDetail(contractId,paymentId) {
		
		var url = '${ctx}/business/payment?methodtype=paymentView&contractId=' + contractId+'&paymentId='+paymentId;
		
		var searchType = $("#searchType").val();
		
		url = url +"&searchType="+searchType;
		
		callWindowFullView("付款管理详情",url);
	}

	function doShowContract(contractId) {

		var searchType = $("#searchType").val();
		
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
		var searchType = $("#searchType").val();
		
		var url = '${ctx}/business/payment?methodtype=downloadExcelForPayment'
				
				 + "&key1=" + key1
				 + "&key2=" + key2
				 + "&finishStatus=070";
				 + "&searchType=" + searchType;
		
		url =encodeURI(encodeURI(url));//中文两次转码

		location.href = url;
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
	
	//已付款
	function doSearchCustomer3(searchType){

		hideAllSearch();
		$('#yearFlag').show();
		
		var monthday = '';//getYearMonth();
		var monthonly = 'ALL';//getMonth();
		var userId = $('#userId').val();
		
		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });		    
		
	 	$('#defutBtnm'+searchType).removeClass("start").addClass("end");
	 	$('#defutBtn'+monthonly).removeClass("start").addClass("end");
	 	$('#defutBtnu'+userId).removeClass("start").addClass("end");

		var curYear = getYear();
		$('#year').val(curYear);//默认显示当前年
		$('#yearList').val(curYear);//默认显示当前年
		$("#searchType").val(searchType);	
		$("#monthday").val(monthday);	
		$("#month").val(monthonly);	

		searchAjax("","false");	
	}
		
	
	//未付款
	function selectContractByDate(searchType){
		
		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });

		var collection = $(".box3");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });

		var userId = $('#userId').val();
		$("#searchType").val(searchType);
	 	$('#defutBtnu'+userId).removeClass("start").addClass("end");
	 	$('#defutBtnm'+searchType).removeClass("start").addClass("end");

		hideAllSearch();

		$("#searchType").val(searchType);
		var scrollHeight = $(document).height() - 200; 
		
		searchAjax(scrollHeight,"false");

	}
		
	function hideAllSearch(){

		$('#yearFlag').hide();
		//$('#userFlag').hide();
	}

	//年度选择
	function doSelectYear(year) {
		
		var searchType = $('#searchType').val();
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    var month = $('#month').val();
	    var monthday = year + '-' + month;

	    $('#year').val(year); 
	    $('#monthday').val(monthday);
	 	$('#defutBtn'+month).removeClass("start").addClass("end"); 
	    
	 	searchAjax('','false');

	};
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
		
		searchAjax('false','');
	}
	
	//采购员选择
	function doSelectUserId(userId){

		var monthday  = $('#monthday').val();
		var searchType = $('#searchType').val();
		
		if(searchType == '2'){
			//已入库
		}else{
			
		}
	 	$('#defutBtnu'+userId).removeClass("start").addClass("end");
	 	$('#defutBtnm'+searchType).removeClass("start").addClass("end");
	 	
		$('#userId').val(userId);

		searchAjax('false','');
	}
	

	//展开，收起
	function doGroupByContract(groupFlag){

		$('#groupFlag').val(groupFlag);

		searchAjax('false','');
	}
	
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
						
			<input type="hidden" id="groupFlag"     value="S" /><!-- 展开收起集计Flag,默认收起 -->				
			<input type="hidden" id="paymentTypeId" value="010">		<!-- 正常付款 -->
			<input type="hidden" id="searchType" value="${searchType }"><!-- 付款情况 -->
			<input type="hidden" id="userId"     value="${userId }" />	<!-- 采购员 -->
			<input type="hidden" id="monthday" name="monthday" value="" /><!-- 年月 -->
			<input type="hidden" id="month"    name="month"    value="" /><!-- 月 -->
			<input type="hidden" id="year"     name="year"     value="" /><!-- 年 -->

			<table>
				<tr>
					<td width="20px"></td> 
					<td class="label" width="60px">关键字1：</td>
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
					<td width=""></td> 
				</tr>
				<tr style="height: 25px;">
					<td width=""></td> 
					<td class="label">付款情况：</td>
					<td colspan="4">
						<!-- a id="defutBtnm0" class="DTTT_button box2" onclick="selectContractByDate('0');">ALL</a -->
						<a id="defutBtnmF" class="DTTT_button box2" onclick="selectContractByDate('F');">发票未齐</a>
						<a id="defutBtnmH" class="DTTT_button box2" onclick="selectContractByDate('H');">合同入库未齐</a>
						
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
				<!-- 
				<tr>
					<td width="50px"></td>
					<td width="" class="label">采购人员：</td>
					<td colspan="4">
						<span id="userFlag2">
							<c:forEach var='list' items='${purchaser}' varStatus='status'>
								<a id="defutBtnu${list.dicId }" style="height: 15px;margin-top: 5px;" 
									class="DTTT_button box3" onclick="doSelectUserId('${list.dicId }');">
									<span>${list.dicName }</span></a>
							</c:forEach>
						</span>			
					</td> 
				</tr>
				 -->
			</table>

		</form>
	</div>
	<table>
	</table>
	<div class="list">
	<!-- 
		<div style="height: 40px;margin-bottom: -10px;">
			<a class="DTTT_button box3" onclick="doGroupByContract('F');" id="defutBtnFZ">&nbsp;发票不齐&nbsp;</a>
			<a class="DTTT_button box3" onclick="doGroupByContract('H');" id="defutBtnFS">&nbsp;合同未入库&nbsp;</a>
		</div>
		 -->
		<table style="width: 100%;" id="TMaterial" class="display">
			<thead>
				<tr>				
					<th width="10px">No</th>
					<th width="55px">付款编号</th>
					<th width="80px">合同编号</th>
					<th width="60px">耀升编号</th>							
					<th width="60px">供应商编号</th>						
					<th>供应商名称</th>
					<th width="30px">采购</th>
					<th width="50px">应付款</th>
					<th width="50px">发票金额</th>
					<th width="50px">已付款</th>
					<th width="50px">合同入库</th>
					<th width="50px">申请日期</th>
					<th width="50px">付款状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
