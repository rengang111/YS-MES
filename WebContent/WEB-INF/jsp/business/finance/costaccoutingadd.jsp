<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>财务核算-成本确认</title>
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
			"sAjaxSource" : "${ctx}/business/financereport?methodtype=getStockoutByMaterialId&YSId="+YSId,
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
					}, {"data": "countQty","className":"td-right"
					}
				],
				"columnDefs":[
		    		{"targets":2,"render":function(data, type, row){
		    			return jQuery.fixedWidth(data,48);
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var rowIndex = row["rownum"];
		    			var text = '<input type="hidden" name="costBomList['+rowIndex+'].materialid" id="costBomList'+rowIndex+'.materialid"  value="'+data+'"a />';
		    			
		    			return data + text;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			var rowIndex = row["rownum"];
		    			var text = '<input type="hidden" name="costBomList['+rowIndex+'].quantity" id="costBomList'+rowIndex+'.quantity"  value="'+data+'" />';
		    			
		    			return floatToCurrency(data) + text;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			var rowIndex = row["rownum"];
		    			var total = floatToCurrency(data);
		    			var text = '<input type="hidden" name="costBomList['+rowIndex+'].totalprice" id="costBomList'+rowIndex+'.totalprice"  value="'+total+'" />';
		    			
		    			return total + text;
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			var rowIndex = row["rownum"];
		    			var quantity = currencyToFloat(row["quantity"]);
		    			var total = currencyToFloat(row["totalPrice"]);
		    			var price = floatToCurrency(total / quantity);
		    			var text = '<input type="hidden" name="costBomList['+rowIndex+'].price" id="costBomList'+rowIndex+'.price"  value="'+price+'" />';
		    			
		    			return price + text;
		    		}},
		    		{"targets":7,"render":function(data, type, row){
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

		$("#costBom\\.accountingdate").val(shortToday());
		
		var scrollHeight = $(document).height() - 275; 
		historyAjax(scrollHeight);//领料统计

		$("#goBack").click(
				function() {
					var url = "${ctx}/business/financereport?methodtype=accountingInit";
					location.href = url;		
		});
		
		
		$("#doCreate").click(
				function() {

				$('#formModel').attr("action", "${ctx}/business/financereport?methodtype=costAccountingSave");
				$('#formModel').submit();
	
		});
		
		//外汇报价换算
		$(".exchange").change(function() {
			
			var exchang  = currencyToFloat($('#bomPlan\\.exchangerate').val());
			var exRebate = currencyToFloat($('#bomPlan\\.rebaterate').val());
			var exprice  = currencyToFloat($('#bomPlan\\.exchangeprice').val());
			var mateCost = currencyToFloat($('#bomPlan\\.materialcost').val());
			var baseCost = currencyToFloat($('#bomPlan\\.productcost').val());
			var discount = currencyToFloat($('#bomPlan\\.discount').val());
			var commissi = currencyToFloat($('#bomPlan\\.commission').val());
			//利润 = 原币价格+退税-基础成本-因子-折扣
			//原币价格 = 汇率*报价
			//利润率= 利润/基础成本
			var rmb = exchang * exprice;
			var rebate = mateCost * exRebate / 1.17 / 100;
			var profit = rmb + rebate - baseCost - baseCost*(discount+commissi)/100;
			var profitRate = profit / baseCost * 100;
			//alert('p='+(rmb - baseCost)+'profit='+profit)
			$('#bomPlan\\.rebate').val(floatToCurrency(rebate));
			$('#bomPlan\\.rmbprice').val(floatToCurrency(rmb));
			$('#bomPlan\\.profit').val(floatToCurrency(profit));
			$('#bomPlan\\.profitrate').val(floatToCurrency(profitRate));
		});
		
		
	});
	

	
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
		
		$('#costBom\\.cost').val(floatToCurrency(cost));
		
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
	<input type="hidden" id="currencyId" value="${order.currencyId }" />
	<form:hidden path="costBom.accountingdate"  />
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr>
				<td class="label" style="width:100px">耀升编号：</td>					
				<td style="width:200px">${order.YSId }
					<form:hidden path="costBom.ysid" value="${order.YSId }"/></td>
				<td class="label" style="width:100px">产品编号：</td>					
				<td>${order.materialId}
					<form:hidden path="costBom.materialid" value="${order.materialId }"/></td>	
				<td class="label" style="width:100px">订单数量：</td>					
				<td>${order.totalQuantity}</td>		
			</tr>
			<tr>
				<td class="label" style="width:100px">产品名称：</td>	
				<td colspan="5">${order.materialName }</td>	
			</tr>									
		</table>
		<table class="form" id="table_form" width="100%">
			<tr>			
				<td class="label" style="width:100px">成本总计：</td>	
				<td style="width:100px" class="font16"><span id="totalCost"></span></td>	
				
				<td class="label" style="width:100px">材料成本：</td>	
				<td style="width:100px" class="font16">
					<form:input path="costBom.cost"  class="read-only num short" style="font-size: 14px;font-weight: bold;"/></td>
				
				<td class="label" style="width:100px">人工成本：</td>	
				<td style="width:100px" class="font16"><span id="labolCost"></span></td>	
				
				<td class="font16" colspan="2"><span id=""></span></td>	
			</tr>
			<tr class="td-center">
				<td class="td-center" >销售总额</td>
				<td class="td-center" width="100px">销售币种</td>
				<td class="td-center" style="width:100px">汇率</td>	
				<td class="td-center" style="width:100px">原币金额</td>	
				<td class="td-center" style="width:100px">退税率</td>	
				<td class="td-center" style="width:100px">退税</td>
				<td class="td-center" style="width:100px">利润</td>
				<td class="td-center" style="width:100px">利润率</td>
				
			</tr>
			<tr>
				<td class="td-center">${order.totalPrice }</td>
				<td class="td-center">${order.currency }</td>
				<td class="td-center">
					<form:input path="costBom.exchangerate"  class="num short exchange"  /></td>	
				<td class="td-center">
					<form:input path="costBom.rmbprice"  class="num short read-only"  /></td>
				<td class="td-center">
					<form:input path="costBom.rebaterate"  class="num mini exchange"  />%</td>
				<td class="td-center">
					<form:input path="costBom.rebate"  class="num short read-only"  /></td>
				<td class="td-center">
					<form:input path="costBom.profit"  class="num short read-only"  /></td>
				<td class="td-center">
					<form:input path="costBom.profitrate"  class="num short read-only" /></td>
				
			</tr>
		</table>
	</fieldset>
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doCreate" class="DTTT_button">成本确认</button>
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
						<th class="dt-center" width="60px">领料数量</th>
						<th class="dt-center" width="60px">合计金额</th>
						<th class="dt-center" width="60px">平均单价</th>
						<th class="dt-center" width="60px">领料次数</th>
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
