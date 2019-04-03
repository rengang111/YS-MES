<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购方案--查看</title>
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
	    		
	    	}else if(type=='dg'){//订购件
	    		var val1=data[13];
	    		var val2=data[9];
	    		var val3=data[1];
	    		if(typeof( val1) != 'undefined')
		    		var tmp1 = val1.substring(3,0);
	    		if(typeof( val3) != 'undefined')
	    			var tmp3 = val3.substring(0,1);
	    		if(typeof( val2) != 'undefined')
	    			var tmp2 = val2.substring(6,4);
	    		
	    		if(tmp1 == '010' && tmp2 != 'YZ' && tmp3 != 'G' ){
	    			return true;
	    		}
	    		
	    	}else if(type=='ty'){//通用件
	    		var val=data[13];
	    		if(typeof( val) != 'undefined')
	    			var tmp = val.substring(3,0);
	    		
	    		if(tmp == '020'){
	    			return true;
	    		}
	    		
	    	}else if(type=='bz'){//包装品
	    		var val=data[1];
	    		if(typeof( val) != 'undefined')
	    			var tmp = val.substring(0,1);
	    		
	    		if(tmp == 'G'){
	    			return true;
	    		}
	    		
	    	}else if(type=='yz'){//自制品
	    		var val=data[9];
	    		if(typeof( val) != 'undefined')
	    			var tmp = val.substring(6,4);
	    		
	    		if(tmp == 'YZ'){
	    			return true;
	    		}
	    		
	    	}else{

		    	return false;
	    		
	    	}
	    	  
 
});

var GcontractStatusFlag="false";

