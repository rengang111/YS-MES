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
		
		var excessType = '${excessType}';
		if(excessType == '020'){
			$('#excess').show();
		}else{
			$('#excess').hide();
		}
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#today").text(today());
		
		ajaxFn();		
		
		
	});
	
function ajaxFn(scrollHeight) {
		
		var requisitionId= '${requisitionId}';
		var actionUrl = "${ctx}/business/requisition?methodtype=requisitionPrint";
		actionUrl = actionUrl +"&requisitionId="+requisitionId;		
		
		var hidCol = 5;
		var excessType = '${excessType}';
		if(excessType == '020'){
			//超领
			hidCol = '';
		}
		
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

						var remarks = data["data"][0]["remarks"];
						var loginName = data["data"][0]["LoginName"];
						
						$('#remarks').html(remarks);	
						$('#loginName').html(loginName);
						
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
				}, {"data": "scrapQuantity","className":"td-right"	,"defaultContent" : '0'//5
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
                {"targets":4,"render":function(data, type, row){
	    			return floatToCurrency(data);
                }},
                {"targets":5,"render":function(data, type, row){
	    			return floatToCurrency(data);
                }},
                {
					"visible" : false,
					"targets" : [hidCol]
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
				<td>${order.YSId }</td>
									
				<td class="label">生产数量：</td>					
				<td colspan="3">${order.totalQuantity }</td>
			</tr>
			<tr>
							
				<td width="100px" class="label">产品编号：</td>					
				<td width="180px">${order.materialId }</td>
							
				<td width="100px" class="label">产品名称：</td>			
				<td colspan="3">${order.materialName }</td>	
			</tr>
			
			<tr>
				<td width="100px" class="label">领料单编号：</td>					
				<td width="180px">${requisitionId }</td>
							
				<td width="100px" class="label">制单人：</td>					
				<td width="180px"><span id="loginName"></span></td>
							
				<td width="100px" class="label">打印时间：</td>				
				<td colspan="3"><span id="today"></span></td>		
			</tr>
			<tr id="excess">				
				<td class="label" width="100px">超领原由：</td>					
				<td colspan="5"><pre><span id="remarks"></span></pre></td>
							
			</tr>
										
		</table>
		
		<div class="list">
		<!-- 
			<div id="DTTT_container" align="left" style="height:40px;margin-right: 30px;width: 50%;margin: 5px 0px -10px 10px;">
				<a class="DTTT_button DTTT_button_text box" id="all" data-id="4">显示全部</a>
				<a class="DTTT_button DTTT_button_text box" id="yz" data-id="0">自制品</a>
				<a class="DTTT_button DTTT_button_text box" id="dg" data-id="1">订购件</a>
				<a class="DTTT_button DTTT_button_text box" id="ty" data-id="2">通用件</a>
				<a class="DTTT_button DTTT_button_text box" id="bz" data-id="3">包装品</a>&nbsp;&nbsp;
			 	<a class="DTTT_button DTTT_button_text box" id="ycl">自制品原材料</a>
				<input type="hidden" id="selectedPurchaseType" />
			</div>
			 -->
			<table id="example" class="display" width="100%">
				<thead>				
					<tr>
						<th style="width:3px">No</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>				
						<th width="60px">单位</th>
						<th width="80px">领料数量</th>
						<th width="80px">退还数量</th>
						<th width="20px"></th>
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
