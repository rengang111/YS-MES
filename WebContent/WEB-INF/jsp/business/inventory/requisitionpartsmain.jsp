<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>

<title>配件订单领料申请--订单一览</title>
<script type="text/javascript">

	function ajaxSearch(sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var requisitionSts = $('#requisitionSts').val();
		var partsType = $('#partsType').val();
		var actionUrl = "${ctx}/business/requisition?methodtype=partsMainSearch";
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		actionUrl = actionUrl + "&requisitionSts=" + requisitionSts;
		actionUrl = actionUrl + "&partsType=" + partsType;
		
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
							{"data": "", "defaultContent" : '', "className" : 'td-left'},
							{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},
							{"data": "materialId", "defaultContent" : '***', "className" : 'td-left'},//3
							{"data": "materialName", "defaultContent" : '***'},//4
							{"data": "deliveryDate",   "defaultContent" : '***', "className" : 'td-center'},
							{"data": "requisitionDate","defaultContent" : '-', "className" : 'td-center'},
							{"data": "totalQuantity", "defaultContent" : '0', "className" : 'td-right'},
							{"data": "requisitionQty", "defaultContent" : '0', "className" : 'td-right'},//8
							{"data": null, "className" : 'td-center'},//9
						],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"] ;				    			 
                    }},
		    		{"targets":2,"render":function(data, type, row){
		    			var requisitionQty = currencyToFloat( row["requisitionQty"] );
		    			var orderQty   = currencyToFloat( row["orderQty"] );
		    			var stockinQty = currencyToFloat( row["computeStockinQty"] );
		    			var partsType  = $('#partsType').val();
		    			var rtn="";
		    			if(partsType == 'P'){

			    			if(requisitionQty > 0 ){//已申请
				    			rtn= "<a href=\"###\" onClick=\"showHistory('"+ row["peiYsid"] + "')\">"+data+"</a>";		    				
			    			}else {
				    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["peiYsid"] + "')\">"+data+"</a>";
			    			}
		    			}else{
			    			if(stockinQty >= orderQty){//已入库
				    			rtn= "<a href=\"###\" onClick=\"showHistoryProduct('"+ row["YSId"] + "')\">"+data+"</a>";		    				
			    			}else {
				    			rtn= "<a href=\"###\" onClick=\"doShowDetailProduct('"+ row["YSId"] + "')\">"+data+"</a>";
			    			}	
			    			
		    			}
		    			return rtn;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			name = jQuery.fixedWidth(name,36);//true:两边截取,左边从汉字开始
		    			return name;
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			return floatToNumber(data);
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			
		    			return floatToNumber(data);
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{"targets":9,"render":function(data, type, row){
		    			var manufactureQty = currencyToFloat( row["totalQuantity"] );
		    			var requisitionQty = currencyToFloat( row["requisitionQty"] );
		    			var stockoutQty    = currencyToFloat( row["stockoutQty"] );
		    			var rtn="";
		    			if(stockoutQty > 0){
		    				rtn = "已出库";
		    			}else{
		    				rtn = "待领料";
		    				
		    			}	    			
		    			return rtn;
		    		}},
		    		{
						"visible" : false,
						"targets" : [1,6,8]
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
		var partsType = $('#partsType').val();
		
		ajaxSearch("true");
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样

		$('#defutBtn'+requisitionSts).removeClass("start").addClass("end");
		$('#defutBtnm'+partsType).removeClass("start").addClass("end");
		
	})	
	
	function doSearch() {	

		/*
		$('#requisitionSts').val('');
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    */
	    
		ajaxSearch("false");

	}
	
	function doSearch2(type) {	
		
		//$("#keyword1").val("");
		//$("#keyword2").val("");

		$('#requisitionSts').val(type);

		ajaxSearch("false");

	}
	
	//普通，成品
	function doSearch3(type) {	
		
		//$("#keyword1").val("");
		//$("#keyword2").val("");

		$('#partsType').val(type);
		
		ajaxSearch("false");

	}
	
	
	function doShowDetail(YSId) {

		var url =  "${ctx}/business/requisition?methodtype=peiAddinit&YSId="+YSId;
		callWindowFullView('装配领料',url);	
	}
	

	function showHistory(YSId) {

		var url = "${ctx}/business/requisition?methodtype=getRequisitionHistoryInitParts&YSId="+YSId;
		callWindowFullView('装配领料',url);	
	};

	function showHistoryProduct(YSId) {
		var virtualClass = $('#virtualClass').val();
		var url = "${ctx}/business/requisition?methodtype=getRequisitionHistoryInit&YSId="+YSId+"&virtualClass="+virtualClass;
		callWindowFullView('出库详情',url);		
	};
	
	function doShowDetailProduct(YSId) {
		
		var methodtype = "addinit"
		
		var url =  "${ctx}/business/requisition?methodtype="+methodtype+"&YSId="+YSId;
		callWindowFullView('装配领料',url)
		
	}
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<!-- 虚拟领料区分 -->
			<input type="hidden" id="virtualClass"   value="${virtualClass }" />
			<input type="hidden" id="requisitionSts" value="${requisitionSts }" />
			<input type="hidden" id="partsType"      value="P" />
			
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
				
				<tr>
					<td width="50px"></td> 
					<td class="label"> 快捷查询：</td>
					<td class="">&nbsp;
						<a id="defutBtn010" class="DTTT_button box2" onclick="doSearch2('010');">未领料</a>
						<a id="defutBtn030" class="DTTT_button box2" onclick="doSearch2('030');" >已领料</a>
					</td>
					<td class="label"></td> 
					<td class="">&nbsp;
					<!--  
						<a id="defutBtnmP" class="DTTT_button box" onclick="doSearch3('P');">普通配件</a>
						<a id="defutBtnmC" class="DTTT_button box" onclick="doSearch3('C');">成品配件</a>
					-->
					</td>
					<td></td>
					<td width=""></td> 
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">					
		<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:left">
			<!--a class="DTTT_button DTTT_button_text box" onclick="doSearch32(1,'010');" id="defutBtn010"><span>未申请</span></a>
			<!--a class="DTTT_button DTTT_button_text box" onclick="doSearch32(8,'020');" id="defutBtn020"><span>待出库</span></a>
			<!-- a class="DTTT_button DTTT_button_text box" onclick="doSearch32(8,'030');" id="defutBtn030"><span>已出库</span></a -->
			<!-- 
			<a class="DTTT_button DTTT_button_text box" onclick="doSearch32(8,'040');" id="defutBtn040"><span>成品已入库但未领料</span></a> -->
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
						<th style="width: 50px;">申请时间</th>
						<th style="width: 60px;">订单数量</th>
						<th style="width: 60px;">领料数量</th>
						<th style="width: 50px;">领料状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
