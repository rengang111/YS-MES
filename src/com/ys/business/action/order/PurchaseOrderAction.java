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
import com.ys.business.action.model.order.PurchaseOrderModel;
import com.ys.business.action.model.order.RequirementModel;
import com.ys.business.service.order.PurchaseOrderService;
import com.ys.business.service.order.RequirementService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;

@Controller
@RequestMapping("/business")
public class PurchaseOrderAction extends BaseAction {
	
	@Autowired PurchaseOrderService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	PurchaseOrderModel reqModel = new PurchaseOrderModel();
	Model model;
	
	@RequestMapping(value="/contract")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("attrForm") PurchaseOrderModel form, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new PurchaseOrderService(model,request,form,userInfo);
		this.reqModel = form;
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
				rtnUrl = "/business/requirement/requirementmain";
				break;				
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				break;							
			case "implement":
				rtnUrl = "/business/bom/purchaseman";
				break;			
			case "searchPurchase":
				dataMap = doSearchPurchase(data);
				printOutJsonObj(response, dataMap);
				break;
			case "create":
				doCreate();
				rtnUrl = "/business/order/zzorderadd";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/requirement/requirementview";
				break;					
			case "purchasePlanView":
				//doShowBomDetail();
				rtnUrl = "/business/bom/bomselectlist";
				break;
			case "edit":
				//doEdit();
				rtnUrl = "/business/purchase/purchaseorderedit";
				break;				
			case "update":
				doUpdate();
				rtnUrl = "/business/order/zzorderview";
				break;				
			case "approve":
				doApprove();
				rtnUrl = "/business/order/ordermain";
				break;
			case "getSupplierPriceList"://供应商编号查询
				dataMap = doGetSupplierPriceList();
				printOutJsonObj(response, dataMap);
				break;
			case "getMaterialList"://物料编号查询
				dataMap = doGetMaterialList();
				printOutJsonObj(response, dataMap);
				break;	
			case "chooseSourseBom":
				//doViewSourseBom();
				rtnUrl = "/business/bom/bomselectlist";
				break;
			case "searchCopyBom":
				dataMap = doSearchCopyBom(data);
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/bom/bomselectlist";
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
			//dataMap = zzOrderService.getBomList(data);
			
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

	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearchCopyBom(
			@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			//dataMap = zzOrderService.getCopyBomList(data);
			
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

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearchPurchase(
			@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			//dataMap = zzOrderService.getBomApproveList(data);
			
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
	
	

	public void doCreate() throws Exception{
		
		//model = service.createZZorder();
		
	}
	
	public void doInsert() throws Exception {

		service.insertAndView();
		
	}		
	
	
	public void doEdit() throws Exception{

		service.editZZorder();
	}	
	
	public void doUpdate() throws Exception {
		
		service.updateAndView();			
		
	}
	
	public void doApprove() throws Exception {
		
		service.approveAndView();			
		
	}	
	
	/*
	 * 
	 */	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doGetMaterialList(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = service.getZZMaterialList();
			
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
	
	/*
	 * doSupplierPriceList
	 */	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doGetSupplierPriceList(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			//dataMap = zzOrderService.getSupplierPriceList();
			
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
