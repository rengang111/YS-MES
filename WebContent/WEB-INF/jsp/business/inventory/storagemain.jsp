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

<title>入库登记一览(检验完毕)</title>
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
			table.fnDestroy();
		}

		var url = "${ctx}/business/storage?methodtype=search";
		
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
				{"data": "materialId"},
				{"data": "materialName"},
				{"data": "unit","className" : 'td-center'},
				{"data": "contractId"},
				{"data": "YSId"},
				{"data": "checkDate","className" : 'td-center'},
				{"data": "resultName","className" : 'td-center'},
				{"data": "quantity","className" : 'td-right'},
				
				
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row){
					return row["rownum"];
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
	    		}}
	           
	         ] 
		});

	}

	


	$(document).ready(function() {
		
		var keyBackup = $("#keyBackup").val();

		if(keyBackup ==""){

			ajax("");
		}else{
			ajax("0");
			
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

		var url = '${ctx}/business/storage?methodtype=addinit&contractId=' + contractId;

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
					<th style="width: 120px;" class="dt-middle ">物料编号</th>
					<th class="dt-middle">物料名称</th>
					<th style="width: 50px;" class="dt-middle">单位</th>
					<th style="width: 100px;" class="dt-middle">合同编号</th>
					<th style="width: 50px;" class="dt-middle">耀升编号</th>
					<th style="width: 60px;" class="dt-middle">质检日期</th>
					<th style="width: 60px;" class="dt-middle">报检结果</th>
					<th style="width: 60px;" class="dt-middle">入库数量</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
