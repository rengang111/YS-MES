package com.ys.system.service.dic;

import org.springframework.stereotype.Service;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.action.model.dic.DicModel;
import com.ys.system.action.model.dic.DicTypeModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.dao.S_DICTYPEDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.db.data.S_DICTYPEData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class DicTypeService extends BaseService {
 
	public DicTypeModel doSearch(HttpServletRequest request, DicTypeModel dataModel, UserInfo userInfo) throws Exception {
		
		dataModel.setQueryFileName("/dic/dicquerydefine");
		dataModel.setQueryName("dictypequerydefine_search");
		
		dataModel.setDicSelectedFlagList(DicUtil.getGroupValue(DicUtil.DICSELECTEDFLAG));
		dataModel.setDicTypeLevelList(DicUtil.getGroupValue(DicUtil.DICTYPELEVEL));
		
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.getQueryData();
				
		return dataModel;
	}
	
	public HashMap<String, Object> doSearch(HttpServletRequest request, String data, UserInfo userInfo) throws Exception {

		int iStart = 0;
		int iEnd =0;
		String sEcho = "";
		String start = "";
		String length = "";
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		UserModel dataModel = new UserModel();
		
		sEcho = getJsonData(data, "sEcho");	
		start = getJsonData(data, "iDisplayStart");		
		if (start != null && !start.equals("")){
			iStart = Integer.parseInt(start);			
		}
		
		length = getJsonData(data, "iDisplayLength");
		if (length != null && !length.equals("")){			
			iEnd = iStart + Integer.parseInt(length);			
		}		
		
		dataModel.setQueryFileName("/dic/dicquerydefine");
		dataModel.setQueryName("dictypequerydefine_search");
		
		String key = getJsonData(data, "dicTypeIdName");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("dicTypeIdName", key);
		
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		ArrayList<HashMap<String, String>> userDataList = baseQuery.getYsQueryData(iStart, iEnd);	
		
		if ( iEnd > dataModel.getYsViewData().size()){
			iEnd = dataModel.getYsViewData().size();
		}
		
		modelMap.put("sEcho", sEcho); 
		
		modelMap.put("recordsTotal", dataModel.getRecordCount()); 
		
		modelMap.put("recordsFiltered", dataModel.getRecordCount());
		
		modelMap.put("data", dataModel.getYsViewData());
		
		return modelMap;
	}

	public DicTypeModel getDetail(HttpServletRequest request) throws Exception {
		String dicTypeId = request.getParameter("dictypeId");
		DicTypeModel dataModel = new DicTypeModel();
		
		dataModel.setDicSelectedFlagList(DicUtil.getGroupValue(DicUtil.DICSELECTEDFLAG));
		dataModel.setDicTypeLevelList(DicUtil.getGroupValue(DicUtil.DICTYPELEVEL));
		
		S_DICTYPEDao dao = new S_DICTYPEDao();
		S_DICTYPEData data = new S_DICTYPEData();
		data.setDictypeid(dicTypeId);
		data = (S_DICTYPEData)dao.FindByPrimaryKey(data);
		
		dataModel.setdicTypeData(data);
		
		dataModel.setIsOnlyView("T");
		
		return dataModel;
	
	}
	
	public DicTypeModel doUpdate(HttpServletRequest request, String formData, UserInfo userInfo, boolean isAdd) {
		S_DICTYPEDao dao = new S_DICTYPEDao();
		S_DICTYPEData data = new S_DICTYPEData();
		DicTypeModel model = new DicTypeModel();
		
		try {
			String dicTypeId = getJsonData(formData, "dicTypeId");
			String dictypeid = getJsonData(formData, "dictypeid");
			String dictypename = getJsonData(formData, "dictypename");
			String dictypelevel = getJsonData(formData, "dictypelevel");
			String dicselectedflag = getJsonData(formData, "dicselectedflag");
			String sortno = getJsonData(formData, "sortno");
			String enableflag = getJsonData(formData, "enableflag");
			
			if (isAdd) {
				data.setDictypeid(dictypeid);
			} else {
				data.setDictypeid(dictypeid);
				data = (S_DICTYPEData)dao.FindByPrimaryKey(data);
			}

			data.setDictypename(dictypename);
			data.setDictypelevel(dictypelevel);
			data.setDictypelevel(dictypelevel);
			data.setDicselectedflag(dicselectedflag);
			data.setEnableflag(enableflag);
			if (sortno != null && !sortno.equals("")) {
				data.setSortno(Integer.parseInt(sortno));
			} else {
				data.setSortno(0);
			}
		
			data = updateModifyInfo(data, userInfo);
		
			if (isAdd) {
				dao.Create(data);
			} else {
				//dao.Store(data);
				DbUpdateEjb bean = new DbUpdateEjb();
		                
		        bean.executeDicTypeUpdate(data, dicTypeId, userInfo);
			}
			
			model.setEndInfoMap(BaseService.NORMAL, "", dicTypeId);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;
	}
	
	
	public DicTypeModel doDelete(HttpServletRequest request, String formData, UserInfo userInfo) {
		

		DbUpdateEjb bean = new DbUpdateEjb();
		DicTypeModel model = new DicTypeModel();
		
		try {
			bean.executeDicTypeDelete(formData, userInfo);
			model.setEndInfoMap(BaseService.NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;

	}
	
	public int preCheck(String formData, String operType) throws Exception {
		S_DICTYPEDao dao = new S_DICTYPEDao();
		S_DICTYPEData data = new S_DICTYPEData();
		
		int result = 0;
		boolean isExists = false;
		
		try {

			data.setDictypeid(getJsonData(formData, "dictypeid"));
			data = (S_DICTYPEData)dao.FindByPrimaryKey(data);
			isExists = true;
		}
		catch(Exception e) {
			
		}

		if (operType.equals("add")) {
			if (isExists) {
				//dicid已存在
				result = 1;
			}
		} else {
			String updateSrcDicTypeId = getJsonData(formData, "dicTypeId");
			if (isExists) {
				if (!data.getDictypeid().equals(updateSrcDicTypeId)) {
					//dicid已存在
					result = 2;
				}
			}
		}
		
		return result;
	}
	
	public static S_DICTYPEData updateModifyInfo(S_DICTYPEData data, UserInfo userInfo) {
		String createUserId = data.getCreateperson();
		if ( createUserId == null || createUserId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());
			
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	private S_DICTYPEData setEnableFlag(S_DICTYPEData data) {
		
		if (data.getEnableflag() == null || data.getEnableflag().equals("")) {
			data.setEnableflag("1");
		}
		
		return data;
	}
}
