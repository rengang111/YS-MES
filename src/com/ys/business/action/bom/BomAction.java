package com.ys.business.action.bom;

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
import com.ys.business.action.model.bom.BomPlanModel;
import com.ys.business.action.model.material.MaterialModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.bom.BomService;
import com.ys.business.service.order.OrderService;
import com.ys.system.action.model.login.UserInfo;


@Controller
@RequestMapping("/business")
public class BomAction extends BaseAction {
	
	@Autowired BomService bomService;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	BomPlanModel reqModel = new BomPlanModel();
	Model model;
	
	@RequestMapping(value="/bom")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("bomForm") BomPlanModel bom, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		bomService = new BomService(model,request,bom,userInfo);
		reqModel = bom;
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
				rtnUrl = "/business/bom/bomplanmain";
				break;				
			case "search":
				dataMap = doSearchBomList(data);
				printOutJsonObj(response, dataMap);
				break;
			case "create":
				doCreate();
				rtnUrl = "/business/bom/bomplanadd";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/bom/bomplanview";
				break;
							
			case "detailView":
				doBomDetailView();
				rtnUrl = "/business/bom/bomplanview";
				break;	
			case "copy":
				doCopy();
				rtnUrl = "/business/bom/bomplanadd";
				break;	
				/*
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
				*/
			case "getSupplierPriceList"://供应商编号查询
				dataMap = doSupplierPriceList(request);
				printOutJsonObj(response, dataMap);
				break;
			case "getMaterialPriceList"://物料编号查询
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
			@ModelAttribute("orderForm") BomModel reqModel, 
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
	public HashMap<String, Object> doSearchBomList(
			@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = bomService.getBomList(data);
			
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

	public void doCopy() throws Exception{
			
		model = bomService.copyBomPlan();
			
	}

	public void doBomDetailView() throws Exception{
				
		model = bomService.getBomDetailView();
			
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doCustomerOrderMAXId(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = bomService.getOrderSubIdByParentId(request, data);
			
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
			dataMap = bomService.getMaterialList(request);
			
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
	 * doSupplierPriceList
	 */	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSupplierPriceList(
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = bomService.getSupplierPriceList(request);
			
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

	public void doCreate() throws Exception{
		
		model = bomService.createBomPlan();
		
	}
	
	public void doInsert() throws Exception {

		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		model = bomService.insert();
		
		//返回到明细查看页面
		//String PIId = reqModel.getOrder().getPiid();
		//dbData = bomService.getOrderViewByPIId(request,PIId);

		//model.addAttribute("order",  dbData.get(0));
		//model.addAttribute("detail", dbData);
		
	}		
	
	
	public void doEdit(
			BomPlanModel reqModel, 
			HttpServletRequest request,
			Model model){

			
			try {

				ArrayList<HashMap<String, String>> dbData = 
						new ArrayList<HashMap<String, String>>();
				
				String PIId = reqModel.getOrder().getPiid();
				//dbData = bomService.getOrderViewByPIId(PIId);
				
				//reqModel = bomService.createOrder(request, reqModel);

				model.addAttribute("order",  dbData.get(0));
				model.addAttribute("detail", dbData);
				model.addAttribute("orderForm", reqModel);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
	}	
	
	public void doUpdate(
			BomPlanModel reqModel,
			Model model,
			HttpServletRequest request) throws Exception {

		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		//model = bomService.update(reqModel,model, request,userInfo);
		
		//返回到明细查看页面
		String PIId = reqModel.getOrder().getPiid();
		//dbData = bomService.getOrderViewByYSId(request,PIId);

		model.addAttribute("order",  dbData.get(0));
		//model.addAttribute("detail", dbData);
		
		
	}	
	
	public MaterialModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		//MaterialModel = materialService.doDelete(data, userInfo);

		//return MaterialModel;
		return null;
	}

	
	
}
