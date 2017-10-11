<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-进料报检</title>
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
					}, {
					}, {"className":"td-right"
					}
				],
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(7)',function() {

			var $td = $(this).parent().find("td");

			var $oArrival = $td.eq(7).find("input");
			var $oQuantity= $td.eq(5).find("span");
			var $oRecorde = $td.eq(6).find("select");
			//var $oSurplus = $td.eq(7).find("span");

			var type = $oRecorde.val();
			var quantity = $oQuantity.text();
			//alert(quantity)
			if(type == '030'){
				//让步接收,允许输入
				$oArrival.removeAttr('readonly');
				$oArrival.removeClass('read-only');
			}else if( type == '040'){
				
				quantity = 0;
				$oArrival.attr('readonly', "true");
				$oArrival.addClass('read-only');
				
			}else{
				$oArrival.attr('readonly', "true");
				$oArrival.addClass('read-only');
			}
			
			$oArrival.val(quantity);
			//var fArrival  = currencyToFloat($oArrival.val());
			//var fRecorde  = currencyToFloat($oRecorde.html());
			//var fquantity = currencyToFloat($oQuantity.html());	
			
			
			
			//剩余数量
			//var fsurplus = floatToCurrency(fquantity - fRecorde - fArrival);	
			//$oSurplus.html(fsurplus);
			//$oArrival.val(floatToCurrency(fArrival))

		});
		
		/*				
		t.on('click', 'tr', function() {			

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
			
		});
		*/
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
		$("#inspect\\.checkdate").val(shortToday());

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
					var url = "${ctx}/business/receiveinspection?keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					var keyBackup = $('#keyBackup').val();				
					$('#formModel').attr("action", "${ctx}/business/receiveinspection?methodtype=insert"+"&keyBackup="+keyBackup);
					$('#formModel').submit();
		});
		
		//foucsInit();
		$(".quantity").attr('readonly', "true");
		$(".quantity").addClass('read-only');
		
		checkResult();//
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/arrival?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}
	
	//检验结果
	function checkResult(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var type      = $(this).find("td").eq(6).find("select").val();
			var $oArrival = $(this).find("td").eq(7).find("input");
			//alert(type)
			if(type == '030'){
				//让步接收,允许输入
				quantity = '${list.quantityQualified}';
				$oArrival.removeAttr('readonly');
				$oArrival.removeClass('read-only');
			}else if( type == '040'){
				//退货
				quantity = 0;
				$oArrival.attr('readonly', "true");
				$oArrival.addClass('read-only');
				
			}else{
				$oArrival.attr('readonly', "true");
				$oArrival.addClass('read-only');
			}			
		})		
		
	}
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<form:hidden path="inspect.ysid" value="${arrived.YSId }"/>
	<form:hidden path="inspect.parentid" value=""/>
	<form:hidden path="inspect.subid" value=""/>
	<form:hidden path="inspect.arrivedate" value="${arrived.arriveDate }"/>
	<input type="hidden" id=report value="${arrived.report }" />
	<input type="hidden" id="keyBackup" value="${keyBackup }" />
	
	<fieldset>
		<legend> 报检信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">到货登记：</td>	
				<td width="200px">${arrived.arrivalId }
					<form:hidden path="inspect.arrivalid" value="${arrived.arrivalId }"/></td>
				<td class="label">合同编号：</td>					
				<td width="200px">${arrived.contractId }
					<form:hidden path="inspect.contractid" value="${arrived.contractId }"/></td>										
				<td class="label" width="100px">供应商：</td>
				<td>${arrived.supplierName }
					<form:hidden path="inspect.supplierid" value="${arrived.supplierId }"/></td>
			</tr>
			<tr> 				
				<!-- <td class="label" width="100px">进料检报告编号：</td>	
				<td width="200px">
					<form:input path="inspect.inspectionid" class="read-only" /></td> -->
				<td class="label">质检员：</td>					
				<td width="200px">
					<form:input path="inspect.checkerid" value="${userName }" class="read-only" /></td>										
				<td class="label" width="100px">报检日期：</td>
				<td colspan="3">
					<form:input path="inspect.checkdate" value="" class="read-only"/></td>
			</tr>
												
		</table>		
	</fieldset>
	<div style="clear: both"></div>
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="insert" class="DTTT_button">保存</button>
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
					<th class="dt-center" width="80px">本次到货</th>
					<th class="dt-center" width="60px">检验结果</th>
					<th class="dt-center" width="60px">合格数量</th>
				</tr>
			</thead>
			
		<tbody>
			<c:forEach var="list" items="${material}" varStatus='status' >
				
					<tr>
						<td></td>
						<td>${list.materialId }
							<form:hidden path="inspectList[${status.index}].materialid" value="${list.materialId }"/></td>
						<td><span>${list.materialName }</span></td>
						<td><span>${list.unit }</span></td>
						<td><span>${list.contractQuantity }</span></td>
						<td><span>${list.quantity }</span></td>												
						<td><form:select path="inspectList[${status.index}].checkresult" style="width: 100px;">
								<form:options items="${resultList}" 
									itemValue="key" itemLabel="value"/></form:select></td>
						<td><span><form:input path="inspectList[${status.index}].quantityqualified" value="${list.quantityQualified }" class="num short quantity"/></span></td>
					</tr>
					<script type="text/javascript">
							var index = '${status.index}';
							var type = '${list.checkResultId}';	
							var $oArrival = $('#inspectList'+index+'\\.quantityqualified');
							var quantity = '${list.quantity}';
							//alert($oArrival.val())
							if(type=='010')
								type = '020';//默认设置为合格
							$('#inspectList'+index+'\\.checkresult').val(type);
					</script>
				
			</c:forEach>
			
		</tbody>
	</table>
	</div>
	</fieldset>		
	<fieldset>
		<legend> 检验报告</legend>
		<table class="form" id="table_form">		
			<tr>
				<td rowspan="3" width="700">
					<form:textarea path="inspect.report" rows="7" cols="80" /></td>
			</tr>												
		</table>
		
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
