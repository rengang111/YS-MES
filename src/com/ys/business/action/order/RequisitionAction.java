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
			case "addinit":
				doAddInit();
				rtnUrl = "/business/inventory/requisitionadd";
				break;
			case "updateInit":
				doUpdateInit();
				rtnUrl = "/business/inventory/requisitionedit";
				break;
			case "update":
				doUpdate();
				rtnUrl = "/business/inventory/requisitionview";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/inventory/requisitionview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				return null;
			case "detailView":
				dataMap = doShowDetail();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/inventory/requisitionview";
				rtnUrl = null;
				break;
			case "getRequisitionHistoryInit":
				doAddInit();
				rtnUrl = "/business/inventory/requisitionview";
				break;
			case "getRequisitionHistory":
				dataMap = getRequisitionHistory();
				printOutJsonObj(response, dataMap);
				return null;
			case "getRequisitionDetail":
				dataMap = getRequisitionDetail();
				printOutJsonObj(response, dataMap);
				return null;
			case "print"://领料单打印
				doPrintInit();
				rtnUrl = "/business/inventory/requisitionprint";
				break;
				
		}
		
		return rtnUrl;
	}	
	
	public void doInit(){	

	}	
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
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
	
	
	public void doAddInit(){
		try{
			service.addInit();
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


	public void doPrintInit(){
		try{
			service.addInit();
			model.addAttribute("userName", userInfo.getUserName());
			model.addAttribute("requisitionId", request.getParameter("requisitionId"));
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
	
	public void doUpdateInit(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.updateInit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	

	public void doUpdate(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.updateAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void doDelete(@RequestBody String data) throws Exception{
		
		service.doDelete(data);

	}
	
	public HashMap<String, Object> doShowDetail() throws Exception{
		
		return service.showDetail();

	}


	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> getRequisitionHistory(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			String YSId = request.getParameter("YSId");
			dataMap = service.getRequisitionHistory(YSId);
			
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
	public HashMap<String, Object> getRequisitionDetail(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			dataMap = service.getRequisitionDetail();
			
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
}
