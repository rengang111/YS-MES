<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>物料的核算成本-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter1  = 0;
	var counter5  = 0;

	$.fn.dataTable.TableTools.buttons.add_rows1 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				//var rowIndex = $("#documentary tbody tr").length -1 ;
				var rowIndex = counter1  ;
				var trHtml = "";
				
				//rowIndex++;
				var rownum = rowIndex+1;
				var checkbox = "<input type=checkbox name='numCheck' id='numCheck' value='" + rowIndex + "' />";
				trHtml+="<tr>";	
				trHtml+='<td class="td-left">'+ rownum + checkbox +'</td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines1['+rowIndex+'].costname"      id="documentaryLines1'+rowIndex+'.costname" class="costname middle" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines1['+rowIndex+'].quantity"      id="documentaryLines1'+rowIndex+'.quantity" class="num mini" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines1['+rowIndex+'].price"         id="documentaryLines1'+rowIndex+'.price" class="cash mini" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines1['+rowIndex+'].cost"          id="documentaryLines1'+rowIndex+'.cost" class="cash read-only" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines1['+rowIndex+'].remarks" id="documentaryLines1'+rowIndex+'.remarks" class="" /></td>';
				trHtml+="</tr>";	

				$('#documentary tbody tr:last').after(trHtml);
				if(counter1 == 0){
					$('#documentary tbody tr:eq(0)').hide();//删除无效行
				}
				counter1 += 1;//记录费用总行数
				//alert(counter1+'::counter1')
					
				foucsInit();
				
			}
		});
	
	$.fn.dataTable.TableTools.buttons.reset1 = $.extend(true, {},
			$.fn.dataTable.TableTools.buttonBase, {
			"fnClick" : function(button) {
				
				var t=$('#documentary').DataTable();
				
				rowIndex = t.row('.selected').index();
				
				var str = true;
				$("input[name='numCheck']").each(function(){
					if ($(this).prop('checked')) {
						var n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
						$('#documentary tbody').find("tr:eq("+n+")").remove();
						str = false;
					}
				});
				
				if(str){
					$().toastmessage('showWarningToast', "请选择要 删除 的数据。");
					return;
				}else{
					$().toastmessage('showWarningToast', "删除后,请保存");					
				}

				var rowCont = true;
				$("input[name='numCheck']").each(function(){
					rowCont = false;
				});
				
				if(rowCont == true){
					$('#documentary tbody tr:eq(0)').show();//显示无效行
					counter1 = 0;
				
				}
			}
		});
	
	$.fn.dataTable.TableTools.buttons.save1 = $.extend(true, {},
			$.fn.dataTable.TableTools.buttonBase, {
			"fnClick" : function(button) {
				
				doSave('M');
			}
		});
	
	$.fn.dataTable.TableTools.buttons.return1 = $.extend(true, {},
			$.fn.dataTable.TableTools.buttonBase, {
			"fnClick" : function(button) {
				
				doBack();
			}
		});
	
	$.fn.dataTable.TableTools.buttons.save5 = $.extend(true, {},
			$.fn.dataTable.TableTools.buttonBase, {
			"fnClick" : function(button) {
				
				doSave('P');
			}
		});
	
	$.fn.dataTable.TableTools.buttons.return5 = $.extend(true, {},
			$.fn.dataTable.TableTools.buttonBase, {
			"fnClick" : function(button) {
				
				doBack();
			}
		});
		
	
	
	$.fn.dataTable.TableTools.buttons.add_rows5 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = counter5  ;
				var trHtml = "";
				
				//rowIndex++;
				var rownum = rowIndex+1;
				var checkbox = "<input type=checkbox name='numCheck5' id='numCheck5' value='" + rowIndex + "' />";
				trHtml+="<tr>";	
				trHtml+='<td class="td-center">'+ rownum + checkbox +'</td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines5['+rowIndex+'].costname"      id="documentaryLines5'+rowIndex+'.costname" class="costname long" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines5['+rowIndex+'].cost"          id="documentaryLines5'+rowIndex+'.cost" class="cash short" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines5['+rowIndex+'].remarks"       id="documentaryLines5'+rowIndex+'.remarks" class="" /></td>';
				trHtml+="</tr>";	

				$('#inspection tbody tr:last').after(trHtml);
				if(counter5 == 0){
					$('#inspection tbody tr:eq(0)').hide();//删除无效行
				}
				counter5 += 1;//记录费用总行数
				
				foucsInit();
			}
		});

	$.fn.dataTable.TableTools.buttons.reset5 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {
			
			var t=$('#inspection').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			var str = true;
			$("input[name='numCheck5']").each(function(){
				if ($(this).prop('checked')) {
					var n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
					$('#inspection tbody').find("tr:eq("+n+")").remove();
					str = false;
				}
			});
			
			if(str){
				$().toastmessage('showWarningToast', "请选择要 删除 的数据。");
				return;
			}else{
				$().toastmessage('showWarningToast', "删除后,请保存");					
			}	
			var rowCont = true;
			$("input[name='numCheck5']").each(function(){
				rowCont = false;
			});
			
			if(rowCont == true){
				$('#inspection tbody tr:eq(0)').show();//显示无效行
				counter5 = 0;
			
			}					
		}
	});
	
	
	function documentaryAjax() {//材料成本

		var table = $('#documentary').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var materialId = '${material.materialId}';
		var actionUrl = "${ctx}/business/material?methodtype=getMaterialCostList&type=M&materialId="+materialId;

		var t = $('#documentary').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
			"async"		: false,
	        "ordering"  : false,
			"sAjaxSource" : actionUrl,
			dom : 'T<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : JSON.stringify($('#material').serializeArray()),// 要提交的表单
					success : function(data) {

						counter1 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);

						foucsInit();

						costComputer();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_rows1",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset1",
					"sButtonText" : "删除行"
				},
				{
					"sExtends" : "save1",
					"sButtonText" : "保存"
				},
				/*{
					"sExtends" : "return1",
					"sButtonText" : "返回"
				}*/],
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-left'},
			    {"data": "costName", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "quantity", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "price", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "remarks", "defaultContent" : '',"className" : 'td-center'},
			    				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
					var checkbox = "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />";
	    			
	    			return rownum + checkbox;
	    			
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    				rtnVal = "<input type='text' name='documentaryLines1["+ rownum +"].costname' id='documentaryLines1"+ rownum +".costname' value='" + data + "' class='middle' />"
	    			
	    			return rtnVal;
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	       			rtnVal = "<input type='text' name='documentaryLines1["+ rownum +"].quantity'   id='documentaryLines1"+ rownum +".quantity'  value='" + data + "' class='cash' />"
	    					    			
	    			return rtnVal;
                }},
	    		{"targets":3,"render":function(data, type, row){
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	       			rtnVal = "<input type='text' name='documentaryLines1["+ rownum +"].price'   id='documentaryLines1"+ rownum +".price'  value='" + data + "' class='cash' />"
    			
	    			return rtnVal;
                }},
	    		{"targets":4,"render":function(data, type, row){
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	       			rtnVal = "<input type='text' name='documentaryLines1["+ rownum +"].cost'   id='documentaryLines1"+ rownum +".cost'  value='" + data + "' class='cash read-only' />"
    			
	    			return rtnVal;
                }},
	    		{"targets":5,"render":function(data, type, row){
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			rtnVal = "<input type='text' name='documentaryLines1["+ rownum +"].remarks' id='documentaryLines1"+ rownum +".remarks' value='" + data + "' class='middle' />"
	    			
	    			return rtnVal;
                }}            
		           
		     ] ,			
		})

		
		t.on('change', 'tr td:nth-child(3),tr td:nth-child(4)',function() {			
			var $tds = $(this).parent().find("td");
			var $quantity = $tds.eq(2).find("input");//用量	
			var $price = $tds.eq(3).find("input");//单价
			var $cost = $tds.eq(4).find("input");//合计
			
			var quantity = currencyToFloat($quantity.val());
			var price = currencyToFloat($price.val());
			var cost = quantity * price;
			
			$quantity.val(floatToCurrency(quantity));
			$price.val(floatToCurrency(price));
			$cost.val(floatToCurrency(cost));
			
			costComputer();
		});
		
	};//材料成本
	
	function costComputer(){
		
		var materailCost = 0;	
		$('#documentary tbody tr').each (function (){
			var contractValue = $(this).find("td").eq(4).find("input").val();//
			contractValue= currencyToFloat(contractValue);
			materailCost = materailCost + contractValue;
		});	
		
		var processCost = 0;	
		$('#inspection tbody tr').each (function (){
			var contractValue = $(this).find("td").eq(2).find("input").val();//
			contractValue= currencyToFloat(contractValue);
			processCost = processCost + contractValue;
		});	
		
		var cost = materailCost + processCost;
	//	alert("materailCost:"+materailCost+" processCost:"+processCost+" cost:"+cost)
		$('#materailCost').text(floatToCurrency(materailCost));
		$('#processCost').text(floatToCurrency(processCost));
		$('#costView').text(floatToCurrency(cost));
		$('#cost').val(floatToCurrency(cost));
				
	}
	
	$(document).ready(function() {
		
		documentaryAjax();	//材料成本
		expenseAjax5();		//加工描述
		

		$(".goBack").click(
				function() {
							
				});

		
		$("input:text").focus (function(){
		    $(this).select();
		});	
		

		foucsInit();
	});

