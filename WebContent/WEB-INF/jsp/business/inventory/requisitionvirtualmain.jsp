<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>

<title>虚拟领料--订单一览</title>
<script type="text/javascript">

	function ajaxSearch(virtualClass,sessionFlag,requisitionSts) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var actionUrl = "${ctx}/business/requisition?methodtype=virtualSearch";
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		actionUrl = actionUrl + "&requisitionSts=" + requisitionSts;
		//actionUrl = actionUrl + "&YsYear=17";
		actionUrl = actionUrl + "&YsYear=";//临时开发所有数据2018.7.30
		actionUrl = actionUrl + "&virtualClass=" + virtualClass;
		
		
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
							{"data": "requisitionId", "defaultContent" : '', "className" : 'td-left'},
							{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},
							{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},
							{"data": "materialName", "defaultContent" : ''},//3
							{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-center'},
							{"data": "orderQty", "defaultContent" : '0', "className" : 'td-right'},
							{"data": "requisitionDate", "defaultContent" : '-', "className" : 'td-center'},
							{"data": null, "className" : 'td-center'},//8
						],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"] ;				    			 
                    }},
		    		{"targets":2,"render":function(data, type, row){
		    			var manufactureQty = currencyToFloat( row["manufactureQty"] );
		    			var requisitionQty = currencyToFloat( row["requisitionQty"] );
		    			var rtn="";
		    			if(requisitionQty == manufactureQty){//已出库
			    			rtn= "<a href=\"###\" onClick=\"showHistory('"+ row["YSId"] + "')\">"+data+"</a>";		    				
		    			}else {
			    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["YSId"] + "')\">"+data+"</a>";
		    				
		    			}		    			
		    			
		    			return rtn;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			name = jQuery.fixedWidth(name,50);//true:两边截取,左边从汉字开始
		    			return name;
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			return data;
		    			//return floatToCurrency(data);
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{"targets":8,"render":function(data, type, row){
		    			var manufactureQty = currencyToFloat( row["manufactureQty"] );
		    			var requisitionQty = currencyToFloat( row["requisitionQty"] );
		    			var rtn="";
		    			if(requisitionQty == '0'){
		    				rtn = "待申请";
		    				
		    			}else if(requisitionQty == manufactureQty){
		    				rtn = "已出库";
		    				
		    			}else {
		    				rtn = "出库中";
		    				
		    			}		    			
		    			return rtn;
		    		}},
		    		{
						"visible" : false,
						"targets" : [1]
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

		ajaxSearch("","true","010");
		
	})	
	
	function doSearch() {	

		ajaxSearch("","false","");

	}
	function doSearch2(virtualClass,type) {	
		
		$("#keyword1").val("");
		$("#keyword2").val("");
		
		ajaxSearch(virtualClass,"false",type);

	}
	
	
	function doShowDetail(YSId) {
		var virtualType = $('#virtualType').val();
		var url =  "${ctx}/business/requisition?methodtype=virtualAddinit"+"&YSId="+YSId+"&virtualType=V";
		location.href = url;
	}
	

	function showHistory(YSId) {
		var virtualType = $('#virtualType').val();
		var url = "${ctx}/business/requisition?methodtype=getRequisitionHistoryInit&YSId="+YSId+"&virtualType=V";
		location.href = url;		
	};

	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<!-- 虚拟领料区分 -->
			<input type="hidden" id="virtualType" value="${virtualType }" />
			
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
		<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:left">
			<a class="DTTT_button DTTT_button_text" onclick="doSearch2('','010');"><span>待出库</span></a>
			<a class="DTTT_button DTTT_button_text" onclick="doSearch2('020','020');"><span>部分出库</span></a>
			<a class="DTTT_button DTTT_button_text" onclick="doSearch2('020','030');"><span>已出库</span></a>
		</div>
		<table id="TMaterial" class="display">
			<thead>						
				<tr>
						<th style="width: 10px;">No</th>
						<th style="width: 70px;">领料单编号</th>
						<th style="width: 70px;">耀升编号</th>
						<th style="width: 120px;">产品编号</th>
						<th>产品名称</th>
						<th style="width: 50px;">订单交期</th>
						<th style="width: 60px;">订单数量</th>
						<th style="width: 60px;">出库时间</th>
						<th style="width: 60px;">领料状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
