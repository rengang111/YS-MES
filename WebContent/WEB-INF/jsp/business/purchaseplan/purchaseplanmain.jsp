<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>采购方案--订单基本数据</title>
<script type="text/javascript">

	function ajax(pageFlg) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable();
			table.fnDestroy();
		}
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : false,
				"serverSide" : false,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"pagingType" : "full_numbers",
				//"scrollY":scrollHeight,
				//"scrollCollapse":true,
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/order?methodtype=search&keyBackup="+pageFlg,
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
					{"data": "YSId", "defaultContent" : '',"className" : 'td-left'},
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-left'},
					{"data": "materialId", "defaultContent" : '',"className" : 'td-left'},
					{"data": "materialName", "defaultContent" : ''},
					{"data": "quantity", "defaultContent" : '0', "className" : 'td-right'},
					{"data": "price", "defaultContent" : '0', "className" : 'td-right'},
					{"data": null, "defaultContent" : '0', "className" : 'td-right'},
				],
				"columnDefs":[
		    		
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShow('"+ row["YSId"] + "','"+ row["materialId"] + "')\">"+row["YSId"]+"</a>";
		    			return rtn;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			var name = row["materialName"];		    			
		    			name = jQuery.fixedWidth(name,40);		    			
		    			return name;
		    		}},
		    		{
						"visible" : false,
						"targets" : [ ]
					}	 
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
		
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				var num   = i + 1;
				cell.innerHTML = num;
			});
		}).draw();

	}
	
	function YSKcheck(v,id){
		var zzFlag = "";
		if(id != null && id != ''){
			zzFlag = id.substr(2,3);
		}
		if(zzFlag == 'YSK') v = 0;//库存订单不显示明细内容
		return v;
		
	}
	
	function initEvent(){

		ajax("");
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}

	$(document).ready(function() {

		
		initEvent();
		
	})	
	
	function doSearch() {	

		ajax("S");

	}
	

	function doShow(YSId,materialId) {

		var backFlag = 'purchasePlan';
		var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanAddInit&YSId=' 
				+ YSId+'&materialId='+materialId
				+'&backFlag='+backFlag;


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
									style="width:50px" value="查询" onclick="doSearch();"/>查询
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">

					<table id="TMaterial" class="display" >
						<thead>						
							<tr>
								<th style="width: 10px;" class="dt-middle ">No</th>
								<th style="width: 70px;" class="dt-middle ">耀升编号</th>
								<th style="width: 60px;" class="dt-middle ">订单交期</th>
								<th style="width: 120px;" class="dt-middle ">产品编号</th>
								<th class="dt-middle ">产品名称</th>
								<th style="width: 40px;" class="dt-middle ">数量</th>
								<th style="width: 65px;" class="dt-middle ">单价</th>
								<th style="width: 90px;" class="dt-middle ">销售总价</th>
							</tr>
						</thead>
					</table>
			</div>
		</div>
	</div>
</body>
</html>
