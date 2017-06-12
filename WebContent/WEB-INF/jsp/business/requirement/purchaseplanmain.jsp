<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../../common/common.jsp"%>

<title>采购方案一览</title>
<script type="text/javascript">

	function ajax(pageFlg) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnDestroy();
		}
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"lengthChange":false,
				"lengthMenu":[50,100,200],//设置一页展示20条记录
				"processing" : false,
				"serverSide" : false,
				"stateSave" : false,
				"ordering "	:true,
				"searching" : false,
				"pagingType" : "full_numbers",
				//"scrollY":scrollHeight,
				//"scrollCollapse":true,
				"retrieve" : true,
				"sAjaxSource" : "${ctx}/business/requirement?methodtype=getPurchasePlanList&keyBackup="+pageFlg,
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

							//重设显示窗口(iframe)高度
							//resetbodyHeight();
							//$('#TMaterial').sScrollY = $(window).height() - 300;
							//alert($('#TMaterial').sScrollY)
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
							{"data": "materialId", "defaultContent" : ''},
							{"data": "materialName", "defaultContent" : ''},
							{"data": "unit", "defaultContent" : '',"className" : 'td-center'},
							{"data": "deliveryDate", "className" : 'td-center'},
							{"data": "quantity", "defaultContent" : '0', "className" : 'td-right'},
							{"data": "total", "defaultContent" : '',"className" : 'td-right'}
						],
				"columnDefs":[
				    		{"targets":0,"render":function(data, type, row){
								return row["rownum"] ;
		                    }},
				    		{"targets":1,"render":function(data, type, row){				    			
				    			return "<a href=\"###\" onClick=\"doShowDetail('" + row["YSId"] + "')\">"+row["YSId"]+"</a>";			    			

				    		}},
				    		{"targets":3,"render":function(data, type, row){
				    			var name = row["materialName"];				    			
				    			if(name != null) name = jQuery.fixedWidth(name,40);
				    			return name;
				    		}}
			         	] 
			}
		);

	}
	
	function YSKcheck(v,id){
		var zzFlag = "";
		if(id != ''){
			zzFlag = id.substr(2,3);
		}
		if(zzFlag == 'YSK') v = 0;//库存订单不显示明细内容
		return v;
		
	}
	
	function initEvent(){
	
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

		ajax("");
		
		$("#create").click(
				function() {			
			$('#purchaseForm').attr("action", "${ctx}/business/purchase?methodtype=insert");
			$('#purchaseForm').submit();
		});	
		
	})	
	
	function doSearch() {	

		ajax("purchaseplan");

	}

	
	function doShowDetail(YSId) {
		
		var url =  "${ctx}/business/requirement?methodtype=purchasePlanView&YSId="+YSId;
		location.href = url;
	}
	
	

	function doDelete() {

		var str = '';
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				str += $(this).val() + ",";
			}
		});

		if (str != '') {
			if(confirm("确定要删除数据吗？")) {
				jQuery.ajax({
					type : 'POST',
					async: false,
					contentType : 'application/json',
					dataType : 'json',
					data : str,
					url : "${ctx}/business/matcategory?methodtype=delete",
					success : function(data) {
						reload();						
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
		             }
				});
			}
		} else {
			alert("请至少选择一条数据");
		}
		
	}
	
	
</script>
</head>

<body>
<div id="container">
<div id="main">
		
	<div id="search">
		<form id="condition"  style='padding: 0px; margin: 10px;' >
			<table>
				<tr>
					<td width="10%"></td> 
					<td class="label">关键字1：</td>
					<td class="condition">
						<input type="text" id="keyword1" name="keyword1" class="middle"/>
					</td>
					<td class="label">关键字2：</td> 
					<td class="condition">
						<input type="text" id="keyword2" name="keyword2" class="middle"/>
					</td>
					<td>
						<button type="button" id="retrieve" class="DTTT_button" 
							style="width:50px" onclick="doSearch();">查询</button>
					</td>
					<td width="10%"></td> 
				</tr>
			</table>

		</form>
	</div>
	<div  style="height:10px"></div>

	<div class="list">

		<table id="TMaterial" class="display dataTable">
			<thead>						
				<tr>
					<th style="width: 10px;" class="dt-middle ">No</th>
					<th style="width: 80px;" class="dt-middle ">耀升编号</th>
					<th style="width: 150px;" class="dt-middle ">产品编号</th>
					<th class="dt-middle ">产品名称</th>
					<th style="width: 30px;" class="dt-middle ">单位</th>
					<th style="width: 80px;" class="dt-middle ">订单交期</th>
					<th style="width: 80px;"  class="dt-middle ">订单数量</th>
					<th style="width: 100px;"  class="dt-middle ">采购金额</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</div>
</body>
</html>
