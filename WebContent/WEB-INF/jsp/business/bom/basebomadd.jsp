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
<title>新建基础BOM</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

	var counter = 0;
	var moduleNum = 0;
	var GBomId = "";
	
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
	
	//追加单行
	$.fn.dataTable.TableTools.buttons.add_row = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {
					
					counter ++;
					for (var i=0;i<1;i++){

						var rowIndex = i+1;
						var hidden =	'<input type="hidden" name="bomDetailLines['+counter+'].subbomid" id="bomDetailLines'+counter+'.subbomid" value=""/>'+
									 	'<input type="hidden" name="bomDetailLines['+counter+'].subbomserial" id="bomDetailLines'+counter+'.subbomserial" value=""/>'
						var rowNode = $('#example')
							.DataTable()
							.row
							.add(
							  [
								'<td><input type="text"   name="bomDetailLines['+counter+'].subbomno"   id="bomDetailLines'+counter+'.subbomno" value="0" class="cash"  style="width:20px"/></td>',
								'<td>'+hidden+rowIndex+'<input type=checkbox name="numCheck" id="numCheck" value="" /></td>',
								'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
									'<input type="hidden" name="bomDetailLines['+counter+'].materialid" id="bomDetailLines'+counter+'.materialid" /></td>',
								'<td><span></span></td>',
								'<td><input type="text"   name="attributeList2"  class="attributeList2" style="width:100px"> '+
									'<input type="hidden" name="bomDetailLines['+counter+'].supplierid" id="bomDetailLines'+counter+'.supplierid" /></td>',
								'<td><input type="text"   name="bomDetailLines['+counter+'].quantity"   id="bomDetailLines'+counter+'.quantity"   class="cash"  style="width:70px"/></td>',
								'<td><span></span><input type="hidden"   name="bomDetailLines['+counter+'].price"      id="bomDetailLines'+counter+'.price" /></td>',
								'<td><span></span><input type="hidden"   name="bomDetailLines['+counter+'].totalprice" id="bomDetailLines'+counter+'.totalprice"/><input type="hidden" id="labor"></td>',
								
								]).draw();
						
												
					}					
					//counter += 1;
					
					autocomplete();
						
					foucsInit();
				}
			});
	
	//模块追加
	$.fn.dataTable.TableTools.buttons.add_rows = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {
					
					moduleNum++;//块:物料一组一组地
					
					for (var i=0;i<10;i++){

						counter ++;	//总行数
						var rowIndex = i+1;
						var hidden = '<input type="hidden" name="bomDetailLines['+counter+'].subbomid" id="bomDetailLines'+counter+'.subbomid" value=""/>'+
									 '<input type="hidden" name="bomDetailLines['+counter+'].subbomserial" id="bomDetailLines'+counter+'.subbomserial" value=""/>'
					
						var rowNode = $('#example')
							.DataTable()
							.row
							.add(
							  [
								'<td><input type="text"   name="bomDetailLines['+counter+'].subbomno"   id="bomDetailLines'+counter+'.subbomno" value="'+moduleNum+'" class="cash"  style="width:20px"/></td>',
								'<td>' + hidden + rowIndex + '<input type=checkbox name="numCheck" id="numCheck" value="" /></td>',
								'<td><input type="text"   name="attributeList1"  class="attributeList1">'+
									'<input type="hidden" name="bomDetailLines['+counter+'].materialid" id="bomDetailLines'+counter+'.materialid" /></td>',
								'<td><span></span></td>',
								'<td><input type="text"   name="attributeList2"  class="attributeList2" style="width:100px"> '+
									'<input type="hidden" name="bomDetailLines['+counter+'].supplierid" id="bomDetailLines'+counter+'.supplierid" /></td>',
								'<td><input type="text"   name="bomDetailLines['+counter+'].quantity"   id="bomDetailLines'+counter+'.quantity"   class="cash"  style="width:70px"/></td>',
								'<td><span></span><input type="hidden"   name="bomDetailLines['+counter+'].price"      id="bomDetailLines'+counter+'.price" /></td>',
								'<td><span></span><input type="hidden"   name="bomDetailLines['+counter+'].totalprice" id="bomDetailLines'+counter+'.totalprice"/><input type="hidden" id="labor"></td>',
								
															
								]).draw();
								
					}
					
					// moduleNum++;//模块数量
					
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
	
	function ajax() {

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : true,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,

			dom : 'T<"clear">rt',
			success : function(data) {
			},
			"tableTools" : {

				//"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

				"aButtons" : [ {
					"sExtends" : "add_row",
					"sButtonText" : "追加行"
				},
				{
					"sExtends" : "add_rows",
					"sButtonText" : "追加块"
				},
				{
					"sExtends" : "reset",
					"sButtonText" : "删除行"
				}],
			},
			
			"columns" : [ 
			        	{"className":"dt-body-center"
					}, {"className":"dt-body-center"
					}, {
					}, {								
					}, {				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}, {"className":"td-right"				
					}			
				]
			
		}).draw();

		
		t.on('blur', 'tr td:nth-child(3),tr td:nth-child(5)',function() {
			
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
			
			/*产品成本 = 各项累计
			人工成本 = H带头的ERP编号下的累加
			材料成本 = 产品成本 - 人工成本
			经管费 = 经管费率 x 产品成本
			核算成本 = 产品成本 + 经管费*/
			
            var $tds = $(this).parent().find("td");
			
            var $oMaterial  = $tds.eq(2).find("input:text");
            var $oQuantity  = $tds.eq(5).find("input");
			var $oThisPrice = $tds.eq(6).find("span");
			var $oAmount1   = $tds.eq(7).find("input:hidden");
			var $oAmount2   = $tds.eq(7).find("span");
			var $oAmountd   = $tds.eq(7).find("input:last-child");//人工成本
			
			var materialId = $oMaterial.val();
			var fPrice = currencyToFloat($oThisPrice.text());		
			var fQuantity = currencyToFloat($oQuantity.val());	
			var fTotalNew = currencyToFloat(fPrice * fQuantity);
			var fAmountd  = fnLaborCost(materialId,fTotalNew);//人工成本

			var vPrice = float4ToCurrency(fPrice);	
			var vQuantity = float5ToCurrency(fQuantity);
			var vTotalNew = float4ToCurrency(fTotalNew);
					
			//详情列表显示新的价格
			//$oThisPrice.val(vPrice);					
			$oQuantity.val(vQuantity);	
			$oAmount1.val(vTotalNew);	
			$oAmount2.html(vTotalNew);
			$oAmountd.val(fAmountd);

			//合计成本
			costAcount();
			
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
				var num   = i + 1;
				var checkBox = "<input type=checkbox name='numCheck' id='numCheck' value='" + num + "' />";
				//cell.innerHTML = num + checkBox;
			});
		}).draw();

	};//ajax()

	$(document).ready(function() {
		
		var rate = '${material.managementCostRate}';
		if(rate =='') $("#bomPlan\\.managementcostrate").val('2');
		
		ajax();
		autocomplete();
		//$('#example').DataTable().columns.adjust().draw();
		
		$("#goBack").click(
				function() {
					var materialId = '${product.materialId}';
					var keyBackup = $("#keyBackup").val();
					//alert("keyBackup:"+keyBackup)
					var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId+'&keyBackup='+keyBackup;
					location.href = url;		
				});
		
		$("#update").click(function() {	
			var keyBackup = $("#keyBackup").val();		
			$('#bomForm').attr("action", "${ctx}/business/bom?methodtype=baseBomInsert&keyBackup="+keyBackup);
			$('#bomForm').submit();
		});
		

		$("#searchProductModel").click(function() {		
			var model = $('#productModel').val();
			var materialId = '${product.materialId}';
			var url = "${ctx}/business/bom?methodtype=searchProductModel&model="+model+"&materialId="+materialId;
			location.href = url;
		});
		
		//经管费计算
		$("#bomPlan\\.managementcostrate").change(function() {
			
			costAcount();					
		});
	
		
		foucsInit();//input获取焦点初始化处理
		
		costAcount();//成本核算
		
		$("#reverse").click(function () { 
			$("input[name='numCheck']").each(function () {  
		        $(this).prop("checked", !$(this).prop("checked"));  
		    });
		});
		
		$("#selectall").click(function () { 
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
		});

		$(".DTTT_container").css('float','left');
		
		//Bom信息来源于复制旧的BOM的情况,后续按新增处理
		var oldBomId = '${material.bomId}';
		var newBomId = '${bomForm.bomPlan.bomid}';
		if(oldBomId != newBomId)
			$('#bomPlan\\.recordid').val('');

	});	
		
	function fnLaborCost(materialId,cost){
		
		var laborCost = '0';
		
		//判断是否是人工成本
		if(materialId != '' && materialId.substring(0,1) == 'H')
			laborCost = cost;
				
		return laborCost;
	}
	
	function costAcount(){
		//计算该客户的销售总价,首先减去旧的		
		//产品成本=各项累计
		//人工成本=H带头的ERP编号下的累加
		//材料成本=产品成本-人工成本
		//经管费=经管费率x产品成本
		//核算成本=产品成本+经管费
		var managementCostRate = $('#bomPlan\\.managementcostrate').val();
		managementCostRate = currencyToFloat(managementCostRate) / 100;//费率百分比转换
		
		var laborCost = laborCostSum();
		var bomCost = productCostSum();
		
		var fmaterialCost = bomCost - laborCost;
		var productCost = bomCost * 1.1;		
		var ftotalCost = productCost * ( 1 + managementCostRate );

		$('#bomPlan\\.bomcost').val(floatToCurrency(bomCost));
		$('#bomPlan\\.productcost').val(floatToCurrency(productCost));
		$('#bomPlan\\.laborcost').val(floatToCurrency(laborCost));
		$('#bomPlan\\.materialcost').val(floatToCurrency(fmaterialCost));
		$('#bomPlan\\.totalcost').val(floatToCurrency(ftotalCost));
		//alert('labor:'+laborCost+'--product:'+productCost)
		
	}
	
	//列合计:总价
	function productCostSum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(7).find("span").text();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})
		return sum;
	}
	
	//列合计:人工成本
	function laborCostSum(){

		var sum = 0;
		$('#example tbody tr').each (function (){
			
			var vtotal = $(this).find("td").eq(7).find("input:last-child").val();
			var ftotal = currencyToFloat(vtotal);
			
			sum = currencyToFloat(sum) + ftotal;			
		})		
		return sum;
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
		<form:hidden path="bomPlan.recordid" value="${material.productRecord}"/>
		<form:hidden path="keyBackup"  value="${keyBackup }"/>
		
		<fieldset>
			<legend>基础BOM</legend>
			<table class="form" id="table_form">
				<tr>
					<td class="label" width="100px">BOM编号：</td>
					<td width="150px">${bomForm.bomPlan.bomid}
						<form:hidden path="bomPlan.bomid"    value="${bomForm.bomPlan.bomid}"/>
						<form:hidden path="bomPlan.parentid" value="${bomForm.bomPlan.parentid}"/>
						<form:hidden path="bomPlan.subid"    value="${bomForm.bomPlan.subid}"/></td>
						
					<td class="label" width="100px">产品编号：</td>
					<td width="150px">${product.materialId}
						<form:hidden path="bomPlan.materialid"  value="${product.materialId}"/></td>
					
					<td class="label" width="100px">产品名称：</td>
					<td>${product.materialName }</td>
				</tr>
				<tr>
					<td class="label">机器型号：</td>
					<td>${product.productModel }
						<form:hidden path="bomDetail.productmodel"  value="${product.productModel}"/></td>
					
					<td class="label">客户简称：</td>
					<td>${product.shortName }</td>
					
					<td class="label">客户名称：</td>
					<td>${product.customerName }</td>						
					
				</tr>
												
			</table>
			
			<table class="form" id="table_form2" style="margin-top: 6px;">
				
				<tr>
					<td class="td-center"><label>材料成本<br>A</label></td>	
					<td class="td-center"><label>人工成本<br>B</label></td>
					<td class="td-center"><label> BOM成本<br>C=A＋B</label></td>
					<td class="td-center"><label>基础成本<br>D=C＊1.1</label></td>
					<td class="td-center"><label>经管费率<br>E</label></td>
					<td class="td-center"><label>核算成本<br>F=D＊(1+E)</label></td>
				</tr>	
				<tr>			
					<td class="td-center">
						<form:input path="bomPlan.materialcost" class="read-only cash" /></td>
					<td class="td-center">
						<form:input path="bomPlan.laborcost" class="read-only cash" value="" /></td>
					<td class="td-center">
						<form:input path="bomPlan.bomcost" class="read-only cash" value="${material.bomCost}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.productcost" class="read-only cash" value="${material.productCost}"/></td>
					<td class="td-center">
						<form:input path="bomPlan.managementcostrate" class="num mini" value="${material.managementCostRate}" style="text-align: center;"/>%</td>
					<td class="td-center">
						<form:input path="bomPlan.totalcost" class="read-only cash" value="${material.totalCost}"/></td>
				</tr>								
			</table>
	</fieldset>
		
	<div style="margin: 0px 0px 0px 0px; float:left; width:80%;padding-left: 15px;" >
		机器型号：
		<input type="text" id="productModel" class="short" style="width:80px;height: 25px;padding-left: 10px;" value="${productMode }"/>
		<button type="button" id="searchProductModel" class="DTTT_button">查询</button>
		历史BOM：<input type="text" id="searchBom" style="width:180px;height: 25px;padding-left: 10px;" value="${selectedBomId }"/>
		追加BOM：<input type="text" id="searchBom2" style="width:180px;height: 25px;padding-left: 10px;" value=""/>
	</div>
	<div style="margin: -3px 10px 0px 5px;float:right; padding:0px;">	
		<button type="button" id="update" class="DTTT_button">保存</button>
		<button type="button" id="goBack" class="DTTT_button">返回</button>
	</div>
	<fieldset>
		<div class="list" style="margin-top: -4px;">
		<table id="example" class="display">
			<thead>				
			<tr>
				<th width="30px">模块<br>编号</th>
				<th width="50px">
					<input type="checkbox" name="selectall" id="selectall" /><label for="selectall">全选</label> 
					<input type="checkbox" name="reverse" id="reverse" /><label for="reverse">反选</label></th>
				<th class="dt-center" width="200px">物料编码</th>
				<th class="dt-center" >物料名称</th>
				<th class="dt-center" width="100px">供应商编号</th>
				<th class="dt-center" width="60px">用量</th>
				<th class="dt-center" width="60px">本次单价</th>
				<th class="dt-center" width="60px">总价</th>
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
				</tr>
			</tfoot>
		<tbody>
		
		<c:if test="${fn:length(materialDetail) > 0}" >
		<script type="text/javascript">
			var prev = '0';
			var subIndex = '0';
		</script>
			<c:forEach var="detail" items="${materialDetail}" varStatus='status' >		
				
				<tr>
					<td><form:input path="bomDetailLines[${status.index}].subbomno" value="${detail.subbomno}"  class="cash" style="width:20px"/></td>
					<td><span id="index${status.index}"></span><input type=checkbox name='numCheck' id='numCheck' value='' /></td>
					<td><input type="text" name="attributeList1" class="attributeList1" value="${detail.materialId}" />
						<form:hidden path="bomDetailLines[${status.index}].materialid"  value="${detail.materialId}"/></td>								
					<td><span id="name${status.index}">${detail.materialName}</span></td>
					<td><input type="text" name="attributeList2" class="attributeList2"  value="${detail.supplierId}" style="width:100px" />
						<form:hidden path="bomDetailLines[${status.index}].supplierid"  value="${detail.supplierId}" /></td>
					<td><form:input path="bomDetailLines[${status.index}].quantity" value="${detail.quantity}"  class="cash"  style="width:70px"/></td>							
					<td><span>${detail.price}</span><form:hidden path="bomDetailLines[${status.index}].price"  value="${detail.price}"  /></td>						
					<td><span id="total${status.index}">${detail.totalPrice}</span>
						<form:hidden path="bomDetailLines[${status.index}].totalprice"  value="${detail.totalPrice}"/>
						<input type="hidden" id="labor${status.index}"></td>	
					
					<form:hidden path="bomDetailLines[${status.index}].sourceprice"  value="${detail.price}" />	
					<form:hidden path="bomDetailLines[${status.index}].subbomid"  value="" />
					<form:hidden path="bomDetailLines[${status.index}].subbomserial"  value="" />
					
				</tr>

				<script type="text/javascript">
					// var accessFlg = '${accessFlg}';
					var index = '${status.index}';
					var materialId = '${detail.materialId}';
					var materialName= "${detail.materialNameView}";
					var quantity = currencyToFloat('${detail.quantity}');
					var price =currencyToFloat( '${detail.price}');
					//var subBomId = '${detail.subBomId}';
					//var bomid = '${detail.bomId}';
					//if(accessFlg == '1'){
					//	bomid = subBomId;
					//}
					
					var totalPrice = float4ToCurrency(quantity * price);
					var labor = fnLaborCost( materialId,totalPrice);
					

					var next = '${detail.subbomno}';
					if(next == prev){
						subIndex++;
						
					}else{
						subIndex = '1';
						prev = next;
					}
		
					//alert('accessFlg'+accessFlg+"bomid:"+bomid)
					var rowNum = '${status.index +1}';
					//$('#index'+index).html(rowNum);
					$('#index'+index).html(subIndex);
					$('#labor'+index).val(labor);
					$('#total'+index).html(totalPrice);
					$('#bomDetailLines'+index+'\\.quantity').val(float5ToCurrency(quantity));
					$('#bomDetailLines'+index+'\\.totalprice').val(totalPrice);
					$('#name'+index).html(jQuery.fixedWidth(materialName,35));
					
					//$('#bomDetailLines'+index+'\\.subbomid').val(bomid);
					//$('#bomDetailLines'+index+'\\.subbomno').val(moduleNum);
					$('#bomDetailLines'+index+'\\.subbomserial').val(rowNum);
					
					moduleNum = '${detail.subbomno}';//模块数量,模块追加时,该No加一
					
					counter++;	
					
				</script>
				
			</c:forEach>
		</c:if>
		</tbody>
	</table>
	* 1、修改"模块编号",可以调整该模块的显示顺序,同一模块内的自动按照物料编码顺序排列；<br>
	 * 2、"添加单行"后,请修改该行的模块编号；
	</div>
	</fieldset>
		
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function autocomplete(){
	//BOM方案查询
	$("#searchBom").autocomplete({
		minLength : 1,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/bom?methodtype=searchBom",
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
									value : item.bomId,
									id : item.bomId,
									YSId:item.YSId
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
			//所选择的BOM编号里面含有产品编号,所以要锁定原来的产品
			var materialId = '${product.materialId}';
			var keyBackup = $('#keyBackup').val();
			var url = '${ctx}/business/bom?methodtype=changeBomAdd&bomId='+ui.item.id+'&materialId='+materialId+'&keyBackup='+keyBackup;
			location.href = url;
		},

		
	});//BOM方案查询
	
	//BOM方案查询
	$("#searchBom2").autocomplete({
		minLength : 1,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/bom?methodtype=searchBom",
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
									value : item.bomId,
									id : item.bomId,
									YSId:item.YSId
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
			//所选择的BOM编号里面含有产品编号,所以要锁定原来的产品
			var materialId = '${product.materialId}';
			var bomId = ui.item.id;
			var actionUrl = '${ctx}/business/bom?methodtype=MultipleBomAdd&bomId='+ui.item.id+'&materialId='+materialId;
			//location.href = url;
			$.ajax({
				type : "post",
				url : actionUrl,
				async : false,
				data : '',
				dataType : "json",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {
					var jsonObj = data;
					var recordCont = data["recordsTotal"];
					var dataList = data["data"];
					//alert(recordCont)
					if(recordCont>0){
						moduleNum++;
					}
					for (var i = 0; i < recordCont; i++) {						
						counter++;//总行数
						var recordId  = data['data'][i]['productRecord'];
						var bomId     = data['data'][i]['bomId'];
						var materialId  = data['data'][i]['materialId'];
						var materialName  = data['data'][i]['materialName'];
						var supplierid  = data['data'][i]['supplierId'];
						var quantity  = data['data'][i]['quantity'];
						var price  = data['data'][i]['price'];
						//alert('bomId'+bomId);
						var rowNum = i+1;
						var trhtml = "";

						var hidden = '<input type="hidden" name="bomDetailLines['+counter+'].subbomid" id="bomDetailLines'+counter+'.subbomid" value=""/>'+
									 '<input type="hidden" name="bomDetailLines['+counter+'].subbomserial" id="bomDetailLines'+counter+'.subbomserial" value=""/>';
					
						var rowNode = $('#example')
						.DataTable()
						.row
						.add(
						  [
							'<td><input type="text"   name="bomDetailLines['+counter+'].subbomno"   id="bomDetailLines'+counter+'.subbomno" value="'+moduleNum+'" class="cash"  style="width:20px"/></td>',
							'<td>'+ hidden + rowNum+'<input type=checkbox name="numCheck" id="numCheck" value="" /></td>',
							'<td><input type="text" name="attributeList1" class="attributeList1" value="'+materialId+'" />'+
							'    <input type="hidden" name="bomDetailLines['+counter+'].materialid"   id="bomDetailLines'+counter+'.materialid"  value="'+materialId+'"/></td>	',							
							'<td><span id="name'+counter+'"></span><input type="hidden" name="bomDetailLines['+counter+'].subbomid"   id="bomDetailLines'+counter+'.subbomid" value="" /></td>',
							'<td><input type="text" name="attributeList2" class="attributeList2"  value="'+supplierid+'" style="width:100px" />'+
							'    <input type="hidden" name="bomDetailLines['+counter+'].supplierid" id="bomDetailLines'+counter+'.materialid" value="'+supplierid+'" /></td>',
							'<td><input type="text"   name="bomDetailLines['+counter+'].quantity"   id="bomDetailLines'+counter+'.quantity"   value="'+quantity+'"  class="cash"  style="width:70px"/></td>	',					
							'<td class="td-right"><span id="price'+counter+'"></span><input type="hidden"   name="bomDetailLines['+counter+'].price"      id="bomDetailLines'+counter+'.price"      value="'+price+'"  /></td>	',					
							'<td class="td-right"><span id="total'+counter+'"></span>'+
							'    <input type="hidden" name="bomDetailLines['+counter+'].totalprice" id="bomDetailLines'+counter+'.totalprice" value=""/>'+
							'    <input type="hidden" id="labor'+counter+'"></td>',
							
						   ]).draw();
						   
						   
						/*   
						   
						trhtml += '<tr>';
						trhtml +='<td>'+rowNum+'<input type=checkbox name="numCheck" id="numCheck" value="' + counter + '" /></td>';
						trhtml +='<td><input type="text" name="attributeList1" class="attributeList1" value="'+materialId+'" />';
						trhtml +='    <input type="hidden" name="bomDetailLines['+counter+'].materialid"   id="bomDetailLines'+counter+'.materialid"  value="'+materialId+'"/></td>	';							
						trhtml +='<td><span id="name'+counter+'"></span></td>';
						trhtml +='<td><input type="text" name="attributeList2" class="attributeList2"  value="'+supplierid+'" style="width:100px" />';
						trhtml +='    <input type="hidden" name="bomDetailLines['+counter+'].supplierid" id="bomDetailLines'+counter+'.materialid" value="'+supplierid+'" /></td>';
						trhtml +='<td><input type="text"   name="bomDetailLines['+counter+'].quantity"   id="bomDetailLines'+counter+'.quantity"   value="'+quantity+'"  class="cash"  style="width:70px"/></td>	';					
						trhtml +='<td class="td-right"><span id="price'+counter+'"></span><input type="hidden"   name="bomDetailLines['+counter+'].price"      id="bomDetailLines'+counter+'.price"      value="'+price+'"  /></td>	';					
						trhtml +='<td class="td-right"><span id="total'+counter+'">${detail.totalPrice}</span>';
						trhtml +='    <input type="hidden" name="bomDetailLines['+counter+'].totalprice" id="bomDetailLines'+counter+'.totalprice" value="${detail.totalPrice}"/>';
						trhtml +='    <input type="hidden" id="labor'+counter+'"></td>';	
						
						trhtml +='<input type="hidden" name="bomDetailLines['+counter+'].sourceprice" id="bomDetailLines'+counter+'.sourceprice" value="${detail.price}" />	';
						trhtml +='</tr>';

						$("#example tbody tr:last").after(trhtml);
						*/

						//var subBomId = '${detail.subBomId}';
						//var bomid = '${detail.bomId}';
						//if(accessFlg == '1'){
						//	bomid = subBomId;
						//}
						
						price = currencyToFloat(price);
						quantity = currencyToFloat(quantity);
						
						var totalPrice = float4ToCurrency(quantity * price);
						var labor = fnLaborCost( materialId,totalPrice);
						$('#price'+counter).html(float4ToCurrency(price));
						$('#total'+counter).html(totalPrice);
						$('#bomDetailLines'+counter+'\\.price').val(price);
						$('#bomDetailLines'+counter+'\\.quantity').val(float5ToCurrency(quantity));
						$('#bomDetailLines'+counter+'\\.totalprice').val(totalPrice);
						$('#name'+counter).html(jQuery.fixedWidth(materialName,35));
						//alert($('#bomDetailLines'+counter+'\\.subbomid').val()+':::'+bomId)

						//$('#bomDetailLines'+counter+'\\.subbomid').val(bomid);
						//$('#bomDetailLines'+counter+'\\.subbomno').val(moduleNum);	
						//alert(moduleNum)
						//$('#bomDetailLines'+counter+'\\.subbomserial').val(rowNum);						
							
					};

					$().toastmessage('showNoticeToast', "从第 "+counter+" 行开始追加成功。");
					
					
					foucsInit();//input获取焦点初始化处理

					autocomplete();

				},
				error : function(
						XMLHttpRequest,
						textStatus,
						errorThrown) {
				}
			});
		},//select

		
	});//BOM方案查询2
	
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
			
			//产品编号
			$(this).parent().find("input:hidden").val(ui.item.materialId);
			
			var $td = $(this).parent().parent().find('td');

			var $oMatName   = $td.eq(3).find("span");
			var $oSupplier  = $td.eq(4).find("input");
			var $oQuantity  = $td.eq(5).find("input");
			var $oThisPrice = $td.eq(6).find("span");
			var $oThisPriceh= $td.eq(6).find("input:hidden");
			var $oAmount1   = $td.eq(7).find("input:hidden")
			var $oAmount2   = $td.eq(7).find("span");;
			var $oAmountd   = $td.eq(7).find("input:last-child");//人工成本
			//var $oCurrPrice = $td.eq(7).find("span");
			//var $oSourPrice = $td.eq(8).find("span");
			//var $oMinPrice  = $td.eq(9).find("span");
		
			//开始计算
			var fPrice    = currencyToFloat(ui.item.price);//计算用单价
			var fQuantity = currencyToFloat($oQuantity.val());//计算用数量
			var fTotalNew = currencyToFloat(fPrice * fQuantity);//合计
			var fAmountd  = fnLaborCost(ui.item.materialId,fTotalNew);//人工成本

			//显示到页面
			var vPrice    = float4ToCurrency(fPrice);
			var vTotalNew = float4ToCurrency(fTotalNew);
			//var vMinPrice = floatToCurrency(ui.item.minPrice);

			$oMatName.html(jQuery.fixedWidth(ui.item.name,35));
			$oSupplier.val(ui.item.supplierId);
			$oThisPrice.text(vPrice);
			$oThisPriceh.val(vPrice);
			$oAmount1.val(vTotalNew);
			$oAmount2.html(vTotalNew);
			$oAmountd.val(fAmountd);//人工成本
			//$oCurrPrice.html(vPrice);
			//$oMinPrice.html(vMinPrice);
			//$oSourPrice.html('');//清空"上次BOM价格"

			//合计
			costAcount();
			
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

			var $oMaterial  = $td.eq(2).find("input:text");
			var $oSupplier  = $td.eq(4).find("input:hidden");
			var $oQuantity  = $td.eq(5).find("input");
			var $oThisPrice = $td.eq(6).find("span");
			var $oThisPriceh= $td.eq(6).find("input:hidden");
			var $oAmount1   = $td.eq(7).find("input:hidden");
			var $oAmount2   = $td.eq(7).find("span");
			var $oAmountd   = $td.eq(7).find("input:last-child");//人工成本
			//var $oCurrPrice = $td.eq(7).find("span");
			
			//计算
			var materialId = $oMaterial.val();
			var fPrice = currencyToFloat(ui.item.price);//计算用单价
			var fQuantity = currencyToFloat($oQuantity.val());//计算用数量
			var fTotalNew = currencyToFloat(fPrice * fQuantity);//合计
			var fAmountd  = fnLaborCost(materialId,fTotalNew);//人工成本
	
			//显示到页面	
			var vPrice = float5ToCurrency(fPrice);
			var vTotalNew = floatToCurrency(fTotalNew);

			$oSupplier.val(ui.item.supplierId);
			$oThisPrice.text(vPrice);
			$oThisPriceh.val(vPrice);
			$oAmount1.val(vTotalNew);
			$oAmount2.html(vTotalNew);
			//$oCurrPrice.html(vPrice);
			$oAmountd.val(fAmountd);

			//合计
			costAcount();
			
		},

		
	});//供应商选择
}

</script>
	
</html>