function initEvent(){
	
	$('#contractTable').DataTable().on('click', 'tr', function() {
		
		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
        	
        	$('#contractTable').DataTable().$('tr.selected').removeClass('selected');
            $(this).addClass('selected');            
            var d = $('#contractTable').DataTable().row(this).data();			
			$('#contractDetail').DataTable().destroy();
			//$('#set_lines').DataTable().ajax.reload();
			//ajax_factory_bid_set_lines(d["bid_id"]);
			//var d = $('#contractTable').DataTable().row(this).data();
			//alert(d["contractId"])
			contractDetailView(d["contractId"]);
				            
        }			
	});
}

	$(document).ready(function() {		

		$("#requisitionType").attr('disabled',true);
		$("#saveRequisitionType").hide();
		
		$("#editRequisitionType").click(function() {
			$("#requisitionType").attr('disabled',false);
			$("#saveRequisitionType").show();
			$("#editRequisitionType").hide();
		});
		
		//保存 领料方式
		$("#saveRequisitionType").click(function() {
			var requisitionType = $('#requisitionType').val();
			var YSId ="${order.YSId}";
			var url = "${ctx}/business/purchasePlan?methodtype=updateRequisitionType";
			url = url + "&YSId="+YSId+"&requisitionType="+requisitionType;

			$.ajax({
				type : "post",
				url : url,
				//async : false,
				//data : null,
				dataType : "json",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {			

					var returnCode = data['returnCode'];
					
					if(returnCode == 'S'){
						$().toastmessage('showNoticeToast', "更新成功。");
					}						
					else{
						$().toastmessage('showNoticeToast', "更新失败，请重试。");
					}
					
					$("#requisitionType").attr('disabled',true);
					$("#saveRequisitionType").hide();
					$("#editRequisitionType").show();
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("系统错误，请联系管理员。")
				}
			});	
		});///保存 领料方式
		
		$(".loading").hide();
		$(".read-only").attr( 'readonly',true)
		$( "#tabs" ).tabs();
		
		baseBomView();//基础BOM
		var now = new Date();  //Wed Jul 05 2017 13:50:11 GMT+0800 (中国标准时间)
		now.setDate(now.getDate() + 20);//默认20天
		$("#contractDelivery").val(formatTime(now));
		$("#contractDelivery").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
		}); 
		
		$("#createPurchaseOrder").click(function() {

			var str = '';
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					str += $(this).val() + ",";
				}
			});
			var materialId='${order.materialId}';
			var YSId ="${order.YSId}";
			var quantity ="${order.quantity}";
			var contractDelivery=$('#contractDelivery').val();
			var actionUrl = "${ctx}/business/contract?methodtype=creatPurchaseOrder&YSId="
			+YSId+"&materialId="+materialId+"&contractDelivery="+contractDelivery
			+"&quantity="+quantity;

			if (str != '') {

				jQuery.ajax({
					type : 'POST',
					async: false,
					contentType : 'application/json',
					dataType : 'json',
					data : str,
					url : actionUrl,
					async: false, //同步请求，默认情况下是异步（true）
					success : function(data) {
						$().toastmessage('showNoticeToast', "合同创建成功。");
						$('#example').DataTable().ajax.reload(false);	
						
					},
					beforeSend: function(){
						$('#createPurchaseOrder').attr("disabled","true").removeClass("DTTT_button");
						$(".loading").show();
					},
					complete: function () {
						$('#createPurchaseOrder').removeAttr("disabled").addClass("DTTT_button");
					    $(".loading").hide();  
					},					
					error:function(XMLHttpRequest, textStatus, errorThrown){
		            	alert("error:"+errorThrown);
					}
				});
			} else {
				alert("请至少选择一条数据");
			}
		});
				
		$(".goBack").click(function() {

			var YSId ="${order.YSId}";
			var url = '${ctx}/business/purchasePlan?keyBackup=' + YSId;
	
			location.href = url;		
		});
		
		$("#editPurchasePlan").click(function() {

			var YSId ="${order.YSId}";

			var editFlag = true;
/*
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : null,
				url : actionUrl,
				async: false, //同步请求，默认情况下是异步（true）
				success : function(data) {
					var record = data["recordCount"];
					if(record > '0'){
						editFlag = false;						
					}
					
				},									
				error:function(XMLHttpRequest, textStatus, errorThrown){
	            	alert("error:"+errorThrown);
				}
			});
			
			if(editFlag == false){			
				$().toastmessage('showWarningToast', "采购方案已经开始执行了,不能修改。");
				return false;
			}		
				*/		
			var materialId='${order.materialId}';
			var quantity ="${order.quantity}";
			var backFlag = $("#backFlag").val();
			$('#attrForm').attr("action",
					"${ctx}/business/purchasePlan?methodtype=purchasePlanEdit"
							+"&YSId="+YSId
							+"&materialId="+materialId
							+"&backFlag="+backFlag
							+"&quantity="+quantity);
			$('#attrForm').submit();
		});
			
		$("#deletePurchasePlan").click(
				function() {

			var productId='${order.materialId}';
			var YSId ="${order.YSId}";
			var quantity ="${order.quantity}";
			var backFlag = $("#backFlag").val();
			if(GcontractStatusFlag == true){
				alert("该方案已生成合同,不能删除！");
				return;					
			}
			if(confirm("删除采购方案后不能恢复,确定要删除吗？")) {
				
				$('#attrForm').attr("action",
						"${ctx}/business/purchasePlan?methodtype=purchasePlanDelete"
								+"&YSId="+YSId
								+"&productId="+productId
								+"&backFlag="+backFlag
								+"&quantity="+quantity);
				$('#attrForm').submit();
			}
			
		});
			
		
		$("#resetPackage").click(
				function() {

			var productId='${order.materialId}';
			var YSId ="${order.YSId}";
			var quantity ="${order.quantity}";
			var backFlag = $("#backFlag").val();
			if(confirm("重置包装件,会删除现有方案中的包装件,\n\n        确定要重置吗？")) {
				
				$('#attrForm').attr("action",
						"${ctx}/business/purchasePlan?methodtype=resetPackageInit"
								+"&YSId="+YSId
								+"&productId="+productId
								+"&backFlag="+backFlag
								+"&materialFlag=G"
								+"&quantity="+quantity);
				$('#attrForm').submit();
			}
			
		});
		
		$("#resetYszz").click(
				function() {

			var productId='${order.materialId}';
			var YSId ="${order.YSId}";
			var quantity ="${order.quantity}";
			var backFlag = $("#backFlag").val();
			if(confirm("重置自制品,会删除现有方案中的自制品,\n\n        确定要重置吗？")) {
				
				$('#attrForm').attr("action",
						"${ctx}/business/purchasePlan?methodtype=resetYszzInit"
								+"&YSId="+YSId
								+"&productId="+productId
								+"&backFlag="+backFlag
								+"&supplierId=0574YZ00"
								+"&quantity="+quantity);
				$('#attrForm').submit();
			}
			
		});
		
		
		$('#example').DataTable().on('click', 'tr', function() {
			
			if(!(typeof($(this).children().last().children().val()) == 'undefined')){
				
				//只处理有checkBox的数据
				$(this).toggleClass("selected");
			    if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
			        
			    	$(this).children().last().children().prop("checked", true);
			    }else{//如果没有被选中

			    	$(this).children().last().children().prop("checked", false);
			    }
			}			
		});	
		
		$(".tabs2").click(function() {
					contractTableView();
					//$('#contractTableView').DataTable().ajax.reload(false);
				});
		
		$("#doSaveCost").click(function(){
			
			var PIId = '${order.PIId}';
			var detailRecordId = '${order.detailRecordId}';

			var url = "${ctx}/business/purchasePlan?methodtype=updateOrderCost"
					+"&PIId="+PIId+"&detailRecordId="+detailRecordId;
		
			$.ajax({
				type : "post",
				url : url,
				async : false,
				data :$("#attrForm").serializeArray(),
				dataType : "json",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {			

					$().toastmessage('showNoticeToast', "保存成功。");
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(textStatus)
				}
			});	
		});
		
		//外汇报价换算
		$(".exchange").change(function() {
			
			costAcount();
		});
		
		var table = $('#example').DataTable();
		// Event listener to the two range filtering inputs to redraw on input
	    $('#yz, #ty, #dg, #bz, #all, #ycl, #wll').click( function() {
	    	
	    	 $('#selectedPurchaseType').val($(this).attr('id'));
    		 table.draw();
	    } );
	    buttonSelectedEvent();//按钮点击效果

		contractTableView();//采购合同
		ZZmaterialView();//二级BOM
		initEvent();//合同明细联动
	});

	
	function baseBomView() {

		var scrollHeight = $(window).height() - 120;
		var YSId='${order.YSId}';
		//var table = $('#example').dataTable();
		//if(table) {
		//	table.fnDestroy();
		//}
		
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
	       //	"fixedColumns":   { leftColumns: 2 },
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
						$('#purchasePlan\\.recordid').val(data["data"][0]["planRecordId"]);
						$('#orderDetail\\.productcost').val(data["data"][0]["productCost"]);
						
						$('#requisitionType').val(data["data"][0]["requisitionTypeId"]);
						
						costAcount();//初始化计算
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		            	alert("系统错误，请联系管理员。")
					 }
				})
			},
			
	       	"language": {
	       		"url":"${ctx}/plugins/datatables/chinese.json"
	       	},
			"columns": [
				{"data": null,"className" : 'td-center',"sWidth": "15px"},//0
				{"data": "materialId","className" : 'td-left',"sWidth": "120px"},//1.物料编号
				{"data": null,"defaultContent" : ''},//2.物料名称
				{"data": "purchaseType","className" : 'td-center', "defaultContent" : ''},//3.物料特性:物料
				{"data": "unitQuantity","className" : 'td-right', "defaultContent" : ''},//4.单位使用量:baseBom
				{"data": null,"className" : 'td-right'},//5.订单需求量
				// {"data": "requisitionType","className" : 'td-right'},//6.领料方式
				{"data": "availabelToPromise","className" : 'td-right'},//6.当前库存(虚拟库存):物料
				{"data": "purchaseQuantity","className" : 'td-right'},//7.建议采购量:输入
				{"data": "totalRequisition","className" : 'td-right'},//8.领料数量
				{"data": "supplierId","className" : 'td-left',"sWidth": "60px"},//9.供应商,可修改:baseBom
				{"data": "price","className" : 'td-right', "defaultContent" : '0'},//10.本次单价,可修改:baseBom
				{"data": "totalPrice","className" : 'td-right', "defaultContent" : '0'},//11.总价=本次单价*采购量
				{"data": null,"className" : 'td-right',"sWidth": "100px"},//12
				{"data": "purchaseTypeId","className" : 'td-center'},//13
				],
			"columnDefs":[
				{"targets":12,"render":function(data, type, row){
					var contractId = row["contractSupplierId"];
					var contractPrice = currencyToFloat(row["contractPrice"]);
					var planPrice = currencyToFloat(row["price"]);
					var contractQty = currencyToFloat(row["contractQty"]);
					var planQty = currencyToFloat(row["planQty"]);
					var txt = "";
					if(contractId == ''){//没做过合同
						if(planQty <= '0'){
							txt = txt + '本次不采购';
						}else{
							txt = txt + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["supplierId"] +":"+ row["recordId"] + "' />";	
							txt = txt + '新建合同';
						}
					}else{//做过合同
						//if(contractPrice == planPrice && contractQty == planQty){
						if(contractQty == planQty){

							txt = txt + "采购无变化"
						}else{
							if(planQty <= '0'){
								txt = "<input type=checkbox name='numCheck' id='numCheck' value='" + row["supplierId"]  +":"+ row["recordId"]+ "' />";	
								txt = txt + '采购有变化'
							}else{
								txt = "<input type=checkbox name='numCheck' id='numCheck' value='" + row["supplierId"]  +":"+ row["recordId"]+ "' />";	
								txt = txt + "采购有变化"
							}
						}
						GcontractStatusFlag = true;//只要有一个合同存在，就不能删除采购方案
					}
					
					return txt;
				
				}},
				{"targets":1,"render":function(data, type, row){
					rtn= "<a href=\"###\" onClick=\"doEditMaterial('" + row["materialRecordId"] +"','" + row["materialParentId"] +"')\">"+data+"</a>";
					return rtn;
				}},
	    		{"targets":2,"render":function(data, type, row){	 			
	    			return jQuery.fixedWidth(row["materialName"],35);	
	    		}},
	    		{"targets":5,"render":function(data, type, row){	
	    			
	    			return floatToCurrency(row["manufactureQuantity"]);
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			
	    			var stock =  row["availabelToPromise"] ;
	    			var fstock = currencyToFloat( stock );
					var rtn = "";
	    			return stock;
	    		}},
	    		{"targets":7,"render":function(data, type, row){
	    			
	    			var stock =  row["purchaseQuantity"] ;
	    			var fstock = currencyToFloat( stock );
					var rtn = "";
					if(fstock > 0){
						rtn = '<div style="font-weight:bold;">' + stock + '</div>';
					}else {
						rtn = stock;
					}
	    			return rtn;
	    		}},
	    		{"targets":10,"render":function(data, type, row){
	    			
	    			return formatNumber(data);
	    		}},
	    		{
					"visible" : false,
					"targets" : [3,4,13]
				},
	    		{ "bSortable": false, "aTargets": [ 0 ,12] }
	          
	        ] 
	     
		}).draw();	
		
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
	
	function fnselectall() { 
		if($("#selectall").prop("checked")){
			$("input[name='numCheck']").each(function() {
				$(this).prop("checked", true);
				$(this).parent().parent().addClass("selected");
			});
				
		}else{
			$("input[name='numCheck']").each(function() {
				if($(this).prop("checked")){
					$(this).removeAttr("checked");
					$(this).parent().parent().removeClass('selected');
				}
			});
		}
	};
	
	function fnreverse() { 
		$("input[name='numCheck']").each(function () {  
	        $(this).prop("checked", !$(this).prop("checked"));  
			$(this).parent().parent().toggleClass("selected");
	    });
	};
	
