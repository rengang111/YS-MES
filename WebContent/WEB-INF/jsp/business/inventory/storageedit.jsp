<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function ajax() {

		var receiptId = $("#stock\\.receiptid").val();
		var t = $('#example').DataTable({
			
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
			"columns" : [
			        	{"className":"dt-body-center"
					}, {"className":"td-left"
					}, {
					}, {"className":"td-right"
					}, {"className":"td-right"
					}, {"className":"td-right"	
					}, {"className":"td-left"
					}, {"className":"td-left"
					}, {
					}
				],
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = data;				    			
    			name = jQuery.fixedWidth(name,35);				    			
    			return name;
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

		$("#stock\\.checkindate").val(shortToday());
		
		ajax();
		
		$("#goBack").click(
				function() {
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/storage?keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
				var receiptId = $("#receiptid").val();	
			$('#formModel').attr("action", "${ctx}/business/storage?methodtype=update&receiptId="+receiptId);
			$('#formModel').submit();
		});
		

		foucsInit();

		$(".quantity").attr('readonly', "true");
		$(".quantity").addClass('read-only');
		$(".quantity").removeClass('bgnone');
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/arrival?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
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

	<form:hidden path="stock.receiptid"  value="${receiptId}"/>
	<form:hidden path="stock.subid" />
	<form:hidden path="stock.arrivelid" />
	<form:hidden path="stock.contractid"  value=""/>
	<form:hidden path="stock.supplierid"  value=""/>
	
	
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="200px">&nbsp;${contract.YSId }</td>
							
				<td width="100px" class="label">成品编码：</td>
				<td width="200px">&nbsp;${contract.materialId }</td>							
				<td width="100px" class="label">成品名称：</td>
				<td>${contract.materialName }</td>
			</tr>
			<tr>							
				<td class="label">合同编号：</td>					
				<td>&nbsp;<a href="#" onClick="showContract('${contract.contractId }')">${contract.contractId }</a></td>								 	
				<td class="label">供应商：</td>					
				<td colspan="3">&nbsp;${contract.supplierName }</td>	
			</tr>
			<tr> 				
				<td class="label" width="100px">入库时间：</td>					
				<td width="200px">
					<form:input path="stock.checkindate" class="read-only" /></td>
							
				<td width="100px" class="label">仓管员：</td>
				<td width="200px">
					<form:input path="stock.keepuser" class="short read-only" value="${userName }" /></td>							
				<td class="label">物料类别：</td>
				<td>${contract.purchaseType }</td>
			</tr>
										
		</table>
	</fieldset>
	<fieldset class="action" style="text-align: right;margin-top:-20px">
		<button type="button" id="insert" class="DTTT_button">确认入库</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>	
	<fieldset style="margin-top: -40px;">
		<legend> 物料信息</legend>
		<div class="list">
		<table class="display" id="example">	
			<thead>		
				<tr>
						<th style="width:1px">No</th>
						<th style="width:100px">物料编号</th>
						<th>物料名称</th>
						<th style="width:65px">合同数量</th>
						<th style="width:65px">已入库数量</th>
						<th style="width:65px">待入库数</th>
						<th style="width:55px">包装方式</th>
						<th style="width:40px">件数</th>
						<th style="width:60px">库位编号</th>			
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${material}" varStatus='status' >			
					<tr>
						<td></td>
						<td>${list.materialId }
							<form:hidden path="stockList[${status.index}].materialid" value="${list.materialId }"/></td>
						<td>${list.materialName }</td>
						<td>${list.contractQuantity }</td>
						<td>${list.contractStorage }</td>
						<td><form:input path="stockList[${status.index}].quantity"  value="${list.quantity }" class="num short quantity" /></td>
						<td><form:select path="stockList[${status.index}].packaging" style="width:70px">
								<form:options items="${packagingList}" 
									itemValue="key" itemLabel="value"/></form:select></td>
						<td><form:input path="stockList[${status.index}].packagnumber" value="${list.packagNumber }" class="mini" /></td>
						<td><form:input path="stockList[${status.index}].areanumber" value="${list.areaNumber }"  class="short" /></td>
					</tr>
					<script type="text/javascript">
							var index = '${status.index}';
							var type = '${list.packagingId}';

							$('#stockList'+index+'\\.packaging').val(type);
					</script>
				
				</c:forEach>
			
		</tbody>		
										
		</table>
		</div>
	</fieldset>


</form:form>

</div>
</div>
</body>


</html>
