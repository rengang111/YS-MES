<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>应付款--完成一览</title>
<script type="text/javascript">

	function ajax(type,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/payment?methodtype=finishSearch";
		url = url + "&sessionFlag="+sessionFlag;
		url = url + "&finishStatus="+type;

		var t = $('#TMaterial').DataTable({
				"paging": true,
				"iDisplayLength" : 50,
				"lengthChange":false,
				//"lengthMenu":[10,150,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"ordering "	:true,
				"searching" :false,
				"stateSave" :false,
	         	"bAutoWidth":false,
				"pagingType" : "full_numbers",
				//"retrieve" : true,
				"sAjaxSource" : url,
				//"scrollY":scrollHeight,
				//"scrollCollapse":false,
				// "aaSorting": [[ 1, "DESC" ]],
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
							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);

							$("input[aria-controls='TMaterial']").css("height","25px");
							$("input[aria-controls='TMaterial']").css("width","200px");
							$("#TMaterial_filter").css("float","left");

						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null,"className" : 'td-center'},//0
					{"data": "paymentHistoryId", "defaultContent" : '', "className" : 'td-left'},//1 付款编号
					{"data": "paymentId", "defaultContent" : '', "className" : 'td-left'},//2 申请单编号
					{"data": "supplierId", "defaultContent" : '', "className" : 'td-left'},//3
					{"data": "supplierName", "defaultContent" : '', "className" : 'td-left'},//4		
					{"data": "requestDate", "defaultContent" : '0', "className" : 'td-center'},//5
					{"data": "approvalDate", "defaultContent" : '', "className" : 'td-center'},//6
					{"data": "finishDate", "defaultContent" : '（未付款）', "className" : 'td-center'},//7
					{"data": "paymentAmount", "defaultContent" : '0', "className" : 'td-right'},//8			
					{"data": "totalPayable", "defaultContent" : '', "className" : 'td-right'},//9	
					{"data": "finishStatus", "className" : 'td-center'},//10
				
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"];
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";//
		    			var paymentId=row["paymentId"];
		    			var id = data;
		    			if(data =="" || data == null){
		    				id= "（未付款）";		    			 				
		    			}
		    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ paymentId + "')\">" + id + "</a>";		    					    				
		    			
					    return rtn;
					    
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			return jQuery.fixedWidth(data,35);		
		    		}},
		    		{ "bSortable": false, "aTargets": [ 0 ] },
		    		{
						"visible" : false,
						"targets" : []
					}
	           
	         ] 
		});
		
	}

	$(document).ready(function() {
		ajax("030","true");
		
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		
	})	
	

	function doSearch() {	
		
		ajax("","false");

	}
	
	function doSearch2(type) {	
		
		ajax(type,"false");

	}
	
	function doCreate2(contractId) {
	
		var url = '${ctx}/business/payment?methodtype=addinit';
		url = url +"&contractIds="+contractId;
		location.href = url;
		
	}
	
	function doShowDetail(paymentId) {

		var url = '${ctx}/business/payment?methodtype=finishAddOrView' + '&paymentId='+ paymentId;
		
		location.href = url;
	}

	function doShowContract(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("采购合同",url);
	}	
	
</script>
</head>

<body>
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition"  style='padding: 0px; margin: 10px;' >
					
					<input type="hidden" id="makeType" value="${makeType }" />

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
									style="width:50px" value="查询" onclick="doSearch();">查询</button>
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div style="height:10px"></div>
		
			<div class="list">					
				<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:left">
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2('030');"><span>待付款</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2('040');"><span>分批付款中</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2('050');"><span>已完成</span></a>
				</div>
				<table style="width: 100%;" id="TMaterial" class="display">
					<thead>						
						<tr>
							<th width="20px">No</th>
							<th width="80px">付款单</th>
							<th width="70px">申请单</th>
							<th width="70px">供应商</th>
							<th>供应商名称</th>
							<th width="60px">申请日期</th>
							<th width="60px">审核日期</th>
							<th width="60px">付款日期</th>
							<th width="60px">付款金额</th>
							<th width="70px">应付款</th>
							<th width="50px">状态</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
