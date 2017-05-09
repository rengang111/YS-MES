<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>
<!-- <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script> -->
<title>模具单元基本信息流水号调整</title>
<script type="text/javascript">
	var validatorBaseInfo;

	var serialNoBackup = new Array();	
	var datas = new Array();
	
	function PrefixInteger(obj, length) {
		var num = $(obj).val();
		if (num.length < length) {
			$(obj).val("0" + num);
		}
	} 
	
	function ajax() {
		var table = $('#TSerialNo').dataTable();
		if(table) {
			table.fnDestroy();
		}

		var t = $('#TSerialNo').DataTable({
				"paging": false,
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"pagingType" : "full_numbers",
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/mouldregister?methodtype=getSerialNoList",
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
							serialNoBackup = new Array();
							
							fnCallback(data);
							
							for(var i = 0; i < serialNoBackup.length; i++) {
								$('#' + serialNoBackup[i]).rules('add', { required: true, noDuplicateSerialNo: true, digits: true, maxlength: 2});
							}
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
							{"data": "name", "defaultContent" : '',"className" : 'td-left'},
							{"data": "no", "defaultContent" : '',"className" : 'td-center'},
							{"data": null, "defaultContent" : '',"className" : 'td-center'},
				        ],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								//return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["id"] + "' />"
								return row["rownum"];
		                    }},
				    		{"targets":2,"render":function(data, type, row){
				    			return "<label name='newNoView-" + row["id"] + "' id='newNoView-" + row["id"] + "' class='mini'>" + row["no"] + "</>" + "<input type=hidden name='newNoOld-" + row["id"] + "' id='newNoOld-" + row["id"] + "' class='mini' value='" + row["no"] + "'/>"
		                    }},
				    		{"targets":3,"render":function(data, type, row){
				    			serialNoBackup.push("newNo-" + row["id"]);
				    			return "<input type='text' name='newNo-" + row["id"] + "' id='newNo-" + row["id"] + "' class='mini' value='" + row["no"] + "' onblur='PrefixInteger(this, 2)'/>"
		                    }}
			         ] 
			}
		);
	}
	
	function initEvent(){
		
		$('#factoryTable').width(750);
		
		autoCompleteType();
		
		validatorBaseInfo = $("#mouldBaseInfo").validate({
			rules: {
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
			
	    jQuery.validator.addMethod("noDuplicateSerialNo",function(value, element){
	    	var rtnValue = true;

	    	if (value != "") {
				for(var i = 0; i < serialNoBackup.length; i++) {
					var oldVal = $('#' + serialNoBackup[i]).val()
					for(var j = i + 1; j < serialNoBackup.length; j++) {
						var newVal = $('#' + serialNoBackup[j]).val();
						if (oldVal == newVal) {
							rtnValue = false;
							break;
	    				}
					}
				}	
	    	}

	        return rtnValue;   
	    }, "重复");
	    
		ajax();
	    
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
	
				actionUrl = "${ctx}/business/mouldregister?methodtype=updatetrimserialno";

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
							doReturn();
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
				$("#selctedMouldType").val(ui.item.id);
				$("#mouldType").val(ui.item.parentId + " " + ui.item.parentName);
				reloadSerialNo();
				//$("#factoryProductCode").focus();
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#selctedMouldType").val("");
    				$("#mouldType").val('');
    				reloadSerialNo();
                }
            },
			
			minLength : 1,
			autoFocus : false,
			width: 200,
			mustMatch:true,
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
	
	function reloadSerialNo() {
		$('#TSerialNo').DataTable().ajax.reload(null,false);
	}
	
</script>
</head>

<body>
<div id="container">

		<div id="main">
			<form:form modelAttribute="dataModels" id="mouldBaseInfo" style='padding: 0px; margin: 10px;' >
				<input type=hidden id="keyBackup" name="keyBackup" value="${DisplayData.keyBackup}"/>
				<input type=hidden id="selctedMouldType" name="selctedMouldType" value=""/>
				<legend>分类编码-基本信息</legend>
				<div style="height:10px"></div>

				<table class="form" width="1100px" cellspacing="0">
					<tr>
						<td width="60px">分类编码：	</td>
						<td width="150px">
							<form:input path="type"	onBlur="" class="short"/>
						</td>
						<td width="60px">编码解释：</td>
						<td width="150px">
							<input type=text name="mouldType" id="mouldType" class="read-only" readonly="readonly" value="${DisplayData.mouldType} ${DisplayData.typeDesc}"/>
						</td>
					</tr>
				</table>			
				<button type="button" id="edit" class="DTTT_button" onClick="doSave(0);"
						style="height:25px;margin:10px 5px 0px 0px;float:right;" >保存</button>
				<button type="button" id="return" class="DTTT_button" style="height:25px;margin:10px 5px 0px 0px;float:right;" onClick="doReturn();">返回</button>
				
				<div  style="height:20px"></div>
				
			
				<div id="factoryArea" style="width:100%;">
					<legend>供应商单价信息</legend>
					<div  style="height:20px"></div>
					<div class="form">
						<table aria-describedby="TSerialNo" style="width: 100%;" id="TSerialNo" class="display dataTable" cellspacing="0"  style="table-layout:fixed">
							<thead>
								<tr class="selected">
									<th colspan="1" rowspan="1" style="width: 40px;" aria-label="No:" class="dt-middle sorting_disabled">No</th>
									<th colspan="1" rowspan="1" style="width: 260px;" aria-label="模具名称:" class="dt-middle sorting_disabled">模具名称</th>
									<th colspan="1" rowspan="1" style="width: 80px;" aria-label="流水号(现):" class="dt-middle sorting_disabled">流水号(现)</th>
									<th colspan="1" rowspan="1" style="width: 120px;" aria-label="流水号(新):" class="dt-middle sorting_disabled">流水号(新)</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>


			</form:form>
		</div>
</html>
