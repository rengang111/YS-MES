<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>产品设计明细-编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter = 5;
	var options="";

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
					
					rowIndex =  rowIndex+1;
					var hidden = "";
					
					hidden = '';
					
					var rowNode = $('#machineConfiguration')
						.DataTable()
						.row
						.add(
						  [
							'<td></td>',
							'<td><input   name="machineConfigList['+rowIndex+'].componentname" id="machineConfigList'+rowIndex+'.componentname"  class="short" /></td>',
							'<td><input   name="machineConfigList['+rowIndex+'].materialid"    id="machineConfigList'+rowIndex+'.materialid"  class="materialid"/></td>',
							'<td><span></span></td>',
							'<td><select  name="machineConfigList['+rowIndex+'].purchaser"   id="machineConfigList'+rowIndex+'.purchaser" style="width: 100px;"></select></td>',
							'<td><input   name="machineConfigList['+rowIndex+'].remark"   	 id="machineConfigList'+rowIndex+'.remark"   class="middle" /></td>',
							
							]).draw();
					
							$("#machineConfigList" + rowIndex + "\\.purchaser").html(options);
					
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

			var t=$('#machineConfiguration').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			}else{
				
				//var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				
				$().toastmessage('showWarningToast', "删除后,请点击[ 保存 ]按钮。");	
				t.row('.selected').remove().draw();
	
			}
					
		}
	});
	
	function machineConfigurationView() {

		var t = $('#machineConfiguration').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
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
					}, {		
					}, {				
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
		
	}//ajax()供应商信息

</script>

<script type="text/javascript">
	//塑料制品
	var plasticCont = 6;

	$.fn.dataTable.TableTools.buttons.add_rows2 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = plasticCont;
				var hidden = '';
				
				for (var i=0;i<1;i++){
					
					rowIndex =  rowIndex+1;
					var hidden = "";
					
					hidden = '';
					
					var rowNode = $('#plastic')
						.DataTable()
						.row
						.add(
						  [
							'<td></td>',
							'<td><input  name="plasticList['+rowIndex+'].componentname" id="plasticList'+rowIndex+'.componentname"  class="short" /></td>',
							'<td><input  name="plasticList['+rowIndex+'].materialid"    id="plasticList'+rowIndex+'.materialid"  class="materialid"/></td>',
							'<td><span></span></td>',
							'<td><input  name="plasticList['+rowIndex+'].materialquality" id="plasticList'+rowIndex+'.materialquality" class="short" /></td>',
							'<td><input  name="plasticList['+rowIndex+'].color"           id="plasticList'+rowIndex+'.color" class="mini" /></td>',
							'<td><input  name="plasticList['+rowIndex+'].remark"   	      id="plasticList'+rowIndex+'.remark"   class="middle" /></td>',
							
							]).draw();
					
					rowIndex ++;						
				}					
				plasticCont += 1;
				
				foucsInit();//设置新增行的基本属性
				
				autocomplete();//调用自动填充功能
				
			}
		});

	$.fn.dataTable.TableTools.buttons.reset2 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {

			var t=$('#plastic').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			}else{
				
				//var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				
				$().toastmessage('showWarningToast', "删除后,请点击[ 保存 ]按钮。");	
				t.row('.selected').remove().draw();
	
			}
					
		}
	});
	
	//塑料制品
	function plasticView() {

		var t = $('#plastic').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : 'T<"clear">rt',
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_rows2",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset2",
					"sButtonText" : "清空一行"
				}  ],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {	
					}, {				
					}, {				
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
		
	}//ajax()供应商信息

</script>

