<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>订单管理-订单新建(成品管理)</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var options = "";//币种可选项
	var counter = 0;
	var YSParentId = "";
	var YSSwift = "";
	var totalPrice = "0";
	var shortYear = ""; 
	var matMsg= "请选择产品!";

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
						
						YSSwift = Number(YSSwift)+1;
						var fmtId = YSParentId + PrefixInteger(YSSwift,3); 
						var lineNo =  rowIndex+1;
						var hidden = "";
						
						hidden = '';
						
						var rowNode = $('#example')
							.DataTable()
							.row
							.add(
							  [
								'<td><input type="text"   name="orderDetailLines['+rowIndex+'].ysid"  id="orderDetailLines'+rowIndex+'.ysid" class="mini read-only ysidCheck" /></td>',
								'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
									'<input type="hidden" name="orderDetailLines['+rowIndex+'].materialid" id="orderDetailLines'+rowIndex+'.materialid" /></td>',
								'<td><span></span></td>',
								'<td><select  name="orderDetailLines['+rowIndex+'].productclassify"   id="orderDetailLines'+rowIndex+'.productclassify" class="short"></select></td>',
								'<td><input type="text"   name="orderDetailLines['+rowIndex+'].quantity"   id="orderDetailLines'+rowIndex+'.quantity"   class="num mini" /></td>',
								'<td><input type="text"   name="orderDetailLines['+rowIndex+'].extraquantity"    id="orderDetailLines'+rowIndex+'.extraquantity"   class="num mini" />'+
									'<input type="hidden" name="orderDetailLines['+rowIndex+'].totalquantity"    id="orderDetailLines'+rowIndex+'.totalquantity" /></td>',
								'<td><input type="text"   name="orderDetailLines['+rowIndex+'].price"      id="orderDetailLines'+rowIndex+'.price"      class="cash short" /></td>',
								'<td><span></span><input type="hidden"   name="orderDetailLines['+rowIndex+'].totalprice" id="orderDetailLines'+rowIndex+'.totalprice" /></td>',				
								
								]).draw();

						$("#orderDetailLines" + rowIndex + "\\.productclassify").html(options);
						$("#orderDetailLines" + rowIndex + "\\.ysid").val(fmtId);
						
						rowIndex ++;						
					}					
					counter += 1;

					$('select').css('width','100px');	
					
					autocomplete();
						
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
				
				//var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				//alert('['+amount+']:amount '+'---- totalPrice:'+totalPrice)
				
				$().toastmessage('showWarningToast', "删除后,[ PI编号 ]可能会发生变化。");	
				t.row('.selected').remove().draw();

				//销售总价	
				var currency = $('#order\\.currency option:checked').text();// 选中项目的显示值
				totalPrice = floatToSymbol( productCostSum(),currency);			
				$('#order\\.totalprice').val(floatToCurrency(totalPrice));	
				$('#total').text(floatToCurrency(totalPrice));
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
			        	{
					}, {
					}, {								
					}, {				
					}, {				
					}, {				
					}, {				
					}, {"className":"dt-body-right"				
					}			
				]
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(5),tr td:nth-child(7)',function() {

			var $td = $(this).parent().find("td");

			var $oQuantity = $td.eq(4).find("input");
			var $oPricei  = $td.eq(6).find("input:text");
			//var $oPriceh  = $td.eq(6).find("input:hidden");
			var $oAmount  = $td.eq(7).find("input");
			var $oAmounts = $td.eq(7).find("span");
			
			var currency = $('#order\\.currency option:checked').text();// 选中项目的显示值

			var fPrice = currencyToFloat($oPricei.val());	

			var fQuantity = currencyToFloat($oQuantity.val());
			var fTotalOld = currencyToFloat($oAmount.val());

			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vPricei = floatToSymbol(fPrice,currency);
			//var vPriceh = floatToCurrency(fPrice);
			var vQuantity = floatToNumber($oQuantity.val());
			var vTotalNew = floatToSymbol(fTotalNew,currency);
			
			//详情列表显示新的价格
			$oPricei.val(vPricei);
			//$oPriceh.val(vPriceh);
			$oQuantity.val(vQuantity);
			$oAmount.val(vTotalNew);
			$oAmounts.html(vTotalNew);

			//临时计算该客户的销售总价
			//首先减去旧的价格			
			//totalPrice = floatToSymbol(currencyToFloat(totalPrice) - fTotalOld + fTotalNew,currency);
			totalPrice = floatToSymbol( productCostSum(),currency);			
			$('#order\\.totalprice').val(totalPrice);
			$('#total').text(totalPrice);			

		});		
			
		t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {

			var $td = $(this).parent().find("td");

			var $oQuantity = $td.eq(4).find("input");
			var $oExtraQua = $td.eq(5).find("input:text");
			var $oTotalQua = $td.eq(5).find("input:hidden");		

			var fQuantity = currencyToFloat($oQuantity.val());
			var fExtraQua = currencyToFloat($oExtraQua.val());
			var fTotalQua = currencyToFloat(fExtraQua + fQuantity);

			var vPriceh = floatToCurrency(fExtraQua);
			var vTotalNew = floatToCurrency(fTotalQua);			
			//
			$oExtraQua.val(vPriceh);
			$oTotalQua.val(vTotalNew);
			$oExtraQua.val(floatToNumber(fExtraQua));
			//alert("fQuantity"+fQuantity+"fExtraQua"+fExtraQua+"oTotalQua"+$oTotalQua.val())

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

	};//ajax()

	//列合计:总价
	function productCostSum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(7).find("input").val();
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
		
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		
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
					var PIId = '${order.PIId }';
					var url = "${ctx}/business/order?methodtype=detailView&PIId=" + PIId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {	

			var str = '';
			$(".attributeList1").each(function(){			
				str += $(this).val();				
			});			
			if(str.length<=0){
				$().toastmessage('showWarningToast', "至少保留一个产品信息。");		
				return;				
			}
			
			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=update");
			$('#orderForm').submit();
		});
		
		$(document).on("change", ".ysidCheck", function(){

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
		});	//ysidcheck()
		
		$('#order\\.currency').val('${order.currencyId }');
		$('#order\\.shippingcase').val('${order.shippingCaseId }');
		$('#order\\.loadingport').val('${order.loadingPortId }');
		$('#order\\.deliveryport').val('${order.deliveryPortId }');
		$('#order\\.team').val('${order.teamId }');
		var orderCompany = '${order.orderCompany }';
		if(orderCompany != null && orderCompany != ""){
			$('#order\\.ordercompany').val(orderCompany);
		}
		
		//input获取焦点初始化处理
		foucsInit();
		
		$(".cash") .blur(function(){
			
			var currency = $('#order\\.currency option:checked').text();// 选中项目的显示值
			$(this).val(floatToSymbol($(this).val(),currency));
		});
		
		$('select').css('width','100px');
		$('#order\\.ordercompany').css('width','200px');
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
		<form:hidden path="order.recordid"  value="${order.orderRecordId}"/>
		<form:hidden path="order.parentid" />
		<form:hidden path="order.subid" />
		
		<fieldset>
			<legend> 订单综合信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" width="100px"><label>PI编号：</label></td>					
					<td><form:input path="order.piid" 
							value="${order.PIId }" class="read-only" />
						<form:hidden path="keyBackup" 
							value="${order.PIId }" /></td>
					<td width="100px" class="label" >
						<label >客户订单号：</label></td>
					<td colspan="5">
						<form:input path="order.orderid" class="short required"  value="${order.orderId }" /></td>								
				</tr>	
				<tr>
					<td class="label"><label>客户名称：</label></td>				
					<td colspan="3">${order.customerId }（${order.shortName }）${order.fullName }</td>
					<td class="label"><label>下单公司：</label></td>				
					<td colspan="3">
						<form:select path="order.ordercompany">
								<form:options items="${orderForm.ordercompanyList}" 
								  itemValue="key" itemLabel="value" />
						</form:select></td>	
				</tr>				
				<tr> 
					<td class="label"><label>付款条件：</label></td>
					<td >&nbsp;出运后
						<form:input path="order.paymentterm"  value="${order.paymentTerm }" 
							style="width: 30px;text-align: center;" class="td-center" />&nbsp;天</td>
						
					<td width="100px"  class="label">
						<label >出运条件：</label></td>
					<td >
						<form:select path="order.shippingcase">
								<form:options items="${orderForm.shippingCaseList}" 
								  itemValue="key" itemLabel="value" />
						</form:select></td>
					
					<td class="label"><label>出运港：</label></td>
					<td><form:select path="order.loadingport">
							<form:options items="${orderForm.loadingPortList}"
							  itemValue="key" itemLabel="value" />
						</form:select></td>

					<td class="label"><label>目的港：</label></td>
					<td><form:select path="order.deliveryport" >
							<form:options items="${orderForm.deliveryPortList}" 
							 itemValue="key" itemLabel="value" />
						</form:select></td>							
				</tr>
				<tr>
					<td class="label">业务组：</td>
					<td>
						<form:select path="order.team"  >
							<form:options items="${orderForm.teamList}" itemValue="key" itemLabel="value" />
						</form:select></td>			
					<td class="label">
						<label>下单日期：</label></td>
					<td>
						<form:input path="order.orderdate" class="short required"  value="${order.orderDate }" /></td>					
					<td class="label">
						<label>订单交期：</label></td>
					<td>
						<form:input path="order.deliverydate" class="short required"  value="${order.deliveryDate }" /></td>
				</tr>
				<tr>
					<td class="label">币种：</td>
					<td>
						<form:select path="order.currency"  >
							<form:options items="${orderForm.currencyList}" itemValue="key" itemLabel="value" />
						</form:select></td>			
					<td class="label">
						<label>销售总价：</label></td>
					<td colspan="5"><span id="total">${order.total}</span>
						<form:hidden path="order.totalprice" value="${order.total}" /></td>																	
				</tr>						
			</table>
			

	</fieldset>
	
	<fieldset>
		<legend> 订单详情</legend>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display" >
			<thead>				
			<tr>
				<th class="dt-center" width="60px">耀升编号</th>
				<th class="dt-center" width="100px">产品编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" width="90px">版本类别</th>
				<th class="dt-center" width="60px">订单数量</th>
				<th class="dt-center" width="60px">额外<br>采购数量</th>
				<th class="dt-center" width="60px">销售单价</th>
				<th class="dt-center" width="60px">销售总价</th>
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
			<c:forEach var='order' items='${detail}' varStatus='status'>	

				<tr>
					<td><input type="text" name="orderDetailLines[${status.index}].ysid" id="orderDetailLines${status.index}.ysid" value="${order.YSId}" class="mini read-only ysidCheck"  /></td>
					<td><input type="text" name="attributeList1" class="attributeList1"  value="${order.materialId}" />
						<form:hidden path="orderDetailLines[${status.index}].materialid" value="${order.materialId }"/></td>
					<td id="shortName${status.index}"></td>
					<td>
						<form:select path="orderDetailLines[${status.index}].productclassify" >							
							<form:options items="${orderForm.productClassifyList}" 
								itemValue="key" itemLabel="value" /></form:select></td>
					<td><form:input path="orderDetailLines[${status.index}].quantity" class="num mini" value="${order.quantity}"/></td>
				<td><input id="extraquantiry${status.index}" class="num mini"  value="${order.extraQuantity}"/>
					<form:hidden path="orderDetailLines[${status.index}].totalquantity" value="${order.totalQuantity}"/></td>
					<td><form:input path="orderDetailLines[${status.index}].price" class="cash short"  value="${order.price}"/></td>
					<td><span>${order.totalPrice}</span><input type="hidden" name="orderDetailLines[${status.index}].totalprice" id="orderDetailLines${status.index}.totalprice" value="${order.totalPrice}" /></td>
					
					<form:hidden path="orderDetailLines[${status.index}].recordid" value="${order.detailRecordId }"/>
				</tr>
					<script type="text/javascript">
						var productclassify = '${order.productClassify}';
						var materialName = '${order.materialName}';
						var index = '${status.index}';
						$('#shortName'+index).html(jQuery.fixedWidth(materialName,35));
						$("#orderDetailLines"+index+"\\.productclassify").val(productclassify);
						counter++;
					</script>	
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
			$(this).parent().parent().find("td").eq(2).find("span")
				.html(jQuery.fixedWidth(ui.item.name,35));

			//产品编号
			$(this).parent().find("input:hidden").val(ui.item.materialId);
			

			$(this).css('background-color','');
			
		},

		
	});
}

</script>
	
</html>
