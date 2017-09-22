<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
				trHtml+='<td class="td-right"><input type="text" name="documentaryLines1['+rowIndex+'].cost"          id="documentaryLines1'+rowIndex+'.cost" class="cash" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines1['+rowIndex+'].person"        id="documentaryLines1'+rowIndex+'.person" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines1['+rowIndex+'].quotationdate" id="documentaryLines1'+rowIndex+'.quotationdate" class="quotationdate read-only" style="text-align: center;" /></td>';
				trHtml+='<td class="td-center"></td>',
				trHtml+="</tr>";	

				$('#documentary tbody tr:last').after(trHtml);
				
				if(counter1 == 0){
					//$('#documentary tbody tr:eq(0)').remove();//删除无效行
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
				}else{
					$().toastmessage('showWarningToast', "删除后,请保存");					
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
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines2['+rowIndex+'].costname"      id="documentaryLines1'+rowIndex+'.costname" class="costname middle" /></td>';
				trHtml+='<td class="td-right"><input type="text"  name="documentaryLines2['+rowIndex+'].cost"          id="documentaryLines1'+rowIndex+'.cost" class="cash" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines2['+rowIndex+'].person"        id="documentaryLines1'+rowIndex+'.person" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines2['+rowIndex+'].quotationdate" id="documentaryLines1'+rowIndex+'.quotationdate" class="quotationdate read-only" style="text-align: center;" /></td>';
				trHtml+='<td class="td-center"></td>',
				trHtml+="</tr>";	

				$('#custmer tbody tr:last').after(trHtml);

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
			}else{
				$().toastmessage('showWarningToast', "删除后,请保存");					
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
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines3['+rowIndex+'].contractid"    id="documentaryLines3'+rowIndex+'.contractid" class="" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines3['+rowIndex+'].costname"      id="documentaryLines3'+rowIndex+'.costname" class=" middle" /></td>';
				trHtml+='<td class="td-right"><input type="text"  name="documentaryLines3['+rowIndex+'].cost"          id="documentaryLines3'+rowIndex+'.cost" class="cash" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines3['+rowIndex+'].person"        id="documentaryLines3'+rowIndex+'.person" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines3['+rowIndex+'].quotationdate" id="documentaryLines3'+rowIndex+'.quotationdate" class="quotationdate read-only" style="text-align: center;" /></td>';
				trHtml+='<td class="td-center"></td>',
				trHtml+="</tr>";	

				$('#supplier tbody tr:last').after(trHtml);

				counter3 += 1;//记录费用总行数

				$(".quotationdate").val(shortToday());
				
				foucsInit();
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
			}else{
				$().toastmessage('showWarningToast', "删除后,请保存");					
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
				trHtml+='<td class="td-right"> <input type="text" name="documentaryLines4['+rowIndex+'].cost"          id="documentaryLines4'+rowIndex+'.cost" class="cash" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines4['+rowIndex+'].person"        id="documentaryLines4'+rowIndex+'.person" /></td>';
				trHtml+='<td class="td-center"><input type="text" name="documentaryLines4['+rowIndex+'].quotationdate" id="documentaryLines4'+rowIndex+'.quotationdate" class="quotationdate read-only" style="text-align: center;" /></td>';
				trHtml+='<td class="td-center"></td>',
				trHtml+="</tr>";	

				$('#workshop tbody tr:last').after(trHtml);

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
			}else{
				$().toastmessage('showWarningToast', "删除后,请保存");					
			}						
		}
	});
	
	
	
	function documentaryAjax() {

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
			    {"data": null, "defaultContent" : '',"className" : 'td-center'},
			    {"data": null, "defaultContent" : '',"className" : 'td-right'},
			    {"data": null, "defaultContent" : '',"className" : 'td-center'},
			    {"data": "quotationDate", "defaultContent" : '',"className" : 'td-center'},
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
	    			var costName = row["costName"];
	    			var recordHidden = "<input type='hidden' name='documentaryLines1["+ rownum +"].recordid'   id='documentaryLines1"+ rownum +".recordid'  value='" + row["recordId"] + "' />";
	    			var nameHidden   = "<input type='hidden' name='documentaryLines1["+ rownum +"].costname'   id='documentaryLines1"+ rownum +".costname'  value='" + costName + "' class='cash' />";
	    			
	    			if (status != "1"){//可以修改
	    				return costName +recordHidden+nameHidden;
	    			}else{
	    				return costName;
	    			}
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var cost = row["cost"];
	    			if (status != "1"){//可以修改
	    				return "<input type='text' name='documentaryLines1["+ rownum +"].cost'   id='documentaryLines1"+ rownum +".cost'  value='" + cost + "' class='cash' />"
	    			}else{
	    				return fomatToColor(cost)+'&nbsp;';
	    			}
                }},
	    		{"targets":3,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var person = row["person"];
	    			if (status != "1"){//可以修改
	    				return "<input type='text' name='documentaryLines1["+ rownum +"].person' id='documentaryLines1"+ rownum +".person' value='" + person + "' />"
	    			}else{
	    				return person;
	    			}
                }},
	    		{"targets":4,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var date = row["quotationDate"];
	    			if (status != "1"){//可以修改
	    				return "<input type='text' name='documentaryLines1["+ rownum +"].quotationdate' id='documentaryLines1"+ rownum +".quotationdate' value='" + date + "' class='read-only' style='text-align: center;'/>"
	    			}else{
	    				return date;
	    			}
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
		})

		
		t.on('change', 'tr td:nth-child(3)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(2).find("input");//金额	
			$cost.val(floatToCurrency($cost.val()));
			
		});
		
			/*
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
				var num   = i + 1;
				var checkBox = "<input type=checkbox name='numCheck' id='numCheck' value='" + num + "' />";
				cell.innerHTML = num + checkBox;
			});
		}).draw();
*/
	};//ajax()


	
	$(document).ready(function() {

		$("#bomPlan\\.plandate").val(shortToday());
		$("#bomPlan\\.plandate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});	
		
		documentaryAjax();//
		expenseAjax2();//
		expenseAjax3();//
		expenseAjax4();//
		
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

function expenseAjax2() {

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
			    {"data": null, "defaultContent" : '',"className" : 'td-center'},
			    {"data": null, "defaultContent" : '',"className" : 'td-right'},
			    {"data": null, "defaultContent" : '',"className" : 'td-center'},
			    {"data": "quotationDate", "defaultContent" : '',"className" : 'td-center'},
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
	    			var costName = row["costName"];
	    			
	    			return costName;
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var cost = row["cost"];
	    			if (status != "1"){//可以修改
	    				return "<input type='text' name='documentaryLines2["+ rownum +"].cost'   id='documentaryLines1"+ rownum +".cost'  value='" + cost + "' class='cash' />"
	    			}else{
	    				return cost+'&nbsp;';
	    			}
                }},
	    		{"targets":3,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var person = row["person"];
	    			if (status != "1"){//可以修改
	    				return "<input type='text' name='documentaryLines2["+ rownum +"].person' id='documentaryLines1"+ rownum +".person' value='" + person + "' />"
	    			}else{
	    				return person;
	    			}
                }},
	    		{"targets":4,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var date = row["quotationDate"];
	    			if (status != "1"){//可以修改
	    				return "<input type='text' name='documentaryLines2["+ rownum +"].quotationdate' id='documentaryLines1"+ rownum +".quotationdate' value='" + date + "' class='read-only' style='text-align: center;'/>"
	    			}else{
	    				return date;
	    			}
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
			
		})

		
		t.on('change', 'tr td:nth-child(3)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(2).find("input");//金额			
			$cost.val(floatToCurrency($cost.val()));
			
			
		});

};//ajax()

