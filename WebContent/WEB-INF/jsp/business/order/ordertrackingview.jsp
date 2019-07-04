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

$('#contract').dataTable.ext.search.push(function( settings, data, dataIndex ) {
	       
	var type =  $('#selectedPurchaseType').val();
	       	
    	//return true;
    	
    	var data11=data[2];//物料编码	   
	    		
 		
    	if (type =='' || type == 'all')		    		
    	{		    		
    		return true;
    		
    	}else if(type=='dz'){//电子
    		var val=data[2];
    		var tmp = val.substring(0,3);
    		//alert(tmp)
    		if(tmp == 'B05' || tmp == 'B06' ||tmp == 'B07' || tmp == 'B08' ||
    		   tmp == 'B09' || tmp == 'B10' ||tmp == 'B16'){
    			return true;
    		}
    		
    	}else if(type=='wj'){//五金
    		var val=data[2];
    		var tmp = val.substring(0,3);
    		var tmp2= val.substring(0,1);
    		
    		if(tmp == 'B02' || tmp == 'B03' ||tmp == 'B04' ||tmp == 'B11' || 
    			tmp == 'B12' || tmp == 'B13' ||tmp == 'B14' ||tmp == 'B15' ||
    			tmp == 'B17' || tmp == 'B18' ||tmp == 'B19' || tmp2 == 'C'|| tmp2 == 'E' ||  tmp2 == 'H'){
    			return true;
    		}
    		
    	}else if(type=='bz'){//包装品(C,E,F,G)
    		var val=data[2];
    		var tmp = val.substring(0,1);
    		
    		if(tmp == 'C' || tmp == 'F' ||tmp == 'G' ){
    			return true;
    		}
    		
    	}else if(type=='zz'){//自制品
    		var val=data[2];
    		var tmp = val.substring(0,3);
    		
    		if(tmp == 'B01'){
    			return true;
    		}
    		
    	}else if(type=='ycl'){//原材料
    		var val=data[9];
    		var tmp = val.substring(3,0);
    		
    		if(tmp == '040'){
    			return true;
    		}
    		
    	}else if(type=='wll'){//未领物料
    		var val5=data[5];//已领数量
    		var val4=data[4];//计划用量
    		var jihua = currencyToFloat(val4);
    		var yiling = currencyToFloat(val5);
    		
    		if(yiling < jihua){
    			return true;
    		}
    		
    	}else{

	    	return false;
    		
    	}    	  
 
});



	$(document).ready(function() {		
		
		contractInfo();//合同信息	
				
		planAndStorage();//库存页面
		
		$("#goBack").click(function() {

			var YSId ="${order.YSId}";
			var url = '${ctx}/business/orderTrack?methodtype=orderTrackingSearchInit';
	
			location.href = url;		
		});
		
		var table = $('#contract').DataTable();
		// Event listener to the two range filtering inputs to redraw on input
	    $('#wj, #zz, #bz, #dz, #all').click( function() {
	    	
	    	 $('#selectedPurchaseType').val($(this).attr('id'));
    		 table.draw();
	    } );
		
	    buttonSelectedEvent();//按钮点击效果

	    $('#all').trigger('click');//默认点击事件
	
	});

	
	function planAndStorage() {

		var scrollHeight = $(window).height() - 240;
		var YSId='${order.YSId}';
		var table = $('#storage').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var actionUrl = "${ctx}/business/orderTrack?methodtype=orderTrackingForStorage"
				+"&YSId="+YSId+"&materialH=H";
		var t = $('#storage').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
	        "ordering"  : true,
	        "autoWidth": false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	        //"scrollCollapse": false,
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
				{"data": "manufactureQuantity","className" : '',"className" : 'td-right'},//3.订单数量
				{"data": "quantityOnHand","className" : 'td-right'},//4.当前库存
				{"data": "waitStockIn","className" : 'td-right', "defaultContent" : '0'},//5.待入
				{"data": "waitStockOut","className" : 'td-right'},//6.待出库
				{"data": "availabelToPromise","className" : 'td-right', "defaultContent" : '0'},//7.虚拟库存				
				],
				
			"columnDefs":[
	    		{"targets":2,"render":function(data, type, row){	 			
	    			return jQuery.fixedWidth(row["materialName"],48);	
	    		}},
	    		{
					"visible" : false,
					"targets" : []
				},
	    		//{ "bSortable": false, "aTargets": [0] }
	          
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
		
		
	}//库存页面
	
</script>	

