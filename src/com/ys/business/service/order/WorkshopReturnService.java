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
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.PurchaseOrderModel;
import com.ys.business.action.model.order.WorkshopReturnModel;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_PurchasePlanDetailDao;
import com.ys.business.db.dao.B_WorkshopReturnDao;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_PurchasePlanDetailData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.db.data.B_WorkshopReturnData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.business.service.order.CommonService;

@Service
public class WorkshopReturnService extends CommonService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_PurchaseOrderDao orderDao;
	private B_PurchaseOrderDetailDao detailDao;
	private WorkshopReturnModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;	
	HttpSession session;

	public WorkshopReturnService(){
		
	}

	public WorkshopReturnService(Model model,
			HttpServletRequest request,
			HttpSession session,
			WorkshopReturnModel reqModel,
			UserInfo userInfo){
		
		this.orderDao = new B_PurchaseOrderDao();
		this.detailDao = new B_PurchaseOrderDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.session = session;
		this.userInfo = userInfo;
		this.dataModel = new BaseModel();
		this.userDefinedSearchCase = new HashMap<String, String>();
		this.dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}

	
	public HashMap<String, Object> getContractListForWorkshopReturn(
			String data,String formId) throws Exception {
		
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
		//dataModel.setQueryName("getContractListForWorkshopReturn");
		
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


	
	@SuppressWarnings("unchecked")
	public List<B_WorkshopReturnData> getWorkshopReturnDetail(
			String workshopReturnId) throws Exception {
			
		String astr_Where = "workshopReturnId = '"+workshopReturnId+"'";
		return (List<B_WorkshopReturnData>)
				new B_WorkshopReturnDao().Find(astr_Where);
	}
	
	
	public HashMap<String, Object> getWorkshopReturnList(
			String YSId,
			String workshopReturnId) throws Exception {

		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		dataModel.setQueryName("getWorkshopReturnList");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("workshopReturnId", workshopReturnId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		modelMap = new HashMap<String, Object>();
		modelMap.put("data",dataModel.getYsViewData());
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("workshop",dataModel.getYsViewData().get(0));
			model.addAttribute("workshopList",dataModel.getYsViewData());
		}
		
		return modelMap;
		
	}
	
	
	
	/**
	 * 插入处理
	 */
	private void insertWorkshopRentun(
			B_WorkshopReturnData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"WorkshopReturnInsert",userInfo);
		copyProperties(data,commData);
		
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setReturnperson(userInfo.getUserId());
		
		new B_WorkshopReturnDao().Create(data);	
	}
			
	
	public void updateWorkshopRentunInit() throws Exception{

		String workshopReturnId = request.getParameter("workshopReturnId");
		String YSId = request.getParameter("YSId");
		
		//跳转到查看页面
		getOrderDetail(YSId);

		getWorkshopReturnList(YSId,workshopReturnId);
	}

	
		
	
	/*
	 * 更新处理
	 */
	private void deleteWorkshopRentun(String id) 
			throws Exception{		
		
		String where = "workshopReturnId = '" + id + "'" ; 
			
		new B_WorkshopReturnDao().RemoveByWhere(where);	
		
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

	public void createWorkshopRentunInit() throws Exception {

		String YSId = request.getParameter("YSId");
		if(YSId== null || ("").equals(YSId))
			return;
		
		//订单详情
		getOrderDetail(YSId);
	
	}
	

	/**
	 * 车间退货处理
	 * @throws Exception
	 */
	public void createWorkshopRentun() throws Exception {
		
		String YSId = insert();
		
		//跳转到查看页面
		getOrderDetail(YSId);

		//getWorkshopReturnList(workshop.getYsid(),contractId);
	}
	
	/**
	 * 车间退货处理
	 * @throws Exception
	 */
	private String insert() throws Exception {
			
		ts = new BaseTransaction();

		B_WorkshopReturnData workshop = reqModel.getWorkshopReturn();
		List<B_WorkshopReturnData> reqList = reqModel.getWorkshopRetunList();
		String YSId = workshop.getYsid();
		try {
			ts.begin();			
			
			//创建退货编号
			String workshopReturnId = YSId + "-"+ CalendarUtil.timeStempDate();
						
			//退货明细
			for(B_WorkshopReturnData dt:reqList){

				float quantity = stringToFloat(dt.getQuantity());
				if(quantity <= 0)
					continue;
				
				dt.setWorkshopreturnid(workshopReturnId);
				dt.setYsid(workshop.getYsid());
				dt.setTaskid(workshop.getTaskid());
				dt.setReturndate(workshop.getReturndate());
				insertWorkshopRentun(dt);	
			}
			
			ts.commit();
			
		}catch(Exception e){
			ts.rollback();
			System.out.println(e.getMessage());
		}
		
		return YSId;
	}
	
	/**
	 * 车间退货处理
	 * @throws Exception
	 */
	public void updateWorkshopAndView() throws Exception {

		String YSId = update();
		
		//跳转到查看页面
		getOrderDetail(YSId);
	}

	/**
	 * 车间退货处理
	 * @throws Exception
	 */
	private String update() throws Exception {
			
		ts = new BaseTransaction();

		B_WorkshopReturnData workshop = reqModel.getWorkshopReturn();
		List<B_WorkshopReturnData> reqList = reqModel.getWorkshopRetunList();
		String YSId = workshop.getYsid();
		try {
			ts.begin();			
			
			//旧的退货编号
			String oldWorkshopReturnId = workshop.getWorkshopreturnid();
			
			//删除旧的退货
			deleteWorkshopRentun(oldWorkshopReturnId);

			//新的退货编号
			String workshopReturnId = YSId + "-"+ CalendarUtil.timeStempDate();
			
			//退货明细
			for(B_WorkshopReturnData dt:reqList){
				
				dt.setWorkshopreturnid(workshopReturnId);
				dt.setYsid(workshop.getYsid());
				dt.setTaskid(workshop.getTaskid());
				dt.setReturndate(workshop.getReturndate());
				insertWorkshopRentun(dt);				
			}
			
			ts.commit();
			
		}catch(Exception e){
			ts.rollback();
			System.out.println(e.getMessage());
		}
		
		return YSId;
	}
	
	/**
	 * 车间退货处理
	 * @throws Exception
	 */
	public void workshopRentunDetailView() throws Exception {
			
		String YSId = request.getParameter("YSId");

		if(YSId == null || ("").equals(YSId))
			return;		

		//订单详情
		getOrderDetail(YSId);
		
	}
	
	public HashMap<String, Object> getPurchasePlanDetail() throws Exception {

		String YSId = request.getParameter("YSId");
		if(YSId == null || ("").equals(YSId))
			return null;
		//物料需求表
		return getPurchasePlan(YSId);
	}
	
	private HashMap<String, Object> getPurchasePlan(String YSId) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		dataModel.setQueryName("getPurchasePlanDetailGroup");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		
		modelMap.put("data", dataModel.getYsViewData());		
		
		return modelMap;		
	}

}
