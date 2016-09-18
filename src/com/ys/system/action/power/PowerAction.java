package com.ys.system.action.power;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.power.PowerModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.power.PowerService;

@Controller

public class PowerAction extends BaseAction {
	
	@Autowired
	PowerService powerService;
	
	@RequestMapping("/power")
	public String execute(@ModelAttribute("dataModels")PowerModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
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
				rtnUrl = "/power/powermanageframe";
				break;
			case "initframe":
				rtnUrl = "/power/powermain";
				break;
			case "search":
				rtnUrl = doSearch(dataModel, result,  model, session, request, response);
				break;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "add":
				rtnUrl = doAdd(dataModel, result, model, session, request, response);
				break;
			case "delete":
				rtnUrl = doDelete(dataModel, result, model, session, request, response);
				break;
			case "getUnitList":
				rtnUrl = getUnitList(request, session);
			printOut(response, rtnUrl);
				return null;
		}
		
		return rtnUrl;
	}	
	
	public String doSearch(PowerModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel = powerService.doSearch(request, dataModel, userInfo);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/power/powermain";
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

	public String doAdd(PowerModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			int rowCount = powerService.doAdd(request, dataModel, userInfo);
			dataModel.setUpdatedRecordCount(rowCount);
			dataModel.setOperType("add");
			if (rowCount > 0) {
				dataModel.setMessage("授权成功");
			} else {
				dataModel.setMessage("权限已存在，本次无授权发生。");
			}
			/*
			int preCheckResult = powerService.preCheck(request);
			switch(preCheckResult) {
			case 0:
				powerService.doAdd(request, dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setOperType("add");
				dataModel.setMessage("增加成功");
				
				break;
			case 1:
				dataModel.setMessage("权限已存在");
				break;
			case 2:
				dataModel.setMessage("权限不存在");
				break;
			case 3:
				dataModel.setMessage("权限已存在");
				break;
			}
			*/
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("授权失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/power/poweredit";
	}	
	
	public String doDelete(PowerModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			powerService.doDelete(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("授权删除成功");
			dataModel = powerService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("授权删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/power/powermain";
	}
	
	public String getUnitList(HttpServletRequest request, HttpSession session) {
		String json = "";
		
		String userIdList = request.getParameter("userId");
		String roleIdList = request.getParameter("roleId");
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);

		json = powerService.getUnitList(request, userIdList, roleIdList, userInfo);
		
		return json;
	}		
}
