<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />


<%@ include file="../../common/common.jsp"%>

<title>进料报检一览(报检前)</title>
<script type="text/javascript">

	function ajax(pageFlg) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnDestroy();
		}
		
var url = "${ctx}/business/receiveinspection?methodtype=search";
		
		var type = pageFlg;
		
		if(type == '0'){
			//未检验
			$("#keyword1").val("");
			$("#keyword2").val("");
			//url += "&result=0"+"&purchaseDate1="+shortToday();
			
		}else if(type == '1'){
			//合格
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&result=020";
			
		}else if(type == '2'){
			//让步接收
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&result=030";
			
		}else if(type == '3'){
			//退货
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&result=040";			
		}

		url += "&keyBackup="+pageFlg;
		//alert(type+"----"+url)
		//var url = "${ctx}/business/receiveinspection?methodtype=search&pageFlg="+pageFlg;

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
							fnCallback(data);

						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
								//alert(XMLHttpRequest.status);
								//alert(XMLHttpRequest.readyState);
								//alert(textStatus);
								//alert(errorThrown);
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
					{"data": "arrivalId"},
					{"data": "arriveDate","className" : 'td-center'},
					{"data": "contractId"},
					{"data": "YSId"},
					{"data": "quantity","className" : 'td-right'},
					{"data": "resultName","className" : 'td-center'},
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
                    }},
		    		{"targets":1,"render":function(data, type, row){

		    			var materialId = row["materialId"];	
		    			var arrivalId = row["arrivalId"];		    			
		    			var rtn= "<a href=\"###\" onClick=\"doShow('" + arrivalId + "','" + materialId + "')\">"+materialId+"</a>";
		    			return rtn;
		    		}},
		    		{"targets":2,"render":function(data, type, row){
		    			
		    			var name = row["materialName"];				    			
		    			name = jQuery.fixedWidth(name,35);				    			
		    			return name;
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			
		    			var contractId = row["contractId"];
		    			var YSId = contractId.split('-',1);
		    							    			
		    			return YSId;
		    		}}
	           
	         ] 
		});

	}

	


	$(document).ready(function() {
		
		ajax("");
	
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

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		ajax("S");

	}
	

	
	function doShow(arrivalId,materialId) {

		var url = '${ctx}/business/receiveinspection?methodtype=addinit&materialId='+materialId+'&arrivalId='+arrivalId;
		location.href = url;
		
	}

	
</script>
</head>

<body>
<div id="container">
<div id="main">		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >

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
	<div  style="height:10px"></div>

	<div class="list">
		<div id="DTTT_container" align="left" style="height:40px;width:50%">
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('0');">未检验</a>
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('1');">合格</a>
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('2');">让步接收</a>
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('3');">退货</a>
		</div>
		<div id="clear"></div>
		<table id="TMaterial" class="display dataTable">
			<thead>						
				<tr>
					<th style="width: 1px;" class="dt-middle ">No</th>
					<th style="width: 170px;" class="dt-middle ">物料编号</th>
					<th class="dt-middle">物料名称</th>
					<th style="width: 50px;" class="dt-middle">到货编号</th>
					<th style="width: 60px;" class="dt-middle">到货日期</th>
					<th style="width: 95px;" class="dt-middle">合同编号</th>
					<th style="width: 60px;" class="dt-middle">耀升编号</th>
					<th style="width: 60px;" class="dt-middle ">到货数量</th>
					<th style="width: 40px;" class="dt-middle ">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
