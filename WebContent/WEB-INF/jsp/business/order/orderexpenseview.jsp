<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>订单过程-开销查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

		
	
	function documentaryAjax() {//跟单费用

		var table = $('#documentary').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${order.YSId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=D&YSId="+YSId;

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
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {

						counter1 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);
						
						documentarySum();
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
			    {"data": "costName", "defaultContent" : '',"className" : 'td-left'},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},
			    {"data": "remarks", "defaultContent" : '',"className" : ''},
			    {"data": null, "className" : 'td-center', "defaultContent" : ''},
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
	    			return rownum;
	    		}} 
		     ]			
		})
		
	};//跟单费用
	
	$(document).ready(function() {
		
		documentaryAjax();	//跟单费用
		expenseAjax2();		//客户增减费用
		expenseAjax3();		//工厂（供应商）增减费用
		expenseAjax4();		//车间增减费用
		expenseAjax5();		//检验费用
		

		productPhotoView();//附件显示
		
	});

</script>

<script type="text/javascript">

	function goBack(){
		var url = "${ctx}/business/order?methodtype=orderExpenseInit";
		location.href = url;
	}
	
	function doEdit(){
		var YSId = '${order.YSId}';
		var url = "${ctx}/business/bom?methodtype=orderexpenseadd" + "&YSId=" + YSId;
		location.href = url;
	}
</script>

<script type="text/javascript">

function expenseAjax2() {//客户增减费用

	var table = $('#custmer').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${order.YSId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=C&YSId="+YSId;

		var t = $('#custmer').DataTable({
			
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
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {

						counter2 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);

						custmerSum();
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
			    {"data": "costName", "defaultContent" : '',"className" : 'td-left'},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},
			    {"data": "remarks", "defaultContent" : ''},
			    {"data": null, "className" : 'td-center'},
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
	    		return rownum;
	    			
	    		}}              
		           
		     ] ,
			
		}).draw();


};//ajax()

</script>

<script type="text/javascript">

function expenseAjax3() {//工厂（供应商）增减费用

	var table = $('#supplier').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${order.YSId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=S&YSId="+YSId;

		var t = $('#supplier').DataTable({
			
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
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {

						counter3 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);

						supplierSum();
						
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
			    {"data": "supplierId", "defaultContent" : '',"className" : 'td-left'},//供应商
			    {"data": "contractId", "defaultContent" : '',"className" : 'td-left'},//合同编号
			    {"data": "costName", "defaultContent" : '',"className" : 'td-left'},//增减内容3
			    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},//金额4
			    {"data": "remarks", "defaultContent" : ''},//备注5
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
	    			return rownum;
	    		}},
	    		{"targets":2,"render":function(data, type, row){
	    			var rtn = "";
	    			rtn= "<a href=\"###\" onClick=\"doShowContract('"+ row["contractId"] + "')\">"+row["contractId"]+"</a>";
	    			return rtn;
	    		}}           
		           
		     ] ,
			
		})
		
		t.on('change', 'tr td:nth-child(5)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(4).find("input");//金额			
			$cost.val(floatToCurrency($cost.val()));
		});
	
};//供应商

</script>

<script type="text/javascript">

function expenseAjax4() {//车间增减费用
	var table = $('#workshop').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var YSId = '${order.YSId}';
	var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=W&YSId="+YSId;

	var t = $('#workshop').DataTable({
		
		"processing" : false,
		"retrieve"   : false,
		"stateSave"  : false,
		"serverSide" : false,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
		"async"		: false,
        "ordering"  : false,
		"sAjaxSource" : actionUrl,
		"zeroRecords":"",
		"dom" : '<"clear">lt',
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"type" : "POST",
				"contentType": "application/json; charset=utf-8",
				"dataType" : 'json',
				"url" : sSource,
				"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
				success : function(data) {

					counter4 = data['recordsTotal'];//记录总件数
					
					fnCallback(data);

					workshopSum();
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
		    {"data": "costName", "defaultContent" : '',"className" : 'td-left'},
		    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},
		    {"data": "supplierId", "defaultContent" : '',"className" : 'td-left'},
		   // {"data": "cost", "defaultContent" : '',"className" : 'td-center'},
		    {"data": "remarks", "defaultContent" : ''},
		    {"data": null, "className" : 'td-center'},
		],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
    			var rownum = row["rownum"];
    			return rownum;    			
    		}}             
	           
	     ] ,
		
	}).draw();

};//ajax()

