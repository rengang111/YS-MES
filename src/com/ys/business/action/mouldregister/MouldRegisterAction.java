
package com.ys.business.action.mouldregister;

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
import com.ys.system.action.model.login.UserInfo;
import com.ys.business.action.model.mouldregister.MouldRegisterModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.basequery.common.BaseModel;
import com.ys.business.service.mouldregister.MouldRegisterService;

@Controller
@RequestMapping("/business")
public class MouldRegisterAction extends BaseAction {
	
	@Autowired
	MouldRegisterService mouldRegisterService;
	
	@RequestMapping(value="mouldregister")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")MouldRegisterModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		MouldRegisterModel viewModel = null;
		
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
				rtnUrl = "/business/mouldregister/mouldregistermain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
				rtnUrl = doAddInit(model, session, request, response);
				break;				
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "add":
			case "update":
				viewModel = doUpdate(data, session, request);
				if (viewModel.getOperType().equals("2")) {
					request.setAttribute("key", viewModel.getKeyBackup());
					request.setAttribute("activeSubCode", viewModel.getActiveSubCode());
				}
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
			case "productModelIdSearch"://供应商查询
				dataMap = doProductModelIdSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;
			case "factoryIdSearch":
				dataMap = doFactoryIdSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;
			case "getMouldId":
				viewModel = doGetMouldId(data, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "rotate":
				viewModel = doRotate(data, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "getSubCodeFactoryList":
				dataMap = doGetSubCodeFactoryList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addSubCodeFactoryInit":
				rtnUrl = doAddSubCodeFactoryInit(model, session, request, response);
				break;
			case "updateSubCodeFactoryInit":
				rtnUrl = doUpdateSubCodeFactoryInit(model, session, request, response);
				break;
			case "supplierSearch":
				dataMap = doSupplierSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;
			case "updateFactory":
				viewModel = doUpdateFactory(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "deleteFactory":
				viewModel = doDeleteFactory(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "viewHistoryPrice":
				rtnUrl = doViewHistoryPriceInit(model, session, request, response);
				break;
			case "getFactoryPriceHistory":
				dataMap = doGetSupplierPriceHistory(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "deletePriceHistory":
				viewModel = doDeleteFactoryPriceHistory(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldRegisterService.doSearch(request, data, userInfo);
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

		MouldRegisterModel dataModel = new MouldRegisterModel();

		try {
			dataModel = mouldRegisterService.doAddInit(request);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldregister/mouldregisteredit";
	}
	
	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldRegisterModel dataModel = new MouldRegisterModel();
		String key = request.getParameter("key");
		String activeSubCode = request.getParameter("activeSubCode");
		try {
			dataModel = mouldRegisterService.doUpdateInit(request, key, activeSubCode);
			dataModel = mouldRegisterService.getFileList(request, dataModel);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldregister/mouldregisterupdate";
	}		
	
	public String doAddSubCodeFactoryInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldRegisterModel dataModel = new MouldRegisterModel();

		try {
			dataModel = mouldRegisterService.doAddFactoryInit(request);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldregister/mouldregisterfactoryedit";
	}
	
	public String doUpdateSubCodeFactoryInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldRegisterModel dataModel = new MouldRegisterModel();
		String key = request.getParameter("key");
		String activeSubCode = request.getParameter("activeSubCode");
		try {
			dataModel = mouldRegisterService.doUpdateFactoryInit(request, key, activeSubCode);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldregister/mouldregisterfactoryedit";
	}
	
	public String doViewHistoryPriceInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldRegisterModel dataModel = new MouldRegisterModel();

		try {
			dataModel = mouldRegisterService.doViewHistoryPriceInit(request);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldregister/mouldpricehistory";
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doProductModelIdSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = mouldRegisterService.doProductModelIdSearch(request);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSupplierSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = mouldRegisterService.doSupplierSearch(request);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doFactoryIdSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = mouldRegisterService.doFactoryIdSearch(request);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	public MouldRegisterModel doGetMouldId(@RequestBody String data, HttpServletRequest request){
		
		MouldRegisterModel model = new MouldRegisterModel();
		
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			String mouldId = mouldRegisterService.getMouldId(request, data);
			model.setEndInfoMap(BaseService.NORMAL, "", mouldId);	
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");	
		}
		
		return model;
	}
	
	@SuppressWarnings("unchecked")
	public MouldRegisterModel doRotate(@RequestBody String data, HttpServletRequest request){
		
		MouldRegisterModel model = new MouldRegisterModel();
		
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			String mouldId = mouldRegisterService.doRotate(request, data);
			model.setEndInfoMap(BaseService.NORMAL, "", mouldId);	
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");	
		}
		
		return model;
	}
	
	public MouldRegisterModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		MouldRegisterModel model = new MouldRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		try {
			String key = mouldRegisterService.getJsonData(data, "keyBackup");
			model = mouldRegisterService.doUpdate(request, data, userInfo);
			if (key != null && !key.equals("")) {
				model.setOperType("");
			} else {
				model.setOperType("2");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return model;
	}	
	
	public MouldRegisterModel doUpdateFactory(String data, HttpSession session, HttpServletRequest request){
		
		MouldRegisterModel model = new MouldRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		try {
			model = mouldRegisterService.doUpdateFactory(request, data, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return model;
	}
	
	public MouldRegisterModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldRegisterModel model = new MouldRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldRegisterService.doDelete(request, data, userInfo);

		return model;
	}

	public MouldRegisterModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldRegisterModel model = new MouldRegisterModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldRegisterService.doDelete(request, data, userInfo);
		
		return model;
	}

	public MouldRegisterModel doDeleteFactory(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldRegisterModel model = new MouldRegisterModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		try {
			model = mouldRegisterService.doDeleteFactory(request, data, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");	
		}
		
		return model;
	}
	
	public MouldRegisterModel doDeleteFactoryPriceHistory(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldRegisterModel model = new MouldRegisterModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldRegisterService.doDeleteFactoryPriceHistory(request, data, userInfo);
		
		return model;
	}
	
	public HashMap<String, Object> doGetSubCodeFactoryList(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldRegisterService.doGetSubCodeFactoryList(request, data, userInfo);
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
	
	public HashMap<String, Object> doGetSupplierPriceHistory(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldRegisterService.doGetFactoryPriceHistory(request, data, userInfo);
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
	
}
