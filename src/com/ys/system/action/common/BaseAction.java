package com.ys.system.action.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.BaseQuery;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Element;
import com.ys.util.XmlUtil;
import java.util.Iterator;
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
	
	protected String openFileBrowser(HttpServletRequest request, HttpSession session, Model model) {
		
		String key = request.getParameter("key");
		String tabTitle = request.getParameter("tabTitle");
		String folderName = request.getParameter("folderName");
		String id = request.getParameter("id");
		String customFolder = request.getParameter("customFolder");
		String realPath = "";
		
		if (customFolder == null) {
			customFolder = "";
		}
		
		String contextPath = request.getContextPath();
		String requestUrl = request.getRequestURL().toString();
		int iIndex = requestUrl.indexOf(contextPath);
		requestUrl = requestUrl.subSequence(0, iIndex) + contextPath;
		String configURL[] = getBaseUrl();
		requestUrl += configURL[2];
		/*
		try {
            realPath = java.net.URLEncoder.encode(request.getSession().getServletContext().getRealPath("/"), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
		if (key == null) {
			realPath = "//";
		}
		*/
		model.addAttribute("realPath", requestUrl);
		
		if (folderName != null && !folderName.equals("")) {
			folderName = "/" + folderName + "/"; 
			String userFolderName = folderName;//"/" + key + "/" + configURL[1] + folderName;
			session.setAttribute(BusinessConstants.FILESYSTEMBROWSERUSERFOLDER + id + "-" + tabTitle, userFolderName);
			session.setAttribute(BusinessConstants.FILESYSTEMBROWSERUSERFOLDER + id, userFolderName);
			model.addAttribute("userFolder", folderName);
			//model.addAttribute("finder-" + index, );
		} else {
			session.removeAttribute(BusinessConstants.FILESYSTEMBROWSERUSERFOLDER + id + "-" + tabTitle);
			session.removeAttribute(BusinessConstants.FILESYSTEMBROWSERUSERFOLDER + id);
			model.addAttribute("userFolder", "//");
		}
		
		if (!customFolder.equals("")) {
			key += "//" + customFolder;
		}
		
		session.setAttribute(BusinessConstants.FILESYSTEMBROWSERBASEFOLDER + id + "-" + tabTitle, key);
		session.setAttribute(BusinessConstants.FILESYSTEMBROWSERBASEFOLDER + id, key);
		
		return "";
		//return "/common/filebrowser";
	}
	
	protected ArrayList<String>getFolderList(HttpServletRequest request, HttpSession session) {
		ArrayList<String> folderList = new ArrayList<String>();
		String key = request.getParameter("key");
		String tabTitle = request.getParameter("tabTitle");
		session.setAttribute(BusinessConstants.FILESYSTEMBROWSERBASEFOLDER + tabTitle, key);
		session.setAttribute(BusinessConstants.FILESYSTEMBROWSERBASEFOLDER, key);
		
		String dir = getRootUrl(request, key); 
    	File file = new File(dir);
        String [] fileNames = file.list();
        
        folderList.add("/");
        
        if (fileNames != null && fileNames.length > 0) {
			for(String fileName:fileNames) {
				file = new File(dir + "/" + fileName);
				if (file.isDirectory()) {
					if (fileName.length() > 0 && !fileName.substring(0, 1).equals("_")) {
						folderList.add(fileName);
					}
				}
			}
        } else {
        	
        }
       
        return folderList;
	}
	
	private String getRootUrl(HttpServletRequest request, String key) {
		String configURL[] = getBaseUrl();
		String dir = request.getSession().getServletContext().getRealPath("/") + configURL[0] + "/" + key + "/" + configURL[1]; 

		return dir;
	}
	
	private String[] getBaseUrl() {
		String baseURL[] = {"", "", ""};
		String xmlFileName = "/setting/ckfinder.xml";
		int breakCount = 0;
		Element element = XmlUtil.getRootElement(xmlFileName);
		
		if (element != null) {
			Iterator<Element> iter = element.elementIterator();
			while(iter.hasNext()){
			    Element el = (Element)iter.next();
			    if (el.getName().equals("webdav")) { 
			    	baseURL[2] = (String)el.getData();
			    	breakCount++;
			    }
			    if (el.getName().equals("baseURL")) { 
			    	baseURL[0] = (String)el.getData();
			    	breakCount++;
			    }
			    if (el.getName().equals("types")) {
			    	Iterator<Element> subIter = el.elementIterator();
					while(subIter.hasNext()){
						Element subEl = (Element)subIter.next();
						if (subEl.attributes().size() > 0) {
							
							if (subEl.attribute(0).getValue().equals("Files")) {
								Iterator<Element> grandSonIter = subEl.elementIterator();
								while(grandSonIter.hasNext()){
									Element grandSonEl = (Element)grandSonIter.next();
									if (grandSonEl.getName().equals("directory")) {
										String dictory = (String)grandSonEl.getData();
										int index = dictory.indexOf("%BASE_DIR%");
										String filesFolder = dictory.substring(index + "%BASE_DIR%".length(), dictory.length()); 
										baseURL[1] = filesFolder;
										breakCount++;
										break;
									}
								}
							}
						}
						if (breakCount == baseURL.length) {
							break;
						}
					}
					if (breakCount == baseURL.length) {
						break;
					}
			    }
			}
		}
		return baseURL;
	}
	
	
	
}
