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

import com.ys.system.action.common.BaseAction;
import com.ys.business.action.model.order.OrderModel;
import com.ys.business.action.model.order.OrderReviewModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.basequery.common.Constants;
import com.ys.business.service.order.OrderReviewService;
import com.ys.business.service.order.OrderService;
import com.ys.system.action.model.login.UserInfo;


@Controller
@RequestMapping("/business")
public class OrderAction extends BaseAction {
	
	@Autowired OrderService orderService;
	@Autowired HttpServletRequest request;
	
	OrderModel reqModel= new OrderModel();
	UserInfo userInfo = new UserInfo();
	Model model;
	HttpSession session;
	HttpServletResponse response;
	
	@RequestMapping(value="/order")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("orderForm") OrderModel order, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		orderService = new OrderService(model,request,response,session,order,userInfo);
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
				doInit(Constants.FORM_ORDER,session);
				rtnUrl = "/business/order/ordermain";
				break;
			case "search":
				dataMap = doSearchOrderList(Constants.FORM_ORDER,data);
				printOutJsonObj(response, dataMap);
				break;
			case "orderTrackingSearchInit"://订单跟踪查询
				orderTrackingSearchInit();
				rtnUrl = "/business/order/ordertrackingmain";
				break;
			case "orderTrackingSearch"://订单跟踪查询
				dataMap = doSearchOrderTrackingList(Constants.FORM_ORDERTRACKING,data);
				printOutJsonObj(response, dataMap);
				break;
			case "orderTrackingShow"://订单跟踪详情
				 doShowOrderTracking();
				rtnUrl = "/business/order/ordertrackingview";
				break;
			case "orderTrackingDetail"://订单跟踪详情
				dataMap = purchasePlanView();
				printOutJsonObj(response, dataMap);
				break;
			case "create":
				doCreate(request,reqModel,model);
				rtnUrl = "/business/order/orderadd";
				break;
			case "createByMaterialId"://来自于成品管理
				createByMaterialId(reqModel,request,model);
				rtnUrl = "/business/order/orderedit";
				break;
			case "insert":
				doInsert(reqModel,model, request);
				rtnUrl = "/business/order/orderview";
				break;
			case "detailView":
				rtnUrl = doOrderView();
				break;				
			case "edit":
				doEdit(reqModel,request,model);
				rtnUrl = "/business/order/orderedit";
				break;
			case "update":
				doUpdate(reqModel,model,request);
				rtnUrl = "/business/order/orderview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				rtnUrl = "/business/order/ordermain";
			case "customerSearch"://客户编号查询
				dataMap = doCustomerSearch(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "customerOrderMAXId"://物料最新编号查询
				dataMap = doCustomerOrderMAXId(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "getMaterialList"://物料最新编号查询
				dataMap = doMaterialList(request);
				printOutJsonObj(response, dataMap);
				break;			
			case "searchDemand"://查询物料需求表待做成的数据
				dataMap = doSearchOrderListDemand(data);
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/demand/demandmain";
				break;			
			case "searchDemand1"://
				documentaryEdit();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/order/documentaryedit";
				break;			
			case "documenterayNameSearch"://
				dataMap = documenterayNameSearch();
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/demand/demandmain";
				break;			
			case "getPurchaseOrder":
				getPurchaseOrder();
				rtnUrl = "/business/purchase/purchaseorderlist";
				break;		
			case "piidExistCheck":
				dataMap = piidExistCheck();
				printOutJsonObj(response, dataMap);
				return null;		
			case "ysidExistCheck":
				dataMap = ysidExistCheck();
				printOutJsonObj(response, dataMap);
				return null;
			case "getYsidList":
				dataMap = getYsidList();
				printOutJsonObj(response, dataMap);
				return null;
			case "setOrderFollow"://设置重点关注订单
				dataMap = setOrderFollow();
				printOutJsonObj(response, dataMap);
				return null;
			case "orderCancelSearchInit":
				doInit(Constants.FORM_ORDER,session);
				rtnUrl = "/business/order/ordercancelmain";
				break;
			case "orderCancelSearch":
				dataMap = doSearchOrderCancelList(Constants.FORM_ORDERCANCEL,data);
				printOutJsonObj(response, dataMap);
				break;
			case "orderCancelAddInit":
				doOrderCancelAddInit();
				rtnUrl = "/business/order/ordercanceladd";
				break;
			case "orderCancelAdd":
				doOrderCancelAdd();
				rtnUrl = "/business/order/ordercancelview";
				break;
			case "orderCancelView":
				doOrderCancelView();
				rtnUrl = "/business/order/ordercancelview";
				break;
			case "orderCancelEdit":
				doOrderCancelEdit();
				rtnUrl = "/business/order/ordercanceledit";
				break;
			case "orderCancelUpdate":
				doOrderCancelUpdate();
				rtnUrl = "/business/order/ordercancelview";
				break;
			case "orderCancelDelete":
				doOrderCancelDelete();
				rtnUrl = "/business/order/ordercancelmain";
				break;
			case "orderTransfer":
				doOrderTransfer();
				rtnUrl = "/business/order/ordertransferadd";
				break;
			case "orderDetailSearch":
				dataMap = orderDetailSearch();
				printOutJsonObj(response, dataMap);
				break;
			case "insertTransfer":
				doInsertTransfer(reqModel,model, request);
				rtnUrl = "/business/order/orderview";
				break;
			case "orderExpenseInit"://订单过程初始化
				orderExpenseInit();
				rtnUrl = "/business/order/orderexpensemain";
				break;
			case "orderExpenseSearch"://订单过程查询
				dataMap = doSearchOrderExpenseList(Constants.FORM_ORDEREXPENSE,data);
				printOutJsonObj(response, dataMap);
				break;
			case "orderExpenseYsid"://选择订单（耀升编号）
				rtnUrl = "/business/order/orderexpenseysid";
				break;
			case "orderExpenseProductPhoto"://显示订单过程的附件
				dataMap = getProductPhoto();
				printOutJsonObj(response, dataMap);
				break;
			case "productPhotoDelete"://删除订单过程的附件
				dataMap = deletePhoto("product","productFileList","productFileCount");
				printOutJsonObj(response, dataMap);
				break;
			case "getOrderDetailByYSId":
				getOrderDetailByYSId();
				rtnUrl = "/business/order/orderview";

			case "insertDivertOrder"://保存挪用订单
				insertDivertOrder(data);
				printOutJsonObj(response, dataMap);
				break;
			case "getDivertOrder"://显示挪用订单
				dataMap = getDivertOrder();
				printOutJsonObj(response, dataMap);
				break;
		}
		
		return rtnUrl;		
	}
	
