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
import com.ys.business.action.model.order.ZPOrderModel;
import com.ys.business.service.order.ZPorderService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;


@Controller
@RequestMapping("/business")
public class ZPorderAction extends BaseAction {
	
	@Autowired ZPorderService zpOrderService;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	ZPOrderModel reqModel = new ZPOrderModel();
	Model model;
	
	@RequestMapping(value="/zporder")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("ZPOrder") ZPOrderModel form, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		zpOrderService = new ZPorderService(model,request,form,userInfo);
		reqModel = form;
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
			case "implement":
				rtnUrl = "/business/bom/purchaseman";
				break;	
			case "create":
				doCreate();
				rtnUrl = "/business/order/zporderadd";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/order/zporderview";
				break;		
			case "purchasePlanView":
				//doShowBomDetail();
				rtnUrl = "/business/bom/bomselectlist";
				break;	
			case "edit":
				doEdit();
				rtnUrl = "/business/order/zporderedit";
				break;				
			case "update":
				doUpdate();
				rtnUrl = "/business/order/zporderview";
				break;			
			case "approve":
				doApprove();
				rtnUrl = "/business/order/ordermain";
				break;
			case "getMaterialList"://物料编号查询
				dataMap = doGetMaterialList();
				printOutJsonObj(response, dataMap);
				break;	
			case "chooseSourseBom":
				//doViewSourseBom();
				rtnUrl = "/business/bom/bomselectlist";
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
	

	public void doCreate() throws Exception{
		
		 zpOrderService.createZPorder();
		
	}
	
	public void doInsert() throws Exception {

		zpOrderService.insertAndView();
		
	}		
	
	
	public void doEdit() throws Exception{

		zpOrderService.editZPorder();
	}	
	
	public void doUpdate() throws Exception {
		
		zpOrderService.updateAndView();			
		
	}

	
	public void doApprove() throws Exception {
		
		zpOrderService.approveAndView();			
		
	}

	/*
	 * 
	 */	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doGetMaterialList(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = zpOrderService.getZPMaterialList();
			
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
