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

	$(document).ready(function() {
		
		$("#today").text(today());	
		ajaxFn();		
	
	});
	
	function ajaxFn() {
		
		var stockOutId= '${stockOutId}';
		var YSId= '${order.YSId}';
		var actionUrl = "${ctx}/business/stockout?methodtype=getPrintData";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&stockOutId="+stockOutId;
				
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
						
						var detail = data['detailData']['checkOutDate'];
			            $("#outdate").text(detail);	
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
				}, {"data": "manufactureQuantity","className":"td-right"//4
				}, {"data": "totalQuantity","className":"td-right"//5
				}, {"data": "depotId"		//7
				}
			],
			"columnDefs":[
				
                
	    		{"targets":2,"render":function(data, type, row){ 					
					//var index=row["rownum"]	
	    			//var name =  jQuery.fixedWidth( row["materialName"],40);

	    			return "&nbsp;&nbsp;"+data;
                }},
	    		{"targets":4,"render":function(data, type, row){	    			
	    			/*
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
	    			*/					
	    			return row["totalQuantity"];				 
                }},
                {
					"visible" : false,
					"targets" : []
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
				<td  colspan="5">&nbsp;${order.totalQuantity }</td>
				
			</tr>
			<tr>
							
				<td width="100px" class="label">产品编号：</td>					
				<td width="180px">&nbsp;${order.materialId }</td>
							
				<td width="100px" class="label">产品名称：</td>			
				<td colspan="5">&nbsp;${order.materialName }</td>	
			</tr>
			<tr>
							
				<td width="100px" class="label">制单人：</td>					
				<td width="180px">&nbsp;${userName }</td>
							
				
				<td class="label" width="100px" >出库单号：</td>					
				<td  width="100px">&nbsp;${stockOutId}</td>
				
				<td width="100px" class="label">出库时间：</td>				
				<td  width="100px">&nbsp;<span id="outdate"></span></td>		
				
				<td width="100px" class="label">打印时间：</td>				
				<td>&nbsp;<span id="today"></span></td>		
			</tr>
										
		</table>
		
		<div class="list">
			<table id="example" class="display" width="100%">
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>
						<th width="80px">计划用量</th>
						<th width="80px">出库数量</th>
						<th width="100px">库位</th>
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
	//$("#DTTT_container").show();
}
</script>
</body>
	
</html>
