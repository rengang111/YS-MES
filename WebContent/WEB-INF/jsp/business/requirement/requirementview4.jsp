<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购方案--采购合同</title>
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
	
	function initEvent(){
		
		$('#contractTable').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	
	        	$('#contractTable').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            
	            var d = $('#contractTable').DataTable().row(this).data();
				//alert(d["bid_id"]);
				
				$('#contractDetail').DataTable().destroy();
				//$('#set_lines').DataTable().ajax.reload();
				//ajax_factory_bid_set_lines(d["bid_id"]);
				//var d = $('#contractTable').DataTable().row(this).data();
				//alert(d["contractId"])
				contractDetailView(d["contractId"]);
					            
	        }			
		});
	}
	
	$(document).ready(function() {		
		
		
		orderBomView();//订单BOM
		
		ZZmaterialView();//二级BOM
		
		requirementAjax();//采购方案

		contractTableView();//采购合同
		
		initEvent();//合同明细联动
		
		$( "#tabs" ).tabs();
		$( "#tabs" ).tabs( "option", "active", 3 );//设置默认显示内容
		
		$(".goBack").click(
				function() {
					var YSId = '${order.YSId}';
					var materialId = '${order.materialId}';
					var url = '${ctx}/business/bom?methodtype=orderDetail&YSId=' + YSId+'&materialId='+materialId;
					location.href = url;		
				});
		
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

			$('.DTFC_Cloned').find('tr').removeClass('selected');
			$('.DTFC_Cloned').find('tr').eq(rowIndex+2).addClass('selected');
		});
			
		
		$("#doReset").click(function() {
			var order = '${order.quantity}';
			order = order.replace(/,/g, "");
			var materialId='${order.materialId}';
			var YSId = '${order.YSId}';
			var url = "${ctx}/business/requirement?methodtype=resetRequirement&YSId="+YSId+"&materialId="+materialId+"&order="+order;
			location.href = url;		
			
		});
		

		$("#doEditPlan").click(function() {
			var YSId = '${order.YSId}';
			var bomId=$('#bomId').val();
			var url = "${ctx}/business/requirement?methodtype=editRequirement&YSId="+YSId+"&bomId="+bomId;
			location.href = url;		
		});
		
		$("#doContract").click(function() {
			var YSId = '${order.YSId}';
			var materialId='${order.materialId}';
			var bomId=$('#bomId').val();
			var url = "${ctx}/business/requirement?methodtype=creatPurchaseOrder&YSId="+YSId+"&materialId="+materialId+"&bomId="+bomId;
			location.href = url;
			
		});
		
		$("#doReset").click(function() {
			var order = '${order.quantity}';
			order = order.replace(/,/g, "");
			var materialId='${order.materialId}';
			var YSId = '${order.YSId}';
			var url = "${ctx}/business/requirement?methodtype=resetRequirement&YSId="+YSId+"&materialId="+materialId+"&order="+order;
			location.href = url;		
			
		});
		
		$(".tabs3").click( function() {
			
			//$('#example').DataTable().ajax.reload(null,true);
			//$(".DTFC_Cloned").attr('width','90% !important');
			//$(".DTFC_Cloned").removeAttr('style');
			
			var scrollHeight = $(window).height() - 250;
			$('#example').DataTable({
		        "destroy":true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
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
		        "fixedColumns":   { leftColumns: 3 },
		        "sScrollXInner": "110%",
		        "bScrollCollapse": true,
				"dom" : '<"clear">rt',
		        "ordering"  : false,
			});
			
		});
	
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
		<input type="hidden" id="bomId" value="${bomId }">
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:150px;">${order.YSId}
					<form:hidden path="orderBom.ysid"  value="${order.YSId}" /></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;">${order.materialId}
					<form:hidden path="orderBom.materialid"  value="${order.materialId}" /></td>
				
					<td class="label" style="width:100px;"><label>产品名称：</label></td>				
					<td>${order.materialName}</td>
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
				
		<fieldset class="action" style="text-align: right;margin-top: -5px;">
			<button type="button" id="doEditPlan" class="DTTT_button">修改采购方案</button>
			<button type="button" id="doReset" class="DTTT_button">重置订单BOM</button>
			<button type="button" id="goBack" class="DTTT_button goBack">返回订单详情</button>
		</fieldset>	
		
		<div id="tabs" style="padding: 0px;white-space: nowrap;">
			<ul>
				<li><a href="#tabs-1" class="tabs1">订单BOM</a></li>
				<li><a href="#tabs-2" class="tabs2">二级BOM</a></li>
				<li><a href="#tabs-3" class="tabs3">采购方案</a></li>
				<li><a href="#tabs-4" class="tabs4">采购合同</a></li>
			</ul>
	
			<div id="tabs-1" style="padding: 5px;">
	
				<table id="orderBom" class="display" style="width:98%">
					<thead>				
						<tr>
							<th width="1px">No</th>
							<th class="dt-center" style="width:120px">物料编码</th>
							<th class="dt-center" >物料名称</th>
							<th class="dt-center" style="width:60px">用量</th>
							<th class="dt-center" style="width:30px">单位</th>
							<th class="dt-center" style="width:60px">供应商</th>
							<th class="dt-center" style="width:60px">订单数量</th>
							<th class="dt-center" style="width:80px">总量</th>
							<th class="dt-center" style="width:60px">当前库存</th>
							<th class="dt-center" style="width:80px">建议采购量</th>
							<th class="dt-center" style="width:60px">单价</th>
							<th class="dt-center" style="width:60px">总价</th>
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
							<th class="dt-center" width="80px !important">单位</th>
							<th class="dt-center" width="100px">总量</th>
							<th class="dt-center" width="100px">单价</th>
							<th class="dt-center" width="100px !important">总价</th>
						</tr>
					</thead>
							
				</table>
			</div>
			
		<div id="tabs-3" style="padding: 5px;">
			<table id="example" class="datatable display" >
				<thead>				
					<tr>
						<th style="width:30px">No</th>
						<th class="dt-center" style="width:120px">物料编码</th>
						<th class="dt-center" >物料名称</th>
						<th class="dt-center" style="width:30px">单位</th>
						<th class="dt-center" width="80px">订单需求量</th>
						<th class="dt-center" width="80px">当前库存</th>
						<th class="dt-center" width="80px">建议采购量</th>
						<th class="dt-center" width="100px">供应商</th>
						<th class="dt-center" style="width:100px">本次单价</th>
						<th class="dt-center" style="width:100px">&nbsp;&nbsp;总价&nbsp;&nbsp;</th>
						<th class="dt-center" style="width:100px">最新单价</th>	
						<th class="dt-center" style="width:100px">历史最低</th>	
					</tr>
				</thead>
			</table>
	
		</div>	
		<div id="tabs-4" style="padding: 5px;">
		
			<table id="contractTable" class="display" style="width:98%">
				<thead>				
					<tr>
						<th width="1px">No</th>
						<th class="dt-center" style="width:120px">合同编号</th>
						<th class="dt-center" style="width:100px">供应商简称</th>
						<th class="dt-center" >供应商名称</th>
						<th class="dt-center" style="width:80px">下单日期</th>
						<th class="dt-center" width="80px">交货日期</th>
						<th class="dt-center" width="100px">合同金额</th>
						<th class="dt-center" width="30px"></th>
					</tr>
				</thead>			
			</table>
			<br/>
			<fieldset style="min-height: 300px;">
			<legend>合同明细</legend>
			<div class="list">
			<table id="contractDetail" class="display">	
				<thead>
				<tr>
					<th style="width:30px">No</th>
					<th style="width:150px">ERP编码</th>
					<th>物料名称</th>
					<th style="width:80px">数量</th>
					<th style="width:60px">单价</th>
					<th style="width:50px">单位</th>
					<th style="width:80px">金额</th>
				</tr>
				</thead>
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tfoot>
			</table>
			</div>
			</fieldset>
		</div>
		</div>	
		<div style="clear: both"></div>		
	</form:form>
