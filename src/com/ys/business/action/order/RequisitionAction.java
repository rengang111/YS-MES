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

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.action.model.order.RequisitionModel;
import com.ys.business.service.order.ArrivalService;
import com.ys.business.service.order.RequisitionService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class RequisitionAction extends BaseAction {
	
	@Autowired
	RequisitionService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	RequisitionModel reqModel = new RequisitionModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="requisition")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")RequisitionModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new RequisitionService(model,request,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		
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
				doInit();
				rtnUrl = "/business/inventory/requisitionmain";
				break;
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				return null;				
			case "contractArrivalSearch":
				dataMap = contractArrivalSearch(data);
				printOutJsonObj(response, dataMap);
				return null;	
			case "addinit":
				doAddInit();
				rtnUrl = "/business/inventory/requisitionadd";
				break;
			case "edit":
				doEdit();
				rtnUrl = "/business/inventory/arrivaledit";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/inventory/arrivalview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				return null;
			case "detailView":
				doShowDetail();
				//printOutJsonObj(response, viewModel.getEndInfoMap());
				rtnUrl = "/business/inventory/arrivalview";
				break;
			case "getArrivalHistory":
				dataMap = getArrivalHistory();
				printOutJsonObj(response, dataMap);
				return null;
			case "gotoArrivalView":
				gotoArrivalView();
				rtnUrl = "/business/inventory/arrivalview";
				break;
				
		}
		
		return rtnUrl;
	}	
	
	public void doInit(){	
			
		String keyBackup = request.getParameter("keyBackup");
		//没有物料编号,说明是初期显示,清空保存的查询条件
		if(keyBackup == null || ("").equals(keyBackup)){
			session.removeAttribute(Constants.FORM_REQUISITION+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REQUISITION+Constants.FORM_KEYWORD2);
		}else{
			model.addAttribute("keyBackup",keyBackup);
		}
		
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String keyBackup = request.getParameter("keyBackup");
		if(keyBackup != null && !("").equals(keyBackup)){
			session.removeAttribute(Constants.FORM_REQUISITION+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REQUISITION+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			dataMap = service.doSearch(data);
			
			ArrayList<HashMap<String, String>> dbData = 
					(ArrayList<HashMap<String, String>>)dataMap.get("data");
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
	public HashMap<String, Object> contractArrivalSearch(
			@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		//优先执行查询按钮事件,清空session中的查询条件
		String keyBackup = request.getParameter("keyBackup");
		if(keyBackup != null && !("").equals(keyBackup)){
			session.removeAttribute(Constants.FORM_ARRIVAL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_ARRIVAL+Constants.FORM_KEYWORD2);
			
		}
		try {
			dataMap = service.contractArrivalSearch(data);
			
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
	
	public void doAddInit(){
		try{
			service.addInit();
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void doInsert(){
		try{
			service.insertAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doEdit(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.getContractByArrivalId();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void doDelete(@RequestBody String data) throws Exception{
		
		service.doDelete(data);

	}
	
	public void doShowDetail() throws Exception{
		
		service.showArrivalDetail();

	}


	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> getArrivalHistory(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			String contractId = request.getParameter("contractId");
			dataMap = service.getArrivalHistory(contractId);
			
			ArrayList<HashMap<String, String>> dbData = 
					(ArrayList<HashMap<String, String>>)dataMap.get("data");
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
	
	public void gotoArrivalView(){
		try{
			String contractId = request.getParameter("contractId");
			//service.getContractDetail(contractId);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
