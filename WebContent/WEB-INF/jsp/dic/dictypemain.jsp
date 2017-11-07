<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
		<legend>字典类管理</legend>
		<div style="height:5px"></div>
		<!-- 
		<table>
			<tr>
				<td>
					代码类：
				</td>
				<td>
					<input type=text name="dicTypeIdName" id="dicTypeIdName" value="${DisplayData.dicTypeIdName}"/>
				</td>
				<td>
					<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="doSearch();"><span>查询</span></a>
				</td>
			</tr>
		</table>
		 -->
		
		
		<div id="TMould_wrapper" class="list">
			<div id="DTTT_container" align="right" style="height:40px">
				<a class="DTTT_button DTTT_button_text" onClick="addDictype();"><span>增加</span></a>
				<a class="DTTT_button DTTT_button_text" onClick="deleteDictype();"><span>删除</span></a>
			</div>	
			<table id="TMain" class="display">
				<thead>
					<tr>
						<th style="width: 30px;">No</th>
						<th style="width: 85px;">字典编码</th>
						<th style="width: 100px;">字典名称</th>
						<th style="width: 85px;">字典使用范围</th>
						<th style="width: 50px;">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		</fieldset>
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
				"paging": false,
				"lengthMenu":[10,20,50],//设置一页展示10条记录
				"processing" : true,
				"serverSide" : false,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : true,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/dictype?methodtype=search",
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
							{"data": null, "defaultContent" : '',"className" : 'td-left'},
							{"data": "DicTypeID", "defaultContent" : '',"className" : 'td-left'},
							{"data": "DicTypeName", "defaultContent" : '',"className" : 'td-left'},
							{"data": "DicTypeLevel", "defaultContent" : '',"className" : 'td-center'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
						return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["DicTypeID"] + "' />"
                    }},	
		    		{"targets":4,"render":function(data, type, row){
		    			// return 	"<a href='javascript:void(0);' title='增加子代码' onClick=\"callAddSubDictype('" + row["DicTypeID"] + "', '" + row["DicTypeName"] + "');\">增加子代码</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='详细信息' onClick=\"dispDictypeDetail('" + row["DicTypeID"] + "');\">详细信息</a>" + "&nbsp;" + "<a href='javascript:void(0);' title='修改' onClick=\"callUpdateDictype('" + row["DicTypeID"] + "');\">修改</a>"
		    			return 	 "<a href='javascript:void(0);' title='修改' onClick=\"callUpdateDictype('" + row["DicTypeID"] + "');\">修改</a>"
                    }}
	         	] 
			}
		);

		t.on('click', 'tr', function() {

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}
	
	$(function(){
		ajax();
		$('#form').attr("action", "${ctx}/dictype?methodtype=search");

		var updateRecordCount = parseInt('${DisplayData.updatedRecordCount}');

		if ('${DisplayData.message}' != '') {
			alert('${DisplayData.message}');
		}
		
	});

	function inputCheck() {
		/*
		var str = $('#dicTypeIdName').val();
		if (!inputStrCheck(str, "代码类", 60, 7, true, true)) {
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

	function addDictype() {
		$('#operType').val("add");
		//popupWindow("DictypeDetail", "${pageContext.request.contextPath}/dictype?methodtype=updateinit&operType=add", 800, 600);
		
		var url = "${ctx}/dictype?methodtype=updateinit&operType=add";
		openLayer(url, $(document).width() - 150, layerHeight, true);
	}
	
	function deleteDictype() {
		
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
				var actionUrl = "${ctx}/dictype?methodtype=delete";
				
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

	function callAddSubDictype(dicTypeId, dicTypeName) {
		$('#operType').val("addsubfromtype");
		//popupWindow("DictypeDetail", "${pageContext.request.contextPath}/diccode?methodtype=updateinit&operType=addsubfromtype&dicTypeId=" + dicTypeId + "&dicTypeName=" + dicTypeName, 800, 600);
		var url = "${ctx}/diccode?methodtype=updateinit&operType=addsubfromtype&dicTypeId=" + dicTypeId + "&dicTypeName=" + dicTypeName;
		openLayer(url, $(document).width() - 250, layerHeight, true);
	}
	
	function dispDictypeDetail(dictypeId) {
		//popupWindow("DictypeDetail", "${pageContext.request.contextPath}/dictype?methodtype=detail&dictypeId=" + dictypeId, 800, 600);
		var url = "${ctx}/dictype?methodtype=detail&dictypeId=" + dictypeId;
		openLayer(url, $(document).width() - 250, layerHeight, true);
	}

	function callUpdateDictype(dictypeId) {
		$('#operType').val("update");
		//popupWindow("DictypeDetail", "${pageContext.request.contextPath}/dictype?methodtype=updateinit&operType=update&dictypeId=" + dictypeId, 800, 600);
		var url = "${ctx}/dictype?methodtype=updateinit&operType=update&dictypeId=" + encodeURI(encodeURI(dictypeId));
		//alert(url);//中文两次转码
		openLayer(url, $(document).width() - 150, layerHeight, true);
	}
	function reload() {
		$('#TMain').DataTable().ajax.reload(null,false);
		return true;
	}
</script>
</html>