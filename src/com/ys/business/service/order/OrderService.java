package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.common.FinanceMouthly;
import com.ys.business.action.model.order.OrderModel;
import com.ys.business.db.dao.B_FollowDao;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderCancelDao;
import com.ys.business.db.dao.B_OrderDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.B_RequisitionDetailDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.dao.B_StockOutDetailDao;
import com.ys.business.db.data.B_FollowData;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderCancelData;
import com.ys.business.db.data.B_OrderData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.B_RequisitionDetailData;
import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.B_StockOutDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class OrderService extends CommonService  {

	DicUtil util = new DicUtil();

	BaseTransaction ts;
	String guid ="";
	private CommFieldsData commData;

	private OrderModel reqModel;

	private HttpServletRequest request;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HttpSession session;
	
	public OrderService(){
		
	}

	public OrderService(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			OrderModel reqModel,
			UserInfo userInfo){
		
		//this.bomPlanDao = new B_BomPlanDao();
		//this.bomPlanData = new B_BomPlanData();
		//this.bomDetailDao = new B_BomDetailDao();
		//this.bomDetailData = new B_BomDetailData();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	
	public HashMap<String, Object> getOrderList(String formId,String data) throws Exception {
		
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
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];		

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");	
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2))
			userDefinedSearchCase.put("status", "");
		
		baseQuery = new BaseQuery(request, dataModel);	
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
	

	public HashMap<String, Object> getOrderCancelList(String formId,String data) throws Exception {
		
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
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];		

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderCancelList");	
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2))
			userDefinedSearchCase.put("status", "");
		
		baseQuery = new BaseQuery(request, dataModel);	
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

	public HashMap<String, Object> getOrderExpenseList(String formId,String data) throws Exception {
		
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
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];	
		
		String monthday = request.getParameter("monthday");
		if(isNullOrEmpty(monthday)){
			monthday = CalendarUtil.getToDay();
		}
		FinanceMouthly monthly = new FinanceMouthly(monthday);

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderExpenseList");	
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		userDefinedSearchCase.put("startDate", monthly.getStartDate());
		userDefinedSearchCase.put("endDate", monthly.getEndDate());

		String statusFlag = request.getParameter("statusFlag");
		String having = "1=1";
		
		if(notEmpty(key1) || notEmpty(key2)){
			statusFlag = "";//有查询key，则忽略其状态
			userDefinedSearchCase.put("startDate", "");//忽略其时间段
			userDefinedSearchCase.put("endDate", "");//忽略其时间段
			
		}
		
		if(("010").equals(statusFlag)){
			having=" stockinQty+0 < quantity+0 ";//待入库
			
		}else if(("020").equals(statusFlag)){
			having=" stockinQty+0 >= quantity+0 ";//已入库			
		}
		
		baseQuery = new BaseQuery(request, dataModel);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", having);
		System.out.println("订单过程查询SQL："+sql);	
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	 
		
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
	
	public HashMap<String, Object> getOrderTrackingList(String formId,String data) throws Exception {
		
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
		String[] keyArr = getSearchKey(formId,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];		

		String having = "1=1";
		String follow = "";
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("orderTrackingList");
		
		if(isNullOrEmpty(key1) && isNullOrEmpty(key2)){
			follow  = getJsonData(data, "orderFollow");//重点关注
			String stockUp = getJsonData(data, "stockUp");//备货情况
			if(("1").equals(stockUp)){
				//货已备齐
				having = "replace(IFNULL(contractQty,0),',','')+0 <= replace(IFNULL(stockinQty,0),',','')";
			}else if(("2").equals(stockUp)){
				//未齐
				having = "replace(IFNULL(contractQty,0),',','')+0 > replace(IFNULL(stockinQty,0),',','')";
			}
		}
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		userDefinedSearchCase.put("follow", follow);
		
		baseQuery = new BaseQuery(request, dataModel);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", having);
		System.out.println("订单跟踪:"+sql);
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	 
		
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

	public HashMap<String, Object> getOrderListDemand(String data) throws Exception {
		
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
		

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderListDemand");
		
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
		modelMap.put("keyword1",key1);	
		modelMap.put("keyword2",key2);		
		
		return modelMap;
	}
	
	public HashMap<String, Object> getOrderViewByPIId() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");
		baseQuery = new BaseQuery(request, dataModel);

		String key = request.getParameter("key").toUpperCase();
		userDefinedSearchCase.put("key", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}
	

	public ArrayList<HashMap<String, String>> getOrderViewByPIId(
			String PIId ) throws Exception {

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");
		
		baseQuery = new BaseQuery(request, dataModel);

		String YSId = request.getParameter("YSId");
		if(YSId != null && !YSId.equals(""))
			userDefinedSearchCase.put("YSId", YSId);
		
		if(PIId != null && !PIId.equals(""))
			userDefinedSearchCase.put("keywords1", PIId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		return modelMap;
	}
	public ArrayList<HashMap<String, String>> createOrderFromProduct(
			String materialId ) throws Exception {

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("createorderfromproduct");
		
		baseQuery = new BaseQuery(request, dataModel);

		
		userDefinedSearchCase.put("materialId", materialId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsFullData();
		
		return modelMap;
	}
	
	public ArrayList<HashMap<String, String>> getZZOrderDetail(
			String PIId ) throws Exception {

		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("getZZOrderDetail");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("orderId", PIId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		return modelMap;
	}
	public ArrayList<HashMap<String, String>> getZPOrderDetail(
			String PIId ) throws Exception {

		dataModel.setQueryFileName("/business/order/zzorderquerydefine");
		dataModel.setQueryName("getZZOrderDetail");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("orderId", PIId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);
		
		return modelMap;
	}
	
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
			String key) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

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

		dataModel.setQueryFileName("/business/material/materialquerydefine");
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
			
			ts.begin();
					
			B_OrderData reqData = (B_OrderData)reqFormBean.getOrder();
			
			//插入订单基本信息
			insertOrder(reqData,userInfo);
			
			//处理订单详情数据		
			List<B_OrderDetailData> reqDataList = reqFormBean.getOrderDetailLines();
			
			String piId = reqData.getPiid();
			
			boolean peiYsidCheckFlag = true;			
			B_OrderDetailData tmpData = new B_OrderDetailData();
			int peiIndex = 1;
			for(B_OrderDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && 
					data.getMaterialid() != null && 
					data.getMaterialid() != ""){

					String orderType = data.getOrdertype();
					//配件订单的统一耀升编号处理
					if(("020").equals(orderType)){

						String list[] = data.getYsid().split("-");
						String ysid = list[0];
						String peiYsid = ysid + "P";
						
						if(peiYsidCheckFlag){
							
							String where = " YSId like '%"+ysid +"%' AND deleteFlag='0' " ;
							
							String existFlag = ysidExistCheck(where);
							
							//如果重复的话,重新设置
							if(("1").equals(existFlag)){
						        String paternId = BusinessService.getYSCommCode();
								int YSMaxid = getYSIdByParentId(paternId);
								ysid = BusinessService.getYSFormatCode(YSMaxid,true);
								
								data.setYsid(ysid + "-" + peiIndex);
								data.setParentid(paternId);
								data.setSubid(String.valueOf(YSMaxid+1));
								data.setPeiysid(ysid+"P");
								
							}else{
								data.setYsid(ysid + "-" + peiIndex);
								data.setParentid(ysid.substring(0, 4));
								data.setSubid(ysid.substring(4));
								data.setPeiysid(peiYsid);
							}
							tmpData.setYsid(ysid);
							tmpData.setParentid(data.getParentid());
							tmpData.setSubid(data.getSubid());
							tmpData.setPeiysid(data.getPeiysid());
							
							peiYsidCheckFlag = false;
						}else{
							data.setYsid(tmpData.getYsid() + "-" + peiIndex);
							data.setParentid(tmpData.getParentid());
							data.setSubid(tmpData.getSubid());
							data.setPeiysid(tmpData.getPeiysid());
						}
												
						insertOrderDetailPei(data,piId);
						
						peiIndex++;
						
					}else{
						//正常订单
						data.setStatus(Constants.ORDER_STS_1);
						insertOrderDetail(data,piId);						
					}							
				}			
			}
			
			reqFormBean.setEndInfoMap(NORMAL, "suc001", "");
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqFormBean.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return rtnModel;
	}	
	
	
	/**
	 * 订单转移
	 * 
	 */
	public Model insertTransfer(
			OrderModel reqFormBean,
			Model rtnModel,
			HttpServletRequest request,
			UserInfo userInfo) throws Exception  {

		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_OrderData reqData = (B_OrderData)reqFormBean.getOrder();
			B_OrderDetailData reqOrderDetail = reqFormBean.getOrderDetail();
			B_OrderDetailData reqOrderTransfer = reqFormBean.getOrderTransfer();
			
			String newYsid = reqOrderDetail.getYsid();
			String trfsYsid = reqOrderTransfer.getYsid();
			String piId = reqData.getPiid();
		
			//过滤空行或者被删除的数据
			if(reqOrderDetail.getMaterialid() != null && 
				reqOrderDetail.getMaterialid() != ""){

				//新订单:新增订单基本信息
				insertOrder(reqData,userInfo);
			
				//新订单:新增详情
				reqOrderTransfer.setTotalquantity(reqOrderTransfer.getQuantity());
				reqOrderDetail.setStatus(Constants.ORDER_STS_4);//已入库
				insertOrderDetail(reqOrderDetail,piId);						
						
				//转移目标的订单：更新订单数量
				updateOrderDetail2(reqOrderTransfer);
				
				//转移目标的订单：更新总价
				B_OrderDetailData transfer = getOrderDetailById(trfsYsid);
				String transferPiid = transfer.getPiid();
				
				float price = stringToFloat( transfer.getPrice() );
				float quantity = stringToFloat( transfer.getTotalquantity() );
				float oldTotal = price * quantity;
				float cutQty = stringToFloat( reqOrderDetail.getTotalquantity() );
				float newTotal = price * (quantity - cutQty);
				
				updateOrder(transferPiid,userInfo,newTotal,oldTotal);

				List<B_PurchasePlanDetailData> planList = getPurchasePlanDetail(trfsYsid);
				
				//******转移目标的订单:负数出库******
				String cutQuantity = floatToString( cutQty * (-1));

				insertStockOutData(planList,trfsYsid,cutQuantity);
				
				//******新订单:增加出库******
				//出库数量: cutQty
				insertStockOutData(planList,newYsid,floatToString(cutQty));
			}
			
			ts.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
		}
		
		return rtnModel;
	}
	
	private void insertStockOutData(
			List<B_PurchasePlanDetailData> planList,
			String ysid,
			String cutQuantity) throws Exception{
		

		B_StockOutData out = new B_StockOutData();
		out = getStorageRecordId(out);	
		String stockoutid = out.getStockoutid();
		

		//出库单做成
		for(B_PurchasePlanDetailData plan : planList){

			B_StockOutDetailData outDetail = new B_StockOutDetailData();
			
			String materialId = plan.getMaterialid();	
			
			outDetail.setStockoutid(stockoutid);//出库单编号
			outDetail.setMaterialid(materialId);
			outDetail.setDepotid(getDepotId(materialId));

			String outPrice = getMAPrice(materialId);
			float total = stringToFloat(outPrice) * stringToFloat(cutQuantity);
			outDetail.setPrice(outPrice);
			outDetail.setQuantity(cutQuantity);
			outDetail.setTotalprice(floatToString(total));
			
			insertStockOutDetail(outDetail);
			
		}
		//出库单
		out.setYsid(ysid);
		insertStockOut(out);
	}
	
	private void insertStockOut(
			B_StockOutData stock) throws Exception {
		
	
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"订单转移",userInfo);
		copyProperties(stock,commData);
		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		stock.setKeepuser(userInfo.getUserId());//默认为登陆者
		stock.setCheckoutdate(CalendarUtil.fmtYmdDate());
		stock.setStockouttype(Constants.REQUISITION_PARTS);//装配领料
		
		new B_StockOutDao().Create(stock);
		
		
	}

	private void insertStockOutDetail(
			B_StockOutDetailData stock) throws Exception {

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"订单转移",userInfo);
		copyProperties(stock,commData);
		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
				
		new B_StockOutDetailDao().Create(stock);
	}
	
	@SuppressWarnings("unchecked")
	private String getMAPrice(
			String materialId) throws Exception{
		String price="0";
		String where = " materialId ='" + materialId + "' AND deleteFlag='0'";
		List<B_MaterialData> list = new B_MaterialDao().Find(where);
		
		if(list.size() > 0)
			price = list.get(0).getMaprice();
				
		return price; 
	}

	
	/**
	 * 设置默认的仓库分类
	 * @return
	 */
	public String getDepotId(String materialId){
		
		String depotid="010";//采购件
		
		if(materialId.substring(0,1).equals("A")){
			depotid="030";//原材料	
		}else if(materialId.substring(0,1).equals("G")){
			depotid="040";//包装件								
		}else if(materialId.substring(0,3).equals("B01")){
			depotid="020";//自制件								
		}		
		return depotid;
	}
	
	private B_StockOutData getStorageRecordId(
			B_StockOutData data) throws Exception {
	
		String parentId = BusinessService.getshortYearcode()+
				BusinessConstants.SHORTNAME_CK;
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		dataModel.setQueryName("getMAXStockOutId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");	
		
		String id = BusinessService.getStockOutId(
						parentId,
						code,
						false);	
		
		data.setStockoutid(id);
		data.setParentid(parentId);
		data.setSubid(code);			
		
		return data;
		
	}
	
	@SuppressWarnings("unchecked")
	private List<B_PurchasePlanDetailData> getPurchasePlanDetail(
			String ysid) throws Exception{
		String where = " YSId ='" + ysid + "' AND left(materialId,1) <> 'H' AND deleteFlag='0'";
		List<B_PurchasePlanDetailData> list = new B_PurchasePlanDetailDao().Find(where);
				
		return list; 
	}

	/*
	 * 订单详情插入处理
	 */
	public void insertOrder(
			B_OrderData data,
			UserInfo userInfo) throws Exception{

		B_OrderDao dao = new B_OrderDao();	
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"OrderInsert",userInfo);
		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		//重新编辑PI的流水号
		String PI = data.getPiid();
		String parentId = data.getParentid();
		String subId = PI.substring(parentId.length());
		data.setSubid(subId);
		
		dao.Create(data);
	}	
	
	/*
	 * 订单详情插入处理
	 */
	private void insertOrderDetail(
			B_OrderDetailData newData,
			String piId) throws Exception{

		B_OrderDetailDao dao = new B_OrderDetailDao();

		String ysid = newData.getYsid();
		//耀升编号重复check
		String where = " YSId like'%"+ysid +"%' AND deleteFlag='0' " ;
		String existFlag = ysidExistCheck(where);
		
		//如果重复的话,重新设置
		if(("1").equals(existFlag)){
	        String paternId = BusinessService.getYSCommCode();
			int YSMaxid = getYSIdByParentId(paternId);
			ysid = BusinessService.getYSFormatCode(YSMaxid,true);
			
			newData.setYsid(ysid);
			newData.setParentid(paternId);
			newData.setSubid(String.valueOf(YSMaxid+1));
		}else{
			newData.setParentid(ysid.substring(0, 4));
			newData.setSubid(newData.getYsid().substring(4));
		}
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"OrderDetailInsert",userInfo);
		copyProperties(newData,commData);
		guid = BaseDAO.getGuId();
		newData.setRecordid(guid);
	
		newData.setPiid(piId);
		newData.setCurrency(reqModel.getCurrency());
		//newData.setRebaterate(reqModel.getRebateRate());//退税率
		//newData.setStatus(Constants.ORDER_STS_1);
		newData.setReturnquantity(Constants.ORDER_RETURNQUANTY);
		
		dao.Create(newData);

	}	
	
	/*
	 * 订单详情插入处理
	 */
	private void insertOrderDetailPei(
			B_OrderDetailData newData,
			String piId) throws Exception{

		B_OrderDetailDao dao = new B_OrderDetailDao();
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"OrderDetailInsert",userInfo);
		copyProperties(newData,commData);
		guid = BaseDAO.getGuId();
		newData.setRecordid(guid);
	
		newData.setPiid(piId);
		newData.setCurrency(reqModel.getCurrency());
		newData.setRebaterate(reqModel.getRebateRate());//退税率
		newData.setStatus(Constants.ORDER_STS_1);
		newData.setReturnquantity(Constants.ORDER_RETURNQUANTY);
		
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
				
			B_OrderData reqOrder = (B_OrderData)reqForm.getOrder();
			
			//订单基本信息更新处理
			updateOrder(reqOrder,userInfo);
			
			//订单详情 更新/删除/插入 处理			
			List<B_OrderDetailData> newDetailList = reqForm.getOrderDetailLines();
			
			//订单详情更新处理
			//首先删除DB中的订单详情
			String piId = reqOrder.getPiid();
			String oldPiId = reqModel.getKeyBackup();
		
			deleteOrderDetail(oldPiId);
			
			//订单详情 
			for(B_OrderDetailData newData:newDetailList ){

				String mateId = newData.getMaterialid();	
					
				if(mateId != null && mateId.trim() != ""){
					
					//更新处理
					newData.setPiid(piId);
					String orderType = newData.getOrdertype();
					//配件订单的统一耀升编号处理
					if(("020").equals(orderType)){
						
						String[] list = newData.getYsid().split("-");
						String peiYsid = list[0]+"P";
						newData.setPeiysid(peiYsid);
						newData.setParentid(list[0].substring(0,4));
						newData.setSubid(list[0].substring(4));
					}
					updateOrderDetail(newData);
				}
			}
			
			ts.commit();
					
			reqForm.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			ts.rollback();
			reqForm.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
				
		return rtnModel;
	}
	
	@SuppressWarnings("unchecked")
	public void updataOrderTotalPrice(
			String piid,
			float newtotal,
			float oldtotal) throws Exception{
	
		B_OrderDao dao = new B_OrderDao();
		
		//取得更新前数据	
		String where = " PIId ='" + piid + "' AND deleteFlag='0'";
		List<B_OrderData> list = dao.Find(where);					
		
		if(null == list || ("").equals(list)){
			return;
		}
			
		B_OrderData dbData  = list.get(0);
		//处理共通信息
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"OrderCancelUpdate",userInfo);
		copyProperties(dbData,commData);
		//获取页面数据
		float fdbTtoal = stringToFloat(dbData.getTotalprice());
		
		float fnewTotalprice = fdbTtoal - oldtotal + newtotal;
		
		dbData.setTotalprice(floatToString(fnewTotalprice));
		
		dao.Store(dbData);
			
		
	}
	

	/*
	 * 订单基本信息更新处理
	 */
	@SuppressWarnings("unchecked")
	public void updateOrder(
			String piid,
			UserInfo userInfo,
			float newTotal,
			float oldTotal) throws Exception{
		
		B_OrderDao dao = new B_OrderDao();
		
		//取得更新前数据		
		String astr_Where = " piid='" + piid +"' AND deleteFlag='0' ";
		List<B_OrderData> list = dao.Find(astr_Where);					
		
		if(null != list && list.size() > 0){
			
			B_OrderData dbData = list.get(0);

			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"订单转移金额修正",userInfo);

			copyProperties(dbData,commData);
			//获取页面数据
			float dbTotal = stringToFloat( dbData.getTotalprice() );
			String total = floatToString(dbTotal - oldTotal + newTotal);
			dbData.setTotalprice(total);
			
			dao.Store(dbData);
			
		}
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
			
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,"OrderUpdate",userInfo);

			copyProperties(dbData,commData);
			//获取页面数据
			String piid = order.getPiid();
			dbData.setPiid(piid);
			dbData.setParentid(piid.substring(0,piid.length() -3));
			dbData.setSubid(piid.substring(piid.length() -3));
			dbData.setOrderid(order.getOrderid());
			dbData.setPaymentterm(order.getPaymentterm());
			//dbData.setCurrency(order.getCurrency());;
			dbData.setShippingcase(order.getShippingcase());
			dbData.setDeliverydate(order.getDeliverydate());
			dbData.setOrderdate(order.getOrderdate());
			dbData.setDeliveryport(order.getDeliveryport());
			dbData.setLoadingport(order.getLoadingport());
			dbData.setTotalprice(order.getTotalprice());
			dbData.setTeam(order.getTeam());
			dbData.setOrdercompany(order.getOrdercompany());
			
			dao.Store(dbData);
			
		}else{
			
			commData = commFiledEdit(
					Constants.ACCESSTYPE_INS,"OrderInsert",userInfo);
			copyProperties(order,commData);
			
			guid = BaseDAO.getGuId();
			order.setRecordid(guid);
			//重新编辑PI的流水号
			String PI = order.getPiid();
			String parentId = order.getParentid();
			String subId = PI.substring(parentId.length());
			order.setSubid(subId);
			
			dao.Create(order);	
		}
	}

	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail2(
			B_OrderDetailData newData) throws Exception{
		
		String where = " YSId ='" + newData.getYsid() +"' AND deleteFlag='0'";
		B_OrderDetailData dbData = null;
		List<B_OrderDetailData> list = new B_OrderDetailDao().Find(where);
				
		if(list != null && list.size() > 0){
			
			//获取页面数据
			dbData = list.get(0);
			
			copyProperties(dbData,newData);
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"订单转移",userInfo);
			copyProperties(dbData,commData);
			dbData.setTotalprice(newData.getTotalprice());//销售总价
			
			new B_OrderDetailDao().Store(dbData);
		}
	}
	
	/*
	 * 订单详情更新处理
	 */
	private void updateOrderDetail(
			B_OrderDetailData newData) throws Exception{
		
		B_OrderDetailData dbData = null;
		try{
			dbData = new B_OrderDetailDao(newData).beanData;
		}catch(Exception e){
			
		}		
		if(dbData == null || ("").equals(dbData)){
			//insert
			if(("020").equals(newData.getOrdertype())){

				insertOrderDetailPei(newData,newData.getPiid());
			}else{
				newData.setStatus(Constants.ORDER_STS_1);
				insertOrderDetail(newData,newData.getPiid());
				
			}
		}else{
			//获取页面数据
			copyProperties(dbData,newData);
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"OrderDetailUpdate",userInfo);
			copyProperties(dbData,commData);
			dbData.setRebaterate(reqModel.getRebateRate());//退税率
			
			new B_OrderDetailDao().Store(dbData);
		}
	}
	
	/*
	 * 详情删除处理
	 */
	@SuppressWarnings("unchecked")
	private void deleteOrder(String piid) throws Exception {
		String where = " piid = '"+piid +"'" ;
		List<B_OrderData> list = new B_OrderDao().Find(where);
		
		for(B_OrderData detail:list){
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"OrderDelete",userInfo);
			copyProperties(detail,commData);			
			new B_OrderDao().Store(detail);
		}
		
	}
	
	/*
	 * 详情删除处理
	 */
	@SuppressWarnings("unchecked")
	private void deleteOrderDetail(String oldPiId) throws Exception {
		String where = " piid = '"+oldPiId +"'" ;
		List<B_OrderDetailData> list = new B_OrderDetailDao().Find(where);
		
		for(B_OrderDetailData detail:list){
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"OrderDetailDelete",userInfo);
			copyProperties(detail,commData);			
			new B_OrderDetailDao().Store(detail);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void updateOrderDetail(
			String ysid,
			String quantity) throws Exception{
		
		B_OrderDetailData dbData = null;
		String where = " YSId ='" + ysid + "' AND deleteFlag='0' ";
		List<B_OrderDetailData> list  = new B_OrderDetailDao().Find(where);
			
		if(list == null || list.size() == 0){
			return;
		}else{
			//处理共通信息
			dbData = list.get(0);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"OrderCancelUpdate",userInfo);
			copyProperties(dbData,commData);
			
			float fcancel = stringToFloat(quantity);
			float foldQty = stringToFloat(dbData.getQuantity());//销售数量
			float flotTotal = stringToFloat(dbData.getTotalquantity());//销售数量+额外订单
			float fprice = stringToFloat(dbData.getPrice());
			float foldTotalPrice = stringToFloat(dbData.getTotalprice());
			
			float fnewQty = foldQty - fcancel;//新的销售数量
			float fnewTotal = flotTotal -fcancel;//销售数量+额外订单
			float fnewtotalPrice = fnewQty * fprice;//新的销售总计
			
			dbData.setQuantity(floatToString(fnewQty));
			dbData.setTotalquantity(floatToString(fnewTotal));
			dbData.setTotalprice(floatToString(fnewtotalPrice));
			
			new B_OrderDetailDao().Store(dbData);
			
			//更新整个PI的销售总价
			updataOrderTotalPrice(dbData.getPiid(),fnewtotalPrice,foldTotalPrice);
		}
	}
	
	public Model delete(String delData){
	
		B_OrderData order = reqModel.getOrder();
		String piid = order.getPiid();
		try {				
			ts = new BaseTransaction();										
			ts.begin();
			
			deleteOrder(piid);
			
			deleteOrderDetail(piid);			
			
			ts.commit();
		}
		catch(Exception e) {
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}		
		
		return model;
		
	}
	
	/*
	 * 
	 */
	public OrderModel createOrder() {

		try {			
      
			//耀升编号
	        String paternId = BusinessService.getYSCommCode();	        
			int YSMaxId = getYSIdByParentId(paternId);
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
			reqModel.setTeamList(
					util.getListOption(DicUtil.BUSINESSTEAM, ""));
			reqModel.setOrdercompanyList(
					util.getListOption(DicUtil.ORDERCOMPANY, ""));
			reqModel.setProductClassifyList(
					util.getListOption(DicUtil.PRODUCTCLASSIFY, ""));
			reqModel.setOrderNatureList(
					util.getListOption(DicUtil.ORDERNATURE,""));
			model.addAttribute("rebateRateList",
					util.getListOption(DicUtil.TAXREBATERATE,""));//退税率
			
			reqModel.setEndInfoMap(NORMAL, "", "");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return reqModel;
	
	}
	
	public void createOrderByMaterialId() throws Exception{

		String materialId = request.getParameter("materialId");
		
		B_OrderData order = getPiidByCustomer(materialId);
		//B_CustomerData cus = getCustomerInfo(order.getCustomerid());
		
		ArrayList<HashMap<String, String>> dbData = createOrderFromProduct(materialId);

		order.setCustomerid(dbData.get(0).get("customerId"));
		reqModel.setOrderData(order);
		//order.setCustomerid(cus.getCustomerid());
		//order.setCurrency(cus.getCurrency());
		//order.setDeliveryport(cus.getDestinationport());
		//order.setLoadingport(cus.getShippiingport());
		//order.setPaymentterm(cus.getPaymentterm());
		//order.setShippingcase(cus.getShippingcondition());

		createOrder();//下拉框
		String YSId = reqModel.getYSParentId()+String.valueOf(reqModel.getYSMaxId()+1);
		
		dbData.get(0).put("PIId",order.getPiid());
		dbData.get(0).put("orderDate",CalendarUtil.getToDay());
		dbData.get(0).put("deliveryDate",CalendarUtil.dateAddToString(CalendarUtil.getToDay(), 20));
		dbData.get(0).put("YSId",YSId);

		
		model.addAttribute("order",  dbData.get(0));
		model.addAttribute("detail", dbData);
		model.addAttribute("orderForm", reqModel);
	}

	
	private B_OrderData getPiidByCustomer(String materialId) throws Exception{
		
		String shortName = "";
		
		if(!(materialId == null || ("").equals(materialId))){
			String[] splits = materialId.split("[.]");
			if(splits.length ==4){
				shortName = splits[2].substring(0, splits[2].length()-3);
			}else if(splits.length ==5){
				shortName = splits[3].substring(0, splits[3].length()-3);
				
			}
		}
		
		String yearCode = BusinessService.getshortYearcode();
		String parentId = yearCode+shortName;
		HashMap<String, Object> map = getOrderSubIdByParentId(parentId);
		String piid = parentId + map.get("codeFormat");
		
		B_OrderData order = new B_OrderData();		
		order.setSubid(map.get("code").toString());
		order.setParentid(parentId);
		order.setPiid(piid);
		order.setCustomerid(shortName);
		
		
		return order;
	}

	/*
	 * 取得耀升编号的流水号
	 */
	public int getYSIdByParentId(String paternId) 
			throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;		
  
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getYSIdByParentId");		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件        
		userDefinedSearchCase.put("keywords1", paternId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
		
		return code;
	}
	

	/*
	 * 取得耀升编号的流水号
	 */
	@SuppressWarnings("unchecked")
	private int getYSMaxIdByParentId() 
			throws Exception {

		String parentId = BusinessService.getYSCommCode();
		int code = 1;
		B_OrderDetailDao dao = new B_OrderDetailDao();
				
		try {
			String astr_Where = " parentId= '" +parentId +"' order by subId+0";
			List<B_OrderDetailData> list = dao.Find(astr_Where);
			
			if(list == null || ("").equals(list))
					return code;
			
			int max=BusinessConstants.ORDERNO_MAX;
			int subid =0;
			int size = list.size();
			for(int i=0;i<max;i++){
				
				String s="";
				try{
					s = list.get(i).getSubid();
				}catch(Exception e){
					code = i;
					break;
				}
				
				try{
					subid = Integer.parseInt(s);
				}catch(Exception e){
					continue;//防止有非数字混入到编码中
				}
				
				if(subid > BusinessConstants.ORDERBNO_START){
					
					if(i<size-1){
						
						String s2 = list.get(i+1).getSubid();
						int subid2=0;
						try{
							subid2 = Integer.parseInt(s2);
						}catch(Exception e){
							continue;//防止有非数字混入到编码中									
						}
						if(subid+1 >= subid2){
							continue;
						}else{
							code = subid;
							break;
						}
						
					}else{
						code = subid;
						break;
						
					}//i<size-1					
					
				}else{
					continue;
				}
					
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

  
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
	
	public HashMap<String, Object> getDocumenterayName() throws Exception{
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		 String key1 = this.request.getParameter("key").toUpperCase();
		
		 this.dataModel.setQueryFileName("/business/order/orderquerydefine");
		 this.dataModel.setQueryName("getDocumenterayName");
		
		 this.baseQuery = new BaseQuery(this.request, this.dataModel);
		
		 this.userDefinedSearchCase.put("costName", key1);
		
		 this.baseQuery.setUserDefinedSearchCase(this.userDefinedSearchCase);
		 this.baseQuery.getYsFullData();
		
		 modelMap.put("data", this.dataModel.getYsViewData());
		
		 modelMap.put("retValue", "success");
		
		 return modelMap;
	}

	public void documentaryEdit() throws Exception{
		
		String YSId = this.request.getParameter("YSId");
		getOrderDetail(YSId);
	}
	
	
	public void getPurchaseOrder() throws Exception{
		
		String YSId = this.request.getParameter("YSId");
		getOrderDetail(YSId);
	}
	
	public void getOrderDetail(String YSId) throws Exception{
		
		  this.dataModel.setQueryFileName("/business/order/orderquerydefine");
		  this.dataModel.setQueryName("getOrderList");
		
		  this.baseQuery = new BaseQuery(this.request, this.dataModel);
		
		  this.userDefinedSearchCase.put("keyword1", YSId);
		  this.baseQuery.setUserDefinedSearchCase(this.userDefinedSearchCase);
		  this.baseQuery.getYsFullData();
		
		  this.model.addAttribute("order", this.dataModel.getYsViewData().get(0));
	}
	
	public HashMap<String, Object> getYsidList() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		  this.dataModel.setQueryFileName("/business/order/orderquerydefine");
		  this.dataModel.setQueryName("getOrderList");
		
		  this.baseQuery = new BaseQuery(this.request, this.dataModel);
		  String key = request.getParameter("key");
		  if(notEmpty(key))
			  key = key.toUpperCase();
		  this.userDefinedSearchCase.put("keyword1", key);
		  this.baseQuery.setUserDefinedSearchCase(this.userDefinedSearchCase);
		  this.baseQuery.getYsFullData();
		
		  modelMap.put("data", dataModel.getYsViewData());
		  
		  return modelMap;		  
		}
		
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> piidExistCheck() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String ExFlag = "";
		String PIId = request.getParameter("PIId");

		String where = " piid = '"+PIId +"' AND deleteFlag='0' " ;
		B_OrderDao dao = new B_OrderDao();
		List<B_OrderData> list; 
		list = (List<B_OrderData>)dao.Find(where);	
		if(list != null && list.size() > 0){
			ExFlag = "1";
		}	
		modelMap.put("ExFlag",ExFlag);
			
		return modelMap;		
	}
	
	@SuppressWarnings("unchecked")
	public B_OrderDetailData getOrderDetailById(String YSId) throws Exception{
		B_OrderDetailData data = new B_OrderDetailData();
		String where = " YSId='"+YSId+"' AND deleteFlag='0'";
		B_OrderDetailDao dao = new B_OrderDetailDao();
		List<B_OrderDetailData> list; 
		list = (List<B_OrderDetailData>)dao.Find(where);	
		if(list != null && list.size() > 0){
			data = list.get(0);
		}
			
		return data;		
	}
	
	
	@SuppressWarnings("unchecked")
	public String ysidExistCheck(String where) throws Exception{

		String ExFlag = "";
		B_OrderDetailDao dao = new B_OrderDetailDao();
		List<B_OrderDetailDao> list; 
		list = (List<B_OrderDetailDao>)dao.Find(where);	
		if(list != null && list.size() > 0){
			ExFlag = "1";
		}
			
		return ExFlag;		
	}
	

	public void getOrderDetail() throws Exception{
			
		String YSId = request.getParameter("YSId");	

		getOrderDetailByYSId(YSId);
		//return getPurchaseDetail(YSId);
	}
	
	public HashMap<String, Object> getOrderDetailByYSId(
			String YSId) throws Exception{

		return getOrderDetailByYSId(YSId,"");
		
	}
	
	public HashMap<String, Object> getOrderDetailByYSId(
			String YSId,String peiYsid) throws Exception{

		HashMap<String, Object> HashMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("peiYsid", peiYsid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		model.addAttribute("order", dataModel.getYsViewData().get(0));	
		HashMap.put("data", dataModel.getYsViewData());
		return HashMap;
		
	}
	
	
	
	public HashMap<String, Object> getOrderTrackingDetail() throws Exception {

		String YSId = request.getParameter("YSId");
		
		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("orderTrackingDetail");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		HashMap.put("data", dataModel.getYsViewData());
		
		return HashMap;
	}
	/**
	 * 订单关注
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> setOrderFollow() throws Exception {
	
		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		String followStatus = "";

		String YSId = request.getParameter("YSId");
		String where = " YSId='"+ YSId +"' and followType='"+Constants.FOLLOWTYPE_1+"' ";
		List<B_FollowData> list = new B_FollowDao().Find(where);
		if(list.size() > 0){
			
			B_FollowData data = list.get(0);		
			String status = data.getStatus();
			
			if((Constants.FOLLOWSTATUS_0).equals(status)){
				//取消关注
				data.setStatus(Constants.FOLLOWSTATUS_1);//取消关注
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"OrderFollowDelete",userInfo);
			}else{
				//再次关注
				data.setStatus(Constants.FOLLOWSTATUS_0);//关注
				commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
						"OrderFollowInsert",userInfo);
			}
			copyProperties(data,commData);
			
			
			new B_FollowDao().Store(data);
			
			followStatus = data.getStatus();
		}else{
			//首次关注
			B_FollowData data = new B_FollowData();		
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"OrderFollowInsert",userInfo);
			copyProperties(data,commData);
			
			guid = BaseDAO.getGuId();
			data.setRecordid(guid);
			data.setYsid(YSId);
			data.setFollowtype(Constants.FOLLOWTYPE_1);//订单
			data.setStatus(Constants.FOLLOWSTATUS_0);//关注
			
			new B_FollowDao().Create(data);

			followStatus = data.getStatus();
		}

		HashMap.put("status", followStatus);
		return HashMap;
	}
	
	public void orderCancelAddInit(){
		
	}
	
	public void orderCancelInsertAndView() throws Exception{
		String ysid = orderCancelInsert();
		
		getOrderCancelDetail(ysid);
	}
	
	
	public void orderCancelUpdateAndView() throws Exception{
		String ysid = orderCancelUpdate();
		
		getOrderCancelDetail(ysid);
	}
	
	
	public void orderCancelView() throws Exception{
		String ysid = request.getParameter("YSId");
		getOrderCancelDetail(ysid);
	}
	
	public void orderCancelEdit() throws Exception{
		String ysid = reqModel.getOrderCancel().getYsid();
		getOrderCancelDetail(ysid);
	}
	
	public void orderCancelDelete() throws Exception{

		ts = new BaseTransaction();	
		try {
			
			ts.begin();
			
			//删除退货
			B_OrderCancelData reqData = reqModel.getOrderCancel();
			String record = reqData.getRecordid();
			
			if(notEmpty(record)){

				B_OrderCancelData db = (B_OrderCancelData) new B_OrderCancelDao()
						.FindByPrimaryKey(reqData);
				
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"OrderCancelDelete",userInfo);
				copyProperties(db,commData);
				
				new B_OrderCancelDao().Store(db);

				
				//还原订单信息
				float quantity = stringToFloat(reqData.getCancelquantity()) * (-1);
				updateOrderDetail(
						reqData.getYsid(),
						floatToString(quantity));
			
			}
				
			

			
			ts.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
		}

	}
	
	private String orderCancelInsert() throws Exception{
		ts = new BaseTransaction();
		String ysid="";
		try {
			
			ts.begin();
					
			B_OrderCancelData reqData = reqModel.getOrderCancel();
			
			//插入订单退货
			insertOrderCancel(reqData);
			
			//更新订单信息	
			ysid = reqData.getYsid();
			updateOrderDetail(
					reqData.getYsid(),
					reqData.getCancelquantity());

			
			ts.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
		}
		
		return ysid;
		
	}
	
	private String orderCancelUpdate() throws Exception{
		ts = new BaseTransaction();
		String ysid="";
		try {
			
			ts.begin();
					
			B_OrderCancelData reqData = reqModel.getOrderCancel();
			
			//插入订单退货
			String oldQty = updateOrderCancel(reqData);
			
			//更新订单信息	
			ysid = reqData.getYsid();
			float reqQty = stringToFloat(reqData.getCancelquantity());
			float dbQty = stringToFloat(oldQty);
			float newQty = reqQty - dbQty;
			updateOrderDetail(
					ysid,
					floatToString(newQty));

			
			ts.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
		}
		
		return ysid;
		
	}
	
	@SuppressWarnings("unchecked")
	private void insertOrderCancel(B_OrderCancelData reqData) throws Exception{
		
		String ysid = reqData.getYsid();
		String where = " YSId ='" + ysid + "' AND deleteFlag='0' ";
		List<B_OrderCancelData> list = new B_OrderCancelDao().Find(where);
		
		if(list == null || list.size() == 0 ){

			//新增处理
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"OrderCancelInsert",userInfo);
			copyProperties(reqData,commData);
			guid = BaseDAO.getGuId();
			
			reqData.setRecordid(guid);
			reqData.setRemarks(replaceTextArea(reqData.getRemarks()));//字符转换
			reqData.setCanceldate(CalendarUtil.fmtYmdDate());
			
			new B_OrderCancelDao().Create(reqData);
		}else{
			//更新处理
			B_OrderCancelData db = list.get(0);
			
			float dbQty = stringToFloat(db.getCancelquantity());
			float reqQty = stringToFloat(reqData.getCancelquantity());
			String newQty = floatToString( dbQty + reqQty );

			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"OrderCancelUpdate",userInfo);
			copyProperties(reqData,commData);
			
			db.setCancelquantity(newQty);
			db.setRemarks(replaceTextArea(reqData.getRemarks()));//字符转换
			db.setCanceldate(CalendarUtil.fmtYmdDate());
			
			new B_OrderCancelDao().Store(db);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private String updateOrderCancel(B_OrderCancelData reqData) throws Exception{
		String rtnQty = "";
		String ysid = reqData.getYsid();
		String where = " YSId ='" + ysid + "' AND deleteFlag='0' ";
		List<B_OrderCancelData> list = new B_OrderCancelDao().Find(where);
		
		if(list == null || list.size() == 0 ){

			//新增处理
			rtnQty = reqData.getCancelquantity();
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"OrderCancelInsert",userInfo);
			copyProperties(reqData,commData);
			guid = BaseDAO.getGuId();
			
			reqData.setRecordid(guid);
			reqData.setRemarks(replaceTextArea(reqData.getRemarks()));//字符转换
			reqData.setCanceldate(CalendarUtil.fmtYmdDate());
			
			new B_OrderCancelDao().Create(reqData);
		}else{
			//更新处理
			B_OrderCancelData db = list.get(0);
			rtnQty = db.getCancelquantity();

			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"OrderCancelUpdate",userInfo);
			copyProperties(reqData,commData);
			
			db.setCancelquantity(reqData.getCancelquantity());
			db.setRemarks(replaceTextArea(reqData.getRemarks()));//字符转换
			db.setCanceldate(CalendarUtil.fmtYmdDate());
			
			new B_OrderCancelDao().Store(db);
		}
		
		return rtnQty;
		
	}
	public HashMap<String, Object> getContractDetail(String data) throws Exception {

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
		if (length != null){			
			iEnd = iStart + Integer.parseInt(length);		
		}	
		String key1 = getJsonData(data, "keyword1").trim().toUpperCase();
		String key2 = getJsonData(data, "keyword2").trim().toUpperCase();
				
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = new BaseQuery(request, dataModel);

		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getContractList");
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		String having = "1=1";
		sql = sql.replace("#", having);
		baseQuery.getYsQueryData(sql,having,iStart, iEnd);	
		
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
	
	private void getOrderCancelDetail(String ysid) throws Exception{
		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("orderCancelDetail");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", ysid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("order", dataModel.getYsViewData().get(0));	
		}
	}
}
