<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>采购合同-查看</title>
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
	        "ordering"  : false,

			dom : '<"clear">rt',
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {"className":"td-center"					
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				]
			
		}).draw();
		
			
		t.on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
			
		});
		
		t.on('contract.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	};//ajaxRawGroup()
	
	$(document).ready(function() {

		var YSId = '${ contract.YSId }';
		//var productid = '${ contract.productId }';
		if(YSId == null || YSId == ""){
			$('#ysid00').attr("style","display:none");
						
		}else{
			//if(productid == null || productid == ""){
				$('#ysidLink').contents().unwrap();			
			//}
		}
		
		
		//返回按钮
		var goBactkBt = '${openFlag}';
		if(goBactkBt == "newWindow"){
			$('#goBack').attr("style","display:none");			
		}
		
		ajaxRawGroup();			
		
		$( "#tabs" ).tabs();
		
		$('#example').DataTable().columns.adjust().draw();		
		
		$("#goBack").click(
				function() {	
					var keyBackup = '${contract.YSId}';
					var url = '${ctx}/business/contract?keyBackup='+keyBackup;
					location.href = url;		
				});
		
		$("#doEdit").click(
				function() {
					//var deleteFlag = $('#deleteFlag').val();
					//if(deleteFlag == '0'){
						//$().toastmessage('showWarningToast', "该合同已经有收货了,不能编辑。");
						//return false;
					//}		
			$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=edit");
			$('#attrForm').submit();
		});	
		
		$("#doDelete").click(
				function() {
					var deleteFlag = $('#deleteFlag').val();
			if(deleteFlag == '0'){
				$().toastmessage('showWarningToast', "该合同已经有收货了,不能删除。");
				return false;
			}		
			if(confirm("采购合同删除后不能恢复,\n\n        确定要删除吗？")) {
				$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=delete");
				$('#attrForm').submit();
			}else{
				return false;
			}
			
		});
		
		sumFn();//合计值计算
		
	});	
	
	function doShowYS(YSId) {

		var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;
		
		location.href = url;
	}
	

	function doEditMaterial(recordid,parentid) {
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
			  //$('#baseBomTable').DataTable().ajax.reload(null,false);
			  return false; 
			}    
		});		

	};
	
	function sumFn(){
		
		var sum7 = 0;
		var sum8 = 0;
		var sum9 = 0;
		$('#example tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(5).text();////合同
			var returnValue  = $(this).find("td").eq(6).text();//退货
			var payValue   = $(this).find("td").eq(7).text();//应付
			
			contractValue= currencyToFloat(contractValue);
			returnValue = currencyToFloat(returnValue);
			payValue  = currencyToFloat(payValue);
			//alert("计划用量+已领量+库存:"+fjihua+"---"+fyiling+"---"+fkucun)
			
			sum7 = sum7 + contractValue;
			sum8 = sum8 + returnValue;
			sum9 = sum9 + payValue;
						
		});	
		
		$('#contractValue').html(floatToCurrency(sum7));
		$('#returnValue').html(floatToCurrency(sum8));
		$('#payValue').html(floatToCurrency(sum9));
	}
	

	function showContract() {
		var contractId = '${ contract.contractId }';
		var url = '${ctx}/business/requirement?methodtype=contractPrint';
		url = url +'&contractId='+contractId;
		//alert(url)
		
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
			  //  layer.close(index)
			 // }
			  //baseBomView();
			 // return false; 
			}    
		});		

	};
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
			
		<form:hidden path="contract.recordid" value="${contract.contractRecordId }"/>
		<input type="hidden" id="deleteFlag" value="${deleteFlag }" />
		
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
			
				<tr id="ysid00"> 		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td colspan="7">
						<a href="#" id="ysidLink" onClick="doShowYS('${contract.YSId}')">${contract.YSId }</a>
						<form:hidden path="contract.ysid" value="${contract.YSId }"/></td>
					
				</tr>
				
				<tr> 		
					<td class="label"><label>供应商编号：</label></td>					
					<td>${ contract.supplierId }
						<form:hidden path="contract.supplierid" value="${contract.supplierId }"/></td>
									
					<td class="label"><label>供应商简称：</label></td>					
					<td width="100px">${ contract.shortName }</td>
						
					<td class="label" width="100px"><label>供应商名称：</label></td>
					<td colspan="3">${ contract.fullName }</td>
				</tr>	
				<tr> 		
					<td class="label"><label>合同编号：</label></td>					
					<td>${ contract.contractId }
						<form:hidden path="contract.contractid" value="${contract.contractId }"/></td>
					<td class="label" width="100px">付款条件：</td>
					<td>入库后&nbsp;${ contract.paymentTerm }&nbsp;天</td>
					<td class="label"><label>下单日期：</label></td>
					<td>${ contract.purchaseDate }</td>
					<td class="label"><label>合同交期：</label></td>
					<td>${ contract.deliveryDate }</td>
				</tr>									
			</table>
			
	</fieldset>
	
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="submit" id="doEdit" class="DTTT_button">编辑</button>
		<button type="submit" id="doDelete" class="DTTT_button">删除</button>
		<button type="button" id="doPrint" class="DTTT_button" onclick="showContract();">打印</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	<div style="clear: both"></div>		
	<fieldset style="margin-top: -30px;">
	<legend> 合同详情</legend>
	
	<div class="list">
	<table id="example" class="display" >	
		<thead>
		<tr>
			<th style="width:10px">No</th>
			<th style="width:130px">物料编码</th>
			<th>物料名称</th>
			<th style="width:50px">合同数</th>
			<th style="width:50px">单价</th>
			<th style="width:60px">合同金额</th>
			<th style="width:50px">退款</th>
			<th style="width:60px">应付款</th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="detail" items="${detail}" varStatus='status' >	
				<tr id="detailTr${status.index }">
					<td></td>
					<td><a href="###" id="meteLink${status.index }" onClick="doEditMaterial('${detail.materialRecordId}','${detail.materialParentId}')">${detail.materialId}</a>
						<form:hidden path="detailList[${status.index}].materialid" value="${detail.materialId}" /></td>								
					<td><span id="name${status.index}"></span>${ detail.description }</td>					
				
					<td>${ detail.quantity}   </td>								
					<td><span id="price${status.index }">${ detail.price }</span></td>
					<td><span id="total${status.index }">${ detail.totalPrice }</span></td>					
					<td><span id="returnValue${status.index }"></span></td>				
					<td><span id="pay${status.index }"></span></td>			
						
						<form:hidden path="detailList[${status.index}].recordid" value="${detail.recordId}" />	
						<form:hidden path="detailList[${status.index}].quantity" value="${detail.quantity}" />	
				</tr>	
								
				<script type="text/javascript">
					//var materialName = '${detail.materialName}';
					var index = '${status.index}';
					var contractQty = currencyToFloat('${detail.quantity}');
					var chargeback = currencyToFloat('${detail.chargeback}');
					var price = currencyToFloat('${detail.price}');
					//alert("合同数量+退货数量+单价"+contractQty+"---"+chargeback+"---"+price)
					var contractValue = contractQty * price;
					
					var pay = floatToCurrency( contractValue + chargeback );
					
					
					
					
					//$('#name'+index).html(jQuery.fixedWidth(materialName,45));
					$('#returnValue'+index).html(floatToCurrency( chargeback ));
					$('#pay'+index).html(pay);
					$('#price'+index).html(formatNumber(price));
					$('#total'+index).html(floatToCurrency(contractValue));

					var deleteFlag = '${detail.deleteFlag}';
					if( deleteFlag == 1 ) {
						$('#detailTr'+index).addClass('delete');
						$('#meteLink'+index).contents().unwrap();	
						$('#total'+index).html('0');
						$('#pay'+index).html('0');
	 				}				
				</script>	
					
			</c:forEach>
			
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td class="font16">合计：</td>
				<td class="font16"><div id="contractValue"></div></td>
				<td class="font16"><div id="returnValue"></div></td>
				<td class="font16"><div id="payValue"></div></td>
			</tr>
		</tfoot>
	</table>
	</div>
	</fieldset>
	<fieldset>
	<legend> 合同注意事项</legend>
	<table class="form" >
		<tr>
			<td class="td-left"><pre>${contract.memo}</pre></td>
		</tr>
	</table>
	
	</fieldset>
		
</form:form>

</div>
</div>
</body>

	
</html>
