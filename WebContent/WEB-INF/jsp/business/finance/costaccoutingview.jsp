<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-收货记录</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	
	function historyAjax() {
		
		var contractId = '${contract.contractId }';
		
		var t = $('#example2').DataTable({			
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
			"sAjaxSource" : "${ctx}/business/arrival?methodtype=getArrivalHistory&contractId="+contractId,
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
					}, {"data": "materialId"
					}, {"data": "materialName","className":"td-right"
					}, {"data": "unit","className":"td-right"
					}, {"data": "quantity","className":"td-right"
					}, {"data": "quantity","className":"td-right"
					}, {"data": "quantity","className":"td-right"
					}, {"data": "LoginName","className":"td-right"
					}
				],
				"columnDefs":[
		    		{"targets":4,"render":function(data, type, row){
		    			return jQuery.fixedWidth(data,32);
		    		}},
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
		
		historyAjax();//领料统计

		$("#goBack").click(
				function() {
					var url = "${ctx}/business/costAccounting";
					location.href = url;		
		});
		
		
		$("#doCreate2").click(
				function() {
				var contractId = '${contract.contractId}'
				var makeType=$('#makeType').val();
				var url = '${ctx}/business/arrival?methodtype=addinit&contractId='+contractId+"&makeType="+makeType;
				location.href = url;
	
		});
		
		
	});
	
	function doEdit(contractId,arrivalId) {

		var makeType=$('#makeType').val();
		var url = '${ctx}/business/arrival?methodtype=edit&contractId='+contractId
				+'&arrivalId='+arrivalId+'&makeType='+makeType;
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
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr>
				<td class="label" style="width:100px"><label>耀升编号：</label></td>					
				<td style="width:200px">${contract.YSId }</td>
				<td class="label" style="width:100px"><label>产品编号：</label></td>					
				<td>${contract.fullName}</td>	
			</tr>
			<tr>
				<td class="label" style="width:100px"><label>产品名称：</label></td>	
				<td colspan="3">${contract.deliveryDate }</td>	
			</tr>
			<tr>
				<td class="label" style="width:100px"><label>材料成本总计：</label></td>	
				<td colspan="3"></td>	
			</tr>									
		</table>
	</fieldset>
	
	<div style="clear: both"></div>
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doCreate" class="DTTT_button">成本确认</button>
		<button type="button" id=goBack class="DTTT_button">返回</button>
	</fieldset>
	<fieldset>
		<legend>领料统计</legend>
		<div class="list">
			<table id="example2" class="display" >
				<thead>
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" width="120px">物料编号</th>
						<th class="dt-center" >物料名称</th>
						<th class="dt-center" width="40px">单位</th>
						<th class="dt-center" width="60px">领料数量</th>
						<th class="dt-center" width="60px">合计金额</th>
						<th class="dt-center" style="width:50px">平均单价</th>
						<th class="dt-center" style="width:50px">领料次数</th>
					</tr>
				</thead>
			</table>
		</div>
	</fieldset>

</form:form>

</div>
</div>
</body>


</html>
