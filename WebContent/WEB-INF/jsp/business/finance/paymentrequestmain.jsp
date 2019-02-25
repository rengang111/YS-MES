<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>应付款--申请一览</title>
<script type="text/javascript">

	function ajax(action,type,scrollHeight,sessionFlag,SearchFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var userId     = $("#userId").val();
		var url = "${ctx}/business/payment?methodtype=search";
		url = url + "&sessionFlag="+sessionFlag;
		url = url + "&finishStatus="+type;
		url = url + "&searchType=" + type;
		url = url + "&userId="+userId;

		var colSort = 4;
		if(type == '070')
			colSort = 9;
		
		var hideCol = 11;
		if(SearchFlag == "S")
			hideCol = '';
			
		var t = $('#TMaterial').DataTable({
				"paging": action,
				"iDisplayLength" : 300,
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
				"scrollY":scrollHeight,
				"scrollCollapse":false,
				 "aaSorting": [[ colSort, "asc" ]],
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

						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null,"className" : 'td-left'},//0
					{"data": null, "defaultContent" : '', "className" : 'td-left'},//1 付款申请编号
					{"data": "contractId", "defaultContent" : '', "className" : 'td-left'},//2
					{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},//3				
					{"data": "supplierId", "defaultContent" : '', "className" : 'td-left'},//4		
					{"data": "supplierName", "defaultContent" : ''},//5	
					{"data": "purchaserName", "defaultContent" : '***'},//6
					{"data": "totalPrice", "defaultContent" : '0', "className" : 'td-right'},//7合同金额
					{"data": "chargeback", "defaultContent" : '0', "className" : 'td-right'},//8合同扣款
					{"data": "stockInDate", "className" : 'td-center'},//9入库时间
					{"data": "invoiceDate", "defaultContent" : '***',"className" : 'td-center'},//10发票日期
					{"data": "finishStatus", "className" : 'td-center'},//11
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			var paymentId = row["paymentId"];
		    			if(paymentId == ""){
							return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["contractId"] + "' />";		    				
		    			}else{
							return row["rownum"];
		    			}
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = row["paymentId"];//
		    			if(rtn == ""){
				    		rtn= "<a href=\"###\" onClick=\"doCreate2('" + row["contractId"] +"')\">" + "快速申请" + "</a>";
		    			}else{
			    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["contractId"] + "','"+ row["paymentId"] + "')\">" + row["paymentId"] + "</a>";
		    			}
		    			
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			var rtn = "";
		    			var contractId = jQuery.fixedWidth(row["contractId"],16);
		    			rtn= "<a href=\"###\" onClick=\"doShowContract('" + row["contractId"] +"')\">" + contractId + "</a>";
		    			return rtn;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    					    			
		    			return jQuery.fixedWidth(data,12);
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    					    			
		    			return jQuery.fixedWidth(data,24);
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			return floatToCurrency(data);
		    		}},
		    		{ "bSortable": false, "aTargets": [ 0 ] },
		    		{
						"visible" : false,
						"targets" : [hideCol]
					}
	         ] 
		});
	}

	$(document).ready(function() {
	
		var scrollHeight = $(document).height() - 200; 
		var searchType = $('#searchType').val();
		var searchSts = $("#searchType").val();

		hideAllSearch();

		setYearList();

		var userId    = $('#userId').val();
		var month     = $('#month').val();
		var monthday  = $('#monthday').val();
		
		if(searchSts == '2'){//已收货
			$('#yearFlag').show();
			monthday = shortToday();
		}else{
			 monthday = '';
		}
		//var deliveryDate = '';
		//if(searchSts == '0'){//逾期未到货
		//	 deliveryDate = shortToday();
		//}

		//searchAjax(searchSts,"true",'',monthday);
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		buttonSelectedEvent3();//按钮选择式样

		$('#defutBtn'+month).removeClass("start").addClass("end");	
		$('#defutBtnu'+userId).removeClass("start").addClass("end");	
		$('#defutBtnm'+searchSts).removeClass("start").addClass("end");	

		
		ajax(monthday,searchSts,scrollHeight,"true","R");
		
		$("#year").change(function() {
						
			var year  = $('#year').val();
			var currYear = getYear();
			
			if(year == currYear){//当前年份
				var month = getMonth();//$('#month').val();
			
			}else{//其他年份

				var month = '12';//默认是年末
			}
			
			var monthday = year +"-"+month;
			$('#monthday').val(monthday);

			var collection = $(".box");
		    $.each(collection, function () {
		    	$(this).removeClass("end");
		    });
		    
		 	$('#defutBtn'+month).removeClass("start").addClass("end");
		 	
		 	searchAjax('2','false','',monthday);
	
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
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		var scrollHeight = $(document).height() - 200; 
		
		ajax("false","",scrollHeight,"false","S");

	}
	
	function doSearch2(colNum,type) {	

		//$("#keyword1").val("");
		//$("#keyword2").val("");
		
		$("#searchType").val(type);
		var scrollHeight = $(document).height() - 200; 
		
		ajax("false",type,scrollHeight,"false","C");

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
		location.href = url;
		
	}
	
	function doCreate2(contractId) {

		var paymentTypeId = $("#paymentTypeId").val();
		var searchType = $("#searchType").val();
		
		var url = '${ctx}/business/payment?methodtype=addinit';
		url = url +"&contractIds="+contractId;
		url = url +"&paymentTypeId="+paymentTypeId;
		url = url +"&searchType="+searchType;
		location.href = url;
		
	}
	
	function doShowDetail(contractId,paymentId) {

		var url = '${ctx}/business/payment?methodtype=paymentView&contractId=' + contractId+'&paymentId='+paymentId;

		url = url +"&searchType="+searchType;
		
		location.href = url;
	}

	function doShowContract(contractId) {

		var searchType = $("#searchType").val();
		
		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("采购合同",url);
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
		$('#year').html(options);
		$('#year').val(curYear);//默认显示当前年
	}
	
	//已付款
	function doSearchCustomer3(searchSts,sessionFlag){

		hideAllSearch();
		$('#yearFlag').show();

		var monthday = getYearMonth();
		var monthonly = getMonth();
		var userId = $('#userId').val();
		
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
		$("#searchSts").val(searchSts);	
		$("#monthday").val(monthday);	
		$("#month").val(monthonly);	

		ajax("false",searchSts,scrollHeight,"false","C");
	 	//searchAjax(searchSts,'false','',monthday);		
	}
		
	
	//未付款
	function selectContractByDate(searchSts,sessionFlag){
		
		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });

		var collection = $(".box3");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });

		var userId = $('#userId').val();
		$("#searchSts").val(searchSts);
	 	$('#defutBtnu'+userId).removeClass("start").addClass("end");
	 	$('#defutBtnm'+searchSts).removeClass("start").addClass("end");

		hideAllSearch();

		$("#searchType").val(type);
		var scrollHeight = $(document).height() - 200; 
		
		ajax("false",searchSts,scrollHeight,"false","C");

	}
		
	function hideAllSearch(){

		$('#yearFlag').hide();
		//$('#userFlag').hide();
	}
	//月份选择
	function doSearchCustomer(month){
				
		var year = $('#year').val();
		if(month == 'ALL'){
			var monthday = '';
		}else{
			var monthday = year +"-"+month;
		}
		$('#monthday').val(monthday);
		$('#month').val(month);

		if(month == '12'){
			var crrMonth = getMonth();
			if(month == crrMonth){
				//当前就是12月				
			}else{
				var CurrYear = getYear();
				CurrYear = CurrYear - 1;
				monthday = CurrYear - 1 +"-"+month; 
				$('#year').val(CurrYear);
			}
		}
		searchAjax('2','false','',monthday);
	}
	
	//采购员选择
	function doSelectUserId(userId){

		var monthday  = $('#monthday').val();
		var searchSts = $('#searchSts').val();
		
		if(searchSts == '2'){
			//已入库
		}else{
			
		}
	 	$('#defutBtnu'+userId).removeClass("start").addClass("end");
	 	$('#defutBtnm'+searchSts).removeClass("start").addClass("end");
	 	
		$('#userId').val(userId);

		searchAjax(searchSts,'false','',monthday);
	}
	
	
