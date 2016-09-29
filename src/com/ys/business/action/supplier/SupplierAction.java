package com.ys.business.action.supplier;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.TestModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.supplier.SupplierModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;
import com.ys.business.service.supplier.SupplierService;

@Controller
@RequestMapping("/business")
public class SupplierAction extends BaseAction {
	
	@Autowired
	SupplierService supplierService;
	
	@RequestMapping(value="supplier")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")SupplierModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		SupplierModel viewModel = null;
		
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
				rtnUrl = "/business/supplier/suppliermain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				rtnUrl = doAddInit(model, session, request, response);
				break;
			case "add":
				viewModel = doAdd(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "update":
				viewModel = doUpdate(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
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
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = supplierService.doSearch(request, data, userInfo);
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
	
	public String doAddInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		SupplierModel supplierModel = supplierService.doAddInit(request);
		model.addAttribute("DisplayData", supplierModel);

		return "/business/supplier/supplieredit";
	}
	
	public SupplierModel doAdd(String data, HttpSession session, HttpServletRequest request){
		
		SupplierModel model = new SupplierModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = supplierService.doAdd(request, data, userInfo);
		
		return model;
	}		
	
	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		SupplierModel dataModel = new SupplierModel();
		String key = request.getParameter("key");
		try {
			dataModel = supplierService.getSupplierBaseInfo(key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/supplier/supplieredit";
	}	
	
	public SupplierModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		SupplierModel model = new SupplierModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = supplierService.doUpdate(request, data, userInfo);
		
		return model;
	}	
	
	public SupplierModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		SupplierModel model = new SupplierModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = supplierService.doDelete(data, userInfo);

		return model;
	}

	public SupplierModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		SupplierModel model = new SupplierModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = supplierService.doDelete(data, userInfo);

		return model;
	}	
	
	public SupplierModel doOptionChange(String data){
		
		SupplierModel model = null;
		
		String[] paras = data.split("&");
		String[] datas = paras[1].split("=");
		model = supplierService.doOptionChange(DicUtil.ADDRESS, datas[1]);

		return model;
	}

}
