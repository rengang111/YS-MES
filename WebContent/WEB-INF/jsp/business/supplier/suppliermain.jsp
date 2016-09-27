<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>供应商基本数据检索</title>
<script type="text/javascript">

	var layerHeight = '600';

	function doSearch() {
	
		ajax();
		//reload();
	}
	
	function doCreate() {
		var url = "${ctx}/business/supplier?methodtype=addinit";
		openLayer(url, '', layerHeight, true);
	}
	
	function doUpdate(key) {
		var str = '';
		var isFirstRow = true;
		var url = "${ctx}/business/supplier?methodtype=updateinit&key=" + key;

		openLayer(url, '', layerHeight, true);
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
					url : "${ctx}/business/supplier?methodtype=delete",
					success : function(data) {
						reload();
						alert(data.message);
						
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
		                // alert(XMLHttpRequest.status);
		                //alert(XMLHttpRequest.readyState);
		                //alert(textStatus);
		             }
				});
			}
		} else {
			alert("请至少选择一条数据");
		}
		
	}

	function reload() {
		$('#TSupplier').DataTable().ajax.reload(null,false);
		
		return true;
	}

	function ajax() {
		var table = $('#TSupplier').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TSupplier')
		.DataTable({
				"paging": true,
				"lengthMenu":[25,50,-1],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/supplier?methodtype=search",
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
							{"data": null, "defaultContent" : ''},
							{"data": "supplierID", "defaultContent" : ''},
							{"data": "supplierSimpleDes", "defaultContent" : ''},
							{"data": "supplierDes", "defaultContent" : ''},
							{"data": "twoLevelID", "defaultContent" : ''},
							{"data": "twoLevelIDDes", "defaultContent" : ''},
							{"data": "paymentTerm", "defaultContent" : ''},
							{"data": "fullAddress", "defaultContent" : ''},
							{"data": "userName", "defaultContent" : ''},
							{"data": null, "defaultContent" : ''}
				        ],
				"columnDefs":[
					    		{"targets":0,"render":function(data, type, row){
									return "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
			                    }},
					    		{"targets":9,"render":function(data, type, row){
					    			return "<a href=\"#\" onClick=\"doUpdate('" + row["id"] + "')\">编辑</a>"
			                    }}
			           
			         ] 
			}
		);
	}

	
	function initEvent(){

		doSearch();
	
		$('#TSupplier').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TSupplier').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		
		/*
		$('#TSupplier').DataTable().on('dblclick', 'tr', function() {

			var d = $('#TSupplier').DataTable().row(this).data();

			location.href = '${pageContext.request.contextPath}/factory/show/' + d["factory_id"] + '.html';		
			
		});
		*/
	}

	$(document).ready(function() {
		//ajax();
		initEvent();
		
	})
	
</script>

</head>

<body class="easyui-layout">
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition" 
					style='padding: 0px; margin: 10px;' >

					<table>
						<tr>
							<td width="10%"></td> 
							<td class="label">keyword1：</td>
							<td class="condition">
								<input type="text" id="keyword1" name="keyword1" class="middle"/>
							</td>
							<td class="label">keyword2：</td> 
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

				<div id="TSupplier_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container" align="right" style="height:40px">
						<a aria-controls="TSupplier" tabindex="0" id="ToolTables_TSupplier_0" class="DTTT_button DTTT_button_text" onClick="doCreate();"><span>新建</span></a>
						<a aria-controls="TSupplier" tabindex="0" id="ToolTables_TSupplier_1" class="DTTT_button DTTT_button_text" onClick="doDelete();"><span>删除</span></a>
					</div>
					<div id="clear"></div>
					<table aria-describedby="TSupplier_info" style="width: 100%;" id="TSupplier" class="display dataTable" cellspacing="0">
						<thead>
						
							<tr class="selected">
								<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
								<th colspan="1" rowspan="1" style="width: 60px;" aria-label="编码:" class="dt-middle sorting_disabled">编码</th>
								<th colspan="1" rowspan="1" style="width: 82px;" aria-label="简称:" class="dt-middle sorting_disabled">简称</th>
								<th colspan="1" rowspan="1" style="width: 120px;" aria-label="名称" class="dt-middle sorting_disabled">名称</th>
								<th colspan="1" rowspan="1" style="width: 60px;" aria-label="二级编码" class="dt-middle sorting_disabled">二级编码</th>
								<th colspan="1" rowspan="1" style="width: 120px;" aria-label="编码解释" class="dt-middle sorting_disabled">编码解释</th>
								<th colspan="1" rowspan="1" style="width: 35px;" aria-label="付款条件" class="dt-middle sorting_disabled">付款条件</th>
								<th colspan="1" rowspan="1" style="width: 150px;" aria-label="地址" class="dt-middle sorting_disabled">地址</th>
								<th colspan="1" rowspan="1" style="width: 150px;" aria-label="联系人" class="dt-middle sorting_disabled">联系人</th>
								<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
							</tr>
						</thead>

					</table>
				</div>
			</div>
		</div>
	</div>
</html>
