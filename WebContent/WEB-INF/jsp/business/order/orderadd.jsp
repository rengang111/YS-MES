<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>订单管理-订单录入</title>
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
					
					var fmtId = "";
					var deleteFlag = false;
					
					var lineNo =  rowIndex+1;
					var hidden = "";

					//优先考虑前端删掉的编号
					for(var i=0;i<ysidList.length;i++){
						var tmp = ysidList[i][1];
						if(tmp == "1"){
							fmtId = ysidList[i][0];
							ysidList[i][1]="0";
							deleteFlag = true;
							break;
						}
					}
					if(deleteFlag == false){
						
						YSSwift = parseInt(YSSwift)+1;
						fmtId = YSParentId + PrefixInteger(YSSwift,4); 							
						var i = ysidList.length;	
						ysidList[i]=new Array()
						ysidList[i][0]=fmtId;
						ysidList[i][1]="0";						
					}
					
					
					ysidCheck(fmtId);//耀升编号重复check
					
					var rowNode = $('#example')
						.DataTable()
						.row
						.add(
						  [
							'<td><input type="text"   name="orderDetailLines['+rowIndex+'].ysid"       id="orderDetailLines'+rowIndex+'.ysid"  class="mini  read-only ysidCheck" /></td>',
							'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
								'<input type="hidden" name="orderDetailLines['+rowIndex+'].materialid" id="orderDetailLines'+rowIndex+'.materialid" /></td>',
							'<td><span></span></td>',
							'<td><select  name="orderDetailLines['+rowIndex+'].productclassify"   id="orderDetailLines'+rowIndex+'.productclassify" class="short"></select>'+
							    '<input type="hidden" name="orderDetailLines['+rowIndex+'].ordertype"  id="orderDetailLines'+rowIndex+'.ordertype"  value="010"/></td>',
							'<td><input type="text"   name="orderDetailLines['+rowIndex+'].quantity"   id="orderDetailLines'+rowIndex+'.quantity"   class="num mini" /></td>',
							'<td><input type="text"   name="orderDetailLines['+rowIndex+'].extraquantity"   	 id="orderDetailLines'+rowIndex+'.extraquantity"   class="num" style="width:30px;height: 20px;"/>'+
								'<input type="hidden" name="orderDetailLines['+rowIndex+'].totalquantity"        id="orderDetailLines'+rowIndex+'.totalquantity" /></td>',
							'<td><input type="text"   name="orderDetailLines['+rowIndex+'].price"                id="orderDetailLines'+rowIndex+'.price"     class="cash short" />'+
								'<span class="order"><input type="text"   name="orderDetailLines['+rowIndex+'].rmbprice"             id="orderDetailLines'+rowIndex+'.rmbprice"  class="cash short rmb" style="margin-top: 5px;"/></span></td>',
							'<td><input type="text"   name="orderDetailLines['+rowIndex+'].totalprice" id="orderDetailLines'+rowIndex+'.totalprice" class="cash short" />'+
								'<span class="order"><input type="text"   name="orderDetailLines['+rowIndex+'].ordercost"  id="orderDetailLines'+rowIndex+'.ordercost"  class="cash short rmb" style="margin-top: 5px;"/></span></td>',
							
							]).draw();
					
					$("#orderDetailLines" + rowIndex + "\\.productclassify").html(options);
					$("#orderDetailLines" + rowIndex + "\\.ysid").val(fmtId);
					
					rowIndex ++;						
				}					
				counter += 1;				

				$('select').css('width','100px');
				$('#order\\.ordercompany').css('width','300px');	
				
				foucsInit();//设置新增行的基本属性
				
				autocomplete();//调用自动填充功能
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
				
				var ysid = $('#example tbody tr').eq(rowIndex).find("td").eq(0).find("input").val();
				for(var i=0;i<ysidList.length;i++){
					var tmp = ysidList[i][0];
					//alert("tmp:"+tmp)
					if(ysid == tmp){
						ysidList[i][1] = "1";//删除的耀升编号标识出来
						break;
					}
				}
								
				$().toastmessage('showWarningToast', "删除后,[ 耀升编号 ]可能会发生变化。");	
				t.row('.selected').remove().draw();

				//销售总价	
				//var currency = $('#currency option:checked').text();// 选中项目的显示值
				//totalPrice = floatToSymbol( saleTotalSum(),currency);			
				//$('#order\\.totalprice').val(totalPrice);
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
			var $oPricei   = $td.eq(6).find("input").eq(0);
			var $oPriceo   = $td.eq(6).find("input").eq(1);
			var $oAmounti  = $td.eq(7).find("input").eq(0);
			var $oAmounto  = $td.eq(7).find("input").eq(1);
			
			var currency = $('#currency option:checked').text();// 选中项目的显示值：币种
			var company  = $('#order\\.ordercompany option:checked').val();// 选中项目的显示值:下单公司

			var fQuantity = currencyToFloat($oQuantity.val());
			var fPricei = currencyToFloat($oPricei.val());
			var fPriceo = currencyToFloat($oPriceo.val());

			var fTotalOrder = '0';
			var fPriceo     = '0';
			if(company != '010'){
				fPriceo     = currencyToFloat($oPriceo.val());
				fTotalOrder = currencyToFloat(fPriceo * fQuantity)
			}

			var fTotalNew = currencyToFloat(fPricei * fQuantity);

			var vQuantity = floatToNumber($oQuantity.val());
			
			var vPricei = floatToSymbol(fPricei,currency);
			var vTotalNew = floatToSymbol(fTotalNew,currency);
			
			var vTotalOrder = floatToSymbol(fTotalOrder,'人民币');
			var vPriceo =     floatToSymbol(fPriceo,'人民币');
			//alert("vPriceo"+fPriceo+"----"+vPriceo)
			
			//详情列表显示新的价格
			$oQuantity.val(vQuantity);
			$oPricei.val(vPricei);
			$oPriceo.val(vPriceo);
			$oAmounti.val(vTotalNew);
			$oAmounto.val(vTotalOrder);

			//临时计算该客户的销售总价
			//totalPrice = floatToSymbol( saleTotalSum(),currency);
			//$('#order\\.totalprice').val(totalPrice);

		});

		t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {

			var $td = $(this).parent().find("td");

			var $oQuantity = $td.eq(4).find("input");
			var $oExtraQua = $td.eq(5).find("input:text");
			var $oTotalQua = $td.eq(5).find("input:hidden");

			var fQuantity = currencyToFloat($oQuantity.val());
			var fExtraQua = currencyToFloat($oExtraQua.val());
			var fTotalQua = currencyToFloat(fExtraQua + fQuantity);

			//var vPriceh = floatToCurrency(fExtraQua);
			var vTotalNew = floatToCurrency(fTotalQua);			
			//
			//$oExtraQua.val(vPriceh);
			$oTotalQua.val(vTotalNew);
			$oExtraQua.val(floatToNumber(fExtraQua));

		});
		
		/*				
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
*/
		
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
		
		//设置光标项目
		$("#attribute1").focus();

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#order\\.orderdate").val(shortToday());
		
		ajax();//正常订单
		ajax2();//配件订单

		
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
			
			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=insert");
			$('#orderForm').submit();
		});
		
		$("#currency").change(function() {

			$().toastmessage('showWarningToast', "货币符号发生变化,请重新输入销售单价。");	
		});
		
		//下单公司选择
		$("#order\\.ordercompany").change(function() {
			var id = $(this).val();
			if(id=='010'){
				$('.rmb').removeClass('bgnone').addClass('read-only');
				$('.rmb').val('0');
				$('.order').hide();		
			}else{
				$('.rmb').removeClass('read-only').addClass('bgnone');
				$('.order').show();		
			}
			
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
			var company  = $('#order\\.ordercompany option:checked').val();// 选中项目的显示值:下单公司
			if(company == '010'){
				
			}else{
				
			}
			//$(this).val(floatToSymbol($(this).val(),currency));
			//$('.rmb').val(floatToSymbol($(this).val(),'人民币'));
		});
			
		$(".DTTT_container").css('float','left');

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
				<td colspan="5">
					<form:select path="order.ordercompany">
							<form:options items="${orderForm.ordercompanyList}" 
							  itemValue="key" itemLabel="value" />
					</form:select></td>	
					<!--  
				<td class="label">销售总价：</td>
				<td>
					<form:input path="order.totalprice" class="read-only cash" style="width:150px"/></td>		-->									
			</tr>							
		</table>
