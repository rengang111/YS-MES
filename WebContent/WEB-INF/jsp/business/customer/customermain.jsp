<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>客户基本数据检索</title>
<script type="text/javascript">

	var layerHeight = '600';

	function ajax() {
		var table = $('#TCustomer').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TCustomer').DataTable({
				"paging": false,
				"lengthMenu":[50,100,150],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : false,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/customer?methodtype=search",
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
							{"data": null,"className" : 'td-center'},
							{"data": "customerId"},
							{"data": "shortName"},
							{"data": "customerName"},
							{"data": "country","className" : 'td-center'},
							{"data": "paymentTerm","className" : 'td-right'},
							{"data": "currency","className" : 'td-center'},
							{"data": "shippingCondition","className" : 'td-center'},
							{"data": "shippiingPort","className" : 'td-center'},							
							{"data": "destinationPort","className" : 'td-center'},
							{"data": null,"className" : 'td-center'}
				        ],
				"columnDefs":[
					    		{"targets":0,"render":function(data, type, row){
									return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
			                    }},
					    		{"targets":10,"render":function(data, type, row){
					    			return "<a href=\"#\" onClick=\"doUpdate('" + row["recordId"] + "')\">查看</a>"
			                    }}
			           
			         ] 
			}
		);
	}

	
	function initEvent(){

		doSearch();
	
		$('#TCustomer').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TCustomer').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		
		/*
		$('#TCustomer').DataTable().on('dblclick', 'tr', function() {

			var d = $('#TCustomer').DataTable().row(this).data();

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
	
	function doCreate() {
		var url = "${ctx}/business/customer?methodtype=addinit";
		location.href = url
	}
	
	function doUpdate(key) {
		var url = "${ctx}/business/customer?methodtype=showDetail&key=" + key;
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
					url : "${ctx}/business/customer?methodtype=delete",
					success : function(d) {
						if (d.rtnCd != "000") {
							alert(data.message);
						} else {
							reload();
						}
						
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
		$('#TCustomer').DataTable().ajax.reload(null,false);
		
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
								<button type="button" id="retrieve" class="DTTT_button" style="width:50px" value="查询" onClick="doSearch();"/>查询
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">

				<div id="TCustomer_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container" align="right" style="height:40px">
						<a aria-controls="TCustomer" tabindex="0" id="ToolTables_TCustomer_0" class="DTTT_button DTTT_button_text" onClick="doCreate();"><span>新建</span></a>
						<a aria-controls="TCustomer" tabindex="0" id="ToolTables_TCustomer_1" class="DTTT_button DTTT_button_text" onClick="doDelete();"><span>删除</span></a>
					</div>
					<div id="clear"></div>
					<table aria-describedby="TCustomer_info" style="width: 100%;" id="TCustomer" class="display dataTable" cellspacing="0">
						<thead>
						
							<tr class="selected">
								<th style="width: 10px;" aria-label="No:" class="dt-middle ">No</th>
								<th style="width: 80px;" aria-label="编号:" class="dt-middle ">编号</th>
								<th style="width: 50px;" aria-label="简称:" class="dt-middle ">简称</th>
								<th class="dt-middle ">名称</th>
								<th style="width: 60px;" aria-label="所在国家" class="dt-middle ">国家</th>
								<th style="width: 30px;" aria-label="付款条件" class="dt-middle ">付款条件</th>
								<th style="width: 60px;" aria-label="计价货币" class="dt-middle ">计价货币</th>
								<th style="width: 60px;" aria-label="出运条件" class="dt-middle ">出运条件</th>
								<th style="width: 60px;" aria-label="出运港" class="dt-middle ">出运港</th>
								<th style="width: 60px;" aria-label="目的港" class="dt-middle ">目的港</th>
								<th style="width: 30px;" aria-label="操作" class="dt-middle ">操作</th>
							</tr>
						</thead>

					</table>
				</div>
			</div>
		</div>
	</div>
</html>