</script>

<script type="text/javascript">

function expenseAjax3() {

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
			    {"data": "contractId", "defaultContent" : '',"className" : 'td-center'},
			    {"data": null, "defaultContent" : '',"className" : 'td-right'},
			    {"data": null, "defaultContent" : '',"className" : 'td-right'},
			    {"data": "quotationDate", "defaultContent" : '',"className" : 'td-center'},
			    {"data": null, "className" : 'td-center'},
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
	    		{"targets":3,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var person = row["person"];
	    			if (status != "1"){//可以修改
	    				return "<input type='text' name='documentaryLines3["+ rownum +"].person' id='documentaryLines1"+ rownum +".person' value='" + person + "' />"
	    			}else{
	    				return person;
	    			}
                }},
	    		{"targets":4,"render":function(data, type, row){
	    			var status = row["status"];
	    			var rownum = row["rownum"] - 1;
	    			var date = row["quotationDate"];
	    			if (status != "1"){//可以修改
	    				return "<input type='text' name='documentaryLines3["+ rownum +"].quotationdate' id='documentaryLines1"+ rownum +".quotationdate' value='" + date + "' class='read-only' style='text-align: center;'/>"
	    			}else{
	    				return date;
	    			}
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
			
		})

		
		t.on('change', 'tr td:nth-child(3)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(2).find("input");//金额			
			$cost.val(floatToCurrency($cost.val()));
		});
	
};//ajax()