</script>

<script type="text/javascript">

function expenseAjax5() {//检验费用

	var table = $('#inspection').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${order.YSId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=E&YSId="+YSId;

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
			"dom"		: '<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {

						counter5 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);

						inspectionSum();
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
			    {"data": "costName", "defaultContent" : '',"className" : 'td-left'},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},
			    {"data": "person", "defaultContent" : '',"className" : 'td-center'},
			   	{"data": "remarks", "defaultContent" : ''},
			    {"data": null, "className" : 'td-center'},
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
	    			var rownum = row["rownum"];
	    			return rownum;	    			
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

	<form:form modelAttribute="bomForm" method="POST"
		id="bomForm" name="bomForm"  k="off">
		
		<form:hidden path="counter1" />
		<form:hidden path="counter2" />
		<form:hidden path="counter3" />
		<form:hidden path="counter4" />
		<form:hidden path="counter5" />
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:120px;">${order.YSId}
						<form:hidden path="bomPlan.ysid" value="${order.YSId}"/></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;">${order.materialId}
						<form:hidden path="bomPlan.materialid" value="${order.materialId}"/></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td colspan="3">${order.materialName}</td>
				</tr>
				<tr>
					<td class="label"><label>ＰＩ编号：</label></td>
					<td>${order.PIId}</td>

					<td class="label"><label>订单数量：</label></td>
					<td><span id="quantity">${order.quantity}</span></td>
						
					<td class="label"><label>客户名称：</label></td>
					<td>${order.customerFullName}</td>
					
					<td class="label" style="width:100px;"><label>返还数量：</label></td>				
					<td></td>
				</tr>							
			</table>
		</fieldset>
		<fieldset style="margin-top: -20px;">
			<table class="form" id="table_form2">
				<tr>
					<th class="td-center"><label>车间增减费用</label></th>
					<th class="td-center"><label>供应商增减</label></th>
					<th class="td-center"><label> 客户增减</label></th>
					<th class="td-center"><label>检验费用</label></th>
					<th class="td-center"><label>跟单费用</label></th>
				</tr>
				<tr>
					<td class="td-center"><span id="workshopSum"></span> </td>
					<td class="td-center"><span id="supplierSum"></span> </td>
					<td class="td-center"><span id="custmerSum"></span> </td>
					<td class="td-center"><span id="inspectionSum"></span> </td>
					<td class="td-center"><span id="documentarySum"></span></td>
				</tr>								
			</table>
		</fieldset>
			<div style="text-align: right;width: 98%;margin-top: -10px;">
				<button type="button" id="insert" onclick="doEdit();" class="DTTT_button">编辑</button>
				<button type="button" id="goBack2" onclick="goBack();" class="DTTT_button">返回</button>
			</div>
		<fieldset>
			<span class="tablename"> 车间增减费用</span>
				<div class="list">	
				<table id="workshop" class="display" >
					<thead>				
					<tr>
						<th width="30px">No</th>
						<th class="dt-center" width="250px">增减内容</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" width="150px">供应商</th>
						<!-- <th class="dt-center" width="150px">供应商承担金额</th> -->
						<th class="dt-center" >备注</th>
						<th class="dt-center" width="50px"></th>
					</tr>
					</thead>
				</table>
			</div>
		</fieldset>	
		
		
		<fieldset>
			<span class="tablename"> 工厂（供应商）增减费用</span>
				<div class="list">		
				<table id="supplier" class="display" >
					<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="100px">供应商</th>
						<th class="dt-center" width="150px">合同编号</th>
						<th class="dt-center" width="200px">增减内容</th>
						<th class="dt-center" width="100px">金额</th>
						<th class="dt-center">备注</th>
					</tr>
					</thead>					
				</table>
			</div>
		</fieldset>
		
		<fieldset>
			<span class="tablename"> 客户增减费用</span>
			<div class="list">
				<table id="custmer" class="display" >
					<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="300px">增减内容</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" >备注</th>
						<th class="dt-center" width="50px"></th>
					</tr>
					</thead>
				</table>
			</div>
		</fieldset>	
		
		<fieldset>
			<span class="tablename"> 检验费用</span>
			<div class="list">
				<table id="inspection" class="display" >
					<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="300px">费用名称</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" width="100px">申报人</th>
						<th class="dt-center">备注</th>
						<th class="dt-center" width="50px"></th>
					</tr>
					</thead>
				</table>
						
			</div>
		</fieldset>	
		
		<fieldset>
			<span class="tablename"> 跟单费用</span>
			<div class="list">
				<table id="documentary" class="display" >
					<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="300px">费用名称</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" >备注</th>
						<th class="dt-center" width="50px"></th>
					</tr>
					</thead>
				</table>
			</div>
		</fieldset>	
	<fieldset>
	<legend> 附件 </legend>
	<div class="list">
		<div class="" id="subidDiv" style="min-height: 300px;">
			<table id="productPhoto" class="phototable">
				<tbody><tr class="photo"><td></td><td></td></tr></tbody>
			</table>
		</div>
	</div>	
