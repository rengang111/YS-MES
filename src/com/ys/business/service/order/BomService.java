package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
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
import com.ys.business.db.dao.B_PriceSupplierHistoryDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.B_PriceSupplierHistoryData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

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
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HttpSession session;
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
		dataModel = new BaseModel();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		
	}
	
	public HashMap<String, Object> getBomList() throws Exception {
		
		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryName("getBomList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);	 

		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		modelMap.put("data", dataModel.getYsViewData());
		//model.addAttribute("bomPlan",  modelMap.get(0));
		//model.addAttribute("bomDetail", modelMap);
		
		return modelMap;
	}
	

	public HashMap<String, Object> getBomList(String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

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
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	public HashMap<String, Object> getBaseBomList(String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

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

		dataModel.setQueryName("getBaseBomList");
		
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
	

	public Model getCopyBomList() throws Exception {
		
		dataModel = new BaseModel();
		
		//String key1 = getJsonData(data, "keyword1").toUpperCase();
		//String key2 = getJsonData(data, "keyword2").toUpperCase();
		
		String key1 = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getCopyBomList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("keyword1", key1);
		//userDefinedSearchCase.put("keyword2", key2);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);	 
				
		//modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("bomPlan",  modelMap.get(0));
		model.addAttribute("bomDetail", modelMap);
		
		return model;
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

	public String getOrderDetail(String YSId) 
			throws Exception {
	
		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keyword1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("bomPlan",  modelMap.get(0));
		//model.addAttribute("bomDetail", modelMap);
		String materialId = modelMap.get(0).get("materialId");
		
		return materialId;
				
	}
	
	public Model getBomDetail(String YSId) throws Exception {
	
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomDetailListByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("bomPlan",  modelMap.get(0));
		model.addAttribute("bomDetail", modelMap);
				
		return model;
	}
	public HashMap<String, Object> getBaseBomDetail(String bomId) throws Exception {

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
			//编辑用
			model.addAttribute("material",dataModel.getYsViewData().get(0));
			model.addAttribute("materialDetail",dataModel.getYsViewData());
		}else{
			return null;
		}		
				
		return HashMap;
	}

	public HashMap<String, Object> getQuotationBomDetail(String bomId) throws Exception {
	
				
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
	

		dataModel.setQueryName("getQuotationBom");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("materialId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	
			
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	public void getBomInfo(String YSId) throws Exception {
	
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomDetailListByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keywords1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);
				
	}
	public String getOrderDetailByYSId(String YSId) throws Exception{
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getOrderDetailByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keywords1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("order",  modelMap.get(0));
		
		String materialId = modelMap.get(0).get("productId");
		
		return materialId;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean BomPlanExistCheck(String YSId) throws Exception {
		
		boolean blRtn = true;
		List<B_BomPlanData> dbList = null;
		
		try {
			//status=1:待评审,表明该方案已存在
			String where = "YSId = '" + YSId
				+ "' AND  deleteFlag = '0' ";
			dbList = (List<B_BomPlanData>)bomPlanDao.Find(where);
			if ( dbList == null || dbList.size() == 0 )
				blRtn = false;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			blRtn = false;
		}
		
		return blRtn;
	}
	
	/*
	 * 
	 */
	public HashMap<String, Object> getMaterialList() throws Exception {
		
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
	public void getProductById(String materialId) throws Exception {

		dataModel.setQueryName("getProductById");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("materialId", materialId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		model.addAttribute("product", dataModel.getYsViewData().get(0));
			
		
		//return modelMap;
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
	
	public void getMaterialbyProductModel() throws Exception {

		String key1 = request.getParameter("model");

		if(key1 != null && key1.trim().length() > 1){
			key1 = key1.toUpperCase();
		}else{
			return;
		}
		dataModel.setQueryName("getMaterialbyProductModel");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("shareModel", key1);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", key1);
		baseQuery.getYsQueryData(sql);
		//baseQuery.getYsQueryData(0, 0);	 		
		
		model.addAttribute("materialDetail", dataModel.getYsViewData());
		model.addAttribute("productMode",key1);
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
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	private String insertBaseBom() throws Exception  {

		String bomid="";
		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_BomPlanData reqData = (B_BomPlanData)reqModel.getBomPlan();
			B_BomDetailData reqDtlDt = (B_BomDetailData)reqModel.getBomDetail();
			
			//插入BOM基本信息
			updateBomPlan(reqData);
			
			//插入BOM详情
			//首先删除DB中的BOM详情
			bomid = reqData.getBomid();
			String where = " bomId = '"+bomid +"'";
			deleteBomDetail(where);
			
			List<B_BomDetailData> reqDataList = reqModel.getBomDetailLines();
			
			for(B_BomDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data.getMaterialid() != null && !("").equals(data.getMaterialid())){
					
					data.setBomid(bomid);
					data.setProductmodel(reqDtlDt.getProductmodel());
					insertBomDetail(data);	
					
					//供应商单价修改
					String supplierId = data.getSupplierid();
					String materialId = data.getMaterialid();
					String price = data.getPrice();
					updatePriceSupplier(materialId,supplierId,price);
				}	
			
			}
			
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
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"BaseBomInsert",userInfo);

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
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"BomDetailInsert",userInfo);

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
		String YSId = "";
		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
						
			B_BomPlanData reqBom = (B_BomPlanData)reqModel.getBomPlan();

			bomId = reqBom.getBomid();
			YSId = reqBom.getYsid();
			
			//订单基本信息 新增/更新 处理
			updateBomPlan(reqBom);
			
			//订单详情更新处理
			//首先删除DB中的BOM详情
			String where = " bomId = '"+bomId +"'";
			deleteBomDetail(where);
			
			//新增页面的BOM详情		
			List<B_BomDetailData> reqDataList = reqModel.getBomDetailLines();
			
			for(B_BomDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && data.getMaterialid() != null && 
					!data.getMaterialid().trim().equals("")){
					
					data.setBomid(bomId);
					insertBomDetail(data);					
				}			
			}
			//更新订单状态为待评审
			updateOrderByYSId(YSId);
			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
		}
				
		return YSId;
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

			//获取页面数据
			copyProperties(bomPlanData,reqBom);
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"BomPlanUpdate",userInfo);
			copyProperties(bomPlanData,commData);
			
			bomPlanDao.Store(bomPlanData);
			
		}else{
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"BomPlanInsert",userInfo);
			copyProperties(reqBom,commData);

			guid = BaseDAO.getGuId();
			reqBom.setRecordid(guid);
			reqBom.setBomtype(Constants.BOMTYPE_B);//BOM类别
			
			bomPlanDao.Create(reqBom);	
		}
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
		B_PriceSupplierHistoryData historyDt = new B_PriceSupplierHistoryData();
		B_PriceSupplierHistoryDao historyDao = new B_PriceSupplierHistoryDao();

		String where ="materialId= '" + materialId + "'" + 
				" AND supplierId = '" + supplierId + "'" + 
				" AND deleteFlag = '0' ";		

		priceList = (List<B_PriceSupplierData>)dao.Find(where);
		
		if(priceList != null && priceList.size() > 0){
			
			pricedt = priceList.get(0);	

			if(!price.equals(pricedt.getPrice())){//价格有变动的话,更新为最新价格
				
				//处理共通信息					
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"BaseBomInsert",userInfo);
				copyProperties(pricedt,commData);
				
				pricedt.setPrice(price);
				
				dao.Store(pricedt);
				
				//插入历史表
				//处理共通信息	
				copyProperties(historyDt,pricedt);
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,"BaseBomInsert",userInfo);
				copyProperties(historyDt,commData);
				
				guid = BaseDAO.getGuId();
				historyDt.setRecordid(guid);
				
				historyDao.Create(historyDt);
			}
		}
				
	}
	
	/*
	 * BOM详情删除处理
	 */
	private void deleteBomDetail(String where) {
		
		try{
			bomDetailDao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
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
					"OrderStatusUpdate",userInfo);
			
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
	private B_BomPlanData getBomIdByMaterialId(String materialId) 
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
				
		String bomId = BusinessService.getBOMFormatId(materialId,code, true);
		
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(String.valueOf( code+1 ));
		bomPlanData.setMaterialid(materialId);
		
		reqModel.setBomPlan(bomPlanData);

		return bomPlanData;
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
	
	public void getProductDetail(String productId) throws Exception {

		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", productId);
		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getProductList");
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();	 
		
		model.addAttribute("product", dataModel.getYsViewData().get(0));

	}
	
	public Model copyBomPlan() throws Exception {		

		String bomId = request.getParameter("bomId");
		
		String materialId = "";  
		
		if(bomId != null && bomId.length() > 4){
			materialId = bomId.substring(0,bomId.length() - 4);
		}
		
		getOrderDetail(bomId);		
		getBomIdByMaterialId(materialId);
		
		return model;
		
	}
	public Model createBaseBom() throws Exception {

		String materialId = request.getParameter("materialId");	
		//取得该产品的新BOM编号
		String bomId = BusinessService.getBaseBomId(materialId);
		
		String accessFlg = request.getParameter("accessFlg");//新建/编辑标识
		
		if(accessFlg != null && accessFlg != ""){
			//编辑
			//取得产品信息
			getProductById(materialId);

			getBaseBomDetail(bomId);
		}
		
		//新建
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(BusinessConstants.FORMAT_00);
		bomPlanData.setMaterialid(materialId);		
		reqModel.setBomPlan(bomPlanData);

		//取得产品信息
		getProductById(materialId);
		
		return model;
		
	}
	
	public Model getProductModel() throws Exception {

		String materialId = request.getParameter("materialId");	
		//取得产品信息
		String bomId = BusinessService.getBaseBomId(materialId);
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(BusinessConstants.FORMAT_00);
		bomPlanData.setMaterialid(materialId);
		reqModel.setBomPlan(bomPlanData);
		
		getProductById(materialId);		

		getMaterialbyProductModel();
		
		return model;
		
	}
	
	public HashMap<String, Object> showBaseBomDetail() throws Exception {

		String materialId = request.getParameter("materialId");
		String bomId = BusinessService.getBaseBomId(materialId);
		
		return getBaseBomDetail(bomId);		
	}
	public HashMap<String, Object> showQuotationBomDetail() throws Exception {

		String materialId = request.getParameter("materialId");
		
		return getQuotationBomDetail(materialId);		
	}
		
	public Model createBomPlan() throws Exception {

		String YSId = request.getParameter("YSId");
		String materialId = request.getParameter("materialId");	
	
		//取得该产品的新BOM编号	
		getBomIdByMaterialId(materialId);	
		
		//取得订单信息
		getOrderDetailByYSId(YSId);

		//设置经管费率下拉框		
		reqModel.setManageRateList(
				util.getListOption(DicUtil.MANAGEMENTRATE, ""));
		
		return model;
		
	}

	public Model editBomPlan() throws Exception {

		String YSId = request.getParameter("YSId");
		
		//取得产品基本信息
		getOrderDetailByYSId(YSId);	
		
		//取得BOM的详细信息
		getBomDetail(YSId);
		
		//设置经管费率下拉框		
		reqModel.setManageRateList(
				util.getListOption(DicUtil.MANAGEMENTRATE, ""));
				
		return model;
		
	}
	
	public Model changeBomPlanAdd() throws Exception {

		String materialId = request.getParameter("materialId");
		String oldBomId = request.getParameter("bomId");

		//取得该产品的新BOM编号
		String newBomId = BusinessService.getBaseBomId(materialId);
		
		//取得所选BOM的详细信息
		getBaseBomDetail(oldBomId);
		
		//返回给页面刚才选择的BOM编号
		model.addAttribute("selectedBomId",oldBomId);
		//新建
		bomPlanData.setBomid(newBomId);
		bomPlanData.setSubid(BusinessConstants.FORMAT_00);
		bomPlanData.setMaterialid(materialId);		
		reqModel.setBomPlan(bomPlanData);

		//取得产品信息
		getProductById(materialId);
	
		return model;
		
	}
	
	public Model changeBomPlanEdit() throws Exception {

		String YSId = request.getParameter("YSId");
		String bomId = request.getParameter("bomId");
		String orderYSId = request.getParameter("orderYSId");

		//取得本次订单的基本信息
		getOrderDetailByYSId(orderYSId);

		//取得本次BOM的基本信息
		getBomInfo(orderYSId);
		model.addAttribute("bomPlan",  modelMap.get(0));
		
		//取得所选BOM的详细信息
		getBomInfo(YSId);	
		model.addAttribute("bomDetail", modelMap);
		
		//设置经管费率下拉框		
		reqModel.setManageRateList(
				util.getListOption(DicUtil.MANAGEMENTRATE, ""));
		
		return model;
		
	}
	public Model showBomDetail() throws Exception {

		String YSId = request.getParameter("YSId");
		getBomDetail(YSId);
		
		return model;
		
	}
	

		
	public Model insertAndView() throws Exception {

		String bomId = insert();
		getBomDetail(bomId);
		
		return model;
		
	}
	
	public Model insertBaseBomAndView() throws Exception {
	
		String bomId = insertBaseBom();
		String materialId = reqModel.getBomPlan().getMaterialid();
		
		//取得产品信息
		getProductDetail(materialId);
		//getBaseBomDetail(bomId);
		
		return model;
		
	}
	public Model updateAndView() throws Exception {

		String YSId = update();
		getBomDetail(YSId);
		
		return model;
		
	}
	
	public void updateBomPlan() {
		
		try {
			
			B_BomPlanData data = new B_BomPlanData();
			
			String costRate  = request.getParameter("costRate");
			String totalCost = request.getParameter("totalCost");
			String recordId  = request.getParameter("recordId");
			
			data.setRecordid(recordId);
			data.setManagementcostrate(costRate);
			data.setTotalcost(totalCost);

			updateBomPlan(data);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			
		}
		
	}
}
