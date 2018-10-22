<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>应收款--一览</title>
<script type="text/javascript">

	function ajax(action,type,scrollHeight,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var searchType = $("#searchType").val();
		
		var url = "${ctx}/business/receivable?methodtype=search";
		url = url + "&sessionFlag="+sessionFlag;
		url = url + "&status="+type;
		url = url + "&searchType=" + type;

		var colSort = 8;
		//if(type == '070')
		//	colSort = 8;
			
		//var scrollHeight = $(document).height(); 
		var t = $('#TMaterial').DataTable({
				"paging": false,//action,
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
				 "aaSorting": [[ colSort, "asc" ]],
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

							//$("input[aria-controls='TMaterial']").css("height","25px");
							//$("input[aria-controls='TMaterial']").css("width","200px");
							//$("#TMaterial_filter").css("float","left");

						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null,"className" : 'td-center'},//0
					{"data": null, "defaultContent" : '', "className" : 'td-left'},//1 收款编号
					{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},//2
					{"data": "productId", "defaultContent" : '', "className" : 'td-left'},//3				
					{"data": "productName", "defaultContent" : '', "className" : ''},//4		
					{"data": "orderPrice", "defaultContent" : '0', "className" : 'td-right'},//5 应收金额
					{"data": "bankDeductionCnt", "defaultContent" : '0', "className" : 'td-right'},//6银行扣款
					{"data": "actualAmountCnt", "defaultContent" : '0', "className" : 'td-right'},//7实收金额
					{"data": "reserveDate", "className" : 'td-center', "defaultContent" : '***'},//8 约定收款日
					{"data": "collectionDate", "className" : 'td-center', "defaultContent" : '***'},//9 实际收款日
					{"data": "status", "className" : 'td-center'},//10 收款状态
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"];
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = row["receivableId"];//
		    			if(rtn == ""){
				    		rtn= "<a href=\"###\" onClick=\"doCreate2('" + row["YSId"] +"')\">" + "（未收款）" + "</a>";
		    			}else{
			    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["YSId"] + "','"+ row["receivableId"] + "')\">" + row["receivableId"] + "</a>";
		    			}
		    			
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			var rtn = "";
		    			var contractId = jQuery.fixedWidth(row["YSId"],10);
		    			//rtn= "<a href=\"###\" onClick=\"doShowContract('" + row["contractId"] +"')\">" + contractId + "</a>";
		    			return contractId;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    					    			
		    			return jQuery.fixedWidth(data,32);
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    					    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    					    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			return floatToCurrency(data);
		    		}},
		    		{ "bSortable": false, "aTargets": [ 0 ] },
		    		{
						"visible" : false,
						"targets" : []
					}
	           
	         ] 
			});
	}

	$(document).ready(function() {
	
		var scrollHeight = $(document).height() - 200; 
		var type = $('#searchType').val();
		ajax("true",type,scrollHeight,"true");
	/*
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			$(this).toggleClass("selected");
		    if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
		        
		    	$(this).children().first().children().prop("checked", true);
		    }else{//如果没有被选中

		    	$(this).children().first().children().prop("checked", false);
		    }			
		});	
*/
		var searchType = $('#searchType').val();
		buttonSelectedEvent();//按钮选择式样
		$('#defutBtn'+searchType).removeClass("start").addClass("end");

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
		
		ajax("false",type,scrollHeight,"false");

	}
	
	function doCreate() {
		var str = '';
		var supplierId = '';
		var supplierId_next = '';
	    var flag = true;
	    var rtnValue = true;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				str += $(this).val() + ",";	
								
				if(flag){
					supplierId = $(this).parent().parent().find('td').eq(4).text();
					supplierId_next = $(this).parent().parent().find('td').eq(4).text();
					flag = false;
				}else{
					supplierId_next = $(this).parent().parent().find('td').eq(4).text();
					//alert(supplierId+"---"+supplierId_next)
					if(supplierId != supplierId_next){
					
						$().toastmessage('showWarningToast', "请选择同一个供应商。");

						rtnValue = false;
						return false;
					}
				}
			
			
			}
		});

		
		if(!rtnValue)
			return;
		
		if (str == '') {
			alert("请至少选择一条数据");
			return;
		}	
		var paymentTypeId = $("#paymentTypeId").val();	
		var url = '${ctx}/business/payment?methodtype=addinit';
		url = url +"&contractIds="+str;
		url = url +"&paymentTypeId="+paymentTypeId;
		location.href = url;
		
	}
	
	function doCreate2(YSId) {

		var url = '${ctx}/business/receivable?methodtype=addInit';
		url = url +"&YSId="+YSId;
		location.href = url;
		
	}
	
	function doShowDetail(YSId,receivableId) {

		var url = '${ctx}/business/receivable?methodtype=receivableDetailInit&receivableId=' + receivableId+'&YSId='+YSId;
		
		location.href = url;
	}

	function doShowContract(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&openFlag=newWindow&contractId=' + contractId;
		
		callProductDesignView("采购合同",url);
	}	
	

	function fnselectall() { 
		if($("#selectall").prop("checked")){
			$("input[name='numCheck']").each(function() {
				$(this).prop("checked", true);
				$(this).parent().parent().addClass("selected");
			});
				
		}else{
			$("input[name='numCheck']").each(function() {
				if($(this).prop("checked")){
					$(this).removeAttr("checked");
					$(this).parent().parent().removeClass('selected');
				}
			});
		}
	};
	
	function fnreverse() { 
		$("input[name='numCheck']").each(function () {  
	        $(this).prop("checked", !$(this).prop("checked"));  
			$(this).parent().parent().toggleClass("selected");
	    });
	};
	
	function downloadExcel(finishStatus) {
		 
		var key1 = $("#keyword1").val();
		var key2 = $("#keyword2").val();
		var searchType = $("#searchType").val();
		
		var url = '${ctx}/business/payment?methodtype=downloadExcelForPayment'
				
				 + "&key1=" + key1
				 + "&key2=" + key2
				 + "&finishStatus=070";
				 + "&searchType=" + searchType;
		
		url =encodeURI(encodeURI(url));//中文两次转码

		location.href = url;
	}
	
