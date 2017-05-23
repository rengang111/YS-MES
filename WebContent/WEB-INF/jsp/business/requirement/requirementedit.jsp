<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购方案--编辑</title>
<%@ include file="../../common/common2.jsp"%>
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

					//重设显示窗口(iframe)高度
					iFramAutoSroll();
						
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

		$("#bomPlan\\.plandate").val(shortToday());

		//alert(00000)
		requirementAjax();//
		//alert(11111)
		baseBomView();//分离数量
		//alert(22222)		
		ZZmaterialView();//合并数量
		//alert(33333)
		autocomplete();
		//alert(44444)
		
		$(".goBack").click(
				function() {
					var YSId = '${order.YSId}';
					var materialId = '${order.materialId}';
					var url = '${ctx}/business/bom?methodtype=orderDetail&YSId=' + YSId+'&materialId='+materialId;
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
					
			$('#attrForm').attr("action", "${ctx}/business/requirement?methodtype=updateProcurement");
			$('#attrForm').submit();
		});
			
		
		$("input:text").focus (function(){
		    $(this).select();
		});

		$(".DTTT_container").css('float','left');

		$( "#tabs" ).tabs();
		$( "#tabs" ).tabs( "option", "active", 2 );//设置默认显示内容
		/*
		$(".tabs1").click( function() {
			$('#baseBomTable thead tr').each (function (){
				$(this).find("th").eq(0).css('width','30px ');
				$(this).find("th").eq(1).css('width','120px');
				$(this).find("th").eq(2).css('width','200px');
				$(this).find("th").eq(3).css('width','60px');
				$(this).find("th").eq(4).css('width','30px');
				$(this).find("th").eq(5).css('width','60px');
				$(this).find("th").eq(6).css('width','80px');
				$(this).find("th").eq(7).css('width','60px');
				$(this).find("th").eq(8).css('width','80px');
				
			})
		});
		$(".tabs2").click( function() {
			$('#ZZmaterial thead tr').each (function (){
				$(this).find("th").eq(0).css('width','30px ');
				$(this).find("th").eq(1).css('width','150px');
				$(this).find("th").eq(2).css('width','250px');
				$(this).find("th").eq(3).css('width','60px');
				$(this).find("th").eq(4).css('width','80px');
				$(this).find("th").eq(5).css('width','80px');
				$(this).find("th").eq(6).css('width','100px');
			})
		});
		*/
		
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
			
			//$('.DTFC_Cloned').find('tr').removeClass('selected');
			//$('.DTFC_Cloned').find('tr').eq(rowIndex).addClass('selected');
			
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
		<form:hidden path="bomPlan.plandate" />
		<form:hidden path="bomPlan.bomid" value="${bomId}" />
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
		<button type="button" id="goBack" class="DTTT_button goBack" style="float: right;margin: -65px 40px 0px 0px;">返回</button>
	

	<div id="tabs" style="padding: 0px;white-space: nowrap;">
		<ul>
			<li><a href="#tabs-1" class="tabs1">一级BOM</a></li>
			<li><a href="#tabs-2" class="tabs2">二级BOM</a></li>
			<li><a href="#tabs-3" class="tabs3">采购方案</a></li>
		</ul>

		<div id="tabs-1" style="padding: 5px;">

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
		</div>
		<div id="tabs-2" style="padding: 5px;">

			<table id="ZZmaterial" class="display" style="width:98%">
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:120px !important">原材料编码</th>
						<th class="dt-center">原材料名称</th>
						<th class="dt-center" width="60px !important">单位</th>
						<th class="dt-center" width="80px">总量</th>
						<th class="dt-center" width="60px">当前库存</th>
						<th class="dt-center" width="80px !important">建议需求量</th>
					</tr>
				</thead>
				
				<tbody>
				<c:forEach var="detail" items="${requirement}" varStatus='status' >	
					<c:if test="${detail.materialId.substring(0,1) == 'A'}">
						<tr>
							<td></td>
							<td>${detail.materialId}</td>								
							<td><span id="rawName${status.index}"></span></td>
							<td>${detail.unit}</td>		
							<td>${detail.orderQuantity}</td>	
							<td>0</td>	
							<td>${detail.quantity}</td>
						</tr>

						<script type="text/javascript">
							var index = '${status.index}';
							var materialName = '${detail.materialName}';
							var quantity = '${detail.advice}';	
							//alert('quantity['+'${detail.advice}'+']')	
							$('#rawName'+index).html(jQuery.fixedWidth(materialName,25));
						</script>
					</c:if>
				</c:forEach>	
				
				</tbody>		
			</table>
		</div>
		<div id="tabs-3" style="padding: 5px;">
			<table id="example" class="display" >
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:150px">物料编码</th>
						<th class="dt-center" style="width:180px">物料名称</th>
						<th class="dt-center" style="width:30px">单位</th>
						<th class="dt-center" width="60px">订单需求量</th>
						<th class="dt-center" width="60px">当前库存</th>
						<th class="dt-center" width="60px">建议采购量</th>
						<th class="dt-center" style="width:30px">本次单价</th>
						<th class="dt-center" style="width:80px">总价</th>
						<th class="dt-center" width="60px">供应商</th>						
						<th class="dt-center" width="100px">当前价格</th>	
						<th class="dt-center" style="width:100px">历史最低</th>	
						<th class="dt-center" style="width:1px"></th>	
					</tr>
				</thead>
				<tbody>
				<c:forEach var="detail" items="${requirement}" varStatus='status' >		
				
				<tr>
					<td></td>
					<td>${detail.materialId}</td>									
					<td><span id="name${status.index}"></span></td>
					<td>${detail.unit}</td>
					<td>${detail.orderQuantity}
						<form:hidden path="purchaseList[${status.index}].orderquantity"  value="${detail.orderQuantity}" /></td>
					<td>0</td>
					<td><form:input path="purchaseList[${status.index}].quantity" value="${detail.quantity}" class="mini num" /></td>			
					<td><form:input  path="purchaseList[${status.index}].price"  value="${detail.lastPrice}"  class="cash" style="width:100px" /></td>				
					<td><span id="total${status.index}"></span>
						<form:hidden path="purchaseList[${status.index}].totalprice" /></td>					
					<td><form:input path="purchaseList[${status.index}].supplierid"  class="attributeList2"  value="${detail.lastSupplierId}" style="width:100px" /></td>
					<td><span id="last${status.index}"></span>
						<input type="hidden" id="lastPrice${status.index}"></td>
					<td><span id="min${status.index}"></span>
						<form:hidden path="purchaseList[${status.index}].materialid"  value="${detail.materialId}" /></td>
					<td></td>
					
						
					
				</tr>

				<script type="text/javascript">
					var index = '${status.index}';
					var lastPrice = float4ToCurrency( '${detail.lastPrice}' );
					var lastSupplierId = '${detail.lastSupplierId}';
					var minPrice = float4ToCurrency( '${detail.minPrice}' );
					var minSupplierId = '${detail.minSupplierId}';
					var materialName = '${detail.materialName}';
					var price =currencyToFloat( '${detail.lastPrice}');
					var quantity = currencyToFloat('${detail.quantity}');	
					
					var total = floatToCurrency( price * quantity );
					
					$('#purchaseList'+index+'\\.totalprice').val(total);
					$('#total'+index).html(total);
					
					$('#last'+index).html(lastPrice+'／'+stringPadAfter(lastSupplierId,12) );
					$('#min'+index).html(minPrice+'／'+stringPadAfter(minSupplierId,12) );
					$('#lastPrice'+index).val(lastPrice);
					
					$('#name'+index).html(jQuery.fixedWidth(materialName,25));
					
					counter++;
				</script>
				
			</c:forEach>	
			</tbody>	
			
			</table>
		<fieldset class="action" style="text-align: right;margin-top: 10px;">
			<button type="button" id="insert" class="DTTT_button">保存</button>
			<button type="button" id="goBack" class="DTTT_button goBack">返回</button>
		</fieldset>	
	
		</div>
	</div>
		
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
    			var rownum = row["rownum"]-1;
    			rtn= "<a href=\"###\" onClick=\"doEditMaterial('" + row["rawRecordId"] +"','"+ row["parentId"] + "')\">"+materialId+"</a>";
    			rtn=rtn+ "<input type=\"hidden\" id=\"bomDetailList"+rownum+".materialid\" name=\"bomDetailList["+rownum+"].materialid\" value=\""+materialId+"\">";
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

	
}//ajax()供应商信息

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

