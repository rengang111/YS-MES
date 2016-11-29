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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>自制品基本数据-新建</title>
</head>

<body class="easyui-layout">
<div id="container">
<div id="main">
	
<form:form modelAttribute="ZZMaterial" method="POST" style='padding: 0px; margin: 0px 10px;' 
	id="ZZMaterial" name="ZZMaterial"   autocomplete="off">
		
	<form:hidden path="material.recordid" value=""/>
	
<fieldset style="margin-top: -16px;">
	<legend style='margin: 20px 10px -10px 0px;'>基本信息</legend>

	<table class="form" width="100%">		
		<tr>
			<td class="label" style="width: 100px;">产品编码：</td>
			<td style="width: 120px;">${material.materialId }</td>
								
			<td class="label" style="width: 100px;"><label>产品名称：</label></td>
			<td>${material.materialname }</td>	
			<td class="label" style="width: 100px;"></td>
			<td style="width: 300px;"></td>	
			
		<tr>										
			<td class="label"><label>管理费率：</label></td>
			<td>
				<form:select path="price.managementcostrate" style="width: 50px;">							
					<form:options items="${ZZMaterial.manageRateList}" 
						itemValue="key" itemLabel="value" /></form:select></td>	
			<td class="label"><label>计量单位：</label></td>
			<td>${material.unitname }</td>	
			<td class="label" >单价合计：</td>
			<td><form:input path="price.totalprice" class="read-only cash short" readonly="readonly" /></td>					
		</tr>
	</table>
	</fieldset>
	<div style="clear: both"></div>		
<fieldset style="margin-top: -14px;">
	<legend style="margin: 10px 0px -10px 0px"> 原材料</legend>

		<table id="example" class="display" width="100%">
	
		<thead>
		<tr>
			<th style="width:30px">No</th>
			<th style="width:80px">原材料编码</th>
			<th>原材料名称</th>
			<th style="width:70px">用料净重量</th>
			<th style="width:60px">损耗2%</th>
			<th style="width:60px">用料重量</th>
			<th style="width:60px">每公斤单价</th>
			<th style="width:80px">单位材料价 </th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="i" begin="0" end="4" step="1">		
				<tr>
					<td></td>
					<td><input type="text" name="attributeList1" class="attributeList1">
						<form:hidden path="rawMaterials[${i}].rawmaterialid" /></td>								
					<td><span></span></td>
					<td><form:input path="rawMaterials[${i}].netweight" class="cash mini" /></td>							
					<td><span></span>
						<form:hidden path="rawMaterials[${i}].wastage"   /></td>
					<td><span></span>
						<form:hidden path="rawMaterials[${i}].weight"  /></td>
					<td><form:input path="rawMaterials[${i}].kgprice" class="cash mini"  /></td>
					<td><span></span>
						<form:hidden path="rawMaterials[${i}].materialprice"  /></td>				
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	</fieldset>
	<fieldset style="margin-top: -15px;">
	<legend style="margin: 10px 0px 0px 0px"> 人工成本</legend>
	<table class="form" width="100%" style="text-align: center;margin-top: -5px;">
	
		<thead>
		<tr>
			<td style="width:200px">出模数</td>
			<td style="width:200px">出模时间(秒)</td>
			<td style="width:200px">每小时产量</td>
			<td style="width:200px">每小时工价</td>
			<td>单位产品工价</td>
		</tr>
		</thead>		
		<tbody>
			<tr>
				<td><form:input path="price.cavitiesnumber" class="cash short labor" /></td>							
				<td><form:input path="price.time" class="cash short labor"  /></td>	
				<td><span id="houryield"></span><form:hidden path="price.houryield" /></td>				
				<td><form:input path="price.hourprice" class="cash short labor"  /></td>
				<td><span id="laborprice"></span><form:hidden path="price.laborprice" /></td>				
			</tr>			
		</tbody>
	</table>
	<legend style="margin: 10px 0px 0px 0px"> 电耗</legend>
	<table class="form" width="100%" style="text-align: center;">
	
		<thead>
		<tr>
			<td style="width:200px">机器功率(KW)</td>
			<td style="width:200px">每小时耗电</td>
			<td style="width:200px">每度电价</td>
			<td>单位产品电价</td>
		</tr>
		</thead>		
		<tbody>
			<tr>
				<td><form:input path="price.kilowatt" class="cash short power" /></td>							
				<td><span id="hourpower"></span><form:hidden path="price.hourpower"/></td>
				<td><form:input path="price.kwprice"  class="cash short power"/></td>
				<td><span id="powerprice"></span><form:hidden path="price.powerprice"  /></td>				
			</tr>			
		</tbody>
	</table>	
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">					
		<button type="button" id="return" class="DTTT_button">返回</button>
		<button type="button" id="submitRefresh" class="DTTT_button">保存并继续添加</button>
		<button type="button" id="submitReturn" class="DTTT_button">保存</button>
	</fieldset>
	
