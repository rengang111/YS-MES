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
<title>自制品基本数据-编辑</title>

<script type="text/javascript">

	var counter  = 0;
	var Gpwer  = currencyToFloat('${price.powerPrice }');
	var Glabor = currencyToFloat('${price.laborPrice }');
	var Graw = '0';

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
							'<td><span></span><input type="hidden" name="rawMaterials['+rowIndex+'].wastage"   id="rawMaterials'+rowIndex+'.wastage" /></td>',
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
				
				var vprice = $tds.eq(7).find("input").val();
				var fprice = currencyToFloat(vprice);
				
				Graw = Graw - fprice;
				//alert(fprice+':fprice---'+Graw+':Graw')
				t.row('.selected').remove().draw();
	
				//随时计算该客户的销售总价
				costAcount();
				$().toastmessage('showNoticeToast', "删除成功。");	
				
	
				//重设显示窗口(iframe)高度
				iFramAutoSroll();
			}						
		}
	});
	
function foucsInit(){
	
	$("input:text").not(".read-only").addClass('bgnone');
	$("#price\\.materialid").removeClass('bgnone');
	$(".cash").css('border','1px solid #dadada');
	$(".attributeList1 ").addClass('bsolid')
	
	$("input:text") .not(".read-only") .focus(function(){
		$(this).removeClass('bgnone').removeClass('error').addClass('bgwhite');
		$("#price\\.materialid").removeClass('bgwhite');
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
		
	})

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
		var fpriceold = currencyToFloat($omatpricei.val());
		
		var fwastage = fnetweight * 0.02;//损耗2%
		var fweight = fnetweight + fwastage;//用料重量=净重量+损耗
		var aa=fkgprice.length;
		
		var	fpricenew = (fkgprice / 1000 * fweight);//单位材料价=单价/1000*克重量		

		var vwastage = floatToCurrency(fwastage);	
		var vweight = floatToCurrency(fweight);			
		var vpricenew = float4ToCurrency(fpricenew);
		
		//详情列表显示新的价格
		$owastages.html(vwastage);					
		$owastagei.val(vwastage);			
		$oweights.html(vweight);		
		$oweighti.val(vweight);
		$omatprices.html(vpricenew);	
		$omatpricei.val(vpricenew);	

		Graw = (Graw) - (fpriceold) + (fpricenew);//原材料单价总计
		//alert(Graw+':Graw----'+'fpriceold:'+fpriceold+'---fpricenew:'+fpricenew);
		
		//临时计算该客户的销售总价
		//首先减去旧的价格		
		costAcount();

		//判断原材料是否选择
        var $omaterial  = $tds.eq(1).find("input");//原材料
		var materialid = $omaterial.val();
		alert(materialid)
        if(materialid == "")
        	$omaterial.addClass("required");
		
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

	ajax();

	autocomplete();
	
	//设置光标项目
	//$("#attribute1").focus();
	//$("#attribute2").attr('readonly', "true");
	$("#price\\.totalprice").attr('readonly', "true");
		
	//设置经管费率默认值:12%
	$("#price\\.managementcostrate").val($("#price\\.managementcostrate option:eq(2)").val());

	
	$("#goBack").click(function() {
			var url = "${ctx}/business/zzmaterial";
			location.href = url;		
		});
	
	$("#update").click(function() {
		if(inputCheck()){
			$('#ZZMaterial').attr("action", "${ctx}/business/zzmaterial?methodtype=update");
			$('#ZZMaterial').submit();
		}
	});
	
	//计算人工成本
	$(".labor").change(function() {
		
		var vtime   = $("#price\\.time").val();//出模时间
		var vnum    = $("#price\\.cavitiesnumber").val();//出模数
		var vhprice = $("#price\\.hourprice").val();//每小时工价
		var ftime = currencyToFloat(vtime);
		var fnum  = currencyToFloat(vnum);
		var fhprice  = currencyToFloat(vhprice);
		//alert('fnum:'+fnum+'--ftime:'+ftime)
		
		//每小时产量 = 3600/出模时间*出模数
		var fyield = '0';
		if(ftime != '0' && fnum != '0'){
			fyield = (3600 / ftime * fnum).toFixed(4);			
		}
		var vyield = floatToCurrency(fyield);
		
		//工价 =每小时工价 / 每小时产量
		Glabor = 0;
		if(fyield != '0'){
			Glabor = fhprice / fyield;
		}
		
		var vlabor  = float4ToCurrency(Glabor);		

		$('#houryield').html(vyield);
		$("#price\\.houryield").val(vyield);
		$('#laborprice').html(vlabor);	
		$("#price\\.laborprice").val(vlabor);
		
		var fhourpwer = currencyToFloat($("#price\\.hourpower").val());
		var fkwprice  = currencyToFloat($("#price\\.kwprice").val());
		
		//计算该自制品的合计单价
		costAcount();

		//单位产品电价 = 每小时耗电*每度电价/每小时产量
		acountPowerPrice(fhourpwer,fkwprice,fyield);
		
		
	});
	
	//计算电耗
	$(".power").change(function() {
		
		var vkwatt   = $("#price\\.kilowatt").val();//机器功率(KW)
		var vkwprice = $("#price\\.kwprice").val();//每度电价
		
		var fkwatt   = currencyToFloat(vkwatt);
		var fkwprice = currencyToFloat(vkwprice);
				
		//每小时耗电 = 机器功率(KW) * 1
		var fhourpwer = fkwatt * 1;
		var vhourpwer = floatToCurrency(fhourpwer);
		$('#hourpower').html(vhourpwer);
		$('#price\\.hourpower').val(vhourpwer);
		
		//计算单位产品电价 = a每小时耗电*每度电价/每小时产量	
		var fyield = currencyToFloat($('#houryield').text());
		acountPowerPrice(fhourpwer,fkwprice,fyield);
		
		//计算该自制品的合计单价
		costAcount();
		
	});

	foucsInit();
});

