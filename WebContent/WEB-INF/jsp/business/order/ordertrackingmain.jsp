<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%@ include file="../../common/common2.jsp"%>
<title>订单跟踪--订单基本数据</title>
<script type="text/javascript">

	function ajaxTrack(orderSts,sessionFlag,follow,monthday) {
		var table = $('#TMaterial').dataTable();
		if(table) {
			table.fnClearTable(false);
			table.fnDestroy();
		}
		var url = "${ctx}/business/order?methodtype=orderTrackingSearch&sessionFlag="+sessionFlag
					+"&follow="+follow
					+"&orderSts="+orderSts
					+"&monthday="+monthday;

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
			//"retrieve" : true,
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
						
						if(key1 == "" && key2 == ""){
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
				{"data": null, "defaultContent" : '',"className" : 'td-left'},
				{"data": "YSId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "productId", "defaultContent" : '',"className" : 'td-left'},
				{"data": "productName", "defaultContent" : ''},
				{"data": "deliveryDate", "defaultContent" : '', "className" : 'td-center'},
				{"data": "orderQty", "defaultContent" : '0', "className" : 'td-right'},
				{"data": null, "className" : 'td-center', "defaultContent" : ''},
				{"data": null, "className" : 'td-center', "defaultContent" : ''},
			],
			"columnDefs":[	    		
	    		{"targets":0,"render":function(data, type, row){
	    			var followFlag = row["followStatus"];
	    			var YSId = row["YSId"];	var imgName = "pixel"; var altMsg="置顶";
	    			if(followFlag == '0'){
	    				imgName = "icon-top";
	    			}
		    		return row["rownum"]+'<input type="image" title="'+altMsg+'" style="border: 0px;" src="${ctx}/images/'+imgName+'.png" />';
	    			
	    		}},
	    		{"targets":7,"render":function(data, type, row){
	    			var followFlag = row["followStatus"];var YSId = row["YSId"];
	    			var text = "icon-top2"; var color = "red";
	    			if(followFlag == '0'){
						text = '撤销';
	    			}else{
						text = '置顶';color = "blue";
	    			}
	    			return '<a t href=\"###\"  onclick="setFollow(\''+YSId+'\');return false;" style="color: '+color+';">'+text+'</a>';
	    		}},
	    		{"targets":1,"render":function(data, type, row){
	    			var rtn = "";
	    			rtn= "<a href=\"###\" onClick=\"doShow('"+ row["YSId"] + "','"+ row["materialId"] + "')\">"+row["YSId"]+"</a>";
	    			return rtn;
	    		}},
	    		{"targets":3,"render":function(data, type, row){
	    			var name = row["productName"];		    			
	    			name = jQuery.fixedWidth(name,48);
	    			return name;
	    		}},
	    		{"targets":6,"render":function(data, type, row){//备货状态
	    			var contractQty = currencyToFloat(row["contractQty"]);
	    			var stockFlag  = currencyToFloat(row["stockFlag"]);

	    			var rtn = "已备齐";
	    			if(stockFlag > 0){
	    				rtn = "未齐";
	    			}
	    			return rtn;
	    		}},
	    		{"visible" : false,"targets" : [ 6]
				},
				{"bSortable": false, "aTargets": [ ] 
                }
         	] ,
         	//"aaSorting": [[ 1, "ASC" ]]
	    		
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
	function initEvent(){

		var monthday = $('#monthday').val();
		var follow   = $('#follow').val();
		var orderSts = $('#orderSts').val();
		
		var year = getYear();
		var mounth = getMonth() ;
		//mounth = mounth -1;//前移一个月
		if(mounth<10){
			mounth =  '0'+mounth;
		}
		//if(monthday == '12'){
		//	year = year - 1;//去年的12月
		//}
		var todaytmp = year +'-'+mounth+"-01";
		
		//alert('todaytmp:'+todaytmp)
		$('#monthday').val(todaytmp);
		ajaxTrack(orderSts,"true",follow,monthday);
		
	}

	$(document).ready(function() {

		$('#yearFlag').hide();
		initEvent();
		
		
		var month = "";
		var monthday = $('#monthday').val();
		var statusFlag = $('#statusFlag').val();
		var orderType = $('#orderType').val();

		//alert('month'+monthday)
		if(monthday == '' || monthday == null){
			month = getMonth();
			
		}else{
			month = monthday.substring(5,7);
		}
		$('#month').val(month);
		
	    buttonSelectedEvent();//按钮点击效果
	    buttonSelectedEvent3();//按钮点击效果3
		buttonSelectedEvent2();//按钮选择式样2
		$('#defutBtn'+month).removeClass("start").addClass("end");
		
	 	$('#defutBtn2').removeClass("start").addClass("end");
		$('#defutBtnz').removeClass("start").addClass("end");
		
		setYearList();
		
	})	
	
	function setYearList(){
		var i = 0;	
		var options = "";
		<c:forEach var="list" items="${year}">
			i++;
			options += '<option value="${list.key}">${list.value}</option>';
		</c:forEach>
		
		var curYear = getYear();
		$('#year').html(options);
		$('#year').val(curYear);//默认显示当前年
	}
	
	//年份选择
	function doSearchCustomer4(){
		
		$('#keyword1').val('');
		$('#keyword2').val('');
		
		var year  = $('#year').val();
		var month = $('#month').val();
		
		var monthday = year +"-"+month+'-01';
		$('#monthday').val(monthday);
		
		var orderSts = $('#orderSts').val();
		var follow = $('#follow').val();

		ajaxTrack(orderSts,'false',follow,monthday);
	}
	
	//月份选择
	function doSearchCustomer(month){
		
		$('#keyword1').val('');
		$('#keyword2').val('');
		
		var year = $('#year').val();
		
		var monthday = year +"-"+month+'-01';
		$('#monthday').val(monthday);
		$('#month').val(month);
		
		var orderSts = $('#orderSts').val();
		var follow = $('#follow').val();
		
		ajaxTrack(orderSts,'false',follow,monthday);
	}
	
	//备货情况
	function doSearchCustomer3(orderSts){
		
		$('#yearFlag').hide();//隐藏月份选择
		$('#yearShowFlag').val('');	
		
		$("#keyword1").val('');
		$("#keyword2").val('');
		var follow   = $('#follow').val();
		var monthday = $('#monthday').val();
		
		$('#orderSts').val(orderSts);
		
		ajaxTrack(orderSts,'false',follow,monthday);
	}
	
	//重点关注
	function doSearchCustomer2(follow){
		$("#keyword1").val('');
		$("#keyword2").val('');
		var orderSts = $('#orderSts').val();
		var monthday = $('#monthday').val();
		
		$('#follow').val(follow);
		ajaxTrack(orderSts,'false',follow,monthday);
	}
	
	//已领料：显示月份选择
	function doSearchCustomer4(orderSts){
		var yearShowFlag = $('#yearShowFlag').val();
		if(yearShowFlag == ''){
			$('#yearFlag').show();
			$('#yearShowFlag').val('1');			
		}
		else{
			$('#yearFlag').hide();
			$('#yearShowFlag').val('');			
		}
		
		$('#orderSts').val(orderSts);
	}
	
	
	function doSearch() {	
		$('#yearFlag').hide();//隐藏月份选择
		$('#yearShowFlag').val('');	
		
		$('.box').removeClass('end');
		$('.box3').removeClass('end');
		
		var follow = $('#follow').val();
		var orderSts = $('#orderSts').val();
		
		ajaxTrack(orderSts,'false',follow);

	}	

	function doShow(YSId,materialId) {

		var url = '${ctx}/business/order?methodtype=orderTrackingShow&YSId=' 
				+ YSId+'&materialId='+materialId;

		callWindowFullView("订单跟踪",url);
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
				//var jsonObj = data;
				status = data["status"];
				if(status == '0'){
					$().toastmessage('showNoticeToast', "置顶成功。");
				}else{
					$().toastmessage('showNoticeToast', "取消置顶。");
				}
			},
			error : function(
					XMLHttpRequest,
					textStatus,
					errorThrown) {
			}
		});
		
		$('#TMaterial').DataTable().ajax.reload(false);
	}
	

