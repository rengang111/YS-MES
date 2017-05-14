package com.ys.system.action.unit;

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
import com.ys.system.action.model.unit.UnitModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.unit.UnitService;
import com.ys.util.DicUtil;


@Controller

public class UnitAction extends BaseAction {
	
	@Autowired
	UnitService unitService;
	
	@RequestMapping("/unit")
	public String doInit(@RequestBody String data, @ModelAttribute("dataModels")UnitModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		UnitModel viewModel = null;
		
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
				rtnUrl = "/unit/unitmanageframe";
				break;
			case "initframe":
				rtnUrl = "/unit/unitmain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "update":
				viewModel = doUpdate(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "add":
				viewModel = doAdd(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "delete":
				viewModel = doDelete(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "detail":
				rtnUrl = doDetail(model, session, request, response);
				break;
			case "unitSearch":
				dataMap = doUnitSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;
			case "addressSearch":
				dataMap = doAddressSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;
		}
		
		return rtnUrl;		
		
	}	
	
	public HashMap<String, Object> doSearch(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = unitService.doSearch(request, data, userInfo);
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

	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		UnitModel dataModel = new UnitModel();
		String operType = request.getParameter("operType");
		try {
			if (operType.equals("update")) {
				dataModel = unitService.getDetail(request);
				dataModel.setParentUnitId(dataModel.getunitData().getParentid());
			}
			if (operType.equals("addsub")) {
				dataModel = unitService.getParentDeptName(request);
				//dataModel.setParentUnitId(dataModel.getunitData().getParentid());
				dataModel.setUnitPropertyList(DicUtil.getGroupValue(DicUtil.UNITPROPERTY));
				dataModel.setUnitTypeList(DicUtil.getGroupValue(DicUtil.UNITTYPE));
			}
			if (operType.equals("add")) {
				dataModel.setUnitPropertyList(DicUtil.getGroupValue(DicUtil.UNITPROPERTY));
				dataModel.setUnitTypeList(DicUtil.getGroupValue(DicUtil.UNITTYPE));
			}
			dataModel.setOperType(operType);	
			dataModel.setIsOnlyView("");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/unit/unitedit";
	}	
	
	//public String doUpdate(UnitModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
	public UnitModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		UnitModel dataModel = new UnitModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			int preCheckResult = unitService.preCheck(request, "update", data, userInfo.getUnitId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				dataModel = unitService.doUpdate(request, data, userInfo, false);
				//dataModel.setUpdatedRecordCount(1);
				dataModel.setOperType("update");
				DicUtil.emptyBuffer(true);
				break;
			case 1:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "父单位不正确", "");
				break;				
			case 2:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "父单位不正确", "");
				break;
			case 3:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "单位名称已存在", "");
				break;
			}			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		return dataModel;
	}	

	public UnitModel doAdd(String data, HttpSession session, HttpServletRequest request){
		
		UnitModel dataModel = new UnitModel();
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			int preCheckResult = unitService.preCheck(request, "add", data, userInfo.getUnitId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				dataModel = unitService.doUpdate(request, data, userInfo, true);
				dataModel.setOperType("add");
				DicUtil.emptyBuffer(true);
				break;
			case 1:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "父单位不正确", "");
				break;				
			case 2:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "父单位不正确", "");
				break;
			case 3:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "单位名称已存在", "");
				break;
			case 4:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "单位不存在", "");
				break;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return dataModel;
	}	
	
	public UnitModel doDelete(String data, HttpSession session, HttpServletRequest request){

		UnitModel dataModel = new UnitModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = unitService.doDelete(request, data, userInfo);
		DicUtil.emptyBuffer(true);
		dataModel.setOperType("delete");
		
		return dataModel;
	}
	
	public String doDetail(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		UnitModel dataModel = new UnitModel();
		
		try {
			dataModel = unitService.getDetail(request);
			dataModel.setIsOnlyView("1");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("详细信息取得失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/unit/unitedit";
	}	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doUnitSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = unitService.doUnitSearch(request, data);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doAddressSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = unitService.doAddressSearch(request, data);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
}
