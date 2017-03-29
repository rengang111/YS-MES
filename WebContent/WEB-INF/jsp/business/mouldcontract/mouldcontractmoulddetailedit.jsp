<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>模具详情</title>
<script type="text/javascript">

$(document).ready(function() {
	ajaxMouldDetailList();
})

function ajaxMouldDetailList() {
	var table = $('#MouldDetailList').dataTable();
	
	if(table) {
		table.fnDestroy();
	}

	var t = $('#MouldDetailList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/mouldcontract?methodtype=getMouldBaseInfoList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#baseInfo").serializeArray();
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
								sumPrice = data.sumPrice;
								$('#sumPrice').html(data.sumPrice);
								if (sumPrice != '' && sumPrice != 0) {
									$('#payable').html(sumPrice - parseFloat('${DisplayData.mouldPayInfoData.withhold}'));
								}
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
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "no", "className" : 'td-center'}, 
						{"data" : "name", "className" : 'td-center'},
						{"data" : "size", "className" : 'td-center'}, 
						{"data" : "materialQuality", "className" : 'td-center'},
						{"data" : "unloadingNum", "className" : 'td-center'},
						{"data" : "heavy", "className" : 'td-center'},
						{"data" : "price", "className" : 'td-center'},
						{"data" : "mouldFactory", "className" : 'td-center'},
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheckMD' id='numCheckMD' value='" + row["id"] + "' />"
	                    }}
				    ] 						
				});
	
	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

		var tr = $(this).closest('tr');
		
		var row = t.row(tr);

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(format(row.data())).show();
			tr.addClass('shown');
		}
	});
};

function doReturn() {
	//var url = "${ctx}/business/externalsample";
	//location.href = url;	
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	//parent.$('#events').DataTable().destroy();/
	//parent.reload_contactor();
	parent.layer.close(index); //执行关闭
}

function doSave() {

	var str = '';
	$("input[name='numCheckMD']").each(function(){
		if ($(this).prop('checked')) {
			str += $(this).val() + ",";
		}
	});

	if (str != '') {		
		var message = "${DisplayData.endInfoMap.message}";
		
		actionUrl = "${ctx}/business/mouldcontract?methodtype=updatemoulddetail";

		if (confirm(message)) {
			var actionUrl;			
		
			str += $('#mouldContractBaseId').val();
			
			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : str,// 要提交的表单
				success : function(d) {
					parent.reloadMouldDetailList();

					if (d.message != "") {
						alert(d.message);	
					} else {
						
						//不管成功还是失败都刷新父窗口，关闭子窗口
						var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
						//parent.$('#events').DataTable().destroy();
						parent.layer.close(index); //执行关闭
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
	}
}


</script>

</head>

<body>
<div id="container">

		<div id="main">
			<div id="mouldDetailBasic">				
				<div  style="height:20px"></div>
				
				<legend>模具详情信息</legend>
				<button type="button" id="save" class="DTTT_button" onClick="doReturn();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >返回</button>				
				<button type="button" id="save" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >选择</button>
					
				<form:form modelAttribute="dataModels" id="baseInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="mouldBaseId" name="mouldBaseId" value="${DisplayData.mouldBaseId}"/>
					
					<div class="list">
						<table id="MouldDetailList" class="display" cellspacing="0">
							<thead>
								<tr class="selected">
									<th style="width: 40px;" class="dt-middle">No</th>
									<th style="width: 80px;" class="dt-middle">模具<br>编号</th>
									<th style="width: 80px;" class="dt-middle">模具<br>名称</th>
									<th style="width: 80px;" class="dt-middle">模架<br>尺寸</th>
									<th style="width: 80px;" class="dt-middle">材质</th>
									<th style="width: 80px;" class="dt-middle">出模数</th>
									<th style="width: 80px;" class="dt-middle">重量</th>
									<th style="width: 80px;" class="dt-middle">价格</th>
									<th style="width: 80px;" class="dt-middle">模具<br>工厂</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
									<th></th>
								</tr>
							</tfoot>
						</table>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</html>