<script type="text/javascript">	

	function contractInfo() {

		var YSId='${order.YSId}';
		var table = $('#contract').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var actionUrl = "${ctx}/business/orderTrack?methodtype=getContractByYsid"
				+"&YSId="+YSId+"&materialH=H";
		var t = $('#contract').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
	        "ordering"  : true,
	        "autoWidth": false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	        //"scrollCollapse": false,
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

						setDeliveryDate();
						
						inputDateInit();//日期格式初始化
						
						foucsInit();//input格式化
						
						errorCheckAndCostCount();
						
						setContractCount();//计算数量汇总
						
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
				//{"data": null,"className" : 'td-center',"sWidth": "15px"},//
				{"data": "contractId","className" : 'td-left'},//0.合同编号
				{"data": "shortName","className" : 'td-center', "defaultContent" : ''},//1.供应商
				{"data": "materialId","className" : 'td-left',"sWidth": "100px"},//2.物料编号
				{"data": null,"defaultContent" : ''},//3.物料名称
				{"data": "deliveryDate","className" : 'td-center', "defaultContent" : ''},//4.合同交期
				{"data": "contractQty","className" : 'td-right'},//5.合同数量
				{"data": "stockinQty","className" : 'td-right'},//6.入库数量
				{"data": null,"className" : 'td-right'},//7.数量总汇
				{"data": null,"className" : 'td-center'},//8.交期总汇
				{"data": "deliveryDate","className" : 'td-right'},//9.最新交期
				{"data": null,"className" : 'td-right', "defaultContent" : ''},//10.更新时间			
			],
			"columnDefs":[
	    		
	    		{"targets":0,"render":function(data, type, row){
	    			var index = row["rownum"] - 1;
	    			var hidden = '<input type="hidden" id="supplierId'+index+'" name="supplierId" value="'+row['supplierId']+'" >';
					
	    			return hidden + "<a href=\"###\" onClick=\"doShowControct('" + row["contractId"] + "','" + row["quantity"] + "','" + row["arrivalQty"] + "','" + row["contractStorage"] + "')\">"+row["contractId"]+"</a>";			    			
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			
	    			return "<a href=\"###\" onClick=\"doShowSupplier('" + row["supplierRecordId"] + "')\">"+row["shortName"]+"</a>";			    			
	    		}},
	    		{"targets":3,"render":function(data, type, row){	 			
	    			return jQuery.fixedWidth(row["materialName"],48);	
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			
	    			return "<a href=\"###\" onClick=\"doShowStockin('" + row["contractId"] + "')\">"+floatToNumber(data)+"</a>";			    			
					
	    		}},
	    		{"targets":9,"render":function(data, type, row){
	    			var index = row["rownum"] - 1;
					var deliveryDate    = row["deliveryDate"];
					var newDeliveryDate = row["newDeliveryDate"];
					var materialDelivery = row["materialDeliveryDate"];
					var materialId   = row["materialId"];
					var contractId   = row["contractId"];
					var stockinQty   = currencyToFloat(row["stockinQty"]);
					var contractQty  = currencyToFloat(row["contractQty"]);
					
					if(stockinQty >= contractQty){
						var hidden = "";
						hidden += '<input type="hidden" id="stockinFlag'+index+'" name="stockinFlag" value="1" >';
						return hidden + "已入库";
					}else{
						if(materialDelivery == ''){
							if(newDeliveryDate == ''){
								newDeliveryDate = deliveryDate;		
							}
						}else{
							newDeliveryDate = materialDelivery;							
						}
						
						var hidden = "";
						hidden += '<input type="hidden" id="stockinFlag'+index+'"   value="0" name="stockinFlag">';
						hidden += '<input type="hidden" id="materialId'+index+'"    value='+materialId+' >';
						hidden += '<input type="hidden" id="contractId'+index+'"    value='+contractId+' >';
						hidden += '<input type="hidden" id="deliveryDate'+index+'"  value='+newDeliveryDate+' >';
						
						var spanDate = '<span id=span'+index+'>'+newDeliveryDate+'</span>';
						var inputDate= '<input type="text" id="deliveryInput'+index+'" name="deliveryDate" value='+newDeliveryDate+' class="deliverDate"  style="width: 80px;display:none;" >';


						var	text = '';
						text +=	'<input type="image" id="edit'+index+'"   name="edit'+index+'"   src="${ctx}/images/action_edit.png"   onclick="editbtn('+index+');return false;"   title="编辑" style="border: 0;width: 10px;" >';
						text += '<input type="image" id="save'+index+'"   name="save'+index+'"   src="${ctx}/images/action_save.png"   onclick="savebtn('+index+');return false;"   title="保存" style="border: 0;width: 10px;display:none;" >';
						text += '<input type="image" id="cancel'+index+'" name="cancel'+index+'" src="${ctx}/images/action_cancel.png" onclick="cancelbtn('+index+');return false;" title="取消" style="border: 0;width: 10px;display:none;" >';
						
						return spanDate + inputDate + text + hidden;
					}
					
						
						
	    		}},
	    		{"targets":7,"render":function(data, type, row){	 			
	    			return "";	
	    		}},
	    		{"targets":8,"render":function(data, type, row){	 			
	    			return "";	
	    		}},
	    		{"targets":10,"render":function(data, type, row){
	    			var newUpdateDate = row['newUpdateDate'];
	    			if(newUpdateDate == '')
	    				return '***'
	    			else
	    				return newUpdateDate;	
	    		}},
	    		{
					"visible" : false,
					"targets" : []
				},
	    		//{ "bSortable": false, "aTargets": [0] }
	          
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
				
		
	}//ajax()
		
	function setDeliveryDate(){
		
		var wjList = new Array(); 
		var dzList = new Array(); 
		var zzList = new Array(); 
		var wjindex = 0,wjFlag = false;
		var dzindex = 0,dzFlag = false;
		var zzindex = 0,zzFlag = false;
		
		$('#contract tbody tr').each (function (){

			var val  = $(this).find("td").eq(2).text();//
    	 	var date = $(this).find("td").eq(9).find('input[name=deliveryDate]').val();
    	 	var flag = $(this).find("td").eq(9).find('input[name=stockinFlag]').val();//是否入库标识

    		var tmp  = val.substring(0,3);	
    		var tmp1 = val.substring(0,1);			
		
	    	if(tmp == 'B05' || tmp == 'B06' ||tmp == 'B07' || tmp == 'B08' ||
	    		   tmp == 'B09' || tmp == 'B10' ||tmp == 'B16'){
				//电子
				if(flag == '0'){//未入库
		    		dzList[dzindex] = date;
		    		dzindex ++;
				}
				dzFlag = true;					
				
	    		
    		}else if(tmp == 'B02' || tmp == 'B03' ||tmp == 'B04' ||tmp == 'B11' || 
	    			tmp == 'B12' || tmp == 'B13' ||tmp == 'B14' ||tmp == 'B15' ||
	    			tmp == 'B17' || tmp == 'B18' ||tmp == 'B19' || tmp1== 'C'|| tmp1 == 'E' ||  tmp1 == 'H'){
				//五金
				if(flag == '0'){//未入库
	    			wjList[wjindex] = date;
		    		wjindex ++;
				}
				wjFlag = true;				
				
  	    		
	    	}else if(tmp == 'B01'){//**********************自制件

				if(flag == '0'){//未入库
		    		zzList[zzindex] = date;
		    		zzindex ++;
				}
				zzFlag = true;
	    	}
						
		});	
		//**********************五金件********************
		var wjmax = '***';
		if(wjFlag == true){
			if(wjList.length > 0){
				wjList.sort();
				wjmax = wjList[wjList.length -1];
				var today = shortToday();
				if(wjmax < today)
					$('#wjmax').addClass('error');
			}else{
				wjmax = '已入库';				
			}
				
		}
		//**********************电子件********************
		var dzmax = '***';
		if(dzFlag == true){
			if(dzList.length > 0){

				dzList.sort();
				dzmax = dzList[dzList.length -1];
				var today = shortToday();
				if(dzmax < today)
					$('#dzmax').addClass('error');	
			}else{
				dzmax = '已入库';
			}
	
		}
		//**********************自制件********************
		var zzmax = '***';		
		if(zzFlag == true){
			if(zzList.length > 0){
				zzList.sort();
				zzmax = zzList[zzList.length -1];
				var today = shortToday();
				if(zzmax < today)
					$('#zzmax').addClass('error');	
			}else{
				zzmax = '已入库';
			}			
		}
		
		$('#wjmax').text(wjmax);
		$('#dzmax').text(dzmax);
		$('#zzmax').text(zzmax);
			
		//装配物料时间
		var zpmax = '';
		var zpList =  new Array();
		var index = 0;

		if(wjmax != '***' && wjmax != '已入库'){
			zpList[index] = wjmax;
			index++;
		}
		if(zzmax != '***' && zzmax != '已入库'){
			zpList[index] = zzmax;
			index++;
		}
		if(dzmax != '***' && dzmax != '已入库'){
			zpList[index] = dzmax;
			index++;
		}
		if(zpList.length>0){
			zpList.sort();
			zpmax = zpList[zpList.length -1];	
			var today = shortToday();
			if(zpmax < today)
				$('#zpmax').addClass('error');	
		}else{
			zpmax = '已入库';
		}

		$('#zpmax').text(zpmax);
	}
	
	function deleteRow(){
		
		$('#contract tbody tr').each (function (){

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
	
	function setContractCount(){
		
		var cost = 0;
		
		$('#contract tbody tr').each (function (){

			var materialId   = $(this).find("td").eq(2).text();//物料
    	 	var supplierId = $(this).find("td").eq(0).find('input[name=supplierId]').val();
			
			var list = ajaxContractByMaterialId(supplierId,materialId);
			//alert('list'+list)
			if(list[0] == 0){

				$(this).find("td").eq(8).html(list[1]);
			}else{
				$(this).find("td").eq(8).html(list[1]+'<br>' +list[2]);
			}
			var text = "<a href=\"###\" onClick=\"doShowUnStockinList('" + materialId + "')\">"+list[0]+"</a>";			    			
			
			$(this).find("td").eq(7).html(text);
					
						
		});	
		
}
	//计算物料别的未收货汇总
	function ajaxContractByMaterialId(supplierId,materialId) {
		
		var result;
		
		var url = "${ctx}/business/orderTrack?methodtype=getUnStorageContractCount";
		url = url + "&supplierId=" +supplierId;
		url = url + "&materialId=" +materialId;
		
		$.ajax({
			type : "post",
			url : url,
			async : false,
			data : 'key=',
			dataType : "json",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				
				var list = new Array();
				var qty = data['data'][0]['suplus'];
				var min = data['data'][0]['deliveryDateMin'];
				var max = data['data'][0]['deliveryDateMax'];
				
				if(currencyToFloat(qty) == 0){
					qty = 0;
					min = '***';
					max = '***';
				} 
				list[0] = qty;
				list[1] = min;	
				list[2] = max;
				 
				
				result = list;
			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {
			}
		});
		
		
		return result;


	}
	
	function editbtn(index){
		//alert('index'+index)
		$("#save"+index).show();
		$("#cancel"+index).show();
		$("#edit"+index).hide();

		$("#span"+index).hide();
		$("#deliveryInput"+index).show();
		
		return false;
	}


	function cancelbtn(index){
		$("#save"+index).hide();
		$("#cancel"+index).hide();
		$("#edit"+index).show();

		$("#span"+index).show();
		$("#deliveryInput"+index).hide();
		
		return false;
	}

	function savebtn(index){
		$("#save"+index).hide();
		$("#cancel"+index).hide();
		$("#edit"+index).show();

		var YSId = '${order.YSId}';
		var newDelivery  = $("#deliveryInput"+index).val();
		var materialId   = $("#materialId"+index).val();
		var contractId   = $("#contractId"+index).val();
		var contractId   = $("#contractId"+index).val();
		var deliveryDate = $("#deliveryDate"+index).val();

		var par = "&deliveryDate=" + deliveryDate+
					"&materialId=" + materialId+
					"&contractId=" + contractId+
					"&YSId=" 	   + YSId+
					"&newDeliveryDate=" + newDelivery;
		
		
		if (deliveryDate != "") {
			
			$.ajax({
				contentType : 'application/json',
				dataType : 'json',						
				type : "POST",
				data : deliveryDate,// 要提交的表单						
				url : "${ctx}/business/orderTrack?methodtype=updateContracDeliveryDate"+par,
				success : function(d) {	
					
					$('#contract').DataTable().ajax.reload(null,false);
					$().toastmessage('showWarningToast', "合同交期更新成功。");
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					
					//发生系统异常，请再试或者联系管理员。
					alert("发生系统异常，，请再试或者联系管理员。");
				}
			});
			
		} else {
			$().toastmessage('showWarningToast', "请输入有效日期");
			return false;
		}
	}

	function inputDateInit(){
		
		$(".deliverDate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 
	}
	
	function doShowControct(contractId,quantity,arrivalQty,stockinQty) {

		var deleteFlag = '0';
		//var editFlag = '1';
		//if(currencyToFloat(arrivalQty) > 0){
		//	deleteFlag = '0';//已经在执行中,不能删除
		//}
		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId+'&deleteFlag=' + deleteFlag;
		
		callWindowFullView("合同详情",url);
	}	
	
	function doShowSupplier(key) {

		var url = "${ctx}/business/supplier?methodtype=show&key=" + key;
		callWindowFullView("合同详情",url);
	}
	
	function doShowStockin(contractId) {

		var url = "${ctx}/business/orderTrack?methodtype=showStorageListInit&contractId=" + contractId;
		callWindowFullView("入库详情",url);
	}
	
	
	function doShowUnStockinList(materialId) {

		var url = "${ctx}/business/orderTrack?methodtype=getUnStockinContractListInit&contractId="+"&materialId="+materialId;
		callWindowFullView("未入库明细",url);
	}	
	
	function setMaterialFinished(){

		var YSId  = '${order.YSId}';
		
		var zpmax =$('#zpmax').text();
		if(zpmax != '已入库'){
			$().toastmessage('showWarningToast', "物料未备齐。");
			return;
		}

		var par = "&YSId=" + YSId;
		$.ajax({
			contentType : 'application/json',
			dataType : 'json',						
			type : "POST",
			data : "",// 要提交的表单						
			url : "${ctx}/business/orderTrack?methodtype=setMaterialFinished"+par,
			success : function(d) {	
				
				$().toastmessage('showWarningToast', "选入成功。");
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
				//发生系统异常，请再试或者联系管理员。
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});
	}
	
	function errorCheckAndCostCount(){
		
		var cost = 0;
		$('#contract tbody tr').each (function (){
			
    	 	var deliveryDate  = $(this).find("td").eq(9).find('input[name=deliveryDate]').val();
						
			var today = shortToday();
			
			if( deliveryDate < today){
				
				$(this).addClass('error');
			}
						
		});	
	}
		
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
			<legend> 基本信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:100px;">${order.YSId}</td>
								
					<td class="label" style="width:80px;"><label>产品编号：</label></td>					
					<td style="width:100px;"><a href="###" onClick="doShowProduct()">${order.materialId}</a></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td colspan="3">${order.materialName}</td>
					
					<td class="label" style="width:100px;">订单交期：</td>
					<td style="width:150px;">${order.deliveryDate}</td>
				</tr>
				<tr>
					<td class="label"><label>产品数量：</label></td>
					<td><span id="quantity">${order.quantity}</span></td>
					
					<td class="label"><label>业务小组：</label></td>
					<td>${order.team}</td>
						
					<td class="label"><label>客户名称：</label></td>
					<td colspan="3">${order.customerFullName}</td>

					<td class="label"><label>实际交期：</label></td>
					<td><span id="quantity"></span></td>

				</tr>
			</table>
			<table style="width: 100%;">	
				<tr>
					<td class="label" style="width: 150px;">装配物料备齐时间：</td>
					<td style="width: 100px;"><span id="zpmax"></span></td>

					<td class="label" style="width: 150px;">自制件备齐时间：</td>
					<td style="width: 100px;"><span id="zzmax"></span></td>
						
					<td class="label" style="width: 150px;">电子件备齐时间：</td>
					<td style="width:100px;"><span id="dzmax"></span></td>

					<td class="label" style="width:150px;">五金件备齐时间：</td>
					<td style="width: 100px;"><span id="wjmax"></span></td>

					<td><!-- a class="DTTT_button" id="" onclick="setMaterialFinished();return false;">选入料已备齐</a --></td>

				</tr>							
			</table>
		</fieldset>
		
		
		<fieldset style="">
			<legend> 合同信息</legend>			 
			<div class="list">
				<div id="DTTT_container" >
					<a class="DTTT_button  box" id="all" data-id="9">ALL</a>
					<a class="DTTT_button  box" id="zz"  data-id="4">自制件</a>
					<a class="DTTT_button  box" id="dz"  data-id="1">电子</a>
					<a class="DTTT_button  box" id="wj"  data-id="0">五金</a>
				 	<a class="DTTT_button  box" id="bz"  data-id="3">包装</a>
					<input type="hidden" id="selectedPurchaseType" />
				</div>
				<table id="contract" class="display" >
					<thead>				
						<tr>
							<th width="80px">合同编号</th>
							<th width="50px">工厂</th>
							<th width="70px">物料编码</th>
							<th>物料名称</th>
							<th width="60px">合同交期</th>
							<th width="60px">合同数</th>
							<th width="60px">入库数量</th>
							<th width="60px">数量汇总</th>
							<th width="60px">交期汇总</th>
							<th width="60px">最新交期</th>
							<th width="60px">更新时间</th>
						</tr>
					</thead>
				</table>
			</div>
		</fieldset>
		<fieldset style="">
			<legend> 库存页面</legend>
			<div class="list">
				<table id="storage" class="display" >
					<thead>				
						<tr>
							<th width="10px">No</th>
							<th width="70px">物料编码</th>
							<th>物料名称</th>
							<th width="70px">订单数量</th>
							<th width="70px">当前库存</th>
							<th width="70px">待入</th>
							<th width="70px">待出</th>
							<th width="70px">虚拟库存</th>
						</tr>
					</thead>
				</table>
			</div>
		</fieldset>
	</form:form>
</div>
</div>
</body>
	
</html>
