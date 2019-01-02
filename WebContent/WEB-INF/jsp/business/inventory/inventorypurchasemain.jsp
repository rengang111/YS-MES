<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>库存一览页面--采购件</title>
<style>
body{
  /*  font-size:10px;*/
}
</style>
<script type="text/javascript">

	function searchAjax(sessionFlag,searchType,confirmFlag,categoryId) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var stockType    = $("#stockType").val();
		var url = "${ctx}/business/storage?methodtype=beginningInventorySearch&sessionFlag="+sessionFlag;
		url = url + "&stockType="+stockType;
		url = url + "&searchType="+searchType;
		url = url + "&categoryId="+categoryId;
		
		
		
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
							fnCallback(data);
							var key1 = data["keyword1"]
							var key2 = data["keyword2"]
							$("#keyword1").val(key1);
							$("#keyword2").val(key2);
							/*
							if((key1) == "" && (key2) == ""){
							 	$('#defutBtn').removeClass("start").addClass("end");							
							}else{							
							 	$('#defutBtn').removeClass("end").addClass("start");
							}	
							*/
							//deleteRow();
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
					{"data": "materialName","className" : 'td-left'},//2
					{"data": "categoryName","className" : 'td-left'},//3
					{"data": "reviseDate","className" : 'td-right', "defaultContent" : '***'},//4					
					{"data": "beginningInventory","className" : 'td-right'},//5
					{"data": "beginningPrice","className" : 'td-right'},//6
					{"data": "MAPrice","className" : 'td-right'},//7
										
					{"data": "waitStockIn","className" : 'td-right'},//8待入
					{"data": "waitStockOut","className" : 'td-right'},//12待出
					{"data": null,"className" : 'td-right'},//9当前增量
					{"data": "quantityOnHand","className" : 'td-right'},//10实际库存
					{"data": "availabelToPromise","className" : 'td-right'},//11虚拟库存
					{"data": null,"className" : 'td-center'},//13状态
				
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"];
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShow('" + row["recordId"] +"','"+ row["parentId"] + "')\">" + row["materialId"] + "</a>";
		    			var index = row["rownum"]; 
		    			var text = '<input type="hidden" id="stockList'+index+'.materialid" value="'+data+'" />'
		    			return  rtn + text;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			
		    			var name = row["materialName"];				    			
		    			name = jQuery.fixedWidth(name,24);
		    			var rtn = "<a href=\"###\"  onClick=\"showMaterialHistory('" + row["materialId"] +"')\">" + name + "</a>";
			    			
		    			return rtn;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			
		    			var reviseDate = row["reviseDate"];	
		    			var reviseQty = currencyToFloat(row["reviseQty"]);
		    			if(reviseDate == '' || reviseDate == null)
		    				return '***';
		    			else
		    				return reviseDate+'<br>' + reviseQty;
		     			//txt +=  "<a href=\"###\" onClick=\"showInventoryHistory('" + row["materialId"] +"')\">" + quantity + "</a>";
		     		      			
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			//物料分类名称		    					    			
		    			return  data;	    			
		    		}},
		    		{"targets":13,"render":function(data, type, row){
		    			//实际库存修正
		    			var confirmFlag = row["quantityEditFlag"];
		    			var txt = "";
	
		    				if((confirmFlag) == "1"){
		    					txt = "待确认";
		    				}else if((confirmFlag) == "0"){	
			    				
				    			txt = "已确认";
			    			}

						return  txt;
						
		     					    	    			
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    						    			
		    			return floatToCurrency(data);
		    		}},
	
		    		{"targets":10,"render":function(data, type, row){//当前增量
		    			var rtn = "";
		    			var stockin= currencyToFloat(row["waitStockIn"]);
		    			var stockout= currencyToFloat(row["waitStockOut"]);
		    			var quantity = floatToCurrency( stockin - stockout );

		    			//rtn= "<a href=\"###\"  onClick=\"doShowPlan('" + row["materialId"] +"')\">" + quantity + "</a>";
		    			
		    			return quantity;
		    		}},
		    		{"targets":11,"render":function(data, type, row){//实际库存
		    			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			var style = '';
		    			if(currencyToFloat(data) < 0){
		    				style = 'color: green;font-weight: bold;font-size: 11px;';//负数
		    			}
		    			rtn +=  "<a href=\"###\" style=\""+style+"\"  onClick=\"setQuantityOnHand('" + row["recordId"] +"')\">" + qty + "</a>";
		    			return rtn;
		    		}},
		    		{"targets":12,"render":function(data, type, row){//虚拟库存
		    			var rtn = "";
		    			var style = '';
		    			if(currencyToFloat(data) < 0){
		    				style = 'color: green;font-weight: bold;font-size: 11px;';//负数
		    			}
		    			var qty= floatToCurrency(data);
		    			rtn = "<span style=\""+style+"\" >"+qty+"</span>";
		    			//rtn= "<a href=\"###\" onClick=\"doShowWaitOut('" + row["materialId"] +"')\">" + qty + "</a>";
		    						    			
		    			return rtn;
		    		}},
		    		{"targets":8,"render":function(data, type, row){//待入
		    			var rtn = "";
		    			var mate = row["materialId"];
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowWaitIn('" + row["materialId"] +"')\">" + qty + "</a>";
		    						    			
		    			return rtn;
		    		}},
		    		{"targets":9,"render":function(data, type, row){//待出
		    			var rtn = "";
		    			var mate = row["materialId"];
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowWaitOut('" + row["materialId"] +"')\">" + qty + "</a>";
		    						    			
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
		    		{
		    			"visible":false,
		    			"targets":[5,6,7]
		    		}
	        	] 
			}
		);

	}

	$(document).ready(function() {
		var searchType = $('#searchType').val();
		$('#categoryId').val('B02');
		
		searchAjax("true",searchType,"","B02");
	
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
	 	$('#defutBtnB02').removeClass("start").addClass("end");
		
	})	
	
	function deleteRow(){
		
		var sum7 = 0;
		$('#TMaterial tbody tr').each (function (){
			
			var materialId = $(this).find("td").eq(1).find("input").val();//
			//alert(materialId)


			if(materialId == 'A02.02005.000'){
				$(this).find("td").parent().addClass('delete')
			}
						
		});	
		
		//$('#totalValue'+num).html(floatToCurrency(sum7));
	}
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		searchAjax("false","","","");

	}
		
	function doShow(recordId,parentId) {

		var url = '${ctx}/business/material?methodtype=detailView&parentId=' + parentId+'&recordId='+recordId;
		callProductDesignView("物料信息",url);
		
	}

	function doShowPlan(materialId) {
		var stockType=$('#stockType').val();

		if(stockType == '010'){//
			var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanForRawByMaterialId&materialId=' + materialId;
			//callProductDesignView("原材料采购方案",url);
			
		}else{
			var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanByMaterialId&materialId=' + materialId;
			//callProductDesignView("采购件",url);
		}
		
		layer.open({
			offset :[30,''],
			type : 2,
			title : false,
			area : [ '1000px', '500px' ], 
			scrollbar : false,
			title : false,
			content : url,
			//只有当点击confirm框的确定时，该层才会关闭
			cancel: function(index){ 
			 // if(confirm('确定要关闭么')){
			    layer.close(index)
			 // }
			  $('#TMaterial').DataTable().ajax.reload(null,false);
			  return false; 
			}    
		});		
		
	}


	function doShowWaitOut(materialId) {

		var url = '${ctx}/business/inventory?methodtype=planAndStockOut&materialId=' + materialId;
		callProductDesignView("待出数量",url);
		
	}
	function doShowWaitIn(materialId) {

		var url = '${ctx}/business/inventory?methodtype=contractAndStockIn&materialId=' + materialId;
		callProductDesignView("待入数量",url);
		
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

	function showMaterialHistory(materialId) {

		var url = '${ctx}/business/financereport?methodtype=reportForDaybookByMaterialIdInit&materialId=' + materialId;
		callProductDesignView("物料入出库明细",url);
		
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
		var categoryId = $('#categoryId').val();
		var searchType = $('#searchType').val();
		var keyword1 = $("#keyword1").val();
		var keyword2 = $("#keyword2").val();

		var stockType = $("#stockType").val();
		
		if(keyword1 != '' || keyword2 != '' ){
			searchType = '';//不区分正常与否
		}
		var url = '${ctx}/business/storage?methodtype=downloadExcelForInventory';
			url = url + "&searchType="+searchType;
			url = url + "&keyword1="+keyword1;
			url = url + "&keyword2="+keyword2;
			url = url + "&stockType="+stockType;
			url = url + "&categoryId="+categoryId;
		
		url =encodeURI(encodeURI(url));//中文两次转码

		location.href = url;
	}
	
	//物料分类
	function doSearchCustomer(categoryId){
		$('#keyword1').val('');
		$('#keyword2').val('');
		$('#categoryId').val(categoryId);
		
		searchAjax('false','','',categoryId);
	}
</script>
</head>

<body>
<div id="container">
<div id="main">
	<div id="search">

		<form id="condition"  style='padding: 0px; margin: 10px;' >

			<input type="hidden" id="stockType" value="${stockType }" />
			<input type="hidden" id="searchType" value="${searchType }" />
			<input type="hidden" id="categoryId" value="" />
			
			<table>
				<tr>
					<td width="50px"></td> 
					<td class="label">关键字1：</td>
					<td class="condition">
						<input type="text" id="keyword1" name="keyword1" class="middle" value="${keyword1 }"/>
					</td>
					<td class="label">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle"/>
					</td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();">查询</button>
					</td>
				<td width="100px"></td> 
				</tr>
			</table>
			<table>
				<tr>
				<td width="50px"></td> 
				<td width=""></td> 
				<td width="50px"></td> 
				<td colspan="2">
				<c:forEach var='list' items='${category}' varStatus='status'>
					<a id="defutBtn${list.categoryId }" style="height: 15px;margin-top: 5px;" 
						class="DTTT_button box" onclick="doSearchCustomer('${list.categoryId }');">
						<span>${list.categoryId }</span></a>
				</c:forEach>
				</td> 
				<td width="100px"></td>
				</tr>
			</table>

		</form>
	</div>
	<div class="list">
		<!-- 
			<div style="height:40px;float: left">
				
				<a  class="DTTT_button box" onclick="doSearchCustomer('','');"  id="defutBtn">全部</a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('1','');">实库为负</a>
				
				<a  class="DTTT_button box" onclick="doSearchCustomer('2','');">虚库为负</a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('3','');">实库 ≠ 总到货－总领料</a>&nbsp;&nbsp;
				
				<a  class="DTTT_button box" onclick="doSearchCustomer('3','0');">已确认</a>&nbsp;&nbsp;
				<a  class="DTTT_button box" onclick="doSearchCustomer('3','0','edit');">再次编辑</a>
			</div>-->
			<div style="height:40px;float: right">
				<a  class="DTTT_button" onclick="downloadExcel();"><span>EXCEL导出</span></a>
			</div>
		<table  id="TMaterial" class="display">
			<thead>			
				<tr >
					<th style="width: 1px;">No</th>
					<th style="width: 100px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 40px;">物料分类</th>
					<th style="width: 45px;">修改时间<br>修改记录</th>
					<th style="width: 50px;">期初库存</th>
					<th style="width: 50px;">期初单价</th>
					<th style="width: 50px;">移动<br>平均单价</th>
					
					<th style="width: 50px;">待入<br>K</th>
					<th style="width: 50px;">待出<br>J</th>
					<th style="width: 50px;">当前增量<br>F=K-J</th>
					<th style="width: 50px;">实际库存<br>G</th>
					<th style="width: 50px;">虚拟库存<br>H=G+F</th>
					
					<th style="width: 40px;">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
