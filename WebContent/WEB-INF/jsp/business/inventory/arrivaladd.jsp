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
					}, {"className":"td-right"
					}
				]
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(5)',function() {

			var $td = $(this).parent().find("td");

			var $oArrival = $td.eq(4).find("input");//本次收货
			var $oQuantity= $td.eq(5).find("span");//合同
			var $oSumArra = $td.eq(6).find("span");//累计收货
			var $oRecorde = $td.eq(7).find("span");//累计退货
			var $oSurplus = $td.eq(8).find("span");//剩余

			var fArrival  = currencyToFloat($oArrival.val());
			var fquantity = currencyToFloat($oQuantity.html());
			var foSumArra = currencyToFloat($oSumArra.html());
			var fRecorde  = currencyToFloat($oRecorde.html());	
			
			var surplus = fquantity - foSumArra + fRecorde;
			
			if( fArrival > surplus ){

				$().toastmessage('showWarningToast', "注意,收货数量不能大于合同。");
				fArrival = surplus;//最大收货数量
		        //$(this).find("input:text").removeClass('bgwhite').removeClass('bgnone');
           	 	//$(this).find("input:text").addClass('error');
				//return;
			}else{
				$(this).find("input:text").removeClass('error')
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
					}, {"data": null,"className":"td-center"
					}
				] ,
				"columnDefs":[
		    		{"targets":8,"render":function(data, type, row){
		    			var contractId = row["contractId"];		    			
		    			var rtn= "<a href=\"###\" onClick=\"doEdit('" + row["contractId"] + "','" + row["arrivalId"] + "')\">编辑</a>";
		    			rtn = rtn +"&nbsp;&nbsp;"+ "<a href=\"###\" onClick=\"doDelete('" + row["recordId"] + "')\">删除</a>";
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

		//设置光标项目
		//$("#attribute1").focus();
		//$("#order\\.piid").attr('readonly', "true");

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#arrival\\.arrivedate").val(shortToday());
		
		ajax();

		//historyAjax();//到货登记历史记录

		//autocomplete();
		
		//$('#example').DataTable().columns.adjust().draw();
		
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
			
			var makeType=$('#makeType').val();	
			$('#formModel').attr("action", "${ctx}/business/arrival?methodtype=insert"+ "&makeType="+makeType);
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
				var vreturn   = $(this).find("td").eq(7).find("span").text();//已退货
				var vsurplus  = $(this).find("td").eq(8).find("span").text();//剩余

				var fcontract= currencyToFloat(vcontract);
				var freceive = currencyToFloat(vreceive);
				var freturn  = currencyToFloat(vreturn);
				var fsurplus = floatToCurrency( fcontract - freceive + freturn );
				

				if(fsurplus > "0"){
					$(this).find("td").eq(4).find("input").val(fsurplus);//本次到货
					$(this).find("td").eq(8).find("span").text("0")//剩余数清零
				}
							
			})

		});
		

		$("#reverse").click(function () { 
			
			$('#example tbody tr').each (function (){

				var varrival  = $(this).find("td").eq(4).find("input").val();////本次收货
				var vcontract = $(this).find("td").eq(5).find("span").text();////合同数
				var vreceive  = $(this).find("td").eq(6).find("span").text();//已收货
				var vreturn   = $(this).find("td").eq(7).find("span").text();//已退货
				var vsurplus  = $(this).find("td").eq(8).find("span").text();//剩余

				var fcontract= currencyToFloat(vcontract);
				var freceive = currencyToFloat(vreceive);
				var freturn  = currencyToFloat(vreturn);
				var fsurplus = floatToCurrency( fcontract - freceive + freturn );

				if(varrival > "0"){
					$(this).find("td").eq(8).find("span").text(fsurplus);//剩余数
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
				
				<td width="100px" class="label">仓管员：</td>
				<td>
					<form:input path="arrival.userid" class="short read-only" value="${userName }" /></td>
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
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
<fieldset class="action" style="text-align: right;">
	<button type="button" id="insert" class="DTTT_button">确认收货</button>
	<button type="button" id="doView" class="DTTT_button">查看到货记录</button>
	<button type="button" id="goBack" class="DTTT_button">返回</button>
</fieldset>
<fieldset style="margin-top: -25px;">
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
				<th class="dt-center" width="60px">累计收货</th>
				<th class="dt-center" width="60px">累计退货</th>
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
					<td><span>${list.sumArrivalQty }</span></td>
					<td><span>${list.sumReturnQty }</span></td>
					<td><span id="surplus${ status.index}"></span></td>
				</tr>
				<script type="text/javascript">
						var index = '${status.index}';
						var quantity = currencyToFloat('${list.quantity}');
						var sumArrivalQty = currencyToFloat('${list.sumArrivalQty}');
						var sumReturn = currencyToFloat('${list.sumReturnQty}');
						var surplus = quantity - sumArrivalQty + sumReturn;
						
						$('#surplus'+index).html(floatToCurrency( surplus ))
				</script>
		
		</c:forEach>
		
	</tbody>
</table>
</div>
</fieldset>		
 <!-- 
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
				<th class="dt-center" width="30px"></th>
			</tr>
		</thead>
</table>
</div>
</fieldset>
 -->
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