function costAcount(){

	//计算该自制品的合计单价=合计*经管费率
	var frate = $('#price\\.managementcostrate').val();
	var ftotalPrice = (Glabor + Gpwer + Graw) * (1 + Number(frate) / 100);
	var vtotalPrice = floatToCurrency(ftotalPrice);
	
	$('#price\\.totalprice').val(vtotalPrice);
	// alert('Glabor:'+Glabor+"Gpwer:"+Gpwer+"Graw:"+Graw)
	$('#raw').text(Graw);
}

function acountPowerPrice(fpwer,fprice,fyield){

	//单位产品电价 = 每小时耗电*每度电价/每小时产量
	if(fyield == 0){
		Gpwer = 0;		
	}else{
		Gpwer = fpwer * fprice / fyield;			
	}
	//alert('fpwer:'+fpwer+'---fprice:'+fprice+'---fyield:'+fyield)
	var vpowerprice = float4ToCurrency(Gpwer);

	$('#powerprice').html(vpowerprice);
	$('#price\\.powerprice').val(vpowerprice);
	
}//计算单位产品电价


</script>
</head>

<body class="easyui-layout">
<div id="container">
<div id="main">
	
<form:form modelAttribute="ZZMaterial" method="POST" style='padding: 0px; margin: 0px 10px;' 
	id="ZZMaterial" name="ZZMaterial"   autocomplete="off">
		
	<form:hidden path="price.recordid" value="${price.zzRecordId }"/>
	
