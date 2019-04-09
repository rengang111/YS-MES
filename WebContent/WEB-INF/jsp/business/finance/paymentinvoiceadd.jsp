<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<%@ include file="../../common/common2.jsp"%>
<title>新增发票</title>
<script type="text/javascript">

	function checkNumber(str) { 
	   // var reg = /^\d+(,\d\d\d)*.\d+$/; 
	    //var reg = /^(\d+,?)+$/;
	    var reg = /^\d+(,\d\d\d)*.\d+$/;
	    str = $.trim(str)
	    if (str != "") { 
	        if (!reg.test(str)) {            
	            return false;
	        } else{
	        	return true;
	        }
	    }
	}
	
	$(document).ready(function() {
		
		$("#invoice\\.invoicedate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 
		
		$("#invoice\\.invoiceamount").focus(function(){
			$(this).val(currencyToFloat($(this).val()));
		   	$(this).select();
		});
		
		$("#invoice\\.invoiceamount") .blur(function(){
			
			var num = $(this).val();// 		
			var contractPrice = currencyToFloat('${contractPrice}');// 		
			var invoiceCnt = currencyToFloat('${invoiceCnt}');// 	invoiceCnt	
			
			var checkedNum = checkNumber(num);			
			if(checkedNum == false){
				$().toastmessage('showWarningToast', "请输入有效数字。");
				return;
			}

			thisNum = currencyToFloat(num);
			var maxNum = contractPrice - invoiceCnt;
			if(thisNum > maxNum){
				$().toastmessage('showWarningToast', "发票金额不能超过付款总额。");
				return;
			}
			
			$(this).val(floatToCurrency(num));
					
		});
		
		$("#return").click(function() {
					var index = parent.layer
							.getFrameIndex(window.name); //获取当前窗体索引
					parent.layer.close(index); //执行关闭
		});
		
		
		$("#insert").click(function() {


			var num    = $('#invoice\\.invoicenumber').val();
			var date   = $('#invoice\\.invoicedate').val();
			var type   = $('#invoice\\.invoicetype').val();
			var amount = $('#invoice\\.invoiceamount').val();
			
			if($.trim(amount) ==''){
				$().toastmessage('showWarningToast', "请输入发票金额。");
				return;				
			}
			if( type !='030' ){//选择有发票
				if($.trim(num) =='' ){
					$().toastmessage('showWarningToast', "请输入发票编号。");
					return;
				}
				if($.trim(date) ==''){
					$().toastmessage('showWarningToast', "请输入发票日期。");
					return;				
				}
			}else{
				if($.trim(num) !='' ){
					$().toastmessage('showWarningToast', "有发票编号，请选择发票类型。");
					return;
				}
				if($.trim(num) !='' && $.trim(date) ==''){
					$().toastmessage('showWarningToast', "有发票编号，请输入发票日期。");
					return;				
				}
			}

			$("#insert").attr("disabled", "disabled");

			$.ajax({
				async:false,
				type : "POST",
				url : "${ctx}/business/payment?methodtype=insertPyamentInvoice",
				data : $('#formModel').serialize(),// 要提交的表单
				success : function(d) {

					var retValue = d['message'];
					if (retValue != "failure") {
						//alert(retValue);
						parent.$('#handle_status').val('1');
					} else {
						// 从体验度来说，不要成功提示似乎操作更流畅。
						 parent.$('#handle_status').val('2');
					}
					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引	
					parent.invoiceAjax();//刷新供应商单价信息
					parent.layer.close(index); //执行关闭	
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {						

					if (XMLHttpRequest.status == "800") {
						alert("800"); //请不要重复提交！
					} else {
						alert("发生系统异常，请再试或者联系系统管理员。"); 
					}
					//关闭窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引		
					parent.layer.close(index); //执行关闭					

				}
			});
			
		});

	});
	
</script>
</head>
<body class="noscroll">
<div id="layer_main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

		<form:hidden path="invoice.recordid" />
		<form:hidden path="invoice.paymentid"  value="${paymentId }"/>

		<fieldset>
			<legend> 发票信息</legend>
			<table class="form" id="table_form2">
				<tr>
					<td class="label" width="100px">付款总金额：</td> 
					<td width="200px"><span id="contractPrice" class="font16">${contractPrice }</span></td>
						
					<td class="label" width="100px">已申请付款金额：</td> 
					<td width="200px"><span id="invoiceCnt" class="font16">${invoiceCnt }</span></td>
				</tr>
				<tr>
					<td class="label" width="100px">发票金额：</td> 
					<td width="200px"><form:input path="invoice.invoiceamount"  class="num"  value=""/></td>
						
					<td class="label" width="100px">发票日期：</td> 
					<td width="200px"><form:input path="invoice.invoicedate"  class="short"  value=""/></td>
				</tr>
				<tr>
					<td class="label" width="100px">发票类型： </td>
					<td width="150px">
						<form:select path="invoice.invoicetype" style="width: 120px;" value="">							
						<form:options items="${invoiceTypeOption}" 
							itemValue="key" itemLabel="value" /></form:select> </td>
				
					<td class="label" width="100px">发票编号：</td> 
					<td width="200px"><form:input path="invoice.invoicenumber"  class="middle"  value=""/></td>
				</tr>												
			</table>
		</fieldset>

		<fieldset style="text-align: right;">
			<button type="button" id="return" class="DTTT_button">关闭</button>
			<button type="button" id="insert" class="DTTT_button">保存</button>
		</fieldset>
	</form:form>
</div>
</body>
</html>
