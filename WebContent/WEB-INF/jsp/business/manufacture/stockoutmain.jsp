<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>
<title>料件出库--一览</title>
<script type="text/javascript">

	function ajax(status,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var key1 = $("#keyword1").val();
		var key2 = $("#keyword1").val();
		var key = myTrim(key1)+myTrim(key2);
		//if(key == "")
		//	status = "010";

		var actionUrl = "${ctx}/business/stockout?methodtype=search";
		actionUrl = actionUrl + "&sessionFlag=" + sessionFlag;
		actionUrl = actionUrl + "&requisitionSts=" + status;
		
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"autoWidth"	:false,
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
							{"data": "requisitionId", "defaultContent" : '', "className" : 'td-center'},
							{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},
							{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},//3
							{"data": "materialName", "defaultContent" : ''},//4
							{"data": "requisitionUserName", "defaultContent" : '', "className" : 'td-center'},//5 领料申请者
							{"data": "checkOutDate", "defaultContent" : '', "className" : 'td-right'},//6出库时间
							{"data": "keepUser", "defaultContent" : '', "className" : 'td-right'},//7仓管员
						],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"] ;				    			 
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["YSId"] + "','"+ row["requisitionId"] + "')\">"+row["requisitionId"]+"</a>";
		    			return rtn;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["materialId"];
		    			if(name == null){
		    				name="（自制件领料）";
		    			}
		    			
		    			return name;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			if(name == null){
		    				name="（自制件领料）";
		    			}else{
			    			name = jQuery.fixedWidth(name,50);//true:两边截取,左边从汉字开始
		    				
		    			}
		    			
		    			return name;
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			var val = row["checkOutDate"];
		    			if(val == null || val=="")
		    				val = "未出库";
		    			return val;
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			var val = row["checkOutDate"];
		    			if(val == null || val=="")
		    				val = "未出库";
		    			return val;
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : [7]
					}
	         	]
			}
		);
		

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

		ajax("020","true");

	    buttonSelectedEvent();//按钮点击效果
	})	
	
	function doSearch() {	

		ajax("","false");
	}

	
	function doShowDetail(YSId,requisitionId) {
		
		var url =  "${ctx}/business/stockout?methodtype=addinit&YSId="+YSId+"&requisitionId="+requisitionId;
		location.href = url;
	}
	
	function selectContractByDate(type){
		
		ajax(type,"false");
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
		<div id="DTTT_container" align="left" style="height:40px;width:50%">
			<a class="DTTT_button DTTT_button_text box" onclick="selectContractByDate('020');">待出库</a>
			<a class="DTTT_button DTTT_button_text box" onclick="selectContractByDate('030');">已出库</a>
		</div>
	
		<table id="TMaterial" class="display">
			<thead>						
				<tr>
						<th style="width: 10px;">No</th>
						<th style="width: 70px;">领料单编号</th>
						<th style="width: 70px;">耀升编号</th>
						<th style="width: 120px;">产品编号</th>
						<th>产品名称</th>
						<th style="width: 70px;">料件申请人</th>
						<th style="width: 50px;">出库时间</th>
						<th style="width: 50px;">仓管员</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