<script type="text/javascript">
	//配件
	var accessoryCont = 2;

	$.fn.dataTable.TableTools.buttons.add_rows3 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = accessoryCont;
				var hidden = '';
				
				for (var i=0;i<1;i++){
					
					rowIndex =  rowIndex+1;
					var hidden = "";
					
					hidden = '';
					
					var rowNode = $('#accessory')
						.DataTable()
						.row
						.add(
						  [
							'<td></td>',
							'<td><input  name="accessoryList['+rowIndex+'].componentname"   id="accessoryList'+rowIndex+'.componentname"  class="middle" /></td>',
							'<td><input  name="accessoryList['+rowIndex+'].materialquality" id="accessoryList'+rowIndex+'.materialquality" class="short" /></td>',
							'<td><input  name="accessoryList['+rowIndex+'].process"         id="accessoryList'+rowIndex+'.process" class="short" /></td>',
							'<td><input  name="accessoryList['+rowIndex+'].specification"   id="accessoryList'+rowIndex+'.specification" class="short" /></td>',
							'<td><input  name="accessoryList['+rowIndex+'].remark"   	    id="accessoryList'+rowIndex+'.remark"   class="short" /></td>',
							
							]).draw();
					
					rowIndex ++;						
				}					
				accessoryCont += 1;
				
				foucsInit();//设置新增行的基本属性
				
				autocomplete();//调用自动填充功能
				
			}
		});

	$.fn.dataTable.TableTools.buttons.reset3 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {

			var t=$('#accessory').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			}else{
				
				//var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				
				$().toastmessage('showWarningToast', "删除后,请点击[ 保存 ]按钮。");	
				t.row('.selected').remove().draw();
	
			}
					
		}
	});
	
	//配件
	function accessoryView() {

		var t = $('#accessory').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : 'T<"clear">rt',
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_rows3",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset3",
					"sButtonText" : "清空一行"
				}  ],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {	
					}, {				
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
		
	}//配件

</script>

<script type="text/javascript">
	//标贴
	var labelCont = 5;

	$.fn.dataTable.TableTools.buttons.add_rows4 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = labelCont;
				var hidden = '';
				
				for (var i=0;i<1;i++){
					
					rowIndex =  rowIndex+1;
					var hidden = "";
					
					hidden = '';
					
					var rowNode = $('#labelT')
						.DataTable()
						.row
						.add(
						  [
							'<td></td>',
							'<td><input  name="labelList['+rowIndex+'].componentname"   id="labelList'+rowIndex+'.componentname"  class="short" /></td>',
							'<td><input  name="labelList['+rowIndex+'].materialquality" id="labelList'+rowIndex+'.materialquality" class="middle" /></td>',
							'<td><input  name="labelList['+rowIndex+'].process"         id="labelList'+rowIndex+'.process" class="short" /></td>',
							'<td><input  name="labelList['+rowIndex+'].specification"   id="labelList'+rowIndex+'.specification" class="middle" /></td>',
							'<td><input  name="labelList['+rowIndex+'].remark"   	    id="labelList'+rowIndex+'.remark"   class="middle" /></td>',
							
							]).draw();
					
					rowIndex ++;						
				}					
				labelCont += 1;
				
				foucsInit();//设置新增行的基本属性
				
				autocomplete();//调用自动填充功能
				
			}
		});

	$.fn.dataTable.TableTools.buttons.reset4 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {

			var t=$('#labelT').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			}else{
				
				//var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				
				$().toastmessage('showWarningToast', "删除后,请点击[ 保存 ]按钮。");	
				t.row('.selected').remove().draw();
	
			}
					
		}
	});
	
	function labelView() {

		var t = $('#labelT').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : 'T<"clear">rt',
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_rows4",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset4",
					"sButtonText" : "清空一行"
				}  ],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {	
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
		
	}//标贴

</script>

