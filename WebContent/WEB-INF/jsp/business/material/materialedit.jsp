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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
  var ctx = '${ctx}'; 
</script>
<script type="text/javascript" src="${ctx}/js/jquery-2.1.3.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/dataTables.tableTools.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/all.css" />

<title>物料基本数据-编辑</title>
<style type="text/css">

input.middle{width:300px;}
</style>
</head>

<body class="panel-body">
<div id="container">

		<div id="main">
	
<form:form modelAttribute="material" method="POST"
	id="material" name="material"   autocomplete="off">
	
	<form:hidden path="material.recordid" />
	<form:hidden path="material.parentid" />
	<form:hidden path="material.serialnumber" />
	<form:hidden path="material.categoryid" />
	<form:hidden path="categoryname" />
	<input type="hidden" id="keyBackup" value="${keyBackup }"/>
	
<fieldset>
	<legend>物料基本信息-编辑</legend>

	<table class="form">		
		<tr>
			<td class="label" style="width: 100px;"><label>物料(ERP)编号：</label></td>
			<td style="width: 240px;">
				<form:input path="material.materialid" class="read-only"  style="width: 180px;"/></td>
								
			<td class="label" style="width: 100px;"><label>物料名称：</label></td>
			<td colspan="3">
				<form:input path="material.materialname" class="long required" /></td>												
		</tr>
		<tr>				
			<td class="label" ><label>分类编码：</label></td>
			<td>
				<form:input path="attribute1" class="required read-only" /></td>	
				
			<td class="label"><label>编码解释：</label></td>
			<td style="width: 300px;">
				<form:input path="attribute2" class="middle read-only" /></td>
				
			<td class="label"  style="width: 100px;">原物料编码：</td>
			<td style="width: 150px;">
				<form:input path="material.originalid" class="" /></td>
		</tr>
		<tr>				
			<td class="label"><label>计量单位：</label></td>
			<td>
				<form:select path="material.unit" style="width: 100px;">							
					<form:options items="${material.unitList}" 
						itemValue="key" itemLabel="value" /></form:select></td>	
						
			<td class="label">物料采购类别：</td>
			<td style="width: 100px;">
				<form:select path="material.purchasetype" style="width: 120px;">							
					<form:options items="${material.purchaseTypeList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
				
			<td class="label"></td>
			<td style="width: 150px;"></td>						
		</tr>
		<tr>				
			<td class="label" >采购员：</td>
			<td>
				<form:select path="material.purchaser" style="width: 120px;">							
					<form:options items="${PurchaserList}" 
						itemValue="key" itemLabel="value" /></form:select></td>	
				
			<td class="label">质检员：</td>
			<td><form:hidden path="personnel[0].materialid" value="${material.material.materialid }" />
				<form:hidden path="personnel[0].personneltype" value="Q" />
				<form:select path="personnel[0].personnelid" style="width: 120px;" class="qualityId">							
					<form:options items="${QualityList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
				
			<td class="label">仓管员：</td>
			<td><form:hidden path="personnel[1].materialid" value="${material.material.materialid }" />
				<form:hidden path="personnel[1].personneltype" value="W" />
				<form:select path="personnel[1].personnelid" style="width: 120px;" class="inventoryId" >							
					<form:options items="${InventoryList}" 
						itemValue="key" itemLabel="value" /></form:select></td>
		</tr>
				
		
	</table>
	
	<legend style="margin: 10px 0px 0px 0px"> 物料-描述信息</legend>

	<table class="form" width="100%">
		<tr>
			<td width="55%">中文描述：</td>
			<td>
				<table width="100%">
					<tr>
						<td class="td-center">子编码</td>
						<td class="td-center">子编码解释</td>
						<td class="td-center" width='60px'>
							<button type="button"  style = "height:25px;" 
							id="createSubid" class="DTTT_button">新建</button></td></tr></table></td></tr>		
		<tr>
			<td><form:textarea path="material.description" rows="7" cols="70" /></td>
			<td>			
				<div class="" id="subidDiv" style="overflow: auto;height: 160px;">
					<table id="subidTab" class="dataTable list"  style="width: 95%;">
						<tr style='display:none'><td></td>
						<td></td><td></td></tr>
						
						
						</table></div></td>
		</tr>
	</table>
	
	<legend style="margin: 10px 0px 0px 0px"> 物料-关联信息</legend>
	<div id="autoscroll">
	<table class="form" width="100%">
		<tr>
			<td><label>通用型号：</label>
				<button type="button"  style = "height:25px" class="DTTT_button"
				 id="createShare" >新建</button></td>
		</tr>		
		<tr>
			<td><form:hidden path="material.sharemodel" value=""/>	
				<div class="" id="coupon">
					<table id="ShareTab"><tr><td></td></tr></table></div></td>
		</tr>
	</table>
	</div>
	
	<div style="clear: both"></div>			
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">					
		<!-- button type="button" id="close" class="DTTT_button">关闭</button -->
		<button type="button" id="return" class="DTTT_button">返回</button>
		<!-- button type="reset" id="reset" class="DTTT_button">重置</button -->
		<!-- button type="button" id="submitRefresh" class="DTTT_button">保存并继续添加</button-->
		<button type="button" id="submitReturn" class="DTTT_button">保存</button>
	</fieldset>
	
