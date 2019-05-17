<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>采购合同-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function ajaxRawGroup() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
	        "bAutoWidth":false,
	        "ordering"  : false,
			"dom" : '<"clear">rt',			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {"className":"td-left"
					}, {								
					}, {"className":"td-center"
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				],
				"columnDefs":[
				    {
						"visible" : false,
						"targets" : [7 ]
					}
				]
			
		}).draw();
		
		t.on('blur', 'tr td:nth-child(5),tr td:nth-child(6)',function() {
			
	          // $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
			
		t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {
			
            var $tds = $(this).parent().find("td");
			
            var $oQuantity  = $tds.eq(4).find("input[type=text]");
			var $oThisPrice = $tds.eq(5).find("input[type=text]");
			var $oAmounti   = $tds.eq(6).find("input:hidden");
			var $oAmounts   = $tds.eq(6).find("span");
			
			var fPrice    = currencyToFloat($oThisPrice.val());		
			var fQuantity = currencyToFloat($oQuantity.val());			
			var fTotalOld = currencyToFloat($oAmounti.val());
			
			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vPrice = formatNumber(fPrice);	
			var vQuantity = floatToCurrency(fQuantity);
			var vTotalNew = floatToCurrency(fTotalNew);
					
			//详情列表显示新的价格
			$oThisPrice.val(vPrice);					
			$oQuantity.val(vQuantity);	
			$oAmounti.val(vTotalNew);	
			$oAmounts.html(vTotalNew);	

			//列合计
			weightsum();
			
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
		
		$('#contract\\.purchaser').val('${contract.purchaserId}');
		
		var productid = '${ contract.productId }';
		if(productid == null || productid == ""){
			$('#ysid00').attr("style","display:none");			
		}

		$('#contract\\.memo').val(replaceTextarea('${contract.memo}'));
		
		ajaxRawGroup();			
		
		var deliverydate = '${contract.purchaseDate}';
		if(deliverydate == "" || deliverydate == null){
		var date = new Date(shortToday());
			date.setDate(date.getDate()+20);
			$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
			$("#contract\\.purchasedate").val(shortToday());
		}
		
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
		
		
		$('#example').DataTable().columns.adjust().draw();		
		
		$("#goBack").click(
				function() {
					var contractId = '${ contract.contractId }';
					var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {	
					var purchaser = $('#contract\\.purchaser').val();
					if(purchaser == '' || purchaser == null){
						$().toastmessage('showWarningToast', "请选择采购员。");	
						return;
					}
					$('#insert').attr("disabled","true").removeClass("DTTT_button");	
			$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=update");
			$('#attrForm').submit();
		});		

		$("#contract\\.purchasedate").change(function(){
			
			var val = $(this).val();
			var date = new Date(val);
			date.setDate(date.getDate()+20);
			$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
		});	
		
		$("#contract\\.supplierid").change(function(){
			
			var YSId = '${order.YSId }';
			var supplierId = $(this).val();
			var url = '${ctx}/business/contract?methodtype=edit&YSId='+YSId+"&supplierId="+supplierId;
			location.href = url;	
		});	
		
		$(".imgbtn").click(function(){
			
			var input = $(this).parent().find("input[type=text]");
			var span = $(this).parent().find("span");
			if(input.is(":hidden")){
				input.show();
				span.hide();
			}else{
				input.hide();
				span.show();
				input.val(span.text());
			}
			
			return false;
		});	
		
		//input格式化
		foucsInit();
		
		$(".short").hide();
		
		//列合计
		weightsum();

	});	
	
	//列合计
	function weightsum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(6).find("span").text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;
			
		})
		var fsum = floatToCurrency(sum);
		$('#weightsum').text(fsum);
		$('#contract\\.total').val(fsum);
		

	}
	
	function doShowMaterial(recordid,parentid) {
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
			 // if(confirm('确定要关闭么')){
			    layer.close(index)
			 // }
			  $('#baseBomTable').DataTable().ajax.reload(null,false);
			  return false; 
			}    
		});		

	};
	
	function doDeleteContract(obj){
		var txt = myTrim($(obj).text());

		if(confirm(txt+'后不能恢复,确定要'+txt+'吗?')){
			$(obj).parent().parent().remove();
			//$().toastmessage('showWarningToast', '请保存数据。');
		}
		weightsum();
	}
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
			
		<input type="hidden" id="methodtype" value="${methodtype }" />
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
				<tr id="ysid00">		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="150px">${contract.YSId }
						<form:hidden path="contract.recordid" value="${contract.contractRecordId }"/>
						<form:hidden path="contract.ysid" value="${contract.YSId }"/></td>
									
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td width="150px">&nbsp;${ contract.productId }</td>
						
					<td class="label" width="100px"><label>产品名称：</label></td>
					<td>&nbsp;${ contract.productName } </td>
				</tr>	
				<tr>
					<td class="label"><label>供应商：</label></td>					
					<td colspan="5">${ contract.supplierId }（${ contract.shortName }）${ contract.fullName }
						<form:hidden path="contract.supplierid" value="${contract.supplierId }"/></td>
									
					<td class="label"><label>采购员：</label></td>
					<td>
						<form:select path="contract.purchaser" style="width: 100px;">
							<form:options items="${purchaserList}" 
							  itemValue="key" itemLabel="value" />
						</form:select></td>
				</tr>	
				<tr> 		
					<td class="label" width="100px"><label>采购合同编号：</label></td>					
					<td width="150px">${ contract.contractId }
						<form:hidden path="contract.contractid" value="${contract.contractId }"/></td>
					<td class="label" width="100px"><label>下单日期：</label></td>
					<td width="100px">
						<form:input path="contract.purchasedate" value="${ contract.purchaseDate }"/></td>
					<td class="label" width="100px"><label>合同交期：</label></td>
					<td width="100px">
						<form:input path="contract.deliverydate" value="${ contract.deliveryDate }"/></td>
					<td class="label" width="100px"><label>退税率：</label></td>
					<td>
						<form:select path="contract.taxrate" style="width: 100px;">
							<form:options items="${rebateRateList}" 
							  itemValue="key" itemLabel="value" />
						</form:select></td>
				</tr>									
			</table>
			
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="insert" class="DTTT_button">保存</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>			
	<fieldset style="margin-top: -30px;">
	<legend> 合同详情</legend>	
		<div class="list">
		<table id="example" class="display" style="width:100%">	
			<thead>
			<tr>
				<th style="width:1px">No</th>
				<th style="width:120px">物料ERP编码</th>
				<th>物料名称</th>
				<th style="width:30px">单位</th>
				<th style="width:160px">数量</th>
				<th style="width:160px">单价</th>
				<th style="width:70px">总价</th>
				<td style="width:50px">操作</td>
			</tr>
			</thead>		
			<tbody>
				<c:forEach var="detail" items="${detail}" varStatus='status' >	
					<c:if test="${detail.deleteFlag eq '0'}">
					<tr>
						<td></td>
						<td>
							<a href="###" onClick="doShowMaterial('${detail.materialRecordId}','${detail.materialParentId}')">${detail.materialId}</a>
							<form:hidden path="detailList[${status.index}].materialid" value="${detail.materialId}" /></td>								
						
						<td>
							<textarea id="detailList${status.index}.description" name="detailList[${status.index}].description" 
								style="width: 500px; height: 150px;font-size: 11px;">${detail.description}</textarea></td>					
						
						<td>${ detail.unit }</td>
						
						<td><span>${detail.quantity}</span>
							<form:input path="detailList[${status.index}].quantity" value="${detail.quantity}" class="num short" />
							<input type="image" name="quantityBtn${status.index}" src="${ctx}/images/action_edit.png" class="imgbtn" style="border: 0;" ></td>							
						
						<td><span>${detail.price}</span>
							<form:input path="detailList[${status.index}].price" value="${detail.price}"  class="cash short" />
							<input type="image" name="priceBtn${status.index}" src="${ctx}/images/action_edit.png" class="imgbtn" style="border: 0;"></td>
						
						<td><span id="totalPrice${status.index }"></span>
							<form:hidden  path="detailList[${status.index}].totalprice" value="${detail.totalPrice}"/></td>				
						<td>
							<a href="###" onClick="doDeleteContract(this);return false;">
								<span id="delete${status.index }"></span></a>
						</td>
							<form:hidden path="detailList[${status.index}].recordid" value="${detail.recordId}" />
					</tr>	
									
					<script type="text/javascript">
						var index = '${status.index}';
						var materialName = '${detail.materialName}';
						var text = "";
						
						var arrivalQty = '${detail.arrivalQty}';//收货数量
						var ReturnQty = '${detail.contractReturnQty}';//退货数量
						var description = '${detail.description}';
						
						if(arrivalQty > '0'){
							if(ReturnQty > '0'){
								text = "结束合同";
							}
						}else{
							text = "删除";
						}					
						$('#delete'+index).html(text);
						
						var price = currencyToFloat('${detail.price}');
						var quantity = currencyToFloat('${detail.quantity}');						
						var totalPrice = floatToCurrency( price * quantity );
						$('#totalPrice'+index).html(totalPrice);
						$('#detailList'+index+'\\.totalprice').val(totalPrice);
						$('#detailList'+index+'\\.description').val(replaceTextarea(description));

					</script>	
						
					</c:if>
				</c:forEach>
				
			</tbody>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td class="td-right">合计:</td>
					<td class="td-right" style="padding-right: 2px;"><span id=weightsum></span>
						<form:hidden path="contract.total"/></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
		</div>
		</fieldset>
		<fieldset>
		<legend> 合同注意事项</legend>
		<table class="form" >
			<tr>
				<td class="td-left">
					<form:textarea path="contract.memo" rows="6" cols="80" />
				</td>
			</tr>
		</table>
		
		</fieldset>
			
	</form:form>

</div>
</div>
</body>

	
</html>
