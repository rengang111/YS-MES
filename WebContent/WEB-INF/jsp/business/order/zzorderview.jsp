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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>自制品库存订单-查看</title>

<script type="text/javascript">

function ajax() {

	var t = $('#example').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
        "ordering"  : false,

		dom : '<"clear">lt',
		
		"columns" : [ 
		        	{"className":"dt-body-center"
				}, {
				}, {											
				}, {"className":"dt-body-right"				
				}, {"className":"dt-body-right"				
				}, {"className":"dt-body-right"				
				}, {				
				}			
			]
		
	})

	
	t.on('blur', 'tr td:nth-child(1),tr td:nth-child(4)',function() {
		
       $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

	});
	
	t.on('change', 'tr td:nth-child(4)',function() {
		
        var $tds = $(this).parent().find("td");

        var $oquantity = $tds.eq(3).find("input");//数量
		var $oprice    = $tds.eq(4).find("input");//单价
        var $ototalnew = $tds.eq(5).find("span");//总价
        var $ototalold = $tds.eq(5).find("input");//总价

		var fquantity = currencyToFloat($oquantity.val());		
		var fprice    = currencyToFloat($oprice.val());		
		var totalold  = currencyToFloat($ototalold.val());
		
		var ftotalnew = fquantity * fprice;//数量 * 单价
			
		//alert(fquantity+"----"+fprice+":::"+ftotalnew)
		var vtotal  = floatToCurrency(ftotalnew);
		
		//详情列表显示新的价格
		$ototalnew.html(vtotal);	
		$ototalold.val(vtotal);	
		
		weightsum();

		
		//判断原材料是否选择
        var $omaterial  = $tds.eq(1).find("input");//自制品
		var materialid = $omaterial.val();
		// alert(materialid)
        if(materialid == "")
        	$omaterial.addClass("required");
		
	});
		
	t.on('click', 'tr', function() {
		
		// var rowIndex = $(this).context._DT_RowIndex; //行号	

        var $tds = $(this).find("td");
        var desc = $tds.eq(6).find("input").val();
        //alert(rowIndex+'::::'+desc)
		$("#productDetail").html('<pre>'+desc+ '</pre>');
		
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

	$( "#tabs" ).tabs();
	
	//设置光标项目
	var today = shortToday();
	$("#order\\.orderid").attr('readonly', "true");
	
	$("#goBack").click(
		function() {
			url = "${ctx}/business/order";
			location.href = url;		
		});
	
	$("#doEdit").click(
		function() {
			var YSId = '${order.YSId}';
			url = "${ctx}/business/zzorder?methodtype=edit&YSId="+YSId;
			location.href = url;
		});	
	
	$("#doApprove").click(
		function() {
			var YSId = '${order.YSId}';
			url = "${ctx}/business/zzorder?methodtype=approve&YSId="+YSId;
			location.href = url;
		});
	
	$("#doDemand").click(
			function() {
				var YSId = '${order.YSId}';
				url = "${ctx}/business/requirement?methodtype=edit&YSId="+YSId;
				location.href = url;
			});
	
	
});

//列合计
function weightsum(){

	var sum = 0;
	$('#example tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(5).find("span").text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;
		
	})
	$('#weightsum').text(floatToCurrency(sum));
	$('#order\\.totalprice').val(floatToCurrency(sum));

}

function doSubmitReturn(){
		
	url = "${ctx}/business/zzorder?methodtype=insert";
	location.href = url;
}

</script>
</head>

<body class="easyui-layout">
<div id="container">
<div id="main">
	
<fieldset>
	<legend>自制品库存订单</legend>

	<table id="baseinfo" class="form" width="100%">		
		<tr>
			<td class="label" style="width: 100px;">耀升编码：</td>
			<td style="width: 120px;">${order.YSId }
				<input type="hidden" id="orderid" value="${order.YSId }"/></td>
								
			<td class="label" style="width: 100px;"><label>下单日期：</label></td>
			<td >${order.orderDate }</td>
			<td class="label" style="width: 100px;"><label>完成日期：</label></td>
			<td>${order.deliveryDate }</td>
			<td class="label" style="width: 100px;"><label>订单总金额：</label></td>
			<td>${order.total }</td>
	</table>
	</fieldset>
	<div style="clear: both"></div>		
	<fieldset>
	<legend> 产品详情</legend>
	<div id="tabs" style="margin: -6px 0px 0px 5px; float: right; padding: 0px;">
		<ul>
			<li><a href="#tabs-1" style="font-size: 11px;">描述</a></li>
			<li><a href="#tabs-2" style="font-size: 11px;">图片</a></li>
		</ul>

		<div id="tabs-2" style="padding: 5px;">
			<img id="productImage"
				src="${ctx}/css/images/blankDemo.png"
				style="width: 280px; height: 210px;" />
		</div>

		<div id="tabs-1" style="padding: 5px;">
			<div id="productDetail" style="width: 280px; height: 210px;"></div>
		</div>

	</div>
	<div id="floatTable" style="width: 70%; float: left; margin: 5px 0px 0px 0px;">
	
	<div class="list">
	<table id="example" class="display" width="100%">	
		<thead>
		<tr>
			<th style="width:20px">No</th>
			<th style="width:120px">产品编码(带子编码)</th>
			<th>产品名称</th>
			<th style="width:80px">数量</th>
			<th style="width:50px">制作单价</th>
			<th style="width:70px">制作总价</th>
			<th style="width:1px"></th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="detail" items="${detail}" varStatus='status' >	
				<tr>
					<td></td>
					<td>${detail.materialId}</td>								
					<td><span id="name${status.index}"></span></td>
					<td>${detail.quantity}</td>							
					<td>${detail.price}</td>
					<td>${detail.totalprice}</td>				
					<td><input type="hidden" id="orderDetailLines[${i}].description"  value="${detail.description}" /></td>
				</tr>					
				<script type="text/javascript">
					var materialName = '${detail.materialName}';
					var index = '${status.index}';
					$('#name'+index).html(jQuery.fixedWidth(materialName,20));
				</script>	
							
			</c:forEach>			
		</tbody>
	</table>
	</div>
	</div>
	</fieldset>
	<fieldset>
	<legend> 库存制作原因</legend>
		<table class="form" >
			<tr>
				<td class="td-left"><pre>${order.YSKorderTarget }</pre></td>
			</tr>
		</table>	
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="doEdit" class="DTTT_button">编辑</button>	
		<button type="button" id="doApprove" class="DTTT_button">审批</button>			
		<button type="button" id="doDemand" class="DTTT_button">生成物料需求表</button>				
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	
</div>
</div>
</body>
</html>
