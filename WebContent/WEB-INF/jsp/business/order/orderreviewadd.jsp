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
<title>订单详情-录入</title>
<%@ include file="../../common/common2.jsp"%>
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
		documentaryAjax2();
		documentaryAjax3();
		documentaryAjax4();
		
		
		baseBomView();//显示基础BOM
		
		$('#bomPlan\\.rebaterate').val('17');//初始值
		
		//外汇报价换算
		$(".exchange").change(function() {
			
			costAcount();
		});

		$("#goBack").click(
				function() {
					var url = "${ctx}/business/order";
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					
			$('#bomForm').attr("action", "${ctx}/business/bom?methodtype=insertOrderBom");
			$('#bomForm').submit();
		});
		
		$("#requirement").click(
				function() {
			var YSId = '${order.YSId}';
			var materialId='${order.materialId}';
			var order = currencyToFloat( '${order.quantity}' );
			$('#bomForm').attr("action", "${ctx}/business/requirement?methodtype=create&YSId="+YSId+"&materialId="+materialId+"&order="+order);
			$('#bomForm').submit();
		});
		
		$("#bomPlan\\.exchangerate").blur(function() {
			var rate = $(this).val();
			var currencyId = '${order.currencyId}';
			//alert(currencyId)
			//return;

			var url = "${ctx}/business/orderreview?methodtype=updateExchangeRate";
			url = url + "&currencyId="+currencyId+"&rate="+rate;

			$.ajax({
				type : "post",
				url : url,
				//async : false,
				//data : null,
				dataType : "text",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {			

					$().toastmessage('showNoticeToast', "汇率保存成功。");	
					//$("#costRate").attr('readonly',true);
					//$("#costRate").addClass('read-only');
					//$("#doSave").hide();
					//$("#doEdit").show();
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					//alert(textStatus)
				}
			});	
		});
		
		$("input:text").focus (function(){
		    $(this).select();
		});

		$(".DTTT_container").css('float','left');

		$( "#tabs" ).tabs();
	});

</script>
<script type="text/javascript">

