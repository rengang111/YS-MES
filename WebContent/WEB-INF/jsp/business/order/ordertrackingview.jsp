<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单跟踪--查看</title>
<%@ include file="../../common/common2.jsp"%>
  	
<script type="text/javascript">

/* Custom filtering function which will search data in column four between two values */
$('#example').dataTable.ext.search.push(function( settings, data, dataIndex ) {
	       
	var type =  $('#selectedPurchaseType').val();
	       	
	    	//return true;
	    	
	    	var data11=data[10];
	    	var data01=data[1];
	        var data011 = data01.substring(0,1)
		   
		    		
	    	if (type =='' || type == 'all')		    		
	    	{		    		
	    		return true;
	    		
	    	}else if(type=='dg'){//采购件（有合同，包括自制件）
	    		var val3=data[1];
	    		var contract=data[3];
	    		if(typeof( val3) != 'undefined')
	    			var tmp3 = val3.substring(0,1);
	    		
	    		if(contract != '***' ){
	    			return true;
	    		}	    		
	    		
	    	}else if(type=='bz'){//包装采购件（有合同）
	    		var val=data[1];
	    		var contract=data[3];
	    		if(typeof( val) != 'undefined'){
	    			var tmp = val.substring(0,1);
	    		}
	    		
	    		if(tmp == 'G' && contract != '***'){
	    			return true;
	    		}
	    		
	    	}else if(type=='yz'){//非采购件（无合同）
	    		var contract=data[3];
	    		
	    		if(contract == '***'){
	    			return true;
	    		}
	    		
	    	}else{

		    	return false;
	    		
	    	}
	    	  
 
});

