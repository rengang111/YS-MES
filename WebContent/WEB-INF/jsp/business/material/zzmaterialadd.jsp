<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>自制品基本数据-新建</title>

<script type="text/javascript">

	var counter  = 2;
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
					.add([
						'<td></td>',
						'<td><input type="text"   name="attributeList1"  class="attributeList1" style="width: 130px;">'+
							'<input type="hidden" name="rawMaterials['+rowIndex+'].rawmaterialid" id="rawMaterials'+rowIndex+'.rawmaterialid" /></td>',
						'<td><span></span>'+
							'<input type="hidden" name="rawMaterials['+rowIndex+'].supplierid"    id="rawMaterials'+rowIndex+'.supplierid" /></td>',
						'<td><input type="text"   name="rawMaterials['+rowIndex+'].netweight"     id="rawMaterials'+rowIndex+'.netweight" class="cash mini" /></td>',
						'<td><span></span><select name="rawMaterials['+rowIndex+'].unit"          id="rawMaterials'+rowIndex+'.unit" style="display:none;width:60px"></select>'+
							'<input type="hidden" id="unit'+rowIndex+'"  /></td>',
						'<td><input type="text"   name="rawMaterials['+rowIndex+'].wastagerate"   id="rawMaterials'+rowIndex+'.wastagerate" class="num small" />%</td>',
						'<td><span></span><input  name="rawMaterials['+rowIndex+'].wastage"       id="rawMaterials'+rowIndex+'.wastage"  type="hidden" /></td>',
						'<td><span></span><input  name="rawMaterials['+rowIndex+'].weight"        id="rawMaterials'+rowIndex+'.weight"  type="hidden" /></td>',
						'<td><span></span>'+
										 '<input  id="rawPrice'+rowIndex+'" type="hidden" />'+
										 '<input  name="rawMaterials['+rowIndex+'].convertunit"       id="rawMaterials'+rowIndex+'.convertunit" type="hidden" /></td>',				
						'<td><span></span><input  name="rawMaterials['+rowIndex+'].materialprice" id="rawMaterials'+rowIndex+'.materialprice" type="hidden"  /></td>',	
						]).draw();

					rowIndex ++;						
				}					
				counter += 1;
				
				autocomplete();

				//重设显示窗口(iframe)高度
				//iFramAutoSroll();
					
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
				
				t.row('.selected').remove().draw();
	
				//计算自制品单价
				costAcount();
				$().toastmessage('showNoticeToast', "删除成功。");	
					
				//重设显示窗口(iframe)高度
				iFramAutoSroll();
			}						
		}
	});
	
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
			}, {"className":"dt-body-right"
			}, {"className":"dt-body-center"
			}, {"className":"dt-body-center"	
			}, {"className":"dt-body-right"	
			}, {"className":"dt-body-right"
			}, {"className":"dt-body-right"
			}, {"className":"dt-body-right"	
			}			
		]
		
	})

	t.on('blur', 'tr td:nth-child(2),tr td:nth-child(4),tr td:nth-child(6),tr td:nth-child(9)',function() {
		
       $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

	});
	
	t.on('change', 'tr td:nth-child(4),tr td:nth-child(5),tr td:nth-child(6),tr td:nth-child(9)',function() {
		
        var $tds = $(this).parent().find("td");

        var $onetweight = $tds.eq(3).find("input");//用料净重量

		var $orawunit   = $tds.eq(4).find("input:hidden");//原计量单位
		var $ochgunit   = $tds.eq(4).find("select");//换算后的计量单位$("select option:checked").text();
		var $ounittext  = $tds.eq(4).find("select option:checked");
		var $owastRate  = $tds.eq(5).find("input");//损耗比
		var $owastages  = $tds.eq(6).find("span");//损耗
		var $owastagei  = $tds.eq(6).find("input:hidden");
		var $oweights   = $tds.eq(7).find("span");//用料重量
		var $oweighti   = $tds.eq(7).find("input:hidden");
        var $okgprice   = $tds.eq(8).find("span");//使用单位(只写)
        var $orawprice  = $tds.eq(8).find("input:hidden").eq(0);//购买单位(只读)
        var $oconvunit  = $tds.eq(8).find("input:hidden").eq(1);//换算单位(只写)
		var $omatprices = $tds.eq(9).find("span");//总价
		var $omatpricei = $tds.eq(9).find("input:hidden");

		var vunitnew   = "";
		var vrawunit   = $orawunit.val();
		var vchgunit   = $ochgunit.val();
		var vwastRate  = $owastRate.val();
		var unittext   = $ounittext.text();
		var fnetweight = currencyToFloat($onetweight.val());
		var frawprice  = currencyToFloat($orawprice.val());

		var farwunit = '1';//初始值
		//原材料的购买单位
		for(var i=0;i<unitAaary.length;i++){
			var val = unitAaary[i][0];//取得计算单位:100,1000...
			var key = unitAaary[i][1];//取得显示单位:克,吨...
			if(vrawunit == key){
				farwunit = val;
				//alert('原材料的购买单位'+farwunit)
				break;
			}
		}

		//自制品的用量单位
		var fchgunit = '1';//初始值
		for(var i=0;i<unitAaary.length;i++){
			var val = unitAaary[i][0];//取得计算单位:100,1000...
			var key = unitAaary[i][1];//取得显示单位:克,吨...
			if(unittext == key){
				fchgunit = val;//只有在需要换算的时候,才设置换算单位
				//alert('自制品的用量单位'+fchgunit)
				break;
			}
		}		
		var fwastRate = currencyToFloat(vwastRate);			//损耗比
		var fwastage  = fnetweight * fwastRate / 100;		//损耗
		var fweight   = fnetweight + fwastage;				//总用量=净用量量+损耗
		var fkgprice  = frawprice * farwunit / fchgunit; 	//换算后单价=原单价*原单位/新单位
		var fpricenew = fkgprice * fweight ;				//单位材料价=新单价*总用量	
		var fconvunit = fchgunit / farwunit;				//换算单位
		//alert('frawprice:'+frawprice+'--farwunit:'+farwunit+'--fchgunit:'+fchgunit+'--fpricenew:'+fpricenew);	

		var vnetweight = formatNumber(fnetweight);
		var vwastage = formatNumber(fwastage);	
		var vweight  = formatNumber(fweight);		
		var vkgprice  = formatNumber(fkgprice);
		var vpricenew = formatNumber(fpricenew);
		
		//详情列表显示新的价格
		$onetweight.val(vnetweight);
		$owastages.html(vwastage);					
		$owastagei.val(vwastage);			
		$oweights.html(vweight);
		$oweighti.val(vweight);
		$okgprice.html(vkgprice);
		$oconvunit.val(fconvunit);
		$omatprices.html(vpricenew);	
		$omatpricei.val(vpricenew);	

		//计算自制品单价
		costAcount();
		
		//判断原材料是否选择
        var $omaterial  = $tds.eq(1).find("input");//原材料
		var materialid = $omaterial.val();

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
	$("#price\\.totalprice").attr('readonly', "true");
		
	//设置经管费率默认值:12%
	$("#price\\.managementcostrate").val($("#price\\.managementcostrate option:eq(2)").val());

	
	$("#goBack").click(function() {
			var url = "${ctx}/business/zzmaterial";
			location.href = url;		
		});
	
	$("#submitReturn").click(
			function() {
				if(inputCheck()){
					doSubmitReturn();
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
		
		//每小时产量 = 3600/出模时间*出模数
		var fyield = '0';
		if(ftime != '0' && fnum != '0'){
			fyield = (3600 / ftime * fnum).toFixed(4);			
		}
		
		//工价 =每小时工价 / 每小时产量
		var labor = 0;
		if(fyield != '0'){
			labor = fhprice / fyield;
		}

		var vyield = floatToCurrency(fyield);
		var vlabor = formatNumber(labor);		

		$('#houryield').html(vyield);
		$("#price\\.houryield").val(vyield);
		$('#laborprice').html(vlabor);	
		$("#price\\.laborprice").val(vlabor);

		//单位产品电价 = 每小时耗电*每度电价/每小时产量
		acountPowerPrice();
		
		//计算该自制品的合计单价
		costAcount();
		
	});
	
	//计算电耗
	$(".power").change(function() {
		
		var vkwatt   = $("#price\\.kilowatt").val();//机器功率(KW)
		var vkwprice = $("#price\\.kwprice").val();//每度电价
		
		var fkwatt   = currencyToFloat(vkwatt);
		var fkwprice = currencyToFloat(vkwprice);
				
		//每小时耗电 = 机器功率(KW) * 1
		var fhourpwer = fkwatt * 0.6;
		var vhourpwer = floatToCurrency(fhourpwer);
		$('#hourpower').html(vhourpwer);
		$('#price\\.hourpower').val(vhourpwer);
		
		//单位产品电价 = 每小时耗电*每度电价/每小时产量	
		acountPowerPrice();
		
		//计算该自制品的合计单价
		costAcount();
		
	});
	
	//经管费率
	$("#price\\.managementcostrate").change(function() {
		
		//计算该自制品的合计单价
		costAcount();
		
	});

	foucsInit();
	
	$(".DTTT_container").css('margin-top',' -24px');
});

function costAcount(){

	var frate = Number( $('#price\\.managementcostrate').val() );
	var labor = currencyToFloat($('#laborprice').text());
	var pwer  = currencyToFloat($('#powerprice').text());
	
	//原材料合计
	var raw = productCostSum();
	
	//计算该自制品的合计单价=合计*经管费率
	var ftotalPrice = (labor + pwer + raw) * (1 + frate / 100);
	var vtotalPrice = formatNumber(ftotalPrice);
	
	$('#price\\.totalprice').val(vtotalPrice);
	
	//$('#raw').text(raw);//测试用	
	//alert('labor:'+labor+"pwer:"+pwer+"raw:"+raw+"frate:"+frate)	
}

//列合计:
function productCostSum(){

	var sum = 0;
	$('#example tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(9).find("span").text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;
		
	})
	return sum;

}

