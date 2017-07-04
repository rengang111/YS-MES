<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-入库登记</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter = 5;
	var shortYear = ""; 
	
	function ajax() {

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
			//"scrollY"    : "160px",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : '<"clear">rt',		
			"columns" : [
			        	{"className":"dt-body-center"
					}, {
					}, {
					}, {"className":"dt-body-center"
					}, {"className":"dt-body-center"
					}, {"className":"dt-body-center"
					}, {"className":"td-center"	
					}, {"className":"td-right"
					}, {"className":"td-right"
					}, {
					}
				],
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = data;				    			
    			name = jQuery.fixedWidth(name,40);				    			
    			return name;
    		}}
           
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

		//设置光标项目
		//$("#attribute1").focus();
		//$("#order\\.piid").attr('readonly', "true");

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#stock\\.checkindate").val(shortToday());
		
		ajax();
		
		$("#stock\\.checkindate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$("#goBack").click(
				function() {
					var contractId='${contract.contractId }';
					var url = "${ctx}/business/storage?keyBackup="+contractId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
				
			var i=0;
			$(".error").each(function () {  
		       i++;  
		    });
			
			if(i>0){
				$().toastmessage('showWarningToast', "请先修正页面中的错误输入，再保存。");
				return
			}
					
			$('#formModel').attr("action", "${ctx}/business/storage?methodtype=insert");
			$('#formModel').submit();
		});
				
		foucsInit();
		
		
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

	<input type="hidden" id="tmpMaterialId" />
	
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">入库单编号：</td>					
				<td width="200px">
					<form:input path="stock.receiptid" class="required read-only" />
					<form:hidden path="stock.subid" />
					<form:hidden path="stock.arrivelid"  value="${contract.arrivalId }"/></td>
							
				<td width="100px" class="label">仓管员：</td>
				<td width="200px">
					<form:input path="stock.keepuser" class="short read-only" value="${userName }" /></td>							
				<td class="label">入库时间：</td>
				<td>
					<form:input path="stock.checkindate" class="short read-only" /></td>

			<tr>
							
				<td class="label">合同编号：</td>					
				<td>&nbsp;<a href="#" onClick="showContract('${contract.contractId }')">${contract.contractId }</a>
					<form:hidden path="stock.contractid"  value="${contract.contractId }"/></td>								 	
				
				<td class="label">供应商：</td>					
				<td colspan="3">&nbsp;${contract.supplierName }
					<form:hidden path="stock.supplierid"  value="${contract.supplierId }"/></td>	
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
				<th style="width:120px">物料编号</th>
				<th>物料名称</th>
				<th style="width:30px">单位</th>
				<th style="width:60px">报检日期</th>
				<th style="width:50px">报检人</th>
				<th style="width:55px">报检结果</th>
				<th style="width:60px">合同数量</th>
				<th style="width:80px">待入库数</th>
				<th style="width:80px">仓库位置</th>		
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${material}" varStatus='status' >			
				<tr>
					<td></td>
					<td>${list.materialId }
						<form:hidden path="stockList[${status.index}].materialid" value="${list.materialId }"/></td>
					<td>${list.materialName }</td>
					<td>${list.unit }</td>
					<td>${list.checkDate }</td>
					<td>${list.checkerName }</td>
					<td>${list.resultName }</td>
					<td>${list.contractQuantity }</td>
					<td>${list.quantity }
						<form:hidden path="stockList[${status.index}].quantity" value="${list.quantity }" /></td>
					<td><form:input path="stockList[${status.index}].areanumber" class="short" /></td>
				</tr>
				<script type="text/javascript">
						var index = '${status.index}';
				</script>
			
			</c:forEach>
		
	</tbody>
									
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

<script type="text/javascript">

function autocomplete(){
	
	//合同编号自动提示
	$("#arrival\\.contractid").autocomplete({
		minLength : 2,
		autoFocus : false,
	
		source : function(request, response) {
			//alert(888);
			var supplierId = $("#attribute1").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/business/contract?methodtype=getContractId",
				dataType : "json",
				data : {
					contractId : request.term,
					supplierId : supplierId
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.contractId,
									value : item.contractId,
									id : item.contractId
								}
							}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus);
					alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},
		
	});//attributeList1	
}

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	openLayer(url);

};

function showYS(YSId) {
	var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;

	//var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
	openLayer(url);

};

</script>

</html>
