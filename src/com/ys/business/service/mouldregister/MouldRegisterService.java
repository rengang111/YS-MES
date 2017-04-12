package com.ys.business.service.mouldregister;

import java.io.File;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ckfinder.connector.utils.FileUtils;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.externalsample.ExternalSampleModel;
import com.ys.business.action.model.mouldregister.MouldRegisterModel;
import com.ys.business.db.dao.B_ExternalSampleDao;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.dao.B_MouldFactoryDao;
import com.ys.business.db.dao.B_MouldHistoryPriceDao;
import com.ys.business.db.dao.B_MouldLastestPriceDao;
import com.ys.business.db.dao.B_MouldSubDao;
import com.ys.business.db.dao.B_SupplierDao;
import com.ys.business.db.data.B_ExternalSampleData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldFactoryData;
import com.ys.business.db.data.B_MouldHistoryPriceData;
import com.ys.business.db.data.B_MouldLastestPriceData;
import com.ys.business.db.data.B_MouldSubData;
import com.ys.business.db.data.B_SupplierData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.common.I_BaseService;
import com.ys.system.service.common.I_MultiAlbumService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.UploadReceiver;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

@Service
public class MouldRegisterService extends BaseService implements I_BaseService {

	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
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
		
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
		dataModel.setQueryName("mouldregisterquerydefine_search");
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

	public MouldRegisterModel doAddInit(HttpServletRequest request) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();
		
		//model.setTypeList(doOptionChange(DicUtil.MOULDTYPE, ""));
		model.setMouldFactoryList(doGetMouldFactoryList(request));
		model.setUnitList(doOptionChange(DicUtil.MEASURESTYPE, ""));
		model.setKeyBackup("");
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public MouldRegisterModel doUpdateInit(HttpServletRequest request, String key, String activeSubCode) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		B_MouldSubDao subDao = new B_MouldSubDao();
		B_MouldSubData subData = new B_MouldSubData();

		if (key != null && !key.equals("")) {
			dbData.setId(key);
			dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
			model.setMouldBaseInfoData(dbData);
			
			subData.setId(activeSubCode);
			subData = (B_MouldSubData)subDao.FindByPrimaryKey(subData);
			model.setSubCode(subData.getSubcode());
			/*
			S_DICDao dicDao = new S_DICDao();
			S_DICData dicData = new S_DICData();
			dicData.setDicid(dbData.getProductmodelid());
			dicData.setDictypeid(DicUtil.PRODUCTMODEL);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			model.setProductModelIdView(dicData.getDicname());
			model.setProductModelName(dicData.getDicdes());
			*/
			model.setProductModelIdView(dbData.getProductmodelid());
			model.setProductModelName(dbData.getProductmodelname());

			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			BaseQuery baseQuery = null;
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("mouldregisterquerydefine_getsubids");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("mouldId", key);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			model.setMouldSubDatas(baseQuery.getYsQueryData(0,0));
			
			userDefinedSearchCase = new HashMap<String, String>();
			dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("getParentName");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("keywords1", dbData.getType());
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			ArrayList<HashMap<String, String>> tmpData = baseQuery.getYsQueryData(0,0);
			if (tmpData.size() > 0) {
				model.setTypeDesc(tmpData.get(0).get("name"));
				model.setMouldType(tmpData.get(0).get("id"));
			}
			
			userDefinedSearchCase = new HashMap<String, String>();
			dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("mouldregisterquerydefine_getfactorylist");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("activeSubCode", activeSubCode);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			model.setMouldFactoryDatas(baseQuery.getYsQueryData(0,0));
			
		}
		//model.setTypeList(doOptionChange(DicUtil.MOULDTYPE, ""));
		model.setMouldFactoryList(doGetMouldFactoryList(request));
		model.setUnitList(doOptionChange(DicUtil.MEASURESTYPE, ""));
		
		model.setKeyBackup(key);
		
		model.setActiveSubCode(activeSubCode);
		
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public MouldRegisterModel doAddFactoryInit(HttpServletRequest request) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		B_MouldSubDao subDao = new B_MouldSubDao();
		B_MouldSubData subData = new B_MouldSubData();
		S_DICDao dicDao = new S_DICDao();
		S_DICData dicData = new S_DICData();
		
