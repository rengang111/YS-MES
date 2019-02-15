<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>
<title>料件出库--一览（配件单）</title>
<script type="text/javascript">

	function ajax(status,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var makeType = $("#makeType").val();
		var usedType = $("#usedType").val();

		var actionUrl = "${ctx}/business/stockout?methodtype=partsStockoutSearch";
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		actionUrl = actionUrl + "&requisitionSts=" + status;
		actionUrl = actionUrl + "&makeType=" + makeType;
		actionUrl = actionUrl + "&usedType=" + usedType;
		
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
	         	//"aaSorting": [[ 1, "DESC" ]],
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
					{"data": "orderYsid", "defaultContent" : '', "className" : 'td-left'},
					{"data": "materialId", "defaultContent" : '***', "className" : 'td-left'},//3
					{"data": "materialName", "defaultContent" : '***'},//4
					{"data": "orderQty", "defaultContent" : '0', "className" : 'td-right'},
					{"data": "requisitionQty", "defaultContent" : '', "className" : 'td-right'},
					{"data": "requisitionDate", "defaultContent" : '', "className" : 'td-center'},//7 申请时间
					{"data": "checkOutDate", "defaultContent" : '', "className" : 'td-center'},//8 出库时间
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"] ;				    			 
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var  rtn= "<a href=\"###\" onClick=\"showHistory('"+ row["YSId"] + "','"+ row["stockOutId"] + "','"+ row["requisitionTypeId"] + "')\">"+row["stockOutId"]+"</a>";
		    			if(data == ""){
		    				rtn= "<a href=\"###\" onClick=\"doCreate('"+ row["YSId"] + "','"+ row["requisitionId"] + "','"+ row["requisitionTypeId"] + "')\">"+row["requisitionId"]+"</a>";
		    			
		    			}
		    			return rtn;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["materialId"];
		    			var type = row["requisitionTypeId"];
		    			
		    			return name;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			if(name == null){
		    				name = jQuery.fixedWidth(row["collectYsid"],40);
		    			}else{
			    			name = jQuery.fixedWidth(name,40);//true:两边截取,左边从汉字开始
		    				
		    			}
		    			
		    			return name;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			var val = parseInt(data);
		    			return val;
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			var val = parseInt(data);
		    			return val;
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			var val = row["checkOutDate"];
		    			if(val == null || val=="")
		    				val = "未出库";
		    			return val;
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

		var requisitionSts = $('#requisitionSts').val();
		ajax(requisitionSts,"true");

	    buttonSelectedEvent();//按钮点击效果

		$('#defutBtn'+requisitionSts).removeClass("start").addClass("end");
	})	
	
	function doSearch() {	

		ajax("","false");
	}

	
	function showHistory(YSId,stockOutId,requisitionType) {

		var url = "${ctx}/business/stockout?methodtype=stockoutPartsHistoryInit&YSId="+YSId
			+"&stockOutId="+stockOutId+"&requisitionType="+requisitionType;
		location.href = url;
	}
	

	function doCreate(YSId,requisitionId,requisitionType) {

		var url =  "${ctx}/business/stockout?methodtype=partsAddInit&YSId="+YSId
			+"&requisitionId="+requisitionId+"&requisitionType="+requisitionType;
			
		location.href = url;
	}
	
	function selectContractByDate(type){
		$("#keyword1").val('');
		$("#keyword1").val('');
		ajax(type,"false");
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<input type="hidden" id="makeType" value="${makeType }" />
			<input type="hidden" id="usedType" value="${usedType }" />
			<input type="hidden" id="requisitionSts" value="${requisitionSts }" />
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
		<div id="DTTT_container" align="left" style="height:40px;width:50%">
			<a class="DTTT_button DTTT_button_text box" onclick="selectContractByDate('020');" id="defutBtn020">待出库</a>
			<a class="DTTT_button DTTT_button_text box" onclick="selectContractByDate('030');" id="defutBtn030">已出库</a>
		</div>
	
		<table id="TMaterial" class="display">
			<thead>						
				<tr>
					<th style="width: 10px;">No</th>
					<th style="width: 80px;">出库单编号</th>
					<th style="width: 80px;">耀升编号</th>
					<th style="width: 120px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 60px;">订单数量</th>
					<th style="width: 60px;">申请数量</th>
					<th style="width: 60px;">申请时间</th>
					<th style="width: 60px;">出库时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>