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
import com.ys.business.action.model.order.RequisitionModel;
import com.ys.business.service.order.ArrivalService;
import com.ys.business.service.order.RequisitionService;
import com.ys.business.service.order.RequisitionZZService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
/**
 * 自制件领料
 * @author rengang
 *
 */
@Controller
@RequestMapping("/business")
public class RequisitionZZAction extends BaseAction {
	@Autowired
	RequisitionZZService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	RequisitionModel reqModel = new RequisitionModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="requisitionzz")
	public String execute(@RequestBody String data, 
			@ModelAttribute("formModel")RequisitionModel dataModel, 
			BindingResult result, 
			Model model, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		String makeType = request.getParameter("makeType");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new RequisitionZZService(model,request,session,dataModel,userInfo);
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
				doInit(makeType);
				rtnUrl = "/business/manufacture/requisitionzzmain";
				break;
			case "searchOrderList":
				String formId = Constants.FORM_REQUISITION_Z;//注塑
				if(("blow").equals(makeType)){
					formId = Constants.FORM_REQUISITION_C;//吹塑
				}else if (("blister").equals(makeType)){
					formId = Constants.FORM_REQUISITION_X;//吸塑
				}
				dataMap = doSearch(makeType,data,formId);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				doAddInit(makeType);
				rtnUrl = "/business/manufacture/requisitionzzadd";
				break;
			case "updateInit":
				doUpdateInit();
				rtnUrl = "/business/manufacture/requisitionzzedit";
				break;
			case "update":
				doUpdate();
				rtnUrl = "/business/manufacture/requisitionzzview";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/manufacture/requisitionzzview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				return null;
			case "getRawMaterialList":
				dataMap = getRawMaterialList(makeType);
				printOutJsonObj(response, dataMap);
				//rtnUrl = "/business/manufacture/requisitionview";
				rtnUrl = null;
				break;
			case "getRequisitionHistoryInit":
				doRequisitionHistoryInit();
				rtnUrl = "/business/manufacture/requisitionzzview";
				break;
			case "getRequisitionHistory":
				dataMap = getRequisitionHistory();
				printOutJsonObj(response, dataMap);
				return null;
			case "getRequisitionDetail":
				dataMap = getRequisitionDetail();
				printOutJsonObj(response, dataMap);
				return null;
			case "print"://领料单打印
				doAddInit(makeType);
				rtnUrl = "/business/manufacture/requisitionzzprint";
				break;
			case "getMaterialZZList"://取得自制品
				dataMap = getMaterialZZList(makeType);
				printOutJsonObj(response, dataMap);
				rtnUrl = null;
				break;
				
		}
		
		return rtnUrl;
	}	
	
	public void doInit(String makeType){

	}	
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(String makeType,String data,String formId){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			dataMap = service.doSearch(makeType,data,formId);
			
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
	
	
	public void doAddInit(String makeType){
		try{
			service.addInit();

			model.addAttribute("userName", userInfo.getUserName());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void doRequisitionHistoryInit(){
		try{
			service.getRequisitionHistoryInit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
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
			model.addAttribute("userName", userInfo.getUserName());
			service.updateInit();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	

	public void doUpdate(){
		try{
			model.addAttribute("userName", userInfo.getUserName());
			service.updateAndView();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void doDelete(@RequestBody String data) throws Exception{
		
		service.doDelete(data);

	}
	
	public HashMap<String, Object> getRawMaterialList(String makeType) throws Exception{
		
		return service.getRawMaterial(makeType);

	}

	public HashMap<String, Object> getMaterialZZList(String makeType) throws Exception{
		
		return service.getMaterialZZ(makeType);

	}

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> getRequisitionHistory(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			String taskId = request.getParameter("taskId");
			dataMap = service.getRequisitionHistory(taskId);
			
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
	public HashMap<String, Object> getRequisitionDetail(){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();		
		
		try {
			dataMap = service.getRequisitionDetail();
			
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
}
