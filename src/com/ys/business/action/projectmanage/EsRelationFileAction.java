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
import com.ys.business.action.model.esrelationfile.EsRelationFileModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.projectmanage.EsRelationFileService;

@Controller
@RequestMapping("/business")
public class EsRelationFileAction extends BaseAction {
	
	@Autowired
	EsRelationFileService esRelationFileService;
	
	@RequestMapping(value="esrelationfile")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")EsRelationFileModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		EsRelationFileModel viewModel = null;
		
		if (type == null) {
			type = "";
		} else {
			int q = type.indexOf("?");
			if (q >= 0) {
				type = type.substring(0, q);
			}
		}
		
		switch(type) {
			case "searchtestfile":
				dataMap = doSearch(data, session, request, response, 0);
				printOutJsonObj(response, dataMap);
				return null;
			case "searchmachinefile":
				dataMap = doSearch(data, session, request, response, 1);
				printOutJsonObj(response, dataMap);
				return null;				
			case "addtestinit":
				rtnUrl = doAddInit(model, session, request, response, 0);
				break;
			case "addmachineinit":
				rtnUrl = doAddInit(model, session, request, response, 1);
				break;				
			case "add":
				viewModel = doAdd(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "updatetestinit":
			case "updatemachineinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;				
			case "update":
				viewModel = doUpdate(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "deletetest":
			case "deletemachine":
			case "deleteDetail":	
				viewModel = doDelete(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response, int type){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = esRelationFileService.doSearch(request, data, userInfo, type);
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
	
	public String doAddInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response, int type){
		EsRelationFileModel EsRelationFileModel = esRelationFileService.doAddInit(request, type);
		model.addAttribute("DisplayData", EsRelationFileModel);

		return "/business/esrelationfile/esrelationfileedit";
	}
	
	public EsRelationFileModel doAdd(String data, HttpSession session, HttpServletRequest request){
		
		EsRelationFileModel model = new EsRelationFileModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = esRelationFileService.doAdd(request, data, userInfo);
		
		return model;
	}		
	
	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		EsRelationFileModel dataModel = new EsRelationFileModel();
		String key = request.getParameter("key");
		try {
			dataModel = esRelationFileService.getEsRelationFileDetailInfo(key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/esrelationfile/esrelationfileedit";
	}	
	
	public EsRelationFileModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		EsRelationFileModel model = new EsRelationFileModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = esRelationFileService.doUpdate(request, data, userInfo);
		
		return model;
	}	
	
	public EsRelationFileModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		EsRelationFileModel model = new EsRelationFileModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = esRelationFileService.doDelete(data, userInfo);

		return model;
	}


}
