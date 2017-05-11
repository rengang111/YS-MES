<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-到货登记</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter = 5;
	var shortYear = ""; 
	
	$.fn.dataTable.TableTools.buttons.add_rows = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {
					
					var rowIndex = counter;
					var hidden = '';
					
					for (var i=0;i<1;i++){						
						var rowNode = $('#example')
							.DataTable()
							.row
							.add(
							  [
								'<td class="dt-center"></td>',
								'<td><input type="text"   name="arrivalList['+rowIndex+'].contractid" id="arrivalList'+rowIndex+'.contractid" class="contractId" /></td>',
								'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
									'<input type="hidden" name="arrivalList['+rowIndex+'].materialid" id="arrivalList'+rowIndex+'.materialid" /></td>',
								'<td><span></span></td>',
								'<td><span></span></td>',
								'<td><input type="text"   name="arrivalList['+rowIndex+'].quantity"   id="arrivalList'+rowIndex+'.quantity"  class="cash mini" /></td>',
								'<td><span></span></td>',
								'<td><span></span></td>',
								'<td><span></span></td>',
								
								]).draw();
						
						rowIndex ++;						
					}					
					counter += 1;
					
					foucsInit();//设置新增行的基本属性
					
					autocomplete();//调用自动填充功能
					
				}
			});

	$.fn.dataTable.TableTools.buttons.reset = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {

			var t=$('#example').DataTable();
			
			rowIndex = t.row('.selected').index();

			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的行。");	
			}else{
				
				var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				
				t.row('.selected').remove().draw();
			}
						
		}
	});
	
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

			dom : 'T<"clear">rt',

			"tableTools" : {

				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

				"aButtons" : [ {
					"sExtends" : "add_rows",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset",
					"sButtonText" : "清空一行"
				}  ],
			},
			
			"columns" : [
			        	{"className":"dt-body-center"
					}, {
					}, {
					}, {
					}, {"className":"dt-body-center"
					}, {
					}, {"className":"dt-body-right"	
					}, {"className":"dt-body-right"
					}, {"className":"dt-body-right"
					}
				]
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(6)',function() {

			var $td = $(this).parent().find("td");

			var $oArrival = $td.eq(5).find("input");
			var $oRecorde = $td.eq(6).find("span");
			var $oQuantity= $td.eq(7).find("span");
			var $oSurplus = $td.eq(8).find("span");

			var fArrival  = currencyToFloat($oArrival.val());
			var fRecorde  = currencyToFloat($oRecorde.html());
			var fquantity = currencyToFloat($oQuantity.html());	
			
			if(fArrival > fquantity){

				$().toastmessage('showWarningToast', "到货数量不能大于合同数量");
				return;
			}
			
			//剩余数量
			var fsurplus = floatToNumber(fquantity - fRecorde - fArrival);	
			$oSurplus.html(fsurplus);

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

		//设置光标项目
		//$("#attribute1").focus();
		//$("#order\\.piid").attr('readonly', "true");

		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#arrival\\.arrivedate").val(shortToday());
		
		ajax();


		autocomplete();
		
		//$('#example').DataTable().columns.adjust().draw();
		
		$("#order\\.orderdate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 

		$("#order\\.deliverydate").datepicker({
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
		
		foucsInit();
		
		$(".cash") .blur(function(){
			
			var currency = $('#order\\.currency option:checked').text();// 选中项目的显示值
			$(this).val(floatToSymbol($(this).val(),currency));
		});
		
		$('select').css('width','100px');
		$(".DTTT_container").css('float','left');
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
		<legend> 到货登记</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px"><label>到货编号：</label></td>					
				<td>
					<form:input path="arrival.arrivalid" class="short required read-only" value="${arrivalId }" /></td>
														
				<td width="100px" class="label">到货日期：</td>
				<td>
					<form:input path="arrival.arrivedate" class="short read-only" /></td>
				
				<td width="100px" class="label">仓管员：</td>
				<td>
					<form:input path="arrival.userid" class="short read-only" value="${userName }" /></td>
			</tr>
			<tr> 				
				<td class="label"><label>供应商：</label></td>					
				<td colspan="5">
					<div class="ui-widget">
						<input type="text" id="attribute1" class="middle" />
						<span style="color: blue">&nbsp;
						（查询范围：供应商编号、简称、全称）</span>
						</div></td>			
			</tr>
			<tr> 				
				<td class="label"><label>供应商名称：</label></td>					
				<td colspan="5">
					<input type="text" id="supplierName" class="long read-only" /></td>
			
			</tr>
										
		</table>
</fieldset>

<fieldset>
	<legend>到货详情</legend>
	<div class="list">
	
	<table id="example" class="display" >
		<thead>				
		<tr>
			<th width="1px">No</th>
			<th class="dt-center" width="80px">合同编号</th>
			<!-- th class="dt-left" width="70px">耀升编号</th> -->
			<th class="dt-center" width="175px">物料编号</th>
			<th class="dt-center" >物料名称</th>
			<th class="dt-center" width="30px">单位</th>
			<th class="dt-center" width="60px">本次<br/>到货数量</th>
			<th class="dt-center" width="60px">累计<br/>到货数量</th>
			<th class="dt-center" width="60px">合同<br/>总数量</th>
			<th class="dt-center" width="60px">剩余数量</th>
		</tr>
		</thead>
		
	<tbody>
		<c:forEach var="i" begin="0" end="4" step="1">		
			<tr>
				<td></td>
				<td><form:input path="arrivalList[${i}].contractid" class="contractId"/></td>
				<td><input type="text" name="attributeList1" class="attributeList1">
					<form:hidden path="arrivalList[${i}].materialid" /></td>
				<td><span></span></td>
				<td><span></span></td>
				<td><form:input path="arrivalList[${i}].quantity" class="num mini" /></td>
				<td><span></span></td>
				<td><span></span></td>
				<td><span></span></td>
			</tr>
				
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
	
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function autocomplete(){
	
	//合同编号自动提示
	$(".contractId").autocomplete({
		minLength : 2,
		autoFocus : false,
		
		search: function( event, ui ) {
			var supplierId = $("#attribute1").val();

			 if(supplierId ==""){
				$().toastmessage('showWarningToast', "请先输入供应商编号。");	
				 return false;
			 }
			
		},
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
	
	
	$(".attributeList1").autocomplete({
		minLength : 0,
		autoFocus : false,
		
		search: function( event, ui ) {
			var $tds = $(this).parent().parent().find('td');
			var contractId = $tds.eq(1).find("input:text").val();
			$('#tmpMaterialId').val(contractId);

			 if(contractId ==""){
				$().toastmessage('showWarningToast', "请先输入合同编号。");	
				 return false;
			 }
			
		},
		
		source : function(request, response) {
			//alert(888);
			var contractId = $('#tmpMaterialId').val();
			$.ajax({
				type : "POST",
				url : "${ctx}/business/contract?methodtype=getContractByMaterail",
				dataType : "json",
				data : {
					materialId : request.term,
					contractId : contractId
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.materialId+"｜"+item.materialName,
									value : item.materialId,
									id : item.materialId,
									name : item.materialName,
									materialId : item.materialId,
									quantity : item.quantity,
									unit : item.unit,
									recodeSum : item.recodeSum
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

		select : function(event, ui) {
			
			//产品名称
			var $tds = $(this).parent().parent().find("td");
			

			//产品编号
			$(this).parent().find("input:hidden").val(ui.item.materialId);
			var contractId = $tds.eq(1).find("input").val();
			
			var farrvail = currencyToFloat($tds.eq(5).find("input").val());
			var fquantiy = currencyToFloat(ui.item.quantity);
			var frecode  = currencyToFloat(ui.item.recodeSum);
			var fsurplus = (fquantiy - frecode - farrvail);

			$tds.eq(3).find("span").html(jQuery.fixedWidth(ui.item.name,25));
			$tds.eq(4).find("span").html(ui.item.unit);
			$tds.eq(6).find("span").html(ui.item.recodeSum);//累计到货
			$tds.eq(7).find("span").html(floatToNumber(ui.item.quantity));//总数量
			$tds.eq(8).find("span").html(floatToNumber(fsurplus));//剩余数量
			
			//alert('contractId'+contractId)
		
		},
	});//attributeList1
	
	
}

</script>

<script type="text/javascript">

	$("#attribute1").autocomplete({
		
		source : function(request, response) {
			//alert(888);
			var supplierId = '';
			//alert(supplierId)
			$.ajax({
				type : "POST",
				url : "${ctx}/business/material?methodtype=supplierSearch2&supplierId="+supplierId,
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(777);
					response($.map(
						data.data,
						function(item) {
							//alert(item.viewList)
							return {
								label : item.viewList,
								value : item.supplierId,
								id : item.supplierId,
								shortName : item.shortName,
								fullName : item.fullName,
								
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

		select : function(event, ui) {	
			$("#supplierName").val(ui.item.fullName);

		},

		minLength : 2,
		autoFocus : false,
		width: 500,
	});
</script>
</html>
