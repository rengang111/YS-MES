<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>产品设计资料-新建</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	//机器配置
	var counter = 5;
	var purchaserOptions="";

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
					
							$("#machineConfigList" + rowIndex + "\\.purchaser").html(purchaserOptions);
					
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
	var accessoryCont = 0;
	var dbCnt3 = '${accessoryCount}';
	if(dbCnt3 >0 ){
		accessoryCont = Number(dbCnt3)-1;
	}

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
							'<td class="dt-center"></td>',
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
							'<td><div id="fileName'+rowIndex+'"></div>'+
								'<input  name="textPrintList['+rowIndex+'].filename"        id="textPrintList'+rowIndex+'.filename" type="hidden" /></td>',
							'<td><div id="uploadItem'+rowIndex+'" >'+
								'<input  name="pdfFile" id="pdfFile" onchange="uploadFileFn('+rowIndex+')" type="file" size="30" style="width:60px;border:0px"/>'+
								'</div>'+
								'<div id="deleteItem'+rowIndex+'" style="display:none">'+
								'<a href="###" onclick="deleteTextPrintFile('+rowIndex+')">删除</a>'+
								'</div>'+
							'</td>',
							
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
					}, {"className":"dt-body-center"
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
		
	}//包装描述

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
			<td colspan="3">&nbsp;${product.materialName}</td>
		</tr>
		<tr>				
			<td class="label">交货时间：</td>
			<td>&nbsp;${product.deliveryDate}</td>				
								
			<td class="label">交货数量：</td>
			<td>&nbsp;${product.quantity}</td>
			
			<td class="label">封样数量：</td>
			<td colspan="3"><form:input path="productDesign.sealedsample"  class="short"/></td>		
		</tr>
		<tr>
			<td class="label">包装描述：</td>
			<td colspan="3"><form:input path="productDesign.packagedescription"  class="long"/></td>

			<td class="label">版本类别：</td>
			<td>&nbsp;${product.productClassify}</td>
								
			<td class="label">资料完成状况：</td>
			<td style="width: 150px;">
				<form:select path="productDesign.status" style="width: 100px;">							
					<form:options items="${statusList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
		</tr>
		<c:set var="type"  value="${product.productClassifyId}"/>
		<c:if test="${type eq '010'}">		
		<tr>
			<td class="label" >电池包数量：</td>
			<td>
				<form:select path="productDesign.batterypack" style="width: 100px;">							
					<form:options items="${batteryPackList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
								
			<td class="label">充电器：</td>
			<td colspan="5">
				<form:select path="productDesign.chargertype" style="width: 100px;">							
					<form:options items="${chargerTypeList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
				
		</tr>
		</c:if>
	</table>
</fieldset>	
		
<div style="clear: both"></div>		
<div class="action" style="text-align: right;margin: 10px;">
	<button type="button" id="doSave" class="DTTT_button" >保存</button>
	<button type="button" id="goBack" class="DTTT_button">返回</button>
</div>

