<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>料件出库-出库确认(配件单)</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var options ="";	
	var shortYear = ""; 
	
	function ajax(scrollHeight) {
		
		var requisitionid= $("#stockout\\.requisitionid").val();
		var YSId= '${order.YSId }';
		var actionUrl = "${ctx}/business/stockout?methodtype=getRequisitionDetail";
		actionUrl = actionUrl +"&requisitionId="+requisitionid;
		actionUrl = actionUrl +"&YSId="+YSId;
		
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
	        "width"  : false,
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
						
						//foucsInit();
						
						setDepotId();//设置物料的默认仓库
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
				}, {"data": "orderYsid","className":"td-left"//1
				}, {"data": "materialId","className":"td-left"//2
				}, {"data": "materialName",						//3
				}, {"data": "quantityOnHand","className":"td-right"	//4
				}, {"data": "orderQty","className":"td-right"	//5
				}, {"data": "stockoutQty","className":"td-right","defaultContent" : '0'//6 已出库数量
				}, {"data": null,"className":"td-right"//7  本次领料
				}, {"data": null,"className":"td-right"//8
				}, {"data": "areaNumber","className":""//9
				}
			],
			"columnDefs":[
				{"targets":2,"render":function(data, type, row){ 					
					var index=row["rownum"]	
					var inputTxt = '';
					inputTxt= inputTxt + row["materialId"];
					inputTxt= inputTxt + '<input type="hidden" id="stockList'+index+'.materialid" name="stockList['+index+'].materialid" value="'+row["materialId"]+'"/>';					
					return name + inputTxt;
				}},
	    		{"targets":3,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],40);
					var inputTxt = '';
	    			inputTxt= inputTxt + '<input type="hidden" id="stockList'+index+'.price" name="stockList['+index+'].price" value="'+row["MAPrice"]+'"/>';
	    			
	    			return name + inputTxt;
                }},
	    		{"targets":4,"render":function(data, type, row){//当前库存
					var quantityOnHand= floatToCurrency( row["quantityOnHand"] );	
					var materialId=row["materialId"]	
					var inputTxt = '';
					inputTxt= inputTxt +'<a href="###" onClick="doShowInventory(\''+materialId+'\')">'+quantityOnHand+'</a>';
	    			
	    			return inputTxt;
                }},
                
	    		{"targets":6,"render":function(data, type, row){//已出库数
	    			
	    			return floatToCurrency(data);
                }},
                
	    		{"targets":7,"render":function(data, type, row){//本次领料
	    			
					var index=row["rownum"];
					var quantity       = currencyToFloat(row["requisitionQty"]);
					var stockoutQty    = currencyToFloat(row["stockoutQty"]);
					var quantityOnHand = currencyToFloat(row["quantityOnHand"] );	
					var shengyu        = currencyToFloat(quantity - stockoutQty );
					var benCi = 0;
					
					if(quantity < 0){
						//退货申请
						benCi = quantity;
					}else{
						if(quantityOnHand <= 0){
							benCi = 0;
						}else if(quantityOnHand <= shengyu){
							benCi = quantityOnHand;
						}else{
							benCi = shengyu;
						}
						var className="num mini";
						if(benCi <= 0){
							benCi = 0;
							if(shengyu > 0)
								className="num mini error";// class="num mini"
						}
						
					}
					var inputTxt = '';
					if(benCi == 0){
						inputTxt = '0' + '<input class="'+className+'" type="hidden" id="stockList'+index+'.quantity" name="stockList['+index+'].quantity" value="'+benCi+'" />';												
					}else if(benCi == 0 && quantityOnHand <= 0){
						inputTxt = '0' + '<input class="'+className+'" type="hidden" id="stockList'+index+'.quantity" name="stockList['+index+'].quantity" value="'+benCi+'" />';							
					}else{
						inputTxt = '<input class="'+className+'" type="text" id="stockList'+index+'.quantity" name="stockList['+index+'].quantity" value="'+benCi+'" />';							
					}
				
					return inputTxt;
                }},
	    		{"targets":8,"render":function(data, type, row){	
	    			
					var index=row["rownum"];
					var quantity = (row["quantity"]);
					var inputTxt = '<select  id="stockList'+index+'.depotid" name="stockList['+index+'].depotid"    class="short depotid"></select>';
				
					return inputTxt;
                }},
	    		{"targets":9,"render":function(data, type, row){ 					
			
					return  jQuery.fixedWidth( data,18);
					
                }},
                {
					"visible" : false,
					"targets" : []
				}
			]
			
		}).draw();

		t.on('change', 'tr td:nth-child(8)',function() {

			var $td = $(this).parent().find("td");

			var $oOnhand  = $td.eq(4);//库存
			var $oArrival = $td.eq(5);//计划
			var $oyiling  = $td.eq(6);//已领料
			var $oCurrQty = $td.eq(7).find("input");//本次领料

			var fOnhand = currencyToFloat($oOnhand.text());
			var fArrival  = currencyToFloat($oArrival.text());
			var fYiling   = currencyToFloat($oyiling.text());
			var benCi  = currencyToFloat($oCurrQty.val());	

			//最多允许领料数量 = 计划 - 已领料
		 	var shengyu = fArrival - fYiling;
			//var benCi = 0;
			
			if(fOnhand <= 0){
				$().toastmessage('showWarningToast', "当前没有库存数！");
				benCi = 0;
			}else if(fOnhand >= shengyu ){
				if(benCi > shengyu){
					$().toastmessage('showWarningToast', "领料数量不能超过需求量！");
					benCi = shengyu;
				}
			}else if(fOnhand < shengyu ){
				if(benCi > shengyu || benCi > fOnhand){
					$().toastmessage('showWarningToast', "领料数量不能超过库存数！");
					benCi = fOnhand;
				}
			}			
			
			//alert("fArrival--fYiling--fMaxQuanty--fCurrQty:"+fArrival+"---"+fYiling+"--"+fMaxQuanty+"---"+fCurrQty)
			if ( benCi > shengyu + 1 ){//允许小数位往上收
				
				benCi = shengyu;
				$().toastmessage('showWarningToast', "领料数量不能超出需求量！");
			}
			
			$oCurrQty.val(floatToCurrency(benCi));

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


		var width = $(window).width();
		$(".showPhotoDiv").width(width - 100);
		
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
					var usedType=$('#usedType').val();
					var url = "${ctx}/business/stockout?methodtype=partsStockoutSearchInit"
							+"&usedType="+usedType;
					location.href = url;		
				});

		
		$("#insert").click(function() {
					
			var requisitionType=$('#requisitionType').val();
			var YSId = '${order.YSId }';
			
			var checkFlag = stockoutQuantityCheck();
			//checkFlag = true;//11.2临时开放
			if(checkFlag == true){
				if(confirm("个别物料的出库量为零，确定要继续出库吗？")){

					$("#insert").attr("disabled", "disabled");
					$('#formModel').attr("action", "${ctx}/business/stockout?methodtype=partsAdd"
							+"&requisitionType="+requisitionType+"&YSId="+YSId);
					$('#formModel').submit();
				}else{

					return false;
				}
			}
					
		});
		
				
		//foucsInit();

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
		
		//设置物料的仓库类别列表
		var i = 0;	
		<c:forEach var="list" items="${depotList}">
			i++;
			options += '<option value="${list.key}">${list.value}</option>';
		</c:forEach>		
		
	});

	//领料数量Check
	function stockoutQuantityCheck(){

		var checkFlag = false;
		$('#example tbody tr').each (function (){

			var vtotal5 = $(this).find("td").eq(5).text();//需求量
			var vtotal6 = $(this).find("td").eq(6).text();//已领料
			var vtotal7 = $(this).find("td").eq(7).text();//本次领料
			vtotal5 = currencyToFloat(vtotal5);
			vtotal6 = currencyToFloat(vtotal6);
			vtotal7 = currencyToFloat(vtotal7);
			
			if(vtotal7 == 0 && vtotal6 < vtotal5){
				checkFlag = true;
				return false; //跳出循环
			}			
		})
		return checkFlag;
	}
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
	<input type="hidden" id="requisitionType" value="${requisitionType }" />
	<form:hidden path="stockout.ysid" />
	<form:hidden path="stockout.requisitionid" />
	
	<fieldset>
		<legend> 出库单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">出库单编号：</td>					
				<td width="100px">${formModel.stockout.requisitionid }</td>
													
				<td width="100px" class="label">出库日期：</td>
				<td width="150px">
					<form:input path="stockout.checkoutdate" class="short read-only" /></td>
				
				<td width="100px" class="label">仓管员：</td>
				<td>
					<form:input path="stockout.keepuser" class="short read-only" value="${userName }" /></td>
			</tr>
			 						
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;">
		<button class="DTTT_button " id="insert" >确认出库</button>
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>

	<fieldset style="margin-top: -30px;">
		<legend> 物料需求表</legend>
		<div class="list">
			<table id="example" class="display" >
				<thead>				
					<tr>
						<th style="width:1px">No</th>
						<th width="80px">耀升编号</th>
						<th width="120px">物料编号</th>
						<th >物料名称</th>
						<th width="60px">当前库存</th>
						<th width="60px">订单数量</th>
						<th width="60px">已领数量</th>
						<th width="60px">本次领料</th>
						<th width="80px">仓库分类</th>
						<th width="80px">库位</th>
					</tr>
				</thead>	
			</table>
		</div>
	</fieldset>
	<fieldset>
		<span class="tablename">附件清单</span>&nbsp;<button type="button" id="addProductPhoto" class="DTTT_button">添加图片</button>
		<div class="list">
			<div class="showPhotoDiv" style="overflow: auto;width: 1024px;">
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
	var requisitionId = $('#stockout\\.requisitionid').val();
	$.ajax({
		"url" :"${ctx}/business/stockout?methodtype=getProductPhoto"+"&YSId="+YSId+"&requisitionId="+requisitionId,	
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

function setDepotId(){
	
	$('#example tbody tr').each (function (){

		$(this).find("td").eq(8).find("select").html(options);
		
		var materialId = $(this).find("td").eq(2).find("input").val();
		
		var depotid='010';//采购件
		if(materialId.substring(0,1) == 'A'){								
			depotid='030';//原材料	
		}else if(materialId.substring(0,1) == 'G'){
			depotid='040';//包装件								
		}else if(materialId.substring(0,3) == 'B01'){
			depotid='020';//自制件								
		}
		
		$(this).find("td").eq(8).find("select").val(depotid);
					
	});	
	
}


function doShowInventory(materialId){

	var url = "${ctx}/business/storage?methodtype=beginningInventoryNewWindowSearch";
	url = url + "&keyword1="+materialId;
	callProductDesignView("库存确认",url);
}

</script>
</html>