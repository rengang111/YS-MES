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
		<legend>权限管理</legend>
		<div style="height:5px"></div>
		<table>
			<tr>
				<td>
					单位名称：
				</td>
				<td>
					<input type=text name="unitIdName" id="unitIdName" value="${DisplayData.unitIdName}"/>
				</td>
				<td>
					用户名称：
				</td>
				<td>
					<input type=text name="userIdName" id="userIdName" value="${DisplayData.userIdName}"/>
				</td>
				<td>
					角色：
				</td>
				<td>
					<input type=text name="roleIdName" id="roleIdName" value="${DisplayData.roleIdName}"/>
					<input type=hidden name="roleId" id="roleId"/>
				</td>
				<td>
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="doSearch();"><span>查询</span></a>
				</td>
			</tr>
		</table>
		</fieldset>
		
		<div id="TMould_wrapper" class="list">
			<div id="DTTT_container" align="right" style="height:40px">
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="addPower();"><span>授权</span></a>
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="deletePower('');"><span>删除授权</span></a>
			</div>	
	
			<table aria-describedby="TMould_info" style="width: 100%;" id="TMain" class="display dataTable" cellspacing="0">
				<thead>
					<tr class="selected">
						<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="单位名称:" class="dt-middle sorting_disabled">单位名称</th>
						<th colspan="1" rowspan="1" style="width: 40px;" aria-label="用户名称:" class="dt-middle sorting_disabled">用户名称</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="角色:" class="dt-middle sorting_disabled">角色</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="管理员:" class="dt-middle sorting_disabled">是否管理员</th>
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
var layerHeight = 550;
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
			"sAjaxSource" : "${ctx}/power?methodtype=search",
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
						{"data": "UnitName", "defaultContent" : '',"className" : 'td-center'},
						{"data": "LoginName", "defaultContent" : '',"className" : 'td-left'},
						{"data": "RoleName", "defaultContent" : '',"className" : 'td-left'},
						{"data": "RoleType", "defaultContent" : '',"className" : 'td-center'},
						{"data": null, "defaultContent" : '',"className" : 'td-center'}
			        ],
			"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
	                    }},	
			    		{"targets":5,"render":function(data, type, row){
			    			return 	"<a href='javascript:void(0);' title='修改' onClick=\"updatePower('" + row["id"] + "');\">修改</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='删除' onClick=\"deletePower('" + row["id"] + "');\">删除</a>"
	                    }}
		         ] 
		}
	);
}
	$(function(){
		
		ajax();
		autoComplete();
		
		$('#form').attr("action", "${ctx}/power?methodtype=search");
				
	});

	function noticeNaviChanged(id, name, isLeaf) {

		$('#unitIdName').val(name);
		doSearch();
	}
	
	function inputCheck() {
		/*
		var str = $('#unitIdName').val();
		if (!inputStrCheck(str, "单位名称", 100, 7, true, true)) {
			return false;
		}
		str = $('#userIdName').val();
		if (!inputStrCheck(str, "用户名称", 20, 7, true, true)) {
			return false;
		}
		str = $('#roleIdName').val();
		if (!inputStrCheck(str, "角色", 60, 7, true, true)) {
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

	function addPower() {
		$('#operType').val("add");
		//popupWindow("PowerDetail", "${ctx}/power?methodtype=updateinit&operType=add", 800, 600);
		var url = "${ctx}/power?methodtype=updateinit&operType=add";
		openLayer(url, $(document).width() - 250, layerHeight, true);
	}
	
	function deletePower(id) {
		
		$('#operType').val("delete");
		var str = "";	
		var isAnyOneChecked = false;
		$("input[name='numCheck']").each(function(){
			if (id != '') {
				if ($(this).val() == id) {
					$(this).prop('checked', true);
					isAnyOneChecked = true;
					str += $(this).val() + ",";
				} else {
					$(this).prop('checked', false);
				}
			} else {
				if ($(this).prop('checked')) {
					isAnyOneChecked = true;
					str += $(this).val() + ",";
				}
			}
		});
		if (isAnyOneChecked) {
			if(confirm("确定要删除数据吗？")) {
				var actionUrl = "${ctx}/power?methodtype=delete";
				
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

	function updatePower(id) {
		$('#operType').val("update");
		//popupWindow("PowerDetail", "${pageContext.request.contextPath}/power?methodtype=updateinit&operType=update&id=" + id, 800, 600);
		var url = "${ctx}/power?methodtype=updateinit&operType=update&id=" + id;
		openLayer(url, $(document).width() - 250, layerHeight, true);
	}

	function selectRole() {
		callRoleSelect("roleId", "roleIdName");
	}

	function noticeChanged() {

	}
	
	function reload() {
		
		$('#TMain').DataTable().ajax.reload(null,false);
		
		return true;
	}

	function autoComplete() {

		$("#roleIdName").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/power?methodtype=roleIdNameSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								return {
									label : item.RoleName + ' | ' + item.RoleType,
									value : item.RoleName,
									id : item.roleid,
								}
							}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},

			select : function(event, ui) {
				$("#roleIdName").val(ui.item.value);
				$("#roleId").val(ui.item.id);
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#roleIdName").val("");
    				$("#roleId").val("");
                }
            },
			
			minLength : 1,
			autoFocus : false,
			width: 200,
			mustMatch:true,
		});
	}
</script>
</html>