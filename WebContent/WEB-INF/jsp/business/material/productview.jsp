<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

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
	
	<input type="hidden" id="recordsTotal"  >
	<input type="hidden" id="productRecordId"  >
	<input type="hidden" id="parentId"  >
	
<fieldset style="float: left;width: 65%;">
	<legend>产品信息</legend>

	<table class="form" >		
		<tr>
			<td class="label" style="width: 100px;"><label>产品编号：</label></td>
			<td style="width: 150px;">
				<label><a href="#" onClick="doEditMaterial('${product.recordId}','${product.parentId}')">${product.materialId}</a></label></td>
								
			<td class="label" style="width: 100px;"><label>产品名称：</label></td>
			<td colspan="3">${product.materialName}</td>												
		</tr>
		<tr>				
			<td class="label" style="width: 100px;"><label>分类编码：</label></td>
			<td colspan="3">${product.categoryIdAndName}</td>				
								
			<td class="label" style="width: 100px;"><label>计量单位：</label></td>
			<td style="width: 50px;text-align: center;">${product.dicName}</td>				
		</tr>
		<tr>
			<td class="label">机器型号：</td>
			<td>${product.productModel }</td>			
			<td class="label">客户名称：</td>
			<td colspan="3">${product.customerId }</td>
		</tr>
		<tr>
			<td class="label" style="vertical-align: text-top;">中文描述：</td>
			<td colspan="5" style="vertical-align: text-top;"><pre>${product.description }</pre></td>
		</tr>	
	</table>
	<table class="form" id="table_form2" style="margin-top: 6px;">
				
		<tr>
			<td class="td-center"><label></label></td>	
			<td class="td-center"><label>材料成本</label></td>	
			<td class="td-center"><label>人工成本</label></td>
			<td class="td-center"><label> BOM成本</label></td>
			<td class="td-center"><label>基础成本</label></td>
			<td class="td-center" style="width: 100px;"><label>经管费率</label></td>
			<td class="td-center" style="width: 150px;"><label>核算成本</label></td>
		</tr>	
		<tr>
			<td class="td-center"><label>最近报价</label></td>	
			<td class="td-center"><span id=materialCost1></span></td>
			<td class="td-center"><span id=laborCost1></span></td>
			<td class="td-center"><span id=bomCost1></span></td>
			<td class="td-center"><span id=baseCost1></span></td>
			<td class="td-center"><span id=costRate1></span></td>
			<td class="td-center"><span id=totalSpan1></span></td>
		</tr>	
		<tr>
			<td class="td-center"><label>当前价格</label></td>	
			<td class="td-center"><span id=materialCost></span></td>
			<td class="td-center"><span id=laborCost></span></td>
			<td class="td-center"><span id=bomCost></span></td>
			<td class="td-center"><span id=baseCost></span></td>
			<td class="td-center">
				<input type="text" id="costRate" class="num mini" value="" style="text-align: center;"/>%</td>
			<td class="td-center"><span id=totalSpan></span>
				<input type="hidden" id="totalCost"/></td>
		</tr>									
	</table>
	<div class="action" style="text-align: right;">
		<button type="button" id="doProductDesign" class="DTTT_button" >做单资料</button>
		<button type="button" id="doOrderEdit" class="DTTT_button" >订单录入</button>
		<button type="button" id="doEdit" class="DTTT_button" >修改费率</button>
		<button type="button" id="doSave" class="DTTT_button" >保存</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</div>
	</fieldset>	
		<div id="tabs" style="float:right;margin: 10px 30px 0px 0px;">
				<div id="tabs-1" title="图片">
					<jsp:include page="../../common/album/album2.jsp"></jsp:include>
				</div>
		</div>
		
	<dl class="collapse" style="width: 98%;margin-left:10px">
		<dt style="width: 80%;"><span id="bomId">基础BOM</span></dt>
		<button type="button" class="DTTT_button" onclick="openPrint();" style="float: right;margin-top: -27px;">打印</button>
		<button type="button" class="DTTT_button" onclick="downloadExcel();" style="float: right;margin-top: -27px;">Excel下载</button>
		<button type="button" class="DTTT_button" onclick="doCreateBaseBom();" style="float: right;margin-top: -27px;">编辑</button>
		<dd>
		<table id="baseBomTable" class="display" style="width: 98%;">
			<thead>				
				<tr>
					<th width="1px">No</th>
					<th class="dt-center" width="180px">物料编码</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" width="30px">单位</th>
					<th class="dt-center" width="70px">供应商编号</th>
					<th class="dt-center" width="60px">用量</th>
					<th class="dt-center" width="60px">单价</th>
					<th class="dt-center" width="60px">总价</th>
				</tr>
			</thead>
			
		</table>
		</dd>
	</dl>
