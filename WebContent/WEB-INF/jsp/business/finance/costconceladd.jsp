<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../../common/common2.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>取消财务核算</title>

</head>
<body>

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
<div id="" style="vertical-align: middle;display: table-cell;height:130px;width:300px">	
		
		<input type="hidden" id="YSId" value="${YSId }" >		
		<input type="hidden" id="costFlag" name="costFlag" value="11" >		
			
		<div style="text-align: center;">
			请输入确认码：
			<input type="password" id="pwd" class=""  />
		</div>
		<br><br>
		<div style="text-align: center;">
			<button type="button" id="concelCost" class="DTTT_button">不参与核算</button>
			<button type="button" id="close" class="DTTT_button" >关闭</button>
		</div>
	</div>
</form:form>
<script type="text/javascript">

$(document).ready( function () {  
	
    //提交，最终验证。
     $("#concelCost").click(function() {

			
			var ysid = $('#YSId').val();
			//var url = "${ctx}/business/financereport?methodtype=cancelCost"+"&YSId="+ysid;

			var pwd = $('#pwd').val();
			
			if(pwd == '123456'){

		    	 $("#concelCost").attr("disabled", true);
			}else{
				$().toastmessage('showWarningToast', "确认码不正确，请重试。");
				return;
			}
			
			$.ajax({
				async:false,
				type : "POST",
				url : "${ctx}/business/financereport?methodtype=cancelCost"+"&YSId="+ysid,
				data : $('#form').serialize(),// 要提交的表单
				success : function(d) {
					
					var rtnMsg = d['message'];
					
					if(rtnMsg == '操作成功'){
						parent.$('#costConcelFlag').val('F');//取消核算						
					}

					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引	
					//parent.supplierPriceView();//刷新供应商单价信息
					parent.layer.close(index); //执行关闭	
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					alert(XMLHttpRequest.status);							
					//alert(XMLHttpRequest.readyState);							
					//alert(textStatus);							
					//alert(errorThrown);							

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
    
    
     $("#close").click(function() {

			//alert(999);

			var index = parent.layer
					.getFrameIndex(window.name); //获取当前窗体索引

			parent.layer.close(index); //执行关闭

	});
});

</script>
</body>

</html>