</form:form>
</div>
</div>
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

 function delTr(ckb){
     //获取选中的复选框，然后循环遍历删除
     var ckbs=$("input[name="+ckb+"]:checked");
     if(ckbs.size()==0){
        alert("要删除指定行，需选中要删除的行！");
        return;
     }
      ckbs.each(function(){
         $(this).parent().parent().remove();
      });
  }

var strShareMode = "<input type='text' class='mini' maxlength='5' name='sharemodel";	
 
 function addTd(){

	 //通用型号是从 0 开始 输出的,所以 添加时要减一
	var i = $("#coupon input[type=text]").length - 1;		
	i++;		
    var trHtml = strShareMode + i + "' id='sharemodel" + i + "'/>"; 		
	$("#coupon input:last").after(trHtml);			
	autoNextSelect();
	}
 
 function addTr2(){
	 
	var i = $("#subidTab tr").length -1;
	var maxid = i-1;//已知最后一行的下标
	var maxSub=$("#materialLines"+maxid+"\\.subid").val();
	maxSub++;//已知的最大编号再累计
	var subid = PrefixInteger(maxSub,3);
	
	var trHtml = "";
	trHtml+="<tr>";	
	trHtml+="<td width='30px'>";
	trHtml+="</td>";
	trHtml+="<td class='td-center'>";
	trHtml+="<input name='materialLines["+i+"].subid'    id='materialLines"+i+".subid'   type='text' value='"+subid+"' class='small'/>";
	trHtml+="</td>";
	trHtml+="<td class='td-center'>";
	trHtml+="<input name='materialLines["+i+"].subiddes' id='materialLines"+i+".subiddes'type='text' class='middle'/>";
	trHtml+="</td>";
	trHtml+="</tr>";
	
	$('#subidTab tr:last').after(trHtml);		
}
 
 function addTr(trHtml){

	 var $td=$('#ShareTab tr:first td:last');
    
    if($td.size()==0){
        alert("指定的table id或行数不存在！");
        return;
    }    
	$('#ShareTab tr:first td:first').after(trHtml);
	
	autoNextSelect();
    
   }
function autoNextSelect(){
	//自动跳到下一个输入框  
    $("input[name^='sharemode']").each(function() {
    	
        $(this).keyup(function(e) {
        	
            e = window.event || e;
            var k = e.keyCode || e.which;

            if (k == 8) {   //8是空格键
                if ($(this).val().length < 1) {
                    $(this).prev().focus();
                    $(this).prev().focus(function() {
                        var obj = e.srcElement ? e.srcElement: e.target;
                        if (obj.createTextRange) { //IE浏览器
                            var range = obj.createTextRange();
                            range.moveStart("character", 4);
                            range.collapse(true);
                            range.select();
                        }
                    });
                }
            } else {
                if ($(this).val().length > 4) {
                    $(this).next().focus();
                }
            }
        })
    });//自动跳到下一个输入框 
  }
  
function PrefixInteger(num, length) {
	 return (Array(length).join('0') + num).slice(-length);
	} 
	
