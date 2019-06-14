<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>

<title>生产计划</title>
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

							//setOptons();
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
					{"data": "orderQty", "defaultContent" : '0', "className" : 'td-right'},//5
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-left'},//6
					//{"data": "MaxDeliveryDate", "defaultContent" : '', "className" : 'td-left'},//7
					{"data": "produceLine", "className" : 'td-center'},//7
					{"data": "sortNo", "className" : 'td-center'},//8
					{"data": "sortNo", "className" : 'td-center'},//9
					
				],
				"columnDefs":[
		    		
		    		{"targets":0,"render":function(data, type, row){

						var lastCheceked = '<span id=""  style="display:none" class="orderHide"><input type="checkbox"  name="orderHide" id="orderHide" value="" /></span';

		    			return data + lastCheceked;
		    		}},
		    		{"targets":1,"render":function(data, type, row){
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
		    		{"targets":2,"render":function(data, type, row){

						var lastHide = '<input type="hidden"  name="lastHide" id="lastHide" value="' + row["hideFlag"] + '" /></span';

		    			//var rtn= "<a href=\"###\" onClick=\"doShow('"+ row["PIId"] + "')\">"+data+"</a>";
		    			
		    			return data + lastHide;
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			name = jQuery.fixedWidth(name,38);//true:两边截取,左边从汉字开始
		    			
		    			var lastCheceked = '<input type="hidden"  name="lastCheceked" id="lastCheceked" value="' + row["lastCheceked"] + '" />';
		    			
		    			return name + lastCheceked;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			
		    			return floatToNumber(data);
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			var rtn = "";
		    			var rowIndex = row["rownum"] - 1;
		    			var produceLine = row['produceLine'];
		    			var YSId = row['YSId'];
		    			
		    			var txt = ' <input type="text"   '+
		    				' id="lines'+rowIndex+'.produceLine" '+
		    				' onfocus= removeErrorClass(\''+rowIndex+'\');return false;"   '+
		    				' onblur ="setProduceLine(this,\''+YSId+'\',\''+rowIndex+'\')"   '+
		    				' class="short attributeList1" style="width:50px;"/>'
		    			
		    			if(produceLine == '')
			    			rtn= txt;
			    		else
			    			rtn = produceLine;
		    			
		    			return rtn;
		    		}},
		    		{"targets":9,"render":function(data, type, row){
		    			var imgName = 'arrow_down';
		    			var rtn = "",down='D',up='U';
		    			var produceLine = row['produceLine'];
		    			var rowIndex = row["rownum"];
		    			
		    				rtn += '<input onClick="doUpdateSortNo(\''+ row["produceLine"] + '\',\'' + row["sortNo"] + '\',\'' + down + '\')" type="image" title="向下移" style="border: 0px;" src="${ctx}/images/'+imgName+'.png" />';
		    			//if(rowIndex >1){
			    			rtn += '<input onClick="doUpdateSortNo(\''+ row["produceLine"] + '\',\'' + row["sortNo"] + '\',\'' + up   + '\')" type="image" title="向上移" style="border: 0px;" src="${ctx}/images/arrow_top.png" />';
		    			//}
		    			
		    			if(produceLine =='' || produceLine == null){
		    				rtn = '';	
		    			}
		    			return rtn;
                    }},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : []
					}
	         	],
	         		         	
			});
	}
	
		
	function setOptons(){
		
		$(".option").html(options);	
	}
	
	function initEvent(){

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

		foucsInit();
		
		$('#createCurrent').show();
		
		var i = 0;	
		<c:forEach var="list" items="${produceLineOption}">
			i++;
			options += '<option value="${list.value}">${list.value}</option>';
		</c:forEach>
		
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
	
	//生产线选择
	function setProduceLine($obj,YSId,rowIndex) {

		var produceLine = $obj.value;

		if($.trim(produceLine) == '' ){
			return;
		}
		if(produceLine.length<3){
			$().toastmessage('showWarningToast', "请输入正确的生产线编码");	
			$('#lines'+rowIndex+'\\.produceLine').addClass("error");
			return;
		}
		
		produceLine = encodeURI(encodeURI(produceLine));
		var actionUrl = "${ctx}/business/produce?methodtype=setProduceLineById"
			+"&YSId="+YSId
			+"&produceLine="+produceLine;

		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : actionUrl,
			data : JSON.stringify($('#orderForm').serializeArray()),// 要提交的表单
			success : function(data) {
			
				var rtnValue = data['message'];
				//alert(rtnValue)
				$().toastmessage('showWarningToast', "生产线设置成功!");		
				$('#lines'+rowIndex+'\\.produceLine').addClass("finished");
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {				
				//alert(textStatus);
			}
		});			
			
	};
		
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
	
	//生产线
	function doSelectUserId(lineCode){

		var code = lineCode.substring(0,1)
	
		$('#produceLine').val(lineCode);//已领料
		
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
	
	
	function autocomplete(){
		
		$(".attributeList1").autocomplete({
			minLength : 1,
			autoFocus : false,
			source : function(request, response) {
				//alert(888);
				$
				.ajax({
					type : "POST",
					url : "${ctx}/business/produce?methodtype=getProduceLineList",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						//alert(777);
						response($
							.map(
								data.data,
								function(item) {

									return {
										label : item.parentId +" | "+item.subId,
										value : item.codeName,
										id 	  : item.codeName,
									}
								}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
						//alert(XMLHttpRequest.status);
						//alert(XMLHttpRequest.readyState);
						//alert(textStatus);
						//alert(errorThrown);
						alert("系统异常，请再试或和系统管理员联系。");
					}
				});
			},
			
		});
	}
	
	
	function doUpdateSortNo(produceLine,sortNo,sortFlag) {
		
		produceLine = encodeURI(encodeURI(produceLine));
		//if(confirm("删除后不能恢复，确定要删除数据吗？")) {
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : '',
				url : "${ctx}/business/produce?methodtype=setProducePlanSortNo"+"&produceLine="+produceLine+"&sortFlag="+sortFlag+"&sortNo="+sortNo,
				success : function(data) {
					var message = data['message'];
					if(message == 'S'){
						$().toastmessage('showWarningToast', "顺序已调整。");	
						reload();
					}
					if(message == 'N'){
						$().toastmessage('showWarningToast', "不能调整该订单的生产顺序。");	
					}
					if(message == 'E'){
						$().toastmessage('showWarningToast', "生产顺序调整失败，请联系管理员。");	
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$().toastmessage('showWarningToast', "生产顺序调整失败，请联系管理员。");	
	             }
			});
		//}
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
						<!-- 
						<label><input type="checkbox"  name="numCheck" id="numCheck" value="1"  />全状态</label>
						 -->
						<button type="button" id="retrieve2" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();"/>查询</td>
					
					<td width="10px"></td> 
				</tr>
				<!-- 
				<tr>
					<td width=""></td> 
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
				 -->
				<tr>
					<td width=""></td> 
					<td class="label"> 快捷查询：</td>
					<td colspan="3">
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('A');"   id="defutBtnA">ALL</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask2('C');"  id="defutBtnC">当前任务</a>
						<!-- a  class="DTTT_button box" onclick="doSearchCurrentTask('L');"  id="defutBtnL">中长期计划</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('N');"  id="defutBtnN">未领料</a>&nbsp;-->
						<a  class="DTTT_button box" onclick="doSearchCustomer();"  		 id="defutBtnU">未安排</a>
						<!-- a  class="DTTT_button box" onclick="doSearchCustomer2();"  	 id="defutBtnF">已领料</a -->
					</td>
					
					<td></td>
					<td width=""></td> 
				</tr>
				<!-- 
				<tr>
					<td width=""></td>
					<td width="" class="label">生产线：</td>
					<td colspan="5">
						<span id="produceLine">
							<c:forEach var='list' items='${produceLine}' varStatus='status'>
								<a id="defutBtnu${list.dicId }" style="height: 15px;margin-top: 5px;" 
									class="DTTT_button box3" onclick="doSelectUserId('${list.codeId }');">
									<span>${list.codeName }</span></a>
							</c:forEach>
						</span>			
					</td>
				</tr>
			 -->
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">

		<div id="TSupplier_wrapper" class="dataTables_wrapper">
			<!-- 
			<div id="createCurrent" style="height:40px;float: right">
				<a  class="DTTT_button " onclick="doCreateY('C','31');" id="">添加到当前任务</a>	
				<a  class="DTTT_button " onclick="doCreateY('L','32');" id="">添加到中长期</a>
				<a  class="DTTT_button " onclick="doCreateY('N','33');" id="">添加到未领料</a>
			</div>
			 -->
			<table id="TMaterial" class="display" >
				<thead>						
					<tr>
						<th style="width: 40px;">型号</th>
						<th style="width: 70px;">耀升编号</th>
						<th style="width: 150px;">产品编号</th>
						<th>产品名称</th>
						<th style="width: 40px;">客户</th>
						<th style="width: 60px;">订单数量</th>
						<th style="width: 50px;">订单交期</th>
						<th style="width: 60px;">生产线</th>
						<th style="width: 40px;">生产<br/>序号</th>
						<th style="width: 40px;">调整</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>
