package com.ys.business.action.order;

import java.text.ParseException;
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
import com.ys.util.CalendarUtil;
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
				doInit();
				rtnUrl = "/business/finance/reportfordaybook";
				break;
			case "reportForDaybookSsearch":
				dataMap = reportForDaybookSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "costAccountingFromBom"://没有采购方案，从BOM核算
				dataMap = costSearchFromBom(data);
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
			case "accountingInit"://财务核算初始化
				accountingInit();
				rtnUrl = "/business/finance/costaccoutingmain";
				break;
			case "costAccountingSsearch"://财务核算查询
				dataMap = costAccountingSsearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "costAccountingYsid":
				rtnUrl = "/business/finance/costaccoutingysid";
				break;
			case "costAccountingAdd":
				costAccountingAdd();
				rtnUrl = "/business/finance/costaccoutingadd";
				break;
			case "costAccountingPeiAdd"://配件订单
				costAccountingPeiAdd();
				rtnUrl = "/business/finance/costaccoutingpeiadd";
				break;
			case "costAccountingSave":
				costAccountingSave();
				rtnUrl = "/business/finance/costaccoutingview";
				break;
			case "costBomDetailView"://查看核算详情
				costBomDetailView();
				rtnUrl = "/business/finance/costaccoutingview";
				break;
			case "getStockoutByMaterialId":
				dataMap = getStockoutByMaterialId();
				printOutJsonObj(response, dataMap);
				break;
			case "getOrderPeiByMaterialId":
				dataMap = getOrderPeiByMaterialId();
				printOutJsonObj(response, dataMap);
				break;
			case "getCostBomDetail":
				dataMap = getCostBomDetail();
				printOutJsonObj(response, dataMap);
				break;	
			case "costAccountingEdit"://财务核算：编辑costAccountingUpdate
				 costAccountingEdit();
				rtnUrl = "/business/finance/costaccoutingedit";
				break;	
			case "costAccountingUpdate"://财务核算：编辑
				costAccountingUpdate();
				rtnUrl = "/business/finance/costaccoutingview";
				break;		
			case "updateExchangeRate":
				dataMap = updateExchangeRate();
				printOutJsonObj(response, dataMap);
				break;	
			case "downloadExcelForCostAccounting"://财务核算数据下载
				downloadExcelForCostAccounting(data);
				break;	
			case "downloadExcelForCostStatistics"://财务月度统计数据下载
				downloadExcelForCostStatistics(data);
				break;
			case "cancelCostInit"://不参与核算
				cancelCostInit();
				rtnUrl = "/business/finance/costconceladd";
				break;	
			case "cancelCost"://不参与核算
				dataMap = cancelCost();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/finance/costconceladd";
				break;	
			case "monthlyStatisticsInit"://月度统计初始化
				monthlyStatisticsInit();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/finance/costaccoutingstatistics";
				break;	
			case "monthlyStatistics"://月度统计
				dataMap = monthlyStatistics();
				printOutJsonObj(response, dataMap);
				break;
			case "reportForDaybookByMaterialIdInit":
				reportForDaybookByMaterialIdInit();
				rtnUrl = "/business/inventory/materialstoragehistory";
				break;
			case "reportForDaybookByMaterialIdSsearch":
				dataMap = reportForDaybookByMaterialIdSearch(data);
				printOutJsonObj(response, dataMap);
				break;

		}
		
		return rtnUrl;
	}	
		
	public void doInit(){	
		String materialId = request.getParameter("materialId");
		if(materialId == null)
			materialId = "";
		model.addAttribute("materialId",materialId);
		try {
			service.reportForDaybookInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public HashMap<String, Object> reportForDaybookByMaterialIdSearch(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			dataMap = service.reportForDaybookByMaterialId(data);
			
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
	public HashMap<String, Object> costSearchFromBom(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
				
		try {
			dataMap = service.getBaseBomCostByYsid(data);
			
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
	
	
	private void downloadExcelForCostAccounting(String data) {		
		
		try {
			service.downloadExcelForAccounting(data);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

	private void downloadExcelForCostStatistics(String data) {		
		
		try {
			service.downloadExcelForAStatistics(data);
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
			dataMap = service.costAccountingSearch(data);
			
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
		
		String monthday = request.getParameter("monthday");
		String statusFlag = request.getParameter("statusFlag");
		String orderType = request.getParameter("orderType");
		
		session.setAttribute("monthday",monthday);
		session.setAttribute("statusFlag",statusFlag);
		session.setAttribute("orderType",orderType);
		
		return dataMap;
	}

	private void costAccountingAdd() {		
		
		try {			

			//String monthday = request.getParameter("monthday");
			//String statusFlag = request.getParameter("statusFlag");
			//session.setAttribute("monthday", monthday);
			//session.setAttribute("statusFlag", statusFlag);
			
			service.costAccountingAdd();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void costAccountingPeiAdd() {		
		
		try {			

			//String monthday = request.getParameter("monthday");
			//String statusFlag = request.getParameter("statusFlag");
			//session.setAttribute("monthday", monthday);
			//session.setAttribute("statusFlag", statusFlag);
			
			service.costAccountingPeiAdd();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void costAccountingSave() {		
		
		try {
			service.insertCostBomAndView();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void costBomDetailView() throws Exception {		
		
		//String monthday = request.getParameter("monthday");
		//String statusFlag = request.getParameter("statusFlag");
		//session.setAttribute("monthday", monthday);
		//session.setAttribute("statusFlag", statusFlag);
		
		service.getOrderAndCostBomDetail();
		
	}
	
	private HashMap<String, Object> getStockoutByMaterialId() throws Exception {		
		
		return	service.getStockoutDetail();
		
	}

	private HashMap<String, Object> getOrderPeiByMaterialId() throws Exception {		
		
		return	service.getOrderPeiByMaterialId();
		
	}
	private HashMap<String, Object> getCostBomDetail() throws Exception {		
		
		return	service.getCostBomDetail();
		
	}
	

	private void costAccountingEdit() throws Exception {		
		
		service.getOrderAndCostBomDetail();
		
	}
	

	private void costAccountingUpdate() throws Exception {		
		
		service.updateCostBomAndView();
		
	}
	
	
	private void accountingInit() throws Exception {		
		
		String monthday = (String) session.getAttribute("monthday");
		if(monthday == null || monthday == "")
			monthday = CalendarUtil.getLastDate();//默认是前一月
		
		String statusFlag = (String) session.getAttribute("statusFlag");
		if(statusFlag == null || statusFlag == "")
			statusFlag = "A";//默认是ALL
		

		String orderType = (String) session.getAttribute("orderType");//常规OR配件
		if(orderType == null || ("").equals(orderType))
			orderType = "010";//设置默认值：常规
		
		model.addAttribute("orderType",orderType);
		model.addAttribute("monthday",monthday);
		model.addAttribute("statusFlag",statusFlag);
		

		try {
			service.costAccountingInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public HashMap<String, Object> updateExchangeRate() throws Exception {
		
		return service.updateExchangeRate();			

	}
	
	public void cancelCostInit(){
		String ysid = request.getParameter("YSId");
		model.addAttribute("YSId",ysid);
	}
	
	public HashMap<String, Object> cancelCost(){
		HashMap<String, Object> map = new HashMap<>();
		try {
			service.updateOrderDetailForCostConcel();
			map.put(INFO, SUCCESSMSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put(INFO, ERRMSG);
		}
		
		return map;
	}
	
	public void monthlyStatisticsInit(){
		try {
			service.monthlyStatisticsInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public HashMap<String, Object> monthlyStatistics(){
		HashMap<String, Object> map = new HashMap<>();
		try {
			map = service.monthlyStatistics();
			map.put(INFO, SUCCESSMSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put(INFO, ERRMSG);
		}
		
		return map;
	}
	private void reportForDaybookByMaterialIdInit() throws Exception{
		String materialId= request.getParameter("materialId");
		model.addAttribute("materialId",materialId);
		
		service.reportForDaybookByMaterialIdInit();
	}
}
