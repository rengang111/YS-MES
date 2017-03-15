<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购方案--查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter  = 0;
	//Form序列化后转为AJAX可提交的JSON格式。
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};	
	
	
	$(document).ready(function() {
		
		
		requirementAjax();//
		
		baseBomView();//分离数量
		
		ZZmaterialView();//合并数量

		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/order";
					location.href = url;		
		});

		$("#doEdit").click(function() {
			var YSId = '${order.YSId}';
			var bomId=$('#bomId').text();
			var url = "${ctx}/business/requirement?methodtype=editRequirement&YSId="+YSId+"&bomId="+bomId;
			location.href = url;		
		});
		
		$("#print").click(function() {
			var YSId = '${order.YSId}';
			var bomId=$('#bomId').text();
			var url = "${ctx}/business/requirement?methodtype=printRequirement&YSId="+YSId+"&bomId="+bomId;
			location.href = url;
		});
		
		$("input:text").focus (function(){
		    $(this).select();
		});

		$(".DTTT_container").css('float','left');

		$( "#tabs" ).tabs();
		$( "#tabs" ).tabs( "option", "active", 2 );//设置默认显示内容
		
		$(".tabs1").click( function() {
			$('#orderBomTable thead tr').each (function (){
				$(this).find("th").eq(0).css('width','30px ');
				$(this).find("th").eq(1).css('width','120px');
				$(this).find("th").eq(2).css('width','200px');
				$(this).find("th").eq(3).css('width','60px');
				$(this).find("th").eq(4).css('width','30px');
				$(this).find("th").eq(5).css('width','60px');
				$(this).find("th").eq(6).css('width','80px');
				$(this).find("th").eq(7).css('width','60px');
				$(this).find("th").eq(8).css('width','80px');
				
			})
		});
		$(".tabs2").click( function() {
			$('#ZZmaterial thead tr').each (function (){
				$(this).find("th").eq(0).css('width','30px ');
				$(this).find("th").eq(1).css('width','150px');
				$(this).find("th").eq(2).css('width','250px');
				$(this).find("th").eq(3).css('width','60px');
				$(this).find("th").eq(4).css('width','80px');
				$(this).find("th").eq(5).css('width','80px');
				$(this).find("th").eq(6).css('width','100px');
			})
		});
		
		
		$('#example').DataTable().on('click', 'tr', function() {

			var rowIndex = $(this).context._DT_RowIndex; //行号		
			//alert(rowIndex)			
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#example').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            
	        }
			
			//$('.DTFC_Cloned').find('tr').removeClass('selected');
			//$('.DTFC_Cloned').find('tr').eq(rowIndex).addClass('selected');
			
		});
		
		//foucsInit();//input获取焦点初始化处理
	
	});

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
					<td style="width:150px;">${order.YSId}</td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;">${order.materialId}</td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td>&nbsp;${order.materialName}</td>
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
			<button type="button" id="goBack" class="DTTT_button goBack" style="float: right;margin: -15px 20px 0px 0px;">返回</button>
			<button type="button" id="print1" onClick="openPrint();" class="DTTT_button" style="float: right;margin: -15px 20px 0px 0px;">打印</button>
		<fieldset>
			<legend> 订单核算</legend>
			<table class="form" id="table_form2">
				<tr>
					<td class="td-center" width="150px"><label>BOM编号</label></td>
					<td class="td-center"><label>材料成本</label></td>
					<td class="td-center"><label>人工成本</label></td>
					<td class="td-center"><label> BOM成本</label></td>
					<td class="td-center"><label>基础成本</label></td>
					<td class="td-center"><label>经管费率</label></td>
					<td class="td-center"><label>核算成本</label></td>
				</tr>
				<tr>
					<td class="td-center"><span id="bomId">${attrForm.bomPlan.bomid }</span></td>
					<td class="td-center"><span id="materialCost"></span></td>
					<td class="td-center"><span id="laborCost"></span></td>
					<td class="td-center"><span id="bomCost"></span></td>
					<td class="td-center"><span id="productCost"></span></td>
					<td class="td-center"><span id="costRate">2</span>%</td>
					<td class="td-center"><span id="totalCost"></span></td>
				</tr>								
			</table>

		</fieldset>	
		
	<div id="tabs" style="padding: 0px;white-space: nowrap;">
		<ul>
			<li><a href="#tabs-1" class="tabs1">一级BOM</a></li>
			<li><a href="#tabs-2" class="tabs2">二级BOM</a></li>
			<li><a href="#tabs-3" class="tabs3">采购方案</a></li>
		</ul>

		<div id="tabs-1" style="padding: 5px;">

			<table id="orderBomTable" class="display" style="width:98%">
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:120px">物料编码</th>
						<th class="dt-center" style="width:180px">物料名称</th>
						<th class="dt-center" width="60px">用量</th>
						<th class="dt-center" style="width:30px">单位</th>
						<th class="dt-center" width="80px">供应商</th>
						<th class="dt-center" width="60px">订单数量</th>
						<th class="dt-center" width="80px">总量</th>
						<th class="dt-center" width="80px">单价</th>
						<th class="dt-center" width="80px">总价</th>
					</tr>
				</thead>			
			</table>
		</div>
		<div id="tabs-2" style="padding: 5px;">

			<table id="ZZmaterial" class="display" style="width:98%">
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:120px !important">原材料编码</th>
						<th class="dt-center">原材料名称</th>
						<th class="dt-center" width="60px !important">单位</th>
						<th class="dt-center" width="80px">总量</th>
						<th class="dt-center" width="60px">当前库存</th>
						<th class="dt-center" width="80px !important">建议需求量</th>
					</tr>
				</thead>
				
				<tbody>
				<c:forEach var="detail" items="${requirement}" varStatus='status' >
					<c:if test="${detail.materialId.substring(0,1) == 'A'}">
						<tr>
							<td></td>
							<td>${detail.materialId}</td>								
							<td><span id="rawName${status.index}"></span></td>
							<td>${detail.unit}</td>		
							<td>${detail.orderQuantity}</td>	
							<td>0</td>	
							<td>${detail.quantity}</td>							
						</tr>
		
						<script type="text/javascript">
							var index = '${status.index}';
							var materialName = '${detail.materialName}';
							var quantity = '${detail.advice}';	
							//alert('quantity['+'${detail.advice}'+']')	
							$('#rawName'+index).html(jQuery.fixedWidth(materialName,25));
						</script>
					</c:if>
				</c:forEach>				
				</tbody>		
			</table>
		</div>
		<div id="tabs-3" style="padding: 5px;">
			<table id="example" class="display" >
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:150px">物料编码</th>
						<th class="dt-center" style="width:180px">物料名称</th>
						<th class="dt-center" style="width:30px">单位</th>
						<th class="dt-center" width="60px">采购需求量</th>
						<th class="dt-center" width="60px">当前库存</th>
						<th class="dt-center" width="60px">建议采购量</th>
						<th class="dt-center" style="width:30px">本次单价</th>
						<th class="dt-center" style="width:80px">总价</th>
						<th class="dt-center" width="60px">供应商</th>						
						<th class="dt-center" width="100px">当前价格</th>	
						<th class="dt-center" style="width:100px">历史最低</th>	
					</tr>
				</thead>
				<tbody>
				<c:forEach var="detail" items="${requirement}" varStatus='status' >		
				
				<tr>
					<td></td>
					<td>${detail.materialId}
						<form:hidden path="purchaseList[${status.index}].materialid"  value="${detail.materialId}" /></td>								
					<td><span id="name${status.index}"></span></td>
					<td>${detail.unit}</td>
					<td>${detail.orderQuantity}</td>
					<td>0</td>
					<td>${detail.quantity}</td>			
					<td><span id="price${status.index}" ></span> ${detail.price}</td>				
					<td><span id="total${status.index}"></span></td>					
					<td>${detail.supplierId}</td>
					<td><span id="last${status.index}"></span></td>
					<td><span id="min${status.index}"></span></td>
				</tr>

				<script type="text/javascript">
					var index = '${status.index}';
					var materialName = '${detail.materialName}';
					var lastPrice = float4ToCurrency( '${detail.lastPrice}' );
					var lastSupplierId = '${detail.lastSupplierId}';
					var minPrice = float4ToCurrency( '${detail.minPrice}' );
					var minSupplierId = '${detail.minSupplierId}';
					var price =currencyToFloat( '${detail.price}');
					var quantity = currencyToFloat('${detail.quantity}');
					var total = floatToCurrency( price * quantity );
						
					$('#total'+index).html(total);				
					$('#last'+index).html(lastPrice+'／'+stringPadAfter(lastSupplierId,12) );
					$('#min'+index).html(minPrice+'／'+stringPadAfter(minSupplierId,12) );
					$('#name'+index).html(jQuery.fixedWidth(materialName,25));				
					
					counter++;
				</script>
				
			</c:forEach>	
			</tbody>			
			</table>
		<fieldset class="action" style="text-align: right;margin-top: 10px;">
			<button type="button" id="doEdit" class="DTTT_button">编辑</button>
			<button type="button" id="doReport" class="DTTT_button">提交</button>
			<button type="button" id="doContract" class="DTTT_button">生成采购合同</button>
			<button type="button" id="goBack" class="DTTT_button goBack">返回</button>
		</fieldset>	
	
		</div>

	</div>
		
