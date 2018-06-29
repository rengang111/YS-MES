<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>订单管理-订单转义录入</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var options = "";//币种可选项
	var counter = 0;
	var YSParentId = "";
	var YSSwift = "";
	var PieYSId = ""
	var YSSwiftPeiIndex = "0";
	var totalPrice = "0";
	var shortYear = ""; 
	var ExFlagPI = '';//PI编号重复check
	var ExFlagYS = '';//ys编号重复check
	var ysidList = new Array();
	var ysidPeiList = new Array();

	YSSwift = '${orderForm.YSMaxId}';
	YSParentId = '${orderForm.YSParentId}';
	
	
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
			        	{
					}, {
					}, {
					}, {				
					}, {"className":"dt-body-center"
					}, {				
					}, {"className":"dt-body-right"				
					}			
				]
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {

			var $td = $(this).parent().find("td");

			var $oQuantity = $td.eq(4).find("input");
			var $oPricei  = $td.eq(5).find("input:text");
			var $oAmount  = $td.eq(6).find("input");
			var $oAmounts = $td.eq(6).find("span");
			
			var currency = $('#currency option:checked').text();// 选中项目的显示值

			var fPrice = currencyToFloat($oPricei.val());	

			var fQuantity = currencyToFloat($oQuantity.val());

			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vPricei = floatToSymbol(fPrice,currency);
			var vQuantity = floatToNumber($oQuantity.val());
			var vTotalNew = floatToSymbol(fTotalNew,currency);
			
			//详情列表显示新的价格
			$oPricei.val(vPricei);
			//$oPriceh.val(vPriceh);
			$oQuantity.val(vQuantity);
			$oAmount.val(vTotalNew);
			$oAmounts.html(vTotalNew);

			//临时计算该客户的销售总价
			totalPrice = floatToSymbol( saleTotalSum(),currency);
			$('#order\\.totalprice').val(totalPrice);
			$('#orderDetail\\.totalquantity').val(vQuantity);

		});

		


	};//ajax()

	//列合计:总价
	function productCostSum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(6).find("input").val();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})
		return sum;
	}
	
	$(document).ready(function() {

		var i = 0;	
		<c:forEach var="list" items="${orderForm.productClassifyList}">
			i++;
			options += '<option value="${list.key}">${list.value}</option>';
		</c:forEach>
		
		//设置光标项目
		$("#attribute1").focus();

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#order\\.orderdate").val(shortToday());
		
		ajax();//正常订单

		
		//$('#example').DataTable().columns.adjust().draw();
		
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
		
		
		$("#return").click(
				function() {
					var url = "${ctx}/business/order";
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					var piid = $("#order\\.piid").val();
			if(piid == ""){
				$().toastmessage('showWarningToast', "PI编号不能为空,请重新输入。");		
				return;
			}
			if( ExFlagPI == 1){
				$().toastmessage('showWarningToast', "PI编号已存在,请重新输入。");		
				return;
			}
			if(ExFlagYS == 1){
				$().toastmessage('showWarningToast', "耀升编号有重复,请重新输入。");		
				return;
			}
			var str = '';
			$(".attributeList1").each(function(){			
				str += $(this).val();				
			});			
			if(str.length<=0){
				$().toastmessage('showWarningToast', "请输入产品信息。");		
				return;				
			}
			
			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=insertTransfer");
			$('#orderForm').submit();
		});
		
		$("#currency").change(function() {

			$().toastmessage('showWarningToast', "货币符号发生变化,请重新输入销售单价。");	
		});
		
		
		$("#transferQty").change(function() {

			var quantity = $(this).val();
			var transferQty = currencyToFloat(quantity);
			
			//转移源计算
			var oldQty = $("#orderTransfer\\.quantity").val();
			var price = $("#orderTransfer\\.price").val();
			oldQty = currencyToFloat(oldQty);
			price = currencyToFloat(price);
			newQty = oldQty - transferQty;			
			var total = newQty * price;
			total = floatToCurrency(total);
			
			$("#quantity").text(newQty);
			$('#orderTransfer\\.quantity').val(newQty);
			$("#totalprice").text(total);
			$('#orderTransfer\\.totalprice').val(total);
	
			//新订单计算
			var currency = $('#currency option:checked').text();// 选中项目的显示值
			var fPrice = $('#orderDetail\\.price').val();//单价
			fPrice = currencyToFloat(fPrice);
			
			var fTotalNew = fPrice * transferQty;
			vTotalNew = floatToSymbol(fTotalNew,currency);

			//计算该客户的销售总价
			$('#order\\.totalprice').val(vTotalNew);
			$('#orderDetail\\.totalprice').val(vTotalNew);
			$('#totalprice2').html(vTotalNew);
			$('#orderDetail\\.quantity').val(transferQty);
			
		});
		
		$("#order\\.piid").change(function() {

			var piid = $(this).val().toUpperCase();
			//alert(parentId)
			var url = "${ctx}/business/order?methodtype=piidExistCheck&PIId="+piid
												
			if (piid != ""){ 
				$.ajax({
					type : "post",
					url : url,
					async : false,
					data : 'key=' + piid,
					dataType : "json",
					success : function(data) {
		
						ExFlagPI = data["ExFlag"];
						if(ExFlagPI == '1'){
							$().toastmessage('showWarningToast', "PI编号［ "+piid+" ］已存在,请重新输入。");				
						}

					},
					error : function(
							XMLHttpRequest,
							textStatus,
							errorThrown) {
						
						//alert("supplierId2222:"+textStatus);
					}
				});
			}else{
				//关联项目清空
			}
		});	//
		
		
		$(document).on("change", ".ysidCheck", function(){
			var YSId = $(this).val().toUpperCase();
			return ysidCheck(YSId);

		});	//耀升编号重复check
		
		foucsInit();
		
		$(".cash") .blur(function(){
			
			var currency = $('#currency option:checked').text();// 选中项目的显示值
			$(this).val(floatToSymbol($(this).val(),currency));
		});
			

		$("#orderDetail\\.quantity").removeClass('bgnone');
		autocomplete();
		
		
	});
	
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="orderForm" method="POST"
	id="orderForm" name="orderForm"  autocomplete="off">
	
	<form:hidden path="order.customerid" />
	<form:hidden path="order.parentid" />
	<form:hidden path="order.subid" />
	
	<fieldset>
		<legend> 订单综合信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">PI编号：</td>					
				<td width="150px">
					<form:input path="order.piid" class="short required read-only" /></td>
					
				<td class="label" width="100px">订单性质：</td>
				<td width="150px">
					<form:select path="order.ordernature">
						<form:options items="${orderForm.orderNatureList}" itemValue="key" itemLabel="value" />
					</form:select>	
						
				<td class="label" width="100px" >客户订单号：</td>
				<td colspan="3">
					<form:input path="order.orderid" class="short required" /></td>						
			</tr>
			<tr>
				<td class="label">客户编号：</td>				
				<td colspan="3">
						<form:input path="attribute1" class="short required" />
						<span style="color: blue">（查询范围：客户编号、客户简称、客户名称）</span></td>
				<td class="label">客户名称：</td>
				<td colspan="3">&nbsp;<span id="attribute2"></span></td>	
			</tr>				
			<tr> 
				<td class="label">付款条件：</td>
				<td >&nbsp;出运后<form:input path="order.paymentterm" 
						style="width: 30px;text-align: center;" class="td-center" />&nbsp;天</td>
					
				<td class="label">出运条件：</td>
				<td >
					<form:select path="order.shippingcase">
							<form:options items="${orderForm.shippingCaseList}" 
							  itemValue="key" itemLabel="value" />
					</form:select></td>
				
				<td class="label">出运港：</td>
				<td width="150px">
					<form:select path="order.loadingport">
						<form:options items="${orderForm.loadingPortList}"
						  itemValue="key" itemLabel="value" />
					</form:select></td>

				<td class="label" width="100px"><label>目的港：</label></td>
				<td><form:select path="order.deliveryport">
						<form:options items="${orderForm.deliveryPortList}" 
						 itemValue="key" itemLabel="value" />
					</form:select></td>							
			</tr>
			<tr>
				<td class="label">业务组：</td>
				<td><form:select path="order.team">
						<form:options items="${orderForm.teamList}" 
						 itemValue="key" itemLabel="value" />
					</form:select></td>
				<td class="label">下单日期：</td>
				<td>
					<form:input path="order.orderdate" class="short required" /></td>
				
				<td width="100px" class="label">订单交期：</td>
				<td>
					<form:input path="order.deliverydate" class="short required" /></td>
				<!-- 
				<td width="100px" class="label">退税率：</td>
				<td>
					<form:select path="rebateRate">
							<form:options items="${rebateRateList}" 
							  itemValue="key" itemLabel="value" />
					</form:select></td>	 -->						
			</tr>
			<tr>
				<td class="label">币种：</td>
				<td>
					<form:select path="currency">
						<form:options items="${orderForm.currencyList}" itemValue="key" itemLabel="value" />
					</form:select></td>	
				<td class="label">下单公司：</td>				
				<td colspan="3">
					<form:select path="order.ordercompany">
							<form:options items="${orderForm.ordercompanyList}" 
							  itemValue="key" itemLabel="value" />
					</form:select></td>	
				<td class="label">销售总价：</td>
				<td>
					<form:input path="order.totalprice" class="read-only cash" style="width:150px"/></td>											
			</tr>							
		</table>