var GcontractStatusFlag="false";

	$(document).ready(function() {		
		
		baseBomView();//基础BOM		
				
		$("#goBack").click(function() {

			var YSId ="${order.YSId}";
			var url = '${ctx}/business/order?methodtype=orderTrackingSearchInit';
	
			location.href = url;		
		});
			
		var table = $('#example').DataTable();
		// Event listener to the two range filtering inputs to redraw on input
	    $('#yz, #ty, #dg, #bz, #all, #ycl, #wll').click( function() {
	    	
	    	 $('#selectedPurchaseType').val($(this).attr('id'));
    		 table.draw();
	    } );
		
	    buttonSelectedEvent();//按钮点击效果

	    $('#dg').trigger('click');//默认点击事件
	});

	
	function baseBomView() {

		var scrollHeight = $(window).height() - 230;
		var YSId='${order.YSId}';
		//var table = $('#example').dataTable();
		//if(table) {
		//	table.fnDestroy();
		//}
		var actionUrl = "${ctx}/business/order?methodtype=orderTrackingDetail&orderType=010"
				+"&YSId="+YSId+"&materialH=H";
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
	        "ordering"  : false,
	        "autoWidth": false,
			"pagingType" : "full_numbers",
			"scrollY"    : scrollHeight,
	        "scrollCollapse": false,
	        //"fixedColumns":   { leftColumns: 2 },
			"dom" 		: '<"clear">rt',
			"sAjaxSource" : actionUrl,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					"data" : null,
					success: function(data){
						fnCallback(data);
						
						//deleteRow();
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		            	alert(errorThrown)
					 }
				})
			},
			
	       	"language": {
	       		"url":"${ctx}/plugins/datatables/chinese.json"
	       	},
			"columns": [
				{"data": null,"className" : 'td-center',"sWidth": "15px"},//0
				{"data": "materialId","className" : 'td-left',"sWidth": "100px"},//1.物料编号
				{"data": null,"defaultContent" : ''},//2.物料名称
				{"data": "deliveryDate","className" : 'td-left', "defaultContent" : ''},//3.合同编号
				{"data": "contractSupplierId","className" : '', "defaultContent" : ''},//4.供应商
				{"data": "contractQty","className" : 'td-right'},//5.合同数量
				{"data": "stockinQty","className" : 'td-right'},//6.入库数量
				{"data": "waitStockIn","className" : 'td-right'},//7.待入库
				{"data": "waitStockOut","className" : 'td-right'},//8.待出库
				{"data": "quantityOnHand","className" : 'td-right', "defaultContent" : '0'},//9.当前库存
				{"data": "availabelToPromise","className" : 'td-right', "defaultContent" : '0'},//10.虚拟库存
				
				],
			"columnDefs":[
	    		{"targets":2,"render":function(data, type, row){	 			
	    			return jQuery.fixedWidth(row["materialName"],48);	
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			if(data == ''){
	    				return '<div style="text-align: center;">***</div>';
	    			}else{
	    				return "<a href=\"###\" onClick=\"doShowContract('"+ row["contractId"] + "')\">"+data+"</a>";
	    			}	    			
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			if(data == ''){
	    				return '<div style="text-align: center;">***</div>';
	    			}else{
	    				return data;
	    			}	    			
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			if(data == '0'){
	    				return '<div style="text-align: center;">***</div>';
	    			}else{
	    				return data;
	    			}	    			
	    		}},
	    		{"targets":6,"render":function(data, type, row){//已入库数量
	    			var contract = currencyToFloat( row["contractQty"] );
	    			var stockin = currencyToFloat( row["stockinQty"] );
	    			if(contract == '0'){
	    				return '<div style="text-align: center;">***</div>';
	    			}else{
	    				if( stockin < contract ){
		    				return '<div style="color: red;font-weight: bold;">'+floatToCurrency(stockin)+'</div>';
	    					
	    				}else{
		    				return floatToCurrency(stockin);
	    				}
	    			}	    			
	    		}},
	    		{"targets":7,"render":function(data, type, row){
	    			
	    			return floatToCurrency(data);
	    			    			
	    		}},
	    		{"targets":8,"render":function(data, type, row){
	    			
	    			return floatToCurrency(data);
	    			    			
	    		}},
	    		{"targets":9,"render":function(data, type, row){
	    			
	    			return floatToCurrency(data);
	    			    			
	    		}},
	    		{
					"visible" : false,
					"targets" : [7,8,10]
				},
	    		{ "bSortable": false, "aTargets": [0] }
	          
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
		
	function deleteRow(){
		
		$('#example tbody tr').each (function (){

			var contract = $(this).find("td").eq(4).text();//
			var stockin = $(this).find("td").eq(5).text();//
			
			contract = currencyToFloat(contract);
			stockin = currencyToFloat(stockin);
			
			if(contract > '0' && stockin < contract){
				//alert(materialId)
				$(this).find("td").parent().addClass('bg-yellow')
			}
						
		});	
		
	}
	
	function doShowContract(contractId) {
		
		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("采购合同",url);
	};
	
</script>


</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">
	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">		
		
		<input type="hidden" id="tmpMaterialId" />
		<input type="hidden" id="backFlag"  value="${backFlag }"/>
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:150px;">${order.YSId}</td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;"><a href="###" onClick="doShowProduct()">${order.materialId}</a>
				</td>
				
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
		
		<fieldset style="margin-top: -20px;">
			<div id="DTTT_container"  style="float:left;height:40px;margin-right: 30px;width: 50%;margin: 15px 0px -10px 10px;">
				<a class="DTTT_button  box" id="all" data-id="4">显示全部</a>
				<a class="DTTT_button  box" id="dg" data-id="1">装配件</a>
				<a class="DTTT_button  box" id="yz" data-id="0">非采购件</a>
			<!-- 	<a class="DTTT_button  box" id="bz" data-id="3">包装采购件</a> -->
				<input type="hidden" id="selectedPurchaseType" />
			</div>
			<div id="DTTT_container"  style="float:right;height:40px;width: 40%;margin: 15px 10px 0px 1px;text-align: end;">
				<a class="DTTT_button  " id="goBack" >返回</a>
				<input type="hidden" id="selectedPurchaseType" />
			</div>
			<div class="list">
				<table id="example" class="display" >
					<thead>				
						<tr>
							<th width="20px">No</th>
							<th width="100px">物料编码</th>
							<th>物料名称</th>
							<th width="80px">合同交期</th>
							<th width="60px">供应商</th>
							<th width="60px">合同数量</th>
							<th width="60px">入库数量</th>
							<th width="60px">待入库</th>
							<th width="60px">待出库</th>
							<th width="60px">实际库存</th>
							<th width="60px">虚拟库存</th>
						</tr>
					</thead>
				</table>
			</div>
		</fieldset>

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

</script>
</body>
	
</html>
