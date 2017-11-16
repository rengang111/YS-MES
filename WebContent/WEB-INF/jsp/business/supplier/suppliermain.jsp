<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>供应商基本数据检索</title>
<script type="text/javascript">

	function ajax(keyBackup,type) {
		var table = $('#TSupplier').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		
		var t = $('#TSupplier').DataTable({
				"paging": true,
				"lengthChange":false,
				 "iDisplayLength" : 50,
				//"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"pagingType" : "full_numbers",
				//"scrollY":scrollHeight,
				"scrollCollapse":true,
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/supplier?methodtype=search&type="+type+"&keyBackup="+keyBackup,
				//"dom" : 'T<"clear">rt',
				
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
			                 //alert(XMLHttpRequest.status);
			                 //alert(XMLHttpRequest.readyState);
			                 //alert(textStatus);
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null, "defaultContent" : '',"className" : 'td-center'},
					{"data": "supplierID", "defaultContent" : ''},
					{"data": "shortName", "defaultContent" : ''},
					{"data": "supplierName", "defaultContent" : ''},
					{"data": "categoryId", "defaultContent" : ''},
					{"data": "categoryDes", "defaultContent" : ''},
					{"data": "paymentTerm", "defaultContent" : '',"className" : 'td-center'},
					{"data": null, "defaultContent" : '',"className" : 'td-center'}
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			return "<a href=\"#\" onClick=\"doUpdate('" + row["recordId"] + "')\">"+data+"</a>";
                    }},
		    		{"targets":5,"render":function(data, type, row){
		    			var name = row["categoryDes"];				    			
		    			return jQuery.fixedWidth(name,25);
                    }},
		    		{"targets":7,"render":function(data, type, row){
		    			return "<a href=\"#\" onClick=\"doPurchasePlan('" + row["supplierID"] + "')\">采购下单</a>";
                    }},
                    {"bSortable": false, "aTargets": [ 0,7 ] 
                    }
			           
			     ] ,
			     "aaSorting": [[ 4, "asc" ]]
			}
		);
		
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				var num   = i + 1;
				var checkBox = "<input type=checkbox name='numCheck' id='numCheck' value='" + num + "' />";
				//cell.innerHTML = num + checkBox;
			});
		}).draw();
	}

	
	function initEvent(){

		ajax('','');
	
		$('#TSupplier').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TSupplier').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}

	$(document).ready(function() {
		initEvent();
		
	})	
	
	function doSearch() {
	
		ajax('S','');
	}
	
	function SelectSupplier(type){
		ajax('',type);
	}
	
	function doCreate() {
		
		var url = "${ctx}/business/supplier?methodtype=addinit";
		location.href = url;
		//openLayer(url, '', layerHeight, true);
	}
	
	function doUpdate(key) {
		var url = "${ctx}/business/supplier?methodtype=show&key=" + key;
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
					url : "${ctx}/business/supplier?methodtype=delete",
					success : function(data) {
						reload();
						//alert(data.message);
						
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
		                // alert(XMLHttpRequest.status);
		                //alert(XMLHttpRequest.readyState);
		                //alert(textStatus);
		             }
				});
			}
		} else {
			alert("请至少选择一条数据");
		}
		
	}

	function reload() {
		
		$('#TSupplier').DataTable().ajax.reload(null,false);
		
		return true;
	}

	function doPurchasePlan(supplierId) {
		//goBackFlag:区别采购入口是物料还是供应商
		var url = '${ctx}/business/contract?methodtype=createRoutineContractInit&goBackFlag=';
		url = url + '&supplierId=' + supplierId;
		location.href = url;
		
	}
	
</script>

</head>

<body>
<div id="container">
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
						<button type="button" id="retrieve" class="DTTT_button" style="width:50px" value="查询" onClick="doSearch();"/>查询
					</td>
					<td width="10%"></td> 
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">

			<div id="DTTT_container" align="left" style="height:40px;width:50%">
				<a class="DTTT_button DTTT_button_text" onclick="SelectSupplier('010');">物料供应商</a>
				<a class="DTTT_button DTTT_button_text" onclick="SelectSupplier('020');">模具供应商</a>
				<a class="DTTT_button DTTT_button_text" onclick="SelectSupplier('030');">设备供应商</a>
			</div>
			<div id="DTTT_container" align="right" style="height:40px;margin-top: -40px;">
				<a class="DTTT_button DTTT_button_text" onclick="doCreate();"><span>新建</span></a>
				<a class="DTTT_button DTTT_button_text" onclick="doDelete();"><span>删除</span></a>
			</div>
			<div id="clear"></div>
			<table id="TSupplier" class="display" >
				<thead>
					<tr>
						<th style="width:30px;">No</th>
						<th style="width:120px;">供应商编码</th>
						<th style="width:60px;" >简称</th>
						<th>供应商名称</th>
						<th style="width:50px;">物料分类</th>
						<th style="width:150px;">分类解释</th>
						<th style="width:50px;">付款条件</th>
						<th style="width:50px;">操作</th>
					</tr>
				</thead>

			</table>
	</div>
</div>
</body>
</html>
