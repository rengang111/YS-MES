<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>mainframe</title>
</head>
<%@ include file="../common/common.jsp"%>

<body class="easyui-layout">
    <div id="navi_div" data-options="region:'west',split:false,title:''" style="width:320px;padding:10px;overflow: auto;">
        <h2></h2>
        <div class="easyui-panel" style="padding:5px;width:100%;height:100%;" id="naviTreePanel">
        	<!-- 
            <ul id="naviTree" class="easyui-tree" data-options="animate:false,border:false,checkbox:true"></ul>
        	 -->
        	 <ul id="naviTree" class="easyui-tree" data-options="animate:false,border:false,checkbox:true"></ul>
        	 <ul id="naviTreeNoCheck" class="easyui-tree" data-options="animate:false,border:false,checkbox:false"></ul>
        	 <div id="warningMessage" style="display:none;"></div>
        	 
        </div>
    </div>
    <div id="main_div" data-options="region:'center',title:''" >
   	    <iframe name="mainFrame" id="mainFrame"  width="100%" height="99%" 
   	    frameborder="0" border=0 scrolling="auto" marginwidth="0" marginheight="0" src="#" >
   		</iframe>
    </div>
    </body>
  </html>

<script type="text/javascript" >
    //树形菜单所需要的基本数据。
    	var srcUrl = "";
    	//var NaviObjName = "";
    	var isClicked = 0;
    	var oldId = "";
    	var menuId = "";
    	var initNaviUrl = "";
    	var launchNaviUrl = "";
    	var clickNoCheckFlg = false;
		var naviObj;
    	
        //构造树
        $('#naviTree').tree({
        //$(window.frames["mainFrame"].document.getElementById("naviTree")).tree({
            loadFilter: function(rows){
                return convert(rows);
            },
            onClick: function(node){
				naviTreeClick(node);				
				return;
            }
        });

        //构造树
        $('#naviTreeNoCheck').tree({
        //$(window.frames["mainFrame"].document.getElementById("naviTree")).tree({
            loadFilter: function(rows){

            	//alert('naviTreeNoCheck')
       
                return convert2(rows);
            },
            onClick: function(node){
 
            	naviTreeClick(node);

				return;
            }
        });

        /*
         *前台构造树方法。
         *rows:树所需的基本数据。
         */
        function convert2(rows){
            function exists(rows, parentId){
                for(var i=0; i<rows.length; i++){
                    if (rows[i].id == parentId) return true;
                }
                return false;
            }

            var nodes = [];
            // get the top level nodes
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                if (!exists(rows, row.parentId)){
                    nodes.push(row);
                }
            }

            var toDo = [];
            for(var i=0; i<nodes.length; i++){
                toDo.push(nodes[i]);
            }
            
            while(toDo.length){
                var node = toDo.shift();	// the parent node
                // get the children nodes
                for(var i=0; i<rows.length; i++){
                    var row = rows[i];
                    if (row.parentId == node.id){
                        var child = row;
                        if (node.children){
                            node.children.push(child);
                        } else {
                            node.children = [child];
                        }
                        toDo.push(child);
                    }
                    
                }
                break;
            }
            
            return nodes;
        }// end or  convert(rows)

        
	function naviTreeClick(node) {
    	var count = 0;

    	if (node) {
            //if ($(node.target).hasClass("tree-hit")) {
	          // $(node.target).closest("div.tree-node");
           // }
	           // if (!node.length) {
	           //     return;
	           // }
	           // var x = node[0];
	           // node = node[0];
	           // node.target = x;
				children = naviObj.tree('getChildren', node.target);

		       //   alert(children.length)
				if (children.length == 0) {
					if (isClicked == 1) {
						isClicked = 0;
						return;
					} else {
						isClicked = 1;
					}	
					
					$.post(launchNaviUrl, {
			            "menuid" : menuId,
			            "id" : node.id
			        }, function(json) {
			  		    
			           
								naviObj.tree('append', { 
			                        parent : node.target,
			                        data : json
			                    });

	                            isClicked = 0;
			
			        }, "json");
					
				}else {
					$(naviObj).tree('toggle', node.target);
				}
				/*
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
							naviObj.tree('append', { 
		                        parent : node.target,
		                        data : json[i]
		                    });
		                }
		            }
		
		        }, "json");
		        if (count == 0) {
		        	$(naviObj).tree('toggle', node.target);
		        }
		        */

           // } else {
			//	alert('bbb')
            	noticeChange(node);

           // }
    	}
	}
        
	function setCheckBoxTrue(isCheckBox) {
		if (isCheckBox) {
			naviObj = $('#naviTree');
		} else {
			naviObj = $('#naviTreeNoCheck');
		}
	}
        
	function iFrameHeight() {
		/*
        var ifm = document.getElementById("mainFrame");
        var subWeb = document.frames ? document.frames["mainFrame"].document :
		ifm.contentDocument;
        if(ifm != null && subWeb != null) {
        	ifm.height = subWeb.body.scrollHeight-30;
        }
       */
	}

	function setClickNoCheckFlg(flg) {
		clickNoCheckFlg = false;
	}
	
	function noticeChange(node) {
		
		var isExecute = false;
		var value = node.id;
		if (clickNoCheckFlg == true) {
			isExecute = true;
		} else {
			if (value != oldId) {
				oldId = value;
				isExecute = true;
			}
		}
		if (isExecute) {
			children = $(naviObj).tree('getChildren', node.target);
			if (children.length == 0) {
				//$(window.frames["mainFrame"].document.getElementById(NaviObjName)).val(value);
				mainFrame.window.noticeNaviChanged(node.id, node.text, true);
			} else {
				mainFrame.window.noticeNaviChanged(node.id, node.text, false);
			}			
		}
	}
	
	function setMainFrameSrc(url) {
		document.getElementById("mainFrame").src = url;
	}
	
	//function setNaviObj(NaviObj) {
	//	NaviObjName = NaviObj;
	//}

	function setMenuId(callerMenuId) {
		menuId = callerMenuId;
	}

	function setInitNaviUrl(naviUrl) {
		if (naviUrl.indexOf('?') < 0) {
			initNaviUrl = "${ctx}/" + naviUrl + "?menuId=" + menuId;
		} else {
			initNaviUrl = "${ctx}/" + naviUrl + "&menuId=" + menuId;
		}
	}

	function setLaunchNaviUrl(naviUrl) {
		launchNaviUrl = "${ctx}/" + naviUrl;
	}
	
	function loadData() {
		$(naviObj).tree({
			url : initNaviUrl
		});
	}

	function addNode(parentNodeId, id, text, icon) {
		var node = $(naviObj).tree("find", parentNodeId);

		var newData = [{   
	        		"id":id,   
	        		"text":text,
	        		"iconCls":icon
	        		}];

		$(naviObj).tree("append", {parent:node?node.target:null, data:newData});

		naviTreeClick(node);
		if (node) {
			if (node.target) {
				$(naviObj).tree("expand", node.target);
			}
		}
	}
	
	function updateNode(nodeId, textVal, iconVal) {
		var node = $(naviObj).tree("find", nodeId);
		if (node) {
			node.text = textVal;
			node.iconCls = iconVal;
			$(naviObj).tree("update", node);
		}
	}
	
	function removeNode(nodeId) {
		var node = $(naviObj).tree('find', nodeId);
		if (node) {
			/*
			$(naviObj).tree('check', node.target);		
			var checked = $(naviObj).tree('getChecked');
	        for (var i = checked.length - 1; i >= 0; i--) {
				if (checked[i]) {
		            if (checked[i].id == nodeId) {
		            	$(naviObj).tree('remove', checked[i].target);
		            }
				}
	        }
	        */
			var children = $(naviObj).tree('getChildren', node.target);
	        for (var i = children.length - 1; i >= 0; i--) {
				if (children[i]) {
	            	$(naviObj).tree('remove', children[i].target);
				}
	        }
	        $(naviObj).tree('remove', node.target);
		}
        //naviTreeClick(node);
	}


</script>
