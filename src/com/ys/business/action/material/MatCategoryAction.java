package com.ys.business.action.material;

import java.net.URLDecoder;

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
import com.ys.business.action.model.material.MatCategoryModel;
import com.ys.business.service.material.MatCategoryService;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;


@Controller
@RequestMapping("/business")
public class MatCategoryAction extends BaseAction {
	
	@Autowired
	MatCategoryService matClassService;
	
	@RequestMapping("/matcategory")
	public String doInit(
			@ModelAttribute("dataModels")MatCategoryModel dataModel, 
			BindingResult result,
			Model model,
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response){
		
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
			case "updateinit"://打开 新建/修改 页面
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "update"://保存修改内容
				rtnUrl = doUpdate(dataModel, result, model, session, request, response);
				break;
			case "add"://保存新增内容
				rtnUrl = doAdd(dataModel, result, model, session, request, response);
				break;
			case "delete":
				rtnUrl = doDelete(dataModel, result, model, session, request, response);
				break;
			case "detail"://查看
				rtnUrl = doDetail(model, session, request, response);
				break;
		}
		
		return rtnUrl;		
		
	}	
	
	public String doSearch(@ModelAttribute("dataModels")MatCategoryModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		//dataModel.setRecordPerPage(50);//设置每页的数据条数
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel = matClassService.doSearch(request, dataModel, userInfo);
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

	public String doUpdateInit(Model model, 
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response){

		MatCategoryModel dataModel = new MatCategoryModel();
		String operType = request.getParameter("operType");
		String parentId = request.getParameter("categoryId");
		String parentName = request.getParameter("parentName")+"";
		try {
			//parentName = new String(parentName.getBytes("ISO8859-1"), "UTF-8");
			parentName = URLDecoder.decode(parentName,"utf-8");
			if (operType.equals("update")) {
				dataModel = matClassService.getDetail(request);
				dataModel.setParentCategoryId(dataModel.getunitData().getParentid());
				dataModel.setParentCategoryName(parentName);
			}
			if (operType.equals("addsub")) {
				//dataModel = matClassService.getParentDeptName(request);
				dataModel.setParentCategoryId(parentId);
				dataModel.setParentCategoryName(parentName);
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
	
	public String doUpdate(MatCategoryModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			
			int preCheckResult = 0;
			//preCheckResult = matClassService.preCheck(request, "update", dataModel.getParentCategoryId(), userInfo.getUnitId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				matClassService.update(request, dataModel, userInfo);
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

	public String doAdd(MatCategoryModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			int preCheckResult = 0;
			//preCheckResult = matClassService.preCheck(request, "add", dataModel.getParentCategoryId(), userInfo.getUnitId(), userInfo.getUserType());
			switch(preCheckResult) {
			case 0:
				matClassService.insert(request, dataModel, userInfo);
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
	
	public String doDelete(MatCategoryModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			matClassService.delete(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("删除成功");
			DicUtil.emptyBuffer(true);
			dataModel = matClassService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/business/material/matclassmain";
	}
	
	public String doDetail(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MatCategoryModel dataModel = new MatCategoryModel();
		
		try {
			dataModel = matClassService.getDetail(request);
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
