<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>项目任务书</title>
<script type="text/javascript">
var validator;
var minCols = new Array();
var colNames = new Array();
var colCount = new Array();
minCols[0] = 6;
minCols[1] = 3;
minCols[2] = 2;
minCols[3] = 3;
minCols[4] = 3;
minCols[5] = 3;
minCols[6] = 2;
minCols[7] = 2;

colCount[0] = 6;
colCount[1] = 3;
colCount[2] = 2;
colCount[3] = 3;
colCount[4] = 3;
colCount[5] = 3;
colCount[6] = 2;
colCount[7] = 2;

colNames[0] = "machine";
colNames[1] = "packing";
colNames[2] = "3D";
colNames[3] = "auth";
colNames[4] = "patentCost";
colNames[5] = "patentQuery";
colNames[6] = "design";
colNames[7] = "trialProduce";

function initEvent(){

	controlButtons($('#keyBackup').val());
	
	setProjectTaskCost();

}

function setProjectTaskCost() {
	var costData = new Array();
	var costDataTypeCount = new Array();
	var subCostData = new Array();
	var index = 0;
	costData = eval('${DisplayData.costDataList}');
	costDataTypeCount = eval('${DisplayData.costDataTypeCount}');
	
	for(var i = 0; i < costDataTypeCount.length; i++) {
		var subCostDataTypeCount = parseInt(costDataTypeCount[i]);
	
		for(var j = 0; j < subCostDataTypeCount; j++) {
			subCostData = costData[index];
			if (j < minCols[i]) {
				$('#' + colNames[i] + "-" + (j + 1)).val(subCostData[2]);
			} else {
				addNew(parseInt(subCostData[0]));
				$('#' + colNames[i] + "-name-" + (j + 1)).val(subCostData[1]);
				$('#' + colNames[i] + "-" + (j + 1)).val(subCostData[2]);
			}
			index++;
		}
	}
	
	getSum();

}

$(window).load(function(){
	initEvent();
});

