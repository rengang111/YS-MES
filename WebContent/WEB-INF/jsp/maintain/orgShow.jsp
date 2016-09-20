<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
<head>
<title>机构管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../common/common.jsp"%>
<script type="text/javascript">

function doSearch() {
	alert(2)
	ajax();
}
	
function ajax() {
	var t = $('#example')
		.DataTable({
			"paging": true,
			"lengthMenu":[25,50,100],//设置一页展示10条记录
			"processing" : false,
			"serverSide" : true,
			"stateSave" : false,
			"searching" : false,
			"pagingType" : "full_numbers",
			"retrieve" : true,
			"sAjaxSource" : "${pageContext.request.contextPath}/doSearch",
			"fnServerData" : function(sSource, aoData, fnCallback) {
				alert("fnServerData")
				var param = {};
				var formData = $("#condition").serializeArray();
				formData.forEach(function(e) {
					//alert("e.name:"+e.name+",e.value:"+e.value)
					aoData.push({"name":e.name, "value":e.value});
				});
				//aoData.push({"name":"keyword1", "value":""}, {"name":"keyword2", "value":""});
				$.ajax({
					"url" : sSource,
					"datatype": "text", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					"data" : JSON.stringify(aoData),
					success: function(data){ fnCallback(data) ; }
				})
			},
			"language":{ "url":"${pageContext.request.contextPath}/plugins/datatables/chinese.json"},
			"columns": [
				{"data": "OrgID", "defaultContent" : ''},
				{"data": null},
				{"data": "DicName", "defaultContent" : ''},
				{"data": "ShortName", "defaultContent" : ''},
				{"data": "FullName", "defaultContent" : ''},
				{"data": "Address", "defaultContent" : ''}
			],

			"columnDefs" : [ 
			    {"orderable" : true}, 
			    {"visible" : false,"targets" : [ 0 ]} 
			],
			"createdRow": function( row, data, dataIndex ) {
                $(row).children('td').eq(0).attr('style', 'text-align: center;')
            },
		}
	);
	
	t.on('order.dt search.dt draw.dt', function() {
		t.column(1, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			cell.innerHTML = i + 1;
		});
	}).draw();
	
};
	
	function initEvent(){
		
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
	}

	function reload() {
		$('#example').DataTable().ajax.reload(null,false);
	};
	
	$(document).ready(function() {

		ajax();
		initEvent();

		$("#retrieve").click(
				function() {
					alert("search0")
					ajax();
					//alert("search1")
				});
		
		$("#keywords1").keydown(function(e){
			
			var curKey = e.which;
			
			if(curKey == 13){
				ajax();
				$("#keywords2").focus();
				return false;
			}
		}); 
		
		$("#keywords2").keydown(function(e){
			
			var curKey = e.which;
			
			if(curKey == 13){
				ajax();
				return false;
			}
		}); 
	});



	//捕获键盘事件
	window.document.onkeydown = function keydownHandler(evt){
		evt = (evt) ? evt : window.event;
		if (evt.keyCode) {
			
			//上下键移动选择行
			keydownOption('#example',evt.keyCode);
			
   			if(evt.keyCode == 13 ){//回车键打开详细内容
   				selectOption('#example','${pageContext.request.contextPath}/company');
   			}
		}
	};
</script>
</head>
<body>
	<div id="container" width="100%">

		<!--主工作区,编辑页面或查询显示页面-->
		<div id="main">
		
			<!--查询条件输入区-->
			<div id="search">

				<form id="condition" style='padding: 0px; margin: 10px;' >

					<table class="search" >
						<tr>
							<td width="10%"></td>  
							<td class="label">关键字一：</td> 
							<td class="condition"><input type="text" id="keywords1"
								name="keywords1" class="middle"/></td>
							<td class="label">关键字二：</td> 
							<td class="condition"><input type="text" id="keywords2"
								name="keywords2" class="middle"/></td>
							<td>
								<button type="button" id="retrieve" class="DTTT_button" style="width:50px" value="查询" onClick="doSearch();"/>查询</td>
							<td width="10%"></td> 
						</tr>
					</table>
				</form>
			</div>

			<!--查询结果显示-->
			<div class="list">

				<!--查询结果显示-->
				<table id="example" class="display" cellspacing="0"  width="100%">
					<thead>
						<tr>
							<th style="width: 1px"></th>
							<th class="dt-center" style="width: 30px">序号</th>
							<th class="dt-left" style="width: 150px">机构类型</th>
							<th class="dt-left" style="width: 150px">机构简称</th>
							<th class="dt-left">机构全称</th>
							<th class="dt-left">详细地址</th>
						</tr>
					</thead>				
				</table>
			</div>
		</div>
	</div>
</body>
</html>
