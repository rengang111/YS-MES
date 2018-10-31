<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>到货登记一览(合同未到货)</title>
<script type="text/javascript">

	function ajax(type,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var makeType = $("#makeType").val();
		var url = "${ctx}/business/arrival?methodtype=contractArrivalSearch"+"&sessionFlag="+sessionFlag;
		url = url + "&makeType="+makeType;
		url = url + "&searchSts="+type;
		
		if(type == '0'){			
			url += "&accumulated1=0"+"&deliveryDate="+shortToday();//逾期未到货
		}else if(type == '1'){			
			url += "&accumulated1=0";//未到货			
		}else if(type == '2'){			
			url += "&accumulated2=0";//已到货			
		}
		url += "&actionType="+type;//

		var t = $('#TMaterial').DataTable({
			"paging": true,
			 "iDisplayLength" : 100,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"autoWidth"	:false,
			"searching" : false,
			"pagingType" : "full_numbers",
			// "aaSorting": [[ 7, "ASC" ]],
			//"retrieve" : true,
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
				{"data": "contractId"},
				{"data": "materialId"},
				{"data": "materialName"},
				{"data": "customerShortName", "defaultContent" : '-',"className" : 'td-center'},
				{"data": "supplierId"},
				{"data": "deliveryDate","className" : 'td-right'},
				{"data": "quantity","className" : 'td-right'},
				{"data": "arrivalQty","className" : 'td-right'}, // 10
				{"data": null,"className" : 'td-right', "defaultContent" : '-'},
				{"data": "arriveDate","className" : 'td-center'},// 11

			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":2,"render":function(data, type, row){
	    			var mateid= data;
	    			if(data.length>18)
						mateid= '<div style="font-size: 11px;">' + data + '</div>';
	    			return  "<a href=\"###\" onClick=\"doCreate('" + row["contractId"] + "')\">"+mateid+"</a>";	    			
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var mateid= data;
	    			if(data.length>24)
						mateid= '<div style="font-size: 11px;">' + data + '</div>';
	    			return  "<a href=\"###\" onClick=\"doShow('" + row["materialParentId"] + "','" + row["materialRecordId"] + "','" + row["materialId"] + "')\">"+mateid+"</a>";	    			
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];				    			
	    			name = jQuery.fixedWidth(name,32);				    			
	    			return name;
	    		}},
	    		{"targets":9,"render":function(data, type, row){
	    			//累计收货
	    			var arrivalQty = currencyToFloat(row["arrivalQty"]);	
	    			var stockinRtnQty = currencyToFloat(row["stockinRtnQty"]);	
	    			var inspectRtnQty = 0;//currencyToFloat(row["sumReturnQty"]);	 
	    			var returnQty = stockinRtnQty + inspectRtnQty;
	    			var newQty = setPurchaseQuantity(returnQty,arrivalQty );	
	    			
	    			return floatToCurrency(newQty);
	    		}},
	    		{"targets":11,"render":function(data, type, row){
	    			if(data ==''){
	    				return '（未到货）';   				
	    			}else{
	    				return data;    				
	    			}
	    		}},
	    		{
	    			"visible":false,
	    			"targets":[10]
	    		}
	           
	         ] 
		});

	}

	


	$(document).ready(function() {

		var searchSts = $('#searchSts').val();
		
		ajax(searchSts,"true");//逾期未到货
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});		

		buttonSelectedEvent();//按钮选择式样

		$('#defutBtn'+searchSts).removeClass("start").addClass("end");
	})	
	
	function doCreate(contractId) {

		var makeType=$('#makeType').val();
		var url = '${ctx}/business/arrival?methodtype=addinit&contractId='+contractId+
			'&makeType='+makeType;
		location.href = url;
	}
	

	function doShow(parentid,recordid,materialId) {

		//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
		var url = '${ctx}/business/material?methodtype=detailView';
		url = url + '&parentId=' + parentid+'&recordId='+recordid+'&materialId='+materialId+'&keyBackup=1';
		
		callWindowFullView("物料信息",url);
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

	function reload() {
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		
		return true;
	}
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		ajax("",'false');

	}
	
	function selectContractByDate(type,sessionFlag){

		$("#keyword1").val("");
		$("#keyword2").val("");
		ajax(type,sessionFlag);
	}
	
	function showYS(YSId){
		var url = '${ctx}/business/order?methodtype=detailView&YSId=' + YSId;

		openLayer(url);
	}
	
	function showContract(contractId) {
		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
		openLayer(url);

	};
	
</script>
</head>

<body>
<div id="container">
<div id="main">		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >

		<input type="hidden" id="makeType" value="${makeType }" />
		<input type="hidden" id="searchSts" value="${searchSts }" />
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
			<a id="defutBtn1" class="DTTT_button DTTT_button_text box" onclick="selectContractByDate('1','false');">未到货</a>
			<a id="defutBtn0" class="DTTT_button DTTT_button_text box" onclick="selectContractByDate('0','false');">逾期未到货</a>
			<a id="defutBtn2" class="DTTT_button DTTT_button_text box" onclick="selectContractByDate('2','false');">已收货</a>
		</div>

		<div id="clear"></div>
		<table id="TMaterial" class="display dataTable" style="width: 100%;">
			<thead>						
				<tr>
					<th width="10px">No</th>
					<th style="width: 50px;">耀升编号</th>
					<th style="width: 90px;">合同编号</th>
					<th style="width: 100px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 30px;">客户</th>
					<th style="width: 50px;">供应商</th>
					<th style="width: 50px;">合同交期</th>
					<th style="width: 50px;">合同数</th>
					<th style="width: 50px;">累计收货</th>
					<th style="width: 50px;">累计入库</th>
					<th width="50px">收货日期</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
