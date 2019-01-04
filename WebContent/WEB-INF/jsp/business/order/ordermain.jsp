<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<style>
 
        .addselect {
            border-radius: 2px;
            display: inline-block;
            background-color: #ccc;
            height: 12px;
            width: 16px;
            text-align: center;
            color: #fff;
            font-size: 9px;
            font-family: Arial;
            position: relative;
            margin-left: 4px;
            cursor: pointer;
            overflow: hidden;
            vertical-align: top;
            top: 1px;
        }
 
        .addselect select {
            width: 44px;
            opacity: 0;
            position: absolute;
            left: -10px;
            top: 0;
            cursor: pointer;
        }
 
   
    </style>

<%@ include file="../../common/common.jsp"%>

<title>订单基本数据一览页面</title>
<script type="text/javascript">

//var sortc = 0;

	function ajax(orderNature,status,sessionFlag,col_no) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/order?methodtype=search&sessionFlag="+sessionFlag
				+"&status="+status+"&orderNature="+orderNature;
		
		//$(".addselect").remove();
		//var scrollHeight = $(document).height() - 197; 
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//每页显示条数设置
				"processing" : true,
				"serverSide" : true,
				"stateSave" : false,
	         	"bAutoWidth":false,
				"bSort":true,
				// "bFilter": false, //列筛序功能
				"ordering"	:true,
				"searching" : false,
				// "Info": true,//页脚信息
				// "bPaginate": true, //翻页功能
				"pagingType" : "full_numbers",
				//"scrollY":scrollHeight,
				//"scrollCollapse":false,
				//"retrieve" : true,
				//"dom": 'rt<"bottom"ilp><"clear">',//dom定位			
				//"dom": '<"top"f >rt<"bottom"ilp><"clear">',//dom定位             
				
				"sAjaxSource" : url,
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
							//sortc = 5;//data["iSortCol_0"];
							//alert(sortc)
							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);
						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null, "defaultContent" : '',"className" : 'td-center'},
					{"data": "YSId", "defaultContent" : ''},
					{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},
					{"data": "materialName", "defaultContent" : ''},//3
					{"data": "orderDate", "defaultContent" : ''},
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-left'},
					{"data": "quantity", "defaultContent" : '0', "className" : 'td-right'},
					{"data": "team", "className" : 'td-left'},//7
					{"data": "statusName", "className" : 'td-center'},//8
					{"data": "storageDate", "className" : 'td-center'},//9
				],
				"columnDefs":[
		    		{"targets":0,"render":function(data, type, row){
		    			return row["rownum"];
		    			//return row["rownum"] + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["recordId"] + "' />"
                    }},
		    		{"targets":1,"render":function(data, type, row){
		    			var rtn = "";
		    			rtn= "<a href=\"###\" onClick=\"doShow('"+ row["PIId"] + "')\">"+row["YSId"]+"</a>";
		    			return rtn;
		    		}},
		    		{"targets":6,"render":function(data, type, row){
		    			
		    			return floatToCurrency(data);
		    		}},
		    		{"targets":3,"render":function(data, type, row){
		    			var name = row["materialName"],id = row["YSId"], zzFlag = "";
		    			name = jQuery.fixedWidth(name,40);//true:两边截取,左边从汉字开始
		    			var zzFlag = "";
		    			//if(id != ''){
		    			//	zzFlag = id.substr(2,3);
		    			//}
		    			//if(zzFlag == 'YSK') name = '库存订单';//库存订单不显示明细内容
		    			
		    			return name;
		    		}},
		    		{"targets":7,"render":function(data, type, row){
		    			return jQuery.fixedWidth(row["team"],10);
		    		}},
		    		{
		    			"orderable":false,"targets":[0]
		    		},
		    		{
						"visible" : false,
						"targets" : [col_no]
					},
					{
						//"order": [[ 2, 'asc' ]]
					}
	         	],
	         	//"aaSorting": [[ sortc, "DESC" ]]
	         	/*
	         	
	         	"fnInitComplete": function () {//列筛选
	                   var api = this.api();
	                   api.columns().indexes().flatten().each(function (i) {
	                       if (i == 9 ) {//删除第一列与第二列的筛选框
	                           var column = api.column(i);
	                           var $span = $('<span class="addselect">▼</span>').appendTo($(column.header()))
	                           var select = $('<select><option value="">All</option></select>')
	                                   .appendTo($(column.header()))
	                                   .on('click', function (evt) {
	                                       evt.stopPropagation();
	                                       var val = $.fn.dataTable.util.escapeRegex(
	                                               $(this).val()
	                                       );
	                                       column
	                                               .search(val ? '^' + val + '$' : '', true, false)
	                                               .draw();
	                                   });
	                           column.data().unique().sort().each(function (d, j) {
	                               function delHtmlTag(str) {
	                                   return str.replace(/<[^>]+>/g, "");//去掉html标签
	                               }
	 
	                               d = delHtmlTag(d)
	                               select.append('<option value="' + d + '">' + d + '</option>')
	                               $span.append(select)
	                           });
	 
	                       }
	                   });
	 
	               }*/
	         	
			});


	}
	
	
	function YSKcheck(v,id){
		var zzFlag = "";
		if(id != null && id != ''){
			zzFlag = id.substr(2,3);
		}
		if(zzFlag == 'YSK') v = 0;//库存订单不显示明细内容
		return v;
		
	}
	
	function initEvent(){

		ajax("","010","true",9);
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	$('#TMaterial').DataTable().$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}

	$(document).ready(function() {

		initEvent();
		
		$("#showText").click(function(){ 
			$("#panel").slideToggle("slow"); 
			$(this).toggleClass("active"); 
			var text = $("#showText").text();
			if(text == "展开"){
				$("#showText").text("收起");
			}else{
				$("#showText").text("展开");
			}
			return false; 
		}); 
		

		buttonSelectedEvent();//按钮选择式样
		
		$('#defutBtn').removeClass("start").addClass("end");
	})	
	
	function doSearch() {	

		ajax('','','false',9);
		
		var collection = $(".box");
	    $.each(collection, function () {
	    	$(this).removeClass("end");
	    });
	}
	
	function doCreate(type) {
		
		var url = '${ctx}/business/order?methodtype=create'+'&orderNature='+ type;
		location.href = url;
	}
	//订单状态
	function doSearchCustomer(type,col_no){
		ajax('',type,'false',col_no);
	}
	//常规订单OR库存
	function doSearchCustomer2(orderNature,col_no){
		ajax(orderNature,'','false',col_no);
	}	
	
	function doCreateZZ() {
		
		var url = '${ctx}/business/zzorder?methodtype=create';
		location.href = url;
	}
	
	function doCreateZP() {
		
		var url = '${ctx}/business/zporder?methodtype=create';
		location.href = url;
	}
	
	function doShow(PIId) {

		var url = '${ctx}/business/order?methodtype=detailView&PIId=' + PIId;

		callWindowFullView("订单详情",url);
	}

	function doEdit(recordId,parentId) {
		var url = '${ctx}/business/order?methodtype=edit&parentId=' + parentId+'&recordId='+recordId;

		location.href = url;
	}	

	function doTransfer() {
		var url = '${ctx}/business/order?methodtype=orderTransfer';

		location.href = url;
	}

	function reload() {
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		
		return true;
	}
	
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<input type="hidden" id="keyBackup" value="${keyBackup }" />
			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label" style="width:100px">关键字1：</td>
					<td class="condition">
						<input type="text" id="keyword1" name="keyword1" class="middle"/></td>
					<td class="label" style="width:100px">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle"/></td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" value="查询" onclick="doSearch();"/>查询</td>
					<td style="vertical-align: bottom;width: 150px;">
					<!-- <p class="slide">高级查询&nbsp;[<a id="showText" href="javascript:;" class="btn-slide active">展开</a>]</p>-->
					</td> 
					<td width="10%"></td> 
				</tr>
				<!--  
				<tr>
				<td colspan="8">
				<div style="display: none;" id="panel"> 
					<table>
					<tr style="height:30px">
						<td width="10%"></td> 
						<td class="label" style="width:100px">采购合同：</td>
						<td width="300px" >
							<label><input type="checkbox" name="contract"  value="" />未做成</label>
							<label><input type="checkbox" name="contract"  value="" />未到货</label>&nbsp;
							<label><input type="checkbox" name="contract"  value="" />部分到货</label>
							<label><input type="checkbox" name="contract"  value="" />全部到货</label>
						</td>
						<td class="label" style="width:100px">生产计划：</td> 
						<td>						
							<label><input type="checkbox" name="manufacture"  value="" />未生产</label>
							<label><input type="checkbox" name="manufacture"  value="" />部分完成</label>
							<label><input type="checkbox" name="manufacture"  value="" />全部完成</label></td>
						<td>
							</td>
						<td style="vertical-align: bottom;"></td> 
						<td width="10%"></td> 
					</tr>
					<tr style="height:30px">
						<td width="10%"></td> 
						<td class="label" style="width:100px">财务状况：</td>
						<td width="300px" >
							<label><input type="checkbox" name="receipt"  value="" />未收款</label>
							<label><input type="checkbox" name="receipt"  value="" />部分收款</label>
							<label><input type="checkbox" name="receipt"  value="" />全部收款</label>
						</td>
						<td class="label" style="width:100px">物流：</td> 
						<td>						
							<label><input type="checkbox" name="delivery"  value="" />未发货</label>
							<label><input type="checkbox" name="delivery"  value="" />部分发货</label>
							<label><input type="checkbox" name="delivery"  value="" />已&nbsp;清</label></td>
						<td>
							</td>
						<td style="vertical-align: bottom;"></td> 
						<td width="10%"></td> 
					</tr>
					</table>
				</div>
				</td>
				</tr>
				 -->
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">

		<div id="TSupplier_wrapper" class="dataTables_wrapper">
			<div id="DTTT_container2" style="height:40px;float: left">
				<a  class="DTTT_button box" onclick="doSearchCustomer('010',9);" id="defutBtn"><span>待合同</span></a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('020',9);"><span>待到料</span></a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('030',9);"><span>待交货</span></a>
				<a  class="DTTT_button box" onclick="doSearchCustomer('040',7);"><span>已入库</span></a>&nbsp;&nbsp;
			<!--	<a  class="DTTT_button box" onclick="doSearchCustomer2('010',9);"><span>常规订单</span></a> -->
				<a  class="DTTT_button box" onclick="doSearchCustomer2('020',9);"><span>库存订单</span></a>
			</div>
			<div id="DTTT_container" style="height:40px;float: right">
				<a  class="DTTT_button " onclick="doCreate(1);"><span>订单录入</span></a>
			 	<a  class="DTTT_button " onclick="doTransfer();"><span>订单转移</span></a> 
			</div>
			<div id="clear"></div>
			<table id="TMaterial" class="display" >
				<thead>						
					<tr>
						<th style="width: 10px;">No</th>
						<th style="width: 70px;">耀升编号</th>
						<th style="width: 150px;">产品编号</th>
						<th>产品名称</th>
						<th style="width: 50px;">下单日期</th>
						<th style="width: 50px;">订单交期</th>
						<th style="width: 60px;">数量</th>
						<th style="width: 40px;">业务组</th>
						<th style="width: 60px;">订单状态</th>
						<th style="width: 50px;">入库时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>
