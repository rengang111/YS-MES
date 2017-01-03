<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具出入详情</title>
<script type="text/javascript">

var validatorBaseInfo;
var layerHeight = "250";
var rowIndex = 0;

function ajaxMouldInoutLdList() {
	var table = $('#mouldInoutLdList').dataTable();
	
	if(table) {
		table.fnDestroy();
	}

	var t = $('#mouldInoutLdList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/mouldinoutsearch?methodtype=getMouldLendRegisterList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#mouldReturnBaseInfo").serializeArray();
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
								/*
								if (data.message != undefined) {
									alert(data.message);
								}
								*/
								
								fnCallback(data);

							},
							 error:function(XMLHttpRequest, textStatus, errorThrown){
				                 //alert(XMLHttpRequest.status);
				                 //alert(XMLHttpRequest.readyState);
				                 //alert(textStatus);
				             }
						})
					},
						
					"language": {
		        		"url":"${ctx}/plugins/datatables/chinese.json"
		        	},
					"columns" : [ 
						{"data" : "lendTime", "className" : 'td-center'},
						{"data" : "factoryName", "className" : 'td-center'},
						{"data" : "productModelNo", "className" : 'td-center'},
						{"data" : "productModelName", "className" : 'td-center'}, 
						{"data" : "brokerage", "className" : 'td-center'},
					],
					"columnDefs":[
				    ] 						
				});
	
	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

		var tr = $(this).closest('tr');
		
		var row = t.row(tr);

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(format(row.data())).show();
			tr.addClass('shown');
		}
	});
};

function ajaxMouldInoutRdList() {
	var table = $('#mouldInoutRdList').dataTable();
	
	if(table) {
		table.fnDestroy();
	}

	var t = $('#mouldInoutRdList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/mouldinoutsearch?methodtype=getMouldReturnRegisterList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#mouldReturnBaseInfo").serializeArray();
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
								/*
								if (data.message != undefined) {
									alert(data.message);
								}
								*/
								rowIndex = 0;
								
								fnCallback(data);

							},
							 error:function(XMLHttpRequest, textStatus, errorThrown){
				                 //alert(XMLHttpRequest.status);
				                 //alert(XMLHttpRequest.readyState);
				                 //alert(textStatus);
				             }
						})
					},
						
					"language": {
		        		"url":"${ctx}/plugins/datatables/chinese.json"
		        	},
					"columns" : [ 
						{"data" : "returnTime", "className" : 'td-center'},
						{"data" : "factoryName", "className" : 'td-center'},
						{"data" : "productModelNo", "className" : 'td-center'},
						{"data" : "productModelName", "className" : 'td-center'},
						{"data" : "result", "className" : 'td-center'},
						{"data" : "accepterName", "className" : 'td-center'},
						{"data" : "memo", "className" : 'td-center', 'bVisible': false},
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
			    			hiddenContent = row["returnTime"] + "<br><input type=hidden name='memo-" + rowIndex + "' id='memo-" + rowIndex + "' value='" + row["memo"] + "' />";
			    			rowIndex++;
							return hiddenContent
	                    }},
				    ] 						
				});
	
	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
		
		var tr = $(this).closest('tr');

		var row = t.row(tr);
		
		$('#memo').html($('#memo-' + row.index()).val());
	});

	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

		var tr = $(this).closest('tr');
		
		var row = t.row(tr);

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(format(row.data())).show();
			tr.addClass('shown');
		}
	});
};

function initEvent(){

}

$(window).load(function(){
	initEvent();
});

$(document).ready(function() {

	ajaxMouldInoutLdList();
	ajaxMouldInoutRdList();
	
})


</script>

</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
			<form:form modelAttribute="dataModels" id="mouldReturnBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<legend>模具出入-基本信息</legend>
				<div style="height:10px;"></div>
				<table class="form" width="850px">
					<tr>
						<td width="90px">产品型号：</td> 
						<td>
							<lable id="productModelId" class="required middle">${DisplayData.productModelNo}</lable>
						</td>
						<td width="90px">产品名称：</td> 
						<td>
							<lable id="productModelName" class="read-only">${DisplayData.productModelName}</lable>
						</td>
					</tr>
				</table>			
				
				<div  style="height:20px"></div>
				<legend>出借详情</legend>
				<div  style="height:10px"></div>
				<div class="form" >
					<table id="mouldInoutLdList" class="display" cellspacing="0" style="float:left;">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">出借时间</th>
								<th style="width: 80px;" class="dt-middle">出借工厂</th>
								<th style="width: 80px;" class="dt-middle">模具编号</th>
								<th style="width: 80px;" class="dt-middle">模具名称</th>
								<th style="width: 80px;" class="dt-middle">经手人</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
				<div  style="height:20px"></div>
				<legend>归还验收</legend>
				<div style="height:10px;"></div>
				<div style="float:left;display:inline;width:600px;">
					<div class="form" style="width:600px;">
						<table id="mouldInoutRdList" class="display" cellspacing="0">
							<thead>
								<tr class="selected">
									<th style="width: 80px;" class="dt-middle">归还时间</th>
									<th style="width: 80px;" class="dt-middle">归还工厂</th>
									<th style="width: 80px;" class="dt-middle">模具编号</th>
									<th style="width: 80px;" class="dt-middle">模具名称</th>
									<th style="width: 80px;" class="dt-middle">验收合格</th>
									<th style="width: 80px;" class="dt-middle">经手人</th>
									<th style="width: 80px;" class="dt-middle">memo</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
								</tr>
							</tfoot>
						</table>
					</div>					
				</div>
				<div style="float:right;">
					<textarea id='memo' name='memo' cols=35 rows=5 readonly >
					</textarea>
				</div>
			</form:form>
		</div>
</html>