</script>

<script type="text/javascript">
	function doBack(){
		var url = "${ctx}/business/order?methodtype=orderExpenseInit";
		location.href = url;
	}
</script>


<script type="text/javascript">

function expenseAjax5() {//加工描述

	var table = $('#inspection').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var materialId = '${material.materialId}';
		var actionUrl = "${ctx}/business/material?methodtype=getProcessCostList&type=P&materialId="+materialId;

		var t = $('#inspection').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
			"async"		: false,
	        "ordering"  : false,
			"sAjaxSource" : actionUrl,
			dom : 'T<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : JSON.stringify($('#material').serializeArray()),// 要提交的表单
					success : function(data) {

						counter5 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);

						costComputer();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
			"tableTools" : {

				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

				"aButtons" : [ {
					"sExtends" : "add_rows5",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset5",
					"sButtonText" : "删除行"
				},
				{
					"sExtends" : "save5",
					"sButtonText" : "保存"
				},
				/*
				{
					"sExtends" : "return5",
					"sButtonText" : "返回"
				}*/],
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-center'},
			    {"data": "costName", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "remarks", "defaultContent" : '',"className" : 'td-center'},
				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){		

	    			var rownum = row["rownum"];
					var checkbox = "<input type=checkbox name='numCheck5' id='numCheck5' value='" + row["recordId"] + "' />";
	    			
	    			return rownum + checkbox;
	    			
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			rtnVal = "<input type='text' name='documentaryLines5["+ rownum +"].costname'   id='documentaryLines5"+ rownum +".costname'  value='" + data + "' class='long' />"
	    			
	    			return rtnVal;
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			rtnVal = "<input type='text' name='documentaryLines5["+ rownum +"].cost'   id='documentaryLines5"+ rownum +".cost'  value='" + data + "' class='cash short' />"
	    				    			
	    			return rtnVal;
                }},
	    		{"targets":3,"render":function(data, type, row){
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			rtnVal = "<input type='text' name='documentaryLines5["+ rownum +"].remarks'   id='documentaryLines5"+ rownum +".remarks'  value='" + data + "' class='' />"	    				
	    			
	    			return rtnVal;
                }}          
		           
		     ] ,
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(3)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(2).find("input");//金额			
			$cost.val(floatToCurrency($cost.val()));
									
			costComputer();			
			
		});

};//检验费用