function acountPowerPrice(){

	var fhourpwer = currencyToFloat($("#price\\.hourpower").val());
	var fkwprice  = currencyToFloat($("#price\\.kwprice").val());
	var fyield    = currencyToFloat($('#houryield').text());
	
	//单位产品电价 = 每小时耗电*每度电价/每小时产量
	var pwer = '0';
	if(fyield != '0'){
		pwer = fhourpwer * fkwprice / fyield;			
	}
	var vpwer = formatNumber(pwer);

	$('#powerprice').html(vpwer);
	$('#price\\.powerprice').val(vpwer);

	//alert('fpwer:'+fhourpwer+'---fprice:'+fkwprice+'---fyield:'+fyield)
	
}//计算单位产品电价


function doSubmitReturn(){
		
	$('#ZZMaterial').attr("action", "${ctx}/business/zzmaterial?methodtype=insert");
	$('#ZZMaterial').submit();
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
</head>

<body>
<div id="container">
<div id="main">
	
<form:form modelAttribute="ZZMaterial" method="POST" 
	id="ZZMaterial" name="ZZMaterial"   autocomplete="off">
		
	<form:hidden path="price.recordid" value=""/>
	
<fieldset>
	<legend>基本信息</legend>

	<table class="form">
		<tr>
			<td class="label" style="width: 120px;">产品编号：</td>
			<td style="width: 150px;">
				<form:input path="price.materialid" class="required" value="${price.materialId }"/></td>
								
			<td class="label" style="width: 120px;"><label>产品名称：</label></td>
			<td ><span id="materialname">${price.materialName }</span></td>
			<td class="label" style="width: 120px;"><label>计量单位：</label></td>
			<td style="width: 150px;">&nbsp;<span id="unit">${price.dicName }</span></td>	
		<tr>
			<td class="label"><label>自制类别：</label></td>
			<td>
				<form:select path="price.type" style="width: 100px;">							
					<form:options items="${ZZMaterial.typeList}" 
						itemValue="key" itemLabel="value" /></form:select></td>	
											
			<td class="label"><label>管理费率：</label></td>
			<td>
				<form:select path="price.managementcostrate" style="width: 100px;">							
					<form:options items="${ZZMaterial.manageRateList}" 
						itemValue="key" itemLabel="value" /></form:select></td>	
			<td class="label" >自制品单价（合计）：</td>
			<td><form:input path="price.totalprice" class="read-only cash short" readonly="readonly" /></td>					
		</tr>
	</table>
	</fieldset>
	<div style="clear: both"></div>		
	<fieldset style="margin-top: -14px;">
		<legend style="margin: 10px 0px -10px 0px"> 原材料</legend>
		<div class="list">
			<table id="example" class="display">
	
		<thead>
		<tr>
			<th style="width:1px">No</th>
			<th style="width:80px">原材料编码</th>
			<th>原材料名称</th>
			<th style="width:70px">净用量</th>
			<th style="width:30px">单位</th>
			<th style="width:50px">损耗比</th>
			<th style="width:50px">损耗</th>
			<th style="width:60px">总用量</th>
			<th style="width:60px">单价</th>
			<th style="width:60px">总价</th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="i" begin="0" end="1" step="1">		
				<tr>
					<td></td>
					<td><input type="text" name="attributeList1" class="attributeList1" style="width: 130px;">
						<form:hidden path="rawMaterials[${i}].rawmaterialid" /></td>								
					<td><span></span>
						<form:hidden path="rawMaterials[${i}].supplierid" /></td>
					<td><form:input path="rawMaterials[${i}].netweight" class="cash mini" /></td>
					<td><span></span>
						<select name="rawMaterials[${i}].unit" id="rawMaterials${i}.unit"style="display:none;width:60px"></select>
						<input type="hidden"  id="unit${i}"/></td>
					<td><form:input path="rawMaterials[${i}].wastagerate" class="num small" />%</td>
					<td><span></span>
						<form:hidden path="rawMaterials[${i}].wastage" /></td>
					<td><span></span>
						<form:hidden path="rawMaterials[${i}].weight" /></td>
					<td><span></span>
						<input type="hidden"  id="rawPrice${i }"/>
						<form:hidden path="rawMaterials[${i}].convertunit"  /></td>	
					<td><span></span>
						<form:hidden path="rawMaterials[${i}].materialprice"  /></td>				
				</tr>
				
			</c:forEach>
			
		</tbody>
	</table>
	</div>
	</fieldset>
	<fieldset style="margin-top: -15px;">
	<legend style="margin: 10px 0px 0px 0px"> 人工成本</legend>
	<table class="form" style="text-align: center;margin-top: -5px;">
	
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
				<td><form:input path="price.hourprice" class="cash short labor"  value="12"/></td>
				<td><span id="laborprice"></span><form:hidden path="price.laborprice" /></td>				
			</tr>			
		</tbody>
	</table>
	<legend style="margin: 10px 0px 0px 0px"> 电耗</legend>
	<table class="form" style="text-align: center;">
	
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
		<button type="button" id="submitReturn" class="DTTT_button">保存</button>				
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
				url : "${ctx}/business/bom?methodtype=getMaterialPriceList",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(777);
					response($.map(data.data,function(item) {

						return {
							label : item.viewList,
							value : item.materialId,
							   id : item.materialId,
							 name : item.materialName,
							materialId : item.materialId,
							      unit : item.unit,
							  unitName : item.dicName,
							supplierId : item.supplierId,
							  minPrice : item.minPrice,
							 lastPrice : item.lastPrice,
							     price : item.price
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
			
			var $td = $(this).parent().parent().find('td');	
			var $oMatName   = $td.eq(2).find("span");
			var $oSupplier  = $td.eq(2).find("input:hidden");
			var $oUnit      = $td.eq(4).find("span");
			var $oSelect    = $td.eq(4).find("select");
			var $oUnithid   = $td.eq(4).find("input:hidden");
			var $oWestRate  = $td.eq(5).find("input");//损耗比
			var $oPricet    = $td.eq(8).find("span");
			var $oPriceh1   = $td.eq(8).find("input:hidden").eq(0);
			var $oTotals    = $td.eq(9).find("span");
			
			var unit     = ui.item.unit;
			var unitName = ui.item.unitName;
			//alert('unit:'+unit)
			switch (unitName){
			case '克':
			case '千克':
			case '吨':
				$oUnit.html('');//页面不显示
				$oUnithid.val(unitName);//临时存储
				$oSelect.html(optionWeight);
				$oSelect.val(unit);//显示当前的计量单位
				$oSelect.css("display", "block");				
				break;
			case '米':
			case '厘米':
				$oUnit.html('');//页面不显示
				$oUnithid.val(unitName);//临时存储
				$oSelect.html(optionSize);
				$oSelect.val(unit);//显示当前的计量单位
				$oSelect.css("display", "block"); 
				break;
			default:
				$oUnit.html(unitName);//显示到页面
				$oUnithid.val(unitName);//
				$oSelect.html('');
				$oSelect.val('');
				$oSelect.css("display", "none");//下拉框不显示
			}
			
			$oMatName.html(jQuery.fixedWidth(ui.item.name,20));
			$oPricet.html(ui.item.price);
			$oPriceh1.val(ui.item.price);
			$oTotals .html('');//清空总价
			//产品编号
			$(this).parent().find("input:hidden").val(ui.item.materialId);
			$oWestRate.val('2');//损耗比默认值:2%
			$oSupplier.val(ui.item.supplierId);
			
		},		
	});
}

</script>

</body>
</html>
