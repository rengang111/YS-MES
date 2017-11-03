package com.ys.business.service.order;

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
import com.ys.business.action.model.order.QuotationModel;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_QuotationDao;
import com.ys.business.db.dao.B_QuotationDetailDao;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_QuotationData;
import com.ys.business.db.data.B_QuotationDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class QuotationService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	
	private B_QuotationData quotData;
	private B_QuotationDetailData quotDetailData;
	private B_QuotationDao dao;
	private B_QuotationDetailDao detailDao;
	private QuotationModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public QuotationService(){
		
	}

	public QuotationService(Model model,
			HttpServletRequest request,
			QuotationModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_QuotationDao();
		this.detailDao = new B_QuotationDetailDao();
		this.quotData = new B_QuotationData();
		this.model = model;
		this.reqModel = reqModel;
		this.dataModel = new BaseModel();
		this.request = request;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		
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
	

	//报价BOM:客户报价
	public Model createQuotation() throws Exception {

		String materialId = request.getParameter("materialId");	
		String baseBomId = request.getParameter("bomId");	
		//取得最新的BOM单价		
		String parentId = request.getParameter("parentId");		
		
		getProductById(materialId);

		getBaseBomDetail(baseBomId);
		
		//取得最新的报价BOM编号
		getBomIdByParentId(parentId);
	
		//设置货币下拉框		
		reqModel.setCurrencyList(
				util.getListOption(DicUtil.CURRENCY, ""));	
		
		return model;
		
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

	}
	
	private B_QuotationData getBomIdByParentId(String parentId) 
			throws Exception {
		
		String bomId = null;
		dataModel = new BaseModel();
		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getMAXQuotationId");
		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件   
		userDefinedSearchCase.put("keywords1", parentId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
			
		
		bomId = BusinessService.getBidBOMFormatId(parentId,code, true);
		
		quotData.setBomid(bomId);
		quotData.setSubid(String.valueOf( code+1 ));
		quotData.setParentid(parentId);
		
		reqModel.setBomPlan(quotData);

		return quotData;
	}

	//
	public HashMap<String, Object> getBaseBomDetail(
			String bomId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBaseBomDetailByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("material",dataModel.getYsViewData().get(0));
			model.addAttribute("materialDetail",dataModel.getYsViewData());
			quotData.setRecordid(dataModel.getYsViewData().get(0).get("recordId"));	
			
		}	
				
		return HashMap;
	}
	
	//
	public HashMap<String, Object> getQuotationDetail(
			String bomId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getQuotationDetail");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("material",dataModel.getYsViewData().get(0));
			model.addAttribute("materialDetail",dataModel.getYsViewData());
			quotData.setRecordid(dataModel.getYsViewData().get(0).get("recordId"));	
			
		}	
				
		return HashMap;
	}
	
	//报价BOM:客户报价
	public Model editQuotation() throws Exception {
		String materialId = request.getParameter("materialId");	
		String bomId = request.getParameter("bomId");	
		String subId = request.getParameter("subId");	
		//取得最新的BOM单价		
		String accessFlg = Constants.ACCESSFLG_1;//新建/编辑标识		
	
		getProductById(materialId);

		getQuotationDetail(bomId);		
		
		//取得最新的报价BOM编号
		quotData.setBomid(bomId);
		quotData.setSubid(subId);
		quotData.setMaterialid(materialId);
		reqModel.setBomPlan(quotData);
		reqModel.setAccessFlg(accessFlg);
	
		//设置货币下拉框		
		reqModel.setCurrencyList(
				util.getListOption(DicUtil.CURRENCY, ""));	
		
		return model;
		
	}	
	

	public Model insertQuotation() throws Exception {
		
		insertBidBom();
		String materialId = reqModel.getBomPlan().getMaterialid();
		
		//取得产品信息
		getProductDetail(materialId);
		
		return model;
		
	}
	

	private String insertBidBom() throws Exception  {

		String bomid="";
		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_QuotationData reqData = (B_QuotationData)reqModel.getBomPlan();
			B_BomDetailData reqDtlDt = (B_BomDetailData)reqModel.getBomDetail();
			
			//插入BOM基本信息
			reqData.setBomtype(Constants.BOMTYPE_Q);//BOM类别
			updateBomPlan(reqData);
			
			updateBaseBom(reqData);
			
			//插入BOM详情
			//首先删除DB中的BOM详情
			bomid = reqData.getBomid();
			String where = " bomId = '"+bomid +"'";
			deleteBomDetail(where);
			
			List<B_QuotationDetailData> reqDataList = reqModel.getBomDetailLines();
			
			for(B_QuotationDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data.getMaterialid() != null && !("").equals(data.getMaterialid())){
					
					data.setBomid(bomid);
					data.setProductmodel(reqDtlDt.getProductmodel());
					insertBomDetail(data,false);	
					
				}	
			
			}
			
			ts.commit();
		}
		catch(Exception e) {
			System.out.print(e.getMessage());
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return bomid;
		
	}	

	/*
	 * BOM详情删除处理
	 */
	private void deleteBomDetail(String where) {
		
		try{
			detailDao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
		}		
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
	
	/*
	 * BOM详情插入处理
	 * 
	 * accessFlg:true 基础BOM做成
	 */
	private void insertBomDetail(
			B_QuotationDetailData detailData,
			boolean accessFlg) throws Exception{
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"BomDetailInsert",userInfo);

		copyProperties(detailData,commData);
		guid = BaseDAO.getGuId();
		detailData.setRecordid(guid);
		
		if(accessFlg){
			detailData.setPrice(null);//基础BOM不存单价
			detailData.setTotalprice(null);//基础BOM不存单价
		}
		
		detailDao.Create(detailData);

	}
	
	/*
	 * BOM基本信息更新处理
	 */
	private void updateBomPlan(B_QuotationData reqBom) 
			throws Exception{

		String bomId = reqBom.getBomid();
		
		//取得更新前数据		
		quotData = BomPlanExistCheck(bomId);					
		
		if(null != quotData){

			//获取页面数据
			copyProperties(quotData,reqBom);
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"BomPlanUpdate",userInfo);
			copyProperties(quotData,commData);
			
			dao.Store(quotData);
			
		}else{
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"BomPlanInsert",userInfo);
			copyProperties(reqBom,commData);

			guid = BaseDAO.getGuId();
			reqBom.setRecordid(guid);
			
			dao.Create(reqBom);	
		}
	}
	

	private void updateBaseBom(B_QuotationData reqBom) 
			throws Exception{

		String bomId = BusinessService.getBaseBomId(reqBom.getMaterialid())[1];
		
		//取得更新前数据		
		B_BomPlanData quotData = baseBomExistCheck(bomId);					
		B_BomPlanDao dao = new B_BomPlanDao();
		if(null != quotData){

			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"BomPlanUpdate",userInfo);
			copyProperties(quotData,commData);
			quotData.setRebaterate(reqBom.getRebaterate());//退税率
			
			dao.Store(quotData);
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private B_QuotationData BomPlanExistCheck(String bomId) throws Exception {
		
		List<B_QuotationData> dbList = null;
		quotData = null;
		try {
			String where = "bomId = '" + bomId
				+ "' AND  deleteFlag = '0' ";
			dbList = (List<B_QuotationData>)dao.Find(where);
			if ( dbList == null || dbList.size() > 0 )
				quotData = dbList.get(0);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return quotData;
	}
	

	@SuppressWarnings("unchecked")
	private B_BomPlanData baseBomExistCheck(String bomId) throws Exception {
		
		List<B_BomPlanData> dbList = null;
		B_BomPlanData quotData = null;
		B_BomPlanDao dao = new B_BomPlanDao();
		try {
			String where = "bomId = '" + bomId
				+ "' AND  deleteFlag = '0' ";
			dbList = (List<B_BomPlanData>)dao.Find(where);
			if ( dbList == null || dbList.size() > 0 )
				quotData = dbList.get(0);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return quotData;
	}
	
	
	public void deleteQuotation() throws Exception {		
													
		try {													
			String recordid = request.getParameter("recordId");									
			quotData.setRecordid(recordid);
			dao.Remove(quotData);		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	

	public HashMap<String, Object> showQuotationBomDetail() throws Exception {

		String materialId = request.getParameter("materialId");
		
		return getQuotation(materialId);		
	}
	
	public HashMap<String, Object> getQuotation(String bomId) throws Exception {
		
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
	

		dataModel.setQueryName("getQuotation");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("materialId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	
			
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());
		if(dataModel.getRecordCount() > 0)
			model.addAttribute("lastPrice",dataModel.getYsViewData().get(0));//最新报价
		
		return modelMap;
	}
	
	public HashMap<String, Object> showBaseBomDetail() throws Exception {

		String materialId = request.getParameter("materialId");
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		return getBaseBomDetail(bomId);	
	}
}

