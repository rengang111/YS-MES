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
		var team		= $('#team').val();
		var mateType	= $('#mateType').val();
		
		var url = "${ctx}/business/orderTrack?methodtype=orderTrackingSearch&sessionFlag="+sessionFlag
				+"&orderType=010"+"&searchFlag="+searchFlag
				+"&partsType="+partsType
				+"&team="+team
				+"&mateType="+mateType;

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

							//setOptons();
							
							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);
						
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
					{"data": "team", "defaultContent" : '', "className" : 'td-center'},//8
					{"data": null, "defaultContent" : '', "className" : 'td-center'},//9
					
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
		    			name = jQuery.fixedWidth(name,42);//true:两边截取,左边从汉字开始
		    			
		    			var lastCheceked = '<input type="hidden"  name="lastCheceked" id="lastCheceked" value="' + row["lastCheceked"] + '" />';
		    			
		    			return name + lastCheceked;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			var readyDate = row['readyDate'];
		    			var flag = row['finishFlag'];
		    			var rtnValue = ''
		    			if(readyDate == ''){
		    				if(flag == 'B'){

		    					rtnValue = '已入库';	
		    				}else{
		    					rtnValue = '未知';			    					
		    				}
		    			}else{
		    				var today = shortToday();
		    				if(readyDate < today)
		    					rtnValue = '<span class="error">'+readyDate+'</span>';
		    				else
		    					rtnValue = 	readyDate;
		    				
		    			}
		    			
		    			return rtnValue;
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			return floatToNumber(data);
		    		}},
		    		{"targets":9,"render":function(data, type, row){
		    			var rtn = "";
		    			var rowIndex = row["rownum"] - 1;
		    			var importantFlag = row['importantFlag'];
		    			var YSId = row['YSId'];
		    			
		    			var selected = '';
		    			if(importantFlag == '1'){
		    				selected = 'selected';
		    			}
		    			var txt = '<select  name="lines['+rowIndex+'].important"  '+
		    				' id="lines'+rowIndex+'.important" '+
		    				' onchange="setImportant(this.value,\''+YSId+'\',\''+rowIndex+'\')"   '+
		    				' class="short option" style="text-align: center;"> '+
		    				' <option value=\'0\'>一般</option>  '+
						    ' <option '+selected+' value=\'1\'>重要</option> '+
		    				' </select> ';
		    			
			    		rtn = txt;
		    				
		    			return rtn;
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
	
	//重要性选择
	function setImportant(importantFlag,YSId,rowIndex) {
		
		var actionUrl = "${ctx}/business/orderTrack?methodtype=setOrderForProduce"
			+"&YSId="+YSId+"&importantFlag="+importantFlag;

		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : actionUrl,
			data : JSON.stringify($('#orderForm').serializeArray()),// 要提交的表单
			success : function(data) {
			
				var rtnValue = data['message'];
				$().toastmessage('showWarningToast', "数据保存成功!");
				reload();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {				
				//alert(textStatus);
			}
		});			
			
	};
	
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
		
		var i = 0;	
		<c:forEach var="list" items="${important}">
			i++;
			options += '<option value="${list.key}">${list.value}</option>';
		</c:forEach>
		
		initEvent();
		
		//$('#mateFlag').hide();//
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		buttonSelectedEvent3();//按钮选择式样
		
		$('#defutBtnC').removeClass("start").addClass("end");
		$('#defutBtnmA').removeClass("start").addClass("end");
		$('#defutBtnu999').removeClass("start").addClass("end");
		
		
	})	

		
	function setOptons(){
		
		$(".option").html(options);	
	}
	
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
	function doSearchCustomer(type){

		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    
		$('#searchFlag').val(type);
		$('#mateType').val('A');//包含所有异常数据
		
		ajaxSearch('false');
	}
			
	//ALL
	function doSearchCurrentTask(taskType){
		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    
		$('#searchFlag').val(taskType);//Current:当前任务
		$('#mateType').val('A');	
		
		ajaxSearch('false');
	}
	
	//查看当前任务
	function doSearchCurrentTask2(taskType){
		
		var collection = $(".box2");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
		
		$('#searchFlag').val(taskType);//Current:当前任务
		$('#mateType').val('A');//包含所有异常数据
		$('#sortCode').val(7);//生产线排序	
		
		
		ajaxSearch('false');
	}


	//异常数据：自制件，五金，电子
	function doSearchMateType(type){

		var collection = $(".box3");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
		$('#defutBtnu999').removeClass("start").addClass("end");
	    
	    
		$('#team').val('999');		
		$('#searchFlag').val('C');//Current:当前任务
		$('#mateType').val(type);		
		
		ajaxSearch('false');
	}

	

	//业务组
	function doSelectUserId(teamId){

		$('#team').val(teamId);
		
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
			<input type="hidden" id="team"   value="999" />
			<input type="hidden" id="mateType"   value="A" />
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
					<td>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('A');"   id="defutBtnA">ALL</a>
						<a  class="DTTT_button box" onclick="doSearchCustomer('B');"  		  id="defutBtnU">料已备齐</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask2('C');"  id="defutBtnC">当前跟踪</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask2('P');"  id="defutBtnP">卡点</a>
						
					</td>
					
					<td class="label">异常数据：</td>
					<td width="">
							<a id="defutBtnmZ"  class="DTTT_button box" onclick="doSearchMateType('Z');">
								自制件</a>	
							<a id="defutBtnmW"  class="DTTT_button box" onclick="doSearchMateType('W');">
								五金件</a>	
							<a id="defutBtnmD"  class="DTTT_button box" onclick="doSearchMateType('D');">
								电子件</a>
							<!-- a id="defutBtnmA"  class="DTTT_button box2" onclick="doSearchMateType2('A');">
								ALL</a -->	
						</td> 
					<td></td> 
					<td></td>
				</tr>
				
			</table>
			<table>
				<tr>
					<td width="50px"></td>
					<td width="100px" class="label">业务组：</td>
					<td colspan="">
						<span id="userFlag2">
							<c:forEach var='list' items='${yewuzu}' varStatus='status'>
								<a id="defutBtnu${list.dicId }" style="height: 15px;margin-top: 5px;" 
									class="DTTT_button box3" onclick="doSelectUserId('${list.dicId }');">
									<span>${list.dicName }</span></a>
							</c:forEach>
						</span>			
					</td> 
					<td width="100px"></td>
					<td class="label">重要性：</td>
					<td colspan="">
						<a id="defutBtnmIY"  class="DTTT_button box" onclick="doSearchCurrentTask2('IY');">
							重要</a>	</td>
					<td colspan="">
						<a id="defutBtnmIN"  class="DTTT_button box" onclick="doSearchCurrentTask2('IN');">
							一般</a>	</td>
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
						<th style="width: 120px;">产品编号</th>
						<th>产品名称</th>
						<th style="width: 40px;">客户</th>
						<th style="width: 60px;">装配物料<br/>备齐时间</th>
						<th style="width: 60px;">订单数量</th>
						<th style="width: 50px;">订单交期</th>
						<th style="width: 50px;">业务组</th>
						<th style="width: 50px;">重要性</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>
