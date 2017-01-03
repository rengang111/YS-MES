<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>


<head>
<title>成品信息-查看</title>
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
	
	
	<input id="handle_status" value="1133" hidden="hidden">
	
<fieldset style="float: left;width: 65%;">
	<legend>产品信息</legend>

	<table class="form" >		
		<tr>
			<td class="label" style="width: 100px;"><label>产品编号：</label></td>
			<td style="width: 150px;">
				<label>${material.material.materialid}</label></td>
								
			<td class="label" style="width: 100px;"><label>产品名称：</label></td>
			<td colspan="3">${material.material.materialname}</td>												
		</tr>
		<tr>				
			<td class="label" style="width: 100px;"><label>分类编码：</label></td>
			<td colspan="3">${material.material.categoryid} | ${material.attribute2}</td>				
								
			<td class="label" style="width: 100px;"><label>计量单位：</label></td>
			<td style="width: 50px;text-align: center;">${material.unitname}</td>				
		</tr>
		<tr>
			<td class="label">机器型号：</td>
			<td>${product.productModel }</td>			
			<td class="label">客户名称：</td>
			<td colspan="3">${product.customerName }</td>
		</tr>
		<tr>
			<td class="label" style="vertical-align: text-top;">中文描述：</td>
			<td colspan="5" style="vertical-align: text-top;"><pre></pre></td></tr>	
	</table>
	<div class="action" style="text-align: right;">			
		<button type="button" id="return" class="DTTT_button">返回</button>
		<button type="button" id="doEdit" class="DTTT_button" >编辑</button>
	</div>
	</fieldset>	
		<div id="tabs" style="float:right;margin: 10px 30px 0px 0px;">
				<div id="tabs-1" title="图片">
					<jsp:include page="../../common/album/album2.jsp"></jsp:include>
				</div>
		</div>

	
	<table id="example" class="display">
		<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="80px">物料编码</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="100px">供应商编号</th>
				<th class="dt-center" width="80px">用量</th>
				<th class="dt-center" width="100px">本次单价</th>
				<th class="dt-center" width="100px">总价</th>
			</tr>
		</thead>
		<tbody>
						
			<c:forEach var="detail" items="${materialDetail}" varStatus='status' >						
				<tr>
					<td>${status.index+1}</td>
					<td>${detail.materialId}</td>								
					<td><span id="name${status.index}"></span></td>
					<td>${detail.supplierId}</td>
					<td>${detail.quantity}</td>							
					<td>${detail.price}</td>						
					<td><span id="total${status.index}">${detail.totalPrice}</span></td>					
				</tr>

				<script type="text/javascript">
					var index = '${status.index}';
					var materialName = '${detail.materialName}';
					var quantity = currencyToFloat('${detail.quantity}');
					var price =currencyToFloat( '${detail.price}');
					var totalPrice = quantity * price;
					$('#total'+index).html(totalPrice);
					$('#name'+index).html(jQuery.fixedWidth(materialName,20));
					counter++;
				</script>
				
			</c:forEach>
		</tbody>
	</table>		

<div style="clear: both"></div>		
	
<fieldset>
	<legend>客户报价</legend>
	<a class="DTTT_button" onclick="doCreatePrice();">新建</a>	
	<div class="list">	
		<table id="TSupplier"  class="display dataTable">
			<thead>				
				<tr>
					<th style="width:30px;" class="dt-middle ">No</th>
					<th style="width:80px;" class="dt-middle ">报价时间</th>
					<th style="width:150px;" class="dt-middle ">BOM编号<th>
					<th style="width:280px;"class="dt-middle ">核算价格</th>
					<th style="width:50px;" class="dt-middle ">客户币种</th>
					<th style="width:50px;" class="dt-middle ">客户报价</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="detail" items="${cusBidDetail}" varStatus='status' >						
				<tr>
					<td>${status.index+1}</td>
					<td>${detail.materialId}</td>								
					<td></td>
					<td>${detail.supplierId}</td>
					<td>${detail.quantity}</td>							
					<td>${detail.price}</td>						
					<td></td>					
				</tr>
				
			</c:forEach>
			</tbody>
		</table>
	</div>
</fieldset>
<div style="clear: both"></div>		
	
<fieldset>
	<legend>订单详情</legend>
	<a class="DTTT_button" onclick="doCreateOrder();">新建</a>	
	<div class="list">	
		<table id="TSupplier"  class="display dataTable">
			<thead>				
				<tr>
					<th style="width:30px;" class="dt-middle ">No</th>
					<th style="width:80px;" class="dt-middle ">关联BOM编号</th>
					<th style="width:150px;" class="dt-middle ">下单时间<th>
					<th style="width:280px;"class="dt-middle ">耀升编号</th>
					<th style="width:50px;" class="dt-middle ">客户订单编号</th>
					<th style="width:50px;" class="dt-middle ">订单数量</th>
					<th style="width:50px;" class="dt-middle ">订单交期</th>
					<th style="width:50px;" class="dt-middle ">销售币种</th>
					<th style="width:50px;" class="dt-middle ">单价</th>
					<th style="width:50px;" class="dt-middle ">总价</th>
					<th style="width:50px;" class="dt-middle ">出运条款</th>
					<th style="width:50px;" class="dt-middle ">出运港/目的港</th>
					<th style="width:50px;" class="dt-middle ">付款条件：出运后</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="detail" items="${cusBidDetail}" varStatus='status' >						
				<tr>
					<td>${status.index+1}</td>
					<td>${detail.materialId}</td>								
					<td></td>
					<td>${detail.supplierId}</td>
					<td>${detail.quantity}</td>							
					<td>${detail.price}</td>						
					<td></td>					
				</tr>
				
			</c:forEach>
			</tbody>
		</table>
	</div>
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

$(document).ready(function() {
		
	
	$("#return").click(
		function() {
			var materialId='${material.material.materialid}';
			var url = "${ctx}/business/material?materialId="+materialId;
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
					{"data": null,"className" : 'td-center'},
					{"data": "supplierId"},
					{"data": "shortName"},
					{"data": "fullName"},
					{"data": "price","className" : 'td-right'},
					{"data": "currency","className" : 'td-center'},
					{"data": "unit","className" : 'td-center'},
					{"data": "priceDate","className" : 'td-center'},
					{"data": null,"className" : 'td-center'}
		        ],
		"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			
						return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			var edit = "<a href=\"#\" onClick=\"doUpdate('" + row["supplierId"] + "')\">编辑</a>";
		    			var history = "<a href=\"#\" onClick=\"doShowHistory('" + row["supplierId"] + "')\">历史报价</a>";
		    			var delet = "<a href=\"#\" onClick=\"doDelete('" + row["recordId"] + "')\">删除</a>";
		    			
		    			return edit+"&nbsp;"+history+"&nbsp;"+delet;
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


var layerHeight = '360px';
var layerWidth  = '900px';


//新增供应商
function doCreatePrice() {
	var materialid ='${material.material.materialid}';
	var url = "${ctx}/business/material?methodtype=addSupplier&materialid="+materialid;
	
	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '900px', layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
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
	
	
	if (recordId != ""){ //
		$.ajax({
			type : "post",
			url : "${ctx}/business/material?methodtype=deletePrice&recordId="+recordId,
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
