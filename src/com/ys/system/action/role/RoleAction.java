package com.ys.system.action.role;

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
import com.ys.system.service.role.RoleService;
import com.ys.util.DicUtil;
import com.ys.util.JsonUtil;


@Controller

public class RoleAction extends BaseAction {
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping("/role")
	public String doInit(@RequestBody String para, @ModelAttribute("dataModels")RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
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
				rtnUrl = "/role/rolemanageframe";
				break;
			case "initframe":
				rtnUrl = "/role/rolemain";	
				break;
			case "search":
				rtnUrl = doSearch(dataModel, result,  model, session, request, response);
				break;
			case "updateinit":
				rtnUrl = doUpdateInit(dataModel, model, session, request, response);
				break;
			case "update":
				rtnUrl = doUpdate(dataModel, result, model, session, request, response);
				break;
			case "add":
				rtnUrl = doAdd(dataModel, result, model, session, request, response);
				break;
			case "delete":
				rtnUrl = doDelete(dataModel, result, model, session, request, response);
				break;
			case "detail":
				rtnUrl = doDetail(dataModel, result, model, session, request, response);
				break;
			case "rolerelationuser":
				rtnUrl = getRoleRelationUser(model, session, request, response);
				break;
			case "checkRoleName":
				rtnUrl = doCheckRoleName(para);
			printOut(response, rtnUrl);
				return null;
		}
		
		return rtnUrl;

	}
	
	public String doSearch(RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		try {
			dataModel = roleService.doSearch(request, dataModel, userInfo);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/role/rolemain";
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
	
	public String doUpdate(RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel.setUpdatedRecordCount(0);
			dataModel.setRoleTypeList(roleService.getRoleTypeList(userInfo));
			
			if (checkRoleName(dataModel.getroleData().getRoleid(), dataModel.getroleData().getRolename())) {
				roleService.doUpdate(dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setMessage("更新成功");
			} else {
				dataModel.setMessage("角色名已存在");
			}
			dataModel.setOperType("update");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("更新失败");
		}
		
		model.addAttribute("DisplayData", dataModel);
		return "/role/roleedit";
	}	

	public String doAdd(RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			dataModel.setUpdatedRecordCount(0);
			dataModel.setRoleTypeList(DicUtil.getGroupValue(DicUtil.ROLETYPE));
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			if (checkRoleName(dataModel.getroleData().getRoleid(), dataModel.getroleData().getRolename())) {
				roleService.doAdd(dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setMessage("增加成功");
			} else {
				dataModel.setMessage("角色名已存在");
			}
			dataModel.setOperType("add");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("增加失败");
		}
		dataModel.getroleData().setRoleid("");
		
		model.addAttribute("DisplayData", dataModel);
		
		return "/role/roleedit";
	}	
	
	public String doDelete(RoleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			roleService.doDelete(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("删除成功");
			dataModel = roleService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/role/rolemain";
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
