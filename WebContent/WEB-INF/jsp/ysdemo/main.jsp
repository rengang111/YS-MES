<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../common/common.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctx}/css/all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.dataTables.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/dataTables.tableTools.css" />
<script type="text/javascript" src="${ctx}/js/jquery-2.1.3.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.dataTables.js"></script>



<title>Untitled Document</title>
<script type="text/javascript">

function doSearch() {


	ajax();
	//reload();
}

function reload() {

	$('#example').DataTable().ajax.reload(null,false);
};

function ajax() {
	var table = $('#example').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#example')
	.DataTable({
			"paging": true,
			"lengthMenu":[25,50,-1],//设置一页展示10条记录
			"processing" : false,
			"serverSide" : true,
			"stateSave" : false,
			"searching" : false,
			"pagingType" : "full_numbers",
			"retrieve" : true,
			"sAjaxSource" : "${pageContext.request.contextPath}/yssearch",
			"fnServerData" : function(sSource, aoData, fnCallback) {
				var param = {};
				var formData = $("#condition").serializeArray();
				formData.forEach(function(e) {
					alert(e.name);
					aoData.push({"name":e.name, "value":e.value});
				});
				
				//aoData.push({"name":"keyword1", "value":""}, {"name":"keyword2", "value":""});
				$.ajax({
					"url" : sSource,
					"datatype": "text", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					"data" : JSON.stringify(aoData),
					success: function(data){
						fnCallback(data);
						//$('#example').dataTable().fnClearTable(); 
	               		//$('#example').dataTable().fnAddData(data, true);
	               		//alert(jQuery.parseJSON(data));
					}
				})
			},
			/*
			"ajax" : {
				"url" : "${pageContext.request.contextPath}/yssearch",
				"datatype": "text", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : function(d) {
					var param = {};
					param.draw = d.draw;
					param.start = d.start;
					param.length = d.length;
					param.order = d.order;
					param.columns = d.columns;
					var formData = $("#condition").serializeArray();
					formData.forEach(function(e) {
						param[e.name] = e.value;
					});

					return JSON.stringify(param);
				},
				success: function(data){
					alert(data);
					//$('#example').dataTable().fnClearTable(); 
               		$('#example').dataTable().fnAddData(data, true);
               		//alert(jQuery.parseJSON(data));
				}			
        	},
        	*/
        	"oLanguage": {
        		"sLengthMenu": "每页显示 _MENU_条",
        		"sZeroRecords": "没有找到符合条件的数据",
        		"sProcessing": "&lt;img src=’./loading.gif’ /&gt;",
        		"sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
        		"sInfoEmpty": "木有记录",
        		"sInfoFiltered": "(从 _MAX_ 条记录中过滤)",
        		"sSearch": "搜索：",
        		"oPaginate": {
        		"sFirst": "首页",
        		"sPrevious": "前一页",
        		"sNext": "后一页",
        		"sLast": "尾页"
        		}
        	},
			"columns": [
						{"data": null, "defaultContent" : ''},
						{"data": "id", "defaultContent" : 222},
						{"data": "jiancheng", "defaultContent" : ''},
						{"data": "quancheng", "defaultContent" : ''},
						{"data": "zhulianxiren", "defaultContent" : ''},
						{"data": "shixian", "defaultContent" : ''},
						{"data": "chengjiao", "defaultContent" : ''}
			        ],
			"columnDefs":[
			    		{"targets":4,"render":function(data, type, row){
				    		//return data + "jyy";
							return "<a href=\"" + row["jiancheng"] + "\"> " + data + "</a>"
	                    }},
			    		{"targets":5,"render":function(data, type, row){
				    		//return data + "jyy";
							return "<a href=\"#\" > " + data + "</a>"
	                    }},
	                    {"targets":6,"render":function(data, type, row){
							return data + "lxf";
	                    }}
	                    
	         ] 
		}
	);
};