</script>
<script type="text/javascript">


function contractTableView() {

	var YSId='${order.YSId}';
	var table = $('#contractTable').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#contractTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
        "sScrollY": 250,
		"retrieve" : false,
		"async" : false,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/contract?methodtype=getContract&YSId="+YSId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){

					var record = data["recordsTotal"];
					//alert(record);
					if(record > 0)
					//	$( "#tabs" ).tabs( "option", "active", 3 );//设置默认显示内容
					
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
			{"data": "contractId"},
			{"data": "supplierId"},
			{"data": "supplierName"},
			{"data": "purchaseDate","className" : 'td-center'},
			{"data": "deliveryDate","className" : 'td-center'},
			{"data": "total","className" : 'td-right'},
			{"data": null,"className" : 'td-center'},
			{"data": "deleteFlag","className" : 'td-center'}
        ] ,
		"columnDefs":[
      		{"targets":1,"render":function(data, type, row){
    			var contractId = row["contractId"];
    			rtn = contractId;
    			//rtn= "<a href=\"###\" onClick=\"showControctDetail('" + contractId +"')\">"+contractId+"</a>";
    			return rtn;
    		}},
    		{"targets":7,"render":function(data, type, row){
    			var contractId = row["contractId"];
    			var deleteFlag = row["deleteFlag"];
    			var rtn = "";
    			if(deleteFlag == 0){
        			rtn= "<a href=\"###\" onClick=\"showContract('" + row["supplierId"] +"','"+ row["YSId"] + "')\">"+"打印"+"</a>";   				
    			}
     			return rtn;
    		}},
      		{"targets":6,"render":function(data, type, row){
      			var total = currencyToFloat( row["total"] );			    			
      			totalPrice = floatToCurrency(total);
      			
      			//合同执行状况确认:如已有收货,则不能重置采购方案
      			
      			return totalPrice;
      		}},
      		{"targets":8,"render":function(data, type, row){
      			if( data == 1 ) {
      				return "已删除";
				}else{
      				return "有效";					
				}
      		}},
      		{"targets":8,"createdCell":function(td, cellData, rowData, row, col){

      			if( cellData == 1 ) {
      				$(td).parent().addClass('delete');
				}
      			
      		}},
    		{
				"visible" : false,
				"targets" : []
			},
      		
            
          ]
       	
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

