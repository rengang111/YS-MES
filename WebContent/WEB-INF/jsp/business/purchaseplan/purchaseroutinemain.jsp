<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>

<title>常规采购</title>
<script type="text/javascript">

	function ajax(pageFlg,type,scrollHeight) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/material?methodtype=searchPurchaseRoutine"+"&keyBackup="+pageFlg+type;
		
		var t = $('#TMaterial').DataTable({
				"paging": false,
				// "iDisplayLength" : 50,
				//"lengthChange":false,
				//"lengthMenu":[10,150,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : false,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				//"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : url,
				"scrollY":scrollHeight,
				"scrollCollapse":true,
				 "aaSorting": [[ 5, "asc" ]],
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
							{"data": null,"className" : 'td-right'},
							{"data": "materialId","className" : 'td-left'},//1
							{"data": "materialName"},//2
							{"data": "categoryName","className" : 'td-left'},//3
							{"data": "dicName","className" : 'td-center'},//4
							{"data": "availabelToPromise","className" : 'td-right'},//5
							{"data": "safetyInventory","className" : 'td-right'},//6
							{"data": "supplierId","className" : 'td-left'},//7供应商
							{"data": "price","className" : 'td-right'}//8价格
							
						],
				"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
	                   //row["rownum"] + 
			    		}},
			    		{"targets":1,"render":function(data, type, row){
			    			var rtn = "";
			    			rtn= "<a href=\"###\" onClick=\"doShow('" + row["recordId"] +"','"+ row["parentId"] + "')\">" + row["materialId"] + "</a>";
			    			return rtn;
			    		}},
			    		{"targets":2,"render":function(data, type, row){
			    			
			    			var name = row["materialName"];				    			
			    			name = jQuery.fixedWidth(name,35);				    			
			    			return name;
			    		}},
			    		{"targets":3,"render":function(data, type, row){
			    			
			    			var name = row["categoryName"];				    			
			    			name = jQuery.fixedWidth(name,20);				    			
			    			return name;
			    		}}
			    		//,  { type: 'any-number', targets : 0 }
		           
		         ] 
			});
	}

	
	$(document).ready(function() {
		var scrollHeight = $(document).height() - 200; 
		var type = "&purchaseType1=020&purchaseType2=040";
		ajax("",type,scrollHeight);
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			$(this).toggleClass("selected");
		    if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
		        
		    	$(this).children().first().children().prop("checked", true);
		    }else{//如果没有被选中

		    	$(this).children().first().children().prop("checked", false);
		    }			
		});	

	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		var scrollHeight = $(document).height() - 200; 
		var type = "";
		ajax("purchaseRoutineMain",type,scrollHeight);

	}
	
	function doSearch2(str) {	

		$("#keyword1").val("");
		$("#keyword2").val("");
		var scrollHeight = $(document).height() - 200; 
		var type = "&purchaseType="+str;
		ajax("purchaseRoutineMain",type,scrollHeight);

	}
	
	function doCreate() {
		var str = '';
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				str += $(this).val() + ",";
			}
		});

		if (str == '') {
			alert("请至少选择一条数据");
			return;
		}		
		
		var url = '${ctx}/business/purchasePlan?methodtype=purchaseRoutineAddInit&purchaseType1=020&purchaseType2=040';
		url = url +"&data="+str;
		location.href = url;
		
	}
	
	function doShow(recordId,parentId) {

		var url = '${ctx}/business/material?methodtype=detailView&parentId=' + parentId+'&recordId='+recordId;
		
		layer.open({
			offset :[10,''],
			type : 2,
			title : false,
			area : [ '1100px', '520px' ], 
			scrollbar : false,
			title : false,
			content : url,
			cancel: function(index){ 			
				layer.close(index);
			}    
		});	
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
					url : "${ctx}/business/material?methodtype=delete",
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

				<div id="TSupplier_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:left">
						<a class="DTTT_button DTTT_button_text" onclick="doSearch2('040');"><span>原材料</span></a>
						<a class="DTTT_button DTTT_button_text" onclick="doSearch2('020');"><span>通用件</span></a>
					</div>
					<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:right">
						<a class="DTTT_button DTTT_button_text" onclick="doCreate();"><span>开始采购（原材料、通用件）</span></a>
						<!-- <a class="DTTT_button DTTT_button_text" onclick="doDelete();"><span>删除</span></a> -->
					</div>
					<div id="clear"></div>
					<table style="width: 100%;" id="TMaterial" class="display">
						<thead>						
							<tr>
								<th style="width: 30px;" aria-label="No:" class="dt-middle ">No</th>
								<th style="width: 100px;" class="dt-middle ">物料编号</th>
								<th class="dt-middle">物料名称</th>
								<th style="width: 70px;" class="dt-middle">物料分类</th>
								<th style="width: 30px;" class="dt-middle ">单位</th>
								<th style="width: 70px;" class="dt-middle">当前库存</th>
								<th style="width: 60px;" class="dt-middle">安全库存</th>
								<th style="width: 80px;" class="dt-middle">供应商</th>
								<th style="width: 80px;" class="dt-middle">价格</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
