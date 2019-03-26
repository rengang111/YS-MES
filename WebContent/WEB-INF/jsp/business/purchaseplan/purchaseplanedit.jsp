<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购方案--编辑</title>
<%@ include file="../../common/common2.jsp"%>
  	
<script type="text/javascript">

	var counter = 0;
	var moduleNum = 0;
	
	$.fn.dataTable.TableTools.buttons.add_row = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {

					for (var i=0;i<1;i++){
								
						var orderQuantity = '${order.totalQuantity}';
						var trhtml = "";
						var rowIndex = counter + 1;
						var hidden =	'<input type="hidden" name="planDetailList['+counter+'].subbomid" id="planDetailList'+counter+'.subbomid" value=""/>'+
									 	'<input type="hidden" name="planDetailList['+counter+'].subbomserial" id="planDetailList'+counter+'.subbomserial" value=""/>' 	

					 	var rowNode = $('#example')
						.DataTable()
						.row
						.add(
						  [
	'<td><span></span></td>',
	'<td class="td-center"><input type="text"   name="planDetailList['+counter+'].subbomno"   id="planDetailList'+counter+'.subbomno" value="0" class="cash"  style="width:20px"/></td>',
	'<td class="td-center">'+rowIndex+'<input type=checkbox name="numCheck" id="numCheck" value="" /></td>',
	'<td><input type="text" name="planDetailList['+counter+'].materialid" id="planDetailList'+counter+'.materialid" class="attributeList1"/></td>',
	'<td><span></span></td>',
	'<td class="td-center"><span id="unit'+counter+'"></span></td>',
	'<td><input type="text" name="planDetailList['+counter+'].unitquantity" id="planDetailList'+counter+'.unitquantity"  class="num mini" /></td>',
	'<td class="td-right"><span>'+orderQuantity+'</span></td>',
	'<td class="td-center"><span></span><input type="hidden"   name="planDetailList['+counter+'].manufacturequantity" id="planDetailList'+counter+'.manufacturequantity"/></td>',
	'<td class="td-right"><span></span></td>',
	'<td><input type="text" name="planDetailList['+counter+'].purchasequantity" id="planDetailList'+counter+'.purchasequantity"  class="num mini" /></td>',
	'<td><input type="text" name="planDetailList['+counter+'].totalrequisition" id="planDetailList'+counter+'.totalrequisition"  class="num mini" /></td>',
	'<td><input type="text" name="planDetailList['+counter+'].supplierid" id="planDetailList'+counter+'.supplierid"  class="supplierid short"/></td>',
	'<td class="td-right"><input type="text" name="planDetailList['+counter+'].price"      id="planDetailList'+counter+'.price" class="num mini" /></td>',
	'<td class="td-right"><span id="totalPrice'+counter+'"></span><input type="hidden"   name="planDetailList['+counter+'].totalprice" id="planDetailList'+counter+'.totalprice"/></td>',
	'<td class="td-right"><span></span>'+
		'<input type="hidden"   name="planDetailList['+counter+'].suppliershortname" id="planDetailList'+counter+'.suppliershortname"/>'+
		'<input type="hidden"   name="planDetailList['+counter+'].contractflag" id="planDetailList'+counter+'.contractflag" value="1" /></td>',
				
						]).draw();

						$("#planDetailList" + counter + "\\.materialid").focus();
						counter ++;			
					}
					
					autocomplete();
						
					foucsInit();
					
				}
			});

	$.fn.dataTable.TableTools.buttons.reset = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {
			
			var t=$('#example').DataTable();
			
			rowIndex = t.row('.selected').index();
			
			var str = true;
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					var n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
					$('#example tbody').find("tr:eq("+n+")").remove();
					str = false;
				}
			});
			
			if(str){
				$().toastmessage('showWarningToast', "请选择要删除的数据。");
			}else{
				$().toastmessage('showNoticeToast', "删除成功。");	
			}
			
				
		}
	});

	function fnselectall() { 
		if($("#selectall").prop("checked")){
			$("input[name='numCheck']").each(function() {
				$(this).prop("checked", true);
			});
				
		}else{
			$("input[name='numCheck']").each(function() {
				if($(this).prop("checked")){
					$(this).removeAttr("checked");
				}
			});
		}
	};
	
	function fnreverse() { 
		$("input[name='numCheck']").each(function () {  
	        $(this).prop("checked", !$(this).prop("checked"));  
	    });
	};
	
	$(document).ready(function() {		
			
		baseBomView();//基础BOM
		
		autocomplete();		
		
		$(".goBack").click(
				function() {
			var YSId = '${order.YSId}';
			var url = '${ctx}/business/purchasePlan?keyBackup=' + YSId;
	
			location.href = url;		
		});
		
		$(".goBack").click(
				function() {
			var YSId = '${order.YSId}';
			var url = '${ctx}/business/purchasePlan?keyBackup=' + YSId;
	
			location.href = url;		
		});
		
		$("#updateOrderBom").click(
				function() {
			
			//$(".loading").show();
			$('#updateOrderBom').attr("disabled","true").removeClass("DTTT_button");
			
			var materialId='${order.materialId}';
			var YSId ="${order.YSId}";
			var quantity ="${order.quantity}";
			var backFlag = $("#backFlag").val();
			
			$('#attrForm').attr("action","${ctx}/business/purchasePlan?methodtype=purchasePlanUpdate&YSId="+YSId+"&materialId="+materialId+"&quantity="+quantity+"&backFlag="+backFlag);
			$('#attrForm').submit();
			
		});
			
		
		  var lastIdx = null;
		
		$('#example').DataTable().on('click', 'tr', function() {

			var rowIndex = $(this).context._DT_RowIndex; //行号		
			//rowNumber = $(this).index()
			//alert(rowIndex)			
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#example').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            
	        }
			$('.DTFC_Cloned').find('tr').removeClass('selected');
			//$('.DTFC_Cloned').find('tr').eq(rowIndex+2).addClass('selected');
			
		});
		

		$( "#tabs" ).tabs();

		foucsInit();//input获取焦点初始化处理
		$(".DTTT_container").css('float','left');
	
		//costAcount();//成本核算
	});

	
	function baseBomView() {

		var scrollHeight = $(window).height() - 135;
		var materialId='${order.materialId}';
		var table = $('#example').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var t = $('#example').DataTable({
			"processing" : false,
			"retrieve"   : false,
			"stateSave"  : false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
	        "sScrollY"	: scrollHeight,
	        "sScrollX": true,
	       	"fixedColumns":   { leftColumns: 1 },
			"dom" 		: 'T<"clear">rt',
			//"sAjaxSource" : "${ctx}/business/bom?methodtype=getBaseBom&materialId="+materialId,	
			
			
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_row",
					"sButtonText" : "追加行"
				},
				{
					"sExtends" : "reset",
					"sButtonText" : "删除行"
				}],
			},			
			
	       	"language": {
	       		"url":"${ctx}/plugins/datatables/chinese.json"
	       	},
			"columns": [
				{"className" : 'td-left'},//0
				{"className" : 'td-center', "defaultContent" : ''},//1
				{"className" : 'td-center', "defaultContent" : ''},//2
				{"defaultContent" : ''},//3.物料编号
				{"defaultContent" : ''},//4.物料名称
				{"className" : 'td-center', "defaultContent" : ''},//5.单位:物料
				{"className" : 'td-right', "defaultContent" : ''},//6.单位使用量:baseBom
				{"className" : 'td-right'},//7.生产需求量:订单
				{"className" : 'td-right'},//8.总量= 单位使用量 * 生产需求量
				{"className" : 'td-right'},//9.当前库存(虚拟库存):物料
				{"className" : 'td-right'},//10.建议采购量:输入
				{"className" : 'td-right'},//11.领料数量
				{"defaultContent" : '0'},//11.供应商,可修改:baseBom
				{"className" : 'td-right', "defaultContent" : ''},//12.本次单价,可修改:baseBom
				{"className" : 'td-right', "defaultContent" : ''},//13.总价=本次单价*采购量
				{"className" : 'td-right', "defaultContent" : ''},//14.当前价格:baseBom

			 ],
		
			"columnDefs":[
				   		
	    		{
					"visible" : false,
					"targets" : [ ]
				}	          
	        ]
	     
		});
		
		//采购量
		t.on('change', 'tr td:nth-child(11)',function() {

			var contractQty   = currencyToFloat( $(this).find("input:text").attr("contractQty") );
			var arrivalCount  = currencyToFloat( $(this).find("input:text").attr("arrivalCount") );
			var stockinQty    = currencyToFloat( $(this).find("input:text").attr("stockinQty") );
			var returnQty     = currencyToFloat( $(this).find("input:text").attr("returnQty") );

	        //确认采购数量是否允许修改
	        if(stockinQty > 0){
	        	$(this).find("input:text").val(contractQty);       	
	        	$().toastmessage('showWarningToast', "该物料的合同已入库，不能修改采购数量。");
	        	return;
	        }
	        if(arrivalCount > 0 && returnQty <= 0){
	        	$(this).find("input:text").val(contractQty);       	
	        	$().toastmessage('showWarningToast', "该物料的合同已收货，不能修改采购数量。");
	        }	        
			
		});

		//单价
		t.on('change', 'tr td:nth-child(14)',function() {
			
			var contractPrice  = $(this).find("input:text").attr("contractPrice");
			var stockinQty  = $(this).find("input:text").attr("stockinQty");
	        var quantity = currencyToFloat(stockinQty);

	        //检查是否已生成合同
	        if(quantity > 0){
	        	$(this).find("input:text").val(contractPrice);	        	
	        	$().toastmessage('showWarningToast', "该物料已入库，不能修改单价。");
	        }
			
		});


		t.on('change', 'tr td:nth-child(7)',function() {
						
	        var $td = $(this).parent().find("td");
	        purchasePlanCompute($td,'1');
	        
		});
		
		t.on('change', 'tr td:nth-child(11),tr td:nth-child(13),tr td:nth-child(14)',function() {
					
	        var $td = $(this).parent().find("td");
	        purchasePlanCompute($td,'2');
	       
		});
		
	}//ajax()
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">		
		
		<input type="hidden" id="tmpMaterialId" />
		<input type="hidden" id="backFlag"  value="${backFlag }"/>
		<form:hidden path="purchasePlan.purchaseid"  value="${purchasePlan.purchaseId}" />
		<form:hidden path="purchasePlan.recordid"  value="${purchasePlan.planRecordId}" />
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:80px;"><label>耀升编号：</label></td>					
					<td style="width:100px;">${order.YSId}
					<form:hidden path="purchasePlan.ysid"  value="${order.YSId}" /></td>
								
					<td class="label" style="width:80px;"><label>产品编号：</label></td>					
					<td style="width:100px;"><a href="###" onClick="doShowProduct()">${order.materialId}</a>
					<form:hidden path="purchasePlan.materialid"  value="${order.materialId}" /></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td colspan="3">${order.materialName}</td>
				</tr>
				<tr>
					<td class="label"><label>ＰＩ编号：</label></td>
					<td>${order.PIId}</td>

					<td class="label"><label>订单数量：</label></td>
					<td><span id="quantity">${order.quantity}</span>（${order.unit}）</td>
						
					<td class="label"><label>客户名称：</label></td>
					<td>${order.customerFullName}</td>
					
					<!-- 
					<td class="label" style="width:80px;"><label>领料方式：</label></td>
					<td style="width:100px;"><form:select path="purchasePlan.requisitiontype" >							
						<form:options items="${requisitionType}" 
							itemValue="key" itemLabel="value" /></form:select></td>
							 -->
				</tr>							
			</table>
		</fieldset>
		<fieldset>
			<legend> 采购成本核算</legend>
			<table class="form" id="table_form2">
				<tr>
					<td class="td-center"><label>材料成本</label></td>
					<td class="td-center"><label>人工成本</label></td>
					<td class="td-center"><label> BOM成本</label></td>
					<td class="td-center"><label>基础成本</label></td>
					<td class="td-center"><label>经管费率</label></td>
					<td class="td-center"><label>经管费</label></td>
					<td class="td-center"><label>核算成本</label></td>
				</tr>
				<tr>
					<td class="td-center"><form:input path="purchasePlan.materialcost"  value="${purchasePlan.materialCost }" class="read-only cash" /></td>
					<td class="td-center"><form:input path="purchasePlan.laborcost"     value="${purchasePlan.laborCost }" class="read-only cash" /></td>
					<td class="td-center"><form:input path="purchasePlan.bomcost"       value="${purchasePlan.bomCost }" class="read-only cash" /></td>
					<td class="td-center"><form:input path="purchasePlan.productcost"   value="${purchasePlan.productCost }" class="read-only cash" /></td>
					<td class="td-center"><form:input path="purchasePlan.managementcostrate" value="${purchasePlan.managementCostRate }" class="read-only cash mini" />%</td>
					<td class="td-center"><form:input path="purchasePlan.managementcost"     value="${purchasePlan.managementCost }" class="read-only cash" /></td>
					<td class="td-center"><form:input path="purchasePlan.totalcost"          value="${purchasePlan.totalCost }" class="read-only cash" /></td>
				</tr>								
			</table>
		
		</fieldset>	
		<fieldset class="action" style="text-align: right;margin-top: -15px;">
			<button type="button" id="updateOrderBom" class="DTTT_button">确认采购方案</button>
			<button type="button" id="goBack" class="DTTT_button goBack">返回</button>
		</fieldset>	
		
		<div id="tabs" style="padding: 0px;white-space: nowrap;margin-top: -10px;">
		<ul>
			<li><a href="#tabs-1" class="tabs1">采购方案</a></li>
		</ul>

		<div id="tabs-1" style="padding: 5px;">

		<table id="example" class="display" style="width:100%">
			<thead>				
				<tr>
					<th class="dt-center"  width="100px">物料编号</th>
					<th width="30px">模块<br>编号</th>
					<th width="50px">
						<input type="checkbox" name="selectall" id="selectall" onclick="fnselectall()"/><label for="selectall">全选</label><br>
						<input type="checkbox" name="reverse" id="reverse" onclick="fnreverse()" /><label for="reverse">反选</label></th>
					<th class="dt-center" style="width:100px">更换物料</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" style="width:30px">物料特性</th>
					<th class="dt-center" width="60px">用量</th>
					<th class="dt-center" width="60px">生产需求量</th>
					<th class="dt-center" width="60px">总量</th>
					<th class="dt-center" width="60px">虚拟库存</th>
					<th class="dt-center" width="60px">采购量</th>
					<th class="dt-center" width="60px">领料数量</th>
					<th class="dt-center" width="60px">供应商</th>
					<th class="dt-center" width="60px">本次单价</th>
					<th class="dt-center" width="80px">本次成本</th>
					<th class="dt-center" width="60px">当前价格</th>
				</tr>
			</thead>
			<tbody>
