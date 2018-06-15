<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>采购合同一览</title>
<script type="text/javascript">

	function searchAjax(where,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		
		var actionUrl = "${ctx}/business/contract?methodtype=search";
		actionUrl = actionUrl +	"&sessionFlag="+sessionFlag;
		actionUrl = actionUrl +	where;
		//actionUrl = actionUrl+ 	"&status1="+status1;
		
		var t = $('#TMaterial').DataTable({
			"paging": true,
			 "iDisplayLength" : 100,
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
						
						if(myTrim(key1) == "" && myTrim(key2) == ""){
						 	$('#defutBtn').removeClass("start").addClass("end");							
						}else{							
						 	$('#defutBtn').removeClass("end").addClass("start");
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
				{"data": null,"className" : 'td-center'},
				{"data": "contractId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "materialId","className" : 'td-left'},
				{"data": "materialName", "defaultContent" : ''},
				{"data": "YSId", "defaultContent" : ''},
				{"data": "supplierId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "deliveryDate", "defaultContent" : ''},
				{"data": "quantity", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "arrivalQty", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "quantityInspection", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "contractStorage", "defaultContent" : '',"className" : 'td-right'},
				{"data": "stockinRtnQty", "defaultContent" : '0',"className" : 'td-right'},
			
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
	    			//alert("["+ysid+"]")
	    			var len = ysid.length;
	    			if(ysid == ""){
	    				return  "日常采购";	
	    			}else{
	    				//rtn= "<a href=\"###\" onClick=\"doShowYS('" + row["YSId"] + "')\">"+row["YSId"]+"</a>";				
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
	    			//累计收货
	    			var arrivalQty = currencyToFloat(row["arrivalQty"]);	
	    			var stockinRtnQty = currencyToFloat(row["stockinRtnQty"]);
	    			var inspectRtnQty = currencyToFloat(row["inspectRtnQty"]);
	    			var sumRtnQty = stockinRtnQty + inspectRtnQty;
	    			var newQty = setPurchaseQuantity(sumRtnQty,arrivalQty );		
	    			return floatToCurrency(newQty);
	    		}},
	    		{"targets":9,"render":function(data, type, row){
	    			//已检数
	    			var arrivalQty = currencyToFloat(row["quantityInspection"]);	
	    			var stockinRtnQty = currencyToFloat(row["stockinRtnQty"]);	
	    			var inspectRtnQty = currencyToFloat(row["inspectRtnQty"]);
	    			var sumRtnQty = stockinRtnQty + inspectRtnQty;
	    			var newQty = setPurchaseQuantity(sumRtnQty,arrivalQty );			
	    			return floatToCurrency(newQty);
	    		}},
	    		{"targets":10,"render":function(data, type, row){
	    			//入库数
	    			var arrivalQty = currencyToFloat(row["contractStorage"]);	
	    			var stockinRtnQty = currencyToFloat(row["stockinRtnQty"]);			    			
	    			var newQty = setPurchaseQuantity(stockinRtnQty,arrivalQty );	
	    			
	    			return floatToCurrency(newQty);
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

	$(document).ready(function() {

		var where = "&supplierId2=0574YZ00&materialId2=G&status=030&purchaseType=010";
		searchAjax(where,"true");
		
		buttonSelectedEvent();//按钮选择式样

		$('#defutBtn').removeClass("start").addClass("end");
		
	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		searchAjax("","false");
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	}

	//显示全部
	function doSearchCustomer(){
		searchAjax("","false");
	}
	
	//自制品未到货
	function doSearchCustomer2(){
		$("#keyword1").val('');
		$("#keyword2").val('');
		var where = "&supplierId=0574YZ00&status=030";
		searchAjax(where,"false");
	}
	//订购件未到货
	function doSearchCustomer3(){
		$("#keyword1").val('');
		$("#keyword2").val('');
		var where = "&supplierId2=0574YZ00&materialId2=G&status=030&purchaseType=010";
		searchAjax(where,"false");
	}
	//包装品未到货
	function doSearchCustomer4(){
		$("#keyword1").val('');
		$("#keyword2").val('');
		var where = "&materialId=G&status=030";
		searchAjax(where,"false");
	}
	function doShowYS(YSId) {

		var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;
		
		location.href = url;
	}

	function doShowControct(contractId,quantity,arrivalQty,stockinQty) {

		var deleteFlag = '1';
		var editFlag = '1';
		if(currencyToFloat(arrivalQty) > 0){
			deleteFlag = '0';//已经在执行中,不能删除
		}

		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId+'&deleteFlag=' + deleteFlag;
		location.href = url;
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
							style="width:50px" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
		<div id="DTTT_container2" style="height:40px;float: left">
			<a  class="DTTT_button box" onclick="doSearchCustomer();"><span>显示全部</span></a>&nbsp;&nbsp;
			<a  class="DTTT_button box" onclick="doSearchCustomer2();"><span>自制品未到货</span></a>
			<a  class="DTTT_button box" onclick="doSearchCustomer3();" id="defutBtn"><span>订购件未到货</span></a>
			<a  class="DTTT_button box" onclick="doSearchCustomer4();"><span>包装品未到货</span></a>
		</div>
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
					<th style="width: 50px;">合同数</th>
					<th style="width: 50px;">来货数</th>
					<th style="width: 50px;">已检数</th>
					<th style="width: 50px;">入库数</th>
					<th style="width: 50px;">退货数</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