<div id="tabs" style="padding: 0px;white-space: nowrap;margin: 10px;">
	<ul>
		<li><a href="#tabs-1" class="tabs1">产品信息</a></li>
		<li><a href="#tabs-2" class="tabs2">标贴及文字印刷</a></li>
		<li><a href="#tabs-3" class="tabs3">包装</a></li>
	</ul>
	
	<div id="tabs-1" style="padding: 5px;">
		<c:if test="${type eq '010'}"><!-- 机器配置 -->
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
					<c:forEach var="list" items="${machineConfigList}" varStatus="status">
						<tr>
							<td></td>
							<td><form:input path="machineConfigList[${status.index}].componentname"  class="short" value="${list.componentName }" /></td>
							<td><form:input path="machineConfigList[${status.index}].materialid"  class="materialid" value="${list.materialId }" /></td>
							<td><span>${list.materialName }</span></td>
							<td>
								<form:select path="machineConfigList[${status.index}].purchaser" style="width: 100px;">							
									<form:options items="${purchaserList}" 
										itemValue="key" itemLabel="value" /></form:select>
							</td>
							<td><form:input path="machineConfigList[${status.index}].remark"  class="middle" value="${list.remark }" /></td>
						</tr>
						<script type="text/javascript">
							var purchaser="${list.purchaserId }" ;
							var index ="${status.index}";
							$("#machineConfigList"+index+"\\.purchaser").val(purchaser);
							//$("#machineConfigList"+index+"\\.purchaser").find("option[value='"+purchaser+"']").attr("selected",true);
							
						</script>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</fieldset>
		</c:if>
		<fieldset>
			<span class="tablename">产品图片</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
			<div class="list">
				<div class="showPhotoDiv" style="overflow: auto;">
					<table id="productPhoto" style="width:100%;height:335px">
						<tbody><tr><td class="photo"></td></tr></tbody>
					</table>
				</div>
			</div>	
		</fieldset>
		<c:if test="${type eq '010' || type eq '020'}">
		<fieldset>
			<legend style="">塑料制品</legend>
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
		</c:if>
		
		<fieldset>
			<legend>产品收纳-描述</legend>
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
			<div style="margin-top: 5px;">
			<span class="tablename">产品收纳-图片</span>&nbsp;<button type="button" id="addStoragePhoto" class="DTTT_button">添加图片</button>
			</div>
			<div class="list">
				<div class="showPhotoDiv" style="overflow: auto;">
				<table id="storagePhoto" style="width:100%;height:335px">
					<tbody><tr><td class="photo"></td></tr></tbody>
				</table>
				</div>
			</div>	
		</fieldset>
	</div>
	<div id="tabs-2" style="padding: 5px;">
		<fieldset>
			<legend style="">标贴</legend>
			<div class="list">
				<table id="labelT" class="display">
					<thead>				
						<tr style="text-align: left;">
							<th width="1px">No</th>
							<th style="width:60px">名称</th>
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
			</div><br/>
			<span class="tablename">标贴-图片</span>&nbsp;<button type="button" id="addLabelPhoto" class="DTTT_button">添加图片</button>
			<div class="list">
				<div class="" id="subidDiv" style="overflow: auto;">
					<table id="labelPhoto" style="width:100%;height:300px">
						<tbody><tr class="photo"><td></td></tr></tbody>
					</table>
				</div>
			</div>				
		</fieldset>
		<fieldset>
			<legend style="">文字印刷</legend>
			<div class="list">
			<table id="textPrint" class="display">
				<thead>				
					<tr style="text-align: left;">
						<th width="1px">No</th>
						<th style="width:60px">名称</th>
						<th style="width:40px">材质</th>
						<th style="width:60px">尺寸</th>
						<th style="width:60px">颜色</th>
						<th style="width:60px">文件名</th>
						<th style="width:60px">文件上传</th>
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
							<td><div id="fileName${i }"></div>
								<form:hidden path="textPrintList[${i}].filename" /></td>
							<td>
								<div id="uploadItem${i }" >
									<input name="pdfFile" id="pdfFile" onchange="uploadFileFn('${i}')" type="file" size="30"  style="width:60px;border:0px"/>
								</div>
								<div id="deleteItem${i }" style="display:none">					
									<!-- <a href="###" onclick="downloadFile('${i}')">下载</a>&nbsp; -->
									<a href="###" onclick="deleteTextPrintFile('${i}')">删除</a>	
								</div>
							</td> 
							
						</tr>
					
					</c:forEach>
				</tbody>				
			</table>
			</div>
		</fieldset>
	</div>
	<div id="tabs-3" style="padding: 5px;">
		<fieldset>
			<legend style="">包装描述</legend>
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
			<br/>
			<span class="tablename">包装描述-图片</span>&nbsp;<button type="button" id="addPackagePhoto" class="DTTT_button">添加图片</button>
			<div class="list">
				<div class="showPhotoDiv" style="overflow: auto;">
					<table id="packagePhoto" style="width:100%;height:335px">
						<tbody><tr><td class="photo"></td><td></tbody>
					</table>
				</div>
			</div>	
		</fieldset>
