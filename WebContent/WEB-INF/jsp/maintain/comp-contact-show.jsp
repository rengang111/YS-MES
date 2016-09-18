<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
	
<style type="text/css">
td.details-control {
	background:
		url('${pageContext.request.contextPath}/plugins/datatables/images/details_open.png')
		no-repeat center center;
	cursor: pointer;
}

tr.shown td.details-control {
	background:
		url('${pageContext.request.contextPath}/plugins/datatables/images/details_close.png')
		no-repeat center center;
}
</style>

<script type="text/javascript">

/* Formatting function for row details - modify as you need */
function format ( d ) {
    // `d` is the original data object for the row
    return '<table cellpadding="8" cellspacing="0" border="0" style="padding-left:50px;">'+
	    '<tr>'+
		    '<td>Skype:</td>'+
		    '<td>'+d.skype+'</td>'+
		'</tr>'+
        '<tr>'+
            '<td>备注:</td>'+
            '<td>'+d.memo+'</td>'+
        '</tr>'+        
    '</table>';
}
	
	$.fn.dataTable.TableTools.buttons.Delete = $
			.extend(
					true,
					{},
					$.fn.dataTable.TableTools.buttonBase,
					{
						"fnClick" : function(button) {

							var str_temp = "";

							$('#factory_contact').DataTable().rows('.selected').data()
									.each(function(data) {
										str_temp += data["contact_id"] + ",";
									});

							var str = str_temp
									.substring(0, str_temp.length - 1);

							if (str != "") {
								if (confirm("${msg}") == false) {
									return;
								}

								$.ajax({
									type : "GET",
									url : "${pageContext.request.contextPath}/factoryContact/remove.html?boardIds=" + str,
									data : null,// 要提交的表单
									success : function(
											d) {													
										//alert("success");

										var retValue = d['retValue'];

										//alert(retValue);

										if (retValue == "failure") {	
											//操作中发生错误，请重试或者联系管理员。
											alert("${err001}");																		
										}
										
										$('#factory_contact').DataTable().ajax.reload();
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
										//alert(XMLHttpRequest.status);
										//alert(XMLHttpRequest.readyState);
										//alert(textStatus);
										//alert(errorThrown);
										alert("操作失败，请再试或和系统管理员联系。");
									}
								});
								
							} else {
								alert("请先选中要删除的记录。");
							}

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
										area : [ '900px', '340px' ], 
										scrollbar : false,
										title : false,
										closeBtn: 0, //不显示关闭按钮
										content : '${pageContext.request.contextPath}/factoryContact/create/'+'${factory.factory_id}'+'.html',
									});
						}
					});

	function ajax() {

		//alert("${factory.factory_id}");
		
		var t = $('#factory_contact')
				.DataTable(
						{
							"processing" : false,
							"serverSide" : true,
							"stateSave" : false,
							"searching" : true,
							"pagingType" : "full_numbers",
							"retrieve":true,
							"ajax" : {
								"url" : "${pageContext.request.contextPath}/factoryContact/retrieve.html",
								"type" : "POST",
								"data" : function(d) {//d 是原始的发送给服务器的数据，默认很长。
									//alert(888);
									var param = {};//因为服务端排序，可以新建一个参数对象
									param.factoryId = "${factory.factory_id}";
									
									return param;//自定义需要传递的参数。
								},
								"cache" : false,
							},
							"language":{
								"url":"${pageContext.request.contextPath}/plugins/datatables/chinese.json"
								},

							dom : 'T<"clear">rt',

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
								"className" : 'details-control',
								"orderable" : false,
								"data" : null,
								"defaultContent" : '',
								"width" : "1px"
							}, {
								"data" : "contact_id",
								"width" : '1px'
							}, {
								"data" : null
							}, {
								"data" : "name"
							}, {
								"data" : "gender",
								"width" : '50px'
							}, {
								"data" : "department"
							}, {
								"data" : "position"
							}, {
								"data" : "mobile"
							}, {
								"data" : "phone"
							}, {
								"data" : "fax"
							}, {
								"data" : "Email"
							}, {
								"data" : "qq"
							}, {
								"data" : "primary_flag",
								"width" : '70px'
							}
						
							],

							"columnDefs" : [ 
							    {					                
					                "render": function ( data, type, row ) {
					                	if (data == '1'){
					                		return "是";
					                	}else{
					                		return "";
					                	}
				                },
				                	"targets": 12
					            },{					                
					                "render": function ( data, type, row ) {
					                	return '<a href="mailto:' + data + '">' + data + '</a>';
				                },
				                	"targets": 10
					            },
								{
									"orderable" : false,
									"targets" : [ 0, 1, 2,3,4,5,6,7,8,9,10,11,12] 
								}, {
									"visible" : false,
									"targets" : [ 1 ]
								} ],
							
						});

		//alert(JSON.stringify($("#info")
		//		.serializeObject()));

		t.on('click', 'tr', function() {
			$(this).toggleClass('selected');
		});

		t.on('dblclick', 'tr', function() {

			var d = t.row(this).data();

			//alert(d["factoryContactId"]);

			layer.open({
				type : 2,
				title : false,
				area : [ '900px', '340px' ],
				scrollbar : false,
				title : false,
				closeBtn: 1, //显示关闭按钮
				content : '${pageContext.request.contextPath}/factoryContact/edit/' + d["contact_id"] + '.html'
			});
		});

		t.on('order.dt search.dt draw.dt', function() {
			t.column(2, {
				search : 'applied',
				order : 'applied'
			}).nodes().each(function(cell, i) {
				cell.innerHTML = t.page()*t.page.len() + i + 1;
			});
		}).draw();
		
		// Add event listener for opening and closing details
		t.on('click', 'td.details-control', function() {

			var tr = $(this).closest('tr');
			t
			var row = t.row(tr);
			t

			if (row.child.isShown()) {
				// This row is already open - close it
				row.child.hide();
				tr.removeClass('shown');
			} else {
				// Open this row
				row.child(format(row.data())).show();
				tr.addClass('shown');
			}
		});

	};

	function reload_factory_contact() {
		$('#factory_contact').DataTable().ajax.reload();
	};

	$(document).ready(function() {

		ajax();

		$("#retrieve").click(
				function() {
					
					//alert(999);					
					ajax();

				});

	});
</script>


			<!--查询结果显示-->
			<div class="list"  width="80%">
			
				<span class="tablename">工厂联系人</span>

				<!--查询结果显示-->
				<table id="factory_contact" class="display" cellspacing="0" width="100%" >
					<thead>
						<tr>						
							<th></th>
							<th style="width: 0px"></th>
							<th style="width: 1px"></th>
							<th class="dt-left">姓名</th>
							<th class="dt-left">性别</th>
							<th class="dt-left">部门</th>
							<th class="dt-left">职位</th>
							<th class="dt-left">手机 </th>
							<th class="dt-left">座机</th>
							<th class="dt-left">传真</th>
							<th class="dt-left">邮箱</th>
							<th class="dt-left">QQ </th>
							<th class="dt-left">主联系人</th>
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
							<th></th>
							<th></th>
						</tr>
					</tfoot>

				</table>
			</div>
			