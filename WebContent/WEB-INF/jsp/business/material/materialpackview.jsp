<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
<title>包装物料基本数据-查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
</head>
<body>
<div id="container">
<div id="main">
	
<form:form modelAttribute="material" method="POST" 
	id="material" name="material"   autocomplete="off">
	
	<form:hidden path="material.recordid" />
	<form:hidden path="material.parentid" />
	<form:hidden path="material.serialnumber" />
	<form:hidden path="material.categoryid" />
	<form:hidden path="materialid" value="${material.material.materialid}"/>
	<form:hidden path="categoryname" value="${material.attribute2}" />
	<form:hidden path="materialname" value="${material.material.materialname}" />
	
	<input type="hidden" id="supplierid" />
	<input type="hidden" id="suppliershortname" />
	<input type="hidden" id="supplierfullname" />
	<input type="hidden" id="keyBackup" value="${keyBackup }"/>
	<input type="hidden" id="albumInfo"  value="${albumInfo }"/>
	<input type="hidden" id="price" />
	<input type="hidden" id="purchaseType" value="${material.purchaseType}"/>
	
	
	<input id="handle_status" value="1133" hidden="hidden">
	
<fieldset style="float: left;width: 63%;">
	<legend>物料基本信息</legend>

	<table class="form" >		
		<tr>
			<td class="label" style="width: 100px;"><label>物料编码：</label></td>
			<td  colspan="7">
				<label>${material.material.materialid}</label></td>	
		<!-- 
			<td class="label" style="width: 80px;"><label>原物料编码：</label></td>
			<td style="width: 80px;">${material.material.originalid}</td>
			
			<td class="label" style="width: 60px;"><label>计量单位：</label></td>
			<td style="width: 50px;">${material.unitname}</td>	

			<td class="label" style="width: 60px;"><label>核算成本：</label></td>
			<td><a href="#" onClick="doCostUpdate()"><span id="cost">${material.material.materialcost}</span></a></td>	
			 -->											
		</tr>
		<!-- 
		<tr>		
			<td class="label" ><label>分类编码：</label></td>
			<td>${material.attribute2}</td>
			
			<td class="label">采购类别：</td>
			<td>${material.purchaseTypeName}</td>	
			
			<td class="label">采购人员：</td>
			<td colspan="3" >${material.purchaserName}</td>				
		 -->
		</tr>
		<tr>
			<td class="label"><label>物料名称：</label></td>
			<td colspan="7">${material.material.materialname}</td>		
		</tr>
		<tr>
			<td class="label"><label>通用型号：</label></td>
			<td colspan="7" style="word-break:break-all;"><form:hidden path="material.sharemodel" value=""/>	
				<div class="" id="coupon">
					<table id="ShareTab"><tr><td></td></tr></table></div></td>							
		</tr>	
		<tr>
			<td class="label" style="vertical-align: text-top;">中文描述：</td>
			<td colspan="7" style="vertical-align: text-top;"><pre>${material.material.description}</pre></td></tr>	
	</table>
	<div class="action" style="text-align: right;">			
		<button type="button" id="return" class="DTTT_button">返回</button>
		<button type="button" id="doEdit" class="DTTT_button" >编辑 / 增加子编码</button>
	</div>
	</fieldset>	
		<div id="tabs" style="float:right;margin: 10px 30px 0px 0px;">
				<div id="tabs-1" title="图片">
					<jsp:include page="../../common/album/album2.jsp"></jsp:include>
				</div>
		</div>

	
	<table style="width: 64%;">
		<tr>
			<td>
				<table id="subidTab" class="dataTable list" style="width:98%;margin-bottom: 20px;">
					<tr>
						<td class="td-center"></td>
						<td class="td-center" width="60px">子编码</td>
						<td class="td-center">子编码解释</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>		

<div style="clear: both"></div>			

	
<table style="width: 95%;">
	<tr>
		<td>
<span class="tablename" style="margin: -30px 17px -7px 1px;padding: 5px;float: right;"> &nbsp;工时工价&nbsp; </span>	
<a class="DTTT_button" onclick="doUpdate();" style="float: right;margin: -30px 130px;">编辑工价</a>	
		</td>
	</tr>
	<tr>
		<td>
<div class="list">
	
	<table id="TSupplier" style="width: 100%;height: 60px;" class="display" >
		<thead>
			<tr style="text-align: center;">
				<td style="width:200px">作业人数</td>
				<td style="width:200px">每小时产量</td>
				<td style="width:200px">每小时工价</td>
				<td>工人工价</td>
				<td>核算工价</td>
			</tr>
		</thead>		
		<tbody>
			<tr style="text-align: center;font-size: 14px;font-weight: bold;">
				<td style="width:150px">${price.peopleNumber }</td>
				<td style="width:150px">${price.hourYield }</td>				
				<td style="width:150px">${price.hourPrice }</td>
				<td style="width:150px">${price.workerPrice }</td>
				<td style="width:150px"><span id="laborprice">${price.laborPrice }</span></td>				
				<td></td>			
			</tr>			
		</tbody>
	</table>
</div></td>
	</tr>
	
</table>

