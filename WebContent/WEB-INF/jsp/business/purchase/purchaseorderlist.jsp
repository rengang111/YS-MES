<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购合同--合同列表</title>
<%@ include file="../../common/common2.jsp"%>
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
	
	function initEvent(){
		
		$('#contractTable').DataTable().on('click', 'tr', function() {
			
			//$(this).toggleClass('selected');
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	
	        	$('#contractTable').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            
	            var d = $('#contractTable').DataTable().row(this).data();
				//alert(d["bid_id"]);
				
				$('#contractDetail').DataTable().destroy();
				//$('#set_lines').DataTable().ajax.reload();
				//ajax_factory_bid_set_lines(d["bid_id"]);
				//var d = $('#contractTable').DataTable().row(this).data();
				//alert(d["contractId"])
				contractDetailView(d["contractId"]);
					            
	        }
			
		});
		
	}
	
	$(document).ready(function() {
		

		contractTableView();//采购合同
		
		initEvent();
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/contract";
					location.href = url;		
		});

	
		$("#print").click(function() {
			var YSId = '${order.YSId}';
			var bomId=$('#bomId').text();
			var url = "${ctx}/business/requirement?methodtype=printRequirement&YSId="+YSId+"&bomId="+bomId;
			location.href = url;
		});
		
		$("input:text").focus (function(){
		    $(this).select();
		});

		$(".DTTT_container").css('float','left');


		
	
	});

</script>

</head>
<body>

<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;">耀升编号：</td>					
					<td style="width:150px;">${order.YSId}</td>
								
					<td class="label" style="width:100px;">产品编号：</td>					
					<td style="width:150px;">${order.materialId}</td>
				
					<td class="label" style="width:100px;">产品名称：</td>				
					<td>${order.materialName}</td>
				</tr>
				<tr>
					<td class="label">ＰＩ编号：</td>
					<td>${order.PIId}</td>

					<td class="label">订单数量：</td>
					<td>${order.quantity}（${order.unit}）</td>
						
					<td class="label">客户名称：</td>
					<td>${order.customerFullName}</td>
				</tr>							
			</table>
		</fieldset>
			<button type="button" id="goBack" class="DTTT_button goBack" style="float: right;margin: -15px 20px 0px 0px;">返回</button>
		
		<fieldset>
			<legend> 合同一览</legend>
			<table id="contractTable" class="display" style="width:98%">
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:120px">合同编号</th>
						<th class="dt-center" style="width:100px">供应商简称</th>
						<th class="dt-center" >供应商名称</th>
						<th class="dt-center" style="width:80px">下单日期</th>
						<th class="dt-center" width="80px">交货日期</th>
						<th class="dt-center" width="100px">合同金额</th>
						<th class="dt-center" width="30px"></th>
					</tr>
				</thead>			
			</table>
		</fieldset>
		<br/>
		<fieldset style="min-height: 300px;">
		<legend>合同明细</legend>
		<div class="list">
			<table id="contractDetail" class="display">
				<thead>
				<tr>
					<th style="width:30px">No</th>
					<th style="width:150px">ERP编码</th>
					<th>物料名称</th>
					<th style="width:80px">数量</th>
					<th style="width:60px">单价</th>
					<th style="width:50px">单位</th>
					<th style="width:80px">金额</th>
				</tr>
				</thead>
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tfoot>
			</table>
		</div>
		</fieldset>
		
		<div style="clear: both"></div>
	</form:form>

</div>
</div>


<script  type="text/javascript">

function contractTableView() {

	var YSId='${order.YSId}';
	var table = $('#contractTable').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#contractTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
        "sScrollY": 250,
		"retrieve" : false,
		"async" : false,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/contract?methodtype=getContract&YSId="+YSId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){

					var record = data["recordsTotal"];
					//alert(record);
					if(record > 0)
						$( "#tabs" ).tabs( "option", "active", 3 );//设置默认显示内容
					
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
			{"data": null,"className" : 'td-center'},
			{"data": "contractId"},
			{"data": "supplierId"},
			{"data": "supplierName"},
			{"data": "purchaseDate","className" : 'td-center'},
			{"data": "deliveryDate","className" : 'td-center'},
			{"data": "total","className" : 'td-right'},
			{"data": null,"className" : 'td-right'}
        ] ,
		"columnDefs":[
    		{"targets":1,"render":function(data, type, row){
    			var contractId = row["contractId"];
    			rtn= "<a href=\"###\" onClick=\"showControctDetail('" + contractId +"')\">"+contractId+"</a>";
    			return rtn;
    		}},
    		{"targets":7,"render":function(data, type, row){
    			var contractId = row["contractId"];
    			//rtn = "<button type=\"button\" id=\"doReport\" class=\"DTTT_button\">提交</button>";
    			rtn= "<a href=\"###\" onClick=\"showContract('" + row["supplierId"] +"','"+ row["YSId"] + "')\">"+"打印"+"</a>";
    			return rtn;
    		}},
      		{"targets":6,"render":function(data, type, row){
      			var total = currencyToFloat( row["total"] );			    			
      			totalPrice = floatToCurrency(total);			    			
      			return totalPrice;
      		}}
            
          ] 
       	
	});
	

	t2.on('order.dt search.dt draw.dt', function() {
		t2.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();

	
}//ajax()供应商信息


function contractDetailView(contractId) {

	//var YSId='${order.YSId}';
	var table = $('#contractDetail').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#contractDetail').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/contract?methodtype=getContractDetail&contractId="+contractId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){

					
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
			{"data": null,"className" : 'td-center'},
			{"data": "materialId"},
			{"data": "materialName"},
			{"data": "quantity","className" : 'td-right'},
			{"data": "price","className" : 'td-right'},
			{"data": "unit","className" : 'td-center'},
			{"data": "totalPrice","className" : 'td-right'}
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
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();

	
} // ajax()供应商信息

function showContract(supplierId,YSId) {
	//accessFlg:1 标识新窗口打开
	var url = '${ctx}/business/requirement?methodtype=contractPrint';
	url = url + '&YSId=' + YSId+'&supplierId='+supplierId;
	//alert(YSId)
	//"sAjaxSource" : "${ctx}/business/contract?methodtype=getContractDetail&supplierId="+supplierId,	
	
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '520px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  baseBomView();
		  return false; 
		}    
	});		

};

function showControctDetail(contractId) {

	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	location.href = url;
}
</script>

</body>
	
</html>
