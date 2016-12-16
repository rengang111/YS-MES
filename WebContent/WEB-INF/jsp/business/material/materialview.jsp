<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>


<head>
<title>物料基本数据-查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
  var ctx = '${ctx}'; 
</script>
<script type="text/javascript" src="${ctx}/js/jquery-2.1.3.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/js/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/dataTables.tableTools.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />

</head>

<body class="panel-body">
<div id="container">
<div id="main" style="margin-top: -15px;">
	
<form:form modelAttribute="material" method="POST" 
	style='padding: 0px; margin: 0px 0px;' 
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
	
	
	<input id="handle_status" value="1133" hidden="hidden">
	
<fieldset>
	<legend style='margin: 20px 10px -10px 0px;'>物料基本信息</legend>

	<table class="form" width="100%">		
		<tr>
			<td class="label" style="width: 100px;"><label>物料(ERP)编号：</label></td>
			<td style="width: 280px;">
				<label>${material.material.materialid}</label></td>
								
			<td class="label"><label>物料名称：</label></td>
			<td>${material.material.materialname}</td>												
		</tr>
		<tr>				
			<td class="label"><label>分类编码：</label></td>
			<td>${material.material.categoryid} | ${material.attribute2}</td>					
			<td class="label"><label>计量单位：</label></td>
			<td style="width: 280px;">${material.unitname}</td>				
		</tr>
		<tr>				
			<td class="label"><label>通用型号：</label></td>
			<td colspan="3"><form:hidden path="material.sharemodel" value=""/>	
				<div class="" id="coupon">
					<table id="ShareTab"><tr><td></td></tr></table></div></td>							
		</tr>
				
		
	</table>
	</fieldset>
	<fieldset style="margin-top: -15px;">
	<legend style="margin: 10px 0px -10px 0px"> 描述信息</legend>

	<table class="form" width="100%">
		<tr>
			<td width="55%">中文描述：</td>
			<td>
				<table width="100%">
					<tr>
						<td class="td-center">子编码</td>
						<td class="td-center">子编码解释</td>
						<td class="td-center" width='60px'>
							</td></tr></table></td></tr>		
		<tr>
			<td style="vertical-align: text-top;"><pre>${material.material.description}</pre></td>
			<td>			
				<div class="" id="subidDiv" style="overflow: auto;height: 100px;margin-top: -15px;">
					<table id="subidTab" class="dataTable list" style="width: 95%;">
						<tr style='display:none'><td></td>
						<td></td><td></td></tr>
						
						
						</table></div></td>
		</tr>
	</table>		
</fieldset>

<div style="clear: both"></div>			
<fieldset class="action" style="text-align: right;">					
	<button type="button" id="return" class="DTTT_button">返回</button>
	<!-- button type="reset" id="reset" class="DTTT_button">重置</button -->
	<button type="button" id="doEdit" class="DTTT_button" >编辑</button>
</fieldset>
<div style="clear: both"></div>		
	
<fieldset style="margin-top: -15px;">
<legend style="margin: 10px 0px -10px 0px"> 供应商单价信息</legend>		
<div class="list">

<div id="TSupplier_wrapper" class="dataTables_wrapper">
	<div id="DTTT_container" style="height:40px;align:right">
		<a aria-controls="TSupplier" tabindex="0" id="ToolTables_TSupplier_0" 
			class="DTTT_button DTTT_button_text" onclick="doCreate();"><span>新建</span></a>
		<!-- a aria-controls="TSupplier" tabindex="0" id="ToolTables_TSupplier_1"
		 	class="DTTT_button DTTT_button_text" onclick="doDelete();"><span>删除</span></a-->
	</div>
	<div id="clear"></div>
	<table id="TSupplier"  aria-describedby="TSupplier_info" style="width: 100%;" id="TOgran" class="display dataTable" cellspacing="0">
		<thead>				
		<tr class="selected">
			<th style="width:10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
			<th style="width:80px;" aria-label="供应商编码" class="dt-middle sorting_disabled">供应商编码</th>
			<th style="width:50px;" aria-label="供应商简称" class="dt-middle sorting_disabled">简称</th>
			<th style="width:200px;" aria-label="供应商名称" class="dt-middle sorting_disabled">供应商名称</th>
			<th style="width:80px;" aria-label="采购单价" class="dt-middle sorting_disabled">采购单价</th>
			<th style="width:30px;" aria-label="币种" class="dt-middle sorting_disabled">币种</th>
			<th style="width:50px;" aria-label="报价单位" class="dt-middle sorting_disabled">报价单位</th>
			<th style="width:60px;" aria-label="报价日期" class="dt-middle sorting_disabled">报价日期</th>
			<th style="width:100px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
		</tr>
		</thead>
	</table>
</div>
</div>
</fieldset>
</form:form>
</div>
</div>
<script type="text/javascript">

