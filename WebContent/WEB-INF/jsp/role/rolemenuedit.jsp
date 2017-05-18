<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>

</head>

<script>
function moveNode(source, target, node) { //建立节点的路径
        var pNode, pNodeData = [], pid;
        var children = source.tree('getChildren', node.target);
        pNode = source.tree('getParent', node.target); //收集父节点

        var sortNo = node.attributes.sortNo;
        
        if (pNode == null && children.length == 0) {
	        pid = null;
	        
	        pNodeData.push({ text: node.text, id: node.id, attributes: {url:node.attributes.url, sortNo:node.attributes.sortNo}});
        } else {
        	pid = pNode.id;
            do {
                if (pNodeData.length > 0) {
                	pNodeData[pNodeData.length - 1].pid = pNode.id; //更新上一个父节点的父节点id
                }

                pNodeData.push({ text: pNode.text, id: pNode.id, attributes: {url:pNode.attributes.url, sortNo:pNode.attributes.sortNo}});
            }
            while (pNode = source.tree('getParent', pNode.target));        	
        }
        
        //从根节点建立路径
        for (var i = pNodeData.length - 1; i >= 0; i--) {
            if (!target.tree('find', pNodeData[i].id)) {

            	updateTree(pNodeData[i], source, target);
                /*
				target.tree('append', { 
					parent: pNodeData[i].pid ? target.tree('find', pNodeData[i].pid).target : null, 
					data: { 
							text: pNodeData[i].text, 
							id: pNodeData[i].id
						   }
				});
				*/
            }
        }
        if (pid != null) {
	        //target.tree('append', 
	        //		{parent: target.tree('find', pid).target, 
	        //	     data: { text: node.text, id: node.id} });
        	updateTree(node, source, target);
        } else {
        	
        }
    }
    function treeNodeSelect(cancel) {//将原树中数据移动到目标树中
        var source = $(cancel ? '#menuTreeAll' : '#menuTreeRole');
		var target = $(cancel ? '#menuTreeRole'  : '#menuTreeAll');
        var checked = source.tree('getChecked');
        if (checked.length == 0) {
			alert('请选择节点！');
			return false
		}

        for (var i = checked.length - 1; i >= 0; i--) {
            
            if (source.tree('isLeaf', checked[i].target)) {
            	//只针对叶子节点，父节点会自动通过getParent方法获取后自动建立
                moveNode(source, target, checked[i]);
            }
        }
        //移除节点
        for (var i = checked.length - 1; i >= 0; i--) {
        	source.tree('remove', checked[i].target);
        }
        
        isChanged = true;
    }

	function updateTree(node, source, target) {
		var children;
		var targetNode = null;

		pNode = source.tree('find', node.id);
		if (pNode) {
			var pNode = source.tree('getParent', pNode.target);
		}
		
		if (pNode) {
			pNode = target.tree('find', pNode.id);
		}

		if (pNode == null) {
			children = target.tree('getRoots');
		} else {
			children = target.tree('getChildren', pNode.target);
		}
		for(var i = 0; i < children.length; i++) {
			var srcSortNo = 0;
			var targetSortNo = 0;
			if (node.attributes.sortNo != '') {
				srcSortNo = parseInt(node.attributes.sortNo);
			}
			if (children[i].attributes.sortNo != '') {
				targetSortNo = parseInt(children[i].attributes.sortNo);
			}
			if (srcSortNo < targetSortNo) {
				target.tree('insert', { 
					before: children[i].target, 
					data: { 
							text: node.text, 
							id: node.id,
							attributes: {url:node.attributes.url, sortNo:node.attributes.sortNo}
						  }
				});
				break;
			}

		}
		if (i == children.length) {
			target.tree('append', { 
				parent: pNode ? pNode.target : null, 
				data: { 
						text: node.text, 
						id: node.id,
						attributes: {url:node.attributes.url, sortNo:node.attributes.sortNo}
					   }
			});		
		}
	}
	
    $(document).ready(function() {
    	noticeChanged();
    	
    	if ($('#roleIdName').val() == '') {
    		$('#roleIdName').focus();
	    } else {
	    	$('#roleIdName').attr('readonly', 'true');
	    }
    });
    
    
</script>

<body>
	<div id="container">
		<div id="main">
		<form name="form" id="form" modelAttribute="dataModels" action="" method="post">
			<input type=hidden id="roleId" name="roleId" value='${DisplayData.roleId}' />
			<input type=hidden name="workingRoleIdName" id="workingRoleIdName"  value='${DisplayData.roleIdName}'>
			<table>
				<tr>
					<td>
						角色名称：<input type=text name="roleIdName" id="roleIdName" value='${DisplayData.roleIdName}'/>
					</td>
					<td colspan=2>
						<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="updateMenuTree();"><span>更新</span></a>
						<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="doReturn();"><span>退出</span></a>
					</td>
				</tr>
				<tr>
					<td>
						可授权菜单：
					</td>
					<td>
					</td>
					<td>
						已授权菜单：
					</td>
				</tr>
				<tr>
					<td width=40%>
						<div class="easyui-panel" style="padding:5px;width:300px;height:400px">
							 <ul id="menuTreeAll" class="easyui-tree" data-options="animate:false,border:false,checkbox:true"></ul>
							 <div id="warningMessage" style="display:none;"></div>
						</div>
					</td>
					<td align="center">
						<div style="display:inline">
							<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="treeNodeSelect(true);"><span>增加-></span></a>
						</div>
						<div style="display:inline">
							<a aria-controls="TExternalSample" tabindex="0" id="ToolTables_TExternalSample_1" class="DTTT_button DTTT_button_text" onClick="treeNodeSelect(false);"><span><-删除</span></a>
						</div>
					</td>
					<td width=40%>
						<div class="easyui-panel" style="padding:5px;width:300px;height:400px">
							 <ul id="menuTreeRole" class="easyui-tree" data-options="animate:false,border:false,checkbox:true"></ul>
							 <div id="warningMessage" style="display:none;"></div>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
