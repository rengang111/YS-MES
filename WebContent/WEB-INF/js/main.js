/**
 * Created by Think on 2015/12/17.
 */
/* 根据title，href打开（已存在）或者新建一个tab页
 * selector:目标tabs 选择器
 * title：新tab页标题
 * href：新tab页加载内容路径
 * icon：新tab页图标（可选参数）
 */
function openTab(selector,title,href,icon){
    var $tabs = $(selector);
    //如果请求路径不存在，则跳转到error.html界面
    if(!(href!=""&&href!="null"&&href!=null&&href!=undefined)){
        href="framework/error.html";
    }
    if($tabs.tabs('exists',title)){//存在，则打开
        $tabs.tabs('select',title);
    }else{//不存在，新建,新建时判断tab页个数，超出则关闭第一个
        var content = '<iframe scrolling="no" id="mainFrame" frameborder="0" src="'+href+'" width="100%" height="99%" onload="iFrameHeight();"></iframe>';
        var tabCon = { title:title,  content:content, closable:true, selected:true, iconCls:icon };
        if($('.tabs-inner').length>8){//最多打开8个（不包括首页）
            $.messager.confirm('提示', '菜单页打开过多，是否关闭第一个，并打开“'+title+'”？', function(r){
                if (r){//关闭第一页，并打开当前页
                    $('#_main_center_tabs').tabs('close',1);
                    $tabs.tabs('add',tabCon);
                    tabsRightClickAction();//绑定双击tab事件和右键菜单事件。
                }
            });
        }else{
            $tabs.tabs('add',tabCon);
        }

    }
    //tabsRightClickAction();//绑定双击tab事件和右键菜单事件。
}

/*
 *前台构造树方法。
 *rows:树所需的基本数据。
 */
function convert(rows){
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
    }
    return nodes;
}// end or  convert(rows)



//设置页面只读
function setPageReadonly(selectIdentifier) {
	$("input").each(function(){
		var objType = $(this).attr("type");
		if (objType != undefined) {
			objType = objType.toLowerCase();
		} else {
			$(this).attr("disabled", "disabled");
		}
		if (objType == "button") {
			var objName = $(this).attr("name").toLowerCase();
			if (objName != 'close') {
				$(this).hide();
			}
		} else {
			if (objType == "checkbox" || objType == "radio") {
				$(this).attr("disabled", "disabled");
			} else {
				$(this).attr("readonly", true);
			}
		}
	});
	$("select[id*=" + selectIdentifier + "]").each(function(){
		$(this).attr("disabled", "disabled");
	});
}

function popupWindow(name, url, width, height) {
    var iTop = (window.screen.availHeight - 30 - height) / 2;
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth - 10 - width) / 2;	
	
	
	window.open(url, name, 'height=' + height + ',width=' + width + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no,status=no');
}

var totalPages = $("#totalPages").val();
function goToPage(form_id, index, flg) {
	//区分是否是翻页还是检索
	$('#turnPageFlg').val(1);
	//直接翻到第几页
    $('#startIndex').val(index);
	//不为空则直接跳转到startIndex指定的页
    $('#flg').val(flg);
    if($("#pageIndex").val() == 0)
        $("#pageIndex").val(1);

    if($("#pageIndex").val() > totalPages)
    	$("#pageIndex").val(totalPages);
    
    $("#" + form_id).submit(); 
} 

function recordPerPageChanged() {
	$('#form').submit();
}

function pad(num, n) {  
    var len = num.toString().length;  
    while(len < n) {  
        num = "0" + num;  
        len++;  
    }  
    return num;  
}

function today() {
	var mydate = new Date();
	var y = format(mydate.getFullYear());
	var m = format((mydate.getMonth() + 1));
	var d = format(mydate.getDate());
	var h = format(mydate.getHours());	
	var n = format(mydate.getMinutes());	
	var s = format(mydate.getSeconds());		
	return y+'-'+m+'-'+d+' '+h+':'+n+':'+s;
};

function format(s){
	if(s<10){
		return '0'+s;
	}else{
		return s;
	}
}

function iFramAutoSroll(){
	//重设显示窗口(iframe)高度
	var bodyHeight = $(document).height(); 
	//var viewHeight = bodyHeight<700?bodyHeight:700;
	var viewHeight = bodyHeight;
	
    parent.document.getElementById("mainFrame").height = viewHeight+"px";
}

function iFramNoSroll(){
	//重设显示窗口(iframe)高度	
    parent.document.getElementById("mainFrame").height = "99%";
}

function PrefixInteger(num, length) {
	 return (Array(length).join('0') + num).slice(-length);
} 

/*
 * 金额转换成可计算的数字
 * parm0:金额
 */
function currencyToFloat(currency){

	if(currency =="" || currency == "0" || currency == null)
		return 0;
	
	if(typeof currency == "number")
		return currency;
	
	//去掉逗号
	currency = currency.replace(/,/g, "");
	
	//验证数字，包括小数
	//该函数的返回值要参与计算,所以至少返回 '0'
	var reg = /^[0-9]+.?[0-9]*$/;
	if(!reg.test(currency))
		return 0;		
	
	return parseFloat(currency);
	
}

function floatToCurrency(value){

	var toFloat = '';
	
	if(typeof value == 'number'){
		toFloat = value;
	}else{
		toFloat = currencyToFloat(value);
	}
	
	//转换成float出错的情况,返回原值
	if(toFloat == 0)
		return value;
		
	var numString = toFloat.toFixed(2);
	var parts = numString.split('.');
	var outParts = [];
	var beforeDecimal = '0';
	var afterDecimal = '00';
	var currSegment;

	beforeDecimal = parts[0];
	afterDecimal = parts[1];
	
	while (beforeDecimal.length > 3) {
		
		currSegment = beforeDecimal.substring(
				beforeDecimal.length - 3,
				beforeDecimal.length);
		
		beforeDecimal = beforeDecimal.substring(
				0,
				beforeDecimal.length -3);
		outParts.unshift(currSegment);
	}
	
	if(beforeDecimal.length > 0) {
		
		outParts.unshift(beforeDecimal);
	}
	
	return outParts.join(',') + '.' + afterDecimal;
	
}


		
	
	