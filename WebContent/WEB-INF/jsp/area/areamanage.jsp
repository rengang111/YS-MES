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
<title>业务字典-省市地区</title>
<meta charset="UTF-8">
<%@ include file="../common/common.jsp"%>
<script type="text/javascript">
	
	$.fn.dataTable.TableTools.buttons.Delete = $
		.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {
				
				// alert("删除功能将来会有权限控制，只能管理员使用！不在使用的条目可以通过编辑状态进行控制。");

				var str_temp = "";

				$('#example').DataTable().rows('.selected').data()
						.each(function(data) {
							str_temp += data["recordId"] + ",";
						});

				var str = str_temp
						.substring(0, str_temp.length - 1);

				if (str != "") {
					
					$.ajax({
						type : "GET",
						url : "${ctx}/area?methodtype=delete&recordId=" + str,
						data : null,// 要提交的表单
						success : function(d) {
							
							reload();
						},
						error : function(
								XMLHttpRequest,
								textStatus,
								errorThrown) {
							
							//alert("${err002}");    //发生系统异常，请再试或者联系管理员。
							reload();
						}
					});

				} else {
					alert("请先选中要删除的记录。");
				}

			}
		
		});
	
	$.fn.dataTable.TableTools.buttons.reload = $
	.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {

					reload();

				}
			});

	$.fn.dataTable.TableTools.buttons.create = $
		.extend(
			true,
			{},
			$.fn.dataTable.TableTools.buttonBase,
			{
				"fnClick" : function(button) {
					
					layer.open({
							type : 2,
							title : false,
							area : [ '900px', '300px' ], 
							scrollbar : false,
							title : false,
							closeBtn: 1, //显示关闭按钮
							content : '${ctx}/area?methodtype=create',
						});
						
				}
			});
	
	function ajax() {

		var t = $('#example').DataTable(
		{
			"processing" : false,
			"serverSide" : true,
			"stateSave" : false,
			"searching" : true,
			"pagingType" : "full_numbers",
			"retrieve":true,
			"pageLength":300,
			"ajax" : {
				"url" : "${ctx}/area?methodtype=search",
				"type" : "POST",
				"data" : function(d) {//d 是原始的发送给服务器的数据，默认很长。
					//alert(888);
					var param = {};//因为服务端排序，可以新建一个参数对象
					param.draw = d.draw;
					param.start = d.start;//开始的序号
					param.length = d.length;//要取的数据的
					param.order = d.order;
					param.columns = d.columns;
					var formData = $("#condition")
							.serializeArray();//把form里面的数据序列化成数组/
					formData.forEach(function(e) {
						//alert(e.value);
						param[e.name] = e.value;
					});
					return param;//自定义需要传递的参数。
				},
				"cache" : false,
			},
			"language":{"url":"${ctx}/plugins/datatables/chinese.json"},

			dom : 'T<"clear">ti',

			"tableTools" : {

			"sSwfPath" : "${ctx}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

			"aButtons" : [										
						 {
							"sExtends" : "create",
							"sButtonText" : "新建"
						},
						 {
							"sExtends" : "Delete",
							"sButtonText" : "删除"
						},
				]
			},

			"columns" : [ {
					"data" : "recordId",
					"width" : '1px'
				},{
					"data" : null,
					"width" : '1px'
				}, {
					"data" : "province",
				}, {
					"data" : "city"
				}, {
					"data" : "cityCode",
				}, {
					"data" : "county"
				}, {
					"data" : "countyCode"
				}, {
					"data" : null
				}
			],

			"columnDefs" : [ 
	                 {
							"orderable" : false,
							"targets" : [0,1,2,3,4,5,6] 
						}, {
							"visible" : false,
							"targets" : [0,]
						},
			    		{"targets":7,"render":function(data, type, row){
			    			
			    			var rtn= "<a href=\"#\" onClick=\"doEdit('" + row["recordId"] + "')\">编辑</a>";
			    			return rtn;
			    		}}
	         	],
			
	         initComplete : function() {

					var i = 0;
					var arr_select = [3,];
					var arr_input = [];

					this
							.api()
							.columns()
							.every(
									function() {

										i++;

										if (arr_input.indexOf(i) == 0) {
											//alert(i);
											var title = "";
											//$(this).html( '<input type="text" placeholder="Search '+title+'" />' );	

											var column = this;
											var select = $(
													'<input type="text" />')
													.appendTo(
															$(column.footer()).empty())
													.on(
															'keyup change',
															function() {
																//alert(999);
																column.search(this.value).draw();
															});
										}

										if (arr_select.indexOf(i) == -1) {
											return;
										}

										var column = this;
										var select = $(
												'<select><option value=""></option></select>')
												.appendTo(
														$(
																column
																		.footer())
																.empty())
												.on(
														'change',
														function() {
															var val = $.fn.dataTable.util
																	.escapeRegex($(
																			this)
																			.val());

															column
																	.search(
																			val ? '^'
																					+ val
																					+ '$'
																					: '',
																			true,
																			false)
																	.draw();
														});

										column
												.data()
												.unique()
												.sort()
												.each(
														function(d,
																j) {
															if (d == null) {
																return
															}
															;
															select
																	.append('<option value="'+d+'">'
																			+ d
																			+ '</option>')
														});

									});
			},
			
		});

		//alert(JSON.stringify($("#info")
		//		.serializeObject()));

		t.on('order.dt search.dt draw.dt', function() {
			t.column(1, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();		
		
	};

	function reload() {
		$('#example').DataTable().ajax.reload();
	};
		
	function returnKey(){
		
		//alert(999);
		ajax();
		
		return false;
	}

	$(document).ready(function() {

		ajax();
		
		function initEvent(){
			
			var t = $('#example').DataTable();
			
			$('#example').DataTable().on('click', 'tr', function() {
				
				//$(this).toggleClass('selected');												
				if ( $(this).hasClass('selected') ) {												
		            $(this).removeClass('selected');														
		        }														
		        else {														
		        	$('#example').DataTable().$('tr.selected').removeClass('selected');														
		            $(this).addClass('selected');														
		        }				

			});

			$('#example').DataTable().on('dblclick', 'tr', function() {

				var d = $('#example').DataTable().row(this).data();
				
				layer.open({
						type : 2,
						title : false,
						area : [ '900px', '320px' ], 
						scrollbar : false,
						title : false,
						closeBtn: 0, //不显示关闭按钮
						content : '${pageContext.request.contextPath}/dict/area/edit/'+d["area_id"]+'.html',
					});
					
			});
			
		}
		
		initEvent();

		$("#retrieve").click(
				function() {		
					//alert(999);
					ajax();
		});
	});
	
	function doEdit(recordId) {

		var url = '${ctx}/area?methodtype=edit&recordId=' + recordId;

		layer.open({
			type : 2,
			title : false,
			area : [ '900px', '320px' ], 
			scrollbar : false,
			title : false,
			closeBtn: 1, //显示关闭按钮
			content : url,
		});
	}
</script>
</head>
<body>

	<div id="container">

		<!--主工作区,编辑页面或查询显示页面-->
		<div id="main">
		
			<!--查询条件输入区-->
			<div id="search">

				<form id="condition" 
					style='padding: 0px; margin: 10px;'  onsubmit="return returnKey();">

					<table class="search">
						<tr>
							<td class="label">查询关键字：</td> 
							<td class="condition"><input type="text" id="keyword1"
								name="keyword1" class="long"/>
							</td>
								
							<td>
								<button type="button" id="retrieve" class="DTTT_button" />
								查询</button>
							</td>
						</tr>
					</table>

				</form>
			</div>

			<!--查询结果显示-->
			<div class="list">

				<!--查询结果显示-->
				<table id="example" class="display" cellspacing="0" style="width:100%">
					<thead>
						<tr>
							<th></th>
							<th style="width: 0px"></th>
							<th class="dt-left">省份</th>
							<th class="dt-left">主城市</th>
							<th class="dt-left">主城市电话号码</th>							
							<th class="dt-left">所在市县</th>
							<th class="dt-left">所在市县简称</th>
							<th class="dt-left" style="width: 80px">操作</th>
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

				</table>
			</div>
		</div>
	</div>
</body>
</html>
