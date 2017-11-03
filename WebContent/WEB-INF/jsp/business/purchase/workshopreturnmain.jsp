<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>

<title>车间退货--订单一览</title>
<script type="text/javascript">

	function ajax(pageFlg,sessionFlag,stateSave) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var status = '';
		var actionUrl =  "${ctx}/business/workshopReturn?methodtype=searchWorkshopReturn&sessionFlag="
				+sessionFlag+"&status="+status,

		actionUrl = actionUrl + "&keyBackup=" + pageFlg;
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"pagingType" : "full_numbers",
	         	"aaSorting": [[ 1, "DESC" ]],
	         	"bAutoWidth":false,
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
					{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},
					{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},
					{"data": "materialName", "defaultContent" : ''},//3
					{"data": "orderDate", "defaultContent" : ''},
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-left'},
					{"data": "totalQuantity", "defaultContent" : '0', "className" : 'td-right'},
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
						"targets" : [7,9]
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

		ajax("","true",true);
		initEvent();
		$("#create").click(
				function() {			
			$('#purchaseForm').attr("action", "${ctx}/business/purchase?methodtype=insert");
			$('#purchaseForm').submit();
		});	
		
	})	
	
	function doSearch() {	

		ajax("purchaseplan","false",false);

	}

	
	function doShowDetail(YSId) {
		
		var url =  "${ctx}/business/workshopReturn?methodtype=createWorkshopRentunInit&YSId="+YSId;
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

		<table id="TMaterial" class="display" >
			<thead>						
				<tr>
					<th style="width: 10px;">No</th>
					<th style="width: 70px;">耀升编号</th>
					<th style="width: 120px;">产品编号</th>
					<th>产品名称</th>
					<th style="width: 50px;">下单日期</th>
					<th style="width: 50px;">订单交期</th>
					<th style="width: 60px;">订单数量</th>
					<th style="width: 40px;">业务组</th>
					<th style="width: 50px">订单状态</th>
					<th style="width: 50px;">入库时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
