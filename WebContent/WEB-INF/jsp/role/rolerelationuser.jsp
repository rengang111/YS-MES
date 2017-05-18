<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common2.jsp"%>
<html>

<body>
	<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
		<input type="hidden" id="roleId" name="roleId" value="${DisplayData.roleId}"/>
	</form>
	
	<table>
		<tr>
			<td class="label">
				<label style="margin:0px 10px" align="left">角色：${DisplayData.roleIdName}</label>
			</td>
		</tr>
	</table>
	
	<div class="list">
		
		<table aria-describedby="TMould_info" style="width: 100%;" id="TMain" class="display dataTable" cellspacing="0" style="table-layout:fixed;">
			<thead>
				<tr class="selected">
					<th colspan="1" rowspan="1" style="width: 10px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
					<th colspan="1" rowspan="1" style="width: 40px;" aria-label="用户名称:" class="dt-middle sorting_disabled">用户名称</th>
					<th colspan="1" rowspan="1" style="width: 85px;" aria-label="职务:" class="dt-middle sorting_disabled">职务</th>
					<th colspan="1" rowspan="1" style="width: 85px;" aria-label="所属单位:" class="dt-middle sorting_disabled">所属单位</th>
					<th colspan="1" rowspan="1" style="width: 40px;" aria-label="电话:" class="dt-middle sorting_disabled">电话</th>
					<th colspan="1" rowspan="1" style="width: 85px;" aria-label="手机:" class="dt-middle sorting_disabled">手机</th>
					<th colspan="1" rowspan="1" style="width: 85px;" aria-label="Email:" class="dt-middle sorting_disabled">Email</th>
				</tr>
			</thead>
		</table>
	</div>
	<button type="button" id="close" class="DTTT_button" onClick="doReturn();"
			style="height:25px;margin:10px 5px 0px 0px;float:right;" >返回</button>

</body>

<script>

$(function(){
	
	ajax();
	
}); 


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
			"sAjaxSource" : "${ctx}/role?methodtype=getrolerelationuser",
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
						{"data": "LoginName", "defaultContent" : '',"className" : 'td-left'},
						{"data": "Duty", "defaultContent" : '',"className" : 'td-left'},
						{"data": "UnitID", "defaultContent" : '',"className" : 'td-center'},
						{"data": "Telephone", "defaultContent" : '',"className" : 'td-center'},
						{"data": "HandPhone", "defaultContent" : '',"className" : 'td-center'},
						{"data": "Email", "defaultContent" : '',"className" : 'td-center'},	
			        ],
			"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"]
	                    }}
		         ] 
		}
	);
}

function doReturn() {

	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	parent.layer.close(index); //执行关闭
	
}

</script>
</html>