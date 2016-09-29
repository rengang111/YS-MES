package com.ys.util;

import com.ys.util.basequery.BaseQuery;

public class CommonUtil {
	
	public static String getMsg(String key) {
		String fileName = "/setting/messages_zh_CN";
		return BaseQuery.getContent(fileName, key);
	}
	
}
