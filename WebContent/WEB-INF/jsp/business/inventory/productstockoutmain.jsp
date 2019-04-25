<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>成品出库一览</title>
<script type="text/javascript">

	function ajax(status,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var url = "${ctx}/business/stockout?methodtype=productSearchMain"+"&sessionFlag="+sessionFlag;
			url += "&status="+status;

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

					 }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns": [
				{"data": null,"className" : 'td-center'},
				{"data": "YSId"},
				{"data": "materialId"},
				{"data": "materialName"},
				{"data": "orderQty","className" : 'td-right'},
				{"data": "stockinQty","className" : 'td-right'},
				{"data": "stockOutQty","className" : 'td-right',"defaultContent" : '0'},
				{"data": "stockoutDate","className" : 'td-center',"defaultContent" : '（未出库）'},
								
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":1,"render":function(data, type, row){

	    			var contractId = row["contractId"];	
	    			var arrivalId = row["arrivalId"];		    			
	    			var rtn= "<a href=\"###\" onClick=\"doShow('" + row["YSId"] + "','" + row["stockOutId"] + "')\">"+row["YSId"]+"</a>";
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];				    			
	    			name = jQuery.fixedWidth(name,40);				    			
	    			return name;
	    		}},
	    		{"targets":7,"render":function(data, type, row){

	    			var stockoutDate = row["stockoutDate"];	
	    			var deliveryDate = row["deliveryDate"];	
	    			var rtn = stockoutDate;
	    			if(stockoutDate == '' || stockoutDate == null){
	    				rtn = deliveryDate;
	    			}
	    			return rtn;
	    		}}
	           
	         ] 
		});

	}

	$(document).ready(function() {
		

		hideAllSearch();
		setYearList();
		
		//
		ajax("040","true");
	
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
		
		 $('#defutBtn').removeClass("start").addClass("end");
	})	
	
	function doSearch() {	

		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	        
		//false:不使用session
		ajax("","false");
	}
		
	function doShow(YSId,stockOutId) {

		var url = '${ctx}/business/stockout?methodtype=productStockoutAddInit&YSId=' + YSId+'&stockOutId='+stockOutId;

		location.href = url;
	}
			
	
	function selectContractByDate(status){
	
		ajax(status,'false');
	}
	
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
		
</script>
</head>

<body>
<div id="container">
<div id="main">		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >

		<input type="hidden" id="keyBackup" value="${keyBackup }" />
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
							style="width:50px" value="查询" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
				<tr style="height: 25px;">
					<td width=""></td> 
					<td class="label">快捷查询：</td>
					<td colspan="4">
						<!-- a class="DTTT_button box" onclick="selectContractByDate('030');"  >未入库</a -->
						<a class="DTTT_button box" onclick="selectContractByDate('040');" id="defutBtn" >未出库</a>
						<!--a class="DTTT_button box" onclick="selectContractByDate('051');">部分出库中</a -->
						<a class="DTTT_button box" onclick="selectContractByDate('050');">已出库</a>
						
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
		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
		
		<table id="TMaterial" class="display dataTable" style="width: 100%;">
			<thead>						
				<tr>
					<th style="width: 1px;" class="dt-middle ">No</th>
					<th style="width: 100px;" class="dt-middle">耀升编号</th>
					<th style="width: 170px;" class="dt-middle ">产品编号</th>
					<th class="dt-middle">产品名称</th>
					<th style="width: 80px;" class="dt-middle">订单数量</th>
					<th style="width: 80px;" class="dt-middle">入库数量</th>
					<th style="width: 80px;" class="dt-middle">出库数量</th>
					<th style="width: 80px;" class="dt-middle">出库日期</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
