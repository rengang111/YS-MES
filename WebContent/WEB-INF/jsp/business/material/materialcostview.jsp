<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>物料的核算成本-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter1  = 0;
	var counter5  = 0;
	
	function documentaryAjax() {//材料成本

		var table = $('#documentary').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var materialId = '${material.materialId}';
		var actionUrl = "${ctx}/business/material?methodtype=getMaterialCostList&type=M&materialId="+materialId;

		var t = $('#documentary').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
			"async"		: false,
	        "ordering"  : false,
			"sAjaxSource" : actionUrl,
			dom : '<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : JSON.stringify($('#material').serializeArray()),// 要提交的表单
					success : function(data) {

						fnCallback(data);
						
						costComputer();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-left'},
			    {"data": "costName", "defaultContent" : '',"className" : ''},
			    {"data": "quantity", "defaultContent" : '',"className" : 'td-right'},
			    {"data": "price", "defaultContent" : '',"className" : 'td-right'},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},
			    {"data": "remarks", "defaultContent" : '',"className" : ''},
			    				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
	    			return rownum ;
	    		}}
		     ] ,			
		})
		
	};//材料成本
	
	function costComputer(){
		
		var materailCost = 0;	
		$('#documentary tbody tr').each (function (){
			var contractValue = $(this).find("td").eq(4).text();//
			contractValue= currencyToFloat(contractValue);
			materailCost = materailCost + contractValue;
		});	
		
		var processCost = 0;	
		$('#inspection tbody tr').each (function (){
			var contractValue = $(this).find("td").eq(2).text();//
			contractValue= currencyToFloat(contractValue);
			processCost = processCost + contractValue;
		});	
		
		var cost = materailCost + processCost;
	//	alert("materailCost:"+materailCost+" processCost:"+processCost+" cost:"+cost)
		$('#materailCost').text(floatToCurrency(materailCost));
		$('#processCost').text(floatToCurrency(processCost));
		$('#costView').text(floatToCurrency(cost));
		$('#cost').val(floatToCurrency(cost));
				
	}
	
	$(document).ready(function() {
		
		documentaryAjax();	//材料成本
		expenseAjax5();		//加工描述
		
		$("#doEdit").click(
				function() {
					var materialId='${material.materialId}';
					var url = "${ctx}/business/material?methodtype=materialCostEdit";
					url = url + "&materialId="+materialId;
					location.href = url;	
		});

	});

</script>


<script type="text/javascript">

function expenseAjax5() {//加工描述

	var table = $('#inspection').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var materialId = '${material.materialId}';
		var actionUrl = "${ctx}/business/material?methodtype=getProcessCostList&type=P&materialId="+materialId;

		var t = $('#inspection').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
			"async"		: false,
	        "ordering"  : false,
			"sAjaxSource" : actionUrl,
			dom : '<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : JSON.stringify($('#material').serializeArray()),// 要提交的表单
					success : function(data) {

						fnCallback(data);
						
						costComputer();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-center'},
			    {"data": "costName", "defaultContent" : '',"className" : ''},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},
			    {"data": "remarks", "defaultContent" : '',"className" : ''},
				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
	    			var rownum = row["rownum"];	    			
	    			return rownum ;
	    		}}
		     ] ,
			
		}).draw();		

};//检验费用

</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="material" method="POST" 
		id="material" name="material"   autocomplete="off">
		
	<input type="hidden" id="counter1" name="counter1" />
	<input type="hidden" id="counter5" name="counter5"/>
	
	<fieldset style="margin-top: -20px;">
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" style="width:100px;"><label>物料编号：</label></td>					
				<td style="width:120px;">${material.materialId}
			
				<td class="label" style="width:100px;"><label>核算成本：</label></td>					
				<td  style="width:100px;">
					<span id="costView">${material.materialCost}</span>
					<input type="hidden" id="cost" name="cost" value="${material.materialCost}"/></td>
				
				<td class="label" style="width:100px;"><label>物料名称：</label></td>				
				<td>${material.materialName}</td>
			</tr>						
		</table>
	</fieldset>	
	<fieldset  style="text-align: right;margin-top: -20px;">
		<button type="button" id="doEdit" class="DTTT_button">编辑</button>
	</fieldset>	
	<fieldset style="margin-top: -40px;">
		<span class="tablename"> 材料成本</span>
		<div class="list">
			<table id="documentary" class="display" >
				<thead>				
					<tr>
						<th width="10px">No</th>
						<th class="dt-center" width="300px">材料名称</th>
						<th class="dt-center" width="100px">材质用量</th>
						<th class="dt-center" width="100px">材质单价</th>
						<th class="dt-center" width="100px">总价</th>
						<th class="dt-center" >备注</th>
					</tr>
				</thead>
				<tfoot>			
					<tr>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<td style="text-align: right;"><div id="materailCost"></div></td>
						<td></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</fieldset>	
	<fieldset style="margin-top: -20px;">
		<span class="tablename"> 加工描述</span>
		<div class="list">
			<table id="inspection" class="display" >
				<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="300px">描述</th>
						<th class="dt-center" width="150px">成本</th>
						<th class="dt-center">备注</th>
					</tr>
				</thead>
				<tfoot>			
					<tr>
						<th></th>
						<th></th>
						<th style="text-align: right;"><div id="processCost"></div></th>
						<th></th>
					</tr>
				</tfoot>
			</table>
					
		</div>
	</fieldset>	
		
</form:form>

</div>
</div>

</body>
	
</html>
