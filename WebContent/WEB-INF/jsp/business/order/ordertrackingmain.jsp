<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>

<title>订单跟踪</title>
<script type="text/javascript">

    var hideCnt = 0;
	var options = "";

	function ajaxSearch(sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var searchFlag  = $('#searchFlag').val();
		var partsType   = $('#partsType').val();
		var produceLine = $('#produceLine').val();
		
		var url = "${ctx}/business/produce?methodtype=producePlanMainSearch&sessionFlag="+sessionFlag
				+"&orderType=010"+"&searchFlag="+searchFlag
				+"&partsType="+partsType
				+"&produceLine="+encodeURI(encodeURI(produceLine));

		var sortCode = $('#sortCode').val();
		
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
	         	//"aaSorting": [[ sortCode, "ASC" ]],
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
														
							autocomplete();//调用自动填充功能
						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": "machineModel", "className" : 'td-center'},// 0
					{"data": "YSId", "defaultContent" : ''}, //1
					{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},//2
					{"data": "materialName", "defaultContent" : ''},//3
					{"data": "shortName", "className" : 'td-center'},//4
					{"data": null, "className" : 'td-center'},//5:备齐时间
					{"data": "orderQty", "defaultContent" : '0', "className" : 'td-right'},//6
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-left'},//7
					
				],
				"columnDefs":[
		    		
		    		{"targets":0,"render":function(data, type, row){

						var lastCheceked = '<span id=""  style="display:none" class="orderHide"><input type="checkbox"  name="orderHide" id="orderHide" value="" /></span';

		    			return data + lastCheceked;
		    		}},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShow('"+ row["YSId"] + "','"+ row["materialId"] + "')\">"+row["YSId"]+"</a>";
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){

						var lastHide = '<input type="hidden"  name="lastHide" id="lastHide" value="' + row["hideFlag"] + '" /></span';

		    			//var rtn= "<a href=\"###\" onClick=\"doShow2('"+ row["PIId"] + "')\">"+data+"</a>";
		    			
		    			return data + lastHide;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			name = jQuery.fixedWidth(name,48);//true:两边截取,左边从汉字开始
		    			
		    			var lastCheceked = '<input type="hidden"  name="lastCheceked" id="lastCheceked" value="' + row["lastCheceked"] + '" />';
		    			
		    			return name + lastCheceked;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			var ready = row['readyDate'];
		    			var flag = row['finishFlag'];
		    			if(ready == ''){
		    				if(flag == 'B'){

			    				return '已入库';	
		    				}else{
			    				return '未知';			    					
		    				}
		    			}else{
		    				return ready;
		    				
		    			}
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			
		    			return floatToNumber(data);
		    		}},
		    		{
		    			"orderable":false,"targets":[]
		    		},
		    		{
						"visible" : false,
						"targets" : []
					}
	         	],
	         		         	
			});
	}
	
	function doShow(YSId,materialId) {

		var url = '${ctx}/business/orderTrack?methodtype=orderTrackingShow&YSId=' 
				+ YSId+'&materialId='+materialId;

		callWindowFullView("订单跟踪",url);
	}

	

	function initEvent(){

		ajaxSearch("false");
	
		$('#TMaterial').DataTable().on('click', 'tr td:nth-child(1)', function() {

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

		foucsInit();
		
		initEvent();
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		buttonSelectedEvent3();//按钮选择式样
		
		$('#defutBtnC').removeClass("start").addClass("end");
		$('#defutBtnmP').removeClass("start").addClass("end");
		
		
	})	

	function removeErrorClass(rowIndex){
		$('#lines'+rowIndex+'\\.produceLine').removeClass("error");
	}
	
	
		
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

			
		var searchFlag = $('#searchFlag').val();		
	    
		ajaxSearch('false');

		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    
		$('#defutBtn'+searchFlag).removeClass("start").addClass("end");
		
		
	}
			

	
	//料已备齐
	function doSearchCustomer(){

		$('#searchFlag').val('B');
		
		ajaxSearch('false');
	}

	

			
	//ALL
	function doSearchCurrentTask(taskType){
		//$('#keyword1').val('');
		//$('#keyword2').val('');
		$('#searchFlag').val(taskType);//Current:当前任务
		
		ajaxSearch('false');
	}
	
	//查看当前任务
	function doSearchCurrentTask2(taskType){
		//$('#keyword1').val('');
		//$('#keyword2').val('');
		$('#searchFlag').val(taskType);//Current:当前任务
		$('#sortCode').val(7);//生产线排序		
		
		ajaxSearch('false');
	}

	
	function reload() {
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		
		return true;
	}
	
	
	
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<input type="hidden" id="searchFlag" value="C" />
			<input type="hidden" id="peijianFlag" value="" />
			<input type="hidden" id="partsType"   value="C" />
			<input type="hidden" id="produceLine"   value="" />
			<input type="hidden" id="sortCode"   value="6" />
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
						
						<button type="button" id="retrieve2" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();"/>查询</td>
					
					<td width="10px"></td> 
				</tr>
				
				<tr>
					<td width=""></td> 
					<td class="label"> 快捷查询：</td>
					<td colspan="3">
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('A');"   id="defutBtnA">ALL</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask2('C');"  id="defutBtnC">当前跟踪</a>
						<a  class="DTTT_button box" onclick="doSearchCustomer();"  		 id="defutBtnU">料已备齐</a>
					</td>
					
					<td></td>
					<td width=""></td> 
				</tr>
				
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">

		<div id="" class="dataTables_wrapper">
			
			<table id="TMaterial" class="display" >
				<thead>						
					<tr>
						<th style="width: 40px;">型号</th>
						<th style="width: 70px;">耀升编号</th>
						<th style="width: 150px;">产品编号</th>
						<th>产品名称</th>
						<th style="width: 40px;">客户</th>
						<th style="width: 60px;">装配物料<br/>备齐时间</th>
						<th style="width: 60px;">订单数量</th>
						<th style="width: 50px;">订单交期</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>