function documentaryAjax() {

	var t = $('#expense1').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
		"async"		: false,
        "ordering"  : false,
		//"sAjaxSource" : actionUrl,
		"dom"		: '<"clear">lt',
		"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
	     "columns" : [
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-right'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			],
		
	})
		
	t.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
		
	});

};//ajax()
</script>
<script type="text/javascript">
function documentaryAjax2() {

	var t = $('#expense2').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
		"async"		: false,
        "ordering"  : false,
		"dom"		: '<"clear">lt',
		"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
	     "columns" : [
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-right'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			],
		
	})
		
	t.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
		
	});

};//ajax()
</script>
<script type="text/javascript">
function documentaryAjax3() {

	var t = $('#expense3').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
		"async"		: false,
        "ordering"  : false,
		"dom"		: '<"clear">lt',
		"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
	     "columns" : [
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-right'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			],
		
	})
		
	t.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
		
	});

};//ajax()
</script>
<script type="text/javascript">
function documentaryAjax4() {

	var t = $('#expense4').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
		"async"		: false,
        "ordering"  : false,
		"dom"		: '<"clear">lt',
		"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
	     "columns" : [
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-right'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			    {"className" : 'td-center'},
	  			],
		
	})
		
	t.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
		
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
		
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:150px;">${order.YSId}
						<form:hidden path="bomPlan.ysid" value="${order.YSId}"/></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;">${order.materialId}
						<form:hidden path="bomPlan.materialid" value="${order.materialId}"/></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td>&nbsp;${order.materialName}</td>
				</tr>
				<tr>
					<td class="label"><label>ＰＩ编号：</label></td>
					<td>${order.PIId}</td>

					<td class="label"><label>订单交期：</label></td>
					<td>${order.deliveryDate}</td>
						
					<td class="label"><label>客户名称：</label></td>
					<td>${order.customerFullName}</td>
				</tr>							
			</table>
		</fieldset>
		<fieldset>
			<legend> 订单核算</legend>
			<table class="form" id="table_form2">
				<tr>
					<td class="td-center" width="150px"><label>BOM编号</label></td>
					<td class="td-center"><label>材料成本</label></td>
					<td class="td-center"><label>人工成本</label></td>
					<td class="td-center"><label> BOM成本</label></td>
					<td class="td-center"><label>基础成本</label></td>
					<td class="td-center"><label>经管费率</label></td>
					<td class="td-center"><label>核算成本</label></td>
				</tr>
				<tr>
					<td class="td-center"><span id="bomId">${bomForm.bomPlan.bomid }</span>
						<form:hidden path="bomPlan.bomid"  />
						<form:hidden path="bomPlan.parentid"  />
						<form:hidden path="bomPlan.subid"  /></td>
					<td class="td-center"><span id="materialCost"></span></td>
					<td class="td-center"><span id="laborCost"></span></td>
					<td class="td-center"><span id="bomCost"></span></td>
					<td class="td-center"><span id="baseCost"></span></td>
					<td class="td-center"><span id="costRate"></span>%</td>
					<td class="td-center"><span id="totalCost"></span></td>
				</tr>								
			</table>
			
			<table class="form" id="table_form3" >
				
				<tr>
					<td class="td-center"><label>下单日期</label></td>
					
					<td class="td-center"><label>销售单价</label></td> 
					<!-- <td class="td-center"><label>币种</label></td>-->
					<td class="td-center"><label>换汇</label></td>
					<td class="td-center"><label>原币价格</label></td>
					<td class="td-center"><label>销售税</label></td>
					<td class="td-center"><label>退税率</label></td>
					<td class="td-center"><label>退税</label></td>
					<td class="td-center"><label>利润</label></td>
					<td class="td-center"><label>利润率</label></td>
				</tr>	
				<tr>			
					<td class="td-center">${order.orderDate}
						<form:hidden path="bomPlan.plandate"  class="read-only short" value="${order.orderDate}"/></td>
					<td class="td-center"><span id="orderPrice">${order.price}</span></td>
					<!-- <td class="td-center">${order.currency}</td> -->
					<td class="td-center">
						<form:input path="bomPlan.exchangerate" class="cash exchange mini"  value="${order.sysValue}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.rmbprice" class="read-only cash short" /></td>
					<td class="td-center">
						<form:input path="bomPlan.salestax" class="read-only cash short" /></td>
					<td class="td-center">
						<form:input path="bomPlan.rebaterate" class="cash exchange mini"  />%</td>
					<td class="td-center">
						<form:input path="bomPlan.rebate" class="read-only cash mini"  /></td>
					<td class="td-center">
						<form:input  path="bomPlan.profit" class="read-only cash mini" /></td>
					<td class="td-center">
						<form:input  path="bomPlan.profitrate" class="read-only cash mini" />%</td>
				</tr>								
			</table>
			
			<table class="form" id="table_form2" style="margin-top: -3px;height:70px">
				
				<tr style="vertical-align: bottom;">
					<td class="td-center" width="150px"><label>单位销售毛利</label></td>	
					<td class="td-center" width="150px"><label>单位核算毛利</label></td>
					<td class="td-center" width="150px"><label>订单数量</label></td>
					<td class="td-center" style="font-weight: bold;"><label>销售毛利</label></td>	
					<td class="td-center" style="font-weight: bold;"><label>核算毛利</label></td>
					<td class="td-center" style="font-weight: bold;"><label>贸易净利</label></td>
				</tr>	
				<tr>			
					<td class="td-center"><span id="unitSale"></span></td>
					<td class="td-center"><span id="unitAdjust"></span></td>
					<td class="td-center"><span id="quantity">${order.quantity}</span>&nbsp;（${order.unit}）</td>
					<td class="td-center" style="font-weight: bold;"><span id="sale"></span></td>
					<td class="td-center" style="font-weight: bold;"><span id="adjust"></span></td>
					<td class="td-center" style="font-weight: bold;"><span id="netAdjust"></span></td>
				</tr>								
			</table>
		</fieldset>	
		
		<fieldset class="action" style="text-align: right;">
			<!-- button type="button" id="insert" class="DTTT_button">保存</button> -->
			<button type="button" id="requirement" class="DTTT_button">订单采购需求</button>
			<button type="button" id="goBack" class="DTTT_button">返回</button>
		</fieldset>	
	
		<fieldset>
			<div id="tabs" style="padding: 0px;">
				<ul>
					<li><a href="#tabs-1">跟单费用</a></li>
					<li><a href="#tabs-2">客户增减</a></li>
					<li><a href="#tabs-3">工厂增减</a></li>
					<li><a href="#tabs-4">车间增减</a></li>
				</ul>

				<div id="tabs-1" style="padding: 5px;">
					<table id="expense1" class="display" style="width:100%">
						<thead>				
						<tr>
							<th style="width:60px">No</th>
							<th class="dt-center" style="width:200px">费用名称</th>
							<th class="dt-center" style="width:150px">金额</th>
							<th class="dt-center" style="width:100px">报销人</th>
							<th class="dt-center" style="width:100px">报销日期</th>
							<th class="dt-center" style="width:100px">状态</th>
						</tr>
						</thead>
						<tbody>
			<c:set var="index" value="0" />
			<c:forEach var="detail" items="${orderExpense}" varStatus='stat' >	
				<c:if test="${detail.type eq 'D'}" >				
				<tr>
					<td>${index +1}</td>
					<td>${detail.costName }</td>
					<td id="d${index}"></td>
					<td>${detail.person }</td>
					<td>${detail.quotationDate }</td>
					<td>
					<c:if test="${detail.status eq '1'}" >
						已确认
					</c:if>
					<c:if test="${detail.status ne '1'}" >
						<a href="###" id="check1" onclick="doCheck1(1,'${index }','${detail.recordId }');">待确认</a>
					</c:if>
					</td>
					<script type="text/javascript">
						var index = '${index}';	
						$('#d'+index).html(fomatToColor('${detail.cost }'));
					</script>
					<c:set var="index" value="${index+1}" />
				</tr>		
						</c:if>	
			</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th></th>
								<th class="dt-right">合计</th>
								<th class="dt-right"><span id="doctCost1"></span></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot>
					</table>
					<div class="action" style="text-align: right;">
						
					</div>	
				</div>
	
				<div id="tabs-2" style="padding: 5px;">
					<table id="expense2" class="display" style="width:100%">
						<thead>				
						<tr>
							<th style="width:60px">No</th>
							<th class="dt-center" style="width:200px">费用名称</th>
							<th class="dt-center" style="width:150px">金额</th>
							<th class="dt-center" style="width:100px">报销人</th>
							<th class="dt-center" style="width:100px">报销日期</th>
							<th class="dt-center" style="width:100px">状态</th>
						</tr>
						</thead>

			<c:set var="index" value="0" />
			<c:forEach var="detail" items="${orderExpense}" varStatus='stat' >	
				<c:if test="${detail.type eq 'C'}" >		
					<tr>
						<td>${index+1 }</td>
						<td>${detail.costName }</td>
						<td id="c${index}"></td>
						<td>${detail.person }</td>
						<td>${detail.quotationDate }</td>
						<td>
						<c:if test="${detail.status eq '1'}" >
							已确认
						</c:if>
						<c:if test="${detail.status ne '1'}" >
							<a href="###" id="check1" onclick="doCheck1(2,'${index }','${detail.recordId }');">待确认</a>
						</c:if>
						</td>
					</tr>
					<script type="text/javascript">
						var index = '${index}';	
						$('#c'+index).html(fomatToColor('${detail.cost }'));
					</script>
					<c:set var="index" value="${index+1}" />
				</c:if>			
			</c:forEach>
						<tfoot>
							<tr>
								<th></th>
								<th class="dt-right">合计</th>
								<th class="dt-right"><span id="doctCost2"></span></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>		
				<div id="tabs-3" style="padding: 5px;">
					<table id="expense3" class="display" style="width:100%">
						<thead>				
						<tr>
							<th style="width:60px">No</th>
							<th class="dt-center" style="width:200px">费用名称</th>
							<th class="dt-center" style="width:150px">金额</th>
							<th class="dt-center" style="width:100px">报销人</th>
							<th class="dt-center" style="width:100px">报销日期</th>
							<th class="dt-center" style="width:100px">状态</th>
						</tr>
						</thead>

			<c:set var="index" value="0" />
			<c:forEach var="detail" items="${orderExpense}" varStatus='stat' >	
				<c:if test="${detail.type eq 'S'}" >		
					<tr>
						<td>${index +1}</td>
						<td>${detail.costName }</td>
						<td id="s${index}"></td>
						<td>${detail.person }</td>
						<td>${detail.quotationDate }</td>
						<td>
						<c:if test="${detail.status eq '1'}" >
							已确认
						</c:if>
						<c:if test="${detail.status ne '1'}" >
							<a href="###" id="check1" onclick="doCheck1(3,'${index }','${detail.recordId }');">待确认</a>
						</c:if>
						</td>
					</tr>
					<script type="text/javascript">
						var index = '${index}';	
						$('#s'+index).html(fomatToColor('${detail.cost }'));
					</script>
					<c:set var="index" value="${index+1}" />
				</c:if>			
			</c:forEach>
						<tfoot>
							<tr>
								<th></th>
								<th class="dt-right">合计</th>
								<th class="dt-right"><span id="doctCost3"></span></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>		
				<div id="tabs-4" style="padding: 5px;">
					<table id="expense4" class="display" style="width:100%">
						<thead>				
						<tr>
							<th style="width:60px">No</th>
							<th class="dt-center" style="width:200px">增减内容</th>
							<th class="dt-center" style="width:150px">金额</th>
							<th class="dt-center" style="width:100px">报销人</th>
							<th class="dt-center" style="width:100px">报销日期</th>
							<th class="dt-center" style="width:100px">状态</th>
						</tr>
						</thead>

			<c:set var="index" value="0" />
			<c:forEach var="detail" items="${orderExpense}" varStatus='stat' >	
				<c:if test="${detail.type eq 'W'}" >			
					<tr>
						<td>${index+1 }</td>
						<td>${detail.costName }</td>
						<td id="w${index}"></td>
						<td>${detail.person }</td>
						<td>${detail.quotationDate }</td>
						<td>
						<c:if test="${detail.status eq '1'}" >
							已确认
						</c:if>
						<c:if test="${detail.status ne '1'}" >
							<a href="###" id="check1" onclick="doCheck1(4,'${index }','${detail.recordId }');">待确认</a>
						</c:if>
						</td>
					</tr>
					<script type="text/javascript">
						var index = '${index}';	
						$('#w'+index).html(fomatToColor('${detail.cost }'));
					</script>
					
					<c:set var="index" value="${index+1}" />
				</c:if>			
			</c:forEach>
						<tfoot>
							<tr>
								<th></th>
								<th class="dt-right">合计</th>
								<th class="dt-right"><span id="doctCost4"></span></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>

			</div>
			
		</fieldset>	
		
		<dl class="collapse" style="width: 98%;margin-left:10px">
		<dt><span id="bomId">BOM详情</span>
			</dt>
		<dd>
		<table id="baseBomTable" class="display" style="width: 98%;">
			<thead>				
				<tr>
					<th width="1px">No</th>
					<th class="dt-center" width="150px">物料编码</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" width="30px">单位</th>
					<th class="dt-center" width="80px">供应商编号</th>
					<th class="dt-center" width="60px">用量</th>
					<th class="dt-center" width="80px">单价</th>
					<th class="dt-center" width="100px">总价</th>
				</tr>
			</thead>
			
		</table>
		</dd>
	</dl>
