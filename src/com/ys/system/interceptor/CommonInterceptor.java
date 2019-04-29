package com.ys.system.interceptor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_OPERLOGData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.util.CalendarUtil;
import com.ys.util.RequestLog;
import com.ys.util.XmlUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.Constants;

/**
 * @ClassName: CommonInterceptor
 * @Description: action拦截器
 * @author 李晓峰
 * @date 2015年12月17日 上午9:30:00
 * @version 1.0
 */
@Repository
public class CommonInterceptor extends HandlerInterceptorAdapter {

	private static HashMap<String, String> menuMap = new HashMap<String, String>();
	private static HashMap<String, String> onlySessionCheckMap = new HashMap<String, String>();
	private static HashMap<String, String> noInterceptMap = new HashMap<String, String>();
	private static String exceptionUrl = ""; 
	private static String adminRoleList = "";
	private static String filterDefine = "";
	private static boolean isFilter = false;
	protected static Logger logger = Logger.getLogger("");
	
    /**
     * (�� Javadoc)
     * <p>
     * Title: preHandle
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

    	boolean isRedirect = false;
        HttpSession session = request.getSession();
        String userId = "";
        String menuTypeLimit = "";
        ArrayList<ArrayList<String>> dbResult = new ArrayList<ArrayList<String>>();
        String targetMenuId = "";

        UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
        
        //拦截成功后的跳转地址
        if (exceptionUrl.equals("")) {
        	exceptionUrl = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, BusinessConstants.INTERCEPTORFAILEDURL);
        }
        //Admin权限过滤
        if (adminRoleList.equals("")) {
        	adminRoleList = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERADMIN);
        }
        //是否过滤admin权限
        if(filterDefine.equals("")) {
	        filterDefine = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERDEFINE);
	        isFilter = Boolean.parseBoolean(filterDefine);
        }
        
        if(onlySessionCheckMap.isEmpty()) {
        	Element element = XmlUtil.getRootElement(BusinessConstants.ONLYSESSIONCHECKLISTFILENAME);
	        onlySessionCheckMap = XmlUtil.getContentList(element, BusinessConstants.CONTENTNAME);
	        onlySessionCheckMap = replaceCtx(request.getContextPath(), onlySessionCheckMap);
        }
        if (noInterceptMap.isEmpty()) {
        	Element element = XmlUtil.getRootElement(BusinessConstants.NOINTERCEPTLISTFILENAME);
	        noInterceptMap = XmlUtil.getContentList(element, BusinessConstants.CONTENTNAME);
	        noInterceptMap = replaceCtx(request.getContextPath(), noInterceptMap);
        }
        
        String uri = request.getRequestURI();
        
        //不需要拦截的action在此处放过
        if (isExist(noInterceptMap, uri)) {
            return super.preHandle(request, response, handler);
        }
        
        if (isSessionValidate(session)) {
        	//TODO:
        	userId = userInfo.getUserId();

        	String fullUrl = request.getRequestURL().toString();
        	if (request.getQueryString() != null) {
        		fullUrl += "?" + request.getQueryString();
        	}

            String ip = getIpAddr(request);
            
            String browserInfo = request.getHeader("user-agent");

	        if (menuMap.isEmpty() || userInfo.isSA()) {
	        	//取得所有的Menuid和action
	        	dbResult = DBAccess.getAllMenuPS(request, false);
	        	for (ArrayList<String> rowData:dbResult) {
	        		String menuId = rowData.get(0);
	        		String menuUrl = request.getContextPath() + rowData.get(3);
	        		menuMap.put(menuUrl, menuId);
	        	}
	        }
	        
	        if (!isExist(onlySessionCheckMap, uri)) {
	        	//根据uri取出对应的menuid，然后确认该menuid是否有对应的action
		        targetMenuId = menuMap.get(uri);
		        
		        //记录操作日志
		       // executeOperLogAdd(userInfo, targetMenuId==null?"":targetMenuId, fullUrl, ip, browserInfo);
		        StringBuffer bf = new StringBuffer();
		        bf.append("login="+userInfo.getUserId()+"/"+userInfo.getUserName()+"/"+userInfo.geLoginId())
		        .append(",targetMenuId="+targetMenuId)
		        .append(",url="+fullUrl)
		        .append(",ip="+ip)
		        .append(",browser="+browserInfo)
		        ;
		        
		       // RequestLog.requestLog(logger, bf);
		        //System.out.println(bf.toString());
		       // Enumeration rnames=request.getParameterNames();		        
		       // for (Enumeration e = rnames ; e.hasMoreElements() ;) {
		       //      String thisName=e.nextElement().toString();
		       //     String thisValue=request.getParameter(thisName);
		       //     System.out.println(thisName+":"+thisValue);
		       // }
		        
		      //request对象封装的参数是以Map的形式存储的
		        /*
		         Map<String, String[]> paramMap = request.getParameterMap();
		         for(Map.Entry<String, String[]> entry :paramMap.entrySet()){
		             String paramName = entry.getKey();
		             String paramValue = "";
		             String[] paramValueArr = entry.getValue();
		             for (int i = 0; paramValueArr!=null && i < paramValueArr.length; i++) {
		                 if (i == paramValueArr.length-1) {
		                     paramValue+=paramValueArr[i];
		                 }else {
		                     paramValue+=paramValueArr[i]+",";
		                 }
		             }
		             RequestLog.requestLog(logger,MessageFormat.format("{0}={1}", paramName,paramValue));
		             //System.out.println(MessageFormat.format("{0}={1}", paramName,paramValue));
		         }
		        */
		        
