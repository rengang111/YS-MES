package com.ys.business.service.mouldregister;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.mouldregister.MouldRegisterModel;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldFactoryData;
import com.ys.business.db.data.B_MouldSubData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

@Service
public class MouldRegisterService extends BaseService {

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
		
		model.setTypeList(doOptionChange(DicUtil.MOULDTYPE, ""));
		model.setMouldFactoryList(doGetMouldFactoryList(request));
		model.setKeyBackup("");
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public MouldRegisterModel updateInit(HttpServletRequest request, String key) throws Exception {
		MouldRegisterModel model = new MouldRegisterModel();
		B_MouldBaseInfoDao dao = new B_MouldBaseInfoDao();
		B_MouldBaseInfoData dbData = new B_MouldBaseInfoData();

		if (key != null && !key.equals("")) {
			dbData.setId(key);
			dbData = (B_MouldBaseInfoData)dao.FindByPrimaryKey(dbData);
			
			model = getProductModelName(request, model, dbData.getProductmodelid());
			
			
			
		}
		model.setTypeList(doOptionChange(DicUtil.MOULDTYPE, ""));
		model.setMouldFactoryList(doGetMouldFactoryList(request));
		model.setMouldBaseInfoData(dbData);
		
		model.setKeyBackup(key);
		
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
			
			String type = getJsonData(data, "type");
			dicData.setDicid(type);
			dicData.setDictypeid(DicUtil.MOULDTYPE);
			dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
			type = dicData.getDicdes();
			
			String mouldBaseId = getJsonData(data, "mouldBaseId");
			infoData.setId(mouldBaseId);
			infoData = (B_MouldBaseInfoData)infoDao.FindByPrimaryKey(infoData);
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
	
	public MouldRegisterModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldRegisterModel model = new MouldRegisterModel();
		
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
		
		String mouldId = "";

		String productModelIdView = getJsonData(data, "productModelIdView");
		String type = getJsonData(data, "type");
	
		dicData.setDictypeid(DicUtil.MOULDTYPE);
		dicData.setDicid(type);
		dicData = (S_DICData)dicDao.FindByPrimaryKey(dicData);
		String typeMark = dicData.getDicdes();
		mouldId = productModelIdView + typeMark;
		
		BaseModel dataModel = new BaseModel();
		dataModel.setQueryFileName("/business/mouldregister/mouldregisterquerydefine");
		dataModel.setQueryName("mouldregisterquerydefine_getmouldserialno");
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("mouldNo", mouldId);
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<HashMap<String, String>> mouldIdMap = baseQuery.getYsQueryData(0,0);
		if (mouldIdMap.size() > 0) {
			mouldId += String.format("%02d", Integer.parseInt(mouldIdMap.get(0).get("serialNo")));
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
	

	
}
