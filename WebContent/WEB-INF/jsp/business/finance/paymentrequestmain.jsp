<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>应付款--申请一览</title>
<script type="text/javascript">

	function ajax(action,type,scrollHeight,sessionFlag,SearchFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		
		var url = "${ctx}/business/payment?methodtype=search";
		url = url + "&sessionFlag="+sessionFlag;
		url = url + "&finishStatus="+type;
		url = url + "&searchType=" + type;

		var colSort = 4;
		if(type == '070')
			colSort = 8;
		
		var hideCol = 10;
		if(type == "")
			hideCol = '9';
		
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
					{"data": null,"className" : 'td-center'},//0
					{"data": null, "defaultContent" : '', "className" : 'td-left'},//1 付款申请编号
					{"data": "contractId", "defaultContent" : '', "className" : 'td-left'},//2
					{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},//3				
					{"data": "supplierId", "defaultContent" : '', "className" : 'td-left'},//4		
					{"data": "supplierName", "defaultContent" : ''},//5
					{"data": "totalPrice", "defaultContent" : '0', "className" : 'td-right'},//6合同金额
					{"data": "chargeback", "defaultContent" : '0', "className" : 'td-right'},//7合同扣款
					{"data": "stockInDate", "className" : 'td-center'},//8入库时间
					{"data": "invoiceDate", "defaultContent" : '***',"className" : 'td-center'},//10发票日期
					{"data": "finishStatus", "className" : 'td-center'},//11
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			var paymentId = row["paymentId"];
		    			if(paymentId == ""){
							return "<input type=checkbox name='numCheck' id='numCheck' value='" + row["contractId"] + "' />";		    				
		    			}else{
							return row["rownum"];
		    			}
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
		    			var contractId = jQuery.fixedWidth(row["contractId"],16);
		    			rtn= "<a href=\"###\" onClick=\"doShowContract('" + row["contractId"] +"')\">" + contractId + "</a>";
		    			return rtn;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    					    			
		    			return jQuery.fixedWidth(data,12);
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    					    			
		    			return jQuery.fixedWidth(data,24);
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			return floatToCurrency(data);
		    		}},
		    		{ "bSortable": false, "aTargets": [ 0 ] },
		    		{
						"visible" : false,
						"targets" : [hideCol]
					}
	         ] 
		});
	}

	$(document).ready(function() {
	
		var scrollHeight = $(document).height() - 200; 
		var type = $("#searchType").val();
		ajax("true",type,scrollHeight,"true","R");
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			$(this).toggleClass("selected");
		    if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
		        
		    	$(this).children().first().children().prop("checked", true);
		    }else{//如果没有被选中

		    	$(this).children().first().children().prop("checked", false);
		    }			
		});	

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

		var type = $("#searchType").val();
		ajax("false",type,scrollHeight,"false","S");

	}
	
	function doSearch2(colNum,type) {	

		//$("#keyword1").val("");
		//$("#keyword2").val("");
		
		$("#searchType").val(type);
		var scrollHeight = $(document).height() - 200; 
		
		ajax("false",type,scrollHeight,"false","C");

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
	
	function doCreate2(contractId) {

		var paymentTypeId = $("#paymentTypeId").val();
		var searchType = $("#searchType").val();
		
		var url = '${ctx}/business/payment?methodtype=addinit';
		url = url +"&contractIds="+contractId;
		url = url +"&paymentTypeId="+paymentTypeId;
		url = url +"&searchType="+searchType;
		location.href = url;
		
	}
	
	function doShowDetail(contractId,paymentId) {

		var url = '${ctx}/business/payment?methodtype=paymentView&contractId=' + contractId+'&paymentId='+paymentId;

		url = url +"&searchType="+searchType;
		
		location.href = url;
	}

	function doShowContract(contractId) {

		var searchType = $("#searchType").val();
		
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
							<td width="50px"></td> 
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
					<a class="DTTT_button  box" onclick="doSearch2(1,'010');" id="defutBtn010"><span>待申请</span></a>
					<a class="DTTT_button  box" onclick="doSearch2(8,'020');" id="defutBtn020"><span>待审核</span></a>
					<a class="DTTT_button  box" onclick="doSearch2('','');" id="defutBtn030"><span>ALL</span></a>
					<!-- a class="DTTT_button  box" onclick="doSearch2(8,'030');" id="defutBtn030"><span>待付款</span></a -->
					<!-- a class="DTTT_button  box" onclick="doSearch2(8,'010');" id="defutBtn010"><span>未完成</span></a -->
					<!-- a class="DTTT_button  box" onclick="doSearch2(8,'050');" id="defutBtn050"><span>已完成</span></a -->
					<!-- a class="DTTT_button  box" onclick="doSearch2(8,'060');" id="defutBtn060"><span>审核未通过</span></a>&nbsp;&nbsp; -->
					<!-- a class="DTTT_button  box" onclick="doSearch2(8,'070');" id="defutBtn070"><span>逾期未付款</span></a -->
					<a class="DTTT_button " onclick="downloadExcel('080');" ><span>EXCEL导出</span></a>
				</div>
				<div style="height: 40px;margin-bottom: -15px;float:right">
					<a class="DTTT_button " onclick="doCreate();">付款申请</a>
				</div>
				<table style="width: 100%;" id="TMaterial" class="display">
					<thead>						
						<tr>					
							<th width="50px">
								<input type="checkbox" name="selectall" id="selectall" onclick="fnselectall()"/><label for="selectall">全选</label><br>
								<input type="checkbox" name="reverse" id="reverse" onclick="fnreverse()" /><label for="reverse">反选</label></th>
							<th width="65px">付款编号</th>
							<th width="70px">合同编号</th>
							<th width="60px">耀升编号</th>							
							<th width="70px">供应商编号</th>						
							<th>供应商名称</th>
							<th width="60px">合同金额</th>
							<th width="50px">合同扣款</th>
							<th width="60px">入库日期</th>
							<th width="60px">发票日期</th>
							<th width="50px">付款状态</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
