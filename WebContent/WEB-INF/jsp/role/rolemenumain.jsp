<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<body class="easyui-layout">
<div id="container">
	<div id="main">
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<!-- 翻页start -->
		<input type=hidden name="startIndex" id="startIndex" value=""/>
		<input type=hidden name="flg" id="flg" value="11111"/>
		<input type=hidden name="turnPageFlg" id="turnPageFlg" value=""/>
		<input type=hidden name="sortFieldList" id="sortFieldList" value="${DisplayData.sortFieldList}"/>
		<input type=hidden name="totalPages" value="${DisplayData.totalPages}"/>
		<!-- 翻页end -->
		<fieldset>
		<legend>角色菜单管理</legend>
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
				
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="callUpdateRoleMenu('', '', '');"><span>增加</span></a>
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="deleterolemenu();"><span>删除</span></a>
			</div>

			<table aria-describedby="TMould_info" style="width: 100%;" id="TMain" class="display dataTable" cellspacing="0" style="table-layout:fixed;">
				<thead>
					<tr class="selected">
						<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
						<th colspan="1" rowspan="1" style="width: 40px;" aria-label="角色名称:" class="dt-middle sorting_disabled">角色<br>名称</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="创建单位:" class="dt-middle sorting_disabled">创建单位</th>
						<th colspan="1" rowspan="1" style="width: 40px;" aria-label="创建人:" class="dt-middle sorting_disabled">创建人</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="创建时间:" class="dt-middle sorting_disabled">创建时间</th>
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
	var layerHeight = 500;
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
							{"data": "CREATEUNITID", "defaultContent" : '',"className" : 'td-left'},
							{"data": "LoginName", "defaultContent" : '',"className" : 'td-left'},
							{"data": "CreateTime", "defaultContent" : '',"className" : 'td-center'},	
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["UnitID"] + "' />"
		                    }},	
				    		{"targets":5,"render":function(data, type, row){
				    			return 	"<a href='javascript:void(0);' title='修改权限' onClick=\"callUpdateRoleMenu('" + row["RoleID"] + "', '" + row["RoleName"] + "', '" + row["MenuID"] + "');\">修改</a>"
		                    }}
			         ] 
			}
		);
	}

	$(function(){
		ajax();
		
		$('#form').attr("action", "${ctx}/rolemenu?methodtype=search");

		if ('${DisplayData.message}' != '') {
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

	function deleterolemenu() {

		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				isAnyOneChecked = true;
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				var actionUrl = "${ctx}/rolemenu?methodtype=delete";
				
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
			alert("请至少选择一个角色菜单项");
		}
	}

	function callUpdateRoleMenu(roleId, roleName, menuId) {
		//popupWindow("roleDetail", "${pageContext.request.contextPath}/rolemenu?methodtype=updateinit&roleId=" + roleId + "&roleName=" + roleName + "&menuId=" + menuId, 800, 600);
		var url = "${ctx}/rolemenu?methodtype=updateinit&roleId=" + roleId + "&roleName=" + roleName + "&menuId=" + menuId;
		openLayer(url, $(document).width() + 100, layerHeight, true);
	}
	function reload() {
		
		$('#TMain').DataTable().ajax.reload(null,false);
		
		return true;
	}

</script>
</html>