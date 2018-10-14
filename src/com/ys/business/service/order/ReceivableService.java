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
import com.ys.business.action.model.order.ReceivableModel;
import com.ys.business.db.dao.B_PaymentDao;
import com.ys.business.db.dao.B_PaymentDetailDao;
import com.ys.business.db.dao.B_PaymentHistoryDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_ReceivableDao;
import com.ys.business.db.dao.B_ReceivableDetailDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.data.B_PaymentData;
import com.ys.business.db.data.B_PaymentDetailData;
import com.ys.business.db.data.B_PaymentHistoryData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_ReceivableData;
import com.ys.business.db.data.B_ReceivableDetailData;
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
		
		String status = request.getParameter("status");
		if(("070").equals(status)){
			userDefinedSearchCase.put("reserveDate", CalendarUtil.fmtYmdDate());
			having = " REPLACE(actualAndBankCnt,',','')+0 <= 0 ";
		}else if(("010").equals(status)){
			//未收款
			having = " REPLACE(actualAndBankCnt,',','')+0 <= 0 ";
		}else if(("020").equals(status)){
			//部分收款
			having = " REPLACE(actualAndBankCnt,',','')+0 > 0 AND REPLACE(actualAndBankCnt,',','')+0 < REPLACE(format(orderPrice,2),',','')+0";
		}else if(("030").equals(status)){
			//已收款
			having = "REPLACE(actualAndBankCnt,',','')+0 > 0 AND REPLACE(actualAndBankCnt,',','')+0 >= REPLACE(format(orderPrice,2),',','')+0 ";
		}
				
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", having);
		System.out.println("收款查询："+sql);
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
	
	public void getRecivableFromOrder(String YSId) throws Exception{

		dataModel.setQueryName("orderListForReceivabel");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		String having = "1=1";
		sql = sql.replace("#",having);
		System.out.println("收款明细查询："+sql);
		baseQuery.getYsFullData(sql,having);

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("orderDetail",dataModel.getYsViewData());			
		}	
	}
	
	
	public void receivableAddInit() throws Exception{
		String YSId = request.getParameter("YSId");

		getRecivableFromOrder(YSId);
	}
	
	public void receivableInsertAndView() throws Exception{

		String YSId = request.getParameter("YSId");
		
		receivableInsert(YSId);
		
		getRecivableFromOrder(YSId);
		
	}
	
	public HashMap<String, Object> getReceivableDetail() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String YSId = request.getParameter("YSId");
		dataModel.setQueryName("receivableDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
	
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			modelMap.put("data",dataModel.getYsViewData());			
		}
		
		return modelMap;
	}
	
	public void receivableDetailViewInit(String YSId) throws Exception{


		getRecivableFromOrder(YSId);
	}
	
	

	/**
	 * 删除付款申请
	 * @throws Exception
	 */
	public void applyDelete() throws Exception {

		ts = new BaseTransaction();
		
		try {
			ts.begin();
			B_PaymentData reqData = null;//reqModel.getPayment();
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
	
		
	/**
	 * 
	 * @param paymentStatus
	 * @return
	 */
	private void receivableInsert(String YSId){
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

			//收款明细
			reqDataDetail.setReceivableid(reqData.getReceivableid());
			reqDataDetail = getReceivableDetailRecordId(reqDataDetail);
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
					"paymentRequestInsert",userInfo);
			copyProperties(payment,commData);
			guid = BaseDAO.getGuId();
			payment.setRecordid(guid);
			
			new B_ReceivableDao().Create(payment);
		}else{
			//更新
			db = list.get(0);		
			copyProperties(db,payment);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"paymentRequestUpdate",userInfo);
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
			"paymentRequestInsert",userInfo);
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
					"paymentRequestUpdate",userInfo);
			copyProperties(db,commData);
			db.setPayee(userInfo.getUserId());
			
			new B_ReceivableDetailDao().Store(db);
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
				"paymentApprolvalDelete",userInfo);
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
		
		dataModel.setQueryName("getMAXReceivableId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
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
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("orderDetail",dataModel.getYsViewData());			
		}		
	}
	

	
	public HashMap<String, Object> uploadPhotoAndReload(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) throws Exception {

		B_PaymentData payment = null;//reqModel.getPayment();
		//B_PaymentHistoryData history = reqModel.getHistory();

		//判断是否有申请编号
		String paymentId = payment.getPaymentid();
		if(isNullOrEmpty(paymentId)){			
			paymentId = null;//insertApply(Constants.payment_010);//待申请
		}
		modelMap.put("paymentId", paymentId);//返回到申请页面显示
		
		String supplierId = payment.getSupplierid();
		//String paymentId = history.getPaymentid();

		FilePath file = getPath(supplierId,paymentId);
		String savePath = file.getSave();						
		String viewPath = file.getView();
		String webPath = file.getWeb();


		String photoName  = supplierId+ "-" + paymentId + "-" + CalendarUtil.timeStempDate(); 
		
		uploadPhoto(headPhotoFile,photoName,viewPath,savePath,webPath);		

		ArrayList<String> list = getFiles(savePath,webPath);
		modelMap.put(fileList, list);
		modelMap.put(fileCount, list.size());
	
		return modelMap;
	}
	
	public HashMap<String, Object> deletePhotoAndReload(
			String folderName,String fileList,String fileCount) throws Exception {

		String path = request.getParameter("path");
		B_PaymentData payment = null;//reqModel.getPayment();
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
