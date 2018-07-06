package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.ys.business.action.model.common.FilePath;
import com.ys.business.action.model.order.RequisitionModel;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_PurchaseOrderdDeductDao;
import com.ys.business.db.dao.B_RequisitionDao;
import com.ys.business.db.dao.B_RequisitionDetailDao;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_PurchaseOrderdDeductData;
import com.ys.business.db.data.B_RequisitionData;
import com.ys.business.db.data.B_RequisitionDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class RequisitionService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_RequisitionDao dao;
	private B_RequisitionDetailDao detailDao;
	private RequisitionModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public RequisitionService(){
		
	}

	public RequisitionService(Model model,
			HttpServletRequest request,
			HttpSession session,
			RequisitionModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_RequisitionDao();
		this.detailDao = new B_RequisitionDetailDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
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

		String[] keyArr = getSearchKey(Constants.FORM_REQUISITION,data,session);
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

		dataModel.setQueryName("getOrderListForRequisition");	
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		
		String requisitionSts = request.getParameter("requisitionSts");
		if(notEmpty(key1) || notEmpty(key2))
			requisitionSts = "";//有查询条件,不再限定其状态
		String having = "1=1";
		if(("010").equals(requisitionSts)){
			//待申请
			having = " requisitionQty=0 ";
		}else if(("030").equals(requisitionSts)){
			//已出库
			having = " REPLACE(requisitionQty,',','')+0 = REPLACE(manufactureQty,',','')+0 ";
		}else if(("020").equals(requisitionSts)){
			//出库中
			having = "requisitionQty+0 > 0 AND REPLACE(requisitionQty,',','')+0 < REPLACE(manufactureQty,',','')+0 ";
		}else if(("040").equals(requisitionSts)){
			//成品已入库，但未领料,包括虚拟入库
			having = "bstockinQty+0 > 0 AND REPLACE(requisitionQty,',','')+0 < REPLACE(manufactureQty,',','')+0 ";
		}
		sql = sql.replace("#", having);
		System.out.println("装配领料申请SQL："+sql);
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
	
	public HashMap<String, Object> doVirtualSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		String[] keyArr = getSearchKey(Constants.FORM_REQUISITIONVIRTUAL,data,session);
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

		dataModel.setQueryName("getOrderListForRequisitionVirtual");	
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		
		String requisitionSts = request.getParameter("requisitionSts");
		if(notEmpty(key1) || notEmpty(key2))
			requisitionSts = "";//有查询条件,不再限定其状态
		String having = "1=1";
		if(("010").equals(requisitionSts)){
			//待出库
			having = " requisitionQty=0 ";
		}else if(("030").equals(requisitionSts)){
			//已出库
			having = " requisitionQty=manufactureQty ";
		}else if(("020").equals(requisitionSts)){
			//出库中
			having = " requisitionQty> 0 AND requisitionQty+0 < manufactureQty+0 ";
		}
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
	
	public HashMap<String, Object> stockoutReturnSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		String[] keyArr = getSearchKey(Constants.FORM_STOCKOUTRETURN,data,session);
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

		dataModel.setQueryName("getRequisitionListForExcess");	
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		
		//String requisitionSts = request.getParameter("requisitionSts");
		
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
	
	
	public HashMap<String, Object> doExcessSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		String[] keyArr = getSearchKey(Constants.FORM_REQUISITIOEXCESS,data,session);
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

		dataModel.setQueryName("getRequisitionListForExcess");	
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		
		//String requisitionSts = request.getParameter("requisitionSts");
		
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
	
	
	public HashMap<String, Object> doMaterialRequisitionSearch(
			String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		String[] keyArr = getSearchKey(Constants.FORM_REQUISITION_M,data,session);
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

		dataModel.setQueryName("getRequisitionAndStockout");	
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2))
				userDefinedSearchCase.put("requisitionSts", "");//有查询条件,不再限定其状态
		
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
	

	public void addInit() throws Exception {

		String YSId = request.getParameter("YSId");
		String requisitionId = request.getParameter("requisitionId");
		if(YSId== null || ("").equals(YSId))
			return;
		
		//订单详情
		getOrderDetail(YSId);
		model.addAttribute("requisitionId",requisitionId);
	
	}

	public void addPeiInit() throws Exception {

		String YSId = request.getParameter("YSId");
		String requisitionId = request.getParameter("requisitionId");
		if(YSId== null || ("").equals(YSId))
			return;
		
		//订单详情
		getOrderDetail("",YSId);
		model.addAttribute("requisitionId",requisitionId);
	
	}

	public void updateInit() throws Exception {

		String YSId = request.getParameter("YSId");
		//String requisitionId = request.getParameter("requisitionId");
		
		//订单详情
		getOrderDetail(YSId);
		//领料单
		getRequisitionDetail();
	
	}
	
	public HashMap<String, Object> showDetail() throws Exception {

		String YSId = request.getParameter("YSId");
		String orderType = request.getParameter("orderType");
		if(YSId == null || ("").equals(YSId))
			return null;
		if(("010").equals(orderType)){
			//常规订单			
			return getPurchasePlan(YSId);//物料需求表
		}else{
			//配件订单
			String[] list = YSId.split("-");
			String peiYsid = list[0]+"P";
			return getPartsOrderDetail(peiYsid);//装配品信息
		}
	}
	

	public void excessSearchByYsid() throws Exception {

		String YSId = request.getParameter("YSId");
		if(YSId == null || ("").equals(YSId))
			return ;
				
		getPurchasePlanForExcess(YSId);//物料需求表
		
	}
	
	public void updateAndView() throws Exception {

		String YSId = update();

		//订单详情
		getOrderDetail(YSId);
	}
	

	/**
	 * 超领物料查看
	 * @throws Exception
	 */
	public void showExcessDetail() throws Exception {

		//String YSId = request.getParameter("YSId");
		String requisitionId = request.getParameter("requisitionId");

		//订单详情
		//getOrderDetail(YSId);
		
		getExcessDetail(requisitionId);
	}
	
	/**
	 * 超领物料删除
	 * @throws Exception
	 */
	public void deleteExcessAndReturn() throws Exception {

		
		ts = new BaseTransaction();

		try {
			ts.begin();
			
			String YSId = request.getParameter("YSId");
			String requisitionId = request.getParameter("requisitionId");
			
			deleteRequisition(requisitionId);
			
			deleteRequisitionDetail(requisitionId);
		
			deleteContractDeDetail(YSId);
		
			ts.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}

	}
	

	/**
	 * 超领物料保存
	 * @throws Exception
	 */
	public void updateExcessAndView() throws Exception {

		String requisitionId = updateExcess();

		//订单详情

		getExcessDetail(requisitionId);
	}

	/**
	 * 领料退还删除
	 * @throws Exception
	 */
	public void stockoutReturnDelete() throws Exception {

		ts = new BaseTransaction();

		try {
			ts.begin();
			
			String requisitionId = request.getParameter("requisitionId");
			
			deleteRequisition(requisitionId);
			
			deleteRequisitionDetail(requisitionId);
		
		
			ts.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			ts.rollback();
		}
		
	}
	

	/**
	 * 领料退还保存（负数领料）
	 * @throws Exception
	 */
	public void stockoutReturnInsertAndView() throws Exception {

		B_RequisitionData reqData = insertStockOutReturn(
				Constants.REQUISITION_NORMAL,//正常领料
				Constants.STOCKOUT_2	//待出库
				);

		//订单详情
		getExcessDetail(reqData.getRequisitionid());
	}
	
	/**
	 * 超领物料保存
	 * @throws Exception
	 */
	public void insertExcessAndView() throws Exception {

		B_RequisitionData reqData = insertExcess(
				Constants.REQUISITION_NORMAL,//正常领料
				Constants.STOCKOUT_2	//待出库
				);

		//订单详情
		getExcessDetail(reqData.getRequisitionid());
	}
	
	/**
	 * 正常领料
	 * @throws Exception
	 */
	public void insertAndView() throws Exception {

		B_RequisitionData reqData = insert(
				Constants.REQUISITION_NORMAL,//正常领料
				Constants.STOCKOUT_2	//待出库
				);

		//订单详情
		getOrderDetail(reqData.getYsid());
	}
	
	public void virtualInsertAndView() throws Exception {

		B_RequisitionData reqData = insert(
				Constants.REQUISITION_VIRTUAL,//虚拟领料
				Constants.STOCKOUT_3	//已出库
				);

		//订单详情
		getOrderDetail(reqData.getYsid());
	}
	
	private String updateExcess(){
		
		String requisitionid = "";
		ts = new BaseTransaction();

		try {
			ts.begin();
			
			B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
			List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();

			
			//领料单更新处理
			requisitionid = reqData.getRequisitionid();//新的单号
			reqData.setOriginalrequisitionid(requisitionid);
			updateRequisition(reqData);

			//旧的明细删除处理
			deleteRequisitionDetail(requisitionid);
			
			//旧的合同扣款删除处理
			String YSId = reqData.getYsid();
			deleteContractDeDetail(YSId);
			
			//新的领料单明细						
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				
				if(quantity <= 0)
					continue;
				
				data.setRequisitionid(requisitionid);
				insertRequisitionDetail(data);
				
				//生成扣款记录
				B_PurchaseOrderdDeductData deduct = new B_PurchaseOrderdDeductData();
				deduct = getContractUnpaidById(YSId,data.getMaterialid());
				deduct.setQuantity(data.getQuantity());
				deduct.setYsid(YSId);
				
				insertPurchaseOrderdDeduct(deduct);
			}
			ts.commit();			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		
		return requisitionid;
	}
	

	private String update(){
		
		String requisitionid = "";
		ts = new BaseTransaction();

		try {
			ts.begin();
			
			B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
			List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();

			
			//领料单更新处理
			requisitionid = reqData.getRequisitionid();//新的单号
			reqData.setOriginalrequisitionid(requisitionid);
			updateRequisition(reqData);

			//旧的明细删除处理
			deleteRequisitionDetail(requisitionid);
			
			//新的领料单明细						
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				
				if(quantity <= 0)
					continue;
				
				data.setRequisitionid(requisitionid);
				insertRequisitionDetail(data);
			}
			ts.commit();			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		
		return requisitionid;
	}
	

	private B_RequisitionData insert(String virtualClass,String stockoutType){

		B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
		List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();
		
		ts = new BaseTransaction();

		try {
			ts.begin();

			//取得领料单编号
			String YSId = reqData.getYsid();
			reqData = getRequisitionId(reqData);
			String requisitionid = reqData.getRequisitionid();
			
			//新增领料申请
			reqData.setVirtualclass(virtualClass);
			reqData.setRequisitiontype(Constants.REQUISITION_PARTS);//装配领料
			reqData.setRequisitionsts(stockoutType);//
			insertRequisition(reqData);
						
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				
				if(quantity == 0)
					continue;
				
				data.setRequisitionid(requisitionid);
				insertRequisitionDetail(data);
				
				//虚拟出库，直接更新库存
				if((Constants.REQUISITION_VIRTUAL).equals(virtualClass)){
					
					updateMaterial(data.getMaterialid(),quantity);//更新库存
				}
			}
			
			if((Constants.REQUISITION_NORMAL).equals(virtualClass)){
				
				updateOrderDetail(YSId);//更新订单状态:待交货
			}
			
			ts.commit();			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		
		return reqData;
	}
	

	private B_RequisitionData insertStockOutReturn(
			String virtualClass,String stockoutType){

		B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
		List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();
		
		ts = new BaseTransaction();

		try {
			ts.begin();

			//取得领料单编号
			reqData = getRequisitionId(reqData);
			String requisitionid = reqData.getRequisitionid();
			
			//新增领料申请
			reqData.setVirtualclass(virtualClass);
			reqData.setRequisitiontype(Constants.REQUISITION_PARTS);//装配领料
			reqData.setRequisitionsts(stockoutType);//
			reqData.setExcesstype(Constants.REQUISITION_MINUS);//退还（负数领料）
			insertRequisition(reqData);
						
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());

				if(quantity == 0)
					continue;
				
				quantity = quantity * (-1);//退换处理，所以是负数
				
				data.setRequisitionid(requisitionid);
				data.setQuantity(floatToString(quantity));
				insertRequisitionDetail(data);
				
			}
			
			ts.commit();			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		
		return reqData;
	}
	
	private B_PurchaseOrderdDeductData getContractUnpaidById(
			String YSId,
			String materialId) throws Exception{
		
		B_PurchaseOrderdDeductData detail = new B_PurchaseOrderdDeductData();
		//确认当前订单是否有合同	
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
		dataModel.setQueryName("getContractByYSId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			detail.setContractid(dataModel.getYsViewData().get(0).get("contractId"));
			detail.setSupplierid(dataModel.getYsViewData().get(0).get("supplierId"));
			detail.setMaterialid(materialId);
			
			return detail;
		}
		
		//取得该物料最新的未付款的合同
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		dataModel.setQueryName("getMAXContractByMaterialId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", materialId);
		System.out.println("最新的未付款的合同查询："+sql);
		baseQuery.getYsFullData(sql);

		if(dataModel.getRecordCount() >0){
			detail.setContractid(dataModel.getYsViewData().get(0).get("contractId"));
			detail.setSupplierid(dataModel.getYsViewData().get(0).get("supplierId"));
			detail.setMaterialid(materialId);
		}
		
		return detail;
		
	}
	
	private B_RequisitionData insertExcess(String virtualClass,String stockoutType){

		B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
		List<B_RequisitionDetailData> reqDataList = reqModel.getRequisitionList();
		
		ts = new BaseTransaction();

		try {
			ts.begin();

			//取得领料单编号
			String YSId = reqData.getYsid();
			reqData = getRequisitionId(reqData);
			String requisitionid = reqData.getRequisitionid();
			
			//新增领料申请
			reqData.setVirtualclass(virtualClass);
			reqData.setRequisitiontype(Constants.REQUISITION_PARTS);//装配领料
			reqData.setRequisitionsts(stockoutType);//
			insertRequisition(reqData);
						
			for(B_RequisitionDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				
				if(quantity <= 0)
					continue;
				
				data.setRequisitionid(requisitionid);
				insertRequisitionDetail(data);
				
				//生成扣款记录
				B_PurchaseOrderdDeductData deduct = new B_PurchaseOrderdDeductData();
				deduct = getContractUnpaidById(YSId,data.getMaterialid());
				deduct.setQuantity(data.getQuantity());
				deduct.setYsid(YSId);
				
				insertPurchaseOrderdDeduct(deduct);
			}
			
			
			ts.commit();			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		
		return reqData;
	}
	
	//库存处理:减少“待出库”
	@SuppressWarnings("unchecked")
	private void updateMaterial(
			String materialId,
			float reqQuantity) throws Exception{
	
		B_MaterialData data = new B_MaterialData();
		B_MaterialDao dao = new B_MaterialDao();
		
		String where = "materialId ='"+ materialId + "' AND deleteFlag='0' ";
		
		List<B_MaterialData> list = 
				(List<B_MaterialData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		//当前库存数量
		float iQuantity = stringToFloat(data.getQuantityonhand());	
		//待入库
		float waitstockin = stringToFloat(data.getWaitstockin());
		//待出库
		float istockout = stringToFloat(data.getWaitstockout());		
		float iNewStockOut = istockout - reqQuantity;
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float availabeltopromise = iQuantity + waitstockin - iNewStockOut;
		
		data.setWaitstockout(String.valueOf(iNewStockOut));
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"虚拟出库Update",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}
	
	private void insertPurchaseOrderdDeduct(
			B_PurchaseOrderdDeductData deduct) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"超领退货Insert",userInfo);
		copyProperties(deduct,commData);

		guid = BaseDAO.getGuId();
		deduct.setRecordid(guid);
		
		new B_PurchaseOrderdDeductDao().Create(deduct);
	}
	

	private void insertRequisition(
			B_RequisitionData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RequisitionInsert",userInfo);
		copyProperties(stock,commData);

		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		stock.setRequisitionuserid(userInfo.getUserId());//默认为登陆者
		stock.setRequisitiondate(CalendarUtil.fmtYmdDate());
		stock.setRemarks(replaceTextArea(stock.getRemarks()));//换行符转换
		
		dao.Create(stock);
	}
	
	
	private void insertRequisitionDetail(
			B_RequisitionDetailData stock) throws Exception {
		
		//插入新数据
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"RequisitionInsert",userInfo);
		copyProperties(stock,commData);

		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
		
		detailDao.Create(stock);
	}
	
	private void updateRequisitionDetail(
			B_RequisitionDetailData stock) throws Exception {
		
		//update
		B_RequisitionDetailData db = new B_RequisitionDetailDao(stock).beanData;
		
		if(db == null || ("").equals(db)){
			//
			insertRequisitionDetail(stock);
		}else{
			copyProperties(db,stock);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"materialRequisitionUpdate",userInfo);
			copyProperties(db,commData);
			
			detailDao.Store(db);
		}
	}
		
	
	@SuppressWarnings("unchecked")
	private void updateOrderDetail(
			String ysid) throws Exception{
		String where = "YSId = '" + ysid  +"' AND deleteFlag = '0' ";
		List<B_OrderDetailData> list  = new B_OrderDetailDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;	
		
		//更新DB
		B_OrderDetailData data = list.get(0);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"领料申请Update",userInfo);
		copyProperties(data,commData);
		data.setStatus(Constants.ORDER_STS_5);//待交货	
		
		new B_OrderDetailDao().Store(data);
	}
	
	
	private void updateRequisition(
			B_RequisitionData data) throws Exception{
		
		B_RequisitionData db = new B_RequisitionDao(data).beanData;

		copyProperties(db,data);
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"RequisitionUpdate",userInfo);
		copyProperties(db,commData);
		db.setRequisitionuserid(userInfo.getUserId());//默认为登陆者
		db.setRemarks(replaceTextArea(db.getRemarks()));//换行符转换	
		
		new B_RequisitionDao().Store(db);
	}

	private void deleteRequisition(
			String requisitionId) throws Exception{
		
		//
		String where = "requisitionId = '" + requisitionId  +"' AND deleteFlag = '0' ";
		try{
			new B_RequisitionDao().RemoveByWhere(where);
			
		}catch(Exception e){
			//
		}
	}

	private void deleteRequisitionDetail(
			String requisitionId) throws Exception{
		
		//
		String where = "requisitionId = '" + requisitionId  +"' AND deleteFlag = '0' ";
		try{
			new B_RequisitionDetailDao().RemoveByWhere(where);
			
		}catch(Exception e){
			//
		}
	}

	private void deleteContractDeDetail(
			String YSId) throws Exception{
		
		//
		String where = "YSId = '" + YSId  +"' AND deleteFlag = '0' ";
		try{
			new B_PurchaseOrderdDeductDao().RemoveByWhere(where);
			
		}catch(Exception e){
			//
		}
	}

		
	@SuppressWarnings("unchecked")
	public void doDelete(String recordId) throws Exception{
																	
		try {
			
			ts = new BaseTransaction();					
			
			String recordid = request.getParameter("recordId");
			B_RequisitionData req = new B_RequisitionData();
			req.setRecordid(recordid);
			req = new B_RequisitionDao(req).beanData;
			if(req ==null || ("").equals(req))
				return;
			
			ts.begin();	
			commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
					"RequisitionDelete",userInfo);
			copyProperties(req,commData);
			new B_RequisitionDao().Store(req);
			
			String requisitionId = req.getRequisitionid();
			String astr_Where = " requisitionId='" +requisitionId+"' AND deleteFlag='0' ";
			List<B_RequisitionDetailData> list = new B_RequisitionDetailDao().Find(astr_Where);					
			
			
			for(B_RequisitionDetailData dt:list){
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"RequisitionDelete",userInfo);
				copyProperties(dt,commData);
				new B_RequisitionDetailDao().Store(dt);				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
	}
	
	
	
	
	public B_RequisitionData getRequisitionId(
			B_RequisitionData data ) throws Exception {

		String parentId = BusinessService.getshortYearcode()+
				BusinessConstants.SHORTNAME_LL;
		dataModel.setQueryName("getMAXRequisitionId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("parentId", parentId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();
		//sql里已经是MAX+1
		String subid = dataModel.getYsViewData().get(0).get("MaxSubId");
		String id =  BusinessService.getRequisitionId(parentId,subid);
		data.setRequisitionid(id);
		data.setParentid(parentId);
		data.setSubid(subid);
		
		return data;
	}
	
	public HashMap<String, Object> getPurchasePlan(String YSId) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryName("getPurchasePlanByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
			modelMap.put("data", dataModel.getYsViewData());
			modelMap.put("recordCount", dataModel.getRecordCount());
			
		}
		
		return modelMap;		
	}
	
	public HashMap<String, Object> getPurchasePlanForExcess(String YSId) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryName("purchasePlanForExcess");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("YSId", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
			model.addAttribute("recordCount", dataModel.getRecordCount());
			modelMap.put("data", dataModel.getYsViewData());
			modelMap.put("recordCount", dataModel.getRecordCount());
			
		}
		
		return modelMap;		
	}
	
	public HashMap<String, Object> getExcessDetail(String requisitionId) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		dataModel.setQueryName("getRequisitionDetailForExcess");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("requisitionId", requisitionId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("detail",dataModel.getYsViewData().get(0));
			model.addAttribute("detailList",dataModel.getYsViewData());
			modelMap.put("data", dataModel.getYsViewData());
		}
		
		return modelMap;		
	}
	
	public HashMap<String, Object> getPartsOrderDetail(String peiYsid) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		dataModel.setQueryName("getPartsOrderDetailByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("peiYsid", peiYsid);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
			modelMap.put("data", dataModel.getYsViewData());
		}
		
		return modelMap;		
	}
	
	public HashMap<String, Object> getRequisitionHistory(
			String YSId) throws Exception {
		
		dataModel.setQueryName("getRequisitionById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	public HashMap<String, Object> getRequisitionDetail() throws Exception {

		String requisitionId = request.getParameter("requisitionId");
		
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");		
		dataModel.setQueryName("updateRequisition");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("requisitionId", requisitionId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("detail",dataModel.getYsViewData().get(0));
		
		return modelMap;		
	}
	

	public HashMap<String, Object> requisitionPrint() throws Exception {

		String requisitionId = request.getParameter("requisitionId");
		
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");		
		dataModel.setQueryName("updateRequisition");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("requisitionId", requisitionId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("detail",dataModel.getYsViewData().get(0));
		
		return modelMap;		
	}
	public HashMap<String, Object> getOrderDetail(
			String YSId) throws Exception {
		
		return getOrderDetail(YSId,"");
	}
	public HashMap<String, Object> getOrderDetail(
			String YSId,String peiYsid) throws Exception {
		
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderViewByPIId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);	
		userDefinedSearchCase.put("peiYsid", peiYsid);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
		model.addAttribute("order",dataModel.getYsViewData().get(0));
		
		return modelMap;		
	}
	
	
	public HashMap<String, Object> getRequisitionById(
			String YSId) throws Exception {
		
		dataModel.setQueryName("getRequisitionById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		
	}
	
	public void getRequisitionByRequisitionId(
			String recordId) throws Exception {
		
		dataModel.setQueryName("getRequisitionDetailById");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("recordId", recordId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		model.addAttribute("requisition",dataModel.getYsViewData().get(0));
				
	}
	
	public void materialRequisitionAddInit() throws Exception {

		
		//设置领料用途下拉框	
		model.addAttribute("usedType",util.getListOption(DicUtil.DIC_REQUISITION_USEDTYPE, ""));
		
	
	}

	public void materialRequisitionEdit() throws Exception {

		String recordId = request.getParameter("recordId");
		//申请单详情
		getRequisitionByRequisitionId(recordId);
		
		//设置领料用途下拉框	
		model.addAttribute("usedType",util.getListOption(DicUtil.DIC_REQUISITION_USEDTYPE, ""));

		model.addAttribute("editFlag","edit");//编辑标识
	}
	
	public void materialRquisitionView() throws Exception {

		String recordId = request.getParameter("recordId");

		//申请单详情
		getRequisitionByRequisitionId(recordId);
	}
	
	
	public void materialRquisitionInsert() throws Exception {

		String recordId = insertMaterialRquisition();

		//申请单详情
		getRequisitionByRequisitionId(recordId);
	}
	
	/**
	 * 新增 单独领料申请
	 * @return
	 */
	private String insertMaterialRquisition(){
		
		String recordId = "";
		ts = new BaseTransaction();

		try {
			ts.begin();
			
			B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
			B_RequisitionDetailData detail = reqModel.getReqDetail();

			//取得领料单编号
		
			float quantity = stringToFloat(detail.getQuantity());
			
			if(quantity <= 0)
				return recordId;
			recordId = reqData.getRecordid();
			if(isNullOrEmpty(recordId)){
				//
				reqData = getRequisitionId(reqData);
				String requisitionid = reqData.getRequisitionid();
				detail.setRequisitionid(requisitionid);
				
				insertRequisitionDetail(detail);//领料明细

				//领料申请insert
				String ysid = reqData.getYsid();
				if(isNullOrEmpty(ysid))
					reqData.setYsid(reqData.getRequisitionid());//单独领料申请编号
				
				reqData.setRequisitionsts(Constants.STOCKOUT_2);//待出库
				reqData.setRequisitiontype(reqData.getUsedtype());//直接，研发领料申请
				
				insertRequisition(reqData);//直接领料
				
				recordId = reqData.getRecordid();
			}else{
				//update				
				updateRequisitionDetail(detail);//领料明细
				//领料申请
				updateRequisition(reqData);
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
		
		return recordId;
	}


	public HashMap<String, Object> uploadPhotoAndReload(
			MultipartFile[] headPhotoFile,
			String folderName,String fileList,String fileCount) throws Exception {

		String requisitionId = request.getParameter("requisitionId");

		FilePath file = getPath(requisitionId);
		String savePath = file.getSave();						
		String viewPath = file.getView();
		String webPath = file.getWeb();


		String photoName  = requisitionId + "-" + CalendarUtil.timeStempDate(); 
		
		uploadPhoto(headPhotoFile,photoName,viewPath,savePath,webPath);		

		ArrayList<String> list = getFiles(savePath,webPath);
		modelMap.put(fileList, list);
		modelMap.put(fileCount, list.size());
	
		return modelMap;
	}
	
	public HashMap<String, Object> deletePhotoAndReload(
			String folderName,String fileList,String fileCount) throws Exception {

		String path = request.getParameter("path");
		String requisitionId = request.getParameter("requisitionId");

		deletePhoto(path);//删除图片

		getPhoto(requisitionId,folderName,fileList,fileCount);
		
		return modelMap;
	}
	
	
	public HashMap<String, Object> getProductPhoto() throws Exception {
		
		String requisitionId = request.getParameter("requisitionId");

		getPhoto(requisitionId,"product","productFileList","productFileCount");
	
		return modelMap;
	}
	

	private void getPhoto(
			String requisitionId,
			String folderName,String fileList,String fileCount) {

		
		FilePath file = getPath(requisitionId);
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
	
	private FilePath getPath(String key1){
		FilePath filePath = new FilePath();
		
		filePath.setSave(
				session.getServletContext().
				getRealPath(BusinessConstants.PATH_MATERIALREQUISITIONFILE)
				+"/"+key1
				);	
		filePath.setView(
				session.getServletContext().
				getRealPath(BusinessConstants.PATH_MATERIALREQUISITIONVIEW)
				+"/"+key1
				);	

		filePath.setWeb(BusinessConstants.PATH_MATERIALREQUISITIONVIEW
				+key1+"/"
				);	
		
		return filePath;
	}
}