<script type="text/javascript">
	//文字印刷
	var textPrintCont = 2;

	$.fn.dataTable.TableTools.buttons.add_rows5 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = textPrintCont;
				var hidden = '';
				
				for (var i=0;i<1;i++){
					
					rowIndex =  rowIndex+1;
					var hidden = "";
					
					hidden = '';
					//<input  name="textPrintList['+rowIndex+'].pdffilename"     id="textPrintList'+rowIndex+'.pdffilename"   type="file" size="30"/>
					var rowNode = $('#textPrint')
						.DataTable()
						.row
						.add(
						  [
							'<td></td>',
							'<td><input  name="textPrintList['+rowIndex+'].componentname"   id="textPrintList'+rowIndex+'.componentname"  class="short" /></td>',
							'<td><input  name="textPrintList['+rowIndex+'].materialquality" id="textPrintList'+rowIndex+'.materialquality" class="middle" /></td>',
							'<td><input  name="textPrintList['+rowIndex+'].size"            id="textPrintList'+rowIndex+'.size" class="short" /></td>',
							'<td><input  name="textPrintList['+rowIndex+'].color"           id="textPrintList'+rowIndex+'.color" class="short" /></td>',
							'<td></td>',

							]).draw();
					
					rowIndex ++;						
				}					
				textPrintCont += 1;
				
				foucsInit();//设置新增行的基本属性
				
				autocomplete();//调用自动填充功能
				
			}
		});

	$.fn.dataTable.TableTools.buttons.reset5 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {

			var t=$('#textPrint').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			}else{				
				$().toastmessage('showWarningToast', "删除后,请点击[ 保存 ]按钮。");	
				t.row('.selected').remove().draw();	
			}					
		}
	});
	

	function textPrintView() {

		var t = $('#textPrint').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : 'T<"clear">rt',
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_rows5",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset5",
					"sButtonText" : "清空一行"
				}  ],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {	
					}, {	
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
		
	}//文字印刷

</script>
	
<script type="text/javascript">
	//包装描述
	var packageCont = 2;

	$.fn.dataTable.TableTools.buttons.add_rows6 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = packageCont;
				var hidden = '';
				
				for (var i=0;i<1;i++){
					
					rowIndex =  rowIndex+1;
					var hidden = "";
					
					hidden = '';
					
					var rowNode = $('#package')
						.DataTable()
						.row
						.add(
						  [
							'<td></td>',
							'<td><input  name="packageList['+rowIndex+'].componentname"   id="packageList'+rowIndex+'.componentname"  class="short" /></td>',
							'<td><input  name="packageList['+rowIndex+'].materialquality" id="packageList'+rowIndex+'.materialquality" class="middle" /></td>',
							'<td><input  name="packageList['+rowIndex+'].packingqty"      id="packageList'+rowIndex+'.packingqty" class="short" /></td>',
							'<td><input  name="packageList['+rowIndex+'].size"            id="packageList'+rowIndex+'.size" class="short" /></td>',
							'<td><input  name="packageList['+rowIndex+'].remark"   	      id="packageList'+rowIndex+'.remark"   class="middle" /></td>',
							
							]).draw();
					
					rowIndex ++;						
				}					
				packageCont += 1;
				
				foucsInit();//设置新增行的基本属性
				
				autocomplete();//调用自动填充功能
				
			}
		});

	$.fn.dataTable.TableTools.buttons.reset6 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {

			var t=$('#package').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			}else{
				
				//var amount = $('#example tbody tr').eq(rowIndex).find("td").eq(6).find("input").val();
				
				$().toastmessage('showWarningToast', "删除后,请点击[ 保存 ]按钮。");	
				t.row('.selected').remove().draw();
	
			}
					
		}
	});
	
	function packageView() {

		var t = $('#package').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			dom : 'T<"clear">rt',
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_rows6",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset6",
					"sButtonText" : "清空一行"
				}  ],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {	
					}, {				
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
		
	}//包装描述enctype="multipart/form-data"

</script>

</head>
<body>
<div id="container">
<div id="main">
	
<form:form modelAttribute="formModel" method="POST"  
	id="form" name="form"   autocomplete="off">
	
	<input type="hidden" id="PIId" value="${PIId}" />
	<input type="hidden" id="goBackFlag" value="${goBackFlag }" />
	<form:hidden path="productDesign.ysid"  value="${product.YSId}" />
	<form:hidden path="productDesign.productid"  value="${product.materialId}" />
	
