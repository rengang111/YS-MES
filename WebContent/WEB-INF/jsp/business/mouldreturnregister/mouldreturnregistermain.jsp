<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>模具归还检索</title>
<script type="text/javascript">

	var layerHeight = '600';

	function ajax() {
		var table = $('#TMouldReturnRegisterList').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TMouldReturnRegisterList').DataTable({
				"paging": true,
				"lengthMenu":[5,10,15],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/mouldreturnregister?methodtype=search",
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
							{"data": "mouldLendNo", "defaultContent" : '',"className" : 'td-center'},
							{"data": "mouldReturnNo", "defaultContent" : '',"className" : 'td-center'},
							{"data": "productModelNo", "defaultContent" : '',"className" : 'td-center'},
							{"data": "productModelName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "factoryName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "lendTime", "defaultContent" : '',"className" : 'td-center'},
							{"data": "returnTime", "defaultContent" : '',"className" : 'td-center'},
							{"data": "factReturnTime", "defaultContent" : '',"className" : 'td-center'},
							{"data": "confirm", "defaultContent" : '',"className" : 'td-center'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
					    		{"targets":0,"render":function(data, type, row){
					    			if (row["confirm"] == '1') {
					    				return row["rownum"]
					    			} else {
										return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
					    			}
			                    }},
					    		{"targets":9,"render":function(data, type, row){
					    			if (row["confirm"] == '0') {
					    				return "未确认"
					    			}
					    			if (row["confirm"] == '1') {
					    				return "已确认"
					    			}
			                    }},
					    		{"targets":10,"render":function(data, type, row){
					    			return "<a href=\"#\" onClick=\"doUpdate('" + row["id"] + "', '" + row["lendId"] + "')\">编辑</a>"
			                    }}
			           
			         ] 
			}
		);
	}

	
	function initEvent(){

		doSearch();
	
		$('#TMouldReturnRegisterList').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMouldReturnRegisterList').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		
		/*
		$('#TMouldReturnRegisterList').DataTable().on('dblclick', 'tr', function() {

			var d = $('#TMouldReturnRegisterList').DataTable().row(this).data();

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
		
	function doUpdate(key, lendId) {
		
		var url = "${ctx}/business/mouldreturnregister?methodtype=updateinit&key=" + key + "&lendId=" + lendId;

		openLayer(url, '', $(document).height(), false);
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
					url : "${ctx}/business/mouldreturnregister?methodtype=delete",
					success : function(data) {
						if (data.rtnCd != '000') {
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
		
		$('#TMouldReturnRegisterList').DataTable().ajax.reload(null,false);
		
		return true;
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

				<div id="TMouldReturnRegisterList_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container" align="right" style="height:40px">
						<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="doDelete();"><span>删除</span></a>
					</div>

					<div id="clear"></div>
					<table aria-describedby="TMouldReturnRegisterList_info" style="width: 100%;" id="TMouldReturnRegisterList" class="display dataTable" cellspacing="0">
						<thead>
							<tr class="selected">
								<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
								<th colspan="1" rowspan="1" style="width: 60px;" aria-label="出借编号" class="dt-middle sorting_disabled">出借编号</th>
								<th colspan="1" rowspan="1" style="width: 60px;" aria-label="归还编号" class="dt-middle sorting_disabled">归还编号</th>
								<th colspan="1" rowspan="1" style="width: 80px;" aria-label="产品型号" class="dt-middle sorting_disabled">产品型号</th>
								<th colspan="1" rowspan="1" style="width: 120px;" aria-label="产品名称" class="dt-middle sorting_disabled">产品名称</th>
								<th colspan="1" rowspan="1" style="width: 60px;" aria-label="归还工厂" class="dt-middle sorting_disabled">出借工厂</th>
								<th colspan="1" rowspan="1" style="width: 60px;" aria-label="归还时间" class="dt-middle sorting_disabled">出借时间</th>
								<th colspan="1" rowspan="1" style="width: 60px;" aria-label="预期归还" class="dt-middle sorting_disabled">预期归还</th>
								<th colspan="1" rowspan="1" style="width: 60px;" aria-label="实际归还" class="dt-middle sorting_disabled">实际归还</th>
								<th colspan="1" rowspan="1" style="width: 35px;" aria-label="确认状态" class="dt-middle sorting_disabled">确认<br>状态</th>
								<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</html>
