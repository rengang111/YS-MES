<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具合同</title>
<script type="text/javascript">

var validatorBaseInfo;
var layerHeight = "250";
//var sumPrice = 0.0;

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
					"sAjaxSource" : "${ctx}/business/mouldcontract?methodtype=getMouldDetailList",
					"fnServerData" : function(sSource, aoData, fnCallback) {
						var param = {};
						var formData = $("#mouldContractBaseInfo").serializeArray();
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
				             }
						})
					},
						
					"language": {
		        		"url":"${ctx}/plugins/datatables/chinese.json"
		        	},
					"columns" : [ 
						{"data": null, "defaultContent" : '', "className" : 'td-center'}, 
						{"data" : "type", "className" : 'td-center'},
						{"data" : "no", "className" : 'td-center'}, 
						{"data" : "name", "className" : 'td-center'},
						{"data" : "size", "className" : 'td-center'}, 
						{"data" : "materialQuality", "className" : 'td-center'},
						{"data" : "unloadingNum", "className" : 'td-center'},
						{"data" : "weight", "className" : 'td-center'},
						{"data" : "price", "className" : 'td-center'},
						{"data" : "mouldFactory", "className" : 'td-center'},
					],
					"columnDefs":[
			    		{"targets":0,"render":function(data, type, row){
			    			if (row["id"] == "") {
			    				return "合计"
			    			} else {
			    				if (row["selected"] == '0') {
									return row["rownum"] + "<input type=checkbox name='numCheckMD' id='numCheckMD' value='" + row["id"] + "' onChange='getSumPrice();' />"
			    				} else {
			    					return row["rownum"] + "<input type=checkbox name='numCheckMD' id='numCheckMD' value='" + row["id"] + "' onChange='getSumPrice();' checked/>"
			    				}
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

    jQuery.validator.addMethod("contractYear",function(value, element){ 
    	var rtnValue = false;
    	if (value != '') {
  			if (value >= '1950' && value <= '2050') {
  				rtnValue = true;
   			}
    	} else {
    		if ($('#contractId').html() != '') {
    			rtnValue = true;
    		}
    	}
        return rtnValue;  
    }, "合同年份不正确(1950年-2050年，且必须输入)"); 
	
	validatorBaseInfo = $("#mouldContractBaseInfo").validate({
		rules: {
			contractYear: {
				contractYear: true,
				minlength: 1,
				maxlength: 4,
			},
			productModelIdView: {
				required: true,				
				maxlength: 120,
			},
			payCase: {
				required: true,
				digits: true,
				maxlength: 50,
			},
			finishTime: {				
				date: true,
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
	
	if ($('#keyBackup').val() == '') {
		var d = new Date();
		$('#contractYear').val(d.getFullYear());
	}
	
	autoComplete();
}

$(window).load(function(){
	initEvent();
});

$(document).ready(function() {

	ajaxMouldDetailList();
	
	$("#finishTime").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		selectOtherMonths:true,
		showOtherMonths:true,
	});
	if ($("#finishTime").val() == "") {
		$("#finishTime").datepicker( 'setDate' , new Date() );
	}

})

function getSumPrice() {
	var length = $("#MouldDetailList tr").length;
	var sumPrice = 0.0;
	
	for (var i = 2; i < (length - 1); i++) {
		var tr = $("#MouldDetailList tr").eq(i);
		if ($(tr).find("td").eq(0).find("input").prop('checked')) {
			sumPrice += parseFloat($(tr).find("td").eq(8).html());//收入类别
		}
   	}
	
	var tr = $("#MouldDetailList tr").eq(length - 1);
	$(tr).find("td").eq(8).html(sumPrice);
}

function reloadMouldDetailList() {
	$('#MouldDetailList').DataTable().ajax.reload(null,false);
}

function doSave() {

	if (validatorBaseInfo.form()) {
		
		var message = "${DisplayData.endInfoMap.message}";
		
		if ($('#keyBackup').val() == "") {				
			//新建
			actionUrl = "${ctx}/business/mouldcontract?methodtype=add";
		} else {
			//修正
			actionUrl = "${ctx}/business/mouldcontract?methodtype=update";
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
				data : JSON.stringify($('#mouldContractBaseInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						//reloadMouldDetailList();
						reloadTabWindow();
					}
					
					//不管成功还是失败都刷新父窗口，关闭子窗口
					//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
					//parent.$('#events').DataTable().destroy();
					//parent.layer.close(index); //执行关闭
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
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
			url : "${ctx}/business/mouldcontract?methodtype=deleteDetail",
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
				/*	
				//不管成功还是失败都刷新父窗口，关闭子窗口
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				//parent.$('#events').DataTable().destroy();/
				parent.reload_contactor();
				parent.layer.close(index); //执行关闭
				*/
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
}

function controlButtons(data) {
	$('#keyBackup').val(data);
	if (data == '') {
		$('#delete').attr("disabled", true);
		$('#printmd').attr("disabled", true);
		
	} else {
		$('#delete').attr("disabled", false);
		$('#printmd').attr("disabled", false);
	}
}

function clearAll() {
	sumPrice = 0;
	$('#contractId').val("");
	$('#productModelId').val("");
	$('#productModelIdView').val("");
	$("#productModelName").val("");
	$('#payCase').val("");
	$('#finishTime').val("");
}

function autoComplete() { 

	$("#productModelIdView").autocomplete({
		source : function(request, response) {
			$.ajax({
				type : "POST",
				url : "${ctx}/business/mouldcontract?methodtype=productModelIdSearch",
				dataType : "json",
				data : {
					key : $('#mouldFactoryId').val()
				},
				success : function(data) {
					response($.map(
						data.data,
						function(item) {
							//alert(item.viewList)
							return {
								label : item.viewList,
								value : item.name,
								id : item.id,
								name: item.name,
								des : item.des
							}
						}));
				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
				}
			});
		},

		select : function(event, ui) {
			var oldProductModelId = $("#productModelId").val();
			$("#productModelId").val(ui.item.id);
			$("#productModelIdView").val(ui.item.name);
			$("#productModelName").val(ui.item.des);
			if (oldProductModelId != $("#productModelId").val()) {
				reloadMouldDetailList();
			}
		},
		minLength : 1,
		autoFocus : false,
		width: 200,
		mustMatch:true,
		autoFill:true,
		selectFirst:true,
	});	
}

function doReturn() {

	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	parent.layer.close(index); //执行关闭
	
}
</script>
</head>

<body>
<div id="container">

		<div id="main">					
			<div  style="height:20px"></div>
			<form:form modelAttribute="dataModels" id="mouldContractBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<input type=hidden id="productModelId" name="productModelId" value=""/>
				<legend>模具合同-基本信息</legend>
				<div style="height:10px"></div>
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<table class="form" width="850px">
					<tr>
						<td width="90px">模具合同年份：</td>
						<td >
							<input type="text" id="contractYear" name="contractYear" class="small" value='${DisplayData.mouldContractBaseInfoData.year}'></>
						</td>
						<td width="60px">产品型号：</td> 
						<td>
							<form:input path="productModelIdView" class="required short"/>
						</td>
						<td width="60px">产品名称：</td> 
						<td>
							<input type=text id="productModelName" name="productModelName" class="read-only" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							付款条件：
						</td>
						<td>
							交付后
							<input type="text" id="payCase" name="payCase" class="small" value="${DisplayData.mouldContractBaseInfoData.paycase}"></input>
							天
						</td>
						<td>	
							完成时间：
						</td>
						<td colspan=3> 
							<input type="text" id="finishTime" name="finishTime" class="short" value="${DisplayData.mouldContractBaseInfoData.finishtime}"></input>
						</td>
					</tr>
				</table>			
				
				<div  style="height:20px"></div>
				<legend>模具详情</legend>
				<div>
				<button type="button" id="printmd" class="DTTT_button" onClick="doPrintContract();"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >打印模具合同</button>				
				</div>
				<div style="height:10px"></div>
				<div class="list">
					<table id="MouldDetailList" class="display" cellspacing="0">
						<thead>
							<tr class="selected">
								<th style="width: 40px;" class="dt-middle">No</th>
								<th style="width: 40px;" class="dt-middle">类型</th>
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
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
			</form:form>
		</div>
</html>
