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
import com.ys.business.action.model.order.PaymentModel;
import com.ys.business.db.dao.B_PaymentDao;
import com.ys.business.db.dao.B_PaymentDetailDao;
import com.ys.business.db.dao.B_PaymentHistoryDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.data.B_PaymentData;
import com.ys.business.db.data.B_PaymentDetailData;
import com.ys.business.db.data.B_PaymentHistoryData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

import javassist.bytecode.stackmap.TypeData.UninitData;

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
public class PaymentService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private B_StockOutDao dao;
	private PaymentModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public PaymentService(){
		
	}

	public PaymentService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			PaymentModel reqModel,
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
		
		String[] keyArr = getSearchKey(Constants.FORM_PAYMENTREQUEST,data,session);
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
		
		dataModel.setQueryName("contractListForPaymenRequest");//领料单一览
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);

		String having = "stockinQty >= contractQty ";
		if(("before").equals(searchFlag)){
			having = "stockinQty < contractQty ";
		}
		String finishStatus = request.getParameter("finishStatus");
		if(("070").equals(finishStatus)){
			finishStatus = "010";//逾期未付款
			userDefinedSearchCase.put("agreementDate", CalendarUtil.fmtYmdDate());
			userDefinedSearchCase.put("finishStatus", finishStatus);
			
		}
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", having);
		System.out.println("付款申请："+sql);
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
	
	public HashMap<String, Object> approvalSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_PAYMENTAPPROVAL,data,session);
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
		
		dataModel.setQueryName("paymenRequestList");//申请单一览
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2)){
			userDefinedSearchCase.put("approvalStatus", "");
		}
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
	
	public HashMap<String, Object> finishSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_PAYMENTAPPROVAL,data,session);
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
		
		dataModel.setQueryName("paymenApprovalList");//申请单一览:审核通过
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2)){
			userDefinedSearchCase.put("finishStatus", "");
		}
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
	public void applyAddInit() throws Exception {

		String contractIds = request.getParameter("contractIds");

		//确认付款申请是否存在
		String[] list = contractIds.split(",");
		for(String contractId:list){
			String where = " contractId='" + contractId +"' AND deleteFlag='0' ";
			B_PaymentDetailData payment = checkPaymentExsit(where);
			if(!(payment == null)){
				model.addAttribute("payment",payment );
				break;				
			}
		}
		
		//合同详情
		getContractDetail(contractIds);
		
		model.addAttribute("contractIds",contractIds);
		model.addAttribute("taxRateList",
				util.getListOption(DicUtil.TAXREBATERATE,""));//退税率
		model.addAttribute("chargetypeList",
				util.getListOption(DicUtil.CHARGETYPE,""));//扣款方式
	
	}
	

	public String approvalInit() throws Exception {
		
		String rtnFlg = "";
		String paymentid = request.getParameter("paymentId");
		
		//付款申请详情
		HashMap<String, String> map = getPaymentDetail(paymentid);
		if(!(map == null)){
			String contractId = map.get("contractIds");
			//供应商
			getContractDetail(contractId);
			
			if(notEmpty(map.get("approvalDate")))
				rtnFlg = "查看";
		}
		
		reqModel.setApprovalOption(util.getListOption(DicUtil.DIC_APPROVL, ""));
		reqModel.setInvoiceTypeOption(util.getListOption(DicUtil.DIC_INVOICETYPE, ""));

		return rtnFlg;
	}

	public String finishAddOrView() throws Exception {
		
		String rtnFlg = "";
		String paymentid = request.getParameter("paymentId");
		
		//付款记录
		boolean  record = getPaymentHistory(paymentid);
		if(record){			
			rtnFlg = "查看";
			getPaymentHistoryList(paymentid);
		}else{
			B_PaymentHistoryData history = getPaymentHistoryId(paymentid);
			reqModel.setHistory(history);
		}
				
		//付款申请详情
		HashMap<String, String> map = getPaymentDetail(paymentid);
		if(!(map == null)){
			String contractId = map.get("contractIds");
			//供应商
			getContractDetail(contractId);
			
			//取得已付款信息
			String amount = getPaymentSumAmount(paymentid);
			model.addAttribute("paymentAmount",amount);
		}
		
		reqModel.setCurrencyList(util.getListOption(DicUtil.DENOMINATIONCURRENCY, ""));
		reqModel.setPaymentMethodList(util.getListOption(DicUtil.DIC_PAYMENTMETHOD, ""));

		return rtnFlg;
	}
	
	public void finishView() throws Exception {
		
		String paymentid = request.getParameter("paymentId");
		
		//付款记录
		getPaymentHistoryList(paymentid);
		
				
		//付款申请详情
		HashMap<String, String> map = getPaymentDetail(paymentid);
		if(!(map == null)){
			String contractId = map.get("contractIds");
			//供应商
			getContractDetail(contractId);
		}
		
	}
	
	public String finishAddInit() throws Exception {
		
		String rtnFlg = "";
		String paymentid = request.getParameter("paymentId");
		
		//付款单编号
		B_PaymentHistoryData history = getPaymentHistoryId(paymentid);
		reqModel.setHistory(history);		
		
		//付款申请详情
		HashMap<String, String> map = getPaymentDetail(paymentid);
		if(!(map == null)){
			String contractId = map.get("contractIds");
			//供应商
			getContractDetail(contractId);
			
			//取得已付款信息
			String amount = getPaymentSumAmount(paymentid);
			model.addAttribute("paymentAmount",amount);
		}
		
		reqModel.setCurrencyList(util.getListOption(DicUtil.DENOMINATIONCURRENCY, ""));
		reqModel.setPaymentMethodList(util.getListOption(DicUtil.DIC_PAYMENTMETHOD, ""));

		return rtnFlg;
	}

	public void applyUpdateInit() throws Exception {
		B_PaymentData payment = reqModel.getPayment();
		
		String recordId=payment.getRecordid();
		getPaymentDetailDB(recordId);
		
		//供应商
		getContractDetail(payment.getContractids());
		
		model.addAttribute("taxRateList",
				util.getListOption(DicUtil.TAXREBATERATE,""));//退税率
		model.addAttribute("chargetypeList",
				util.getListOption(DicUtil.CHARGETYPE,""));//扣款方式
	}

	private void getPaymentDetailDB(String recordId) throws Exception {

		B_PaymentData payment = reqModel.getPayment();
		
		try{
			payment = new B_PaymentDao(payment).beanData;
		}catch(Exception e){
			e.printStackTrace();
		}
		if( payment == null || ("").equals(payment) )
			return;
		reqModel.setPayment(payment);
	}
	
	public void editProduct() throws Exception {
		String YSId = request.getParameter("YSId");
		String receiptId = request.getParameter("receiptId");
		
		//取得订单信息
		getOrderDetail(YSId);
		//getArrivaRecord(receiptId);//入库明细

		model.addAttribute("packagingList",util.getListOption(DicUtil.DIC_PACKAGING, ""));
		model.addAttribute("receiptId",receiptId);//已入库
	
	}

	//public void printReceipt() throws Exception {
	//	String YSId = request.getParameter("YSId");
		
		//取得订单信息
	//	getOrderDetail(YSId);
		//getArrivaRecord(receiptId);//入库明细	
	//}

	public void printProductReceipt() throws Exception {
		String YSId = request.getParameter("YSId");
		
		//取得订单信息
		getOrderDetail(YSId);	
	}
	

	/**
	 * 新增付款申请
	 */
	public void applyInsertAndReturn() throws Exception {

		String paymentid = insertApply(Constants.payment_020);//待审核

		//付款申请详情
		HashMap<String, String> map = getPaymentDetail(paymentid);
		if(!(map == null)){
			String contractId = map.get("contractIds");
			//供应商
			getContractDetail(contractId);				
		}

	}

	/**
	 * 付款审核:编辑初始化
	 * @throws Exception
	 */
	public void approvalUpdateInit() throws Exception {
		
		B_PaymentData reqData = reqModel.getPayment();
	
		String paymentid = reqData.getPaymentid();
			
		//付款申请详情
		HashMap<String, String> map = getPaymentDetail(paymentid);
		if(!(map == null)){
			String contractId = map.get("contractIds");
			//供应商
			getContractDetail(contractId);				
		}

		reqModel.setApprovalOption(util.getListOption(DicUtil.DIC_APPROVL, ""));
		reqModel.setInvoiceTypeOption(util.getListOption(DicUtil.DIC_INVOICETYPE, ""));

	}
	
	/**
	 * 付款审核
	 * @throws Exception
	 */
	public void approvalUpdateAndReturn() throws Exception {

		
		B_PaymentData reqData = reqModel.getPayment();
	
		String paymentid = reqData.getPaymentid();

		//付款单审核
		updatePayment(reqData);	
			
		//付款申请详情
		HashMap<String, String> map = getPaymentDetail(paymentid);
		if(!(map == null)){
			String contractId = map.get("contractIds");
			//供应商
			getContractDetail(contractId);				
		}

	}
	
	/**
	 * 付款审核:弃审
	 * @throws Exception
	 */
	public void approvalDelete() throws Exception {

		
		B_PaymentData reqData = reqModel.getPayment();
	
		//付款单审核
		updatePaymentForApprolval(reqData);	

	}
	

	/**
	 * 删除付款申请
	 * @throws Exception
	 */
	public void applyDelete() throws Exception {

		ts = new BaseTransaction();
		
		try {
			ts.begin();
			B_PaymentData reqData = reqModel.getPayment();
			String paymentId = reqData.getPaymentid();
		
			//删除付款信息
			deletePayment(paymentId);
			
			//删除付款明细（关联合同）
			deletePaymentDetail(paymentId);
			
			ts.commit();
		
		}catch(Exception e){
			ts.rollback();
			e.printStackTrace();
		}

	}
	public void finishInsertAndReturn() throws Exception {

		String paymentid = insertFinish();
		

		getPaymentHistoryList(paymentid);
		
		//付款申请详情
		HashMap<String, String> map = getPaymentDetail(paymentid);
		if(!(map == null)){
			String contractId = map.get("contractIds");
			//供应商
			getContractDetail(contractId);				
		}		

	}
	
	public void paymentView() throws Exception {

		String paymentid = request.getParameter("paymentId");

		//付款申请详情
		HashMap<String, String> map = getPaymentDetail(paymentid);
		if(!(map == null)){
			String contractId = map.get("contractIds");
			//供应商
			getContractDetail(contractId);				
		}	

	}	
		
	//新建付款申请
	private String insertApply(String paymentStatus){
		String paymentid = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			System.out.println("新建付款申请："+CalendarUtil.getSystemDate());
			B_PaymentData reqData = reqModel.getPayment();
			List<B_PaymentDetailData> reqDataList = reqModel.getPaymentList();
			List<B_PurchaseOrderData> contractList = reqModel.getContractList();

			//取得付款编号
			paymentid = reqData.getPaymentid();
			if(isNullOrEmpty(paymentid)){
				reqData = getPaymentRecordId(reqData);	
				paymentid = reqData.getPaymentid();//重新取值
			}	
			//付款单
			String status = reqData.getFinishstatus();
			if((isNullOrEmpty(status) || 
					(Constants.payment_060).equals(status))){
				reqData.setFinishstatus(paymentStatus);//待审核				
			}
			
			insertPayment(reqData);			

			//检查关联合同是否存在
			//String where = " paymentid='" + paymentid +"' AND deleteFlag='0' ";
			//if ( checkPaymentExsit(where) == null){

				//关联合同
				for(B_PaymentDetailData data:reqDataList ){				
					
					data.setPaymentid(paymentid);
					insertPaymentDetail(data);					
				}				
			//}
			
			//保存退税率
			for(B_PurchaseOrderData data:contractList ){				
				
				updateContractTaxRate(data);
			}
			
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
		
		return paymentid;
	}
		
	//付款完成
	private String insertFinish(){
		String paymentid = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			System.out.println("付款完成："+CalendarUtil.getSystemDate());
			
			B_PaymentData reqData = reqModel.getPayment();
			B_PaymentHistoryData history = reqModel.getHistory();
			paymentid = reqData.getPaymentid();

			
			float contract = stringToFloat(reqData.getTotalpayable());
			float payment = stringToFloat(history.getPaymentamount());

			if(payment <= 0)
				return paymentid;

			//新增付款记录
			insertPaymentHistory(history);
			
			if(payment == contract){
				reqData.setFinishstatus(Constants.payment_050);//完成
			}else{				
				//取得累计付款金额
				float paymentAmount = stringToFloat(getPaymentSumAmount(paymentid));
				
				if(paymentAmount == contract){
					reqData.setFinishstatus(Constants.payment_050);//完成
				}else{
					reqData.setFinishstatus(Constants.payment_040);//部分付款
				}
				
			}			
			//更新付款单状态
			updatePaymentFinishSts(reqData);
			
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
		
		return paymentid;
	}
	
	@SuppressWarnings("unchecked")
	private void insertPayment(
			B_PaymentData payment) throws Exception {
		
		B_PaymentData db = null;
		List<B_PaymentData> list = null;
		try {
			String where = " paymentId ='" + payment.getPaymentid() + "' AND deleteFlag='0'";
			list = new B_PaymentDao().Find(where);

		} catch (Exception e) {
			// nothing
		}		
		
		if(list == null || list.size() == 0){
			//插入新数据
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"paymentRequestInsert",userInfo);
			copyProperties(payment,commData);
			guid = BaseDAO.getGuId();
			payment.setRecordid(guid);
			payment.setApprovalstatus("010");//未审核
			payment.setApplicant(userInfo.getUserId());//默认为登陆者
			System.out.println("付款Insert前："+payment.getPaymentid()+"，时间："+CalendarUtil.getSystemDate());
			new B_PaymentDao().Create(payment);
			System.out.println("付款Insert后："+payment.getPaymentid()+"，时间："+CalendarUtil.getSystemDate());
		}else{
			//更新
			db = list.get(0);		
			copyProperties(db,payment);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"paymentRequestUpdate",userInfo);
			copyProperties(db,commData);
			db.setApplicant(userInfo.getUserId());//默认为登陆者

			System.out.println("付款Update前："+payment.getPaymentid()+"，时间："+CalendarUtil.getSystemDate());
			new B_PaymentDao().Store(db);
			System.out.println("付款Update后："+payment.getPaymentid()+"，时间："+CalendarUtil.getSystemDate());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void insertPaymentDetail(
			B_PaymentDetailData detail) throws Exception {

		B_PaymentDetailData db = null;
		String where = " paymentId='"+detail.getPaymentid() + 
				"' AND contractId='"+detail.getContractid()+
				"' AND deleteFlag='0' ";
		
		List<B_PaymentDetailData> list = new B_PaymentDetailDao().Find(where);

		if(list == null || list.size() <= 0 ){ 
			
			//插入新数据
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
			"paymentRequestInsert",userInfo);
			copyProperties(detail,commData);
			guid = BaseDAO.getGuId();
			detail.setRecordid(guid);

			System.out.println("付款明细Insert前："+detail.getPaymentid()+"，时间："+CalendarUtil.getSystemDate());
			new B_PaymentDetailDao().Create(detail);
			System.out.println("付款明细Insert后："+detail.getPaymentid()+"，时间："+CalendarUtil.getSystemDate());
		}else{
			//更新
			db = list.get(0);
			copyProperties(db,detail);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"paymentRequestUpdate",userInfo);
			copyProperties(db,commData);

			System.out.println("付款明细Update前："+detail.getPaymentid()+"，时间："+CalendarUtil.getSystemDate());
			new B_PaymentDetailDao().Store(db);
			System.out.println("付款明细Update后："+detail.getPaymentid()+"，时间："+CalendarUtil.getSystemDate());
		}		
	}
	
	private void updatePayment(
			B_PaymentData payment) throws Exception {
		
		B_PaymentData db = null;
		try {
			db = new B_PaymentDao(payment).beanData;

		} catch (Exception e) {
			// nothing
		}		
		
		if(db == null || db.equals(""))
			return;
		
		//更新
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"paymentRequestUpdate",userInfo);
		copyProperties(db,commData);
		
		db.setApprovalfeedback(replaceTextArea(payment.getApprovalfeedback()));
		db.setInvoicetype(payment.getInvoicetype());//发票类型
		db.setInvoicenumber(payment.getInvoicenumber());//发票编号
		
		String insertFlag = request.getParameter("insertFlag");
		if( ("insert").equals(insertFlag)){
			db.setApprovaluser(userInfo.getUserId());//默认为登陆者
			db.setApprovaldate(CalendarUtil.fmtYmdDate());
			db.setApprovalstatus(payment.getApprovalstatus());
			//审核结果:020同意;030不同意;010未审核
			if(("020").equals(payment.getApprovalstatus())){
				db.setFinishstatus(Constants.payment_030);//待付款
			}else{
				db.setFinishstatus(Constants.payment_060);//审核未通过			
			}
		}
		new B_PaymentDao().Store(db);
		
	}
	

	private void updatePaymentForApprolval(
			B_PaymentData payment) throws Exception {
		
		B_PaymentData db = null;
		try {
			db = new B_PaymentDao(payment).beanData;

		} catch (Exception e) {
			// nothing
		}		
		
		if(db == null || db.equals(""))
			return;
		
		//更新
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"paymentApprolvalUpdate",userInfo);
		copyProperties(db,commData);
		
		db.setApprovaluser("");//登陆者
		db.setApprovaldate("");
		db.setApprovalstatus("");//审核结果：未审核
		db.setApprovalfeedback("");
		db.setInvoicetype("");//发票类型
		db.setInvoicenumber("");//发票编号		
		db.setFinishstatus(Constants.payment_020);//付款状态：待审核

		new B_PaymentDao().Store(db);
		
	}
	
	private void updatePaymentFinishSts(
			B_PaymentData payment) throws Exception {
		
		B_PaymentData db = null;
		try {
			db = new B_PaymentDao(payment).beanData;

		} catch (Exception e) {
			// nothing
		}		
		
		if(db == null || db.equals(""))
			return;
		
		//更新
		copyProperties(db,payment);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"paymentRequestUpdate",userInfo);
		copyProperties(db,commData);
		
		db.setFinishdate(CalendarUtil.fmtYmdDate());		

		new B_PaymentDao().Store(db);
		
	}
	

	@SuppressWarnings({ "unchecked" })
	private void updateContractTaxRate(
			B_PurchaseOrderData contract) throws Exception {
		
		String where = " contractId ='" + contract.getContractid() + "' AND deleteFlag='0' ";
		List<B_PurchaseOrderData> list = null;
		try {
			list = new B_PurchaseOrderDao().Find(where);

		} catch (Exception e) {
			// nothing
		}		
		
		if(list.size()<= 0)
			return;
		
		//更新
		B_PurchaseOrderData db = list.get(0);
		copyProperties(db,contract);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"ContractTaxRateUpdate",userInfo);
		copyProperties(db,commData);		

		new B_PurchaseOrderDao().Store(db);
		
	}
	
	private void insertPaymentHistory(
			B_PaymentHistoryData payment) throws Exception {
		
		B_PaymentData db = null;
		try {
			db = new B_PaymentDao(payment).beanData;

		} catch (Exception e) {
			// nothing
		}		
		
		if(db == null || db.equals("")){
			//插入新数据
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"paymentRequestInsert",userInfo);
			copyProperties(payment,commData);
			guid = BaseDAO.getGuId();
			payment.setRecordid(guid);
			payment.setFinishuser(userInfo.getUserId());//默认为登陆者
			payment.setFinishdate(CalendarUtil.fmtYmdDate());
			
			new B_PaymentHistoryDao().Create(payment);
		}else{
			//更新
			copyProperties(db,payment);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"paymentRequestUpdate",userInfo);
			copyProperties(db,commData);
			db.setApplicant(userInfo.getUserId());//默认为登陆者
			
			new B_PaymentHistoryDao().Store(db);
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
	

	
	public B_PaymentData getPaymentRecordId(
			B_PaymentData data) throws Exception {
	
		String parentId = BusinessService.getshortYearcode()+
				BusinessConstants.SHORTNAME_FK;
		dataModel.setQueryName("getMAXPaymentId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		//查询出的流水号已经在最大值上 " 加一 "了
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");	
		
		String id = BusinessService.getPaymentId(
						parentId,
						code,
						false);	
		
		data.setPaymentid(id);
		data.setParentid(parentId);
		data.setSubid(code);			
		
		return data;
		
	}
	

	public void getOrderDetail(String YSId) throws Exception {

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("orderDetail",dataModel.getYsViewData());			
		}		
	}
	
	public void getContractDetail(String contractIds) throws Exception {

		dataModel.setQueryName("contractListForPaymenRequestById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractIds);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("supplier",dataModel.getYsViewData().get(0));
			model.addAttribute("contract",dataModel.getYsViewData());			
		}		
	}
	
	public HashMap<String, Object> getStockoutHistory(
			String YSId) throws Exception {
		
		dataModel.setQueryName("stockoutListById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	
	
	public HashMap<String, Object> getStockoutDetail() throws Exception {
		String stockOutId = request.getParameter("stockOutId");
		dataModel.setQueryName("stockoutdetail");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("stockOutId", stockOutId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	

	@SuppressWarnings("unchecked")
	public B_StockOutData checkStcokoutExsit(String where) throws Exception {
		
		B_StockOutData db = null ; 
		
		List<B_StockOutData> list = new B_StockOutDao().Find(where);
		
		if(list.size() > 0)
			db = list.get(0);
		
		return db;
		
	}
	
	@SuppressWarnings("unchecked")
	public B_PaymentDetailData checkPaymentExsit(String where) throws Exception {
		
		B_PaymentDetailData db = null ; 
		
		List<B_PaymentDetailData> list = new B_PaymentDetailDao().Find(where);
		
		if(list.size() > 0)
			db = list.get(0);
		
		return db;
		
	}
	
	
	public HashMap<String, Object> uploadPhotoAndReload(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) throws Exception {

		B_PaymentData payment = reqModel.getPayment();
		//B_PaymentHistoryData history = reqModel.getHistory();

		//判断是否有申请编号
		String paymentId = payment.getPaymentid();
		if(isNullOrEmpty(paymentId)){			
			paymentId = insertApply(Constants.payment_010);//待申请
		}
		modelMap.put("paymentId", paymentId);//返回到申请页面显示
		
		String supplierId = payment.getSupplierid();
		//String paymentId = history.getPaymentid();

		FilePath file = getPath(supplierId,paymentId);
		String savePath = file.getSave();						
		String viewPath = file.getView();
		String webPath = file.getWeb();

		System.out.println("paymentId:"+paymentId);
		System.out.println("savePath:"+savePath);
		System.out.println("viewPath:"+savePath);
		String photoName  = supplierId+ "-" + paymentId + "-" + CalendarUtil.timeStempDate(); 
		
		HashMap<String, Object> jsonObj = 
				uploadPhoto(headPhotoFile,photoName,viewPath,savePath,webPath);		

		System.out.println("message:"+jsonObj.get("message"));
		ArrayList<String> list = getFiles(savePath,webPath);
		modelMap.put(fileList, list);
		modelMap.put(fileCount, list.size());
	
		return modelMap;
	}
	
	public HashMap<String, Object> deletePhotoAndReload(
			String folderName,String fileList,String fileCount) throws Exception {

		String path = request.getParameter("path");
		B_PaymentData payment = reqModel.getPayment();
		//B_PaymentHistoryData history = reqModel.getHistory();
		String supplierId = payment.getSupplierid();
		String paymentId = payment.getPaymentid();

		deletePhoto(path);//删除图片

		getPhoto(supplierId,paymentId,folderName,fileList,fileCount);
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getProductPhoto() throws Exception {
		
		String supplierId = request.getParameter("supplierId");
		String paymentId = request.getParameter("paymentId");

		getPhoto(supplierId,paymentId,"product","productFileList","productFileCount");
	
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
				getRealPath(BusinessConstants.PATH_PAYMENTFILE)
				+"/"+key1+"/"+key2
				);	
		filePath.setView(
				session.getServletContext().
				getRealPath(BusinessConstants.PATH_PAYMENTVIEW)
				+"/"+key1+"/"+key2
				);	

		filePath.setWeb(BusinessConstants.PATH_PAYMENTVIEW
				+key1+"/"+key2+"/"
				);	
		
		return filePath;
	}
	
	@SuppressWarnings({ "unchecked" })
	private void deletePayment(String paymentId) throws Exception {
		
		String where = " paymentId ='" + paymentId + "' AND deleteFlag='0' ";
		List<B_PaymentData> list = null;
		try {
			list = new B_PaymentDao().Find(where);

		} catch (Exception e) {
			// nothing
		}		
		if(list.size()<= 0)
			return;
		
		//更新
		B_PaymentData db = list.get(0);
		commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
				"PaymentDelete",userInfo);
		copyProperties(db,commData);		

		new B_PaymentDao().Store(db);
		
	}
	
	@SuppressWarnings({ "unchecked" })
	private void deletePaymentDetail(String paymentId) throws Exception {
		
		String where = " paymentId ='" + paymentId + "' AND deleteFlag='0' ";
		List<B_PaymentDetailData> list = null;
		try {
			list = new B_PaymentDetailDao().Find(where);

		} catch (Exception e) {
			// nothing
		}		
		if(list.size()<= 0)
			return;
		
		//更新
		for(B_PaymentDetailData db:list){
			
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"PaymentDetailDelete",userInfo);
			copyProperties(db,commData);		

			new B_PaymentDetailDao().Store(db);
		}
		
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
}
