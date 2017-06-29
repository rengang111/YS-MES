package com.ys.system.action.dic;

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
import com.ys.system.action.model.dic.DicModel;
import com.ys.system.action.model.dic.DicTypeModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.dic.DicTypeService;
import com.ys.util.DicUtil;


@Controller

public class DicTypeAction extends BaseAction {
	
	@Autowired
	DicTypeService dicTypeService;
	
	@RequestMapping("/dictype")
	//public String execute(@ModelAttribute("dataModels")DicTypeModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
	public String doInit(@RequestBody String data, @ModelAttribute("dataModels")DicTypeModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		DicTypeModel viewModel = null;
		
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
				rtnUrl = "/dic/dictypemain";
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
		}
		
		return rtnUrl;		

	}	
	
	public HashMap<String, Object> doSearch(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = dicTypeService.doSearch(request, data, userInfo);
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

		DicTypeModel dataModel = new DicTypeModel();
		String operType = request.getParameter("operType");
		try {
			session.getAttribute(BusinessConstants.SESSION_USERINFO);
			if (operType.equals("update")) {
				dataModel = dicTypeService.getDetail(request);
				//dataModel.setDicTypeId(dataModel.getdicTypeData().getDictypeid());
			}
			if (operType.equals("add")) {
				dataModel.setDicSelectedFlagList(DicUtil.getGroupValue(DicUtil.DICSELECTEDFLAG));
				dataModel.setDicTypeLevelList(DicUtil.getGroupValue(DicUtil.DICTYPELEVEL));
			}
			dataModel.setOperType(operType);	
			dataModel.setIsOnlyView("");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/dic/dictypeedit";
	}	
	
	public DicTypeModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		DicTypeModel dataModel = new DicTypeModel();
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);

			dataModel.setDicSelectedFlagList(DicUtil.getGroupValue(DicUtil.DICSELECTEDFLAG));
			dataModel.setDicTypeLevelList(DicUtil.getGroupValue(DicUtil.DICTYPELEVEL));
			
			int preCheckResult = dicTypeService.preCheck(data, "update");
			switch(preCheckResult) {
			case 0:
				dataModel = dicTypeService.doUpdate(request, data, userInfo, false);
				break;
			case 1:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "代码类已存在", "");
				break;				
			case 2:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "代码类已存在", "");
				break;
			}			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return dataModel;
	}	

	public DicTypeModel doAdd(String data, HttpSession session, HttpServletRequest request){
		DicTypeModel dataModel = new DicTypeModel();
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			
			dataModel.setDicSelectedFlagList(DicUtil.getGroupValue(DicUtil.DICSELECTEDFLAG));
			dataModel.setDicTypeLevelList(DicUtil.getGroupValue(DicUtil.DICTYPELEVEL));
			
			int preCheckResult = dicTypeService.preCheck(data, "add");
			switch(preCheckResult) {
			case 0:
				dataModel = dicTypeService.doUpdate(request, data, userInfo, true);
				break;
			case 1:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "代码类已存在", "");
				break;				
			case 2:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "代码类已存在", "");
				break;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return dataModel;
	}	
	
	public DicTypeModel doDelete(String data, HttpSession session, HttpServletRequest request){
		
		DicTypeModel dataModel = new DicTypeModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = dicTypeService.doDelete(request, data, userInfo);
		DicUtil.emptyBuffer(true);
		dataModel.setOperType("delete");
		
		return dataModel;
	}
	
	public String doDetail(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		DicTypeModel dataModel = new DicTypeModel();
		
		try {
			dataModel = dicTypeService.getDetail(request);
			dataModel.setIsOnlyView("1");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("详细信息取得失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/dic/dictypeedit";
	}	
}
