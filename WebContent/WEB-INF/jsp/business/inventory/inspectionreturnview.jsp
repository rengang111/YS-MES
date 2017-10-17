<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>检验退货管理-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

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
					}, {"className":"dt-body-center"
					}, {"className":"td-right"
					}, {"className":"td-right"
					}, {"className":"dt-body-center"
					}, {"className":"td-right"
					}, {"className":"td-right"
					}
				],
			
		}).draw();

				
		t.on('click', 'tr', function() {			

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
		$("#inspectReturn\\.returndate").val(shortToday());

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		
		ajax();

		
		//$('#example').DataTable().columns.adjust().draw();
		
		$("#arrival\\.arrivedate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$("#goBack").click(
				function() {
					var contractId='${arrived.contractId }';
					var url = "${ctx}/business/inspectionReturn?keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					var keyBackup = $('#keyBackup').val();				
					$('#formModel').attr("action", "${ctx}/business/inspectionReturn?methodtype=updateInit"+"&keyBackup="+keyBackup);
					$('#formModel').submit();
		});
		
		foucsInit();
		//$(".quantity").attr('readonly', "true");
		//$(".quantity").addClass('read-only');
		
		
	});
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<form:hidden path="inspectReturn.ysid" value="${arrived.YSId }"/>
	<form:hidden path="inspectReturn.parentid" value=""/>
	<form:hidden path="inspectReturn.subid" value=""/>
	<form:hidden path="inspectReturn.arrivalid" value="${arrived.arrivalId }"/>
	<input type="hidden" id=report value="${arrived.report }" />
	<input type="hidden" id="keyBackup" value="${keyBackup }" />
	
	<fieldset>
		<legend> 退货信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>	
				<td width="200px">&nbsp;${arrived.YSId }</td>
				<td class="label">合同编号：</td>					
				<td width="200px">&nbsp;${arrived.contractId }
					<form:hidden path="inspectReturn.contractid" value="${arrived.contractId }"/></td>										
				<td class="label" width="100px">供应商：</td>
				<td>（${arrived.supplierId }）${arrived.supplierName }
					<form:hidden path="inspectReturn.supplierid" value="${arrived.supplierId }"/></td>
			</tr>
			<tr> 				
				<!-- <td class="label" width="100px">进料检报告编号：</td>	
				<td width="200px"></td> -->
				<td class="label">退货处理人：</td>					
				<td width="200px">${userName }</td>										
				<td class="label" width="100px">处理日期：</td>
				<td colspan="3">${arrived.returnDate }</td>
			</tr>
												
		</table>		
	</fieldset>
	<div style="clear: both"></div>
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="insert" class="DTTT_button">编辑</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	<fieldset style="margin-top: -25px;">
		<div class="list">	
		<table id="example" class="display" >
			<thead>				
				<tr>
					<th style="width:1px">No</th>
					<th class="dt-center" width="120px">物料编号</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" width="30px">单位</th>
					<th class="dt-center" width="60px">合同数量</th>
					<th class="dt-center" width="80px">报检数量</th>
					<th class="dt-center" width="60px">检验结果</th>
					<th class="dt-center" width="80px">合格数量</th>
					<th class="dt-center" width="60px">退货数量</th>
				</tr>
			</thead>
			
		<tbody>
			<c:forEach var="list" items="${material}" varStatus='status' >
				
					<tr>
						<td></td>
						<td>${list.materialId }
							<form:hidden path="inspectReturnList[${status.index}].materialid" value="${list.materialId }"/></td>
						<td><span>${list.materialName }</span></td>
						<td><span>${list.unit }</span></td>
						<td><span>${list.contractQuantity }</span></td>
						<td><span>${list.quantityInspection }</span></td>										
						<td>${list.checkResult }</td>
						<td><span>${list.quantityQualified }</span></td>	
						<td><span>${list.returnQuantity }</span></td>
					</tr>
					<script type="text/javascript">
							var index = '${status.index}';
							var type = '${list.checkResultId}';	
							var $oArrival = $('#inspectList'+index+'\\.quantityqualified');
							var quantity = '${list.quantity}';
							//alert($oArrival.val())
							
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
