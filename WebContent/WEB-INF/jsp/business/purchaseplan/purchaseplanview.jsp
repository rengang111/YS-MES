<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购方案--查看</title>
<%@ include file="../../common/common2.jsp"%>
  	
<script type="text/javascript">

	$(document).ready(function() {		

		$( "#tabs" ).tabs();
		
		baseBomView();//基础BOM
			
		
		$(".goBack").click(
				function() {
			var YSId = '${order.YSId}';
			var url = '${ctx}/business/purchasePlan?keyBackup=' + YSId;
	
			location.href = url;		
		});
		
		$("#editPurchasePlan").click(
				function() {

			var materialId='${order.materialId}';
			var YSId ="${order.YSId}";
			var quantity ="${order.quantity}";
			$('#attrForm').attr("action",
					"${ctx}/business/purchasePlan?methodtype=purchasePlanEdit&YSId="+YSId+"&materialId="+materialId+"&quantity="+quantity);
			$('#attrForm').submit();
		});
			
	
		
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
			$('.DTFC_Cloned').find('tr').eq(rowIndex+2).addClass('selected');
			
		});

		foucsInit();//input获取焦点初始化处理
		$(".DTTT_container").css('float','left');
	
	});

	
	function baseBomView() {

		var scrollHeight = $(window).height() - 255;
		var YSId='${order.YSId}';
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
	       	"fixedColumns":   { leftColumns: 2 },
			"dom" 		: '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/purchasePlan?methodtype=purchasePlanView&orderType=010"+"&YSId="+YSId,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					"data" : null,
					success: function(data){
						fnCallback(data);
						
						$('#bomCost').text(data["data"][0]["bomCost"]);
						$('#managementCostRate').text(data["data"][0]["managementCostRate"]);
						$('#productCost').text(data["data"][0]["productCost"]);
						$('#materialCost').text(data["data"][0]["materialCost"]);
						$('#laborCost').text(data["data"][0]["laborCost"]);
						$('#managementCost').text(data["data"][0]["managementCost"]);
						$('#totalCost').text(data["data"][0]["totalCost"]);
						
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
			
	       	"language": {
	       		"url":"${ctx}/plugins/datatables/chinese.json"
	       	},
			"columns": [
				{"data": null,"className" : 'td-center'},//1
				{"data": "materialId","className" : 'td-left',"defaultContent" : ''},//2.物料编号
				{"data": null,"defaultContent" : ''},//3.物料名称
				{"data": "purchaseType","className" : 'td-center', "defaultContent" : ''},//4.物料特性:物料
				{"data": "unitQuantity","className" : 'td-right', "defaultContent" : ''},//5.单位使用量:baseBom
				{"data": null,"className" : 'td-right'},//6.生产需求量:订单
				{"data": null,"className" : 'td-right'},//7.总量= 单位使用量 * 生产需求量
				{"data": "availabelToPromise","className" : 'td-right'},//8.当前库存(虚拟库存):物料
				{"data": "purchaseQuantity","className" : 'td-right'},//9.建议采购量:输入
				{"data": "supplierId","className" : 'td-left'},//10.供应商,可修改:baseBom
				{"data": "price","className" : 'td-right', "defaultContent" : '0'},//11.本次单价,可修改:baseBom
				{"data": "totalPrice","className" : 'td-right', "defaultContent" : '0'},//12.总价=本次单价*采购量
			 ],
			"columnDefs":[
				
				{"targets":1,"render":function(data, type, row){
					rtn= "<a href=\"#\" onClick=\"doEditMaterial('" + row["materialRecordId"] +"','" + row["materialParentId"] +"')\">"+data+"</a>";
					return rtn;
				}},
	    		{"targets":2,"render":function(data, type, row){	 			
	    			return jQuery.fixedWidth(row["materialName"],35);	
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			var order = '${order.totalQuantity}';
	    			return currencyToFloat(order);
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			var order = currencyToFloat( '${order.totalQuantity}' );
	    			var price = currencyToFloat( row["unitQuantity"] );				    			
	    			var total = floatToCurrency( price * order );			    			
	    			return total;
	    		}},
	    		{"targets":7,"render":function(data, type, row){
	    			
	    			var stock =  row["availabelToPromise"] ;
	    			var fstock = currencyToFloat( stock );
					var rtn = "";
					if(fstock < 0){
						rtn = '<div style="color:red">' + stock + '</div>';
					}else {
						//price = '<div style="font-weight:bold;color:green">' + price + '</div>';
						rtn = stock;
					}
	    			return rtn;
	    		}},
	    		{"targets":8,"render":function(data, type, row){
	    			
	    			var stock =  row["purchaseQuantity"] ;
	    			var fstock = currencyToFloat( stock );
					var rtn = "";
					if(fstock > 0){
						rtn = '<div style="font-weight:bold;">' + stock + '</div>';
					}else {
						//price = '<div style="font-weight:bold;color:green">' + price + '</div>';
						rtn = stock;
					}
	    			return rtn;
	    		}}
	          
	        ] 
	     
		});	
		/*
		t.on('click', 'tr', function() {

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		*/
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				var num   = i + 1;
				cell.innerHTML = num;
			});
		}).draw();
		
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
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:150px;">${order.YSId}
					<form:hidden path="purchasePlan.ysid"  value="${order.YSId}" /></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;"><a href="###" onClick="doShowProduct()">${order.materialId}</a>
					<form:hidden path="purchasePlan.materialid"  value="${order.materialId}" /></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td>${order.materialName}</td>
				</tr>
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
			<button type="button" id="createPurchaseOrder" class="DTTT_button">生成采购合同</button>
			<button type="button" id="editPurchasePlan" class="DTTT_button">修改采购方案</button>
			<button type="button" id="goBack" class="DTTT_button goBack">返回</button>
		</fieldset>	
		<fieldset style="margin-top: -20px;">
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
					<td class="td-center"><span id="materialCost"></span> </td>
					<td class="td-center"><span id="laborCost"></span> </td>
					<td class="td-center"><span id="bomCost"></span> </td>
					<td class="td-center"><span id="productCost"></span> </td>
					<td class="td-center"><span id="managementCostRate"></span>% </td>
					<td class="td-center"><span id="managementCost"></span> </td>
					<td class="td-center"><span id="totalCost"></span> </td>
				</tr>								
			</table>
	
		</fieldset>	
		<div id="tabs" style="padding: 0px;white-space: nowrap;margin-top: -10px;">
		<ul>
			<li><a href="#tabs-1" class="tabs1">采购方案</a></li>
		</ul>

		<div id="tabs-1" style="padding: 5px;">

		<table id="example" class="display" style="width:100%">
			<thead>				
				<tr>
					<th class="dt-center" style="width:30px">No</th>
					<th class="dt-center" style="width:100px">物料编码</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" style="width:30px">物料特性</th>
					<th class="dt-center" width="60px">用量</th>
					<th class="dt-center" width="60px">需求量</th>
					<th class="dt-center" width="60px">总量</th>
					<th class="dt-center" width="60px">当前库存</th>
					<th class="dt-center" width="60px">采购量</th>
					<th class="dt-center" style="width:80px">供应商</th>
					<th class="dt-center" width="60px">单价</th>
					<th class="dt-center" width="80px">总价</th>
				</tr>
			</thead>
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

