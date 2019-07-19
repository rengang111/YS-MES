<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>供应商基本数据检索</title>
<script type="text/javascript">

	function ajax(type,sessionFlag) {
		var table = $('#TSupplier').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var issuesFlag = $('#issuesFlag').val();//	
		var searchType = $('#searchType').val();
		
		var actionUrl = "${ctx}/business/supplier?methodtype=search&type="+type+"&sessionFlag="+sessionFlag;
		
		actionUrl += "&issuesFlag="+issuesFlag;
		actionUrl += "&searchType="+searchType;

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
				//"scrollCollapse":true,
				//"retrieve" : true,
				"sAjaxSource" : actionUrl,
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
							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);

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
					{"data": "normalDelivery", "defaultContent" : '',"className" : 'td-center'},
					{"data": "issues", "defaultContent" : '',"className" : ''},
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
		    			var dn = row["normalDelivery"];	
		    			if(dn == '' || dn == null)
		    				dn = '***';
		    			return dn;
                    }},
		    		{"targets":9,"render":function(data, type, row){
		    			return "<a href=\"#\" onClick=\"doPurchasePlan('" + row["supplierID"] + "')\">采购下单</a>";
                    }},
                    {"bSortable": false, "aTargets": [ 0,8,9 ] 
                    },
		    		{
						"visible" : false,
						"targets" : [6,7]
					}
			           
			     ] ,
			     "aaSorting": [[ 4, "asc" ]]
			}
		);
		
	}

	
	function initEvent(){

		ajax('','true');
	
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

		buttonSelectedEvent();//按钮选择式样
		
	})	
	
	function doSearch() {
	
		ajax('','false');
	}
	
	function SelectSupplier(type){
		ajax(type,'false');
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
		callProductDesignView('采购下单',url);
		
	}
	
	function doSearchCurrentTask(type){
		//$('#keyword1').val('');
		//$('#keyword2').val('');

		$('#searchType').val(type);//	
		
		ajax('','false');
	}
	

	function doSearchCurrentTask2(type){
		//$('#keyword1').val('');
		//$('#keyword2').val('');

		$('#searchType').val(type);//	
		$('#issuesFlag').val('Y');//问题供应商
		
		ajax('','false');
	}

	
</script>

</head>

<body>
<div id="container">
	<div id="search">

		<form id="condition"  style='padding: 0px; margin: 10px;' >

			<input type="hidden" id="issuesFlag" value="" /><!-- 问题供应商 -->
			<input type="hidden" id="searchType" value="" />
			
			<table>
				<tr>
					<td width="50px"></td> 
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
					<td width=""></td> 
				</tr><tr>
					<td width=""></td> 
					<td class="label"> 供应商问题：</td>
					<td colspan="3">
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('A');"   id="defutBtnA">ALL</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('ZZ');"   id="defutBtnZZ">自制</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('WJ');"   id="defutBtnWJ">五金</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('DZ');"   id="defutBtnDZ">电子</a>
						<a  class="DTTT_button box" onclick="doSearchCurrentTask('U');"   id="defutBtnU">未归类</a>&nbsp;
						<a  class="DTTT_button box" onclick="doSearchCurrentTask2('I');"   id="defutBtnI">问题供应商</a>
					</td>
					
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

		<div class="list">
				<!-- 
				<div id="DTTT_container" align="left" style="height:40px;width:50%">
					<a class="DTTT_button DTTT_button_text" onclick="SelectSupplier('010');">物料供应商</a>
					<a class="DTTT_button DTTT_button_text" onclick="SelectSupplier('020');">模具供应商</a>
					<a class="DTTT_button DTTT_button_text" onclick="SelectSupplier('030');">设备供应商</a>
				</div>
				 -->
			<div id="DTTT_container" align="right" style="height:40px;margin-bottom: -10px;">
				<a class="DTTT_button DTTT_button_text" onclick="doCreate();"><span>新建</span></a>
				<a class="DTTT_button DTTT_button_text" onclick="doDelete();"><span>删除</span></a>
			</div>
			<div id="clear"></div>
			<table id="TSupplier" class="display" >
				<thead>
					<tr>
						<th style="width:30px;">No</th>
						<th style="width:80px;">供应商编码</th>
						<th style="width:50px;" >简称</th>
						<th>供应商名称</th>
						<th style="width:50px;">分类</th>
						<th style="width:80px;">分类解释</th>
						<th style="width:50px;">付款条件</th>
						<th style="width:50px;">正常交期</th>
						<th style="width:150px;">供应商问题</th>
						<th style="width:50px;">采购</th>
					</tr>
				</thead>

			</table>
	</div>
</div>
</body>
</html>
