<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<%@ include file="../common/common.jsp"%>
<head>
	代码选择
</head>
<body>
	<div>
		<form id="form" modelAttribute="dataModel" action="" method="post">
		<input type="hidden" name="dicControl" id="dicControl" value="${DisplayData.dicControl}" />
		<input type="hidden" name="dicControlView" id="dicControlView" value="${DisplayData.dicControlView}" />
		<input type="hidden" name="treeType" id="treeType" value="${DisplayData.treeType}" />
		<input type="hidden" name="index" id="index" value="${DisplayData.index}" />
		<table>
			<tr>
				<td>
					<button type="button" id="close" class="DTTT_button" onClick="doSelect();"
									style="height:25px;margin:0px 5px 0px 0px;" >选择</button>
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
	var dicTypeName = "";
	var dicTypeCode = "";
	
	$(function(){

		//0:单选		1:单选，需要级联数据
		if ('${DisplayData.treeType}' == '0' || '${DisplayData.treeType}' == '1') {
			dicObj = $('#dicTreeNoCheck');
		}
		//2:checkbox
		if ('${DisplayData.treeType}' == '2') {
			dicObj = $('#dicTree');
		}

		doSearch();

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
		var dicUrl = "${ctx}/common/selectDicTypePopActionSearch?type=" + "${DisplayData.type}" + "&dicTypeId=" + "${DisplayData.dicTypeId}";
		$(dicObj).tree({
			url : dicUrl
		});
	}

	function doSelect() {

			var dicTypeCode = "";
			var dicTypeName = "";
			
			if ('${DisplayData.treeType}' == '2') {
				var nodes = $(dicObj).tree('getChecked');
				if (nodes) {
	
					for(var i = 0; i < nodes.length; i++) {
						if (i == 0) {
							dicTypeName += nodes[i].text;
						} else {
							dicTypeName += nodes[i].text;
						}
						if (i == 0) {
							dicTypeCode += nodes[i].id;
						} else {
							dicTypeCode += "-" + nodes[i].id;
						}					
					}
				}
			} else {
				var node = $(dicObj).tree('getSelected');

				if ('${DisplayData.treeType}' == '0') {
					if (node) {
						dicTypeCode = node.id;
						dicTypeName = node.text;
					}
				} else {
					if (node) {
						dicTypeCode = node.id;
						dicTypeName = node.text;

						getChain(node);

						
					}
				}
			}

			if (dicTypeCode != "") {
				if ($("#index").val() != "") {
					var body = parent.layer.getChildFrame('body', $("#index").val());
					var inputList = body.find('input');
		            for(var j = 0; j< inputList.length; j++){
		                if ("${DisplayData.dicControl}" == $(inputList[j]).attr('name')) {
		                	$(inputList[j]).val(dicTypeCode);
		                }
		                if ("${DisplayData.dicControlView}" == $(inputList[j]).attr('name')) {
		                	$(inputList[j]).val(dicTypeName);
		                }
	
		            }				
				} else {
					var curTab = parent.parent.$('#_main_center_tabs').tabs('getSelected');
			        if (curTab && curTab.find('iframe').length > 0) {
			            curTabWin = curTab.find('iframe')[0].contentWindow;
			        }
			        curTabWin.setDicTypeInfo(dicTypeName, dicTypeCode);
				}
				
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				parent.layer.close(index); //执行关闭
				
			} else {
				var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
				parent.layer.close(index); //执行关闭
			}			

	}

	function getChain(node) {
		var parentNode = $(dicObj).tree('getParent', node.target);
		if (parentNode) {
			dicTypeName = parentNode.text + dicTypeName;
			dicTypeCode = parentNode.id + "-" + dicTypeCode;

			getChain(parentNode);
		}
	}

	function test() {
		alert(123);
	}

</script>
</body>