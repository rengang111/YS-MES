<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<body>
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
			<legend>角色管理</legend>
			<div style="height:5px"></div>
			<table>
				<tr>
					<td>
						菜单：
					</td>
					<td colspan=3>
						<input type=text name="menuIdName" id="menuIdName" value="${DisplayData.menuIdName}"/>
					</td>
					<td>
						角色名称：
					</td>
					<td>
						<input type=text name="roleIdName" id="roleIdName" value="${DisplayData.roleIdName}"/>
					</td>
					<td>
						关联用户：
					</td>
					<td>
						<input type=text name="userIdName" id="userIdName" value="${DisplayData.userIdName}"/>
					</td>
					<td>
						<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="doSearch();"><span>查询</span></a>
					</td>
				</tr>
			</table>
			</fieldset>
			<div id="TMould_wrapper" class="list">
				<div id="DTTT_container" align="right" style="height:40px">
					
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="addrole();"><span>增加</span></a>
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="setPower('');"><span>授权</span></a>
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="deleterole();"><span>删除</span></a>
				</div>
			<table aria-describedby="TMould_info" style="width: 100%;" id="TMain" class="display dataTable" cellspacing="0" style="table-layout:fixed;">
				<thead>
					<tr class="selected">
						<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
						<th colspan="1" rowspan="1" style="width: 40px;" aria-label="角色名称:" class="dt-middle sorting_disabled">角色<br>名称</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="关联用户:" class="dt-middle sorting_disabled">关联用户</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="管理员:" class="dt-middle sorting_disabled">管理员</th>
						<th colspan="1" rowspan="1" style="width: 40px;" aria-label="创建单位:" class="dt-middle sorting_disabled">创建<br>单位</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="创建人:" class="dt-middle sorting_disabled">创建人</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="创建时间:" class="dt-middle sorting_disabled">创建时间</th>
						<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
						
					</tr>
				</thead>
			</table>
		</form>
	</div>
</div>
</body>

<script>
	var layerHeight = 200;
	function ajax() {
		var table = $('#TMain').dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#TMain').DataTable({
				"paging": true,
				"lengthMenu":[50,100,200],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/role?methodtype=search",
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
							{"data": "RoleName", "defaultContent" : '',"className" : 'td-left'},
							{"data": "relationUserName", "defaultContent" : '',"className" : 'td-left'},
							{"data": "RoleType", "defaultContent" : '',"className" : 'td-center'},
							{"data": "CREATEUNITID", "defaultContent" : '',"className" : 'td-center'},
							{"data": "LoginName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "CreateTime", "defaultContent" : '',"className" : 'td-center'},	
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["RoleID"] + "' />"
		                    }},	
				    		{"targets":7,"render":function(data, type, row){
				    			return 	"<a href='javascript:void(0);' title='详细信息' onClick=\"dispRoleDetail('" + row["RoleID"] + "');\">详细信息</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='修改' onClick=\"callUpdateRole('" + row["RoleID"] + "');\">修改</a>" + "&nbsp;" +	"<a href='javascript:void(0);' title='菜单' onClick=\"callMenu('" + row["RoleID"] + "', '" + row["RoleName"] + "');\">菜单</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='关联用户' onClick=\"dispRelationUser('" + row["RoleID"] + "');\">关联用户</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='授权' onClick=\"setPower('" + row["RoleID"] + "');\">授权</a>"
		                    }}
			         ] 
			}
		);
	}
	$(function(){
		
		ajax();
		
		$('#form').attr("action", "${ctx}/role?methodtype=search");

		var updateRecordCount = parseInt('${DisplayData.updatedRecordCount}');

		if ('${DisplayData.message}' != '' && '${DisplayData.operType}' != 'add') {
			alert('${DisplayData.message}');
		}
		
	}); 

	function noticeNaviChanged(id, name, isLeaf) {
		$('#menuIdName').val(name);
		doSearch();
	}
	
	function inputCheck() {
		/*
		var str = $('#menuIdName').val();
		if (!inputStrCheck(str, "菜单", 30, 7, true, true)) {
			return false;
		}
		
		str = $('#roleIdName').val();
		if (!inputStrCheck(str, "角色名称", 60, 7, true, true)) {
			return false;
		}
		
		str = $('#userIdName').val();
		if (!inputStrCheck(str, "关联用户", 32, 7, true, true)) {
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

	function addrole() {
		$('#operType').val("add");
		//popupWindow("roleDetail", "${ctx}/role?methodtype=updateinit&operType=add", 800, 600);
		var url = "${ctx}/role?methodtype=updateinit&operType=add";
		openLayer(url, $(document).width() + 100, layerHeight, true);
	}
	
	function deleterole() {
		
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
				var actionUrl = "${ctx}/role?methodtype=delete";
				
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
			alert("请至少选择一个角色项");
		}
	}
	
	function dispRoleDetail(roleId) {
		//popupWindow("roleDetail", "${ctx}/role?methodtype=detail&roleId=" + roleId, 800, 600);
		var url = "${ctx}/role?methodtype=detail&roleId=" + roleId;
		openLayer(url, $(document).width() - 25, layerHeight, true);
	}

	function callUpdateRole(roleId) {
		$('#operType').val("update");
		//popupWindow("roleDetail", "${ctx}/role?methodtype=updateinit&operType=update&roleId=" + roleId, 800, 600);	
		
		var url = "${ctx}/role?methodtype=updateinit&operType=update&roleId=" + roleId;
		openLayer(url, $(document).width() - 25, layerHeight, true);

	}

	function dispRelationUser(roleId) {
		//popupWindow("roleRelationUser", "${ctx}/role?methodtype=rolerelationuser&roleId=" + roleId, 800, 600);
		var url = "${ctx}/role?methodtype=rolerelationuser&roleId=" + roleId;
		openLayer(url, $(document).width() - 25, layerHeight + 300, true);
	}
	
	function callMenu(roleId, roleName) {
		//popupWindow("roleDetail", "${pageContext.request.contextPath}/rolemenu?methodtype=updateinit&roleId=" + roleId + "&roleName=" + roleName + "&menuId=", 800, 600);	
		var url = "${pageContext.request.contextPath}/rolemenu?methodtype=updateinit&roleId=" + roleId + "&roleName=" + roleName + "&menuId=";
		openLayer(url, $(document).width() - 25, layerHeight + 300, true);

	}

	function setPower(roleId) {
		if (roleId != '') {
			//popupWindow("PowerDetail", "${ctx}/power?methodtype=updateinit&operType=addviarole&roleId=" + roleId, 800, 600);
			var url = "${ctx}/power?methodtype=updateinit&operType=addviarole&roleId=" + roleId;
			openLayer(url, $(document).width() - 25, layerHeight + 350, true);
		} else {
			var roleIdList = "";
			var isFirst = true;
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					if (isFirst) {
						isFirst = false;
						roleIdList = $(this).val();
					} else {
						roleIdList += "," + $(this).val();
					}
				}
			});
			if (roleIdList != "") {
				//popupWindow("PowerDetail", "${ctx}/power?methodtype=updateinit&operType=addviarole&roleId=" + roleIdList, 800, 600);
				var url = "${ctx}/power?methodtype=updateinit&operType=addviarole&roleId=" + roleIdList + roleId;
				openLayer(url, $(document).width() - 25, layerHeight, true);
			} else {
				alert("请至少选择一个角色项");
			}
		}
	}
	function reload() {
		
		$('#TMain').DataTable().ajax.reload(null,false);
		
		return true;
	}
</script>
</html>