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

<title>自制品单价一览</title>
<script type="text/javascript">

	var layerHeight = '500';
	var layerWidth = '700';
	

	function ajax() {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : false,
				"serverSide" : false,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/zzmaterial?methodtype=search",
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
							{"data": "materialId", "defaultContent" : ''},
							{"data": "materialName", "defaultContent" : ''},
							{"data": "unit", "defaultContent" : '',"className" : 'td-center'},
							{"data": "cavitiesNumber", "defaultContent" : '',"className" : 'cash'},
							{"data": "time", "defaultContent" : '',"className" : 'cash'},
							{"data": "kilowatt", "defaultContent" : '',"className" : 'cash'},
							{"data": "totalPrice", "defaultContent" : '',"className" : 'cash'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
						],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"];
		                    }},
				    		{"targets":2,"render":function(data, type, row){
				    			
				    			var name = row["materialName"];
				    			name = jQuery.fixedWidth(name,30);
				    			
				    			return name;
				    		}},
				    		{"targets":8,"render":function(data, type, row){
				    			var rtn = "";
				    			var space = '&nbsp;';
				    			rtn= "<a href=\"#\" onClick=\"doShow('" + row["materialId"] + "')\">查看</a>";
				    			return rtn;
				    		}}
			         	] 
			}
		);

	}

	
	function initEvent(){

		doSearch();
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}

	$(document).ready(function() {
		
		initEvent();
		
		//重设iframe高度
		iFramNoSroll()
		
	})	
	
	function doSearch() {	

		ajax();

	}
	
	function doCreate() {
		
		var url = '${ctx}/business/zzmaterial?methodtype=create';
		location.href = url;
	}
	
	function doShow(materialId) {

		var url = '${ctx}/business/zzmaterial?methodtype=detailView&materialId=' + materialId;

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
					url : "${ctx}/business/matcategory?methodtype=delete",
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
	
	
</script>
</head>

<body class="easyui-layout">
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
							style="width:50px" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">

		<div id="TSupplier_wrapper" class="dataTables_wrapper">
			<div id="DTTT_container" align="right" style="margin-bottom: -10px;margin-right: 10px;eight:40px">
				<a aria-controls="TMaterial" tabindex="0" id="ToolTables_TSupplier_0" 
					class="DTTT_button DTTT_button_text" onclick="doCreate();"><span>新建</span></a>
			</div>
			<table aria-describedby="TSupplier_info" style="width: 100%;" id="TMaterial" class="display dataTable" cellspacing="0">
				<thead>						
					<tr class="selected">
						<th style="width: 10px;" aria-label="No:" class="dt-middle ">No</th>
						<th style="width: 100px;" aria-label="物料编号" class="dt-middle ">自制品编号</th>
						<th aria-label="物料编号" class="dt-middle ">产品名称</th>
						<th style="width: 40px;" aria-label="物料编号" class="dt-middle ">单位</th>
						<th style="width: 60px;" aria-label="物料编号" class="dt-middle ">出模数</th>
						<th style="width: 60px;" aria-label="物料编号" class="dt-middle ">出模时间</th>
						<th style="width: 60px;" aria-label="物料编号" class="dt-middle ">机器功率(KW)</th>
						<th style="width: 60px;" aria-label="物料编号" class="dt-middle ">成本</th>
						<th style="width: 30px;" aria-label="操作" class="dt-middle ">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>