</script>
</head>

<body>
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition"  style='padding: 0px; margin: 10px;' >
										
					<input type="hidden" id="paymentTypeId" value="010"><!-- 正常付款 -->
					<input type="hidden" id="searchType" value="${searchType }"><!-- 快速查询按钮 -->

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
					<a class="DTTT_button DTTT_button_text box" onclick="doSearch2(1,'010');" id="defutBtn010"><span>待收款</span></a>
					<a class="DTTT_button DTTT_button_text box" onclick="doSearch2(8,'020');" id="defutBtn020"><span>部分收款</span></a>
					<a class="DTTT_button DTTT_button_text box" onclick="doSearch2(8,'030');" id="defutBtn030"><span>已收款</span></a>&nbsp;&nbsp;
					<a class="DTTT_button DTTT_button_text box" onclick="doSearch2(8,'070');" id="defutBtn070"><span>逾期未收款</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="downloadExcel('080');" ><span>EXCEL导出</span></a>
				</div>
				<!-- 
				<div style="height: 40px;margin-bottom: -15px;float:right">
					<a class="DTTT_button DTTT_button_text" onclick="doCreate();">付款申请</a>
				</div> -->
				<table style="width: 100%;" id="TMaterial" class="display">
					<thead>						
						<tr>					
							<th width="1px"></th>
							<th width="60px">收款编号</th>
							<th width="60px">耀升编号</th>							
							<th width="70px">产品编号</th>						
							<th>产品名称</th>
							<th width="60px">应收金额</th>
							<th width="50px">银行扣款</th>
							<th width="60px">实收金额</th>
							<th width="60px">约定收款日</th>
							<th width="60px">实际收款日</th>
							<th width="50px">收款状态</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
