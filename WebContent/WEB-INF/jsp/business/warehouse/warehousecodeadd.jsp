<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>新建子分类</title>
<script type="text/javascript">

var thisCount = 0;

	$(document).ready(function() {
				
		var codeid = '${formModel.warehouse.codeid}';
		var code = '${formModel.warehouse.codename}';
		var codeView = '无';
		var codeView2 = '';
		if(codeid !=''){			
			codeView = codeid + '&nbsp;' + code;	
			codeView2 = '&nbsp;'+codeid;
		}
		$('#parentId').html(codeView);
		$('#parentId2').html(codeView2);
		$('#warehouse\\.codeid').val('');
		$('#warehouse\\.codetype').val('');
		$('#warehouse\\.codename').val('');
		$('#warehouse\\.remarks').val('');
		
		$("#warehouse\\.codeid").focus(function(){
		   	$(this).select();
		});
		
		$("#return").click(function() {
					//alert(999);
					var index = parent.layer
							.getFrameIndex(window.name); //获取当前窗体索引
					parent.layer.close(index); //执行关闭

		});
		
		
		$("#submit").click(function() {

			if ($("#warehouse\\.codeid").val() == "") {	
				$().toastmessage('showWarningToast', "请输入子分类编码。");
				$("#warehouse\\.codeid").focus();
				return;
			}

			$("#submit").attr("disabled", true);
						
			var parentCodeId = '${formModel.warehouse.codeid}';
			
			$.ajax({
				async:false,
				type : "POST",
				url : "${ctx}/business/warehouse?methodtype=addWarehouseCode"+"&parentCodeId="+parentCodeId,
				data : $('#formModel').serialize(),// 要提交的表单
				success : function(data) {

					var messege = data['message'];
					if(messege == 'SUCCESSMSG'){
						var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引	
						parent.reload();//刷新供应商单价信息
						parent.layer.close(index); //执行关闭	
					}else{
						alert("发生系统异常，请再试或者联系系统管理员。");
					}
					//
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
					alert("发生系统异常，请再试或者联系系统管理员。"); 									

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

		<form:hidden path="warehouse.recordid" />
		<form:hidden path="warehouse.multilevel" />
		<form:hidden path="warehouse.sortno" />
		<input type="hidden" id="oldQuantity" />		

		<fieldset>
			<legend> 仓库编码</legend>

			<table class="form">

				<tr>
					<td width="100px" class="label"><label>父级分类：</label></td>
					<td>&nbsp;<span id="parentId">无${formModel.warehouse.codename}</span></td>
				</tr>
				<tr>		
					<td width="80px" class="label"><label>子分类编号：</label></td>
					<td><span id="parentId2"></span><form:input path="warehouse.codeid" class="required short" value=""/>
						<span style="color: blue">（编码规则：子分类编码=父级分类+输入内容,"-"符号需要手动输入）</span></td>
				</tr>
				<tr>		
					<td class="label">产品类别：</td>
					<td><form:input path="warehouse.codetype" class="" style=""/></td>
				</tr>
				<tr>		
					<td width="80px" class="label">名称：</td>
					<td width="120px"><form:input path="warehouse.codename" class="middle" style=""/></td>
				</tr>
				<tr>		
					<td class="label">备注：</td>
					<td><form:input path="warehouse.remarks" class="long" style=""/></td>
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
