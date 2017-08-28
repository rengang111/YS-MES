package com.ys.business.service.order;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ys.business.action.model.order.ArrivalModel;
import com.ys.business.action.model.order.RequisitionModel;
import com.ys.business.db.dao.B_ArrivalDao;
import com.ys.business.db.dao.B_PurchaseOrderDao;
import com.ys.business.db.dao.B_PurchaseOrderDetailDao;
import com.ys.business.db.data.B_ArrivalData;
import com.ys.business.db.data.B_PurchaseOrderData;
import com.ys.business.db.data.B_PurchaseOrderDetailData;
import com.ys.business.db.data.B_RequisitionData;
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
public class RequisitionService extends CommonService {
 
	DicUtil util = new DicUtil();
	BaseTransaction ts;

	String guid ="";
	private CommFieldsData commData;
	
	private HttpServletRequest request;
	
	private B_ArrivalDao dao;
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
		
		this.dao = new B_ArrivalDao();
		this.model = model;
		this.reqModel = reqModel;
		this.request = request;
		this.userInfo = userInfo;
		this.session = session;
		dataModel = new BaseModel();
		modelMap = new HashMap<String, Object>();
		userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/order/purchasequerydefine");
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
	

	public HashMap<String, Object> contractArrivalSearch(
			String data) throws Exception {
		
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

	public void addInit() throws Exception {

		String YSId = request.getParameter("YSId");
		//取得领料单编号
		getRequisitionId(YSId);
		
		//物料需求表
		getPurchasePlan(YSId);
	
	}

	public void showArrivalDetail() {

		String arrivalId = request.getParameter("arrivalId");
		getArrivaRecord(arrivalId);
	}
	
	public void insertAndViewArrival() throws Exception {

		//String contractId = insertArrival();
		//getPurchasePlan(contractId);
	}
	
	private String insertArrival(){
		String contractId = "";
		ts = new BaseTransaction();
		
		/*
		try {
			ts.begin();
			
			B_RequisitionData reqData = (B_RequisitionData)reqModel.getRequisition();
			List<B_RequisitionData> reqDataList = reqModel.getRequisitionList();
			
			//删除旧数据
			String arrivalId = reqData.getArrivalid();
			deleteArrivalById(arrivalId);
			contractId = reqData.getContractid();
			
			for(B_ArrivalData data:reqDataList ){
				String q = data.getQuantity();
				if(q == null || q.equals("") || q.equals("0"))
					continue;
				
				commData = commFiledEdit(Constants.ACCESSTYPE_INS,
						"ArrivalInsert",userInfo);

				copyProperties(data,commData);

				String guid = BaseDAO.getGuId();
				data.setRecordid(guid);
				data.setArrivalid(arrivalId);
				data.setContractid(reqData.getContractid());
				data.setSupplierid(reqData.getSupplierid());
				data.setUserid(userInfo.getUserId());
				data.setArrivedate(reqData.getArrivedate());
				data.setStatus(Constants.ARRIVERECORD_0);//未报检
				
				dao.Create(data);	
				
				//更新累计到货数量
				updateContractArraival(contractId,data.getMaterialid(),data.getQuantity());
			
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
		*/
		return contractId;
	}
	
	@SuppressWarnings("unchecked")
	private void updateContractArraival(
			String contractId,
			String materialId,
			String arrival) throws Exception{
	

		//更新合同状态
		B_PurchaseOrderData d = new B_PurchaseOrderData();
		B_PurchaseOrderDao o = new B_PurchaseOrderDao();		
		String strwhere = "contractId ='"+contractId +
				"' AND deleteFlag='0' ";
		List<B_PurchaseOrderData> l = 
				(List<B_PurchaseOrderData>)o.Find(strwhere);
		
		String where = "contractId ='"+contractId +
				"' AND materialId ='"+ materialId +
				"' AND deleteFlag='0' ";
		if(l ==null || l.size() == 0){
			return ;
		}		
		d = l.get(0);
		d.setStatus(Constants.ORDER_STS_5);//合同执行中
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"ArrivalUpdate",userInfo);
		copyProperties(d,commData);		
		o.Store(d);
		
		//更新到货数量
		B_PurchaseOrderDetailData data = new B_PurchaseOrderDetailData();
		B_PurchaseOrderDetailDao dao = new B_PurchaseOrderDetailDao();
		List<B_PurchaseOrderDetailData> list = 
				(List<B_PurchaseOrderDetailData>)dao.Find(where);
		
		if(list ==null || list.size() == 0){
			return ;
		}

		data = list.get(0);
		
		//计算到货累计数量
		String accumulated = data.getAccumulated();
		float iAcc = 0;
		float iArr = 0;
		if(!(arrival ==null || ("").equals(arrival)))
			iArr = Float.parseFloat(arrival.replace(",", ""));

		if(!(accumulated ==null || ("").equals(accumulated)))
			iAcc = Float.parseFloat(accumulated.replace(",", ""));
		
		float iNew = iArr + iAcc;
		
		data.setAccumulated(String.valueOf(iNew));
		
		//更新DB
		commData = commFiledEdit(Constants.ACCESSTYPE_UPD,
				"ArrivalUpdate",userInfo);
		copyProperties(data,commData);
		
		dao.Store(data);

		
	}
	
	
	private void deleteArrivalById(String arrivalId) {

		String where = " arrivalId = '"+arrivalId+"' AND deleteFlag='0' ";
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
	
	
	
	
	public void getRequisitionId(String parentId) {

		try {
			dataModel.setQueryName("getMAXRequisitionId");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("parentId", parentId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
			baseQuery.getYsFullData();	
			
			String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
			
			model.addAttribute("requisitionId",
					BusinessService.getRequisitionId(parentId,code));
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			reqModel.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
	}
	
	public void getPurchasePlan(String YSId) throws Exception {

		
		dataModel.setQueryName("getPurchasePlanByYSId");
		
		baseQuery = new BaseQuery(request, dataModel);
		
		userDefinedSearchCase.put("contractId", YSId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("order",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());			
		}
		
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
