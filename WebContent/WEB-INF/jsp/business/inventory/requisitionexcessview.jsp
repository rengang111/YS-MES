<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>领料申请-领料单查看（超领）</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">	

	function detailAjax() {
		
		var t = $('#example').DataTable({
			
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
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
		        	{"className":"dt-body-center"
				}, {"className":"td-left"
				}, {
				}, {"className":"td-left"
				}, {"className":"td-left"
				}, {"className":"td-right","defaultContent" : '0'
				}, {"className":"td-right","defaultContent" : '0'//退还数量
				}, {"className":"td-center"
				}
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

	};
	$(document).ready(function() {
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/requisition?methodtype=excessInit";

					location.href = url;			
				});
		
		$("#doUpdate").click(
				function() {
					var YSId=$('#requisition\\.ysid').val();
					var requisitionId = $('#requisition\\.requisitionid').val();
					var methodtype = "excessEdit"
					var url =  "${ctx}/business/requisition?methodtype="+methodtype+"&YSId="+YSId+"&requisitionId="+requisitionId;

					location.href = url;
		});
		
		detailAjax();
	
	});
	
	

	function doDelete() {
		
		var stockOutId = $('#stockOutId').val();
		
		if(stockOutId == "" || stockOutId == null){

			if(confirm("删除后不能恢复,\n\n确定要删除吗？")) {

				var requisitionId = $('#requisition\\.requisitionid').val();
				var YSId = $('#requisition\\.ysid').val();
				var methodtype = "excessDelete"
				var url =  "${ctx}/business/requisition?methodtype="+methodtype
						+"&YSId="+YSId
						+"&requisitionId="+requisitionId;

				location.href = url;
			}
		}else{
			$().toastmessage('showWarningToast', "该申请已出库，不能删除。");
			return;
		}
		
	}
	
	function showContract(contractId) {
		
		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("采购合同",url);

		
	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<!-- 虚拟领料区分 -->
	<input type="hidden" id="virtualType" value="${virtualType }" />
	<input type="hidden" id="goBackFlag" />
	<form:hidden path="requisition.ysid"  value="${detail.YSId }" />
	<form:hidden path="requisition.requisitionid"  value="${detail.requisitionId }" />
	<input type="hidden" id="stockOutId"  value="${detail.stockOutId }"/>
	<fieldset>
		<legend> 领料单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">耀升编号：</td>					
				<td width="150px">${detail.YSId }</td>
				
				<td class="label">产品编号：</td>					
				<td>${detail.productId }</td>
							
				<td class="label">产品名称：</td>					
				<td>${detail.productName }</td>
			</tr>
			<tr>				
				<td class="label" width="100px">生产数量：</td>					
				<td>${detail.orderQty }</td>
				
				<td class="label" width="100px">领料单编号：</td>					
				<td width="150px">${detail.requisitionId }</td>
				
				<td class="label">领料日期：</td>					
				<td>${detail.requisitionDate }</td>
							
			</tr>
			<tr>				
				<td class="label" width="100px">超领原由：</td>					
				<td colspan="5"><pre>${detail.remarks }</pre></td>
							
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 20px;">
		<a class="DTTT_button DTTT_button_text" id="doUpdate" >编辑</a>
		<a class="DTTT_button DTTT_button_text" id="doDelete" onclick="doDelete();return false;">删除</a>
		<a class="DTTT_button DTTT_button_text" id="doPrint"  onclick="doPrint();return false;">打印领料单</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	
	<fieldset>
		<legend> 物料详情</legend>
		<div class="list">		
			<table id="example" class="display" style="width:100%">
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th class="dt-center" width="150px">物料编号</th>
						<th class="dt-center" >物料名称</th>	
						<th class="dt-center" width="100px">退款合同</th>	
						<th class="dt-center" width="80px">供应商</th>	
						<th class="dt-center" width="60px">超领数量</th>
						<th class="dt-center" width="60px">退还数量</th>
						<th width="10px"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="detail" items="${detailList}" varStatus='status' >							
						<tr>
							<td>${status.index + 1}</td>
							<td>${detail.materialId}</td>
							<td>${detail.materialName}</td>
							<td><a href="###" id="meteLink${status.index }" onClick="showContract('${detail.contractId}')">${detail.contractId}</a></td>
							<td>&nbsp;${detail.supplierId}</td>
							<td>${detail.quantity}</td>
							<td>${detail.scrapQuantity}</td>
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
</body>

<script type="text/javascript">


function doPrint() {
	var YSId = '${detail.YSId }';
	var requisitionId = '${detail.requisitionId }';
	var url = '${ctx}/business/requisition?methodtype=print';
	url = url +'&YSId='+YSId;
	url = url +'&requisitionId='+requisitionId;
	url = url +'&excessType=020';
		
	callProductDesignView("print",url);

};

</script>

</html>
