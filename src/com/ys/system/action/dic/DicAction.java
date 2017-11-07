package com.ys.system.action.dic;

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
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.power.PowerModel;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.action.model.dic.DicModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.dic.DicService;
import com.ys.util.DicUtil;

@Controller

public class DicAction extends BaseAction {
	
	@Autowired
	DicService dicService;
	
	@RequestMapping("/diccode")
	//public String execute(@ModelAttribute("dataModels")DicModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
	public String doInit(@RequestBody String data, @ModelAttribute("dataModels")DicModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		DicModel viewModel = null;
		
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
				rtnUrl = "/dic/dicmain";
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
			dataMap = dicService.doSearch(request, data, userInfo);
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

		DicModel dataModel = new DicModel();
		String operType = request.getParameter("operType");
		String index = request.getParameter("index");
		try {
			
			if (operType.equals("update")) {
				dataModel = dicService.getDetail(request);
			}
			if (operType.equals("addsub")) {
				dataModel = dicService.getDetail(request);
				S_DICData data = new S_DICData();
				String dicCodeId = request.getParameter("dicCodeId");
				String dicTypeId = URLDecoder.decode(request.getParameter("dicTypeId"), "UTF-8");
				data.setDicprarentid(dicCodeId);
				dataModel.setDicParentName(DicUtil.getCodeValue(dicTypeId + dicCodeId));
				data.setDictypeid(dicTypeId);
				dataModel.setdicData(data);
			}
			if (operType.equals("addsubfromtype")) {
				S_DICData data = new S_DICData();
				data.setDictypeid(URLDecoder.decode(request.getParameter("dicTypeId"), "UTF-8"));
				dataModel.setDicTypeName(request.getParameter("dicTypeName"));
				dataModel.setdicData(data);
			}			
			
			if (operType.equals("add")) {
			}
			dataModel.setOperType(operType);	
			dataModel.setIsOnlyView("");
			dataModel.setIndex(index);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/dic/dicedit";
	}	
	
	public DicModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		DicModel dataModel = new DicModel();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			
			int preCheckResult = dicService.preCheck(data, "update");
			switch(preCheckResult) {
			case 0:
				dataModel = dicService.doUpdate(request, data, userInfo, false);
				DicUtil.emptyBuffer(false);
				break;
			case 1:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "代码已存在", "");
				break;				
			case 2:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "代码已存在", "");
				break;
			}			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return dataModel;
	}	

	public DicModel doAdd(String data, HttpSession session, HttpServletRequest request){
		DicModel dataModel = new DicModel();
		try {

			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
			int preCheckResult = dicService.preCheck(data, "add");
			switch(preCheckResult) {
			case 0:
				dataModel = dicService.doUpdate(request, data, userInfo, true);
				DicUtil.emptyBuffer(false);
				break;
			case 1:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "代码已存在", "");
				break;				
			case 2:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "代码已存在", "");
				break;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return dataModel;
	}	
	
	public DicModel doDelete(String data, HttpSession session, HttpServletRequest request){
		
		DicModel dataModel = new DicModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = dicService.doDelete(request, data, userInfo);
		DicUtil.emptyBuffer(false);
		dataModel.setOperType("delete");
		
		return dataModel;
	}
	
	public String doDetail(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		DicModel dataModel = new DicModel();
		
		try {
			dataModel = dicService.getDetail(request);
			dataModel.setIsOnlyView("1");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("详细信息取得失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/dic/dicedit";
	}	
}