</script>

<script type="text/javascript">

function expenseAjax4() {
	var table = $('#workshop').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var YSId = '${order.YSId}';
	var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=W&YSId="+YSId;

	var t = $('#workshop').DataTable({
		
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

					counter4 = data['recordsTotal'];//记录总件数
					
					fnCallback(data);
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
		    {"data": null, "defaultContent" : '',"className" : 'td-center'},
		    {"data": null, "defaultContent" : '',"className" : 'td-right'},
		    {"data": null, "defaultContent" : '',"className" : 'td-center'},
		    {"data": "quotationDate", "defaultContent" : '',"className" : 'td-center'},
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
    			var cost = row["cost"];
    			if (status != "1"){//可以修改
    				return "<input type='text' name='documentaryLines4["+ rownum +"].cost'   id='documentaryLines1"+ rownum +".cost'  value='" + cost + "' class='cash' />"
    			}else{
    				return cost+'&nbsp;';
    			}
            }},
    		{"targets":3,"render":function(data, type, row){
    			var status = row["status"];
    			var rownum = row["rownum"] - 1;
    			var person = row["person"];
    			if (status != "1"){//可以修改
    				return "<input type='text' name='documentaryLines4["+ rownum +"].person' id='documentaryLines1"+ rownum +".person' value='" + person + "' />"
    			}else{
    				return person;
    			}
            }},
    		{"targets":4,"render":function(data, type, row){
    			var status = row["status"];
    			var rownum = row["rownum"] - 1;
    			var date = row["quotationDate"];
    			if (status != "1"){//可以修改
    				return "<input type='text' name='documentaryLines4["+ rownum +"].quotationdate' id='documentaryLines1"+ rownum +".quotationdate' value='" + date + "' class='read-only' style='text-align: center;'/>"
    			}else{
    				return date;
    			}
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
		
	})

	
	t.on('change', 'tr td:nth-child(3)',function() {
		
		var $tds = $(this).parent().find("td");
		var $cost = $tds.eq(2).find("input");//金额			
		$cost.val(floatToCurrency($cost.val()));
		
		
	});

};//ajax()

