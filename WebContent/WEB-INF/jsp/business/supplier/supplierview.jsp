<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>供应商基本数据</title>
<script type="text/javascript">

var layerHeight = "300";

function ajax() {
	var table = $('#TContactList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TContactList').DataTable({
		//"paging": true,
		//"lengthMenu":[50],//设置一页展示10条记录
		"processing" : false,
		"serverSide" : true,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : true,
		"sAjaxSource" : "${ctx}/business/contact?methodtype=contactsearch",
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
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
			
		"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
       	
       	dom : '<"clear">rt',
       	
		"columns" : [ 
			{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
			{"data" : "userName", "className" : 'td-left'}, 
			{"data" : "sex", "className" : 'td-center'},
			{"data" : "position", "className" : 'td-center'}, 
			{"data" : "mobile", "className" : 'td-left'}, 
			{"data" : "phone", "className" : 'td-left'}, 
			{"data" : "fax", "className" : 'td-left'}, 
			{"data" : "mail", "className" : 'td-left'}, 
			{"data" : "qq", "className" : 'td-left'}
		],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
				return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
                  }},
    		{"targets":1,"render":function(data, type, row){
    			return "<a href=\"#\" onClick=\"doUpdateContact('" + row["id"] + "')\">"+data+"</a>"
                  }}
	    ] 						
	});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});
	
	
	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

		//alert(999);

		var tr = $(this).closest('tr');
		t
		var row = t.row(tr);
		t

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(format(row.data())).show();
			tr.addClass('shown');
		}
	});

};//ajax,联系人