</fieldset>

<fieldset class="action" style="text-align: right;margin: -15px 0 0px 0;">
	<button type="button" id="return" class="DTTT_button">返回</button>
	<button type="button" id="insert" class="DTTT_button">保存</button>
</fieldset>
<fieldset style="margin-top: -20px;">
	<legend> 订单转移</legend>
	<div class="list">
	
	<table id="example2" class="display" style="width: 100%;">
		<thead>			
			<tr>
				<th class="dt-center" width="60px">被转移的<br>耀升编号</th>
				<th class="dt-center" width="200px">产品编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" width="100px">版本类别</th>
				<th class="dt-center" width="100px">订单数量</th>
				<th class="dt-center" width="100px">转移数量</th>
				<th class="dt-center" width="120px">销售单价</th>
				<th class="dt-center" width="90px">销售总价</th>
			</tr>
		</thead>
		<tbody>
			<tr style="width:40px">
				<td><input type="text" name="orderTransfer.ysid" id="orderTransfer.ysid" class="short"  /></td>
				<td style="text-align: center;"><span id="targetId"></span></td>
				<td><span id="targetName"></span></td>
				<td style="text-align: center;"><span id="productClassify"></span></td>
				<td style="text-align: center;"><span id="quantity"></span><form:hidden path="orderTransfer.quantity" /></td>
				<td style="text-align: center;"><input  type="text" id="transferQty"  value="0" class="mini  num"/></td>
				<td style="text-align: center;"><span id="price"></span><form:hidden path="orderTransfer.price"  class="cash short"  /></td>
				<td style="text-align: center;"><span id="totalprice"></span><form:hidden path="orderTransfer.totalprice" /></td>				
			</tr>
		</tbody>
	</table>
	<div style="height:20px"></div>
	<table id="example" class="display" >
		<thead>
			<tr>
				<th class="dt-center" width="60px">新订单的<br>耀升编号</th>
				<th class="dt-center" width="60px">产品编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" width="100px">版本类别</th>
				<th class="dt-center" width="150px">订单数量</th>
				<th class="dt-center" width="60px">销售单价</th>
				<th class="dt-center" width="60px">销售总价</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="i" begin="0" end="0" step="1">		
			<tr style="width:40px">
				<td><form:input path="orderDetail.ysid" class="short read-only ysidCheck"  /></td>
				<td><input type="text" name="attributeList1" class="attributeList1">
					<form:hidden path="orderDetail.materialid" /></td>
				<td><span></span></td>
				<td>
					<form:select path="orderDetail.productclassify" >							
						<form:options items="${orderForm.productClassifyList}" 
							itemValue="key" itemLabel="value" /></form:select></td>
				<td><form:input path="orderDetail.quantity" class="num mini read-only"  /></td>
				<td><form:input path="orderDetail.price"  class="cash short"  /></td>
				<td><span id="totalprice2"></span><form:hidden path="orderDetail.totalprice" /></td>
				
				<form:hidden path="orderDetail.ordertype" value="010"/>
				<form:hidden path="orderDetail.totalquantity" />
			</tr>
				<script type="text/javascript" >
					YSSwift = parseInt(YSSwift)+ 1;
					var fmtId = YSParentId + PrefixInteger(YSSwift,4); 
					$("#orderDetail\\.ysid").val(fmtId);
					
					counter++;
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

