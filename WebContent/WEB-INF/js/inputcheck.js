	//取得全半角混合字符串长度
	function getDataLength(fData) { 
	    var intLength = 0; 
	    for (var i = 0; i < fData.length; i++) 
	    { 
	        if ((fData.charCodeAt(i) < 0) || (fData.charCodeAt(i) > 255)) 
	            intLength = intLength + 2; 
	        else 
	            intLength = intLength + 1;   
	    } 
	    return intLength; 
	}
	
	//数字，逗号，小数点输入check
	function checkNumber(str) { 
	    //var reg = /^\d+(,\d\d\d)*.\d+$/;	    
	  	var reg = /^([1-9]([0-9,])*(\.[0-9]+)?)$/;
	    str = $.trim(str)
	    if (str != "") { 
	        if (!reg.test(str)) {            
	            return false;
	        } else{
	        	return true;
	        }
	    }
	}
	
	//日期格式检查
	function checkDate(str) {
		if (str.length > 10) {
			return false;
		}
		var strSplit = str.split("/");
	
		if (strSplit.length > 1) {
			//2016/0101
			if (strSplit.length == 2) {
				return false;
			}
		} else {
			strSplit = str.split("-");
			if (strSplit instanceof Array) {
				//201601-01
				if (strSplit.length == 2) {
					return false;
				}
			}
		}
		

		if (strSplit.length == 1) {
			//2016101
			if (str.length < 8) {
				return false;
			}
			strSplit = new Array(3);
			strSplit[0] = str.substr(0, 4);
			strSplit[1] = str.substr(4, 2);
			strSplit[2] = str.substr(6, 2);
		}
		//year
		if (checkDateNum(strSplit[0]) == false) {
			return false;
		}
		if (parseInt(strSplit[0]) < 1950 || parseInt(strSplit[0]) > 2050) {
			return false;
		}
		//month
		if (checkDateNum(strSplit[1]) == false) {
			alert(strSplit[1]);
			return false;
		}
		if (parseInt(strSplit[1]) < 1 || parseInt(strSplit[1]) > 12) {
			return false;
		}
		//day
		if (checkDateNum(strSplit[2]) == false) {
			return false;
		}
		if (parseInt(strSplit[2]) < 1 || parseInt(strSplit[2]) > 31) {
			return false;
		}
		//Feburary
		if (parseInt(strSplit[1]) == 2) {
			if (!isleapYear(strSplit[0]) && parseInt(strSplit[2]) > 28) {
				return false;
			}
			if (isleapYear(strSplit[0]) && parseInt(strSplit[2]) > 29) {
				return false;
			}
		}

		return true;

	}
	
	function isleapYear(year) {
		if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {
			return true;
		}
		return false;
	}
	
	//身份证检查
	function checkIdcard(str){
		str = str.toUpperCase();
	    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
	    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(str)))
	    {
	    	alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
	        return false;
	    }
	    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	    //下面分别分析出生日期和校验位
	    var len, re;
	    len = str.length;
	    if (len == 15)
	    {
	        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
	        var arrSplit = str.match(re);
	 
	        //检查生日日期是否正确
	        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
	        var bGoodDay;
	        bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
	      
	        if (!bGoodDay)
	        {
	        	alert('输入的身份证号里出生日期不对！');
	            return false;
	        }
	    }
	    if (len == 18)
	    {
	        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
	        var arrSplit = str.match(re);
	 
	        //检查生日日期是否正确
	        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
	        var bGoodDay;
	        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));

	        if (!bGoodDay)
	        {
	        	alert('输入的身份证号里出生日期不对！');
	            return false;
	        }

	    }
	    return true;
	}	
	
	//数值检查
	function checkNum(str) {
		var reg = /^[0-9][0-9]*$/;
		return reg.test(str);
	}
	
	//数值检查
	function checkDateNum(str) {
		var reg = /^[0-9][0-9]*$/;
		return reg.test(str);
	}
	
	//手机
	function checkTelphone(str){
		var reg=/^1[3|4|5|8][0-9]\d{8}$/;

		return reg.test(str);
	}
	
	//固话
	function checkPhoneNumber(str){

		var reg=/(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
		return reg.test(str);
		
	}
	
	//email
	function checkEmail(str){
	    var reg =  /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	    return reg.test(str);
	}		
	
	//英数字汉字(可输入_.,:)
	function checkEnglishNumberChinese(str) {
		var reg = /^(?![_.,:])[a-zA-Z0-9_.,:\uFF00-\uFFFF\u4e00-\u9fa5-]+$/;
		return reg.test(str);
	}
	
	//英数字(_.,:-)
	function checkEnglishNumber(str) {
		var reg = /^(?![_,])[a-zA-Z0-9_.,:-]+$/;
		return reg.test(str);
	}
	
	//url
	function checkUrl(str) {
		var reg = /^(?![_.,:])[a-zA-Z0-9._\&:/\u4e00-\u9fa5-]+$/;
		return reg.test(str);
	}
	
	function checkNoHtml(str) {
		var reg = /\<\>\<\>/;
		return reg.test(str);
	}
	
	function inputStrCheck(str, title, length, type, isCanBeEmpty, isNoHtml) {

		if (str == null) {
			str = '';
		}
		if (str != '') {
			str = str.replace(/^\s\s*/, '')
			if (str != '') {
				str = str.replace(/\s\s*$/, '');
			}
		}
		if (isCanBeEmpty == false) {
			if (str == '') {
				alert(title + "不可为空(空格无效)");
				return false;
			}
		}

		if (length > 0 && str.length > length) {
			alert(title + "的数据超长，长度不应超过：" + length);
			return false;
		}

		if(str != '') {
			var checkType = parseInt(type);
			switch(checkType) {
			case 1:
				if (checkDate(str) == false) {
					var newDate = new Date();
					dateStr1 = newDate.format("yyyy-MM-dd");
					dateStr2 = newDate.format("yyyy/MM/dd");
					alert(title + "的日期格式不正确，正确格式：" + dateStr1 + " 或 " + dateStr2 + " 或 " + "yyyymmdd");
					return false;
				}
				break;
			case 2:
				if (checkIdcard(str) == false) {
					return false;
				}
				break;
			case 3:
				if (checkNum(str) == false) {
					alert(title + "只能输入英文的数字");
					return false;
				}
				break;
			case 4:
				if (checkTelphone(str) == false) {
					alert(title + "的手机号码不正确");
					return false;
				}
				break;
			case 5:
				if (checkPhoneNumber(str) == false) {
					alert(title + "的号码不正确");
					return false;
				}
				break;
			case 6:
				if (checkEmail(str) == false) {
					alert(title + "的邮件地址不正确");
					return false;
				}
				break;
			case 7:
				if (checkEnglishNumberChinese(str) == false) {
					alert(title + "只允许输入中文(包括全角字符)，半角英数字及下划线和逗号，且不可以符号开头");
					return false;
				}
				break;
			case 8:
				if (checkEnglishNumber(str) == false) {
					alert(title + "只允许输入半角英数字及下划线和逗号，且不可以符号开头");
					return false;
				}
				break;
			case 9:	
				if (checkUrl(str) == false) {
					alert(title + "的URL地址不正确");
					return false;
				}
				break;
			}
	
			if (isNoHtml) {
				if (checkNoHtml(str) == true) {
					alert(title + "不允许输入符号：< 或 >");
					return false;
				}
			}
		}
		
		return true;
	}
	
	Date.prototype.format = function(format){ 
		var o = { 
			"M+" : this.getMonth()+1, //month 
			"d+" : this.getDate(), //day 
			"h+" : this.getHours(), //hour 
			"m+" : this.getMinutes(), //minute 
			"s+" : this.getSeconds(), //second 
			"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
			"S" : this.getMilliseconds() //millisecond 
		} 

		if (/(y+)/.test(format)) { 
			format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		} 

		for(var k in o) { 
			if(new RegExp("("+ k +")").test(format)) { 
				format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
			} 
		} 
		return format; 
	} 	
	
	//比较日前大小  
	function compareDate(checkStartDate, checkEndDate) {     
		var rtnValue = true;
	    var arys1= new Array();      
	    var arys2= new Array();      
		if(checkStartDate != null && checkEndDate != null) {      
		    arys1=checkStartDate.split('-');      
		    var sdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);      
		    arys2=checkEndDate.split('-');      
		    var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);      
			if(sdate > edate) {      
				rtnValue =  false;         
			}  
	    } 
		
		return rtnValue;
	} 