</form:form>
</div>
</div>

<script type="text/javascript">

var counter  = 5;
var GrawCost = '0';
var Gpwer  = '0';
var Gprice = '0';
var Gyield = '0';
var Glabor = '0';
var Graw   = '0';
var Gtotal = '0';


//Form序列化后转为AJAX可提交的JSON格式。
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

$.fn.dataTable.TableTools.buttons.add_rows = $
.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {
				
				var rowIndex = counter;
				
				for (var i=0;i<1;i++){
					
				var rowNode = $('#example')
						.DataTable()
						.row
						.add(
						  [
							'<td></td>',
							'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
								'<input type="hidden" name="rawMaterials['+rowIndex+'].rawmaterialid" id="rawMaterials'+rowIndex+'.rawmaterialid" /></td>',
							'<td><span></span></td>',
							'<td><input type="text"   name="rawMaterials['+rowIndex+'].netweight" id="rawMaterials'+rowIndex+'.netweight" class="cash mini" /></td>',							
							'<td><input type="hidden" name="rawMaterials['+rowIndex+'].wastage"   id="rawMaterials'+rowIndex+'.wastage" /></td>',
							'<td><span></span><input type="hidden"   name="rawMaterials['+rowIndex+'].weight"      id="rawMaterials'+rowIndex+'.weight" /></td>',
							'<td><input type="text"   name="rawMaterials['+rowIndex+'].kgprice" id="rawMaterials'+rowIndex+'.kgprice" class="cash mini" /></td>',				
							'<td><span></span><input type="hidden"   name="rawMaterials['+rowIndex+'].materialprice"      id="rawMaterials'+rowIndex+'.materialprice" /></td>',	
							]).draw();
					
					rowIndex ++;						
				}					
				counter += 1;
				
				autocomplete();

				//重设显示窗口(iframe)高度
				iFramAutoSroll();
					
				foucsInit();
			}
		});

