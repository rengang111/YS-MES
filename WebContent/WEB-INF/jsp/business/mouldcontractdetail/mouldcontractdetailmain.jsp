<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%@ include file="../../common/common2.jsp"%>

<title>模具详情</title>
<script type="text/javascript">

	var layerHeight = '600';
	var tabNameList = new Array();
	
	function ajax(index) {
		var table = $('#tab-' + index).dataTable();
		if(table) {
			table.fnDestroy();
		}
	
		var t = $('#tab-' + index).DataTable({
				"paging": false,
				"lengthMenu":[5],//设置一页展示10条记录
				"processing" : false,
				"serverSide" : true,
				"stateSave" : false,
				"searching" : false,
				"serverSide" : true,
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/mouldcontractdetail?methodtype=getMouldDetailList",
				"fnServerData" : function(sSource, aoData, fnCallback) {
					var param = {};
					var formData = $("#condition").serializeArray();
					formData.forEach(function(e) {
						aoData.push({"name":e.name, "value":e.value});
					});
					aoData.push({"name":"type", "value":index});
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
							sumPrice = data.sumPrice;
							$('#sumPrice-' + index).html(data.sumPrice);
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
							{"data" : "no", "className" : 'td-center'}, 
							{"data" : "name", "className" : 'td-center'},
							{"data" : "size", "className" : 'td-center'}, 
							{"data" : "materialQuality", "className" : 'td-center'},
							{"data" : "mouldUnloadingNum", "className" : 'td-center'},
							{"data" : "heavy", "className" : 'td-center'},
							{"data" : "price", "className" : 'td-center'},
							{"data" : "mouldFactory", "className" : 'td-center'},
							{"data" : "acceptanceDate", "className" : 'td-center'},
							{"data" : "place", "className" : 'td-center'},
				        ],
				"columnDefs":[
					    		{"targets":0,"render":function(data, type, row){
									return row["rownum"]
			                    }}
			         		 ] 
			}
		);
	}

	
	function initEvent(){

		var tabList = eval('${DisplayData.jsonObject}');
		var tabHtml = "";
		
		for(var i = 0; i < tabList.length; i++) {
			var subArray = tabList[i];
			
			tabHtml += "<legend>" + subArray.value + "模具详情</legend>";
			tabHtml += "<div  style='height:10px'></div>";
			tabHtml += "<table id='tab-" + subArray.key + "' class='form'  width='1000px'>";
			tabHtml += "	<thead>";
			tabHtml += "		<tr class='selected'>";
			tabHtml += "			<th style='width: 40px;' class='dt-middle'>No</th>";
			tabHtml += "			<th style='width: 80px;' class='dt-middle'>模具<br>编号</th>";
			tabHtml += "			<th style='width: 80px;' class='dt-middle'>模具<br>名称</th>";
			tabHtml += "			<th style='width: 80px;' class='dt-middle'>模架<br>尺寸</th>";
			tabHtml += "			<th style='width: 80px;' class='dt-middle'>材质</th>";
			tabHtml += "			<th style='width: 80px;' class='dt-middle'>出模数</th>";
			tabHtml += "			<th style='width: 80px;' class='dt-middle'>重量</th>";
			tabHtml += "			<th style='width: 80px;' class='dt-middle'>价格</th>";
			tabHtml += "			<th style='width: 80px;' class='dt-middle'>模具<br>工厂</th>";
			tabHtml += "			<th style='width: 80px;' class='dt-middle'>验收<br>时间</th>";
			tabHtml += "			<th style='width: 40px;' class='dt-middle'>存放<br>位置</th>";
			tabHtml += "		</tr>";
			tabHtml += "	</thead>";
			tabHtml += "	<tfoot>";
			tabHtml += "		<tr>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "			<th></th>";
			tabHtml += "		</tr>";
			tabHtml += "	</tfoot>";
			tabHtml += "</table>";
			tabHtml += "<table class='display' cellspacing='0' style='margin: 0px;'>";
			tabHtml += "	<tr>";
			tabHtml += "		<td style='width: 630px;' class='dt-middle'>&nbsp;</td>";
			tabHtml += "		<td style='width: 90px;' class='dt-middle'>总价</td>";
			tabHtml += "		<td style='width: 96px;' class='dt-middle'><label id='sumPrice-" + subArray.key + "'></label></td>";
			tabHtml += "		<td ></td>";
			tabHtml += "	</tr>";
			tabHtml += "</table>";
			
			tabNameList.push(subArray.key);
		}
		$('#TMouldContract_wrapper').html(tabHtml);
		doSearch();
		autoComplete();
		

	}

	$(document).ready(function() {
		//ajax();
		initEvent();
		
	})	
	
	function doSearch() {
	
		for(var i = 0; i < tabNameList.length; i++) {
			ajax(tabNameList[i]);
		}
	}

	function autoComplete() { 
		$("#productModelId").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/business/mouldcontract?methodtype=productModelIdSearch",
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
									value : item.id,
									id : item.id,
									des : item.des
								}
							}));
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
						alert(XMLHttpRequest.status);
						alert(XMLHttpRequest.readyState);
						alert(textStatus);
						alert(errorThrown);
						alert("系统异常，请再试或和系统管理员联系。");
					}
				});
			},

			select : function(event, ui) {
				$("#productModelId").val(ui.item.id);
				$("#productModelName").val(ui.item.des);
				//$("#factoryProductCode").focus();

			},
			minLength : 1,
			autoFocus : false,
			width: 200,
		});	
	}
</script>

</head>

<body class="easyui-layout">
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition" 
					style='padding: 0px; margin: 10px;' >

					<table>
						<tr>
							<td width="10%"></td> 
							<td class="label">产品型号：</td>
							<td class="condition">
								<input type="text" id="productModelId" name="productModelId" class="middle"/>
							</td>
							<td class="label">产品名称：</td> 
							<td class="condition">
								<input type="text" id="productModelName" name="productModelName" class="middle"/>
							</td>
							<td>
								<button type="button" id="retrieve" class="DTTT_button" style="width:50px" value="查询" onClick="doSearch();"/>查询
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>
		
			<div class="list">

				<div id="TMouldContract_wrapper" class="dataTables_wrapper">

				</div>
			</div>
		</div>
	</div>
</html>
