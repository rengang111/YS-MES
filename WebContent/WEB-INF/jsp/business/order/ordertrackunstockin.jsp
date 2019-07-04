<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>订单跟踪--物料未入库信息</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

function unStockinListAjax() {
	var table = $('#unStockinList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var materialId = "${contract.materialId }";
	var t = $('#unStockinList').DataTable({
		//"paging": true,
		//"lengthMenu":[50],//设置一页展示10条记录
		"processing" : false,
		"serverSide" : true,
		"stateSave" : false,
		"searching" : false,
		"pagingType": "full_numbers",
		"retrieve" 	: true,
       	"dom"		: '<"clear">rt',			
		"language": {"url":"${ctx}/plugins/datatables/chinese.json" 	},
		"sAjaxSource" : "${ctx}/business/orderTrack?methodtype=getUnStockinContractList"+"&materialId="+materialId,
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var param = {};
			var formData = $("#supplierBasicInfo").serializeArray();
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
					
					contractSum();
										
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},		        	
		"columns" : [ 
			{"data": null, "defaultContent" : '', "className" : 'td-center'}, //0
			{"data" : "materialId", "className" : 'td-left'}, 
			{"data" : "contractId", "className" : 'td-left'},// 2
			{"data" : "YSId", "className" : 'td-left'}, // 3
			{"data" : "materialName", "className" : ''}, // 4
			{"data" : "shortName", "className" : 'td-left'}, // 5
			{"data" : "deliveryDate", "className" : 'td-left'}, // 6
			{"data" : "newDeliveryDate", "className" : 'td-left'}, // 7
			{"data" : "quantity", "className" : 'td-right'}, // 7
			{"data" : "contractStorage", "className" : 'td-right'},// 8
			{"data" : null, "className" : 'td-right'}// 9
			
		],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
				return row["rownum"];
            }},
    		{
				"visible" : false,
				"targets" : [2,7]
			},
			{"targets":4,"render":function(data, type, row){
				return jQuery.fixedWidth(data,48);
            }},
			{"targets":7,"render":function(data, type, row){
				var index = row["rownum"] - 1;
				var	text = '';
				var groupFlag = $('#groupFlag').val();	
				var deliveryDate    = row["deliveryDate"];
				var newDeliveryDate = row["newDeliveryDate"];
				var materialId   = row["materialId"];
				var contractId   = row["contractId"];
				if(newDeliveryDate == '' || newDeliveryDate == null)
					newDeliveryDate = deliveryDate;
				
				var hidden = "";
				hidden += '<input type="hidden" id="materialId'+index+'" value='+materialId+' >';
				hidden += '<input type="hidden" id="contractId'+index+'" value='+contractId+' >';
				
				var spanDate = '<span id=span'+index+'>'+newDeliveryDate+'</span>';
				var inputDate= '<input type="text" id="deliveryInput'+index+'" value='+newDeliveryDate+' class="deliverDate"  style="width: 80px;display:none;" >';
				if(groupFlag == 'Z'){					
					text  =	'<input type="image" id="edit'+index+'"   name="edit'+index+'"   src="${ctx}/images/action_edit.png"   onclick="editbtn('+index+');return false;"   title="编辑" style="border: 0;width: 10px;" >';
					text += '<input type="image" id="save'+index+'"   name="save'+index+'"   src="${ctx}/images/action_save.png"   onclick="savebtn('+index+');return false;"   title="保存" style="border: 0;width: 10px;display:none;" >';
					text += '<input type="image" id="cancel'+index+'" name="cancel'+index+'" src="${ctx}/images/action_cancel.png" onclick="cancelbtn('+index+');return false;" title="取消" style="border: 0;width: 10px;display:none;" >';
					
					return spanDate + inputDate + text+hidden;
					
				}else{
					return '***' + hidden;
				}
					
				
            }},
			{"targets":8,"render":function(data, type, row){
				return floatToCurrency(data);
            }},
			{"targets":9,"render":function(data, type, row){
				return floatToCurrency(data);
            }},
			{"targets":10,"render":function(data, type, row){
				var quantity = currencyToFloat(row['quantity']);
				var storage  = currencyToFloat(row['contractStorage']);
				var tmp = quantity - storage ;
				
				return floatToCurrency(tmp);
            }}
	    ] 						
	});


};//未到货清单

	
	$(document).ready(function() {

		unStockinListAjax();
		
	});
		
		
	//列合计
	function contractSum(){

		var index = 0;	
		var materialId = '';
		var materialId_next = '';
	    var firstFlag = true;
		var strageCnt = 0;
		
	    var table = $('#unStockinList').dataTable();
	    
		$('#unStockinList tbody tr').each (function (){
					
			var vtotal = $(this).find("td").eq(8).text();
			var ftotal = currencyToFloat(vtotal);

			strageCnt = strageCnt + ftotal;	
			
			
		})	
		
		$('#cnt').text(floatToCurrency(strageCnt));
		
		
	}//列合计
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="reviewForm" method="POST"
	id="reviewForm" name="reviewForm"  autocomplete="off">
	
	<input type="hidden" id="ysid" value="${contract.YSId }" />
	<input type="hidden" id="supplierid"  value="${contract.supplierId}"/>
	<input type="hidden" id="contractid"  value="${contract.contractId}"/>
		
	<fieldset>
		<table class="form" id="table_form">
			<tr class="viewFlag">			
				<td class="label" width="100px">物料编号：</td>					
				<td width="150px">${contract.materialId }</td>

				<td class="label">物料名称：</td>
				<td>${contract.materialName }</td>					
			</tr>										
		</table>
	</fieldset>
	<div style="clear: both"></div>
	
<fieldset style="">
	<legend> 合同记录</legend>
	<div class="list">
	<table class="display" id="unStockinList">	
		<thead>		
			<tr>
				<th style="width: 10px;">No</th>
				<th style="width: 100px;">物料编码</th>
				<th style="width: 30px;">合同编号</th>
				<th style="width: 50px;">耀升编号</th>
				<th>物料名称</th>
				<th style="width: 40px;">客户</th>
				<th style="width: 70px;">合同交期</th>
				<th style="width: 110px;">最新交期</th>
				<th style="width: 50px;">数量</th>
				<th style="width: 40px;">到货数</th>
				<th style="width: 50px;">未到合计</th>
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
				<td></td>
				<td></td>
				<td></td>
				<td id=cnt></td>
			</tr>	
		</tfoot>	
									
	</table>
	</div>
</fieldset>

</form:form>

</div>
</div>
</body>


</html>
