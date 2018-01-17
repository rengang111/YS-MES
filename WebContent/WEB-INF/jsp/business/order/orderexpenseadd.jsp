<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>订单过程-开销录入</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter1  = 0;
	var counter2  = 0;
	var counter3  = 0;
	var counter4  = 0;
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
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines1['+rowIndex+'].cost"          id="documentaryLines1'+rowIndex+'.cost" class="cash" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines1['+rowIndex+'].remarks" id="documentaryLines1'+rowIndex+'.remarks" class="middle" /></td>';
				trHtml+='<td class="td-center"></td>',
				trHtml+="</tr>";	

				$('#documentary tbody tr:last').after(trHtml);
				if(counter1 == 0){
					$('#documentary tbody tr:eq(0)').hide();//删除无效行
				}
				counter1 += 1;//记录费用总行数
				//alert(counter1+'::counter1')
				autocomplete();
					
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
	
	$.fn.dataTable.TableTools.buttons.add_rows2 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = counter2  ;
				var trHtml = "";
				
				//rowIndex++;
				var rownum = rowIndex+1;
				var checkbox = "<input type=checkbox name='numCheck2' id='numCheck2' value='" + rowIndex + "' />";
				trHtml+="<tr>";	
				trHtml+='<td class="td-left">'+ rownum + checkbox +'</td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines2['+rowIndex+'].costname"      id="documentaryLines2'+rowIndex+'.costname" class="costname middle" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines2['+rowIndex+'].cost"          id="documentaryLines2'+rowIndex+'.cost" class="cash" /></td>';
			  //trHtml+='<td class="td-center"><input type="text" name="documentaryLines2['+rowIndex+'].person"        id="documentaryLines2'+rowIndex+'.person" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines2['+rowIndex+'].remarks"        id="documentaryLines2'+rowIndex+'.remaks" class="middle" /></td>';
				trHtml+='<td class="td-center"></td>',
				trHtml+="</tr>";	

				$('#custmer tbody tr:last').after(trHtml);
				if(counter2 == 0){
					$('#custmer tbody tr:eq(0)').hide();//删除无效行
				}
				counter2 += 1;//记录费用总行数

				$(".quotationdate").val(shortToday());
				
				foucsInit();
			}
		});

	$.fn.dataTable.TableTools.buttons.reset2 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {
			
			var t=$('#custmer').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			var str = true;
			$("input[name='numCheck2']").each(function(){
				if ($(this).prop('checked')) {
					var n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
					$('#custmer tbody').find("tr:eq("+n+")").remove();
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
			$("input[name='numCheck2']").each(function(){
				rowCont = false;
			});
			
			if(rowCont == true){
				$('#custmer tbody tr:eq(0)').show();//显示无效行
				counter2 = 0;
			
			}	
		}
	});
	
	$.fn.dataTable.TableTools.buttons.add_rows3 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = counter3  ;
				var trHtml = "";
				
				//rowIndex++;
				var rownum = rowIndex+1;
				var checkbox = "<input type=checkbox name='numCheck3' id='numCheck3' value='" + rowIndex + "' />";
				trHtml+="<tr>";	
				trHtml+='<td class="td-left">'+ rownum + checkbox +'</td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines3['+rowIndex+'].supplierid"    id="documentaryLines3'+rowIndex+'.supplierid" class="short supplierid" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines3['+rowIndex+'].contractid"    id="documentaryLines3'+rowIndex+'.contractid" class="" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines3['+rowIndex+'].costname"      id="documentaryLines3'+rowIndex+'.costname" class=" middle" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines3['+rowIndex+'].cost"          id="documentaryLines3'+rowIndex+'.cost" class="cash short" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines3['+rowIndex+'].remarks"       id="documentaryLines3'+rowIndex+'.remarks" /></td>';
				trHtml+='<td class="td-center"></td>',
				trHtml+="</tr>";	

				$('#supplier tbody tr:last').after(trHtml);
				if(counter3 == 0){
					$('#supplier tbody tr:eq(0)').hide();//删除无效行
				}
				counter3 += 1;//记录费用总行数

				// $(".quotationdate").val(shortToday());
				
				foucsInit();
				
				autocomplete();
			}
		});

	$.fn.dataTable.TableTools.buttons.reset3 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {
			
			var t=$('#supplier').DataTable();
			
			rowIndex = t.row('.selected').index();
	
			var str = true;
			$("input[name='numCheck3']").each(function(){
				if ($(this).prop('checked')) {
					var n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
					$('#supplier tbody').find("tr:eq("+n+")").remove();
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
			$("input[name='numCheck3']").each(function(){
				rowCont = false;
			});
			
			if(rowCont == true){
				$('#supplier tbody tr:eq(0)').show();//显示无效行
				counter3 = 0;
			
			}			
		}
	});
	
	$.fn.dataTable.TableTools.buttons.add_rows4 = $
	.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = counter4  ;
				var trHtml = "";
				
				//rowIndex++;
				var rownum = rowIndex+1;
				var checkbox = "<input type=checkbox name='numCheck4' id='numCheck4' value='" + rowIndex + "' />";
				trHtml+="<tr>";	
				trHtml+='<td class="td-left">'+ rownum + checkbox +'</td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines4['+rowIndex+'].costname"      id="documentaryLines4'+rowIndex+'.costname" class="costname middle" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines4['+rowIndex+'].cost"          id="documentaryLines4'+rowIndex+'.cost" class="cash" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines4['+rowIndex+'].person"        id="documentaryLines4'+rowIndex+'.person" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines4['+rowIndex+'].cost"          id="documentaryLines4'+rowIndex+'.cost" class="cash short"  /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines4['+rowIndex+'].remarks"       id="documentaryLines4'+rowIndex+'.remarks" class="" /></td>';
				trHtml+='<td class="td-center"></td>',
				trHtml+="</tr>";	

				$('#workshop tbody tr:last').after(trHtml);
				if(counter4 == 0){
					$('#workshop tbody tr:eq(0)').hide();//删除无效行
				}
				counter4 += 1;//记录费用总行数

				$(".quotationdate").val(shortToday());
				
				foucsInit();
			}
		});

	$.fn.dataTable.TableTools.buttons.reset4 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {
			
			var t=$('#workshop').DataTable();
			
			rowIndex = t.row('.selected').index();
			var str = true;
			$("input[name='numCheck4']").each(function(){
				if ($(this).prop('checked')) {
					var n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
					$('#workshop tbody').find("tr:eq("+n+")").remove();
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
			$("input[name='numCheck4']").each(function(){
				rowCont = false;
			});
			
			if(rowCont == true){
				$('#workshop tbody tr:eq(0)').show();//显示无效行
				counter4 = 0;
			}
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
				trHtml+='<td class="td-left">'+ rownum + checkbox +'</td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines5['+rowIndex+'].costname"      id="documentaryLines5'+rowIndex+'.costname" class="costname middle" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines5['+rowIndex+'].cost"          id="documentaryLines5'+rowIndex+'.cost" class="cash" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines5['+rowIndex+'].person"        id="documentaryLines5'+rowIndex+'.person" class="short"/></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines5['+rowIndex+'].remarks"       id="documentaryLines5'+rowIndex+'.remarks" class="middle" /></td>';
				trHtml+='<td class="td-center"></td>',
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
	
	
	function documentaryAjax() {//跟单费用

		var table = $('#documentary').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${order.YSId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=D&YSId="+YSId;

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
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {

						counter1 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);
						$(".DTTT_container").css('float','left');
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
				}],
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-left'},
			    {"data": "costName", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "remarks", "defaultContent" : '',"className" : 'td-center'},
			    {"data": null, "className" : 'td-center'},
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){		
					var status = row["status"];
	    			var rownum = row["rownum"];
					var checkbox = "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />";
	    			var cost = row["cost"];
	    			if (status != "1"){//可以修改
	    				return rownum + checkbox;
	    			}else{
	    				return rownum;
	    			}
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines1["+ rownum +"].costname' id='documentaryLines1"+ rownum +".costname' value='" + data + "' />"
	    			}else{
	    				rtnVal = "<input type='text' name='documentaryLines1["+ rownum +"].costname' id='documentaryLines1"+ rownum +".costname' value='" + data + "' class='middle' />"
	    			}
	    			return rtnVal;
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = fomatToColor(data) +  "<input type='hidden' name='documentaryLines1["+ rownum +"].cost'   id='documentaryLines1"+ rownum +".cost'  value='" + data + "' class='cash' />"
	    			}else{
	       				rtnVal = "<input type='text' name='documentaryLines1["+ rownum +"].cost'   id='documentaryLines1"+ rownum +".cost'  value='" + data + "' class='cash' />"
	    				
	    			}
	    			return rtnVal;
                }},
	    		{"targets":3,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines1["+ rownum +"].remarks' id='documentaryLines1"+ rownum +".remarks' value='" + data + "' />"
	    			}else{
	    				rtnVal = "<input type='text' name='documentaryLines1["+ rownum +"].remarks' id='documentaryLines1"+ rownum +".remarks' value='" + data + "' class='middle' />"
	    			}
	    			return rtnVal;
                }},
	    		{"targets":4,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"];
	    			var rtn = "";
	    			if (status != "1"){//显示确认按钮
		    			//rtn= "<a href=\"#\" onClick=\"doConfirm ('" + row["recordId"] +"','"+ row["parentId"] + "')\">待确认</a>" ;
		    			rtn= "<span style='color: red'>待确认</span>" ;
	    			}else{
	    				rtn = "已确认";//不可修改
	    			}
	    			return rtn;
                }},               
		           
		     ] ,			
		})

		
		t.on('change', 'tr td:nth-child(3)',function() {			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(2).find("input");//金额	
			$cost.val(floatToCurrency($cost.val()));
			
		});
		
	};//跟单费用
	
	$(document).ready(function() {
		
		documentaryAjax();	//跟单费用
		expenseAjax2();		//客户增减费用
		expenseAjax3();		//工厂（供应商）增减费用
		expenseAjax4();		//车间增减费用
		expenseAjax5();		//检验费用
		
		autocomplete();//


		$(".goBack").click(
				function() {
					var url = "${ctx}/business/order?methodtype=expenseInit";
					location.href = url;		
				});

		
		$("input:text").focus (function(){
		    $(this).select();
		});
		
		$(".DTTT_container").css('float','left');
		
	});

