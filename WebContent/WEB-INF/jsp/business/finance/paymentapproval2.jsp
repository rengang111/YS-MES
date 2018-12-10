<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应付款-二级审核（编辑）</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
	
	function ajax(scrollHeight) {
		
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
		    "autoWidth":false,
			"dom"		: '<"clear">rt',
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},	
			"columns": [
				{"className" : 'td-center'},//
				{"className" : 'td-left'},//
				{"className" : 'td-left'},//
				{"className" : 'td-left'},//
				{"className" : 'td-center'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//
				{"className" : 'td-right'},//			
				
			],	
			
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

	};
	
	function materialzzAjax() {

		var makeType = $('#makeType').val();
		var taskId = $('#task\\.taskid').val();
		var YSId= $('#task\\.collectysid').val();
		var actionUrl = "${ctx}/business/requisitionzz?methodtype=getMaterialZZList";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&taskId="+taskId;
		actionUrl = actionUrl +"&makeType="+makeType;
				
		var t = $('#payment').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	       "autoWidth":false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"aaSorting": [[ 1, "DESC" ]],
			"sAjaxSource" : actionUrl,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				//var param = {};
				//var formData = $("#condition").serializeArray();
				//formData.forEach(function(e) {
				//	aoData.push({"name":e.name, "value":e.value});
				//});

				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					//"data" : JSON.stringify(aoData),
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
		        	{"data": null,"className":"dt-body-center"//0
				}, {"data": "YSId","className":"td-left", "defaultContent" : ''//5
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2
				}, {"data": "unit","className":"td-center"		//3单位
				}, {"data": "manufactureQuantity","className":"td-right"//4
				}, {"data": null, "defaultContent" : ''	//6 
				}
			],
			"columnDefs":[
	    		
	    		{"targets":3,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					//var inputTxt =       '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value=""/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			//inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			
	    			return name;
                }},
				{"targets":5,"render":function(data, type, row){	    			
	    			
	    			var qty = floatToCurrency(row["manufactureQuantity"]);			
	    			return qty;				 
                }}
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
		
		$('#payment\\.approvalfeedback').val(replaceTextarea('${payment.approvalFeedback}'));
		$("#goBack").click(
				function() {
					var url = "${ctx}/business/payment?methodtype=approvalMainL2";
					location.href = url;		
		});

		ajax();
		materialzzAjax();
		productPhotoView();
		
		//产品图片添加位置                                                                                                                                                                                    
		var productIndex = 1;
		$("#addProductPhoto").click(function() {
			
			var path='${ctx}';
			var cols = $("#productPhoto tbody td.photo").length - 1;
			//从 1 开始			
			var trHtml = addPhotoRow('productPhoto','uploadProductPhoto',productIndex,path);		

			$('#productPhoto td.photo:eq('+0+')').after(trHtml);	
			productIndex++;		
			//alert("row:"+row+"-----"+"::productIndex:"+productIndex)
		});
		
		var contract = contractSum(5);
		var minis = contractSum(6);
		var payment = contractSum(7);
		$('#contractTotal').html(floatToCurrency(contract));
		$('#minisTotal').html(floatToCurrency(minis));
		$('#paymentTotal').html(floatToCurrency(payment));		
		$('#payment\\.totalpayable').val(floatToCurrency(payment));
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/requisition?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}

	//列合计
	function contractSum(col){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(col).text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})
		return sum;
	}
	
	function doInsert(status) {
			var insertFlag = $("#insertFlag").val();	
			$("#payment\\.approvalstatus").val(status);	
			//alert($("#payment\\.approvalstatus").val())
			//return;
			$("#submit12").attr("disabled", "disabled");
		$('#formModel').attr("action", "${ctx}/business/payment?methodtype=approvalInsert2"+"&insertFlag="+insertFlag);
		$('#formModel').submit();
	};
	
	function doShowContract(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("采购合同",url);
	}

	function doShowSupplier(supplierId) {

		var url = '${ctx}/business/supplier?methodtype=showById&openFlag=newWindow&key=' + supplierId;
		
		callProductDesignView("供应商",url);
	}
	
	function doShowStockin(contractId) {

		var url = '${ctx}/business/storage?methodtype=showStockInByContractId&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("入库单",url);
	}
	
	function doPrintContract(contractId) {
	
		var url = '${ctx}/business/requirement?methodtype=contractPrint';
		url = url +'&contractId='+contractId;
		//alert(url)		
		callProductDesignView("打印合同",url);	

	}
	
