package com.ys.business.service.order;

import java.net.URLDecoder;
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
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.order.PurchaseOrderModel;
import com.ys.business.action.model.order.ZZOrderModel;
import com.ys.business.action.model.organ.OrganModel;
import com.ys.business.db.dao.B_MaterialRequirmentDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.data.B_MaterialRequirmentData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class PurchaseOrderService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_PurchaseOrderDao orderDao;
	private B_PurchaseOrderDetailDao detailDao;
	private PurchaseOrderModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public PurchaseOrderService(){
		
	}

	public PurchaseOrderService(Model model,
			HttpServletRequest request,
			PurchaseOrderModel reqModel,
			UserInfo userInfo){
		
		this.orderDao = new B_PurchaseOrderDao();
		this.detailDao = new B_PurchaseOrderDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.dataModel = new BaseModel();
		this.request = request;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		
	}

	public HashMap<String, Object> getContractList(String data) throws Exception {
		
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
	
		dataModel.setQueryName("getContractList");
		
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
	
	public void getZZOrderDetail( 
			String YSId) throws Exception {

		dataModel.setQueryName("getRequirementListByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
			
		model.addAttribute("order",dataModel.getYsViewData().get(0));		
		model.addAttribute("detail", dataModel.getYsViewData());
		
	}

	public  ArrayList<HashMap<String, String>> getRequirementBySupplierId( 
			String YSId,String supplierId) throws Exception {

		dataModel.setQueryName("getRequirementBySupplierId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("supplierId", supplierId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				
		return baseQuery.getYsQueryData(0, 0);
		
				
	}
	
	public ArrayList<HashMap<String, String>> getSupplierList(String YSId) throws Exception {
		
		ArrayList<HashMap<String, String>> supplierList = null;
		
		dataModel.setQueryName("getContractSupplierList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		supplierList = baseQuery.getYsQueryData(0, 0);
				
		//设置供应商下拉框
		//setOptiont(supplierList);
		
		return supplierList;	
		
	}
	
	@SuppressWarnings("unchecked")
	private List<B_MaterialRequirmentData> getContractDetail(String where){
		List<B_MaterialRequirmentData> dbList = new ArrayList<B_MaterialRequirmentData>();
		B_MaterialRequirmentDao dao = new B_MaterialRequirmentDao();
				
		try {

			dbList = (List<B_MaterialRequirmentData>)dao.Find(where);
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
	public void getContractBySupplierId(String contractId) throws Exception {

		if(null == contractId || ("").equals(contractId))
			return;
	
		dataModel.setQueryName("getContractBySupplierId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		model.addAttribute("contract",dataModel.getYsViewData().get(0));		
		model.addAttribute("detail", dataModel.getYsViewData());
		
	}	
	
	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	public void insert(String YSId) throws Exception  {

		ts = new BaseTransaction();

		try {
			ts.begin();

			String materialId = request.getParameter("materialId");
			//以供应商为单位集计
			ArrayList<HashMap<String, String>> supplierList = getSupplierList(YSId);

			for(int i=0;i<supplierList.size();i++){
				
				String supplierId = supplierList.get(i).get("supplierId");
				String shortName  = supplierList.get(i).get("shortName");
				String total      = supplierList.get(i).get("total");
				
				//取得供应商的合同流水号
				//父编号:年份+供应商简称
				String parentId = "";
				if(null != YSId && YSId.length() > 3 )
					parentId = YSId.substring(0,2);//耀升编号前两位是年份
				parentId = parentId + shortName;
				
				int subId = getContractCode(parentId);

				//3位流水号格式化	
				//采购合同编号:16YS081-WL002
				String contractId = BusinessService.getContractCode(YSId, shortName, subId);
				
				//新增采购合同
				insertOrder(YSId,materialId,supplierId,contractId,parentId,subId,total);
				
				//从物料需求表取得合同详情
				String where = " YSId = '"+YSId +"'"+ 
						" AND supplierId = '"+supplierId +"'"+ 
						" AND deleteFlag = '0' ";
				List<B_MaterialRequirmentData> detailList = getContractDetail(where);				
				
				for(B_MaterialRequirmentData data:detailList){
														
					//新增合同详情
					insertOrderDetail(data,contractId);					
				}				
			}
						
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
	}	

	/*
	 * 订单详情插入处理
	 */
	private void insertOrder(
			String YSId,
			String materialId,
			String supplierId,
			String contractId,
			String parentId,
			int subId,
			String total) throws Exception{
		
		B_PurchaseOrderData data = new B_PurchaseOrderData();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseOrderInsert",userInfo);

		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		
		data.setRecordid(guid);
		data.setYsid(YSId);
		data.setMaterialid(materialId);
		data.setContractid(contractId);
		data.setParentid(parentId);
		data.setSubid(String.valueOf(subId));
		data.setSupplierid(supplierId);
		data.setTotal(total);
		
		orderDao.Create(data);	
	}	
	
	/*
	 * 订单详情插入处理
	 */
	private void insertOrderDetail(
			B_MaterialRequirmentData mData,
			String contractId) throws Exception{
		
		B_PurchaseOrderDetailData data = new B_PurchaseOrderDetailData();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"PurchaseOrderInsert",userInfo);

		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setContractid(contractId);
		data.setMaterialid(mData.getMaterialid());
		data.setQuantity(mData.getQuantity());
		data.setPrice(mData.getPrice());
		data.setTotalprice(mData.getTotalprice());
		
		detailDao.Create(data);	
	}	
	
	/*
	 * 1.订单基本信息更新处理(1条数据)
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	public void update() throws Exception  {

		ts = new BaseTransaction();
				
		try {
			
			ts.begin();	
			
			//合同基本信息
			B_PurchaseOrderData orderData = reqModel.getContract();
			
			updateOrder(orderData);
			
			//合同详情 更新 处理			
			List<B_PurchaseOrderDetailData> newDetailList = reqModel.getDetailList();
						
			//页面数据的recordId与DB匹配			
			for(B_PurchaseOrderDetailData data:newDetailList ){
				
				//更新处理
				updateOrderDetail(data);						
					
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * 订单基本信息更新处理
	 */
	private void updateOrder(B_PurchaseOrderData order) 
			throws Exception{

		
		orderDao = new B_PurchaseOrderDao(order);
		
		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseOrderUpdate",userInfo);
		copyProperties(orderDao.beanData,commData);
		
		//获取页面数据
		copyProperties(orderDao.beanData,order);
		
		orderDao.Store(orderDao.beanData);
	
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
			B_PurchaseOrderDetailData data) throws Exception{
			
		detailDao = new B_PurchaseOrderDetailDao(data);

		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseOrderDetailUpdate",userInfo);
		copyProperties(detailDao.beanData,commData);
		
		//获取页面数据
		copyProperties(detailDao.beanData,data);
		
		detailDao.Store(detailDao.beanData);
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
	
	private int getContractCode(String parentId) throws Exception {

		dataModel.setQueryName("getContractCode");
		
		baseQuery = new BaseQuery(request, dataModel);
	
		
		userDefinedSearchCase.put("parentId", parentId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		return code;
		
	}	

	//生成采购合同--自制品
	public void creatPurchaseOrderZZ() throws Exception {
		
		String YSId = request.getParameter("YSId");
		//String supplierId = request.getParameter("supplierId");
		
		insert(YSId);
		
		//ArrayList<ArrayList<String>> supplierList = null; 
		
		//生成采购合同		
		//if(null == supplierId){

		//	supplierId = insert(YSId);
			
		//}else{
		//	getSupplierList(YSId);//设置供应商下拉框
		//}
			
		//取得本次采购合同在该供应商购买的材料list
		//String suppShortName = getRequirementBySupplierId(YSId,supplierId);
		
		//getContractCode(YSId,suppShortName);
	}

	public void updateAndView() throws Exception {
		
		update();

		String contractId = reqModel.getContract().getContractid();
		
		getContractBySupplierId(contractId);
		
	}
	
	public void approveAndView() throws Exception {
		
		String YSId = request.getParameter("YSId");
		
		updateDetailToApprove(YSId);
		
		//getZZOrderDetail(YSId);	
		
	}
	
}
