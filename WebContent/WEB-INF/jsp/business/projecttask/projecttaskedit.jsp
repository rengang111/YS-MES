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
minCols[3] = 0;
minCols[4] = 3;
minCols[5] = 0;
minCols[6] = 0;
minCols[7] = 0;
minCols[8] = 0;

colCount[0] = 6;
colCount[1] = 3;
colCount[2] = 2;
colCount[3] = 0;
colCount[4] = 3;
colCount[5] = 0;
colCount[6] = 0;
colCount[7] = 0;
colCount[8] = 0;

colNames[0] = "machine";
colNames[1] = "packing";
colNames[2] = "3D";
colNames[3] = "auth";
colNames[4] = "patentCost";
colNames[5] = "patentQuery";
colNames[6] = "design";
colNames[7] = "trialProduce";
colNames[8] = "other";

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

	validator = $("#projectTaskInfo").validate({
		rules: {
			projectId: {
				required: true,
				minlength: 1,
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
				required: true,
				maxlength: 20,
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
				number: true,
				maxlength: 10,
			},
			sales: {
				maxlength: 10,
			},
			recoveryNum: {
				number: true,
				maxlength: 10,
			},
			failMode: {
				maxlength: 500,
			},
			currency: {
				
				required: true,
			},
			exchangeRate: {
				number: true,
				required: true,
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
	
    $('#tabs').tabs({
        width: $("#tabs").parent().width(),  
        height: "300"  
    });   
	
    $('#tabs2').tabs({
        width: $("#tabs2").parent().width(),  
        height: "300"  
    });

    $('#tabs3').tabs({
        width: $("#tabs3").parent().width(),  
        height: "300"  
    });
    
	if ($('#keyBackup').val() != "") {
		showImageTabs();
	} else {
		addNew(3);
		addNew(5);
		addNew(6);
		addNew(7);
		addNew(8);
	}
	
	$("#beginTime").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
	});
	if ($("#beginTime").val() == "") {
		$("#beginTime").datepicker( 'setDate' , new Date() );
	}
	
	$("#endTime").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
		defaultDate : new Date(),
	});
	if ($("#endTime").val() == "") {
		$("#endTime").datepicker( 'setDate' , new Date() );
	}
	
	addRules(0, true);
	addRules(1, true);
	addRules(2, true);
	
	for(var i = 0; i < 9; i++) {
		$("#expectDate-" + i).datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});
		
		if ($("#expectDate-" + i).val() == "") {
			$("#expectDate-" + i).datepicker( 'setDate' , new Date() );
			if ($('#keyBackup').val() != "") {
				$("#expectDate-" + i).val("***");
				$('#noUse-' + i).attr("checked",'true');
			}
			setNoUse(i);
		}
		$("#expectDate-" + i).rules('add', { required: true, date: true});
		
	}
	
	//$("#manager").val("${DisplayData.projectTaskData.manager}");
	$("#currency").val("${DisplayData.projectTaskData.currency}");

    jQuery.validator.addMethod("verifyDate",function(value, element){ 
    	var rtnValue = true;
    	
    	var beginTime = new Date($('#beginTime').val(''));
    	var endTime = new Date($('#endTime').val(''));
    	rtnValue = beginTime <= endTime;
    	
        return rtnValue;  
    }, "起始日期应在预计完成日期之前"); 
})

function setNoUse(index) {
	if ($('#noUse-' + index).prop('checked')) {
		$('#expectDate-' + index).val("***");
		$('#expectDate-' + index).attr("disabled", true); 
	} else {
		if ($('#expectDate-' + index).val() == "***") {
			$('#expectDate-' + index).datepicker( 'setDate' , new Date() );
		}
		$('#expectDate-' + index).attr("disabled", false);
	}
}

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
						showImageTabs();
						reloadTabWindow();
						controlButtons(d.info);
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(window.name);
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

function showImageTabs() {
	$('#tabs').show();
	$('#tabs2').show();
	$('#tabs3').show();
	$('#tabsContainer1').css('display','inline-block'); 
	$('#tabsContainer2').css('display','inline-block');
	$('#tabsContainer3').css('display','inline-block');
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
	case 8:
		table = $('#other');
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

	//$('#' + colName + "-" + colCount[tableIndex]).rules('add', { number: true, maxlength: 10});
	addRules(tableIndex, false);

	getSum();

}