</script>

<script type="text/javascript">

function expenseAjax2() {//客户增减费用

	var table = $('#custmer').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${order.YSId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=C&YSId="+YSId;

		var t = $('#custmer').DataTable({
			
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
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {

						counter2 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);
						$(".DTTT_container").css('float','left');
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
			"tableTools" : {

				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

				"aButtons" : [ {
					"sExtends" : "add_rows2",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset2",
					"sButtonText" : "删除行"
				}],
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-left'},
			    {"data": "costName", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "remarks", "defaultContent" : '',"className" : 'td-center'},
			   // {"data": "quotationDate", "defaultContent" : '',"className" : 'td-center'},
			    {"data": null, "className" : 'td-center'},
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){		
					var status = row["status"];
	    			var rownum = row["rownum"];
					var checkbox = "<input type=checkbox name='numCheck2' id='numCheck2' value='" + row["recordId"] + "' />";
	    			var cost = row["cost"];
	    			if (status != "1"){//可以修改
	    				return rownum + checkbox;
	    			}else{
	    				return rownum;
	    			}
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines2["+ rownum +"].costname'   id='documentaryLines2"+ rownum +".costname'  value='" + data + "' />"
	    			}else{
	    				//可以修改
	    				rtnVal = "<input type='text' name='documentaryLines2["+ rownum +"].costname'   id='documentaryLines2"+ rownum +".costname'  value='" + data + "' class='costname middle' />"
	    			}
	    			return rtnVal;
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines2["+ rownum +"].cost'   id='documentaryLines2"+ rownum +".cost'  value='" + data + "' />"
	    			}else{
	    				//可以修改
	    				rtnVal = "<input type='text' name='documentaryLines2["+ rownum +"].cost'   id='documentaryLines2"+ rownum +".cost'  value='" + data + "' class='cash' />"
	    			}
	    			return rtnVal;
                }},
	    		{"targets":3,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines2["+ rownum +"].remarks'   id='documentaryLines2"+ rownum +".remarks'  value='" + data + "' />"
	    			}else{
	    				//可以修改
	    				rtnVal = "<input type='text' name='documentaryLines2["+ rownum +"].remarks'   id='documentaryLines2"+ rownum +".remarks'  value='" + data + "' class='middle' />"
	    			}
	    			return rtnVal;
                }},
	    		{"targets":4,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"];
	    			var rtn = "";
	    			if (status != "1"){//显示确认按钮
		    			//rtn= "<a href=\"#\" onClick=\"doConfirm ('" + row["recordId"] +"','"+ row["parentId"] + "')\">待确认</a>" ;
		    			rtn= "<span style='color: red'>待确认</span>" ;
	    			}else{
	    				rtn = "已确认";//不可修改
	    			}
	    			return rtn;
                }},               
		           
		     ] ,
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(3)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(2).find("input");//金额			
			$cost.val(floatToCurrency($cost.val()));
			
			
		});

};//ajax()

