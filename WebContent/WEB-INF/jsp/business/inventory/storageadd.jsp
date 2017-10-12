<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-入库登记</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter = 5;
	var shortYear = ""; 
	
	function ajax() {

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
			//"scrollY"    : "160px",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
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

		//设置光标项目
		//$("#attribute1").focus();
		//$("#order\\.piid").attr('readonly', "true");

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#stock\\.checkindate").val(shortToday());
		
		ajax();
		
		$("#stock\\.checkindate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$("#goBack").click(
				function() {
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/storage?keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					
			$('#formModel').attr("action", "${ctx}/business/storage?methodtype=insert");
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

	<form:hidden path="stock.subid" />
	<form:hidden path="stock.ysid"  value="${contract.YSId }"/>
	<form:hidden path="stock.arrivelid"  value="${contract.arrivalId }"/>
	<form:hidden path="stock.supplierid"  value="${contract.supplierId }"/>
	<form:hidden path="stock.contractid"  value="${contract.contractId }"/>
	
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
						<td><form:input path="stockList[${status.index}].quantity"  value="${list.quantityQualified }" class="num short quantity" /></td>
						<td><form:select path="stockList[${status.index}].packaging" style="width:70px">
								<form:options items="${packagingList}" 
									itemValue="key" itemLabel="value"/></form:select></td>
						<td><form:input path="stockList[${status.index}].packagnumber" class="mini" /></td>
						<td><form:input path="stockList[${status.index}].areanumber" class="short" /></td>
					</tr>
					<script type="text/javascript">
							var index = '${status.index}';
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

<script type="text/javascript">

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	openLayer(url);

};

function showYS(YSId) {
	var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;

	//var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
	openLayer(url);

};

</script>

</html>
