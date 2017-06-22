package com.ys.system.service.dic;

import org.springframework.stereotype.Service;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.action.model.dic.DicModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.dao.S_DICTYPEDao;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.db.data.S_DICTYPEData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DesUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class DicService extends BaseService {
 
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
		dataModel.setQueryName("dicquerydefine_search");
		
		String key1 = getJsonData(data, "dicIdName");
		String key2 = getJsonData(data, "dicTypeIdName");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);

		userDefinedSearchCase.put("dicIdName", key1);
		userDefinedSearchCase.put("dicTypeIdName", key2);
		
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
	
	public DicModel doUpdate(HttpServletRequest request, String formData, UserInfo userInfo, boolean isAdd) {
		S_DICDao dao = new S_DICDao();
		S_DICData data = new S_DICData();
		DicModel model = new DicModel();
		
		try {
			String dicId = getJsonData(formData, "dicId");
			String dictypeid = getJsonData(formData, "dictypeid");
			String dicid = getJsonData(formData, "dicid");
			String dicname = getJsonData(formData, "dicname");
			String jianpin = getJsonData(formData, "jianpin");
			String dicdes = getJsonData(formData, "dicdes");
			String dicprarentid = getJsonData(formData, "dicprarentid");
			String isleaf = getJsonData(formData, "isleaf");
			String sortno = getJsonData(formData, "sortno");
			String enableflag = getJsonData(formData, "enableflag");
			
			if (isAdd) {

				dicId = dicid;
				data.setDicid(dicId);
			} else {
				dicId = dicid;
				data.setDicid(dicId);
				data.setDictypeid(dictypeid);
				data = (S_DICData)dao.FindByPrimaryKey(data);
			}
			data.setDictypeid(dictypeid);
			data.setDicname(dicname);
			data.setJianpin(jianpin);
			data.setDicdes(dicdes);
			data.setDicprarentid(dicprarentid);
			data.setIsleaf(isleaf);

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
				dao.Store(data);
			}
			
			model.setEndInfoMap(BaseService.NORMAL, "", dicId);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;
	}
	
	public DicModel doDelete(HttpServletRequest request, String formData, UserInfo userInfo) {
		

		DbUpdateEjb bean = new DbUpdateEjb();
		DicModel model = new DicModel();
		
		try {
			bean.executeDicDelete(formData, userInfo);
			model.setEndInfoMap(BaseService.NORMAL, "", "");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.setEndInfoMap(BaseService.SYSTEMERROR, "", "");
		}
		
		return model;

        
        //bean.executeDicDelete(DicModel, userInfo);

	}
	
	public int preCheck(String formData, String operType) throws Exception {
		S_DICDao dao = new S_DICDao();
		S_DICData data = new S_DICData();

		int result = 0;
		boolean isExists = false;
		
		String dicid = getJsonData(formData, "dicid");
		String dictypeid = getJsonData(formData, "dictypeid");
		
		try {
			data.setDictypeid(dictypeid);
			data.setDicid(dicid);
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
			String updateSrcDicId = getJsonData(formData, "dicId");;
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
