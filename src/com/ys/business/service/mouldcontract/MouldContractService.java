
package com.ys.business.service.mouldcontract;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.mouldcontract.MouldContractModel;
import com.ys.business.db.dao.B_MouldAcceptanceDao;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.dao.B_MouldContractBaseInfoDao;
import com.ys.business.db.dao.B_MouldContractDetailDao;
import com.ys.business.db.dao.B_MouldContractRegulationDao;
import com.ys.business.db.dao.B_MouldDetailDao;
import com.ys.business.db.dao.B_MouldFactoryDao;
import com.ys.business.db.dao.B_MouldPayInfoDao;
import com.ys.business.db.dao.B_MouldPayListDao;
import com.ys.business.db.dao.B_OrganizationDao;
import com.ys.business.db.dao.B_SupplierDao;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldContractBaseInfoData;
import com.ys.business.db.data.B_MouldContractDetailData;
import com.ys.business.db.data.B_MouldContractRegulationData;
import com.ys.business.db.data.B_MouldDetailData;
import com.ys.business.db.data.B_MouldFactoryData;
import com.ys.business.db.data.B_MouldPayInfoData;
import com.ys.business.db.data.B_MouldPayListData;
import com.ys.business.db.data.B_OrganizationData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

@Service
public class MouldContractService extends BaseService {

	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		String key1 = "";
		String key2 = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key1 = getJsonData(data, "keyword1");
		key2 = getJsonData(data, "keyword2");
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
		dataModel.setQueryName("mouldcontractquerydefine_search");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
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

