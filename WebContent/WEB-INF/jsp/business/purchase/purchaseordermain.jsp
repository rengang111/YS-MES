<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>
<title>采购合同一览</title>
<script type="text/javascript">

	function ajax(pageFlg,status) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
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
			"sAjaxSource" : "${ctx}/business/contract?methodtype=search&keyBackup="+pageFlg+"&status="+status,
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
				{"data": "contractId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "materialId"},
				{"data": "materialName", "defaultContent" : ''},
				{"data": "YSId", "defaultContent" : ''},
				{"data": "supplierId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "purchaseDate", "defaultContent" : ''},
				{"data": "quantity", "defaultContent" : '0',"className" : 'td-right'},
				{"data": "contractStorage", "defaultContent" : '',"className" : 'td-right'},
				{"data": "returnGoods", "defaultContent" : '',"className" : 'td-right'},
			
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                   }},
	    		{"targets":4,"render":function(data, type, row){
	    			var rtn="";var ysid=row["YSId"];
	    			//alert("["+ysid+"]")
	    			var len = ysid.length;
	    			if(ysid == ""){
	    				return  "日常采购";	
	    			}else{
	    				//rtn= "<a href=\"###\" onClick=\"doShowYS('" + row["YSId"] + "')\">"+row["YSId"]+"</a>";				
	    				return ysid;
	    			}
	    			
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			
	    			return "<a href=\"###\" onClick=\"doShowControct('" + row["contractId"] + "')\">"+row["contractId"]+"</a>";			    			
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = row["materialName"];				    			
	    			if(name != null) name = jQuery.fixedWidth(name,35);
	    			return name;
	    		}}
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
		

		var keyBackup = $("#keyBackup").val();

		if(keyBackup ==""){

			ajax("","010");
		}else{
			ajax("","");
			
		}
		
		
	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		ajax("purchaseOrderMain","");

	}

	//合同状态
	function doSearchCustomer(type){
		ajax('purchaseOrderMain',type);
	}
	
	function doShowYS(YSId) {

		var url = '${ctx}/business/order?methodtype=getPurchaseOrder&YSId=' + YSId;
		
		location.href = url;
	}

	function doShowControct(contractId) {

		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
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
		<div id="DTTT_container2" style="height:40px;float: left">
					<a  class="DTTT_button " onclick="doSearchCustomer('010');"><span>未清</span></a>
					<a  class="DTTT_button " onclick="doSearchCustomer('020');"><span>已清</span></a>
		</div>
		<table id="TMaterial" class="display dataTable" >
			<thead>						
				<tr>
					<th style="width: 10px;" class="dt-middle ">No</th>
					<th style="width: 100px;"  class="dt-middle ">合同编号</th>
					<th style="width: 120px;"  class="dt-middle ">物料编号</th>
					<th class="dt-middle ">物料名称</th>
					<th style="width: 70px;"  class="dt-middle ">耀升编号</th>
					<th style="width: 60px;"  class="dt-middle ">供应商</th>
					<th style="width: 50px;"  class="dt-middle ">合同交期</th>
					<th style="width: 60px;"  class="dt-middle ">合同数</th>
					<th style="width: 60px;"  class="dt-middle ">入库数</th>
					<th style="width: 50px;"  class="dt-middle ">退货数</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
