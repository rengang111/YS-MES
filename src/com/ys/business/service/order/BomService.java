package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.service.common.BaseService;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.BomPlanModel;
import com.ys.business.db.dao.B_BomDetailDao;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.CommFieldsData;

@Service
public class BomService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_BomPlanData bomPlanData;
	private B_BomDetailData bomDetailData;
	private B_BomPlanDao bomPlanDao;
	private B_BomDetailDao bomDetailDao;
	private BomPlanModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public BomService(){
		
	}

	public BomService(Model model,
			HttpServletRequest request,
			BomPlanModel reqModel,
			UserInfo userInfo){
		
		this.bomPlanDao = new B_BomPlanDao();
		this.bomPlanData = new B_BomPlanData();
		this.bomDetailDao = new B_BomDetailDao();
		this.bomDetailData = new B_BomDetailData();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		
	}

	public HashMap<String, Object> getBomList(String data) throws Exception {
		
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
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomList");
		
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
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	

	public HashMap<String, Object> getCopyBomList(String data) throws Exception {
		
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
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getCopyBomList");
		
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
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getBomApproveList(String data) throws Exception {
		
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
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomApproveList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	 
		
		
		
		modelMap.put("sEcho", sEcho);		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	public Model getBomDetailView(String bomId) 
			throws Exception {
		

		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomDetailListByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keywords1", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("bomPlan",  modelMap.get(0));
		model.addAttribute("bomDetail", modelMap);
				
		return model;
	}

	public void getOrderDetailByYSId() throws Exception{
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getOrderDetailByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);

		String YSId = request.getParameter("YSId");
		userDefinedSearchCase.put("keywords1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsQueryData(0, 0);
		
		model.addAttribute("bomPlan", dataModel.getYsViewData().get(0));	
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	private List<B_BomDetailData> getBomDetailByBomId(
			String where ) throws Exception {
		
		List<B_BomDetailData> dbList = null;
				
		try {

			dbList = (List<B_BomDetailData>)bomDetailDao.Find(where);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbList = null;
		}
		
		return dbList;
	}
	
	
	
	private HashMap<String, Object> getOrderSubIdByParentId(
			HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		
		String key = request.getParameter("parentId");

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderSubIdByParentId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("retValue", "success");
		
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		int newSubid = code + 1;
		
		String codeFormat = String.format("%03d", newSubid); 
		
		modelMap.put("code",newSubid);	
		modelMap.put("codeFormat",codeFormat);			
		
		return modelMap;
	}
	
	/*
	 * 
	 */
	public HashMap<String, Object> getMaterialList(
			HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;


		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getMaterialPriceList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	/*
	 * 
	 */
	public HashMap<String, Object> getSupplierPriceList() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;


		String key1 = request.getParameter("key1").toUpperCase();
		String key2 = request.getParameter("key2").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getSupplierPriceList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key1);
		userDefinedSearchCase.put("keywords2", key2);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	
	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	private String insert() throws Exception  {

		String bomid="";
		String YSId="";
		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_BomPlanData reqData = (B_BomPlanData)reqModel.getBomPlan();
			
			YSId = reqData.getYsid();
			
			//插入BOM基本信息
			insertBomPlan(reqData);
			
			//插入BOM详情		
			bomid = reqData.getBomid();
			List<B_BomDetailData> reqDataList = reqModel.getBomDetailLines();
			
			for(B_BomDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && 
					data.getMaterialid() != null && 
					data.getMaterialid() != ""){
					
					data.setBomid(bomid);
					insertBomDetail(data);	
					
					//供应商单价修改
					//String supplierId = data.getSupplierid();
					//String materialId = data.getMaterialid();
					//String price = data.getPrice();
					//updatePriceSupplier(materialId,supplierId,price);
				}	
			
			}
			//更新订单状态为待评审
			updateOrderByYSId(YSId);
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return bomid;
		
	}	

	/*
	 * BOM基本信息insert处理
	 */
	private void insertBomPlan(
			B_BomPlanData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"BomPlanInsert",userInfo);

		copyProperties(data,commData);

		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		bomPlanDao.Create(data);	

	}	
	
	/*
	 * BOM详情插入处理
	 */
	private void insertBomDetail(
			B_BomDetailData detailData) throws Exception{
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"BomDetailInsert",userInfo);

		copyProperties(detailData,commData);
		guid = BaseDAO.getGuId();
		detailData.setRecordid(guid);
		
		bomDetailDao.Create(detailData);

	}	
	
	/*
	 * 1.订单基本信息更新处理(1条数据)
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	private String update() throws Exception  {
		
		String bomId = "";
		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
						
			B_BomPlanData reqBom = (B_BomPlanData)reqModel.getBomPlan();
			
			//订单基本信息更新处理
			updateBomPlan(reqBom);
			
			//订单详情 更新/删除/插入 处理			
			List<B_BomDetailData> newDetailList = reqModel.getBomDetailLines();
			List<B_BomDetailData> delDetailList = new ArrayList<B_BomDetailData>();
			
			//过滤页面传来的有效数据
			for(B_BomDetailData data:newDetailList ){
				if(null == data.getMaterialid() || ("").equals(data.getMaterialid())){
					delDetailList.add(data);
				}
			}
			newDetailList.removeAll(delDetailList);
			
			//清空后备用
			delDetailList.clear();
			
			//根据画面的PiId取得DB中更新前的订单详情 
			//key:piId
			List<B_BomDetailData> oldDetailList = null;

			bomId = reqBom.getBomid();
			String where = " bomId = '"+bomId +"'" + " AND deleteFlag = '0' ";
			oldDetailList = getBomDetailByBomId(where);
						
			//页面数据的recordId与DB匹配
			//key存在:update;key不存在:insert;
			//剩下未处理的DB数据:delete
			for(B_BomDetailData newData:newDetailList ){

				String newMaterialid = newData.getMaterialid();	

				boolean insFlag = true;
				for(B_BomDetailData oldData:oldDetailList ){
					String id = oldData.getMaterialid();
					
					if(newMaterialid.equals(id)){
						
						//更新处理
						updateBomDetail(newData,oldData);					
						
						//从old中移除处理过的数据
						oldDetailList.remove(oldData);
						
						insFlag = false;
						
						break;
					}
				}

				//新增处理
				if(insFlag){
					newData.setBomid(bomId);
					insertBomDetail(newData);
				}
				
				//供应商单价修改
				//String supplierId = newData.getSupplierid();
				//String materialId = newData.getMaterialid();
				//String price = newData.getPrice();
				//updatePriceSupplier(materialId,supplierId,price);
			}
			
			//删除处理
			if(oldDetailList.size() > 0){					
				deleteBomDetail(oldDetailList);
			}
			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
		}
				
		return bomId;
	}
	
	/*
	 * BOM基本信息更新处理
	 */
	private void updateBomPlan(B_BomPlanData reqBom) 
			throws Exception{

		String recordid = reqBom.getRecordid();
		
		//取得更新前数据		
		bomPlanData = getBomByRecordId(recordid);					
		
		if(null != bomPlanData){
		
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"BomPlanUpdate",userInfo);

			//处理共通信息
			copyProperties(bomPlanData,commData);
			//获取页面数据
			bomPlanData.setPlandate(reqBom.getPlandate());
			bomPlanData.setManagementcostrate(reqBom.getManagementcostrate());
			bomPlanData.setManagementcost(reqBom.getManagementcost());
			bomPlanData.setLaborcost(reqBom.getLaborcost());
			bomPlanData.setMaterialcost(reqBom.getMaterialcost());
			bomPlanData.setTotalcost(reqBom.getTotalcost());
			bomPlanData.setProductcost(reqBom.getProductcost());
			
			bomPlanDao.Store(bomPlanData);
		}
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateBomDetail(
			B_BomDetailData newData,
			B_BomDetailData dbData) 
			throws Exception{
								
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"BomDetailUpdate",userInfo);

		//处理共通信息
		copyProperties(newData,commData);
		//获取页面数据
		dbData.setQuantity(newData.getQuantity());
		dbData.setPrice(newData.getPrice());;
		dbData.setTotalprice(newData.getTotalprice());
		dbData.setSupplierid(newData.getSupplierid());
		
		bomDetailDao.Store(dbData);

	}
	
	/*
	 * 更新供应商单价
	 */
	@SuppressWarnings("unchecked")
	private void updatePriceSupplier(
			String materialId,
			String supplierId,
			String price ) throws Exception {
		
		List<B_PriceSupplierData> priceList = null;
		B_PriceSupplierData pricedt = null;
		B_PriceSupplierDao dao = new B_PriceSupplierDao();

		String where ="materialId= '" + materialId + "'" + 
				" AND supplierId = '" + supplierId + "'" + 
				" AND deleteFlag = '0' ";		

		priceList = (List<B_PriceSupplierData>)dao.Find(where);
		
		if(priceList != null && priceList.size() > 0){
			
			pricedt = priceList.get(0);	

			if(!price.equals(pricedt.getPrice())){//价格有变动的话,更新为最新价格
				
				//处理共通信息					
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"PriceSupplierUpdate",userInfo);
				copyProperties(pricedt,commData);
				
				pricedt.setPrice(price);
				
				dao.Store(pricedt);
			}
		}
				
	}
	
	/*
	 * BOM详情删除处理
	 */
	private void deleteBomDetail(
			List<B_BomDetailData> oldDetailList) 
			throws Exception{
		
		for(B_BomDetailData detail:oldDetailList){
			
			if(null != detail){
						
				//处理共通信息	
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,"BomDetailDelete",userInfo);
				copyProperties(detail,commData);
				
				bomDetailDao.Store(detail);
			}
		}
	}

	/*
	 * 更新Order表的状态:待评审
	 */
	private void updateOrderByYSId(String YSId) throws Exception{
		
		//确认Order表是否存在
		
		B_OrderDetailDao dao = new B_OrderDetailDao();
		B_OrderDetailData dbData = getOrderByYSId(dao,YSId);
		
		if(dbData != null){				
			//update处理
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"BomInsert",userInfo);
			
			copyProperties(dbData,commData);
			dbData.setStatus(Constants.ORDER_STS_1);

			dao.Store(dbData);				
		}	

	}
	
	@SuppressWarnings("unchecked")
	public B_OrderDetailData getOrderByYSId(
			B_OrderDetailDao dao,
			String YSId ) throws Exception {
		
		List<B_OrderDetailData> dbList = null;
		
		String where = " YSId = '"+YSId +"'" + " AND deleteFlag = '0' ";
						
		dbList = (List<B_OrderDetailData>)dao.Find(where);
		
		if(dbList != null & dbList.size() > 0)
			return dbList.get(0);		
		
		return null;
	}

	/*
	 * 取得耀升编号的流水号
	 */
	private void getBomIdByMaterialId(String materialId) 
			throws Exception {
		
		dataModel = new BaseModel();
		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomIdByMaterialId");
		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件   
		userDefinedSearchCase.put("keywords1", materialId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
				
		int newSubid = code + 1;
		
		String codeFormat = String.format("%03d", newSubid); 
		//例:I01.D019SWC001.00.001
		String bomId = materialId +"."+ codeFormat;
		
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(codeFormat);
		
		reqModel.setBomPlan(bomPlanData);
	}	
	
	private B_BomPlanData getBomByRecordId(String key) throws Exception {
		
		bomPlanData = new B_BomPlanData();
				
		try {
			bomPlanData.setRecordid(key);
			bomPlanData = (B_BomPlanData)bomPlanDao.FindByPrimaryKey(bomPlanData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			bomPlanData = null;
		}
		
		return bomPlanData;
	}

	public Model createBomPlan() throws Exception {
 
		getOrderDetailByYSId();

		String bomId = request.getParameter("bomId");
		String materialId = request.getParameter("materialId");  
		
		getBomIdByMaterialId(materialId);

		reqModel.setManageRateList(
				util.getListOption(DicUtil.MANAGEMENTRATE, ""));
		
		return model;
		
	}
	
	public Model copyBomPlan() throws Exception {		

		String bomId = request.getParameter("bomId");
		
		String materialId = "";  
		
		if(bomId != null && bomId.length() > 4){
			materialId = bomId.substring(0,bomId.length() - 4);
		}
		
		getBomDetailView(bomId);		
		getBomIdByMaterialId(materialId);

		reqModel.setManageRateList(
				util.getListOption(DicUtil.MANAGEMENTRATE, ""));
		
		return model;
		
	}
	

	public Model editBomPlan() throws Exception {

		String bomId = request.getParameter("bomId");
		getBomDetailView(bomId);

		reqModel.setManageRateList(
				util.getListOption(DicUtil.MANAGEMENTRATE, ""));
		
		return model;
		
	}

	public Model showBomDetail() throws Exception {

		String bomId = request.getParameter("bomId");
		getBomDetailView(bomId);
		
		return model;
		
	}
		
	public Model insertAndView() throws Exception {

		String bomId = insert();
		getBomDetailView(bomId);
		
		return model;
		
	}
	
	public Model updateAndView() throws Exception {

		String bomId = update();
		getBomDetailView(bomId);
		
		return model;
		
	}
}
