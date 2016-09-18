<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div>
		<h2>菜单选择</h2>
		<div class="easyui-panel" style="padding:5px;width:300px;height:400px;">
			<!-- 
		    <ul id="menuTree" class="easyui-tree" data-options="animate:false,border:false,checkbox:true"></ul>
			 -->
			 <ul id="menuTree" class="easyui-tree" data-options="animate:false,border:false,checkbox:true,cascadeCheck:false"></ul>
			 <ul id="menuTreeNoCheck" class="easyui-tree" data-options="animate:false,border:false,checkbox:false"></ul>
			 <div id="warningMessage" style="display:none;"></div>
		</div>
	</div>

<script type="text/javascript" >
    //树形菜单所需要的基本数据。
  	var naviObj;
  	var srcUrl = "";
  	var menuObjName = "";
  	var actionControlName = "";
  	var isClicked = 0;
  	var oldId = "";
  	var menuId = "";
  	var initDeptUrl = "${ctx}/mainframe/allMenu";
	var launchNaviUrl = "${ctx}/mainframe/launchMenu";

      //构造树
	$('#menuTree').tree({
		loadFilter: function(rows){
			return convert(rows);
		},
		onCheck: function(node, checked) {
			noticeChange(node, true);
		},
		onClick: function(node){
			var count = 0;
            if ($(node.target).hasClass("tree-hit")) {
	            node = $(node.target).closest("div.tree-node");
	            if (!node.length) {
	                return;
	            }
	            var x = node[0];
	            node = node[0];
	            node.target = x;
			
				children = $(naviObj).tree('getChildren', node.target);
				$.post(launchNaviUrl, {
						"menuid" : menuId,
						"id" : node.id
				}, function(json) {
	
					for(var i = 0; i < json.length; i++) {
						var isExisted = false;
						for(var j = 0; j < children.length; j++) {
							if (json[i].id == children[j].id) {
								isExisted = true;
								break;
	                      	}
						}
						if (!isExisted) {
							count++;
							$(naviObj).tree('append', { 
								parent : node.target,
								data : json[i]
							});
						}
					}
	                  //isClicked = 0;
				}, "json");
				if (count == 0) {
					$(this).tree('toggle', node.target);
				}
            }
            
			//noticeChange(node);

			return;
		}
	});

    //构造树
	$('#menuTreeNoCheck').tree({
		loadFilter: function(rows){
			return convert(rows);
		},
		onCheck: function(node, checked) {
			noticeChange(node, false);
		},
		onClick: function(node){
			var count = 0;

            if ($(node.target).hasClass("tree-hit")) {
	            node = $(node.target).closest("div.tree-node");
	            if (!node.length) {
	                return;
	            }
	            var x = node[0];
	            node = node[0];
	            node.target = x;

				children = $(naviObj).tree('getChildren', node.target);
				$.post(launchNaviUrl, {
						"menuid" : menuId,
						"id" : node.id
				}, function(json) {
	
					for(var i = 0; i < json.length; i++) {
						var isExisted = false;
						for(var j = 0; j < children.length; j++) {
							if (json[i].id == children[j].id) {
								isExisted = true;
								break;
	                      	}
						}
						if (!isExisted) {
							count++;
							$(naviObj).tree('append', { 
								parent : node.target,
								data : json[i]
							});
						}
					}
	                  //isClicked = 0;
				}, "json");
				if (count == 0) {
					$(this).tree('toggle', node.target);
				}
            }
            
			noticeChange(node);

			return;
		}
	});
    
   	function setCheckBoxTrue(isCheckBox) {
   		if (isCheckBox) {
   			naviObj = $('#menuTree');
   		} else {
   			naviObj = $('#menuTreeNoCheck');
   		}
   	}
    	
	function noticeChange(node, isCheckBoxFlag) {
		var value = "";
		if (isCheckBoxFlag) {
			value = getChecked();
		} else {
			value = getSelected();
		}

		//var value = node.id;
		if (value != oldId) {
			oldId = value;
			/*
			if (menuObjName != null && menuObjName != "") {
				//alert(menuObjName);
				window.opener.document.getElementById(menuObjName).value = value;
				//alert(window.opener.document.getElementById(menuObjName).value);
			}
			if (actionControlName != null && actionControlName != "") {
				window.opener.document.getElementById(actionControlName).click();
			}
			*/
			if (!isCheckBoxFlag) {
				closeWindow();
			}
		}
	}

	function setInitDeptUrl(userInitDeptUrl) {
		if(userInitDeptUrl != '') {
			initDeptUrl = userInitDeptUrl;
		} else {
			initDeptUrl = "${ctx}/mainframe/initDept?menuId=";
		}
	}

	function setLaunchNaviUrl(userLaunchNaviUrl) {
		if (userLaunchNaviUrl != '') {
			launchNaviUrl = userLaunchNaviUrl;
		} else {
			launchNaviUrl = "${ctx}/mainframe/launchDept";
		}
	}
	
	function setMenuObj(menuObj) {
		menuObjName = menuObj;
	}

	function setActionControlName(actionControl) {
		actionControlName = actionControl;
	}
	
	function setMenuId(callerMenuId) {
		menuId = callerMenuId;
		initDeptUrl = initDeptUrl + menuId;
	}
	function loadData() {
		$(naviObj).tree({
			url : initDeptUrl
		});
	}
	function getSelected() {
		var rtnValue = "";
		var node = $(naviObj).tree('getSelected');
		if (node) {
			rtnValue = node.text;
		}

		return rtnValue;
	}
	//checkbox 多选单选
	function getChecked(){
	    var nodes = $(naviObj).tree('getChecked');
	    var s = '';
	    for(var i=0; i<nodes.length; i++){
	        if (s != '') s += ',';
	        s += nodes[i].text;
	    }
	    return s;
	}
	function getCheckedId(){
	    var nodes = $(naviObj).tree('getChecked');
	    var s = '';
	    for(var i=0; i<nodes.length; i++){
	        if (s != '') s += ',';
	        s += nodes[i].id;
	    }
	    return s;
	}
	function isTreeEmpty() {
		
		var nodes = $(naviObj).tree('getRoots');
		if (nodes) {
			if (nodes.length > 0) {
				return false;
			}
		}

		return true;
	}
</script>
