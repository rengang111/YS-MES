<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>直接入库-编辑</title>
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
					var hidden =	'<input type="hidden" name="applyDetailList['+counter+'].subbomid" id="applyDetailList'+counter+'.subbomid" value=""/>'+
								 	'<input type="hidden" name="applyDetailList['+counter+'].subbomserial" id="applyDetailList'+counter+'.subbomserial" value=""/>' 	

				 	var rowNode = $('#example')
					.DataTable()
					.row
					.add(
					  [
'<td>'+rowIndex+'</td>',
'<td><input type="text" name="applyDetailList['+counter+'].materialid" id="applyDetailList'+counter+'.materialid" class="materialid"/></td>',
'<td><span></span></td>',
'<td><span></span></td>',
'<td><input type="text" name="applyDetailList['+counter+'].quantity" id="applyDetailList'+counter+'.quantity"  class="quantity num short" /></td>',
				
					]).draw();
					
					$("#applyDetailList" + counter + "\\.materialid").focus();
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
		
		$("#stockinApply\\.requestdate").val(shortToday());		
		$("#stockinApply\\.requestdate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 
		
		ajaxRawGroup();	
		
		$("#goBack").click(
				function() {
					var url = "${ctx}/business/stockinapply";					
					location.href = url;		
				});
		
		$("#update").click(
				function() {
			$('#formModel').attr("action",
					"${ctx}/business/stockinapply?methodtype=stockInApplyUpdate");
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
			
		<form:hidden path="stockinApply.stockinapplyid" value="${apply.stockInApplyId }" />
		<form:hidden path="stockinApply.recordid" value="${apply.recordId }" />
		<fieldset>
			<legend> 直接入库申请</legend>
			<table class="form" id="table_form">				
				<tr>
					<td class="label" width="100px">申请单编号：</td>
					<td width="150px">${apply.stockInApplyId }</td>

					<td width="100px" class="label">申请日期：</td>
					<td width="150px">${apply.requestDate }</td>

					<td width="100px" class="label">申请人：</td>
					<td>${apply.storageName }</td>
				</tr>
			</table>			
		</fieldset>	
		<div style="clear: both"></div>	
		<fieldset class="action" style="text-align: right;">
			<button type="button" id="update" class="DTTT_button">确认申请</button>
			<button type="button" id="goBack" class="DTTT_button">返回</button>
		</fieldset>
		<fieldset>
			<legend> 物料详情</legend>
			
			<div class="list">
				<table id="example" class="display">	
					<thead>
					<tr>
						<th style="width:30px">No</th>
						<th style="width:200px">物料编码</th>
						<th>物料名称</th>
						<th style="width:80px">单位</th>
						<th style="width:150px">入库数量</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach var='detail' items='${applyDetail}' varStatus='status'>
							<tr>
								<td><c:out value="${status.index + 1 }"/></td>
								<td><form:input path="applyDetailList[${status.index}].materialid" value="${detail.materialId }"  class="materialid"/></td>								
								<td><span>${detail.materialName }</span></td>					
								<td><span>${detail.unit }</span></td>
								<td><form:input path="applyDetailList[${status.index}].quantity" value="${detail.quantity }" class="quantity num short"/></td>
							</tr>
								<script type="text/javascript">
									
									counter++;
								</script>
						</c:forEach>
						
					</tbody>
				</table>
			</div>
		</fieldset>
		<fieldset>
			<legend> 备注信息</legend>
			<table>
				<tr>				
					<td>
						<form:textarea path="stockinApply.remarks" rows="3" cols="80" /></td>
					
				</tr>		
			</table>
		</fieldset>
		
	</form:form>

</div>
</div>
</body>
</html>
