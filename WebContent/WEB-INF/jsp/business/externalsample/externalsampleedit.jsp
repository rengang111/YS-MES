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

function ajaxTestFile() {
	var table = $('#TESTestFileList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TESTestFileList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/esrelationfile?methodtype=searchtestfile",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#externalSampleInfo").serializeArray();
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
		        	/*
		        	dom : 'T<"clear">rt',

					"tableTools" : {

						"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

						"aButtons" : [										
								{
									"sExtends" : "create",
									"sButtonText" : "新建"
								},								
								{
									"sExtends" : "Delete",
									"sButtonText" : "删除"
								},
						]
					},
					*/
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "filename", "className" : 'td-center'}, 
						{"data" : "path", "className" : 'td-center'},
						{"data" : "memo", "className" : 'td-center'}, 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheckTest' id='numCheckTest' value='" + row["id"] + "' />"
	                    }},
			    		{"targets":4,"render":function(data, type, row){
			    			return "<a href=\"#\" onClick=\"doUpdateESFileTest('" + row["id"] + "')\">编辑</a>"
	                    }}
				    ] 						
				});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	/*
	t.on('dblclick', 'tr', function() {

		var d = t.row(this).data();

		
		layer.open({
			type : 2,
			title : false,
			area : [ '900px', '370px' ],
			scrollbar : false,
			title : false,
			closeBtn: 0, //不显示关闭按钮
			content : '${ctx}/business/customer/contactedit?name=' + d["name"] + '&id=' + $('#customerID').val()
		});
	});
	*/
	
	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

		//alert(999);

		var tr = $(this).closest('tr');
		t
		var row = t.row(tr);
		t

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

function ajaxMachinePic() {
	var table = $('#TESMachinePicList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var t = $('#TESMachinePicList').DataTable({
					"paging": false,
					"lengthMenu":[5],//设置一页展示10条记录
					"processing" : false,
					"serverSide" : true,
					"stateSave" : false,
					"searching" : false,
					"retrieve" : true,
					"sAjaxSource" : "${ctx}/business/esrelationfile?methodtype=searchmachinefile",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#externalSampleInfo").serializeArray();
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
		        	/*
		        	dom : 'T<"clear">rt',

					"tableTools" : {

						"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

						"aButtons" : [										
								{
									"sExtends" : "create",
									"sButtonText" : "新建"
								},								
								{
									"sExtends" : "Delete",
									"sButtonText" : "删除"
								},
						]
					},
					*/
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "filename", "className" : 'td-center'}, 
						{"data" : "path", "className" : 'td-center'},
						{"data" : "memo", "className" : 'td-center'}, 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
							return row["rownum"] + "<input type=checkbox name='numCheckMachine' id='numCheckMachine' value='" + row["id"] + "' />"
	                    }},
			    		{"targets":4,"render":function(data, type, row){
			    			return "<a href=\"#\" onClick=\"doUpdateESFileMachine('" + row["id"] + "')\">编辑</a>"
	                    }}
				    ] 						
				});

	t.on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	/*
	t.on('dblclick', 'tr', function() {

		var d = t.row(this).data();

		
		layer.open({
			type : 2,
			title : false,
			area : [ '900px', '370px' ],
			scrollbar : false,
			title : false,
			closeBtn: 0, //不显示关闭按钮
			content : '${ctx}/business/customer/contactedit?name=' + d["name"] + '&id=' + $('#customerID').val()
		});
	});
	*/
	
	// Add event listener for opening and closing details
	t.on('click', 'td.details-control', function() {

		//alert(999);

		var tr = $(this).closest('tr');
		t
		var row = t.row(tr);
		t

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

$.fn.dataTable.TableTools.buttons.create = $
.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {

			}
		});
		
$.fn.dataTable.TableTools.buttons.Delete = $
.extend(
		true,
		{},
		$.fn.dataTable.TableTools.buttonBase,
		{
			"fnClick" : function(button) {

			}
		});
		
function initEvent(){

	ajaxTestFile();
	ajaxMachinePic();

	controlButtons($('#keyBackup').val());
	
	$('#TESMachinePicList').DataTable().on('click', 'tr', function() {
		
		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
        	$('#TESMachinePicList').DataTable().$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});
	
	/*
	$('#TESMachinePicList').DataTable().on('dblclick', 'tr', function() {

		var d = $('#TESMachinePicList').DataTable().row(this).data();

		location.href = '${pageContext.request.contextPath}/factory/show/' + d["factory_id"] + '.html';		
		
	});
	*/
}

