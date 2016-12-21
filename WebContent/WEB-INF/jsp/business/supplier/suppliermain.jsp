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

	function ajax() {
		var table = $('#TSupplier').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TSupplier').DataTable({
				"paging": false,
				"lengthMenu":[50,100,200],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : false,
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
							{"data": null, "defaultContent" : '',"className" : 'td-center'},
							{"data": "supplierID", "defaultContent" : ''},
							{"data": "shortName", "defaultContent" : ''},
							{"data": "supplierName", "defaultContent" : ''},
							{"data": "categoryId", "defaultContent" : ''},
							{"data": "categoryDes", "defaultContent" : ''},
							{"data": "paymentTerm", "defaultContent" : '',"className" : 'td-center'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
                    }},
		    		{"targets":7,"render":function(data, type, row){
		    			return "<a href=\"#\" onClick=\"doUpdate('" + row["recordId"] + "')\">查看</a>";
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
	}

	$(document).ready(function() {
		//ajax();
		initEvent();
		
	})	
	
	function doSearch() {
	
		ajax();
		//reload();
	}
	
	function doCreate() {
		
		var url = "${ctx}/business/supplier?methodtype=addinit";
		location.href = url;
		//openLayer(url, '', layerHeight, true);
	}
	
	function doUpdate(key) {
		var url = "${ctx}/business/supplier?methodtype=show&key=" + key;
		location.href = url;
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
						//alert(data.message);
						
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


	
</script>

</head>

<body>
<div id="container">
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
			<div id="DTTT_container" align="right" style="height:40px;">
				<a class="DTTT_button DTTT_button_text" onclick="doCreate();"><span>新建</span></a>
				<a class="DTTT_button DTTT_button_text" onclick="doDelete();"><span>删除</span></a>
			</div>
			<div id="clear"></div>
			<table style="width: 100%;" id="TSupplier" class="display dataTable" cellspacing="0">
				<thead>
				
					<tr class="selected">
						<th style="width:30px;" class="dt-middle">No</th>
						<th style="width:120px;" class="dt-middle">供应商编码</th>
						<th style="width:60px;"  class="dt-middle">简称</th>
						<th class="dt-middle">供应商名称</th>
						<th style="width:50px;" class="dt-middle">物料分类</th>
						<th style="width:150px;" class="dt-middle">分类解释</th>
						<th style="width:50px;" class="dt-middle">付款条件</th>
						<th style="width:50px;" class="dt-middle">操作</th>
					</tr>
				</thead>

			</table>
		</div>
	</div>
</div>
</body>
</html>