<div style="clear: both"></div>		
</form:form>

</div>
</div>

<script  type="text/javascript">
function baseBomView() {

	var bomId=$('#bomId').text();
	var table = $('#orderBomTable').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#orderBomTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/requirement?methodtype=getOrderBom&bomId="+bomId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					
					
					fnCallback(data);

					costAcount();
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "materialId"},
			{"data": "materialName"},
			{"data": "quantity","className" : 'td-right'},
			{"data": "unit","className" : 'td-center'},
			{"data": "supplierId"},
			{"data": null,"className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
			{"data": "price","className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
		 ],
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];				    			
    			name = jQuery.fixedWidth(name,25);				    			
    			return name;
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var materialId = row["materialId"];
    			rtn= "<a href=\"###\" onClick=\"doEditMaterial('" + row["rawRecordId"] +"','"+ row["parentId"] + "')\">"+materialId+"</a>";
    			return rtn;
    		}},
    		{"targets":6,"render":function(data, type, row){
    			
    			var quantity =  '${order.quantity}' ;
    			return quantity;
    		}},
    		{"targets":7,"render":function(data, type, row){
    			var order = currencyToFloat('${order.quantity}' );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var total = floatToCurrency( order * quantity );			    			
    			return total;
    		}},
    		{"targets":9,"render":function(data, type, row){
    			var price = currencyToFloat( row["price"] );
    			var order = currencyToFloat('${order.quantity}' );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var totalPrice = floatToCurrency( price * quantity * order);			    			
    			return totalPrice;
    		}}
          
        ] 
	});
	
	t2.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t2.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});

	t2.on('order.dt search.dt draw.dt', function() {
		t2.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();

	
}//ajax()供应商信息

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
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  baseBomView();
		  return false; 
		}    
	});		

};

