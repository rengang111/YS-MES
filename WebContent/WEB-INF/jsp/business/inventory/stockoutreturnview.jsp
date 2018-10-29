<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>领料退还-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
		
	var counter = 0;
	var moduleNum = 0;
	
	
	
	function ajaxRawGroup() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 500,
	        "ordering"  : false,
	       	"dom"		  : '<"clear">rt',			
			"columns" : [ 
			           {"className":"dt-body-center"
					}, {"className":"td-left"
					}, {							
					}, {"className":"td-center"		
					}, {"className":"td-right"				
					}			
			],				
			
		}).draw();
		
		t.on('blur', 'tr td:nth-child(6)',function() {
			
	           $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
			
		t.on('change', 'tr td:nth-child(6)',function() {
			
            var $tds = $(this).parent().find("td");
			
            var $oQuantity  = $tds.eq(5).find("input");
			var $oThisPrice = $tds.eq(6).find("input");
			var $oAmounti   = $tds.eq(7).find("input:hidden");
			var $oAmounts   = $tds.eq(7).find("span");
			
			var fPrice    = currencyToFloat($oThisPrice.val());		
			var fQuantity = currencyToFloat($oQuantity.val());			
			var fTotalOld = currencyToFloat($oAmounti.val());
			
			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vQuantity = floatToNumber(fQuantity);
			var vTotalNew = floatToCurrency(fTotalNew);
					
			//详情列表显示新的价格					
			$oQuantity.val(vQuantity);	
			$oAmounti.val(vTotalNew);	
			$oAmounts.html(vTotalNew);	

			
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

		
		$("#requisition\\.requisitiondate").val(shortToday());		
		$("#requisition\\.requisitiondate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 
		
		ajaxRawGroup();	
		
		$("#goBack").click(
				function() {
					var url = "${ctx}/business/requisition?methodtype=stockoutReturnInit";					
					location.href = url;		
				});
		
		$("#doEdit").click(function() {
			
		
			$('#formModel').attr("action",
					"${ctx}/business/requisition?methodtype=stockoutReturnInsert");
			//$('#formModel').submit();
		});

		$("#doDelete").click(function() {
			
			var ysid = $('#requisitionId').val();
			if(confirm('删除后不能恢复，确定要删除吗？')){
				var url = "${ctx}/business/requisition?methodtype=stockoutReturnDelete"+"&requisitionId="+requisitionId;
				location.href = url;
			}
		});
	});	
	
	
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">
			
		<input type="hidden" value="" id="requisitionId"  value="${detail.requisitionId }">
		<fieldset>
			<legend> 领料退货</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" width="100px">耀升编号：</td>					
					<td width="150px">${detail.YSId }</td>
					
					<td class="label" width="100px">领料单编号：</td>					
					<td width="150px">${detail.requisitionId }</td>
															
					<td width="100px" class="label">申请日期：</td>
					<td width="100px">${detail.requisitionDate }</td>
					
					<td width="100px" class="label">申请人：</td>
					<td>${detail.requisitionUserId }</td>
				</tr>
				<tr>
					<td class="label">订单数量：</td>					
					<td><span id="quantity">${detail.orderQty }</span></td>
								
					<td class="label"width="100px">产品编号：</td>					
					<td><span id="materialId">${detail.productId }</span></td>
								
					<td class="label" width="100px">产品名称：</td>					
					<td colspan="3"><span id="materialName">${detail.productName }</span></td>
				</tr>
				<tr> 				
					<td class="label" width="100px">退还原由：</td>					
					<td colspan="7">${detail.remarks }</td>
										
				</tr>	
			</table>
			
		</fieldset>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doEdit"   class="DTTT_button">编辑</button>
		<button type="button" id="doDelete" class="DTTT_button">删除</button>
		<button type="button" id="goBack"   class="DTTT_button">返回</button>
	</fieldset>
	<fieldset style="margin-top: -40px;">
		<legend> 物料详情</legend>
		
		<div class="list">
			<table id="example" class="display">	
				<thead>
					<tr>
						<th style="width:30px">No</th>
						<th style="width:200px">物料编码</th>
						<th>物料名称</th>
						<th style="width:80px">单位</th>
						<th style="width:150px">退还数量</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var='order' items='${detailList}' varStatus='status'>
						<tr>
							<td>${status.index + 1 }</td>
							<td>${order.materialId }</td>								
							<td>${order.materialName }</td>					
							<td>${order.unit }</td>
							<td>${order.quantity }</td>
						</tr>
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
