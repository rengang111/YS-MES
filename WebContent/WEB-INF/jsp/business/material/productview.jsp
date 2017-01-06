<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>


<head>
<title>成品信息-查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
</head>

<body>
<div id="container">
<div id="main">
	
<form:form modelAttribute="material" method="POST" 
	id="material" name="material"   autocomplete="off">
	
	<input type="hidden" id="recordsTotal"  >
	
<fieldset style="float: left;width: 65%;">
	<legend>产品信息</legend>

	<table class="form" >		
		<tr>
			<td class="label" style="width: 100px;"><label>产品编号：</label></td>
			<td style="width: 150px;">
				<label>${product.materialId}</label></td>
								
			<td class="label" style="width: 100px;"><label>产品名称：</label></td>
			<td colspan="3">${product.materialName}</td>												
		</tr>
		<tr>				
			<td class="label" style="width: 100px;"><label>分类编码：</label></td>
			<td colspan="3">${product.categoryIdAndName}</td>				
								
			<td class="label" style="width: 100px;"><label>计量单位：</label></td>
			<td style="width: 50px;text-align: center;">${product.dicName}</td>				
		</tr>
		<tr>
			<td class="label">机器型号：</td>
			<td>${product.productModel }</td>			
			<td class="label">客户名称：</td>
			<td colspan="3">${product.customerIdAndName }</td>
		</tr>
		<tr>
			<td class="label" style="vertical-align: text-top;">中文描述：</td>
			<td colspan="5" style="vertical-align: text-top;"><pre>${product.description }</pre></td>
		</tr>
		<tr>
			<td class="label tree-title">基础成本：</td>
			<td class="tree-title" style="font-weight: bold;"><span id=beseCost></span></td>			
			<td class="label tree-title">核算成本：</td>
			<td class="tree-title" style="font-weight: bold;"colspan="3"><span id=totalCost></span></td>
		</tr>	
	</table>
	<div class="action" style="text-align: right;">			
		<button type="button" id="doEdit" class="DTTT_button" >编辑</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</div>
	</fieldset>	
		<div id="tabs" style="float:right;margin: 10px 30px 0px 0px;">
				<div id="tabs-1" title="图片">
					<jsp:include page="../../common/album/album2.jsp"></jsp:include>
				</div>
		</div>

		
	<dl class="collapse">
		<dt><span id="bomId">基础BOM</span> <button type="button" class="DTTT_button" onclick="doCreateBaseBom();">编辑</button>
			（ 单价颜色变化&nbsp;&nbsp; <span style="color:green"> 绿色：下降</span>&nbsp;&nbsp; <span style="color:red">红色：上涨</span> ）</dt>
		<dd>
		<table id="baseBomTable" class="display">
			<thead>				
				<tr>
					<th width="1px">No</th>
					<th class="dt-center" width="200px">物料编码</th>
					<th class="dt-center" width="350px">物料名称</th>
					<th class="dt-center" width="100px">供应商编号</th>
					<th class="dt-center" width="100px">用量</th>
					<th class="dt-center" width="150px">单价</th>
					<th class="dt-center" width="150px">总价</th>
				</tr>
			</thead>
			
		</table>
		</dd>
	</dl>
<div style="clear: both"></div>		
	
<fieldset style="margin-top: 10px;">
	<legend>客户报价</legend>
	<div class="list">	
		<a class="DTTT_button" onclick="doCreatePrice();" style="float: right;">新建</a>	
		<table id="TSupplier"  class="display dataTable">
			<thead>				
				<tr>
					<th style="width:50px;" class="dt-middle ">No</th>
					<th style="width:100px;" class="dt-middle ">报价时间</th>
					<th style="width:150px;" class="dt-middle ">BOM编号<th>
					<th style="width:280px;"class="dt-middle ">核算价格</th>
					<th style="width:100px;" class="dt-middle ">客户币种</th>
					<th style="width:150px;" class="dt-middle ">客户报价</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="detail" items="${cusBidDetail}" varStatus='status' >						
				<tr>
					<td>${status.index+1}</td>
					<td>${detail.materialId}</td>								
					<td></td>
					<td>${detail.supplierId}</td>
					<td>${detail.quantity}</td>							
					<td>${detail.price}</td>						
					<td></td>					
				</tr>
				
			</c:forEach>
			</tbody>
		</table>
	</div>
</fieldset>
<div style="clear: both"></div>		
	
