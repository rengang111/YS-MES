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
			 "iDisplayLength" : 50,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"pagingType" : "full_numbers",
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
				{"data": "YSId", "defaultContent" : ''},
				{"data": "productId"},
				{"data": "productName", "defaultContent" : ''},
				{"data": "contractId", "defaultContent" : ''},
				{"data": "supplierId", "defaultContent" : ''},
				{"data": "purchaseDate", "defaultContent" : ''},
				{"data": "deliveryDate", "defaultContent" : ''},
				{"data": "total", "defaultContent" : '',"className" : 'td-right'},
			
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                   }},
	    		{"targets":1,"render":function(data, type, row){
	    			
	    			return "<a href=\"###\" onClick=\"doShowYS('" + row["YSId"] + "')\">"+row["YSId"]+"</a>";			    			
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			
	    			return "<a href=\"###\" onClick=\"doShowControct('" + row["contractId"] + "')\">"+row["contractId"]+"</a>";			    			
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = row["productName"];				    			
	    			if(name != null) name = jQuery.fixedWidth(name,25);
	    			return name;
	    		}}
         	] 
		});
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
	
	
	function doShowYS(YSId) {

		var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;
		
		location.href = url;
	}

	function doShowControct(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
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

			<table id="TMaterial" class="display dataTable" >
				<thead>						
					<tr>
						<th style="width: 10px;" class="dt-middle ">No</th>
						<th style="width: 70px;"  class="dt-middle ">耀升编号</th>
						<th style="width: 100px;"  class="dt-middle ">产品编码</th>
						<th  class="dt-middle ">产品名称</th>
						<th style="width: 100px;"  class="dt-middle ">合同编号</th>
						<th style="width: 80px;"  class="dt-middle ">供应商</th>
						<th style="width: 60px;"  class="dt-middle ">下单日期</th>
						<th style="width: 60px;"  class="dt-middle ">合同交期</th>
						<th style="width: 60px;"  class="dt-middle ">合同数量</th>
					</tr>
				</thead>
			</table>
	</div>
</div>
</div>
</body>
</html>