function doEditMaterial(recordid,parentid) {
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

function purchasePlanCompute(obj,flg){
	
	 var $td = obj;
		
	var $oMaterIdV  = $td.eq(0).find("span");
	var $oMatName   = $td.eq(4).find("span");
	var $oType      = $td.eq(5).find("span");
	var $oUnitQuty  = $td.eq(6).find("input");
	var $oOrder     = $td.eq(7).find("span");
	var $oTotalQuty = $td.eq(8).find("span");
	var $oStock     = $td.eq(9).find("span");
	var $oPurchase  = $td.eq(10).find("input");
	var $oSupplier  = $td.eq(11).find("input");
	var $oThisPrice = $td.eq(12).find("input");
	var $oTotPriceS = $td.eq(13).find("span");
	var $oTotPriceI = $td.eq(13).find("input");

	//开始计算
	var fUnitQuty = currencyToFloat( $oUnitQuty.val() );
	var fOrder    = currencyToFloat( $oOrder.text() );
	var fTotalQuty= fUnitQuty * fOrder;
	var fStock = currencyToFloat( $oStock.text() );
	if(flg == '1'){
		var fPurchase = setPurchaseQuantity(fStock,fTotalQuty);//建议采购量:自动计算		
		
	}else if (flg == '2'){
		var fPurchase = currencyToFloat( $oPurchase.val() );//建议采购量	:直接输入	
	}
	var fPrice    = currencyToFloat($oThisPrice.val());//计算用单价
	var fTotalNew = currencyToFloat(fPrice * fPurchase);//合计
	
	//var materialId = $oMaterial.val();	
	var vPurchase = floatToCurrency(fPurchase);	
	var vTotalQuty= floatToCurrency(fTotalQuty);	
	var vTotalNew = floatToCurrency(fTotalNew);
	var vUnitQuty = float5ToCurrency(fUnitQuty);
			
	//详情列表显示
	$oUnitQuty.val(vUnitQuty)
	$oPurchase.val(vPurchase);
	$oTotalQuty.html(fTotalQuty);
	$oTotPriceS.html(vTotalNew);
	$oTotPriceI.val(vTotalNew);
}
</script>
</body>
	
</html>