<fieldset>
	<legend>订单详情</legend>
		
	<div class="list">	
	<a class="DTTT_button" onclick="doCreateOrder();" style="float: right;">新建</a>
		<table id="TSupplier"  class="display dataTable">
			<thead>				
				<tr>
					<th style="width:10px;" class="dt-middle ">No</th>
					<th style="width:100px;" class="dt-middle ">BOM编号</th>
					<th style="width:80px;" class="dt-middle ">下单时间<th>
					<th style="width:80px;"class="dt-middle ">耀升编号</th>
					<th style="width:50px;" class="dt-middle ">客户订单编号</th>
					<th style="width:50px;" class="dt-middle ">订单数量</th>
					<th style="width:50px;" class="dt-middle ">订单交期</th>
					<th style="width:50px;" class="dt-middle ">销售币种</th>
					<th style="width:50px;" class="dt-middle ">单价</th>
					<th style="width:50px;" class="dt-middle ">总价</th>
					<th style="width:50px;" class="dt-middle ">出运条款</th>
					<th style="width:70px;" class="dt-middle ">出运港/目的港</th>
					<th style="width:50px;" class="dt-middle ">付款条件：出运后</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="detail" items="${cusBidDetail}" varStatus='status' >						
				<tr>
					<td>${status.index+1}</td>
					<td>${detail.materialId}</td>								
					<td></td>
					<td>${detail.supplierId}</td>
					<td>${detail.quantity}</td>							
					<td>${detail.price}</td>						
					<td></td>					
				</tr>
				
			</c:forEach>
			</tbody>
		</table>
	</div>
</fieldset>
</form:form>
</div>
</div>
<script type="text/javascript">

$(document).ready(function() {
		
	
	$("#goBack").click(
		function() {
			var materialId='${product.materialId}';
			var url = "${ctx}/business/material?methodtype=productInit&materialId="+materialId;
			location.href = url;		
		});

	$("#doEdit").click(
			function() {
				var recordid = '${product.recordId}';
				var parentid = '${product.parentId}';
				var url = '${ctx}/business/material?methodtype=edit';
				url = url + '&parentId=' + parentid+'&recordId='+recordid;
				location.href = url;		

	});
	
	
	supplierPriceView();//供应商单价显示处理
	
	baseBomView();//显示基础BOM
	
	selectedColor();//供应商列表点击颜色变化
    	
});

//列合计:总价
function productCostSum(){

	var sum = 0;
	$('#baseBomTable tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(6).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

//编辑基础BOM
function doCreateBaseBom() {
	var materialId ='${product.materialId}';
	var productModel = '${product.productModel }';
	var accessFlg = $('#recordsTotal').val();
	var url = '${ctx}/business/bom?methodtype=createBaseBom&materialId=' + materialId+'&model='+productModel+'&accessFlg='+accessFlg;
		location.href = url;
	
}

function baseBomView() {

	var materialId='${product.materialId}';
	var table = $('#baseBomTable').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#baseBomTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"sAjaxSource" : "${ctx}/business/bom?methodtype=getBaseBom&materialId="+materialId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);
					
					$('#recordsTotal').val(data['recordsTotal']);
			
					var productCost = data['data'][0]['productCost'];
					
					var price = productCostSum();
					var total = price * 1.1 * 1.02;

					var fproductCost = currencyToFloat(productCost);
					var fprice = currencyToFloat(price).toFixed(2);
					
					var vprice = floatToCurrency(price);
					var vtotal = floatToCurrency(total);
					
					//alert('fprice:'+fprice+'fproductCost:'+fproductCost)
					if(fprice > fproductCost){
						vprice = '<div style="color:red">' + vprice + '</div>';
						vtotal = '<div style="color:red">' + vtotal + '</div>';
					}else if (fprice < fproductCost){
						vprice = '<div style="color:green">' + vprice + '</div>';
						vtotal = '<div style="color:green">' + vtotal + '</div>';						
					}
					var bomId = data['data'][0]['bomId'];
					$('#bomId').html(bomId);
					$('#beseCost').html(vprice);
					$('#totalCost').html(vtotal);
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
			{"data": "supplierId"},
			{"data": "quantity","className" : 'td-right'},
			{"data": "price","className" : 'td-right'},
			{"data": "totalPrice","className" : 'td-right'},
		 ],
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];				    			
    			name = jQuery.fixedWidth(name,30);				    			
    			return name;
    		}},
    		{"targets":5,"render":function(data, type, row){
    			
    			var price =  row["price"] ;
    			var fprice = currencyToFloat( price );
    			var oldPrice = currencyToFloat( row["oldPrice"] );

				if(fprice > oldPrice){
					price = '<div style="font-weight:bold;color:red">' + price + '</div>';
				}else if(fprice < oldPrice){
					price = '<div style="font-weight:bold;color:green">' + price + '</div>';
					
				}
    			return price;
    		}},
    		{"targets":6,"render":function(data, type, row){
    			
    			var price = currencyToFloat( row["price"] );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var total = float4ToCurrency( price * quantity );			    			
    			return total;
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

function supplierPriceView() {

	var table = $('#TSupplier').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TSupplier').DataTable({
		"paging": false,
		//"lengthMenu":[20,50,100],//设置一页展示10条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		//"bJQueryUI": true,
		"retrieve" : false,
		//"sAjaxSource" : "${ctx}/business/material?methodtype=supplierPriceView",				
		"fnServerData" : function(sSource, aoData, fnCallback) {
				
			var param = {};
			var formData = $("#material").serializeArray();
			formData.forEach(function(e) {
				//alert(e.name+"=name"+"value= "+e.value)
				aoData.push({"name":e.name, "value":e.value});
			});
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"async": false,
				"data" : JSON.stringify(aoData),
				success: function(data){
					
					if (data.message != undefined) {
						//alert(data.message);
					}
									
					fnCallback(data);
					$.each(data, function (n, value) {
			               $.each(value, function (i, v) {
				               //alert(i + ' == ' + v["supplierId"]);
			               });
			           })
			           
					//重设显示窗口(iframe)高度
					iFramAutoSroll();

				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	                 alert(XMLHttpRequest.status);
	                 alert(XMLHttpRequest.readyState);
	                 alert(textStatus);
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
					{"data": null,"className" : 'td-center'},
					{"data": "supplierId"},
					{"data": "fullName"},
					{"data": "price","className" : 'td-right'},
					{"data": "currency","className" : 'td-center'},
					{"data": "priceDate","className" : 'td-center'},
					{"data": null,"className" : 'td-center'}
		        ],
		"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			
						return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			var edit = "<a href=\"#\" onClick=\"doUpdate('" + row["supplierId"] + "')\">编辑</a>";
		    			var history = "<a href=\"#\" onClick=\"doShowHistory('" + row["supplierId"] + "')\">历史报价</a>";
		    			var delet = "<a href=\"#\" onClick=\"doDelete('" + row["recordId"] + "')\">删除</a>";
		    			
		    			return edit+"&nbsp;"+history+"&nbsp;"+delet;
                    }}	           
	         	] 
		}
	
	);
	
}//ajax()供应商信息

