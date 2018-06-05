<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>自制件领料申请-领料单申请</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	function ajax(scrollHeight) {

		var makeType = $('#makeType').val();
		var YSId= $('#requisition\\.ysid').val();
		var actionUrl = "${ctx}/business/requisitionzz?methodtype=getRawMaterialList";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&makeType="+makeType;
		
		scrollHeight =$(document).height() - 250; 
		
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
		    "autoWidth":false,
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
						
						reloadFn();
						foucsInit();
						
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
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
				}, {"data": null,"className":"td-center"		//3单位
				}, {"data": "purchaseQuantity","className":"td-right"//4manufactureQuantity
				}, {"data": "requisitionQty","className":"td-right", "defaultContent" : '0'//5
				}, {"data": "totalRequisition","className":"td-right", "defaultContent" : '0'//6
				}, {"data": "quantityOnHand","className":"td-right"	//7 可用库存
				}, {"data": null,"className":"td-right"		//8
				}
			],
			"columnDefs":[
	    		
	    		{"targets":2,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					var inputTxt =       '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value=""/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			
	    			return name + inputTxt;
                }},
	    		{"targets":3,"render":function(data, type, row){	    			
	    			
	    			var unit = row["unit"];	    			
	    			var index=row["rownum"]
	
	    			if(unit == '吨'){
	    				unit = '千克';//转换成公斤
	    			}
	    								
	    			return unit;				 
                }},
	    		{"targets":4,"render":function(data, type, row){	    			
	    			/*
	    			var vrawunit = row["unit"];
	    			var vzzunit = row["zzunit"]; 			
	    			
	    			var qty = currencyToFloat(row["purchaseQuantity"]);//生产数量
	    			var value = '0';
	    			
	    			//原材料的购买单位			
	    			var farwunit = getUnitChange(vrawunit)
	    			//自制品的用量单位
	    			var fchgunit = getUnitChange(vzzunit)	    			
	    			//alert("materialId:"+row["materialId"]+"购买:"+farwunit+"用量:"+fchgunit)

	    			if(vrawunit == '吨')
	    				farwunit = farwunit *1000;//换算成千克
	    				
	    			return floatToCurrency( qty * farwunit / fchgunit );
	    			
	    			*/
	    			return floatToCurrency( row["quantity"] );
	    										 
                }},
	    		{"targets":5,"render":function(data, type, row){	    			
	    			
	    			var qty = floatToCurrency(row["requisitionQty"]);			
	    			return qty;				 
                }},
	    		{"targets":6,"render":function(data, type, row){	    			
	    			
	    			var qty = floatToCurrency(row["totalRequisition"]);			
	    			return qty;				 
                }},
	    		{"targets":7,"render":function(data, type, row){	    			
	    			
	    			var qty = floatToCurrency(row["quantityOnHand"]);			
	    			return qty;				 
                }},
	    		{"targets":8,"render":function(data, type, row){	
	    			
					var index=row["rownum"];	
					//var currValue = currencyToFloat(row["manufactureQuantity"]);
					var vrawunit = row["unit"];
	    			var vzzunit = row["zzunit"]; 			
	    			
	    			var qty = currencyToFloat(row["manufactureQuantity"]);
	    			var value = '0';
	    			
	    			//原材料的购买单位			
	    			var farwunit = getUnitChange(vrawunit)
	    			//自制品的用量单位
	    			var fchgunit = getUnitChange(vzzunit)	    			
	    			//alert("materialId:"+row["materialId"]+"购买:"+farwunit+"用量:"+fchgunit)

	    			if(vrawunit == '吨')
	    				farwunit = farwunit *1000;//换算成千克

		    		var requisitionQty = (row["requisitionQty"]);	
	    			var currValue =  floatToCurrency( qty * farwunit / fchgunit -requisitionQty);
					var inputTxt = '<input type="text" id="requisitionList'+index+'.quantity" name="requisitionList['+index+'].quantity" class="quantity num mini"  value="'+currValue+'"/>';
				
					return inputTxt;
                }},
                {
					"visible" : false,
					"targets" : []
				}
			]
			
		}).draw();		
						
		t.on('change', 'tr td:nth-child(9)',function() {

			var $td = $(this).parent().find("td");
			
			$td.eq(8).find("input").removeClass('error');

			var $oArrival = $td.eq(4);//计划
			var $oyiling  = $td.eq(5);//已领料
			var $oTotalQt = $td.eq(7);//总库存
			var $oCurrQty = $td.eq(8).find("input");//本次领料

			var fArrival  = currencyToFloat($oArrival.text());
			var fYiling   = currencyToFloat($oyiling.text());
			var fCurrQty  = currencyToFloat($oCurrQty.val());	
			var fTotalQt  = currencyToFloat($oTotalQt.text());
			
			//最多允许领料数量 = 计划 - 已领料
		 	var fMaxQuanty = fArrival - fYiling;
			if(fMaxQuanty < 0)
				fMaxQuanty = 0;
			//alert("fArrival--fYiling--fMaxQuanty--fCurrQty:"+fArrival+"---"+fYiling+"--"+fMaxQuanty+"---"+fCurrQty)
			if ( fCurrQty > fMaxQuanty ){//总库存充足
				
				fCurrQty = fMaxQuanty;
				$().toastmessage('showWarningToast', "领料数量不能超出需求量！");
			}
			
			$oCurrQty.val(floatToCurrency(fCurrQty));

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

	};
	
	function materialzzAjax() {

		var makeType = $('#makeType').val();
		var YSId= $('#requisition\\.ysid').val();
		var actionUrl = "${ctx}/business/requisitionzz?methodtype=getMaterialZZList";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&makeType="+makeType;
				
		var t = $('#example2').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	       "autoWidth":false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"aaSorting": [[ 1, "DESC" ]],
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
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns" : [
		        	{"data": null,"className":"dt-body-center"//0
				}, {"data": "YSId","className":"td-left", "defaultContent" : ''//5
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2
				}, {"data": "unit","className":"td-center"		//3单位
				}, {"data": "purchaseQuantity","className":"td-right"//4
				}, {"data": null, "defaultContent" : ''	//6 
				}
			],
			"columnDefs":[
	    		
	    		{"targets":3,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					//var inputTxt =       '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value=""/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			
	    			return name;
                }},
				{"targets":5,"render":function(data, type, row){	    			
	    			
	    			return floatToCurrency(data);				 
                }}
			]
			
		}).draw();

	
		
						
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

	};
	$(document).ready(function() {

		$( "#tabs" ).tabs();
		
		var scrollHeight =$(parent).height() - 200; 
		//日期
		$("#requisition\\.requisitiondate").val(shortToday());
		
		
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var makeType = $('#makeType').val();
					var url = "${ctx}/business/requisitionzz"+"?makeType="+makeType;
					location.href = url;		
				});

		$("#showHistory").click(function() {

			var YSId= $('#requisition\\.ysid').val();
			var makeType = $('#makeType').val();

			$.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : '',
				url : "${ctx}/business/requisitionzz?methodtype=checkRequisitionHistory"+"&YSId="+YSId+"&makeType="+makeType,
				success : function(data) {
	
					var rtnFlag = data["message"];
					if(rtnFlag == "true"){

						var url = "${ctx}/business/requisitionzz?methodtype=getRequisitionHistoryInit&YSId="+YSId+"&makeType="+makeType;
						location.href = url;
					}else{
						$().toastmessage('showWarningToast', "还没有领料记录。");
						return false;						
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			});	
		});
		
		$("#insert").click(
				function() {

			var total = quantitySum();
			if(total <= 0){
				$().toastmessage('showWarningToast', "本次领料数量为零,请重试。");
				return;
			}
			var makeType = $('#makeType').val();
			var addFlag = $('#addFlag').val();
			$('#formModel').attr("action", "${ctx}/business/requisitionzz?methodtype=insert"+
					"&makeType="+makeType+"&addFlag="+addFlag);
			$('#formModel').submit();
		});
		
		$("#reverse").click(function () { 
			$("input[name='numCheck']").each(function () {  
		        $(this).prop("checked", !$(this).prop("checked"));  
		    });
		});
		
		

		ajax(scrollHeight);
		
		materialzzAjax();
		
		foucsInit();
	 
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/requisition?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}

	//列合计
	function quantitySum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(8).find("input").val();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})
		return sum;
	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="goBackFlag" />
	<input type="hidden" id="makeType" value="${makeType }" />
	<input type="hidden" id="addFlag" value="${addFlag }" /><!-- 1:继续领料；0：第一次领料 -->
	
	<form:hidden path="requisition.requisitiontype" value="${makeType}"/>
	<form:hidden path="requisition.requisitionid" value=""/>
	<fieldset>
		<legend> 自制品原材料领料单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="150px">${order.YSId }
					<form:hidden path="requisition.ysid"  value="${order.YSId }"/></td>
														
				<td width="100px" class="label">产品编号：</td>
				<td width="150px">${order.materialId }</td>
														
				<td width="100px" class="label">产品名称：</td>
				<td>${order.materialName }</td>
			</tr>
			<tr>							
				<td width="100px" class="label">领料日期：</td>
				<td width="150px">
					<form:input path="requisition.requisitiondate" class="short read-only" /></td>
				
				<td width="100px" class="label">申请人：</td>
				<td colspan="3">
					<form:input path="requisition.requisitionuserid" class="short read-only" value="${userName }" /></td>
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认领料</a>
		<!-- <a class="DTTT_button DTTT_button_text" id="print" onclick="doPrint();return false;">打印领料单</a> -->
		<a class="DTTT_button DTTT_button_text" id="showHistory" >查看领料记录</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<div id="tabs" style="padding: 0px;white-space: nowrap;width:99%;">
		<ul>
			<li><a href="#tabs-1" class="tabs1">原材料</a></li>
			<li><a href="#tabs-2" class="tabs2">自制品</a></li>
		</ul>

		<div id="tabs-1" style="padding: 5px;">			
			<table id="example" class="display" >
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>
						<th width="50px">领料单位</th>				
						<th width="60px">生产用量</th>
						<th width="60px">已申请数量</th>
						<th width="60px">已出库数量</th>
						<th width="80px">可用库存</th>
						<th width="80px">本次领料</th>
					</tr>
				</thead>	
			</table>			
		</div>
		<div id="tabs-2" style="padding: 5px;">
			<table id="example2" class="display" >
				<thead>				
					<tr>
						<th style="width:3px">No</th>
						<th width="80px">耀升编号</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>
						<th width="50px">单位</th>				
						<th width="100px">生产数量</th>
						<th width="50px"></th>
					</tr>
				</thead>	
			</table>			
		</div>
	</div>
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function reloadFn(){
	
	var countValue = '0';

	//alert("countValue1:"+countValue)
	$('#example tbody tr').each (function (){
		
		var jihua = $(this).find("td").eq(4).text();////计划用量
		var yiling  = $(this).find("td").eq(5).text();//已领量:table初始化时,第五列被隐藏了
		var kucun   = $(this).find("td").eq(7).text();//库存
		var fjihua= currencyToFloat(jihua);
		var fyiling = currencyToFloat(yiling);
		var fkucun  = currencyToFloat(kucun);
		//alert("计划用量+已领量+库存:"+fjihua+"---"+fyiling+"---"+fkucun)
		var fsurplus = fjihua - fyiling;
				
		if(fsurplus > 0){//未领完的场合下
			//if(fkucun >= fsurplus){//库存大于需求量
				$(this).find("td").eq(8).find("input").val(formatNumber(fsurplus));//本次领料
				//$(this).find("td").eq(8).html("0")//剩余数清零
				countValue++;//累计未领完的物料
			//}else{
			//	$(this).find("td").eq(8).find("input").val(formatNumber(fkucun));//本次领料
				//$(this).find("td").eq(8).html(formatNumber( fsurplus - fkucun ));//剩余数清零							
			//}
		}else{
			fsurplus = 0;
			$(this).find("td").eq(8).find("input").val(fsurplus);//本次领料清零
			//$(this).find("td").eq(8).html(fsurplus);//剩余数清零
		}
		
		
	});	

	
}

</script>
</html>