<div style="clear: both"></div>		
	
<fieldset style="margin-top: 10px;">
	<legend>客户报价</legend>
	<div class="list">	
		<a class="DTTT_button" onclick="doCreateQuotation();" >新建</a>	
		<table id="TQuotation"  class="display dataTable">
			<thead>				
				<tr>
					<th style="width:  1px;"  class="dt-middle ">No</th>
					<th style="width: 80px;"  class="dt-middle ">报价时间</th>
					<th style="width:120px;"  class="dt-middle ">BOM编号</th>
					<th style="width: 80px;"  class="dt-middle ">材料成本</th>
					<th style="width: 80px;"  class="dt-middle ">核算成本</th>
					<th style="width: 80px;"  class="dt-middle ">退税额</th>
					<th style="width: 80px;"  class="dt-middle ">报价</th>
					<th style="width: 60px;"  class="dt-middle ">换汇</th>
					<th style="width: 60px;"  class="dt-middle ">原币价格</th>
					<th style="width: 50px;"  class="dt-middle ">折扣</th>
					<th style="width: 50px;"  class="dt-middle ">因子</th>
					<th style="width: 60px;"  class="dt-middle ">利润率</th>
					<th style="width:  1px;"  class="dt-middle "></th>
				</tr>
			</thead>
		</table>
	</div>
</fieldset>
</form:form>
</div>
</div>
<script type="text/javascript">

$(document).ready(function() {
		

	baseBomView();//显示基础BOM

	quotationView();//供应商单价显示处理
	
	$("#costRate").attr('readonly',true);
	$("#costRate").addClass('read-only');
	$("#doSave").hide();
	
	$("#doEdit").click(function() {
		$("#costRate").attr('readonly',false);
		$("#costRate").removeClass('read-only');
		$("#doSave").show();
		$("#doEdit").hide();
	});
	
	$("#goBack").click(function() {
		var materialId='${product.materialId}';
		var url = "${ctx}/business/material?methodtype=productInit&keyBackup="+materialId;
		location.href = url;		
	});
	
	$("#doProductDesign").click(function() {
		var materialId='${product.materialId}';
		var name ='${product.materialName}';
		var goBackFlag = "productView";
		var url = "${ctx}/business/productDesign?methodtype=detailViewHistory"
				+"&keyBackup="+materialId
				+"&productId="+materialId
				+"&goBackFlag="+goBackFlag;
				
		callProductDesignView("ProductView",url);	
	});
	
	$("#doOrderEdit").click(function() {
		var materialId='${product.materialId}';
		var name ='${product.materialName}';
		var goBackFlag = "productView";
		var url = "${ctx}/business/order?methodtype=createByMaterialId"
				+"&keyBackup="+materialId
				+"&materialId="+materialId
				+"&goBackFlag="+goBackFlag;
		//url =encodeURI(encodeURI(url));//中文两次转码
		location.href = url;		
	});

	$("#doSave").click(function() {
		var costRate  = $('#costRate').val();
		var totalCost = $('#totalCost').val();
		var bomId  = $('#bomId').text();
		var url = "${ctx}/business/bom?methodtype=updateBomPlan";
		url = url + "&costRate="+costRate+"&totalCost="+totalCost+"&bomId="+bomId;

		$.ajax({
			type : "post",
			url : url,
			//async : false,
			//data : null,
			dataType : "text",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {			

				$().toastmessage('showNoticeToast', "保存成功。");
				$("#costRate").val(floatToCurrency(costRate));
				$("#costRate").attr('readonly',true);
				$("#costRate").addClass('read-only');
				$("#doSave").hide();
				$("#doEdit").show();
			},
			 error:function(XMLHttpRequest, textStatus, errorThrown){
				//alert(textStatus)
			}
		});		

	});
	
	
	

	
	//经管费计算
	$("#costRate").change(function() {

		var rate = $(this).val();
		var baseCost = $('#baseCost').text();
		
		rate = currencyToFloat(rate);
		baseCost = currencyToFloat(baseCost);
		
		var total = floatToCurrency( baseCost * ( 1 + rate / 100) );
		
		$('#totalCost').val(total);
		$('#totalSpan').html(total);
	});
    	
});
</script>
<script type="text/javascript">
//列合计:总价
function productCostSum(){

	var sum = 0;
	$('#baseBomTable tbody tr').each (function (){
		
		var vtotal = $(this).find("td").eq(7).text();
		var ftotal = currencyToFloat(vtotal);
		
		sum = currencyToFloat(sum) + ftotal;			
	})
	return sum;
}

