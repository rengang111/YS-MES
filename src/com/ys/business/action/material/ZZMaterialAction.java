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

import com.ys.system.action.common.BaseAction;
import com.ys.business.action.model.material.ZZMaterialModel;
import com.ys.business.action.model.order.BomPlanModel;
import com.ys.business.service.material.ZZMaterialService;
import com.ys.business.service.order.BomService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;


@Controller
@RequestMapping("/business")
public class ZZMaterialAction extends BaseAction {
	
	@Autowired ZZMaterialService ZZService;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	ZZMaterialModel reqModel = new ZZMaterialModel();
	Model model;
	
	@RequestMapping(value="/zzmaterial")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("ZZMaterial") ZZMaterialModel bom, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		ZZService = new ZZMaterialService(model,request,bom,userInfo);
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
				rtnUrl = "/business/material/zzmaterialadd";
				break;				
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				break;							
			case "implement":
				rtnUrl = "/business/material/purchaseman";
				break;			
			case "searchPurchase":
				dataMap = doSearchPurchase(data);
				printOutJsonObj(response, dataMap);
				break;
			case "create":
				doCreate();
				rtnUrl = "/business/material/bomplanadd";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/material/bomplanview";
				break;							
			case "detailView":
				doShowBomDetail();
				rtnUrl = "/business/material/bomplanview";
				break;					
			case "purchasePlanView":
				//doShowBomDetail();
				rtnUrl = "/business/bom/bomselectlist";
				break;									
			case "bomCopyDetail":
				doShowBomDetail();
				rtnUrl = "/business/bom/bomcopyview";
				break;
			case "edit":
				doEdit();
				rtnUrl = "/business/bom/bomplanedit";
				break;				
			case "update":
				doUpdate();
				rtnUrl = "/business/bom/bomplanview";
				break;
			case "getSupplierPriceList"://供应商编号查询
				dataMap = doGetSupplierPriceList();
				printOutJsonObj(response, dataMap);
				break;
			case "getMaterialPriceList"://物料编号查询
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
			case "copy":
				doCopy();
				rtnUrl = "/business/bom/bomcopyadd";
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
			//dataMap = bomService.getBomList(data);
			
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
			//dataMap = bomService.getCopyBomList(data);
			
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
			//dataMap = bomService.getBomApproveList(data);
			
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
	
	public void doCopy() throws Exception{
			
		//model = bomService.copyBomPlan();
			
	}

	public void doCreate() throws Exception{
		
		//model = bomService.createBomPlan();
		
	}
	
	public void doInsert() throws Exception {

		//model = bomService.insertAndView();
		
	}		
	
	
	public void doEdit() throws Exception{

		//model = bomService.editBomPlan();
	}	
	
	public void doUpdate() throws Exception {
		
		//bomService.updateAndView();			
		
	}

	public void doShowBomDetail() throws Exception{
				
		//model = bomService.showBomDetail();
			
	}
	
	
	/*
	 * 
	 */	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doGetMaterialList(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			//dataMap = bomService.getMaterialList(request);
			
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
			//dataMap = bomService.getSupplierPriceList();
			
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
