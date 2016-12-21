<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具出借登记</title>
<script type="text/javascript">

var validatorBaseInfo;
var layerHeight = "250";

function ajaxMouldLendRegisterList() {
	var table = $('#MouldLendRegisterList').dataTable();
	
	if(table) {
		table.fnDestroy();
	}

	var t = $('#MouldLendRegisterList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"serverSide" : true,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/mouldlendregister?methodtype=getMouldLendRegisterList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#mouldLendBaseInfo").serializeArray();
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
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "no", "className" : 'td-center'},
						{"data" : "name", "className" : 'td-center'}, 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheckLD' id='numCheckLD' value='" + row["id"] + "' />"
	                    }},
	                
			    		{"targets":3,"render":function(data, type, row){
			    			if(row["status"] == '1') {
			    				return "";
			    			} else {
			    				return "<a href=\"#\" onClick=\"doUpdateLD('" + row["id"] + "')\">编辑</a>"
			    			}
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

function initEvent(){

    jQuery.validator.addMethod("mouldLendNo",function(value, element){ 
    	var rtnValue = false;
    	if (value != '') {
    		var productModelId = $('#productModelId').val();
    		var mouldFactoryId = $('#mouldFactoryId').find("option:selected").text();
    		if (value.length == 8) {
  	    		var serialNoHeader = value.substring(0, 5);
  	    		if (serialNoHeader == "1608J") {
  	    			rtnValue = true;
  	    		}
    		}    		 
    	}
        return rtnValue;  
    }, "模具出借编号不正确(1608J+3位流水号)"); 
	
	validatorBaseInfo = $("#mouldLendBaseInfo").validate({
		rules: {
			mouldLendNo: {
				mouldLendNo: true,
				minlength: 8,
				maxlength: 8,
			},
			productModelId: {
				required: true,				
				maxlength: 120,
			},
			mouldFactoryId: {
				required: true,								
				maxlength: 72,
			},
			lendTime: {
				required: true,
				date: true,
				minlength: 8,
				maxlength: 10,
			},
			returnTime: {		
				required: true,
				date: true,
				minlength: 8,
				maxlength: 10,
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
	$("#productModelId").val('${DisplayData.mouldLendRegisterData.productmodel}');
	$("#productModelName").val('${DisplayData.productModelName}');
	
	$("#mouldFactoryId").val('${DisplayData.mouldLendRegisterData.lendfactory}');
	$("#proposer").val('${DisplayData.mouldLendConfirmData.proposer}');
	$("#brokerage").val('${DisplayData.mouldLendConfirmData.brokerage}');
	if ('${DisplayData.mouldLendConfirmData.confirm}' == '1') {
		doConfirm();
	}
	
	autoComplete();
}

function doConfirm() {
	$('#mouldLendNo').attr("disabled", true);
	$('#productModelId').attr("disabled", true);
	$('#mouldFactoryId').attr("disabled", true);
	$('#lendTime').attr("disabled", true);
	$('#returnTime').attr("disabled", true);
	$('#proposer').attr("disabled", true);
	$('#brokerage').attr("disabled", true);
	$('#confirm').attr("disabled", true);
	$('#addld').attr("disabled", true);
	$('#deleteld').attr("disabled", true);
	$('#edit').attr("disabled", true);
}

$(window).load(function(){
	initEvent();
});

$(document).ready(function() {

	ajaxMouldLendRegisterList();
	
	
})

function reloadMouldDetailList() {
	$('#MouldLendRegisterList').DataTable().ajax.reload(null,false);
	
	//reloadTabWindow();
}

function doSave() {

	if (validatorBaseInfo.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/mouldlendregister?methodtype=add";
		} else {
			//修正
			actionUrl = "${ctx}/business/mouldlendregister?methodtype=update";
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
				data : JSON.stringify($('#mouldLendBaseInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						reloadTabWindow();
						var x = new Array();
						x = d.info.split("|");
						controlButtons(x[0]);
						$('#mouldLendNo').html(x[1]);
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
			url : "${ctx}/business/mouldlendregister?methodtype=deleteDetail",
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
		$('#delete').attr("disabled", true);
		//$('#mouldLendNo').attr("disabled", true);
		//$('#productModelId').attr("disabled", true);
		//$('#mouldFactoryId').attr("disabled", true);
		//$('#lendTime').attr("disabled", true);
		//$('#returnTime').attr("disabled", true);
		//$('#proposer').attr("disabled", true);
		//$('#brokerage').attr("disabled", true);
		$('#addld').attr("disabled", true);
		$('#confirm').attr("disabled", true);
		$('#delete').attr("disabled", true);
		$('#deleteld').attr("disabled", true);
	} else {
		$('#delete').attr("disabled", false);
		$('#mouldLendNo').attr("disabled", false);
		$('#productModelId').attr("disabled", false);
		$('#mouldFactoryId').attr("disabled", false);
		$('#lendTime').attr("disabled", false);
		$('#returnTime').attr("disabled", false);
		$('#proposer').attr("disabled", false);
		$('#brokerage').attr("disabled", false);
		$('#confirm').attr("disabled", false);
		$('#addld').attr("disabled", false);
		$('#deleteld').attr("disabled", false);
	}
}

function clearAll() {

	$('#mouldLendNo').val("");
	$('#productModelId').val("");
	$('#mouldFactoryId').val("");
	$('#lendTime').val("");
	$('#returnTime').val("");
	$('#proposer').val("");
	$('#brokerage').val("");
}

function doAddLD() {
	var mouldLendNo = $('#keyBackup').val();
	var url = "${ctx}/business/mouldlendregister?methodtype=addldinit&mouldLendNo=" + mouldLendNo;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function doUpdateLD(key) {
	var mouldLendNo = $('#keyBackup').val();
	var url = "${ctx}/business/mouldlendregister?methodtype=updateldinit&mouldLendNo=" + mouldLendNo + "&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);
}

function doDeleteLD() {
	var str = '';
	$("input[name='numCheckLD']").each(function(){
		if ($(this).prop('checked')) {
			str += $(this).val() + ",";
		}
	});

	if (str != "") {
		if (confirm("您确认执行该操作吗？") == false) {
			return;
		}
		$.ajax({
			contentType : 'application/json',
			dataType : 'json',						
			type : "POST",
			data : str,// 要提交的表单						
			url : "${ctx}/business/mouldlendregister?methodtype=deleteld",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadMouldDetailList();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("发生系统异常，，请再试或者联系管理员。");
			}
		});
		
	} else {
		alert("请先选中要删除的记录。");
	}
}

function doConfirmLend() {
	if (validatorBaseInfo.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";	
		
		if (confirm(message)) {
			var actionUrl;
			actionUrl = "${ctx}/business/mouldlendregister?methodtype=confirmlend";

			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#mouldLendBaseInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						doConfirm();
						reloadMouldDetailList();
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

</script>
	<script>
		function autoComplete() { 
			$("#productModelId").autocomplete({
				source : function(request, response) {
					$.ajax({
						type : "POST",
						url : "${ctx}/business/mouldlendregister?methodtype=productModelIdSearch",
						dataType : "json",
						data : {
							key : request.term
						},
						success : function(data) {
							response($.map(
								data.data,
								function(item) {
									//alert(item.viewList)
									return {
										label : item.viewList,
										value : item.id,
										id : item.id,
										des : item.des
									}
								}));
						},
						error : function(XMLHttpRequest,
								textStatus, errorThrown) {
							alert(XMLHttpRequest.status);
							alert(XMLHttpRequest.readyState);
							alert(textStatus);
							alert(errorThrown);
							alert("系统异常，请再试或和系统管理员联系。");
						}
					});
				},
	
				select : function(event, ui) {
					$("#productModelId").val(ui.item.id);
					$("#productModelName").val(ui.item.des);
					//$("#factoryProductCode").focus();
	
				},
				minLength : 1,
				autoFocus : false,
				width: 200,
			});	
		}
	</script>
</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
			<form:form modelAttribute="dataModels" id="mouldLendBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<legend>模具出借-基本信息</legend>
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<table class="form" width="850px">
					<tr>
						<td width="90px">模具出借编号：</td>
						<td colspan=3>
							<label id="mouldLendNo" name="mouldLendNo" >${DisplayData.mouldLendRegisterData.mouldlendno}</label>
						</td>
					</tr>
					<tr>
						<td width="60px">产品型号：</td> 
						<td>
							<form:input path="productModelId" class="required middle" />
						</td>
						<td width="60px">产品名称：</td> 
						<td>
							<form:input path="productModelName"	class="read-only" />
						</td>
					</tr>
					<tr>
						<td>
							出借工厂：
						</td>
						<td>
							<form:select path="mouldFactoryId">
								<form:options items="${DisplayData.mouldFactoryList}" itemValue="key"
									itemLabel="value" />
							</form:select>
						</td>
						<td>
							出借时间：
						</td>
						<td>
							<input type="text" id="lendTime" name="lendTime" class="short" value="${DisplayData.mouldLendRegisterData.lendtime}"></input>
						</td>
					</tr>
					<tr>
						<td>	
							预期归还时间：
						</td>
						<td colspan=3> 
							<input type="text" id="returnTime" name="returnTime" class="short" value="${DisplayData.mouldLendRegisterData.returntime}"></input>
						</td>
					</tr>
				</table>			
				
				<div  style="height:20px"></div>
				<legend>出借详情</legend>
				<div>			
				<button type="button" id="deleteld" class="DTTT_button" onClick="doDeleteLD();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addld" class="DTTT_button" onClick="doAddLD();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				</div>
				<table id="MouldLendRegisterList" class="display" cellspacing="0">
					<thead>
						<tr class="selected">
							<th style="width: 40px;" class="dt-middle">No</th>
							<th style="width: 80px;" class="dt-middle">模具编号</th>
							<th style="width: 80px;" class="dt-middle">模具名称</th>
							<th style="width: 80px;" class="dt-middle">操作</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</tfoot>
				</table>

				<div  style="height:20px"></div>
				<legend>出借申请</legend>
				<div style="height:20px"></div>
				<div>
					<table>
						<tr>
							<td>
								申请人
							</td>
							<td>
								<form:select path="proposer">
									<form:options items="${DisplayData.proposerList}" itemValue="key"
								                  itemLabel="value" />
								</form:select>
							</td>
							<td>
								经手人
							</td>
							<td>
								<form:select path="brokerage">
									<form:options items="${DisplayData.proposerList}" itemValue="key"
								                  itemLabel="value" />
								</form:select>						
							</td>
						</tr>
					</table>
					
					<div>
						<button type="button" id="confirm" class="DTTT_button" onClick="doConfirmLend();"
								style="height:25px;margin:20px 0px 0px 0px;" >确认</button>
					</div>
				</div>
			</form:form>
		</div>
</html>
