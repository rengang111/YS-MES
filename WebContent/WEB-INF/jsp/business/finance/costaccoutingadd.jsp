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
						
						lirunjisuan();
						
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
					}, {"data": "unitQuantity","className":"td-right"
					}, {"data": "orderQty","className":"td-right"//4
					}, {"data": "stockoutQty","className":"td-right"//5
					}, {"data": null,"className":"td-right"//6 核算数量
					}, {"data": "price","className":"td-right" //7
					}, {"data": "totalPrice","className":"td-right" //8
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
		    			var unit = currencyToFloat(row["unitQuantity"]);
		    			var order = currencyToFloat(row["totalQuantity"]);
		    			var orderQty = floatToCurrency(unit * order);
		    			
		    			return orderQty;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
			    		return floatToCurrency(data);
		    		}},
		    		{"targets":6,"render":function(data, type, row){//核算数量
		    			var rowIndex = row["rownum"];
		    			//var orderQty = currencyToFloat(row["orderQty"]);
		    			var unit = currencyToFloat(row["unitQuantity"]);
		    			var order = currencyToFloat(row["totalQuantity"]);
		    			var stockoutQty = currencyToFloat(row["stockoutQty"]);
		    			var quantity = 0;
		    			
		    			if(unit==0)
		    				unit = 1;//默认设置单位使用量：1
		    				
		    			var orderQty = unit * order;
		    			
		    			//领料 >= 需求，取领料，否则取需求
		    			if(stockoutQty >= orderQty){
		    				quantity = stockoutQty;
		    			}else{
		    				quantity = orderQty;
		    			}
		    			quantity = floatToCurrency(quantity);

		    			var text = '<input type="hidden" name="costBomList['+rowIndex+'].quantity" id="costBomList'+rowIndex+'.quantity"  value="'+quantity+'" />';

		    			return quantity + text;
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			var rowIndex = row["rownum"];
		    			var price = currencyToFloat(row["price"]);
		    			//var orderQty = currencyToFloat(row["orderQty"]);
		    			var stockoutQty = currencyToFloat(row["stockoutQty"]);
		    			var unit = currencyToFloat(row["unitQuantity"]);
		    			var order = currencyToFloat(row["totalQuantity"]);
		    			
		    			if(unit==0)
		    				unit = 1;//默认设置单位使用量：1
		    			var orderQty = unit * order;
		    			var quantity = 0;
		    			//领料 >= 需求，取领料，否则取需求
		    			//if(stockoutQty >= orderQty){
		    			//	quantity = stockoutQty;
		    			//}else{
		    				quantity = orderQty;
		    			//}
		    			var total = floatToCurrency( quantity * price );
		    			var text = '<input type="hidden" name="costBomList['+rowIndex+'].totalprice" id="costBomList'+rowIndex+'.totalprice"  value="'+total+'" />';
		    			
		    			return total + text;
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			var rowIndex = row["rownum"];
		    			var price = currencyToFloat(data);
		    			var text = '<input type="hidden" name="costBomList['+rowIndex+'].price" id="costBomList'+rowIndex+'.price"  value="'+price+'" />';
		    			
		    			return price + text;
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

		$(".read-only").attr('readonly', "true");

		//人工成本计算
		//var labolCost = currencyToFloat('${LaborCost}');
		//var orderQty = currencyToFloat('${order.totalQuantity}');
		//var labol = labolCost * orderQty;
		//alert('labol'+labol)
		//$('#costBom\\.labolcost').val(floatToCurrency(labol));
		
		var scrollHeight = $(document).height() - 275; 
		historyAjax(scrollHeight);//领料统计

		$("#goBack").click(
				function() {
					var url = "${ctx}/business/financereport?methodtype=accountingInit";
					location.href = url;		
		});
		
		
		$("#doCreate").click(
				function() {

					$("#doCreate").attr("disabled", "disabled");
				$('#formModel').attr("action", "${ctx}/business/financereport?methodtype=costAccountingSave");
				$('#formModel').submit();
	
		});
		
		$("#costBom\\.exchangerate").change(function() {
			var currencyId  = $('#costBom\\.currency').val();
			var exRate = $('#costBom\\.exchangerate').val();
			var url = "${ctx}/business/financereport?methodtype=updateExchangeRate";
			url = url + "&currencyId="+currencyId+"&exRate="+exRate;

			$.ajax({
				type : "post",
				url : url,
				//async : false,
				//data : null,
				dataType : "text",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {			

					$().toastmessage('showNoticeToast', "汇率保存成功。");
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					//alert(textStatus)
				}
			});		
		})
		//外汇报价换算
		$(".exchange").change(function() {
			
			lirunjisuan();
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
	
	
	function errorCheckAndCostCount(){
		
		var cost = 0;
		$('#example2 tbody tr').each (function (){
			
			var stockOutQty   = $(this).find("td").eq(5).text();//领料数量
			var orderQty      = $(this).find("td").eq(4).text();//订单数量
			var contractValue = $(this).find("td").eq(8).text();//领料金额
			
			stockOutQty = currencyToFloat(stockOutQty);
			orderQty= currencyToFloat(orderQty);

			contractValue= currencyToFloat(contractValue);
			cost = cost + contractValue;
			
			if( stockOutQty > orderQty){
				
				$(this).addClass('error');
			}
						
		});	
		

		var labolCost = $('#costBom\\.labolcost').val();

		//var cost = currencyToFloat(labolCost) + mateCost;
		var mateCost = cost - currencyToFloat(labolCost);
		
		$('#costBom\\.materialcost').val(floatToCurrency(mateCost));
		$('#costBom\\.cost').val(floatToCurrency(cost));
		
	}
	
	function lirunjisuan(){
		
		var cost = currencyToFloat( $('#costBom\\.cost').val() );//总成本
		var discount = currencyToFloat( $('#costBom\\.discount').val() );//客户折扣
		var commission = currencyToFloat( $('#costBom\\.commission').val() );//客户佣金
		
		var mateCost  = currencyToFloat( $('#costBom\\.materialcost').val() );//材料成本
		var labolcost = currencyToFloat( $('#costBom\\.labolcost').val() );//人工成本
		var totalPrice = currencyToFloat( '${order.totalPrice }' );	//销售额
		var currency = $('#costBom\\.currency').val();//币种
		var exchange = currencyToFloat( $('#costBom\\.exchangerate').val() );//汇率
		var rebaterate = currencyToFloat( $('#costBom\\.rebaterate').val() );//退税率
		var deductCost = currencyToFloat( $('#costBom\\.deduct').val() );//跟单费用
		
		var actualSales = totalPrice * (1 - discount/100) * (1-commission/100);
		rebaterate = rebaterate / 100;		
		cost = mateCost + labolcost;//总成本=材料+人工；人工有可能是手动输入，因此重新计算
		
		var gross = 0;//毛利
		var profit = 0;//净利
		var rebate = 0;//退税
		var rmbprice = 0;
		var profitrate = 0;//利润率
		var zeng = 0;
		if(currency == 'RMB'){
			
			if(rebaterate == 0){
				//纯内销
				//增值税
				zeng = (actualSales - mateCost) / 1.16 * 0.16 ;
				gross = actualSales - cost - zeng;
				rmbprice = actualSales;
				
				$('#costBom\\.exchangerate').val('1');
				$('#costBom\\.exchangerate').addClass('read-only');	
				$('#costBom\\.rebaterate').val('0')
				$('#costBom\\.rebaterate').addClass('read-only')
				
			}else{
				//人民币外销
				exchange = 1;//外销人民币的汇率默认：1
				rebate = mateCost * rebaterate / (1 + rebaterate);//退税
				rmbprice = actualSales * exchange;
				gross = rmbprice - cost + rebate;
				
				$('#costBom\\.exchangerate').val('1');
				$('#costBom\\.exchangerate').addClass('read-only');						
			}
					
		}else{
			//纯外销			
			//退税
			rebate = mateCost * rebaterate / (1 + rebaterate);
			rmbprice = actualSales * exchange;
			gross = rmbprice - cost + rebate;
		}
		profit = gross + deductCost;//毛利-跟单费用（订单过程中，扣除费用是负数录入的）
		profitrate = profit / cost * 100
		
		$(".read-only").attr('readonly', "true");

		$('#costBom\\.actualsales').val(floatToCurrency(actualSales));//实际销售额
		$('#costBom\\.rmbprice').val(floatToCurrency(rmbprice));//原币金额
		$('#costBom\\.rebate').val(floatToCurrency(rebate));//退税
		$('#costBom\\.gross').val(floatToCurrency(gross));//毛利
		$('#costBom\\.profit').val(floatToCurrency(profit));//利润
		$('#costBom\\.profitrate').val(floatToCurrency(profitrate));//利润率
		$('#costBom\\.vat').val(floatToCurrency(zeng));//增值税
		
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
	<form:hidden path="costBom.accountingdate"  />
	<form:hidden path="costBom.currency"  value="${order.currencyId }"/>
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
				
				<td class="label" style="width:100px">入库时间：</td>	
				<td>${order.checkInDate }</td>	
				
				<td class="label" style="width:100px">客户：</td>	
				<td>${order.customerFullName }</td>	
					
			</tr>									
		</table>
		<table  class="form">
			<tr>
				<td class="label" style="width:100px;">成本总计：</td>	
				<td >
					<form:input path="costBom.cost"  class="read-only num " style="font-size: 14px;font-weight: bold;"/></td>	
				
				<td class="label" style="width:100px;">材料成本：</td>	
				<td >
					<form:input path="costBom.materialcost"  class="read-only num " style="font-size: 14px;font-weight: bold;"/></td>
			
				<td class="label" style="width:100px;">人工成本：</td>	
				<td >
					<form:input path="costBom.labolcost" value="${LaborCost}" class=" num exchange" style="font-size: 14px;font-weight: bold;" /></td>	
			
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
			
				<td class="td-center">
					<form:input path="costBom.discount"  class="num exchange" value="${order.discount }" style="width: 30px;"/>%</td>
				<td class="td-center">
					<form:input path="costBom.commission"  class="num exchange"  value="${order.commission }" style="width: 30px;"/>%</td>
				<td class="td-center">
					<form:input path="costBom.actualsales"  class="num short read-only"  /></td>
					
				<td class="td-center">${order.currency }</td>
				<td class="td-center">
					<form:input path="costBom.exchangerate"  class="num mini exchange" value="${order.exchange_rate }" /></td>	
				<td class="td-center">
					<form:input path="costBom.rmbprice"  class="num short read-only"  /></td>
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
				<td class="td-center" ></td>
			</tr>
			<tr>
				
				<td class="td-center">
					<form:input path="costBom.rebaterate"  value="16" class="num mini exchange"  />%</td>
				<td class="td-center">
					<form:input path="costBom.rebate"  class="num short read-only"  /></td>
				<td class="td-center">
					<form:input path="costBom.vat"  class="num short read-only"  /></td>
				<td class="td-center">
					<form:input path="costBom.gross"  class="num short read-only"  /></td>
				<td class="td-center">
					<form:input path="costBom.deduct"  class="num short read-only" value="${deductCost }" /></td>
				<td class="td-center">
					<form:input path="costBom.profit"  class="num short read-only"  /></td>
				<td class="td-center" >
					<form:input path="costBom.profitrate"  class="num mini read-only" />%</td>
				<td class="td-center"></td>
				
			</tr>
		</table>
	</fieldset>
	<fieldset class="action" style="text-align: right;right;margin-top: -20px;">
		<button type="button" id="doCreate" class="DTTT_button">成本确认</button>
		<button type="button" id=goBack class="DTTT_button">返回</button>
	</fieldset>
	
	<fieldset style="margin-top: -40px;">
		<legend>领料统计</legend>
		<div class="list" style="width:98%">
			<table id="example2" class="display" >
				<thead>
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" width="120px">物料编号</th>
						<th class="dt-center" >物料名称</th>
						<th class="dt-center" width="30px">单位量</th>
						<th class="dt-center" width="50px">订单数量</th>
						<th class="dt-center" width="50px">领料数量</th>
						<th class="dt-center" width="50px">核算数量</th>
						<th class="dt-center" width="60px">BOM单价</th>
						<th class="dt-center" width="60px">合计金额</th>
						<th class="dt-center" width="50px">领料次数</th>
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