</body>
</html>
<script>
	var isChanged = false;
	
	$(document).ready(function() {
		autoComplete();
	})
	
	function autoComplete() {

		$("#roleIdName").autocomplete({
			source : function(request, response) {
				$.ajax({
					type : "POST",
					url : "${ctx}/rolemenu?methodtype=roleIdNameSearch",
					dataType : "json",
					data : {
						key : request.term
					},
					success : function(data) {
						response($.map(
							data.data,
							function(item) {
								return {
									label : item.RoleName,
									value : item.RoleName,
									id : item.roleid,
									name : item.RoleName,
								}
							}));
						datas = data.data;
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});
			},

			select : function(event, ui) {
				$("#workingRoleIdName").val(ui.item.name);
				$("#roleId").val(ui.item.id);
				noticeChanged();
			},

            change: function(event, ui) {
                // provide must match checking if what is in the input
                // is in the list of results. HACK!
                if(ui.item == null) {
                    $(this).val('');
    				$("#workingRoleIdName").val("");
    				$("#roleId").val("");
                }
            },
			
			minLength : 1,
			autoFocus : false,
			width: 200,
			mustMatch:true,
		});
	}
	
	function initMenu() {
		var root = $('#menuTreeAll').tree('getRoot');
		if (root) {
			$("#menuTreeAll").tree('remove', root.target); 
		}
	    $('#menuTreeAll').tree({
    		url:"${ctx}/mainframe/allMenu?minusflg=1&roleId=" + $('#roleIdName').val(),
            loadFilter: function(rows){
                return convert(rows);
            }
    	});
	}

	function selectRole() {
		var root = $('#menuTreeRole').tree('getRoot');
		if (root) {
			$("#menuTreeAll").tree('remove', root.target); 
		}  		
	    $('#menuTreeRole').tree({
    		url:"${ctx}/rolemenu?methodtype=getrolemenu&roleId=" + $('#roleIdName').val(),
            loadFilter: function(rows){
                return convert(rows);
            }
    	});
	}
	    
	function noticeChanged() {
		roleIdName = $('#roleIdName').val();
		roleId = $('#roleId').val();
		if ($('#roleIdName').val() != '') {
			roleIdNameArray = roleIdName.split(",");
			roleIdArray = roleId.split(",");

			if (roleIdNameArray.length > 1) {
				if (confirm("您选择了多个角色，只有第一个角色会被处理，要继续吗？")) {
					$('#roleIdName').val(roleIdNameArray[0]);
					$('#roleId').val(roleIdArray[0]);
				} else {
					return;
				}
			}
			
			var result = false;
			jQuery.ajax({
				type : 'POST',
				async: false,
				contentType : 'application/json',
				dataType : 'json',
				data : $('#roleIdName').val(),
				url : "${ctx}/rolemenu?methodtype=checkrolemenu",
				success : function(data) {

					if (!data.success) {
						$('#add').attr('disabled', true);
						$('#delete').attr('disabled', true);
						alert(data.message);
					} else {
						$('#add').attr('disabled', false);
						$('#delete').attr('disabled', false);
						$('#workingRoleIdName').val($('#roleIdName').val());
						result = true;
					}
				},
				 error:function(XMLHttpRequest, textStatus, errorThrown){
	                 alert(XMLHttpRequest.status);
	                 alert(XMLHttpRequest.readyState);
	                 alert(textStatus);
	             }
			}); 
			if (result) {
				initMenu();
				
				selectRole();
			}
			isChanged = false;
			
		} else {

		}
	}
	
	function updateMenuTree() {

		var nodeList = "";
		
		if (isChanged) {
			if ($('#workingRoleIdName').val() != $('#roleIdName').val()) {
				if (!confirm("角色名称(" + $('#roleIdName').val() + ")与检索时使用的角色名称(" + $('#workingRoleIdName').val() + ")不一致，将按照检索时使用的角色名称进行更新。确定继续吗？")) {
					return;
				}
			}
			if (confirm("确定更新数据库吗？")) {
		        var roots=$('#menuTreeRole').tree('getRoots');
		        for(i = 0; i < roots.length; i++){
		        	if (i == 0) {
		        		nodeList = roots[i].id;
			        } else {
			        	nodeList += "," + roots[i].id;
				    }
		        	children=$('#menuTreeRole').tree('getChildren',roots[i].target);
		        	for(j=0; j < children.length; j++) {
		        		nodeList += "," + children[j].id;	
		        	}
		       	}

				jQuery.ajax({
					type : 'POST',
					async: false,
					contentType : 'application/json',
					dataType : 'json',
					data : $('#workingRoleIdName').val() + "," + nodeList,
					url : "${ctx}/rolemenu?methodtype=update",
					success : function(data) {
						
						if (d.rtnCd != "000") {
							alert(d.message);	
						} else {
							parent.reload();
							doReturn();
						}

					},
					 error:function(XMLHttpRequest, textStatus, errorThrown){
		                 //alert(XMLHttpRequest.status);
		                 //alert(XMLHttpRequest.readyState);
		                 //alert(textStatus);
		             }
				}); 		       	
			}
		}
	}

	function doReturn() {

		var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		parent.layer.close(index); //执行关闭
		
	}

</script>