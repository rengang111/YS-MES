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
@RequestMapping("/mainframe")
public class MainFrameAction extends BaseAction {
	
	@Autowired
	MainFrameService mainFrameService;
	
	@RequestMapping("/welcome")
	public String execute(@RequestBody String para, @ModelAttribute("dataModels")MenuModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		return "/login/login";
	}
	
	@ResponseBody
	@RequestMapping(value="/initmenu", produces = "text/html;charset=UTF-8" )
	public Object doInitMenu(HttpSession session, HttpServletRequest request) {
		final String menuId = "001";
		String json = "";

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		String userId = userInfo.getUserId();
		String userType = userInfo.getUserType();
		boolean isMenuManage = false;
		if (request.getParameter("isMenuManage") != null) {
			isMenuManage = true;
		}
		json = mainFrameService.doInitMenu(request, userId, userType, menuId, isMenuManage);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value="/launchMenu", produces = "text/html;charset=UTF-8")
    public Object doLaunchMenu(@RequestBody String idJson, HttpServletRequest request, HttpSession session) {
		String json = "";

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		String userId = userInfo.getUserId();
		String userType = userInfo.getUserType();
		
		boolean isMenuManage = false;
		if (request.getParameter("isMenuManage") != null) {
			isMenuManage = true;
		}		
		json = mainFrameService.doLaunchMenu(request, userId, userType, idJson, isMenuManage);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value="/allMenu", produces = "text/html;charset=UTF-8")
	public Object doGetAllMenu(HttpServletRequest request, HttpSession session) {
		String json = "";
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);

		json = mainFrameService.doGetAllMenu(request, userInfo);
		

		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/initDept", produces = "text/html;charset=UTF-8")
	public Object doInitDept(@RequestParam("menuId") String menuId, HttpServletRequest request, HttpSession session) {
		String json = "";
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		String userId = userInfo.getUserId();
		String userType = userInfo.getUserType();
		String unitId = userInfo.getUnitId();
		
		json = mainFrameService.doInitDept(request, userId, menuId, unitId, userType);
		
		return json;
	}		
	
	@ResponseBody
	@RequestMapping(value="/launchDept", produces = "text/html;charset=UTF-8")
    public Object doLaunchDept(@RequestBody String idJson, HttpServletRequest request, HttpSession session) {
		String json = "";

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		String userId = userInfo.getUserId();
		String userType = userInfo.getUserType();
		
		json = mainFrameService.doLaunchDept(request, userId, userType, idJson);
		
		return json;
	}	
	
	@ResponseBody
	@RequestMapping(value="/initMaterial", produces = "text/html;charset=UTF-8")
	public Object doInitMaterial(@RequestParam("menuId") String menuId, HttpServletRequest request, HttpSession session) {
		String json = "";
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		String userId = userInfo.getUserId();
		String userType = userInfo.getUserType();
		String unitId = userInfo.getUnitId();
		
		json = mainFrameService.doInitMaterial(request, userId, menuId, unitId, userType);
		
		return json;
	}		
	
	@ResponseBody
	@RequestMapping(value="/launchMaterial", produces = "text/html;charset=UTF-8")
    public Object doLaunchMaterial(@RequestBody String idJson, HttpServletRequest request, HttpSession session) {
		String json = "";

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		String userId = userInfo.getUserId();
		String userType = userInfo.getUserType();
		
		json = mainFrameService.doLaunchMaterial(request, userId, userType, idJson);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value="/quit")
    public String doQuit(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		session.removeAttribute(BusinessConstants.SESSION_USERINFO);
		
		return "";

	}	

	@ResponseBody
	@RequestMapping(value="/resetbaseurl")
    public void doResetBaseurl(@RequestBody String data, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		Object tabUrl = null;
		String userUrl = "";
		
		tabUrl = session.getAttribute(BusinessConstants.FILESYSTEMBROWSERBASEFOLDER + data);
		if (tabUrl != null) {
			userUrl = String.valueOf(tabUrl);

			session.setAttribute(BusinessConstants.FILESYSTEMBROWSERBASEFOLDER, userUrl);
		}
		
		tabUrl = session.getAttribute(BusinessConstants.FILESYSTEMBROWSERUSERFOLDER + data);
		if (tabUrl != null) {
			userUrl = String.valueOf(tabUrl);

			session.setAttribute(BusinessConstants.FILESYSTEMBROWSERUSERFOLDER, userUrl);
		}
	}	
	
	@RequestMapping("/register")
	public String executeRegister(@RequestBody String para, @ModelAttribute("dataModels")MenuModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		return "/common/register";
	}
}
