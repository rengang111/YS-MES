<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>日常采购--新规做成</title>
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
'<td><input type="text" name="planDetailList['+counter+'].supplierid" id="planDetailList'+counter+'.supplierid"  class="supplierid short"/></td>',
'<td class="td-right"><input type="text" name="planDetailList['+counter+'].price"      id="planDetailList'+counter+'.price" class="num mini" /></td>',
'<td class="td-right"><span id="totalPrice'+counter+'"></span><input type="hidden"   name="planDetailList['+counter+'].totalprice" id="planDetailList'+counter+'.totalprice"/></td>',
'<td class="td-right"><span></span><input type="hidden"   name="planDetailList['+counter+'].suppliershortname" id="planDetailList'+counter+'.suppliershortname"/></td>',
					
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
			var backFlag = $("#backFlag").val();
			
				var url = '${ctx}/business/purchasePlan?methodtype=purchaseRoutineInit';
			
	
			location.href = url;		
		});
		
		$("#createOrderBom").click(
				function() {

			var materialId='${order.materialId}';
			var YSId ="${order.YSId}";
			var quantity ="${order.quantity}";
			var backFlag = $("#backFlag").val();
			$('#attrForm').attr("action",
					"${ctx}/business/purchasePlan?methodtype=purchaseRoutineAdd&YSId="
							+YSId+"&materialId="+materialId+"&quantity="+quantity+"&backFlag="+backFlag);
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
	
		autocomplete();
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
	        "sScrollY"	: scrollHeight,
	        "sScrollX": true,
	       	//"fixedColumns":   { leftColumns: 1 },
			"dom" 		: '<"clear">',
			//"sAjaxSource" : "${ctx}/business/bom?methodtype=getBaseBom&materialId="+materialId,	
			
			/*
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
			*/
	       	"language": {
	       		"url":"${ctx}/plugins/datatables/chinese.json"
	       	},
			"columns": [
				{"className" : 'td-left'},//0
				{"defaultContent" : ''},//1.物料名称
				{"className" : 'td-center', "defaultContent" : ''},//2.单位:物料
				{"className" : 'td-right'},//3.安全库存
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
		
		/*
		t.on('blur', 'tr td:nth-child(4),tr td:nth-child(7)',function() {
			
			var currValue = $(this).find("input:text").val().trim();

	        $(this).find("input:text").removeClass('bgwhite');
	        
	        if(currValue =="" ){
	        	
	        	 $(this).find("input:text").addClass('error');
	        }else{
	        	 $(this).find("input:text").addClass('bgnone');
	        }
			
		});

		
		t.on('blur', 'tr td:nth-child(7)',function() {
			
	       $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
		*/		

		
		t.on('change', 'tr td:nth-child(6),tr td:nth-child(8)',function() {
					
	        var $td = $(this).parent().find("td");
	        purchasePlanCompute($td);
	       
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
		
		
		<fieldset>
		<legend> 采购信息</legend>
		<div class="action" style="text-align: right;width: 50%;float: right;">
			<button type="button" id="createOrderBom" class="DTTT_button">确认并生成采购合同</button>
			<button type="button" id="goBack" class="DTTT_button goBack">返回</button>
		</div>
		<table  style="width:300px;float:left">
			<tr> 				
				<td class="label" style="width:100px;">关联耀升编号：</td>					
				<td style="width:150px;">
				<form:input path="purchasePlan.ysid"  value="" style="text-transform:uppercase;"/></td>
				
			</tr>						
		</table>
		<div class="list" style="margin-top: 40px;">
		<table id="example" class="display" style="width:100%">
			<thead>				
				<tr>
					<th width="100px">物料编号</th>
					<th >物料名称</th>
					<th width="30px">单位</th>
					<th width="60px">安全库存</th>
					<th width="60px">当前库存</th>
					<th width="60px">建议采购量</th>
					<th style="width:80px">供应商</th>
					<th width="60px">本次单价</th>
					<th width="80px">总价</th>
					<th width="60px">当前价格</th>
				</tr>
			</thead>
			<tbody>
<c:forEach var='bom' items='${material}' varStatus='status'>	
			
	<tr> 
		<td><a href="###" onClick="doEditMaterial('${status.index}','${bom.recordId }','${bom.parentId }')">${bom.materialId }</a>
		<form:hidden path="planDetailList[${status.index}].materialid"  value="${bom.materialId }" /></td>
	    <td><span id="name${status.index}"></span></td>
	    <td><span id="unit${status.index}">${bom.dicName }</span></td>
	    <td><span id="totalQuantity${status.index}">${bom.safetyInventory }</span></td>
	    <td><span id="availabelToPromise${status.index}">${bom.availabelToPromise }</span></td>
	    <td><form:input path="planDetailList[${status.index}].purchasequantity"  class="num mini" value="" /></td>
	    <td><form:input path="planDetailList[${status.index}].supplierid"  class="supplierid short" value="${bom.supplierId }" /></td>
	    <td><form:input path="planDetailList[${status.index}].price"  class="num mini" value="${bom.price }" /></td>
	    <td><span id="totalPrice${status.index}"></span>
	    	<form:hidden path="planDetailList[${status.index}].totalprice"  class="num short" value="" /></td>
	    <td><span id="price${status.index}">${bom.price }</span></td>
	    
	    	<form:hidden path="planDetailList[${status.index}].purchasetype" value="${bom.purchaseType }" />
	    	<form:hidden path="planDetailList[${status.index}].suppliershortname" value="" />
	</tr>
	<script type="text/javascript">
		var index = '${status.index}';
		
		var materialName = '${bom.materialName}';
		var price = '${bom.price}';
		var quantity = '${bom.quantity}';
		//var order = '${order.totalQuantity}';
		var stock = '${bom.availabelToPromise}';
		var safe = '${bom.safetyInventory}';
		var type = '${bom.purchaseTypeId}';
		var supplierId = '${bom.supplierId}'
		
		var fpurchase = currencyToFloat(safe) - currencyToFloat(stock);
		//var fpurchase = setPurchaseQuantity(stock,totalQuantity);
		if(type=="020"){//通用件单独采购
			fpurchase = "0";
		}
		var totalPrice = floatToCurrency( currencyToFloat(price) * fpurchase );
		var vpurchase = floatToCurrency(fpurchase);
		var shortName = getLetters(supplierId);
		
		$('#name'+index).html(jQuery.fixedWidth(materialName,30));
		//$('#totalQuantity'+index).html(totalQuantity);
		//$("#planDetailList"+index+"\\.manufacturequantity").val(totalQuantity);
		$('#totalPrice'+index).html(totalPrice);
		$("#planDetailList"+index+"\\.totalprice").val(totalPrice);
		$("#planDetailList"+index+"\\.purchasequantity").val(vpurchase);
		$("#planDetailList"+index+"\\.suppliershortname").val(shortName);

		
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
		var $oTotalQuty = $td.eq(8).find("span");
		var $oStock     = $td.eq(9).find("span");
		var $oPurchase  = $td.eq(10).find("input");
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
		var vPrice     = float4ToCurrency(ui.item.price);
		var shortName = getLetters(supplierId);

		//显示到页面
		$('.DTFC_Cloned tbody tr:eq('+rowNumber+') td').eq(0).html(idLink);//固定列是clone出来的,特殊处理
		//s = $('.DTFC_Cloned tr:eq('+rowNumber+') td').eq(0).html();
		//alert("rowNumber:"+rowNumber+"---"+s);
		$oMaterIdV.html(idLink);
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
		var material = $tds.eq(0).find("input").val();
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
		$td.eq(6).find("input").val(shortName);
		
		purchasePlanCompute($td);
		
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
			
			layer.close(index);
		}    
	});	
}

function purchasePlanCompute(obj){
	
	 var $td = obj;
		
	var $oPurchase  = $td.eq(5).find("input");
	var $oThisPrice = $td.eq(7).find("input");
	var $oTotPriceS = $td.eq(8).find("span");
	var $oTotPriceI = $td.eq(8).find("input");

	//开始计算
	var fPurchase = currencyToFloat( $oPurchase.val() );//建议采购量	:直接输入	
	
	var fPrice    = currencyToFloat($oThisPrice.val());//计算用单价
	var fTotalNew = currencyToFloat(fPrice * fPurchase);//合计
		
	var vPurchase = floatToCurrency(fPurchase);		
	var vTotalNew = floatToCurrency(fTotalNew);
			
	//详情列表显示
	$oPurchase.val(vPurchase);
	$oThisPrice.val(float5ToCurrency(fPrice));
	$oTotPriceS.html(vTotalNew);
	$oTotPriceI.val(vTotalNew);
	
}




</script>

<script type="text/javascript">
function autocomplete(){
	
	$("#purchasePlan\\.ysid").autocomplete({
		minLength : 2,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/order?methodtype=getYsidList",
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
									label : item.YSId +" | "+ item.materialId +" | "+ item.materialName,
									value : item.YSId,
								}
							}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
					//alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},
	
	});
}

</script>

</body>
	
</html>
