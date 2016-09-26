package com.ys.system.action.common;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.ys.util.basequery.BaseQuery;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 
 *
 */
public class BaseAction {

	protected static Logger logger = Logger.getLogger(BaseAction.class);
	public static final String INFO = "message";
	protected final String NODATAMSG = "无符合条件的数据";
	protected final String SUCCESSMSG = "操作成功";
	protected final String ERRMSG = "操作失败，请再次尝试或联系管理员";
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
	}
	
	protected boolean printOut(HttpServletResponse response, Object data) {
		
		boolean result = false;
		
		try {
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(data);
			result = true;
			
		}
		catch(Exception e) {
			System.out.println();
		}
		
		return result;
	}
	
	protected boolean printOutJsonObj(HttpServletResponse response, Object data) {
		
		boolean result = false;
		
		try {
			response.setContentType("application/json; charset=UTF-8");
			
			if (data instanceof ArrayList || data.getClass().isArray()) {
				JSONArray jsonObject = JSONArray.fromObject(data);
				response.getWriter().print(jsonObject);
			} else {
				JSONObject jsonObject = JSONObject.fromObject(data);
				response.getWriter().print(jsonObject);
			}
			
			result = true;
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
}
