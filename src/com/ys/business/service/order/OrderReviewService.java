package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.OrderReviewModel;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_OrderReviewDao;
import com.ys.business.db.dao.S_systemConfigDao;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_OrderReviewData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.db.data.S_systemConfigData;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

@Service
public class OrderReviewService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	
	private HttpServletRequest request;
	
	private B_OrderReviewDao dao;
	private CommFieldsData commData;
	private OrderReviewModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public OrderReviewService(){
		
	}

	public OrderReviewService(Model model,
			HttpServletRequest request,
			OrderReviewModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_OrderReviewDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/orderreviewquerydefine");
		
	}
	public HashMap<String, Object> getReviewList( 
			String data) throws Exception {
		
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
		
		dataModel.setQueryName("getreviewlist");
		
		baseQuery = new BaseQuery(request, dataModel);
		
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
	
	public Model getReviewDetailView(String YSId) 
			throws Exception {
				
		dataModel.setQueryName("getReviewAndDetail");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keyword3", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("bomPlan",  modelMap.get(0));
		model.addAttribute("bomDetail", modelMap);
				
		return model;
	}
	

	private void getOrderAndBomByYSId(String YSId) throws Exception{
		
		dataModel.setQueryName("getOrderAndBomByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keyword1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsQueryData(0, 0);
		
		model.addAttribute("bomPlan", dataModel.getYsViewData().get(0));	
				
	}
	
	
	@SuppressWarnings("unchecked")
	private B_OrderReviewData getReviewByBomId(
			String YSId ) throws Exception {
		
		List<B_OrderReviewData> dbList = null;
		
		String where = " YSId = '"+YSId +"'" + " AND deleteFlag = '0' ";
						
		dbList = (List<B_OrderReviewData>)dao.Find(where);
		
		if(dbList != null & dbList.size() > 0)
			return dbList.get(0);		
		
		return null;
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
	
	private String updateApproveByBomId() throws Exception{
		
		String YSId = "";
		//确认BOM表是否存在
		String bomId = request.getParameter("bomId");
		
		B_OrderReviewData dbData = getReviewByBomId(bomId);
		
		if(dbData != null){	
			YSId = dbData.getYsid();
			//update处理
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"OrderReviewUpdate",userInfo);
			
			copyProperties(dbData,commData);
			dbData.setStatus(Constants.APPROVE_OK);
			dbData.setReviewman(userInfo.getUserId());
			dbData.setReviewdate(CalendarUtil.fmtYmdDate());

			dao.Store(dbData);				
		}
		
		return YSId;
	}

	/*
	 * 更新Order表的状态:已评审
	 */
	private void updateOrderByYSId(String YSId) throws Exception{
		
		//确认Order表是否存在
		
		B_OrderDetailDao dao = new B_OrderDetailDao();
		B_OrderDetailData dbData = getOrderByYSId(dao,YSId);
		
		if(dbData != null){				
			//update处理
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"OrderReviewApprove",userInfo);
			
			copyProperties(dbData,commData);
			dbData.setStatus(Constants.ORDER_STS_2);

			dao.Store(dbData);				
		}	

	}
	/*
	 * 更新汇率,退税率等系统信息
	 */
	@SuppressWarnings("unchecked")
	private void updateSystemConfig(
			String exRate,
			String raRate) throws Exception {
				
		S_systemConfigDao dao = new S_systemConfigDao();
		S_systemConfigData dbData = new S_systemConfigData();
		List<S_systemConfigData> dbList = null;
		String where = null;
		
		//更新汇率
		if(exRate != null && !("").equals(exRate)){
			where = "sysKey='exchangeRate'";
			dbList = (List<S_systemConfigData>)dao.Find(where);
			
			if(dbList!=null && dbList.size() >0){

				dbData = dbList.get(0);
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"OrderReviewApprove",userInfo);	
				copyProperties(dbData,commData);
				dbData.setSysvalue(exRate);

				dao.Store(dbData);	
			}else{		
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"OrderReviewApprove",userInfo);	
				copyProperties(dbData,commData);	
				dbData.setSyskey("exchangeRate");
				dbData.setSysvalue(exRate);
				dao.Create(dbData);
			}
			dbList.clear();

		}

		//更新退税率
		dbData = new S_systemConfigData();
		if(exRate != null && !("").equals(raRate)){
			where = "sysKey='rebateRate'";
			dbList = (List<S_systemConfigData>)dao.Find(where);
			
			if(dbList!=null && dbList.size() >0){

				dbData = dbList.get(0);	
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"OrderReviewApprove",userInfo);	
				copyProperties(dbData,commData);	
				dbData.setSysvalue(raRate);

				dao.Store(dbData);	

			}else{			
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"OrderReviewApprove",userInfo);	
				copyProperties(dbData,commData);
				dbData.setSyskey("rebateRate");
				dbData.setSysvalue(raRate);
				
				dao.Create(dbData);}
			
		}

	}
	
	/*
	 * 1.订单评审数据新增处理(一条数据)
	 * 2.
	 */
	public String insert() throws Exception  {
					
		B_OrderReviewData reqData = (B_OrderReviewData)reqModel.getReview();

		String YSId = reqData.getYsid();
		ts = new BaseTransaction();
		
		try{
			ts.begin();
			
			//确认BOM表是否存在
			B_OrderReviewData dbData = getReviewByBomId(YSId);
			
			if(dbData != null){
				
				copyProperties(dbData,reqData);
				//update处理
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"OrderReviewUpdate",userInfo);
	
				copyProperties(dbData,commData);
				dbData.setStatus(Constants.APPROVE_DEF);
				
				dao.Store(dbData);
				
			}else{
				
				//insert处理
				guid = BaseDAO.getGuId();
				reqData.setRecordid(guid);
				
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"OrderReviewInsert",userInfo);
				
				copyProperties(reqData,commData);			
				reqData.setFinishdate(CalendarUtil.fmtYmdDate());
				reqData.setStatus(Constants.APPROVE_DEF);
				
				dao.Create(reqData);
				
			}	
			
			//更新汇率和退税率
			String exRate = reqData.getExchangerate();
			String reRate = reqData.getRebaterate();
			
			updateSystemConfig(exRate,reRate);
	
			ts.commit();
			
		}catch(Exception e) {
			
			ts.rollback();
		}
		
		return YSId;
		
	}	
	
	public Model insertAndView() throws Exception {

		String bomId = insert();
		getReviewDetailView(bomId);
		
		return model;
		
	}
	
	public String createReview() throws Exception {
		 
		String YSId = request.getParameter("YSId");
		B_OrderReviewData db = getReviewByBomId(YSId);
		String urlType = "";
		
		if(db != null){
			//评审存在的话,显示查看页面;
			getReviewDetailView(YSId);
			urlType = "view";
		}else{
			//没有评审的话,显示新建页面;
			getOrderAndBomByYSId(YSId);
			urlType = "add";			
		}
		
		reqModel.setCurrencyList(
				util.getListOption(DicUtil.CURRENCY, ""));
		
		return urlType;
		
	}
	
	public Model editReview() throws Exception {
		 
		String YSId = request.getParameter("YSId");
		B_OrderReviewData db = getReviewByBomId(YSId);
		
		if(db != null){
			//评审存在的话,显示其内容;
			getReviewDetailView(YSId);
		}else{
			//没有评审的话,新建评审;
			getOrderAndBomByYSId(YSId);			
		}
		
		reqModel.setCurrencyList(
				util.getListOption(DicUtil.CURRENCY, ""));
		
		return model;
		
	}
	
	public Model approvAndView() throws Exception {
		 		
		try {
			ts = new BaseTransaction();
			ts.begin();

			String bomId = request.getParameter("bomId");
			String YSId = updateApproveByBomId();
			
			updateOrderByYSId(YSId);
			
			getReviewDetailView(bomId);
			
			ts.commit();
		}catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
	
		return model;
		
	}
}
