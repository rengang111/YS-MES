<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>进料报检一览(报检前)</title>
<script type="text/javascript">

	function ajax(pageFlg,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		
		var url = "${ctx}/business/receiveinspection?methodtype=search"+"&sessionFlag="+sessionFlag;
		
		var type = pageFlg;

		if(pageFlg != ''){
			//未检验
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&checkResult="+pageFlg;
						
		}else{
			//点击查询按钮 不区分状态
			url += "&checkResult=";
		}
		
		url += "&keyBackup="+pageFlg;

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
				"aaSorting": [[ 7, "ASC" ]],
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
					{"data": "YSId"},
					{"data": "supplierId","className" : 'td-left'},
					{"data": "contractQuantity","className" : 'td-right'},
					{"data": "quantity","className" : 'td-right'},
					{"data": "arriveDate","className" : 'td-center'},
					{"data": "checkDate","className" : 'td-center', "defaultContent" : '（未检验）'},
					{"data": "checkResult","className" : 'td-center'},
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"];
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
		    			name = jQuery.fixedWidth(name,40);				    			
		    			return name;
		    		}},
		    		{
						"visible" : false,
						"targets" : []
					}
	           
	         ] 
		});

	}

	


	$(document).ready(function() {
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

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		ajax("","false");

	}
	
	function doShow(arrivalId,contractId) {

		var keyBackup = $("#keyBackup").val();
		var url = '${ctx}/business/receiveinspection?methodtype=addinit&contractId='+contractId
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
			<a class="DTTT_button DTTT_button_text" onclick="ajax('010','false');">未检验</a>
			<a class="DTTT_button DTTT_button_text" onclick="ajax('020','false');">合格</a>
			<a class="DTTT_button DTTT_button_text" onclick="ajax('030','false');">让步接收</a>
			<a class="DTTT_button DTTT_button_text" onclick="ajax('040','false');">退货</a>
		</div>
		<div id="clear"></div>
		<table id="TMaterial" class="display dataTable">
			<thead>						
				<tr>
					<th style="width: 1px;" class="dt-middle ">No</th>
					<th style="width: 120px;" class="dt-middle ">物料编号</th>
					<th class="dt-middle">物料名称</th>
					<th style="width: 80px;" class="dt-middle">耀升编号</th>
					<th style="width: 70px;" class="dt-middle">供应商</th>
					<th style="width: 60px;" class="dt-middle">合同数量</th>
					<th style="width: 60px;" class="dt-middle ">到货数量</th>
					<th style="width: 60px;" class="dt-middle">到货日期</th>
					<th style="width: 60px;" class="dt-middle ">检验日期</th>
					<th style="width: 40px;" class="dt-middle ">结果</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
