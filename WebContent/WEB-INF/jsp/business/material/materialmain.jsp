<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />


<%@ include file="../../common/common.jsp"%>

<title>物料(成品)管理一览页面</title>
<script type="text/javascript">

	function ajax(pageFlg) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var url = "${ctx}/business/material?methodtype=search&pageFlg="+pageFlg;

		var t = $('#TMaterial').DataTable({
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
							{"data": "shareModel"},
							{"data": "price","className" : 'td-right'},
							{"data": "supplierId"},
							{"data": "dicName","className" : 'td-center'}
						],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
		                    }},
				    		{"targets":1,"render":function(data, type, row){
				    			var rtn = "";
				    			rtn= "<a href=\"###\" onClick=\"doShow('" + row["recordId"] +"','"+ row["parentId"] + "')\">" + row["materialId"] + "</a>";
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
				    		{"targets":4,"render":function(data, type, row){
				    			
				    			var name = row["shareModel"];				    			
				    			name = jQuery.fixedWidth(name,20);				    			
				    			return name;
				    		}}
			           
			         ] 
			}
		);

	}

	


	$(document).ready(function() {
		
		ajax("");
	
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
		ajax("S");

	}
	
	function doCreate() {
		
		var url = '${ctx}/business/material?methodtype=create';
		location.href = url;
	}
	
	function doShow(recordId,parentId) {

		var url = '${ctx}/business/material?methodtype=detailView&parentId=' + parentId+'&recordId='+recordId;

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

				<div id="TSupplier_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container" align="right" style="height:40px">
						<a aria-controls="TMaterial" tabindex="0" id="ToolTables_TSupplier_0" 
							class="DTTT_button DTTT_button_text" onclick="doCreate();"><span>新建</span></a>
						<a aria-controls="TMaterial" tabindex="0" id="ToolTables_TSupplier_1" 
							class="DTTT_button DTTT_button_text" onclick="doDelete();"><span>删除</span></a>
					</div>
					<div id="clear"></div>
					<table aria-describedby="TSupplier_info" style="width: 100%;" id="TMaterial" class="display dataTable">
						<thead>						
							<tr class="selected">
								<th style="width: 30px;" aria-label="No:" class="dt-middle ">No</th>
								<th style="width: 140px;" class="dt-middle ">物料编号</th>
								<th class="dt-middle">物料名称</th>
								<th style="width: 140px;" class="dt-middle">物料分类</th>
								<th style="width: 100px;" class="dt-middle">通用型号</th>
								<th style="width: 50px;" class="dt-middle">价格</th>
								<th style="width: 70px;" class="dt-middle">供应商</th>
								<th style="width: 25px;" class="dt-middle ">单位</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
