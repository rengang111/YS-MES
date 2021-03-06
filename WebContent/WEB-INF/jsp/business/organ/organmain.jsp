<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>机构管理一览页面</title>
<script type="text/javascript">

	var layerHeight = '600';

	function ajax() {
		var table = $('#TOgran').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TOgran').DataTable({
				"paging": true,
				"lengthMenu":[20,50,100],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/organ?methodtype=search",
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
							{"data": "DicName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "no", "defaultContent" : '',"className" : 'td-center'},
							{"data": "shortName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "fullName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "address", "defaultContent" : '',"className" : 'td-center'}
							,{"data": null, "defaultContent" : '',"className" : 'td-center'}
						],
				"columnDefs":[
					    		{"targets":0,"render":function(data, type, row){
									return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
			                    }},
					    		{"targets":6,"render":function(data, type, row){
					    			return "<a href=\"#\" onClick=\"doEdit('" + row["recordId"] + "')\">编辑</a>"
			                    }}
			           
			         ] 
			}
		);
	}

	
	function initEvent(){

		doSearch();
	
		$('#TOgran').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TOgran').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}

	$(document).ready(function() {
		initEvent();		
	})	
	
	function doSearch() {	
		ajax();
	}
	
	function doCreate() {
		
		var url = "${ctx}/business/organ?methodtype=create";
		location.href = url;
		//openLayer(url, '', layerHeight, true);
	}
	
	function doEdit(key) {
		var str = '';
		var isFirstRow = true;
		
		var url = "${ctx}/business/organ?methodtype=edit&key=" + key;
		location.href = url;
		//openLayer(url, '', layerHeight, true);
	}
	
	
	function doDelete() {

		var str = '';
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				str += $(this).val() + ",";
			}
		});

		if (str != '') {
			if(confirm("确定要删除数据吗？")) {
				jQuery.ajax({
					type : 'POST',
					async: false,
					contentType : 'application/json',
					dataType : 'json',
					data : str,
					url : "${ctx}/business/organ?methodtype=delete",
					success : function(data) {
						reload();						
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				});
			}
		} else {
			alert("请至少选择一条数据");
		}
		
	}

	function reload() {
		
		$('#TOgran').DataTable().ajax.reload(null,false);
		
		return true;
	}
</script>
</head>

<body class="panel-body">
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition" 
					style='padding: 0px; margin: 10px;' >

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
								<button type="button" id="retrieve" class="DTTT_button" style="width:50px" value="查询" onclick="doSearch();"/>查询
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">

				<div id="TSupplier_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container" align="right" style="height:40px">
						<a aria-controls="TOgran" tabindex="0" id="ToolTables_TSupplier_0" class="DTTT_button DTTT_button_text" onClick="doCreate();"><span>新建</span></a>
						<a aria-controls="TOgran" tabindex="0" id="ToolTables_TSupplier_1" class="DTTT_button DTTT_button_text" onClick="doDelete();"><span>删除</span></a>
					</div>
					<div id="clear"></div>
					<table aria-describedby="TSupplier_info" style="width: 100%;" id="TOgran" class="display dataTable" cellspacing="0">
						<thead>
						
							<tr class="selected">
								<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
								<th colspan="1" rowspan="1" style="width: 80px;" aria-label="机构类型" class="dt-middle sorting_disabled">机构类型</th>
								<th colspan="1" rowspan="1" style="width: 80px;" aria-label="机构编码" class="dt-middle sorting_disabled">机构编码</th>
								<th colspan="1" rowspan="1" style="width: 80px;" aria-label="机构简称" class="dt-middle sorting_disabled">机构简称</th>
								<th colspan="1" rowspan="1" style="width: 200px;" aria-label="机构全称" class="dt-middle sorting_disabled">机构全称</th>
								<th colspan="1" rowspan="1" style="width: 350px;" aria-label="详细地址" class="dt-middle sorting_disabled">详细地址</th>
								<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
