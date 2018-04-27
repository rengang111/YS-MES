<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>
<title>财务料件出库--一览</title>
<script type="text/javascript">

	function ajax(status,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var key1 = $("#keyword1").val();
		var key2 = $("#keyword2").val();
		

		var actionUrl = "${ctx}/business/stockout?methodtype=financeSearch";
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		actionUrl = actionUrl + "&key1=" + key1;
		actionUrl = actionUrl + "&key2=" + key2;
		
		var start = $('#stockOutDateStart').val();
		var end = $('#stockOutDateEnd').val();
		actionUrl += "&stockOutDateStart=" + start;
		actionUrl += "&stockOutDateEnd=" + end;
		
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"autoWidth"	:false,
				"pagingType" : "full_numbers",
	         	"aaSorting": [[ 1, "ASC" ]],
				//"scrollY":scrollHeight,
				//"scrollCollapse":true,
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
					{"data": null, "defaultContent" : '',"className" : 'td-center'},
					{"data": "stockOutId", "defaultContent" : '', "className" : 'td-left'},
					{"data": "checkOutDate", "defaultContent" : '', "className" : 'td-center'},//出库时间
					{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},//4
					{"data": "materialName", "defaultContent" : ''},//5
					{"data": "unit", "defaultContent" : '', "className" : 'td-center'},
					{"data": "YSId", "defaultContent" : ''},//
					{"data": "supplierId", "defaultContent" : '（集中采购）', "className" : 'td-left'},//
					{"data": "quantity", "defaultContent" : '', "className" : 'td-right'},// 领料申请者
					
					],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"] ;				    			 
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var  rtn= "<a href=\"###\" onClick=\"showHistory('"+ row["YSId"] + "','"+ row["stockOutId"] + "')\">"+row["stockOutId"]+"</a>";
		    			if(data == ""){
		    				rtn= "<a href=\"###\" onClick=\"doCreate('"+ row["YSId"] + "','"+ row["requisitionId"] + "')\">"+"（待出库）"+"</a>";
		    			
		    			}
		    			return rtn;
		    		}},
		    		{"targets":6,"render":function(data, type, row){	    			
		    			return jQuery.fixedWidth(data,20);
		    		}},
		    		{"targets":4,"render":function(data, type, row){	    			
		    			return jQuery.fixedWidth(data,45);
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : []
					}
	         	]
			}
		);
		

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

		$("#stockOutDateStart").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
		}); 
		$("#stockOutDateEnd").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 
		
		ajax("020","true");

	    buttonSelectedEvent();//按钮点击效果
	})	
	
	function doSearch() {	

		ajax("","false");
	}

	
	function showHistory(YSId,stockOutId) {
		
		var url = "${ctx}/business/stockout?methodtype=stockoutHistoryInit&YSId="+YSId;
		callProductDesignView('出库信息',url);
	}
	

	function doCreate(YSId,requisitionId) {
		
		var url =  "${ctx}/business/stockout?methodtype=addinit&YSId="+YSId+"&requisitionId="+requisitionId;
		callProductDesignView('出库信息',url);
	}
	
	function selectContractByDate(type){
		$("#keyword1").val('');
		$("#keyword1").val('');
		ajax(type,"false");
	}
	
	function downloadExcel() {
		 
		var key1 = $("#keyword1").val();
		var key2 = $("#keyword2").val();
		var start = $("#stockOutDateStart").val();
		var end   = $("#stockOutDateEnd").val();	
		
		var url = '${ctx}/business/stockout?methodtype=downloadExcel'
				 + "&key1=" + key1
				 + "&key2=" + key2
				 + "&stockOutDateStart=" + start
				 + "&stockOutDateEnd=" + end;
		
		url =encodeURI(encodeURI(url));//中文两次转码

		location.href = url;
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
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
				<tr>
					<td width="10%"></td> 
					<td class="label">出库时间：</td>
					<td>
						<input type="text" name="stockOutDateStart" id="stockOutDateStart"  value=""  class="short"/>&nbsp;至
						<input type="text" name="stockOutDateEnd" id="stockOutDateEnd" value=""  class="short"/>
					</td>
					<td ></td> 
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
	<!-- 
		<div id="DTTT_container2" style="height:40px;float: left">
			<a  class="DTTT_button box" onclick="doSearchCustomer('010',9);">原材料</a>
			<a  class="DTTT_button box" onclick="doSearchCustomer('020',9);">包装件</a>
			<a  class="DTTT_button box" onclick="doSearchCustomer('030',9);">采购件</a>
		</div>
		 -->
		<div id="DTTT_container" align="right" style="height:40px;width:50%;float: right">
			<a class="DTTT_button DTTT_button_text" onclick="downloadExcel();">EXCEL导出</a>
		</div>
	
		<table id="TMaterial" class="display">
			<thead>						
				<tr>
					<th style="width: 30px;">No</th>
					<th style="width: 70px;">出库单编号</th>
					<th style="width: 70px;">出库时间</th>
					<th style="width: 150px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 50px;">单位</th>
					<th style="width: 80px;">耀升编号</th>
					<th style="width: 70px;">供应商</th>
					<th style="width: 100px;">出库数量</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
