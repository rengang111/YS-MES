<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>直接出库一览页面</title>
<script type="text/javascript">

	function ajax(requisitionSts,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/requisition?methodtype=materialRequisitionSearch"
				+"&sessionFlag="+sessionFlag+"&requisitionSts="+requisitionSts;

		var t = $('#TMaterial').DataTable({
				"paging": true,
				 "iDisplayLength" : 50,
				"lengthChange":false,
				//"lengthMenu":[10,150,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : url,
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
							{"data": null,"className" : 'td-center'},
							{"data": "requisitionId","className" : 'td-left'},
							{"data": "materialId"},
							{"data": "materialName"},
							{"data": "unit","className" : 'td-center'},
							{"data": "quantity","className" : 'td-right'},
							{"data": "requisitionDate","className" : 'td-center'},
							{"data": "checkOutDate","className" : 'td-center', "defaultContent" : '（未出库）'},
							{"data": "requisitionSts","className" : 'td-center'},
							
						],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"];
		                    }},
				    		{"targets":1,"render":function(data, type, row){
				    			var rtn = "";
				    			rtn= "<a href=\"###\" onClick=\"doShow('" + row["recordId"] +"')\">" + data + "</a>";
				    			return rtn;
				    		}},
				    		{"targets":3,"render":function(data, type, row){
				    			return jQuery.fixedWidth(data,46);		
				    		}}
				    		
			           
			         ] 
			}
		);

	}

	


	$(document).ready(function() {
		
		ajax("020","true");
	
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

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		ajax("","false");

	}
	
	function doShow(recordId) {

		var url = '${ctx}/business/requisition?methodtype=materialRequisitionView'
				+"&recordId="+recordId;

		location.href = url;
	}

	function doCreate() {

		var url = '${ctx}/business/requisition?methodtype=materialReqeuisitionAddInit';

		location.href = url;
	}


	function doSearch2(type) {	

		
		$("#keyword1").val("");
		$("#keyword2").val("");
		
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
									style="width:50px" value="查询" onclick="doSearch();">查询</button>
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
			<div class="list">					
				<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:left">
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2('020');"><span>待领料</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2('030');"><span>已出库</span></a>
				</div>
				<div style="height: 40px;margin-bottom: -15px;float:right">
					<button type="button" id="zzcreate" class="DTTT_button" 
						style="width:120px" onclick="doCreate();">领料申请</button>
				</div>
				<table  style="width: 100%;" id="TMaterial" class="display dataTable">
					<thead>						
						<tr>
							<th style="width: 30px;" aria-label="No:">No</th>
							<th style="width: 80px;">申请单编号</th>
							<th style="width: 120px;">物料编号</th>
							<th>物料名称</th>
							<th style="width: 40px;">单位</th>
							<th style="width: 60px;">申请数量</th>
							<th style="width: 70px;">申请日期</th>
							<th style="width: 70px;">出库日期</th>
							<th style="width: 60px;">出库状态</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
