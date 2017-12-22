package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.dao.B_ReceiveInspectionDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_ReceiveInspectionData;
import com.ys.business.db.data.CommFieldsData;
import com.ys.business.service.common.BusinessService;
import com.ys.system.action.model.login.UserInfo;
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
public class ArrivalService extends CommonService {
 
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
	HttpSession session;	

	public ArrivalService(){
		
	}

	public ArrivalService(Model model,
			HttpServletRequest request,
			HttpSession session,
			ArrivalModel reqModel,
			UserInfo userInfo){
		
		this.dao = new B_ArrivalDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
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

		String[] keyArr = getSearchKey(Constants.FORM_ARRIVAL,data,session);
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
	

	public HashMap<String, Object> contractArrivalSearch(
			String makeType,String data) throws Exception {
		
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
	
		dataModel.setQueryName("getArrivaList");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		String[] keyArr = getSearchKey(Constants.FORM_ARRIVAL,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		if((key1 !=null && !("").equals(key1)) || 
				(key2 !=null && !("").equals(key2))){
			userDefinedSearchCase.put("accumulated1", "");
		}
		//包装到货,或者是料件到货
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

	public String addInit() throws Exception {

		String rtnFlag = "查看";
		//取得该合同编号下的物料信息
		String contractId = request.getParameter("contractId");
		
		boolean hash = checkContractDetail(contractId);
		
		if (hash) {
			rtnFlag = "新建";
		}
		
		getContractDetail(contractId);
	
		return rtnFlag;
	}

	public void showArrivalDetail() {

		String arrivalId = request.getParameter("arrivalId");
		getArrivaRecord(arrivalId);
	}
	
	public void insertAndViewArrival() throws Exception {

		String contractId = insert();
		getContractDetail(contractId);
	}
	
	private String insert(){
		
		String contractId = "";
		ts = new BaseTransaction();
				
		try {
			ts.begin();
			
			B_ArrivalData reqData = (B_ArrivalData)reqModel.getArrival();
			List<B_ArrivalData> reqDataList = reqModel.getArrivalList();
			
			//删除未报检数据
			contractId = reqData.getContractid();
			deleteArrivalById(contractId);

			//取得到货编号"yyMMdd01"
			String arrivalId = getArriveId();
			
			for(B_ArrivalData data:reqDataList ){
				String q = data.getQuantity();
				if(q == null || q.equals("") || q.equals("0"))
					continue;
				
				data.setArrivalid(arrivalId);
				data.setYsid(reqData.getYsid());
				data.setContractid(reqData.getContractid());
				data.setSupplierid(reqData.getSupplierid());
				data.setUserid(userInfo.getUserId());
				data.setArrivedate(reqData.getArrivedate());
				data.setStatus(Constants.ARRIVAL_STS_1);//待报检
				
				//到货登记
				insertArrival(data);
				
				//更新累计到货数量,合同状态
				updateContractArraival(
						contractId,
						data.getMaterialid(),
						data.getQuantity());
			
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
		
		return contractId;
	}
	

	private void insertArrival(
			B_ArrivalData data) throws Exception{
			
		commData = commFiledEdit(Constants.ACCESSTYPE_INS,
				"ArrivalInsert",userInfo);

		copyProperties(data,commData);

		String guid = BaseDAO.getGuId();
		data.setRecordid(guid);
		
		dao.Create(data);
	}
	
	
	@SuppressWarnings("unchecked")
	private void updateContractArraival(
			String contractId,
			String materialId,
			String arrival) throws Exception{
	

		//更新合同状态
		updateContractStatus(contractId,Constants.CONTRACT_STS_2);
		
					
		//更新到货数量
		B_PurchaseOrderDetailData data = new B_PurchaseOrderDetailData();
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();
		String where = "contractId ='"+contractId +
				"' AND materialId ='"+ materialId +
				"' AND deleteFlag='0' ";
		List<B_PurchaseOrderDetailData> list = 
				(List<B_PurchaseOrderDetailData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		//计算到货累计数量
		float iAcc = stringToFloat(data.getAccumulated());
		float iArr = stringToFloat(arrival);				
		float iNew = iArr + iAcc;		
		
		//更新DB
		data.setAccumulated(String.valueOf(iNew));
		data.setStatus(Constants.CONTRACT_PROCESS_1);//待报检
		
		updateContractDetailStatus(data);	
	}
	
	//删除待报检的记录
	private void deleteArrivalById(String contractId) {

		String where = " contractId = '"+contractId+
				"' AND status='" + Constants.ARRIVAL_STS_1 +"' ";
		try {
			dao.RemoveByWhere(where);
		} catch (Exception e) {
			// nothing
		}
		
	}
	
	private void getArrivaRecord(String arrivalId){
		
		try {
			dataModel.setQueryName("getArrivaRecord");
			userDefinedSearchCase.put("arrivalId", arrivalId);
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsFullData();

			model.addAttribute("arrival",dataModel.getYsViewData().get(0));
			//String userId = dataModel.getYsViewData().get(0).get("userId");
			
			model.addAttribute("arrivalList",dataModel.getYsViewData());
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
		
	public void doDelete(String recordId) throws Exception{
		
		B_ArrivalData data = new B_ArrivalData();	
		String key = recordId;											
		//try {
			
			//ts = new BaseTransaction();										
			//ts.begin();									
			
			//String removeData[] = recordId.split(",");									
			//for (String key:removeData) {									
												
				data.setRecordid(key);							
				dao.Remove(data);	
				
			//}
			
			//ts.commit();
		//}
		//catch(Exception e) {
		//	ts.rollback();
		//}
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
	
	public void getContractDetail(String contractId) throws Exception {
 
		dataModel.setQueryName("getContractById");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
		}
	}
	
	public boolean checkContractDetail(String contractId) throws Exception {

		boolean rtnFlag = false; 
		dataModel.setQueryName("checkContractExsitById");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			rtnFlag = true;
		}
		return rtnFlag;
	}
	public HashMap<String, Object> getArrivalHistory(
			String contractId) throws Exception {
		
		dataModel.setQueryName("getArrivaListByContractId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", contractId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
		
	}
	
	
	public void getContractByArrivalId() throws Exception {

		String contractId = request.getParameter("contractId");
		String arrivalId = request.getParameter("arrivalId");
		
		dataModel.setQueryName("getContractByArrivalId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("contractId", contractId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		String sql = baseQuery.getSql();
		sql = sql.replace("#", arrivalId);
		baseQuery.getYsFullData(sql);

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
			model.addAttribute("arrivalId",arrivalId);
		}
		
	}

}
