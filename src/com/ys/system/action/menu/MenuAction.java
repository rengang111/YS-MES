package com.ys.system.action.menu;

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
	public String execute(@RequestBody String para, @ModelAttribute("dataModels")MenuModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
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
				rtnUrl = "/menu/menumanageframe";
				break;
			case "initframe":
				rtnUrl = "/menu/menumain";
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
			case "checkMenuId":
				rtnUrl = checkMenuId(para, session, request, response);
			printOut(response, rtnUrl);
				return null;
		}
		
		return rtnUrl;
	}	
	
	public String doSearch(MenuModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			dataModel = menuService.doSearch(request, dataModel);
			if (dataModel.getViewData().size() == 0) {
				dataModel.setMessage("无符合条件的数据");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("检索失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/menu/menumain";
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
	
	public String doUpdate(MenuModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel.setMenuTypeList(DicUtil.getGroupValue(DicUtil.MENUTYPE));
			menuService.doUpdate(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("update");
			dataModel.setMessage("更新成功");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("更新失败");
		}
		
		model.addAttribute("DisplayData", dataModel);
		return "/menu/menuedit";
	}	

	public String doAdd(MenuModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataModel.setMenuTypeList(DicUtil.getGroupValue(DicUtil.MENUTYPE));
			menuService.doAdd(dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("add");
			dataModel.setMessage("增加成功");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("增加失败");
		}
		model.addAttribute("DisplayData", dataModel);
		
		return "/menu/menuedit";
	}	
	
	public String doDelete(MenuModel dataModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		
		try {
			dataModel.setUpdatedRecordCount(0);
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			menuService.doDelete(request, dataModel, userInfo);
			dataModel.setUpdatedRecordCount(1);
			dataModel.setOperType("delete");
			dataModel.setMessage("删除成功");
			dataModel = menuService.doSearch(request, dataModel);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataModel.setMessage("删除失败");
		}
		model.addAttribute("DisplayData", dataModel);
		return "/menu/menumain";
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
