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
import com.ys.business.action.model.order.OrderReviewModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.order.OrderReviewService;
import com.ys.system.action.model.login.UserInfo;


@Controller
@RequestMapping("/business")
public class OrderReviewAction extends BaseAction {
	
	@Autowired OrderReviewService reviewService;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	OrderReviewModel reqModel = new OrderReviewModel();
	Model model;
	
	@RequestMapping(value="/orderreview")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("reviewForm") OrderReviewModel review, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		reviewService = new OrderReviewService(model,request,review,userInfo);
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
				rtnUrl = "/business/orderreview/orderreviewmain";
				break;				
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "create":
				rtnUrl = doCreate();
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/orderreview/orderreviewview";
				break;	
			case "edit":
				doEdit();
				rtnUrl = "/business/orderreview/orderreviewadd";
				break;
			case "approve":
				doApprove();//审批
				rtnUrl = "/business/orderreview/orderreviewview";
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


	public String doCreate() throws Exception{

		String strUrl = "/business/orderreview/orderreviewadd";
		
		String url = reviewService.createReview();
		
		if(url != null && url.equals("view")){
			strUrl = "/business/orderreview/orderreviewview";
		}
		return strUrl;
	}
	
	public void doInsert() throws Exception {

		model = reviewService.insertAndView();
		
	}	
	
	public void doApprove() throws Exception {

		model = reviewService.approvAndView();
		
	}		
	
	
	public void doEdit() throws Exception{

		model = reviewService.editReview();
	}	

}
