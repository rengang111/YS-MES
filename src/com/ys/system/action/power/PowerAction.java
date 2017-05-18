package com.ys.system.action.power;

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
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.power.PowerModel;
import com.ys.system.action.model.role.RoleModel;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.power.PowerService;
import com.ys.util.DicUtil;

@Controller

public class PowerAction extends BaseAction {
	
	@Autowired
	PowerService powerService;
	
	@RequestMapping("/power")
	//public String execute(@ModelAttribute("dataModels")PowerModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
	public String doInit(@RequestBody String data, @ModelAttribute("dataModels")PowerModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		PowerModel viewModel = null;
		
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
				rtnUrl = "/power/powermanageframe";
				break;
			case "initframe":
				rtnUrl = "/power/powermain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "add":
				viewModel = doAdd(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "delete":
				viewModel = doDelete(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "getUnitList":
				rtnUrl = getUnitList(request, session);
				printOut(response, rtnUrl);
				return null;
			case "roleIdNameSearch":
				dataMap = doRoleIdNameSearch(data, request, session);
				printOutJsonObj(response, dataMap);
				return null;
			case "userSearch":
				dataMap = doUserSearch(data, request, session);
				printOutJsonObj(response, dataMap);
				return null;
			case "roleSearch":
				dataMap = doRoleIdNameSearch(data, request, session);
				printOutJsonObj(response, dataMap);
				return null;
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = powerService.doSearch(request, data, userInfo);
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

	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		PowerModel dataModel = new PowerModel();
		String operType = request.getParameter("operType");
		try {
			if (operType.equals("update")) {
				dataModel = powerService.getDetail(request);
			}
			if (operType.equals("addviauser")) {
				dataModel = powerService.getUserInfoList(request);
			}
			if (operType.equals("addviarole")) {
				dataModel = powerService.getRoleInfoList(request);
			}			
			dataModel.setOperType(operType);	
			dataModel.setIsOnlyView("");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/power/poweredit";
	}	

	public PowerModel doAdd(String data, HttpSession session, HttpServletRequest request){
		
		PowerModel dataModel = new PowerModel();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);

			dataModel = powerService.doAdd(request, data, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		return dataModel;
	}		
	
	public PowerModel doDelete(String data, HttpSession session, HttpServletRequest request){
		
		PowerModel dataModel = new PowerModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = powerService.doDelete(request, data, userInfo);
		dataModel.setOperType("delete");
		
		return dataModel;
	}
	
	public String getUnitList(HttpServletRequest request, HttpSession session) {
		String json = "";
		
		String userIdList = request.getParameter("userId");
		String roleIdList = request.getParameter("roleId");
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);

		json = powerService.getUnitList(request, userIdList, roleIdList, userInfo);
		
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doRoleIdNameSearch(@RequestBody String data, HttpServletRequest request, HttpSession session){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			dataMap = powerService.doRoleIdNameSearch(request, data, userInfo);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doUserSearch(@RequestBody String data, HttpServletRequest request, HttpSession session){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			dataMap = powerService.doUserSearch(request, data, userInfo);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
}
