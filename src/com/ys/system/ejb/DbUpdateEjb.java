package com.ys.system.ejb;

import java.util.ArrayList;
import java.util.HashMap;

/*
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
*/
//import javax.security.jacc.PolicyContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ys.system.action.model.dic.DicModel;
import com.ys.system.action.model.dic.DicTypeModel;
import com.ys.system.action.model.login.UserInfo;
import com.ys.system.action.model.menu.MenuModel;
import com.ys.system.action.model.power.PowerModel;
import com.ys.system.action.model.role.RoleModel;
import com.ys.system.action.model.unit.UnitModel;
import com.ys.system.action.model.user.UserModel;
import com.ys.system.common.BusinessConstants;
import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.dao.S_DICTYPEDao;
import com.ys.system.db.dao.S_MENUDao;
import com.ys.system.db.dao.S_OPERLOGDao;
import com.ys.system.db.dao.S_POWERDao;
import com.ys.system.db.dao.S_ROLEDao;
import com.ys.system.db.dao.S_ROLEMENUDao;
import com.ys.system.db.dao.S_USERDao;
import com.ys.system.db.data.S_DEPTData;
import com.ys.system.db.data.S_DICData;
import com.ys.system.db.data.S_DICTYPEData;
import com.ys.system.db.data.S_MENUData;
import com.ys.system.db.data.S_OPERLOGData;
import com.ys.system.db.data.S_POWERData;
import com.ys.system.db.data.S_ROLEData;
import com.ys.system.db.data.S_ROLEMENUData;
import com.ys.system.db.data.S_USERData;
import com.ys.system.service.common.BaseService;
import com.ys.system.service.dic.DicService;
import com.ys.system.service.dic.DicTypeService;
import com.ys.system.service.menu.MenuService;
import com.ys.system.service.power.PowerService;
import com.ys.system.service.role.RoleMenuService;
import com.ys.system.service.role.RoleService;
import com.ys.system.service.user.UserService;
import com.ys.util.CalendarUtil;
import com.ys.util.basedao.BaseDAO;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;

public class DbUpdateEjb  {
	
	BaseTransaction ts;
	
