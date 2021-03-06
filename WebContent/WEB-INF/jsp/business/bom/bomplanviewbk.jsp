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
<title>BOM方案-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter = 0;
	var totalPrice = "0";
	var laborCost = "0"
	
	//Form序列化后转为AJAX可提交的JSON格式。
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	
	
	
	function ajax() {
		var height = $(window).height();
		height = height - 370;

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"serverSide" : false,
	        "paging"    : false,
	        "ordering"  : false,
	        "searching"  : false,
	       	"language": {"url":"${ctx}/plugins/datatables/chinese.json"},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {"className":"dt-body-right"				
					}, {"className":"dt-body-right"				
					}, {"className":"dt-body-right"				
					}, {"className":"dt-body-right"				
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

	};//ajax()

	$(document).ready(function() {
		
		ajax();
	
		$("#return").click(function() {
					var url = '${ctx}/business/order';
					location.href = url;		
				});
		
		$("#doEdit").click(function() {
			var bomId = $('#bomid').val();
			$('#bomForm').attr("action", "${ctx}/business/bom?methodtype=edit&bomId="+bomId);
			$('#bomForm').submit();
		});
		

		$("#doCopy").click(function() {
			var bomId = $('#bomid').val();
			var materialId = $('#materialid').val();
			var url = '${ctx}/business/bom?methodtype=copy&bomId=' + bomId+'&materialId='+materialId;
			location.href = url;
		});
		
		//重设显示窗口(iframe)高度
		iFramAutoSroll();		

	});	

	function doCopy() {

		location.href = url;
	}

	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="bomForm" method="POST"
		id="bomForm" name="bomForm"  autocomplete="off">
		
		<input type="hidden" id="tmpMaterialId" />	
		<input type="hidden" id="bomid" value="${bomPlan.bomId}"/>
		<input type="hidden" id="materialid" value="${bomPlan.productId}"/>			
		
		<fieldset>
			<legend> BOM方案</legend>
			<table class="form" id="table_form" width="100%" style="margin-top: -4px;">
				<tr> 				
					<td class="label" width="100px"><label>BOM编号：</label></td>					
					<td width="150px">${bomPlan.bomId}</td>
						
					<td class="label" width="100px"><label>耀升名称：</label></td>					
					<td width="250px">${bomPlan.YSId }</td>
					<td class="label" width="100px"><label>方案日期：</label></td>					
					<td>${bomPlan.planDate }</td>
				</tr>
				<tr>
					<td class="label"><label>产品编号：</label></td>				
					<td>${bomPlan.productId }</td>

					<td class="label"><label>产品名称：</label></td>
					<td>${bomPlan.productName }</td>

					<td class="label"><label>订单数量：</label></td>
					<td>${bomPlan.productQty }</td>
				</tr>								
			</table>
			
			<table class="form" id="table_form2" width="100%" style="margin-top: 6px;">
				
				<tr>
					<td class="td-center"><label>材料成本</label></td>	
					<td class="td-center"><label>人工</label></td>
					<td class="td-center" width="150px"><label>经管费率</label></td>
					<td class="td-center" ><label>经管费</label></td>	
					<td class="td-center"><label>产品成本</label></td>
					<td class="td-center"><label>核算成本</label></td>
				</tr>	
				<tr>			
					<td class="td-center">${bomPlan.materialCost }</td>
					<td class="td-center">${bomPlan.laborCost }</td>
					<td class="td-center">${bomPlan.managementCostRate }</td>
					<td class="td-center">${bomPlan.managementCost }</td>
					<td class="td-center">${bomPlan.productCost }</td>
					<td class="td-center">${bomPlan.totalCost }</td>
				</tr>								
			</table>
	
		<div style="text-align: right;margin-top: 10px;">	
		<button type="button" id="return" class="DTTT_button">返回</button>
		<button type="button" id="doEdit" class="DTTT_button">编辑</button>
		<!-- button type="button" id="doCopy" class="DTTT_button">复制</button> -->
		</div>
	</fieldset>		
	<fieldset>
		<legend> BOM详情</legend>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display dataTable" width="100%">
			<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="100px">ERP编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" width="120px">供应商</th>
				<th class="dt-center" width="60px">用量</th>
				<th class="dt-center" width="60px">本次单价</th>
				<th class="dt-center" width="80px">总价</th>
				<th class="dt-center" width="60px">历史最低</th>
			</tr>
			</thead>
			<tfoot>
				<tr>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</tfoot>
		<tbody>	
						
		<c:forEach var="detail" items="${bomDetail}" varStatus='status' >		
			
			<tr>				
				<td></td>
				<td>${detail.materialId}</td>								
				<td>${detail.materialName}</td>	
				<td>${detail.supplierId}</td>
				<td>${detail.quantity}</td>							
				<td>${detail.price}</td>						
				<td>${detail.totalPrice}</td>	
				<td>${detail.minPrice}</td>	
				
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
