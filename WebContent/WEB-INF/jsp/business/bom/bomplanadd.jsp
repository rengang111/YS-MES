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
<title>BOM方案-新建</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter = 0;
	var productCost = "0";
	var laborCost = "0"
	
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
					
					var rowIndex = counter;
					
					for (var i=0;i<10;i++){
						
				var rowNode = $('#example')
							.DataTable()
							.row
							.add(
							  [
								'<td></td>',
								'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
									'<input type="hidden" name="bomDetailLines['+rowIndex+'].materialid" id="bomDetailLines'+rowIndex+'.materialid" /></td>',
								'<td></td>',
								'<td><input type="text"   name="attributeList2"  class="attributeList2" style="width:50px"> '+
									'<input type="hidden" name="bomDetailLines['+rowIndex+'].supplierid" id="bomDetailLines'+rowIndex+'.supplierid" /></td>',							
								'<td><input type="text"   name="bomDetailLines['+rowIndex+'].quantity"   id="bomDetailLines'+rowIndex+'.quantity"   class="cash mini" /></td>',
								'<td><input type="text"   name="bomDetailLines['+rowIndex+'].price"      id="bomDetailLines'+rowIndex+'.price"      class="cash mini" /></td>',
								'<td><span></span><input type="hidden"   name="bomDetailLines['+rowIndex+'].totalprice" id="bomDetailLines'+rowIndex+'.totalprice"/></td>',				
								'<td><span></span></td>',
								'<td><span></span></td>',	
								]).draw();
						
						rowIndex ++;						
					}					
					counter += 1;
					
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

			if(typeof rowIndex == "undefined"){				
				$().toastmessage('showWarningToast', "请选择要删除的数据。");	
				
			}else{
				
				var $tds = $('#example tbody tr').eq(rowIndex).find("td");
				
				var materialId = $tds.eq(1).find("input:text").val();
				var amount = $tds.eq(6).find("input").val();
				
				t.row('.selected').remove().draw();

				//随时计算该客户的销售总价
				costAcount(materialId,0, currencyToFloat(amount));
				$().toastmessage('showNoticeToast', "删除成功。");	
			}						
		}
	});
	
	function ajax() {
		var height = $(window).height();
		height = height - 370;

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
			"pagingType" : "full_numbers",
			//"scrollY"    : height,
	        //"scrollCollapse": true,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,

			dom : 'T<"clear">lt',
			 //"dom": 'T<"clear">lfrt',

			"tableTools" : {

				"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

				"aButtons" : [ {
					"sExtends" : "add_rows",
					"sButtonText" : "追加新行"
				},
				{
					"sExtends" : "reset",
					"sButtonText" : "清空一行"
				}],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {"className":"dt-body-right"				
					}, {"className":"dt-body-right"				
					}, {"className":"dt-body-right"				
					}, {"className":"dt-body-right"				
					}, {"className":"dt-body-right"				
					}			
				]
			
		}).draw();

		
		t.on('blur', 'tr td:nth-child(2),tr td:nth-child(4)',function() {
			
			var currValue = $(this).find("input:text").val().trim();

            $(this).find("input:text").removeClass('bgwhite');
            
            if(currValue =="" ){
            	
            	 $(this).find("input:text").addClass('error');
            }else{
            	 $(this).find("input:text").addClass('bgnone');
            }
			
		});
			
		
		t.on('blur', 'tr td:nth-child(5),tr td:nth-child(6)',function() {
			
           $(this).find("input:text").removeClass('bgwhite').addClass('bgnone');

		});
		
		t.on('change', 'tr td:nth-child(5),tr td:nth-child(6)',function() {
			
			/*产品成本 = 各项累计
			人工成本 = H带头的ERP编号下的累加
			材料成本 = 产品成本 - 人工成本
			经管费 = 经管费率 x 产品成本
			核算成本 = 产品成本 + 经管费*/
			
            var $tds = $(this).parent().find("td");
			
            var $oMaterial  = $tds.eq(1).find("input:text");
            var $oQuantity  = $tds.eq(4).find("input");
			var $oThisPrice = $tds.eq(5).find("input");
			var $oAmount1   = $tds.eq(6).find("input:hidden");
			var $oAmount2   = $tds.eq(6).find("span");
			
			var fPrice = currencyToFloat($oThisPrice.val());		
			var fQuantity = currencyToFloat($oQuantity.val());			
			var fTotalOld = currencyToFloat($oAmount1.val());
			var fTotalNew = currencyToFloat(fPrice * fQuantity);

			var vPrice = floatToCurrency(fPrice);	
			var vQuantity = floatToCurrency(fQuantity);
			var vTotalNew = floatToCurrency(fTotalNew);
					
			//详情列表显示新的价格
			$oThisPrice.val(vPrice);					
			$oQuantity.val(vQuantity);	
			$oAmount1.val(vTotalNew);	
			$oAmount2.html(vTotalNew);	

			//临时计算该客户的销售总价
			//首先减去旧的价格		
			//alert('fTotalNew:'+fTotalNew+"fTotalOld:"+fTotalOld)
			costAcount($oMaterial.val(),fTotalNew,fTotalOld);
			
		});
			
		t.on('click', 'tr', function() {
			
			var rowIndex = $(this).context._DT_RowIndex; //行号			
			//alert(rowIndex);

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

	};//ajax()

	$(document).ready(function() {
	
		$("#bomPlan\\.plandate").val(shortToday());
		$("#bomPlan\\.plandate").datepicker({
			dateFormat:"yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			selectOtherMonths:true,
			showOtherMonths:true,
		});	
				
		ajax();
		
		autocomplete();
		//设置经管费率默认值:12%
		$("#bomPlan\\.managementcostrate").val($("#bomPlan\\.managementcostrate option:eq(2)").val());
		
		//$('#example').DataTable().columns.adjust().draw();
		
		
		$("#return").click(
				function() {
					var url = '${ctx}/business/order?methodtype=init';
					location.href = url;		
				});
		
		$("#insert").click(
				function() {
			
			$('#bomForm').attr("action", "${ctx}/business/bom?methodtype=insert");
			$('#bomForm').submit();
		});
		
		//经管费计算
		$("#bomPlan\\.managementcostrate").change(function() {
			
			var fproductCost,fmanageCost,faccountCost;
			var vproductCost;
			//取得经管费率
			var selectValue = $(this).val();
			
			vproductCost = $('#bomPlan\\.productcost').val();
			fproductCost = currencyToFloat(vproductCost);
			
			fmanageCost = selectValue * fproductCost/100;
			facountCost = fmanageCost + fproductCost;
			//alert('fmanageCost:'+fmanageCost+'--facountCost:'+facountCost)

			$('#bomPlan\\.totalcost').val(floatToCurrency(facountCost));
			$('#bomPlan\\.managementcost').val(floatToCurrency(fmanageCost));

					
		});

		//重设显示窗口(iframe)高度
		iFramAutoSroll();
		
		//input获取焦点初始化处理
		foucsInit();

	});	
	
	function foucsInit(){
		
		$("input:text").not(".read-only").addClass('bgnone');
		$("#bomPlan\\.plandate").removeClass('bgnone');
		$(".cash").css('border','0px');
		$(".attributeList1 ").addClass('bsolid')
		$(".attributeList2").addClass('bsolid')
		
		$("input:text") .not(".read-only") .focus(function(){
			$(this).removeClass('bgnone').removeClass('error').addClass('bgwhite');
			$("#bomPlan\\.plandate").removeClass('bgwhite');
		    $(this).select();
		});
		
		$(".read-only").removeClass('bgwhite');
		
		$(".cash") .focus(function(){
			$(this).val(currencyToFloat($(this).val()));
		    $(this).select();
		});
		
		$(".cash") .blur(function(){
			$(this).val(floatToCurrency($(this).val()));
		});
		
		$(".DTTT_container").css('float','left');
		
	}
	
	function costAcount(materialId,totalNew,totalOld){
		//alert('productCost: '+productCost+'--new:'+totalNew+'--old:'+totalOld);
		//计算销售总价	
		//产品成本=各项累计
		//人工成本=H带头的ERP编号下的累加
		//材料成本=产品成本-人工成本
		//经管费=经管费率x产品成本
		//核算成本=产品成本+经管费
			
		//判断是否是人工成本
		var costType;
		if(materialId != '')
			costType = materialId.substring(0,1);
		
		if(costType == 'H')
			laborCost = laborCost - totalOld +  totalNew;		
		
		productCost = productCost - totalOld +  totalNew;
		
		var rate = $('#bomPlan\\.managementcostrate').val();
		var fmaterialCost,fmanageCost,facoutCost;
		
		fmaterialCost = productCost - laborCost;
		fmanageCost   = productCost * rate/100;
		facoutCost    = productCost + fmanageCost;
		
		$('#bomPlan\\.productcost').val(floatToCurrency(productCost));
		$('#bomPlan\\.laborcost').val(floatToCurrency(laborCost));
		$('#bomPlan\\.materialcost').val(floatToCurrency(fmaterialCost));
		$('#bomPlan\\.managementcost').val(floatToCurrency(fmanageCost));
		$('#bomPlan\\.totalcost').val(floatToCurrency(facoutCost));
		
	}
		
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="bomForm" method="POST"
		id="bomForm" name="bomForm"  autocomplete="off">
		
		<input type="hidden" id="tmpMaterialId" />		
		
		<fieldset>
			<legend>BOM方案 - 新建</legend>
			<table class="form" id="table_form" width="100%" style="margin-top: -4px;">
				<tr> 				
					<td class="label" width="100px"><label>BOM编号：</label></td>					
					<td width="150px">${bomForm.bomPlan.bomid}
						<form:hidden path="bomPlan.bomid" value="${bomForm.bomPlan.bomid}" /></td>
						
					<td class="label" width="100px"><label>耀升名称：</label></td>					
					<td width="250px">${bomPlan.YSId }
						<form:hidden path="bomPlan.ysid"  value="${bomPlan.YSId }"/></td>
					<td class="label" width="100px"><label>方案日期：</label></td>					
					<td>
						<form:input path="bomPlan.plandate" class="short" /></td>
				</tr>
				<tr>
					<td class="label"><label>产品编号：</label></td>				
					<td>${bomPlan.productId }
						<form:hidden path="bomPlan.materialid"  value="${bomPlan.productId }"/>
						<form:hidden path="bomPlan.subid"  value="${bomForm.bomPlan.subid }"/></td>

					<td class="label"><label>产品名称：</label></td>
					<td>${bomPlan.productName }</td>

					<td class="label"><label>订单数量：</label></td>
					<td>&nbsp;${bomPlan.quantity }
						<form:hidden path="bomPlan.orderquantity"  value="${bomPlan.quantity }"/></td>
				</tr>								
			</table>
			
			<table class="form" id="table_form2" width="100%" style="margin-top: 6px;">
				
				<tr>
					<td class="td-center"><label>材料成本</label></td>	
					<td class="td-center"><label>人工</label></td>
					<td class="td-center" width="150px"><label>经管费率</label></td>
					<td class="td-center" ><label>经管费</label></td>	
					<td class="td-center"><label>产品成本</label></td>
					<td class="td-center"><label>核算成本</label></td>
				</tr>	
				<tr>			
					<td class="td-center">
						<form:input path="bomPlan.materialcost" class="read-only cash short" value="${bomPlan.materialCost}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.laborcost" class="read-only cash short" value="${bomPlan.laborCost}" /></td>
					<td class="td-center">
						<form:select path="bomPlan.managementcostrate" style="width: 60px;" value="${bomPlan.managementCostRate}">
							<form:options items="${bomForm.manageRateList}" 
							  itemValue="key" itemLabel="value" /></form:select></td>
					<td class="td-center">
						<form:input path="bomPlan.managementcost"  class="read-only cash short" value="${bomPlan.managementCost}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.productcost" class="read-only cash short" value="${bomPlan.productCost}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.totalcost" class="read-only cash short" value="${bomPlan.totalCost}"/></td>
				</tr>								
			</table>
	</fieldset>
	<div style="clear: both"></div>
	
	<fieldset class="action" style="text-align: right;">
		<button type="button" id="return" class="DTTT_button">返回</button>
		<button type="button" id="insert" class="DTTT_button">保存</button>
	</fieldset>		
	
	<fieldset>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display" width="100%">
			<thead>				
			<tr>
				<th width="1px">No</th>
				<th class="dt-center" width="80px">ERP编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" style="width:50px;font-size:11px">供应商</th>
				<th class="dt-center" width="50px">用量</th>
				<th class="dt-center" width="50px">本次单价</th>
				<th class="dt-center" width="80px">总价</th>
				<th class="dt-center" width="50px">当前价格</th>
				<th class="dt-center" width="50px">历史最低</th>
			</tr>
			</thead>
			<tfoot>
				<tr>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</tfoot>
		<tbody>

			<c:forEach var="i" begin="0" end="99" step="1">	
				<tr>				
					<td></td>
					<td><input type="text" name="attributeList1" class="attributeList1" />
						<form:hidden path="bomDetailLines[${i}].materialid"/></td>								
					<td></td>	
					<td><input type="text" name="attributeList2" class="attributeList2" style="width:50px"/>
						<form:hidden path="bomDetailLines[${i}].supplierid"/></td>
					<td><form:input path="bomDetailLines[${i}].quantity" class="cash"  style="width:50px"/></td>							
					<td><form:input path="bomDetailLines[${i}].price" class="cash mini"  /></td>						
					<td><span></span>
						<form:hidden path="bomDetailLines[${i}].totalprice"/></td>					
					<td><span></span></td>				
					<td><span></span></td>						
				</tr>				
				<script type="text/javascript">
					counter++;
				</script>
					
			</c:forEach>
		
		</tbody>
	</table>
	</div>
	</fieldset>
		
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function autocomplete(){
	
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
			
			var rowIndex = $(this).parent().parent().parent()
					.find("tr").index(
							$(this).parent().parent()[0]);

			//alert(rowIndex);

			var t = $('#example').DataTable();
			
			//产品名称			
			if(ui.item.name.length > 15){	
				var shortName =  '<div title="' +
				ui.item.name + '">' + 
				ui.item.name.substr(0,15)+ '...</div>';
			}else{	
				var shortName = ui.item.name;
			}

			//产品名称
			t.cell(rowIndex, 2).data(shortName);
			
			//产品编号
			$(this).parent().find("input:hidden").val(ui.item.materialId);
			
			var $td = $(this).parent().parent().find('td');
			
			var $oSupplier  = $td.eq(3).find("input");
			var $oQuantity  = $td.eq(4).find("input");
			var $oThisPrice = $td.eq(5).find("input");
			var $oAmount1   = $td.eq(6).find("input:hidden");
			var $oAmount2   = $td.eq(6).find("span");
			var $oCurrPrice = $td.eq(7).find("span");
			var $oMinPrice  = $td.eq(8).find("span");
		
			//开始计算
			var fPrice    = currencyToFloat(ui.item.price);//计算用单价
			var fQuantity = currencyToFloat($oQuantity.val());//计算用数量
			var fTotalOld = currencyToFloat($oAmount1.val());//更新前的合计值
			var fTotalNew = currencyToFloat(fPrice * fQuantity);//合计
	
			//显示到页面
			var vPrice    = floatToCurrency(fPrice);
			var vTotalNew = floatToCurrency(fTotalNew);
			var vMinPrice = floatToCurrency(ui.item.minPrice);

			$oSupplier.val(ui.item.supplierId);
			$oThisPrice.val(vPrice);
			$oAmount1.val(vTotalNew);
			$oAmount2.html(vTotalNew);
			$oCurrPrice.html(vPrice);
			$oMinPrice.html(vMinPrice);
			
			//合计
			costAcount(ui.item.materialId,fTotalNew,fTotalOld);
			
			if (ui.item.supplierId !="")
				$oSupplier.removeClass('error');
			
			//光标位置
			$oQuantity.focus();
			
		},

		
	});//物料选择
	
	//供应商选择
	$(".attributeList2").autocomplete({
		minLength : 0,
		autoFocus : false,
		width  :1000,
		
		search: function( event, ui ) {
			var $tds = $(this).parent().parent().find('td');
			var material = $tds.eq(1).find("input:text").val();
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

			var $oMaterial  = $td.eq(1).find("input:text");
			var $oSupplier  = $td.eq(3).find("input:hidden");
			var $oQuantity  = $td.eq(4).find("input");
			var $oThisPrice = $td.eq(5).find("input");
			var $oAmount1   = $td.eq(6).find("input:hidden");
			var $oAmount2   = $td.eq(6).find("span");
			var $oCurrPrice = $td.eq(7).find("span");
			
			//计算
			var fPrice = currencyToFloat(ui.item.price);//计算用单价
			var fQuantity = currencyToFloat($oQuantity.val());//计算用数量
			var fTotalOld = currencyToFloat($oAmount1.val());//更新前的合计值
			var fTotalNew = currencyToFloat(fPrice * fQuantity);//合计
	
			//显示到页面	
			var vPrice = floatToCurrency(fPrice);
			var vTotalNew = floatToCurrency(fTotalNew);

			$oSupplier.val(ui.item.supplierId);
			$oThisPrice.val(vPrice);
			$oAmount1.val(vTotalNew);
			$oAmount2.html(vTotalNew);
			$oCurrPrice.html(vPrice);

			//合计
			var materialId = $oMaterial.val();
			costAcount(materialId,fTotalNew,fTotalOld);
			
		},

		
	});//供应商选择
}

</script>
	
</html>
