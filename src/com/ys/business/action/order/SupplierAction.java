package com.ys.business.action.order;

import java.net.URLDecoder;
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

import com.ys.business.action.model.order.SupplierModel;
import com.ys.business.service.order.SupplierService;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class SupplierAction extends BaseAction {
	
	@Autowired
	SupplierService service;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	SupplierModel reqModel = new SupplierModel();
	HashMap<String, Object> modelMap = new HashMap<String, Object>();
	Model model;
	HttpServletResponse response;
	@RequestMapping(value="supplier")
	public String execute(@RequestBody String data, @ModelAttribute("formModel")SupplierModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		HashMap<String, Object> dataMap = null;
		SupplierModel viewModel = null;
		
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		service = new SupplierService(model,request,response,session,dataModel,userInfo);
		reqModel = dataModel;
		this.model = model;
		this.response = response;
		
		String rtnUrl = null;
		
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
				doInit(Constants.FORM_SUPPLIER,session);
				rtnUrl = "/business/supplier/suppliermain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				rtnUrl = doAddInit();
				break;
			case "insert":
				doInsert();
				//printOutJsonObj(response, viewModel.getEndInfoMap());
				rtnUrl = "/business/supplier/supplierview";
				break;
			case "show":
				doShowDetail();
				rtnUrl = "/business/supplier/supplierview";
				break;
			case "showById":
				doShowDetailById();
				rtnUrl = "/business/supplier/supplierview";
				break;
			case "edit":
				doUpdateInit();
				rtnUrl = "/business/supplier/supplieredit";
				break;
			case "delete":
				viewModel = doDelete(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "deleteDetail":
				viewModel = doDeleteDetail(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "optionChange":
				viewModel = doOptionChange(data);
				if (viewModel.getUnsureList() != null) {
					printOutJsonObj(response, viewModel.getUnsureList());
				} else {
					printOutJsonObj(response, viewModel.getEndInfoMap());
				}
				return null;
			case "optionChange2":
				viewModel = doOptionChange2(data);
				if (viewModel.getUnsureList() != null) {
					printOutJsonObj(response, viewModel.getUnsureList());
				} else {
					printOutJsonObj(response, viewModel.getEndInfoMap());
				}
				return null;
			case "optionChange3":
				doOptionChange3(data);
				printOutJsonObj(response, modelMap);
				return null;
			case "setSupplierId":
				setSupplierId(data);
				printOutJsonObj(response, modelMap);
				return null;
			case "checkShortName":
				checkShortName();
				printOutJsonObj(response, modelMap);
				return null;
				//printOutJsonObj(response, modelMap);
				//rtnUrl = "/business/supplier/supplieradd2";
		}
		
		return rtnUrl;
	}	
	
	public void doInit(String formId,HttpSession session){	
		
	}
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			//优先执行查询按钮事件,清空session中的查询条件
			String sessionFlag = request.getParameter("sessionFlag");
			if(("false").equals(sessionFlag)){
				session.removeAttribute(Constants.FORM_SUPPLIER+Constants.FORM_KEYWORD1);
				session.removeAttribute(Constants.FORM_SUPPLIER+Constants.FORM_KEYWORD2);
				
			}		
			dataMap = service.doSearch(request, data, session);
			@SuppressWarnings("unchecked")
			ArrayList<HashMap<String, String>> dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
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
	
	public String doAddInit(){
		model = service.doAddInit();

		return "/business/supplier/supplieradd";
	}
	
	public void doInsert(){
		
		service.insertAndView();
		
	}	
	
	public void doShowDetailById(){

		String key = request.getParameter("key");
		try {
			model = service.getSupplierById(key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void doShowDetail(){

		SupplierModel dataModel = new SupplierModel();
		String key = request.getParameter("key");
		try {
			model = service.getSupplierBaseInfo(key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		//model.addAttribute("DisplayData", dataModel);
	}	
	
	public void doUpdateInit(){

		SupplierModel dataModel = new SupplierModel();
		String key = request.getParameter("key");
		try {
			model = service.getSupplierBaseInfo(key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		//model.addAttribute("DisplayData", dataModel);
	}
	public SupplierModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		SupplierModel model = new SupplierModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = service.doDelete(data, userInfo);

		return model;
	}

	public SupplierModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		SupplierModel model = new SupplierModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = service.doDelete(data, userInfo);

		return model;
	}	
	
	public SupplierModel doOptionChange(String data) throws Exception{
		//request.setCharacterEncoding("utf-8"); 
		SupplierModel model = new SupplierModel();
		String[] paras = data.split("&");
		String[] datas = paras[1].split("=");
		String province = request.getParameter("province");
		province = URLDecoder.decode(province,"utf-8");
		//province = new String(province.getBytes("ISO8859-1"), "UTF-8");
 		model.setUnsureList(service.getCityList(province));

		return model;
	}
	
	public SupplierModel doOptionChange2(String data) throws Exception{
		SupplierModel model = new SupplierModel();
		String province = request.getParameter("province");
		province = URLDecoder.decode(province,"utf-8");
 		model.setUnsureList(service.getCountyList(province));

		return model;
	}

	public void doOptionChange3(String data) throws Exception{
		
		request.setCharacterEncoding("utf-8"); 
		String parentId = request.getParameter("parentId");
		parentId = URLDecoder.decode(parentId,"utf-8");

		modelMap = service.getSupplierId(parentId);

	}
	
	public void setSupplierId(String data) throws Exception{
		
		request.setCharacterEncoding("utf-8"); 
		String parentId = request.getParameter("parentId");
		parentId = URLDecoder.decode(parentId,"utf-8");

		modelMap = service.getSupplierId(parentId);

	}
	
	public void checkShortName() throws Exception{
		
		modelMap = service.checkShortName();

	}

}
