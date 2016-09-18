package com.ys.system.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.system.action.model.common.SelectDicPopupInfo;
import com.ys.system.action.model.common.SelectDicTypePopupInfo;
import com.ys.system.action.model.common.SelectMenuPopupInfo;
import com.ys.system.action.model.common.SelectRolePopupInfo;
import com.ys.system.action.model.common.SelectUnitPopupInfo;
import com.ys.system.action.model.common.SelectUserPopupInfo;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.SelectDicPopupService;
import com.ys.system.service.common.SelectDicTypePopupService;
import com.ys.system.service.common.SelectRolePopupService;
import com.ys.system.service.common.SelectUserPopupService;

/**
 * @author 
 *
 */
@Controller
@RequestMapping("/common")
public class SelectPopupAction {
	@Autowired
	SelectDicPopupService selectDicPopupService;
	
	@Autowired
	SelectRolePopupService SelectRolePopupService;

	@Autowired
	SelectUserPopupService selectUserPopupService;
	
	@Autowired
	SelectDicTypePopupService selectDicTypePopupService;
	
	@RequestMapping(value="/selectDicPopActionInit")
	public String doDicInit(@ModelAttribute("dataModel") SelectDicPopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
				
		try {
			String dicTypeId = request.getParameter("dicTypeID");
			if (dicTypeId != null) {
				dataModel.setDicTypeID(dicTypeId);
			}
			model.addAttribute("DisplayData", dataModel);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "/common/selectdicpopup";
	}
	
	@ResponseBody
	@RequestMapping(value="/selectDicPopActionSearch")
	public String doDicSearch(@ModelAttribute("dataModel") SelectDicPopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String jsonData = "";

		dataModel.setDicCodeName(request.getParameter("dicCodeName"));
		dataModel.setDicParentCodeName(request.getParameter("dicParentCodeName"));
		dataModel.setDicTypeID(request.getParameter("dicTypeID"));
		try {
			jsonData = selectDicPopupService.doSearch(request, dataModel);

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return jsonData;
	}

	@RequestMapping(value="/selectDicTypePopActionInit")
	public String doDicTypeInit(@ModelAttribute("dataModel") SelectDicTypePopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
				
		try {
			model.addAttribute("DisplayData", dataModel);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "/common/selectdictypepopup";
	}
	
	@ResponseBody
	@RequestMapping(value="/selectDicTypePopActionSearch")
	public String doDicTypeSearch(@ModelAttribute("dataModel") SelectDicTypePopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String jsonData = "";

		try {
			jsonData = selectDicTypePopupService.doSearch(request);

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return jsonData;
	}	
	
	@RequestMapping(value="/selectRolePopActionInit")
	public String doRoleInit(@ModelAttribute("dataModel") SelectRolePopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		final String menuId = "";
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		userInfo.getUserId();
		String userDeptId = userInfo.getUnitId();
		
		try {
			dataModel = SelectRolePopupService.doInit(dataModel, request, menuId, userDeptId);
			model.addAttribute("DisplayData", dataModel);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "/common/selectrolepopup";
	}
	
	@RequestMapping(value="/selectRolePopActionSearch")
	public String doRoleSearch(@ModelAttribute("dataModel") SelectRolePopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		final String menuId = "";
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		userInfo.getUserId();
		String userDeptId = userInfo.getUnitId();
		
		try {
			dataModel = SelectRolePopupService.doSearch(dataModel, request, menuId, userDeptId);
			model.addAttribute("DisplayData", dataModel);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "/common/selectrolepopup";
	}	
	
	@RequestMapping(value="/selectUserPopActionInit")
	public String doUserInit(@ModelAttribute("dataModel") SelectUserPopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		final String menuId = "";
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			dataModel = selectUserPopupService.doInit(dataModel, request, menuId, userInfo.getUnitId());
			model.addAttribute("DisplayData", dataModel);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "/common/selectuserpopup";
	}
	
	@RequestMapping(value="/selectUserPopActionSearch")
	public String doUserSearch(@ModelAttribute("dataModel") SelectUserPopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		final String menuId = "";
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
		try {
			dataModel = selectUserPopupService.doSearch(dataModel, request, menuId, userInfo.getUnitId());
			model.addAttribute("DisplayData", dataModel);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "/common/selectuserpopup";
	}
	
	@RequestMapping(value="/selectUnitPopActionSearch")
	public String doUnitSearch(@ModelAttribute("dataModel") SelectUnitPopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		dataModel.setMenuId(request.getParameter("menuId"));
		model.addAttribute("DisplayData", dataModel);
		
		return "/common/selectunitpopup";
	}
	
	@RequestMapping(value="/selectMenuPopActionInit")
	public String doMenuSearch(@ModelAttribute("dataModel") SelectMenuPopupInfo dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		dataModel.setMenuId(request.getParameter("menuId"));
		model.addAttribute("DisplayData", dataModel);
		
		return "/common/selectmenupopup";
	}	
}
