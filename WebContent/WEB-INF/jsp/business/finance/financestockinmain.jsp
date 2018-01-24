<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>财务入库一览</title>
<script type="text/javascript">

	function ajax(type,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var url = "${ctx}/business/storage?methodtype=financeSearch"+"&sessionFlag="+sessionFlag;
		url += "&status=030";

		if(type == 'G'){
			url += "&makeTypeG=G&makeTypeL=";
		}else if(type == 'L'){
			url += "&makeTypeG=&makeTypeL=G";
		}

		var t = $('#TMaterial').DataTable({
			"paging": true,
			 "iDisplayLength" : 100,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"pagingType" : "full_numbers",
         	//"aaSorting": [[ 1, "ASC" ]],
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
						//alert("recordsTotal"+data["recordsTotal"])
						$("#keyword1").val(data["keyword1"]);
						$("#keyword2").val(data["keyword2"]);

						$('#baozhuang').val(data['baozhuang']);
						$('#zhuangpei').val(data['zhuangpei']);
						$('#zongji').val(data['zongji']);
						
						fnCallback(data);
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
					 alert("数据加载失败.")
					 }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns": [
				{"data": null,"className" : 'td-center'},
				{"data": "receiptId","className" : 'td-left'},
				{"data": "contractId"},
				{"data": "materialId"},
				{"data": "materialName"},
				{"data": "unit","className" : 'td-center'},
				{"data": "YSId"},//6
				{"data": "quantity","className" : 'td-right'},
				{"data": "contractPrice","className" : 'td-right'},
				{"data": "taxPrice","className" : 'td-right'},//9
				{"data": "taxTotal","className" : 'td-right'},//10
				{"data": "checkInDate","className" : 'td-center'},//11
				{"data": "approvalStatus","className" : 'td-center', "defaultContent" : '未审核'},//12
		
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":4,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];				    			
	    			name = jQuery.fixedWidth(name,32);				    			
	    			return name;
	    		}},
	    		{"targets":8,"render":function(data, type, row){
	    				    			
	    			return floatToCurrency( data );;
	    		}},
	    		{"targets":9,"render":function(data, type, row){
	    			
	    			var price = currencyToFloat(row["contractPrice"]);
	    			var newPrice = floatToCurrency( price / 1.17 );
	    				    			
	    			return data;
	    		}},
	    		{"targets":10,"render":function(data, type, row){
	    			
	    			var price = currencyToFloat(row["contractPrice"]);
	    			var newPrice = ( price / 1.17 );
	    			var quantity = currencyToFloat( row["quantity"] ); 
	    			var sum = floatToCurrency( newPrice * quantity );
	    			return sum;
	    		}},
	    		{"targets":12,"render":function(data, type, row){
	    			if(data == null || data == ""){
	    				return "未审核";
	    			}else{
		    			return data;
	    			}

	
	    		}},
	    		{
					"visible" : false,
					"targets" : [5]
				}
	           
	         ] 
		});

	}

	


	$(document).ready(function() {
		
		//010:默认显示未处理的数据
		ajax("ALL","true");
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});		
	})	
	
	function doSearch() {	

		$('#baozhuang').val('');
		$('#zhuangpei').val('');
		$('#zongji').val('');
		//false:不使用session
		ajax("ALL","false");

	}
	
	function doShow(contractId,arrivalId,receiptId) {

		var url = '${ctx}/business/storage?methodtype=addinit&contractId=' + contractId+"&arrivalId="+arrivalId+"&receiptId="+receiptId;
		callProductDesignView('入库信息',url);
		//location.href = url;
	}
	
	function selectContractByDate(type){
		
		$("#makeType").val(type);
		ajax(type,'false');
	}
	

	function downloadExcel() {
 
		var key1 = $("#keyword1").val();
		var key2 = $("#keyword2").val();
		var approvalStatusY = "";
		var approvalStatusN = "";
		var makeTypeL = "";
		var makeTypeG = "";
		
		if ($("#approvalStatusY").prop('checked')) {
			 approvalStatusY = $("#approvalStatusY").val();
		}
		if ($("#approvalStatusN").prop('checked')) {
			approvalStatusN = $("#approvalStatusN").val();
		}
		if ($("#makeTypeL").prop('checked')) {
			makeTypeL = $("#makeTypeL").val();
		}
		if ($("#makeTypeG").prop('checked')) {
			makeTypeG = $("#makeTypeG").val();
		}
		
		var url = '${ctx}/business/storage?methodtype=downloadExcel'
				 + "&approvalStatusY=" + approvalStatusY
				 + "&approvalStatusN=" + approvalStatusN
				 + "&makeTypeL=" + makeTypeL
				 + "&makeTypeG=" + makeTypeG
				 + "&key1=" + key1
				 + "&key2=" + key2;
		
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

		<input type="hidden" id="makeType" value="" />
			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label">关键字1：</td>
					<td>
						<input type="text" id="keyword1" name="keyword1" class="middle"/></td>
					<td class="label" width="100px">关键字2：</td> 
					<td>
						<input type="text" id="keyword2" name="keyword2" class="middle"/></td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();">查询</button></td>
					<td width="10%"></td> 
				</tr>
				<tr>
					<td width="10%"></td> 
					<td class="label">付款申请审核：</td>
					<td>
						<label><input type="checkbox" name="approvalStatusY" id="approvalStatusY"  value="020" />已审核</label>
						<label><input type="checkbox" name="approvalStatusN" id="approvalStatusN" value="010" />未审核</label>
					</td>
					<td class="label">物料类别：</td>
					<td>
						<label><input type="checkbox" name="makeTypeL" id="makeTypeL" value="G" />装配件</label>
						<label><input type="checkbox" name="makeTypeG" id="makeTypeG" value="G" />包装件</label>&nbsp;
					</td>
					<td ></td> 
				</tr>
			</table>
		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">
	<!-- 
		<div id="DTTT_container" align="left" style="height:40px;width:20%;float: left;">
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('L');">装配件</a>
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('G');">包装件</a>
		</div>
		 -->
		<div id="DTTT_container3" align="left" style="height:40px;width:70%;float: left;">
			装配件合计:	<input type="text" id="zhuangpei" class="read-only num"/>
			包装件合计:	<input type="text" id="baozhuang" class="read-only num"/>
			总计:		<input type="text" id="zongji" class="read-only num"/>
		</div>
		<div id="DTTT_container2" align="right" style="height:40px;width:10%;float: right">
			<a class="DTTT_button DTTT_button_text" onclick="downloadExcel();">EXCEL导出</a>
		</div>
		<table id="TMaterial" class="display dataTable" style="width: 100%;">
			<thead>						
				<tr>
					<th width=" 25px">No</th>
					<th style="width: 60px;">入库单编号</th>
					<th style="width: 80px;">合同编号</th>
					<th style="width: 100px;">物料编号</th>
					<th>物料名称</th>
					<th style="width: 40px;">单位</th>
					<th style="width: 50px;">耀升编号</th>
					<th style="width: 50px;">入库数量</th>
					<th style="width: 40px;">合同价</th>
					<th style="width: 50px;">税前价</th>
					<th style="width: 50px;">税前金额</th>
					<th style="width: 50px;">入库时间</th>
					<th style="width: 50px;">付款审核</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
