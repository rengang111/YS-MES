<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>领料修正</title>
<script type="text/javascript">

	function ajax(sessionFlag) {
		
		var t = $('#TMaterial').DataTable({
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : 'T<"clear">',
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"className" : 'td-center'},
					{"className" : ''},
					{"className" : 'td-left'},//
					{"className" : 'td-left'},//
					{"className" : ''},
					{"className" : 'td-right',"defaultContent" : '0'},//
					{"className" : 'td-right',"defaultContent" : '0'},//
					{"className" : 'td-right',"defaultContent" : '0'},//
					{"className" : 'td-right',"defaultContent" : '0'},//
				
				]
				
			}
		);

	}

	$(document).ready(function() {
		
		
		ajax("true");
	
		
		$("#doSave").click(
				function() {
					var materialId = '${material.materialId }';
					$('#attrForm').attr("action", "${ctx}/business/purchasePlan?methodtype=insertStockoutCorrection"+"&materialId="+materialId);
					$('#attrForm').submit();
		});
		
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});		

		foucsInit();
		
		//$('#waitout').html(floatToCurrency( currencyToFloat($('#totalValue5').html()) - currencyToFloat($('#totalValue8').html()) ))
	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		ajax("false");

	}

	function sumFn(num){
		
		var sum7 = 0;
		$('#TMaterial tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(num).text();////合同
			contractValue= currencyToFloat(contractValue);
			
			sum7 = sum7 + contractValue;
						
		});	
		
		$('#totalValue'+num).html(floatToCurrency(sum7));
	}
	
	function setStockoutQty(index,quantity){
		var txt = $("#correctionList"+index+"\\.quantity").val();
		if( txt == '' || txt == '0'){
			$("#correctionList"+index+"\\.quantity").val(quantity);
			$("#btn_edit"+index).html('取消');
		}else{
			$("#correctionList"+index+"\\.quantity").val('0');
			$("#btn_edit"+index).html('修正');
		}
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
		
		<fieldset>
			<legend> 采购方案</legend>
			<table width="100%">
				<tr>
					<td class="label" width="100px">物料编号：</td>
					<td width="120px">${material.materialId }</td>
					<td class="label" width="70px">物料名称：</td> 
					<td>${material.materialName }</td>
					<td width="100px"><button type="button" id="doSave" class="DTTT_button">保存修正领料</button></td> 			
			</table>		
			<div class="list">
				<table  id="TMaterial" class="display">
					<thead>	
						<tr >
							<th style="width: 1px;">No</th>
							<th style="width: 50px;">方案日期</th>
							<th style="width: 70px;">耀升编号</th>
							<th style="width: 100px;">产品编号</th>
							<th>产品名称</th>
							<th style="width: 60px;">生产需求</th>
							<th style="width: 60px;">合同数</th>
							<th style="width: 60px;">入库数</th>
							<th style="width: 60px;">已领料</th>
						</tr>
					</thead>
					<tbody>
					
		<c:forEach var='list' items='${planList}' varStatus='status'>
			<tr>
				<td>${status.index +1 }</td>
				<td>${list.planDate }</td>
				<td>${list.YSId }
					<form:hidden path="correctionList[${status.index}].ysid"  value="${list.YSId}"/>
					<form:hidden path="correctionList[${status.index}].subbomno"  value="${list.subBomNo}"/>
					<form:hidden path="correctionList[${status.index}].materialid"  value="${material.materialId}"/></td>
				<td>${list.productId }</td>
				<td><span id="shortName${status.index }">${list.productName }</span></td>
				<td>${list.manufactureQuantity }</td>
				<td>${list.contractQty }</td>
				<td>${list.stockinQty }</td>
				<td>
					<div id="edit${status.index}">
						<form:input path="correctionList[${status.index}].quantity"  
							value="${list.stockoutQty}" class="mini num"/>
						<button type="button" id="btn_edit${status.index}"  style="height: 26px;"
							onclick="setStockoutQty('${status.index}','${list.manufactureQuantity }'); return false;" >修正</button>
					</div>
					<div id="view${status.index}">
						${list.stockoutQty}					
					</div>
				</td>
			</tr>
			
			<script type="text/javascript">
				var index = '${status.index}';	
				var materialName = '${list.productName}';
				var stockoutQty = '${list.stockoutQty}';
				var correctionQty = '${list.correctionQty}';//修正值
				
				correctionQty = currencyToFloat(correctionQty);
				stockoutQty = 	currencyToFloat(stockoutQty);
				if(stockoutQty > 0){
					//$("#correctionList" + index + "\\.quantity").attr('readonly', "true");
					$("#view"+index).html(floatToCurrency(stockoutQty) );
					$("#edit"+index).remove();						
					
				}else{
					if(correctionQty > 0 ){//修正值
						$("#view"+index).html("(改)"+ floatToCurrency(correctionQty) );
						$("#edit"+index).remove();						
					}else{
						$("#view"+index).remove();
						$("#edit"+index).show();						
					}					
				}
				$('#shortName'+index).html(jQuery.fixedWidth(materialName,24));
				
			</script>	
		</c:forEach>		
		
					</tbody>
				</table>
			</div>	
	</fieldset>
</form:form>
</div>
</div>
</body>
</html>
