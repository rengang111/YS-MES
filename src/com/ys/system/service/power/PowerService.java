package com.ys.system.service.power;


import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.power.PowerModel;
import com.ys.system.common.BusinessConstants;
import com.ys.util.basequery.common.Constants;
import com.ys.system.db.dao.S_POWERDao;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_POWERData;
import com.ys.system.db.data.S_ROLEData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.ejb.DbUpdateEjb;
import com.ys.system.interceptor.DicInfo;
import com.ys.system.service.common.MakeTreeStyleData;
import com.ys.util.CalendarUtil;
import com.ys.util.basequery.BaseQuery;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

@Service
public class PowerService {
 
	public PowerModel doSearch(HttpServletRequest request, PowerModel dataModel, UserInfo userInfo) throws Exception {
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		dataModel.setQueryFileName("/power/powerquerydefine");
		dataModel.setQueryName("powerquerydefine_search");
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		if (!userInfo.isSA()) {
			userDefinedSearchCase.put("userUnitId", userInfo.getUnitId());
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
		}
		
		baseQuery.getQueryData();
		
		return dataModel;
	}

	public PowerModel getDetail(HttpServletRequest request) throws Exception {
		
		ArrayList<ArrayList<String>> powerDataList;
		PowerModel powerModel = new PowerModel();
		
		powerModel.setQueryFileName("/power/powerquerydefine");
		powerModel.setQueryName("powerquerydefine_getdetail");
		BaseQuery baseQuery = new BaseQuery(request, powerModel);
		powerDataList = baseQuery.getQueryData();

		powerModel.setId(powerDataList.get(0).get(1));
		powerModel.setUnitId(powerDataList.get(0).get(2));
		powerModel.setUserId(powerDataList.get(0).get(3));
		powerModel.setRoleId(powerDataList.get(0).get(4));
		powerModel.setUserName(powerDataList.get(0).get(5));
		powerModel.setRoleName(powerDataList.get(0).get(6));
		powerModel.setPowerType(powerDataList.get(0).get(7));
		
		return powerModel;
	
	}
	
	public int doAdd(HttpServletRequest request, PowerModel powerModel, UserInfo userInfo) throws Exception {
		int rowCount = 0;
		
		DbUpdateEjb bean = new DbUpdateEjb();
        
        rowCount = bean.executePowerAdd(powerModel, userInfo);

        
        return rowCount;
	}	
	
