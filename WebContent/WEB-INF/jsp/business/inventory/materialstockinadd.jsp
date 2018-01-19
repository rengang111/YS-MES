<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>直接入库-登记</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

function materialAjax(sessionFlag) {
	var table = $('#TMaterial').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var url = "${ctx}/business/material?methodtype=search&sessionFlag="+sessionFlag;
	var scrollHeight = 185;//$(document).height() - 400; 
	var t = $('#TMaterial').DataTable({
			"paging": true,
			 "iDisplayLength" : 50,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"pagingType" : "full_numbers",
			"scrollY":scrollHeight,
			"scrollCollapse":false,
			"retrieve" : true,
			"sAjaxSource" : url,
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
						$("#keyword1").val(data["keyword1"]);
						$("#keyword2").val(data["keyword2"]);						
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
						{"data": "dicName","className" : 'td-center'},
						{"data": "quantityOnHand","className" : 'td-right'},
					],
			"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"];
	                    }},
			    		{"targets":2,"render":function(data, type, row){
			    			return jQuery.fixedWidth(data,46);		
			    		}}
			    		
		           
		         ] 
		}
	);

};
	
	$(document).ready(function() {
		
		//日期
		$("#stock\\.checkindate").val(shortToday());		
		$("#stock\\.checkindate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/storage?methodtype=materialStockinMainInit";
					location.href = url;
				});

		
		$("#insert").click(
				function() {

					var quantity = $('#stockDetail\\.quantity').val();
					quantity = myTrim(quantity);
					quantity = currencyToFloat(quantity);
					if(quantity<=0){
						$().toastmessage('showWarningToast', "入库数量必须大于零,请重试。");
						return;
					}
			$('#formModel').attr("action", "${ctx}/business/storage?methodtype=materialStockinAdd");
			$('#formModel').submit();
		});
		
	
				
		foucsInit();

		$('#TMaterial').DataTable().on('click', 'tr', function() {
			

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            
	            var d = $('#TMaterial').DataTable().row(this).data();				
//alert(d["materialId"])
				$('#stockDetail\\.materialid').val(d["materialId"]);
				$('#materialName').text(d["materialName"]);
				//$('#quantity').text(d["quantityOnHand"]);
				$('#unit').text(d["dicName"]);
					            
	        }
			
		});
	
		materialAjax();
		
	});


	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		materialAjax("false");

	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">
	<div id="search" style="width: 98%;">

		<form id="condition"  style='padding: 0px; margin: 10px;' >

			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label">关键字1：</td>
					<td class="condition">
						<input type="text" id="keyword1" name="keyword1" class="middle"/>
					</td>
					<td class="label">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle"/>
					</td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
			</table>
		</form>			
		<table style="width: 100%;" id="TMaterial" class="display">
			<thead>						
				<tr class="selected">
					<th style="width: 30px;" aria-label="No:">No</th>
					<th style="width: 200px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 40px;">单位</th>
					<th style="width: 100px;">库存</th>
				</tr>
			</thead>
		</table>
	</div>
			
		
			
<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
	
	<input type="hidden" id="makeType" value="${makeType }" />
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;width: 50%;float: right;margin-bottom: -35px;margin-top: 5px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认入库</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 入库单编号</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">入库单编号：</td>					
				<td width="150px">
					<form:input path="stock.receiptid" class="required read-only " value="保存后自动生成" /></td>
														
				<td width="100px" class="label">入库日期：</td>
				<td width="100px">
					<form:input path="stock.checkindate" class="short read-only" /></td>
				
				<td width="100px" class="label">仓管员：</td>
				<td colspan="3">
					<form:input path="stock.keepuser" class="short read-only" value="${userName }" /></td>
			</tr>

			<tr> 				
				<td class="label" width="100px">物料编号：</td>					
				<td width="200px"><form:input path="stockDetail.materialid" class="read-only" value="" /></td>
														
				<td width="100px" class="label">物料名称：</td>
				<td colspan="5">&nbsp;<span id="materialName"></span></td>
				
			</tr>
			<tr> 				
				<td class="label">入库数量：</td>
				<td><form:input path="stockDetail.quantity" class="num " value="" /></td>							
				
				<td class="label">入库件数：</td>
				<td><form:input path="stockDetail.packagnumber" class="num mini" value="" /></td>
												
				<td class="label">包装方式：</td>
				<td width="100px"><form:select path="stockDetail.packaging" style="width:100px">
								<form:options items="${packagingList}" 
									itemValue="key" itemLabel="value"/></form:select></td>					
				
				<td class="label">库位编号：</td>
				<td><form:input path="stockDetail.areanumber" class="num " value="" /></td>
			</tr>
			<tr> 				
				<td class="label">备注信息：</td>					
				<td colspan="5">
					<form:textarea path="stock.remarks" rows="3" cols="80" /></td>
				
			</tr>										
		</table>
</fieldset>
</form:form>

</div>
</div>
</body>

</html>
