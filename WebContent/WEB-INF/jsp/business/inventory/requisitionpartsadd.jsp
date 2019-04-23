<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>配件订单领料申请-领料单</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">
		
	var shortYear = ""; 
	
	function ajax(scrollHeight) {
		
		var YSId= '${peiYsid}';
		var orderType= '${order.orderType}';
		var actionUrl = "${ctx}/business/requisition?methodtype=detailViewForParts";
		actionUrl = actionUrl +"&YSId="+YSId;
		actionUrl = actionUrl +"&orderType="+orderType;
		
		//scrollHeight =$(document).height() - 220; 
		
		var t = $('#example').DataTable({
			"paging": false,
			"processing" : false,
			"retrieve"   : true,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			"scrollY"    : scrollHeight,
	        "scrollCollapse": false,
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
						
						var piid = data["data"][0]["PIId"];
					
						$("#piid").html(piid);
						
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
				}, {"data": "YSId","className":"td-left"//1
				}, {"data": "materialId","className":"td-left"//1
				}, {"data": "materialName",						//2 
				}, {"data": "manufactureQuantity","className":"td-right"//3
				}, {"data": "totalRequisition","className":"td-right"//4 
				}, {"data": "quantityOnHand","className":"td-right","defaultContent" : '0'	//5 可用库存 
				}, {"data": null,"className":"td-right"		//6
				}
			],
			"columnDefs":[
	    		{"targets":0,"render":function(data, type, row,meta){
                    var startIndex = meta.settings._iDisplayStart; 
					return startIndex + meta.row + 1 + "<input type=checkbox  name='numCheck' id='numCheck' value='" + row["materialId"] + "'   />";
	    		}},
	    		{"targets":3,"render":function(data, type, row){ 					
					var index=row["rownum"]	
	    			var name =  jQuery.fixedWidth( row["materialName"],48);
					var inputTxt =       '<input type="hidden" id="requisitionList'+index+'.overquantity" name="requisitionList['+index+'].overquantity" value=""/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.materialid" name="requisitionList['+index+'].materialid" value="'+row["materialId"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.contractid" name="requisitionList['+index+'].contractid" value="'+row["contractId"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.supplierid" name="requisitionList['+index+'].supplierid" value="'+row["supplierId"]+'"/>';
	    			inputTxt= inputTxt + '<input type="hidden" id="requisitionList'+index+'.subbomno" name="requisitionList['+index+'].subbomno" value="'+row["subBomNo"]+'"/>';
	    			
	    			return name + inputTxt;
                }},
	    		{"targets":4,"render":function(data, type, row){	    			
	    				    								
	    			return floatToCurrency(data);				 
                }},
	    		{"targets":7,"render":function(data, type, row){	
	    			
					var index=row["rownum"];	
					
					var materialId=row["materialId"];
					var totalRequ = currencyToFloat(row["totalRequisition"]);
					var qtyManuf = currencyToFloat(row["manufactureQuantity"]);
					var currValue = qtyManuf - totalRequ;
					
					if(currValue <= 0 ){
						currValue = 0
					}else{
						currValue = floatToCurrency(currValue);
					}
					var inputTxt = '<input type="text" id="requisitionList'+index+'.quantity" name="requisitionList['+index+'].quantity" class="quantity num mini"  value="'+currValue+'"/>';
				
					return inputTxt;
                }}
			]
			
		}).draw();

		t.on('change', 'tr td:nth-child(7)',function() {

			var $td = $(this).parent().find("td");

			var $oArrival = $td.eq(3);//订单数
			var $oyiling  = $td.eq(4);//已领料
			var $oTotalQt = $td.eq(5);//总库存
			var $oCurrQty = $td.eq(6).find("input");//本次领料

			var fArrival  = currencyToFloat($oArrival.text());
			var fYiling   = currencyToFloat($oyiling.text());
			var fTotalQt  = currencyToFloat($oTotalQt.text());
			var fCurrQty  = currencyToFloat($oCurrQty.val());
			
			//最多允许领料数量 = 计划 - 已领料
		 	var fMaxQuanty = fArrival - fYiling;
			if(fMaxQuanty < 0)
				fMaxQuanty = 0;
			//alert("fArrival--fYiling--fMaxQuanty--fCurrQty:"+fArrival+"---"+fYiling+"--"+fMaxQuanty+"---"+fCurrQty)
			if ( fCurrQty > fMaxQuanty +1 ){//允许小数进位
				
				fCurrQty = fMaxQuanty;
				$().toastmessage('showWarningToast', "领料数量不能超出订单量！");
			}
			
			$oCurrQty.val(floatToCurrency(fCurrQty));

		});

	};
	
	$(document).ready(function() {
		
		var scrollHeight =$(parent).height() - 340; 
		//日期
		var mydate = new Date();
		var number = mydate.getFullYear();
		shortYear = String(number).substr(2); 
		$("#requisition\\.requisitiondate").val(shortToday());
		
		ajax(scrollHeight);
		
		$("#requisition\\.requisitiondate").datepicker({
				dateFormat:"yy-mm-dd",
				changeYear: true,
				changeMonth: true,
				selectOtherMonths:true,
				showOtherMonths:true,
			}); 
		
		
		$(".goBack").click(
				function() {
					var url = "${ctx}/business/requisition?methodtype=partsMainInit";
					location.href = url;		
				});

		$("#showHistory").click(
				function() {
					var YSId='${peiYsid }';
					var url = "${ctx}/business/requisition?methodtype=getRequisitionHistoryInitParts&YSId="+YSId;

					callProductDesignView("申请记录",url);	
				});
		
		$("#insert").click(function() {
				
			var str1 = '';
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					str1 += $(this).val();
				}
			});
			if (str1 == '') {
				$().toastmessage('showWarningToast', "请选择要领取的配件。");
				return;
			}
			var str = '';
			var val = '';
			var inputFlag=true;
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					str += $(this).val() + ",";
					var qty = $(this).parent().parent().find('td').eq(7).find("input").val();
					qty = currencyToFloat(qty);
					if(qty<=0){
						inputFlag = false;
						$(this).parent().parent().find('td').eq(7).find("input").addClass('error');
					}	
					val += qty;
				}else{
					//未选中物料的领料数量恢复为0
					$(this).parent().parent().find('td').eq(7).find("input").val('0');
				}
			});
						
			if(inputFlag == false){
				$().toastmessage('showWarningToast', "选择的配件没有领料数量。");
				return;				
			}

			$('#insert').attr("disabled","true").removeClass("DTTT_button");
			$('#formModel').attr("action", "${ctx}/business/requisition?methodtype=insertParts");
			$('#formModel').submit();
		});
		
		foucsInit();
		
		
	    //加载事件
        $(function () {
            var collection = $(".box");
            $.each(collection, function () {
                $(this).addClass("start");
            });
        });
        //单击事件
        $(".box").click(function () { 
            var collection = $(".box");
            $.each(collection, function () {
                $(this).removeClass("end");
                $(this).addClass("start");
            });
            $(this).removeClass("start");
            $(this).addClass("end");
        });
		
		 $('#example').DataTable().on(
				 'click', 
				 'tr td:nth-child(2),td:nth-child(3),tr td:nth-child(4),tr td:nth-child(5),tr td:nth-child(6),tr td:nth-child(7),tr td:nth-child(9)', function() {

				$(this).parent().toggleClass("selected");
			    var checkbox  = $(this).parent().children().first().find("input[type='checkbox']");
			    var isChecked = checkbox.is(":checked");
			    

			    if (isChecked) {
			        checkbox.prop("checked", false)
			        checkbox.removeAttr("checked");
			    } else {
			        checkbox.prop("checked", true)
			        checkbox.attr("checked","true");
			    }
			});	
		
	});
	
	function doEdit(contractId,arrivalId) {
		
		var url = '${ctx}/business/requisition?methodtype=edit&contractId='+contractId+'&arrivalId='+arrivalId;
		location.href = url;
	}

	function doShowOrder() {

		var PIId = $("#piid").text();
		var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;

		callWindowFullView("订单信息",url);
	}
	
