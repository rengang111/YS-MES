<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>生产组 新建子分类----编辑</title>
<script type="text/javascript">

var thisCount = 0;

	$(document).ready(function() {
		
		var level = '${multiLevel}';
		var cal = '${produceForm.productionTeam.productiontechnical}';
		
		$('#productionTeam\\.productiontechnical').val(cal);
		if(level == '2'){
			
		}else if (level == '3'){
			
		}
				
		$("#productionTeam\\.codeid").focus(function(){
		   	$(this).select();
		});
		
		
		$("#return").click(function() {

					//alert(999);

					var index = parent.layer
							.getFrameIndex(window.name); //获取当前窗体索引

					parent.layer.close(index); //执行关闭

		});
		
		
		$("#submit").click(function() {

			if ($("#productionTeam\\.codeid").val() == "") {	
				$().toastmessage('showWarningToast', "请输入子分类编码。");
				$("#productionTeam\\.codeid").focus();
				return;
			}
			
			var collection = $(".checkbox");
			var str = '';
			var firstFlag = true;
		    $.each(collection, function () {
		    	 var isChecked = $(this).is(":checked");
		    	 if(isChecked){
					if(collection){
						str += $(this).val();
					}else{
				    	 str +=  "," + $(this).val() ;						
					}
					collection =false;
		    	 }
		    });
		    
		    $('#productionTeam\\.productiontechnical').val(str);

			$('#submit').attr("disabled","true").removeClass("DTTT_button");
						
			
			$.ajax({
				async:false,
				type : "POST",
				url : "${ctx}/business/produce?methodtype=updateGroupCode",
				data : $('#produceForm').serialize(),// 要提交的表单
				success : function(data) {

					var messege = data['message'];
					if(messege == 'SUCCESSMSG'){
						alert('更新成功')					
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

	<form:form modelAttribute="produceForm" method="POST" 
	 id="produceForm" name="produceForm"  autocomplete="off">

		<form:hidden path="productionTeam.recordid" />
		<form:hidden path="productionTeam.multilevel" />
		<form:hidden path="productionTeam.sortno" />
		<form:hidden path="productionTeam.parentid" />
		<form:hidden path="productionTeam.productiontechnical" />

		<fieldset>
			<legend> 生产组编码</legend>

			<table class="form">

				<tr>		
					<td width="80px" class="label"><label>子分类编号：</label></td>
					<td><form:input path="productionTeam.codeid" class="required short" value=""/></td>
				</tr>
				
			<c:set var="multiLevel" value="${multiLevel }" />
				<c:if test="${multiLevel == '2' }">
				<tr>		
					<td width="80px" class="label">生产技能：</td>
					<td width="" style="height: 50px;">
						<div style="width: 310px;">
						<form:checkboxes path="produceList" 
							items="${produceList}" class="checkbox" />		
							</div>					
						
					</td>
				</tr>
				</c:if>
				<c:if test="${multiLevel == '3' }">
				<tr>		
					<td width="80px" class="label">员工技能：</td>
					<td width="120px">						
						<form:select path="productionTeam.productiontechnical">
								<form:options items="${personnel}" 
								 itemValue="key" itemLabel="value" />
						</form:select>
					</td>
				</tr>
				</c:if>
				
				<tr>		
					<td width="80px" class="label">组长：</td>
					<td width="120px"><form:input path="productionTeam.groupleader" class="middle" style=""/></td>
				</tr>
				
				<tr>		
					<td class="label">备注：</td>
					<td><form:input path="productionTeam.remarks" class="long" style=""/></td>
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