$.fn.dataTable.TableTools.buttons.reset = $.extend(true, {},
	$.fn.dataTable.TableTools.buttonBase, {
	"fnClick" : function(button) {
		
		var t=$('#example').DataTable();
		
		rowIndex = t.row('.selected').index();

		if(typeof rowIndex == "undefined"){				
			$().toastmessage('showWarningToast', "请选择要删除的数据。");	
			
		}else{
			
			var $tds = $('#example tbody tr').eq(rowIndex).find("td");
			
			var materialId = $tds.eq(1).find("input:text").val();
			var amount = $tds.eq(6).find("input").val();
			
			t.row('.selected').remove().draw();

			//随时计算该客户的销售总价
			costAcount(materialId,0, currencyToFloat(amount));
			$().toastmessage('showNoticeToast', "删除成功。");	
		}						
	}
});
function foucsInit(){
	
	$("input:text").not(".read-only").addClass('bgnone');
	$("#bomPlan\\.plandate").removeClass('bgnone');
	 $(".cash").css('border','1px solid #dadada');
	$(".attributeList1 ").addClass('bsolid')
	$(".attributeList2").addClass('bsolid')
	
	$("input:text") .not(".read-only") .focus(function(){
		$(this).removeClass('bgnone').removeClass('error').addClass('bgwhite');
		$("#bomPlan\\.plandate").removeClass('bgwhite');
	    $(this).select();
	});
	
	$(".read-only").removeClass('bgwhite');
	
	$(".cash") .focus(function(){
		$(this).val(currencyToFloat($(this).val()));
	    $(this).select();
	});
	
	$(".cash") .blur(function(){
		$(this).val(floatToCurrency($(this).val()));
	});
	
	// $(".DTTT_container").css('float','left');
	$(".DTTT_container").css('margin-top',' -24px');
	
}
function ajax() {

	var t = $('#example').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
		//"scrollY"    : height,
        //"scrollCollapse": true,
        "paging"    : false,
        "pageLength": 50,
        "ordering"  : false,

		dom : 'T<"clear">lt',

		"tableTools" : {

			"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

			"aButtons" : [ {
				"sExtends" : "add_rows",
				"sButtonText" : "追加新行"
			},
			{
				"sExtends" : "reset",
				"sButtonText" : "清空一行"
			}],
		},
		
		"columns" : [ 
		        	{"className":"dt-body-center"
				}, {
				}, {								
				}, {				
				}, {"className":"dt-body-right"				
				}, {"className":"dt-body-right"				
				}, {"className":"dt-body-right"				
				}, {"className":"dt-body-right"				
				}			
			]
		
	}).draw();

	/*
	t.on('blur', 'tr td:nth-child(2),tr td:nth-child(4)',function() {
		
		var currValue = $(this).find("input:text").val().trim();

        $(this).find("input:text").removeClass('bgwhite');
        
        if(currValue =="" ){
        	
        	 $(this).find("input:text").addClass('error');
        }else{
        	 $(this).find("input:text").addClass('bgnone');
        }
		
	});
		
	
	*/
	t.on('blur', 'tr td:nth-child(2),tr td:nth-child(4),tr td:nth-child(7)',function() {
		
       $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

	});
	t.on('change', 'tr td:nth-child(4),tr td:nth-child(7)',function() {
		
		//用料净重量,损耗2%,用料重量=净重量+损耗,每公斤单价, 单位材料价=单价/1000*克重量
		
        var $tds = $(this).parent().find("td");
		
        var $onetweight = $tds.eq(3).find("input");//用料净重量
        var $okgprice   = $tds.eq(6).find("input");//每公斤单价
		var $owastages  = $tds.eq(4).find("span");//损耗
		var $owastagei  = $tds.eq(4).find("input:hidden");
		var $oweights   = $tds.eq(5).find("span");//用料重量
		var $oweighti   = $tds.eq(5).find("input:hidden");
		var $omatprices = $tds.eq(7).find("span");//单位材料价
		var $omatpricei = $tds.eq(7).find("input:hidden");
		
		var fnetweight = currencyToFloat($onetweight.val());		
		var fkgprice = currencyToFloat($okgprice.val());			
		var fwastage = currencyToFloat($owastages.val());
		var fweight = currencyToFloat(fPrice * fQuantity);

		var vPrice = floatToCurrency(fPrice);	
		var vQuantity = floatToCurrency(fQuantity);
		var vTotalNew = floatToCurrency(fTotalNew);
				
		//详情列表显示新的价格
		$oThisPrice.val(vPrice);					
		$oQuantity.val(vQuantity);	
		$oAmount1.val(vTotalNew);	
		$oAmount2.html(vTotalNew);	

		//临时计算该客户的销售总价
		//首先减去旧的价格		
		//alert('fTotalNew:'+fTotalNew+"fTotalOld:"+fTotalOld)
		costAcount($oMaterial.val(),fTotalNew,fTotalOld);
		
	});
		
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

};//ajax()


