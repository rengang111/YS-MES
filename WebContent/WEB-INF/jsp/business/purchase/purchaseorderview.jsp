<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>采购合同-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

function deductAjax() {
	
	var table = $('#example2').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}

	var contractId = $('#contract\\.contractid').val();
	var actionUrl = "${ctx}/business/contract?methodtype=getContractDeduct";
	actionUrl = actionUrl + "&contractId=" + contractId;
	
	
	var t = $('#example2').DataTable({
			"paging": false,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:false,
			"autoWidth"	:false,
			"searching" : false,
			"retrieve"  : true,
			"sAjaxSource" : actionUrl,
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
						
						var delay = currencyToFloat( data["delay"] );
						sumFn(delay);
						
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns": [
						{"data": null, "defaultContent" : '',"className" : 'td-center'},
						{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},
						{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},
						{"data": "materialName", "defaultContent" : ''},//3
						{"data": "contractQty", "defaultContent" : '', "className" : 'td-right'},
						{"data": "deductQty", "defaultContent" : '', "className" : 'td-right'},
						{"data": "price", "defaultContent" : '0', "className" : 'td-right'},
						{"data": null, "defaultContent" : '0', "className" : 'td-right'},
						
						],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
	    			return row["rownum"] ;				    			 
                }},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = row["materialName"];
	    			name = jQuery.fixedWidth(name,46);//true:两边截取,左边从汉字开始
	    			return name;
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":7,"render":function(data, type, row){
	    			var deduct = currencyToFloat( row["deductQty"] );
	    			var price = currencyToFloat( row["price"] );
	    			var rtn="";

	    			rtn = floatToCurrency(deduct * price);
	    			return rtn;
	    		}},
	    		{
					"visible" : false,
					"targets" : []
				}
         	]
		}
	);
	

	t.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});

}
	function ajaxRawGroup() {
		
		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,

			dom : '<"clear">rt',
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {"className":"td-center"					
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				],
			"columnDefs":[
    			{
					"visible" : false,
					"targets" : []
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
		
		t.on('contract.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	};//ajaxRawGroup()
	
	$(document).ready(function() {

		$("#costDeduct").attr('readonly',true);
		$("#costDeduct").addClass('read-only');
		$("#doSaveDeduct").hide();
		$("#doCancelDeduct").hide();
		
		$("#doEditDeduct").click(function() {
			$("#costDeduct").attr('readonly',false);
			$("#costDeduct").removeClass('read-only');
			$("#doSaveDeduct").show();
			$("#doCancelDeduct").show();
			$("#doEditDeduct").hide();
		});
		

		$("#doCancelDeduct").click(function() {
			$("#costDeduct").attr('readonly',true);
			$("#costDeduct").addClass('read-only');
			$("#doSaveDeduct").hide();
			$("#doCancelDeduct").hide();
			$("#doEditDeduct").show();
			$("#costDeduct").val($("#oldDeduct").val());
		});
		
		$("#doSaveDeduct").click(function() {
			var deduct  = $('#costDeduct').val();
			var contractId  = '${ contract.contractId }';
			var url = "${ctx}/business/contract?methodtype=updateContractDeduct";
			url = url + "&deduct="+deduct+"&contractId="+contractId;

			$.ajax({
				type : "post",
				url : url,
				//async : false,
				//data : null,
				dataType : "text",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {			

					$().toastmessage('showNoticeToast', "保存成功。");
					$("#costDeduct").val(floatToCurrency(deduct));
					$("#costDeduct").attr('readonly',true);
					$("#costDeduct").addClass('read-only');
					$("#doSaveDeduct").hide();
					$("#doEditDeduct").show();
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					//alert(textStatus)
				}
			});		

		});
		
		var YSId = '${ contract.YSId }';
		//var productid = '${ contract.productId }';
		if(YSId == null || YSId == ""){
			$('#ysid00').attr("style","display:none");
						
		}else{
			//if(productid == null || productid == ""){
				$('#ysidLink').contents().unwrap();			
			//}
		}
		
		
		//返回按钮
		var goBactkBt = '${openFlag}';
		if(goBactkBt == "newWindow"){
			$('#goBack').attr("style","display:none");			
		}
		
		ajaxRawGroup();			
		
		$( "#tabs" ).tabs();
		
		$('#example').DataTable().columns.adjust().draw();		
		
		$("#goBack").click(
				function() {	
					var keyBackup = '${contract.YSId}';
					var url = '${ctx}/business/contract?keyBackup='+keyBackup;
					location.href = url;		
				});
		
		$("#doEdit").click(
				function() {
					//var deleteFlag = $('#deleteFlag').val();
					//if(deleteFlag == '0'){
						//$().toastmessage('showWarningToast', "该合同已经有收货了,不能编辑。");
						//return false;
					//}		
			$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=edit");
			$('#attrForm').submit();
		});	
		
		$("#doDelete").click(
				function() {
					var deleteFlag = $('#deleteFlag').val();
			if(deleteFlag == '0'){
				$().toastmessage('showWarningToast', "该合同已经有收货了,不能删除。");
				return false;
			}		
			if(confirm("采购合同删除后不能恢复,\n\n        确定要删除吗？")) {
				$('#attrForm').attr("action", "${ctx}/business/contract?methodtype=delete");
				$('#attrForm').submit();
			}else{
				return false;
			}
			
		});
		
		deductAjax();
		
		contractSum();//合同计算
		
		
		
	});	
	
	function doShowYS(YSId) {

		var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;
		
		location.href = url;
	}
	

	function doEditMaterial(recordid,parentid) {
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
			  //$('#baseBomTable').DataTable().ajax.reload(null,false);
			  return false; 
			}    
		});		

	};
	
	function contractSum(){
		
		var contract = 0;
		$('#example tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(5).text();////合同
			
			contractValue= currencyToFloat(contractValue);
			//alert("计划用量+已领量+库存:"+fjihua+"---"+fyiling+"---"+fkucun)
			
			contract = contract + contractValue;
						
		});	
		
		$('#contractCount').html(floatToCurrency(contract));
	}
	
	function sumFn(delay){
	
		var deduct = 0;
		$('#example2 tbody tr').each (function (){
			
			var contractValue = $(this).find("td").eq(7).text();//扣款金额
			
			contractValue= currencyToFloat(contractValue);
			//alert("计划用量+已领量+库存:"+fjihua+"---"+fyiling+"---"+fkucun)
			
			deduct = deduct + contractValue;
						
		});	
		
		var contract = currencyToFloat($('#contractCount').text());

		var taxExcluded,taxes,payment;
		var taxRate = '${ contract.taxRate }';
		taxRate = currencyToFloat(taxRate)/100;
		var deductCnt = deduct + delay
		
		payment = contract - deductCnt;//应付款
		taxExcluded = payment * (1 - taxRate);
		taxes = payment - taxExcluded;

		$('#deductCount').html(floatToCurrency(deduct));
		$('#deductCount1').html(floatToCurrency(deduct));
		$('#deductCount2').html(floatToCurrency(delay));
		$('#payment').html(floatToCurrency(payment));
		$('#taxExcluded').html(floatToCurrency(taxExcluded));
		$('#taxes').html(floatToCurrency(taxes));
		$('#costDeduct').val(floatToCurrency(deductCnt));
		$('#oldDeduct').val(floatToCurrency(deductCnt));
	
	}
	

	function showContract() {
		var contractId = '${ contract.contractId }';
		var url = '${ctx}/business/requirement?methodtype=contractPrint';
		url = url +'&contractId='+contractId;
		//alert(url)
		
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
			  //  layer.close(index)
			 // }
			  //baseBomView();
			 // return false; 
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
			
		<form:hidden path="contract.recordid" value="${contract.contractRecordId }"/>
		<input type="hidden" id="deleteFlag" value="${deleteFlag }" />
		
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
			
				<tr id="ysid00"> 		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td colspan="9">
						<a href="#" id="ysidLink" onClick="doShowYS('${contract.YSId}')">${contract.YSId }</a>
						<form:hidden path="contract.ysid" value="${contract.YSId }"/></td>
				</tr>
				<tr> 		
					<td class="label"><label>供应商编号：</label></td>					
					<td width="150px">${ contract.supplierId }
						<form:hidden path="contract.supplierid" value="${contract.supplierId }"/></td>
									
					<td class="label" width="100px"><label>供应商简称：</label></td>					
					<td width="100px">${ contract.shortName }</td>
						
					<td class="label" width="100px"><label>供应商名称：</label></td>
					<td colspan="5">${ contract.fullName }</td>
				</tr>	
				<tr> 		
					<td class="label"><label>合同编号：</label></td>					
					<td>${ contract.contractId }
						<form:hidden path="contract.contractid" value="${contract.contractId }"/></td>
					<td class="label" width="100px">付款条件：</td>
					<td>入库后&nbsp;${ contract.paymentTerm }&nbsp;天</td>
					<td class="label"><label>下单日期：</label></td>
					<td width="100px">${ contract.purchaseDate }</td>
					<td class="label" width="100px"><label>合同交期：</label></td>
					<td width="100px">${ contract.deliveryDate }</td>
					<td class="label" width="100px"></td>
					<td></td>
				</tr>									
			</table>
			
	</fieldset>
	
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="submit" id="doEdit" class="DTTT_button">编辑</button>
		<button type="submit" id="doDelete" class="DTTT_button">删除</button>
		<button type="button" id="doPrint" class="DTTT_button" onclick="showContract();">打印</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>
	<fieldset style="margin-top: -30px;">
	<legend> 合同付款</legend>
		<table class="form" style="font-weight: bold;font-size: 13px;">	
			<tr> 		
				<td width="100px" class="label"><label>合同总金额：</label></td>					
				<td width="100px">${ contract.total }</td>
				<td width="100px" class="label"><label style="color: red;">报废扣款：</label></td>					
				<td width="100px"><span id="deductCount1" style="color: red;"></span></td>
				<td width="100px" class="label"><label style="color: red;">延迟扣款：</label></td>					
				<td width="100px"><span id="deductCount2" style="color: red;"></span></td>
				<td width="100px" class="label"><label>最终协商扣款：</label></td>					
				<td width="">
					<input type="text" id="costDeduct" class="num mini" value="" />
					<input type="hidden" id="oldDeduct"  />
					<button type="button" id="doEditDeduct" class="DTTT_button" >修改扣款</button>
					<button type="button" id="doSaveDeduct" class="DTTT_button" >保存</button>
					<button type="button" id="doCancelDeduct" class="DTTT_button" >取消修改</button>
				</td>
			</tr>
			<tr>
				<td width="" class="label"><label>应付款：</label></td>					
				<td width=""><span id="payment"></span></td>
				<td width="" class="label"><label>退税率：</label></td>
				<td width="">${ contract.taxRate }</td>
				<td width="" class="label">退税额：</td>
				<td width=""><span id="taxes"></span></td>
				<td width="" class="label"><label>税前价：</label></td>
				<td><span id="taxExcluded"></span></td>
			</tr>	
		</table>
	</fieldset>
	<fieldset style="margin-top: -10px;">
		<legend> 合同详情</legend>
		<div class="list">
			<table id="example" class="display" >	
				<thead>
				<tr>
					<th style="width:10px">No</th>
					<th style="width:130px">物料编码</th>
					<th>物料名称</th>
					<th style="width:60px">合同数</th>
					<th style="width:60px">单价</th>
					<th style="width:60px">合同金额</th>
				</tr>
				</thead>		
				<tbody>
					<c:forEach var="detail" items="${detail}" varStatus='status' >	
						<tr id="detailTr${status.index }">
							<td></td>
							<td><a href="###" id="meteLink${status.index }" onClick="doEditMaterial('${detail.materialRecordId}','${detail.materialParentId}')">${detail.materialId}</a>
								<form:hidden path="detailList[${status.index}].materialid" value="${detail.materialId}" /></td>								
							<td><span id="name${status.index}"></span>${ detail.description }</td>					
						
							<td>${ detail.quantity}   </td>								
							<td><span id="price${status.index }">${ detail.price }</span></td>
							<td><span id="total${status.index }">${ detail.totalPrice }</span></td>
								
								<form:hidden path="detailList[${status.index}].recordid" value="${detail.recordId}" />	
								<form:hidden path="detailList[${status.index}].quantity" value="${detail.quantity}" />	
						</tr>	
										
						<script type="text/javascript">
							//var materialName = '${detail.materialName}';
							var index = '${status.index}';
							var contractQty = currencyToFloat('${detail.quantity}');
							var chargeback = currencyToFloat('${detail.chargeback}');
							var price = currencyToFloat('${detail.price}');
							//alert("合同数量+退货数量+单价"+contractQty+"---"+chargeback+"---"+price)
							var contractValue = contractQty * price;
							
							var pay = floatToCurrency( contractValue + chargeback );
							
							//$('#name'+index).html(jQuery.fixedWidth(materialName,64));
							
							//$('#returnValue'+index).html(floatToCurrency( chargeback ));
							//$('#pay'+index).html(pay);
							$('#price'+index).html(formatNumber(price));
							$('#total'+index).html(floatToCurrency(contractValue));
		
							var deleteFlag = '${detail.deleteFlag}';
							if( deleteFlag == 1 ) {
								$('#detailTr'+index).addClass('delete');
								$('#meteLink'+index).contents().unwrap();	
								$('#total'+index).html('0');
								//$('#pay'+index).html('0');
			 				}				
						</script>	
							
					</c:forEach>
					
				</tbody>
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td >合计：</td>
						<td ><div id="contractCount"></div></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</fieldset>
	<fieldset style="margin-top: -10px;">
		<legend> 合同扣款明细</legend>
		<div class="list">
			<table id="example2" class="display" >	
				<thead>
				<tr>
					<th style="width:10px">No</th>
					<th style="width:80px">退还耀升编号</th>
					<th style="width:130px">物料编码</th>
					<th>物料名称</th>
					<th style="width:50px">合同数量</th>
					<th style="width:50px">退还数量</th>
					<th style="width:50px">单价</th>
					<th style="width:60px">扣款金额</th>
				</tr>
				</thead>		
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td ></td>
						<td ></td>
						<td >扣款合计：</td>
						<td ><div id="deductCount"></div></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</fieldset>
	<fieldset>
	<legend> 合同注意事项</legend>
	<table class="form" >
		<tr>
			<td class="td-left"><pre>${contract.memo}</pre></td>
		</tr>
	</table>
	
	</fieldset>
		
</form:form>

</div>
</div>
</body>

	
</html>
