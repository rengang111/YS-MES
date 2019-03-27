<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-收货记录</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	
	function arrivalCountAjax() {
		
		var contractId = '${contract.contractId }';
		
		var t = $('#arrivalCount').DataTable({			
			"paging": false,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/arrival?methodtype=getArrivalHistoryCount&contractId="+contractId,
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
					}, {"data": "materialId"
					}, {"data": "materialName","className":""
					}, {"data": "contractQty","className":"td-right"
					}, {"data": "arrivalQty","className":"td-right"
					}, {"data": "refundQty","className":"td-right"
					}, {"data": "netArrivalQty","className":"td-right"
					}, {"data": "surplusQty","className":"td-right"
					}
				],
				"columnDefs":[
		    		
		    		{"targets":2,"render":function(data, type, row){
		    						    			
		    			name = jQuery.fixedWidth(data,54);				    			
		    			return name;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			
			    		return floatToCurrency(data);
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			
			    		return floatToCurrency(data);
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			
			    		return floatToCurrency(data);
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			
			    		return floatToCurrency(data);
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			
			    		return floatToCurrency(data);
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
	
function historyAjax() {
		
		var contractId = '${contract.contractId }';
		
		var t = $('#example2').DataTable({			
			"paging": false,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/arrival?methodtype=getArrivalHistory&contractId="+contractId,
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
					}, {"data": "arrivalId"
					}, {"data": "arriveDate","className":"dt-body-center"
					}, {"data": "materialId"
					}, {"data": "materialName"
					}, {"data": "unit","className":"dt-body-center"
					}, {"data": "quantity","className":"td-right"
					}, {"data": "checkResult","className":"dt-body-center"
					}, {"data":null,"className":"dt-body-center"
					}
				],
				"columnDefs":[
		    		{"targets":8,"render":function(data, type, row){
		    			var contractId = row["contractId"];	
		    			var status = row["statusId"];
		    			var rtn= "";
		    			if(status == '010'){//未检验的可以修改
			    			rtn= "<a href=\"###\" onClick=\"doEdit('" + row["contractId"] + "','" + row["arrivalId"] + "')\">编辑</a>";
			    			rtn = rtn +"&nbsp;"+ "<a href=\"###\" onClick=\"doDelete('" + row["recordId"] + "','" + row["contractId"] + "','" + row["materialId"] + "')\">删除</a>";
		    				
		    			}
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
		
		arrivalCountAjax();//收货统计
		
		historyAjax();//收货登记历史记录

		$("#goBack").click(
				function() {
					var makeType=$('#makeType').val();
					var url = "${ctx}/business/arrival?makeType="+makeType;
					location.href = url;		
		});
		
		
		$("#doCreate").click(
				function() {
				var contractId = '${contract.contractId}'
				var makeType=$('#makeType').val();
				var url = '${ctx}/business/arrival?methodtype=addAgainInit&contractId='+contractId+"&makeType="+makeType;
				location.href = url;
	
		});
		
		
	});
	
	function doEdit(contractId,arrivalId) {

		var makeType=$('#makeType').val();
		var url = '${ctx}/business/arrival?methodtype=edit&contractId='+contractId
				+'&arrivalId='+arrivalId+'&makeType='+makeType;
		location.href = url;
	}
	
	function doDelete(recordId,contractId,materialId) {
		
		if(confirm("删除后不能恢复,\n\n确定要删除吗？")) {
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : recordId,
				url : "${ctx}/business/arrival?methodtype=delete"+"&contractId="+contractId+"&materialId="+materialId,
				success : function(data) {
					$().toastmessage('showNoticeToast', "删除成功。");
					$('#example2').DataTable().ajax.reload(null,false);						
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
	
	<input type="hidden" id="makeType" value="${makeType }" />
	<fieldset>
		<legend> 基本信息</legend>
		<table class="form" id="table_form">
			<tr>
				<td class="label" style="width:100px"><label>耀升编号：</label></td>					
				<td style="width:200px">${contract.YSId }</td>
				<td class="label" style="width:100px"><label>供应商：</label></td>					
				<td>${contract.supplierId }（${contract.shortName }）${contract.fullName}</td>
								
				<td width="100px" class="label">采购员：</td>
				<td>${contract.purchaser }</td>	
			</tr>
			<tr>
				<td class="label" style="width:100px"><label>合同编号：</label></td>					
				<td  style="width:200px">${contract.contractId }</td>
				<td class="label" style="width:100px"><label>下单日期：</label></td>					
				<td style="width:200px">${contract.purchaseDate }</td>
				<td class="label" style="width:100px"><label>合同交期：</label></td>					
				<td>${contract.deliveryDate }</td>	
			</tr>								
		</table>
	</fieldset>
	
	<fieldset>
		<span class="tablename"> 收货统计</span>
		<a id="doCreate" href="###" >收货登记</a>
			<div class="list">	
			<table id="arrivalCount" class="display"  style="width:100%">
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" width="130px">物料编号</th>
						<th class="dt-center" >物料名称</th>
						<th class="dt-center" width="70px">合同数量</th>
						<th class="dt-center" width="60px">累计收货</th>
						<th class="dt-center" width="60px">累计退货</th>
						<th class="dt-center" width="60px">净收货</th>
						<th class="dt-center" width="60px">剩余数量</th>
					</tr>
				</thead>
			</table>
		</div>
	</fieldset>
	<div style="clear: both"></div>
	<fieldset>
		<legend>收货详情</legend>
			<div class="list">	
			<table id="example2" class="display"  style="width:100%">
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:60px">收货编号</th>
						<th class="dt-center" style="width:60px">收货日期</th>
						<th class="dt-center" width="120px">物料编号</th>
						<th class="dt-center" >物料名称</th>
						<th class="dt-center" width="40px">单位</th>
						<th class="dt-center" width="60px">收货数量</th>
						<th class="dt-center" style="width:50px">报检结果</th>
						<th class="dt-center" style="width:50px"></th>
					</tr>
				</thead>
			</table>
		</div>
	</fieldset>

</form:form>

</div>
</div>
</body>


</html>