</fieldset>
		
</form:form>

<script type="text/javascript">
//供应商
function supplierSum(){	
	var contract = 0;	
	$('#supplier tbody tr').each (function (){
		var contractValue = $(this).find("td").eq(4).text();//
		contractValue= currencyToFloat(contractValue);
		contract = contract + contractValue;
	});	
	$('#supplierSum').html(floatToCurrency(contract));
}
//车间
function workshopSum(){	
	var contract = 0;	
	$('#workshop tbody tr').each (function (){
		var contractValue = $(this).find("td").eq(2).text();//
		contractValue= currencyToFloat(contractValue);
		contract = contract + contractValue;
	});	
	$('#workshopSum').html(floatToCurrency(contract));
}
//客户
function custmerSum(){	
	var contract = 0;	
	$('#custmer tbody tr').each (function (){
		var contractValue = $(this).find("td").eq(2).text();//
		contractValue= currencyToFloat(contractValue);
		contract = contract + contractValue;
	});	
	$('#custmerSum').html(floatToCurrency(contract));
}
//跟单
function documentarySum(){	
	var contract = 0;	
	$('#documentary tbody tr').each (function (){
		var contractValue = $(this).find("td").eq(2).text();//
		contractValue= currencyToFloat(contractValue);
		contract = contract + contractValue;
	});	
	$('#documentarySum').html(floatToCurrency(contract));
}
//检验费用
function inspectionSum(){	
	var contract = 0;	
	$('#inspection tbody tr').each (function (){
		var contractValue = $(this).find("td").eq(2).text();//
		contractValue= currencyToFloat(contractValue);
		contract = contract + contractValue;
	});	
	$('#inspectionSum').html(floatToCurrency(contract));
}

function doShowContract(contractId) {

	var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
	callProductDesignView("合同明细",url);
	
}

</script>
<script type="text/javascript">

function productPhotoView() {

	var YSId = $('#bomPlan\\.ysid').val();
	$.ajax({
		"url" :"${ctx}/business/order?methodtype=orderExpenseProductPhoto"+"&YSId="+YSId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["productFileCount"];
			//alert(countData)
			photoView('productPhoto','uploadProductPhoto',countData,data['productFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
         	alert(errorThrown)
		 }
	});
	
}//产品图片

function photoView(id, tdTable, count, data) {

	var row = 0;
	for (var index = 0; index < count; index++) {

		var path = '${ctx}' + data[index];
		var pathDel = data[index];
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td>';
		trHtml += '<table style="width:400px;height:300px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr><td>';
		trHtml += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
		trHtml += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
		trHtml += '</a>';
		trHtml += '</td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';

		index++;
		if (index == count) {
			//因为是偶数循环,所以奇数张图片的最后一张为空
			var trHtmlOdd = '<table style="width:400px;margin: auto;" class="">';
			trHtmlOdd += '<tr><td></td></tr>';	
			trHtmlOdd += '</table>';
		} else {
			path = '${ctx}' + data[index];
			pathDel = data[index];

			var trHtmlOdd = '<table style="width:400px;height:300px;margin: auto;" class="form">';
			trHtmlOdd += '<tr><td>';
			trHtmlOdd += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
			trHtmlOdd += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
			trHtmlOdd += '</a>'
			trHtmlOdd += '</td></tr>';
			trHtmlOdd += '</table>';
		}
		trHtml += '<td>';
		trHtml += trHtmlOdd;
		trHtml += '</td>';
		trHtml += "</tr>";

		$('#' + id + ' tr.photo:eq(' + row + ')').after(trHtml);
		row++;

	}
}

</script>
</div>
</div>

</body>
	
</html>
