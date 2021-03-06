<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具单元基本信息管理</title>
<script type="text/javascript">
	var validatorBaseInfo;
	var layerHeight = "380";
	var sumPrice = 0.0;
	var paid = 0.0;    
	
	var datas = new Array();
	
	function PrefixInteger(num, length) {
		 return (Array(length).join('0') + num).slice(-length);
	} 
	
	function ajax() {
		var table = $('#TFactory').dataTable();
		if(table) {
			table.fnDestroy();
		}

		var t = $('#TFactory').DataTable({
				"paging": false,
				"lengthMenu":[5,10,15],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/mouldregister?methodtype=getSubCodeFactoryList",
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var param = {};
					var formData = $("#mouldBaseInfo").serializeArray();
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
							{"data": "supplierId", "defaultContent" : '',"className" : 'td-left'},
							{"data": "shortName", "defaultContent" : '',"className" : 'td-left'},
							{"data": "supplierName", "defaultContent" : '',"className" : 'td-left'},
							{"data": "price", "defaultContent" : '',"className" : 'td-right'},
							{"data": "currency", "defaultContent" : '',"className" : 'td-center'},
							{"data": "priceUnit", "defaultContent" : '',"className" : 'td-center'},
							{"data": "priceTime", "defaultContent" : '',"className" : 'td-center'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								//return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
								return row["rownum"];
		                    }},
							{"targets": 3, "createdCell": function (td, cellData, rowData, row, col) {
						        $(td).attr('title', cellData);
							}},
				    		{"targets":8,"render":function(data, type, row){
				    			return "<a href=\"#\" onClick=\"doUpdateFactory('" + row["id"] + "')\">编辑</a>&nbsp;" + "<a href=\"#\" onClick=\"viewHistoryPrice('" + row["mouldFactoryId"] + "')\">历史报价</a>&nbsp;" + "<a href=\"#\" onClick=\"doDeleteFactory('" + row["id"] + "')\">删除</a>"
		                    }}
			         ] 
			}
		);
	}
	
	function doCreateFactory() {
		var key = $('#keyBackup').val();
		var url = "${ctx}/business/mouldregister?methodtype=addFactoryInit&key=" + key;
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	function doUpdateFactory(id) {
		var mouldId = $('#keyBackup').val();
		var url = "${ctx}/business/mouldregister?methodtype=updateFactoryInit&key=" + id + "&mouldId=" + mouldId;
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	function doDeleteFactory(id) {
		var actionUrl = "${ctx}/business/mouldregister?methodtype=deleteFactory&key=" + id + "&mouldId=" + $('#keyBackup').val();
		if (confirm("${DisplayData.endInfoMap.message}")) {
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : actionUrl,
				data : JSON.stringify($('#mouldBaseInfo').serializeArray()),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						parent.reload();
						reloadFatory();
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
	function viewHistoryPrice(id) {
		var url = "${ctx}/business/mouldregister?methodtype=viewHistoryPrice&mouldFactoryId=" + id + "&mouldId=" + $('#mouldId').val();
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	
	function initEvent(){
		
		$('#factoryTable').width(750);
		
		controlButtons($('#keyBackup').val());
		
		$('#unit').val('${DisplayData.mouldBaseInfoData.unit}');
		if ($('#unit').val() == null) {
		    $("#unit option").each(function(){
		        if($(this).text() == "副"){  
		            $(this).attr("selected","selected");  
		        }  
		    });
		}

		$('#unitView').html($("#unit").find("option:selected").text());
		
		if ($('#keyBackup').val() == '') {

		} else {
			if ("${DisplayData.subCode}" != "") {
				$('#mouldIdView').html("${DisplayData.mouldBaseInfoData.mouldid}.${DisplayData.subCode}");
			} else {
				$('#mouldIdView').html("${DisplayData.mouldBaseInfoData.mouldid}");
			}
			//$('#tabs').css('display','inline-block');
			//$('#rotateArea').css('display','inline-block');
			$('#tabs').show();
			$('#factoryArea').show();
			ajax();
		}
		
	}

	$(document).ready(function() {
		
		initEvent();

	})
	
	function doEdit(isContinue) {
		var url = "${ctx}/business/mouldregister?methodtype=mouldregisteredit&key=" + $('#keyBackup').val();
		location.href = url;	
	}
	
	function doDelete() {
		
		if (confirm("${DisplayData.endInfoMap.message}")) {
			//将提交按钮置为【不可用】状态
			//$("#submit").attr("disabled", true); 
			$.ajax({
				type : "POST",
				contentType : 'application/json',
				dataType : 'json',
				url : "${ctx}/business/mouldregister?methodtype=deleteDetail",
				data : $('#keyBackup').val(),// 要提交的表单
				success : function(d) {
					if (d.rtnCd != "000") {
						alert(d.message);	
					} else {
						controlButtons("");
						clearAll();
						parent.reload();
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
	
	function controlButtons(data) {
		$('#keyBackup').val(data);
		if (data == '') {
			$('#delete').attr("disabled", true);
			$('#printmd').attr("disabled", true);
			$('#deletemd').attr("disabled", true);
			$('#addmd').attr("disabled", true);
			$('#acceptanceDate').attr("disabled", true);
			$('#result').attr("disabled", true);
			$('#memo').attr("disabled", true);
			$('#updateacceptance').attr("disabled", true);
			$('#confirmacceptance').attr("disabled", true);
			$('#withhold').attr("disabled", true);
			$('#deletepay').attr("disabled", true);
			$('#addpay').attr("disabled", true);
			
		} else {
			$('#delete').attr("disabled", false);
			$('#printmd').attr("disabled", false);
			$('#deletemd').attr("disabled", false);
			$('#addmd').attr("disabled", false);
			$('#acceptanceDate').attr("disabled", false);
			$('#result').attr("disabled", false);
			$('#memo').attr("disabled", false);
			$('#updateacceptance').attr("disabled", false);
			$('#confirmacceptance').attr("disabled", false);
			$('#withhold').attr("disabled", false);
			$('#deletepay').attr("disabled", false);
			$('#addpay').attr("disabled", false);
		}
	}
	
	function clearAll() {
		sumPrice = 0;
		paid = 0;
		$('#contractId').val("");
		$('#productModelId').val("");
		$('#productModelIdView').val("");
		$("#productModelName").val("");
		$('#mouldFactoryId').val("");
	
	}
	function autoComplete() { 
		$("#productModelIdView").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/business/mouldregister?methodtype=productModelIdSearch",
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
									value : item.name,
									id : item.id,
									name: item.name,
									des : item.des
								}
							}));
						datas = data.data;
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},

			select : function(event, ui) {
				$("#productModelId").val(ui.item.id);
				$("#productModelIdView").val(ui.item.name);
				$("#productModelName").val(ui.item.des);
				//$("#factoryProductCode").focus();
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if (ui.item == null) {
                	$("#productModelId").val('');
    				$("#productModelIdView").val('');
    				$("#productModelName").val('');
                }
            },
			
			minLength : 1,
			autoFocus : false,
			width: 200,
			mustMatch:true,
		});
	}

	
	function autoCompleteFactory(index) { 
		$("#detailLines1\\[" + index + "\\]\\.code").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/business/mouldregister?methodtype=factoryIdSearch",
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
									value : item.no,
									id : item.id,
									name: item.no,
									des : item.fullName
								}
							}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
						alert("系统异常，请再试或和系统管理员联系。");
					}
				});
			},

			select : function(event, ui) {
				$("#detailLines\\[" + index + "\\]\\.mouldfactoryid").val(ui.item.id);
				$("#detailLines1\\[" + index + "\\]\\.code").val(ui.item.name);
				$("#detailLines1\\[" + index + "\\]\\.name").val(ui.item.des);
			},
			
            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                var source = $(this).val();
                var found = $('.ui-autocomplete li').text().search(source);
                if(found < 0) {
                    $(this).val('');
                } else {
    				$("#detailLines\\[" + index + "\\]\\.mouldfactoryid").val(ui.item.id);
    				$("#detailLines1\\[" + index + "\\]\\.code").val(ui.item.name);
    				$("#detailLines1\\[" + index + "\\]\\.name").val(ui.item.des);                	
                }
            },
			
			minLength : 1,
			autoFocus : false,
			width: 200,
		});	
	}
	
	function doReturn() {
		//var url = "${ctx}/business/externalsample";
		//location.href = url;	
		var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		//parent.$('#events').DataTable().destroy();/
		//parent.reload_contactor();
		parent.layer.close(index); //执行关闭
		
	}
	
	function reloadFatory() {
		$('#TFactory').DataTable().ajax.reload(null,false);
	}

