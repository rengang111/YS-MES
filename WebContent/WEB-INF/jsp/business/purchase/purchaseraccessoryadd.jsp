<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>配件采购-新建</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
		
	$.fn.dataTable.TableTools.buttons.reset = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {
					
					var t=$('#example').DataTable();
					
					rowIndex = t.row('.selected').index();
					
					if(typeof rowIndex == "undefined"){				
						$().toastmessage('showWarningToast', "请选择要删除的数据。");	
					}else{
						if(confirm("删除后不可恢复，确定要删除吗？")) {

							t.row('.selected').remove().draw();
							$().toastmessage('showNoticeToast', "删除成功。");	
							weightsum();
						}	
					}
						
				}
			
			});
	
	function ajaxRawGroup() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 500,
	        "ordering"  : false,
	       	"dom"		  : 'T<"clear">rt',
			"tableTools" : {

				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

				"aButtons" : [ 
				{
					"sExtends" : "reset",
					"sButtonText" : "删除行"
				}],
			},
			
			"columns" : [ 
			           {"className":"dt-body-center"
					}, {"className":"td-left"
					}, {							
					}, {"className":"td-center"
					}, {"className":"td-right"
					}, {"className":"td-right"	
					}, {"className":"td-right"
					}, {"className":"td-right"				
					}, {"className":"td-left"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				],				
			
		}).draw();
		
		t.on('blur', 'tr td:nth-child(8)',function() {
			
	           $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
			
		t.on('change', 'tr td:nth-child(8)',function() {
			
            var $tds = $(this).parent().find("td");
			
            var $oQuantity  = $tds.eq(7).find("input");
			var $oThisPrice = $tds.eq(9).find("input");
			var $oAmounti   = $tds.eq(10).find("input:hidden");
			var $oAmounts   = $tds.eq(10).find("span");
			
			var fPrice    = currencyToFloat($oThisPrice.val());		
			var fQuantity = currencyToFloat($oQuantity.val());			
			var fTotalOld = currencyToFloat($oAmounti.val());
			
			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vPrice = floatToCurrency(fPrice);	
			var vQuantity = floatToNumber(fQuantity);
			var vTotalNew = floatToCurrency(fTotalNew);
					
			//详情列表显示新的价格
			$oThisPrice.val(vPrice);					
			$oQuantity.val(vQuantity);	
			$oAmounti.val(vTotalNew);	
			$oAmounts.html(vTotalNew);	

			//列合计
			weightsum();
			
		});
			
		t.on('click', 'tr', function() {
			
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

	};//ajaxRawGroup()
	
	$(document).ready(function() {

		//设置光标项目
		$(".supplierid").focus();
		autocomplete();
		
		var date = new Date(shortToday());
		date.setDate(date.getDate()+20);
		$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
		$("#contract\\.purchasedate").val(shortToday());
		
		$("#contract\\.purchasedate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 

		$("#contract\\.deliverydate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});
			
		
		ajaxRawGroup();		
		
		$("#contract\\.purchasedate").change(function(){
			
			var val = $(this).val();
			var date = new Date(val);
			date.setDate(date.getDate()+20);
			$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
		});	
		
		$("#goBack").click(
				function() {

					var PIId=$("#PIId").val();
					var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;				
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					var YSId= $("#YSId").val();
			$('#attrForm').attr("action",
					"${ctx}/business/contract?methodtype=createAcssoryContract"+"&YSId="+YSId);
			$('#attrForm').submit();
		});
				
		//input格式化
		foucsInit();

		weightsum();//总计
	});	
	
	//列合计
	function weightsum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(10).find("span").text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;
			
		})
		var fsum = floatToCurrency(sum);
		$('#weightsum').text(fsum);
		$('#contract\\.total').val(fsum);
		

	}
	
	function doShowMaterial(rownum,recordid,parentid) {
		//var height = setScrollTop();
		//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
		var url = '${ctx}/business/material?methodtype=detailView';
		url = url + '&parentId=' + parentid+'&recordId='+recordid+'&keyBackup=1';
				
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
				var price = body.find('#price').val();
				
				var $oQuantity = $('#detailList'+rownum+'\\.quantity');
				var $oPricei   = $('#detailList'+rownum+'\\.price');
				var $oTotali   = $('#detailList'+rownum+'\\.totalprice');
				
				var fQuantity = currencyToFloat($oQuantity.val());
				var fPrice = currencyToFloat(price);//子窗口传回来的新的单价
				
				var vPrice = formatNumber(fPrice);
				var total = floatToCurrency( fQuantity * fPrice );
				
	          	$oPricei.val(vPrice); //赋给当前页面元素
	          	$('#price'+rownum).text(vPrice); //赋给当前页面元素
	          	$oTotali.val(total); //赋给当前页面元素
	          	$('#total'+rownum).text(total);
	          	
				layer.close(index);
			}    
		});	

	};
	
