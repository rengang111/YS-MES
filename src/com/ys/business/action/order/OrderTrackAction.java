package com.ys.business.action.order;

/**
 * 订单跟踪
 */
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
import com.ys.business.action.model.order.OrderTrackModel;
import com.ys.business.service.order.OrderTrackService;
import com.ys.system.common.BusinessConstants;
import com.ys.system.action.model.login.UserInfo;


@Controller
@RequestMapping("/business")
public class OrderTrackAction extends BaseAction {
	
	@Autowired OrderTrackService reviewService;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	OrderTrackModel reqModel = new OrderTrackModel();
	Model model;
	
	@RequestMapping(value="/orderTrack")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("reviewForm") OrderTrackModel review, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		reviewService = new OrderTrackService(model,request,review,userInfo);
		reqModel = review;
		this.model = model;
		
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
				rtnUrl = "/business/order/orderreviewmain";
				break;				
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				break;	
			case "orderTrackingForStorage":
				dataMap = orderTrackingForStorage();
				printOutJsonObj(response, dataMap);
				break;
			case "getContractByYsid":
				dataMap = getContractByYsid();
				printOutJsonObj(response, dataMap);
				break;
			case "getUnStorageContractCount":
				dataMap = getUnStorageContractCount();
				printOutJsonObj(response, dataMap);
				break;
			case "updateContracDeliveryDate":
				dataMap = updateContracDeliveryDate();
				printOutJsonObj(response, dataMap);
				break;
			case "setMaterialFinished"://设置当然任务为：料已备齐
				dataMap = setMaterialFinished();
				printOutJsonObj(response, dataMap);
				break;
			case "orderTrackingShow"://订单跟踪详情
				 doShowOrderTracking();
				rtnUrl = "/business/order/ordertrackingview";
				break;
				
				
		}
		
		return rtnUrl;		
	}	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearch(
			@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = reviewService.getReviewList(data);
			
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
	private HashMap<String, Object> orderTrackingForStorage(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = reviewService.getOrderTrackingForStorage();
			
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
	private HashMap<String, Object> getContractByYsid(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = reviewService.getContractByYsid();
			
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
	private HashMap<String, Object> getUnStorageContractCount(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = reviewService.getUnStorageContractCount();
			
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
	private HashMap<String, Object> updateContracDeliveryDate(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = reviewService.insertNewDeliveryDate();
			
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
	

	private HashMap<String, Object> setMaterialFinished(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			//reviewService.setMaterialFinished();			

			dataMap.put(INFO, NODATAMSG);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	public void doShowOrderTracking() throws Exception
	{
	    reviewService.orderTrackDetailInit();
	 
	}

}
