package com.ys.system.action.dic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.dic.DicTypeModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.dic.DicTypeService;
import com.ys.util.DicUtil;


@Controller

public class DicTypeAction extends BaseAction {
	
	@Autowired
	DicTypeService dicTypeService;
	
	@RequestMapping("/dictype")
	public String execute(@ModelAttribute("dataModels")DicTypeModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
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
				rtnUrl = "/dic/dictypemain";
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
		}
		
		return rtnUrl;		

	}	
	
	public String doSearch(DicTypeModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel = dicTypeService.doSearch(request, dataModel, userInfo);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/dic/dictypemain";
	}

	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		DicTypeModel dataModel = new DicTypeModel();
		String operType = request.getParameter("operType");
		try {
			session.getAttribute(BusinessConstants.SESSION_USERINFO);
			if (operType.equals("update")) {
				dataModel = dicTypeService.getDetail(request);
				dataModel.setDicTypeId(dataModel.getdicTypeData().getDictypeid());
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
	
	public String doUpdate(DicTypeModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);

			dataModel.setDicSelectedFlagList(DicUtil.getGroupValue(DicUtil.DICSELECTEDFLAG));
			dataModel.setDicTypeLevelList(DicUtil.getGroupValue(DicUtil.DICTYPELEVEL));
			
			int preCheckResult = dicTypeService.preCheck(request, "update");
			switch(preCheckResult) {
			case 0:
				dicTypeService.doUpdate(request, dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setOperType("update");
				dataModel.setMessage("更新成功");
				break;
			case 1:
				dataModel.setMessage("代码类已存在");
				break;				
			case 2:
				dataModel.setMessage("代码类已存在");
				break;
			}			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("更新失败");
		}
		
		model.addAttribute("DisplayData", dataModel);
		return "/dic/dictypeedit";
	}	

	public String doAdd(DicTypeModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			
			dataModel.setDicSelectedFlagList(DicUtil.getGroupValue(DicUtil.DICSELECTEDFLAG));
			dataModel.setDicTypeLevelList(DicUtil.getGroupValue(DicUtil.DICTYPELEVEL));
			
			int preCheckResult = dicTypeService.preCheck(request, "add");
			switch(preCheckResult) {
			case 0:
				dicTypeService.doAdd(request, dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setOperType("add");
				dataModel.setMessage("增加成功");
				break;
			case 1:
				dataModel.setMessage("代码类已存在");
				break;				
			case 2:
				dataModel.setMessage("代码类已存在");
				break;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("增加失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/dic/dictypeedit";
	}	
	
	public String doDelete(DicTypeModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dicTypeService.doDelete(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("删除成功");
			dataModel = dicTypeService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/dic/dictypemain";
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