</script>

<script type="text/javascript">

function expenseAjax3() {//工厂（供应商）增减费用

	var table = $('#supplier').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${order.YSId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=S&YSId="+YSId;

		var t = $('#supplier').DataTable({
			
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
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {

						counter3 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);
						$(".DTTT_container").css('float','left');
						
						autocomplete();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
			"tableTools" : {

				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

				"aButtons" : [ {
					"sExtends" : "add_rows3",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset3",
					"sButtonText" : "删除行"
				}],
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-left'},
			    {"data": "supplierId", "defaultContent" : '',"className" : 'td-center'},//供应商
			    {"data": "contractId", "defaultContent" : '',"className" : 'td-center'},//合同编号
			    {"data": "costName", "defaultContent" : '',"className" : 'td-center'},//增减内容3
			    {"data": "cost", "defaultContent" : '',"className" : 'td-center'},//金额4
			    {"data": "remarks", "defaultContent" : '',"className" : 'td-center'},//备注5
			    {"data": null, "className" : 'td-center'},//6
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){		
					var status = row["status"];
	    			var rownum = row["rownum"];
					var checkbox = "<input type=checkbox name='numCheck3' id='numCheck3' value='" + row["recordId"] + "' />";
	    			var cost = row["cost"];
	    			if (status != "1"){//可以修改
	    				return rownum + checkbox;
	    			}else{
	    				return rownum;
	    			}
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines3["+ rownum +"].supplierid' id='documentaryLines3"+ rownum +".supplierid' value='" + data + "'  />"
	    			}else{
	    				//可以修改
	    				rtnVal = "<input type='text' name='documentaryLines3["+ rownum +"].supplierid' id='documentaryLines3"+ rownum +".supplierid' value='" + data + "' class='supplierid short' />"			
	    			}
	    			return rtnVal;
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){//可以修改
	    				rtnVal = data +  "<input type='hidden' name='documentaryLines3["+ rownum +"].contractid' id='documentaryLines3"+ rownum +".contractid' value='" + data + "' />"
	    			}else{
	    				rtnVal = "<input type='text' name='documentaryLines3["+ rownum +"].contractid' id='documentaryLines3"+ rownum +".contractid' value='" + data + "' />"    				
	    			}
	    			return rtnVal;
                }},
	    		{"targets":3,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){//可以修改
	    				rtnVal = data +   "<input type='hidden' name='documentaryLines3["+ rownum +"].costname' id='documentaryLines3"+ rownum +".costname' value='" + data + "' />"
	    			}else{
	    				rtnVal =  "<input type='text' name='documentaryLines3["+ rownum +"].costname' id='documentaryLines3"+ rownum +".costname' value='" + data + "' class='middle' />"			
	    			}
	    			return rtnVal;
                }},
	    		{"targets":4,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){//可以修改
	    				rtnVal = data + "<input type='hidden' name='documentaryLines3["+ rownum +"].cost' id='documentaryLines3"+ rownum +".cost' value='" + data + "' class='cash short' />"
	    			}else{
	    				rtnVal = "<input type='text' name='documentaryLines3["+ rownum +"].cost' id='documentaryLines3"+ rownum +".cost' value='" + data + "' class='cash short' />"
	    			}
	    			return rtnVal;
                }},
	    		{"targets":5,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){//可以修改
	    				rtnVal = data + "<input type='hidden' name='documentaryLines3["+ rownum +"].remarks' id='documentaryLines3"+ rownum +".remarks' value='" + data + "' class='' />"
	    			}else{
	    				rtnVal = "<input type='text' name='documentaryLines3["+ rownum +"].remarks' id='documentaryLines3"+ rownum +".remarks' value='" + data + "' class='' />"
	    			}
	    			return rtnVal;
                }},
	    		{"targets":6,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"];
	    			var rtn = "";
	    			if (status != "1"){//显示确认按钮
		    			//rtn= "<a href=\"#\" onClick=\"doConfirm ('" + row["recordId"] +"','"+ row["parentId"] + "')\">待确认</a>" ;
		    			rtn= "<span style='color: red'>待确认</span>" ;
	    			}else{
	    				rtn = "已确认";//不可修改
	    			}
	    			return rtn;
                }},               
		           
		     ] ,
			
		})
		
		t.on('change', 'tr td:nth-child(5)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(4).find("input");//金额			
			$cost.val(floatToCurrency($cost.val()));
		});
	
};//供应商