<fieldset>
	<legend>做单资料</legend>

	<table class="form" >		
		<tr>
			<td class="label" style="width: 100px;">耀升编号：</td>
			<td style="width: 150px;">&nbsp;${product.YSId}</td>
								
			<td class="label" style="width: 100px;">产品编号：</td>
			<td style="width: 150px;">
				&nbsp;<a href="###" onClick="doShowProduct()">${product.materialId}</a></td>
			
			<td class="label" style="width: 100px;">产品名称：</td>
			<td>&nbsp;${product.materialName}</td>
		</tr>
		<tr>				
			<td class="label">交货时间：</td>
			<td>&nbsp;${product.deliveryDate}</td>				
								
			<td class="label">交货数量：</td>
			<td>&nbsp;${product.quantity}</td>
			
			<td class="label">封样数量：</td>
			<td><form:input path="productDesign.sealedsample"  class="middle"/></td>		
		</tr>
		<tr>
			<td class="label">版本类别：</td>
			<td>&nbsp;${product.productClassify}</td>
			<td class="label" >电池包数量：</td>
			<td>
				<form:select path="productDesign.batterypack" style="width: 100px;">							
					<form:options items="${batteryPackList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
								
			<td class="label">充电器：</td>
			<td>
				<form:select path="productDesign.chargertype" style="width: 100px;">							
					<form:options items="${chargerTypeList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
				
		</tr>
		<tr>		
			<td class="label">包装描述：</td>
			<td colspan="5"><form:input path="productDesign.packagedescription"  class="long"/></td>
		</tr>
	</table>
</fieldset>	
		
<div style="clear: both"></div>		
<div class="action" style="text-align: right;margin-right: 10px;">
	<button type="button" id="doSave" class="DTTT_button" >保存</button>
	<button type="button" id="goBack" class="DTTT_button">返回</button>
</div>

<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;">机器配置</legend>
	<div class="list">
	<table id="machineConfiguration" class="display" >
		<thead>				
			<tr style="text-align: left;">
				<th width="1px">No</th>
				<th style="width:60px">名称</th>
				<th style="width:120px">ERP编码</th>
				<th >产品名称</th>
				<th style="width:105px">采购方</th>
				<th style="width:255px">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" begin="0" end="5" step="1">
				<tr>
					<td></td>
					<td><form:input path="machineConfigList[${i}].componentname"  class="short"/></td>
					<td><form:input path="machineConfigList[${i}].materialid"  class="materialid"/></td>
					<td><span></span></td>
					<td>
						<form:select path="machineConfigList[${i}].purchaser" style="width: 100px;">							
							<form:options items="${purchaserList}" 
								itemValue="key" itemLabel="value" /></form:select>
					</td>
					<td><form:input path="machineConfigList[${i}].remark"  class="middle"/></td>
				</tr>
			
			</c:forEach>
		</tbody>
	</table>
	</div>
</fieldset>

<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;">塑料制品</legend>
	<div class="list">
	<table id="plastic" class="display" >
		<thead>				
			<tr style="text-align: left;">
				<th width="1px">No</th>
				<th style="width:60px">名称</th>
				<th style="width:120px">ERP编码</th>
				<th >产品名称</th>
				<th style="width:40px">材质</th>
				<th style="width:60px">颜色</th>
				<th style="width:60px">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" begin="0" end="6" step="1">
				<tr>
					<td></td>
					<td><form:input path="plasticList[${i}].componentname"  class="short"/></td>
					<td><form:input path="plasticList[${i}].materialid"  class="materialid"/></td>
					<td><span></span></td>
					<td><form:input path="plasticList[${i}].materialquality"  class="short"/></td>
					<td><form:input path="plasticList[${i}].color"  class="mini"/></td>
					<td><form:input path="plasticList[${i}].remark"  class="middle"/></td>
				</tr>
			
			</c:forEach>
		</tbody>		
	</table>
	</div>
