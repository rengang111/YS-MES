
package com.ys.business.service.mouldreturnregister;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ys.business.action.model.common.ListOption;
import com.ys.business.action.model.mouldreturnregister.MouldReturnRegisterModel;
import com.ys.business.db.dao.B_MouldAcceptanceDao;
import com.ys.business.db.dao.B_MouldBaseInfoDao;
import com.ys.business.db.dao.B_MouldDetailDao;
import com.ys.business.db.dao.B_MouldLendRegisterDao;
import com.ys.business.db.dao.B_MouldReturnRegisterDao;
import com.ys.business.db.dao.B_MouldPayInfoDao;
import com.ys.business.db.dao.B_MouldPayListDao;
import com.ys.business.db.dao.B_OrganizationDao;
import com.ys.business.db.data.B_MouldAcceptanceData;
import com.ys.business.db.data.B_MouldBaseInfoData;
import com.ys.business.db.data.B_MouldDetailData;
import com.ys.business.db.data.B_MouldLendRegisterData;
import com.ys.business.db.data.B_MouldReturnRegisterData;
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
public class MouldReturnRegisterService extends BaseService {

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
		
		dataModel.setQueryFileName("/business/mouldreturnregister/mouldreturnregisterquerydefine");
		dataModel.setQueryName("mouldreturnregisterquerydefine_search");
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

