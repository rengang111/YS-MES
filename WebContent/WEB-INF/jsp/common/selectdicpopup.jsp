<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<%@ include file="../common/common.jsp"%>
<head>
	代码选择
</head>
<body>
	<div>
		<h2>代码选择</h2>
		<form id="form" modelAttribute="dataModel" action="" method="post">
		<input type="hidden" name="dicControl" id="dicControl" value="${DisplayData.dicControl}" />
		<input type="hidden" name="dicControlView" id="dicControlView" value="${DisplayData.dicControlView}" />
		<input type="hidden" name="treeType" id="treeType" value="${DisplayData.treeType}" />
		<table>
			<tr>
				<td>
					代码类型：<input type=text name="dicTypeID" id="dicTypeID" value="${DisplayData.dicTypeID}"/>
				</td>
			</tr>
			<tr>
				<td>
					代码名称：<input type=text name="dicCodeName" id="dicCodeName" value="${DisplayData.dicCodeName}"/>
				</td>
			</tr>
			<tr>
				<td>
					上级代码：<input type=text name="dicParentCodeName" id="dicParentCodeName" value="${DisplayData.dicParentCodeName}"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type=button value="查询" onClick="doSearch();"/>
					<input type=button value="选择" onClick="doSelect();"/>
				</td>
			</tr>
		</table>
		</form>		
		<div class="easyui-panel" style="padding:5px;width:300px">
			 <ul id="dicTreeNoCheck" class="easyui-tree" data-options="animate:false,border:false,checkbox:false"></ul>
			 <ul id="dicTree" class="easyui-tree" data-options="animate:false,border:false,checkbox:true"></ul>
			 <div id="warningMessage" style="display:none;"></div>
		</div>
	</div>

<script type="text/javascript" >

	var dicTypeId = "";
	var dicName = "";
	var parentDicId = "";
	var objName = "";
	var dicObj;
	var addressName = "";
	var addressCode = "";
	
	$(function(){

		//0:单选		1:单选，需要级联数据
		if ('${DisplayData.treeType}' == '0' || '${DisplayData.treeType}' == '1') {
			dicObj = $('#dicTreeNoCheck');
		}
		//2:checkbox
		if ('${DisplayData.treeType}' == '2') {
			dicObj = $('#dicTree');
		}
				
		if ("$('#dicTypeID').val()" != "") {
			doSearch();
		}

	});

    //构造树
       //构造树
        $('#dicTree').tree({
        //$(window.frames["mainFrame"].document.getElementById("naviTree")).tree({
            loadFilter: function(rows){
                return convert(rows);
            },
            onClick: function(node){
            	
				return;
            }
        });

        //构造树
        $('#dicTreeNoCheck').tree({
        //$(window.frames["mainFrame"].document.getElementById("naviTree")).tree({
            loadFilter: function(rows){
                return convert(rows);
            },
            onClick: function(node){
            	doSelect();
				return;
            }
        });
	
	function doSearch() {
		var dicUrl = "${ctx}/common/selectDicPopActionSearch?dicTypeID=" + $('#dicTypeID').val() + "&dicCodeName=" + $('#dicCodeName').val() + "&dicParentCodeName=" + $('#dicParentCodeName').val();
		$(dicObj).tree({
			url : dicUrl
		});
	}

	function doSelect() {

		if(window.opener) {

			
			if ('${DisplayData.treeType}' == '2') {
				var nodes = $(dicObj).tree('getChecked');
				if (nodes) {
	
					for(var i = 0; i < nodes.length; i++) {
						if (i == 0) {
							addressName += nodes[i].text;
						} else {
							addressName += nodes[i].text;
						}
						if (i == 0) {
							addressCode += nodes[i].id;
						} else {
							addressCode += "-" + nodes[i].id;
						}					
					}
				}
			} else {
				var node = $(dicObj).tree('getSelected');

				if ('${DisplayData.treeType}' == '0') {
					if (node) {
						addressCode = node.id;
						addressName = node.text;
					}
				} else {
					if (node) {
						addressCode = node.id;
						addressName = node.text;

						getChain(node);

						
					}
				}
			}
			if (addressCode != "") {
				window.opener.document.getElementById("${DisplayData.dicControl}").value = addressCode;
				window.opener.document.getElementById("${DisplayData.dicControlView}").value = addressName;
				self.opener = null;
				self.close();
			} else {
				self.opener = null;
				self.close();
			}			
		}
	}

	function getChain(node) {
		var parentNode = $(dicObj).tree('getParent', node.target);
		if (parentNode) {
			addressName = parentNode.text + addressName;
			addressCode = parentNode.id + "-" + addressCode;

			getChain(parentNode);
		}
	}


</script>
</body>