$(document).ready(function() {

	$("#tabs").tabs();
	$('#tabs').width('330px');
	$('#tabs').height('270px');
	$('#tabs-1').height('300px');

	if ($('#keyBackup').val() != "") {
		$('#tabs').show();
	}

	
	
	validator = $("#projectTaskInfo").validate({
		rules: {
			projectId: {
				required: true,
				minlength: 9,
				maxlength: 9,
			},
			projectName: {
				required: true,				
				maxlength: 50,
			},
			tempVersion: {
				required: true,								
				maxlength: 4,
			},
			manager: {
				required: true
			},
			designCapability: {
				maxlength: 500,
			},
			beginTime: {
				required: true,
				date: true,
			},
			endTime: {
				required: true,
				date: true,
			},
			packing: {
				maxlength: 500,
			},
			estimateCost: {
				maxlength: 10,
			},
			salePrice: {
				maxlength: 10,
			},
			sales: {
				maxlength: 10,
			},
			recoveryNum: {
				maxlength: 10,
			},
			failMode: {
				maxlength: 500,
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
	
	$("#manager").val("${DisplayData.projectTaskData.manager}");

    jQuery.validator.addMethod("verifyDate",function(value, element){ 
    	var rtnValue = true;
    	
    	var beginTime = new Date($('#beginTime').val(''));
    	var endTime = new Date($('#endTime').val(''));
    	rtnValue = beginTime < endTime;
    	
        return rtnValue;  
    }, "手机号码不正确"); 
})

function doSave() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/projecttask?methodtype=add";				
		} else {
			//修正
			actionUrl = "${ctx}/business/projecttask?methodtype=update";
		}		
		
		if (confirm(message)) {		
			$('#minCols').val(minCols);
			$('#colCount').val(colCount);
			$('#colNames').val(colNames);
			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#projectTaskInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						$('#tabs').show();
						reloadTabWindow();
						controlButtons(d.info);
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

function doDelete() {
	
	if (confirm("${DisplayData.endInfoMap.message}")) {
		//将提交按钮置为【不可用】状态
		//$("#submit").attr("disabled", true); 
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/business/projecttask?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					$('#tabs').hide();
					controlButtons("");
					clearProjectTaskInfo();
					reloadTabWindow();
				}
					
				//不管成功还是失败都刷新父窗口，关闭子窗口
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();/
				//parent.reload_contactor();
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



function clearProjectTaskInfo() {
	$('#projectId').val('');
	$('#projectName').val('');
	$('#tempVersion').val('');
	$('#manager').val('');
	$('#referPrototype').val('');
	$('#designCapability').val('');
	$('#beginTime').val('');
	$('#endTime').val('');
	$('#packing').val('');
	$('#estimateCost').val('');
	$("#salePrice").val('');
	$("#sales").val('');
	$("#recoveryNum").val('');
	$("#failMode").val('');
	$("#sales").val('');
}

function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		$('#delete').attr("disabled", true);
	} else {
		$('#delete').attr("disabled", false);
	}
}

function addNew(tableIndex) {
	var table;
	var activeMinCols;
	var colName;
	var colHeaderSuffix = "-name";
	var colHeaderPrefix = "td-";
	var tempColCount = 0;
	
	tableIndex = parseInt(tableIndex);
	
	switch(tableIndex) {
	case 0:
		table = $('#machinePattern');
		break;
	case 1:
		table = $('#packingPattern');
		break;
	case 2:
		table = $('#3D');
		break;
	case 3:
		table = $('#auth');
		break;
	case 4:
		table = $('#patentCost');
		break;
	case 5:
		table = $('#patentQuery');
		break;
	case 6:
		table = $('#design');
		break;
	case 7:
		table = $('#trialProduce');
		break;
	}
	
	activeMinCols = minCols[tableIndex];
	
	colName = colNames[tableIndex];
	
	tempColCount = $(table).find("td").length / $(table).find("tr").length;

	var removeCol = $(table).find("tr").eq(0).children();
	removeCol.eq(tempColCount - 1).remove();
	removeCol = $(table).find("tr").eq(1).children();
	removeCol.eq(tempColCount - 1).remove();
	removeCol = $(table).find("tr").eq(2).children();
	removeCol.eq(tempColCount - 1).remove();

	colCount[tableIndex]++;
	
	td ="<td id='" + colHeaderPrefix + colName + "-" + colCount[tableIndex] + "' align='center'>";
	td += "<input type='text' id='" + colName + colHeaderSuffix + "-" + colCount[tableIndex] + "' name='" + colName + colHeaderSuffix + "-" + colCount[tableIndex] + "' style='width:60px;' />";
	td +="</td>";	
	$(table).find("tr").eq(0).append(td);
	
	td ="<td align='center'>";
	td += "<input type='text' id='" + colName + "-" + colCount[tableIndex] + "' name='" + colName + "-" + colCount[tableIndex] + "' style='width:60px;' oninput='getSum();' />";
	td +="</td>";
	$(table).find("tr").eq(1).append(td);

	td ="<td align='center' width=60>";
	td += "<img id='removeImg-" + colCount + "' name='removeImg-" + colCount + "' onClick='removeCol(" + tableIndex + ", " + colCount[tableIndex] +");' src='${ctx}/images/ee.png' style='vertical-align:bottom;width:15px;height:15px;'>";
	td +="</td>";
	$(table).find("tr").eq(2).append(td);	

	td ="<td align='center' width=60>";
	td += "合计";
	td +="</td>";
	$(table).find("tr").eq(0).append(td);	
	
	td ="<td align='center' width=60>";
	td += "<label id='" + colName + "-sum' class='short'></label>";
	td +="</td>";
	$(table).find("tr").eq(1).append(td);	
	
	td ="<td align='center' width=60>";
	td +="</td>";
	$(table).find("tr").eq(2).append(td);

	$('#' + colName + "-" + colCount[tableIndex]).rules('add', { digits: true, maxlength: 10});

	getSum();

}

function removeCol(tableIndex, colIndex) {
	var table;
	var colName;
	var col = -1;
	var colCount = 0;
	var colHeaderPrefix = "td-";

	switch(tableIndex) {
	case 0:
		table = $('#machinePattern');
		break;
	case 1:
		table = $('#packingPattern');
		break;
	case 2:
		table = $('#3D');
		break;
	case 3:
		table = $('#auth');
		break;
	case 4:
		table = $('#patentCost');
		break;
	case 5:
		table = $('#patentQuery');
		break;
	case 6:
		table = $('#design');
		break;
	case 7:
		table = $('#trialProduce');
		break;
	}

	colName = colHeaderPrefix + colNames[tableIndex] + "-" + colIndex;
	
	$(table).find("td").each(function(){
		colCount++;
		if ($(this).attr('id') == colName) {
			col = colCount;
		}
	});

	$('#' + colNames[tableIndex] + "-" + colIndex).rules('remove');
	
	var removeCol = $(table).find("tr").eq(0).children();
	removeCol.eq(col - 1).remove();
	removeCol = $(table).find("tr").eq(1).children();
	removeCol.eq(col - 1).remove();
	removeCol = $(table).find("tr").eq(2).children();
	removeCol.eq(col - 1).remove();
}

function getSum() {
	var totalSum = 0.0;
	for (var i = 0; i < colNames.length; i++) {
		var sum = 0.0;
		for (var j = 0; j < colCount[i]; j++) {
			var txtName = colNames[i] + "-" + (j + 1);
			if ($('#' + txtName).length > 0) {
				if ($('#' + txtName).val() != '') {
					sum += parseFloat($('#' + txtName).val());
				}
			}
		}

		$('#' + colNames[i] + "-sum").html(sum);
		totalSum += sum;
	}
	
	$('#totalInput').html(totalSum);
}

function doReturn() {
	//var url = "${ctx}/business/externalsample";
	//location.href = url;	
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	//parent.$('#events').DataTable().destroy();/
	//parent.reload_contactor();
	parent.layer.close(index); //执行关闭
	
}
</script>

</head>

<body>
<div id="container">

		<div id="main">	
				
			<div id="tabs" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true" style="margin:10px 0px 0px 15px;padding:0px;display:none;">
				<div id="tabs-1" title="图片" style="padding:5px;height:300px;">
					<jsp:include page="../../common/album/album.jsp"></jsp:include>
				</div>
			</div>
			<div style="clear:both;"></div>
			<div  style="height:20px"></div>
				
				<legend>项目任务书-基本信息</legend>
					
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>	
				<form:form modelAttribute="dataModels" id="projectTaskInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<input type=hidden id="minCols" name="minCols" value=""/>
					<input type=hidden id="colCount" name="colCount" value=""/>
					<input type=hidden id="colNames" name="colNames" value=""/>
					<table class="form" width="850px">
						<tr>
							<td width="60px">项目编号：</td>
							<td width="240px">
								<input type="text" id="projectId" name="projectId" class="short" value="${DisplayData.projectTaskData.projectid}"/>
							</td>
							<td width="60px">项目名称：</td> 
							<td>
								<input type="text" id="projectName" name="projectName" class="short" value="${DisplayData.projectTaskData.projectname}"/>
							</td>
						</tr>
						<tr>
							<td>暂定型号：</td> 
							<td>
								<input type="text" id="tempVersion" name="tempVersion" class="short" value="${DisplayData.projectTaskData.tempversion}"/>
							</td>
							<td>
								项目经理：
							</td>
							<td>
								<form:select path="manager">
									<form:options items="${DisplayData.managerList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td>
								参考原型：
							</td>
							<td>
								<input type="text" id="referPrototype" name="referPrototype" class="short" value="${DisplayData.projectTaskData.referprototype}"/>
							</td>
						</tr>
						<tr>
							<td>	
								起始时间：
							</td>
							<td> 
								<input type="text" id="beginTime" name="beginTime" class="short" value="${DisplayData.projectTaskData.begintime}"/>
							</td>
							<td>	
								预计完成时间：
							</td>
							<td> 
								<input type="text" id="endTime" name="endTime" class="short" value="${DisplayData.projectTaskData.endtime}"/>
							</td>							
						</tr>
						<tr>
							<td>
								设计性能：
							</td>
							<td colspan=3> 
								<textarea id="designCapability" name="designCapability" rows=5 cols=120 class="long">${DisplayData.projectTaskData.designcapability}</textarea>
							</td>
						</tr>

						<tr>
							<td>
								包装描述： 
							</td>
							<td colspan=3>
								<textarea id="packing" name="packing" rows=5 cols=120 class="long">${DisplayData.projectTaskData.packing}</textarea>
							</td>
						</tr>
						<tr>
							<td>
								总投入： 
							</td>
							<td>
								<label id="totalInput" name="totalInput" class="short" value=""/>
							</td>
							<td>
								预估成本： 
							</td>
							<td>
								<input type="text" id="estimateCost" name="estimateCost" style="resize:none;width=200px;height=50px;" class="middle" value="${DisplayData.projectTaskData.estimatecost}"/>
							</td>
						</tr>
					</table>
					
					<div  style="height:20px"></div>
					<legend>费用预估</legend>
					<div  style="height:10px"></div>
					<div>
						1.机器磨具
						<button type="button" class="DTTT_button" id="addMachine" onClick="addNew(0);">新建</button>
					</div>
					<div class="list">
						<table id='machinePattern' class="editableTable">
							<tr height=30>
								<td align="center">机身模具</td>
								<td align="center">齿轮箱模具</td>
								<td align="center">电池包模具</td>
								<td align="center">充电器模具</td>
								<td align="center">压铸件模具</td>
								<td align="center">五金件模具</td>
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-machine-1" align="center" width=60><input type="text" id="machine-1" name="machine-1" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-machine-2" align="center" width=60><input type="text" id="machine-2" name="machine-2" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-machine-3" align="center" width=60><input type="text" id="machine-3" name="machine-3" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-machine-4" align="center" width=60><input type="text" id="machine-4" name="machine-4" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-machine-5" align="center" width=60><input type="text" id="machine-5" name="machine-5" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-machine-6" align="center" width=60><input type="text" id="machine-6" name="machine-6" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-machine-sum" align="center" width=60><label id="machine-sum" class="short"></label></td>
							</tr>
							<tr height=30>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>
					<div>
						2.包装模具
						<button type="button" class="DTTT_button" id="addPacking" onClick="addNew(1);">新建</button>
					</div>
					<div class="list">
						<table id='packingPattern' class="editableTable">
							<tr height=30>
								<td align="center">吹塑包装模具</td>
								<td align="center">注塑包装模具</td>
								<td align="center">泡壳模具</td>
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-packing-1" align="center" ><input type="text" id="packing-1" name="packing-1" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-packing-2" align="center" ><input type="text" id="packing-2" name="packing-2" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-packing-3" align="center" ><input type="text" id="packing-3" name="packing-3" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-packing-sum" align="center" ><label id="packing-sum" name="packing-sum" style="width:60px;"></label></td>
							</tr>
							<tr height=30>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>			
					<div>
						3.3D制作
						<button type="button" class="DTTT_button" id="add3D" onClick="addNew(2);">新建</button>
					</div>
					<div class="list">
						<table id='3D' class="editableTable">
							<tr height=30>
								<td align="center">手模</td>
								<td align="center">工作样机</td>
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-3D-1" align="center" ><input type="text" id="3D-1" name="3D-1" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-3D-2" align="center" ><input type="text" id="3D-2" name="3D-2" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-3D-sum" align="center" ><label id="3D-sum" name="3D-sum" style="width:60px;"></label></td>
							</tr>
							<tr height=30>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>
					<div>
						4.认证费用
						<button type="button" class="DTTT_button" id="addAuthentication" onClick="addNew(3);">新建</button>
					</div>
					<div class="list">
						<table id='auth' class="editableTable">
							<tr height=30>
								<td align="center">认证项目1</td>
								<td align="center">认证项目2</td>
								<td align="center">认证项目3</td>
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-auth-1" align="center" ><input type="text" id="auth-1" name="auth-1" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-auth-2" align="center" ><input type="text" id="auth-2" name="auth-2" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-auth-3" align="center" ><input type="text" id="auth-3" name="auth-3" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-auth-sum" align="center" ><label id="auth-sum" name="auth-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>
					<div>					
						5.专利费用
						<button type="button" class="DTTT_button" id="addPatentCost" onClick="addNew(4);">新建</button>
					</div>
					<div class="list">
						<table id='patentCost' class="editableTable">
							<tr height=30>
								<td align="center">外观</td>
								<td align="center">实用性</td>
								<td align="center">发明</td>
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-patentCost-1" align="center" ><input type="text" id="patentCost-1" name="patentCost-1" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-patentCost-2" align="center" ><input type="text" id="patentCost-2" name="patentCost-2" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-patentCost-3" align="center" ><input type="text" id="patentCost-3" name="patentCost-3" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-patentCost-sum" align="center" ><label id="patentCost-sum" name="patentCost-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>
					<div>
						6.专利查询
						<button type="button" class="DTTT_button" id="addPatentQuery" onClick="addNew(5);">新建</button>
					</div>
					<div class="list">
						<table id='patentQuery' class="editableTable">
							<tr height=30>
								<td align="center">查询项目1</td>
								<td align="center">查询项目2</td>
								<td align="center">查询项目3</td>
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-patentQuery-1" align="center" ><input type="text" id="patentQuery-1" name="patentQuery-1" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-patentQuery-2" align="center" ><input type="text" id="patentQuery-2" name="patentQuery-2" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-patentQuery-3" align="center" ><input type="text" id="patentQuery-3" name="patentQuery-3" style="width:60px;" oninput="getSum();"></input></td>	
								<td id="td-patentQuery-sum" align="center" ><label id="patentQuery-sum" name="patentQuery-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>
					<div>
						7.委托设计
						<button type="button" class="DTTT_button" id="addDesign" onClick="addNew(6);">新建</button>
					</div>
					<div class="list">
						<table id='design' class="editableTable">
	
							<tr height=30>
								<td align="center">设计项目1</td>
								<td align="center">设计项目2</td>
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-design-1" align="center" ><input type="text" id="design-1" name="design-1" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-design-2" align="center" ><input type="text" id="design-2" name="design-2" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-design-sum" align="center" ><label id="design-sum" name="design-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>
					<div>			
						8.试产费用
						<button type="button" class="DTTT_button" id="addTrialProduce" onClick="addNew(7);">新建</button>
					</div>
					<div class="list">
						<table id='trialProduce' class="editableTable">
							<tr height=30>
								<td align="center">试产数量</td>
								<td align="center">试产费用</td>
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-trialProduce-1" align="center" ><input type="text" id="trialProduce-1" name="trialProduce-1" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-trialProduce-2" align="center" ><input type="text" id="trialProduce-2" name="trialProduce-2" style="width:60px;" oninput="getSum();"></input></td>
								<td id="td-trialProduce-sum" align="center" ><label id="trialProduce-sum" name="trialProduce-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>
					<legend>市场预期</legend>
					<div  style="height:10px"></div>
					<div class="list">
						<table class='display' cellspacing="0">
							<tr>
								<td align="center">
									平均销售价格
								</td>
								<td align="center">
									年预期销量
								</td>
								<td align="center">
									回本数量
								</td>
							</tr>
							<tr>
								<td><input type="text" id="salePrice" name="salePrice" value="${DisplayData.projectTaskData.saleprice}"></input></td>
								<td><input type="text" id="sales" name="sales" value="${DisplayData.projectTaskData.sales}"></input></td>
								<td><input type="text" id="recoveryNum" name="recoveryNum" value="${DisplayData.projectTaskData.recoverynum}"></input></td>
							</tr>
						</table>
					</div>
					<div  style="height:10px"></div>
					<legend>失败模式</legend>
					<div  style="height:10px"></div>
					<div class="list">
						<textarea id="failMode" name="failMode" rows=5 cols=120>${DisplayData.projectTaskData.failmode}</textarea>
					</div>
				</form:form>
				
			</div>
			
			

</html>
