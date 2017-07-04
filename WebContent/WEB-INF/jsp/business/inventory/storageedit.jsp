<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function ajax() {

		var receiptId = $("#stock\\.receiptid").val();
		var t = $('#example').DataTable({
			
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/storage?methodtype=getStockInDetail&receiptId="+receiptId,
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
						$("#receiptId").text(data["data"][0]["receiptId"]);
						$("#supplierName").text(data["data"][0]["supplierName"]);
						$("#contractId").text(data["data"][0]["contractId"]);
						$("#LoginName").text(data["data"][0]["LoginName"]);
						$("#checkInDate").text(data["data"][0]["checkInDate"]);
						$("#stock\\.arrivelid").val(data["data"][0]["arrivelId"]);
						$("#stock\\.subid").val(data["data"][0]["subId"]);
						$("#stock\\.contractid").val(data["data"][0]["contractId"]);
						$("#stock\\.supplierid").val(data["data"][0]["supplierId"]);
						fnCallback(data);
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
						 alert(errorThrown)
		             }
				})
			},
			"columns" : [
	           		{"data": null,"className":"dt-body-center"
				}, {"data": "materialId"
				}, {"data": "materialName"
				}, {"data": "unit","className":"dt-body-center"
				}, {"data": "contractQuantity","className":"td-right"	
				}, {"data": "quantity","className":"td-right"	
				}, {"data": "areaNumber","className":"td-left"
				}
			] ,	
			"columnDefs":[
	    		{"targets":5,"render":function(data, type, row){
	    			var quantity = row["quantity"];	
	    			var rownum = row["rownum"]-1;
	    			var rtn = quantity;
	     			rtn += "<input type=\"hidden\" id=\"stockList"+rownum+".quantity\" name=\"stockList["+rownum+"].quantity\"  class=\"num\" value=\""+quantity+"\">";
	     			rtn += "<input type=\"hidden\" id=\"stockList"+rownum+".materialid\" name=\"stockList["+rownum+"].materialid\"  class=\"num\" value=\""+row["materialId"]+"\">";
	    			return rtn;
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			var areaNumber = row["areaNumber"];	
	    			var rownum = row["rownum"]-1;
	     			var rtn= "<input type=\"text\" id=\"stockList"+rownum+".areanumber\" name=\"stockList["+rownum+"].areanumber\"  class=\"short\" value=\""+areaNumber+"\">";
	    			return rtn;
	    		}},
	    	]  	

		}).draw();
						
		t.on('click', 'tr', function() {
			
			var rowIndex = $(this).context._DT_RowIndex; //行号			
			//alert(rowIndex);

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

		$("#stock\\.checkindate").val(shortToday());
		
		ajax();
		
		$("#goBack").click(
				function() {
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/storage?keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
				var receiptId = $("#receiptid").val();	
			$('#formModel').attr("action", "${ctx}/business/storage?methodtype=update&receiptId="+receiptId);
			$('#formModel').submit();
		});
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/arrival?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
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

	<form:hidden path="stock.receiptid"  value="${receiptId}"/>
	<form:hidden path="stock.subid" />
	<form:hidden path="stock.arrivelid" />
	<form:hidden path="stock.contractid"  value=""/>
	<form:hidden path="stock.supplierid"  value=""/>
	
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">入库单编号：</td>					
				<td width="200px"><span id="receiptId"></span></td>		
									
				<td width="100px" class="label">仓管员：</td>
				<td width="200px"><span id="LoginName"></span></td>	
										
				<td width="100px"class="label">入库时间：</td>
				<td><span id="checkInDate"></span>
					<form:hidden path="stock.checkindate" /></td>
			<tr>							
				<td class="label">合同编号：</td>					
				<td><span id="contractId"></span></td>								 	
				
				<td class="label">供应商：</td>					
				<td colspan="3"><span id="supplierName"> </span></td>	
			</tr>
										
		</table>
</fieldset>
<fieldset>
	<legend> 物料信息</legend>
	<div class="list">
	<table class="display" id="example">	
		<thead>		
			<tr>
				<th style="width:1px">No</th>
				<th style="width:150px">物料编号</th>
				<th>物料名称</th>
				<th style="width:30px">单位</th>
				<th style="width:100px">合同数量</th>
				<th style="width:100px">入库数量</th>
				<th style="width:120px">仓库位置</th>		
			</tr>
		</thead>		
									
	</table>
	<fieldset class="action" style="text-align: right;margin-top:10px">
		<button type="button" id="insert" class="DTTT_button">入库</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>	
	</div>
</fieldset>


</form:form>

</div>
</div>
</body>


</html>
