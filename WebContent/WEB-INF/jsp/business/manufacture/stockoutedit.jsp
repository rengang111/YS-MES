<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>料件出库-编辑</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	
	var shortYear = ""; 
	
	function ajax(scrollHeight) {
		
		var requisitionid= $("#stockout\\.requisitionid").val();
		var actionUrl = "${ctx}/business/stockout?methodtype=getRequisitionDetail";
		actionUrl = actionUrl +"&requisitionId="+requisitionid;
		
		//scrollHeight =$(document).height() - 200; 
		
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : scrollHeight,
	       // "scrollCollapse": false,
	        "paging"    : false,
	        //"pageLength": 50,
	        "ordering"  : false,
			"dom"		: '<"clear">rt',
			"sAjaxSource" : actionUrl,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				//var param = {};
				//var formData = $("#condition").serializeArray();
				//formData.forEach(function(e) {
				//	aoData.push({"name":e.name, "value":e.value});
				//});

				$.ajax({
					"url" : sSource,
					"datatype": "json", 
					"contentType": "application/json; charset=utf-8",
					"type" : "POST",
					//"data" : JSON.stringify(aoData),
					success: function(data){					
						fnCallback(data);
						
						foucsInit();
						
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
        	"language": {
        		"url":"${ctx}/plugins/datatables/chinese.json"
        	},
			"columns" : [
		        	{"data": null,"className":"dt-body-center"//0
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2
				}, {"data": "quantityOnHand","className":"td-right"	//3
				}, {"data": null,"className":"td-right"//4
				}, {"data": "areaNumber","className":"td-right"//5
				}
			],
			"columnDefs":[
	    		
	    		{"targets":2,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					var inputTxt = '';
	    			inputTxt= inputTxt + '<input type="hidden" id="stockList'+index+'.materialid" name="stockList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			
	    			return name + inputTxt;
                }},
	    		{"targets":4,"render":function(data, type, row){	
	    			
					var index=row["rownum"];
					var quantity = (row["quantity"]);
					var inputTxt = '<input type="text" id="stockList'+index+'.quantity" name="stockList['+index+'].quantity" class="quantity num "  value="'+quantity+'"/>';
				
					return inputTxt;
                }},
                {
					"visible" : false,
					"targets" : []
				}
			]
			
		}).draw();

						
		t.on('click', 'tr', function() {
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }			
		});
		
		t.on('order.dt search.dt draw.dt', function() {
			t.column(0, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	};
	
	$(document).ready(function() {
		
		var scrollHeight =$(parent).height() - 200; 
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#stockout\\.checkoutdate").val(shortToday());
		
		ajax(scrollHeight);
		
		$("#stockout\\.checkoutdate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var makeType=$('#makeType').val();
					var usedType=$('#usedType').val();
					var url = "${ctx}/business/stockout?makeType="
							+makeType+"&usedType="+usedType;
					location.href = url;		
				});

		$("#showHistory").click(
				function() {
					var YSId=$('#stockout\\.ysid').val();
					var makeType=$('#makeType').val();
					var usedType=$('#usedType').val();
					var url = "${ctx}/business/stockout?methodtype=stockoutHistoryInit&YSId="+YSId
					+"&makeType="+makeType+"&usedType="+usedType;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {

					var makeType=$('#makeType').val();
					var usedType=$('#usedType').val();
			$('#formModel').attr("action", "${ctx}/business/stockout?methodtype=update"+"&makeType="+makeType+"&usedType="+usedType);
			$('#formModel').submit();
		});
		
		$("#reverse").click(function () { 
			$("input[name='numCheck']").each(function () {  
		        $(this).prop("checked", !$(this).prop("checked"));  
		    });
		});
				
		foucsInit();

		productPhotoView();//出库单附件
		
		//产品图片添加位置,                                                                                                                                                                                        
		var productIndex = 1;
		$("#addProductPhoto").click(function() {
			
			var path='${ctx}';
			var cols = $("#productPhoto tbody td.photo").length - 1;
			//从 1 开始
			var trHtml = addPhotoRow('productPhoto','uploadProductPhoto',productIndex,path);		

			$('#productPhoto td.photo:eq('+0+')').after(trHtml);	
			productIndex++;		
			//alert("row:"+row+"-----"+"::productIndex:"+productIndex)
		});
		
	});

	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">
	
	<input type="hidden" id="makeType" value="${makeType }" />
	<input type="hidden" id="usedType" value="${usedType }" />
	<form:hidden path="stockout.ysid" />
	<form:hidden path="stockout.requisitionid"  />
	<form:hidden path="stockout.recordid"  />
	
	<fieldset>
		<legend> 出库单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">出库单编号：</td>					
				<td width="150px">${ formModel.stockout.stockoutid}
					<form:hidden path="stockout.stockoutid"  /></td>
														
				<td width="100px" class="label">出库日期：</td>
				<td>
					<form:input path="stockout.checkoutdate" class="short read-only" /></td>
				
				<td width="100px" class="label">仓管员：</td>
				<td>
					<form:input path="stockout.keepuser" class="short read-only" value="${userName }" /></td>
			</tr>
			<!-- 
			<tr> 				
				<td class="label">耀升编号：</td>					
				<td>&nbsp;${order.YSId }</td>
																
				<td class="label">产品编号：</td>					
				<td>&nbsp;${order.materialId }</td>
							
				<td class="label">产品名称：</td>					
				<td>&nbsp;${order.materialName }</td>
			</tr>
			 -->
										
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认出库</a>
	<!-- 	<a class="DTTT_button DTTT_button_text" id="print" onclick="doPrint();return false;">打印领料单</a> -->
		<a class="DTTT_button DTTT_button_text" id="showHistory" >查看出库记录</a>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>

	<fieldset style="margin-top: -30px;">
		<legend> 物料需求表</legend>
		<div class="list">
			<table id="example" class="display" >
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>
						<th width="60px">可用库存</th>
						<th width="80px">本次领料</th>
						<th width="160px">库位</th>
					</tr>
				</thead>	
			</table>
		</div>
	</fieldset>
	<fieldset>
		<span class="tablename">附件清单</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
		<div class="list">
			<div class="showPhotoDiv" style="overflow: auto;">
				<table id="productPhoto" style="width:100%;height:335px">
					<tbody><tr><td class="photo"></td></tr></tbody>
				</table>
			</div>
		</div>	
	</fieldset>
</form:form>

</div>
</div>
</body>
<script type="text/javascript">

function productPhotoView() {
	var YSId = $('#stockout\\.ysid').val();
	$.ajax({
		"url" :"${ctx}/business/stockout?methodtype=getProductPhoto"+"&YSId="+YSId,	
		"datatype": "json", 
		"contentType": "application/json; charset=utf-8",
		"type" : "GET",
		data : null,// 你的formid
		success: function(data){
				
			var countData = data["productFileCount"];
			//alert(countData)
			photoView('productPhoto','uploadProductPhoto',countData,data['productFileList'])		
		},
		 error:function(XMLHttpRequest, textStatus, errorThrown){
         	//alert(errorThrown)
		 }
	});
	
}//产品图片

function photoView(id, tdTable, count, data) {
	
	var row = 0;
	for (var index = 0; index < count; index++) {
		var path = '${ctx}' + data[index];
		var pathDel = data[index];		
		var trHtml = showPhotoRow(id,tdTable,path,pathDel,index);		
		$('#' + id + ' td.photo:eq(' + row + ')').after(trHtml);
		row++;
	}
}


function deletePhoto(tableId,tdTable,path) {
	
	var url = '${ctx}/business/stockout?methodtype='+tableId+'Delete';
	url+='&tabelId='+tableId+"&path="+path;
	    
	if(!(confirm("确定要删除该图片吗？"))){
		return;
	}
    $("#formModel").ajaxSubmit({
		type: "POST",
		url:url,	
		data:$('#formModel').serialize(),// 你的formid
		dataType: 'json',
	    success: function(data){
	    	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
			}
			
			//删除后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片删除失败,请重试。")
		}
	});
}

function uploadPhoto(tableId,tdTable, id) {

	var url = '${ctx}/business/ODOUpload?methodtype=uploadPhoto';
	
	$("#formModel").ajaxSubmit({
		type : "POST",
		url : url,
		data : $('#formModel').serialize(),// 你的formid
		dataType : 'json',
		success : function(data) {
	
			var type = tableId;
			var countData = "0";
			var photo="";
			var flg="true";
			switch (type) {
				case "productPhoto":
					countData = data["productFileCount"];
					photo = data['productFileList'];
					break;
			}
			
			//添加后,刷新现有图片
			$("#" + tableId + " td:gt(0)").remove();
			if(flg =="true"){
				photoView(tableId, tdTable, countData, photo);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("图片上传失败,请重试。")
		}
	});
}

</script>
</html>
