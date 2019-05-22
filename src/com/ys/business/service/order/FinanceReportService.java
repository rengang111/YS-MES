package com.ys.business.service.order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.ys.business.action.model.common.FinanceMouthly;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.order.FinanceReportModel;
import com.ys.business.db.dao.B_CostBomDao;
import com.ys.business.db.dao.B_CostBomDetailDao;
import com.ys.business.db.dao.B_InventoryMonthlyReportDao;
import com.ys.business.db.dao.B_OrderDetailDao;
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

import net.sf.json.JSONArray;
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

		String materialId = getJsonData(data, "materialId");
		String strMonthly = request.getParameter("monthday");
		FinanceMouthly monthly = new FinanceMouthly(strMonthly);

		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		String sql = baseQuery.getSql();
		sql = sql.replace("#0", monthly.getStartDate());
		sql = sql.replace("#1", monthly.getEndDate());
		//sql = sql.replace("#2", materialId);
		
		List<String> list = new ArrayList<String>();
		list.add(monthly.getStartDate());
		list.add(monthly.getEndDate());
		//list.add(materialId);
		
		System.out.println("日期别流水："+sql);
		
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
	
	public HashMap<String, Object> getBeginingStorage() throws Exception{

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		String materialId = request.getParameter("materialId");
		String strMonthly = request.getParameter("monthday");
		
		FinanceMouthly monthly = new FinanceMouthly(strMonthly);
		String start = monthly.getStartDate();
		String end = monthly.getEndDate();
		
		end = CalendarUtil.dateAddToString(end,-1);//当月的期末就是下月的期初，so,期末的日期还是25号
		
		dataModel.setQueryName("getStorageValueByMonthly");//
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("materialId",materialId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#0", start);
		sql = sql.replace("#1", end);
		
		List<String> list = new ArrayList<String>();
		list.add(start);
		list.add(end);
		
		System.out.println("期初期末值取得："+sql);
		
		baseQuery.getYsQueryData(sql,list,0,0);

		modelMap.put("beginingRecord", dataModel.getRecordCount());
		modelMap.put("begining", dataModel.getYsViewData());
		
		modelMap.put("startDate",CalendarUtil.dateAddToString(start,1));//页面显示用：上月26号期初
		modelMap.put("endDate",end);//当月25号期末
		
		return modelMap;
	}
	
	public HashMap<String, Object> reportForDaybookByMaterialId( String data) throws Exception {
	
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		//重新取得期末期初值
		modelMap = getBeginingStorage();
		
		modelMap = reportForDaybookByMaterialIdSearch(data,modelMap);
		
		return modelMap;
	}
	public HashMap<String, Object> reportForDaybookByMaterialIdSearch(
			String data,HashMap<String, Object> modelMap) throws Exception {
		
		dataModel.setQueryName("financeReprotForDaybookBymaterialId");//物料别流水账
		baseQuery = new BaseQuery(request, dataModel);

		String materialId = request.getParameter("materialId");
		String strMonthly = request.getParameter("monthday");
		
		FinanceMouthly monthly = new FinanceMouthly(strMonthly);

		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#0", monthly.getStartDate());
		sql = sql.replace("#1", monthly.getEndDate());
		sql = sql.replace("#2", materialId);
		
		System.out.println("物料别流水："+sql);
		
		baseQuery.getYsFullData(sql);	 
						
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
		
		//String monthday = request.getParameter("monthday");
		//if(isNullOrEmpty(monthday)){
		//	monthday = CalendarUtil.getToDay();
		//}
		//FinanceMouthly monthly = new FinanceMouthly(monthday);

		
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
		//userDefinedSearchCase.put("startDate", monthly.getStartDate());
		//userDefinedSearchCase.put("endDate", monthly.getEndDate());

		String statusFlag = request.getParameter("statusFlag");
		String unFinished = request.getParameter("unFinished");
		String monthday = request.getParameter("monthday");

		userDefinedSearchCase.put("groupFlag", monthday);
		
		String having = "1=1";
		//String monthDay1 = " AND checkInDate > '" + monthly.getStartDate()
		//		+"' AND checkInDate < '"+monthly.getEndDate()+"' ";
		//String monthDay2 = " AND a.orderDate < '" + monthly.getEndDate() +"' ";
		
		String str_table=" v_costAccounting_m ";
		//查询：关键字，不再区分月份
		if(notEmpty(key1) || notEmpty(key2)){
			str_table = " v_costAccounting_m_no_where ";
			statusFlag = "";//有查询key，则忽略其状态
			//monthDay1 = "";
			//monthDay2 = "";			
		}

		//查询：部分入库，不再区分月份
		if(("B").equals(unFinished)){
			statusFlag = "";//不再区分审核状态
			//monthDay1 = "";
			//monthDay2 = "";
			str_table = " v_costAccounting_m_no_where ";
			having = " REPLACE(completedQuantity,',','')+0 > 0 AND REPLACE(completedQuantity,',','')+0  < REPLACE(quantity,',','')+0 ";		
			//having="stockinQty+0 > 0 AND stockinQty+0 < quantity+0 AND storageFinish ='010'";		
		}
		
		//查询：审核状态
		if(("A").equals(statusFlag)){//ALL(待核算 + 已核算)
			//having=" stockinQty+0 >= quantity+0 ";
			
		}else if(("D").equals(statusFlag)){//待核算
			str_table = " v_costAccounting_m_no_where ";
			having = "  REPLACE(completedQuantity,',','')+0  >= REPLACE(quantity,',','')+0  AND ysidAlready IS NULL";
			
			//having=" storageFinish ='020' and accountingDate='' ";
			
		}else if(("Y").equals(statusFlag)){//已核算
			//having=" accountingDate!='' ";
			
		}
		
		//查询：业务组
		String team = request.getParameter("team");
		if(("ALL").equals(team)){//全部
			userDefinedSearchCase.put("team", "");
		}else{
			userDefinedSearchCase.put("team", team);
		}
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = getSortKeyFormWeb(data,baseQuery);
		
		sql = sql.replace("#1", str_table);
		sql = sql.replace("#2", having);
		//sql = sql.replace("#1", monthly.getStartDate());
		//sql = sql.replace("#1", monthDay1);
		//sql = sql.replace("#2", monthDay2);
		//sql = sql.replace("#3", having);
		System.out.println("财务核算SQL："+sql);
		baseQuery.getYsQueryDataNoPage(sql);	
		//baseQuery.getYsQueryData(sql,having,iStart, iEnd); 
				
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
		
		getOrderProcess(YSId);//取得订单过程的跟单费用
		
	}
	
	/**
	 * 配件订单
	 * @throws Exception
	 */
	public void costAccountingPeiAdd() throws Exception{

		String YSId = request.getParameter("YSId");
		getOrderDetailByYSId(YSId);		
	}
	
	private void getLaborCost(
			String YSId	) throws Exception{

			dataModel.setQueryFileName("/business/order/financequerydefine");
			dataModel.setQueryName("getLabolCostByYsid");		
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("YSId", YSId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
			baseQuery.getYsFullData();
			if(dataModel.getRecordCount() > 0 ){		
				model.addAttribute("LaborCost",dataModel.getYsViewData().get(0).get("labolCost"));
			}else{
				model.addAttribute("LaborCost","0");
			}
	}
	
	private void getOrderProcess(
			String YSId	) throws Exception{

			dataModel.setQueryFileName("/business/order/financequerydefine");
			dataModel.setQueryName("orderProcessByYsid");		
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("YSId", YSId);
			userDefinedSearchCase.put("type", "D");//跟单费用
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
			baseQuery.getYsFullData();
			if(dataModel.getRecordCount() > 0 ){
				model.addAttribute("deductCost",dataModel.getYsViewData().get(0).get("cost"));				
			}else{
				model.addAttribute("deductCost","0");
			}
		}
	
	public void getOrderDetailByYSId(
		String YSId	) throws Exception{

		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0 ){
			model.addAttribute("order", dataModel.getYsViewData().get(0));
		}
		
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
	

	public HashMap<String, Object> getOrderPeiByMaterialId() throws Exception{

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		
		String YSId = request.getParameter("YSId");
	
		HashMap = getOrderPeiDetail(YSId);
		
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
		
		String sql = baseQuery.getSql();
		sql = sql.replace("#", YSId);
		System.out.println("领料统计查询："+sql);
		
		baseQuery.getYsFullData(sql,YSId);
		
		HashMap.put("data", dataModel.getYsViewData());
		
		return HashMap;
		
	}
	
	private HashMap<String, Object> getOrderPeiDetail(
			String YSId	) throws Exception{

			HashMap<String, Object> HashMap = new HashMap<String, Object>();

			dataModel.setQueryFileName("/business/order/financequerydefine");
			dataModel.setQueryName("orderDetailPeiForCostAccouting");		
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
			reqData.setBomid(YSId);
			//String materialId = reqData.getMaterialid();
			//reqData = setCostBomId(materialId,reqData);
			//String bomId = reqData.getBomid();

			insertCostBom(reqData);
						
			for(B_CostBomDetailData data:reqDataList ){
				
				//物料明细
				if(isNullOrEmpty(data.getMaterialid()))
					continue;
				
				data.setBomid(YSId);
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
	
	private B_CostBomData setCostBomId(
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

		getOrderProcess(YSId);//取得订单过程的跟单费用
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
	

	public HashMap<String, Object> getBaseBomCostByYsid(String data) throws Exception{
		
		HashMap<String, Object> HashMap = new HashMap<String, Object>();

		String YSId = request.getParameter("YSId");
		
		dataModel.setQueryFileName("/business/order/financequerydefine");
		dataModel.setQueryName("getBaseBomCostByYsid");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();

		HashMap.put("data", dataModel.getYsViewData());		
		
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
	

	/**
	 * 财务核算月度统计导出
	 * @throws Exception
	 */
	public void downloadExcelForAStatistics(String data) throws Exception{
		
		//设置响应头，控制浏览器下载该文件
				
		//数据取得
		List<Map<Integer, Object>>  datalist = getDownloadListForStatistics(data);			
		
		String fileName = CalendarUtil.timeStempDate()+".xls";
		String dest = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)
				+"/"+File.separator+fileName;
       
		String tempFilePath = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_EXCELTEMPLATE)+File.separator+"costStatistics.xls";
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
		String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "costStatisticsExcel");
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
	
	/**
	 * 财务核算导出
	 * @throws Exception
	 */
	public void downloadExcelForAccounting(String data) throws Exception{
		
		//设置响应头，控制浏览器下载该文件
				
		//数据取得
		//List<Map<Integer, Object>>  datalist = getDownloadList();	
		List<Map<Integer, Object>>  datalist = getDownloadList(data);			
		
		String fileName = CalendarUtil.timeStempDate()+".xls";
		String dest = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)
				+"/"+File.separator+fileName;
       
		String tempFilePath = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_EXCELTEMPLATE)+File.separator+"costaccounting.xls";
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
		String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "costAccoutingExcel");
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
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public List<Map<Integer, Object>> getDownloadListForStatistics(String jsonStr) throws Exception {

		 List<Map<Integer, Object>> listMap = new ArrayList<Map<Integer, Object>>();
		 
		 dataModel.setQueryName("getPurchaseStockInById");
		 baseQuery = new BaseQuery(request, dataModel);

		 String htmlStr = reqModel.getJsonData();
		 
		 //String htmlStr = URLDecoder.decode(request.getParameter("html"), "UTF-8");
		 JSONArray jsonArray = JSONArray.fromObject(htmlStr);
		 Object[] list = (Object[]) JSONArray.toArray(jsonArray);

		// Map<String,Object> map1 = (Map<String,Object>)JSON.parse(jsonStr); 
	      
		 for(int i=0;i<jsonArray.size();i++){
			String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "costStatisticsTitle");
			String[] titles = title.split(",",-1);
			Map<Integer, Object> excel = new HashMap<Integer, Object>();

			Map map = parseJSON2Map(jsonArray.get(i).toString());
			 
			for(int j=0;j<titles.length;j++){
				excel.put(j,map.get(titles[j]));		
			}
			listMap.add(excel);
		 }
		
		 return  listMap;

	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public List<Map<Integer, Object>> getDownloadList(String jsonStr) throws Exception {

		 List<Map<Integer, Object>> listMap = new ArrayList<Map<Integer, Object>>();
		 
		 dataModel.setQueryName("getPurchaseStockInById");
		 baseQuery = new BaseQuery(request, dataModel);

		 String htmlStr = reqModel.getJsonData();
		 
		 //String htmlStr = URLDecoder.decode(request.getParameter("html"), "UTF-8");
		 JSONArray jsonArray = JSONArray.fromObject(htmlStr);
		 Object[] list = (Object[]) JSONArray.toArray(jsonArray);

		// Map<String,Object> map1 = (Map<String,Object>)JSON.parse(jsonStr); 
	      
		 for(int i=0;i<jsonArray.size();i++){
			String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "costAccoutingTitle");
			String[] titles = title.split(",",-1);
			Map<Integer, Object> excel = new HashMap<Integer, Object>();

			Map map = parseJSON2Map(jsonArray.get(i).toString());
			 
			for(int j=0;j<titles.length;j++){
				excel.put(j,map.get(titles[j]));		
			}
			listMap.add(excel);
		 }
		
		 return  listMap;

	}
	
	@SuppressWarnings("unchecked")
	public void updateOrderDetailForCostConcel() throws Exception{
		String YSId = request.getParameter("YSId");
		
		String where = "YSId ='" + YSId + "' AND deleteFlag='0' ";
		List<B_OrderDetailData> list = new B_OrderDetailDao().Find(where);
		
		if(list.size() > 0 ){
			B_OrderDetailData dt = list.get(0);			
			
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"不参与财务核算",userInfo);
			copyProperties(dt,commData);
			dt.setReceipt("F");//不参与核算
			
			new B_OrderDetailDao().Store(dt);
		}
		
	}
	public void costAccountingInit() throws Exception{

		model.addAttribute("team",util.getListOptionAddDefault(DicUtil.BUSINESSTEAM, ""));
		model.addAttribute("year",util.getListOption(DicUtil.BUSINESSYEAR, ""));
		
	}
	
	public void monthlyStatisticsInit() throws Exception{

		model.addAttribute("team",util.getListOptionAddDefault(DicUtil.BUSINESSTEAM, ""));
		model.addAttribute("year",util.getListOption(DicUtil.BUSINESSYEAR, ""));
		
	}
	
	public void reportForDaybookInit() throws Exception{

		model.addAttribute("year",util.getListOption(DicUtil.BUSINESSYEAR, ""));
		
	}
	
	public HashMap<String, Object> monthlyStatistics() throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		
		
		dataModel.setQueryName("costAccountingGroupByYear");//月度统计
		baseQuery = new BaseQuery(request, dataModel);

		String yearFlag = request.getParameter("yearFlag");
		String team = request.getParameter("team");
		if(("ALL").equals(team)){//全部
			userDefinedSearchCase.put("team", "");
		}else{
			userDefinedSearchCase.put("team", team);
		}
		userDefinedSearchCase.put("yearFlag", yearFlag);

		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();
				
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
	public void reportForDaybookByMaterialIdInit() throws Exception{

		String materialId = request.getParameter("materialId");
		String strMonthly = request.getParameter("monthly");
		if(isNullOrEmpty(strMonthly)){
			strMonthly = CalendarUtil.fmtYmdDate();
		}
		//FinanceMouthly monthly = new FinanceMouthly(strMonthly);
		//String start = monthly.getStartDate();
		//String end = monthly.getEndDate();
		
		String day = CalendarUtil.getDayOfYear();
		//String month = CalendarUtil.getMonthOfYear();
		
		//int imonth = Integer.parseInt(month);
		int iday = Integer.parseInt(day);
		if(iday > 25){
			//strMonthly = CalendarUtil.getLastDate();
		}else{
			strMonthly = CalendarUtil.getLastDate();			
		}
		
		String strMonth = strMonthly.substring(0, 7);
		

		dataModel.setQueryFileName("/business/order/financequerydefine");
		dataModel.setQueryName("getStorageValueFromMonth");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("materialId", materialId);
		userDefinedSearchCase.put("beginningDate", strMonth);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0 )
			model.addAttribute("storage",dataModel.getYsViewData().get(0));		

		model.addAttribute("year",util.getListOption(DicUtil.BUSINESSYEAR, ""));
	}

}
