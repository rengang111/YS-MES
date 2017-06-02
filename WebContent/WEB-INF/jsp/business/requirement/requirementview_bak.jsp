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
<title>订单采购方案--物料需求表查看</title>
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
		
		baseBomView();//分离数量
		
		
		$(".goBack").click(
				function() {
					var YSId = '${order.YSId}';
					var materialId = '${order.materialId}';
					var url = '${ctx}/business/bom?methodtype=orderDetail&YSId=' + YSId+'&materialId='+materialId;
		
					location.href = url;		
		});

		$("#doEdit").click(function() {
			var YSId = '${order.YSId}';
			var bomId=$('#bomId').text();
			var url = "${ctx}/business/requirement?methodtype=editRequirement&YSId="+YSId+"&bomId="+bomId;
			location.href = url;		
		});
		
		$("#doReset").click(function() {
			var order = '${order.quantity}';
			order = order.replace(/,/g, "");
			var materialId='${order.materialId}';
			var YSId = '${order.YSId}';
			var url = "${ctx}/business/requirement?methodtype=resetRequirement&YSId="+YSId+"&materialId="+materialId+"&order="+order;
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
	    //tab_option = $('#tabs').tabs('getTab',"采购合同").panel('options').tab;  
	    //tab_option.hide();  
		
		

		
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
			<li><a href="#tabs-1" class="tabs1">物料需求表</a></li>
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
	</div>
		
<div style="clear: both"></div>		
</form:form>

</div>
</div>

<script  type="text/javascript">
function baseBomView() {

	var materialId='${order.materialId}';
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

</script>


<script  type="text/javascript">

function requirementAjax() {

	var scrollHeight = $(window).height() - 250;
	var t3 = $('#example').DataTable({
		"destroy":true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
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
      //  "fixedColumns":   { leftColumns: 3 },
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
			 "columnDefs": [{    
                 "targets": [ 4,5,10 ], //隐藏第1列，从第0列开始   
                 "visible": false    
	     	}],
		
	}).draw();

	
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
