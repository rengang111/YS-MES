<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>车间退货-录入</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	var shortYear = ""; 
	
	function ajax(scrollHeight) {
		
		var YSId= '${order.YSId}';
		var actionUrl = "${ctx}/business/workshopReturn?methodtype=getPurchasePlanDetail";
		actionUrl = actionUrl +"&YSId="+YSId;
		
		var table = $('#example').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			"scrollY"    : scrollHeight - 290,//无滚动条
	        "scrollCollapse": false,
	        "search"    : true,
	        "ordering"  : false,
			//"dom"		: '<"clear">rt',
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
		             //alert(errorThrown)
					 }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns" : [
		        	{"data": null,"className":"dt-body-center"
				}, {"data": "materialId","className":"td-left"
				}, {"data": "materialName","className":"td-left"
				}, {"data": "supplierId","className":"td-left"	//4
				}, {"data": "unitQuantity","className":"td-right"// 5
				}, {"data": "manufactureQuantity","className":"td-right" //  6
				}, {"data": "quantity","className":"td-right"	//7
				}, {"data": null,"className":"td-right"		//8
				}							
				
			],
			"columnDefs":[
	    		
	    		{"targets":2,"render":function(data, type, row){ 		
	    			return  jQuery.fixedWidth( row["materialName"],40); 	    			
	    			
                }},
	    		{"targets":5,"render":function(data, type, row){	    			
	    			
	    			var storage = row["manufactureQuantity"];
	    			var index=row["rownum"]	    			
	    			var inputTxt = '<input type="hidden" id="workshopRetunList'+index+'.materialid" name="workshopRetunList['+index+'].materialid" value="'+row["materialId"]+'"/>';
				
	    			return storage + inputTxt;				 
                }},
	    		{"targets":7,"render":function(data, type, row){	    			
					var index=row["rownum"]
					var inputTxt = '<input type="text" id="workshopRetunList'+index+'.quantity" name="workshopRetunList['+index+'].quantity" class="quantity num mini"  value="0"/>';
				
					return inputTxt;
                }},
                {
					"visible" : false,
					"targets" : []
				}
			]
			
		}).draw();

		t.on('change', 'tr td:nth-child(8)',function() {

			var $tr = $(this).parent();
			var $td = $(this).parent().find("td");

			var $oQuantity = $td.eq(7).find("input");//退货数量
			var quantity = currencyToFloat($oQuantity.val());
			
			if(quantity > 0){
				if ( $tr.hasClass('selected') ) {
		            //$(this).removeClass('selected');
		        }
		        else {
		            //t.$('tr.selected').removeClass('selected');
		            $tr.addClass('selected');
		        }		
			}else{
				$tr.removeClass('selected');
			}

		});
		
		/*
		t.on('click', 'tr', function() {
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }			
		});
		*/
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
		
		//var scrollHeight =$(document).height() < $('body').height() ? $(document).height() : $('body').height(); 

		var scrollHeight =$(window).height(); 
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#workshopReturn\\.returndate").val(shortToday());
		
		ajax(scrollHeight);

		$("#workshopReturn\\.returndate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/workshopReturn";
					location.href = url;		
				});

		$("#showHistory").click(
				function() {
					var YSId='${order.YSId }';
					var url = "${ctx}/business/workshopReturn?methodtype=workshopRentunDetailView&YSId="+YSId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {

					$('#insert').attr("disabled","true").removeClass("DTTT_button");
			$('#formModel').attr("action", "${ctx}/business/workshopReturn?methodtype=createWorkshopRentun");
			$('#formModel').submit();
		});
		
		$("#reverse").click(function () { 
			$("input[name='numCheck']").each(function () {  
		        $(this).prop("checked", !$(this).prop("checked"));  
		    });
		});
				
		foucsInit();
		
		var table = $('#example').DataTable();
		// Event listener to the two range filtering inputs to redraw on input
	    $('#yz, #ty, #dg, #bz,#all').click( function() {
	    	
	    	 $('#selectedPurchaseType').val($(this).attr('id'));
    		 table.draw();
	    } );
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/workshopReturn?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}

	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="attrForm" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="goBackFlag" />
	<form:hidden path="workshopReturn.ysid"  value="${order.YSId }" />
	<fieldset>
		<legend> 领料单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" style="width:100px">耀升编号：</td>					
				<td style="width:150px">${order.YSId }</td>
															
				<td class="label">产品编号：</td>					
				<td>${order.materialId }</td>
							
				<td class="label">产品名称：</td>					
				<td>${order.materialName }</td>
			</tr>
			<tr>
				<td class="label" style="width:100px">生产数量：</td>					
				<td>${order.totalQuantity }</td>
				
				<td class="label" style="width:100px">退货日期：</td>					
				<td><form:input path="workshopReturn.returndate"  class="read-only short"/></td>
				
				<td class="label" style="width:100px">退货人员：</td>					
				<td><form:input path="workshopReturn.returnperson" value="${userName}"  class="read-only short" /></td>
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认退货</a>
		<a class="DTTT_button DTTT_button_text" id="showHistory" >查看退货记录</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>

	<fieldset style="margin-top: -30px;">
		<legend> 物料需求表</legend>
		<!-- 
		<div id="DTTT_container" align="left" style="height:40px;margin-right: 30px;width: 50%;margin: 5px 0px -10px 10px;">
			<a class="DTTT_button DTTT_button_text" id="all" data-id="4">显示全部</a>
			<a class="DTTT_button DTTT_button_text" id="yz" data-id="0">自制品</a>
			<a class="DTTT_button DTTT_button_text" id="dg" data-id="1">订购件</a>
			<a class="DTTT_button DTTT_button_text" id="ty" data-id="2">通用件</a>
			<a class="DTTT_button DTTT_button_text" id="bz" data-id="3">包装品</a>
			<input type="hidden" id="selectedPurchaseType" />
		</div>
	 -->
	 <div class="list">
		<table id="example" class="display" style="width:100%">
			<thead>				
				<tr>
					<th style="width:1px">No</th>
					<th class="dt-center" width="120px">物料编号</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" width="60px">供应商</th>
					<th class="dt-center" width="60px">基本用量</th>
					<th class="dt-center" width="60px">计划用量</th>
					<th class="dt-center" width="60px">累计退货</th>
					<th class="dt-center" width="60px">今日退货</th>
				</tr>
			</thead>		
		</table>
	</div>
	</fieldset>
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	openLayer(url);

};

function showYS(YSId) {
	//var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;

	var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
	openLayer(url);

};

</script>

</html>
