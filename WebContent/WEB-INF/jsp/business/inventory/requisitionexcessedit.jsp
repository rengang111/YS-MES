<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>领料申请-领料单编辑（超领）</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
		
	function ajax() {

		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : false,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : "200px",
	      // "scrollCollapse": false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"columns" : [
		        	{"className":"dt-body-center",//0
				}, {"className":"td-left"//
				}, {"className":"td-left"//2
				}, {"className":"td-right"//	
				}, {"className":"td-right"//4
				}
			]
			
		}).draw();
						
		t.on('click', 'tr', function() {
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

		var remarks = '${detail.remarks }';

		$("#requisition\\.remarks").val(replaceTextarea(remarks));
		
		//日期
		$("#requisition\\.requisitiondate").val(shortToday());
		
		
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/requisition?methodtype=excessInit";					
					location.href = url;			
				});

		$("#showHistory").click(
				function() {
					var YSId='${detail.YSId }';
					var requisitionId='${detail.requisitionId }'
					var url = "${ctx}/business/requisition?methodtype=excessDetail&YSId="+YSId+"&requisitionId="+requisitionId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {

			var virtualType = $('#virtualType').val();
			$('#formModel').attr("action", "${ctx}/business/requisition?methodtype=excessUpdate"+"&virtualType="+virtualType);
			$('#formModel').submit();
		});
		
		
		foucsInit();		

		ajax();
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/requisition?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
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
	<form:hidden path="requisition.recordid"  value="${detail.recordId }" />
	<form:hidden path="requisition.ysid"  value="${detail.YSId }" />
	<form:hidden path="requisition.requisitionid"  value="${detail.requisitionId }" />
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
				<td colspan="5"><form:textarea path="requisition.remarks" rows="2" cols="80" /></td>
							
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >编辑保存</a>
		<a class="DTTT_button DTTT_button_text" id="showHistory" >取消编辑</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>

	<fieldset style="margin-top: -30px;">
		<legend> 物料需求表</legend>
		<div class="list">
			<table id="example" class="display" style="width:100%">
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th class="dt-center" width="120px">物料编号</th>
						<th class="dt-center" >物料名称</th>	
						<th class="dt-center" width="80px">本次领料</th>
						<th class="dt-center" width="60px">退还数量</th>
					</tr>
				</thead>	
				<tbody>
					<c:forEach var="detail" items="${detailList}" varStatus='status' >							
						<tr>
							<td>${status.index + 1}</td>
							<td>${detail.materialId}
								<form:hidden path="requisitionList[${status.index}].materialid"  value="${detail.materialId}"/>
								<form:hidden path="requisitionList[${status.index}].subbomno"  value="${detail.subBomNo}"/></td>
							<td>${detail.materialName}</td>
							<td>
								<input type="text" 
									id="requisitionList'+index+'.quantity" 
									name="requisitionList[${status.index}].quantity" 
									class="quantity num mini"  
									value="${detail.quantity}"/></td>
							<td>
								<input type="text" 
									id="requisitionList'+index+'.scrapquantity" 
									name="requisitionList[${status.index}].scrapquantity" 
									class="quantity num mini"  
									value="${detail.scrapQuantity}"/></td>
							
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
</html>