</script>
</head>

<body>
	<div id="container">

		<div id="main">
		
			<div id="search">

				<form id="condition"  style='padding: 0px; margin: 10px;' >

					<input type="hidden" id="orderSts" name="orderSts" value="2" />
					<input type="hidden" id="follow"   name="follow"   value="" />
					<input type="hidden" id="monthday" name="monthday" value="" />
					<input type="hidden" id="month"    name="month"    value="" />
					<input type="hidden" id="yearShowFlag"    name="yearShowFlag"    value="" />
					<table>
						<tr>
							<td width="50px"></td> 
							<td class="label">关键字1：</td>
							<td>
								<input type="text" id="keyword1" name="keyword1" class="middle"/></td>
							<td class="label" width="100px">关键字2：</td> 
							<td>
								<input type="text" id="keyword2" name="keyword2" class="middle"/></td>
							<td>
								<button type="button" id="retrieve" class="DTTT_button" 
									style="width:50px" value="查询" onclick="doSearch();">查询</button></td>
							
						</tr>
						<tr style="height: 25px;">
							<td width=""></td> 
							<td class="label">备货情况：
							<td colspan="4">
								<a  class="DTTT_button box3" onclick="doSearchCustomer3('2');" id="defutBtn2">未齐</a>
								<a  class="DTTT_button box3" onclick="doSearchCustomer3('1');" id="defutBtn1">已备齐</a>&nbsp;&nbsp;
							<!-- 
							<td class="label">重点关注：</td>
							<td>
								<a  class="DTTT_button box2" onclick="doSearchCustomer2('');"  id="defutBtnz">全部</a>							
								<a  class="DTTT_button box2" onclick="doSearchCustomer2('0');" id="defutBtnz0">重点关注</a>
							</td>
							 -->
							<!-- 
							<a  class="DTTT_button box3" onclick="doSearchCustomer4('3');" id="defutBtn3">已领料</a>
		<span id="yearFlag">			
			<select id="year" name="year" onchange="doSearchCustomer4();" style="width: 100px;vertical-align: bottom;"></select>
			
			<a id="defutBtn12"  class="DTTT_button box" onclick="doSearchCustomer('12');">
				12</a>
			<a id="defutBtn01"  class="DTTT_button box" onclick="doSearchCustomer('01');">
				1</a>
			<a id="defutBtn02"  class="DTTT_button box" onclick="doSearchCustomer('02');">
				2</a>
			<a id="defutBtn03"  class="DTTT_button box" onclick="doSearchCustomer('03');">
				3</a>
			<a id="defutBtn04"  class="DTTT_button box" onclick="doSearchCustomer('04');">
				4</a>
			<a id="defutBtn05"  class="DTTT_button box" onclick="doSearchCustomer('05');">
				5</a>
			<a id="defutBtn06"  class="DTTT_button box" onclick="doSearchCustomer('06');">
				6</a>
			<a id="defutBtn07"  class="DTTT_button box" onclick="doSearchCustomer('07');">
				7</a>
			<a id="defutBtn08"  class="DTTT_button box" onclick="doSearchCustomer('08');">
				8</a>
			<a id="defutBtn09"  class="DTTT_button box" onclick="doSearchCustomer('09');">
				9</a>
			<a id="defutBtn10"  class="DTTT_button box" onclick="doSearchCustomer('10');">
				10</a>
			<a id="defutBtn11"  class="DTTT_button box" onclick="doSearchCustomer('11');">
				11</a>
		</span>
		 -->
		</td>
						</tr>
					</table>

				</form>
			</div>
			<div  style="height:10px"></div>		
			<div class="list">	
				<table id="TMaterial" class="display"  style="width:100%">
					<thead>						
						<tr>
							<th style="width: 30px;" class="dt-middle ">No</th>
							<th style="width: 90px;" class="dt-middle ">耀升编号</th>
							<th style="width: 150px;" class="dt-middle ">产品编号</th>
							<th class="dt-middle ">产品名称</th>
							<th style="width: 60px;" class="dt-middle ">订单交期</th>
							<th style="width: 80px;" class="dt-middle ">订单数量</th>
							<th style="width: 60px;" class="dt-middle ">备货状态</th>
							<th style="width: 40px;" class="dt-middle ">重点<br>关注</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
