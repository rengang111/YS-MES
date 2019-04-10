<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>订单管理-订单查看</title>
<%@ include file="../../common/common2.jsp"%>
<script type="text/javascript">

var counter1  = 0;
var ysids = "";

$.fn.dataTable.TableTools.buttons.add_rows1 = $
.extend(
	true,
	{},
	$.fn.dataTable.TableTools.buttonBase,
	{
		"fnClick" : function(button) {
			var rowIndex = counter1  ;
			var trHtml = "";
			
			//rowIndex++;
			var rownum = rowIndex+1;
			var checkbox = "<input type=checkbox name='numCheck' id='numCheck' value='" + rowIndex + "' />";
			trHtml+="<tr>";	
			trHtml+='<td class="td-left">' + checkbox +'</td>';
			trHtml+='<td class="td-center"><input type="text" name="orderDevertLines['+rowIndex+'].diverttoysid"   id="orderDevertLines'+rowIndex+'.diverttoysid" class="short" /></td>';
			trHtml+='<td class="td-center"><input type="text" name="orderDevertLines['+rowIndex+'].divertfromysid" id="orderDevertLines'+rowIndex+'.divertfromysid" class="short attributeList1" /></td>';
			trHtml+='<td class="td-center"><input type="text" name="orderDevertLines['+rowIndex+'].materialid"     id="orderDevertLines'+rowIndex+'.materialid" class="attributeList1" /></td>';
			trHtml+='<td class="td-center"><span id=""></span>';
			trHtml+= '<input type="hidden" name="orderDevertLines['+rowIndex+'].divertfrompiid" id="orderDevertLines'+rowIndex+'.divertfrompiid" class="short" /></td>',
			trHtml+='<td class="td-center"><input type="text" name="orderDevertLines['+rowIndex+'].shortname"        id="orderDevertLines'+rowIndex+'.shortname" class="" /></td>',
			trHtml+='<td class="td-center"><input type="text" name="orderDevertLines['+rowIndex+'].divertquantity"   id="orderDevertLines'+rowIndex+'.divertquantity" class="short" /></td>',
			trHtml+='<td class="td-center"><input type="text" name="orderDevertLines['+rowIndex+'].thisreductionqty" id="orderDevertLines'+rowIndex+'.thisreductionqty" class="short" /></td>',
			trHtml+="</tr>";	

			$('#documentary tbody tr:last').after(trHtml);
			if(counter1 == 0){
				$('#documentary tbody tr:eq(0)').hide();//删除无效行
			}
			counter1 += 1;//记录费用总行数
			//alert(counter1+'::counter1')
			autocomplete();
				
			foucsInit();
			
		}
	});

$.fn.dataTable.TableTools.buttons.reset1 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {
			
			if(confirm('删除后不能恢复，确定要删除吗？')){
				
			}else{
				return;
			}
			var t=$('#documentary').DataTable();
			
			rowIndex = t.row('.selected').index();
			
			var str = true;
			var ysidList = '';
			$("input[name='numCheck']").each(function(){
				if ($(this).prop('checked')) {
					var n = $(this).parents("tr").index();  // 获取checkbox所在行的顺序
					$('#documentary tbody').find("tr:eq("+n+")").remove();
					
					var fromysid = $(this).parents("tr").find('td').eq(1).text();
					var toysid   = $(this).parents("tr").find('td').eq(2).text();
					if(!fromysid == '')
						ysidList += fromysid +','+toysid+';'
					str = false;
				}
			});
			
			if(str){
				$().toastmessage('showWarningToast', "请选择要 删除 的数据。");
				return;
			}else{
				//$().toastmessage('showWarningToast', "删除后,请保存");					
			}
			var rowCont = true;
			$("input[name='numCheck']").each(function(){
				rowCont = false;
			});			
			if(rowCont == true){
				$('#documentary tbody tr:eq(0)').show();//显示无效行
				counter1 = 0;
			}
			
			doDelete(ysidList);
		}
	});

