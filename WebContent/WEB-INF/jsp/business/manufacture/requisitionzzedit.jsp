<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>自制件领料申请-领料单</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	function ajax(scrollHeight) {

		var makeType = $('#makeType').val();
		var taskId = $('#task\\.taskid').val();
		var YSId= $('#task\\.collectysid').val();
		var actionUrl = "${ctx}/business/requisitionzz?methodtype=getRawMaterialList";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&taskId="+taskId;
		actionUrl = actionUrl +"&makeType="+makeType;
		
		scrollHeight =$(document).height() - 250; 
		
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
		    "autoWidth":false,
			"dom"		: '<"clear">rt',
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
						
						foucsInit();
						
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
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2
				}, {"data": null,"className":"td-center"		//3单位
				}, {"data": "manufactureQuantity","className":"td-right"//4
				}, {"data": "totalRequisition","className":"td-right", "defaultContent" : '0'//5
				}, {"data": "quantityOnHand","className":"td-right"	//6 可用库存
				}, {"data": null,"className":"td-right"		//7
				}
			],
			"columnDefs":[
	    		
	    		{"targets":2,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					var inputTxt =       '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value=""/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			
	    			return name + inputTxt;
                }},
	    		{"targets":3,"render":function(data, type, row){	    			
	    			
	    			var unit = row["unit"];	    			
	    			var index=row["rownum"]
	
	    			if(unit == '吨'){
	    				unit = '千克';//转换成公斤
	    			}
	    								
	    			return unit;				 
                }},
	    		{"targets":4,"render":function(data, type, row){	    			
	    			
	    			var vrawunit = row["unit"];
	    			var vzzunit = row["zzunit"]; 			
	    			
	    			var qty = currencyToFloat(row["manufactureQuantity"]);
	    			var value = '0';
	    			
	    			//原材料的购买单位			
	    			var farwunit = getUnitChange(vrawunit)
	    			//自制品的用量单位
	    			var fchgunit = getUnitChange(vzzunit)	    			
	    			//alert("materialId:"+row["materialId"]+"购买:"+farwunit+"用量:"+fchgunit)

	    			if(vrawunit == '吨')
	    				farwunit = farwunit *1000;//换算成千克
	    				
	    			return floatToCurrency( qty * farwunit / fchgunit );
	    										 
                }},
	    		{"targets":5,"render":function(data, type, row){	    			
	    			
	    			var qty = floatToCurrency(row["totalRequisition"]);			
	    			return qty;				 
                }},
	    		{"targets":6,"render":function(data, type, row){	    			
	    			
	    			var qty = floatToCurrency(row["quantityOnHand"]);			
	    			return qty;				 
                }},
	    		{"targets":7,"render":function(data, type, row){	
	    			
					var index=row["rownum"];	
					var vrawunit = row["unit"];
	    			var vzzunit = row["zzunit"]; 			
	    			
	    			var qty = currencyToFloat(row["manufactureQuantity"]);
	    			var value = '0';
	    			
	    			//原材料的购买单位			
	    			var farwunit = getUnitChange(vrawunit)
	    			//自制品的用量单位
	    			var fchgunit = getUnitChange(vzzunit)	    			
	    			//alert("materialId:"+row["materialId"]+"购买:"+farwunit+"用量:"+fchgunit)

	    			if(vrawunit == '吨')
	    				farwunit = farwunit *1000;//换算成千克
	    				
	    			var currValue =  floatToCurrency( qty * farwunit / fchgunit );
					var inputTxt = '<input type="text" id="requisitionList'+index+'.quantity" name="requisitionList['+index+'].quantity" class="quantity num short"  value="'+currValue+'"/>';
				
					return inputTxt;
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
	
	function materialzzAjax() {

		var makeType = $('#makeType').val();
		var taskId = $('#task\\.taskid').val();
		var YSId= $('#task\\.collectysid').val();
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
	    			var name =  jQuery.fixedWidth( row["materialName"],40);    			
	    			return name;
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
	$(document).ready(function() {

		$( "#tabs" ).tabs();
		
		var scrollHeight =$(parent).height() - 200; 
		//日期
		$("#requisition\\.requisitiondate").val(shortToday());
		
		
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var makeType = $('#makeType').val();
					var url = "${ctx}/business/requisitionzz"+"?makeType="+makeType;
					location.href = url;		
				});

		$("#showHistory").click(
				function() {
					var taskId=$("#task\\.taskid").val();
					var makeType = $('#makeType').val();
					if(taskId ==''){
						$().toastmessage('showWarningToast', "还没有领料记录。");
						return;
					}
					var url = "${ctx}/business/requisitionzz?methodtype=getRequisitionHistoryInit&taskId="+taskId+"&makeType="+makeType;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {

					var makeType = $('#makeType').val();
			$('#formModel').attr("action", "${ctx}/business/requisitionzz?methodtype=update"+"&makeType="+makeType);
			$('#formModel').submit();
		});

		ajax(scrollHeight);
		
		materialzzAjax();
		
		foucsInit();	 
		
	});
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="makeType" value="${makeType }" />
	<form:hidden path="requisition.recordid" value="${detail.recordId }" />
	<form:hidden path="requisition.requisitionid"  value="${detail.requisitionId }" />
	<form:hidden path="task.collectysid" value="${detail.collectYsid }" />
	<form:hidden path="task.taskid"  value="${detail.taskId }"/>
	
	<fieldset>
		<legend> 自制品原材料领料单</legend>
		<table class="form" id="table_form">
			<tr> 														
				<td width="100px" class="label">领料单编号：</td>
				<td width="150px">${detail.requisitionId }</td>
														
				<td width="100px" class="label">领料日期：</td>
				<td width="150px">
					<form:input path="requisition.requisitiondate" class="short read-only" /></td>
				
				<td width="100px" class="label">领料人：</td>
				<td>
					<form:input path="requisition.requisitionuserid" class="short read-only" value="${userName }" /></td>
			</tr>
			<tr> 				
				<td class="label" width="100px">任务编号：</td>					
				<td width="150px">${detail.taskId }</td>				
				<td class="label">关联耀升编号：</td>				
				<td colspan="3">${detail.collectYsid }</td>
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认领料</a>
		<a class="DTTT_button DTTT_button_text" id="showHistory" >查看领料记录</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<div id="tabs" style="padding: 0px;white-space: nowrap;width:99%;">
		<ul>
			<li><a href="#tabs-1" class="tabs1">原材料</a></li>
			<li><a href="#tabs-2" class="tabs2">自制品</a></li>
		</ul>

		<div id="tabs-1" style="padding: 5px;">			
			<table id="example" class="display" >
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>
						<th width="50px">领料单位</th>				
						<th width="60px">计划用量</th>
						<th width="60px">已领数量</th>
						<th width="80px">可用库存</th>
						<th width="80px">本次领料</th>
					</tr>
				</thead>	
			</table>			
		</div>
		<div id="tabs-2" style="padding: 5px;">
			<table id="example2" class="display" >
				<thead>				
					<tr>
						<th style="width:3px">No</th>
						<th width="80px">耀升编号</th>
						<th width="120px">物料编号</th>
						<th>物料名称</th>
						<th width="50px">单位</th>				
						<th width="100px">生产数量</th>
						<th width="50px"></th>
					</tr>
				</thead>	
			</table>			
		</div>
	</div>
</form:form>

</div>
</div>
</body>


</html>