function showControctDetail(contractId) {

	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	location.href = url;
}

function contractDetailView(contractId) {

	//var YSId='${order.YSId}';
	var table = $('#contractDetail').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#contractDetail').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/contract?methodtype=getContractDetail&contractId="+contractId,				
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
			{"data": "price","className" : 'td-right'},
			{"data": "unit","className" : 'td-center'},
			{"data": "totalPrice","className" : 'td-right'},
			{"data": "deleteFlag","className" : 'td-right'}
        ],
        "columnDefs":[

			{"targets":2,"render":function(data, type, row){
				
				var name = row["materialName"];

				name = jQuery.fixedWidth(name,40);	
				    			
				return name;
			}},
       		{"targets":7,"render":function(data, type, row){
       			if( data == 1 ) {
       				return "已删除";
 				}else{
       				return "有效";					
 				}
       		}},
       		{"targets":7,"createdCell":function(td, cellData, rowData, row, col){

       			if( cellData == 1 ) {
       				$(td).parent().addClass('delete');
 				}
       			
       		}},
       		{
   				"visible" : false,
   				"targets" : []
   			},
         		
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

	
} // ajax()供应商信息

function showContract(supplierId,YSId) {
	//accessFlg:1 标识新窗口打开
	var url = '${ctx}/business/requirement?methodtype=contractPrint';
	url = url + '&YSId=' + YSId+'&supplierId='+supplierId;
	//alert(YSId)
	//"sAjaxSource" : "${ctx}/business/contract?methodtype=getContractDetail&supplierId="+supplierId,	
	
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
		  //baseBomView();
		  return false; 
		}    
	});		

};

