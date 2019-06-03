<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../common/common2.jsp"%>
<title>生产线 新建子分类</title>
<script type="text/javascript">

var thisCount = 0;

	$(document).ready(function() {
				
		var codeid = '${produceForm.produceLine.codeid}';
		var code = '${produceForm.produceLine.codename}';
		var codeView = '无';
		var codeView2 = '';
		if(codeid !=''){			
			codeView = codeid + '&nbsp;' + code;	
			codeView2 = '&nbsp;'+codeid;
		}
		$('#parentId').html(codeView);
		$('#parentId2').html(codeView2);
		$('#produceLine\\.codeid').val('');
		$('#produceLine\\.codetype').val('');
		$('#produceLine\\.codename').val('');
		$('#produceLine\\.remarks').val('');
		
		$("#produceLine\\.codeid").focus(function(){
		   	$(this).select();
		});
		

		autoCompleteUser();
		
		$("#return").click(function() {
					//alert(999);
					var index = parent.layer
							.getFrameIndex(window.name); //获取当前窗体索引
					parent.layer.close(index); //执行关闭

		});
		
		
		$("#submit").click(function() {

			if ($("#produceLine\\.codeid").val() == "") {	
				$().toastmessage('showWarningToast', "请输入子分类编码。");
				$("#produceLine\\.codeid").focus();
				return;
			}

			$('#submit').attr("disabled","true").removeClass("DTTT_button");
						
			var parentCodeId = '${produceForm.produceLine.codeid}';
			
			$.ajax({
				async:false,
				type : "POST",
				url : "${ctx}/business/produce?methodtype=addWarehouseCode"+"&parentCodeId="+parentCodeId,
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
		$("#produceLine\\.codetype").autocomplete({
			
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
                terms = split($("#produceLine\\.codeid").val());
                // 移除当前所有的内容
                terms.pop();
                // 将选中的添加到input中
                terms.push( ui.item.id );
                // 在最后添加分隔符
                terms.push( "" );
                $("#produceLine\\.codeid").val(terms.join( "," ));
                */
                return false;
                
				
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
                    $('#produceLine\\.codetype').val('');
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

		<form:hidden path="produceLine.recordid" />
		<form:hidden path="produceLine.multilevel" />
		<form:hidden path="produceLine.sortno" />
		<input type="hidden" id="oldQuantity" />		

		<fieldset>
			<legend> 仓库编码</legend>

			<table class="form">

				<tr>
					<td width="100px" class="label"><label>父级分类：</label></td>
					<td>&nbsp;<span id="parentId">无${produceForm.produceLine.codename}</span></td>
				</tr>
				<tr>		
					<td width="80px" class="label"><label>子分类编号：</label></td>
					<td><span id="parentId2"></span><form:input path="produceLine.codeid" class="required short" value=""/>
						<span style="color: blue">（编码规则：子分类编码=父级分类+输入内容）</span></td>
				</tr>
				
				<tr>		
					<td width="80px" class="label">分类中文名称：</td>
					<td width="120px"><form:input path="produceLine.codename" class="middle" style=""/></td>
				</tr>
				
				<tr>		
					<td class="label">备注：</td>
					<td><form:input path="produceLine.remarks" class="long" style=""/></td>
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
