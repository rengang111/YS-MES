<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>

<title>领料申请--订单一览</title>
<script type="text/javascript">

	function ajax(pageFlg,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var actionUrl = "${ctx}/business/requisition?methodtype=search";
		actionUrl = actionUrl + "&keyBackup=" + pageFlg;
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		
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
	         	"aaSorting": [[ 1, "DESC" ]],
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
							{"data": "YSId", "defaultContent" : ''},
							{"data": "materialId", "defaultContent" : ''},
							{"data": "materialName", "defaultContent" : ''},//3
							{"data": "orderDate", "defaultContent" : ''},
							{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-left'},
							{"data": "quantity", "defaultContent" : '0', "className" : 'td-right'},
							{"data": "team", "className" : 'td-left'},//7
							{"data": "statusName", "className" : 'td-center'},//8
							{"data": "storageDate", "className" : 'td-center'},//9
						],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"] ;				    			 
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["YSId"] + "')\">"+row["YSId"]+"</a>";
		    			return rtn;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			name = jQuery.fixedWidth(name,50);//true:两边截取,左边从汉字开始
		    			return name;
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : [7,8,9]
					}
	         	]
			}
		);

	}

	function initEvent(){
	
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

		ajax("","true");
		initEvent();
		$("#create").click(
				function() {			
			$('#purchaseForm').attr("action", "${ctx}/business/purchase?methodtype=insert");
			$('#purchaseForm').submit();
		});	
		
	})	
	
	function doSearch() {	

		ajax("purchaseplan","false");

	}

	
	function doShowDetail(YSId) {
		
		var url =  "${ctx}/business/requisition?methodtype=addinit&YSId="+YSId;
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

		<table id="TMaterial" class="display dataTable" style="width:100%">
			<thead>						
				<tr>
						<th style="width: 10px;" class="dt-middle ">No</th>
						<th style="width: 70px;" class="dt-middle ">耀升编号</th>
						<th style="width: 150px;" class="dt-middle ">产品编号</th>
						<th class="dt-middle ">产品名称</th>
						<th style="width: 50px;" class="dt-middle ">下单日期</th>
						<th style="width: 50px;" class="dt-middle ">订单交期</th>
						<th style="width: 60px;" class="dt-middle ">订单数量</th>
						<th style="width: 40px;" class="dt-middle ">业务组</th>
						<th style="width: 60px;" class="dt-middle ">订单状态</th>
						<th style="width: 50px;" class="dt-middle ">入库时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