</script>

</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

<form:form modelAttribute="formModel" method="POST"
	id="formModel" name="formModel"  autocomplete="off">

	<!-- 虚拟领料区分 -->
	<input type="hidden" id="virtualClass" value="${virtualClass }" />
	<input type="hidden" id="requrisitionFlag" value="0"/>
	<form:hidden path="requisition.ysid"  value="${peiYsid }" />
	<fieldset>
		<legend> 领料单</legend>
		<table class="form" id="table_form">
			<tr> 				
				<td class="label" width="100px">PI编号：</td>					
				<td width="150px"><a href="###"  onclick="doShowOrder();"><span id="piid"></span></a>
														
				<td width="100px" class="label">领料日期：</td>
				<td width="200px">
					<form:input path="requisition.requisitiondate" class="short read-only" /></td>
				
				<td width="100px" class="label">领料人：</td>
				<td>
					<form:input path="requisition.requisitionuserid" class="short read-only" value="${userName }" /></td>
			</tr>
										
		</table>
</fieldset>
<div style="clear: both"></div>
	
	<div id="DTTT_container" align="right" style="height:40px;margin-right: 30px;margin-top: -15px;">
		<a class="DTTT_button DTTT_button_text" id="insert" >确认领料</a>
		<!-- a class="DTTT_button DTTT_button_text" id="showHistory" >查看领料记录</a -->
		<a class="DTTT_button DTTT_button_text goBack" id="goBack" >返回</a>
	</div>
	<fieldset style="margin-top: -15px;">
		<div class="list">
			<table id="example" class="display" >
				<thead>				
					<tr>
						<th style="width:30px">No</th>
						<th width="70px">耀升编号</th>
						<th width="100px">物料编号</th>
						<th >物料名称</th>
						<th width="60px">订单数量</th>
						<th width="60px">已领数量</th>
						<th width="60px">当前库存</th>
						<th width="80px">
								<input type="checkbox" name="selectall" id="selectall" onclick="fnselectall()"/><label for="selectall">全选</label><br>
								<input type="checkbox" name="reverse" id="reverse" onclick="fnreverse()" /><label for="reverse">反选</label></th>

					</tr>
				</thead>	
			</table>
		</div>
	</fieldset>
