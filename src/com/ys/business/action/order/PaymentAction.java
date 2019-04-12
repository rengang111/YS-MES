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

import com.ys.business.action.model.order.PaymentModel;
import com.ys.business.service.order.PaymentService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;
import com.ys.util.basequery.common.Constants;
/**
 * 应付款管理
 * @author rengang
 *
 */
@Controller
@RequestMapping("/business")
public class PaymentAction extends BaseAction {
	
	@Autowired
	PaymentService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	PaymentModel reqModel = new PaymentModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="payment")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")PaymentModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String paymentTypeId = request.getParameter("paymentTypeId");
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new 	PaymentService(model,request,
				 response,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		this.model.addAttribute("paymentTypeId",paymentTypeId);
		
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
				rtnUrl = "/business/finance/paymentrequestmain";
				break;
			case "search"://付款申请
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				return null;
			case "getRequisitionDetail"://领料单详情
				//dataMap = getRequisitionDetail();
				printOutJsonObj(response, dataMap);
				return null;
			case "applyUpdateInit"://付款申请修改初始化
				applyUpdateInit();
				rtnUrl = "/business/finance/paymentrequestadd";
				break;
			case "addinit"://付款申请初始化
				doApplyAddInit();
				rtnUrl = "/business/finance/paymentrequestadd";
				return rtnUrl;
			case "beforehandMainInit"://预付款查询初始化
				rtnUrl = "/business/finance/paymentrequestbeformain";
				break;
			case "beforehandSearch"://预付款查询
				dataMap = doBeforhandSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "beforeAddinit"://预付款申请初始化
				doApplyBeforeAddInit();
				rtnUrl = "/business/finance/paymentrequestadd";
				return rtnUrl;
			case "applyInsert"://付款申请
				doApplyInsert();
				rtnUrl = "/business/finance/paymentrequestview";
				break;
			case "applyDelete"://删除付款申请
				doApplyDelete();
				rtnUrl = "/business/finance/paymentrequestmain";
				break;
			case "approvalInit"://一级审核初始化
				rtnUrl = approvalInit();
				break;
			case "approvalInit2"://二级审核初始化
				rtnUrl = approvalInit2();
				break;
			case "approvalInsert"://一级审核确认
				doApprovalUpdate();
				rtnUrl = "/business/finance/paymentapprovalview";
				break;
			case "approvalInsert2"://二级审核确认
				doApprovalUpdate2();
				rtnUrl = "/business/finance/paymentapprovalview2";
				break;
			case "approvalEdit"://一级审核编辑
				doApprovalUpdateInit();
				rtnUrl = "/business/finance/paymentapproval";
				break;
			case "approvalEdit2"://二级审核编辑
				doApprovalUpdateInit();
				rtnUrl = "/business/finance/paymentapproval2";
				break;
			case "approvalDelete"://审核删除（弃审）
				doApprovalDelete();
				rtnUrl = "/business/finance/paymentapprovalmain";
				break;
			case "invoiceCheckCancel"://审核删除（弃审）:一级审核，发票确认
				doInvoiceCheckCancel();
				rtnUrl = "/business/finance/paymentapprovalmain";
				break;
			case "paymentView":
				paymentView();
				rtnUrl = "/business/finance/paymentrequestview";
				break;
			//case "print"://打印出库单
			//	doPrintReceipt();
			//	rtnUrl = "/business/finance/stockoutprint";
			//	break;
			case "printProductReceipt"://打印成品入库单
				doPrintProductReceipt();
				rtnUrl = "/business/finance/productstorageprint";
				break;
			case "getProductPhoto"://显示出库单附件
				dataMap = getProductPhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "productPhotoDelete"://删除出库单附件
				dataMap = deletePhoto("product","productFileList","productFileCount");
				printOutJsonObj(response, dataMap);
				break;
			case "approvalMainL1"://一级审核
				approvalMainL1();
				rtnUrl = "/business/finance/paymentapprovalmain";
				break;
			case "approvalMainL2"://二级审核
				approvalMainL2();
				rtnUrl = "/business/finance/paymentapprovalmain2";
				break;
			case "approvalSearch"://一级审核
				dataMap = approvalSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "approvalSearch2"://二级审核
				dataMap = approvalSearch2(data);
				printOutJsonObj(response, dataMap);
				break;
			case "finishMain"://付款完成
				rtnUrl = "/business/finance/paymentfinishmain";
				break;
			case "finishSearch":
				dataMap = finishSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "finishAddOrView":
				rtnUrl = finishAddOrView();
				break;
			case "finishInsert":
				finishInsert();
				rtnUrl = "/business/finance/paymentfinishview";
				break;
			case "finishEdit":
				//finishInsert();
				rtnUrl = "/business/finance/paymentfinishedit";
				break;
			case "finishAddInit":
				finishAddInit();
				rtnUrl = "/business/finance/paymentfinishadd";
				break;
			case "finishHistoryView":
				finishHistoryView();
				rtnUrl = "/business/finance/paymentfinishview";
				break;
			case "downloadExcelForPayment"://EXCEL导出
				downloadExcelForPayment();
				break;
			case "contractPayment"://采购合同详情：付款记录
				dataMap = contractPayment();
				printOutJsonObj(response, dataMap);
				break;
			case "paymentRequestBySupplier":
				paymentRequestBySupplierInit();
				rtnUrl = "/business/finance/paymentrequestsupplier";
				break;
			case "searchBySupplierId"://付款申请
				dataMap = doSearchBySupplierId(data);
				printOutJsonObj(response, dataMap);
				break;
			case "paymentManageInit"://付款管理
				paymentManageInit();
				rtnUrl = "/business/finance/paymentmanagemain";
				break;
			case "paymentManageSearch"://付款管理
				dataMap = doPaymentManageSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "paymentSearchMainInit"://付款查询
				paymentSearchMainInit();
				rtnUrl = "/business/finance/paymentsearchmain";
				break;
			case "paymentSearchMain"://付款查询
				dataMap = doPaymentSearchMain(data);
				printOutJsonObj(response, dataMap);
				break;
			case "paymentInvoiceList"://发票明细
				dataMap = paymentInvoiceList();
				printOutJsonObj(response, dataMap);
				break;
			case "addPyamentInvoice"://新增发票
				addPyamentInvoice();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/finance/paymentinvoiceadd";
				break;
			case "insertPyamentInvoice"://保存新增发票
				insertPyamentInvoice();
				break;
			case "editPyamentInvoice"://编辑发票
				editPyamentInvoice();
				rtnUrl = "/business/finance/paymentinvoiceadd";
				break;
			case "deletePyamentInvoice"://删除发票
				dataMap = deletePyamentInvoice();
				printOutJsonObj(response, dataMap);
				break;
			case "paymentContractList"://合同明细
				dataMap = paymentContractList();
				printOutJsonObj(response, dataMap);
				break;
			case "paymentHistoryList"://付款记录明细
				dataMap = paymentHistoryList();
				printOutJsonObj(response, dataMap);
				break;
			case "deletePyamentRecord"://删除付款记录
				dataMap = deletePyamentRecord();
				printOutJsonObj(response, dataMap);
				break;
			case "paymentAbnormalMainInit"://异常付款查询
				paymentAbnormalMainInit();
				rtnUrl = "/business/finance/paymentabnormalmain";
				break;
			case "paymentAbnormalMain"://异常付款查询
				dataMap = doPaymentAbnormalMain(data);
				printOutJsonObj(response, dataMap);
				break;
		}
		
		return rtnUrl;
	}	
	
	public void doInit(){	
		
		String searchType = (String) session.getAttribute("searchType");
		String userId = (String) session.getAttribute("userId");
		if(searchType == null || ("").equals(searchType))
			searchType = "010";//设置默认值：待申请
		
		if(userId == null || ("").equals(userId)){
			userId = "999";//设置默认值：全员
		}
		
		try {
			service.paymentRequestMainInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("searchType",searchType);
		model.addAttribute("userId",userId);		
		
	}	
	
	public void paymentManageInit(){	
		
		String searchType = (String) session.getAttribute("searchType");//付款状态
		String userId = (String) session.getAttribute("userId");
		if(searchType == null || ("").equals(searchType))
			searchType = "010";//设置默认值：待申请
		
		if(userId == null || ("").equals(userId)){
			userId = "999";//设置默认值：全员
		}
		
		try {
			service.paymentRequestMainInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("searchType",searchType);
		model.addAttribute("userId",userId);	
		
		model.addAttribute("loginId",userInfo.getUserId());	
		
	}	
	
	public void paymentSearchMainInit(){	
		
		String searchType = (String) session.getAttribute("searchType");//付款状态
		String userId = (String) session.getAttribute("userId");
		if(searchType == null || ("").equals(searchType))
			searchType = "020";//设置默认值：待申请
		
		if(userId == null || ("").equals(userId)){
			userId = "999";//设置默认值：全员
		}
		
		try {
			service.paymentRequestMainInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("searchType",searchType);
		model.addAttribute("userId",userId);		
		
	}
	
	public void paymentAbnormalMainInit(){	
		
		String searchType = (String) session.getAttribute("searchType");//付款状态
		String userId = (String) session.getAttribute("userId");
		if(searchType == null || ("").equals(searchType))
			searchType = "H";//设置默认值：发票
		
		if(userId == null || ("").equals(userId)){
			userId = "999";//设置默认值：全员
		}
		
		try {
			service.paymentRequestMainInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("searchType",searchType);
		model.addAttribute("userId",userId);		
		
	}	
	public void paymentRequestBySupplierInit(){	
		
		String supplierId = request.getParameter("supplierId");
		model.addAttribute("supplierId",supplierId);
	}
	
	private void approvalMainL1(){
		String searchType = (String) session.getAttribute("searchType");
		if(searchType == null || ("").equals(searchType))
			searchType = "020";//设置默认值：待一级审核
		model.addAttribute("searchType",searchType);
	}
	
	private void approvalMainL2(){
		String searchType = (String) session.getAttribute("searchType");
		if(searchType == null || ("").equals(searchType))
			searchType = "021";//设置默认值：待二级审核
		model.addAttribute("searchType",searchType);
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
	
	//付款单上传
	@RequestMapping(value="paymentBillUpload")
	public String doInit(
			@RequestParam(value = "photoFile", required = false) MultipartFile[] headPhotoFile,
			@RequestBody String data,
			@ModelAttribute("formModel")PaymentModel dataModel,
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

		this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		this.service = new PaymentService(model,request,response,session,dataModel,userInfo);;
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
		

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PAYMENTREQUEST+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PAYMENTREQUEST+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.doSearch(data,"after");
			
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

		String searchType = request.getParameter("searchType");
		
		model.addAttribute("searchType",searchType);
		session.setAttribute("searchType", searchType);
		
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doPaymentManageSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PAYMENTMANAGE + Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PAYMENTMANAGE + Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.paymentManageSearch(data);
			
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

		String searchType = request.getParameter("searchType");
		
		model.addAttribute("searchType",searchType);
		session.setAttribute("searchType", searchType);
		
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doPaymentSearchMain(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PAYMENTSEARCH + Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PAYMENTSEARCH + Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.paymentSearchMain(data);
			
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

		String searchType = request.getParameter("searchType");
		model.addAttribute("searchType",searchType);
		session.setAttribute("searchType", searchType);
		
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doPaymentAbnormalMain(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PAYMENTSEARCH + Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PAYMENTSEARCH + Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.paymentAbnormalMain(data);
			
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

		String searchType = request.getParameter("searchType");
		model.addAttribute("searchType",searchType);
		session.setAttribute("searchType", searchType);
		
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearchBySupplierId(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		//String sessionFlag = request.getParameter("sessionFlag");
		//if(("false").equals(sessionFlag)){
		//	session.removeAttribute(Constants.FORM_PAYMENTREQUEST+Constants.FORM_KEYWORD1);
		//	session.removeAttribute(Constants.FORM_PAYMENTREQUEST+Constants.FORM_KEYWORD2);			
		//}
		
		try {
			dataMap = service.doSearchBySupplierId(data,"after");
			
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

		String searchType = request.getParameter("searchType");
		
		model.addAttribute("searchType",searchType);
		session.setAttribute("searchType", searchType);
		
		return dataMap;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doBeforhandSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PAYMENTREQUEST+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PAYMENTREQUEST+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.doSearch(data,"before");
			
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
	public HashMap<String, Object> approvalSearch(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PAYMENTAPPROVAL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PAYMENTAPPROVAL+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.approvalSearch(data);
			
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
		

		String searchType = request.getParameter("searchType");
		
		model.addAttribute("searchType",searchType);
		session.setAttribute("searchType", searchType);
		
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> approvalSearch2(String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PAYMENTAPPROVAL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PAYMENTAPPROVAL+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.approvalSearch2(data);
			
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
		
		String searchType = request.getParameter("searchType");
		
		model.addAttribute("searchType",searchType);
		session.setAttribute("searchType", searchType);
		
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
	

	public void doApplyAddInit(){

		try{
			String searchType = request.getParameter("searchType");
			service.applyAddInit();

			model.addAttribute("userName", userInfo.getUserName());
			model.addAttribute("searchType",searchType);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	public void doApplyBeforeAddInit(){

		try{
			service.applyAddInit();

			//model.addAttribute("paymentTypeId", "020");//预付款
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public String approvalInit(){

		String  rtnUrl = "/business/finance/paymentapproval";
		try{
			String rtnFlag = service.approvalInit();
			if(("查看").equals(rtnFlag))
				rtnUrl = "/business/finance/paymentapprovalview";
			
			model.addAttribute("userName", userInfo.getUserName());
			model.addAttribute("insertFlag","insert");
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		String searchType = request.getParameter("searchType");
		
		model.addAttribute("searchType",searchType);
		session.setAttribute("searchType", searchType);
		
		return rtnUrl;
	}
	
	public String approvalInit2(){

		String  rtnUrl = "/business/finance/paymentapproval2";
		try{
			String rtnFlag = service.approvalInit2();
			if(("查看").equals(rtnFlag))
				rtnUrl = "/business/finance/paymentapprovalview2";
			
			model.addAttribute("userName", userInfo.getUserName());
			model.addAttribute("insertFlag","insert");
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}


		String searchType = request.getParameter("searchType");
		
		model.addAttribute("searchType",searchType);
		session.setAttribute("searchType", searchType);
		
		return rtnUrl;
	}
	
	
	
	public String finishAddOrView(){

		String  rtnUrl = "/business/finance/paymentfinishadd";
		try{
			String rtnFlag = service.finishAddOrView();
			if(("查看").equals(rtnFlag))
				rtnUrl = "/business/finance/paymentfinishview";
			
			model.addAttribute("userName", userInfo.getUserName());
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return rtnUrl;
	}

	
	public void finishHistoryView(){

	
		try{
			service.finishView();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	public void downloadExcelForPayment(){

		
		try{
			service.downloadExcelForPayment();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}


	public void finishAddInit(){
	
		try{
			service.finishAddInit();
			
			model.addAttribute("userName", userInfo.getUserName());
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	public void finishInsert() throws Exception{

		 service.finishInsertAndReturn();		
		
	}
	
	
	public HashMap<String, Object> getStockoutDetail() throws Exception{

		return service.getStockoutDetail();		
		
	}
	
	public HashMap<String, Object> contractPayment() throws Exception{

		return service.contractPayment();		
		
	}
	
	
	public void doApplyInsert(){
		try{
			service.applyInsertAndReturn();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doApprovalUpdateInit(){
		try{
			service.approvalUpdateInit();
			model.addAttribute("insertFlag","update");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	
	public void doApprovalUpdate(){
		try{
			service.approvalUpdateAndReturn();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doApprovalUpdate2(){
		try{
			service.approvalUpdateAndReturn2();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void doApprovalDelete(){
		try{
			service.approvalDelete();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public void doInvoiceCheckCancel(){
		try{
			service.invoiceCheckCancel();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doApplyDelete(){
		try{
			service.applyDelete();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void paymentView(){
		try{
			service.paymentView();
			
			String searchType = request.getParameter("searchType");
			model.addAttribute("searchType",searchType);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	
	public void applyUpdateInit(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.applyUpdateInit();
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
	
	//public void doPrintReceipt(){
	//	try{
	//		service.printReceipt();
	//	}catch(Exception e){
	//		System.out.println(e.getMessage());
	//	}
	//}

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
	

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> paymentInvoiceList(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			dataMap = service.getPaymentInvoiceList();
			
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
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> paymentContractList(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			dataMap = service.getPaymentContractList();
			
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
	

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> paymentHistoryList(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			dataMap = service.getPaymentHistoryList();
			
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
	
	public HashMap<String, Object> getPyamentInvoice(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			dataMap = service.getPaymentInvoiceList();
			
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
	
	public void addPyamentInvoice(){
		try{
			String paymentId     = request.getParameter("paymentId");
			String contractPrice = request.getParameter("contractPrice");
			String invoiceCnt    = request.getParameter("invoiceCnt");
						
			model.addAttribute("paymentId",paymentId);
			model.addAttribute("contractPrice",contractPrice);
			model.addAttribute("invoiceCnt",invoiceCnt);
			//发票类型
			model.addAttribute("invoiceTypeOption",
				new DicUtil().getListOption(DicUtil.DIC_INVOICETYPE,
						""));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void insertPyamentInvoice(){
		try{
			service.insertPyamentInvoice();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public HashMap<String, Object> deletePyamentInvoice(){

		HashMap<String, Object> dataMap = new HashMap<String, Object>();	
		try{
			service.deletePyamentInvoice();
			dataMap.put(INFO, SUCCESSMSG);
		}catch(Exception e){
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		return dataMap;
	}
	

	public HashMap<String, Object> deletePyamentRecord(){

		HashMap<String, Object> dataMap = new HashMap<String, Object>();	
		try{
			service.deletePyamentRecord();
			dataMap.put(INFO, SUCCESSMSG);
		}catch(Exception e){
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		return dataMap;
	}
	
	public void editPyamentInvoice(){
		try{
			
			service.editPyamentInvoiceInit();

			String paymentId = request.getParameter("paymentId");
			//发票类型
			model.addAttribute("invoiceTypeOption",
				new DicUtil().getListOption(DicUtil.DIC_INVOICETYPE,
						""));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