function doEditMaterial2(recordid,parentid) {
	
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
		   // ZZmaterialView();
		   window.location.reload();
		  return false; 
		}    
	});		

};

</script>

<script  type="text/javascript">
function ZZmaterialView() {

	var materialId='${order.materialId}';
	var table = $('#ZZmaterial').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#ZZmaterial').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		//"sAjaxSource" : "${ctx}/business/requirement?methodtype=getzzMaterial&materialId="+materialId,				
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
			{},
			{},
			{"className" : 'td-center'},
			{"defaultContent" : '0',"className" : 'td-right'},
			{"defaultContent" : '0',"className" : 'td-right'},
			{"defaultContent" : '0',"className" : 'td-right'},
		 ],
		 /*
		"columnDefs":[
      		{"targets":1,"render":function(data, type, row){
      			var name = row["rawMaterialId"];
    			if(name == null){
    				//name = '******';
    				name = "<a href=\"###\" onClick=\"doEditMaterial2('" + row["materialRecordId"] +"','"+ row["materialParentId"] + "')\">"+row["materialId"]+"</a>";
    			}			    			
    			return name;
    		}},
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["rawMaterialName"];
    			if(name == null){
    				name = '******';
    			}else{
        			name = jQuery.fixedWidth(name,40);	
    			}			    			
    			return name;
    		}},
    		{"targets":3,"render":function(data, type, row){
    			var rtn ='';
    			var unit = row["unitName"];
    			var zzunit = row["zzunitName"];
    			//alert("["+zzunit+"]")
    			
    			if(zzunit == ''){
    				rtn = unit;
    			}else{
    				rtn = zzunit;	
    			}			    			
    			return rtn;
    		}}
          
        ] ,
        */
	     "aaSorting": [[ 1, "asc" ]]
	});
	
	t2.on('blur', 'tr td:nth-child(7),tr td:nth-child(8),tr td:nth-child(9)',function() {
		
        $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

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

	
}//ajax()供应商信息
</script>

<script  type="text/javascript">

function requirementAjax() {

	var scrollHeight = $(window).height() - 250;
	
	var t3 = $('#example').DataTable({

		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
        "sScrollY": scrollHeight,
        "sScrollX": true,
        "fixedColumns":   {
            leftColumns: 3
        },
		"dom" : '<"clear">rt',

		"columns" : [ 
		        	{"className":"dt-body-center"
				}, {
				}, {								
				}, {"className":"td-center"
				}, {"className":"td-right"
				}, {"className":"td-right"
				}, {"className":"td-right"
				}, {"className":"td-right"
				}, {"className":"td-right bold"	
				}, {"className":"td-right"			
				}, {"className":"td-right"
				}, {"className":"td-right"
				}, {"className":"td-right"
				}			
			],
		    "aaSorting": [[ 1, "asc" ]],
			"columnDefs":[
	    		{
					"visible" : false,
					"targets" : [ 12 ]
				} 
			] 
		
	}).draw();

	
	t3.on('blur', 'tr td:nth-child(7),tr td:nth-child(8),tr td:nth-child(10)',function() {
		
		var currValue = $(this).find("input:text").val().trim();

        $(this).find("input:text").removeClass('bgwhite');
        
        if(currValue =="" ){
        	
        	 $(this).find("input:text").addClass('error');
        }else{
        	 $(this).find("input:text").addClass('bgnone');
        }
		
	});
			

	t3.on('change', 'tr td:nth-child(7),tr td:nth-child(8)',function() {
		
		/*产品成本 = 各项累计
		人工成本 = H带头的ERP编号下的累加
		材料成本 = 产品成本 - 人工成本
		经管费 = 经管费率 x 产品成本
		核算成本 = 产品成本 + 经管费*/
		
        var $tds = $(this).parent().find("td");
		
        //var $oMaterial  = $tds.eq(1).find("input:text");
       // var $oQuantity  = $tds.eq(4).find("input");
		//var $oThisPrice = $tds.eq(5).find("span");
		var $oQuantity    = $tds.eq(6).find("input");
		var $oPrice       = $tds.eq(7).find("input");
		var $oTotali      = $tds.eq(8).find("input");
		var $oTotals      = $tds.eq(8).find("span");
		var $oLastPrice   = $tds.eq(10).find("input");
		//var $oAmount2   = $tds.eq(6).find("span");
		//var $oAmountd   = $tds.eq(6).find("input:last-child");//人工成本
		
		//var materialId = $oMaterial.val();
		var fLastPrice = currencyToFloat($oLastPrice.val());
		var fPrice = currencyToFloat($oPrice.val());		
		var fQuantity = currencyToFloat($oQuantity.val());	
		
		var fTotalNew = currencyToFloat(fPrice * fQuantity);
		//var fAmountd  = fnLaborCost(materialId,fTotalNew);//人工成本

		var vPrice = float4ToCurrency(fPrice);	
		var vQuantity = floatToCurrency(fQuantity);
		var vTotalNew = floatToCurrency(fTotalNew);
				
		//详情列表显示新的价格
		//$oThisPrice.val(vPrice);					
		$oQuantity.val(vQuantity);	
		$oPrice.val(vPrice);	
		$oTotals.html(vTotalNew);
		$oTotali.val(vTotalNew);
		
		if(fPrice > fLastPrice){
			$oPrice.removeClass('decline').addClass('rise');
		}else if(fPrice < fLastPrice){
			$oPrice.removeClass('rise').addClass('decline');			
		}

		//alert("fPrice:"+fPrice+"::fLastPrice:"+fLastPrice)
		
	});
	
	t3.on('order.dt search.dt draw.dt', function() {
		t3.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			var num   = i + 1;
			//var checkBox = "<input type=checkbox name='numCheck' id='numCheck' value='" + num + "' />";
			cell.innerHTML = num;
		});
	}).draw();

};//ajax()



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