</script>

<script  type="text/javascript">
function ZZmaterialView() {

	var YSId=$("#purchasePlan\\.ysid").val();
	var table = $('#ZZmaterial').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#ZZmaterial').DataTable({
		"paging": false,
		"processing" : true,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"autoWidth":false,
		"retrieve" : false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/purchasePlan?methodtype=getRawMaterialList&YSId="+YSId,				
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
			{"data": null,},
			{"data": null,},
			{"data": "unit","className" : 'td-center'},
			{"data": "purchaseQuantity","defaultContent" : '0',"className" : 'td-right'},
			{"data": "price","defaultContent" : '0',"className" : 'td-right'},
			{"data": "total","defaultContent" : '0',"className" : 'td-right'},
		 ],
		 
		"columnDefs":[
      		{"targets":1,"render":function(data, type, row){
      			var name = row["rawMaterialId"];
    			//if(name == null){
    			//	name = "<a href=\"###\" onClick=\"doEditMaterial2('" + row["materialRecordId"] +"','"+ row["materialParentId"] + "')\">"+row["materialId"]+"</a>";
    			//}			    			
    			return name;
    		}},
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];
    			if(name == null){
    				name = '******';
    			}else{
        			name = jQuery.fixedWidth(name,40);	
    			}			    			
    			return name;
    		}},
    		{"targets":3,"render":function(data, type, row){
      			var total = row["totalQuantity"];
      			var unit = row["unit"];
      			var chanegVal = "";
    			if(unit == "吨"){//换算成千克
    				unit = "千克";
    			}	    			
    			return unit;
    		}},
    		{"targets":4,"render":function(data, type, row){
      			var total = row["purchaseQuantity"];
      			var unit = row["unit"];
      			var chanegVal = "";
    			if(unit == "吨"){//换算成千克
    				total = formatNumber( total * 1000 );
    			}else{
    				total = formatNumber( total);
    			}	    			
    			return total;
    		}},
    		{"targets":5,"render":function(data, type, row){
      				    			
    			return  floatToCurrency( data );;
    		}},
          
        ] ,
        
	     "aaSorting": [[ 1, "asc" ]]
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
					<td class="label" style="width:80px;"><label>耀升编号：</label></td>					
					<td style="width:100px;">${order.YSId}
					<form:hidden path="purchasePlan.ysid"  value="${order.YSId}" /></td>
								
					<td class="label" style="width:80px;"><label>产品编号：</label></td>					
					<td style="width:100px;"><a href="###" onClick="doShowProduct()">${order.materialId}</a>
					<form:hidden path="purchasePlan.materialid"  value="${order.materialId}" /></td>
				
					<td class="label" style="width:80px;"><label>产品名称：</label></td>				
					<td>${order.materialName}</td>
				</tr>
				<tr>
					<td class="label"><label>ＰＩ编号：</label></td>
					<td>${order.PIId}</td>

					<td class="label"><label>订单数量：</label></td>
					<td><span id="quantity">${order.totalQuantity}</span>（${order.unit}）</td>
						
					<td class="label"><label>客户名称：</label></td>
					<td>${order.customerFullName}</td>
					
					<!-- 
					<td class="label" style="width:80px;"><label>领料方式：</label></td>
					<td style="width:150px;">
						<form:select path="requisitionType" >							
						<form:options items="${requisitionType}" 
							itemValue="key" itemLabel="value" /></form:select>&nbsp;
						<a href="###"  id="editRequisitionType" >修改</a>
						<a href="###"  id="saveRequisitionType" >保存</a></td>
						 -->
				</tr>							
			</table>
		</fieldset>
		<div style="text-align: right;float: right;width: 50%;margin: 0px 10px -30px 0px;">
			<button type="button" id="doSaveCost" class="DTTT_button">保存核算结果</button>
		</div>	
		<fieldset>
			<legend> 成本核算</legend>
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
			<table class="form" id="table_form3" >				
				<tr>
					<!-- <td class="td-center"><label>下单日期</label></td>-->
					
					<td class="td-center"><label>销售单价</label></td> 
					<!-- <td class="td-center"><label>币种</label></td>-->
					<td class="td-center"><label>换汇</label></td>
					<td class="td-center"><label>原币价格</label></td>
					<td class="td-center"><label>销售税</label></td>
					<td class="td-center"><label>退税率</label></td>
					<td class="td-center"><label>退税</label></td>
					<td class="td-center"><label>利润</label></td>
					<td class="td-center"><label>利润率</label></td>
				</tr>	
				<tr>
					<form:hidden path="purchasePlan.recordid" />			
					<!-- <td class="td-center">${order.orderDate}
						<form:hidden path="purchasePlan.plandate"  class="read-only short" value="${order.orderDate}"/></td>-->
					<td class="td-center"><span id="orderPrice">${order.price}</span></td>
					<!-- <td class="td-center">${order.currency}</td> -->
					<td class="td-center">
						<form:input path="orderDetail.exchangerate" class="cash exchange mini"  value="${order.sysValue}"/></td>
					<td class="td-center">
						<form:input path="orderDetail.rmbprice" class="read-only cash short" value=""/></td>
					<td class="td-center">
						<form:input path="orderDetail.salestax" class="read-only cash short" value=""/></td>
					<td class="td-center">
						<form:input path="orderDetail.rebaterate" class="cash exchange mini"  value="${order.rebateRate}" />%</td>
					<td class="td-center">
						<form:input path="orderDetail.rebate" class="read-only cash short"  value=""/></td>
					<td class="td-center">
						<form:input path="orderDetail.profit" class="read-only cash short" value=""/></td>
					<td class="td-center">
						<form:input path="orderDetail.profitrate" class="read-only cash mini" value="" />%</td>
				</tr>								
			</table>
			
			<table class="form" id="table_form2" style="margin-top: -3px;height:70px">
				
				<tr style="vertical-align: bottom;">
					<td class="td-center" width="150px"><label>单位销售毛利</label></td>	
					<td class="td-center" width="150px"><label>单位核算毛利</label></td>
					<td class="td-center" style="font-weight: bold;"><label>销售毛利</label></td>
					<td class="td-center" style="font-weight: bold;"><label>核算毛利</label></td>
					<td class="td-center" style="font-weight: bold;"><label>订单增减费用</label></td>
					<td class="td-center" style="font-weight: bold;"><label>贸易净利</label></td>
				</tr>	
				<tr>			
					<td class="td-center">
						<form:input  path="orderDetail.salesprofit" class="read-only cash mini" value="" /></td>
					<td class="td-center">
						<form:input  path="orderDetail.adjustprofit" class="read-only cash mini" value="" /></td>
					<td class="td-center" style="font-weight: bold;">
						<form:input  path="orderDetail.totalsalesprofit" class="read-only cash short" value="" /></td>
					<td class="td-center" style="font-weight: bold;">
						<form:input  path="orderDetail.totaladjustprofit" class="read-only cash short" value="" /></td>
					<td class="td-center" style="font-weight: bold;">${order.orderCost}
						<input type="hidden" id="orderCost" value="${order.orderCost}" /></td>
					<td class="td-center" style="font-weight: bold;width: 250px;">
						<span id="netProfit"></span>
						<form:input  path="orderDetail.netprofit" class="read-only cash short" value="" /></td>
						<form:hidden path="orderDetail.productcost" value="" />
				</tr>								
			</table>
		</fieldset>	
		
		<fieldset class="action" style="text-align: right;">
			<button type="button" id="editPurchasePlan" class="DTTT_button">修改采购方案</button>
	<!-- 		<button type="button" id="deletePurchasePlan" class="DTTT_button warning">重置采购方案</button> -->
			<button type="button" id="resetYszz" class="DTTT_button warning">重置自制品</button>
			<button type="button" id="resetPackage" class="DTTT_button warning">重置包装件</button>
	 		<button type="button" id="deletePurchasePlan" class="DTTT_button">删除采购方案</button> 
			<button type="button" id="goBack" class="DTTT_button goBack">返回</button>
		</fieldset>	
		
		<div id="tabs" style="padding: 0px;white-space: nowrap;margin-top: -10px;">
		<ul>
			<li><a href="#tabs-1" class="tabs1">采购方案</a></li> 
			<li><a href="#tabs-2" class="tabs2">采购合同</a></li>
			<li><a href="#tabs-3" class="tabs3">自制物料需求表（原材料）</a></li>
		</ul>

		<div id="tabs-1" style="padding: 5px;">

			<div style="float:left;height:40px;margin-right: 30px;width: 50%;margin: 5px 0px -10px 10px;">
				<a class="DTTT_button DTTT_button_text box" id="all" data-id="4">显示全部</a>
				<a class="DTTT_button DTTT_button_text box" id="yz" data-id="0">自制品</a>
				<a class="DTTT_button DTTT_button_text box" id="dg" data-id="1">订购件</a>
				<a class="DTTT_button DTTT_button_text box" id="bz" data-id="3">包装品</a>
				<a class="DTTT_button DTTT_button_text box" id="ty" data-id="2">通用件</a>
				<input type="hidden" id="selectedPurchaseType" />
			</div>
			<div style="float:right;height:40px;margin: 5px 0px -10px 10px;">
				<b>合同交期：<input type="text" id="contractDelivery"  value=""  class=short/></b>&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" id="createPurchaseOrder" class="DTTT_button">选中并生成采购合同</button>
			</div>
		<table id="example" class="display" >
			<thead>				
				<tr>
					<th>No</th>
					<th>物料编码</th>
					<th>物料名称</th>
					<th>物料特性</th>
					<th width="40px">基本用量</th>
					<th width="60px">订单数</th>
					<th width="60px">虚拟库存</th>
					<th width="60px">采购量</th>
					<th width="60px">领料量</th>
					<th>供应商</th>
					<th width="60px">单价</th>
					<th width="70px">总价</th>
					<th>合同做成<br>
						<input type="checkbox" name="selectall" id="selectall" onclick="fnselectall()"/><label for="selectall">全选</label><input type="checkbox" name="reverse" id="reverse" onclick="fnreverse()" /><label for="reverse">反选</label></th>
					<th width="1px"></th>
				</tr>
			</thead>
		</table>

	</div>
	<div id="tabs-2" style="padding: 5px;">
		
		<table id="contractTable" class="display" style="width:98%">
			<thead>				
				<tr>
					<th width="1px">No</th>
					<th style="width:120px">合同编号</th>
					<th style="width:100px">供应商简称</th>
					<th >供应商名称</th>
					<th style="width:80px">下单日期</th>
					<th width="80px">交货日期</th>
					<th width="100px">合同金额</th>
					<th width="30px"></th>
					<th width="30px">是否有效</th>
				</tr>
			</thead>			
		</table>
		<br/>
		<fieldset style="min-height: 300px;">
		<legend>合同明细</legend>
		<div class="list">
		<table id="contractDetail" class="display">	
			<thead>
			<tr>
				<th style="width:30px">No</th>
				<th style="width:150px">ERP编码</th>
				<th>物料名称</th>
				<th style="width:80px">数量</th>
				<th style="width:60px">单价</th>
				<th style="width:50px">单位</th>
				<th style="width:80px">金额</th>
				<th style="width:30px">是否有效</th>
			</tr>
			</thead>
		</table>
		</div>
		</fieldset>
	</div>
		<div id="tabs-3" style="padding: 5px;">
	
				<table id="ZZmaterial" class="display" style="width:98%">
					<thead>				
						<tr>
							<th width="15px">No</th>
							<th style="width:100px">原材料编码</th>
							<th>原材料名称</th>
							<th width="15px">单位</th>
							<th width="50px">总量</th>
							<th width="50px">单价</th>
							<th width="50px">总价</th>
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
	var vUnitQuty = formatNumber(fUnitQuty);
			
	//详情列表显示
	$oUnitQuty.val(vUnitQuty)
	$oPurchase.val(vPurchase);
	$oTotalQuty.html(fTotalQuty);
	$oTotPriceS.html(vTotalNew);
	$oTotPriceI.val(vTotalNew);
}

