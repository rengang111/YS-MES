<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-到货登记</title>
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
			
			$td.eq(4).find("input").removeClass('error')
			
			var $oArrival = $td.eq(4).find("input");//本次收货
			var $oQuantity= $td.eq(5).find("span");//合同
			var $oSumArra = $td.eq(6).find("span");//累计收货
			//var $oRecorde = $td.eq(7).find("span");//累计退货
			var $oSurplus = $td.eq(7).find("span");//剩余

			var fArrival  = currencyToFloat($oArrival.val());
			var fquantity = currencyToFloat($oQuantity.html());
			var foSumArra = currencyToFloat($oSumArra.html());
			//var fRecorde  = currencyToFloat($oRecorde.html());	
			
			var surplus = fquantity - foSumArra;
			
			if( fArrival <=0 ){

				$().toastmessage('showWarningToast', "注意,收货数量必须大于零。");
				fArrival = 0;//最大收货数量
				$td.eq(4).find("input").addClass('error');

			}
			
			if( fArrival > surplus ){

				$().toastmessage('showWarningToast', "注意,收货数量不能大于合同。");
				fArrival = surplus;//最大收货数量
				$td.eq(4).find("input").addClass('error');

			}
			
			//剩余数量
			var viewplus = surplus - fArrival;			
			if(viewplus < 0){
				viewplus = 0;
			}				
			var fsurplus = floatToCurrency( viewplus );	
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

	
	
	$(document).ready(function() {

		setPurchaserList();//设置采购员列表
	
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#arrival\\.arrivedate").val(shortToday());
		
		ajax();

		$("#arrival\\.arrivedate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$("#goBack").click(
				function() {
					var makeType=$('#makeType').val();
					var url = "${ctx}/business/arrival?makeType="+makeType;
					location.href = url;		
				});

		$("#doView").click(
				function() {
					var contractId='${contract.contractId }';
					var makeType=$('#makeType').val();
					var url = "${ctx}/business/arrival?methodtype=gotoArrivalView";
					url = url + "&contractId="+contractId;
					url = url + "&makeType="+makeType;
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

			$('#insert').attr("disabled","true").removeClass("DTTT_button");
			var makeType=$('#makeType').val();	
			var purchaser = $('#purchaserList').val();
			var url = "${ctx}/business/arrival?methodtype=insert"+ "&makeType="+makeType+'&purchaser='+purchaser;
			$('#formModel').attr("action", url);

			$('#formModel').submit();
		});
		
		$("#reverse").click(function () { 
			$("input[name='numCheck']").each(function () {  
		        $(this).prop("checked", !$(this).prop("checked"));  
		    });
		});
		
		$("#selectall").click(function () { 
			
			$('#example tbody tr').each (function (){
				
				var vcontract = $(this).find("td").eq(5).find("span").text();////合同数
				var vreceive  = $(this).find("td").eq(6).find("span").text();//已收货
				//var vreturn   = $(this).find("td").eq(7).find("span").text();//已退货
				var vsurplus  = $(this).find("td").eq(7).find("span").text();//剩余

				var fcontract= currencyToFloat(vcontract);
				var freceive = currencyToFloat(vreceive);
				//var freturn  = currencyToFloat(vreturn);
				var fsurplus = floatToCurrency( fcontract - freceive );
				

				if(fsurplus > "0"){
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
				//var vreturn   = $(this).find("td").eq(7).find("span").text();//已退货
				var vsurplus  = $(this).find("td").eq(7).find("span").text();//剩余

				var fcontract= currencyToFloat(vcontract);
				var freceive = currencyToFloat(vreceive);
				//var freturn  = currencyToFloat(vreturn);
				var fsurplus = floatToCurrency( fcontract - freceive );

				if(varrival > "0"){
					$(this).find("td").eq(7).find("span").text(fsurplus);//剩余数
					$(this).find("td").eq(4).find("input").val("0");//本次到货清零
				}
							
			})

		});
		
		foucsInit();
		
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/arrival?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}
	
	
	function setPurchaserList(){
		var i = 0;	
		var options = "";
		<c:forEach var="list" items="${purchaser}">
			i++;
			options += '<option value="${list.key}">${list.value}</option>';
		</c:forEach>		
		var purchase = '${contract.purchaser}'
		
		$('#purchaserList').html(options);		
		$('#purchaserList').val(purchase);//默认值
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
	<input type="hidden" id="makeType" value="${makeType }" />
	<form:hidden path="arrival.arrivalid"  value="${arrivalId }" />
	
	<fieldset>
		<legend> 合同信息</legend>
		<table class="form" id="table_form">
			<tr> 
							
				<td class="label" width="100px"><label>合同编号：</label></td>					
				<td width="200px"><a href="#" onClick="showContract('${contract.contractId }')">${contract.contractId }</a>
					<form:hidden path="arrival.contractid"  value="${contract.contractId }"/></td>				
																		
				<td width="100px" class="label">到货日期：</td>
				<td width="200px">
					<form:input path="arrival.arrivedate" class="short read-only" /></td>
				
			</tr>
			<tr> 				
				<td class="label"><label>耀升编号：</label></td>					
				<td><a href="#" onClick="showYS('${contract.YSId}')">${contract.YSId }</a>
					<form:hidden path="arrival.ysid"  value="${contract.YSId }"/></td>
									
				<td class="label"><label>供应商：</label></td>					
				<td colspan="3">&nbsp;${contract.supplierId }（${contract.shortName }）${contract.fullName}
					<form:hidden path="arrival.supplierid"  value="${contract.supplierId }"/></td>	
			</tr>
			<tr>
							
				<td class="label"><label>下单日期：</label></td>					
				<td>${contract.purchaseDate }</td>	
			 	
				<td class="label"><label>合同交期：</label></td>					
				<td>&nbsp;${contract.deliveryDate }</td>
								
				<td width="100px" class="label">采购员：</td>
				<td>
					<select id="purchaserList" name="purchaserList"  style="width: 100px;vertical-align: bottom;height: 25px;"></select></td>
				
			</tr>
										
		</table>
</fieldset>
<fieldset style="">
	<span class="tablename"> 收货登记</span>
	<a id="doView" href="###" >收货详情</a>
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
				<th class="dt-center" width="60px">合同数量</th>
				<th class="dt-center" width="60px">净收货</th>
				<th class="dt-center" width="60px">剩余数量</th>
			</tr>
		</thead>
		
	<tbody>
		<c:forEach var="list" items="${material}" varStatus='status' >
		
				<tr>
					<td></td>
					<td>${list.materialId }
						<form:hidden path="arrivalList[${status.index}].materialid" value="${list.materialId }"/></td>
					<td><span>${list.materialName }</span></td>
					<td><span>${list.unit }</span></td>
					<td><form:input path="arrivalList[${status.index}].quantity" class="quantity num mini"  value="0"/></td>
					<td><span>${list.quantity }</span></td>
					<td><span id="arrival${ status.index}">${list.accumulated }</span></td>
					<td><span id="surplus${ status.index}"></span></td>
				</tr>
				<script type="text/javascript">
					var index = '${status.index}';
					var quantity = currencyToFloat('${list.quantity}');
					var accumulated = currencyToFloat('${list.accumulated}');//累计净收货
					var surplus = quantity - accumulated;
					if(surplus < 0)
						surplus = 0;
					
					$('#surplus'+index).html(floatToCurrency( surplus ))
						
				</script>
		
		</c:forEach>
		
	</tbody>
</table>
</div>
</fieldset>	
<fieldset class="action" style="text-align: right;">
	<button type="button" id="insert" class="DTTT_button" style="margin-right: 30px;">确认收货</button>
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

	openLayer(url);

};

</script>

</html>
