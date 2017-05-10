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
	var layerHeight = "350";
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
				    			return "<a href=\"#\" onClick=\"doUpdateFactory('" + row["id"] + "','" + row["subCode"] + "')\">编辑</a>&nbsp;" + "<a href=\"#\" onClick=\"viewHistoryPrice('" + row["mouldFactoryId"] + "')\">历史报价</a>&nbsp;" + "<a href=\"#\" onClick=\"doDeleteFactory('" + row["id"] + "')\">删除</a>"
		                    }}
			         ] 
			}
		);
	}
	
	function doCreateFactory() {
		var key = $('#activeSubCode').val();
		var url = "${ctx}/business/mouldregister?methodtype=addSubCodeFactoryInit&activeSubCode=" + key;
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	function doUpdateFactory(id, subCode) {
		var url = "${ctx}/business/mouldregister?methodtype=updateSubCodeFactoryInit&key=" + id + "&activeSubCode=" + $('#activeSubCode').val();
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	function doDeleteFactory(id) {
		var actionUrl = "${ctx}/business/mouldregister?methodtype=deleteFactory&key=" + id + "&activeSubCode=" + $('#activeSubCode').val();
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
		var url = "${ctx}/business/mouldregister?methodtype=viewHistoryPrice&mouldFactoryId=" + id + "&activeSubCode=" + $('#activeSubCode').val();
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
						var url = "${ctx}/business/mouldregister?methodtype=updateinit&key=" + d.info + "&activeSubCode=" + id;
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
		
		if ($('#type').val() != "" && $('#type').val().substr(0, 1) == 'M') {
		
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
						if (d.info != "nochange") {
							$('#mouldId').html('<font color="red">' + d.info + '</font>');
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
		} else {
			$('#mouldId').html("");
			$('#mouldType').html("");
			$('#typeDesc').html("");
		}
	}
	
	function setViewOnly(flag) {
		$(":input").each(function(a,b){
	        var disabled= $(b).attr("disabled", flag);//获取当前对象的disabled属性
	    });
	}
	
	function addSubCodeTr(activeSubCode, subCode, subName, id){
		 
		var i = $("#subidTab tr").length - 1;	
		var subid = PrefixInteger(i, 2);
		var trHtml = "";

		if (activeSubCode == subCode) {
			if (activeSubCode != '') {
				trHtml += "<tr class='selected'>";
				trHtml += "<td width='50px'>";
				trHtml += "本条记录";
				trHtml += "</td>";
			} else {
				trHtml += "<tr>";
				trHtml += "<td width='50px'>";
				trHtml += "</td>";				
			}
			if (activeSubCode != '') {
				trHtml += "<td class='td-center' width='100px'>";
			} else {
				trHtml += "<td class='td-center' width='100px'>";
			}
			if (subCode == "") {
				subCode = subid;
			}
			trHtml += "<input name='mouldSubs[" + i + "].subcode' id='mouldSubs[" + i + "].subcode' type='text' class='mini' value='" + subCode + "'/>";
			trHtml += "</td>";
			if (activeSubCode != '') {
				trHtml += "<td class='td-left' colspan=2>";
			} else {
				trHtml += "<td class='td-left' colspan=2>";
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
			trHtml += "<td width='50px'>";
			trHtml += "</td>";
			trHtml += "<td class='td-left' width='100px'>";
			trHtml += "<a href=\"#\" style='padding-left: 20px' onClick=\"changeSubCode('" + id +"')\">" + subCode + "</></label>";
			trHtml += "</td>";
			trHtml += "<td class='td-left'  colspan=2>";
			trHtml += "<a href=\"#\" style='padding-left: 10px' onClick=\"changeSubCode('" + id +"')\">" + subName + "</>";
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
					mouldType: true,
					maxlength: 100,
				},
				productModelIdView: {
					required: true,				
					maxlength: 120,
				},
				productModelIdName: {
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
	    
	    jQuery.validator.addMethod("mouldType",function(value, element){
	    	var rtnValue = true;

	    	if (value != "") {
	    		if (value.substr(0, 1) != 'M') {
	    			rtnValue = false;
	    		}
	    	}
	        return rtnValue;   
	    }, "M*");
	    
		controlButtons($('#keyBackup').val());
		
		$("#productModelId").val('${DisplayData.mouldBaseInfoData.productmodelid}');
		$("#productModelIdView").val('${DisplayData.productModelIdView}');
		$("#productModelName").val('${DisplayData.productModelName}');

		if ('${DisplayData.mouldBaseInfoData.type}' != '') {
			$('#type').val('${DisplayData.mouldBaseInfoData.type}');
		} else {
			$("#type option:first").prop("selected", 'selected');
		}

		if ('${DisplayData.mouldBaseInfoData.unit}' != '') {
			$('#unit').val('${DisplayData.mouldBaseInfoData.unit}');
		} else {
			$("#unit option:first").prop("selected", 'selected');
		}

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
				if ('${DisplayData.subCode}' == '${item.subCode}') {
					$('#activeSubCodeIndex').val(index);
				}
			</c:forEach>
			
			$('#tabs').css('display','inline-block');
			//$('#rotateArea').css('display','inline-block');
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
							//不管成功还是失败都刷新父窗口，关闭子窗口
							//var index = parent.layer.getFrameIndex(wind$("#mainfrm")[0].contentWindow.ow.name); //获取当前窗体索引
							//parent.$('#events').DataTable().destroy();
							//parent.layer.close(index); //执行关闭
							$('#tabs').css('display','inline-block');
							//$('#rotateArea').css('display','inline-block');
							$('#factoryArea').show();

							var x = new Array();
							x = d.info.split("|");
							controlButtons(x[0]);
							$('#mouldId').html(x[1]);
							
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
								return {
									label : item.viewList,
									value : item.id,
									id : item.id,
									name: item.categoryViewName,
									parentId: item.parentcategoryId,
									parentName: item.parentName,
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
				$("#mouldType").html(ui.item.parentId);
				$("#typeDesc").html(ui.item.parentName);
				getMouldId();
				//$("#factoryProductCode").focus();
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#mouldType").html('');
    				$("#typeDesc").html('');
                    $('#mouldId').html('');
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
	
	function changeSubCode(id) {
		var url = "${ctx}/business/mouldregister?methodtype=updateinit&key=" + $('#keyBackup').val() + "&activeSubCode=" + id;
		$(window.location).attr('href', url);
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
				<input type=hidden id="activeSubCode" name="activeSubCode" value="${DisplayData.activeSubCode}"/>
				<input type=hidden id="activeSubCodeIndex" name="activeSubCodeIndex" value=""/>
				<input type=hidden id="rotateDirect" name="rotateDirect" value=""/>
				<legend>模具单元-基本信息</legend>
				<div style="height:10px"></div>
				<!-- 
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				 -->
				<button type="button" id="edit" class="DTTT_button" onClick="doSave(0);"
						style="height:25px;margin:-20px 5px 0px 0px;float:right;" >保存</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:-20px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				<div style="height:10px"></div>
				<table class="form" width="1100px" cellspacing="0" style="table-layout:fixed">
					<tr>
						<td width="60px">编号：</td>
						<td width="130px">
							<label id="mouldId" name="mouldId" style="margin:0px 10px">${DisplayData.mouldBaseInfoData.mouldid}.${DisplayData.subCode}</label>
						</td>
						<td width="60px">分类编码：	</td>
						<td width="150px">
							<form:input path="type"	class="short" onBlur="getMouldId();"/>
						</td>
						<td width="60px">模具类型：</td>
						<td width="130px">
							<label name="mouldType" id="mouldType" >${DisplayData.mouldType}</label>
						</td>
						<td width="60px">类型解释：</td>
						<td width="150px">
							<label name="typeDesc" id="typeDesc" class="short" class="read-only short">${DisplayData.typeDesc}</label>
						</td>
						<td width="60px">
							模具名称：
						</td>
						<td>
							<input type="text" id="name" name="name" class="short" value="${DisplayData.mouldBaseInfoData.name}"></input>
						</td>
					</tr>
					<tr>
						<td width="50px">
							出模数：
						</td>
						<td>
							<input type="text" id="unloadingNum" name="unloadingNum" class="mini" value="${DisplayData.mouldBaseInfoData.unloadingnum}"></input>
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
					</tr>
					<tr>
						<td>
							重量：
						</td>
						<td>
							<input type="text" id="weight" name="weight" class="mini" value="${DisplayData.mouldBaseInfoData.weight}"></input>
						</td>
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
												<td width="50px"></td>
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
											<table id="subidTab" class="dataTable" style="table-layout:fixed;" width='350px'>
												<tr style='display:none'>
													<td></td>
													<td></td>
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
										<div id="tabs" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true" style="margin:10px 0px 0px 150px;padding:0px;display:none;">
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


			</form:form>
		</div>
</html>
