package com.ys.util;

import java.util.regex.Matcher;

import org.springframework.web.servlet.ModelAndView;

import com.ys.util.basequery.BaseQuery;

public class CommonUtil {
	
	public static String getMsg(String key) {
		String fileName = "/setting/messages_zh_CN";
		return BaseQuery.getContent(fileName, key);
	}

	public static int getCharacterPosition(String string,String search,int num){
	    //这里是获取"/"符号的位置
	    Matcher slashMatcher = java.util.regex.Pattern.compile(search).matcher(string);
	    int mIdx = 0;
	    while(slashMatcher.find()) {
	       mIdx++;
	       //当"/"符号第三次出现的位置
	       if(mIdx == num){
	          break;
	       }
	    }
	    return slashMatcher.start();
	 }
	
	public static void mvAddMsg(ModelAndView mv){
		
		mv.addObject("msg", getMsg("0001"));
		
		mv.addObject("msg0004", getMsg("0004"));
		
		mv.addObject("msg0005", getMsg("0005"));
		
		mv.addObject("err001", getMsg("err001"));
		
		mv.addObject("err002", getMsg("err002"));
		
		mv.addObject("err003", getMsg("err003"));
		
		mv.addObject("err004", getMsg("err004"));
		
		mv.addObject("err005", getMsg("err005"));
		
		mv.addObject("err006", getMsg("err006"));
		
		mv.addObject("suc001", getMsg("suc001"));
		
	}		
}