function doDelete(ysidList) {
	
	var PIId = '${order.PIId}';
	var actionUrl = "${ctx}/business/order?methodtype=deleteDivertOrder&PIId="+PIId+"&ysidList="+ysidList;
	

	$('#keyBackup').val(counter1);

	$.ajax({
		type : "POST",
		contentType : 'application/json',
		dataType : 'json',
		url : actionUrl,
		data : ysidList,//
		success : function(data) {

								
			var PIId = data['PIId'];
			documentaryAjax(PIId);	//挪用订单
			
			$().toastmessage('showWarningToast', "保存成功!");		
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {				
			//alert(textStatus);
		}
	});
}

$.fn.dataTable.TableTools.buttons.save1 = $.extend(true, {},
		$.fn.dataTable.TableTools.buttonBase, {
		"fnClick" : function(button) {
			
			doSave('D');
		}
	});


function doSave(type) {
	var PIId = '${order.PIId}';
	var actionUrl = "${ctx}/business/order?methodtype=insertDivertOrder&PIId="+PIId;

	$('#keyBackup').val(counter1);

	$.ajax({
		type : "POST",
		contentType : 'application/json',
		dataType : 'json',
		url : actionUrl,
		data : JSON.stringify($('#orderForm').serializeArray()),// 要提交的表单
		success : function(data) {

			var rtnValue = data['returnValue'];
			if(rtnValue == 'SUCCESS'){
				$().toastmessage('showWarningToast', "保存成功!");	
				documentaryAjax(PIId);	//挪用订单				
			}else{
				$().toastmessage('showWarningToast', "挪用失败，请检查被挪用订单数量。");	
			}
			
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {				
			//alert(textStatus);
		}
	});
}
	
function documentaryAjax(PIId) {//挪用订单

	var table = $('#documentary').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var actionUrl = "${ctx}/business/order?methodtype=getDivertOrder&PIId="+PIId;

	var t = $('#documentary').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
		"async"		: false,
        "ordering"  : false,
		"sAjaxSource" : actionUrl,
		dom : 'T<"clear">lt',
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"type" : "POST",
				"contentType": "application/json; charset=utf-8",
				"dataType" : 'json',
				"url" : sSource,
				"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
				success : function(data) {

					counter1 = data['recordsTotal'];//记录总件数
					
					fnCallback(data);
					//$(".DTTT_container").css('float','left');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus)
				}
			})
		},
		"tableTools" : {
			"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",
			"aButtons" : [ {
				"sExtends" : "add_rows1",
				"sButtonText" : "追加新行"
			},
			{
				"sExtends" : "reset1",
				"sButtonText" : "删除行"
			},
			{
				"sExtends" : "save1",
				"sButtonText" : "保存"
			}],
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
		
		"columns" : [
		    {"data": null,"className" : 'td-center'},
		    {"data": "diverToYsid", "defaultContent" : '',"className" : 'td-center'},
		    {"data": "divertFromYsid", "defaultContent" : '',"className" : 'td-center'},
		    {"data": "materialId", "defaultContent" : '',"className" : 'td-center'},
		    {"data": "materialName", "defaultContent" : '',"className" : ''},
		    {"data": "shortName", "className" : 'td-center'},
		    {"data": "divertQuantity", "className" : 'td-center'},
		    {"data": "thisReductionQty", "className" : 'td-center'},
		    
		],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){		
				var status = row["status"];
    			var rownum = row["rownum"];
				var checkbox = "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />";

    			return checkbox ;
    		}},
    		{"targets":2,"render":function(data, type, row){
    			var status = row["status"];
    			var rownum = row["rownum"] - 1;
    			var rtnVal = data;
   				rtnVal += "<input type='hidden' name='orderDevertLines["+ rownum +"].divertfromysid' id='orderDevertLines"+ rownum +".divertfromysid'  value='" + row["divertFromYsid"] + "' />"
   				rtnVal += "<input type='hidden' name='orderDevertLines["+ rownum +"].divertoysid'    id='orderDevertLines"+ rownum +".divertoysid'     value='" + row["diverToYsid"] + "' />"
     			
      			return rtnVal;
            }}         
	           
	     ] ,			
	})
	
};//挪用订单


