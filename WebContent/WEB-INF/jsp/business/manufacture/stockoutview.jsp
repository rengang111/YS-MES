<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>料件出库-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function historyAjax() {
		var YSId = '${order.YSId }';
		var t = $('#example2').DataTable({
			
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/stockout?methodtype=getStockoutHistory&YSId="+YSId,
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
			
			"columns" : [
			        	{"data": null,"className":"dt-body-center"
					}, {"data": "stockOutId","className":"dt-body-center"
					}, {"data": "checkOutDate","className":"dt-body-center"
					}, {"data": "keepUser","className":"dt-body-center"
					}, {"data": null,"className":"dt-body-center"
					}, {"data": null,"className":"td-center","defaultContent" : ''
					}
				] ,
				"columnDefs":[
		    		{"targets":4,"render":function(data, type, row){
		    			var contractId = row["contractId"];		    			
		    			var rtn= "<a href=\"###\" onClick=\"doEdit('" + row["YSId"] + "','" + row["stockOutId"] + "')\">编辑</a>";
		    			return rtn;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			var contractId = row["contractId"];		    			
		    			var rtn= "<a href=\"###\" onClick=\"doPrint('" + row["stockOutId"] + "')\">打印出库单</a>";
		    			return rtn;
		    		}},
		    	]        
			
		}).draw();
						
		
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	};
	

	function detailAjax(stockOutId) {
	
		var table = $('#example').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var t = $('#example').DataTable({
			
			"paging": false,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/stockout?methodtype=getStockoutDetail&stockOutId="+stockOutId,
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
			
			"columns" : [
		        	{"data": null,"className":"dt-body-center"
				}, {"data": "materialId","className":"td-left"
				}, {"data": "materialName"
				}, {"data": "quantity","className":"td-center"
				}
			] 
			
			
		}).draw();
						
		t.on('click', 'tr', function() {
			
			var rowIndex = $(this).context._DT_RowIndex; //行号			
			//alert(rowIndex);

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
			
		});
		
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	};
	$(document).ready(function() {
				
		historyAjax();//出库记录
				
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/stockout";
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					var YSId='${order.YSId }';
					var url =  "${ctx}/business/stockout?methodtype=addinit&YSId="+YSId;
					location.href = url;
		});
		
		$('#example2').DataTable().on('click', 'tr', function() {
			
			//$(this).toggleClass('selected');
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	
	        	$('#example2').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            
	            var d = $('#example2').DataTable().row(this).data();				
				$('#example').DataTable().destroy();
				detailAjax(d["stockOutId"]);
					            
	        }
			
		});
	});
	
	function doEdit(YSId,requisitionId) {
		
		var url = '${ctx}/business/requisition?methodtype=updateInit&YSId='
				+YSId+'&requisitionId='+requisitionId;
		//alert("requisitionId"+requisitionId)
		//callProductDesignView("requisition",url)
		location.href = url;
	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="goBackFlag" />
	<form:hidden path="stockout.ysid"  />
	<fieldset>
		<legend> 出库单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="150px">${order.YSId }</td>
									
				<td class="label" width="100px">生产数量：</td>					
				<td colspan="3">${order.manufactureQuantity }&nbsp;(订单数量+额外采购)</td>
			</tr>
			<tr>							
				<td class="label">产品编号：</td>					
				<td>${order.materialId }</td>
							
				<td class="label">产品名称：</td>					
				<td colspan="3">${order.materialName }</td>
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	<div id="DTTT_container" align="right" style="margin-right: 30px;">
	<!-- 	<a class="DTTT_button DTTT_button_text" id="insert" >继续出库</a> -->
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" > 返回 </a>
	</div>
	<fieldset>
		<legend> 出库记录</legend>
		<div class="list">
			<table id="example2" class="display" style="width:100%">
				<thead>				
					<tr>
						<th width="30px">No</th>
						<th class="dt-center" style="width:150px">出库单编号</th>
						<th class="dt-center" width="150px">出库日期</th>
						<th class="dt-center" width="150px">仓管员</th>
						<th class="dt-center" width="150px">操作</th>
						<th class="dt-center" ></th>
					</tr>
				</thead>
			</table>
			</div>
	</fieldset>
	
	<fieldset>
		<legend> 物料详情</legend>
		<div class="list">		
			<table id="example" class="display" style="width:100%">
				<thead>				
					<tr>
						<th style="width:30px">No</th>
						<th class="dt-center" width="200px">物料编号</th>
						<th class="dt-center" >物料名称</th>	
						<th class="dt-center" width="150px">当前出库数量</th>
					</tr>
				</thead>
			</table>
		</div>
	</fieldset>
		
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function doPrint(stockOutId) {
	var YSId = '${order.YSId }';
	var url = '${ctx}/business/stockout?methodtype=print';
	url = url +'&YSId='+YSId;
	url = url +'&stockOutId='+stockOutId;
		
	callProductDesignView("print",url);

};

</script>

</html>
