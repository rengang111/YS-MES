<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>车间退货-查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	function historyAjax() {
		var YSId = '${order.YSId }';
		var t = $('#example').DataTable({
			
			"paging": true,
			"lengthChange":false,
			"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"serverSide" : false,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"retrieve" : true,
			dom : '<"clear">rt',
			"sAjaxSource" : "${ctx}/business/workshopReturn?methodtype=getWorkshopReturnHistory&YSId="+YSId,
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
			        	{"data": null,"className":"dt-body-center"
					}, {"data": "returnDate","className":"dt-body-center"
					}, {"data": "taskId","className":"td-left"
					}, {"data": "materialId","className":"td-left"
					}, {"data": "materialName","className":"td-left"
					}, {"data": "unit","className":"td-center"
					}, {"data": "quantity","className":"td-right","defaultContent" : '0'
					}, {"data": null,"className":"td-center","defaultContent" : ''
					}
				] ,
				"columnDefs":[
		    		{"targets":7,"render":function(data, type, row){
		    			var returnDate = row["returnDate"];
		    			var today = shortToday();
		    			//var sysDate = new Date();//获取系统时间 
		    		  // var newDate = new Date(returnDate);//把录入时间转换成日期格式；  
		    		    var rtn= "";
		    		   // alert("newDate:"+newDate+"-----sysDate:"+sysDate)
		    		    if(today == returnDate){  
		    		         rtn = "<a href=\"###\" onClick=\"doEdit('" + row["YSId"] + "','" + row["workshopReturnId"] + "')\">编辑</a>";
		    		    }		    			
		    			return rtn;
		    		}},
		    	]        
			
		}).draw();
						
		
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
		
		$("#workshopReturn\\.returndate").val(shortToday());
		
		
		$("#workshopReturn\\.returndate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		}); 
		
			
		
		$("#addInit").click(function() {
			var status = $('#orderStatus').val();
			if(status == '040'){
				alert('该订单已入库，关闭车间退货录入。');
				return;
			}
			var YSId = '${ order.YSId }';
			var url =  "${ctx}/business/workshopReturn?methodtype=createWorkshopRentunInit&YSId="+YSId;
			location.href = url;	
		});

		$("#goBack").click(function() {
			var YSId = '${ order.YSId }';
			var url = '${ctx}/business/workshopReturn?methodtype=workshopRentunInit&keyBackup=' + YSId;
			location.href = url;	
		});
	});	
	
	function doEdit(YSId,workshopReturnId) {
		
		var url =  "${ctx}/business/workshopReturn?methodtype=workshopRentunEdit"
				+"&workshopReturnId="+workshopReturnId
				+"&YSId="+YSId;
		location.href = url;	
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
			
		<form:hidden path="workshopReturn.recordid" value="${order.recordId }"/>
		<form:hidden path="workshopReturn.ysid" value="${order.YSId }"/>
		<input type="hidden" id ="orderStatus" value = "${orderStatus }" />
		
		<fieldset>
			<legend> 采购合同</legend>
			<table class="form" id="table_form">
				<tr id="ysid00">		
					<td class="label" width="100px"><label>耀升编号：</label></td>					
					<td width="150px">${order.YSId }</td>
									
					<td class="label" width="100px"><label>产品编号：</label></td>					
					<td width="150px">&nbsp;${ order.materialId }</td>
						
					<td class="label" width="100px"><label>产品名称：</label></td>
					<td>${ order.materialName } </td>
				</tr>								
			</table>
			
	</fieldset>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="addInit" class="DTTT_button">继续退货</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</fieldset>			
	<fieldset style="margin-top: -30px;">
	<legend> 退货记录</legend>	
		<div class="list">
		<table id="example" class="display" style="width:100%">	
			<thead>
				<tr>
					<th style="width:30px">No</th>
					<th style="width:100px">退货日期</th>
					<th style="width:100px">任务编号</th>
					<th style="width:150px">物料编码</th>
					<th>物料名称</th>
					<th style="width:40px">单位</th>
					<th style="width:100px">退货数量</th>
					<th style="width:40px">操作</th>
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