function addRules(tableIndex, init) {
	colName = colNames[tableIndex];
	if (init) {
		for (var i = 0; i < colCount[tableIndex]; i++) {
			$('#' + colName + "-" + (i + 1)).rules('add', { number: true, maxlength: 10});
		}
	} else {
		
		$('#' + colName + "-" + colCount[tableIndex]).rules('add', { number: true, maxlength: 10});
	}
}

function removeCol(tableIndex, colIndex) {
	var table;
	var colName;
	var col = -1;
	var removeColCount = 0;
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
	case 8:
		table = $('#other');
		break;
	}

	colName = colHeaderPrefix + colNames[tableIndex] + "-" + colIndex;
	
	$(table).find("td").each(function(){
		removeColCount++;
		if ($(this).attr('id') == colName) {
			col = removeColCount;
		}
	});

	$('#' + colNames[tableIndex] + "-" + colIndex).rules('remove');
	
	var removeCol = $(table).find("tr").eq(0).children();
	removeCol.eq(col - 1).remove();
	removeCol = $(table).find("tr").eq(1).children();
	removeCol.eq(col - 1).remove();
	removeCol = $(table).find("tr").eq(2).children();
	removeCol.eq(col - 1).remove();
	
	colCount[tableIndex]--;
}

function getSum() {
	var totalSum = 0.0;
	for (var i = 0; i < colNames.length; i++) {
		var sum = 0.0;
		for (var j = 0; j < colCount[i]; j++) {
			var txtName = colNames[i] + "-" + (j + 1);
			if ($('#' + txtName).length > 0) {
				if (isFinite($('#' + txtName).val())) {
					if ($('#' + txtName).val() != '') {
						sum += parseFloat($('#' + txtName).val());
					}
				}
			}
		}

		$('#' + colNames[i] + "-sum").html(sum);
		totalSum += sum;
	}
	
	$('#totalInput').html(totalSum);
	
	if ($('#salePrice').val() != '' && $('#exchangeRate').val() != '') {
		if (isFinite($('#salePrice').val()) && isFinite($('#exchangeRate').val())) {
			$('#originalPrice').val((parseFloat($('#salePrice').val()) * parseFloat($('#exchangeRate').val())).toFixed(2));
		}
	}
	
	if ($('#totalInput').html() != '' && $('#originalPrice').val() != '') {
		if (isFinite($('#totalInput').val()) && isFinite($('#estimateCost').val())) {
			$('#recoveryNum').val((parseFloat($('#totalInput').html()) / (parseFloat($('#originalPrice').val()) - parseFloat($('#estimateCost').val()))).toFixed(2));
		}
	}
		
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
				
			<div id="tabsContainer1" style="width:330px;height:300px;display:none;">
				<div id="tabs" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true" style="margin:10px 0px 0px 15px;padding:0px;display:none;">
					<div id="tabs-1" title="图片" style="padding:5px;height:300px;">
						<jsp:include page="../../common/album/multialbum.jsp">
							<jsp:param value="1" name="index"/>
							<jsp:param value="3" name="albumCount"/>
						</jsp:include>
					</div>
				</div>
			</div>
			<div id="tabsContainer2" style="width:330px;height:300px;display:none;">
				<div id="tabs2" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true" style="margin:10px 0px 0px 15px;padding:0px;display:none;">
					<div id="tabs-2" title="图片" style="padding:5px;height:300px;">
						<jsp:include page="../../common/album/multialbum.jsp">
							<jsp:param value="2" name="index"/>
							<jsp:param value="3" name="albumCount"/>
						</jsp:include>
					</div>
				</div>
			</div>	
			<div  id="tabsContainer3" style="width:330px;height:300px;display:none;">
				<div id="tabs3" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true" style="margin:10px 0px 0px 15px;padding:0px;display:none;">
					<div id="tabs-3" title="图片" style="padding:5px;height:300px;">
						<jsp:include page="../../common/album/multialbum.jsp">
							<jsp:param value="3" name="index"/>
							<jsp:param value="3" name="albumCount"/>
						</jsp:include>

					</div>
				</div>
			</div>
			
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
							<td width="100px">项目编号：</td>
							<td width="240px">
								<input type="text" id="projectId" name="projectId" class="short" value="${DisplayData.projectTaskData.projectid}"/>
							</td>
							<td width="80px">项目名称：</td> 
							<td width="240px">
								<input type="text" id="projectName" name="projectName" class="short" value="${DisplayData.projectTaskData.projectname}"/>
							</td>
							<td width="80px">暂定型号：</td> 
							<td width="240px">
								<input type="text" id="tempVersion" name="tempVersion" class="short" value="${DisplayData.projectTaskData.tempversion}"/>
							</td>							
							<td width="80px">
								项目经理：
							</td>
							<td width="240px">
								<input type="text" id="manager" name="manager" class="short" value="${DisplayData.projectTaskData.manager}"/>
							</td>
						</tr>
						<tr>
							<td>
								参考原型：
							</td>
							<td>
								<input type="text" id="referPrototype" name="referPrototype" class="short" value="${DisplayData.projectTaskData.referprototype}"/>
							</td>
							<td>	
								起始时间：
							</td>
							<td> 
								<input type="text" id="beginTime" name="beginTime" class="short" value="${DisplayData.projectTaskData.begintime}"/>
							</td>
							<td>	
								预计完成<p>时间：
							</td>
							<td> 
								<input type="text" id="endTime" name="endTime" class="short" value="${DisplayData.projectTaskData.endtime}"/>
							</td>		
							<td colspan=5>
							</td>					
						</tr>
						<tr>
							<td>
								设计性能：
							</td>
							<td colspan=7> 
								<textarea id="designCapability" name="designCapability" rows=5 cols=120 class="long">${DisplayData.projectTaskData.designcapability}</textarea>
							</td>
						</tr>

						<tr>
							<td>
								包装描述： 
							</td>
							<td colspan=7>
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
							<td colspan=5>
								<input type="text" id="estimateCost" name="estimateCost" style="resize:none;width=200px;height=50px;" class="middle" value="${DisplayData.projectTaskData.estimatecost}" oninput='getSum();'/>
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
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-auth-sum" align="center" ><label id="auth-sum" name="auth-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
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
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-patentQuery-sum" align="center" ><label id="patentQuery-sum" name="patentQuery-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
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
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-design-sum" align="center" ><label id="design-sum" name="design-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
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
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-trialProduce-sum" align="center" ><label id="trialProduce-sum" name="trialProduce-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
								<td></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>
					<div>			
						9.其他
						<button type="button" class="DTTT_button" id="addOther" onClick="addNew(8);">新建</button>
					</div>
					<div class="list">
						<table id='other' class="editableTable">
							<tr height=30>
								<td align="center">合计</td>
							</tr>
							<tr height=30>
								<td id="td-trialProduce-sum" align="center" ><label id="trialProduce-sum" name="trialProduce-sum" style="width:60px;"></label></td>							
							</tr>
							<tr height=30>
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
									币种
								</td>
								<td align="center">
									汇率
								</td>
								</td>
								<td align="center">
									平均销售价格
								</td>
								<td align="center">
									原币价格
								</td>								
								<td align="center">
									年预期销量
								</td>
								<td align="center">
									回本数量
								</td>
							</tr>
							<tr>
								<td>
									<form:select path="currency">
										<form:options items="${DisplayData.currencyList}" itemValue="key"
											itemLabel="value" />
									</form:select>
								</td>
								<td>
									<input type="text" id="exchangeRate" name="exchangeRate" value="${DisplayData.projectTaskData.exchangerate}" oninput='getSum();'></input>
								</td>
								<td width="60px"><input type="text" id="salePrice" name="salePrice" value="${DisplayData.projectTaskData.saleprice}" oninput='getSum();'></input></td>
								<td width="60px"><input type="text" id="originalPrice" name="originalPrice" disabled></input></td>
								<td><input type="text" id="sales" name="sales" value="${DisplayData.projectTaskData.sales}"></input></td>
								<td><input type="text" id="recoveryNum" name="recoveryNum" value="${DisplayData.projectTaskData.recoverynum}" disabled></input></td>
							</tr>
						</table>
					</div>
					<div  style="height:10px"></div>
					<legend>失败模式</legend>
					<div  style="height:10px"></div>
					<div class="list">
						<textarea id="failMode" name="failMode" rows=5 cols=120>${DisplayData.projectTaskData.failmode}</textarea>
					</div>
					<div  style="height:10px"></div>
					<legend>进程预期</legend>
					<div class="list" style="width:400px;">
						<table id="processDetail" class="display" cellspacing="0">
							<tr class="selected">
								<td style="width: 80px;" class="dt-middle"></td>
								<td style="width: 240px;" class="dt-middle" align="center">预期完成</td>
								<td style="width: 80px;" class="dt-middle" align="center">未使用</td>
							</tr>
							<tr>
								<td align="left" class="dt-middle">3D完成</td>
								<td align="center"><input type="text" id="expectDate-0" name="expectDate-0" value="${DisplayData.expectDateList[0]}"></input></td>
								<td align="center"><input type="checkbox" id="noUse-0" name="noUse-0" onClick="setNoUse(0)"></input></td>
							</tr>
							<tr>
								<td align="left" class="dt-middle">3D手模</td>
								<td align="center"><input type="text" id="expectDate-1" name="expectDate-1" value="${DisplayData.expectDateList[1]}"></input></td>
								<td align="center"><input type="checkbox" id="noUse-1" name="noUse-1" onClick="setNoUse(1)"></input></td>
							</tr>
							<tr>
								<td align="left" class="dt-middle">3D工作样机</td>
								<td align="center"><input type="text" id="expectDate-2" name="expectDate-2" value="${DisplayData.expectDateList[2]}"></input></td>
								<td align="center"><input type="checkbox" id="noUse-2" name="noUse-2" onClick="setNoUse(2)"></input></td>
							</tr>
							<tr>
								<td align="left" class="dt-middle">模具确认</td>
								<td align="center"><input type="text" id="expectDate-3" name="expectDate-3" value="${DisplayData.expectDateList[3]}"></input></td>
								<td align="center"><input type="checkbox" id="noUse-3" name="noUse-3" onClick="setNoUse(3)"></input></td>
							</tr>
							<tr>
								<td align="left" class="dt-middle">模具完成</td>
								<td align="center"><input type="text" id="expectDate-4" name="expectDate-4" value="${DisplayData.expectDateList[4]}"></input></td>
								<td align="center"><input type="checkbox" id="noUse-4" name="noUse-4" onClick="setNoUse(4)"></input></td>
							</tr>
							<tr>
								<td align="left" class="dt-middle">模具调整</td>
								<td align="center"><input type="text" id="expectDate-5" name="expectDate-5" value="${DisplayData.expectDateList[5]}"></input></td>
								<td align="center"><input type="checkbox" id="noUse-5" name="noUse-5" onClick="setNoUse(5)"></input></td>
							</tr>
							<tr>
								<td align="left" class="dt-middle">委外加工</td>
								<td align="center"><input type="text" id="expectDate-6" name="expectDate-6" value="${DisplayData.expectDateList[6]}"></input></td>
								<td align="center"><input type="checkbox" id="noUse-6" name="noUse-6" onClick="setNoUse(6)"></input></td>
							</tr>
							<tr>
								<td align="left" class="dt-middle">试产</td>
								<td align="center"><input type="text" id="expectDate-7" name="expectDate-7" value="${DisplayData.expectDateList[7]}"></input></td>
								<td align="center"><input type="checkbox" id="noUse-7" name="noUse-7" onClick="setNoUse(7)"></input></td>
							</tr>
							<tr>
								<td align="left" class="dt-middle">文档整理</td>
								<td align="center"><input type="text" id="expectDate-8" name="expectDate-8" value="${DisplayData.expectDateList[8]}"></input></td>
								<td align="center"><input type="checkbox" id="noUse-8" name="noUse-8" onClick="setNoUse(8)"></input></td>
							</tr>
						</table>					
					</div>
					
				</form:form>
				
			</div>
			
			

</html>
