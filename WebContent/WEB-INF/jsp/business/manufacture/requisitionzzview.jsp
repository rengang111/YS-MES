<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>自制品领料申请-领料单查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function historyAjax() {
		
		var taskId = '${formModel.task.taskid }';
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
			"sAjaxSource" : "${ctx}/business/requisitionzz?methodtype=getRequisitionHistory&taskId="+taskId,
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
					}, {"data": "requisitionId","className":"dt-body-center"
					}, {"data": "requisitionDate","className":"dt-body-center"
					}, {"data": "collectYsid","className":""
					}, {"data": "requisitionUser","className":"dt-body-center"
					}, {"data": "storeUser","className":"dt-body-center"
					}, {"data": null,"className":"td-center","defaultContent" : ''
					}
				] ,
				"columnDefs":[
		    		{"targets":6,"render":function(data, type, row){
		    			var contractId = row["contractId"];		    			
		    			var rtn= "<a href=\"###\" onClick=\"doEdit('" + row["YSId"] + "','" + row["requisitionId"] + "')\">编辑</a>";
		    			rtn = rtn + "&nbsp;&nbsp;";
		    			rtn = rtn +  "<a href=\"###\" onClick=\"doPrint('" + row["requisitionId"] + "')\">打印领料单</a>";
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
	

	function detailAjax(requisitionId) {
	
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
			"sAjaxSource" : "${ctx}/business/requisitionzz?methodtype=getRequisitionDetail&requisitionId="+requisitionId,
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
				}, {"data": "purchaseType","className":"td-center"
				}, {"data": "supplierId","className":"td-left"
				}, {"data": "contractId","className":"td-left","defaultContent" : ''
				}, {"data": "quantity","className":"td-right","defaultContent" : '0'
				}, {"data": "overQuantity","className":"td-right","defaultContent" : '0'
				}
			] ,
			"columnDefs":[
	      		{"targets":7,"render":function(data, type, row){
	      			var val = row["overQuantity"];
	      			if(val == '' || val == null || val == 'null')
	      				val = 0;
	      			return val;
	      		}},
	      		{
					"visible" : false,
					"targets" : [3,4,5]
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
				
		//detailAjax();
		historyAjax();//领料记录
		
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
				var makeType = $('#makeType').val();
				var url = "${ctx}/business/requisitionzz"+"?makeType="+makeType;

					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					var YSId='${order.YSId }';
					var url =  "${ctx}/business/requisitionzz?methodtype=addinit&YSId="+YSId;
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
				//alert(d["bid_id"]);
				
				$('#example').DataTable().destroy();
				//$('#set_lines').DataTable().ajax.reload();
				//ajax_factory_bid_set_lines(d["bid_id"]);
				//var d = $('#contractTable').DataTable().row(this).data();
				//alert(d["requisitionId"])
				detailAjax(d["requisitionId"]);
					            
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

	<input type="hidden" id="makeType" value="${makeType }" />
	<form:hidden path="task.taskid" />
	<fieldset>
		<legend> 自制品领料单</legend>
		<table class="form" id="table_form">
			<tr>
				<td class="label" width="100px">任务编号：</td>					
				<td width="150px">${formModel.task.taskid }</td>
									
				<td class="label" width="100px">关联耀升编号：</td>					
				<td>${formModel.task.collectysid }</td>
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	<div id="DTTT_container" align="right" style="height:40px;">
		<!-- <a class="DTTT_button DTTT_button_text" id="insert" >继续领料</a> -->
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 领料记录</legend>
		<div class="list">
			<table id="example2" class="display" style="width:100%">
				<thead>				
					<tr>
						<th width="30px">No</th>
						<th class="dt-center" style="width:120px">领料单编号</th>
						<th class="dt-center" width="60px">领料日期</th>
						<th class="dt-center" >本次领料耀升编号</th>
						<th class="dt-center" width="70px">申请人</th>
						<th class="dt-center" width="70px">仓管员</th>
						<th class="dt-center" width="150px"></th>
					</tr>
				</thead>
			</table>
			</div>
	</fieldset>
	
	<fieldset>
		<legend> 领料详情</legend>
		<div class="list">		
			<table id="example" class="display" style="width:100%">
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th class="dt-center" width="120px">物料编号</th>
						<th class="dt-center" >物料名称</th>	
						<th class="dt-center" width="60px">物料特性</th>
						<th class="dt-center" width="60px">供应商</th>
						<th class="dt-center" width="60px">合同编号</th>
						<th class="dt-center" width="60px">当前领料</th>
						<th class="dt-center" width="80px">超领数量</th>
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

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	openLayer(url);

};

function showYS(YSId) {
	//var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;

	var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
	openLayer(url);

};

function doPrint(requisitionId) {
	var YSId = '${order.YSId }';
	var url = '${ctx}/business/requisition?methodtype=print';
	url = url +'&YSId='+YSId;
	url = url +'&requisitionId='+requisitionId;
		
	callProductDesignView("print",url);

};
</script>

</html>
