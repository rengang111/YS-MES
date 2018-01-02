<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>领料申请-领料单查看</title>
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
			"sAjaxSource" : "${ctx}/business/requisition?methodtype=getRequisitionHistory&YSId="+YSId,
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
					}, {"data": "requisitionUserId","className":"dt-body-center"
					}, {"data": "requisitionSts","className":"dt-body-center"
					}, {"data": null,"className":"td-center","defaultContent" : ''
					}, {"data": null,"className":"td-center","defaultContent" : ''
					}
				] ,
				"columnDefs":[
		    		{"targets":5,"render":function(data, type, row){
		    			var contractId = row["contractId"];		    			
		    			var rtn= "<a href=\"###\" onClick=\"doEdit('" + row["YSId"] + "','" + row["requisitionId"] + "')\">编辑</a>";
		    			rtn = rtn + "&nbsp;" + "<a href=\"###\" onClick=\"doDelete('" + row["recordId"] + "','" + row["requisitionId"] + "')\">删除</a>";
						return rtn;
		    		}},{"targets":6,"render":function(data, type, row){
		    			var contractId = row["contractId"];		    			
		    			var rtn= "<a href=\"###\" onClick=\"doPrint('" + row["requisitionId"] + "')\">打印领料单</a>";
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
			table.fnClearTable(false);
			table.fnDestroy();
		}
		if(requisitionId =='' || requisitionId == null)
			return;
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
			"sAjaxSource" : "${ctx}/business/requisition?methodtype=getRequisitionDetail&requisitionId="+requisitionId,
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
	      		}}
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
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/requisition";
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					var YSId='${order.YSId }';
					var url =  "${ctx}/business/requisition?methodtype=addinit&YSId="+YSId;
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
				if(!(typeof(d)=="undefined")){ 
					detailAjax(d["requisitionId"]);
				}
					            
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
	
	

	function doDelete(recordId,requisitionId) {
		if(confirm("删除后不能恢复,\n\n确定要删除吗？")) {
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : '',
				url : "${ctx}/business/requisition?methodtype=delete"+"&recordId="+recordId,
				success : function(data) {
					$('#example2').DataTable().ajax.reload(null,false);	
					var table = $('#example').dataTable();
					if(table) {
						table.fnClearTable(false);
						table.fnDestroy();
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			});
		}
		
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
	<form:hidden path="requisition.ysid"  value="${order.YSId }" />
	<fieldset>
		<legend> 领料单</legend>
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
	<div id="DTTT_container" align="right" style="height:40px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >继续领料</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 领料记录</legend>
		<div class="list">
			<table id="example2" class="display" style="width:100%">
				<thead>				
					<tr>
						<th width="30px">No</th>
						<th class="dt-center" style="width:150px">领料单编号</th>
						<th class="dt-center" width="150px">领料日期</th>
						<th class="dt-center" width="150px">申请人</th>
						<th class="dt-center" width="150px">是否出库</th>
						<th class="dt-center" >操作</th>
						<th class="dt-center" ></th>
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


function doPrint(requisitionId) {
	var YSId = '${order.YSId }';
	var url = '${ctx}/business/requisition?methodtype=print';
	url = url +'&YSId='+YSId;
	url = url +'&requisitionId='+requisitionId;
		
	callProductDesignView("print",url);

};

</script>

</html>
