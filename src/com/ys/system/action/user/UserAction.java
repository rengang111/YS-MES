package com.ys.system.action.user;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.system.service.user.UserService;
import com.ys.util.DicUtil;


@Controller

public class UserAction extends BaseAction {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/user")
	//public String doInit(@RequestParam(value = "headPhotoFile", required = false) MultipartFile headPhotoFile, @ModelAttribute("dataModels")UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
	public String doInit(@ModelAttribute("dataModels")UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){	
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		
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
				rtnUrl = doSearch(dataModel, result,  model, session, request, response);
				break;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "update":
				rtnUrl = doUpdate(dataModel, result, model, session, request, response);
				break;
			case "add":
				rtnUrl = doAdd(dataModel, result, model, session, request, response);
				break;
			case "delete":
				rtnUrl = doDelete(dataModel, result, model, session, request, response);
				break;
			case "detail":
				rtnUrl = doDetail(model, session, request, response);
				break;
			case "lock":
				rtnUrl = doLock(dataModel, result, model, session, request, response);
				break;
			case "unlock":
				rtnUrl = doUnLock(dataModel, result, model, session, request, response);
				break;
			case "resetpwdinit":
				rtnUrl = doResetPwdInit();
				break;
			case "resetpwd":
				rtnUrl = doResetPwd(dataModel, result, model, session, request, response);
				break;
			case "uploadHeadPhoto":
				//uploadHeadPhoto(headPhotoFile,	request, response);
				uploadHeadPhoto(request, response);
				//boolean printResult = printOut(response, rtnUrl);
				return null;

		}
		
		return rtnUrl;
	}	
	
	private String doSearch(UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel = userService.doSearch(request, dataModel, userInfo);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/user/usermain";
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
	
	private String doUpdate(UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		try {
			dataModel.setUpdatedRecordCount(0);
			dataModel.setDutyList(DicUtil.getGroupValue(DicUtil.DUTIES));
			dataModel.setSexList(DicUtil.getGroupValue(DicUtil.SEX));
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);

			int preCheckResult = userService.preCheck(request, "update", userInfo.getUserId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				userService.doUpdate(request, dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setOperType("update");
				dataModel.setMessage("更新成功");
				dataModel.setPhotoChangeFlg("");
				dataModel.getuserData().setLoginpwd("");
				break;
			case 1:
				dataModel.setMessage("登录id已存在");
				break;
			case 2:
				dataModel.setMessage("登录id不存在");
				break;
			case 3:
				dataModel.setMessage("登录id已存在");
				break;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("更新失败");
		}
		
		
		model.addAttribute("DisplayData", dataModel);
		return "/user/useredit";
	}	

	private String doAdd(UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			dataModel.setDutyList(DicUtil.getGroupValue(DicUtil.DUTIES));
			dataModel.setSexList(DicUtil.getGroupValue(DicUtil.SEX));
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			int preCheckResult = userService.preCheck(request, "add", userInfo.getUserId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				userService.doAdd(request, dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setOperType("add");
				dataModel.setMessage("增加成功");
				dataModel.setPhotoChangeFlg("");
				dataModel.getuserData().setUserid("");
				dataModel.getuserData().setLoginpwd("");
				break;
			case 1:
				dataModel.setMessage("登录id已存在");
				break;
			case 2:
				dataModel.setMessage("登录id不存在");
				break;
			case 3:
				dataModel.setMessage("登录id已存在");
				break;
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("增加失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/user/useredit";
	}	
	
	private String doDelete(UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			userService.doDelete(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("删除成功");
			dataModel = userService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/user/usermain";
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
	
	private String doLock(UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			userService.doLock(dataModel, userInfo, 1);
			dataModel.setMessage("用户锁定成功");
			dataModel = userService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("用户锁定失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/user/usermain";
	}
	
	private String doUnLock(UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			userService.doLock(dataModel, userInfo, 0);
			dataModel.setMessage("用户解锁成功");
			dataModel = userService.doSearch(request, dataModel, userInfo);			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("用户解锁失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/user/usermain";
	}

	private void uploadHeadPhoto(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	    MultipartFile headPhotoFile = multipartRequest.getFile("headPhotoFile");
	    
		String userId = request.getParameter("userData.userid");
		JSONObject jsonObj = userService.uploadPhoto(request, userId, headPhotoFile);
		
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
	
	private String doResetPwd(UserModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			int resetResult = userService.doResetPwd(request, dataModel, userInfo);
			if (resetResult == 0) {
				dataModel.setMessage("密码修改成功");
			} else {
				dataModel.setMessage("现在的密码输入不正确");
			}
			//dataModel = userService.doSearch(request, dataModel, userInfo);			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("密码修改失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/user/userresetpwd";
	}
}
