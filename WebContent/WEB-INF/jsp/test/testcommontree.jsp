<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/common.jsp"%>
<html>
<head>

</head>

<script>
	$(function(){
		setMenuId("001");
		setDeptObj("dept");
		loadData();
	}); 
	

function moveNode(source, target, node) { //建立节点的路径
        var pNode, pNodeData = [], pid;
        var children = source.tree('getChildren', node.target);
        pNode = source.tree('getParent', node.target); //收集父节点

        if (pNode == null && children.length == 0) {
	        pid = null;
	        pNodeData.push({ text: node.text, id: node.id });
        } else {
        	pid = pNode.id;
            do {
                if (pNodeData.length > 0) {
                	pNodeData[pNodeData.length - 1].pid = pNode.id; //更新上一个父节点的父节点id
                }
                pNodeData.push({ text: pNode.text, id: pNode.id });
            }
            while (pNode = source.tree('getParent', pNode.target));        	
        }
        
        //从根节点建立路径
        for (var i = pNodeData.length - 1; i >= 0; i--) {
            if (!target.tree('find', pNodeData[i].id)) {
				target.tree('append', { 
					parent: pNodeData[i].pid ? target.tree('find', pNodeData[i].pid).target : null, 
					data: { 
							text: pNodeData[i].text, 
							id: pNodeData[i].id
						   }
				});
            }
        }
        if (pid != null) {
	        target.tree('append', 
	        		{parent: target.tree('find', pid).target, 
	        	     data: { text: node.text, id: node.text} });
        } else {
        	
        }
    }
    function treeNodeSelect(cancel) {//将原树中数据移动到目标树中
    	
        var source = $(cancel ? '#naviTree' : '#deptTree1');
		var target = $(cancel ? '#deptTree1'  : '#naviTree');
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
    }
	
    $(document).ready(function() {
	    $('#deptTree2').tree({
	    		url:"${ctx}/mainFrame/allMenu",
	            loadFilter: function(rows){
	                return convert(rows);
	            }
	        });
    });
    
    
</script>

<body>

	<table>
		<tr>
			<td>
				代码名称：<input type=text/>
			</td>
		</tr>
		<tr>
			<td>
				上级代码：<input type=text/>
			</td>
		</tr>
		<tr>
			<td>
				组织机构：<input type=text id="dept" name="dept" readonly=true enabled=false/>
				<input type=button onclick="treeNodeSelect(true);" value="addNode"/>
				<input type=button onclick="treeNodeSelect(false);" value="removeNode"/>
			</td>
		</tr>
		
	</table>
	<%@ include file="../common/deptselector.jsp"%>
	<p>动态区域<p>
	<div class="easyui-panel" style="padding:5px;width:300px">
		 <ul id="deptTree1" class="easyui-tree" data-options="animate:false,border:false,checkbox:true"></ul>
		 <div id="warningMessage" style="display:none;"></div>
	</div>	
	
	<p>动态menu区域<p>
	<div class="easyui-panel" style="padding:5px;width:300px">
		 <ul id="deptTree2" class="easyui-tree" data-options="animate:false,border:false,checkbox:true"></ul>
		 <div id="warningMessage" style="display:none;"></div>
	</div>
</body>


</html>