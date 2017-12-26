package com.ys.system.action.role;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ys.system.action.common.BaseAction;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.role.RoleModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.interceptor.MenuInfo;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.common.MakeTreeStyleData;
import com.ys.system.service.role.RoleMenuService;
import com.ys.util.DicUtil;
import com.ys.util.JsonUtil;


@Controller

public class RoleMenuAction extends BaseAction {
	
	@Autowired
	RoleMenuService roleMenuService;
	
	@RequestMapping("/rolemenu")
	public String doInitRoleMenu(@RequestBody String data, @ModelAttribute("dataModels")RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		RoleModel viewModel = null;
		
		if (type == null) {
			type = "";
		} else {
			int q = type.indexOf("?");
			if (q >= 0) {
				type = type.substring(0, q);
			}
		}
		
		switch(type) {
			case "":
			case "init":
				rtnUrl = "/role/rolemenumanageframe";
				break;
			case "initframe":
				rtnUrl = "/role/rolemenumain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "delete":
				viewModel = doDelete(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "checkrolemenu":
				rtnUrl = doCheckRoleMenu(data, session, request, response);
			printOut(response, rtnUrl);
				return null;
			case "getrolemenu":
				rtnUrl = getRoleMenu(model, session, request, response);
			printOut(response, rtnUrl);
				return null;
			case "update":
				viewModel = doUpdate(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "roleIdNameSearch":
				dataMap = doRoleIdNameSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;

		}
		
		return rtnUrl;
		
	}
	
	public HashMap<String, Object> doSearch(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = roleMenuService.doSearch(request, data, userInfo);
			ArrayList<HashMap<String, String>> dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
			if (dbData.size() == 0) {
				dataMap.put(INFO, NODATAMSG);
			}

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}	
	
	public RoleModel doDelete(String data, HttpSession session, HttpServletRequest request){
		
		RoleModel dataModel = new RoleModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = roleMenuService.doDelete(request, data, userInfo);
		DicUtil.emptyBuffer(true);
		dataModel.setOperType("delete");
		
		return dataModel;
	}
	
	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		RoleModel roleModel = new RoleModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		roleModel.setDeptGuid(userInfo.getDeptGuid());
		roleModel.setRoleId(request.getParameter("roleId"));
		try {
			roleModel.setRoleIdName(URLDecoder.decode(request.getParameter("roleName"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("DisplayData", roleModel);
		
		return "/role/rolemenuedit";
	}
	

	public String doCheckRoleMenu(String roleIdName, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		BaseModel base = new BaseModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		try {
			boolean result = roleMenuService.isRightRole(request, roleIdName, userInfo);
	    	base.setSuccess(result);
	    	if (!result) {
		    	base.setMessage("角色名不正确");
	    	}
		}
		catch(Exception e) {
			e.printStackTrace();
			base.setMessage("系统错误");
		}
		return JsonUtil.toJson(base);
	}
	
	public String getRoleMenu(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String json = "";
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
				ArrayList<MenuInfo> menuInfoLIst = roleMenuService.getRoleIdMenu(request, userInfo);
				if (roleMenuService.isRightRole(request, "", userInfo)) {
				json = MakeTreeStyleData.convertMenuChainToJson(menuInfoLIst);
			} else {
				System.out.println("角色不正确，无法更新");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return json;
	}
	
	public RoleModel doUpdate(String data, HttpSession session, HttpServletRequest request){

		RoleModel dataModel = new RoleModel();
		String dataArray[] = data.split(",");
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			if (roleMenuService.isRightRole(request, dataArray[0], userInfo)) {
				ArrayList<String> menuIdList = new ArrayList<String>();
				for(int i = 1; i < dataArray.length; i++) {
					menuIdList.add(dataArray[i]);
				}
				roleMenuService.doUpdate(request, dataArray[0], menuIdList, userInfo);
				dataModel.setEndInfoMap(BaseService.NORMAL, "", "");
			} else {
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "角色不正确，无法更新", "");
			}
			
			//dataModel = roleMenuService.getRoleIdMenu(request);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		
		return dataModel;
	}	

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doRoleIdNameSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = roleMenuService.doRoleIdNameSearch(request);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
}
