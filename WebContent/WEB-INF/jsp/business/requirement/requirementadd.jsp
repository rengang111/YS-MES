<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>订单采购方案--物料需求表做成(订单BOM)</title>
<%@ include file="../../common/common2.jsp"%>
 <style>th, td { white-space: nowrap; }</style>
  	
<script type="text/javascript">

	var counter = 0;
	var moduleNum = 0;
	
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
	
	$.fn.dataTable.TableTools.buttons.add_row = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {

					for (var i=0;i<1;i++){
								
						var orderQuantity = '${order.quantity}';
						var trhtml = "";
						var rowIndex = counter + 1;
						var hidden =	'<input type="hidden" name="bomDetailList['+counter+'].subbomid" id="bomDetailList'+counter+'.subbomid" value=""/>'+
									 	'<input type="hidden" name="bomDetailList['+counter+'].subbomserial" id="bomDetailList'+counter+'.subbomserial" value=""/>'
						/*
							var rowNode = $('#example')
							.DataTable()
							.row
							.add(
							  [
								'<td><input type="text"   name="bomDetailList['+counter+'].subbomno"   id="bomDetailList'+counter+'.subbomno" value="0" class="cash"  style="width:20px"/></td>',
								'<td>'+hidden+rowIndex+'<input type=checkbox name="numCheck" id="numCheck" value="" /></td>',
								'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
									'<input type="hidden" name="bomDetailList['+counter+'].materialid" id="bomDetailList'+counter+'.materialid" /></td>',
								'<td><span></span></td>',
								'<td><span></span></td>',
								'<td><span></span></td>',
								'<td>input type="hidden" name="bomDetailList['+counter+'].supplierid" id="bomDetailList'+counter+'.supplierid"  class="supplierid"/></td>',
								'<td><span></span></td>',
								'<td><input type="text"   name="bomDetailList['+counter+'].quantity"   id="bomDetailList'+counter+'.quantity"   class="cash"  style="width:70px"/></td>',
								'<td><span></span><input type="hidden"   name="bomDetailList['+counter+'].price"      id="bomDetailList'+counter+'.price" /></td>',
								'<td><span></span><input type="hidden"   name="bomDetailList['+counter+'].totalprice" id="bomDetailList'+counter+'.totalprice"/><input type="hidden" id="labor"></td>',
								
								]);
							*/		 	
							
						trhtml += '<tr>';
						trhtml +='<td class="td-center"><input type="text"   name="bomDetailList['+counter+'].subbomno"   id="bomDetailList'+counter+'.subbomno" value="0" class="cash"  style="width:20px"/></td>';
						trhtml +='<td class="td-center">'+rowIndex+'<input type=checkbox name="numCheck" id="numCheck" value="" /></td>',
						trhtml +='<td><input type="text" name="bomDetailList['+counter+'].materialid" id="bomDetailList'+counter+'.materialid" class="attributeList1"/></td>';
						trhtml +='<td><span></span></td>';
						trhtml +='<td class="td-center"><span></span></td>';
						trhtml +='<td class="td-right"><input type="text" name="bomDetailList['+counter+'].quantity" id="bomDetailList'+counter+'.quantity" class="num" style="width:60px"/></td>';
						trhtml +='<td><input type="text" name="bomDetailList['+counter+'].supplierid" id="bomDetailList'+counter+'.supplierid"  class="supplierid" style="width:100px"/></td>';
						//trhtml +='<td class="td-right"><span>'+orderQuantity+'</span></td>';
						//trhtml +='<td class="td-right"><input type="hidden"   name="bomDetailList['+counter+'].quantity"   id="bomDetailList'+counter+'.quantity"   class="cash"  style="width:70px"/></td>';
						trhtml +='<td class="td-right"><span></span><input type="hidden"   name="bomDetailList['+counter+'].price"      id="bomDetailList'+counter+'.price" /></td>',
						trhtml +='<td class="td-right"><span></span><input type="hidden"   name="bomDetailList['+counter+'].totalprice" id="bomDetailList'+counter+'.totalprice"/><input type="hidden" id="labor"></td>';
						trhtml +='</tr>';
						
						$("#example tbody tr:last").after(trhtml);
						

						counter ++;			
					}				
					
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
				//costAcount();				
			}
				
		}
	});

	function fnselectall() { 
		if($("#selectall").prop("checked")){
			$("input[name='numCheck']").each(function() {
				$(this).prop("checked", true);
			});
				
		}else{
			$("input[name='numCheck']").each(function() {
				if($(this).prop("checked")){
					$(this).removeAttr("checked");
				}
			});
		}
	};
	
	function fnreverse() { 
		$("input[name='numCheck']").each(function () {  
	        $(this).prop("checked", !$(this).prop("checked"));  
	    });
	};
	
	$(document).ready(function() {		

		baseBomView();//分离数量
		
		productStock();//半成品库存信息
		
		autocomplete();		
		
		$(".goBack").click(
				function() {
			var YSId = '${order.YSId}';
			var materialId = '${order.materialId}';
			var url = '${ctx}/business/bom?methodtype=orderDetail&YSId=' + YSId+'&materialId='+materialId;
	
			location.href = url;		
		});
		
		$("#createOrderBom").click(
				function() {

			var materialId='${order.materialId}';
			var YSId ="${order.YSId}";
			var quantity ="${order.quantity}";
			$('#attrForm').attr("action",
					"${ctx}/business/requirement?methodtype=createOrderBom&YSId="+YSId+"&materialId="+materialId+"&quantity="+quantity);
			$('#attrForm').submit();
		});
			
		
		
		/*
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
		*/

		$( "#tabs" ).tabs();

		foucsInit();//input获取焦点初始化处理
		$(".DTTT_container").css('float','left');
	
	});


	function baseBomView() {

		var scrollHeight = $(window).height() - 255;
		var materialId='${order.materialId}';
		var table = $('#example').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var t = $('#example').DataTable({
			"processing" : false,
			"retrieve"   : false,
			"stateSave"  : false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
	        "sScrollY": scrollHeight,
			"dom" 		: 'T<"clear">rt',
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
						counter = data["recordsTotal"];
						
						autocomplete();//
						foucsInit();//input获取焦点初始化处理
						// $(".DTFC_Cloned").css('width','100%');
						
						
					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				})
			},
			"tableTools" : {
				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
				"aButtons" : [ {
					"sExtends" : "add_row",
					"sButtonText" : "追加行"
				},
				{
					"sExtends" : "reset",
					"sButtonText" : "删除行"
				}],
			},			
			
	       	"language": {
	       		"url":"${ctx}/plugins/datatables/chinese.json"
	       	},
			"columns": [
				{"data": null,"className" : 'td-center', "defaultContent" : ''},
				{"data": null,"className" : 'td-center', "defaultContent" : ''},
				{"data": "materialId", "defaultContent" : ''},
				{"data": "materialName", "defaultContent" : ''},
				{"data": "unit","className" : 'td-center', "defaultContent" : ''},
				{"data": "quantity","className" : 'td-right', "defaultContent" : ''},
				{"data": "supplierId", "defaultContent" : ''},
				{"data": "price","className" : 'td-right', "defaultContent" : ''},
				{"data": null,"className" : 'td-right', "defaultContent" : ''},
			 ],
		
			"columnDefs":[
				{"targets":0,"render":function(data, type, row){
					
	    			var subbomno = row["subbomno"];
	    			var rownum = row["rownum"]-1;
	     			var rtn= "<input type=\"text\" id=\"bomDetailList"+rownum+".subbomno\" name=\"bomDetailList["+rownum+"].subbomno\"  class=\"cash\" style=\"width:20px\" value=\""+subbomno+"\">";
	    			return rtn;
				}},
	    		{"targets":1,"render":function(data, type, row){
	    			var rtn="";
	    			var materialId = row["materialId"];
	    			var subBomId = row["subBomId"];
	    			var rownum = row["rownum"]-1;
	    			var rownum2 = row["rownum"]
					rtn+= "<span id='index"+rownum+"'>"+rownum2+"</span>";
	    			rtn=rtn+ '<input type="checkbox" id="numCheck" name="numCheck" value="">';
	    			return rtn;
	    		}},
	    		{"targets":2,"render":function(data, type, row){
	    			 //物料编号
	    			//alert("type:"+type+":data:"+data)
	    			var rtn="";
	    			var materialId = row["materialId"];
	    			var subBomId = row["subBomId"];
	    			var rownum = row["rownum"]-1;	    			
	    			//rtn=rtn+ '<input type="text" name="attributeList1" class="attributeList1" value="'+materialId+'">';
	    			rtn=rtn+ "<input type=\"text\" id=\"bomDetailList"+rownum+".materialid\" class=\"attributeList1\"  name=\"bomDetailList["+rownum+"].materialid\" value=\""+materialId+"\">";
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = row["materialName"];			    			
	    			name = jQuery.fixedWidth(name,40);
	    			var rownum = row["rownum"];
	    			var rtn = "<span>";
	    			rtn += name;
	    			rtn += "</span>";
	    			return rtn;
	    		}},
	    		{"targets":5,"render":function(data, type, row){
	    			var rtn="";
	    			var quantity = row["quantity"];
	    			var rownum = row["rownum"]-1;
	    			var sourceprice = row["price"];
	    			var supplierid = row["supplierId"];
	    			rtn=quantity+ "<input type=\"hidden\" id=\"bomDetailList"+rownum+".quantity\" name=\"bomDetailList["+rownum+"].quantity\" value=\""+quantity+"\">";
	    			return rtn;
	    		}},
	    		{"targets":6,"render":function(data, type, row){
	    			var rtn="";
	    			var supplierid = row["supplierId"];
	    			var rownum = row["rownum"]-1;
	    			rtn+= "<input type=\"text\" id=\"bomDetailList"+rownum+".supplierid\" name=\"bomDetailList["+rownum+"].supplierid\" style=\"width:100px\" class=\"supplierid\" value=\""+supplierid+"\">";
	        		return rtn;
	    		}},
	    		{"targets":7,"render":function(data, type, row){
	    			var rtn="";
	    			var price = row["price"];
	    			var rownum = row["rownum"]-1;
	    			rtn+= formatNumber(price);
	    			rtn+= "<input type=\"hidden\" id=\"bomDetailList"+rownum+".price\" name=\"bomDetailList["+rownum+"].price\"  value=\""+price+"\">";
	        		return rtn;
	    		}},
	    		{"targets":8,"render":function(data, type, row){
	    			var price = currencyToFloat(row["price"] );
	    			var quantity = currencyToFloat( row["quantity"] );				    			
	    			var total = floatToCurrency( price * quantity );			    			
	    			return total;
	    		}},
	    		
	    		{
					"visible" : false,
					"targets" : [ ]
				} 
	          
	        ]
	       
	     
		});
		
		t.on('blur', 'tr td:nth-child(3),tr td:nth-child(7)',function() {
			
			var currValue = $(this).find("input:text").val().trim();

	        $(this).find("input:text").removeClass('bgwhite');
	        
	        if(currValue =="" ){
	        	
	        	 $(this).find("input:text").addClass('error');
	        }else{
	        	 $(this).find("input:text").addClass('bgnone');
	        }
			
		});

		
		t.on('blur', 'tr td:nth-child(6)',function() {
			
	       $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
				

		t.on('change', 'tr td:nth-child(6)',function() {
			
			
	        var $tds = $(this).parent().find("td");
			
	       // var $oMaterial  = $tds.eq(2).find("input:text");
	        var $oQuantity  = $tds.eq(5).find("input");
			var $oThisPrice = $tds.eq(7).find("span");
			//var $oAmount1   = $tds.eq(7).find("input:hidden");
			var $oAmount2   = $tds.eq(8).find("span");
			//var $oAmountd   = $tds.eq(7).find("input:last-child");//人工成本
			
			//var materialId = $oMaterial.val();
			var fPrice = currencyToFloat($oThisPrice.text());		
			var fQuantity = currencyToFloat($oQuantity.val());	
			var fTotalNew = currencyToFloat(fPrice * fQuantity);
			//var fAmountd  = fnLaborCost(materialId,fTotalNew);//人工成本

			//var vPrice = formatNumber(fPrice);	
			var vQuantity = formatNumber(fQuantity);
			var vTotalNew = floatToCurrency(fTotalNew);
					
			//详情列表显示新的价格
			//$oThisPrice.val(vPrice);					
			$oQuantity.val(vQuantity);	
			//$oAmount1.val(vTotalNew);	
			$oAmount2.html(vTotalNew);
			//$oAmountd.val(fAmountd);

			//合计成本
			//costAcount();
			
		});
		
	}//ajax()
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="attrForm" method="POST"
		id="attrForm" name="attrForm"  autocomplete="off">		
		
		<input type="hidden" id="tmpMaterialId" />
		<form:hidden path="orderBom.bomid"  value="${bomId}" />
		
		<fieldset>
			<legend> 产品信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" style="width:100px;"><label>耀升编号：</label></td>					
					<td style="width:150px;">${order.YSId}
					<form:hidden path="orderBom.ysid"  value="${order.YSId}" /></td>
								
					<td class="label" style="width:100px;"><label>产品编号：</label></td>					
					<td style="width:150px;"><a href="###" onClick="doShowProduct()">${order.materialId}</a>
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
			
		<fieldset>
			<legend>半成品库存信息</legend>
			<div class="list" style="width: 50%;">
				<table id="productStock" class="display" style="width:98%">
					<thead>				
						<tr>
							<th width="1px">No</th>
							<th class="dt-center" style="width:100px">物料编码</th>
							<th class="dt-center" style="width:100px">当前库存</th>
							<th class="dt-center" style="width:80px">操作</th>
						</tr>
					</thead>			
				</table>
			</div>
		</fieldset>
				
		<fieldset class="action" style="text-align: right;margin-top: -50px;width: 30%;float: right;">
			<button type="button" id="createOrderBom" class="DTTT_button">生成订单BOM</button>
			<button type="button" id="goBack" class="DTTT_button goBack">返回订单详情</button>
		</fieldset>	
		
		<div id="tabs" style="padding: 0px;white-space: nowrap;">
		<ul>
			<li><a href="#tabs-1" class="tabs1">基础BOM</a></li>
		</ul>

		<div id="tabs-1" style="padding: 5px;">

		<table id="example" class="display" style="width:100%">
			<thead>				
				<tr>
					<th width="30px">模块<br>编号</th>
					<th width="50px">
						<input type="checkbox" name="selectall" id="selectall" onclick="fnselectall()"/><label for="selectall">全选</label><br>
						<input type="checkbox" name="reverse" id="reverse" onclick="fnreverse()" /><label for="reverse">反选</label></th>
					<th class="dt-center" style="width:120px">物料编码</th>
					<th class="dt-center" >物料名称</th>
					<th class="dt-center" style="width:30px">单位</th>
					<th class="dt-center" width="60px">用量</th>
					<th class="dt-center" style="width:80px">供应商</th>
					<th class="dt-center" width="60px">单价</th>
					<th class="dt-center" width="80px">总价</th>
				</tr>
			</thead>
			
		</table>
	 * 1、修改"模块编号",可以调整该模块的显示顺序,同一模块内的自动按照物料编码顺序排列；<br>
	 * 2、"添加单行"后,请修改该行的模块编号；
	</div>
</div>

		
<div style="clear: both"></div>		
</form:form>
</div>
</div>

<script  type="text/javascript">


</script>
<script type="text/javascript">
function productSemiUsed(semiMaterialId) {

	var YSId='${order.YSId}';
	var materialId = '${order.materialId}';
	var url = "${ctx}/business/requirement?methodtype=productSemiUsed";
	var url = url + "&materialId="+materialId+ "&semiMaterialId="+semiMaterialId+"&YSId="+YSId;

	$('#attrForm').attr("action", url);
	$('#attrForm').submit();
}
function doShowProduct() {
	var materialId = '${order.materialId}';
	//callProductView(materialId);
	
	var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '500px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
			$('#example').DataTable().ajax.reload(null,false);
		  	return false; 
		}    
	});
	
}

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
	 			return  "<a href=\"###\" onClick=\"productSemiUsed('" + row["materialId"] + "')\">使用库存</a>";
	    		
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
	$('.attributeList1').bind('input propertychange', function() {

		$(this).parent().parent().find('td').eq(3).find('div').text('');
	}); 
	
