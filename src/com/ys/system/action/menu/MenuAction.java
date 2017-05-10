package com.ys.system.action.menu;

import java.util.ArrayList;
import java.util.HashMap;

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

import com.ys.business.action.model.mouldregister.MouldRegisterModel;
import com.ys.system.action.common.BaseAction;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.menu.MenuModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.data.S_MENUData;
import com.ys.system.service.menu.MenuService;
import com.ys.util.DicUtil;
import com.ys.util.JsonUtil;


@Controller
public class MenuAction extends BaseAction {
	
	@Autowired
	MenuService menuService;
	@RequestMapping("/menu")
	public String execute(@RequestBody String data, @ModelAttribute("dataModels")MenuModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("methodtype");
		String rtnUrl = "";
		HashMap<String, Object> dataMap = null;
		MenuModel viewModel = null;
		
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
				rtnUrl = "/menu/menumanageframe";
				break;
			case "initframe":
				rtnUrl = "/menu/menumain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				return null;
			case "updateinit":
				rtnUrl = doUpdateInit(model, session, request, response);
				break;
			case "update":
				viewModel = doUpdate(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "add":
				viewModel = doAdd(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "delete":
				viewModel = doDelete(data, session, request);
				printOutJsonObj(response, viewModel.getEndInfoMap());
				return null;
			case "detail":
				rtnUrl = doDetail(model, session, request, response);
				break;
			case "checkMenuId":
				rtnUrl = checkMenuId(data, session, request, response);
				printOut(response, rtnUrl);
				return null;
		}
		
		return rtnUrl;
	}	
	
	public HashMap<String, Object> doSearch(String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = menuService.doSearch(request, data);
			ArrayList<HashMap<String, String>> dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
			if (dbData.size() == 0) {
				dataMap.put(INFO, NODATAMSG);
			}

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}

	public String doUpdateInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		MenuModel dataModel = new MenuModel();
		String operType = request.getParameter("operType");
		try {
			if (operType.equals("update")) {
				dataModel = menuService.getDetail(request);
			}
			if (operType.equals("addsub")) {
				S_MENUData menuData = new S_MENUData();
				menuData.setMenuparentid(request.getParameter("menuId"));
				dataModel.setmenuData(menuData);
				dataModel.setMenuTypeList(DicUtil.getGroupValue(DicUtil.MENUTYPE));
			}
			if (operType.equals("add")) {
				dataModel.setMenuTypeList(DicUtil.getGroupValue(DicUtil.MENUTYPE));
			}
			dataModel.setOperType(operType);	
			dataModel.setIsOnlyView("");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/menu/menuedit";
	}	
	
	public MenuModel doUpdate(String data, HttpSession session, HttpServletRequest request){
		MenuModel dataModel = new MenuModel();
	
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = menuService.doUpdate(data, userInfo, false);
		dataModel.setOperType("update");

		return dataModel;
	}	

	public MenuModel doAdd(String data, HttpSession session, HttpServletRequest request){

		MenuModel dataModel = new MenuModel();
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = menuService.doUpdate(data, userInfo, true);
		dataModel.setOperType("add");

		return dataModel;
	}	
	
	public MenuModel doDelete(String data, HttpSession session, HttpServletRequest request){

		MenuModel dataModel = new MenuModel();

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		dataModel = menuService.doDelete(request, data, userInfo);
		dataModel.setOperType("delete");
		
		return dataModel;
	}
	
	public String doDetail(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		MenuModel dataModel = new MenuModel();
		
		try {
			dataModel = menuService.getDetail(request);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("详细信息取得失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/menu/menuedit";
	}
	
	public String checkMenuId(@RequestBody String para, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		BaseModel base = new BaseModel();

		try {
			String menuId = "";
			String operType = "";
			String paraArray[] = para.split("&");
			
			menuId = paraArray[0];
			operType = paraArray[1];

			boolean result = menuService.isRightMenuId(menuId, operType);
	    	base.setSuccess(result);
	    	if (!result) {
		    	base.setMessage("菜单id已存在");
	    	}
	    	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			base.setMessage("系统错误");
		}
		return JsonUtil.toJson(base);
	}	
}
