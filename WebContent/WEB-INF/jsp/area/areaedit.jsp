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
<title>业务字典-编辑省市地区</title>
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
	
	$(document)
			.ready(
					function() {

						//设置光标项目
						$("#province").focus();

						$("#return").click(
								function() {

									//alert(999);
									
									if (confirm("${msg0004}")){		//您正在编辑的页面尚未保存，确定要离开此页吗？
										
										var index = parent.layer
											.getFrameIndex(window.name); //获取当前窗体索引

										parent.layer.close(index); //执行关闭
										
									}
									
								});

						$("#area")
								.validate(
										{
											rules : {
												bidPrice : {
													required : true,
													maxlength : 8,
												}
											},
											submitHandler : function(user) {
																						
											var result = 0;
												
												$
												.ajax({
													type : "POST",
													url : "${pageContext.request.contextPath}/dict/area/duplicate.html",
													async: false,
													data : $(
															'#area')
															.serialize(),// 要提交的表单
													success : function(
															d) {
														
														//alert("success");

														var retValue = d['retValue'];

														//alert(retValue);

														if (retValue == "failure") {																
															result = 1;	
															//请确认是否已存在相同条目的记录，可修正后再进行保存。
															alert("${err005}");
														}
													},
													error : function(
															XMLHttpRequest,
															textStatus,
															errorThrown) {
														
														//alert(XMLHttpRequest.status);
														//alert(XMLHttpRequest.readyState);
														//alert(textStatus);
														//alert(errorThrown);
														
														//发生系统异常，页面将关闭，请再试或者联系管理员。
														alert("${err002}");
														
														//刷新父窗口，关闭子窗口
														var index = parent.layer
														.getFrameIndex(window.name); //获取当前窗体索引
														
														parent
																.reload();
														parent.layer
																.close(index); //执行关闭
																
													}
												});
																								
												if (result==1){
													//alert(999);
													return ;
												}
												
												if (confirm("${msg}")) {
													
													//将提交按钮置为【不可用】状态
													$("#submit").attr("disabled", true); 

													//var jsonuserinfo = $.toJSON($('#r').serializeObject());  
													//(jsonuserinfo);  

													$
															.ajax({
																type : "POST",
																url : "${pageContext.request.contextPath}/dict/area/update.html",
																data : $(
																		'#area')
																		.serialize(),// 要提交的表单
																success : function(
																		d) {
																	
																	//alert("success");

																	var retValue = d['retValue'];

																	//alert(retValue);

																	if (retValue == "failure") {
																		
																		var retCode = d['retCode'];
																		
																		//alert(retCode);
																		
																		switch (retCode){
																			case "801":
																				//记录已经被其他人修改，不能保存。页面将关闭，请确认或再次进行编辑。
																				alert("${err003}");	
																				break;
																			case '-100':
																				//alert("999");
																				var errMsg = d['errMsg'];
																				alert(errMsg);	
																				break;
																			default:
																				//操作中发生错误，页面将关闭，请重试或者联系管理员。
																				alert("${err001}");		
																			}
																																		
																	}else{
																		alert("${suc001}");
																	}

																	//刷新父窗口，关闭子窗口
																	var index = parent.layer
																			.getFrameIndex(window.name); //获取当前窗体索引
																	
																	parent
																			.reload();
																	parent.layer
																			.close(index); //执行关闭
																},
																error : function(
																		XMLHttpRequest,
																		textStatus,
																		errorThrown) {
																	
																	alert(XMLHttpRequest.status);
																	alert(XMLHttpRequest.readyState);
																	alert(textStatus);
																	alert(errorThrown);
																	
																	//发生系统异常，页面将关闭，请再试或者联系管理员。
																	alert("${err002}");
																	
																	//刷新父窗口，关闭子窗口
																	var index = parent.layer
																	.getFrameIndex(window.name); //获取当前窗体索引
																	
																	parent
																			.reload();
																	parent.layer
																			.close(index); //执行关闭
																			
																}
															});
												}
											}
										});

					});
</script>

</head>
<body class="noscroll">

	<div id="layer_main" >
					
		<div >
		
			<form:form modelAttribute="area" method="POST" id="area">
			
			<form:hidden path="areaId" />
			<form:hidden path="version" />
			<form:hidden path="createdBy" />
			<form:hidden path="createDate" />
			
			<input type="hidden" name="token" value="${token}" />
			
			<fieldset>
				<legend> 编辑省市地区</legend>
				
				<table class="form" cellspacing="0" cellpadding="0" >

					<tr>
						<td width="100px">
							<label>省份：</label>
						</td>
						<td>												
							<form:input path="province" class="middle required" />	
						</td>
					</tr>
					<tr>
						<td width="100px">
							<label>主城市：</label>
						</td>
						<td>												
							<form:input path="city" class="middle required" />	
						</td>
					</tr>
					<tr>
						<td width="100px">
							<label>主城市电话号码：</label>
						</td>
						<td>												
							<form:input path="cityPhone" class="middle required" />	
						</td>
					</tr>
					<tr>
						<td width="100px">
							<label>所在地区：</label>
						</td>
						<td>												
							<form:input path="county" class="middle required" />	
						</td>
					</tr>
					<tr>
						<td width="100px">
							<label>所在地区简称：</label>
						</td>
						<td>												
							<form:input path="countyCode" class="middle required" />	
						</td>
					</tr>
					
					<tr>
						<td width="100px">
							<label>可用状态：</label>
						</td>
						<td>												
							<form:radiobuttons path="status" items="${statusList}"
								itemValue="key" itemLabel="value" />		
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
	</div>
</body>

</html>
