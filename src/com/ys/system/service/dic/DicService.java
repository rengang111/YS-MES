package com.ys.system.service.dic;

import org.springframework.stereotype.Service;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.dic.DicModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.dao.S_DICTYPEDao;

import com.ys.system.db.data.S_DICData;
import com.ys.system.db.data.S_DICTYPEData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.util.CalendarUtil;
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class DicService {
 
	public DicModel doSearch(HttpServletRequest request, DicModel dataModel, UserInfo userInfo) throws Exception {
		
		dataModel.setQueryFileName("/dic/dicquerydefine");
		dataModel.setQueryName("dicquerydefine_search");

		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		baseQuery.getQueryData();
		
		return dataModel;
	}

	public DicModel getDetail(HttpServletRequest request) throws Exception {
		String dicId = request.getParameter("dicCodeId");
		String dicTypeId = request.getParameter("dicTypeId");
		DicModel dicModel = new DicModel();
		
		S_DICDao dao = new S_DICDao();
		S_DICData data = new S_DICData();
		data.setDicid(dicId);
		data.setDictypeid(dicTypeId);
		data = (S_DICData)dao.FindByPrimaryKey(data);
		dicModel.setdicData(data);
		
		S_DICTYPEDao typeDao = new S_DICTYPEDao();
		S_DICTYPEData typeData = new S_DICTYPEData();
		typeData.setDictypeid(data.getDictypeid());
		typeData = (S_DICTYPEData)typeDao.FindByPrimaryKey(typeData);		
		dicModel.setDicTypeName(typeData.getDictypename());
		
		if (data.getDicprarentid() != null && !data.getDicprarentid().equals("")) {
			S_DICData parentData = new S_DICData();
			parentData.setDicid(data.getDicprarentid());
			parentData.setDictypeid(data.getDictypeid());
			data = (S_DICData)dao.FindByPrimaryKey(parentData);
			dicModel.setDicParentName(data.getDicname());	
		}
		
		dicModel.setIsOnlyView("T");
		
		return dicModel;
	
	}
	
	public void doAdd(HttpServletRequest request, DicModel dicModel, UserInfo userInfo) throws Exception {
		S_DICDao dao = new S_DICDao();
		
		S_DICData data = dicModel.getdicData();
		data = updateModifyInfo(dicModel.getdicData(), userInfo);
		data = setEnableFlag(data);
		dao.Create(data);
	}	
	
	public void doUpdate(HttpServletRequest request, DicModel dicModel, UserInfo userInfo) throws Exception {
		S_DICDao dao = new S_DICDao();
		S_DICData data = updateModifyInfo(dicModel.getdicData(), userInfo);
		data = setEnableFlag(data);
		dao.Store(data);
	}
	
	public void doDelete(HttpServletRequest request, DicModel DicModel, UserInfo userInfo) throws Exception {
		
		DbUpdateEjb bean = new DbUpdateEjb();
        
        bean.executeDicDelete(DicModel, userInfo);

	}
	
	public int preCheck(HttpServletRequest request, String operType) throws Exception {
		S_DICDao dao = new S_DICDao();
		S_DICData data = new S_DICData();
		String dicTypeId = "";
		int result = 0;
		boolean isExists = false;
		
		try {
			dicTypeId = request.getParameter("dicData.dictypeid");
			data.setDictypeid(dicTypeId);
			data.setDicid(request.getParameter("dicData.dicid"));
			data = (S_DICData)dao.FindByPrimaryKey(data);

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
			String updateSrcDicId = request.getParameter("dicId");
			if (isExists) {
				if (!data.getDicid().equals(updateSrcDicId)) {
					//dicid已存在
					result = 2;
				}
			}
		}
		
		return result;
	}
	
	public static S_DICData updateModifyInfo(S_DICData data, UserInfo userInfo) {
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
	
	private S_DICData setEnableFlag(S_DICData data) {
		
		if (data.getEnableflag() == null || data.getEnableflag().equals("")) {
			data.setEnableflag("1");
		}
		
		return data;
	}
}
