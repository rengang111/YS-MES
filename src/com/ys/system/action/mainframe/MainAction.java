package com.ys.system.action.mainframe;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.system.action.common.BaseAction;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.menu.MenuModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.interceptor.CommonInterceptor;
import com.ys.system.service.mainframe.MainFrameService;


@Controller
public class MainAction extends BaseAction {
	
	@Autowired
	MainFrameService mainFrameService;
	
	@RequestMapping("/")
	public String execute(@RequestBody String para, @ModelAttribute("dataModels")MenuModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		return "/login/login";
	}
}