		String activeSubCode = request.getParameter("activeSubCode");
		
		if (activeSubCode != null && !activeSubCode.equals("")) {
			subData.setId(activeSubCode);
			subData = (B_MouldSubData)subDao.FindByPrimaryKey(subData);
			model.setSubCode(subData.getSubcode());
			
			dbData.setId(subData.getMouldid());
			dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
			model.setMouldBaseInfoData(dbData);
			/*

			dicData.setDicid(dbData.getProductmodelid());
			dicData.setDictypeid(DicUtil.PRODUCTMODEL);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			model.setProductModelIdView(dicData.getDicname());
			model.setProductModelName(dicData.getDicdes());
			*/
			model.setProductModelIdView(dbData.getProductmodelid());
			model.setProductModelName(dbData.getProductmodelname());
			
			dicData.setDicid(dbData.getUnit());
			dicData.setDictypeid(DicUtil.MEASURESTYPE);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			model.setUnit(dicData.getDicname());

			//dicData.setDicid(dbData.getType());
			//dicData.setDictypeid(DicUtil.MOULDTYPE);
			//dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			//model.setType(dicData.getDicname());
			model.setType(dbData.getType());
			
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("getParentName");
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("keywords1", dbData.getType());
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			ArrayList<HashMap<String, String>> tmpData = baseQuery.getYsQueryData(0,0);
			if (tmpData.size() > 0) {
				model.setTypeDesc(tmpData.get(0).get("name"));
				model.setMouldType(tmpData.get(0).get("id"));
			}

		}
		
		model.setCurrencyList(doOptionChange(DicUtil.CURRENCY, ""));
		model.setKeyBackup("");
		model.setActiveSubCode(activeSubCode);
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public MouldRegisterModel doUpdateFactoryInit(HttpServletRequest request, String key, String activeSubCode) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		B_MouldSubDao subDao = new B_MouldSubDao();
		B_MouldSubData subData = new B_MouldSubData();
		B_MouldFactoryDao factoryDao = new B_MouldFactoryDao();
		B_MouldFactoryData factoryData = new B_MouldFactoryData();
		B_SupplierDao supplierDao = new B_SupplierDao();
		B_SupplierData supplierData = new B_SupplierData();
		S_DICDao dicDao = new S_DICDao();
		S_DICData dicData = new S_DICData();

		if (key != null && !key.equals("")) {
			subData.setId(activeSubCode);
			subData = (B_MouldSubData)subDao.FindByPrimaryKey(subData);
			model.setSubCode(subData.getSubcode());
			
			dbData.setId(subData.getMouldid());
			dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
			model.setMouldBaseInfoData(dbData);
			
			factoryData.setId(key);
			factoryData = (B_MouldFactoryData)factoryDao.FindByPrimaryKey(factoryData);
			model.setMouldFactoryData(factoryData);
			model.setPriceTime(factoryData.getPricetime());

			supplierData.setRecordid(factoryData.getMouldfactoryid());
			supplierData = (B_SupplierData)supplierDao.FindByPrimaryKey(supplierData);
			model.setSupplierData(supplierData);
			/*
			dicData.setDicid(dbData.getProductmodelid());
			dicData.setDictypeid(DicUtil.PRODUCTMODEL);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			model.setProductModelIdView(dicData.getDicname());
			model.setProductModelName(dicData.getDicdes());
			*/
			model.setProductModelIdView(dbData.getProductmodelid());
			model.setProductModelName(dbData.getProductmodelname());
			
			
			dicData.setDicid(dbData.getUnit());
			dicData.setDictypeid(DicUtil.MEASURESTYPE);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			model.setUnit(dicData.getDicname());

			//dicData.setDicid(dbData.getType());
			//dicData.setDictypeid(DicUtil.MOULDTYPE);
			//dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			//model.setType(dicData.getDicname());
			model.setType(dbData.getType());
			
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseModel dataModel = new BaseModel();
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("getParentName");
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("keywords1", dbData.getType());
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			ArrayList<HashMap<String, String>> tmpData = baseQuery.getYsQueryData(0,0);
			if (tmpData.size() > 0) {
				model.setTypeDesc(tmpData.get(0).get("name"));
				model.setMouldType(tmpData.get(0).get("id"));
			}
		}