//物料选择
$(".attributeList1").autocomplete({
	minLength : 2,
	autoFocus : false,
	source : function(request, response) {
		//alert(888);
		$
		.ajax({
			type : "POST",
			url : "${ctx}/business/bom?methodtype=getMaterialPriceList",
			dataType : "json",
			data : {
				key : request.term
			},
			success : function(data) {
				//alert(777);
				response($
					.map(
						data.data,
						function(item) {

							return {
								label : item.viewList,
								value : item.materialId,
								id : item.materialId,
								name : item.materialName,
								materialId : item.materialId,
								supplierId : item.supplierId,
								  minPrice : item.minPrice,
								 lastPrice : item.lastPrice,
								     price : item.price,
								  unitName : item.dicName,
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
		
		//产品编号
		$(this).parent().find("input:hidden").val(ui.item.materialId);
		
		var $td = $(this).parent().parent().find('td');

		var $oMatName   = $td.eq(3).find("span");
		var $oUnit      = $td.eq(4).find("span");
		var $oQuantity  = $td.eq(5).find("input");
		var $oSupplier  = $td.eq(6).find("input");
		var $oThisPrice = $td.eq(7).find("span");
		var $oThisPriceh= $td.eq(7).find("input");
		//var $oAmount1   = $td.eq(8).find("input:hidden")
		var $oAmount2   = $td.eq(8).find("span");;
		//var $oAmountd   = $td.eq(7).find("input:last-child");//人工成本
		//var $oCurrPrice = $td.eq(7).find("span");
		//var $oSourPrice = $td.eq(8).find("span");
		//var $oMinPrice  = $td.eq(9).find("span");
	
		//开始计算
		var fPrice    = currencyToFloat(ui.item.price);//计算用单价
		var fQuantity = currencyToFloat($oQuantity.val());//计算用数量
		var fTotalNew = currencyToFloat(fPrice * fQuantity);//合计
		//var fAmountd  = fnLaborCost(ui.item.materialId,fTotalNew);//人工成本

		//显示到页面
		var vPrice    = formatNumber(fPrice);
		var vTotalNew = floatToCurrency(fTotalNew);
		//var vMinPrice = floatToCurrency(ui.item.minPrice);

		$oMatName.html(jQuery.fixedWidth(ui.item.name,40));
		$oUnit.text(ui.item.unitName);
		$oSupplier.val(ui.item.supplierId);
		$oThisPrice.text(vPrice);
		$oThisPriceh.val(vPrice);
		//$oAmount1.val(vTotalNew);
		$oAmount2.html(vTotalNew);
		//$oAmountd.val(fAmountd);//人工成本
		//$oCurrPrice.html(vPrice);
		//$oMinPrice.html(vMinPrice);
		//$oSourPrice.html('');//清空"上次BOM价格"

		//合计
		//costAcount();
		
		if (ui.item.supplierId !="")
			$oSupplier.removeClass('error');
		
		//光标位置
		$oQuantity.focus();			
	},

	
});//物料选择

//供应商选择
$(".supplierid").autocomplete({
	minLength : 0,
	autoFocus : false,
	width  :1000,
	
	search: function( event, ui ) {
		var $tds = $(this).parent().parent().find('td');
		var material = $tds.eq(2).find("input:text").val();
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
		var $oQuantity  = $td.eq(5).find("input");
		var $oSupplier  = $td.eq(6).find("input");
		var $oThisPrice = $td.eq(7).find("span");
		var $oThisPriceh= $td.eq(7).find("input");
		//var $oAmount1   = $td.eq(7).find("input:hidden");
		var $oAmount2   = $td.eq(8).find("span");
		//var $oAmountd   = $td.eq(7).find("input:last-child");//人工成本
		//var $oCurrPrice = $td.eq(7).find("span");
		
		//计算
		//var materialId = $oMaterial.val();
		var fPrice = currencyToFloat(ui.item.price);//计算用单价
		var fQuantity = currencyToFloat($oQuantity.val());//计算用数量
		var fTotalNew = currencyToFloat(fPrice * fQuantity);//合计
		//var fAmountd  = fnLaborCost(materialId,fTotalNew);//人工成本

		//显示到页面	
		var vPrice = formatNumber(fPrice);
		var vTotalNew = floatToCurrency(fTotalNew);

		$oSupplier.val(ui.item.supplierId);
		$oThisPrice.text(vPrice);
		$oThisPriceh.val(vPrice);
		//$oAmount1.val(vTotalNew);
		$oAmount2.html(vTotalNew);
		//$oCurrPrice.html(vPrice);
		//$oAmountd.val(fAmountd);

		//合计
		//costAcount();
		
	},

	
	});//供应商选择
}

</script>
</body>
	
</html>