</div>
</div>

<script  type="text/javascript">
function orderBomView() {

	var YSId='${order.YSId}';
	var table = $('#orderBom').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#orderBom').DataTable({
		"paging": false,
		"processing" : true,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/requirement?methodtype=getOrderBom&YSId="+YSId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);;
					foucsInit();//input获取焦点初始化处理
					
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
			{"data": "supplierId"},
			{"data": null,"className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
			{"data": "availabelToPromise","className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
			{"data": "price","className" : 'td-right'},
			{"data": null,"className" : 'td-right'},	
		 ],
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];				    			
    			name = jQuery.fixedWidth(name,40);				    			
    			return name;
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var materialId = row["materialId"];
    			var subBomId = row["subBomId"];
    			var rownum = row["rownum"]-1;
    			rtn = materialId;
    			//rtn= "<a href=\"###\" onClick=\"doEditMaterial('#orderBom','" + row["materialRecordId"] +"','"+ row["parentId"] + "')\">"+materialId+"</a>";
    			// rtn=rtn+ "<input type=\"hidden\" id=\"bomDetailList"+rownum+".materialid\" name=\"bomDetailList["+rownum+"].materialid\" value=\""+materialId+"\">";
    			return rtn;
    		}},
    		{"targets":6,"render":function(data, type, row){
    			
    			var quantity =  '${order.quantity}' ;
    			return quantity;
    		}},
    		{"targets":7,"render":function(data, type, row){
    			var order = currencyToFloat('${order.quantity}' );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var total = floatToCurrency( order * quantity );			    			
    			return total;
    		}},
    		{"targets":11,"render":function(data, type, row){
    			var price = currencyToFloat(row["price"] );
    			var order = currencyToFloat('${order.quantity}' );
    			var quantity = currencyToFloat( row["quantity"] );				    			
    			var total = floatToCurrency( order * quantity * price);		    			
    			return total;
    		}},
	 		{
				"visible" : false,
				"targets" : [5,8,9]
			}
          
        ] 
	});
	
	t2.on('change', 'tr td:nth-child(4)',function() {
		
		/*总量计算*/
		var fOrderQuanty =  currencyToFloat('${order.quantity}') ;//订单数量

        var $tds = $(this).parent().find("td");

        var $oMaterail   = $tds.eq(1).find("input:hidden");//物料使用量
        var $oQuantityt  = $tds.eq(3).find("input:text");//物料使用量
        var $oQuantityh  = $tds.eq(3).find("input:hidden");//在基础BOM中的使用量
		var $oAmount1   = $tds.eq(7);
        
		var materialId = $oMaterail.val();
		var fQuantityt = currencyToFloat($oQuantityt.val());
		var fQuantityh = currencyToFloat($oQuantityh.val());
		
		//alert("oder:"+fOrderQuanty+"fQuantityt:"+fQuantityt)
		var fTotalNew = currencyToFloat(fOrderQuanty * fQuantityt);

				
		//详情列表显示新的价格	
		$oQuantityt.val(float5ToCurrency(fQuantityt));
		$oAmount1.text(floatToCurrency(fTotalNew));	
		
		
		//更新订单BOM的使用量
		if(fQuantityt != fQuantityh){
			
			//alert("new:"+fQuantityt+"old:"+fQuantityh)
			var bomId  = $('#bomId').val();
			
			var url = "${ctx}/business/requirement?methodtype=updateOrderBomQuantity";
			url = url + "&materialId="+ materialId + "&bomId="+bomId;
			url = url + "&quantity="+fQuantityt
	
			$.ajax({
				type : "post",
				url : url,
				//async : false,
				//data : null,
				dataType : "text",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {			
	
					$().toastmessage('showNoticeToast', "保存成功。");
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
					//alert(textStatus)
				}
			});	
			
			//hidden重新赋值
			$oQuantityh.val(float5ToCurrency(fQuantityt));
		}
		
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

function productSemiUsed(semiMaterialId) {

	var YSId='${order.YSId}';
	var materialId = '${order.materialId}';
	var url = "${ctx}/business/requirement?methodtype=productSemiUsed";
	var url = url + "&materialId="+materialId+ "&semiMaterialId="+semiMaterialId+"&YSId="+YSId;

	location.href = url;
}

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
			//$('#example').DataTable().ajax.reload();
			/*
			
					function ( json ) {
					    //这里的json返回的是服务器的数据
					   // alert(2222);
						$(".DTFC_Cloned").css('width','380px');
						$('#example thead tr').each (function (){
							$(this).find("th").eq(2).css('width','100px ');
						});
					} 
			*/
			layer.close(index);
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
		"processing" : true,
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
	 			//return  "<a href=\"###\" onClick=\"productSemiUsed('" + row["materialId"] + "')\">使用库存</a>";
	    		return ""
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

<script  type="text/javascript">
function ZZmaterialView() {

	var bomId=$("#bomId").val();
	var table = $('#ZZmaterial').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#ZZmaterial').DataTable({
		"paging": false,
		"processing" : true,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		"sAjaxSource" : "${ctx}/business/requirement?methodtype=getzzMaterial&bomId="+bomId,				
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
			{"data": null,},
			{"data": null,},
			{"data": "unitName","className" : 'td-center'},
			{"data": null,"defaultContent" : '0',"className" : 'td-right'},
			{"data": "lastPrice","defaultContent" : '0',"className" : 'td-right'},
			{"data": null,"defaultContent" : '0',"className" : 'td-right'},
		 ],
		 
		"columnDefs":[
      		{"targets":1,"render":function(data, type, row){
      			var name = row["rawMaterialId"];
    			if(name == null){
    				name = "<a href=\"###\" onClick=\"doEditMaterial2('" + row["materialRecordId"] +"','"+ row["materialParentId"] + "')\">"+row["materialId"]+"</a>";
    			}			    			
    			return name;
    		}},
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];
    			if(name == null){
    				name = '******';
    			}else{
        			name = jQuery.fixedWidth(name,40);	
    			}			    			
    			return name;
    		}},
    		{"targets":4,"render":function(data, type, row){
    		
    			var order="${order.quantity}";
    			var requirement = row["requirement"];
    			order=currencyToFloat(order);
    			requirement=currencyToFloat(requirement);
    			
    			var unittext=row["zzUnitName"];
    			var vrawunit=row["unitName"]
    			
    			var farwunit = '1';//初始值
    			//原材料的购买单位
    			for(var i=0;i<unitAaary.length;i++){
    				var val = unitAaary[i][0];//取得计算单位:100,1000...
    				var key = unitAaary[i][1];//取得显示单位:克,吨...
    				if(vrawunit == key){
    					farwunit = val;
    					//alert('原材料的购买单位'+farwunit)
    					break;
    				}
    			}
    			
    			//自制品的用量单位
    			var fchgunit = '1';//初始值
    			for(var i=0;i<unitAaary.length;i++){
    				var val = unitAaary[i][0];//取得计算单位:100,1000...
    				var key = unitAaary[i][1];//取得显示单位:克,吨...
    				if(unittext == key){
    					fchgunit = val;//只有在需要换算的时候,才设置换算单位
    					//alert('自制品的用量单位'+fchgunit)
    					break;
    				}
    			}	
    			
    			var total = float5ToCurrency( order * requirement * farwunit / fchgunit);
		
    			return total;
    		}},
    		{"targets":6,"render":function(data, type, row){
    			//总价
    			var price=row["lastPrice"];
    			var order="${order.quantity}";
    			var requirement = row["requirement"];
    			var promise = row["availabelToPromise"];
    			price=currencyToFloat(price);
    			order=currencyToFloat(order);
    			requirement=currencyToFloat(requirement);
    			promise=currencyToFloat(promise);
    			
    			var unittext=row["zzUnitName"];
    			var vrawunit=row["unitName"]
    			
    			var farwunit = '1';//初始值
    			//原材料的购买单位
    			for(var i=0;i<unitAaary.length;i++){
    				var val = unitAaary[i][0];//取得计算单位:100,1000...
    				var key = unitAaary[i][1];//取得显示单位:克,吨...
    				if(vrawunit == key){
    					farwunit = val;
    					//alert('原材料的购买单位'+farwunit)
    					break;
    				}
    			}
    			
    			//自制品的用量单位
    			var fchgunit = '1';//初始值
    			for(var i=0;i<unitAaary.length;i++){
    				var val = unitAaary[i][0];//取得计算单位:100,1000...
    				var key = unitAaary[i][1];//取得显示单位:克,吨...
    				if(unittext == key){
    					fchgunit = val;//只有在需要换算的时候,才设置换算单位
    					//alert('自制品的用量单位'+fchgunit)
    					break;
    				}
    			}	
    			var total = order * requirement * farwunit / fchgunit;
    			var totalPrice = floatToCurrency( price * total );

    			return totalPrice;
    		}}
          
        ] ,
        
	     "aaSorting": [[ 1, "asc" ]]
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

