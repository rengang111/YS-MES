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
			//DefaultMultipartHttpServletRequest multipartRequest,
			//@RequestParam(value = "pdfFile", required = false) MultipartFile[] headPhotoFile,
			@ModelAttribute("formModel")ProductDesignModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{

		/*		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart){ 
		    MultipartHttpServletRequest multipartRequest3 = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		    MultipartFile file = multipartRequest3.getFile("pdfFile");
			MultipartHttpServletRequest multipartRequest2 = (MultipartHttpServletRequest) request;		
			MultipartFile headPhotoFile2 = multipartRequest2.getFile("pdfFile");			
		}*/		
		
		String type = request.getParameter("methodtype");
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new ProductDesignService(model,request,response,session,dataModel,userInfo);
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
				rtnUrl = "/business/material/productdesignmain";
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
			case "accessoryAddInit"://配件订单的做单资料
				rtnUrl = doAccessoryAddInit();
				break;
			case "edit":
				doEdit();
				rtnUrl = "/business/material/productdesignedit";
				break;
			case "accessoryEdit":
				doEdit();
				rtnUrl = "/business/material/accessorydesignedit";
				break;
			case "copyToEdit":
				doCopyToEdit();
				rtnUrl = "/business/material/productdesignedit";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/material/productdesignview";
				break;
			case "accessoryInsert":
				doAccessoryInsert();
				rtnUrl = "/business/material/accessorydesignview";
				break;
			case "update":
				doUpdate();
				rtnUrl = "/business/material/productdesignview";
				break;
			case "accessoryUpdate":
				doUpdate();
				rtnUrl = "/business/material/accessorydesignview";
				break;
			case "delete":
				doDelete(data);
				break;
			case "detailView":
				rtnUrl = doShowDetail();
				//printOutJsonObj(response, viewModel.getEndInfoMap());
				//rtnUrl = "/business/material/productdesignview";
				break;	
			case "getSupplierFromBom":
				dataMap = getSupplierFromBom();
				printOutJsonObj(response, dataMap);
				
				break;				
			case "detailViewHistory":
				dataMap = doShowDetailHistory();
				printOutJsonObj(response, dataMap);
				rtnUrl = "/business/material/productdesignhistory";
				break;
			case "getProductPhoto":
				dataMap = getProductPhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "getProductStoragePhoto":
				dataMap = getProductStoragePhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "getLabelPhoto":
				dataMap = getLabelPhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "getPackagePhoto":
				dataMap = getPackagePhoto();
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
			case "downloadFile":
				downloadFile();
				break;
			case "convertToPdf":
				convertToPdf();
				break;
			case "accceesoryConvertToPdf":
				accessoryConvertToPdf();
				break;
			case "productPhotoDelete":
				dataMap = deletePhoto("product","productFileList","productFileCount");
				printOutJsonObj(response, dataMap);
				break;
			case "storagePhotoDelete":
				dataMap = deletePhoto("storage","storageFileList","storageFileCount");
				printOutJsonObj(response, dataMap);
				break;
			case "labelPhotoDelete":
				dataMap = deletePhoto("label","labelFileList","labelFileCount");
				printOutJsonObj(response, dataMap);
				break;
			case "packagePhotoDelete":
				dataMap = deletePhoto("package","packageFileList","packageFileCount");
				printOutJsonObj(response, dataMap);
				break;
			case "textPrintFileDelete":
				dataMap = deleteTextPrintFile();
				printOutJsonObj(response, dataMap);
				break;
				
		}
		
		return rtnUrl;
	}	
	
	@RequestMapping(value="productDesignPhotoUpload")
	public String doInit(
			@RequestParam(value = "photoFile", required = false) MultipartFile[] headPhotoFile,
			@RequestBody String data,
			@ModelAttribute("formModel")ProductDesignModel dataModel,
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

		this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		this.service = new ProductDesignService(model,request,response,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		HashMap<String, Object> dataMap = null;

		String type = request.getParameter("methodtype");
		
		switch(type) {
		case "":
			break;
		case "uploadPackagePhoto":
			dataMap = uploadPhoto(headPhotoFile,"package","packageFileList","packageFileCount");
			printOutJsonObj(response, dataMap);
			break;
		case "uploadLabelPhoto":
			dataMap = uploadPhoto(headPhotoFile,"label","labelFileList","labelFileCount");
			printOutJsonObj(response, dataMap);
			break;
		case "uploadProductPhoto":
			dataMap = uploadPhoto(headPhotoFile,"product","productFileList","productFileCount");
			printOutJsonObj(response, dataMap);
			break;
		case "uploadStoragePhoto":
			dataMap = uploadPhoto(headPhotoFile,"storage","storageFileList","storageFileCount");
			printOutJsonObj(response, dataMap);
			break;
		}
		
		
		return null;
	}
	@RequestMapping(value="productDesignFileUpload")
	public String doInit(
			@RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile,
			@RequestBody String data,
			@ModelAttribute("formModel")ProductDesignModel dataModel,
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

		this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		this.service = new ProductDesignService(model,request,response,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;

		String type = request.getParameter("methodtype");
		switch(type) {
		case "":
			break;
		case "uploadTextPrintFile":
			dataMap = uploadFile(pdfFile,"textPrint");
			printOutJsonObj(response, dataMap);
			break;
		}
		
		
		return null;
	}
	
	public void doInit(){	
			
		String keyBackup = request.getParameter("keyBackup");
		//没有物料编号,说明是初期显示,清空保存的查询条件
		if(keyBackup == null || ("").equals(keyBackup)){
			session.removeAttribute(Constants.FORM_PRODUCTDETAIL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PRODUCTDETAIL+Constants.FORM_KEYWORD2);
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
			session.removeAttribute(Constants.FORM_PRODUCTDETAIL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PRODUCTDETAIL+Constants.FORM_KEYWORD2);
			
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
			session.removeAttribute(Constants.FORM_PRODUCTDETAIL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PRODUCTDETAIL+Constants.FORM_KEYWORD2);
			
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
	
		String rtnUrl = "";
		try{			
			String redirect = service.doAddOrView();
			switch(redirect) {
			case "":
			case "新建":
					rtnUrl = "/business/material/productdesignadd";				
				break;				
			case "查看":
					rtnUrl = "/business/material/productdesignview";
				break;				
			case "编辑新建":
				rtnUrl = "/business/material/productdesignedit";
				break;				
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}		
		return rtnUrl;
	}


	public String doAccessoryAddInit(){
	
		String rtnUrl = "";
		try{			
			String redirect = service.doAddOrView();
			switch(redirect) {
			case "":
			case "新建":
				rtnUrl = "/business/material/accessorydesignadd";				
				break;				
			case "查看":
				rtnUrl = "/business/material/accessorydesignview";
				break;				
			case "编辑新建":
				rtnUrl = "/business/material/accessorydesignedit";
				break;				
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}		
		return rtnUrl;
	}

	public void doInsert(){
		try{
			String PIId = request.getParameter("PIId");
			String goBackFlag = request.getParameter("goBackFlag");
			model.addAttribute("PIId",PIId);
			model.addAttribute("goBackFlag",goBackFlag);
			service.doInsertAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doAccessoryInsert(){
		try{
			String PIId = request.getParameter("PIId");
			String goBackFlag = request.getParameter("goBackFlag");
			model.addAttribute("PIId",PIId);
			model.addAttribute("goBackFlag",goBackFlag);
			service.doInsertAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doUpdate(){
		try{
			String PIId = request.getParameter("PIId");
			String goBackFlag = request.getParameter("goBackFlag");
			model.addAttribute("goBackFlag",goBackFlag);
			model.addAttribute("PIId",PIId);
			service.doUpdateAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doEdit(){
		try{
			String PIId = request.getParameter("PIId");
			String YSId = request.getParameter("YSId");
			String goBackFlag = request.getParameter("goBackFlag");
			model.addAttribute("PIId",PIId);
			model.addAttribute("YSId",YSId);
			model.addAttribute("goBackFlag",goBackFlag);
			service.updateInit(YSId);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	

	public void doCopyToEdit(){
	
		try{			
			service.doCopyToEdit();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}		
	}
	
	public void doDelete(@RequestBody String data) throws Exception{
		
		service.doDelete(data);

	}
	
	public HashMap<String, Object> getSupplierFromBom() throws Exception{
		
		return service.getSupplierFromBom();

	}
	
	public String doShowDetail() throws Exception{

		String rtnUrl = "/business/material/productdesignadd";
		try{
			String type=request.getParameter("productType");
			
			String redirect = service.doAddOrView();
			switch(redirect) {
			case "":
			case "新建":
				rtnUrl = "/business/material/productdesignadd";
				
				if(("035").equals(type))
					rtnUrl = "/business/material/accessorydesignadd";
				break;
			case "查看":
				rtnUrl = "/business/material/productdesignview";
				if(("035").equals(type))
					rtnUrl = "/business/material/accessorydesignview";
				break;
			case "编辑新建":
				rtnUrl = "/business/material/productdesignedit";
				if(("035").equals(type))
					rtnUrl = "/business/material/accessorydesignedit";
				break;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return rtnUrl;

	}
	
	public HashMap<String, Object> doShowDetailHistory() {
		try {
			String ysid = service.doShowDetailHistory();
			if(ysid == null){
				model.addAttribute("message","还没有该产品的做单资料");
			}else{
				
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		return dataMap;
	}
	
	public HashMap<String, Object> deletePhoto(
			String folderName,String fileList,String fileCount){	
		
		try {
			dataMap = service.deletePhotoAndReload(folderName,fileList,fileCount);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	public HashMap<String, Object> deleteTextPrintFile(){	
		
		try {
			
			boolean rtnFlag = service.deleteTextPrintFile();
			if(rtnFlag){
				dataMap.put(INFO, SUCCESSMSG);	
			}else{
				dataMap.put(INFO, ERRMSG);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		return dataMap;
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

	public HashMap<String, Object> getPackagePhoto(){	
		
		try {
			dataMap = service.getPackagePhoto();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	

	public HashMap<String, Object> getLabelPhoto(){	
		
		try {
			dataMap = service.getLabelPhoto();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}

	public HashMap<String, Object> getMachineConfiguration(){	
		
		try {
			String productDetailId = request.getParameter("productDetailId");
			dataMap = service.getMachineConfiguration(productDetailId);
		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	public HashMap<String, Object> getPlastic(){	
		
		try {
			String productDetailId = request.getParameter("productDetailId");
			dataMap = service.getPlastic(productDetailId);

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	

	public HashMap<String, Object> getAccessory(){	
		
		try {
			String productDetailId = request.getParameter("productDetailId");
			dataMap = service.getAccessory(productDetailId);
			
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
			String productDetailId = request.getParameter("productDetailId");
			dataMap = service.getTextPrint(productDetailId);
			
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
	
	
	
	private HashMap<String, Object> uploadPhoto(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) {
		
		HashMap<String, Object> map = null;
		
		try {
			 map = service.uploadPhotoAndReload(headPhotoFile,folderName,fileList,fileCount);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return map;
	}
	
	private HashMap<String, Object> uploadFile(MultipartFile file,String folder) {
			
		try {
			dataMap= service.uploadFileToTempFolder(file,folder);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return dataMap;
	}
	
	private void downloadFile() {
		
		//PrintWriter out = null;
			    
		
		try {
			service.downloadFile();
			//JSONObject jsonObj = service.downloadFile();
			//out = response.getWriter();
			//out.print(jsonObj);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private void convertToPdf() {
		try {
			service.convertAndDownloadToPdf();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	private void accessoryConvertToPdf() {
		try {
			service.accessoryConvertAndDownloadToPdf();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
