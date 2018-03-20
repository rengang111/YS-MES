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

import com.ys.business.action.model.order.PurchasePlanModel;
import com.ys.business.service.order.PurchasePlanService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.system.action.common.BaseAction;


@Controller
@RequestMapping("/business")
public class PurchasePlanAction extends BaseAction {
	
	@Autowired PurchasePlanService purchaseService;
	@Autowired HttpServletRequest request;
	HttpSession session;
	
	UserInfo userInfo = new UserInfo();
	PurchasePlanModel reqModel = new PurchasePlanModel();
	Model model;
	
	@RequestMapping(value="/purchasePlan")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("attrForm") PurchasePlanModel plan, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		purchaseService = new PurchasePlanService(model,request,response,session,plan,userInfo);
		reqModel = plan;
		this.model = model;
		this.session = session;
		
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
				doInit(Constants.FORM_PURCHASEPLAN,session);
				rtnUrl = "/business/purchaseplan/purchaseplanmain";
				break;				
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "purchasePlan":
				//doShowBomDetail();
				rtnUrl = "/business/purchaseplan/purchaseplanedit";
				break;
			case "searchPurchase":
				dataMap = doSearchPurchase(data);
				printOutJsonObj(response, dataMap);
				break;
			case "purchasePlanAddInit"://订单采购
				rtnUrl = purchasePlanAddInit();
				break;
			case "purchasePlanAdd"://保存采购方案
				doPurchasePlanAdd();
				rtnUrl = "/business/purchaseplan/purchaseplanview";
				break;
			case "detailView":
				doShowPurchaseDetail();
				rtnUrl = "/business/purchaseplan/purchaseplanview";
				break;				
			case "purchasePlanView":
				dataMap = purchasePlanView();
				printOutJsonObj(response, dataMap);
				break;
			case "PurchaseView":
				doShowPurchaseDetail();
				rtnUrl = "/business/purchase/purchaseplan";
				break;
			case "purchasePlanEdit":
				doEdit();
				rtnUrl = "/business/purchaseplan/purchaseplanedit";
				break;				
			case "purchasePlanUpdate":
				doUpdate();
				rtnUrl = "/business/purchaseplan/purchaseplanview";
				break;	
			case "purchasePlanDeleteInit"://重置采购
				doDeleteInit();
				rtnUrl = "/business/purchaseplan/purchaseplanadd";
				break;		
			case "resetPackageInit"://重置包装件
				doResetPackageInit();
				rtnUrl = "/business/purchaseplan/purchaseplanadd";
				break;		
			case "resetYszzInit"://重置包装件
				doResetYszzInit();
				rtnUrl = "/business/purchaseplan/purchaseplanadd";
				break;			
			case "updateOrderCost":
				dataMap = doUpdateOrderCost();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/purchaseplan/purchaseplanview";
				break;		
			case "getRawMaterialList":
				dataMap = getRawMaterialList();
				printOutJsonObj(response, dataMap);
				break;	
			case "purchaseRoutineInit"://日常采购一览
				doInit(Constants.FORM_PURCHASEROUTINE,session);
				rtnUrl = "/business/purchaseplan/purchaseroutinemain";
				break;
			case "purchaseRoutineAddInit"://日常采购初始化
				purchaseRoutineAddInit(data);
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/purchaseplan/purchaseroutineadd";
				break;
			case "purchaseRoutineAdd"://日常采购
				purchaseRoutineAdd(data);
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/purchase/purchaseordermain";
				break;
			case "purchasePlanByMaterialId"://采购方案一览(采购件)
				getPurchasePlanByMaterialId();
				rtnUrl = "/business/inventory/beginninginventoryplan";
				break;
			case "purchasePlanForRawByMaterialId"://采购方案一览(原材料)
				getPurchasePlanForRawByMaterialId();
				rtnUrl = "/business/inventory/beginninginventoryplanraw";
				break;
				
		}
		
		return rtnUrl;		
	}
	
	public void doInit(String formId,HttpSession session){	
		/*
		String keyBackup = request.getParameter("keyBackup");
		//没有物料编号,说明是初期显示,清空保存的查询条件
		if(keyBackup == null || ("").equals(keyBackup)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);
		}else{
			model.addAttribute("keyBackup",keyBackup);
		}
		*/
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearch(String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PURCHASEPLAN+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PURCHASEPLAN+Constants.FORM_KEYWORD2);			
		}		
		
		try {
			dataMap = purchaseService.getOrderList(data);
			
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
			dataMap = purchaseService.getBomApproveList(data);
			
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


	public String purchasePlanAddInit() throws Exception{

		String rtnUrl = "/business/purchaseplan/purchaseplanadd";
		String flag = purchaseService.createBomPlan();
		
		if(flag.equals("查看"))
			rtnUrl = "/business/purchaseplan/purchaseplanview";
		
		return rtnUrl;
	}
	
	public void doPurchasePlanAdd() throws Exception {

		model = purchaseService.insertAndView();

	}		
	
	
	public void doEdit() throws Exception{
		
		purchaseService.editPurchasePlan();

	}	
	
	
	public void doDeleteInit() throws Exception{
		try{

			purchaseService.deleteInitPurchasePlan();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void doResetPackageInit() throws Exception{
		try{

			purchaseService.deleteInitPurchasePlan();

			model.addAttribute("materialFlag","package");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public void doResetYszzInit() throws Exception{
		try{

			purchaseService.deleteInitPurchasePlan();

			model.addAttribute("materialFlag","yszz");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void doUpdate() throws Exception {
		
		purchaseService.updateAndView();			

	}

	public HashMap<String, Object> doUpdateOrderCost() throws Exception {
		
		return purchaseService.updateOrderCost();			

	}


	public HashMap<String, Object> getRawMaterialList() throws Exception {
		
		return purchaseService.getRawMaterialList();			

	}


	public void doShowPurchaseDetail() throws Exception{
		
		purchaseService.showPurchaseDetail();
			
	}
	
	public HashMap<String, Object> purchaseRoutineAddInit(String data) throws Exception{
		
		return purchaseService.getPurchaseMaterialList(data);
			
	}

	public void purchaseRoutineAdd(String data) throws Exception{
		
		 purchaseService.purchaseRoutineAdd();
			
	}
	
	
	public HashMap<String, Object> purchasePlanView() throws Exception {
		
		return purchaseService.purchasePlanView();		
		
	}
	
	
	public void getPurchasePlanByMaterialId(){	
		
		try {
			purchaseService.getPurchasePlanByMaterialId();		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	
	public void getPurchasePlanForRawByMaterialId(){	
		
		try {
			purchaseService.getPurchasePlanForRawByMaterialId();		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
