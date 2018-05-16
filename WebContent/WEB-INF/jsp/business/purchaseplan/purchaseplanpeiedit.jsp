<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>配件订单采购方案--编辑</title>
<%@ include file="../../common/common2.jsp"%>
  	
<script type="text/javascript">

		
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
			var peiYsid ="${order.peiYsid}";
			var quantity ="${order.quantity}";
			var backFlag = $("#backFlag").val();
			
			$('#attrForm').attr("action","${ctx}/business/purchasePlan?methodtype=purchasePlanUpdatePei"
					+"&YSId="+YSId
					+"&peiYsid="+peiYsid
					+"&materialId="+materialId
					+"&quantity="+quantity
					+"&backFlag="+backFlag);
			
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
	       //	"fixedColumns":   { leftColumns: 1 },
			"dom" 		: '<"clear">rt',			
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
		
		t.on('change', 'tr td:nth-child(11)',function() {

			var contractQty   = currencyToFloat( $(this).find("input:text").attr("contractQty") );
			var arrivalCount  = currencyToFloat( $(this).find("input:text").attr("arrivalCount") );
			var stockinQty    = currencyToFloat( $(this).find("input:text").attr("stockinQty") );
			var returnQty     = currencyToFloat( $(this).find("input:text").attr("returnQty") );

	     
		});

		t.on('change', 'tr td:nth-child(13)',function() {
			
			var contractPrice  = $(this).find("input:text").attr("contractPrice");
			var stockinQty  = $(this).find("input:text").attr("stockinQty");
	        var quantity = currencyToFloat(stockinQty);

	        //检查是否已生成合同
	        if(quantity > 0){
	        	$(this).find("input:text").val(contractPrice);	        	
	        	$().toastmessage('showWarningToast', "该物料已入库，不能修改单价。");
	        }
			
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
		<input type="hidden" id="backFlag"  value="${backFlag }"/>
		<form:hidden path="purchasePlan.purchaseid"  value="${purchasePlan.purchaseId}" />
		<form:hidden path="purchasePlan.recordid"  value="${purchasePlan.planRecordId}" />
		<form:hidden path="purchasePlan.ysid"  value="${order.YSId}" />
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr>
					<td class="label"><label>ＰＩ编号：</label></td>
					<td>${order.PIId}</td>

					<td class="label"><label>订单数量：</label></td>
					<td><span id="quantity">${order.quantity}</span>（${order.unit}）</td>
						
					<td class="label"><label>客户名称：</label></td>
					<td>${order.customerFullName}</td>
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
<c:forEach var='bom' items='${planDetail}' varStatus='status'>	
			
	<tr id="tr${status.index}"> 	
		<td><span id="index${status.index}">${bom.rownum }</span></td>
		<td>
			<a href="###" onClick="doEditMaterial('${status.index}','${bom.materialRecordId }','${bom.materialParentId }')">${bom.materialId }</a>
			<form:hidden path="planDetailList[${status.index}].materialid" value="${bom.materialId}" /></td>
	    <td><span id="name${status.index}"></span></td>
	    <!-- 总量 -->
	    <td>${bom.manufactureQuantity}
	    	<form:hidden path="planDetailList[${status.index}].manufacturequantity" value="${bom.manufactureQuantity}" /></td>
	     <!-- 当前库存 -->
	    <td><span id="availabelToPromise${status.index}">${bom.availabelToPromise }</span></td>
	     <!-- 建议采购量 -->
	    <td><form:input value="${bom.purchaseQuantity }" 	    
	    		contractQty="${bom.contractQty }" 
	    		arrivalCount="${bom.arrivalCount }" 
	    		stockinQty="${bom.stockinQty }" 
	    		returnQty="${bom.returnQty }" 
	    		path="planDetailList[${status.index}].purchasequantity"  class="num mini"  /></td>
	     <!-- 供应商 -->
	    <td><form:input value="${bom.supplierId }"  path="planDetailList[${status.index}].supplierid"  class="supplierid short" /></td>
	     <!-- 本次单价 -->
	    <td><form:input path="planDetailList[${status.index}].price"  class="num mini" value="${bom.price }"  contractPrice="${bom.contractPrice }" stockinQty="${bom.stockinQty }"/></td>
	     <!-- 总价 -->
	    <td><span id="totalPrice${status.index}">${bom.totalPrice }</span>
	    	<form:hidden path="planDetailList[${status.index}].totalprice"  value="${bom.totalPrice }" /></td>
	     <!-- 当前价格 -->
	     <td><span id="price${status.index}">${bom.price }</span>
	    	<form:hidden path="planDetailList[${status.index}].suppliershortname" value="" /></td>
	    
	    	<form:hidden path="planDetailList[${status.index}].recordid" value="${bom.recordId }" />	    	
	    	<form:hidden path="planDetailList[${status.index}].contractflag" value="1" />
	</tr>
	<script type="text/javascript">
		var index = '${status.index}';
		var materialName = '${bom.materialName}';
		var price = '${bom.price}';
		var quantity = '${bom.quantity}';
		var stock = '${bom.availabelToPromise}';
		var type = '${bom.purchaseTypeId}';
		var supplierId = '${bom.supplierId}'
		var shortName = getLetters(supplierId);		
	
		var fpurchase = currencyToFloat('${bom.purchaseQuantity}');
		var fprice = currencyToFloat('${bom.price}');
		//虚拟库存回退处理

		var totalPrice = floatToCurrency( fprice * fpurchase );
		
		var vprice = formatNumber(price);
		var vpurchase = floatToCurrency(fpurchase);
	
		$('#name'+index).html(jQuery.fixedWidth(materialName,30))
		$('#totalPrice'+index).html(totalPrice);
		$("#planDetailList"+index+"\\.totalprice").val(totalPrice);
		
		$("#price"+index).html(vprice);
		$("#planDetailList"+index+"\\.price").val(vprice);
		
		$("#planDetailList"+index+"\\.purchasequantity").val(vpurchase);
		$("#planDetailList"+index+"\\.suppliershortname").val(shortName);
		
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
		var $oUnitQuty  = $td.eq(6).find("input");
		var $oOrder     = $td.eq(7).find("span");
		var $oTotalQuty = $td.eq(8).find("span");
		var $oStock     = $td.eq(9).find("span");
		var $oPurchase  = $td.eq(10).find("input:text");
		var $oSupplier  = $td.eq(11).find("input");
		var $oThisPrice = $td.eq(12).find("input");
		var $oTotPriceS = $td.eq(13).find("span");
		var $oTotPriceI = $td.eq(13).find("input");
		var $oSourPrice = $td.eq(14).find("span");
		var $oShortName = $td.eq(14).find("input");
	
		//开始计算
		//var fPrice    = currencyToFloat(ui.item.price);//计算用单价
		//var fQuantity = currencyToFloat($oQuantity.val());//计算用数量
		//var fTotalNew = currencyToFloat(fPrice * fQuantity);//合计
		
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
		//s = $('.DTFC_Cloned tr:eq('+rowNumber+') td').eq(0).html();
		//alert("rowNumber:"+$(this).index()+"---"+s);
		$oMaterIdV.html(idLink);
		//$(this).parent().parent().find("td").eq(0).find("span").html(idLink);
		$oMatName.html(matName);
		$oType.html(type);
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
