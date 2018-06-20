<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>超领申请-领料单</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
		
	function planAjax(scrollHeight) {

		var table = $('#example').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		
		var YSId= $('#requisition\\.ysid').val();
		
		var actionUrl = "${ctx}/business/requisition?methodtype=detailView";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&orderType=010";
			
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			"scrollY"    : scrollHeight,
	        "scrollCollapse": false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"sAjaxSource" : actionUrl,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				//var param = {};
				//var formData = $("#condition").serializeArray();
				//formData.forEach(function(e) {
				//	aoData.push({"name":e.name, "value":e.value});
				//});
 					
				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					//"data" : JSON.stringify(aoData),
					success: function(data){					
						fnCallback(data);
						
						checkBoxSelectFn();
						
						foucsInit();
						
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
						 alert("加载有误，请重试！")	
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns" : [
		        	{"data": null,"className":"dt-body-center"//0
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2
				}, {"data": "unitQuantity","className":"td-right"	//3
				}, {"data": "manufactureQuantity","className":"td-right"//4
				}, {"data": "totalRequisition","className":"td-right"//5
				}, {"data": "quantityOnHand","className":"td-right","defaultContent" : '0'	//6 可用库存
				}, {"data": null,"className":"td-right"		//7
				}, {"data": null,"className":"td-right","defaultContent" : '0'		//8
				}, {"data": "purchaseType","className":"td-right"		//9
				}, {"data": "supplierId","className":"td-right"		//10
				}, {"data":  "purchaseType","className":"td-right"		//11
				}
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row,meta){
                    var startIndex = meta.settings._iDisplayStart; 
                    var idx = startIndex + meta.row + 1 ;
					//return '<label for="numCheck'+idx+'">'+ idx +"</label>"+ "<input type='checkbox' class='numCheck1' name='numCheck' id='numCheck"+idx+"' value='" + row["materialId"] + "' />";
					return  idx + "<input type='checkbox' class='numCheck1' name='numCheck' id='numCheck"+idx+"' value='" + row["materialId"] + "' />";
	    		}},
	    		{"targets":2,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					var inputTxt =       '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value=""/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.subbomno" name="requisitionList['+index+'].subbomno" value="'+row["subBomNo"]+'"/>';
	    			
	    			return name + inputTxt;
                }},
	    		{"targets":4,"render":function(data, type, row){	    			
	    			
	    			var unit = row["unit"];	    			
	    			var index=row["rownum"]
	    			var qty = currencyToFloat(row["manufactureQuantity"]);
	    			var value = '0';
	    			//alert(unit)
	    			if(unit == '吨'){
	    				value = formatNumber( qty * 1000 );//转换成公斤
	    			}else{
	    				value = formatNumber(qty);
	    			}
	    								
	    			return value;				 
                }},
	    		{"targets":7,"render":function(data, type, row){	
	    			
					var index=row["rownum"];
					var totalRequ = currencyToFloat(row["totalRequisition"]);
					var qtyManuf = currencyToFloat(row["manufactureQuantity"]);
					var currValue = qtyManuf - totalRequ;
					if(currValue <= 0 ){
						currValue = 0
					}else{
						currValue = floatToCurrency(currValue);
					}
					var inputTxt = '<input type="text" id="requisitionList'+index+'.quantity" name="requisitionList['+index+'].quantity" class="quantity num mini"  value="0"/>';
				
					return inputTxt;
                }},
	    		{"targets":11,"render":function(data, type, row){	
	    			
					var index=row["rownum"];

					var inputTxt = '<input type="text" id="requisitionList'+index+'.scrapquantity" name="requisitionList['+index+'].scrapquantity" class="quantity num mini"  value=""/>';
				
					return inputTxt;
                }},
                {
					"visible" : false,
					"targets" : [5,8,9,10]
				}
			]
			
		}).draw();

		t.on('change', 'tr td:nth-child(7)',function() {

			var $td = $(this).parent().find("td");
			
			var $oCurrQty = $td.eq(6).find("input");//本次领料

			$oCurrQty.removeClass('error');
			$oCurrQty.val(floatToCurrency($.trim($oCurrQty.val())));

		});
		t.on('change', 'tr td:nth-child(8)',function() {

			var $td = $(this).parent().find("td");
			
			var $oCurrQty = $td.eq(7).find("input");//本次领料

			$oCurrQty.removeClass('error');
			$oCurrQty.val(floatToCurrency($.trim($oCurrQty.val())));

		});

	};
	
	$(document).ready(function() {
		
		//autocomplete();//
		//日期
		$("#requisition\\.requisitiondate").val(shortToday());

		//var scrollHeight =$(parent).height() - 200; 
		//planAjax(scrollHeight);
		
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/requisition?methodtype=excessInit";
					location.href = url;		
				});

		$("#showHistory").click(
				function() {
					var YSId='${order.YSId }';
					var url = "${ctx}/business/requisition?methodtype=getRequisitionHistoryInit&YSId="+YSId;
					location.href = url;		
		});
		
		$("#insert").click(function() {
				
			var str1 = '';
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					str1 += $(this).val();
				}
			});
			if (str1 == '') {
				$().toastmessage('showWarningToast', "请选择要领取的配件。");
				return;
			}
			var str = '';
			var val = '';
			var inputFlag=true;
			var scraFlag=true;
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					str += $(this).val() + ",";
					var excess = $(this).parent().parent().find('td').eq(6).find("input").val();
					var scra = $(this).parent().parent().find('td').eq(7).find("input").val();
					excess = currencyToFloat(excess);
					
					if(excess<=0){
						inputFlag = false;
						$(this).parent().parent().find('td').eq(6).find("input").addClass('error');
					}
					if(scra=="" || $.trim(scra) == ""){
						scraFlag = false;
						$(this).parent().parent().find('td').eq(7).find("input").addClass('error');
					}else{
						scra = currencyToFloat(scra);
						if(scra<=0){
							scraFlag = false;
							$(this).parent().parent().find('td').eq(7).find("input").addClass('error');
						}
					}		
					//val += qty;
				}else{
					//未选中物料的领料数量恢复为0
					$(this).parent().parent().find('td').eq(6).find("input").val('0');
					$(this).parent().parent().find('td').eq(7).find("input").val('');
				}
			});
						
			if(inputFlag == false){
				$().toastmessage('showWarningToast', "请输入领料数量。");
				return;				
			}
			
			if(scraFlag == false){
				$().toastmessage('showWarningToast', "请输入退还的报废物料数量。");
				return;				
			}
			
			$('#formModel').attr("action", "${ctx}/business/requisition?methodtype=excessAdd");
			$('#formModel').submit();
		});
		
		foucsInit();
		
		/*
		var table = $('#example').DataTable();
		// Event listener to the two range filtering inputs to redraw on input
	    $('#yz, #ty, #dg, #bz, #all, #ycl, #wll').click( function() {	    	
	    	 $('#selectedPurchaseType').val($(this).attr('id'));
    		 table.draw();
	    } );
		
	    //加载事件
        $(function () {
            var collection = $(".box");
            $.each(collection, function () {
                $(this).addClass("start");
            });
        });
        //单击事件
        $(".box").click(function () { 
            var collection = $(".box");
            $.each(collection, function () {
                $(this).removeClass("end");
                $(this).addClass("start");
            });
            $(this).removeClass("start");
            $(this).addClass("end");
        });
		*/
		
		
		
		
	});

	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<!-- 虚拟领料区分 -->
	<input type="hidden" id="virtualClass" value="${virtualClass }" />
	<input type="hidden" id="goBackFlag" />
	<input type="hidden" id="requrisitionFlag" value="0"/>
	<form:hidden path="requisition.excesstype"  value="020" /><!-- 超领物料 -->
	<fieldset>
		<legend> 领料单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td colspan="5"><form:input path="requisition.ysid" 
					class="middel" value="" />
					<span style="color: blue">（查询范围：耀升编号、产品编号、产品名称）</span></td>
									
			</tr>
			<tr>
				<td class="label">生产数量：</td>					
				<td width="150px">&nbsp;<span id="quantity"></span></td>
							
				<td class="label"width="100px">产品编号：</td>					
				<td width="100px"><span id="materialId"></span></td>
							
				<td class="label" width="100px">产品名称：</td>					
				<td><span id="materialName"></span></td>
			</tr>
			<tr> 				
				<td class="label" width="100px">超领原由：</td>					
				<td colspan="5">
					<form:textarea path="requisition.remarks" rows="2" cols="80" /></td>
									
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;margin-top: -15px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认领料</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>

	<fieldset style="margin-top: -15px;">
		<div class="list">
		<!--
			<div id="DTTT_container" align="left" style="height:40px;margin-right: 30px;width: 50%;margin: 5px 0px -10px 10px;">
				<a class="DTTT_button DTTT_button_text box" id="all" data-id="4">显示全部</a>
		 		<a class="DTTT_button DTTT_button_text box" id="wll" data-id="5">未领物料</a>
	 			<a class="DTTT_button DTTT_button_text box" id="yz" data-id="0">自制品</a>
	 			<a class="DTTT_button DTTT_button_text box" id="dg" data-id="1">订购件</a>
				<a class="DTTT_button DTTT_button_text box" id="ty" data-id="2">装配品</a>
				<a class="DTTT_button DTTT_button_text box" id="bz" data-id="3">包装品</a>
				<input type="hidden" id="selectedPurchaseType" />
			</div>
			 -->
			<table id="example" class="display" >
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>				
						<th width="60px">基本用量</th>
						<th width="60px">计划用量</th>
						<th width="60px">已领数量</th>
						<th width="60px">当前库存</th>
						<th width="80px">超领数量</th>
						<!-- 
								<input type="checkbox" name="selectall" id="selectall" onclick="fnselectall()"/><label for="selectall">全选</label><br>
								<input type="checkbox" name="reverse" id="reverse" onclick="fnreverse()" /><label for="reverse">反选</label> -->
						<th width="60px">剩余数量</th>
						<th width="1px"></th>
						<th width="1px"></th>
						<th width="60px">退货数</th>
					</tr>
				</thead>	
			</table>
		</div>
	</fieldset>
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function fnselectall() { 
	if($("#selectall").prop("checked")){
		$("input[name='numCheck']").each(function() {
			$(this).prop("checked", true);
			$(this).parent().parent().addClass("selected");
		});
			
	}else{
		$("input[name='numCheck']").each(function() {
			if($(this).prop("checked")){
				$(this).removeAttr("checked");
				$(this).parent().parent().removeClass('selected');
			}
		});
	}
};