//列合计:人工成本
function laborCostSum(){

	var sum = 0;
	$('#baseBomTable tbody tr').each (function (){
		
		var materialId = $(this).find("td").eq(1).text();
		var vtotal = $(this).find("td").eq(7).text();

		//判断是否是人工成本
		if(materialId != '' && materialId.substring(0,1) == 'H'){
			var ftotal = currencyToFloat(vtotal);
			sum = currencyToFloat(sum) + ftotal;	
		}
		
				
	})		
	return sum;
}

var prev ='0';
var subIndex = '0';//
function rowNoShow(next){
	if(next == prev){
		subIndex++;
		
	}else{
		subIndex = "1";
		prev = next;
	}

	//alert(subIndex+"::返回值")
	return subIndex;
}

//编辑基础BOM
function doCreateBaseBom() {
	var materialId ='${product.materialId}';
	var productModel = '${product.productModel }';
	var accessFlg = $('#recordsTotal').val();
	if(accessFlg > 0){
		var url = '${ctx}/business/bom?methodtype=editBaseBom&materialId=' + materialId+'&model='+productModel;

	}else{
		var url = '${ctx}/business/bom?methodtype=createBaseBom&materialId=' + materialId+'&model='+productModel;	
	}
	location.href = url;
	
}

function baseBomView() {

	var materialId='${product.materialId}';
	//alert(materialId)
	var table = $('#baseBomTable').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var t2 = $('#baseBomTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"ordering":false,
		"async" : true,
		"sAjaxSource" : "${ctx}/business/bom?methodtype=getBaseBom&materialId="+materialId,	
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					
					fnCallback(data);
					
					if(data['recordsTotal'] == '0')
						return;
					
					$('#recordsTotal').val(data['recordsTotal']);
					
					var recordId  = data['data'][0]['productRecord'];
					var bomId     = data['data'][0]['bomId'];
					var parentId  = data['data'][0]['productParentId'];
					var costRote  = data['data'][0]['managementCostRate'];
					if(costRote == null || costRote =='')
						costRote = '5.00';//默认值设定
					
					var laborCost = laborCostSum();
					var bomCost   = productCostSum();
					var mateCost  = bomCost - laborCost;
					var baseCost  = bomCost * 1.1;
					var totalCost = baseCost * ( 1 + costRote / 100 );

					mateCost  =  floatToCurrency( mateCost );
					bomCost   =  floatToCurrency( bomCost ) ;
					baseCost  =  floatToCurrency( baseCost ) ;
					totalCost =  floatToCurrency( totalCost );
					laborCost =  floatToCurrency( laborCost );

					$('#parentId').val(parentId);
					$('#bomId').html(bomId);
					$('#materialCost').html(mateCost);
					$('#laborCost').html(laborCost);
					$('#bomCost').html(bomCost);
					$('#baseCost').html(baseCost);
					$('#totalCost').val(totalCost);
					$('#totalSpan').html(totalCost);
					$('#costRate').val(floatToCurrency(costRote));
					$('#productRecordId').val(recordId);
					
					subIndex = '0';
					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             alert(errorThrown)
				 }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-left'},
			{"data": "materialId"},
			{"data": "materialName"},
			{"data": "unit","className" : 'td-center'},
			{"data": "supplierId"},
			{"data": "quantity","className" : 'td-right'},
			{"data": "price","className" : 'td-right'},
			{"data": "totalPrice","className" : 'td-right'},
		 ],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
    			
    			var subbomno = row["subbomno"];
    			var rownum = row["rownum"];
    			var setNo = rowNoShow(subbomno);			    			
    			return subbomno + '-' + setNo;
    		}},
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];				    			
    			name = jQuery.fixedWidth(name,40);				    			
    			return name;
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var materialId = row["materialId"];
    			rtn= "<a href=\"###\" onClick=\"doEditMaterial('" + row["rawRecordId"] +"','"+ row["parentId"] + "')\">"+materialId+"</a>";
    			return rtn;
    		}},
    		{"targets":4,"render":function(data, type, row){
    			var supplierId = row["supplierId"];
    			rtn= "<a href=\"###\" onClick=\"doShowSupplier('" + row["supplierId"] +"')\">"+supplierId+"</a>";
    			return rtn;
    		}},
    		{"targets":6,"render":function(data, type, row){
    			
    			return formatNumber( row["price"] );

    		}},
    		{"targets":7,"render":function(data, type, row){
    			
    			var price = currencyToFloat( row["price"] );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var total = formatNumber( price * quantity );			    			
    			return total;
    		}}
          
        ] 
	});
	
	t2.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t2.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});

	t2.on('order.dt search.dt draw.dt', function() {
		t2.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			//var num   = i + 1;
			//cell.innerHTML = num ;
		});
	}).draw();
	
}//ajax()供应商信息

