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
import com.ys.business.action.model.order.PurchasePlanModel;
import com.ys.business.db.dao.B_BomDetailDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class PurchasePlanService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_PurchasePlanData purchaseData;
	private B_PurchasePlanDao purchaseDao;
	private B_BomDetailDao bomDetailDao;
	private PurchasePlanModel reqModel;
	private BaseModel dataModel;
	private Model model;
	private UserInfo userInfo;
	
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public PurchasePlanService(){
		
	}

	public PurchasePlanService(Model model,
			HttpServletRequest request,
			PurchasePlanModel reqModel,
			UserInfo userInfo){
		
		this.purchaseDao = new B_PurchasePlanDao();
		this.purchaseData = new B_PurchasePlanData();
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

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
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

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getOrderList");
		
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
	
	public Model getBomDetailView(String YSId) 
			throws Exception {
		
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
	public List<B_BomDetailData> getBomDetailByBomId(
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
	
	
	
	public HashMap<String, Object> getOrderSubIdByParentId(
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
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */	
	public String insert() throws Exception  {

		String YSId="";
		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
			
			//订单采购方案
			YSId = reqModel.getYSId();
			
			//首先删除DB中的BOM详情
			String where = " YSId = '"+YSId +"'";
			deletePlanDetail(where);
			
			List<B_PurchasePlanData> reqDataList = reqModel.getDetail();
			
			for(B_PurchasePlanData data:reqDataList ){

				data.setYsid(YSId);
				insertPurchasePlan(data);
			
			}
			
			updateOrderByYSId(YSId);
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return YSId;
		
	}	

	/*
	 * BOM基本信息insert处理
	 */
	public void insertPurchasePlan(
			B_PurchasePlanData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchasePlanInsert",userInfo);

		copyProperties(data,commData);

		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		purchaseDao.Create(data);	

	}	
	
	
	


	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
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
	public void updatePriceSupplier(
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
	public void deletePlanDetail(String where) 
			throws Exception{
		
		try{
			purchaseDao.RemoveByWhere(where);
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
			dbData.setStatus(Constants.ORDER_STS_3);

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

	public Model createBomPlan() throws Exception {
 
		getOrderDetailByYSId();

		String bomId = request.getParameter("bomId");

		
		return model;
		
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
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();

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
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	

	public Model editBomPlan() throws Exception {

		String bomId = request.getParameter("bomId");
		getBomDetailView(bomId);

			
		return model;
		
	}

	public Model showBomDetail() throws Exception {

		String YSId = request.getParameter("YSId");
		getBomDetailView(YSId);
		
		return model;
		
	}
	
	public Model insertAndView() throws Exception {

		String YSId = insert();
		//getBomDetailView(YSId);
		
		return model;
		
	}
	

}
