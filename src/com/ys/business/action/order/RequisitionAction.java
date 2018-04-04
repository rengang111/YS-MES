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

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.action.model.order.PaymentModel;
import com.ys.business.action.model.order.RequisitionModel;
import com.ys.business.service.order.ArrivalService;
import com.ys.business.service.order.PaymentService;
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
		String virtualClass = request.getParameter("virtualClass");//虚拟领料区分
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new RequisitionService(model,request,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		this.model.addAttribute("virtualClass",virtualClass);
		
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
			case "requisitionPrint":
				dataMap = requisitionPrint();
				printOutJsonObj(response, dataMap);
				return null;
			case "print"://领料单打印
				doPrintInit();
				rtnUrl = "/business/inventory/requisitionprint";
				break;
			case "materialRequisitionMain":
				rtnUrl = "/business/manufacture/materialrequisitionmain";
				break;
			case "materialRequisitionSearch":
				dataMap = materialRequisitionSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "materialReqeuisitionAddInit":
				materialRequisitionAddInit();
				rtnUrl = "/business/manufacture/materialrequisitionadd";
				break;
			case "materialRequisitionInsert":
				materialStockoutAdd();
				rtnUrl = "/business/manufacture/materialrequisitionview";
				break;
			case "materialRequisitionView":
				materialStockoutView();
				rtnUrl = "/business/manufacture/materialrequisitionview";
				break;
			case "materialRequisitionEdit":
				materialRequisitionEdit();
				rtnUrl = "/business/manufacture/materialrequisitionadd";
				break;
			case "getProductPhoto"://显示附件
				dataMap = getProductPhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "productPhotoDelete"://删除附件
				dataMap = deletePhoto("product","productFileList","productFileCount");
				printOutJsonObj(response, dataMap);
				break;
			case "virtualInsert"://虚拟领料:保存
				doVirtualInsert();
				rtnUrl = "/business/inventory/requisitionview";
				break;
			case "virtualAddinit":
				doAddInit();
				rtnUrl = "/business/inventory/requisitionvirtualadd";
				break;
				
		}
		
		return rtnUrl;
	}	
	
	//单据上传
	@RequestMapping(value="materialRequisitionUpload")
	public String doInit(
			@RequestParam(value = "photoFile", required = false) MultipartFile[] headPhotoFile,
			@RequestBody String data,
			@ModelAttribute("formModel")RequisitionModel dataModel,
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

		this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		this.service = new RequisitionService(model,request,session,dataModel,userInfo);;
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
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> materialRequisitionSearch(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_REQUISITION_M+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REQUISITION_M+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			dataMap = service.doMaterialRequisitionSearch(data);
			
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
	

	public void doVirtualInsert(){
		try{
			service.virtualInsertAndView();
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
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> requisitionPrint(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			dataMap = service.requisitionPrint();
			
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
	

	public void materialRequisitionAddInit(){

		try{
			service.materialRequisitionAddInit();			
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void materialStockoutAdd(){

		try{
			service.materialRquisitionInsert();			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	

	public void materialStockoutView(){

		try{
			service.materialRquisitionView();			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	

	public void materialRequisitionEdit(){

		try{
			service.materialRequisitionEdit();			
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			e.printStackTrace();;
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
}
