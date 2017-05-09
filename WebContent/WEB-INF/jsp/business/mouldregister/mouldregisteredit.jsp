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
	var layerHeight = "350";
	var sumPrice = 0.0;
	var paid = 0.0;    
	var strShareMode = "<input type='text' class='mini' maxlength='5' name='sharemodel";
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
							{"data": "supplierId", "defaultContent" : '',"className" : 'td-center'},
							{"data": "shortName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "supplierName", "defaultContent" : '',"className" : 'td-center'},
							{"data": "price", "defaultContent" : '',"className" : 'td-center'},
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
		var key = $('#keyBackup').val();
		var url = "${ctx}/business/mouldregister?methodtype=addFactoryInit&mouldId=" + key;
		openLayer(url, $(document).width() - 25, layerHeight, false);	
	}
	function doUpdateFactory(id, subCode) {
		var url = "${ctx}/business/mouldregister?methodtype=updateFactoryInit&key=" + id + "&mouldId=" + $('#keyBackup').val();
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
		var url = "${ctx}/business/mouldregister?methodtype=viewHistoryPrice&mouldFactoryId=" + id + "&mouldId=" + $('#keyBackup').val();
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
			$('#mouldType').val("");
			$('#typeDesc').html("");
		}
	}
	
	function setViewOnly(flag) {
		$(":input").each(function(a,b){
	        var disabled= $(b).attr("disabled", flag);//获取当前对象的disabled属性
	    });
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
				comment: {
					maxlength: 120,
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

		$('#unit').val('${DisplayData.mouldBaseInfoData.unit}');
		if ($('#unit').val() == null) {
		    $("#unit option").each(function(){
		    	console.log($(this).text());
		        if($(this).text() == "副"){  
		            $(this).attr("selected","selected");  
		        }  
		    });
		}

		if ($('#keyBackup').val() == '') {

		} else {
			if ("${DisplayData.subCode}" != "") {
				$('#mouldId').html("${DisplayData.mouldBaseInfoData.mouldid}.${DisplayData.subCode}");
			} else {
				$('#mouldId').html("${DisplayData.mouldBaseInfoData.mouldid}");
			}
			//$('#tabs').css('display','inline-block');
			//$('#rotateArea').css('display','inline-block');
			//$('#factoryArea').show();
			//ajax();
		}
		
		autoAddShareModel();
		
		$('#type').focus();
	}

	$(document).ready(function() {
		
		initEvent();

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
				$('#shareModelCount').val($("#coupon input[type=text]").length);
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
							
							var x = new Array();
							x = d.info.split("|");
							controlButtons(x[0]);
							$('#mouldId').html(x[1]);
							
							var url = "${ctx}/business/mouldregister?methodtype=updateinit&key=" + $('#keyBackup').val();
							location.href = url;	
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
				$("#mouldType").val(ui.item.parentId + " " + ui.item.parentName);
				getMouldId();
				//$("#factoryProductCode").focus();
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#mouldType").val('');
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
	
	function autoAddShareModel() {

		var firstFlg = true;
		var isData = false;
		<c:forEach var="model" items="${DisplayData.shareModelList}" varStatus="status">

			var modelSize = '${DisplayData.shareModelList}.size()'
			var model = '${model}';
			var i = '${status.index}';
			var tdHtml = '';
			isData = true;
			if(firstFlg){
				tdHtml = strShareMode + i + "' id='sharemodel" + i + "' value='" + model + "'/>"; 
			
				$('#ShareTab tr:first td:first').after(tdHtml);
				firstFlg = false;
			}else{
				tdHtml = strShareMode + i + "' id='sharemodel" + i + "' value='" + model + "'/>"; 		
				$('#coupon input:last').after(tdHtml);	
			}

		</c:forEach>
		if (isData) {
			autoNextSelect();
		} else {
			autoAddShareModelInit();
		}
		
	} 
	
	 function addTd(){	
		var i = $("#coupon input[type=text]").length - 1;		
		i++;		
	    var trHtml = strShareMode + i + "' id='sharemodel" + i + "'/>"; 		
		$("#coupon input:last").after(trHtml);			
		autoNextSelect();
	}
	 
	function autoAddShareModelInit() {
		var i = 0;
		var tdHtml = '';
		tdHtml = strShareMode + i + "' id='sharemodel" + i+ "'/>";     
		$('#ShareTab tr:first td:first').after(tdHtml);
	
		i++;	
		for(i; i < 5; i++){
		    tdHtml = strShareMode + i + "' id='sharemodel" + i + "'/>"; 		
			$('#coupon input:last').after(tdHtml);
		}
		autoNextSelect();
	}
	 
	function autoNextSelect(){
		//自动跳到下一个输入框  
	    $("input[name^='sharemode']").each(function() {
	    	
	        $(this).keyup(function(e) {
	        	
	            e = window.event || e;
	            var k = e.keyCode || e.which;
	
	            if (k == 8) {   //8是空格键
	                if ($(this).val().length < 1) {
	                    $(this).prev().focus();
	                    $(this).prev().focus(function() {
	                        var obj = e.srcElement ? e.srcElement: e.target;
	                        if (obj.createTextRange) { //IE浏览器
	                            var range = obj.createTextRange();
	                            range.moveStart("character", 4);
	                            range.collapse(true);
	                            range.select();
	                        }
	                    });
	                }
	            } else {
	                if ($(this).val().length > 4) {
	                    $(this).next().focus();
	                }
	            }
	        })
	    });//自动跳到下一个输入框 
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
				<input type=hidden id="activeSubCodeIndex" name="activeSubCodeIndex" value=""/>
				<input type=hidden id="rotateDirect" name="rotateDirect" value=""/>
				<input type=hidden id="shareModelCount" name="shareModelCount" value=""/>
				<fieldset>
				<legend>模具单元-基本信息</legend>
				<div style="height:10px"></div>
				<!-- 
				<button type="button" id="delete" class="DTTT_button" onClick="doDelete();"
						style="height:25px;margin:-20px 30px 0px 0px;float:right;">删除</button>
				 -->

				<table class="form" width="1100px" cellspacing="0">
					<tr>
						<td width="80px">编号：</td>
						<td width="150px">
							<label id="mouldId" name="mouldId" style="margin:0px 10px"></label>
						</td>
						<td width="60px">分类编码：	</td>
						<td width="150px">
							<form:input path="type"	onBlur="getMouldId();" class="short"/>
						</td>
						<td width="60px">编码解释：</td>
						<td width="150px">
							<input type=text name="mouldType" id="mouldType" class="read-only" readonly="readonly" value="${DisplayData.mouldType} ${DisplayData.typeDesc}"/>
						</td>
					</tr>
						<td width="60px">
							模具名称：
						</td>
						<td>
							<input type="text" id="name" name="name" class="middle" value="${DisplayData.mouldBaseInfoData.name}"></input>
						</td>
						<td width="50px">
							出模数-一出：
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
					</tr>
					<tr>
					
						<td>
							尺寸：
						</td>
						<td>
							<input type="text" id="size" name="size" class="middle" value="${DisplayData.mouldBaseInfoData.size}"></input>
						</td>
						<td>
							重量：
						</td>
						<td width="100px">
							<input type="text" id="weight" name="weight" class="mini" value="${DisplayData.mouldBaseInfoData.weight}"></input>
						</td>
						<td width="40px">
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
						<td rowspan=2>
							中文描述：
						</td>
						<td rowspan=2 colspan=2>
							<textarea id="comment" name="comment" cols=50 rows=10>${DisplayData.mouldBaseInfoData.comment}</textarea>
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
				</fieldset>
				<fieldset>
				<legend style="margin: 0px 0px 0px 0px"> 模具-关联信息</legend>
				<div id="autoscroll">
					<table class="form">
						<tr>
							<td><label>通用型号：</label>
								<button type="button"  style = "height:25px" class="DTTT_button"
								 id="createShare" onClick="addTd();">新建</button></td>
						</tr>		
						<tr>
							<td>
								<form:hidden path="shareModel" value=""/>	
								<div class="" id="coupon">
									<table id="ShareTab">
										<tr>
											<td></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>

				<button type="button" id="edit" class="DTTT_button" onClick="doSave(0);"
						style="height:25px;margin:10px 5px 0px 0px;float:right;" >保存</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:10px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				</fieldset>
			</form:form>
		</div>
</html>
