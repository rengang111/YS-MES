package com.ys.business.action.mouldcontract;

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
import com.ys.business.action.model.externalsample.ExternalSampleModel;
import com.ys.business.action.model.mouldcontract.MouldContractModel;
import com.ys.business.action.model.mouldregister.MouldRegisterModel;
import com.ys.business.action.model.processcontrol.ProcessControlModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.business.service.mouldcontract.MouldContractService;

@Controller
@RequestMapping("/business")
public class MouldContractAction extends BaseAction {
	
	@Autowired
	MouldContractService mouldContractService;
	
	@RequestMapping(value="mouldcontract")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")MouldContractModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		MouldContractModel viewModel = null;
		
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
				rtnUrl = "/business/mouldcontract/mouldcontractmain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
			case "updateinit":
				rtnUrl = doGetMouldContractBaseInfo(model, session, request, response);
				break;
			case "add":
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
			case "getMouldDetailList":
				dataMap = getMouldDetailList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "getPayList":
				dataMap = getPayList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addmdinit":		
			case "updatemdinit":
				rtnUrl = doUpdateMdInit(model, session, request, response);
				break;				
			case "deletemd":
			case "deletemoulddetail":
				viewModel = doDeleteMd(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "updatemoulddetail":
				viewModel = doUpdateMd(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "addacceptance":
			case "updateacceptance":
				viewModel = doUpdateAcceptance(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "confirmacceptance":
				viewModel = doConfirmAcceptance(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "addpayinit":
			case "updatepayinit":
				rtnUrl = doUpdatePayInit(model, session, request, response);
				break;	
			case "deletepay":
				viewModel = doDeletePay(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;	
			case "updatepay":
				viewModel = doUpdatePay(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "productModelIdSearch"://供应商查询
				dataMap = doProductModelIdSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;
			case "confirmpay":
				viewModel = doConfirmPay(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "getMouldBaseInfoList":
				dataMap = getMouldBaseInfoList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "getMouldContractId":
				viewModel = getMouldContractId(data, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "getMouldFactoryList":
				dataMap = getMouldFactoryList(data, request);
				printOutJsonObj(response, dataMap.get("factoryList"));
				return null;
				
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldContractService.doSearch(request, data, userInfo);
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
	
	public String doGetMouldContractBaseInfo(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldContractModel dataModel = new MouldContractModel();
		String key = request.getParameter("key");
		try {
			dataModel = mouldContractService.getMouldContractBaseInfo(request, key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldcontract/mouldcontractedit";
	}		
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doProductModelIdSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = mouldContractService.doProductModelIdSearch(request, data);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}

	@SuppressWarnings("unchecked")
	public MouldContractModel getMouldContractId(@RequestBody String data, HttpServletRequest request){
		
		MouldContractModel model = new MouldContractModel();
		
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			String mouldContractId = mouldContractService.doGetMouldContractId(request, data);
			model.setEndInfoMap(BaseService.NORMAL, "", mouldContractId);	
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");	
		}
		
		return model;
	}
	
	public HashMap<String, Object> getMouldDetailList(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldContractService.doGetMouldDetailList(request, data, userInfo);
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
	
	public HashMap<String, Object> getPayList(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldContractService.doGetPayList(request, data, userInfo);
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
	
	public HashMap<String, Object> getMouldBaseInfoList(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldContractService.doGetMouldBaseInfoList(request, data, userInfo);
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
	
	public HashMap<String, Object> getMouldFactoryList(@RequestBody String data, HttpServletRequest request){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			ArrayList<ListOption> datas = mouldContractService.getMouldFactoryList(request, data);
			dataMap.put("factoryList", datas);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	public String doUpdateMdInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldContractModel dataModel = new MouldContractModel();
		String mouldBaseId = request.getParameter("mouldBaseId");
		String key = request.getParameter("key");

		try {
			dataModel = mouldContractService.doUpdateMdInit(request, mouldBaseId, key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldcontract/mouldcontractmoulddetailedit";
	}	
	
	public MouldContractModel doUpdateAcceptance(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MouldContractModel model = new MouldContractModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doUpdateAcceptance(request, data, "", userInfo);
		
		return model;
	}	
	
	public MouldContractModel doConfirmAcceptance(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MouldContractModel model = new MouldContractModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doConfirmAcceptance(request, data, userInfo);
		
		return model;
	}	
	
	public String doUpdatePayInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldContractModel dataModel = new MouldContractModel();
		String mouldBaseId = request.getParameter("mouldBaseId");
		String key = request.getParameter("key");
		String withhold = request.getParameter("withhold");
		try {
			dataModel = mouldContractService.doUpdatePayInit(request, mouldBaseId, key, withhold);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldcontract/mouldcontractpayedit";
	}	
	
	public MouldContractModel doUpdateMd(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MouldContractModel model = new MouldContractModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doUpdateMd(request, data, userInfo);
		
		return model;
	}		
	
	public MouldContractModel doUpdatePay(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MouldContractModel model = new MouldContractModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doUpdatePay(request, data, "", userInfo);
		
		return model;
	}	
	
	public MouldContractModel doDeleteMd(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldContractModel model = new MouldContractModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doDeleteMd(request, data, userInfo);

		return model;
	}
	
	public MouldContractModel doDeletePay(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldContractModel model = new MouldContractModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doDeletePay(request, data, userInfo);

		return model;
	}
	
	public MouldContractModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		MouldContractModel model = new MouldContractModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doUpdate(request, data, userInfo);
		
		return model;
	}	
	
	public MouldContractModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldContractModel model = new MouldContractModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doDelete(request, data, userInfo);

		return model;
	}

	public MouldContractModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldContractModel model = new MouldContractModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doDelete(request, data, userInfo);
		
		return model;
	}
	
	public MouldContractModel doConfirmPay(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MouldContractModel model = new MouldContractModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doConfirmPay(request, data, userInfo);
		
		return model;
	}	

}