<c:forEach var='bom' items='${planDetail}' varStatus='status'>	
			
	<tr id="tr${status.index}"> 
		<td><span id="materialId${status.index}">
			<a href="###" onClick="doEditMaterial('${status.index}','${bom.materialRecordId }','${bom.materialParentId }')">${bom.materialId }</a></span></td>
		<td><form:input value="${bom.subbomno }" path="planDetailList[${status.index}].subbomno" class="cash" style="width:20px"  /></td>
	    <td>
			<span id="index${status.index}">${bom.rownum }</span><input type="checkbox" id="numCheck" name="numCheck" value="" /></td>
   		<td>
	    	<form:input path="planDetailList[${status.index}].materialid" class="attributeList1"  value="${bom.materialId }" /></td>
	    <td><span id="name${status.index}"></span></td>
	    <!-- 物料特性 -->
	    <td><span id="unit${status.index}">${bom.purchaseType }</span>
	    	<form:hidden value="${bom.purchaseType }" path="planDetailList[${status.index}].purchasetype" /></td>
	    <!-- 用量 -->
	    <td><form:input value="${bom.unitQuantity }" path="planDetailList[${status.index}].unitquantity"  class="num mini"  /></td>
	    <!-- 生产需求量 -->
	    <td><span id="orderQuantity${status.index}">${order.totalQuantity}</span></td>
	    <!-- 总量 -->
	    <td><span id="totalQuantity${status.index}">${bom.manufactureQuantity }</span>
	    	<form:hidden value="${bom.manufactureQuantity }" path="planDetailList[${status.index}].manufacturequantity" /></td>
	     <!-- 当前库存 -->
	    <td><span id="availabelToPromise${status.index}">${bom.availabelToPromise }</span></td>
	     <!-- 建议采购量 -->
	    <td><form:input value="${bom.purchaseQuantity }" 	    
	    		contractQty="${bom.contractQty }" 
	    		arrivalCount="${bom.arrivalCount }" 
	    		stockinQty="${bom.stockinQty }" 
	    		returnQty="${bom.returnQty }" 
	    		path="planDetailList[${status.index}].purchasequantity"  class="num mini"  /></td>
	     <!-- 领料数量 -->
	    <td><form:input value="${bom.totalRequisition }" 
	    		path="planDetailList[${status.index}].totalrequisition"  class="num mini"  /></td>
	     <!-- 供应商 -->
	    <td><form:input value="${bom.supplierId }"  path="planDetailList[${status.index}].supplierid"  class="supplierid short" /></td>
	     <!-- 本次单价 -->
	    <td><form:input path="planDetailList[${status.index}].price"  class="num mini" value="${bom.price }"  contractPrice="${bom.contractPrice }" stockinQty="${bom.stockinQty }"/></td>
	     <!-- 总价 -->
	    <td><span id="totalPrice${status.index}">${bom.totalPrice }</span>
	    	<form:hidden path="planDetailList[${status.index}].totalprice"  value="${bom.totalPrice }" /></td>
	     <!-- 当前价格 -->
	    <td><span id="price${status.index}">${bom.lastPrice }</span>
	    	<form:hidden path="planDetailList[${status.index}].suppliershortname" value="" /></td>	   
	    	<form:hidden path="planDetailList[${status.index}].recordid" value="${bom.recordId }" />
	    	<form:hidden path="planDetailList[${status.index}].purchaseid" value="${bom.purchaseId }" />		
	    	<form:hidden path="planDetailList[${status.index}].contractflag" value="1" />
	</tr>
	<script type="text/javascript">
		var index = '${status.index}';
		var materialName = "${bom.materialName}";
		var planMaterialId = '${bom.planMaterialId}';
		var order = currencyToFloat( '${order.totalQuantity}' );
		var unitQuantity = currencyToFloat( '${bom.unitQuantity}' );
		var totalPrice = currencyToFloat( '${bom.totalPrice}' );
		var stock = currencyToFloat( '${bom.availabelToPromise}' );
		var type = '${bom.purchaseTypeId}';
		var supplierId = '${bom.supplierId}'
		
		var vtotalPrice = '0';//初始化		
		var shortName = getLetters(supplierId);		
		var fPlanQuantity = order * unitQuantity;
		
		//包装件进位
		var materialId = '${bom.materialId }';
		var tmp3 = materialId.substring(0,1);
		if(tmp3 == 'G')
			fPlanQuantity = Math.ceil(fPlanQuantity);
		
		var ftotalQuantity =  currencyToFloat( '${bom.purchaseQuantity}' ) ;
		var price = currencyToFloat ('${bom.price}');
		var vprice = formatNumber(price);

		vtotalPrice = floatToCurrency( price * fPlanQuantity );
		
		//虚拟库存回退处理		
		var contractId = '${bom.contractId}';
		var contractPrice = currencyToFloat('${bom.contractPrice}');
		var contractQty = currencyToFloat('${bom.contractQty}');
		//虚拟库存=虚拟库存 + 本次订单计划用量 - 本次合同,这里做反向操作: 页面显示的虚拟库存 = 实际库存 - 待入库 + 待出库
		var newStock =  floatToCurrency( stock - contractQty + fPlanQuantity );
		//alert("newStock:---"+stock+"---"+contractQty+"---"+ftotalQuantity)
		if(type=="020"){//通用件单独采购
			ftotalQuantity = "0";
		}	
		$('#availabelToPromise'+index).html(floatToCurrency(stock));
		
		$('#totalPrice'+index).html(vtotalPrice);
		$("#planDetailList"+index+"\\.totalprice").val(vtotalPrice);	
		$("#planDetailList"+index+"\\.price").val(vprice);	
		$('#name'+index).html(jQuery.fixedWidth(materialName,30));
		$("#planDetailList"+index+"\\.suppliershortname").val(shortName);
		$("#price"+index).html(vprice);

		//重新计算生产总量
		var total = floatToCurrency(fPlanQuantity);		
		$("#planDetailList"+index+"\\.manufacturequantity").val(total);//总量
		$("#totalQuantity"+index).html(total);//总量 
		
		counter++;
		
	</script>	
