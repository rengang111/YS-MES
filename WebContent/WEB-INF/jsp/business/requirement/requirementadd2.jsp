<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购方案--新建(半成品)</title>
<%@ include file="../../common/common2.jsp"%>
 <style>th, td { white-space: nowrap; }</style>
  	
<script type="text/javascript">

	var counter  = 0;
	//Form序列化后转为AJAX可提交的JSON格式。
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};	
	
	$.fn.dataTable.TableTools.buttons.add_rows = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {
					
					// var rowIndex = counter;
					
					for (var i=0;i<10;i++){
						
				var rowNode = $('#example')
							.DataTable()
							.row
							.add(
							  [
								'<td></td>',
								'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
									'<input type="hidden" name="purchaseList['+counter+'].materialid" id="purchaseList'+counter+'.materialid" /></td>',
								'<td><span></span></td>',
								'<td><input type="text"   name="attributeList2"  class="attributeList2" style="width:80px"> '+
									'<input type="hidden" name="purchaseList['+counter+'].supplierid" id="purchaseList'+counter+'.supplierid" /></td>',
								'<td><input type="text"   name="purchaseList['+counter+'].quantity"   id="purchaseList'+counter+'.quantity"   class="cash"  style="width:70px"/></td>',
								'<td><span></span><input type="hidden"   name="purchaseList['+counter+'].price"      id="purchaseList'+counter+'.price" /></td>',
								'<td><span></span><input type="hidden"   name="purchaseList['+counter+'].totalprice" id="purchaseList'+counter+'.totalprice"/><input type="hidden" id="labor"></td>',
								]).draw();
						
						counter ++;						
					}					
					//counter += 1;
					
					autocomplete();
						
					foucsInit();
				}
			});

	$.fn.dataTable.TableTools.buttons.reset = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {
			
			var t=$('#example').DataTable();
			
			rowIndex = t.row('.selected').index();
			
			var str = true;
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					var n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
					$('#example tbody').find("tr:eq("+n+")").remove();
					str = false;
				}
			});
			
			if(str){
				$().toastmessage('showWarningToast', "请选择要删除的数据。");
			}else{
				$().toastmessage('showNoticeToast', "删除成功。");	
				costAcount();				
			}
				
		}
	});
	
	
	$(document).ready(function() {		
		
		
		baseBomView();//分离数量
		
		productStock();//半成品库存信息
		
		autocomplete();
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/order";
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					
			$('#attrForm').attr("action", "${ctx}/business/requirement?methodtype=insertProcurement");
			$('#attrForm').submit();
		});
			
		
		$("input:text").focus (function(){
		    $(this).select();
		});

		$(".DTTT_container").css('float','left');

		
		$('#example').DataTable().on('click', 'tr', function() {

			var rowIndex = $(this).context._DT_RowIndex; //行号		
			//alert(rowIndex)			
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#example').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            
	        }		
			
		});
		
		foucsInit();//input获取焦点初始化处理
	
	});

</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">
		
		
		<input type="hidden" id="tmpMaterialId" />
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:150px;">${order.YSId}
					<form:hidden path="bomPlan.ysid"  value="${order.YSId}" /></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;">${order.materialId}
					<form:hidden path="bomPlan.materialid"  value="${order.materialId}" /></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td>&nbsp;${order.materialName}</td>
				</tr>
				<tr>
					<td class="label"><label>ＰＩ编号：</label></td>
					<td>${order.PIId}</td>

					<td class="label"><label>订单数量：</label></td>
					<td><span id="quantity">${order.quantity}</span>（${order.unit}）</td>
						
					<td class="label"><label>客户名称：</label></td>
					<td>${order.customerFullName}</td>
				</tr>							
			</table>
		</fieldset>
			
		<fieldset>
			<legend>半成品库存信息</legend>
			<div class="list" style="width: 60%;">
				<table id="productStock" class="display" style="width:98%">
						<thead>				
							<tr>
								<th width="1px">No</th>
								<th class="dt-center" style="width:150px">物料编码</th>
								<th class="dt-center" style="width:100px">当前库存</th>
								<th class="dt-center" style="width:80px">操作</th>
							</tr>
						</thead>			
				</table>
			</div>
		</fieldset>
				
		<fieldset class="action" style="text-align: right;margin-top: -50px;width: 30%;float: right;">
			<button type="button" id="deleteOrderBom" class="DTTT_button">删除订单BOM</button>
			<button type="button" id="requirement" class="DTTT_button">生成采购合同</button>
			<button type="button" id="goBack" class="DTTT_button goBack">返回</button>
		</fieldset>	
		
		<fieldset>
		<legend>订单BOM方案</legend>

			<table id="baseBomTable" class="display" style="width:98%">
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:120px">物料编码</th>
						<th class="dt-center" style="width:180px">物料名称</th>
						<th class="dt-center" width="60px">用量</th>
						<th class="dt-center" style="width:30px">单位</th>
						<th class="dt-center" width="60px">订单数量</th>
						<th class="dt-center" width="80px">总量</th>
						<th class="dt-center" width="60px">当前库存</th>
						<th class="dt-center" width="80px">建议需求量</th>
					</tr>
				</thead>			
			</table>
		</fieldset>
		
		<div style="clear: both"></div>		
	</form:form>
</div>
</div>