</script>

<script type="text/javascript">

function expenseAjax4() {//车间增减费用
	var table = $('#workshop').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var YSId = '${order.YSId}';
	var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=W&YSId="+YSId;

	var t = $('#workshop').DataTable({
		
		"processing" : false,
		"retrieve"   : false,
		"stateSave"  : false,
		"serverSide" : false,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
		"async"		: false,
        "ordering"  : false,
		"sAjaxSource" : actionUrl,
		"zeroRecords":"",
		"dom" : 'T<"clear">lt',
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"type" : "POST",
				"contentType": "application/json; charset=utf-8",
				"dataType" : 'json',
				"url" : sSource,
				"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
				success : function(data) {

					counter4 = data['recordsTotal'];//记录总件数
					
					fnCallback(data);
					$(".DTTT_container").css('float','left');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus)
				}
			})
		},
		"tableTools" : {

			"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

			"aButtons" : [ {
				"sExtends" : "add_rows4",
				"sButtonText" : "追加新行"
			},
			{
				"sExtends" : "reset4",
				"sButtonText" : "删除行"
			}],
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
		
		"columns" : [
		    {"data": null,"className" : 'td-left'},
		    {"data": "costName", "defaultContent" : '',"className" : 'td-center'},
		    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},
		    {"data": null, "defaultContent" : '',"className" : 'td-center'},
		    {"data": "cost", "defaultContent" : '',"className" : 'td-center'},
		    {"data": "remarks", "defaultContent" : '',"className" : 'td-center'},
		    {"data": null, "className" : 'td-center'},
		],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){		
				var status = row["status"];
    			var rownum = row["rownum"];
				var checkbox = "<input type=checkbox name='numCheck4' id='numCheck4' value='" + row["recordId"] + "' />";
    			var cost = row["cost"];
    			if (status != "1"){//可以修改
    				return rownum + checkbox;
    			}else{
    				return rownum;
    			}
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var status = row["status"];
    			var rownum = row["rownum"] - 1;
    			var costName = row["costName"];
    			
    			return costName;
            }},
    		{"targets":2,"render":function(data, type, row){
    			var status = row["status"];
    			var rownum = row["rownum"] - 1;
    			var rtnVal = "";
    			if (status == "1"){//可以修改
    				return "<input type='text' name='documentaryLines4["+ rownum +"].cost'   id='documentaryLines4"+ rownum +".cost'  value='" + cost + "' class='cash' />"
    			}else{
    				return cost+'&nbsp;';
    			}
            }},
    		{"targets":3,"render":function(data, type, row){
    			var status = row["status"];
    			var rownum = row["rownum"] - 1;
    			var person = row["person"];
    			if (status != "1"){//可以修改
    				return "<input type='text' name='documentaryLines4["+ rownum +"].person' id='documentaryLines4"+ rownum +".person' value='" + person + "' />"
    			}else{
    				return person;
    			}
            }},
    		{"targets":4,"render":function(data, type, row){
    			var status = row["status"];
    			var rownum = row["rownum"] - 1;
    			var date = row["quotationDate"];
    			if (status != "1"){//可以修改
    				return "<input type='text' name='documentaryLines4["+ rownum +"].cost' id='documentaryLines4"+ rownum +".cost' value='" + cost + "' class='short cash' />"
    			}else{
    				return date;
    			}
            }},
    		{"targets":5,"render":function(data, type, row){
    			var status = row["status"];
    			var rownum = row["rownum"] - 1;
    			var date = row["remarks"];
    			if (status != "1"){//可以修改
    				return "<input type='text' name='documentaryLines4["+ rownum +"].remarks' id='documentaryLines4"+ rownum +".remarks' value='" + remarks + "' />"
    			}else{
    				return date;
    			}
            }},
    		{"targets":6,"render":function(data, type, row){
    			var status = row["status"];
    			var rownum = row["rownum"];
    			var rtn = "";
    			if (status != "1"){//显示确认按钮
	    			//rtn= "<a href=\"#\" onClick=\"doConfirm ('" + row["recordId"] +"','"+ row["parentId"] + "')\">待确认</a>" ;
	    			rtn= "<span style='color: red'>待确认</span>" ;
    			}else{
    				rtn = "已确认";//不可修改
    			}
    			return rtn;
            }},               
	           
	     ] ,
		
	}).draw();

	
	t.on('change', 'tr td:nth-child(3)',function() {
		
		var $tds = $(this).parent().find("td");
		var $cost = $tds.eq(2).find("input");//金额			
		$cost.val(floatToCurrency($cost.val()));
		
		
	});

};//ajax()

