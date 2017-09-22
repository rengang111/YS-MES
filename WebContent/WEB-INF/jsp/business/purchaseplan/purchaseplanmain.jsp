<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>采购方案--订单基本数据</title>
<script type="text/javascript">

	function ajax(pageFlg,status,orderNature,col_no) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/order?methodtype=purchasePlanSearch&keyBackup="+pageFlg
					+"&status="+status
					+"&orderNature="+orderNature;

		var t = $('#TMaterial').DataTable({
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//每页显示条数设置
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			//"bSort":true,
			// "bFilter": false, //列筛序功能
			"ordering"	:true,
			"searching" : false,
			// "Info": true,//页脚信息
			// "bPaginate": true, //翻页功能
			"pagingType" : "full_numbers",
				"sAjaxSource" : url,
				"fnPreDrawCallback": function (oSettings) {

					//alert('2222222222');

		        },
				 "fnInitComplete": function (oSettings, json) {

			           // alert('DataTables has finished its initialisation.');

			        },
				 "fnDrawCallback": function (oSettings) {

			            //alert('DataTables 重绘了');

			        },
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
					{"data": "YSId", "defaultContent" : '',"className" : 'td-left'},
					{"data": "materialId", "defaultContent" : '',"className" : 'td-left'},
					{"data": "materialName", "defaultContent" : ''},
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-center'},
					{"data": "quantity", "defaultContent" : '0', "className" : 'td-right'},
					{"data": "totalQuantity", "defaultContent" : '0', "className" : 'td-right'},
					{"data": "statusName", "className" : 'td-center'},
					{"data": "storageDate", "className" : 'td-center'},//8
				],
				"columnDefs":[
		    		
		    		
		    		{"targets":0,"render":function(data, type, row){
		    				return row["rownum"];
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShow('"+ row["YSId"] + "','"+ row["materialId"] + "')\">"+row["YSId"]+"</a>";
		    			return rtn;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["materialName"];		    			
		    			name = jQuery.fixedWidth(name,40);		    			
		    			return name;
		    		}},
		    		{
						"visible" : false,
						"targets" : [col_no ]
					}
	         	] ,
	         	"aaSorting": [[ 1, "DESC" ]]
	    		
			});
		
		t.on('click', 'tr', function() {

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		
		
	}
	
	function YSKcheck(v,id){
		var zzFlag = "";
		if(id != null && id != ''){
			zzFlag = id.substr(2,3);
		}
		if(zzFlag == 'YSK') v = 0;//库存订单不显示明细内容
		return v;
		
	}
	
	function initEvent(){

		var keyBackup = $("#keyBackup").val();

		if(keyBackup ==""){

			ajax("","010","",8);
		}else{
			ajax("","","",8);
			
		}
	
	}

	$(document).ready(function() {

		
		initEvent();
		
	})	
	//订单状态
	function doSearchCustomer(type,col_no){
		ajax('orderMain',type,'',col_no);
	}
	
	function doSearch() {	

		ajax('orderMain','','',8);

	}
	

	function doShow(YSId,materialId) {

		var backFlag = 'purchasePlan';
		var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanAddInit&YSId=' 
				+ YSId+'&materialId='+materialId
				+'&backFlag='+backFlag;


		location.href = url;
	}

	
	
	
</script>
</head>

<body>
	<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition"  style='padding: 0px; margin: 10px;' >

					<input type="hidden" id="keyBackup" value="${keyBackup }" />
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
									style="width:50px" value="查询" onclick="doSearch();"/>查询
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">
				<div id="DTTT_container2" style="height:40px;float: left">
					<a  class="DTTT_button " onclick="doSearchCustomer('010',8);"><span>待合同</span></a>
					<a  class="DTTT_button " onclick="doSearchCustomer('020',8);"><span>待到料</span></a>
					<a  class="DTTT_button " onclick="doSearchCustomer('030',8);"><span>待交货</span></a>
					<a  class="DTTT_button " onclick="doSearchCustomer('040','');"><span>已入库</span></a>
				</div>
					<table id="TMaterial" class="display"  style="width:100%">
						<thead>						
							<tr>
								<th style="width: 10px;" class="dt-middle ">No</th>
								<th style="width: 90px;" class="dt-middle ">耀升编号</th>
								<th style="width: 150px;" class="dt-middle ">产品编号</th>
								<th class="dt-middle ">产品名称</th>
								<th style="width: 60px;" class="dt-middle ">订单交期</th>
								<th style="width: 60px;" class="dt-middle ">订单数量</th>
								<th style="width: 60px;" class="dt-middle ">需求数量</th>
								<th style="width: 70px;" class="dt-middle ">订单状态</th>
								<th style="width: 70px;" class="dt-middle ">交货时间</th>
							</tr>
						</thead>
					</table>
			</div>
		</div>
	</div>
</body>
</html>
