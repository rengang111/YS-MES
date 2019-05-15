package com.ys.business.service.order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.ys.business.action.model.common.FilePath;
import com.ys.business.action.model.order.ReceivableModel;
import com.ys.business.db.dao.B_PaymentDao;
import com.ys.business.db.dao.B_PaymentDetailDao;
import com.ys.business.db.dao.B_PaymentHistoryDao;
import com.ys.business.db.dao.B_ReceivableDao;
import com.ys.business.db.dao.B_ReceivableDetailDao;
import com.ys.business.db.dao.B_ReceivableOrderDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.data.B_PaymentData;
import com.ys.business.db.data.B_PaymentDetailData;
import com.ys.business.db.data.B_PaymentHistoryData;
import com.ys.business.db.data.B_ReceivableData;
import com.ys.business.db.data.B_ReceivableDetailData;
import com.ys.business.db.data.B_ReceivableOrderData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.ExcelUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class ReceivableService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private B_StockOutDao dao;
	private ReceivableModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public ReceivableService(){
		
	}

	public ReceivableService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			ReceivableModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_StockOutDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.response = response;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/financequerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	
	public HashMap<String, Object> doSearch(
			String data,String searchFlag) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_RECEIVABLE,data,session);
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
		
		dataModel.setQueryName("orderListForReceivabel");//付款一览
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);

		String having = "1=1 ";
		String year   = request.getParameter("year");
		String status = request.getParameter("status");
		String yewuzuId = request.getParameter("yewuzuId");
		String monthday = request.getParameter("monthday");
						
		//*** 收汇状态
		if(("070").equals(status)){
			userDefinedSearchCase.put("reserveDate", CalendarUtil.fmtYmdDate());
			having = " REPLACE(actualAndBankCnt,',','')+0 <= 0 ";
		}else if(("010").equals(status)){
			//未收款
		//	having = " REPLACE(actualAndBankCnt,',','')+0 <= 0 ";
		//}else if(("020").equals(status)){
			//部分收款
			having = " REPLACE(actualAndBankCnt,',','')+0 < REPLACE(format(orderPrice,2),',','')+0";
			//having = " REPLACE(actualAndBankCnt,',','')+0 > 0 AND REPLACE(actualAndBankCnt,',','')+0 < REPLACE(format(orderPrice,2),',','')+0";
		}else if(("030").equals(status)){
			//已收款
			having = "REPLACE(actualAndBankCnt,',','')+0 > 0 AND REPLACE(actualAndBankCnt,',','')+0 >= REPLACE(format(orderPrice,2),',','')+0 ";			
		}
		
		//*** 月份筛选
		if(("030").equals(status)){
			if(isNullOrEmpty(monthday)){//ALL
				monthday = "";
				if(isNullOrEmpty(year)){//年份选择
					year =  CalendarUtil.getYear();//当前年
				}
			}
		}else{
			year = "";
			monthday = "";
		}
		userDefinedSearchCase.put("year", year);
		userDefinedSearchCase.put("monthday", monthday);
			
		//*** 采购员选择
		if(("999").equals(yewuzuId)){
			userDefinedSearchCase.put("yewuzuId", "");				
		}else{
			userDefinedSearchCase.put("yewuzuId", yewuzuId);						
		}
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", having);
		System.out.println("收款查询："+sql);
		HashMap<String,Object> hashmap = baseQuery.getYsQueryDataAndSumRecord(
				sql,having,true,iStart, iEnd);	 
			
		ArrayList<HashMap<String, String>> list =
				(ArrayList<HashMap<String, String>>) hashmap.get("resultSumList");
		
		if(list.size() > 0){
			HashMap<String, Object>  mapSum = getRecordSumDate(list);
			modelMap.put("mapSum", mapSum); 		
		}
		
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
	
	private HashMap<String, Object> getRecordSumDate(ArrayList<HashMap<String, String>> list){
		HashMap<String, Object> map = new HashMap<String, Object>();

		float rmbOrderSum = 0;//应收 人民币
		float usdOrderSum = 0;//应收 美元
		float rmbActuaSum = 0;//实收 人民币
		float usdActuaSum = 0;//实收 美元
		float rmbSurplSum = 0;//剩余 人民币
		float usdSurplSum = 0;//剩余 美元
		
		for(HashMap<String, String> record:list){
			float orderPrice   = stringToFloat(record.get("orderPrice"));//应收款 
			float actualAmount = stringToFloat(record.get("actualCnt"));//实收
			String currencyId = record.get("currencyId");
						
			if(("USD").equals(currencyId)){
				usdOrderSum = usdOrderSum + orderPrice;
				usdActuaSum = usdActuaSum + actualAmount;
				usdSurplSum = usdSurplSum + (orderPrice - actualAmount);
			}else {
				rmbOrderSum = rmbOrderSum + orderPrice;
				rmbActuaSum = rmbActuaSum + actualAmount;
				rmbSurplSum = rmbSurplSum + (orderPrice - actualAmount);
			}
		}
		map.put("rmbOrderSum", rmbOrderSum);
		map.put("usdOrderSum", usdOrderSum);
		map.put("rmbActuaSum", rmbActuaSum);
		map.put("usdActuaSum", usdActuaSum);
		map.put("rmbSurplSum", rmbSurplSum);
		map.put("usdSurplSum", usdSurplSum);
		
		return map;
	}
	
	public void getRecivableFromOrder(String YSId) throws Exception{

		dataModel.setQueryName("orderListForReceivabel");		
		baseQuery = new BaseQuery(request, dataModel);	
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		String having = "1=1";
		sql = sql.replace("#",having);
		System.out.println("款明细查询："+sql);
		baseQuery.getYsFullData(sql,having);

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("orderDetail",dataModel.getYsViewData());			
		}	
	}
	

	public void receivableEditInit() throws Exception{
		String YSId = request.getParameter("YSId");
		String receivableSerialId = request.getParameter("receivableSerialId");
		
		getReceivableDetail("",receivableSerialId);
		
		getRecivableFromOrder(YSId);//
	}
	
	/**
	 * 单个订单收汇
	 * @throws Exception
	 */
	public void receivableAddInit() throws Exception{
		//String YSId = request.getParameter("YSId");

		
		String ysids = request.getParameter("ysids");

		//确认是否有过收汇
		//String[] list = ysids.split(",");
		/*
		for(String contractId:list){
			String where = " contractId='" + contractId +"' AND deleteFlag='0' ";
			B_PaymentDetailData payment = checkPaymentExsit(where);
			if(!(payment == null)){
				model.addAttribute("payment",payment );
				break;				
			}
		}
		*/
		
		getRecivableFromOrder(ysids);//
		
		B_ReceivableData receivable = new  B_ReceivableData();
		receivable = getReceivableRecordId(receivable);
		//receivable.setYsid(YSId);
		
		B_ReceivableDetailData reqDataDetail = new B_ReceivableDetailData();
		reqDataDetail.setReceivableid(receivable.getReceivableid());
		reqDataDetail = getReceivableDetailRecordId(reqDataDetail);
		
		reqModel.setReceivable(receivable);
		reqModel.setReceivableDetail(reqDataDetail);
		model.addAttribute("ysids",ysids);
		
	}
	
	/**
	 * 合并收汇
	 * @throws Exception
	 */
	public void receivableOrdersAddInit() throws Exception{
		//String YSId = request.getParameter("YSId");

		
		String ysids = request.getParameter("ysids");

		//确认付款申请是否存在
		//String[] list = ysids.split(",");
		/*
		for(String contractId:list){
			String where = " contractId='" + contractId +"' AND deleteFlag='0' ";
			B_PaymentDetailData payment = checkPaymentExsit(where);
			if(!(payment == null)){
				model.addAttribute("payment",payment );
				break;				
			}
		}
		*/
		
		//getRecivableFromOrder(YSId);//
		
		B_ReceivableData receivable = new  B_ReceivableData();
		receivable = getReceivableRecordId(receivable);
		//receivable.setYsid(YSId);
		
		B_ReceivableDetailData reqDataDetail = new B_ReceivableDetailData();
		reqDataDetail.setReceivableid(receivable.getReceivableid());
		reqDataDetail = getReceivableDetailRecordId(reqDataDetail);
		
		reqModel.setReceivable(receivable);
		reqModel.setReceivableDetail(reqDataDetail);
		model.addAttribute("ysids",ysids);
		
	}
	
	/**
	 * 继续收款
	 * @throws Exception
	 */
	public void receivableAddContinueInit() throws Exception{
		String detailId = request.getParameter("detailId");

		//getRecivableFromOrder(YSId);
		
		B_ReceivableData receivable = getReceivableById(detailId);
		if(receivable == null){
			receivable = new  B_ReceivableData();
			receivable = getReceivableRecordId(receivable);
			//receivable.setYsid(YSId);
		}
		
		B_ReceivableDetailData reqDataDetail = new B_ReceivableDetailData();
		reqDataDetail.setReceivableid(receivable.getReceivableid());
		reqDataDetail = getReceivableDetailRecordId(reqDataDetail);
		
		reqModel.setReceivable(receivable);
		reqModel.setReceivableDetail(reqDataDetail);
		
		model.addAttribute("receivableId",detailId);
		
	}
	
	public void receivableInsertAndView() throws Exception{

		String YSId = request.getParameter("YSId");
		
		String receivableId = receivableInsert();

		model.addAttribute("receivableId",receivableId);
		//getRecivableFromOrder(YSId);
		
	}
	
	public void receivableAginInsertAndView() throws Exception{

		
		String receivableId = receivableAginInsert();

		model.addAttribute("receivableId",receivableId);
		
	}

	public void receivableUpdateAndView() throws Exception{

		String YSId = request.getParameter("YSId");
		
		receivableInsert();
		
		getRecivableFromOrder(YSId);
		
	}
	
	
	public HashMap<String, Object> getReceivableDetail(
			String receivableId,
			String receivableSerialId) throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryName("receivableDetailById");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("receivableId", receivableId);
		userDefinedSearchCase.put("receivableSerialId", receivableSerialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
	
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("detail",dataModel.getYsViewData().get(0));
			modelMap.put("data",dataModel.getYsViewData());			
		}
		
		return modelMap;
	}
	
	public void receivableDetailViewInit() throws Exception{

		String receivableId = request.getParameter("receivableId");

		getReceivableDetail(receivableId,"");
	}
	
	

	/**
	 * 删除收款记录
	 * @throws Exception
	 */
	public void receivableDelete() throws Exception {

		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String receivableId = request.getParameter("receivableId");
			String detailid = request.getParameter("receivableSerialId");

			//删除收款明细
			deleteReceivableDetail(detailid);
			
			//check是否还有收款记录，否则删除头表
			B_ReceivableDetailData existFlag = checkReceivableExist(receivableId);
			
			//删除付款信息
			if(existFlag == null){
				deleteReceivable(receivableId);
			}else{

				B_ReceivableData dt = new B_ReceivableData();
				dt.setReceivableid(receivableId);
				dt.setStatus(Constants.RECEIVABLE_020);
				insertPayment(dt);	
			}			
			
			ts.commit();
		
		}catch(Exception e){
			ts.rollback();
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("unchecked")
	private B_ReceivableDetailData checkReceivableExist(String receivableId) throws Exception{
		B_ReceivableDetailData rtn = null;
		String where = " receivableId ='" + receivableId + "' AND deleteFlag='0' ";
		List<B_ReceivableDetailData> list = new B_ReceivableDetailDao().Find(where); 
		
		if(list.size() > 0)
			rtn = list.get(0);
		
		return rtn;
	}
	

	@SuppressWarnings("unchecked")
	private B_ReceivableData getReceivableById(String detailId) throws Exception{
		B_ReceivableData rtn = null;
		String where = " receivableId ='" + detailId + "' AND deleteFlag='0' ";
		List<B_ReceivableData> list = new B_ReceivableDao().Find(where); 
		
		if(list.size() > 0)
			rtn = list.get(0);
		
		return rtn;
	}
		
	/**
	 * 
	 * @param paymentStatus
	 * @return
	 */
	private String receivableInsert(){
		String receivableid = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_ReceivableData reqData = reqModel.getReceivable();
			B_ReceivableDetailData reqDataDetail = reqModel.getReceivableDetail();

			//取得收款编号
			receivableid = reqData.getReceivableid();
			if(isNullOrEmpty(receivableid)){
				reqData = getReceivableRecordId(reqData);	
				receivableid = reqData.getReceivableid();//重新取值
			}	
			//付款单状态
			float amountReceivable = stringToFloat(reqData.getAmountreceivable());//应付款
			float actualAmountCnt = getActualAmount(receivableid);
			float actualAmount = stringToFloat(reqDataDetail.getActualamount());
			float bankDeduction = stringToFloat(reqDataDetail.getBankdeduction());
			
			float amount = actualAmount + bankDeduction;//本次收款金额，包含扣款
			float receCnt = actualAmountCnt + amount;//到目前，共收款金额，包含扣款

			String status=Constants.RECEIVABLE_010;
			if(receCnt < amountReceivable){
				status=Constants.RECEIVABLE_020;
			}else{
				status=Constants.RECEIVABLE_030;
			}
			reqData.setStatus(status);
			insertPayment(reqData);		
			
			//订单明细
			insertReceivableOrder(receivableid);

			//收款明细
			reqDataDetail.setReceivableid(reqData.getReceivableid());
			
			String detailid = reqDataDetail.getReceivableserialid();
			if(isNullOrEmpty(detailid)){
				reqDataDetail = getReceivableDetailRecordId(reqDataDetail);//重新设置流水号
				detailid = reqDataDetail.getReceivableserialid();
			}	
			
			insertPaymentDetail(reqDataDetail);		
			
			ts.commit();			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return receivableid;
		
	}


	/**
	 * 
	 * @param paymentStatus
	 * @return
	 */
	private String receivableAginInsert(){
		String receivableid = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_ReceivableData reqData = reqModel.getReceivable();
			B_ReceivableDetailData reqDataDetail = reqModel.getReceivableDetail();

			//取得收款编号
			receivableid = reqData.getReceivableid();
			if(isNullOrEmpty(receivableid)){
				reqData = getReceivableRecordId(reqData);	
				receivableid = reqData.getReceivableid();//重新取值
			}	
			//付款单状态
			float amountReceivable = stringToFloat(reqData.getAmountreceivable());//应付款
			float actualAmountCnt = getActualAmount(receivableid);
			float actualAmount = stringToFloat(reqDataDetail.getActualamount());
			float bankDeduction = stringToFloat(reqDataDetail.getBankdeduction());
			
			float amount = actualAmount + bankDeduction;//本次收款金额，包含扣款
			float receCnt = actualAmountCnt + amount;//到目前，共收款金额，包含扣款

			String status=Constants.RECEIVABLE_010;
			if(receCnt < amountReceivable){
				status=Constants.RECEIVABLE_020;
			}else{
				status=Constants.RECEIVABLE_030;
			}
			reqData.setStatus(status);
			insertPayment(reqData);		
			
			//订单明细
			//insertReceivableOrder(receivableid);

			//收款明细
			reqDataDetail.setReceivableid(reqData.getReceivableid());
			
			String detailid = reqDataDetail.getReceivableserialid();
			if(isNullOrEmpty(detailid)){
				reqDataDetail = getReceivableDetailRecordId(reqDataDetail);//重新设置流水号
				detailid = reqDataDetail.getReceivableserialid();
			}	
			
			insertPaymentDetail(reqDataDetail);		
			
			ts.commit();			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return receivableid;
		
	}
	
	private void insertReceivableOrder(
			String receivableid) throws Exception {

		List<B_ReceivableOrderData> orderList = reqModel.getOrderList();
		
		try {
			String where = " receivableId ='" + receivableid + "' AND deleteFlag='0'";
			new B_ReceivableOrderDao().RemoveByWhere(where);

		} catch (Exception e) {
			// nothing
		}		
		
		//插入新数据		
		for(B_ReceivableOrderData order:orderList){
			order.setReceivableid(receivableid);
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"ReceivableOrderInsert",userInfo);
			copyProperties(order,commData);
			guid = BaseDAO.getGuId();
			order.setRecordid(guid);
			
			new B_ReceivableOrderDao().Create(order);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void insertPayment(
			B_ReceivableData payment) throws Exception {
		
		B_ReceivableData db = null;
		List<B_ReceivableData> list = null;
		try {
			String where = " receivableId ='" + payment.getReceivableid() + "' AND deleteFlag='0'";
			list = new B_ReceivableDao().Find(where);

		} catch (Exception e) {
			// nothing
		}		
		
		if(list == null || list.size() == 0){
			//插入新数据
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"ReceivableInsert",userInfo);
			copyProperties(payment,commData);
			guid = BaseDAO.getGuId();
			payment.setRecordid(guid);
			
			new B_ReceivableDao().Create(payment);
		}else{
			//更新
			db = list.get(0);		
			copyProperties(db,payment);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ReceivableUpdate",userInfo);
			copyProperties(db,commData);
			
			new B_ReceivableDao().Store(db);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void insertPaymentDetail(
			B_ReceivableDetailData detail) throws Exception {

		B_ReceivableDetailData db = null;
		String where = " receivableId='"+detail.getReceivableid() + 
				"' AND receivableserialId='"+detail.getReceivableserialid()+
				"' AND deleteFlag='0' ";
		
		List<B_ReceivableDetailData> list = new B_ReceivableDetailDao().Find(where);

		if(list == null || list.size() <= 0 ){ 
			
			//插入新数据
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
			"ReceivableInsert",userInfo);
			copyProperties(detail,commData);
			guid = BaseDAO.getGuId();
			detail.setRecordid(guid);
			detail.setPayee(userInfo.getUserId());
					
			new B_ReceivableDetailDao().Create(detail);
		}else{
			//更新
			db = list.get(0);
			copyProperties(db,detail);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"ReceivableUpdate",userInfo);
			copyProperties(db,commData);
			db.setPayee(userInfo.getUserId());
			
			new B_ReceivableDetailDao().Store(db);
		}		
	}
	
	public HashMap<String, String>  getPaymentDetail(String paymentid) throws Exception{

		HashMap<String, String> payment = null;
		
		dataModel.setQueryName("paymentDetailById");
		userDefinedSearchCase.put("paymentId", paymentid);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0){
			payment = dataModel.getYsViewData().get(0);
			model.addAttribute("payment",payment);
			//contractIds = dataModel.getYsViewData().get(0).get("contractIds");
			modelMap.put("data", dataModel.getYsViewData());
		}	
	
		return payment;
	}
	

	public HashMap<String, Object>  getOrderDetailByYsids() throws Exception{

		HashMap<String, Object> payment = new HashMap<String, Object>();
		String ysids = request.getParameter("ysids");
		
		dataModel.setQueryName("orderListForReceivabel");
		userDefinedSearchCase.put("ysids", ysids);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		String having = "1=1";
		String sql = baseQuery.getSql();
		sql = sql.replace("#", having);
		baseQuery.getYsFullData(sql,having);
		
		payment.put("data", dataModel.getYsViewData());
	
		return payment;
	}

	public HashMap<String, Object>  getOrderDetail() throws Exception{

		HashMap<String, Object> payment = new HashMap<String, Object>();
		String receivableId = request.getParameter("receivableId");
		
		dataModel.setQueryName("orderListForReceivabelById");
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		String sql = baseQuery.getSql();
		sql = sql.replace("#", receivableId);
		System.out.println("订单详情："+sql);
		baseQuery.getYsFullData(sql,receivableId);
		
		payment.put("data", dataModel.getYsViewData());
	
		return payment;
	}
	

	public HashMap<String, Object> getProductStockoutDetail() throws Exception{

		HashMap<String, Object> payment = new HashMap<String, Object>();
		String receivableId = request.getParameter("receivableId");
		
		dataModel.setQueryName("orderStockoutForReceivabelById");
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		String sql = baseQuery.getSql();
		sql = sql.replace("#", receivableId);
		System.out.println("订单出库详情："+sql);
		baseQuery.getYsFullData(sql,receivableId);
		
		payment.put("data", dataModel.getYsViewData());
	
		return payment;
	}
	
	public String  getPaymentSumAmount(String paymentid) throws Exception{

		String payment = "0";
		
		dataModel.setQueryName("sumPaymentHistoryByPaymentId");
		userDefinedSearchCase.put("paymentId", paymentid);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		if(dataModel.getRecordCount() > 0){			
			payment = dataModel.getYsViewData().get(0).get("paymentAmount");
		}	
	
		return payment;
	}
	
	public void  getPaymentHistoryList(String paymentid) throws Exception{

		dataModel.setQueryName("paymentHistoryList");
		userDefinedSearchCase.put("paymentId", paymentid);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		model.addAttribute("history",dataModel.getYsViewData());
		
	}
	
	public boolean  getPaymentHistory(String paymentid) throws Exception{

		boolean rtnFlag=false;
		String where = " paymentId ='" + paymentid +"' AND deleteFlag='0' ";
		
		List list = new B_PaymentHistoryDao().Find(where);
		
		if(list.size() > 0)
			rtnFlag = true;
		
		return rtnFlag;
		
	}
	
	private B_PaymentHistoryData getPaymentHistoryId(String paymentid) throws Exception{

		dataModel.setQueryName("paymentHistoryMAXId");
		userDefinedSearchCase.put("parentId", paymentid);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");	
		
		String id = BusinessService.getPaymentHistoryId(
						paymentid,
						code,
						false);	
		
		B_PaymentHistoryData data = new B_PaymentHistoryData();
		data.setPaymenthistoryid(id);
		data.setParentid(paymentid);
		data.setSubid(code);
		
		return data;
	}
	

	public float getActualAmount(
			String  receivableid) throws Exception {
	
		dataModel.setQueryName("getReceivableCountById");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("receivableId", receivableid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		//
		float rtn = 0;
		if(baseQuery.getRecodCount() > 0 ){
			rtn = stringToFloat(dataModel.getYsViewData().get(0).get("actualAmountCnt"));
		}
		
		return rtn;		
	}

	
	public B_ReceivableData getReceivableRecordId(
			B_ReceivableData data) throws Exception {
	
		String parentId = BusinessService.getshortYearcode()+
				BusinessConstants.SHORTNAME_SK;
		dataModel.setQueryName("getMAXReceivableId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");	
		
		String id = BusinessService.getReceivableId(
						parentId,
						code,
						false);	
		
		data.setReceivableid(id);
		data.setParentid(parentId);
		data.setSubid(code);			
		
		return data;
		
	}
	
	public B_ReceivableDetailData getReceivableDetailRecordId(
			B_ReceivableDetailData data) throws Exception {
	
		String parentId = data.getReceivableid();
		
		dataModel.setQueryName("getMAXReceivableDetailId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("receivableId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");	
		
		String id = BusinessService.getReceivableHistoryId(
						parentId,
						code,
						false);	
		
		data.setReceivableserialid(id);
		data.setSubid(code);			
		
		return data;
		
	}

	public void getOrderDetail(String YSId) throws Exception {

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		String sql = baseQuery.getSql();
		sql = sql.replace("#", YSId);
		System.out.println("订单查询："+sql);
		
		baseQuery.getYsFullData(sql,YSId);

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("orderDetail",dataModel.getYsViewData());			
		}		
	}
	

	
	public HashMap<String, Object> uploadPhotoAndReload(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) throws Exception {

		B_ReceivableData head = reqModel.getReceivable();

		//判断是否有编号
		String detailid = head.getReceivableid();
		if(isNullOrEmpty(detailid)){			
			head = getReceivableRecordId(head);//
		}
		
		String ysid = detailid;//之前是以耀升编号为单位收款，现在可以合并收款，简单对应

		FilePath file = getPath(ysid,detailid);
		String savePath = file.getSave();						
		String viewPath = file.getView();
		String webPath = file.getWeb();


		String photoName  = ysid+ "-" + detailid + "-" + CalendarUtil.timeStempDate(); 
		
		uploadPhoto(headPhotoFile,photoName,viewPath,savePath,webPath);		

		ArrayList<String> list = getFiles(savePath,webPath);
		modelMap.put(fileList, list);
		modelMap.put(fileCount, list.size());
	
		return modelMap;
	}
	
	public HashMap<String, Object> deletePhotoAndReload(
			String folderName,String fileList,String fileCount) throws Exception {

		String path = request.getParameter("path");
		
		B_ReceivableData head = reqModel.getReceivable();
		;
		String ysid = head.getYsid();
		String detailid = head.getReceivableid();

		deletePhoto(path);//删除图片

		getPhoto(ysid,detailid,folderName,fileList,fileCount);
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getProductPhoto() throws Exception {
		
		//String ysid = request.getParameter("YSId");
		String detailid = request.getParameter("detailId");

		getPhoto(detailid,detailid,"product","productFileList","productFileCount");//废掉耀升编号
	
		return modelMap;
	}
	

	private void getPhoto(
			String supplierId,String paymentId,
			String folderName,String fileList,String fileCount) {

		
		FilePath file = getPath(supplierId,paymentId);
		String savePath = file.getSave();						
		String viewPath = file.getWeb();
		
		try {

			ArrayList<String> list = getFiles(savePath,viewPath);
			modelMap.put(fileList, list);
			modelMap.put(fileCount, list.size());
							
		}
		catch(Exception e) {
			System.out.println(e.getMessage());

		}
	}
	
	private FilePath getPath(String key1,String key2){
		FilePath filePath = new FilePath();
		
		filePath.setSave(
				session.getServletContext().
				getRealPath(BusinessConstants.PATH_RECEIVABLEFILE)
				+"/"+key1+"/"+key2
				);	
		filePath.setView(
				session.getServletContext().
				getRealPath(BusinessConstants.PATH_RECEIVABLEVIEW)
				+"/"+key1+"/"+key2
				);	

		filePath.setWeb(BusinessConstants.PATH_RECEIVABLEVIEW
				+key1+"/"+key2+"/"
				);	
		
		return filePath;
	}
	
	@SuppressWarnings({ "unchecked" })
	private void deleteReceivable(String detailId) throws Exception {
		
		String where = " receivableId ='" + detailId + "' AND deleteFlag='0' ";
		List<B_ReceivableData> list = null;
		try {
			list = new B_ReceivableDao().Find(where);

		} catch (Exception e) {
			// nothing
		}		
		if(list.size()<= 0)
			return;
		
		//更新
		B_ReceivableData db = list.get(0);
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"PaymentDelete",userInfo);
		copyProperties(db,commData);		

		new B_ReceivableDao().Store(db);
		
	}
	
	@SuppressWarnings({ "unchecked" })
	private void deleteReceivableDetail(String detailId) throws Exception {

		String where = " receivableSerialId ='" + detailId + "' AND deleteFlag='0' ";
		List<B_ReceivableDetailData> list = null;
		try {
			list = new B_ReceivableDetailDao().Find(where);

		} catch (Exception e) {
			// nothing
		}		
		if(list.size()<= 0)
			return;
		
		//更新
		B_ReceivableDetailData dt = list.get(0);

		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"ReceivableDelete",userInfo);
		copyProperties(dt,commData);	
		new B_ReceivableDetailDao().Store(dt);
		
		
	}
	
	
	public void downloadExcelForPayment() throws Exception{
		
		//设置响应头，控制浏览器下载该文件
				
		//baseBom数据取得
		List<Map<Integer, Object>>  datalist = getStockinList();		
		
		String fileName = CalendarUtil.timeStempDate()+".xls";
		String dest = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)
				+"/"+File.separator+fileName;
       
		String tempFilePath = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_EXCELTEMPLATE)+File.separator+"overduePaymen.xls";
        File file = new File(dest);
       
        OutputStream out = new FileOutputStream(file);         
        ExcelUtil excel = new ExcelUtil(response);


        //读取模板
        Workbook wbModule = excel.getTempWorkbook(tempFilePath);
        //数据填充的sheet
        int sheetNo=0;
        //title
        Map<String, Object> dataMap = new HashMap<String, Object>();
        wbModule = excel.writeData(wbModule, dataMap, sheetNo);        
        //detail
        //必须为列表头部所有位置集合,输出 数据单元格样式和头部单元格样式保持一致
		String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "overduePaymenExcel");
		String[] heads = title.split(",",-1);
        excel.writeDateList(wbModule,heads,datalist,sheetNo);
         
        //写到输出流并移除资源
        excel.writeAndClose(tempFilePath, out);
        System.out.println("导出成功");
        out.flush();
        out.close();
        
      //***********************Excel下载************************//
        excel.downloadFile(dest,fileName);
	}
	
	public List<Map<Integer, Object>> getStockinList() throws Exception {
			
		String key1 = request.getParameter("key1");
		String key2 = request.getParameter("key2");
		String searchType = request.getParameter("searchType");
		
		dataModel.setQueryName("contractListForPaymenRequest");//领料单一览
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
	
		String having = "stockinQty >= contractQty ";
		if(("before").equals(searchType)){
			having = "stockinQty < contractQty ";
		}
		String finishStatus = request.getParameter("finishStatus");
		if(("070").equals(finishStatus)){
			finishStatus = "010";//逾期未付款
			userDefinedSearchCase.put("agreementDate", CalendarUtil.fmtYmdDate());
			userDefinedSearchCase.put("finishStatus", finishStatus);
		}
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql().replace("#", having);
		System.out.println("付款申请EXCEL导出："+sql);
		ArrayList<HashMap<String, String>>  hashMap = baseQuery.getYsFullData(sql,having);	

		List<Map<Integer, Object>> listMap = new ArrayList<Map<Integer, Object>>();
		for(int i=0;i<hashMap.size();i++){
			String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "overduePaymenTitle");
			String[] titles = title.split(",",-1);
			Map<Integer, Object> excel = new HashMap<Integer, Object>();
			for(int j=0;j<titles.length;j++){
				excel.put(j,hashMap.get(i).get(titles[j]));		
			}
			listMap.add(excel);
		}
		
		return  listMap;

	}
	
	public void receivableSearchMainInit() throws Exception{

		ArrayList<HashMap<String, String>> list = getYewuzurById();
		ArrayList<HashMap<String, String>> year = getYearById();

		model.addAttribute("yewuzu",list);
		model.addAttribute("yearList",year);
		model.addAttribute("year",util.getListOption(DicUtil.BUSINESSYEAR, ""));
	}
}
