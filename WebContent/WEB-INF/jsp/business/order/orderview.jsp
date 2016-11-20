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
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
			//"scrollY"    : "160px",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
	        "searching"  : false,
	       	"language": {"url":"${ctx}/plugins/datatables/chinese.json"},
	       	
			"columns" : [{"className":"dt-body-center"}, {}, {}, {}, {}, {}, {},
			             {"className":"dt-body-center"},
			             {"className":"dt-body-center"}, {}
						],
			
			"columnDefs":[
				{
					"visible" : false,
					"targets" : [ 9 ]
				},
			  { "targets":3,"render":function(data, type, row){
	    			var fullName = row[3];
	    			var shortName = '';
	    			
	    			if(fullName.length > 20){	
	    				var shortName =  '<div title="' + fullName + '">' + fullName.substr(0,20)+ '...</div>';
	    			}else{	
	    				var shortName = fullName;
	    			}	    			
	    			return shortName;
	    	  }},
	    	  { "targets":7,"render":function(data, type, row){
	    			var rtn = "";
	    			rtn= "<a href=\"#\" onClick=\"orderReview2('" + row[1] +"','"+ row[2] + "')\">做单资料</a>";
	    			return rtn;
	    	  }},
	    	  { "targets":8,"render":function(data, type, row){
	    			var rtn = "";
	    			var space = '&nbsp;';
	    			var status = row[9];
	    			if(status != 1){//1:BOM表已做成
	    				rtn = rtn+ space + "<a href=\"#\" onClick=\"doCopy('" + row[1] +"','"+ row[2] + "')\">复制</a>";
		    			rtn = rtn+ space + "<a href=\"#\" onClick=\"AddBomPlan('" + row[1] +"','"+ row[2] + "')\">新建</a>";
		   				
	    			}else{
	    				rtn = "<div title='BOM表已做成'>"+space+space+space+space+"</div>"
	    			}
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
		$("#attribute1").attr('readonly', "true");
		$("#attribute2").attr('readonly', "true");
		$("#attribute3").attr('readonly', "true");
		
		ajax();

		$('#example').DataTable().columns.adjust().draw();
		
		$("#return").click(
				function() {
					var url = "${ctx}/business/order";
					location.href = url;		
				});
		
		$("#edit").click(
				function() {

			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=edit");
			$('#orderForm').submit();
		});

		//重设显示窗口(iframe)高度
		iFramAutoSroll();

	});
	
	function AddBomPlan(YSId,materialId) {

		var url = '${ctx}/business/bom?methodtype=create&YSId=' + YSId+'&materialId='+materialId;

		location.href = url;
	}
	

	function doCopy(YSId,materialId) {
		var url = '${ctx}/business/bom?methodtype=chooseSourseBom&YSId=' + YSId+'&materialId='+materialId;
		location.href = url;
	};
	
	
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
			<table class="form" id="table_form" width="100%" style="margin-top: -4px;">
				<tr> 				
					<td class="label" width="100px"><label>PI编号：</label></td>					
					<td colspan="7">${order.PIId}</td>
				</tr>
				<tr>
					<td class="label"><label>客户编号：</label></td>				
					<td colspan="7">${order.customerId}</td>
				</tr>
				<tr>
					<td class="label"><label>客户简称：</label></td>
					<td>${order.shortName}</td>

					<td class="label"><label>客户全称：</label></td>
					<td colspan="3">${order.fullName}</td>
						
					<td class="label"><label>币种：</label></td>
					<td>${order.currency}</td>
				</tr>					
				<tr> 
					<td class="label"><label>付款条件：</label></td>
					<td >出运后&nbsp;${order.paymentTerm}&nbsp;天</td>
						
					<td width="100px"  class="label">
						<label >出运条件：</label></td>
					<td >${order.shippingCase}</td>
					
					<td class="label"><label>出运港：</label></td>
					<td>${order.loadingPort}</td>

					<td class="label"><label>目的港：</label></td>
					<td>${order.deliveryPort}</td>							
				</tr>			
				<tr>
					<td width="100px" class="label" >
						<label >客户订单号：</label></td>
					<td>${order.orderId}</td>

					<td class="label">
						<label>下单日期：</label></td>
					<td>${order.orderDate}</td>
					
					<td class="label">
						<label  >订单交期：</label></td>
					<td>${order.deliveryDate}</td>
					
					<td class="label">
						<label>销售总价：</label></td>
					<td>${order.total}</td>
												
				</tr>							
			</table>
			

	</fieldset>
	
	<fieldset>
		<legend> 订单详情</legend>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display" >
			<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="60px">耀升编号</th>
				<th class="dt-center" width="120px">产品编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" width="80px">销售数量</th>
				<th class="dt-center" width="50px">销售单价</th>
				<th class="dt-center" width="100px">销售总价</th>
				<th class="dt-center" width="60px">操作</th>
				<th class="dt-center" width="60px">BOM表</th>
				<th class="dt-center" width="1px"></th>
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
					<td></td>
					<td>${order.YSId}</td>
					<td>${order.materialId}</td>								
					<td>${order.materialName}</td>
					<td class="cash" style="padding-right: 20px;">${order.quantity}</td>							
					<td class="cash" style="padding-right: 20px;">${order.price}</td>
					<td class="cash" style="padding-right: 20px;">${order.totalPrice}</td>
					<td></td>
					<td></td>
					<td>${order.status}</td>													
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
