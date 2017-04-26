<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>	
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>

<head>
<title></title>
<meta charset="UTF-8"> 
<%@ include file="../../common/common.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugins/fine-uploader-5.3.2/client/bootstrap.min.css" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugins/fine-uploader-5.3.2/client/fineuploader-gallery.css" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/plugins/fine-uploader-5.3.2/client/fineuploader-new.css" />

</head>
<body style="overflow:scroll;overflow-y:hidden" onbeforeunload="return beforeunload(event);" >

	<div id="layer_main" style="padding:0px 0px;">

			<div id="container">
		    <!-- The element where Fine Uploader will exist. -->
		 		<div id="fine-uploader-manual-trigger">
		    	</div>
		    </div>

    <!-- Fine Uploader -->
    <script src="${pageContext.request.contextPath}/plugins/fine-uploader-5.3.2/client/js/fine-uploader.min.js" type="text/javascript"></script>

    <!-- The element where Fine Uploader will exist. -->

    <!-- Fine Uploader -->
    <script src="fine-uploader.min.js" type="text/javascript"></script>

    <script type="text/template" id="qq-template-manual-trigger">
        <div class="qq-uploader-selector qq-uploader" qq-drop-area-text="Drop files here">
            <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
                <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
            </div>
            <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
                <span class="qq-upload-drop-area-text-selector"></span>
            </div>
            <div class="buttons">
                 <div class="qq-upload-button-selector qq-upload-button" style="margin-right:5px">
                     <div>选择文件</div>
                 </div>
                 <button type="button" id="trigger-upload" class="btn btn-primary qq-upload-button">
                      <i class="icon-upload icon-white"></i> 上传
                 </button>
            </div>
            <span class="qq-drop-processing-selector qq-drop-processing">
                <span>Processing dropped files...</span>
                <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
            </span>
            <ul class="qq-upload-list-selector qq-upload-list" aria-live="polite" aria-relevant="additions removals">
                <li>
                    <div class="qq-progress-bar-container-selector">
                        <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
                    </div>
                    <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                    <img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale>
                    <span class="qq-upload-file-selector qq-upload-file"></span>
                    <span class="qq-edit-filename-icon-selector qq-edit-filename-icon" aria-label="Edit filename"></span>
                    <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
                    <span class="qq-upload-size-selector qq-upload-size"></span>
                    <button type="button" class="qq-btn qq-upload-cancel-selector qq-upload-cancel">Cancel</button>
                    <button type="button" class="qq-btn qq-upload-retry-selector qq-upload-retry">Retry</button>
                    <button type="button" class="qq-btn qq-upload-delete-selector qq-upload-delete">Delete</button>
                    <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
                </li>
            </ul>

            <dialog class="qq-alert-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <div class="qq-dialog-buttons">
                    <button type="button" class="qq-cancel-button-selector">Close</button>
                </div>
            </dialog>

            <dialog class="qq-confirm-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <div class="qq-dialog-buttons">
                    <button type="button" class="qq-cancel-button-selector">No</button>
                    <button type="button" class="qq-ok-button-selector">Yes</button>
                </div>
            </dialog>

            <dialog class="qq-prompt-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <input type="text">
                <div class="qq-dialog-buttons">
                    <button type="button" class="qq-cancel-button-selector">Cancel</button>
                    <button type="button" class="qq-ok-button-selector">Ok</button>
                </div>
            </dialog>
        </div>
    </script>
    
    <style>
        #trigger-upload {
            color: white;
            background-color: #00ABC7;
            font-size: 14px;     
            padding: 7px 0px;
            background-image: none;
        }

        #fine-uploader-manual-trigger .qq-upload-button {
            margin-right: 15px;
        }

        #fine-uploader-manual-trigger .buttons {
            width: 36%;
        }

        #fine-uploader-manual-trigger .qq-uploader .qq-total-progress-bar-container {
            width: 60%;
        }
    </style>

    <script>
    	var totalAttachmentsCount = 0;
    	var uploadedAttachmentsCount = 0;
    	
        var uploader = new qq.FineUploader({
            element: document.getElementById('fine-uploader-manual-trigger'),
            template: 'qq-template-manual-trigger',
            multiple: true,
            request: {
                endpoint: '${pageContext.request.contextPath}/album/album-upload',
                params:
                { 
                	key: "${DisplayData.key}",
                	info: "${DisplayData.info}",
                },
            },
            validation: {
                //控制上传文件的后缀格式数组
                allowedExtensions: ['jpeg', 'jpg', 'png'],
             //控制上传文件大小
                sizeLimit: (100000 * 1024 * 1024) // 200 kB = 200 * 1024 bytes
            },
            thumbnails: {
                placeholders: {
                    waitingPath: '${pageContext.request.contextPath}/plugins/fine-uploader-5.3.2/client/placeholders/waiting-generic.png',
                    notAvailablePath: '${pageContext.request.contextPath}/plugins/fine-uploader-5.3.2/client/placeholders/not_available-generic.png'
                }
            },
            autoUpload: false,
            debug: true,
            callbacks:
            {
                onComplete: function (id, fileName, responseJSON) {
                	
                	uploadedAttachmentsCount++;
                	
                	if (totalAttachmentsCount == uploadedAttachmentsCount) {
                		parent.refresh();
	            		var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	            		
	            		parent.layer.close(index);
                	}
                }
            }
        });
        
        qq(document.getElementById("trigger-upload")).attach("click", function() {

        	//if (confirm("${msg}")) {
        		totalAttachmentsCount = uploader._storedIds.length;
        		uploader.uploadStoredFiles();
        		

        		/*
				//parent.$('#events').DataTable().destroy();/
				if ('${DisplayData.info}' == '') {
					parent.refresh();
					//alert(parent.location);
					parent.layer.close(index); //执行关闭	
				} else {
					var curTab = parent.parent.$('#tabs').tabs('getSelected');
					parent.refresh();
					layer.close(index); //执行关闭
				}
				*/
        	//}
        	
        });
        
    </script>
    </div>
</body>
</html>
