<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>超领申请-领料单</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

var counter = Number('${recordCount}');

$.fn.dataTable.TableTools.buttons.add_rows = $
.extend(
	true,
	{},
	$.fn.dataTable.TableTools.buttonBase,
	{
		"fnClick" : function(button) {
			
			var rowIndex = counter;
			for (var i=0;i<1;i++){
				
				var fmtId = "";
				var deleteFlag = false;
				
				var lineNo = rowIndex+1;
			
				var hidden = '<input type="hidden" id="requisitionList'+rowIndex+'.subbomno" name="requisitionList['+rowIndex+'].subbomno" value="0"/>';
				
				var rowNode = $('#example2')
					.DataTable()
					.row
					.add(
					  [
						'<td>'+lineNo+'<input type="checkbox" class="numCheck1" name="numCheck" id="numCheck"  /></td>',
						'<td><input type="text"   name="requisitionList['+rowIndex+'].materialid"    id="requisitionList'+rowIndex+'.materialid" class="attributeList1" /></td>',
						'<td><span id="name'+rowIndex+'"></span></td>',
						'<td><span id="onhand'+rowIndex+'"></span></td>',
						'<td><input type="text"   name="requisitionList['+rowIndex+'].quantity"      id="requisitionList'+rowIndex+'.quantity"       class="num mini" /></td>',
						'<td><input type="text"   name="requisitionList['+rowIndex+'].scrapquantity" id="requisitionList'+rowIndex+'.scrapquantity"  class="num mini" /></td>',
						'<td><input type="hidden" name="requisitionList['+rowIndex+'].subbomno"      id="requisitionList'+rowIndex+'.subbomno"  value="0"/></td>',
						]).draw();
				
				$("#requisitionList" + rowIndex + "\\.materialid").focus();
				
				//rowIndex ++;						
			}					
			counter += 1;				
	
			
			foucsInit();//设置新增行的基本属性
			
			autocomplete();//重新加载
		}
	});