function reloadSupplier() {

	//$('#TSupplier').DataTable().supplierPriceView.reload(null,false);
	//alert(4444)
	//reloadTabWindow();

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
			var url = "${ctx}/business/material";
			location.href = url;		
		});

	$("#doEdit").click(
			function() {				
				var recordid = $('#material\\.recordid').val();
				var parentid = $('#material\\.parentid').val();
				var url = '${ctx}/business/material?methodtype=edit';
				url = url + '&parentId=' + parentid+'&recordId='+recordid;
				location.href = url;			

	});

	//供应商单价显示处理
	supplierPriceView();
	//供应商列表点击颜色变化
	selectedColor();
    	
});

function supplierPriceView() {

	var table = $('#TSupplier').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TSupplier').DataTable({
		"paging": false,
		"lengthMenu":[20,50,100],//设置一页展示10条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		//"bJQueryUI": true,
		"retrieve" : true,
		"sAjaxSource" : "${ctx}/business/material?methodtype=supplierPriceView",				
		"fnServerData" : function(sSource, aoData, fnCallback) {
				
			var param = {};
			var formData = $("#material").serializeArray();
			formData.forEach(function(e) {
				//alert(e.name+"=name"+"value= "+e.value)
				aoData.push({"name":e.name, "value":e.value});
			});
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : JSON.stringify(aoData),
				success: function(data){
					
					if (data.message != undefined) {
						//alert(data.message);
					}
									
					fnCallback(data);
					$.each(data, function (n, value) {
			               $.each(value, function (i, v) {
				               //alert(i + ' == ' + v["supplierId"]);
			               });
			           })
			           
					//重设显示窗口(iframe)高度
					iFramAutoSroll();

				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	                 alert(XMLHttpRequest.status);
	                 alert(XMLHttpRequest.readyState);
	                 alert(textStatus);
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
					{"data": null, "defaultContent" : '',"className" : 'td-center'},
					{"data": "supplierId", "defaultContent" : '',"className" : 'td-center'},
					{"data": "shortName", "defaultContent" : ''},
					{"data": "fullName", "defaultContent" : ''},
					{"data": "price", "defaultContent" : '',"className" : 'td-center'},
					{"data": "currency", "defaultContent" : '',"className" : 'td-center'},
					{"data": "priceUnit", "defaultContent" : '',"className" : 'td-center'},
					{"data": "priceDate", "defaultContent" : '',"className" : 'td-center'},
					{"data": null, "defaultContent" : '',"className" : 'td-center'}
		        ],
		"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			
						return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			var edit = "<a href=\"#\" onClick=\"doUpdate('" + row["recordId"] + "','" + row["shortName"] + "','" + row["fullName"] + "','" + row["supplierId"] + "')\">编辑</a>";
		    			var history = "<a href=\"#\" onClick=\"doShowHistory('" + row["supplierId"] + "')\">历史报价</a>";
		    			
		    			return edit+"&nbsp;"+history;
                    }}	           
	         	] 
		}
	
	);
	
}//ajax()供应商信息

function selectedColor(){

	$('#TSupplier').DataTable().on('click', 'tr', function() {
		
		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
        	$('#TSupplier').DataTable().$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});
	
}

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
		var i = '${status.index}';	
		
		var trHtml = '';
		
		trHtml+="<tr>";	
		trHtml+="<td width='60px'>";
		trHtml+="</td>";
		trHtml+="<td class='td-center'>";		  
		trHtml+="<label>"+subid+"</a></label>";
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
			trHtml+="<label><a href=\"#\" onClick=\"doSubDetail('" + recordid +"','"+ parentid + "')\">"+des+"</a></label>";
			
		}
		trHtml+="</td>";
		trHtml+="</tr>";
		
		$('#subidTab tr:last').after(trHtml);	
	
	</c:forEach>
	
	selectedTR++;//跳过第一行的隐藏行
	$('#subidTab tr:eq('+selectedTR+')').addClass('selected');
	$('#subidTab tr:eq('+selectedTR+') td').eq(0).html('本条记录');
}


function doSubDetail(recordid , parentid) {

	var str = recordid + parentid;

	var url = '${ctx}/business/material?methodtype=detailView';
	url = url + '&parentId=' + parentid+'&recordId='+recordid;
	location.href = url;
	
}

var layerHeight = '600';

//新增供应商
function doCreate() {
	var recordId =$('#material\\.recordid').val();
	var categoryName =$('#categoryname').val();
	var url = "${ctx}/business/material?methodtype=addSupplier&recordId=";
	url = url + recordId+"&categoryName="+categoryName;
	
	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '900px', '400px' ], 
		scrollbar : false,
		title : false,
		content : url,
	});
}

function doUpdate(recordId,s,f,id) {
	$('#suppliershortname').val(s);
	$('#supplierfullname').val(f);
	$('#supplierid').val(id);
	
	var url = "${ctx}/business/material?methodtype=editPrice&recordId=" + recordId;

	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '900px', '400px' ], 
		scrollbar : false,
		title : false,
		content : url,
	});
}

function doShowHistory(supplierId) {
		
	var url = "${ctx}/business/material?methodtype=supplierPriceHistoryInit&supplierId=" + supplierId;

	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '900px', '400px' ], 
		scrollbar : false,
		title : false,
		content : url,
	});
}


</script>
</body>
</html>
