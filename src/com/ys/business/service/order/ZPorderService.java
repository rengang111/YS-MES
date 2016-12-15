package com.ys.business.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

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
import com.ys.business.action.model.order.ZPOrderModel;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class ZPorderService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_OrderDao orderDao;
	private B_OrderDetailDao detailDao;
	private ZPOrderModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public ZPorderService(){
		
	}

	public ZPorderService(Model model,
			HttpServletRequest request,
			ZPOrderModel reqModel,
			UserInfo userInfo){
		
		this.orderDao = new B_OrderDao();
		this.detailDao = new B_OrderDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.dataModel = new BaseModel();
		this.request = request;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		
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
	

	

	@SuppressWarnings("unchecked")
	private List<B_OrderDetailData> getOrderDetailByPIId(String where){
		List<B_OrderDetailData> dbList = new ArrayList<B_OrderDetailData>();
				
		try {

			dbList = (List<B_OrderDetailData>)detailDao.Find(where);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbList = null;
		}
		
		return dbList;
	}
	
	/*
	 * 
	 */
	public HashMap<String, Object> getZPMaterialList() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryName("zzmaterialList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	
	
	/*
	 * 1.装配品库存订单新增处理(一条数据)
	 */
	@SuppressWarnings("rawtypes")
	public String insert() throws Exception  {

		ts = new BaseTransaction();
		String orderId = "";

		try {
			ts.begin();

			List code = getOrderZPYSKMAXId();

			String YSCode  = code.get(0).toString();
			String YSSubId = code.get(1).toString();	
			
			B_OrderData reqData = (B_OrderData)reqModel.getOrder();
			
			//插入订单基本信息
			insertOrder(reqData,YSCode,YSSubId);
			
			//处理订单详情数据		
			B_OrderDetailData reqDetailData = reqModel.getOrderDetail();
			
			orderId = reqData.getOrderid();
	
			insertOrderDetail(reqDetailData, YSSubId,orderId);		
						
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
		
		return orderId;
	}	

	/*
	 * 订单基本信息插入处理
	 */
	public void insertOrder(
			B_OrderData data,
			String ysCode,
			String subId) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ZPOrderInsert",userInfo);

		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setPiid(ysCode);
		data.setParentid(BusinessService.getYSKCommCode());
		data.setSubid(subId);
		data.setCustomerid(BusinessConstants.SHORTNAME_YS);
		data.setOrderid(ysCode);
		
		orderDao.Create(data);	
	}	
	
	/*
	 * 订单详情插入处理
	 */
	private void insertOrderDetail(
			B_OrderDetailData newData,
			String YSSubId ,
			String piId) throws Exception{
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ZPOrderDetailInsert",userInfo);
		copyProperties(newData,commData);
		
		guid = BaseDAO.getGuId();
		newData.setRecordid(guid);
		newData.setParentid(BusinessService.getYSKCommCode());
		newData.setSubid(YSSubId);
		newData.setPiid(piId);
		newData.setYsid(piId);
		newData.setStatus(Constants.ORDER_STS_0);
		
		detailDao.Create(newData);

	}	
	
	/*
	 * 1.订单基本信息更新处理(1条数据)
	 */
	public String update() throws Exception  {

		ts = new BaseTransaction();
		String orderId = "";
		try {
			
			ts.begin();

			B_OrderData reqOrder = (B_OrderData)reqModel.getOrder();
			B_OrderDetailData reqDetail = (B_OrderDetailData)reqModel.getOrderDetail();
			
			//订单基本信息更新处理
			updateOrder(reqOrder);
			
			//订单详情处理	
			updateOrderDetail(reqDetail);

			orderId = reqOrder.getOrderid();
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
				
		return orderId;
	}
	
	/*
	 * 订单基本信息更新处理
	 */
	public void updateOrder(B_OrderData order) 
			throws Exception{

		String recordid = order.getRecordid();		
		
		//取得更新前数据		
		B_OrderData dbData = getOrderByRecordId(recordid);					
		
		if(null != dbData){
			
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ZPOrderUpdate",userInfo);
			copyProperties(dbData,commData);
			
			//获取页面数据
			dbData.setOrderdate(order.getOrderdate());
			dbData.setDeliverydate(order.getDeliverydate());
			dbData.setYskordertarget(order.getYskordertarget());
			
			orderDao.Store(dbData);
		}
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
			B_OrderDetailData newData) 
			throws Exception{
			

		String recordid = newData.getRecordid();		
		
		//取得更新前数据		
		B_OrderDetailData dbData = getOrderDetailByRecordId(recordid);					
		
		if(null != dbData){

			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ZPOrderDetailUpdate",userInfo);
			copyProperties(dbData,commData);
			
			//获取页面数据
			dbData.setQuantity(newData.getQuantity());
			dbData.setStatus(Constants.ORDER_STS_0);
			
			detailDao.Store(dbData);
		}
	}
	
	/*
	 * 订单详情删除处理
	 */
	public void deleteOrderDetail(List<B_OrderDetailData> oldDetailList) 
			throws Exception{
		
		for(B_OrderDetailData detail:oldDetailList){
			
			if(null != detail){
				
				//处理共通信息
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"ZZOrderDetailDelete",userInfo);

				copyProperties(detail,commData);
				
				detailDao.Store(detail);
			}
		}
	}	

	

	/*
	 * 取得耀升编号的流水号
	 */
	public int getYSIdByParentId(HttpServletRequest request) 
			throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;
		
  
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getYSIdByParentId");
		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件
        String paternId = BusinessService.getYSKCommCode();
        
		userDefinedSearchCase.put("keywords1", paternId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		return code;
	}
	
	
	private B_OrderData getOrderByRecordId(String key) throws Exception {
		B_OrderData dbData = new B_OrderData();
				
		try {
			dbData.setRecordid(key);
			dbData = (B_OrderData)orderDao.FindByPrimaryKey(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		return dbData;
	}
	
	
	private B_OrderDetailData getOrderDetailByRecordId(String key) throws Exception {

		B_OrderDetailData dbData = new B_OrderDetailData();
				
		try {
			dbData.setRecordid(key);
			dbData = (B_OrderDetailData)detailDao.FindByPrimaryKey(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		return dbData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getOrderZPYSKMAXId() throws Exception {

		List rtnArray = new ArrayList();
	
		dataModel.setQueryName("getOrderZZYSKMAXId");
		
		baseQuery = new BaseQuery(request, dataModel);
		String key = BusinessService.getYSKCommCode();
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		//String ysCode = BusinessService.getYSKFormat2Code(code,true); 
		//ysCode = ysCode + BusinessConstants.SHORTNAME_ZP;
		String zpCode = BusinessService.getOrderIdZP(code, true);
		
		model.addAttribute("ysCode",zpCode);		
		
		rtnArray.add(0,zpCode);
		rtnArray.add(1,code+1);
		
		return rtnArray;
	}
	
	public Model createZPorder() throws Exception{
		
		getOrderZPYSKMAXId();
		
		//完整的自制品库存订单编号:16YSK+2位流水号+ZZ
		return model;
		
		
	}

	public void insertAndView() throws Exception {
		
		String orderId = insert();
		getZZOrderDetail(orderId);	
	}

	public void editZPorder() throws Exception {
		
		String orderId = request.getParameter("YSId");
		getZZOrderDetail(orderId);
	}

	public void updateAndView() throws Exception {
		String orderId = update();
		getZZOrderDetail(orderId);	
		
	}

	public void approveAndView() throws Exception {
		
		String recordId = request.getParameter("recordId");
		String orderId = request.getParameter("orderId");
		
		B_OrderDetailData dt = new B_OrderDetailData();
		dt.setRecordid(recordId);
		
		B_OrderDetailDao dao = new B_OrderDetailDao(dt);
		
		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"ZPOrderUpdate",userInfo);
		copyProperties(dao.beanData,commData);
		dao.beanData.setStatus(Constants.ORDER_STS_1);//更新审批状态
		dao.Store();
		
		//getZZOrderDetail(orderId);	
		
	}
	
}
