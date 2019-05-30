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

import com.ys.business.action.model.order.RequisitionModel;
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
		String virtualType = request.getParameter("virtualType");//虚拟领料区分
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new RequisitionService(model,request,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		this.model.addAttribute("virtualType",virtualType);
		
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
			case "peiAddinit":
				doAddPeiInit();
				rtnUrl = "/business/inventory/requisitionpartsadd";
				break;
			case "updateInit":
				doUpdateInit();
				rtnUrl = "/business/inventory/requisitionedit";
				break;
			case "updateInitParts":
				doUpdateInitParts();
				rtnUrl = "/business/inventory/requisitionpartsedit";
				break;
			case "update":
				doUpdate();
				rtnUrl = "/business/inventory/requisitionview";
				break;
			case "updateParts":
				doUpdateParts();
				rtnUrl = "/business/inventory/requisitionpartsview";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/inventory/requisitionview";
				break;
			case "insertParts":
				doInsertParts();
				rtnUrl = "/business/inventory/requisitionpartsview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				return null;
			case "detailView":
				dataMap = doShowDetail();
				printOutJsonObj(response, dataMap);
				rtnUrl = null;
				break;
			case "detailViewForParts":
				dataMap = doShowDetailForParts();
				printOutJsonObj(response, dataMap);
				rtnUrl = null;
				break;
			case "getRequisitionHistoryInit":
				doAddInit();
				rtnUrl = "/business/inventory/requisitionview";
				break;
			case "getRequisitionHistoryInitParts":
				doViewPeiInit();
				rtnUrl = "/business/inventory/requisitionpartsview";
				break;
			case "getRequisitionHistoryParts":
				dataMap = getRequisitionHistoryParts();
				printOutJsonObj(response, dataMap);
				break;
			case "getRequisitionHistory":
				dataMap = getRequisitionHistory();
				printOutJsonObj(response, dataMap);
				break;
			case "getRequisitionDetail":
				dataMap = getRequisitionDetail();
				printOutJsonObj(response, dataMap);
				break;
			case "requisitionPrint":
				dataMap = requisitionPrint();
				printOutJsonObj(response, dataMap);
				break;
			case "print"://领料单打印
				doPrintInit();
				rtnUrl = "/business/inventory/requisitionprint";
				break;
			case "printParts"://领料单打印：配件订单
				doPrintInitParts();
				rtnUrl = "/business/inventory/requisitionpartsprint";
				break;
			case "requisitionPrintParts":
				dataMap = requisitionPrintParts();
				printOutJsonObj(response, dataMap);
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
			case "virtualInit"://虚拟领料查询
				doInit();
				rtnUrl = "/business/inventory/requisitionvirtualmain";
				break;
			case "virtualSearch"://虚拟领料查询
				dataMap = doVirtualSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "excessInit"://超领查询*********************
				rtnUrl = "/business/inventory/requisitionexcessmain";
				break;
			case "excessSearch"://超领查询
				dataMap = doExcessSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "excessAddInit"://超领申请
				//excessAddInit();
				rtnUrl = "/business/inventory/requisitionexcessadd";
				break;
			case "excessInitYsidSelected"://超领申请:耀升编号选择
				excessInitYsidSelected();
				rtnUrl = "/business/inventory/requisitionexcessadd";
				break;
			case "excessAdd"://超领申请保存
				doExcessInsert();
				rtnUrl = "/business/inventory/requisitionexcessview";
				break;
			case "excessDetail"://超领申请查看
				doShowExcessDetail();
				rtnUrl = "/business/inventory/requisitionexcessview";
				break;
			case "excessEdit"://超领申请编辑
				doShowExcessDetail();
				rtnUrl = "/business/inventory/requisitionexcessedit";
				break;
			case "excessUpdate"://超领申请编辑保存
				doExcessUpdate();
				rtnUrl = "/business/inventory/requisitionexcessview";
				break;
			case "excessDelete"://超领删除
				doExcessDelete();
				rtnUrl = "/business/inventory/requisitionexcessmain";
				break;
			case "stockoutReturnInit"://领料退货初始化*********************
				rtnUrl = "/business/inventory/stockoutreturnmain";
				break;
			case "stockoutReturnSearch"://领料退货查询
				dataMap = stockoutReturnSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "stockoutReturnAddInit"://领料退货申请
				stockoutReturnAddInit();
				rtnUrl = "/business/inventory/stockoutreturnadd";
				break;
			case "stockoutReturnInsert"://领料退货保存
				stockoutReturnInsert();
				rtnUrl = "/business/inventory/stockoutreturnview";
				break;
			case "stockoutReturnDelete"://领料退货保存
				stockoutReturnDelete();
				rtnUrl = "/business/inventory/stockoutreturnmain";
				break;
			case "stockoutReturnDetail"://领料退货详情
				stockoutReturnDetail();
				rtnUrl = "/business/inventory/stockoutreturnview";
				break;
			case "stockoutReturnEditInit"://领料退货编辑
				stockoutReturnAddInit();
				rtnUrl = "/business/inventory/stockoutreturnadd";
				break;
			case "partsMainInit"://配件订单领料
				doInit();
				rtnUrl = "/business/inventory/requisitionpartsmain";
				break;
			case "partsMainSearch"://配件订单领料查询
				dataMap = doPartsSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "requisitionMainSearchInit":
				doRequisitionMainSearchInit();
				rtnUrl = "/business/inventory/requisitionmain";
				break;
			case "requisitionMainSearch":
				dataMap = doRequisitionMainSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "setCurrentTask"://设置当前任务
				dataMap = setCurrentTask();
				printOutJsonObj(response, dataMap);
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
		
		String requisitionSts = (String) session.getAttribute("requisitionSts");
		if(requisitionSts == null || ("").equals(requisitionSts))
				requisitionSts = "010";//设置默认值：待申请
		model.addAttribute("requisitionSts",requisitionSts);

	}	
	
	
	public void doRequisitionMainSearchInit(){	
		
		String searchFlag = (String) session.getAttribute("searchFlag");
		if(searchFlag == null || ("").equals(searchFlag))
			searchFlag = "U";//设置默认值：未领料
		model.addAttribute("searchFlag",searchFlag);

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
		

		String requisitionSts = request.getParameter("requisitionSts");
		session.setAttribute("requisitionSts", requisitionSts);
		
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doRequisitionMainSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_REQUISITION+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REQUISITION+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			dataMap = service.doRequisitionMainSearch(data);
			
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
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doPartsSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_REQUISITIONPARTS+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REQUISITIONPARTS+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			String partsType = request.getParameter("partsType");
			if(("P").equals(partsType)){
				//普通配件订单
				dataMap = service.doPartsSearch(data);
			}else{
				//成品配件订单
				dataMap = service.doProductPartsSearch(data);				
			}
			
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
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doVirtualSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_REQUISITIONVIRTUAL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REQUISITIONVIRTUAL+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			dataMap = service.doVirtualSearch(data);
			
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
	public HashMap<String, Object> doExcessSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_REQUISITIOEXCESS+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_REQUISITIOEXCESS+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			dataMap = service.doExcessSearch(data);
			
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
	public HashMap<String, Object> stockoutReturnSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_STOCKOUTRETURN+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_STOCKOUTRETURN+Constants.FORM_KEYWORD2);
		}
		
		try {
			dataMap = service.stockoutReturnSearch(data);
			
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
	
	public void doAddPeiInit(){
		try{
			service.addPeiInit();
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


	public void doViewPeiInit(){
		try{
			service.viewPeiInit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	

	public HashMap<String, Object> getRequisitionHistoryParts() throws Exception{
	
		return service.viewPeiRequisitionDetail();
		
	}

	public void doPrintInit(){
		try{
			service.addInit();
			model.addAttribute("userName", userInfo.getUserName());
			model.addAttribute("requisitionId", request.getParameter("requisitionId"));
			model.addAttribute("excessType", request.getParameter("excessType"));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	

	public void doPrintInitParts(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			model.addAttribute("YSId", request.getParameter("YSId"));
			model.addAttribute("excessType", request.getParameter("excessType"));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void excessInitYsidSelected(){
		try{
			service.excessSearchByYsid();
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
	
	public void doInsertParts(){
		try{
			service.insertPartsAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doExcessInsert(){
		try{
			service.insertExcessAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doExcessUpdate(){
		try{
			service.updateExcessAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void doExcessDelete(){
		try{
			service.deleteExcessAndReturn();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}



	public void stockoutReturnAddInit(){
		try{

			model.addAttribute("userName",userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


	public void stockoutReturnInsert(){
		try{

			service.stockoutReturnInsertAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


	public void stockoutReturnDelete(){
		try{

			service.stockoutReturnDelete();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


	public void stockoutReturnDetail(){
		try{

			service.showExcessDetail();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	
	
	public void doShowExcessDetail(){
		try{
			service.showExcessDetail();
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
	

	public void doUpdateInitParts(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.updateInitParts();
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
	

	public void doUpdateParts(){
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
	

	public HashMap<String, Object> doShowDetailForParts() throws Exception{
		
		return service.showRquisitionPartsDetail();

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
	

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> requisitionPrintParts(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			dataMap = service.requisitionPrintParts();
			
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
	
	public HashMap<String, Object> setCurrentTask(){	

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			modelMap = service.selectedOrderListForTask();
			modelMap.put(INFO, SUCCESSMSG);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
}
