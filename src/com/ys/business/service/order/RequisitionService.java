package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.action.model.order.RequisitionModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.B_RequisitionDao;
import com.ys.business.db.dao.B_RequisitionDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchasePlanData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_PurchaseStockInDetailData;
import com.ys.business.db.data.B_RequisitionData;
import com.ys.business.db.data.B_RequisitionDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class RequisitionService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_RequisitionDao dao;
	private B_RequisitionDetailDao detailDao;
	private RequisitionModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public RequisitionService(){
		
	}

	public RequisitionService(Model model,
			HttpServletRequest request,
			HttpSession session,
			RequisitionModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_RequisitionDao();
		this.detailDao = new B_RequisitionDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	public HashMap<String, Object> doSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		String[] keyArr = getSearchKey(Constants.FORM_REQUISITION,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");	
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		baseQuery.getYsQueryData(sql,iStart, iEnd);	 
				
		if ( iEnd > dataModel.getYsViewData().size()){			
			iEnd = dataModel.getYsViewData().size();			
		}
		
		modelMap.put("sEcho", sEcho); 		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());		
		modelMap.put("data", dataModel.getYsViewData());
		modelMap.put("keyword1",key1);	
		modelMap.put("keyword2",key2);		
		
		return modelMap;		

	}
	

	public void addInit() throws Exception {

		String YSId = request.getParameter("YSId");
		String requisitionId = request.getParameter("requisitionId");
		if(YSId== null || ("").equals(YSId))
			return;
		
		//订单详情
		getOrderDetail(YSId);
		model.addAttribute("requisitionId",requisitionId);
	
	}


	public void updateInit() throws Exception {

		String YSId = request.getParameter("YSId");
		//String requisitionId = request.getParameter("requisitionId");
		
		//订单详情
		getOrderDetail(YSId);
		//领料单
		getRequisitionDetail();
	
	}
	public HashMap<String, Object> showDetail() throws Exception {

		String YSId = request.getParameter("YSId");
		if(YSId == null || ("").equals(YSId))
			return null;
		//物料需求表
		return getPurchasePlan(YSId);
	}
	public void updateAndView() throws Exception {

		String YSId = update();

		//订单详情
		getOrderDetail(YSId);
	}
	
	public void insertAndView() throws Exception {

		String YSId = insert();

		//订单详情
		getOrderDetail(YSId);
	}
	

	private String update(){
		
		String YSId = "";
		ts = new BaseTransaction();

		try {
			ts.begin();
			
			B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
			List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();

			YSId = reqData.getYsid();
			//旧的领料单号
			String oldId = reqData.getRequisitionid();
			
			//领料单更新处理
			reqData = getRequisitionId(reqData);
			String requisitionid = reqData.getRequisitionid();//新的单号
			reqData.setOriginalrequisitionid(oldId);
			updateRequisition(reqData);

			//旧的明细删除处理
			deleteRequisitionDetail(YSId,oldId);
			
			//新的领料单明细						
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				//float overQuty = stringToFloat(data.getOverquantity());//超领
				
				if(quantity <= 0)
					continue;
				
				data.setRequisitionid(requisitionid);
				insertRequisitionDetail(data);
								
				//更新累计领料数量
				//updatePurchasePlan(YSId,data.getMaterialid(),quantity);
				
				//更新库存
				//updateMaterialStock(data.getMaterialid(),quantity,overQuty);
			
			}
			
			//更新订单状态:待交货
			//updateOrderDetail(YSId);
			
			
			ts.commit();			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			try {
				ts.rollback();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		return YSId;
	}
	
	private String insert(){
		
		String YSId = "";
		ts = new BaseTransaction();

		try {
			ts.begin();
			
			B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
			List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();

			//取得领料单编号
			YSId = reqData.getYsid();
			reqData = getRequisitionId(reqData);
			String requisitionid = reqData.getRequisitionid();
			
			//领料申请insert
			insertRequisition(reqData);
						
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				//float overQuty = stringToFloat(data.getOverquantity());//超领
				
				if(quantity <= 0)
					continue;
				
				data.setRequisitionid(requisitionid);
				insertRequisitionDetail(data);
								
				//更新累计领料数量
				//updatePurchasePlan(YSId,data.getMaterialid(),quantity);
				
				//更新库存
				//updateMaterialStock(data.getMaterialid(),quantity,overQuty);
			
			}
			
			//更新订单状态:待交货
			updateOrderDetail(YSId);
			
			
			ts.commit();			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			try {
				ts.rollback();
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		return YSId;
	}
	
	private void insertRequisition(
			B_RequisitionData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RequisitionInsert",userInfo);
		copyProperties(stock,commData);

		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		stock.setRequisitionuserid(userInfo.getUserId());//默认为登陆者
		stock.setRequisitiondate(CalendarUtil.fmtYmdDate());
		
		dao.Create(stock);
	}
	
	private void insertRequisitionDetail(
			B_RequisitionDetailData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RequisitionInsert",userInfo);
		copyProperties(stock,commData);

		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		
		detailDao.Create(stock);
	}
	
	@SuppressWarnings("unchecked")
	private void updatePurchasePlan(
			String YSId,
			String materialId,
			float quantity) throws Exception{
		
		String where = "YSId ='"+YSId +
				"' AND materialId ='"+ materialId +
				"' AND deleteFlag='0' ";
				
		//更新到货数量
		B_PurchasePlanDetailData data = new B_PurchasePlanDetailData();
		B_PurchasePlanDetailDao dao = new B_PurchasePlanDetailDao();
		List<B_PurchasePlanDetailData> list = 
				(List<B_PurchasePlanDetailData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}
		data = list.get(0);
		
		//计算累计领料数量
		float iAcc = stringToFloat(data.getTotalrequisition());
		float iNew = quantity + iAcc;		
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"RequriUpdate",userInfo);
		copyProperties(data,commData);
		data.setTotalrequisition(String.valueOf(iNew));
		
		dao.Store(data);		
	}
	
	//更新当前库存:领料时，减少“当前库存”,减少“待出库”,
	@SuppressWarnings("unchecked")
	private void updateMaterialStock(
			String materialId,
			float reqQuantity,
			float overQuantity) throws Exception{
	
		B_MaterialData data = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		String where = "materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_MaterialData> list = 
				(List<B_MaterialData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		//当前库存数量
		float iQuantity = stringToFloat(data.getQuantityonhand());
		//float ireqQuantity = stringToFloat(reqQuantity);				
		float iNewQuantiy = iQuantity - reqQuantity;		
		
		//待入库数量
		float istockin = stringToFloat(data.getWaitstockin());		
		//float iNewStockIn = istockin - reqQuantity;
		
		//待出库
		float waitstockout = stringToFloat(data.getWaitstockout());
		waitstockout = waitstockout - reqQuantity + overQuantity;//超领部分不计入
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float availabeltopromise = iNewQuantiy + istockin - waitstockout;
		
		data.setQuantityonhand(String.valueOf(iNewQuantiy));
		data.setWaitstockout(String.valueOf(waitstockout));
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}
		
	@SuppressWarnings("unchecked")
	private void updateOrderDetail(
			String ysid) throws Exception{
		String where = "YSId = '" + ysid  +"' AND deleteFlag = '0' ";
		List<B_OrderDetailData> list  = new B_OrderDetailDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;	
		
		//更新DB
		B_OrderDetailData data = list.get(0);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		data.setStatus(Constants.ORDER_STS_3);//待交货	
		
		new B_OrderDetailDao().Store(data);
	}
	
	
	private void updateRequisition(
			B_RequisitionData data) throws Exception{
		
		//
		B_RequisitionData db = new B_RequisitionDao(data).beanData;

		copyProperties(db,data);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"RequisitionUpdate",userInfo);
		copyProperties(db,commData);
		db.setRequisitiondate(CalendarUtil.fmtYmdDate());		
		
		new B_RequisitionDao().Store(db);
	}
	

	@SuppressWarnings("unchecked")
	private void deleteRequisitionDetail(
			String YSId,
			String requisitionId) throws Exception{
		
		B_RequisitionDetailDao dao = new B_RequisitionDetailDao();
		//
		String where = "requisitionId = '" + requisitionId  +"' AND deleteFlag = '0' ";
		List<B_RequisitionDetailData> list  = 
				new B_RequisitionDetailDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;
		
		for(B_RequisitionDetailData dt:list){
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"RequisitionDelete",userInfo);
			copyProperties(dt,commData);
			
			dao.Store(dt);
			
			//更新累计领料数量(恢复)
			String mateId = dt.getMaterialid();
			float quantity = (-1) * stringToFloat(dt.getQuantity());
			//updatePurchasePlan(YSId,mateId,quantity);
			
			//更新库存(恢复)
			float overQuty = (-1) * stringToFloat(dt.getOverquantity());
			updateMaterialStock(mateId,quantity,overQuty);
		}
		
	}

		
	public void doDelete(String recordId) throws Exception{
		
		B_ArrivalData data = new B_ArrivalData();	
															
		try {
			
			ts = new BaseTransaction();										
			ts.begin();									
			
			String removeData[] = recordId.split(",");									
			for (String key:removeData) {									
												
				data.setRecordid(key);							
				dao.Remove(data);	
				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
	}
	
	
	
	
	public B_RequisitionData getRequisitionId(
			B_RequisitionData data ) throws Exception {

		String parentId = BusinessService.getshortYearcode()+
				BusinessConstants.SHORTNAME_LL;
		dataModel.setQueryName("getMAXRequisitionId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();
		//sql里已经是MAX+1
		String subid = dataModel.getYsViewData().get(0).get("MaxSubId");
		String id =  BusinessService.getRequisitionId(parentId,subid);
		data.setRequisitionid(id);
		data.setParentid(parentId);
		data.setSubid(subid);
		
		return data;
	}
	
	public HashMap<String, Object> getPurchasePlan(String YSId) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryName("getPurchasePlanByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
			modelMap.put("data", dataModel.getYsViewData());
		}
		
		return modelMap;		
	}
	
	public HashMap<String, Object> getRequisitionHistory(
			String YSId) throws Exception {
		
		dataModel.setQueryName("getRequisitionById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	public HashMap<String, Object> getRequisitionDetail() throws Exception {

		String requisitionId = request.getParameter("requisitionId");
		
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");		
		dataModel.setQueryName("updateRequisition");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("requisitionId", requisitionId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("detail",dataModel.getYsViewData().get(0));
		
		return modelMap;		
	}
	

	public HashMap<String, Object> requisitionPrint() throws Exception {

		String requisitionId = request.getParameter("requisitionId");
		
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");		
		dataModel.setQueryName("updateRequisition");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("requisitionId", requisitionId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("detail",dataModel.getYsViewData().get(0));
		
		return modelMap;		
	}
	
	public HashMap<String, Object> getOrderDetail(
			String YSId) throws Exception {
		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		model.addAttribute("order",dataModel.getYsViewData().get(0));
		
		return modelMap;		
	}
	
	
	public HashMap<String, Object> getRequisitionById(
			String YSId) throws Exception {
		
		dataModel.setQueryName("getRequisitionById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		
	}

}