function divertFromListAjax(PIId) {//挪用详情

	var table = $('#divertFromList').dataTable();
	if(table) {
		table.fnDestroy();
	}

	var actionUrl = "${ctx}/business/order?methodtype=getDivertOrderFrom&PIId="+PIId;

	var t = $('#divertFromList').DataTable({
		
		"processing" : false,
		"retrieve"   : true,
		"stateSave"  : true,
		"pagingType" : "full_numbers",
        "paging"    : false,
        "pageLength": 50,
		"async"		: false,
        "ordering"  : false,
		"sAjaxSource" : actionUrl,
		dom : '<"clear">lt',
		"fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"type" : "POST",
				"contentType": "application/json; charset=utf-8",
				"dataType" : 'json',
				"url" : sSource,
				"data" : JSON.stringify($('#bomForm').serializeArray()),// 要提交的表单
				success : function(data) {

					counter1 = data['recordsTotal'];//记录总件数
					
					fnCallback(data);
					//$(".DTTT_container").css('float','left');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus)
				}
			})
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},
		
		"columns" : [
		    {"data": null,"className" : 'td-center'},
		    {"data": "divertFromYsid", "defaultContent" : '',"className" : 'td-left'},
		    {"data": "diverToYsid", "defaultContent" : '',"className" : 'td-left'},
		    {"data": "materialId", "defaultContent" : '',"className" : 'td-left'},
		    {"data": "materialName", "defaultContent" : '',"className" : ''},
		    {"data": "shortName", "className" : 'td-center'},
		    {"data": "divertQuantity", "className" : 'td-right'},
		    {"data": "thisReductionQty", "className" : 'td-right'},
		    
		],
		"columnDefs":[
		      		{"targets":0,"render":function(data, type, row){		
		  				var status = row["status"];
		      			var rownum = row["rownum"];
		  				var checkbox = "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />";

		      			return rownum ;
		      		}}       
		  	           
		  	     ] ,
			
	})
	
};//挪用详情

	function ajax() {

		var t = $('#example').DataTable({
			
			"processing" : false,
			"retrieve"   : false,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : "160px",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
	        "searching" : false,
			"dom" 		: '<"clear">rt',
	       	"language": {"url":"${ctx}/plugins/datatables/chinese.json"},
	       	
			"columns" : [
			             {},
			             {}, 
			             {}, 
			             {"className" : "dt-body-center"}, 
			             {"className" : 'td-right'}, 
			             {"className" : 'td-right'}, 
			             {"className" : 'td-right'},
			             {"className" : 'td-right'},
			             {"className" : 'td-right'},
			             {"className" : "dt-body-center"},
			             {"className" : "dt-body-center"},
			            
						],
			
			"columnDefs":[
				
			  { "targets":2,"render":function(data, type, row){
	    			var name = row[2];	    			
	    			name = jQuery.fixedWidth(name,50);		    			
	    			return name;
	    	  }},	    	  
	    	  {
					"visible" : false,
					"targets" : []
				}
			  ] 	
			
		}).draw();
						
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

	};

	function ajax2() {

		var t = $('#example2').DataTable({
			
			"processing" : false,
			"retrieve"   : false,
			"stateSave"  : false,
			"pagingType" : "full_numbers",
			//"scrollY"    : "160px",
	        "scrollCollapse": false,
	        "paging"    : false,
	        "pageLength": 50,
	        "ordering"  : false,
	        "searching" : false,
			"dom" 		: '<"clear">rt',
	       	"language": {"url":"${ctx}/plugins/datatables/chinese.json"},
	       	
			"columns" : [
			             {},
			             {}, 
			             {}, 
			             {"className" : "dt-body-center"}, 
			             {"className" : 'td-right'}, 
			             {"className" : 'td-right'}, 
			             {"className" : 'td-right'},
			             {"className" : 'td-right'},
			             {"className" : "dt-body-center"},
			             {"className" : "dt-body-center"},
			             {}
						],
			
			"columnDefs":[
				
			  { "targets":2,"render":function(data, type, row){
	    			var name = row[2];	    			
	    			name = jQuery.fixedWidth(name,35);		    			
	    			return name;
	    	  }},
	    	  { "targets":9,"render":function(data, type, row){
	    			var YSId = row[0];
	    			var type=row[10];
	    			var piid = $('#order\\.piid').val();
	    			var rtn = "<a href=\"###\" onClick=\"doDeleteOrder('"
    					+ piid + "','"+ YSId +"')\">删除</a>"+"<br />";		
	    			rtn += "<a href=\"###\" onClick=\"doPurchasePlan('"
	    				+ YSId + "')\">采购合同</a>";
	    			return rtn;
	    	  }},
	    	  {
					"visible" : false,
					"targets" : [10,]
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

	};

	
	$(document).ready(function() {
		
		var company = $('#company').val();		
		if(company == '010'){
			$('.order').hide();
		}

		//设置光标项目
		$("#attribute1").attr('readonly', "true");
		$("#attribute2").attr('readonly', "true");
		$("#attribute3").attr('readonly', "true");
		
		ajax();
		ajax2();


		var PIId = '${order.PIId}';
		documentaryAjax(PIId);	//挪用订单
		
		divertFromListAjax(PIId);//挪用详情

		productReciveAjax();//成品领料
		
		autocomplete();//		
				
		$("#goBack").click(
				function() {

					var goBackFlag = $('#goBackFlag').val();
					var materialId = $('#materialId').val();
					if(goBackFlag == "productView"){
						//该查看页面来自于一览
						var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;
						
					}else{
						var url = "${ctx}/business/order?keyBackup=${order.PIId}";
						
					}
					location.href = url;		
				});
		
		$("#edit").click(
				function() {

			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=edit");
			$('#orderForm').submit();
		});
		
		$("#doDelete").click(
				function() {
			if(confirm("删除后不能恢复,\n\n确定要删除订单吗？")) {	
				$('#orderForm').attr("action", "${ctx}/business/order?methodtype=delete");
				$('#orderForm').submit();	
			}		
		});
		
		
	});
	
	function doDeleteOrder(piid,ysid){

		if(confirm("删除后不能恢复,\n\n确定要删除订单吗？")) {	
			$('#orderForm').attr("action", "${ctx}/business/order?methodtype=delete"+"&PIId="+piid+"&YSId="+ysid);
			$('#orderForm').submit();	
		}
	}

	function ShowBomPlan(YSId,materialId) {
		var backFlag = 'orderView';
		var url = '${ctx}/business/purchasePlan?methodtype=purchasePlanAddInitFromOrder&YSId=' 
				+ YSId+'&materialId='+materialId+'&backFlag='+backFlag;

		callProductDesignView("采购方案",url);
	};
	
	function ShowProductDesign(PIId,YSId,productId,type) {
 		var goBackFlag = '';
		var url = '${ctx}/business/productDesign?methodtype=addinit'
				+'&PIId=' + PIId
				+'&YSId=' + YSId
				+'&productId=' + productId
				+'&productType=' + type
				+'&goBackFlag=' + goBackFlag;
		
		callProductDesignView("做单资料",url);
	};
	

	function ShowProductDesign2(PIId,YSId,productId,type) {
 		var goBackFlag = '';
		var url = '${ctx}/business/productDesign?methodtype=accessoryAddInit'
				+'&PIId=' + PIId
				+'&YSId=' + YSId
				+'&productId=' + productId
				+'&productType=' + type
				+'&goBackFlag=' + goBackFlag;
		
		location.href = url;
	};
	
	function doShow(materialId) {

		var url = '${ctx}/business/material?methodtype=productView&materialId=' + materialId;

		openLayer(url);
	}
	
	function doPurchasePlan(YSId) {
		
		var url = '${ctx}/business/contract?methodtype=createAcssoryContractInit';
		url = url +'&YSId='+YSId;
		callProductDesignView("配件采购",url);
		//location.href = url;
		
	}
	
	function doEditMaterial(recordid,parentid) {
		//var height = setScrollTop();
		//keyBackup:1 在新窗口打开时,隐藏"返回"按钮	
		var url = '${ctx}/business/material?methodtype=detailView';
		url = url + '&parentId=' + parentid+'&recordId='+recordid+'&keyBackup=1';
		
		layer.open({
			offset :[30,''],
			type : 2,
			title : false,
			area : [ '1000px', '500px' ], 
			scrollbar : false,
			title : false,
			content : url,
			//只有当点击confirm框的确定时，该层才会关闭
			cancel: function(index){ 
			 // if(confirm('确定要关闭么')){
			    layer.close(index)
			 // }
			  //$('#baseBomTable').DataTable().ajax.reload(null,false);
			  return false; 
			}    
		});		

	};	
</script>

<script type="text/javascript">

function autocomplete(){
	
	$(".attributeList1").autocomplete({
		minLength : 8,
		autoFocus : false,
		source : function(request, response) {
			//alert(888);
			$
			.ajax({
				type : "POST",
				url : "${ctx}/business/order?methodtype=getOrderDetailForDivert",
				dataType : "json",
				data : {
					YSId : request.term
				},
				success : function(data) {
					//alert(777);
					response($
						.map(
							data.data,
							function(item) {

								return {
									label : item.YSId + ' | ' + item.materialId +' | ' + item.materialName,
									value : item.YSId,
									id : item.materialId,
									name : item.materialName,
									piid : item.PIId,
									materialId : item.materialId
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
			//产品名称
			$(this).parent().parent().find("td").eq(4).find("span").html(ui.item.name);
			$(this).parent().parent().find("td").eq(4).find("input").val(ui.item.piid);
			//产品编号
			$(this).parent().parent().find("td").eq(3).find("input").val(ui.item.materialId);			
		},
		
	});

}


//新增领料
function doCreateProductRecive(YSId) {
	var url = "${ctx}/business/order?methodtype=addProductReciveInit"+"&YSId="+YSId;
	//alert('url：'+url)
	layer.open({
		offset :[100,''],
		type : 2,
		title : false,
		area : [ '800px', '360px' ], 
		scrollbar : false,
		title : false,
		content : url,
		cancel: function(index){ 
		  layer.close(index)
		  productReciveAjax();
		  document.getElementById('dingwei').scrollIntoView();
		  return false; 
		}
	});
}

function productReciveAjax() {
	
	var table = $('#productReceive').dataTable();
	if(table) {
		table.fnClearTable(false);
		table.fnDestroy();
	}
	var YSId = ysids;

	var t = $('#productReceive').DataTable({			
		"paging": false,
		"lengthChange":false,
		"lengthMenu":[50,100,200],//设置一页展示20条记录
		"processing" : false,
		"serverSide" : false,
		"stateSave" : false,
		"ordering "	:true,
		"searching" : false,
		"retrieve" : true,
		dom : '<"clear">rt',
		"sAjaxSource" : "${ctx}/business/order?methodtype=getProductReceiveList&YSId="+YSId,
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var param = {};
			var formData = $("#condition").serializeArray();
			formData.forEach(function(e) {
				aoData.push({"name":e.name, "value":e.value});
			});

			$.ajax({
				"url" : sSource,
				"datatype": "json", 
				"contentType": "application/json; charset=utf-8",
				"type" : "POST",
				"data" : JSON.stringify(aoData),
				success: function(data){							
					fnCallback(data);		
					//invoiceCountFn();
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	             }
			})
		},
    	"language": {
    		"url":"${ctx}/plugins/datatables/chinese.json"
    	},		
		"columns" : [
		        	{"data": null,"className":"dt-body-center"
				}, {"data": "YSId","className":"td-left"
				}, {"data": "materialId","className":"td-left"
				}, {"data": "materialName", "defaultContent" : '',// 4
				}, {"data": "receiveQuantity","className":"td-right"
				}, {"data": "receiveUnit","className":"td-center"
				}, {"data": "requester","className":"td-center"
				}, {"data": "receiveDate","className":"td-center"
				}, {"data": "remarks","className":""
				}, {"data": null,//9
				}
				
			],
		"columnDefs":[
    		{"targets":0,"render":function(data, type, row){
    			return row['rownum'];
    		}},
    		{"targets":4,"render":function(data, type, row){
    			return floatToCurrency(data);
    		}},
    		{"targets":9,"render":function(data, type, row){
    			var edit    = "<a href=\"#\" onClick=\"doUpdateInvoice('" + row["recordId"] + "','" + row["YSId"] + "')\">编辑</a>";
    			var delet   = "<a href=\"#\" onClick=\"doDeleteInvoice('" + row["recordId"] + "','" + row["receiveQuantity"] + "','" + row["YSId"] + "')\">删除</a>";
    			
    			return edit+"&nbsp;"+"&nbsp;"+delet;
            }}
    	]
		
	}).draw();
					
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

};

function doUpdateInvoice(recordId,YSId) {

	layerWidth  = '900px';
	layerHeight = '360px';
	var url = "${ctx}/business/order?methodtype=editProductInvoice&recordId=" + recordId+"&YSId="+YSId;		

	layer.open({
		offset :[50,''],
		type : 2,
		title : false,
		area : [ layerWidth, layerHeight ], 
		scrollbar : false,
		title : false,
		content : url,
		cancel: function(index){ 
			  layer.close(index)
			  productReciveAjax();
			  document.getElementById('dingwei').scrollIntoView();
			  return false; 
		}    
	});
}

function doDeleteInvoice(recordId,quantity,YSId){
	
	
	if (confirm("删除后不能恢复，确定要删除吗？")){ //
		$.ajax({
			type : "post",
			url : "${ctx}/business/order?methodtype=deleteProductInvoice&recordId="+recordId+"&quantity="+quantity+"&YSId="+YSId,
			async : false,
			data : 'key=' + recordId,
			dataType : "json",
			success : function(data) {
				document.getElementById('dingwei').scrollIntoView();
				productReciveAjax();
			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {
				
				
			}
		});
	}else{
		//
	}
}


</script>
</head>
<body>
<div id="container">
<!--主工作区,编辑页面或查询显示页面-->
<div id="main">

	<form:form modelAttribute="orderForm" method="POST"
		id="orderForm" name="orderForm"  autocomplete="off">	
		
		<form:hidden path="order.piid" value="${order.PIId}"/>
		<input type="hidden" id="goBackFlag" value="${goBackFlag }" />
		<input type="hidden" id="materialId" value="${order.materialId }" />
		<form:hidden path="keyBackup" />
		
		<fieldset>
			<legend> 订单综合信息</legend>
			<table class="form" id="table_form">
				<tr> 				
					<td class="label" width="100px">PI编号：</td>					
					<td width="150px">${order.PIId}</td>

					<td width="100px" class="label" >订单性质：</td>
					<td width="150px">${order.orderNatureName}</td>
					<td width="100px" class="label" >客户订单号：</td>
					<td colspan="3">${order.orderId}</td>
				</tr>
				<tr>
					<td class="label">客户名称：</td>				
					<td colspan="3">${order.customerId}（${order.shortName}）${order.fullName}</td>
					<td class="label">下单公司：</td>				
					<td  colspan="3">${order.orderCompanyName}
						<input type="hidden" id="company" value="${order.orderCompany }"></td>
					<!-- 
					<td class="label" width="100px">退税率：</td>				
					<td>${order.rebateRate}</td>
						 -->
				</tr>						
				<tr> 
					<td class="label">付款条件：</td>
					<td >出运后&nbsp;${order.paymentTerm}&nbsp;天</td>
						
					<td class="label">出运条件：</td>
					<td >${order.shippingCase}</td>
					
					<td class="label">出运港：</td>
					<td  width="100px">${order.loadingPort}</td>

					<td class="label"  width="100px">目的港：</td>
					<td>${order.deliveryPort}</td>							
				</tr>			
				<tr>
					<td class="label">下单日期：</td>
					<td>${order.orderDate}</td>
					
					<td class="label">订单交期：</td>
					<td>${order.deliveryDate}</td>
					
					<td class="label">业务组：</td>
					<td colspan="3">${order.team}</td>
					<!-- 
					<td class="label">销售总价：</td>
					<td>${order.total}</td>		 -->										
				</tr>							
			</table>
	</fieldset>
		
	<fieldset  style="text-align: right;margin-top: -20px;">
		<button type="button" id="edit" class="DTTT_button">编辑订单</button>
		<!-- button type="button" id="goBack" class="DTTT_button">返回</button -->
	</fieldset>	
	<fieldset style="margin-top: -30px;">
		<legend>正常订单详情</legend>
		<div class="list" style="margin-top: -4px;">
		
		<table id="example" class="display" style="width:100%">
			<thead>				
			<tr>
				<th class="dt-center" width="65px">耀升编号</th>
				<th class="dt-center" width="120px">产品编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" width="50px">型号</th>
				<th class="dt-center" width="55px">销售数量</th>
				<th class="dt-center" width="60px">生产数量</th>
				<th class="dt-center" width="30px">返还<BR>数量</th>
				<th class="dt-center" width="50px">销售单价<span class="order"><br />下单价格</span></th>
				<th class="dt-center" width="80px">销售总价<span class="order"><br />下单总价</span></th>
				<th class="dt-center" width="50px">操作</th>
				<th class="dt-center" width="50px"></th>
			</tr>
			</thead>
		<tbody>
			<c:forEach var='order' items='${detail}' varStatus='status'>	
			<c:if test="${order.orderType eq '010' }">	
				<tr>
					<td>${order.YSId}</td>
					<td><a href="###" onClick="doShow('${order.materialId}')">${order.materialId}</a></td>								
					<td>${order.materialName}</td>
					<td>${order.machineModel}</td>
					<td class="cash" style="padding-right: 20px;">${order.quantity}</td>	
					<td class="cash" style="padding-right: 20px;">${order.totalQuantity}</td>
					<td class="cash" style="padding-right: 20px;">${order.returnQuantity}</td>					
					<td class="cash" style="padding-right: 20px;">${order.price}
						<span class="order"><br />${order.RMBPrice}</span>
						</td>
					<td class="cash" style="padding-right: 20px;">${order.totalPrice}
						<span class="order"><br />${order.orderCost}</span></td>
					<td>
						<a href="###" onClick="doDeleteOrder('${order.PIId}','${order.YSId}')">删除</a><br>		
						<a href="###" onClick="ShowProductDesign('${order.PIId}','${order.YSId}','${order.materialId}','${order.productClassify}')">做单资料</a><br>						
						<a href="###" onClick="ShowBomPlan('${order.YSId}','${order.materialId}')">采购方案</a></td>	
					<td>						
						<a href="###" onClick="doCreateProductRecive('${order.YSId}')">成品领料</a></td>										
				</tr>
				<script type="text/javascript" >
					ysids = ysids + ","+ '${order.YSId}';//取得所有耀升编号

				</script>
			</c:if>
					
			</c:forEach>
			
		</tbody>
	</table>
	</div>
	</fieldset>
	<fieldset>
		<span class="tablename"> 配件订单详情</span>
		<!-- <button type="button" id="createPurchasePlan" class="DTTT_button">生成采购方案</button> -->
		<div class="list" style="margin-top: -4px;">
		
		<table id="example2" class="display" style="width:100%">
			<thead>				
			<tr>
				<th class="dt-center" width="65px">耀升编号</th>
				<th class="dt-center" width="120px">产品编号</th>
				<th class="dt-center" >产品名称</th>
				<th class="dt-center" width="40px">版本类别</th>
				<th class="dt-center" width="60px">销售数量</th>
				<th class="dt-center" width="60px">生产数量</th>
				<th class="dt-center" width="50px">销售单价</th>
				<th class="dt-center" width="80px">销售总价</th>
				<th class="dt-center" width="30px">操作</th>
				<th class="dt-center" width="30px"></th>
				<th class="dt-center" width="30px"></th>
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
					<th></th>
					<th></th>
				</tr>
			</tfoot>
		<tbody>
			<c:forEach var='order' items='${detail}' varStatus='status'>
			<c:if test="${order.orderType eq '020' }">	
				<tr>
					<td>${order.YSId}</td>
					<td><a href="###" onClick="doEditMaterial('${order.materialRecordId}','${order.materialParentId}')">${order.materialId}</a></td>								
					<td>${order.materialName}</td>
					<td>${order.productClassifyName}</td>
					<td class="cash" style="padding-right: 20px;">${order.quantity}</td>	
					<td class="cash" style="padding-right: 20px;">${order.totalQuantity}</td>						
					<td class="cash" style="padding-right: 20px;">${order.price}</td>
					<td class="cash" style="padding-right: 20px;">${order.totalPrice}</td>
					<td><a href="###" onClick="ShowProductDesign2('${order.PIId}','${order.YSId}','${order.materialId}','${order.type}')">做单资料</a></td>
					<td>${order.materialId}</td>
					<td>${order.productClassify}</td>									
				</tr>
			</c:if>	
			</c:forEach>
			
		</tbody>
	</table>
	</div>
	</fieldset>
		
	<fieldset>
		<span class="tablename"> 挪用详情</span>
			<div class="list">
				<table id="divertFromList" class="display" >
					<thead>				
						<tr>
							<th width="20px">No</th>
							<th class="dt-center" width="100px">被挪用订单</th>
							<th class="dt-center" width="100px">当前利用订单</th>
							<th class="dt-center" width="200px">产品编号</th>
							<th class="dt-center" >产品名称</th>
							<th class="dt-center" width="100px">挪用品名</th>
							<th class="dt-center" width="100px">挪用数量</th>
							<th class="dt-center" width="150px">本次减少数</th>
						</tr>
					</thead>
				</table>
			</div>
	</fieldset>		
	<fieldset>
		<span class="tablename"> 订单挪用</span>
		<div class="list">
			<table id="documentary" class="display" >
				<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="100px">当前利用订单</th>
						<th class="dt-center" width="100px">被挪用订单编号</th>
						<th class="dt-center" width="200px">产品编号</th>
						<th class="dt-center" >产品名称</th>
						<th class="dt-center" width="100px">挪用品名</th>
						<th class="dt-center" width="100px">挪用数量</th>
						<th class="dt-center" width="150px">本次减少数</th>
					</tr>
				</thead>
			</table>
		</div>
	</fieldset>		
	<fieldset>
		<span class="tablename" style="">  成品领料</span>	
		<!--a class="DTTT_button" onclick="doCreatePrice();" style="">新增领料</a -->
		<div class="list">
			<table id="productReceive" class="display" >
				<thead>				
					<tr>
						<th width="20px">No</th>
						<th class="dt-center" width="80px">耀升编号</th>
						<th class="dt-center" width="120px">产品编号</th>
						<th class="dt-center" >产品名称</th>
						<th class="dt-center" width="60px">领料数量</th>
						<th class="dt-center" width="70px">领料部门</th>
						<th class="dt-center" width="60px">领料人</th>
						<th class="dt-center" width="80px">领料日期</th>
						<th class="dt-center" width="80px">备注</th>
						<th class="dt-center" width="50px">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="dingwei"></div>
	</fieldset>
</form:form>

</div>
</div>
</body>

	
</html>
