package com.ys.system.action.role;

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
import com.ys.system.action.model.unit.UnitModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.role.RoleService;
import com.ys.util.DicUtil;
import com.ys.util.JsonUtil;


@Controller

public class RoleAction extends BaseAction {
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping("/role")
	public String doInit(@RequestBody String data, @ModelAttribute("dataModels")RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
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
				rtnUrl = "/role/rolemanageframe";
				break;
			case "initframe":
				rtnUrl = "/role/rolemain";	
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "updateinit":
				rtnUrl = doUpdateInit(dataModel, model, session, request, response);
				break;
			case "update":
				viewModel = doUpdate(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "add":
				viewModel = doAdd(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "delete":
				viewModel = doDelete(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "detail":
				rtnUrl = doDetail(dataModel, result, model, session, request, response);
				break;
			case "rolerelationuser":
				rtnUrl = getRoleRelationUser(model, session, request, response);
				break;
			case "getrolerelationuser":
				dataMap = getRoleRelationUserList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "checkRoleName":
				rtnUrl = doCheckRoleName(data);
				printOut(response, rtnUrl);
				return null;
		}
		
		return rtnUrl;

	}
	
	public HashMap<String, Object> doSearch(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = roleService.doSearch(request, data, userInfo);
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

	public String doUpdateInit(RoleModel dataModel, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		String operType = request.getParameter("operType");
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);

		try {
			if (operType.equals("update")) {
				dataModel = roleService.getDetail(request, userInfo);
			} else {
				dataModel.setRoleTypeList(roleService.getRoleTypeList(userInfo));
			}
			dataModel.setUserName(userInfo.getUserName());
			dataModel.setUnitName(userInfo.getUnitName());

			dataModel.setOperType(operType);	
			dataModel.setIsOnlyView("");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/role/roleedit";
	}	
	
	public RoleModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		RoleModel dataModel = new RoleModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			if (checkRoleName(roleService.getJsonData(data, "roleid"), roleService.getJsonData(data, "rolename"))) {
				dataModel = roleService.doUpdate(request, data, userInfo, false);
			} else {
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "角色名已存在", "");
			}
			dataModel.setOperType("update");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		return dataModel;
	}	

	public RoleModel doAdd(String data, HttpSession session, HttpServletRequest request){

		RoleModel dataModel = new RoleModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			if (checkRoleName(roleService.getJsonData(data, "roleid"), roleService.getJsonData(data, "rolename"))) {
				dataModel = roleService.doUpdate(request, data, userInfo, true);
			} else {
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "角色名已存在", "");
			}
			dataModel.setOperType("add");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		return dataModel;
	}	
	
	public RoleModel doDelete(String data, HttpSession session, HttpServletRequest request){
		
		RoleModel dataModel = new RoleModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = roleService.doDelete(request, data, userInfo);
		DicUtil.emptyBuffer(true);
		dataModel.setOperType("delete");
		
		return dataModel;
	}
	

	public String doDetail(RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			dataModel.setRoleTypeList(DicUtil.getGroupValue(DicUtil.ROLETYPE));
			dataModel = roleService.getDetail(request, userInfo);
			dataModel.setUserName(userInfo.getUserName());
			dataModel.setUnitName(userInfo.getUnitName());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("详细信息取得失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/role/roleedit";
	}
	
	public String getRoleRelationUser(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		RoleModel dataModel = new RoleModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			dataModel = roleService.getRoleRelationUser(request);
			dataModel.setUserName(userInfo.getUserName());
			dataModel.setUnitName(userInfo.getUnitName());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("详细信息取得失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/role/rolerelationuser";
	}	
	
	public HashMap<String, Object> getRoleRelationUserList(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = roleService.getRoleRelationUserList(request, data, userInfo);
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
	
    public String doCheckRoleName(String roleIdName) {
    	BaseModel base = new BaseModel();
    	
    	String roleId = "";
    	String roleName = "";
    	
    	String infoArray[] = roleIdName.split("&");
    	
    	roleName = infoArray[0];
    	if (infoArray.length > 1) {
    		roleId = infoArray[1];
    	}
    	
    	boolean result = checkRoleName(roleId, roleName);
    	base.setSuccess(result);
    	if (!result) {
	    	base.setMessage("角色名已存在");
    	}
    	
    	return JsonUtil.toJson(base);
    	
    }
    
	private boolean checkRoleName(String roleId, String roleName) {
		boolean rtnValue = false;
		
		try {
			if (roleService.checkRoleName(roleId, roleName) == 0) {
				rtnValue = true;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return rtnValue;

	}
	
}
