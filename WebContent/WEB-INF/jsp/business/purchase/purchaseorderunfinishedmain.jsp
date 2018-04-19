<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>采购合同--未订合同一览</title>
<script type="text/javascript">

	function searchAjax(where,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		
		var actionUrl = "${ctx}/business/contract?methodtype=unfinishedSearch";
		actionUrl = actionUrl +	"&sessionFlag="+sessionFlag;
		actionUrl = actionUrl +	where;
		//actionUrl = actionUrl+ 	"&status1="+status1;
		
		var t = $('#TMaterial').DataTable({
			"paging": true,
			 "iDisplayLength" : 50,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"pagingType" : "full_numbers",
			"retrieve" : true,
			"sAjaxSource" : actionUrl,
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
						var key1 = data["keyword1"]
						var key2 = data["keyword2"]
						$("#keyword1").val(key1);
						$("#keyword2").val(key2);						
					
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns": [
				{"data": null,"className" : 'td-center'},
				{"data": "YSId", "defaultContent" : ''},
				{"data": "materialId","className" : 'td-left'},
				{"data": "materialName", "defaultContent" : ''},
				{"data": "unit", "defaultContent" : '',"className" : 'td-center'},
				{"data": "manufactureQuantity", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "purchaseQuantity", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "supplierId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "price", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "totalPrice", "defaultContent" : '',"className" : 'td-right'},
			//	{"data": null, "defaultContent" : '',"className" : 'td-right'},							
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":1,"render":function(data, type, row){
	    			rtn= "<a href=\"###\" onClick=\"doShowYS('" + row["YSId"] + "','" + row["materialId"] + "')\">"+row["YSId"]+"</a>";						
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = row["materialName"];				    			
	    			if(name != null) name = jQuery.fixedWidth(name,36);
	    			return name;
	    		}},
	    		{
					"visible" : false,
					"targets" : []
				}
         	] 
		});
		
		t.on('click', 'tr', function() {

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}	

	$(document).ready(function() {

		var where = "&supplierId2=0574YZ00&materialId2=G&status=030&purchaseType=010";
		searchAjax(where,"true");
		
		buttonSelectedEvent();//按钮选择式样

		$('#defutBtn').removeClass("start").addClass("end");
		
	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		searchAjax("","false");
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	}

	//显示全部
	function doSearchCustomer(){
		searchAjax("","false");
	}
	
	//自制品未到货
	function doSearchCustomer2(){
		$("#keyword1").val('');
		$("#keyword2").val('');
		var where = "&supplierId=0574YZ00&status=030";
		searchAjax(where,"false");
	}
	//订购件未到货
	function doSearchCustomer3(){
		$("#keyword1").val('');
		$("#keyword2").val('');
		var where = "&supplierId2=0574YZ00&materialId2=G&status=030&purchaseType=010";
		searchAjax(where,"false");
	}
	//包装品未到货
	function doSearchCustomer4(){
		$("#keyword1").val('');
		$("#keyword2").val('');
		var where = "&materialId=G&status=030";
		searchAjax(where,"false");
	}
	function doShowYS(YSId,materialId) {

		var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanAddInit&YSId=' 
				+ YSId+'&materialId='+materialId;
		
		layer.open({
			offset :[30,''],
			type : 2,
			title : false,
			area : [ '1100px', '500px' ], 
			scrollbar : false,
			title : false,
			content : url,
			//只有当点击confirm框的确定时，该层才会关闭
			cancel: function(index){ 
			    layer.close(index)
			  	$('#TMaterial').DataTable().ajax.reload(null,false);
			  	return false; 
			}    
		});	
		//callProductDesignView("采购方案",url);		
	}

	function doShowControct(contractId,quantity,arrivalQty,stockinQty) {

		var deleteFlag = '1';
		var editFlag = '1';
		if(currencyToFloat(arrivalQty) > 0){
			deleteFlag = '0';//已经在执行中,不能删除
		}

		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId+'&deleteFlag=' + deleteFlag;
		location.href = url;
	}
	
</script>
</head>

<body>
<div id="container">
<div id="main">
	<div id="search">

		<form id="condition"  style='padding: 0px; margin: 10px;' >

			<input type="hidden" id="keyBackup" value="${keyBackup }" />
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
							style="width:50px" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
	<!-- 
		<div id="DTTT_container2" style="height:40px;float: left">
			<a  class="DTTT_button box" onclick="doSearchCustomer();"><span>显示全部</span></a>&nbsp;&nbsp;
			<a  class="DTTT_button box" onclick="doSearchCustomer2();"><span>自制品未到货</span></a>
			<a  class="DTTT_button box" onclick="doSearchCustomer3();" id="defutBtn"><span>订购件未到货</span></a>
			<a  class="DTTT_button box" onclick="doSearchCustomer4();"><span>包装品未到货</span></a>
		</div>
	-->
		<table id="TMaterial" class="display" >
			<thead>
				<tr>
					<th style="width: 1px;">No</th>
					<th style="width: 70px;">耀升编号</th>
					<th style="width: 100px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 40px;">单位</th>
					<th style="width: 50px;">需求量</th>
					<th style="width: 50px;">采购量</th>
					<th style="width: 60px;">供应商</th>
					<th style="width: 50px;">单价</th>
					<th style="width: 50px;">合计</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
