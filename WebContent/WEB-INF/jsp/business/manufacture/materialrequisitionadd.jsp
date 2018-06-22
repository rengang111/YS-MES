<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>直接出库-出库确认</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

function materialAjax(sessionFlag) {
	var table = $('#TMaterial').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var url = "${ctx}/business/material?methodtype=search&sessionFlag="+sessionFlag;
	var scrollHeight = "185px";//$(document).height() - 400; 
	var t = $('#TMaterial').DataTable({
			"paging": true,
			 "iDisplayLength" : 50,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"pagingType" : "full_numbers",
			"scrollY":scrollHeight,
			"scrollCollapse":false,
			"retrieve" : true,
			"sAjaxSource" : url,
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
						$("#keyword1").val(data["keyword1"]);
						$("#keyword2").val(data["keyword2"]);						
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
						{"data": "dicName","className" : 'td-center'},
						{"data": "quantityOnHand","className" : 'td-right'},
					],
			"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"];
	                    }},
			    		{"targets":2,"render":function(data, type, row){
			    			return jQuery.fixedWidth(data,46);		
			    		}}
			    		
		           
		         ] 
		}
	);

};
	
	$(document).ready(function() {
		
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
					var url = "${ctx}/business/requisition?methodtype=materialRequisitionMain";
					location.href = url;
		});

		
		$("#insert").click(
				function() {

			var quantity = $('#reqDetail\\.quantity').val();
			var materialid = $('#reqDetail\\.materialid').val();
			var ysid = $('#requisition\\.ysid').val();
			quantity = myTrim(quantity);
			quantity = currencyToFloat(quantity);
			/*
			if(ysid == '' || ysid == null){
				if(confirm("未输入耀升编号，要关联吗？")){
					return;
				}
			}
			*/
			//if(ysid == '' || ysid == null){
			//	$().toastmessage('showWarningToast', "请输入耀升编号。");
			//	return;
			//}
			if(materialid == '' || materialid == null){
				$().toastmessage('showWarningToast', "请选择物料。");
				return;
			}
			if(quantity<=0){
				$().toastmessage('showWarningToast', "申请数量必须大于零,请重试。");
				return;
			}
			$('#formModel').attr("action", "${ctx}/business/requisition?methodtype=materialRequisitionInsert");
			$('#formModel').submit();
		});	
				
		foucsInit();

		$('#TMaterial').DataTable().on('click', 'tr', function() {
			

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            
	            var d = $('#TMaterial').DataTable().row(this).data();				
				//alert(d["materialId"])
				$('#reqDetail\\.materialid').val(d["materialId"]);
				$('#materialName').text(d["materialName"]);
				$('#quantity').text(d["quantityOnHand"]);
				$('#unit').text(d["dicName"]);
					            
	        }
			
		});
	
		//materialAjax();
		
		//编辑模式
		var editFlag = '${editFlag}';
		if(editFlag == 'edit'){
			$('#requisition\\.requisitionid').val('${requisition.requisitionId}');
			$('#requisition\\.usedtype').val('${requisition.usedTypeId}');
			$('#requisition\\.remarks').val(replaceTextarea('${requisition.remarks}'));
			$('#materialName').text('${requisition.materialName}');
		}
		

		autocomplete();
		
	});


	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		materialAjax("false");

	}
	
	
	
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">
	<div id="search" style="width: 98%;">

		<form id="condition"  style='padding: 0px; margin: 10px;' >

			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label">关键字1：</td>
					<td class="condition">
						<input type="text" id="keyword1" name="keyword1" class="middle"/>
					</td>
					<td class="label">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle"/>
					</td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
			</table>
		</form>			
		<table style="width: 100%;" id="TMaterial" class="display">
			<thead>						
				<tr class="selected">
					<th style="width: 30px;" aria-label="No:">No</th>
					<th style="width: 200px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 40px;">单位</th>
					<th style="width: 100px;">库存</th>
				</tr>
			</thead>
		</table>
	</div>
					
<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
	
	<form:hidden path="requisition.recordid" value="${requisition.recordId }" />
	<form:hidden path="reqDetail.recordid"   value="${requisition.detailRecordId }" />
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;width: 50%;float: right;margin-bottom: -35px;margin-top: 5px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认申请</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<fieldset>
		<legend> 出库申请单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">申请单编号：</td>					
				<td width="150px">
					<form:input path="requisition.requisitionid" class="required read-only " value="保存后自动生成" /></td>
														
				<td width="100px" class="label">申请日期：</td>
				<td width="150px">
					<form:input path="requisition.requisitiondate" class="short read-only" /></td>
				
				<td width="100px" class="label">申请人：</td>
				<td colspan="3">
					<form:input path="requisition.requisitionuserid" class="short read-only" value="${userName }" /></td>
			</tr>

			<tr> 				
				<td class="label" width="100px">物料编号：</td>					
				<td><form:input path="reqDetail.materialid" class="read-only" value="${requisition.materialId }" /></td>
														
				<td width="100px" class="label">物料名称：</td>
				<td colspan="5">&nbsp;<span id="materialName"></span></td>
				
			</tr>
			<tr> 				
				<td class="label">申请数量：</td>
				<td><form:input path="reqDetail.quantity" class="num " value="${requisition.quantity }" /></td>
				<td class="label">现有库存：</td>					
				<td>&nbsp;<span class="font16"><span id="quantity"></span></span>（<span id="unit"></span>）</td>
								
				<td class="label">关联耀升编号：</td>
				<td style="width: 150px;"><form:input path="requisition.ysid" class="short" value="${requisition.YSId }" /></td>
				
				<td class="label" width="100px">领料用途：</td>
				<td>
					<form:select path="requisition.usedtype" style="width: 100px;">
							<form:options items="${usedType}" 
							  itemValue="key" itemLabel="value" />
					</form:select></td>
			</tr>
			<tr> 				
				<td class="label">申请事由：</td>					
				<td colspan="7">
					<form:textarea path="requisition.remarks" rows="3" cols="80" /></td>
				
			</tr>										
		</table>
</fieldset>
</form:form>
<script>
function autocomplete(){
	
	$("#requisition\\.ysid").autocomplete({
		minLength : 2,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/order?methodtype=getYsidList",
				dataType : "json",
				data : {
					key : request.term
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.YSId +" | "+ item.materialId +" | "+ item.materialName,
									value : item.YSId,
								}
							}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					//alert(XMLHttpRequest.status);
					//alert(XMLHttpRequest.readyState);
					//alert(textStatus);
					//alert(errorThrown);
					alert("系统异常，请再试或和系统管理员联系。");
				}
			});
		},
	
	});
}
</script>
</div>
</div>
</body>

</html>
