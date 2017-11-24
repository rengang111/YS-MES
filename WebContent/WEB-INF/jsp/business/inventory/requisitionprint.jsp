<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<!-- 领料单打印 -->
<%@ include file="../../common/common2.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/print.css" />
<script type="text/javascript">
	
	/* Custom filtering function which will search data in column four between two values */
	$.fn.dataTable.ext.search.push(function( settings, data, dataIndex ) {
	       
		var type =  $('#selectedPurchaseType').val();
	    		
    	if (type =='' || type == 'all')		    		
    	{		    		
    		return true;
    		
    	}else if(type=='dg'){//订购件
    		var val1=data[9];
    		var val2=data[10];
    		var val3=data[1];
    		var tmp3 = val3.substring(0,1);
    		var tmp2 = val2.substring(6,4);
    		var tmp1 = val1.substring(3,0);
    		//alert(tmp)
    		if(tmp1 == '010' && tmp2 != 'YZ' && tmp3 != 'G' ){
    			return true;
    		}
    		
    	}else if(type=='ty'){//通用件
    		var val=data[9];
    		var tmp = val.substring(3,0);
    		
    		if(tmp == '020'){
    			return true;
    		}
    		
    	}else if(type=='bz'){//包装品
    		var val=data[1];
    		var tmp = val.substring(0,1);
    		
    		if(tmp == 'G'){
    			return true;
    		}
    		
    	}else if(type=='yz'){//自制品
    		var val=data[10];
    		var tmp = val.substring(6,4);
    		
    		if(tmp == 'YZ'){
    			return true;
    		}
    		
    	}else if(type=='ycl'){//原材料
    		var val=data[9];
    		var tmp = val.substring(3,0);
    		
    		if(tmp == '050'){
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
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#today").text(today());
		
		ajaxFn();		
		
		var table = $('#example').DataTable();
		// Event listener to the two range filtering inputs to redraw on input
	    $('#yz, #ty, #dg, #bz, #all, #ycl, #wll').click( function() {
	    	
	    	 $('#selectedPurchaseType').val($(this).attr('id'));
    		 table.draw();
	    } );
		
	});
	
function ajaxFn(scrollHeight) {
		
		var YSId= '${order.YSId}';
		var actionUrl = "${ctx}/business/requisition?methodtype=detailView";
		actionUrl = actionUrl +"&YSId="+YSId;
		
		scrollHeight =$(document).height() - 200; 
		
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"sAjaxSource" : actionUrl,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					//"data" : JSON.stringify(aoData),
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
			"columns" : [
		        	{"data": null,"className":"dt-body-center"//0
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2
				}, {"data": "unitQuantity","className":"td-right"	//3
				}, {"data": "manufactureQuantity","className":"td-right"//4
				}, {"data": "totalRequisition","className":"td-right"//5
				}, {"data": "quantityOnHand","className":"td-right"	//6 可用库存
				}, {"data": "areaNumber"		//7
				}, {"data": null,"className":"td-right","defaultContent" : '0'//8
				}, {"data": "purchaseType","className":"td-right"		//9
				}, {"data": "supplierId","className":"td-right"		//10
				}
			],
			"columnDefs":[
				
                
	    		{"targets":2,"render":function(data, type, row){ 					
					//var index=row["rownum"]	
	    			//var name =  jQuery.fixedWidth( row["materialName"],40);

	    			return "&nbsp;&nbsp;"+data;
                }},
	    		{"targets":4,"render":function(data, type, row){	    			
	    			
	    			var unit = row["unit"];	    			
	    			var index=row["rownum"]
	    			var qty = currencyToFloat(row["manufactureQuantity"]);
	    			var value = '0';
	    			//alert(unit)
	    			if(unit == '吨'){
	    				value = formatNumber( qty * 1000 );//转换成公斤
	    			}else{
	    				value = formatNumber(qty);
	    			}
	    								
	    			return value;				 
                }},
                {
					"visible" : false,
					"targets" : [3,6,8,9,10]
				}
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
				cell.innerHTML = i + 1;
			});
		}).draw();

	};
</script>
</head>
<body>

<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">
		
		<div id="printBtn">
			<button type="button" id="print" onclick="doPrint();"class="DTTT_button " style="float: right;margin: 15px 20px -50px 0px;height: 40px;width: 70px;">打印</button>
		</div>
		<table class="" id="table_form"  style="margin-top: -10px;width: 100%;">
			<tr> 				
				<td class="td-center" colspan="6" style="font-size: 26px;height: 50px;">领料单</td>
			</tr>
		</table>
		<table>
			<tr> 				
				<td class="label">耀升编号：</td>					
				<td>&nbsp;${order.YSId }</td>
									
				<td class="label">生产数量：</td>					
				<td colspan="3">&nbsp;${order.totalQuantity }</td>
			</tr>
			<tr>
							
				<td width="100px" class="label">产品编号：</td>					
				<td width="180px">&nbsp;${order.materialId }</td>
							
				<td width="100px" class="label">产品名称：</td>			
				<td colspan="3">&nbsp;${order.materialName }</td>	
			</tr>
			<tr>
							
				<td width="100px" class="label">制单人：</td>					
				<td width="180px">&nbsp;${userName }</td>
							
				<td width="100px" class="label">打印时间：</td>				
				<td colspan="3">&nbsp;<span id="today"></span></td>		
			</tr>
										
		</table>
		
		<div class="list">	
			<div id="DTTT_container" align="left" style="height:40px;margin-right: 30px;width: 50%;margin: 5px 0px -10px 10px;">
				<a class="DTTT_button DTTT_button_text box" id="all" data-id="4">显示全部</a>
				<a class="DTTT_button DTTT_button_text box" id="yz" data-id="0">自制品</a>
				<a class="DTTT_button DTTT_button_text box" id="dg" data-id="1">订购件</a>
				<a class="DTTT_button DTTT_button_text box" id="ty" data-id="2">通用件</a>
				<a class="DTTT_button DTTT_button_text box" id="bz" data-id="3">包装品</a>&nbsp;&nbsp;
			 	<a class="DTTT_button DTTT_button_text box" id="ycl">自制品原材料</a>
				<input type="hidden" id="selectedPurchaseType" />
			</div>
			<table id="example" class="display" width="100%">
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>				
						<th width="60px">基本用量</th>
						<th width="60px">计划用量</th>
						<th width="80px">已领数量</th>
						<th width="80px">可用库存</th>
						<th width="100px">库位</th>
						<th width="60px">剩余数量</th>
						<th width="1px"></th>
						<th width="1px"></th>
					</tr>
	
			</table>
		</div>		
	
	
	</form:form>
</div>
</div>


<script  type="text/javascript">

function doPrint(){
	
	var headstr = "<html><head><title></title></head><body>";  
	var footstr = "</body>";
	$("#printBtn").hide();
    $("#DTTT_container").hide();
	//var newstr = document.getElementById("main").innerHTML;
	//var cont = document.body.innerHTML;    
	//$("#print").addClass('noprint');
	//var oldstr = window.document.body.innerHTML;
	//document.body.innerHTML = headstr+newstr+footstr;  
	window.print();
	//document.body.innerHTML = oldstr;  

	$("#printBtn").show();
	$("#DTTT_container").show();
}
</script>
</body>
	
</html>
