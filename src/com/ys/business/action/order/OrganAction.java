package com.ys.business.action.order;

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
import com.ys.system.action.common.BaseAction;
import com.ys.business.action.model.order.OrganModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.order.OrganService;
import com.ys.system.action.model.login.UserInfo;

@Controller
@RequestMapping("/business")
public class OrganAction extends BaseAction {
	
	@Autowired OrganService organService;
	
	OrganModel organModel= new OrganModel();
	
	@RequestMapping(value="/organ")
	public String init(@RequestBody String data, @ModelAttribute("organ") OrganModel TModel, BindingResult result, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");
		
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		organModel = null;
		
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
				rtnUrl = "/business/organ/organmain";
				break;
			case "search":
				dataMap = doSearch(data, session, request, response);
				printOutJsonObj(response, dataMap);
				break;
			case "create":
				doCreate(model, session, request, response);
				rtnUrl = "/business/organ/organedit";
				break;
			case "edit":
				rtnUrl = doEdit(model, session, request, response);
				break;
			case "insert":
				organModel = doInsert(TModel,data, session, request);
				printOutJsonObj(response, organModel.getEndInfoMap());
				break;
			case "update":
				organModel = doUpdate(data, session, request);
				printOutJsonObj(response, organModel.getEndInfoMap());
				break;
			case "delete":
				organModel = doDelete(data, session, request, response);
				printOutJsonObj(response, organModel.getEndInfoMap());
				break;
			case "deleteContact":
				//viewModel = doDelete(data, session, request, response);
				printOutJsonObj(response, organModel.getEndInfoMap());
				break;
			/*case "optionChange":
				viewModel = doOptionChange(data);
				if (viewModel.getUnsureList() != null) {
					printOutJsonObj(response, viewModel.getUnsureList());
				} else {
					printOutJsonObj(response, viewModel.getEndInfoMap());
				}
				break;*/
		}
		
		return rtnUrl;		
	}
	
	public HashMap<String, Object> doSearch(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			dataMap = organService.search(request, data, userInfo);
			
			@SuppressWarnings("unchecked")
			ArrayList<HashMap<String, String>> dbData = 
					(ArrayList<HashMap<String, String>>)dataMap.get("data");
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
	

	public void doCreate(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		organModel = organService.createOrgan(request);
		model.addAttribute("DisplayData", organModel);
		
	}
	
	public OrganModel doInsert(OrganModel model,String data, HttpSession session, HttpServletRequest request){

		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
			
		organModel = organService.insert(request, data, userInfo);
		
		return organModel;
	}		
	
	public OrganModel doUpdate(String data, HttpSession session, HttpServletRequest request){
				
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		organModel = organService.update(request, data, userInfo);
		
		return organModel;
	}	
	
	public OrganModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		UserInfo userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		organModel = organService.doDelete(data, userInfo);

		return organModel;
	}

	
	public String doEdit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		String key = request.getParameter("key");
		try {
			organModel = organService.getOrganBaseInfo(key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			organModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", organModel);
		
		return "/business/organ/organedit";
	}	

}
