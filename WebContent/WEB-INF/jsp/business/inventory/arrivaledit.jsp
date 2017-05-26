<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-到货修改</title>
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
					}, {"className":"td-right"	
					}, {"className":"td-right"
					}, {"className":"td-right"
					}
			]
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(5)',function() {

			var $td = $(this).parent().find("td");

			var $oArrival = $td.eq(4).find("input");
			var $oRecorde = $td.eq(6).find("span");
			var $oQuantity= $td.eq(5).find("span");
			var $oSurplus = $td.eq(7).find("span");

			var fArrival  = currencyToFloat($oArrival.val());
			var fRecorde  = currencyToFloat($oRecorde.html());
			var fquantity = currencyToFloat($oQuantity.html());	
			
			if(fArrival > (fquantity-fRecorde)){

				$().toastmessage('showWarningToast', "登记数不能大于剩余数");
				return;
			}
			
			//剩余数量
			var fsurplus = floatToCurrency(fquantity - fRecorde - fArrival);	
			$oSurplus.html(fsurplus);
			$oArrival.val(floatToCurrency(fArrival))

		});
		
						
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

	function historyAjax() {
		var contractId = '${contract.contractId }';
		var t = $('#example2').DataTable({
			
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
			"sAjaxSource" : "${ctx}/business/arrival?methodtype=getArrivalHistory&contractId="+contractId,
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
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			        	{"data": null,"className":"dt-body-center"
					}, {"data": "arrivalId","className":"dt-body-center"
					}, {"data": "arriveDate","className":"dt-body-center"
					}, {"data": "materialId"
					}, {"data": "materialName"
					}, {"data": "unit","className":"dt-body-center"
					}, {"data": "quantity","className":"td-right"	
					}, {"data": "status","className":"td-center"
					}
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
		$("#arrival\\.arrivedate").val(shortToday());
		
		ajax();

		historyAjax();//到货登记历史记录

		autocomplete();
		
		//$('#example').DataTable().columns.adjust().draw();
		
		$("#arrival\\.arrivedate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$("#return").click(
				function() {
					var url = "${ctx}/business/arrival";
					location.href = url;		
				});
		
		$("#insert").click(
				function() {

			$('#formModel').attr("action", "${ctx}/business/arrival?methodtype=insert");
			$('#formModel').submit();
		});
		
$("#selectall").click(function () { 
			
			$('#example tbody tr').each (function (){
				
				var vcontract = $(this).find("td").eq(5).find("span").text();////合同数
				var vreceive  = $(this).find("td").eq(6).find("span").text();//已收货
				var vsurplus  = $(this).find("td").eq(7).find("span").text();//剩余

				var fcontract= currencyToFloat(vcontract);
				var freceive = currencyToFloat(vreceive);
				var fsurplus = floatToCurrency(fcontract - freceive);
				

				if(vsurplus > "0"){
					$(this).find("td").eq(4).find("input").val(fsurplus);//本次到货
					$(this).find("td").eq(7).find("span").text("0")//剩余数清零
				}
							
			})

		});
		

		$("#reverse").click(function () { 
			
			$('#example tbody tr').each (function (){

				var varrival  = $(this).find("td").eq(4).find("input").val();////本次收货
				var vcontract = $(this).find("td").eq(5).find("span").text();////合同数
				var vreceive  = $(this).find("td").eq(6).find("span").text();//已收货
				var vsurplus  = $(this).find("td").eq(7).find("span").text();//剩余

				var fcontract= currencyToFloat(vcontract);
				var freceive = currencyToFloat(vreceive);
				var fsurplus = floatToCurrency(fcontract - freceive);

				if(varrival > "0"){
					$(this).find("td").eq(7).find("span").text(fsurplus);//剩余数
					$(this).find("td").eq(4).find("input").val("0");//本次到货清零
				}
							
			})

		});
		
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

	<input type="hidden" id="tmpMaterialId" />
	
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px"><label>到货编号：</label></td>					
				<td>${arrivalId }
					<form:hidden path="arrival.arrivalid"  value="${arrivalId }" /></td>
														
				<td width="100px" class="label">到货日期：</td>
				<td>
					<form:input path="arrival.arrivedate" class="short read-only"  value=""/></td>
				
				<td width="100px" class="label">仓管员：</td>
				<td>
					<form:input path="arrival.userid" class="short read-only" value="${userName }" /></td>
			</tr>
			<tr> 				
				<td class="label"><label>耀升编号：</label></td>					
				<td>${contract.YSId }</td>
							
				<td class="label"><label>合同编号：</label></td>					
				<td>&nbsp;${contract.contractId }
					<form:hidden path="arrival.contractid"  value="${contract.contractId }"/></td>
							
				<td class="label"><label>供应商：</label></td>					
				<td>&nbsp;${contract.supplierId }（${contract.shortName }）${contract.fullName}</td>	
			</tr>
										
		</table>
</fieldset>

<fieldset>
	<legend>到货登记</legend>
	<div class="list">	
	<table id="example" class="display" >
		<thead>				
			<tr>
				<th style="width:1px">No</th>
				<th class="dt-center" width="175px">物料编号</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="30px">单位</th>
				<th class="dt-center" width="80px">
					<input type="checkbox" name="selectall" id="selectall" /><label for="selectall">全部到货</label> 
					<input type="checkbox" name="reverse" id="reverse" /><label for="reverse">全部清空</label></th>
				<th class="dt-center" width="60px">合同总数</th>
				<th class="dt-center" width="60px">累计收货</th>
				<th class="dt-center" width="60px">剩余数量</th>
			</tr>
		</thead>
		
	<tbody>
		<c:forEach var="list" items="${material}" varStatus='status' >	
			<tr>
				<td></td>
				<td>${list.materialId }
					<form:hidden path="arrivalList[${status.index}].materialid" value="${list.materialId }" /></td>
				<td><span>${list.materialName }</span></td>
				<td><span>${list.unit }</span></td>
				<td><form:input path="arrivalList[${status.index}].quantity" class="num mini"  value="${list.quantity }"/></td>
				<td><span>${list.contractQuantity }</span></td>
				<td><span id="arrivalSum${ status.index}"></span></td>
				<td><span id="surplus${ status.index}"></span></td>
			</tr>
			<script type="text/javascript">
					var index = '${status.index}';
					var contractQuantity = currencyToFloat('${list.contractQuantity}');
					var arrivalSum = currencyToFloat('${list.arrivalSum}');
					var quantity = currencyToFloat('${list.quantity}');
					
					arrivalSum = arrivalSum - quantity;//剩余 - 刚登记的
					var surplus = contractQuantity - arrivalSum - quantity;
					
					$('#arrivalSum'+index).html(floatToCurrency( arrivalSum ))
					$('#surplus'+index).html(floatToCurrency( surplus ))
			</script>
			
		</c:forEach>
		
	</tbody>
</table>
</div>
</fieldset>
<div style="clear: both"></div>
<fieldset class="action" style="text-align: right;">
	<button type="button" id="return" class="DTTT_button">返回</button>
	<button type="button" id="insert" class="DTTT_button">保存</button>
</fieldset>		

<div style="clear: both"></div>

<fieldset>
	<legend>收货记录</legend>
	<div class="list">	
	<table id="example2" class="display" >
		<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" style="width:60px">收货编号</th>
				<th class="dt-center" width="100px">到货日期</th>
				<th class="dt-center" width="150px">物料编号</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="40px">单位</th>
				<th class="dt-center" width="80px">到货数量</th>
				<th class="dt-center" width="60px">状态</th>
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

</script>

</html>
