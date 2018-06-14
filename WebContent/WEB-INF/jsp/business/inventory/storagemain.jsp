<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>入库登记一览(检验完毕)</title>
<script type="text/javascript">

	function ajax(status,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var makeType = $("#makeType").val();
		var url = "${ctx}/business/storage?methodtype=search"+"&sessionFlag="+sessionFlag;
		url = url + "&makeType="+makeType;
		
		if(status != ''){
			//默认是质检合格或者让步接收
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&status="+status;
			
		}

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
			"aaSorting": [[ 10, "ASC" ]],
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
						//alert("recordsTotal"+data["recordsTotal"])
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
				{"data": "receiptId"},
				{"data": "materialId"},
				{"data": "materialName"},
				{"data": "unit","className" : 'td-center'},
				{"data": "supplierId", "defaultContent" : '***'},
				{"data": "YSId", "defaultContent" : '***'},
				{"data": "contractQuantity","className" : 'td-right', "defaultContent" : '***'},
				{"data": "stockinQty","className" : 'td-right'},
				{"data": "quantityQualified","className" : 'td-right'},
				{"data": "checkDate","className" : 'td-center'},
				{"data": "checkInDate","className" : 'td-center'},
				
				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":1,"render":function(data, type, row){

	    			var contractId = row["contractId"];	
	    			var arrivalId = row["arrivalId"];	
	    			if(data == ''){
		    			var rtn= "<a href=\"###\" onClick=\"doAdd('" + contractId + "','" + arrivalId + "','" + row["receiptId"] + "')\">"+"（未入库）"+"</a>";
    				
	    			}else{
		    			var rtn= "<a href=\"###\" onClick=\"doShow('" + contractId + "','" + arrivalId + "','" + row["receiptId"] + "')\">"+data+"</a>";
	    				
	    			}	    			
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];				    			
	    			name = jQuery.fixedWidth(name,35);				    			
	    			return name;
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			
	    			var rtn = data;
	    			if(data == null || data == "")
	    				rtn = "***";				    			
	    			return rtn;
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			
	    			var rtn = data;
	    			if(data == null || data == "")
	    				rtn = "***";				    			
	    			return rtn;
	    		}},
	    		{"targets":8,"render":function(data, type, row){
	    			
	    			var reverse = row["reverse"];
	    			var rtn = floatToCurrency(data);
	    			if(reverse == '1'){
	    				var txt = '<span style="color: red;font-weight: bolder;">'
	    				rtn = txt + floatToCurrency(currencyToFloat(data) * (-1)) +'</span>';
	    			}				    			
	    			return rtn;
	    		}},
	    		{"targets":9,"render":function(data, type, row){
	    			
	    			var reverse = row["reverse"];
	    			var rtn = floatToCurrency(data);
	    			if(reverse == '1'){	    				
	    				rtn = '(退货)';
	    			}				    			
	    			return rtn;
	    		}},
	    		{
					"visible" : false,
					"targets" : [4]
				}
	           
	         ] 
		});

	}

	


	$(document).ready(function() {
		
		//010:默认显示未处理的数据
		ajax("020","true");
	
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
	
	
	
	function doAdd(contractId,arrivalId,receiptId) {

		var makeType=$('#makeType').val();
		var url = '${ctx}/business/storage?methodtype=addinit&contractId=' 
				+ contractId
				+"&arrivalId="+arrivalId
				+"&receiptId="+receiptId
				+"&makeType="+makeType;

		location.href = url;
	}

	

	function doShow(contractId,arrivalId,receiptId) {

		var makeType=$('#makeType').val();
		var url = '${ctx}/business/storage?methodtype=showStockinDetail&contractId=' 
				+ contractId
				+"&arrivalId="+arrivalId
				+"&receiptId="+receiptId
				+"&makeType="+makeType;

		location.href = url;
	}
	function doDelete() {

		var str = '';
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				str += $(this).val() + ",";
			}
		});

		if (str != '') {
			if(confirm("确定要删除数据吗？")) {
				jQuery.ajax({
					type : 'POST',
					async: false,
					contentType : 'application/json',
					dataType : 'json',
					data : str,
					url : "${ctx}/business/arrival?methodtype=delete",
					success : function(data) {
						reload();						
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				});
			}
		} else {
			alert("请至少选择一条数据");
		}
		
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

		<input type="hidden" id="makeType" value="${makeType }" />
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
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('020','false');">未入库</a>
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('030','false');">已入库</a>
		</div>
		<table id="TMaterial" class="display" style="width: 100%;">
			<thead>						
				<tr>
					<th style="width: 1px;" class="dt-middle ">No</th>
					<th style="width: 70px;" class="dt-middle">入库单编号</th>
					<th style="width: 100px;" class="dt-middle ">物料编号</th>
					<th class="dt-middle">物料名称</th>
					<th style="width: 50px;" class="dt-middle">单位</th>
					<th style="width: 70px;" class="dt-middle">供应商</th>
					<th style="width: 50px;" class="dt-middle">耀升编号</th>
					<th style="width: 60px;" class="dt-middle">合同数量</th>
					<th style="width: 60px;" class="dt-middle">已入库数</th>
					<th style="width: 60px;" class="dt-middle">质检数量</th>
					<th style="width: 50px;" class="dt-middle">质检日期</th>
					<th style="width: 50px;" class="dt-middle">入库时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
