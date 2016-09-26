package com.ys.business.ejb;

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

import com.ys.business.db.dao.B_SupplierBasicInfoDao;
import com.ys.business.db.data.B_SupplierBasicInfoData;
import com.ys.business.service.supplier.SupplierService;
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

public class BusinessDbUpdateEjb  {
	
	BaseTransaction ts;
	

    public void executeSupplierDelete(String keyData, UserInfo userInfo) throws Exception {
    	B_SupplierBasicInfoData data = new B_SupplierBasicInfoData();
		int count = 0;
	
		ts = new BaseTransaction();
		
		try {
			ts.begin();
			String removeData[] = keyData.split(",");
			for (String key:removeData) {
				StringBuffer sql = new StringBuffer("");
				data.setId(key);
				B_SupplierBasicInfoDao dao = new B_SupplierBasicInfoDao(data);
				data = SupplierService.updateModifyInfo(dao.beanData, userInfo);				
				
				sql.append("UPDATE b_Contact SET DeleteFlag = '" + BusinessConstants.DELETEFLG_DELETED + "' ");
				sql.append(", ModifyTime = '" + CalendarUtil.fmtDate() + "'");
				sql.append(", ModifyPerson = '" + userInfo.getUserId() + "'");
				sql.append(" WHERE companyCode = '" + key + "' AND DELETEFLAG = '" + BusinessConstants.DELETEFLG_UNDELETE + "'");
				BaseDAO.execUpdate(sql.toString());
				
				data.setDeleteflag(BusinessConstants.DELETEFLG_DELETED);
				dao.Store(data);
				
				count++;
				//roleModel.setUpdatedRecordCount(count);
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
    

}

