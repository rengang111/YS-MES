package com.ys.business.service.esrelationfile;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Service;

import com.ys.business.action.model.esrelationfile.EsRelationFileModel;
import com.ys.business.db.dao.B_ESRelationFileDao;
import com.ys.business.db.data.B_ESRelationFileData;
import com.ys.business.ejb.BusinessDbUpdateEjb;
import com.ys.system.action.model.login.UserInfo;
import com.ys.util.basequery.common.BaseModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;

import javax.servlet.http.HttpServletRequest;

@Service
public class EsRelationFileService extends BaseService {
 
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo, int type) throws Exception {

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
		if (key.equals("")) {
			key = DUMMYKEY;
		}
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/business/esrelationfile/esrelationfilequerydefine");
		dataModel.setQueryName("esrelationfilequerydefine_search");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		userDefinedSearchCase.put("keyword", key);
		userDefinedSearchCase.put("type", String.valueOf(type));
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryData(iStart, iEnd);	
		//ArrayList<HashMap<String, String>> dbData1 = dataModel.getYsViewData()
		if ( iEnd > dataModel.getYsViewData().size()){
			
			iEnd = dataModel.getYsViewData().size();
			
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;		

	}

	public EsRelationFileModel doAddInit(HttpServletRequest request, int type) {

		EsRelationFileModel model = new EsRelationFileModel();

		try {
			String key = request.getParameter("key");
			model.setEsId(key);
			model.setType(String.valueOf(type));
			model.setEndInfoMap("098", "0001", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	
	}

	public EsRelationFileModel doAdd(HttpServletRequest request, String data, UserInfo userInfo) {

		EsRelationFileModel model = new EsRelationFileModel();
		try {
			B_ESRelationFileDao dao = new B_ESRelationFileDao();
			B_ESRelationFileData dbData = new B_ESRelationFileData();

			String guid = BaseDAO.getGuId();
			dbData.setId(guid);
			dbData.setEsid(getJsonData(data, "esId"));
			dbData.setType(getJsonData(data, "type"));
			dbData.setTitle(getJsonData(data, "title"));
			dbData.setFilename(getJsonData(data, "fileName"));
			dbData.setPath(getJsonData(data, "path"));
			dbData.setMemo(getJsonData(data, "memo"));
			
			dbData = updateModifyInfo(dbData, userInfo);
			dao.Create(dbData);
			model.setEndInfoMap(NORMAL, "", guid);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}	
	
	public EsRelationFileModel doUpdate(HttpServletRequest request, String data, UserInfo userInfo) {
		EsRelationFileModel model = new EsRelationFileModel();
		String id = getJsonData(data, "keyBackup");
		
		try {
			B_ESRelationFileDao dao = new B_ESRelationFileDao();
			B_ESRelationFileData dbData = new B_ESRelationFileData();
			
			dbData.setId(getJsonData(data, "keyBackup"));
			dbData = (B_ESRelationFileData)dao.FindByPrimaryKey(dbData);
			
			dbData.setTitle(getJsonData(data, "title"));
			dbData.setFilename(getJsonData(data, "fileName"));
			dbData.setPath(getJsonData(data, "path"));
			dbData.setMemo(getJsonData(data, "memo"));
			
			dbData = updateModifyInfo(dbData, userInfo);
			dao.Store(dbData);
			model.setEndInfoMap(NORMAL, "", id);
		}
		catch(Exception e) {
			model.setEndInfoMap(SYSTEMERROR, "err001", id);
		}
		
		return model;
	}
	
	public EsRelationFileModel doDelete(String data, UserInfo userInfo){
		
		EsRelationFileModel model = new EsRelationFileModel();
		
		try {
			BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();
	        
	        bean.executeESRelationFileDelete(data, userInfo);

	        model.setEndInfoMap(NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(SYSTEMERROR, "err001", "");
		}
		
		return model;
	}
	
	public static B_ESRelationFileData updateModifyInfo(B_ESRelationFileData data, UserInfo userInfo) {
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
	
	public EsRelationFileModel getEsRelationFileDetailInfo(String key) throws Exception {
		EsRelationFileModel model = new EsRelationFileModel();
		B_ESRelationFileDao dao = new B_ESRelationFileDao();
		B_ESRelationFileData dbData = new B_ESRelationFileData();
		dbData.setId(key);
		dbData = (B_ESRelationFileData)dao.FindByPrimaryKey(dbData);
		model.setEsRelationFileData(dbData);
		model.setEsId(dbData.getEsid());
		
		model.setEndInfoMap("098", "0001", "");
		model.setKeyBackup(dbData.getId());
		
		return model;
	}

}
