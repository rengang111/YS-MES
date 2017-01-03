<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具归还登记</title>
<script type="text/javascript">

var validatorBaseInfo;
var layerHeight = "250";

function ajaxMouldReturnRegisterList() {
	var table = $('#MouldReturnRegisterList').dataTable();
	
	if(table) {
		table.fnDestroy();
	}

	var t = $('#MouldReturnRegisterList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/mouldreturnregister?methodtype=getMouldLendRegisterList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#mouldReturnBaseInfo").serializeArray();
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
					"columns" : [ 
						{"data" : "no", "className" : 'td-center'},
						{"data" : "name", "className" : 'td-center'}, 
					],
					"columnDefs":[
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

function initEvent(){

	validatorBaseInfo = $("#mouldReturnBaseInfo").validate({
		rules: {
			factReturnTime: {
				required: true,
				date: true,
				minlength: 8,
				maxlength: 10,
			},
			acceptResult: {
				required: true,	
			},
			memo: {								
				maxlength: 200,
			},
			acceptor: {
				required: true,
			},
		},
		errorPlacement: function(error, element) {
		    if (element.is(":radio"))
		        error.appendTo(element.parent().next().next());
		    else if (element.is(":checkbox"))											    	
		    	error.insertAfter(element.parent().parent());
		    else
		    	error.insertAfter(element);
		}
	});
	
	
	controlButtons($('#keyBackup').val());

	$('#acceptResult').val('${DisplayData.mouldReturnRegisterData.acceptresult}');
	$("#acceptor").val('${DisplayData.mouldReturnRegisterData.acceptor}');
	if ('${DisplayData.mouldReturnRegisterData.confirm}' == '1') {
		doConfirm();
	}
}

function doConfirm() {
	$('#factReturnTime').attr("disabled", true);
	$('#acceptor').attr("disabled", true);
	$('#acceptResult').attr("disabled", true);
	$('#memo').attr("disabled", true);
	$('#confirm').attr("disabled", true);
	$('#edit').attr("disabled", true);
	$('#delete').attr("disabled", true);
}

$(window).load(function(){
	initEvent();
});

$(document).ready(function() {

	ajaxMouldReturnRegisterList();
	
	
})

function reloadMouldDetailList() {
	$('#MouldReturnRegisterList').DataTable().ajax.reload(null,false);
	
	//reloadTabWindow();
}

function doSave() {

	if (validatorBaseInfo.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/mouldreturnregister?methodtype=add";
		} else {
			//修正
			actionUrl = "${ctx}/business/mouldreturnregister?methodtype=update";
		}		
		
		if (confirm(message)) {
			var actionUrl;			

			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#mouldReturnBaseInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						reloadTabWindow();
						controlButtons(d.info);
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();
					//parent.layer.close(index); //执行关闭
					
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

function doDelete() {
	
	if (confirm("${DisplayData.endInfoMap.message}")) {
		//将提交按钮置为【不可用】状态
		//$("#submit").attr("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/business/mouldreturnregister?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					controlButtons("");
					clearAll();
					reloadMouldDetailList();
					reloadTabWindow();
				}
					
				//不管成功还是失败都刷新父窗口，关闭子窗口
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();/
				parent.layer.close(index); //执行关闭
				
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

function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		$('#confirm').attr("disabled", true);
		$('#delete').attr("disabled", true);
	} else {
		$('#delete').attr("disabled", false);
		$('#factReturnTime').attr("disabled", false);
		$('#acceptor').attr("disabled", false);
		$('#acceptResult').attr("disabled", false);
		$('#memo').attr("disabled", false);
		$('#confirm').attr("disabled", false);
	}
}

function clearAll() {

	$('#mouldReturnNo').html("");
	$('#productModelId').html("");
	$('#mouldFactoryId').html("");
	$('#returnTime').html("");
	$('#factReturnTime').val("");
	$('#acceptor').val("");
	$('#memo').val("");
	$('#acceptResult').val("");
}

function doConfirmReturn() {
	if (validatorBaseInfo.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";	
		
		if (confirm(message)) {
			var actionUrl;
			actionUrl = "${ctx}/business/mouldreturnregister?methodtype=confirmreturn";
			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#mouldReturnBaseInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						doConfirm();
						reloadTabWindow();
					}
					//不管成功还是失败都刷新父窗口，关闭子窗口
					var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();
					parent.layer.close(index); //执行关闭
					
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
			<div  style="height:20px"></div>
			<form:form modelAttribute="dataModels" id="mouldReturnBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<input type=hidden id="lendId" name="lendId" value="${DisplayData.lendId}"/>
				<legend>模具归还-基本信息</legend>
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<table class="form" width="850px">
					<tr>
						<td width="90px">模具归还编号：</td>
						<td colspan=3>
							<label id="mouldReturnNo" name="mouldReturnNo" >${DisplayData.mouldReturnRegisterData.mouldreturnno}</label>
						</td>
					</tr>
					<tr>
						<td width="60px">产品型号：</td> 
						<td>
							<lable id="productModelId" class="required middle">${DisplayData.productModelNo}</lable>
						</td>
						<td width="90px">产品名称：</td> 
						<td>
							<lable id="productModelName" class="read-only">${DisplayData.productModelName}</lable>
						</td>
					</tr>
					<tr>
						<td>
							出借工厂：
						</td>
						<td>
							<lable id="productModelName" class="read-only">${DisplayData.mouldFactory}</lable>
						</td>
						<td>
							出借时间：
						</td>
						<td>
							<lable id="lendTime" class="read-only">${DisplayData.mouldLendRegisterData.lendtime}</lable>
						</td>
					</tr>
					<tr>
						<td>	
							预期归还时间：
						</td>
						<td> 
							<lable id="returnTime" class="read-only">${DisplayData.mouldLendRegisterData.returntime}</lable>
						</td>
						<td>	
							实际归还时间：
						</td>
						<td> 
							<input type="text" id="factReturnTime" name="factReturnTime" class="short" value="${DisplayData.mouldReturnRegisterData.factreturntime}"></input>
						</td>

					</tr>
				</table>			
				
				<div  style="height:20px"></div>
				<legend>归还详情</legend>
				<div  style="height:10px"></div>
				<table id="MouldReturnRegisterList" class="display" cellspacing="0" width="200px" style="float:left;">
					<thead>
						<tr class="selected">
							<th style="width: 80px;" class="dt-middle">模具编号</th>
							<th style="width: 80px;" class="dt-middle">模具名称</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
						</tr>
					</tfoot>
				</table>

				<div  style="height:20px"></div>
				<legend>归还验收</legend>
				<div style="height:20px"></div>
				<div>
					<table>
						<tr>
							<td>
								验收结果:
								<form:select path="acceptResult">
									<form:options items="${DisplayData.acceptResultList}" itemValue="key"
								                  itemLabel="value" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td>
								<textarea id="memo" name="memo" cols=100 rows=10>${DisplayData.mouldReturnRegisterData.memo}</textarea>
							</td>
						</tr>
							<td>
								验收人
								<form:select path="acceptor">
									<form:options items="${DisplayData.acceptorList}" itemValue="key"
								                  itemLabel="value" />
								</form:select>						
							</td>
						</tr>
					</table>
					
					<div>
						<button type="button" id="confirm" class="DTTT_button" onClick="doConfirmReturn(1);"
								style="height:25px;margin:20px 0px 0px 0px;" >上级确认</button>
					</div>
				</div>
			</form:form>
		</div>
</html>