function doSave(type) {

	var materialId = '${material.materialId}';
	var actionUrl = "${ctx}/business/material?methodtype=insertMaterialCost&type="+type;
	actionUrl = actionUrl + "&materialId="+materialId;

	$('#counter1').val(counter1);
	$('#counter5').val(counter5);
	//alert(counter1+':::counter1'+counter5+':::counter5')
	//return ;
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		dataType : 'text',
		url : actionUrl,
		data : JSON.stringify($('#material').serializeArray()),// 要提交的表单
		success : function(d) {
			//alert(d)
			switch(type){
				case "M":
					documentaryAjax();	//材料成本
					break;
				case "P":
					expenseAjax5();		//加工描述
					break;				
			}			
			
			$().toastmessage('showWarningToast', "保存成功!");		
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {				
			//alert(textStatus);
		}
	});

}

</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="material" method="POST" 
		id="material" name="material"   autocomplete="off">
		
	<input type="hidden" id="counter1" name="counter1" />
	<input type="hidden" id="counter5" name="counter5"/>
	
	<fieldset style="margin-top: -20px;">
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" style="width:100px;"><label>物料编号：</label></td>					
				<td style="width:120px;">${material.materialId}
			
				<td class="label" style="width:100px;"><label>核算成本：</label></td>					
				<td  style="width:100px;">
					<span id="costView">${material.materialCost}</span>
					<input type="hidden" id="cost" name="cost" value="${material.materialCost}"/></td>
				
				<td class="label" style="width:100px;"><label>物料名称：</label></td>				
				<td>${material.materialName}</td>
			</tr>						
		</table>
	</fieldset>
	
	<fieldset style="margin-top: -20px;">
		<span class="tablename"> 材料成本</span>
		<div class="list">
			<table id="documentary" class="display" >
				<thead>				
					<tr>
						<th width="10px">No</th>
						<th class="dt-center" width="300px">材料名称</th>
						<th class="dt-center" width="100px">材质用量</th>
						<th class="dt-center" width="100px">材质单价</th>
						<th class="dt-center" width="100px">总价</th>
						<th class="dt-center" >备注</th>
					</tr>
				</thead>
				<tfoot>			
					<tr>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
						<td style="text-align: right;"><div id="materailCost"></div></td>
						<td></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</fieldset>	
	<fieldset>
		<span class="tablename"> 加工描述</span>
		<div class="list">
			<table id="inspection" class="display" >
				<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="300px">描述</th>
						<th class="dt-center" width="150px">成本</th>
						<th class="dt-center">备注</th>
					</tr>
				</thead>
				<tfoot>			
					<tr>
						<th></th>
						<th></th>
						<th style="text-align: right;"><div id="processCost"></div></th>
						<th></th>
					</tr>
				</tfoot>
			</table>
					
		</div>
	</fieldset>	
		
</form:form>

</div>
</div>

</body>
	
</html>
