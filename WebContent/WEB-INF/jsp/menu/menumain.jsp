<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<body class="easyui-layout">
<div id="container">
	<div id="main">
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden id="operType" name="operType" value='${DisplayData.operType}'>
			<!-- 翻页start -->
			<input type=hidden name="startIndex" id="startIndex" value=""/>
			<input type=hidden name="flg" id="flg" value="11111"/>
			<input type=hidden name="turnPageFlg" id="turnPageFlg" value=""/>
			<input type=hidden name="sortFieldList" id="sortFieldList" value="${DisplayData.sortFieldList}"/>
			<input type=hidden name="totalPages" value="${DisplayData.totalPages}"/>
			<!-- 翻页end -->
			<fieldset>
				<legend>功能菜单管理</legend>
				<div style="height:5px"></div>
				<table width="100%">
					<tr>
						<td width=60px>
							上级菜单：
						</td>
						<td>
							<input type=text name="parentMenuIdName" id="parentMenuIdName" class="short" value="${DisplayData.parentMenuIdName}"/>
						</td>
						<td width=60px>
							菜单ID：
						</td>
						<td>
							<input type=text name="menuId" id="menuId" class="short" value="${DisplayData.menuId}"/>
						</td>
						<td width=60px>
							菜单名称：
						</td>
						<td>
							<input type=text name="menuName" id="menuName" class="short" value="${DisplayData.menuName}"/>
						</td>
						<td>
							<button type="button" id="edit" class="DTTT_button" onClick="doSearch();"
								style="height:25px;margin:0px 5px 0px 0px;" >查询</button>
						</td>
					</tr>
				</table>
			</fieldset>
			<div id="TMould_wrapper" class="list">
				<div id="DTTT_container" align="right" style="height:40px">
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="addMenu();"><span>新建</span></a>
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="deleteMenu();"><span>删除</span></a>
				</div>

				<div id="clear"></div>
			
				<table aria-describedby="TMould_info" style="width: 100%;" id="TMain" class="display dataTable" cellspacing="0" style="table-layout:fixed;">
					<thead>
						<tr class="selected">
							<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
							<th colspan="1" rowspan="1" style="width: 40px;" aria-label="菜单ID:" class="dt-middle sorting_disabled">菜单ID</th>
							<th colspan="1" rowspan="1" style="width: 85px;" aria-label="菜单名称:" class="dt-middle sorting_disabled">菜单名称</th>
							<th colspan="1" rowspan="1" style="width: 85px;" aria-label="上级菜单:" class="dt-middle sorting_disabled">上级菜单</th>
							<th colspan="1" rowspan="1" style="width: 150px;" aria-label="URL" class="dt-middle sorting_disabled">URL</th>
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
				"sAjaxSource" : "${ctx}/menu?methodtype=search",
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
							{"data": "MenuID", "defaultContent" : '',"className" : 'td-left'},
							{"data": "MenuName", "defaultContent" : '',"className" : 'td-left'},
							{"data": "parentMenuName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "MenuURL", "defaultContent" : '',"className" : 'td-left'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["MenuID"] + "' />"
		                    }},	
				    		{"targets":5,"render":function(data, type, row){
				    			return 	"<a href='javascript:void(0);' title='子菜' onClick=\"callAddSubMenu('" + row["MenuID"] + "');\">子菜单</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='详细信息' onClick=\"dispMenuDetail('" + row["MenuID"] + "');\">详细信息</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='修改' onClick=\"callUpdateMenu('" + row["MenuID"] + "');\">修改</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='关联角色' onClick=\"callRelationRole('" + row["MenuID"] + "');\">关联角色</a>"
		                    }}
			         ] 
			}
		);
	}
	
	$(function(){
		$('#form').attr("action", "${ctx}/menu?methodtype=search");

		ajax();
		
		var updateRecordCount = parseInt('${DisplayData.updatedRecordCount}');

		if (updateRecordCount > 0) {
			if ($('#operType').val() == 'add') {
				reloadTree('${DisplayData.menuData.menuparentid}');
			}
			if ($('#operType').val() == 'addsub') {
				reloadTree('${DisplayData.menuData.menuparentid}');
			}
			if ($('#operType').val() == 'update') {
				reloadTree('${DisplayData.menuData.menuparentid}');
			}
			if ($('#operType').val() == 'delete') {
				var dataList = '${DisplayData.numCheck}'.split(",");
				for(i = 0; i < dataList.length; i++) {
					removeNode(dataList[i]);
				}

				$('#menuId').val("");
				$('#menuName').val("");
			}
		}

		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	}); 
	function noticeNaviChanged(id, name, isLeaf) {

		if (isLeaf) {
			$('#parentMenuIdName').val("");	
			$('#menuId').val(id);
			$('#menuName').val(name);
		} else {
			$('#menuId').val("");
			$('#menuName').val("");
			$('#parentMenuIdName').val(name);	
		}

		//doSearch();
		ajax();
	}

	function inputCheck() {
		/*
		var str = $('#parentMenuIdName').val();
		if (!inputStrCheck(str, "上级功能", 7, 30, true, true)) {
			return false;
		}
		
		str = $('#menuId').val();
		if (!inputStrCheck(str, "菜单ID", 3, 8, true, true)) {
			return false;
		}
		
		str = $('#menuName').val();
		if (!inputStrCheck(str, "菜单名称", 30, 7, true, true)) {
			return false;
		}
		*/
		return true;
	}

	function doSearch() {

		if (inputCheck()) {
			//$('#form').attr("action", "${ctx}/menu?methodtype=search");
			//$('#form').submit();
			reload();
		}
	}

	function addMenu() {
		$('#operType').val("add");
		//popupWindow("MenuDetail", "${pageContext.request.contextPath}/menu?methodtype=updateinit&operType=add", 800, 600);
		
		var url = "${pageContext.request.contextPath}/menu?methodtype=updateinit&operType=add";
		openLayer(url, $(document).width() - 25, layerHeight, true);
	}
	
	function deleteMenu() {
		
		$('#operType').val("delete");
		var str = "";
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
				str += $(this).val() + ",";
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				var actionUrl = "${ctx}/menu?methodtype=delete";
				
				$.ajax({
					type : "POST",
					contentType : 'application/json',
					dataType : 'json',
					url : actionUrl,
					data : str,// 要提交的表单
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

	function callAddSubMenu(menuId) {
		$('#operType').val("addsub");
		//popupWindow("MenuDetail", "${pageContext.request.contextPath}/menu?methodtype=updateinit&operType=addsub&menuId=" + menuId, 800, 600);
		var url = "${pageContext.request.contextPath}/menu?methodtype=updateinit&operType=addsub&menuId=" + menuId;
		openLayer(url, $(document).width() - 25, layerHeight, true);
	}
	
	function dispMenuDetail(menuId) {
		//popupWindow("MenuDetail", "${pageContext.request.contextPath}/menu?methodtype=detail&menuId=" + menuId, 800, 600);
		var url = "${pageContext.request.contextPath}/menu?methodtype=detail&menuId=" + menuId;
		openLayer(url, $(document).width() - 25, layerHeight, true);

	}

	function callUpdateMenu(menuId) {
		$('#operType').val("update");
		//popupWindow("MenuDetail", "${pageContext.request.contextPath}/menu?methodtype=updateinit&operType=update&menuId=" + menuId, 800, 600);	
		var url = "${pageContext.request.contextPath}/menu?methodtype=updateinit&operType=update&menuId=" + menuId;
		openLayer(url, $(document).width() - 25, layerHeight, true);

	}

	function callRelationRole(menuId) {
		popupWindow("roleDetail", "${pageContext.request.contextPath}/rolemenu?methodtype=updateinit&roleId=" + "&roleName=" + "&menuId=", 800, 600);	
	}

	function removeNode(nodeId) {
		window.parent.removeNode(nodeId);
	}	
	function updateNode(nodeId, text, icon) {
		window.parent.updateNode(nodeId, text, icon);
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