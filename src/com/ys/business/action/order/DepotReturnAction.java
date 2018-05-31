package com.ys.business.action.order;

/**
 * 仓库退货
 */
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
import com.ys.business.action.model.order.DepotReturnModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;
import com.ys.util.basequery.common.Constants;
import com.ys.business.service.order.DepotReturnService;
import com.ys.business.service.order.FinanceReportService;
import com.ys.system.action.model.login.UserInfo;

@Controller
@RequestMapping("/business")
public class DepotReturnAction extends BaseAction {
	
	@Autowired DepotReturnService service;
	@Autowired HttpServletRequest request;

	UserInfo userInfo = new UserInfo();
	DepotReturnModel reqModel= new DepotReturnModel();
	Model model;
	HttpServletResponse response;
	HttpSession session;
	
	@RequestMapping(value="/depotReturn")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("formModel") DepotReturnModel dataModel, 
			BindingResult result,
			Model model,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String type = request.getParameter("methodtype");		
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
		this.userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new DepotReturnService(model,request,response,session,dataModel,userInfo);
		this.reqModel = dataModel;
		this.model = model;
		this.response = response;
		this.session = session;
		
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
				rtnUrl = "/business/inventory/depotreturnmain";
				break;
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "create":
				doCreate();
				rtnUrl = "/business/inventory/depotreturnadd";
				break;
			case "depotRentunEdit":
				doEdit();
				rtnUrl = "/business/inventory/depotreturnedit";
				break;
			case "materialRequisitionInsert":
				doInsert();
				rtnUrl = "/business/inventory/depotreturnview";
				break;
			case "depotRentunUpdate":
				doUpdate();
				rtnUrl = "/business/inventory/depotreturnview";
				break;
			case "depotRentunDelete":
				doDelete();
				rtnUrl = "/business/inventory/depotreturnmain";
				break;
			case "getContractDetail":
				dataMap = doGetContractDetail(data);
				printOutJsonObj(response, dataMap);
				break;
			case "showDepotRentunDeital":
				getDepotRentunDeital();
				rtnUrl = "/business/inventory/depotreturnview";
				break;
		}
		
		return rtnUrl;		
	}
	
	public HashMap<String, Object> doSearch(@RequestBody String data){
		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_DEPOTRETURN+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_DEPOTRETURN+Constants.FORM_KEYWORD2);			
		}
		
		try {
			dataMap = service.search(data,Constants.FORM_DEPOTRETURN);
			
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
	

	public void doCreate(){
		
		try {
			service.createDepotReturnInit();
			model.addAttribute("userName",userInfo.getUserName());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void doInsert(){

			
		try {
			service.insert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	
	public void doEdit(){

			
		try {
			service.edit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	public void doUpdate(){
				
		try {
			service.update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	public void doDelete() {
		
		try {
			service.doDelete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public String doEdit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		String key = request.getParameter("key");
		try {
			//reqModel = service.getOrganBaseInfo(key);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setMessage("发生错误，请联系系统管理员");
		}
		model.addAttribute("DisplayData", reqModel);
		
		return "/business/organ/organedit";
	}	
	
	public HashMap<String, Object> doGetContractDetail(String data) throws Exception{

		return service.getContractDetail(data);
	}

	public void getDepotRentunDeital() {

		 try {
			service.getDepotRentunDeital();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}