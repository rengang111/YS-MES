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
<title>业务字典-新建省市地区</title>
<meta charset="UTF-8">
<%@ include file="../common/common.jsp"%>

<script type="text/javascript">
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

		//设置光标项目
		$("#province").focus();
								
		$("#area").validate({
		rules : {
			bidPrice : {
				required : true,
				maxlength : 8,
			}
		},
		submitHandler : function() {			
			
			//将提交按钮置为【不可用】状态
			$("#submit").attr("disabled", true); 

			$.ajax({
				type : "POST",
				url : "${ctx}/area?methodtype=insert",
				data : $(
						'#area')
						.serialize(),// 要提交的表单
				success : function(d) {
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();/
					parent.reload();
					parent.layer.close(index); //执行关闭
				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {						
					//alert("${err002}");  //发生系统异常，请再试或者联系管理员。
					
					//刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();/
					parent.reload();
					parent.layer.close(index); //执行关闭
				}
			});
			
		}
	});
						
});
		
</script>

</head>
<body class="noscroll">
	<div id="layer_main" >
		
			<form:form modelAttribute="area" method="POST" id="area">		
			<form:hidden path="area.recordid" />
			<fieldset>
				<legend> 新建省市地区</legend>
				
				<table class="form" cellspacing="0" cellpadding="0" >

					<tr>
						<td width="100px">
							<label>省份：</label>
						</td>
						<td>												
							<form:input path="area.province" class=" required" />	
						</td>
					</tr>
					<tr>
						<td width="100px">
							<label>主城市：</label>
						</td>
						<td>												
							<form:input path="area.city" class=" required" />	
						</td>
					</tr>
					<tr>
						<td width="100px">
							<label>主城市电话号码：</label>
						</td>
						<td>												
							<form:input path="area.citycode" class=" required" />	
						</td>
					</tr>
					<tr>
						<td width="100px">
							<label>所在地区：</label>
						</td>
						<td>												
							<form:input path="area.county" class=" required" />	
						</td>
					</tr>
					<tr>
						<td width="100px">
							<label>所在地区简称：</label>
						</td>
						<td>												
							<form:input path="area.countycode" class=" required" />	
						</td>
					</tr>
					
				</table>
			</fieldset>

			<fieldset class="action"  style="margin-bottom:40px;">
				<button type="reset" id="reset" class="DTTT_button">重置</button>
				<button type="submit" id="submit" class="DTTT_button">保存</button>
			</fieldset>
			
		</form:form>
		
	</div>	
</body>

</html>
