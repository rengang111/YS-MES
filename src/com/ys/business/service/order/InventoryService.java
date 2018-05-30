package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;

@Service
public class InventoryService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_ArrivalDao dao;
	private ArrivalModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private  Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	HashMap<String, Object> modelMap = null;	

	public InventoryService(){
		
	}

	public InventoryService(Model model,
			HttpServletRequest request,
			ArrivalModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_ArrivalDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
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

		String[] keyArr = getSearchKey(Constants.FORM_RECEIVEINSPECTION,data,session);
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
		
		dataModel.setQueryName("getArrivaList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
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

	public void addInit() {

		//取得到货编号"yyMMdd01"
		getArriveId();
	
	}

	public HashMap<String, Object> getSemiProductStock() {

		String materialId = request.getParameter("materialId");
		String type = getMaterialType(materialId);
		return getProductStock(type);
	}
	
	public String getMaterialType(String materialId) {

		String rtn = "";
		if(materialId==null || materialId.equals(""))
			return rtn;
		
		String[] arry = materialId.split("[.]");
		boolean lenFlag = true;
		for(int i = 0;i<arry.length;i++){
			if(i == 0 || i == arry.length-1 || i == (arry.length-2))
				continue;
			
			if(lenFlag){
				rtn = "." + arry[i];
				lenFlag = false;
			}else{
				rtn = rtn + "." + arry[i];		
			}
		}
		
		return rtn;
	}
	
	public void insertArrival() {

		//String arrivalId = insertAndView();
		//getArrivaRecord(arrivalId);
	}
	

	
	private HashMap<String, Object> getProductStock(String type){

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		try {
			dataModel.setQueryName("getProductStock");
			userDefinedSearchCase.put("materialId", type);
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsFullData();

			modelMap.put("recordsTotal", dataModel.getRecordCount());
			modelMap.put("data", dataModel.getYsViewData());
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return modelMap;
	}
	
		
	public void doDelete(String recordId) throws Exception{
		
		B_ArrivalData data = new B_ArrivalData();	
															
		try {
			
			ts = new BaseTransaction();										
			ts.begin();									
			
			String removeData[] = recordId.split(",");									
			for (String key:removeData) {									
												
				data.setRecordid(key);							
				dao.Remove(data);	
				
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
		}
	}
	
	
	
	
	public void getArriveId() {

		try {
			String key = CalendarUtil.fmtYmdDate();
			dataModel.setQueryName("getMAXArrivalId");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("arriveDate", key);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
			baseQuery.getYsFullData();	
			
			String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
			
			model.addAttribute("arrivalId",
					BusinessService.getArriveRecordId(code));
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
	}
	
	public void contractAndStockIn() throws Exception{

		String materialId = request.getParameter("materialId");
			
		dataModel.setQueryName("contractAndStockInForInventory");
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		String having = " stockinQty < contractQty ";//未到合同
		sql = sql.replace("#", having);
		System.out.println("未到合同："+sql);
		baseQuery.getYsFullData(sql,having);

		model.addAttribute("material", dataModel.getYsViewData().get(0));
		model.addAttribute("contractList", dataModel.getYsViewData());
		
	}
	

	public void planAndStockOut() throws Exception{

		String materialId = request.getParameter("materialId");
			
		dataModel.setQueryName("planAndStockOutForInventory");
		userDefinedSearchCase.put("materialId", materialId);
		baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		String having = " stockoutQty < manufactureQuantity ";//未到合同
		sql = sql.replace("#", having);
		System.out.println("未到合同："+sql);
		baseQuery.getYsFullData(sql,having);

		model.addAttribute("material", dataModel.getYsViewData().get(0));
		model.addAttribute("contractList", dataModel.getYsViewData());
		
	}
	

}