<script type="text/javascript">

function autocomplete(){
	
	
	//供应商选择
	$(".supplierId").autocomplete({
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
		
		select : function(event, ui) {

			var $td = $(this).parent().parent().find('td');

			//var $oMaterial  = $td.eq(2).find("input:text");
			//var $oSupplier  = $td.eq(4).find("input:hidden");
			var $oQuantity    = $td.eq(6).find("input");
			//var $oThisPrice = $td.eq(6).find("span");
			//var $oThisPriceh= $td.eq(6).find("input:hidden");
			var $oPriceh      = $td.eq(8).find("input");
			var $oPrices      = $td.eq(8).find("span");
			var $oAmount      = $td.eq(9).find("span");//总价
			
			//计算
			//var materialId = $oMaterial.val();
			var fPrice = currencyToFloat(ui.item.price);//最新单价
			var fQuantity = currencyToFloat($oQuantity.val());//数量
			var fTotalNew = currencyToFloat(fPrice * fQuantity);//合计
	
			//显示到页面	
			var vPrice = float5ToCurrency(fPrice);
			var vTotalNew = floatToCurrency(fTotalNew);

			$oPrices.text(vPrice);
			$oPriceh.val(vPrice);
			$oAmount.html(vTotalNew);
			//$oCurrPrice.html(vPrice);
			//$oAmountd.val(fAmountd);

			
		},
		
	});//供应商选择
}