</script>

<script type="text/javascript">

function expenseAjax5() {//检验费用

	var table = $('#inspection').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${order.YSId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=E&YSId="+YSId;

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
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {

						counter5 = data['recordsTotal'];//记录总件数
						
						fnCallback(data);
						$(".DTTT_container").css('float','left');
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
				}],
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-left'},
			    {"data": "costName", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "cost", "defaultContent" : '',"className" : 'td-center'},
			    {"data": "person", "defaultContent" : '',"className" : 'td-center'},
			   	{"data": "remarks", "defaultContent" : '',"className" : 'td-center'},
			    {"data": null, "className" : 'td-center'},
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){		
					var status = row["status"];
	    			var rownum = row["rownum"];
					var checkbox = "<input type=checkbox name='numCheck5' id='numCheck5' value='" + row["recordId"] + "' />";
	    			if (status != "1"){//可以修改
	    				return rownum + checkbox;
	    			}else{
	    				return rownum;
	    			}
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines5["+ rownum +"].costname'   id='documentaryLines5"+ rownum +".costname'  value='" + data + "' />"
	    			}else{
	    				rtnVal = "<input type='text' name='documentaryLines5["+ rownum +"].costname'   id='documentaryLines5"+ rownum +".costname'  value='" + data + "' class='costname middle' />"
	    			}
	    			return rtnVal;
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines5["+ rownum +"].cost'   id='documentaryLines5"+ rownum +".cost'  value='" + data + "' />"
	    			}else{
	    				rtnVal = "<input type='text' name='documentaryLines5["+ rownum +"].cost'   id='documentaryLines5"+ rownum +".cost'  value='" + data + "' class='cash' />"
	    				
	    			}
	    			return rtnVal;
                }},
	    		{"targets":3,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines5["+ rownum +"].person'   id='documentaryLines5"+ rownum +".person'  value='" + data + "' />"
	    			}else{
	    				rtnVal = "<input type='text' name='documentaryLines5["+ rownum +"].person'   id='documentaryLines5"+ rownum +".person'  value='" + data + "' class='short' />"	    				
	    			}
	    			return rtnVal;
                }},
	    		{"targets":4,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var rtnVal = "";
	    			if (status == "1"){
	    				rtnVal = data + "<input type='hidden' name='documentaryLines5["+ rownum +"].remarks'   id='documentaryLines5"+ rownum +".remarks'  value='" + data + "' />"
	    			}else{
	    				rtnVal = "<input type='text' name='documentaryLines5["+ rownum +"].remarks'   id='documentaryLines5"+ rownum +".remarks'  value='" + data + "' class='middle' />"	    				
	    			}
	    			return rtnVal;
                }},
	    		{"targets":5,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"];
	    			var rtn = "";
	    			if (status != "1"){//显示确认按钮
		    			//rtn= "<a href=\"#\" onClick=\"doConfirm ('" + row["recordId"] +"','"+ row["parentId"] + "')\">待确认</a>" ;
		    			rtn= "<span style='color: red'>待确认</span>" ;
	    			}else{
	    				rtn = "已确认";//不可修改
	    			}
	    			return rtn;
                }},               
		           
		     ] ,
			
		}).draw();

		
		t.on('change', 'tr td:nth-child(3)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(2).find("input");//金额			
			$cost.val(floatToCurrency($cost.val()));
			
			
		});

};//检验费用