</fieldset>
<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;">配件清单</legend>
	<div class="list">
	<table id="accessory" class="display">
		<thead>				
			<tr style="text-align: left;">
				<th width="1px">No</th>
				<th style="width:60px">配件名称及规格描述</th>
				<th style="width:40px">材质</th>
				<th style="width:60px">加工方式</th>
				<th style="width:60px">表面处理</th>
				<th style="width:60px">备注</th>
			</tr>
		</thead>	
		<tbody>
			<c:forEach var="i" begin="0" end="2" step="1">
				<tr>
					<td></td>
					<td><form:input path="accessoryList[${i}].componentname"  class="middle"/></td>
					<td><form:input path="accessoryList[${i}].materialquality"  class="short"/></td>
					<td><form:input path="accessoryList[${i}].process"  class="short"/></td>
					<td><form:input path="accessoryList[${i}].specification"  class="short"/></td>
					<td><form:input path="accessoryList[${i}].remark"  class="short"/></td>
				</tr>
			
			</c:forEach>
		</tbody>				
	</table>
	</div>
</fieldset>
<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;margin-top: 20px;">产品收纳描述信息</legend>
	<div class="list">
	<table id="orderBom1" class="display" >
		<thead>		
			<tr>
				<td>
					<form:textarea path="productDesign.storagedescription" style="width: 800px; height: 150px;margin-top: 30px;"/>
				</td>

			</tr>
		</thead>			
	</table>
	</div>
</fieldset>
<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;">标贴</legend>
	<div class="list">
		<table id="labelT" class="display">
			<thead>				
				<tr style="text-align: left;">
					<th width="1px">No</th>
					<th style="width:60px">配件名称</th>
					<th style="width:40px">材质及要求</th>
					<th style="width:60px">尺寸</th>
					<th style="width:60px">备注</th>
				</tr>
			</thead>	
			<tbody>
				<c:forEach var="i" begin="0" end="5" step="1">
					<tr>
						<td></td>
						<td><form:input path="labelList[${i}].componentname"  class="short"/></td>
						<td><form:input path="labelList[${i}].materialquality"  class="middle"/></td>
						<td><form:input path="labelList[${i}].size"  class="short"/></td>
						<td><form:input path="labelList[${i}].remark"  class="middle"/></td>
					</tr>
				
				</c:forEach>
			</tbody>
		</table>
	</div>				
</fieldset>
<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;">文字印刷</legend>
	<div class="list">
	<table id="textPrint" class="display">
		<thead>				
			<tr style="text-align: left;">
				<th width="1px">No</th>
				<th style="width:60px">配件名称</th>
				<th style="width:40px">材质</th>
				<th style="width:60px">尺寸</th>
				<th style="width:60px">颜色</th>
				<th style="width:60px">文件</th>
			</tr>
		</thead>	
		<tbody>
			<c:forEach var="i" begin="0" end="2" step="1">
				<tr>
					<td></td>
					<td><form:input path="textPrintList[${i}].componentname"  class="short"/></td>
					<td><form:input path="textPrintList[${i}].materialquality"  class="middle"/></td>
					<td><form:input path="textPrintList[${i}].size"  class="short"/></td>
					<td><form:input path="textPrintList[${i}].color"  class="short"/></td>
					<td><!-- input name="pdfFile" id="pdfFile" type="file" size="30" /> --></td>
				</tr>
			
			</c:forEach>
		</tbody>				
	</table>
	</div>
</fieldset>
<fieldset>
	<legend style="margin-bottom: -60px;margin-left: 10px;">包装描述</legend>
	<div class="list">
	<table id="package" class="display">
		<thead>				
			<tr style="text-align: left;">
				<th width="1px">No</th>
				<th style="width:60px">名称</th>
				<th style="width:40px">材质</th>
				<th style="width:60px">装箱数量</th>
				<th style="width:60px">尺寸</th>
				<th style="width:60px">备注</th>
			</tr>
		</thead>	
		<tbody>
			<c:forEach var="i" begin="0" end="2" step="1">
				<tr>
					<td></td>
					<td><form:input path="packageList[${i}].componentname"  class="short"/></td>
					<td><form:input path="packageList[${i}].materialquality"  class="middle"/></td>
					<td><form:input path="packageList[${i}].packingqty"  class="short"/></td>
					<td><form:input path="packageList[${i}].size"  class="short"/></td>
					<td><form:input path="packageList[${i}].remark"  class="middle"/></td>
				</tr>
			
			</c:forEach>
		</tbody>				
	</table>
	</div>
