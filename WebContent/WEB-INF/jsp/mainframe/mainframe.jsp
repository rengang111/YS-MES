<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="../common/common.jsp"%>



<body class="easyui-layout">

	<div data-options="region:'north',border:false">

		<div style="height:50px;line-height: 50px;font-size: 25px;float: left">
			<table>
			<tr>
				<td>
					<img src="${ctx}/img/log.png" height="44" />
				</td>
				<td>
					<img id="headphoto" src="${ctx}${sessionScope.userinfo.photo}" height="44" width="44" style="cursor:pointer;"/>
				</td>
				<td>
					${sessionScope.userinfo.userName}
				</td>				
			</tr>
			</table>
		</div>
		<div style="float: right;padding: 12px;">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-resetpwd'" style="width:80px" onClick="resetPwd();">密码重置</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-quit'" style="width:80px" onClick="quit();">退出</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help'" style="width:80px">帮助</a>
		</div>
	</div>

	<div id="center_div" data-options="region:'west',split:true,title:'树形菜单'" style="width:250px;padding:10px;">
		<ul id="menuTree" data-options="animate:false,border:false"></ul>
	</div>
	
	<div data-options="region:'center',title:''">
		<div id = "_main_center_tabs" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true">
			<div title="首页" data-options ="iconCls:'icon-home_nav',fit:true">
				<div style="padding: 10px  30px">
					通知信息：
					<input type=button onClick="openLayer();"/>
				</div>
			</div>
		</div>
	</div>	

	<!--右键tab页菜单-->
	<div id="mm" class="easyui-menu" style="width:150px;">
        <div id="mm-tabclose"  data-options ="iconCls:'icon-cancel'">关闭</div>
        <div id="mm-tabcloseall" data-options ="iconCls:'icon-left-right'">全部关闭</div>
        <div id="mm-tabcloseother" data-options ="iconCls:'icon-close_orthers'">除此之外全部关闭</div>
        <div class="menu-sep"></div>
        <div id="mm-tabcloseright" data-options ="iconCls:'icon-right'">当前页右侧全部关闭</div>
        <div id="mm-tabcloseleft" data-options ="iconCls:'icon-left'">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
        <div id="mm-update"  data-options ="iconCls:'icon-reload'">刷新</div>
	</div>
	<script type="text/javascript" >
		var isClicked = 0;
	    $(function() {
	        $(document).ready(function() {
	        	$('#menuTree').tree({
	                url: "${ctx}/mainframe/initmenu",  
	                loadFilter: function(data){
	                    return convert(data);        
	                },
	                onClick : function(node) {
						//点击打开窗口
						if(undefined != node.attributes && node.attributes.url != ''){
							var url = "${ctx}" + node.attributes.url;
							var title = node.text;
							var icon = node.iconCls; 
							openTab('#_main_center_tabs', title, url, icon);
						} else {
							children = $('#menuTree').tree('getChildren', node.target);
							if (children.length == 0) {
								if (isClicked == 1) {
									isClicked = 0;
									return;
								} else {
									isClicked = 1;
								}	
		                        $.post("${ctx}/mainframe/launchMenu", {
		            	            "menuid" : "",
		            	            "id" : node.id
		                        }, function(json) {
		                            $('#menuTree').tree('append', { 
		                                parent : node.target,
		                                data : json
		                            });
		                            isClicked = 0;
		                        }, "json");
		                        
		                   
							} else {
								$(this).tree('toggle', node.target);
							}
						}
						return;
	                }
	            }); 
	        }); 
	    }); 
	    
	    function quit() {
			if (confirm("确认退出 吗？")) {
				jQuery.ajax({
					type : 'POST',
					contentType : 'application/json',
					dataType : 'text',
					data : $('#rolename').val() + '&' + $('#roleid').val(),
					url : "${ctx}/mainframe/quit",
					success : function(data) {
						window.location.href = '${ctx}';
					}
				});
			}
		}

	    function resetPwd() {
	    	popupWindow("resetpassword", "${ctx}/user?methodtype=resetpwdinit", 800, 600);
	    	
		}
	    
	    function openLayer() {
	    	
			layer
			.open({
				type : 2,
				title : false,
				area : [ '900px', '420px' ], 
				scrollbar : false,
				title : false,
				content : '${pageContext.request.contextPath}/classProduct/createSubClass.html'
			});
	    }
	</script>
</body>
</html>