<fieldset style="margin-top: -16px;">
	<legend style='margin: 20px 10px -10px 0px;'>基本信息</legend>

	<table class="form" width="100%">		
		<tr>
			<td class="label" style="width: 100px;">产品编码：</td>
			<td style="width: 120px;">${price.materialId }
				<form:hidden path="price.materialid" /></td>
								
			<td class="label" style="width: 100px;"><label>产品名称：</label></td>
			<td colspan="3">${price.materialName }</td>
			
		<tr>
			<td class="label"><label>计量单位：</label></td>
			<td style="width: 100px;">${price.unit }</td>											
			<td class="label"><label>管理费率：</label></td>
			<td>
				<form:select path="price.managementcostrate" style="width: 50px;">							
					<form:options items="${ZZMaterial.manageRateList}" 
						itemValue="key" itemLabel="value" /></form:select></td>	
			<td class="label" >自制品单价（合计）：</td>
			<td><form:input path="price.totalprice" class="read-only cash short" value="${price.totalPrice }" /></td>					
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
			<th style="width:60px">单价/公斤</th>
			<th style="width:80px">材料价/克 </th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="raw" items="${detail}" varStatus="i">		
				<script type="text/javascript" >
					var cost = '${raw.materialPrice}';
					Graw = currencyToFloat(Graw) + currencyToFloat(cost);	
					//alert("Graw:"+Graw+"---currencyToFloat(cost):"+currencyToFloat(cost))
					counter++;
				</script>	
				<tr>
					<td></td>
					<td><input type="text" name="attributeList1" class="attributeList1" value="${raw.rawMaterialId }" />
						<form:hidden path="rawMaterials[${i.index}].rawmaterialid" value="${raw.rawMaterialId }" /></td>								
					<td><span>${raw.materialName }</span></td>
					<td><form:input path="rawMaterials[${i.index}].netweight" class="cash mini" value="${raw.netWeight }" /></td>							
					<td><span>${raw.wastage }</span>
						<form:hidden path="rawMaterials[${i.index}].wastage"  value="${raw.wastage }"  /></td>
					<td><span>${raw.weight }</span>
						<form:hidden path="rawMaterials[${i.index}].weight"  value="${raw.weight }" /></td>
					<td><form:input path="rawMaterials[${i.index}].kgprice" class="cash mini"  value="${raw.kgPrice }" /></td>
					<td><span>${raw.materialPrice }</span>
						<form:hidden path="rawMaterials[${i.index}].materialprice"  value="${raw.materialPrice }" /></td>				
				</tr>
				
					<form:hidden path="rawMaterials[${i.index}].recordid" value="${raw.recordId }" />
			
			</c:forEach>
		</tbody>
	</table>
			<span id="raw"></span>
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
				<td><form:input path="price.cavitiesnumber" class="cash short labor"  value="${price.cavitiesNumber }"/></td>							
				<td><form:input path="price.time" class="cash short labor" value="${price.time }" /></td>	
				<td><span id="houryield">${price.hourYield }</span><form:hidden path="price.houryield" value="${price.hourYield }" /></td>				
				<td><form:input path="price.hourprice" class="cash short labor"  value="${price.hourPrice }"/></td>
				<td><span id="laborprice">${price.laborPrice }</span><form:hidden path="price.laborprice"  value="${price.laborPrice }" /></td>				
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
				<td><form:input path="price.kilowatt" class="cash short power" value="${price.kilowatt }" /></td>							
				<td><span id="hourpower">${price.hourPower }</span><form:hidden path="price.hourpower" value="${price.hourPower }" /></td>
				<td><form:input path="price.kwprice"  class="cash short power" value="${price.kwPrice }"/></td>
				<td><span id="powerprice">${price.powerPrice }</span><form:hidden path="price.powerprice" value="${price.powerPrice }" /></td>				
			</tr>			
		</tbody>
	</table>	
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">	
		<button type="button" id="update" class="DTTT_button">保存</button>				
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	
</form:form>
</div>
</div>

<script type="text/javascript">

function autocomplete(){
		
	//自制品选择
	$("#price\\.materialid").autocomplete({
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
			
			//产品名称
			$('#materialname').text(ui.item.name);
			
			//计量单位
			$('#unit').text(ui.item.unit);			
		},

		
	});//自制品选择
		
	//原材料选择
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
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
					//alert(errorThrown);
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