</script>
<script type="text/javascript">

function productPhotoView() {

	var paymentId = $("#payment\\.paymentid").val();
	var supplierId = $("#payment\\.supplierid").val();

	$.ajax({
		"url" :"${ctx}/business/payment?methodtype=getProductPhoto&paymentId="+paymentId+"&supplierId="+supplierId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "POST",
		"data" : null,
		success: function(data){
				
			var countData = data["productFileCount"];
			
			photoView('productPhoto','uploadProductPhoto',countData,data['productFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
         	alert(errorThrown)
		 }
	});
	
}//产品图片


function photoView(id, tdTable, count, data) {
	
	var row = 0;
	for (var index = 0; index < count; index++) {
		var path = '${ctx}' + data[index];
		var pathDel = data[index];		
		var trHtml = showPhotoRow(id,tdTable,path,pathDel,index);		
		$('#' + id + ' td.photo:eq(' + row + ')').after(trHtml);
		row++;
	}
}

function deletePhoto(tableId,tdTable,path) {
	
	var url = '${ctx}/business/payment?methodtype='+tableId+'Delete';
	url+='&tabelId='+tableId+"&path="+path;
	    
	if(!(confirm("确定要删除该图片吗？"))){
		return;
	}
    $("#formModel").ajaxSubmit({
		type: "POST",
		url:url,	
		data:$('#formModel').serialize(),// 你的formid
		dataType: 'json',
	    success: function(data){
	    	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
			}
			
			//删除后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片删除失败,请重试。")
		}
	});
}

function uploadPhoto(tableId,tdTable, id) {

	var url = '${ctx}/business/paymentBillUpload'
			+ '?methodtype=uploadPhoto' + '&id=' + id;
	
	var paymentId = $('#payment\\.paymentid').val();
	if(paymentId == '（保存后自动生成）')
		$('#payment\\.paymentid').val('');//清除非正常ID

	$("#formModel").ajaxSubmit({
		type : "POST",
		url : url,
		data : $('#formModel').serialize(),// 你的formid
		dataType : 'json',
		success : function(data) {
	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					$('#payment\\.paymentid').val(data["paymentId"]);//设置新的ID
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
			}
			
			//添加后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片上传失败,请重试。")
		}
	});
}
</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<form:hidden path="payment.parentid"  />
	<form:hidden path="payment.recordid"  value="${payment.recordId }"/>
	<form:hidden path="payment.paymentid" value="${payment.paymentId }"/>
	<form:hidden path="payment.supplierid" value="${supplier.supplierId }" />
	<form:hidden path="payment.approvalstatus" value="" />
	
	<input type="hidden" id="insertFlag" value="${insertFlag }">
	<fieldset>
		<legend> 付款申请单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">申请单编号：</td>					
				<td width="150px">${payment.paymentId }</td>	
								
				<td class="label" width="100px">申请人：</td>					
				<td width="150px">${payment.applicantName }</td>
														
				<td width="100px" class="label">申请日期：</td>
				<td>${payment.requestDate }</td>				
			</tr>
			<tr> 				
				<td class="label" width="100px">供应商编号：</td>					
				<td width="150px">${supplier.supplierId }</td>
														
				<td width="100px" class="label">供应商简称：</td>
				<td width="150px">${supplier.shortName }</td>
														
				<td width="100px" class="label">供应商名称：</td>
				<td>${supplier.supplierName }</td>
			</tr>
			<tr>			
				<td class="label" width="100px">申请付款总额：</td>					
				<td class="font16" width="150px">${payment.totalPayable }</td>
								
				<td class="label" width="100px">付款条件：</td>					
				<td width="150px">入库后&nbsp;${supplier.paymentTerm }&nbsp;天</td>
														
				<td width="100px" class="label">付款类别：</td>
				<td width="150px">${payment.paymentType }</td>
				
				<td width="100px" class="label">付款状态：</td>
				<td class="bold">${payment.finishStatus }</td>
			</tr>										
		</table>
	</fieldset>
	<fieldset>
		<legend> 审核意见</legend>
		<table class="form" id="table_form2">
		 
		 
			<tr>
				<td class="label" width="100px">发票类型： </td>
				<td width="100px">${payment.invoiceType }</td>
			
				<td class="label" width="100px">发票编号： </td>
				<td width="150px">${payment.invoiceNumber }</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="4" width="700">
					<form:textarea path="payment.approvalfeedback" rows="5" cols="80" /></td>
					
				<td style="text-align: left;vertical-align: bottom;" >
					<button type="button" id="submit12"  onclick="doInsert('020');"
						class="DTTT_button" style="margin-bottom: 5px;">通过</button>
					<button type="button" id="submit12"  onclick="doInsert('030');"
						class="DTTT_button" style="margin-bottom: 5px;">不通过</button>
					<a class="DTTT_button " id="goBack" >返回</a></td>
			</tr>
											
		</table>
	</fieldset>
	
	<fieldset>
		<legend> 合同明细</legend>
		<div class="list">
		<table id="example" class="display" >
			<thead>				
				<tr>
					<th width="30px">No</th>
					<th width="120px">合同编号</th>
					<th width="120px">入库单号</th>
					<th width="80px">耀升编号</th>
					<th width="80px">约定付款日</th>
					<th width="100px">合同金额</th>
					<th width="100px">增减项总额</th>
					<th width="100px">应付款金额</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var='list' items='${contract}' varStatus='status'>	
					<tr>
						<td>${status.index+1 }</td>
						<td><a href="###" onClick="doShowContract('${list.contractId }')">${list.contractId }</a></td>
						<td><a href="###" onClick="doShowStockin('${list.contractId }')"><span id="receipt${status.index }">${list.receiptId }</span></a></td>
						<td>${list.YSId }</td>
						<td>${list.agreementDate }</td>
						<td>${list.totalPrice }</td>
						<td class="td-right"><span id="chargeback${status.index }"></span></td>
						<td class="td-right"><span id="payment${status.index }">${list.payable }</span></td>
						<td class="td-center">
							<a href="###" onClick="doPrintContract('${list.contractId }')">打印合同</a>&nbsp;&nbsp;
							<a href="###" onClick="doShowStockin('${list.contractId }')">打印入库单</a>
						</td>
							<form:hidden path="paymentList[${status.index }].contractid"  value="${list.contractId }" />
							<form:hidden path="paymentList[${status.index }].payable"  value="${list.total }" />
					</tr>
					<script type="text/javascript">
						//var contract = currencyToFloat('${list.totalPrice }');
						var chargeback = currencyToFloat('${list.chargeback }');
						//var payment = floatToCurrency( contract + chargeback );
						var index = ${status.index }
						//alert('payment--chargeback:'+chargeback+'---'+payment)
						//$('#payment'+index).html(payment);
						var receipt = '${list.receiptId }';
						$('#receipt'+index).html(jQuery.fixedWidth(receipt,12)); 
						$('#chargeback'+index).html(floatToCurrency(chargeback));
					</script>
				</c:forEach>
			</tbody>
			<!--  -->
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>合计：</td>
					<td><span id="contractTotal" style="font-weight: bold;"></span></td>
					<td><span id="minisTotal" style="font-weight: bold;"></span></td>
					<td><span id="paymentTotal" style="font-weight: bold;"></span></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
		</div>
	</fieldset>
	<fieldset>
		<span class="tablename">发票收据</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加发票</button>
		<div class="list">
			<div class="showPhotoDiv" style="overflow: auto;">
				<table id="productPhoto" style="width:100%;height:335px">
					<tbody><tr><td class="photo"></td></tr></tbody>
				</table>
			</div>
		</div>	
	</fieldset>
</form:form>

</div>
</div>
</body>

</html>
