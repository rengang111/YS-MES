package com.ys.business.action.order;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
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
import com.ys.business.service.order.ProductDesignService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class ProductDesignAction extends BaseAction {
	
	@Autowired
	ProductDesignService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	ProductDesignModel reqModel = new ProductDesignModel();
	HashMap<String, Object> dataMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping("productDesign")
	public String init(@RequestBody String data,
			@ModelAttribute("formModel")ProductDesignModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new ProductDesignService(model,request,session,dataModel,userInfo);
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
				rtnUrl = "/business/inventory/arrivalmain";
				break;
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				break;				
			case "contractArrivalSearch":
				dataMap = contractArrivalSearch(data);
				printOutJsonObj(response, dataMap);
				break;	
			case "addinit":
				rtnUrl = doAddInit();
				break;
			case "edit":
				doEdit();
				rtnUrl = "/business/material/productdesignedit";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/material/productdesignview";
				break;
			case "update":
				doUpdate();
				rtnUrl = "/business/material/productdesignview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				break;
			case "detailView":
				doShowDetail();
				//printOutJsonObj(response, viewModel.getEndInfoMap());
				rtnUrl = "/business/inventory/arrivalview";
				break;
			case "getProductPhoto":
				dataMap = getProductPhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "getProductStoragePhoto":
				dataMap = getProductStoragePhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "getMachineConfiguration":
				dataMap = getMachineConfiguration();
				printOutJsonObj(response, dataMap);
				break;
			case "getPlastic":
				dataMap = getPlastic();
				printOutJsonObj(response, dataMap);
				break;
			case "getAccessory":
				dataMap = getAccessory();
				printOutJsonObj(response, dataMap);
				break;
			case "getLabel":
				dataMap = getLabel();
				printOutJsonObj(response, dataMap);
				break;
			case "getTextPrint":
				dataMap = getTextPrint();
				printOutJsonObj(response, dataMap);
				break;
			case "getPackage":
				dataMap = getPackage();
				printOutJsonObj(response, dataMap);
				break;
				
		}
		
		return rtnUrl;
	}	
	
	@RequestMapping(value="productDesignPhotoUpload{id}")
	public String doInit(
			@RequestParam(value = "photoFile", required = false) MultipartFile[] headPhotoFile,
			//@RequestParam MultipartFile[] headPhotoFile, 
			@RequestBody String data,
			@ModelAttribute("formModel")ProductDesignModel dataModel,
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

		this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		this.service = new ProductDesignService(model,request,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;

		String type = request.getParameter("methodtype");
		
		switch(type) {
		case "":
			break;
		case "uploadPackagePhoto":
			uploadPhoto(headPhotoFile,"package");
			break;
		case "uploadLabelPhoto":
			uploadPhoto(headPhotoFile,"label");
			break;
		case "uploadProductPhoto":
			uploadPhoto(headPhotoFile,"product");
			break;
		case "uploadStoragePhoto":
			uploadPhoto(headPhotoFile,"storage");
			break;
		}
		
		
		return null;
	}
	
	public void doInit(){	
			
		String keyBackup = request.getParameter("keyBackup");
		//没有物料编号,说明是初期显示,清空保存的查询条件
		if(keyBackup == null || ("").equals(keyBackup)){
			session.removeAttribute(Constants.FORM_ARRIVAL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_ARRIVAL+Constants.FORM_KEYWORD2);
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
			session.removeAttribute(Constants.FORM_ARRIVAL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_ARRIVAL+Constants.FORM_KEYWORD2);
			
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
	
	public String doAddInit(){

		String PIId = request.getParameter("PIId");
		model.addAttribute("PIId",PIId);
		
		String rtnUrl = "/business/material/productdesignadd";
		try{
			boolean redirect = service.addOrView();
			if(redirect){//已存在的话,查看该信息
				rtnUrl = "/business/material/productdesignview";
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return rtnUrl;
	}

	public void doInsert(){
		try{
			String PIId = request.getParameter("PIId");
			model.addAttribute("PIId",PIId);
			service.insertAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doUpdate(){
		try{
			String PIId = request.getParameter("PIId");
			model.addAttribute("PIId",PIId);
			service.UpdateAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doEdit(){
		try{
			String PIId = request.getParameter("PIId");
			model.addAttribute("PIId",PIId);
			service.updateInit();
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

	public HashMap<String, Object> getProductPhoto(){	
		
		try {
			dataMap = service.getProductPhoto();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	

	public HashMap<String, Object> getProductStoragePhoto(){	
		
		try {
			dataMap = service.getProductStoragePhoto();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}

	public HashMap<String, Object> getMachineConfiguration(){	
		
		try {
			dataMap = service.getMachineConfiguration();
		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	public HashMap<String, Object> getPlastic(){	
		
		try {
			dataMap = service.getPlastic();

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	

	public HashMap<String, Object> getAccessory(){	
		
		try {
			dataMap = service.getAccessory();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	

	public HashMap<String, Object> getLabel(){		
		
		try {
			dataMap = service.getLabelAndPhoto();			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	

	@SuppressWarnings({ })
	public HashMap<String, Object> getTextPrint(){
		
		try {
			dataMap = service.getTextPrint();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	

	@SuppressWarnings({ })
	public HashMap<String, Object> getPackage(){	
		
		try {
			dataMap = service.getPackageAndPhoto();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	
	
	private void uploadPhoto(MultipartFile[] headPhotoFile,String folder) {
		
		PrintWriter out = null;
			    
		JSONObject jsonObj = service.uploadPhoto(headPhotoFile,folder);
		
		try {
			out = response.getWriter();
			out.print(jsonObj);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
