package com.ys.business.action.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.ys.business.db.data.B_MaterialCategoryData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.system.action.common.BaseAction;
import com.ys.business.action.model.order.MaterialModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.business.service.order.MaterialService;
import com.ys.system.action.model.login.UserInfo;

@Controller
@RequestMapping("/business")
public class MaterialAction extends BaseAction {
		
	MaterialModel MaterialModel= new MaterialModel();

	UserInfo userInfo = new UserInfo();
	@Autowired MaterialService materialService;
	@Autowired HttpServletRequest request;
	HttpSession session;
	
	Model model;
	
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
	
		materialService = new MaterialService(model,request,session,reqModel,userInfo);
		MaterialModel = reqModel;
		this.session = session;
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
				rtnUrl = doInit(Constants.FORM_MATERIAL,session);
				break;
			case "search":
				dataMap = doSearch(data, Constants.FORM_MATERIAL);
				printOutJsonObj(response, dataMap);
				break;
			case "supplierPriceView":
				dataMap = doSupplierPriceView(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "supplierPriceHistory":
				dataMap = doSupplierPriceHistory();
				printOutJsonObj(response, dataMap);
				break;
			case "supplierPriceHistoryInit":
				doSupplierPriceHistoryInit();
				rtnUrl = "/business/material/matbidhistory";
				break;
			case "create":
				doCreate(model);
				rtnUrl = "/business/material/materialadd";
				break;
			case "detailView":
				doDetailView();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/material/materialview";
				break;
			case "edit":
				doEdit();
				//printOutJsonObj(response, dataMap);
				rtnUrl = "/business/material/materialedit";
				break;
			case "insertReturn":
				rtnUrl = doInsert(reqModel,model, request);
				break;
			case "addSupplier":
				doAddSupplier();
				rtnUrl = "/business/material/matbidadd";
				break;
			case "supplierSearch"://供应商查询:过滤已有报价的供应商
				dataMap = doSupplierSearch(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "supplierSearch2"://供应商查询:全部
				dataMap = doSupplierSearch2(data, request);
				printOutJsonObj(response, dataMap);
				break;
			case "insertPrice":
				doInsertPrice(reqModel,model, request);
				//rtnUrl = "/business/material/materialview";
				printOutJsonObj(response, reqModel.getEndInfoMap());
				break;
			case "editPrice":
				doEditPrice();
				printOutJsonObj(response, reqModel.getEndInfoMap());
				rtnUrl = "/business/material/matbidedit";
				break;
			case "insertRefresh":
				doInsert(reqModel,model, request);
				printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
			case "update":
				rtnUrl = doUpdate();
				//rtnUrl = "/business/material/materialview";
				break;
			case "delete":
				MaterialModel = doDelete(data);
				printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
			case "deletePrice":
				MaterialModel = doDeletePrice(data);
				printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
			case "deletePriceHistory":
				MaterialModel = doDeletePriceHistory(data);
				printOutJsonObj(response, MaterialModel.getEndInfoMap());
				break;
			case "categorySearch"://单个物料分类查询
				dataMap = doCategorySearch(data);
				printOutJsonObj(response, dataMap);
				break;
			case "categorySearchMul"://多个物料分类查询
				List<B_MaterialCategoryData> dd = doCategorySearchMul(data);
				printOutJsonObj(response, dd.toArray());
				break;
			case "mategoryMAXId"://物料最新编号查询
				dataMap = doMaterialMAXId(data);
				printOutJsonObj(response, dataMap);
				break;				
			case "productInit"://成品
				doInit(Constants.FORM_PRODUCT,session);
				rtnUrl = "/business/material/productmain";
				break;
			case "searchProduct"://成品
				dataMap = dosearchProduct(session,data);
				printOutJsonObj(response, dataMap);
				break;	
			case "productView":
				rtnUrl = productView();
				break;	
			case "productSemiInit"://半成品
				doInit(Constants.FORM_PRODUCTSEMI,session);
				rtnUrl = "/business/material/productsemimain";
				break;			
			case "searchProductSemi"://半成品
				dataMap = dosearchProductSemi(session,data);
				printOutJsonObj(response, dataMap);
				break;	
			case "productSemiView":
				productSemiView();
				rtnUrl = "/business/material/productsemiview";
				break;
			case "searchPurchaseRoutine":
				dataMap = doSearch(data,Constants.FORM_PURCHASEROUTINE );
				printOutJsonObj(response, dataMap);
				break;
			case "materialCostView"://查看物料的核算成本
				materialCostView();
				rtnUrl = "/business/material/materialcostview";
				break;
			case "materialCostEdit"://编辑物料的核算成本
				materialCostEdit();
				rtnUrl = "/business/material/materialcostedit";
				break;
			case "insertMaterialCost"://保存物料的核算成本
				insertMaterialCost(data);
				printOutJsonObj(response, dataMap);
				break;
			case "getMaterialCostList"://取得物料的材料成本
				dataMap = getMaterialCostList();
				printOutJsonObj(response, dataMap);
				break;
			case "getProcessCostList"://取得物料的加工描述
				dataMap = getProcessCostList();
				printOutJsonObj(response, dataMap);
				break;
			case "materialPackSearchInit"://包装页面查询初始化
				doInit(Constants.FORM_MATERIALPACK,session);
				rtnUrl = "/business/material/materialpackmain";
				break;
			case "materialPackSearch"://包装页面查询
				dataMap = doSearchPack(data, Constants.FORM_MATERIALPACK);
				printOutJsonObj(response, dataMap);
				break;
			case "detailViewPack"://包装详情页面
				doDetailViewForPack();
				rtnUrl = "/business/material/materialpackview";
				break;
		
		}
		
		return rtnUrl;		
	}
	