	/*
	@RequestMapping(value="/orderView")
	public ModelAndView orderView(
			@RequestBody String data, 
			@ModelAttribute("orderForm") OrderModel reqModel, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {

		ModelAndView mv;

		mv = new ModelAndView("/business/order/orderview");
				
		HashMap<String, String> dataMap = null;
	
		//dataMap = orderService.getOrderViewByRecordId(request,model);
		

		mv.addObject("orderData",dataMap);
		mv.addObject("test","22222");
	
		return mv;		
	}
*/
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
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearchOrderCancelList(String formId, String data){
		
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
			dataMap = orderService.getOrderCancelList(formId,data);
			
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
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearchOrderExpenseList(String formId, String data){
		
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
			dataMap = orderService.getOrderExpenseList(formId,data);
			
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
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearchOrderTrackingList(String formId, String data){
		
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
			dataMap = orderService.getOrderTrackingList(formId,data);
			
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

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearchOrderListDemand(@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = orderService.getOrderListDemand(data);
			
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
	
	public String doOrderView(){
		
		String rtnUrl = "/business/order/ordermain";
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			String PIId = request.getParameter("PIId");
			String yskFlag = "";
			if(null != PIId && PIId.length() > 3){
				yskFlag = PIId.substring(PIId.length() - 2,PIId.length());
				if(yskFlag.equals(BusinessConstants.SHORTNAME_ZZ)){
					
					dbData = orderService.getZZOrderDetail(PIId);
					
					rtnUrl = "/business/order/zzorderview";	
					
				}else if(yskFlag.equals(BusinessConstants.SHORTNAME_ZP)){

					dbData = orderService.getZPOrderDetail(PIId);
					
					rtnUrl = "/business/order/zporderview";
					
				}else{
					dbData = orderService.getOrderViewByPIId(PIId);
					
					rtnUrl = "/business/order/orderview";
				}
			}else{
				dbData = orderService.getOrderViewByPIId(PIId);
				rtnUrl = "/business/order/orderviewcomm";
			}

			model.addAttribute("order",  dbData.get(0));
			model.addAttribute("detail", dbData);
	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return rtnUrl;
		
	}

	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doCustomerSearch(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = orderService.getCustomer(request, data);
			
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
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doCustomerOrderMAXId(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {

			String key = request.getParameter("parentId");

			dataMap = orderService.getOrderSubIdByParentId(key);
			
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
	
	/*
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doMaterialList(
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = orderService.getMaterialList(request);
			
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

	public void doCreate(
			HttpServletRequest request,
			OrderModel reqModel,
			Model model){
		
		reqModel = orderService.createOrder();
		model.addAttribute("orderForm", reqModel);
		
	}
	
	public void doInsert(
			OrderModel reqModel,
			Model model,
			HttpServletRequest request) throws Exception {

		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		model = orderService.insert(reqModel,model, request,userInfo);
		
		//返回到明细查看页面
		String PIId = reqModel.getOrder().getPiid();
		dbData = orderService.getOrderViewByPIId(PIId);

		String goBackFlag = request.getParameter("goBackFlag");
		model.addAttribute("order",  dbData.get(0));
		model.addAttribute("detail", dbData);
		model.addAttribute("goBackFlag",goBackFlag);
		
	}		
	
	public void doInsertTransfer(
			OrderModel reqModel,
			Model model,
			HttpServletRequest request) throws Exception {

		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		model = orderService.insertTransfer(reqModel,model, request,userInfo);
		
		//返回到明细查看页面
		String PIId = reqModel.getOrder().getPiid();
		dbData = orderService.getOrderViewByPIId(PIId);

		String goBackFlag = request.getParameter("goBackFlag");
		model.addAttribute("order",  dbData.get(0));
		model.addAttribute("detail", dbData);
		model.addAttribute("goBackFlag",goBackFlag);
		
	}		
	
	
	public void doEdit(
			OrderModel reqModel, 
			HttpServletRequest request,
			Model model){
		
			try {
				String goBackFlag = request.getParameter("goBackFlag");

				ArrayList<HashMap<String, String>> dbData = 
						new ArrayList<HashMap<String, String>>();
				
				String PIId = reqModel.getOrder().getPiid();
				dbData = orderService.getOrderViewByPIId(PIId);
				
				reqModel = orderService.createOrder();

				model.addAttribute("order",  dbData.get(0));
				model.addAttribute("detail", dbData);
				model.addAttribute("orderForm", reqModel);
				model.addAttribute("goBackFlag",goBackFlag);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
	}	

	public void createByMaterialId(
			OrderModel reqModel, 
			HttpServletRequest request,
			Model model){
		
			try {

				orderService.createOrderByMaterialId();

				String goBackFlag = request.getParameter("goBackFlag");
				model.addAttribute("goBackFlag",goBackFlag);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
	}	
	
	
	public void doUpdate(
			OrderModel reqModel,
			Model model,
			HttpServletRequest request) throws Exception {

		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		model = orderService.update(reqModel,model, request,userInfo);

		String goBackFlag = request.getParameter("goBackFlag");
		//返回到明细查看页面
		String PIId = reqModel.getOrder().getPiid();
		dbData = orderService.getOrderViewByPIId(PIId);

		model.addAttribute("order",  dbData.get(0));
		model.addAttribute("detail", dbData);
		model.addAttribute("goBackFlag",goBackFlag);
		
		
	}	
	
	public void doDelete(String data){
		
		orderService.delete(data);

	}
	public void documentaryEdit()
	{
	  try {
	    this.orderService.documentaryEdit();
	  }
	  catch (Exception e) {
	    System.out.println(e.getMessage());
	  }
	}
	
	public void getPurchaseOrder()
	{
	  try {
	    this.orderService.getPurchaseOrder();
	  }
	  catch (Exception e) {
	    System.out.println(e.getMessage());
	  }
	}

	public HashMap<String, Object>  piidExistCheck() throws Exception
	{
	    return this.orderService.piidExistCheck();
	 
	}
	
	public HashMap<String, Object>  getYsidList() throws Exception
	{
	    return this.orderService.getYsidList();
	 
	}	

	public HashMap<String, Object>  orderDetailSearch() throws Exception
	{
	    return this.orderService.getOrderViewByPIId();
	 
	}
	
	public HashMap<String, Object>  ysidExistCheck() throws Exception
	{
		String YSId = request.getParameter("YSId");
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		String where = " YSId like '%"+YSId +"%' AND deleteFlag='0' " ;
		String ExFlag = orderService.ysidExistCheck(where);
		dataMap.put("ExFlag",ExFlag);
	    return dataMap;
	 
	}	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> documenterayNameSearch()
	{
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
	  try
	  {
	    dataMap = this.orderService.getDocumenterayName();

	    dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
	    if (dbData.size() == 0)
	      dataMap.put("message", "无符合条件的数据");
	  }
	  catch (Exception e)
	  {
	    System.out.println(e.getMessage());
	    dataMap.put("message", "操作失败，请再次尝试或联系管理员");
	  }

	  return dataMap;
	}
	
	public void doShowOrderTracking() throws Exception
	{
	    this.orderService.getOrderDetail();
	 
	}	
	
	public HashMap<String, Object> purchasePlanView() throws Exception {
		
		return orderService.getOrderTrackingDetail();		
		
	}
	
	public HashMap<String, Object> setOrderFollow() throws Exception {
		
		return orderService.setOrderFollow();		
		
	}
	
	private void doOrderCancelAddInit(){
		
		orderService.orderCancelAddInit();
		
		model.addAttribute("userName",userInfo.getUserName());
	}


	private void doOrderCancelAdd(){
		
		try {
			orderService.orderCancelInsertAndView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	private void doOrderCancelUpdate(){
		
		try {
			orderService.orderCancelUpdateAndView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	private void doOrderCancelView(){
		
		try {
			orderService.orderCancelView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	private void doOrderCancelEdit(){
		
		try {
			orderService.orderCancelEdit();
			model.addAttribute("userName",userInfo.getUserName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void doOrderCancelDelete(){
		
		try {
			orderService.orderCancelDelete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		

	public void doOrderTransfer(){
		
		reqModel = orderService.createOrder();
		model.addAttribute("orderForm", reqModel);
		
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doOrderCancelSearch(String formId, String data){
		
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
			dataMap = orderService.getOrderCancelList(formId,data);
			
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
	
	private void orderExpenseInit() throws Exception {		
		
		String monthday = (String) session.getAttribute("monthday");
		if(monthday == null || monthday == "")
			monthday = CalendarUtil.getLastDate();
		
		String statusFlag = (String) session.getAttribute("statusFlag");
		if(statusFlag == null || statusFlag == "")
			statusFlag = "020";//默认是已入库
		
		String hiddenCol = (String) session.getAttribute("hiddenCol");
		if(statusFlag == null || statusFlag == "")
			statusFlag = "5";//默认是隐藏订单交期列

		model.addAttribute("monthday",monthday);
		model.addAttribute("statusFlag",statusFlag);
		model.addAttribute("hiddenCol",hiddenCol);
		
	}
	
	public HashMap<String, Object> getProductPhoto(){	

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			modelMap = orderService.getProductPhoto();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	

	public HashMap<String, Object> insertDivertOrder(String data){	

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			orderService.insertDivertOrder(data);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	
	public HashMap<String, Object> getDivertOrder(){	

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			modelMap = orderService.getDivertOrder();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	
	public HashMap<String, Object> deletePhoto(
			String folderName,String fileList,String fileCount){	

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			modelMap = orderService.deletePhotoAndReload(folderName,fileList,fileCount);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			modelMap.put(INFO, ERRMSG);
		}
		
		return modelMap;
	}
	
	//出库单上传
	@RequestMapping(value="orderExpensePhoto")
	public String doInit(
			@RequestParam(value = "photoFile", required = false) MultipartFile[] headPhotoFile,
			@RequestBody String data,
			@ModelAttribute("orderForm") OrderModel order, 
			BindingResult result, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response){

			this.userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			orderService = new OrderService(model,request,response,session,order,userInfo);
			this.reqModel = order;
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
			 map = orderService.uploadPhotoAndReload(headPhotoFile,folderName,fileList,fileCount);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return map;
	}
	
	public void getOrderDetailByYSId() throws Exception {

		//返回到明细查看页面
		String PIId = request.getParameter("PIId");

		ArrayList<HashMap<String, String>> dbData =  
				orderService.getOrderViewByPIId(PIId);		

		model.addAttribute("order",  dbData.get(0));
		model.addAttribute("detail", dbData);
	}
	
	public void orderTrackingSearchInit(){
		try {
			orderService.orderTrackingSearchInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
