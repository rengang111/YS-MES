package com.ys.system.service.dic;

import org.springframework.stereotype.Service;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.dic.DicTypeModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICTYPEDao;
import com.ys.system.db.data.S_DICTYPEData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basequery.BaseQuery;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class DicTypeService {
 
	public DicTypeModel doSearch(HttpServletRequest request, DicTypeModel dataModel, UserInfo userInfo) throws Exception {
		
		dataModel.setQueryFileName("/dic/dicquerydefine");
		dataModel.setQueryName("dictypequerydefine_search");
		
		dataModel.setDicSelectedFlagList(DicUtil.getGroupValue(DicUtil.DICSELECTEDFLAG));
		dataModel.setDicTypeLevelList(DicUtil.getGroupValue(DicUtil.DICTYPELEVEL));
		
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.getQueryData();
				
		return dataModel;
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
	
	public void doAdd(HttpServletRequest request, DicTypeModel dicTypeModel, UserInfo userInfo) throws Exception {
		S_DICTYPEDao dao = new S_DICTYPEDao();
		
		S_DICTYPEData data = dicTypeModel.getdicTypeData();
		data = updateModifyInfo(dicTypeModel.getdicTypeData(), userInfo);
		data = setEnableFlag(data);
		dao.Create(data);
	}	
	
	public void doUpdate(HttpServletRequest request, DicTypeModel dicTypeModel, UserInfo userInfo) throws Exception {
		String updateSrcDicTypeId = request.getParameter("dicTypeId");
		dicTypeModel.setdicTypeData(setEnableFlag(dicTypeModel.getdicTypeData()));
		
		DbUpdateEjb bean = new DbUpdateEjb();
                
        bean.executeDicTypeUpdate(dicTypeModel, updateSrcDicTypeId, userInfo);
	}
	
	public void doDelete(DicTypeModel DicTypeModel, UserInfo userInfo) throws Exception {
		
        DbUpdateEjb bean = new DbUpdateEjb();
        
        bean.executeDicTypeDelete(DicTypeModel, userInfo);

	}
	
	public int preCheck(HttpServletRequest request, String operType) throws Exception {
		S_DICTYPEDao dao = new S_DICTYPEDao();
		S_DICTYPEData data = new S_DICTYPEData();
		
		int result = 0;
		boolean isExists = false;
		
		try {

			data.setDictypeid(request.getParameter("dicTypeData.dictypeid"));
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
			String updateSrcDicTypeId = request.getParameter("dicTypeId");
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
