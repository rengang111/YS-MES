<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>自制品基本数据-编辑</title>

<script type="text/javascript">

	var counter  = 0;

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
				}, {"className":"dt-body-right"	
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

		var $orawunit   = $tds.eq(4).find("input:hidden").eq(0);//原计量单位
		var $ochgunit   = $tds.eq(4).find("select");//换算后的计量单位$("select option:checked").text();
		var $ounittext  = $tds.eq(4).find("select option:checked");
		var $ounittexts = $tds.eq(4).find("span");
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
		var unittexts  = $ounittexts.text();
		var fnetweight = currencyToFloat($onetweight.val());
		var frawprice  = currencyToFloat($orawprice.val());
		
		//alert('unittext['+unittext+']'+'unittexts['+unittexts+']')
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
		if( unittext == '' )
			unittext = unittexts;	//没有下拉框,即为修改已有数据的情况
			
		for(var i=0;i<unitAaary.length;i++){
			var val = unitAaary[i][0];//取得计算单位:100,1000...
			var key = unitAaary[i][1];//取得显示单位:克,吨...
			if(unittext == key){
				fchgunit = val;//只有在需要换算的时候,才设置换算单位
				//alert('自制品的用量单位'+fchgunit)
				break;
			}
		}		
		var fwastRate = currencyToFloat(vwastRate);//损耗比
		var fwastage  = fnetweight * fwastRate / 100;//损耗
		var fweight   = fnetweight + fwastage;//总用量=净用量量+损耗
		
		var fkgprice = currencyToFloat( $okgprice.text() );
		if((vchgunit)){									//没有重新选择原材料,没有修改使用单位
			fkgprice = frawprice * farwunit / fchgunit; //换算后单价=原单价*原单位/新单位	
			
		}
		var vconvunit = fchgunit / farwunit;	//换算单位	
		var fpricenew = fkgprice * fweight ;//单位材料价=新单价*总用量	
		//alert('frawprice:'+frawprice+'--farwunit:'+farwunit+'--fchgunit:'+fchgunit+'--fpricenew:'+fpricenew);	

		var vnetweight = float5ToCurrency(fnetweight);
		var vwastage = float5ToCurrency(fwastage);	
		var vweight  = float5ToCurrency(fweight);		
		var vkgprice  = float4ToCurrency(fkgprice);
		var vpricenew = float4ToCurrency(fpricenew);
		
		//详情列表显示新的价格
		$onetweight.val(vnetweight);
		$owastages.html(vwastage);					
		$owastagei.val(vwastage);			
		$oweights.html(vweight);
		$oweighti.val(vweight);
		$okgprice.html(vkgprice);
		$omatprices.html(vpricenew);	
		$omatpricei.val(vpricenew);
		$oconvunit.val(vconvunit);	//换算单位

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

	$('#price\\.type').val('${price.type}');
	
	ajax();

	autocomplete();
	
	//设置光标项目
	$("#price\\.totalprice").attr('readonly', "true");
		
	//设置经管费率默认值:12%
	
	//$("#price\\.managementcostrate").val($("#price\\.managementcostrate option:eq(2)").val());
	$("#price\\.managementcostrate").val('${price.managementCostRate}');
	
	$("#goBack").click(function() {
			var url = "${ctx}/business/zzmaterial";
			location.href = url;		
		});
	
	$("#update").click(function() {
		if(inputCheck()){
			$('#ZZMaterial').attr("action", "${ctx}/business/zzmaterial?methodtype=updateB");
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
		var vlabor = float4ToCurrency(labor);		

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
		var fhourpwer = fkwatt * 1;
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
	
	costAcount();//页面加载完后,重新计算

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
	var vtotalPrice = float4ToCurrency(ftotalPrice);
	
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
	var vpwer = float4ToCurrency(pwer);

	$('#powerprice').html(vpwer);
	$('#price\\.powerprice').val(vpwer);

	//alert('fpwer:'+fhourpwer+'---fprice:'+fkwprice+'---fyield:'+fyield)
	
}//计算单位产品电价


</script>
</head>

<body>
<div id="container">
<div id="main">
	
<form:form modelAttribute="ZZMaterial" method="POST"
	id="ZZMaterial" name="ZZMaterial"   autocomplete="off">
		
	<form:hidden path="price.recordid" value="${price.zzRecordId }"/>
	
<fieldset>
	<legend>基本信息</legend>

	<table class="form">		
		<tr>
			<td class="label" style="width: 120px;">产品编号：</td>
			<td style="width: 150px;">${price.materialId }
				<form:hidden path="price.materialid" value="${price.materialId }"/></td>
								
			<td class="label" style="width: 120px;"><label>产品名称：</label></td>
			<td>${price.materialName }</td>
			<td class="label" style="width: 120px;"><label>计量单位：</label></td>
			<td style="width: 150px;">${price.dicName }</td>	
			
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
			<td><form:input path="price.totalprice" class="read-only cash short" value="${price.totalPrice }" /></td>					
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
			<c:forEach var="raw" items="${detail}" varStatus="i">		
					
				<tr>
					<td></td>
					<td>&nbsp;${raw.rawMaterialId }
						<form:hidden path="rawMaterials[${i.index}].rawmaterialid" value="${raw.rawMaterialId }" /></td>								
					<td><span id="rawMaterialName${i.index}"></span>
						<form:hidden path="rawMaterials[${i.index}].supplierid" value="${raw.supplierId }" /></td>
					<td><form:input path="rawMaterials[${i.index}].netweight" class="cash mini" value="${raw.netWeight }" /></td>							
				
					<td><span id="unitSpan${i.index}">${raw.viewUnit}</span>
						<input type="hidden"  id="orgUnit${i.index}" value="${raw.orgUnitName }"/>
						<input type="hidden"  name="rawMaterials[${i.index}].unit" id="rawMaterials${i.index}.unit" value="${raw.zzunit }"/></td>
					<td><form:input path="rawMaterials[${i.index}].wastagerate" value="${raw.wastageRate }" class="num small"  />%</td>											
					<td><span>${raw.wastage }</span>
						<form:hidden path="rawMaterials[${i.index}].wastage"  value="${raw.wastage }"  /></td>
					<td><span>${raw.weight }</span>
						<form:hidden path="rawMaterials[${i.index}].weight"  value="${raw.weight }" /></td>
					<td><span id="price${i.index}"></span>
						<input  id="rawPrice${i.index}" type="hidden"  value="${raw.price }"/>
						<form:hidden path="rawMaterials[${i.index}].convertunit" value="${raw.convertUnit }"/></td>
					<td><span>${raw.materialPrice }</span>
						<form:hidden path="rawMaterials[${i.index}].materialprice"  value="${raw.materialPrice }" /></td>				
				</tr>
				
					<form:hidden path="rawMaterials[${i.index}].recordid" value="${raw.recordId }" />
			
			<script type="text/javascript" >
					counter++;
					var index = '${i.index}';
					/*
					var $oUnit      = $('#unitSpan'+index);
					var $oSelect    = $('#rawMaterials'+index+'\\.unit');
					var $oOrgUnit   = $('#orgUnit'+index);
					var $oUsedUnit  = $('#usedUnit'+index);
					
					var orgUnitName = '${raw.orgUnitName}';//原材料的购买单位名称
					var unit     = '${raw.viewUnit}';
					//alert('unit:'+unit+'--unitName:'+unitName)
					switch (orgUnitName){
					case '克':
					case '千克':
					case '吨':
						$oUnit.html('');//页面不显示
						$oOrgUnit.val(orgUnitName);//临时存储
						$oUsedUnit.val(unit);//使用量单位,再次计算单价时,作为比较对象,判断所改变的单位是否和该值一致
						$oSelect.html(optionWeight);
						//$oSelect.val(unit);//显示当前的计量单位
						$oSelect.css("display", "block");	
						break;
					case '米':
					case '厘米':
						$oUnit.html('');//页面不显示
						$oOrgUnit.val(orgUnitName);//临时存储
						$oUsedUnit.val(unit);
						$oSelect.html(optionSize);
						//$oSelect.val(unit);//显示当前的计量单位
						$oSelect.css("display", "block"); 
						break;
					default:
						$oUnit.html(unit);//显示到页面
						$oOrgUnit.val(unit);//
						$oUsedUnit.val(unit);
						$oSelect.css("display", "none");//下拉框不显示
					}
			*/
					var name = '${raw.rawMaterialName }';
					$('#rawMaterialName'+index).html(jQuery.fixedWidth(name,25));
					
					var price  = currencyToFloat( '${raw.price }' );
					var convet = currencyToFloat( '${raw.convertUnit }' );
					var vprice = float4ToCurrency(price / convet);
					$('#price'+index).html(vprice);
				</script>
				
			</c:forEach>
		</tbody>
	</table>
	</div>
			<span id="raw"></span>
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
				<td><form:input path="price.cavitiesnumber" class="cash short labor"  value="${price.cavitiesNumber }"/></td>							
				<td><form:input path="price.time" class="cash short labor" value="${price.time }" /></td>	
				<td><span id="houryield">${price.hourYield }</span><form:hidden path="price.houryield" value="${price.hourYield }" /></td>				
				<td><form:input path="price.hourprice" class="cash short labor"  value="${price.hourPrice }"/></td>
				<td><span id="laborprice">${price.laborPrice }</span><form:hidden path="price.laborprice"  value="${price.laborPrice }" /></td>				
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
			//alert('unit:'+unit+'--unitName:'+unitName)
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
				$oUnithid.val(unitName);//临时存储
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
