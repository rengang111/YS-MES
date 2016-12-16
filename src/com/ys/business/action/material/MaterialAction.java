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
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.system.action.common.BaseAction;
import com.ys.business.action.model.material.MaterialModel;
import com.ys.system.common.BusinessConstants;
import com.ys.business.service.material.MaterialService;
import com.ys.system.action.model.login.UserInfo;

@Controller
@RequestMapping("/business")
public class MaterialAction extends BaseAction {
	
	@Autowired MaterialService materialService;
	
	MaterialModel MaterialModel= new MaterialModel();

	UserInfo userInfo = new UserInfo();
	
	@RequestMapping(value="/material")
	public String init(
			@RequestBody String data, 
			@ModelAttribute("material") MaterialModel reqModel, 
			BindingResult result,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		
		String type = request.getParameter("methodtype");
		userInfo = (UserInfo)session.getAttribute(BusinessConstants.SESSION_USERINFO);
		
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
				rtnUrl = "/business/material/materialmain";
				break;
			case "search":
				dataMap = doSearch(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "supplierPriceView":
				dataMap = doSupplierPriceView(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "supplierPriceHistory":
				dataMap = doSupplierPriceHistory(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "supplierPriceHistoryInit":
				doSupplierPriceHistoryInit(reqModel,model, request);
				rtnUrl = "/business/material/matbidhistory";
				break;
			case "create":
				doCreate(model);
				rtnUrl = "/business/material/materialadd";
				break;
			case "detailView":
				doDetailView(reqModel,model, request);
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/material/materialview";
				break;
			case "edit":
				doEdit(reqModel,model, request);
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/material/materialedit";
				break;
			case "insertReturn":
				doInsert(reqModel,model, request);
				rtnUrl = "/business/material/materialview";
				break;
			case "addSupplier":
				doAddSupplier(reqModel,model, request);
				rtnUrl = "/business/material/matbidadd";
				//printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
			case "supplierSearch"://供应商查询
				dataMap = doSupplierSearch(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "insertPrice":
				doInsertPrice(reqModel,model, request);
				//rtnUrl = "/business/material/materialview";
				printOutJsonObj(response, reqModel.getEndInfoMap());
				break;
			case "editPrice":
				doEditPrice(reqModel,model, request);
				printOutJsonObj(response, reqModel.getEndInfoMap());
				rtnUrl = "/business/material/matbidedit";
				break;
			case "insertRefresh":
				doInsert(reqModel,model, request);
				printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
			case "update":
				doUpdate(reqModel,model,request);
				rtnUrl = "/business/material/materialview";
				break;
			case "delete":
				MaterialModel = doDelete(data, session, request, response);
				printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
			case "categorySearch"://物料分类查询
				dataMap = doCategorySearch(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "mategoryMAXId"://物料最新编号查询
				dataMap = doMaterialMAXId(data, request);
				printOutJsonObj(response, dataMap);
				break;
		}
		
		return rtnUrl;		
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSearch(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = materialService.search(request, data);
			
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

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSupplierPriceView(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = materialService.supplierPriceView(request, data);
			
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
	
	public void doSupplierPriceHistoryInit(
			MaterialModel reqModel,
			Model model,
			HttpServletRequest request){	
		
		B_PriceSupplierData price = new B_PriceSupplierData();

		String supplierId = request.getParameter("supplierId");
		price.setSupplierid(supplierId);
		MaterialModel.setPrice(price); 
		model.addAttribute("material", MaterialModel);
		
	}


	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSupplierPriceHistory(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = materialService.supplierPriceHistory(request, data);
			
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

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doCategorySearch(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = materialService.categorySearch(request, data);
			
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
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doMaterialMAXId(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = materialService.getMaterialMAXId(request, data);
			
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

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSupplierSearch(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = materialService.getSupplierList(request, data);
			
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
	public void doCreate(Model model){
		
		MaterialModel = materialService.createMaterial();
		model.addAttribute("DisplayData", MaterialModel);
		
	}
	
	public void doInsert(MaterialModel reqModel,
			Model model,
			HttpServletRequest request) throws Exception {

		model = materialService.insert(reqModel,model, request,userInfo);
		
	}		

	public void doInsertPrice(MaterialModel reqModel,
			Model model,
			HttpServletRequest request) throws Exception {

		model = materialService.insertPrice(reqModel,model, request,userInfo);
		
	}	

	
	public void doUpdate(MaterialModel reqModel,Model model,
			HttpServletRequest request) throws Exception {
				
		model = materialService.update(reqModel,model, request,userInfo);
		
		
	}	
	
	public MaterialModel doDelete(@RequestBody String data, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		MaterialModel = materialService.doDelete(data, userInfo);

		return MaterialModel;
	}

	public void doAddSupplier(
			MaterialModel reqModel,
			Model model,
			HttpServletRequest request) {

		model = materialService.SelectSupplier(reqModel,request,model);

	}	
	
	public void doDetailView(MaterialModel reqModel,Model model, HttpServletRequest request){

		String recordId = request.getParameter("recordId");
		String parentId = request.getParameter("parentId");
		model = materialService.view(request,model,recordId,parentId);

	}	
	public void doEdit(MaterialModel reqModel,Model model, HttpServletRequest request){

		String recordId = request.getParameter("recordId");
		String parentId = request.getParameter("parentId");
		model = materialService.view(request,model,recordId,parentId);

	}	
	
	public void doEditPrice(MaterialModel reqModel,Model model, HttpServletRequest request){

		String recordId = request.getParameter("recordId");
		String parentId = request.getParameter("parentId");
		model = materialService.viewPrice(request,model,recordId,parentId);

	}	
	
}
