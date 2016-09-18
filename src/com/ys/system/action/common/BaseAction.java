package com.ys.system.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author 
 *
 */
public class BaseAction {

	protected static Logger logger = Logger.getLogger(BaseAction.class);
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
	}
	
	protected boolean printOut(HttpServletResponse response, String data) {
		
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
}
