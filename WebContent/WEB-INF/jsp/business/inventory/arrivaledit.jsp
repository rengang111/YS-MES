<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-收货修改</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
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
					}, {
					}, {
					}, {"className":"dt-body-center"
					}, {"className":"dt-body-center"
					}, {"className":"td-right"	
					}
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

		//日期
		$("#arrival\\.arrivedate").val(shortToday());
		
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
					var makeType=$('#makeType').val();
					var url = "${ctx}/business/arrival?makeType="+makeType;
					location.href = url;		
				});
		
		$("#doView").click(
				function() {
					var makeType=$('#makeType').val();
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/arrival?methodtype=gotoArrivalView";
					url = url + "&contractId="+contractId;
					url = url + "&makeType="+makeType;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {

					$('#insert').attr("disabled","true").removeClass("DTTT_button");
			var makeType=$('#makeType').val();
			$('#formModel').attr("action", "${ctx}/business/arrival?methodtype=insert"+ "&makeType="+makeType);
			$('#formModel').submit();
		});

		foucsInit();
		
		
	});
	
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="tmpMaterialId" />
	<input type="hidden" id="makeType" value="${makeType }" />
	
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px"><label>收货编号：</label></td>					
				<td>${arrivalId }
					<form:hidden path="arrival.arrivalid"  value="${arrivalId }" /></td>
														
				<td width="100px" class="label">收货日期：</td>
				<td>
					<form:input path="arrival.arrivedate" class="short read-only"  value=""/></td>
				
				<td width="100px" class="label">仓管员：</td>
				<td>
					<form:input path="arrival.userid" class="short read-only" value="${userName }" /></td>
			</tr>
			<tr> 				
				<td class="label"><label>耀升编号：</label></td>					
				<td>${contract.YSId }
					<form:hidden path="arrival.ysid"  value="${contract.YSId }"/></td>
							
				<td class="label"><label>合同编号：</label></td>					
				<td>&nbsp;${contract.contractId }
					<form:hidden path="arrival.contractid"  value="${contract.contractId }"/></td>
							
				<td class="label"><label>供应商：</label></td>					
				<td>&nbsp;${contract.supplierId } | ${contract.supplierName}
					<form:hidden path="arrival.supplierid"  value="${contract.supplierId }"/></td>	
			</tr>
										
		</table>
</fieldset>

<fieldset>
	<span class="tablename"> 收货登记</span>
	<a id="doView" href="###" >收货详情</a>
	<div class="list">	
	<table id="example" class="display" >
		<thead>				
			<tr>
				<th style="width:1px">No</th>
				<th class="dt-center" width="175px">物料编号</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="30px">单位</th>
				<th class="dt-center" width="80px">本次收货</th>
				<th class="dt-center" width="60px">合同数量</th>
			</tr>
		</thead>
		
	<tbody>
		<c:forEach var="list" items="${material}" varStatus='status' >	
			<tr>
				<td></td>
				<td>${list.materialId }
					<form:hidden path="arrivalList[${status.index}].materialid" value="${list.materialId }" /></td>
				<td><span>${list.materialName }</span></td>
				<td><span>${list.unit }</span></td>
				<td><form:input path="arrivalList[${status.index}].quantity" class="num mini"  value="${list.quantity }"/></td>
				<td><span>${list.total }</span></td>
			</tr>
			
		</c:forEach>
		
	</tbody>
</table>
</div>
</fieldset>	

<fieldset class="action" style="text-align: right;">
	<button type="button" id="insert" class="DTTT_button">确认收货</button>
</fieldset>	
</form:form>

</div>
</div>
</body>
</html>
