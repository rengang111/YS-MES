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
<title></title>
<%@ include file="../../common/common2.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/print.css" />
<script type="text/javascript">

	var counter  = 0;
	
	$(document).ready(function() {
		
		
		baseBomView();//订单BOM
		
		var total2 = productCostSum();
		$('#total2').html(floatToCurrency(total2));
		var total = Arabia_to_Chinese(total2);
		$('#total').html(total);
		
		$('#expense').hide();
		expenseAjax3();//订单过程扣款明细
		
	});
	
	
</script>
</head>
<body>
<button type="button" id="print" onclick="doPrint();"class="DTTT_button " style="float: right;margin: 15px 20px -50px 0px;height: 40px;width: 70px;">打印</button>

<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">

	<c:forEach var="detail" items="${contractList}" varStatus='status' step="4">
	
		<table class="" id="table_form"  style="margin-top: -10px;">
			<tr> 				
				<td class="td-center" colspan="6" style="font-size: 24px;">采购订单</td>
			</tr>
			<tr> 				
				<td class="label" style="width:100px;">耀升编号11：</td>					
				<td style="width:400px;">${contract.YSId}</td>
								
				<td class="label" style="width:100px;">合同编号：</td>					
				<td style="width:200px;">${contract.contractId}</td>
							
				<td class="label" style="width:100px;"> 签单日期：</td>					
				<td>${contract.purchaseDate}</td>
			</tr>
			<tr> 				
				<td class="label" style="width:100px;">供方：</td>					
				<td>${contract.supplierName}</td>
							
				<td class="label" style="width:100px;"> 需方：</td>					
				<td colspan="3">宁波耀升工具实业有限公司</td>
			</tr>
			<tr> 				
				<td class="label" style="width:100px;">地址：</td>					
				<td>${contract.address}</td>
							
				<td class="label" style="width:100px;"> 地址：</td>					
				<td colspan="3">宁波镇海区蛟川街道俞范东路776弄11号</td>
			</tr>
			<tr> 				
				<td class="label" style="width:100px;">电话：</td>					
				<td>${contract.mobile}&nbsp;${contract.phone}&nbsp;传真：${contract.fax}</td>
							
				<td class="label" style="width:100px;">电话：</td>					
				<td colspan="3"> 0574-86365153 &nbsp;传真：0574-86656306</td>
			</tr>
			<tr> 				
				<td class="label" style="width:100px;">联系人：</td>					
				<td>${contract.userName}</td>
							
				<td class="label" style="width:100px;"> </td>					
				<td colspan="3"></td>
			</tr>
								
		</table>
		
		<div class="list">	
			<table id="orderBomTable" class="display orderBomTable" >
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th style="width:100px">ERP编码</th>
						<th>物料名称</th>
						<th style="width:50px">数量</th>
						<th style="width:40px">单价</th>
						<th style="width:30px">单位</th>
						<th style="width:60px">金额</th>
						<th style="width:70px">交货日期</th>
					</tr>
				</thead>
				<tbody>
				<c:set var="start" value="${status.index }" />
				<c:set var="end" value="${status.index +3}" />
				<c:forEach begin="${start }" end="${end }"  var="i" >  		
					<c:if test="${not empty  contractList[i] }">
						<tr>
							<td>${i + 1 }</td>
							<td>${contractList[i].materialId }</td>
							<td><div id="name${i }" >${contractList[i].description }</div></td>
							<td>${contractList[i].quantity }</td>
							<td>${contractList[i].price }</td>
							<td>${contractList[i].unit }</td>
							<td>${contractList[i].totalPrice }</td>
							<td>${contractList[i].deliveryDate }</td>
						</tr>
					</c:if>
					<script  type="text/javascript">
						var index = '${i}';
						var name = '${contractList[i].description }';
						
						if (lengthB(name) > 50){
							
							name = '<span style="font-size:9px">' + name + '</sapn>'
						}
						$('#name'+index).html(name);
					</script>
				</c:forEach>
				</tbody>
				
				<tfoot>
					<tr>
						<th colspan="8">
							<table style="width: 100%;">
								<tr>
									<td style="text-align:left ;"><span  class="font16">不经需方允许不得变动制作方案</span></td>
									<td style="text-align:center ;">
										金额总计：<span id="total2" class="total"></span>&nbsp;&nbsp;&nbsp;&nbsp;
										<span id="total" class="total"></span></td>
								</tr>
							</table>						
						</th>
					</tr>
				</tfoot>			
			</table>
			
		</div>
		<pre>
		<span style="">备注：以上配件具体要求为：需符合ROHS(2016最新标准),PAHS(多环芳烃),REACH(SVHC高关注度物质),NP(壬基酚)，SCCP(锻炼氯化石蜡)等环保要求！</span><br>
