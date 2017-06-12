package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.RequirementModel;
import com.ys.business.db.dao.B_BomDetailDao;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_MaterialRequirmentDao;
import com.ys.business.db.dao.B_OrderBomDao;
import com.ys.business.db.dao.B_OrderBomDetailDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_MaterialRequirmentData;
import com.ys.business.db.data.B_OrderBomData;
import com.ys.business.db.data.B_OrderBomDetailData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
import com.ys.business.db.data.B_QuotationData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.business.service.material.CommonService;

@Service
public class RequirementService extends CommonService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_MaterialRequirmentDao dao;
	private RequirementModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HttpSession session;

	public RequirementService(){
		
	}

	public RequirementService(Model model,
			HttpServletRequest request,
			HttpSession session,
			RequirementModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_MaterialRequirmentDao();
		this.model = model;
		this.reqModel = reqModel;
		this.dataModel = new BaseModel();
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		this.userDefinedSearchCase = new HashMap<String, String>();
		this.dataModel.setQueryFileName("/business/order/orderquerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}

	public void getZZOrderDetail( 
			String orderId) throws Exception {

		dataModel.setQueryName("getZZOrderDetail");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("orderId", orderId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
			
		model.addAttribute("order",dataModel.getYsViewData().get(0));		
		model.addAttribute("detail", dataModel.getYsViewData());
		
	}
	
	public HashMap<String, Object> getPurchasePlanList(String data) throws Exception {		
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		dataModel = new BaseModel();

		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		//String key1 = getJsonData(data, "keyword1").toUpperCase();
		//String key2 = getJsonData(data, "keyword2").toUpperCase();

		String[] keyArr = getSearchKey(Constants.FORM_PURCHASEPLAN,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getPurchasePlanList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	 
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();			
		}		
		
		modelMap.put("sEcho", sEcho);		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());	
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}

	public HashMap<String, Object> getOrderBomList(String data) throws Exception {		
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		dataModel = new BaseModel();

		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		//String key1 = getJsonData(data, "keyword1").toUpperCase();
		//String key2 = getJsonData(data, "keyword2").toUpperCase();

		String[] keyArr = getSearchKey(Constants.FORM_PURCHASEPLAN,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getOrderBomList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	 
		
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();			
		}		
		
		modelMap.put("sEcho", sEcho);		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());	
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	
	public void getOrderZZRawList( 
			String YSId) throws Exception {

		dataModel.setQueryName("getOrderZZRawList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
			
		model.addAttribute("order",dataModel.getYsViewData().get(0));		
		model.addAttribute("rawDetail", dataModel.getYsViewData());
		
	}
	
	public void getOrderZZRawSum( 
			String YSId) throws Exception {

		dataModel.setQueryName("getOrderZZRawSum");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
				
		model.addAttribute("rawGroup", dataModel.getYsViewData());
		
	}
	public void getOrderRawList( 
			String YSId) throws Exception {

		dataModel.setQueryName("getOrderRawList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
			
		model.addAttribute("order",dataModel.getYsViewData().get(0));		
		model.addAttribute("rawDetail", dataModel.getYsViewData());
		
	}
	
	public void getOrderRawSum( 
			String YSId) throws Exception {

		dataModel.setQueryName("getOrderRawSum");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
				
		model.addAttribute("rawGroup", dataModel.getYsViewData());
		
	}

	public void getOrderPartList( 
			String YSId) throws Exception {

		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("getOrderPartList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
	
		model.addAttribute("rawGroup", dataModel.getYsViewData());
		
	}

	public void getRequirementList( 
			String YSId) throws Exception {

		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("getRequirementList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
	
		model.addAttribute("rawGroup", dataModel.getYsViewData());
		
	}
	
	public void getOrderDetailByYSId( 
			String YSId) throws Exception {

		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getOrderDetailByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("order",dataModel.getYsViewData().get(0));
		
	}
	
	/*
	 * 
	 */
	public HashMap<String, Object> getZZMaterialList() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("zzmaterialList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	
	public HashMap<String, String> getMaterialInfo(String materialid) {

		HashMap<String, String> map = new HashMap<String, String>();
		try{
			
			dataModel.setQueryFileName("/business/material/materialquerydefine");
			dataModel.setQueryName("getMaterialByMaterialId");
			
			baseQuery = new BaseQuery(request, dataModel);

			userDefinedSearchCase.put("materialid", materialid);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			modelMap = baseQuery.getYsFullData();

			if(dataModel.getRecordCount() > 0){
				map=dataModel.getYsViewData().get(0);
				model.addAttribute("product",map);
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return map;
	}
	

	@SuppressWarnings("unchecked")
	public B_MaterialData getMaterialData2(String materialid) {

		B_MaterialData map = new B_MaterialData();
		try{
			
			B_MaterialDao dao = new B_MaterialDao();
			String where =" materialid= '" + materialid  +"'" + " AND deleteFlag = '0' ";
			
			List<B_MaterialData> list = dao.Find(where);	
			
			if(list.size() > 0){
				map = list.get(0);	
			}			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return map;
	}
	
	
	public Model productSemiUsed() throws Exception {

		String YSId = request.getParameter("YSId");	
		String orderBomId = createOrderBom(YSId);
		model.addAttribute("bomId",orderBomId);
		
		//取得订单信息
		getOrderDetail(YSId);		
		
		return model;
		
	}


	public HashMap<String,Object> createPurchasePlan() throws Exception {

		String bomId = request.getParameter("bomId");
		model.addAttribute("bomId",bomId);
		
		//取得订单信息
		String YSId = request.getParameter("YSId");	

		getOrderDetail(YSId);

		//采购方案
		String orderQuantity = request.getParameter("quantity");	
		return createPurchasePlanView(YSId,bomId,orderQuantity);
		
	}
	

	public HashMap<String,Object> createPurchasePlanFromBaseBom() throws Exception {
	
		String bomId = request.getParameter("bomId");
				
		//取得订单信息
		String YSId = request.getParameter("YSId");			
		
		//采购方案
		String orderQuantity = request.getParameter("quantity");	
		return createPurchasePlanView(YSId,bomId,orderQuantity);		
		
	}
	
	public void createOrderBom() throws Exception {

		B_OrderBomData reqBom = reqModel.getOrderBom();
		String materialId = reqBom.getMaterialid();
		String YSId = reqBom.getYsid();	
		String bomId = reqBom.getBomid();
		
		if(YSId != null && !("").equals(YSId)){
			//已经存在的,先删除
			deleteOrderBom(YSId);
		}
		
		if(bomId != null && !("").equals(bomId)){
			//已经存在的,先删除
			deleteBomDetail(bomId);
		}
		
		//取得订单BOM的编号
		B_OrderBomData order = getMAXOrderBomId (materialId);	
		bomId = order.getBomid();
		
		model.addAttribute("bomId",bomId);

		//创建订单BOM
		order.setYsid(YSId);
		order.setMaterialid(materialId);
		createOrderBomFromBaseBom(order);
		
		//取得订单信息
		getOrderDetail(YSId);
		
	}
	
	@SuppressWarnings("unchecked")
	private List<B_BomDetailData> getBomDetailData(String bomId) throws Exception {
		
		List<B_BomDetailData> dbList = null;
		B_BomDetailDao dao = new B_BomDetailDao();
		try {
			String where = "bomId = '" + bomId
				+ "' AND  deleteFlag = '0' ";
			dbList = (List<B_BomDetailData>)dao.Find(where);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return dbList;
	}	
	
	
	/*
	 * 1.原材料需求表新增处理(N条数据)
	 */
	public String insert(String YSId) throws Exception  {

		ts = new BaseTransaction();

		try {
			ts.begin();
			
			//首先删除DB中的BOM详情
			String where = " YSId = '"+YSId +"'";
			deleteMaterialRequirement(where);
						
			//处理订单详情数据		
			List<B_MaterialRequirmentData> reqDataList = reqModel.getRequirmentList();
			
			for(B_MaterialRequirmentData data:reqDataList ){

				insertRequirment(data);
				
			}
			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return YSId;
	}	

	/*
	 * 订单详情插入处理
	 */
	private void insertRequirment(
			B_MaterialRequirmentData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"MaterialRequirmentInsert",userInfo);

		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		dao.Create(data);	
	}	
	

	private void insertPurchasePlan(
			B_PurchasePlanData data) throws Exception{
		
		B_PurchasePlanDao dao = new B_PurchasePlanDao();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"MaterialRequirmentInsert",userInfo);

		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		dao.Create(data);	
	}	
	

	
	private void insertOrderBom(
			B_OrderBomData data) throws Exception{
		
		B_OrderBomDao dao = new B_OrderBomDao();
				
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"OrderBomInsert",userInfo);
		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		//data.setMaterialid(req.getMaterialid());
		dao.Create(data);			
		
	}
	
	@SuppressWarnings("unchecked")
	private void insertOrderBom2(
			B_OrderBomData orderData,String BaseBomId){
		
		try{
			B_OrderBomDao order = new B_OrderBomDao();
			B_OrderBomData orderDt = new B_OrderBomData();
			B_BomPlanDao plan = new B_BomPlanDao();
			
			String bomId = orderData.getBomid();
			String YSId = orderData.getYsid();
			String subId = orderData.getSubid();
			String parentId = orderData.getParentid();
			
			String base_Where = " bomid = '"+BaseBomId +"'";		
	
			//把基础BOM复制到订单BOM
			List<B_BomPlanData> list = plan.Find(base_Where);		
			if(list != null && list.size() > 0 ){
				copyProperties(orderDt,list.get(0));
			}
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"OrderBomInsert",userInfo);
			copyProperties(orderDt,commData);
			
			guid = BaseDAO.getGuId();
			orderDt.setRecordid(guid);
			orderDt.setBomid(bomId);
			orderDt.setYsid(YSId);
			orderDt.setParentid(parentId);
			orderDt.setSubid(subId);
			order.Create(orderDt);	
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	
	private void insertBomDetail(
			B_BomDetailData data,
			String bomId) throws Exception{
		
		B_OrderBomDetailDao dao = new B_OrderBomDetailDao();
		B_OrderBomDetailData dt = new B_OrderBomDetailData();

		copyProperties(dt,data);
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"OrderBomDetailInsert",userInfo);
		copyProperties(dt,commData);
		
		guid = BaseDAO.getGuId();
		dt.setRecordid(guid);
		dt.setBomid(bomId);
		
		dao.Create(dt);	
	}
	
	
	private void insertOrderBomDetail(
			B_OrderBomDetailData data) throws Exception{
		
		B_OrderBomDetailDao dao = new B_OrderBomDetailDao();

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"OrderBomDetailInsert",userInfo);
		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		dao.Create(data);	
	}
	
	@SuppressWarnings("unchecked")
	private void insertOrderBomDetail2(
			String bomId,
			String baseBomId) throws Exception{
		
		B_OrderBomDetailDao orderDao = new B_OrderBomDetailDao();
		B_BomDetailDao baseDao = new B_BomDetailDao();

		String base_Where = " bomid = '"+baseBomId +"'";
		String order_Where = " bomid = '"+bomId +"'";
		
		//删除旧数据
		try{
			orderDao.RemoveByWhere(order_Where);
		}catch(Exception e){
			//nothing
		}
		
		try{
			//把基础BOM复制到订单BOM
			List<B_BomDetailData> list = baseDao.Find(base_Where);		
			if(list != null && list.size() > 0 ){
				
				for(B_BomDetailData base:list){
	
					B_OrderBomDetailData order = new B_OrderBomDetailData();
					copyProperties(order,base);
					
					commData = commFiledEdit(Constants.ACCESSTYPE_INS,
							"OrderBomDetailInsert",userInfo);
					copyProperties(order,commData);
					
					guid = BaseDAO.getGuId();
					order.setRecordid(guid);
					order.setBomid(bomId);
					
					orderDao.Create(order);				
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void createOrderBomFromBaseBom(
			B_OrderBomData order){
		ts = new BaseTransaction();

		try {
			ts.begin();

			//删除旧数据
			String bomId = order.getBomid();
			//String materialId = order.getMaterialid();
			//String YSId = order.getYsid();
			//deleteOrderBom(YSId);
			//deleteBomDetail(bomId);

			//基础BOM编号
			//String baseMaterialId =BusinessService.getBaseBomId(materialId)[1];
			
			insertOrderBom(order);
			
			List<B_OrderBomDetailData> list = reqModel.getBomDetailList();
			
			for(B_OrderBomDetailData detail:list){
				String materilid = detail.getMaterialid();
				if(materilid != null && !("").equals(materilid)){
					detail.setBomid(bomId);
					insertOrderBomDetail(detail);					
				}
			}
			

			//订单BOM做成
			//insertOrderBom2(order,baseMaterialId);


			//订单BOM详情做成
			//insertOrderBomDetail2(bomId,baseMaterialId);
			
			ts.commit();
			
		}
		catch(Exception e) {
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
	}
	
	private B_OrderBomData getMAXOrderBomId(String parentId) throws Exception {
		
		String bomId = null;
		B_OrderBomData quotData = new B_OrderBomData();

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getMAXOrderBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件   
		userDefinedSearchCase.put("keywords1", parentId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));			
		
		bomId = BusinessService.getOrderBOMFormatId(parentId,code, true);		
		quotData.setBomid(bomId);
		quotData.setSubid(String.valueOf( code+1 ));
		quotData.setParentid(parentId);
		
		//reqModel.setOrderBom(quotData);

		return quotData;
	}
	
	public String insertProcurementPlan(boolean accessFlg) throws Exception  {

		ts = new BaseTransaction();

		String YSId = "";
		try {
			ts.begin();
			
			//更新基础BOM
			B_OrderBomData reqBom = reqModel.getOrderBom();
			YSId = reqBom.getYsid();
			String materialId = reqBom.getMaterialid();

			String bomId = BusinessService.getBaseBomId(materialId)[1];
			
			
			//BOM详情
			//采购方案详情		
			List<B_PurchasePlanData> reqDataList = reqModel.getPurchaseList();
			
			//首先删除旧数据
			//String where = " bomId = '"+bomId +"'";
			//deleteBomDetail(where);
			
			//基础BOM的供应商,单价处理
			
			for(B_PurchasePlanData data:reqDataList ){

				//data.setBomid(bomId);
				String baseMaterialId=data.getMaterialid();
				//String price = data.getPrice();
				String supplierId = data.getSupplierid();
				//String oldPrice = data.getOldprice();
				String oldSupplierId = data.getOldsupplierid();
				
				//float fprice = 0;
				//float foldPrice = 0;
				
				//if(oldPrice != null && !oldPrice.equals("")){
				//	foldPrice = Float.valueOf(oldPrice.replace(",", ""));
				//}
				
				//if(price != null && !price.equals("")){			
				//	fprice = Float.valueOf(price.replace(",", ""));
				//}
				
				if(supplierId != null && !supplierId.equals(oldSupplierId)){
					//只有供应商变动时,更新供应商
					updateBaseBom(bomId,baseMaterialId,supplierId);	
				}
				
				//if(fprice != foldPrice){
					//只有价格有变动时,更新单价
					//updatePriceSupplier(baseMaterialId,supplierId,price);
				//}
			}
			
			//采购方案做成
			//首先删除旧数据
			String where = " YSId = '"+YSId +"'";
			deleteProcurement(where);
			
			for(B_PurchasePlanData data:reqDataList ){

				//float quantiy = toFloat(data.getQuantity());
				//采购数量==0的物料不计入
				//if(quantiy > 0){
					data.setPurchaseid(YSId+"000");
					data.setYsid(YSId);
					insertPurchasePlan(data);
				//}
				
			}
			
			//删除既存合同信息
			deleteContract(YSId);
			
			//删除既存合同明细
			deleteContractDetail(YSId);
			
			//虚拟库存处理
			/*
			for(B_PurchasePlanData data:reqDataList ){

				B_InventoryData d = new B_InventoryData();
				d.setMaterialid(data.getMaterialid());
				d.setAvailabeltopromise(data.getQuantity());
				
				updateInventory(d);			
				
			}
			*/
			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return YSId;
		
	}
	
	/**
	 * 合同删除处理
	 */
	private void deleteContract(String YSId) {
		
		B_PurchaseOrderDao dao = new B_PurchaseOrderDao();

		String astr_Where = " YSId = '" + YSId +"'";	
		try {
			dao.RemoveByWhere(astr_Where);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
	}

	/**
	 * 合同明细删除处理
	 */
	private void deleteContractDetail(String YSId) {
		
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();

		String astr_Where = " YSId = '" + YSId +"'";	
		try {
			dao.RemoveByWhere(astr_Where);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		
	}
	
	/**
	 * 做成订单BOM(物料需求表)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String createOrderBom(String YSId) throws Exception{

		String orderBomId="";
		
		//取得半成品的基础BOM编号
		String semiMaterialId = request.getParameter("semiMaterialId");	
		String semiBaseBomId = BusinessService.getBaseBomId(semiMaterialId)[1];

		//取得产品的基础BOM编号
		String materialId = request.getParameter("materialId");	
		String baseBomId = BusinessService.getBaseBomId(materialId)[1];
		
		//取得基础BOM信息		
		ArrayList<HashMap<String, String>> dataMap = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> semiMap = new ArrayList<HashMap<String, String>>();
		List<B_BomDetailData> rtnMap  = new ArrayList<B_BomDetailData>();
		
		dataMap = (ArrayList<HashMap<String, String>>)getOrderBomDetail(baseBomId).get("data");
		
		semiMap = (ArrayList<HashMap<String, String>>)getOrderBomDetail(semiBaseBomId).get("data");
		
		
		for(HashMap<String, String> product:dataMap){
			B_BomDetailData detail = new B_BomDetailData();
			String material = product.get("materialId");			
			boolean ExFlag = true;
			for(int i=0;i<semiMap.size();i++){
				if(semiMap.get(i).get("materialId").contentEquals(material)){
					semiMap.remove(i);
					ExFlag = false;
					break;
				}
			}
			
			if(ExFlag){
				detail.setPrice(product.get("price"));
				detail.setSupplierid(product.get("supplierId"));
				detail.setMaterialid(product.get("materialId"));
				detail.setQuantity(product.get("quantity"));
				detail.setSubbomno(product.get("subbomno"));
				rtnMap.add(detail);	
			}
		}

		ts = new BaseTransaction();
		try {
			ts.begin();
		
			//取得订单BOM编号
			B_OrderBomData order = getMAXOrderBomId (materialId) ;	
		
			orderBomId = order.getBomid();
			//orderBomId = BusinessService.getOrderBOMParentId(materialId);
	
			//取得半成品信息
			HashMap<String, String> dbData  = getMaterialInfo(semiMaterialId);

			//首先删除旧数据
			B_OrderBomData reqOrder = reqModel.getOrderBom();
			//String YSId = reqOrder.getYsid();
			deleteOrderBom(YSId);
			deleteBomDetail(orderBomId);
			
			//头表处理
			order.setYsid(YSId);
			order.setMaterialid(reqOrder.getMaterialid());
			insertOrderBom(order);
			
			//订单BOM详情
			
			//半成品作为物料录入订单BOM
			B_BomDetailData detail =new B_BomDetailData();
			detail.setMaterialid(semiMaterialId);
			detail.setPrice(dbData.get("price"));
			detail.setSupplierid(dbData.get("supplierId"));
			detail.setQuantity("1");//使用量默认为1
			insertBomDetail(detail,orderBomId);	
			
			//其他物料
			for(B_BomDetailData data:rtnMap ){

				insertBomDetail(data,orderBomId);		
				
			}
			ts.commit();
			
		}catch(Exception e){
			ts.rollback();
			System.out.println(e.getMessage());
		}
		
		return orderBomId;
	}
	
	/*
	public String insertProcurementPlan(boolean accessFlg) throws Exception  {

		ts = new BaseTransaction();

		String YSId = "";
		try {
			ts.begin();
			
			//订单BOM做成
			//BOM方案
			B_BomPlanData reqBom = reqModel.getBomPlan();
			YSId = reqBom.getYsid();
			String materialId = reqBom.getMaterialid();
			String bomId="";
			if(accessFlg){
				String parentId = BusinessService.getOrderBOMParentId(materialId);
				B_BomPlanData bom = getBomIdByParentId(parentId);
				bomId = bom.getBomid();
				reqBom.setBomid(bomId);
				reqBom.setParentid(bom.getParentid());
				reqBom.setSubid(bom.getSubid());
			}else{
				bomId = reqBom.getBomid();
			}
			
			insertBomPlan(reqBom);
			//reqModel.setBomPlan(reqBom);
			
			//BOM详情
			List<B_BomDetailData> reqBomList = reqModel.getBomDetailList();
			//采购方案详情		
			List<B_PurchasePlanData> reqDataList = reqModel.getPurchaseList();
			
			//首先删除旧数据
			String where = " bomId = '"+bomId +"'";
			deleteBomDetail(where);
			
			for(B_BomDetailData data:reqBomList ){

				data.setBomid(bomId);
				String baseMaterialId=data.getMaterialid();
				
				//一级单价处理
				boolean flg = true;
				for(B_PurchasePlanData purchase:reqDataList){
					String purchaseMate = purchase.getMaterialid();
					if(baseMaterialId.equals(purchaseMate)){
						data.setPrice(purchase.getPrice());
						data.setSupplierid(purchase.getSupplierid());
						data.setTotalprice(purchase.getTotalprice());
						
						flg = false;
						break;
					}
				}
				
				if(flg){//二级物料

					//B系列的计算					
					List<HashMap<String, String>> hsmp = getZZRawMaterial(baseMaterialId);
					
					for(HashMap<String, String> map:hsmp){
													
						if(baseMaterialId.equals(map.get("materialId"))){
							data.setPrice(map.get("newPrice"));
							break;								
						}
					}					
				}
				
				insertBomDetail(data);		
				
			}
			
			//采购方案做成
			//首先删除旧数据
			where = " YSId = '"+YSId +"'";
			deleteProcurement(where);
			
			for(B_PurchasePlanData data:reqDataList ){

				data.setPurchaseid(YSId+"000");
				data.setYsid(YSId);
				insertPurchasePlan(data);				
			}
			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return YSId;
		
	}
	*/
	public ArrayList<HashMap<String, String>> getZZRawMaterial(
			String materialId) throws Exception{
		
		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getRequirementPrice");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		return dataModel.getYsViewData();

	}
	
	private B_OrderBomData getBomIdByParentId(String parentId) 
			throws Exception {
		
		B_OrderBomData bomPlanData = new B_OrderBomData();
		String bomId = null;
		dataModel = new BaseModel();
		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomIdByParentId");
		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件   
		userDefinedSearchCase.put("keywords1", parentId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		bomId = BusinessService.getOrderBOMFormatId(parentId,code, true);
			
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(String.valueOf( code+1 ));
		bomPlanData.setParentid(parentId);
		
		reqModel.setOrderBom(bomPlanData);

		return bomPlanData;
	}
	
	/*
	 * 订单基本信息更新处理
	 */
	public void updateRequirment(
			B_MaterialRequirmentData data) throws Exception{

			//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"MaterialRequirementUpdate",userInfo);
		copyProperties(dao.beanData,commData);

		//获取页面数据
		dao.beanData.setQuantity(data.getQuantity());
		dao.beanData.setPrice(data.getPrice());
		dao.beanData.setTotalprice(data.getTotalprice());
		
		dao.Store(dao.beanData);

	}

	/*
	 * 审批处理:常规订单是以耀升编号为单位更新,
	 * 库存订单存在多个相同的耀升编号,需要逐条更新
	 */
	public void updateDetailToApprove(String PIId) throws Exception  {

		ts = new BaseTransaction();
		
		try {
			
			ts.begin();

			//根据画面的PiId取得DB中更新前的订单详情 
			List<B_OrderDetailData> detailList = null;

			//库存订单的PI编号,订单编号,耀升编号相同
			String where = " PIId = '"+PIId +"'" + " AND deleteFlag = '0' ";
			//detailList = getOrderDetailByPIId(where);
						
			for(B_OrderDetailData dbData:detailList ){				
					
				//更新处理//处理共通信息
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"ZZOrderDetailUpdate",userInfo);

				copyProperties(dbData,commData);
				//更新审批状态:下一步是生成物料需求表
				dbData.setStatus(Constants.ORDER_STS_3);
				
				//detailDao.Store(dbData);
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
		
	}
	
	public void updateBaseBom(
			String bomId,String material,String supplier) throws Exception{

		B_BomDetailDao dao = new B_BomDetailDao();
		B_BomDetailData bom = supplierCheck(bomId,material);
		
		
		if(bom == null){
			return;
		}
		
		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"SupplierUpdate",userInfo);
		copyProperties(bom,commData);

		//设置新的供应商
		bom.setSupplierid(supplier);
		
		dao.Store(bom);

	}
	
	public void deleteOrderBom(String YSId){

		String where = " YSId = '"+YSId +"'";
		B_OrderBomDao dao = new B_OrderBomDao();
		
		try{
			dao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
		}	
	}
	
	public void deleteBomDetail(String bomId) 
			throws Exception{

		String where = " bomId = '"+bomId +"'";
		B_OrderBomDetailDao dao = new B_OrderBomDetailDao();
		
		try{
			dao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
		}	
	}
	
	
	/*
	 * 删除处理
	 */
	public void deleteProcurement(String where) 
			throws Exception{
		B_PurchasePlanDao dao = new B_PurchasePlanDao();
		
		try{
			dao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
		}	
	}
	
	/*
	 * 删除处理
	 */
	public void deleteMaterialRequirement(String where) 
			throws Exception{
		
		try{
			dao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
		}	
	}
	
	public void getOrderDetail(String YSId) throws Exception {

		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keyword1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		modelMap = baseQuery.getYsFullData();
		
		if (dataModel.getRecordCount() > 0 ){
			model.addAttribute("order",  modelMap.get(0));		
		}	
	}
	public void getRequirement(String bomId,String order) throws Exception {

		System.out.println("*****有临时对应*****");
		
		ArrayList<HashMap<String, String>> list = new  ArrayList<HashMap<String, String>>();
		int orderNum = Integer.parseInt(order.replace(",", ""));
		
		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getRequirement");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		//baseQuery.getSql();
		//baseQuery.getFullData();
		
		if (dataModel.getRecordCount() <= 0 )
			return;
			
		boolean repeat = true;
		String materialId2 = "";
		int index = 0;
				
		for(HashMap<String, String> map:dataModel.getYsViewData()){
			
			String materialId = map.get("materialId");//一级级物料名称

			String typeH = materialId.substring(0,1);//H类判定用
			String type = materialId.substring(0, 3);

			String lastPrice = map.get("lastPrice");//原材料 最新单价
			String lastSupplierId = map.get("lastSupplierId");//原材料 最新供应商
			//String matLastPrice = map.get("matLastPrice");//配件 最新单价
			//String matLastSupplierId = map.get("matLastSupplierId");//配件 最新供应商
			String minPrice = map.get("minPrice");//原材料最低单价
			String minSupplierId = map.get("minSupplierId");//原材料最低供应商
			String matMinPrice = map.get("matMinPrice");//配件最低单价
			String matMinSupplierId = map.get("matMinSupplierId");//配件最低供应商
			String price = map.get("price");//配件当前单价
			String supplierId = map.get("supplierId");//一级物料供应商
			String bomq = map.get("quantity");//自制品的用量
			String stock = map.get("stock");//自制品的库存
			stock = "0";//******************临时对应************************
			float ibomq = Float.parseFloat(bomq);//自制品的用量
			float istock = Float.parseFloat(stock);//自制品的库存
			
			if (supplierId!=null && supplierId.trim().equals("0574YS00")){

				if(typeH.equals("H"))
					continue;
				
				String rawmater = map.get("rawMaterialId")+"";//二级物料名称(原材料)

				String zzq = map.get("weight");//原材料的使用量
				String zzconvert = map.get("convertUnit");//原材料的使用单位与采购单位的比例

				float iconvert = Float.parseFloat(zzconvert);//原材料的使用单位与采购单位的比例
				float izzq = Float.parseFloat(zzq);//原材料的使用量
				
				float value = ibomq * izzq * (orderNum - istock) / iconvert;//原材料的需求量="建议采购量"

					
				map.put("advice", BusinessService.format2Decimal(value));
				map.put("gradedFlg", "1");//标识为二级物料
				map.put("price",lastPrice );
				map.put("matSupplierId",lastSupplierId );
				map.put("minPrice",minPrice );
				map.put("minSupplierId",minSupplierId );
				map.put("lastPrice",lastPrice );
				map.put("lastSupplierId",lastSupplierId );
				
				//自制品
				repeat = true;
				for(HashMap<String, String> map2:list){
					
					materialId2 = map2.get("materialId");//一级级物料名称
					String bomsupplier2 = (map2.get("supplierId")+"").trim();//一级物料供应商
					String rawmater2 = map2.get("rawMaterialId");//二级物料名称(原材料)\
					
					if(rawmater.equals(rawmater2) 
							&& bomsupplier2.equals("0574YS00")){
							//&& subBomIdBefore.equals(subBomIdAfter)){
						//合并重复的原材料
						float value2 = Float.parseFloat(map2.get("advice"));
						float cnt = value + value2;
						map2.put("advice", BusinessService.format2Decimal(cnt));
						
						repeat = false;//找到重复项
						break;							
					}						
				}
				
				if(repeat){
					list.add(map);//没有重复项时,新加一条	
					index++;
				}					
				
				
			}else{
				
				boolean skip = true;
				if(type.equals("B01") || type.equals("F01") || 
				   type.equals("F02") || type.equals("F03") || 
				   type.equals("F04")){							
				
					//配件
					if(list.size() >0){
						materialId2 = list.get(index-1).get("materialId");//一级级物料名称
				
						if(materialId.equals(materialId2)){
								//&& subBomIdBefore.equals(subBomIdAfter)){
							//该自制品虽然是多个原材料构成,但由于需要外采整个自制品,所以过滤掉重复的数据
							skip = false;
						}
					}
				}
				
				if(skip){
					//配件
					float value = ibomq * orderNum - istock;//配件需求量=用量*订单数量-库存
					map.put("advice", String.valueOf(value));
					map.put("price",price );
					map.put("matSupplierId",supplierId );
					map.put("minPrice",matMinPrice );
					map.put("minSupplierId",matMinSupplierId );
					map.put("lastPrice",price );
					map.put("lastSupplierId",supplierId );
					
					repeat=true;					
					for(HashMap<String, String> map2:list){

						materialId2 = map2.get("materialId");//一级级物料名称
						
						if(materialId.equals(materialId2)){
							float value2 = Float.parseFloat(map2.get("advice"));
							float cnt = value + value2;
							map2.put("advice", BusinessService.format2Decimal(cnt));
							
							repeat = false;//找到重复项
							break;
						}
					}
					
					if(repeat){
						list.add(map);
						index++;	
						
					}				
				}

			}
		}	
		
		model.addAttribute("requirement", list);
			
			
	}
	

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> createPurchasePlanView(
			String YSId,String bomId,String order) throws Exception {
		
		ArrayList<HashMap<String, String>> list = 
				new  ArrayList<HashMap<String, String>>();		
			
		int orderNum = Integer.parseInt(order.replace(",", ""));
		
		ArrayList<HashMap<String, String>> zzmaterial = 
				(ArrayList<HashMap<String, String>>) getZZMaterial(bomId).get("data");		
			
		int rowNum = 1;
		for(HashMap<String, String> map:zzmaterial){
			String reqQuantiy = map.get("requirement").replace(",", "");
			String convertUnit = map.get("convertUnit");
			String promise = map.get("availabelToPromise").replace(",", "");
			float freqQuantiy = Float.parseFloat(reqQuantiy);
			float fconvertUnit = Float.parseFloat(convertUnit);
			float fpromise = Float.parseFloat(promise);
			
			float orderQuantity = freqQuantiy * orderNum / fconvertUnit;//订单需求量
			float purchaseQuantiy = orderQuantity - fpromise;//需求量-库存
			if(purchaseQuantiy < 0) purchaseQuantiy = 0;//小于库存,即为有库存,采购量就是0

			map.put("orderQuantity", String.valueOf(orderQuantity));
			map.put("purchaseQuantity", String.valueOf(purchaseQuantiy));
			
			list.add(map);
			rowNum++;
			
		}
		
		//取得订单BOM的物料信息
		dataModel.setQueryName("getOrderBomForPurchasePlan");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		ArrayList<HashMap<String, String>> material = baseQuery.getYsFullData();
		
		for(int i=0;i<material.size();i++){
			HashMap<String, String> map = material.get(i);
			String materialId = map.get("materialId");//一级级物料名称
			String type = materialId.substring(0, 3);//物料编号
			String supplierId = map.get("supplierId");//一级物料供应商
			
			if (supplierId!=null && supplierId.trim().equals("0574YS00")){
				if(type.equals("B01") || type.equals("F01") || 
				   type.equals("F02") || type.equals("F03") || 
				   type.equals("F04")){	//自制品按照原材料采购,不显示一级BOM
					
					continue;//过滤掉二级BOM
				}
			}
			String promise = map.get("availabelToPromise").replace(",", "");
			String quantity = map.get("quantity").replace(",", "");
			float freqQuantiy = Float.parseFloat(quantity);
			float fpromise = Float.parseFloat(promise);
			
			float orderQuantity = freqQuantiy * orderNum;
			float purchaseQuantiy = orderQuantity - fpromise;//需求量-库存
			if(purchaseQuantiy < 0) purchaseQuantiy = 0;//小于库存,即为有库存,采购量就是0
			
			map.put("orderQuantity", String.valueOf(orderQuantity));//订单需求量
			map.put("purchaseQuantity", String.valueOf(purchaseQuantiy));//建议采购量
			map.put("rownum", String.valueOf(rowNum));
			list.add(map);	
			rowNum++;			
						
		}
		
		model.addAttribute("requirement", list);	
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("data", list);
		
		return hashmap;
			
	}
	
	
	public void createRequirementTable() throws Exception{
		String order = "0";
		ArrayList<HashMap<String, String>> list = new  ArrayList<HashMap<String, String>>();
		int orderNum = Integer.parseInt(order.replace(",", ""));
		
		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getRequirement");
		
		baseQuery = new BaseQuery(request, dataModel);

		//userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		//baseQuery.getSql();
		//baseQuery.getFullData();
		
		if (dataModel.getRecordCount() <= 0 )
			return;
		
	}
	
	public HashMap<String, Object> getOrderBomByYSId(
			String YSId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderBomByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			HashMap.put("recordsTotal", dataModel.getRecordCount());
			HashMap.put("data", dataModel.getYsViewData());		
	
		}		
				
		return HashMap;
	}
	
	public HashMap<String, Object> getOrderBomDetail(
			String bomId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBaseBomDetailByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			HashMap.put("recordsTotal", dataModel.getRecordCount());
			HashMap.put("data", dataModel.getYsViewData());		
			model.addAttribute("requirement", dataModel.getYsViewData());
	
		}else{
			return null;
		}		
				
		return HashMap;
	}
	
	public HashMap<String, Object> getPurchaseDetail(
			String YSId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getPurchaseDetail");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("requirement", dataModel.getYsViewData());	
			HashMap.put("data", dataModel.getYsViewData());
		}
		return HashMap;
	}
	
	public void geBaseDetail(
			String YSId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getPurchaseDetail");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("requirement", dataModel.getYsViewData());	
		}	
	}
	
	@SuppressWarnings({ "rawtypes" })
	public boolean checkContractExsit(
			String YSId) throws Exception {

		boolean rtn = false;
		B_PurchaseOrderDao dao = new B_PurchaseOrderDao();
		String where =" YSId= '" + YSId  +"'" + " AND deleteFlag = '0' ";
		
		Vector list = dao.Find(where);
		if(list.size() > 0){
			rtn = true;	
		}
		
		return rtn;
				
	}
	
	@SuppressWarnings("rawtypes")
	public boolean checkPurchaseExsit(
			String YSId) throws Exception {

		boolean rtn = false;
		B_PurchasePlanDao dao = new B_PurchasePlanDao();
		String where =" YSId= '" + YSId  +"'" + " AND deleteFlag = '0' ";
		
		Vector list = dao.Find(where);
		if(list.size() > 0){
			rtn = true;	
		}
		
		return rtn;
				
	}
	
	@SuppressWarnings("rawtypes")
	public boolean checkOrderBomExsit(
			String YSId) throws Exception {

		boolean rtn = false;
		B_OrderBomDao dao = new B_OrderBomDao();
		String where =" YSId= '" + YSId  +"'" + " AND deleteFlag = '0' ";
		
		Vector list = dao.Find(where);
		if(list.size() > 0){
			rtn = true;	
			B_OrderBomData d = (B_OrderBomData) list.get(0);
			String bomId = d.getBomid();
			model.addAttribute("bomId",bomId);
		}
		
		return rtn;
				
	}
	
	@SuppressWarnings("unchecked")
	public B_BomDetailData supplierCheck(
			String bomId,String materialId) throws Exception {

		B_BomDetailData rtn = null;
		B_BomDetailDao dao = new B_BomDetailDao();
		String where =" bomId= '" + bomId  +"'" +
					  " AND materialId= '" + materialId  +"'"+
				      " AND deleteFlag = '0' ";
		
		List<B_BomDetailData> list = dao.Find(where);
		
		
		
		if(list.size() > 0){
			rtn = list.get(0);	
		}
		
		return rtn;
				
	}
	public void insertAndView() throws Exception {

		B_MaterialRequirmentData requirement = reqModel.getRequirment();
		String YSId = requirement.getYsid();
		
		insert(YSId);
		
		String sub = YSId.substring(YSId.length()-2);
		
		if (sub.equals(BusinessConstants.SHORTNAME_ZZ)){
			
			getOrderZZRawList(YSId);
			
			getOrderZZRawSum(YSId);	
			
		}else{

			getOrderDetailByYSId(YSId);
			
			getRequirementList(YSId);
			
		}
	}

	public void editZZorder() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		getOrderZZRawList(YSId);
		
		getOrderZZRawSum(YSId);
	}
	
	public void editorder() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		getOrderRawList(YSId);
		
		getOrderRawSum(YSId);
	}
	
	public void editorderPart() throws Exception {
		
		String YSId = request.getParameter("YSId");

		getOrderDetailByYSId(YSId);
		
		getOrderPartList(YSId);
	}
	public void approveAndView() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		updateDetailToApprove(YSId);
		
		//getZZOrderDetail(YSId);	
		
	}
	
	public void createOrView() throws Exception {
		
		String YSId = request.getParameter("YSId");
		getOrderDetail(YSId);
		
	}
	

	public void getPurchasePlan() throws Exception {
		
		String YSId = request.getParameter("YSId");	
			
		//return getPurchaseDetail(YSId);	

		getOrderDetail(YSId);
		
	}
	
	public void editRequirement() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String bomId = request.getParameter("bomId");
		model.addAttribute("bomId",bomId);

		getOrderDetail(YSId);
		
	}
	
	//重置采购方案
	public void resetRequirement() throws Exception {
		
		String YSId = reqModel.getOrderBom().getYsid();
		String bomId = reqModel.getOrderBom().getBomid();
		model.addAttribute("bomId",bomId);
		
		getOrderDetail(YSId);		
	}
	
	
	public void createRequirement() throws Exception {
		
		String materialId = request.getParameter("materialId");

		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		String order = request.getParameter("order");
		getRequirement(bomId,order);	
		
	}

	public void insertProcurement() throws Exception {
		
		String YSId = insertProcurementPlan(true);	
		
		//getPurchaseDetail(YSId);
		String bomId = reqModel.getOrderBom().getBomid();
		model.addAttribute("bomId",bomId);
		
		//取得订单信息
		getOrderDetail(YSId);
		
	}
	
	public void cansolEditPlan() throws Exception {
		
		String YSId = request.getParameter("YSId");			
		String bomId = request.getParameter("bomId");
		model.addAttribute("bomId",bomId);
		
		//取得订单信息
		getOrderDetail(YSId);
		
	}

	public void creatPurchaseOrder() throws Exception {

		String YSId = request.getParameter("YSId");
		
		PurchaseOrderService contract = new PurchaseOrderService(
				model,request,session,userInfo);
		
		contract.insert(YSId);
		
		String bomId = request.getParameter("bomId");
		model.addAttribute("bomId",bomId);

		//取得订单信息
		getOrderDetail(YSId);
		
	}
	
	public void updateProcurement() throws Exception {
		
		String YSId = insertProcurementPlan(false);	
		
		getPurchaseDetail(YSId);
		
		getOrderDetail(YSId);		
	}

	public void printProcurement() throws Exception {
		
		String materialId = request.getParameter("materialId");	
		String bomId = request.getParameter("bomId");	
		
		//getPurchaseDetail(YSId);
		model.addAttribute("bomId",bomId);
		
		getMaterialInfo(materialId);		
	}

	public HashMap<String, Object> getZZMaterial(String bomId) throws Exception {

		HashMap<String, Object> map =new HashMap<String, Object>();
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getRawRequirement");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		map.put("data", dataModel.getYsViewData());
		return map;

	}
	
	public HashMap<String, Object> getOrderBomDetail() throws Exception {

		String YSId = request.getParameter("YSId");
		
		return getOrderBomByYSId(YSId);		
	}
	
	@SuppressWarnings("unchecked")
	public void updateOrderBomQuantity() throws Exception {

		String bomId = request.getParameter("bomId");
		String materialId = request.getParameter("materialId");
		String quantity = request.getParameter("quantity");


		String where = " bomId='"+bomId +
				"' AND materialId='" + materialId +
				"' AND deleteFlag='0'";
		B_OrderBomDetailDao dao = new B_OrderBomDetailDao();
		B_OrderBomDetailData dt = new B_OrderBomDetailData();
		
		List<B_OrderBomDetailData> list = (List<B_OrderBomDetailData>)dao.Find(where);
		
		if(list.size() >0){
			
			dt = list.get(0);
			dt.setQuantity(quantity);
			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"RequirmentUpdate",userInfo);
			copyProperties(dt,commData);
			
			dao.Store(dt);
		}
	}
	
	public HashMap<String, Object> showBaseBomDetail() throws Exception {

		String materialId = request.getParameter("materialId");
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		return getOrderBomDetail(bomId);		
	}
	
	public HashMap<String, Object> getRequriementBySupplier() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String YSId = request.getParameter("YSId");
		String supplierId = request.getParameter("supplierId");

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getRequriementBySupplier");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("supplierId", supplierId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("contractList",dataModel.getYsViewData());
		model.addAttribute("contract",dataModel.getYsViewData().get(0));
		
		modelMap.put("retValue", "success");	
		
		return modelMap;
			
	}
}
