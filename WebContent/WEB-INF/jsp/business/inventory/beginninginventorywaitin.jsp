<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>库存一览--待入</title>
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
				
			}
		);

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
		
		var contract = sumFn(5);
		var stockin = sumFn(6);
		$('#waitout').html(floatToCurrency( contract - stockin) );
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
		
		return sum7;
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
					<td class="label" width="100px">剩余待入库数量：</td>
					<td width="100px"><span id="waitout"></span></td>
				</tr>
			</table>
			<div class="list">
				<table  id="TMaterial" class="display">
					<thead>	
						<tr >
							<th style="width: 1px;">No</th>
							<th style="width: 90px;">合同编号</th>
							<th style="width: 80px;">耀升编号</th>
							<th style="width: 120px;">产品编号</th>
							<th>产品名称</th>
							<th style="width: 60px;">合同数</th>
							<th style="width: 60px;">入库数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var='list' items='${contractList}' varStatus='status'>							
							<tr>
								<td>${status.index +1 }</td>
								<td>${list.contractId }</td>
								<td>${list.YSId }</td>
								<td>${list.productId }</td>
								<td><span id="shortName${status.index }">${list.productName }</span></td>
								<td>${list.contractQty }</td>
								<td>${list.stockinQty }</td>
							</tr>
							
							<script type="text/javascript">
								var materialName = '${list.productName	}';
								var index = '${status.index}';
								$('#shortName'+index).html(jQuery.fixedWidth(materialName,48));
							</script>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th>合计：</th>
							<th style="text-align: right;"><span id="totalValue5"></span></th>
							<th style="text-align: right;"><span id="totalValue6"></span></th>
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