$("#attribute1").autocomplete({

	source : function(request, response) {
		//alert(888);
		$.ajax({
			type : "POST",
			url : "${ctx}/business/order?methodtype=customerSearch",
			dataType : "json",
			data : {
				key : request.term
			},
			success : function(data) {
				//alert(777);
				response($.map(
					data.data,
					function(item) {
						//alert(item.viewList)
						return {
							label : item.viewList,
							value : item.customerId,
							id    : item.customerId,
							shortName    : item.shortName,
							fullName     : item.customerName,
							paymentTerm  : item.paymentTerm,
							shippingCase : item.shippingCondition,
							loadingPort  : item.shippiingPort,
							deliveryPort : item.destinationPort,
							currency     : item.currency,
							
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
	
	select : function(event, ui) {//选择物料分类后,自动添加流水号IPid

		$("#attribute1").val(ui.item.id);	
		$("#order\\.customerid").val(ui.item.id);
		$("#attribute2").html("（"+ui.item.shortName+"）"+ui.item.fullName);
		$("#order\\.paymentterm").val(ui.item.paymentTerm);
		$("#order\\.shippingcase").val(ui.item.shippingCase);
		$("#order\\.loadingport").val(ui.item.loadingPort);
		$("#order\\.deliveryport").val(ui.item.deliveryPort);
		$("#currency").val(ui.item.currency);
		
		var shortName = ui.item.shortName;
		var parentId = shortYear + shortName;
		
		if (shortName != "") {//判断所选的编号
			$
			.ajax({
				type : "post",
				url : "${ctx}/business/order?methodtype=customerOrderMAXId",
				async : false,
				data : {
					parentId : parentId,
				},
				dataType : "json",
				success : function(data) {

					var retValue = data['retValue'];

					if (retValue === "failure") {
						$().toastmessage('showWarningToast',"请联系系统管理员。");
					} else {

						$("#order\\.parentid").val(parentId);
						$("#order\\.subid").val(data.codeFormat);	
						$("#order\\.piid").val(shortYear + shortName + data.codeFormat);
						//设置光标项目
						$("#order\\.orderid").focus();
					}
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					alert("发生系统异常，请再试或者联系系统管理员."); 	
				}
			});
		} else {}	
	},//select		
	
	
	minLength : 0,
	autoFocus : false,
});


$("#orderTransfer\\.ysid").autocomplete({

	source : function(request, response) {
		//alert(888);
		$.ajax({
			type : "POST",
			url : "${ctx}/business/order?methodtype=orderDetailSearch",
			dataType : "json",
			data : {
				key : request.term
			},
			success : function(data) {
				//alert(777);
				response($.map(
					data.data,
					function(item) {
						//alert(item.viewList)
						return {
							label : item.YSId+" | "+item.materialId+" | "+item.materialName,
							value : item.YSId,
							materialId   : item.materialId,
							materialName : item.materialName,
							quantity  	 : item.quantity,
							productClassifyName : item.productClassifyName,
							price  		: item.price,
							totalprice 	: item.totalPrice,
							
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
	
	select : function(event, ui) {//选择物料分类后,自动添加流水号IPid

		$("#targetId").html(ui.item.materialId);
		$("#targetName").html(ui.item.materialName);
		$("#productClassify").html(ui.item.productClassifyName);
		$("#quantity").html(ui.item.quantity);
		$("#price").html(ui.item.price);
			
		$("#orderTransfer\\.price").val(ui.item.price);
		$("#orderTransfer\\.quantity").val(ui.item.quantity);
		
	
	},//select		
	
	
	minLength : 5,
	autoFocus : false,
});


function autocomplete(){

	$('select').css('width','100px');	
	$('#order\\.ordercompany').css('width','300px');
	$('.ysidCheck').removeClass('bgnone');
	$(".ysidCheck").attr('readonly', "true");
	
	$(".attributeList1").autocomplete({
		minLength : 2,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/order?methodtype=getMaterialList",
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
									materialId : item.materialId
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
			
			//产品名称
			$(this).parent().parent().find("td").eq(2).find("span")
				.html(jQuery.fixedWidth(ui.item.name,30));

			//产品编号
			$(this).parent().find("input:hidden").val(ui.item.materialId);
			
		},

		
	});
}

</script>

<script type="text/javascript">



function saleTotalSum(){
	
	var product = productCostSum();//正常订单销售额

	var product2 = 0;
	
	return product+ product2;
	
}
function ysidCheck(YSId){
	
	var url = "${ctx}/business/order?methodtype=ysidExistCheck&YSId="+YSId
	
	if (YSId != ""){ 
		$.ajax({
			type : "post",
			url : url,
			async : false,
			data : 'key=' + YSId,
			dataType : "json",
			success : function(data) {

				ExFlagYS = data["ExFlag"];
				if(ExFlagYS == '1'){
					$().toastmessage('showWarningToast', "耀升编号［ "+YSId+" ］已存在,请重新输入。");				
				}

			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {
				
				//alert("supplierId2222:"+textStatus);
			}
		});
	}else{
	}
	
}
</script>
</html>
