<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>配件订单-领料单查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

function historyAjax() {
	
	var YSId = '${peiYsid }';
	
	var t = $('#example').DataTable({			
		"paging": false,
		"lengthChange":false,
		"lengthMenu"  :[50,100,200],//设置一页展示20条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"ordering "	: true,
		"searching" : false,
		"retrieve" 	: true,
		"dom" 		: '<"clear">rt',
		"sAjaxSource"  : "${ctx}/business/requisition?methodtype=getRequisitionHistoryParts&YSId="+YSId,
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
					fnCallback(data);
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
		
		"columns" : [
		           {"data": null,"className":"dt-body-center"
				}, {"data": "requisitionId", "defaultContent" : '（待申请）'
				}, {"data": "YSId"
				}, {"data": "materialId"
				}, {"data": "materialName"
				}, {"data": "totalQuantity","className":"td-right"
				}, {"data": "requisitionDate"
				}, {"data": "requisitionQty","className":"td-right"
				}, {"data": "stockoutQty","className":"td-right"
				}, {"data": null,"className":"dt-body-center"
				}
				
			],
			"columnDefs":[

				{"targets":4,"render":function(data, type, row){
					
					var name = row["materialName"];				    			
					name = jQuery.fixedWidth(name,42);				    			
					return name;
				}},
	    		{"targets":9,"render":function(data, type, row){
	    			var stockoutQty = currencyToFloat( row["stockoutQty"] );
	    			var id = row["requisitionId"];
	    			
	    			var rtn= "";
	    			if( stockoutQty <= 0 ){//未出库的可以删除
		    			//rtn =  "<a href=\"###\" onClick=\"doEdit('" + row["requisitionRecordId"] + "')\">编辑</a>";
		    			rtn == "<a href=\"###\" onClick=\"doDeleteRequisition('" + row["requisitionRecordId"] + "')\">删除</a>";
	    				
	    			}
	    			if( id == null || id == "")
	    				rtn = "";
	    			
		    		return rtn;
	    		}},
	    	] 
		
	}).draw();
					
	t.on('click', 'tr', function() {
		
		var rowIndex = $(this).context._DT_RowIndex; //行号			
		//alert(rowIndex);

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
		
	});
	
	t.on('order.dt search.dt draw.dt', function() {
		t.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			cell.innerHTML = i + 1;
		});
	}).draw();

};
	
	$(document).ready(function() {
		
		historyAjax();
		
		$(".goBack").click(
				function() {
					
					var url = "${ctx}/business/requisition?methodtype=partsMainInit";
					
					location.href = url;			
				});
		
		$("#doAdd").click(
				function() {					
					var YSId = '${peiYsid }';
					var url =  "${ctx}/business/requisition?methodtype=peiAddinit&YSId="+YSId;

					location.href = url;
		});

		$("#doEdit").click(
				function() {

					var YSId = '${peiYsid }';
					var url = '${ctx}/business/requisition?methodtype=updateInitParts&YSId='+YSId;
					location.href = url;
		});

		$("#doDelete").click(
				function() {
					var YSId = '${peiYsid }';
					var url = '${ctx}/business/requisition?methodtype=deleteInitParts&YSId='+YSId;
					location.href = url;
		});
		
	});
	
	function doEdit(YSId,requisitionId) {

		var url = '${ctx}/business/requisition?methodtype=updateInitParts'
				+YSId+'&requisitionId='+requisitionId;
		location.href = url;
	}
	

	function doShowOrder() {

		var PIId = $("#piid").text();
		var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;

		callWindowFullView("订单信息",url);
	}
	
	function doDeleteRequisition(recordId) {
		if(confirm("删除后不能恢复,确定要删除吗？")) {
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : '',
				url : "${ctx}/business/requisition?methodtype=delete"+"&recordId="+recordId,
				success : function(data) {

					$().toastmessage('showNoticeToast', "删除成功。");	
					
					$('#example').DataTable().ajax.reload(null,false);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			});
		}
		
	}
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<input type="hidden" id="goBackFlag" />
	<form:hidden path="requisition.ysid"  value="${order.YSId }" />
	<!-- <fieldset>
		<legend> 领料单</legend>
		<table class="form" id="table_form">
			<tr> 	
					
				<td class="label" width="100px">PI编号：</td>					
				<td width="100px"><a href="###"  onclick="doShowOrder();"><span id="piid">${head.PIId }</span></a></td>
				
				<td class="label" width="100px">领料单编号：</td>					
				<td width="100px">${head.requisitionId }</td>
							
				<td class="label" width="100px">申请日期：</td>					
				<td>${head.requisitionDate }</td>
				 
				<td class="label" width="100px">申请人：</td>					
				<td width="120px">${head.materialId }</td>
					 		
			</tr>
					
										
		</table>
	</fieldset> 
	-->
<div style="clear: both"></div>
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 20px;">
		<a class="DTTT_button DTTT_button_text" id="doAdd" >继续领料</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	
	<fieldset>
		<legend> 领料详情</legend>
		<div class="list">		
			<table id="example" class="display" style="width:100%">
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th class="dt-center" width="70px">领料单编号</th>
						<th class="dt-center" width="70px">耀升编号</th>
						<th class="dt-center" width="120px">物料编号</th>
						<th class="dt-center" >物料名称</th>	
						<th class="dt-center" width="60px">订单数量</th>
						<th class="dt-center" width="60px">申请日期</th>
						<th class="dt-center" width="60px">申请数量</th>
						<th class="dt-center" width="60px">出库数量</th>
						<th class="dt-center" width="30px"></th>
					</tr>
				</thead>
			</table>
		</div>
	</fieldset>
		
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	openLayer(url);

};


function doPrint(requisitionId) {
	var YSId = '${head.YSId }';
	var url = '${ctx}/business/requisition?methodtype=print';
	url = url +'&YSId='+YSId;
	url = url +'&requisitionId='+requisitionId;
		
	callProductDesignView("print",url);

};

</script>

</html>
