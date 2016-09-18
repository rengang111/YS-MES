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
import com.ys.system.action.model.dic.DicModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.dic.DicService;
import com.ys.util.DicUtil;

@Controller

public class DicAction extends BaseAction {
	
	@Autowired
	DicService dicService;
	
	@RequestMapping("/diccode")
	public String execute(@ModelAttribute("dataModels")DicModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
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
				rtnUrl = "/dic/dicmain";
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
	
	public String doSearch(DicModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel = dicService.doSearch(request, dataModel, userInfo);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/dic/dicmain";
	}

	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		DicModel dataModel = new DicModel();
		String operType = request.getParameter("operType");
		try {

			if (operType.equals("update")) {
				dataModel = dicService.getDetail(request);
			}
			if (operType.equals("addsub")) {
				dataModel = dicService.getDetail(request);
				S_DICData data = new S_DICData();
				String dicCodeId = request.getParameter("dicCodeId");
				String dicTypeId = request.getParameter("dicTypeId");
				data.setDicprarentid(dicCodeId);
				dataModel.setDicParentName(DicUtil.getCodeValue(dicTypeId + dicCodeId));
				data.setDictypeid(dicTypeId);
				dataModel.setdicData(data);
			}
			if (operType.equals("addsubfromtype")) {
				S_DICData data = new S_DICData();
				data.setDictypeid(request.getParameter("dicTypeId"));
				dataModel.setDicTypeName(request.getParameter("dicTypeName"));
				dataModel.setdicData(data);
			}			
			
			if (operType.equals("add")) {
			}
			dataModel.setOperType(operType);	
			dataModel.setIsOnlyView("");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/dic/dicedit";
	}	
	
	public String doUpdate(DicModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			
			int preCheckResult = dicService.preCheck(request, "update");
			switch(preCheckResult) {
			case 0:
				dicService.doUpdate(request, dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setOperType("update");
				dataModel.setMessage("更新成功");
				DicUtil.emptyBuffer(false);
				break;
			case 1:
				dataModel.setMessage("代码已存在");
				break;				
			case 2:
				dataModel.setMessage("代码已存在");
				break;
			}			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("更新失败");
		}
		
		model.addAttribute("DisplayData", dataModel);
		return "/dic/dicedit";
	}	

	public String doAdd(DicModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
			int preCheckResult = dicService.preCheck(request, "add");
			switch(preCheckResult) {
			case 0:
				dataModel.setOperType("add");
				dicService.doAdd(request, dataModel, userInfo);
				dataModel.setUpdatedRecordCount(1);
				dataModel.setMessage("增加成功");
				DicUtil.emptyBuffer(false);
				break;
			case 1:
				dataModel.setMessage("代码已存在");
				break;				
			case 2:
				dataModel.setMessage("代码已存在");
				break;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("增加失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/dic/dicedit";
	}	
	
	public String doDelete(DicModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dicService.doDelete(request, dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("删除成功");
			DicUtil.emptyBuffer(false);
			dataModel = dicService.doSearch(request, dataModel, userInfo);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/dic/dicmain";
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
