<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
<head>
<title>业务字典-部门</title>
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
							
							alert("删除功能将来会有权限控制，只能管理员使用！不在使用的条目可以通过编辑状态进行控制。");

							var str_temp = "";

							$('#example').DataTable().rows('.selected').data()
									.each(function(data) {
										str_temp += data["dict_id"] + ",";
									});

							var str = str_temp
									.substring(0, str_temp.length - 1);

							if (str != "") {
								
								if (confirm("${msg}") == false) {
									return;
								}
																							
								$
								.ajax({
									type : "GET",
									url : "${pageContext.request.contextPath}/dict/standard/remove.html?boardIds="+ str+"&category=department",
									data : null,// 要提交的表单
									success : function(
											d) {
										
										//alert("success");

										var retValue = d['retValue'];

										//alert(retValue);

										if (retValue == "failure") {																		
											alert("${err001}");																		
										}
										
										reload();
									},
									error : function(
											XMLHttpRequest,
											textStatus,
											errorThrown) {
										
										//alert(XMLHttpRequest.status);
										//alert(XMLHttpRequest.readyState);
										//alert(textStatus);
										//alert(errorThrown);
										
										alert("${err002}");
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
							
							layer
								.open({
									type : 2,
									title : false,
									area : [ '900px', '272px' ], 
									scrollbar : false,
									title : false,
									content : '${pageContext.request.contextPath}/dict/standard/department/create.html',
								});
								
						}
					});
	function ajax() {

		var t = $('#example')
				.DataTable(
						{
							"processing" : false,
							"serverSide" : true,
							"stateSave" : false,
							"searching" : false,
							"pagingType" : "full_numbers",
							"retrieve":true,
							"ajax" : {
								"url" : "${pageContext.request.contextPath}/dict/standard/retrieve.html",
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
							"language":{
								"url":"${pageContext.request.contextPath}/plugins/datatables/chinese.json"
								},

							dom : 'T<"clear">ti',

							"tableTools" : {

								"sSwfPath" : "${pageContext.request.contextPath}/plugins/datatablesTools/swf/copy_csv_xls_pdf.swf",

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
								"data" : "dict_id",
								"width" : '1px'
							},{
								"data" : null,
								"width" : '1px'
							}, {
								"data" : "name_chn",
								"width" : '300px'
							}, {
								"data" : "name_eng"
							}, {
								"data" : "status_value",
								"width" : '100px'
							}, {
								"data" : "desc_chn"
							}, {
								"data" : "desc_eng"
							}
							],

							"columnDefs" : [ 
					                 {
								"orderable" : false,
								"targets" : [0,1,2,3,4,5,6] 
							}, {
								"visible" : false,
								"targets" : [0,3,6]
							},{  
					            // 定义操作列  
					            "targets": 7,  
					            "data": null,  
					            "width" : '50px',
					            "render": function(data, type, row) {  
					            	
					            	//alert(row['status_value']);
					            	var html = '';
					            	
					            	if (row['status_value']=="正常"){
					            		html += ' <a href="javascript:void(0);" class="up "><img src="${pageContext.request.contextPath}/css/images/arrow_top.png" /></a>';
						                html += ' <a href="javascript:void(0);" class="down "><img src="${pageContext.request.contextPath}/css/images/arrow_down.png" /></a>';  
					            	}	
					            	
					                return html;  
					                
					            }  
					        } ],
							
							initComplete : function() {

								var i = 0;
								var arr_select = [5];
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
																		$(
																				column
																						.footer())
																				.empty())
																.on(
																		'keyup change',
																		function() {
																			//alert(999);
																			column
																					.search(
																							this.value)
																					.draw();
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

				var d = t.row(this).data();
				
				layer
					.open({
						type : 2,
						title : false,
						area : [ '900px', '305px' ], 
						scrollbar : false,
						title : false,
						content : '${pageContext.request.contextPath}/dict/standard/department/edit/'+d["dict_id"]+'.html',
					});
					
			});
			
			// 初始化上升按钮  
		    t.on('click', 'a.up', function(e) {  
		    	//alert(999);
		        e.preventDefault();  
		       
		        var index = t.row($(this).parents('tr')).index();  
		        //alert(index);
		        
		        var d = t.row(index).data();
				var dictId =  d["dict_id"];		
		        
		        if ((index - 1) >= 0) {
					
					$
					.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/dict/standard/updateOrder.html?dictId="+ dictId +"&order=Up",
						data : null,// 要提交的表单
						success : function(
								d) {
							
							//alert("success");

							var retValue = d['retValue'];

							//alert(retValue);

							if (retValue == "failure") {																		
								alert("${err001}");																		
							}
							
							reload();
						},
						error : function(
								XMLHttpRequest,
								textStatus,
								errorThrown) {
							
							//alert(XMLHttpRequest.status);
							//alert(XMLHttpRequest.readyState);
							//alert(textStatus);
							//alert(errorThrown);
							
							alert("${err002}");
							reload();
						}
					});
				} else {
					alert("已经到顶了!");
				}
		  
		    }); 
			
		 // 初始化下降按钮  
		    t.on('click', 'a.down', function(e) {
		    	//alert(666);
		        e.preventDefault();  
		       
		        var index = t.row($(this).parents('tr')).index();
		        var d = t.row(index).data();
				var dictId =  d["dict_id"];	
				//alert(dictId);
				
				//var max = t.rows().data().length;
				var max = 0;			
				var i = 0;
										
				t.rows().data().each( function (index) {			
					
					if(t.row(i).data()["status_value"]=="正常"){
						max++;
					};
					i++;
					
				} );
		        
		        if ((index + 1) < max) {
					
					$
					.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/dict/standard/updateOrder.html?dictId="+ dictId +"&order=Down",
						data : null,// 要提交的表单
						success : function(
								d) {
							
							//alert("success");

							var retValue = d['retValue'];

							//alert(retValue);

							if (retValue == "failure") {																		
								alert("${err001}");																		
							}
							
							reload();
						},
						error : function(
								XMLHttpRequest,
								textStatus,
								errorThrown) {
							
							//alert(XMLHttpRequest.status);
							//alert(XMLHttpRequest.readyState);
							//alert(textStatus);
							//alert(errorThrown);
							
							alert("${err002}");
							reload();
						}
					});
				} else {
					alert("已经到底了!");
				}
		  
		    }); 
		}
		
		initEvent();

		$("#retrieve").click(
				function() {
					alert("search");			
					ajax();
				});

	});
</script>
</head>
<body>



	<div id="container">

		<!--主工作区,编辑页面或查询显示页面-->
		<div id="main">
		
			<!--查询条件输入区-->
			<div id="search">

				<form id="condition" 
					style='padding: 0px; margin: 10px;'>

					<table class="search">
						<tr>
							<td class="label">查询关键字：</td> 
							<td class="condition"><input type="text" id="keywords"
								name="keywords" class="long"/>
							</td>
								<input type="hidden" id="category" name="category" value="department"/>
							<td>
								<button type="button" id="retrieve" class="DTTT_button" />
								查询</>
							</td>
						</tr>
					</table>

				</form>
			</div>

			<!--查询结果显示-->
			<div class="list">

				<!--查询结果显示-->
				<table id="example" class="display" cellspacing="0" >
					<thead>
						<tr>
							<th></th>
							<th style="width: 0px"></th>
							<th class="dt-left">部门</th>
							<th></th>
							<th class="dt-left">状态</th>							
							<th class="dt-left">说明</th>
							<th></th>
							<th class="dt-left">排序</th>
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
