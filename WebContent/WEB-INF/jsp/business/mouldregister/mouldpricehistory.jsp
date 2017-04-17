<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>


<head>
<title>模具单元--供应商单价历史记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
  var ctx = '${ctx}'; 
</script>
<script type="text/javascript" src="${ctx}/js/jquery-2.1.3.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/plugins/datatables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/js/layer.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/dataTables.tableTools.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />

</head>


<body>

<div id="layer_main">
	
<form:form modelAttribute="dataModels" method="POST" 
	id="mould" name="mould"   autocomplete="off">
	
	<form:hidden path="mouldId" value = "${DisplayData.mouldId}"/>
	<form:hidden path="mouldFactoryId" value = "${DisplayData.mouldFactoryId}"/>

<fieldset>
<legend> 供应商报价--历史记录</legend>		

<div id="TSupplier_wrapper" class="dataTables_wrapper">
	<div id="clear"></div>
	<table id="TSupplier"  aria-describedby="TSupplier_info" style="width: 100%;" id="TOgran" class="display dataTable" cellspacing="0">
		<thead>				
		<tr class="selected">
			<th style="width:10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
			<th style="width:80px;" aria-label="供应商编码" class="dt-middle sorting_disabled">供应商编码</th>
			<th style="width:50px;" aria-label="供应商简称" class="dt-middle sorting_disabled">简称</th>
			<th style="width:200px;" aria-label="供应商名称" class="dt-middle sorting_disabled">供应商名称</th>
			<th style="width:80px;" aria-label="单价" class="dt-middle sorting_disabled">单价</th>
			<th style="width:30px;" aria-label="币种" class="dt-middle sorting_disabled">币种</th>
			<th style="width:60px;" aria-label="报价日期" class="dt-middle sorting_disabled">报价日期</th>
			<th style="width:30px;" aria-label="报价日期" class="dt-middle sorting_disabled"></th>
		</tr>
		</thead>
	</table>
</div>
</fieldset>
</form:form>
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

$(document).ready(function() {
	
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

	var mouldFactoryId = $('#mouldFactoryId').val();
	var mouldId = $('#mouldId').val();
	
	var t = $('#TSupplier').DataTable({
		"paging": false,
		//"lengthMenu":[20,50,100],//设置一页展示10条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		//"bJQueryUI": true,
		"scrollY":270,
		"scrollCollapse":true,
		"retrieve" : true,
		"sAjaxSource" : "${ctx}/business/mouldregister?methodtype=getFactoryPriceHistory&mouldFactoryId=" + mouldFactoryId + "&mouldId=" + mouldId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
				
			var param = {};
			var formData = $("#mould").serializeArray();
			formData.forEach(function(e) {
				//alert(e.name+"=name"+"value= "+e.value)
				//aoData.push({"name":e.name, "value":e.value});
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

			               });
			           });

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
					{"data": "shortName", "defaultContent" : '', "className" : 'td-center'},
					{"data": "fullName", "defaultContent" : '', "className" : 'td-center'},
					{"data": "price", "defaultContent" : '',"className" : 'td-center'},
					{"data": "currency", "defaultContent" : '',"className" : 'td-center'},
					{"data": "priceTime", "defaultContent" : '',"className" : 'td-center'},
					{"data": null,"className" : 'td-center'},
		        ],
		"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){		    			
						return row["rownum"] 
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			var delet = "<a href=\"#\" onClick=\"doDelete('" + row["id"] + "')\">删除</a>";
		    			
		    			return delet;
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


function doDelete(recordId){
	if (recordId != ""){ //
		$.ajax({
			type : "post",
			url : "${ctx}/business/mouldregister?methodtype=deletePriceHistory&id=" + recordId,
			async : false,
			data : 'key=' + recordId,
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
</script>
</body>
</html>
