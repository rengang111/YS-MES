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

		String[] keyArr = getSearchKey(Constants.FORM_ARRIVAL,data,session);
		String key1 = keyArr[0];
		String key2 = keyArr[1];
		
		String actionType = request.getParameter("actionType");
		String deliveryDate = request.getParameter("deliveryDate");//合同交期
		
		if(notEmpty(key1) || notEmpty(key2)){
			actionType = "";//忽略其状态
		}
		if(("0").equals(actionType) ){
			dataModel.setQueryName("getContractListForNoArrival");//逾期未到货
		}else if (("1").equals(actionType)){
			dataModel.setQueryName("getContractListForNoArrival");//未到货
			deliveryDate = "";//清空时间条件
		}else{
			dataModel.setQueryName("getContractListForArrival");//已到货 或者 忽略 是否到货			
		}
		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("keyword1", key1);
		userDefinedSearchCase.put("keyword2", key2);
		userDefinedSearchCase.put("deliveryDate",deliveryDate);

		//包装到货,或者是料件到货
		if(("G").equals(makeType)){//包装
			userDefinedSearchCase.put("makeTypeG", "G");
			userDefinedSearchCase.put("makeTypeL", "");
			userDefinedSearchCase.put("supplierId11", "");
			userDefinedSearchCase.put("supplierId12", "");
			userDefinedSearchCase.put("supplierId21", Constants.SUPPLIER_YZ);
			userDefinedSearchCase.put("supplierId22", Constants.SUPPLIER_YS);
		}else if(("L").equals(makeType)){//料件
			userDefinedSearchCase.put("makeTypeG", "");
			userDefinedSearchCase.put("makeTypeL", "G");
			userDefinedSearchCase.put("supplierId11", "");
			userDefinedSearchCase.put("supplierId12", "");
			userDefinedSearchCase.put("supplierId21", Constants.SUPPLIER_YZ);
			userDefinedSearchCase.put("supplierId22", Constants.SUPPLIER_YS);
		}else{//自制件
			userDefinedSearchCase.put("makeTypeG", "");
			userDefinedSearchCase.put("makeTypeL", "");	
			userDefinedSearchCase.put("supplierId11", Constants.SUPPLIER_YZ);
			userDefinedSearchCase.put("supplierId12", Constants.SUPPLIER_YS);
			userDefinedSearchCase.put("supplierId21", "");
			userDefinedSearchCase.put("supplierId22", "");			
		}
		
		String userId = request.getParameter("userId");
		if(("999").equals(userId)){
			userDefinedSearchCase.put("userId", "");//999:查询全员
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
			getContractForArrivalById(contractId);
		}else{
			getContractDetail(contractId);
			
		}
		
	
		return rtnFlag;
	}
	
	public void addAgainInit() throws Exception {

		//取得该合同编号下的物料信息
		String contractId = request.getParameter("contractId");
		
		getContractForArrivalById(contractId);
	
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
			String makeType = request.getParameter("makeType");
			
			contractId = reqData.getContractid();

			//取得到货编号"yyMMdd01"
			String arrivalId = reqData.getArrivalid();
			if(isNullOrEmpty(arrivalId)){
				arrivalId = getNewArriveId();//重新取得到货编号
			}else{
				deleteArrivalById(arrivalId);//删除旧数据
			}
						
			for(B_ArrivalData data:reqDataList ){
				String q = data.getQuantity();
				if(q == null || q.equals("") || q.equals("0"))
					continue;
				
				//入库类别
				String stockinType = "";
				if(("Y").equals(makeType)){
					
					stockinType = Constants.STOCKINTYPE_22;//自制件到货
				}else if(("G").equals(makeType)){
					
					stockinType = Constants.STOCKINTYPE_24;//包装到货
				}else if(("L").equals(makeType)){
					//采购件到货
					String mateId = data.getMaterialid();
					if(("A").equals(mateId.substring(0, 1))){
						
						stockinType = Constants.STOCKINTYPE_21;//原材料到货
					}else{						
						stockinType = Constants.STOCKINTYPE_23;//装配件到货
					}					
				}
				data.setStockintype(stockinType);//入库类别
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
			
		//String where = " arrivalId='"+data.getArrivalid() + "' AND deleteFlag='0' ";
		//List<B_ArrivalData> list = dao.Find(where);
		//if(list.size() > 0){
			//收货编号存在的情况，重新设置ID
		//	String arrivalId = getNewArriveId();//重新取得到货编号
		//	data.setArrivalid(arrivalId);			
		//}

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
	@SuppressWarnings("unchecked")
	private void deleteArrivalById(String arrivalId) throws Exception {

		String where = " arrivalId = '"+arrivalId+
				"' AND deleteFlag='0' ";
		
		List<B_ArrivalData> list = dao.Find(where);
		if(list.size() > 0){
			
			for(B_ArrivalData data:list){

				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"ArrivalDelete",userInfo);
				copyProperties(data,commData);
				dao.Store(data);
				
				//恢复合同里的到货数量
				float quantity = stringToFloat(data.getQuantity());
				quantity = (-1) * quantity;

				updateContractArraival(
						data.getContractid(),
						data.getMaterialid(),
						String.valueOf(quantity));
			}
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
		try {
			
			ts = new BaseTransaction();										
			ts.begin();									
			
			String contractId = request.getParameter("contractId");
			//String materialId = request.getParameter("materialId");
			
			String where = " recordId = '"+recordId+
					"' AND status='" + Constants.ARRIVAL_STS_1 +"' ";
			
			List list = dao.Find(where);
			if(list.size() > 0){
				data = (B_ArrivalData) list.get(0);
				commData = commFiledEdit(Constants.ACCESSTYPE_DEL,
						"ArrivalDelete",userInfo);

				copyProperties(data,commData);
				dao.Store(data);
				
				//恢复合同里的到货数量
				float quantity = stringToFloat(data.getQuantity());
				quantity = (-1) * quantity;

				updateContractArraival(
						contractId,
						data.getMaterialid(),
						String.valueOf(quantity));
			}		
			
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			e.printStackTrace();
		}
	}
	
	
	
	
	public String getNewArriveId() throws Exception {

		String key = CalendarUtil.getCurrentTimeMillis();
		/*
		dataModel.setQueryName("getMAXArrivalId");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("arriveDate", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);			
		baseQuery.getYsFullData();	
		
		String code = dataModel.getYsViewData().get(0).get("MaxSubId");		
		return BusinessService.getArriveRecordId(code);		
		*/
		
		return key;
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
	
	private void getContractForArrivalById(String contractId) throws Exception {
		 
		dataModel.setQueryName("contractForArrivalById");		
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
		dataModel.setQueryName("getContractListForNoArrival");		
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
	

	public HashMap<String, Object> getArrivalByYsid( ) throws Exception {
		String YSId = request.getParameter("YSId");
		dataModel.setQueryName("getArrivaListByContractId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("YSId", YSId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		modelMap.put("recordCount", dataModel.getRecordCount());
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		
	}
	
	public void doEdit() throws Exception{
		
		String arrivalId = request.getParameter("arrivalId");
		
		getContractByArrivalId(arrivalId);		
		
	}
	
	private void getContractByArrivalId(String arrivalId) throws Exception {
		
		dataModel.setQueryName("getArrivaListByContractId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("arrivalId", arrivalId);		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			model.addAttribute("contract",dataModel.getYsViewData().get(0));
			model.addAttribute("material",dataModel.getYsViewData());
			model.addAttribute("arrivalId",arrivalId);
		}
		
	}
	
	public void contractArrivalSearchInit() throws Exception{
		
		dataModel.setQueryName("getPuchaserByMaterialId");		
		baseQuery = new BaseQuery(request, dataModel);		
		userDefinedSearchCase.put("dicTypeId", "采购人员");		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsFullData();

		if(dataModel.getRecordCount() >0){
			ArrayList<HashMap<String, String>> list = dataModel.getYsViewData();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("rownum", String.valueOf(list.size()+1));
			map.put("dicName", "ALL");
			map.put("dicId", "999");
			map.put("SortNo", "999");
			list.add(map);
			model.addAttribute("purchaser",list);
			model.addAttribute("defUser",map);
		
		}
	}

}
