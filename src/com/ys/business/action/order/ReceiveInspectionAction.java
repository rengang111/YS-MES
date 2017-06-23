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
import com.ys.business.action.model.order.ReceiveInspectionModel;
import com.ys.business.service.order.ArrivalService;
import com.ys.business.service.order.ReceiveInspectionService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class ReceiveInspectionAction extends BaseAction {
	
	@Autowired
	ReceiveInspectionService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	ReceiveInspectionModel reqModel = new ReceiveInspectionModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	
	@RequestMapping(value="receiveinspection")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")ReceiveInspectionModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new ReceiveInspectionService(model,request,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		
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
				doInit(Constants.FORM_RECEIVEINSPECTION,session);
				rtnUrl = "/business/inventory/receiveinspectionmain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "updateInit":
				doUpdateInit();
				rtnUrl = "/business/inventory/receiveinspectionadd";
				break;
			case "addinit":
				rtnUrl = doAddInit();
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/inventory/receiveinspectionview";
				break;
			case "insertPM":
				doUpdatePM();
				rtnUrl = "/business/inventory/receiveinspectionview";
				break;
			case "insertGM":
				doUpdateGM();
				rtnUrl = "/business/inventory/receiveinspectionview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				return null;
			case "detailView":
				doShowDetail();
				//printOutJsonObj(response, viewModel.getEndInfoMap());
				rtnUrl = "/business/inventory/arrivalview";
				break;
				
		}
		
		return rtnUrl;
	}	
	

	public void doInit(String formId,HttpSession session){	
			
		String keyBackup = request.getParameter("keyBackup");
		//没有物料编号,说明是初期显示,清空保存的查询条件
		if(keyBackup == null || ("").equals(keyBackup)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String keyBackup = request.getParameter("keyBackup");
		if(keyBackup != null && !("").equals(keyBackup)){
			session.removeAttribute(Constants.FORM_RECEIVEINSPECTION+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_RECEIVEINSPECTION+Constants.FORM_KEYWORD2);
			
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
		
		return dataMap;
	}
	
	public String  doAddInit(){
		String rtnUrl = "/business/inventory/receiveinspectionadd";
		try{
			String inspectArrivalId = service.addInit();
			if(inspectArrivalId != null && !("").equals(inspectArrivalId)){
				rtnUrl = "/business/inventory/receiveinspectionview";
			}
			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return rtnUrl;
	}

	public void doInsert(){
		try{
			service.insertAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


	public void doUpdateInit(){
		try{
			service.updateInit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doUpdatePM(){
		try{
			service.PMUpdateAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	

	public void doUpdateGM(){
		try{
			service.GMUpdateAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doDelete(@RequestBody String data) throws Exception{
		
		service.doDelete(data);

	}
	
	public void doShowDetail() throws Exception{
		
		service.showArrivalDetail();

	}

}
