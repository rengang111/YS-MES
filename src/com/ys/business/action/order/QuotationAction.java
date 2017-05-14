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
import com.ys.business.action.model.order.BomModel;
import com.ys.business.action.model.order.QuotationModel;
import com.ys.business.service.order.BomService;
import com.ys.business.service.order.QuotationService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;


@Controller
@RequestMapping("/business")
public class QuotationAction extends BaseAction {
	
	@Autowired QuotationService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	QuotationModel reqModel = new QuotationModel();
	Model model;
	
	@RequestMapping(value="/quotation")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("bomForm") QuotationModel bom, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		service = new QuotationService(model,request,bom,userInfo);
		reqModel = bom;
		this.model = model;
		
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
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
				rtnUrl = "/business/bom/bomplanmain";
				break;				
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "getBaseBom":
				dataMap = doShowBaseBom();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/bom/productview";
				break;
			case "createQuotation":
				createQuotation();
				rtnUrl = "/business/bom/bombidadd";
				break;
			case "showQuotation":
				showQuotation();
				rtnUrl = "/business/bom/bombidview";
				break;
			case "insertQuotation":
				insertQuotation();
				rtnUrl = "/business/material/productview";
				break;
			case "deleteQuotation":
				deleteQuotation();
				//rtnUrl = "/business/material/productview";
				break;
			case "getQuotationBom":
				dataMap = doShowQuotationBom();
				printOutJsonObj(response, dataMap);
				break;
		}
		
		return rtnUrl;		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearch(
			@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			//dataMap = service.getBomList(data);
			
			dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
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

	
	public HashMap<String, Object> doSearchBaseBom(String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			//dataMap = service.getBaseBomList(data);
			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}


	public HashMap<String, Object> doShowBaseBom() throws Exception{
		
		return  service.showBaseBomDetail();
			
	}
	
	public void createQuotation() throws Exception {

		service.createQuotation();
		
	}
	public void showQuotation() throws Exception {

		 service.editQuotation();
		
	}
	public void insertQuotation() throws Exception {

		service.insertQuotation();
		
	}

	public void deleteQuotation() throws Exception {

		service.deleteQuotation();
		
	}	
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doShowQuotationBom() throws Exception{
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap =service.showQuotationBomDetail();
			
			dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
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
	
}