//销售利润计算
function costAcount(){
	var exchang  = currencyToFloat($('#orderDetail\\.exchangerate').val());
	var exRebate = currencyToFloat($('#orderDetail\\.rebaterate').val());
	var exprice  = currencyToFloat($('#orderPrice').text());
	var mateCost = currencyToFloat($('#materialCost').text());
	var productCost = currencyToFloat($('#productCost').text());
	var costRate =  currencyToFloat($('#managementCostRate').text());
	var quantity = currencyToFloat($('#quantity').text());
	//原币价格=汇率*报价*订单数，利润率=（原币价格+退税-基础成本）/基础成本
	var rmb = exchang * exprice * quantity;
	//alert("exRebate:"+exRebate+"----mateCost:"+mateCost)
	var rebate = exRebate * mateCost / 1.17 / 100;	//退税
	var profit = rmb + rebate - productCost ;			//利润
	var profitRate = profit / productCost * 100;		//利润率
	//销售税=（销售单价(原币)-材料成本）*17%
	var salesTax = ( rmb - mateCost ) * 17 / 100;	
	
	//单位销售毛利=销售单价（原币）-产品成本-销售税+退税
	//单位核算毛利=销售单价（原币）-产品成本-销售税+退税-经管费=单位销售毛利-经管费
	var sale = rmb - productCost - salesTax + rebate;
	var adjust = sale - productCost * costRate / 100;
	var unitSale = sale / quantity;
	var unitAdjust = adjust / quantity;
	//alert('经管费='+costRate+'productCost='+productCost)
	$('#orderDetail\\.salestax').val(floatToCurrency(salesTax));
	$('#orderDetail\\.rebate').val(floatToCurrency(rebate));
	$('#orderDetail\\.rmbprice').val(floatToCurrency(rmb));
	$('#orderDetail\\.profit').val(floatToCurrency(profit));
	$('#orderDetail\\.profitrate').val(floatToCurrency(profitRate));

	$('#orderDetail\\.salesprofit').val(floatToCurrency(unitSale));
	$('#orderDetail\\.adjustprofit').val(floatToCurrency(unitAdjust));
	$('#orderDetail\\.totalsalesprofit').val(floatToCurrency(sale));
	$('#orderDetail\\.totaladjustprofit').val(floatToCurrency(adjust));
	
	netAdjustAccout();//贸易净利	
}

function netAdjustAccout(){
	var ordercost = currencyToFloat( $('#orderCost').val() );
	var adjust = currencyToFloat( $('#orderDetail\\.totaladjustprofit').val() );
	//alert('docCost:'+ordercost+'--adjust:'+adjust)
	var netProfit = adjust - ordercost ;
	$('#orderDetail\\.netprofit').val(floatToCurrency(netProfit));
	
}
</script>
</body>
	
</html>
