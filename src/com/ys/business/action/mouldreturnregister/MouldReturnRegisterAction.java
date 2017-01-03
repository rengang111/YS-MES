package com.ys.business.action.mouldreturnregister;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.TestModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.externalsample.ExternalSampleModel;
import com.ys.business.action.model.mouldreturnregister.MouldReturnRegisterModel;
import com.ys.business.action.model.processcontrol.ProcessControlModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;
import com.ys.business.service.mouldreturnregister.MouldReturnRegisterService;

@Controller
@RequestMapping("/business")
public class MouldReturnRegisterAction extends BaseAction {
	
	@Autowired
	MouldReturnRegisterService mouldReturnRegisterService;
	
	@RequestMapping(value="mouldreturnregister")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")MouldReturnRegisterModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		MouldReturnRegisterModel viewModel = null;
		
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
				rtnUrl = "/business/mouldreturnregister/mouldreturnregistermain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
			case "updateinit":
				rtnUrl = doGetMouldReturnRegisterBaseInfo(model, session, request, response);
				break;
			case "add":
			case "update":
				viewModel = doUpdate(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "delete":
				viewModel = doDelete(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "deleteDetail":
				viewModel = doDeleteDetail(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "getMouldLendRegisterList":
				dataMap = getMouldLendRegisterList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "confirmreturn":
				viewModel = doConfirmReturn(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldReturnRegisterService.doSearch(request, data, userInfo);
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
	
	public String doGetMouldReturnRegisterBaseInfo(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldReturnRegisterModel dataModel = new MouldReturnRegisterModel();

		try {
			dataModel = mouldReturnRegisterService.getMouldReturnRegisterBaseInfo(request);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldreturnregister/mouldreturnregisteredit";
	}		

	public HashMap<String, Object> getMouldLendRegisterList(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldReturnRegisterService.doGetMouldLendDetailList(request, data, userInfo);
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
	
	public MouldReturnRegisterModel doConfirmReturn(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MouldReturnRegisterModel model = new MouldReturnRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldReturnRegisterService.doConfirmReturn(request, data, userInfo);
		
		return model;
	}	
	
	public MouldReturnRegisterModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		MouldReturnRegisterModel model = new MouldReturnRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldReturnRegisterService.doUpdate(request, data, "0", userInfo);
		
		return model;
	}	
	
	public MouldReturnRegisterModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldReturnRegisterModel model = new MouldReturnRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldReturnRegisterService.doDelete(request, data, userInfo);

		return model;
	}

	public MouldReturnRegisterModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldReturnRegisterModel model = new MouldReturnRegisterModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldReturnRegisterService.doDelete(request, data, userInfo);
		
		return model;
	}
	
}
