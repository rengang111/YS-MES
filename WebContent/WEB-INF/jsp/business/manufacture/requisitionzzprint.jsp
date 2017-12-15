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
		
		requisitionAjaxFn();//领料单
		
		materialZZAjaxFn();	//自制件一览		
		
	});
	
function requisitionAjaxFn(scrollHeight) {
		
		var requisitionId= '${requisitionId}';
		var actionUrl = "${ctx}/business/requisition?methodtype=requisitionPrint";
		actionUrl = actionUrl +"&requisitionId="+requisitionId;		
		
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
				}, {"data": "unit","className":"td-center"	//3
				}, {"data": "quantity","className":"td-right"	//4
				}, {"data": null,"className":"td-right"	,"defaultContent" : ''//5
				}
			],
			"columnDefs":[                
	    		{"targets":2,"render":function(data, type, row){
	    			return "&nbsp;&nbsp;"+data;
                }},
	    		{"targets":3,"render":function(data, type, row){	    			
	    			var unit = row["unit"];		
	    			if(unit == '吨'){
	    				unit = '千克';//转换成公斤
	    			}	    								
	    			return unit;						 
                }},
                {
					"visible" : false,
					"targets" : [5]
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
	
	
	function materialZZAjaxFn(scrollHeight) {

		var makeType = '${makeType}';
		var taskId = '${taskId}';
		var YSId= '${YSId}';
		var actionUrl = "${ctx}/business/requisitionzz?methodtype=getMaterialZZList";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&taskId="+taskId;
		actionUrl = actionUrl +"&makeType="+makeType;
				
		var t = $('#example2').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	       "autoWidth":false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"aaSorting": [[ 1, "DESC" ]],
			"sAjaxSource" : actionUrl,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				//var param = {};
				//var formData = $("#condition").serializeArray();
				//formData.forEach(function(e) {
				//	aoData.push({"name":e.name, "value":e.value});
				//});

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
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns" : [
		        	{"data": null,"className":"dt-body-center"//0
				}, {"data": "YSId","className":"td-left", "defaultContent" : ''//5
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2
				}, {"data": "unit","className":"td-center"		//3单位
				}, {"data": "manufactureQuantity","className":"td-right"//4
				}, {"data": null, "defaultContent" : ''	//6 
				}
			],
			"columnDefs":[
	    		
	    		{"targets":3,"render":function(data, type, row){ 					
					//var index=row["rownum"]	
	    			//var name =  jQuery.fixedWidth( row["materialName"],40);
					//var inputTxt =       '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value=""/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			
	    			return data;
                }},
				{"targets":5,"render":function(data, type, row){	    			
	    			
	    			var qty = floatToCurrency(row["manufactureQuantity"]);			
	    			return qty;				 
                }}
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
				<td width="100px" class="label">领料单编号：</td>					
				<td width="180px">${requisitionId }</td>
							
				<td width="100px" class="label">制单人：</td>					
				<td width="180px">${userName }</td>
							
				<td width="100px" class="label">打印时间：</td>				
				<td colspan="3"><span id="today"></span></td>		
			</tr>
										
		</table>
		
		<fieldset>
			<legend> 原材料</legend>
			<div class="list">
				<table id="example" class="display" width="100%">
					<thead>				
						<tr>
							<th style="width:3px">No</th>
							<th width="120px">物料编号</th>
							<th >物料名称</th>				
							<th width="60px">单位</th>
							<th width="80px">领料数量</th>
							<th width="20px"></th>
						</tr>	
				</table>
			</div>
		</fieldset>
		<fieldset>
			<legend> 自制品</legend>
			<div class="list">
				<table id="example2" class="display" width="100%">
					<thead>				
						<tr>
							<th style="width:15px">No</th>
							<th width="70px">耀升编号</th>
							<th width="120px">物料编号</th>
							<th >物料名称</th>
							<th width="30px">单位</th>				
							<th width="80px">生产数量</th>
						</tr>
				</table>
			</div>		
		</fieldset>
	
	
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