</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="bomForm" method="POST"
		id="bomForm" name="bomForm"  autocomplete="off">
		
		<form:hidden path="counter1" />
		<form:hidden path="counter2" />
		<form:hidden path="counter3" />
		<form:hidden path="counter4" />
		<form:hidden path="counter5" />
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:120px;">${order.YSId}
						<form:hidden path="bomPlan.ysid" value="${order.YSId}"/></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;">${order.materialId}
						<form:hidden path="bomPlan.materialid" value="${order.materialId}"/></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td colspan="3">${order.materialName}</td>
				</tr>
				<tr>
					<td class="label"><label>ＰＩ编号：</label></td>
					<td>${order.PIId}</td>

					<td class="label"><label>订单数量：</label></td>
					<td><span id="quantity">${order.quantity}</span>（${order.unit}）</td>
						
					<td class="label"><label>客户名称：</label></td>
					<td>${order.customerFullName}</td>
					<td class="label" style="width:100px;"><label>返还数量：</label></td>				
					<td><form:input path="orderDetail.returnquantity" value=""/></td>
				</tr>							
			</table>
		</fieldset>
		<fieldset style="margin-top: -20px;">
			<table class="form" id="table_form2">
				<tr>
					<th class="td-center"><label>车间增减费用</label></th>
					<th class="td-center"><label>供应商增减</label></th>
					<th class="td-center"><label> 客户增减</label></th>
					<th class="td-center"><label>检验费用</label></th>
					<th class="td-center"><label>跟单费用</label></th>
				</tr>
				<tr>
					<td class="td-center"><span id="materialCost"></span> </td>
					<td class="td-center"><span id="laborCost"></span> </td>
					<td class="td-center"><span id="bomCost"></span> </td>
					<td class="td-center"><span id="productCost"></span> </td>
					<td class="td-center"><span id="managementCostRate"></span></td>
				</tr>								
			</table>
		</fieldset>
			
		<fieldset>
			<legend> 车间增减费用</legend>
				<div class="action" style="text-align: right;width: 50%;float: right;margin-top: -30px;">
					<button type="button" id="insert4" onclick="doSave('W');" class="DTTT_button">保存</button>
					<button type="button" id="goBack4" class="goBack DTTT_button">返回</button>
				</div>
			<div class="list">	
				<table id="workshop" class="display" >
					<thead>				
					<tr>
						<th width="30px">No</th>
						<th class="dt-center" width="250px">增减内容</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" width="150px">供应商</th>
						<th class="dt-center" width="150px">供应商承担金额</th>
						<th class="dt-center" >备注</th>
						<th class="dt-center" width="50px"></th>
					</tr>
					</thead>
				</table>
			</div>
		</fieldset>	
		
		
		<fieldset>
			<legend> 工厂（供应商）增减费用</legend>
			<div class="action" style="text-align: right;width: 50%;float: right;margin-top: -30px;">
				<button type="button" id="insert3" onclick="doSave('S');" class="DTTT_button">保存</button>
				<button type="button" id="goBack3" class="goBack DTTT_button">返回</button>
			</div>	
			<div class="list">		
				<table id="supplier" class="display" >
					<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="150px">供应商</th>
						<th class="dt-center" width="150px">合同编号</th>
						<th class="dt-center" >增减内容</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center">备注</th>
						<th class="dt-center" width="50px"></th>
					</tr>
					</thead>					
				</table>
			</div>
		</fieldset>		
		
		<fieldset>
			<legend> 客户增减费用</legend>
				<div class="action" style="text-align: right;width: 50%;float: right;margin-top: -30px;">
					<button type="button" id="insert2" onclick="doSave('C');" class="DTTT_button">保存</button>
					<button type="button" id="goBack2" class=" goBack DTTT_button">返回</button>
				</div>	
			<div class="list">
				<table id="custmer" class="display" >
					<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="300px">增减内容</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" >备注</th>
						<th class="dt-center" width="50px"></th>
					</tr>
					</thead>
				</table>
			</div>
		</fieldset>	
		
		<fieldset>
			<legend> 检验费用</legend>
				<div class="action" style="text-align: right;width: 50%;float: right;margin-top: -30px;">
					<button type="button" id="insert1" onclick="doSave('E');" class="DTTT_button">保存</button>
					<button type="button" id="goBack" class="goBack DTTT_button">返回</button>
				</div>	
			<div class="list">
				<table id="inspection" class="display" >
					<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="300px">费用名称</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" width="100px">申报人</th>
						<th class="dt-center">备注</th>
						<th class="dt-center" width="50px"></th>
					</tr>
					</thead>
				</table>
						
			</div>
		</fieldset>	
		
		<fieldset>
			<legend> 跟单费用</legend>
						
				<div class="action" style="text-align: right;width: 50%;float: right;margin-top: -30px;">
					<button type="button" id="insert1" onclick="doSave('D');" class="DTTT_button">保存</button>
					<!-- button type="button" id="check1" onclick="doCheck1();" class="DTTT_button">确认</button-->
					<button type="button" id="goBack" class="goBack DTTT_button">返回</button>
				</div>	
			<div class="list">
				<table id="documentary" class="display" >
					<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="300px">费用名称</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" >备注</th>
						<th class="dt-center" width="50px"></th>
					</tr>
					</thead>
				</table>
			</div>
		</fieldset>	
		
