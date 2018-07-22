<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>财务核算-成本查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	
	function historyAjax(scrollHeight) {
		
		var YSId = '${YSId }';

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
			"scrollY":scrollHeight,
			"scrollCollapse":true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/financereport?methodtype=getCostBomDetail&YSId="+YSId,
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
						var mateCost = data["cost"]["cost"];
						$('#mateCost').html(mateCost);
						errorCheckAndCostCount();
						
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
					}, {"data": "materialName"
					}, {"data": "unit","className":"td-center"
					}, {"data": "quantity","className":"td-right"
					}, {"data": "totalPrice","className":"td-right"
					}, {"data": "price","className":"td-right"
					}, {"data": null, "defaultContent" : '',
					}
				],
				"columnDefs":[
		    		{"targets":2,"render":function(data, type, row){
		    			return jQuery.fixedWidth(data,64);
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			return floatToCurrency(data);
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

		var scrollHeight = $(document).height() - 275; 
		historyAjax(scrollHeight);//领料统计

		$("#goBack").click(
				function() {
					var url = "${ctx}/business/financereport?methodtype=accountingInit";
					location.href = url;		
		});
		
		
		$("#doCreate2").click(
				function() {
				var contractId = '${order.contractId}'
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
	
	function errorCheckAndCostCount(){
		
		var cost = 0;
		$('#example2 tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(5).text();//领料金额
			
			contractValue= currencyToFloat(contractValue);
			//alert("计划用量+已领量+库存:"+fjihua+"---"+fyiling+"---"+fkucun)
			
			cost = cost + contractValue;
			
			if( contractValue == 0){
				
				$(this).addClass('error');
			}
						
		});	
		
		//$('#mateCost').html(floatToCurrency(cost));
		
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
				<td style="width:200px">${order.YSId }</td>
				<td class="label" style="width:100px"><label>产品编号：</label></td>					
				<td style="width:200px">${order.materialId}</td>	
				<td class="label" style="width:100px"><label>订单数量：</label></td>					
				<td>${order.totalQuantity}</td>	
			</tr>
			<tr>
				<td class="label" style="width:100px"><label>产品名称：</label></td>	
				<td colspan="5">${order.materialName }</td>	
			</tr>									
		</table>
		<table class="form" id="table_form">
			<tr>
				<td class="label" style="width:100px"><label>材料成本总计：</label></td>	
				<td style="width:150px" class="font16"><span id="mateCost" ></span></td>
				
				<td class="label" style="width:100px"><label>人工成本：</label></td>	
				<td style="width:150px" class="font16"><span id="labolCost"></span></td>	
				
				<td class="label" style="width:100px"><label>利润：</label></td>	
				<td style="width:150px" class="font16"><span id="profit"></span></td>	
				<td style="text-align: right;">
					<button type="button" id="doEdit" class="DTTT_button">核算</button>
					<button type="button" id=goBack class="DTTT_button">返回</button></td>	
			</tr>									
		</table>
	</fieldset>
	
	<fieldset>
		<legend>领料统计</legend>
		<div class="list">
			<table id="example2" class="display" width="100%">
				<thead>
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" width="120px">物料编号</th>
						<th class="dt-center" >物料名称</th>
						<th class="dt-center" width="40px">单位</th>
						<th class="dt-center" width="80px">领料数量</th>
						<th class="dt-center" width="80px">合计金额</th>
						<th class="dt-center" width="80px">平均单价</th>
						<th class="dt-center" width="10px"></th>
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
