<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>半成品一览</title>
<script type="text/javascript">

	function ajax(pageFlg) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnDestroy();
		}

		var t = $('#TMaterial').DataTable({
			"paging": true,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			 "iDisplayLength" : 50,
			"pagingType" : "full_numbers",
			"retrieve" : true,
			"sAjaxSource" : "${ctx}/business/material?methodtype=searchProductSemi&pageFlg="+pageFlg,
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
						{"data": "totalCost", "defaultContent" : '0',"className" : 'td-right'},
						{"data": "exchangePrice", "defaultContent" : '0',"className" : 'td-right'},
						{"data": "currency", "defaultContent" : '0',"className" : 'td-center'},
						{"data": null,"className" : 'td-right'},
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":1,"render":function(data, type, row){
	    			var material = row["materialId"];
	    			var rtn= "<a href=\"###\" onClick=\"doShow('" + row["materialId"] + "')\">"+material+"</a>";	
	    			return rtn;
	    		}},
	    		{"targets":2,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];				    			
	    			name = jQuery.fixedWidth(name,45);				    			
	    			return name;
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			var rate = row["exchangePrice"];
	    			var c = row["currency"];
	    			if(rate == null || rate == ""){
		    			return "0";
	    			}else{
	    				return floatToSymbol(rate,c);
		    			 //return row["profitRate"] + "%";	    				
	    			}	    				
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			var rate = row["profitRate"];
	    			if(rate == null || rate == ""){
		    			return "0";
	    			}else{
		    			return row["profitRate"] + "%";	    				
	    			}	    				
	    		}}
         	] 
		});
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
	
		
	function doShow(materialId) {

		var url = '${ctx}/business/material?methodtype=productSemiView&materialId=' + materialId;

		location.href = url;
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
		<table id="TMaterial" class="display dataTable" >
			<thead>						
				<tr>
					<th style="width: 10px;"class="dt-middle ">No</th>
					<th style="width: 180px;" class="dt-middle ">半成品编号</th>
					<th class="dt-middle">产品名称</th>
					<th style="width: 80px;" class="dt-middle">核算成本</th>
					<th style="width: 80px;" class="dt-middle">客户报价</th>
					<th style="width: 60px;" class="dt-middle">币种</th>
					<th style="width: 60px;" class="dt-middle ">利润率</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