function autoAddShareModel() {

	var firstFlg = true;
	<c:forEach var="model" items="${material.shareModelList}" varStatus="status">

		var modelSize = '${material.shareModelList}.size()'
		var model = '${model}';
		var i = '${status.index}';
		var tdHtml = '';
		
		if(firstFlg){
			tdHtml = strShareMode + i + "' id='sharemodel" + i+ "' value='"+model+"'/>"; 
		
			$('#ShareTab tr:first td:first').after(tdHtml);
			firstFlg = false;
		}else{
			tdHtml = strShareMode + i + "' id='sharemodel" + i+ "' value='"+model+"'/>"; 		
			$('#coupon input:last').after(tdHtml);	
		}

	</c:forEach>
	autoNextSelect();
} 

function autoAddSubid() {
	
	var selectedTR = 0;
	var selectedId = $('#material\\.recordid').val();
	
	<c:forEach var="sub" items="${material.materialLines}" varStatus="status">
	
		var recordid = '${sub.recordid}';
		var subid = '${sub.subid}';
		var des = '${sub.subiddes}';
		var parentid = '${sub.parentid}';
		var i = '${status.index}';	
		
		var trHtml = '';
		
		trHtml+="<tr>";	
		trHtml+="<td width='60px'>";
		trHtml+="</td>";
		trHtml+="<td class='td-center'>";	
		if(recordid == selectedId){
			selectedTR = i;
			trHtml+="<input name='materialLines["+i+"].subid'    id='materialLines"+i+".subid'   type='text' value='"+subid+"' class='small center' />";
			trHtml+="<input name='materialLines["+i+"].recordid'    id='materialLines"+i+".recordid'   type='hidden' value='"+recordid+"' />";
			trHtml+="</td>";
			trHtml+="<td class='td-center'>";
		
			//把选中的物料recordid设为当前信息
			$('#material\\.recordid').val(recordid);
			$('#material\\.parentid').val(parentid);
			trHtml+="<input name='materialLines["+i+"].subiddes' id='materialLines"+i+".subiddes'type='text' class='middle' style='text-align: center;' value='"+des+"'/>";
		
		}else{
			trHtml+="<input name='materialLines["+i+"].subid'    id='materialLines"+i+".subid'   type='text' value='"+subid+"' class='small center read-only' readonly='readonly' />";
			trHtml+="<input name='materialLines["+i+"].recordid'    id='materialLines"+i+".recordid'   type='hidden' value='"+recordid+"' />";
			trHtml+="</td>";
			trHtml+="<td class='td-center'>";

			trHtml+="<label><a href=\"#\" onClick=\"doSubDetail('" + recordid +"','"+ parentid + "')\">"+des+"</a></label>";
			
		}
		trHtml+="</td>";
		trHtml+="</tr>";
		
		$('#subidTab tr:last').after(trHtml);	
	
	</c:forEach>
	
	selectedTR++;//第一行为隐藏行
	$('#subidTab tr:eq('+selectedTR+')').addClass('selected');
	$('#subidTab tr:eq('+selectedTR+') td').eq(0).html('本条记录');
	
}

function doSubDetail(recordid , parentid) {

	var keyBackup = $('#keyBackup').val();
	var url = '${ctx}/business/material?methodtype=edit';
	url = url + '&parentId=' + parentid+'&recordId='+recordid;
	url = url + '&keyBackup=' + keyBackup;
	location.href = url;
	
}

