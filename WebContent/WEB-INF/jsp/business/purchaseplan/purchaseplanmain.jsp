<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>采购方案--订单基本数据</title>
<script type="text/javascript">

	function ajax(sqlFlag,materialType,sessionFlag,col_no,status) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/purchasePlan?methodtype=search&sessionFlag="+sessionFlag
					+"&sqlFlag="+sqlFlag
					+"&status="+status
					+"&materialType="+materialType;

		var t = $('#TMaterial').DataTable({
			"paging": true,
			"lengthChange":false,
			"lengthMenu"  :[50,100,200],//每页显示条数设置
			"processing" : true,
			"serverSide" : true,
			"stateSave"  : false,
			"ordering"	 : true,
			"searching"  : false,
			"pagingType" : "full_numbers",
			"sAjaxSource" : url,
			//"bSort":true,
			// "bFilter": false, //列筛序功能
			// "Info": true,//页脚信息
			// "bPaginate": true, //翻页功能			
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
				{"data": "totalQuantity", "defaultContent" : '0', "className" : 'td-right'},
				{"data": null, "className" : 'td-center', "defaultContent" : ''},
			],
			"columnDefs":[	    		
	    		{"targets":0,"render":function(data, type, row){
	    				return row["rownum"];
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var rtn = "";
	    			var ysid = row["YSId"];
	    			var peiYsid = row["peiYsid"];
	    			var orderType = row["orderType"];
	    			//if(orderType == '020'){
	    			//	ysid = peiYsid;
	    			//}
	    			rtn= "<a href=\"###\" onClick=\"doShow('"+ 
	    					ysid 				+ "','"+ 
	    					peiYsid				+ "','"+ 
	    					row["materialId"] 	+ "','"+ 
	    					row["PIId"] 		+ "','"+ 
	    					row["orderType"] 	+ "','"+ 
	    					row["planYsid"] + "')\">"	+ row["YSId"]+"</a>";
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = row["materialName"];		    			
	    			name = jQuery.fixedWidth(name,60);		    			
	    			return name;
	    		}},
	    		{"visible" : false,"targets" : [ ]
				},
				{"bSortable": false, "aTargets": [ 0,6 ] 
                }
         	] ,
         	"aaSorting": [[ 1, "DESC" ]]
	    		
		}).draw();
		
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


	$(document).ready(function() {

		ajax("new","","true",8,"010");

	    buttonSelectedEvent();//按钮点击效果
		
	})	
	
	//订单状态
	function doSearchCustomer(sqlFlag,type,col_no){
		$("#keyword1").val('');
		$("#keyword2").val('');
		ajax(sqlFlag,type,'false',col_no,'010');
	}
	
	function doSearch() {	
		$('.box').removeClass('end');
		ajax('new','','false',8,'');

	}
	

	function doShow(YSId,peiYsid,materialId,PIId,orderType,planYsid) {

		var backFlag = 'purchasePlan';
		var action = 'purchasePlanAddInit';
		if(orderType == '020'){//配件订单
			if(planYsid == ''){
				action = 'purchasePlanPeiAddInit';//没有采购方案
				YSId="";
				peiYsid="";
			}else{
				action = 'showPurchasePlanPei';//已有采购方案
			}
		}else{//常规订单
			if(planYsid == ''){
				action = 'purchasePlanAddInit';//没有采购方案
			}else{
				action = 'showPurchasePlan';//已有采购方案
			}
		}			

		var url = '${ctx}/business/purchasePlan?methodtype='+action
				+'&YSId=' + YSId
				+'&peiYsid='+peiYsid
				+'&materialId='+materialId
				+'&PIId='+PIId
				+'&orderType='+orderType
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
									style="width:50px" value="查询" onclick="doSearch();">查询</button>
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">
				<div id="DTTT_container2" style="height:40px;float: left">
					<a  class="DTTT_button DTTT_button_text box" onclick="doSearchCustomer('new','',8);">新合同</a>&nbsp;&nbsp;
					<a  class="DTTT_button box" onclick="doSearchCustomer('','yszz',8);">待自制品合同</a>
					<a  class="DTTT_button box" onclick="doSearchCustomer('','order',8);">待订购件合同</a>
					<a  class="DTTT_button box" onclick="doSearchCustomer('','package',8);">待包装品合同</a>&nbsp;&nbsp;
					<a  class="DTTT_button box" onclick="doSearchCustomer('contract','',8);">合同全部完成</a>
				</div>
					<table id="TMaterial" class="display"  style="width:100%">
						<thead>						
							<tr>
								<th style="width: 30px;" class="dt-middle ">No</th>
								<th style="width: 90px;" class="dt-middle ">耀升编号</th>
								<th style="width: 150px;" class="dt-middle ">产品编号</th>
								<th class="dt-middle ">产品名称</th>
								<th style="width: 60px;" class="dt-middle ">订单交期</th>
								<th style="width: 80px;" class="dt-middle ">需求数量</th>
								<th style="width: 30px;" class="dt-middle "></th>
							</tr>
						</thead>
					</table>
			</div>
		</div>
	</div>
</body>
</html>