</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="bomForm" method="POST"
		id="bomForm" name="bomForm"  autocomplete="off">
		
		<form:hidden path="counter" />
		<form:hidden path="counter1" />
		<form:hidden path="counter2" />
		<form:hidden path="counter3" />
		<form:hidden path="counter4" />
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:120px;">${order.YSId}
						<form:hidden path="bomPlan.ysid" value="${order.YSId}"/></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:120px;">${order.materialId}
						<form:hidden path="bomPlan.materialid" value="${order.materialId}"/></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td>${order.materialName}</td>
				</tr>
				<tr>
					<td class="label"><label>ＰＩ编号：</label></td>
					<td>${order.PIId}</td>

					<td class="label"><label>订单数量：</label></td>
					<td><span id="quantity">${order.quantity}</span>（${order.unit}）</td>
						
					<td class="label"><label>客户名称：</label></td>
					<td>${order.customerFullName}</td>
				</tr>							
			</table>
		</fieldset>

			
		<fieldset>
			<legend> 车间增减费用</legend>
			<div class="list">
				<table id="workshop" class="display" >
					<thead>				
					<tr>
						<th width="30px">No</th>
						<th class="dt-center" width="250px">增减内容</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" width="150px">供应商</th>
						<th class="dt-center" width="150px">供应商金额</th>
						<th></th>
					</tr>
					</thead>
					<tfoot><tr><th></th><th></th><th></th><th></th><th></th><th></th></tr></tfoot>
				</table>
				<div class="action" style="text-align: right;">
					<button type="button" id="insert4" onclick="doSave('W');" class="DTTT_button">保存</button>
					<button type="button" id="goBack4" class="goBack DTTT_button">返回</button>
				</div>	
			</div>
		</fieldset>	
				
		<fieldset>
			<legend> 工厂（供应商）增减费用</legend>
			<div class="list">
				<table id="supplier" class="display" >
					<thead>				
					<tr>
						<th width="30px">No</th>
						<th class="dt-center" width="150px">工厂合同号</th>
						<th class="dt-center" width="150px">工厂名称</th>
						<th class="dt-center" width="250px">增减内容</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" width="150px">日期</th>
					</tr>
					</thead>					
					<tfoot><tr><th></th><th></th><th></th><th></th><th></th><th></th></tr></tfoot>
				</table>
				<div class="action" style="text-align: right;">
					<button type="button" id="insert3" onclick="doSave('S');" class="DTTT_button">保存</button>
					<button type="button" id="goBack3" class="goBack DTTT_button">返回</button>
				</div>	
			</div>
		</fieldset>			
		
		<fieldset>
			<legend> 客户增减</legend>
			<div class="list">
				<table id="custmer" class="display" >
					<thead>				
					<tr>
						<th width="30px">No</th>
						<th class="dt-center" width="250px">费用名称</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" width="150px">报销人</th>
						<th class="dt-center" width="150px">日期</th>
						<th></th>
					</tr>
					</thead>
					
					<tfoot><tr><th></th><th></th><th></th><th></th><th></th><th></th></tr></tfoot>
				</table>
				<div class="action" style="text-align: right;">
					<button type="button" id="insert2" onclick="doSave('C');" class="DTTT_button">保存</button>
					<button type="button" id="goBack2" class=" goBack DTTT_button">返回</button>
				</div>	
			</div>
		</fieldset>	
		
		<fieldset>
			<legend> 跟单费用</legend>
			<div class="list">
				<table id="documentary" class="display" >
					<thead>				
					<tr>
						<th width="30px">No</th>
						<th class="dt-center" width="250px">费用名称</th>
						<th class="dt-center" width="150px">金额</th>
						<th class="dt-center" width="150px">报销人</th>
						<th class="dt-center" width="150px">报销日期</th>
						<th></th>
					</tr>
					</thead>
					<tfoot><tr><th></th><th></th><th></th><th></th><th></th><th></th></tr></tfoot>
				</table>
						
				<div class="action" style="text-align: right;">
					<button type="button" id="insert1" onclick="doSave('D');" class="DTTT_button">保存</button>
					<!-- button type="button" id="check1" onclick="doCheck1();" class="DTTT_button">确认</button-->
					<button type="button" id="goBack" class="goBack DTTT_button">返回</button>
				</div>	
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
}
</script>
<script type="text/javascript">


function doSave(type) {

	var actionUrl = "${ctx}/business/bom?methodtype=insertOrderCost1&type="+type;

	$('#counter1').val(counter1);
	$('#counter2').val(counter2);
	$('#counter3').val(counter3);
	$('#counter4').val(counter4);
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
			documentaryAjax();
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
