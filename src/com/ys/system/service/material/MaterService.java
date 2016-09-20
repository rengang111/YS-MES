package com.ys.system.service.material;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.unit.UnitModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.system.db.dao.S_DEPTDao;
import com.ys.system.db.data.S_DEPTData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class MaterService {
 
	public UnitModel doSearch(HttpServletRequest request, UnitModel dataModel, UserInfo userInfo) throws Exception {
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/unit/unitquerydefine");
		dataModel.setQueryName("unitquerydefine_search");
		
		dataModel.setUnitPropertyList(DicUtil.getGroupValue(DicUtil.UNITPROPERTY));
		dataModel.setUnitTypeList(DicUtil.getGroupValue(DicUtil.UNITTYPE));
		
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		if (userInfo.isSA()) {
			userDefinedSearchCase.put("userUnitId", "");
		} else {
			userDefinedSearchCase.put("userUnitId", userInfo.getUnitId());
		}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getQueryData();
		
		dataModel.setUserUnitId(userInfo.getUnitId());
		
		return dataModel;
	}

	public UnitModel getDetail(HttpServletRequest request) throws Exception {
		String unitId = request.getParameter("unitId");
		UnitModel unitModel = new UnitModel();
		
		unitModel.setUnitPropertyList(DicUtil.getGroupValue(DicUtil.UNITPROPERTY));
		unitModel.setUnitTypeList(DicUtil.getGroupValue(DicUtil.UNITTYPE));
		
		S_DEPTDao dao = new S_DEPTDao();
		S_DEPTData data = new S_DEPTData();
		data.setUnitid(unitId);
		data = (S_DEPTData)dao.FindByPrimaryKey(data);
		
		unitModel.setunitData(data);
		
		if (data.getParentid() != null && !data.getParentid().equals("")) {
			S_DEPTData parentData = new S_DEPTData();
			parentData.setUnitid(data.getParentid());
			parentData = (S_DEPTData)dao.FindByPrimaryKey(parentData);
			
			unitModel.setParentUnitName(parentData.getUnitname());
			unitModel.setParentUnitId(data.getParentid());
		}
		String addressCode[] = data.getAddresscode().split("-");
		String address = "";
		for(int i = 0; i < addressCode.length; i++) {
			if (i == 0) {
				address += DicUtil.getCodeValue(DicUtil.ADDRESS + addressCode[i]);
			} else {
				address += DicUtil.getCodeValue(DicUtil.ADDRESS + addressCode[i]);
			}
		}
		unitModel.setAddress(address);
		unitModel.setIsOnlyView("T");
		
		return unitModel;
	
	}

	public UnitModel getParentDeptName(HttpServletRequest request) throws Exception {
		
		String unitId = request.getParameter("unitId");
		
		UnitModel unitModel = new UnitModel();
		S_DEPTData parentData = new S_DEPTData();
		S_DEPTDao dao = new S_DEPTDao();
		
		parentData.setUnitid(unitId);
		parentData = (S_DEPTData)dao.FindByPrimaryKey(parentData);
		unitModel.setParentUnitId(unitId);
		unitModel.setParentUnitName(parentData.getUnitname());
		
		return unitModel;
	
	}
	
	public void doAdd(HttpServletRequest request, UnitModel unitModel, UserInfo userInfo) throws Exception {
		S_DEPTDao dao = new S_DEPTDao();
		
		S_DEPTData data = unitModel.getunitData();
		data = updateModifyInfo(unitModel.getunitData(), userInfo);
		
		data = storeDeptGuid(request, unitModel, data);
		data.setUnitid(getNewUnitId(unitModel, userInfo));
		
		dao.Create(data);
	}	
	
	public void doUpdate(HttpServletRequest request, UnitModel unitModel, UserInfo userInfo) throws Exception {
		S_DEPTDao dao = new S_DEPTDao();
		S_DEPTData data = updateModifyInfo(unitModel.getunitData(), userInfo);
		data = storeDeptGuid(request, unitModel, data);
		dao.Store(data);
	}
	
	public void doDelete(UnitModel UnitModel, UserInfo userInfo) throws Exception {
		

		DbUpdateEjb bean = new DbUpdateEjb();
        
        bean.executeUnitDelete(UnitModel, userInfo);
        
	}
	
	public int preCheck(HttpServletRequest request, String operType, String parentUnitId, String userUnitId, String userType) throws Exception {
		
		int result = 0;
		
		UnitModel unitModel = new UnitModel();
		
		unitModel.setQueryFileName("/unit/unitquerydefine");
		unitModel.setQueryName("unitquerydefine_getparentdeptguid");
		
		BaseQuery baseQuery = new BaseQuery(request, unitModel);
		ArrayList<ArrayList<String>> unitDataList = baseQuery.getQueryData();
		//父单位是否存在
		if (unitDataList.size() == 0) {
			if (!parentUnitId.equals("")) {
				result = 1;
			}
		} else {
			if (!userType.equals(Constants.USER_SA)) {
				String parentId = unitDataList.get(0).get(1);
				if (parentId.length() >= userUnitId.length()) {
					if (!parentId.substring(0, userUnitId.length()).equals(userUnitId)) {
						//父单位无权限
						result = 2;						
					}
				} else {
					//父单位无权限
					result = 2;
				}
			}
			
			//单位名称是否存在
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			userDefinedSearchCase.put("unitIdName", request.getParameter("unitData.unitname"));
			userDefinedSearchCase.put("userUnitId", userUnitId);
			unitModel.setQueryFileName("/unit/unitquerydefine");
			unitModel.setQueryName("unitquerydefine_confirmunitname");
			baseQuery = new BaseQuery(request, unitModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			unitDataList = baseQuery.getQueryData();
			if (operType.equals("add") || operType.equals("addsub")) {
				if (unitDataList.size() > 0) {
					//单位名称已存在
					result = 3;
				}
			} else {
				if (unitDataList.size() == 0) {
					//单位名称不存在
					//result = 4;
				} else {
					String updateSrcUnitId = request.getParameter("unitData.unitid");
					if (!unitDataList.get(0).get(1).equals(updateSrcUnitId)) {
						//单位名称已存在
						result = 3;
					}
				}
			}
		}
		
		return result;
	}
	
	public static S_DEPTData updateModifyInfo(S_DEPTData data, UserInfo userInfo) {
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
	
	private S_DEPTData storeDeptGuid(HttpServletRequest request, UnitModel unitModel, S_DEPTData data) throws Exception {
		if (unitModel.getParentUnitName().equals("")) {
			data.setDeptguid(BaseDAO.getGuId());
			data.setParentid("");
		} else {
			unitModel.setQueryFileName("/unit/unitquerydefine");
			unitModel.setQueryName("unitquerydefine_getparentdeptguid");
			
			BaseQuery baseQuery = new BaseQuery(request, unitModel);
			ArrayList<ArrayList<String>> unitDataList = baseQuery.getQueryData();
			data.setParentid(unitDataList.get(0).get(1));
			data.setDeptguid(unitDataList.get(0).get(2));
		}
		
		return data;
	}
	
	private String getNewUnitId(UnitModel unitModel, UserInfo userInfo) throws Exception {

		String newDeptId = "";
		String parentUnitId = unitModel.getParentUnitId();
		
		if (parentUnitId.equals("")) {
			if (!userInfo.getUserType().equals(Constants.USER_SA)) {
				parentUnitId = userInfo.getUnitId();
			}
		}
		
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT CONCAT('" + parentUnitId + "', LPAD(A.numUnit + 1, " + BusinessConstants.UNITIDLENGTH + ", '0')) as newunitid ");
		sql.append("FROM ");
		sql.append("(select unitid, cast(substr(unitid, if(length('" + parentUnitId + "')=0, 1, length('" + parentUnitId + "') + 1), if(length('" + parentUnitId + "')=0, " + BusinessConstants.UNITIDLENGTH + ", length('" + parentUnitId + "'))) as SIGNED) as numUnit ");
		sql.append("from s_dept as a ");
		sql.append("where a.unitid like '%') as A ");
		sql.append("where (A.numUnit + 1) NOT IN ");
		sql.append("(select cast(substr(unitid, if(length('" + parentUnitId + "')=0, 1, length('" + parentUnitId + "') + 1), if(length('" + parentUnitId + "')=0, " + BusinessConstants.UNITIDLENGTH + ", length('" + parentUnitId + "'))) as SIGNED) as numUnit ");
		sql.append("from s_dept as a ");
		sql.append("where a.unitid like '%') ");
		sql.append("order by newunitid");
		ArrayList<ArrayList<String>> result = BaseDAO.execSQL(sql.toString());
		
		if (result.size() == 0) {
			newDeptId = String.format("%0" + BusinessConstants.UNITIDLENGTH + "d", 1);
		} else {
			newDeptId = result.get(0).get(0);
		}
		
		return newDeptId;
	}
}
