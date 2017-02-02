package com.ys.business.action.mouldlendregister;

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
import com.ys.business.action.model.mouldlendregister.MouldLendRegisterModel;
import com.ys.business.action.model.processcontrol.ProcessControlModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;
import com.ys.business.service.mouldlendregister.MouldLendRegisterService;

@Controller
@RequestMapping("/business")
public class MouldLendRegisterAction extends BaseAction {
	
	@Autowired
	MouldLendRegisterService mouldContractService;
	
	@RequestMapping(value="mouldlendregister")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")MouldLendRegisterModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		MouldLendRegisterModel viewModel = null;
		
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
				rtnUrl = "/business/mouldlendregister/mouldlendregistermain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addinit":
			case "updateinit":
				rtnUrl = doGetMouldLendRegisterBaseInfo(model, session, request, response);
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
			case "getMouldLendRegisterList":
				dataMap = getMouldLendRegisterList(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "addldinit":		
			case "updateldinit":
				rtnUrl = doUpdateLdInit(model, session, request, response);
				break;				
			case "deleteld":
			case "deletemouldlendregisterdetail":
				viewModel = doDeleteLd(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "updatemouldlendregisterdetail":
				viewModel = doUpdateLd(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "confirmlend":
				viewModel = doConfirmLend(data, session, request, response);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "productModelIdSearch"://供应商查询
				dataMap = doProductModelIdSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;
			case "modelNoSearch"://供应商查询
				dataMap = doModelNoSearch(data, request);
				printOutJsonObj(response, dataMap);
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
	
	public String doGetMouldLendRegisterBaseInfo(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldLendRegisterModel dataModel = new MouldLendRegisterModel();
		String key = request.getParameter("key");
		try {
			dataModel = mouldContractService.getMouldLendRegisterBaseInfo(request, key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldlendregister/mouldlendregisteredit";
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
	public HashMap<String, Object> doModelNoSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = mouldContractService.doModelNoSearch(request, data);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	public HashMap<String, Object> getMouldLendRegisterList(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = mouldContractService.doGetMouldLendDetailList(request, data, userInfo);
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
	
	public String doUpdateLdInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MouldLendRegisterModel dataModel = new MouldLendRegisterModel();
		String mouldLendNo = request.getParameter("mouldLendNo");
		String key = request.getParameter("key");

		try {
			dataModel = mouldContractService.doUpdateLdInit(request, mouldLendNo, key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/mouldlendregister/mouldlendregisterdetailedit";
	}	
	
	public MouldLendRegisterModel doConfirmLend(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doConfirmLend(request, data, userInfo);
		
		return model;
	}	
	
	public MouldLendRegisterModel doUpdateLd(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doUpdateLd(request, data, userInfo);
		
		return model;
	}
	
	public MouldLendRegisterModel doDeleteLd(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doDeleteLd(request, data, userInfo);

		return model;
	}
	
	public MouldLendRegisterModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doUpdate(request, data, "0", userInfo);
		
		return model;
	}	
	
	public MouldLendRegisterModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doDelete(request, data, userInfo);

		return model;
	}

	public MouldLendRegisterModel doDeleteDetail(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		model = mouldContractService.doDelete(request, data, userInfo);
		
		return model;
	}
	
}