</script>
</head>

<body>
<div id="container">

		<div id="main">
			<form:form modelAttribute="dataModels" id="mouldBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<input type=hidden id='productModelId' name='productModelId'/>
				<input type=hidden id="subCodeCount" name="subCodeCount" value=""/>
				<input type=hidden id="mouldId" name="mouldId" value="${DisplayData.mouldBaseInfoData.id}"/>
				<fieldset style="float:left;width:65%">
					<legend>模具单元-基本信息</legend>
					<div style="height:10px"></div>

					<table class="form" width="700px" cellspacing="0">
						<tr>
							<td class="label" width="80px">编号：</td>
							<td width="150px">
								<label id="mouldIdView" name="mouldIdView" style="margin:0px 10px" align="left"></label>
							</td>
							<td class="label" width="60px">分类编码：</td>
							<td width="150px">
								<label id="type" name="type" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.type}</label>
							</td>
							<td class="label" width="60px">编码解释：</td>
							<td width="150px">
								<label name="mouldType" id="mouldType" style="margin:0px 10px">${DisplayData.mouldType} ${DisplayData.typeDesc}</label>
							</td>
						</tr>
						<tr>
						
							<td class="label" width="60px">
								模具名称：
							</td>
							<td>
								<label id="name" name="name" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.name}</label>
							</td>
							<td class="label" width="50px">
								出模数-&nbsp;<p>一出：
							</td>					
							<td>
								<label id="unloadingNum" name="unloadingNum" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.unloadingnum}</label>
							</td>
							<td class="label">
								材质：
							</td>
							<td>
								<label id="materialQuality" name="materialQuality" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.materialquality}</label>
							</td>
						</tr>
						<tr>
							<td class="label">
								尺寸：
							</td>
							<td>
								<label id="size" name="size" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.size}</label>
							</td>
							<td class="label">
								重量：
							</td>
							<td width="100px">
								<label id="weight" name="weight" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.weight}</label>
							</td>
							<td class="label" width="40px">
								单位：
							</td>
							<td>
								<label id="unitView" name="unitView" style="margin:0px 10px"></label>
								<form:select path="unit" style="display:none">
									<form:options items="${DisplayData.unitList}" itemValue="key"
										itemLabel="value" />
								</form:select>
							</td>
						</tr>
						<tr>				
							<td class="label"><label>通用型号：</label></td>
							<td colspan="2" style="word-break:break-all;">
								<label style="margin:0px 10px">${DisplayData.shareModel}</label>	
							</td>							
						</tr>
						<tr>
							<td rowspan=2 class="label">
								中文描述：
							</td>
							<td rowspan=2 colspan=2>
								<label id="comment" name="comment" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.comment}</label>
							</td>
						</tr>
					</table>			
					<button type="button" id="edit" class="DTTT_button" onClick="doEdit(0);"
							style="height:25px;margin:10px 5px 0px 0px;float:right;" >编辑</button>
					<button type="button" id="return" class="DTTT_button" style="height:25px;margin:10px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				</fieldset>
				<div id="tabs" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true" style="margin:0px 20px 0px px;padding:0px;display:none;float:right;">
					<div id="tabs-1" title="图片" style="padding:5px;height:200px;">
						<jsp:include page="../../common/album/album.jsp"></jsp:include>
					</div>
				</div>
				
				<div  style="height:30px;clear:both;"></div>
				<div id="factoryArea" style="display:none;">
					<fieldset>
					<legend>供应商单价信息</legend>
					<div  style="height:10px;"></div>
					<button type="button" id="createFactory" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doCreateFactory();">新建</button>
					<div  style="height:10px;"></div>
					<div class="form">
						<table aria-describedby="TFactory" style="width: 100%;" id="TFactory" class="display dataTable" cellspacing="0"  style="table-layout:fixed">
							<thead>
								<tr class="selected">
									<th colspan="1" rowspan="1" style="width: 40px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
									<th colspan="1" rowspan="1" style="width: 80px;" aria-label="供应商编码:" class="dt-middle sorting_disabled">供应商编码</th>
									<th colspan="1" rowspan="1" style="width: 60px;" aria-label="供应商简称:" class="dt-middle sorting_disabled">供应商简称</th>
									<th colspan="1" rowspan="1" style="width: 100px;" aria-label="供应商全称" class="dt-middle sorting_disabled">供应商全称</th>
									<th colspan="1" rowspan="1" style="width: 40px;" aria-label="采购单价" class="dt-middle sorting_disabled">采购单价</th>
									<th colspan="1" rowspan="1" style="width: 40px;" aria-label="币种" class="dt-middle sorting_disabled">币种</th>
									<th colspan="1" rowspan="1" style="width: 40px;" aria-label="报价单位" class="dt-middle sorting_disabled">报价单位</th>
									<th colspan="1" rowspan="1" style="width: 40px;" aria-label="报价日期" class="dt-middle sorting_disabled">报价日期</th>
									<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
								</tr>
							</thead>
						</table>
					</div>
					</fieldset>
				</div>
			</form:form>
		</div>
</html>
