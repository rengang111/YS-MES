<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
<title>日常采购-新建</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

		
	$.fn.dataTable.TableTools.buttons.reset = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {
					
					var t=$('#example').DataTable();
					
					rowIndex = t.row('.selected').index();
					
					if(typeof rowIndex == "undefined"){				
						$().toastmessage('showWarningToast', "请选择要删除的数据。");	
					}else{
						if(confirm("删除后不可恢复，确定要删除吗？")) {

							t.row('.selected').remove().draw();
							$().toastmessage('showNoticeToast', "删除成功。");	
							weightsum();
						}	
					}
						
				}
			
			});
	
	function ajaxRawGroup() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
	       	"dom"		  : 'T<"clear">rt',
			"tableTools" : {

				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

				"aButtons" : [ 
				{
					"sExtends" : "reset",
					"sButtonText" : "删除行"
				}],
			},
			
			"columns" : [ 
			           {"className":"dt-body-center"
					}, {
					}, {							
					}, {"className":"td-center"
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				],
				/*
			"columnDefs":[
	    		
	    		{"targets":2,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];
	    			name = jQuery.fixedWidth(name,30);
	    			
	    			return name;
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var rtn = "";
	    			var rowIndex = row["rownum"];
	    			var materialId = row["materialId"];
	    			rtn = '<a href="###" onClick="doShow('+'\''+ materialId +'\''+ ')">'+materialId+'</a>';
	    			rtn += '<input type="hidden" id="contract'+rowIndex+'.materialid" name="contract['+rowIndex+'].materialid" value="'+materialId+'">';
	    			return rtn;
	    		}}
         	] 
			*/
			
		}).draw();
		
		t.on('blur', 'tr td:nth-child(7)',function() {
			
	           $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
			
		t.on('change', 'tr td:nth-child(7)',function() {
			
            var $tds = $(this).parent().find("td");
			
            var $oQuantity  = $tds.eq(6).find("input");
			var $oThisPrice = $tds.eq(7).find("input");
			var $oAmounti   = $tds.eq(8).find("input:hidden");
			var $oAmounts   = $tds.eq(8).find("span");
			
			var fPrice    = currencyToFloat($oThisPrice.val());		
			var fQuantity = currencyToFloat($oQuantity.val());			
			var fTotalOld = currencyToFloat($oAmounti.val());
			
			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vPrice = floatToCurrency(fPrice);	
			var vQuantity = floatToNumber(fQuantity);
			var vTotalNew = floatToCurrency(fTotalNew);
					
			//详情列表显示新的价格
			$oThisPrice.val(vPrice);					
			$oQuantity.val(vQuantity);	
			$oAmounti.val(vTotalNew);	
			$oAmounts.html(vTotalNew);	

			//列合计
			weightsum();
			
		});
			
		t.on('click', 'tr', function() {
			
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

	};//ajaxRawGroup()
	
	$(document).ready(function() {

		var date = new Date(shortToday());
		date.setDate(date.getDate()+20);
		$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
		$("#contract\\.purchasedate").val(shortToday());
		
		$("#contract\\.purchasedate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 

		$("#contract\\.deliverydate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});
			
		
		ajaxRawGroup();		
		
		$("#contract\\.purchasedate").change(function(){
			
			var val = $(this).val();
			var date = new Date(val);
			date.setDate(date.getDate()+20);
			$("#contract\\.deliverydate").val(date.format("yyyy-MM-dd"));
		});	
		
		$("#goBack").click(
				function() {
					var goBackFlag = $('#goBackFlag').val();
					
					if(goBackFlag == "1"){
						//该页面来自于物料详情
						var recordId ="${ contract.materialRecordId }";
						var parentId ="${ contract.materialParentId }";
						var url = '${ctx}/business/material?methodtype=detailView';
						url = url + '&parentId=' + parentId+'&recordId='+recordId+'&keyBackup=2';
						
					}else{
						//该页面来自于供应商
						var supplierId="${ contract.supplierId }";
						var url = "${ctx}/business/supplier?keyBackup="+supplierId;
						
					}
					
					location.href = url;		
				});
		
		$("#insert").click(
				function() {			
			$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=createRoutineContract");
			$('#attrForm').submit();
		});
				
		//input格式化
		foucsInit();
		
		//列合计
		weightsum();

	});	
	
	//列合计
	function weightsum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(8).find("span").text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;
			
		})
		var fsum = floatToCurrency(sum);
		$('#weightsum').text(fsum);
		$('#contract\\.total').val(fsum);
		

	}
	
	function doShowMaterial(recordid,parentid) {
		//var height = setScrollTop();
		//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
		var url = '${ctx}/business/material?methodtype=detailView';
		url = url + '&parentId=' + parentid+'&recordId='+recordid+'&keyBackup=1';
		
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
			  $('#baseBomTable').DataTable().ajax.reload(null,false);
			  return false; 
			}    
		});		

	};
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
			
		<input type="hidden" id="goBackFlag"  value="${goBackFlag }"/>
		<form:hidden path="shortName"  value="${ contract.shortName }"/>
		<fieldset>
			<legend> 供应商</legend>
			<table class="form" id="table_form">
				
				<tr> 		
					<td class="label" style="width:120px">供应商编号：</td>					
					<td style="width:200px">&nbsp;${ contract.supplierId }
						<form:hidden path="contract.supplierid" value="${ contract.supplierId }"/></td>
									
					<td class="label" style="width:120px">供应商简称：</td>					
					<td style="width:200px">&nbsp;${ contract.shortName }</td>
						
					<td class="label" style="width:120px">供应商名称：</td>
					<td><div id="fullName">${ contract.supplierName }</div></td>
				</tr>	
				<tr> 
					<td class="label">下单日期：</td>
					<td><form:input path="contract.purchasedate" value="${ contract.purchaseDate }"/></td>
					<td class="label">合同交期：</td>
					<td colspan="3"><form:input path="contract.deliverydate" value="${ contract.deliveryDate }"/></td>
				</tr>								
			</table>
			
	</fieldset>
	
	<div style="clear: both"></div>		
	<fieldset>
	<legend> 物料详情</legend>
	
	<div class="list">
	<table id="example" class="display">	
		<thead>
		<tr>
			<th style="width:1px">No</th>
			<th style="width:150px">物料编码</th>
			<th>物料名称</th>
			<th style="width:30px">单位</th>
			<th style="width:60px">当前库存</th>
			<th style="width:60px">虚拟库存</th>
			<th style="width:80px">采购数量</th>
			<th style="width:50px">单价</th>
			<th style="width:70px">总价</th>
		</tr>
		</thead>		
		<tbody>
			<c:forEach var="detail" items="${detail}" varStatus='status' >	
				<tr>
					<td></td>
					<td>
						<a href="###" onClick="doShowMaterial('${detail.materialRecordId}','${detail.materialParentId}')">${detail.materialId}</a>
						<form:hidden path="detailList[${status.index}].materialid" value="${detail.materialId}" /></td>								
					<td><span id="name${status.index}">${detail.materialName}</span></td>					
					<td>${ detail.unit }</td>				
					<td>${ detail.accountingQuantity }</td>				
					<td>${ detail.availabelToPromise }</td>
					<td><form:input path="detailList[${status.index}].quantity" value="" class="num short"/></td>							
					<td>${detail.price}<form:hidden  path="detailList[${status.index}].price" value="${detail.price}"  class="cash short" /></td>
					<td><span>${ detail.totalPrice}</span><form:hidden  path="detailList[${status.index}].totalprice" value="${detail.totalPrice} "/></td>								
					
				</tr>
					
			</c:forEach>
			
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td class="td-right">合计:</td>
				<td class="td-right" style="padding-right: 2px;"><span id=weightsum></span>
					<form:hidden path="contract.total"/></td>
			</tr>
		</tfoot>
	</table>
	</div>
	</fieldset>
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="insert" class="DTTT_button">生成采购合同</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>		
		
</form:form>

</div>
</div>
</body>

	
</html>
