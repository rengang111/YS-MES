<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common.jsp"%>

<title>自制件领料一览</title>
<script type="text/javascript">

	function ajax(colNum,type,scrollHeight,sessionFlag) {
		
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var makeType = $("#makeType").val();
		var url = "${ctx}/business/requisitionzz?methodtype=searchOrderList";
		url = url + "&sessionFlag="+sessionFlag;
		url = url + "&makeType="+makeType;
		url = url + "&requisitionSts="+type;

		var scrollHeight = $(document).height() - 230; 
		var t = $('#TMaterial').DataTable({
				"paging": true,
				"iDisplayLength" : 50,
				"lengthChange":false,
				//"lengthMenu":[10,150,200],//设置一页展示20条记录
				"processing" : true,
				"serverSide" : true,
				"ordering "	:true,
				"searching" :false,
				"stateSave" :false,
	         	"bAutoWidth":false,
				"pagingType" : "full_numbers",
				"sAjaxSource" : url,
				"scrollY":scrollHeight,
				"scrollCollapse":true,
				 "aaSorting": [[ 5, "asc" ]],
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
							$("#keyword1").val(data["keyword1"]);
							$("#keyword2").val(data["keyword2"]);

							$("input[aria-controls='TMaterial']").css("height","25px");
							$("input[aria-controls='TMaterial']").css("width","200px");
							$("#TMaterial_filter").css("float","left");

						},
						 error:function(XMLHttpRequest, textStatus, errorThrown){
			             }
					})
				},
				fnRowCallback : function(nRow, aData, iDisplayIndex){  
		            //jQuery("td:first", nRow).html(iDisplayIndex +1);  
		            //return nRow;  
		       }, 
	        	"language": {
	        		"url":"${ctx}/plugins/datatables/chinese.json"
	        	},
				"columns": [
					{"data": null,"className" : 'td-right'},
					{"data": "requisitionId", "className" : 'td-left'},//1
					{"data": "YSId", "defaultContent" : '', "className" : 'td-left'},//2
					{"data": "materialId", "defaultContent" : '', "className" : 'td-left'},//3
					{"data": "materialName", "defaultContent" : ''},//4
					{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-center'},//5
					{"data": "totalQuantity", "defaultContent" : '0', "className" : 'td-right'},//6
					{"data": "requisitionSts", "className" : 'td-center'},//7
					{"data": null, "defaultContent" : '', "className" : 'td-center'},//8

							
						],
				"columnDefs":[
			    		{"targets":0,"render":function(data, type, row,meta){
		                    var startIndex = meta.settings._iDisplayStart; 
							return startIndex + meta.row + 1 + "<input type=checkbox name='numCheck' id='numCheck' value='" + row["YSId"] + "' />";
			    		}},
			    		{"targets":1,"render":function(data, type, row){
			    			var rtn = row["requisitionId"];//
			    			if(rtn == ""){
					    		rtn= "<a href=\"###\" onClick=\"doCreate2('" + row["YSId"] +"')\">" + "（快速领料）" + "</a>";
			    			}else{
				    			rtn= "<a href=\"###\" onClick=\"doShowDetail('"+ row["taskId"] + "')\">" + row["requisitionId"] + "</a>";
			    			}
			    			
			    			return rtn;
			    		}},
			    		{"targets":2,"render":function(data, type, row){
			    			var rtn = "";
			    			//rtn= "<a href=\"###\" onClick=\"doShow('" + row["recordId"] +"','"+ row["parentId"] + "')\">" + row["YSId"] + "</a>";
			    			rtn=  row["YSId"];
			    			return rtn;
			    		}},
			    		{"targets":4,"render":function(data, type, row){
			    			
			    			var name = row["materialName"];				    			
			    			name = jQuery.fixedWidth(name,45);				    			
			    			return name;
			    		}},
			    		{"targets":5,"render":function(data, type, row){
			    			var rtn = data;
			    			if(data == null || data == "")
			    				rtn = "（未领料）"			    			
			    			return rtn;
			    		}},
			    		{"targets":7,"render":function(data, type, row){
			    			var rtn = "";
			    			if(data == "010"){
			    				rtn = "待申请";
			    				
			    			}else if(data=="020"){
			    				rtn = "待出库";
			    				
			    			}else{
			    				rtn = "已出库";
			    				
			    			}			    			
			    			return rtn;
			    		}},
			    		{"targets":8,"render":function(data, type, row){
			    			var rtn = "";
			    			var sts = row["requisitionSts"];
			    			if(sts == "010")
			    				rtn= "<a href=\"###\" onClick=\"doCreate2('" + row["YSId"] +"')\">" + "快速领料" + "</a>";
			    			return rtn;
			    		}},
			    		{ "bSortable": false, "aTargets": [ 0 ] },
			    		{
							"visible" : false,
							"targets" : [8]
						}
		           
		         ] 
			});
		
		
	}

	
	$(document).ready(function() {
		var scrollHeight = $(document).height() - 200; 

		ajax(1,"010",scrollHeight,"true");
	
		$('#TMaterial').DataTable().on('click', 'tr', function() {
			
			$(this).toggleClass("selected");
		    if($(this).hasClass("selected")){//如果有某个样式则表明，这一行已经被选中
		        
		    	$(this).children().first().children().prop("checked", true);
		    }else{//如果没有被选中

		    	$(this).children().first().children().prop("checked", false);
		    }			
		});	
		

	})	
	
	function doSearch() {	

		//S:点击查询按钮所的Search事件,对应的有初始化和他页面返回事件
		var scrollHeight = $(document).height() - 200; 
		
		ajax(8,"",scrollHeight,"false");

	}
	
	function doSearch2(colNum,type) {	

		$("#keyword1").val("");
		$("#keyword2").val("");
		var scrollHeight = $(document).height() - 200; 
		
		ajax(colNum,type,scrollHeight,"false");

	}
	
	function doCreate() {
		var str = '';
		var requisitionId = '';
		var requisitionId_next = '';
	    var flag = true;
	    var rtnValue = true;
		$("input[name='numCheck']").each(function(){
			if ($(this).prop('checked')) {
				str += $(this).val() + ",";
				/*
				if(flag == 'true'){
					requisitionId = $(this).parent().parent().find('td').eq(4).text();
					requisitionId_next = $(this).parent().parent().find('td').eq(4).text();
					flag = false;
				}else{
					requisitionId_next = $(this).parent().parent().find('td').eq(4).text();
					
					if(requisitionId_next != '（未领料）' && requisitionId != requisitionId_next){
						
						var ysid = $(this).parent().parent().find('td').eq(1).text();
						rtnValue = confirm('耀升编号[ '+ysid+' ]与其他耀升编号不在同一领料单内！')
						return false;
					}
				}
				*/
			}
		});

		if(rtnValue=='false')
			return;
		
		if (str == '') {
			alert("请至少选择一条数据");
			return;
		}		
		var makeType = $("#makeType").val();
		var url = '${ctx}/business/requisitionzz?methodtype=addinit';
		url = url +"&data="+str;
		url = url +"&makeType="+makeType;
		location.href = url;
		
	}
	
	function doCreate2(YSId) {
	
		var makeType = $("#makeType").val();
		var url = '${ctx}/business/requisitionzz?methodtype=addinit';
		url = url +"&data="+YSId;
		url = url +"&makeType="+makeType;
		location.href = url;
		
	}
	
	function doShowDetail(taskId) {

		var makeType = $("#makeType").val();
		var url = '${ctx}/business/requisitionzz?methodtype=detailView&taskId=' + taskId+'&makeType='+makeType;
		
		location.href = url;
	}

	function reload() {
		
		$('#TMaterial').DataTable().ajax.reload(null,false);
		
		return true;
	}
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
	