	public MouldContractModel getMouldContractBaseInfo(HttpServletRequest request, String key) throws Exception {
		MouldContractModel model = new MouldContractModel();
		B_MouldContractBaseInfoDao dao = new B_MouldContractBaseInfoDao();
		B_MouldContractBaseInfoData dbData = new B_MouldContractBaseInfoData();
		B_MouldAcceptanceDao acceptanceDao = new B_MouldAcceptanceDao();
		B_MouldAcceptanceData acceptanceData = new B_MouldAcceptanceData();
		B_MouldPayInfoDao payInfoDao = new B_MouldPayInfoDao();
		B_MouldPayInfoData payInfoData = new B_MouldPayInfoData();
		B_SupplierDao supplierDao = new B_SupplierDao();
		B_SupplierData supplierData = new B_SupplierData();
		
		if (key != null && !key.equals("")) {
			dbData.setId(key);
			dbData = (B_MouldContractBaseInfoData)dao.FindByPrimaryKey(dbData);
			model.setMouldContractBaseInfoData(dbData);
			
			CalendarUtil calendarUtil = new CalendarUtil(dbData.getContractdate());
			model.setContractDate(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			calendarUtil = new CalendarUtil(dbData.getDeliverdate());
			model.setDeliverDate(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			BaseQuery baseQuery = null;
			dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
			dataModel.setQueryName("mouldcontractquerydefine_getregulations");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("key", key);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			model.setRegulations(baseQuery.getYsQueryData(0,0));
			
			userDefinedSearchCase = new HashMap<String, String>();
			dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
			dataModel.setQueryName("getParentName");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("keywords1", dbData.getType());
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			ArrayList<HashMap<String, String>> tmpData = baseQuery.getYsQueryData(0,0);
			if (tmpData.size() > 0) {
				model.setTypeDesc(tmpData.get(0).get("name"));
				model.setMouldType(tmpData.get(0).get("id"));
			}
			
			supplierData.setRecordid(dbData.getSupplierid());
			supplierData = (B_SupplierData)supplierDao.FindByPrimaryKey(supplierData);
			model.setSupplierIdView(supplierData.getSupplierid());
			model.setSupplierName(supplierData.getSuppliername());
			
			//CalendarUtil calendarUtil = new CalendarUtil(dbData.getFinishtime());
			//dbData.setFinishtime(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			
			//model = getProductModelName(request, model, dbData.getProductmodelid());
			
			try {
				//acceptanceData.setMouldbaseid(dbData.getId());
				//acceptanceData = (B_MouldAcceptanceData)acceptanceDao.FindByPrimaryKey(acceptanceData);
				//calendarUtil = new CalendarUtil(acceptanceData.getAcceptancedate());
				//acceptanceData.setAcceptancedate(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			}
			catch(Exception e) {
				
			}
			try {
				//payInfoData.setMouldbaseid(dbData.getId());
				//payInfoData = (B_MouldPayInfoData)payInfoDao.FindByPrimaryKey(payInfoData);
			}
			catch(Exception e) {
				
			}
		}
		
		model.setBelongList(doOptionChange(DicUtil.MOULDBELONG, ""));
		/*
		model.setResultList(doOptionChange(DicUtil.CONFIRMRESULT, ""));
		model.setMouldFactoryList(doGetMouldFactoryList(request));
		model.setMouldContractBaseInfoData(dbData);
		model.setMouldAcceptanceData(acceptanceData);
		model.setMouldPayInfoData(payInfoData);
		*/
		model.setKeyBackup(key);
		
		model.setEndInfoMap("098", "0001", "");
		
		
		return model;
		
	}
	
	public MouldContractModel doAddInit(HttpServletRequest request) throws Exception {
		MouldContractModel model = new MouldContractModel();
		
		model.setBelongList(doOptionChange(DicUtil.MOULDBELONG, ""));
		/*
		model.setResultList(doOptionChange(DicUtil.CONFIRMRESULT, ""));
		model.setMouldFactoryList(doGetMouldFactoryList(request));
		model.setMouldContractBaseInfoData(dbData);
		model.setMouldAcceptanceData(acceptanceData);
		model.setMouldPayInfoData(payInfoData);
		*/
		model.setKeyBackup("");
		
		model.setEndInfoMap("098", "0001", "");
		
		
		return model;
		
	}
	
	public ArrayList<ListOption> doGetMouldFactoryList(HttpServletRequest request) throws Exception {
			
		ArrayList<ListOption> listOption = new ArrayList<ListOption>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	

		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");	
		dataModel.setQueryName("mouldcontractquerydefine_searchfactory");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(-1, -1);	
			
		listOption.add(new ListOption("", ""));
		for( HashMap<String, String> rowData:dataModel.getYsViewData()) {
			ListOption x = new ListOption(rowData.get("id"), rowData.get("shortName"));
			listOption.add(x);
		}
		
		return listOption;	
	}		

	public HashMap<String, Object> doSupplierSearch(HttpServletRequest request, String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = new BaseQuery(request, dataModel);

		//String key = request.getParameter("key").toUpperCase();	
		String id = request.getParameter("key");
		String type = request.getParameter("type");
		
		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");	
		dataModel.setQueryName("mouldcontractquerydefine_searchsupplierbytype");	
		//TODO
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("type", type);
		if (!id.equals("**")) {
			userDefinedSearchCase.put("id", id);
		}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			
		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
	
	public String doGetMouldContractId(HttpServletRequest request, String data) throws Exception {
		
		String contractYear = getJsonData(data, "contractYear");
		String type = getJsonData(data, "type");
	
		return getMouldContractId(request, contractYear, type);

	}
	
	public HashMap<String, Object> doGetMouldDetailList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String id = "";
		float sumPrice = 0;
		
		data = URLDecoder.decode(data, "UTF-8");

		id = getJsonData(data, "keyBackup");
		if (id.equals("")) {
			id = "-1";
		}
				
		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
		dataModel.setQueryName("mouldcontractquerydefine_searchmd");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("productModelId", getJsonData(data, "productModelId"));
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
		ArrayList<HashMap<String, String>> datas = dataModel.getYsViewData();
	
		for(HashMap<String, String>rowData:datas) {
			if (!rowData.get("selected").equals("0")) {
				String price = rowData.get("price");
				if (price != null && !price.equals("")) {
					sumPrice += Float.parseFloat(price);
				}
			}
		}
		
		HashMap<String, String> sumPriceRow = new HashMap<String, String>(); 
		
		sumPriceRow.put("rownum", "");
		sumPriceRow.put("id", "");
		sumPriceRow.put("type", "");
		sumPriceRow.put("no", "");
		sumPriceRow.put("name", "");
		sumPriceRow.put("size", "");
		sumPriceRow.put("materialQuality", "");
		sumPriceRow.put("unloadingNum", "");
		sumPriceRow.put("weight", "");
		sumPriceRow.put("price", String.valueOf(sumPrice));
		sumPriceRow.put("mouldFactory", "");
		datas.add(sumPriceRow);
		modelMap.put("data", datas);
		
		return modelMap;		

	}
	
	public HashMap<String, Object> doGetPayList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		float paid = 0;
		
		String mouldBaseId = "";		
		data = URLDecoder.decode(data, "UTF-8");

		mouldBaseId = getJsonData(data, "keyBackup");
		if (mouldBaseId.equals("")) {
			mouldBaseId = "-1";
		}
		
		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
		dataModel.setQueryName("mouldcontractquerydefine_searchpay");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("mouldBaseId", mouldBaseId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
		
		for(HashMap<String, String>rowData:dataModel.getYsViewData()) {
			String pay = rowData.get("pay");
			String confirm = rowData.get("confirm");
			if (pay != null && !pay.equals("") && confirm != null && confirm.equals("1")) {
				paid += Float.parseFloat(pay);
			}
		}
		modelMap.put("paid", String.valueOf(paid));
		return modelMap;

	}
	
	public HashMap<String, Object> doGetMouldBaseInfoList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		float paid = 0;
		
		B_MouldContractBaseInfoData baseInfoData = new B_MouldContractBaseInfoData();
		B_MouldContractBaseInfoDao baseInfoDao = new B_MouldContractBaseInfoDao();
		S_DICData dicData = new S_DICData(); 
		S_DICDao dicDao = new S_DICDao();
		String mouldContractBaseId = getJsonData(data, "mouldBaseId");
		baseInfoData.setId(mouldContractBaseId);
		baseInfoData = (B_MouldContractBaseInfoData)baseInfoDao.FindByPrimaryKey(baseInfoData);
		
		dicData.setDictypeid(DicUtil.PRODUCTMODEL);
		dicData.setDicid(baseInfoData.getProductmodelid());
		dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
		
		if (mouldContractBaseId.equals("")) {
			mouldContractBaseId = "-1";
		}
		/*
		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
		dataModel.setQueryName("mouldcontractquerydefine_getMouldBaseInfoList");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("mouldBaseId", dicData.getDicname());
		userDefinedSearchCase.put("mouldFactoryId", );
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<HashMap<String, String>> mouldBaseInfoList = baseQuery.getYsQueryData(0, -1);
		
		dataModel.setQueryName("mouldcontractquerydefine_getUsedMouldBaseInfoList");
		baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("mouldContractBaseId", mouldContractBaseId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<HashMap<String, String>> usedMouldBaseInfoList = baseQuery.getYsQueryData(0, -1);
		
		for(HashMap<String, String>usedRowData:usedMouldBaseInfoList) {
			for(HashMap<String, String>rowData:mouldBaseInfoList) {
				if (usedRowData.get("id").equals(rowData.get("id"))) {
					mouldBaseInfoList.remove(rowData);
				}
			}
		}
		
		modelMap.put("data", mouldBaseInfoList);
		*/
		return modelMap;

	}
	
	public ArrayList<ListOption> getMouldFactoryList(HttpServletRequest request, String data) {
		ArrayList<ListOption> factoryList = new ArrayList<ListOption>();
		
		try {
			String productModelId = getJsonData(data, "productModelId");
			
			if (!productModelId.equals("")) {
				ArrayList<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
				BaseModel dataModel = new BaseModel();
				dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
				dataModel.setQueryName("mouldcontractquerydefine_searchfactorybyproductmodel");
				BaseQuery baseQuery = new BaseQuery(request, dataModel);
				userDefinedSearchCase.put("productModelId", productModelId);
				baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				datas = baseQuery.getYsQueryData(-1, -1);
				for(HashMap<String, String>rowData:datas) {
					ListOption option = new ListOption(rowData.get("id"), rowData.get("shortName"));
					factoryList.add(option);
				}
	
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return factoryList;
	}
	
	public MouldContractModel doUpdateAcceptance(HttpServletRequest request, String data, String confirm, UserInfo userInfo) {
		MouldContractModel model = new MouldContractModel();
		B_MouldAcceptanceData dbData = new B_MouldAcceptanceData();											
		B_MouldAcceptanceDao dao = new B_MouldAcceptanceDao();											

		String key = getJsonData(data, "keyBackup");
		String guid = "";
		try {
			if (key != null && !key.equals("")) {
				dbData.setMouldbaseid(key);
				dbData = (B_MouldAcceptanceData)dao.FindByPrimaryKey(dbData);
			}
		}
		catch(Exception e) {
			key = "";
		}
		try {
													
			if (key == null || key.equals("")) {
				//guid = BaseDAO.getGuId();
				dbData.setMouldbaseid(getJsonData(data, "keyBackup"));
				dbData.setAcceptancedate(getJsonData(data, "acceptanceDate"));
				dbData.setResult(getJsonData(data, "result"));
				dbData.setMemo(getJsonData(data, "memo"));
				dbData.setConfirm(confirm);
				dbData = updateMouldAcceptanceModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				//key = guid;
			} else {
				dbData.setAcceptancedate(getJsonData(data, "acceptanceDate"));
				dbData.setResult(getJsonData(data, "result"));
				dbData.setMemo(getJsonData(data, "memo"));
				dbData.setConfirm(confirm);
				dbData = updateMouldAcceptanceModifyInfo(dbData, userInfo);
				dao.Store(dbData);
			}

			model.setEndInfoMap(NORMAL, "", key);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", key);
		}
		
		return model;
	}
	
	public MouldContractModel doConfirmAcceptance(HttpServletRequest request, String data, UserInfo userInfo) {
		MouldContractModel model = new MouldContractModel();
		B_MouldAcceptanceData dbData = new B_MouldAcceptanceData();											
		B_MouldAcceptanceDao dao = new B_MouldAcceptanceDao();											

		String key = getJsonData(data, "keyBackup");
		String guid = "";
		
		try {
			/*
			dbData.setMouldbaseid(key);
			dbData = (B_MouldAcceptanceData)dao.FindByPrimaryKey(dbData);
			dbData.setConfirm("1");
			dbData = updateMouldAcceptanceModifyInfo(dbData, userInfo);
			dao.Store(dbData);
			*/
			doUpdateAcceptance(request, data, "1", userInfo);
			model.setEndInfoMap(NORMAL, "", key);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", key);
		}
		
		return model;
	}
	
	public MouldContractModel doUpdatePayInit(HttpServletRequest request, String mouldBaseId, String id, String withhold) throws Exception {
		MouldContractModel model = new MouldContractModel();
		B_MouldPayListDao dao = new B_MouldPayListDao();
		B_MouldPayListData dbData = new B_MouldPayListData();
		
		if (id != null && !id.equals("")) {
			dbData.setId(id);
			dbData = (B_MouldPayListData)dao.FindByPrimaryKey(dbData);
			
			CalendarUtil calendarUtil = new CalendarUtil(dbData.getPaytime());
			dbData.setPaytime(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));

		}
				
		model.setMouldPayListData(dbData);
		
		model.setKeyBackup(id);
		model.setMouldBaseId(mouldBaseId);
		model.setWithhold(withhold);
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}	
	
	public MouldContractModel doUpdateRegulation(HttpServletRequest request, String data, UserInfo userInfo) {
		MouldContractModel model = new MouldContractModel();
		B_MouldContractRegulationData dbData = new B_MouldContractRegulationData();											
		B_MouldContractRegulationDao dao = new B_MouldContractRegulationDao();

		try {
			String keyBackup = getJsonData(data, "keyBackup");
			String mouldId = getJsonData(data, "mouldId");
			String name = getJsonData(data, "name");
			String money = getJsonData(data, "money");
			
			if (keyBackup != null && !keyBackup.equals("")) {
				dbData.setId(keyBackup);
				dbData = (B_MouldContractRegulationData)dao.FindByPrimaryKey(dbData);
				dbData.setName(name);
				dbData.setMoney(money);
				dbData = updateMouldContractRegulationModifyInfo(dbData, userInfo);
				dao.Store(dbData);
			} else {
				dbData.setId(BaseDAO.getGuId());
				dbData.setMouldcontractbaseid(mouldId);
				dbData.setName(name);
				dbData.setMoney(money);
				dbData = updateMouldContractRegulationModifyInfo(dbData, userInfo);
				dao.Create(dbData);
			}
	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}

		
		return model;
	}	
	
	public MouldContractModel doUpdateDetails(HttpServletRequest request, String data, UserInfo userInfo) {
		MouldContractModel model = new MouldContractModel();
		B_MouldContractDetailData dbData = new B_MouldContractDetailData();											
		B_MouldContractDetailDao dao = new B_MouldContractDetailDao();

		try {
			String keyBackup = getJsonData(data, "keyBackup");
			String mouldContractId = getJsonData(data, "mouldContractId");
			String mouldId = getJsonData(data, "mouldId");
			String mouldFactoryId = getJsonData(data, "mouldFactoryId");
			String num = getJsonData(data, "num");
			String totalPrice = getJsonData(data, "totalPrice");
			
			if (keyBackup != null && !keyBackup.equals("")) {
				dbData.setId(keyBackup);
				dbData = (B_MouldContractDetailData)dao.FindByPrimaryKey(dbData);
				dbData.setMouldid(mouldId);
				dbData.setMouldfactoryid(mouldFactoryId);
				dbData.setNumber(num);
				dbData.setTotalprice(totalPrice);
				dbData = updateMouldContractDetailModifyInfo(dbData, userInfo);
				dao.Store(dbData);
			} else {
				dbData.setId(BaseDAO.getGuId());
				dbData.setMouldcontractbaseid(mouldContractId);
				dbData.setMouldid(mouldId);
				dbData.setMouldfactoryid(mouldFactoryId);
				dbData.setNumber(num);
				dbData.setTotalprice(totalPrice);
				dbData = updateMouldContractDetailModifyInfo(dbData, userInfo);
				dao.Create(dbData);
			}
	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}		
	
	public MouldContractModel doUpdatePay(HttpServletRequest request, String data, String confirm, UserInfo userInfo) {
		MouldContractModel model = new MouldContractModel();
    	B_MouldPayInfoData dbInfoData = new B_MouldPayInfoData();											
    	B_MouldPayInfoDao daoInfo = new B_MouldPayInfoDao();
    	B_MouldPayListData dbData = new B_MouldPayListData();											
    	B_MouldPayListDao dao = new B_MouldPayListDao();											
		
		try {
																
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.updateMouldContractPayList(data, confirm, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public MouldContractModel doConfirmPay(HttpServletRequest request, String data, UserInfo userInfo) {
		MouldContractModel model = new MouldContractModel();
		B_MouldAcceptanceData dbData = new B_MouldAcceptanceData();											
		B_MouldAcceptanceDao dao = new B_MouldAcceptanceDao();											
		
		try {
			doUpdatePay(request, data, "1", userInfo);
			model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public String doCheckNo(HttpServletRequest request, String data) {
		String message = "";										
		B_MouldContractBaseInfoDao infoDao = new B_MouldContractBaseInfoDao();
		B_MouldContractBaseInfoData infoData = new B_MouldContractBaseInfoData();
		S_DICDao dicDao = new S_DICDao();
		S_DICData dicData = new S_DICData();
		
		try {
			String no = getJsonData(data, "no");
			
			String type = getJsonData(data, "type");
			dicData.setDicid(type);
			dicData.setDictypeid(DicUtil.MOULDTYPE);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			type = dicData.getDicdes();
			
			String mouldBaseId = getJsonData(data, "mouldBaseId");
			infoData.setId(mouldBaseId);
			infoData = (B_MouldContractBaseInfoData)infoDao.FindByPrimaryKey(infoData);
			//dicData.setDicid(infoData.getProductmodelid());
			//dicData.setDictypeid(DicUtil.PRODUCTMODEL);
			//dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			String productModelName = infoData.getProductmodelid();
			
			message = "err008";
			if (no.length() == (productModelName.length() + type.length() + 2)) {
				if (no.substring(0, no.length() - 2).equals(productModelName + type)) {
					message = "";
				}
			}
		}
		catch(Exception e) {
			message = "err001";
		}
		
		return message;
	}
	
	public MouldContractModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		MouldContractModel model = new MouldContractModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
			model = bean.executeMouldContractUpdate(request, data, userInfo);
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public MouldContractModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldContractModel model = new MouldContractModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeMouldContractDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}

	public MouldContractModel doDeleteRegulation(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldContractModel model = new MouldContractModel();
    	B_MouldContractRegulationData dbData = new B_MouldContractRegulationData();
    	B_MouldContractRegulationDao dao = new B_MouldContractRegulationDao();
		
		try {
			dbData.setId(data);
			dbData = (B_MouldContractRegulationData)dao.FindByPrimaryKey(dbData);
	        dbData = updateMouldContractRegulationModifyInfo(dbData, userInfo);
	        dbData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
	        dao.Store(dbData);
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}

	
	public MouldContractModel doDeleteDetail(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldContractModel model = new MouldContractModel();
    	B_MouldContractDetailData dbData = new B_MouldContractDetailData();
    	B_MouldContractDetailDao dao = new B_MouldContractDetailDao();
		
		try {
			dbData.setId(data);
			dbData = (B_MouldContractDetailData)dao.FindByPrimaryKey(dbData);
	        dbData = updateMouldContractDetailModifyInfo(dbData, userInfo);
	        dbData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
	        dao.Store(dbData);
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public MouldContractModel doDeletePay(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldContractModel model = new MouldContractModel();
    	
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeMouldContractPayListDelete(data, userInfo);					
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public ArrayList<ListOption> doOptionChange(String type, String parentCode) {
		DicUtil util = new DicUtil();
		ArrayList<ListOption> optionList = null;
		
		try {
			optionList = util.getListOption(type, parentCode);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return optionList;
	}
	
	public HashMap<String, Object> getProductModelList(HttpServletRequest request, String data) throws Exception {
	
			
			HashMap<String, Object> modelMap = new HashMap<String, Object>();
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			BaseQuery baseQuery = null;
			
			dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
			dataModel.setQueryName("mouldcontractquerydefine_searchproductmodel");
			
			baseQuery = new BaseQuery(request, dataModel);
			
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsQueryData(0,0);
			
			modelMap.put("data", dataModel.getYsViewData());
			
			return modelMap;
		}	

	
	public static B_MouldContractBaseInfoData updateMouldContractBaseInfoModifyInfo(B_MouldContractBaseInfoData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	public static B_MouldContractRegulationData updateMouldContractRegulationModifyInfo(B_MouldContractRegulationData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	public static B_MouldContractDetailData updateMouldContractDetailModifyInfo(B_MouldContractDetailData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	public static B_MouldAcceptanceData updateMouldAcceptanceModifyInfo(B_MouldAcceptanceData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	public static B_MouldPayInfoData updateMouldPayInfoModifyInfo(B_MouldPayInfoData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	public static B_MouldPayListData updateMouldPayListModifyInfo(B_MouldPayListData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			data.setDeptguid(userInfo.getDeptGuid());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}

	private MouldContractModel getProductModelName(HttpServletRequest request, MouldContractModel model, String productModelId) throws Exception {
		/*
		HashMap<String, Object> productModel = doProductModelIdSearch(request, "");
		ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>)productModel.get("data");
		for(HashMap<String, String> rowData:data) {
			if (rowData.get("id").equals(productModelId)) {
				model.setProductModelName(rowData.get("des"));
				model.setProductModelIdView(rowData.get("name"));
				break;
			}
		}
		*/
		return model;
	}
	
	public String getMouldContractId(HttpServletRequest request, String contractYear, String type) throws Exception {

		String contractId = "";
		
		if (!contractYear.equals("") && !type.equals("")) {
			BaseModel dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
			dataModel.setQueryName("mouldcontractquerydefine_getcontractserialno");
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			userDefinedSearchCase.put("contractIdBase", contractYear + type);
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsQueryData(0,0);
			String serialNo = String.format("%03d", Integer.parseInt(dataModel.getYsViewData().get(0).get("serialNo")));
/*
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("getParentName");
			userDefinedSearchCase = new HashMap<String, String>();
			userDefinedSearchCase.put("keywords1", type);
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsQueryData(0,0);
		
			contractId = contractYear + dataModel.getYsViewData().get(0).get("id") + serialNo;
*/
			contractId = contractYear + type + serialNo;
		}
		return contractId;
	}

	public HashMap<String, Object> doTypeSearch(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		String key = request.getParameter("key");	
			
		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");	
		dataModel.setQueryName("categorylist");	
		baseQuery = new BaseQuery(request, dataModel);	
		
		//TODO:
		//如果强制M开头就在这里做动作
		userDefinedSearchCase.put("keywords1", key);
		userDefinedSearchCase.put("keywords2", key);
		userDefinedSearchCase.put("keywords3", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
	
	public HashMap<String, Object> doFactorySearch(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		String key = request.getParameter("key");	
		String type = request.getParameter("type");
		String supplierId = request.getParameter("supplierId");
			
		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");	
		dataModel.setQueryName("factorysearch");	
		baseQuery = new BaseQuery(request, dataModel);	
		
		//TODO:
		//如果强制M开头就在这里做动作
		userDefinedSearchCase.put("key", key);
		userDefinedSearchCase.put("type", type);
		userDefinedSearchCase.put("supplierId", supplierId);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
	
	public HashMap<String, Object> getMouldContractDetailList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		String key = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key = getJsonData(data, "keyBackup");
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
		dataModel.setQueryName("mouldcontractquerydefine_getdetails");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("mouldContractBaseId", key);
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
	
	public HashMap<String, Object> getMouldContractRegulationList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		String key = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key = getJsonData(data, "keyBackup");
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/mouldcontract/mouldcontractquerydefine");
		dataModel.setQueryName("mouldcontractquerydefine_getregulations");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("key", key);
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
	
	public MouldContractModel doUpdateRegulationInit(HttpServletRequest request, MouldContractModel model) throws Exception {
		
		B_MouldContractRegulationDao dao = new B_MouldContractRegulationDao();
		B_MouldContractRegulationData data = new B_MouldContractRegulationData();
		String key = request.getParameter("key");
		if (key != null && !key.equals("")) {
			data.setId(key);
			data = (B_MouldContractRegulationData)dao.FindByPrimaryKey(data);
			model.setMouldContractRegulationData(data);
		}
		model.setKeyBackup(key);
		
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public MouldContractModel doUpdateDetailsInit(HttpServletRequest request, MouldContractModel model) throws Exception {
		B_MouldContractDetailDao dao = new B_MouldContractDetailDao();
		B_MouldContractDetailData data = new B_MouldContractDetailData();
		B_MouldBaseInfoDao infoDao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData infoData = new B_MouldBaseInfoData();
		B_MouldFactoryDao factoryDao = new B_MouldFactoryDao();
		B_MouldFactoryData factoryData = new B_MouldFactoryData();

		String key = request.getParameter("key");
		if (key != null && !key.equals("")) {
			data.setId(key);
			data = (B_MouldContractDetailData)dao.FindByPrimaryKey(data);
			model.setMouldContractDetailData(data);
			
			infoData.setId(data.getMouldid());
			infoData = (B_MouldBaseInfoData)infoDao.FindByPrimaryKey(infoData);
			model.setMouldBaseInfoData(infoData);
			
			factoryData.setId(data.getMouldfactoryid());
			factoryData = (B_MouldFactoryData)factoryDao.FindByPrimaryKey(factoryData);
			model.setMouldFactoryData(factoryData);
		}
		model.setKeyBackup(key);
		
		model.setEndInfoMap("098", "0001", "");
		
		
		return model;
		
	}
}
