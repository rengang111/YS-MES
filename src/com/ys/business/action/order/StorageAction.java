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

import com.ys.business.action.model.order.StorageModel;
import com.ys.business.service.order.StorageService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class StorageAction extends BaseAction {
	
	@Autowired
	StorageService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	StorageModel reqModel = new StorageModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="storage")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")StorageModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String makeType = request.getParameter("makeType");
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new StorageService(model,request,response,session,dataModel,userInfo);
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
				rtnUrl = "/business/inventory/storagemain";
				break;
			case "search":
				dataMap = doSearch(data,makeType);
				printOutJsonObj(response, dataMap);
				return null;
			case "initYszz":
				rtnUrl = "/business/inventory/storagezzmain";
				break;
			case "searchYszz"://自制件任务查询
				dataMap = doSearchYszz(data,makeType);
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
			case "delete":
				doDelete(data);
				return null;
			case "showHistory":
				showHistory();
				rtnUrl = "/business/inventory/storageview";
				break;
			case "getStockInDetail":
				dataMap = doShowDetail();
				printOutJsonObj(response, dataMap);
				return null;
			case "getStockInByMaterialId":
				getStockInByMaterialId();
				rtnUrl = "/business/inventory/beginninginventorystockin";
				break;
			case "orderSearchInit":
				doInit();
				rtnUrl = "/business/inventory/productstoragemain";
				break;
			case "orderSearch":
				dataMap = doOrderSearch(data);
				printOutJsonObj(response, dataMap);
				return null;
			case "productAddInit":
				doProductAddInit();
				rtnUrl = "/business/inventory/productstorageadd";
				return rtnUrl;
			case "updateProduct":
				doUpdateProduct();
				rtnUrl = "/business/inventory/productstorageview";
				break;
			case "insertProduct":
				doInsertProduct();
				rtnUrl = "/business/inventory/productstorageview";
				break;
			case "editProduct":
				doEditProduct();
				rtnUrl = "/business/inventory/productstorageedit";
				break;
			case "getProductStockInDetail":
				dataMap = doShowProductDetail();
				printOutJsonObj(response, dataMap);
				return null;
			case "printReceipt"://打印入库单
				doPrintReceipt();
				rtnUrl = "/business/inventory/storageprint";
				break;
			case "getProductPhoto"://显示供应商的入库单
				dataMap = getProductPhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "productPhotoDelete"://删除供应商的入库单
				dataMap = deletePhoto("product","productFileList","productFileCount");
				printOutJsonObj(response, dataMap);
				break;
			case "printProductReceipt"://打印成品入库单
				doPrintProductReceipt();
				rtnUrl = "/business/inventory/productstorageprint";
				break;
			case "financeSearchInit":
				doInit();
				rtnUrl = "/business/finance/financestockinmain";
				break;
			case "financeSearch":
				dataMap = financeSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "downloadExcel":
				downloadExcel();
				break;
			case "receiptListPrint"://批量打印入库单
				receiptListPrint();
				rtnUrl = "/business/finance/paymentstockinview";
				break;
			case "materialStockinMainInit"://直接入库一览
				rtnUrl = "/business/inventory/materialstockinmain";
				break;
			case "materialStockinMainSearch"://直接入库查询
				dataMap = doMaterialStockinSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "materialStockinAddInit"://直接入库
				materialStockinAddInit();
				rtnUrl = "/business/inventory/materialstockinadd";
				break;
			case "materialStockinAdd"://直接入库
				materialStockinAdd();
				rtnUrl = "/business/inventory/materialstockinview";
				break;
			case "materialStockinDetailView"://直接入库明细查看
				materialStockinDetailView();
				rtnUrl = "/business/inventory/materialstockinview";
				break;
			case "showStockInByContractId":
				showStockIn();
				rtnUrl = "/business/finance/paymentstockinprint";
				break;
			case "beginningInventoryMainInit":
				rtnUrl = "/business/inventory/beginninginventorymain";
				break;
			case "beginningInventorySearch":
				dataMap = doBeginningInventorySearch(data,Constants.FORM_BEGINNINGINVENTROY);
				printOutJsonObj(response, dataMap);
				break;
			case "setBeginningInventory":
				setBeginningInventory();
				rtnUrl = "/business/inventory/beginninginventoryadd";
				break;
			case "beginningInventoryAdd":
				beginningInventoryAdd();
				break;
				
		}
		
		return rtnUrl;
	}	
	
	//入库单上传
	@RequestMapping(value="godownEntryUpload")
	public String doInit(
			@RequestParam(value = "photoFile", required = false) MultipartFile[] headPhotoFile,
			@RequestBody String data,
			@ModelAttribute("formModel")StorageModel dataModel,
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

		this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		this.service = new StorageService(model,request,response,session,dataModel,userInfo);;
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
			 map = service.uploadPhotoAndReload(headPhotoFile,folderName,fileList,fileCount);
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
			session.removeAttribute(Constants.FORM_MATERIALSTORAGE+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_MATERIALSTORAGE+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.doSearch(data,Constants.FORM_MATERIALSTORAGE,makeType);
			
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
	public HashMap<String, Object> doBeginningInventorySearch(String data,String formId){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.beginningInventorySearch(data,formId);
			
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
	public HashMap<String, Object> doSearchYszz(String data,String makeType){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_CONTRACTZZ+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_CONTRACTZZ+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.doSearchYszz(data,Constants.FORM_CONTRACTZZ,makeType);
			
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
	public HashMap<String, Object> doMaterialStockinSearch(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_MATERIALSTORAGE+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_MATERIALSTORAGE+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.doMaterialStockinSearch(data);
			
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
	public HashMap<String, Object> financeSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_FINANCESTOCKIN+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_FINANCESTOCKIN+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.doFinanceSearch(data);
			
			ArrayList<HashMap<String, String>> dbData = 
					(ArrayList<HashMap<String, String>>)dataMap.get("data");
			if (dbData.size() == 0) {
				dataMap.put(INFO, NODATAMSG);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
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
			String viewFlag = service.addInit();
			if(("查看").equals(viewFlag)){
				rtnUrl = "/business/inventory/storageview";
			}
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return rtnUrl;
	}

	public void receiptListPrint(){
		
		try{
			service.receiptListPrint();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void doProductAddInit(){

		try{
			service.addProductInit();
			
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		
	}
	
	public void doInsert(){
		try{
			service.insertAndReturn();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void doInsertProduct(){
		try{
			service.insertProductAndReturn();
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
	
	public void doUpdateProduct(){
		try{
			service.updateProductAndReturn();
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
	
	public void doDelete(@RequestBody String data) throws Exception{
		
		service.doDelete(data);

	}
	
	public HashMap<String, Object> doShowDetail() throws Exception{
		
		return service.getStockInDetail();

	}

	public void showHistory() throws Exception{
		
		service.showHistory();

	}


	public HashMap<String, Object> doShowProductDetail() throws Exception{
		
		return service.getProductStockInDetail();

	}

	
	public void gotoArrivalView(){
		try{
			String contractId = request.getParameter("contractId");
			service.getContractDetail(contractId);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public HashMap<String, Object> getProductPhoto(){	
		
		try {
			modelMap = service.getProductPhoto();
			
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
			modelMap = service.deletePhotoAndReload(folderName,fileList,fileCount);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	

	private void downloadExcel() {
				
		try {
			service.stockinDownloadExcelForfinance();
		}
		catch(Exception e) {
			e.printStackTrace();
			//System.out.println(e.getMessage());
		}
		
	}
	
	public void materialStockinAddInit(){

		try{		
			service.materialStockinAddInit();
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void materialStockinAdd(){

		try{	
			service.insertMaterialAndReturn();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public void materialStockinDetailView(){

		try{	
			service.materialStockinDetailView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	public void showStockIn(){
		
		try{
			service.showStockIn();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void setBeginningInventory(){
		
		try{
			service.setBeginningInventory();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	public void beginningInventoryAdd(){
		
		try{
			service.beginningInventoryAdd();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void getStockInByMaterialId(){			
		try {
			service.getStockinDetailByMaterialId();			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
