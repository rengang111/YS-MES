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

import com.ys.business.action.model.order.StockInApplyModel;
import com.ys.business.service.order.StockInApplyService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
/**
 * 直接入库申请
 * @author rengang
 *
 */
@Controller
@RequestMapping("/business")
public class StockInApplyAction extends BaseAction {
	
	@Autowired
	StockInApplyService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	StockInApplyModel reqModel = new StockInApplyModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="stockinapply")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")StockInApplyModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String makeType = request.getParameter("makeType");
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new StockInApplyService(model,request,response,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		model.addAttribute("makeType",makeType);
		
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
				rtnUrl = "/business/inventory/stockinapplymain";
				break;
			case "search":
				dataMap = doSearch(data,makeType);
				printOutJsonObj(response, dataMap);
				return null;
			case "stockinApplyAddInit":
				doAddInit();
				rtnUrl = "/business/inventory/stockinapplyadd";
				return rtnUrl;
			case "stockInApplyEdit":
				stockInApplyEdit();
				rtnUrl = "/business/inventory/stockinapplyedit";
				break;
			case "stockInApplyUpdate":
				stockInApplyUpdate();
				rtnUrl = "/business/inventory/stockinapplyview";
				break;
			case "stockInApplyInsert":
				doInsert();
				rtnUrl = "/business/inventory/stockinapplyview";
				break;
			case "delete":
				doDelete(data);
				return null;
			case "showStockinApply":
				showStockinApply();
				rtnUrl = "/business/inventory/stockinapplyview";
				break;
				
		}
		
		return rtnUrl;
	}	
	
	//入库单上传
	@RequestMapping(value="godownEntryUpload2")
	public String doInit(
			@RequestParam(value = "photoFile", required = false) MultipartFile[] headPhotoFile,
			@RequestBody String data,
			@ModelAttribute("formModel")StockInApplyModel dataModel,
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

		this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		this.service = new StockInApplyService(model,request,response,session,dataModel,userInfo);;
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		HashMap<String, Object> dataMap = null;

		String type = request.getParameter("methodtype");
		
		switch(type) {
		case "":
			break;
		case "uploadPhoto":
			dataMap = uploadPhoto(headPhotoFile,"product","productFileList","productFileCount");
			printOutJsonObj(response, dataMap);
			break;
	
		}
		
		
		return null;
	}
	
	private HashMap<String, Object> uploadPhoto(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) {
		
		HashMap<String, Object> map = null;
		
		try {
			// map = service.uploadPhotoAndReload(headPhotoFile,folderName,fileList,fileCount);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return map;
	}
	public void doInit(){	

	}
	
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(String data,String makeType){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_STOCKINAPPLY+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_STOCKINAPPLY+Constants.FORM_KEYWORD2);			
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
			service.stockinApplyAddInit();
			
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	public void receiptListPrint(){
		
		try{
			//service.receiptListPrint();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	
	public void doInsert(){
		try{
			service.stockInApplyInsert();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	
	
	public void stockInApplyUpdate(){
		try{
			service.stockInApplyUpdate();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	
	public void stockInApplyEdit(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.stockInApplyEdit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void doPrintProductReceipt(){
		try{
			//service.printProductReceipt();
			model.addAttribute("userName",userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
		

	public void showStockinApply(){
		try{
			service.showStockinApply();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doDelete(@RequestBody String data) throws Exception{
		
		//service.doDelete(data);

	}



	
	public HashMap<String, Object> getProductPhoto(){	
		
		try {
			//modelMap = service.getProductPhoto();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	
	public HashMap<String, Object> deletePhoto(
			String folderName,String fileList,String fileCount){	
		
		try {
			//modelMap = service.deletePhotoAndReload(folderName,fileList,fileCount);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	
	
}
