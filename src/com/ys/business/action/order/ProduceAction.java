/**
 * 生产任务
 */
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
import com.ys.business.action.model.order.ProduceModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.business.service.order.ProduceService;
import com.ys.system.action.model.login.UserInfo;


@Controller
@RequestMapping("/business")
public class ProduceAction extends BaseAction {
	
	@Autowired ProduceService orderService;
	@Autowired HttpServletRequest request;
	
	ProduceModel reqModel= new ProduceModel();
	UserInfo userInfo = new UserInfo();
	Model model;
	HttpSession session;
	HttpServletResponse response;
	
	@RequestMapping(value="/produce")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("produceForm") ProduceModel order, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		orderService = new ProduceService(model,request,response,session,order,userInfo);
		reqModel = order;
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
				doInit(Constants.FORM_PRODUCETASK,session);
				rtnUrl = "/business/produce/producetaskmain";
				break;
			case "search":
				dataMap = doSearchOrderList(Constants.FORM_PRODUCETASK,data);
				printOutJsonObj(response, dataMap);
				break;
			case "productTaskMerge"://订单合并
				dataMap = produceTracMerge();
				printOutJsonObj(response, dataMap);
				break;
			case "productTaskOrderHide"://订单隐藏
				dataMap = productTaskOrderHide();
				printOutJsonObj(response, dataMap);
				break;
			case "productTaskOrderShow"://订单显示
				dataMap = productTaskOrderShow();
				printOutJsonObj(response, dataMap);
				break;
			case "produceLineCodeInit":
				doProduceLineCodeInit();
				rtnUrl = "/business/produce/producelinemain";
				break;
			case "produceLineCodeSearch":
				dataMap = doProduceLineCodeSearch(Constants.FORM_PRODUCELINECODE,data);
				printOutJsonObj(response, dataMap);
				break;	
			case "addTopInit":
				addTopInit();
				rtnUrl = "/business/produce/producelineadd";
				break;	
			case "addWarehouseCode":
				dataMap = addWarehouseCode();
				printOutJsonObj(response, dataMap);
				break;
			case "editWarehouseCode":
				doEdit();
				rtnUrl = "/business/produce/producelineedit";
				break;
			case "updateWarehouseCode":
				dataMap = updateWarehouseCode();
				printOutJsonObj(response, dataMap);
				break;
			case "deleteWarehouseCode":
				dataMap = deleteWarehouseCode();
				printOutJsonObj(response, dataMap);
				break;
			case "producePlanMainSearchInit":
				doProducePlanSearchInit();
				rtnUrl = "/business/produce/produceplanmain";
				break;
			case "producePlanMainSearch":
				dataMap = doProducePlanMainSearchSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "setProduceLineById":
				dataMap = doSetProduceLineById();
				printOutJsonObj(response, dataMap);
				break;
			case "getProduceLineList":
				dataMap = doGetProduceLineList();
				printOutJsonObj(response, dataMap);
				break;
			case "setProducePlanSortNo":
				dataMap = doSetProducePlanSortNo();
				printOutJsonObj(response, dataMap);
				break;
			case "setOrderForProduce"://设置生产计划过滤数据
				dataMap = doSetOrderForProduce();
				printOutJsonObj(response, dataMap);
				break;
		}
		
		return rtnUrl;		
	}
	

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doProducePlanMainSearchSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_REQUISITION+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REQUISITION+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			dataMap = orderService.producePlanSearch(data);
			
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
		

		String requisitionSts = request.getParameter("requisitionSts");
		session.setAttribute("requisitionSts", requisitionSts);
		
		return dataMap;
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

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearchOrderList(String formId, String data){
		
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
			dataMap = orderService.getOrderList(formId,data);
			
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
	

	public HashMap<String, Object> produceTracMerge(){	

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			modelMap = orderService.produceTracMerge();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	

	public HashMap<String, Object> productTaskOrderHide(){	

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			modelMap = orderService.productTaskOrderHide();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	
	public HashMap<String, Object> productTaskOrderShow(){	

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			modelMap = orderService.productTaskOrderShow();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}

	
	public void doProduceLineCodeInit(){	
		
		try {
			orderService.produceLineCodeSearchInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doProduceLineCodeSearch(String formId, String data){
		
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
			dataMap = orderService.produceLineCodeSearch(formId,data);
			
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
	
	public HashMap<String, Object> deleteWarehouseCode() throws Exception{

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try{
			orderService.deleteWarehouseCode();
			dataMap.put(INFO, "SUCCESSMSG");
		}catch(Exception e){
			dataMap.put(INFO, "ERRMSG");
			e.printStackTrace();
		}

		return dataMap;
	}
	

	public HashMap<String, Object> updateWarehouseCode(){

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try{
			orderService.updateWarehouseCode();
			dataMap.put(INFO, "SUCCESSMSG");
		}catch(Exception e){
			dataMap.put(INFO, "ERRMSG");
			e.printStackTrace();
		}
		
		return  dataMap;
		
	}
	

	public void addTopInit(){

		try{
			orderService.getParentCodeDetail();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public HashMap<String, Object> addWarehouseCode(){

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try{
			orderService.insertWarehouseCode();
			dataMap.put(INFO, "SUCCESSMSG");
		}catch(Exception e){
			dataMap.put(INFO, "ERRMSG");
			e.printStackTrace();
		}
		
		return  dataMap;
		
	}
	

	public void doEdit(){
		try{
			orderService.getParentCodeDetail();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doProducePlanSearchInit(){	
		
		String searchFlag = (String) session.getAttribute("searchFlag");
		if(searchFlag == null || ("").equals(searchFlag))
			searchFlag = "C";//设置默认值：未领料
		model.addAttribute("searchFlag",searchFlag);
		

		try {
			orderService.producePlanSearchInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public HashMap<String, Object> doSetProduceLineById(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
	
		try {
			boolean rtnFlag = orderService.setProduceLineById();
		
			dataMap.put(INFO, SUCCESSMSG);			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}		
		
		return dataMap;
	}
	
	public HashMap<String, Object> doSetOrderForProduce(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
	
		try {
			orderService.setProducePlanFilter();
		
			dataMap.put(INFO, SUCCESSMSG);			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}		
		
		return dataMap;
	}
	
	public HashMap<String, Object> doGetProduceLineList(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
	
		try {
			dataMap = orderService.getProduceLineByKey();
		
			dataMap.put(INFO, SUCCESSMSG);			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	public HashMap<String, Object> doSetProducePlanSortNo(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
	
		try {
			boolean val = orderService.SetProducePlanSortNo();
		
			if(val){
				dataMap.put(INFO, "S");	
			}else{
				dataMap.put(INFO, "N");					
			}		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, "E");
		}
		
		return dataMap;
	}
	
}
