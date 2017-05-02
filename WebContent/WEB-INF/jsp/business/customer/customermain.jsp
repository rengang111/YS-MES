<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>

<title>客户基本数据检索</title>
<script type="text/javascript">

	var layerHeight = '600';

	function ajax() {
		var table = $('#TCustomer').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TCustomer').DataTable({
				"paging": true,
				 "iDisplayLength" : 100,
				"lengthChange":false,
				//"lengthMenu":[10,150,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
				"ordering "	:true,
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
							{"data": null},
							{"data": null,"className" : 'td-center'}
				        ],
				"columnDefs":[
					    		{"targets":0,"render":function(data, type, row){
									return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
			                    }},
					    		{"targets":3,"render":function(data, type, row){
					    			
					    			var name = row["customerName"];				    			
					    			name = jQuery.fixedWidth(name,30);				    			
					    			return name;
					    		}},
					    		{"targets":8,"render":function(data, type, row){
					    			return row["shippiingPort"] + "/"+row["destinationPort"] ;
			                    }},
					    		{"targets":9,"render":function(data, type, row){
					    			return "<a href=\"###\" onClick=\"doUpdate('" + row["recordId"] + "')\">查看</a>"
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
						reload();						
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

				<div id="DTTT_container" align="right" style="height:40px">
					<a class="DTTT_button" onclick="doCreate();"><span>新建</span></a>
					<a class="DTTT_button" onclick="doDelete();"><span>删除</span></a>
				</div>
				<div id="clear"></div>
				<table id="TCustomer" class="display dataTable" cellspacing="0">
					<thead>
					
						<tr class="selected">
							<th style="width: 10px;" class="dt-middle ">No</th>
							<th style="width: 80px;" class="dt-middle ">客户编号</th>
							<th style="width: 50px;" class="dt-middle ">简称</th>
							<th class="dt-middle ">客户名称</th>
							<th style="width: 80px;" class="dt-middle ">所在国家</th>
							<th style="width: 30px;" class="dt-middle ">付款条件</th>
							<th style="width: 30px;" class="dt-middle ">计价货币</th>
							<th style="width: 30px;" class="dt-middle ">出运条件</th>
							<th style="width: 140px;" class="dt-middle ">出运港/目的港</th>
							<th style="width: 30px;"  class="dt-middle ">操作</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
	</div>
	</body>
</html>