</script>
</head>

<body>
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition"  style='padding: 0px; margin: 10px;' >
										
					<input type="hidden" id="paymentTypeId" value="010"><!-- 正常付款 -->
					<input type="hidden" id="searchType" value="${searchType }"><!-- 快速查询按钮 -->
					<input type="hidden" id="methodtype" value="${methodtype }" />
					<input type="hidden" id="makeType"   value="${makeType }" />
					<input type="hidden" id="searchSts"  value="${searchSts }" />
					<input type="hidden" id="userId"     value="${userId }" />
					<input type="hidden" id="methodkey"  value="${methodkey }" />
					<input type="hidden" id="monthday" name="monthday" value="" />
					<input type="hidden" id="month"    name="month"    value="" />

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
							<td width=""></td> 
						</tr>
					</table>

				</form>
			</div>
			<table>
				<tr style="height: 25px;">
					<td width=""></td> 
					<td class="label">付款情况：</td>
					<td colspan="4">
						<a id="defutBtnm0" class="DTTT_button box2" onclick="selectContractByDate2('0',11);">ALL</a>
						<a id="defutBtnm1" class="DTTT_button box2" onclick="selectContractByDate('1',11);">未付款</a>
						<a id="defutBtnm2" class="DTTT_button box2" onclick="doSearchCustomer3('2','');" >已付款</a>
						
						<span id="yearFlag">			
							<select id="year" name="year"  style="width: 100px;vertical-align: bottom;height: 25px;"></select>
							
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
					<td width="" class="label">采购人员：</td>
					<td colspan="">
						<span id="userFlag2">
							<c:forEach var='list' items='${purchaser}' varStatus='status'>
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
		
			<div class="list">					
				<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:left">
					<a class="DTTT_button DTTT_button_text box" onclick="doSearch2(1,'010');" id="defutBtn010"><span>待申请</span></a>
					<a class="DTTT_button DTTT_button_text box" onclick="doSearch2(8,'020');" id="defutBtn020"><span>待审核</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="downloadExcel('080');" ><span>EXCEL导出</span></a>
				</div>
				<div style="height: 40px;margin-bottom: -15px;float:right">
					<a class="DTTT_button DTTT_button_text" onclick="doCreate();">付款申请</a>
				</div>
				<table style="width: 100%;" id="TMaterial" class="display">
					<thead>						
						<tr>					
							<th width="50px">
								<input type="checkbox" name="selectall" id="selectall" onclick="fnselectall()"/><label for="selectall">全选</label><br>
								<input type="checkbox" name="reverse" id="reverse" onclick="fnreverse()" /><label for="reverse">反选</label></th>
							<th width="65px">付款编号</th>
							<th width="70px">合同编号</th>
							<th width="60px">耀升编号</th>							
							<th width="70px">供应商编号</th>						
							<th>供应商名称</th>
							<th width="36px">采购</th>
							<th width="60px">合同金额</th>
							<th width="50px">合同扣款</th>
							<th width="60px">入库日期</th>
							<th width="60px">发票日期</th>
							<th width="50px">付款状态</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
