<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>预付款--申请一览</title>
<script type="text/javascript">

	function ajax(action,type,scrollHeight,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/payment?methodtype=beforehandSearch";
		url = url + "&sessionFlag="+sessionFlag;
		url = url + "&finishStatus="+type;

		
		//var scrollHeight = $(document).height(); 
		var t = $('#TMaterial').DataTable({
				"paging": action,
				"iDisplayLength" : 300,
				"lengthChange":false,
				//"lengthMenu":[10,150,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"ordering "	:true,
				"searching" :false,
				"stateSave" :false,
	         	"bAutoWidth":false,
				"pagingType" : "full_numbers",
				//"retrieve" : true,
				"sAjaxSource" : url,
				"scrollY":scrollHeight,
				"scrollCollapse":false,
				 "aaSorting": [[ 4, "asc" ]],
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
							fnCallback(data);
							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);

							$("input[aria-controls='TMaterial']").css("height","25px");
							$("input[aria-controls='TMaterial']").css("width","200px");
							$("#TMaterial_filter").css("float","left");

						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null,"className" : 'td-left'},//0
					{"data": null, "defaultContent" : '', "className" : 'td-left'},//1 付款申请编号
					{"data": "contractId", "defaultContent" : '', "className" : 'td-left'},//2
					{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},//3				
					{"data": "supplierId", "defaultContent" : '', "className" : 'td-left'},//4		
					{"data": "supplierName", "defaultContent" : ''},//5
					{"data": "totalPrice", "defaultContent" : '0', "className" : 'td-right'},//6合同金额
					{"data": "chargeback", "defaultContent" : '0', "className" : 'td-right'},//7合同扣款
					{"data": "stockInDate", "className" : 'td-center'},//8约定付款日
					{"data": "agreementDate", "className" : 'td-center'},//9约定付款日
					{"data": "finishDate", "className" : 'td-center'},//10实际付款日
					{"data": "finishStatus", "className" : 'td-center'},//11
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"];
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = row["paymentId"];//
		    			if(rtn == ""){
				    		rtn= "<a href=\"###\" onClick=\"doCreate2('" + row["contractId"] +"')\">" + "快速申请" + "</a>";
		    			}else{
			    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["contractId"] + "','"+ row["paymentId"] + "')\">" + row["paymentId"] + "</a>";
		    			}
		    			
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShowContract('" + row["contractId"] +"')\">" + row["contractId"] + "</a>";
		    			//rtn=  row["YSId"];
		    			return rtn;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    					    			
		    			return jQuery.fixedWidth(data,18);
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			return floatToCurrency(data);
		    		}},
		    		{ "bSortable": false, "aTargets": [ 0 ] },
		    		{
						"visible" : false,
						"targets" : [10]
					}
	           
	         ] 
			});
	}

	$(document).ready(function() {
	
		var scrollHeight = $(document).height() - 200; 
		ajax("true","010",scrollHeight,"true");
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			$(this).toggleClass("selected");
		    if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
		        
		    	$(this).children().first().children().prop("checked", true);
		    }else{//如果没有被选中

		    	$(this).children().first().children().prop("checked", false);
		    }			
		});	
		

	})	
	
	function toFixed(num, s) {
    var times = Math.pow(10, s)
    //var des = num * times + 0.5
    var des = parseInt(num* times, 10) / times
    return des + ''
}
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		var scrollHeight = $(document).height() - 200; 
		
		ajax("false","",scrollHeight,"false");

	}
	
	function doSearch2(colNum,type) {	

		//$("#keyword1").val("");
		//$("#keyword2").val("");
		var scrollHeight = $(document).height() - 200; 
		
		ajax("false",type,scrollHeight,"true");

	}
	
	
	function doCreate2(contractId) {
	
		var paymentTypeId = $("#paymentTypeId").val();
		var url = '${ctx}/business/payment?methodtype=beforeAddinit';
		url = url +"&contractIds="+contractId;
		url = url +"&paymentTypeId="+paymentTypeId;
		location.href = url;
		
	}
	
	function doShowDetail(contractId,paymentId) {

		var paymentTypeId = $("#paymentTypeId").val();
		var url = '${ctx}/business/payment?methodtype=paymentView&contractId=' + contractId+'&paymentId='+paymentId;
		url = url +"&paymentTypeId="+paymentTypeId;
		location.href = url;
	}

	function doShowContract(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("采购合同",url);
	}	
	
	
</script>
</head>

<body>
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition"  style='padding: 0px; margin: 10px;' >
					
					<input type="hidden" id="paymentTypeId" value="020"><!-- 预付款 -->

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
			<div style="height:10px"></div>
		
			<div class="list">					
				<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:left">
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2(1,'010');"><span>待申请</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2(8,'020');"><span>待审核</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2(8,'030');"><span>待付款</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2(8,'050');"><span>已完成</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2(8,'060');"><span>审核未通过</span></a>
				</div>				
				<table style="width: 100%;" id="TMaterial" class="display">
					<thead>						
						<tr>					
							<th width="10px">No</th>
							<th width="65px">付款编号</th>
							<th width="70px">合同编号</th>
							<th width="60px">耀升编号</th>							
							<th width="70px">供应商编号</th>						
							<th>供应商名称</th>
							<th width="60px">合同金额</th>
							<th width="60px">合同扣款</th>
							<th width="60px">入库日期</th>
							<th width="60px">约定付款日</th>
							<th width="60px">实际付款日</th>
							<th width="50px">付款状态</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