</c:forEach>
			</tbody>
		</table>
	 * 1、修改"模块编号",可以调整该模块的显示顺序,同一模块内的自动按照物料编码顺序排列；<br>
	 * 2、"添加单行"后,请修改该行的模块编号；<!-- <span style="color:red">* 3、红色,表示基础BOM有变动</span> -->
	</div>
</div>

</form:form>
</div>
</div>

<script type="text/javascript">

function doShowProduct() {
	var materialId = '${order.materialId}';
	//callProductView(materialId);
	
	var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '500px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
			$('#example').DataTable().ajax.reload(null,false);
		  	return false; 
		}    
	});
	
}


</script>


<script type="text/javascript">

function autocomplete(){
	$('.attributeList1').bind('input propertychange', function() {

		$(this).parent().parent().find('td').eq(3).find('div').text('');
	}); 
	
//物料选择
$(".attributeList1").autocomplete({
	minLength : 2,
	autoFocus : false,
	source : function(request, response) {
		//alert(888);
		$
		.ajax({
			type : "POST",
			url : "${ctx}/business/bom?methodtype=getMaterialPriceList",
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
								materialId : item.materialId,
								supplierId : item.supplierId,
								  minPrice : item.minPrice,
								 lastPrice : item.lastPrice,
								     price : item.price,
								  unitName : item.dicName,
								  parentId : item.materialParentId,
								  recordId : item.materialRecordId,
							  purchaseType : item.purchaseType,
							purchaseTypeId : item.purchaseTypeId,
						availabelToPromise : item.availabelToPromise,
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
		
		//产品编号
		//$(this).parent().find("input:hidden").val(ui.item.materialId);
		
		var $td = $(this).parent().parent().find('td');

		var $oMaterIdV  = $td.eq(0).find("span");
		var $oMatName   = $td.eq(4).find("span");
		var $oType      = $td.eq(5).find("span");
		var $oTypeI     = $td.eq(5).find("input");
		var $oUnitQuty  = $td.eq(6).find("input");
		var $oOrder     = $td.eq(7).find("span");
		var $oTotalQuty = $td.eq(8).find("span");
		var $oStock     = $td.eq(9).find("span");
		var $oPurchase  = $td.eq(10).find("input:text");
		var $oRequition = $td.eq(11).find("input:text");//领料数量
		var $oSupplier  = $td.eq(12).find("input");
		var $oThisPrice = $td.eq(13).find("input");
		var $oTotPriceS = $td.eq(14).find("span");
		var $oTotPriceI = $td.eq(14).find("input");
		var $oSourPrice = $td.eq(15).find("span");
		var $oShortName = $td.eq(15).find("input");
			
		//格式化数据	
		var rowNumber = $(this).parent().parent().index();
		var idLink  = '<a href="###" onClick="doEditMaterial(\''+rowNumber+'\',\''+ui.item.recordId+'\',\''+ui.item.parentId+'\')">'+ui.item.materialId+'</a>';
		var matName = jQuery.fixedWidth(ui.item.name,30);
		var type    = ui.item.purchaseType;
		var typeId  = ui.item.purchaseTypeId;
		var vStock  = floatToCurrency(ui.item.availabelToPromise);
		var supplierId = ui.item.supplierId;		
		var vPrice     = formatNumber(ui.item.price);
		var shortName = getLetters(supplierId);
		
		//显示到页面
		$('.DTFC_Cloned tbody tr:eq('+rowNumber+') td').eq(0).html(idLink);//固定列是clone出来的,特殊处理
		$oMaterIdV.html(idLink);
		$oMatName.html(matName);
		$oType.html(type);
		$oTypeI.val(typeId);
		$oStock.html(vStock)
		$oSupplier.val(supplierId);
		$oShortName.val(shortName);
		$oThisPrice.val(vPrice);
		$oSourPrice.html(vPrice);

		
		if(typeId == "020"){//通用件
			var total = 0;
          	$oPurchase.val(total); //赋给当前页面元素
          	$oTotPriceI.val(total); 
          	$oTotPriceS.text(total);
		}
		if (ui.item.supplierId !="")
			$oSupplier.removeClass('error');
		
		//光标位置
      	$oUnitQuty.val('0');//更换物料后,使用量默认为0
		$oUnitQuty.focus();
		purchasePlanCompute($td,'1');
	},

	
});//物料选择

//供应商选择
$(".supplierid").autocomplete({
	minLength : 0,
	autoFocus : false,
	width  :1000,
	
	search: function( event, ui ) {
		var $tds = $(this).parent().parent().find('td');
		var material = $tds.eq(3).find("input:text").val();
		$('#tmpMaterialId').val(material);

		 if(material==""){
			$().toastmessage('showWarningToast', "请先输入ERP编号。");	
			 return false;
		 }
		
	},

	source : function(request, response) {
		var url = "${ctx}/business/bom?methodtype=getSupplierPriceList";
		var MaterialId = $('#tmpMaterialId').val();
		$
		.ajax({
			type : "POST",
			url : url,
			dataType : "json",
			data : {
				key1 : request.term,
				key2 : MaterialId,
			},
			success : function(data) {
				//alert(777);
				response($
					.map(
						data.data,
						function(item) {

							return {
								label : item.viewList,
								value : item.supplierId,
								id : item.supplierId,
								supplierId : item.supplierId,
							     price : item.price
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

		var $td = $(this).parent().parent().find('td');
		
		var supplierId = ui.item.supplierId;
		var shortName = getLetters(supplierId);
		
		$td.eq(12).find("input").val(ui.item.price);
		$td.eq(14).find("span").html(ui.item.price);
		$td.eq(14).find("input").val(shortName);
		
		purchasePlanCompute($td,'2');
		
	},

	
	});//供应商选择
}

function doEditMaterial(rownum,recordid,parentid) {
	//accessFlg:1 标识新窗口打开
	var url = '${ctx}/business/material?methodtype=detailView&keyBackup=1';
	url = url + '&parentId=' + parentid+'&recordId='+recordid;
	
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '520px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
			var body = layer.getChildFrame('body', index);  //加载目标页面的内容
			var type = body.find('#purchaseType').val();
			
			if(type=="020"){//通用件
				var total = 0;
	          	$('#unit'+rownum).text("通用件");
				$('#planDetailList'+rownum+'\\.purchasequantity').val(total); //赋给当前页面元素
				$('#planDetailList'+rownum+'\\.totalprice').val(total); //赋给当前页面元素
	          	$('#totalPrice'+rownum).text(total);
			}else if(type=="010"){
				var quantity = $('#planDetailList'+rownum+'\\.manufacturequantity').val();
				var price = $('#planDetailList'+rownum+'\\.price').val();
				var totalPrice = floatToCurrency( currencyToFloat(quantity) * currencyToFloat(price) );
				$('#unit'+rownum).text("订购件");
				$('#planDetailList'+rownum+'\\.purchasequantity').val(quantity); //赋给当前页面元素
				$('#planDetailList'+rownum+'\\.totalprice').val(totalPrice); //赋给当前页面元素
	          	$('#totalPrice'+rownum).text(totalPrice);
			}

			layer.close(index);
		}    
	});	
}

function purchasePlanCompute(obj,flg){
	
	 var $td = obj;
		
	var $oMaterIdV  = $td.eq(0).find("span");
	var $oMatName   = $td.eq(4).find("span");
	var $oType      = $td.eq(5).find("span");
	var $oUnitQuty  = $td.eq(6).find("input");
	var $oOrder     = $td.eq(7).find("span");
	var $oTotalQuty = $td.eq(8).find("span");
	var $oTotalQutyI= $td.eq(8).find("input");
	var $oStock     = $td.eq(9).find("span");
	var $oPurchase  = $td.eq(10).find("input:text");
	var $oRequistion= $td.eq(11).find("input:text");//领料数量
	var $oSupplier  = $td.eq(12).find("input");
	var $oThisPrice = $td.eq(13).find("input");
	var $oTotPriceS = $td.eq(14).find("span");
	var $oTotPriceI = $td.eq(14).find("input");

	//开始计算
	var fUnitQuty = currencyToFloat( $oUnitQuty.val() );
	var fOrder    = currencyToFloat( $oOrder.text() );
	var fTotalQuty= fUnitQuty * fOrder;
	var fStock = currencyToFloat( $oStock.text() );
	
	var tmp3 =  $.trim($oMaterIdV.text()).substring(0,1);//包装件进位
	if(tmp3 == 'G')
		fTotalQuty = Math.ceil(fTotalQuty);
	
	if(flg == '1'){
		var fPurchase = setPurchaseQuantity(fStock,fTotalQuty);//建议采购量:自动计算			
		
	}else if (flg == '2'){
		var fPurchase = currencyToFloat( $oPurchase.val() );//建议采购量	:直接输入	
	}
	
	var fPrice    = currencyToFloat($oThisPrice.val());//计算用单价
	var fTotalNew = currencyToFloat(fPrice * fTotalQuty);//合计
	
	//var materialId = $oMaterial.val();	
	var vPurchase = floatToCurrency(fPurchase);	
	var vTotalQuty= floatToCurrency(fTotalQuty);	
	var vTotalNew = floatToCurrency(fTotalNew);
	var vUnitQuty = formatNumber(fUnitQuty);
	var vPrice = formatNumber(fPrice);
			
	//详情列表显示
	$oUnitQuty.val(vUnitQuty)
	$oPurchase.val(vPurchase);
	$oTotalQuty.html(vTotalQuty);
	$oTotalQutyI.val(vTotalQuty);
	$oRequistion.val(vTotalQuty);
	$oTotPriceS.html(vTotalNew);
	$oTotPriceI.val(vTotalNew);
	$oThisPrice.val(vPrice);
	$oSupplier.val($.trim($oSupplier.val()));
	
	costAcount();//成本核算
}

function fnLaborCost(materialId,cost){
	
	var laborCost = '0';
	
	//判断是否是人工成本
	if(materialId != '' && materialId.substring(0,1) == 'H')
		laborCost = cost;
			
	return laborCost;
}

function costAcount(){		
	//产品成本=各项累计
	//人工成本=H带头的ERP编号下的累加
	//材料成本=产品成本-人工成本
	//经管费=经管费率x产品成本
	//核算成本=产品成本+经管费
	var managementCostRate = $('#purchasePlan\\.managementcostrate').val();
	managementCostRate = currencyToFloat(managementCostRate) / 100;//费率百分比转换
	
	var laborCost = laborCostSum();
	var bomCost = bomCostSum();
	
	var fmaterialCost = bomCost - laborCost;
	var productCost = bomCost * 1.1;
	var managementCost = productCost * managementCostRate;
	var ftotalCost = productCost * ( 1 + managementCostRate );

	$('#purchasePlan\\.bomcost').val(floatToCurrency(bomCost));
	$('#purchasePlan\\.productcost').val(floatToCurrency(productCost));
	$('#purchasePlan\\.laborcost').val(floatToCurrency(laborCost));
	$('#purchasePlan\\.materialcost').val(floatToCurrency(fmaterialCost));
	$('#purchasePlan\\.managementcost').val(floatToCurrency(managementCost));
	$('#purchasePlan\\.totalcost').val(floatToCurrency(ftotalCost));
	//alert('labor:'+laborCost+'--product:'+productCost)
	
}

//列合计:总价
function bomCostSum(){

	var sum = 0;
	$('#example tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(14).find("span").text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

//列合计:人工成本
function laborCostSum(){

	var sum = 0;
	$('#example tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(14).find("span").text();
		var materialId = $(this).find("td").eq(3).find("input").val();
		var ftotal = 0;
		//判断是否是人工成本
		if(materialId != '' && materialId.substring(0,1) == 'H'){
			ftotal = currencyToFloat(vtotal);
		}
		
		sum = currencyToFloat(sum) + ftotal;			
	})		
	return sum;
}
</script>
</body>
	
</html>