function reloadMachinePicList() {

	$('#TESMachinePicList').DataTable().ajax.reload(null,false);
	reloadTabWindow();

	return true;
}

function reloadTestFileList() {

	$('#TESTestFileList').DataTable().ajax.reload(null,false);
	reloadTabWindow();

	return true;
}

$(document).ready(function() {

	$("#tabs").tabs();
	$('#tabs').width('330px');
	$('#tabs').height('270px');
	$('#tabs-1').height('300px');

	if ($('#keyBackup').val() != "") {
		$('#tabs').show();
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
})

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
						$('#tabs').show();
						reloadTabWindow();
						controlButtons(d.info);
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
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
			url : "${ctx}/business/externalsample?methodtype=deleteDetail",
			data : $('#keyBackup').val(),// 要提交的表单
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);	
				} else {
					$('#tabs').hide();
					controlButtons("");
					clearExternalSampleInfo();
					reloadMachinePicList();
					reloadTestFileList();
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

function doAddESFileTest() {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/esrelationfile?methodtype=addtestinit&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function doUpdateESFileTest(key) {		
	var url = "${ctx}/business/esrelationfile?methodtype=updatetestinit&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);
}

function doDeleteESFileTest() {
	var str = '';
	$("input[name='numCheckTest']").each(function(){
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
			url : "${ctx}/business/esrelationfile?methodtype=deletetest",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadMachinePicList();
					reloadTestFileList();
				}
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
		
	} else {
		alert("请先选中要删除的记录。");
	}

}

function doAddESFileMachine() {
	var key = $('#keyBackup').val();
	var url = "${ctx}/business/esrelationfile?methodtype=addmachineinit&key=" + key;
	openLayer(url, $(document).width() - 25, layerHeight, false);	
}

function doUpdateESFileMachine(key) {		
	var url = "${ctx}/business/esrelationfile?methodtype=updatemachineinit&key=" + key;
	openLayer(url, '', layerHeight, false);
}

function doDeleteESFileMachine() {
	var str = '';
	$("input[name='numCheckMachine']").each(function(){
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
			url : "${ctx}/business/esrelationfile?methodtype=deletemachine",
			success : function(d) {
				if (d.rtnCd != "000") {
					alert(d.message);
				} else {
					reloadMachinePicList();
					reloadTestFileList();
				}
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
		
	} else {
		alert("请先选中要删除的记录。");
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
		
	} else {
		$('#delete').attr("disabled", false);
		$('#addesfilemachine').attr("disabled", false);
		$('#addesfiletest').attr("disabled", false);
	}
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
				
				<legend>外样记录-综合信息</legend>
					
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<form:form modelAttribute="dataModels" id="externalSampleInfo" style='padding: 0px; margin: 10px;' >
					<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
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
							<td colspan=1></td>
						</tr>
					</table>

				</form:form>
			</div>
			
			<div  style="height:20px"></div>
				
			<div>
				<legend>测试报告</legend>
				<button type="button" id="deleteesfiletest" class="DTTT_button" onClick="doDeleteESFileTest();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
				<button type="button" id="addesfiletest" class="DTTT_button" onClick="doAddESFileTest();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
				<div style="height:10px"></div>
				<div class="list">
					<table id="TESTestFileList" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 80px;" class="dt-middle">No</th>
								<th style="width: 80px;" class="dt-middle">文件名</th>
								<th style="width: 30px;" class="dt-middle">路径</th>
								<th style="width: 80px;" class="dt-middle">说明</th>
								<th style="width: 80px;" class="dt-middle">操作</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
		</div>			
			
		<div  style="height:20px"></div>
			
		<div>
			<legend>机器详情</legend>
			<button type="button" id="deleteesfilemachine" class="DTTT_button" onClick="doDeleteESFileMachine();"
					style="height:25px;margin:-20px 30px 0px 0px;float:right;" >删除</button>
			<button type="button" id="addesfilemachine" class="DTTT_button" onClick="doAddESFileMachine();"
					style="height:25px;margin:-20px 5px 0px 0px;float:right;" >新建</button>
			<div style="height:10px"></div>
			<div class="list">
				<table id="TESMachinePicList" class="display" cellspacing="0">
					<thead>
						<tr class="selected">
							<th style="width: 80px;" class="dt-middle">No</th>
							<th style="width: 80px;" class="dt-middle">文件名</th>
							<th style="width: 30px;" class="dt-middle">路径</th>
							<th style="width: 80px;" class="dt-middle">说明</th>
							<th style="width: 80px;" class="dt-middle">操作</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>

</html>
