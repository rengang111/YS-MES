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
import com.ys.business.service.order.ArrivalService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class ArrivalAction extends BaseAction {
	
	@Autowired
	ArrivalService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	ArrivalModel reqModel = new ArrivalModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="arrival")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")ArrivalModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String makeType = request.getParameter("makeType");
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new ArrivalService(model,request,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		model.addAttribute("makeType",makeType);
		
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
				rtnUrl = "/business/inventory/arrivalmain";
				break;			
			case "contractArrivalSearch":
				dataMap = contractArrivalSearch(makeType,data);
				printOutJsonObj(response, dataMap);
				return null;	
			case "addinit":
				doAddInit();
				rtnUrl = "/business/inventory/arrivaladd";
				break;	
			case "addAgainInit":
				addAgainInit();
				rtnUrl = "/business/inventory/arrivaladd";
				break;
			case "edit":
				doEdit();
				rtnUrl = "/business/inventory/arrivaledit";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/inventory/arrivalview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				break;
			case "detailView":
				doShowDetail();
				//printOutJsonObj(response, viewModel.getEndInfoMap());
				rtnUrl = "/business/inventory/arrivalview";
				break;
			case "getArrivalHistory":
				dataMap = getArrivalHistory();
				printOutJsonObj(response, dataMap);
				break;
			case "gotoArrivalView":
				gotoArrivalView();
				rtnUrl = "/business/inventory/arrivalview";
				break;
			case "getArrivalByYSId":
				dataMap = getArrivalByYSId();
				printOutJsonObj(response, dataMap);
				break;
				
		}
		
		return rtnUrl;
	}	
	
	public void doInit(){	
		String searchSts = (String) session.getAttribute("searchSts");
		String userId = (String) session.getAttribute("userId");
		if(searchSts == null || ("").equals(searchSts))
				searchSts = "0";//设置默认值：待申请
		model.addAttribute("searchSts",searchSts);

		if(userId == null || ("").equals(userId))
			userId = "999";//设置默认值：全员
		model.addAttribute("userId",userId);
		
		try {
			service.contractArrivalSearchInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> contractArrivalSearch(
			String makeType, String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_ARRIVAL+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_ARRIVAL+Constants.FORM_KEYWORD2);
			
		}
		try {
			dataMap = service.contractArrivalSearch(makeType,data);
			
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
		String userId = request.getParameter("userId");
		session.setAttribute("searchSts", searchSts);
		session.setAttribute("userId", userId);
		
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

	public void addAgainInit(){

		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.addAgainInit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void doInsert(){
		try{
			service.insertAndViewArrival();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doEdit(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.doEdit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void doDelete(@RequestBody String data) throws Exception{
		
		service.doDelete(data);

	}
	
	public void doShowDetail() throws Exception{
		
		try{
			service.showArraivlDetail();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}


	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> getArrivalHistory(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			String contractId = request.getParameter("contractId");
			dataMap = service.getArrivalHistory(contractId);
			
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
	

	public HashMap<String, Object> getArrivalByYSId(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		try {
			dataMap = service.getArrivalByYsid();		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());			
		}
		
		return dataMap;
	}
	
	public void gotoArrivalView(){
		try{
			String contractId = request.getParameter("contractId");
			service.getContractDetail(contractId);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
