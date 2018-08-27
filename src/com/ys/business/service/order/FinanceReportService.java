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
import com.ys.business.action.model.common.FinanceMouthly;
import com.ys.business.action.model.order.FinanceReportModel;
import com.ys.business.db.dao.B_CostBomDao;
import com.ys.business.db.dao.B_CostBomDetailDao;
import com.ys.business.db.dao.B_InventoryMonthlyReportDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.data.B_CostBomData;
import com.ys.business.db.data.B_CostBomDetailData;
import com.ys.business.db.data.B_InventoryMonthlyReportData;
import com.ys.business.db.data.B_OrderDetailData;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;;

@Service
public class FinanceReportService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private FinanceReportModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;
	HttpSession session;	

	public FinanceReportService(){
		
	}

	public FinanceReportService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			FinanceReportModel reqModel,
			UserInfo userInfo){
		
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
	
	public HashMap<String, Object> reportForDaybookSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		//String[] keyArr = getSearchKey(Constants.FORM_PAYMENTREQUEST,data,session);

		

		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryName("financeReprotForDaybook");//流水账
		baseQuery = new BaseQuery(request, dataModel);
		
		String strMonthly = getJsonData(data, "monthly");
		FinanceMouthly monthly = new FinanceMouthly(strMonthly);
		userDefinedSearchCase.put("startDate", monthly.getStartDate());
		userDefinedSearchCase.put("endDate", monthly.getEndDate());

		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		//String sql = getSortKeyFormWeb(data,baseQuery);	
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
	
	public HashMap<String, Object> inventoryMonthlySearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		

		String monthday = getJsonData(data, "monthday");
		FinanceMouthly monthly = new FinanceMouthly(monthday);
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryName("financeReprotForMonthly");//申请单一览
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("startDate", monthly.getStartDate());
		userDefinedSearchCase.put("endDate", monthly.getEndDate());

		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String strMonthly= getJsonData(data, "monthly");;
		String sql = baseQuery.getSql();
		sql = sql.replace("#0", strMonthly.replace("-", ""))
				.replace("#1", monthly.getStartDate())
				.replace("#2", monthly.getEndDate());
		
		List<String> list = new ArrayList<>();
		list.add(strMonthly.replace("-", ""));
		list.add(monthly.getStartDate());
		list.add(monthly.getEndDate());
		
		baseQuery.getYsQueryData(sql,list,iStart, iEnd);	 
				
		if ( iEnd > dataModel.getYsViewData().size()){			
			iEnd = dataModel.getYsViewData().size();			
		}		
		modelMap.put("sEcho", sEcho); 		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
	public HashMap<String, Object> inventoryReportSearch(String data) throws Exception{
		
		//确认该月的报表是否做成
		data = URLDecoder.decode(data, "UTF-8");
		String monthly = getJsonData(data, "monthly");
		
		//查询既存报表
		//getInventoryMonthly(data);
				
		return modelMap;
		
	}
	

	public HashMap<String, Object> createInventoryReport(String data) throws Exception{
		
		//确认该月的报表是否做成
		data = URLDecoder.decode(data, "UTF-8");
		String monthly = getJsonData(data, "monthly");
		int record = checkInventoryMonthlyReport(monthly);
		
		if(record <= 0 ){
			//新建报表
			createInventoryReport(data);
		}else{
			//查询既存报表
			//getInventoryMonthly(data);
		}
		
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
	public HashMap<String, Object> costAccountingSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_COSTACCOUTING,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		String monthday = request.getParameter("monthday");
		if(isNullOrEmpty(monthday)){
			monthday = CalendarUtil.getToDay();
		}
		FinanceMouthly monthly = new FinanceMouthly(monthday);

		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryName("costAccountingList");//
		
		baseQuery = new BaseQuery(request, dataModel);
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

		//040：查询部分入库，不再区分月份
		if(("040").equals(statusFlag)){
			userDefinedSearchCase.put("startDate", "");//忽略其时间段
			userDefinedSearchCase.put("endDate", "");//忽略其时间段			
		}
		
		if(("010").equals(statusFlag)){
			//having=" stockinQty+0 < quantity+0 ";//ALL
			
		}else if(("020").equals(statusFlag)){
			having=" storageFinish ='020' and accountingDate='' ";//待核算
			
		}else if(("030").equals(statusFlag)){
			having=" accountingDate!='' ";//已核算
			
		}else if(("040").equals(statusFlag)){
			having="stockinQty+0 > 0 AND storageFinish ='010' ";//部分入库
		}
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);	
		sql = sql.replace("#", having);
		System.out.println("财务核算SQL："+sql);
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
	
	@SuppressWarnings("unchecked")
	private int checkInventoryMonthlyReport(
			String monthly) throws Exception{
		
		String astr_Where = " monthly ='"+ monthly +"' And deleteFlag='0' ";
		List<B_InventoryMonthlyReportData> list =
				new B_InventoryMonthlyReportDao().Find(astr_Where);
		
		return list.size();
	}
	
	public List<Map<Integer, Object>> getDaybookList() throws Exception {

		dataModel.setQueryName("financeReprotForDaybook");//流水账
		
		String strMonthly = request.getParameter("monthly");
		FinanceMouthly monthly = new FinanceMouthly(strMonthly);
		userDefinedSearchCase.put("startDate", monthly.getStartDate());
		userDefinedSearchCase.put("endDate", monthly.getEndDate());
		
		baseQuery = new BaseQuery(request, dataModel);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		List<Map<Integer, Object>> listMap = new ArrayList<Map<Integer, Object>>();
		ArrayList<HashMap<String, String>>  hashMap = baseQuery.getYsQueryData(0,0);	

		String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "dayBookTitle");
		String[] titles = title.split(",",-1);
		for(int i=0;i<hashMap.size();i++){
			HashMap<String, String> map = hashMap.get(i);
			String flag = map.get("dataFlag");
			Map<Integer, Object> excel = new HashMap<Integer, Object>();
			for(int j=0;j<titles.length;j++){
			
				if(j == 2){//取得入出库的单据类型
					
					if(("I").equals(flag)){//入库
						excel.put(j,map.get("stockinType"));
					}else{//出库
						excel.put(j,map.get("stockoutType"));
					}
				}else{
					excel.put(j,map.get(titles[j]));
				}
			}
			listMap.add(excel);
		}
		
		return  listMap;

	}
	
	/**
	 * 流水账 EXCEL导出
	 * @throws Exception
	 */
	public void excelForInvertory() throws Exception{
		
		//设置响应头，控制浏览器下载该文件
				
		//数据取得
		List<Map<Integer, Object>>  datalist = getDaybookList( );		

		String monthly = request.getParameter("monthly");
		String fileName = "accountsDetail_" +monthly+"_"+ CalendarUtil.timeStempDate()+".xls";
		String dest = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)
				+"/"+File.separator+fileName;
       
		String tempFilePath = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_EXCELTEMPLATE)+File.separator+"daybookreport.xls";
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
		String head = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "dayBookExcel");
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
	
	public void costAccountingAdd() throws Exception{

		String YSId = request.getParameter("YSId");
		getOrderDetailByYSId(YSId);
		
		getLaborCost(YSId);
		
	}
	
	private void getLaborCost(
			String YSId	) throws Exception{

			dataModel.setQueryFileName("/business/order/financequerydefine");
			dataModel.setQueryName("getLabolCostByYsid");		
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("YSId", YSId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
			baseQuery.getYsFullData();
			
			model.addAttribute("LaborCost",dataModel.getYsViewData().get(0).get("labolCost"));
		}
	
	public void getOrderDetailByYSId(
		String YSId	) throws Exception{

		//dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		model.addAttribute("order", dataModel.getYsViewData().get(0));
		
	}
	

	public HashMap<String, Object> getStockoutDetail() throws Exception{

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		
		String YSId = request.getParameter("YSId");
		//check StockOutDate
		//int outCount = checkStockOut(YSId);
		
		//if(outCount > 0 ){
		//	HashMap = getStockoutByMaterialId(YSId);
		//}else{
			HashMap = getPlanBomDetail(YSId);
		//}		
		return HashMap;			
	}
	
	private int checkStockOut(String YSId) throws Exception{
		
		int rtn = 0;

		dataModel.setQueryFileName("/business/order/financequerydefine");
		dataModel.setQueryName("checkStockOutById");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		rtn = dataModel.getRecordCount();
		
		return rtn;
	}
	
	private HashMap<String, Object> getPlanBomDetail(
		String YSId	) throws Exception{

		HashMap<String, Object> HashMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/order/financequerydefine");
		dataModel.setQueryName("planBomDetailById");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		HashMap.put("data", dataModel.getYsViewData());
		
		return HashMap;
		
	}
	
	private HashMap<String, Object> getStockoutByMaterialId(
			String YSId	) throws Exception{

			HashMap<String, Object> HashMap = new HashMap<String, Object>();

			dataModel.setQueryFileName("/business/order/financequerydefine");
			dataModel.setQueryName("stockoutDetailGroupByYsid");		
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("YSId", YSId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
			baseQuery.getYsFullData();
			
			HashMap.put("data", dataModel.getYsViewData());
			
			return HashMap;
			
	}
		
	
	public void insertCostBomAndView() throws Exception{
		
		String YSId = insertCostBomData();
		
		getOrderDetailByYSId(YSId);
		
		getCostBomDetail(YSId);
	}

		
	public void updateCostBomAndView() throws Exception{
		
		String YSId = updateCostBomData();
		
		getOrderDetailByYSId(YSId);
		
		getCostBomDetail(YSId);
	}
	
	
	private String updateCostBomData(){
		
		String YSId = "";
		ts = new BaseTransaction();
				
		try {
			ts.begin();
			
			B_CostBomData reqData = reqModel.getCostBom();

			YSId = reqData.getYsid();

			updateCostBom(reqData);
						
			ts.commit();			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return YSId;
	}
	
	private String insertCostBomData(){
		
		String YSId = "";
		ts = new BaseTransaction();
				
		try {
			ts.begin();
			
			B_CostBomData reqData = reqModel.getCostBom();
			List<B_CostBomDetailData> reqDataList = reqModel.getCostBomList();

			//取得BOM编号
			YSId = reqData.getYsid();
			String materialId = reqData.getMaterialid();
			reqData = setCostBomId(materialId,reqData);
			String bomId = reqData.getBomid();

			insertCostBom(reqData);
						
			for(B_CostBomDetailData data:reqDataList ){
				
				//物料明细
				if(isNullOrEmpty(data.getMaterialid()))
					continue;
				
				data.setBomid(bomId);
				insertCostBomDetail(data);
								
			}
			ts.commit();			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return YSId;
	}
	
	private void insertCostBom(B_CostBomData data) throws Exception {
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"财务核算BomInsert",userInfo);
		copyProperties(data,commData);
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		data.setAccountingdate(CalendarUtil.fmtYmdDate());
	
		new B_CostBomDao().Create(data);
	}
	
	private void updateCostBom(B_CostBomData data) throws Exception {
		
		B_CostBomData db = new B_CostBomDao(data).beanData;
		
		if(db == null || ("").equals(db)){
			
			insertCostBom(data);//新增
			
		}else{

			copyProperties(db,data);
			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"财务核算BomUpdate",userInfo);
			copyProperties(data,commData);
			
			new B_CostBomDao().Store(db);
		}
			
	
	}
	
	private void insertCostBomDetail(B_CostBomDetailData data) throws Exception {
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"财务核算BomInsert",userInfo);
		copyProperties(data,commData);
		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
	
		new B_CostBomDetailDao().Create(data);		
	}
	
	public B_CostBomData setCostBomId(
			String materialId,
			B_CostBomData reqData) throws Exception {

		//取得该产品的新BOM编号
		String parentId = BusinessService.getCostBomId(materialId)[0];
		String bomId = BusinessService.getCostBomId(materialId)[1];
		
		//新建
		reqData.setBomid(bomId);
		reqData.setSubid(BusinessConstants.FORMAT_00);
		reqData.setParentid(parentId);	
		
		return reqData;
		
	}

	public void getOrderAndCostBomDetail() throws Exception{
		
		String YSId = request.getParameter("YSId");

		getOrderDetailByYSId(YSId);
		
		getCostBomDetail(YSId);
	}

	public HashMap<String, Object> getCostBomDetail() throws Exception{
		String YSId = request.getParameter("YSId");
		
		return getCostBomDetail(YSId);
	}
	

	public HashMap<String, Object> getCostBomDetail(String YSId) throws Exception{
		
		HashMap<String, Object> HashMap = new HashMap<String, Object>();

		dataModel.setQueryFileName("/business/order/financequerydefine");
		dataModel.setQueryName("costBomDetailByYsid");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();

		HashMap.put("cost", dataModel.getYsViewData().get(0));
		HashMap.put("data", dataModel.getYsViewData());
		
		model.addAttribute("cost",dataModel.getYsViewData().get(0));
		
		return HashMap;
	}
	public HashMap<String, Object> updateExchangeRate() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		//更新汇率
		String currencyId = request.getParameter("currencyId");
		String exRate = request.getParameter("exRate");
		
		updateExchangeRate(currencyId,exRate);
		
		modelMap.put("message", NORMAL);	
		
		return modelMap;
		
	}
}
