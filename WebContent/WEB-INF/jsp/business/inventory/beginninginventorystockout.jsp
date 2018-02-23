<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>库存一览--出库</title>
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
							{"className" : ''},
							{"className" : 'td-left'},//
							{"className" : 'td-left'},//
							{"className" : 'td-right'},//	
							{"className" : ''},
				
				],
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
	
	function sumFn(){
		
		var sum7 = 0;
		$('#TMaterial tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(5).text();////合同
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
			<legend> 出库明细</legend>
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
							<th style="width: 40px;">No</th>
							<th style="width: 100px;">出库时间</th>
							<th style="width: 100px;">出库单号</th>
							<th style="width: 100px;">耀升编号</th>
							<th style="width: 100px;">领料部门</th>
							<th style="width: 100px;">领料数量</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var='list' items='${stockoutList}' varStatus='status'>
							<tr>
								<td>${status.index +1 }</td>
								<td>${list.checkOutDate }</td>
								<td>${list.stockOutId }</td>
								<td>${list.YSId }</td>
								<td>${list.loginName }</td>
								<td>${list.quantity }</td>
								<td></td>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th>合计：</th>
							<th style="text-align: right;"><span id="totalValue"></span></th>
							<th></th>
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