<div style="clear: both"></div>		
</form:form>

</div>
</div>

<script type="text/javascript">

function autocomplete(){
	
	$(".quotationdate").val(shortToday());
	
	$(".costname").autocomplete({
		source : function(request, response) {
			$.ajax({
				type: "POST",
				url : "${ctx}/business/order?methodtype=documenterayNameSearch",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					response($.map(
						data.data,
						function(item) {
							return {
								label : item.costName,
								value : item.costName,
								id    : item.costName,							
							}
						}));
				},
				error : function(XMLHttpRequest,textStatus, errorThrown) {
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},
		
		minLength : 0,
		autoFocus : false,
	});

	//供应商选择
	$(".supplierid").autocomplete({
		
		source : function(request, response) {
			
			var ysid = $('#bomPlan\\.ysid').val();
			//alert(ysid)
			$.ajax({
				type : "POST",
				url : "${ctx}/business/contract?methodtype=getContract&YSId="+ysid,
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
								label : item.supplierId +" | " + item.supplierName,
								value : item.supplierId,
								id : item.supplierId,
								contractId : item.contractId,
								
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
			//合同编号
			$(this).parent().parent().find("td").eq(2)
				.find("input").val(ui.item.contractId);
		},

		minLength : 2,
		autoFocus : false,
		width: 300,
	});
}
</script>
<script type="text/javascript">


function doSave(type) {

	var actionUrl = "${ctx}/business/bom?methodtype=insertOrderCost1&type="+type;

	$('#counter1').val(counter1);
	$('#counter2').val(counter2);
	$('#counter3').val(counter3);
	$('#counter4').val(counter4);
	$('#counter5').val(counter5);
	//alert(counter1+':::counter1'+counter2+':::counter2::'+counter3+':::counter3::'+counter4+':::counter4')
	//return null;
	$.ajax({
		type : "POST",
		contentType : 'application/json',
		dataType : 'text',
		url : actionUrl,
		data : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
		success : function(d) {
			//alert(d)
			switch(type){
				case "C":
					expenseAjax2();		//客户增减费用
					break;
				case "W":
					expenseAjax4();		//车间增减费用
					break;
				case "S":
					expenseAjax3();		//工厂（供应商）增减费用
					break;
				case "D":
					documentaryAjax();	//跟单费用
					break;
				case "E":
					expenseAjax5();		//检验费用
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
</body>
	
</html>