一、包装要求：内保装要保证单件产品不刮划擦伤，外包装要注意坚固不破损，必须在外箱上写明耀升编号，品名，数量及总箱数，以及联系人和电话，<br>
并能及时把发货清单传真给我司采购员。 <br>
二、合同履行地：需方仓库 特别注意：合同要双方盖章，严格履行交货期，延时10天以上将扣该10%的货款。<br>
三、免费数量：确保我司出货数量，供方须免费提供1%的余量给需方。或提供若干数量成品。<br>
四、结算方式及期限：凭本合同正本，送货单，在全部到货并经需方抽样检验合格的前提下，自需方收到供方开具正确的增值税发票后60天结算。<br>
五、解决合同纠纷方式：按合同法协商解决，协商不成立由需方所在地法院判决。合同制定地为法律管辖地。<br>
六、合同的生效：合同自供方收到3天之内生效。任何修改需要盖章书面确认。<br>
七、具体约束内容，详见《供应商尽责条例》。<br>		
		</pre>
		<br>
		<table style="width: 100%;">
			<tr>
				<td style="width:50%">供方盖章：</td>
				<td>需方盖章：</td>
			</tr>
		</table>
	<div style="page-break-before:always;"></div>
		
	</c:forEach>
	<div id="expense">
	<fieldset>
	<legend> 订单过程扣款明细</legend>
		
	 <div class="list">
		<table id="supplier" class="display" >
			<thead>				
				<tr>
					<th width="20px">No</th>
					<th class="dt-center" width="100px">供应商</th>
					<th class="dt-center" width="150px">合同编号</th>
					<th class="dt-center" width="200px">增减内容</th>
					<th class="dt-center" width="100px">金额</th>
					<th class="dt-center">备注</th>
				</tr>
				</thead>	
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td style="text-align: right;">扣款合计：</td>
						<td ><div id="supplierSum"></div></td>
						<td ></td>
					</tr>
				</tfoot>
		</table>
	</div>
	</fieldset>
	</div>
	</form:form>
</div>
</div>


<script  type="text/javascript">

