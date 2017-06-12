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
import com.ys.business.action.model.order.RequirementModel;
import com.ys.business.service.order.RequirementService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class RequirementAction extends BaseAction {
	
	@Autowired RequirementService service;
	@Autowired HttpServletRequest request;
	HttpSession session;
	
	UserInfo userInfo = new UserInfo();
	RequirementModel reqModel = new RequirementModel();
	Model model;
	
	@RequestMapping(value="/requirement")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("attrForm") RequirementModel form, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new RequirementService(model,request,session,form,userInfo);
		this.reqModel = form;
		this.session = session;
		this.request = request;
		
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
			case "createRequirement"://订单采购需求
				rtnUrl = createRequirement();
				break;		
			case "insert":
				doInsert();
				rtnUrl = "/business/requirement/requirementview";
				break;					
			case "purchasePlanView":
				purchasePlanView();
				printOutJsonObj(response, dataMap);
				rtnUrl = "/business/requirement/purchaseplanview";
				break;
			case "editRequirement"://编辑采购方案
				doEditRequirement();
				rtnUrl = "/business/requirement/requirementedit";
				break;
			case "resetRequirement":
				doRestRequirement();
				rtnUrl = "/business/requirement/requirementadd";
				break;
			case "editZZ":
				doEditZZ();
				rtnUrl = "/business/requirement/rawrequirementedit";
				break;	
			case "editRaw":
				doEdit();
				rtnUrl = "/business/requirement/rawrequirementedit";
				break;
			case "editPart":
				doEditPart();
				rtnUrl = "/business/requirement/requirementedit";
				break;				
			case "approve":
				//doApprove();
				rtnUrl = "/business/order/ordermain";
				break;
			case "getzzMaterial"://订单中的自制品原材料合并数据
				dataMap = getzzMaterial();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/requirement/requirementedit";
				break;				
			case "insertProcurement"://保存采购方案
				doInsertProcurement();
				rtnUrl = "/business/requirement/requirementview3";
				break;				
			case "cansolEditPlan"://取消编辑采购方案,返回到采购方案查看页面
				cansolEditPlan();
				rtnUrl = "/business/requirement/requirementview3";
				break;				
			case "updateProcurement":
				doUpdateProcurement();
				rtnUrl = "/business/requirement/requirementview";
				break;		
			case "getBaseBom":
				dataMap = doShowBaseBom();
				printOutJsonObj(response, dataMap);
				break;					
			case "printRequirement":
				orderBomPrint();
				printOutJsonObj(response, dataMap);
				rtnUrl = "/business/requirement/requirementprint";
				break;				
			case "contractPrint":
				getRequriementBySupplier();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/requirement/contractprint";
				break;				
			case "getRequriementBySupplier":
				getRequriementBySupplier();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/requirement/contractprint";
				break;								
			case "productSemiUsed":
				productSemiUsed();
				rtnUrl = "/business/requirement/requirementview";
				break;							
			case "getOrderBom":
				dataMap = getOrderBom();
				printOutJsonObj(response, dataMap);
				rtnUrl = null;
				break;						
			case "updateOrderBomQuantity"://更新订单BOM的使用量
				updateOrderBomQuantity();
				printOutJsonObj(response, dataMap);
				rtnUrl = null;
				break;					
			case "createPurchasePlan"://生成采购方案
				createPurchasePlan();
				rtnUrl = "/business/requirement/requirementview2";
				break;				
			case "createOrderBom"://生成订单BOM
				createOrderBom();
				rtnUrl = "/business/requirement/requirementview2";
				break;
			case "createPurchasePlanFromBaseBom"://生成采购方案ajax
				dataMap = createPurchasePlanFromBaseBom();
				printOutJsonObj(response, dataMap);
				break;
			case "creatPurchaseOrder"://生成采购合同
				creatPurchaseOrder();
				rtnUrl = "/business/requirement/requirementview4";
				break;
			case "purchasePlanListInit"://采购方案一览
				doInit(Constants.FORM_PURCHASEPLAN);
				rtnUrl = "/business/requirement/purchaseplanmain";
				break;
			case "getPurchasePlanList":
				dataMap = getPurchasePlanList(data);
				printOutJsonObj(response, dataMap);
				break;
			case "orderBomInit"://采购方案一览
				doInit(Constants.FORM_ORDERBOM);
				rtnUrl = "/business/requirement/purchaseplanmain";
				break;
			case "getOrderBomList":
				dataMap = getOrderBomList(data);
				printOutJsonObj(response, dataMap);
				break;
				
		}
		
		return rtnUrl;		
	}
	
	@SuppressWarnings("deprecation")
	public void doInit(String formId){	
			
		String keyBackup = request.getParameter("keyBackup");
		//没有物料编号,说明是初期显示,清空保存的查询条件
		if(keyBackup == null || ("").equals(keyBackup)){
			session.removeValue(formId+Constants.FORM_KEYWORD1);
			session.removeValue(formId+Constants.FORM_KEYWORD2);
		}
		
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
	
	@SuppressWarnings("deprecation")
	public HashMap<String, Object> getPurchasePlanList(String data) throws Exception{
		//优先执行查询按钮事件,清空session中的查询条件
		String keyBackup = request.getParameter("keyBackup");
		if(keyBackup != null && !("").equals(keyBackup)){
			session.removeValue(Constants.FORM_PURCHASEPLAN+Constants.FORM_KEYWORD1);
			session.removeValue(Constants.FORM_PURCHASEPLAN+Constants.FORM_KEYWORD2);
			
		}
		return service.getPurchasePlanList(data);
			
	}

	public void purchasePlanView() throws Exception {
		
		service.getPurchasePlan();		
		
	}		
	
	
	@SuppressWarnings("deprecation")
	public HashMap<String, Object> getOrderBomList(String data) throws Exception{
		//优先执行查询按钮事件,清空session中的查询条件
		String keyBackup = request.getParameter("keyBackup");
		if(keyBackup != null && !("").equals(keyBackup)){
			session.removeValue(Constants.FORM_ORDERBOM+Constants.FORM_KEYWORD1);
			session.removeValue(Constants.FORM_ORDERBOM+Constants.FORM_KEYWORD2);
			
		}
		return service.getOrderBomList(data);
			
	}

	
	public String createRequirement() throws Exception {
		
		service.createOrView();
		
		String YSId=request.getParameter("YSId");
		
		boolean flg = service.checkContractExsit(YSId);	
		//默认到初始页面(新建订单BOM)
		String rtnUrl= "/business/requirement/requirementadd";
		if(flg){//合同check
			service.checkOrderBomExsit(YSId);//订单BOM编号取得
			return rtnUrl = "/business/requirement/requirementview4";
		}
		flg = service.checkPurchaseExsit(YSId);
		if(flg){//采购方案check
			service.checkOrderBomExsit(YSId);//订单BOM编号取得
			return rtnUrl = "/business/requirement/requirementview3";
		}
		flg = service.checkOrderBomExsit(YSId);
		if(flg){//订单BOMcheck
			return rtnUrl = "/business/requirement/requirementview2";
		}
		
		return rtnUrl;
	}	
	
	public void doEditRequirement() throws Exception{

		service.editRequirement();
	}
	
	public void doRestRequirement() throws Exception{

		service.resetRequirement();
	}
	
	public void doInsert() throws Exception {

		service.insertAndView();
		
	}		
	
	
	public void doEditZZ() throws Exception{

		service.editZZorder();
	}
	
	public void doEdit() throws Exception{

		service.editorder();
	}
	
	public void doEditPart() throws Exception{

		service.editorderPart();
	}	
	
	public void doInsertProcurement() throws Exception {
		
		service.insertProcurement();			
		
	}


	public void cansolEditPlan() throws Exception {
		
		service.cansolEditPlan();			
		
	}
	
	public void doUpdateProcurement() throws Exception {
		
		service.updateProcurement();			
		
	}

	public void orderBomPrint() throws Exception {
		
		service.printProcurement();			
		
	}
	
	public HashMap<String, Object> getzzMaterial() throws Exception {

		//HashMap<String, Object> dataMap = new HashMap<String, Object>();
		String bomId = request.getParameter("bomId");
		return service.getZZMaterial(bomId);	
			
		
	}
	public HashMap<String, Object> getRequriementBySupplier() throws Exception {

		return service.getRequriementBySupplier();				
		
	}	

	public void productSemiUsed() throws Exception{

		service.productSemiUsed();
	}


	public void createPurchasePlan() throws Exception{

		service.createPurchasePlan();
	}
	
	public void createOrderBom() throws Exception{

		service.createOrderBom();
	}
	
	public void creatPurchaseOrder() throws Exception{

		service.creatPurchaseOrder();
	}
	
	
	public HashMap<String,Object> createPurchasePlanFromBaseBom() throws Exception{

		return service.createPurchasePlanFromBaseBom();
	}

	public HashMap<String, Object> getOrderBom() throws Exception{
		
		return  service.getOrderBomDetail();
			
	}
	

	public void updateOrderBomQuantity() throws Exception{
		
		service.updateOrderBomQuantity();
			
	}
	
	public HashMap<String, Object> doShowBaseBom() throws Exception{
		
		return  service.showBaseBomDetail();
			
	}
}
