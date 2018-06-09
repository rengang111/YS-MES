<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<!-- 标贴打印 -->
<%@ include file="../../common/common2.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/print.css" />
<script type="text/javascript">
	
	$(document).ready(function() {

		var materialName= '${contract.materialName }';
		materialName = jQuery.fixedWidth(materialName,32);	
		$('#materialName').html(materialName);
		
		//baseBomView();		
		
	});
	
	
</script>
</head>
<body>
<!-- 
<button type="button" id="print" onclick="doPrint();"class="DTTT_button " style="float: right;margin: 15px 20px -50px 0px;height: 40px;width: 70px;">打印</button>
 -->
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">
			<table style="width:90%">
				<tr> 				
					<td>
						
						<!-- 入库单编号/耀升编号 -->
						<div id="foo">${contract.receiptId }/${contract.YSId }<br>
						<!-- 合同编号： -->
						${contract.contractId }<br>
						<!-- 物料编号： -->
						${contract.materialId }<br>
						<!-- 物料编号： -->
						<span id="materialName">${contract.materialName }</span>
						
						合同数量:&nbsp;${contract.contractQty }<br>
						
						已入数量:&nbsp;${contract.stockinQty }<br>
						
						本次数量:&nbsp;${contract.quantity }<br>
						
						入库日期:&nbsp;${contract.checkInDate }<br>
						</div>
					</td>
					<td>
						<button type="button" class="btn" style="width:150px"
							data-clipboard-action="copy" 
							data-clipboard-target="#foo">复制到剪贴板</button>
					
					</td>				
							
				</tr>				
			</table>
		</form:form>
	</div>
</div>

<script  type="text/javascript">
    var clipboard = new ClipboardJS('.btn');

    clipboard.on('success', function(e) {
        console.log(e);
    });

    clipboard.on('error', function(e) {
        console.log(e);
    });
</script>
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
