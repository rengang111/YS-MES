<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>应付款--二级审批一览</title>
<script type="text/javascript">

	function searchAjax(approvalStatus,sessionFlag,finishStatus) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/payment?methodtype=approvalSearch2";
		url = url + "&sessionFlag="+sessionFlag;
		url = url + "&approvalStatus="+approvalStatus;
		url = url + "&finishStatus="+finishStatus;

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
				"aaSorting": [[ 1, "ASC" ]],
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
					{"data": "paymentId", "defaultContent" : '', "className" : 'td-left'},//1 付款申请编号
					{"data": "supplierId", "defaultContent" : '', "className" : 'td-left'},//2
					{"data": "supplierName", "defaultContent" : '', "className" : 'td-left'},//3				
					{"data": "contractIds", "defaultContent" : '', "className" : 'td-left'},//4		
					{"data": "totalPayable", "defaultContent" : '0', "className" : 'td-right'},//5
					{"data": "requestDate", "defaultContent" : '', "className" : 'td-center'},//6
					{"data": "approvalDate",  "defaultContent" : '（待审核）', "className" : 'td-center'},//7
					{"data": "approvalStatus","defaultContent" : '***',"className" : 'td-center'},//8
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"];
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = row["paymentId"];//		    			
			    		rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["paymentId"] + "')\">" + row["paymentId"] + "</a>";		    			
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			return jQuery.fixedWidth(data,16);		
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			return jQuery.fixedWidth(data,35);		
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			return jQuery.fixedWidth(data,20);		
		    		}},
		    		{ "bSortable": false, "aTargets": [ 0,4 ] },
		    		{
						"visible" : false,
						"targets" : []
					}
	           
	         ] 
		});
	}

	$(document).ready(function() {
		
		searchAjax("","true","021");//021：待二级审核
		
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		

		buttonSelectedEvent();//按钮选择式样

		$('#defutBtn').removeClass("start").addClass("end");
	})	
	

	function doSearch() {	
		
		searchAjax("","false","");

	}
	
	function doSearch2(approvalStatus,finishStatus) {	

		$("#keyword1").val("");
		$("#keyword2").val("");
		searchAjax(approvalStatus,"false",finishStatus);

	}
	
	function doCreate2(contractId) {
	
		var url = '${ctx}/business/payment?methodtype=addinit';
		url = url +"&contractIds="+contractId;
		location.href = url;
		
	}
	
	function doShowDetail(paymentId) {

		var url = '${ctx}/business/payment?methodtype=approvalInit2' + '&paymentId='+ paymentId;
		
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
					<a class="DTTT_button box"  onclick="doSearch2('','021');" id="defutBtn"><span>&nbsp;二级待审&nbsp;</span></a>
					<!-- a class="DTTT_button" onclick="doSearch2('020');"><span>&nbsp;通过&nbsp;</span></a-->
					<a class="DTTT_button box" onclick="doSearch2('030','060');"><span>&nbsp;不通过&nbsp;</span></a>
				</div>
				<table style="width: 100%;" id="TMaterial" class="display">
					<thead>						
						<tr>
							<th width="30px">No</th>
							<th width="80px">付款申请编号</th>
							<th width="70px">供应商</th>
							<th>供应商名称</th>
							<th width="120px">关联合同</th>
							<th width="70px">应付款</th>
							<th width="60px">申请日期</th>
							<th width="60px">审核日期</th>
							<th width="50px">审核结果</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>