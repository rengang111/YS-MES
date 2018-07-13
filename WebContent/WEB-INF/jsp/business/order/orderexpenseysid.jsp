<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>领料退还-新建</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
		
	var counter = 0;
	var moduleNum = 0;
	
	$.fn.dataTable.TableTools.buttons.add_row = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {

				for (var i=0;i<1;i++){
							
					var orderQuantity = '${order.totalQuantity}';
					var trhtml = "";
					var rowIndex = counter + 1;
				 	var rowNode = $('#example')
					.DataTable()
					.row
					.add(
					  [
'<td>'+rowIndex+'</td>',
'<td><input type="text" name="detailList['+counter+'].materialid" id="detailList'+counter+'.materialid" class="materialid"/></td>',
'<td><span></span></td>',
'<td><span></span></td>',
'<td><input type="text" name="detailList['+counter+'].quantity" id="detailList'+counter+'.quantity"  class="quantity num short" /></td>',
				
					]).draw();
					
					$("#detailList" + counter + "\\.materialid").focus();
					counter ++;			
				}
				
				autocomplete();
					
				foucsInit();
				
			}
		});
		
	$.fn.dataTable.TableTools.buttons.reset = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {
					
					var t=$('#example').DataTable();
					
					rowIndex = t.row('.selected').index();
					
					if(typeof rowIndex == "undefined"){				
						$().toastmessage('showWarningToast', "请选择要删除的数据。");	
					}else{
						if(confirm("删除后不可恢复，确定要删除吗？")) {

							t.row('.selected').remove().draw();
							$().toastmessage('showNoticeToast', "删除成功。");	

						}	
					}
						
				}
			
			});
	
	function ajaxRawGroup() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 500,
	        "ordering"  : false,
	       	"dom"		  : 'T<"clear">rt',
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [
					{
						"sExtends" : "add_row",
						"sButtonText" : "追加行"
					},
					{
						"sExtends" : "reset",
						"sButtonText" : "删除行"
					}],
			},			
			"columns" : [ 
			           {"className":"dt-body-center"
					}, {"className":"td-left"
					}, {							
					}, {"className":"td-center"		
					}, {"className":"td-center"				
					}			
				],				
			
		}).draw();
		
		t.on('blur', 'tr td:nth-child(6)',function() {
			
	           $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
			
		t.on('change', 'tr td:nth-child(6)',function() {
			
            var $tds = $(this).parent().find("td");
			
            var $oQuantity  = $tds.eq(5).find("input");
			var $oThisPrice = $tds.eq(6).find("input");
			var $oAmounti   = $tds.eq(7).find("input:hidden");
			var $oAmounts   = $tds.eq(7).find("span");
			
			var fPrice    = currencyToFloat($oThisPrice.val());		
			var fQuantity = currencyToFloat($oQuantity.val());			
			var fTotalOld = currencyToFloat($oAmounti.val());
			
			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vQuantity = floatToNumber(fQuantity);
			var vTotalNew = floatToCurrency(fTotalNew);
					
			//详情列表显示新的价格					
			$oQuantity.val(vQuantity);	
			$oAmounti.val(vTotalNew);	
			$oAmounts.html(vTotalNew);	

			
		});
			
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

	};//ajaxRawGroup()
	
	$(document).ready(function() {

		autocomplete();
		
		$("#requisition\\.requisitiondate").val(shortToday());		
		$("#requisition\\.requisitiondate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 
		
		ajaxRawGroup();	
		
		$("#goBack").click(
				function() {
					var url = "${ctx}/business/order?methodtype=orderExpenseInit";					
					location.href = url;		
				});
		
		$("#insert").click(function() {
			
			var ysid = $('#requisition\\.ysid').val();
			if(ysid == '' || $.trim(ysid) == ''){
				$().toastmessage('showWarningToast', "请选择要退还的耀升编号。");
				return;
			}
			$('#formModel').attr("action",
					"${ctx}/business/requisition?methodtype=stockoutReturnInsert");
			$('#formModel').submit();
		});
				
		//input格式化
		foucsInit();

		$(".DTTT_container").css('float','left');
	});	
	
	
	function doShowMaterial(recordid,parentid) {
		//var height = setScrollTop();
		//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
		var url = '${ctx}/business/material?methodtype=detailView';
		url = url + '&parentId=' + parentid+'&recordId='+recordid+'&keyBackup=1';

		layer.open({
			offset :[10,''],
			type : 2,
			title : false,
			area : [ '1100px', '520px' ], 
			scrollbar : false,
			title : false,
			content : url,
			//只有当点击confirm框的确定时，该层才会关闭
			cancel: function(index){ 
			 // if(confirm('确定要关闭么')){
			    layer.close(index)
			 // }
			  $('#baseBomTable').DataTable().ajax.reload(null,false);
			  return false; 
			}    
		});		

	};
	
</script>

<script type="text/javascript">
function autocomplete(){
	$(".materialid").autocomplete({
		minLength : 2,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/order?methodtype=getMaterialList",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.viewList,
									value : item.materialId,
									name : item.materialName,
									price : item.price,
									quantityOnHand : item.quantityOnHand,
									purchaseType : item.purchaseType,
									unit : item.dicName
								}
							}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
					//alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},

		select : function(event, ui) {
			
			 var $tds = $(this).parent().parent().find("td");
			 
			//产品名称
			 $tds.eq(2).find("span").html(jQuery.fixedWidth(ui.item.name,50));
			 $tds.eq(3).find("span").html(ui.item.unit);
			 //$tds.eq(3).find("input").val(ui.item.purchaseType);
			// $tds.eq(4).find("span").html(ui.item.quantityOnHand);
			// $tds.eq(6).find("span").html(ui.item.price);//单价
			// $tds.eq(6).find("input").val(ui.item.price);//单价
			 
			//产品名称
			//$(this).parent().parent().find("td").eq(2).find("span").html(jQuery.fixedWidth(ui.item.name,50));

			//产品编号
			//$(this).parent().find("input:hidden").val(ui.item.materialId);
			
		},
	});
	
	
	$("#contract\\.ysid").autocomplete({
		minLength : 2,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/order?methodtype=getYsidList",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.YSId +" | "+ item.materialId +" | "+ item.materialName,
									value : item.YSId,
								}
							}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
					//alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},
	
	});
}
</script>	
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">
			
		<fieldset>
			<legend> 订单过程--选择订单</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" width="100px">耀升编号：</td>					
					<td colspan="5">
						<input type="text" id="ysid" class="middel" />
						<span style="color: blue">（查询范围：耀升编号、产品编号、产品名称）</span></td>
										
				</tr>
				<tr>
					<td class="label">订单数量：</td>					
					<td width="150px">&nbsp;<span id="quantity"></span></td>
								
					<td class="label"width="100px">产品编号：</td>					
					<td width="100px"><span id="materialId"></span></td>
								
					<td class="label" width="100px">产品名称：</td>					
					<td><span id="materialName"></span></td>
				</tr>
			</table>
			
		</fieldset>
		
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	
</form:form>

<script type="text/javascript">


	
$("#ysid").autocomplete({

	source : function(request, response) {
		//alert(888);
		$.ajax({
			type : "POST",
			url : "${ctx}/business/order?methodtype=getYsidList",
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
							label 		: item.YSId+" | "+item.materialId+" | "+item.materialName,
							value 		: item.YSId,
							materialId 	: item.materialId,
							materialName: item.materialName,
							quantity 	: item.totalQuantity,
							
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
	
	select : function(event, ui) {//选择物料分类后,自动添加流水号IPid

		var actionUrl = "${ctx}/business/bom?methodtype=orderexpenseadd";
		actionUrl = actionUrl +"&YSId="+ui.item.value;

		location.href = actionUrl;
	

	},//select
	
	minLength : 5,
	autoFocus : false,
});


	
</script>
</div>
</div>
</body>
</html>
