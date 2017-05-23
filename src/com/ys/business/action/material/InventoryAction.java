package com.ys.business.action.material;

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

import com.ys.business.action.model.material.ArrivalModel;
import com.ys.business.action.model.material.MaterialModel;
import com.ys.business.service.material.ArrivalService;
import com.ys.business.service.material.InventoryService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;

@Controller
@RequestMapping("/business")
public class InventoryAction extends BaseAction {
	
	@Autowired
	InventoryService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	ArrivalModel reqModel = new ArrivalModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	
	@RequestMapping(value="inventory")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")ArrivalModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new InventoryService(model,request,dataModel,userInfo);
		this.reqModel = dataModel;
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
				rtnUrl = "/business/inventory/arrivalmain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				doAddInit();
				rtnUrl = "/business/inventory/arrivaladd";
				break;
			case "insert":
				doInsert();
				//printOutJsonObj(response, viewModel.getEndInfoMap());
				rtnUrl = "/business/inventory/arrivalview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				return null;
			case "getSemiProductStock":
				dataMap = getSemiProductStock();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/inventory/arrivalview";
				return null;
				
		}
		
		return rtnUrl;
	}	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			dataMap = service.doSearch(data);
			
			ArrayList<HashMap<String, String>> dbData = 
					(ArrayList<HashMap<String, String>>)dataMap.get("data");
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
	
	public void doAddInit(){
		try{
			service.addInit();
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void doInsert(){
		try{
			service.insertArrival();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doDelete(@RequestBody String data) throws Exception{
		
		service.doDelete(data);

	}
	
	public void doShowDetail() throws Exception{
		
		//service.showArrivalDetail();

	}
	

	public HashMap<String, Object> getSemiProductStock() throws Exception{
		
		return service.getSemiProductStock();

	}

}