</div>
</div>

</form:form>
</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	
	$( "#tabs" ).tabs();

	machineConfigurationView();//机器配置
	plasticView();//塑料制品
	accessoryView();//配件清单
	labelView();//标贴
	textPrintView();//文字印刷
	packageView();//包装描述	
	
	setPlasticName();//塑料制品默认项
	setLableName();//标贴
	setTextPrintName();//文字印刷
	setPackageName();//包装描述

	productPhotoView();//产品图片
	productStoragePhotoView();//产品收纳图片
	packagePhotoView();//包装图片
	labelPhotoView();//标贴图片	

	var i = 0;
	<c:forEach var="purchaser" items="${purchaserList}">
		i++;
		purchaserOptions += '<option value="${purchaser.key}">${purchaser.value}</option>';
	</c:forEach>
	//alert(purchaserOptions)
	
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
	
	//横向滚动条宽度计算
	var maxW = $("#container").width();
	var DivWidth = maxW -80 + "px";
	$(".showPhotoDiv").css("width",DivWidth);
	
	//产品图片添加位置,                                                                                                                                                                                        
	var productIndex = 1;
	$("#addProductPhoto").click(function() {
		
		var cols = $("#productPhoto tbody td.photo").length - 1;

		//从 1 开始
		var trHtml = addPhotoRow('productPhoto','uploadProductPhoto',productIndex);		

		$('#productPhoto td.photo:eq('+0+')').after(trHtml);	
		productIndex++;		
		//alert("row:"+row+"-----"+"::productIndex:"+productIndex)	
						
		
	});
	
	//产品收纳图片添加位置,
	var storageIndex = 1;
	$("#addStoragePhoto").click(function() {
		
		var row = $("#storagePhoto tbody td.photo").length - 1;

		var trHtml = addPhotoRow('storagePhoto','uploadStoragePhoto',storageIndex);		

		$('#storagePhoto td.photo:eq('+0+')').after(trHtml);	
		storageIndex++;
						
		
	});
	
	//包装图片添加位置,
	var packageIndex = 1;
	$("#addPackagePhoto").click(function() {
		
		var row = $("#packagePhoto tbody td.photo").length - 1;

		var trHtml = addPhotoRow('packagePhoto','uploadPackagePhoto',packageIndex);	
		
		$('#packagePhoto td.photo:eq('+0+')').after(trHtml);	
		packageIndex++;	
		
	});
	
	//标贴图片添加位置,
	var labelIndex = 0;
	$("#addLabelPhoto").click(function() {
		
		var row = $("#labelPhoto tbody tr.photo").length - 1;

		var trHtml = addPhotoRowLabel('labelPhoto','uploadLabelPhoto',labelIndex);		

		$('#labelPhoto tr.photo:eq('+row+')').after(trHtml);	
		labelIndex++;
						
		
	});
    	
});
</script>
<script type="text/javascript">

