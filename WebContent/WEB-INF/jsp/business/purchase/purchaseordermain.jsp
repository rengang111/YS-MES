<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>采购合同一览</title>
<script type="text/javascript">

	function searchAjax(status,sessionFlag,deliveryDate,monthday) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var methodtype = $('#methodtype').val();
		var makeType   = $('#makeType').val();
		var actionUrl  = "${ctx}/business/contract?methodtype="+methodtype;
		actionUrl = actionUrl +	"&sessionFlag="+sessionFlag;;
		actionUrl = actionUrl + "&deliveryDate="+deliveryDate;
		actionUrl = actionUrl + "&monthday="+monthday;
		actionUrl = actionUrl + "&makeType="+makeType;
		actionUrl = actionUrl + "&status="+status;
		
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
						/*
						if(myTrim(key1) == "" && myTrim(key2) == ""){
						 	$('#defutBtn').removeClass("start").addClass("end");							
						}else{							
						 	$('#defutBtn').removeClass("end").addClass("start");
						}*/
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
				{"data": "materialName", "defaultContent" : ''},
				{"data": "YSId", "defaultContent" : ''},
				{"data": "supplierId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "deliveryDate", "defaultContent" : ''},
				{"data": "totalPrice", "defaultContent" : '0',"className" : 'td-right'},//合同金额
				{"data": null, "defaultContent" : '0',"className" : 'td-center'},//收货状态
				{"data": null, "defaultContent" : '0',"className" : 'td-center'},//付款状态
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":2,"render":function(data, type, row){			
	    			var rtn=data;
	    			if(data.length > 20){
	    				rtn = '<div  title="'+data+'" style="font-size: 9px;">'+data+'</div>';	
	    			}
	    			return rtn;
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			var rtn="";var ysid=row["YSId"];
	    			if(ysid == ""){
	    				return  "日常采购";	
	    			}else{				
	    				return ysid;
	    			}	    			
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			
	    			return "<a href=\"###\" onClick=\"doShowControct('" + row["contractId"] + "','" + row["quantity"] + "','" + row["arrivalQty"] + "','" + row["contractStorage"] + "')\">"+row["contractId"]+"</a>";			    			
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = row["materialName"];				    			
	    			if(name != null) name = jQuery.fixedWidth(name,35);
	    			return name;
	    		}},
	    		{"targets":8,"render":function(data, type, row){
	    			//收货状态
	    			var contractQty = currencyToFloat(row['quantity']);
	    			var storageQty  = currencyToFloat(row['contractStorage']);
	    			var storageSts = '未收货';
	    			if(storageQty >= contractQty){
	    				storageSts = '已收货';
	    			}else if(storageQty > 0){
	    				storageSts = '部分收货';
	    			}
	    			return storageSts;
	    		}},
	    		{"targets":9,"render":function(data, type, row){
	    			//付款状态
	    			var finishStatus = row['paymentSts'];
	    			var paymentSts = '未付款';
	    			if(finishStatus == '050'){
	    				paymentSts = '已付款';
	    			}else if(finishStatus == '040'){
	    				paymentSts = '部分付款';
	    			}
	    			return paymentSts;
	    		}}
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
		var month = $('#month').val();
		var monthday = $('#monthday').val();
		
		if(searchSts == '2'){//已收货
			$('#yearFlag').show();
			monthday = shortToday();
		}else{
			 monthday = '';
		}

		var deliveryDate = '';
		if(searchSts == '0'){//逾期未到货
			 deliveryDate = shortToday();
		}


		searchAjax(searchSts,"true",deliveryDate,monthday);
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样

		$('#defutBtnm'+searchSts).removeClass("start").addClass("end");	
		$('#defutBtn'+month).removeClass("start").addClass("end");	
		
		$("#year").change(function() {
			
			$('#keyword1').val('');
			$('#keyword2').val('');
			
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
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		searchAjax("","false",'','');
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	}

	function doShowControct(contractId,quantity,arrivalQty,stockinQty) {

		var deleteFlag = '1';
		var editFlag = '1';
		if(currencyToFloat(arrivalQty) > 0){
			deleteFlag = '0';//已经在执行中,不能删除
		}
		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId+'&deleteFlag=' + deleteFlag;
		

		callWindowFullView("合同详情",url);
		//location.href = url;
	}
	
	//已收货
	function doSearchCustomer3(searchSts,sessionFlag){

		hideAllSearch();
		$('#yearFlag').show();

		var monthday = getYearMonth();
		var monthonly = getMonth();
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });		    
	 	$('#defutBtnm'+searchSts).removeClass("start").addClass("end");
	 	$('#defutBtn'+monthonly).removeClass("start").addClass("end");

		$("#searchSts").val(searchSts);	
		$("#monthday").val(monthday);	
		$("#month").val(monthonly);	

		$("#keyword1").val("");
		$("#keyword2").val("");
		
	 	searchAjax(searchSts,'false','',monthday);		
	}
	
	function hideAllSearch(){

		$('#yearFlag').hide();
		//$('#userFlag').hide();
	}
	
	//未到货已付款
	function selectContractByDate2(searchSts,hideCol){

		hideAllSearch();

		var deliveryDate = shortToday();
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    
	 	$('#defutBtnm'+searchSts).removeClass("start").addClass("end");

		$("#searchSts").val(searchSts);
		
		$("#keyword1").val("");
		$("#keyword2").val("");
		
		searchAjax(searchSts,'false','','');
	
		
	}
	//未到货
	function selectContractByDate(status,sessionFlag){

		hideAllSearch();
		
		$("#keyword1").val("");
		$("#keyword2").val("");
		
		searchAjax(status,'false','','');
	}
		
	//月份选择
	function doSearchCustomer(month){
		
		$('#keyword1').val('');
		$('#keyword2').val('');
		
		var year = $('#year').val();
		
		var monthday = year +"-"+month;
		$('#monthday').val(monthday);
		$('#month').val(month);

		searchAjax('2','false','',monthday);
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
				<tr style="height: 25px;">
					<td width=""></td> 
					<td class="label">到货情况：</td>
					<td colspan="4">
						<!--a id="defutBtnm0" class="DTTT_button box2" onclick="selectContractByDate2('0',11);">逾期未到货</a-->
						<a id="defutBtnm1" class="DTTT_button box2" onclick="selectContractByDate('1',11);">未到货</a>
						<a id="defutBtnm0" class="DTTT_button box2" onclick="selectContractByDate2('3',11);">未到货已付款</a>
						<a id="defutBtnm2" class="DTTT_button box2" onclick="doSearchCustomer3('2','');" >已收货</a>
						
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
						</span>
								 
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
					<th>物料名称</th>
					<th style="width: 70px;">耀升编号</th>
					<th style="width: 60px;">供应商</th>
					<th style="width: 60px;">合同交期</th>
					<th style="width: 50px;">合同金额</th>
					<th style="width: 55px;">收货状态</th>
					<th style="width: 55px;">付款状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
