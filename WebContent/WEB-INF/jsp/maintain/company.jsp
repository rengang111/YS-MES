<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
<head>
<title>公司信息维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../common/common.jsp"%>

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

<body class="easyui-layout">
<div id="container">

		<!--主工作区,编辑页面或查询显示页面-->
		<div id="main">		
		
		<div class="form" style="overflow:hidden;zoom:1;">
		
			<c:url var="saveUrl" value="/factory/update.html" />
			<form:form modelAttribute="company" method="POST" id="company" action=""  >
			
				
				
				<fieldset>
					<legend>机构管理-编辑</legend>

					<table class="form" width="100%">
						<tr>
							<td width="80px">
								<label>机构类别：</label></td>
							<td colspan="3">								
									<form:input path="compName" class="short" /></td></tr>
						<tr>
							<td>
								<label>机构简称：</label></td>
							<td>
								<form:input path="compName" class="short" /></td>
							<td width="80px">
								<label>机构全称：</label></td>
							<td>								
								<form:input path="compFullname" class="long" /></td></tr>
						<tr>
							<td>								
								<label>详细地址：</label></td>
							<td colspan="3">								
								<form:input path="address" class="long"/></td>
						</tr>	
					</table>
				</fieldset>
				
				<fieldset class="action">
					<button type="button" id="return" class="DTTT_button">返回</button>
					<button type="reset" id="reset" class="DTTT_button">重置</button>
					<button type="submit" id="submit" class="DTTT_button">保存</button>
				</fieldset>

			</form:form>
					
		<div style="clear:both"></div>
		
		<div>
			</div>
		
		</div>
		</div>
	</div>
</body>
</html>
