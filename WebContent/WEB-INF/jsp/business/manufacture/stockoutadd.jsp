<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>料件出库-出库确认</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	
	var shortYear = ""; 
	
	function ajax(scrollHeight) {
		
		var requisitionid= $("#stockout\\.requisitionid").val();
		var actionUrl = "${ctx}/business/stockout?methodtype=getRequisitionDetail";
		actionUrl = actionUrl +"&requisitionId="+requisitionid;
		
		//scrollHeight =$(document).height() - 200; 
		
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
				}, {"data": "quantityOnHand","className":"td-right"	//3
				}, {"data": null,"className":"td-right"//4
				}, {"data": "areaNumber","className":"td-right"//5
				}
			],
			"columnDefs":[
	    		
	    		{"targets":2,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					var inputTxt = '';
	    			inputTxt= inputTxt + '<input type="hidden" id="stockList'+index+'.materialid" name="stockList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			
	    			return name + inputTxt;
                }},
	    		{"targets":4,"render":function(data, type, row){	
	    			
					var index=row["rownum"];
					var quantity = (row["quantity"]);
					var inputTxt = '<input type="text" id="stockList'+index+'.quantity" name="stockList['+index+'].quantity" class="quantity num "  value="'+quantity+'"/>';
				
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
	
	$(document).ready(function() {
		
		var scrollHeight =$(parent).height() - 200; 
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#stockout\\.checkoutdate").val(shortToday());
		
		ajax(scrollHeight);
		
		$("#stockout\\.checkoutdate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/stockout";
					location.href = url;		
				});

		$("#showHistory").click(
				function() {
					var YSId='${order.YSId }';
					var url = "${ctx}/business/stockout?methodtype=getStockoutHistoryInit&YSId="+YSId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					
			$('#formModel').attr("action", "${ctx}/business/stockout?methodtype=insert");
			$('#formModel').submit();
		});
		
		$("#reverse").click(function () { 
			$("input[name='numCheck']").each(function () {  
		        $(this).prop("checked", !$(this).prop("checked"));  
		    });
		});
				
		foucsInit();
	
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/requisition?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}

	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
	
	<form:hidden path="stockout.ysid" />
	<form:hidden path="stockout.requisitionid"  />
	
	<fieldset>
		<legend> 出库单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">出库单编号：</td>					
				<td width="150px">
					<form:input path="stockout.stockoutid" class="short required read-only" value="保存后自动生成" /></td>
														
				<td width="100px" class="label">出库日期：</td>
				<td>
					<form:input path="stockout.checkoutdate" class="short read-only" /></td>
				
				<td width="100px" class="label">领料人：</td>
				<td>
					<form:input path="stockout.keepuser" class="short read-only" value="${userName }" /></td>
			</tr>
			<tr> 				
				<td class="label">耀升编号：</td>					
				<td>&nbsp;${order.YSId }</td>
																
				<td class="label">产品编号：</td>					
				<td>&nbsp;${order.materialId }</td>
							
				<td class="label">产品名称：</td>					
				<td>&nbsp;${order.materialName }</td>
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认出库</a>
	<!-- 	<a class="DTTT_button DTTT_button_text" id="print" onclick="doPrint();return false;">打印领料单</a> -->
		<a class="DTTT_button DTTT_button_text" id="showHistory" >查看出库记录</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>

	<fieldset style="margin-top: -30px;">
		<legend> 物料需求表</legend>
		<div class="list">
			<table id="example" class="display" >
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>
						<th width="60px">可用库存</th>
						<th width="80px">本次领料</th>
						<th width="160px">库位</th>
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
