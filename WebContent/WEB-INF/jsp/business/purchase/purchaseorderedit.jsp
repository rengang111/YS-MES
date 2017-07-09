<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
<title>采购合同-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function ajaxRawGroup() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,

			dom : '<"clear">rt',
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {"className":"td-center"
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				]
			
		}).draw();
		
		t.on('blur', 'tr td:nth-child(5),tr td:nth-child(6)',function() {
			
	           $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
			
		t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {
			
            var $tds = $(this).parent().find("td");
			
            var $oQuantity  = $tds.eq(4).find("input");
			var $oThisPrice = $tds.eq(5).find("input");
			var $oAmounti   = $tds.eq(6).find("input:hidden");
			var $oAmounts   = $tds.eq(6).find("span");
			
			var fPrice    = currencyToFloat($oThisPrice.val());		
			var fQuantity = currencyToFloat($oQuantity.val());			
			var fTotalOld = currencyToFloat($oAmounti.val());
			
			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vPrice = floatToCurrency(fPrice);	
			var vQuantity = floatToNumber(fQuantity);
			var vTotalNew = floatToCurrency(fTotalNew);
					
			//详情列表显示新的价格
			$oThisPrice.val(vPrice);					
			$oQuantity.val(vQuantity);	
			$oAmounti.val(vTotalNew);	
			$oAmounts.html(vTotalNew);	

			//列合计
			weightsum();
			
		});
			
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

	};//ajaxRawGroup()
	
	$(document).ready(function() {

		ajaxRawGroup();			
		
		$( "#tabs" ).tabs();
		
		var deliverydate = '${contract.purchaseDate}';
		if(deliverydate == "" || deliverydate == null){
		var date = new Date(shortToday());
			date.setDate(date.getDate()+20);
			$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
			$("#contract\\.purchasedate").val(shortToday());
		}
		
		$("#contract\\.purchasedate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 

		$("#contract\\.deliverydate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});
		
		
		$('#example').DataTable().columns.adjust().draw();		
		
		$("#goBack").click(
				function() {
					history.go(-1);
					//var url = '${ctx}/business/purchase?methodtype=init';
					//location.href = url;		
				});
		
		$("#insert").click(
				function() {			
			$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=update");
			$('#attrForm').submit();
		});		

		$("#contract\\.purchasedate").change(function(){
			
			var val = $(this).val();
			var date = new Date(val);
			date.setDate(date.getDate()+20);
			$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
		});	
		
		$("#contract\\.supplierid").change(function(){
			
			var YSId = '${order.YSId }';
			var supplierId = $(this).val();
			var url = '${ctx}/business/contract?methodtype=edit&YSId='+YSId+"&supplierId="+supplierId;
			location.href = url;	
		});	
		
		//input格式化
		foucsInit();
		
		//列合计
		weightsum();

	});	
	
	//列合计
	function weightsum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(6).find("span").text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;
			
		})
		var fsum = floatToCurrency(sum);
		$('#weightsum').text(fsum);
		$('#contract\\.total').val(fsum);
		

	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
			
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
				<tr> 		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="200px">${contract.YSId }
						<form:hidden path="contract.recordid" value="${contract.contractRecordId }"/>
						<form:hidden path="contract.ysid" value="${contract.YSId }"/></td>
									
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td width="150px">&nbsp;${ contract.productId }</td>
						
					<td class="label" width="100px"><label>产品名称：</label></td>
					<td colspan="3">&nbsp;${ contract.productName } </td>
				</tr>	
				<tr> 		
					<td class="label"><label>供应商编号：</label></td>					
					<td>${ contract.supplierId }
						<form:hidden path="contract.supplierid" value="${contract.supplierId }"/></td>
									
					<td class="label"><label>供应商简称：</label></td>					
					<td>&nbsp;${ contract.shortName }</td>
						
					<td class="label"><label>供应商名称：</label></td>
					<td colspan="3">&nbsp;${ contract.fullName }</td>
				</tr>	
				<tr> 		
					<td class="label"><label>采购合同编号：</label></td>					
					<td>${ contract.contractId }
						<form:hidden path="contract.contractid" value="${contract.contractId }"/></td>
					<td class="label"><label>下单日期：</label></td>
					<td>
						<form:input path="contract.purchasedate" value="${ contract.purchaseDate }"/></td>
					<td class="label"><label>合同交期：</label></td>
					<td colspan="3">
						<form:input path="contract.deliverydate" value="${ contract.deliveryDate }"/></td>
				</tr>									
			</table>
			
	</fieldset>
	
	<div style="clear: both"></div>		
	<fieldset>
	<legend> 合同详情</legend>
	
	<div class="list">
	<table id="example" class="display">	
		<thead>
		<tr>
			<th style="width:30px">No</th>
			<th style="width:150px">物料ERP编码</th>
			<th>物料名称</th>
			<th style="width:50px">计量单位</th>
			<th style="width:80px">数量</th>
			<th style="width:50px">单价</th>
			<th style="width:70px">总价</th>
			<th style="width:1px"></th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="detail" items="${detail}" varStatus='status' >	
				<tr>
					<td></td>
					<td>${ detail.materialId }<form:hidden path="detailList[${status.index}].materialid" value="${detail.materialId}" /></td>								
					<td><span id="name${status.index}"></span></td>					
					<td>${ detail.unit }</td>
					<td><form:input path="detailList[${status.index}].quantity" value="${detail.quantity}" class="num short"/></td>							
					<td><form:input  path="detailList[${status.index}].price" value="${detail.price}"  class="cash short" /></td>
					<td><span>${ detail.totalPrice}</span><form:hidden  path="detailList[${status.index}].totalprice" value="${detail.totalPrice} "/></td>				
					<td>					  <form:hidden path="detailList[${status.index}].recordid" value="${detail.recordId}" /></td>				
					
				</tr>	
								
				<script type="text/javascript">
					var materialName = '${detail.materialName}';
					var index = '${status.index}';
					
					$('#name'+index).html(jQuery.fixedWidth(materialName,20));
					
					counter++;
					
				</script>	
					
			</c:forEach>
			
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td class="td-right">合计:</td>
				<td class="td-right" style="padding-right: 2px;"><span id=weightsum></span>
					<form:hidden path="contract.total"/></td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	</div>
	</fieldset>
	<fieldset>
	<legend> 合同注意事项</legend>
	<table class="form" >
		<tr>
			<td class="td-left"><textarea name="contract.memo" rows="7" cols="100" >${contract.memo}</textarea></td>
		</tr>
	</table>
	
	</fieldset>
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="insert" class="DTTT_button">保存</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>		
		
</form:form>

</div>
</div>
</body>

	
</html>
