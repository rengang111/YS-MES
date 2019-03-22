<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>

<title>生产任务 -- 一览页面</title>
<script type="text/javascript">

    var hideCnt = 0;

	function ajaxSearch(orderType,sessionFlag,searchFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var searchFlag = $('#searchFlag').val();
		
		var url = "${ctx}/business/produce?methodtype=search&sessionFlag="+sessionFlag
				+"&orderType="+orderType+"&searchFlag="+searchFlag;

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
							var collection = $(".box2");
						    $.each(collection, function () {
						    	$(this).removeClass("end");
						    });
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
					{"data": "quantity", "defaultContent" : '0', "className" : 'td-right'},//5
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-left'},//6
					{"data": "MaxDeliveryDate", "defaultContent" : '', "className" : 'td-left'},//7
					{"data": "team", "className" : 'td-center'},//8
					{"data": "statusName", "className" : 'td-center'},//9
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){

		    			var mergeYsid = row["lastCheceked"];
		    			var stockinQty = currencyToFloat(row['stockinQty']);
		    			var contractQty = currencyToFloat(row['contractQty']);
		    			var peijianFlag = $('#peijianFlag').val();
		    			var text = row["rownum"];
		    		
		    			if(searchFlag == 'Z' && peijianFlag == ''){//展开才有复选框
		    				var checkedFlag = 'checked';
			    			if(mergeYsid == '0'){
			    				checkedFlag = ''
			    			}			    				
			    			text = "<input "+checkedFlag+" type='checkbox' class='checkbox' name='numCheck' id='numCheck' value='" + row["rownum"] + "' />";	
		    			}

		    			return text;	
		    			
                    }},
		    		{"targets":1,"render":function(data, type, row){

						var lastCheceked = '<span id=""  style="display:none" class="orderHide"><input type="checkbox"  name="orderHide" id="orderHide" value="' + row["lastCheceked"] + '" /></span';

		    			return data + lastCheceked;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShow('"+ row["PIId"] + "')\">"+row["YSId"]+"</a>";
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
		    		{"targets":5,"render":function(data, type, row){
		    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			if(data == '' || data == null )
		    				data = '***';
		    			return data;
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : []
					},
					{
						//"order": [[ 2, 'asc' ]]
					}
	         	//"aaSorting": [[ sortc, "DESC" ]]
	         	],
	         		         	
			});
	}
	
		
	function initEvent(){

		$('#defutBtnH').hide();

		$('#peijianFlag').val('');//配件订单不用合并

		ajaxSearch("010","true");
	
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

		$('.orderHide').hide();
		
		initEvent();
		
		buttonSelectedEvent();//按钮选择式样
		buttonSelectedEvent2();//按钮选择式样
		
		$('#defutBtn').removeClass("start").addClass("end");
		$('#defutBtnZ').removeClass("start").addClass("end");
	})	
	
	function doSearch() {	

		$('#merge').show();//显示展开，合并按钮
		$('#peijianFlag').val('');//配件订单不用合并
		$('#searchFlag').val('Z');
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	    
		ajaxSearch('','false');

		
		
	}
	
	//合并订单
	function doCreate() {
		
		$('#searchFlag').val('S');
		
		var str = '';
		var supplierId = '';
		var supplierId_next = '';
	    var flag = true;
	    var rtnValue = true;

		var checkedList = "";
		var firstFlag = true;
		
		$('#TMaterial tbody tr').each (function (){
			
			var $newsts = $(this).find("td").eq(0).find("input");
			var hidsts  = $(this).find("td").eq(4).find("input").val();
			var ysid    = $(this).find("td").eq(2).text();
			
			var newCheced = "0";
			var oldCheced = "0";
			var checkFlag = false;
			
			if( $newsts.prop('checked')){//本次被选中
				newCheced = "1" ;
				checkFlag = true;
			}
			
			if(hidsts == '1'){//上次被选中
				oldCheced = "1" ;
				checkFlag = true;
			}
			
			if( checkFlag == true ){
				if(firstFlag){
					checkedList += ysid + ',' + newCheced +','+ oldCheced;
					firstFlag = false;
				}else{
					checkedList += ';';
					checkedList += ysid + ',' + newCheced +','+ oldCheced;
				}
			}
					
		});

		//alert("checkedList:"+checkedList);
		
		var actionUrl = "${ctx}/business/produce?methodtype=productTaskMerge&checkedList="+checkedList;

		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : actionUrl,
			data : JSON.stringify($('#orderForm').serializeArray()),// 要提交的表单
			success : function(data) {
				//alert(d)
				$('#searchFlag').val('S');//合并收起
				ajaxSearch('010','false');	//刷新页面
				//$().toastmessage('showWarningToast', "保存成功!");		
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {				
				//alert(textStatus);
			}
		});
	}
	
	//隐藏订单
	function doCreateY() {
		
		if(hideCnt <= 0){
			$().toastmessage('showWarningToast', "请选择要隐藏的数据!");		
			return;
		}
		if(confirm('确定要隐藏 底色 被改变 的订单吗？')){
			
		}else{
			return;
		}
		$('#searchFlag').val('Y');//合并收起
		//按钮显示规则
		$('#defutBtnH').hide();
		$('#merge').hide();
				
		var str = '';
		var supplierId = '';
		var supplierId_next = '';
	    var flag = true;
	    var rtnValue = true;

		var checkedList = "";
		var firstFlag = true;
		
		$('#TMaterial tbody tr').each (function (){
			
			var $newsts = $(this).find("td").eq(1).find("input");
			var hidsts  = $(this).find("td").eq(3).find("input").val();
			var ysid    = $(this).find("td").eq(2).text();
			
			var newCheced = "F";
			var oldCheced = "F";
			var checkFlag = false;
			
			if( $newsts.prop('checked')){//本次被选中
				newCheced = "T" ;
				checkFlag = true;
			}
			
			if(hidsts == 'T'){//上次被选中
				oldCheced = "T" ;
				checkFlag = true;
			}
			
			if( checkFlag == true ){
				if(firstFlag){
					checkedList += ysid + ',' + newCheced +','+ oldCheced;
					firstFlag = false;
				}else{
					checkedList += ';';
					checkedList += ysid + ',' + newCheced +','+ oldCheced;
				}
			}
					
		});

		alert("checkedList:"+checkedList);
		
		var actionUrl = "${ctx}/business/produce?methodtype=productTaskOrderHide&checkedList="+checkedList;

		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : actionUrl,
			data : JSON.stringify($('#orderForm').serializeArray()),// 要提交的表单
			success : function(data) {
				//alert(d)				
				ajaxSearch('010','false',"Y");	//刷新页面
				
				//$().toastmessage('showWarningToast', "保存成功!");		
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {				
				//alert(textStatus);
			}
		});
	}
	
	//恢复订单
	function doCreateH() {
		
		if(hideCnt <= 0){
			$().toastmessage('showWarningToast', "请选择要恢复的数据!");		
			return;
		}
		if(confirm('确定要恢复 底色 被改变 的订单吗？')){
			
		}else{
			return;
		}
		$('#searchFlag').val('Y');
		//按钮显示规则
		$('#defutBtnY').hide();
		$('#defutBtnH').show();
		$('#merge').hide();
				
		var str = '';
		var supplierId = '';
		var supplierId_next = '';
	    var flag = true;
	    var rtnValue = true;

		var checkedList = "";
		var firstFlag = true;
		
		$('#TMaterial tbody tr').each (function (){
			
			var $newsts = $(this).find("td").eq(1).find("input");
			var hidsts  = $(this).find("td").eq(3).find("input").val();
			var ysid    = $(this).find("td").eq(2).text();
			
			var newCheced = "F";
			var oldCheced = "F";
			var checkFlag = false;
			
			if( $newsts.prop('checked')){//本次被选中
				newCheced = "T" ;
				checkFlag = true;
			}
			
			if(hidsts == 'T'){//上次被选中
				oldCheced = "T" ;
				checkFlag = true;
			}
			
			if( checkFlag == true ){
				if(firstFlag){
					checkedList += ysid + ',' + newCheced +','+ oldCheced;
					firstFlag = false;
				}else{
					checkedList += ';';
					checkedList += ysid + ',' + newCheced +','+ oldCheced;
				}
			}
					
		});

		alert("checkedList:"+checkedList);
		
		var actionUrl = "${ctx}/business/produce?methodtype=productTaskOrderShow&checkedList="+checkedList;

		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : actionUrl,
			data : JSON.stringify($('#orderForm').serializeArray()),// 要提交的表单
			success : function(data) {
				//alert(d)				
				ajaxSearch('030','false');	//刷新页面
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {				
				//alert(textStatus);
			}
		});
	}
	
	//常规订单
	function doSearchCustomer(type,searchFlag){

		$('#merge').show();//显示展开，合并按钮
		$('#searchFlag').val('Z');//常规订单
		$('#peijianFlag').val('');//配件订单不用合并
		//按钮显示规则
		$('#merge').show();//显示展开.合并按钮
		$('#defutBtnH').hide();
		$('#defutBtnY').show();
		
		ajaxSearch('010','false');
	}

	//配件订单
	function doSearchCustomer2(){

		$('#searchFlag').val('Z');//配件订单
		$('#peijianFlag').val('P');//配件订单不用合并
		
		//按钮显示规则
		$('#merge').hide();//显示展开.合并按钮
		$('#defutBtnH').hide();
		$('#defutBtnY').show();
		
		ajaxSearch('020','false');
		
	}
	
	//查看隐藏订单
	function doSearchViewHide(){

		$('#searchFlag').val('Y');//配件订单
		$('#peijianFlag').val('');//配件订单不用合并
		
		//按钮显示规则
		$('#merge').hide();//不显示展开.合并按钮
		$('#defutBtnH').show();
		$('#defutBtnY').hide();
		
		ajaxSearch('030','false');
	}


	//展开查询
	function doSearchCustomer3(){

		$('#peijianFlag').val('');//配件订单不用合并
		$('#searchFlag').val('Z');
		
		//按钮显示规则
		$('#defutBtnY').show();
		$('#defutBtnH').hide();
		
		ajaxSearch('010','false');
	}

	
	function doShow(PIId) {

		var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;

		callWindowFullView("订单详情",url);
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
			<input type="hidden" id="searchFlag" value="Z" />
			<input type="hidden" id="peijianFlag" value="" />
			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label" style="width:100px">关键字1：</td>
					<td class="condition">
						<input type="text" id="keyword1" name="keyword1" class="middle"/></td>
					<td class="label" style="width:100px">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle"/></td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();"/>查询</td>
					<td style="vertical-align: bottom;width: 150px;">
					</td> 
					<td width="10%"></td> 
				</tr>
			
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">

		<div id="TSupplier_wrapper" class="dataTables_wrapper">
			<div id="" style="height:40px;float: left">
				<a  class="DTTT_button box" onclick="doSearchCustomer();" id="defutBtn"><span>常规订单</span></a>
				<a  class="DTTT_button box" onclick="doSearchCustomer2();"><span>配件订单</span></a>
				<a  class="DTTT_button box" onclick="doSearchViewHide();"><span>查看隐藏</span></a>
			</div>
			<div id="" style="height:40px;float: right">
					<a  class="DTTT_button box2" onclick="doCreateY();" id="defutBtnY">&nbsp;隐藏&nbsp;</a>
					<a  class="DTTT_button box2" onclick="doCreateH();" id="defutBtnH">&nbsp;恢复&nbsp;</a>
				<span id="merge">
					<a  class="DTTT_button box2" onclick="doSearchCustomer3();" id="defutBtnZ">&nbsp;展开&nbsp;</a>
					<a  class="DTTT_button box2" onclick="doCreate();" id="defutBtnS">合并收起</a>
				</span>
			</div>
			<table id="TMaterial" class="display" >
				<thead>						
					<tr>
						<th style="width: 10px;">No</th>
						<th style="width: 50px;">型号</th>
						<th style="width: 70px;">耀升编号</th>
						<th style="width: 150px;">产品编号</th>
						<th>产品名称</th>
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
