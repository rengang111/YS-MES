<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />


<%@ include file="../../common/common.jsp"%>

<title>订单采购方案一览</title>
<script type="text/javascript">

	var layerHeight = '500';
	var layerWidth = '700';
	

	function ajax(scrollHeight) {
		var table = $('#TMaterial').dataTable();
		if(table) {
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
				"scrollY":scrollHeight,
				"scrollCollapse":true,
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/purchase?methodtype=search",
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
							{"data": "YSId", "defaultContent" : ''},
							{"data": "bomId", "defaultContent" : ''},
							{"data": "orderQuantity", "className" : 'cash'},
							{"data": "materialCost", "defaultContent" : '',"className" : 'cash'},
							{"data": "laborCost", "defaultContent" : '',"className" : 'cash'},
							{"data": "managementCost", "defaultContent" : '',"className" : 'cash'},
							{"data": "productCost", "defaultContent" : '',"className" : 'cash'},
							{"data": "totalCost", "defaultContent" : '',"className" : 'cash'},
							{"data": "planDate", "className" : 'td-center'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'},
							{"data": "status"},
						],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"];
		                    }},
				    		{"targets":10,"render":function(data, type, row){
				    			var rtn = "";
				    			var space = '&nbsp;';
				    			//rtn= "<a href=\"#\" onClick=\"ShowBomPlan('" + row["bomId"] + "')\">BOM表</a>";
				    			rtn= rtn+space+"<a href=\"#\" onClick=\"ShowBomPlan('" + row["bomId"] + "')\">采购方案</a>";
				    			return rtn;
				    		}},
				    		{
								"visible" : false,
								"targets" : [ 11 ]
							} 
			         	] 
			}
		);

	}

	
	function initEvent(){

		doSearch();
	
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

		//重设iframe高度
		iFramNoSroll()
		
	})	
	
	function doSearch() {	

		var scrollHeight = $(document).height() - 197; 
		ajax(scrollHeight);

	}
	
	
	function CreatePurchasePlan(bomId) {

		var url = '${ctx}/business/purchase?methodtype=detail&bomId=' + bomId;
		location.href = url;
	}

	function ShowBomPlan(bomId) {

		var url = '${ctx}/business/bom?methodtype=detailView&bomId=' + bomId;
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
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">

			<table aria-describedby="TSupplier_info" style="width: 100%;" id="TMaterial" class="display dataTable" cellspacing="0">
				<thead>						
					<tr class="selected">
						<th style="width: 10px;" aria-label="No:" class="dt-middle ">No</th>
						<th style="width: 55px;" class="dt-middle ">耀升编号</th>
						<th style="width: 100px;" aria-label="物料编号" class="dt-middle ">产品编号</th>
						<th style="width: 40px;" aria-label="物料编号" class="dt-middle ">订单数量</th>
						<th style="width: 60px;" aria-label="物料编号" class="dt-middle ">方案日期</th>
						<th style="width: 50px;" aria-label="操作" class="dt-middle ">操作</th>
						<th></th>
					</tr>
				</thead>
			</table>
	</div>
</div>
</div>
</body>
</html>
