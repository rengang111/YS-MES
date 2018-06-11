<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<!-- 标贴打印（待入库） -->
<%@ include file="../../common/common2.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/print.css" />
<script type="text/javascript">
	
	$(document).ready(function() {

		var materialName= '${head.materialName }';
		materialName = jQuery.fixedWidth(materialName,32);	
		$('#materialName').html(materialName);
		

		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#printDate").text(shortToday());
	});
	
	
</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="formModel" method="POST"
		id="formModel" name="formModel"  autocomplete="off">
			<table style="width:90%">
				<tr> 				
					<td>
						
						<!-- 入库单编号/耀升编号 -->
						<div id="foo">${contract.YSId }<br>
						<!-- 合同编号： -->
						${contract.contractId }<br>
						<!-- 物料编号： -->
						${head.materialId }<br>
						<!-- 物料编号： -->
						<span id="materialName">${head.materialName }</span>
						
						合同数量:&nbsp;${head.contractQuantity }<br>
						
						已入数量:&nbsp;${head.stockinQty }<br>
						
						本次数量:&nbsp;${head.quantityQualified }<br>
						
						打印日期:&nbsp;<span id="printDate"></span><br>
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

</body>
	
</html>
