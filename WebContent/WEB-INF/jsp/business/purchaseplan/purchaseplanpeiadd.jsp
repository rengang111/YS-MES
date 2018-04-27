<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>配件订单采购方案--新规做成</title>
<%@ include file="../../common/common2.jsp"%>
  	
<script type="text/javascript">

	
	$(document).ready(function() {		

		baseBomView();//基础BOM
		
		autocomplete();		
		
		$(".goBack").click(
				function() {

			var PIId = '${order.PIId}';
			var YSId = '${order.YSId}';
			var url = '${ctx}/business/purchasePlan?keyBackup=' + YSId;
	
			location.href = url;		
		});
		
		$("#createOrderBom").click(
				function() {
			
			$('#createOrderBom').attr("disabled","true").removeClass("DTTT_button");
			var materialFlag = $("#materialFlag").val();//区分自制件,包装品,订购件
			var materialId='${order.materialId}';
			var YSId ="${order.YSId}";
			var quantity ="${order.quantity}";
			$('#attrForm').attr("action",
					"${ctx}/business/purchasePlan?methodtype=purchasePlanPeiAdd"
							+"&YSId="+YSId
							+"&materialId="+materialId
							+"&materialFlag="+materialFlag
							+"&quantity="+quantity);
			
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
	
	});

	
	function baseBomView() {

		var scrollHeight = $(window).height() - 200;
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
	       // "sScrollY"	: scrollHeight,
	       // "sScrollX": true,
	       //	"fixedColumns":   { leftColumns: 1 },
			"dom" 		: '<"clear">rt',
			//"sAjaxSource" : "${ctx}/business/bom?methodtype=getBaseBom&materialId="+materialId,			
	       	"language": {
	       		"url":"${ctx}/plugins/datatables/chinese.json"
	       	},
			"columns": [
				{"className" : 'td-center', "defaultContent" : ''},//0
				{"className" : 'td-left'},//1
				{"defaultContent" : ''},//2.物料名称
				{"className" : 'td-right'},//3.生产需求量:订单数量
				{"className" : 'td-right'},//4.当前库存(虚拟库存):物料
				{"className" : 'td-right'},//5.建议采购量:输入
				{"defaultContent" : '0'},//6.供应商,可修改:baseBom
				{"className" : 'td-right', "defaultContent" : ''},//7.本次单价,可修改:baseBom
				{"className" : 'td-right', "defaultContent" : ''},//8.总价=本次单价*采购量
				{"className" : 'td-right', "defaultContent" : ''},//9.当前价格:baseBom
			 ],
		
			"columnDefs":[
				   		
	    		{
					"visible" : false,
					"targets" : [ ]
				}	          
	        ]
	     
		});
		
		t.on('change', 'tr td:nth-child(6),tr td:nth-child(7),tr td:nth-child(8)',function() {
					
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
		<input type="hidden" id="materialFlag"  value="${materialFlag }"/>
		<form:hidden path="purchasePlan.ysid"  value="${order.peiYsid}" />
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr>
					<td class="label"><label>ＰＩ编号：</label></td>
					<td>${order.PIId}</td>
					
					<td class="label"><label>客户名称：</label></td>
					<td>${order.customerFullName}</td>
				</tr>							
			</table>
		</fieldset>
		<fieldset class="action" style="text-align: right;margin-top: -15px;">
			<button type="button" id="createOrderBom" class="DTTT_button">确认采购方案</button>
			<button type="button" id="goBack" class="DTTT_button goBack">返回</button>
		</fieldset>	
		<script type="text/javascript">

		</script>
		<div id="tabs" style="padding: 0px;white-space: nowrap;margin-top: -10px;">
		<ul>
			<li><a href="#tabs-1" class="tabs1">采购方案</a></li>
		</ul>

		<div id="tabs-1" style="padding: 5px;">

		<table id="example" class="display" style="width:100%">
			<thead>				
				<tr>
					<th width="30px">No</th>
					<th class="dt-center"  width="100px">物料编号</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" width="60px">订单数量</th>
					<th class="dt-center" width="60px">虚拟库存</th>
					<th class="dt-center" width="60px">建议采购量</th>
					<th class="dt-center" style="width:80px">供应商</th>
					<th class="dt-center" width="60px">本次单价</th>
					<th class="dt-center" width="80px">总价</th>
					<th class="dt-center" width="60px">当前价格</th>
				</tr>
			</thead>
			<tbody>
<c:forEach var='bom' items='${bomDetail}' varStatus='status'>	
			
	<tr> 
		 <td><span id="index${status.index}">${bom.rownum }</span></td>
		<td>
			<a href="###" onClick="doEditMaterial('${status.index}','${bom.materialRecordId }','${bom.materialParentId }')">${bom.materialId }</a>
			<form:hidden path="planDetailList[${status.index}].materialid" value="${bom.materialId}" /></td>
	    <td><span id="name${status.index}"></span></td>
	    <td><span id="totalQuantity${status.index}">${bom.totalQuantity}</span>
	    	<form:hidden path="planDetailList[${status.index}].manufacturequantity" value="${bom.totalQuantity}" /></td>
	    <td><span id="availabelToPromise${status.index}">${bom.availabelToPromise }</span></td>
	    <td><form:input path="planDetailList[${status.index}].purchasequantity"  class="num mini" value="" /></td>
	    <td><form:input path="planDetailList[${status.index}].supplierid"  class="supplierid short" value="${bom.supplierId }" /></td>
	    <td><form:input path="planDetailList[${status.index}].price"  class="num mini" value="" /></td>
	    <td><span id="totalPrice${status.index}"></span>
	    	<form:hidden path="planDetailList[${status.index}].totalprice"  class="num short" value="" /></td>
	    <td><span id="price${status.index}">${bom.price }</span>
	    	<form:hidden path="planDetailList[${status.index}].suppliershortname" value="" /></td>
	    
	    	<form:hidden path="planDetailList[${status.index}].purchasetype" value="${bom.purchaseTypeId }" />	    	
	    	<form:hidden path="planDetailList[${status.index}].contractflag" value="1" />
	</tr>
	<script type="text/javascript">
		var index = '${status.index}';
		var materialName = '${bom.materialNameView}';
		var price = '${bom.price}';
		var quantity = '${bom.quantity}';
		var stock = '${bom.availabelToPromise}';
		var type = '${bom.purchaseTypeId}';
		var supplierId = '${bom.supplierId}'

		var totalQuantity = floatToCurrency('${bom.totalQuantity}');
		//虚拟库存回退处理		
		var contractId = '${bom.contractId}';
		var contractPrice = currencyToFloat('${bom.contractPrice}');
		var contractQty = currencyToFloat('${bom.contractQty}');
		var fPlanQuantity =  currencyToFloat( '${bom.manufactureQuantity}' ) ;
		//虚拟库存=实际库存 + 待入库 - 待出库,这里做反向操作: 页面显示的虚拟库存 = 实际库存 - 待入库 + 待出库
		var fpurchase = setPurchaseQuantity(stock,totalQuantity);
		var vpurchase = floatToCurrency(fpurchase);
		var totalPrice = floatToCurrency( currencyToFloat(price) * fpurchase );
		var vprice = formatNumber(price);
		var shortName = getLetters(supplierId);

		$('#name'+index).html(jQuery.fixedWidth(materialName,30));
		$("#planDetailList"+index+"\\.manufacturequantity").val(totalQuantity);
		$('#totalPrice'+index).html(totalPrice);
		$("#planDetailList"+index+"\\.totalprice").val(totalPrice);
		$("#planDetailList"+index+"\\.price").val(vprice);
		$("#planDetailList"+index+"\\.purchasequantity").val(vpurchase);
		$("#planDetailList"+index+"\\.suppliershortname").val(shortName);
		$("#price"+index).html(vprice);
		
	</script>	
</c:forEach>
			</tbody>
		</table>
	</div>
</div>

</form:form>
</div>
</div>


<script type="text/javascript">

function autocomplete(){
	$('.attributeList1').bind('input propertychange', function() {

		$(this).parent().parent().find('td').eq(3).find('div').text('');
	}); 
	

//供应商选择
$(".supplierid").autocomplete({
	minLength : 0,
	autoFocus : false,
	width  :1000,
	
	search: function( event, ui ) {
		var $tds = $(this).parent().parent().find('td');
		var material = $tds.eq(1).find("input").val();
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
		
		$td.eq(7).find("input").val(ui.item.price);
		$td.eq(9).find("span").html(ui.item.price);
		$td.eq(9).find("input").val(shortName);
		
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
			}

			layer.close(index);
		}    
	});	
}

function purchasePlanCompute(obj,flg){
	
	var $td = obj;
		
	var $oPurchase  = $td.eq(5).find("input");
	var $oSupplier  = $td.eq(6).find("input");
	var $oThisPrice = $td.eq(7).find("input");
	var $oTotPriceS = $td.eq(8).find("span");
	var $oTotPriceI = $td.eq(8).find("input");

	//开始计算
	var fPurchase = currencyToFloat($oPurchase.val());//建议采购量	:直接输入
	var fPrice    = currencyToFloat($oThisPrice.val());//计算用单价
	var fTotalNew = currencyToFloat(fPrice * fPurchase);//合计
	
	var vPurchase = floatToCurrency(fPurchase);	
	var vTotalQuty= floatToCurrency(fPurchase);	
	var vTotalNew = floatToCurrency(fTotalNew);
	var vPrice = formatNumber(fPrice);
			
	//详情列表显示
	$oPurchase.val(vPurchase);
	$oTotPriceS.html(vTotalNew);
	$oTotPriceI.val(vPrice);
	$oThisPrice.val(vPrice);
	
}

</script>
</body>
	
</html>
