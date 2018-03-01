<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>

<head>
<title>物料库存--价历史记录</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<body>

<div id="layer_main">
	
<form:form modelAttribute="formModel" method="POST" 
	id="material" name="material"   autocomplete="off">
	
	<input type="hidden" id="materialId" value="${materialId }" />

<fieldset>
<legend> 物料库存--修改记录</legend>		

<div id="TSupplier_wrapper" class="dataTables_wrapper">
	<div id="clear"></div>
	<table id="TSupplier"  style="width: 100%;" id="TOgran" class="display" >
		<thead>				
			<tr class="selected">
				<th style="width:10px;" >No</th>
				<th style="width:70px;" >修改日期</th>
				<th style="width:100px;" >物料编号</th>
				<th>名称</th>
				<th style="width:30px;" >单位</th>
				<th style="width:70px;" >修改前库存</th>
				<th style="width:70px;" >本次修改</th>
				<th style="width:50px;" >修改人</th>
			</tr>
		</thead>
	</table>
</div>
</fieldset>
</form:form>
</div>
<script type="text/javascript">

$(document).ready(function() {
	
	//供应商单价显示处理
	supplierPriceView();
	//供应商列表点击颜色变化
	selectedColor();
    	
});

function supplierPriceView() {

	var table = $('#TSupplier').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}

	var materialId = $('#materialId').val();
	
	var t = $('#TSupplier').DataTable({
		"paging": false,
		//"lengthMenu":[20,50,100],//设置一页展示10条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		//"bJQueryUI": true,
		"retrieve" : true,
		"sAjaxSource" : "${ctx}/business/storage?methodtype=showInventoryHistory"+"&materialId="+materialId,
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var param = {};
			var formData = $("#condition").serializeArray();
			formData.forEach(function(e) {
				aoData.push({"name":e.name, "value":e.value});
			});

			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : JSON.stringify(aoData),
				success: function(data){	
					$("#keyword1").val(data["keyword1"]);
					$("#keyword2").val(data["keyword2"]);						
					fnCallback(data);

				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
					{"data": null, "defaultContent" : '',"className" : 'td-center'},
					{"data": "createTime", "defaultContent" : '',"className" : 'td-center'},
					{"data": "materialId", "defaultContent" : '',"className" : 'td-left'},
					{"data": "materialName", "defaultContent" : ''},
					{"data": "unit", "defaultContent" : '',"className" : 'td-center'},
					{"data": "originQuantity", "defaultContent" : '',"className" : 'td-right'},
					{"data": "quantity", "defaultContent" : '',"className" : 'td-right'},
					{"data": "LoginName", "defaultContent" : '',"className" : 'td-left'},
		        ],
		"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){		    			
						return row["rownum"] 
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    					    			
		    			name = jQuery.fixedWidth(data,40);				    			
		    			return name;
		    		}},
		    		{"targets":5,"render":function(data, type, row){		    			
						return floatToCurrency(data);
		    		}}	,
		    		{"targets":6,"render":function(data, type, row){	    			
						return floatToCurrency(data);
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
			url : "${ctx}/business/material?methodtype=deletePriceHistory&recordId="+recordId,
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