</fieldset>

</form:form>
</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	
	machineConfigurationView();//机器配置

	plasticView();//塑料制品
	accessoryView();//配件清单
	labelView();//标贴
	textPrintView();//文字印刷
	packageView();//包装描述
	
	$( "#tabs" ).tabs();
	
	setMachineName();//设置机器配置默认项
	setPlasticName();//塑料制品默认项
	setLableName();//标贴
	setTextPrintName();//文字印刷
	setPackageName();//包装描述
	
	var i = 0;	
	<c:forEach var="purchaser" items="${purchaserList}">
		i++;
		options += '<option value="${purchaser.key}">${purchaser.value}</option>';
	</c:forEach>
	
	autocomplete();
	
	
	$("#goBack").click(function() {
		var PIId=$('#PIId').val();
		var goBackFlag = $('#goBackFlag').val();
		
		if(goBackFlag == "1"){
			//该查看页面来自于一览
			var url = '${ctx}/business/productDesign?keyBackup='+ PIId;
			
		}else{
			var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
			
		}
		location.href = url;			
	});

	$("#doSave").click(function() {
		$('#form').attr("action", 
				"${ctx}/business/productDesign?methodtype=insert");
		$('#form').submit();	

	});

	foucsInit();//设置input的基本属性
    	
});
</script>
<script type="text/javascript">

function doEditMaterial(recordid,parentid) {
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

//机器配置
function setMachineName(){

	var name=new Array("钻夹头","开关","电机","线路板","电池","充电器");

	$('#machineConfiguration tbody tr').each (function (index){
		
		$(this).find("td").eq(1).find("input").val(name[index]);

	})
}

//塑料制品
function setPlasticName(){

	var name=new Array("机身","电池包壳体","充电器壳体","开关","正反转拨杆","单双速推扭","电池包按钮");

	$('#plastic tbody tr').each (function (index){
		
		$(this).find("td").eq(1).find("input").val(name[index]);

	})
}
//文字印刷
function setTextPrintName(){

	var name=new Array("说明书","警告说明书","CE声明");

	$('#textPrint tbody tr').each (function (index){
		
		$(this).find("td").eq(1).find("input").val(name[index]);

	})
}
//标贴
function setLableName(){

	var name=new Array("机身标贴正面","机身标贴反面","电池包标贴正面","电池包标贴反面","电池包底部标贴","充电器标贴");

	$('#labelT tbody tr').each (function (index){
		
		$(this).find("td").eq(1).find("input").val(name[index]);

	})
}

//包装
function setPackageName(){

	var name=new Array("彩盒","中盒","外箱");

	$('#package tbody tr').each (function (index){
		
		$(this).find("td").eq(1).find("input").val(name[index]);

	})
}

function foucsInit(){
	
	$("#machineConfiguration input").addClass('bgnone').addClass('bsolid');
	$("#plastic input").addClass('bgnone').addClass('bsolid');
	$("#accessory input").addClass('bsolid').addClass('bsolid');
	$("#textPrint input").addClass('bsolid').addClass('bsolid');
	$("#labelT input").addClass('bsolid').addClass('bsolid');
	$("#package input").addClass('bsolid').addClass('bsolid');
	
}
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
									id : item.materialId,
									name : item.materialName,
									materialId : item.materialId
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
			$(this).parent().parent().find("td").eq(3).find("span")
				.html(jQuery.fixedWidth(ui.item.name,45));

			//产品编号
			//$(this).parent().find("input:hidden").val(ui.item.materialId);
			
		},
	});
}

function doShowProduct() {
	var materialId = '${product.materialId}';
	callProductView(materialId);
	/*
	var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '500px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  return false; 
		}    
	});	
	*/
}

</script>
</body>
</html>
