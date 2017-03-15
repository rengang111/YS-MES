<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common.jsp"%>

<title>外样基本数据</title>
<script type="text/javascript">
var validator;
var layerHeight = "250";

function initEvent(){

	controlButtons($('#keyBackup').val());
	
}

$(document).ready(function() {

	//$("#tabs").tabs();
	
    $('#tabs').tabs({
        width: $("#tabs").parent().width(),  
        height: "270"  
    });   
	
    $('#tabs2').tabs({
        width: $("#tabs2").parent().width(),  
        height: "270"  
    });

    
	resetFinder(0, 2);
	
	if ($('#keyBackup').val() != "") {
		$('#tabs').show();
		$('#tabs2').show();
		refreshFileBrowser(0);
		refreshFileBrowser(1);
	}
	
	initEvent();
	
	validator = $("#externalSampleInfo").validate({
		rules: {
			sampleId: {
				required: true,
				minlength: 6,
				maxlength: 20,
			},
			sampleVersion: {
				required: true,				
				maxlength: 20,
			},
			sampleName: {
				required: true,								
				maxlength: 50,
			},
			brand: {
				required: true,					
				maxlength: 10,
			},
			buyTime: {				
				date: true,
			}
			
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

	$("#currency").val("${DisplayData.externalSampleData.currency}");
	
	//refreshTestFileList();

})

function refreshTestFileList() {
	
	var key = $('#keyBackup').val();
	var tabTitle = getTabTitle();
	var url = "${ctx}/business/externalsample?methodtype=getfilelist&key=" + key + "&tabTitle=" + tabTitle;
	
	$.ajax({
		contentType : 'application/json',
		dataType : 'json',						
		type : "POST",
		data : '',// 要提交的表单						
		url : url,
		success : function(d) {
			
			$('#TESTFileList').empty();
			
			var tr = '';
			var rdPerRow = 10;
			var dArray = d.toString().split(",");
			for(var i = 0; i < dArray.length; i++) {
				if (i % rdPerRow == 0) {
					tr = '	<tr>';
				}
				tr += '		<td width=100>';
				tr += '			<div style="color:#6d6d6d;text-align:left;font-size:15px;font-weight:bold" onclick="openFolder(\'' + dArray[i] + '\');">';
				tr += '			<img id="folderImg-' + i + '" name="folderImg-' + i + '" src="${ctx}/images/folder.ico" style="vertical-align:bottom;width:30px;height:30px;"/></div>';
				tr += '         <div>' + dArray[i] + '</div>'
				tr += '		</td>';
				if ((i + 1) % rdPerRow == 0) {
					tr += '	</tr>';
				} else {
					if (i == dArray.length - 1) {
						tr += '	</tr>';
					}
				}
			}
			$('#TESTFileList').append(tr);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert(XMLHttpRequest.status);
			//alert(XMLHttpRequest.readyState);
			//alert(textStatus);
			//alert(errorThrown);
			
			//发生系统异常，请再试或者联系管理员。
			alert("发生系统异常，，请再试或者联系管理员。");
		}
	});
}

function openFolder(folderName) {
	var key = $('#keyBackup').val();
	var tabTitle = getTabTitle();
	var url = "${ctx}/business/externalsample?methodtype=openfilebrowser&key=" + key + "&tabTitle=" + tabTitle + "&folderName=" + folderName;
	//openLayer(url, '', $(window).height(), false);	
}

function doSave() {

	if (validator.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/externalsample?methodtype=add";				
		} else {
			//修正
			actionUrl = "${ctx}/business/externalsample?methodtype=update";
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
				data : JSON.stringify($('#externalSampleInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						$('#keyBackup').val(d.info);
						$('#tabs').show();
						refreshFileBrowser(0);
						refreshFileBrowser(1);
						reloadTabWindow();
						controlButtons(d.info);
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					//var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
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
			url : "${ctx}/business/externalsample?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					$('#tabs').hide();
					$('#TESTFileArea').hide();
					$('#TESTMachineArea').hide();
					controlButtons("");
					clearExternalSampleInfo();
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

function doReturn() {
	//var url = "${ctx}/business/externalsample";
	//location.href = url;	
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	//parent.$('#events').DataTable().destroy();/
	//parent.reload_contactor();
	parent.layer.close(index); //执行关闭
	
}

function doDocumentManageTest() {
	var key = $('#keyBackup').val();
	var tabTitle = getTabTitle();
	var url = "${ctx}/business/externalsample?methodtype=openfilebrowser&key=" + key + "&tabTitle=" + tabTitle;
	//openLayer(url, '', $(window).height(), false);
}

function refreshFileBrowser(id) {

	//var key = $('#keyBackup').val();
	//var tabTitle = getTabTitle();
	//var url = "${ctx}/business/externalsample?methodtype=openfilebrowser&key=" + key + "&tabTitle=" + tabTitle;
	//var url = "${ctx}/jsp/common/filebrowser.jsp";
	//$("#TESTFileArea").load(url);
	if (id == 0) {
		$("#TESTFileArea").html("");
		$("#TESTFileArea").show();
		doRefreshFileBrowser("TESTFileArea", id, "TestReport");
	}
	if (id == 1) {
		$("#TESTMachineArea").html("");
		$("#TESTMachineArea").show();
		doRefreshFileBrowser("TESTMachineArea", id, "MachineInfo");
	}
	
}

function clearExternalSampleInfo() {
	$('#sampleId').val('');
	$('#sampleVersion').val('');
	$('#sampleName').val('');
	$('#brand').val('');
	$('#buyTime').val('');
	$('#currency').val('');
	$('#price').val('');
	$("#address").val('');
	$("#memo").val('');
}

function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		$('#delete').attr("disabled", true);
		$('#addesfilemachine').attr("disabled", true);
		$('#deleteesfilemachine').attr("disabled", true);
		$('#addesfiletest').attr("disabled", true);
		$('#deleteesfiletest').attr("disabled", true);
		$('#managedoctest').attr("disabled", true);
	} else {
		$('#delete').attr("disabled", false);
		$('#addesfilemachine').attr("disabled", false);
		$('#addesfiletest').attr("disabled", false);
		$('#managedoctest').attr("disabled", false);
	}
}
</script>

</head>

<body>
<div id="container">

		<div id="main">	
			<div style="width:330px;height:270px;display:inline;">
				<div id="tabs" style="margin:10px 0px 0px 15px;padding:0px;display:none;">
					3333333
				</div>

			</div>
			<div style="width:330px;height:270px;display:inline;">
				<div id="tabs2" style="margin:10px 0px 0px 15px;padding:0px;display:none;">
					11112222
				</div>
			</div>			
			
			<div  style="height:20px"></div>
				
				<legend>外样记录-综合信息</legend>
					
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<form:form modelAttribute="dataModels" id="externalSampleInfo" style='padding: 0px; margin: 10px;' >
					<input type="hidden" id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
					<table class="form" width="850px">
						<tr>
							<td width="60px">样品编号：</td>
							<td width="320px">
								<input type="text" id="sampleId" name="sampleId" class="short" value="${DisplayData.externalSampleData.sampleid}"/>
							</td>
							<td width="60px">样品型号：</td> 
							<td>
								<input type="text" id="sampleVersion" name="sampleVersion" class="short" value="${DisplayData.externalSampleData.sampleversion}"/>
							</td>
						</tr>
						<tr>
							<td>样品名称：</td> 
							<td>
								<input type="text" id="sampleName" name="sampleName" class="middlelong" value="${DisplayData.externalSampleData.samplename}"/>
							</td>
							<td>
								品牌：
							</td>
							<td>
								<input type="text" id="brand" name="brand" class="mini" value="${DisplayData.externalSampleData.brand}"/>
							</td>
						</tr>
						<tr>
							<td>
								采购时间：
							</td>
							<td>
								<input type="text" id="buyTime" name="buyTime" class="short" value="${DisplayData.externalSampleData.buytime}"/>
							</td>
							<td>	
								采购地点：
							</td>
							<td> 
								<input type="text" id="address" name="address" class="middle" value="${DisplayData.externalSampleData.address}"/>
							</td>
						</tr>
						<tr>
							<td>
								计价货币：
							</td>
							<td> 
								<form:select path="currency">
									<form:options items="${DisplayData.currencyList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>
							<td>
								价格：
							</td>
							<td>
								<input type="text" id="price" name="price" class="mini" value="${DisplayData.externalSampleData.price}"/>
							</td>
						</tr>

						<tr>
							<td>
								产品描述： 
							</td>
							<td colspan=2>
								<input type="text" id="memo" name="memo" style="resize:none;width=200px;height=50px;" class="long" value="${DisplayData.externalSampleData.memo}"/>
							</td>
							<td></td>
						</tr>
					</table>

				</form:form>
			</div>
			
			<div  style="height:20px"></div>
				
			<div>
				<legend>测试报告</legend>
				<div class="list" style="height:440">
					<div id="TESTFileArea" style="display:none;">
						<!-- 
						<table id="TESTFileList" class="display" cellspacing="0">
						</table>
						 -->
						 <%@ include file="../../common/filebrowser.jsp"%>
					</div>
				</div>
			</div>
	
			<div  style="height:20px"></div>
				
			<div>
				<legend>机器详情</legend>
				<div class="list">
					<div id="TESTMachineArea" style="display:none;">
						<!-- 
						<table id="TESTMachinePicList" class="display" cellspacing="0">
						</table>
						-->
						<%@ include file="../../common/filebrowser.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