</fieldset>

<fieldset class="action" style="text-align: right;margin: -15px 0 0px 0;">
	<button type="button" id="return" class="DTTT_button">返回</button>
	<button type="button" id="insert" class="DTTT_button">保存</button>
</fieldset>
<fieldset style="margin-top: -20px;">
	<legend> 正常订单详情</legend>
	<div class="list">
	
	<table id="example" class="display" >
		<thead>				
		<tr>
			<th class="dt-center" width="60px">耀升编号</th>
			<th class="dt-center" width="60px">产品编号</th>
			<th class="dt-center" >产品名称</th>
			<th class="dt-center" width="60px">版本类别</th>
			<th class="dt-center" width="60px">订单数量</th>
			<th class="dt-center" width="30px">额外<br />采购</th>
			<th class="dt-center" width="60px">销售单价<span class="order"><br />下单价格</span></th>
			<th class="dt-center" width="90px">销售总价<span class="order"><br />下单总价</span></th>
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
		<c:forEach var="i" begin="0" end="0" step="1">		
			<tr>
				<td><input type="text" name="orderDetailLines[${i}].ysid" id="orderDetailLines${i}.ysid" class="mini read-only ysidCheck"  /></td>
				<td><input type="text" name="attributeList1" class="attributeList1">
					<form:hidden path="orderDetailLines[${i}].materialid" /></td>
				<td><span></span></td>
				<td>
					<form:select path="orderDetailLines[${i}].productclassify" >							
						<form:options items="${orderForm.productClassifyList}" 
							itemValue="key" itemLabel="value" /></form:select></td>
				<td><form:input path="orderDetailLines[${i}].quantity" class="num mini"  /></td>
				<td><input id="extraquantiry${i}" class="num " style="width:30px;height: 20px;" />
					<form:hidden path="orderDetailLines[${i}].totalquantity" /></td>
				<td><form:input path="orderDetailLines[${i}].price"     class="cash short"  /><br />
					<span class="order">
					<form:input path="orderDetailLines[${i}].rmbprice"  class="cash short rmb"  style="margin-top: 5px;"/>
					</span></td>
				<td>
					<form:input path="orderDetailLines[${i}].totalprice"  class="cash"      readonly="readonly"/>
					<span class="order">
					<form:input path="orderDetailLines[${i}].ordercost"   class="cash rmb"  readonly="readonly" style="margin-top: 5px;"/>
					</span></td>
				
				<form:hidden path="orderDetailLines[${i}].parentid" />
				<form:hidden path="orderDetailLines[${i}].subid" />
				<form:hidden path="orderDetailLines[${i}].ordertype" value="010"/>
				
			</tr>
				<script type="text/javascript" >
					var index = '${i}';
					YSSwift = parseInt(YSSwift)+ 1;
					var fmtId = YSParentId + PrefixInteger(YSSwift,4); 
					$("#orderDetailLines" + index + "\\.ysid").val(fmtId);		

					//alert("ysidList1:"+index)
					ysidList[index]=new Array()
					ysidList[index][0]=fmtId;
					ysidList[index][1]="0";
					
					counter++;
				</script>
		</c:forEach>
		
	</tbody>
