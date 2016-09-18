
//inputId:file的id
//picListId:多张图片上传后回显用id开始名称
//retImageID:回显的img
//actionUrl:上传用action
function uploadOneFileNew(inputId, picListId, retImageID, actionUrl){
	
	rPath = ${ctx}"/";
	
	retImage = $('#' + retImageID);
	var fileObj = $('#'+inputId)[0].files[0];
	// 初始化XMLHttpRequest
	var xhr = new XMLHttpRequest();
	xhr.open('post', actionUrl, false);
	// 这里很关键，初始化一个FormData，并将File文件发送到后台
	var fd = new FormData(); 
	fd.append("file", fileObj);
	loading();
	//这里接受response 	
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {  
			var resultValue = eval('(' + xhr.responseText + ')');
			var err = resultValue.err;
            if(err != undefined){
            	alert(err);
            	return false;
            }
            /*
			var picList = $('#' + picListId);
			if(picList.val() == ''){
				picList.val(resultValue.imgPath);
			}else{
				picList.val(resultValue.imgPath);
			}
			*/
			$(retImage).attr("src", rPath + resultValue.imgPath);
		}
	};
	xhr.send(fd); 
}