</form:form>

</div>
</div>
</body>

<script type="text/javascript">

function fnselectall() { 
	if($("#selectall").prop("checked")){
		$("input[name='numCheck']").each(function() {
			$(this).prop("checked", true);
			$(this).parent().parent().addClass("selected");
		});
			
	}else{
		$("input[name='numCheck']").each(function() {
			if($(this).prop("checked")){
				$(this).removeAttr("checked");
				$(this).parent().parent().removeClass('selected');
			}
		});
	}
};

function fnreverse() { 
	$("input[name='numCheck']").each(function () {  
        $(this).prop("checked", !$(this).prop("checked"));  
		$(this).parent().parent().toggleClass("selected");
    });
};

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	openLayer(url);

};

function showYS(YSId) {
	var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;
	openLayer(url);

};

function reloadFn(){
	
	var countValue = '0';

	//alert("countValue1:"+countValue)
	$('#example tbody tr').each (function (){
		
		var jihua = $(this).find("td").eq(4).text();////计划用量
		var yiling  = $(this).find("td").eq(5).text();//已领量:table初始化时,第五列被隐藏了
		var kucun   = $(this).find("td").eq(6).text();//库存
		var fjihua= currencyToFloat(jihua);
		var fyiling = currencyToFloat(yiling);
		var fkucun  = currencyToFloat(kucun);
		//alert("计划用量+已领量+库存:"+fjihua+"---"+fyiling+"---"+fkucun)
		var fsurplus = fjihua - fyiling;
					
		if(fsurplus > 0){//未领完的场合下
			if(fkucun >= fsurplus){//库存大于需求量
				$(this).find("td").eq(7).find("input").val(formatNumber(fsurplus));//本次领料
				//$(this).find("td").eq(8).html("0")//剩余数清零
				countValue++;//累计未领完的物料
			}else{
				$(this).find("td").eq(7).find("input").val("0");//本次领料
				//$(this).find("td").eq(8).html(formatNumber( fsurplus - fkucun ));//剩余数清零							
			}
		}else{
			fsurplus = 0;
			$(this).find("td").eq(7).find("input").val(fsurplus);//本次领料清零
			//$(this).find("td").eq(8).html(fsurplus);//剩余数清零
		}
		
		
	});	

	if(countValue > '0')
		$('#requrisitionFlag').val('1');//是否可以继续领料标识
	
	$("#selectall").click(function () { 
		
		var sltFlag = $(this).prop("checked");
			
		$('#example tbody tr').each (function (){
		
			var vcontract = $(this).find("td").eq(4).text();////计划用量
			var vreceive  = $(this).find("td").eq(5).text();//已领量:table初始化时,第五列被隐藏了
			var vstocks   = $(this).find("td").eq(6).text();//库存
			var fcontract= currencyToFloat(vcontract);
			var freceive = currencyToFloat(vreceive);
			var fstocks  = currencyToFloat(vstocks);
			//alert("计划用量+已领量+库存:"+fcontract+"---"+freceive+"---"+fstocks)
			var fsurplus = fcontract - freceive;
			if(fsurplus < 0)
				fsurplus = 0;
			var vsurplus = formatNumber(fsurplus);

			if(sltFlag){//一次性全部领料
				
				if(fsurplus > "0"){//未领完的场合下
					if(fstocks >= fsurplus){//库存大于需求量
						$(this).find("td").eq(7).find("input").val(vsurplus);//本次领料
						//$(this).find("td").eq(8).html("0")//剩余数清零
					}else{
						$(this).find("td").eq(7).find("input").val("0");//本次领料
						//$(this).find("td").eq(8).html(formatNumber( fsurplus - fstocks ));//剩余数清零							
					}
				}else{//超领
					
				}
			
			}else{//取消一次性全部领料
				$(this).find("td").eq(7).find("input").val("0");//本次领料清零
				//$(this).find("td").eq(8).html(vsurplus);//剩余数
			}		
		})			
	

	});
	
}

//复选后单元格变色
function chkRow(obj){
	
	var r = obj.parentElement.parentElement;
	if(obj.checked){ 
		r.toggleClass("selected");
	}else if(r.rowIndex%2==1){
		r.style.backgroundColor="";
	}else{
		r.toggleClass("selected");
	}
	
}

</script>
</html>
