<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>成品入库一览</title>
<script type="text/javascript">

	function ajax(status,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var url = "${ctx}/business/storage?methodtype=orderSearch"+"&sessionFlag="+sessionFlag;
		
		if(status != ''){
			//默认是质检合格或者让步接收
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&status="+status;
			
		}
		url += "&keyBackup="+status;

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
				{"data": "YSId"},
				{"data": "materialId"},
				{"data": "materialName"},
				{"data": "orderQty","className" : 'td-right'},
				{"data": "stockinQty","className" : 'td-right'},
				{"data": "storageDate","className" : 'td-center',"defaultContent" : '（未入库）'},
								
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":1,"render":function(data, type, row){

	    			var contractId = row["contractId"];	
	    			var arrivalId = row["arrivalId"];		    			
	    			var rtn= "<a href=\"###\" onClick=\"doShow('" + row["YSId"] + "')\">"+row["YSId"]+"</a>";
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];				    			
	    			name = jQuery.fixedWidth(name,48);				    			
	    			return name;
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    						    			
	    			return floatToCurrency(data);
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    						    			
	    			return floatToCurrency(data);
	    		}}
	           
	         ] 
		});

	}

	$(document).ready(function() {
		
		//030:默认显示待生产的数据
		ajax("030","true");
	
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
		
	function doShow(YSId) {

		var url = '${ctx}/business/storage?methodtype=productAddInit&YSId=' + YSId;

		location.href = url;
	}

	
	function selectContractByDate(status,sessionFlag){
	
		ajax(status,sessionFlag);
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
	</div>
	<div  style="height:10px"></div>

	<div class="list">
		<div id="DTTT_container" align="left" style="height:40px;width:50%">
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('030','false');">待交货</a>
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('040','false');">已入库</a>
		</div>
		<table id="TMaterial" class="display dataTable" style="width: 100%;">
			<thead>						
				<tr>
					<th style="width: 1px;">No</th>
					<th style="width: 100px;">耀升编号</th>
					<th style="width: 150px;">产品编号</th>
					<th>产品名称</th>
					<th style="width: 80px;">订单数量</th>
					<th style="width: 80px;">入库数量</th>
					<th style="width: 80px;">入库时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
