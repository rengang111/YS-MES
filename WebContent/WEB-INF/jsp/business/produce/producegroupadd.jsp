<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>生产组 新建子分类</title>
<script type="text/javascript">

var thisCount = 0;

	$(document).ready(function() {
				
		var codeid = '${produceForm.productionTeam.codeid}';
		var codeView = '无';
		var codeView2 = '';
		if(codeid !=''){			
			codeView = codeid ;	
			codeView2 = '&nbsp;'+codeid;
		}
		$('#parentId').html(codeView);
		$('#parentId2').html(codeView2);
		$('#productionTeam\\.codeid').val('');
		$('#productionTeam\\.codetype').val('');
		$('#productionTeam\\.codename').val('');
		$('#productionTeam\\.remarks').val('');
		
		var multiLevel = '${multiLevel}';
		if(multiLevel == '2'){
			
		}else{
			
		}
		
		$("#productionTeam\\.codeid").focus(function(){
		   	$(this).select();
		});
		

		//autoCompleteUser();
		
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
				return false;
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
						
			var parentCodeId = '${produceForm.productionTeam.codeid}';
			
			$.ajax({
				async:false,
				type : "POST",
				url : "${ctx}/business/produce?methodtype=addGroupCode"+"&parentCodeId="+parentCodeId,
				data : $('#produceForm').serialize(),// 要提交的表单
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
<script type="text/javascript">
function autoCompleteUser() { 
		$("#productionTeam\\.codetype").autocomplete({
			
			source : function(request, response) {
				//alert(111);
				$.ajax({
					type : "POST",
					url : "${ctx}/business/warehouse?methodtype=materialCategorySearch",
					dataType : "json",
					data : {
						parentId : request.term,
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								return {
									label : item.categoryId + " | " + item.categoryName,
									value : item.categoryId,
									id 	  : item.categoryId,
								}
							}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},
			focus: function() {
                // 按上下键时不做选择操作
                return false;
            },
			select : function(event, ui) {
								
	        	var terms = split(this.value);
                // 移除当前所有的内容
                terms.pop();
                // 将选中的添加到input中
                terms.push( ui.item.value );
                // 在最后添加分隔符
                terms.push( "" );
                this.value = terms.join( "," );
                
                /*
                terms = split($("#productionTeam\\.codeid").val());
                // 移除当前所有的内容
                terms.pop();
                // 将选中的添加到input中
                terms.push( ui.item.id );
                // 在最后添加分隔符
                terms.push( "" );
                $("#productionTeam\\.codeid").val(terms.join( "," ));
                */
                return false;
                
				
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
                    $('#productionTeam\\.codetype').val('');
                }
            },
			
			minLength : 1,
			//autoFocus : false,
			width: 200,
			//mustMatch:true,
		});
	}
	
function split( val ) {
    return val.split( /,\s*/ );
}

</script>

</head>
<body class="noscroll">
<div id="layer_main">

	<form:form modelAttribute="produceForm" method="POST" 
	 id="produceForm" name="produceForm"  autocomplete="off">

		<form:hidden path="productionTeam.recordid" />
		<form:hidden path="productionTeam.multilevel" />
		<form:hidden path="productionTeam.sortno" />
		<form:hidden path="productionTeam.productiontechnical" />
		<input type="hidden" id="oldQuantity" />		

		<fieldset>
			<legend> 生产组编码</legend>

			<table class="form">

				<tr>
					<td width="100px" class="label"><label>父级分类：</label></td>
					<td>&nbsp;<span id="parentId">无${produceForm.productionTeam.parentid}</span></td>
				</tr>
				<tr>		
					<td width="80px" class="label"><label>子分类编号：</label></td>
					<td><span id="parentId2"></span><form:input path="productionTeam.codeid" class="required short" value=""/>
						<span style="color: blue">（编码规则：子分类编码=父级分类+输入内容）</span></td>
				</tr>
				
				<c:set var="multiLevel" value="${multiLevel }" />
				<c:out value="${multiLevel }"></c:out>
				<c:if test="${multiLevel == '1' }">
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
				<c:if test="${multiLevel == '2' }">
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