</table>
</div>
</fieldset>	

<fieldset>
	<legend> 配件订单详情</legend>
	<div class="list" style="margin-top: -4px;">
	
		<table id="example2" class="display" >
			<thead>				
				<tr>
					<th class="dt-center" width="60px">耀升编号</th>
					<th class="dt-center" width="60px">产品编号</th>
						<th class="dt-center" >产品名称</th>
					<th class="dt-center" width="60px">版本类别</th>
					<th class="dt-center" width="60px">订单数量</th>
					<th class="dt-center" width="60px">额外采购</th>
					<th class="dt-center" width="60px">销售单价</th>
					<th class="dt-center" width="90px">销售总价</th>
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


function autocomplete(){
	
	$('select').css('width','100px');	
	$('#order\\.ordercompany').css('width','300px');
	$('.ysidCheck').removeClass('bgnone');
	$(".ysidCheck").attr('readonly', "true");

	var company = $('#order\\.ordercompany option:checked').val();// 选中项目的显示值:下单公司
	if(company == '010'){
		$('.rmb').removeClass('bgnone').addClass('read-only');
		$('.rmb').val('0');
		$('.order').hide();		
	}
	
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
			//$(this).parent().parent().find("td").eq(2).find("span").html(jQuery.fixedWidth(ui.item.name,64));
			$(this).parent().parent().find("td").eq(2).find("span").html(ui.item.name,64);

			//产品编号
			$(this).parent().find("input:hidden").val(ui.item.materialId);
			
		},

		
	});
}

</script>

