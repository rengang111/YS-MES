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

import com.ys.system.action.common.BaseAction;
import com.ys.business.action.model.material.MaterialModel;
import com.ys.business.action.model.order.OrderModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.order.OrderService;
import com.ys.system.action.model.login.UserInfo;


@Controller
@RequestMapping("/business")
public class OrderAction extends BaseAction {
	
	@Autowired OrderService orderService;
	
	OrderModel orderModel= new OrderModel();

	UserInfo userInfo = new UserInfo();
	
	@RequestMapping(value="/order")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("orderForm") OrderModel reqModel, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
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
				rtnUrl = "/business/order/ordermain";
				break;				
			case "search":
				dataMap = doSearchOrderList(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "create":
				doCreate(request,reqModel,model);
				rtnUrl = "/business/order/orderadd";
				break;
			case "insert":
				doInsert(reqModel,model, request);
				rtnUrl = "/business/order/orderview";
				break;				
			case "detailView":
				doOrderView(request,model);
				rtnUrl = "/business/order/orderview";
				break;				
			case "edit":
				doEdit(reqModel,request,model);
				rtnUrl = "/business/order/orderedit";
				break;
			case "update":
				doUpdate(reqModel,model,request);
				rtnUrl = "/business/order/orderview";
				break;
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
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearchOrderList(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = orderService.getOrderList(request, data);
			
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

	public void doOrderView( 
			HttpServletRequest request,
			Model model){
		
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			String PIId = request.getParameter("PIId");
			//dataMap = orderService.getOrderViewByPIId(request,model,PIId);
			dbData = orderService.getOrderViewByPIId(request,PIId);

			model.addAttribute("order",  dbData.get(0));
			model.addAttribute("detail", dbData);
	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
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
			dataMap = orderService.getOrderSubIdByParentId(request, data);
			
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
		
		reqModel = orderService.createOrder(request,reqModel);
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
		dbData = orderService.getOrderViewByPIId(request,PIId);

		model.addAttribute("order",  dbData.get(0));
		model.addAttribute("detail", dbData);
		
	}		
	
	
	public void doEdit(
			OrderModel reqModel, 
			HttpServletRequest request,
			Model model){

			
			try {

				ArrayList<HashMap<String, String>> dbData = 
						new ArrayList<HashMap<String, String>>();
				
				String PIId = reqModel.getOrder().getPiid();
				dbData = orderService.getOrderViewByPIId(request,PIId);
				
				reqModel = orderService.createOrder(request, reqModel);

				model.addAttribute("order",  dbData.get(0));
				model.addAttribute("detail", dbData);
				model.addAttribute("orderForm", reqModel);
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
		
		//返回到明细查看页面
		String PIId = reqModel.getOrder().getPiid();
		dbData = orderService.getOrderViewByPIId(request,PIId);

		model.addAttribute("order",  dbData.get(0));
		model.addAttribute("detail", dbData);
		
		
	}	
	
	public MaterialModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		//MaterialModel = materialService.doDelete(data, userInfo);

		//return MaterialModel;
		return null;
	}

	
	
}
