package com.ys.business.action.order;

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
import com.ys.business.action.model.customeraddr.CustomerAddrModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.order.CustomerAddrService;

@Controller
@RequestMapping("/business")
public class CustomerAddrAction extends BaseAction {
	
	@Autowired
	CustomerAddrService customerAddrService;
	
	@RequestMapping(value="customeraddr")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")CustomerAddrModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		CustomerAddrModel viewModel = null;
		
		if (type == null) {
			type = "";
		} else {
			int q = type.indexOf("?");
			if (q >= 0) {
				type = type.substring(0, q);
			}
		}
		
		switch(type) {
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				rtnUrl = doAddInit(model, session, request, response);
				break;
			case "add":
				viewModel = doAdd(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
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
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = customerAddrService.doSearch(request, data, userInfo);
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
		CustomerAddrModel CustomerAddrModel = customerAddrService.doAddInit(request);
		model.addAttribute("DisplayData", CustomerAddrModel);

		return "/business/customeraddr/customeraddredit";
	}
	
	public CustomerAddrModel doAdd(String data, HttpSession session, HttpServletRequest request){
		
		CustomerAddrModel model = new CustomerAddrModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = customerAddrService.doAdd(request, data, userInfo);
		
		return model;
	}		
	
	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		CustomerAddrModel dataModel = new CustomerAddrModel();
		String key = request.getParameter("key");
		try {
			dataModel = customerAddrService.getCustomerAddrDetailInfo(key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/customeraddr/customeraddredit";
	}	
	
	public CustomerAddrModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		CustomerAddrModel model = new CustomerAddrModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = customerAddrService.doUpdate(request, data, userInfo);
		
		return model;
	}	
	
	public CustomerAddrModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		CustomerAddrModel model = new CustomerAddrModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = customerAddrService.doDelete(data, userInfo);

		return model;
	}

	public CustomerAddrModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		CustomerAddrModel model = new CustomerAddrModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = customerAddrService.doDelete(data, userInfo);

		return model;
	}	


}