$.fn.dataTable.TableTools.buttons.reset = $.extend(true, {},
	$.fn.dataTable.TableTools.buttonBase, {
	"fnClick" : function(button) {

		var t=$('#example2').DataTable();
		
		rowIndex = t.row('.selected').index();

		if(typeof rowIndex == "undefined"){				
			$().toastmessage('showWarningToast', "请选择要删除的数据。");	
		}else{
			
			t.row('.selected').remove().draw();
			$().toastmessage('showWarningToast', "删除成功。");	

		}
					
	}
});	
	
	function planAjax(scrollHeight) {
		
		var t = $('#example2').DataTable({
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
			"scrollY"    : scrollHeight,
	        "scrollCollapse": true,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
			"dom" : 'T<"clear">rt',
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
		        	{"className":"dt-body-center"//0
				}, {"className":"td-left"//1
				}, {				//2
				}, {"className":"td-right","defaultContent" : '0'	//6 可用库存
				}, {"className":"td-right"		//7
				}, {"className":"td-right"		//11
				}, {"className":"td-right"		//11
				}
			]
			
		}).draw();

		t.on('change', 'tr td:nth-child(5)',function() {

			var $td = $(this).parent().find("td");
			
			var $oCurrQty = $td.eq(4).find("input");//本次领料

			$oCurrQty.removeClass('error');
			$oCurrQty.val(floatToCurrency($.trim($oCurrQty.val())));

		});
		
		t.on('change', 'tr td:nth-child(6)',function() {

			var $td = $(this).parent().find("td");
			
			var $oCurrQty = $td.eq(5).find("input");//本次领料

			$oCurrQty.removeClass('error');
			$oCurrQty.val(floatToCurrency($.trim($oCurrQty.val())));

		});

	};
	
	$(document).ready(function() {
		
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
			var mateFlag = true;
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					str += $(this).val() + ",";
					var excess = $(this).parent().parent().find('td').eq(4).find("input").val();
					var scra = $(this).parent().parent().find('td').eq(5).find("input").val();
					var materialid = $(this).parent().parent().find('td').eq(1).find("input").val();
					
					excess = currencyToFloat(excess);
					if(excess<=0){
						inputFlag = false;
						$(this).parent().parent().find('td').eq(4).find("input").addClass('error');
					}
					if(scra=="" || $.trim(scra) == ""){
						scraFlag = false;
						$(this).parent().parent().find('td').eq(5).find("input").addClass('error');
					}else{
						scra = currencyToFloat(scra);
						if(scra<=0){
							scraFlag = false;
							$(this).parent().parent().find('td').eq(5).find("input").addClass('error');
						}
					}
					if(materialid=="" || $.trim(materialid)=="" ){
						mateFlag = false;
						$(this).parent().parent().find('td').eq(1).find("input").addClass('error');
					}
					//val += qty;
				}else{
					//未选中物料的领料数量恢复为0
					$(this).parent().parent().find('td').eq(4).find("input").val('0');
					$(this).parent().parent().find('td').eq(5).find("input").val('');
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

			if(mateFlag == false){
				$().toastmessage('showWarningToast', "请输入物料编码。");
				return;				
			}
			
			$('#formModel').attr("action", "${ctx}/business/requisition?methodtype=excessAdd");
			$('#formModel').submit();
		});
		
		foucsInit();

		var scrollHeight =$(parent).height() - 230; 
		planAjax(scrollHeight);
		$(".DTTT_container").css('float','left');
		
		$('#example2').DataTable().on('click', 'tr td:nth-child(1)', function() {

			$(this).parent().toggleClass("selected");

		    var checkbox  = $(this).find("input[type='checkbox']");
		    var isChecked = checkbox.is(":checked");
		    

		    if (isChecked) {
		        checkbox.prop("checked", false)
		        checkbox.removeAttr("checked");
		    } else {
		        checkbox.prop("checked", true)
		        checkbox.attr("checked","true");
		    }
		});	
		   
	   $('#example2').DataTable().on('click', 'tr td:nth-child(3),tr td:nth-child(4)', function() {

			$(this).parent().toggleClass("selected");
		    var checkbox  = $(this).parent().children().first().find("input[type='checkbox']");
		    var isChecked = checkbox.is(":checked");
		    

		    if (isChecked) {
		        checkbox.prop("checked", false)
		        checkbox.removeAttr("checked");
		    } else {
		        checkbox.prop("checked", true)
		        checkbox.attr("checked","true");
		    }
		});	
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
					class="middel" value="${order.YSId}" />
					<span style="color: blue">（查询范围：耀升编号、产品编号、产品名称）</span></td>
									
			</tr>
			<tr>
				<td class="label">生产数量：</td>					
				<td width="150px">&nbsp;<span id="quantity">${order.orderQty}</span></td>
							
				<td class="label"width="100px">产品编号：</td>					
				<td width="100px"><span id="materialId">${order.productId}</span></td>
							
				<td class="label" width="100px">产品名称：</td>					
				<td><span id="materialName">${order.productName}</span></td>
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
			<table id="example2" class="display" >
				<thead>				
					<tr>
						<th style="width:30px">No</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>
						<th width="60px">当前库存</th>
						<th width="80px">超领数量</th>
						<th width="60px">退货数</th>
						<th width="10px"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="detail" items="${material}" varStatus='status' >							
						<tr>
							<td>${status.index + 1}
								<input type="checkbox" class="numCheck1" name="numCheck" id="numCheck${status.index}" 
									value="${detail.materialId}" /></td>
							
							<td>${detail.materialId}
								<form:hidden path="requisitionList[${status.index}].materialid" 
									value="${detail.materialId}" style="width:200px" /></td>
							
							<td>${detail.materialName}</td>
							<td>${detail.quantityOnHand}</td>
							
							<td>${detail.quantity}
								<form:input path="requisitionList[${status.index}].quantity" 
									value="0" class="quantity num mini" /></td>
							
							<td>${detail.scrapQuantity}
								<form:input path="requisitionList[${status.index}].scrapquantity" 
									value="" class="quantity num mini" /></td>
							
							<td><form:hidden path="requisitionList[${status.index}].subbomno" 
									value="${detail.subBomNo}"  /></td>	
						</tr>
					</c:forEach>
				</tbody>	
			</table>
		</div>
	</fieldset>
</form:form>

</div>
</div>
</body>


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

		var actionUrl = "${ctx}/business/requisition?methodtype=excessInitYsidSelected";
		actionUrl = actionUrl +"&YSId="+ui.item.value;
		actionUrl = actionUrl +"&orderType=010";

		location.href = actionUrl;

	},//select
	
	minLength : 5,
	autoFocus : false,
});


function autocomplete(){
	
	$('.attributeList1').bind('input propertychange', function() {

		$(this).parent().parent().find('td').eq(2).find("span").text('');
	}); 
	
	//物料选择
	$(".attributeList1").autocomplete({
		minLength : 5,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/bom?methodtype=getMaterialPriceList",
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
						   		   onhand : item.quantityOnHand,
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
			
			var $td = $(this).parent().parent().find('td');

			var $oMatName = $td.eq(2).find("span");
			var $oOnhand  = $td.eq(3).find("span");
			var $oQuantity= $td.eq(4).find("input");
		
			//格式化数据	
			var matName = jQuery.fixedWidth(ui.item.name,30);
			var onhand    = ui.item.onhand;
			
			//显示到页面
			$oMatName.html(matName);
			$oOnhand.html(onhand);
			
			
			//光标位置
	      	$oQuantity.val('0');//使用量默认为0
			$oQuantity.focus();
			
			
			
		},
	
		
	});//物料选择
}
</script>

</html>
