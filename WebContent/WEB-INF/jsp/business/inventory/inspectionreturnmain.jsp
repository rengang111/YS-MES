<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>报检退货一览</title>
<script type="text/javascript">

	function ajax(status,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		
		var url = "${ctx}/business/inspectionReturn?methodtype=search"
				+"&sessionFlag="+sessionFlag;
		
		if(status != ''){
			//未检验
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&status="+status;
			
		}		

		url += "&keyBackup="+status;
	
		var t = $('#TMaterial').DataTable({
				"paging": true,
				 "iDisplayLength" : 150,
				"lengthChange":false,
				//"lengthMenu":[10,150,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"pagingType" : "full_numbers",
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
								//alert(XMLHttpRequest.status);
								//alert(XMLHttpRequest.readyState);
								//alert(textStatus);
								//alert(errorThrown);
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
					{"data": "checkDate","className" : 'td-center'},
					{"data": "YSId"},
					{"data": "contractId","className" : 'td-left'},
					{"data": "contractQuantity","className" : 'td-right'},
					{"data": "quantity","className" : 'td-right'},
					{"data": "returnDate","className" : 'td-center'},
					{"data": "status","className" : 'td-center'},
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
                    }},
		    		{"targets":1,"render":function(data, type, row){

		    			var materialId = row["materialId"];	
		    			var arrivalId = row["arrivalId"];	
		    			var contractId = row["contractId"];			    			
		    			var rtn= "<a href=\"###\" onClick=\"doShow('" + arrivalId + "','" + contractId + "')\">"+materialId+"</a>";
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			
		    			var name = row["materialName"];				    			
		    			name = jQuery.fixedWidth(name,35);				    			
		    			return name;
		    		}}	           
	         ] 
		});

	}

	


	$(document).ready(function() {

		//true:使用session
		//010:默认显示未处理的数据
		ajax("010","true");
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});		
	})	
	
	function doSearch() {	

		//false:不使用session
		ajax("","false");
	}
	
	function doShow(arrivalId,contractId) {

		var keyBackup = $("#keyBackup").val();
		var url = '${ctx}/business/inspectionReturn?methodtype=addinit&contractId='+contractId
				+'&arrivalId='+arrivalId+'&keyBackup='+keyBackup;
		location.href = url;
		
	}

	
</script>
</head>

<body>
<div id="container">
<div id="main">		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<input type="hidden" id="keyBackup" value="${keyBackup }" />
			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label">关键字1：</td>
					<td class="condition">
						<input type="text" id="keyword1" name="keyword1" class="middle" value="${keyword1 }" />
					</td>
					<td class="label">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle" value="${keyword2 }" />
					</td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
			</table>
		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
		<div id="DTTT_container" align="left" style="height:40px;width:50%">
			<a class="DTTT_button DTTT_button_text" onclick="ajax('010','false');">未处理</a>
			<a class="DTTT_button DTTT_button_text" onclick="ajax('020','false');">已处理</a>
		</div>
		<div id="clear"></div>
		<table id="TMaterial" class="display dataTable">
			<thead>						
				<tr>
					<th style="width: 1px;" class="dt-middle ">No</th>
					<th style="width: 120px;" class="dt-middle ">物料编号</th>
					<th class="dt-middle">物料名称</th>
					<th style="width: 60px;" class="dt-middle">报检日期</th>
					<th style="width: 80px;" class="dt-middle">耀升编号</th>
					<th style="width: 95px;" class="dt-middle">合同编号</th>
					<th style="width: 70px;" class="dt-middle">合同数量</th>
					<th style="width: 70px;" class="dt-middle ">退货数量</th>
					<th style="width: 70px;" class="dt-middle ">检验时间</th>
					<th style="width: 50px;" class="dt-middle ">处理结果</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
