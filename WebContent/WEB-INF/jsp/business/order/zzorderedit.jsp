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
<title>自制品库存订单-编辑</title>

<script type="text/javascript">

	var counter  = 0;

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

	$.fn.dataTable.TableTools.buttons.add_rows = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = counter;
				
				for (var i=0;i<1;i++){
					
				var rowNode = $('#example')
						.DataTable()
						.row
						.add(
						  [
							'<td></td>',
							'<td><input 			 type="text"   name="orderDetailLines['+rowIndex+'].materialid"  id="orderDetailLines'+rowIndex+'.materialid" class="attributeList1" /></td>',
							'<td><span></span></td>',
							'<td><input 			 type="text"   name="orderDetailLines['+rowIndex+'].quantity"    id="orderDetailLines'+rowIndex+'.quantity" class="cash mini" /></td>',							
							'<td><span></span><input type="hidden" name="orderDetailLines['+rowIndex+'].price"       id="orderDetailLines'+rowIndex+'.price"" /></td>',				
							'<td><span></span><input type="hidden" name="orderDetailLines['+rowIndex+'].totalprice"  id="orderDetailLines'+rowIndex+'.totalprice" /></td>',
							'<td><input 			 type="hidden" name="orderDetailLines['+rowIndex+'].description" id="orderDetailLines'+rowIndex+'.description"  /></td>',	
							]).draw();
					
					rowIndex ++;						
				}					
				counter += 1;
				
				autocomplete();

				//重设显示窗口(iframe)高度
				iFramAutoSroll();
					
				foucsInit();
				
			}
		});

	$.fn.dataTable.TableTools.buttons.reset = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {
			
			var t=$('#example').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
				
			}else{
				
				t.row('.selected').remove().draw();
	
				//随时计算该客户的销售总价
				weightsum();
				
				$().toastmessage('showNoticeToast', "删除成功。");	
	
				//重设显示窗口(iframe)高度
				iFramAutoSroll();
			}						
		}
	});
	


function ajax() {

	var t = $('#example').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
        "ordering"  : false,

		dom : 'T<"clear">lt',

		"tableTools" : {

			"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

			"aButtons" : [ {
				"sExtends" : "add_rows",
				"sButtonText" : "追加新行"
			},
			{
				"sExtends" : "reset",
				"sButtonText" : "清空一行"
			}],
		},
		
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
        /*
		var $owastagei  = $tds.eq(4).find("input:hidden");
		var $oweights   = $tds.eq(5).find("span");//用料重量
		var $oweighti   = $tds.eq(5).find("input:hidden");
		var $omatprices = $tds.eq(7).find("span");//单位材料价
		var $omatpricei = $tds.eq(7).find("input:hidden");
		*/
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
		// alert(rowIndex);

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

	autocomplete();

	$( "#tabs" ).tabs();

	$(".DTTT_container").css('margin-top',' -24px');	
	
	//设置光标项目
	var today = shortToday();
	$("#order\\.orderid").attr('readonly', "true");
	$("#order\\.orderdate").val(today);
	
	$("#order\\.orderdate").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
	}); 

	$("#order\\.deliverydate").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
	});

	
	$("#goBack").click(
		function() {
			history.go(-1);			
		});
	
	$("#submitReturn").click(
			function() {
				if(inputCheck()){
					doSubmitReturn();
				}else{
					$().toastmessage('showWarningToast', "请填写完整后,再保存。");
				}
				

		});

	foucsInit();
	
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
		
	$('#ZZOrder').attr("action", "${ctx}/business/zzorder?methodtype=update");
	$('#ZZOrder').submit();
}

function doSubmitRefresh(){
			
	var actionUrl='${ctx}/business/material?methodtype=insertRefresh';

	$.ajax({
		type : "POST",
		//contentType : 'application/json',
		dataType : 'json',
		async:false,
		url : actionUrl,
		data : $('#material').serialize(),
		success : function(data) {
			
			if(data.message!=""){
				alert(data.message);
				window.location.reload();
			}
		},		
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert(XMLHttpRequest.status);					
			//alert(XMLHttpRequest.readyState);					
			//alert(textStatus);					
			//alert(errorThrown);
			alert("发生系统异常，请再试或者联系系统管理员.");
		}
	});
	
}
</script>
</head>

<body class="panel-body">
<div id="container">
<div id="main">
	
<form:form modelAttribute="ZZOrder" method="POST" 
	id="ZZOrder" name="ZZOrder"   autocomplete="off">
		
	<form:hidden path="order.recordid" value="${order.orderRecordId }"/>
	
<fieldset>
	<legend>自制品库存订单</legend>

	<table id="baseinfo" class="form" width="100%">		
		<tr>
			<td class="label" style="width: 100px;">耀升编码：</td>
			<td style="width: 120px;">
				<form:input path="order.orderid" class="required read-only" value="${order.YSId }"/></td>
								
			<td class="label" style="width: 100px;"><label>下单日期：</label></td>
			<td ><form:input path="order.orderdate" class="required" value="${order.orderDate }" /></td>
			<td class="label" style="width: 100px;"><label>完成日期：</label></td>
			<td><form:input path="order.deliverydate" class="required" value="${order.deliveryDate }" /></td>
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
					<td><form:input path="orderDetailLines[${status.index}].materialid" class="attributeList1" value="${detail.materialId}" /></td>								
					<td><span id="name${status.index}"></span></td>
					<td><form:input path="orderDetailLines[${status.index}].quantity" class="num mini"  value="${detail.quantity}"/></td>							
					<td><span>${detail.price}</span><form:hidden path="orderDetailLines[${status.index}].price" value="${detail.price}" /></td>
					<td><span>${detail.totalprice}</span>
						<form:hidden path="orderDetailLines[${status.index}].totalprice" value="${detail.totalprice}" /></td>				
					<td><input type="hidden" id="orderDetailLines[${status.index}].description" value="${detail.description}" /></td>
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
				<td>合计:</td>
				<td class="td-right" >
					<span id="weightsum">${order.total }</span>
					<form:hidden path="order.totalprice" value="${order.total }" /></td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	</div>
	</div>
	</fieldset>
	<fieldset>
	<legend> 库存制作原因</legend>
	<table class="form" >

		<tr>
			<td class="td-left"><textarea name="order.yskordertarget" rows="7" cols="70" >${order.YSKorderTarget}</textarea></td>
		</tr>
	</table>
	
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">	
		<button type="button" id="submitReturn" class="DTTT_button">保存</button>				
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	
</form:form>
</div>
</div>

<script type="text/javascript">

function autocomplete(){
		
	//原材料选择
	$(".attributeList1").autocomplete({
		minLength : 2,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/zzorder?methodtype=getMaterialList",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.viewList,
									value : item.materialId,
									id : item.materialId,
									name : item.materialName,
									desc : item.description,
									price: item.totalPrice
								}
							}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},

		select : function(event, ui) {
			
			var $tds = $(this).parent().parent().find('td');

			var $oname   = $tds.eq(2).find("span");
			var $opricei = $tds.eq(4).find("input");
			var $oprices = $tds.eq(4).find("span");
			var $odesc   = $tds.eq(6).find("input:hidden");
			
			$odesc.val(ui.item.desc);
			$opricei.val(ui.item.price);
			$oprices.html(ui.item.price);
			$oname.html(jQuery.fixedWidth(ui.item.name,25));
			
			$("#productDetail").html('<pre>'+ui.item.desc+ '</pre>');
		},
		
	});
}

</script>
</body>
</html>
