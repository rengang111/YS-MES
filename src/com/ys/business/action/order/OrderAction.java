package com.ys.business.action.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.business.action.model.supplier.SupplierModel;
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.system.action.common.BaseAction;
import com.ys.business.action.model.material.MaterialModel;
import com.ys.business.action.model.order.OrderModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.material.MaterialService;
import com.ys.business.service.order.OrderService;
import com.ys.util.DicUtil;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.action.model.dic.DicModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.service.common.BaseService;

import org.springframework.web.servlet.ModelAndView;

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
				/*
			case "supplierPriceView":
				dataMap = doSupplierPriceView(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "supplierPriceHistory":
				dataMap = doSupplierPriceHistory(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "supplierPriceHistoryInit":
				doSupplierPriceHistoryInit(reqModel,model, request);
				rtnUrl = "/business/material/matbidhistory";
				break;
				*/
			case "create":
				doCreate(request,reqModel,model);
				rtnUrl = "/business/order/orderadd";
				break;
			case "insert":
				doInsert(reqModel,model, request);
				//rtnUrl = "/business/order/materialview";
				break;
				
			case "detailView":
				doOrderView(request,model);
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/order/orderview";
				break;
				
			case "edit":
				doEdit(reqModel,request,model);
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/order/orderedit";
				break;/*
			case "addSupplier":
				doAddSupplier(reqModel,model, request);
				rtnUrl = "/business/material/matbidadd";
				//printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
			case "supplierSearch"://供应商查询
				dataMap = doSupplierSearch(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "insertPrice":
				doInsertPrice(reqModel,model, request);
				//rtnUrl = "/business/material/materialview";
				printOutJsonObj(response, reqModel.getEndInfoMap());
				break;
			case "editPrice":
				doEditPrice(reqModel,model, request);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				rtnUrl = "/business/material/matbidedit";
				break;
			case "insertRefresh":
				doInsert(reqModel,model, request);
				printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
			case "update":
				doUpdate(reqModel,model,request);
				rtnUrl = "/business/material/materialview";
				break;
			case "delete":
				MaterialModel = doDelete(data, session, request, response);
				printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
				*/
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
				dataMap = doMaterialList(data, request);
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
		
		HashMap<String, String> dataMap = null;
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			String PIId = request.getParameter("PIId");
			//dataMap = orderService.getOrderViewByPIId(request,model,PIId);
			dbData = orderService.getOrderViewByPIId(request,PIId);

			model.addAttribute("order",  dbData.get(0));
			model.addAttribute("detail", dbData);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("orderForm");
			//if (dbData.size() == 0) {
			//	dataMap.put(INFO, NODATAMSG);
			//}
			//model.addAttribute("orderData",dataMap);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	/*
	public void doSupplierPriceHistoryInit(
			MaterialModel reqModel,
			Model model,
			HttpServletRequest request){	
		
		B_PriceSupplierData price = new B_PriceSupplierData();

		String supplierId = request.getParameter("supplierId");
		price.setSupplierid(supplierId);
		//MaterialModel.setPrice(price); 
		//model.addAttribute("material", MaterialModel);
		
	}


	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSupplierPriceHistory(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			//dataMap = materialService.supplierPriceHistory(request, data);
			
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
*/
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
	public HashMap<String, Object> doMaterialList(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = orderService.getMaterialList(request, data);
			
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
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSupplierSearch(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			//dataMap = materialService.getSupplierList(request, data);
			
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
	*/
	public void doCreate(
			HttpServletRequest request,
			OrderModel reqModel,
			Model model){
		
		reqModel = orderService.createOrder(request,reqModel);
		model.addAttribute("orderForm", reqModel);
		
	}
	
	public void doInsert(OrderModel reqModel,
			Model model,
			HttpServletRequest request) throws Exception {

		model = orderService.insert(reqModel,model, request,userInfo);
		
	}		

	
	public void doUpdate(OrderModel reqModel,Model model,
			HttpServletRequest request) throws Exception {
				
		model = orderService.update(reqModel,model, request,userInfo);
		
		
	}	
	
	public MaterialModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		//MaterialModel = materialService.doDelete(data, userInfo);

		//return MaterialModel;
		return null;
	}

		
	
	public void doEdit(OrderModel reqModel, 
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
	
}
