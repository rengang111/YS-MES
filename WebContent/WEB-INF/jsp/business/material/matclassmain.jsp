<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML >
<html>
<%@ include file="../../common/common2.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>	物料分类管理</title>
</head>
<body class="panel-body">
<div id="container">
<div id="main">

	<form name="form" id="form" modelAttribute="dataModels" action="" method="post"
	 style='padding: 0px; margin: 10px;'>
		<input type="hidden" id="operType" name="operType" value='${DisplayData.operType}'/>
		<input type="hidden" id="parentCategoryName" name="parentCategoryName" value=''/>
		<input type="hidden" name="userCategoryId" id="userCategoryId" value="${DisplayData.userCategoryId}"/>

		<div id="search">
		<table style="height: 50px;">
			<tr>
				<td class="label" width="20%">
					关键字1：
				</td>
				<td class="condition">
					<input type="text" name="categoryName" id="categoryName" class="middle" value="${DisplayData.categoryName}"/>
				</td>
				<td>
					<button type="button" id="retrieve"
						onclick="doSearch()" class="DTTT_button" />查询
				</td>
			</tr>
		</table>
		</div>
			<div  style="height:10px"></div>
		
			<div class="list">
		<table width="100%" cellspacing="0" >
			<tr>
				<td colspan="6" align="right">
					<!-- input type=button name="add" id="add" value="新建子分类" onClick="addUnit()"/ -->
					<!-- input type=button name="merge" id="merge" value="合并" onClick="mergeUnit()"/ -->
					<button type="button"  id="delete" 
						onclick="deleteUnit()" class="DTTT_button"/>删除分类
				</td>
			</tr>
			</table>
		<table width="100%"  class="display dataTable" cellspacing="0" id="example" >
			<thead>
			<tr class="selected">
				<td width="1px" style="display:none"></td>
				<td width="30px">序号</td>
				<td width="120px">分类编码</td>
				<td width="200px">分类名称</td>
				<td width="100px">上级单位</td>
				<td width="160px">规格说明</td>
				<td width="80px">操作</td>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${DisplayData.viewData}" var="value" varStatus="status">
				<tr>
					<td style="display:none">
						<input type="checkbox" name="numCheckNode" id="numCheckNode" value='${value[1]}' />						
					</td>
					<td>
						${value[0]}<input type="checkbox" name="numCheck" id="numCheck" value='${value[5]}'/>						
					</td>
					<td>
						${value[1]}
					</td>
					<td>
						${value[2]}
					</td>
					<td>
						${value[4]}
					</td>
					<td>
						${value[6]}
					</td>
					<td>
						<!--  href="javascript:void(0);" title="查看"  onClick="dispUnitDetail('${value[5]}');">查看</a-->
						<a href="javascript:void(0);" title="修改" 
							onclick="callUpdateUnit('${value[4]}','${value[5]}');">修改</a>
						<a href="javascript:void(0);" title="增加子分类" 
							onclick="callAddSubUnit('${value[1]}','${value[2]}');">增加子分类</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
	</form>
</div>
</div>
<script type="text/javascript">

	var layerHeight = '400';
	var layerWidth = '700';

	$(function(){
		$('#form').attr("action", "${ctx}/business/matcategory?methodtype=search");

		var updateRecordCount = parseInt('${DisplayData.updatedRecordCount}');
		if (updateRecordCount > 0) {
			if ($('#operType').val() == 'add') {
				reloadTree('${DisplayData.unitData.parentid}');
			}
			if ($('#operType').val() == 'addsub') {
				reloadTree('${DisplayData.unitData.parentid}');
			}
			if ($('#operType').val() == 'update') {
				reloadTree('${DisplayData.unitData.parentid}');
			}
			if ($('#operType').val() == 'delete') {
				var dataList = '${DisplayData.numCheckNode}'.split(",");
				for(i = 0; i < dataList.length; i++) {
					removeNode(dataList[i]);
				}
			}
		}

		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 
	function noticeNaviChanged(id, name, isLeaf) {
		//var infoList = name.split("_");
		//alert(name+':name')
		if( typeof name =='undefined')
			return;
		$('#userCategoryId').val(id);
		$('#categoryName').val(name);
		
		$('#retrieve').click();

	}

	function doSearch() {		
		$('#form').attr("action", "${ctx}/business/matcategory?methodtype=search");
		$('#form').submit();
	}

	function addUnit() {
		$('#operType').val("add");
		popupWindow("UnitDetail", "${pageContext.request.contextPath}/business/matcategory?methodtype=updateinit&operType=add", 800, 500);	
	}
	
	function deleteUnit() {
		
		$('#operType').val("delete");
		
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(index, domEle){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
				$("input[name='numCheckNode']").eq(index).attr('checked', 'true');
				//通过可见的复选框选中后,自动选中隐藏的CategoryID复选框,
				//以便删除后,目录树可以自动更新;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				$('#form').attr("action", "${ctx}/business/matcategory?methodtype=delete");
				$('#form').submit();
			}
		} else {
			alert("请至少选择一个菜单项");
		}
	}

	function callAddSubUnit(categoryId,parentName) {
		$('#operType').val("addsub");
		var url = "${ctx}/business/matcategory?methodtype=updateinit&operType=addsub&categoryId=" + categoryId + "&parentName=" + encodeURIComponent(parentName);
		openLayer(url, layerWidth, layerHeight, false,'100px');
	}
	
	function dispUnitDetail(recordId) {
		var url = "${ctx}/business/matcategory?methodtype=detail&recordId=" + recordId
		openLayer(url, layerWidth, layerHeight, true,'100px');
	}

	function callUpdateUnit(parentName,recordId) {
		$('#operType').val("update");
		var url = "${ctx}/business/matcategory?methodtype=updateinit&operType=update&recordId=" + recordId+ "&parentName=" + encodeURI(encodeURI(parentName));
		openLayer(url, layerWidth, layerHeight, false,'100px');
	}

	function mergeUnit(categoryId) {
		alert("等待实装...");
	}

	function removeNode(nodeId) {
		window.parent.removeNode(nodeId);
	}	
	function updateNode(parentNodeId, nodeId, text, icon) {
		window.parent.removeNode(nodeId);
		window.parent.addNode(parentNodeId, nodeId, text, icon);
	}
	function addNode(parentNodeId, id, text, icon) {
		window.parent.addNode(parentNodeId, id, text, icon);
	}

$(document).ready( function () {  

	var scrollHeight = $(window).height() - 207; 
	
	var t = $('#example').DataTable({
			"paging": true,
			 "iDisplayLength" : 100,
			//"lengthMenu":[50,100,200],//设置一页展示100条记录
			"searching" : false,
			"lengthChange":false,
			//"scrollY":scrollHeight,
			//"scrollCollapse":true,
    	  	"language": {"url":"${ctx}/plugins/datatables/chinese.json"},

    	  	"columnDefs" : [ 
			                 {
						"orderable" : false,
						"targets" : [6] 
					}, {
						"visible" : false,
						"targets" : [0,4]
					} ]
	});   
      
     t.on('click', 'tr', function() {
    	 
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            
	        }
	});
});
</script> 
</body>
</html>