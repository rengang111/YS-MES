<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>库存一览页面</title>
<script type="text/javascript">

	function searchAjax(sessionFlag,searchType,confirmFlag,editFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var materialType = $("#materialType").val();
		var materialTypeZZ = $("#materialTypeZZ").val();
		var materialTypeYS1 = $("#materialTypeYS1").val();
		var materialTypeYS2 = $("#materialTypeYS2").val();
		var url = "${ctx}/business/storage?methodtype=beginningInventorySearch&sessionFlag="+sessionFlag;
		url = url + "&materialType="+materialType;
		url = url + "&materialTypeZZ="+materialTypeZZ;
		url = url + "&materialTypeYS1="+materialTypeYS1;
		url = url + "&materialTypeYS2="+materialTypeYS2;
		url = url + "&searchType="+searchType;
		url = url + "&quantityEditFlag="+confirmFlag;
		
		
		
		var scrollHeight = $(document).height() - 195; 
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
				"bAutoWidth":false,
				"scrollY":scrollHeight,
				"scrollCollapse":true,
				"pagingType" : "full_numbers",
				"retrieve" : true,
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
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null,"className" : 'td-center'},//0
					{"data": "materialId"},//1
					{"data": "materialName"},//2
					{"data": "dicName","className" : 'td-center'},//3
					{"data": "reviseQty","className" : 'td-right'},//4
					{"data": "beginningInventory","className" : 'td-right'},//5
					{"data": "beginningPrice","className" : 'td-right'},//6
					{"data": "MAPrice","className" : 'td-right'},//7
					{"data": "planQty","className" : 'td-right'},//	8
					{"data": "contractQty","className" : 'td-right'},//	8
					{"data": "stockinQtiy","className" : 'td-right'},//9
					{"data": "stockoutQty","className" : 'td-right'},//10
					{"data": "quantityOnHand","className" : 'td-right'},//11
					{"data": "availabelToPromise","className" : 'td-right'},//12
					{"data": "waitStockIn","className" : 'td-right'},//13
					{"data": "waitStockOut","className" : 'td-right'},//14
					{"data": null,"className" : 'td-center'},//15
				
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"];
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShow('" + row["recordId"] +"','"+ row["parentId"] + "')\">" + row["materialId"] + "</a>";
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			
		    			var name = row["materialName"];				    			
		    			name = jQuery.fixedWidth(name,36);				    			
		    			return name;
		    		}},
		    		{"targets":16,"render":function(data, type, row){
		    			//实际库存修正
		    			var confirmFlag = row["quantityEditFlag"];
		    			var quantityOnHand = currencyToFloat(row["quantityOnHand"]);
		    			var stockinQtiy = currencyToFloat(row["stockinQtiy"]);
		    			var stockoutQty = currencyToFloat(row["stockoutQty"]);
		    			var totalQty = stockoutQty + quantityOnHand;
		    			if(row["materialId"] == "B01.A001001.000"){

		    				//alert("hand:in:out:"+quantityOnHand+"---"+stockinQtiy+"---"+stockoutQty+"---"+totalQty+"---"+editFlag)
		    			}
		    			var txt = "";	
		    			/*
		    			if((confirmFlag) == "1"){
	    					//待确认
		    				txt +=  "<span style=\"color: red;\">" + "待确认" + "</span>";		    				
		    			}else if((confirmFlag) == "0"){
		    				txt += "已确认";
		    			}else{
		    				txt += "";
		    			}
		    			*/
						if(quantityOnHand < 0 || stockinQtiy != totalQty ){//库存为负数
		    				
		    				if(confirmFlag == "0"){
		    					//已确认,但是还有问题,再次编辑
					    			//txt +=  "<a href=\"###\" onClick=\"setQuantityOnHand('" + row["recordId"] +"')\">" + "再次修改" + "</a>";	    			
		    					txt = "已确认";
		    				}else{
		    					//未修改
				    			//txt +=  "<a href=\"###\" onClick=\"setQuantityOnHand('" + row["recordId"] +"')\">" + "未修改" + "</a>";
			    				txt = "未修改";
			    			}		    				
		    			}else{
		    				if((confirmFlag) == "1"){
		    					//待确认
			    				//txt +=  "<a href=\"###\" onClick=\"confirmQuantityOnHand('" + row["recordId"] +"') \" style=\"color: red;\">" + "待确认" + "</a>";
		    					txt = "待确认";
		    				}else if((confirmFlag) == "0"){	
			    				
				    			txt = "已确认";
			    			}
		    			}	

						return  txt;
						
		    			/*
		    			if(quantityOnHand < 0 || stockinQtiy != totalQty ){//库存为负数
		    				
		    				if(confirmFlag == "0"){
		    					//已确认,但是还有问题
		    					if(editFlag == 'edit'){
		    						//再次编辑
					    			txt +=  "<a href=\"###\" onClick=\"setQuantityOnHand('" + row["recordId"] +"')\">" + "再次修改" + "</a>";	
			    				}else{
				    				txt +=  "<a href=\"###\" onClick=\"confirmQuantityOnHand('" + row["recordId"] +"') \" style=\"color: red;\">" + "还有问题" + "</a>";		    				
			    				}
			    			
			    			}else{
		    					//未修改
				    			txt +=  "<a href=\"###\" onClick=\"setQuantityOnHand('" + row["recordId"] +"')\">" + "未修改" + "</a>";			    				
			    			}		    				
		    			}else{
		    				if((confirmFlag) == "1"){
		    					//待确认
			    				txt +=  "<a href=\"###\" onClick=\"confirmQuantityOnHand('" + row["recordId"] +"') \" style=\"color: red;\">" + "待确认" + "</a>";		    				
			    			}else if((confirmFlag) == "0"){
			    				if(editFlag == 'edit'){

					    			txt +=  "<a href=\"###\" onClick=\"setQuantityOnHand('" + row["recordId"] +"')\">" + "再次修改" + "</a>";	
			    				}else{

				    				txt = "已确认";
			    				}
			    			}
		    			}	
		    			*/		    					    	    			
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    						    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		     			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowPlan('" + row["materialId"] +"')\">" + qty + "</a>";
		    			return rtn;		    			
		    		}},
		    		{"targets":9,"render":function(data, type, row){
		     			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowContract('" + row["materialId"] +"')\">" + qty + "</a>";
		    			return rtn;		    			
		    		}},
		    		{"targets":10,"render":function(data, type, row){
		    			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowStockIn('" + row["materialId"] +"')\">" + qty + "</a>";
		    			return rtn;		    			
		    		}},
		    		{"targets":11,"render":function(data, type, row){
		    			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowStockOut('" + row["materialId"] +"')\">" + qty + "</a>";
		    						    			
		    			return rtn;
		    		}},
		    		{"targets":12,"render":function(data, type, row){//实际库存
		    			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn +=  "<a href=\"###\" onClick=\"setQuantityOnHand('" + row["recordId"] +"')\">" + qty + "</a>";
		    			return rtn;
		    		}},
		    		{"targets":13,"render":function(data, type, row){
		    			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowWaitOut('" + row["materialId"] +"')\">" + qty + "</a>";
		    						    			
		    			return rtn;
		    		}},
		    		{"targets":15,"render":function(data, type, row){
		    			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowPlan('" + row["materialId"] +"')\">" + qty + "</a>";
		    						    			
		    			return rtn;
		    		}},
		    		{"targets":5,"render":function(data, type, row){
		    			//期初值设定
		    			var inventory = currencyToFloat(data);
		    			var val =data;
		    			if(inventory <= 0 )
		    				val=  "设置";		    			
		    			return  "<a href=\"###\" onClick=\"setBeginningInventory('" + row["recordId"] +"')\">" + val + "</a>";	    			
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			//实际库存修正
		    			var quantity = floatToCurrency(data);
		    			var txt = "";
		    			txt +=  "<a href=\"###\" onClick=\"showInventoryHistory('" + row["materialId"] +"')\">" + quantity + "</a>";
		    					    			
		    			return  txt;	    			
		    		}},
		    		{
		    			"visible":false,
		    			"targets":[5,6,7]
		    		}
	        	] 
			}
		);

	}

	$(document).ready(function() {
		
		searchAjax("true","3","");
	
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
	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		searchAjax("false","3","");

	}
	

	function doSearchCustomer(type,confirmFlag,editFlag) {	
		$('#searchType').val(type);
		searchAjax("false",type,confirmFlag,editFlag);

	}
	
	function doShow(recordId,parentId) {

		var url = '${ctx}/business/material?methodtype=detailView&parentId=' + parentId+'&recordId='+recordId;
		callProductDesignView("物料信息",url);
		
	}

	function doShowPlan(materialId) {
		var materialType=$('#materialType').val();
		alert(materialType)
		if(materialType == 'A'){
			var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanForRawByMaterialId&materialId=' + materialId;
			callProductDesignView("采购方案",url);
			
		}else{
			var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanByMaterialId&materialId=' + materialId;
			callProductDesignView("待出数量",url);
		}
		
	}


	function doShowPlan2(materialId) {

		var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanByMaterialId&materialId=' + materialId;
		callProductDesignView("待出数量",url);
		
	}


	function doShowContract(materialId) {

		var url = '${ctx}/business/contract?methodtype=contractListByMaterialId&materialId=' + materialId;
		callProductDesignView("合同明细",url);
		
	}

	function doShowStockIn(materialId) {

		var url = '${ctx}/business/storage?methodtype=getStockInByMaterialId&materialId=' + materialId;
		callProductDesignView("入库明细",url);
		
	}

	function doShowStockOut(materialId) {

		var url = '${ctx}/business/stockout?methodtype=getStockoutByMaterialId&materialId=' + materialId;
		callProductDesignView("领料明细",url);
		
	}

	function reload() {
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		$().toastmessage('showNoticeToast', "修改成功。");
		
		return true;
	}	
	
	function setBeginningInventory(recordid) {
		var url = '${ctx}/business/storage?methodtype=setBeginningInventory';
		url = url +'&recordId='+recordid;
		
		layer.open({
			offset	:[30,''],
			type 	: 2,
			title 	: false,
			area 	: [ '800px', '300px' ], 
			scrollbar : false,
			title 	: false,
			content : url,
			cancel	: function(index){
			    layer.close(index);
				$('#TMaterial').DataTable().ajax.reload(null,false);
			  	return false; 
			}    
		});		

	};
	
	function setQuantityOnHand(recordid) {
		var url = '${ctx}/business/storage?methodtype=quantityOnHandView';
		url = url +'&recordId='+recordid;
		
		layer.open({
			offset	:[30,''],
			type 	: 2,
			title 	: false,
			area 	: [ '800px', '300px' ], 
			scrollbar : false,
			title 	: false,
			content : url,
			cancel	: function(index){				
			    layer.close(index);
				$('#TMaterial').DataTable().ajax.reload(null,false);
			  	return false; 
			},
			
		});		

	};
	
	function confirmQuantityOnHand(recordid) {
		var url = "${ctx}/business/storage?methodtype=quantityOnHandConfirmInit";
		url = url +'&recordId='+recordid;
		
		layer.open({
			offset	:[30,''],
			type 	: 2,
			title 	: false,
			area 	: [ '800px', '300px' ], 
			scrollbar : false,
			title 	: false,
			content : url,
			cancel	: function(index){				
			    layer.close(index);
				$('#TMaterial').DataTable().ajax.reload(null,false);
			  	return false; 
			},
			
		});	
		/*
		$.ajax({
			type : "post",
			url : url,
			//async : false,
			//data : null,
			dataType : "text",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {			
				reload();
			},
			 error:function(XMLHttpRequest, textStatus, errorThrown){
				//alert(textStatus)
			}
		});
		*/

	};
	

	function showInventoryHistory(materialId) {

		var url = '${ctx}/business/storage?methodtype=showInventoryHistoryInit&materialId=' + materialId;
		callProductDesignView("库存修改历史记录",url);
		
	}
	
	function downloadExcel() {
		
		var recordsTotal = $('#recordsTotal').val();
		if(recordsTotal <=0){
			$().toastmessage('showWarningToast', "没有可以导出的数据。");	
			return;
		}
		var searchType = $('#searchType').val();
		var keyword1 = $("#keyword1").val();
		var keyword2 = $("#keyword2").val();

		var materialType = $("#materialType").val();
		var materialTypeZZ = $("#materialTypeZZ").val();
		var materialTypeYS1 = $("#materialTypeYS1").val();
		var materialTypeYS2 = $("#materialTypeYS2").val();
		
		if(myTrim(keyword1) == '' && myTrim(keyword2) == '' ){
			//
		}else{
			searchType = '3';//不区分正常与否
		}
		var url = '${ctx}/business/financereport?methodtype=downloadExcel';
			url = url + "&searchType="+searchType;
			url = url + "&keyword1="+keyword1;
			url = url + "&keyword2="+keyword2;
			url = url + "&materialType="+materialType;
			url = url + "&materialTypeZZ="+materialTypeZZ;
			url = url + "&materialTypeYS1="+materialTypeYS1;
			url = url + "&materialTypeYS2="+materialTypeYS2;
		
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

			<input type="hidden" id="materialType" value="${materialType }" />
			<input type="hidden" id="materialTypeZZ" value="${materialTypeZZ }" />
			<input type="hidden" id="materialTypeYS1" value="${materialTypeYS1 }" />
			<input type="hidden" id="materialTypeYS2" value="${materialTypeYS2 }" />
			<input type="hidden" id="searchType" value="" />
			
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
	<div class="list">
			<div style="height:40px;float: left">
				<a  class="DTTT_button box" onclick="doSearchCustomer('3','');">全部</a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('1','');">库存为负数</a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('2','');">库存 ≠ 总到货－总领料</a>&nbsp;&nbsp;
				<!-- 
				<a  class="DTTT_button box" onclick="doSearchCustomer('3','1');">修改未确认</a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('3','0');">已确认</a>&nbsp;&nbsp;
				<a  class="DTTT_button box" onclick="doSearchCustomer('3','0','edit');">再次编辑</a> -->
			</div>
			<div style="height:40px;float: right">
				<a  class="DTTT_button box" onclick="downloadExcel('3');"><span>EXCEL导出</span></a>
			</div>
		<table  id="TMaterial" class="display">
			<thead>			
				<tr >
					<th style="width: 1px;">No</th>
					<th style="width: 100px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 25px;">单位</th>
					<th style="width: 40px;">修改记录</th>
					<th style="width: 50px;">期初库存</th>
					<th style="width: 50px;">期初单价</th>
					<th style="width: 50px;">移动<br>平均单价</th>
					<th style="width: 50px;">总需求数</th>
					<th style="width: 50px;">总合同数</th>
					<th style="width: 50px;">总到货数</th>
					<th style="width: 50px;">总领料数</th>
					<th style="width: 50px;">实际库存</th>
					<th style="width: 50px;">虚拟库存</th>
					<th style="width: 50px;">待入</th>
					<th style="width: 50px;">待出</th>
					<th style="width: 40px;">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