function productPhotoView() {

	var productDetailId = $("#productDetailId").val();
	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();

	$.ajax({
		"url" :"${ctx}/business/productDesign?methodtype=getProductPhoto&productDetailId="+productDetailId+"&YSId="+YSId+"&productId="+productId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["productFileCount"];
			//alert(countData)
			photoView('productPhoto','uploadProductPhoto',countData,data['productFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
            }
	});
	
}//产品图片


function productStoragePhotoView() {

	var productDetailId = $("#productDetailId").val();
	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();

	$.ajax({
		"url" :"${ctx}/business/productDesign?methodtype=getProductStoragePhoto&productDetailId="+productDetailId+"&YSId="+YSId+"&productId="+productId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["storageFileCount"];
			//alert(countData)
			photoView('storagePhoto','uploadStoragePhoto',countData,data['storageFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
            }
	});
	
}//产品收纳图片


function labelPhotoView() {

	var productDetailId = $("#productDetailId").val();
	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();

	$.ajax({
		"url" :"${ctx}/business/productDesign?methodtype=getLabelPhoto&productDetailId="+productDetailId+"&YSId="+YSId+"&productId="+productId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){

			var countData = data["labelFileCount"];
			photoViewLabel('labelPhoto','uploadLabelPhoto',countData,data['labelFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
            }
	});
	
}//标贴图片

function packagePhotoView() {

	var productDetailId = $("#productDetailId").val();
	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();

	$.ajax({
		"url" :"${ctx}/business/productDesign?methodtype=getPackagePhoto&productDetailId="+productDetailId+"&YSId="+YSId+"&productId="+productId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){

			var countData = data["packageFileCount"];
			photoView('packagePhoto','uploadPackagePhoto',countData,data['packageFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
            }
	});
	
}//包装图片

</script>
<script type="text/javascript">


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
	
	$("#plastic input").addClass('bgnone').addClass('bsolid');
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

</script>

<script type="text/javascript">

function photoView(id, tdTable, count, data) {
	
	//alert("id:"+id+"--count:"+count+"--countView:"+countView)	
	var row = 0;
	for (var index = 0; index < count; index++) {

		var path = '${ctx}' + data[index];
		var pathDel = data[index];
		//alert(index+"::::::::::::"+path)
		var trHtml = '';

		//trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td class="photo" style="text-align:center;padding: 10px;">';
		trHtml += '<table style="width:400px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td></td>';
		trHtml += '<td width="50px"><a id="uploadFile' + index + '" href="###" '+
				'onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\''+ ',' + '\'' + pathDel + '\'' + ');">删除</a></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;">';
		trHtml += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
		//trHtml += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage2(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
		trHtml += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
		trHtml += '</a>';
		trHtml += '</td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
/*
		index++;
		if (index == count) {
			//因为是偶数循环,所以奇数张图片的最后一张为空

			var trHtmlOdd = '<table style="width:400px;margin: auto;" class="">';
			trHtmlOdd += '<tr>';
			trHtmlOdd += '<td></td>';			
			trHtmlOdd += '<td width="50px"></td>';			
			trHtmlOdd += "</tr>";
			trHtmlOdd += '<tr><td colspan="2"  style="height:300px;"></td>';
		} else {
			path = '${ctx}' + data[index];
			pathDel = data[index];

			var trHtmlOdd = '<table style="width:400px;margin: auto;" class="form">';
			trHtmlOdd += '<tr style="background: #d4d0d0;height: 35px;">';
			trHtmlOdd += '<td></td>';			
			trHtmlOdd += '<td width="50px">';
			trHtmlOdd += '<a id="uploadFile1' + index + '" href="###" onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\''+ tdTable + '\'' + ','+ '\'' + pathDel + '\'' + ');">删除';
			trHtmlOdd += '</a>';
			trHtmlOdd += '</td>';
			trHtmlOdd += '</tr>';
			trHtmlOdd += '<tr>';
			trHtmlOdd += '<td colspan="2"  style="height:300px;">';
			trHtmlOdd += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage2(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
			trHtmlOdd += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
			trHtmlOdd += '</a>'
			trHtmlOdd += '</td>';
		}

		trHtml += '<td>';
		//trHtml += '<table style="width:400px;margin: auto;" class="form">';
		//trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += trHtmlOdd;
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		trHtml += "</tr>";
*/
		$('#' + id + ' td.photo:eq(' + row + ')').after(trHtml);
		row++;

	}

}


function photoViewLabel(id, tdTable, count, data) {
	
	//alert("id:"+id+"--count:"+count+"--countView:"+countView)	
	var row = 0;
	for (var index = 0; index < count; index++) {

		var path = '${ctx}' + data[index];
		var pathDel = data[index];
		//alert(index+"::::::::::::"+path)
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';

		trHtml += '<td>';
		trHtml += '<table style="width:100%;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td></td>';
		trHtml += '<td width="50px"><a id="uploadFile' + index + '" href="###" '+
				'onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\''+ ',' + '\'' + pathDel + '\'' + ');">删除</a></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;">'
		trHtml += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
		//trHtml += '<a id=linkFile'+tdTable+index+'" href="###" onclick="bigImage2(' + '\'' + tdTable + '\'' + ',' + '\''+ index + '\'' + ','+ '\'' + path + '\'' + ');">';
		trHtml += '<img id="imgFile'+tdTable+index+'" src="'+path+'"  style="max-height: 450px;" />';
		trHtml += '</a>';
		trHtml += '</td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		trHtml += "</tr>";

		$('#' + id + ' tr.photo:eq(' + row + ')').after(trHtml);
		row++;

	}

}

function addPhotoRowLabel(id,tdTable, index) {

	for (var i = 0; i < 1; i++) {

		var path = '${ctx}' + "/images/blankDemo.png";
		var pathDel = '';
		var trHtml = '';

		trHtml += '<tr style="text-align: center;" class="photo">';

		trHtml += '<td>';
		trHtml += '<table style="width:100%;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td><div id="uploadFile'+tdTable+index+'" ><input type="file"  id="photoFile" name="photoFile" '+
				'onchange="uploadPhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\'' + ',' + index + ');" accept="image/*" style="max-width: 250px;" /></div></td>';
		//trHtml+='<td><div id="uploadFile'+index+'" ></div></td>';
		trHtml += '<td width="50px"><div id="deleteFile'+tdTable+index+'"><a href="###" '+
				'onclick=\"deletePhoto(' + '\'' + id + '\'' + ','+ '\''+ tdTable + '\'' + ',' + '\'' + pathDel+ '\''+ ')\">删除</a></div></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;"><img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  /></td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		trHtml += "</tr>";
	
	}//for		

	return trHtml;
}

function addPhotoRow(id,tdTable, index) {
	
	for (var i = 0; i < 1; i++) {

		var path = '${ctx}' + "/images/blankDemo.png";
		var pathDel = '';
		var trHtml = '';

		//trHtml += '<tr style="text-align: center;" class="photo">';
		trHtml += '<td class="photo" style="text-align:center;padding: 10px;">';
		trHtml += '<table style="width:400px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td><div id="uploadFile'+tdTable+index+'" ><input type="file"  id="photoFile" name="photoFile" '+
				'onchange="uploadPhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\'' + ',' + index + ');" accept="image/*" style="max-width: 250px;" /></div></td>';
		trHtml += '<td width="50px"><div id="deleteFile'+tdTable+index+'" ><a href="###" '+
				'onclick=\"deletePhoto(' + '\'' + id + '\'' + ','+ '\''+ tdTable + '\'' + ',' + '\'' + pathDel+ '\''+ ')\">删除</a></div></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;text-align: center;""><img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  /></td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		/*
		index++;

		trHtml += '<td>';
		trHtml += '<table style="width:400px;margin: auto;" class="">';
		trHtml += '<tr style="height: 35px;">';
		trHtml += '<td></td>';
		trHtml += '<td width="50px"></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;"></td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		trHtml += "</tr>";	
		*/
		
	}//for		

	return trHtml;
}
function uploadFileFn(rowNo) {
	
	var componentName = $("#textPrintList"+rowNo+"\\.componentname").val();
	if(componentName ==""){
		$().toastmessage('showWarningToast', "请先输入名称。");
		return 
	}
	var url = '${ctx}/business/productDesignFileUpload?methodtype=uploadTextPrintFile';
	url =encodeURI(encodeURI(url));//中文两次转码
	$("#form").ajaxSubmit({
		type : "POST",
		url : url,
		data : $('#form').serialize(),// 你的formid
		dataType : 'json',
		success : function(data) {
			if (data.result == '0') {
				var fileName = data.fileName;
				//alert("fileName:"+fileName)
				
				var shortName = jQuery.fixedWidth(fileName,20)
				$("#fileName"+rowNo).html(shortName);
				$("#textPrintList"+rowNo+"\\.filename").val(fileName);
				
				$("input[name='pdfFile']").each(function(){
					var file = $(this);
					file.after(file.clone().val(""));
					file.remove(); 
					$("#uploadItem"+rowNo).attr("style","display:none");
					$("#deleteItem"+rowNo).attr("style","display:block");					
				});

			} else {
				alert(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error:"+errorThrown)
		}
	});
}

function downloadFile(rowNo) {

	var YSId = $("#productDesign\\.ysid").val();
	var productId = $("#productDesign\\.productid").val();
	var fileName = $("#textPrintList"+rowNo+"\\.filename").val();
	var url = '${ctx}/business/productDesign?methodtype=downloadFile&fileName='
			+ fileName + "&productId=" + productId + "&YSId=" + YSId;
	url =encodeURI(encodeURI(url));//中文两次转码

	location.href = url;
}

function deleteTextPrintFile(rowNo){
	
	var componentName = $("#textPrintList"+rowNo+"\\.componentname").val();
	var fileName = $("#textPrintList"+rowNo+"\\.filename").val();
	
	if(confirm("确定要删除文件 [ "+componentName+" ] 吗？")){
		
		var url = '${ctx}/business/productDesign?methodtype=textPrintFileDelete'+'&fileName='+fileName;
		url =encodeURI(encodeURI(url));//中文两次转码

		$("#form").ajaxSubmit({
			type : "POST",
			url : url,
			data : $('#form').serialize(),// 你的formid
			dataType : 'json',
			success : function(data) {
				if (data.message == "操作成功") {
					$().toastmessage('showWarningToast', "文件已被删除。");
					$("#fileName"+rowNo).html("");
					$("#textPrintList"+rowNo+"\\.filename").val("");
					$("#uploadItem"+rowNo).attr("style","display:block");
					$("#deleteItem"+rowNo).attr("style","display:none");
					
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("error:"+errorThrown)
			}

		});
	}
	
}

function doShowProduct() {
	var materialId = '${product.productId}';
	callProductView(materialId);
}

function deletePhoto(tableId,tdTable,path) {
	
	var url = '${ctx}/business/productDesign?methodtype='+tableId+'Delete';
	url+='&tabelId='+tableId+"&path="+path;
	    
	if(!(confirm("确定要删除该图片吗？"))){
		return;
	}
    $("#form").ajaxSubmit({
		type: "POST",
		url:url,	
		data:$('#form').serialize(),// 你的formid
		dataType: 'json',
	    success: function(data){
	    	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
				case "storagePhoto":
					countData = data["storageFileCount"];
					photo = data['storageFileList'];
					break;
				case "labelPhoto":
					countData = data["labelFileCount"];
					photo = data['labelFileList']
					flg = "false";
					break;
				case "packagePhoto":
					countData = data["packageFileCount"];
					photo = data['packageFileList']
					break;
				default:
					break;
			}
			
			//删除后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}else{
				photoViewLabel(tableId, tdTable, countData, photo);
				
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error:"+errorThrown)
		}
	});
}

function uploadPhoto(tableId,tdTable, id) {

	var url = '${ctx}/business/productDesignPhotoUpload'
			+ '?methodtype=' + tdTable + '&id=' + id;
	//alert(url)
	$("#form").ajaxSubmit({
		type : "POST",
		url : url,
		data : $('#form').serialize(),// 你的formid
		dataType : 'json',
		success : function(data) {
	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
				case "storagePhoto":
					countData = data["storageFileCount"];
					photo = data['storageFileList'];
					break;
				case "labelPhoto":
					countData = data["labelFileCount"];
					photo = data['labelFileList']
					flg = "false";
					break;
				case "packagePhoto":
					countData = data["packageFileCount"];
					photo = data['packageFileList']
					break;
				default:
					break;
			}
			
			//添加后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}else{
				photoViewLabel(tableId, tdTable, countData, photo);
				
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error:"+errorThrown)
		}
	});
}

</script>
</body>
</html>