	public MouldReturnRegisterModel getMouldReturnRegisterBaseInfo(HttpServletRequest request) throws Exception {
		BaseModel dataModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		
		MouldReturnRegisterModel model = new MouldReturnRegisterModel();
		B_MouldLendRegisterDao lendDao = new B_MouldLendRegisterDao();
		B_MouldLendRegisterData lendData = new B_MouldLendRegisterData();
		B_MouldReturnRegisterDao returnDao = new B_MouldReturnRegisterDao();
		B_MouldReturnRegisterData returnData = new B_MouldReturnRegisterData();
		
		CalendarUtil calendarUtil;
		
		String key = request.getParameter("key");
		String lendId = request.getParameter("lendId");

		model.setLendId(lendId);
		
		if (lendId != null && !lendId.equals("")) {
			lendData.setId(lendId);
			lendData = (B_MouldLendRegisterData)lendDao.FindByPrimaryKey(lendData);
			
			model.setLendId(lendData.getId());
			
			calendarUtil = new CalendarUtil(lendData.getLendtime());
			lendData.setLendtime(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
		
			calendarUtil = new CalendarUtil(lendData.getReturntime());
			lendData.setReturntime(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
			
			HashMap<String, Object> productModel = doProductModelIdSearch(request, "");
			ArrayList<HashMap<String, String>> data = (ArrayList<HashMap<String, String>>)productModel.get("data");
			for(HashMap<String, String> rowData:data) {
				if (rowData.get("id").equals(lendData.getProductmodel())) {
					model.setProductModelName(rowData.get("des"));
					model.setProductModelNo(rowData.get("dicName"));
					break;
				}
			}
			
			dataModel.setQueryFileName("/business/mouldlendregister/mouldlendregisterquerydefine");	
			dataModel.setQueryName("mouldlendregisterquerydefine_searchfactory");	
			BaseQuery baseQuery = new BaseQuery(request, dataModel);	
			baseQuery.getYsQueryData(-1, -1);
				
			for( HashMap<String, String> rowData:dataModel.getYsViewData()) {
				if (rowData.get("id").equals(lendData.getLendfactory())) {
					model.setMouldFactory(rowData.get("shortName"));
					break;
				}
			}
		}
		if (key != null && !key.equals("")) {

			returnData.setId(key);
			returnData = (B_MouldReturnRegisterData)returnDao.FindByPrimaryKey(returnData);
			calendarUtil = new CalendarUtil(returnData.getFactreturntime());
			returnData.setFactreturntime(CalendarUtil.fmtDate(calendarUtil.getDate(), "yyyy/MM/dd"));
		}
		
		model.setAcceptorList(doGetUserList(request));
		model.setAcceptResultList(doOptionChange(DicUtil.ACCEPTRESULT, ""));
		model.setMouldLendRegisterData(lendData);
		model.setMouldReturnRegisterData(returnData);
		model.setKeyBackup(key);
		
		model.setEndInfoMap("098", "0001", "");
		
		return model;
		
	}
	
	public ArrayList<ListOption> doGetUserList(HttpServletRequest request) throws Exception {
		
		ArrayList<ListOption> listOption = new ArrayList<ListOption>();	
		BaseModel dataModel = new BaseModel();	
		BaseQuery baseQuery = null;	

		dataModel.setQueryFileName("/business/mouldreturnregister/mouldreturnregisterquerydefine");	
		dataModel.setQueryName("mouldreturnregisterquerydefine_searchuser");	
			
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
			
		dataModel.setQueryFileName("/business/mouldreturnregister/mouldreturnregisterquerydefine");	
		dataModel.setQueryName("mouldreturnregisterquerydefine_searchproductmodel");	
			
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

		id = getJsonData(data, "lendId");
		if (id.equals("")) {
			id = "-1";
		}
		
		dataModel.setQueryFileName("/business/mouldreturnregister/mouldreturnregisterquerydefine");
		dataModel.setQueryName("mouldreturnregisterquerydefine_searchld");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("lendId", id);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(0, -1);	
				
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}

	public MouldReturnRegisterModel doConfirmReturn(HttpServletRequest request, String data, UserInfo userInfo) {
		MouldReturnRegisterModel model = new MouldReturnRegisterModel();
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
	
	public MouldReturnRegisterModel doUpdate(HttpServletRequest request, String data, String confirm, UserInfo userInfo) {
		MouldReturnRegisterModel model = new MouldReturnRegisterModel();
		B_MouldReturnRegisterDao returnDao = new B_MouldReturnRegisterDao();
		B_MouldReturnRegisterData returnData = new B_MouldReturnRegisterData();
		BaseModel dataModel = new BaseModel();
		BaseQuery baseQuery = null;

		String key = getJsonData(data, "keyBackup");
		String lendId = getJsonData(data, "lendId");
		String guid = "";
		String mouldReturnNo = "1608H";
		
		try {

			if (key == null || key.equals("")) {
				guid = BaseDAO.getGuId();									
				returnData.setId(guid);	
				
				dataModel.setQueryFileName("/business/mouldreturnregister/mouldreturnregisterquerydefine");
				dataModel.setQueryName("mouldreturnregisterquerydefine_getserialno");
				baseQuery = new BaseQuery(request, dataModel);
				ArrayList<HashMap<String, String>> mouldReturnNoMap = baseQuery.getYsQueryData(0,0);				
				if (mouldReturnNoMap.size() > 0) {
					mouldReturnNo += String.format("%03d", Integer.parseInt(mouldReturnNoMap.get(0).get("serialNo")));
				}
				
				returnData.setLendid(lendId);
				returnData.setMouldreturnno(mouldReturnNo);
				returnData.setFactreturntime(getJsonData(data, "factReturnTime"));
				returnData.setAcceptresult(getJsonData(data, "acceptResult"));
				returnData.setMemo(getJsonData(data, "memo"));
				returnData.setAcceptor(getJsonData(data, "acceptor"));
				returnData.setConfirm(confirm);

				returnData = updateMouldReturnRegisterModifyInfo(returnData, userInfo);
				returnDao.Create(returnData);
				
				key = guid;
			} else {
				returnData.setId(key);
				returnData = (B_MouldReturnRegisterData)returnDao.FindByPrimaryKey(returnData);
				//returnData.setMouldreturnno(mouldReturnNo);
				returnData.setFactreturntime(getJsonData(data, "factReturnTime"));
				returnData.setAcceptresult(getJsonData(data, "acceptResult"));
				returnData.setMemo(getJsonData(data, "memo"));
				returnData.setAcceptor(getJsonData(data, "acceptor"));
				returnData.setConfirm(confirm);
				returnData = updateMouldReturnRegisterModifyInfo(returnData, userInfo);
				returnDao.Store(returnData);
			}
			
			model.setEndInfoMap(NORMAL, "", key);

		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", key);
		}
		
		return model;
	}
	
	public MouldReturnRegisterModel doDelete(HttpServletRequest request, String data, UserInfo userInfo){
		
		MouldReturnRegisterModel model = new MouldReturnRegisterModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeMouldRegisterDelete(data, userInfo);
	        
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
	
	
	public static B_MouldReturnRegisterData updateMouldReturnRegisterModifyInfo(B_MouldReturnRegisterData data, UserInfo userInfo) {
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
