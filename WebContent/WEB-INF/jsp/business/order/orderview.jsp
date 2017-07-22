<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE HTML>
<html>

<head>
<title>订单管理-订单查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
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

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : false,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : "160px",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
	        "searching"  : false,
	       	"language": {"url":"${ctx}/plugins/datatables/chinese.json"},
	       	
			"columns" : [
			             {},
			             {}, 
			             {}, 
			             {"className" : "dt-body-center"}, 
			             {"className" : 'td-right'}, 
			             {"className" : 'td-right'}, 
			             {"className" : 'td-right'},
			             {"className" : 'td-right'},
			             {"className" : "dt-body-center"},
			             {"className" : "dt-body-center"}
						],
			
			"columnDefs":[
				
			  { "targets":2,"render":function(data, type, row){
	    			var name = row[2];	    			
	    			name = jQuery.fixedWidth(name,30);		    			
	    			return name;
	    	  }},
	    	  { "targets":8,"render":function(data, type, row){
	    			var PIId = '${order.PIId}';
	    			var rtn = "<a href=\"#\" onClick=\"ShowProductDesign('" + PIId + "','" + row[0] + "','" + row[8] + "')\">做单资料</a>";
	    			return rtn;
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

	};

	
	$(document).ready(function() {

		//设置光标项目
		$("#attribute1").attr('readonly', "true");
		$("#attribute2").attr('readonly', "true");
		$("#attribute3").attr('readonly', "true");
		
		ajax();

		//$('#example').DataTable().columns.adjust().draw();
		
		$("#return").click(
				function() {
					var url = "${ctx}/business/order?keyBackup=${order.PIId}";
					location.href = url;		
				});
		
		$("#edit").click(
				function() {

			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=edit");
			$('#orderForm').submit();
		});


	});

	function ShowBomPlan(YSId,materialId) {
		var url = '${ctx}/business/bom?methodtype=orderDetail&YSId=' + YSId+'&materialId='+materialId;
		location.href = url;
	};
	
	function ShowProductDesign(PIId,YSId,productId) {
 		var goBackFlag = '';
		var url = '${ctx}/business/productDesign?methodtype=addinit'
				+'&PIId=' + PIId
				+'&YSId=' + YSId
				+'&productId=' + productId
				+'&goBackFlag=' + goBackFlag;
		
		location.href = url;
	};
	
	function doShow(materialId) {

		var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;

		openLayer(url);
	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="orderForm" method="POST"
		id="orderForm" name="orderForm"  autocomplete="off">	
		
		<form:hidden path="order.piid" value="${order.PIId}"/>
		
		<fieldset>
			<legend> 订单综合信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" width="100px">PI编号：</td>					
					<td>${order.PIId}</td>

					<td width="100px" class="label" >客户订单号：</td>
					<td colspan="5">${order.orderId}</td>
				</tr>
				<tr>
					<td class="label">客户名称：</td>				
					<td colspan="3">${order.customerId}（${order.shortName}）${order.fullName}</td>
					<td class="label">下单公司：</td>				
					<td colspan="3">${order.orderCompanyName}</td>
						

				</tr>						
				<tr> 
					<td class="label">付款条件：</td>
					<td >出运后&nbsp;${order.paymentTerm}&nbsp;天</td>
						
					<td class="label">出运条件：</td>
					<td >${order.shippingCase}</td>
					
					<td class="label">出运港：</td>
					<td>${order.loadingPort}</td>

					<td class="label">目的港：</td>
					<td>${order.deliveryPort}</td>							
				</tr>			
				<tr>
					<td class="label">下单日期：</td>
					<td>${order.orderDate}</td>
					
					<td class="label">订单交期：</td>
					<td>${order.deliveryDate}</td>
					
					<td class="label">业务组：</td>
					<td>${order.team}</td>
					
					<td class="label">销售总价：</td>
					<td>${order.total}</td>
												
				</tr>							
			</table>
			

	</fieldset>
	
	<fieldset>
		<legend> 订单详情</legend>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display" style="width:100%">
			<thead>				
			<tr>
				<th class="dt-center" width="65px">耀升编号</th>
				<th class="dt-center" width="120px">产品编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" width="60px">版本类别</th>
				<th class="dt-center" width="60px">销售数量</th>
				<th class="dt-center" width="60px">生产数量</th>
				<th class="dt-center" width="50px">销售单价</th>
				<th class="dt-center" width="80px">销售总价</th>
				<th class="dt-center" width="30px">操作</th>
				<th class="dt-center" width="30px"></th>
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
					<th></th>
					<th></th>
				</tr>
			</tfoot>
		<tbody>
			<c:forEach var='order' items='${detail}' varStatus='status'>	
				<tr>
					<td>${order.YSId}</td>
					<td><a href="###" onClick="doShow('${order.materialId}')">${order.materialId}</a></td>								
					<td>${order.materialName}</td>
					<td>${order.productClassifyName}</td>
					<td class="cash" style="padding-right: 20px;">${order.quantity}</td>	
					<td class="cash" style="padding-right: 20px;">${order.totalQuantity}</td>						
					<td class="cash" style="padding-right: 20px;">${order.price}</td>
					<td class="cash" style="padding-right: 20px;">${order.totalPrice}</td>
					<td>${order.materialId}</td>
					<td><a href="###" onClick="ShowBomPlan('${order.YSId}','${order.materialId}')">订单详情</a></td>												
				</tr>
					
			</c:forEach>
			
		</tbody>
	</table>
	</div>
	</fieldset>
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="return" class="DTTT_button">返回</button>
		<button type="button" id="edit" class="DTTT_button">编辑</button>
	</fieldset>		
</form:form>		

</div>
</div>
</body>

	
</html>
