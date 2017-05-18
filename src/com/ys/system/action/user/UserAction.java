package com.ys.system.action.user;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.role.RoleModel;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.user.UserService;
import com.ys.util.DicUtil;


@Controller

public class UserAction extends BaseAction {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/userphoto")
	public String doInit(@RequestParam(value = "headPhotoFile", required = false) MultipartFile headPhotoFile, @ModelAttribute("dataModels")UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		uploadHeadPhoto(headPhotoFile, request, response);
		//boolean printResult = printOut(response, rtnUrl);
		return null;
	}
	
	@RequestMapping("/user")
	public String doInit(@RequestBody String data, @ModelAttribute("dataModels")UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){	
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		UserModel viewModel = null;
		
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
				rtnUrl = "/user/usermanageframe";
				break;
			case "initframe":
				rtnUrl = "/user/usermain";
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
			case "lock":
				viewModel = doLock(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "unlock":
				viewModel = doUnLock(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "resetpwdinit":
				rtnUrl = doResetPwdInit();
				break;
			case "resetpwd":
				viewModel = doUnLock(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			//case "uploadHeadPhoto":
				//uploadHeadPhoto(headPhotoFile,	request, response);
				//uploadHeadPhoto(data, request, response);
				//boolean printResult = printOut(response, rtnUrl);
				//return null;
			case "dutiesIdNameSearch":
				dataMap = doDutiesIdNameSearch(data, request);
				printOutJsonObj(response, dataMap);
				return null;
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
			dataMap = userService.doSearch(request, data, userInfo);
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

	private String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		UserModel dataModel = new UserModel();
		String operType = request.getParameter("operType");
		try {
			if (operType.equals("update")) {
				dataModel = userService.getDetail(request);
			}
			if (operType.equals("add")) {
				dataModel.setDutyList(DicUtil.getGroupValue(DicUtil.DUTIES));
				dataModel.setSexList(DicUtil.getGroupValue(DicUtil.SEX));
			}
			dataModel.setOperType(operType);	
			dataModel.setIsOnlyView("");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/user/useredit";
	}	
	
	public UserModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		
		UserModel dataModel = new UserModel();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);

			int preCheckResult = userService.preCheck(request, data, "update", userInfo.getUserId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				dataModel = userService.doUpdate(request, data, userInfo, false);
				break;
			case 1:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "登录ID已存在", "");
				break;
			case 2:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "登录id不存在", "");
				break;
			case 3:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "登录ID已存在", "");
				break;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		return dataModel;
	}	

	public UserModel doAdd(String data, HttpSession session, HttpServletRequest request){
		UserModel dataModel = new UserModel();

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			int preCheckResult = userService.preCheck(request, data, "add", userInfo.getUserId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				dataModel = userService.doUpdate(request, data, userInfo, true);
				break;
			case 1:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "登录ID已存在", "");
				break;
			case 2:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "登录id不存在", "");
				break;
			case 3:
				dataModel.setEndInfoMap(BaseService.DUMMYKEY, "登录ID已存在", "");
				break;
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		return dataModel;
	}	
	
	public UserModel doDelete(String data, HttpSession session, HttpServletRequest request){
		
		UserModel dataModel = new UserModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = userService.doDelete(request, data, userInfo);
		DicUtil.emptyBuffer(true);
		dataModel.setOperType("delete");
		
		return dataModel;
	}
	
	private String doDetail(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		UserModel dataModel = new UserModel();
		
		try {
			dataModel.setIsOnlyView("1");
			dataModel = userService.getDetail(request);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("详细信息取得失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/user/useredit";
	}
	
	private UserModel doLock(String data, HttpSession session, HttpServletRequest request){

		UserModel dataModel = new UserModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = userService.doLock(data, userInfo, 1);
		DicUtil.emptyBuffer(true);
		dataModel.setOperType("delete");
		
		return dataModel;
	}
	
	private UserModel doUnLock(String data, HttpSession session, HttpServletRequest request){

		UserModel dataModel = new UserModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = userService.doLock(data, userInfo, 0);
		DicUtil.emptyBuffer(true);
		dataModel.setOperType("delete");
		
		return dataModel;
	}

	private void uploadHeadPhoto(MultipartFile headPhotoFile, HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	    //MultipartFile headPhotoFile = multipartRequest.getFile("headPhotoFile");
	    
		JSONObject jsonObj = userService.uploadPhoto(headPhotoFile, request);
		
		try {
			out = response.getWriter();
			out.print(jsonObj);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	private String doResetPwdInit() {
		return "/user/userresetpwd";
	}
	
	
	public UserModel doResetPwd(String data, HttpSession session, HttpServletRequest request){
		UserModel dataModel = new UserModel();

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			int resetResult = userService.doResetPwd(request, dataModel, userInfo);
			if (resetResult == 0) {
				dataModel.setMessage("密码修改成功");
			} else {
				dataModel.setMessage("现在的密码输入不正确");
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		return dataModel;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doDutiesIdNameSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = userService.doDutiesIdNameSearch(request, data);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doUnitSearch(@RequestBody String data, HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = userService.doUnitSearch(request, data);
			
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
			dataMap = userService.doAddressSearch(request, data);
			
			//dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
}
