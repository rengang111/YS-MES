package com.ys.system.action.operlog;

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
import com.ys.system.action.model.operlog.OperLogModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.operlog.OperLogService;


@Controller

public class OperLogAction extends BaseAction {
	
	@Autowired
	OperLogService operLogService;
	
	@RequestMapping("/operlog")
	public String doInit(@RequestBody String para, @ModelAttribute("dataModels")OperLogModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
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
			case "initframe":
				rtnUrl = "/operlog/operlogmain";
				break;
			case "search":
				rtnUrl = doSearch(dataModel, result,  model, session, request, response);
				break;
		}
		
		return rtnUrl;		
		
	}	
	
	public String doSearch(@ModelAttribute("dataModels")OperLogModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel = operLogService.doSearch(request, dataModel, userInfo);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/operlog/operlogmain";
	}


}
