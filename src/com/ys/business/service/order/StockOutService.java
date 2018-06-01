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

import com.ys.business.action.model.order.StockOutModel;
import com.ys.business.db.dao.B_MaterialDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_RequisitionDao;
import com.ys.business.db.dao.B_RequisitionDetailDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.dao.B_StockOutDetailDao;
import com.ys.business.db.data.B_MaterialData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_RequisitionData;
import com.ys.business.db.data.B_StockOutData;
import com.ys.business.db.data.B_StockOutDetailData;
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
public class StockOutService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private B_StockOutDao dao;
	private StockOutModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public StockOutService(){
		
	}

	public StockOutService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			StockOutModel reqModel,
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
		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		super.request = request;
		super.userInfo = userInfo;
		super.session = session;
		
	}
	
	public HashMap<String, Object> doSearch( String data,String makeType,String formId) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(formId,data,session);
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
		
		dataModel.setQueryName("requisitionList");//领料单一览
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2)){
			userDefinedSearchCase.put("requisitionSts", "");
		}
		//包装,或者是料件入库
		if(("G").equals(makeType)){//包装
			userDefinedSearchCase.put("makeTypeG", "G");
			userDefinedSearchCase.put("makeTypeL", "");
		}else{//料件
			userDefinedSearchCase.put("makeTypeG", "");
			userDefinedSearchCase.put("makeTypeL", "G");			
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

		String YSId = request.getParameter("YSId");
		String requisitionId = request.getParameter("requisitionId");
				
		B_StockOutData stock = new B_StockOutData();
		stock.setYsid(YSId);
		stock.setRequisitionid(requisitionId);
		reqModel.setStockout(stock);
		
		getOrderDetail(YSId);//订单信息
		
		model.addAttribute("depotList",util.getListOption(DicUtil.DIC_DEPOTLIST, ""));
	
	}
	

	public void stockoutHistoryInit() throws Exception {

		String YSId = request.getParameter("YSId");
		String stockOutId = request.getParameter("stockOutId");
				
		B_StockOutData stock = new B_StockOutData();
		stock.setYsid(YSId);
		stock.setStockoutid(stockOutId);
		reqModel.setStockout(stock);
	
	}
	

	public void edit() throws Exception {
		String stockoutId = request.getParameter("stockoutId");
	
		String where = " stockoutId='"+stockoutId+"' ";
		B_StockOutData data = checkStcokoutExsit(where);
		reqModel.setStockout(data);	

		model.addAttribute("depotList",util.getListOption(DicUtil.DIC_DEPOTLIST, ""));
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
		//String stockOutId = request.getParameter("stockOutId");
		
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
	
	public void insertAndReturn() throws Exception {

		String YSId = insertStorage();

		getOrderDetail(YSId);

	}

	public void updateAndReturn() throws Exception {

		String YSId = updateStorage();

		getOrderDetail(YSId);

	}
	/**
	 * 成品出库
	 * @throws Exception
	 */
	public void insertProductAndReturn() throws Exception {

		String YSId = insertProductStorage();

		getOrderDetail(YSId);

	}
	
	private String updateStorage(){
		String YSId = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
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
	
	private String insertStorage(){
		String YSId = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_StockOutData reqData = reqModel.getStockout();
			List<B_StockOutDetailData> reqDataList = reqModel.getStockList();

			//取得出库单编号
			YSId= reqData.getYsid();
			reqData = getStorageRecordId(reqData);			
			String id = reqData.getStockoutid();
			String requisitionId = reqData.getRequisitionid();
	
			//出库记录
			//取得出库类别（单据类型）
			String stockoutType = getStockoutTypeFromRequisition(requisitionId);
			reqData.setStockouttype(stockoutType);
			insertStockOut(reqData);
			

			for(B_StockOutDetailData data:reqDataList ){
				float quantity = stringToFloat(data.getQuantity());
				
				if(quantity <= 0)
					continue;
				
				data.setStockoutid(id);
				insertStockOutDetail(data);								
				
				//更新库存
				updateMaterial(data.getMaterialid(),quantity);//更新库存
			
			}
			
			updateRequisition(requisitionId);
			
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
	
	/**
	 * 成品出库
	 * @return
	 */
	private String insertProductStorage(){
		String YSId = "";
		ts = new BaseTransaction();		
		
		try {
			ts.begin();
			
			B_StockOutData reqData = reqModel.getStockout();
			B_StockOutDetailData detail = reqModel.getStockDetail();

			//取得出库单编号
			YSId= reqData.getYsid();
			reqData = getStorageRecordId(reqData);			
			String id = reqData.getStockoutid();
	
			//出库记录
			reqData.setStockouttype(Constants.STOCKOUTTYPE_2);
			insertStockOut(reqData);
			
		
			float quantity = stringToFloat(detail.getQuantity());
			
			detail.setStockoutid(id);
			insertStockOutDetail(detail);								
			
			//更新库存
			updateMaterial(detail.getMaterialid(),quantity);			
			
			//更新订单状态->出库
			updateOrderDetail(YSId,quantity);
			
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

	@SuppressWarnings("unchecked")
	private void updateOrderDetail(
			String ysid,
			float quantity) throws Exception{
		String where = "YSId = '" + ysid  +"' AND deleteFlag = '0' ";
		List<B_OrderDetailData> list  = new B_OrderDetailDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;	
		
		//更新DB
		B_OrderDetailData data = list.get(0);
	
		float orderQty = stringToFloat(data.getQuantity());//订单数量
		float oldQuan = stringToFloat(data.getStockoutqty());//已出库数量		
		
		float totalQuan = oldQuan + quantity;//累计出库
		
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"ProductStockOutUpdate",userInfo);
		copyProperties(data,commData);
	
		if(totalQuan >= orderQty){
			data.setStatus(Constants.ORDER_STS_5);//已出库
		}else{
			data.setStatus(Constants.ORDER_STS_51);//出库中			
		}
		
		data.setStockoutqty(String.valueOf(totalQuan));
		data.setStockoutdate(CalendarUtil.fmtYmdDate());//出库时间
		new B_OrderDetailDao().Store(data);
	}
	
	//更新当前库存:出库时，减少“当前库存”，减少“待出库“
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
		float iNewQuantiy = iQuantity - reqQuantity;		
		//待入库
		float waitstockin = stringToFloat(data.getWaitstockin());
		//待出库
		float istockout = stringToFloat(data.getWaitstockout());		
		float iNewStockOut = istockout - reqQuantity;
		
		//虚拟库存=当前库存 + 待入库 - 待出库
		float availabeltopromise = iNewQuantiy + waitstockin - iNewStockOut;
		
		data.setQuantityonhand(String.valueOf(iNewQuantiy));
		data.setWaitstockout(String.valueOf(iNewStockOut));
		data.setAvailabeltopromise(String.valueOf(availabeltopromise));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"PurchaseStockInUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);
		
	}	

	@SuppressWarnings("unchecked")
	private void updateRequisition(
			String id) throws Exception{

		String where = "requisitionId = '" + id  +"' AND deleteFlag = '0' ";
		List<B_RequisitionData> list  = new B_RequisitionDao().Find(where);
		if(list ==null || list.size() == 0)
			return ;	

		B_RequisitionData data = list.get(0);		
		//更新状态
		data.setRequisitionsts(Constants.STOCKOUT_3);//已出库		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"RequisitionUpdate",userInfo);
		copyProperties(data,commData);
		
		new B_RequisitionDao().Store(data);
		
	}

	
	private void insertStockOut(
			B_StockOutData stock) throws Exception {
		
		B_StockOutData db=null;
		try {
			db = new B_StockOutDao(stock).beanData;
		} catch (Exception e) {
			// nothing
		}		
		
		if(db == null || db.equals("")){
			//插入新数据
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"StockOutInsert",userInfo);
			copyProperties(stock,commData);
			guid = BaseDAO.getGuId();
			stock.setRecordid(guid);
			stock.setKeepuser(userInfo.getUserId());//默认为登陆者
			
			dao.Create(stock);
		}else{
			//更新
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"StockOutUpdate",userInfo);
			copyProperties(stock,commData);
			stock.setKeepuser(userInfo.getUserId());//默认为登陆者
			
			dao.Store(stock);
		}
		
	}
	
	private void insertStockOutDetail(
			B_StockOutDetailData stock) throws Exception {

		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"StockOutDetailInsert",userInfo);
		copyProperties(stock,commData);
		guid = BaseDAO.getGuId();
		stock.setRecordid(guid);
				
		new B_StockOutDetailDao().Create(stock);
	}
	

	
	public HashMap<String, Object> getRequisitionDetail() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String requisitionId = request.getParameter("requisitionId");
		
		dataModel.setQueryName("requisitionDetailById");
		userDefinedSearchCase.put("requisitionId", requisitionId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
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
	

	
	public B_StockOutData getStorageRecordId(
			B_StockOutData data) throws Exception {
	
		String parentId = BusinessService.getshortYearcode()+
				BusinessConstants.SHORTNAME_CK;
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
	
	public HashMap<String, Object> getStockoutHistory(
			String YSId,String stockOutId) throws Exception {
		
		dataModel.setQueryName("stockoutHistory");
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("stockOutId", stockOutId);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	

	public HashMap<String, Object> getStockoutHistoryForProduct(
			String YSId,String materialId) throws Exception {
		
		dataModel.setQueryName("stockoutHistoryForProduct");
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("materialId", materialId);	
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	
	public void getStockoutByMaterialId() throws Exception {
		String materialId = request.getParameter("materialId");
		dataModel.setQueryName("stockoutByMaterialId");
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("materialId", materialId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() > 0 ){
			model.addAttribute("material", dataModel.getYsViewData().get(0));
			model.addAttribute("stockoutList", dataModel.getYsViewData());
		}	
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
	
		return modelMap;
	}
	
	public HashMap<String, Object> deletePhotoAndReload(
			String folderName,String fileList,String fileCount) throws Exception {

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
	

	@SuppressWarnings("unchecked")
	private void deleteStockoutDetail(
			String stockoutId) throws Exception{
		
		B_StockOutDetailDao dao = new B_StockOutDetailDao();
		//
		String where = "stockoutId = '" + stockoutId  +"' AND deleteFlag = '0' ";
		List<B_StockOutDetailData> list  = dao.Find(where);
		if(list ==null || list.size() == 0)
			return ;
		
		for(B_StockOutDetailData dt:list){
			
			dao.Remove(dt);

			//更新库存(恢复)
			String mateId = dt.getMaterialid();
			float quantity = (-1) * stringToFloat(dt.getQuantity());
			
			updateMaterial(mateId,quantity);
			
		}		
	}
	
	public void stockoutDownloadExcelForfinance() throws Exception{
		
		//设置响应头，控制浏览器下载该文件
				
		//baseBom数据取得
		String key1 = request.getParameter("key1");
		String key2 = request.getParameter("key2");		

		List<Map<Integer, Object>>  datalist = getStockoutList( key1, key2);		
		
		String fileName = CalendarUtil.timeStempDate()+".xls";
		String dest = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)
				+"/"+File.separator+fileName;
       
		String tempFilePath = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_EXCELTEMPLATE)+File.separator+"stockout.xls";
        File file = new File(dest);
       
        OutputStream out = new FileOutputStream(file);         
        ExcelUtil excel = new ExcelUtil(response);

        //读取模板
        Workbook wbModule = excel.getTempWorkbook(tempFilePath);
        //数据填充的sheet
        int sheetNo=0;
        //title
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //dataMap.put("D1", materialId);
        //dataMap.put("H1", mateiralName);
        wbModule = excel.writeData(wbModule, dataMap, sheetNo);        
        
        //detail
        //必须为列表头部所有位置集合,输出 数据单元格样式和头部单元格样式保持一致
		String head = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "stockoutForFinanceExcel");
        String[] heads = head.split(",");  
        excel.writeDateList(wbModule,heads,datalist,sheetNo);
         
        //写到输出流并移除资源
        excel.writeAndClose(tempFilePath, out);
        System.out.println("导出成功");
        out.flush();
        out.close();
        
      //***********************Excel下载************************//
        excel.downloadFile(dest,fileName);
	}


	public List<Map<Integer, Object>> getStockoutList(
			String key1,String key2) throws Exception {
		
		dataModel.setQueryName("stockoutForfinance");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		List<Map<Integer, Object>> listMap = new ArrayList<Map<Integer, Object>>();
		ArrayList<HashMap<String, String>>  hashMap = baseQuery.getYsFullData();	
		
		for(int i=0;i<hashMap.size();i++){
			String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "stockoutForFinanceTitle");
			String[] titles = title.split(",",-1);
			Map<Integer, Object> excel = new HashMap<Integer, Object>();
			for(int j=0;j<titles.length;j++){
				excel.put(j,hashMap.get(i).get(titles[j]));		
			}
			listMap.add(excel);
		}
		
		return  listMap;

	}
	
	
	public HashMap<String, Object> getStockoutForFinance(String data) throws Exception{

		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		

		dataModel.setQueryName("stockoutForfinance");
		baseQuery = new BaseQuery(request, dataModel);
		
		String key1 = request.getParameter("key1").trim().toUpperCase();
		String key2 = request.getParameter("key2").trim().toUpperCase();
		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if(notEmpty(key1) || notEmpty(key2)){
			userDefinedSearchCase.put("requisitionSts", "");
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
	


	public HashMap<String, Object> doSearchForProduct(String data) throws Exception{

		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		

		dataModel.setQueryName("stockoutforproduct");
		baseQuery = new BaseQuery(request, dataModel);
		
		String[] keyArr = getSearchKey(Constants.FORM_PRODUCTSTOCKOUT,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];

		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		String status = request.getParameter("status");
		//if(notEmpty(key2) || notEmpty(key1)){
		//	status = "";//有查询key,忽略其状态值
		//}
		StringBuffer sb = new StringBuffer();
		sb.append(" AND ");;
		if(("030").equals(status)){
			//未入库
			sb.append(" a.stockinQty+0 <= 0 ");
			userDefinedSearchCase.put("status", "050");
			key1 = "";key2="";//清空关键字
		}else if(("040").equals(status)){
			//未出库(已入库)
			sb.append(" a.stockoutQty+0 <= 0 AND a.stockinQty+0 > 0  ");
			userDefinedSearchCase.put("status", "050");
			key1 = "";key2="";//清空关键字
		}else if(("050").equals(status)){
			//已出库
			sb.append(" a.stockoutQty+0 = a.orderQty+0 ");
			key1 = "";key2="";//清空关键字		
		}else if(("051").equals(status)){
			//部分出库
			sb.append(" a.stockoutQty+0 > 0 AND a.stockoutQty+0 < a.orderQty+0 ");
			key1 = "";key2="";//清空关键字		
		}else{
			//普通查询
			sb.append(" 1=1 ");
			userDefinedSearchCase.put("keyword1", key1);
			userDefinedSearchCase.put("keyword2", key2);
		}

		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", sb.toString());
		baseQuery.getYsQueryData(sql,sb.toString(),iStart, iEnd);
				
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
	

	public void productStockoutAddInit() throws Exception {
		String YSId = request.getParameter("YSId");
		//String stockOutId = request.getParameter("stockOutId");
		
		//取得订单信息
		getOrderDetail(YSId);
		//取得入库信息
		getStockinDetail(YSId,"");//入库明细

	
	}
	
	public HashMap<String, Object> getProductStockoutDetail() throws Exception {
		
		String YSId = request.getParameter("YSId");	
		String materialId = request.getParameter("materialId");	
		
		return getStockoutHistoryForProduct(YSId,materialId);//出库明细	
		
	}

	public void getStockinDetail(String YSId,String stockOutId) throws Exception {

		dataModel.setQueryFileName("/business/order/manufacturequerydefine");
		dataModel.setQueryName("stockoutforproduct");
		userDefinedSearchCase.put("YSId", YSId);
		userDefinedSearchCase.put("stockOutId", stockOutId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql(); 
		StringBuffer sb = new StringBuffer();
		sb.append(" AND 1=1 ");
		sql = sql.replace("#", sb);
		baseQuery.getYsFullData(sql,sb.toString());

		if(dataModel.getRecordCount() > 0) {
			model.addAttribute("stockin",dataModel.getYsViewData().get(0));
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getStockoutTypeFromRequisition(String requisitionId) throws Exception{
		
		String stockoutType = Constants.STOCKOUTTYPE_1;
		String where = " requisitionId='"+requisitionId+"' AND deleteFlag='0'";
		List<B_RequisitionData> list = new B_RequisitionDao().Find(where);
		
		if(list == null || list.size() == 0)
			return stockoutType;
		
		String requisitionType = list.get(0).getRequisitiontype();
		if((Constants.REQUISITION_BLOW).equals(requisitionType)
				|| (Constants.REQUISITION_BLISTE).equals(requisitionType)
				|| (Constants.REQUISITION_INJECT).equals(requisitionType)){
			stockoutType = Constants.STOCKOUTTYPE_3;
		}else{
			stockoutType = requisitionType;
		}
		
		return stockoutType;
	}
	
}
