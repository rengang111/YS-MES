<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript" src="${ctx}/jsp/ckfinder/ckfinder.js"></script>

<script type="text/javascript">
	var fileBrowserCount = 1;
	var api = new Array();
	var customFolder = new Array();
    var finder = new Array();//new CKFinder();
    var userFolder = "${userFolder}";
    if (userFolder == "") {
    	userFolder = "//";
    }
    
	//resetFinder();
	
    //doRefreshFileBrowser();
  	//api = finder.create();
	/*    	
    function doReturn() {
       	//var folder = api.getSelectedFolder();
       	//if ( folder )
       	//	alert( folder.type );   	
    	
    	//api.openFolder('Files', '/123/');
    	
    	//var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    	//parent.layer.close(index); //执行关闭
    }
	*/
	function resetFinder(type, typeNum) {
		
		if (type == 0) {
			fileBrowserCount = typeNum;	
			for(var i = 0; i < fileBrowserCount; i++) {
				resetParam(i);
			}
		} else {
			resetParam(typeNum);
		}
		

	}
	
	function resetParam(index) {
		finder[index] = new CKFinder();
		finder[index].id = "finder-" + index;
	    finder[index].disableHelpButton = true;
	   	finder[index].startupPath = "Files:" + userFolder;
	    finder[index].callback = function( api ) {
			//TODO
	    };
	    finder[index].selectActionFunction = function( fileUrl, data ) {
	    	
	    	//var folder = api.getSelectedFolder();
	    	//if ( folder ) {
	    		//alert( folder.getUrl() );
	    	//}
	    	
	    	openFileEditor(fileUrl, index);
	    	// Using CKFinderAPI to show simple dialog.
	    	//this.openMsgDialog( '', 'Additional data: ' + data['selectActionData'] );
	    }
		
	}
	
	function doRefreshFileBrowser(elementId, id, customFolder) {
		
    	var key = $('#keyBackup').val();
    	var tabTitle = getTabTitle();
    	var url = "${ctx}/business/externalsample?methodtype=openfilebrowser&key=" + key + "&tabTitle=" + tabTitle + "&id=" + id + "&customFolder=" + customFolder;
    	
		$.ajax({
			type : "POST",
			url : url,
			data : "",// 要提交的表单
			success : function() {
				
			}
		});
		
		finder[id] = new CKFinder();
		customFolder[id] = customFolder;
		resetFinder(id);
		if (elementId == null || elementId == "") {
			//api = finder.create();
		} else {
			api[id] = finder[id].appendTo( elementId );
		}
	}
</script>

<script type="text/javascript">
	function openFileEditor(fileName, index) {
		var openDocObj;
		var realPath = decodeURIComponent('${realPath}');
		var filePath = realPath + fileName;

		var fileExtension = new Array();
		var tempExtension = "";
		fileExtension = fileName.split(".");
		
		if (fileExtension.length >= 1) {
			tempExtension = fileExtension[fileExtension.length - 1];
		}
		
		if (tempExtension.toLowerCase() == "jpeg" || tempExtension.toLowerCase() == "jpg" || tempExtension.toLowerCase() == "bmp" || tempExtension.toLowerCase() == "png") {
			var url = "${ctx}/imageview?path=" + fileName;
			openLayer(url, $(window).width(), $(window).height(), false);	

		} else {
			var isCreated = false;
			for(var i = 1; i <= 5; i++) {
				try {
					openDocObj = new ActiveXObject("SharePoint.OpenDocuments." + i);
					isCreated = true;
				}
				catch(err) {
				}
			}			
			if (isCreated) {
				//openDocObj.EditDocument("http://localhost:8080/YS-MES/webdav.java/userfiles/c30209c4-a551-4468-9a8c-61bfd6fa385c/TestReport/files/QA2_0.xlsx", true);
				openDocObj.EditDocument(filePath, true);
				//openDocObj.EditDocument("http://localhost:8080/YS-MES/webdav.java/userfiles/c30209c4-a551-4468-9a8c-61bfd6fa385c/TestReport/files/QA2_0.xlsx", true);
			} else {
				var msg = "无法在线查看文档，请下载到客户端进行查看。";
				if (
				CKFinder.env.safari ||
				CKFinder.env.opera ||
				CKFinder.env.chrome ||
				CKFinder.env.gecko) {
					msg += "或使用IE浏览器再次尝试或.";
				}
				
				alert(msg);

			}
		}		
		//openDocObj.EditDocument("http://localhost:8080/YS-MES/userfiles/c30209c4-a551-4468-9a8c-61bfd6fa385c/TestReport/files/QA2_0.xlsx", true);

	}
</script>
