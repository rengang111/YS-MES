<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../../common/common.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>物料分类维护</title>

</head>
<body class="noscroll">
<div id="layer_main">	
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post" style="padding-top: 10px;">
		<input type=hidden name="operType" id="operType" value='${DisplayData.operType}'/>
		<fieldset>
		<table class="form" >
			<tr style="height:40px">
				<td width="100px" class="label">上级分类编码：</td>
				<td width="150px">&nbsp;${DisplayData.parentCategoryId}
					<input type=hidden name="parentCategoryId" id="parentCategoryId" value="${DisplayData.parentCategoryId}" />
				</td>
				<td width="100px" class="label">上级分类名称：</td>
				<td><label>${DisplayData.parentCategoryName}</label>
					<input type=hidden name="parentCategoryName" id="parentCategoryName" value="${DisplayData.parentCategoryName}" />
				</td>
			</tr>
			
			<tr>
				<td class="label">子分类编码：</td>
				<td colSpan="3">
					<input type="text" name="unitData.categoryid" id="categoryid" value="${DisplayData.unitData.categoryid}"   class="required loang" />
					<input type=hidden name="unitData.recordid" id="recordid" value="${DisplayData.unitData.recordid}"/>
					<input type=hidden name="unitData.createtime" id="createtime" value="${DisplayData.unitData.createtime}"/>
					<input type=hidden name="unitData.createperson" id="createperson" value="${DisplayData.unitData.createperson}"/>
					<input type=hidden name="unitData.createunitid" id="createunitid" value="${DisplayData.unitData.createunitid}"/>
					<input type=hidden name="unitData.parentid" id="parentid" value="${DisplayData.parentCategoryId}"/>
				</td>
			</tr>		
	
			<tr>
				<td class="label">
					子分类名称：
				</td>
				<td colspan="3">
					<input type="text" name="unitData.categoryname" id="categoryname" class="middle required" value="${DisplayData.unitData.categoryname}"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					规格描述：
				</td>
				<td colspan="3">
					<input type="text" name="unitData.formatdes" id="formatdes"  class="long" value="${DisplayData.unitData.formatdes}"/>
				</td>
			</tr>
			<tr>
				<td class="label">备注：</td>
				<td colspan="3">
					<textarea rows="6" cols="60" name="unitData.memo" id="memo" class="long" >${DisplayData.unitData.memo}</textarea>
				</td>
			</tr>
			<!-- 
			<tr>
				<td class="label">原物料编码：</td>
				<td colspan="3">
					<input type="text" name="unitData.originalid" id="originalid" class="middle" value="${DisplayData.unitData.originalid}"/>
				</td>
			</tr>
			 -->
		</table>
		</fieldset>					
		<fieldset class="action" style="text-align: end;">
			<button type="reset" id="reset" class="DTTT_button">重置</button>
			<button type="button" id="save"
					onclick="saveUpdate()" class="DTTT_button" />保存
		</fieldset>
	</form>
	</div>

<script type="text/javascript">

var operType = '';
var isUpdateSuccessed = false;
var updatedRecordCount = parseInt('${DisplayData.updatedRecordCount}');

operType = $('#operType').val();

if (updatedRecordCount > 0 ) {

	var strName = '${DisplayData.unitData.categoryname}';
	var strchild =' ${DisplayData.unitData.childid}';
	var treeview = strchild+'_'+strName;
	
	if (operType == 'add') {	
		window.parent.addNode('${DisplayData.unitData.parentid}', '${DisplayData.unitData.categoryid}', strName, '');
	}
	if (operType == 'update') {
		window.parent.updateNode('${DisplayData.unitData.parentid}', '${DisplayData.unitData.categoryid}', strName, '');
	}
}

if (operType == 'addsub') {
	$('#categoryid').val('${DisplayData.parentCategoryId}');
}

if ('${DisplayData.message}' != '') {
	// alert('${DisplayData.message}');
}	

if (updatedRecordCount > 0) {
	refreshOpenerData();
}

function noticeNaviChanged(id, name, isLeaf) {

	if (isLeaf) {
		$('#parentCategoryName').val("");	
		$('#categoryid').val(id);
		$('#categoryname').val(name);
	} else {
		$('#categoryid').val("");
		$('#categoryname').val("");
		$('#parentCategoryName').val(id);	
	}
}

function selectAddress() {
	callDicSelect("addresscode", "address", 'A2', '1' );
}

function refreshOpenerData() {
	parent.doSearch();
	//var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	parent.layer.close(index); //执行关闭
}

$(document).ready( function () {  

	$("form :input.required").each(function(){
        var $required = $("<strong class='high'> *</strong>"); //创建元素
        $(this).parent().append($required); //然后将它追加到文档中
    });
	
	 //文本框失去焦点后
    $('form :input').blur(function(){
    	//alert(888)
         var $parent = $(this).parent();
         $parent.find(".formtips").remove();
         //alert(111)
         //验证用户名
         if( $(this).is('#categoryid') ){
                if( this.value==""  ){
                    var errorMsg = '请输入物料分类编号!';
                    $parent.append('<label class="formtips error">'+errorMsg+'</label>');
                }
         }
         //验证邮件
         if( $(this).is('#categoryname') ){
             if( this.value==""  ){
                  var errorMsg = '请输入分类名称!';
                  $parent.append('<label class="formtips error">'+errorMsg+'</label>');
            }
         }

    }).keyup(function(){
       $(this).triggerHandler("blur");
    }).focus(function(){
         $(this).triggerHandler("blur");
    });//end blur

   
    //提交，最终验证。
     $('#save').click(function(){
    	 
		 $("form :input.required").trigger('blur');
		 var numError = $('form .error').length;
		 if(numError){
		     return false;
		 } 
		 
		if (operType == 'add' || operType == 'addsub') {
			$('#form').attr("action", "${ctx}/business/matcategory?methodtype=add");
		} else {
			$('#form').attr("action", "${ctx}/business/matcategory?methodtype=update");
		}

		$('#save').attr("disabled","true").removeClass("DTTT_button");
		
		$('#form').submit();
		
     });
    
    //重置
     $('#reset').click(function(){
           $(".formtips").remove(); 
     });
});

</script>
</body>

</html>