		model.setCurrencyList(doOptionChange(DicUtil.CURRENCY, ""));
		
		model.setKeyBackup(key);
		//model.setSupplierid(supplierid);
		
		model.setActiveSubCode(activeSubCode);
		
		model.setEndInfoMap("098", "0001", "");
		
		
		return model;
		
	}
	
	public MouldRegisterModel doViewHistoryPriceInit(HttpServletRequest request) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();
		
		model.setActiveSubCode(request.getParameter("activeSubCode"));
		model.setMouldFactoryId(request.getParameter("mouldFactoryId"));
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public ArrayList<ListOption> doGetMouldFactoryList(HttpServletRequest request) throws Exception {
			
		ArrayList<ListOption> listOption = new ArrayList<ListOption>();	

		HashMap<String, Object> modelData = doFactoryIdSearch(request);
		ArrayList<HashMap<String, String>> datas = (ArrayList<HashMap<String, String>>)modelData.get("data");
		for( HashMap<String, String> rowData:datas) {
			ListOption x = new ListOption(rowData.get("id"), rowData.get("shortName"));
			listOption.add(x);
		}
			
		return listOption;	
	}		

	public HashMap<String, Object> doProductModelIdSearch(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
			
		//String key = request.getParameter("key").toUpperCase();	
			
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");	
		dataModel.setQueryName("mouldregisterquerydefine_searchproductmodel");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}

	public HashMap<String, Object> doSupplierSearch(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		String key = request.getParameter("key");	
			
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");	
		dataModel.setQueryName("mouldregisterquerydefine_supplierSearch");	
		baseQuery = new BaseQuery(request, dataModel);	
		
		userDefinedSearchCase.put("keywords1", key);
		userDefinedSearchCase.put("keywords2", key);
		userDefinedSearchCase.put("keywords3", key);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
	
	public HashMap<String, Object> doTypeSearch(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		String key = request.getParameter("key");	
			
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");	
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
	
	public HashMap<String, Object> doFactoryIdSearch(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
			
		//String key = request.getParameter("key").toUpperCase();	
			
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");	
		dataModel.setQueryName("mouldregisterquerydefine_searchfactory");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
	
	public String doCheckNo(HttpServletRequest request, String data) {
		String message = "";										
		B_MouldBaseInfoDao infoDao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData infoData = new B_MouldBaseInfoData();
		S_DICDao dicDao = new S_DICDao();
		S_DICData dicData = new S_DICData();
		
		try {
			String no = getJsonData(data, "no");
			/*
			String type = getJsonData(data, "type");
			dicData.setDicid(type);
			dicData.setDictypeid(DicUtil.MOULDTYPE);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			type = dicData.getDicdes();
			*/
			String mouldBaseId = getJsonData(data, "mouldBaseId");
			infoData.setId(mouldBaseId);
			infoData = (B_MouldBaseInfoData)infoDao.FindByPrimaryKey(infoData);
			//dicData.setDicid(infoData.getProductmodelid());
			//dicData.setDictypeid(DicUtil.PRODUCTMODEL);
			//dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			String productModelName = infoData.getProductmodelid();
			
			message = "err008";
			/*
			if (no.length() == (productModelName.length() + 2)) {
				if (no.substring(0, no.length() - 2).equals(productModelName + type)) {
					message = "";
				}
			}
			*/
		}
		catch(Exception e) {
			message = "err001";
		}
		
		return message;
	}
	
	public MouldRegisterModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();

		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
			
		model = bean.executeMouldRegisterUpdate(request, data, userInfo);
		
		return model;
	}
	
	public MouldRegisterModel doUpdateFactory(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();

		String key = getJsonData(data, "keyBackup");
		String activeSubCode = getJsonData(data, "activeSubCode");
		
		B_MouldFactoryDao dao = new B_MouldFactoryDao();
		B_MouldFactoryData dbData = new B_MouldFactoryData();
		B_MouldHistoryPriceDao historyDao = new B_MouldHistoryPriceDao();
		B_MouldHistoryPriceData historyData = new B_MouldHistoryPriceData();
		B_MouldLastestPriceDao lastestPriceDao = new B_MouldLastestPriceDao();
		B_MouldLastestPriceData lastestPriceData = new B_MouldLastestPriceData();
		String supplierid = getJsonData(data, "supplierid");
		String price = getJsonData(data, "price");
		String currency = getJsonData(data, "currency");
		String priceTime = getJsonData(data, "priceTime");
		String unit = getJsonData(data, "unit");
		
		BaseTransaction ts = new BaseTransaction();
		try {
			ts.begin();

			if (!key.equals("")) {
				dbData.setId(key);
				dbData = (B_MouldFactoryData)dao.FindByPrimaryKey(dbData);
				
				historyData.setId(BaseDAO.getGuId());
				historyData.setSubcode(dbData.getSubcode());
				historyData.setMouldfactoryid(dbData.getMouldfactoryid());
				historyData.setPrice(dbData.getPrice());
				historyData.setCurrency(dbData.getCurrency());
				historyData.setPricetime(dbData.getPricetime());
				historyData.setPriceunit(unit);
				historyData = updateMouldHistoryPriceModifyInfo(historyData, userInfo);
				historyDao.Create(historyData);
				
				lastestPriceData.setId(activeSubCode);
				lastestPriceData = (B_MouldLastestPriceData)lastestPriceDao.FindByPrimaryKey(lastestPriceData);
				lastestPriceData.setMouldfactoryid(supplierid);
				lastestPriceData.setPrice(price);
				lastestPriceData.setPricetime(dbData.getPricetime());
				lastestPriceData = updateMouldLastestPriceModifyInfo(lastestPriceData, userInfo);
				lastestPriceDao.Store(lastestPriceData);
				
				dbData.setMouldfactoryid(supplierid);
				dbData.setPrice(price);
				dbData.setPricetime(priceTime);
				dbData.setCurrency(currency);
				dbData.setPriceunit(unit);
				dbData = updateMouldFactoryModifyInfo(dbData, userInfo);
				dao.Store(dbData);
				
			} else {
				dbData.setId(BaseDAO.getGuId());
				dbData.setSubcode(activeSubCode);
				dbData.setMouldfactoryid(supplierid);
				dbData.setPrice(price);
				dbData.setPricetime(priceTime);
				dbData.setCurrency(currency);
				dbData.setPriceunit(unit);			
				dbData = updateMouldFactoryModifyInfo(dbData, userInfo);
				dao.Create(dbData);
				try {
					lastestPriceData.setId(activeSubCode);
					lastestPriceData = (B_MouldLastestPriceData)lastestPriceDao.FindByPrimaryKey(lastestPriceData);
					lastestPriceData.setMouldfactoryid(supplierid);
					lastestPriceData.setPrice(price);
					lastestPriceData.setPricetime(dbData.getPricetime());
					lastestPriceData = updateMouldLastestPriceModifyInfo(lastestPriceData, userInfo);
					lastestPriceDao.Store(lastestPriceData);
				}
				catch(Exception e) {
					//lastestPriceData.setId(activeSubCode);
					lastestPriceData.setMouldfactoryid(supplierid);
					lastestPriceData.setPrice(price);
					lastestPriceData.setPricetime(priceTime);
					lastestPriceData = updateMouldLastestPriceModifyInfo(lastestPriceData, userInfo);
					lastestPriceDao.Create(lastestPriceData);
				}
				
				historyData.setId(BaseDAO.getGuId());
				historyData.setSubcode(dbData.getSubcode());
				historyData.setMouldfactoryid(dbData.getMouldfactoryid());
				historyData.setPrice(dbData.getPrice());
				historyData.setCurrency(dbData.getCurrency());
				historyData.setPricetime(dbData.getPricetime());
				historyData.setPriceunit(unit);
				historyData = updateMouldHistoryPriceModifyInfo(historyData, userInfo);
				historyDao.Create(historyData);
	
			}
			ts.commit();
			model.setEndInfoMap(BaseService.NORMAL, "", model.getKeyBackup() + "|" + model.getActiveSubCode());
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
		
		return model;
	}
	
	public MouldRegisterModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldRegisterModel model = new MouldRegisterModel();
		boolean isDBOperationSuccessed = false;
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeMouldRegisterDelete(request, data, userInfo);
	        
	        isDBOperationSuccessed = true;
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		if (isDBOperationSuccessed) {
			String removeData[] = data.split(",");			
			for (String key:removeData) {	
				String dir = request.getSession().getServletContext().getRealPath("/")
						+ BusinessConstants.BUSINESSPHOTOPATH + key; 			
				//String dirSmall = dir + BusinessConstants.BUSINESSSMALLPHOTOPATH; 			
				
				FileUtils.delete(new File(dir));
			}
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
			
			//String key = request.getParameter("key").toUpperCase();
			
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("mouldregisterquerydefine_searchproductmodel");
			
			baseQuery = new BaseQuery(request, dataModel);
			
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsQueryData(0,0);
			
			modelMap.put("data", dataModel.getYsViewData());
			
			return modelMap;
	}	

	public String getMouldId(HttpServletRequest request, String data) throws Exception {
		S_DICDao dicDao = new S_DICDao(); 
		S_DICData dicData = new S_DICData();
		
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldSubData subData = new B_MouldSubData();
		B_MouldSubDao subDao = new B_MouldSubDao();
		
		String mouldId = "";

		String type = getJsonData(data, "type");
		String activeSubCode = getJsonData(data, "activeSubCode");
		boolean isTypeChanged = false;
		try {
			if (!activeSubCode.equals("")) {
				subData.setId(activeSubCode);
				subData = (B_MouldSubData)subDao.FindByPrimaryKey(subData);
				dbData.setId(subData.getMouldid());
				dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
				if (!dbData.getType().equals(type)) {
					isTypeChanged = true;
				}
			} else {
				isTypeChanged = true;
			}
			if(isTypeChanged) {
				/*
				dicData.setDictypeid(DicUtil.MOULDTYPE);
				dicData.setDicid(type);
				dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
				String typeMark = dicData.getDicdes();
				mouldId = typeMark + "." + productModelIdView + "." ;
				*/
				mouldId = type;
				
				BaseModel dataModel = new BaseModel();
				dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
				dataModel.setQueryName("mouldregisterquerydefine_getmouldserialno");
				HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
				userDefinedSearchCase.put("mouldNo", mouldId);
				BaseQuery baseQuery = new BaseQuery(request, dataModel);
				baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				ArrayList<HashMap<String, String>> mouldIdMap = baseQuery.getYsQueryData(0,0);
				if (mouldIdMap.size() > 0) {
					mouldId += "." + String.format("%02d", Integer.parseInt(mouldIdMap.get(0).get("serialNo")));
				}
			} else {
				mouldId = "nochange";
			}
		}
		catch(Exception e) {
			
		}
		
		return mouldId;
	}
	
	public String doRotate(HttpServletRequest request, String data) throws Exception {
		S_DICDao dicDao = new S_DICDao(); 
		S_DICData dicData = new S_DICData();
		
		String mouldId = "";

		String keyBackup = getJsonData(data, "keyBackup");
		String type = getJsonData(data, "type");
		String productModelIdView = getJsonData(data, "productModelIdView");
		String rotateDirect = getJsonData(data, "rotateDirect");
		/*
		dicData.setDictypeid(DicUtil.MOULDTYPE);
		dicData.setDicid(type);
		dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
		String typeMark = dicData.getDicdes();
		mouldId = productModelIdView + typeMark;
		*/
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
		dataModel.setQueryName("mouldregisterquerydefine_rotate");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("mouldId", mouldId);
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<HashMap<String, String>> mouldIdMap = baseQuery.getYsQueryData(0,0);
		if (mouldIdMap.size() > 0) {
			int index = -1;
			for(HashMap<String, String> rowData:mouldIdMap) {
				String id = rowData.get("id");
				index++;
				if (id.equals(keyBackup)) {
					if (index == 0) {
						if (rotateDirect.equals("0")) {
							mouldId = id;
							break;
						} else {
							//next one
							if ((index + 1) >= mouldIdMap.size()) {
								mouldId = id;
								break;
							} else {
								mouldId = mouldIdMap.get(index + 1).get("id");
								break;
							}
								
						}
					} else {
						if (index == mouldIdMap.size() - 1) {
							if (rotateDirect.equals("1")) {
								mouldId = id;
							} else {
								if ((index - 1) < 0) {
									mouldId = id;
									break;
								} else {
									mouldId = mouldIdMap.get(index - 1).get("id");
									break;
								}
							}
						} else {
							if (rotateDirect.equals("1")) {
								if ((index + 1) >= mouldIdMap.size()) {
									mouldId = id;
									break;
								} else {
									mouldId = mouldIdMap.get(index + 1).get("id");
									break;
								}
							} else {
								//pre one
								if ((index - 1) < 0) {
									mouldId = id;
									break;
								} else {
									mouldId = mouldIdMap.get(index - 1).get("id");
									break;
								}
							}							
						}
					}
					
				}
			}
		}

		return mouldId;
	}
	
	public static B_MouldBaseInfoData updateMouldBaseInfoModifyInfo(B_MouldBaseInfoData data, UserInfo userInfo) {
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
	
	public static B_MouldSubData updateMouldSubModifyInfo(B_MouldSubData data, UserInfo userInfo) {
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

	public static B_MouldFactoryData updateMouldFactoryModifyInfo(B_MouldFactoryData data, UserInfo userInfo) {
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
	
	public static B_MouldLastestPriceData updateMouldLastestPriceModifyInfo(B_MouldLastestPriceData data, UserInfo userInfo) {
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
	
	public static B_MouldHistoryPriceData updateMouldHistoryPriceModifyInfo(B_MouldHistoryPriceData data, UserInfo userInfo) {
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
	
	private MouldRegisterModel getProductModelName(HttpServletRequest request, MouldRegisterModel model, String productModelId) throws Exception {
		HashMap<String, Object> productModel = doProductModelIdSearch(request);
		ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>)productModel.get("data");
		for(HashMap<String, String> rowData:data) {
			if (rowData.get("id").equals(productModelId)) {
				model.setProductModelName(rowData.get("des"));
				model.setProductModelIdView(rowData.get("name"));
				break;
			}
		}
		
		return model;
	}
	
	private MouldRegisterModel getFactoryName(HttpServletRequest request, MouldRegisterModel model, String factoryId) throws Exception {
		HashMap<String, Object> productModel = doProductModelIdSearch(request);
		ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>)productModel.get("data");
		for(HashMap<String, String> rowData:data) {
			if (rowData.get("id").equals(factoryId)) {
				model.setMouldFactoryId(rowData.get("no"));
				model.setMouldFactoryName(rowData.get("fullname"));
				break;
			}
		}
		
		return model;
	}
	
	public MouldRegisterModel getFileList(HttpServletRequest request, MouldRegisterModel model) {
		UploadReceiver uploadReceiver = new UploadReceiver();
		int arraySize = 0;
		String dir = request.getSession().getServletContext().getRealPath("/")
				+ BusinessConstants.BUSINESSPHOTOPATH + model.getMouldBaseInfoData().getId() ; 
		
		String nowUseImage = model.getMouldBaseInfoData().getImage_filename();
		
		ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();
		
		String[] filenames = uploadReceiver.getFileNameList(dir + File.separator + BusinessConstants.BUSINESSSMALLPHOTOPATH);
		if (null != filenames && filenames.length > 0){
			
			//将当前图片放到最前
			if (!(null == nowUseImage||nowUseImage.equals(""))){	
				
				ArrayList<String> list_image = new ArrayList<>(Arrays.asList(filenames));
				
				for(String fileName:list_image) {
					if(fileName.equals(nowUseImage)) {
						list_image.remove(nowUseImage);
						break;
					}
				}
				
				list_image.add(0, nowUseImage);		
				
				filenames = new String[list_image.size()];
				int index = 0;
				for(Object fileName:list_image) {
					filenames[index++] = String.valueOf(fileName);
				}
			}			
		}
				
		model.setFilenames(filenames);
		model.setImageKey(model.getMouldBaseInfoData().getId());
		model.setPath(BusinessConstants.BUSINESSPHOTOPATH);
		model.setNowUseImage(nowUseImage);
		
		return model;
	}
	
	public void setNowUseImage(String key, String src) throws Exception {
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		
		dbData.setId(key);
		dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(BusinessConstants.SESSION_USERINFO);
		
		dbData = updateMouldBaseInfoModifyInfo(dbData, userInfo);
		
		dbData.setImage_filename(src);
		
		dao.Store(dbData);
		
	}
	
	public String getNowUseImage(String key) {
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();
		
		String nowUseImage = "";
		
		try {
			dbData.setId(key);
			dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
			nowUseImage = dbData.getImage_filename();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return nowUseImage;
		
	}	
	
	public HashMap<String, Object> doGetFactoryPriceHistory(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {
	
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		String activeSubCode = "";
		String mouldFactoryId = "";
		
		data = URLDecoder.decode(data, "UTF-8");
	
		activeSubCode = request.getParameter("activeSubCode");
		mouldFactoryId = request.getParameter("mouldFactoryId");
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
		dataModel.setQueryName("mouldregisterquerydefine_getsupplierpricehistory");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("subCode", activeSubCode);
		userDefinedSearchCase.put("mouldFactoryId", mouldFactoryId);
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
	
	public HashMap<String, Object> doGetSubCodeFactoryList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		String key = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		key = getJsonData(data, "activeSubCode");
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
		dataModel.setQueryName("mouldregisterquerydefine_getfactorylist");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("activeSubCode", key);
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

	public MouldRegisterModel doDeleteFactory(HttpServletRequest request, String data, UserInfo userInfo) throws Exception{
		
		MouldRegisterModel model = new MouldRegisterModel();
		B_MouldFactoryDao dao = new B_MouldFactoryDao();
		B_MouldFactoryData dbData = new B_MouldFactoryData();
		
		BaseTransaction ts = new BaseTransaction();
		String activeSubCode = request.getParameter("activeSubCode");
		String key = request.getParameter("key");
		
		try {
				
			ts.begin();
			
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE b_MouldHistoryPrice SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");								
			sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");								
			sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");								
			sql.append(" WHERE mouldFactoryId = '" + request.getParameter("key") + "' ");
			sql.append(" AND subCode = '" + activeSubCode + "' ");
			sql.append(" AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");								
			BaseDAO.execUpdate(sql.toString());	
			
			dbData.setId(key);
			dbData = (B_MouldFactoryData)dao.FindByPrimaryKey(dbData);
			dbData = MouldRegisterService.updateMouldFactoryModifyInfo(dbData, userInfo);
			dbData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
			dao.Store(dbData);

			B_MouldLastestPriceDao lastestPriceDao = new B_MouldLastestPriceDao();
			B_MouldLastestPriceData lastestPriceData = new B_MouldLastestPriceData();
			lastestPriceData.setId(activeSubCode);
			lastestPriceData = (B_MouldLastestPriceData)lastestPriceDao.FindByPrimaryKey(lastestPriceData);

			BaseModel dataModel = new BaseModel();
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			BaseQuery baseQuery = null;
			dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
			dataModel.setQueryName("mouldregisterquerydefine_getnewlastestprice");
			baseQuery = new BaseQuery(request, dataModel);
			userDefinedSearchCase.put("subCode", activeSubCode);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			ArrayList<HashMap<String, String>> newLastestPrice = baseQuery.getYsQueryData(0,1);
			if (newLastestPrice.size() > 0) {
				lastestPriceData.setMouldfactoryid(newLastestPrice.get(0).get("mouldFactoryId"));
				lastestPriceData.setPrice(newLastestPrice.get(0).get("price"));
				lastestPriceData.setPricetime(newLastestPrice.get(0).get("priceTime"));
				lastestPriceData = updateMouldLastestPriceModifyInfo(lastestPriceData, userInfo);
				lastestPriceDao.Store(lastestPriceData);
			} else {
				lastestPriceData = updateMouldLastestPriceModifyInfo(lastestPriceData, userInfo);
				lastestPriceData.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				lastestPriceDao.Store(lastestPriceData);
			}
			
			ts.commit();
			
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			ts.rollback();
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		
		return model;
	}
	
	public MouldRegisterModel doDeleteFactoryPriceHistory(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldRegisterModel model = new MouldRegisterModel();
		B_MouldHistoryPriceDao dao = new B_MouldHistoryPriceDao();
		B_MouldHistoryPriceData dbData = new B_MouldHistoryPriceData();
		
		try {
			dbData.setId(request.getParameter("id"));
			dbData = (B_MouldHistoryPriceData)dao.FindByPrimaryKey(dbData);
			dbData = MouldRegisterService.updateMouldHistoryPriceModifyInfo(dbData, userInfo);
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
}
