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

import com.ys.business.action.model.order.StockOutModel;
import com.ys.business.action.model.order.StorageModel;
import com.ys.business.service.order.StockOutService;
import com.ys.business.service.order.StorageService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
/**
 * 料件出库
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
				rtnUrl = "/business/manufacture/stockoutmain";
				break;
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				rtnUrl = doAddInit();
				return rtnUrl;
			case "getRequisitionDetail"://领料单详情
				dataMap = getRequisitionDetail();
				printOutJsonObj(response, dataMap);
				return null;
			case "updateInit":
				doEdit();
				rtnUrl = "/business/manufacture/stockoutedit";
				break;
			case "update":
				doUpdate();
				rtnUrl = "/business/manufacture/stockoutview";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/manufacture/stockoutview";
				break;
			case "orderSearchInit":
				doInit();
				rtnUrl = "/business/manufacture/productstoragemain";
				break;
			case "print"://打印出库单
				doPrintReceipt();
				rtnUrl = "/business/manufacture/stockoutprint";
				break;
			case "printProductReceipt"://打印成品入库单
				doPrintProductReceipt();
				rtnUrl = "/business/manufacture/productstorageprint";
				break;
			case "getStockoutHistoryInit":
				doAddInit();
				rtnUrl = "/business/manufacture/stockoutview";
				break;
			case "getStockoutHistory":
				dataMap = getStockoutHistory();
				printOutJsonObj(response, dataMap);
				break;
			case "getStockoutDetail":
				dataMap = getStockoutDetail();
				printOutJsonObj(response, dataMap);
				break;
			case "getProductPhoto"://显示出库单附件
				dataMap = getProductPhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "productPhotoDelete"://删除出库单附件
				dataMap = deletePhoto("product","productFileList","productFileCount");
				printOutJsonObj(response, dataMap);
				break;
				
		}
		
		return rtnUrl;
	}	
	
	//出库单上传
	@RequestMapping(value="ODOUpload")
	public String doInit(
			@RequestParam(value = "photoFile", required = false) MultipartFile[] headPhotoFile,
			@RequestBody String data,
			@ModelAttribute("formModel")StockOutModel dataModel,
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

		this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		this.service = new StockOutService(model,request,session,dataModel,userInfo);;
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
	public HashMap<String, Object> doSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_MATERIALSTOCKOUT+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_MATERIALSTOCKOUT+Constants.FORM_KEYWORD2);			
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
	

	public String doAddInit(){

		String rtnUrl = "/business/manufacture/stockoutadd";
		try{
			String flag = service.addInitOrView();
			if(flag.equals("查看"))
				rtnUrl = "/business/manufacture/stockoutview";
			
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return rtnUrl;
		
	}

	public HashMap<String, Object> getRequisitionDetail() throws Exception{

		return service.getRequisitionDetail();		
		
	}
	
	public HashMap<String, Object> getStockoutDetail() throws Exception{

		return service.getStockoutDetail();		
		
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
	

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> getStockoutHistory(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			String YSId = request.getParameter("YSId");
			dataMap = service.getStockoutHistory(YSId);
			
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
}