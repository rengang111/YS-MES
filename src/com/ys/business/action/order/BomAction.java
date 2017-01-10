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
import com.ys.business.action.model.order.BomPlanModel;
import com.ys.business.service.order.BomService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;


@Controller
@RequestMapping("/business")
public class BomAction extends BaseAction {
	
	@Autowired BomService bomService;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	BomPlanModel reqModel = new BomPlanModel();
	Model model;
	
	@RequestMapping(value="/bom")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("bomForm") BomPlanModel bom, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		bomService = new BomService(model,request,bom,userInfo);
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
			case "baseBomInit":
				rtnUrl = "/business/bom/basebommain";
				break;					
			case "searchBaseBom":
				dataMap = doSearchBaseBom(data);
				printOutJsonObj(response, dataMap);
				break;
			case "createBaseBom":
				doCreateBaseBom();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/bom/basebomadd";
				break;				
			case "searchBom":
				dataMap = doSearchBom();
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
				rtnUrl = "/business/bom/bomplanadd";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/bom/bomplanview";
				break;							
			case "detailView":
				doShowBomDetail();
				rtnUrl = "/business/bom/bomplanview";
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
			case "updateBomPlan":
				doUpdateBomPlan();
				printOutJsonObj(response, dataMap);
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
			case "changeBomAdd":
				doChangeBomAdd();
				rtnUrl = "/business/bom/bomplanadd";
				break;	
			case "changeBomEdit":
				doChangeBomEdit();
				rtnUrl = "/business/bom/bomplanedit";
				break;	
			case "copy":
				doCopy();
				rtnUrl = "/business/bom/bomcopyadd";
				break;
			case "searchProductModel":
				doSearchProductModel();
				rtnUrl = "/business/bom/basebomadd";
				break;
			case "baseBomInsert":
				doInsertBaseBom();
				rtnUrl = "/business/material/productview";
				break;
			case "getBaseBom":
				dataMap = doShowBaseBom();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/bom/productview";
				break;
			case "getQuotationBom":
				dataMap = doShowQuotationBom();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/bom/productview";
				break;
				
		}
		
		return rtnUrl;		
	}
	
	public void doSearchProductModel(){
		
		
		try {
			bomService.getProductModel();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearch(
			@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = bomService.getBomList(data);
			
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
			dataMap = bomService.getBaseBomList(data);
			
			
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
			dataMap = bomService.getBomApproveList(data);
			
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
			
		bomService.getCopyBomList();
			
	}
	public void doCreateBaseBom() throws Exception{
		
		bomService.createBaseBom();
		
	}
	public void doCreate() throws Exception{
		
		model = bomService.createBomPlan();
		
	}
	
	public void doInsert() throws Exception {

		model = bomService.insertAndView();
		
	}		

	public void doInsertBaseBom() throws Exception {

		model = bomService.insertBaseBomAndView();
		
	}
	
	public HashMap<String, Object> doSearchBom() throws Exception{

		return bomService.getBomList();
		
	}	
	
	public void doEdit() throws Exception{

		model = bomService.editBomPlan();
	}
	
	public void doChangeBomAdd() throws Exception{

		model = bomService.changeBomPlanAdd();
	}	
	
	public void doChangeBomEdit() throws Exception{

		model = bomService.changeBomPlanEdit();
	}
	
	public void doUpdate() throws Exception {
		
		bomService.updateAndView();			
		
	}

	public void doUpdateBomPlan(){
		
		bomService.updateBomPlan();
	}
	public void doShowBomDetail() throws Exception{
				
		model = bomService.showBomDetail();
			
	}
	public HashMap<String, Object> doShowBaseBom() throws Exception{
		
		return  bomService.showBaseBomDetail();
			
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doShowQuotationBom() throws Exception{
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap =bomService.showQuotationBomDetail();
			
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
	 * 
	 */	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doGetMaterialList(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = bomService.getMaterialList();
			
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
			dataMap = bomService.getSupplierPriceList();
			
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
