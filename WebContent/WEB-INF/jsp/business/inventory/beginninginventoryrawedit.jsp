<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>库存一览--采购方案)(原材料)</title>
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
				
				]
				
			}
		);

	}

	$(document).ready(function() {
		
		ajax("true");
		
		$("#doSave").click(
				function() {
					var materialId = '${material.rawMaterialId }';
					$('#attrForm').attr("action", "${ctx}/business/purchasePlan?methodtype=insertStockoutCorrectionForRaw"+"&materialId="+materialId);
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
		
		//sumFn(6);
		//sumFn(7);
		//$('#waitout').html(floatToCurrency( currencyToFloat($('#totalValue6').html()) - currencyToFloat($('#totalValue7').html()) ))
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
					<td width="120px">${material.rawMaterialId }</td>
					<td class="label" width="70px">物料名称：</td> 
					<td>${material.rawMaterialName }</td>		
					<td width="100px"><button type="button" id="doSave" class="DTTT_button">保存修正领料</button></td> 			
				</tr>
			</table>		
			<div class="list">
				<table  id="TMaterial" class="display">
					<thead>	
						<tr >
							<th style="width: 1px;">No</th>
							<th style="width: 60px;">方案做成日</th>
							<th style="width: 80px;">耀升编号</th>
							<th style="width: 120px;">产品编号</th>
							<th>产品名称</th>
							<th style="width: 60px;">订单数量</th>
							<th style="width: 60px;">生产需求量</th>
							<th style="width: 60px;">领料数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var='list' items='${planList}' varStatus='status'>
							<tr>
								<td>${status.index +1 }</td>
								<td>${list.planDate }</td>
								<td>${list.YSId }
									<form:hidden path="correctionList[${status.index}].ysid"  value="${list.YSId}"/>
									<form:hidden path="correctionList[${status.index}].materialid"  value="${material.rawMaterialId}"/></td>
								<td>${list.productId }</td>
								<td><span id="shortName${status.index }">${list.materialName }</span></td>
								<td>${list.manufactureQuantity }</td>
								<td id="weight${status.index }"></td>
								
								<td>								
									<div id="edit${status.index}">
										<form:input path="correctionList[${status.index}].quantity"  
											value="${list.stockoutQty}" class="short num"/>
									</div>
									<div id="view${status.index}"></div>					
								</td>
								
							</tr>
							
							<script type="text/javascript">
								var index = '${status.index}';	
								var materialName = '${list.materialName}';
								var weight = '${list.rawOrderWeight}';
								var stockoutQty = '${list.stockOutQty}';
								var correctionQty = '${list.correctionQty}';//修正值																

								var vrawunit = '${list.unit}';
								var unittext = '${list.zzunit}';
								
								var farwunit = '1';//初始值
								//原材料的购买单位
								//alert(unitAaary.length)
								for(var i=0;i<unitAaary.length;i++){
									var val = unitAaary[i][0];//取得计算单位:100,1000...
									var key = unitAaary[i][1];//取得显示单位:克,吨...
									if(vrawunit == key){
										farwunit = val;
										//alert('原材料的购买单位'+farwunit)
										break;
									}
								}

								//自制品的用量单位
								var fchgunit = '1';//初始值
								for(var i=0;i<unitAaary.length;i++){
									var val = unitAaary[i][0];//取得计算单位:100,1000...
									var key = unitAaary[i][1];//取得显示单位:克,吨...
									if(unittext == key){
										fchgunit = val;//只有在需要换算的时候,才设置换算单位
										//alert('自制品的用量单位'+fchgunit)
										break;
									}
								}	
								
								var fconvunit = fchgunit / farwunit;//换算单位
								var newWeight = weight / fconvunit;
								//alert('weight --newWeight--fchgunit--farwunit'+weight +"--"+fchgunit+"--"+farwunit+"--"+newWeight)
								
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
								
								
								$('#weight'+index).html(floatToCurrency(newWeight));
								$('#shortName'+index).html(jQuery.fixedWidth(materialName,32));
							</script>	
						</c:forEach>
					</tbody>
					<!-- 
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th>合计：</th>
							<th style="text-align: right;"><span id="totalValue6"></span></th>
							<th style="text-align: right;"><span id="totalValue7"></span></th>
						</tr>
					</tfoot>
					 -->
				</table>
			</div>	
	</fieldset>
</form:form>
</div>
</div>
</body>
</html>