<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>库存一览页面</title>
<script type="text/javascript">

	function searchAjax(sessionFlag,searchType) {
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
					{"data": null,"className" : 'td-center'},
					{"data": "materialId"},
					{"data": "materialName"},
					{"data": "dicName","className" : 'td-center'},
					{"data": "beginningInventory","className" : 'td-right'},//4
					{"data": "beginningPrice","className" : 'td-right'},//5
					{"data": "MAPrice","className" : 'td-right'},//6
					{"data": "contractQty","className" : 'td-right'},//	7
					{"data": "stockinQtiy","className" : 'td-right'},//
					{"data": "stockoutQty","className" : 'td-right'},//9
					{"data": "quantityOnHand","className" : 'td-right'},//10
					{"data": "availabelToPromise","className" : 'td-right'},
					{"data": "waitStockIn","className" : 'td-right'},
					{"data": "waitStockOut","className" : 'td-right'},
					{"data": null,"className" : 'td-center'},//14
				
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
		    		{"targets":5,"render":function(data, type, row){
		    						    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    						    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		     			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowContract('" + row["materialId"] +"')\">" + qty + "</a>";
		    			return rtn;		    			
		    		}},
		    		{"targets":8,"render":function(data, type, row){
		    			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowStockIn('" + row["materialId"] +"')\">" + qty + "</a>";
		    			return rtn;		    			
		    		}},
		    		{"targets":9,"render":function(data, type, row){
		    			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowStockOut('" + row["materialId"] +"')\">" + qty + "</a>";
		    						    			
		    			return rtn;
		    		}},
		    		{"targets":10,"render":function(data, type, row){
		    						    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":11,"render":function(data, type, row){
		    						    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":12,"render":function(data, type, row){
		    						    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":13,"render":function(data, type, row){
		    			var rtn = "";
		    			var qty= floatToCurrency(data);
		    			rtn= "<a href=\"###\" onClick=\"doShowPlan('" + row["materialId"] +"')\">" + qty + "</a>";
		    						    			
		    			return rtn;
		    		}},
		    		{"targets":4,"render":function(data, type, row){
		    			//期初值设定
		    			var inventory = currencyToFloat(data);
		    			var val =data;
		    			if(inventory <= 0 )
		    				val=  "设置";		    			
		    			return  "<a href=\"###\" onClick=\"setBeginningInventory('" + row["recordId"] +"')\">" + val + "</a>";	    			
		    		}},
		    		{"targets":14,"render":function(data, type, row){
		    			//实际库存修正
		    			var txt = "";
		    			txt +=  "<a href=\"###\" onClick=\"setQuantityOnHand('" + row["recordId"] +"')\">" + "修改" + "</a>";
		    			txt += "&nbsp;";
		    			txt +=  "<a href=\"###\" onClick=\"confirmQuantityOnHand('" + row["recordId"] +"')\">" + "确认" + "</a>";
		    					    			
		    			return  txt;	    			
		    		}},
		    		{
		    			"visible":false,
		    			"targets":[4,5,6]
		    		}
	        	] 
			}
		);

	}

	$(document).ready(function() {
		
		searchAjax("true","1");
	
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
		searchAjax("false","");

	}
	

	function doSearchCustomer(type) {	

		searchAjax("false",type);

	}
	
	function doShow(recordId,parentId) {

		var url = '${ctx}/business/material?methodtype=detailView&parentId=' + parentId+'&recordId='+recordId;
		callProductDesignView("物料信息",url);
		
	}

	function doShowPlan(materialId) {

		var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanByMaterialId&materialId=' + materialId;
		callProductDesignView("合同明细",url);
		
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
	function doEdit(recordId,parentId) {
		var str = '';
		var isFirstRow = true;
		var url = '${ctx}/business/material?methodtype=edit&parentId=' + parentId+'&recordId='+recordId;

		location.href = url;
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
		var url = '${ctx}/business/storage?methodtype=setQuantityOnHand';
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
			},
			
		});		

	};
	
	function confirmQuantityOnHand(recordid) {
		var url = "${ctx}/business/storage?methodtype=confirmQuantityOnHand";
		url = url +'&recordId='+recordid;
		if(confirm('确定要修正吗?')){
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
		}

	};
	
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
				<a  class="DTTT_button box" onclick="doSearchCustomer('1');"><span>库存为负数</span></a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('2');"><span>库存 ≠ 总到货－总领料</span></a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('3');"><span>正常库存</span></a>
			</div>
		<table  id="TMaterial" class="display">
			<thead>			
				<tr >
					<th style="width: 1px;">No</th>
					<th style="width: 100px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 25px;">单位</th>
					<th style="width: 50px;">期初库存</th>
					<th style="width: 50px;">期初单价</th>
					<th style="width: 50px;">移动<br>平均单价</th>
					<th style="width: 50px;">总合同数</th>
					<th style="width: 50px;">总到货数</th>
					<th style="width: 50px;">总领料数</th>
					<th style="width: 50px;">实际库存</th>
					<th style="width: 50px;">虚拟库存</th>
					<th style="width: 50px;">待入</th>
					<th style="width: 50px;">待出</th>
					<th style="width: 50px;">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
