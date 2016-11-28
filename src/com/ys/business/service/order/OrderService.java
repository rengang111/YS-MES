package com.ys.business.service.order;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.core.ReflectUtils;
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
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.common.TableFields;
import com.ys.business.action.model.contact.ContactModel;
import com.ys.business.action.model.material.MaterialModel;
import com.ys.business.action.model.order.OrderModel;
import com.ys.business.db.dao.B_ContactDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.data.B_ContactData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.business.service.common.BusinessService;
import com.ys.util.CalendarUtil;

@Service
public class OrderService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	TableFields fields;
	String guid ="";

	public HashMap<String, Object> getOrderList(HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

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
		

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");
		
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
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	
	public ArrayList<HashMap<String, String>> getOrderViewByPIId(
			HttpServletRequest request,
			String PIId ) throws Exception {
		
		ArrayList<HashMap<String, String>> modelMap = null;
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");
		
		baseQuery = new BaseQuery(request, dataModel);


		userDefinedSearchCase.put("keywords1", PIId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		return modelMap;
	}
	

	@SuppressWarnings("unchecked")
	private List<B_OrderDetailData> getOrderDetailByPIId(String where){
		B_OrderDetailDao dao = new B_OrderDetailDao();
		List<B_OrderDetailData> dbList = new ArrayList<B_OrderDetailData>();
				
		try {

			dbList = (List<B_OrderDetailData>)dao.Find(where);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbList = null;
		}
		
		return dbList;
	}
	
	/*
	public ArrayList<HashMap<String, String>> getOrderDetailByPIId(
			HttpServletRequest request,
			String PIId ) throws Exception {
		
		ArrayList<HashMap<String, String>> modelMap = null;
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderDetailByPIId");
		
		baseQuery = new BaseQuery(request, dataModel);


		userDefinedSearchCase.put("keywords1", PIId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		return modelMap;
	}
	*/
	
	public HashMap<String, Object> getCustomer(HttpServletRequest request, 
			String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		data = URLDecoder.decode(data, "UTF-8");
		
		String key = request.getParameter("key").toUpperCase();
		
				

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getCustomer");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 	
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());

		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
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
	 * 
	 */
	public HashMap<String, Object> getMaterialList(
			HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;


		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("materialList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
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
	public Model insert(
			OrderModel reqFormBean,
			Model rtnModel,
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {

		ts = new BaseTransaction();

		try {

			int YSMaxid = getYSIdByParentId(request);
			
			ts.begin();
					
			B_OrderData reqData = (B_OrderData)reqFormBean.getOrder();
			
			//插入订单基本信息
			insertOrder(reqData,userInfo);
			
			//处理订单详情数据		
			List<B_OrderDetailData> reqDataList = reqFormBean.getOrderDetailLines();
			
			String piId = reqData.getPiid();
			
			for(B_OrderDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && 
					data.getMaterialid() != null && 
					data.getMaterialid() != ""){

					insertOrderDetail(data, YSMaxid,piId, userInfo);
					
					YSMaxid++;
								
				}	
			
			}
			
			//重新查询,回到查看页面
			//rtnModel = view(request,rtnModel,selectedRecord,parentId);
			
			reqFormBean.setEndInfoMap(NORMAL, "suc001", "");
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqFormBean.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return rtnModel;
	}	

	/*
	 * 订单详情插入处理
	 */
	public void insertOrder(
			B_OrderData data,
			UserInfo userInfo) throws Exception{

		B_OrderDao dao = new B_OrderDao();	
		
		fields = BusinessService.updateModifyInfo
		(Constants.ACCESSTYPE_INS,"OrderInsert", userInfo);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setInsFields(fields);
		
		dao.Create(data);	
		

	}	
	
	/*
	 * 订单详情插入处理
	 */
	private void insertOrderDetail(
			B_OrderDetailData newData,
			int YSMaxid ,
			String piId,
			UserInfo userInfo) throws Exception{

		B_OrderDetailDao dao = new B_OrderDetailDao();

		String parentid = BusinessService.getYSCommCode();
		
		String ysid = BusinessService.getYSFormatCode(YSMaxid,true);
			
		 fields = BusinessService.updateModifyInfo
				(Constants.ACCESSTYPE_INS,"OrderDetailInsert", userInfo);

		guid = BaseDAO.getGuId();
		newData.setRecordid(guid);
		newData.setInsFields(fields);
		newData.setParentid(parentid);
		newData.setSubid(String.valueOf(YSMaxid+1));
		newData.setPiid(piId);
		newData.setYsid(ysid);
		
		dao.Create(newData);

	}	
	
	/*
	 * 1.订单基本信息更新处理(1条数据)
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	public Model update(
			OrderModel reqForm,
			Model rtnModel, 
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {

		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
			
			int YSMaxid = getYSIdByParentId(request);
				
			B_OrderData reqOrder = (B_OrderData)reqForm.getOrder();
			
			//订单基本信息更新处理
			updateOrder(reqOrder,userInfo);
			
			//订单详情 更新/删除/插入 处理			
			List<B_OrderDetailData> newDetailList = reqForm.getOrderDetailLines();
			List<B_OrderDetailData> delDetailList = new ArrayList<B_OrderDetailData>();
			
			//过滤页面传来的有效数据
			for(B_OrderDetailData data:newDetailList ){
				if(null == data.getMaterialid()){
					delDetailList.add(data);
				}
			}
			newDetailList.removeAll(delDetailList);
			
			//清空后备用
			delDetailList.clear();
			
			//根据画面的PiId取得DB中更新前的订单详情 
			//key:piId
			List<B_OrderDetailData> oldDetailList = null;

			String piId = reqOrder.getPiid();
			String where = " piid = '"+piId +"'" + " AND deleteFlag = '0' ";
			oldDetailList = getOrderDetailByPIId(where);
						
			//页面数据的recordId与DB匹配
			//key存在:update;key不存在:insert;
			//剩下未处理的DB数据:delete
			for(B_OrderDetailData newData:newDetailList ){

				String newMaterialid = newData.getMaterialid();	

				boolean insFlag = true;
				for(B_OrderDetailData oldData:oldDetailList ){
					String id = oldData.getMaterialid();
					
					if(newMaterialid.equals(id)){
						
						//更新处理
						updateOrderDetail(newData,oldData,userInfo);						
						
						//从old中移除处理过的数据
						oldDetailList.remove(oldData);
						
						insFlag = false;
						
						break;
					}
				}

				//新增处理
				if(insFlag){
					insertOrderDetail(newData, YSMaxid, piId, userInfo);
				}
			}
			
			//删除处理
			if(oldDetailList.size() > 0){					
				deleteOrderDetail(oldDetailList,userInfo);
			}
			
			ts.commit();
			
			//重新查询
			//rtnModel = view(request,rtnModel,selectedRecord,parentId);		
			reqForm.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			ts.rollback();
			reqForm.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
				
		return rtnModel;
	}
	
	/*
	 * 订单基本信息更新处理
	 */
	public void updateOrder(B_OrderData order,UserInfo userInfo) 
			throws Exception{

		String recordid = order.getRecordid();		
		B_OrderDao dao = new B_OrderDao();
		
		//取得更新前数据		
		B_OrderData dbData = getOrderByRecordId(recordid);					
		
		if(null != dbData){
			
			fields = BusinessService.updateModifyInfo
					(Constants.ACCESSTYPE_UPD,"OrderUpdate", userInfo);
	
			//处理共通信息
			dbData.setUpdFields(fields);
			//获取页面数据
			dbData.setOrderid(order.getOrderid());
			dbData.setPaymentterm(order.getPaymentterm());
			dbData.setCurrency(order.getCurrency());;
			dbData.setShippingcase(order.getShippingcase());
			dbData.setDeliverydate(order.getDeliverydate());
			dbData.setOrderdate(order.getOrderdate());
			dbData.setDeliveryport(order.getDeliveryport());
			dbData.setLoadingport(order.getLoadingport());
			dbData.setTotalprice(order.getTotalprice());
			
			dao.Store(dbData);
		}
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
			B_OrderDetailData newData,
			B_OrderDetailData dbData,
			UserInfo userInfo) 
			throws Exception{
		
		B_OrderDetailDao dao = new B_OrderDetailDao();							
		
		fields = BusinessService.updateModifyInfo
				(Constants.ACCESSTYPE_UPD,"OrderDetailUpdate", userInfo);

		//处理共通信息
		dbData.setUpdFields(fields);
		//获取页面数据
		dbData.setQuantity(newData.getQuantity());
		dbData.setPrice(newData.getPrice());;
		dbData.setTotalprice(newData.getTotalprice());
		
		dao.Store(dbData);

	}
	
	/*
	 * 订单详情删除处理
	 */
	public void deleteOrderDetail(List<B_OrderDetailData> oldDetailList,UserInfo userInfo) 
			throws Exception{

		B_OrderDetailDao dao = new B_OrderDetailDao();
		
		for(B_OrderDetailData detail:oldDetailList){
			
			//String recordid = detail.getRecordid();		
			
			//取得更新前数据		
			//B_OrderDetailData dbData = getOrderDetailByRecordId(recordid);	
			//B_OrderDetailData dbData = detail;					
			
			if(null != detail){
				
				fields = BusinessService.updateModifyInfo
						(Constants.ACCESSTYPE_DEL,"OrderDetailDelete", userInfo);
		
				//处理共通信息
				detail.setUpdFields(fields);
				
				dao.Store(detail);
			}
		}
	}
	

	
	/*
	 * 新增物料初始处理
	 */
	public OrderModel createOrder(
			HttpServletRequest request,
			OrderModel reqModel) {

		try {	
			
      
			//耀升编号
	        String paternId = BusinessService.getYSCommCode();
	        
			int YSMaxId = getYSIdByParentId(request);

			reqModel.setYSMaxId(YSMaxId);	
			reqModel.setYSParentId(paternId);
			
			reqModel.setDeliveryPortList(
					util.getListOption(DicUtil.DELIVERYPORT, ""));
			reqModel.setShippingCaseList(
					util.getListOption(DicUtil.SHIPPINGCASE, ""));
			reqModel.setLoadingPortList(
					util.getListOption(DicUtil.LOADINGPORT, ""));
			reqModel.setCurrencyList(
					util.getListOption(DicUtil.CURRENCY, ""));
			
			
			reqModel.setEndInfoMap(NORMAL, "", "");
				

			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return reqModel;
	
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
        String paternId = BusinessService.getYSCommCode();
        
		userDefinedSearchCase.put("keywords1", paternId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		return code;
	}
	
	
	private B_OrderData getOrderByRecordId(String key) throws Exception {
		B_OrderDao dao = new B_OrderDao();
		B_OrderData dbData = new B_OrderData();
				
		try {
			dbData.setRecordid(key);
			dbData = (B_OrderData)dao.FindByPrimaryKey(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		return dbData;
	}
	
	private B_OrderDetailData getOrderDetailByRecordId(String key) throws Exception {
		B_OrderDetailDao dao = new B_OrderDetailDao();
		B_OrderDetailData dbData = new B_OrderDetailData();
				
		try {
			dbData.setRecordid(key);
			dbData = (B_OrderDetailData)dao.FindByPrimaryKey(dbData);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			dbData = null;
		}
		
		return dbData;
	}
	
}
