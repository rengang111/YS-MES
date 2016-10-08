package com.ys.business.service.material;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.business.action.model.material.MatClassModel;
import com.ys.business.db.dao.B_MaterialClassDao;
import com.ys.business.db.data.B_MaterialClassData;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.BaseModel;
import com.ys.util.basequery.common.Constants;
import com.ys.system.db.dao.S_DEPTDao;
import com.ys.system.db.data.S_DEPTData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.system.interceptor.CommonDept;
import com.ys.system.interceptor.DBAccess;
import com.ys.system.interceptor.DicInfo;
import com.ys.system.service.common.BaseService;
import com.ys.util.CalendarUtil;
import com.ys.util.DicUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basequery.BaseQuery;
import com.ys.business.ejb.BusinessDbUpdateEjb;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class MatClassService extends BaseService {

	public MatClassModel doSearch(HttpServletRequest request, MatClassModel dataModel, UserInfo userInfo)
			throws Exception {

		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/business/material/matclassquerydefine");
		dataModel.setQueryName("matclassquerydefine_search");


		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		//if (userInfo.isSA()) {
			userDefinedSearchCase.put("categoryId", "");
		//} else {
		//	userDefinedSearchCase.put("userUnitId", userInfo.getUnitId());
		//}
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		baseQuery.getYsQueryDataList(0, 0);

		

		return dataModel;
	}

	public MatClassModel getDetail(HttpServletRequest request) throws Exception {
		String recordId = request.getParameter("recordId");
		MatClassModel MatClassModel = new MatClassModel();


		B_MaterialClassDao dao = new B_MaterialClassDao();
		B_MaterialClassData data = new B_MaterialClassData();
		data.setRecordid(recordId);
		data = (B_MaterialClassData) dao.FindByPrimaryKey(data);

		MatClassModel.setunitData(data);

		MatClassModel.setIsOnlyView("T");

		return MatClassModel;

	}

	public MatClassModel getParentDeptName(HttpServletRequest request) throws Exception {

		String parentId = request.getParameter("categoryId");
		String parentName = request.getParameter("categoryName");
		parentName = new String(parentName.getBytes("ISO8859-1"), "UTF-8");

		MatClassModel MatClassModel = new MatClassModel();
		MatClassModel.setParentCategoryId(parentId);
		MatClassModel.setParentCategoryName(parentName);

		return MatClassModel;

	}

	public void insert(HttpServletRequest request, 
			MatClassModel MatClassModel,
			UserInfo userInfo) throws Exception {
		
		B_MaterialClassDao dao = new B_MaterialClassDao();
		B_MaterialClassData data = eidtChildId(MatClassModel.getunitData());
		
		data = updateModifyInfo(data,
				userInfo,"MaterialCategroyAdd",
				Constants.ACCESSTYPE_INS);

		data.setRecordid(BaseDAO.getGuId());
		data.setParentid(request.getParameter("parentCategoryId"));

		dao.Create(data);
	}
	
	private B_MaterialClassData eidtChildId (B_MaterialClassData data){
		
		String categroyId = data.getCategoryid()+"";
		String parentId=data.getParentid()+"";
		
		if (parentId.equals(Constants.MATERIALCATEGORY)){
			data.setChildid(categroyId);
		}else{		
			if(categroyId.length() !=0 && parentId.length() !=0)
			{
				String childid = categroyId.substring(parentId.length());
				int inChar=childid.indexOf(".");
					if(inChar > -1)
					{
						childid = childid.substring(inChar + 1);
					}
					data.setChildid(childid);
					
				}			
		}
		return data;
	}
	
	public void update(HttpServletRequest request, 
			MatClassModel MatClassModel, 
			UserInfo userInfo) throws Exception {
		
		B_MaterialClassDao dao = new B_MaterialClassDao();
		B_MaterialClassData data = eidtChildId(MatClassModel.getunitData());
		
		updateModifyInfo(data, 
				userInfo,"MaterialCategroyUpdate",
				Constants.ACCESSTYPE_UPD);

		dao.Store(data);
	}

	public void delete(MatClassModel MatClassModel, UserInfo userInfo) throws Exception {

		BusinessDbUpdateEjb bean = new BusinessDbUpdateEjb();

		bean.executeMaterialCategroyDelete(MatClassModel.getNumCheck(), userInfo);

	}

	public int preCheck(HttpServletRequest request, String operType, String parentUnitId, String userUnitId,
			String userType) throws Exception {

		int result = 0;

		MatClassModel MatClassModel = new MatClassModel();

		MatClassModel.setQueryFileName("/business/material/matclassquerydefine");
		MatClassModel.setQueryName("unitquerydefine_getparentdeptguid");

		BaseQuery baseQuery = new BaseQuery(request, MatClassModel);
		ArrayList<ArrayList<String>> unitDataList = baseQuery.getQueryData();
		// 父单位是否存在
		if (unitDataList.size() == 0) {
			if (!parentUnitId.equals("")) {
				result = 1;
			}
		} else {
			if (!userType.equals(Constants.USER_SA)) {
				String parentId = unitDataList.get(0).get(1);
				if (parentId.length() >= userUnitId.length()) {
					if (!parentId.substring(0, userUnitId.length()).equals(userUnitId)) {
						// 父单位无权限
						result = 2;
					}
				} else {
					// 父单位无权限
					result = 2;
				}
			}

			// 单位名称是否存在
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			userDefinedSearchCase.put("unitIdName", request.getParameter("unitData.unitname"));
			userDefinedSearchCase.put("userUnitId", userUnitId);
			MatClassModel.setQueryFileName("/business/material/matclassquerydefine");
			MatClassModel.setQueryName("unitquerydefine_confirmunitname");
			baseQuery = new BaseQuery(request, MatClassModel);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			unitDataList = baseQuery.getQueryData();
			if (operType.equals("add") || operType.equals("addsub")) {
				if (unitDataList.size() > 0) {
					// 单位名称已存在
					result = 3;
				}
			} else {
				if (unitDataList.size() == 0) {
					// 单位名称不存在
					// result = 4;
				} else {
					String updateSrcUnitId = request.getParameter("unitData.unitid");
					if (!unitDataList.get(0).get(1).equals(updateSrcUnitId)) {
						// 单位名称已存在
						result = 3;
					}
				}
			}
		}

		return result;
	}

	public static B_MaterialClassData updateModifyInfo(B_MaterialClassData data,
			UserInfo userInfo,
			String method,int type) {

		data.setFormid(method);
		
		if (type == Constants.ACCESSTYPE_INS) {//insert
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateunitid(userInfo.getUnitId());

		}else{
			data.setModifyperson(userInfo.getUserId());
			data.setModifytime(CalendarUtil.fmtDate());
			
		}
		
		if (type == Constants.ACCESSTYPE_DEL) {//delete
			data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
		}else{
			data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
			
		}

		return data;
	}

	
	private String getNewUnitId(MatClassModel MatClassModel, UserInfo userInfo) throws Exception {

		String newDeptId = "";
		String parentUnitId = MatClassModel.getParentCategoryId();

		if (parentUnitId.equals("")) {
			if (!userInfo.getUserType().equals(Constants.USER_SA)) {
				parentUnitId = userInfo.getUnitId();
			}
		}

		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT CONCAT('" + parentUnitId + "', LPAD(A.numUnit + 1, " + BusinessConstants.UNITIDLENGTH
				+ ", '0')) as newunitid ");
		sql.append("FROM ");
		sql.append("(select unitid, cast(substr(unitid, if(length('" + parentUnitId + "')=0, 1, length('" + parentUnitId
				+ "') + 1), if(length('" + parentUnitId + "')=0, " + BusinessConstants.UNITIDLENGTH + ", length('"
				+ parentUnitId + "'))) as SIGNED) as numUnit ");
		sql.append("from s_dept as a ");
		sql.append("where a.unitid like '%') as A ");
		sql.append("where (A.numUnit + 1) NOT IN ");
		sql.append("(select cast(substr(unitid, if(length('" + parentUnitId + "')=0, 1, length('" + parentUnitId
				+ "') + 1), if(length('" + parentUnitId + "')=0, " + BusinessConstants.UNITIDLENGTH + ", length('"
				+ parentUnitId + "'))) as SIGNED) as numUnit ");
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

	public ArrayList<DicInfo> getInitMaterial(HttpServletRequest request, String userId, String menuId,
			String unitId, String userType) throws Exception {

		ArrayList<ArrayList<DicInfo>> deptChain = InitMaterial(request, userId, menuId, unitId, userType);

		return CommonDept.sortDept(deptChain);
	}

	private ArrayList<ArrayList<DicInfo>> InitMaterial(HttpServletRequest request, String userId, String menuId,
			String unitId, String userType) throws Exception {
		ArrayList<ArrayList<DicInfo>> deptChain = new ArrayList<ArrayList<DicInfo>>();
		ArrayList<ArrayList<String>> dbResult = new ArrayList<ArrayList<String>>();
		HashMap<String, DicInfo> deptMap = new HashMap<String, DicInfo>();

		/*
		 * isFilter = Boolean.parseBoolean(BaseQuery.getContent(Constants.
		 * SYSTEMPROPERTYFILENAME, Constants.FILTERDEFINE)); String
		 * adminRoleList =
		 * BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME,
		 * Constants.FILTERADMIN);
		 * 
		 * int adminRoleidCount = BaseQuery.getAdminRoleidCount(request, userId,
		 * adminRoleList); if (adminRoleidCount == 0) { adminRoleList =
		 * Constants.BUSINESSROLE; }
		 * 
		 * if (userType.equals(Constants.USER_SA)) { isFilter = false; }
		 */
		dbResult = getAllMaterial(request, userId, menuId, unitId, userType);
		/*for (ArrayList<String> rowData : dbResult) {
			DicInfo deptInfo = new DicInfo();
			deptInfo.setId(rowData.get(0));
			deptInfo.setName(rowData.get(1));
			deptInfo.setDes(rowData.get(2));
			deptInfo.setJianpin(rowData.get(3));
			deptInfo.setParentId(rowData.get(4));
			deptInfo.setDeptGuid(rowData.get(5));
			deptInfo.setSortNo(rowData.get(6));
			deptMap.put(deptInfo.getId(), deptInfo);
		}
*/
		for (ArrayList<String> rowData : dbResult) {
			ArrayList<DicInfo> deptSubChain = new ArrayList<DicInfo>();

			DicInfo deptInfo = new DicInfo();
			deptInfo.setId(rowData.get(0));
			deptInfo.setName(rowData.get(1));
			deptInfo.setDes(rowData.get(2));
			//deptInfo.setJianpin(rowData.get(3));
			deptInfo.setParentId(rowData.get(3));
			deptInfo.setDeptGuid(rowData.get(4));
			//deptInfo.setSortNo(rowData.get(6));

			// getDeptChain(deptInfo, deptSubChain, dbResult, deptMap);
			deptSubChain.add(deptInfo);
			deptChain.add(deptSubChain);
		}

		return deptChain;
	}

	public ArrayList<ArrayList<String>> getAllMaterial(HttpServletRequest request, String userId, String menuId,
			String unitId, String userType) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		BaseQuery baseQuery;
		BaseModel baseModel = new BaseModel();
		//HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();

		baseModel.setQueryFileName("/business/material/matclassquerydefine");
		baseModel.setQueryName("mainframequery_getallmaterials");
		baseQuery = new BaseQuery(request, baseModel);
		// if (userType.equals(Constants.USER_ADMIN)) {
		// userDefinedSearchCase.put("unitId", unitId);
		// }

		//baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		result = baseQuery.getFullData();

		return result;
	}

	public ArrayList<DicInfo> launchMaterial(HttpServletRequest request, String userId, String menuId, String unitId,
			String userType) throws Exception {

		ArrayList<DicInfo> leafDept = new ArrayList<DicInfo>();
		ArrayList<ArrayList<String>> dbResult = new ArrayList<ArrayList<String>>();
		String adminRoleList = BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME, Constants.FILTERADMIN);
		// 是否需要过滤admin
		// isFilter =
		// Boolean.parseBoolean(BaseQuery.getContent(Constants.SYSTEMPROPERTYFILENAME,
		// Constants.FILTERDEFINE));
		//int adminRoleidCount = BaseQuery.getAdminRoleidCount(request, userId, adminRoleList);
		// if (adminRoleidCount == 0) {
		// 普通用户
		// adminRoleList = Constants.BUSINESSROLE;
		// }

		// if (userType.equals(Constants.USER_SA)) {
		// isFilter = false;
		// }

		dbResult = getLeafDept(request);

		for (ArrayList<String> rowData : dbResult) {
			DicInfo deptInfo = new DicInfo();
			deptInfo.setId(rowData.get(0));
			deptInfo.setName(rowData.get(1));
			deptInfo.setDes(rowData.get(2));
			//deptInfo.setJianpin(rowData.get(3));
			deptInfo.setParentId(rowData.get(3));
			deptInfo.setDeptGuid(rowData.get(4));

			leafDept.add(deptInfo);
		}

		return leafDept;
	}

	public ArrayList<ArrayList<String>> getLeafDept(HttpServletRequest request) throws Exception {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		BaseQuery baseQuery;
		BaseModel baseModel = new BaseModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();

		baseModel.setQueryFileName("/business/material/matclassquerydefine");
		baseModel.setQueryName("mainframequery_getleafdept_admin");
		baseQuery = new BaseQuery(request, baseModel);
		//userDefinedSearchCase.put("parentid", unitId);

		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);

		result = baseQuery.getFullData();

		return result;

	}
}
