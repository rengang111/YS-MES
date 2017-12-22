<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<!-- 入库单打印 -->
<%@ include file="../../common/common2.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/print.css" />
<script type="text/javascript">
	
	$(document).ready(function() {

		baseBomView();		
		
	});
	
	
</script>
</head>
<body>
<button type="button" id="print" onclick="doPrint();"class="DTTT_button " style="float: right;margin: 15px 20px -50px 0px;height: 40px;width: 70px;">打印</button>

<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">


	
		<table class="" id="table_form"  style="margin-top: -10px;width: 100%;">
			<tr> 				
				<td class="td-center" colspan="6" style="font-size: 26px;height: 50px;">料件入库单</td>
			</tr>
		</table>
		<table>
			<tr> 				
				<td class="label" width="100px">入库单编号：</td>					
				<td width="150px">${head.receiptId }</td>
				<td class="label" width="100px">入库时间：</td>					
				<td  colspan="3">${head.checkInDate }</td>				
			</tr>
			<tr>
				<td class="label" width="100px">耀升编号：</td>					
				<td>${contract.YSId }</td>			
				<td class="label">合同编号：</td>					
				<td class="td-left">${contract.contractId }</td>								 	
				<td class="label" width="100px">供应商：</td>					
				<td>${contract.supplierId }（${contract.shortName }）${contract.fullName }</td>	
			</tr>
			<tr> 							
				<td width="100px" class="label">成品编码：</td>
				<td class="td-left">${contract.productId }</td>							
				<td width="100px" class="label">成品名称：</td>
				<td colspan="3">${contract.productName }</td>
			</tr>
										
		</table>
		
		<div class="list">	
			<table id="orderBomTable" class="display orderBomTable" >
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th style="width:100px">物料编号</th>
						<th>物料名称</th>
						<th style="width:65px">合同数量</th>
						<th style="width:60px">单价</th>
						<th style="width:65px">合同金额</th>
						<th style="width:65px">本次入库</th>
						<th style="width:50px">包装</th>
						<th style="width:60px">库位</th>	
					</tr>
				</thead>
				<tbody>
					<c:forEach var="detail" items="${material}" varStatus='status' >
						<tr>
							<td>${status.index + 1 }</td>
							<td>${detail.materialId }</td>
							<td><div id="name${status.index }" >${detail.materialName }</div></td>
							<td>${detail.contractQuantity }</td>
							<td>${detail.price }</td>
							<td>${detail.contractTotalPrice }</td>
							<td>${detail.quantity }</td>
							<td>${detail.packaging }</td>
							<td>${detail.areaNumber }</td>
						</tr>
					
					<script  type="text/javascript">
						var index = '${status.index}';
						var name = '${detail.materialName }';
						
						if (lengthB(name) > 50){							
							name = '<span style="font-size:10px">' + name + '</sapn>'
						}
						$('#name'+index).html(name);
					</script>
				</c:forEach>
				</tbody>	
			</table>
		</div>
	
	</form:form>
</div>
</div>


<script  type="text/javascript">

function baseBomView() {

	
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
			{"className" : 'td-left'},
			{},
			{"className" : 'td-right'},
			{"className" : 'td-right'},
			{"className" : 'td-right'},
			{"className" : 'td-right'},
			{"className" : 'td-center'},
			{"className" : 'td-center'},
		 ],
		"columnDefs":[{
			"visible" : false,
			"targets" : [4]
		}
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
	
}//ajax()供应商信息

function doPrint(){
	
	var headstr = "<html><head><title></title></head><body>";  
	var footstr = "</body>";
	var newstr = document.getElementById("main").innerHTML;  
	var oldstr = window.document.body.innerHTML;
	document.body.innerHTML = headstr+newstr+footstr;  
	window.print();
	document.body.innerHTML = oldstr;  
	
}
</script>
</body>
	
</html>
