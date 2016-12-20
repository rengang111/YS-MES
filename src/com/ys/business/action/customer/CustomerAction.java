package com.ys.business.action.customer;

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
import com.ys.system.action.model.login.UserInfo;
import com.ys.business.action.model.customer.CustomerModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.customer.CustomerService;

@Controller
@RequestMapping("/business")
public class CustomerAction extends BaseAction {
	
	@Autowired
	CustomerService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	CustomerModel reqModel = new CustomerModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	
	@RequestMapping(value="customer")
	public String execute(@RequestBody String data, @ModelAttribute("formModel")CustomerModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		CustomerModel viewModel = null;
		
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		service = new CustomerService(model,request,dataModel,userInfo);
		reqModel = dataModel;
		this.model = model;
		this.response = response;
		
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
				rtnUrl = "/business/customer/customermain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				rtnUrl = doAddInit();
				rtnUrl = "/business/customer/customeredit";
				break;
			case "insert":
				doInsert();
				//printOutJsonObj(response, viewModel.getEndInfoMap());
				rtnUrl = "/business/customer/customerview";
				break;
			case "showDetail":
				doShowDetail();
				rtnUrl = "/business/customer/customerview";
				break;
			case "edit":
				doEdit();
				rtnUrl = "/business/customer/customeredit";
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
			case "optionChange":
				doOptionChange();
				printOutJsonObj(response, modelMap);
				return null;
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = service.doSearch(request, data, userInfo);
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
	
	public String doAddInit(){
		try{
		model = service.doAddInit();
		//model.addAttribute("DisplayData", customerModel);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "/business/customer/customeredit";
	}
	
	public void doInsert(){
		
		service.insertAndView();
		
	}		
	
	public void doShowDetail(){

		String key = request.getParameter("key");
		try {
			model = service.getCustomerByRecordId(key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		//model.addAttribute("DisplayData", dataModel);
		
	}	
	
	public void doEdit(){

		try {
			model = service.editInit();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		//model.addAttribute("DisplayData", dataModel);
		
	}	
	
	public CustomerModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		CustomerModel model = new CustomerModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		//model = service.doUpdate(request, data, userInfo);
		
		return model;
	}	
	
	public CustomerModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		CustomerModel model = new CustomerModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = service.doDelete(data, userInfo);

		return model;
	}

	public CustomerModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		CustomerModel model = new CustomerModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = service.doDelete(data, userInfo);

		return model;
	}	
	
	public void doOptionChange() throws Exception{
		
		modelMap = service.getCustomerId();

	}


}
