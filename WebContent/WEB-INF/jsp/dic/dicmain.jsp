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
		<legend>字典管理</legend>
		<div style="height:5px"></div>
		<table>
			<tr>
				<td>
					代码类：
				</td>
				<td>
					<input type=text name="dicTypeIdName" id="dicTypeIdName" value="${DisplayData.dicTypeIdName}"/>
					<input type=hidden name="dicTypeId" id="dicTypeId"/>
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="selectDicType();"><span>选择</span></a>
				</td>
				<td>
					代码名称：
				</td>
				<td>
					<input type=text name="dicIdName" id="dicIdName" value="${DisplayData.dicIdName}"/>
				</td>
				<td>
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="doSearch();"><span>查询</span></a>
				</td>
			</tr>
		</table>
		</fieldset>
		
		<div id="TMould_wrapper" class="list">
			<div id="DTTT_container" align="right" style="height:40px">
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="addDicCode();"><span>增加</span></a>
				<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="deleteDicCode();"><span>删除</span></a>
			</div>	
	
			<table aria-describedby="TMould_info" style="width: 100%;" id="TMain" class="display dataTable" cellspacing="0">
				<thead>
					<tr class="selected">
						<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="代码类ID:" class="dt-middle sorting_disabled">代码类ID</th>
						<th colspan="1" rowspan="1" style="width: 40px;" aria-label="代码类名称:" class="dt-middle sorting_disabled">代码类名称</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="代码类属性:" class="dt-middle sorting_disabled">代码类属性</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="代码录入层次:" class="dt-middle sorting_disabled">代码录入层次</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="代码ID:" class="dt-middle sorting_disabled">代码ID</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="代码名称:" class="dt-middle sorting_disabled">代码名称</th>
						<th colspan="1" rowspan="1" style="width: 85px;" aria-label="上级代码:" class="dt-middle sorting_disabled">上级代码</th>
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
var layerHeight = 270;
function ajax() {
	var table = $('#TMain').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TMain').DataTable({
			"paging": true,
			 "iDisplayLength" : 100,
			"lengthChange":false,
			//"lengthMenu":[50,100,200],//设置一页展示20条记录
			"processing" : false,
			"retrieve" : true,
			"stateSave" : true,
			"pagingType" : "full_numbers",
			"serverSide" : false,
			"ordering "	:true,
			"searching" : true,
			//"processing" : false,
			//"retrieve" : true,
			//"stateSave" : true,
			//"pagingType" : "full_numbers",
			//"pageLength": 50,		
			"sAjaxSource" : "${ctx}/diccode?methodtype=search",
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
						{"data": "DicTypeID", "defaultContent" : '',"className" : 'td-center'},
						{"data": "DicTypeName", "defaultContent" : '',"className" : 'td-left'},
						{"data": "DicTypeLevel", "defaultContent" : '',"className" : 'td-left'},
						{"data": "DicSelectedFlag", "defaultContent" : '',"className" : 'td-center'},
						{"data": "DicID", "defaultContent" : '',"className" : 'td-center'},
						{"data": "DicName", "defaultContent" : '',"className" : 'td-center'},
						{"data": "parentName", "defaultContent" : '',"className" : 'td-center'},
						{"data": null, "defaultContent" : '',"className" : 'td-center'}
			        ],
			"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["DicID"] + "--" + row["DicTypeID"] + "' />"
	                    }},	
			    		{"targets":7,"render":function(data, type, row){
			    			return 	"<a href='javascript:void(0);' title='增加子代码' onClick=\"callAddSubDicCode('" + row["DicID"] + "', '" + row["DicTypeID"] + "');\">增加子代码</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='详细信息' onClick=\"dispDicCodeDetail('" + row["DicID"] + "', '" + row["DicTypeID"] + "');\">详细信息</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='修改' onClick=\"callUpdateDicCode('" + row["DicID"] + "', '" + row["DicTypeID"] + "');\">修改</a>"
	                    }}
		         ] 
		}
	);
}
	$(function(){
		
		ajax();
		
		$('#form').attr("action", "${ctx}/diccode?methodtype=search");

		var updateRecordCount = parseInt('${DisplayData.updatedRecordCount}');

		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
		$('#TMain').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMain').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});		
		
	});

	function inputCheck() {
		/*
		var str = $('#dicTypeIdName').val();
		if (!inputStrCheck(str, "代码类", 60, 7, true, true)) {
			return false;
		}

		str = $('#dicIdName').val();
		if (!inputStrCheck(str, "代码ID", 60, 7, true, true)) {
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

	function deleteDicCode() {
		
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
				var actionUrl = "${ctx}/diccode?methodtype=delete";
				
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
			alert("请至少选择一个项目");
		}
	}
	
	function dispDicCodeDetail(dicCodeId, dicTypeId) {
		//popupWindow("DicCodeDetail", "${pageContext.request.contextPath}/diccode?methodtype=detail&dicCodeId=" + dicCodeId + "&dicTypeId=" + dicTypeId, 800, 600);
		var url = "${ctx}/diccode?methodtype=detail&dicCodeId=" + dicCodeId + "&dicTypeId=" + dicTypeId;
		openLayer(url, $(document).width() - 250, layerHeight, true);
	}

	function addDicCode() {
		$('#operType').val("add");
		//popupWindow("DicCodeDetail", "${pageContext.request.contextPath}/diccode?methodtype=updateinit&operType=add", 800, 600);	
		var url = "${ctx}/diccode?methodtype=updateinit&operType=add";
		openLayer(url, $(document).width() - 250, layerHeight, true);

	}
		
	function callAddSubDicCode(dicCodeId, dicTypeId) {
		$('#operType').val("addsub");
		//popupWindow("DicCodeDetail", "${pageContext.request.contextPath}/diccode?methodtype=updateinit&operType=addsub&dicCodeId=" + dicCodeId + "&dicTypeId=" + dicTypeId, 800, 600);	
		var url = "${ctx}/diccode?methodtype=updateinit&operType=addsub&dicCodeId=" + dicCodeId + "&dicTypeId=" + dicTypeId;
		openLayer(url, $(document).width() - 250, layerHeight, true);

	}

	function callUpdateDicCode(dicCodeId, dicTypeId) {
		$('#operType').val("update");
		//popupWindow("DictypeDetail", "${pageContext.request.contextPath}/diccode?methodtype=updateinit&operType=update&dicCodeId=" + dicCodeId + "&dicTypeId=" + dicTypeId, 800, 600);
		var url = "${ctx}/diccode?methodtype=updateinit&operType=update&dicCodeId=" + dicCodeId + "&dicTypeId=" + dicTypeId;
		openLayer(url, $(document).width() - 250, layerHeight, true);

	}

	function selectDicType() {
		//callDicTypeSelect("dicTypeId", "dicTypeIdName", "0", "", "");
		var url = "${ctx}" + "/common/selectDicTypePopActionInit?dicControl=" + dicTypeId + "&dicControlView=" + dicTypeIdName + "&type=" + 0 + "&dicTypeId=" + "&treeType=0&index=";
		//openLayer(url, $(document).width() - 250, layerHeight, true);
		callDicTypeSelect("dicTypeId", "dicTypeIdName", "0", "", "0", "");
		
	}
	
	function reload() {
		$('#TMain').DataTable().ajax.reload(null,false);
		return true;
	}
	
	function setDicTypeInfo(typeName, typeId) {
		$('#dicTypeIdName').val(typeName);
		$('#dicTypeId').val(typeId);
	}
	
</script>
</html>