function fnreverse() { 
	$("input[name='numCheck']").each(function () {  
        $(this).prop("checked", !$(this).prop("checked"));  
		$(this).parent().parent().toggleClass("selected");
    });
};




</script>

<script type="text/javascript">


	
$("#requisition\\.ysid").autocomplete({

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
							name 		: item.materialName,
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

		$("#materialId").html(ui.item.materialId);
		$("#materialName").html(ui.item.name);
		$("#quantity").html(ui.item.quantity);
		$('#requisition\\.ysid').val(ui.item.value);
		
		var scrollHeight =$(parent).height() - 200;
		planAjax(scrollHeight);
		

	},//select
	
	
	minLength : 5,
	autoFocus : false,
});


</script>
<script type="text/javascript">
   function checkBoxSelectFn(){
	   /*
	   //input 单击事件  
       $(".numCheck1").click(function () {  
           //获取checkbox选中项  
           if ($(this).prop("checked") == true) {  
               $(this).parent().parent().css("background", "#b2dba1");  
           } else {  
               $(this).parent().parent().css("background", "");  
           }  
       });  
	   */
   
	   
   }
   $('#example').DataTable().on('click', 'tr td:nth-child(1)', function() {
		//alert(1111)
		$(this).parent().toggleClass("selected");
	    /*
		if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
	        
	    	//$(this).children().first().children().prop("checked", true);
	    	$(this).children().prop("checked", true);
	    }else{//如果没有被选中

	    	$(this).children().first().children().prop("checked", false);
	    }	
	    */
	    var checkbox  = $(this).find("input[type='checkbox']");
	    var isChecked = checkbox.is(":checked");
	    
	    //alert(isChecked)
	    if (isChecked) {
	        checkbox.prop("checked", false)
	        checkbox.removeAttr("checked");
	    } else {
	        checkbox.prop("checked", true)
	        checkbox.attr("checked","true");
	    }
	});	
	   
   $('#example').DataTable().on('click', 'tr td:nth-child(2),tr td:nth-child(3),tr td:nth-child(4),tr td:nth-child(5),tr td:nth-child(6)', function() {
		//alert(1111)
		$(this).parent().toggleClass("selected");
	    /*
		if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
	        
	    	//$(this).children().first().children().prop("checked", true);
	    	$(this).children().prop("checked", true);
	    }else{//如果没有被选中

	    	$(this).children().first().children().prop("checked", false);
	    }	
	    */
	    var checkbox  = $(this).parent().children().first().find("input[type='checkbox']");
	    var isChecked = checkbox.is(":checked");
	    
	    //alert(isChecked)
	    if (isChecked) {
	        checkbox.prop("checked", false)
	        checkbox.removeAttr("checked");
	    } else {
	        checkbox.prop("checked", true)
	        checkbox.attr("checked","true");
	    }
	});	

</script> 

</html>