</script>


<script  type="text/javascript">

function requirementAjax() {

	var scrollHeight = $(window).height() - 250;
	var bomId = "${bomId}";
	var YSId = '${order.YSId}';

	var table = $('#example').dataTable();
	if(table) {
		table.fnDestroy();
	}	
	var t3 = $('#example').DataTable({
		//"destroy":true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
		"bAutoWidth": false,
		"paging": false,
		"processing" : true,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"retrieve" : true,
		"async" : false,
        "sScrollY": scrollHeight,
        "sScrollX": true,
        "fixedColumns":   { leftColumns: 3 },
        "sScrollXInner": "130%",
        "bScrollCollapse": true,
		"dom" : '<"clear">rt',
        "ordering"  : false,
		"sAjaxSource" : "${ctx}/business/requirement?methodtype=purchasePlanView&bomId="+bomId+"&YSId="+YSId,
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){
					fnCallback(data);
					//autocomplete();//
					//foucsInit();//input获取焦点初始化处理
					// $(".DTFC_Cloned").css('width','100%');
					
					
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
		"fnHeaderCallback": function( nHead, aData, iStart, iEnd, aiDisplay ) {
		      //nHead.getElementsByTagName('th')[0].innerHTML = "Displaying "+(iEnd-iStart)+" records";
		     // nHead.getElementsByTagName('th')[0].css('width','30px');
		 },
       	"language": {
       		"url":"${ctx}/plugins/datatables/chinese.json"
       	},
		"columns": [
			{"data": null,"className" : 'td-center'},
			{"data": "materialId" },
			{"data": "materialName"},
			{"data": "unitName","className" : 'td-center'},
			{"data": "orderQuantity","className" : 'td-right'},
			{"data": "availabelToPromise","className" : 'td-right'},
			{"data": "purchaseQuantity","className" : 'td-right'},
			{"data": "supplierId"},
			{"data": "price","className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
			{"data": null,"className" : 'td-right'},
		 ],
		
		"columnDefs":[
    		{"targets":2,"render":function(data, type, row){
    			
    			var name = row["materialName"];				    			
    			name = jQuery.fixedWidth(name,25);				    			
    			return name;
    		}},
    		{"targets":1,"render":function(data, type, row){
    			var rtn="";
    			var rownum = row["rownum"]-1;
    			var materialId = row["materialId"];
    			//rtn+= materialId;
    			rtn+= "<a href=\"###\" onClick=\"doEditMaterial('" + row["materialRecordId"] +"','"+ row["materialParentId"] + "')\">"+materialId+"</a>";
    			return rtn;
    		}},
    		{"targets":4,"render":function(data, type, row){    			
				var rtn = "";
    			var rownum = row["rownum"]-1;
    			var quantity =  floatToCurrency( row["orderQuantity"] );
    			rtn+= quantity;
    			//rtn+= "<input type=\"hidden\" id=\"purchaseList"+rownum+".orderquantity\" name=\"purchaseList["+rownum+"].orderquantity\" value=\""+quantity+"\">";
    			return rtn;
    		}},
    		{"targets":6,"render":function(data, type, row){
				var rtn = "";
    			var rownum = row["rownum"]-1;
    			var quantity =  floatToCurrency( row["quantity"] );
    			rtn+= quantity;
    			//rtn+= "<input type=\"text\" id=\"purchaseList"+rownum+".quantity\" name=\"purchaseList["+rownum+"].quantity\" class=\"num\" style=\"width:80px\" value=\""+quantity+"\">";
    			return rtn;
    		}},
    		{"targets":9,"render":function(data, type, row){
    			//总价
				var purchaseQuantity = currencyToFloat(row["quantity"]);	
				var price =currencyToFloat(row["price"]);		
				var total = floatToCurrency( price * purchaseQuantity );
    			return "<span style='font-weight: bold;'>"+total+"</span>";
    		}},
    		{"targets":10,"render":function(data, type, row){
    			//最新价
    			var rtn ="";
    			var rownum = row["rownum"]-1;
				var price = row["lastPrice"];
				var supplierId = row["lastSupplierId"];				
				rtn+=  float4ToCurrency(price)+'／'+stringPadAfter(supplierId,12);
				//rtn+= "<input type=\"hidden\" id=\"purchaseList"+rownum+".oldsupplierid\" name=\"purchaseList["+rownum+"].oldsupplierid\"  value=\""+supplierId+"\">";
				//rtn+= "<input type=\"hidden\" id=\"purchaseList"+rownum+".oldprice\" name=\"purchaseList["+rownum+"].oldprice\"  value=\""+price+"\">";
    			return rtn;
    		}},
    		{"targets":11,"render":function(data, type, row){
    			//最低价
    			var rtn ="";
    			var rownum = row["rownum"]-1;
    			var materialId = row["materialId"];
				var minPrice = row["minPrice"];
				var minSupplierId = row["minSupplierId"];				
				rtn+=  float4ToCurrency(minPrice)+'／'+stringPadAfter(minSupplierId,12);
				//rtn+= "<input type=\"hidden\" id=\"purchaseList"+rownum+".materialid\" name=\"purchaseList["+rownum+"].materialid\"  value=\""+materialId+"\">";
    			return rtn;
    		}}          
        ]
		
	});

	
	t3.on('blur', 'tr td:nth-child(7),tr td:nth-child(8),tr td:nth-child(9)',function() {
		
		var currValue = $(this).find("input:text").val().trim();

        $(this).find("input:text").removeClass('bgwhite');
        
        if(currValue =="" ){
        	
        	 $(this).find("input:text").addClass('error');
        }else{
        	 $(this).find("input:text").removeClass('error').addClass('bgnone');
        }
		
	});
			

	t3.on('change', 'tr td:nth-child(7),tr td:nth-child(8),tr td:nth-child(9)',function() {
		
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
		var $oPrice       = $tds.eq(8).find("input");
		//var $oTotali      = $tds.eq(9).find("input");
		var $oTotals      = $tds.eq(9).find("span");
		//var $oLastPrice   = $tds.eq(10).find("input");
		//var $oAmount2   = $tds.eq(6).find("span");
		//var $oAmountd   = $tds.eq(6).find("input:last-child");//人工成本
		
		//var materialId = $oMaterial.val();
		//var fLastPrice = currencyToFloat($oLastPrice.val());
		var fPrice = currencyToFloat($oPrice.val());		
		var fQuantity = currencyToFloat($oQuantity.val());	
		6
		var fTotalNew = currencyToFloat(fPrice * fQuantity);
		//var fAmountd  = fnLaborCost(materialId,fTotalNew);//人工成本	

		var vPrice = floatToCurrency(fPrice);	
		var vQuantity = floatToCurrency(fQuantity);
		var vTotalNew = floatToCurrency(fTotalNew);
				
		//详情列表显示新的价格
		//$oThisPrice.val(vPrice);					
		$oQuantity.val(vQuantity);	
		$oPrice.val(vPrice);	
		$oTotals.html(vTotalNew);
	//	$oTotali.val(vTotalNew);
		
		/*
		if(fPrice > fLastPrice){
			$oPrice.removeClass('decline').addClass('rise');
		}else if(fPrice < fLastPrice){
			$oPrice.removeClass('rise').addClass('decline');			
		}
		*/
		
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


function contractTableView() {

	var YSId='${order.YSId}';
	var table = $('#contractTable').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#contractTable').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
        "sScrollY": 250,
		"retrieve" : false,
		"async" : false,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/contract?methodtype=getContract&YSId="+YSId,				
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : null,
				success: function(data){

					var record = data["recordsTotal"];
					//alert(record);
					if(record > 0)
					//	$( "#tabs" ).tabs( "option", "active", 3 );//设置默认显示内容
					
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
			{"data": "contractId"},
			{"data": "supplierId"},
			{"data": "supplierName"},
			{"data": "purchaseDate","className" : 'td-center'},
			{"data": "deliveryDate","className" : 'td-center'},
			{"data": "total","className" : 'td-right'},
			{"data": null,"className" : 'td-right'}
        ] ,
		"columnDefs":[
    		{"targets":7,"render":function(data, type, row){
    			var contractId = row["contractId"];
     			rtn= "<a href=\"###\" onClick=\"showContract('" + row["supplierId"] +"','"+ row["YSId"] + "')\">"+"打印"+"</a>";
    			return rtn;
    		}},
      		{"targets":6,"render":function(data, type, row){
      			var total = currencyToFloat( row["total"] );			    			
      			totalPrice = floatToCurrency(total);			    			
      			return totalPrice;
      		}}
            
          ] 
       	
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


function contractDetailView(contractId) {

	//var YSId='${order.YSId}';
	var table = $('#contractDetail').dataTable();
	if(table) {
		table.fnDestroy();
	}
	var t2 = $('#contractDetail').DataTable({
		"paging": false,
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"searching" : false,
		"pagingType" : "full_numbers",
		"retrieve" : false,
		"async" : false,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/contract?methodtype=getContractDetail&contractId="+contractId,				
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
			{"data": "price","className" : 'td-right'},
			{"data": "unit","className" : 'td-center'},
			{"data": "totalPrice","className" : 'td-right'}
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

	
} // ajax()供应商信息

function showContract(supplierId,YSId) {
	//accessFlg:1 标识新窗口打开
	var url = '${ctx}/business/requirement?methodtype=contractPrint';
	url = url + '&YSId=' + YSId+'&supplierId='+supplierId;
	//alert(YSId)
	//"sAjaxSource" : "${ctx}/business/contract?methodtype=getContractDetail&supplierId="+supplierId,	
	
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

</script>
</body>
	
</html>
