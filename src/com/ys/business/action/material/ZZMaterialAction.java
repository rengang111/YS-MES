package com.ys.business.action.material;

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
import com.ys.business.action.model.material.ZZMaterialModel;
import com.ys.business.service.material.ZZMaterialService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;


@Controller
@RequestMapping("/business")
public class ZZMaterialAction extends BaseAction {
	
	@Autowired ZZMaterialService ZZService;
	@Autowired HttpServletRequest request;
	
	UserInfo userInfo = new UserInfo();
	ZZMaterialModel reqModel = new ZZMaterialModel();
	Model model;
	
	@RequestMapping(value="/zzmaterial")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("ZZMaterial") ZZMaterialModel bom, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(
				BusinessConstants.SESSION_USERINFO);
		
		ZZService = new ZZMaterialService(model,request,bom,userInfo);
		reqModel = bom;
		this.model = model;
		
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
			case "init":
				rtnUrl = "/business/material/zzmaterialmain";
				break;				
			case "search":
				dataMap = doSearch(data);
				printOutJsonObj(response, dataMap);
				break;							
			case "implement":
				rtnUrl = "/business/material/purchaseman";
				break;
			case "createB":
				doCreateB();
				rtnUrl = "/business/material/zzmaterialadd";
				break;
			case "createH":
				//doCreateH();
				rtnUrl = "/business/material/zzhmaterialadd";
				break;
			case "insert":
				doInsert();
				rtnUrl = "/business/material/zzmaterialview";
				break;							
			case "detailViewB":
				doShowDetail();
				rtnUrl = "/business/material/zzmaterialview";
				break;								
			case "detailViewH":
				doShowDetail();
				rtnUrl = "/business/material/zzhmaterialview";
				break;					
			case "purchasePlanView":
				//doShowBomDetail();
				rtnUrl = "/business/bom/bomselectlist";
				break;									
			case "bomCopyDetail":
				doShowDetail();
				rtnUrl = "/business/bom/bomcopyview";
				break;
			case "editB":
				doEdit();
				rtnUrl = "/business/material/zzmaterialedit";
				break;	
			case "editH":
				doEditH();
				rtnUrl = "/business/material/zzhmaterialadd";
				break;				
			case "updateB":
				doUpdate();
				rtnUrl = "/business/material/zzmaterialview";
				break;				
			case "updateH":
				doUpdate();
				rtnUrl = "/business/material/zzhmaterialview";
				break;
			case "delete":
				doDelete(data);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				break;
				
		}
		
		return rtnUrl;		
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearch(
			@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = ZZService.search(data);
			
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

	
	
	public void doCreateB() throws Exception{
		
		model = ZZService.createMaterial();
		
	}
	
	public void doCreateH() throws Exception{
		
		model = ZZService.createMaterial();
		
	}
	public void doInsert() throws Exception {

		model = ZZService.insertAndView();
		
	}		

	public void doEdit() throws Exception{

		ZZService.createMaterial();
		
		model = ZZService.getDetailView();
		
	}	
	
	
	public void doEditH() throws Exception{
		
		model = ZZService.getDetailView();
		
	}	
	
	public void doUpdate() throws Exception {
		
		model = ZZService.updateAndView();	
			
		
	}
	
	public void doDelete(String data) throws Exception {
		
		model = ZZService.delete(data);	
			
		
	}
	public void doShowDetail() throws Exception{
				
		model = ZZService.getDetailView();
			
	}

	
}
