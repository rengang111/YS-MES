<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>基础BOM一览</title>
<script type="text/javascript">

	function ajax(scrollHeight) {
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
			//"pagingType" : "full_numbers",
			//"scrollY":scrollHeight,
			"scrollCollapse":true,
			"retrieve" : true,
			"sAjaxSource" : "${ctx}/business/material?methodtype=searchProduct",
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
						{"data": "productModel"},
						{"data": "customerId"},
						{"data": null, "defaultContent" : '0',"className" : 'td-right'},
						{"data": "dicName","className" : 'td-center'},
						{"data": null,"className" : 'td-center'}
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                   }},
	    		{"targets":7,"render":function(data, type, row){
	    			var rtn = "";
	    			var space = '&nbsp;';
	    			rtn= "<a href=\"#\" onClick=\"doShow('" + row["materialId"] + "')\">查看</a>";
	    			rtn= rtn+space+"<a href=\"#\" onClick=\"doCreate('" + row["materialId"] + "')\">BOM</a>";		
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
		
	})	
	
	function doSearch() {	

		var scrollHeight = $(document).height() - 197; 
		ajax(scrollHeight);

	}
	
	function doCreate(materialId) {
		
		var url = '${ctx}/business/bom?methodtype=createBaseBom&materialId=' + materialId;
		location.href = url;
	}
	
	function doShow(materialId) {

		var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;

		location.href = url;
	}

	function doReview(YSId,bomId) {
		//var url = '${ctx}/business/bom?methodtype=copy&bomId=' + bomId+'&materialId='+materialId;
		var url = '${ctx}/business/orderreview?methodtype=create&YSId=' + YSId+'&bomId='+bomId;
		//var url = '${ctx}/business/orderreview?methodtype=detailView&bomId=' + bomId;

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
							style="width:50px" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
		<div align="right" style="height:40px">
			<a class="DTTT_button" onclick="doDelete();">删除</a>
		</div>

		<table id="TMaterial" class="display dataTable" >
			<thead>						
				<tr>
					<th style="width: 30px;"class="dt-middle ">No</th>
					<th style="width: 140px;" class="dt-middle ">成品编号</th>
					<th class="dt-middle">产品名称</th>
					<th style="width: 60px;" class="dt-middle">型号</th>
					<th style="width: 60px;" class="dt-middle">客户简称</th>
					<th style="width: 100px;" class="dt-middle">基础成本</th>
					<th style="width: 25px;" class="dt-middle ">单位</th>
					<th style="width: 50px;" class="dt-middle ">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
