package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.BomModel;
import com.ys.business.db.dao.B_BomDao;
import com.ys.business.db.dao.B_BomDetailDao;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_OrderDetailDao;
import com.ys.business.db.dao.B_OrderExpenseDao;
import com.ys.business.db.dao.B_PriceSupplierDao;
import com.ys.business.db.dao.B_PriceSupplierHistoryDao;
import com.ys.business.db.data.B_BomData;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_OrderDetailData;
import com.ys.business.db.data.B_OrderExpenseData;
import com.ys.business.db.data.B_PriceSupplierData;
import com.ys.business.db.data.B_PriceSupplierHistoryData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class BomService extends BaseService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_BomPlanData bomPlanData;
	private B_BomDetailData bomDetailData;
	private B_BomPlanDao bomPlanDao;
	private B_BomDetailDao bomDetailDao;
	private BomModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	
	HttpSession session;
	public BomService(){
		
	}

	public BomService(Model model,
			HttpServletRequest request,
			BomModel reqModel,
			UserInfo userInfo){
		
		this.bomPlanDao = new B_BomPlanDao();
		this.bomPlanData = new B_BomPlanData();
		this.bomDetailDao = new B_BomDetailDao();
		this.bomDetailData = new B_BomDetailData();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		dataModel = new BaseModel();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		
	}
	
	public HashMap<String, Object> getBomList() throws Exception {
		
		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryName("getBomList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);	 

		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		modelMap.put("data", dataModel.getYsViewData());
		//model.addAttribute("bomPlan",  modelMap.get(0));
		//model.addAttribute("bomDetail", modelMap);
		
		return modelMap;
	}
	

	public HashMap<String, Object> getBomList(String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();

		dataModel.setQueryName("getBomList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
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

	public HashMap<String, Object> getBaseBomList(String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();

		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();

		dataModel.setQueryName("getBaseBomList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
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
	

	public Model getCopyBomList() throws Exception {
		
		dataModel = new BaseModel();
		
		//String key1 = getJsonData(data, "keyword1").toUpperCase();
		//String key2 = getJsonData(data, "keyword2").toUpperCase();
		
		String key1 = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getCopyBomList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("keyword1", key1);
		//userDefinedSearchCase.put("keyword2", key2);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		modelMap = baseQuery.getYsQueryData(0, 0);	 
				
		//modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("bomPlan",  modelMap.get(0));
		model.addAttribute("bomDetail", modelMap);
		
		return model;
	}
	
	
	public HashMap<String, Object> getBomApproveList(String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		dataModel = new BaseModel();

		data = URLDecoder.decode(data, "UTF-8");
		
		int iStart = 0;
		int iEnd =0;
		String sEcho = getJsonData(data, "sEcho");	
		String start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		String length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		String key1 = getJsonData(data, "keyword1").toUpperCase();
		String key2 = getJsonData(data, "keyword2").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomApproveList");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	 
		
		modelMap.put("sEcho", sEcho);		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("unitList",util.getListOption(DicUtil.MEASURESTYPE, ""));		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	public void getOrderDetail(String YSId) throws Exception {

		dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/order/orderquerydefine");
		dataModel.setQueryName("getOrderList");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keyword1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);		
		modelMap = baseQuery.getYsFullData();
		
		if (dataModel.getRecordCount() > 0 ){
			model.addAttribute("order",  modelMap.get(0));		
		}	
	}
	
	public HashMap<String, Object> getDocumentary() throws Exception{
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		String YSId = request.getParameter("YSId");
		this.dataModel.setQueryName("getDocumentary");		
		this.baseQuery = new BaseQuery(request, dataModel);
		
		this.userDefinedSearchCase.put("YSId", YSId);
		this.baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		this.baseQuery.getYsFullData();
		
		modelMap.put("recordsTotal", dataModel.getRecordCount());
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	public void getOrderExpense(String YSId)throws Exception{
		
	  this.dataModel.setQueryFileName("/business/order/bomquerydefine");
	  this.dataModel.setQueryName("getDocumentary");

	  this.baseQuery = new BaseQuery(this.request, this.dataModel);
	  this.userDefinedSearchCase.put("YSId", YSId);
	  this.baseQuery.setUserDefinedSearchCase(this.userDefinedSearchCase);
	  this.baseQuery.getYsFullData();

	  this.model.addAttribute("orderExpense", this.dataModel.getYsViewData());
	}


	public Model getBomDetail(String YSId) throws Exception {
	
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomDetailListByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("YSId", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsFullData();

		if(baseQuery.getRecodCount() > 0){
			model.addAttribute("bomPlan",  modelMap.get(0));
			model.addAttribute("bomDetail", modelMap);			
		}
				
		return model;
	}
	
	//accessFlg:true 新建; false:编辑
	public HashMap<String, Object> getBaseBomDetail(
			String bomId,
			boolean accessFlg) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBaseBomDetailByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			if(accessFlg){
				HashMap.put("recordsTotal", dataModel.getRecordCount());
				HashMap.put("data", modelMap);				
			}else{
				//编辑用
				model.addAttribute("material",dataModel.getYsViewData().get(0));
				model.addAttribute("materialDetail",dataModel.getYsViewData());
				bomPlanData.setRecordid(dataModel.getYsViewData().get(0).get("recordId"));		
			}
		}	
				
		return HashMap;
	}

	public HashMap<String, Object> getQuotationBomDetail(String bomId) throws Exception {
	
				
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
	

		dataModel.setQueryName("getQuotationBom");
		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("materialId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	
			
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());
		model.addAttribute("lastPrice",dataModel.getYsViewData().get(0));//最新报价
		
		return modelMap;
	}
	public void getBomInfo(String YSId) throws Exception {
	
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomDetailListByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keywords1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		modelMap = baseQuery.getYsQueryData(0, 0);
				
	}
	public String getOrderDetailByYSId(String YSId) throws Exception{
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getOrderDetailByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("keywords1", YSId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				
		modelMap = baseQuery.getYsQueryData(0, 0);

		model.addAttribute("order",  modelMap.get(0));
		
		String materialId = modelMap.get(0).get("productId");
		
		return materialId;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private B_BomPlanData BomPlanExistCheck(String bomId) throws Exception {
		
		List<B_BomPlanData> dbList = null;
		bomPlanData = null;
		try {
			String where = "bomId = '" + bomId
				+ "' AND  deleteFlag = '0' ";
			dbList = (List<B_BomPlanData>)bomPlanDao.Find(where);
			if ( dbList == null || dbList.size() > 0 )
				bomPlanData = dbList.get(0);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return bomPlanData;
	}
	
	@SuppressWarnings("unchecked")
	private B_BomPlanData BomPlanExistCheck2(String ysid)
			  throws Exception
			{
		List<B_BomPlanData> dbList = null;
		bomPlanData = null;
		try {
			String where = "ysid = '" + ysid + 
					"' AND  deleteFlag = '0' ";
	    dbList = (List<B_BomPlanData>)bomPlanDao.Find(where);
	    if ((dbList != null) && (dbList.size() > 0))
	      bomPlanData = ((B_BomPlanData)dbList.get(0));
	  }
	  catch (Exception e) {
	    System.out.println(e.getMessage());
	  }

	  return this.bomPlanData;
	}

	private String insertOrder1(String data, int index, String counter, String type)
			  throws Exception
	{
	  if ((data == null) || (data.trim() == "")) return null;

	  String ysid = "";
	  String materialId = "";
	  this.ts = new BaseTransaction();
	  try
	  {
	    this.ts.begin();

	    B_OrderExpenseData order = new B_OrderExpenseData();

	    ysid = getJsonData(data, "bomPlan.ysid");
	    materialId = getJsonData(data, "bomPlan.materialid");
	    String where = " ysid = '" + ysid + 
	      "' AND type = '" + type + 
	      "' AND status = " + "0";
	    deleteDocumentary(where);

	    int counterInt = 0;
	    if ((counter != null) && (counter.trim() != "")) {
	      counterInt = Integer.parseInt(counter);
	    }
	    for (int i = 0; i < counterInt; ++i) {

	    	String name = getJsonData(data, "documentaryLines" + index + "[" + i + "].costname");
	    	String contr = getJsonData(data, "documentaryLines" + index + "[" + i + "].contractid");
		    String cost = getJsonData(data, "documentaryLines" + index + "[" + i + "].cost");
		    String person = getJsonData(data, "documentaryLines" + index + "[" + i + "].person");
		    String date = getJsonData(data, "documentaryLines" + index + "[" + i + "].quotationdate");

		    if ((name == null) || ("".equals(name)))
		    	continue;
	      
		      order.setYsid(ysid);
		      order.setMaterialid(materialId);
		      order.setContractid(contr);
		      order.setCostname(name);
		      order.setCost(cost);
		      order.setPerson(person);
		      order.setQuotationdate(date);
		      order.setType(type);
		      order.setStatus("0");
		      insertDocumentary(order);
	    }

	    this.ts.commit();
	  }
	  catch (Exception e) {
	    this.ts.rollback();
	    this.reqModel.setEndInfoMap("-1", "err001", "");
	  }

	  return ysid;
	}


	private void insertDocumentary(B_OrderExpenseData detailData)
	  throws Exception
	{
	  B_OrderExpenseDao dao = new B_OrderExpenseDao();
	  this.commData = commFiledEdit(0, 
	    "DocumentaryInsert", this.userInfo);

	  copyProperties(detailData, this.commData);
	  this.guid = BaseDAO.getGuId();
	  detailData.setRecordid(this.guid);

	  dao.Create(detailData);
	}
		
	private void deleteDocumentary(String where){
	  B_OrderExpenseDao dao = new B_OrderExpenseDao();
	  try {
	    dao.RemoveByWhere(where);
	  }catch (Exception e){
		  System.out.println(e.getMessage());
	  }
	}
	private void updateOrderStatus(String data)
			  throws Exception {
		  B_OrderExpenseDao dao = new B_OrderExpenseDao();
		  B_OrderExpenseData dbData = new B_OrderExpenseData();
		  try
		  {
		    this.ts = new BaseTransaction();
		    this.ts.begin();
		    String[] removeData = data.split(",");
		    for (String key : removeData)
		    {
		      dbData = OrderExpenseExistCheck(key);

		      this.commData = commFiledEdit(1, 
		        "OrderExpenseUpdate", this.userInfo);

		      copyProperties(dbData, this.commData);
		      dbData.setStatus("1");
		      dao.Store(dbData);
		    }
		    this.ts.commit();
		  }
		  catch (Exception e) {
		    this.ts.rollback();
		  }
	}
	
	private B_OrderExpenseData OrderExpenseExistCheck(String recordid) throws Exception
	{
	  B_OrderExpenseData dt = new B_OrderExpenseData();
	  dt.setRecordid(recordid);

	  B_OrderExpenseDao dao = new B_OrderExpenseDao(dt);
	  return dao.beanData;
	}


	@SuppressWarnings("unchecked")
	private B_BomData BomExistCheck(String bomId) throws Exception {
		
		List<B_BomData> dbList = null;
		B_BomData bomData = null;
		try {
			String where = "bomId = '" + bomId
				+ "' AND  deleteFlag = '0' ";
			dbList = (List<B_BomData>)bomPlanDao.Find(where);
			
			if ( dbList == null || dbList.size() > 0 )
				bomData = dbList.get(0);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return bomData;
	}	
	
	/*
	 * 
	 */
	public HashMap<String, Object> getMaterialList() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;


		String key = request.getParameter("key").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getMaterialPriceList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}

	/*
	 * 
	 */
	public void getProductById(String materialId) throws Exception {

		dataModel.setQueryName("getProductById");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("materialId", materialId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		model.addAttribute("product", dataModel.getYsViewData().get(0));

	}
	
	/*
	 * 
	 */
	public HashMap<String, Object> getSupplierPriceList() throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;


		String key1 = request.getParameter("key1").toUpperCase();
		String key2 = request.getParameter("key2").toUpperCase();

		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getSupplierPriceList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("keywords1", key1);
		userDefinedSearchCase.put("keywords2", key2);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 

		modelMap.put("data", dataModel.getYsViewData());
		
		modelMap.put("retValue", "success");			
		
		return modelMap;
	}
	
	public void getMaterialbyProductModel() throws Exception {

		String key1 = request.getParameter("model");

		if(key1 != null && key1.trim().length() > 1){
			key1 = key1.toUpperCase();
		}else{
			return;
		}
		dataModel.setQueryName("getMaterialbyProductModel");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("shareModel", key1);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", key1);
		baseQuery.getYsQueryData(sql);	 		
		
		model.addAttribute("materialDetail", dataModel.getYsViewData());
		model.addAttribute("productMode",key1);
	}
	
	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	private String insert() throws Exception  {

		String bomid="";
		String YSId="";
		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_BomPlanData reqData = (B_BomPlanData)reqModel.getBomPlan();
			
			YSId = reqData.getYsid();
			
			//插入BOM基本信息
			insertBomPlan(reqData);
			
			//插入BOM详情		
			bomid = reqData.getBomid();
			List<B_BomDetailData> reqDataList = reqModel.getBomDetailLines();
			
			for(B_BomDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && 
					data.getMaterialid() != null && 
					data.getMaterialid() != ""){
					
					data.setBomid(bomid);
					insertBomDetail(data,false);	
					
					//供应商单价修改
					//String supplierId = data.getSupplierid();
					//String materialId = data.getMaterialid();
					//String price = data.getPrice();
					//updatePriceSupplier(materialId,supplierId,price);
				}	
			
			}
			//更新订单状态为待评审
			updateOrderByYSId(YSId);
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return bomid;
		
	}	

	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	private String insertBaseBom() throws Exception  {

		String bomid="";
		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_BomPlanData reqData = (B_BomPlanData)reqModel.getBomPlan();
			B_BomDetailData reqDtlDt = (B_BomDetailData)reqModel.getBomDetail();
			
			//插入BOM基本信息
			reqData.setBomtype(Constants.BOMTYPE_B);//BOM类别			
			updateBomPlan(reqData);//基础BOM
			
			//插入BOM详情
			//首先删除DB中的BOM详情
			bomid = reqData.getBomid();
			String where = " bomId = '"+bomid +"'";
			deleteBomDetail(where);
			
			List<B_BomDetailData> reqDataList = reqModel.getBomDetailLines();
			String subBomId1 = "";
			for(B_BomDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data.getMaterialid() != null && !("").equals(data.getMaterialid())){
					
					data.setBomid(bomid);
					data.setProductmodel(reqDtlDt.getProductmodel());
					String subBomId2 = data.getSubbomid();
					if(subBomId2 == null || subBomId2.equals("")){
						subBomId2 =subBomId1;//基础BOM从历史BOM查新建后,又新增行时,没有subbomid;
					}else{
						subBomId1 = data.getSubbomid();
					}
					data.setSubbomid(subBomId2);
					insertBomDetail(data,true);	

					updateBom(data);//BOM结构
					
					//供应商单价修改
					//String supplierId = data.getSupplierid();
					//String materialId = data.getMaterialid();
					//String price = data.getPrice();
					//updatePriceSupplier(materialId,supplierId,price);
				}	
			
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return bomid;
		
	}	
	/*
	 * 1.物料新增处理(一条数据)
	 * 2.子编码新增处理(N条数据)
	 */
	private String insertBidBom() throws Exception  {

		String bomid="";
		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_BomPlanData reqData = (B_BomPlanData)reqModel.getBomPlan();
			B_BomDetailData reqDtlDt = (B_BomDetailData)reqModel.getBomDetail();
			
			//插入BOM基本信息
			reqData.setBomtype(Constants.BOMTYPE_Q);//BOM类别
			updateBomPlan(reqData);
			
			//插入BOM详情
			//首先删除DB中的BOM详情
			bomid = reqData.getBomid();
			String where = " bomId = '"+bomid +"'";
			deleteBomDetail(where);
			
			List<B_BomDetailData> reqDataList = reqModel.getBomDetailLines();
			
			for(B_BomDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data.getMaterialid() != null && !("").equals(data.getMaterialid())){
					
					data.setBomid(bomid);
					data.setProductmodel(reqDtlDt.getProductmodel());
					insertBomDetail(data,false);	
					
					//供应商单价修改
					//String supplierId = data.getSupplierid();
					//String materialId = data.getMaterialid();
					//String price = data.getPrice();
					//updatePriceSupplier(materialId,supplierId,price);
				}	
			
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return bomid;
		
	}	
	/*
	 * BOM基本信息insert处理
	 */
	private void insertBomPlan(
			B_BomPlanData data) throws Exception{
		
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,"BaseBomInsert",userInfo);

		copyProperties(data,commData);

		guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		bomPlanDao.Create(data);	

	}	
	
	/*
	 * BOM详情插入处理
	 * 
	 * accessFlg:true 基础BOM做成
	 */
	private void insertBomDetail(
			B_BomDetailData detailData,
			boolean accessFlg) throws Exception{
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"BomDetailInsert",userInfo);

		copyProperties(detailData,commData);
		guid = BaseDAO.getGuId();
		detailData.setRecordid(guid);
		
		if(accessFlg){
			detailData.setPrice(null);//基础BOM不存单价
			detailData.setTotalprice(null);//基础BOM不存单价
		}
		
		bomDetailDao.Create(detailData);

	}	
	
	/*
	 * 1.订单基本信息更新处理(1条数据)
	 * 2.订单详情 新增/更新/删除 处理(N条数据)
	 */
	private String update() throws Exception  {
		
		String bomId = "";
		String YSId = "";
		ts = new BaseTransaction();
		
		try {
			
			ts.begin();
						
			B_BomPlanData reqBom = (B_BomPlanData)reqModel.getBomPlan();

			bomId = reqBom.getBomid();
			YSId = reqBom.getYsid();
			
			//订单基本信息 新增/更新 处理
			updateBomPlan(reqBom);
			
			//订单详情更新处理
			//首先删除DB中的BOM详情
			String where = " bomId = '"+bomId +"'";
			deleteBomDetail(where);
			
			//新增页面的BOM详情		
			List<B_BomDetailData> reqDataList = reqModel.getBomDetailLines();
			
			for(B_BomDetailData data:reqDataList ){
				
				//过滤空行或者被删除的数据
				if(data != null && data.getMaterialid() != null && 
					!data.getMaterialid().trim().equals("")){
					
					data.setBomid(bomId);
					insertBomDetail(data,false);					
				}			
			}
			//更新订单状态为待评审
			updateOrderByYSId(YSId);
			
			ts.commit();
			
		}
		catch(Exception e) {
			ts.rollback();
		}
				
		return YSId;
	}
	
	/*
	 * BOM基本信息更新处理
	 */
	private void updateBomPlan(B_BomPlanData reqBom) 
			throws Exception{

		String bomId = reqBom.getBomid();
		
		//取得更新前数据		
		bomPlanData = BomPlanExistCheck(bomId);					
		
		if(null != bomPlanData){

			//获取页面数据
			copyProperties(bomPlanData,reqBom);
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"BomPlanUpdate",userInfo);
			copyProperties(bomPlanData,commData);
			
			bomPlanDao.Store(bomPlanData);
			
		}else{
			
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"BomPlanInsert",userInfo);
			copyProperties(reqBom,commData);

			guid = BaseDAO.getGuId();
			reqBom.setRecordid(guid);
			
			bomPlanDao.Create(reqBom);	
		}
	}

	/*
	 * BOM结构信息更新处理
	 */
	private void updateBom(B_BomDetailData reqBom) 
			throws Exception{

		B_BomDao bomDao = new B_BomDao();
		String bomId = reqBom.getBomid();
		
		//取得更新前数据		
		B_BomData bomData = BomExistCheck(bomId);					
		
		if(null != bomData){

			//获取页面数据
			bomData.setMaterialid(reqBom.getMaterialid());
			bomData.setQuantity(reqBom.getQuantity());
			//处理共通信息
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"BomPlanUpdate",userInfo);
			copyProperties(bomData,commData);
			
			bomDao.Store(bomData);
			
		}else{
			bomData = new B_BomData();
			bomData.setBomid(reqBom.getBomid());
			bomData.setMaterialid(reqBom.getMaterialid());
			bomData.setQuantity(reqBom.getQuantity());
			commData = commFiledEdit(Constants.ACCESSTYPE_INS,
					"BomPlanInsert",userInfo);
			copyProperties(bomData,commData);

			guid = BaseDAO.getGuId();
			bomData.setRecordid(guid);
			
			bomDao.Create(bomData);	
		}
	}


	
	/*
	 * BOM详情删除处理
	 */
	private void deleteBomDetail(String where) {
		
		try{
			bomDetailDao.RemoveByWhere(where);
		}catch(Exception e){
			//nothing
		}		
	}

	/*
	 * 更新Order表的状态:待评审
	 */
	private void updateOrderByYSId(String YSId) throws Exception{
		
		//确认Order表是否存在
		
		B_OrderDetailDao dao = new B_OrderDetailDao();
		B_OrderDetailData dbData = getOrderByYSId(dao,YSId);
		
		if(dbData != null){				
			//update处理
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"OrderStatusUpdate",userInfo);
			
			copyProperties(dbData,commData);
			dbData.setStatus(Constants.ORDER_STS_1);

			dao.Store(dbData);				
		}
	}
	
	@SuppressWarnings("unchecked")
	public B_OrderDetailData getOrderByYSId(
			B_OrderDetailDao dao,
			String YSId ) throws Exception {
		
		List<B_OrderDetailData> dbList = null;
		
		String where = " YSId = '"+YSId +"'" + " AND deleteFlag = '0' ";
						
		dbList = (List<B_OrderDetailData>)dao.Find(where);
		
		if(dbList != null & dbList.size() > 0)
			return dbList.get(0);		
		
		return null;
	}

	/*
	 * 取得耀升编号的流水号
	 * flg:true 报价bom;false 订单bom
	 */
	private B_BomPlanData getBomIdByParentId(String parentId,boolean flg) 
			throws Exception {
		
		String bomId = null;
		String newParentId = null;
		dataModel = new BaseModel();
		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBomIdByParentId");
		
		baseQuery = new BaseQuery(request, dataModel);

		//查询条件   
		userDefinedSearchCase.put("keywords1", parentId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, 0);	 
		
		//取得已有的最大流水号
		int code =Integer.parseInt(dataModel.getYsViewData().get(0).get("MaxSubId"));
			
		if(flg){
			bomId = BusinessService.getBidBOMFormatId(parentId,code, true);
			
		}else{
			parentId = BusinessService.getOrderBOMParentId(parentId);
			bomId = BusinessService.getOrderBOMFormatId(parentId,code, true);
			
		}
		
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(String.valueOf( code+1 ));
		bomPlanData.setParentid(parentId);
		
		reqModel.setBomPlan(bomPlanData);

		return bomPlanData;
	}
	
	public void getProductDetail(String productId) throws Exception {

		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", productId);
		dataModel.setQueryFileName("/business/material/materialquerydefine");
		dataModel.setQueryName("getProductList");
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();	 
		
		model.addAttribute("product", dataModel.getYsViewData().get(0));

	}
	/*
	public Model copyBomPlan() throws Exception {		

		String bomId = request.getParameter("bomId");
		
		String materialId = "";  
		
		if(bomId != null && bomId.length() > 4){
			materialId = bomId.substring(0,bomId.length() - 4);
		}
		
		getOrderDetail(bomId);		
		getBomIdByParentId(materialId);
		
		return model;
		
	}
	*/
	public Model createBaseBom() throws Exception {

		String materialId = request.getParameter("materialId");	
		//取得该产品的新BOM编号
		String parentId = BusinessService.getBaseBomId(materialId)[0];
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		//新建
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(BusinessConstants.FORMAT_00);
		bomPlanData.setParentid(parentId);		
		reqModel.setBomPlan(bomPlanData);

		//取得产品信息
		getProductById(materialId);
		
		return model;
		
	}

	public void editBaseBom() throws Exception{

		String materialId = request.getParameter("materialId");	
		getBaseBom(materialId);
	}
	
	private void getBaseBom(String materialId) throws Exception {

		//取得该产品的新BOM编号
		String parentId = BusinessService.getBaseBomId(materialId)[0];
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		//新建
		bomPlanData = new B_BomPlanData();
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(BusinessConstants.FORMAT_00);
		bomPlanData.setParentid(parentId);	
		
		reqModel.setBomPlan(bomPlanData);

		getBaseBomDetail(bomId,false);
		//取得产品信息
		getProductById(materialId);
		
		model.addAttribute("accessFlg","1");//accessFlg:1 编辑
		
		//return model;
		
	}
	
	//报价BOM:客户报价
	public Model createQuotation() throws Exception {

		String materialId = request.getParameter("materialId");	
		String baseBomId = request.getParameter("bomId");	
		//取得最新的BOM单价		
		String parentId = request.getParameter("parentId");
		
		//if(accessFlg != null && accessFlg != ""){
			//编辑
			//取得产品信息
			getProductById(materialId);

			getBaseBomDetail(baseBomId,false);
		//}
		
		//取得最新的报价BOM编号
		getBomIdByParentId(parentId,true);
	
		//设置货币下拉框		
		reqModel.setCurrencyList(
				util.getListOption(DicUtil.CURRENCY, ""));
	
		
		return model;
		
	}

	//报价BOM:客户报价
	public Model editQuotation() throws Exception {
		String materialId = request.getParameter("materialId");	
		String bomId = request.getParameter("bomId");	
		String subId = request.getParameter("subId");	
		//取得最新的BOM单价		
		String accessFlg = Constants.ACCESSFLG_1;//新建/编辑标识
		
		//if(accessFlg != null && accessFlg != ""){
			//编辑
			//取得产品信息
			getProductById(materialId);

			getBaseBomDetail(bomId,false);
		//}
		
		//取得最新的报价BOM编号
		bomPlanData = new B_BomPlanData();
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(subId);
		bomPlanData.setMaterialid(materialId);
		reqModel.setBomPlan(bomPlanData);
		reqModel.setAccessFlg(accessFlg);
	
		//设置货币下拉框		
		reqModel.setCurrencyList(
				util.getListOption(DicUtil.CURRENCY, ""));
	
		
		return model;
		
	}
	public Model getProductModel() throws Exception {

		String materialId = request.getParameter("materialId");	
		//取得产品信息
		String parentId = BusinessService.getBaseBomId(materialId)[0];
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		//新建
		bomPlanData.setBomid(bomId);
		bomPlanData.setSubid(BusinessConstants.FORMAT_00);
		bomPlanData.setParentid(parentId);
		reqModel.setBomPlan(bomPlanData);
		
		getProductById(materialId);		

		getMaterialbyProductModel();
		
		return model;
		
	}
	
	public HashMap<String, Object> showBaseBomDetail() throws Exception {

		String materialId = request.getParameter("materialId");
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		return getBaseBomDetail(bomId,true);		
	}
	public HashMap<String, Object> showQuotationBomDetail() throws Exception {

		String materialId = request.getParameter("materialId");
		
		return getQuotationBomDetail(materialId);		
	}
		
	/*
	public Model createBomPlan() throws Exception {

		String YSId = request.getParameter("YSId");
		String materialId = request.getParameter("materialId");	

		//取得该产品的基础BOM编号
		String baseBomId = BusinessService.getBaseBomId(materialId)[1];
		//取得基础BOM信息
		getBaseBomDetail(baseBomId,false);
		
		//取得该产品的新BOM编号	
		getOrderBomIdByMaterialId(materialId);	
		
		//取得订单信息
		getOrderDetailByYSId(YSId);
		
		return model;
		
	}
*/
	public Model editBomPlan() throws Exception {

		String YSId = request.getParameter("YSId");
		
		//取得产品基本信息
		getOrderDetailByYSId(YSId);	
		
		//取得BOM的详细信息
		getBomDetail(YSId);
		
		//设置经管费率下拉框		
		reqModel.setManageRateList(
				util.getListOption(DicUtil.MANAGEMENTRATE, ""));
				
		return model;
		
	}
	
	public Model changeBomPlanAdd() throws Exception {

		String materialId = request.getParameter("materialId");
		String oldBomId = request.getParameter("bomId");

		//取得该产品的新BOM编号
		String parentId = BusinessService.getBaseBomId(materialId)[0];
		String newBomId = BusinessService.getBaseBomId(materialId)[1];
		
		//取得所选BOM的详细信息
		getBaseBomDetail(oldBomId,false);
		
		//返回给页面刚才选择的BOM编号
		model.addAttribute("selectedBomId",oldBomId);
		//新建
		bomPlanData.setBomid(newBomId);
		bomPlanData.setSubid(BusinessConstants.FORMAT_00);
		bomPlanData.setParentid(parentId);		
		reqModel.setBomPlan(bomPlanData);

		//取得产品信息
		getProductById(materialId);
	
		return model;
		
	}
	
	public HashMap<String, Object> multipleBomAdd() throws Exception {

		String bomId = request.getParameter("bomId");
		
		//取得所选BOM的详细信息
		return getBaseBomDetail(bomId,true);	
		
	}
	
	public Model changeBomPlanEdit() throws Exception {

		String YSId = request.getParameter("YSId");
		String orderYSId = request.getParameter("orderYSId");

		//取得本次订单的基本信息
		getOrderDetailByYSId(orderYSId);

		//取得本次BOM的基本信息
		getBomInfo(orderYSId);
		model.addAttribute("bomPlan",  modelMap.get(0));
		
		//取得所选BOM的详细信息
		getBomInfo(YSId);	
		model.addAttribute("bomDetail", modelMap);
		
		//设置经管费率下拉框		
		reqModel.setManageRateList(
				util.getListOption(DicUtil.MANAGEMENTRATE, ""));
		
		return model;
		
	}
	
	
	public Model getOrderDetail() throws Exception
	{
	  String YSId = this.request.getParameter("YSId");
	  String materialId = this.request.getParameter("materialId");
	  String parentId = null;
	 // String bomId = null;
	  /*
	  this.bomPlanData = BomPlanExistCheck2(YSId);
	  if (this.bomPlanData == null)
	  {
	    this.bomPlanData = new B_BomPlanData();
	    parentId = BusinessService.getOrderBOMParentId(materialId);
	    bomPlanData = getBomIdByParentId(parentId, false);
	  }
	  else
	  {
	    this.reqModel.setBomPlan(this.bomPlanData);
	  }
*/
	  getOrderDetail(YSId);

	  getOrderExpense(YSId);

	  return this.model;
	}


	public Model insertOrderBom(String data)
			  throws Exception{
		  //insertOrderBom();
		  String YSId = this.reqModel.getBomPlan().getYsid();
		  getOrderDetail(YSId);
	
		  return this.model;
	}

	public Model insertOrderCost1(String data) throws Exception
	{
	  String type = this.request.getParameter("type");
	  String counter1 = getJsonData(data, "counter1");
	  String counter2 = getJsonData(data, "counter2");
	  String counter3 = getJsonData(data, "counter3");
	  String counter4 = getJsonData(data, "counter4");

	  switch (type){
		  case "D":
			  insertOrder1(data, 1, counter1, type); break;
		  case "C":
			  insertOrder1(data, 2, counter2, type); break;
		  case "S":
			  insertOrder1(data, 3, counter3, type); break;
		  case "W":
			  insertOrder1(data, 4, counter4, type); break;
	  }

	  	return this.model;
	}

	public Model CheckOrderCost1(String data)
	  throws Exception
	{
	  updateOrderStatus(data);

	  return this.model;
	}
			
	public Model getOrderInfo()
	  throws Exception
	{
	  String YSId = this.request.getParameter("YSId");
	  getOrderDetail(YSId);

	  return this.model;
	}
		
	public Model insertAndView() throws Exception {

		String bomId = insert();
		getBomDetail(bomId);
		
		return model;
		
	}
	
	public Model insertBaseBomAndView() throws Exception {
	
		insertBaseBom();
		
		String materialId = reqModel.getBomPlan().getMaterialid();
		
		//取得产品信息
		//getProductDetail(materialId);
		
		getBaseBom(materialId);
		
		return model;
		
	}
	
	public Model insertQuotation() throws Exception {
		
		insertBidBom();
		String materialId = reqModel.getBomPlan().getMaterialid();
		
		//取得产品信息
		getProductDetail(materialId);
		
		return model;
		
	}
	
	public void deleteQuotation() throws Exception {
	
		bomPlanData = new B_BomPlanData();	
													
		try {	
												
			String recordid = request.getParameter("recordId");									
			bomPlanData.setRecordid(recordid);
			bomPlanDao.Remove(bomPlanData);		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	public Model updateAndView() throws Exception {

		String YSId = update();
		getBomDetail(YSId);
		
		return model;
		
	}
	
	public void updateBomPlan() {
		
		try {
			
			B_BomPlanData data = new B_BomPlanData();
			
			String costRate  = request.getParameter("costRate");
			String totalCost = request.getParameter("totalCost");
			String bomId  = request.getParameter("bomId");
			
			data.setBomid(bomId);
			data.setManagementcostrate(costRate);
			data.setTotalcost(totalCost);

			updateBomPlan(data);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			
		}
		
	}
}
