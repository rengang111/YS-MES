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
        var content = '<iframe scrolling="auto" id="mainFrame" frameborder="0" src="'+href+'" width="100%" height="99%"></iframe>';
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
	
	
	window.open(url, name, 'height=' + height + ',width=' + width + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no,status=no');
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


function fullToday() {
	var mydate = new Date();
	var y = format(mydate.getFullYear());
	var m = format((mydate.getMonth() + 1));
	var d = format(mydate.getDate());
	var h = format(mydate.getHours());	
	var n = format(mydate.getMinutes());	
	var s = format(mydate.getSeconds());	
	return y+'-'+m+'-'+d+' '+h+':'+n+':'+s;
};

function shortToday() {
	var mydate = new Date();
	var y = format(mydate.getFullYear());
	var m = format((mydate.getMonth() + 1));
	var d = format(mydate.getDate());	
	return y+'-'+m+'-'+d;
};

function formatTime(date) {
  var year = date.getFullYear();
  var month = date.getMonth()+1, month = month < 10 ? '0' + month : month;
  var day = date.getDate(), day =day < 10 ? '0' + day : day;
  return year + '-' + month + '-' + day;
}

function format(s){
	if(s<10){
		return '0'+s;
	}else{
		return s;
	}
}

function iFramAutoSroll(){
	/*
	//重设显示窗口(iframe)高度
	var bodyHeight = $(document).height(); 
	var viewHeight = bodyHeight<700?700:bodyHeight;
	//alert(bodyHeight)
	//var viewHeight = bodyHeight;
	
    parent.document.getElementById("mainFrame").height = viewHeight+"px";
    */
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

	currency = currency.replace(/,/g, "");//去掉逗号
	//currency = currency.replace(/[^\d.]*/g, "");//保留数字和小数点
	currency = currency.replace(/[^\- \d.]/g, "");//保留数字和小数点
	//value.replace(/[^\- \d.]/g,'')

	//验证数字，包括小数
	//该函数的返回值要参与计算,所以至少返回 '0'
	//var reg = /^[0-9]+.?[0-9]*$/;
	//var reg = /^[\+\-]?\d+(\.\d*)?$/;
	//if(!reg.test(currency))
		//return 0;		
	
	return parseFloat(currency);
	
}

function floatToNumber(value){

	//n = n > 0 && n <= 20 ? n : 2;  
    var s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(0) + "";  
    var l = s.split(".")[0].split("").reverse();
    var r = s.split(".")[1];  
    var t = "";  
    for (i = 0; i < l.length; i++) {  
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
    }  
    return t.split("").reverse().join("") + "." + r;  
	
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
	var sybolFlg = false;

	beforeDecimal = parts[0];
	afterDecimal = parts[1];
	
	//负号判断
	var sybol = beforeDecimal.substring(
				0,
				1);		
	if(sybol == '-'){
	
		beforeDecimal = beforeDecimal.substring(
				1,
				beforeDecimal.length);
		sybolFlg = true;	
	}
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
	
	if(sybolFlg){		
		return sybol + outParts.join(',') + '.' + afterDecimal;
	}else{
		return outParts.join(',') + '.' + afterDecimal;
		
	}	
	
}


function float4ToCurrency(value){

	var toFloat = '';
	
	if(typeof value == 'number'){
		toFloat = value;
	}else{
		toFloat = currencyToFloat(value);
	}
	
	//转换成float出错的情况,返回原值
	if(toFloat == 0)
		return value;
		
	var numString = toFloat.toFixed(5);
	var parts = numString.split('.');
	var outParts = [];
	var beforeDecimal = '0';
	var afterDecimal = '00000';
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

function float5ToCurrency(value){

	var toFloat = '';
	
	if(typeof value == 'number'){
		toFloat = value;
	}else{
		toFloat = currencyToFloat(value);
	}
	
	//转换成float出错的情况,返回原值
	if(toFloat == 0)
		return value;
		
	var numString = toFloat.toFixed(5);
	var parts = numString.split('.');
	var outParts = [];
	var beforeDecimal = '0';
	var afterDecimal = '00000';
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

function floatToNumber(value){

var toFloat = '';
	
	if(typeof value == 'number'){
		toFloat = value;
	}else{
		toFloat = currencyToFloat(value);
	}
	
	//转换成float出错的情况,返回原值
	if(toFloat == 0)
		return value;
		
	var numString = toFloat.toFixed(0);
	var parts = numString.split('.');
	var outParts = [];
	var beforeDecimal = '0';
	var currSegment;

	beforeDecimal = parts[0];
	
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
	
	return outParts.join(',');
	
}

function inputCheck(){
	
	var blcheck = true;
	$('.required').each(function() {
    	var val = $(this).val();
    	if(val == ''){
    		$(this).removeClass('bgnone').addClass('error');
    		blcheck = false;
    	}    		
  	});
	
	return blcheck;
}		
	
(function($){
	$.extend($,{
		fixedWidth:function(str,length,flag,char){
		
			if(str == null || str == '' ) return str;
			str = str.toString();
			if(!char) char='...';
			var strFull = str;	
			var num = length - lengthB(str);//获取字符串的字节数与指定长度的差值
			
			if((flag) && (num < 0)){				
    			for(var i=0;i<str.length;i++){
					if(str.charCodeAt(i) > 255 ){
		    			str = str.substring(i,str.length);
						break;
					}
				}
			}
			num = length - lengthB(str);//重复计算一次
			
			if(num < 0){
				//按字节数截取字符串,并附加后缀
				str = substringB(str,length - lengthB(char)) + char;
				//添加一个提示信息				
				str =  '<div title="' + strFull + '">' + str + '</div>';
				
			}else{
				str =  '<div title="' + strFull + '">' + str + '</div>';
				
			}
			return str;
		
			function substringB(str,length){
			
				var num = 0,len = str.length,tenp = "";
				
				if( len ){
					for(var i=0;i<len;i++){
						if(num> length) break;
						if(str.charCodeAt(i) > 255){	//双字节字符
							num  += 2;					//增加两个字节数
							tenp += str.charAt(i);
						}else{
							num++;
							tenp += str.charAt(i);
						}
					}
				
					return tenp;
				}else{
					return null;
				}
				
			}//substrigB
			
					
		}
	})
})(jQuery);

//获取字符串的字节数
function lengthB(str){

	var num = 0,len = str.length;
	
	if(len){
		for(var i=0;i<len;i++){
			if(str.charCodeAt(i) > 255 ){
				num+= 2;
			}else{
				num++;
			}
		}
		
		return num;
	}else{
		return 0;
	}
}

function foucsInit(){
	
	$("table[id^='example'] input").addClass('bgnone');
	$("table[id^='example'] input").addClass('bsolid');

	
	$("#baseinfo input").focus(function(){
		$(this).removeClass('bgnone');
	});
	
	$("table[id^='example'] input").focus(function(){
		//$(this).removeClass('bgnone').addClass('bgwhite');
	});

	$(".num") .focus(function(){
		//$(this).val(currencyToFloat($(this).val()));
		$(this).select();
	});
	
	$(".cash") .focus(function(){
		//$(this).val(currencyToFloat($(this).val()));
		$(this).select();
	});
	
	$(".num") .blur(function(){
		//$(this).val(floatToNumber($(this).val()));
	});

		
	$(".cash") .blur(function(){
		//$(this).val(floatToCurrency($(this).val()));
	});

	
	$("input").focus(function(){
	   	$(this).select();
	});
	// $(".DTTT_container").css('float','left');
	//$(".DTTT_container").css('margin-top',' -24px');
	
}

function GetRandomNum(Min,Max){   
	var Range = Max - Min;   
	var Rand = Math.random();   
	return(Min + Math.round(Rand * Range));   
}

function floatToSymbol(v,s){
	var curr = floatToCurrency(v);
	var symbol = getCurrencySymbol(s);
	
	return symbol + '  ' + curr;
}

var currencyAarry = [
	['$','美元'],
	['€','欧元'],
	['£','英镑'],
	['¥','人民币'],
	['¥','日元'],
	['$','新加坡']
	
];

function getCurrencySymbol(curr){
	var rtn = '¥';//默认单位:人民币
	for(var i=0;i<currencyAarry.length;i++){
		var val = currencyAarry[i][0];//取得货币符号:$,€...
		var key = currencyAarry[i][1];//取得货币名称:美元,欧元...
		if(curr == key){
			rtn = val;//匹配货币符号
			break;
		}
	}
	return rtn;
}

$(function(){
	var t = [];
	var dt = $("dl.collapse dt");
	var dd = $("dl.collapse dd");
	
	dt.each(function(i){
		t[i] = false;		//设置折叠初始状态
		$(dt[i]).click((function(i,dd){
			
			return function(){		//返回一个闭包函数,闭包能够存储传递进来的动态参数
				
				if(t[i]){					
					$(dd).show();
					t[i] = false;
				}else{
					$(dd).hide();
					t[i] = true;
				}					
			}
		})(i,dd[i]))	//向当前执行函数中传递参数
	})
})


function fomatToColor(value){
	
	if (currencyToFloat(value) < 0 ){
		return "<span style='color: red'>" +floatToCurrency( value ) + "</span>";
	}else{
		return floatToCurrency(value);
	}
	
}

function stringPadAfter(value,len){   
	var str='';
	if(value.length<len){
		
	    for(var i=0;i<(len-value.length);i++){
	        str+="&nbsp;";
	    }
	    
	}
	return value+str;
}


function replaceTextarea(str){
	var reg=new RegExp("<br>","g");
	var reg1=new RegExp("<p>","g");
	var reg2=new RegExp("&quot;","g");
	var reg3=new RegExp("&apos;","g");

	str = str.replace(reg,"\r\n");
	str = str.replace(reg1," ");
	str = str.replace(reg2,"\"");
	str = str.replace(reg3,"\'");

	return str;
} 

function replaceTextarea1(str){
	var reg=new RegExp("\r\n","g");
	var reg1=new RegExp(" ","g");
	
	str = str.replace(reg,"＜br＞");
	str = str.replace(reg1,"＜p＞");
	
	return str;
}

function bigImage(tdTable,index,path){

	var sWidth = $(window).width()+"px";
	var sHeight= $(window).height()+"px";
	var tWidth = ($(window).width() - 100 )+"px"
	var tHeight= ($(window).height() - 50 )+"px"
	//alert("sWidth:"+sWidth+"----tWidth:"+tWidth)
 	var imgUrl = path
	var html  = "<table style='height:"+tHeight+";width:"+tWidth+"'><tr><td style='text-align: center;'>";
	html += "<img class='img_url_class' style='' src='"+imgUrl+"' />";
	html += "</td></tr></table>";
	 //页面层
 	var idx = parent.layer.open({
    	title:'原始图预览',
    	type: 1,
		scrollbar : true,
   		skin: 'layui-layer-rim', //加上边框
   		area: [sWidth, sHeight], //宽高
   		content: html
  	});
}

function bigImage2(tdTable,index,path){

	var imgUrl = path;
	//var imgUrl = $("#itemLi_"+id).attr('data-src');
	//var imgSrc = $("#linkFile"+tdTable+index).attr("src");

	getImageWidth(path,function(imageW,iamgeH){
		//alert("width:"+w+"---height:"+h);
		var sWidth = $(window).width()+"px";
		var sHeight= $(window).height()+"px";
		var tWidth = ($(window).width() - 100 )+"px"
		var tHeight= ($(window).height() - 50 )+"px"
		window.parent.img_width = imageW;
		var html  = "<table style='height:"+tHeight+";width:"+tWidth+"'><tr><td style='text-align: center;'>";
		//var html  = "<center>"
		html += "<div style='position:fixed; left:50%; margin-left:-40px; bottom:5px;'>";
		   html += "  <button style='width:40px; height:40px; border-radius:20px;'";
		   html += "        onclick='window.parent.img_width < "+imageW+" ? window.parent.img_width += 100 : 0;";
		   html += "  $(\".img_url_class\").css({width: window.img_width});'>";
		   html += "  放大";
		   html += "  </button>&nbsp; &nbsp; ";
		   html += "  <button style='width:40px;  height:40px;border-radius:20px;'";
		   html += "  onclick='window.parent.img_width > 100 ? window.parent.img_width -= 100 : 0;";
		   html += "  $(\".img_url_class\").css({width: window.img_width})' >"; 
		   html += "  缩小"; 
		   html += "  </button>";
		   html += "</div>";
		   html += "</center>";
		   html += "<br/>";
		   html += "<center>";
		   html += "<img class='img_url_class'  src='"+imgUrl+"' />";
		   //html += "</center>"
		   html += "</td></tr></table>";
		  //页面层
		var idx = parent.layer.open({
		    title:'原始图预览',
		    type: 1,
			//skin: 'layui-layer-rim', //加上边框
	  		area: [sWidth, sHeight], //宽高
		   	content: html
		});
		parent.layer.full(idx);
	});
}

function getImageWidth(url,callback){
	var img = new Image();
	img.src = url;
	
	// 如果图片被缓存，则直接返回缓存数据
	if(img.complete){
	    callback(img.width, img.height);
	}else{
            // 完全加载完毕的事件
	    img.onload = function(){
		callback(img.width, img.height);
	    }
    }
	
}

function setPurchaseQuantity(stock,order){
	
	stock = currencyToFloat(stock);//格式化
	order = currencyToFloat(order);//格式化
	var quan = 0;
	if(stock >= 0 ){
		if(stock < order){			
			quan = order - stock;
		}else{
			quan = 0;
		}
		
	}else{
		quan = order;
	}
	
	return quan;
}

function getLetters(text){
	//alert(text)
	//var value = text.replace(/[^0-9]/ig,""); 
	var value =  text.replace(/[^a-z]+/ig,"");
	return value;
}

function buttonSelectedEvent(){
	//加载事件
    $(function () {
        var collection = $(".box");
        $.each(collection, function () {
            $(this).addClass("start");
        });
    });
    //单击事件
    $(".box").click(function () { 
        var collection = $(".box");
        $.each(collection, function () {
            $(this).removeClass("end");
            $(this).addClass("start");
        });
        $(this).removeClass("start");
        $(this).addClass("end");
    });
}


 