<script  type="text/javascript">
function baseBomView() {

	var materialId='${order.materialId}';
	var table = $('#baseBomTable').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#baseBomTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/bom?methodtype=getBaseBom&materialId="+materialId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
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
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "materialId"},
			{"data": "materialName"},
			{"data": "quantity","className" : 'td-right'},
			{"data": "unit","className" : 'td-center'},
			{"data": null,"className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
		 ],
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];				    			
    			name = jQuery.fixedWidth(name,30);				    			
    			return name;
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var materialId = row["materialId"];
    			var subBomId = row["subBomId"];
    			var rownum = row["rownum"]-1;
    			rtn= "<a href=\"###\" onClick=\"doEditMaterial('" + row["rawRecordId"] +"','"+ row["parentId"] + "')\">"+materialId+"</a>";
    			rtn=rtn+ "<input type=\"hidden\" id=\"bomDetailList"+rownum+".materialid\" name=\"bomDetailList["+rownum+"].materialid\" value=\""+materialId+"\">";
    			rtn=rtn+ "<input type=\"hidden\" id=\"bomDetailList"+rownum+".subbomid\"   name=\"bomDetailList["+rownum+"].subbomid\" value=\""+subBomId+"\">";
    			return rtn;
    		}},
    		{"targets":3,"render":function(data, type, row){
    			var quantity = row["quantity"];
    			var rownum = row["rownum"]-1;
    			var sourceprice = row["price"];
    			var supplierid = row["supplierId"];
    			rtn=quantity+ "<input type=\"hidden\" id=\"bomDetailList"+rownum+".quantity\" name=\"bomDetailList["+rownum+"].quantity\" value=\""+quantity+"\">";
    			rtn=rtn  + "<input type=\"hidden\" id=\"bomDetailList"+rownum+".sourceprice\" name=\"bomDetailList["+rownum+"].sourceprice\" value=\""+sourceprice+"\">";
    			rtn=rtn  + "<input type=\"hidden\" id=\"bomDetailList"+rownum+".supplierid\" name=\"bomDetailList["+rownum+"].supplierid\" value=\""+supplierid+"\">";
    			return rtn;
    		}},
    		{"targets":5,"render":function(data, type, row){
    			
    			var quantity =  '${order.quantity}' ;
    			return quantity;
    		}},
    		{"targets":6,"render":function(data, type, row){
    			var price = currencyToFloat('${order.quantity}' );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var total = floatToCurrency( price * quantity );			    			
    			return total;
    		}},
    		{"targets":7,"render":function(data, type, row){			    			
    			return "0";
    		}},
    		{"targets":8,"render":function(data, type, row){
    			var price = currencyToFloat('${order.quantity}' );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var total = floatToCurrency( price * quantity );			    			
    			return total;
    		}}
          
        ] 
	});
	
	t2.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t2.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});

	t2.on('order.dt search.dt draw.dt', function() {
		t2.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();

	
}//ajax()

function doEditMaterial(recordid,parentid) {
	//accessFlg:1 标识新窗口打开
	var url = '${ctx}/business/material?methodtype=detailView&keyBackup=1';
	url = url + '&parentId=' + parentid+'&recordId='+recordid;
	
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '520px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		  baseBomView();
		  return false; 
		}    
	});		

};

function productStock() {

	var materialId='${order.materialId}';
	var table = $('#productStock').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#productStock').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		//"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/inventory?methodtype=getSemiProductStock&materialId="+materialId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					//alert("recordsTotal"+data["recordsTotal"])
					fnCallback(data);
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
						alert(XMLHttpRequest.status);
						alert(XMLHttpRequest.readyState);
						alert(textStatus);
						alert(errorThrown);
	             }
			})
		},
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "materialId"},
			{"data": "promise","className" : 'td-right'},
			{"data": null,"className" : 'td-center'}
		 ],
		 "columnDefs":[
	 		{"targets":3,"render":function(data, type, row){
	 			return  "<a href=\"###\" onClick=\"doShow('" + row["recordId"] +"','"+ row["materialId"] + "')\">使用库存</a>";
	    		
	 		}}
			]
		
	});
	
	t2.on('click', 'tr', function() {

		if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            t2.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});

	t2.on('order.dt search.dt draw.dt', function() {
		t2.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			cell.innerHTML = num ;
		});
	}).draw();

	
}//ajax()

</script>



<script type="text/javascript">

function autocomplete(){
	
	
	//供应商选择
	$(".attributeList2").autocomplete({
		minLength : 0,
		autoFocus : false,
		
		search: function( event, ui ) {
			
			var $tds = $(this).parent().parent().find('td');
			var material = $tds.eq(11).find('input').val();
			$('#tmpMaterialId').val(material);

			 if(material==""){
				$().toastmessage('showWarningToast', "请先输入ERP编号。");	
				 return false;
			 }
			
		},

		source : function(request, response) {
			var url = "${ctx}/business/bom?methodtype=getSupplierPriceList";
			var MaterialId = $('#tmpMaterialId').val();
			$
			.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				data : {
					key1 : request.term,
					key2 : MaterialId,
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.viewList,
									value : item.supplierId,
									id : item.supplierId,
									supplierId : item.supplierId,
								     price : item.price
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
		
	});//供应商选择
}

</script>
</body>
	
</html>
