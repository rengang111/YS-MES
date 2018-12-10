<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>包装物料一览页面</title>
<script type="text/javascript">

	function ajax(sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/material?methodtype=materialPackSearch&sessionFlag="+sessionFlag;

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
							{"data": "materialId"},
							{"data": "materialName"},
							{"data": "categoryName"},
							{"data": "peopleNumber","className" : 'td-right', "defaultContent" : '0'},
							{"data": "hourYield","className" : 'td-right', "defaultContent" : '0'},
							{"data": "hourprice","className" : 'td-right', "defaultContent" : '0'},
							{"data": "laborPrice","className" : 'td-right', "defaultContent" : '0'},
						],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								//return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
								return row["rownum"];
		                    }},
				    		{"targets":1,"render":function(data, type, row){
				    			var rtn = "";
				    			rtn= "<a href=\"###\" onClick=\"doShow('" + row["recordId"] +"','" + row["parentId"] +"','"+ row["materialId"] + "')\">" + row["materialId"] + "</a>";
				    			return rtn;
				    		}},
				    		{"targets":2,"render":function(data, type, row){
				    			
				    			var name = row["materialName"];				    			
				    			name = jQuery.fixedWidth(name,40);				    			
				    			return name;
				    		}},
				    		{"targets":3,"render":function(data, type, row){
				    			
				    			var name = row["categoryName"];				    			
				    			name = jQuery.fixedWidth(name,20);				    			
				    			return name;
				    		}},
				    		{"targets":6,"render":function(data, type, row){
				    			
				    			//作业人数*每小时工价/每小时产量
				    			var fpeople = currencyToFloat(row["peopleNumber"]);
				    			var vhyield = currencyToFloat(row["hourYield"]);
				    			var fhprice = 11;
				    			var labor = 0;
				    			if(vhyield <= 0)
				    				labor = 0;
				    			else
				    				labor = fpeople * fhprice / vhyield;
				    						    			
				    			return floatToCurrency(row["workerPrice"]);
				    		}}
			           
			         ] 
			}
		);

	}

	


	$(document).ready(function() {
		
		ajax("true");
	
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
		ajax("false");

	}
	
	function doCreate() {
		var keyBackup = "pack";
		var url = '${ctx}/business/material?methodtype=create'+"&keyBackup="+keyBackup;
		location.href = url;
	}
	
	function doShow(recordId,parentId,materialId) {

		var url = '${ctx}/business/material?methodtype=detailViewPack&parentId='
				+ parentId+'&recordId='+recordId+'&materialId='+materialId;

		location.href = url;
	}

	function doEdit(recordId,parentId) {
		var str = '';
		var isFirstRow = true;
		var url = '${ctx}/business/material?methodtype=edit&parentId=' + parentId+'&recordId='+recordId;

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
					url : "${ctx}/business/material?methodtype=delete",
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
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		
		return true;
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
							<td width="50px"></td> 
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

				<div id="TSupplier_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container" align="right" style="height:40px">
						<a class="DTTT_button " onclick="doCreate();"><span>新建工价编号</span></a>
						<!-- a 	class="DTTT_button" onclick="doDelete();"><span>删除</span></a-->
					</div>
					<div id="clear"></div>
					<table aria-describedby="TSupplier_info" style="width: 100%;" id="TMaterial" class="display dataTable">
						<thead>						
							<tr class="selected">
								<th style="width: 30px;">No</th>
								<th style="width: 150px;" class="dt-middle ">物料编号</th>
								<th class="dt-middle">物料名称</th>
								<th style="width: 140px;" class="dt-middle">物料分类</th>
								<th style="width: 70px;" class="dt-middle">作业人数</th>
								<th style="width: 70px;" class="dt-middle">每小时产量</th>
								<th style="width: 70px;" class="dt-middle">工人工价</th>
								<th style="width: 70px;" class="dt-middle">核算工价</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
