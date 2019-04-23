<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>入库退货-新建</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

function materialAjax(sessionFlag) {
	var table = $('#TMaterial').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var url = "${ctx}/business/depotReturn?methodtype=getContractDetail&sessionFlag="+sessionFlag;
	var scrollHeight = "185px";//$(document).height() - 400; 
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
						{"data": "YSId"},
						{"data": "contractId"},
						{"data": "supplierId"},
						{"data": "materialId"},
						{"data": "materialName"},
						{"data": "unit","className" : 'td-center'},
						{"data": "quantity","className" : 'td-right'},
					],
			"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"];
	                    }},
			    		{"targets":5,"render":function(data, type, row){
			    			return jQuery.fixedWidth(data,32);		
			    		}}
			    		
		         ] 
		}
	);

};
	
	$(document).ready(function() {
		
		//日期
		$("#stockin\\.checkindate").val(shortToday());		
		
		$("#stockin\\.checkindate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
		}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/depotReturn";
					location.href = url;
		});

		
		$("#insert").click(
				function() {

			var quantity = $('#detail\\.quantity').val();
			var contractQty = $('#contractQty').val();
			quantity = currencyToFloat(quantity);
			contractQty = currencyToFloat(contractQty);
			
			if(quantity<=0){
				$().toastmessage('showWarningToast', "退货数量必须大于零。");
				return;
			}

			if(contractQty < quantity){
				$().toastmessage('showWarningToast', "退货数量不能大于合同数。");
				return;
			}

			$('#insert').attr("disabled","true").removeClass("DTTT_button");
			$('#formModel').attr("action", "${ctx}/business/depotReturn?methodtype=materialRequisitionInsert");
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
				
				$('#contractQty').val(d["quantity"]);
				$('#materialName').text(d["materialName"]);
				$('#detail\\.materialid').val(d["materialId"]);
				$('#stockin\\.ysid').val(d["YSId"]);
				$('#stockin\\.contractid').val(d["contractId"]);
				$('#stockin\\.supplierid').val(d["supplierId"]);
					            
	        }
			
		});
	
		//materialAjax();
		
		
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
					<th style="width: 30px;">No</th>
					<th style="width: 100px;">耀升编号</th>
					<th style="width: 100px;">合同编号</th>
					<th style="width: 100px;">供应商</th>
					<th style="width: 150px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 40px;">单位</th>
					<th style="width: 100px;">合同数量</th>
				</tr>
			</thead>
		</table>
	</div>
			
		
			
<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
	
	<form:hidden path="stockin.recordid"  />
	<input type="hidden" id="contractQty" value="0">
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;width: 50%;float: right;margin-bottom: -35px;margin-top: 5px;">
		<a class="DTTT_button" id="insert" >确认退货</a>
		<a class="DTTT_button goBack" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 入库退货</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">申请单编号：</td>					
				<td width="300px">
					<form:input path="stockin.receiptid" class="required read-only " value="保存后自动生成" /></td>
														
				<td width="100px" class="label">取消日期：</td>
				<td width="150px">
					<form:input path="stockin.checkindate" class="short read-only" /></td>
				
				<td width="100px" class="label">处理人：</td>
				<td>
					<form:input path="stockin.keepuser" class="short read-only" value="${userName }" /></td>
			</tr>
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="300px">
					<form:input path="stockin.ysid" class="required read-only"  /></td>
														
				<td width="100px" class="label">合同编号：</td>
				<td width="150px">
					<form:input path="stockin.contractid" class="read-only" /></td>
				
				<td width="100px" class="label">供应商：</td>
				<td>
					<form:input path="stockin.supplierid" class="short read-only"  /></td>
			</tr>
			<tr> 				
				<td class="label" width="100px">物料编号：</td>					
				<td width="200px"><form:input path="detail.materialid" class="read-only middle"  /></td>
														
				<td width="100px" class="label">物料名称：</td>
				<td colspan="3">&nbsp;<span id="materialName"></span></td>
			</tr>
			<tr> 				
				<td class="label">取消数量：</td>
				<td colspan="5"><form:input path="detail.quantity" class="num "  /></td>
				
			</tr>
			<tr> 				
				<td class="label">取消事由：</td>					
				<td colspan="5">
					<form:textarea path="stockin.remarks" rows="2" cols="80" /></td>
				
			</tr>										
		</table>
</fieldset>
</form:form>

</div>
</div>
</body>

</html>
