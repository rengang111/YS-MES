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
import com.ys.business.service.order.BomService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;


@Controller
@RequestMapping("/business")
public class BomAction extends BaseAction {
	
	@Autowired BomService bomService;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	BomModel reqModel = new BomModel();
	Model model;
	
	@RequestMapping(value="/bom")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("bomForm") BomModel bom, 
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
			case "editBaseBom":
				doEditBaseBom();
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
			case "createOrderBom":
				doCreateOrderBom();
				//rtnUrl = "/business/bom/bomorderadd";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/bom/bomplanview";
				break;							
			case "orderDetail":
				doShowOrderDetail();
				rtnUrl = "/business/order/orderreviewadd";
				break;					
			case "purchasePlanView":
				//doShowBomDetail();
				rtnUrl = "/business/bom/bomselectlist";
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
			case "MultipleBomAdd":
				dataMap = doMultipleBomAdd();
				printOutJsonObj(response, dataMap);
				break;	
			case "changeBomAdd":
				doChangeBomAdd();
				rtnUrl = "/business/bom/basebomadd";
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
				rtnUrl = doInsertBaseBom();
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
			case "editQuotation":
				editQuotation();
				rtnUrl = "/business/bom/bombidadd";
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
			case "getDocumentary":
				dataMap = getDocumentary();
				printOutJsonObj(response, dataMap);
				break;
			case "orderexpenseadd":
				doShowOrder();
				rtnUrl = "/business/order/orderexpenseadd";
				break;
			case "CheckOrderCost1":
				CheckOrderCost1(data);;
				printOutJsonObj(response, dataMap);
				break;
			case "insertOrderCost1":
				insertOrderCost1(data);
				printOutJsonObj(response, dataMap);
				break;
			case "updateRebaterate"://更新退税率
				updateRebaterate();
				printOutJsonObj(response, dataMap);
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

		model.addAttribute("keyBackup", request.getParameter("keyBackup"));	
		bomService.createBaseBom();
		
	}
	public void doEditBaseBom() throws Exception{

		model.addAttribute("keyBackup", request.getParameter("keyBackup"));	
		bomService.editBaseBom();
		
	}
	public void doCreateOrderBom() throws Exception{
		
		bomService.createOrderBom();
		
	}
	
	public void doInsert() throws Exception {

		model = bomService.insertAndView();
		
	}		

	public String doInsertBaseBom() throws Exception {
		
		String rtnUrl = "/business/material/productview";
		bomService.insertBaseBomAndView();
		String keyBackup = request.getParameter("keyBackup");
		
		if(keyBackup!=null && keyBackup.equals("K")){
				
			rtnUrl = "/business/material/productsemiview";			
		}
		
		return rtnUrl;
		
	}
	
	public void createQuotation() throws Exception {

		model = bomService.createQuotation();
		
	}
	public void editQuotation() throws Exception {

		model = bomService.editQuotation();
		
	}
	public void insertQuotation() throws Exception {

		model = bomService.insertQuotation();
		
	}

	public void deleteQuotation() throws Exception {

		 bomService.deleteQuotation();
		
	}
	
	public HashMap<String, Object> doSearchBom() throws Exception{

		return bomService.getBomList();
		
	}	
	
	public void doEdit() throws Exception{

		model = bomService.editBomPlan();
	}
		
	
	public HashMap<String, Object> doMultipleBomAdd() throws Exception{

		return bomService.multipleBomAdd();
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
	public void doShowOrderDetail() throws Exception{
				
		bomService.getOrderDetail();
			
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
	
	public void insertOrderCost1(String data) throws Exception
	{
	  this.bomService.insertOrderCost1(data);
	}

	public void CheckOrderCost1(String data) throws Exception
	{
	  this.bomService.CheckOrderCost1(data);
	}
	
	public void updateRebaterate() throws Exception
	{
	  this.bomService.updateRebaterate();
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getDocumentary()
	{
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
	  try
	  {
	    dataMap = this.bomService.getDocumentary();

	    dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
	    if (dbData.size() == 0)
	      dataMap.put("message", "无符合条件的数据");
	  }
	  catch (Exception e)
	  {
	    System.out.println(e.getMessage());
	    dataMap.put("message", "操作失败，请再次尝试或联系管理员");
	  }

	  return dataMap;
	}

	public void doShowOrder() throws Exception {
	  this.model = this.bomService.getOrderInfo();
	}

	
}