</script>
</head>

<body>
<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition"  style='padding: 0px; margin: 10px;' >
					
					<input type="hidden" id="makeType" value="${makeType }" />

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
				<div id="DTTT_container" style="height:40px;margin-bottom: -10px;float:left">
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2(1,'010');"><span>待申请</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2(8,'020');"><span>待出库</span></a>
					<a class="DTTT_button DTTT_button_text" onclick="doSearch2(8,'030');"><span>已领料</span></a>
				</div>
				<div style="height: 40px;margin-bottom: -15px;float:right">
					<a class="DTTT_button DTTT_button_text" onclick="doCreate();">自制件领料申请</a>
				</div>
				<table style="width: 100%;" id="TMaterial" class="display">
					<thead>						
						<tr>					
							<th width="50px">
								<input type="checkbox" name="selectall" id="selectall" onclick="fnselectall()"/><label for="selectall">全选</label><br>
								<input type="checkbox" name="reverse" id="reverse" onclick="fnreverse()" /><label for="reverse">反选</label></th>
							
							<th style="width: 70px;">领料单编号</th>
							<th style="width: 70px;">耀升编号</th>
							<th style="width: 120px;">产品编号</th>
							<th>产品名称</th>
							<th style="width: 50px;">订单交期</th>
							<th style="width: 60px;">订单数量</th>
							<th style="width: 50px;">领料状态</th>
							<th style="width: 50px;">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