		        if (targetMenuId == null) {
		        	//菜单表中无对应的action
		        	isRedirect = true;
		        } else {
		        	if (userInfo.getUserType().equals(Constants.USER_SA)) {
		        		//判断URL是否是功能级，不是则返回错误
		        		int sysMenuCount = DBAccess.getSysMenucount(request, uri);
		        		if (sysMenuCount == 0) {
		        			isRedirect = true;
		        		}
		        	} else {
		        		//确认用户是否具有admin权限，有数据即视为具有admin权限
			        	int adminRoleidCount = BaseQuery.getAdminRoleidCount(request, userId, adminRoleList);
			        	if (adminRoleidCount > 0) {
			        		menuTypeLimit = BusinessConstants.MENUTYPE_ADMIN;
			        	} else {
			        		menuTypeLimit = BusinessConstants.MENUTYPE_BUSINESS;
			        	}
			        	int roleIdCount = DBAccess.getRoleidCount(request, userId, targetMenuId, menuTypeLimit, isFilter);
			        	//权限判定，0为无权限
			        	if (roleIdCount == 0) {
			        		dbResult = DBAccess.getRelationalMenuID(request, targetMenuId);
			        		if (dbResult.size() > 0) {
				        		String relationalMenuIDList = dbResult.get(0).get(0);
				        		String relationalMenuId[] = relationalMenuIDList.split(",");
				        		StringBuffer relationalMenuIdBuffer = new StringBuffer();
				        		boolean isFirst = true;
				        		for(String relationalMenuIdSub:relationalMenuId) {
				        			if (isFirst) {
				        				isFirst = false;
				        			} else {
				        				relationalMenuIdBuffer.append(",");
				        			}
				        			relationalMenuIdBuffer.append("'");
				        			relationalMenuIdBuffer.append(relationalMenuIdSub.trim());
				        			relationalMenuIdBuffer.append("'");
				        		}
				        		
				        		roleIdCount = DBAccess.getRelationalRoleidCount(request, userId, relationalMenuIdBuffer.toString(), menuTypeLimit, isFilter);
				        		if (roleIdCount == 0) {
				        			//无权限
				        			isRedirect = true;
				        		}
			        		} else {
			        			//无权限
			        			isRedirect = true;
			        		}
			        	}
		        	}
		        }
	        }
        } else {
        	isRedirect = true;
        }
        
        if (isRedirect) {
        	//TODO:
        	//如果需要清空session，在这里清空
        	
        	//TODO:为了测试方便，将拦截器关闭--begin
        	//request.getRequestDispatcher(exceptionUrl).forward(request, response); 
        	////request.getRequestDispatcher("/").forward(request, response); 
            ////response.sendRedirect(request.getContextPath() + exceptionUrl);
            //return false;
        	
        	//TODO:正式环境中下面的处理应该去除
        	if (!isSessionValidate(session)) {
        		request.getRequestDispatcher(exceptionUrl).forward(request, response);
        		return false;
        	}
        	//TODO:为了测试方便，将拦截器关闭--end
        }
        
        return super.preHandle(request, response, handler);
    }
    /*
	private String getAction(String url) {
    	String action = "";
    	int index = 0;
    	int length = url.length();
    	
    	if (length != 0) {   	
	    	index = url.lastIndexOf("/");
	    	if (index < 0) {
	    		index = 0;
	    	} else {
	    		index++;	
	    	}
		   	action = url.substring(index, length);
    	}
    	
    	return action;
   	}
   	*/
	
	private boolean isSessionValidate(HttpSession session) {
		boolean isOk = false;
		try {
			Enumeration<String> sessionPropertyList = session.getAttributeNames();
			while(sessionPropertyList.hasMoreElements()) {
				String sessionProperty = sessionPropertyList.nextElement();
				if (sessionProperty.toLowerCase().equals(BusinessConstants.SESSION_USERINFO)) {
					isOk = true;
					break;
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return isOk;
	}
	
	private HashMap<String, String> replaceCtx(String contextPath, HashMap<String, String> map) {
		HashMap<String, String> rtnMap = new HashMap<String, String>();
		
		Set<String> keySet = map.keySet();
		Iterator<String> keyList = keySet.iterator();
		while(keyList.hasNext()) {
			String key = keyList.next();
			if (key.toLowerCase().indexOf("${ctx}") >= 0) {
				key = key.replace("${ctx}", contextPath);
			}
			rtnMap.put(key, "");
		}
		return rtnMap;			
	}
	
	private boolean isExist(HashMap<String, String> map, String uri) {
		boolean rtnValue = false;
		
		if (map.containsKey(uri)) {
			rtnValue = true;
		} else {
			Set<String> keySet = map.keySet();
			Iterator<String> keyList = keySet.iterator();
			while(keyList.hasNext()) {
				String key = keyList.next();
				if (key.toLowerCase().indexOf("*") >= 0) {
					if(uri.length() >= key.length()) {
						if (key.substring(0, key.length() - 1).equals(uri.substring(0, key.length() - 1))) {
							rtnValue = true;
							break;
						}
					}
				}
			}
		}
		
		return rtnValue;
	}
	
	private String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("PRoxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }
	    
	    String ips[] = ip.split(",");
	    
	    return ips[0];  
	}  
	
	private void executeOperLogAdd(UserInfo userInfo, String menuId, String menuUrl, String ip, String browserName) {
		try {

			S_OPERLOGData data = new S_OPERLOGData();
			
			data.setId(BaseDAO.getGuId());
			data.setUserid(userInfo.getUserId());
			data.setUsername(userInfo.getUserName());
			data.setMenuywclass("");
			data.setMenuid(menuId);
			data.setMenuname("");
			data.setMenuurl(menuUrl);
			data.setIp(ip);
			data.setBrowsename(browserName);
			data = updateModifyInfo(data, userInfo);
			data = setDeptGuid(data, userInfo);
			
			DbUpdateEjb bean = new DbUpdateEjb();
		    
		    bean.executeOperLogAdd(data);

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private S_OPERLOGData updateModifyInfo(S_OPERLOGData data, UserInfo userInfo) {
		String createPowerId = data.getCreateperson();
		if ( createPowerId == null || createPowerId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateperson(userInfo.getUserId());
			data.setCreateunitid(userInfo.getUnitId());
		}
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	private S_OPERLOGData setDeptGuid(S_OPERLOGData data, UserInfo userInfo) throws Exception {
		if (userInfo.isSA()) {
			S_USERData userData = new S_USERData();
			userData.setUserid(userInfo.getUserId());
			S_USERDao userDao = new S_USERDao(userData);
			data.setDeptguid(userDao.beanData.getDeptguid());
		} else {
			data.setDeptguid(userInfo.getDeptGuid());
		}
		
		return data;
	}	
}
