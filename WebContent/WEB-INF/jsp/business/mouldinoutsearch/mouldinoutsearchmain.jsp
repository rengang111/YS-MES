<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>模具出入检索</title>
<script type="text/javascript">

	var layerHeight = '600';

	function ajax() {
		var table = $('#TMouldInoutList').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TMouldInoutList').DataTable({
				"paging": true,
				"lengthMenu":[5,10,15],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/mouldinoutsearch?methodtype=search",
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
				"columns": [
							{"data": null, "defaultContent" : '',"className" : 'td-center'},
							{"data": "productModelNo", "defaultContent" : '',"className" : 'td-center'},
							{"data": "productModelName", "defaultContent" : '',"className" : 'td-center'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
					    		{"targets":0,"render":function(data, type, row){
				    				return row["rownum"]
			                    }},
					    		{"targets":3,"render":function(data, type, row){
					    			return "<a href=\"#\" onClick=\"doView('" + row["productModel"] + "')\">详细信息</a>"
			                    }}
			           
			         ] 
			}
		);
	}

	
	function initEvent(){

		doSearch();
	
		$('#TMouldInoutList').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMouldInoutList').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		
		/*
		$('#TMouldInoutList').DataTable().on('dblclick', 'tr', function() {

			var d = $('#TMouldInoutList').DataTable().row(this).data();

			location.href = '${pageContext.request.contextPath}/factory/show/' + d["factory_id"] + '.html';		
			
		});
		*/
	}

	$(document).ready(function() {
		//ajax();
		initEvent();
		
	})	
	
	function doSearch() {
	
		ajax();
		//reload();
	}
	
	function doView(key) {
		var url = "${ctx}/business/mouldinoutsearch?methodtype=doView&key=" + key;

		openLayer(url, '', layerHeight, false);
	}
</script>

</head>

<body class="easyui-layout">
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition" style='padding: 0px; margin: 10px;' >

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
								<button type="button" id="retrieve" class="DTTT_button" style="width:50px" value="查询" onClick="doSearch();"/>查询
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">

				<div id="TMouldInoutList_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container" align="right" style="height:40px">
						<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="doDelete();"><span>删除</span></a>
					</div>

					<div id="clear"></div>
					<table aria-describedby="TMouldInoutList_info" style="width: 100%;" id="TMouldInoutList" class="display dataTable" cellspacing="0">
						<thead>
							<tr class="selected">
								<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
								<th colspan="1" rowspan="1" style="width: 80px;" aria-label="产品型号" class="dt-middle sorting_disabled">产品型号</th>
								<th colspan="1" rowspan="1" style="width: 120px;" aria-label="产品名称" class="dt-middle sorting_disabled">产品名称</th>
								<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</html>
