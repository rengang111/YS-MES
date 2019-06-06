<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>新增合同扣款</title>
<script type="text/javascript">

var thisCount = 0;

	$(document).ready(function() {
		
		//设置允许领料最大数
		var dbThisQty = $('#recive\\.receivequantity').val();
		var orderQty = '${order.quantity}';
		dbThisQty = currencyToFloat(dbThisQty);
		orderQty = currencyToFloat(orderQty);
		
		thisCount = dbThisQty + orderQty;
		
		//设置选择项目的选中项	
		$("#recive\\.receivedate").val(shortToday());
		$("#recive\\.receivedate").attr('readonly', "true");
		$("#recive\\.receivedate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
		}); 
		
		
		$("#recive\\.receivequantity").focus(function(){
			$(this).val(floatToNumber($(this).val()));
		   	$(this).select();
		});
		
		$("#recive\\.receivequantity") .blur(function(){
			
			var num = $(this).val();// 		
			//var oldQty = currencyToFloat('${order.quantity}');// 	
			var oldQty = thisCount;
			
			var checkedNum = checkNumber(num);			
			if(checkedNum == false){
				$().toastmessage('showWarningToast', "请输入有效数字。");
				$(this).val('');
				return;
			}
			
			var thisNum = currencyToFloat(num);
			if(thisNum > oldQty){
				$().toastmessage('showWarningToast', "领料数量不能大于原订单数量。");
				$(this).val(floatToNumber(oldQty));
				return;
			}
			
			$(this).val(floatToNumber(num));
					
		});
		
		$("#return").click(function() {

					//alert(999);

					var index = parent.layer
							.getFrameIndex(window.name); //获取当前窗体索引

					parent.layer.close(index); //执行关闭

		});
		
		
		$("#submit").click(function() {

			if ($("#recive\\.receivetopiid").val() == "") {	
				$().toastmessage('showWarningToast', "请输入耀升编号。");
				$("#recive\\.receivetopiid").focus();
				return;
			}
			
			if ($("#recive\\.receivequantity").val() == "") {	
				$().toastmessage('showWarningToast', "请输入领料数量。");
				$("#recive\\.receivequantity").focus();
				return;
			}

			$('#counter3').val('1');
			var type = "S";
			var actionUrl = "${ctx}/business/bom?methodtype=insertContractDeduction&type="+type;

			$("#submit").attr("disabled", true);
						
			$.ajax({
				async:false,
				type : "POST",
				url  : actionUrl,
				data : $('#bomForm').serialize(),// 要提交的表单
				success : function(d) {

					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引	
					parent.expenseAjax3();//刷新供应商单价信息
					parent.layer.close(index); //执行关闭	
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					alert(XMLHttpRequest.status);							

					if (XMLHttpRequest.status == "800") {
						alert("800"); //请不要重复提交！
					} else {
						alert("发生系统异常，请再试或者联系系统管理员。"); 
					}
					//关闭窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引						
					//parent.reloadSupplier();
					parent.layer.close(index); //执行关闭					

				}
			});
			
		});

	});
	

	
</script>

</head>
<body class="noscroll">
<div id="layer_main">

	<form:form modelAttribute="bomForm" method="POST" 
	 id="bomForm" name="bomForm"  autocomplete="off">

		<form:hidden path="expense.recordid" />
		<form:hidden path="expense.ysid"  value="${YSId}"/>
		<form:hidden path="expense.type"  value="S"/><!-- 供应商，合同扣款 -->
		<form:hidden path="expense.contractid"  value="${contractId}"/>
		<form:hidden path="expense.supplierid"  value=""/>

		<fieldset>
			<legend> 成品领料</legend>

			<table class="form">

				<tr>
					<td width="80px" class="label"><label>耀升编号：</label></td>
					<td width="150px">${YSId}</td>						
					<td width="80px" class="label"><label>合同编号：</label></td>
					<td width="120px">${contractId}</td>
				</tr>
			</table>
			<table class="form">
				<tr>
					<td class="label" width="80px" >扣款金额：</td>

					<td width="150px" >
						<form:input path="expense.cost"
							class="required cash" style="width: 130px;"/></td>
					
				</tr>
				<tr>
					
					
					<td class="label" width="80px" ><label>扣款原因：</label></td>
					<td>
						<form:input path="expense.costname"
							class="required long" /></td>
				</tr>
				
			</table>
		</fieldset>

		<fieldset class="action">
			<button type="button" id="return" class="DTTT_button">关闭</button>
			<button type="submit" id="submit" class="DTTT_button">保存</button>
		</fieldset>

	</form:form>

</div>
</body>
</html>
