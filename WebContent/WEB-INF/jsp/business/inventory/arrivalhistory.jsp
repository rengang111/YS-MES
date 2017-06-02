<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
<title>库存管理-到货登记</title>
<script type="text/javascript">

	function historyAjax() {
		var contractId = '${contract.contractId }';
		var t = $('#example2').DataTable({
			
			"processing" : false,
			"serverSide" : true,
			"stateSave" : false,
			"searching" : true,
			"pagingType" : "full_numbers",
			"retrieve":true,
			"cache" : false,
			dom : '<"clear">rt',
			//"sAjaxSource" : "${ctx}/business/arrival?methodtype=getArrivalHistory&contractId="+contractId,
			//"fnServerData" : function(sSource, aoData, fnCallback) {
				//var param = {};
				//var formData = $("#condition").serializeArray();
				//formData.forEach(function(e) {
				//	aoData.push({"name":e.name, "value":e.value});
				//});

				"ajax":{
					"url" : "${ctx}/business/arrival?methodtype=getArrivalHistory&contractId="+contractId,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					//"data" : JSON.stringify(aoData),
					success: function(data){		
						alert(222)
						fnCallback(data);
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				},
			//},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			
			"columns" : [
			        	{"data": null,"className":"dt-body-center"
					}, {"data": "arrivalId","className":"dt-body-center"
					}, {"data": "arriveDate","className":"dt-body-center"
					}, {"data": "materialId"
					}, {"data": "materialName"
					}, {"data": "unit","className":"dt-body-center"
					}, {"data": "quantity","className":"td-right"	
					}, {"data": "status","className":"td-center"
					}, {"data": null,"className":"td-center"
					}
				] ,
				"columnDefs":[
		    		{"targets":8,"render":function(data, type, row){
		    			var contractId = row["contractId"];		    			
		    			var rtn= "<a href=\"###\" onClick=\"doEdit('" + row["contractId"] + "','" + row["arrivalId"] + "')\">编辑</a>";
		    			return rtn;
		    		}},
		    	]        
			
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

		historyAjax();//到货登记历史记录		
		
		$('#example2').DataTable().on('click', 'tr', function() {
			

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#example2').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
			
		});
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/arrival?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}
	
</script>


	


<fieldset>
	<legend>收货记录</legend>
	<div class="list">	
	<table id="example2" class="display" >
		<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" style="width:60px">收货编号</th>
				<th class="dt-center" width="100px">到货日期</th>
				<th class="dt-center" width="150px">物料编号</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="40px">单位</th>
				<th class="dt-center" width="80px">到货数量</th>
				<th class="dt-center" width="60px">状态</th>
				<th class="dt-center" width="30px"></th>
			</tr>
		</thead>
	</table>
	</div>
</fieldset>


