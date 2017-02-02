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

<title>采购合同一览</title>
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
				"sAjaxSource" : "${ctx}/business/contract?methodtype=search",
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
					{"data": "contractId", "defaultContent" : ''},
					{"data": "productId"},
					{"data": "productName", "defaultContent" : ''},
					{"data": "supplierId", "defaultContent" : ''},
					{"data": "fullName", "defaultContent" : ''},
					{"data": "purchaseDate", "defaultContent" : ''},
					{"data": "deliveryDate", "defaultContent" : ''},
					{"data": "total", "defaultContent" : '',"className" : 'td-right'},
					{"data": null, "defaultContent" : '',"className" : 'td-center'},
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"];
                    }},
		    		{"targets":9,"render":function(data, type, row){
		    			
		    			return "<a href=\"#\" onClick=\"doShow('" + row["contractId"] + "')\">查看</a>";			    			
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["productName"];				    			
		    			if(name != null) name = jQuery.fixedWidth(name,10);
		    			return name;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			var name = row["fullName"];
		    			if(name != null) name = jQuery.fixedWidth(name,15);
		    			return name;
		    		}}
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

	function doShow(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
		location.href = url;
	}
	
	
</script>
</head>

<body class="panel-body">
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

		<div id="TSupplier_wrapper" class="dataTables_wrapper">
			<table aria-describedby="TSupplier_info" style="width: 100%;" id="TMaterial" class="display dataTable" cellspacing="0">
				<thead>						
					<tr class="selected">
						<th style="width: 10px;" aria-label="No:" class="dt-middle ">No</th>
						<th style="width: 120px;" aria-label="物料编号" class="dt-middle ">合同编号</th>
						<th style="width: 100px;" aria-label="物料编号" class="dt-middle ">产品编号</th>
						<th style="width: 120px;" aria-label="物料编号" class="dt-middle ">产品名称</th>
						<th style="width: 80px;" aria-label="物料编号" class="dt-middle ">供应商</th>
						<th style="width: 100px;" aria-label="物料编号" class="dt-middle ">供应商名称</th>
						<th style="width: 60px;" aria-label="物料编号" class="dt-middle ">下单日期</th>
						<th style="width: 60px;" aria-label="物料编号" class="dt-middle ">合同交期</th>
						<th style="width: 80px;" aria-label="物料编号" class="dt-middle ">合同金额</th>
						<th style="width: 30px;" aria-label="操作" class="dt-middle ">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>
