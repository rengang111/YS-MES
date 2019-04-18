<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>仓库编码</title>
<script type="text/javascript">

	function ajax(type,sessionFlag) {
		var table = $('#warehouse').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var t = $('#warehouse').DataTable({
				"paging": true,
				"lengthChange":false,
				 "iDisplayLength" : 50,
				//"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
				"ordering "	:false,
				"searching" : false,
				"pagingType" : "full_numbers",
				//"scrollY":scrollHeight,
				//"scrollCollapse":true,
				//"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/warehouse?methodtype=warehouseCodeSearch&type="+type+"&sessionFlag="+sessionFlag,
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
					{"data": "codeId", "defaultContent" : ''},
					{"data": "codeType", "defaultContent" : ''},
					{"data": "codeName", "defaultContent" : ''},
					{"data": "remarks", "defaultContent" : ''},
					{"data": null, "defaultContent" : '',"className" : 'td-center'}
					
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			var multiLevel = Number(row['multiLevel']);//层级序号
		    			var space = "";
		    			for(var i=0;i<multiLevel;i++){
		    				space += "&nbsp;&nbsp;"
		    			}
						return space + row['codeId'];
                    }},
		    		{"targets":4,"render":function(data, type, row){
		    			var imgName = 'arrow_down';
		    			var rtn = "";
		    			rtn += "<a href=\"###\" onClick=\"doAddSub('" + row["codeId"] + "')\">增加子分类</a>";
		    			rtn += "&nbsp;&nbsp;";
		    			rtn += "<a href=\"###\" onClick=\"doEdit('" + row["codeId"] + "')\">修改</a>";
		    			rtn += "&nbsp;";
		    			rtn += "<a href=\"###\" onClick=\"doDelete('" + row["recordId"] + "')\">删除</a>";
		    			rtn += "&nbsp;";
		    			//rtn += '<input onClick="doUpdateSortNo(' + row["recordId"] + '，"D")" type="image" title="向下移" style="border: 0px;" src="${ctx}/images/'+imgName+'.png" />';
		    			//rtn += '<input onClick="doUpdateSortNo(' + row["recordId"] + '，"U")" type="image" title="向上移" style="border: 0px;" src="${ctx}/images/arrow_top.png" />';
		    			
		    			return rtn;
                    }},
                    {"bSortable": false, "aTargets": [ 0,1,2,3,4 ] 
                    }
			           
			     ] ,
			    // "aaSorting": [[ 4, "asc" ]]
			}
		);
		
	}

	
	function initEvent(){

		ajax('','true');
	
		$('#warehouse').DataTable().on(
				'click', 
				 'tr td:nth-child(1),td:nth-child(2), tr td:nth-child(3),td:nth-child(4)',
				function() {
				//	 $(this).parent().toggleClass("selected");
			if ( $(this).parent().hasClass('selected') ) {
	            $(this).parent().removeClass('selected');
	        }
	        else {
	        	$('#warehouse').DataTable().$('tr.selected').removeClass('selected');
	            $(this).parent().addClass('selected');
	        }
		});
	}

	$(document).ready(function() {
		initEvent();
		
	})	
	
	function doSearch() {
	
		ajax('','false');
	}
	
	function SelectSupplier(type){
		ajax(type,'false');
	}
	
	function doCreate() {
		var codeId = '0';//一级编码的父类
		var url = "${ctx}/business/warehouse?methodtype=addTopInit"+"&parentCodeId="+codeId;

		layerWidth  = '800px';
		layerHeight = '300px';

		layer.open({
			offset :[50,''],
			type : 2,
			title : false,
			area : [ layerWidth, layerHeight ], 
			scrollbar : false,
			title : false,
			content : url,
			cancel: function(index){ 
				  layer.close(index)
				 // productReciveAjax();
				 // document.getElementById('dingwei').scrollIntoView();
				  return false; 
			}    
		});
	}
	

	function doAddSub(codeId) {
	
		var url = "${ctx}/business/warehouse?methodtype=addTopInit"+"&codeId="+codeId;

		layerWidth  = '800px';
		layerHeight = '300px';

		layer.open({
			offset :[50,''],
			type : 2,
			title : false,
			area : [ layerWidth, layerHeight ], 
			scrollbar : false,
			title : false,
			content : url,
			cancel: function(index){ 
				layer.close(index);
				reload();
				return false; 
			}    
		});
	}
	
	function doEdit(codeId) {
		
		var url = "${ctx}/business/warehouse?methodtype=editWarehouseCode"+"&codeId="+codeId;

		layerWidth  = '800px';
		layerHeight = '300px';

		layer.open({
			offset :[50,''],
			type : 2,
			title : false,
			area : [ layerWidth, layerHeight ], 
			scrollbar : false,
			title : false,
			content : url,
			cancel: function(index){ 
				layer.close(index);
				reload();
				return false; 
			}    
		});
	}
	

	function doDelete(recordId) {
				
		if(confirm("删除后不能恢复，确定要删除数据吗？")) {
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : '',
				url : "${ctx}/business/warehouse?methodtype=deleteWarehouseCode"+"&recordId="+recordId,
				success : function(data) {
					var message = data['message'];
					if(message == 'SUCCESSMSG'){
						$().toastmessage('showWarningToast', "删除成功。");	
					}
					reload();
					
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			});
		}
	}


	function doUpdateSortNo(recordId,sortFlag) {
				
		if(confirm("删除后不能恢复，确定要删除数据吗？")) {
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : '',
				url : "${ctx}/business/warehouse?methodtype=updateSortNo"+"&recordId="+recordId+"&sortFlag="+sortFlag,
				success : function(data) {
					var message = data['message'];
					if(message == 'SUCCESSMSG'){
						//$().toastmessage('showWarningToast', "删除成功。");	
					}
					reload();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			});
		}
	}
	
	function reload() {
		
		$('#warehouse').DataTable().ajax.reload(null,false);
		
		return true;
	}
	
</script>

</head>

<body>
<div id="container">

		<form id="condition"  style='padding: 0px; margin: 10px;' >
<!-- 
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
 -->
		</form>
	<div class="list">

			<div id="DTTT_container" align="right" style="">
				<a class="DTTT_button " onclick="doCreate();"><span>新建一级分类</span></a>
			</div>
			<div id="clear"></div>
			<table id="warehouse" class="display" >
				<thead>
					<tr>
						<th style="width:200px;">库位区分</th>
						<th style="width:200px;">产品类别</th>
						<th style="width:200px;" >名称</th>
						<th>备注</th>
						<th style="width:100px;">操作</th>
					</tr>
				</thead>

			</table>
	</div>
</div>
</body>
</html>