<script type="text/javascript">
$.fn.dataTable.TableTools.buttons.add_rows2 = $
.extend(
	true,
	{},
	$.fn.dataTable.TableTools.buttonBase,
	{
		"fnClick" : function(button) {
			
			var rowIndex = counter;
			var hidden = '';
			var fmtId = "";	
			var deleteFlag = false;		
			//YSSwiftPeiIndex = PrefixInteger(YSSwiftPeiIndex,2)
			
			//优先考虑正常订单删掉的编号
			var deleteFlag2 = false;
			if(PieYSId == ""){
				for(var i=0;i<ysidList.length;i++){
					var tmp = ysidList[i][1];
					if(tmp == "1"){
						PieYSId = ysidList[i][0];
						ysidList[i][1]="0";
						deleteFlag2 = true;
						break;
					}
				}				
			}
			//alert("deleteFlag2:"+deleteFlag2+"--PieYSId:"+PieYSId)
			//找出前端删掉的编号
			if(deleteFlag2 == false){

				for(var i=0;i<ysidPeiList.length;i++){
					var tmp = ysidPeiList[i][2];
					if(tmp == "1"){
						fmtId = ysidPeiList[i][0];
						ysidPeiList[i][2]="0";
						deleteFlag = true;
						break;
					}
				}			

				if(deleteFlag == false){
	
					YSSwiftPeiIndex++;
					if(PieYSId ==""){
						YSSwift = parseInt(YSSwift)+1;
						PieYSId = YSParentId + PrefixInteger(YSSwift,4);
					}
					fmtId = PieYSId+"-"+YSSwiftPeiIndex;
					var index = ysidPeiList.length;
					ysidPeiList[index] = new Array();
					ysidPeiList[index][0] = fmtId;
					ysidPeiList[index][1] = PieYSId;
					ysidPeiList[index][2] = "0";
					
				}
			}else{
				YSSwiftPeiIndex++;
				fmtId = PieYSId+"-"+YSSwiftPeiIndex;
				
				var index = ysidPeiList.length;
				ysidPeiList[index] = new Array();
				ysidPeiList[index][0] = fmtId;
				ysidPeiList[index][1] = PieYSId;
				ysidPeiList[index][2] = "0";
			}
			
			for (var i=0;i<1;i++){
				var lineNo =  rowIndex+1;
				var hidden = "";
				
				var rowNode = $('#example2')
					.DataTable()
					.row
					.add(
					  [
						'<td><input type="text"   name="orderDetailLines['+rowIndex+'].ysid"       id="orderDetailLines'+rowIndex+'.ysid"  class="short read-only ysidCheck" /></td>',
						'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
							'<input type="hidden" name="orderDetailLines['+rowIndex+'].materialid" id="orderDetailLines'+rowIndex+'.materialid" /></td>',
						'<td><span></span></td>',
						'<td><select  name="orderDetailLines['+rowIndex+'].productclassify"   id="orderDetailLines'+rowIndex+'.productclassify" class="short"></select>'+
					        '<input type="hidden" name="orderDetailLines['+rowIndex+'].ordertype"  id="orderDetailLines'+rowIndex+'.ordertype"  value="020"/></td>',
						'<td><input type="text"   name="orderDetailLines['+rowIndex+'].quantity"   id="orderDetailLines'+rowIndex+'.quantity"   class="num mini" /></td>',
						'<td><input type="text"   name="orderDetailLines['+rowIndex+'].extraquantity"   	 id="orderDetailLines'+rowIndex+'.extraquantity"   class="num mini" />'+
							'<input type="hidden" name="orderDetailLines['+rowIndex+'].totalquantity"        id="orderDetailLines'+rowIndex+'.totalquantity" /></td>',
						'<td><input type="text"   name="orderDetailLines['+rowIndex+'].price"           id="orderDetailLines'+rowIndex+'.price"           class="cash short" /></td>',
						'<td><span></span><input type="hidden"   name="orderDetailLines['+rowIndex+'].totalprice" id="orderDetailLines'+rowIndex+'.totalprice" /></td>',
						
						]).draw();
				
				$("#orderDetailLines" + rowIndex + "\\.productclassify").html(options);
				$("#orderDetailLines" + rowIndex + "\\.productclassify").val('RMB');
				$("#orderDetailLines" + rowIndex + "\\.ysid").val(fmtId);
				
				rowIndex ++;						
			}					
			counter += 1;				

			foucsInit();//设置新增行的基本属性
			
			autocomplete();//调用自动填充功能
		}
	});