function quotationView() {

	var table = $('#TQuotation').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	
	var materialId='${product.materialId}';

	var t = $('#TQuotation').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : true,
		"sAjaxSource" : "${ctx}/business/quotation?methodtype=getQuotationBom&materialId="+materialId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {			
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					
						fnCallback(data);
						if(data["recordsTotal"] == "0")
							return;
						var mateCost1  = data['data'][0]['materialCost'];
						var laborCost1 = data['data'][0]['laborCost'];
						var bomCost1   = data['data'][0]['bomCost'];
						var baseCost1  = data['data'][0]['productCost'];
						var totalCost1 = data['data'][0]['totalCost'];
						var costRote1  = data['data'][0]['managementCostRate'];

						$('#materialCost1').html(floatToCurrency( mateCost1 ));
						$('#laborCost1').html(floatToCurrency( laborCost1 ));
						$('#bomCost1').html(floatToCurrency( bomCost1 ));
						$('#baseCost1').html(floatToCurrency( baseCost1 ));
						$('#totalSpan1').html(floatToCurrency( totalCost1 ));
						$('#costRate1').html(costRote1);
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	                 //alert(XMLHttpRequest.status);
	                 //alert(XMLHttpRequest.readyState);
	                 alert(textStatus);
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "planDate"},
			{"data": "bomId"},
			{"data": "materialCost","className" : 'td-right'},
			{"data": "totalCost","className" : 'td-right'},
			{"data": "rebate","className" : 'td-right'},
			{"data": "exchangePrice","className" : 'td-right'},
			{"data": "exchangeRate","className" : 'td-right'},
			{"data": "RMBPrice","className" : 'td-right'},
			{"data": "discount","className" : 'td-right'},
			{"data": "commission","className" : 'td-right'},
			{"data": "profitRate","className" : 'td-right'},
			{"data": null,"className" : 'td-center'},
        ],
		"columnDefs":[
      		{"targets":2,"render":function(data, type, row){
      			var bomId = row["bomId"];
      			var materialId = row["materialId"];
      			var accessFlg = row["recordId"];
      			rtn= "<a href=\"###\" onClick=\"doEditQuotation('" + row["bomId"] +"','"+ row["materialId"] + "','"+ row["subId"] + "')\">"+bomId+"</a>";
      			return rtn;
      		}},
      		{"targets":6,"render":function(data, type, row){
      			var price = row["exchangePrice"];
      			var curry = row["currency"];      			
      			return floatToSymbol(price,curry);
      		}},
      		{"targets":9,"render":function(data, type, row){
      			var v = row["discount"];      			
      			return v + "%";
      		}},
      		{"targets":10,"render":function(data, type, row){
      			var v = row["commission"];      			
      			return v + "%";
      		}},
      		{"targets":11,"render":function(data, type, row){
      			var rate = row["profitRate"];      			
      			return rate + "%";
      		}},
      		{"targets":12,"render":function(data, type, row){
      			var rtn="";
      			rtn += "<a href=\"###\" onClick=\"doBomDiff('" + row["bomId"] + "')\">BOM比对</a>";
      			rtn += "<br />"
      			rtn += "<a href=\"###\" onClick=\"doDelete('" + row["recordId"] + "')\">删除</a>";	    		
      			return rtn;
      		}}
        ] 
	});

	t.on('order.dt search.dt draw.dt', function() {
		t.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();
	
}//ajax()供应商信息

var layerHeight = '360px';
var layerWidth  = '900px';


function doEditMaterial(recordid,parentid) {
	//var height = setScrollTop();
	//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
	var url = '${ctx}/business/material?methodtype=detailView';
	url = url + '&parentId=' + parentid+'&recordId='+recordid+'&keyBackup=1';
	
	layer.open({
		offset :[30,''],
		type : 2,
		title : false,
		area : [ '1000px', '500px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  $('#baseBomTable').DataTable().ajax.reload(null,false);
		  return false; 
		}    
	});		

};

function doShowSupplier(recordid) {

	var url = "${ctx}/business/supplier?methodtype=showById&key=" + recordid;
	
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '500px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  return false; 
		}    
	});		

};

