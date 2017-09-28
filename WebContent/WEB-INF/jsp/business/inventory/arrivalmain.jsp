<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />


<%@ include file="../../common/common2.jsp"%>

<title>到货登记一览(合同未到货)</title>
<script type="text/javascript">


	//Form序列化后转为AJAX可提交的JSON格式。
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	function ajax(pageFlg) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}

		var url = "${ctx}/business/arrival?methodtype=contractArrivalSearch";
		
		var type = pageFlg;
		
		if(type == '0'){
			//逾期未到货
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&accumulated1=0"+"&purchaseDate1="+shortToday();
			
		}else if(type == '1'){
			//未到货
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&accumulated1=0";
			
		}else if(type == '2'){
			//已到货
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&accumulated2=0";
			
		}

		url += "&keyBackup="+pageFlg;
		//alert(type+"----"+url)

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
				{"data": "YSId"},
				{"data": "contractId"},
				{"data": "materialId"},
				{"data": "materialName"},
				{"data": "customerShortName"},
				{"data": "supplierId"},
				{"data": "purchaseDate","className" : 'td-right'},
				{"data": "quantity","className" : 'td-right'},
				{"data": "accumulated","className" : 'td-right'},
				{"data": "surplus","className" : 'td-right'},
				{"data": null,"className" : 'td-center'},

			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
                }},
	    		{"targets":1,"render":function(data, type, row){
	    			
	    			var rtn = "<a href=\"###\" onClick=\"showYS('" + row["YSId"] + "')\">"+row["YSId"]+"</a>";
    			
	    			return data;
	    		}},
	    		{"targets":2,"render":function(data, type, row){
	    			
	    			var rtn = "<a href=\"###\" onClick=\"showContract('" + row["contractId"] + "')\">"+row["contractId"]+"</a>";
    			
	    			return data;
	    		}},
	    		{"targets":3,"render":function(data, type, row){

	    			var contractId = row["contractId"];	
	    			var quantity = currencyToFloat(row["quantity"]);
	    			var arrivalSum = currencyToFloat(row["arrivalSum"]);
	    			var reful=  ( quantity - arrivalSum);

	    			var rtn = row["materialId"];
	    			// if(reful >0)
	    			rtn =  "<a href=\"###\" onClick=\"doCreate('" + row["contractId"] + "')\">"+row["materialId"]+"</a>";
	    		
	    			return rtn;
	    		}},
	    		{"targets":4,"render":function(data, type, row){
	    			
	    			var name = row["materialName"];				    			
	    			name = jQuery.fixedWidth(name,30);				    			
	    			return name;
	    		}},
	    		{"targets":11,"render":function(data, type, row){
	    			
	    			var rtn = "<a href=\"###\" onClick=\"doShow('" + row["contractId"] + "')\">查看</a>";
    			
	    			return rtn;
	    		}},
	    		{
	    			"visible":false,
	    			"targets":[11]
	    		}
	           
	         ] 
		});

	}

	


	$(document).ready(function() {
		
		var keyBackup = $("#keyBackup").val();

		if(keyBackup ==""){

			ajax("0");//逾期未到货
		}else{
			ajax("");
			
		}
	
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
	
	function doCreate(contractId) {
		
		var url = '${ctx}/business/arrival?methodtype=addinit&contractId='+contractId;
		location.href = url;
	}
	
	function doShow(contractId) {

		var url = '${ctx}/business/arrival?methodtype=gotoArrivalView&contractId=' + contractId;

		location.href = url;
	}

	function doEdit(recordId,parentId) {
		var str = '';
		var isFirstRow = true;
		var url = '${ctx}/business/material?methodtype=edit&parentId=' + parentId+'&recordId='+recordId;

		location.href = url;
	}
		
	function doDelete() {

		var str = '';
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				str += $(this).val() + ",";
			}
		});

		if (str != '') {
			if(confirm("确定要删除数据吗？")) {
				jQuery.ajax({
					type : 'POST',
					async: false,
					contentType : 'application/json',
					dataType : 'json',
					data : str,
					url : "${ctx}/business/arrival?methodtype=delete",
					success : function(data) {
						reload();						
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				});
			}
		} else {
			alert("请至少选择一条数据");
		}
		
	}

	function reload() {
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		
		return true;
	}
	
	function selectContractByDate(type){
		
		ajax(type);
	}
	
	function showYS(YSId){
		var url = '${ctx}/business/order?methodtype=detailView&YSId=' + YSId;

		openLayer(url);
	}
	
	function showContract(contractId) {
		var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
		openLayer(url);

	};
	
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
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('0');">逾期未到货</a>
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('1');">未到货</a>
			<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('2');">已到货</a>
		</div>

		<div id="clear"></div>
		<table id="TMaterial" class="display dataTable" style="width: 100%;">
			<thead>						
				<tr>
					<th style="width: 1px;" class="dt-middle ">No</th>
					<th style="width: 50px;" class="dt-middle">耀升编号</th>
					<th style="width: 90px;" class="dt-middle">合同编号</th>
					<th style="width: 100px;" class="dt-middle ">物料编号</th>
					<th class="dt-middle">物料名称</th>
					<th style="width: 30px;" class="dt-middle">客户</th>
					<th style="width: 50px;" class="dt-middle">供应商</th>
					<th style="width: 50px;" class="dt-middle">合同交期</th>
					<th style="width: 50px;" class="dt-middle">合同数量</th>
					<th style="width: 50px;" class="dt-middle ">累计入库</th>
					<th style="width: 50px;" class="dt-middle ">剩余数量</th>
					<th style="width: 1px;" class="dt-middle ">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
