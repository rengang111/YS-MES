<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>财务核算-成本查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	
	function historyAjax(scrollHeight) {
		
		var YSId = '${order.YSId }';

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
		
		
		$("#doCreate").click(
				function() {
			
				var YSId = '${order.YSId }';
				var url = "${ctx}/business/financereport?methodtype=costAccountingEdit"+"&YSId="+YSId;
				location.href = url;	
	
		});
		
		
		
	});
	
	function doShowOrder(PIId,YSId){
		var url = '${ctx}/business/order?methodtype=getOrderDetailByYSId&openFlag=newWindow&YSId=' + YSId+'&PIId=' + PIId;
		
		callProductDesignView("订单信息",url);
	}
	
	function doShowPlan(YSId){
		var url = '${ctx}/business/purchasePlan?methodtype=showPurchasePlan&YSId=' + YSId;
		callProductDesignView('采购方案',url);
	}
	
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
		<legend> 财务核算</legend>
		<table class="form" id="table_form">
			<tr>
				<td class="label" style="width:100px">耀升编号：</td>					
				<td style="width:150px">
					<a href="###" onClick="doShowOrder('${order.PIId }','${order.YSId }')">${order.YSId }</a>
					<form:hidden path="costBom.ysid" value="${order.YSId }"/></td>
				<td class="label" style="width:100px">产品编号：</td>					
				<td style="width:150px">
					<a href="###" onClick="doShowPlan('${order.YSId }')">${order.materialId}</a>
					<form:hidden path="costBom.materialid" value="${order.materialId }"/></td>	

				<td class="label" style="width:100px">产品名称：</td>	
				<td>${order.materialName }</td>	
			</tr>
			<tr>
				<td class="label" style="width:100px">订单数量：</td>					
				<td>${order.totalQuantity}</td>	
				<td class="label" style="width:100px">客户：</td>	
				<td colspan="3">${order.customerFullName }</td>		
			</tr>									
		</table>
		<table  class="form">
			<tr>
				<td class="label" style="width:100px;">成本总计：</td>	
				<td >${cost.cost}</td>	
				
				<td class="label" style="width:100px;">材料成本：</td>	
				<td >${cost.materialCost}</td>
			
				<td class="label" style="width:100px;">人工成本：</td>	
				<td >${cost.labolCost}</td>	
			
			</tr>
		</table>
		<table class="form" id="table_form"  style="width:100%">
			<tr class="td-center">
				<td class="td-center" style="width:120px">销售总额</td>
				<td class="td-center" style="width:120px">客户折扣</td>	
				<td class="td-center" style="width:120px">客户佣金</td>	
				<td class="td-center" style="width:170px">实际销售额</td>
				
				<td class="td-center" width="120px">销售币种</td>
				<td class="td-center" style="width:120px">汇率</td>	
				<td class="td-center" style="width:170px" >原币金额</td>	
				<td class="td-center" ></td>	
				
			</tr>
			<tr>
				<td class="td-center">${order.totalPrice }</td>
			
				<td class="td-center">${cost.discount }%</td>
				<td class="td-center">${cost.commission }%</td>
				<td class="td-center">${cost.actualSales }</td>
					
				<td class="td-center">${cost.currency }</td>
				<td class="td-center">${cost.exchangeRate }</td>	
				<td class="td-center">${cost.RMBPrice }</td>
				<td class="td-center"></td>
				
				
			</tr>
			<tr class="td-center">
				
				<td class="td-center" style="width:100px">退税率</td>	
				<td class="td-center" style="width:100px">退税</td>	
				<td class="td-center" style="width:100px">增值税</td>
				<td class="td-center" style="width:100px">毛利</td>
				<td class="td-center" style="width:100px">跟单费用</td>
				<td class="td-center" style="width:100px">净利</td>
				<td class="td-center" >利润率</td>
				<td class="td-center"></td>
			</tr>
			<tr>
				
				<td class="td-center">${cost.rebateRate }%</td>
				<td class="td-center">${cost.rebate }</td>
				<td class="td-center">${cost.vat }</td>
				<td class="td-center">${cost.gross }</td>
				<td class="td-center">${cost.deduct }</td>
				<td class="td-center">${cost.profit }</td>
				<td class="td-center">${cost.profitRate }%</td>
				<td class="td-center"></td>
				
			</tr>
		</table>
	</fieldset>
	<fieldset class="action" style="text-align: right;right;margin-top: -20px;">
		<button type="button" id="doCreate" class="DTTT_button">编辑</button>
		<button type="button" id=goBack class="DTTT_button">返回</button>
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
