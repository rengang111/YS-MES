package com.ys.business.action.order;

/**
 * 采购合同
 */
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
		String makeType = request.getParameter("makeType");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new PurchaseOrderService(model,request,session,form,userInfo);
		this.reqModel = form;
		this.model = model;
		this.session = session;
		model.addAttribute("makeType",makeType);
		
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
			case "purchaseSearchinit"://采购件合同
				doInit(Constants.FORM_CONTRACT_C);
				rtnUrl = "/business/purchase/purchaseordermain";
				break;		
			case "purchaseSearch":
				dataMap = doSearch(makeType,Constants.FORM_CONTRACT_C,data);
				printOutJsonObj(response, dataMap);
				break;
			case "yszzSearchinit"://自制件合同
				doInit(Constants.FORM_CONTRACT_Z);
				rtnUrl = "/business/purchase/purchaseordermain";
				break;		
			case "yszzSearch":
				dataMap = doSearch(makeType,Constants.FORM_CONTRACT_Z,data);
				printOutJsonObj(response, dataMap);
				break;
			case "partSearchinit"://包装件合同
				doInit(Constants.FORM_CONTRACT_B);
				rtnUrl = "/business/purchase/purchaseordermain";
				break;		
			case "partSearch":
				dataMap = doSearch(makeType,Constants.FORM_CONTRACT_B,data);
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
				//dataMap = doSearch(data,Constants.FORM_CONTRACTZZ);
				printOutJsonObj(response, dataMap);
				break;
			case "contractListByMaterialId":
				getContractListByMaterialId();
				rtnUrl = "/business/inventory/beginninginventorycontract";
				break;		
			case "getContractDeduct"://取得合同扣款明细
				dataMap = getContractDeduct();
				printOutJsonObj(response, dataMap);
				break;		
			case "updateContractDeduct"://更新合同的协商扣款
				updateContractDeduct();
				printOutJsonObj(response, dataMap);
				break;		
			case "checkContractDelete"://更新合同的协商扣款
				dataMap = checkContractDelete();
				printOutJsonObj(response, dataMap);
				break;
			case "goBackContractMainInit"://返回查询页面之前，取得页面参数
				goBackContractMainInit();
				rtnUrl = "/business/purchase/purchaseordermain";
				break;
				
		}
		
		return rtnUrl;		
	}
	
	//返回时，从session取值
	public void goBackContractMainInit(){	
			
		String methodtype = (String)session.getAttribute("methodtype");
		//
		if(methodtype == null || ("").equals(methodtype)){
			model.addAttribute("methodtype","purchaseSearchinit");
		}else{
			model.addAttribute("methodtype",methodtype);
		}
		
	}
	
	public void doInit(String formId){	

		String searchSts = (String) session.getAttribute("searchSts");
		String userId = (String) session.getAttribute("userId");
		String methodtype = request.getParameter("methodtype");
		String makeType = request.getParameter("makeType");
		//没有物料编号,说明是初期显示,清空保存的查询条件
		if(methodtype == null || ("").equals(methodtype)){
			model.addAttribute("methodtype","purchaseSearchinit");
		}else{
			model.addAttribute("methodtype",formId);
		}

		if(searchSts == null || ("").equals(searchSts)){
			searchSts = "1";//设置默认值：未入库
		}
		if(userId == null || ("").equals(userId)){
			userId = "999";//设置默认值：全员
		}
		
		model.addAttribute("userId",userId);		
		model.addAttribute("makeType",makeType);
		model.addAttribute("searchSts",searchSts);
		
		try {
			service.purchaseOrderMainInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(
			String makeType,String formId,String data){
		
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
			dataMap = service.getContractList(data,formId,makeType);
			
			dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
			if (dbData.size() == 0) {
				dataMap.put(INFO, NODATAMSG);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		String searchSts = request.getParameter("searchSts");
		session.setAttribute("searchSts", searchSts);
		
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
		String methodtype = (String)session.getAttribute("methodtype");
		
		service.getContractDetailList(contractId);
		
		model.addAttribute("methodtype",methodtype);//打开不同的页面
		model.addAttribute("openFlag",openFlag);//新窗口模式,不显示返回按钮
		model.addAttribute("deleteFlag",deleteFlag);//删除标识
		
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
	
	public HashMap<String, Object> getContractDeduct() throws Exception{	
		
		return service.getContractDeduct();		
	}
	
	public HashMap<String, Object> checkContractDelete() throws Exception{	
		
		return service.checkContractDelete();		
	}
	
	
	public void updateContractDeduct(){	
		
		try {
			 service.updateContractDeduct();		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	

}
