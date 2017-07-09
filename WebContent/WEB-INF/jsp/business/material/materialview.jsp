<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>


<head>
<title>物料基本数据-查看</title>
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
	<input type="hidden" id="price" />
	
	
	<input id="handle_status" value="1133" hidden="hidden">
	
<fieldset style="float: left;width: 65%;">
	<legend>物料基本信息</legend>

	<table class="form" >		
		<tr>
			<td class="label" style="width: 100px;"><label>物料(ERP)编号：</label></td>
			<td style="width: 150px;">
				<label>${material.material.materialid}</label></td>	
				
			<td class="label" style="width: 100px;"><label>分类编码：</label></td>
			<td>${material.attribute2}</td>
			<td class="label" style="width: 100px;"><label>计量单位：</label></td>
			<td style="width: 50px;text-align: center;">${material.unitname}</td>	
														
		</tr>
		<tr>
			<td class="label" style="width: 100px;"><label>物料名称：</label></td>
			<td colspan="3">${material.material.materialname}</td>			
								
			<td class="label" style="width: 100px;">采购类别：</td>
			<td style="width: 50px;text-align: center;">${material.purchaseTypeName}</td>					
		</tr>
		<tr>
			<td class="label"><label>通用型号：</label></td>
			<td colspan="5" style="word-break:break-all;"><form:hidden path="material.sharemodel" value=""/>	
				<div class="" id="coupon">
					<table id="ShareTab"><tr><td></td></tr></table></div></td>							
		</tr>	
		<tr>
			<td class="label" style="vertical-align: text-top;">中文描述：</td>
			<td colspan="5" style="vertical-align: text-top;"><pre>${material.material.description}</pre></td></tr>	
	</table>
	<div class="action" style="text-align: right;">			
		<button type="button" id="return" class="DTTT_button">返回</button>
		<button type="button" id="doEdit" class="DTTT_button" >编辑</button>
		<button type="button" id="doCreate" class="DTTT_button" onclick="doCreateBOMZZ()">新建二级BOM</button>
	</div>
	</fieldset>	
		<div id="tabs" style="float:right;margin: 10px 30px 0px 0px;">
				<div id="tabs-1" title="图片">
					<jsp:include page="../../common/album/album2.jsp"></jsp:include>
				</div>
		</div>

	
	<table style="width: 66%;">
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

	

<span class="tablename" style="margin: -30px 17px -7px 1px;padding: 5px;float: right;"> 供应商单价信息</span>	
<a class="DTTT_button" onclick="doCreatePrice();" style="float: right;margin: -30px 130px;">新建</a>	
<div class="list">

	
	<table id="TSupplier" style="width: 100%;" id="TOgran" class="display dataTable" >
		<thead>				
		<tr>
			<th style="width:10px;"  class="dt-middle ">No</th>
			<th style="width:70px;"  class="dt-middle ">供应商编码</th>
			<th style="width:30px;"  class="dt-middle ">简称</th>
			<th class="dt-middle ">供应商名称</th>
			<th style="width:60px;"  class="dt-middle ">采购单价</th>
			<th style="width:30px;"  class="dt-middle ">币种</th>
			<th style="width:50px;"  class="dt-middle ">报价单位</th>
			<th style="width:60px;" class="dt-middle ">报价日期</th>
			<th style="width:50px;" class="dt-middle ">采购</th>
			<th style="width:100px;" class="dt-middle ">操作</th>
		</tr>
		</thead>
	</table>
