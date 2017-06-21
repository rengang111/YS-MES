
package com.ys.business.service.mould;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.mouldlendregister.MouldLendRegisterModel;
import com.ys.business.db.dao.B_MouldAcceptanceDao;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.dao.B_MouldDetailDao;
import com.ys.business.db.dao.B_MouldLendConfirmDao;
import com.ys.business.db.dao.B_MouldLendDetailDao;
import com.ys.business.db.dao.B_MouldLendRegisterDao;
import com.ys.business.db.dao.B_MouldPayInfoDao;
import com.ys.business.db.dao.B_MouldPayListDao;
import com.ys.business.db.dao.B_OrganizationDao;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldDetailData;
import com.ys.business.db.data.B_MouldLendConfirmData;
import com.ys.business.db.data.B_MouldLendDetailData;
import com.ys.business.db.data.B_MouldLendRegisterData;
import com.ys.business.db.data.B_MouldPayInfoData;
import com.ys.business.db.data.B_MouldPayListData;
import com.ys.business.db.data.B_OrganizationData;
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
public class MouldLendRegisterService extends BaseService {

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
		
		dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");
		dataModel.setQueryName("mouldlendregisterquerydefine_search");
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

	public MouldLendRegisterModel getMouldLendRegisterBaseInfo(HttpServletRequest request, String key) throws Exception {
		BaseModel dataModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		B_MouldLendRegisterDao dao = new B_MouldLendRegisterDao();
		B_MouldLendRegisterData dbData = new B_MouldLendRegisterData();
		B_MouldLendConfirmDao confirmDao = new B_MouldLendConfirmDao();
		B_MouldLendConfirmData confirmData = new B_MouldLendConfirmData();

		if (key != null && !key.equals("")) {
			dbData.setId(key);
			dbData = (B_MouldLendRegisterData)dao.FindByPrimaryKey(dbData);
			
			CalendarUtil calendarUtil = new CalendarUtil(dbData.getLendtime());
			dbData.setLendtime(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
		
			calendarUtil = new CalendarUtil(dbData.getReturntime());
			dbData.setReturntime(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			
			HashMap<String, Object> productModel = doProductModelIdSearch(request, "");
			ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>)productModel.get("data");
			for(HashMap<String, String> rowData:data) {
				if (rowData.get("id").equals(dbData.getProductmodel())) {
					model.setProductModelName(rowData.get("des"));
					break;
				}
			}
			
			try {
				confirmData.setLendid(dbData.getId());
				confirmData = (B_MouldLendConfirmData)confirmDao.FindByPrimaryKey(confirmData);
			}
			catch(Exception e) {
				
			}
		}
		
		model.setMouldFactoryList(doGetMouldFactoryList(request));
		model.setProposerList(doGetUserList(request));
		model.setMouldLendRegisterData(dbData);
		model.setMouldLendConfirmData(confirmData);
		model.setKeyBackup(key);
		
		model.setEndInfoMap("098", "0001", "");
		
		
		return model;
		
	}
	
	public ArrayList<ListOption> doGetMouldFactoryList(HttpServletRequest request) throws Exception {
			
		ArrayList<ListOption> listOption = new ArrayList<ListOption>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	

		dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");	
		dataModel.setQueryName("mouldlendregisterquerydefine_searchfactory");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(-1, -1);	
			
		for( HashMap<String, String> rowData:dataModel.getYsViewData()) {
			ListOption x = new ListOption(rowData.get("id"), rowData.get("shortName"));
			listOption.add(x);
		}
			
		return listOption;	
	}		

	public ArrayList<ListOption> doGetUserList(HttpServletRequest request) throws Exception {
		
		ArrayList<ListOption> listOption = new ArrayList<ListOption>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	

		dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");	
		dataModel.setQueryName("mouldlendregisterquerydefine_searchuser");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(-1, -1);	
			
		for( HashMap<String, String> rowData:dataModel.getYsViewData()) {
			ListOption x = new ListOption(rowData.get("id"), rowData.get("name"));
			listOption.add(x);
		}
			
		return listOption;	
	}	
	
	public HashMap<String, Object> doProductModelIdSearch(HttpServletRequest request, String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
			
		String key = request.getParameter("key").toUpperCase();	
			
		dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");	
		dataModel.setQueryName("mouldlendregisterquerydefine_searchproductmodel");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
	
	
	public HashMap<String, Object> doModelNoSearch(HttpServletRequest request, String data) throws Exception {
		
		HashMap<String, Object> modelMap = new HashMap<String, Object>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	
			
		String key = request.getParameter("key").toUpperCase();	
			
		dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");	
		dataModel.setQueryName("mouldlendregisterquerydefine_searchmodelno");	
			
		baseQuery = new BaseQuery(request, dataModel);	
			
		baseQuery.getYsQueryData(0,0);	
			
		modelMap.put("data", dataModel.getYsViewData());	
			
		return modelMap;	
	}
	public HashMap<String, Object> doGetMouldLendDetailList(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> rtnData = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		BaseModel dataModel = new BaseModel();
		String id = "";
		
		data = URLDecoder.decode(data, "UTF-8");

		id = getJsonData(data, "keyBackup");
		if (id.equals("")) {
			id = "-1";
		}
		
		dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");
		dataModel.setQueryName("mouldlendregisterquerydefine_searchld");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("lendId", id);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}
	
	public MouldLendRegisterModel doUpdateLdInit(HttpServletRequest request, String mouldLendNo, String id) throws Exception {
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		B_MouldLendDetailDao lendDao = new B_MouldLendDetailDao();
		B_MouldLendDetailData lendData = new B_MouldLendDetailData();
		B_MouldDetailDao dao = new B_MouldDetailDao();
		B_MouldDetailData data = new B_MouldDetailData();
		
		if (id != null && !id.equals("")) {
			lendData.setId(id);
			lendData = (B_MouldLendDetailData)lendDao.FindByPrimaryKey(lendData);
			data.setId(lendData.getMouldno());
			data = (B_MouldDetailData)dao.FindByPrimaryKey(data);
			//model.setName(data.getName());
		}
		
		model.setMouldLendDetailData(lendData);
		//model.setNo(data.getNo());
		model.setKeyBackup(id);
		model.setMouldLendNo(mouldLendNo);
		//model.setType(type);

		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}	

	public MouldLendRegisterModel doUpdateLd(HttpServletRequest request, String data, UserInfo userInfo) {
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		B_MouldLendDetailDao lendDao = new B_MouldLendDetailDao();
		B_MouldLendDetailData lendData = new B_MouldLendDetailData();										
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;
		
		String key = getJsonData(data, "keyBackup");
		String guid = "";
		
		try {
			if (key == null || key.equals("")) {
				guid = BaseDAO.getGuId();									
				lendData.setId(guid);	
				lendData.setLendid(getJsonData(data, "mouldLendNo"));
				lendData.setMouldno(getJsonData(data, "mouldDetailId"));
				lendData.setStatus("0");
				lendData = updateLdModifyInfo(lendData, userInfo);
				lendDao.Create(lendData);
				key = guid;
			} else {
				lendData.setId(key);
				lendData = (B_MouldLendDetailData)lendDao.FindByPrimaryKey(lendData);
				lendData.setMouldno(getJsonData(data, "mouldDetailId"));
				lendData = updateLdModifyInfo(lendData, userInfo);
				lendDao.Store(lendData);
			}
			model.setEndInfoMap(NORMAL, "", key);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", key);
		}
		
		return model;
	}		
	

	public MouldLendRegisterModel doConfirmLend(HttpServletRequest request, String data, UserInfo userInfo) {
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		B_MouldAcceptanceData dbData = new B_MouldAcceptanceData();											
		B_MouldAcceptanceDao dao = new B_MouldAcceptanceDao();											
		
		try {
			model = doUpdate(request, data, "1", userInfo);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
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
			
			String lendId = getJsonData(data, "lendId");
			infoData.setId(lendId);
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
	
	public MouldLendRegisterModel doUpdate(HttpServletRequest request, String data, String confirm, UserInfo userInfo) {
		MouldLendRegisterModel model = new MouldLendRegisterModel();

		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		String key = getJsonData(data, "keyBackup");

		boolean checkLendNoFlg = false;
		
		try {
			/*
			dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");
			dataModel.setQueryName("mouldcontractquerydefine_checkLendNo");
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			userDefinedSearchCase.put("mouldLendNo", getJsonData(data, "mouldLendNo"));
			baseQuery = new BaseQuery(request, dataModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsQueryData(0,0);
			if (dataModel.getYsViewData().size() == 0) {
				checkLendNoFlg = true;
			} else {
				if (dataModel.getYsViewData().get(0).get("id").equals(key)) {
					checkLendNoFlg = true;
				}
			}
			if (checkLendNoFlg) {
			} else {
				model.setEndInfoMap(DUMMYKEY, "err005", key);
			}
			*/
			
			BusinessDbUpdateEjb ejb = new BusinessDbUpdateEjb();
			String mouldLendNo = ejb.executeMouldLendRegisterUpdate(request, key, data, confirm, userInfo);
			model.setEndInfoMap(NORMAL, "", mouldLendNo);

		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", key);
		}
		
		return model;
	}
	
	public MouldLendRegisterModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldLendRegisterModel model = new MouldLendRegisterModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeMouldLendRegisterDelete(data, userInfo);
	        
	        model.setEndInfoMap(NORMAL, "", "");
	        
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public MouldLendRegisterModel doDeleteLd(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldLendRegisterModel model = new MouldLendRegisterModel();
    	
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeMouldLendDetailDelete(data, userInfo);					
	        
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
			
			String key = request.getParameter("key").toUpperCase();
			
			dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");
			dataModel.setQueryName("mouldlendregisterquerydefine_searchproductmodel");
			
			baseQuery = new BaseQuery(request, dataModel);
			
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			baseQuery.getYsQueryData(0,0);
			
			modelMap.put("data", dataModel.getYsViewData());
			
			return modelMap;
		}	

	
	public static B_MouldLendRegisterData updateMouldLendRegisterModifyInfo(B_MouldLendRegisterData data, UserInfo userInfo) {
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
	
	public static B_MouldLendDetailData updateLdModifyInfo(B_MouldLendDetailData data, UserInfo userInfo) {
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
	
	public static B_MouldLendConfirmData updateMouldLendConfirmModifyInfo(B_MouldLendConfirmData data, UserInfo userInfo) {
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


	
}