function initEvent(){
	
		ajax();
	
		$('#example').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#example').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
		
		
		$('#example').DataTable().on('dblclick', 'tr', function() {

			var d = $('#example').DataTable().row(this).data();

			location.href = '${pageContext.request.contextPath}/factory/show/' + d["factory_id"] + '.html';		
			
		});
	};
	
	function addTr(tab, row, trHtml){

     var $tr=$("#example tr").eq(-1);
     if($tr.size()==0){
        return;
     }
     $tr.after(trHtml);
  }
   
  function delTr(ckb){
     var ckbs=$("input[name="+ckb+"]:checked");
     if(ckbs.size()==0){
        return;
     }
           ckbs.each(function(){
              $(this).parent().parent().remove();
           });
  }
   
	
	$(document).ready(function() {
		//ajax();
		initEvent();
		
	});

	
	function selectOption(){
		var factory_id = "";		
		var selOption = $('#example').DataTable().rows('.selected').data();				
		if( selOption.length != 0 ){
			selOption.each(function(data) {
				factory_id = data["factory_id"];
			});
			location.href = '${pageContext.request.contextPath}/factory/show/' + factory_id + '.html';
		}		
	}
	
	window.document.onkeydown = function keydownHandler(evt){
		evt = (evt) ? evt : window.event;
		if (evt.keyCode) {
   			if(evt.keyCode == 38){
     			moveOption(false);
     			return false;
   			}
   			else if(evt.keyCode == 40){
   				moveOption(true);
   				return false;
   			}else if(evt.keyCode == 13 ){
   				selectOption();
   			}

		}
	};
	
	function moveOption(moveDown ){
		var selOption = $("#example").find("tr.selected");
		if( moveDown ){
			if( selOption.length ==0 ){
				//$("#example").find("tr").first().addClass("selected");
			}

			else if(selOption.next("tr").length != 0 ){
				selOption.removeClass("selected");
				selOption.next("tr").addClass("selected");
			}
			else{
				selOption.removeClass("selected");
				$("#example").find("tr:eq(2)").addClass("selected");
			}
		}
		else{

			if( selOption.length ==0 ){
				//$("#example").find("tr").last().addClass("selected");
			}
			else if(selOption.prev("tr").length != 0 ){
				selOption.removeClass("selected");
				selOption.prev("tr").addClass("selected");
			}
			else{
				selOption.removeClass("selected");
				$("#example").find("tr").last().addClass("selected");
			}
		}
	}
	
</script>

</head>

<body class="panel-body">
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition" 
					style='padding: 0px; margin: 10px;' >

					<table>
						<tr>
							<td width="10%"></td>
							<td class="label">关键字一：</td>
							<td class="condition">
								<input type="text" id="keyword1" name="keyword1" class="middle"/>
							</td>
							<td class="label">关键字二：</td> 
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

				<div id="example_wrapper" class="dataTables_wrapper">
					<div id="DTTT_container" align="right" style="height:40px">
						<a aria-controls="example" tabindex="0" id="ToolTables_example_0" class="DTTT_button DTTT_button_text"><span>新建</span></a>
						<a aria-controls="example" tabindex="0" id="ToolTables_example_1" class="DTTT_button DTTT_button_text"><span>删除</span></a>
					</div>
					<div id="clear"></div>
					<table aria-describedby="example_info" role="grid" style="width: 95%;" id="example" class="display dataTable" cellspacing="0">
						<thead>
							<tr class="selected" role="row">
								<th colspan="1" rowspan="1" class="sorting_disabled" aria-label="" style="width: 4px;"></th>
								<th colspan="1" rowspan="1" aria-controls="example" tabindex="0" style="width: 82px;" aria-label="工厂编号: activate to sort column ascending" class="dt-left sorting">工厂编号</th>
								<th colspan="1" rowspan="1" aria-controls="example" tabindex="0" style="width: 82px;" aria-label="工厂简称: activate to sort column ascending" class="dt-left sorting">工厂简称</th>
								<th colspan="1" rowspan="1" style="width: 433px;" aria-label="工厂全称" class="dt-left sorting_disabled">工厂全称</th>
								<th colspan="1" rowspan="1" style="width: 82px;" aria-label="主联系人" class="dt-left sorting_disabled">主联系人</th>
								<th colspan="1" rowspan="1" style="width: 82px;" aria-label="所在市县" class="dt-left sorting_disabled">所在市县</th>
								<th colspan="1" rowspan="1" style="width: 114px;" aria-label="是否成交" class="dt-left sorting_disabled">是否成交</th>
							</tr>
						</thead>

					</table>
				</div>
			</div>
		</div>
	</div>
</html>