function doCreateQuotation() {
	var bomId  = $('#bomId').text();
	var parentId  = $('#parentId').val();
	var materialId ='${product.materialId}'; 
	if(bomId =='') return;
	var url = "${ctx}/business/quotation?methodtype=createQuotation";
	url = url + "&bomId="+bomId +"&materialId="+materialId+"&parentId="+parentId;
	location.href = url;

};
function doEditQuotation(bomId,materialId,subId) {
	if(bomId =='') return;
	var url = "${ctx}/business/quotation?methodtype=showQuotation";
	url = url + "&bomId="+bomId +"&materialId="+materialId+"&subId="+subId;//
	location.href = url;

};

function doBomDiff(bomId) {

	var baseBomId = $('#bomId').text();

	var url = "${ctx}/business/quotation?methodtype=showBomDiff";
	url = url + "&bomId="+bomId +"&baseBomId="+baseBomId;
	
	callWindowFullView("BOM对比",url);	

}

function doDelete(recordId){
	
	
	if (recordId != ""){ //
		$.ajax({
			type : "post",
			url : "${ctx}/business/quotation?methodtype=deleteQuotation&recordId="+recordId,
			async : false,
			data : 'key=' + recordId,
			dataType : "text",
			success : function(data) {
				//$('#TQuotation').DataTable().ajax.reload(null,true);
				quotationView();
				$().toastmessage('showNoticeToast', "删除成功。");	
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
	
<script type="text/javascript">

function openPrint() {
	
	var url = '${ctx}/business/requirement?methodtype=printRequirement';
	var materialId = '${product.materialId}';
	var bomId =  $('#bomId').text();
	url = url + '&materialId=' + materialId+ '&bomId=' + bomId;

	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1000px','500px' ], 
		scrollbar : true,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		 // documentaryShow();
		  return false; 
		}    
	});		
}
 

function downloadExcel() {

	var materialId='${product.materialId}';
	var url = '${ctx}/business/bom?methodtype=downloadExcelForBaseBom'
			 + "&materialId=" + materialId;
	url =encodeURI(encodeURI(url));//中文两次转码
	//"sAjaxSource" : "${ctx}/business/bom?methodtype=getBaseBom&materialId="+materialId,	

	location.href = url;
}

</script>
</body>
</html>