$.fn.dataTable.TableTools.buttons.reset2 = $.extend(true, {},
	$.fn.dataTable.TableTools.buttonBase, {
	"fnClick" : function(button) {

		var t=$('#example2').DataTable();
		
		rowIndex = t.row('.selected').index();

		if(typeof rowIndex == "undefined"){				
			$().toastmessage('showWarningToast', "请选择要删除的数据。");	
		}else{
			
			var ysid = $('#example2 tbody tr').eq(rowIndex).find("td").eq(0).find("input").val();

			for(var i=0;i<ysidPeiList.length;i++){
				var tmp = ysidPeiList[i][0];
				//alert("length:"+ysidPeiList.length+"--list:"+tmp+"--ysid:"+ysid)
				if(ysid == tmp){
					ysidPeiList[i][2] = "1";//删除的耀升编号标识出来
					break;
				}
			}
			
			var ysidCount = true;
			for(var i=0;i<ysidPeiList.length;i++){//确认是否已经全部删除
				var tmp = ysidPeiList[i][2];
				//alert("tmp:"+tmp+"--ysid:"+ysid)
				if(tmp == "0"){
					ysidCount = false;
					break;
				}
			}
			
			if(ysidCount == true){//如全部删除,回收该编号
				//alert("ysidCount:"+ysidCount)
				PieYSId = "";//清空预留的耀升编号
				YSSwiftPeiIndex = 0;//清空
				ysidPeiList = new Array();//清空
				
				var i = ysidList.length;
				ysidList[i] = new Array();
				ysidList[i][0] = ysid.split("-")[0];
				ysidList[i][1] = "1";
			}
			
			$().toastmessage('showWarningToast', "删除后,[ 耀升编号 ]可能会发生变化。");	
			t.row('.selected').remove().draw();

			//销售总价	
			//var currency = $('#currency option:checked').text();// 选中项目的显示值
			//totalPrice = floatToSymbol( saleTotalSum(),currency);
			//$('#order\\.totalprice').val(totalPrice);
		}
					
	}
});

function ajax2() {

	var t = $('#example2').DataTable({
		
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
				"sExtends" : "add_rows2",
				"sButtonText" : "追加新行"
			},
			{
				"sExtends" : "reset2",
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
		var $oAmount  = $td.eq(7).find("input");
		var $oAmounts = $td.eq(7).find("span");
		
		var currency = $('#currency option:checked').text();// 选中项目的显示值

		var fPrice = currencyToFloat($oPricei.val());	

		var fQuantity = currencyToFloat($oQuantity.val());

		var fTotalNew = currencyToFloat(fPrice * fQuantity);

		var vPricei = floatToSymbol(fPrice,currency);
		var vQuantity = floatToNumber($oQuantity.val());
		var vTotalNew = floatToSymbol(fTotalNew,currency);
		
		//详情列表显示新的价格
		$oPricei.val(vPricei);
		$oQuantity.val(vQuantity);
		$oAmount.val(vTotalNew);
		$oAmounts.html(vTotalNew);

		//临时计算该客户的销售总价
		//首先减去旧的价格			
		//totalPrice = floatToSymbol( saleTotalSum(),currency);
		//$('#order\\.totalprice').val(totalPrice);

	});
	
	t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {

		var $td = $(this).parent().find("td");

		var $oQuantity = $td.eq(4).find("input");
		var $oExtraQua = $td.eq(5).find("input:text");
		var $oTotalQua = $td.eq(5).find("input:hidden");			

		var fQuantity = currencyToFloat($oQuantity.val());
		var fExtraQua = currencyToFloat($oExtraQua.val());
		var fTotalQua = currencyToFloat(fExtraQua + fQuantity);

		//var vPriceh = floatToCurrency(fExtraQua);
		var vTotalNew = floatToCurrency(fTotalQua);			
		//
		//alert("fQuantity"+fQuantity+"fExtraQua"+fExtraQua+"vTotalNew"+vTotalNew)
		//$oExtraQua.val(vPriceh);
		$oTotalQua.val(vTotalNew);
		$oExtraQua.val(floatToNumber(fExtraQua));

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

};

//列合计:总价
function productCostSum2(){

	var sum = 0;
	$('#example2 tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(7).find("input").val();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

function saleTotalSum(){
	
	var product = productCostSum();//正常订单销售额

	var product2 = productCostSum2();//配件订单销售额
	
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
