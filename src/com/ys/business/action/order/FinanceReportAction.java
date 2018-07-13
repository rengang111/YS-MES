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
import com.ys.business.action.model.order.FinanceReportModel;
import com.ys.business.service.order.FinanceReportService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
/**
 * 财务报表
 * @author rengang
 *
 */
@Controller
@RequestMapping("/business")
public class FinanceReportAction extends BaseAction {
	
	@Autowired
	FinanceReportService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	FinanceReportModel reqModel = new FinanceReportModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="financereport")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")FinanceReportModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new 	FinanceReportService(model,request,response,session,dataModel,userInfo);
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
			case "reportForDaybookInit":
				rtnUrl = "/business/finance/reportfordaybook";
				break;
			case "reportForDaybookSsearch":
				dataMap = reportForDaybookSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "downloadExcel":
				downloadExcel();
				break;
			case "inventoryReportInit":
				rtnUrl = "/business/finance/reportformonthly";
				break;
			case "inventoryReportSearch":
				dataMap = inventoryReportSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "accountingInit":
				rtnUrl = "/business/finance/costaccoutingmain";
				break;
			case "costAccountingSsearch":
				dataMap = costAccountingSsearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "costAccountingYsid":
				rtnUrl = "/business/finance/costaccoutingadd";
				break;
			case "costConfirm":
				getOrderDetail();
				rtnUrl = "/business/finance/costaccoutingadd";
				break;
			case "getStockoutByMaterialId":
				dataMap = getStockoutByMaterialId();
				printOutJsonObj(response, dataMap);
			break;
				
		}
		
		return rtnUrl;
	}	
		
	
		
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> reportForDaybookSearch(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_REPORTFORDAYBOOK+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REPORTFORDAYBOOK+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.reportForDaybookSearch(data);
			
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
	public HashMap<String, Object> inventoryReportSearch(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_REPORTFORINVENTORY+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REPORTFORINVENTORY+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.inventoryMonthlySearch(data);
			
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
	public HashMap<String, Object> finishSearch(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PAYMENTAPPROVAL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PAYMENTAPPROVAL+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.finishSearch(data);
			
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
	
	private void downloadExcel() {		
		
		try {
			service.excelForInvertory();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> costAccountingSsearch(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_COSTACCOUTING+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_COSTACCOUTING+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.reportForDaybookSearch(data);
			
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

	private void getOrderDetail() {		
		
		try {
			service.getOrderDetailByYSId();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private HashMap<String, Object> getStockoutByMaterialId() throws Exception {		
		
		return	service.getStockoutByMaterialId();
		
	}
}