$(document).ready(function() {

	$(".read-only").attr('readonly', "true");

	var purchaserId = '${purchaserId}';
	var quality     = '${qualityId}';
	var invertory   = '${invertoryId}';

	$('#personnel0\\.personnelid').val(quality);
	$('#personnel1\\.personnelid').val(invertory);
	$('#material\\.purchaser').val(purchaserId);
	
	$("#material :input.required").each(function(){
        var $required = $("<strong class='high'> *</strong>"); //创建元素
        $(this).parent().append($required); //然后将它追加到文档中
    });
	
	 
	//通用型号 初始化时,5 个输入框,注意:编号从 0 开始
	autoAddShareModel();

	//子编码 初始化时,5 个输入框,注意:编号从 0 开始
	autoAddSubid();


	$("#attribute1").autocomplete({		
		source : function(request, response) {
			//alert(00);
			$.ajax({
				type : "POST",
				url : "${ctx}/business/material?methodtype=categorySearch",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(111)
					response($.map(data.data,
						function(item) {
							return {
								label : item.viewList,
								value : item.categoryId,
								id : item.categoryId,
								categoryName : item.categoryName,
							}
						}));
				},//success
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
					//alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}//error
			});
		},//source
	
		select : function(event, ui) {//选择物料分类后,自动添加流水号categoryId
			//alert(ui.item.id);	
		
			$("#attribute2").val(ui.item.categoryName);
			$("#categoryname").val(ui.item.categoryName);
			
			var val_category = ui.item.id;
			
			if (val_category != "") {//判断所选的分类编号
				$
				.ajax({
					type : "post",
					url : "${ctx}/business/material?methodtype=mategoryMAXId",
					async : false,
					data : {
						categoryId : val_category,
					},
					dataType : "json",
					success : function(data) {

						var retValue = data['retValue'];

						if (retValue === "failure") {
							$().toastmessage('showWarningToast',"请联系系统管理员。");
						} else {
							$("#material\\.categoryid").val(val_category);	
							$("#material\\.materialid").val(val_category + data.codeFormat);	
							$("#material\\.serialnumber").val(data.code);
							//设置光标项目
							$("#material\\.materialname").focus();
							//设置采购类别
							var type=val_category.substring(0,1);
							if(typeof(type) !='undefined'){
								if(type == 'G'){
									$('#material\\.purchasetype').val('050');//包装品
								}else if(type == 'A'){
									$('#material\\.purchasetype').val('040');//原材料
									
								}
							}
						}
					},
					error : function(
							XMLHttpRequest,
							textStatus,
							errorThrown) {
						$("#material\\.materialid").val("");
						alert("发生系统异常，请再试或者联系系统管理员."); 	
					}
				});
			} else {}	
		},//select		
		minLength : 1,
		autoFocus : false,
	
	}); //autocomplete

	
	$("#return").click(
		function() {
			var keyBackup = $('#keyBackup').val();
			var url = "${ctx}/business/material"+"?keyBackup="+keyBackup;
			location.href = url;		
		});

	$("#createShare").click(
		function() {
			addTd();

	});
	
	$("#createSubid").click(
			function() {
				addTr2();

		});
	

	
	$("#submitRefresh").click(
			function() {				
				if(inputCheck()){
					doSubmitRefresh();
				}

		});
	$("#submitReturn").click(
			function() {
				if(inputCheck()){
					doSubmit();
				}

		});
});


function inputCheck(){
	
	//验证
	var strValue = true;
    if($('#material\\.categoryid').val()==""  ){
        $("#attribute1").css("backgroundColor","rgba(255, 0, 0, 0.28)");
        strValue = false;
    }
     if($('#material\\.materialname').val()==""  ){
         $("#material\\.materialname").css("backgroundColor","rgba(255, 0, 0, 0.28)");
         strValue = false;
    }  
     
 	var strModelValue = '';
	var firstflg = true;	
	$("#coupon input[type=text]").each(function () {
		
		if(firstflg){
			if($.trim($(this).val()) != ''){
				strModelValue =$(this).val();
			}
			firstflg = false;
		}else{
			if($.trim($(this).val()) != ''){
				strModelValue =strModelValue +","+ $(this).val();
			}			
		}
	})
	$("#material\\.sharemodel").val(strModelValue) ;
	return strValue;
}

function doSubmit(){
	
	var keyBackup = $("#keyBackup").val();
	$('#material').attr("action", "${ctx}/business/material?methodtype=update"+"&keyBackup="+keyBackup);
	$('#material').submit();
}

function doSubmitRefresh(){
			
	var actionUrl='${ctx}/business/material?methodtype=insertRefresh';

	var material = $('#material\\.materialname').val();
	alert(material)
	$.ajax({
		type : "POST",
		//contentType : 'application/json',
		dataType : 'json',
		async:false,
		url : actionUrl,
		data : $('#material').serialize(),
		success : function(data) {
			
			if(data.message!=""){
				alert(data.message);
				window.location.reload();
			}
		},		
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert(XMLHttpRequest.status);					
			//alert(XMLHttpRequest.readyState);					
			//alert(textStatus);					
			//alert(errorThrown);
			alert("发生系统异常，请再试或者联系系统管理员.");
		}
	});
	
}
</script>
</body>
</html>
