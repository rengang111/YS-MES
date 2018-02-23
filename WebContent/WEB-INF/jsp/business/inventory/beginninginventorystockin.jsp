<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>库存一览--入库</title>
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
					{ },
					{},
					{"className" : ''},
					{"className" : 'td-left'},//4
					{"className" : 'td-left'},//
					{"className" : ''},
					{"className" : 'td-right'},//	7
				
				]
				
			}
		);

	}

	$(document).ready(function() {
		
		ajax("true");
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});		
		
		sumFn();
	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		ajax("false");

	}

	function sumFn(){
		
		var sum7 = 0;
		$('#TMaterial tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(7).text();////合同
			contractValue= currencyToFloat(contractValue);
			
			sum7 = sum7 + contractValue;
						
		});	
		
		$('#totalValue').html(floatToCurrency(sum7));
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">
		
		<fieldset>
			<legend> 入库明细</legend>
			<table height="30px">
				<tr>
					<td width="10%"></td> 
					<td class="label">物料编号：</td>
					<td width="150px">${material.materialId }</td>
					<td class="label">物料名称：</td> 
					<td>${material.materialName }</td>					
				</tr>
			</table>		
			<div class="list">
				<table  id="TMaterial" class="display">
					<thead>	
						<tr >
							<th style="width: 1px;">No</th>
							<th style="width: 70px;">入库时间</th>
							<th style="width: 100px;">入库单号</th>
							<th style="width: 70px;">仓库</th>
							<th style="width: 70px;">合同编号</th>
							<th style="width: 60px;">供应商</th>
							<th>供应商名称</th>
							<th style="width: 60px;">入库数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var='list' items='${stockinList}' varStatus='status'>
							<tr>
								<td>${status.index +1 }</td>
								<td>${list.checkInDate }</td>
								<td>${list.receiptId }</td>
								<td>${list.areaNumber }</td>
								<td>${list.contractId }</td>
								<td>${list.supplierId }</td>
								<td>${list.supplierFullName }</td>
								<td>${list.quantity }</td>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th>合计：</th>
							<th style="text-align: right;"><span id="totalValue"></span></th>
							
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
