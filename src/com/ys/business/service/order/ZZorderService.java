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
import com.ys.business.action.model.order.ZZOrderModel;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class ZZorderService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_OrderDao orderDao;
	private B_OrderDetailDao detailDao;
	private ZZOrderModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public ZZorderService(){
		
	}

	public ZZorderService(Model model,
			HttpServletRequest request,
			ZZOrderModel reqModel,
			UserInfo userInfo){
		
		this.orderDao = new B_OrderDao();
		this.detailDao = new B_OrderDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.dataModel = new BaseModel();
		this.request = request;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		
	}

	public void getZZOrderDetail( 
			String orderId) throws Exception {

		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
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
	
	
	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	@SuppressWarnings("rawtypes")
	public String insert() throws Exception  {

		ts = new BaseTransaction();
		String orderId = "";

		try {
			ts.begin();

			List code = getOrderZZYSKMAXId();

			String YSCode  = code.get(0).toString();
			String YSSubId = code.get(1).toString();	
			
			B_OrderData reqData = (B_OrderData)reqModel.getOrder();
			
			//插入订单基本信息
			insertOrder(reqData,YSCode,YSSubId);
			
			//处理订单详情数据		
			List<B_OrderDetailData> reqDataList = reqModel.getOrderDetailLines();
			
			orderId = reqData.getOrderid();
			
			for(B_OrderDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && 
					data.getMaterialid() != null && 
				   !data.getMaterialid().equals("")){

					insertOrderDetail(data, YSSubId,orderId);
				}	
			}
			
			//重新查询,回到查看页面
			//rtnModel = view(request,rtnModel,selectedRecord,parentId);
			
			reqModel.setEndInfoMap(NORMAL, "suc001", "");
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return orderId;
	}	

	/*
	 * 订单详情插入处理
	 */
	public void insertOrder(
			B_OrderData data,
			String ysCode,
			String subId) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"ZZOrderInsert",userInfo);

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
				"ZZOrderDetailInsert",userInfo);

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
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	public String update() throws Exception  {

		ts = new BaseTransaction();
		String orderId = "";
		try {
			
			ts.begin();

			B_OrderData reqOrder = (B_OrderData)reqModel.getOrder();
			
			//订单基本信息更新处理
			updateOrder(reqOrder);
			
			//订单详情 更新/删除/插入 处理			
			List<B_OrderDetailData> newDetailList = reqModel.getOrderDetailLines();
			List<B_OrderDetailData> delDetailList = new ArrayList<B_OrderDetailData>();
			
			//过滤页面传来的有效数据
			for(B_OrderDetailData data:newDetailList ){
				if(null == data.getMaterialid() || ("").equals(data.getMaterialid())){
					delDetailList.add(data);
				}
			}
			newDetailList.removeAll(delDetailList);
			
			//清空后备用
			delDetailList.clear();
			
			//根据画面的PiId取得DB中更新前的订单详情 
			//key:piId
			List<B_OrderDetailData> oldDetailList = null;

			orderId = reqOrder.getOrderid();
			String YSSubId = reqOrder.getSubid();	
			//库存订单的PI编号,订单编号,耀升编号相同
			String where = " PIId = '"+orderId +"'" + " AND deleteFlag = '0' ";
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
						updateOrderDetail(newData,oldData);						
						
						//从old中移除处理过的数据
						oldDetailList.remove(oldData);
						
						insFlag = false;
						
						break;
					}
				}

				//新增处理
				if(insFlag){

					insertOrderDetail(newData, YSSubId, orderId);
				}
			}
			
			//删除处理
			if(oldDetailList.size() > 0){					
				deleteOrderDetail(oldDetailList);
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
				
		return orderId;
	}
	
	/*
	 * 订单基本信息更新处理
	 */
	public B_OrderData updateOrder(B_OrderData order) 
			throws Exception{

		String recordid = order.getRecordid();		
		
		//取得更新前数据		
		B_OrderData dbData = getOrderByRecordId(recordid);					
		
		if(null != dbData){
			
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ZZOrderUpdate",userInfo);
			copyProperties(dbData,commData);
			
			//获取页面数据
			copyProperties(dbData,order);
			
			orderDao.Store(dbData);
		}
		
		return dbData;
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
			B_OrderDetailData newData,
			B_OrderDetailData dbData) 
			throws Exception{
			
		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"ZZOrderDetailUpdate",userInfo);

		copyProperties(dbData,commData);
		//获取页面数据
		dbData.setQuantity(newData.getQuantity());
		dbData.setPrice(newData.getPrice());;
		dbData.setTotalprice(newData.getTotalprice());
		dbData.setStatus(Constants.ORDER_STS_0);
		
		detailDao.Store(dbData);

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
			detailList = getOrderDetailByPIId(where);
						
			for(B_OrderDetailData dbData:detailList ){				
					
				//更新处理//处理共通信息
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"ZZOrderDetailUpdate",userInfo);

				copyProperties(dbData,commData);
				//更新审批状态:下一步是审批
				dbData.setStatus(Constants.ORDER_STS_1);
				
				detailDao.Store(dbData);
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
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
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getOrderZZYSKMAXId() throws Exception {

		List rtnArray = new ArrayList();
	
		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("getOrderZZYSKMAXId");
		
		baseQuery = new BaseQuery(request, dataModel);
		String key = BusinessService.getYSKCommCode();
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
			
		String ysCode = BusinessService.getYSKFormat2Code(code,true); 
		ysCode = ysCode + BusinessConstants.SHORTNAME_ZZ;
		
		model.addAttribute("ysCode",ysCode);		
		
		rtnArray.add(0,ysCode);
		rtnArray.add(1,	code+1);
		
		return rtnArray;
	}
	
	public Model createZZorder() throws Exception{
		
		getOrderZZYSKMAXId();
		
		//完整的自制品库存订单编号:16YSK+2位流水号+ZZ
		return model;
		
		
	}

	public void insertAndView() throws Exception {
		
		String orderId = insert();
		getZZOrderDetail(orderId);	
	}

	public void editZZorder() throws Exception {
		
		String orderId = request.getParameter("YSId");
		getZZOrderDetail(orderId);
	}

	public void updateAndView() throws Exception {
		String orderId = update();
		getZZOrderDetail(orderId);	
		
	}
	
	public void approveAndView() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		updateDetailToApprove(YSId);
		
		//getZZOrderDetail(YSId);	
		
	}
	
}
