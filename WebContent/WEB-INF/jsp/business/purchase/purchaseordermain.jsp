<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>采购合同一览</title>
<style>
body{
	font-size:10px;
}
</style>
<script type="text/javascript">

	function searchAjax(searchSts,sessionFlag,deliveryDate,monthday) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var methodtype = $('#methodtype').val();
		var makeType   = $('#makeType').val();
		var userId     = $("#userId").val();
		var year       = $("#year").val();
		var actionUrl  = "${ctx}/business/contract?methodtype="+methodtype;
		actionUrl = actionUrl +	"&sessionFlag="+sessionFlag;;
		actionUrl = actionUrl + "&deliveryDate="+deliveryDate;
		actionUrl = actionUrl + "&monthday="+monthday;
		actionUrl = actionUrl + "&makeType="+makeType;
		actionUrl = actionUrl + "&status="+searchSts;
		actionUrl = actionUrl + "&userId="+userId;
		actionUrl = actionUrl + "&year="+year;
		
		var t = $('#TMaterial').DataTable({
			"paging": true,
			 "iDisplayLength" : 50,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"pagingType" : "full_numbers",
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
						fnCallback(data);
						var key1 = data["keyword1"]
						var key2 = data["keyword2"]
						$("#keyword1").val(key1);
						$("#keyword2").val(key2);
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns": [
				{"data": null,"className" : 'td-center'},
				{"data": "contractId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "materialId","className" : 'td-left'},
				{"data": "purchaserName", "defaultContent" : '-',"className" : 'td-left'},//3
				{"data": "materialName", "defaultContent" : ''},//4
				{"data": "YSId", "defaultContent" : ''},//5
				{"data": "supplierId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "deliveryDate", "defaultContent" : ''},
				{"data": "quantity", "defaultContent" : '0',"className" : 'td-right'},//合同金额
				{"data": null, "defaultContent" : '0',"className" : 'td-center'},//收货状态
				{"data": null, "defaultContent" : '0',"className" : 'td-center'},//付款状态
				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
	    			var followFlag = row["followStatus"];
	    			var YSId = row["YSId"];	var imgName = "pixel"; var altMsg="置顶";
	    			if(followFlag == '0'){
	    				imgName = "icon-top";
			    		return '<input type="image" title="'+altMsg+'" style="border: 0px;" src="${ctx}/images/'+imgName+'.png" />';
	    			}else{
			    		return row["rownum"];
	    				
	    			}
	    			
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			
	    			return "<a href=\"###\" onClick=\"doShowControct('" + row["contractId"] + "','" + row["quantity"] + "','" + row["arrivalQty"] + "','" + row["contractStorage"] + "')\">"+row["contractId"]+"</a>";			    			
	    		}},
	    		{"targets":2,"render":function(data, type, row){			
	    			var rtn=data;
	    			if(data.length > 20){
	    				rtn = '<div  title="'+data+'" style="font-size: 9px;">'+data+'</div>';	
	    			}
	    			return  "<a href=\"###\" onClick=\"doShow('" + row["materialParentId"] + "','" + row["materialRecordId"] + "','" + row["materialId"] + "')\">"+data+"</a>";
	    			//return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			//采购员
	    			if(data == "" || data == null){
	    				return  "-";	
	    			}else{				
	    				return data;
	    			}	    			
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			var name = row["materialName"];				    			
	    			if(name != null) name = jQuery.fixedWidth(name,35);
	    			return name;
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			var rtn="";var ysid=row["YSId"];
	    			if(ysid == ""){
	    				return  "日常采购";	
	    			}else{				
	    				return ysid;
	    			}	    			
	    		}},
	    		{"targets":8,"render":function(data, type, row){	//金额		
	    			
	    			return data;
	    		}},
	    		{"targets":9,"render":function(data, type, row){
	    			//收货状态
	    			var contractQty = currencyToFloat(row['quantity']);
	    			var storageQty  = currencyToFloat(row['contractStorage']);
	    			var storageSts = '未入库';
	    			if(storageQty >= contractQty){
	    				storageSts = '已入库';
	    				storageSts = row['stockInDate'];
	    			}else if(storageQty > 0){
	    				storageSts = floatToCurrency(row['contractStorage']);
	    			}
	    			return storageSts;
	    		}},
	    		{"targets":10,"render":function(data, type, row){
	    			//付款状态
	    			var contractQty = currencyToFloat(row['quantity']);
	    			var storageQty  = currencyToFloat(row['contractStorage']);
	    			var storageSts = '0';
	    			if(storageQty >= contractQty){
	    				storageSts = '1';
	    			}
	    			
	    			var finishStatus = row['paymentSts'];
	    			var paymentSts = '未付款';
	    			if(finishStatus == '050'){
	    				paymentSts = '已付款';
	    			}else if(finishStatus == '040'){
	    				paymentSts = '部分付款';
	    			}else{
	    				//if(storageQty >= contractQty){
		    				//已入库
		    				//paymentSts = "<a href=\"###\" onClick=\"doPaymentRequest('" + row["supplierId"] + "','" + row["contractId"] + "')\">"+paymentSts+"</a>";
		    			//}
	    			}
	    			return paymentSts;
	    		}}
	    		/*
	    		{"targets":11,"render":function(data, type, row){
	    			var followFlag = row["followStatus"];var YSId = row["YSId"];var contractId = row["contractId"];
	    			var text = "icon-top2"; var color = "red";
	    			if(followFlag == '0'){
						text = '撤销';
	    			}else{
						text = '置顶';color = "blue";
	    			}
	    			return '<a t href=\"###\"  onclick="setFollow(\''+YSId+'\',\''+contractId+'\');return false;" style="color: '+color+';">'+text+'</a>';
	    		}}
	    		*/
         	] 
		});
		
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
	
	function doShow(parentid,recordid,materialId) {

		//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
		var url = '${ctx}/business/material?methodtype=detailView';
		url = url + '&parentId=' + parentid+'&recordId='+recordid+'&materialId='+materialId+'&keyBackup=1';
		
		callWindowFullView("物料信息",url);
	}
	
	function setFollow(YSId,contractId){
		
		$.ajax({
			type : "post",
			url : "${ctx}/business/contract?methodtype=setContractFollow"+"&YSId="+YSId+"&contractId="+contractId,
			async : false,
			data : 'key=' + YSId,
			dataType : "json",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				//var jsonObj = data;
				status = data["status"];
				if(status == '0'){
					$().toastmessage('showNoticeToast', "置顶成功。");
				}else{
					$().toastmessage('showNoticeToast', "取消置顶。");
				}
			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {
			}
		});
		
		$('#TMaterial').DataTable().ajax.reload(false);
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
	
	$(document).ready(function() {
		
		hideAllSearch();

		setYearList();

		var searchSts = $('#searchSts').val();
		var userId    = $('#userId').val();
		var month     = $('#month').val();
		var monthday  = $('#monthday').val();
		
		if(searchSts == '2'){//已收货
			$('#yearFlag').show();
			monthday = shortToday();
		}else{
			 monthday = '';
		}
		var currYear = getYear();
		$('#year').val(currYear);
		searchAjax(searchSts,"true",'',monthday);
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		buttonSelectedEvent3();//按钮选择式样
		buttonSelectedEvent4();//按钮选择式样

		$('#defutBtn'+month).removeClass("start").addClass("end");	
		$('#defutBtnu'+userId).removeClass("start").addClass("end");	
		$('#defutBtnm'+searchSts).removeClass("start").addClass("end");	
		$('#defutBtny'+currYear).removeClass("start").addClass("end");	
		
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
		
	})	
	
	

	function doShowControct(contractId,quantity,arrivalQty,stockinQty) {

		var deleteFlag = '1';
		var editFlag = '1';
		if(currencyToFloat(arrivalQty) > 0){
			deleteFlag = '0';//已经在执行中,不能删除
		}
		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId+'&deleteFlag=' + deleteFlag;
		
		callWindowFullView("合同详情",url);
	}	
	
	function doPaymentRequest(supplierId,contractId) {

		var url = '${ctx}/business/payment?methodtype=paymentRequestBySupplier'
				+'&contractId=' + contractId
				+'&supplierId=' + supplierId;
		
		callWindowFullView("付款申请",url);
	}	

	function hideAllSearch(){

		$('#yearFlag').hide();
		//$('#userFlag').hide();
	}
	
	function doSearch() {	

		//hideAllSearch();
		
		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		var searchSts = $('#searchSts').val();
		var userId    = $('#userId').val();
		var month     = $('#month').val();
		var monthday  = $('#monthday').val();
		
		searchAjax(searchSts,"false",'',monthday);
		
		/*
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    collection = $(".box3");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    */
	}
	
	
	//已入库
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
		
	 	searchAjax(searchSts,'false','',monthday);		
	}
		
	//未入库已付款
	function selectContractByDate2(searchSts,hideCol){

		hideAllSearch();

		var deliveryDate = shortToday();
		
		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    
	 	$('#defutBtnm'+searchSts).removeClass("start").addClass("end");

		$("#searchSts").val(searchSts);
				
		searchAjax(searchSts,'false','','');
	
		
	}
	//未入库
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
				
		searchAjax(searchSts,'false','','');
	}
	//年度选择
	function doSelectYear(year) {
		
		var searchSts = $('#searchSts').val();
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    $('#year').val(year); 	
	 	searchAjax(searchSts,'false','','');

	};
	
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
				if(year == CurrYear){
					
					CurrYear = CurrYear - 1;
					monthday = CurrYear +"-"+month; 
					$('#year').val(CurrYear);
				}
				
				var collection = $(".box4");
			    $.each(collection, function () {
			    	$(this).removeClass("end");
			    });
				$('#defutBtny'+CurrYear).removeClass("start").addClass("end");
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
							style="width:50px" onclick="doSearch();">查询</button>
					</td>
					<td width=""></td> 
				</tr>
				<!-- 
				<tr style="height: 25px;">
					<td width="50px"></td>
					<td width="" class="label">年份选择：</td>
					<td colspan="">
						<span id="">
							<c:forEach var='list' items='${yearList}' varStatus='status'>
								<a id="defutBtny${list.dicId }" style="height: 15px;margin-top: 5px;" 
									class="DTTT_button box4" onclick="doSelectYear('${list.dicId }');">
									<span>${list.dicName }</span></a>
							</c:forEach>
						</span>			
					</td> 
					<td width="100px"></td>
					<td class="label"></td>
					<td colspan=""></td>
				</tr>
				 -->
				<tr style="height: 25px;">
					<td width=""></td> 
					<td class="label">到货情况：</td>
					<td colspan="4">
						<a id="defutBtnm0" class="DTTT_button box2" onclick="selectContractByDate2('0',11);">ALL</a>
						<a id="defutBtnm1" class="DTTT_button box2" onclick="selectContractByDate('1',11);">未入库</a>
						<a id="defutBtnm3" class="DTTT_button box2" onclick="selectContractByDate2('3',11);">未入库已付款</a>
						<a id="defutBtnm2" class="DTTT_button box2" onclick="doSearchCustomer3('2','');" >已入库</a>
						
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

		</form>
	</div>
	
	<div class="list">
		<table id="TMaterial" class="display" >
			<thead>						
				<tr>
					<th style="width: 1px;">No</th>
					<th style="width: 80px;">合同编号</th>
					<th style="width: 100px;">物料编号</th>
					<th style="width: 20px;">采购</th>
					<th>物料名称</th>
					<th style="width: 70px;">耀升编号</th>
					<th style="width: 60px;">供应商</th>
					<th style="width: 55px;">合同交期</th>
					<th style="width: 50px;">合同数量</th>
					<th style="width: 40px;">收货</th>
					<th style="width: 40px;">付款</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
