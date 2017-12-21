package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.ys.business.action.model.order.PaymentModel;
import com.ys.business.action.model.order.StockOutModel;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_PaymentDao;
import com.ys.business.db.dao.B_PaymentDetailDao;
import com.ys.business.db.dao.B_RequisitionDao;
import com.ys.business.db.dao.B_RequisitionDetailDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.dao.B_StockOutDetailDao;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_PaymentData;
import com.ys.business.db.data.B_PaymentDetailData;
import com.ys.business.db.data.B_PurchaseStockInData;
import com.ys.business.db.data.B_RequisitionData;
import com.ys.business.db.data.B_RequisitionDetailData;
import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.B_StockOutDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

import antlr.collections.impl.Vector;

import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class PaymentService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
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
			HttpSession session,
			PaymentModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_StockOutDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
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
	
	public HashMap<String, Object> doSearch( String data) throws Exception {

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
	
	public void addInitOrView() throws Exception {

		String contractIds = request.getParameter("contractIds");

		//合同详情
		getContractDetail(contractIds);
		
		model.addAttribute("contractIds",contractIds);
	
	}
	

	public void edit() throws Exception {
		String stockoutId = request.getParameter("stockoutId");
	
		String where = " stockoutId='"+stockoutId+"' ";
		B_StockOutData data = checkStcokoutExsit(where);
		//reqModel.setStockout(data);	
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

	public void printReceipt() throws Exception {
		String YSId = request.getParameter("YSId");
		String stockOutId = request.getParameter("stockOutId");
		
		//取得订单信息
		getOrderDetail(YSId);
		//getArrivaRecord(receiptId);//入库明细	
	}

	public void printProductReceipt() throws Exception {
		String YSId = request.getParameter("YSId");
		
		//取得订单信息
		getOrderDetail(YSId);	
	}
	
	
	public HashMap<String, Object> getProductStockInDetail() {

		String YSId = request.getParameter("YSId");
		String materialId = request.getParameter("materialId");
		return getOrderAndStockDetail(YSId,materialId);
	}
	
	public void applyInsertAndReturn() throws Exception {

		String paymentid = applyInsert();

		//付款申请详情
		String contractId = getPaymentDetail(paymentid);
		
		//供应商
		getContractDetail(contractId);	

	}

	public void paymentView() throws Exception {

		String paymentid = request.getParameter("paymentId");

		//付款申请详情
		String contractId = getPaymentDetail(paymentid);
		
		//供应商
		getContractDetail(contractId);		

	}
	
	public void updateAndReturn() throws Exception {

		String YSId = updateStorage();

		getOrderDetail(YSId);

	}

	
	private String updateStorage(){
		String YSId = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			/*
			B_StockOutData reqData = reqModel.getStockout();
			List<B_StockOutDetailData> reqDataList = reqModel.getStockList();

			//取得出库单编号
			YSId= reqData.getYsid();		
			String stockoutId = reqData.getStockoutid();
	
			//出库记录
			insertStockOut(reqData);
			
			//删除既存数据
			deleteStockoutDetail(stockoutId);
			
			for(B_StockOutDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				
				if(quantity <= 0)
					continue;
				
				data.setStockoutid(stockoutId);
				insertStockOutDetail(data);								
				
				//更新库存
				updateMaterial(data.getMaterialid(),quantity);//更新库存
			
			}
			*/
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
	
	//新建付款申请
	private String applyInsert(){
		String paymentid = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_PaymentData reqData = reqModel.getPayment();
			List<B_PaymentDetailData> reqDataList = reqModel.getPaymentList();

			//取得付款编号
			reqData = getPaymentRecordId(reqData);			
			paymentid = reqData.getPaymentid();
	
			//付款单
			insertPayment(reqData);			

			//关联合同
			for(B_PaymentDetailData data:reqDataList ){				
				
				data.setPaymentid(paymentid);
				insertPaymentDetail(data);
			
			}			
			
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
		
	private void insertPayment(
			B_PaymentData payment) throws Exception {
		
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
			payment.setApplicant(userInfo.getUserId());//默认为登陆者
			payment.setFinishstatus(Constants.payment_020);//待审核
			
			new B_PaymentDao().Create(payment);
		}else{
			//更新
			copyProperties(db,payment);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"paymentRequestUpdate",userInfo);
			copyProperties(db,commData);
			db.setApplicant(userInfo.getUserId());//默认为登陆者
			
			new B_PaymentDao().Store(db);
		}
		
	}
	
	private void insertPaymentDetail(
			B_PaymentDetailData detail) throws Exception {

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"paymentRequestInsert",userInfo);
		copyProperties(detail,commData);
		guid = BaseDAO.getGuId();
		detail.setRecordid(guid);
				
		new B_PaymentDetailDao().Create(detail);
	}
	

	
	public String  getPaymentDetail(String paymentid) throws Exception{

		String contractIds = "";
		
		dataModel.setQueryName("paymentDetailById");
		userDefinedSearchCase.put("paymentId", paymentid);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0){
			model.addAttribute("payment",dataModel.getYsViewData().get(0));
			contractIds = dataModel.getYsViewData().get(0).get("contractIds");
			modelMap.put("data", dataModel.getYsViewData());
		}	
	
		return contractIds;
	}
	
	private HashMap<String, Object> getOrderAndStockDetail(
			String YSId,String materialId){

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		try {
			dataModel.setQueryName("getPurchaseStockInById");
			userDefinedSearchCase.put("YSId", YSId);
			userDefinedSearchCase.put("materialId", materialId);
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsFullData();

			modelMap.put("data", dataModel.getYsViewData());
			model.addAttribute("head",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());		
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return modelMap;
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

		dataModel.setQueryName("contractListForPaymenRequest");		
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
	
	public HashMap<String, Object> uploadPhotoAndReload(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) throws Exception {
/*
		B_StockOutData reqDt = reqModel.getStockout();
		String YSId = reqDt.getYsid();

		String viewPath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_STOCKOUTVIEW)+"/"+YSId;	

		String savePath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_STOCKOUTFILE)+"/"+YSId;	
						
		String webPath = BusinessConstants.PATH_STOCKOUTVIEW +YSId;
		
		String photoName  = YSId  + "-" + CalendarUtil.timeStempDate(); 
		
		uploadPhoto(headPhotoFile,photoName,viewPath,savePath,webPath);		

		ArrayList<String> list = getFiles(savePath,webPath);
		modelMap.put(fileList, list);
		modelMap.put(fileCount, list.size());
	*/
		return modelMap;
	}
	
	public HashMap<String, Object> deletePhotoAndReload(
			String folderName,String fileList,String fileCount) throws Exception {
/*
		String path = request.getParameter("path");
		B_StockOutData reqDt = reqModel.getStockout();
		String YSId = reqDt.getYsid();		
		String savePath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_STOCKOUTFILE)+"/"+YSId;							
		String webPath = BusinessConstants.PATH_STOCKOUTVIEW +YSId;

		deletePhoto(path);//删除图片
		
		ArrayList<String> list = getFiles(savePath,webPath);//重新获取图片
		
		modelMap.put(fileList, list);
		modelMap.put(fileCount, list.size());
		*/
		return modelMap;
	}
	
	
	public HashMap<String, Object> getProductPhoto() throws Exception {
		
		String YSId = request.getParameter("YSId");

		String savePath = session.getServletContext().
				getRealPath(BusinessConstants.PATH_STOCKOUTFILE)+"/"+YSId;							
		String webPath = BusinessConstants.PATH_STOCKOUTVIEW +YSId;
		
		ArrayList<String> list = getFiles(savePath,webPath);//获取图片

		modelMap.put("productFileList", list);
		modelMap.put("productFileCount", list.size());
		
		return modelMap;
	}
	

}