function unStockinListAjax(active) {
	var table = $('#unStockinList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var supplierId = "${formModel.supplier.supplierid}";
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
		"sAjaxSource" : "${ctx}/business/contract?methodtype="+active+"&supplierId="+supplierId,
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
					
					//contractSum();
					
					inputDateInit();

					//input格式化
					foucsInit();
					
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
				"targets" : [2]
			},
			{"targets":4,"render":function(data, type, row){
				return jQuery.fixedWidth(data,24);
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

		
function initEvent(){

	ajax();
	
	$('#TContactList').DataTable().on('click', 'tr', function() {
		
		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
        	$('#TContactList').DataTable().$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});
	
}

function reloadContact() {

	$('#TContactList').DataTable().ajax.reload(null,false);
	
	return true;
}

$(document).ready(function() {
	//返回按钮
	
	initEvent();
	// getUnStockinContractList  //unStockinListForRetract
	unStockinListAjax('unStockinListForRetract');//

	buttonSelectedEvent();//按钮选择式样
	
	$('#defutBtn').removeClass("start").addClass("end");
	
	var goBactkBt = '${openFlag}';
	if(goBactkBt == "newWindow"){
		$('#goBack').attr("style","display:none");			
	}

	$("#country").val("${DisplayData.supplierBasicInfoData.country}");
	$("#province").val("${DisplayData.supplierBasicInfoData.province}");
	$("#city").val("${DisplayData.supplierBasicInfoData.city}");
	
	
})

function doEdit() {
	var recodId = '${formModel.supplier.recordid}';
	var url = "${ctx}/business/supplier?methodtype=edit&key="+recodId;
	location.href = url;
}



function doBack() {
	var supplierId = "${formModel.supplier.supplierid}";
	var url = "${ctx}/business/supplier?keyBackup="+supplierId;
	location.href = url;
}

function doAddContact() {
	var supplierId = $("#supplier\\.supplierid").val();
	var url = "${ctx}/business/contact?methodtype=addinit&supplierId="+supplierId;
	openLayer(url, $(document).width() - 300, layerHeight, false);	
	
}

function doUpdateContact(key) {		
	var url = "${ctx}/business/contact?methodtype=updateinit&key=" + key;
	openLayer(url,$(document).width() - 300, layerHeight, false);
}

function doDeleteContact() {


	if (str != "") {
		if (confirm("您确认执行该操作吗？") == false) {
			return;
		}
		$.ajax({
			contentType : 'application/json',
			dataType : 'json',						
			type : "POST",
			data : str,// 要提交的表单						
			url : "${ctx}/business/contact?methodtype=delete",
			success : function(d) {													
				reloadContact();
				//alert(data.message);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {			
				//发生系统异常，请再试或者联系管理员。
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});
		
	} else {
		alert("请先选中要删除的记录。");
	}

}

//列合计
function contractSum(){

	var index = 0;	
	var materialId = '';
	var materialId_next = '';
    var firstFlag = true;
	var strageCnt = 0;
	
    var table = $('#unStockinList').dataTable();
    
	$('#unStockinList tbody tr').each (function (){

		var $td = $('#unStockinList tbody tr:eq('+index+') td');
				
		var vtotal = $(this).find("td").eq(8).text();
		var ftotal = currencyToFloat(vtotal);
	
		
		if(firstFlag){
			
			materialId      = $td.eq(1).text().trim();			
			storageQty      = $td.eq(8).text().trim();			
			storageQty      = currencyToFloat(storageQty);			
			firstFlag = false;
		}else{
			materialId_next = $td.eq(1).text().trim();
			storageQty_next = $td.eq(8).text().trim();			
			storageQty_next = currencyToFloat(storageQty_next);

			if(materialId == materialId_next){
				
				strageCnt = strageCnt + storageQty + storageQty_next;
				
				storageQty = 0;//上一行清空
				var privt = index - 1;//上一行
				
		        $('#unStockinList tbody tr:eq('+privt+') td').eq(8).text(storageQty);
				$('#unStockinList tbody tr:eq('+index+') td').eq(8).text(strageCnt);			
				
			}else{

				strageCnt = 0;//相同物料的合计值清空
				storageQty = storageQty_next;//保存当前条作为下次的基数
								
			}			
			materialId = materialId_next;			
		}
		index++;	
		
	})	
}//列合计


function developFn(){

	$('#groupFlag').val('Z');//展开
	var active = 'getUnStockinContractList';

	unStockinListAjax(active);//
}
function retractFn(){
	
	$('#groupFlag').val('S');//收起
	var active = 'unStockinListForRetract';

	unStockinListAjax(active);//
}

function setStockoutQty(index,quantity){
	var txt = $("#correctionList"+index+"\\.quantity").val();
	if( txt == '' || txt == '0'){
		$("#correctionList"+index+"\\.quantity").val(quantity);
		$("#btn_edit"+index).html('取消');
	}else{
		$("#correctionList"+index+"\\.quantity").val('0');
		$("#btn_edit"+index).html('修正');
	}
}

function editbtn(index){
	//alert('index'+index)
	$("#save"+index).show();
	$("#cancel"+index).show();
	$("#edit"+index).hide();

	$("#span"+index).hide();
	$("#deliveryInput"+index).show();
	
	return false;
}


function cancelbtn(index){
	$("#save"+index).hide();
	$("#cancel"+index).hide();
	$("#edit"+index).show();

	$("#span"+index).show();
	$("#deliveryInput"+index).hide();
	
	return false;
}

function savebtn(index){
	$("#save"+index).hide();
	$("#cancel"+index).hide();
	$("#edit"+index).show();
	
	var deliveryDate = $("#deliveryInput"+index).val();
	var materialId   = $("#materialId"+index).val();
	var contractId   = $("#contractId"+index).val();

	var par = "&deliveryDate=" + deliveryDate+
				"&materialId=" + materialId+
				"&contractId=" + contractId;
	
	
	if (deliveryDate != "") {
		
		$.ajax({
			contentType : 'application/json',
			dataType : 'json',						
			type : "POST",
			data : deliveryDate,// 要提交的表单						
			url : "${ctx}/business/supplier?methodtype=updateContractDetail"+par,
			success : function(d) {	
				
				$('#unStockinList').DataTable().ajax.reload(null,false);
				$().toastmessage('showWarningToast', "合同交期更新成功。");
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
				//发生系统异常，请再试或者联系管理员。
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});
		
	} else {
		$().toastmessage('showWarningToast', "请输入有效日期");
		return false;
	}
}

function inputDateInit(){
	
	$(".deliverDate").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
	}); 
}

</script>

</head>

<body>
<div id="container">

	<form:form modelAttribute="formModel" id="supplierBasicInfo" >	
		
		<form:hidden path="keyBackup" value="${formModel.supplier.supplierid}" />		
		<form:hidden path="supplier.recordid" />
		<input type="hidden" id="groupFlag" value="S">
		<fieldset>		
			<legend>供应商-综合信息</legend>
				
			<table class="form" >
				<tr>
					<td class="label" width="100px">供应商编码：</td>
					<td width="150px">${formModel.supplier.supplierid}</td>
					<td class="label" width="100px">简称：</td> 
					<td width="250px">${formModel.supplier.shortname}</td>
	
					<td class="label" width="100px">名称：</td> 
					<td>${formModel.supplier.suppliername}</td>
				</tr>
				<tr>	
					<td class="label" width="100px">物料分类：</td> 
					<td>${formModel.supplier.categoryid}</td>
					<td class="label" width="100px">分类解释：</td> 
					<td colspan=3>${formModel.supplier.categorydes}</td>				
				</tr>
				<tr>
					<td class="label">详细地址： </td>
					<td colspan=3>${formModel.supplier.address}</td>
	
					<td class="label" width="100px">付款条件：</td>
					<td>发票后&nbsp;${formModel.supplier.paymentterm}&nbsp;天</td>
				</tr>
				<tr>
					<td class="label">正常交期： </td>
					<td colspan=3>&nbsp;${formModel.supplier.normaldelivery}&nbsp;天</td>
	
					<td class="label" width="100px">最长交期：</td>
					<td>&nbsp;${formModel.supplier.maxdelivery}&nbsp;天</td>
				</tr>
			</table>

		</fieldset>	
		<fieldset class="action" style="text-align: right;">
		<button type="button" class="DTTT_button" onclick="doEdit();">编辑</button>
		<button type="button" class="DTTT_button" onclick="doBack();" id="goBack">返回</button>
		</fieldset>
							
		<fieldset style="margin-top: -30px;">		
			<legend> 联系人</legend>
			<div class="list">
				<div id="DTTT_container" style="height:40px;align:right">
					<button type="button" id="deletecontact" class="DTTT_button " onClick="doDeleteContact();"
						style="height:25px;" >删除</button>
					<button type="button" id="addcontact" class="DTTT_button " onClick="doAddContact();"
						style="height:25px;" >新建</button>
				</div>
				<table id="TContactList" class="display" style="width:100%">
					<thead>
						<tr class="selected">
							<th style="width: 10px;">No</th>
							<th style="width: 100px;">姓名</th>
							<th style="width: 30px;">性别</th>
							<th style="width: 50px;">职务</th>
							<th style="width: 100px;">手机</th>
							<th style="width: 100px;">电话</th>
							<th style="width: 100px;">传真</th>
							<th>邮箱</th>
							<th style="width: 50px;">QQ</th>
					</thead>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</tfoot>
				</table>
			</div>
		</fieldset>					
		<fieldset>		
			<legend> 未交货清单</legend>
			<div class="list">
				<div id="DTTT_container" align="right" style="height:40px">
					<a id="defutBtn" 
						class="DTTT_button  box" onclick="retractFn();"><span>收起</span></a>
					<a id="" 
						class="DTTT_button  box" onclick="developFn();"><span>展开</span></a>
				</div>
				<table id="unStockinList" class="display"  style="width:100%">
					<thead>
						<tr class="selected">
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
					</thead>
				</table>
			</div>
		</fieldset>
	</form:form>
</div>
</body>
</html>
