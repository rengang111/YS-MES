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

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.action.model.order.WarehouseModel;
import com.ys.business.service.order.ArrivalService;
import com.ys.business.service.order.ProduceService;
import com.ys.business.service.order.WarehouseService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class WarehouseAction extends BaseAction {
	
	@Autowired
	WarehouseService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	WarehouseModel reqModel = new WarehouseModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="warehouse")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")WarehouseModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		service = new WarehouseService(model,request,response,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
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
				doInit();
				rtnUrl = "/business/warehouse/warehousecodemain";
				break;			
			case "warehouseCodeSearch":
				dataMap = warehouseCodeSearch(data);
				printOutJsonObj(response, dataMap);
				return null;	
			case "addTopInit":
				addTopInit();
				rtnUrl = "/business/warehouse/warehousecodeadd";
				break;	
			case "addWarehouseCode":
				dataMap = addWarehouseCode();
				printOutJsonObj(response, dataMap);
				break;
			case "editWarehouseCode":
				doEdit();
				rtnUrl = "/business/warehouse/warehousecodeedit";
				break;
			case "updateWarehouseCode":
				dataMap = updateWarehouseCode();
				printOutJsonObj(response, dataMap);
				break;
			case "deleteWarehouseCode":
				dataMap = deleteWarehouseCode();
				printOutJsonObj(response, dataMap);
				break;
			case "updateSortNo":
				dataMap = updateSortNo();
				printOutJsonObj(response, dataMap);
				break;
			case "materialCategorySearch":
				dataMap = materialCategorySearch();
				printOutJsonObj(response, dataMap);
				break;
				
		}
		
		return rtnUrl;
	}	
	
	public void doInit(){	
		String searchSts = (String) session.getAttribute("searchSts");
		if(searchSts == null || ("").equals(searchSts))
				searchSts = "0";//设置默认值：待申请
		model.addAttribute("searchSts",searchSts);
		
		try {
			//service.contractArrivalSearchInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> warehouseCodeSearch(
			String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_WAREHOUSECODE+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_WAREHOUSECODE+Constants.FORM_KEYWORD2);
			
		}
		try {
			dataMap = service.warehouseCodeSearch(Constants.FORM_WAREHOUSECODE,data);
			
			dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
			if (dbData.size() == 0) {
				dataMap.put(INFO, NODATAMSG);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}

		String searchSts = request.getParameter("searchSts");
		session.setAttribute("searchSts", searchSts);
		
		return dataMap;
	}
	
	public void addTopInit(){

		try{
			service.getParentCodeDetail();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public HashMap<String, Object> addWarehouseCode(){

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try{
			service.insertWarehouseCode();
			dataMap.put(INFO, "SUCCESSMSG");
		}catch(Exception e){
			dataMap.put(INFO, "ERRMSG");
			e.printStackTrace();
		}
		
		return  dataMap;
		
	}
	
	public HashMap<String, Object> updateWarehouseCode(){

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try{
			service.updateWarehouseCode();
			dataMap.put(INFO, "SUCCESSMSG");
		}catch(Exception e){
			dataMap.put(INFO, "ERRMSG");
			e.printStackTrace();
		}
		
		return  dataMap;
		
	}

	public HashMap<String, Object> materialCategorySearch(){

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = service.getMaterialCategory();
			dataMap.put(INFO, "SUCCESSMSG");
		}catch(Exception e){
			dataMap.put(INFO, "ERRMSG");
			e.printStackTrace();
		}
		
		return  dataMap;
		
	}
	
	public void addAgainInit(){

		try{
			model.addAttribute("userName", userInfo.getUserName());
			//service.addAgainInit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void doInsert(){
		try{
			//service.insertAndViewArrival();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doEdit(){
		try{
			service.getParentCodeDetail();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public HashMap<String, Object> deleteWarehouseCode() throws Exception{

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try{
			service.deleteWarehouseCode();
			dataMap.put(INFO, "SUCCESSMSG");
		}catch(Exception e){
			dataMap.put(INFO, "ERRMSG");
			e.printStackTrace();
		}

		return dataMap;
	}
	

	public HashMap<String, Object> updateSortNo() throws Exception{

		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try{
			service.updateSortNo();
			dataMap.put(INFO, "SUCCESSMSG");
		}catch(Exception e){
			dataMap.put(INFO, "ERRMSG");
			e.printStackTrace();
		}

		return dataMap;
	}
	
	public void doShowDetail() throws Exception{
		
		try{
			//service.showArraivlDetail();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	
}