</form:form>
</div>
</div>
<script type="text/javascript">
function reloadSupplier() {

	return true;
}
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
	
	//通用型号 初始化时,5 个输入框,注意:编号从 0 开始
	autoAddShareModel();
	//子编码 初始化时,5 个输入框,注意:编号从 0 开始
	autoAddSubid();
		
	$("#return").click(
		function() {
			var materialId='${material.material.materialid}';
			var url = "${ctx}/business/material?methodtype=materialPackSearchInit";
			location.href = url;		
		});
	
	$("#doEdit").click(
			function() {				
				var recordid = $('#material\\.recordid').val();
				var parentid = $('#material\\.parentid').val();
				var keyBackup = "pack";
				var url = '${ctx}/business/material?methodtype=edit';
				url = url + '&parentId=' + parentid+'&recordId='+recordid+"&keyBackup="+keyBackup;
				location.href = url;			
	});
;
	
	$("#storageHistory").click(
			function() {				
				var materialId = '${material.material.materialid}';
				var url = '${ctx}/business/financereport?methodtype=reportForDaybookInit';
				url = url + '&materialId=' + materialId;

				callWindowFullView("出入库历史",url);			
	});
	
	var keyBackup = $('#keyBackup').val();
	if(keyBackup == '1'){
		$('#return').css('display', 'none');//弹窗方式下,不显示返回按钮
	}
	
	
	
    	
});

function autoAddShareModel() {
	var firstFlg = true;
	<c:forEach var='model' items='${material.shareModelList}' varStatus='status'>
		var modelSize = '${material.shareModelList}.size()'
		var model = '${model}';
		var i = '${status.index}';
		var tdHtml = '';
		var space = '&nbsp;';
		tdHtml = model + space; 
	
		$('#ShareTab tr td').append(tdHtml);	
		
	</c:forEach>
} 
function autoAddSubid() {
	
	var selectedTR = 0;
	var selectedId = $('#material\\.recordid').val();
	<c:forEach var="sub" items="${material.materialLines}" varStatus="status">
	
		var recordid = '${sub.recordid}';
		var subid = '${sub.subid}';
		var des = '${sub.subiddes}';
		var parentid = '${sub.parentid}';
		var materialid = '${sub.materialid}';
		var i = '${status.index}';	
		
		var trHtml = '';
		
		trHtml+="<tr>";	
		trHtml+="<td width='60px'>";
		trHtml+="</td>";
		trHtml+="<td class='td-center'>";		  
		trHtml+="<label><a href=\"#\" onClick=\"doSubDetail('" + recordid +"','" + parentid +"','"+ materialid + "')\">"+subid+"</a></label>";
		trHtml+="<input name='materialLines["+i+"].recordid'    id='materialLines"+i+".recordid'   type='hidden' value='"+recordid+"' />";
		trHtml+="</td>";
		trHtml+="<td class='td-center'>";
		if(recordid == selectedId){
			selectedTR = i;
			//把选中的物料recordid设为当前信息
			$('#material\\.recordid').val(recordid);
			$('#material\\.parentid').val(parentid);
			trHtml+="<label>"+des+"</label>";
		
		}else{
			trHtml+="<label>"+des+"</label>";
			
		}
		trHtml+="</td>";
		trHtml+="</tr>";
		
		$('#subidTab tr:last').after(trHtml);	
	
	</c:forEach>
	
	selectedTR++;//跳过第一行的隐藏行
	$('#subidTab tr:eq('+selectedTR+')').addClass('selected');
	$('#subidTab tr:eq('+selectedTR+') td').eq(0).html('本条记录');
}
function doSubDetail(recordid , parentid,materialId) {
	var str = recordid + parentid;
	var url = '${ctx}/business/material?methodtype=detailViewPack';
	url = url + '&parentId=' + parentid+'&recordId='+recordid;
	url = url + '&materialId=' + materialId;
	location.href = url;
	
}


var layerHeight = '360px';
var layerWidth  = '900px';

function doUpdate(supplierId) {
	var materialId ='${material.material.materialid}';
	
	layerWidth  = '1000px';
	layerHeight = '300px';
	var url = '${ctx}/business/zzmaterial?methodtype=editH&materialId=' + materialId;

	layer.open({
		offset :[50,''],
		type : 2,
		title : false,
		area : [ layerWidth, layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
		cancel: function(index){ 
			layer.close(index)
			reload();
			return false; 
		}    
	});
}

function doDelete(recordId){
	var materialId= '${material.material.materialid}';
	
	if (recordId != ""){ //
		$.ajax({
			type : "post",
			url : "${ctx}/business/material?methodtype=deletePrice&recordId="+recordId,
			async : false,
			data : 'key=' + materialId,
			dataType : "json",
			success : function(data) {
				$('#TSupplier').DataTable().ajax.reload(null,false);
			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {
				
				
			}
		});
	}else{
		//
	}
}

function reload(){
	window.location.reload();
}

function doCostUpdate(){

	var materialId= '${material.material.materialid}';
	
	var url = "${ctx}/business/material?methodtype=materialCostView";
	url = url + "&materialId="+materialId;
	layer.open({
		offset :[20,''],
		type : 2,
		title : false,
		area : [ '1024px', '500px' ], 
		scrollbar : [true,false],
		title : false,
		content : url,
		cancel: function(index){ 

			var body = layer.getChildFrame('body', index);  //加载目标页面的内容
			var cost = body.find('#cost').val();			
			$('#cost').text(cost)

			layer.close(index)			
			return false; 
		}
	});
}
</script>
</body>
</html>