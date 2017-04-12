<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具单元管理</title>
<script type="text/javascript">
	var validatorBaseInfo;
	var layerHeight = "250";
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
				"paging": true,
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
					var formData = $("#condition").serializeArray();
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
							{"data": "supplierId", "defaultContent" : '',"className" : 'td-center'},
							{"data": "shortName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "fullName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "price", "defaultContent" : '',"className" : 'td-center'},
							{"data": "currency", "defaultContent" : '',"className" : 'td-center'},
							{"data": "priceUnit", "defaultContent" : '',"className" : 'td-center'},
							{"data": "priceDate", "defaultContent" : '',"className" : 'td-center'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'}
				        ],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
		                    }},
							{"targets": 3, "createdCell": function (td, cellData, rowData, row, col) {
						        $(td).attr('title', cellData);
							}},
				    		{"targets":7,"render":function(data, type, row){
				    			return "<a href=\"#\" onClick=\"doUpdateFactory('" + row["id"] + "')\">编辑</a>" + "<a href=\"#\" onClick=\"viewHistoryPrice('" + row["id"] + "')\">历史报价</a>" + "<a href=\"#\" onClick=\"doDeleteFactory('" + row["id"] + "')\">删除</a>"
		                    }}
			         ] 
			}
		);
	}
	
	function createSubCodeFactory(id) {
		var key = $('#keyBackup').val();
		var url = "${ctx}/business/mouldregister?methodtype=updateSubCodeFactoryInit&key=" + key + "&isCreate=1";
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	function doUpdateFactory(id) {
		var key = $('#keyBackup').val();
		var url = "${ctx}/business/mouldregister?methodtype=updateSubCodeFactoryInit&key=" + key;
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	function doDeleteFactory(id) {
		var key = $('#keyBackup').val();
		var url = "${ctx}/business/mouldregister?methodtype=deleteFactory&key=" + key;
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	function viewHistoryPrice(id) {
		var key = $('#keyBackup').val();
		var url = "${ctx}/business/mouldregister?methodtype=viewHistoryPrice&key=" + key;
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	
	function doRotate(direct) {
		
		$('#rotateDirect').val(direct);
		var actionUrl = "${ctx}/business/mouldregister?methodtype=rotate";
		
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
					if (d.info == $('#keyBackup').val()) {
						if (direct == 0) {
							alert("当前已经是第一条数据了");
						} else {
							alert("当前已经是最后一条数据了");
						}
					} else {
						var url = "${ctx}/business/mouldregister?methodtype=updateinit&key=" + d.info;
						$(window.location).attr('href', url);
					}
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
	
	function getMouldId() {
		var actionUrl = "${ctx}/business/mouldregister?methodtype=getMouldId";
		
		if ($('#productModelIdView').val() != "") {
		
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
						$('#mouldId').html('<font color="red">' + d.info + '</font>');
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
		} else {
			$('#mouldId').html("");
		}
	}
	
	function setViewOnly(flag) {
		$(":input").each(function(a,b){
	        var disabled= $(b).attr("disabled", flag);//获取当前对象的disabled属性
	    });
	}
	
	function addSubCodeTr(activeSubCode, subCode, subName, id){
		 
		var i = $("#subidTab tr").length - 1;	
		var subid = PrefixInteger(i, 3);
		var trHtml = "";
		
		if (activeSubCode == subCode) {
			if (activeSubCode != '') {
				trHtml += "<tr class='selected'>";
				trHtml += "<td>";
				trHtml += "当前记录";
				trHtml += "</td>";
			} else {
				trHtml += "<tr>";
			}

			trHtml += "<td class='td-center ' width='200px'>";
			trHtml += "<input name='mouldSubs[" + i + "].subcode' id='mouldSubs[" + i + "].subcode' type='text' value='" + subid + "' class='mini' value='" + subCode + "'/>";
			trHtml += "</td>";
			if (activeSubCode != '') {
				trHtml += "<td class='td-center'>";
			} else {
				trHtml += "<td class='td-center' colspan=2>";
			}
			trHtml += "<input name='mouldSubs[" + i + "].name' id='mouldSubs[" + i + "].name' type='text' class='middle' value='" + subName + "'/>";
			trHtml += "</td>";
			trHtml += "</tr>";			
			$('#subidTab tr:last').after(trHtml);

			$('#mouldSubs\\[' + i + '\\]\\.subcode').rules('add', { noDuplicateSubcode: true});
			$('#mouldSubs\\[' + i + '\\]\\.subcode').rules('add', { maxlength: 3 });
			$('#mouldSubs\\[' + i + '\\]\\.name').rules('add', { maxlength: 50 });
		} else {
			trHtml += "<tr>";
			trHtml += "<td class='td-center' width='150px'>";
			trHtml += "<a href=\"#\" onClick=\"changeSubCode('" + id +")\" class=\"mini\">" + subCode + "</>";
			trHtml += "</td>";
			trHtml += "<td class='td-center' colspan=2>";
			trHtml += "<label class='middle'>" + subName + "</label>";
			trHtml += "</td>";
			trHtml += "</tr>";
			$('#subidTab tr:last').after(trHtml);

		}
		$('#subCodeCount').val($("#subidTab tr").length - 1);
	}

	function initEvent(){
		
		$('#factoryTable').width(750);
		
		autoCompleteType();
		
		validatorBaseInfo = $("#mouldBaseInfo").validate({
			rules: {
				type: {
					required: true,
					maxlength: 100,
				},
				productModelIdView: {
					required: true,				
					maxlength: 120,
				},
				mouldFactoryId: {
					required: true,								
					maxlength: 72,
				},
				name: {
					required: true,
					maxlength: 50,
				},
				materialQuality: {				
					required: true,
					maxlength: 72,
				},
				size: {				
					required: true,
					maxlength: 50,
				},
				weight: {
					required: true,
					maxlength: 5,
				},
				unloadingNum: {
					required: true,
					maxlength: 5,
				},
				unit: {
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
			
	    jQuery.validator.addMethod("noDuplicateSubcode",function(value, element){
	    	var rtnValue = true;

	    	if (value != "") {
				for(var i = 0; i < $('#subCodeCount').val(); i++) {
					if (element.id != ("mouldSubs[" + i + "].subcode")) {
						if (value == $('#mouldSubs\\[' + i + '\\]\\.subcode').val()) {
							rtnValue = false;
							break;
						}
					}	
				}
	    	}

	        return rtnValue;   
	    }, "子编码重复");
	    
		controlButtons($('#keyBackup').val());
		
		$("#productModelId").val('${DisplayData.mouldBaseInfoData.productmodelid}');
		$("#productModelIdView").val('${DisplayData.productModelIdView}');
		$("#productModelName").val('${DisplayData.productModelName}');

		if ($('#keyBackup').val() == '') {
			addSubCodeTr("", "", "", "");
			addSubCodeTr("", "", "", "");
			addSubCodeTr("", "", "", "");
			addSubCodeTr("", "", "", "");
		} else {
			<c:forEach items="${DisplayData.mouldSubDatas}" var="item">
				addSubCodeTr('${DisplayData.subCode}', '${item.subCode}', '${item.name}', '${item.id}');
				var index = $('#subCodeCount').val();
				index--;
				if ('${DisplayData.subCode}' != '${item.subCode}') {
					$('#activeSubCodeIndex').val(index);
				}
			</c:forEach>
			
			$('#tabs').css('display','inline-block');
			$('#rotateArea').css('display','inline-block');
			$('#factoryArea').show();
			ajax();
		}
	}

	$(document).ready(function() {
		
		initEvent();

		//create mode
		if ('${DisplayData.operType}' == '1') {
			$('#factoryArea').hide();
		}

		//edit mode
		if ('${DisplayData.operType}' == '2') {
			//setViewOnly();
		}
		
		//viewOnly mode
		if ('${DisplayData.operType}' == '3') {
			setViewOnly();
		}
		
	    $("#unit option").each(function(){  
	        if($(this).text() == "副"){  
	            $(this).attr("selected","selected");  
	        }  
	    });
	})
	
	function doSave(isContinue) {

		if (validatorBaseInfo.form()) {
			
			var message = "${DisplayData.endInfoMap.message}";
						
			if (confirm(message)) {
				var actionUrl;			
	
				if ($('#keyBackup').val() == "") {				
					//新建
					actionUrl = "${ctx}/business/mouldregister?methodtype=add";
				} else {
					//修正
					actionUrl = "${ctx}/business/mouldregister?methodtype=update";
				}
				//将提交按钮置为【不可用】状态
				//$("#submit").attr("disabled", true); 
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
							if ($('#keyBackup').val() == "") {
								var x = new Array();
								x = d.info.split("|");
								actionUrl = "${ctx}/business/mouldregister?methodtype=updateinit&key=" + x[0] + "&activeSubCode=" + x[1];
								location.href = actionUrl;
							}
							//不管成功还是失败都刷新父窗口，关闭子窗口
							//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
							//parent.$('#events').DataTable().destroy();
							//parent.layer.close(index); //执行关闭
							/*
							$('#tabs').css('display','inline-block');
							$('#rotateArea').css('display','inline-block');
							$('#factoryArea').show();

							var x = new Array();
							x = d.info.split("|");
							controlButtons(x[0]);
							$('#mouldId').html(x[1]);
							*/
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
	function autoCompleteType() { 
		$("#type").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/business/mouldregister?methodtype=typeSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								console.log(item);
								return {
									label : item.viewList,
									value : item.id,
									id : item.id,
									name: item.categoryViewName,
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
				$("#type").val(ui.item.id);
				$("#typeDesc").html(ui.item.name);
				$("#selectedTypeDesc").val(ui.item.name);
				//$("#factoryProductCode").focus();
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                var inputSource = $(this).val();
                var found = $('.ui-autocomplete li').text().search(inputSource);
                console.debug('found:' + found);
                if(found < 0) {
                    $(this).val('');
                    $("#typeDesc").html('');
                    $("#selectedTypeDesc").val('');
                    
                } else {
                	var matcher = new RegExp("^" + $(this).val());
                	for(var i = 0; i < datas.length; i++){//用javascript的for/in循环遍历对象的属性
                		if (matcher.test(datas[i].name)) {
            				$("#type").val(datas[i].id);
            				$("#typeDesc").html(datas[i].name);
            				$("#selectedTypeDesc").val(datas[i].name);
            				break;
                		}
                	}
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
</script>
</head>

<body>
<div id="container">

		<div id="main">
			<form:form modelAttribute="dataModels" id="mouldBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<input type=hidden id='productModelId' name='productModelId'/>
				<input type=hidden id="subCodeCount" name="subCodeCount" value=""/>
				<input type=hidden id="selectedTypeDesc" name="selectedTypeDesc" value="${DisplayData.mouldBaseInfoData.typedesc}"/>
				<input type=hidden id="activeSubCode" name="activeSubCode" value="${DisplayData.activeSubCode}"/>
				<input type=hidden id="activeSubCodeIndex" name="activeSubCodeIndex" value=""/>
				<input type=hidden id="rotateDirect" name="rotateDirect" value=""/>
				<legend>模具单元-基本信息</legend>
				<div style="height:10px"></div>
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;display:none;">删除</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave(1);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;display:none;" >保存(连续登记)</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<button type="button" id="edit" class="DTTT_button" onClick="doSave(0);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>

				<div style="height:10px"></div>
				<table class="form" width="1100px" cellspacing="0" style="table-layout:fixed">
					<tr>
						<td width="60px">编号：</td>
						<td width="130px">
							<label id="mouldId" name="mouldId" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.mouldid}</label>
						</td>
						<td width="60px">产品型号：</td>
						<td width="130px">
							<form:input path="productModelIdView" class="required mini"/>
						</td>
						<td width="60px">产品名称：</td>
						<td width="130px">
							<form:input path="productModelName"	class="short" />
						</td>
						<td width="60px">模具类型：</td>
						<td width="130px">
							<input type="text" name="type" id="type" class="short" onblur="getMouldId();" value="${DisplayData.mouldBaseInfoData.type}">
						</td>
						<td width="60px">类型解释：</td>
						<td width="130px">
							<label name="typeDesc" id="typeDesc" class="short" class="read-only short">${DisplayData.mouldBaseInfoData.typedesc}</label>
						</td>
					</tr>
					<tr>
						<td width="50px">
							出模数：
						</td>
						<td width="100px">
							<input type="text" id="unloadingNum" name="unloadingNum" class="mini" value="${DisplayData.mouldBaseInfoData.unloadingnum}"></input>
						</td>
						<td>
							模具名称：
						</td>
						<td>
							<input type="text" id="name" name="name" class="short" value="${DisplayData.mouldBaseInfoData.name}"></input>
						</td>
						<td>
							材质：
						</td>
						<td>
							<input type="text" id="materialQuality" name="materialQuality" class="short" value="${DisplayData.mouldBaseInfoData.materialquality}"></input>
						</td>
						<td>
							尺寸：
						</td>
						<td>
							<input type="text" id="size" name="size" class="short" value="${DisplayData.mouldBaseInfoData.size}"></input>
						</td>
						<td>
							重量：
						</td>
						<td>
							<input type="text" id="weight" name="weight" class="mini" value="${DisplayData.mouldBaseInfoData.weight}"></input>
						</td>
					</tr>
					<tr>
						<td>
							单位：
						</td>
						<td>
							<form:select path="unit">
								<form:options items="${DisplayData.unitList}" itemValue="key"
									itemLabel="value" />
							</form:select>
						</td>

					</tr>
					<tr>
						<td align=center>
							子编码
						</td>
						<td colspan=3>
							<table class="form">
								<tr>
									<td>
										<table width='450px'>
											<tr>
												<td class="td-center" width='150px'>子编码</td>
												<td class="td-center" width='150px'>子编码解释</td>
												<td class="td-center">
													<button type="button"  style = "height:20px;" 
													id="createSubid" class="DTTT_button" onClick="addSubCodeTr('', '', '', '');">新建</button>
												</td>
											</tr>
										</table>
									</td>
								</tr>		
								<tr>
									<td>			
										<div class="" id="subidDiv" style="overflow: auto;height: 150px;">
											<table id="subidTab" class="dataTable" width='350px'>
												<tr style='display:none'>
													<td width='150px'></td>
													<td width='150px'></td>
													<td></td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
						<td colspan=3>	
							<table>
								<tr>
									<td>
										<div id="tabs" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true" style="margin:10px 0px 0px 15px;padding:0px;display:none;">
											<div id="tabs-1" title="图片" style="padding:5px;height:200px;">
												<jsp:include page="../../common/album/album.jsp"></jsp:include>
											</div>
										</div>
									</td>
								</tr>
							</table>						
						</td>
						<td colspan=3 align=center>
							<div id="rotateArea" style="display:none;margin:0px 0px 0px 50px">
								<div style="height:40px">
									<button type="button" id="delete" class="DTTT_button" onClick="doRotate(1);"
										style="height:25px;margin:-20px 30px 0px 0px;float:right;">&gt;&gt;</button>
									<button type="button" id="delete" class="DTTT_button" onClick="doRotate(0);"
										style="height:25px;margin:-20px 30px 0px 0px;float:left;">&lt;&lt;</button>
								</div>
								<div style="height:40px">
									<label 
										style="height:25px;margin:-20px 10px 0px 0px;float:right;">下一个模具</label>
									<label
										style="height:25px;margin:-20px 30px 0px -10px;float:left;">前一个模具</label>
								</div>
							</div>						
						</td>
					</tr>
				</table>			
				
				<div  style="height:20px"></div>
				
			
				<div id="factoryArea" style="display:none;">
					<legend>供应商单价信息</legend>
					<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doCreateFactory();">新建</button>
					<table aria-describedby="TFactory" style="width: 100%;" id="TMould" class="display dataTable" cellspacing="0"  style="table-layout:fixed">
						<thead>
							<tr class="selected">
								<th colspan="1" rowspan="1" style="width: 40px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
								<th colspan="1" rowspan="1" style="width: 80px;" aria-label="供应商编码:" class="dt-middle sorting_disabled">供应商编码</th>
								<th colspan="1" rowspan="1" style="width: 100px;" aria-label="供应商简称:" class="dt-middle sorting_disabled">供应商简称</th>
								<th colspan="1" rowspan="1" style="width: 120px;" aria-label="供应商全称" class="dt-middle sorting_disabled">供应商全称</th>
								<th colspan="1" rowspan="1" style="width: 40px;" aria-label="采购单价" class="dt-middle sorting_disabled">采购单价</th>
								<th colspan="1" rowspan="1" style="width: 40px;" aria-label="币种" class="dt-middle sorting_disabled">币种</th>
								<th colspan="1" rowspan="1" style="width: 40px;" aria-label="报价单位" class="dt-middle sorting_disabled">报价单位</th>
								<th colspan="1" rowspan="1" style="width: 40px;" aria-label="报价日期" class="dt-middle sorting_disabled">报价日期</th>
								<th colspan="1" rowspan="1" style="width: 50px;" aria-label="操作" class="dt-middle sorting_disabled">操作</th>
							</tr>
						</thead>
					</table>
				</div>


			</form:form>
		</div>
</html>
