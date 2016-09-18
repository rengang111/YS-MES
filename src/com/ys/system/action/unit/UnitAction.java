package com.ys.system.action.unit;

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
import com.ys.util.basequery.common.Constants;
import com.ys.system.service.unit.UnitService;
import com.ys.util.DicUtil;


@Controller

public class UnitAction extends BaseAction {
	
	@Autowired
	UnitService unitService;
	
	@RequestMapping("/unit")
	public String doInit(@RequestBody String para, @ModelAttribute("dataModels")UnitModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
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
				rtnUrl = "/unit/unitmanageframe";
				break;
			case "initframe":
				rtnUrl = "/unit/unitmain";
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
	
	public String doSearch(@ModelAttribute("dataModels")UnitModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel = unitService.doSearch(request, dataModel, userInfo);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/unit/unitmain";
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
	
	public String doUpdate(UnitModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel.setUnitPropertyList(DicUtil.getGroupValue(DicUtil.UNITPROPERTY));
			dataModel.setUnitTypeList(DicUtil.getGroupValue(DicUtil.UNITTYPE));
			
			int preCheckResult = unitService.preCheck(request, "update", dataModel.getParentUnitId(), userInfo.getUnitId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				unitService.doUpdate(request, dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setOperType("update");
				dataModel.setMessage("更新成功");
				DicUtil.emptyBuffer(true);
				break;
			case 1:
				dataModel.setMessage("父单位不正确");
				break;				
			case 2:
				dataModel.setMessage("父单位不正确");
				break;
			case 3:
				dataModel.setMessage("单位名称已存在");
				break;
			}			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("更新失败");
		}
		
		model.addAttribute("DisplayData", dataModel);
		return "/unit/unitedit";
	}	

	public String doAdd(UnitModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel.setUnitPropertyList(DicUtil.getGroupValue(DicUtil.UNITPROPERTY));
			dataModel.setUnitTypeList(DicUtil.getGroupValue(DicUtil.UNITTYPE));
			
			int preCheckResult = unitService.preCheck(request, "add", dataModel.getParentUnitId(), userInfo.getUnitId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				unitService.doAdd(request, dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setOperType("add");
				dataModel.setMessage("增加成功");
				DicUtil.emptyBuffer(true);
				break;
			case 1:
				dataModel.setMessage("父单位不正确");
				break;				
			case 2:
				dataModel.setMessage("父单位不正确");
				break;
			case 3:
				dataModel.setMessage("单位名称已存在");
				break;
			case 4:
				dataModel.setMessage("单位不存在");
				break;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("增加失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/unit/unitedit";
	}	
	
	public String doDelete(UnitModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			unitService.doDelete(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("删除成功");
			DicUtil.emptyBuffer(true);
			dataModel = unitService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/unit/unitmain";
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
}
