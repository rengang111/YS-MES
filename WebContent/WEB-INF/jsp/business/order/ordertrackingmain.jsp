<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>订单跟踪--订单基本数据</title>
<script type="text/javascript">

	function ajax(sqlFlag,sessionFlag) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/order?methodtype=orderTrackingSearch&sessionFlag="+sessionFlag
					+"&sqlFlag="+sqlFlag
					+"&sessionFlag="+sessionFlag;

		var t = $('#TMaterial').DataTable({
			"paging": true,
			 "iDisplayLength" : 50,
			"lengthChange":false,
			//"lengthMenu":[10,150,200],//设置一页展示20条记录
			"processing" : true,
			"serverSide" : true,
			"stateSave" : false,
			"ordering "	:true,
			"searching" : false,
			"pagingType" : "full_numbers",
			"retrieve" : true,
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
						var key1 = data["keyword1"]
						var key2 = data["keyword2"]
						$("#keyword1").val(key1);
						$("#keyword2").val(key2);
						
						if(myTrim(key1) == "" && myTrim(key2) == ""){
						 	$('#defutBtn').removeClass("start").addClass("end");							
						}else{							
						 	$('#defutBtn').removeClass("end").addClass("start");
						}			
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
				{"data": "YSId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "productId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "productName", "defaultContent" : ''},
				{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-center'},
				{"data": "orderQty", "defaultContent" : '0', "className" : 'td-right'},
				{"data": null, "className" : 'td-center', "defaultContent" : ''},
			],
			"columnDefs":[	    		
	    		{"targets":0,"render":function(data, type, row){
	    			var followFlag = row["status"];
	    			var YSId = row["YSId"];
	    			
	    			var imgName = "follow1";
	    			if(followFlag == '0'){
	    				imgName = "follow2";
	    			}
	    				return row["rownum"]+'<input type="image" style="border: 0px;" src="${ctx}/images/'+imgName+'.png" onclick="setFollow('+YSId+');return false;"/>';
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var rtn = "";
	    			rtn= "<a href=\"###\" onClick=\"doShow('"+ row["YSId"] + "','"+ row["materialId"] + "')\">"+row["YSId"]+"</a>";
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = row["materialName"];		    			
	    			name = jQuery.fixedWidth(name,60);
	    			return name;
	    		}},
	    		{"visible" : false,"targets" : [ ]
				},
				{"bSortable": false, "aTargets": [ 0,6 ] 
                }
         	] ,
         	"aaSorting": [[ 1, "ASC" ]]
	    		
		});
		
		t.on('click', 'tr', function() {

			if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	            t.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
		});
	}


	$(document).ready(function() {

		ajax("1","true");

	    buttonSelectedEvent();//按钮点击效果
	 	$('#defutBtn').removeClass("start").addClass("end");
		
	})	
	
	//订单状态
	function doSearchCustomer(sqlFlag){
		$("#keyword1").val('');
		$("#keyword2").val('');
		ajax(sqlFlag,'false');
	}
	
	function doSearch() {	
		$('.box').removeClass('end');
		ajax('','false');

	}
	

	function doShow(YSId,materialId) {

		var url = '${ctx}/business/order?methodtype=orderTrackingShow&YSId=' 
				+ YSId+'&materialId='+materialId;

		location.href = url;
	}
	
	function setFollow(YSId){
		
		$.ajax({
			type : "post",
			url : "${ctx}/business/order?methodtype=setOrderFollow"+"&YSId="+YSId,
			async : false,
			data : 'key=' + YSId,
			dataType : "json",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				var jsonObj = data;
				
				$().toastmessage('showNoticeToast', "重点关注成功。");
			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {
			}
		});
		
		$('#example').DataTable().ajax.reload(false);
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
									style="width:50px" value="查询" onclick="doSearch();">查询</button>
							</td>
							<td width="10%"></td> 
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>		
			<div class="list">			
				<div id="DTTT_container2" style="height:40px;float: left">
					<a  class="DTTT_button box" onclick="doSearchCustomer('0');">货已备齐</a>
					<a  class="DTTT_button box" onclick="doSearchCustomer('1'); " id="defutBtn">未齐</a>
				</div>				
				<table id="TMaterial" class="display"  style="width:100%">
					<thead>						
						<tr>
							<th style="width: 30px;" class="dt-middle ">No</th>
							<th style="width: 90px;" class="dt-middle ">耀升编号</th>
							<th style="width: 150px;" class="dt-middle ">产品编号</th>
							<th class="dt-middle ">产品名称</th>
							<th style="width: 60px;" class="dt-middle ">订单交期</th>
							<th style="width: 80px;" class="dt-middle ">订单数量</th>
							<th style="width: 30px;" class="dt-middle "></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
