<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>库存一览--待出</title>
<script type="text/javascript">

	function ajax(sessionFlag) {
		
		var t = $('#TMaterial').DataTable({
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : 'T<"clear">',
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"className" : 'td-center'},
					{"className" : ''},
					{"className" : 'td-left'},//
					{"className" : ''},
					{"className" : 'td-right',"defaultContent" : '0'},//
					{"className" : 'td-right',"defaultContent" : '0'},//
				
				],
			"columnDefs":[
	    		{
	    			"visible":false,
	    			"targets":[]
	    		}
	    	]
		});
	}

	$(document).ready(function() {
		
		ajax("true");
		

		$("#doEdit").click(
				function() {
					var materialId='${material.materialId }';
					var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanByMaterialIdToEdit&materialId=' + materialId;

					location.href = url;		
		});
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});		
		
		sumFn(4);
		sumFn(5);
		$('#waitout').html(floatToCurrency( currencyToFloat($('#totalValue4').html()) - currencyToFloat($('#totalValue5').html()) ))
	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		ajax("false");

	}

	function sumFn(num){
		
		var sum7 = 0;
		$('#TMaterial tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(num).text();////合同
			contractValue= currencyToFloat(contractValue);
			
			sum7 = sum7 + contractValue;
						
		});	
		
		$('#totalValue'+num).html(floatToCurrency(sum7));
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">
		
		<fieldset>
			<legend> 采购方案</legend>
			<table width="100%">
				<tr>
					<td class="label" width="100px">物料编号：</td>
					<td width="120px">${material.materialId }</td>
					<td class="label" width="70px">物料名称：</td> 
					<td>${material.materialName }</td>		
					<td class="label" width="100px">剩余待出库数量：</td>
					<td width="100px"><span id="waitout"></span></td>
				</tr>
			</table>
			<div class="list">
				<table  id="TMaterial" class="display">
					<thead>	
						<tr >
							<th style="width: 1px;">No</th>
							<th style="width: 80px;">耀升编号</th>
							<th style="width: 120px;">产品编号</th>
							<th>产品名称</th>
							<th style="width: 60px;">生产需求</th>
							<th style="width: 60px;">领料数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var='list' items='${contractList}' varStatus='status'>							
							<tr>
								<td>${status.index +1 }</td>
								<td>${list.YSId }</td>
								<td>${list.productId }</td>
								<td><span id="shortName${status.index }">${list.productName }</span></td>
								<td>${list.manufactureQuantity }</td>
								<td>${list.stockoutQty }</td>
							</tr>
							
							<script type="text/javascript">
								var materialName = '${list.productName}';
								var index = '${status.index}';			
								/*
								var stockoutQty = '${list.stockoutQty}';//实际出库数
								var correctionQty = '${list.correctionQty}';//修正值
								//alert("m--o"+'${list.manufactureQuantity }'+"---"+stockoutQty)
								
								var viewQty='0';
								if (currencyToFloat(stockoutQty) == 0 && currencyToFloat(correctionQty) > 0 ){
									viewQty = "(改)" + floatToCurrency( correctionQty);
								}else{
									viewQty = floatToCurrency( stockoutQty);
								}
								*/
								//$('#stockoutQty'+index).html(viewQty);
								$('#shortName'+index).html(jQuery.fixedWidth(materialName,48));
							</script>	
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th>合计：</th>
							<th style="text-align: right;"><span id="totalValue4"></span></th>
							<th style="text-align: right;"><span id="totalValue5"></span></th>
						</tr>
					</tfoot>
				</table>
			</div>	
	</fieldset>
</form:form>
</div>
</div>
</body>
</html>
