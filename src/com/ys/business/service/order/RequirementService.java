package com.ys.business.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.ys.business.db.dao.B_BaseBomDao;
import com.ys.business.db.dao.B_BomDetailDao;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_MaterialRequirmentDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.data.B_BaseBomData;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_MaterialRequirmentData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
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
			RequirementModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_MaterialRequirmentDao();
		this.model = model;
		this.reqModel = reqModel;
		this.dataModel = new BaseModel();
		this.request = request;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
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
	
	public void getMaterialInfo(String materialid) {

		try{

			dataModel.setQueryFileName("/business/material/materialquerydefine");
			dataModel.setQueryName("getMaterialByMaterialId");
			
			baseQuery = new BaseQuery(request, dataModel);

			userDefinedSearchCase.put("materialid", materialid);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			modelMap = baseQuery.getYsFullData();

			model.addAttribute("product",dataModel.getYsViewData().get(0));

			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
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
	
	private void insertBomPlan(
			B_BomPlanData data) throws Exception{
		
		B_BomPlanDao dao = new B_BomPlanDao();
		
		String bomid = data.getBomid();
		String astr_Where = " bomid = '"+bomid +"'";
		@SuppressWarnings("unchecked")
		List<B_BomPlanData> list = dao.Find(astr_Where);
		
		if(list!=null && list.size() > 0){
			//data  = list.get(0);
			copyProperties(list.get(0),data);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"BomPlanUpdate",userInfo);

			copyProperties(list.get(0),commData);
			
			dao.Store(list.get(0));
			
		}else{
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"BomPlanInsert",userInfo);

			copyProperties(data,commData);
			
			guid = BaseDAO.getGuId();
			data.setRecordid(guid);
			data.setBomtype(Constants.BOMTYPE_O);//订单BOM
			dao.Create(data);
			
		}	
	}
	
	private void insertBomDetail(
			B_BomDetailData data) throws Exception{
		
		B_BomDetailDao dao = new B_BomDetailDao();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"BomDetailInsert",userInfo);

		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		dao.Create(data);	
	}
	
	public String insertProcurementPlan(boolean accessFlg) throws Exception  {

		ts = new BaseTransaction();

		String YSId = "";
		try {
			ts.begin();
			
			//更新基础BOM
			B_BomPlanData reqBom = reqModel.getBomPlan();
			YSId = reqBom.getYsid();
			String materialId = reqBom.getMaterialid();

			String bomId = BusinessService.getBaseBomId(materialId)[1];
			
			
			//BOM详情
			List<B_BomDetailData> reqBomList = reqModel.getBomDetailList();
			//采购方案详情		
			List<B_PurchasePlanData> reqDataList = reqModel.getPurchaseList();
			
			//首先删除旧数据
			//String where = " bomId = '"+bomId +"'";
			//deleteBomDetail(where);
			
			for(B_PurchasePlanData data:reqDataList ){

				//data.setBomid(bomId);
				String baseMaterialId=data.getMaterialid();
				String supplierId = data.getSupplierid();
				String price = data.getPrice();
				
				//更新供应商
				updateBaseBom(bomId,baseMaterialId,supplierId);				
				
				//更新单价
				updatePriceSupplier(baseMaterialId,supplierId,price);
				//更新最新,最低单价
				updatePriceInfo(baseMaterialId);
			}
			
			//采购方案做成
			//首先删除旧数据
			String where = " YSId = '"+YSId +"'";
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
	
	private B_BomPlanData getBomIdByParentId(String parentId) 
			throws Exception {
		
		B_BomPlanData bomPlanData = new B_BomPlanData();
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
		
		reqModel.setBomPlan(bomPlanData);

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
		
		if(bom.getSupplierid().equals(supplier)){
			//供应商没变化,则不更新
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
	
	public void deleteBomDetail(String where) 
			throws Exception{
		B_BomDetailDao dao = new B_BomDetailDao();
		
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
		int orderNum = Integer.parseInt(order);
		
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
		
		String subBomIdBefore = "";
		String subBomIdAfter  = "";
		boolean firstFlg = true;
		
		for(HashMap<String, String> map:dataModel.getYsViewData()){
			
			String materialId = map.get("materialId");//一级级物料名称
			String subBomId = map.get("subBomId")+"";//组套BOM中的BOM编号

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
				
			subBomIdBefore = subBomId;//第一次不设置				

			if (supplierId.trim().equals("0574YS00")){

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
					String rawmater2 = map2.get("rawMaterialId");//二级物料名称(原材料)
					subBomIdAfter = map2.get("subBomId");//已存储到采购方案里的子BOM编号
					
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
						subBomIdAfter = list.get(index-1).get("subBomId");//已存储到采购方案里的子BOM编号
				
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
				HashMap.put("data", modelMap);				
	
		}else{
			return null;
		}		
				
		return HashMap;
	}
	public void getPurchaseDetail(
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
	
	@SuppressWarnings("unchecked")
	public boolean checkPurchaseExsit(
			String YSId) throws Exception {

		boolean rtn = false;
		B_PurchasePlanDao dao = new B_PurchasePlanDao();
		String where =" YSId= '" + YSId  +"'" + " AND deleteFlag = '0' ";
		
		List<B_PurchasePlanData> list = dao.Find(where);
		
		
		
		if(list.size() > 0){
			rtn = true;	
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
	
	public boolean createOrView() throws Exception {
		
		String YSId = request.getParameter("YSId");

		boolean flg = checkPurchaseExsit(YSId);
		
		if(flg){
			
			getPurchaseDetail(YSId);
			
		}else{

			createRequirement();
		}

		getOrderDetail(YSId);
		
		return flg;
	}
	
	public void editRequirement() throws Exception {
		
		String YSId = request.getParameter("YSId");
		String bomId = request.getParameter("bomId");
		model.addAttribute("bomId",bomId);
		getPurchaseDetail(YSId);
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
		
		getPurchaseDetail(YSId);
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

	public HashMap<String, Object> getZZMaterial() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String materialId = request.getParameter("materialId");

		String bomId = BusinessService.getBaseBomId(materialId)[1];
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getRawRequirement");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");	
		
		return modelMap;
		
	}
	
	public HashMap<String, Object> showOrderBomDetail() throws Exception {

		String bomId = request.getParameter("bomId");
		
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
