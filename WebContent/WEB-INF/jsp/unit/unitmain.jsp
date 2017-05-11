<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>

</head>
<body class="easyui-layout">
<div id="container">
	<div id="main">
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden id="operType" name="operType" value='${DisplayData.operType}'>
			<input type=hidden name="userUnitId" id="userUnitId" value="${DisplayData.userUnitId}"/>
			<!-- 翻页start -->
			<input type=hidden name="startIndex" id="startIndex" value=""/>
			<input type=hidden name="flg" id="flg" value="11111"/>
			<input type=hidden name="turnPageFlg" id="turnPageFlg" value=""/>
			<input type=hidden name="sortFieldList" id="sortFieldList" value="${DisplayData.sortFieldList}"/>
			<input type=hidden name="totalPages" value="${DisplayData.totalPages}"/>
			<!-- 翻页end -->
			<fieldset>
			<legend>组织机构管理</legend>
			<div style="height:5px"></div>
			<table>
				<tr>
					<td>
						上级单位名称：
					</td>
					<td>
						<input type=text name="unitIdName" id="unitIdName" value="${DisplayData.unitIdName}"/>
					</td>
				</tr>
				<tr>
					<td>
						<input type=button name="search" id="search" value="查询" onClick="doSearch()"/>
					</td>
				</tr>
			</table>
			</fieldset>
			<div id="TMould_wrapper" class="list">
				<div id="DTTT_container" align="right" style="height:40px">
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="addUnit();"><span>增加机构</span></a>
					<!-- 
						<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="mergeUnit();"><span>合并</span></a>
					 -->
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="deleteUnit();"><span>删除机构</span></a>
				</div>
				
				<table aria-describedby="TMould_info" style="width: 100%;" id="TMain" class="display dataTable" cellspacing="0" style="table-layout:fixed;">
					<thead>
						<tr class="selected">
							<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
							<th colspan="1" rowspan="1" style="width: 40px;" aria-label="单位名称:" class="dt-middle sorting_disabled">单位名称</th>
							<th colspan="1" rowspan="1" style="width: 85px;" aria-label="单位编码:" class="dt-middle sorting_disabled">单位编码</th>
							<th colspan="1" rowspan="1" style="width: 85px;" aria-label="上级单位:" class="dt-middle sorting_disabled">上级单位</th>
							<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</form>
		
	</div>
</div>
</body>

<script>
	var layerHeight = 400;
	function ajax() {
		var table = $('#TMain').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TMain').DataTable({
				"paging": true,
				"lengthMenu":[10,20,50],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/unit?methodtype=search",
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var param = {};
					var formData = $("#form").serializeArray();
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
							/*
							if (data.message != undefined) {
								alert(data.message);
							}
							*/
							
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
							{"data": "UnitName", "defaultContent" : '',"className" : 'td-left'},
							{"data": "UnitID", "defaultContent" : '',"className" : 'td-left'},
							{"data": "parentUnitName", "defaultContent" : '',"className" : 'td-left'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["UnitID"] + "' />"
		                    }},	
				    		{"targets":4,"render":function(data, type, row){
				    			return 	"<a href='javascript:void(0);' title='增加子单位' onClick=\"callAddSubUnit('" + row["UnitID"] + "');\">子单位</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='详细信息' onClick=\"dispUnitDetail('" + row["UnitID"] + "');\">详细信息</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='修改' onClick=\"callUpdateUnit('" + row["UnitID"] + "');\">修改</a>"
		                    }}
			         ] 
			}
		);
	}
	
	$(function(){
		
		ajax();
		
		$('#form').attr("action", "${ctx}/unit?methodtype=search");

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
				var dataList = '${DisplayData.numCheck}'.split(",");
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

		$('#unitIdName').val(name);
		$('#search').click();

	}

	function inputCheck() {
		/*
		var str = $('#unitIdName').val();
		if (!inputStrCheck(str, "单位名称", 100, 7, true, true)) {
			return false;
		}
		*/
		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			reload();
		}
	}

	function addUnit() {
		$('#operType').val("add");
		//popupWindow("UnitDetail", "${pageContext.request.contextPath}/unit?methodtype=updateinit&operType=add", 800, 600);
		var url = "${pageContext.request.contextPath}/unit?methodtype=updateinit&operType=add";
		openLayer(url, $(document).width() + 100, layerHeight, true);
	}
	
	function deleteUnit() {
		
		$('#operType').val("delete");
		
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				var actionUrl = "${ctx}/unit?methodtype=delete";
				
				$.ajax({
					type : "POST",
					contentType : 'application/json',
					dataType : 'json',
					url : actionUrl,
					data : JSON.stringify($('#form').serializeArray()),// 要提交的表单
					success : function(d) {
						if (d.rtnCd != "000") {
							alert(d.message);	
						} else {
							reload();
							parent.loadData(d.info);
						}
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						//alert(XMLHttpRequest.status);					
						//alert(XMLHttpRequest.readyState);					
						//alert(textStatus);					
						//alert(errorThrown);
					}
				});
			}
		} else {
			alert("请至少选择一个菜单项");
		}
	}

	function callAddSubUnit(unitId) {
		$('#operType').val("addsub");
		//popupWindow("UnitDetail", "${pageContext.request.contextPath}/unit?methodtype=updateinit&operType=addsub&unitId=" + unitId, 800, 600);
		var url = "${pageContext.request.contextPath}/unit?methodtype=updateinit&operType=addsub&unitId=" + unitId;
		openLayer(url, $(document).width() - 25, layerHeight, true);
	}
	
	function dispUnitDetail(unitId) {
		//popupWindow("UnitDetail", "${pageContext.request.contextPath}/unit?methodtype=detail&unitId=" + unitId, 800, 600);
		var url = "${pageContext.request.contextPath}/unit?methodtype=detail&unitId=" + unitId;
		openLayer(url, $(document).width() - 25, layerHeight, true);
	}

	function callUpdateUnit(unitId) {
		$('#operType').val("update");
		//popupWindow("UnitDetail", "${pageContext.request.contextPath}/unit?methodtype=updateinit&operType=update&unitId=" + unitId, 800, 600);
		var url = "${pageContext.request.contextPath}/unit?methodtype=updateinit&operType=update&unitId=" + unitId;
		openLayer(url, $(document).width() - 25, layerHeight, true);
	}

	function mergeUnit(unitId) {
		//alert("等待实装...");
	}

	function removeNode(nodeId) {
		window.parent.removeNode(nodeId);
	}	
	function updateNode(parentNodeId, nodeId, text, icon) {
		//window.parent.updateNode(nodeId, text, icon);
		window.parent.removeNode(nodeId);
		window.parent.addNode(parentNodeId, nodeId, text, icon);
	}
	function addNode(parentNodeId, id, text, icon) {
		window.parent.addNode(parentNodeId, id, text, icon);
	}
	
	function reload() {
		
		$('#TMain').DataTable().ajax.reload(null,false);
		
		return true;
	}
</script>
</html>