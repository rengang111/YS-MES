package com.ys.business.service.order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import com.ys.business.action.model.order.FinanceReportModel;
import com.ys.business.db.dao.B_InventoryMonthlyReportDao;
import com.ys.business.db.dao.B_StockOutDao;
import com.ys.business.db.data.B_InventoryMonthlyReportData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;

import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.ExcelUtil;
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
	
	private B_StockOutDao dao;
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
	
	public HashMap<String, Object> reportForDaybookSearch( String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		//String[] keyArr = getSearchKey(Constants.FORM_PAYMENTREQUEST,data,session);
		String strMonthly = getJsonData(data, "monthly");
		FinanceMouthly monthly = new FinanceMouthly(strMonthly);
		

		
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
	
	@SuppressWarnings("unchecked")
	private int checkInventoryMonthlyReport(
			String monthly) throws Exception{
		
		String astr_Where = " monthly ='"+ monthly +"' And deleteFlag='0' ";
		List<B_InventoryMonthlyReportData> list =
				new B_InventoryMonthlyReportDao().Find(astr_Where);
		
		return list.size();
	}
	
	public List<Map<Integer, Object>> getDaybookList() throws Exception {

		dataModel.setQueryFileName("/business/material/materialquerydefine");
		
		String searchType = request.getParameter("searchType");
		if(("1").equals(searchType)){
			//库存为负数
			dataModel.setQueryName("materialinventory_search");	
		}else if(("2").equals(searchType)){
			//库存 ≠ 总到货－总领料
			dataModel.setQueryName("materialinventory_search2");	
		}else{
			//全部
			dataModel.setQueryName("materialinventoryForNormal_search");	
		}
		baseQuery = new BaseQuery(request, dataModel);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		List<Map<Integer, Object>> listMap = new ArrayList<Map<Integer, Object>>();
		ArrayList<HashMap<String, String>>  hashMap = baseQuery.getYsFullData();	
		
		for(int i=0;i<hashMap.size();i++){
			String title = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "inventoryTitle");
			String[] titles = title.split(",",-1);
			Map<Integer, Object> excel = new HashMap<Integer, Object>();
			for(int j=0;j<titles.length;j++){
				excel.put(j,hashMap.get(i).get(titles[j]));		
			}
			listMap.add(excel);
		}
		
		return  listMap;

	}
	
	public void excelForInvertory() throws Exception{
		
		//设置响应头，控制浏览器下载该文件
				
		//baseBom数据取得
		List<Map<Integer, Object>>  datalist = getDaybookList( );		
		
		String fileName = "inventory_" + CalendarUtil.timeStempDate()+".xls";
		String dest = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP)
				+"/"+File.separator+fileName;
       
		String tempFilePath = session
				.getServletContext()
				.getRealPath(BusinessConstants.PATH_EXCELTEMPLATE)+File.separator+"inventory.xls";
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
		String head = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, "inventoryExcel");
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
	
	

}
