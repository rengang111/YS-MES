package com.ys.business.action.externalsample;

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
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;
import com.ys.business.service.externalsample.ExternalSampleService;

@Controller
@RequestMapping("/business")
public class ExternalSampleAction extends BaseAction {
	
	@Autowired
	ExternalSampleService externalsampleService;
	
	@RequestMapping(value="externalsample")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")ExternalSampleModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		ExternalSampleModel viewModel = null;
		
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
				rtnUrl = "/business/externalsample/externalsamplemain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				rtnUrl = doAddInit(model, session, request, response);
				//super.openFileBrowser(request, session, model);
				break;
			case "add":
				viewModel = doAdd(data, session, request);
				
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				super.openFileBrowser(request, session, model);
				break;
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
			case "openfilebrowser":
				super.openFileBrowser(request, session, model);
				return null;
			case "getfilelist":
				printOutJsonObj(response, super.getFolderList(request, session));
				return null;
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = externalsampleService.doSearch(request, data, userInfo);
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
	
	public String doAddInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		ExternalSampleModel externalsampleModel = externalsampleService.doAddInit(request);
		model.addAttribute("DisplayData", externalsampleModel);

		return "/business/externalsample/externalsampleedit";
	}
	
	public ExternalSampleModel doAdd(String data, HttpSession session, HttpServletRequest request){
		
		ExternalSampleModel extModel = new ExternalSampleModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		extModel = externalsampleService.doAdd(request, data, userInfo);
		
		return extModel;
	}		
	
	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		ExternalSampleModel dataModel = new ExternalSampleModel();
		String key = request.getParameter("key");
		try {
			dataModel = externalsampleService.getExternalSampleBaseInfo(key);
			dataModel = externalsampleService.getFileList(request, dataModel);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/externalsample/externalsampleedit";
	}	
	
	public ExternalSampleModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		ExternalSampleModel model = new ExternalSampleModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = externalsampleService.doUpdate(request, data, userInfo);
		
		return model;
	}	
	
	public ExternalSampleModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		ExternalSampleModel model = new ExternalSampleModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = externalsampleService.doDelete(request, data, userInfo);

		return model;
	}

	public ExternalSampleModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		ExternalSampleModel model = new ExternalSampleModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = externalsampleService.doDelete(request, data, userInfo);

		return model;
	}	


}