	public String doInit(String formId,HttpSession session){	
			
		String keyBackup = request.getParameter("keyBackup");
		//
		String rtnUrl = "/business/material/materialmain";
		if(("pack").equals(keyBackup)){
			rtnUrl = "/business/material/materialpackmain";
		}
		
		return rtnUrl;
	}


	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearch(String data, 
			String formId){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			dataMap = materialService.search(data,formId);
			
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

	@SuppressWarnings({ "unchecked" })
	public HashMap<String, Object> doSearchPack(String data, 
			String formId){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(formId+Constants.FORM_KEYWORD1);
			session.removeAttribute(formId+Constants.FORM_KEYWORD2);
			
		}
		
		try {
			dataMap = materialService.searchPack(data,formId);
			
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
	
	public void doSupplierPriceHistoryInit(){	
		
		B_PriceSupplierData price = new B_PriceSupplierData();

		String supplierId = request.getParameter("supplierId");
		String materialId = request.getParameter("materialId");
		price.setSupplierid(supplierId);
		price.setMaterialid(materialId);
		MaterialModel.setPrice(price); 
		model.addAttribute("material", MaterialModel);
		
	}


	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSupplierPriceHistory(){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = 
				new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = materialService.supplierPriceHistory();
			
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
	public HashMap<String, Object> doCategorySearch(@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();

		String key = request.getParameter("key").toUpperCase();	
		
		try {
			dataMap = materialService.categorySearch(key);
			
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
	public List<B_MaterialCategoryData> doCategorySearchMul(@RequestBody String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		List<B_MaterialCategoryData> dd = null;
		String key = request.getParameter("key").toUpperCase();	
		String strWhere = "";
		
		if (key == null || ("").equals(key.trim())) {
			return null;
		} 
		
		String arry[] = key.split(",");

		boolean firstFlg = true;
		for(String str:arry){
			
			if(firstFlg){
				strWhere = "'"+str+"'";
				firstFlg = false;
			}else{
				strWhere = strWhere +","+"'"+str+"'";
			}			
		}

		
		try {
			dd = materialService.categorySearchMul(strWhere);
			
			dbData = (ArrayList<HashMap<String, String>>)dataMap.get("data");
			if (dbData.size() == 0) {
				dataMap.put(INFO, NODATAMSG);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dd;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doMaterialMAXId(@RequestBody String data){
		
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
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> doSupplierSearch2(@RequestBody String data, 
			HttpServletRequest request){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> dbData = new ArrayList<HashMap<String, String>>();
		
		try {
			dataMap = materialService.getSupplierList2(request, data);
			
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
		String keyBackup = request.getParameter("keyBackup");
		
		model.addAttribute("DisplayData", MaterialModel);
		model.addAttribute("keyBackup", keyBackup);
		
	}
	
	public String doInsert(MaterialModel reqModel,
			Model model,
			HttpServletRequest request) throws Exception {

		model = materialService.insert();

		String rtnUrl = "/business/material/materialview";
		String keyBackup = request.getParameter("keyBackup");
		if(("pack").equals(keyBackup))
			rtnUrl = "/business/material/materialpackview";
			
		return rtnUrl;
	}		

	public void doInsertPrice(MaterialModel reqModel,
			Model model,
			HttpServletRequest request) throws Exception {

		
		model = materialService.insertPrice(reqModel,model, request,userInfo);
		
	}	

	
	public String doUpdate() throws Exception {

		String rtnUrl = "/business/material/materialview";
		String keyBackup = this.request.getParameter("keyBackup");
		this.model.addAttribute("keyBackup", keyBackup);
		
		materialService.update(keyBackup);
		
		if(("pack").equals(keyBackup))
			rtnUrl = "/business/material/materialpackview";//返回到包装页面
		
		return rtnUrl;
	}	
	
	public MaterialModel doDelete(@RequestBody String data) throws Exception{
		
		MaterialModel = materialService.doDelete(data, userInfo);

		return MaterialModel;
	}
	
	public MaterialModel doDeletePrice(@RequestBody String data) throws Exception{
		
		MaterialModel = materialService.doDeletePrice(data, userInfo);

		return MaterialModel;
	}
	
	public MaterialModel doDeletePriceHistory(@RequestBody String data) throws Exception{
		
		MaterialModel = materialService.doDeletePriceHistory(data, userInfo);

		return MaterialModel;
	}

	public void doAddSupplier() {

		String materialid = request.getParameter("materialid");
		String supplierId = request.getParameter("supplierId");
		model.addAttribute("supplierId",supplierId);
		model = materialService.SelectSupplier(materialid);

	}	
	
	public HashMap<String, Object> dosearchProduct(HttpSession session,String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		//优先执行查询按钮事件,清空session中的查询条件
		String sessionFlag = request.getParameter("sessionFlag");
		if(("false").equals(sessionFlag)){
			session.removeAttribute(Constants.FORM_PRODUCT+Constants.FORM_KEYWORD1);
			session.removeAttribute(Constants.FORM_PRODUCT+Constants.FORM_KEYWORD2);

			
		}
		try {
			dataMap = materialService.getProductList(data);			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	@SuppressWarnings("deprecation")
	public HashMap<String, Object> dosearchProductSemi(HttpSession session,String data){
		
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		//优先执行查询按钮事件,清空session中的查询条件
				String keyBackup = request.getParameter("keyBackup");
				if(keyBackup != null && !("").equals(keyBackup)){
					session.removeAttribute(Constants.FORM_PRODUCTSEMI+Constants.FORM_KEYWORD1);
					session.removeAttribute(Constants.FORM_PRODUCTSEMI+Constants.FORM_KEYWORD2);

					
				}
		try {
			dataMap = materialService.getProductSemiList(data);			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dataMap.put(INFO, ERRMSG);
		}
		
		return dataMap;
	}
	
	public String productView(){		

		String rtnUrl = "/business/material/productview";
		try {
			String keyBackup = request.getParameter("keyBackup");
			
			if(("K").equals(keyBackup)){
				
				materialService.getProductSemiDeital();	
				rtnUrl = "/business/material/productsemiview";
				
			}else{
				materialService.getProductDeital();
				
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}	
		
		return rtnUrl;
	}
	

	public void productSemiView(){		
		
		try {
			materialService.getProductSemiDeital();		
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	public void doDetailView(){

		String recordId = request.getParameter("recordId");
		String parentId = request.getParameter("parentId");
		String materialId = request.getParameter("materialId");
		String keyBackup = this.request.getParameter("keyBackup");
		try {
			model = materialService.MaterailDetail(recordId,parentId,materialId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MaterialModel.setKeyBackup(recordId);
		model.addAttribute("DisplayData",MaterialModel);
		model.addAttribute("keyBackup", keyBackup);
		
		//图片路径等信息:文件夹名称 + 物料编号 + Class名
		String albumInfo = "material" +"," + materialId +","  + MaterialModel.className;
		model.addAttribute("albumInfo",albumInfo);

	}
	
	public void doDetailViewForPack(){

		String recordId = request.getParameter("recordId");
		String parentId = request.getParameter("parentId");
		String materialId = request.getParameter("materialId");
		String keyBackup = this.request.getParameter("keyBackup");
		try {
			model = materialService.MaterailDetailForPack(recordId,parentId,materialId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MaterialModel.setKeyBackup(recordId);
		model.addAttribute("DisplayData",MaterialModel);
		model.addAttribute("keyBackup", keyBackup);
		
		//图片路径等信息:文件夹名称 + 物料编号 + Class名
		String albumInfo = "material" +"," + materialId +","  + MaterialModel.className;
		model.addAttribute("albumInfo",albumInfo);

	}
	
	public void doEdit(){

		String recordId = request.getParameter("recordId");
		String parentId = request.getParameter("parentId");
		String materialId = request.getParameter("materialId");
		String keyBackup = this.request.getParameter("keyBackup");
		
		try {
			model = materialService.MaterailDetail(recordId,parentId,materialId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.model.addAttribute("keyBackup", keyBackup);

	}	
	
	public void doEditPrice(){

		String recordId = request.getParameter("recordId");
		String materialId = request.getParameter("materialId");
		model = materialService.editPrice(recordId,materialId);

	}
	
	public void materialCostView() throws Exception{

		materialService.materialCostView();

	}	
	
	public void materialCostEdit() throws Exception{

		materialService.materialCostView();

	}	
	
	public void insertMaterialCost(String data) throws Exception
	{
	  this.materialService.insertMaterialCost(data);
	}
	
	public HashMap<String, Object> getMaterialCostList() throws Exception{
						
		return materialService.getMaterialCostList();
	}
	
	public HashMap<String, Object> getProcessCostList() throws Exception{
		
		return materialService.getProcessCostList();
	}
	
	
}
