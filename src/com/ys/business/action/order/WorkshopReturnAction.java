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
import com.ys.business.action.model.order.PurchaseOrderModel;
import com.ys.business.action.model.order.WorkshopReturnModel;
import com.ys.business.service.order.PurchaseOrderService;
import com.ys.business.service.order.WorkshopReturnService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;

@Controller
@RequestMapping("/business")
public class WorkshopReturnAction extends BaseAction {
	
	@Autowired WorkshopReturnService service;
	@Autowired HttpServletRequest request;
	HttpSession session;
	
	UserInfo userInfo = new UserInfo();
	WorkshopReturnModel reqModel = new WorkshopReturnModel();
	Model model;
	
	@RequestMapping(value="/workshopReturn")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("attrForm") WorkshopReturnModel form, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		this.service = new WorkshopReturnService(model,request,session,form,userInfo);
		this.reqModel = form;
		this.model = model;
		this.session = session;
		
		String rtnUrl = null;
		HashMap<String, Object> dataMap = null;
		
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
			case "workshopRentunInit"://车间退货一览
				doInit(Constants.FORM_WORKSHOPRETURN);
				rtnUrl = "/business/purchase/workshopreturnmain";
				break;			
			case "searchWorkshopReturn"://车间退货:查询
				dataMap = doSearchForWorkshopReturn(data,Constants.FORM_WORKSHOPRETURN);
				printOutJsonObj(response, dataMap);
				break;			
			case "createWorkshopRentunInit"://车间退货:新建
				rtnUrl = createWorkshopRentunInit();
				//printOutJsonObj(response, dataMap);
				break;				
			case "createWorkshopRentun"://车间退货:新建保存
				createWorkshopRentun();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/purchase/workshopreturnview";
				break;				
			case "workshopRentunEdit"://车间退货:编辑
				workshopRentunEdit();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/purchase/workshopreturnedit";
				break;					
			case "workshopRentunUpdate"://车间退货:编辑保存
				updateWorkshopRentun();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/purchase/workshopreturnview";
				break;						
			case "workshopRentunDetailView"://车间退货:查看
				workshopRentunDetailView();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/purchase/workshopreturnview";
				break;					
			case "getWorkshopReturnHistory"://车间退货:历史记录
				dataMap = getWorkshopReturnHistory();
				printOutJsonObj(response, dataMap);
				break;	
			case "getPurchasePlanDetail":
				dataMap = getPurchasePlanDetail();
				printOutJsonObj(response, dataMap);
				rtnUrl = null;
				break;		
		}
		
		return rtnUrl;		
	}
	
	public void doInit(String formId){	
		/*	
		String keyBackup = request.getParameter("keyBackup");
		//没有物料编号,说明是初期显示,清空保存的查询条件
		if(keyBackup == null || ("").equals(keyBackup)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);
		}else{
			model.addAttribute("keyBackup",keyBackup);
		}
		*/
	}
	
	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearchForWorkshopReturn(
			String data,String formId){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);
			
		}
		try {
			dataMap = service.getContractListForWorkshopReturn(data,formId);
			
			dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
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
	
	
	

	public void workshopRentunEdit() throws Exception{

		service.updateWorkshopRentunInit();
	}

	public void updateWorkshopRentun() throws Exception{

		service.updateWorkshopAndView();
	}
	
	public void workshopRentunDetailView() throws Exception{

		service.workshopRentunDetailView();
	}

	public HashMap<String, Object> getWorkshopReturnHistory() throws Exception{

		String YSId = request.getParameter("YSId");
		
		return service.getWorkshopReturnList(YSId,"");
	}
	
	public String createWorkshopRentunInit() throws Exception{

		String rtnUrl = "/business/purchase/workshopreturnadd";
		String flag = service.createWorkshopRentunInit();
		if("查看".equals(flag))
			rtnUrl = "/business/purchase/workshopreturnview";
		return rtnUrl;
	}
	
	
	public void createWorkshopRentun() throws Exception{

		service.createWorkshopRentun();
	}
	
	
	public HashMap<String, Object> getPurchasePlanDetail() throws Exception{
		
		return service.getPurchasePlanDetail();

	}
	

}
