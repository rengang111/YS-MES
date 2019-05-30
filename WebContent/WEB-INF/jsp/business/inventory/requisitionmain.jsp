<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>

<title>领料申请--订单一览</title>
<script type="text/javascript">

    var hideCnt = 0;

	function ajaxSearch(sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var searchFlag = $('#searchFlag').val();
		var partsType = $('#partsType').val();
		
		var url = "${ctx}/business/requisition?methodtype=requisitionMainSearch&sessionFlag="+sessionFlag
				+"&orderType=010"+"&searchFlag="+searchFlag
				+"&partsType="+partsType;

		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//每页显示条数设置
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
	         	"bAutoWidth":false,
				"bSort":true,
				"ordering"	:true,
				"searching" : false,
				"pagingType" : "full_numbers",        
	         	"aaSorting": [[ 1, "ASC" ]],
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
							fnCallback(data);

							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);
							
							var flag = $('#searchFlag').val();
							$('#defutBtn'+flag).removeClass("start").addClass("end");
							
						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null, "defaultContent" : '',"className" : 'td-center'},//0
					{"data": "machineModel", "className" : 'td-center'},// 1
					{"data": "YSId", "defaultContent" : ''}, //2
					{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},//3
					{"data": "materialName", "defaultContent" : ''},//4
					{"data": "shortName", "className" : 'td-center'},//5
					{"data": "orderQty", "defaultContent" : '0', "className" : 'td-right'},//6
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-left'},//7
					{"data": "MaxDeliveryDate", "defaultContent" : '', "className" : 'td-left'},//8
					{"data": "team", "className" : 'td-center'},//9
					{"data": "team", "className" : 'td-center'},//10
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){

		    			var currentSts  = row["currentSts"];
		    			var currentType = row["currentType"];
		    			var stockinQty  = currencyToFloat(row['computeStockinQty']);
		    			var orderQty    = currencyToFloat(row['orderQty']);
		    			var text = row["rownum"];
		    		
		    			if(currentSts == '0'){
		    				
		    				if(currentType == '31'){
		    					text = '当前';
		    				}else if(currentType == '32'){
		    					text = '中长期';
		    					
		    				}else if(currentType == '33'){
		    					text = '未领料';		    					
		    				}		    				    		
		    			}else{
		    				if(stockinQty >= orderQty){
		    					text = '已领料';
		    				}else{
		    					text = '未安排';
		    				}
		    			}
					
		    			return text;	
		    			
                    }},
		    		{"targets":1,"render":function(data, type, row){

						var lastCheceked = '<span id=""  style="display:none" class="orderHide"><input type="checkbox"  name="orderHide" id="orderHide" value="" /></span';

		    			return data + lastCheceked;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			var rtn = "";
		    			var orderQty   = currencyToFloat( row["orderQty"] );
		    			var stockinQty = currencyToFloat( row["computeStockinQty"] );
		    			var rtn="";
		    			if(stockinQty >= orderQty){//已入库
			    			rtn= "<a href=\"###\" onClick=\"showHistory('"+ row["YSId"] + "')\">"+data+"</a>";		    				
		    			}else {
			    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["YSId"] + "')\">"+data+"</a>";
		    				
		    			}	
		    			
		    			return rtn;
		    		}},
		    		{"targets":3,"render":function(data, type, row){

						var lastHide = '<input type="hidden"  name="lastHide" id="lastHide" value="' + row["hideFlag"] + '" /></span';

		    			//var rtn= "<a href=\"###\" onClick=\"doShow('"+ row["PIId"] + "')\">"+data+"</a>";
		    			
		    			return data + lastHide;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			name = jQuery.fixedWidth(name,38);//true:两边截取,左边从汉字开始
		    			
		    			var lastCheceked = '<input type="hidden"  name="lastCheceked" id="lastCheceked" value="' + row["lastCheceked"] + '" />';
		    			
		    			return name + lastCheceked;
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			
		    			return floatToNumber(data);
		    		}},
		    		{"targets":9,"render":function(data, type, row){
		    			if(data == '' || data == null )
		    				data = '***';
		    			return data;
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : [10]
					},
					{
						//"order": [[ 2, 'asc' ]]
					}
	         	],
	         		         	
			});
	}
	
		
	function initEvent(){

		//$('#defutBtnH').hide();

		//$('#peijianFlag').val('');//配件订单不用合并

		ajaxSearch("false");
	
		$('#TMaterial').DataTable().on('click', 'tr td:nth-child(2)', function() {

			$(this).parent().toggleClass("selected");

		    var checkbox  = $(this).find("input[type='checkbox']");
		    var isChecked = checkbox.is(":checked");		    

		    if (isChecked) {
		    	hideCnt--;
		        checkbox.prop("checked", false)
		        checkbox.removeAttr("checked");
		    } else {
		    	hideCnt++;
		        checkbox.prop("checked", true)
		        checkbox.attr("checked","true");
		    }
		});	
		
	}

	$(document).ready(function() {

		$('#createCurrent').show();
		
		initEvent();
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		
		$('#defutBtnU').removeClass("start").addClass("end");
		$('#defutBtnmP').removeClass("start").addClass("end");
	})	
	
	function showHistory(YSId) {
		var virtualClass = $('#virtualClass').val();
		var url = "${ctx}/business/requisition?methodtype=getRequisitionHistoryInit&YSId="+YSId+"&virtualClass="+virtualClass;
		callWindowFullView('出库详情',url);		
	};
	
	function doShowDetail(YSId) {
		var virtualClass = $('#virtualClass').val();
		var methodtype = "addinit"
		if(virtualClass == '020'){			
			methodtype = "virtualAddinit";//虚拟领料
		}
		var peiYsid = YSId.indexOf("P");
		if(peiYsid > 0){
			methodtype =  "peiAddinit";
		}
		var url =  "${ctx}/business/requisition?methodtype="+methodtype+"&YSId="+YSId+"&virtualClass="+virtualClass;
		callWindowFullView('装配领料',url)
		//location.href = url;
	}
	
	function doSearch() {	

		var numCheck = $("#numCheck").is(":checked");
		var searchFlag = $('#searchFlag').val();
		if(numCheck){
			searchFlag = '';
			
		}else{
			if(searchFlag == 'C' || searchFlag == 'L'|| searchFlag == 'N'){
				searchFlag = "U";//未领料
			}
		}
		
		$('#searchFlag').val(searchFlag);		
	    
		ajaxSearch('false');

		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    
		$('#defutBtn'+searchFlag).removeClass("start").addClass("end");
		
		
	}
			
	//添加到当前任务
	function doCreateY(taskType,currentyType) {
		
		if(hideCnt <= 0){
			$().toastmessage('showWarningToast', "请选择数据。");		
			return;
		}
		
		//按钮显示规则
			
		var checkedList = "";
		var firstFlag = true;
		
		$('#TMaterial tbody tr').each (function (){

			var $newsts = $(this).find("td").eq(1).find("input");
			var ysid    = $(this).find("td").eq(2).text();			

			var checkFlag = false;
			
			if( $newsts.prop('checked')){//本次被选中
				checkFlag = true;
			}
			if( checkFlag == true ){
				if(firstFlag){
					checkedList += ysid;
					firstFlag = false;
				}else{
					checkedList += ',';
					checkedList += ysid;
				}			
			}		
		});
		
		var actionUrl = "${ctx}/business/requisition?methodtype=setCurrentTask"
				+"&checkedList="+checkedList
				+"&currentyType="+currentyType;

		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : actionUrl,
			data : JSON.stringify($('#orderForm').serializeArray()),// 要提交的表单
			success : function(data) {
				//alert(d)				
				ajaxSearch('false');	//刷新页面
				
				//$().toastmessage('showWarningToast', "保存成功!");		
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {				
				//alert(textStatus);
			}
		});
	}
	
	//未领料
	function doSearchCustomer(type,searchFlag){

		$('#searchFlag').val('U');//未领料
		//$('#createCurrent').show();
		
		ajaxSearch('false');
	}

	//已领料
	function doSearchCustomer2(){

		$('#searchFlag').val('F');//已领料
		//$('#createCurrent').hide();
		
		ajaxSearch('false');
		
	}
			
	//查看当前任务
	function doSearchCurrentTask(taskType){
		$('#keyword1').val('');
		$('#keyword2').val('');
		$('#searchFlag').val(taskType);//Current:当前任务
		
		ajaxSearch('false');
	}

	
	function doShow(PIId) {

		var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;

		callWindowFullView("订单详情",url);
	}
	
	function reload() {
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		
		return true;
	}
	
	
	//普通，成品
	function doSearch3(type) {	
		
		//$("#keyword1").val("");
		//$("#keyword2").val("");

		$('#partsType').val(type);
		
		ajaxSearch("false");

	}
	
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<input type="hidden" id="searchFlag" value="U" />
			<input type="hidden" id="peijianFlag" value="" />
			<input type="hidden" id="partsType"   value="C" />
			<table>
				<tr>
					<td width="50px"></td> 
					<td class="label" style="width:100px">关键字1：</td>
					<td class="condition">
						<input type="text" id="keyword1" name="keyword1" class="middle"/></td>
					<td class="label" style="width:100px">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle"/></td>
					<td  width="150px">
						<label><input type="checkbox"  name="numCheck" id="numCheck" value="1"  />全状态</label>
						<button type="button" id="retrieve2" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();"/>查询</td>
					
					<td width="10px"></td> 
				</tr>
				<tr>
					<td width="50px"></td> 
					<td class="label"> 快捷查询：</td>
					<td class="">
						<a id="defutBtnmP" class="DTTT_button box2" onclick="doSearch3('C');">常规订单</a>
						<a id="defutBtnmC" class="DTTT_button box2" onclick="doSearch3('P');">配件单</a>
					</td>
					<td class="label"></td> 
					<td class="">&nbsp;
					</td>
					<td></td>
					<td width=""></td> 
				</tr>
				<tr>
					<td width="50px"></td> 
					<td class="label"> 快捷查询：</td>
					<td colspan="3">
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('C');"  id="defutBtnC">当前任务</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('L');"  id="defutBtnL">中长期计划</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('N');"  id="defutBtnN">未领料</a>&nbsp;
						<a  class="DTTT_button box" onclick="doSearchCustomer();"  		 id="defutBtnU">未安排</a>
						<a  class="DTTT_button box" onclick="doSearchCustomer2();"  	 id="defutBtnF">已领料</a>
					</td>
					
					<td></td>
					<td width=""></td> 
				</tr>
			
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">

		<div id="TSupplier_wrapper" class="dataTables_wrapper">
			<!-- div id="" style="height:40px;float: left">
				<a  class="DTTT_button box" onclick="doSearchCurrentTask('C');"  id="defutBtnC">当前任务</a>
				<a  class="DTTT_button box" onclick="doSearchCurrentTask('L');"  id="defutBtnL">中长期计划</a>
				<a  class="DTTT_button box" onclick="doSearchCurrentTask('N');"  id="defutBtnN">未领料</a>&nbsp;&nbsp;
				<a  class="DTTT_button box" onclick="doSearchCustomer();"  		 id="defutBtnU">未安排</a>
				<a  class="DTTT_button box" onclick="doSearchCustomer2();"  	 id="defutBtnF">已领料</a>
			</div -->
			<div id="createCurrent" style="height:40px;float: right">
				<a  class="DTTT_button " onclick="doCreateY('C','31');" id="">添加到当前任务</a>	
				<a  class="DTTT_button " onclick="doCreateY('L','32');" id="">添加到中长期</a>
				<a  class="DTTT_button " onclick="doCreateY('N','33');" id="">添加到未领料</a>
			</div>
			<table id="TMaterial" class="display" >
				<thead>						
					<tr>
						<th style="width: 10px;">No</th>
						<th style="width: 40px;">型号</th>
						<th style="width: 70px;">耀升编号</th>
						<th style="width: 150px;">产品编号</th>
						<th>产品名称</th>
						<th style="width: 40px;">客户</th>
						<th style="width: 60px;">订单数量</th>
						<th style="width: 50px;">订单交期</th>
						<th style="width: 50px;">最晚交期</th>
						<th style="width: 40px;">业务组</th>
						<th style="width: 60px;">订单状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>
