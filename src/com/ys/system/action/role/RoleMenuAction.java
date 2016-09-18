package com.ys.system.action.role;

import java.util.ArrayList;

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
import com.ys.system.service.common.MakeTreeStyleData;
import com.ys.system.service.role.RoleMenuService;
import com.ys.util.JsonUtil;


@Controller

public class RoleMenuAction extends BaseAction {
	
	@Autowired
	RoleMenuService roleMenuService;
	
	@RequestMapping("/rolemenu")
	public String doInitRoleMenu(@RequestBody String para, @ModelAttribute("dataModels")RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("methodtype");
		String rtnUrl = "";
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
				rtnUrl = doSearch(dataModel, result,  model, session, request, response);
				break;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "delete":
				rtnUrl = doDelete(dataModel, result, model, session, request, response);
				break;
			case "checkrolemenu":
				rtnUrl = doCheckRoleMenu(para, session, request, response);
			printOut(response, rtnUrl);
				return null;
			case "getrolemenu":
				rtnUrl = getRoleMenu(model, session, request, response);
			printOut(response, rtnUrl);
				return null;
			case "update":
				rtnUrl = doUpdate(para, model, session, request, response);
			printOut(response, rtnUrl);
				return null;
		}
		
		return rtnUrl;
		
	}
	
	public String doSearch(RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		try {
			dataModel = roleMenuService.doSearch(request, dataModel, userInfo);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/role/rolemenumain";
	}	
	
	public String doDelete(RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			
			dataModel.setUpdatedRecordCount(0);
			
			roleMenuService.doDelete(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("删除成功");
			dataModel = roleMenuService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/role/rolemenumain";
	}
	
	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		RoleModel roleModel = new RoleModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		roleModel.setDeptGuid(userInfo.getDeptGuid());
		roleModel.setRoleId(request.getParameter("roleId"));
		roleModel.setRoleIdName(request.getParameter("roleName"));
		
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
			System.out.println(e.getMessage());
			base.setMessage("系统错误");
		}
		return JsonUtil.toJson(base);
	}
	
	public String getRoleMenu(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String json = "";
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			if (roleMenuService.isRightRole(request, "", userInfo)) {
				ArrayList<MenuInfo> menuInfoLIst = roleMenuService.getRoleIdMenu(request, userInfo);
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
	
	public String doUpdate(@RequestBody String data, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		BaseModel base = new BaseModel();
		String dataArray[] = data.split(",");
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			if (roleMenuService.isRightRole(request, dataArray[0], userInfo)) {
				ArrayList<String> menuIdList = new ArrayList<String>();
				for(int i = 1; i < dataArray.length; i++) {
					menuIdList.add(dataArray[i]);
				}
				roleMenuService.doUpdate(request, dataArray[0], menuIdList, userInfo);
				base.setMessage("更新完毕");
				base.setSuccess(true);
			} else {
				base.setSuccess(false);
				base.setMessage("角色不正确，无法更新");
			}
			
			//dataModel = roleMenuService.getRoleIdMenu(request);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			base.setMessage("发生错误，请联系系统管理员");
			base.setSuccess(false);
		}
		
		
		return JsonUtil.toJson(base);
	}	

}