	public void doUpdate(HttpServletRequest request, PowerModel powerModel, UserInfo userInfo) throws Exception {
		S_POWERDao dao = new S_POWERDao();
		S_POWERData data = new S_POWERData();
		
		String id = request.getParameter("id");
		data.setId(id);
		data.setUserid(powerModel.getUnitId());
		data.setRoleid(powerModel.getRoleId());
		data.setUnitid(powerModel.getUnitId());
		data.setPowertype(powerModel.getPowerType());
		data = updateModifyInfo(powerModel.getpowerData(), userInfo);
		data = setDeptGuid(data, powerModel.getUnitId(), userInfo);
		
		try {
			dao.Store(data);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public void doDelete(PowerModel powerModel, UserInfo userInfo) throws Exception {
		
		DbUpdateEjb bean = new DbUpdateEjb();
        
        bean.executePowerDelete(powerModel, userInfo);

	}
	/*
	public int preCheck(HttpServletRequest request) throws Exception {
		
		int result = 0;
		PowerModel powerModel = new PowerModel();
		BaseQuery baseQuery;
		
		String operType = request.getParameter("operType");
		
		String userIdPara = request.getParameter("userId");
		String roleIdPara = request.getParameter("roleId");
		String unitIdPara = request.getParameter("unitId");
		
		String userIdArray[] = userIdPara.split(",");
		String roleIdArray[] = roleIdPara.split(",");
		String unitIdArray[] = unitIdPara.split(",");

		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		powerModel.setQueryFileName("/power/powerquerydefine");
		powerModel.setQueryName("userquerydefine_checkkey");		
		for(String userId:userIdArray) {
			for(String roleId:roleIdArray ) {
				for(String unitId:unitIdArray ) {
					baseQuery = new BaseQuery(request, powerModel);
					userDefinedSearchCase.put("userId", userId);
					userDefinedSearchCase.put("roleId", roleId);
					userDefinedSearchCase.put("unitId", unitId);
					baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);				
					ArrayList<ArrayList<String>> powerDataList = baseQuery.getQueryData();
					if (powerDataList.size() > 0) {
						//权限已存在
						result = 1;
						break;
					}
				}
				if (result == 1) {
					break;
				}
			}
			if (result == 1) {
				break;
			}
		}
		
		return result;
	}
	*/
	/*
		单一用户，角色中有admin，单位：用户所属单位
		多用户，角色中有admin，用户是同一单位，单位：用户所属单位
		单一用户，角色中无admin，单位：操作者单位及子单位
		多用户，角色中无admin，单位：操作者单位及子单位
		多用户，角色中有admin，用户不是同一单位，单位:无法选择，无法继续操作
	*/
	public String getUnitList(HttpServletRequest request, String userIdList, String roleIdList, UserInfo userInfo) {
		String json = "";
		ArrayList<ArrayList<String>> deptList = new ArrayList<ArrayList<String>>();
		PowerModel powerModel = new PowerModel();
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();	
		boolean isAdmin = false;
		boolean isSameUnit = true;
		boolean isOneUser = false;
		S_ROLEDao roleDao = new S_ROLEDao();
		S_USERDao userDao = new S_USERDao();
		String userIdArray[] = userIdList.split(",");
		String roleIdArray[] = roleIdList.split(",");
			
		try {
			//判断是否单一用户
			if (userIdArray.length == 1) {
				isOneUser = true;
			}
			
			//判断角色中是否有admin角色
			for(String roleId:roleIdArray) {
				S_ROLEData data = new S_ROLEData();
				data.setRoleid(roleId);
				data = (S_ROLEData)roleDao.FindByPrimaryKey(data);
				if (data.getRoletype().equals(Integer.valueOf(Constants.USER_ADMIN))) {
					isAdmin = true;
					break;
				}
			}
			
			if (!isOneUser && isAdmin) {
				//判断用户单位是否相同
				String unitIdBackup = "";
				for(String userId:userIdArray) {
					S_USERData data = new S_USERData();
					data.setUserid(userId);
					data = (S_USERData)userDao.FindByPrimaryKey(data);
					if (unitIdBackup.equals("")) {
						unitIdBackup = data.getUnitid();
					} else {
						if (!data.getUnitid().equals(unitIdBackup)) {
							isSameUnit = false;
							break;
						}
					}
				}
			}

			//用户所属单位
			if ((isOneUser && isAdmin) || (!isOneUser && isAdmin && isSameUnit)) {
				powerModel.setQueryFileName("/unit/unitquerydefine");
				powerModel.setQueryName("unitquerydefine_getunit");
				BaseQuery baseQuery = new BaseQuery(request, powerModel);				
				userDefinedSearchCase.put("userId", userIdArray[0]);
				baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				deptList = baseQuery.getFullData();
			}

			//操作者单位及子单位
			if ((isOneUser && !isAdmin) || (!isOneUser && !isAdmin)) {
				powerModel.setQueryFileName("/unit/unitquerydefine");
				powerModel.setQueryName("unitquerydefine_getunitchain");
				BaseQuery baseQuery = new BaseQuery(request, powerModel);				
				userDefinedSearchCase.put("unitId", userInfo.getUnitId());
				baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
				deptList = baseQuery.getFullData();				
			}

			if (deptList.size() > 0) {
				ArrayList<DicInfo> deptChain = new ArrayList<DicInfo>();
				for(ArrayList<String> rowData:deptList) {
					DicInfo deptInfo = new DicInfo();
					deptInfo.setId(rowData.get(0));
					deptInfo.setName(rowData.get(1));
					deptInfo.setParentId(rowData.get(2));
					deptInfo.setSortNo(rowData.get(3));
					deptChain.add(deptInfo);
				}
				
				json = MakeTreeStyleData.convertDeptChainToJson(deptChain);
			}
			//单位不可选择
			//if (!isOneUser && isAdmin && !isSameUnit) {
			//}
			
		}
		catch(Exception e) {
			System.out.println("系统错误发生");
		}
		//
		
		return json;
	}
	
	public static S_POWERData updateModifyInfo(S_POWERData data, UserInfo userInfo) {
		String createPowerId = data.getCreateperson();
		if ( createPowerId == null || createPowerId.equals("")) {
			data.setCreateperson(userInfo.getUserId());
			data.setCreatetime(CalendarUtil.fmtDate());
			data.setCreateperson(userInfo.getUserId());
			data.setCreateunitid(userInfo.getUnitId());
		}
		data.setModifyperson(userInfo.getUserId());
		data.setModifytime(CalendarUtil.fmtDate());
		data.setDeleteflag(BusinessConstants.DELETEFLG_UNDELETE);
		
		return data;
	}
	
	public static S_POWERData setDeptGuid(S_POWERData data, String userId, UserInfo userInfo) throws Exception {
		if (userInfo.isSA()) {
			S_USERData userData = new S_USERData();
			userData.setUserid(userId);
			S_USERDao userDao = new S_USERDao(userData);
			data.setDeptguid(userDao.beanData.getDeptguid());
		} else {
			data.setDeptguid(userInfo.getDeptGuid());
		}
		
		return data;
	}
	
	public PowerModel getUserInfoList(HttpServletRequest request) throws Exception {
		PowerModel powerModel = new PowerModel();
		String userIdPara = request.getParameter("userId");
		String userIdArray[] = userIdPara.split(",");
		String userIdList = "";
		String userNameList = "";
		HashMap<String, String> userIdMap = new HashMap<String, String>();
		boolean isFirst = true;
		for(String userId:userIdArray) {
			if (!userIdMap.containsKey(userId)) {
				userIdMap.put(userId, "");			
				S_USERData userData = new S_USERData();
				userData.setUserid(userId);
				S_USERDao userDao = new S_USERDao(userData);		
				if (isFirst) {
					isFirst = false;
					userIdList += userId;
					userNameList += userDao.beanData.getLoginname();				
				} else {
					userIdList += "," + userId;
					userNameList += "," + userDao.beanData.getLoginname();
				}
			}
		}
		powerModel.setUserId(userIdList);
		powerModel.setUserName(userNameList);	
		
		return powerModel;
				
	}
	
	public PowerModel getRoleInfoList(HttpServletRequest request) throws Exception {
		PowerModel powerModel = new PowerModel();
		String roleIdPara = request.getParameter("roleId");
		String roleIdArray[] = roleIdPara.split(",");
		String roleIdList = "";
		String roleNameList = "";
		HashMap<String, String> roleIdMap = new HashMap<String, String>();
		
		boolean isFirst = true;
		for(String roleId:roleIdArray) {
			if (!roleIdMap.containsKey(roleId)) {
				roleIdMap.put(roleId, "");
				S_ROLEData roleData = new S_ROLEData();
				roleData.setRoleid(roleId);
				S_ROLEDao roleDao = new S_ROLEDao(roleData);		
				if (isFirst) {
					isFirst = false;
					roleIdList += roleId;
					roleNameList += roleDao.beanData.getRolename();				
				} else {
					roleIdList += "," + roleId;
					roleNameList += "," + roleDao.beanData.getRolename();
				}
			}
		}
		powerModel.setRoleId(roleIdList);
		powerModel.setRoleName(roleNameList);	
		
		return powerModel;
	}	
}
