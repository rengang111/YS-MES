<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>成品入库一览</title>
<script type="text/javascript">

	function ajax(status,sessionFlag,storageMonth) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/storage?methodtype=productStockinSearch"+"&sessionFlag="+sessionFlag;
		
		
		var key1 = $("#keyword1").val();
		var key2 = $("#keyword2").val();
		if(key1 != '' || key2 != ''){
			//
			status = '';			
		}
		url += "&storageMonth=" + storageMonth;
		url += "&status="+status;
		url += "&keyBackup="+status;

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
							//alert(XMLHttpRequest.status);
							//alert(XMLHttpRequest.readyState);
							//alert(textStatus);
							//alert(errorThrown);
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
				{"data": null,"className" : 'td-right'},
				{"data": "storageDate","className" : 'td-center',"defaultContent" : '（未入库）'},
				{"data": "team","className" : 'td-center'},
								
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":1,"render":function(data, type, row){

	    			var contractId = row["contractId"];	
	    			var arrivalId = row["arrivalId"];	
	    			var status = row["statusId"];
	    			if(status == '050'){
	    				var rtn= "<a href=\"###\" onClick=\"doShow('" + row["YSId"] + "')\">"+row["YSId"]+"</a>";
	    	    		
	    			}else{
	    				var rtn= "<a href=\"###\" onClick=\"doAdd('" + row["YSId"] + "')\">"+row["YSId"]+"</a>";
	    	    		
	    			}
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];				    			
	    			name = jQuery.fixedWidth(name,48);				    			
	    			return name;
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    						    			
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			//入库数量
	    			var orderQty = floatToCurrency(row["orderQty"]);
	    			var stockin = currencyToFloat(row["stockinQty"]);
	    			var virtualin = currencyToFloat(row["completedQuantity"]);//订单表里的入库数量（虚拟入库使用）
	    			var ysid=row["YSId"];
	    			var viewQty = stockin;
	    			var span_s = '',span_e='</span>';
	    			if(stockin > 0){
	    				span_s = '<span>';
	    			}else{
	    				if(virtualin > 0){
	    					viewQty = orderQty;
	    					span_s = '<span style="color: red;">';
	    				}
	    			}
	    			var rtn = span_s + viewQty + span_e;
	    			
	    		
	    			return rtn;
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			//入库时间
	    			var storageDate = (row["storageDate"]);
	    			var stockin = currencyToFloat(row["stockinQty"]);
	    			var virtualin = currencyToFloat(row["completedQuantity"]);
	    			if(stockin <=0 )
	    				storageDate = "（未入库）";
	    			else{
		    			if(storageDate == null || storageDate == ''){
		    				if(virtualin > 0 && stockin <=0 ){
			    				storageDate = '（手动修正）';
		    				}
		    			}
	    				
	    			}
	    			return storageDate;
	    		}}
	           
	         ] 
		});

	}

	$(document).ready(function() {

		$('#yearFlag').hide();
		
		//030:默认显示待生产的数据
		var keyBackup = '030';//$('#keyBackup').val();
		ajax(keyBackup,true,'');
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});	
		
		setYearList();

		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		
	 	$('#defutBtn'+keyBackup).removeClass("start").addClass("end");
	 	//$('#defutBtn').removeClass("start").addClass("end");
	})	
	
	//已入库
	function doSearchCustomer3(status,sessionFlag){
		
		var yearShowFlag = $('#yearShowFlag').val();
		if(yearShowFlag == ''){
			$('#yearFlag').show();
			$('#yearShowFlag').val('1');	
			
			var month = getYearMonth();
			var monthonly = getMonth();
			
			var collection = $(".box");
		    $.each(collection, function () {
		    	$(this).removeClass("end");
		    });
		    
		 	$('#defutBtn'+monthonly).removeClass("start").addClass("end");
		 	
			ajax(status,sessionFlag,month);
		}
		else{
			$('#yearFlag').hide();
			$('#yearShowFlag').val('');			
		}
		
		var follow   = $('#follow').val();
		var monthday = $('#monthday').val();
		
		
		//ajaxTrack(orderSts,'false',follow,monthday);
	}
	
	function doSearch() {	

		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
		
		//false:不使用session
		ajax("","false",'');
	}
		
	function doShow(YSId) {

		var url = '${ctx}/business/storage?methodtype=productAddInit&YSId=' + YSId;

		callWindowFullView("成品入库",url);
	}
	
	function doAdd(YSId) {

		var url = '${ctx}/business/storage?methodtype=productAddInit&YSId=' + YSId;

		location.href = url;
	}

	
	function selectContractByDate(status,sessionFlag){

		$('#yearFlag').hide();
		$('#yearShowFlag').val('1');
		
		ajax(status,sessionFlag,'');
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
	
	//年份选择
	function doSearchCustomer4(){
		
		$('#keyword1').val('');
		$('#keyword2').val('');
		
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
	 	
	 	ajax('040','false',monthday);
	}
	
	//月份选择
	function doSearchCustomer(month){
		
		$('#keyword1').val('');
		$('#keyword2').val('');
		
		var year = $('#year').val();
		
		var monthday = year +"-"+month;
		$('#monthday').val(monthday);
		$('#month').val(month);
				
		ajax('040','false',monthday);
	}
</script>
</head>

<body>
<div id="container">
<div id="main">		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >

			<input type="hidden" id="keyBackup" value="${keyBackup }" />
			<input type="hidden" id="monthday" name="monthday" value="" />
			<input type="hidden" id="month"    name="month"    value="" />
			<input type="hidden" id="yearShowFlag"    name="yearShowFlag"    value="" />
			
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
				<tr style="height: 25px;">
					<td width=""></td> 
					<td class="label">备货情况：</td>
					<td colspan="5">
						<a  class="DTTT_button box2" onclick="selectContractByDate('030','false');" id="defutBtn030">待交货</a>
						<a  class="DTTT_button box2" onclick="doSearchCustomer3('040','false');" id="defutBtn040">已入库</a>
						
						<span id="yearFlag">			
							<select id="year" name="year" onclick="doSearchCustomer4();"  style="width: 100px;vertical-align: bottom;"></select>
							
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
	<div  style="height:10px"></div>

	<div class="list">
		<table id="TMaterial" class="display" style="width: 100%;">
			<thead>						
				<tr>
					<th style="width: 1px;">No</th>
					<th style="width: 100px;">耀升编号</th>
					<th style="width: 150px;">产品编号</th>
					<th>产品名称</th>
					<th style="width: 80px;">订单数量</th>
					<th style="width: 80px;">入库数量</th>
					<th style="width: 80px;">入库时间</th>
					<th style="width: 60px;">业务组</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
