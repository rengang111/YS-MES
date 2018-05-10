package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.business.action.model.order.QuotationModel;
import com.ys.business.action.model.order.StockInApplyModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_BomPlanDao;
import com.ys.business.db.dao.B_QuotationDao;
import com.ys.business.db.dao.B_QuotationDetailDao;
import com.ys.business.db.dao.B_StockInApplyDao;
import com.ys.business.db.dao.B_StockInApplyDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_BomDetailData;
import com.ys.business.db.data.B_BomPlanData;
import com.ys.business.db.data.B_QuotationData;
import com.ys.business.db.data.B_QuotationDetailData;
import com.ys.business.db.data.B_StockInApplyData;
import com.ys.business.db.data.B_StockInApplyDetailData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;

@Service
public class StockInApplyService extends CommonService {

	DicUtil util = new DicUtil();

	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	
	

	private StockInApplyModel reqModel;
	private UserInfo userInfo;
	private BaseModel dataModel;
	private Model model;
	private HashMap<String, String> userDefinedSearchCase;
	private BaseQuery baseQuery;
	ArrayList<HashMap<String, String>> modelMap = null;	

	public StockInApplyService(){
		
	}

	public StockInApplyService(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			StockInApplyModel reqModel,
			UserInfo userInfo){
		
		this.model = model;
		this.reqModel = reqModel;
		this.dataModel = new BaseModel();
		this.request = request;
		this.response = response;
		this.session = session;
		this.userInfo = userInfo;
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/inventoryquerydefine");
		
	}

	
	public HashMap<String, Object> doSearch(String data) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		
		data = URLDecoder.decode(data, "UTF-8");
		
		String[] keyArr = getSearchKey(Constants.FORM_STOCKINAPPLY,data,session);
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
		
		dataModel.setQueryName("stockInApplyList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if((key1 !=null && !("").equals(key1)) || 
				(key2 !=null && !("").equals(key2))){
			userDefinedSearchCase.put("status", "");
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

	
	
	public String getArriveId() throws Exception {

		String key = CalendarUtil.fmtYmdDate();
		dataModel.setQueryName("getMAXArrivalId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("arriveDate", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
		return BusinessService.getArriveRecordId(code);			
	}

	//
	public HashMap<String, Object> getBaseBomDetail(
			String bomId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getBaseBomDetailByBomId");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("material",dataModel.getYsViewData().get(0));
			model.addAttribute("materialDetail",dataModel.getYsViewData());
			//quotData.setRecordid(dataModel.getYsViewData().get(0).get("recordId"));	
			
		}	
				
		return HashMap;
	}
	
	//
	public HashMap<String, Object> getQuotationDetail(
			String bomId) throws Exception {

		HashMap<String, Object> HashMap = new HashMap<String, Object>();
		dataModel = new BaseModel();		
		dataModel.setQueryFileName("/business/order/bomquerydefine");
		dataModel.setQueryName("getQuotationDetail");
		
		baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("bomId", bomId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		
		baseQuery.getYsFullData();
		
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("material",dataModel.getYsViewData().get(0));
			model.addAttribute("materialDetail",dataModel.getYsViewData());
			//quotData.setRecordid(dataModel.getYsViewData().get(0).get("recordId"));	
			
		}	
				
		return HashMap;
	}
	

	private String insert() throws Exception  {

		String applyId = "";
		ts = new BaseTransaction();

		try {
			
			ts.begin();
					
			B_ArrivalData reqData = reqModel.getStockinApply();
			String remarks = reqData.getRemarks();
			
			applyId = reqData.getArrivalid();
			if( isNullOrEmpty(applyId) ){
				applyId = getArriveId();//申请编号
			}else{
				deleteArrivalById(applyId);
			}			
			
			List<B_ArrivalData> reqDataList = reqModel.getApplyDetailList();
			
			for(B_ArrivalData data:reqDataList ){
				
				String materId = data.getMaterialid();
				String quantity = data.getQuantity();
				//过滤空行或者被删除的数据
				if(notEmpty(materId) && notEmpty(quantity)){					
					data.setArrivalid(applyId);
					data.setRemarks(remarks);
					data.setStockintype(Constants.STOCKINTYPE_1);//入库类别：直接入库
					insertApplyDetail(data);
				}
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			e.printStackTrace();
		}
		
		return applyId;
		
	}	

	//删除待报检的记录
	@SuppressWarnings("unchecked")
	private void deleteArrivalById(String arrivalId) throws Exception {

		String where = " arrivalId = '"+arrivalId+
				"' AND deleteFlag='0' ";
		
		List<B_ArrivalData> list = new B_ArrivalDao().Find(where);
		if(list.size() > 0){
			
			for(B_ArrivalData data:list){

				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"ArrivalDelete",userInfo);
				copyProperties(data,commData);
				new B_ArrivalDao().Store(data);				
				
			}
		}		
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
	
	
	private void insertApplyDetail(
			B_ArrivalData apply) throws Exception{
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"StockInApplyDetailInsert",userInfo);
		copyProperties(apply,commData);

		guid = BaseDAO.getGuId();
		apply.setRecordid(guid);
		apply.setStatus(Constants.ARRIVAL_STS_1);//待报检
		apply.setArrivaltype(Constants.ARRIVAL_TYPE_2);//直接入库
		apply.setUserid(userInfo.getUserId());
		apply.setArrivedate(CalendarUtil.fmtYmdDate());
		apply.setRemarks(replaceTextArea(apply.getRemarks()));//换行符转换
		
		new B_ArrivalDao().Create(apply);

	}	

	private void updateStockInApply(B_ArrivalData apply) 
			throws Exception{
		
		//取得更新前数据
		B_StockInApplyData db = null;
		try{
			db = new B_StockInApplyDao(apply).beanData;
		}catch(Exception e){
			//
		}
		if(db == null){
			getArriveId();
		}else{
			//处理共通信息
			copyProperties(db,apply);
			commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
					"StockInApplyUpdate",userInfo);
			copyProperties(db,commData);
			db.setRequestuserid(userInfo.getUserId());
			db.setStockinstatus("020");//待入库
			db.setRemarks(replaceTextArea(db.getRemarks()));
			
			new B_StockInApplyDao().Store(db);
		}
		
	}
	
	public void getStockInApplyDetail(String applyId) throws Exception {

		dataModel.setQueryName("getArrivaListByArrivalId");		
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("arrivalId", applyId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();	
			
		if(dataModel.getRecordCount() > 0){
			model.addAttribute("apply",dataModel.getYsViewData().get(0));//
			model.addAttribute("applyDetail",dataModel.getYsViewData());			
		}		
	}
	
	public HashMap<String, Object> showBaseBomDetail() throws Exception {

		String materialId = request.getParameter("materialId");
		String bomId = BusinessService.getBaseBomId(materialId)[1];
		
		return getBaseBomDetail(bomId);	
	}
	
	public void stockinApplyAddInit(){
		//nothing
	}
	
	public void stockInApplyInsert() throws Exception{
		
		String applyId = insert();
		getStockInApplyDetail(applyId);		
	}
	
	public void showStockinApply() throws Exception{
		
		String applyId = request.getParameter("applyId");
		
		getStockInApplyDetail(applyId);		
	}
	
	public void stockInApplyEdit() throws Exception{
		
		String applyId = request.getParameter("applyId");
		
		getStockInApplyDetail(applyId);		
	}
	
	public void stockInApplyUpdate() throws Exception{
		
		String applyId = insert();
		
		getStockInApplyDetail(applyId);		
	}
}

