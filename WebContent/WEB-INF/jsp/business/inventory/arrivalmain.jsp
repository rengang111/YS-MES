<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>到货登记一览(合同未到货)</title>
<script type="text/javascript">

	function searchAjax(sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var makeType  = $("#makeType").val();//区分包装件，采购件，自制件
		var userId    = $("#userId").val();
		var searchSts = $("#searchSts").val();
		var month     = $("#month").val();
		var monthday  = $('#monthday').val();
		var year      = $('#year').val();
		var url = "${ctx}/business/arrival?methodtype=contractArrivalSearch"+"&sessionFlag="+sessionFlag;
		url = url + "&makeType="+makeType;
		url = url + "&searchSts="+searchSts;
		url = url + "&userId="+userId;
		url = url + "&month="+month;
		url = url + "&monthday="+monthday;
		url = url + "&year="+year;
		
		if(searchSts == '0'){			
			url += "&deliveryDate="+shortToday();//逾期未到货
		}else if(searchSts == '1'){			
			url += "&deliveryDate=";//未到货			
		}else if(searchSts == '2'){			
			url += "&deliveryDate=";//已到货			
		}

		var t = $('#TMaterial').DataTable({
			"paging": true,
			 "iDisplayLength" : 100,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"autoWidth"	:false,
			"searching" : false,
			"pagingType" : "full_numbers",
			// "aaSorting": [[ 7, "ASC" ]],
			//"retrieve" : true,
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
						$("#keyword1").val(data["keyword1"]);
						$("#keyword2").val(data["keyword2"]);
						fnCallback(data);

					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
						alert('系统异常，请联系管理员。');
					 }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns": [
				{"data": null,"className" : 'td-center'},
				{"data": "YSId"},
				{"data": "contractId","className" : 'td-left'},
				{"data": "purchaserName","className" : 'td-center', "defaultContent" : '-'}, //10
				{"data": "materialId"},
				{"data": "materialName"},
				{"data": "customerShortName", "defaultContent" : '-',"className" : 'td-center'},
				{"data": "supplierId"},
				{"data": "deliveryDate","className" : 'td-right'},
				{"data": "quantity","className" : 'td-right'},
				{"data": "accumulated","className" : 'td-right'}, // 10 净收货
				{"data": "arriveDate","className" : 'td-center'},// 11

			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var mateid= data;
	    			var contractQty = currencyToFloat(row['quantity']);//合同数
	    			var accumulated = currencyToFloat(row['accumulated']);//净收货
	    			if(data.length>18){
						mateid= '<div style="font-size: 11px;">' + data + '</div>';	    				
	    			}
					if(accumulated >= contractQty ){
		    			return  "<a href=\"###\" onClick=\"doShowDetail('" + row["contractId"] + "')\">"+mateid+"</a>";	 
					}else{
		    			return  "<a href=\"###\" onClick=\"doCreate('" + row["contractId"] + "')\">"+mateid+"</a>";						
					}   			
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			var mateid= data;
	    			if(data.length>24)
						mateid= '<div style="font-size: 11px;">' + data + '</div>';
	    			return  "<a href=\"###\" onClick=\"doShow('" + row["materialParentId"] + "','" + row["materialRecordId"] + "','" + row["materialId"] + "')\">"+mateid+"</a>";	    			
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];				    			
	    			name = jQuery.fixedWidth(name,32);				    			
	    			return name;
	    		}},
	    		{"targets":9,"render":function(data, type, row){

	    			return parseInt(currencyToFloat(data));
	    		}},
	    		{"targets":10,"render":function(data, type, row){
	    			//净收货
	    			return parseInt(currencyToFloat(data));
	    		}},
	    		{"targets":11,"render":function(data, type, row){
	    			if(data ==''){
	    				return '（未收货）'; 
	    			}else{
	    				return data;    				
	    			}
	    		}},
	    		{
	    			"visible":false,
	    			"targets":[]
	    		}
	           
	         ] 
		});

	}

	
	function hideAllSearch(){

		$('#yearFlag').hide();
		$('#userFlag').hide();
	}

	$(document).ready(function() {

		setYearList();
		hideAllSearch();

		var defUser = $('#userId').val();
		var searchSts = $('#searchSts').val();
		var month = 'ALL';//$('#month').val();
		var monthday = $('#monthday').val();
		var currYear = getYear();
		$('#year').val(currYear);
		
		if(searchSts == '2'){//已收货
			$('#yearFlag').show();
			monthday = shortToday();
		}else{
			 monthday = '';
		}

		$('#defutBtn'+month).removeClass("start").addClass("end");
		$('#defutBtnu'+defUser).removeClass("start").addClass("end");
		$('#defutBtnm'+searchSts).removeClass("start").addClass("end");	
		
		$('#userId').val(defUser);		
		
		searchAjax("true");
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});

		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		buttonSelectedEvent3();//按钮选择式样		

		
		$("#year").change(function() {
						
			var year  = $('#year').val();
			var currYear = getYear();
			
			if(year == currYear){//当前年份

				var month = $('#month').val();
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
		 	
		 	searchAjax('false');
	
		});
	})	
	
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
	
	function doCreate(contractId) {

		var makeType=$('#makeType').val();
		var url = '${ctx}/business/arrival?methodtype=addinit&contractId='+contractId+
			'&makeType='+makeType;
		
		callWindowFullView("收货",url);
	}
	
	
	function doShowDetail(contractId) {

		var makeType=$('#makeType').val();
		var url = '${ctx}/business/arrival?methodtype=detailView&contractId='+contractId+
			'&makeType='+makeType;
		
		callWindowFullView("收货详情",url);
	}
	


	function doShow(parentid,recordid,materialId) {

		//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
		var url = '${ctx}/business/material?methodtype=detailView';
		url = url + '&parentId=' + parentid+'&recordId='+recordid+'&materialId='+materialId+'&keyBackup=1';
		
		callWindowFullView("物料信息",url);
	}

	function doDelete() {

		var str = '';
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				str += $(this).val() + ",";
			}
		});

		if (str != '') {
			if(confirm("确定要删除数据吗？")) {
				jQuery.ajax({
					type : 'POST',
					async: false,
					contentType : 'application/json',
					dataType : 'json',
					data : str,
					url : "${ctx}/business/arrival?methodtype=delete",
					success : function(data) {
						reload();						
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				});
			}
		} else {
			alert("请至少选择一条数据");
		}
		
	}

	function reload() {
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		
		return true;
	}
	
	function doSearch() {	

		hideAllSearch();
		
		/*
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    */
	    
		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		searchAjax('false');

	}
	
	//未收货
	function selectContractByDate(type,hideCol){

		hideAllSearch();

		$("#searchSts").val(type);
		
		searchAjax('false');
	}
	
	//逾期未收货
	function selectContractByDate2(type,hideCol){

		hideAllSearch();
		$('#userFlag').show();
			
		var month = getYearMonth();
		var monthonly = getMonth();
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    
	 	$('#defutBtnm'+type).removeClass("start").addClass("end");

		$("#searchSts").val(type);
				
		searchAjax('false');
		
		
		
	}
	
	//已收货
	function doSearchCustomer3(type,sessionFlag){

		hideAllSearch();
		$('#yearFlag').show();
		
		var month = '';//getYearMonth();
		var monthday = 'ALL';//getMonth();
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });		    
	 	$('#defutBtn'+monthday).removeClass("start").addClass("end");

		$("#searchSts").val(type);
		$("#month").val(month);
		$("#monthday").val('');
		
	 	searchAjax('false');
			
	}
	
	//采购员选择
	function doSelectUserId(userId){
				
		$('#userId').val(userId);

		searchAjax('false');
	}
	
	
	
	//月份选择
	function doSearchCustomer(month){
				
		var year = $('#year').val();
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
								
			}
		}else{
			if(month != 'ALL'){
				monthday = year +"-"+month; 
			}
			$('#year').val(year);
		}

		$('#monthday').val(monthday);
		$('#month').val(month);

		searchAjax('false');
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >

		<input type="hidden" id="makeType"  value="${makeType }" />
		<input type="hidden" id="searchSts" value="${searchSts }" />
		<input type="hidden" id="userId"   name="userId"  value="${userId }" />
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
					<td width="10%"></td> 
				</tr>
			</table>
			
			<table>
				<tr>
					<td width="10px"></td>
					<td width="80px" class="label">收货情况&nbsp;</td>
					<td colspan="">
						<a id="defutBtnm3" class="DTTT_button box2" onclick="selectContractByDate2('3');">ALL</a>	
						<a id="defutBtnm0" class="DTTT_button box2" onclick="selectContractByDate2('0');">逾期未收货</a>				
						<a id="defutBtnm1" class="DTTT_button box2" onclick="selectContractByDate('1');">未收货</a>
						<a id="defutBtnm2" class="DTTT_button box2" onclick="doSearchCustomer3('2');" >已收货</a>	
					
					</td> 
					<td colspan="">		
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
							<a id="defutBtnALL" class="DTTT_button box" onclick="doSearchCustomer('ALL');">
								ALL</a>
						</span>								 
					</td>
				</tr>
				<tr>
					<td width=""></td>
					<td width="" class="label">采购员&nbsp;</td>
					<td colspan="">
						<span id="">
							<c:forEach var='list' items='${purchaser}' varStatus='status'>
								<a id="defutBtnu${list.dicId }" style="height: 15px;margin-top: 5px;" 
									class="DTTT_button box3" onclick="doSelectUserId('${list.dicId }');">
									<span>${list.dicName }</span></a>
							</c:forEach>
						</span>								 
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
		<table id="TMaterial" class="display" style="width: 100%;">
			<thead>						
				<tr>
					<th width="10px">No</th>
					<th style="width: 50px;">耀升编号</th>
					<th style="width: 90px;">合同编号</th>
					<th style="width: 50px;">采购员</th>
					<th style="width: 100px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 30px;">客户</th>
					<th style="width: 50px;">供应商</th>
					<th style="width: 50px;">合同交期</th>
					<th style="width: 50px;">合同数</th>
					<th style="width: 50px;">净收货</th>
					<th width="50px">收货日期</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
