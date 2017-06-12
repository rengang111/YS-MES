<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购方案--采购方案(查看)</title>
<%@ include file="../../common/common2.jsp"%>
 <style>th, td { white-space: nowrap; }</style>
  	
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
		
		
		orderBomView();//订单BOM
		
		
		$(".goBack").click(
				function() {
					var YSId = '${order.YSId}';
					var url = '${ctx}/business/requirement?methodtype=purchasePlanListInit&keyBackup='+YSId;
					location.href = url;		
		});	
	
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
		<form:hidden path="orderBom.bomid" value="${bomId }" />
		<input type="hidden" id="price" />
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:150px;">${order.YSId}
					<form:hidden path="orderBom.ysid"  value="${order.YSId}" /></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;">${order.materialId}
					<form:hidden path="orderBom.materialid"  value="${order.materialId}" /></td>
				
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
				
		<fieldset class="action" style="text-align: right;margin-top: -5px;">
			<button type="button" id="goBack" class="DTTT_button goBack">返回</button>
		</fieldset>	
		
	
		<fieldset>
			<legend> 订单BOM详情</legend>
			<table id="orderBom" class="display" style="width:98%">
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:120px">物料编码</th>
						<th class="dt-center" >物料名称</th>
						<th class="dt-center" style="width:60px">用量</th>
						<th class="dt-center" style="width:30px">单位</th>
						<th class="dt-center" style="width:60px">供应商</th>
						<th class="dt-center" style="width:60px">订单数量</th>
						<th class="dt-center" style="width:80px">总量</th>
						<th class="dt-center" style="width:60px">单价</th>
						<th class="dt-center" style="width:60px">总价</th>
					</tr>
				</thead>			
			</table>
		</fieldset>
			
	<div style="clear: both"></div>		
</form:form>
</div>
</div>

<script  type="text/javascript">
function orderBomView() {

	var YSId='${order.YSId}';
	var table = $('#orderBom').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#orderBom').DataTable({
		"paging": false,
		"processing" : true,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/requirement?methodtype=getOrderBom&YSId="+YSId,				
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
    			name = jQuery.fixedWidth(name,40);				    			
    			return name;
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var materialId = row["materialId"];
    			var rtn= "<a href=\"###\" onClick=\"doEditMaterial('" + row["materialRecordId"] +"','"+ row["parentId"] + "')\">"+materialId+"</a>";
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
    			var price = currencyToFloat(row["price"] );
    			var order = currencyToFloat('${order.quantity}' );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var total = floatToCurrency( order * quantity * price);		    			
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

	
}//ajax()


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

};


</script>

</body>
	
</html>
