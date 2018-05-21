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
import com.ys.business.service.order.PurchaseOrderService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class PurchaseOrderAction extends BaseAction {
	
	@Autowired PurchaseOrderService service;
	@Autowired HttpServletRequest request;
	HttpSession session;
	
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
		
		this.service = new PurchaseOrderService(model,request,session,form,userInfo);
		this.reqModel = form;
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
				doInit(Constants.FORM_CONTRACT);
				rtnUrl = "/business/purchase/purchaseordermain";
				break;		
			case "search":
				dataMap = doSearch(data,Constants.FORM_CONTRACT);
				printOutJsonObj(response, dataMap);
				break;
			case "unfinishedInit":
				doInit(Constants.FORM_CONTRACT_UNFINISHED);
				rtnUrl = "/business/purchase/purchaseorderunfinishedmain";
				break;		
			case "unfinishedSearch":
				dataMap = doUnfinishedSearch(data,Constants.FORM_CONTRACT_UNFINISHED);
				printOutJsonObj(response, dataMap);
				break;
			case "creatPurchaseOrder":
				dataMap = creatPurchaseOrder(data);
				printOutJsonObj(response, dataMap);
				break;					
			case "getContract":
				dataMap = getContract();
				printOutJsonObj(response, dataMap);
				break;				
			case "getContractDetail":
				dataMap = getContractDetail();
				printOutJsonObj(response, dataMap);
				break;				
			case "getContractId":
				dataMap = getContractId();
				printOutJsonObj(response, dataMap);
				break;
			case "createZZ":
				creatPurchaseOrderZZ();
				rtnUrl = "/business/purchase/purchaseordermain";
				break;					
			case "detailView":
				doDetailView();
				rtnUrl = "/business/purchase/purchaseorderview";
				break;
			case "edit":
				editPurchaseOrder();
				rtnUrl = "/business/purchase/purchaseorderedit";
				break;				
			case "update":
				doUpdate();
				rtnUrl = "/business/purchase/purchaseorderview";
				break;	
			case "delete":
				deletePurchaseOrder();
				rtnUrl = "/business/purchase/purchaseordermain";
				break;
			case "createRoutineContractInit"://物料管理入口,直接采购
				createContractInit();
				rtnUrl = "/business/purchase/purchaseroutineadd";
				break;	
			case "createRoutineContract":
				createRoutineContract();
				rtnUrl = "/business/purchase/purchaseorderview";
				break;		
			case "createAcssoryContractInit"://配件订单直接下采购合同
				createAcssoryContractInit();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/purchase/purchaseraccessoryadd";
				break;	
			case "createAcssoryContract":
				createAcssoryContract();
				rtnUrl = "/business/purchase/purchaseorderview";
				break;	
			case "YZinit"://自制件任务查询
				doInit(Constants.FORM_CONTRACTZZ);
				rtnUrl = "/business/purchase/purchaseorderzzmain";
				break;		
			case "YZsearch"://自制件任务查询
				dataMap = doSearch(data,Constants.FORM_CONTRACTZZ);
				printOutJsonObj(response, dataMap);
				break;
			case "contractListByMaterialId":
				getContractListByMaterialId();
				rtnUrl = "/business/inventory/beginninginventorycontract";
				break;
		}
		
		return rtnUrl;		
	}
	
	public void doInit(String formId){	
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
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(
			String data,String formId){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);
			
		}
		try {
			dataMap = service.getContractList(data,formId);
			
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
	

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doUnfinishedSearch(
			String data,String formId){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);
			
		}
		try {
			dataMap = service.getUnfinishedContractList(data,formId);
			
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
	
	public void creatPurchaseOrderZZ() throws Exception{

		service.creatPurchaseOrderZZ();
	}
	
	public HashMap<String, Object> getContract() throws Exception{

		return service.getContractByYSId();
	}
	public HashMap<String, Object> getContractDetail() throws Exception{

		return service.getContractDetail();
	}

	public HashMap<String, Object> getContractId() throws Exception{

		return service.getContractId();
	}
	public HashMap<String, Object> creatPurchaseOrder(String inData) throws Exception{

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			service.creatPurchaseOrder(inData);
			dataMap.put(INFO, SUCCESSMSG);			
		}
		catch(Exception e) {
			dataMap.put(INFO, ERRMSG);
			System.out.println(e.getMessage());
		}
		return dataMap;
	}	

	public void doDetailView() throws Exception{

		String contractId = request.getParameter("contractId");
		String openFlag = request.getParameter("openFlag");
		String deleteFlag = request.getParameter("deleteFlag");

		model.addAttribute("openFlag",openFlag);//新窗口模式,不显示返回按钮
		model.addAttribute("deleteFlag",deleteFlag);//删除标识
		
		service.getContractDetailList(contractId);
		
	}
		
	
	public void doUpdate() throws Exception {
		
		service.updateAndView();			
		
	}
	
	public void editPurchaseOrder() throws Exception {
		
		service.editPurchaseOrder();
		
	}
	
	public void deletePurchaseOrder() throws Exception {
	
		service.deletePurchaseOrder();			
		
	}
		
	
	public void createContractInit() throws Exception {
		
		service.createContractInit();			
		
	}
	
	public void createRoutineContract() throws Exception {
		
		service.createRoutineContract();			
		
	}
	
	public void  createAcssoryContractInit() throws Exception
	{
		service.createAcssoryContractInit();
	 
	}
	public void  createAcssoryContract() throws Exception
	{
		service.createAcssoryContractAndView();
	 
	}
	
	public void getContractListByMaterialId(){	
		
		try {
			 service.getContractListByMaterialId();		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
