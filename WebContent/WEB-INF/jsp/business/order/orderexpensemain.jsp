<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />


<%@ include file="../../common/common.jsp"%>

<title>订单基本数据一览页面(订单过程)</title>
<script type="text/javascript">

	function ajax(scrollHeight) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var t = $('#TMaterial').DataTable({
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//每页显示条数设置
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			//"bSort":true,
			// "bFilter": false, //列筛序功能
			"ordering"	:true,
			"searching" : false,
			// "Info": true,//页脚信息
			// "bPaginate": true, //翻页功能
			"pagingType" : "full_numbers",
				"sAjaxSource" : "${ctx}/business/order?methodtype=orderExpenseSearch",
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
					{"data": "YSId", "defaultContent" : ''},
					{"data": "materialId", "defaultContent" : ''},
					{"data": "materialName", "defaultContent" : ''},
					{"data": "quantity", "className" : 'td-right'},
					{"data": "cost", "defaultContent" : '',"className" : 'td-right'},
					{"data": "status", "defaultContent" : '',"className" : 'td-center'}
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"];			    			 
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"#\" onClick=\"doShow('" + row["YSId"] +"','"+ row["materialId"] + "')\">"+ row["YSId"] +"</a>";
		    			return rtn;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			name = jQuery.fixedWidth(name,40);
		    			return name;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			return floatToCurrency(data);
		    		}}				           
	         	] 
			}
		);

	}
	
	function YSKcheck(v,id){
		var zzFlag = "";
		if(id != ''){
			zzFlag = id.substr(2,3);
		}
		if(zzFlag == 'YSK') v = 0;//库存订单不显示明细内容
		return v;
		
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
	
	function doShow(YSId,materialId) {

		var url = '${ctx}/business/bom?methodtype=orderexpenseview&YSId=' + YSId+'&materialId='+materialId;

		location.href = url;
	}


	function doCreate() {

		var url = '${ctx}/business/order?methodtype=orderExpenseYsid';

		location.href = url;
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
									style="width:50px" value="查询" onclick="doSearch();"/>查询
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">

				<div id="TSupplier_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container2" style="height:40px;float: right">
						<a  class="DTTT_button box" onclick="doCreate();" ><span>订单过程录入</span></a>
					</div>
					<table id="TMaterial" class="display dataTable" cellspacing="0">
						<thead>						
							<tr>
								<th style="width: 10px;" class="dt-middle ">No</th>
								<th style="width: 100px;" class="dt-middle ">耀升编号</th>
								<th style="width: 200px;" class="dt-middle ">产品编号</th>
								<th class="dt-middle ">产品名称</th>
								<th style="width: 100px;" class="dt-middle ">数量</th>
								<th style="width: 100px;" class="dt-middle ">增减费用</th>
								<th style="width: 80px;" class="dt-middle ">订单状态</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
