<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>进程控制检索</title>
<script type="text/javascript">

	var layerHeight = '600';

	$(document).ready(function() {
		initEvent();
		
	})	
	
	function ajax() {
		var table = $('#TProcessControl').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TProcessControl').DataTable({
				"paging": true,
				"lengthMenu":[5,10,15],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/processcontrol?methodtype=search",
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
							{"data": "projectId", "defaultContent" : '',"className" : 'td-center'},
							{"data": "projectName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "tempVersion", "defaultContent" : '',"className" : 'td-center'},
							{"data": "loginName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "beginTime", "defaultContent" : '',"className" : 'td-center'},
							{"data": "endTime", "defaultContent" : '',"className" : 'td-center'},
							{"data": "exceedTime", "defaultContent" : '',"className" : 'td-center'},							
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
					    		{"targets":0,"render":function(data, type, row){
									return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
			                    }},
					    		{"targets":8,"render":function(data, type, row){
					    			return "<a href=\"#\" onClick=\"doUpdate('" + row["id"] + "')\">查看</a>"
			                    }}
			           
			         ] 
			}
		);
	}

	
	function initEvent(){

		doSearch();

		$('#TProcessControl').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TProcessControl').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		
		/*
		$('#TProcessControl').DataTable().on('dblclick', 'tr', function() {

			var d = $('#TProcessControl').DataTable().row(this).data();

			location.href = '${pageContext.request.contextPath}/factory/show/' + d["factory_id"] + '.html';		
			
		});
		*/
	}
	
	function doSearch() {
		ajax();
		//reload();
	}
	
	function doCreate() {
		
		var url = "${ctx}/business/processcontrol?methodtype=addinit";
		openLayer(url, '', $(document).height(), false);
	}
	
	function doUpdate(key) {
		var str = '';
		var isFirstRow = true;
		var url = "${ctx}/business/processcontrol?methodtype=updateinit&key=" + key;

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
					url : "${ctx}/business/processcontrol?methodtype=delete",
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
		
		$('#TProcessControl').DataTable().ajax.reload(null,false);
		
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

				<div id="TProcessControl_wrapper" class="dataTables_wrapper">

					<div id="clear"></div>
					<table aria-describedby="TProcessControl_info" style="width: 100%;" id="TProcessControl" class="display dataTable" cellspacing="0">
						<thead>
						
							<tr class="selected">
								<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
								<th colspan="1" rowspan="1" style="width: 60px;" aria-label="项目编号:" class="dt-middle sorting_disabled">项目编号</th>
								<th colspan="1" rowspan="1" style="width: 82px;" aria-label="项目名称:" class="dt-middle sorting_disabled">项目名称</th>
								<th colspan="1" rowspan="1" style="width: 120px;" aria-label="暂定型号" class="dt-middle sorting_disabled">暂定型号</th>
								<th colspan="1" rowspan="1" style="width: 35px;" aria-label="项目经理" class="dt-middle sorting_disabled">项目经理</th>
								<th colspan="1" rowspan="1" style="width: 35px;" aria-label="开发起始时间" class="dt-middle sorting_disabled">开发起始时间</th>
								<th colspan="1" rowspan="1" style="width: 35px;" aria-label="预定完成时间" class="dt-middle sorting_disabled">预定完成时间</th>
								<th colspan="1" rowspan="1" style="width: 80px;" aria-label="超期" class="dt-middle sorting_disabled">超期</th>
								<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
							</tr>
						</thead>

					</table>
				</div>
			</div>
		</div>
	</div>
</html>