function doEditMaterial2(recordid,parentid) {
	
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
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		   // ZZmaterialView();
		   window.location.reload();
		  return false; 
		}    
	});		

};
function doEditDocumentary() {
	
	var url = '${ctx}/business/order?methodtype=documentaryEdit';
	var YSId = '${order.YSId}';
	url = url + '&YSId=' + YSId;
	
	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '800px', '300px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  documentaryShow();
		  return false; 
		}    
	});		

};
</script>

<script  type="text/javascript">
function ZZmaterialView() {

	var materialId='${order.materialId}';
	var table = $('#ZZmaterial').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#ZZmaterial').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		//"sAjaxSource" : "${ctx}/business/requirement?methodtype=getzzMaterial&materialId="+materialId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);
										
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
       	
		"columns": [
			{"data": null,"className" : 'td-center'},
			{},
			{},
			{"className" : 'td-center'},
			{"defaultContent" : '0',"className" : 'td-right'},
			{"defaultContent" : '0',"className" : 'td-right'},
			{"defaultContent" : '0',"className" : 'td-right'},
		 ],
		
	    // "aaSorting": [[ 1, "asc" ]]
	});
	
	t2.on('blur', 'tr td:nth-child(7),tr td:nth-child(8),tr td:nth-child(9)',function() {
		
        $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
	
	t2.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t2.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});

	t2.on('order.dt search.dt draw.dt', function() {
		t2.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();

	
}//ajax()供应商信息
</script>

<script  type="text/javascript">

function requirementAjax() {

	var scrollHeight = $(window).height() - 250;
	var t3 = $('#example').DataTable({

		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
        "sScrollY": scrollHeight,
        "sScrollX": true,
        "fixedColumns":   {
            leftColumns: 3
        },
		"dom" : '<"clear">rt',

		"columns" : [ 
		        	{"className":"dt-body-center"
				}, {
				}, {								
				}, {"className":"td-center"
				}, {"className":"td-right"
				}, {"className":"td-right"
				}, {"className":"td-right"
				}, {"className":"td-right"
				}, {"className":"td-right bold"	
				}, {"className":"td-right"			
				}, {"className":"td-right"
				}, {"className":"td-right"
				}			
			],
		    // "aaSorting": [[ 1, "asc" ]]
		
	}).draw();


// new $.fn.dataTable.FixedColumns( t3 ,{leftColumns: 1,leftColumns: 2,leftColumns: 3});
	
	t3.on('blur', 'tr td:nth-child(7),tr td:nth-child(8),tr td:nth-child(10)',function() {
		
		var currValue = $(this).find("input:text").val().trim();

        $(this).find("input:text").removeClass('bgwhite');
        
        if(currValue =="" ){
        	
        	 $(this).find("input:text").addClass('error');
        }else{
        	 $(this).find("input:text").addClass('bgnone');
        }
		
	});
			

	t3.on('change', 'tr td:nth-child(7),tr td:nth-child(8)',function() {
		
		/*产品成本 = 各项累计
		人工成本 = H带头的ERP编号下的累加
		材料成本 = 产品成本 - 人工成本
		经管费 = 经管费率 x 产品成本
		核算成本 = 产品成本 + 经管费*/
		
        var $tds = $(this).parent().find("td");
		
        //var $oMaterial  = $tds.eq(1).find("input:text");
       // var $oQuantity  = $tds.eq(4).find("input");
		//var $oThisPrice = $tds.eq(5).find("span");
		var $oQuantity    = $tds.eq(6).find("input");
		var $oPrice       = $tds.eq(7).find("input");
		var $oTotali      = $tds.eq(8);
		//var $oTotals      = $tds.eq(9).find("span");
		var $oLastPrice   = $tds.eq(10).find("input");
		//var $oAmount2   = $tds.eq(6).find("span");
		//var $oAmountd   = $tds.eq(6).find("input:last-child");//人工成本
		
		//var materialId = $oMaterial.val();
		var fLastPrice = currencyToFloat($oLastPrice.val());
		var fPrice = currencyToFloat($oPrice.val());		
		var fQuantity = currencyToFloat($oQuantity.val());	
		
		var fTotalNew = currencyToFloat(fPrice * fQuantity);
		//var fAmountd  = fnLaborCost(materialId,fTotalNew);//人工成本

		var vPrice = float4ToCurrency(fPrice);	
		var vQuantity = floatToCurrency(fQuantity);
		var vTotalNew = floatToCurrency(fTotalNew);
				
		//详情列表显示新的价格
		//$oThisPrice.val(vPrice);					
		$oQuantity.val(vQuantity);	
		$oPrice.val(vPrice);	
		//$oTotals.html(vTotalNew);
		$oTotali.text(vTotalNew);
		
		if(fPrice > fLastPrice){
			$oPrice.removeClass('decline').addClass('rise');
		}else if(fPrice < fLastPrice){
			$oPrice.removeClass('rise').addClass('decline');			
		}

		//alert("fPrice:"+fPrice+"::fLastPrice:"+fLastPrice)
		//合计成本
		costAcount();
		
	});
	
	t3.on('order.dt search.dt draw.dt', function() {
		t3.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			//var checkBox = "<input type=checkbox name='numCheck' id='numCheck' value='" + num + "' />";
			cell.innerHTML = num;
		});
	}).draw();

};//ajax()

