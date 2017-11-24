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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ys.business.action.model.order.ProductDesignModel;
import com.ys.business.action.model.order.StockOutModel;
import com.ys.business.action.model.order.StorageModel;
import com.ys.business.service.order.ProductDesignService;
import com.ys.business.service.order.StockOutService;
import com.ys.business.service.order.StorageService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
/**
 * 成品出库
 * @author rengang
 *
 */
@Controller
@RequestMapping("/business")
public class StockOutAction extends BaseAction {
	
	@Autowired
	StockOutService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	StockOutModel reqModel = new StockOutModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="stockout")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")StockOutModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new StockOutService(model,request,session,dataModel,userInfo);
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
				rtnUrl = "/business/inventory/storagemain";
				break;
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				rtnUrl = doAddInit();
				return rtnUrl;
			case "edit":
				doEdit();
				rtnUrl = "/business/inventory/storageedit";
				break;
			case "update":
				doUpdate();
				rtnUrl = "/business/inventory/storageview";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/inventory/storageview";
				break;
			case "getStockInDetail":
				dataMap = doShowDetail();
				printOutJsonObj(response, dataMap);
				return null;
			case "orderSearchInit":
				doInit();
				rtnUrl = "/business/inventory/productstoragemain";
				break;
			case "orderSearch":
				dataMap = doOrderSearch(data);
				printOutJsonObj(response, dataMap);
				return null;
			case "printReceipt"://打印入库单
				doPrintReceipt();
				rtnUrl = "/business/inventory/storageprint";
				break;
			case "printProductReceipt"://打印成品入库单
				doPrintProductReceipt();
				rtnUrl = "/business/inventory/productstorageprint";
				break;
				
		}
		
		return rtnUrl;
	}	
	

	
	
	public void doInit(){	
		/*	
		String keyBackup = request.getParameter("keyBackup");
		//没有物料编号,说明是初期显示,清空保存的查询条件
		if(keyBackup == null || ("").equals(keyBackup)){
			session.removeAttribute(Constants.FORM_STORAGE+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_STORAGE+Constants.FORM_KEYWORD2);
		}else{
			model.addAttribute("keyBackup",keyBackup);
		}
		*/
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_MATERIALSTORAGE+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_MATERIALSTORAGE+Constants.FORM_KEYWORD2);			
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
	public HashMap<String, Object> doOrderSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PRODUCTSTORAGE	+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PRODUCTSTORAGE+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.doOrderSearch(data);
			
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
		
	public String doAddInit(){

		String rtnUrl = "/business/inventory/storageadd";
		try{
			boolean viewFlag = service.addInit();
			if(viewFlag){
				rtnUrl = "/business/inventory/storageview";
			}
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return rtnUrl;
	}

	
	
	public void doInsert(){
		try{
			service.insertAndReturn();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	
	
	public void doUpdate(){
		try{
			service.updateAndReturn();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	public void doEdit(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.edit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void doEditProduct(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.editProduct();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doPrintReceipt(){
		try{
			service.printReceipt();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void doPrintProductReceipt(){
		try{
			service.printProductReceipt();
			model.addAttribute("userName",userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	
	public HashMap<String, Object> doShowDetail() throws Exception{
		
		return service.getStockInDetail();

	}
	
}
