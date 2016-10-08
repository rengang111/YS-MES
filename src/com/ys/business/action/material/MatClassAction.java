package com.ys.business.action.material;

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
import com.ys.business.action.model.material.MatClassModel;
import com.ys.business.service.material.MatClassService;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;


@Controller
@RequestMapping("/business")
public class MatClassAction extends BaseAction {
	
	@Autowired
	MatClassService materialService;
	
	@RequestMapping("/material")
	public String doInit(@RequestBody String para, @ModelAttribute("dataModels")MatClassModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
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
				rtnUrl = "/business/material/matclassframe";
				break;
			case "initframe":
				rtnUrl = "/business/material/matclassmain";
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
	
	public String doSearch(@ModelAttribute("dataModels")MatClassModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel = materialService.doSearch(request, dataModel, userInfo);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/business/material/matclassmain";
	}

	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MatClassModel dataModel = new MatClassModel();
		String operType = request.getParameter("operType");
		try {
			if (operType.equals("update")) {
				dataModel = materialService.getDetail(request);
				dataModel.setParentUnitId(dataModel.getunitData().getParentid());
			}
			if (operType.equals("addsub")) {
				dataModel = materialService.getParentDeptName(request);
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
		
		return "/business/material/matclassedit";
	}	
	
	public String doUpdate(MatClassModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel.setUnitPropertyList(DicUtil.getGroupValue(DicUtil.UNITPROPERTY));
			dataModel.setUnitTypeList(DicUtil.getGroupValue(DicUtil.UNITTYPE));
			
			int preCheckResult = materialService.preCheck(request, "update", dataModel.getParentUnitId(), userInfo.getUnitId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				materialService.doUpdate(request, dataModel, userInfo);
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
		return "/business/material/matclassedit";
	}	

	public String doAdd(MatClassModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel.setUnitPropertyList(DicUtil.getGroupValue(DicUtil.UNITPROPERTY));
			dataModel.setUnitTypeList(DicUtil.getGroupValue(DicUtil.UNITTYPE));
			
			int preCheckResult = materialService.preCheck(request, "add", dataModel.getParentUnitId(), userInfo.getUnitId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				materialService.doAdd(request, dataModel, userInfo);
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
		
		return "/business/material/matclassedit";
	}	
	
	public String doDelete(MatClassModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			materialService.doDelete(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("删除成功");
			DicUtil.emptyBuffer(true);
			dataModel = materialService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/business/material/matclassmain";
	}
	
	public String doDetail(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MatClassModel dataModel = new MatClassModel();
		
		try {
			dataModel = materialService.getDetail(request);
			dataModel.setIsOnlyView("1");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("详细信息取得失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/business/material/matclassedit";
	}	
}