function fnLaborCost(materialId,cost){
	
	var laborCost = '0';
	
	//判断是否是人工成本
	if(materialId != '' && materialId.substring(0,1) == 'H')
		laborCost = cost;
	
	//alert('materialId:'+materialId+'--laborCost:'+laborCost);
	
	return laborCost;
}

//列合计:总价
function productCostSum(){

	var sum = 0;
	$('#orderBomTable tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(9).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
	
}
//列合计:自制品
function laborCostSum(){

	var sum = 0;
	$('#orderBomTable tbody tr').each (function (){
		
		var materialId = $(this).find("td").eq(1).text();
		var vtotal = $(this).find("td").eq(9).text();
		//判断是否是人工成本
		if(materialId != '' && materialId.substring(0,1) == 'H'){
			var ftotal = currencyToFloat(vtotal);
			sum = currencyToFloat(sum) + ftotal;	
		}	
	})		
	return sum;
	
}

function costAcount(){
	//计算该客户的销售总价,首先减去旧的		
	//产品成本=各项累计
	//人工成本=H带头的ERP编号下的累加
	//材料成本=产品成本-人工成本
	//经管费=经管费率x产品成本
	//核算成本=产品成本+经管费
	var managementCostRate = '2';
	managementCostRate = currencyToFloat(managementCostRate) / 100;//费率百分比转换
	
	var laborCost = laborCostSum();
	var bomCost = productCostSum();
	
	var fmaterialCost = bomCost - laborCost;
	var productCost = bomCost * 1.1;		
	var ftotalCost = productCost * ( 1 + managementCostRate );

	$('#bomCost').html(floatToCurrency(bomCost));
	$('#productCost').html(floatToCurrency(productCost));
	$('#laborCost').html(floatToCurrency(laborCost));
	$('#materialCost').html(floatToCurrency(fmaterialCost));
	$('#totalCost').html(floatToCurrency(ftotalCost));
	$('#bomPlan\\.bomcost').val(floatToCurrency(bomCost));
	$('#bomPlan\\.productcost').val(floatToCurrency(productCost));
	$('#bomPlan\\.laborcost').val(floatToCurrency(laborCost));
	$('#bomPlan\\.materialcost').val(floatToCurrency(fmaterialCost));
	$('#bomPlan\\.totalcost').val(floatToCurrency(ftotalCost));
	//alert('labor:'+laborCost+'--product:'+productCost)

}

function printPage(preview)
{
	try {
		var content=window.document.body.innerHTML;
		var oricontent=content;
		while(content.indexOf("{$printhide}")>=0) 
			content=content.replace("{$printhide}","style='display:none'");
		if(content.indexOf("ID=\"PrintControl\"")<0) 
			content=content+"<OBJECT ID=\"PrintControl\" WIDTH=0 HEIGHT=0 CLASSID=\"CLSID:8856F961-340A-11D0-A96B-00C04FD705A2\"></OBJECT>";
		window.document.body.innerHTML=content;
		//PrintControl.ExecWB(7,1)打印预览，(1,1)打开，(4,1)另存为，(17,1)全选，(10,1)属性，(6,1)打印，(6,6)直接打印，(8,1)页面设置
		if(preview==null||preview==false) 
			PrintControl.ExecWB(6,1);
		else 
			PrintControl.ExecWB(7,1); //OLECMDID_PRINT=7; OLECMDEXECOPT_DONTPROMPTUSER=6/OLECMDEXECOPT_PROMPTUSER=1
		window.document.body.innerHTML=oricontent;
	}
	catch(ex){
		alert("执行Javascript脚本出错。"); 
	}
}

function printConten(preview, html){
	
	//try{
		var content=html;
		var oricontent=window.document.body.innerHTML;
		while(content.indexOf("{$printhide}")>=0) {
			content=content.replace("{$printhide}","style='display:none'");
		}
		//if(content.indexOf("ID=\"PrintControl\"")<0) 
		content=content+"<OBJECT ID=\"PrintControl\" WIDTH=0 HEIGHT=0 CLASSID=\"CLSID:8856F961-340A-11D0-A96B-00C04FD705A2\"></OBJECT>";
		window.document.body.innerHTML=content;
		PrintControl.ExecWB(8,1);// 打印预览，(1,1)打开，(4,1)另存为，(17,1)全选，(10,1)属性，(6,1)打印，(6,6)直接打印，(8,1)页面设置
		
		if(preview==null || preview==false) {
			//PrintControl.ExecWB(6,1);
		}else {

			//alert(11)
			
			//PrintControl.ExecWB(7,1); //OLECMDID_PRINT=7; OLECMDEXECOPT_DONTPROMPTUSER=6/OLECMDEXECOPT_PROMPTUSER=1
		}
		//window.document.body.innerHTML=oricontent;
	//}
	//catch(ex){ 
	//	alert("执行Javascript脚本出错。"); 
	//}
	
}


function openPrint() {
	
	var url = '${ctx}/business/requirement?methodtype=printRequirement';
	var YSId = '${order.YSId}';
	var bomId = '${attrForm.bomPlan.bomid }';
	url = url + '&YSId=' + YSId+ '&bomId=' + bomId;

	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1000px','500px' ], 
		scrollbar : true,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		 // documentaryShow();
		  return false; 
		}    
	});		
}

</script>

</body>
	
</html>
