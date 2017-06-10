<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<body class="easyui-layout">
<div id="container">
	<div id="main">
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type=hidden id="operType" name="operType" value='${DisplayData.operType}'>
		<input type=hidden name="userId" id="userId" value="${DisplayData.userId}"/>
		<input type=hidden name="duttiesId" id="duttiesId" value=""/>
		<!-- 翻页start -->
		<input type=hidden name="startIndex" id="startIndex" value=""/>
		<input type=hidden name="flg" id="flg" value="11111"/>
		<input type=hidden name="turnPageFlg" id="turnPageFlg" value=""/>
		<input type=hidden name="sortFieldList" id="sortFieldList" value="${DisplayData.sortFieldList}"/>
		<input type=hidden name="totalPages" value="${DisplayData.totalPages}"/>
		<!-- 翻页end -->
		<fieldset>
		<legend>用户管理</legend>
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
					职务：
				</td>
				<td>
					<input type=text name="dutiesIdName" id="dutiesIdName" value="${DisplayData.dutiesIdName}"/>
				</td>
				<td>
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="doSearch();"><span>查询</span></a>
				</td>
			</tr>
		</table>
		</fieldset>		
		
		<div id="TMould_wrapper" class="list">
			<div id="DTTT_container" align="right" style="height:40px">
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="addUser();"><span>增加用户</span></a>
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="deleteUser();"><span>删除用户</span></a>
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="setPower();"><span>授权</span></a>
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="lockUser();"><span>锁定</span></a>
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="unlockUser();"><span>解锁</span></a>
			</div>		

			<table aria-describedby="TMould_info" style="width: 100%;" id="TMain" class="display dataTable" cellspacing="0">
				<thead>
					<tr class="selected">
						<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
						<th colspan="1" rowspan="1" style="width: 40px;" aria-label="头像:" class="dt-middle sorting_disabled">头像</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="单位名称:" class="dt-middle sorting_disabled">单位名称</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="用户ID:" class="dt-middle sorting_disabled">用户ID</th>
						<th colspan="1" rowspan="1" style="width: 40px;" aria-label="用户名称:" class="dt-middle sorting_disabled">用户名称</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="职务:" class="dt-middle sorting_disabled">职务</th>
						<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</form>

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
			"sAjaxSource" : "${ctx}/user?methodtype=search",
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
						{"data": "photo", "defaultContent" : '',"className" : 'td-center'},
						{"data": "UnitId", "defaultContent" : '',"className" : 'td-left'},
						{"data": "LoginID", "defaultContent" : '',"className" : 'td-left'},
						{"data": "LoginName", "defaultContent" : '',"className" : 'td-center'},
						{"data": "Duty", "defaultContent" : '',"className" : 'td-center'},
						{"data": null, "defaultContent" : '',"className" : 'td-center'}
			        ],
			"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["UserID"] + "' />"
	                    }},	
			    		{"targets":1,"render":function(data, type, row){
							return "<img src='${ctx}" + row["photo"] + "' width=20px height=25px />"
	                    }},	
			    		{"targets":6,"render":function(data, type, row){
			    			return 	"<a href='javascript:void(0);' title='授权' onClick=\"setPower('" + row["UserID"] + "');\">授权</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='详细信息' onClick=\"dispUserDetail('" + row["UserID"] + "');\">详细信息</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='修改' onClick=\"callUpdateUser('" + row["UserID"] + "');\">修改</a>"
	                    }}
		         ] 
		}
	);
}

	$(function(){
		
		ajax();
		autoComplete();
				
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
		str = $('#dutiesIdName').val();
		if (!inputStrCheck(str, "职务", 60, 7, true, true)) {
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

	function addUser() {
		$('#operType').val("add");
		//popupWindow("UserDetail", "${pageContext.request.contextPath}/user?methodtype=updateinit&operType=add", 800, 600);
		var url = "${ctx}/user?methodtype=updateinit&operType=add";
		openLayer(url, $(document).width() + 100, layerHeight, true);
	}
	
	function deleteUser() {
		
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
				var actionUrl = "${ctx}/user?methodtype=delete";
				
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
	
	function dispUserDetail(userId) {
		//popupWindow("UserDetail", "${pageContext.request.contextPath}/user?methodtype=detail&userId=" + userId, 800, 600);
		var url = "${ctx}/user?methodtype=detail&userId=" + userId;
		openLayer(url, $(document).width() - 25, layerHeight, true);
	}

	function callUpdateUser(userId) {
		$('#operType').val("update");
		//popupWindow("UserDetail", "${pageContext.request.contextPath}/user?methodtype=updateinit&operType=update&userId=" + userId, 800, 600);	
		var url = "${ctx}/user?methodtype=updateinit&operType=update&userId=" + userId;
		openLayer(url, $(document).width() - 25, layerHeight, true);
	}

	function setPower(userId) {
		if (userId != '') {
			//popupWindow("PowerDetail", "${pageContext.request.contextPath}/power?methodtype=updateinit&operType=addviauser&userId=" + userId, 800, 600);
			var url = "${ctx}/power?methodtype=updateinit&operType=addviauser&userId=" + userId;
			openLayer(url, $(document).width() - 25, layerHeight, true);
		} else {
			var userIdList = "";
			var isFirst = true;
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					if (isFirst) {
						isFirst = false;
						userIdList = $(this).val();
					} else {
						userIdList += "," + $(this).val();
					}
				}
			});
			if (userIdList != "") {
				//popupWindow("PowerDetail", "${pageContext.request.contextPath}/power?methodtype=updateinit&operType=addviauser&userId=" + userIdList, 800, 600);
				var url = "${ctx}/power?methodtype=updateinit&operType=addviauser&userId=" + userIdList;
				openLayer(url, $(document).width() - 25, layerHeight, true);
			} else {
				alert("请至少选择一个用户");
			}
		}
	}
	function lockUser() {
		$('#operType').val("lock");
		
		var actionUrl = "${ctx}/user?methodtype=lock";
		
		var userIdList = "";
		var isFirst = true;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				if (isFirst) {
					isFirst = false;
					userIdList = $(this).val();
				} else {
					userIdList += "," + $(this).val();
				}
			}
		});
		if (userIdList != "") {
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
		} else {
			alert("请至少选择一个用户");
		}
	}
	function unlockUser() {
		$('#operType').val("unlock");
		var actionUrl = "${ctx}/user?methodtype=unlock";
		
		var userIdList = "";
		var isFirst = true;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				if (isFirst) {
					isFirst = false;
					userIdList = $(this).val();
				} else {
					userIdList += "," + $(this).val();
				}
			}
		});
		if (userIdList != "") {
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
		} else {
			alert("请至少选择一个用户");
		}
	}
	function selectDuties() {
		callDicSelect("duttiesId", "dutiesIdName", 'A3', '1' );
	}
	
	function reload() {
		
		$('#TMain').DataTable().ajax.reload(null,false);
		
		return true;
	}

	function autoComplete() {

		$("#dutiesIdName").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/user?methodtype=dutiesIdNameSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								return {
									label : item.dicName,
									value : item.dicName,
									id : item.dicId,
								}
							}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},

			select : function(event, ui) {
				$("#dutiesIdName").val(ui.item.value);
				$("#duttiesId").val(ui.item.id);
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#dutiesIdName").val("");
    				$("#duttiesId").val("");
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