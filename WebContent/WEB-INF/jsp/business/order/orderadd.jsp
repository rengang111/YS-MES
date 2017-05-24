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
<title>订单管理-订单录入</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var options = "";//币种可选项
	var counter = 5;
	var YSParentId = "";
	var YSSwift = "";
	var totalPrice = "";
	var shortYear = ""; 
	var ExFlagPI = '';//PI编号重复check
	var ExFlagYS = '';//ys编号重复check

	YSSwift = '${orderForm.YSMaxId}';
	YSParentId = '${orderForm.YSParentId}';
	
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
					var hidden = '';
					
					for (var i=0;i<1;i++){
						
						//alert('YSSwift='+YSSwift); 
						YSSwift = YSSwift+1;
						var fmtId = YSParentId + PrefixInteger(YSSwift,3); 
						var lineNo =  rowIndex+1;
						var hidden = "";
						
						hidden = '';
						
						var rowNode = $('#example')
							.DataTable()
							.row
							.add(
							  [
								'<td class="dt-center"></td>',
								'<td><input type="text"   name="orderDetailLines['+rowIndex+'].ysid"       id="orderDetailLines'+rowIndex+'.ysid" style="width:70px;" class="read-only" /></td>',
								'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
									'<input type="hidden" name="orderDetailLines['+rowIndex+'].materialid" id="orderDetailLines'+rowIndex+'.materialid" /></td>',
								'<td></td>',
								'<td><input type="text"   name="orderDetailLines['+rowIndex+'].quantity"   id="orderDetailLines'+rowIndex+'.quantity"   class="num short" /></td>',
								'<td><input type="text"   name="orderDetailLines['+rowIndex+'].price"      id="orderDetailLines'+rowIndex+'.price"      class="cash short" /></td>',
								'<td><span></span><input type="hidden"   name="orderDetailLines['+rowIndex+'].totalprice" id="orderDetailLines'+rowIndex+'.totalprice"  readonly="readonly"/></td>',
								'<td></td>',				
								
								]).draw();
						
						$("#orderDetailLines" + rowIndex + "\\.currency").html(options);
						$("#orderDetailLines" + rowIndex + "\\.ysid").val(fmtId);
						
						rowIndex ++;						
					}					
					counter += 1;
					
					foucsInit();//设置新增行的基本属性
					
					autocomplete();//调用自动填充功能
					
					iFramAutoSroll();//重设显示窗口(iframe)高度
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
				
				var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				//alert('['+amount+']:amount '+'---- totalPrice:'+totalPrice)
				
				$().toastmessage('showWarningToast', "删除后,[ PI编号 ]可能会发生变化。");	
				t.row('.selected').remove().draw();

				//随时计算该客户的销售总价
				totalPrice = currencyToFloat(totalPrice) - currencyToFloat(amount);			
				$('#order\\.totalprice').val(floatToCurrency(totalPrice));
			}
						
		}
	});
	
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

			dom : 'T<"clear">rt',

			"tableTools" : {

				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

				"aButtons" : [ {
					"sExtends" : "add_rows",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset",
					"sButtonText" : "清空一行"
				}  ],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {				
					}, {				
					}, {"className":"dt-body-right"				
					}			
				]
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {

			var $td = $(this).parent().find("td");

			var $oQuantity = $td.eq(4).find("input");
			var $oPricei  = $td.eq(5).find("input");
			var $oPriceh  = $td.eq(5).find("hidden");
			var $oAmount  = $td.eq(6).find("input");
			var $oAmounts = $td.eq(6).find("span");
			
			var currency = $('#order\\.currency option:checked').text();// 选中项目的显示值

			var fPrice = currencyToFloat($oPricei.val());	

			var fQuantity = currencyToFloat($oQuantity.val());
			var fTotalOld = currencyToFloat($oAmount.val());

			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vPricei = floatToSymbol(fPrice,currency);
			var vPriceh = floatToCurrency(fPrice);
			var vQuantity = floatToNumber($oQuantity.val());
			var vTotalNew = floatToSymbol(fTotalNew,currency);
			
			//详情列表显示新的价格
			$oPricei.val(vPricei);
			$oPriceh.val(vPriceh);
			$oQuantity.val(vQuantity);
			$oAmount.val(vTotalNew);
			$oAmounts.html(vTotalNew);

			//临时计算该客户的销售总价
			//首先减去旧的价格			
			totalPrice = currencyToFloat(totalPrice) - fTotalOld + fTotalNew;
						
			$('#order\\.totalprice').val(floatToSymbol(totalPrice,currency));	
				

		});
		
						
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
		$("#attribute1").focus();
		//$("#order\\.piid").attr('readonly', "true");

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#order\\.orderdate").val(shortToday());
		
		ajax();


		autocomplete();
		
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
			if(ExFlagPI == 1){
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
			
			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=insert");
			$('#orderForm').submit();
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
		
		$(".ysidCheck").change(function() {

			var YSId = $(this).val().toUpperCase();
			//alert(YSId)
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
		});	//
		
		foucsInit();
		
		$(".cash") .blur(function(){
			
			var currency = $('#order\\.currency option:checked').text();// 选中项目的显示值
			$(this).val(floatToSymbol($(this).val(),currency));
		});
		
		$('select').css('width','100px');
		$(".DTTT_container").css('float','left');
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
				<td>
					<form:input path="order.piid" class="short required read-only" /></td>
					
				<td width="100px" class="label" >客户订单号：</td>
				<td colspan="5">
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
				<td >&nbsp;出运后
					<form:input path="order.paymentterm" 
						style="width: 30px;text-align: center;" class="td-center" />&nbsp;天</td>
					
				<td class="label">出运条件：</td>
				<td >
					<form:select path="order.shippingcase">
							<form:options items="${orderForm.shippingCaseList}" 
							  itemValue="key" itemLabel="value" />
					</form:select></td>
				
				<td class="label">出运港：</td>
				<td><form:select path="order.loadingport">
						<form:options items="${orderForm.loadingPortList}"
						  itemValue="key" itemLabel="value" />
					</form:select></td>

				<td class="label"><label>目的港：</label></td>
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
				<td colspan="3">
					<form:input path="order.deliverydate" class="short required" /></td>
											
			</tr>
			<tr>
				<td class="label">币种：</td>
				<td>
					<form:select path="order.currency">
						<form:options items="${orderForm.currencyList}" itemValue="key" itemLabel="value" />
					</form:select></td>	
				<td class="label">销售总价：</td>
				<td colspan="5">
					<form:input path="order.totalprice" class="read-only cash" /></td>											
			</tr>							
		</table>
</fieldset>

<fieldset>
	<legend> 订单详情</legend>
	<div class="list">
	
	<table id="example" class="display" >
		<thead>				
		<tr>
			<th width="1px">No</th>
			<th class="dt-left" width="80px">耀升编号</th>
			<th class="dt-left" width="100px">产品编号</th>
			<th class="dt-left" >产品名称</th>
			<th class="dt-left" width="100px">数量</th>
			<th class="dt-left" width="100px">销售单价</th>
			<th class="dt-left" width="120px">销售总价</th>
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
			</tr>
		</tfoot>
	<tbody>
		<c:forEach var="i" begin="0" end="4" step="1">		
			<tr>
				<td></td>
				<td><input type="text" name="orderDetailLines[${i}].ysid" id="orderDetailLines${i}.ysid" style="width:70px;" class="read-only ysidCheck"  /></td>
				<td><input type="text" name="attributeList1" class="attributeList1">
					<form:hidden path="orderDetailLines[${i}].materialid" /></td>								
				<td><span></span></td>
				<td><form:input path="orderDetailLines[${i}].quantity" class="num short" /></td>							
				<td><input id="price${i}" class="cash short"  />
					<form:hidden path="orderDetailLines[${i}].price" /></td>
				<td><span></span><input type="hidden" name="orderDetailLines[${i}].totalprice" id="orderDetailLines${i}.totalprice"  readonly="readonly"/></td>
			
				<script type="text/javascript" >
					var index = '${i}';
					YSSwift = parseInt(YSSwift)+ 1;
					var fmtId = YSParentId + PrefixInteger(YSSwift,3); 
					$("#orderDetailLines" + index + "\\.ysid").val(fmtId);						
				</script>
				
				<form:hidden path="orderDetailLines[${i}].parentid" />
				<form:hidden path="orderDetailLines[${i}].subid" />
				
			</tr>
		</c:forEach>
		
	</tbody>
</table>
</div>
</fieldset>
<div style="clear: both"></div>

<fieldset class="action" style="text-align: right;">
	<button type="button" id="return" class="DTTT_button">返回</button>
	<button type="button" id="insert" class="DTTT_button">保存</button>
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
		$("#order\\.currency").val(ui.item.currency);
		
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


function autocomplete(){
	
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
			$(this).parent().parent().find("td").eq(3).find("span")
				.html(jQuery.fixedWidth(ui.item.name,25));

			//产品编号
			$(this).parent().find("input:hidden").val(ui.item.materialId);
			
		},
	});
}

</script>
	
</html>