</div>

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
			var materialId='${material.material.materialid}';
			var url = "${ctx}/business/material?keyBackup="+materialId;
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
	
	var keyBackup = $('#keyBackup').val();
	if(keyBackup != ''){
		$('#return').css('display', 'none');//弹窗方式下,不显示返回按钮
	}
	/*
	$("#doCreate").click(
			function() {				
				var materialId = '${material.material.materialid}';
				//var url = '${ctx}/business/zzmaterial?methodtype=create';
				var url = '${ctx}/business/zzmaterial?methodtype=create&materialId=' + materialId;
				location.href = url;			
	});
*/
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
		//"lengthMenu":[20,50,100],//设置一页展示10条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		//"bJQueryUI": true,
		"retrieve" : false,
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
					var supplierId = "";
					$.each(data, function (n, value) {
			            $.each(value, function (i, v) {
		            	   supplierId = supplierId + v["supplierId"]+",";
			               //alert(i + ' == ' + v["supplierId"]);
			            });
			        })
					$("#supplierid").val(supplierId);
					
					var price = data["data"][0]["price"];
					$("#price").val(price);			        
 
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
					{"data": null,"className" : 'td-center'},
					{"data": "supplierId"},
					{"data": "shortName","className" : 'td-center'},
					{"data": "fullName"},
					{"data": "price","className" : 'td-right'},
					{"data": "currency","className" : 'td-center'},
					{"data": "unit","className" : 'td-center'},
					{"data": "priceDate","className" : 'td-center'},
					{"data": null,"className" : 'td-center'},
					{"data": null,"className" : 'td-center'}
		        ],
		"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			
						return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
		    		}},
		    		{"targets":9,"render":function(data, type, row){
		    			var edit = "<a href=\"#\" onClick=\"doUpdate('" + row["supplierId"] + "')\">编辑</a>";
		    			var history = "<a href=\"#\" onClick=\"doShowHistory('" + row["supplierId"] + "')\">历史报价</a>";
		    			var delet = "<a href=\"#\" onClick=\"doDelete('" + row["recordId"] + "')\">删除</a>";
		    			
		    			return edit+"&nbsp;"+history+"&nbsp;"+delet;
                    }},
		    		{"targets":8,"render":function(data, type, row){
		    			var materialId="${material.material.materialid}";
		    			var edit = "<a href=\"#\" onClick=\"doUpdate('" + row["supplierId"] + "','" + row["materialId"] + "')\">直接采购</a>";
		    			
		    			return edit;
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
		trHtml+="<label><a href=\"#\" onClick=\"doSubDetail('" + recordid +"','"+ parentid + "')\">"+subid+"</a></label>";
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
function doSubDetail(recordid , parentid) {
	var str = recordid + parentid;
	var url = '${ctx}/business/material?methodtype=detailView';
	url = url + '&parentId=' + parentid+'&recordId='+recordid;
	location.href = url;
	
}
var layerHeight = '360px';
var layerWidth  = '900px';
//新增供应商
function doCreatePrice() {
	var supplierId = $("#supplierid").val();
	var materialid ='${material.material.materialid}';
	var url = "${ctx}/business/material?methodtype=addSupplier&materialid="+materialid+"&supplierId="+supplierId;
	//alert('supplier'+supplierId)
	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '900px', layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  supplierPriceView();
		  return false; 
		}    
	});
}
//新建二级BOM
function doCreateBOMZZ() {
	var materialId ='${material.material.materialid}';
	layerWidth  = '1000px';
	
	if(materialId.length > 0){
		var type = materialId.substring(0,1);//截取物料大分类
		if(type == 'H'){//装配品
			layerHeight = '300px';
			var url = '${ctx}/business/zzmaterial?methodtype=createH&materialId=' + materialId;
		
		}else{//塑料制品
			layerHeight = '450px';
			var url = '${ctx}/business/zzmaterial?methodtype=createB&materialId=' + materialId;
			
		}
	}
	layer.open({
		offset :[50,''],
		type : 2,
		title : false,
		area : [ layerWidth, layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
		cancel: function(index){ 
			 // if(confirm('确定要关闭么')){
			    layer.close(index)
			 // }
			    supplierPriceView();
			  return false; 
			}    
	});
}
function doUpdate(supplierId) {
	var materialId ='${material.material.materialid}';
	var type = materialId.substring(0,1);//截取物料大分类
	if(supplierId == '0574YS00'){
		if(type == 'H'){
			layerWidth  = '1000px';
			layerHeight = '300px';
			var url = '${ctx}/business/zzmaterial?methodtype=editH&materialId=' + materialId;
			
		}else{
			layerWidth  = '1000px';
			layerHeight = '450px';
			var url = '${ctx}/business/zzmaterial?methodtype=editB&materialId=' + materialId;
			
		}
	}else{
		layerWidth  = '900px';
		layerHeight = '360px';
		var url = "${ctx}/business/material?methodtype=editPrice&supplierId=" + supplierId+"&materialId="+materialId;		
	}
	layer.open({
		offset :[50,''],
		type : 2,
		title : false,
		area : [ layerWidth, layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
		cancel: function(index){ 
			 // if(confirm('确定要关闭么')){
			    layer.close(index)
			 // }
			  supplierPriceView();
			  return false; 
			}    
	});
}
function doShowHistory(supplierId) {
	var materialId ='${material.material.materialid}';
			
	var url = "${ctx}/business/material?methodtype=supplierPriceHistoryInit&supplierId=" + supplierId+"&materialId="+materialId;
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
</script>
</body>
</html>