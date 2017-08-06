<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />


<%@ include file="../../common/common.jsp"%>

<title>做单资料一览页面</title>
<script type="text/javascript">

	function ajax(pageFlg) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable();
			table.fnDestroy();
		}
		var url = "${ctx}/business/productDesign?methodtype=search";
		var type = pageFlg;
		
		if(type == '0'){
			//未完成
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&status=010";
			
		}else if(type == '1'){
			//完成
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&status=020";
			
		}else if(type == '2'){
			//完美
			$("#keyword1").val("");
			$("#keyword2").val("");
			url += "&status=030";
			
		}

		url += "&keyBackup="+pageFlg;
		
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : false,
				"serverSide" : false,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"pagingType" : "full_numbers",
				//"scrollY":scrollHeight,
				//"scrollCollapse":true,
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
						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null, "defaultContent" : '',"className" : 'td-center'},
					{"data": "YSId", "defaultContent" : ''},
					{"data": "materialId", "defaultContent" : ''},
					{"data": "materialName", "defaultContent" : ''},
					{"data": "orderId", "defaultContent" : ''},
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-left'},
					{"data": "statusName", "defaultContent" : '',"className" : 'td-center'},
					{"data": "productClassifyName", "className" : 'td-center'},
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"];
		    			 
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ 
		    					row["PIId"] + "','"+ 
		    					row["YSId"] + "','"+ 
		    					row["productId"] + "','"+ 
		    					row["productClassify"] + 
		    					"')\">"+row["YSId"]+"</a>";
		    			return rtn;
		    		}},
		    		
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["materialName"];
		    			name = jQuery.fixedWidth(name,40,true);//true:两边截取,左边从汉字开始
		    			return name;
		    		}},
		    		
		    		{"targets":2,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShowHistory('"+ 
		    					row["PIId"] + "','"+ 
		    					row["YSId"] + "','"+ 
		    					row["materialId"] + 
		    					"')\">"+row["materialId"]+"</a>";
		    			return rtn;
		    		}}				           
	         	] 
			}
		);

	}
	
	function YSKcheck(v,id){
		var zzFlag = "";
		if(id != null && id != ''){
			zzFlag = id.substr(2,3);
		}
		if(zzFlag == 'YSK') v = 0;//库存订单不显示明细内容
		return v;
		
	}
	
	function initEvent(){

	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}

	$(document).ready(function() {

		var keyBackup = $("#keyBackup").val();

		if(keyBackup ==""){

			ajax("0");//未完成
		}else{
			ajax("");
			
		}
		
		initEvent();
		
	})	
	
	function doSearch() {	

		ajax("S");

	}

	
	function doShowDetail(PIId,YSId,productId,type) {
 		var goBackFlag = 'productDesignMain';
		var url = '${ctx}/business/productDesign?methodtype=detailView'
				+'&PIId='+PIId 
				+'&YSId='+YSId
				+'&productId='+productId
				+'&productType='+type
				+'&goBackFlag='+goBackFlag;

		location.href = url;
	}
	

	function doShowHistory(PIId,YSId,productId) {
 		var goBackFlag = '1';
		var url = '${ctx}/business/productDesign?methodtype=detailViewHistory'
				+'&PIId='+PIId 
				+'&YSId='+YSId
				+'&productId='+productId
				+'&goBackFlag='+goBackFlag;

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
			if(confirm("采购方案,采购合同,全部会被删除,\n\n        确定要删除订单吗？")) {
				jQuery.ajax({
					type : 'POST',
					async: false,
					contentType : 'application/json',
					dataType : 'json',
					data : str,
					url : "${ctx}/business/order?methodtype=delete",
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
	
	function selectContractByDate(type){
		
		ajax(type);
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
									style="width:50px" value="查询" onclick="doSearch();"/>查询
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">
				<div id="DTTT_container" align="left" style="height:40px;width:50%">
					<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('0');">未完成</a>
					<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('1');">完成</a>
					<a class="DTTT_button DTTT_button_text" onclick="selectContractByDate('2');">完美</a>
				</div>
			
				<table id="TMaterial" class="display dataTable" cellspacing="0">
					<thead>						
						<tr>
							<th style="width: 10px;" class="dt-middle ">No</th>
							<th style="width: 70px;" class="dt-middle ">耀升编号</th>
							<th style="width: 150px;" class="dt-middle ">产品编号</th>
							<th class="dt-middle ">产品名称</th>
							<th style="width: 120px;" class="dt-middle ">订单号</th>
							<th style="width: 60px;" class="dt-middle ">订单交期</th>
							<th style="width: 60px;" class="dt-middle ">完成状况</th>
							<th style="width: 60px;" class="dt-middle ">版本类别</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
