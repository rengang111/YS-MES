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
<title>BOM方案--打印</title>
<%@ include file="../../common/common2.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/print.css" />
<script type="text/javascript">

	var counter  = 0;
	
	$(document).ready(function() {
		
		
		baseBomView();//订单BOM
		
	});
	
	
</script>
</head>
<body>
<button type="button" id="print" onclick="doPrint();"class="DTTT_button " style="float: right;margin: 15px 20px -54px 0px;height: 40px;width: 70px;">打印</button>

<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
		
		
		<input type="hidden" id="tmpMaterialId" />
		<table class="form" id="table_form" style="height:50px;border: 1px solid #c0c0c0;">
			<tr> 							
				<td class="label" style="width:100px;"><label>产品编号：</label></td>					
				<td style="width:120px;">${product.materialId}</td>
			
				<td class="label" style="width:100px;"><label>产品名称：</label></td>				
				<td>&nbsp;${product.materialName}</td>
			</tr>						
		</table>
		<!--  
		<table class="form" id="table_form2">
			<tr>
				<td class="td-center" width="180px"><label>BOM编号</label></td>
				<td class="td-center"><label>材料成本</label></td>
				<td class="td-center"><label>人工成本</label></td>
				<td class="td-center"><label> BOM成本</label></td>
				<td class="td-center"><label>基础成本</label></td>
				<td class="td-center"><label>经管费率</label></td>
				<td class="td-center"><label>核算成本</label></td>
			</tr>
			<tr>
				<td class="td-center"><span id="bomId">${bomId }</span></td>
				<td class="td-center"><span id="materialCost"></span></td>
				<td class="td-center"><span id="laborCost"></span></td>
				<td class="td-center"><span id="bomCost"></span></td>
				<td class="td-center"><span id="productCost"></span></td>
				<td class="td-center"><span id="costRate">2</span>%</td>
				<td class="td-center"><span id="totalCost"></span></td>
			</tr>								
		</table>
		-->
		<div class="list">	
			<table id="orderBomTable" class="display" >
				<thead>				
					<tr>
						<th style="width:30px">No</th>
						<th class="dt-center" style="width:120px">物料编码</th>
						<th class="dt-center" style="width:200px">物料名称</th>
						<th class="dt-center" style="width:80px">供应商</th>
						<th class="dt-center" style="width:80px">单价</th>
						<th class="dt-center" style="width:60px">用量</th>
						<th class="dt-center" style="width:35px">单位</th>
						<th class="dt-center" style="width:80px">总价</th>
					</tr>
				</thead>			
			</table>
		</div>
	</form:form>

</div>
</div>


<script  type="text/javascript">

//列合计:总价
function productCostSum(){

	var sum = 0;
	$('#orderBomTable tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(7).text();
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
		var vtotal = $(this).find("td").eq(7).text();
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


function baseBomView() {

	var materialId='${product.materialId}';
	
	var t2 = $('#orderBomTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
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

					costAcount();
					
					$('#orderBomTable thead tr').each (function (){
						$(this).find("th").eq(0).css('width','30px ');
						$(this).find("th").eq(1).css('width','150px');
						// $(this).find("th").eq(2).css('width','250px');
						$(this).find("th").eq(3).css('width','60px');
						$(this).find("th").eq(4).css('width','80px');
						$(this).find("th").eq(5).css('width','80px');
						$(this).find("th").eq(6).css('width','30px');
						$(this).find("th").eq(7).css('width','50px');
					})
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
			{"data": "price","className" : 'td-right'},
			{"data": "quantity","className" : 'td-right'},
			{"data": "unit","className" : 'td-center'},
			{"data": null,"className" : 'td-right'},
		 ],
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];				    			
    			name = jQuery.fixedWidth(name,25);				    			
    			return name;
    		}},
    		{"targets":7,"render":function(data, type, row){
    			var price = currencyToFloat( row["price"] );
    			var order = currencyToFloat('${order.quantity}' );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var totalPrice = floatToCurrency( price * quantity );			    			
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

function doPrint(){
	
	var headstr = "<html><head><title></title></head><body>";  
	var footstr = "</body>";
	var newstr = document.getElementById("main").innerHTML;
	//var cont = document.body.innerHTML;    
	//$("#print").addClass('noprint');      
	var oldstr = window.document.body.innerHTML;
	document.body.innerHTML = headstr+newstr+footstr;  
	window.print();
	document.body.innerHTML = oldstr;  
	
}
</script>

</body>
	
</html>
