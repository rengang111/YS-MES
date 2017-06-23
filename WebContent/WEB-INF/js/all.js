
function nopermit(){
	$().toastmessage('showWarningToast', "您没有相应权限！");	
}

//回车键页面跳转
function selectOption(dtTable,path ){
	var factory_id = "";		
	var selOption = $(dtTable).DataTable().rows('.selected').data();		
	if( selOption.length != 0 ){
		selOption.each(function(data) {
			factory_id = data['factory_id'];
		});
		location.href = path + factory_id + '.html';
	}
}

//上下键移动选择行
function keydownOption(dtTable,keyCode){
	if(keyCode == 38){//向上
	moveOption(dtTable,false);
	return false;
	}
	else if(keyCode == 40){//向下
		moveOption(dtTable,true);
		return false;
	}
};

//上下键移动处理
function moveOption(dtTable,moveDown ){
	var selOption = $(dtTable).find("tr.selected");
	if( moveDown ){
		//没有选中的
		if( selOption.length ==0 ){
			//$("#example").find("tr").first().addClass("selected");
		}
		//有选中的，且不是最后一个
		else if(selOption.next("tr").length != 0 ){
			selOption.removeClass("selected");
			selOption.next("tr").addClass("selected");
		}
		else{
			selOption.removeClass("selected");
			$(dtTable).find("tr:eq(2)").addClass("selected");
		}
	}
	else{
		//没有选中的
		if( selOption.length ==0 ){
			//$("#example").find("tr").last().addClass("selected");
		}
		//有选中的，且不是第一个
		else if(selOption.prev("tr").length != 0 ){
			selOption.removeClass("selected");
			selOption.prev("tr").addClass("selected");
		}
		else{
			selOption.removeClass("selected");
			$(dtTable).find("tr").last().addClass("selected");
		}
	}
}

function showContract(contractId) {
	var url = '${ctx}/business/contract?methodtype=detailView&contractId=' + contractId;
	alert(url)
	layer.open({
		offset :[10,''],
		type : 2,
		title : false,
		area : [ '1100px', '520px' ], 
		scrollbar : false,
		title : false,
		content : url,
		//只有当点击confirm框的确定时，该层才会关闭
		cancel: function(index){ 
		 // if(confirm('确定要关闭么')){
		    layer.close(index)
		 // }
		}    
	});		

};