<div style="clear: both"></div>		
</form:form>

</div>
</div>
<script type="text/javascript">
//列合计:总价
function productCostSum(){

	var sum = 0;
	$('#baseBomTable tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(7).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

//列合计:人工成本
function laborCostSum(){

	var sum = 0;
	$('#baseBomTable tbody tr').each (function (){
		
		var materialId = $(this).find("td").eq(1).text();
		var vtotal = $(this).find("td").eq(7).text();

		//判断是否是人工成本
		if(materialId != '' && materialId.substring(0,1) == 'H'){
			var ftotal = currencyToFloat(vtotal);
			sum = currencyToFloat(sum) + ftotal;	
		}
		
				
	})		
	return sum;
}

//列合计:跟单费用
function docCostSum1(){

	var sum = 0;
	$('#expense1 tbody tr').each (function (){
		
		//var vtotal = $(this).find("td").eq(2).find("input").val();
		var vtotal = $(this).find("td").eq(2).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

function docCostSum2(){

	var sum = 0;
	$('#expense2 tbody tr').each (function (){
		
		//var vtotal = $(this).find("td").eq(2).find("input").val();
		var vtotal = $(this).find("td").eq(2).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

function docCostSum3(){

	var sum = 0;
	$('#expense3 tbody tr').each (function (){
		
		//var vtotal = $(this).find("td").eq(2).find("input").val();
		var vtotal = $(this).find("td").eq(2).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

function docCostSum4(){

	var sum = 0;
	$('#expense4 tbody tr').each (function (){
		
		//var vtotal = $(this).find("td").eq(2).find("input").val();
		var vtotal = $(this).find("td").eq(2).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}
function netAdjustAccout(){
	var docCost1 = docCostSum1();
	var docCost2 = docCostSum2();
	var docCost3 = docCostSum3();
	var docCost4 = docCostSum4();
	var adjust = currencyToFloat( $('#adjust').text() );
	//alert('docCost:'+docCost+'--adjust:'+adjust)
	var netAdjust = adjust - docCost1 - docCost2 - docCost3 - docCost4 ;
	$('#doctCost1').html(fomatToColor(docCost1));
	$('#doctCost2').html(fomatToColor(docCost2));
	$('#doctCost3').html(fomatToColor(docCost3));
	$('#doctCost4').html(fomatToColor(docCost4));
	$('#netAdjust').html(fomatToColor(netAdjust));
	
}
//销售利润计算
function costAcount(){
	var exchang  = currencyToFloat($('#bomPlan\\.exchangerate').val());
	var exRebate = currencyToFloat($('#bomPlan\\.rebaterate').val());
	var exprice  = currencyToFloat($('#orderPrice').text());
	var mateCost = currencyToFloat($('#materialCost').text());
	var baseCost = currencyToFloat($('#baseCost').text());
	//原币价格=汇率*报价，利润率=（原币价格+退税-基础成本）/基础成本
	var rmb = exchang * exprice;
	var rebate = exRebate * mateCost / 1.17 / 100;	//退税
	var profit = rmb + rebate - baseCost ;			//利润
	var profitRate = profit / baseCost * 100;		//利润率
	//销售税=（销售单价(原币)-材料成本）*17%
	var salesTax = ( rmb - mateCost ) * exRebate / 100;
	
	var costRate =  currencyToFloat($('#costRate').text());
	var quantity = currencyToFloat($('#quantity').text());
	
	//单位销售毛利=销售单价（原币）-产品成本-销售税+退税
	//单位核算毛利=销售单价（原币）-产品成本-销售税+退税-经管费=单位销售毛利-经管费
	var unitSale = rmb - baseCost - salesTax + rebate;
	var unitAdjust = unitSale - baseCost * costRate / 100;
	var sale = unitSale * quantity;
	var adjust = unitAdjust * quantity;
	//alert('p='+(rmb - baseCost)+'profit='+profit)
	$('#bomPlan\\.salestax').val(floatToCurrency(salesTax));
	$('#bomPlan\\.rebate').val(floatToCurrency(rebate));
	$('#bomPlan\\.rmbprice').val(floatToCurrency(rmb));
	$('#bomPlan\\.profit').val(floatToCurrency(profit));
	$('#bomPlan\\.profitrate').val(floatToCurrency(profitRate));

	$('#unitSale').text(floatToCurrency(unitSale));
	$('#unitAdjust').text(floatToCurrency(unitAdjust));
	$('#sale').text(floatToCurrency(sale));
	$('#adjust').text(floatToCurrency(adjust));
	
	netAdjustAccout();//贸易净利	
}

</script>

<script  type="text/javascript">
function baseBomView() {

	var materialId='${order.materialId}';
	var table = $('#baseBomTable').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#baseBomTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/bom?methodtype=getBaseBom&materialId="+materialId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);
					
					$('#recordsTotal').val(data['recordsTotal']);

					var recordId  = data['data'][0]['productRecord'];
					var bomId     = data['data'][0]['bomId'];
					var parentId  = data['data'][0]['productParentId'];
					var costRote  = data['data'][0]['managementCostRate'];
					
					var laborCost = laborCostSum();
					var bomCost   = productCostSum();
					var mateCost  = bomCost - laborCost;
					var baseCost  = bomCost * 1.1;
					var totalCost = baseCost * ( 1 + costRote / 100 );

					mateCost  =  floatToCurrency( mateCost );
					bomCost   =  floatToCurrency( bomCost ) ;
					baseCost  =  floatToCurrency( baseCost ) ;
					totalCost =  floatToCurrency( totalCost );
					laborCost =  floatToCurrency( laborCost );

					$('#parentId').val(parentId);
					$('#bomId').html(bomId);
					$('#materialCost').html(mateCost);
					$('#laborCost').html(laborCost);
					$('#bomCost').html(bomCost);
					$('#baseCost').html(baseCost);
					//$('#totalCost').val(totalCost);
					$('#totalCost').html(totalCost);
					$('#costRate').html(costRote);
					$('#productRecordId').val(recordId);
					
					costAcount();//销售利润
					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "materialId"},
			{"data": "materialName"},
			{"data": "unit","className" : 'td-center'},
			{"data": "supplierId"},
			{"data": "quantity","className" : 'td-right'},
			{"data": "price","className" : 'td-right'},
			{"data": "totalPrice","className" : 'td-right'},
		 ],
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];				    			
    			name = jQuery.fixedWidth(name,40);				    			
    			return name;
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var materialId = row["materialId"];
    			rtn= "<a href=\"#\" onClick=\"doEditMaterial('" + row["rawRecordId"] +"','"+ row["parentId"] + "')\">"+materialId+"</a>";
    			return rtn;
    		}},
    		{"targets":4,"render":function(data, type, row){
    			var supplierId = row["supplierId"];
    			rtn= "<a href=\"#\" onClick=\"doShowSupplier('" + row["supplierId"] +"')\">"+supplierId+"</a>";
    			return rtn;
    		}},
    		{"targets":6,"render":function(data, type, row){
    			
    			var price =  row["price"] ;
    			var fprice = currencyToFloat( price );
    			var oldPrice = currencyToFloat( row["oldPrice"] );

				if(fprice > oldPrice){
					price = '<div style="font-weight:bold;color:red">' + price + '</div>';
				}else if(fprice < oldPrice){
					price = '<div style="font-weight:bold;color:green">' + price + '</div>';
					
				}
    			return price;
    		}},
    		{"targets":7,"render":function(data, type, row){
    			
    			var price = currencyToFloat( row["price"] );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var total = float4ToCurrency( price * quantity );			    			
    			return total;
    		}}
          
        ] 
	});
	
	t2.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t2.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});

	t2.on('order.dt search.dt draw.dt', function() {
		t2.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();

	
}//ajax()供应商信息

function doEditMaterial(recordid,parentid) {
			
	var url = '${ctx}/business/material?methodtype=detailView';
	url = url + '&parentId=' + parentid+'&recordId='+recordid;
	
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '520px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  baseBomView();
		  return false; 
		}    
	});		

};

function doEditDocumentary() {
	
	var url = '${ctx}/business/order?methodtype=documentaryEdit';
	var YSId = '${order.YSId}';
	url = url + '&YSId=' + YSId;
	
	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '800px', '300px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  documentaryShow();
		  return false; 
		}    
	});		

};
</script>

<script type="text/javascript">
function doCheck1(tableid,index,recordid) {
	
	var actionUrl = "${ctx}/business/bom?methodtype=CheckOrderCost1";

	$.ajax({
		type : 'POST',
		async: false,
		contentType : 'application/json',
		dataType : 'text',
		data : recordid,
		url : actionUrl,
		success : function(data) {
			$('#expense'+tableid+  ' tbody tr').eq(index).find("td").eq(5).html("已确认");
			
			$().toastmessage('showNoticeToast', "确认完毕!");	
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert(textStatus)
        }
	});
}
</script>
</body>
	
</html>