//列合计:总价
function productCostSum(){

	var sum = 0;
	$('#orderBomTable tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(6).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
	
}
//列合计:自制品
function laborCostSum(){

	var sum = 0;
	$('#orderBomTable tbody tr').each (function (){
		
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

function costAcount(){
	//计算该客户的销售总价,首先减去旧的		
	//产品成本=各项累计
	//人工成本=H带头的ERP编号下的累加
	//材料成本=产品成本-人工成本
	//经管费=经管费率x产品成本
	//核算成本=产品成本+经管费
	var managementCostRate = '2';
	managementCostRate = currencyToFloat(managementCostRate) / 100;//费率百分比转换
	
	var laborCost = laborCostSum();
	var bomCost = productCostSum();
	
	var fmaterialCost = bomCost - laborCost;
	var productCost = bomCost * 1.1;		
	var ftotalCost = productCost * ( 1 + managementCostRate );

	$('#bomCost').html(floatToCurrency(bomCost));
	$('#productCost').html(floatToCurrency(productCost));
	$('#laborCost').html(floatToCurrency(laborCost));
	$('#materialCost').html(floatToCurrency(fmaterialCost));
	$('#totalCost').html(floatToCurrency(ftotalCost));

}


function baseBomView() {

	var bomId=$('#bomId').text();

	var t2 = $('.orderBomTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		dom : '<"clear">rt',
		
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"className" : 'td-center'},
			{},
			{},
			{"className" : 'td-right'},
			{"className" : 'td-right'},
			{"className" : 'td-center'},
			{"className" : 'td-right'},
			{"className" : 'td-center'},
		 ],
		"columnDefs":[
    		
    		
          
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
/*
	t2.on('order.dt search.dt draw.dt', function() {
		t2.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();
*/
	
}//ajax()供应商信息

function doPrint(){
	
	var headstr = '<html><head><title></title></head><body style="margin: 0px 0px 0px 0px;">';  
	var footstr = "</body>";
	var newstr = document.getElementById("main").innerHTML;
	//var cont = document.body.innerHTML;    
	//$("#print").addClass('noprint');      
	var oldstr = window.document.body.innerHTML;
	document.body.innerHTML = headstr+newstr+footstr;  
	window.print();
	document.body.innerHTML = oldstr;  
	
}
</script>
<script  type="text/javascript">
        function Arabia_to_Chinese(Num) {
            for (i = Num.length - 1; i >= 0; i--) {
                Num = Num.replace(",", "")//替换tomoney()中的“,”
                Num = Num.replace(" ", "")//替换tomoney()中的空格
            }
            Num = String(Num).replace("￥", "")//替换掉可能出现的￥字符
            if (isNaN(Num)) { //验证输入的字符是否为数字
                alert("请检查小写金额是否正确");
                return;
            }
            //---字符处理完毕，开始转换，转换采用前后两部分分别转换---//
            part = String(Num).split(".");
            newchar = "";
            //小数点前进行转化
            for (i = part[0].length - 1; i >= 0; i--) {
                if (part[0].length > 10) { alert("位数过大，无法计算"); return ""; } //若数量超过拾亿单位，提示
                tmpnewchar = ""
                perchar = part[0].charAt(i);
                switch (perchar) {
                    case "0": tmpnewchar = "零" + tmpnewchar; break;
                    case "1": tmpnewchar = "壹" + tmpnewchar; break;
                    case "2": tmpnewchar = "贰" + tmpnewchar; break;
                    case "3": tmpnewchar = "叁" + tmpnewchar; break;
                    case "4": tmpnewchar = "肆" + tmpnewchar; break;
                    case "5": tmpnewchar = "伍" + tmpnewchar; break;
                    case "6": tmpnewchar = "陆" + tmpnewchar; break;
                    case "7": tmpnewchar = "柒" + tmpnewchar; break;
                    case "8": tmpnewchar = "捌" + tmpnewchar; break;
                    case "9": tmpnewchar = "玖" + tmpnewchar; break;
                }
                switch (part[0].length - i - 1) {
                    case 0: tmpnewchar = tmpnewchar + "元"; break;
                    case 1: if (perchar != 0) tmpnewchar = tmpnewchar + "拾"; break;
                    case 2: if (perchar != 0) tmpnewchar = tmpnewchar + "佰"; break;
                    case 3: if (perchar != 0) tmpnewchar = tmpnewchar + "仟"; break;
                    case 4: tmpnewchar = tmpnewchar + "万"; break;
                    case 5: if (perchar != 0) tmpnewchar = tmpnewchar + "拾"; break;
                    case 6: if (perchar != 0) tmpnewchar = tmpnewchar + "佰"; break;
                    case 7: if (perchar != 0) tmpnewchar = tmpnewchar + "仟"; break;
                    case 8: tmpnewchar = tmpnewchar + "亿"; break;
                    case 9: tmpnewchar = tmpnewchar + "拾"; break;
                }
                newchar = tmpnewchar + newchar;
            }
            //小数点之后进行转化
            if (Num.indexOf(".") != -1) {
                if (part[1].length > 2) {
                    alert("小数点之后只能保留两位,系统将自动截段");
                    part[1] = part[1].substr(0, 2)
                }
                for (i = 0; i < part[1].length; i++) {
                    tmpnewchar = ""
                    perchar = part[1].charAt(i)
                    switch (perchar) {
                        case "0": tmpnewchar = "零" + tmpnewchar; break;
                        case "1": tmpnewchar = "壹" + tmpnewchar; break;
                        case "2": tmpnewchar = "贰" + tmpnewchar; break;
                        case "3": tmpnewchar = "叁" + tmpnewchar; break;
                        case "4": tmpnewchar = "肆" + tmpnewchar; break;
                        case "5": tmpnewchar = "伍" + tmpnewchar; break;
                        case "6": tmpnewchar = "陆" + tmpnewchar; break;
                        case "7": tmpnewchar = "柒" + tmpnewchar; break;
                        case "8": tmpnewchar = "捌" + tmpnewchar; break;
                        case "9": tmpnewchar = "玖" + tmpnewchar; break;
                    }
                    if (i == 0) tmpnewchar = tmpnewchar + "角";
                    if (i == 1) tmpnewchar = tmpnewchar + "分";
                    newchar = newchar + tmpnewchar;
                }
            }
            //替换所有无用汉字
            while (newchar.search("零零") != -1)
                newchar = newchar.replace("零零", "零");
            newchar = newchar.replace("零亿", "亿");
            newchar = newchar.replace("亿万", "亿");
            newchar = newchar.replace("零万", "万");
            newchar = newchar.replace("零元", "元");
            newchar = newchar.replace("零角", "");
            newchar = newchar.replace("零分", "");

            if (newchar.charAt(newchar.length - 1) == "元" || newchar.charAt(newchar.length - 1) == "角")
                newchar = newchar + "整"
          //  document.write(newchar);
            return newchar;

        }
    </script>
    
<script type="text/javascript">

function expenseAjax3() {//工厂（供应商）增减费用

	var table = $('#supplier').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var YSId = '${contract.YSId}';
		var contractId = '${contract.contractId}';
		var actionUrl = "${ctx}/business/bom?methodtype=getDocumentary&type=S&YSId="+YSId+"&contractId="+contractId;

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
			dom : '<"clear">lt',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "POST",
					"contentType": "application/json; charset=utf-8",
					"dataType" : 'json',
					"url" : sSource,
					"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
					success : function(data) {
						
						var recordsTotal = data["recordsTotal"];
						if(recordsTotal <= 0 ){
							$('#expense').hide();
						}else{
							$('#expense').show();
						}
						
						fnCallback(data);

						supplierSum();
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus)
					}
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			    {"data": null,"className" : 'td-center'},
			    {"data": "supplierId", "defaultContent" : '',"className" : 'td-center'},//供应商
			    {"data": "contractId", "defaultContent" : '',"className" : 'td-center'},//合同编号
			    {"data": "costName", "defaultContent" : '',"className" : 'td-left'},//增减内容3
			    {"data": "cost", "defaultContent" : '',"className" : 'td-right'},//金额4
			    {"data": "remarks", "defaultContent" : ''},//备注5
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){	
	    			var rownum = row["rownum"];
	    			return rownum;
	    		}}
		     ] ,
		})
		
		t.on('change', 'tr td:nth-child(5)',function() {
			
			var $tds = $(this).parent().find("td");
			var $cost = $tds.eq(4).find("input");//金额			
			$cost.val(floatToCurrency($cost.val()));
		});
	
};//供应商

//供应商
function supplierSum(){	
	var contract = 0;	
	$('#supplier tbody tr').each (function (){
		var contractValue = $(this).find("td").eq(4).text();//
		contractValue= currencyToFloat(contractValue);
		contract = contract + contractValue;
	});	
	$('#supplierSum').html(floatToCurrency(contract));
}
</script>

</body>
	
</html>