</script>
<script type="text/javascript">
function autocomplete(){
	
	$(".supplierid").autocomplete({
	
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
			//alert(888);
			var materialId = $('#tmpMaterialId').val();
			//alert(materialId)
			$.ajax({
				type : "POST",
				url : "${ctx}/business/bom?methodtype=getSupplierPriceList",
				dataType : "json",
				data : {
					key1 : request.term,
					key2 : materialId,
				},
				success : function(data) {
					//alert(777);
					response($.map(
						data.data,
						function(item) {
							//alert(item.viewList)
							return {
								label : item.viewList,
								value : item.supplierId,
								id : item.supplierId,
								shortName : item.shortName,
								fullName : item.supplierName,
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

			var $oShortName  = $td.eq(3).find("input");
			var $oQuanty  = $td.eq(7).find("input");
			var $oPriceS  = $td.eq(9).find("span");
			var $oPriceH  = $td.eq(9).find("input:hidden");
			var $oTotalS  = $td.eq(10).find("span");
			var $oTotalH  = $td.eq(10).find("input:hidden");
			
			var fQuanty = currencyToFloat($oQuanty.val());
			var fPrice  = currencyToFloat(ui.item.price);
			
			var vTotal = floatToCurrency( fQuanty * fPrice );
			var vPrice = formatNumber(fPrice);
			
			$oPriceS.text(vPrice);
			$oPriceH.val(vPrice);
			$oTotalS.text(vTotal);
			$oTotalH.val(vTotal);
			$oShortName.val(ui.item.shortName);
			
			weightsum();//列合计
		},

		minLength : 1,
		autoFocus : false,
		width: 500,
	});	
		
}
</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
			
		<input type="hidden" id="goBackFlag"  value=""/>
		<input type="hidden" id="tmpMaterialId"  value=""/>
		<input type="hidden" id="PIId"  value="${PIId }"/>
		<input type="hidden" id="YSId"  value="${order.YSId }"/>
		
		<fieldset>
			<legend> 配件订单采购信息</legend>
			<table class="form" id="table_form">
				<tr>  		
					<td class="label" style="width:120px">PI编号：</td>					
					<td style="width:200px">${ PIId }</td>				
					<td class="label">下单日期：</td>
					<td><form:input path="contract.purchasedate" /></td>
					<td class="label">合同交期：</td>
					<td colspan="3"><form:input path="contract.deliverydate" /></td>
				</tr>						
			</table>			
	</fieldset>
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="insert" class="DTTT_button">生成采购合同</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>	
	
	<fieldset>
	<legend> 物料详情</legend>	
	<div class="list">
	<table id="example" class="display">	
		<thead>
		<tr>
			<th style="width:1px">No</th>
			<th style="width:100px">物料编码</th>
			<th>物料名称</th>
			<th style="width:30px">单位</th>
			<th style="width:50px">当前库存</th>
			<!--  <th style="width:50px">虚拟库存</th>-->
			<th style="width:50px">订单数量</th>
			<th style="width:50px">计划采购</th>
			<th style="width:50px">建议采购</th>
			<th style="width:50px">供应商</th>
			<th style="width:50px">单价</th>
			<th style="width:50px">总价</th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="detail" items="${detail}" varStatus='status' >	
				<tr>
					<td></td>
					<td>
						<a href="###" onClick="doShowMaterial('${status.index}','${detail.materialRecordId}','${detail.materialParentId}')">${detail.materialId}</a>
						<form:hidden path="detailList[${status.index}].materialid" value="${detail.materialId}"  class="materialid"/></td>								
					<td><span id="name${status.index}">${detail.materialName}</span></td>					
					<td>${ detail.unit }
						<form:hidden path="contractList[${status.index}].subid" value="${ detail.shortName }"/></td>
					<td>${ detail.quantityOnHand }</td>				
					<td>${ detail.quantity }</td>			
					<td>${ detail.totalQuantity }</td>	
					<td><form:input path="detailList[${status.index}].quantity" value="" class="quantity num mini"/></td>
					<td><form:input path="contractList[${status.index}].supplierid" value="${ detail.lastSupplierId }" class="supplierid mini"/></td>						
					<td><span id="price${status.index}">${detail.lastPrice}</span>
						<form:hidden  path="detailList[${status.index}].price" value="${detail.lastPrice}"  class="price " /></td>
					<td><span id="total${status.index}">${ detail.totalPrice}</span>
						<form:hidden  path="detailList[${status.index}].totalprice" value="${detail.totalPrice}" class="total"/></td>
				</tr>	
				<script type="text/javascript">
					var	index = '${status.index}';
					var stock = '${ detail.quantityOnHand }';//库存
					var order = '${ detail.totalQuantity }';//需求量
					var price = currencyToFloat('${detail.lastPrice}');
					var quan = setPurchaseQuantity(stock,order);
					
					var total = floatToCurrency( price * quan );
					
					$("#detailList"+index+"\\.quantity").val(quan);
					$("#detailList"+index+"\\.totalprice").val(total);
					$("#total"+index).text(total);
					
				</script>		
			</c:forEach>
			
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td class="td-right">合计:</td>
				<td class="td-right" style="padding-right: 2px;"><span id=weightsum></span>
					<form:hidden path="contract.total"/></td>
			</tr>
		</tfoot>
	</table>
	</div>
	</fieldset>	
		
</form:form>

</div>
</div>
</body>

	
</html>
