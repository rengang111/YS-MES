<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<!-- 成品入库单打印 -->
<%@ include file="../../common/common2.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/print.css" />
<script type="text/javascript">
	
	$(document).ready(function() {
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#today").text(today());
		
		ajaxFn();		
		
		
	});
	
	function ajaxFn(scrollHeight) {
			
	
		var YSId = '${order.YSId }';
		var materialId = '${order.materialId }';
		var actionUrl = "${ctx}/business/storage?methodtype=getProductStockInDetail";
		actionUrl = actionUrl + "&YSId="+YSId+"&materialId="+materialId;
			
		var t = $('#history').DataTable({
			
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"autoWidth"	:false,
			"searching" : false,
			"retrieve"  : true,
			"dom"		: '<"clear">rt',
			"sAjaxSource" : actionUrl,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				var param = {};
				var formData = $("#condition").serializeArray();
				formData.forEach(function(e) {
					aoData.push({"name":e.name, "value":e.value});
				});
	
				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					"data" : JSON.stringify(aoData),
					success: function(data){
						fnCallback(data);
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
						 alert(errorThrown)
		             }
				})
			},	
				"columns" : [
		        	{"data": null,"className":"dt-body-center"
				}, {"data": "checkInDate","className":"td-center"
				}, {"data": "receiptId","className":"td-left"
				}, {"data": "quantity","className":"td-right"
				}, {"data": "packagNumber","className":"td-right"
				}, {"data": "packaging","className":"td-center"
				}, {"data": "areaNumber",
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
				<td class="td-center" colspan="6" style="font-size: 26px;height: 50px;">成品入库单</td>
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
			<table class="display" id="history">	
				<thead>		
					<tr>
						<th style="width:15px">No</th>
						<th style="width:80px">入库时间</th>
						<th style="width:120px">入库单号</th>
						<th style="width:80px">入库数量</th>
						<th style="width:60px">入库件数</th>
						<th style="width:55px">包装方式</th>
						<th style="width:80px">库位编号</th>	
						
					</tr>
				</thead>	
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
	//var newstr = document.getElementById("main").innerHTML;
	//var cont = document.body.innerHTML;    
	//$("#print").addClass('noprint');
	//var oldstr = window.document.body.innerHTML;
	//document.body.innerHTML = headstr+newstr+footstr;  
	window.print();
	//document.body.innerHTML = oldstr;  

	$("#printBtn").show();
}
</script>
</body>
	
</html>