function selectedColor(){

	$('#TSupplier').DataTable().on('click', 'tr', function() {
		
		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
        	$('#TSupplier').DataTable().$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});
	
}


var layerHeight = '360px';
var layerWidth  = '900px';




//新建二级BOM
function doCreateBOMZZ() {
	var materialId ='${material.material.materialid}';
	layerWidth  = '1000px';
	
	if(materialId.length > 0){
		var type = materialId.substring(0,1);//截取物料大分类
		if(type == 'H'){//装配品
			layerHeight = '300px';
			var url = '${ctx}/business/zzmaterial?methodtype=createH&materialId=' + materialId;
		
		}else{//塑料制品
			layerHeight = '450px';
			var url = '${ctx}/business/zzmaterial?methodtype=createB&materialId=' + materialId;
			
		}
	}

	layer.open({
		offset :[50,''],
		type : 2,
		title : false,
		area : [ layerWidth, layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
	});
}

function doUpdate(supplierId) {
	var materialId ='${material.material.materialid}';
	var type = materialId.substring(0,1);//截取物料大分类
	if(supplierId == '0574YS00'){
		if(type == 'H'){
			layerWidth  = '1000px';
			layerHeight = '300px';
			var url = '${ctx}/business/zzmaterial?methodtype=editH&materialId=' + materialId;
			
		}else{
			layerWidth  = '1000px';
			layerHeight = '450px';
			var url = '${ctx}/business/zzmaterial?methodtype=editB&materialId=' + materialId;
			
		}
	}else{
		layerWidth  = '900px';
		layerHeight = '360px';
		var url = "${ctx}/business/material?methodtype=editPrice&supplierId=" + supplierId+"&materialId="+materialId;		
	}

	layer.open({
		offset :[50,''],
		type : 2,
		title : false,
		area : [ layerWidth, layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
	});
}

function doShowHistory(supplierId) {
	var materialId ='${material.material.materialid}';
			
	var url = "${ctx}/business/material?methodtype=supplierPriceHistoryInit&supplierId=" + supplierId+"&materialId="+materialId;

	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '900px', '400px' ], 
		scrollbar : false,
		title : false,
		content : url,
	});
}

</script>
	
<script type="text/javascript">
$(function(){
	var t = [];
	var dt = $("dl.collapse dt");
	var dd = $("dl.collapse dd");
	
	dt.each(function(i){
		t[i] = false;		//设置折叠初始状态
		$(dt[i]).click((function(i,dd){
			
			return function(){		//返回一个闭包函数,闭包能够存储传递进来的动态参数
				
				if(t[i]){					
					$(dd).show();
					t[i] = false;
				}else{
					$(dd).hide();
					t[i] = true;
				}					
			}
		})(i,dd[i]))	//向当前执行函数中传递参数
	})
})
</script>
</body>
</html>