    public void executeMenuDelete(String formData, UserInfo userInfo) throws Exception {
		
		
		int count = 0;
	
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        BaseService service = new BaseService();
        ts = new BaseTransaction();

        
		try {
			ts.begin();
			String removeData[] = service.getJsonData(formData, "numCheck").split(",");
			for (String menuId:removeData) {
				count = deleteMenuChain(request, menuId, userInfo, count);
				//menuModel.setUpdatedRecordCount(count);
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }
    
    private int deleteMenuChain(HttpServletRequest request, String menuId, UserInfo userInfo, int count) throws Exception {
		BaseModel dataModel = new BaseModel();
		ArrayList<ArrayList<String>> menuDataList;
		StringBuffer sql = new StringBuffer("");
    	S_MENUData data = new S_MENUData();
    	
		data.setMenuid(menuId);
		S_MENUDao dao = new S_MENUDao(data);
		data = MenuService.updateModifyInfo(dao.beanData, userInfo);
		data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
		dao.Store(data);
		count++;

		sql.append("UPDATE s_RoleMenu SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
		sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
		sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
		sql.append(" WHERE MenuID = '" + menuId + "' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
		BaseDAO.execUpdate(sql.toString());
		
		dataModel.setQueryFileName("/menu/menuquerydefine");
		dataModel.setQueryName("menuquerydefine_searchfordelete");
		
		BaseQuery baseQuery = new BaseQuery(request, dataModel);
		
		HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
		userDefinedSearchCase.put("menuId", menuId);
		baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);	
		
		menuDataList = baseQuery.getQueryData();		
		
		for(ArrayList<String> rowData:menuDataList) {
			count = deleteMenuChain(request, rowData.get(1), userInfo, count);
		}

		return count;
    }

    public void executeRoleDelete(RoleModel roleModel, UserInfo userInfo) throws Exception {
		S_ROLEData data = new S_ROLEData();
		int count = 0;
	
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = roleModel.getNumCheck().split(",");
			for (String roleId:removeData) {
				StringBuffer sql = new StringBuffer("");
				//TODO:
				//s_power应该物理删除
				//sql.append("UPDATE s_Power SET DeleteFlag = '" + Constants.DELETEFLG_DELETED + "' ");
				//sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				//sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append("DELETE FROM s_Power ");
				sql.append(" WHERE RoleID = '" + roleId + "' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());

				sql = new StringBuffer("");
				sql.append("UPDATE s_RoleMenu SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE RoleID = '" + roleId + "' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());

				data.setRoleid(roleId);
				S_ROLEDao dao = new S_ROLEDao(data);
				data = RoleService.updateModifyInfo(dao.beanData, userInfo);
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;
				roleModel.setUpdatedRecordCount(count);
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }
    
    public void executeRoleMenuDelete(RoleModel roleModel, UserInfo userInfo) throws Exception {
		S_ROLEMENUData data = new S_ROLEMENUData();
		int count = 0;
	
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = roleModel.getNumCheck().split(",");
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				data.setId(key);
				S_ROLEMENUDao dao = new S_ROLEMENUDao(data);
				data = RoleMenuService.updateModifyInfo(dao.beanData, userInfo);				
				
				//TODO:
				//s_power应该物理删除
				//sql.append("UPDATE s_Power SET DeleteFlag = '" + Constants.DELETEFLG_DELETED + "' ");
				//sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				//sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append("DELETE FROM s_Power ");
				sql.append(" WHERE RoleID = '" + dao.beanData.getRoleid() + "' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());
				
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;
				roleModel.setUpdatedRecordCount(count);
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }
    
    public void executeRoleMenuUpdate(String roleId, ArrayList<String> menuIdList, UserInfo userInfo) throws Exception {
    	S_ROLEMENUDao dao = new S_ROLEMENUDao();
		S_ROLEMENUData data = new S_ROLEMENUData();
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			
			//删除已有的数据
			StringBuffer sql = new StringBuffer("UPDATE S_ROLEMENU SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
			sql.append(" WHERE RoleID = '" + roleId + "' AND DeleteFlag = '" +  BusinessConstants.DELETEFLG_UNDELETE + "' ");
			BaseDAO.execUpdate(sql.toString());
			
			//追加数据
			for (String menuId:menuIdList) {
				data = new S_ROLEMENUData();
				data.setId(BaseDAO.getGuId());
				data.setRoleid(roleId);
				data.setMenuid(menuId);
				data = RoleMenuService.updateModifyInfo(data, userInfo);
				dao.Create(data);
			}				

			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
    }
    
    public void executeUnitDelete(String formData, UserInfo userInfo) throws Exception {
		new S_DEPTData();
		int count = 0;
		BaseService service = new BaseService();
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = service.getJsonData(formData, "numCheck").split(",");
			for (String unitId:removeData) {
				StringBuffer sql = new StringBuffer("");
				//TODO:
				//s_power应该物理删除
				//sql.append("UPDATE s_Power SET DeleteFlag = '" + Constants.DELETEFLG_DELETED + "' ");
				//sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				//sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append("DELETE FROM s_Power ");
				sql.append(" WHERE UnitID like '" + unitId + "%' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				count += BaseDAO.execUpdate(sql.toString());

				//data.setUnitid(unitId);
				//S_DEPTDao dao = new S_DEPTDao(data);
				//data = UnitService.updateModifyInfo(dao.beanData, userInfo);
				//if (!data.getParentid().equals("")) {
				sql = new StringBuffer("");
				sql.append("UPDATE s_Dept SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE unitID LIKE '" + unitId + "%' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				count += BaseDAO.execUpdate(sql.toString());					
				//}
				
				//data.setDeleteflag(Constants.DELETEFLG_DELETED);
				//dao.Store(data);

			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
	}
    
	public void executeUserDelete(UserModel userModel, UserInfo userInfo) throws Exception {
		S_USERData data = new S_USERData();
		int count = 0;
	
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = userModel.getNumCheck().split(",");
			for (String userId:removeData) {
				StringBuffer sql = new StringBuffer("");
				//TODO:
				//s_power应该物理删除
				//sql.append("UPDATE s_Power SET DeleteFlag = '" + Constants.DELETEFLG_DELETED + "' ");
				//sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				//sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append("DELETE FROM s_Power ");
				sql.append(" WHERE UnitID = '" + userId + "' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());

				data.setUserid(userId);
				S_USERDao dao = new S_USERDao(data);
				data = UserService.updateModifyInfo(dao.beanData, userInfo);
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;
				userModel.setUpdatedRecordCount(count);
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}		
	}
	public void executeUserLock(UserModel userModel, UserInfo userInfo, String lockFlg) throws Exception {
		S_USERData data = new S_USERData();
		int count = 0;
	
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String lockData[] = userModel.getNumCheck().split(",");
			for (String userId:lockData) {

				data.setUserid(userId);
				S_USERDao dao = new S_USERDao(data);
				data = UserService.updateModifyInfo(dao.beanData, userInfo);
				if (lockFlg.equals("1")) {
					data.setLockflag(UserInfo.LOCKED);
				} else {
					data.setLockflag(UserInfo.UNLOCKED);
				}
				dao.Store(data);
				
				count++;
				userModel.setUpdatedRecordCount(count);
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}				
	}
	
	public void executeDicTypeDelete(DicTypeModel dicTypeModel, UserInfo userInfo) throws Exception {
		S_DICTYPEData data = new S_DICTYPEData();
		int count = 0;
	
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = dicTypeModel.getNumCheck().split(",");
			for (String dicTypeId:removeData) {
				StringBuffer sql = new StringBuffer("");

				sql.append("UPDATE s_Dic SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE DicTypeID = '" + dicTypeId + "' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());

				data.setDictypeid(dicTypeId);;
				S_DICTYPEDao dao = new S_DICTYPEDao(data);
				data = DicTypeService.updateModifyInfo(dao.beanData, userInfo);
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;
				dicTypeModel.setUpdatedRecordCount(count);
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}			
	}
	
	public void executeDicTypeUpdate(DicTypeModel dicTypeModel, String updateSrcDicTypeId, UserInfo userInfo) throws Exception {
		
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		
		S_DICTYPEDao dao = new S_DICTYPEDao();
		S_DICTYPEData data = DicTypeService.updateModifyInfo(dicTypeModel.getdicTypeData(), userInfo);
		
		try {
			ts.begin();
			dao.Store(data);

			if (!data.getDictypeid().equals(updateSrcDicTypeId)) {
				//dicid变更
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE s_dic SET DicTypeID = '" + dicTypeModel.getdicTypeData().getDictypeid() + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE DicTypeID = '" + updateSrcDicTypeId + "' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());
			}
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;			
		}
	}
	
	public void executeDicDelete(DicModel dicModel, UserInfo userInfo) throws Exception {
		int count = 0;
		
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		//HttpServletRequest request = (HttpServletRequest)PolicyContext.getContext("javax.servlet.http.HttpServletRequest");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		try {
			ts.begin();
			String removeData[] = dicModel.getNumCheck().split(",");
			for (String menuId:removeData) {
				count = deleteDicChain(request, menuId, userInfo, count);
				dicModel.setUpdatedRecordCount(count);
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}		
	}
	
	private int deleteDicChain(HttpServletRequest request, String dicId, UserInfo userInfo, int count) throws Exception {
			BaseModel dataModel = new BaseModel();
			ArrayList<ArrayList<String>> dicDataList;
			new StringBuffer("");
		S_DICData data = new S_DICData();
		
    	//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		try {
    		ts.begin();
			data.setDicid(dicId);
			S_DICDao dao = new S_DICDao(data);
			data = DicService.updateModifyInfo(dao.beanData, userInfo);
			data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
			dao.Store(data);
			count++;
			
			dataModel.setQueryFileName("/dic/dicquerydefine");
			dataModel.setQueryName("dicquerydefine_searchfordelete");
			BaseQuery baseQuery = new BaseQuery(request, dataModel);
			
			HashMap<String, String> userDefinedSearchCase = new HashMap<String, String>();
			userDefinedSearchCase.put("dicId", dicId);
			baseQuery.setUserDefinedSearchCase(userDefinedSearchCase);
			dicDataList = baseQuery.getQueryData();		
			
			for(ArrayList<String> rowData:dicDataList) {
				count = deleteDicChain(request, rowData.get(1), userInfo, count);
			}
			
			ts.commit();
    	}
    	catch(Exception e) {
    		ts.rollback();
    		throw e;
    	}
		return count;
	}
	
	public int executePowerAdd(PowerModel powerModel, UserInfo userInfo) throws Exception {
		String userArray[] = powerModel.getUserId().split(",");
		String roleArray[] = powerModel.getRoleId().split(",");
		String unitArray[] = powerModel.getUnitId().split(",");
		S_POWERDao dao = new S_POWERDao();
		int rowCount = 0;
		
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		try {
			ts.begin();
			for(String userId:userArray) {
				for(String roleId:roleArray) {
					for(String unitId:unitArray) {
						boolean isExisted = true;
						S_POWERData data = new S_POWERData();
						data.setUserid(userId);
						data.setRoleid(roleId);
						data.setUnitid(unitId);
						try {
							dao.FindByPrimaryKey(data);
						}
						catch(Exception e) {
							isExisted = false;
						}
						if (!isExisted) {
							data = new S_POWERData();
							data.setId(BaseDAO.getGuId());
							data.setUserid(userId);
							data.setRoleid(roleId);
							data.setUnitid(unitId);						
							data.setPowertype(powerModel.getPowerType());
							data = PowerService.setDeptGuid(data, userId, userInfo);
							data = PowerService.updateModifyInfo(data, userInfo);
							dao.Create(data);
							rowCount++;
						}
					}
				}
			}
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
	
		return rowCount;
	}	
	public void executePowerDelete(PowerModel powerModel, UserInfo userInfo) throws Exception {
		//new S_ROLEData();
		int count = 0;
	
		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		try {
			ts.begin();
			String removeData[] = powerModel.getNumCheck().split(",");
			for (String powerId:removeData) {
				StringBuffer sql = new StringBuffer("");
				//TODO:
				//s_power应该物理删除
				//sql.append("UPDATE s_Power SET DeleteFlag = '" + Constants.DELETEFLG_DELETED + "' ");
				//sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				//sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append("DELETE FROM s_Power ");
				sql.append(" WHERE id = '" + powerId + "' AND DeleteFlag = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());
				count++;
			}
			powerModel.setUpdatedRecordCount(count);
			
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
	}

	public void executeOperLogAdd(S_OPERLOGData data) throws Exception {

		//UserTransaction ts = context.getUserTransaction();
		ts = new BaseTransaction();
		S_OPERLOGDao dao = new S_OPERLOGDao();
		try {
			ts.begin();
			dao.Create(data);
			ts.commit();
		}
		catch(Exception e) {
			ts.rollback();
			throw e;
		}
	}
}