$(document).ready(function() {

	$("#material :input.required").each(function(){
        var $required = $("<strong class='high'> *</strong>"); //创建元素
        $(this).parent().append($required); //然后将它追加到文档中
    });

	
	ajax();

	autocomplete();
	
	//设置光标项目
	//$("#attribute1").focus();
	//$("#attribute2").attr('readonly', "true");
	//$("#material\\.materialid").attr('readonly', "true");

	
	$("#return").click(
		function() {
			var url = "${ctx}/business/material";
			location.href = url;		
		});
	
	$("#submitRefresh").click(
			function() {				
				if(inputCheck()){
					doSubmitRefresh();
				}

		});
	$("#submitReturn").click(
			function() {
				if(inputCheck()){
					doSubmitReturn();
				}

		});
	
	//计算人工成本
	$(".labor").change(function() {
		
		var vtime   = $("#price\\.time").val();
		var vnum    = $("#price\\.cavitiesnumber").val();
		var vhprice = $("#price\\.hourprice").val();//每小时工价
		var ftime = currencyToFloat(vtime);
		var fnum  = currencyToFloat(vnum);
		var fhprice  = currencyToFloat(vhprice);
		//alert('fnum:'+fnum+'--ftime:'+ftime)
		
		//每小时产量 = 3600/出模时间*出模数
		if(ftime == 0){
			Gyield = 0;
		}else{
			Gyield = 3600 / ftime * fnum;			
		}
		var vyield = floatToCurrency(Gyield);

		//alert('Gyield:'+Gyield+'--vyield:'+vyield)
		
		//工价 =每小时工价 * 每小时产量
		Glabor = fhprice * Gyield;
		var vlabor  = floatToCurrency(Glabor);		

		$('#houryield').html(vyield);
		$('#laborprice').html(vlabor);	
		$("#price\\.houryield").val(vyield);
		$("#price\\.laborprice").val(vlabor);

		//单位产品电价 = 每小时耗电*每度电价/每小时产量
		acountPowerPrice(Gpwer,Gprice,Gyield);
		
	});
	
	//计算电耗
	$(".power").change(function() {
		
		var vkwatt = $("#price\\.kilowatt").val();
		var vprice = $("#price\\.kwprice").val();
		var fkwatt = currencyToFloat(vkwatt);
		Gprice = currencyToFloat(vprice);
				
		//每小时耗电 = 机器功率(KW) * 1
		Gpwer = fkwatt * 1;
		var vpwer = floatToCurrency(Gpwer);
		$('#hourpower').html(vpwer);
		
		//计算单位产品电价
		acountPowerPrice(Gpwer,Gprice,Gyield);
		
	});

	foucsInit();
});

function costAcount(){
	
}
function acountPowerPrice(fpwer,fprice,fyield){

	//单位产品电价 = 每小时耗电*每度电价/每小时产量
	if(fyield == 0){
		var fpowerprice = 0;
		
	}else{
		var fpowerprice = fpwer * fprice / fyield;			
	}
	
	var vpowerprice = float4ToCurrency(fpowerprice);

	$('#powerprice').html(vpowerprice);
	
}//计算单位产品电价

function inputCheck(){
	
	//验证
	var strValue = true;
    if($('#material\\.categoryid').val()==""  ){
        $("#attribute1").css("backgroundColor","rgba(255, 0, 0, 0.28)");
        strValue = false;
    }
     if($('#material\\.materialname').val()==""  ){
         $("#material\\.materialname").css("backgroundColor","rgba(255, 0, 0, 0.28)");
         strValue = false;
    }  	
	var strModelValue = '';
		
	$("#coupon input[type=text]").each(function () {
		if($.trim($(this).val()) != ''){
			strModelValue =$(this).val() +","+strModelValue;
		}
	})
	$("#material\\.sharemodel").val(strModelValue) ;
	
	return strValue;
}

function doSubmitReturn(){
		
	$('#material').attr("action", "${ctx}/business/material?methodtype=insertReturn");
	$('#material').submit();
}

function doSubmitRefresh(){
			
	var actionUrl='${ctx}/business/material?methodtype=insertRefresh';

	$.ajax({
		type : "POST",
		//contentType : 'application/json',
		dataType : 'json',
		async:false,
		url : actionUrl,
		data : $('#material').serialize(),
		success : function(data) {
			
			if(data.message!=""){
				alert(data.message);
				window.location.reload();
			}
		},		
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert(XMLHttpRequest.status);					
			//alert(XMLHttpRequest.readyState);					
			//alert(textStatus);					
			//alert(errorThrown);
			alert("发生系统异常，请再试或者联系系统管理员.");
		}
	});
	
}
</script>
<script type="text/javascript">

function autocomplete(){
	
	$(".attributeList1").autocomplete({
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
			
			var rowIndex = $(this).parent().parent().parent()
					.find("tr").index(
							$(this).parent().parent()[0]);

			//alert(rowIndex);

			var t = $('#example').DataTable();
			
			//产品名称			
			if(ui.item.name.length > 20){	
				var shortName =  '<div title="' +
				ui.item.name + '">' + 
				ui.item.name.substr(0,20)+ '...</div>';
			}else{	
				var shortName = ui.item.name;
			}
			
			t.cell(rowIndex, 2).data(shortName);

			//产品编号
			$(this).parent().find("input:hidden").val(ui.item.materialId);
			
		},

		
	});
}

</script>
</body>
</html>
