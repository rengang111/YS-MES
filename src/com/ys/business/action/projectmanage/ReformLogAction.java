package com.ys.business.action.projectmanage;

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
import com.ys.business.action.model.reformlog.ReformLogModel;
import com.ys.business.action.model.processcontrol.ProcessControlModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.projectmanage.ReformLogService;
import com.ys.business.service.projectmanage.ProcessControlService;

@Controller
@RequestMapping("/business")
public class ReformLogAction extends BaseAction {
	
	@Autowired
	ReformLogService reformLogService;
	
	@Autowired
	ProcessControlService processControlService;
	
	
	@RequestMapping(value="reformlog")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")ReformLogModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		ReformLogModel viewModel = null;
		
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
				rtnUrl = "/business/reformlog/reformlogmain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
			case "updateinit":
				rtnUrl = doGetProjectBaseInfo(model, session, request, response);
				break;
			case "getReformLogList":
				dataMap = getReformLogList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;	
			case "delete":
				viewModel = doDelete(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "deleteDetail":
				viewModel = doDeleteDetail(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "addreformloginit":
			case "updatereformloginit":
				//rtnUrl = doUpdateReformLogInit(model, session, request, response);
				rtnUrl = doGetProjectBaseInfo(model, session, request, response);
				rtnUrl = "/business/reformlog/reformlogedit";
				break;	
			case "deletereformlog":
				viewModel = doDeleteReformLog(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;	
			case "updatereformlog":
				rtnUrl = doUpdateReformLog(dataModel, session, request, response, model);
				return rtnUrl;
				//printOutJsonObj(response, viewModel.getEndInfoMap());
				//return null;	
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = processControlService.doSearch(request, data, userInfo);
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
	
	public String doGetProjectBaseInfo(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		ProcessControlModel dataModel = new ProcessControlModel();
		String key = request.getParameter("key");
		try {
			dataModel = processControlService.getProjectBaseInfo(request, key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		HashMap<String, Object> detailData = getReformLogList(key, session, request, response);
		model.addAttribute("detail", detailData);
		model.addAttribute("detailCount", ((ArrayList<HashMap<String, String>>)detailData.get("data")).size());
		
		return "/business/reformlog/reformlogview";
	}		
	
	
	public HashMap<String, Object> getReformLogList(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = reformLogService.doGetReformLogList(request, data, userInfo);
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
	
	public String doUpdateReformLogInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		ReformLogModel dataModel = new ReformLogModel();
		String projectId = request.getParameter("projectId");
		String key = request.getParameter("key");

		try {
			dataModel = reformLogService.doUpdateReformLogInit(request, projectId, key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/reformlog/reformlogedit";
	}		
	
	public String doUpdateReformLog(ReformLogModel dataModel, HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model){
		
		ReformLogModel modelView = new ReformLogModel();
		ProcessControlModel baseModel = null;

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		modelView = reformLogService.doUpdateReformLog(request, dataModel, userInfo);
		
		try {
			baseModel = processControlService.getProjectBaseInfo(request, dataModel.getKeyBackup());
			baseModel.setEndInfoMap(modelView.getEndInfoMap());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			baseModel.setMessage("发生错误，请联系系统管理员");
		}
		
		model.addAttribute("DisplayData", baseModel);
		HashMap<String, Object> detailData = getReformLogList(dataModel.getKeyBackup(), session, request, response);
		model.addAttribute("detail", detailData);
		model.addAttribute("detailCount", ((ArrayList<HashMap<String, String>>)detailData.get("data")).size());
		
		return "/business/reformlog/reformlogview";
	}	
	
	public ReformLogModel doDeleteReformLog(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		ReformLogModel model = new ReformLogModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = reformLogService.doDeleteReformLog(request, data, userInfo);

		return model;
	}	
	
	public ReformLogModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		ReformLogModel model = new ReformLogModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = reformLogService.doDelete(request, data, userInfo);

		return model;
	}

	public ReformLogModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		ReformLogModel model = new ReformLogModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = reformLogService.doDelete(request, data, userInfo);

		return model;
	}
	
	
	
}
